package com.nakersolutionid.nakersolutionid.data.remote.dto.machine

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus // Mengimpor ResultStatus dari common package

data class MachineReportRequest(
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: MachineGeneralData,
    @SerializedName("technicalData") val technicalData: MachineTechnicalData,
    @SerializedName("visualInspection") val visualInspection: MachineVisualInspection,
    @SerializedName("testingAndMeasurement") val testingAndMeasurement: MachineTestingAndMeasurement,
    @SerializedName("electricalPanelComponents") val electricalPanelComponents: MachineElectricalPanelComponents,
    @SerializedName("conclusionAndRecommendation") val conclusionAndRecommendation: MachineConclusionAndRecommendation,
    @SerializedName("administration") val administration: MachineAdministration,
    @SerializedName("foundationAnalysis") val foundationAnalysis: MachineFoundationAnalysis,
    @SerializedName("environmentalMeasurement") val environmentalMeasurement: MachineEnvironmentalMeasurement
)

// Data DTO for single Machine Report response
data class MachineSingleReportResponseData(
    @SerializedName("laporan") val laporan: MachineReportData
)

// Data DTO for list of Machine Reports response
data class MachineListReportResponseData(
    @SerializedName("laporan") val laporan: List<MachineReportData>
)

// Main DTO for Machine Report (used for create, update, and individual get)
data class MachineReportData(
    @SerializedName("id") val id: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String, // String (timestamp)
    @SerializedName("generalData") val generalData: MachineGeneralData,
    @SerializedName("technicalData") val technicalData: MachineTechnicalData,
    @SerializedName("visualInspection") val visualInspection: MachineVisualInspection,
    @SerializedName("testingAndMeasurement") val testingAndMeasurement: MachineTestingAndMeasurement,
    @SerializedName("electricalPanelComponents") val electricalPanelComponents: MachineElectricalPanelComponents,
    @SerializedName("conclusionAndRecommendation") val conclusionAndRecommendation: MachineConclusionAndRecommendation,
    @SerializedName("administration") val administration: MachineAdministration,
    @SerializedName("foundationAnalysis") val foundationAnalysis: MachineFoundationAnalysis,
    @SerializedName("environmentalMeasurement") val environmentalMeasurement: MachineEnvironmentalMeasurement,
    @SerializedName("subInspectionType") val subInspectionType: String, // Dari JSON respons
    @SerializedName("documentType") val documentType: String // Dari JSON respons
)

data class MachineGeneralData(
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
    @SerializedName("technicalDataDieselMotorPowerRpm") val technicalDataDieselMotorPowerRpm: String, // String karena "750 kVA / 1500 RPM"
    @SerializedName("intendedUse") val intendedUse: String,
    @SerializedName("pjk3SkpNo") val pjk3SkpNo: String,
    @SerializedName("ak3SkpNo") val ak3SkpNo: String,
    @SerializedName("usagePermitNumber") val usagePermitNumber: String,
    @SerializedName("operatorName") val operatorName: String,
    @SerializedName("equipmentHistory") val equipmentHistory: String
)

data class MachineTechnicalData(
    @SerializedName("machineSpecification") val machineSpecification: MachineSpecification,
    @SerializedName("foundationDimension") val foundationDimension: MachineFoundationDimension
)

data class MachineSpecification(
    @SerializedName("brandType") val brandType: String,
    @SerializedName("technicalDataMaxFeederSpeed") val technicalDataMaxFeederSpeed: String?, // null di JSON
    @SerializedName("technicalDataMaxPlateWidth") val technicalDataMaxPlateWidth: String?, // null di JSON
    @SerializedName("technicalDataPlateThickness") val technicalDataPlateThickness: String?, // null di JSON
    @SerializedName("technicalDataMaxPlateWeight") val technicalDataMaxPlateWeight: String?, // null di JSON
    @SerializedName("technicalDataMaxInnerCoilDiameter") val technicalDataMaxInnerCoilDiameter: String?, // null di JSON
    @SerializedName("technicalDataMaxOuterCoilDiameter") val technicalDataMaxOuterCoilDiameter: String?, // null di JSON
    @SerializedName("technicalDataDriveMotor") val technicalDataDriveMotor: String,
    @SerializedName("technicalDataDieselMotorPowerRpm") val technicalDataDieselMotorPowerRpm: Int, // Int murni
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("technicalDataMachineWeight") val technicalDataMachineWeight: Int, // Int murni
    @SerializedName("technicalDataOverallDimension") val technicalDataOverallDimension: String
)

