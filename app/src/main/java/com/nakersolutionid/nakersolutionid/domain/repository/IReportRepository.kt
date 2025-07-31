package com.nakersolutionid.nakersolutionid.domain.repository

import androidx.paging.PagingData
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.ml.MLData
import com.nakersolutionid.nakersolutionid.domain.model.DownloadInfo
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.history.FilterState
import kotlinx.coroutines.flow.Flow

interface IReportRepository {
    suspend fun saveReport(request: InspectionWithDetailsDomain): Long
    fun createReport(report: InspectionWithDetailsDomain): Flow<Resource<String>>
    fun updateReport(report: InspectionWithDetailsDomain): Flow<Resource<String>>
    suspend fun getInspection(id: Long): InspectionWithDetailsDomain?
    suspend fun getPendingSyncReports(): List<InspectionWithDetailsDomain>
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
    suspend fun syncInspection(): Boolean
    suspend fun syncUpdateInspection(): Boolean
    fun getAllReports(query: String, filters: FilterState): Flow<PagingData<History>>
    fun getAllReports(query: String, filters: FilterState, fromBapScreen: Boolean): Flow<PagingData<History>>
    fun getDownloadedReports(): Flow<List<History>>
    suspend fun deleteReport(id: Long)
    suspend fun getDownloadInfo(id: Long): Resource<DownloadInfo>
    suspend fun updateDownloadedStatus(id: Long, isDownloaded: Boolean, filePath: String)
    fun getApiPath(subInspectionType: SubInspectionType, documentType: DocumentType): String
    fun getMLResult(inspection: InspectionWithDetailsDomain): Flow<Resource<MLData>>
}