package com.nakersolutionid.nakersolutionid.data.remote.dto.diesel

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus // Mengimpor ResultStatus dari common package

data class DieselReportRequest(
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String, // String karena bisa "" di body
    @SerializedName("generalData") val generalData: DieselGeneralData,
    @SerializedName("technicalData") val technicalData: DieselTechnicalData,
    @SerializedName("visualChecks") val visualChecks: DieselVisualChecks,
    @SerializedName("tests") val tests: DieselTests,
    @SerializedName("electricalComponents") val electricalComponents: DieselElectricalComponents,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String,
    @SerializedName("inspectionDate") val inspectionDate: String, // Field ini ada di root level response, perlu ditambahkan ke request dan report data
    @SerializedName("mcbCalculation") val mcbCalculation: DieselMcbCalculation,
    @SerializedName("noiseMeasurement") val noiseMeasurement: DieselNoiseMeasurement,
    @SerializedName("lightingMeasurement") val lightingMeasurement: DieselLightingMeasurement
)

// Data DTO for single Diesel Report response
data class DieselSingleReportResponseData(
    @SerializedName("laporan") val laporan: DieselReportData
)

// Data DTO for list of Diesel Reports response
data class DieselListReportResponseData(
    @SerializedName("laporan") val laporan: List<DieselReportData>
)

// Main DTO for Diesel Report (used for create, update, and individual get)
data class DieselReportData(
    @SerializedName("id") val id: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("extraId") val extraId: Long, // extraId adalah Int murni
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: DieselGeneralData,
    @SerializedName("technicalData") val technicalData: DieselTechnicalData,
    @SerializedName("visualChecks") val visualChecks: DieselVisualChecks,
    @SerializedName("tests") val tests: DieselTests,
    @SerializedName("electricalComponents") val electricalComponents: DieselElectricalComponents,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("mcbCalculation") val mcbCalculation: DieselMcbCalculation,
    @SerializedName("noiseMeasurement") val noiseMeasurement: DieselNoiseMeasurement,
    @SerializedName("lightingMeasurement") val lightingMeasurement: DieselLightingMeasurement,
    @SerializedName("subInspectionType") val subInspectionType: String, // Dari JSON respons
    @SerializedName("documentType") val documentType: String // Dari JSON respons
)

data class DieselGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("userInCharge") val userInCharge: String,
    @SerializedName("userAddressInCharge") val userAddressInCharge: String,
    @SerializedName("subcontractorPersonInCharge") val subcontractorPersonInCharge: String,
    @SerializedName("unitLocation") val unitLocation: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("brandType") val brandType: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String, // String karena "500 KVA / 1500 RPM"
    @SerializedName("intendedUse") val intendedUse: String,
    @SerializedName("pjk3SkpNo") val pjk3SkpNo: String,
    @SerializedName("ak3SkpNo") val ak3SkpNo: String,
    @SerializedName("portableOrStationer") val portableOrStationer: String,
    @SerializedName("usagePermitNumber") val usagePermitNumber: String,
    @SerializedName("operatorName") val operatorName: String,
    @SerializedName("equipmentHistory") val equipmentHistory: String
)

data class DieselTechnicalData(
    @SerializedName("dieselMotor") val dieselMotor: DieselMotor,
    @SerializedName("generator") val generator: DieselGenerator
)

data class DieselMotor(
    @SerializedName("brandModel") val brandModel: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("classification") val classification: String,
    @SerializedName("serialNumber") val serialNumber: String,
    @SerializedName("powerRpm") val powerRpm: String, // String karena "450 kW / 1500 RPM"
    @SerializedName("startingPower") val startingPower: String,
    @SerializedName("cylinderCount") val cylinderCount: String // String karena "6" diapit kutip
)

data class DieselGenerator(
    @SerializedName("brandType") val brandType: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("serialNumber") val serialNumber: String,
    @SerializedName("power") val power: String, // String karena "500 KVA"
    @SerializedName("frequency") val frequency: String, // String karena "50 Hz"
    @SerializedName("rpm") val rpm: String, // String karena "1500 RPM"
    @SerializedName("voltage") val voltage: String, // String karena "220/380 V"
    @SerializedName("powerFactor") val powerFactor: String, // String karena "0.8" diapit kutip
    @SerializedName("current") val current: String // String karena "760 A"
)

