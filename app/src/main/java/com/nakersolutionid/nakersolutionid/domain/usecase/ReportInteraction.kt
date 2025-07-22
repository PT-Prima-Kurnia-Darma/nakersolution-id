package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import kotlinx.coroutines.flow.Flow

class ReportInteraction(private val reportRepository: IReportRepository) : ReportUseCase {
    override suspend fun saveReport(request: InspectionWithDetailsDomain) {
        val extraId = request.inspection.extraId
        reportRepository.saveReport(request, extraId)
    }
    override suspend fun getInspection(id: Long): InspectionWithDetailsDomain? = reportRepository.getInspection(id)
    override fun getAllReports(): Flow<List<History>> = reportRepository.getAllReports()
    override suspend fun deleteReport(id: Long) = reportRepository.deleteReport(id)
}