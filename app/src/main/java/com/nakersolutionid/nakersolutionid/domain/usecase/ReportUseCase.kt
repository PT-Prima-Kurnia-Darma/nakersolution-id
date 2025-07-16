package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import kotlinx.coroutines.flow.Flow

interface ReportUseCase {
    suspend fun saveReport(request: InspectionWithDetailsDomain)
    fun getAllReports(): Flow<List<History>>
}