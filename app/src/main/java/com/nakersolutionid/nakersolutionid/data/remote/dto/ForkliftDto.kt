package com.nakersolutionid.nakersolutionid.data.remote.dto

import com.google.gson.annotations.SerializedName

// --- Create Forklift Report DTOs ---

data class CreateForkliftReportBody(
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("inspectionDate") val inspectionDate: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("generalData") val generalData: ForkliftReportGeneralData = ForkliftReportGeneralData(),
    @SerializedName("technicalData") val technicalData: ForkliftReportTechnicalData = ForkliftReportTechnicalData(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: ForkliftReportInspectionAndTesting = ForkliftReportInspectionAndTesting(),
    @SerializedName("testingForklift") val testingForklift: ForkliftReportTestingForklift = ForkliftReportTestingForklift(),
    @SerializedName("conclusion") val conclusion: String = "",
    @SerializedName("recommendation") val recommendation: String = ""
)

data class CreateForkliftReportResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: ForkliftReportData = ForkliftReportData()
)

// --- Get Forklift Reports DTOs ---

data class GetForkliftReportsResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: ForkliftReportsResponseData = ForkliftReportsResponseData()
)

data class ForkliftReportsResponseData(
    @SerializedName("laporan") val listLaporan: List<ForkliftReportDetail> = emptyList()
)

// --- Delete Forklift Report DTOs ---

data class DeleteForkliftReportResponse(
    @SerializedName("message") val message: String = "",
    @SerializedName("status") val status: String = ""
)

// --- Core Forklift Report Data DTOs ---

data class ForkliftReportData(
    @SerializedName("laporan") val laporan: ForkliftReportDetail = ForkliftReportDetail()
)

