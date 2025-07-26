package com.nakersolutionid.nakersolutionid.data.remote.dto.ipk

import com.google.gson.annotations.SerializedName

data class IpkBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraid") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("generalData") val generalData: IpkBapGeneralData,
    @SerializedName("technicalData") val technicalData: IpkBapTechnicalData,
    @SerializedName("testResults") val testResults: IpkBapTestResults
)

// Data DTO for single IPK BAP response
data class IpkBapSingleReportResponseData(
    @SerializedName("bap") val bap: IpkBapReportData
)

// Data DTO for list of IPK BAP reports response
data class IpkBapListReportResponseData(
    @SerializedName("bap") val bap: List<IpkBapReportData>
)

// Main DTO for IPK BAP (used for create, update, and individual get)
data class IpkBapReportData(
    @SerializedName("id") val id: String,
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraid") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("generalData") val generalData: IpkBapGeneralData,
    @SerializedName("technicalData") val technicalData: IpkBapTechnicalData,
    @SerializedName("testResults") val testResults: IpkBapTestResults,
    @SerializedName("subInspectionType") val subInspectionType: String,
    @SerializedName("documentType") val documentType: String
)

data class IpkBapGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("usageLocation") val usageLocation: String,
    @SerializedName("addressUsageLocation") val addressUsageLocation: String
)

data class IpkBapTechnicalData(
    @SerializedName("landArea") val landArea: String,
    @SerializedName("buildingArea") val buildingArea: String,
    @SerializedName("buildingHeight") val buildingHeight: String,
    @SerializedName("floorCount") val floorCount: String,
    @SerializedName("pillarAndOutdoorHydrant") val pillarAndOutdoorHydrant: String,
    @SerializedName("totalHydrantBuilding") val totalHydrantBuilding: String,
    @SerializedName("totalHoseRell") val totalHoseRell: String,
    @SerializedName("totalSpinkler") val totalSpinkler: String,
    @SerializedName("totalHeatDetector") val totalHeatDetector: String,
    @SerializedName("totalSmokeDetector") val totalSmokeDetector: String,
    @SerializedName("totalFlameDetector") val totalFlameDetector: String,
    @SerializedName("totalGasDetector") val totalGasDetector: String,
    @SerializedName("manualButton") val manualButton: String,
    @SerializedName("alarmBell") val alarmBell: String,
    @SerializedName("signalAlarmLamp") val signalAlarmLamp: String,
    @SerializedName("emergencyExit") val emergencyExit: String,
    @SerializedName("apar") val apar: String,
    @SerializedName("certificateNumber") val certificateNumber: String
)

data class IpkBapTestResults(
    @SerializedName("visualInspection") val visualInspection: IpkBapVisualInspection,
    @SerializedName("functionalTests") val functionalTests: IpkBapFunctionalTests
)

data class IpkBapVisualInspection(
    @SerializedName("isAparAvailable") val isAparAvailable: Boolean,
    @SerializedName("isAparInGoodCondition") val isAparInGoodCondition: Boolean,
    @SerializedName("isHydrantPanelInGoodCondition") val isHydrantPanelInGoodCondition: Boolean,
    @SerializedName("isPumpsAvailable") val isPumpsAvailable: Boolean,
    @SerializedName("isPumpsInGoodCondition") val isPumpsInGoodCondition: Boolean,
    @SerializedName("isSprinklerSystemAvailable") val isSprinklerSystemAvailable: Boolean,
    @SerializedName("isSprinklerSystemInGoodCondition") val isSprinklerSystemInGoodCondition: Boolean,
    @SerializedName("isDetectorSystemAvailable") val isDetectorSystemAvailable: Boolean,
    @SerializedName("isDetectorSystemInGoodCondition") val isDetectorSystemInGoodCondition: Boolean
)

data class IpkBapFunctionalTests(
    @SerializedName("isAparFunctional") val isAparFunctional: Boolean,
    @SerializedName("arePumpsFunctional") val arePumpsFunctional: Boolean,
    @SerializedName("isSprinklerFunctional") val isSprinklerFunctional: Boolean,
    @SerializedName("isDetectorFunctional") val isDetectorFunctional: Boolean,
    @SerializedName("isDetectorConnectedToMcfa") val isDetectorConnectedToMcfa: Boolean
)