package com.nakersolutionid.nakersolutionid.data.remote.dto.elevator

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus

data class ElevatorReportRequest(
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("generalData") val generalData: ElevatorGeneralData,
    @SerializedName("technicalDocumentInspection") val technicalDocumentInspection: ElevatorTechnicalDocumentInspection,
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: ElevatorInspectionAndTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recomendations") val recomendations: String,
)

// Data DTO for single Elevator Report response
data class ElevatorSingleReportResponseData(
    @SerializedName("laporan") val laporan: ElevatorReportData
)

// Data DTO for list of Elevator Reports response
data class ElevatorListReportResponseData(
    @SerializedName("laporan") val laporan: List<ElevatorReportData>
)

// Main DTO for Elevator Report (used for create, update, and individual get)
data class ElevatorReportData(
    @SerializedName("id") val id: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("generalData") val generalData: ElevatorGeneralData,
    @SerializedName("technicalDocumentInspection") val technicalDocumentInspection: ElevatorTechnicalDocumentInspection,
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: ElevatorInspectionAndTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recomendations") val recomendations: String,
    @SerializedName("subInspectionType") val subInspectionType: String,
    @SerializedName("documentType") val documentType: String
)

data class ElevatorGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("ownerAddress") val ownerAddress: String,
    @SerializedName("nameUsageLocation") val nameUsageLocation: String,
    @SerializedName("addressUsageLocation") val addressUsageLocation: String,
    @SerializedName("manufacturerOrInstaller") val manufacturerOrInstaller: String,
    @SerializedName("elevatorType") val elevatorType: String,
    @SerializedName("brandOrType") val brandOrType: String,
    @SerializedName("countryAndYear") val countryAndYear: String,
    @SerializedName("serialNumber") val serialNumber: String,
    @SerializedName("capacity") val capacity: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("floorsServed") val floorsServed: String,
    @SerializedName("permitNumber") val permitNumber: String,
    @SerializedName("inspectionDate") val inspectionDate: String
)

data class ElevatorTechnicalDocumentInspection(
    @SerializedName("designDrawing") val designDrawing: Boolean,
    @SerializedName("technicalCalculation") val technicalCalculation: Boolean,
    @SerializedName("materialCertificate") val materialCertificate: Boolean,
    @SerializedName("controlPanelDiagram") val controlPanelDiagram: Boolean,
    @SerializedName("asBuiltDrawing") val asBuiltDrawing: Boolean,
    @SerializedName("componentCertificates") val componentCertificates: Boolean,
    @SerializedName("safeWorkProcedure") val safeWorkProcedure: Boolean
)

data class ElevatorInspectionAndTesting(
    @SerializedName("machineRoomAndMachinery") val machineRoomAndMachinery: ElevatorMachineRoomAndMachinery,
    @SerializedName("suspensionRopesAndBelts") val suspensionRopesAndBelts: ElevatorSuspensionRopesAndBelts,
    @SerializedName("drumsAndSheaves") val drumsAndSheaves: ElevatorDrumsAndSheaves,
    @SerializedName("hoistwayAndPit") val hoistwayAndPit: ElevatorHoistwayAndPit,
    @SerializedName("car") val car: ElevatorCar,
    @SerializedName("governorAndSafetyBrake") val governorAndSafetyBrake: ElevatorGovernorAndSafetyBrake,
    @SerializedName("counterweightGuideRailsAndBuffers") val counterweightGuideRailsAndBuffers: ElevatorCounterweightGuideRailsAndBuffers,
    @SerializedName("electricalInstallation") val electricalInstallation: ElevatorElectricalInstallation
)

data class ElevatorMachineRoomAndMachinery(
    @SerializedName("machineMounting") val machineMounting: ResultStatus,
    @SerializedName("mechanicalBrake") val mechanicalBrake: ResultStatus,
    @SerializedName("electricalBrake") val electricalBrake: ResultStatus,
    @SerializedName("machineRoomConstruction") val machineRoomConstruction: ResultStatus,
    @SerializedName("machineRoomClearance") val machineRoomClearance: ResultStatus,
    @SerializedName("machineRoomImplementation") val machineRoomImplementation: ResultStatus,
    @SerializedName("ventilation") val ventilation: ResultStatus,
    @SerializedName("machineRoomDoor") val machineRoomDoor: ResultStatus,
    @SerializedName("mainPowerPanelPosition") val mainPowerPanelPosition: ResultStatus,
    @SerializedName("rotatingPartsGuard") val rotatingPartsGuard: ResultStatus,
    @SerializedName("ropeHoleGuard") val ropeHoleGuard: ResultStatus,
    @SerializedName("machineRoomAccessLadder") val machineRoomAccessLadder: ResultStatus,
    @SerializedName("floorLevelDifference") val floorLevelDifference: ResultStatus,
    @SerializedName("fireExtinguisher") val fireExtinguisher: ResultStatus,
    @SerializedName("machineRoomless") val machineRoomless: ElevatorMachineRoomless,
    @SerializedName("emergencyStopSwitch") val emergencyStopSwitch: ResultStatus
)

