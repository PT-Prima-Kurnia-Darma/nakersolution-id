package com.nakersolutionid.nakersolutionid.data.remote.dto.escalator

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus

data class EscalatorReportRequest(
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: EscalatorGeneralData,
    @SerializedName("technicalData") val technicalData: EscalatorTechnicalData,
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: EscalatorInspectionAndTesting,
    @SerializedName("testingEscalator") val testingEscalator: String,
    @SerializedName("conclusion") val conclusion: String,
)

// Data DTO for single Escalator Report response
data class EscalatorSingleReportResponseData(
    @SerializedName("laporan") val laporan: EscalatorReportData
)

// Data DTO for list of Escalator Reports response
data class EscalatorListReportResponseData(
    @SerializedName("laporan") val laporan: List<EscalatorReportData>
)

// Main DTO for Escalator Report (used for create, update, and individual get)
data class EscalatorReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: EscalatorGeneralData,
    @SerializedName("technicalData") val technicalData: EscalatorTechnicalData,
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: EscalatorInspectionAndTesting,
    @SerializedName("testingEscalator") val testingEscalator: String,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class EscalatorGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("nameUsageLocation") val nameUsageLocation: String,
    @SerializedName("safetyObjectTypeAndNumber") val safetyObjectTypeAndNumber: String,
    @SerializedName("intendedUse") val intendedUse: String,
    @SerializedName("permitNumber") val permitNumber: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String
)

data class EscalatorTechnicalData(
    @SerializedName("technicalDatamanufacturer") val manufacturer: String,
    @SerializedName("technicalDatabrand") val brand: String,
    @SerializedName("technicalDatacountryAndYear") val countryAndYear: String,
    @SerializedName("technicalDataserialNumber") val serialNumber: String,
    @SerializedName("technicalDatatransports") val transports: String,
    @SerializedName("technicalDatacapacity") val capacity: String,
    @SerializedName("technicalDataliftHeight") val liftHeight: String,
    @SerializedName("technicalDataspeed") val speed: String,
    @SerializedName("technicalDatadriveType") val driveType: String,
    @SerializedName("technicalDatamotorCurrent") val motorCurrent: String,
    @SerializedName("technicalDatamotorPower") val motorPower: String,
    @SerializedName("technicalDatasafetyDevices") val safetyDevices: String
)

data class EscalatorInspectionAndTesting(
    @SerializedName("inspectionAndTestingframeAndMachineRoom") val frameAndMachineRoom: EscalatorFrameAndMachineRoom,
    @SerializedName("driveEquipment") val driveEquipment: EscalatorDriveEquipment,
    @SerializedName("stepsOrPallets") val stepsOrPallets: EscalatorStepsOrPallets,
    @SerializedName("landingArea") val landingArea: EscalatorLandingArea,
    @SerializedName("balustrade") val balustrade: EscalatorBalustrade,
    @SerializedName("handrail") val handrail: EscalatorHandrail,
    @SerializedName("runway") val runway: EscalatorRunway,
    @SerializedName("safetyEquipment") val safetyEquipment: EscalatorSafetyEquipment,
    @SerializedName("electricalInstallation") val electricalInstallation: EscalatorElectricalInstallation,
    @SerializedName("outdoorSpecifics") val outdoorSpecifics: EscalatorOutdoorSpecifics,
    @SerializedName("userSafety") val userSafety: EscalatorUserSafety
)

data class EscalatorFrameAndMachineRoom(
    @SerializedName("inspectionAndTestingframeAndMachineRoomframeresult") val frameResult: ResultStatus,
    @SerializedName("inspectionAndTestingframeAndMachineRoomsupportBeamsresults") val supportBeamsResults: ResultStatus,
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineRoomConditionresult") val machineRoomConditionResult: ResultStatus,
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineRoomClearanceresult") val machineRoomClearanceResult: ResultStatus,
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineRoomLightingresult") val machineRoomLightingResult: ResultStatus,
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineCoverPlateresult") val machineCoverPlateResult: ResultStatus,
    @SerializedName("inspectionAndTestingframeAndMachineRoompitConditionresult") val pitConditionResult: ResultStatus,
    @SerializedName("inspectionAndTestingframeAndMachineRoompitClearanceresult") val pitClearanceResult: ResultStatus,
    @SerializedName("inspectionAndTestingframeAndMachineRoompitStepCoverPlateresult") val pitStepCoverPlateResult: ResultStatus
)

