package com.nakersolutionid.nakersolutionid.domain.usecase

import androidx.paging.PagingData
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.history.FilterState
import kotlinx.coroutines.flow.Flow

interface ReportUseCase {
    suspend fun saveReport(request: InspectionWithDetailsDomain): Long
    fun createReport(report: InspectionWithDetailsDomain): Flow<Resource<String>>
    fun updateReport(report: InspectionWithDetailsDomain): Flow<Resource<String>>
    suspend fun getInspection(id: Long): InspectionWithDetailsDomain?
    fun getAllReports(query: String, filters: FilterState): Flow<PagingData<History>>
    fun getAllReports(query: String, filters: FilterState, fromBapScreen: Boolean): Flow<PagingData<History>>
    suspend fun deleteReport(id: Long)
    suspend fun updateDownloadedStatus(id: Long, isDownloaded: Boolean, filePath: String)
    fun getDownloadedReports(): Flow<List<History>>
}