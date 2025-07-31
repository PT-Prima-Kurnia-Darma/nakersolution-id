package com.nakersolutionid.nakersolutionid.data.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("oldPassword") val oldPassword: String?,
    @SerializedName("newPassword") val newPassword: String?
)
