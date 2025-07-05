package com.nakersolutionid.nakersolutionid.data.repository

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiResponse
import com.nakersolutionid.nakersolutionid.domain.model.Report
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import com.nakersolutionid.nakersolutionid.utils.toDomain
import com.nakersolutionid.nakersolutionid.utils.toEntity
import com.nakersolutionid.nakersolutionid.utils.toNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ReportRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val userPreference: UserPreference
) : IReportRepository {
    override fun sendReport(request: Report): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val token = userPreference.getUserToken() ?: ""
        when (val apiResponse = remoteDataSource.sendReport(token, request.toNetwork()).first()) {
            is ApiResponse.Success -> {
                val id = apiResponse.data.data.laporan.id
                val createdAt = apiResponse.data.data.laporan.createdAt
                localDataSource.insertReport(request.toEntity(id, createdAt))
                emit(Resource.Success(apiResponse.data.message))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override fun getAllReports(): Flow<List<Report>> {
        return localDataSource.getAllReports()
            .map { entityList ->
                entityList.map { it.toDomain() }
            }
    }
}