data class DieselVisualChecks(
    @SerializedName("basicConstruction") val basicConstruction: DieselBasicConstruction,
    @SerializedName("structuralConstruction") val structuralConstruction: DieselStructuralConstruction,
    @SerializedName("lubricationSystem") val lubricationSystem: DieselLubricationSystem,
    @SerializedName("fuelSystem") val fuelSystem: DieselFuelSystem,
    @SerializedName("startingAid") val startingAid: DieselStartingAid,
    @SerializedName("coolingSystem") val coolingSystem: DieselCoolingSystem,
    @SerializedName("airCirculationSystem") val airCirculationSystem: DieselAirCirculationSystem,
    @SerializedName("mainParts") val mainParts: DieselMainParts,
    @SerializedName("generatorParts") val generatorParts: DieselGeneratorParts,
    @SerializedName("transmission") val transmission: DieselTransmission,
    @SerializedName("mdp") val mdp: DieselMdp,
    @SerializedName("safetyDevice") val safetyDevice: DieselSafetyDevice
)

data class DieselBasicConstruction(
    @SerializedName("foundation") val foundation: DieselStatusResult,
    @SerializedName("dieselHousing") val dieselHousing: DieselStatusResult,
    @SerializedName("support") val support: DieselStatusResult,
    @SerializedName("anchorBolt") val anchorBolt: DieselStatusResult
)

data class DieselStructuralConstruction(
    @SerializedName("dailyTank") val dailyTank: DieselStatusResult,
    @SerializedName("muffler") val muffler: DieselStatusResult,
    @SerializedName("airVessel") val airVessel: DieselStatusResult,
    @SerializedName("panel") val panel: DieselStatusResult
)

data class DieselLubricationSystem(
    @SerializedName("oil") val oil: DieselStatusResult,
    @SerializedName("oilStrainer") val oilStrainer: DieselStatusResult,
    @SerializedName("oilCooler") val oilCooler: DieselStatusResult,
    @SerializedName("oilFilter") val oilFilter: DieselStatusResult,
    @SerializedName("byPassFilter") val byPassFilter: DieselStatusResult,
    @SerializedName("safetyValve") val safetyValve: DieselStatusResult,
    @SerializedName("packing") val packing: DieselStatusResult
)

data class DieselFuelSystem(
    @SerializedName("dailyTank") val dailyTank: DieselStatusResult,
    @SerializedName("fuelInjector") val fuelInjector: DieselStatusResult,
    @SerializedName("connections") val connections: DieselStatusResult,
    @SerializedName("floatTank") val floatTank: DieselStatusResult,
    @SerializedName("fuelFilter") val fuelFilter: DieselStatusResult,
    @SerializedName("fuelPump") val fuelPump: DieselStatusResult,
    @SerializedName("magneticScreen") val magneticScreen: DieselStatusResult,
    @SerializedName("governor") val governor: DieselStatusResult,
    @SerializedName("throttleShaft") val throttleShaft: DieselStatusResult,
    @SerializedName("regulator") val regulator: DieselStatusResult,
    @SerializedName("shutOffValve") val shutOffValve: DieselStatusResult
)

data class DieselStartingAid(
    @SerializedName("feedPump") val feedPump: DieselStatusResult,
    @SerializedName("fuelValve") val fuelValve: DieselStatusResult,
    @SerializedName("primingPump") val primingPump: DieselStatusResult,
    @SerializedName("heaterPlug") val heaterPlug: DieselStatusResult,
    @SerializedName("heaterSwitch") val heaterSwitch: DieselStatusResult,
    @SerializedName("preHeater") val preHeater: DieselStatusResult,
    @SerializedName("waterSignal") val waterSignal: DieselStatusResult,
    @SerializedName("startingSwitch") val startingSwitch: DieselStatusResult,
    @SerializedName("batteryPoles") val batteryPoles: DieselStatusResult,
    @SerializedName("thermostartTank") val thermostartTank: DieselStatusResult,
    @SerializedName("thermostart") val thermostart: DieselStatusResult,
    @SerializedName("heaterSignal") val heaterSignal: DieselStatusResult,
    @SerializedName("thermostartSwitch") val thermostartSwitch: DieselStatusResult,
    @SerializedName("glowPlug") val glowPlug: DieselStatusResult,
    @SerializedName("speedSensor") val speedSensor: DieselStatusResult,
    @SerializedName("serviceMeter") val serviceMeter: DieselStatusResult,
    @SerializedName("tempSensor") val tempSensor: DieselStatusResult,
    @SerializedName("startingMotor") val startingMotor: DieselStatusResult
)

