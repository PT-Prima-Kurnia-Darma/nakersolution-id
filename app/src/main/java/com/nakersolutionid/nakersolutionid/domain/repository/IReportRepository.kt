package com.nakersolutionid.nakersolutionid.domain.repository

import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import kotlinx.coroutines.flow.Flow

interface IReportRepository {
    suspend fun saveReport(request: InspectionWithDetailsDomain)
    suspend fun getInspection(id: Long): InspectionWithDetailsDomain?
    fun getAllReports(): Flow<List<History>>
}