data class ElevatorMachineRoomless(
    @SerializedName("panelPlacement") val panelPlacement: ResultStatus,
    @SerializedName("lightingWorkArea") val lightingWorkArea: ResultStatus,
    @SerializedName("lightingBetweenWorkArea") val lightingBetweenWorkArea: ResultStatus,
    @SerializedName("manualBrakeRelease") val manualBrakeRelease: ResultStatus,
    @SerializedName("fireExtinguisherPlacement") val fireExtinguisherPlacement: ResultStatus
)

data class ElevatorSuspensionRopesAndBelts(
    @SerializedName("condition") val condition: ResultStatus,
    @SerializedName("chainUsage") val chainUsage: ResultStatus,
    @SerializedName("safetyFactor") val safetyFactor: ResultStatus,
    @SerializedName("ropeWithCounterweight") val ropeWithCounterweight: ResultStatus,
    @SerializedName("ropeWithoutCounterweight") val ropeWithoutCounterweight: ResultStatus,
    @SerializedName("belt") val belt: ResultStatus,
    @SerializedName("slackRopeDevice") val slackRopeDevice: ResultStatus
)

data class ElevatorDrumsAndSheaves(
    @SerializedName("drumGrooves") val drumGrooves: ResultStatus,
    @SerializedName("passengerDrumDiameter") val passengerDrumDiameter: ResultStatus,
    @SerializedName("governorDrumDiameter") val governorDrumDiameter: ResultStatus
)

data class ElevatorHoistwayAndPit(
    @SerializedName("construction") val construction: ResultStatus,
    @SerializedName("walls") val walls: ResultStatus,
    @SerializedName("inclinedElevatorTrackBed") val inclinedElevatorTrackBed: ResultStatus,
    @SerializedName("cleanliness") val cleanliness: ResultStatus,
    @SerializedName("lighting") val lighting: ResultStatus,
    @SerializedName("emergencyDoorNonStop") val emergencyDoorNonStop: ResultStatus,
    @SerializedName("emergencyDoorSize") val emergencyDoorSize: ResultStatus,
    @SerializedName("emergencyDoorSafetySwitch") val emergencyDoorSafetySwitch: ResultStatus,
    @SerializedName("emergencyDoorBridge") val emergencyDoorBridge: ResultStatus,
    @SerializedName("carTopClearance") val carTopClearance: ResultStatus,
    @SerializedName("pitClearance") val pitClearance: ResultStatus,
    @SerializedName("pitLadder") val pitLadder: ResultStatus,
    @SerializedName("pitBelowWorkingArea") val pitBelowWorkingArea: ResultStatus,
    @SerializedName("pitAccessSwitch") val pitAccessSwitch: ResultStatus,
    @SerializedName("pitScreen") val pitScreen: ResultStatus,
    @SerializedName("hoistwayDoorLeaf") val hoistwayDoorLeaf: ResultStatus,
    @SerializedName("hoistwayDoorInterlock") val hoistwayDoorInterlock: ResultStatus,
    @SerializedName("floorLeveling") val floorLeveling: ResultStatus,
    @SerializedName("hoistwaySeparatorBeam") val hoistwaySeparatorBeam: ResultStatus,
    @SerializedName("inclinedElevatorStairs") val inclinedElevatorStairs: ResultStatus
)

data class ElevatorCar(
    @SerializedName("frame") val frame: ResultStatus,
    @SerializedName("body") val body: ResultStatus,
    @SerializedName("wallHeight") val wallHeight: ResultStatus,
    @SerializedName("floorArea") val floorArea: ResultStatus,
    @SerializedName("carAreaExpansion") val carAreaExpansion: ResultStatus,
    @SerializedName("carDoor") val carDoor: ResultStatus,
    @SerializedName("carDoorSpecs") val carDoorSpecs: ElevatorCarDoorSpecs,
    @SerializedName("carToBeamClearance") val carToBeamClearance: ResultStatus,
    @SerializedName("alarmBell") val alarmBell: ResultStatus,
    @SerializedName("backupPowerARD") val backupPowerARD: ResultStatus,
    @SerializedName("intercom") val intercom: ResultStatus,
    @SerializedName("ventilation") val ventilation: ResultStatus,
    @SerializedName("emergencyLighting") val emergencyLighting: ResultStatus,
    @SerializedName("operatingPanel") val operatingPanel: ResultStatus,
    @SerializedName("carPositionIndicator") val carPositionIndicator: ResultStatus,
    @SerializedName("carSignage") val carSignage: ElevatorCarSignage,
    @SerializedName("carRoofStrength") val carRoofStrength: ResultStatus,
    @SerializedName("carTopEmergencyExit") val carTopEmergencyExit: ResultStatus,
    @SerializedName("carSideEmergencyExit") val carSideEmergencyExit: ResultStatus,
    @SerializedName("carTopGuardRail") val carTopGuardRail: ResultStatus,
    @SerializedName("guardRailHeight300to850") val guardRailHeight300to850: ResultStatus,
    @SerializedName("guardRailHeightOver850") val guardRailHeightOver850: ResultStatus,
    @SerializedName("carTopLighting") val carTopLighting: ResultStatus,
    @SerializedName("manualOperationButtons") val manualOperationButtons: ResultStatus,
    @SerializedName("carInterior") val carInterior: ResultStatus
)

