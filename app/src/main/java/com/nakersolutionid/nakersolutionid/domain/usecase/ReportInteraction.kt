package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import kotlinx.coroutines.flow.Flow

class ReportInteraction(private val reportRepository: IReportRepository) : ReportUseCase {
    override suspend fun saveReport(request: InspectionWithDetailsDomain) = reportRepository.saveReport(request)
    override fun createReport(report: InspectionWithDetailsDomain): Flow<Resource<String>> = reportRepository.createReport(
        report
    )
    override fun updateReport(report: InspectionWithDetailsDomain): Flow<Resource<String>> = reportRepository.updateReport(
        report
    )
    override suspend fun getInspection(id: Long): InspectionWithDetailsDomain? = reportRepository.getInspection(id)
    override fun getAllReports(): Flow<List<History>> = reportRepository.getAllReports()
    override suspend fun deleteReport(id: Long) = reportRepository.deleteReport(id)
}