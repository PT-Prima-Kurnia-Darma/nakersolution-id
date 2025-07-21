package com.nakersolutionid.nakersolutionid.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateElevatorReportBody(
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("extraId") val extraId: Long = 0,
    @SerializedName("generalData") val generalData: ElevatorReportGeneralData = ElevatorReportGeneralData(),
    @SerializedName("technicalDocumentInspection") val technicalDocumentInspection: ElevatorReportTechnicalDocumentInspection = ElevatorReportTechnicalDocumentInspection(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: ElevatorReportInspectionAndTesting = ElevatorReportInspectionAndTesting(),
    @SerializedName("conclusion") val conclusion: String = ""
)

data class CreateElevatorReportResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: ElevatorReportData = ElevatorReportData()
)

data class DeleteElevatorReportResponse(
    @SerializedName("message") val message: String = "",
    @SerializedName("status") val status: String = ""
)

data class GetElevatorReportsResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: ElevatorReportsResponseData = ElevatorReportsResponseData()
)

data class ElevatorReportsResponseData( // Renamed from GetElevatorReportsDataWrapperDto
    @SerializedName("laporan") val listLaporan: List<ElevatorReportDetail> = emptyList() // Updated type
)

data class ElevatorReportGeneralData( // Renamed from ElevatorReportGeneralDataDto
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

data class ElevatorReportTechnicalDocumentInspection( // Renamed from ElevatorReportTechnicalDocumentInspectionDto
    @SerializedName("designDrawing") val designDrawing: Boolean = false,
    @SerializedName("technicalCalculation") val technicalCalculation: Boolean = false,
    @SerializedName("materialCertificate") val materialCertificate: Boolean = false,
    @SerializedName("controlPanelDiagram") val controlPanelDiagram: Boolean = false,
    @SerializedName("asBuiltDrawing") val asBuiltDrawing: Boolean = false,
    @SerializedName("componentCertificates") val componentCertificates: Boolean = false,
    @SerializedName("safeWorkProcedure") val safeWorkProcedure: Boolean = false
)

data class ElevatorReportInspectionAndTesting( // Renamed from ElevatorReportInspectionAndTestingDto
    @SerializedName("machineRoomAndMachinery") val machineRoomAndMachinery: ElevatorReportMachineRoomAndMachinery = ElevatorReportMachineRoomAndMachinery(),
    @SerializedName("suspensionRopesAndBelts") val suspensionRopesAndBelts: ElevatorReportSuspensionRopesAndBelts = ElevatorReportSuspensionRopesAndBelts(),
    @SerializedName("drumsAndSheaves") val drumsAndSheaves: ElevatorReportDrumsAndSheaves = ElevatorReportDrumsAndSheaves(),
    @SerializedName("hoistwayAndPit") val hoistwayAndPit: ElevatorReportHoistwayAndPit = ElevatorReportHoistwayAndPit(),
    @SerializedName("car") val car: ElevatorReportCar = ElevatorReportCar(),
    @SerializedName("governorAndSafetyBrake") val governorAndSafetyBrake: ElevatorReportGovernorAndSafetyBrake = ElevatorReportGovernorAndSafetyBrake(),
    @SerializedName("counterweightGuideRailsAndBuffers") val counterweightGuideRailsAndBuffers: ElevatorReportCounterweightGuideRailsAndBuffers = ElevatorReportCounterweightGuideRailsAndBuffers(),
    @SerializedName("electricalInstallation") val electricalInstallation: ElevatorReportElectricalInstallation = ElevatorReportElectricalInstallation()
)

data class ElevatorReportMachineRoomAndMachinery( // Renamed from ElevatorReportMachineRoomAndMachineryDto
    @SerializedName("machineMounting") val machineMounting: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("mechanicalBrake") val mechanicalBrake: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("electricalBrake") val electricalBrake: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("machineRoomConstruction") val machineRoomConstruction: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("machineRoomClearance") val machineRoomClearance: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("machineRoomImplementation") val machineRoomImplementation: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("ventilation") val ventilation: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("machineRoomDoor") val machineRoomDoor: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("mainPowerPanelPosition") val mainPowerPanelPosition: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("rotatingPartsGuard") val rotatingPartsGuard: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("ropeHoleGuard") val ropeHoleGuard: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("machineRoomAccessLadder") val machineRoomAccessLadder: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("floorLevelDifference") val floorLevelDifference: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("fireExtinguisher") val fireExtinguisher: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("machineRoomless") val machineRoomless: ElevatorReportMachineRoomless = ElevatorReportMachineRoomless(),
    @SerializedName("emergencyStopSwitch") val emergencyStopSwitch: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportMachineRoomless( // Renamed from ElevatorReportMachineRoomlessDto
    @SerializedName("panelPlacement") val panelPlacement: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("lightingWorkArea") val lightingWorkArea: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("lightingBetweenWorkArea") val lightingBetweenWorkArea: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("manualBrakeRelease") val manualBrakeRelease: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("fireExtinguisherPlacement") val fireExtinguisherPlacement: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportSuspensionRopesAndBelts( // Renamed from ElevatorReportSuspensionRopesAndBeltsDto
    @SerializedName("condition") val condition: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("chainUsage") val chainUsage: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("safetyFactor") val safetyFactor: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("ropeWithCounterweight") val ropeWithCounterweight: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("ropeWithoutCounterweight") val ropeWithoutCounterweight: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("belt") val belt: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("slackRopeDevice") val slackRopeDevice: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportDrumsAndSheaves( // Renamed from ElevatorReportDrumsAndSheavesDto
    @SerializedName("drumGrooves") val drumGrooves: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("passengerDrumDiameter") val passengerDrumDiameter: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("governorDrumDiameter") val governorDrumDiameter: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportHoistwayAndPit( // Renamed from ElevatorReportHoistwayAndPitDto
    @SerializedName("construction") val construction: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("walls") val walls: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("inclinedElevatorTrackBed") val inclinedElevatorTrackBed: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("cleanliness") val cleanliness: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("lighting") val lighting: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("emergencyDoorNonStop") val emergencyDoorNonStop: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("emergencyDoorSize") val emergencyDoorSize: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("emergencyDoorSafetySwitch") val emergencyDoorSafetySwitch: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("emergencyDoorBridge") val emergencyDoorBridge: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carTopClearance") val carTopClearance: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("pitClearance") val pitClearance: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("pitLadder") val pitLadder: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("pitBelowWorkingArea") val pitBelowWorkingArea: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("pitAccessSwitch") val pitAccessSwitch: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("pitScreen") val pitScreen: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("hoistwayDoorLeaf") val hoistwayDoorLeaf: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("hoistwayDoorInterlock") val hoistwayDoorInterlock: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("floorLeveling") val floorLeveling: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("hoistwaySeparatorBeam") val hoistwaySeparatorBeam: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("inclinedElevatorStairs") val inclinedElevatorStairs: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportCar( // Renamed from ElevatorReportCarDto
    @SerializedName("frame") val frame: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("body") val body: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("wallHeight") val wallHeight: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("floorArea") val floorArea: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carAreaExpansion") val carAreaExpansion: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carDoor") val carDoor: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carDoorSpecs") val carDoorSpecs: ElevatorReportCarDoorSpecs = ElevatorReportCarDoorSpecs(),
    @SerializedName("carToBeamClearance") val carToBeamClearance: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("alarmBell") val alarmBell: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("backupPowerARD") val backupPowerARD: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("intercom") val intercom: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("ventilation") val ventilation: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("emergencyLighting") val emergencyLighting: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("operatingPanel") val operatingPanel: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carPositionIndicator") val carPositionIndicator: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carSignage") val carSignage: ElevatorReportCarSignage = ElevatorReportCarSignage(),
    @SerializedName("carRoofStrength") val carRoofStrength: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carTopEmergencyExit") val carTopEmergencyExit: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carSideEmergencyExit") val carSideEmergencyExit: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carTopGuardRail") val carTopGuardRail: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("guardRailHeight300to850") val guardRailHeight300to850: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("guardRailHeightOver850") val guardRailHeightOver850: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carTopLighting") val carTopLighting: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("manualOperationButtons") val manualOperationButtons: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carInterior") val carInterior: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportCarDoorSpecs( // Renamed from ElevatorReportCarDoorSpecsDto
    @SerializedName("size") val size: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("lockAndSwitch") val lockAndSwitch: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("sillClearance") val sillClearance: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportCarSignage( // Renamed from ElevatorReportCarSignageDto
    @SerializedName("manufacturerName") val manufacturerName: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("loadCapacity") val loadCapacity: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("noSmokingSign") val noSmokingSign: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("overloadIndicator") val overloadIndicator: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("doorOpenCloseButtons") val doorOpenCloseButtons: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("floorButtons") val floorButtons: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("alarmButton") val alarmButton: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("twoWayIntercom") val twoWayIntercom: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportGovernorAndSafetyBrake( // Renamed from ElevatorReportGovernorAndSafetyBrakeDto
    @SerializedName("governorRopeClamp") val governorRopeClamp: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("governorSwitch") val governorSwitch: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("safetyBrakeSpeed") val safetyBrakeSpeed: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("safetyBrakeType") val safetyBrakeType: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("safetyBrakeMechanism") val safetyBrakeMechanism: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("progressiveSafetyBrake") val progressiveSafetyBrake: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("instantaneousSafetyBrake") val instantaneousSafetyBrake: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("safetyBrakeOperation") val safetyBrakeOperation: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("electricalCutoutSwitch") val electricalCutoutSwitch: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("limitSwitch") val limitSwitch: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("overloadDevice") val overloadDevice: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportCounterweightGuideRailsAndBuffers( // Renamed from ElevatorReportCounterweightGuideRailsAndBuffersDto
    @SerializedName("counterweightMaterial") val counterweightMaterial: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("counterweightGuardScreen") val counterweightGuardScreen: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("guideRailConstruction") val guideRailConstruction: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("bufferType") val bufferType: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("bufferFunction") val bufferFunction: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("bufferSafetySwitch") val bufferSafetySwitch: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportElectricalInstallation( // Renamed from ElevatorReportElectricalInstallationDto
    @SerializedName("installationStandard") val installationStandard: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("electricalPanel") val electricalPanel: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("backupPowerARD") val backupPowerARD: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("groundingCable") val groundingCable: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("fireAlarmConnection") val fireAlarmConnection: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("fireServiceElevator") val fireServiceElevator: ElevatorReportFireServiceElevator = ElevatorReportFireServiceElevator(),
    @SerializedName("accessibilityElevator") val accessibilityElevator: ElevatorReportAccessibilityElevator = ElevatorReportAccessibilityElevator(),
    @SerializedName("seismicSensor") val seismicSensor: ElevatorReportSeismicSensor = ElevatorReportSeismicSensor()
)

data class ElevatorReportFireServiceElevator( // Renamed from ElevatorReportFireServiceElevatorDto
    @SerializedName("backupPower") val backupPower: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("specialOperation") val specialOperation: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("fireSwitch") val fireSwitch: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("label") val label: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("electricalFireResistance") val electricalFireResistance: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("hoistwayWallFireResistance") val hoistwayWallFireResistance: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("carSize") val carSize: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("doorSize") val doorSize: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("travelTime") val travelTime: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("evacuationFloor") val evacuationFloor: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportAccessibilityElevator( // Renamed from ElevatorReportAccessibilityElevatorDto
    @SerializedName("operatingPanel") val operatingPanel: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("panelHeight") val panelHeight: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("doorOpenTime") val doorOpenTime: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("doorWidth") val doorWidth: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("audioInformation") val audioInformation: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("label") val label: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportSeismicSensor( // Renamed from ElevatorReportSeismicSensorDto
    @SerializedName("availability") val availability: ElevatorReportInspectionResult = ElevatorReportInspectionResult(),
    @SerializedName("function") val function: ElevatorReportInspectionResult = ElevatorReportInspectionResult()
)

data class ElevatorReportInspectionResult( // Renamed from ElevatorReportInspectionResultDto
    @SerializedName("result") val result: String = "",
    @SerializedName("status") val status: Boolean = false
)

data class ElevatorReportData( // Renamed from ElevatorReportDataWrapperDto
    @SerializedName("laporan") val laporan: ElevatorReportDetail = ElevatorReportDetail() // Updated type
)

data class ElevatorReportDetail( // Renamed from ElevatorReportDetailDto
    @SerializedName("id") val id: String = "",
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("extraId") val extraId: Long = 0,
    @SerializedName("generalData") val generalData: ElevatorReportGeneralData = ElevatorReportGeneralData(),
    @SerializedName("technicalDocumentInspection") val technicalDocumentInspection: ElevatorReportTechnicalDocumentInspection = ElevatorReportTechnicalDocumentInspection(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: ElevatorReportInspectionAndTesting = ElevatorReportInspectionAndTesting(),
    @SerializedName("conclusion") val conclusion: String = "",
    @SerializedName("subInspectionType") val subInspectionType: String = "",
    @SerializedName("documentType") val documentType: String = ""
)