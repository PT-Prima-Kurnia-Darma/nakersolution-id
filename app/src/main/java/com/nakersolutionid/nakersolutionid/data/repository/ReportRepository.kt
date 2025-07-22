package com.nakersolutionid.nakersolutionid.data.repository

import android.util.Log
import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.local.mapper.toDomain
import com.nakersolutionid.nakersolutionid.data.local.mapper.toEntity
import com.nakersolutionid.nakersolutionid.data.local.mapper.toHistory
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toCreateElevatorReportBody
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiResponse
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ReportRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val userPreference: UserPreference
) : IReportRepository {
    override suspend fun saveReport(request: InspectionWithDetailsDomain, extraId: String?) {
        val inspectionWithDetails = request.toEntity(extraId ?: "", request.inspection.id)
        localDataSource.insertInspection(
            inspectionEntity = inspectionWithDetails.inspectionEntity,
            checkItems = inspectionWithDetails.checkItems,
            findings = inspectionWithDetails.findings,
            testResults = inspectionWithDetails.testResults
        )
    }

    override suspend fun getInspection(id: Long): InspectionWithDetailsDomain? {
        return localDataSource.getInspection(id).map { it.toDomain() }.firstOrNull()
    }

    override suspend fun getPendingSyncReports(): List<InspectionWithDetailsDomain> {
        return localDataSource.getPendingSyncReports().map { it.toDomain() }
    }

    override suspend fun updateSyncStatus(id: Long, isSynced: Boolean) {
        localDataSource.updateSyncStatus(id, isSynced)
    }

    override suspend fun syncInspection(): Boolean {
        val listReports = localDataSource.getPendingSyncReports()
        if (listReports.isEmpty()) {
            return false
        }

        var fail = 0
        val token = userPreference.getUserToken() ?: ""

        listReports.map {
            it.toDomain()
        }.forEach { report ->
            val apiResponse = when (report.inspection.documentType) {
                DocumentType.LAPORAN -> when (report.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.createElevatorReport(token, report.toCreateElevatorReportBody()).first()
                    else -> ApiResponse.Empty
                }

                DocumentType.BAP -> ApiResponse.Empty
                else -> ApiResponse.Empty
            }

            when (apiResponse) {
                is ApiResponse.Empty -> null
                is ApiResponse.Error -> {
                    fail++
                }
                is ApiResponse.Success -> {
                    try {
                        val extraId = apiResponse.data.data.laporan.id
                        saveReport(apiResponse.data.data.laporan.toInspectionWithDetailsDomain(), extraId)
                        localDataSource.updateSyncStatus(report.inspection.id, true)
                    } catch (_: Exception) {
                        fail++
                    }
                }
            }
        }
        return fail == 0
    }

    override suspend fun syncUpdateInspection(): Boolean {
        val listReports = localDataSource.getPendingSyncReports()
        if (listReports.isEmpty()) {
            return false
        }

        var fail = 0
        val token = userPreference.getUserToken() ?: ""

        listReports.map {
            it.toDomain()
        }.forEach { report ->
            val extraId = report.inspection.extraId
            val apiResponse = when (report.inspection.documentType) {
                DocumentType.LAPORAN -> when (report.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.updateElevatorReport(token, extraId, report.toCreateElevatorReportBody()).first()
                    else -> ApiResponse.Empty
                }

                DocumentType.BAP -> ApiResponse.Empty
                else -> ApiResponse.Empty
            }

            when (apiResponse) {
                is ApiResponse.Empty -> null
                is ApiResponse.Error -> {
                    fail++
                }
                is ApiResponse.Success -> {
                    try {
                        val extraId = apiResponse.data.data.laporan.id
                        saveReport(apiResponse.data.data.laporan.toInspectionWithDetailsDomain(), extraId)
                        localDataSource.updateSyncStatus(report.inspection.id, true)
                    } catch (_: Exception) {
                        fail++
                    }
                }
            }
        }
        return fail == 0
    }

    override fun getAllReports(): Flow<List<History>> {
        return localDataSource.getAllInspectionsWithDetails().map { it ->
            it.map {
                it.inspectionEntity.toHistory()
            }
        }
    }

    override suspend fun deleteReport(id: Long) {
        val data = localDataSource.getInspection(id).firstOrNull()
        data?.let { data ->
            val extraId = data.inspectionEntity.extraId
            val token = userPreference.getUserToken() ?: ""
            val isSynced = data.inspectionEntity.isSynced

            if (!isSynced) {
                localDataSource.deleteInspection(id)
                return
            }

            val apiResponse = when (data.inspectionEntity.subInspectionType) {
                SubInspectionType.Elevator -> remoteDataSource.deleteElevatorReport(token, extraId).first()
                SubInspectionType.Escalator -> remoteDataSource.deleteEscalatorReport(token, extraId).first()
                SubInspectionType.Forklift -> remoteDataSource.deleteForkliftReport(token, extraId).first()
                SubInspectionType.Mobile_Crane -> remoteDataSource.deleteMobileCraneReport(token, extraId).first()
                SubInspectionType.Overhead_Crane -> remoteDataSource.deleteOverheadCraneReport(token, extraId).first()
                SubInspectionType.Gantry_Crane -> remoteDataSource.deleteGantryCraneReport(token, extraId).first()
                SubInspectionType.Gondola -> remoteDataSource.deleteGondolaReport(token, extraId).first()
                else -> ApiResponse.Empty
            }
            when (apiResponse) {
                is ApiResponse.Empty -> null
                is ApiResponse.Error -> null
                is ApiResponse.Success -> {
                    localDataSource.deleteInspection(id)
                }
            }
        }
    }
}





/*override fun saveReportInCloud(localId: Long, request: InspectionWithDetailsDomain): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val token = userPreference.getUserToken() ?: ""
        val apiResponse = remoteDataSource.sendReport(token, request.toNetworkDto()).first()
        when (apiResponse) {
            is ApiResponse.Success -> {
                val extraId = apiResponse.data.data.laporan?.id ?: "NONE"
                val inspectionWithDetails = request.toEntity(extraId, request.inspection.id)
                localDataSource.insertInspection(
                    inspectionEntity = inspectionWithDetails.inspectionEntity,
                    checkItems = inspectionWithDetails.checkItems,
                    findings = inspectionWithDetails.findings,
                    testResults = inspectionWithDetails.testResults
                )
//                saveInspectionInLocal("", localDataSource, request)
                emit(Resource.Success(apiResponse.data.message))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }

            is ApiResponse.Empty -> {}
        }
    }*/