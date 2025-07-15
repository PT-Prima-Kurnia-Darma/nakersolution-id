package com.nakersolutionid.nakersolutionid.data.remote.dto

import com.google.gson.annotations.SerializedName

// DTO untuk struktur { "result": "...", "status": ... }
data class ResultStatusDto(
    @SerializedName("result") val result: String = "",
    @SerializedName("status") val status: Boolean = false
)

// DTO untuk 'generalData'
data class GeneralDataDto(
    @SerializedName("ownerName") val ownerName: String = "",
    @SerializedName("ownerAddress") val ownerAddress: String = "",
    @SerializedName("nameUsageLocation") val nameUsageLocation: String = "",
    @SerializedName("addressUsageLocation") val addressUsageLocation: String = "",
    @SerializedName("manufacturerOrInstaller") val manufacturerOrInstaller: String = "",
    @SerializedName("elevatorType") val elevatorType: String = "",
    @SerializedName("brandOrType") val brandOrType: String = "",
    @SerializedName("countryAndYear") val countryAndYear: String = "",
    @SerializedName("serialNumber") val serialNumber: String = "",
    @SerializedName("capacity") val capacity: String = "",
    @SerializedName("speed") val speed: String = "",
    @SerializedName("floorsServed") val floorsServed: String = "",
    @SerializedName("permitNumber") val permitNumber: String = "",
    @SerializedName("inspectionDate") val inspectionDate: String = ""
)

// DTO untuk 'technicalDocumentInspection'
data class TechnicalDocumentInspectionDto(
    @SerializedName("designDrawing") val designDrawing: Boolean = false,
    @SerializedName("technicalCalculation") val technicalCalculation: Boolean = false,
    @SerializedName("materialCertificate") val materialCertificate: Boolean = false,
    @SerializedName("controlPanelDiagram") val controlPanelDiagram: Boolean = false,
    @SerializedName("asBuiltDrawing") val asBuiltDrawing: Boolean = false,
    @SerializedName("componentCertificates") val componentCertificates: Boolean = false,
    @SerializedName("safeWorkProcedure") val safeWorkProcedure: Boolean = false
)

// --- DTO untuk 'inspectionAndTesting' dan sub-seksinya ---

data class MachineRoomlessDto(
    @SerializedName("panelPlacement") val panelPlacement: ResultStatusDto = ResultStatusDto(),
    @SerializedName("lightingWorkArea") val lightingWorkArea: ResultStatusDto = ResultStatusDto(),
    @SerializedName("lightingBetweenWorkArea") val lightingBetweenWorkArea: ResultStatusDto = ResultStatusDto(),
    @SerializedName("manualBrakeRelease") val manualBrakeRelease: ResultStatusDto = ResultStatusDto(),
    @SerializedName("fireExtinguisherPlacement") val fireExtinguisherPlacement: ResultStatusDto = ResultStatusDto()
)

data class MachineRoomAndMachineryDto(
    @SerializedName("machineMounting") val machineMounting: ResultStatusDto = ResultStatusDto(),
    @SerializedName("mechanicalBrake") val mechanicalBrake: ResultStatusDto = ResultStatusDto(),
    @SerializedName("electricalBrake") val electricalBrake: ResultStatusDto = ResultStatusDto(),
    @SerializedName("machineRoomConstruction") val machineRoomConstruction: ResultStatusDto = ResultStatusDto(),
    @SerializedName("machineRoomClearance") val machineRoomClearance: ResultStatusDto = ResultStatusDto(),
    @SerializedName("machineRoomImplementation") val machineRoomImplementation: ResultStatusDto = ResultStatusDto(),
    @SerializedName("ventilation") val ventilation: ResultStatusDto = ResultStatusDto(),
    @SerializedName("machineRoomDoor") val machineRoomDoor: ResultStatusDto = ResultStatusDto(),
    @SerializedName("mainPowerPanelPosition") val mainPowerPanelPosition: ResultStatusDto = ResultStatusDto(),
    @SerializedName("rotatingPartsGuard") val rotatingPartsGuard: ResultStatusDto = ResultStatusDto(),
    @SerializedName("ropeHoleGuard") val ropeHoleGuard: ResultStatusDto = ResultStatusDto(),
    @SerializedName("machineRoomAccessLadder") val machineRoomAccessLadder: ResultStatusDto = ResultStatusDto(),
    @SerializedName("floorLevelDifference") val floorLevelDifference: ResultStatusDto = ResultStatusDto(),
    @SerializedName("fireExtinguisher") val fireExtinguisher: ResultStatusDto = ResultStatusDto(),
    @SerializedName("machineRoomless") val machineRoomless: MachineRoomlessDto = MachineRoomlessDto(),
    @SerializedName("emergencyStopSwitch") val emergencyStopSwitch: ResultStatusDto = ResultStatusDto()
)

