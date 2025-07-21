package com.nakersolutionid.nakersolutionid.data.remote.body.report

import com.google.gson.annotations.SerializedName

data class CreateEscalatorReportBody(
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("generalData") val generalData: EscalatorReportGeneralDataDto = EscalatorReportGeneralDataDto(),
    @SerializedName("technicalData") val technicalData: EscalatorReportTechnicalDataDto = EscalatorReportTechnicalDataDto(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: EscalatorReportInspectionAndTestingDto = EscalatorReportInspectionAndTestingDto(),
    @SerializedName("testingEscalator") val testingEscalator: String = "",
    @SerializedName("conclusion") val conclusion: String = ""
)

data class EscalatorReportGeneralDataDto(
    @SerializedName("ownerName") val ownerName: String = "",
    @SerializedName("nameUsageLocation") val nameUsageLocation: String = "",
    @SerializedName("safetyObjectTypeAndNumber") val safetyObjectTypeAndNumber: String = "",
    @SerializedName("intendedUse") val intendedUse: String = "",
    @SerializedName("permitNumber") val permitNumber: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("inspectionDate") val inspectionDate: String = ""
)

data class EscalatorReportTechnicalDataDto(
    @SerializedName("technicalDatamanufacturer") val technicalDatamanufacturer: String = "",
    @SerializedName("technicalDatabrand") val technicalDatabrand: String = "",
    @SerializedName("technicalDatacountryAndYear") val technicalDatacountryAndYear: String = "",
    @SerializedName("technicalDataserialNumber") val technicalDataserialNumber: String = "",
    @SerializedName("technicalDatatransports") val technicalDatatransports: String = "",
    @SerializedName("technicalDatacapacity") val technicalDatacapacity: String = "",
    @SerializedName("technicalDataliftHeight") val technicalDataliftHeight: String = "",
    @SerializedName("technicalDataspeed") val technicalDataspeed: String = "",
    @SerializedName("technicalDatadriveType") val technicalDatadriveType: String = "",
    @SerializedName("technicalDatamotorCurrent") val technicalDatamotorCurrent: String = "",
    @SerializedName("technicalDatamotorPower") val technicalDatamotorPower: String = "",
    @SerializedName("technicalDatasafetyDevices") val technicalDatasafetyDevices: String = ""
)

data class EscalatorReportInspectionAndTestingDto(
    @SerializedName("inspectionAndTestingframeAndMachineRoom") val inspectionAndTestingframeAndMachineRoom: EscalatorReportFrameAndMachineRoomDto = EscalatorReportFrameAndMachineRoomDto(),
    @SerializedName("driveEquipment") val driveEquipment: EscalatorReportDriveEquipmentDto = EscalatorReportDriveEquipmentDto(),
    @SerializedName("stepsOrPallets") val stepsOrPallets: EscalatorReportStepsOrPalletsDto = EscalatorReportStepsOrPalletsDto(),
    @SerializedName("landingArea") val landingArea: EscalatorReportLandingAreaDto = EscalatorReportLandingAreaDto(),
    @SerializedName("balustrade") val balustrade: EscalatorReportBalustradeDto = EscalatorReportBalustradeDto(),
    @SerializedName("handrail") val handrail: EscalatorReportHandrailDto = EscalatorReportHandrailDto(),
    @SerializedName("runway") val runway: EscalatorReportRunwayDto = EscalatorReportRunwayDto(),
    @SerializedName("safetyEquipment") val safetyEquipment: EscalatorReportSafetyEquipmentDto = EscalatorReportSafetyEquipmentDto(),
    @SerializedName("electricalInstallation") val electricalInstallation: EscalatorReportElectricalInstallationDto = EscalatorReportElectricalInstallationDto(),
    @SerializedName("outdoorSpecifics") val outdoorSpecifics: EscalatorReportOutdoorSpecificsDto = EscalatorReportOutdoorSpecificsDto(),
    @SerializedName("userSafety") val userSafety: EscalatorReportUserSafetyDto = EscalatorReportUserSafetyDto()
)

data class EscalatorReportFrameAndMachineRoomDto(
    @SerializedName("inspectionAndTestingframeAndMachineRoomframeresult") val frameResult: ResultDto = ResultDto(),
    @SerializedName("inspectionAndTestingframeAndMachineRoomsupportBeamsresults") val supportBeamsResults: ResultDto = ResultDto(),
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineRoomConditionresult") val machineRoomConditionResult: ResultDto = ResultDto(),
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineRoomClearanceresult") val machineRoomClearanceResult: ResultDto = ResultDto(),
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineRoomLightingresult") val machineRoomLightingResult: ResultDto = ResultDto(),
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineCoverPlateresult") val machineCoverPlateResult: ResultDto = ResultDto(),
    @SerializedName("inspectionAndTestingframeAndMachineRoompitConditionresult") val pitConditionResult: ResultDto = ResultDto(),
    @SerializedName("inspectionAndTestingframeAndMachineRoompitClearanceresult") val pitClearanceResult: ResultDto = ResultDto(),
    @SerializedName("inspectionAndTestingframeAndMachineRoompitStepCoverPlateresult") val pitStepCoverPlateResult: ResultDto = ResultDto()
)

data class EscalatorReportDriveEquipmentDto(
    @SerializedName("driveEquipmentdriveMachineresult") val driveMachineResult: ResultDto = ResultDto(),
    @SerializedName("driveEquipmentspeedUnder30Degreesresult") val speedUnder30DegreesResult: ResultDto = ResultDto(),
    @SerializedName("driveEquipmentspeed30to35Degreesresult") val speed30to35DegreesResult: ResultDto = ResultDto(),
    @SerializedName("driveEquipmenttravelatorSpeedresult") val travelatorSpeedResult: ResultDto = ResultDto(),
    @SerializedName("driveEquipmentstoppingDistance0_5result") val stoppingDistance05Result: ResultDto = ResultDto(),
    @SerializedName("driveEquipmentstoppingDistance0_75result") val stoppingDistance075Result: ResultDto = ResultDto(),
    @SerializedName("driveEquipmentstoppingDistance0_90result") val stoppingDistance090Result: ResultDto = ResultDto(),
    @SerializedName("driveEquipmentdriveChainresult") val driveChainResult: ResultDto = ResultDto(),
    @SerializedName("driveEquipmentchainBreakingStrengthresult") val chainBreakingStrengthResult: ResultDto = ResultDto()
)

data class EscalatorReportStepsOrPalletsDto(
    @SerializedName("stepsOrPalletsstepMaterialresult") val stepMaterialResult: ResultDto = ResultDto(),
    @SerializedName("stepsOrPalletsstepDimensionsresult") val stepDimensionsResult: ResultDto = ResultDto(),
    @SerializedName("stepsOrPalletspalletDimensionsresult") val palletDimensionsResult: ResultDto = ResultDto(),
    @SerializedName("stepsOrPalletsstepSurfaceresult") val stepSurfaceResult: ResultDto = ResultDto(),
    @SerializedName("stepsOrPalletsstepLevelnessresult") val stepLevelnessResult: ResultDto = ResultDto(),
    @SerializedName("stepsOrPalletsskirtBrushresult") val skirtBrushResult: ResultDto = ResultDto(),
    @SerializedName("stepsOrPalletsstepWheelsresult") val stepWheelsResult: ResultDto = ResultDto()
)

data class EscalatorReportLandingAreaDto(
    @SerializedName("landingArealandingPlatesresult") val landingPlatesResult: ResultDto = ResultDto(),
    @SerializedName("landingAreacombTeethresult") val combTeethResult: ResultDto = ResultDto(),
    @SerializedName("landingAreacombConditionresult") val combConditionResult: ResultDto = ResultDto(),
    @SerializedName("landingArealandingCoverresult") val landingCoverResult: ResultDto = ResultDto(),
    @SerializedName("landingArealandingAccessArearesult") val landingAccessAreaResult: ResultDto = ResultDto()
)

data class EscalatorReportBalustradeDto(
    @SerializedName("balustradebalustradePanelmaterialresult") val balustradePanelMaterialResult: ResultDto = ResultDto(),
    @SerializedName("balustradebalustradePanelheightresult") val balustradePanelHeightResult: ResultDto = ResultDto(),
    @SerializedName("balustradebalustradePanelsidePressureresult") val balustradePanelSidePressureResult: ResultDto = ResultDto(),
    @SerializedName("balustradebalustradePanelverticalPressureresult") val balustradePanelVerticalPressureResult: ResultDto = ResultDto(),
    @SerializedName("balustradeskirtPanelresult") val skirtPanelResult: ResultDto = ResultDto(),
    @SerializedName("balustradeskirtPanelFlexibilityresult") val skirtPanelFlexibilityResult: ResultDto = ResultDto(),
    @SerializedName("balustradestepToSkirtClearanceresult") val stepToSkirtClearanceResult: ResultDto = ResultDto()
)

data class EscalatorReportHandrailDto(
    @SerializedName("handrailhandrailConditionresult") val handrailConditionResult: ResultDto = ResultDto(),
    @SerializedName("handrailhandrailSpeedSynchronizationresult") val handrailSpeedSynchronizationResult: ResultDto = ResultDto(),
    @SerializedName("handrailhandrailWidthresult") val handrailWidthResult: ResultDto = ResultDto()
)

data class EscalatorReportRunwayDto(
    @SerializedName("runwaybeamStrengthAndPositionresult") val beamStrengthAndPositionResult: ResultDto = ResultDto(),
    @SerializedName("runwaypitWallConditionresult") val pitWallConditionResult: ResultDto = ResultDto(),
    @SerializedName("runwayescalatorFrameEnclosureresult") val escalatorFrameEnclosureResult: ResultDto = ResultDto(),
    @SerializedName("runwaylightingresult") val lightingResult: ResultDto = ResultDto(),
    @SerializedName("runwayheadroomClearanceresult") val headroomClearanceResult: ResultDto = ResultDto(),
    @SerializedName("runwaybalustradeToObjectClearanceresult") val balustradeToObjectClearanceResult: ResultDto = ResultDto(),
    @SerializedName("runwayantiClimbDeviceHeightresult") val antiClimbDeviceHeightResult: ResultDto = ResultDto(),
    @SerializedName("runwayornamentPlacementresult") val ornamentPlacementResult: ResultDto = ResultDto(),
    @SerializedName("runwayoutdoorClearanceresult") val outdoorClearanceResult: ResultDto = ResultDto()
)

data class EscalatorReportSafetyEquipmentDto(
    @SerializedName("safetyEquipmentoperationControlKeyresult") val operationControlKeyResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmentemergencyStopSwitchresult") val emergencyStopSwitchResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmentstepChainSafetyDeviceresult") val stepChainSafetyDeviceResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmentdriveChainSafetyDeviceresult") val driveChainSafetyDeviceResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmentstepSafetyDeviceresult") val stepSafetyDeviceResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmenthandrailSafetyDeviceresult") val handrailSafetyDeviceResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmentreversalStopDeviceresult") val reversalStopDeviceResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmenthandrailEntryGuardresult") val handrailEntryGuardResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmentcombPlateSafetyDeviceresult") val combPlateSafetyDeviceResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmentinnerDeckingBrushresult") val innerDeckingBrushResult: ResultDto = ResultDto(),
    @SerializedName("safetyEquipmentstopButtonsresult") val stopButtonsResult: ResultDto = ResultDto()
)

