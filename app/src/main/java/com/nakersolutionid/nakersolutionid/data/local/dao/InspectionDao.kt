package com.nakersolutionid.nakersolutionid.data.local.dao

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
     * You would call this from your repository.
     */
    @Transaction
    suspend fun insertInspectionWithDetails(
        inspectionEntity: InspectionEntity,
        checkItems: List<InspectionCheckItem>,
        findings: List<InspectionFinding>,
        testResults: List<InspectionTestResult>
    ): Long {
        val inspectionId = insertInspection(inspectionEntity)

        // Associate the returned ID with each related item
        insertCheckItems(checkItems.map { it.copy(inspectionId = inspectionId) })
        insertFindings(findings.map { it.copy(inspectionId = inspectionId) })
        insertTestResults(testResults.map { it.copy(inspectionId = inspectionId) })

        return inspectionId
    }

    /**
     * Fetches a single complete inspectionEntity report by its ID.
     * @Transaction ensures all related data is loaded in a single, consistent operation.
     */
    @Transaction
    @Query("SELECT * FROM inspections WHERE id = :id")
    fun getInspectionWithDetails(id: Long): Flow<InspectionWithDetails>

    /**
     * Fetches all inspectionEntity reports, including all their details.
     */
    @Transaction
    @Query("SELECT * FROM inspections ORDER BY created_at ASC")
    fun getAllInspectionsWithDetails(): Flow<List<InspectionWithDetails>>

    /**
     * Efficiently updates only the sync status of an inspection by its ID.
     */
    @Query("UPDATE inspections SET is_synced = :isSynced WHERE id = :id")
    suspend fun updateSyncStatus(id: Long, isSynced: Boolean)
}