data class SuspensionRopesAndBeltsDto(
    @SerializedName("condition") val condition: ResultStatusDto = ResultStatusDto(),
    @SerializedName("chainUsage") val chainUsage: ResultStatusDto = ResultStatusDto(),
    @SerializedName("safetyFactor") val safetyFactor: ResultStatusDto = ResultStatusDto(),
    @SerializedName("ropeWithCounterweight") val ropeWithCounterweight: ResultStatusDto = ResultStatusDto(),
    @SerializedName("ropeWithoutCounterweight") val ropeWithoutCounterweight: ResultStatusDto = ResultStatusDto(),
    @SerializedName("belt") val belt: ResultStatusDto = ResultStatusDto(),
    @SerializedName("slackRopeDevice") val slackRopeDevice: ResultStatusDto = ResultStatusDto()
)

data class DrumsAndSheavesDto(
    @SerializedName("drumGrooves") val drumGrooves: ResultStatusDto = ResultStatusDto(),
    @SerializedName("passengerDrumDiameter") val passengerDrumDiameter: ResultStatusDto = ResultStatusDto(),
    @SerializedName("governorDrumDiameter") val governorDrumDiameter: ResultStatusDto = ResultStatusDto()
)

data class HoistwayAndPitDto(
    @SerializedName("construction") val construction: ResultStatusDto = ResultStatusDto(),
    @SerializedName("walls") val walls: ResultStatusDto = ResultStatusDto(),
    @SerializedName("inclinedElevatorTrackBed") val inclinedElevatorTrackBed: ResultStatusDto = ResultStatusDto(),
    @SerializedName("cleanliness") val cleanliness: ResultStatusDto = ResultStatusDto(),
    @SerializedName("lighting") val lighting: ResultStatusDto = ResultStatusDto(),
    @SerializedName("emergencyDoorNonStop") val emergencyDoorNonStop: ResultStatusDto = ResultStatusDto(),
    @SerializedName("emergencyDoorSize") val emergencyDoorSize: ResultStatusDto = ResultStatusDto(),
    @SerializedName("emergencyDoorSafetySwitch") val emergencyDoorSafetySwitch: ResultStatusDto = ResultStatusDto(),
    @SerializedName("emergencyDoorBridge") val emergencyDoorBridge: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carTopClearance") val carTopClearance: ResultStatusDto = ResultStatusDto(),
    @SerializedName("pitClearance") val pitClearance: ResultStatusDto = ResultStatusDto(),
    @SerializedName("pitLadder") val pitLadder: ResultStatusDto = ResultStatusDto(),
    @SerializedName("pitBelowWorkingArea") val pitBelowWorkingArea: ResultStatusDto = ResultStatusDto(),
    @SerializedName("pitAccessSwitch") val pitAccessSwitch: ResultStatusDto = ResultStatusDto(),
    @SerializedName("pitScreen") val pitScreen: ResultStatusDto = ResultStatusDto(),
    @SerializedName("hoistwayDoorLeaf") val hoistwayDoorLeaf: ResultStatusDto = ResultStatusDto(),
    @SerializedName("hoistwayDoorInterlock") val hoistwayDoorInterlock: ResultStatusDto = ResultStatusDto(),
    @SerializedName("floorLeveling") val floorLeveling: ResultStatusDto = ResultStatusDto(),
    @SerializedName("hoistwaySeparatorBeam") val hoistwaySeparatorBeam: ResultStatusDto = ResultStatusDto(),
    @SerializedName("inclinedElevatorStairs") val inclinedElevatorStairs: ResultStatusDto = ResultStatusDto()
)

data class CarDoorSpecsDto(
    @SerializedName("size") val size: ResultStatusDto = ResultStatusDto(),
    @SerializedName("lockAndSwitch") val lockAndSwitch: ResultStatusDto = ResultStatusDto(),
    @SerializedName("sillClearance") val sillClearance: ResultStatusDto = ResultStatusDto()
)

data class CarSignageDto(
    @SerializedName("manufacturerName") val manufacturerName: ResultStatusDto = ResultStatusDto(),
    @SerializedName("loadCapacity") val loadCapacity: ResultStatusDto = ResultStatusDto(),
    @SerializedName("noSmokingSign") val noSmokingSign: ResultStatusDto = ResultStatusDto(),
    @SerializedName("overloadIndicator") val overloadIndicator: ResultStatusDto = ResultStatusDto(),
    @SerializedName("doorOpenCloseButtons") val doorOpenCloseButtons: ResultStatusDto = ResultStatusDto(),
    @SerializedName("floorButtons") val floorButtons: ResultStatusDto = ResultStatusDto(),
    @SerializedName("alarmButton") val alarmButton: ResultStatusDto = ResultStatusDto(),
    @SerializedName("twoWayIntercom") val twoWayIntercom: ResultStatusDto = ResultStatusDto()
)

