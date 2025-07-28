package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import kotlinx.coroutines.flow.Flow

interface ReportUseCase {
    suspend fun saveReport(request: InspectionWithDetailsDomain)
    fun createReport(report: InspectionWithDetailsDomain): Flow<Resource<String>>
    fun updateReport(report: InspectionWithDetailsDomain): Flow<Resource<String>>
    suspend fun getInspection(id: Long): InspectionWithDetailsDomain?
    fun getAllReports(): Flow<List<History>>
    suspend fun deleteReport(id: Long)
}