data class DieselCoolingSystem(
    @SerializedName("coolingWater") val coolingWater: DieselStatusResult,
    @SerializedName("bolts") val bolts: DieselStatusResult,
    @SerializedName("clamps") val clamps: DieselStatusResult,
    @SerializedName("radiator") val radiator: DieselStatusResult,
    @SerializedName("thermostat") val thermostat: DieselStatusResult,
    @SerializedName("fan") val fan: DieselStatusResult,
    @SerializedName("fanGuard") val fanGuard: DieselStatusResult,
    @SerializedName("fanRotation") val fanRotation: DieselStatusResult,
    @SerializedName("bearing") val bearing: DieselStatusResult
)

data class DieselAirCirculationSystem(
    @SerializedName("preCleaner") val preCleaner: DieselStatusResult,
    @SerializedName("dustIndicator") val dustIndicator: DieselStatusResult,
    @SerializedName("airCleaner") val airCleaner: DieselStatusResult,
    @SerializedName("turboCharger") val turboCharger: DieselStatusResult,
    @SerializedName("clamps") val clamps: DieselStatusResult,
    @SerializedName("afterCooler") val afterCooler: DieselStatusResult,
    @SerializedName("muffler") val muffler: DieselStatusResult,
    @SerializedName("silencer") val silencer: DieselStatusResult,
    @SerializedName("heatDamper") val heatDamper: DieselStatusResult,
    @SerializedName("bolts") val bolts: DieselStatusResult
)

data class DieselMainParts(
    @SerializedName("damperBolts") val damperBolts: DieselStatusResult,
    @SerializedName("support") val support: DieselStatusResult,
    @SerializedName("flyWheelHousing") val flyWheelHousing: DieselStatusResult,
    @SerializedName("flyWheel") val flyWheel: DieselStatusResult,
    @SerializedName("vibrationDamper") val vibrationDamper: DieselStatusResult,
    @SerializedName("beltAndPulley") val beltAndPulley: DieselStatusResult,
    @SerializedName("crankshaft") val crankshaft: DieselStatusResult
)

data class DieselGeneratorParts(
    @SerializedName("terminal") val terminal: DieselStatusResult,
    @SerializedName("cable") val cable: DieselStatusResult,
    @SerializedName("panel") val panel: DieselStatusResult,
    @SerializedName("ampereMeter") val ampereMeter: DieselStatusResult,
    @SerializedName("voltMeter") val voltMeter: DieselStatusResult,
    @SerializedName("frequencyMeter") val frequencyMeter: DieselStatusResult,
    @SerializedName("circuitBreaker") val circuitBreaker: DieselStatusResult,
    @SerializedName("onOffSwitch") val onOffSwitch: DieselStatusResult
)

data class DieselTransmission(
    @SerializedName("gear") val gear: DieselStatusResult,
    @SerializedName("belt") val belt: DieselStatusResult,
    @SerializedName("chain") val chain: DieselStatusResult
)

data class DieselMdp(
    @SerializedName("cable") val cable: DieselStatusResult,
    @SerializedName("condition") val condition: DieselStatusResult,
    @SerializedName("ampereMeter") val ampereMeter: DieselStatusResult,
    @SerializedName("voltMeter") val voltMeter: DieselStatusResult,
    @SerializedName("mainCircuitBreaker") val mainCircuitBreaker: DieselStatusResult
)

data class DieselSafetyDevice(
    @SerializedName("grounding") val grounding: DieselStatusResult,
    @SerializedName("lightningArrester") val lightningArrester: DieselStatusResult,
    @SerializedName("emergencyStop") val emergencyStop: DieselStatusResult,
    @SerializedName("governor") val governor: DieselStatusResult,
    @SerializedName("thermostat") val thermostat: DieselStatusResult,
    @SerializedName("waterSignal") val waterSignal: DieselStatusResult,
    @SerializedName("fanGuard") val fanGuard: DieselStatusResult,
    @SerializedName("silencer") val silencer: DieselStatusResult,
    @SerializedName("vibrationDamper") val vibrationDamper: DieselStatusResult,
    @SerializedName("circuitBreaker") val circuitBreaker: DieselStatusResult,
    @SerializedName("avr") val avr: DieselStatusResult
)