// UPDATED ForkliftReportDetail to include 'id', 'subInspectionType', and 'documentType'
data class ForkliftReportDetail(
    @SerializedName("id") val id: String = "",
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("inspectionDate") val inspectionDate: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("generalData") val generalData: ForkliftReportGeneralData = ForkliftReportGeneralData(),
    @SerializedName("technicalData") val technicalData: ForkliftReportTechnicalData = ForkliftReportTechnicalData(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: ForkliftReportInspectionAndTesting = ForkliftReportInspectionAndTesting(),
    @SerializedName("testingForklift") val testingForklift: ForkliftReportTestingForklift = ForkliftReportTestingForklift(),
    @SerializedName("conclusion") val conclusion: String = "",
    @SerializedName("recommendation") val recommendation: String = "",
    @SerializedName("subInspectionType") val subInspectionType: String = "", // Added based on response JSON
    @SerializedName("documentType") val documentType: String = "", // Added based on response JSON
    @SerializedName("createdAt") val createdAt: String = "" // Added based on response JSON (appears here as well as root)
)

// --- General Data DTO (from PAA - Forklift.json) ---

data class ForkliftReportGeneralData(
    @SerializedName("ownerName") val ownerName: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("ownerAddress") val ownerAddress: String = "",
    @SerializedName("userInCharge") val userInCharge: String = "",
    @SerializedName("subcontractorPersonInCharge") val subcontractorPersonInCharge: String = "",
    @SerializedName("unitLocation") val unitLocation: String = "",
    @SerializedName("operatorName") val operatorName: String = "",
    @SerializedName("manufacturer") val manufacturer: String = "",
    @SerializedName("brandType") val brandType: String = "",
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String = "",
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String = "",
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String = "",
    @SerializedName("intendedUse") val intendedUse: String = "",
    @SerializedName("certificateNumber") val certificateNumber: String = "",
    @SerializedName("equipmentHistory") val equipmentHistory: String = ""
)

// --- Technical Data DTO (from PAA - Forklift.json) ---

data class ForkliftReportTechnicalData(
    @SerializedName("specificationSerialNumber") val specificationSerialNumber: String = "",
    @SerializedName("specificationCapacity") val specificationCapacity: String = "",
    @SerializedName("specificationAttachment") val specificationAttachment: String = "",
    @SerializedName("specificationForkDimensions") val specificationForkDimensions: String = "",
    @SerializedName("specificationSpeedLifting") val specificationSpeedLifting: String = "",
    @SerializedName("specificationSpeedLowering") val specificationSpeedLowering: String = "",
    @SerializedName("specificationSpeedTravelling") val specificationSpeedTravelling: String = "",
    @SerializedName("primeMoverBrandType") val primeMoverBrandType: String = "",
    @SerializedName("primeMoverSerialNumber") val primeMoverSerialNumber: String = "",
    @SerializedName("primeMoverYearOfManufacture") val primeMoverYearOfManufacture: String = "",
    @SerializedName("primeMoverRevolution") val primeMoverRevolution: String = "",
    @SerializedName("primeMoverPower") val primeMoverPower: String = "",
    @SerializedName("primeMoverNumberOfCylinders") val primeMoverNumberOfCylinders: String = "",
    @SerializedName("dimensionLength") val dimensionLength: String = "",
    @SerializedName("dimensionWidth") val dimensionWidth: String = "",
    @SerializedName("dimensionHeight") val dimensionHeight: String = "",
    @SerializedName("dimensionForkLiftingHeight") val dimensionForkLiftingHeight: String = "",
    @SerializedName("tirePressureDriveWheel") val tirePressureDriveWheel: String = "",
    @SerializedName("tirePressureSteeringWheel") val tirePressureSteeringWheel: String = "",
    @SerializedName("driveWheelSize") val driveWheelSize: String = "",
    @SerializedName("driveWheelType") val driveWheelType: String = "",
    @SerializedName("steeringWheelSize") val steeringWheelSize: String = "",
    @SerializedName("steeringWheelType") val steeringWheelType: String = "",
    @SerializedName("travellingBrakeSize") val travellingBrakeSize: String = "",
    @SerializedName("travellingBrakeType") val travellingBrakeType: String = "",
    @SerializedName("hydraulicPumpPressure") val hydraulicPumpPressure: String = "",
    @SerializedName("hydraulicPumpType") val hydraulicPumpType: String = "",
    @SerializedName("hydraulicPumpReliefValve") val hydraulicPumpReliefValve: String = ""
)

// --- Inspection and Testing DTOs (from PAA - Forklift.json) ---

data class ForkliftReportInspectionAndTesting(
    @SerializedName("mainFrameAndChassis") val mainFrameAndChassis: ForkliftReportMainFrameAndChassis = ForkliftReportMainFrameAndChassis(),
    @SerializedName("primeMover") val primeMover: ForkliftReportPrimeMover = ForkliftReportPrimeMover(),
    @SerializedName("dashboard") val dashboard: ForkliftReportDashboard = ForkliftReportDashboard(),
    @SerializedName("powerTrain") val powerTrain: ForkliftReportPowerTrain = ForkliftReportPowerTrain(),
    @SerializedName("attachments") val attachments: ForkliftReportAttachments = ForkliftReportAttachments(),
    @SerializedName("personalBasketAndHandrail") val personalBasketAndHandrail: ForkliftReportPersonalBasketAndHandrail = ForkliftReportPersonalBasketAndHandrail(),
    @SerializedName("hydraulicComponents") val hydraulicComponents: ForkliftReportHydraulicComponents = ForkliftReportHydraulicComponents(),
    @SerializedName("engineOnChecks") val engineOnChecks: ForkliftReportEngineOnChecks = ForkliftReportEngineOnChecks()
)

data class ForkliftReportMainFrameAndChassis(
    @SerializedName("reinforcingFrameCorrosionResult") val reinforcingFrameCorrosionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("reinforcingFrameCracksResult") val reinforcingFrameCracksResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("reinforcingFrameDeformationResult") val reinforcingFrameDeformationResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("counterweightCorrosionResult") val counterweightCorrosionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("counterweightConditionResult") val counterweightConditionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("otherEquipmentFloorDeckResult") val otherEquipmentFloorDeckResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("otherEquipmentStairsStepsResult") val otherEquipmentStairsStepsResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("otherEquipmentFasteningBoltsResult") val otherEquipmentFasteningBoltsResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("otherEquipmentOperatorSeatResult") val otherEquipmentOperatorSeatResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult()
)

data class ForkliftReportPrimeMover(
    @SerializedName("systemCoolingResult") val systemCoolingResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("systemLubricationResult") val systemLubricationResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("systemFuelResult") val systemFuelResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("systemAirIntakeResult") val systemAirIntakeResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("systemExhaustGasResult") val systemExhaustGasResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("systemStarterResult") val systemStarterResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalBatteryResult") val electricalBatteryResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalStartingDynamoResult") val electricalStartingDynamoResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalAlternatorResult") val electricalAlternatorResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalBatteryCableResult") val electricalBatteryCableResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalInstallationCableResult") val electricalInstallationCableResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalLightingLampsResult") val electricalLightingLampsResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalSafetyLampsResult") val electricalSafetyLampsResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalHornResult") val electricalHornResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalFuseResult") val electricalFuseResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult()
)

