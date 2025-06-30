package com.nakersolutionid.nakersolutionid.data.preference.model

import com.nakersolutionid.nakersolutionid.domain.model.User

data class UserModel(
    val id: String,
    val name: String,
    val username: String,
    val token: String
)

fun UserModel.toUserDomain(): User {
    return User(
        id = id,
        name = name,
        username = username,
        token = token
    )
}