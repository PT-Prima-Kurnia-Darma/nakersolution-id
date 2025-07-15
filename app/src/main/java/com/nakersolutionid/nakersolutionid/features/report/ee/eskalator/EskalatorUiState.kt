package com.nakersolutionid.nakersolutionid.features.report.ee.eskalator

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

@Immutable
data class EskalatorUiState(
    val isLoading: Boolean = false,
    val eskalatorData: EskalatorGeneralData = EskalatorGeneralData()
)

@Immutable
data class EskalatorGeneralData(
    val id: Long = 0,
    val documentType: DocumentType = DocumentType.LAPORAN,
    val inspectionType: InspectionType = InspectionType.EE,
    val subInspectionType: SubInspectionType = SubInspectionType.Escalator,
    val equipmentType: String = "",
    val examinationType: String = "",
    val conclusion: String = "",
    val companyData: EskalatorCompanyData = EskalatorCompanyData(),
    val technicalData: EskalatorTechnicalData = EskalatorTechnicalData(),
    val inspectionAndTesting: EskalatorInspectionAndTesting = EskalatorInspectionAndTesting(),
    val testingSummary: EskalatorTestingSummary = EskalatorTestingSummary()
)

@Immutable
data class EskalatorCompanyData(
    val companyOrBuildingName: String = "",
    val usageAddress: String = "",
    val safetyObjectTypeAndNumber: String = "",
    val intendedUse: String = "",
    val usagePermit: String = "",
    val inspectionDate: String = ""
)

@Immutable
data class EskalatorTechnicalData(
    val manufacturer: String = "",
    val brand: String = "",
    val countryAndYear: String = "",
    val serialNumber: String = "",
    val transports: String = "",
    val capacity: String = "",
    val liftHeight: String = "",
    val speed: String = "",
    val driveType: String = "",
    val motorCurrent: String = "",
    val motorPower: String = "",
    val safetyDevices: String = ""
)

@Immutable
data class EskalatorInspectionAndTesting(
    val frameAndMachineRoom: FrameAndMachineRoom = FrameAndMachineRoom(),
    val driveEquipment: DriveEquipment = DriveEquipment(),
    val stepsOrPallets: StepsOrPallets = StepsOrPallets(),
    val landingArea: LandingArea = LandingArea(),
    val balustrade: Balustrade = Balustrade(),
    val handrail: Handrail = Handrail(),
    val runway: Runway = Runway(),
    val safetyEquipment: SafetyEquipment = SafetyEquipment(),
    val electricalInstallation: ElectricalInstallation = ElectricalInstallation(),
    val outdoorSpecifics: OutdoorSpecifics = OutdoorSpecifics(),
    val userSafetySignage: UserSafetySignage = UserSafetySignage()
)

/**
 * Data class for inspection items related to the frame and machine room.
 */
