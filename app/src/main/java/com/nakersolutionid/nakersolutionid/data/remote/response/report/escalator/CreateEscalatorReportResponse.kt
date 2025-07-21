package com.nakersolutionid.nakersolutionid.data.remote.response.report.escalator

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.body.report.EscalatorReportGeneralDataDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.EscalatorReportInspectionAndTestingDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.EscalatorReportTechnicalDataDto

data class CreateEscalatorReportResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: EscalatorReportResponseDataDto = EscalatorReportResponseDataDto()
)

data class EscalatorReportResponseDataDto(
    @SerializedName("laporan") val laporan: EscalatorReportLaporanDto = EscalatorReportLaporanDto()
)

data class EscalatorReportLaporanDto(
    @SerializedName("id") val id: String = "",
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("generalData") val generalData: EscalatorReportGeneralDataDto = EscalatorReportGeneralDataDto(),
    @SerializedName("technicalData") val technicalData: EscalatorReportTechnicalDataDto = EscalatorReportTechnicalDataDto(),
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: EscalatorReportInspectionAndTestingDto = EscalatorReportInspectionAndTestingDto(),
    @SerializedName("testingEscalator") val testingEscalator: String = "",
    @SerializedName("conclusion") val conclusion: String = "",
    @SerializedName("subInspectionType") val subInspectionType: String = "",
    @SerializedName("documentType") val documentType: String = "",
    @SerializedName("createdAt") val createdAt: String = ""
)