package com.nakersolutionid.nakersolutionid.data.remote.response.report.elevator

import com.google.gson.annotations.SerializedName

data class DeleteElevatorReportResponse(
	@SerializedName("message") val message: String = "",
	@SerializedName("status") val status: String = ""
)
