package com.nakersolutionid.nakersolutionid.data.remote.dto.forklift

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus

data class ForkliftReportRequest(
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: ForkliftGeneralData,
    @SerializedName("technicalData") val technicalData: ForkliftTechnicalData,
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: ForkliftInspectionAndTesting,
    @SerializedName("testingForklift") val testingForklift: ForkliftTestingForklift,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendation") val recommendation: String
)

// Data DTO for single Forklift Report response
data class ForkliftSingleReportResponseData(
    @SerializedName("laporan") val laporan: ForkliftReportData
)

// Data DTO for list of Forklift Reports response
data class ForkliftListReportResponseData(
    @SerializedName("laporan") val laporan: List<ForkliftReportData>
)

// Main DTO for Forklift Report (used for create, update, and individual get)
data class ForkliftReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("documentType") val documentType: String, // From response JSON
    @SerializedName("createdAt") val createdAt: String, // From response JSON
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("generalData") val generalData: ForkliftGeneralData,
    @SerializedName("technicalData") val technicalData: ForkliftTechnicalData,
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: ForkliftInspectionAndTesting,
    @SerializedName("testingForklift") val testingForklift: ForkliftTestingForklift,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendation") val recommendation: String
)

data class ForkliftGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("ownerAddress") val ownerAddress: String,
    @SerializedName("userInCharge") val userInCharge: String,
    @SerializedName("subcontractorPersonInCharge") val subcontractorPersonInCharge: String,
    @SerializedName("unitLocation") val unitLocation: String,
    @SerializedName("operatorName") val operatorName: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("brandType") val brandType: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String,
    @SerializedName("intendedUse") val intendedUse: String,
    @SerializedName("certificateNumber") val certificateNumber: String,
    @SerializedName("equipmentHistory") val equipmentHistory: String
)

data class ForkliftTechnicalData(
    @SerializedName("specificationSerialNumber") val specificationSerialNumber: String,
    @SerializedName("specificationCapacity") val specificationCapacity: String,
    @SerializedName("specificationAttachment") val specificationAttachment: String,
    @SerializedName("specificationForkDimensions") val specificationForkDimensions: String,
    @SerializedName("specificationSpeedLifting") val specificationSpeedLifting: String,
    @SerializedName("specificationSpeedLowering") val specificationSpeedLowering: String,
    @SerializedName("specificationSpeedTravelling") val specificationSpeedTravelling: String,
    @SerializedName("primeMoverBrandType") val primeMoverBrandType: String,
    @SerializedName("primeMoverSerialNumber") val primeMoverSerialNumber: String,
    @SerializedName("primeMoverYearOfManufacture") val primeMoverYearOfManufacture: String,
    @SerializedName("primeMoverRevolution") val primeMoverRevolution: String,
    @SerializedName("primeMoverPower") val primeMoverPower: String,
    @SerializedName("primeMoverNumberOfCylinders") val primeMoverNumberOfCylinders: String,
    @SerializedName("dimensionLength") val dimensionLength: String,
    @SerializedName("dimensionWidth") val dimensionWidth: String,
    @SerializedName("dimensionHeight") val dimensionHeight: String,
    @SerializedName("dimensionForkLiftingHeight") val dimensionForkLiftingHeight: String,
    @SerializedName("tirePressureDriveWheel") val tirePressureDriveWheel: String,
    @SerializedName("tirePressureSteeringWheel") val tirePressureSteeringWheel: String,
    @SerializedName("driveWheelSize") val driveWheelSize: String,
    @SerializedName("driveWheelType") val driveWheelType: String,
    @SerializedName("steeringWheelSize") val steeringWheelSize: String,
    @SerializedName("steeringWheelType") val steeringWheelType: String,
    @SerializedName("travellingBrakeSize") val travellingBrakeSize: String,
    @SerializedName("travellingBrakeType") val travellingBrakeType: String,
    @SerializedName("hydraulicPumpPressure") val hydraulicPumpPressure: String,
    @SerializedName("hydraulicPumpType") val hydraulicPumpType: String,
    @SerializedName("hydraulicPumpReliefValve") val hydraulicPumpReliefValve: String
)

