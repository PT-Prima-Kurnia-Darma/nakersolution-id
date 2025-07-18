package com.nakersolutionid.nakersolutionid.features.report.paa.forklift

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Forklift inspection report.
 * This class is immutable.
 */
@Immutable
data class ForkliftUiState(
    val forkliftInspectionReport: ForkliftInspectionReport = ForkliftInspectionReport()
)

@Immutable
data class ForkliftInspectionReport(
    val equipmentType: String = "",
    val examinationType: String = "",
    val generalData: ForkliftGeneralData = ForkliftGeneralData(),
    val technicalData: ForkliftTechnicalData = ForkliftTechnicalData(),
    val visualInspection: ForkliftVisualInspection = ForkliftVisualInspection(),
    val nonDestructiveExamination: ForkliftNde = ForkliftNde(),
    val testing: ForkliftTesting = ForkliftTesting(),
    val conclusion: ForkliftConclusion = ForkliftConclusion()
)

@Immutable
data class ForkliftGeneralData(
    val owner: String = "",
    val address: String = "",
    val user: String = "",
    val personInCharge: String = "",
    val unitLocation: String = "",
    val operatorName: String = "",
    val driveType: String = "",
    val manufacturer: String = "",
    val brandType: String = "",
    val yearOfManufacture: String = "",
    val serialNumber: String = "",
    val liftingCapacity: String = "",
    val intendedUse: String = "",
    val permitNumber: String = "",
    val equipmentHistory: String = ""
)

@Immutable
data class ForkliftTechnicalData(
    val specifications: ForkliftSpecifications = ForkliftSpecifications(),
    val speed: ForkliftSpeed = ForkliftSpeed(),
    val primeMover: ForkliftPrimeMover = ForkliftPrimeMover(),
    val dimensions: ForkliftDimensions = ForkliftDimensions(),
    val tirePressure: ForkliftTirePressure = ForkliftTirePressure(),
    val driveWheel: ForkliftWheel = ForkliftWheel(),
    val steeringWheel: ForkliftWheel = ForkliftWheel(),
    val travellingBrake: ForkliftWheel = ForkliftWheel(),
    val hydraulicPump: ForkliftHydraulicPump = ForkliftHydraulicPump()
)

@Immutable
data class ForkliftSpecifications(
    val serialNumber: String = "",
    val capacity: String = "",
    val attachment: String = "",
    val forkDimension: String = ""
)

@Immutable
data class ForkliftSpeed(
    val lifting: String = "",
    val lowering: String = "",
    val travelling: String = ""
)

@Immutable
data class ForkliftPrimeMover(
    val revolution: String = "",
    val brandType: String = "",
    val serialNumber: String = "",
    val yearOfManufacture: String = "",
    val power: String = "",
    val numberOfCylinders: String = ""
)

@Immutable
data class ForkliftDimensions(
    val length: String = "",
    val width: String = "",
    val height: String = "",
    val forkLiftingHeight: String = ""
)

@Immutable
data class ForkliftTirePressure(
    val driveWheel: String = "",
    val steeringWheel: String = ""
)

@Immutable
data class ForkliftWheel(
    val size: String = "",
    val type: String = ""
)

@Immutable
data class ForkliftHydraulicPump(
    val pressure: String = "",
    val type: String = "",
    val reliefValve: String = ""
)

@Immutable
data class ForkliftInspectionResult(
    val status: Boolean = false,
    val result: String = ""
)

