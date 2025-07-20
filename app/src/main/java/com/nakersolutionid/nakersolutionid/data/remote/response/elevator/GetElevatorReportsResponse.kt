package com.nakersolutionid.nakersolutionid.data.remote.response.elevator

import com.google.gson.annotations.SerializedName

/**
 * Top-level DTO for the API response.
 */
data class GetElevatorReportsResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: GetElevatorReportsDataWrapperDto = GetElevatorReportsDataWrapperDto()
)

/**
 * Wrapper DTO for the actual report data.
 */
data class GetElevatorReportsDataWrapperDto(
    @SerializedName("laporan") val listLaporan: List<ElevatorReportDetailDto> = emptyList()
)