data class ForkliftInspectionAndTesting(
    @SerializedName("mainFrameAndChassis") val mainFrameAndChassis: ForkliftMainFrameAndChassis,
    @SerializedName("primeMover") val primeMover: ForkliftPrimeMover,
    @SerializedName("dashboard") val dashboard: ForkliftDashboard,
    @SerializedName("powerTrain") val powerTrain: ForkliftPowerTrain,
    @SerializedName("attachments") val attachments: ForkliftAttachments,
    @SerializedName("personalBasketAndHandrail") val personalBasketAndHandrail: ForkliftPersonalBasketAndHandrail,
    @SerializedName("hydraulicComponents") val hydraulicComponents: ForkliftHydraulicComponents,
    @SerializedName("engineOnChecks") val engineOnChecks: ForkliftEngineOnChecks
)

data class ForkliftMainFrameAndChassis(
    @SerializedName("reinforcingFrameCorrosionResult") val reinforcingFrameCorrosionResult: ResultStatus,
    @SerializedName("reinforcingFrameCracksResult") val reinforcingFrameCracksResult: ResultStatus,
    @SerializedName("reinforcingFrameDeformationResult") val reinforcingFrameDeformationResult: ResultStatus,
    @SerializedName("counterweightCorrosionResult") val counterweightCorrosionResult: ResultStatus,
    @SerializedName("counterweightConditionResult") val counterweightConditionResult: ResultStatus,
    @SerializedName("otherEquipmentFloorDeckResult") val otherEquipmentFloorDeckResult: ResultStatus,
    @SerializedName("otherEquipmentStairsStepsResult") val otherEquipmentStairsStepsResult: ResultStatus,
    @SerializedName("otherEquipmentFasteningBoltsResult") val otherEquipmentFasteningBoltsResult: ResultStatus,
    @SerializedName("otherEquipmentOperatorSeatResult") val otherEquipmentOperatorSeatResult: ResultStatus
)

data class ForkliftPrimeMover(
    @SerializedName("systemCoolingResult") val systemCoolingResult: ResultStatus,
    @SerializedName("systemLubricationResult") val systemLubricationResult: ResultStatus,
    @SerializedName("systemFuelResult") val systemFuelResult: ResultStatus,
    @SerializedName("systemAirIntakeResult") val systemAirIntakeResult: ResultStatus,
    @SerializedName("systemExhaustGasResult") val systemExhaustGasResult: ResultStatus,
    @SerializedName("systemStarterResult") val systemStarterResult: ResultStatus,
    @SerializedName("electricalBatteryResult") val electricalBatteryResult: ResultStatus,
    @SerializedName("electricalStartingDynamoResult") val electricalStartingDynamoResult: ResultStatus,
    @SerializedName("electricalAlternatorResult") val electricalAlternatorResult: ResultStatus,
    @SerializedName("electricalBatteryCableResult") val electricalBatteryCableResult: ResultStatus,
    @SerializedName("electricalInstallationCableResult") val electricalInstallationCableResult: ResultStatus,
    @SerializedName("electricalLightingLampsResult") val electricalLightingLampsResult: ResultStatus,
    @SerializedName("electricalSafetyLampsResult") val electricalSafetyLampsResult: ResultStatus,
    @SerializedName("electricalHornResult") val electricalHornResult: ResultStatus,
    @SerializedName("electricalFuseResult") val electricalFuseResult: ResultStatus
)

data class ForkliftDashboard(
    @SerializedName("tempIndicatorResult") val tempIndicatorResult: ResultStatus,
    @SerializedName("oilPressureResult") val oilPressureResult: ResultStatus,
    @SerializedName("hydraulicPressureResult") val hydraulicPressureResult: ResultStatus,
    @SerializedName("hourMeterResult") val hourMeterResult: ResultStatus,
    @SerializedName("glowPlugResult") val glowPlugResult: ResultStatus,
    @SerializedName("fuelIndicatorResult") val fuelIndicatorResult: ResultStatus,
    @SerializedName("loadIndicatorResult") val loadIndicatorResult: ResultStatus,
    @SerializedName("loadChartResult") val loadChartResult: ResultStatus
)

