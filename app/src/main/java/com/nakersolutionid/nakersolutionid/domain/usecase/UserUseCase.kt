package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun register(name: String, username: String, password: String): Flow<Resource<String>>
    fun login(username: String, password: String): Flow<Resource<String>>
    fun logout(): Flow<Resource<String>>
    fun getUser(): Flow<Resource<User>>
    suspend fun getUserToken(): String?
    suspend fun clearUser()
}