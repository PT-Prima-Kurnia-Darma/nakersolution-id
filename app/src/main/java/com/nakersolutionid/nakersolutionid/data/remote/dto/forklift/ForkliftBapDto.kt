package com.nakersolutionid.nakersolutionid.data.remote.dto.forklift

import com.google.gson.annotations.SerializedName

data class ForkliftBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("generalData") val generalData: ForkliftBapGeneralData,
    @SerializedName("technicalData") val technicalData: ForkliftBapTechnicalData,
    @SerializedName("inspectionResult") val inspectionResult: ForkliftBapInspectionResult,
)

// Data DTO for single Forklift BAP response
data class ForkliftBapSingleReportResponseData(
    @SerializedName("bap") val bap: ForkliftBapReportData
)

// Data DTO for list of Forklift BAP reports response
data class ForkliftBapListReportResponseData(
    @SerializedName("bap") val bap: List<ForkliftBapReportData>
)

// Main DTO for Forklift BAP (used for create, update, and individual get)
data class ForkliftBapReportData(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("generalData") val generalData: ForkliftBapGeneralData,
    @SerializedName("technicalData") val technicalData: ForkliftBapTechnicalData,
    @SerializedName("inspectionResult") val inspectionResult: ForkliftBapInspectionResult,
    @SerializedName("id") val id: String // From response JSON
)

data class ForkliftBapGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("ownerAddress") val ownerAddress: String,
    @SerializedName("userInCharge") val userInCharge: String
)

data class ForkliftBapTechnicalData(
    @SerializedName("brandType") val brandType: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String,
    @SerializedName("liftingHeightMeters") val liftingHeightMeters: String
)

data class ForkliftBapInspectionResult(
    @SerializedName("visualCheck") val visualCheck: ForkliftBapVisualCheck,
    @SerializedName("functionalTest") val functionalTest: ForkliftBapFunctionalTest
)

data class ForkliftBapVisualCheck(
    @SerializedName("hasForkDefects") val hasForkDefects: Boolean,
    @SerializedName("isNameplateAttached") val isNameplateAttached: Boolean,
    @SerializedName("isAparAvailable") val isAparAvailable: Boolean,
    @SerializedName("isCapacityMarkingDisplayed") val isCapacityMarkingDisplayed: Boolean,
    @SerializedName("hasHydraulicLeak") val hasHydraulicLeak: Boolean,
    @SerializedName("isChainGoodCondition") val isChainGoodCondition: Boolean
)

data class ForkliftBapFunctionalTest(
    @SerializedName("loadKg") val loadKg: String,
    @SerializedName("liftHeightMeters") val liftHeightMeters: String,
    @SerializedName("isAbleToLiftAndHold") val isAbleToLiftAndHold: Boolean,
    @SerializedName("isFunctioningWell") val isFunctioningWell: Boolean,
    @SerializedName("hasCrackIndication") val hasCrackIndication: Boolean,
    @SerializedName("isEmergencyStopFunctional") val isEmergencyStopFunctional: Boolean,
    @SerializedName("isWarningLampHornFunctional") val isWarningLampHornFunctional: Boolean
)
