package com.nakersolutionid.nakersolutionid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

// STEP 1: DEFINE YOUR DATABASE ENTITY AND SUPPORTING DATA CLASSES
// =================================================================

/**
 * The main entity that will be stored in the 'reports' table in your Room database.
 * Note that complex objects like 'generalData' are stored using TypeConverters.
 */
@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey
    val id: String, // As requested, a non-auto-generated String ID

    val nameOfInspectionType: String? = null,
    val subNameOfInspectionType: String? = null,
    val typeInspection: String? = null,
    val eskOrElevType: String? = null,
    val generalData: GeneralData? = null,
    val technicalDocumentInspection: TechnicalDocumentInspection? = null,
    val inspectionAndTesting: InspectionAndTesting? = null,
    val conclusion: String? = null,
    val createdAt: String? = null
)

// --- Supporting Data Classes (These are not tables, they will be converted to JSON) ---

data class ResultStatus(
    val result: String? = null,
    val status: Boolean? = null
)

data class GeneralData(
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

data class TechnicalDocumentInspection(
    val designDrawing: String? = null,
    val technicalCalculation: String? = null,
    val materialCertificate: String? = null,
    val controlPanelDiagram: String? = null,
    val asBuiltDrawing: String? = null,
    val componentCertificates: String? = null,
    val safeWorkProcedure: String? = null
)

data class MachineRoomless(
    val panelPlacement: ResultStatus? = null,
    val lightingWorkArea: ResultStatus? = null,
    val lightingBetweenWorkArea: ResultStatus? = null,
    val manualBrakeRelease: ResultStatus? = null,
    val fireExtinguisherPlacement: ResultStatus? = null
)

data class MachineRoomAndMachinery(
    val machineMounting: ResultStatus? = null,
    val mechanicalBrake: ResultStatus? = null,
    val electricalBrake: ResultStatus? = null,
    val machineRoomConstruction: ResultStatus? = null,
    val machineRoomClearance: ResultStatus? = null,
    val machineRoomImplementation: ResultStatus? = null,
    val ventilation: ResultStatus? = null,
    val machineRoomDoor: ResultStatus? = null,
    val mainPowerPanelPosition: ResultStatus? = null,
    val rotatingPartsGuard: ResultStatus? = null,
    val ropeHoleGuard: ResultStatus? = null,
    val machineRoomAccessLadder: ResultStatus? = null,
    val floorLevelDifference: ResultStatus? = null,
    val fireExtinguisher: ResultStatus? = null,
    val machineRoomless: MachineRoomless? = null,
    val emergencyStopSwitch: ResultStatus? = null
)

data class SuspensionRopesAndBelts(
    val condition: ResultStatus? = null,
    val chainUsage: ResultStatus? = null,
    val safetyFactor: ResultStatus? = null,
    val ropeWithCounterweight: ResultStatus? = null,
    val ropeWithoutCounterweight: ResultStatus? = null,
    val belt: ResultStatus? = null,
    val slackRopeDevice: ResultStatus? = null
)

data class DrumsAndSheaves(
    val drumGrooves: ResultStatus? = null,
    val passengerDrumDiameter: ResultStatus? = null,
    val governorDrumDiameter: ResultStatus? = null
)

data class HoistwayAndPit(
    val construction: ResultStatus? = null,
    val walls: ResultStatus? = null,
    val inclinedElevatorTrackBed: ResultStatus? = null,
    val cleanliness: ResultStatus? = null,
    val lighting: ResultStatus? = null,
    val emergencyDoorNonStop: ResultStatus? = null,
    val emergencyDoorSize: ResultStatus? = null,
    val emergencyDoorSafetySwitch: ResultStatus? = null,
    val emergencyDoorBridge: ResultStatus? = null,
    val carTopClearance: ResultStatus? = null,
    val pitClearance: ResultStatus? = null,
    val pitLadder: ResultStatus? = null,
    val pitBelowWorkingArea: ResultStatus? = null,
    val pitAccessSwitch: ResultStatus? = null,
    val pitScreen: ResultStatus? = null,
    val hoistwayDoorLeaf: ResultStatus? = null,
    val hoistwayDoorInterlock: ResultStatus? = null,
    val floorLeveling: ResultStatus? = null,
    val hoistwaySeparatorBeam: ResultStatus? = null,
    val inclinedElevatorStairs: ResultStatus? = null
)

data class CarDoorSpecs(
    val size: ResultStatus? = null,
    val lockAndSwitch: ResultStatus? = null,
    val sillClearance: ResultStatus? = null
)

data class CarSignage(
    val manufacturerName: ResultStatus? = null,
    val loadCapacity: ResultStatus? = null,
    val noSmokingSign: ResultStatus? = null,
    val overloadIndicator: ResultStatus? = null,
    val doorOpenCloseButtons: ResultStatus? = null,
    val floorButtons: ResultStatus? = null,
    val alarmButton: ResultStatus? = null,
    val twoWayIntercom: ResultStatus? = null
)

data class Car(
    val frame: ResultStatus? = null,
    val body: ResultStatus? = null,
    val wallHeight: ResultStatus? = null,
    val floorArea: ResultStatus? = null,
    val carAreaExpansion: ResultStatus? = null,
    val carDoor: ResultStatus? = null,
    val carDoorSpecs: CarDoorSpecs? = null,
    val carToBeamClearance: ResultStatus? = null,
    val alarmBell: ResultStatus? = null,
    val backupPowerARD: ResultStatus? = null,
    val intercom: ResultStatus? = null,
    val ventilation: ResultStatus? = null,
    val emergencyLighting: ResultStatus? = null,
    val operatingPanel: ResultStatus? = null,
    val carPositionIndicator: ResultStatus? = null,
    val carSignage: CarSignage? = null,
    val carRoofStrength: ResultStatus? = null,
    val carTopEmergencyExit: ResultStatus? = null,
    val carSideEmergencyExit: ResultStatus? = null,
    val carTopGuardRail: ResultStatus? = null,
    val guardRailHeight300to850: ResultStatus? = null,
    val guardRailHeightOver850: ResultStatus? = null,
    val carTopLighting: ResultStatus? = null,
    val manualOperationButtons: ResultStatus? = null,
    val carInterior: ResultStatus? = null
)

data class GovernorAndSafetyBrake(
    val governorRopeClamp: ResultStatus? = null,
    val governorSwitch: ResultStatus? = null,
    val safetyBrakeSpeed: ResultStatus? = null,
    val safetyBrakeType: ResultStatus? = null,
    val safetyBrakeMechanism: ResultStatus? = null,
    val progressiveSafetyBrake: ResultStatus? = null,
    val instantaneousSafetyBrake: ResultStatus? = null,
    val safetyBrakeOperation: ResultStatus? = null,
    val electricalCutoutSwitch: ResultStatus? = null,
    val limitSwitch: ResultStatus? = null,
    val overloadDevice: ResultStatus? = null
)

data class CounterweightGuideRailsAndBuffers(
    val counterweightMaterial: ResultStatus? = null,
    val counterweightGuardScreen: ResultStatus? = null,
    val guideRailConstruction: ResultStatus? = null,
    val bufferType: ResultStatus? = null,
    val bufferFunction: ResultStatus? = null,
    val bufferSafetySwitch: ResultStatus? = null
)

data class FireServiceElevator(
    val backupPower: ResultStatus? = null,
    val specialOperation: ResultStatus? = null,
    val fireSwitch: ResultStatus? = null,
    val label: ResultStatus? = null,
    val electricalFireResistance: ResultStatus? = null,
    val hoistwayWallFireResistance: ResultStatus? = null,
    val carSize: ResultStatus? = null,
    val doorSize: ResultStatus? = null,
    val travelTime: ResultStatus? = null,
    val evacuationFloor: ResultStatus? = null
)

data class AccessibilityElevator(
    val operatingPanel: ResultStatus? = null,
    val panelHeight: ResultStatus? = null,
    val doorOpenTime: ResultStatus? = null,
    val doorWidth: ResultStatus? = null,
    val audioInformation: ResultStatus? = null,
    val label: ResultStatus? = null
)

data class SeismicSensor(
    val availability: ResultStatus? = null,
    val function: ResultStatus? = null
)

data class ElectricalInstallation(
    val installationStandard: ResultStatus? = null,
    val electricalPanel: ResultStatus? = null,
    val backupPowerARD: ResultStatus? = null,
    val groundingCable: ResultStatus? = null,
    val fireAlarmConnection: ResultStatus? = null,
    val fireServiceElevator: FireServiceElevator? = null,
    val accessibilityElevator: AccessibilityElevator? = null,
    val seismicSensor: SeismicSensor? = null
)

data class InspectionAndTesting(
    val machineRoomAndMachinery: MachineRoomAndMachinery? = null,
    val suspensionRopesAndBelts: SuspensionRopesAndBelts? = null,
    val drumsAndSheaves: DrumsAndSheaves? = null,
    val hoistwayAndPit: HoistwayAndPit? = null,
    val car: Car? = null,
    val governorAndSafetyBrake: GovernorAndSafetyBrake? = null,
    val counterweightGuideRailsAndBuffers: CounterweightGuideRailsAndBuffers? = null,
    val electricalInstallation: ElectricalInstallation? = null
)


// STEP 2: CREATE THE TYPE CONVERTER CLASS
// =================================================================

/**
 * This class tells Room how to convert our custom data classes into a format
 * it can store in the database (a String) and how to convert them back.
 * We use Gson for easy serialization/deserialization to/from JSON.
 */
class Converters {
    private val gson = Gson()

    // --- Add a converter for each custom data class used in ReportEntity ---

    @TypeConverter
    fun fromGeneralData(value: GeneralData?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toGeneralData(value: String?): GeneralData? {
        return gson.fromJson(value, GeneralData::class.java)
    }

    @TypeConverter
    fun fromTechnicalDocumentInspection(value: TechnicalDocumentInspection?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTechnicalDocumentInspection(value: String?): TechnicalDocumentInspection? {
        return gson.fromJson(value, TechnicalDocumentInspection::class.java)
    }

    @TypeConverter
    fun fromInspectionAndTesting(value: InspectionAndTesting?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toInspectionAndTesting(value: String?): InspectionAndTesting? {
        return gson.fromJson(value, InspectionAndTesting::class.java)
    }
}


// STEP 3: REGISTER THE CONVERTERS WITH YOUR DATABASE
// =================================================================

/*
In your file where you define your RoomDatabase, add the @TypeConverters annotation.
It should look something like this:

@Database(entities = [ReportEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao
}

*/
