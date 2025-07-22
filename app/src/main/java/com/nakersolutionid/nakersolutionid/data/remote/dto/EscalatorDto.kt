package com.nakersolutionid.nakersolutionid.data.remote.dto

import com.google.gson.annotations.SerializedName

// --- Create Escalator Report DTOs ---

data class CreateEscalatorReportBody(
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("generalData") val generalData: EscalatorReportGeneralData = EscalatorReportGeneralData(),
    @SerializedName("technicalData") val technicalData: EscalatorReportTechnicalData = EscalatorReportTechnicalData(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: EscalatorReportInspectionAndTesting = EscalatorReportInspectionAndTesting(),
    @SerializedName("testingEscalator") val testingEscalator: String = "",
    @SerializedName("conclusion") val conclusion: String = ""
)

data class CreateEscalatorReportResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: EscalatorReportData = EscalatorReportData()
)

// --- Get Escalator Reports DTOs ---

data class GetEscalatorReportsResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: EscalatorReportsResponseData = EscalatorReportsResponseData()
)

data class EscalatorReportsResponseData(
    @SerializedName("laporan") val listLaporan: List<EscalatorReportDetail> = emptyList()
)

// --- Delete Escalator Report DTOs ---

data class DeleteEscalatorReportResponse(
    @SerializedName("message") val message: String = "",
    @SerializedName("status") val status: String = ""
)

// --- Core Escalator Report Data DTOs ---

data class EscalatorReportData(
    @SerializedName("laporan") val laporan: EscalatorReportDetail = EscalatorReportDetail()
)

