package com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Diesel Motor inspection report.
 * This class is immutable.
 */
@Immutable
data class DieselMotorUiState(
    val isLoading: Boolean = false,
    val inspectionReport: DieselMotorInspectionReport = DieselMotorInspectionReport()
)

@Immutable
data class DieselMotorInspectionReport(
    val equipmentType: String = "",
    val examinationType: String = "",
    val generalData: DieselMotorGeneralData = DieselMotorGeneralData(),
    val technicalData: DieselMotorTechnicalData = DieselMotorTechnicalData(),
    val visualInspection: DieselMotorVisualInspection = DieselMotorVisualInspection(),
    val testingAndMeasurement: DieselMotorTestingAndMeasurement = DieselMotorTestingAndMeasurement(),
    val mcbCalculation: DieselMotorMcbCalculation = DieselMotorMcbCalculation(),
    val noiseMeasurement: DieselMotorNoiseMeasurement = DieselMotorNoiseMeasurement(),
    val lightingMeasurement: DieselMotorLightingMeasurement = DieselMotorLightingMeasurement(),
    val conclusion: DieselMotorConclusion = DieselMotorConclusion()
)

@Immutable
data class DieselMotorGeneralData(
    val ownerName: String = "",
    val ownerAddress: String = "",
    val userInCharge: String = "",
    val userAddressInCharge: String = "",
    val subcontractorPersonInCharge: String = "",
    val unitLocation: String = "",
    val brandType: String = "",
    val driveType: String = "",
    val serialNumberUnitNumber: String = "",
    val manufacturer: String = "",
    val locationAndYearOfManufacture: String = "",
    val capacityRpm: String = "",
    val intendedUse: String = "",
    val pjk3SkpNo: String = "",
    val ak3SkpNo: String = "",
    val classification: String = "",
    val usagePermitNumber: String = "",
    val operatorName: String = "",
    val equipmentHistory: String = ""
)

@Immutable
data class DieselMotorTechnicalData(
    val dieselMotor: DieselMotorInfo = DieselMotorInfo(),
    val generator: DieselMotorGeneratorInfo = DieselMotorGeneratorInfo()
)

@Immutable
data class DieselMotorInfo(
    val brandModel: String = "",
    val manufacturer: String = "",
    val classification: String = "",
    val serialNumber: String = "",
    val powerRpm: String = "",
    val startingPower: String = "",
    val cylinderCount: String = ""
)

@Immutable
data class DieselMotorGeneratorInfo(
    val brandType: String = "",
    val manufacturer: String = "",
    val serialNumber: String = "",
    val power: String = "",
    val frequency: String = "",
    val rpm: String = "",
    val voltage: String = "",
    val powerFactor: String = "",
    val current: String = ""
)