// Reusable data class for status and result pairs
data class DieselStatusResult(
    @SerializedName("status") val status: Boolean,
    @SerializedName("result") val result: String
)

data class DieselTests(
    @SerializedName("ndt") val ndt: DieselNdt,
    @SerializedName("safetyDevice") val safetyDevice: DieselTestSafetyDevice
)

data class DieselNdt(
    @SerializedName("shaftRpm") val shaftRpm: DieselRemarksResult,
    @SerializedName("weldJoint") val weldJoint: DieselRemarksResult,
    @SerializedName("noise") val noise: DieselRemarksResult,
    @SerializedName("lighting") val lighting: DieselRemarksResult,
    @SerializedName("loadTest") val loadTest: DieselRemarksResult
)

data class DieselTestSafetyDevice(
    @SerializedName("governor") val governor: DieselRemarksResult,
    @SerializedName("emergencyStop") val emergencyStop: DieselRemarksResult,
    @SerializedName("grounding") val grounding: DieselRemarksResult,
    @SerializedName("indicatorPanel") val indicatorPanel: DieselRemarksResult,
    @SerializedName("pressureGauge") val pressureGauge: DieselRemarksResult,
    @SerializedName("tempIndicator") val tempIndicator: DieselRemarksResult,
    @SerializedName("waterIndicator") val waterIndicator: DieselRemarksResult,
    @SerializedName("safetyValves") val safetyValves: DieselRemarksResult,
    @SerializedName("radiator") val radiator: DieselRemarksResult
)

// Reusable data class for remarks and result pairs
data class DieselRemarksResult(
    @SerializedName("remarks") val remarks: String,
    @SerializedName("result") val result: String
)

data class DieselElectricalComponents(
    @SerializedName("panelControl") val panelControl: DieselPanelControl
)

data class DieselPanelControl(
    @SerializedName("ka") val ka: String,
    @SerializedName("voltage") val voltage: DieselVoltage,
    @SerializedName("powerInfo") val powerInfo: DieselPowerInfo
)

data class DieselVoltage(
    @SerializedName("rs") val rs: Int,
    @SerializedName("rt") val rt: Int,
    @SerializedName("st") val st: Int,
    @SerializedName("rn") val rn: Int,
    @SerializedName("rg") val rg: Int,
    @SerializedName("ng") val ng: Int
)

data class DieselPowerInfo(
    @SerializedName("frequency") val frequency: String, // String karena "50.1 HZ"
    @SerializedName("cosQ") val cosQ: Double,
    @SerializedName("ampere") val ampere: DieselAmpere,
    @SerializedName("result") val result: String
)

data class DieselAmpere(
    @SerializedName("r") val r: Int,
    @SerializedName("s") val s: Int,
    @SerializedName("t") val t: Int
)

data class DieselMcbCalculation(
    @SerializedName("phase") val phase: Int,
    @SerializedName("voltage") val voltage: String, // String karena "220/380"
    @SerializedName("cosQ") val cosQ: Double,
    @SerializedName("generatorPowerKva") val generatorPowerKva: Int,
    @SerializedName("generatorPowerKw") val generatorPowerKw: Int,
    @SerializedName("resultCalculation") val resultCalculation: Int,
    @SerializedName("requirementCalculation") val requirementCalculation: Int,
    @SerializedName("conclusion") val conclusion: String
)

data class DieselNoiseMeasurement(
    @SerializedName("pointA") val pointA: DieselMeasurementPoint,
    @SerializedName("pointB") val pointB: DieselMeasurementPoint,
    @SerializedName("pointC") val pointC: DieselMeasurementPoint,
    @SerializedName("pointD") val pointD: DieselMeasurementPoint
)

data class DieselLightingMeasurement(
    @SerializedName("pointA") val pointA: DieselMeasurementPoint,
    @SerializedName("pointB") val pointB: DieselMeasurementPoint,
    @SerializedName("pointC") val pointC: DieselMeasurementPoint,
    @SerializedName("pointD") val pointD: DieselMeasurementPoint
)

// Reusable data class for measurement points (noise/lighting)
data class DieselMeasurementPoint(
    @SerializedName("result") val result: Double,
    @SerializedName("status") val status: String
)