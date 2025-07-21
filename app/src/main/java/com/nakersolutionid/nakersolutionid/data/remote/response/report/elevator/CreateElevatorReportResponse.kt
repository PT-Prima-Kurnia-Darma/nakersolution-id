package com.nakersolutionid.nakersolutionid.data.remote.response.report.elevator

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportGeneralDataDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportInspectionAndTestingDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportTechnicalDocumentInspectionDto

/**
 * Top-level DTO for the API response.
 */
data class CreateElevatorReportResponse(
	@SerializedName("status") val status: String = "",
	@SerializedName("message") val message: String = "",
	@SerializedName("data") val data: ElevatorReportDataWrapperDto = ElevatorReportDataWrapperDto()
)

/**
 * Wrapper DTO for the actual report data.
 */
data class ElevatorReportDataWrapperDto(
	@SerializedName("laporan") val laporan: ElevatorReportDetailDto = ElevatorReportDetailDto()
)

/**
 * DTO representing the detailed elevator report.
 */
data class ElevatorReportDetailDto(
	@SerializedName("id") val id: String = "",
	@SerializedName("inspectionType") val inspectionType: String = "",
	@SerializedName("examinationType") val examinationType: String = "",
	@SerializedName("createdAt") val createdAt: String = "",
	@SerializedName("equipmentType") val equipmentType: String = "",
	@SerializedName("extraId") val extraId: Long = 0,
	@SerializedName("generalData") val generalData: ElevatorReportGeneralDataDto = ElevatorReportGeneralDataDto(),
	@SerializedName("technicalDocumentInspection") val technicalDocumentInspection: ElevatorReportTechnicalDocumentInspectionDto = ElevatorReportTechnicalDocumentInspectionDto(),
	@SerializedName("inspectionAndTesting") val inspectionAndTesting: ElevatorReportInspectionAndTestingDto = ElevatorReportInspectionAndTestingDto(),
	@SerializedName("conclusion") val conclusion: String = "",
	@SerializedName("subInspectionType") val subInspectionType: String = "",
	@SerializedName("documentType") val documentType: String = ""
)