data class ForkliftReportDashboard(
    @SerializedName("tempIndicatorResult") val tempIndicatorResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("oilPressureResult") val oilPressureResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("hydraulicPressureResult") val hydraulicPressureResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("hourMeterResult") val hourMeterResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("glowPlugResult") val glowPlugResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("fuelIndicatorResult") val fuelIndicatorResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("loadIndicatorResult") val loadIndicatorResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("loadChartResult") val loadChartResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult()
)

data class ForkliftReportPowerTrain(
    @SerializedName("starterDynamoResult") val starterDynamoResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("steeringWheelResult") val steeringWheelResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("steeringRodResult") val steeringRodResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("steeringGearBoxResult") val steeringGearBoxResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("steeringPitmanResult") val steeringPitmanResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("steeringDragLinkResult") val steeringDragLinkResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("steeringTieRodResult") val steeringTieRodResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("steeringLubeResult") val steeringLubeResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("wheelsFrontResult") val wheelsFrontResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("wheelsRearResult") val wheelsRearResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("wheelsBoltsResult") val wheelsBoltsResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("wheelsDrumResult") val wheelsDrumResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("wheelsLubeResult") val wheelsLubeResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("wheelsMechanicalResult") val wheelsMechanicalResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("clutchHousingResult") val clutchHousingResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("clutchConditionResult") val clutchConditionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("clutchTransOilResult") val clutchTransOilResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("clutchTransLeakResult") val clutchTransLeakResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("clutchShaftResult") val clutchShaftResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("clutchMechanicalResult") val clutchMechanicalResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("diffHousingResult") val diffHousingResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("diffConditionResult") val diffConditionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("diffOilResult") val diffOilResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("diffLeakResult") val diffLeakResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("diffShaftResult") val diffShaftResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("brakesMainResult") val brakesMainResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("brakesHandResult") val brakesHandResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("brakesEmergencyResult") val brakesEmergencyResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("brakesLeakResult") val brakesLeakResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("brakesMechanicalResult") val brakesMechanicalResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("transHousingResult") val transHousingResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("transOilResult") val transOilResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("transLeakResult") val transLeakResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("transMechanicalResult") val transMechanicalResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult()
)

data class ForkliftReportAttachments(
    @SerializedName("mastWearResult") val mastWearResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("mastCracksResult") val mastCracksResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("mastDeformationResult") val mastDeformationResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("mastLubeResult") val mastLubeResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("mastShaftBearingResult") val mastShaftBearingResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("liftChainConditionResult") val liftChainConditionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("liftChainDeformationResult") val liftChainDeformationResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("liftChainLubeResult") val liftChainLubeResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult()
)

data class ForkliftReportPersonalBasketAndHandrail(
    @SerializedName("basketFloorCorrosionResult") val basketFloorCorrosionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketFloorCracksResult") val basketFloorCracksResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketFloorDeformationResult") val basketFloorDeformationResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketFloorFasteningResult") val basketFloorFasteningResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketFrameCorrosionResult") val basketFrameCorrosionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketFrameCracksResult") val basketFrameCracksResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketFrameDeformationResult") val basketFrameDeformationResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketFrameCrossBracingResult") val basketFrameCrossBracingResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketFrameDiagonalBracingResult") val basketFrameDiagonalBracingResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketBoltsCorrosionResult") val basketBoltsCorrosionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketBoltsCracksResult") val basketBoltsCracksResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketBoltsDeformationResult") val basketBoltsDeformationResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketBoltsFasteningResult") val basketBoltsFasteningResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketDoorCorrosionResult") val basketDoorCorrosionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketDoorCracksResult") val basketDoorCracksResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketDoorDeformationResult") val basketDoorDeformationResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("basketDoorFasteningResult") val basketDoorFasteningResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("handrailCracksResult") val handrailCracksResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("handrailWearResult") val handrailWearResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("handrailCracks2Result") val handrailCracks2Result: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("handrailRailStraightnessResult") val handrailRailStraightnessResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("handrailRailJointsResult") val handrailRailJointsResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("handrailInterRailStraightnessResult") val handrailInterRailStraightnessResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("handrailRailJointGapResult") val handrailRailJointGapResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("handrailRailFastenersResult") val handrailRailFastenersResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("handrailRailStopperResult") val handrailRailStopperResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult()
)