data class EscalatorDriveEquipment(
    @SerializedName("driveEquipmentdriveMachineresult") val driveMachineResult: ResultStatus,
    @SerializedName("driveEquipmentspeedUnder30Degreesresult") val speedUnder30DegreesResult: ResultStatus,
    @SerializedName("driveEquipmentspeed30to35Degreesresult") val speed30to35DegreesResult: ResultStatus,
    @SerializedName("driveEquipmenttravelatorSpeedresult") val travelatorSpeedResult: ResultStatus,
    @SerializedName("driveEquipmentstoppingDistance0_5result") val stoppingDistance0_5Result: ResultStatus,
    @SerializedName("driveEquipmentstoppingDistance0_75result") val stoppingDistance0_75Result: ResultStatus,
    @SerializedName("driveEquipmentstoppingDistance0_90result") val stoppingDistance0_90Result: ResultStatus,
    @SerializedName("driveEquipmentdriveChainresult") val driveChainResult: ResultStatus,
    @SerializedName("driveEquipmentchainBreakingStrengthresult") val chainBreakingStrengthResult: ResultStatus
)

data class EscalatorStepsOrPallets(
    @SerializedName("stepsOrPalletsstepMaterialresult") val stepMaterialResult: ResultStatus,
    @SerializedName("stepsOrPalletsstepDimensionsresult") val stepDimensionsResult: ResultStatus,
    @SerializedName("stepsOrPalletspalletDimensionsresult") val palletDimensionsResult: ResultStatus,
    @SerializedName("stepsOrPalletsstepSurfaceresult") val stepSurfaceResult: ResultStatus,
    @SerializedName("stepsOrPalletsstepLevelnessresult") val stepLevelnessResult: ResultStatus,
    @SerializedName("stepsOrPalletsskirtBrushresult") val skirtBrushResult: ResultStatus,
    @SerializedName("stepsOrPalletsstepWheelsresult") val stepWheelsResult: ResultStatus
)

data class EscalatorLandingArea(
    @SerializedName("landingArealandingPlatesresult") val landingPlatesResult: ResultStatus,
    @SerializedName("landingAreacombTeethresult") val combTeethResult: ResultStatus,
    @SerializedName("landingAreacombConditionresult") val combConditionResult: ResultStatus,
    @SerializedName("landingArealandingCoverresult") val landingCoverResult: ResultStatus,
    @SerializedName("landingArealandingAccessArearesult") val landingAccessAreaResult: ResultStatus
)

data class EscalatorBalustrade(
    @SerializedName("balustradebalustradePanelmaterialresult") val balustradePanelMaterialResult: ResultStatus,
    @SerializedName("balustradebalustradePanelheightresult") val balustradePanelHeightResult: ResultStatus,
    @SerializedName("balustradebalustradePanelsidePressureresult") val balustradePanelSidePressureResult: ResultStatus,
    @SerializedName("balustradebalustradePanelverticalPressureresult") val balustradePanelVerticalPressureResult: ResultStatus,
    @SerializedName("balustradeskirtPanelresult") val skirtPanelResult: ResultStatus,
    @SerializedName("balustradeskirtPanelFlexibilityresult") val skirtPanelFlexibilityResult: ResultStatus,
    @SerializedName("balustradestepToSkirtClearanceresult") val stepToSkirtClearanceResult: ResultStatus
)

data class EscalatorHandrail(
    @SerializedName("handrailhandrailConditionresult") val handrailConditionResult: ResultStatus,
    @SerializedName("handrailhandrailSpeedSynchronizationresult") val handrailSpeedSynchronizationResult: ResultStatus,
    @SerializedName("handrailhandrailWidthresult") val handrailWidthResult: ResultStatus
)

