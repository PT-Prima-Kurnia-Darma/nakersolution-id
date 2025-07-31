package com.nakersolutionid.nakersolutionid.data.local

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nakersolutionid.nakersolutionid.data.local.dao.InspectionDao
import com.nakersolutionid.nakersolutionid.data.local.dao.RemoteKeyDao
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionCheckItem
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionEntity
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionFinding
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionTestResult
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails
import com.nakersolutionid.nakersolutionid.data.local.entity.RemoteKeyEntity
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.features.history.FilterState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class LocalDataSource(
    private val inspectionDao: InspectionDao,
    private val remoteKeyDao: RemoteKeyDao,
) {
    suspend fun insertInspection(
        inspectionEntity: InspectionEntity,
        checkItems: List<InspectionCheckItem>,
        findings: List<InspectionFinding>,
        testResults: List<InspectionTestResult>
    ) = inspectionDao.insertInspectionWithDetails(
        inspectionEntity,
        checkItems,
        findings,
        testResults
    )

    fun getInspection(id: Long): Flow<InspectionWithDetails> = inspectionDao.getInspectionWithDetails(id)
    fun getAllInspectionsWithDetails(): Flow<List<InspectionWithDetails>> = inspectionDao.getAllInspectionsWithDetails()
    suspend fun getPendingSyncReports(): List<InspectionWithDetails> {
        return inspectionDao.getAllUnsyncedInspectionsWithDetails().firstOrNull() ?: emptyList()
    }

    suspend fun updateSyncStatus(id: Long, isSynced: Boolean) = inspectionDao.updateSyncStatus(id, isSynced)
    suspend fun clearAllInspections() = inspectionDao.clearAllInspections()
    suspend fun deleteInspection(id: Long) = inspectionDao.deleteInspectionWithDetails(id)
    suspend fun updateDownloadStatus(id: Long, isDownloaded: Boolean, filePath: String) = inspectionDao.updateDownloadStatus(id, isDownloaded, filePath)

    suspend fun insertAllInspectionsWithDetails(inspections: List<InspectionWithDetails>) = inspectionDao.insertAllInspectionsWithDetails(inspections)
    fun searchAllInspectionsPaged(query: String?, filters: FilterState): PagingSource<Int, InspectionWithDetails> =
        inspectionDao.searchAllInspectionsPaged(
            query = query,
            documentType = filters.documentType?.toDisplayString(),
            inspectionType = filters.inspectionType?.toDisplayString(),
            subInspectionType = filters.subInspectionType?.toDisplayString()
        )
    fun getDownloadedInspectionsWithDetails(): Flow<List<InspectionWithDetails>> = inspectionDao.getDownloadedInspectionsWithDetails()
    suspend fun insertKey(remoteKey: RemoteKeyEntity) = remoteKeyDao.insertKey(remoteKey)
    suspend fun getLatestKey(): RemoteKeyEntity? = remoteKeyDao.getLatestKey()
    suspend fun clearAllRemoteKeys() = remoteKeyDao.clearAllRemoteKeys()
}