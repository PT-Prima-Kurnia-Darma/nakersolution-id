package com.nakersolutionid.nakersolutionid.data.remote.body.report

import com.google.gson.annotations.SerializedName

data class CreateElevatorReportBody(
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("extraId") val extraId: Long = 0,
    @SerializedName("generalData") val generalData: ElevatorReportGeneralDataDto = ElevatorReportGeneralDataDto(),
    @SerializedName("technicalDocumentInspection") val technicalDocumentInspection: ElevatorReportTechnicalDocumentInspectionDto = ElevatorReportTechnicalDocumentInspectionDto(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: ElevatorReportInspectionAndTestingDto = ElevatorReportInspectionAndTestingDto(),
    @SerializedName("conclusion") val conclusion: String = ""
)

data class ElevatorReportGeneralDataDto(
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

data class ElevatorReportTechnicalDocumentInspectionDto(
    @SerializedName("designDrawing") val designDrawing: Boolean = false,
    @SerializedName("technicalCalculation") val technicalCalculation: Boolean = false,
    @SerializedName("materialCertificate") val materialCertificate: Boolean = false,
    @SerializedName("controlPanelDiagram") val controlPanelDiagram: Boolean = false,
    @SerializedName("asBuiltDrawing") val asBuiltDrawing: Boolean = false,
    @SerializedName("componentCertificates") val componentCertificates: Boolean = false,
    @SerializedName("safeWorkProcedure") val safeWorkProcedure: Boolean = false
)

data class ElevatorReportInspectionAndTestingDto(
    @SerializedName("machineRoomAndMachinery") val machineRoomAndMachinery: ElevatorReportMachineRoomAndMachineryDto = ElevatorReportMachineRoomAndMachineryDto(),
    @SerializedName("suspensionRopesAndBelts") val suspensionRopesAndBelts: ElevatorReportSuspensionRopesAndBeltsDto = ElevatorReportSuspensionRopesAndBeltsDto(),
    @SerializedName("drumsAndSheaves") val drumsAndSheaves: ElevatorReportDrumsAndSheavesDto = ElevatorReportDrumsAndSheavesDto(),
    @SerializedName("hoistwayAndPit") val hoistwayAndPit: ElevatorReportHoistwayAndPitDto = ElevatorReportHoistwayAndPitDto(),
    @SerializedName("car") val car: ElevatorReportCarDto = ElevatorReportCarDto(),
    @SerializedName("governorAndSafetyBrake") val governorAndSafetyBrake: ElevatorReportGovernorAndSafetyBrakeDto = ElevatorReportGovernorAndSafetyBrakeDto(),
    @SerializedName("counterweightGuideRailsAndBuffers") val counterweightGuideRailsAndBuffers: ElevatorReportCounterweightGuideRailsAndBuffersDto = ElevatorReportCounterweightGuideRailsAndBuffersDto(),
    @SerializedName("electricalInstallation") val electricalInstallation: ElevatorReportElectricalInstallationDto = ElevatorReportElectricalInstallationDto()
)

data class ElevatorReportMachineRoomAndMachineryDto(
    @SerializedName("machineMounting") val machineMounting: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("mechanicalBrake") val mechanicalBrake: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("electricalBrake") val electricalBrake: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("machineRoomConstruction") val machineRoomConstruction: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("machineRoomClearance") val machineRoomClearance: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("machineRoomImplementation") val machineRoomImplementation: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("ventilation") val ventilation: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("machineRoomDoor") val machineRoomDoor: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("mainPowerPanelPosition") val mainPowerPanelPosition: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("rotatingPartsGuard") val rotatingPartsGuard: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("ropeHoleGuard") val ropeHoleGuard: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("machineRoomAccessLadder") val machineRoomAccessLadder: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("floorLevelDifference") val floorLevelDifference: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("fireExtinguisher") val fireExtinguisher: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("machineRoomless") val machineRoomless: ElevatorReportMachineRoomlessDto = ElevatorReportMachineRoomlessDto(),
    @SerializedName("emergencyStopSwitch") val emergencyStopSwitch: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportMachineRoomlessDto(
    @SerializedName("panelPlacement") val panelPlacement: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("lightingWorkArea") val lightingWorkArea: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("lightingBetweenWorkArea") val lightingBetweenWorkArea: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("manualBrakeRelease") val manualBrakeRelease: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("fireExtinguisherPlacement") val fireExtinguisherPlacement: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportSuspensionRopesAndBeltsDto(
    @SerializedName("condition") val condition: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("chainUsage") val chainUsage: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("safetyFactor") val safetyFactor: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("ropeWithCounterweight") val ropeWithCounterweight: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("ropeWithoutCounterweight") val ropeWithoutCounterweight: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("belt") val belt: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("slackRopeDevice") val slackRopeDevice: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportDrumsAndSheavesDto(
    @SerializedName("drumGrooves") val drumGrooves: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("passengerDrumDiameter") val passengerDrumDiameter: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("governorDrumDiameter") val governorDrumDiameter: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportHoistwayAndPitDto(
    @SerializedName("construction") val construction: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("walls") val walls: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("inclinedElevatorTrackBed") val inclinedElevatorTrackBed: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("cleanliness") val cleanliness: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("lighting") val lighting: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("emergencyDoorNonStop") val emergencyDoorNonStop: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("emergencyDoorSize") val emergencyDoorSize: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("emergencyDoorSafetySwitch") val emergencyDoorSafetySwitch: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("emergencyDoorBridge") val emergencyDoorBridge: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carTopClearance") val carTopClearance: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("pitClearance") val pitClearance: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("pitLadder") val pitLadder: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("pitBelowWorkingArea") val pitBelowWorkingArea: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("pitAccessSwitch") val pitAccessSwitch: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("pitScreen") val pitScreen: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("hoistwayDoorLeaf") val hoistwayDoorLeaf: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("hoistwayDoorInterlock") val hoistwayDoorInterlock: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("floorLeveling") val floorLeveling: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("hoistwaySeparatorBeam") val hoistwaySeparatorBeam: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("inclinedElevatorStairs") val inclinedElevatorStairs: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportCarDto(
    @SerializedName("frame") val frame: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("body") val body: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("wallHeight") val wallHeight: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("floorArea") val floorArea: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carAreaExpansion") val carAreaExpansion: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carDoor") val carDoor: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carDoorSpecs") val carDoorSpecs: ElevatorReportCarDoorSpecsDto = ElevatorReportCarDoorSpecsDto(),
    @SerializedName("carToBeamClearance") val carToBeamClearance: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("alarmBell") val alarmBell: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("backupPowerARD") val backupPowerARD: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("intercom") val intercom: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("ventilation") val ventilation: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("emergencyLighting") val emergencyLighting: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("operatingPanel") val operatingPanel: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carPositionIndicator") val carPositionIndicator: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carSignage") val carSignage: ElevatorReportCarSignageDto = ElevatorReportCarSignageDto(),
    @SerializedName("carRoofStrength") val carRoofStrength: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carTopEmergencyExit") val carTopEmergencyExit: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carSideEmergencyExit") val carSideEmergencyExit: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carTopGuardRail") val carTopGuardRail: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("guardRailHeight300to850") val guardRailHeight300to850: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("guardRailHeightOver850") val guardRailHeightOver850: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carTopLighting") val carTopLighting: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("manualOperationButtons") val manualOperationButtons: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carInterior") val carInterior: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportCarDoorSpecsDto(
    @SerializedName("size") val size: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("lockAndSwitch") val lockAndSwitch: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("sillClearance") val sillClearance: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportCarSignageDto(
    @SerializedName("manufacturerName") val manufacturerName: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("loadCapacity") val loadCapacity: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("noSmokingSign") val noSmokingSign: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("overloadIndicator") val overloadIndicator: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("doorOpenCloseButtons") val doorOpenCloseButtons: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("floorButtons") val floorButtons: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("alarmButton") val alarmButton: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("twoWayIntercom") val twoWayIntercom: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportGovernorAndSafetyBrakeDto(
    @SerializedName("governorRopeClamp") val governorRopeClamp: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("governorSwitch") val governorSwitch: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("safetyBrakeSpeed") val safetyBrakeSpeed: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("safetyBrakeType") val safetyBrakeType: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("safetyBrakeMechanism") val safetyBrakeMechanism: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("progressiveSafetyBrake") val progressiveSafetyBrake: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("instantaneousSafetyBrake") val instantaneousSafetyBrake: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("safetyBrakeOperation") val safetyBrakeOperation: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("electricalCutoutSwitch") val electricalCutoutSwitch: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("limitSwitch") val limitSwitch: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("overloadDevice") val overloadDevice: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportCounterweightGuideRailsAndBuffersDto(
    @SerializedName("counterweightMaterial") val counterweightMaterial: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("counterweightGuardScreen") val counterweightGuardScreen: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("guideRailConstruction") val guideRailConstruction: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("bufferType") val bufferType: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("bufferFunction") val bufferFunction: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("bufferSafetySwitch") val bufferSafetySwitch: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportElectricalInstallationDto(
    @SerializedName("installationStandard") val installationStandard: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("electricalPanel") val electricalPanel: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("backupPowerARD") val backupPowerARD: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("groundingCable") val groundingCable: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("fireAlarmConnection") val fireAlarmConnection: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("fireServiceElevator") val fireServiceElevator: ElevatorReportFireServiceElevatorDto = ElevatorReportFireServiceElevatorDto(),
    @SerializedName("accessibilityElevator") val accessibilityElevator: ElevatorReportAccessibilityElevatorDto = ElevatorReportAccessibilityElevatorDto(),
    @SerializedName("seismicSensor") val seismicSensor: ElevatorReportSeismicSensorDto = ElevatorReportSeismicSensorDto()
)

data class ElevatorReportFireServiceElevatorDto(
    @SerializedName("backupPower") val backupPower: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("specialOperation") val specialOperation: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("fireSwitch") val fireSwitch: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("label") val label: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("electricalFireResistance") val electricalFireResistance: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("hoistwayWallFireResistance") val hoistwayWallFireResistance: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("carSize") val carSize: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("doorSize") val doorSize: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("travelTime") val travelTime: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("evacuationFloor") val evacuationFloor: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportAccessibilityElevatorDto(
    @SerializedName("operatingPanel") val operatingPanel: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("panelHeight") val panelHeight: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("doorOpenTime") val doorOpenTime: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("doorWidth") val doorWidth: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("audioInformation") val audioInformation: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("label") val label: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportSeismicSensorDto(
    @SerializedName("availability") val availability: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto(),
    @SerializedName("function") val function: ElevatorReportInspectionResultDto = ElevatorReportInspectionResultDto()
)

data class ElevatorReportInspectionResultDto(
    @SerializedName("result") val result: String = "",
    @SerializedName("status") val status: Boolean = false
)