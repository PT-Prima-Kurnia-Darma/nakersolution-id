// ElevatorUiState.kt
package com.nakersolutionid.nakersolutionid.features.report.ee.elevator

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

@Immutable
data class ResultStatusUiState(
    val result: String = "",
    val status: Boolean = false
)

@Immutable
data class GeneralDataUiState(
    val ownerName: String = "",
    val ownerAddress: String = "",
    val nameUsageLocation: String = "",
    val addressUsageLocation: String = "",
    val manufacturerOrInstaller: String = "",
    val elevatorType: String = "",
    val brandOrType: String = "",
    val countryAndYear: String = "",
    val serialNumber: String = "",
    val capacity: String = "",
    val speed: String = "",
    val floorsServed: String = "",
    val permitNumber: String = "",
    val inspectionDate: String = ""
)

@Immutable
data class TechnicalDocumentInspectionUiState(
    val designDrawing: Boolean = false,
    val technicalCalculation: Boolean = false,
    val materialCertificate: Boolean = false,
    val controlPanelDiagram: Boolean = false,
    val asBuiltDrawing: Boolean = false,
    val componentCertificates: Boolean = false,
    val safeWorkProcedure: Boolean = false
)

@Immutable
data class MachineRoomlessUiState(
    val panelPlacement: ResultStatusUiState = ResultStatusUiState(),
    val lightingWorkArea: ResultStatusUiState = ResultStatusUiState(),
    val lightingBetweenWorkArea: ResultStatusUiState = ResultStatusUiState(),
    val manualBrakeRelease: ResultStatusUiState = ResultStatusUiState(),
    val fireExtinguisherPlacement: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class MachineRoomAndMachineryUiState(
    val machineMounting: ResultStatusUiState = ResultStatusUiState(),
    val mechanicalBrake: ResultStatusUiState = ResultStatusUiState(),
    val electricalBrake: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomConstruction: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomClearance: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomImplementation: ResultStatusUiState = ResultStatusUiState(),
    val ventilation: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomDoor: ResultStatusUiState = ResultStatusUiState(),
    val mainPowerPanelPosition: ResultStatusUiState = ResultStatusUiState(),
    val rotatingPartsGuard: ResultStatusUiState = ResultStatusUiState(),
    val ropeHoleGuard: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomAccessLadder: ResultStatusUiState = ResultStatusUiState(),
    val floorLevelDifference: ResultStatusUiState = ResultStatusUiState(),
    val fireExtinguisher: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomless: MachineRoomlessUiState = MachineRoomlessUiState(),
    val emergencyStopSwitch: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class SuspensionRopesAndBeltsUiState(
    val condition: ResultStatusUiState = ResultStatusUiState(),
    val chainUsage: ResultStatusUiState = ResultStatusUiState(),
    val safetyFactor: ResultStatusUiState = ResultStatusUiState(),
    val ropeWithCounterweight: ResultStatusUiState = ResultStatusUiState(),
    val ropeWithoutCounterweight: ResultStatusUiState = ResultStatusUiState(),
    val belt: ResultStatusUiState = ResultStatusUiState(),
    val slackRopeDevice: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class DrumsAndSheavesUiState(
    val drumGrooves: ResultStatusUiState = ResultStatusUiState(),
    val passengerDrumDiameter: ResultStatusUiState = ResultStatusUiState(),
    val governorDrumDiameter: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class HoistwayAndPitUiState(
    val construction: ResultStatusUiState = ResultStatusUiState(),
    val walls: ResultStatusUiState = ResultStatusUiState(),
    val inclinedElevatorTrackBed: ResultStatusUiState = ResultStatusUiState(),
    val cleanliness: ResultStatusUiState = ResultStatusUiState(),
    val lighting: ResultStatusUiState = ResultStatusUiState(),
    val emergencyDoorNonStop: ResultStatusUiState = ResultStatusUiState(),
    val emergencyDoorSize: ResultStatusUiState = ResultStatusUiState(),
    val emergencyDoorSafetySwitch: ResultStatusUiState = ResultStatusUiState(),
    val emergencyDoorBridge: ResultStatusUiState = ResultStatusUiState(),
    val carTopClearance: ResultStatusUiState = ResultStatusUiState(),
    val pitClearance: ResultStatusUiState = ResultStatusUiState(),
    val pitLadder: ResultStatusUiState = ResultStatusUiState(),
    val pitBelowWorkingArea: ResultStatusUiState = ResultStatusUiState(),
    val pitAccessSwitch: ResultStatusUiState = ResultStatusUiState(),
    val pitScreen: ResultStatusUiState = ResultStatusUiState(),
    val hoistwayDoorLeaf: ResultStatusUiState = ResultStatusUiState(),
    val hoistwayDoorInterlock: ResultStatusUiState = ResultStatusUiState(),
    val floorLeveling: ResultStatusUiState = ResultStatusUiState(),
    val hoistwaySeparatorBeam: ResultStatusUiState = ResultStatusUiState(),
    val inclinedElevatorStairs: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class CarDoorSpecsUiState(
    val size: ResultStatusUiState = ResultStatusUiState(),
    val lockAndSwitch: ResultStatusUiState = ResultStatusUiState(),
    val sillClearance: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class CarSignageUiState(
    val manufacturerName: ResultStatusUiState = ResultStatusUiState(),
    val loadCapacity: ResultStatusUiState = ResultStatusUiState(),
    val noSmokingSign: ResultStatusUiState = ResultStatusUiState(),
    val overloadIndicator: ResultStatusUiState = ResultStatusUiState(),
    val doorOpenCloseButtons: ResultStatusUiState = ResultStatusUiState(),
    val floorButtons: ResultStatusUiState = ResultStatusUiState(),
    val alarmButton: ResultStatusUiState = ResultStatusUiState(),
    val twoWayIntercom: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class CarUiState(
    val frame: ResultStatusUiState = ResultStatusUiState(),
    val body: ResultStatusUiState = ResultStatusUiState(),
    val wallHeight: ResultStatusUiState = ResultStatusUiState(),
    val floorArea: ResultStatusUiState = ResultStatusUiState(),
    val carAreaExpansion: ResultStatusUiState = ResultStatusUiState(),
    val carDoor: ResultStatusUiState = ResultStatusUiState(),
    val carDoorSpecs: CarDoorSpecsUiState = CarDoorSpecsUiState(),
    val carToBeamClearance: ResultStatusUiState = ResultStatusUiState(),
    val alarmBell: ResultStatusUiState = ResultStatusUiState(),
    val backupPowerARD: ResultStatusUiState = ResultStatusUiState(),
    val intercom: ResultStatusUiState = ResultStatusUiState(),
    val ventilation: ResultStatusUiState = ResultStatusUiState(),
    val emergencyLighting: ResultStatusUiState = ResultStatusUiState(),
    val operatingPanel: ResultStatusUiState = ResultStatusUiState(),
    val carPositionIndicator: ResultStatusUiState = ResultStatusUiState(),
    val carSignage: CarSignageUiState = CarSignageUiState(),
    val carRoofStrength: ResultStatusUiState = ResultStatusUiState(),
    val carTopEmergencyExit: ResultStatusUiState = ResultStatusUiState(),
    val carSideEmergencyExit: ResultStatusUiState = ResultStatusUiState(),
    val carTopGuardRail: ResultStatusUiState = ResultStatusUiState(),
    val guardRailHeight300to850: ResultStatusUiState = ResultStatusUiState(),
    val guardRailHeightOver850: ResultStatusUiState = ResultStatusUiState(),
    val carTopLighting: ResultStatusUiState = ResultStatusUiState(),
    val manualOperationButtons: ResultStatusUiState = ResultStatusUiState(),
    val carInterior: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class GovernorAndSafetyBrakeUiState(
    val governorRopeClamp: ResultStatusUiState = ResultStatusUiState(),
    val governorSwitch: ResultStatusUiState = ResultStatusUiState(),
    val safetyBrakeSpeed: ResultStatusUiState = ResultStatusUiState(),
    val safetyBrakeType: ResultStatusUiState = ResultStatusUiState(),
    val safetyBrakeMechanism: ResultStatusUiState = ResultStatusUiState(),
    val progressiveSafetyBrake: ResultStatusUiState = ResultStatusUiState(),
    val instantaneousSafetyBrake: ResultStatusUiState = ResultStatusUiState(),
    val safetyBrakeOperation: ResultStatusUiState = ResultStatusUiState(),
    val electricalCutoutSwitch: ResultStatusUiState = ResultStatusUiState(),
    val limitSwitch: ResultStatusUiState = ResultStatusUiState(),
    val overloadDevice: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class CounterweightGuideRailsAndBuffersUiState(
    val counterweightMaterial: ResultStatusUiState = ResultStatusUiState(),
    val counterweightGuardScreen: ResultStatusUiState = ResultStatusUiState(),
    val guideRailConstruction: ResultStatusUiState = ResultStatusUiState(),
    val bufferType: ResultStatusUiState = ResultStatusUiState(),
    val bufferFunction: ResultStatusUiState = ResultStatusUiState(),
    val bufferSafetySwitch: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class FireServiceElevatorUiState(
    val backupPower: ResultStatusUiState = ResultStatusUiState(),
    val specialOperation: ResultStatusUiState = ResultStatusUiState(),
    val fireSwitch: ResultStatusUiState = ResultStatusUiState(),
    val label: ResultStatusUiState = ResultStatusUiState(),
    val electricalFireResistance: ResultStatusUiState = ResultStatusUiState(),
    val hoistwayWallFireResistance: ResultStatusUiState = ResultStatusUiState(),
    val carSize: ResultStatusUiState = ResultStatusUiState(),
    val doorSize: ResultStatusUiState = ResultStatusUiState(),
    val travelTime: ResultStatusUiState = ResultStatusUiState(),
    val evacuationFloor: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class AccessibilityElevatorUiState(
    val operatingPanel: ResultStatusUiState = ResultStatusUiState(),
    val panelHeight: ResultStatusUiState = ResultStatusUiState(),
    val doorOpenTime: ResultStatusUiState = ResultStatusUiState(),
    val doorWidth: ResultStatusUiState = ResultStatusUiState(),
    val audioInformation: ResultStatusUiState = ResultStatusUiState(),
    val label: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class SeismicSensorUiState(
    val availability: ResultStatusUiState = ResultStatusUiState(),
    val function: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class ElectricalInstallationUiState(
    val installationStandard: ResultStatusUiState = ResultStatusUiState(),
    val electricalPanel: ResultStatusUiState = ResultStatusUiState(),
    val backupPowerARD: ResultStatusUiState = ResultStatusUiState(),
    val groundingCable: ResultStatusUiState = ResultStatusUiState(),
    val fireAlarmConnection: ResultStatusUiState = ResultStatusUiState(),
    val fireServiceElevator: FireServiceElevatorUiState = FireServiceElevatorUiState(),
    val accessibilityElevator: AccessibilityElevatorUiState = AccessibilityElevatorUiState(),
    val seismicSensor: SeismicSensorUiState = SeismicSensorUiState()
)

@Immutable
data class InspectionAndTestingUiState(
    val machineRoomAndMachinery: MachineRoomAndMachineryUiState = MachineRoomAndMachineryUiState(),
    val suspensionRopesAndBelts: SuspensionRopesAndBeltsUiState = SuspensionRopesAndBeltsUiState(),
    val drumsAndSheaves: DrumsAndSheavesUiState = DrumsAndSheavesUiState(),
    val hoistwayAndPit: HoistwayAndPitUiState = HoistwayAndPitUiState(),
    val car: CarUiState = CarUiState(),
    val governorAndSafetyBrake: GovernorAndSafetyBrakeUiState = GovernorAndSafetyBrakeUiState(),
    val counterweightGuideRailsAndBuffers: CounterweightGuideRailsAndBuffersUiState = CounterweightGuideRailsAndBuffersUiState(),
    val electricalInstallation: ElectricalInstallationUiState = ElectricalInstallationUiState()
)

@Immutable
data class ElevatorUiState(
    val id: Long = 0,
    val documentType: DocumentType = DocumentType.LAPORAN,
    val nameOfInspectionType: InspectionType = InspectionType.EE,
    val subNameOfInspectionType: SubInspectionType = SubInspectionType.Elevator,
    val typeInspection: String = "",
    val eskOrElevType: String = "",
    val generalData: GeneralDataUiState = GeneralDataUiState(),
    val technicalDocumentInspection: TechnicalDocumentInspectionUiState = TechnicalDocumentInspectionUiState(),
    val inspectionAndTesting: InspectionAndTestingUiState = InspectionAndTestingUiState(),
    val conclusion: String = "",
    val createdAt: String = "",
    val isLoading: Boolean = false
)