data class ForkliftPowerTrain(
    @SerializedName("starterDynamoResult") val starterDynamoResult: ResultStatus,
    @SerializedName("steeringWheelResult") val steeringWheelResult: ResultStatus,
    @SerializedName("steeringRodResult") val steeringRodResult: ResultStatus,
    @SerializedName("steeringGearBoxResult") val steeringGearBoxResult: ResultStatus,
    @SerializedName("steeringPitmanResult") val steeringPitmanResult: ResultStatus,
    @SerializedName("steeringDragLinkResult") val steeringDragLinkResult: ResultStatus,
    @SerializedName("steeringTieRodResult") val steeringTieRodResult: ResultStatus,
    @SerializedName("steeringLubeResult") val steeringLubeResult: ResultStatus,
    @SerializedName("wheelsFrontResult") val wheelsFrontResult: ResultStatus,
    @SerializedName("wheelsRearResult") val wheelsRearResult: ResultStatus,
    @SerializedName("wheelsBoltsResult") val wheelsBoltsResult: ResultStatus,
    @SerializedName("wheelsDrumResult") val wheelsDrumResult: ResultStatus,
    @SerializedName("wheelsLubeResult") val wheelsLubeResult: ResultStatus,
    @SerializedName("wheelsMechanicalResult") val wheelsMechanicalResult: ResultStatus,
    @SerializedName("clutchHousingResult") val clutchHousingResult: ResultStatus,
    @SerializedName("clutchConditionResult") val clutchConditionResult: ResultStatus,
    @SerializedName("clutchTransOilResult") val clutchTransOilResult: ResultStatus,
    @SerializedName("clutchTransLeakResult") val clutchTransLeakResult: ResultStatus,
    @SerializedName("clutchShaftResult") val clutchShaftResult: ResultStatus,
    @SerializedName("clutchMechanicalResult") val clutchMechanicalResult: ResultStatus,
    @SerializedName("diffHousingResult") val diffHousingResult: ResultStatus,
    @SerializedName("diffConditionResult") val diffConditionResult: ResultStatus,
    @SerializedName("diffOilResult") val diffOilResult: ResultStatus,
    @SerializedName("diffLeakResult") val diffLeakResult: ResultStatus,
    @SerializedName("diffShaftResult") val diffShaftResult: ResultStatus,
    @SerializedName("brakesMainResult") val brakesMainResult: ResultStatus,
    @SerializedName("brakesHandResult") val brakesHandResult: ResultStatus,
    @SerializedName("brakesEmergencyResult") val brakesEmergencyResult: ResultStatus,
    @SerializedName("brakesLeakResult") val brakesLeakResult: ResultStatus,
    @SerializedName("brakesMechanicalResult") val brakesMechanicalResult: ResultStatus,
    @SerializedName("transHousingResult") val transHousingResult: ResultStatus,
    @SerializedName("transOilResult") val transOilResult: ResultStatus,
    @SerializedName("transLeakResult") val transLeakResult: ResultStatus,
    @SerializedName("transMechanicalResult") val transMechanicalResult: ResultStatus
)

data class ForkliftAttachments(
    @SerializedName("mastWearResult") val mastWearResult: ResultStatus,
    @SerializedName("mastCracksResult") val mastCracksResult: ResultStatus,
    @SerializedName("mastDeformationResult") val mastDeformationResult: ResultStatus,
    @SerializedName("mastLubeResult") val mastLubeResult: ResultStatus,
    @SerializedName("mastShaftBearingResult") val mastShaftBearingResult: ResultStatus,
    @SerializedName("liftChainConditionResult") val liftChainConditionResult: ResultStatus,
    @SerializedName("liftChainDeformationResult") val liftChainDeformationResult: ResultStatus,
    @SerializedName("liftChainLubeResult") val liftChainLubeResult: ResultStatus
)

