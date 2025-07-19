package com.nakersolutionid.nakersolutionid.data.repository

//import com.nakersolutionid.nakersolutionid.utils.toEntity
//import com.nakersolutionid.nakersolutionid.data.local.mapper.toHistory
//import com.nakersolutionid.nakersolutionid.data.local.mapper.toInspectionWithDetails
import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.local.mapper.toDomain
import com.nakersolutionid.nakersolutionid.data.local.mapper.toEntity
import com.nakersolutionid.nakersolutionid.data.local.mapper.toHistory
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ReportRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val userPreference: UserPreference
) : IReportRepository {
    override suspend fun saveReport(request: InspectionWithDetailsDomain) {
        val inspectionWithDetails = request.toEntity("")
        localDataSource.insertInspection(
            inspectionEntity = inspectionWithDetails.inspectionEntity,
            checkItems = inspectionWithDetails.checkItems,
            findings = inspectionWithDetails.findings,
            testResults = inspectionWithDetails.testResults
        )
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

    override suspend fun getInspection(id: Long): InspectionWithDetailsDomain? {
        return localDataSource.getInspection(id).map { it.toDomain() }.firstOrNull()
    }

    override fun getAllReports(): Flow<List<History>> {
        return localDataSource.getAllInspectionsWithDetails().map {
            it.map {
                it.inspectionEntity.toHistory()
            }
        }
    }

    override suspend fun deleteReport(id: Long) {
        localDataSource.deleteInspection(id)
    }
}