data class EscalatorRunway(
    @SerializedName("runwaybeamStrengthAndPositionresult") val beamStrengthAndPositionResult: ResultStatus,
    @SerializedName("runwaypitWallConditionresult") val pitWallConditionResult: ResultStatus,
    @SerializedName("runwayescalatorFrameEnclosureresult") val escalatorFrameEnclosureResult: ResultStatus,
    @SerializedName("runwaylightingresult") val lightingResult: ResultStatus,
    @SerializedName("runwayheadroomClearanceresult") val headroomClearanceResult: ResultStatus,
    @SerializedName("runwaybalustradeToObjectClearanceresult") val balustradeToObjectClearanceResult: ResultStatus,
    @SerializedName("runwayantiClimbDeviceHeightresult") val antiClimbDeviceHeightResult: ResultStatus,
    @SerializedName("runwayornamentPlacementresult") val ornamentPlacementResult: ResultStatus,
    @SerializedName("runwayoutdoorClearanceresult") val outdoorClearanceResult: ResultStatus
)

data class EscalatorSafetyEquipment(
    @SerializedName("safetyEquipmentoperationControlKeyresult") val operationControlKeyResult: ResultStatus,
    @SerializedName("safetyEquipmentemergencyStopSwitchresult") val emergencyStopSwitchResult: ResultStatus,
    @SerializedName("safetyEquipmentstepChainSafetyDeviceresult") val stepChainSafetyDeviceResult: ResultStatus,
    @SerializedName("safetyEquipmentdriveChainSafetyDeviceresult") val driveChainSafetyDeviceResult: ResultStatus,
    @SerializedName("safetyEquipmentstepSafetyDeviceresult") val stepSafetyDeviceResult: ResultStatus,
    @SerializedName("safetyEquipmenthandrailSafetyDeviceresult") val handrailSafetyDeviceResult: ResultStatus,
    @SerializedName("safetyEquipmentreversalStopDeviceresult") val reversalStopDeviceResult: ResultStatus,
    @SerializedName("safetyEquipmenthandrailEntryGuardresult") val handrailEntryGuardResult: ResultStatus,
    @SerializedName("safetyEquipmentcombPlateSafetyDeviceresult") val combPlateSafetyDeviceResult: ResultStatus,
    @SerializedName("safetyEquipmentinnerDeckingBrushresult") val innerDeckingBrushResult: ResultStatus,
    @SerializedName("safetyEquipmentstopButtonsresult") val stopButtonsResult: ResultStatus
)

data class EscalatorElectricalInstallation(
    @SerializedName("electricalInstallationinstallationStandardresult") val installationStandardResult: ResultStatus,
    @SerializedName("electricalInstallationelectricalPanelresult") val electricalPanelResult: ResultStatus,
    @SerializedName("electricalInstallationgroundingCableresult") val groundingCableResult: ResultStatus,
    @SerializedName("electricalInstallationfireAlarmConnectionresult") val fireAlarmConnectionResult: ResultStatus
)

data class EscalatorOutdoorSpecifics(
    @SerializedName("outdoorSpecificspitWaterPumpresult") val pitWaterPumpResult: ResultStatus,
    @SerializedName("outdoorSpecificsweatherproofComponentsresult") val weatherproofComponentsResult: ResultStatus
)

data class EscalatorUserSafety(
    @SerializedName("userSafetySignagenoBulkyItemsresult") val noBulkyItemsResult: ResultStatus,
    @SerializedName("userSafetySignagenoJumpingresult") val noJumpingResult: ResultStatus,
    @SerializedName("userSafetySignageunattendedChildrenresult") val unattendedChildrenResult: ResultStatus,
    @SerializedName("userSafetySignagenoTrolleysOrStrollersresult") val noTrolleysOrStrollersResult: ResultStatus,
    @SerializedName("userSafetySignagenoLeaningresult") val noLeaningResult: ResultStatus,
    @SerializedName("userSafetySignagenoSteppingOnSkirtresult") val noSteppingOnSkirtResult: ResultStatus,
    @SerializedName("userSafetySignagesoftSoleFootwearWarningresult") val softSoleFootwearWarningResult: ResultStatus,
    @SerializedName("userSafetySignagenoSittingOnStepsresult") val noSittingOnStepsResult: ResultStatus,
    @SerializedName("userSafetySignageholdHandrailresult") val holdHandrailResult: ResultStatus
)
