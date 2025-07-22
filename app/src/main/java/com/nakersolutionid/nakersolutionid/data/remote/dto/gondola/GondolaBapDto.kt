package com.nakersolutionid.nakersolutionid.data.remote.dto.gondola

import com.google.gson.annotations.SerializedName

data class GondolaBapRequest(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("generalData") val generalData: GondolaBapGeneralData,
    @SerializedName("technicalData") val technicalData: GondolaBapTechnicalData,
    @SerializedName("inspectionResult") val inspectionResult: GondolaBapInspectionResult,
)

// Data DTO for single Gondola BAP response
data class GondolaBapSingleReportResponseData(
    @SerializedName("bap") val bap: GondolaBapReportData
)

// Data DTO for list of Gondola BAP reports response
data class GondolaBapListReportResponseData(
    @SerializedName("bap") val bap: List<GondolaBapReportData>
)

// Main DTO for Gondola BAP (used for create, update, and individual get)
data class GondolaBapReportData(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("generalData") val generalData: GondolaBapGeneralData,
    @SerializedName("technicalData") val technicalData: GondolaBapTechnicalData,
    @SerializedName("inspectionResult") val inspectionResult: GondolaBapInspectionResult,
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class GondolaBapGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("userInCharge") val userInCharge: String,
    @SerializedName("ownerAddress") val ownerAddress: String
)

data class GondolaBapTechnicalData(
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("intendedUse") val intendedUse: String,
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String,
    @SerializedName("maxLiftingHeightMeters") val maxLiftingHeightMeters: String
)

data class GondolaBapInspectionResult(
    @SerializedName("visualCheck") val visualCheck: GondolaBapVisualCheck,
    @SerializedName("functionalTest") val functionalTest: GondolaBapFunctionalTest
)

data class GondolaBapVisualCheck(
    @SerializedName("isSlingDiameterAcceptable") val isSlingDiameterAcceptable: Boolean,
    @SerializedName("isBumperInstalled") val isBumperInstalled: Boolean,
    @SerializedName("isCapacityMarkingDisplayed") val isCapacityMarkingDisplayed: Boolean,
    @SerializedName("isPlatformConditionAcceptable") val isPlatformConditionAcceptable: Boolean,
    @SerializedName("driveMotorCondition") val driveMotorCondition: GondolaDriveMotorCondition,
    @SerializedName("isControlPanelClean") val isControlPanelClean: Boolean,
    @SerializedName("isBodyHarnessAvailable") val isBodyHarnessAvailable: Boolean,
    @SerializedName("isLifelineAvailable") val isLifelineAvailable: Boolean,
    @SerializedName("isButtonLabelsDisplayed") val isButtonLabelsDisplayed: Boolean
)

data class GondolaDriveMotorCondition(
    @SerializedName("isGoodCondition") val isGoodCondition: Boolean,
    @SerializedName("hasOilLeak") val hasOilLeak: Boolean
)

data class GondolaBapFunctionalTest(
    @SerializedName("isWireRopeMeasurementOk") val isWireRopeMeasurementOk: Boolean,
    @SerializedName("isUpDownFunctionOk") val isUpDownFunctionOk: Boolean,
    @SerializedName("isDriveMotorFunctionOk") val isDriveMotorFunctionOk: Boolean,
    @SerializedName("isEmergencyStopFunctional") val isEmergencyStopFunctional: Boolean,
    @SerializedName("isSafetyLifelineFunctional") val isSafetyLifelineFunctional: Boolean,
    @SerializedName("ndtTest") val ndtTest: GondolaNdtTest
)

data class GondolaNdtTest(
    @SerializedName("method") val method: String,
    @SerializedName("isResultGood") val isResultGood: Boolean,
    @SerializedName("hasCrackIndication") val hasCrackIndication: Boolean
)
