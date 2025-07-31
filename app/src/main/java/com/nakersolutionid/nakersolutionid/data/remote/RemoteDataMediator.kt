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
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES)

        return if (System.currentTimeMillis() - (localDataSource.getLatestKey()?.createdAt ?: 0) < cacheTimeout) {
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
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val nextKey = localDataSource.getLatestKey()?.nextKey
                    Log.i("RemoteDataMediator", "APPEND -> nextKey: $nextKey")
                    if (nextKey == null) return MediatorResult.Success(endOfPaginationReached = true)
                    nextKey
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

                for ((_, inspectionJson) in inspectionArrayJson.withIndex()) {
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
            Log.i("RemoteDataMediator", "End of Pagination ($page >= ${auditResponse.paging.totalPages}, Total Items: ${auditResponse.paging.totalItems}): $endOfPaginationReached")

            val inspectionEntityList = inspectionList.map { it.toEntity() }
            localDataSource.insertAllInspectionsWithDetails(inspectionEntityList)

            if (loadType == LoadType.REFRESH) {
//                localDataSource.clearAllInspections()
                localDataSource.clearAllRemoteKeys()
            }

            val nextKey = if (endOfPaginationReached) null else page + 1

            localDataSource.insertKey(RemoteKeyEntity(nextKey = nextKey))

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