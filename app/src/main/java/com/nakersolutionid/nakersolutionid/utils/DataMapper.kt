package com.nakersolutionid.nakersolutionid.utils

import com.nakersolutionid.nakersolutionid.data.preference.model.UserModel
import com.nakersolutionid.nakersolutionid.domain.model.User

object DataMapper {
    fun mapModelToDomain(user: UserModel): User {
        return User(
            id = user.id,
            name = user.name,
            username = user.username,
            token = user.token
        )
    }
}