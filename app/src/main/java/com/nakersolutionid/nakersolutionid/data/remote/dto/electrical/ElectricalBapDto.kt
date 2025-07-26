package com.nakersolutionid.nakersolutionid.data.remote.dto.electrical

import com.google.gson.annotations.SerializedName

data class ElectricalBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("generalData") val generalData: ElectricalBapGeneralData,
    @SerializedName("technicalData") val technicalData: ElectricalBapTechnicalData,
    @SerializedName("visualInspection") val visualInspection: ElectricalBapVisualInspection,
    @SerializedName("testing") val testing: ElectricalBapTesting,
)

// Data DTO for single Electrical BAP response
data class ElectricalBapSingleReportResponseData(
    @SerializedName("bap") val bap: ElectricalBapReportData
)

// Data DTO for list of Electrical BAP reports response
data class ElectricalBapListReportResponseData(
    @SerializedName("bap") val bap: List<ElectricalBapReportData>
)

// Main DTO for Electrical BAP (used for create, update, and individual get)
data class ElectricalBapReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("generalData") val generalData: ElectricalBapGeneralData,
    @SerializedName("technicalData") val technicalData: ElectricalBapTechnicalData,
    @SerializedName("visualInspection") val visualInspection: ElectricalBapVisualInspection,
    @SerializedName("testing") val testing: ElectricalBapTesting,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class ElectricalBapGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("usageLocation") val usageLocation: String,
    @SerializedName("addressUsageLocation") val addressUsageLocation: String
)

data class ElectricalBapTechnicalData(
    @SerializedName("technicalDataPlnPower") val plnPower: String,
    @SerializedName("technicalDataGeneratorPower") val generatorPower: String,
    @SerializedName("technicalDataLightingPower") val lightingPower: String,
    @SerializedName("technicalDataPowerLoad") val powerLoad: String,
    @SerializedName("serialNumber") val serialNumber: String
)

data class ElectricalBapVisualInspection(
    @SerializedName("isRoomClean") val isRoomClean: Boolean,
    @SerializedName("isRoomClearItems") val isRoomClearItems: Boolean,
    @SerializedName("hasSingleLineDiagram") val hasSingleLineDiagram: Boolean,
    @SerializedName("hasProtectiveCover") val hasProtectiveCover: Boolean,
    @SerializedName("isLabelingComplete") val isLabelingComplete: Boolean,
    @SerializedName("isFireExtinguisherAvailable") val isFireExtinguisherAvailable: Boolean
)

data class ElectricalBapTesting(
    @SerializedName("isThermographTestOk") val isThermographTestOk: Boolean,
    @SerializedName("areSafetyDevicesFunctional") val areSafetyDevicesFunctional: Boolean,
    @SerializedName("isVoltageBetweenPhasesNormal") val isVoltageBetweenPhasesNormal: Boolean,
    @SerializedName("isPhaseLoadBalanced") val isPhaseLoadBalanced: Boolean
)
