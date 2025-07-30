package com.nakersolutionid.nakersolutionid.data.remote.dto.lightning

import com.google.gson.annotations.SerializedName

data class LightningBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("inspectionType") val inspectionType: String,
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
    @SerializedName("extraId") val extraId: Long,
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
    @SerializedName("buildingHeight") val buildingHeight: String,
    @SerializedName("buildingArea") val buildingArea: String,
    @SerializedName("receiverHeight") val receiverHeight: String,
    @SerializedName("receiverCount") val receiverCount: String,
    @SerializedName("groundElectrodeCount") val groundElectrodeCount: String,
    @SerializedName("conductorDescription") val conductorDescription: String,
    @SerializedName("installer") val installer: String,
    @SerializedName("groundingResistance") val groundingResistance: String
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
    @SerializedName("measuredGroundingResistance") val measuredGroundingResistance: String,
    @SerializedName("measuredGroundingResistanceResult") val measuredGroundingResistanceResult: Boolean
)