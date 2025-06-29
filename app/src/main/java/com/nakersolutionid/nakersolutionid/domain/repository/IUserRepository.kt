package com.nakersolutionid.nakersolutionid.domain.repository

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun register(name: String, username: String, password: String): Flow<Resource<String>>
    fun login(username: String, password: String): Flow<Resource<String>>
    fun logout(): Flow<Resource<String>>
    fun getUser(): Flow<Resource<User>>
    suspend fun getUserToken(): String?
    suspend fun clearUser()
}