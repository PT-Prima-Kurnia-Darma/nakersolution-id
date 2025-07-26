package com.nakersolutionid.nakersolutionid.data.remote.dto.lightning

import com.google.gson.annotations.SerializedName

data class LightningBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("extraId") val extraId: Long, // Dari JSON: 1 (angka murni)
    @SerializedName("generalData") val generalData: LightningBapGeneralData,
    @SerializedName("technicalData") val technicalData: LightningBapTechnicalData,
    @SerializedName("testResults") val testResults: LightningBapTestResults
)

// Data DTO for single Lightning BAP response
data class LightningBapSingleReportResponseData(
    @SerializedName("bap") val bap: LightningBapReportData
)

// Data DTO for list of Lightning BAP reports response
data class LightningBapListReportResponseData(
    @SerializedName("bap") val bap: List<LightningBapReportData>
)

// Main DTO for Lightning BAP (used for create, update, and individual get)
data class LightningBapReportData(
    @SerializedName("id") val id: String, // Dari JSON respons
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("extraId") val extraId: Long, // Dari JSON: 1 (angka murni)
    @SerializedName("generalData") val generalData: LightningBapGeneralData,
    @SerializedName("technicalData") val technicalData: LightningBapTechnicalData,
    @SerializedName("testResults") val testResults: LightningBapTestResults,
    @SerializedName("subInspectionType") val subInspectionType: String, // Dari JSON respons
    @SerializedName("documentType") val documentType: String, // Dari JSON respons
    @SerializedName("createdAt") val createdAt: String // Dari JSON respons
)

data class LightningBapGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("usageLocation") val usageLocation: String,
    @SerializedName("addressUsageLocation") val addressUsageLocation: String
)

data class LightningBapTechnicalData(
    @SerializedName("conductorType") val conductorType: String,
    @SerializedName("serialNumber") val serialNumber: String,
    @SerializedName("buildingHeight") val buildingHeight: Int,
    @SerializedName("buildingArea") val buildingArea: Int,
    @SerializedName("receiverHeight") val receiverHeight: Int,
    @SerializedName("receiverCount") val receiverCount: Int,
    @SerializedName("groundElectrodeCount") val groundElectrodeCount: Int,
    @SerializedName("conductorDescription") val conductorDescription: String,
    @SerializedName("installer") val installer: String,
    @SerializedName("groundingResistance") val groundingResistance: Double // Ini adalah Double di JSON (tidak pakai kutip)
)

data class LightningBapTestResults(
    @SerializedName("visualInspection") val visualInspection: LightningBapVisualInspection,
    @SerializedName("measurement") val measurement: LightningBapMeasurement
)

data class LightningBapVisualInspection(
    @SerializedName("isSystemOverallGood") val isSystemOverallGood: Boolean,
    @SerializedName("isReceiverConditionGood") val isReceiverConditionGood: Boolean,
    @SerializedName("isReceiverPoleConditionGood") val isReceiverPoleConditionGood: Boolean,
    @SerializedName("isConductorInsulated") val isConductorInsulated: Boolean,
    @SerializedName("isControlBoxAvailable") val isControlBoxAvailable: Boolean,
    @SerializedName("isControlBoxConditionGood") val isControlBoxConditionGood: Boolean
)

data class LightningBapMeasurement(
    @SerializedName("conductorContinuityResult") val conductorContinuityResult: String,
    @SerializedName("measuredGroundingResistance") val measuredGroundingResistance: String, // Ini adalah String di JSON ("0,30")
    @SerializedName("measuredGroundingResistanceResult") val measuredGroundingResistanceResult: Boolean
)