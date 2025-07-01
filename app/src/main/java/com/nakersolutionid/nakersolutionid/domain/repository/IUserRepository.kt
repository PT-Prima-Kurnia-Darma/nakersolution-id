package com.nakersolutionid.nakersolutionid.domain.repository

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import com.nakersolutionid.nakersolutionid.utils.ThemeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IUserRepository {
    val currentUser: StateFlow<User>
    fun register(name: String, username: String, password: String): Flow<Resource<String>>
    fun login(username: String, password: String): Flow<Resource<String>>
    fun logout(): Flow<Resource<String>>
    fun updateUser(
        name: String?,
        username: String?,
        oldPassword: String?,
        newPassword: String?
    ): Flow<Resource<String>>
    suspend fun clearUser()
}