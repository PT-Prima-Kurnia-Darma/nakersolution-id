package com.nakersolutionid.nakersolutionid.data.remote.dto.elevator

import com.google.gson.annotations.SerializedName

// Data DTO for single BAP response
data class BapSingleReportResponseData(
    @SerializedName("bap") val bap: BapReportData
)

// Data DTO for list of BAP reports response
data class BapListReportResponseData(
    @SerializedName("bap") val bap: List<BapReportData>
)

data class BapReportData(
    @SerializedName("laporanId") val laporanId: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("extraId") val extraId: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("generalData") val generalData: BapGeneralData,
    @SerializedName("technicalData") val technicalData: BapTechnicalData,
    @SerializedName("visualInspection") val visualInspection: BapVisualInspection,
    @SerializedName("testing") val testing: BapTesting,
    @SerializedName("id") val id: String,
    @SerializedName("subInspectionType") val subInspectionType: String,
    @SerializedName("documentType") val documentType: String
)

data class BapGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("ownerAddress") val ownerAddress: String,
    @SerializedName("nameUsageLocation") val nameUsageLocation: String,
    @SerializedName("addressUsageLocation") val addressUsageLocation: String
)

data class BapTechnicalData(
    @SerializedName("elevatorType") val elevatorType: String,
    @SerializedName("manufacturerOrInstaller") val manufacturerOrInstaller: String,
    @SerializedName("brandOrType") val brandOrType: String,
    @SerializedName("countryAndYear") val countryAndYear: String,
    @SerializedName("serialNumber") val serialNumber: String,
    @SerializedName("capacity") val capacity: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("floorsServed") val floorsServed: String
)

data class BapVisualInspection(
    @SerializedName("isMachineRoomConditionAcceptable") val isMachineRoomConditionAcceptable: Boolean,
    @SerializedName("isPanelGoodCondition") val isPanelGoodCondition: Boolean,
    @SerializedName("isAparAvailableInPanelRoom") val isAparAvailableInPanelRoom: Boolean,
    @SerializedName("lightingCondition") val lightingCondition: Boolean,
    @SerializedName("isPitLadderAvailable") val isPitLadderAvailable: Boolean
)

data class BapTesting(
    @SerializedName("isNdtThermographPanelOk") val isNdtThermographPanelOk: Boolean,
    @SerializedName("isArdFunctional") val isArdFunctional: Boolean,
    @SerializedName("isGovernorFunctional") val isGovernorFunctional: Boolean,
    @SerializedName("isSlingConditionOkByTester") val isSlingConditionOkByTester: Boolean,
    @SerializedName("limitSwitchTest") val limitSwitchTest: Boolean,
    @SerializedName("isDoorSwitchFunctional") val isDoorSwitchFunctional: Boolean,
    @SerializedName("pitEmergencyStopStatus") val pitEmergencyStopStatus: Boolean,
    @SerializedName("isIntercomFunctional") val isIntercomFunctional: Boolean,
    @SerializedName("isFiremanSwitchFunctional") val isFiremanSwitchFunctional: Boolean
)
