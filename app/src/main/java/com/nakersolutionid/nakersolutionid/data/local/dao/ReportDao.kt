package com.nakersolutionid.nakersolutionid.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.nakersolutionid.nakersolutionid.data.local.entity.*
import com.nakersolutionid.nakersolutionid.data.remote.request.SendReportRequest
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the inspection report database.
 *
 * This interface defines all the necessary database operations, including complex
 * transactional inserts for handling nested network data, updates, deletes, and queries.
 * It is designed to be the single source of truth for interacting with the report data.
 */
@Dao
interface ReportDao {

    /**
     * =========================================================================================
     * INSERT OPERATIONS
     *
     * The following methods handle inserting data into the database. The main entry point
     * is the transactional `insertOrUpdateReports`, which is designed to be flexible and
     * robust.
     * =========================================================================================
     */

    // Private insert methods for each entity. These are called by the public transactional method.
    // Using OnConflictStrategy.REPLACE makes the insert operation idempotent.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralData(generalData: GeneralDataEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechnicalDocuments(technicalDocument: TechnicalDocumentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspectionAndTesting(inspectionAndTesting: InspectionAndTestingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMachineRoomInspection(entity: MachineRoomInspectionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuspensionRopesInspection(entity: SuspensionRopesInspectionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrumsAndSheavesInspection(entity: DrumsAndSheavesInspectionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoistwayPitInspection(entity: HoistwayPitInspectionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarInspection(entity: CarInspectionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGovernorAndSafetyBrakeInspection(entity: GovernorAndSafetyBrakeInspectionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCounterweightGuideRailsAndBuffersInspection(entity: CounterweightGuideRailsAndBuffersInspectionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElectricalInstallationInspection(entity: ElectricalInstallationInspectionEntity): Long

    /**
     * Inserts one or more complete reports from the network into the normalized database.
     * This method is transactional, meaning all operations within it will either succeed
     * together or fail together, ensuring database consistency.
     *
     * It flexibly accepts a variable number of `ReportNetwork` objects.
     *
     * @param reports The `ReportNetwork` objects received from the API to be inserted.
     */
    @Transaction
    suspend fun insertCompleteReports(vararg reports: SendReportRequest) {
        for (reportNetwork in reports) {
            // 1. Insert the main Report and get its generated ID
            val reportId = insertReport(
                ReportEntity(
                    nameOfInspectionType = reportNetwork.nameOfInspectionType,
                    subNameOfInspectionType = reportNetwork.subNameOfInspectionType,
                    typeInspection = reportNetwork.typeInspection,
                    eskOrElevType = reportNetwork.EskOrElevType,
                    conclusion = reportNetwork.conclusion
                )
            )

            // 2. Insert General Data and Technical Docs, linking them with the reportId
            reportNetwork.generalData?.let {
                insertGeneralData(
                    GeneralDataEntity(reportId = reportId, /* ... map other fields ... */)
                )
            }
            reportNetwork.technicalDocumentInspection?.let {
                insertTechnicalDocuments(
                    TechnicalDocumentEntity(reportId = reportId, /* ... map other fields ... */)
                )
            }

            // 3. Insert the main InspectionAndTesting hub entity and get its ID
            reportNetwork.inspectionAndTesting?.let { inspection ->
                val inspectionId = insertInspectionAndTesting(
                    InspectionAndTestingEntity(reportId = reportId)
                )

                // 4. Insert all detailed inspection sub-entities, linking them with the inspectionId
                inspection.machineRoomAndMachinery?.let {
                    insertMachineRoomInspection(MachineRoomInspectionEntity(inspectionId = inspectionId, /* ... */))
                }
                inspection.suspensionRopesAndBelts?.let {
                    insertSuspensionRopesInspection(SuspensionRopesInspectionEntity(inspectionId = inspectionId, /* ... */))
                }
                inspection.drumsAndSheaves?.let {
                    insertDrumsAndSheavesInspection(DrumsAndSheavesInspectionEntity(inspectionId = inspectionId, /* ... */))
                }
                inspection.hoistwayAndPit?.let {
                    insertHoistwayPitInspection(HoistwayPitInspectionEntity(inspectionId = inspectionId, /* ... */))
                }
                inspection.car?.let {
                    insertCarInspection(CarInspectionEntity(inspectionId = inspectionId, /* ... */))
                }
                inspection.governorAndSafetyBrake?.let {
                    insertGovernorAndSafetyBrakeInspection(GovernorAndSafetyBrakeInspectionEntity(inspectionId = inspectionId, /* ... */))
                }
                inspection.counterweightGuideRailsAndBuffers?.let {
                    insertCounterweightGuideRailsAndBuffersInspection(CounterweightGuideRailsAndBuffersInspectionEntity(inspectionId = inspectionId, /* ... */))
                }
                inspection.electricalInstallation?.let {
                    insertElectricalInstallationInspection(ElectricalInstallationInspectionEntity(inspectionId = inspectionId, /* ... */))
                }
            }
        }
    }


    /**
     * =========================================================================================
     * QUERY OPERATIONS
     * =========================================================================================
     */

    /**
     * Retrieves all reports from the database as a list of `CompleteReport` objects.
     * The `@Transaction` annotation is crucial here, as it tells Room to perform the
     * necessary joins to assemble the related entities into the `CompleteReport` data class.
     *
     * @return A Flow that emits a list of all complete reports whenever the data changes.
     */
    @Transaction
    @Query("SELECT * FROM reports ORDER BY created_at DESC")
    fun getAllCompleteReports(): Flow<List<CompleteReport>>


    /**
     * =========================================================================================
     * UPDATE OPERATIONS
     * =========================================================================================
     */

    // Individual update methods for each entity
    @Update
    suspend fun updateReport(report: ReportEntity)

    @Update
    suspend fun updateGeneralData(generalData: GeneralDataEntity)

    // ... Add @Update methods for every other entity as needed ...


    /**
     * Updates a complete report in the database within a single transaction.
     * This ensures data consistency when modifying a report.
     *
     * @param completeReport The `CompleteReport` object containing the updated data.
     */
    @Transaction
    suspend fun updateCompleteReport(completeReport: CompleteReport) {
        updateReport(completeReport.report)
        completeReport.generalData?.let { updateGeneralData(it) }
        // ... Call update for every other entity in the CompleteReport object ...
    }


    /**
     * =========================================================================================
     * DELETE OPERATIONS
     * =========================================================================================
     */

    /**
     * Deletes a report from the database.
     * Because of the `onDelete = ForeignKey.CASCADE` setting in the entity definitions,
     * deleting a `ReportEntity` will automatically trigger the deletion of all its
     * associated child data across all tables. This makes the delete operation simple
     * and safe.
     *
     * @param report The `ReportEntity` to delete.
     */
    @Delete
    suspend fun deleteReport(report: ReportEntity)

}
