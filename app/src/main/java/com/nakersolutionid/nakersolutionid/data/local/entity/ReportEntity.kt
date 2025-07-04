package com.nakersolutionid.nakersolutionid.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

/**
 * =========================================================================================
 * CORE ENTITIES (DATABASE TABLES)
 *
 * These data classes represent the actual tables in your Room database.
 * They are designed to be "flat" and normalized for maximum query performance.
 * Each entity has a primary key, and relationships are managed via foreign keys.
 * This structure avoids nested objects in a single table, which is inefficient.
 * =========================================================================================
 */

/**
 * Represents a single inspection check, replacing the repetitive `ResultStatusNetwork`.
 * This class will be embedded into other entities to avoid creating thousands of tiny tables,
 * which is a key optimization for performance.
 *
 * @property result The textual outcome or finding of the check (e.g., "Good", "Needs Repair").
 * @property status The boolean status of the check (e.g., true for 'Pass', false for 'Fail').
 * @property notes Optional field for additional remarks or details from the inspector.
 */
data class ResultStatus(
    var result: String? = null,
    var status: Boolean? = null,
    var notes: String? = null // Added for more detailed reports
)

/**
 * The main entity representing a single, complete inspection report.
 * This is the parent table for all other related data.
 *
 * @param id The unique identifier for this report.
 */
@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "inspection_type_name")
    val nameOfInspectionType: String? = null,

    @ColumnInfo(name = "inspection_type_subname")
    val subNameOfInspectionType: String? = null,

    @ColumnInfo(name = "type_inspection")
    val typeInspection: String? = null,

    @ColumnInfo(name = "elevator_type")
    val eskOrElevType: String? = null,

    @ColumnInfo(name = "conclusion")
    val conclusion: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis() // Track when the report was created
)

/**
 * General data about the elevator and its owner.
 * Linked to a ReportEntity via a one-to-one relationship.
 */
@Entity(
    tableName = "general_data",
    foreignKeys = [ForeignKey(
        entity = ReportEntity::class,
        parentColumns = ["id"],
        childColumns = ["report_id"],
        onDelete = ForeignKey.CASCADE // If a report is deleted, this data is also deleted.
    )]
)
data class GeneralDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "report_id", index = true)
    val reportId: Long,

    val ownerName: String? = null,
    val ownerAddress: String? = null,
    val nameUsageLocation: String? = null,
    val addressUsageLocation: String? = null,
    val manufacturerOrInstaller: String? = null,
    val elevatorType: String? = null,
    val brandOrType: String? = null,
    val countryAndYear: String? = null,
    val serialNumber: String? = null,
    val capacity: String? = null,
    val speed: String? = null,
    val floorsServed: String? = null,
    val permitNumber: String? = null,
    val inspectionDate: String? = null
)

/**
 * Status of technical documentation.
 * Linked to a ReportEntity via a one-to-one relationship.
 */