data class CarDto(
    @SerializedName("frame") val frame: ResultStatusDto = ResultStatusDto(),
    @SerializedName("body") val body: ResultStatusDto = ResultStatusDto(),
    @SerializedName("wallHeight") val wallHeight: ResultStatusDto = ResultStatusDto(),
    @SerializedName("floorArea") val floorArea: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carAreaExpansion") val carAreaExpansion: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carDoor") val carDoor: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carDoorSpecs") val carDoorSpecs: CarDoorSpecsDto = CarDoorSpecsDto(),
    @SerializedName("carToBeamClearance") val carToBeamClearance: ResultStatusDto = ResultStatusDto(),
    @SerializedName("alarmBell") val alarmBell: ResultStatusDto = ResultStatusDto(),
    @SerializedName("backupPowerARD") val backupPowerARD: ResultStatusDto = ResultStatusDto(),
    @SerializedName("intercom") val intercom: ResultStatusDto = ResultStatusDto(),
    @SerializedName("ventilation") val ventilation: ResultStatusDto = ResultStatusDto(),
    @SerializedName("emergencyLighting") val emergencyLighting: ResultStatusDto = ResultStatusDto(),
    @SerializedName("operatingPanel") val operatingPanel: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carPositionIndicator") val carPositionIndicator: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carSignage") val carSignage: CarSignageDto = CarSignageDto(),
    @SerializedName("carRoofStrength") val carRoofStrength: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carTopEmergencyExit") val carTopEmergencyExit: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carSideEmergencyExit") val carSideEmergencyExit: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carTopGuardRail") val carTopGuardRail: ResultStatusDto = ResultStatusDto(),
    @SerializedName("guardRailHeight300to850") val guardRailHeight300to850: ResultStatusDto = ResultStatusDto(),
    @SerializedName("guardRailHeightOver850") val guardRailHeightOver850: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carTopLighting") val carTopLighting: ResultStatusDto = ResultStatusDto(),
    @SerializedName("manualOperationButtons") val manualOperationButtons: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carInterior") val carInterior: ResultStatusDto = ResultStatusDto()
)

data class GovernorAndSafetyBrakeDto(
    @SerializedName("governorRopeClamp") val governorRopeClamp: ResultStatusDto = ResultStatusDto(),
    @SerializedName("governorSwitch") val governorSwitch: ResultStatusDto = ResultStatusDto(),
    @SerializedName("safetyBrakeSpeed") val safetyBrakeSpeed: ResultStatusDto = ResultStatusDto(),
    @SerializedName("safetyBrakeType") val safetyBrakeType: ResultStatusDto = ResultStatusDto(),
    @SerializedName("safetyBrakeMechanism") val safetyBrakeMechanism: ResultStatusDto = ResultStatusDto(),
    @SerializedName("progressiveSafetyBrake") val progressiveSafetyBrake: ResultStatusDto = ResultStatusDto(),
    @SerializedName("instantaneousSafetyBrake") val instantaneousSafetyBrake: ResultStatusDto = ResultStatusDto(),
    @SerializedName("safetyBrakeOperation") val safetyBrakeOperation: ResultStatusDto = ResultStatusDto(),
    @SerializedName("electricalCutoutSwitch") val electricalCutoutSwitch: ResultStatusDto = ResultStatusDto(),
    @SerializedName("limitSwitch") val limitSwitch: ResultStatusDto = ResultStatusDto(),
    @SerializedName("overloadDevice") val overloadDevice: ResultStatusDto = ResultStatusDto()
)

data class CounterweightGuideRailsAndBuffersDto(
    @SerializedName("counterweightMaterial") val counterweightMaterial: ResultStatusDto = ResultStatusDto(),
    @SerializedName("counterweightGuardScreen") val counterweightGuardScreen: ResultStatusDto = ResultStatusDto(),
    @SerializedName("guideRailConstruction") val guideRailConstruction: ResultStatusDto = ResultStatusDto(),
    @SerializedName("bufferType") val bufferType: ResultStatusDto = ResultStatusDto(),
    @SerializedName("bufferFunction") val bufferFunction: ResultStatusDto = ResultStatusDto(),
    @SerializedName("bufferSafetySwitch") val bufferSafetySwitch: ResultStatusDto = ResultStatusDto()
)

