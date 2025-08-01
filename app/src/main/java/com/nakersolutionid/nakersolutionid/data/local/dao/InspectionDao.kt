package com.nakersolutionid.nakersolutionid.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionEntity
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionCheckItem
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionFinding
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionTestResult
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface InspectionDao {

    /**
     * Inserts a main inspectionEntity report and returns its generated ID.
     * This ID is then used as a foreign key for related data.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspection(inspectionEntity: InspectionEntity): Long

    /**
     * Inserts a list of related data (check items, findings, etc.).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckItems(items: List<InspectionCheckItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFindings(findings: List<InspectionFinding>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestResults(results: List<InspectionTestResult>)

    /**
     * A transactional method to insert a full report with all its details.
     * This remains as a helper function for the main upsert logic.
     */
    @Transaction
    suspend fun insertInspectionWithDetails(
        inspectionEntity: InspectionEntity,
        checkItems: List<InspectionCheckItem>,
        findings: List<InspectionFinding>,
        testResults: List<InspectionTestResult>
    ): Long {
        val inspectionId = insertInspection(inspectionEntity)

        // Associate the returned ID with each related item.
        // When a parent row is replaced due to OnConflictStrategy.REPLACE,
        // the ForeignKey's onDelete = CASCADE will automatically remove the old children.
        insertCheckItems(checkItems.map { it.copy(inspectionId = inspectionId) })
        insertFindings(findings.map { it.copy(inspectionId = inspectionId) })
        insertTestResults(testResults.map { it.copy(inspectionId = inspectionId) })

        return inspectionId
    }

    /**
     * Fetches a single complete inspection with all its details by its unique backend ID.
     * This is used to check if an inspection already exists locally and to get its children for comparison.
     */
    @Transaction
    @Query("SELECT * FROM inspections WHERE extra_id = :extraId LIMIT 1")
    suspend fun getInspectionWithDetailsByExtraId(extraId: String): InspectionWithDetails?

    /**
     * MODIFIED: This function now implements a conditional "upsert" (update or insert) logic.
     * It performs a deep comparison, checking for changes in the main entity AND all its related lists
     * (checkItems, findings, testResults) before performing a database write.
     * This preserves the local `isDownloaded` and `filePath` state.
     */
    @Transaction
    suspend fun insertAllInspectionsWithDetails(inspections: List<InspectionWithDetails>) {
        for (newDetails in inspections) {
            val oldDetails = getInspectionWithDetailsByExtraId(newDetails.inspectionEntity.extraId)

            if (oldDetails == null) {
                // This inspection is not in the database yet. Insert it.
                insertInspectionWithDetails(
                    newDetails.inspectionEntity,
                    newDetails.checkItems,
                    newDetails.findings,
                    newDetails.testResults
                )
            } else {
                // The inspection exists. Check if an update is needed by comparing content.

                // 1. Compare the main InspectionEntity, ignoring local-only fields.
                val isEntityDifferent = oldDetails.inspectionEntity.copy(isDownloaded = false, filePath = "") !=
                        newDetails.inspectionEntity.copy(isDownloaded = false, filePath = "")

                // 2. Compare the child lists, ignoring auto-generated IDs and list order.
                // We map the items to a version without IDs and then convert to a Set for comparison.
                val areCheckItemsDifferent = oldDetails.checkItems.map { it.copy(id = 0, inspectionId = 0) }.toSet() !=
                        newDetails.checkItems.map { it.copy(id = 0, inspectionId = 0) }.toSet()

                val areFindingsDifferent = oldDetails.findings.map { it.copy(id = 0, inspectionId = 0) }.toSet() !=
                        newDetails.findings.map { it.copy(id = 0, inspectionId = 0) }.toSet()

                val areTestResultsDifferent = oldDetails.testResults.map { it.copy(id = 0, inspectionId = 0) }.toSet() !=
                        newDetails.testResults.map { it.copy(id = 0, inspectionId = 0) }.toSet()

                if (isEntityDifferent || areCheckItemsDifferent || areFindingsDifferent || areTestResultsDifferent) {
                    // The data from the server is different. Update it, but preserve local state.
                    val entityToUpdate = newDetails.inspectionEntity.copy(
                        id = oldDetails.inspectionEntity.id, // CRUCIAL: Use the existing primary key to trigger a REPLACE on the correct row.
                        isDownloaded = false,
                        filePath = ""
                    )

                    // This will replace the main entity and its children.
                    insertInspectionWithDetails(
                        entityToUpdate,
                        newDetails.checkItems,
                        newDetails.findings,
                        newDetails.testResults
                    )
                }
                // If nothing is different, do nothing. The local data is up-to-date.
            }
        }
    }

    @Transaction
    @Query("""
        SELECT * FROM inspections
        WHERE
            (:query IS NULL OR :query = '' OR rowid IN (
                SELECT rowid FROM inspections_fts WHERE inspections_fts MATCH :query
            ))
            AND (:documentType IS NULL OR document_type = :documentType)
            AND (:inspectionType IS NULL OR inspection_type = :inspectionType)
            AND (:subInspectionType IS NULL OR sub_inspection_type = :subInspectionType)
        ORDER BY created_at DESC
    """)
    fun searchAllInspectionsPaged(
        query: String?,
        documentType: String?,
        inspectionType: String?,
        subInspectionType: String?
    ): PagingSource<Int, InspectionWithDetails>

    /**
     * Fetches a single complete inspectionEntity report by its ID.
     * @Transaction ensures all related data is loaded in a single, consistent operation.
     */
    @Transaction
    @Query("SELECT * FROM inspections WHERE rowid = :id")
    fun getInspectionWithDetails(id: Long): Flow<InspectionWithDetails>

    /**
     * Fetches all inspectionEntity reports, including all their details.
     */
    @Transaction
    @Query("SELECT * FROM inspections ORDER BY created_at ASC")
    fun getAllInspectionsWithDetails(): Flow<List<InspectionWithDetails>>

    /**
     * Fetches all inspection reports that have not yet been synced.
     */
    @Transaction
    @Query("SELECT * FROM inspections WHERE is_synced = 0 ORDER BY created_at ASC")
    fun getAllUnsyncedInspectionsWithDetails(): Flow<List<InspectionWithDetails>>

    /**
     * Fetches all inspection reports that have been downloaded.
     */
    @Transaction
    @Query("SELECT * FROM inspections WHERE is_downloaded = 1 ORDER BY created_at DESC")
    fun getDownloadedInspectionsWithDetails(): Flow<List<InspectionWithDetails>>

    /**
     * Efficiently updates only the sync status of an inspection by its ID.
     */
    @Query("UPDATE inspections SET is_synced = :isSynced WHERE rowid = :id")
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)

    @Query("UPDATE inspections SET is_downloaded = :isDownloaded, file_path = :filePath WHERE rowid = :id")
    suspend fun updateDownloadStatus(id: Long, isDownloaded: Boolean, filePath: String)

    /**
     * Deletes an inspection and all its related data by ID.
     * Due to foreign key constraints, related data will be automatically deleted.
     */
    @Transaction
    @Query("DELETE FROM inspections WHERE rowid = :id")
    suspend fun deleteInspectionWithDetails(id: Long) // Renamed from deleteInspection for clarity

    @Query("DELETE FROM inspections WHERE rowid = :id")
    suspend fun deleteInspection(id: Long)

    @Query("DELETE FROM inspection_check_items WHERE inspection_id = :inspectionId")
    suspend fun deleteCheckItems(inspectionId: Long)

    @Query("DELETE FROM inspection_findings WHERE inspection_id = :inspectionId")
    suspend fun deleteFindings(inspectionId: Long)

    @Query("DELETE FROM inspection_test_results WHERE inspection_id = :inspectionId")
    suspend fun deleteTestResults(inspectionId: Long)

    // In InspectionDao.kt
    @Query("DELETE FROM inspections")
    suspend fun clearAllInspections()
}
