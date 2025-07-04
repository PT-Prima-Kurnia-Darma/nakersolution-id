package com.nakersolutionid.nakersolutionid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nakersolutionid.nakersolutionid.data.local.entity.ReportEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the 'reports' table.
 * This interface defines all the database operations for ReportEntity.
 */
@Dao
interface ReportDao {

    /**
     * Inserts a single report into the database.
     * If a report with the same primary key already exists, it will be replaced.
     * This is useful for both creating new reports and updating existing ones.
     *
     * @param report The ReportEntity object to be inserted or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity)

    /**
     * Inserts a list of reports into the database.
     * If any report in the list has a primary key that already exists, it will be replaced.
     *
     * @param reports A list of ReportEntity objects to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReports(reports: List<ReportEntity>)

    /**
     * Retrieves a single report from the database by its unique ID.
     *
     * @param id The primary key of the report to retrieve.
     * @return A single ReportEntity object, or null if no report with the given ID is found.
     */
    @Query("SELECT * FROM reports WHERE id = :id")
    suspend fun getReportById(id: String): ReportEntity?

    /**
     * Retrieves all reports from the database, ordered by their ID.
     * This function returns a Flow, which allows you to observe the data for changes.
     * Your UI can automatically update whenever the list of reports changes.
     *
     * @return A Flow emitting a list of all ReportEntity objects in the database.
     */
    @Query("SELECT * FROM reports ORDER BY id DESC")
    fun getAllReports(): Flow<List<ReportEntity>>

    /**
     * Deletes a specific report from the database by its ID.
     *
     * @param id The primary key of the report to delete.
     * @return The number of rows deleted. This will be 1 if the report was found and deleted, 0 otherwise.
     */
    @Query("DELETE FROM reports WHERE id = :id")
    suspend fun deleteReportById(id: String): Int

    /**
     * Deletes all reports from the database.
     * Use this function with caution as it will clear the entire 'reports' table.
     */
    @Query("DELETE FROM reports")
    suspend fun deleteAllReports()
}
