package com.nakersolutionid.nakersolutionid.data.remote.dto.pubt

import com.google.gson.annotations.SerializedName

data class PubtBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("generalData") val generalData: PubtBapGeneralData,
    @SerializedName("technicalData") val technicalData: PubtBapTechnicalData,
    @SerializedName("visualInspection") val visualInspection: PubtBapVisualInspection,
    @SerializedName("testing") val testing: PubtBapTesting
)

// Data DTO for single PUBT BAP response
data class PubtBapSingleReportResponseData(
    @SerializedName("bap") val bap: PubtBapReportData
)

// Data DTO for list of PUBT BAP reports response
data class PubtBapListReportResponseData(
    @SerializedName("bap") val bap: List<PubtBapReportData>
)

// Main DTO for PUBT BAP (used for create, update, and individual get)
data class PubtBapReportData(
    @SerializedName("id") val id: String, // Dari JSON respons
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("generalData") val generalData: PubtBapGeneralData,
    @SerializedName("technicalData") val technicalData: PubtBapTechnicalData,
    @SerializedName("visualInspection") val visualInspection: PubtBapVisualInspection,
    @SerializedName("testing") val testing: PubtBapTesting,
    @SerializedName("subInspectionType") val subInspectionType: String, // Dari JSON respons
    @SerializedName("documentType") val documentType: String // Dari JSON respons
)

data class PubtBapGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("userUsage") val userUsage: String,
    @SerializedName("userAddress") val userAddress: String
)

// Perubahan: Mengubah tipe data Int menjadi String agar sesuai dengan Joi schema
data class PubtBapTechnicalData(
    @SerializedName("brandType") val brandType: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("countryAndYearOfManufacture") val countryAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("fuelType") val fuelType: String,
    @SerializedName("operatingPressure") val operatingPressure: String,
    @SerializedName("designPressureKgCm2") val designPressureKgCm2: String,
    @SerializedName("maxAllowableWorkingPressure") val maxAllowableWorkingPressure: String,
    @SerializedName("technicalDataShellMaterial") val technicalDataShellMaterial: String,
    @SerializedName("safetyValveType") val safetyValveType: String,
    @SerializedName("volumeLiters") val volumeLiters: String
)

data class PubtBapVisualInspection(
    @SerializedName("foundationCondition") val foundationCondition: Boolean,
    @SerializedName("safetyValveIsInstalled") val safetyValveIsInstalled: Boolean,
    @SerializedName("safetyValveCondition") val safetyValveCondition: Boolean,
    @SerializedName("aparAvailable") val aparAvailable: Boolean,
    @SerializedName("aparCondition") val aparCondition: Boolean,
    @SerializedName("wheelCondition") val wheelCondition: Boolean,
    @SerializedName("pipeCondition") val pipeCondition: Boolean
)

data class PubtBapTesting(
    @SerializedName("ndtTestingFulfilled") val ndtTestingFulfilled: Boolean,
    @SerializedName("thicknessTestingComply") val thicknessTestingComply: Boolean,
    @SerializedName("pneumaticTestingCondition") val pneumaticTestingCondition: Boolean,
    @SerializedName("hydroTestingFullFilled") val hydroTestingFullFilled: Boolean,
    @SerializedName("safetyValveTestingCondition") val safetyValveTestingCondition: Boolean
)