@Immutable
data class DieselMotorConditionResult(
    val isGood: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class DieselMotorVisualInspection(
    val baseConstructionFoundation: DieselMotorConditionResult = DieselMotorConditionResult(),
    val baseConstructionDieselHousing: DieselMotorConditionResult = DieselMotorConditionResult(),
    val baseConstructionSupport: DieselMotorConditionResult = DieselMotorConditionResult(),
    val baseConstructionAnchorBolt: DieselMotorConditionResult = DieselMotorConditionResult(),
    val structureDailyTank: DieselMotorConditionResult = DieselMotorConditionResult(),
    val structureMuffler: DieselMotorConditionResult = DieselMotorConditionResult(),
    val structureAirVessel: DieselMotorConditionResult = DieselMotorConditionResult(),
    val structurePanel: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemOil: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemOilStrainer: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemOilCooler: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemOilFilter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemByPassFilter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemSafetyValve: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemPacking: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemDailyTank: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemFuelInjector: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemConnections: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemFloatTank: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemFuelFilter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemFuelInjectorPump: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemMagneticScreen: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemGovernor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemThrottleShaft: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemRegulator: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemShutOffValve: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemFeedPump: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemFuelValve: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemPrimingPump: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemHeaterPlug: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemHeaterSwitch: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemPreHeater: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemWaterSignal: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemSwitch: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemBatteryPoles: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemThermostartTank: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemThermostart: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemHeaterSignal: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemThermostartSwitch: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemGlowPlug: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemSpeedSensor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemServiceMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemTempSensor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemMotor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemWater: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemBolts: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemClamps: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemRadiator: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemThermostat: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemFan: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemFanGuard: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemFanRotation: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemBearing: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationPreCleaner: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationDustIndicator: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationAirCleaner: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationTurboCharger: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationClamps: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationAfterCooler: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationMuffler: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationSilencer: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationHeatDamper: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationBolts: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsDamperBolts: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsSupport: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsFlyWheelHousing: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsFlyWheel: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsVibrationDamper: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsBeltAndPulley: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsCrankshaft: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorTerminalConnection: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorCableToPanel: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorPanelBoard: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorAmpereMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorVoltMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorFrequencyMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorCircuitBreaker: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorOnOffSwitch: DieselMotorConditionResult = DieselMotorConditionResult(),
    val transmissionGear: DieselMotorConditionResult = DieselMotorConditionResult(),
    val transmissionBelt: DieselMotorConditionResult = DieselMotorConditionResult(),
    val transmissionChain: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpCableConnection: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpCondition: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpAmpereMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpVoltMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpMainCircuitBreaker: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyGrounding: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyLightningArrester: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyEmergencyStop: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyGovernor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyThermostat: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyWaterSignal: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyFanGuard: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetySilencer: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyVibrationDamper: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyCircuitBreaker: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyAvr: DieselMotorConditionResult = DieselMotorConditionResult()
)

@Immutable
data class DieselMotorTestingAndMeasurement(
    val ndtTests: DieselMotorNdtTests = DieselMotorNdtTests(),
    val safetyDeviceTests: DieselMotorSafetyDeviceTests = DieselMotorSafetyDeviceTests(),
    val electricalMeasurements: DieselMotorElectricalMeasurements = DieselMotorElectricalMeasurements()
)

@Immutable
data class DieselMotorTestResult(
    val result: String = "",
    val remarks: String = ""
)

@Immutable
data class DieselMotorNdtTests(
    val shaftRpm: DieselMotorTestResult = DieselMotorTestResult(),
    val weldJoint: DieselMotorTestResult = DieselMotorTestResult(),
    val noise: DieselMotorTestResult = DieselMotorTestResult(),
    val lighting: DieselMotorTestResult = DieselMotorTestResult(),
    val loadTest: DieselMotorTestResult = DieselMotorTestResult()
)

@Immutable
data class DieselMotorSafetyDeviceTests(
    val governor: DieselMotorTestResult = DieselMotorTestResult(),
    val emergencyStop: DieselMotorTestResult = DieselMotorTestResult(),
    val grounding: DieselMotorTestResult = DieselMotorTestResult(),
    val panelIndicators: DieselMotorTestResult = DieselMotorTestResult(),
    val pressureGauge: DieselMotorTestResult = DieselMotorTestResult(),
    val temperatureIndicator: DieselMotorTestResult = DieselMotorTestResult(),
    val waterIndicator: DieselMotorTestResult = DieselMotorTestResult(),
    val safetyValves: DieselMotorTestResult = DieselMotorTestResult(),
    val radiator: DieselMotorTestResult = DieselMotorTestResult()
)

@Immutable
data class DieselMotorElectricalMeasurements(
    val panelControl: DieselMotorPanelControl = DieselMotorPanelControl(),
    val powerInfo: DieselMotorPowerInfo = DieselMotorPowerInfo()
)

@Immutable
data class DieselMotorPanelControl(
    val ka: String = "",
    val voltageRS: String = "",
    val voltageRT: String = "",
    val voltageST: String = "",
    val voltageRN: String = "",
    val voltageRG: String = "",
    val voltageNG: String = ""
)

@Immutable
data class DieselMotorPowerInfo(
    val frequency: String = "",
    val cosQ: String = "",
    val ampereR: String = "",
    val ampereS: String = "",
    val ampereT: String = "",
    val remarks: String = ""
)

@Immutable
data class DieselMotorMcbCalculation(
    val known: DieselMotorMcbKnownData = DieselMotorMcbKnownData(),
    val calculation: DieselMotorMcbCalculationResult = DieselMotorMcbCalculationResult(),
    val conclusion: String = ""
)

@Immutable
data class DieselMotorMcbKnownData(
    val phase: String = "",
    val voltage: String = "",
    val cosQ: String = "",
    val generatorPowerKva: String = "",
    val generatorPowerKw: String = ""
)

@Immutable
data class DieselMotorMcbCalculationResult(
    val formula: String = "I = P / (V * CosQ * sqrt(3))",
    val resultA: String = "",
    val requiredAmps: String = ""
)

@Immutable
data class DieselMotorMeasurement<T>(
    val location: String = "",
    val measurements: ImmutableList<T> = persistentListOf(),
    val analysis: DieselMotorMeasurementAnalysis = DieselMotorMeasurementAnalysis()
)

@Immutable
data class DieselMotorNoiseMeasurementPoint(
    val point: String = "",
    val valueDb: String = ""
)

@Immutable
data class DieselMotorLightingMeasurementPoint(
    val point: String = "",
    val valueLux: String = ""
)

@Immutable
data class DieselMotorMeasurementAnalysis(
    val standard: String = "",
    val result: String = ""
)

typealias DieselMotorNoiseMeasurement = DieselMotorMeasurement<DieselMotorNoiseMeasurementPoint>
typealias DieselMotorLightingMeasurement = DieselMotorMeasurement<DieselMotorLightingMeasurementPoint>

@Immutable
data class DieselMotorConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val requirements: ImmutableList<String> = persistentListOf()
)