data class ForkliftReportHydraulicComponents(
    @SerializedName("tankLeakageResult") val tankLeakageResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("tankOilLevelResult") val tankOilLevelResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("tankOilConditionResult") val tankOilConditionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("tankSuctionLineResult") val tankSuctionLineResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("tankReturnLineResult") val tankReturnLineResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("pumpLeakageResult") val pumpLeakageResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("pumpSuctionLineResult") val pumpSuctionLineResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("pumpPressureLineResult") val pumpPressureLineResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("pumpFunctionResult") val pumpFunctionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("pumpNoiseResult") val pumpNoiseResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("valveLeakageResult") val valveLeakageResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("valveLineConditionResult") val valveLineConditionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("valveReliefFunctionResult") val valveReliefFunctionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("valveNoiseResult") val valveNoiseResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("valveLiftCylinderResult") val valveLiftCylinderResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("valveTiltCylinderResult") val valveTiltCylinderResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("valveSteeringCylinderResult") val valveSteeringCylinderResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("actuatorLeakageResult") val actuatorLeakageResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("actuatorLineConditionResult") val actuatorLineConditionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("actuatorNoiseResult") val actuatorNoiseResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult()
)

data class ForkliftReportEngineOnChecks(
    @SerializedName("starterDynamoResult") val starterDynamoResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("instrumentResult") val instrumentResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("electricalResult") val electricalResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("leakageEngineOilResult") val leakageEngineOilResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("leakageFuelResult") val leakageFuelResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("leakageCoolantResult") val leakageCoolantResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("leakageHydraulicOilResult") val leakageHydraulicOilResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("leakageTransmissionOilResult") val leakageTransmissionOilResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("leakageFinalDriveOilResult") val leakageFinalDriveOilResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("leakageBrakeFluidResult") val leakageBrakeFluidResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("clutchResult") val clutchResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("transmissionResult") val transmissionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("brakeResult") val brakeResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("hornAlarmResult") val hornAlarmResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("lampsResult") val lampsResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("hydraulicMotorResult") val hydraulicMotorResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("steeringCylinderResult") val steeringCylinderResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("liftingCylinderResult") val liftingCylinderResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("tiltingCylinderResult") val tiltingCylinderResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("exhaustGasResult") val exhaustGasResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("controlLeversResult") val controlLeversResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("noiseEngineResult") val noiseEngineResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("noiseTurbochargerResult") val noiseTurbochargerResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("noiseTransmissionResult") val noiseTransmissionResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("noiseHydraulicPumpResult") val noiseHydraulicPumpResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult(),
    @SerializedName("noiseProtectiveCoverResult") val noiseProtectiveCoverResult: ForkliftReportInspectionResult = ForkliftReportInspectionResult()
)

// --- Testing Forklift DTOs (from PAA - Forklift.json) ---

data class ForkliftReportTestingForklift(
    @SerializedName("liftingChainInspection") val liftingChainInspection: List<ForkliftReportLiftingChainInspection> = emptyList(),
    @SerializedName("nonDestructiveTesting") val nonDestructiveTesting: ForkliftReportNonDestructiveTesting = ForkliftReportNonDestructiveTesting(),
    @SerializedName("loadTesting") val loadTesting: List<ForkliftReportLoadTesting> = emptyList()
)

data class ForkliftReportLiftingChainInspection(
    @SerializedName("inspectedPart") val inspectedPart: String = "",
    @SerializedName("constructionType") val constructionType: String = "",
    @SerializedName("standardPitch") val standardPitch: String = "",
    @SerializedName("measuredPitch") val measuredPitch: String = "",
    @SerializedName("standardPin") val standardPin: String = "",
    @SerializedName("measuredPin") val measuredPin: String = "",
    @SerializedName("result") val result: String = ""
)

data class ForkliftReportNonDestructiveTesting(
    @SerializedName("ndtType") val ndtType: String = "",
    @SerializedName("results") val results: List<ForkliftReportNdtResult> = emptyList()
)

data class ForkliftReportNdtResult(
    @SerializedName("inspectedPart") val inspectedPart: String = "",
    @SerializedName("location") val location: String = "",
    @SerializedName("defectFound") val defectFound: String = "",
    @SerializedName("defectNotFound") val defectNotFound: String = "",
    @SerializedName("result") val result: String = ""
)

data class ForkliftReportLoadTesting(
    @SerializedName("liftingHeight") val liftingHeight: String = "",
    @SerializedName("testLoad") val testLoad: String = "",
    @SerializedName("speed") val speed: String = "",
    @SerializedName("movement") val movement: String = "",
    @SerializedName("remarks") val remarks: String = "",
    @SerializedName("result") val result: String = ""
)

// --- Generic Inspection Result DTO (from PAA - Forklift.json) ---

data class ForkliftReportInspectionResult(
    @SerializedName("result") val result: String = "",
    @SerializedName("status") val status: Boolean = false
)