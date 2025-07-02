package com.nakersolutionid.nakersolutionid.di

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

class FakeUserRepository : IUserRepository {
    override val currentUser: StateFlow<User> = MutableStateFlow(User("1", "Fake name", "Fake username", ""))

    override fun register(name: String, username: String, password: String): Flow<Resource<String>> {
        // Simulate a success response for previews
        return flowOf(Resource.Success("Registration successful"))
    }

    override fun login(username: String, password: String): Flow<Resource<String>> {
        // Simulate a success response for previews
        return flowOf(Resource.Success("Login successful"))
    }

    override fun logout(): Flow<Resource<String>> {
        return flowOf(Resource.Success("Logout successful"))
    }

    override fun updateUser(
        name: String?,
        username: String?,
        oldPassword: String?,
        newPassword: String?
    ): Flow<Resource<String>> {
        return flowOf(Resource.Success("Update user successful"))
    }

    override suspend fun clearUser() {}
    override suspend fun isLoggedIn(): Boolean = true
}