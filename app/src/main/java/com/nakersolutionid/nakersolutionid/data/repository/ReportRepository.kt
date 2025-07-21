package com.nakersolutionid.nakersolutionid.data.repository

import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.local.mapper.toDomain
import com.nakersolutionid.nakersolutionid.data.local.mapper.toEntity
import com.nakersolutionid.nakersolutionid.data.local.mapper.toHistory
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toCreateElevatorReportBody
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
    override suspend fun saveReport(request: InspectionWithDetailsDomain) {
        val inspectionWithDetails = request.toEntity("", request.inspection.id)
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
        val token = userPreference.getUserToken() ?: ""
        val listReports = localDataSource.getPendingSyncReports()
        var fail = 0

        listReports.map {
            it.toDomain()
        }.forEach { report ->
            // region CAPE
            val apiResponse = when (report.inspection.documentType) {
                DocumentType.LAPORAN -> when (report.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.createElevatorReport(token, report.toCreateElevatorReportBody()).first()
                    else -> ApiResponse.Empty
                }

                DocumentType.BAP -> ApiResponse.Empty
                else -> ApiResponse.Empty
            }
            // endregion

            when (apiResponse) {
                is ApiResponse.Empty -> null
                is ApiResponse.Error -> {
                    fail++
                }

                is ApiResponse.Success -> {
                    try {
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
        localDataSource.deleteInspection(id)
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