package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.Report
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import kotlinx.coroutines.flow.Flow

class ReportInteraction(private val reportRepository: IReportRepository) : ReportUseCase {
    override fun sendReport(request: Report): Flow<Resource<String>> = reportRepository.sendReport(request)
    override fun getAllReports(): Flow<List<Report>> = reportRepository.getAllReports()
}