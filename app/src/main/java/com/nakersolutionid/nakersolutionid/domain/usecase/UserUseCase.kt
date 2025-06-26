package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun register(user: User): Flow<Resource<String>>
}