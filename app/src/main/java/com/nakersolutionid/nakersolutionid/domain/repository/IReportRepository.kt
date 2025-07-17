package com.nakersolutionid.nakersolutionid.domain.repository

import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import kotlinx.coroutines.flow.Flow

interface IReportRepository {
    suspend fun saveReport(request: InspectionWithDetailsDomain)
    suspend fun getInspection(id: Long): InspectionWithDetails?
    fun getAllReports(): Flow<List<History>>
}