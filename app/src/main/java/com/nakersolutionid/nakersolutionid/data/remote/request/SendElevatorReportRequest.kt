package com.nakersolutionid.nakersolutionid.data.remote.request

// Root Data Class
data class SendElevatorReportRequest(
    val inspectionType: String? = null,
    val subInspectionType: String? = null,
    val examinationType: String? = null,
    val equipmentType: String? = null,
    val generalData: GeneralDataNetwork? = null,
    val technicalDocumentInspection: TechnicalDocumentInspectionNetwork? = null,
    val inspectionAndTesting: InspectionAndTestingNetwork? = null,
    val conclusion: String? = null
)

// GeneralData
data class GeneralDataNetwork(
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

// TechnicalDocumentInspection
data class TechnicalDocumentInspectionNetwork(
    val designDrawing: Boolean? = null,
    val technicalCalculation: Boolean? = null,
    val materialCertificate: Boolean? = null,
    val controlPanelDiagram: Boolean? = null,
    val asBuiltDrawing: Boolean? = null,
    val componentCertificates: Boolean? = null,
    val safeWorkProcedure: Boolean? = null
)

// InspectionAndTesting
data class InspectionAndTestingNetwork(
    val machineRoomAndMachinery: MachineRoomAndMachineryNetwork? = null,
    val suspensionRopesAndBelts: SuspensionRopesAndBeltsNetwork? = null,
    val drumsAndSheaves: DrumsAndSheavesNetwork? = null,
    val hoistwayAndPit: HoistwayAndPitNetwork? = null,
    val car: CarNetwork? = null,
    val governorAndSafetyBrake: GovernorAndSafetyBrakeNetwork? = null,
    val counterweightGuideRailsAndBuffers: CounterweightGuideRailsAndBuffersNetwork? = null,
    val electricalInstallation: ElectricalInstallationNetwork? = null
)

// MachineRoomAndMachinery
data class MachineRoomAndMachineryNetwork(
    val machineMounting: ResultStatusNetwork? = null,
    val mechanicalBrake: ResultStatusNetwork? = null,
    val electricalBrake: ResultStatusNetwork? = null,
    val machineRoomConstruction: ResultStatusNetwork? = null,
    val machineRoomClearance: ResultStatusNetwork? = null,
    val machineRoomImplementation: ResultStatusNetwork? = null,
    val ventilation: ResultStatusNetwork? = null,
    val machineRoomDoor: ResultStatusNetwork? = null,
    val mainPowerPanelPosition: ResultStatusNetwork? = null,
    val rotatingPartsGuard: ResultStatusNetwork? = null,
    val ropeHoleGuard: ResultStatusNetwork? = null,
    val machineRoomAccessLadder: ResultStatusNetwork? = null,
    val floorLevelDifference: ResultStatusNetwork? = null,
    val fireExtinguisher: ResultStatusNetwork? = null,
    val machineRoomless: MachineRoomlessNetwork? = null,
    val emergencyStopSwitch: ResultStatusNetwork? = null
)

// ResultStatus (Generic class for result and status)
data class ResultStatusNetwork(
    val result: String? = null,
    val status: Boolean? = null
)

// MachineRoomless
data class MachineRoomlessNetwork(
    val panelPlacement: ResultStatusNetwork? = null,
    val lightingWorkArea: ResultStatusNetwork? = null,
    val lightingBetweenWorkArea: ResultStatusNetwork? = null,
    val manualBrakeRelease: ResultStatusNetwork? = null,
    val fireExtinguisherPlacement: ResultStatusNetwork? = null
)

// SuspensionRopesAndBelts
data class SuspensionRopesAndBeltsNetwork(
    val condition: ResultStatusNetwork? = null,
    val chainUsage: ResultStatusNetwork? = null,
    val safetyFactor: ResultStatusNetwork? = null,
    val ropeWithCounterweight: ResultStatusNetwork? = null,
    val ropeWithoutCounterweight: ResultStatusNetwork? = null,
    val belt: ResultStatusNetwork? = null,
    val slackRopeDevice: ResultStatusNetwork? = null
)

// DrumsAndSheaves
data class DrumsAndSheavesNetwork(
    val drumGrooves: ResultStatusNetwork? = null,
    val passengerDrumDiameter: ResultStatusNetwork? = null,
    val governorDrumDiameter: ResultStatusNetwork? = null
)

// HoistwayAndPit
data class HoistwayAndPitNetwork(
    val construction: ResultStatusNetwork? = null,
    val walls: ResultStatusNetwork? = null,
    val inclinedElevatorTrackBed: ResultStatusNetwork? = null,
    val cleanliness: ResultStatusNetwork? = null,
    val lighting: ResultStatusNetwork? = null,
    val emergencyDoorNonStop: ResultStatusNetwork? = null,
    val emergencyDoorSize: ResultStatusNetwork? = null,
    val emergencyDoorSafetySwitch: ResultStatusNetwork? = null,
    val emergencyDoorBridge: ResultStatusNetwork? = null,
    val carTopClearance: ResultStatusNetwork? = null,
    val pitClearance: ResultStatusNetwork? = null,
    val pitLadder: ResultStatusNetwork? = null,
    val pitBelowWorkingArea: ResultStatusNetwork? = null,
    val pitAccessSwitch: ResultStatusNetwork? = null,
    val pitScreen: ResultStatusNetwork? = null,
    val hoistwayDoorLeaf: ResultStatusNetwork? = null,
    val hoistwayDoorInterlock: ResultStatusNetwork? = null,
    val floorLeveling: ResultStatusNetwork? = null,
    val hoistwaySeparatorBeam: ResultStatusNetwork? = null,
    val inclinedElevatorStairs: ResultStatusNetwork? = null
)

// Car
data class CarNetwork(
    val frame: ResultStatusNetwork? = null,
    val body: ResultStatusNetwork? = null,
    val wallHeight: ResultStatusNetwork? = null,
    val floorArea: ResultStatusNetwork? = null,
    val carAreaExpansion: ResultStatusNetwork? = null,
    val carDoor: ResultStatusNetwork? = null,
    val carDoorSpecs: CarDoorSpecsNetwork? = null,
    val carToBeamClearance: ResultStatusNetwork? = null,
    val alarmBell: ResultStatusNetwork? = null,
    val backupPowerARD: ResultStatusNetwork? = null,
    val intercom: ResultStatusNetwork? = null,
    val ventilation: ResultStatusNetwork? = null,
    val emergencyLighting: ResultStatusNetwork? = null,
    val operatingPanel: ResultStatusNetwork? = null,
    val carPositionIndicator: ResultStatusNetwork? = null,
    val carSignage: CarSignageNetwork? = null,
    val carRoofStrength: ResultStatusNetwork? = null,
    val carTopEmergencyExit: ResultStatusNetwork? = null,
    val carSideEmergencyExit: ResultStatusNetwork? = null,
    val carTopGuardRail: ResultStatusNetwork? = null,
    val guardRailHeight300to850: ResultStatusNetwork? = null,
    val guardRailHeightOver850: ResultStatusNetwork? = null,
    val carTopLighting: ResultStatusNetwork? = null,
    val manualOperationButtons: ResultStatusNetwork? = null,
    val carInterior: ResultStatusNetwork? = null
)

// CarDoorSpecs
data class CarDoorSpecsNetwork(
    val size: ResultStatusNetwork? = null,
    val lockAndSwitch: ResultStatusNetwork? = null,
    val sillClearance: ResultStatusNetwork? = null
)

// CarSignage
data class CarSignageNetwork(
    val manufacturerName: ResultStatusNetwork? = null,
    val loadCapacity: ResultStatusNetwork? = null,
    val noSmokingSign: ResultStatusNetwork? = null,
    val overloadIndicator: ResultStatusNetwork? = null,
    val doorOpenCloseButtons: ResultStatusNetwork? = null,
    val floorButtons: ResultStatusNetwork? = null,
    val alarmButton: ResultStatusNetwork? = null,
    val twoWayIntercom: ResultStatusNetwork? = null
)

// GovernorAndSafetyBrake
data class GovernorAndSafetyBrakeNetwork(
    val governorRopeClamp: ResultStatusNetwork? = null,
    val governorSwitch: ResultStatusNetwork? = null,
    val safetyBrakeSpeed: ResultStatusNetwork? = null,
    val safetyBrakeType: ResultStatusNetwork? = null,
    val safetyBrakeMechanism: ResultStatusNetwork? = null,
    val progressiveSafetyBrake: ResultStatusNetwork? = null,
    val instantaneousSafetyBrake: ResultStatusNetwork? = null,
    val safetyBrakeOperation: ResultStatusNetwork? = null,
    val electricalCutoutSwitch: ResultStatusNetwork? = null,
    val limitSwitch: ResultStatusNetwork? = null,
    val overloadDevice: ResultStatusNetwork? = null
)

// CounterweightGuideRailsAndBuffers
data class CounterweightGuideRailsAndBuffersNetwork(
    val counterweightMaterial: ResultStatusNetwork? = null,
    val counterweightGuardScreen: ResultStatusNetwork? = null,
    val guideRailConstruction: ResultStatusNetwork? = null,
    val bufferType: ResultStatusNetwork? = null,
    val bufferFunction: ResultStatusNetwork? = null,
    val bufferSafetySwitch: ResultStatusNetwork? = null
)

// ElectricalInstallation
data class ElectricalInstallationNetwork(
    val installationStandard: ResultStatusNetwork? = null,
    val electricalPanel: ResultStatusNetwork? = null,
    val backupPowerARD: ResultStatusNetwork? = null,
    val groundingCable: ResultStatusNetwork? = null,
    val fireAlarmConnection: ResultStatusNetwork? = null,
    val fireServiceElevator: FireServiceElevatorNetwork? = null,
    val accessibilityElevator: AccessibilityElevatorNetwork? = null,
    val seismicSensor: SeismicSensorNetwork? = null
)

// FireServiceElevator
data class FireServiceElevatorNetwork(
    val backupPower: ResultStatusNetwork? = null,
    val specialOperation: ResultStatusNetwork? = null,
    val fireSwitch: ResultStatusNetwork? = null,
    val label: ResultStatusNetwork? = null,
    val electricalFireResistance: ResultStatusNetwork? = null,
    val hoistwayWallFireResistance: ResultStatusNetwork? = null,
    val carSize: ResultStatusNetwork? = null,
    val doorSize: ResultStatusNetwork? = null,
    val travelTime: ResultStatusNetwork? = null,
    val evacuationFloor: ResultStatusNetwork? = null
)

// AccessibilityElevator
data class AccessibilityElevatorNetwork(
    val operatingPanel: ResultStatusNetwork? = null,
    val panelHeight: ResultStatusNetwork? = null,
    val doorOpenTime: ResultStatusNetwork? = null,
    val doorWidth: ResultStatusNetwork? = null,
    val audioInformation: ResultStatusNetwork? = null,
    val label: ResultStatusNetwork? = null
)

// SeismicSensor
data class SeismicSensorNetwork(
    val availability: ResultStatusNetwork? = null,
    val function: ResultStatusNetwork? = null
)