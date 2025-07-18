package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import kotlinx.coroutines.flow.Flow

interface ReportUseCase {
    suspend fun saveReport(request: InspectionWithDetailsDomain)
    suspend fun getInspection(id: Long): InspectionWithDetailsDomain?
    fun getAllReports(): Flow<List<History>>
}