package com.nakersolutionid.nakersolutionid.domain.repository

import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import kotlinx.coroutines.flow.Flow

interface IReportRepository {
    suspend fun saveReport(request: InspectionWithDetailsDomain)
    suspend fun getInspection(id: Long): InspectionWithDetailsDomain?
    suspend fun getPendingSyncReports(): List<InspectionWithDetailsDomain>
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
    suspend fun syncInspection(): Boolean
    suspend fun syncUpdateInspection(): Boolean
    fun getAllReports(): Flow<List<History>>
    suspend fun deleteReport(id: Long)
}