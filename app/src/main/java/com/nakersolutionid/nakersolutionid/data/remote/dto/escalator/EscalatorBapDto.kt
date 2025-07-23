package com.nakersolutionid.nakersolutionid.data.remote.dto.escalator

import com.google.gson.annotations.SerializedName

data class EscalatorBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: EscalatorBapGeneralData,
    @SerializedName("technicalData") val technicalData: EscalatorBapTechnicalData,
    @SerializedName("visualInspection") val visualInspection: EscalatorBapVisualInspection,
    @SerializedName("testing") val testing: EscalatorBapTesting,
)

// Data DTO for single Escalator BAP response
data class EscalatorBapSingleReportResponseData(
    @SerializedName("bap") val bap: EscalatorBapReportData
)

// Data DTO for list of Escalator BAP reports response
data class EscalatorBapListReportResponseData(
    @SerializedName("bap") val bap: List<EscalatorBapReportData>
)

// Main DTO for Escalator BAP (used for create, update, and individual get)
data class EscalatorBapReportData(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: EscalatorBapGeneralData,
    @SerializedName("technicalData") val technicalData: EscalatorBapTechnicalData,
    @SerializedName("visualInspection") val visualInspection: EscalatorBapVisualInspection,
    @SerializedName("testing") val testing: EscalatorBapTesting,
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class EscalatorBapGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("nameUsageLocation") val nameUsageLocation: String,
    @SerializedName("locationUsageLocation") val locationUsageLocation: String
)

data class EscalatorBapTechnicalData(
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("technicalDatamanufacturer") val manufacturer: String,
    @SerializedName("technicalDatabrand") val brand: String,
    @SerializedName("technicalDatacountryAndYear") val countryAndYear: String,
    @SerializedName("technicalDataserialNumber") val serialNumber: String,
    @SerializedName("technicalDatacapacity") val capacity: String,
    @SerializedName("technicalDataspeed") val speed: String,
    @SerializedName("technicalDatatransports") val transports: String
)

data class EscalatorBapVisualInspection(
    @SerializedName("isMachineRoomConditionAcceptable") val isMachineRoomConditionAcceptable: Boolean,
    @SerializedName("isPanelConditionAcceptable") val isPanelConditionAcceptable: Boolean,
    @SerializedName("islightingConditionisPitLightAcceptable") val isLightingConditionIsPitLightAcceptable: Boolean,
    @SerializedName("areSafetySignsAvailable") val areSafetySignsAvailable: Boolean
)

data class EscalatorBapTesting(
    @SerializedName("testingisNdtThermographPanel") val isNdtThermographPanel: Boolean,
    @SerializedName("testingareSafetyDevicesFunctional") val areSafetyDevicesFunctional: Boolean,
    @SerializedName("testingisLimitSwitchFunctional") val isLimitSwitchFunctional: Boolean,
    @SerializedName("testingisDoorSwitchFunctiona") val isDoorSwitchFunctional: Boolean,
    @SerializedName("testingpitEmergencyStopStatusisAvailable") val pitEmergencyStopStatusIsAvailable: Boolean,
    @SerializedName("testingpitEmergencyStopStatusisFunctional") val pitEmergencyStopStatusIsFunctional: Boolean,
    @SerializedName("isEscalatorFunctionOk") val isEscalatorFunctionOk: Boolean
)
