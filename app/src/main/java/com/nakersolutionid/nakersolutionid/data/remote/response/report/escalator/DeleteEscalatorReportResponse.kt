package com.nakersolutionid.nakersolutionid.data.remote.response.report.escalator

import com.google.gson.annotations.SerializedName

data class DeleteEscalatorReportResponse(
    @SerializedName("message") val message: String = "",
    @SerializedName("status") val status: String = ""
)
