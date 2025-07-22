package com.nakersolutionid.nakersolutionid.data.remote.dto.overheadcrane

import com.google.gson.annotations.SerializedName
// Assuming CommonDtos.kt is in the same package or imported
// import com.your_package_name.CommonDtos.ResultStatus

// Data DTO for single Overhead Crane BAP response
data class OverheadCraneBapSingleReportResponseData(
    @SerializedName("bap") val bap: OverheadCraneBapReportData
)

// Data DTO for list of Overhead Crane BAP reports response
data class OverheadCraneBapListReportResponseData(
    @SerializedName("bap") val bap: List<OverheadCraneBapReportData>
)

// Main DTO for Overhead Crane BAP (used for create, update, and individual get)
data class OverheadCraneBapReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("reportHeader") val reportHeader: OverheadCraneBapReportHeader,
    @SerializedName("generalData") val generalData: OverheadCraneBapGeneralData,
    @SerializedName("technicalData") val technicalData: OverheadCraneBapTechnicalData,
    @SerializedName("visualInspection") val visualInspection: OverheadCraneBapVisualInspection,
    @SerializedName("testing") val testing: OverheadCraneBapTesting,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String, // From response JSON
    @SerializedName("createdAt") val createdAt: String // From response JSON
)

data class OverheadCraneBapReportHeader(
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("equipmentType") val equipmentType: String
)

data class OverheadCraneBapGeneralData(
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("userInCharge") val userInCharge: String,
    @SerializedName("ownerAddress") val ownerAddress: String,
    @SerializedName("unitLocation") val unitLocation: String,
    @SerializedName("ownerName") val ownerName: String // From update_bap_body.json
)

data class OverheadCraneBapTechnicalData(
    @SerializedName("brandType") val brandType: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String,
    @SerializedName("liftingSpeedMpm") val liftingSpeedMpm: String
)

data class OverheadCraneBapVisualInspection(
    @SerializedName("hasConstructionDefects") val hasConstructionDefects: Boolean,
    @SerializedName("hookHasSafetyLatch") val hookHasSafetyLatch: Boolean,
    @SerializedName("isEmergencyStopInstalled") val isEmergencyStopInstalled: Boolean,
    @SerializedName("isWireropeGoodCondition") val isWireropeGoodCondition: Boolean,
    @SerializedName("operatorHasK3License") val operatorHasK3License: Boolean
)

data class OverheadCraneBapTesting(
    @SerializedName("functionTest") val functionTest: Boolean,
    @SerializedName("loadTest") val loadTest: OverheadCraneBapLoadTest,
    @SerializedName("ndtTest") val ndtTest: OverheadCraneBapNdtTest
)

data class OverheadCraneBapLoadTest(
    @SerializedName("loadTon") val loadTon: Int,
    @SerializedName("isAbleToLift") val isAbleToLift: Boolean,
    @SerializedName("hasLoadDrop") val hasLoadDrop: Boolean
)

data class OverheadCraneBapNdtTest(
    @SerializedName("isNdtResultGood") val isNdtResultGood: Boolean,
    @SerializedName("hasCrackIndication") val hasCrackIndication: Boolean
)