@Entity(
    tableName = "technical_documents",
    foreignKeys = [ForeignKey(
        entity = ReportEntity::class,
        parentColumns = ["id"],
        childColumns = ["report_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TechnicalDocumentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "report_id", index = true)
    val reportId: Long,

    @Embedded(prefix = "design_drawing_")
    val designDrawing: ResultStatus? = null,

    @Embedded(prefix = "tech_calculation_")
    val technicalCalculation: ResultStatus? = null,

    @Embedded(prefix = "material_cert_")
    val materialCertificate: ResultStatus? = null,

    @Embedded(prefix = "control_panel_diagram_")
    val controlPanelDiagram: ResultStatus? = null,

    @Embedded(prefix = "as_built_drawing_")
    val asBuiltDrawing: ResultStatus? = null,

    @Embedded(prefix = "component_certs_")
    val componentCertificates: ResultStatus? = null,

    @Embedded(prefix = "safe_work_procedure_")
    val safeWorkProcedure: ResultStatus? = null
)


/**
 * Central entity for all physical inspection and testing results.
 * This acts as a hub, linking various sub-inspection tables to a single report.
 */
@Entity(
    tableName = "inspections_and_testing",
    foreignKeys = [ForeignKey(
        entity = ReportEntity::class,
        parentColumns = ["id"],
        childColumns = ["report_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class InspectionAndTestingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "report_id", index = true)
    val reportId: Long
)


/**
 * Detailed inspection results for the Machine Room and Machinery.
 * Linked to the main InspectionAndTestingEntity.
 */
@Entity(
    tableName = "machine_room_inspection",
    foreignKeys = [ForeignKey(
        entity = InspectionAndTestingEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MachineRoomInspectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @Embedded(prefix = "machine_mounting_")
    val machineMounting: ResultStatus? = null,
    @Embedded(prefix = "mechanical_brake_")
    val mechanicalBrake: ResultStatus? = null,
    @Embedded(prefix = "electrical_brake_")
    val electricalBrake: ResultStatus? = null,
    @Embedded(prefix = "room_construction_")
    val machineRoomConstruction: ResultStatus? = null,
    @Embedded(prefix = "room_clearance_")
    val machineRoomClearance: ResultStatus? = null,
    @Embedded(prefix = "room_implementation_")
    val machineRoomImplementation: ResultStatus? = null,
    @Embedded(prefix = "ventilation_")
    val ventilation: ResultStatus? = null,
    @Embedded(prefix = "room_door_")
    val machineRoomDoor: ResultStatus? = null,
    @Embedded(prefix = "main_power_panel_")
    val mainPowerPanelPosition: ResultStatus? = null,
    @Embedded(prefix = "rotating_parts_guard_")
    val rotatingPartsGuard: ResultStatus? = null,
    @Embedded(prefix = "rope_hole_guard_")
    val ropeHoleGuard: ResultStatus? = null,
    @Embedded(prefix = "access_ladder_")
    val machineRoomAccessLadder: ResultStatus? = null,
    @Embedded(prefix = "floor_level_diff_")
    val floorLevelDifference: ResultStatus? = null,
    @Embedded(prefix = "fire_extinguisher_")
    val fireExtinguisher: ResultStatus? = null,
    @Embedded(prefix = "emergency_stop_")
    val emergencyStopSwitch: ResultStatus? = null,

    // Nested MachineRoomlessNetwork is now embedded for performance
    @Embedded(prefix = "mrl_panel_placement_")
    val panelPlacement: ResultStatus? = null,
    @Embedded(prefix = "mrl_lighting_work_area_")
    val lightingWorkArea: ResultStatus? = null,
    @Embedded(prefix = "mrl_lighting_between_work_area_")
    val lightingBetweenWorkArea: ResultStatus? = null,
    @Embedded(prefix = "mrl_manual_brake_release_")
    val manualBrakeRelease: ResultStatus? = null,
    @Embedded(prefix = "mrl_fire_extinguisher_placement_")
    val fireExtinguisherPlacement: ResultStatus? = null
)


/**
 * Detailed inspection results for Suspension Ropes and Belts.
 */
@Entity(
    tableName = "suspension_ropes_inspection",
    foreignKeys = [ForeignKey(
        entity = InspectionAndTestingEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SuspensionRopesInspectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @Embedded(prefix = "condition_")
    val condition: ResultStatus? = null,
    @Embedded(prefix = "chain_usage_")
    val chainUsage: ResultStatus? = null,
    @Embedded(prefix = "safety_factor_")
    val safetyFactor: ResultStatus? = null,
    @Embedded(prefix = "rope_with_counterweight_")
    val ropeWithCounterweight: ResultStatus? = null,
    @Embedded(prefix = "rope_without_counterweight_")
    val ropeWithoutCounterweight: ResultStatus? = null,
    @Embedded(prefix = "belt_")
    val belt: ResultStatus? = null,
    @Embedded(prefix = "slack_rope_device_")
    val slackRopeDevice: ResultStatus? = null
)

/**
 * Detailed inspection results for Drums and Sheaves.
 */
@Entity(
    tableName = "drums_sheaves_inspection",
    foreignKeys = [ForeignKey(
        entity = InspectionAndTestingEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DrumsAndSheavesInspectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @Embedded(prefix = "drum_grooves_")
    val drumGrooves: ResultStatus? = null,
    @Embedded(prefix = "passenger_drum_diameter_")
    val passengerDrumDiameter: ResultStatus? = null,
    @Embedded(prefix = "governor_drum_diameter_")
    val governorDrumDiameter: ResultStatus? = null
)


/**
 * Detailed inspection results for the Hoistway and Pit.
 */
@Entity(
    tableName = "hoistway_pit_inspection",
    foreignKeys = [ForeignKey(
        entity = InspectionAndTestingEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HoistwayPitInspectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @Embedded(prefix = "construction_")
    val construction: ResultStatus? = null,
    @Embedded(prefix = "walls_")
    val walls: ResultStatus? = null,
    @Embedded(prefix = "inclined_track_bed_")
    val inclinedElevatorTrackBed: ResultStatus? = null,
    @Embedded(prefix = "cleanliness_")
    val cleanliness: ResultStatus? = null,
    @Embedded(prefix = "lighting_")
    val lighting: ResultStatus? = null,
    @Embedded(prefix = "emergency_door_non_stop_")
    val emergencyDoorNonStop: ResultStatus? = null,
    @Embedded(prefix = "emergency_door_size_")
    val emergencyDoorSize: ResultStatus? = null,
    @Embedded(prefix = "emergency_door_safety_switch_")
    val emergencyDoorSafetySwitch: ResultStatus? = null,
    @Embedded(prefix = "emergency_door_bridge_")
    val emergencyDoorBridge: ResultStatus? = null,
    @Embedded(prefix = "car_top_clearance_")
    val carTopClearance: ResultStatus? = null,
    @Embedded(prefix = "pit_clearance_")
    val pitClearance: ResultStatus? = null,
    @Embedded(prefix = "pit_ladder_")
    val pitLadder: ResultStatus? = null,
    @Embedded(prefix = "pit_below_working_area_")
    val pitBelowWorkingArea: ResultStatus? = null,
    @Embedded(prefix = "pit_access_switch_")
    val pitAccessSwitch: ResultStatus? = null,
    @Embedded(prefix = "pit_screen_")
    val pitScreen: ResultStatus? = null,
    @Embedded(prefix = "hoistway_door_leaf_")
    val hoistwayDoorLeaf: ResultStatus? = null,
    @Embedded(prefix = "hoistway_door_interlock_")
    val hoistwayDoorInterlock: ResultStatus? = null,
    @Embedded(prefix = "floor_leveling_")
    val floorLeveling: ResultStatus? = null,
    @Embedded(prefix = "hoistway_separator_beam_")
    val hoistwaySeparatorBeam: ResultStatus? = null,
    @Embedded(prefix = "inclined_elevator_stairs_")
    val inclinedElevatorStairs: ResultStatus? = null
)


/**
 * Detailed inspection results for the Elevator Car.
 */
@Entity(
    tableName = "car_inspection",
    foreignKeys = [ForeignKey(
        entity = InspectionAndTestingEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CarInspectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @Embedded(prefix = "frame_")
    val frame: ResultStatus? = null,
    @Embedded(prefix = "body_")
    val body: ResultStatus? = null,
    @Embedded(prefix = "wall_height_")
    val wallHeight: ResultStatus? = null,
    @Embedded(prefix = "floor_area_")
    val floorArea: ResultStatus? = null,
    @Embedded(prefix = "area_expansion_")
    val carAreaExpansion: ResultStatus? = null,
    @Embedded(prefix = "car_door_")
    val carDoor: ResultStatus? = null,
    @Embedded(prefix = "to_beam_clearance_")
    val carToBeamClearance: ResultStatus? = null,
    @Embedded(prefix = "alarm_bell_")
    val alarmBell: ResultStatus? = null,
    @Embedded(prefix = "backup_power_ard_")
    val backupPowerARD: ResultStatus? = null,
    @Embedded(prefix = "intercom_")
    val intercom: ResultStatus? = null,
    @Embedded(prefix = "ventilation_")
    val ventilation: ResultStatus? = null,
    @Embedded(prefix = "emergency_lighting_")
    val emergencyLighting: ResultStatus? = null,
    @Embedded(prefix = "operating_panel_")
    val operatingPanel: ResultStatus? = null,
    @Embedded(prefix = "position_indicator_")
    val carPositionIndicator: ResultStatus? = null,
    @Embedded(prefix = "roof_strength_")
    val carRoofStrength: ResultStatus? = null,
    @Embedded(prefix = "top_emergency_exit_")
    val carTopEmergencyExit: ResultStatus? = null,
    @Embedded(prefix = "side_emergency_exit_")
    val carSideEmergencyExit: ResultStatus? = null,
    @Embedded(prefix = "top_guard_rail_")
    val carTopGuardRail: ResultStatus? = null,
    @Embedded(prefix = "guard_rail_height_300_850_")
    val guardRailHeight300to850: ResultStatus? = null,
    @Embedded(prefix = "guard_rail_height_over_850_")
    val guardRailHeightOver850: ResultStatus? = null,
    @Embedded(prefix = "top_lighting_")
    val carTopLighting: ResultStatus? = null,
    @Embedded(prefix = "manual_op_buttons_")
    val manualOperationButtons: ResultStatus? = null,
    @Embedded(prefix = "interior_")
    val carInterior: ResultStatus? = null,

    // Embedded CarDoorSpecs
    @Embedded(prefix = "door_spec_size_")
    val doorSpecSize: ResultStatus? = null,
    @Embedded(prefix = "door_spec_lock_switch_")
    val doorSpecLockAndSwitch: ResultStatus? = null,
    @Embedded(prefix = "door_spec_sill_clearance_")
    val doorSpecSillClearance: ResultStatus? = null,

    // Embedded CarSignage
    @Embedded(prefix = "sign_mfg_name_")
    val signManufacturerName: ResultStatus? = null,
    @Embedded(prefix = "sign_load_capacity_")
    val signLoadCapacity: ResultStatus? = null,
    @Embedded(prefix = "sign_no_smoking_")
    val signNoSmokingSign: ResultStatus? = null,
    @Embedded(prefix = "sign_overload_indicator_")
    val signOverloadIndicator: ResultStatus? = null,
    @Embedded(prefix = "sign_door_buttons_")
    val signDoorOpenCloseButtons: ResultStatus? = null,
    @Embedded(prefix = "sign_floor_buttons_")
    val signFloorButtons: ResultStatus? = null,
    @Embedded(prefix = "sign_alarm_button_")
    val signAlarmButton: ResultStatus? = null,
    @Embedded(prefix = "sign_two_way_intercom_")
    val signTwoWayIntercom: ResultStatus? = null
)

/**
 * Detailed inspection results for the Governor and Safety Brake.
 */
@Entity(
    tableName = "governor_safety_brake_inspection",
    foreignKeys = [ForeignKey(
        entity = InspectionAndTestingEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class GovernorAndSafetyBrakeInspectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @Embedded(prefix = "gov_rope_clamp_")
    val governorRopeClamp: ResultStatus? = null,
    @Embedded(prefix = "gov_switch_")
    val governorSwitch: ResultStatus? = null,
    @Embedded(prefix = "safety_brake_speed_")
    val safetyBrakeSpeed: ResultStatus? = null,
    @Embedded(prefix = "safety_brake_type_")
    val safetyBrakeType: ResultStatus? = null,
    @Embedded(prefix = "safety_brake_mechanism_")
    val safetyBrakeMechanism: ResultStatus? = null,
    @Embedded(prefix = "progressive_safety_brake_")
    val progressiveSafetyBrake: ResultStatus? = null,
    @Embedded(prefix = "instantaneous_safety_brake_")
    val instantaneousSafetyBrake: ResultStatus? = null,
    @Embedded(prefix = "safety_brake_operation_")
    val safetyBrakeOperation: ResultStatus? = null,
    @Embedded(prefix = "electrical_cutout_switch_")
    val electricalCutoutSwitch: ResultStatus? = null,
    @Embedded(prefix = "limit_switch_")
    val limitSwitch: ResultStatus? = null,
    @Embedded(prefix = "overload_device_")
    val overloadDevice: ResultStatus? = null
)

/**
 * Detailed inspection results for Counterweight, Guide Rails, and Buffers.
 */
@Entity(
    tableName = "counterweight_buffers_inspection",
    foreignKeys = [ForeignKey(
        entity = InspectionAndTestingEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CounterweightGuideRailsAndBuffersInspectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @Embedded(prefix = "counterweight_material_")
    val counterweightMaterial: ResultStatus? = null,
    @Embedded(prefix = "counterweight_guard_screen_")
    val counterweightGuardScreen: ResultStatus? = null,
    @Embedded(prefix = "guide_rail_construction_")
    val guideRailConstruction: ResultStatus? = null,
    @Embedded(prefix = "buffer_type_")
    val bufferType: ResultStatus? = null,
    @Embedded(prefix = "buffer_function_")
    val bufferFunction: ResultStatus? = null,
    @Embedded(prefix = "buffer_safety_switch_")
    val bufferSafetySwitch: ResultStatus? = null
)

/**
 * Detailed inspection results for the Electrical Installation.
 */
@Entity(
    tableName = "electrical_installation_inspection",
    foreignKeys = [ForeignKey(
        entity = InspectionAndTestingEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ElectricalInstallationInspectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @Embedded(prefix = "installation_standard_")
    val installationStandard: ResultStatus? = null,
    @Embedded(prefix = "electrical_panel_")
    val electricalPanel: ResultStatus? = null,
    @Embedded(prefix = "backup_power_ard_")
    val backupPowerARD: ResultStatus? = null,
    @Embedded(prefix = "grounding_cable_")
    val groundingCable: ResultStatus? = null,
    @Embedded(prefix = "fire_alarm_connection_")
    val fireAlarmConnection: ResultStatus? = null,

    // Embedded Fire Service Elevator
    @Embedded(prefix = "fire_service_backup_power_")
    val fireServiceBackupPower: ResultStatus? = null,
    @Embedded(prefix = "fire_service_special_op_")
    val fireServiceSpecialOperation: ResultStatus? = null,
    @Embedded(prefix = "fire_service_switch_")
    val fireServiceFireSwitch: ResultStatus? = null,
    @Embedded(prefix = "fire_service_label_")
    val fireServiceLabel: ResultStatus? = null,
    @Embedded(prefix = "fire_service_electrical_resistance_")
    val fireServiceElectricalFireResistance: ResultStatus? = null,
    @Embedded(prefix = "fire_service_hoistway_wall_resistance_")
    val fireServiceHoistwayWallFireResistance: ResultStatus? = null,
    @Embedded(prefix = "fire_service_car_size_")
    val fireServiceCarSize: ResultStatus? = null,
    @Embedded(prefix = "fire_service_door_size_")
    val fireServiceDoorSize: ResultStatus? = null,
    @Embedded(prefix = "fire_service_travel_time_")
    val fireServiceTravelTime: ResultStatus? = null,
    @Embedded(prefix = "fire_service_evacuation_floor_")
    val fireServiceEvacuationFloor: ResultStatus? = null,

    // Embedded Accessibility Elevator
    @Embedded(prefix = "accessibility_op_panel_")
    val accessibilityOperatingPanel: ResultStatus? = null,
    @Embedded(prefix = "accessibility_panel_height_")
    val accessibilityPanelHeight: ResultStatus? = null,
    @Embedded(prefix = "accessibility_door_open_time_")
    val accessibilityDoorOpenTime: ResultStatus? = null,
    @Embedded(prefix = "accessibility_door_width_")
    val accessibilityDoorWidth: ResultStatus? = null,
    @Embedded(prefix = "accessibility_audio_info_")
    val accessibilityAudioInformation: ResultStatus? = null,
    @Embedded(prefix = "accessibility_label_")
    val accessibilityLabel: ResultStatus? = null,

    // Embedded Seismic Sensor
    @Embedded(prefix = "seismic_availability_")
    val seismicAvailability: ResultStatus? = null,
    @Embedded(prefix = "seismic_function_")
    val seismicFunction: ResultStatus? = null
)


/**
 * =========================================================================================
 * RELATIONAL DATA CLASSES (FOR QUERYING)
 *
 * These classes are not tables. They define how to query and combine the flat
 * entity tables into useful, structured objects for your app's UI or logic layer.
 * Using @Relation tells Room to handle the complex joins for you efficiently.
 * =========================================================================================
 */

/**
 * Represents the full, detailed results of all physical inspections.
 * This class demonstrates how to group related sub-inspections under one parent.
 */
data class FullInspectionAndTesting(
    @Embedded
    val inspectionAndTesting: InspectionAndTestingEntity,

    @Relation(parentColumn = "id", entityColumn = "inspection_id")
    val machineRoomInspection: MachineRoomInspectionEntity?,

    @Relation(parentColumn = "id", entityColumn = "inspection_id")
    val suspensionRopesInspection: SuspensionRopesInspectionEntity?,

    @Relation(parentColumn = "id", entityColumn = "inspection_id")
    val drumsAndSheavesInspection: DrumsAndSheavesInspectionEntity?,

    @Relation(parentColumn = "id", entityColumn = "inspection_id")
    val hoistwayPitInspection: HoistwayPitInspectionEntity?,

    @Relation(parentColumn = "id", entityColumn = "inspection_id")
    val carInspection: CarInspectionEntity?,

    @Relation(parentColumn = "id", entityColumn = "inspection_id")
    val governorAndSafetyBrakeInspection: GovernorAndSafetyBrakeInspectionEntity?,

    @Relation(parentColumn = "id", entityColumn = "inspection_id")
    val counterweightGuideRailsAndBuffersInspection: CounterweightGuideRailsAndBuffersInspectionEntity?,

    @Relation(parentColumn = "id", entityColumn = "inspection_id")
    val electricalInstallationInspection: ElectricalInstallationInspectionEntity?
)

/**
 * This is the ultimate data class for your UI.
 * It fetches a parent Report and ALL its related data in a single, highly-optimized database query.
 * Your DAO would have a method that returns this object.
 */
data class CompleteReport(
    @Embedded
    val report: ReportEntity,

    @Relation(parentColumn = "id", entityColumn = "report_id")
    val generalData: GeneralDataEntity?,

    @Relation(parentColumn = "id", entityColumn = "report_id")
    val technicalDocuments: TechnicalDocumentEntity?,

    @Relation(
        entity = InspectionAndTestingEntity::class,
        parentColumn = "id",
        entityColumn = "report_id"
    )
    val fullInspectionAndTesting: FullInspectionAndTesting?
)