@Immutable
data class FrameAndMachineRoom(
    val frame: EskalatorResultStatus = EskalatorResultStatus(),
    val supportBeams: EskalatorResultStatus = EskalatorResultStatus(),
    val machineRoomCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val machineRoomClearance: EskalatorResultStatus = EskalatorResultStatus(),
    val machineRoomLighting: EskalatorResultStatus = EskalatorResultStatus(),
    val machineCoverPlate: EskalatorResultStatus = EskalatorResultStatus(),
    val pitCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val pitClearance: EskalatorResultStatus = EskalatorResultStatus(),
    val pitStepCoverPlate: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to drive equipment.
 */
@Immutable
data class DriveEquipment(
    val driveMachine: EskalatorResultStatus = EskalatorResultStatus(),
    val speedUnder30Degrees: EskalatorResultStatus = EskalatorResultStatus(),
    val speed30to35Degrees: EskalatorResultStatus = EskalatorResultStatus(),
    val travelatorSpeed: EskalatorResultStatus = EskalatorResultStatus(),
    val stoppingDistance0_5: EskalatorResultStatus = EskalatorResultStatus(),
    val stoppingDistance0_75: EskalatorResultStatus = EskalatorResultStatus(),
    val stoppingDistance0_90: EskalatorResultStatus = EskalatorResultStatus(),
    val driveChain: EskalatorResultStatus = EskalatorResultStatus(),
    val chainBreakingStrength: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to steps or pallets.
 */
@Immutable
data class StepsOrPallets(
    val stepMaterial: EskalatorResultStatus = EskalatorResultStatus(),
    val stepDimensions: EskalatorResultStatus = EskalatorResultStatus(),
    val palletDimensions: EskalatorResultStatus = EskalatorResultStatus(),
    val stepSurface: EskalatorResultStatus = EskalatorResultStatus(),
    val stepLevelness: EskalatorResultStatus = EskalatorResultStatus(),
    val skirtBrush: EskalatorResultStatus = EskalatorResultStatus(),
    val stepWheels: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to the landing area.
 */
@Immutable
data class LandingArea(
    val landingPlates: EskalatorResultStatus = EskalatorResultStatus(),
    val combTeeth: EskalatorResultStatus = EskalatorResultStatus(),
    val combCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val landingCover: EskalatorResultStatus = EskalatorResultStatus(),
    val landingAccessArea: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to the balustrade.
 */
@Immutable
data class Balustrade(
    val balustradePanel: BalustradePanel = BalustradePanel(),
    val skirtPanel: EskalatorResultStatus = EskalatorResultStatus(),
    val skirtPanelFlexibility: EskalatorResultStatus = EskalatorResultStatus(),
    val stepToSkirtClearance: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Nested data class for balustrade panel details.
 */
data class BalustradePanel(
    val material: EskalatorResultStatus = EskalatorResultStatus(),
    val height: EskalatorResultStatus = EskalatorResultStatus(),
    val sidePressure: EskalatorResultStatus = EskalatorResultStatus(),
    val verticalPressure: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to the handrail.
 */
@Immutable
data class Handrail(
    val handrailCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val handrailSpeedSynchronization: EskalatorResultStatus = EskalatorResultStatus(),
    val handrailWidth: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to the runway.
 */
@Immutable
data class Runway(
    val beamStrengthAndPosition: EskalatorResultStatus = EskalatorResultStatus(),
    val pitWallCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val escalatorFrameEnclosure: EskalatorResultStatus = EskalatorResultStatus(),
    val lighting: EskalatorResultStatus = EskalatorResultStatus(),
    val headroomClearance: EskalatorResultStatus = EskalatorResultStatus(),
    val balustradeToObjectClearance: EskalatorResultStatus = EskalatorResultStatus(),
    val antiClimbDeviceHeight: EskalatorResultStatus = EskalatorResultStatus(),
    val ornamentPlacement: EskalatorResultStatus = EskalatorResultStatus(),
    val outdoorClearance: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to safety equipment.
 */
@Immutable
data class SafetyEquipment(
    val operationControlKey: EskalatorResultStatus = EskalatorResultStatus(),
    val emergencyStopSwitch: EskalatorResultStatus = EskalatorResultStatus(),
    val stepChainSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val driveChainSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val stepSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val handrailSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val reversalStopDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val handrailEntryGuard: EskalatorResultStatus = EskalatorResultStatus(),
    val combPlateSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val innerDeckingBrush: EskalatorResultStatus = EskalatorResultStatus(),
    val stopButtons: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to electrical installation.
 */
@Immutable
data class ElectricalInstallation(
    val installationStandard: EskalatorResultStatus = EskalatorResultStatus(),
    val electricalPanel: EskalatorResultStatus = EskalatorResultStatus(),
    val groundingCable: EskalatorResultStatus = EskalatorResultStatus(),
    val fireAlarmConnection: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items specific to outdoor installations.
 */
@Immutable
data class OutdoorSpecifics(
    val pitWaterPump: EskalatorResultStatus = EskalatorResultStatus(),
    val weatherproofComponents: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for user safety signage inspection items.
 */
@Immutable
data class UserSafetySignage(
    val noBulkyItems: EskalatorResultStatus = EskalatorResultStatus(),
    val noJumping: EskalatorResultStatus = EskalatorResultStatus(),
    val unattendedChildren: EskalatorResultStatus = EskalatorResultStatus(),
    val noTrolleysOrStrollers: EskalatorResultStatus = EskalatorResultStatus(),
    val noLeaning: EskalatorResultStatus = EskalatorResultStatus(),
    val noSteppingOnSkirt: EskalatorResultStatus = EskalatorResultStatus(),
    val softSoleFootwearWarning: EskalatorResultStatus = EskalatorResultStatus(),
    val noSittingOnSteps: EskalatorResultStatus = EskalatorResultStatus(),
    val holdHandrail: EskalatorResultStatus = EskalatorResultStatus()
)

@Immutable
data class EskalatorTestingSummary(
    val safetyDevices: String = "",
    val noLoadTest: String = "",
    val brakeTest: String = ""
)

@Immutable
data class EskalatorResultStatus(
    val result: String = "",
    val status: Boolean = false
)