@Immutable
data class ForkliftVisualInspection(
    val chassisReinforcementCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val chassisReinforcementCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val chassisReinforcementDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val counterweightCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val counterweightCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val otherEquipmentFloorDeck: ForkliftInspectionResult = ForkliftInspectionResult(),
    val otherEquipmentStairs: ForkliftInspectionResult = ForkliftInspectionResult(),
    val otherEquipmentBindingBolts: ForkliftInspectionResult = ForkliftInspectionResult(),
    val otherEquipmentOperatorSeat: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverCoolingSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverLubricantSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverFuelSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverAirIntakeSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverExhaustSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverStarterSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalBattery: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalStartingDynamo: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalAlternator: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalBatteryCable: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalWiring: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalLighting: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalSafetyLights: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalHorn: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalFuse: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardTemperatureIndicator: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardEngineOilPressure: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardHydraulicPressure: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardHourMeter: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardGlowPlug: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardFuelIndicator: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardLoadIndicator: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardLoadChart: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardAmpereMeter: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringWheel: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringRod: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringGearBox: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringPitmanArm: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringDragLink: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringTieRod: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringLubrication: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelFront: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelRear: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelBindingBolts: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelHub: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelLubrication: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelMechanicalEquipment: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchHousing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchTransmissionOil: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchTransmissionLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchConnectingShaft: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchMechanicalEquipment: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialHousing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialOil: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialConnectingShaft: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeMainCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeHandbrakeCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeEmergencyCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeMechanicalComponents: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainTransmissionHousing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainTransmissionOil: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainTransmissionLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainTransmissionMechanicalEquipment: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastWear: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastLubrication: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastShaftAndBearing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentLiftChainCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentLiftChainDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentLiftChainLubrication: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketWorkFloorCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketWorkFloorCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketWorkFloorDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketWorkFloorBinding: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameCrossBracing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameDiagonalBracing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketBindingBoltCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketBindingBoltCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketBindingBoltDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketBindingBoltBinding: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketDoorCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketDoorCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketDoorDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketDoorBinding: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailWear: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailRailStraightness: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailRailJoint: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailAlignmentBetweenRails: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailGapBetweenRailJoints: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailRailFastener: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailRailStopper: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankOilLevel: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankOilCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankSuctionLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankReturnLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpSuctionLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpPressureLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpAbnormalNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveReliefValveFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveAbnormalNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveLiftCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveTiltCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveSteeringCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val actuatorLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val actuatorLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val actuatorAbnormalNoise: ForkliftInspectionResult = ForkliftInspectionResult()
)

@Immutable
data class ForkliftNde(
    val liftingChainInspection: ForkliftNdeChainInspection = ForkliftNdeChainInspection(),
    val forkNDT: ForkliftNdeFork = ForkliftNdeFork()
)

@Immutable
data class ForkliftNdeChainInspection(
    val items: ImmutableList<ForkliftNdeChainItem> = persistentListOf()
)

@Immutable
data class ForkliftNdeChainItem(
    val chain: String = "",
    val typeAndConstruction: String = "",
    val standardPitchMm: String = "",
    val measuredPitchMm: String = "",
    val standardPinMm: String = "",
    val measuredPinMm: String = "",
    val remarks: String = ""
)

@Immutable
data class ForkliftNdeFork(
    val ndtType: String = "",
    val items: ImmutableList<ForkliftNdeForkItem> = persistentListOf()
)

@Immutable
data class ForkliftNdeForkItem(
    val partInspected: String = "",
    val location: String = "",
    val finding: ForkliftInspectionResult = ForkliftInspectionResult()
)

@Immutable
data class ForkliftTesting(
    val engineOnInspection: ForkliftEngineOnInspection = ForkliftEngineOnInspection(),
    val loadTest: ForkliftLoadTest = ForkliftLoadTest()
)

@Immutable
data class ForkliftEngineOnInspection(
    val dynamoStarter: ForkliftInspectionResult = ForkliftInspectionResult(),
    val instrumentIndicatorFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val electricalEquipmentFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val engineOilLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val fuelLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val coolantLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicOilLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val transmissionOilLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val finalDriveOilLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val brakeFluidLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val clutchFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val transmissionFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val brakeFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hornAlarmFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val lightsFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicSystemFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerSteeringFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val liftCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val tiltCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val exhaustGasCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlLeversFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val engineNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val turbochargerNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val transmissionNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val guardNoise: ForkliftInspectionResult = ForkliftInspectionResult()
)

@Immutable
data class ForkliftLoadTest(
    val items: ImmutableList<ForkliftLoadTestItem> = persistentListOf()
)

@Immutable
data class ForkliftLoadTestItem(
    val forkLiftingHeight: String = "",
    val testLoad: String = "",
    val travellingSpeed: String = "",
    val movement: String = "",
    val result: String = "",
    val remarks: String = ""
)

@Immutable
data class ForkliftConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val recommendations: ImmutableList<String> = persistentListOf()
)