data class MachineFoundationDimension(
    @SerializedName("technicalDataFoundationDim") val technicalDataFoundationDim: String,
    @SerializedName("technicalDataFoundationDistance") val technicalDataFoundationDistance: String,
    @SerializedName("technicalDataVibrationDamperType") val technicalDataVibrationDamperType: String,
    @SerializedName("technicalDataFoundationWeight1") val technicalDataFoundationWeight1: Int, // Int murni
    @SerializedName("technicalDataFoundationWeight2") val technicalDataFoundationWeight2: Int // Int murni
)

data class MachineVisualInspection(
    @SerializedName("foundation") val foundation: MachineStatusResultDetail,
    @SerializedName("foundationBearing") val foundationBearing: MachineStatusResultDetail,
    @SerializedName("machineFrame") val machineFrame: MachineFrame,
    @SerializedName("roller") val roller: MachineStatusResultDetail, // status bisa null
    @SerializedName("controlPanel") val controlPanel: MachineStatusResultDetail,
    @SerializedName("display") val display: MachineStatusResultDetail,
    @SerializedName("operationButtons") val operationButtons: MachineStatusResultDetail,
    @SerializedName("electricalComponents") val electricalComponents: MachineElectricalComponentsVisual,
    @SerializedName("safetyDevices") val safetyDevices: MachineSafetyDevicesVisual,
    @SerializedName("hydraulic") val hydraulic: MachineHydraulicVisual
)

// Reusable data class for status (nullable) and result
data class MachineStatusResultDetail(
    @SerializedName("status") val status: Boolean?, // Bisa null
    @SerializedName("result") val result: String
)

data class MachineFrame(
    @SerializedName("mainFrame") val mainFrame: MachineStatusResultDetail,
    @SerializedName("braceFrame") val braceFrame: MachineStatusResultDetail
)

data class MachineElectricalComponentsVisual(
    @SerializedName("measurements") val measurements: MachineElectricalMeasurements,
    @SerializedName("voltage") val voltage: MachineStatusResultDetail,
    @SerializedName("power") val power: MachineStatusResultDetail,
    @SerializedName("phase") val phase: MachineStatusResultDetail,
    @SerializedName("frequency") val frequency: MachineStatusResultDetail,
    @SerializedName("current") val current: MachineStatusResultDetail,
    @SerializedName("electricalPanel") val electricalPanel: MachineStatusResultDetail,
    @SerializedName("conductor") val conductor: MachineStatusResultDetail,
    @SerializedName("insulation") val insulation: MachineStatusResultDetail
)

data class MachineElectricalMeasurements(
    @SerializedName("electricVoltage") val electricVoltage: Int,
    @SerializedName("electricPhase") val electricPhase: Int,
    @SerializedName("electricFrequency") val electricFrequency: Int,
    @SerializedName("electricAmper") val electricAmper: Int
)

data class MachineSafetyDevicesVisual(
    @SerializedName("limitSwitchUp") val limitSwitchUp: MachineStatusResultDetail, // status bisa null
    @SerializedName("limitSwitchDown") val limitSwitchDown: MachineStatusResultDetail, // status bisa null
    @SerializedName("grounding") val grounding: MachineStatusResultDetail,
    @SerializedName("safetyGuard") val safetyGuard: MachineStatusResultDetail,
    @SerializedName("stampLock") val stampLock: MachineStatusResultDetail, // status bisa null
    @SerializedName("pressureIndicator") val pressureIndicator: MachineStatusResultDetail,
    @SerializedName("emergencyStop") val emergencyStop: MachineStatusResultDetail,
    @SerializedName("handSensor") val handSensor: MachineStatusResultDetail // status bisa null
)

