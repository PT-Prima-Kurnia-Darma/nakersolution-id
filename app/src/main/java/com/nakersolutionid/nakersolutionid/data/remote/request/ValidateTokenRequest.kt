package com.nakersolutionid.nakersolutionid.data.remote.request

import com.google.gson.annotations.SerializedName

data class ValidateTokenRequest(
    @SerializedName("token") val token: String
)