// UPDATED EscalatorReportDetail to include 'id', 'subInspectionType', and 'documentType'
data class EscalatorReportDetail(
    @SerializedName("id") val id: String = "",
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("generalData") val generalData: EscalatorReportGeneralData = EscalatorReportGeneralData(),
    @SerializedName("technicalData") val technicalData: EscalatorReportTechnicalData = EscalatorReportTechnicalData(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: EscalatorReportInspectionAndTesting = EscalatorReportInspectionAndTesting(),
    @SerializedName("testingEscalator") val testingEscalator: String = "",
    @SerializedName("conclusion") val conclusion: String = "",
    @SerializedName("subInspectionType") val subInspectionType: String = "", // Added based on response JSON
    @SerializedName("documentType") val documentType: String = "" // Added based on response JSON
)

// --- General and Technical Data DTOs (from original Escalator JSON) ---

data class EscalatorReportGeneralData(
    @SerializedName("ownerName") val ownerName: String = "",
    @SerializedName("nameUsageLocation") val nameUsageLocation: String = "",
    @SerializedName("safetyObjectTypeAndNumber") val safetyObjectTypeAndNumber: String = "",
    @SerializedName("intendedUse") val intendedUse: String = "",
    @SerializedName("permitNumber") val permitNumber: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("inspectionDate") val inspectionDate: String = ""
)

data class EscalatorReportTechnicalData(
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

// --- Inspection and Testing DTOs (from original Escalator JSON) ---

data class EscalatorReportInspectionAndTesting(
    @SerializedName("inspectionAndTestingframeAndMachineRoom") val frameAndMachineRoom: EscalatorReportFrameAndMachineRoom = EscalatorReportFrameAndMachineRoom(),
    @SerializedName("driveEquipment") val driveEquipment: EscalatorReportDriveEquipment = EscalatorReportDriveEquipment(),
    @SerializedName("stepsOrPallets") val stepsOrPallets: EscalatorReportStepsOrPallets = EscalatorReportStepsOrPallets(),
    @SerializedName("landingArea") val landingArea: EscalatorReportLandingArea = EscalatorReportLandingArea(),
    @SerializedName("balustrade") val balustrade: EscalatorReportBalustrade = EscalatorReportBalustrade(),
    @SerializedName("handrail") val handrail: EscalatorReportHandrail = EscalatorReportHandrail(),
    @SerializedName("runway") val runway: EscalatorReportRunway = EscalatorReportRunway(),
    @SerializedName("safetyEquipment") val safetyEquipment: EscalatorReportSafetyEquipment = EscalatorReportSafetyEquipment(),
    @SerializedName("electricalInstallation") val electricalInstallation: EscalatorReportElectricalInstallation = EscalatorReportElectricalInstallation(),
    @SerializedName("outdoorSpecifics") val outdoorSpecifics: EscalatorReportOutdoorSpecifics = EscalatorReportOutdoorSpecifics(),
    @SerializedName("userSafety") val userSafety: EscalatorReportUserSafety = EscalatorReportUserSafety()
)

data class EscalatorReportFrameAndMachineRoom(
    @SerializedName("inspectionAndTestingframeAndMachineRoomframeresult") val frameResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("inspectionAndTestingframeAndMachineRoomsupportBeamsresults") val supportBeamsResults: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineRoomConditionresult") val machineRoomConditionResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineRoomClearanceresult") val machineRoomClearanceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineRoomLightingresult") val machineRoomLightingResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("inspectionAndTestingframeAndMachineRoommachineCoverPlateresult") val machineCoverPlateResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("inspectionAndTestingframeAndMachineRoompitConditionresult") val pitConditionResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("inspectionAndTestingframeAndMachineRoompitClearanceresult") val pitClearanceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("inspectionAndTestingframeAndMachineRoompitStepCoverPlateresult") val pitStepCoverPlateResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportDriveEquipment(
    @SerializedName("driveEquipmentdriveMachineresult") val driveMachineResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("driveEquipmentspeedUnder30Degreesresult") val speedUnder30DegreesResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("driveEquipmentspeed30to35Degreesresult") val speed30to35DegreesResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("driveEquipmenttravelatorSpeedresult") val travelatorSpeedResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("driveEquipmentstoppingDistance0_5result") val stoppingDistance05Result: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("driveEquipmentstoppingDistance0_75result") val stoppingDistance075Result: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("driveEquipmentstoppingDistance0_90result") val stoppingDistance090Result: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("driveEquipmentdriveChainresult") val driveChainResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("driveEquipmentchainBreakingStrengthresult") val chainBreakingStrengthResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportStepsOrPallets(
    @SerializedName("stepsOrPalletsstepMaterialresult") val stepMaterialResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("stepsOrPalletsstepDimensionsresult") val stepDimensionsResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("stepsOrPalletspalletDimensionsresult") val palletDimensionsResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("stepsOrPalletsstepSurfaceresult") val stepSurfaceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("stepsOrPalletsstepLevelnessresult") val stepLevelnessResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("stepsOrPalletsskirtBrushresult") val skirtBrushResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("stepsOrPalletsstepWheelsresult") val stepWheelsResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportLandingArea(
    @SerializedName("landingArealandingPlatesresult") val landingPlatesResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("landingAreacombTeethresult") val combTeethResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("landingAreacombConditionresult") val combConditionResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("landingArealandingCoverresult") val landingCoverResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("landingArealandingAccessArearesult") val landingAccessAreaResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportBalustrade(
    @SerializedName("balustradebalustradePanelmaterialresult") val balustradePanelMaterialResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("balustradebalustradePanelheightresult") val balustradePanelHeightResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("balustradebalustradePanelsidePressureresult") val balustradePanelSidePressureResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("balustradebalustradePanelverticalPressureresult") val balustradePanelVerticalPressureResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("balustradeskirtPanelresult") val skirtPanelResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("balustradeskirtPanelFlexibilityresult") val skirtPanelFlexibilityResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("balustradestepToSkirtClearanceresult") val stepToSkirtClearanceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportHandrail(
    @SerializedName("handrailhandrailConditionresult") val handrailConditionResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("handrailhandrailSpeedSynchronizationresult") val handrailSpeedSynchronizationResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("handrailhandrailWidthresult") val handrailWidthResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportRunway(
    @SerializedName("runwaybeamStrengthAndPositionresult") val beamStrengthAndPositionResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("runwaypitWallConditionresult") val pitWallConditionResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("runwayescalatorFrameEnclosureresult") val escalatorFrameEnclosureResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("runwaylightingresult") val lightingResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("runwayheadroomClearanceresult") val headroomClearanceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("runwaybalustradeToObjectClearanceresult") val balustradeToObjectClearanceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("runwayantiClimbDeviceHeightresult") val antiClimbDeviceHeightResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("runwayornamentPlacementresult") val ornamentPlacementResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("runwayoutdoorClearanceresult") val outdoorClearanceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportSafetyEquipment(
    @SerializedName("safetyEquipmentoperationControlKeyresult") val operationControlKeyResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmentemergencyStopSwitchresult") val emergencyStopSwitchResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmentstepChainSafetyDeviceresult") val stepChainSafetyDeviceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmentdriveChainSafetyDeviceresult") val driveChainSafetyDeviceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmentstepSafetyDeviceresult") val stepSafetyDeviceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmenthandrailSafetyDeviceresult") val handrailSafetyDeviceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmentreversalStopDeviceresult") val reversalStopDeviceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmenthandrailEntryGuardresult") val handrailEntryGuardResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmentcombPlateSafetyDeviceresult") val combPlateSafetyDeviceResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmentinnerDeckingBrushresult") val innerDeckingBrushResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("safetyEquipmentstopButtonsresult") val stopButtonsResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportElectricalInstallation(
    @SerializedName("electricalInstallationinstallationStandardresult") val installationStandardResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("electricalInstallationelectricalPanelresult") val electricalPanelResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("electricalInstallationgroundingCableresult") val groundingCableResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("electricalInstallationfireAlarmConnectionresult") val fireAlarmConnectionResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportOutdoorSpecifics(
    @SerializedName("outdoorSpecificspitWaterPumpresult") val pitWaterPumpResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("outdoorSpecificsweatherproofComponentsresult") val weatherproofComponentsResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportUserSafety(
    @SerializedName("userSafetySignagenoBulkyItemsresult") val signageNoBulkyItemsResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("userSafetySignagenoJumpingresult") val signageNoJumpingResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("userSafetySignageunattendedChildrenresult") val signageUnattendedChildrenResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("userSafetySignagenoTrolleysOrStrollersresult") val signageNoTrolleysOrStrollersResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("userSafetySignagenoLeaningresult") val signageNoLeaningResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("userSafetySignagenoSteppingOnSkirtresult") val signageNoSteppingOnSkirtResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("userSafetySignagesoftSoleFootwearWarningresult") val signageSoftSoleFootwearWarningResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("userSafetySignagenoSittingOnStepsresult") val signageNoSittingOnStepsResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult(),
    @SerializedName("userSafetySignageholdHandrailresult") val signageHoldHandrailResult: EscalatorReportInspectionResult = EscalatorReportInspectionResult()
)

data class EscalatorReportInspectionResult(
    @SerializedName("result") val result: String = "",
    @SerializedName("status") val status: Boolean = false
)