data class EscalatorReportElectricalInstallationDto(
    @SerializedName("electricalInstallationinstallationStandardresult") val installationStandardResult: ResultDto = ResultDto(),
    @SerializedName("electricalInstallationelectricalPanelresult") val electricalPanelResult: ResultDto = ResultDto(),
    @SerializedName("electricalInstallationgroundingCableresult") val groundingCableResult: ResultDto = ResultDto(),
    @SerializedName("electricalInstallationfireAlarmConnectionresult") val fireAlarmConnectionResult: ResultDto = ResultDto()
)

data class EscalatorReportOutdoorSpecificsDto(
    @SerializedName("outdoorSpecificspitWaterPumpresult") val pitWaterPumpResult: ResultDto = ResultDto(),
    @SerializedName("outdoorSpecificsweatherproofComponentsresult") val weatherproofComponentsResult: ResultDto = ResultDto()
)

data class EscalatorReportUserSafetyDto(
    @SerializedName("userSafetySignagenoBulkyItemsresult") val signageNoBulkyItemsResult: ResultDto = ResultDto(),
    @SerializedName("userSafetySignagenoJumpingresult") val signageNoJumpingResult: ResultDto = ResultDto(),
    @SerializedName("userSafetySignageunattendedChildrenresult") val signageUnattendedChildrenResult: ResultDto = ResultDto(),
    @SerializedName("userSafetySignagenoTrolleysOrStrollersresult") val signageNoTrolleysOrStrollersResult: ResultDto = ResultDto(),
    @SerializedName("userSafetySignagenoLeaningresult") val signageNoLeaningResult: ResultDto = ResultDto(),
    @SerializedName("userSafetySignagenoSteppingOnSkirtresult") val signageNoSteppingOnSkirtResult: ResultDto = ResultDto(),
    @SerializedName("userSafetySignagesoftSoleFootwearWarningresult") val signageSoftSoleFootwearWarningResult: ResultDto = ResultDto(),
    @SerializedName("userSafetySignagenoSittingOnStepsresult") val signageNoSittingOnStepsResult: ResultDto = ResultDto(),
    @SerializedName("userSafetySignageholdHandrailresult") val signageHoldHandrailResult: ResultDto = ResultDto()
)

data class ResultDto(
    @SerializedName("result") val result: String = "",
    @SerializedName("status") val status: Boolean = false
)