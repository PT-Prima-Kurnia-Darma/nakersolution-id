package com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane

import com.google.gson.annotations.SerializedName

data class MobileCraneBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: MobileCraneBapGeneralData,
    @SerializedName("technicalData") val technicalData: MobileCraneBapTechnicalData,
    @SerializedName("inspectionResult") val inspectionResult: MobileCraneBapInspectionResult,
    @SerializedName("signature") val signature: MobileCraneBapSignature,
)

// Data DTO for single Mobile Crane BAP response
data class MobileCraneBapSingleReportResponseData(
    @SerializedName("bap") val bap: MobileCraneBapReportData
)

// Data DTO for list of Mobile Crane BAP reports response
data class MobileCraneBapListReportResponseData(
    @SerializedName("bap") val bap: List<MobileCraneBapReportData>
)

// Main DTO for Mobile Crane BAP (used for create, update, and individual get)
data class MobileCraneBapReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: MobileCraneBapGeneralData,
    @SerializedName("technicalData") val technicalData: MobileCraneBapTechnicalData,
    @SerializedName("inspectionResult") val inspectionResult: MobileCraneBapInspectionResult,
    @SerializedName("signature") val signature: MobileCraneBapSignature,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class MobileCraneBapGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("ownerAddress") val ownerAddress: String,
    @SerializedName("userAddress") val userAddress: String
)

data class MobileCraneBapTechnicalData(
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String,
    @SerializedName("maxLiftingHeight") val maxLiftingHeight: String,
    @SerializedName("materialCertificateNumber") val materialCertificateNumber: String,
    @SerializedName("liftingSpeedMpm") val liftingSpeedMpm: String
)

data class MobileCraneBapInspectionResult(
    @SerializedName("visualCheck") val visualCheck: MobileCraneBapVisualCheck,
    @SerializedName("functionalTest") val functionalTest: MobileCraneBapFunctionalTest
)

data class MobileCraneBapVisualCheck(
    @SerializedName("hasBoomDefects") val hasBoomDefects: Boolean,
    @SerializedName("isNameplateAttached") val isNameplateAttached: Boolean,
    @SerializedName("areBoltsAndNutsSecure") val areBoltsAndNutsSecure: Boolean,
    @SerializedName("isSlingGoodCondition") val isSlingGoodCondition: Boolean,
    @SerializedName("isHookGoodCondition") val isHookGoodCondition: Boolean,
    @SerializedName("isSafetyLatchInstalled") val isSafetyLatchInstalled: Boolean,
    @SerializedName("isTireGoodCondition") val isTireGoodCondition: Boolean,
    @SerializedName("isWorkLampFunctional") val isWorkLampFunctional: Boolean
)

data class MobileCraneBapFunctionalTest(
    @SerializedName("isForwardReverseFunctionOk") val isForwardReverseFunctionOk: Boolean,
    @SerializedName("isSwingFunctionOk") val isSwingFunctionOk: Boolean,
    @SerializedName("isHoistingFunctionOk") val isHoistingFunctionOk: Boolean,
    @SerializedName("loadTest") val loadTest: MobileCraneBapLoadTest,
    @SerializedName("ndtTest") val ndtTest: MobileCraneBapNdtTest
)

data class MobileCraneBapLoadTest(
    @SerializedName("loadKg") val loadKg: String,
    @SerializedName("liftHeightMeters") val liftHeightMeters: String,
    @SerializedName("holdTimeSeconds") val holdTimeSeconds: String,
    @SerializedName("isResultGood") val isResultGood: Boolean
)

data class MobileCraneBapNdtTest(
    @SerializedName("method") val method: String,
    @SerializedName("isResultGood") val isResultGood: Boolean
)

data class MobileCraneBapSignature(
    @SerializedName("companyName") val companyName: String
)