data class ElevatorCarDoorSpecs(
    @SerializedName("size") val size: ResultStatus,
    @SerializedName("lockAndSwitch") val lockAndSwitch: ResultStatus,
    @SerializedName("sillClearance") val sillClearance: ResultStatus
)

data class ElevatorCarSignage(
    @SerializedName("manufacturerName") val manufacturerName: ResultStatus,
    @SerializedName("loadCapacity") val loadCapacity: ResultStatus,
    @SerializedName("noSmokingSign") val noSmokingSign: ResultStatus,
    @SerializedName("overloadIndicator") val overloadIndicator: ResultStatus,
    @SerializedName("doorOpenCloseButtons") val doorOpenCloseButtons: ResultStatus,
    @SerializedName("floorButtons") val floorButtons: ResultStatus,
    @SerializedName("alarmButton") val alarmButton: ResultStatus,
    @SerializedName("twoWayIntercom") val twoWayIntercom: ResultStatus
)

data class ElevatorGovernorAndSafetyBrake(
    @SerializedName("governorRopeClamp") val governorRopeClamp: ResultStatus,
    @SerializedName("governorSwitch") val governorSwitch: ResultStatus,
    @SerializedName("safetyBrakeSpeed") val safetyBrakeSpeed: ResultStatus,
    @SerializedName("safetyBrakeType") val safetyBrakeType: ResultStatus,
    @SerializedName("safetyBrakeMechanism") val safetyBrakeMechanism: ResultStatus,
    @SerializedName("progressiveSafetyBrake") val progressiveSafetyBrake: ResultStatus,
    @SerializedName("instantaneousSafetyBrake") val instantaneousSafetyBrake: ResultStatus,
    @SerializedName("safetyBrakeOperation") val safetyBrakeOperation: ResultStatus,
    @SerializedName("electricalCutoutSwitch") val electricalCutoutSwitch: ResultStatus,
    @SerializedName("limitSwitch") val limitSwitch: ResultStatus,
    @SerializedName("overloadDevice") val overloadDevice: ResultStatus
)

data class ElevatorCounterweightGuideRailsAndBuffers(
    @SerializedName("counterweightMaterial") val counterweightMaterial: ResultStatus,
    @SerializedName("counterweightGuardScreen") val counterweightGuardScreen: ResultStatus,
    @SerializedName("guideRailConstruction") val guideRailConstruction: ResultStatus,
    @SerializedName("bufferType") val bufferType: ResultStatus,
    @SerializedName("bufferFunction") val bufferFunction: ResultStatus,
    @SerializedName("bufferSafetySwitch") val bufferSafetySwitch: ResultStatus
)

data class ElevatorElectricalInstallation(
    @SerializedName("installationStandard") val installationStandard: ResultStatus,
    @SerializedName("electricalPanel") val electricalPanel: ResultStatus,
    @SerializedName("backupPowerARD") val backupPowerARD: ResultStatus,
    @SerializedName("groundingCable") val groundingCable: ResultStatus,
    @SerializedName("fireAlarmConnection") val fireAlarmConnection: ResultStatus,
    @SerializedName("fireServiceElevator") val fireServiceElevator: ElevatorFireServiceElevator,
    @SerializedName("accessibilityElevator") val accessibilityElevator: ElevatorAccessibilityElevator,
    @SerializedName("seismicSensor") val seismicSensor: ElevatorSeismicSensor
)

data class ElevatorFireServiceElevator(
    @SerializedName("backupPower") val backupPower: ResultStatus,
    @SerializedName("specialOperation") val specialOperation: ResultStatus,
    @SerializedName("fireSwitch") val fireSwitch: ResultStatus,
    @SerializedName("label") val label: ResultStatus,
    @SerializedName("electricalFireResistance") val electricalFireResistance: ResultStatus,
    @SerializedName("hoistwayWallFireResistance") val hoistwayWallFireResistance: ResultStatus,
    @SerializedName("carSize") val carSize: ResultStatus,
    @SerializedName("doorSize") val doorSize: ResultStatus,
    @SerializedName("travelTime") val travelTime: ResultStatus,
    @SerializedName("evacuationFloor") val evacuationFloor: ResultStatus
)

data class ElevatorAccessibilityElevator(
    @SerializedName("operatingPanel") val operatingPanel: ResultStatus,
    @SerializedName("panelHeight") val panelHeight: ResultStatus,
    @SerializedName("doorOpenTime") val doorOpenTime: ResultStatus,
    @SerializedName("doorWidth") val doorWidth: ResultStatus,
    @SerializedName("audioInformation") val audioInformation: ResultStatus,
    @SerializedName("label") val label: ResultStatus
)

data class ElevatorSeismicSensor(
    @SerializedName("availability") val availability: ResultStatus,
    @SerializedName("function") val function: ResultStatus
)