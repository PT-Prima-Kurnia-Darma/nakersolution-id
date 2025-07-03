package com.nakersolutionid.nakersolutionid.domain.model

data class ResultStatusDomain(
    val result: String? = null,
    val status: Boolean? = null
)

data class GeneralDataDomain(
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

data class TechnicalDocumentInspectionDomain(
    val designDrawing: String? = null,
    val technicalCalculation: String? = null,
    val materialCertificate: String? = null,
    val controlPanelDiagram: String? = null,
    val asBuiltDrawing: String? = null,
    val componentCertificates: String? = null,
    val safeWorkProcedure: String? = null
)

data class MachineRoomlessDomain(
    val panelPlacement: ResultStatusDomain? = null,
    val lightingWorkArea: ResultStatusDomain? = null,
    val lightingBetweenWorkArea: ResultStatusDomain? = null,
    val manualBrakeRelease: ResultStatusDomain? = null,
    val fireExtinguisherPlacement: ResultStatusDomain? = null
)

data class MachineRoomAndMachineryDomain(
    val machineMounting: ResultStatusDomain? = null,
    val mechanicalBrake: ResultStatusDomain? = null,
    val electricalBrake: ResultStatusDomain? = null,
    val machineRoomConstruction: ResultStatusDomain? = null,
    val machineRoomClearance: ResultStatusDomain? = null,
    val machineRoomImplementation: ResultStatusDomain? = null,
    val ventilation: ResultStatusDomain? = null,
    val machineRoomDoor: ResultStatusDomain? = null,
    val mainPowerPanelPosition: ResultStatusDomain? = null,
    val rotatingPartsGuard: ResultStatusDomain? = null,
    val ropeHoleGuard: ResultStatusDomain? = null,
    val machineRoomAccessLadder: ResultStatusDomain? = null,
    val floorLevelDifference: ResultStatusDomain? = null,
    val fireExtinguisher: ResultStatusDomain? = null,
    val machineRoomless: MachineRoomlessDomain? = null,
    val emergencyStopSwitch: ResultStatusDomain? = null
)

data class SuspensionRopesAndBeltsDomain(
    val condition: ResultStatusDomain? = null,
    val chainUsage: ResultStatusDomain? = null,
    val safetyFactor: ResultStatusDomain? = null,
    val ropeWithCounterweight: ResultStatusDomain? = null,
    val ropeWithoutCounterweight: ResultStatusDomain? = null,
    val belt: ResultStatusDomain? = null,
    val slackRopeDevice: ResultStatusDomain? = null
)

data class DrumsAndSheavesDomain(
    val drumGrooves: ResultStatusDomain? = null,
    val passengerDrumDiameter: ResultStatusDomain? = null,
    val governorDrumDiameter: ResultStatusDomain? = null
)

data class HoistwayAndPitDomain(
    val construction: ResultStatusDomain? = null,
    val walls: ResultStatusDomain? = null,
    val inclinedElevatorTrackBed: ResultStatusDomain? = null,
    val cleanliness: ResultStatusDomain? = null,
    val lighting: ResultStatusDomain? = null,
    val emergencyDoorNonStop: ResultStatusDomain? = null,
    val emergencyDoorSize: ResultStatusDomain? = null,
    val emergencyDoorSafetySwitch: ResultStatusDomain? = null,
    val emergencyDoorBridge: ResultStatusDomain? = null,
    val carTopClearance: ResultStatusDomain? = null,
    val pitClearance: ResultStatusDomain? = null,
    val pitLadder: ResultStatusDomain? = null,
    val pitBelowWorkingArea: ResultStatusDomain? = null,
    val pitAccessSwitch: ResultStatusDomain? = null,
    val pitScreen: ResultStatusDomain? = null,
    val hoistwayDoorLeaf: ResultStatusDomain? = null,
    val hoistwayDoorInterlock: ResultStatusDomain? = null,
    val floorLeveling: ResultStatusDomain? = null,
    val hoistwaySeparatorBeam: ResultStatusDomain? = null,
    val inclinedElevatorStairs: ResultStatusDomain? = null
)

data class CarDoorSpecsDomain(
    val size: ResultStatusDomain? = null,
    val lockAndSwitch: ResultStatusDomain? = null,
    val sillClearance: ResultStatusDomain? = null
)

data class CarSignageDomain(
    val manufacturerName: ResultStatusDomain? = null,
    val loadCapacity: ResultStatusDomain? = null,
    val noSmokingSign: ResultStatusDomain? = null,
    val overloadIndicator: ResultStatusDomain? = null,
    val doorOpenCloseButtons: ResultStatusDomain? = null,
    val floorButtons: ResultStatusDomain? = null,
    val alarmButton: ResultStatusDomain? = null,
    val twoWayIntercom: ResultStatusDomain? = null
)

data class CarDomain(
    val frame: ResultStatusDomain? = null,
    val body: ResultStatusDomain? = null,
    val wallHeight: ResultStatusDomain? = null,
    val floorArea: ResultStatusDomain? = null,
    val carAreaExpansion: ResultStatusDomain? = null,
    val carDoor: ResultStatusDomain? = null,
    val carDoorSpecs: CarDoorSpecsDomain? = null,
    val carToBeamClearance: ResultStatusDomain? = null,
    val alarmBell: ResultStatusDomain? = null,
    val backupPowerARD: ResultStatusDomain? = null,
    val intercom: ResultStatusDomain? = null,
    val ventilation: ResultStatusDomain? = null,
    val emergencyLighting: ResultStatusDomain? = null,
    val operatingPanel: ResultStatusDomain? = null,
    val carPositionIndicator: ResultStatusDomain? = null,
    val carSignage: CarSignageDomain? = null,
    val carRoofStrength: ResultStatusDomain? = null,
    val carTopEmergencyExit: ResultStatusDomain? = null,
    val carSideEmergencyExit: ResultStatusDomain? = null,
    val carTopGuardRail: ResultStatusDomain? = null,
    val guardRailHeight300to850: ResultStatusDomain? = null,
    val guardRailHeightOver850: ResultStatusDomain? = null,
    val carTopLighting: ResultStatusDomain? = null,
    val manualOperationButtons: ResultStatusDomain? = null,
    val carInterior: ResultStatusDomain? = null
)

data class GovernorAndSafetyBrakeDomain(
    val governorRopeClamp: ResultStatusDomain? = null,
    val governorSwitch: ResultStatusDomain? = null,
    val safetyBrakeSpeed: ResultStatusDomain? = null,
    val safetyBrakeType: ResultStatusDomain? = null,
    val safetyBrakeMechanism: ResultStatusDomain? = null,
    val progressiveSafetyBrake: ResultStatusDomain? = null,
    val instantaneousSafetyBrake: ResultStatusDomain? = null,
    val safetyBrakeOperation: ResultStatusDomain? = null,
    val electricalCutoutSwitch: ResultStatusDomain? = null,
    val limitSwitch: ResultStatusDomain? = null,
    val overloadDevice: ResultStatusDomain? = null
)

data class CounterweightGuideRailsAndBuffersDomain(
    val counterweightMaterial: ResultStatusDomain? = null,
    val counterweightGuardScreen: ResultStatusDomain? = null,
    val guideRailConstruction: ResultStatusDomain? = null,
    val bufferType: ResultStatusDomain? = null,
    val bufferFunction: ResultStatusDomain? = null,
    val bufferSafetySwitch: ResultStatusDomain? = null
)

data class FireServiceElevatorDomain(
    val backupPower: ResultStatusDomain? = null,
    val specialOperation: ResultStatusDomain? = null,
    val fireSwitch: ResultStatusDomain? = null,
    val label: ResultStatusDomain? = null,
    val electricalFireResistance: ResultStatusDomain? = null,
    val hoistwayWallFireResistance: ResultStatusDomain? = null,
    val carSize: ResultStatusDomain? = null,
    val doorSize: ResultStatusDomain? = null,
    val travelTime: ResultStatusDomain? = null,
    val evacuationFloor: ResultStatusDomain? = null
)

data class AccessibilityElevatorDomain(
    val operatingPanel: ResultStatusDomain? = null,
    val panelHeight: ResultStatusDomain? = null,
    val doorOpenTime: ResultStatusDomain? = null,
    val doorWidth: ResultStatusDomain? = null,
    val audioInformation: ResultStatusDomain? = null,
    val label: ResultStatusDomain? = null
)

data class SeismicSensorDomain(
    val availability: ResultStatusDomain? = null,
    val function: ResultStatusDomain? = null
)

data class ElectricalInstallationDomain(
    val installationStandard: ResultStatusDomain? = null,
    val electricalPanel: ResultStatusDomain? = null,
    val backupPowerARD: ResultStatusDomain? = null,
    val groundingCable: ResultStatusDomain? = null,
    val fireAlarmConnection: ResultStatusDomain? = null,
    val fireServiceElevator: FireServiceElevatorDomain? = null,
    val accessibilityElevator: AccessibilityElevatorDomain? = null,
    val seismicSensor: SeismicSensorDomain? = null
)

data class InspectionAndTestingDomain(
    val machineRoomAndMachinery: MachineRoomAndMachineryDomain? = null,
    val suspensionRopesAndBelts: SuspensionRopesAndBeltsDomain? = null,
    val drumsAndSheaves: DrumsAndSheavesDomain? = null,
    val hoistwayAndPit: HoistwayAndPitDomain? = null,
    val car: CarDomain? = null,
    val governorAndSafetyBrake: GovernorAndSafetyBrakeDomain? = null,
    val counterweightGuideRailsAndBuffers: CounterweightGuideRailsAndBuffersDomain? = null,
    val electricalInstallation: ElectricalInstallationDomain? = null
)

data class Report(
    val nameOfInspectionType: String? = null,
    val subNameOfInspectionType: String? = null,
    val typeInspection: String? = null,
    val eskOrElevType: String? = null,
    val generalData: GeneralDataDomain? = null,
    val technicalDocumentInspection: TechnicalDocumentInspectionDomain? = null,
    val inspectionAndTesting: InspectionAndTestingDomain? = null,
    val conclusion: String? = null
)