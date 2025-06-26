package com.nakersolutionid.nakersolutionid.domain.repository

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun register(user: User): Flow<Resource<String>>
}