data class ForkliftPersonalBasketAndHandrail(
    @SerializedName("basketFloorCorrosionResult") val basketFloorCorrosionResult: ResultStatus,
    @SerializedName("basketFloorCracksResult") val basketFloorCracksResult: ResultStatus,
    @SerializedName("basketFloorDeformationResult") val basketFloorDeformationResult: ResultStatus,
    @SerializedName("basketFloorFasteningResult") val basketFloorFasteningResult: ResultStatus,
    @SerializedName("basketFrameCorrosionResult") val basketFrameCorrosionResult: ResultStatus,
    @SerializedName("basketFrameCracksResult") val basketFrameCracksResult: ResultStatus,
    @SerializedName("basketFrameDeformationResult") val basketFrameDeformationResult: ResultStatus,
    @SerializedName("basketFrameCrossBracingResult") val basketFrameCrossBracingResult: ResultStatus,
    @SerializedName("basketFrameDiagonalBracingResult") val basketFrameDiagonalBracingResult: ResultStatus,
    @SerializedName("basketBoltsCorrosionResult") val basketBoltsCorrosionResult: ResultStatus,
    @SerializedName("basketBoltsCracksResult") val basketBoltsCracksResult: ResultStatus,
    @SerializedName("basketBoltsDeformationResult") val basketBoltsDeformationResult: ResultStatus,
    @SerializedName("basketBoltsFasteningResult") val basketBoltsFasteningResult: ResultStatus,
    @SerializedName("basketDoorCorrosionResult") val basketDoorCorrosionResult: ResultStatus,
    @SerializedName("basketDoorCracksResult") val basketDoorCracksResult: ResultStatus,
    @SerializedName("basketDoorDeformationResult") val basketDoorDeformationResult: ResultStatus,
    @SerializedName("basketDoorFasteningResult") val basketDoorFasteningResult: ResultStatus,
    @SerializedName("handrailCracksResult") val handrailCracksResult: ResultStatus,
    @SerializedName("handrailWearResult") val handrailWearResult: ResultStatus,
    @SerializedName("handrailCracks2Result") val handrailCracks2Result: ResultStatus,
    @SerializedName("handrailRailStraightnessResult") val handrailRailStraightnessResult: ResultStatus,
    @SerializedName("handrailRailJointsResult") val handrailRailJointsResult: ResultStatus,
    @SerializedName("handrailInterRailStraightnessResult") val handrailInterRailStraightnessResult: ResultStatus,
    @SerializedName("handrailRailJointGapResult") val handrailRailJointGapResult: ResultStatus,
    @SerializedName("handrailRailFastenersResult") val handrailRailFastenersResult: ResultStatus,
    @SerializedName("handrailRailStopperResult") val handrailRailStopperResult: ResultStatus
)

data class ForkliftHydraulicComponents(
    @SerializedName("tankLeakageResult") val tankLeakageResult: ResultStatus,
    @SerializedName("tankOilLevelResult") val tankOilLevelResult: ResultStatus,
    @SerializedName("tankOilConditionResult") val tankOilConditionResult: ResultStatus,
    @SerializedName("tankSuctionLineResult") val tankSuctionLineResult: ResultStatus,
    @SerializedName("tankReturnLineResult") val tankReturnLineResult: ResultStatus,
    @SerializedName("pumpLeakageResult") val pumpLeakageResult: ResultStatus,
    @SerializedName("pumpSuctionLineResult") val pumpSuctionLineResult: ResultStatus,
    @SerializedName("pumpPressureLineResult") val pumpPressureLineResult: ResultStatus,
    @SerializedName("pumpFunctionResult") val pumpFunctionResult: ResultStatus,
    @SerializedName("pumpNoiseResult") val pumpNoiseResult: ResultStatus,
    @SerializedName("valveLeakageResult") val valveLeakageResult: ResultStatus,
    @SerializedName("valveLineConditionResult") val valveLineConditionResult: ResultStatus,
    @SerializedName("valveReliefFunctionResult") val valveReliefFunctionResult: ResultStatus,
    @SerializedName("valveNoiseResult") val valveNoiseResult: ResultStatus,
    @SerializedName("valveLiftCylinderResult") val valveLiftCylinderResult: ResultStatus,
    @SerializedName("valveTiltCylinderResult") val valveTiltCylinderResult: ResultStatus,
    @SerializedName("valveSteeringCylinderResult") val valveSteeringCylinderResult: ResultStatus,
    @SerializedName("actuatorLeakageResult") val actuatorLeakageResult: ResultStatus,
    @SerializedName("actuatorLineConditionResult") val actuatorLineConditionResult: ResultStatus,
    @SerializedName("actuatorNoiseResult") val actuatorNoiseResult: ResultStatus
)

