package com.nakersolutionid.nakersolutionid.data.remote.dto.lightning

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus

data class LightningReportRequest(
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("pjk3Data") val pjk3Data: LightningPjk3Data,
    @SerializedName("ownerData") val ownerData: LightningOwnerData,
    @SerializedName("technicalData") val technicalData: LightningTechnicalData,
    @SerializedName("physicalInspection") val physicalInspection: LightningPhysicalInspection,
    @SerializedName("visualInspection") val visualInspection: LightningVisualInspection,
    @SerializedName("standardCompliance") val standardCompliance: LightningStandardCompliance,
    @SerializedName("dynamicTestItems") val dynamicTestItems: List<LightningDynamicTestItem>,
    @SerializedName("dynamicItems") val dynamicItems: List<LightningDynamicItem>,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recomendations") val recomendations: String
)

// Data DTO for single Lightning Report response
data class LightningSingleReportResponseData(
    @SerializedName("laporan") val laporan: LightningReportData
)

// Data DTO for list of Lightning Reports response
data class LightningListReportResponseData(
    @SerializedName("laporan") val laporan: List<LightningReportData>
)

// Main DTO for Lightning Report (used for create, update, and individual get)
data class LightningReportData(
    @SerializedName("id") val id: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("pjk3Data") val pjk3Data: LightningPjk3Data,
    @SerializedName("ownerData") val ownerData: LightningOwnerData,
    @SerializedName("technicalData") val technicalData: LightningTechnicalData,
    @SerializedName("physicalInspection") val physicalInspection: LightningPhysicalInspection,
    @SerializedName("visualInspection") val visualInspection: LightningVisualInspection,
    @SerializedName("standardCompliance") val standardCompliance: LightningStandardCompliance,
    @SerializedName("dynamicTestItems") val dynamicTestItems: List<LightningDynamicTestItem>,
    @SerializedName("dynamicItems") val dynamicItems: List<LightningDynamicItem>,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recomendations") val recomendations: String,
    @SerializedName("subInspectionType") val subInspectionType: String,
    @SerializedName("documentType") val documentType: String
)

data class LightningPjk3Data(
    @SerializedName("companyNameandaddres") val companyNameAndAddress: String,
    @SerializedName("companyPermitNo") val companyPermitNo: String,
    @SerializedName("expertPermitNo") val expertPermitNo: String,
    @SerializedName("expertName") val expertName: String,
    @SerializedName("riksaujiTools") val riksaujiTools: String
)

data class LightningOwnerData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("usageLocation") val usageLocation: String,
    @SerializedName("objectType") val objectType: String,
    @SerializedName("typeInspection") val typeInspection: String,
    @SerializedName("certificateNo") val certificateNo: String,
    @SerializedName("inspectionDate") val inspectionDate: String
)

data class LightningTechnicalData(
    @SerializedName("conductorType") val conductorType: String,
    @SerializedName("buildingHeight") val buildingHeight: String,
    @SerializedName("buildingArea") val buildingArea: String,
    @SerializedName("receiverHeight") val receiverHeight: String,
    @SerializedName("receiverCount") val receiverCount: Int,
    @SerializedName("testJointCount") val testJointCount: Int,
    @SerializedName("conductorDescription") val conductorDescription: String,
    @SerializedName("groundingResistance") val groundingResistance: String,
    @SerializedName("spreadingResistance") val spreadingResistance: String,
    @SerializedName("installer") val installer: String
)

data class LightningPhysicalInspection(
    @SerializedName("installationSystem") val installationSystem: LightningResultStatusDetail,
    @SerializedName("receiverHead") val receiverHead: LightningResultStatusDetail,
    @SerializedName("receiverPole") val receiverPole: LightningResultStatusDetail,
    @SerializedName("poleReinforcement") val poleReinforcement: LightningResultStatusDetail,
    @SerializedName("downConductor") val downConductor: LightningResultStatusDetail,
    @SerializedName("conductorClamps") val conductorClamps: LightningResultStatusDetail,
    @SerializedName("jointConnections") val jointConnections: LightningResultStatusDetail,
    @SerializedName("groundingTerminalBox") val groundingTerminalBox: LightningResultStatusDetail,
    @SerializedName("controlBox") val controlBox: LightningResultStatusDetail,
    @SerializedName("groundingSystem") val groundingSystem: LightningResultStatusDetail,
    @SerializedName("conductorToGroundConnection") val conductorToGroundConnection: LightningResultStatusDetail,
    @SerializedName("notes") val notes: String
)

data class LightningResultStatusDetail(
    @SerializedName("good") val good: Boolean,
    @SerializedName("fair") val fair: Boolean,
    @SerializedName("poor") val poor: Boolean
)

data class LightningVisualInspection(
    @SerializedName("airTerminal") val airTerminal: String,
    @SerializedName("downConductorCheck") val downConductorCheck: String,
    @SerializedName("groundingAndTestJoint") val groundingAndTestJoint: String
)

data class LightningStandardCompliance(
    @SerializedName("asBuiltDrawing") val asBuiltDrawing: LightningStandardComplianceDetail,
    @SerializedName("terminalToConductorConnection") val terminalToConductorConnection: LightningStandardComplianceDetail,
    @SerializedName("downConductorJoints") val downConductorJoints: LightningStandardComplianceDetail,
    @SerializedName("testJointBoxInstallation") val testJointBoxInstallation: LightningStandardComplianceDetail,
    @SerializedName("conductorMaterialStandard") val conductorMaterialStandard: LightningStandardComplianceDetail,
    @SerializedName("lightningCounter") val lightningCounter: LightningStandardComplianceDetail,
    @SerializedName("radioactiveTerminal") val radioactiveTerminal: LightningStandardComplianceDetail,
    @SerializedName("groundingRodMaterial") val groundingRodMaterial: LightningStandardComplianceDetail,
    @SerializedName("arresterInstallation") val arresterInstallation: LightningStandardComplianceDetail
)

data class LightningStandardComplianceDetail(
    @SerializedName("good") val good: Boolean,
    @SerializedName("poor") val poor: Boolean,
    @SerializedName("notes") val notes: String
)

data class LightningDynamicTestItem(
    @SerializedName("ecResult") val ecResult: String,
    @SerializedName("epResult") val epResult: String,
    @SerializedName("rValue") val rValue: Double,
    @SerializedName("result") val result: String
)

data class LightningDynamicItem(
    @SerializedName("materialConditionsItems") val materialConditionsItems: String,
    @SerializedName("outcomeBaik") val outcomeBaik: Boolean,
    @SerializedName("outcomeBuruk") val outcomeBuruk: Boolean,
    @SerializedName("rGradeItems") val rGradeItems: String,
    @SerializedName("result") val result: String
)