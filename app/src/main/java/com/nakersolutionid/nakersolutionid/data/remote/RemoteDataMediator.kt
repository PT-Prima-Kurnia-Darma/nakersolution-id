package com.nakersolutionid.nakersolutionid.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.google.gson.Gson
import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.local.database.AppDatabase
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails
import com.nakersolutionid.nakersolutionid.data.local.entity.RemoteKeyEntity
import com.nakersolutionid.nakersolutionid.data.local.mapper.toEntity
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.toSubInspectionType
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane.GantryCraneBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane.GantryCraneReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.gondola.GondolaBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.gondola.GondolaReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.overheadcrane.OverheadCraneBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.overheadcrane.OverheadCraneReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.pubt.PubtBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.pubt.PubtReportData
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class RemoteDataMediator(
    private val userPreference: UserPreference,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appDatabase: AppDatabase,
    private val gson: Gson
) : RemoteMediator<Int, InspectionWithDetails>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (localDataSource.getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, InspectionWithDetails>
    ): MediatorResult {
        return try {
            // 1. Determine the page to load
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    // On refresh, start from the first page.
                    Log.d("RemoteDataMediator", "Refreshing data...")
                    1
                }
                LoadType.PREPEND -> {
                    // We don't support prepending in this example.
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    // Get the remote key for the last item in the list.
                    val lastItem = state.lastItemOrNull()

                    // 💡 FIX: This check solves the race condition.
                    // If lastItem is null, it means REFRESH is likely still running.
                    // We return 'false' to let the Paging library know it should try again later.
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = false)
                    }

                    val remoteKey = localDataSource.getRemoteKeyByInspectionId(lastItem.inspectionEntity.id)
                        ?: return MediatorResult.Error(IOException("Remote key for last item not found"))

                    // If nextKey is null, we have reached the end.
                    remoteKey.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            // 2. Fetch data from the network
            val token = "Bearer ${userPreference.getUserToken() ?: ""}"
            val response = remoteDataSource.getAllAudits(
                token = token,
                page = page,
                size = state.config.pageSize
            )

            if (!response.isSuccessful) {
                throw HttpException(response)
            }

            val auditResponse = response.body()
            if (auditResponse == null) {
                return MediatorResult.Error(IOException("Response body is null"))
            }

            val dataJson = auditResponse.data

            val inspectionList: MutableList<InspectionWithDetailsDomain> = mutableListOf()
            if (dataJson.isJsonArray) {
                val inspectionArrayJson = dataJson.asJsonArray

                for ((index, inspectionJson) in inspectionArrayJson.withIndex()) {
                    if (inspectionJson.isJsonObject) {
                        val inspection = inspectionJson.asJsonObject
                        val documentType = inspection.get("documentType")?.asString
                        val subInspectionType = inspection.get("subInspectionType")?.asString

                        if (documentType == null) continue
                        if (subInspectionType == null) continue

                        val inspectionDomain = when (documentType.toDocumentType()) {
                            DocumentType.LAPORAN -> when (subInspectionType.toSubInspectionType()) {
                                SubInspectionType.Elevator -> gson.fromJson(inspection, ElevatorReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Escalator -> gson.fromJson(inspection, EscalatorReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Forklift -> gson.fromJson(inspection, ForkliftReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Mobile_Crane -> gson.fromJson(inspection, MobileCraneReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Overhead_Crane -> gson.fromJson(inspection, OverheadCraneReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Gantry_Crane -> gson.fromJson(inspection, GantryCraneReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Gondola -> gson.fromJson(inspection, GondolaReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Electrical -> gson.fromJson(inspection, ElectricalReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Lightning_Conductor -> gson.fromJson(inspection, LightningReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.General_PUBT -> gson.fromJson(inspection, PubtReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Fire_Protection -> gson.fromJson(inspection, IpkReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Motor_Diesel -> gson.fromJson(inspection, DieselReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Machine -> gson.fromJson(inspection, MachineReportData::class.java).toInspectionWithDetailsDomain()
                                null -> continue
                            }

                            DocumentType.BAP -> when (subInspectionType.toSubInspectionType()) {
                                SubInspectionType.Elevator -> gson.fromJson(inspection, ElevatorBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Escalator -> gson.fromJson(inspection, EscalatorBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Forklift -> gson.fromJson(inspection, ForkliftBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Mobile_Crane -> gson.fromJson(inspection, MobileCraneBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Overhead_Crane -> gson.fromJson(inspection, OverheadCraneBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Gantry_Crane -> gson.fromJson(inspection, GantryCraneBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Gondola -> gson.fromJson(inspection, GondolaBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Electrical -> gson.fromJson(inspection, ElectricalBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Lightning_Conductor -> gson.fromJson(inspection, LightningBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.General_PUBT -> gson.fromJson(inspection, PubtBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Fire_Protection -> gson.fromJson(inspection, IpkBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Motor_Diesel -> gson.fromJson(inspection, DieselBapReportData::class.java).toInspectionWithDetailsDomain()
                                SubInspectionType.Machine -> gson.fromJson(inspection, MachineBapReportData::class.java).toInspectionWithDetailsDomain()
                                null -> continue
                            }

                            else -> continue
                        }

                        inspectionList.add(inspectionDomain)
                    }
                }
            }

            val endOfPaginationReached = page >= auditResponse.paging.totalPages
            Log.d("RemoteDataMediator", "Page: $page, Size: ${state.config.pageSize}, Total Pages: ${auditResponse.paging.totalPages} ,End of Pagination: $endOfPaginationReached")

            // 4. Save the data and remote keys into the database
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // On refresh, clear old data.
                    Log.d("RemoteDataMediator", "Clearing old data...")
                    localDataSource.clearAllInspections()
                    localDataSource.clearRemoteKeys()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                // Create a mutable list to hold the correctly formed keys
                val newRemoteKeys = mutableListOf<RemoteKeyEntity>()

                // Iterate through each fetched report
                for (inspectionDomain in inspectionList) {
                    val inspectionEntity = inspectionDomain.toEntity()

                    // Insert a single inspection and get its newly generated ID back.
                    // This requires using a DAO method that returns the ID.
                    // Assuming localDataSource.insertInspection returns the new Long ID.
                    val newId = localDataSource.insertInspection(
                        inspectionEntity.inspectionEntity,
                        inspectionEntity.checkItems,
                        inspectionEntity.findings,
                        inspectionEntity.testResults
                    )

                    // Create the remote key using the CORRECT, newly generated ID
                    newRemoteKeys.add(RemoteKeyEntity(inspectionId = newId, prevKey = prevKey, nextKey = nextKey))
                }

                // Now, insert all the correctly created remote keys into the database
                localDataSource.insertAll(newRemoteKeys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            // Catching other exceptions like JsonSyntaxException during parsing
            MediatorResult.Error(e)
        }
    }
}