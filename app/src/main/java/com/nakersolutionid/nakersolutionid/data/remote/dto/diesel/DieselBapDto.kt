package com.nakersolutionid.nakersolutionid.data.remote.dto.diesel

import com.google.gson.annotations.SerializedName

data class DieselBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("generalData") val generalData: DieselBapGeneralData,
    @SerializedName("technicalData") val technicalData: DieselBapTechnicalData,
    @SerializedName("visualChecks") val visualChecks: DieselBapVisualChecks,
    @SerializedName("functionalTests") val functionalTests: DieselBapFunctionalTests
)

// Data DTO for single Diesel BAP response
data class DieselBapSingleReportResponseData(
    @SerializedName("bap") val bap: DieselBapReportData
)

// Data DTO for list of Diesel BAP reports response
data class DieselBapListReportResponseData(
    @SerializedName("bap") val bap: List<DieselBapReportData>
)

// Main DTO for Diesel BAP (used for create, update, and individual get)
data class DieselBapReportData(
    @SerializedName("id") val id: String, // Dari JSON respons
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("createdAt") val createdAt: String, // Dari JSON respons
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("generalData") val generalData: DieselBapGeneralData,
    @SerializedName("technicalData") val technicalData: DieselBapTechnicalData,
    @SerializedName("visualChecks") val visualChecks: DieselBapVisualChecks,
    @SerializedName("functionalTests") val functionalTests: DieselBapFunctionalTests,
    @SerializedName("subInspectionType") val subInspectionType: String, // Dari JSON respons
    @SerializedName("documentType") val documentType: String // Dari JSON respons
)

data class DieselBapGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("unitLocation") val unitLocation: String,
    @SerializedName("userAddressInCharge") val userAddressInCharge: String
)

data class DieselBapTechnicalData(
    @SerializedName("brandType") val brandType: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String, // String karena "500 KVA"
    @SerializedName("technicalDataDieselMotorPowerRpm") val technicalDataDieselMotorPowerRpm: String, // String karena "450" (diapit kutip)
    @SerializedName("specialSpecification") val specialSpecification: String,
    @SerializedName("dimensionsDescription") val dimensionsDescription: String, // String karena "4m x 1.5m x 2m"
    @SerializedName("rotationRpm") val rotationRpm: String, // String karena "1500" (diapit kutip)
    @SerializedName("technicalDataGeneratorFrequency") val technicalDataGeneratorFrequency: String, // String karena "50" (diapit kutip)
    @SerializedName("technicalDataGeneratorCurrent") val technicalDataGeneratorCurrent: String, // String karena "760" (diapit kutip)
    @SerializedName("machineWeightKg") val machineWeightKg: String, // String karena "4500" (diapit kutip)
    @SerializedName("areSafetyFeaturesInstalled") val areSafetyFeaturesInstalled: Boolean
)

data class DieselBapVisualChecks(
    @SerializedName("isMachineGoodCondition") val isMachineGoodCondition: Boolean,
    @SerializedName("areElectricalIndicatorsGood") val areElectricalIndicatorsGood: Boolean,
    @SerializedName("isAparAvailable") val isAparAvailable: Boolean,
    @SerializedName("isPpeAvailable") val isPpeAvailable: Boolean,
    @SerializedName("isGroundingInstalled") val isGroundingInstalled: Boolean,
    @SerializedName("isBatteryGoodCondition") val isBatteryGoodCondition: Boolean,
    @SerializedName("hasLubricationLeak") val hasLubricationLeak: Boolean,
    @SerializedName("isFoundationGoodCondition") val isFoundationGoodCondition: Boolean,
    @SerializedName("hasHydraulicLeak") val hasHydraulicLeak: Boolean
)

data class DieselBapFunctionalTests(
    @SerializedName("isLightingCompliant") val isLightingCompliant: Boolean,
    @SerializedName("isNoiseLevelCompliant") val isNoiseLevelCompliant: Boolean,
    @SerializedName("isEmergencyStopFunctional") val isEmergencyStopFunctional: Boolean,
    @SerializedName("isMachineFunctional") val isMachineFunctional: Boolean,
    @SerializedName("isVibrationLevelCompliant") val isVibrationLevelCompliant: Boolean,
    @SerializedName("isInsulationResistanceOk") val isInsulationResistanceOk: Boolean,
    @SerializedName("isShaftRotationCompliant") val isShaftRotationCompliant: Boolean,
    @SerializedName("isGroundingResistanceCompliant") val isGroundingResistanceCompliant: Boolean,
    @SerializedName("isNdtWeldTestOk") val isNdtWeldTestOk: Boolean,
    @SerializedName("isVoltageBetweenPhasesNormal") val isVoltageBetweenPhasesNormal: Boolean,
    @SerializedName("isPhaseLoadBalanced") val isPhaseLoadBalanced: Boolean
)