data class FireServiceElevatorDto(
    @SerializedName("backupPower") val backupPower: ResultStatusDto = ResultStatusDto(),
    @SerializedName("specialOperation") val specialOperation: ResultStatusDto = ResultStatusDto(),
    @SerializedName("fireSwitch") val fireSwitch: ResultStatusDto = ResultStatusDto(),
    @SerializedName("label") val label: ResultStatusDto = ResultStatusDto(),
    @SerializedName("electricalFireResistance") val electricalFireResistance: ResultStatusDto = ResultStatusDto(),
    @SerializedName("hoistwayWallFireResistance") val hoistwayWallFireResistance: ResultStatusDto = ResultStatusDto(),
    @SerializedName("carSize") val carSize: ResultStatusDto = ResultStatusDto(),
    @SerializedName("doorSize") val doorSize: ResultStatusDto = ResultStatusDto(),
    @SerializedName("travelTime") val travelTime: ResultStatusDto = ResultStatusDto(),
    @SerializedName("evacuationFloor") val evacuationFloor: ResultStatusDto = ResultStatusDto()
)

data class AccessibilityElevatorDto(
    @SerializedName("operatingPanel") val operatingPanel: ResultStatusDto = ResultStatusDto(),
    @SerializedName("panelHeight") val panelHeight: ResultStatusDto = ResultStatusDto(),
    @SerializedName("doorOpenTime") val doorOpenTime: ResultStatusDto = ResultStatusDto(),
    @SerializedName("doorWidth") val doorWidth: ResultStatusDto = ResultStatusDto(),
    @SerializedName("audioInformation") val audioInformation: ResultStatusDto = ResultStatusDto(),
    @SerializedName("label") val label: ResultStatusDto = ResultStatusDto()
)

data class SeismicSensorDto(
    @SerializedName("availability") val availability: ResultStatusDto = ResultStatusDto(),
    @SerializedName("function") val function: ResultStatusDto = ResultStatusDto()
)

data class ElectricalInstallationDto(
    @SerializedName("installationStandard") val installationStandard: ResultStatusDto = ResultStatusDto(),
    @SerializedName("electricalPanel") val electricalPanel: ResultStatusDto = ResultStatusDto(),
    @SerializedName("backupPowerARD") val backupPowerARD: ResultStatusDto = ResultStatusDto(),
    @SerializedName("groundingCable") val groundingCable: ResultStatusDto = ResultStatusDto(),
    @SerializedName("fireAlarmConnection") val fireAlarmConnection: ResultStatusDto = ResultStatusDto(),
    @SerializedName("fireServiceElevator") val fireServiceElevator: FireServiceElevatorDto = FireServiceElevatorDto(),
    @SerializedName("accessibilityElevator") val accessibilityElevator: AccessibilityElevatorDto = AccessibilityElevatorDto(),
    @SerializedName("seismicSensor") val seismicSensor: SeismicSensorDto = SeismicSensorDto()
)

data class InspectionAndTestingDto(
    @SerializedName("machineRoomAndMachinery") val machineRoomAndMachinery: MachineRoomAndMachineryDto = MachineRoomAndMachineryDto(),
    @SerializedName("suspensionRopesAndBelts") val suspensionRopesAndBelts: SuspensionRopesAndBeltsDto = SuspensionRopesAndBeltsDto(),
    @SerializedName("drumsAndSheaves") val drumsAndSheaves: DrumsAndSheavesDto = DrumsAndSheavesDto(),
    @SerializedName("hoistwayAndPit") val hoistwayAndPit: HoistwayAndPitDto = HoistwayAndPitDto(),
    @SerializedName("car") val car: CarDto = CarDto(),
    @SerializedName("governorAndSafetyBrake") val governorAndSafetyBrake: GovernorAndSafetyBrakeDto = GovernorAndSafetyBrakeDto(),
    @SerializedName("counterweightGuideRailsAndBuffers") val counterweightGuideRailsAndBuffers: CounterweightGuideRailsAndBuffersDto = CounterweightGuideRailsAndBuffersDto(),
    @SerializedName("electricalInstallation") val electricalInstallation: ElectricalInstallationDto = ElectricalInstallationDto()
)

// DTO utama yang akan dikirim sebagai body JSON
data class ElevatorReportDto(
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("generalData") val generalData: GeneralDataDto = GeneralDataDto(),
    @SerializedName("technicalDocumentInspection") val technicalDocumentInspection: TechnicalDocumentInspectionDto = TechnicalDocumentInspectionDto(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: InspectionAndTestingDto = InspectionAndTestingDto(),
    @SerializedName("conclusion") val conclusion: String = ""
)