// ElevatorUiState.kt
package com.nakersolutionid.nakersolutionid.features.report.elevator

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

data class ResultStatusUiState(
    val result: String? = null,
    val status: Boolean = false
)

data class GeneralDataUiState(
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

data class TechnicalDocumentInspectionUiState(
    val designDrawing: Boolean? = null,
    val technicalCalculation: Boolean? = null,
    val materialCertificate: Boolean? = null,
    val controlPanelDiagram: Boolean? = null,
    val asBuiltDrawing: Boolean? = null,
    val componentCertificates: Boolean? = null,
    val safeWorkProcedure: Boolean? = null
)

data class MachineRoomlessUiState(
    val panelPlacement: ResultStatusUiState? = null,
    val lightingWorkArea: ResultStatusUiState? = null,
    val lightingBetweenWorkArea: ResultStatusUiState? = null,
    val manualBrakeRelease: ResultStatusUiState? = null,
    val fireExtinguisherPlacement: ResultStatusUiState? = null
)

data class MachineRoomAndMachineryUiState(
    val machineMounting: ResultStatusUiState? = null,
    val mechanicalBrake: ResultStatusUiState? = null,
    val electricalBrake: ResultStatusUiState? = null,
    val machineRoomConstruction: ResultStatusUiState? = null,
    val machineRoomClearance: ResultStatusUiState? = null,
    val machineRoomImplementation: ResultStatusUiState? = null,
    val ventilation: ResultStatusUiState? = null,
    val machineRoomDoor: ResultStatusUiState? = null,
    val mainPowerPanelPosition: ResultStatusUiState? = null,
    val rotatingPartsGuard: ResultStatusUiState? = null,
    val ropeHoleGuard: ResultStatusUiState? = null,
    val machineRoomAccessLadder: ResultStatusUiState? = null,
    val floorLevelDifference: ResultStatusUiState? = null,
    val fireExtinguisher: ResultStatusUiState? = null,
    val machineRoomless: MachineRoomlessUiState? = null,
    val emergencyStopSwitch: ResultStatusUiState? = null
)

data class SuspensionRopesAndBeltsUiState(
    val condition: ResultStatusUiState? = null,
    val chainUsage: ResultStatusUiState? = null,
    val safetyFactor: ResultStatusUiState? = null,
    val ropeWithCounterweight: ResultStatusUiState? = null,
    val ropeWithoutCounterweight: ResultStatusUiState? = null,
    val belt: ResultStatusUiState? = null,
    val slackRopeDevice: ResultStatusUiState? = null
)

data class DrumsAndSheavesUiState(
    val drumGrooves: ResultStatusUiState? = null,
    val passengerDrumDiameter: ResultStatusUiState? = null,
    val governorDrumDiameter: ResultStatusUiState? = null
)

data class HoistwayAndPitUiState(
    val construction: ResultStatusUiState? = null,
    val walls: ResultStatusUiState? = null,
    val inclinedElevatorTrackBed: ResultStatusUiState? = null,
    val cleanliness: ResultStatusUiState? = null,
    val lighting: ResultStatusUiState? = null,
    val emergencyDoorNonStop: ResultStatusUiState? = null,
    val emergencyDoorSize: ResultStatusUiState? = null,
    val emergencyDoorSafetySwitch: ResultStatusUiState? = null,
    val emergencyDoorBridge: ResultStatusUiState? = null,
    val carTopClearance: ResultStatusUiState? = null,
    val pitClearance: ResultStatusUiState? = null,
    val pitLadder: ResultStatusUiState? = null,
    val pitBelowWorkingArea: ResultStatusUiState? = null,
    val pitAccessSwitch: ResultStatusUiState? = null,
    val pitScreen: ResultStatusUiState? = null,
    val hoistwayDoorLeaf: ResultStatusUiState? = null,
    val hoistwayDoorInterlock: ResultStatusUiState? = null,
    val floorLeveling: ResultStatusUiState? = null,
    val hoistwaySeparatorBeam: ResultStatusUiState? = null,
    val inclinedElevatorStairs: ResultStatusUiState? = null
)

data class CarDoorSpecsUiState(
    val size: ResultStatusUiState? = null,
    val lockAndSwitch: ResultStatusUiState? = null,
    val sillClearance: ResultStatusUiState? = null
)

data class CarSignageUiState(
    val manufacturerName: ResultStatusUiState? = null,
    val loadCapacity: ResultStatusUiState? = null,
    val noSmokingSign: ResultStatusUiState? = null,
    val overloadIndicator: ResultStatusUiState? = null,
    val doorOpenCloseButtons: ResultStatusUiState? = null,
    val floorButtons: ResultStatusUiState? = null,
    val alarmButton: ResultStatusUiState? = null,
    val twoWayIntercom: ResultStatusUiState? = null
)

data class CarUiState(
    val frame: ResultStatusUiState? = null,
    val body: ResultStatusUiState? = null,
    val wallHeight: ResultStatusUiState? = null,
    val floorArea: ResultStatusUiState? = null,
    val carAreaExpansion: ResultStatusUiState? = null,
    val carDoor: ResultStatusUiState? = null,
    val carDoorSpecs: CarDoorSpecsUiState? = null,
    val carToBeamClearance: ResultStatusUiState? = null,
    val alarmBell: ResultStatusUiState? = null,
    val backupPowerARD: ResultStatusUiState? = null,
    val intercom: ResultStatusUiState? = null,
    val ventilation: ResultStatusUiState? = null,
    val emergencyLighting: ResultStatusUiState? = null,
    val operatingPanel: ResultStatusUiState? = null,
    val carPositionIndicator: ResultStatusUiState? = null,
    val carSignage: CarSignageUiState? = null,
    val carRoofStrength: ResultStatusUiState? = null,
    val carTopEmergencyExit: ResultStatusUiState? = null,
    val carSideEmergencyExit: ResultStatusUiState? = null,
    val carTopGuardRail: ResultStatusUiState? = null,
    val guardRailHeight300to850: ResultStatusUiState? = null,
    val guardRailHeightOver850: ResultStatusUiState? = null,
    val carTopLighting: ResultStatusUiState? = null,
    val manualOperationButtons: ResultStatusUiState? = null,
    val carInterior: ResultStatusUiState? = null
)

data class GovernorAndSafetyBrakeUiState(
    val governorRopeClamp: ResultStatusUiState? = null,
    val governorSwitch: ResultStatusUiState? = null,
    val safetyBrakeSpeed: ResultStatusUiState? = null,
    val safetyBrakeType: ResultStatusUiState? = null,
    val safetyBrakeMechanism: ResultStatusUiState? = null,
    val progressiveSafetyBrake: ResultStatusUiState? = null,
    val instantaneousSafetyBrake: ResultStatusUiState? = null,
    val safetyBrakeOperation: ResultStatusUiState? = null,
    val electricalCutoutSwitch: ResultStatusUiState? = null,
    val limitSwitch: ResultStatusUiState? = null,
    val overloadDevice: ResultStatusUiState? = null
)

data class CounterweightGuideRailsAndBuffersUiState(
    val counterweightMaterial: ResultStatusUiState? = null,
    val counterweightGuardScreen: ResultStatusUiState? = null,
    val guideRailConstruction: ResultStatusUiState? = null,
    val bufferType: ResultStatusUiState? = null,
    val bufferFunction: ResultStatusUiState? = null,
    val bufferSafetySwitch: ResultStatusUiState? = null
)

data class FireServiceElevatorUiState(
    val backupPower: ResultStatusUiState? = null,
    val specialOperation: ResultStatusUiState? = null,
    val fireSwitch: ResultStatusUiState? = null,
    val label: ResultStatusUiState? = null,
    val electricalFireResistance: ResultStatusUiState? = null,
    val hoistwayWallFireResistance: ResultStatusUiState? = null,
    val carSize: ResultStatusUiState? = null,
    val doorSize: ResultStatusUiState? = null,
    val travelTime: ResultStatusUiState? = null,
    val evacuationFloor: ResultStatusUiState? = null
)

data class AccessibilityElevatorUiState(
    val operatingPanel: ResultStatusUiState? = null,
    val panelHeight: ResultStatusUiState? = null,
    val doorOpenTime: ResultStatusUiState? = null,
    val doorWidth: ResultStatusUiState? = null,
    val audioInformation: ResultStatusUiState? = null,
    val label: ResultStatusUiState? = null
)

data class SeismicSensorUiState(
    val availability: ResultStatusUiState? = null,
    val function: ResultStatusUiState? = null
)

data class ElectricalInstallationUiState(
    val installationStandard: ResultStatusUiState? = null,
    val electricalPanel: ResultStatusUiState? = null,
    val backupPowerARD: ResultStatusUiState? = null,
    val groundingCable: ResultStatusUiState? = null,
    val fireAlarmConnection: ResultStatusUiState? = null,
    val fireServiceElevator: FireServiceElevatorUiState? = null,
    val accessibilityElevator: AccessibilityElevatorUiState? = null,
    val seismicSensor: SeismicSensorUiState? = null
)

data class InspectionAndTestingUiState(
    val machineRoomAndMachinery: MachineRoomAndMachineryUiState? = null,
    val suspensionRopesAndBelts: SuspensionRopesAndBeltsUiState? = null,
    val drumsAndSheaves: DrumsAndSheavesUiState? = null,
    val hoistwayAndPit: HoistwayAndPitUiState? = null,
    val car: CarUiState? = null,
    val governorAndSafetyBrake: GovernorAndSafetyBrakeUiState? = null,
    val counterweightGuideRailsAndBuffers: CounterweightGuideRailsAndBuffersUiState? = null,
    val electricalInstallation: ElectricalInstallationUiState? = null
)

data class ElevatorUiState(
    val id: Long = 0,
    val documentType: DocumentType = DocumentType.LAPORAN,
    val nameOfInspectionType: InspectionType = InspectionType.EE,
    val subNameOfInspectionType: SubInspectionType = SubInspectionType.Elevator,
    val typeInspection: String? = null,
    val eskOrElevType: String? = null,
    val generalData: GeneralDataUiState? = null,
    val technicalDocumentInspection: TechnicalDocumentInspectionUiState? = null,
    val inspectionAndTesting: InspectionAndTestingUiState? = null,
    val conclusion: String? = null,
    val sendReportResult: Resource<String>? = null,
    val createdAt: String? = null,
    val isLoading: Boolean = false
)