data class MachineHydraulicVisual(
    @SerializedName("pump") val pump: MachineStatusResultDetail, // status bisa null
    @SerializedName("hose") val hose: MachineStatusResultDetail
)

data class MachineTestingAndMeasurement(
    @SerializedName("safetyDeviceTest") val safetyDeviceTest: MachineSafetyDeviceTest,
    @SerializedName("speedTest") val speedTest: MachineStatusResultDetail,
    @SerializedName("functionTest") val functionTest: MachineStatusResultDetail,
    @SerializedName("weldJointTest") val weldJointTest: MachineStatusResultDetail,
    @SerializedName("vibrationTest") val vibrationTest: MachineStatusResultDetail,
    @SerializedName("lightingTest") val lightingTest: MachineStatusResultDetail,
    @SerializedName("noiseTest") val noiseTest: MachineStatusResultDetail
)

data class MachineSafetyDeviceTest(
    @SerializedName("grounding") val grounding: MachineStatusResultDetail,
    @SerializedName("safetyGuard") val safetyGuard: MachineStatusResultDetail,
    @SerializedName("roller") val roller: MachineStatusResultDetail, // status bisa null
    @SerializedName("emergencyStop") val emergencyStop: MachineStatusResultDetail
)

data class MachineElectricalPanelComponents(
    @SerializedName("ka") val ka: String,
    @SerializedName("voltage") val voltage: MachineVoltageMeasurement,
    @SerializedName("powerInfo") val powerInfo: MachinePowerInfo
)

data class MachineVoltageMeasurement(
    @SerializedName("rs") val rs: Int,
    @SerializedName("rt") val rt: Int,
    @SerializedName("st") val st: Int,
    @SerializedName("rn") val rn: Int,
    @SerializedName("rg") val rg: Double,
    @SerializedName("ng") val ng: Double
)

data class MachinePowerInfo(
    @SerializedName("frequency") val frequency: Double, // Double murni
    @SerializedName("cosQ") val cosQ: Double,
    @SerializedName("ampere") val ampere: MachineAmpereMeasurement,
    @SerializedName("result") val result: String
)

data class MachineAmpereMeasurement(
    @SerializedName("r") val r: Int,
    @SerializedName("s") val s: Int,
    @SerializedName("t") val t: Int
)

data class MachineConclusionAndRecommendation(
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String
)

data class MachineAdministration(
    @SerializedName("inspectionDate") val inspectionDate: String // String (contoh: "26 Juli 2024")
)

data class MachineFoundationAnalysis(
    @SerializedName("actualWeight") val actualWeight: Double,
    @SerializedName("additionalMeterials") val additionalMeterials: Int,
    @SerializedName("totalWeight") val totalWeight: Double,
    @SerializedName("minimumFoundationWeight") val minimumFoundationWeight: Double,
    @SerializedName("totalMinimumFoundationWeight") val totalMinimumFoundationWeight: Double,
    @SerializedName("foundationWeight") val foundationWeight: Double,
    @SerializedName("heightFoundation") val heightFoundation: Double,
    @SerializedName("foundationAnalysisResult") val foundationAnalysisResult: String
)

data class MachineEnvironmentalMeasurement(
    @SerializedName("noise") val noise: MachineMeasurementPoints,
    @SerializedName("lighting") val lighting: MachineMeasurementPoints
)

data class MachineMeasurementPoints(
    @SerializedName("pointA") val pointA: MachineMeasurementPoint,
    @SerializedName("pointB") val pointB: MachineMeasurementPoint,
    @SerializedName("pointC") val pointC: MachineMeasurementPoint,
    @SerializedName("pointD") val pointD: MachineMeasurementPoint
)

data class MachineMeasurementPoint(
    @SerializedName("result") val result: Double,
    @SerializedName("status") val status: String
)