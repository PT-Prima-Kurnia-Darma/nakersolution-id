package com.nakersolutionid.nakersolutionid.data.remote.response.report.escalator

import com.google.gson.annotations.SerializedName

data class GetEscalatorReportsResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: GetEscalatorReportResponseDataDto = GetEscalatorReportResponseDataDto()
)

data class GetEscalatorReportResponseDataDto(
    @SerializedName("laporan") val listLaporan: List<EscalatorReportLaporanDto> = emptyList()
)