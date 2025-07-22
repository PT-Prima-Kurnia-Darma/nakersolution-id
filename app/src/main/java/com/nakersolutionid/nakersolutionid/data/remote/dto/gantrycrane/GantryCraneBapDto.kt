package com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane

import com.google.gson.annotations.SerializedName

// Data DTO for single Gantry Crane BAP response
data class GantryCraneBapSingleReportResponseData(
    @SerializedName("bap") val bap: GantryCraneBapReportData
)

// Data DTO for list of Gantry Crane BAP reports response
data class GantryCraneBapListReportResponseData(
    @SerializedName("bap") val bap: List<GantryCraneBapReportData>
)

// Main DTO for Gantry Crane BAP (used for create, update, and individual get)
data class GantryCraneBapReportData(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("generalData") val generalData: GantryCraneBapGeneralData,
    @SerializedName("technicalData") val technicalData: GantryCraneBapTechnicalData,
    @SerializedName("inspectionResult") val inspectionResult: GantryCraneBapInspectionResult,
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class GantryCraneBapGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("usageLocation") val usageLocation: String,
    @SerializedName("location") val location: String
)

data class GantryCraneBapTechnicalData(
    @SerializedName("brandOrType") val brandOrType: String,
    @SerializedName("manufacturerHoist") val manufacturerHoist: String,
    @SerializedName("manufactureStructure") val manufactureStructure: String,
    @SerializedName("manufactureYear") val manufactureYear: String,
    @SerializedName("manufactureCountry") val manufactureCountry: String,
    @SerializedName("serialNumber") val serialNumber: String,
    @SerializedName("maxLiftingCapacityKg") val maxLiftingCapacityKg: String,
    @SerializedName("liftingSpeedMpm") val liftingSpeedMpm: String
)

data class GantryCraneBapInspectionResult(
    @SerializedName("visualCheck") val visualCheck: GantryCraneBapVisualCheck,
    @SerializedName("functionalTest") val functionalTest: GantryCraneBapFunctionalTest,
    @SerializedName("ndtTest") val ndtTest: GantryCraneBapNdtTest,
    @SerializedName("loadTest") val loadTest: GantryCraneBapLoadTest
)

data class GantryCraneBapVisualCheck(
    @SerializedName("isMainStructureGood") val isMainStructureGood: Boolean,
    @SerializedName("areBoltsAndNutsSecure") val areBoltsAndNutsSecure: Boolean,
    @SerializedName("isWireRopeGoodCondition") val isWireRopeGoodCondition: Boolean,
    @SerializedName("isHookGoodCondition") val isHookGoodCondition: Boolean,
    @SerializedName("isGearboxGoodCondition") val isGearboxGoodCondition: Boolean,
    @SerializedName("hasGearboxOilLeak") val hasGearboxOilLeak: Boolean,
    @SerializedName("isWarningLampGoodCondition") val isWarningLampGoodCondition: Boolean,
    @SerializedName("isCapacityMarkingDisplayed") val isCapacityMarkingDisplayed: Boolean
)

data class GantryCraneBapFunctionalTest(
    @SerializedName("isForwardReverseFunctionOk") val isForwardReverseFunctionOk: Boolean,
    @SerializedName("isHoistingFunctionOk") val isHoistingFunctionOk: Boolean,
    @SerializedName("isLimitSwitchFunctional") val isLimitSwitchFunctional: Boolean
)

data class GantryCraneBapNdtTest(
    @SerializedName("method") val method: String,
    @SerializedName("isNdtResultGood") val isNdtResultGood: Boolean
)

data class GantryCraneBapLoadTest(
    @SerializedName("loadKg") val loadKg: String,
    @SerializedName("liftHeightMeters") val liftHeightMeters: String,
    @SerializedName("holdTimeSeconds") val holdTimeSeconds: String,
    @SerializedName("isLoadTestResultGood") val isLoadTestResultGood: Boolean
)
