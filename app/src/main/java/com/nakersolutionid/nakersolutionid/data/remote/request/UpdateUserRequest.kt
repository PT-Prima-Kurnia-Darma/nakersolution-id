package com.nakersolutionid.nakersolutionid.data.remote.request

data class UpdateUserRequest(
    val name: String?,
    val username: String?,
    val oldPassword: String?,
    val newPassword: String?
)
