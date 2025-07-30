package com.nakersolutionid.nakersolutionid.domain.usecase

import androidx.paging.PagingData
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import com.nakersolutionid.nakersolutionid.features.history.FilterState
import kotlinx.coroutines.flow.Flow

class ReportInteraction(private val reportRepository: IReportRepository) : ReportUseCase {
    override suspend fun saveReport(request: InspectionWithDetailsDomain): Long = reportRepository.saveReport(request)
    override fun createReport(report: InspectionWithDetailsDomain): Flow<Resource<String>> = reportRepository.createReport(
        report
    )
    override fun updateReport(report: InspectionWithDetailsDomain): Flow<Resource<String>> = reportRepository.updateReport(
        report
    )
    override suspend fun getInspection(id: Long): InspectionWithDetailsDomain? = reportRepository.getInspection(id)
    override fun getAllReports(query: String, filters: FilterState): Flow<PagingData<History>> = reportRepository.getAllReports(query, filters)
    override fun getAllReports(
        query: String,
        filters: FilterState,
        fromBapScreen: Boolean
    ): Flow<PagingData<History>> = reportRepository.getAllReports(query, filters, fromBapScreen)

    override suspend fun deleteReport(id: Long) = reportRepository.deleteReport(id)
    override suspend fun updateDownloadedStatus(id: Long, isDownloaded: Boolean, filePath: String) = reportRepository.updateDownloadedStatus(id, isDownloaded, filePath)
    override fun getDownloadedReports(): Flow<List<History>> = reportRepository.getDownloadedReports()
}