data class ForkliftEngineOnChecks(
    @SerializedName("starterDynamoResult") val starterDynamoResult: ResultStatus,
    @SerializedName("instrumentResult") val instrumentResult: ResultStatus,
    @SerializedName("electricalResult") val electricalResult: ResultStatus,
    @SerializedName("leakageEngineOilResult") val leakageEngineOilResult: ResultStatus,
    @SerializedName("leakageFuelResult") val leakageFuelResult: ResultStatus,
    @SerializedName("leakageCoolantResult") val leakageCoolantResult: ResultStatus,
    @SerializedName("leakageHydraulicOilResult") val leakageHydraulicOilResult: ResultStatus,
    @SerializedName("leakageTransmissionOilResult") val leakageTransmissionOilResult: ResultStatus,
    @SerializedName("leakageFinalDriveOilResult") val leakageFinalDriveOilResult: ResultStatus,
    @SerializedName("leakageBrakeFluidResult") val leakageBrakeFluidResult: ResultStatus,
    @SerializedName("clutchResult") val clutchResult: ResultStatus,
    @SerializedName("transmissionResult") val transmissionResult: ResultStatus,
    @SerializedName("brakeResult") val brakeResult: ResultStatus,
    @SerializedName("hornAlarmResult") val hornAlarmResult: ResultStatus,
    @SerializedName("lampsResult") val lampsResult: ResultStatus,
    @SerializedName("hydraulicMotorResult") val hydraulicMotorResult: ResultStatus,
    @SerializedName("steeringCylinderResult") val steeringCylinderResult: ResultStatus,
    @SerializedName("liftingCylinderResult") val liftingCylinderResult: ResultStatus,
    @SerializedName("tiltingCylinderResult") val tiltingCylinderResult: ResultStatus,
    @SerializedName("exhaustGasResult") val exhaustGasResult: ResultStatus,
    @SerializedName("controlLeversResult") val controlLeversResult: ResultStatus,
    @SerializedName("noiseEngineResult") val noiseEngineResult: ResultStatus,
    @SerializedName("noiseTurbochargerResult") val noiseTurbochargerResult: ResultStatus,
    @SerializedName("noiseTransmissionResult") val noiseTransmissionResult: ResultStatus,
    @SerializedName("noiseHydraulicPumpResult") val noiseHydraulicPumpResult: ResultStatus,
    @SerializedName("noiseProtectiveCoverResult") val noiseProtectiveCoverResult: ResultStatus
)

data class ForkliftTestingForklift(
    @SerializedName("liftingChainInspection") val liftingChainInspection: List<ForkliftLiftingChainInspection>,
    @SerializedName("nonDestructiveTesting") val nonDestructiveTesting: ForkliftNonDestructiveTesting,
    @SerializedName("loadTesting") val loadTesting: List<ForkliftLoadTesting>
)

data class ForkliftLiftingChainInspection(
    @SerializedName("inspectedPart") val inspectedPart: String,
    @SerializedName("constructionType") val constructionType: String,
    @SerializedName("standardPitch") val standardPitch: String,
    @SerializedName("measuredPitch") val measuredPitch: String,
    @SerializedName("standardPin") val standardPin: String,
    @SerializedName("measuredPin") val measuredPin: String,
    @SerializedName("result") val result: String
)

data class ForkliftNonDestructiveTesting(
    @SerializedName("ndtType") val ndtType: String,
    @SerializedName("results") val results: List<ForkliftNonDestructiveTestingResult>
)

data class ForkliftNonDestructiveTestingResult(
    @SerializedName("inspectedPart") val inspectedPart: String,
    @SerializedName("location") val location: String,
    @SerializedName("defectFound") val defectFound: String,
    @SerializedName("defectNotFound") val defectNotFound: String,
    @SerializedName("result") val result: String
)

data class ForkliftLoadTesting(
    @SerializedName("liftingHeight") val liftingHeight: String,
    @SerializedName("testLoad") val testLoad: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("movement") val movement: String,
    @SerializedName("remarks") val remarks: String,
    @SerializedName("result") val result: String
)
