package com.nakersolutionid.nakersolutionid.di

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUserRepository : IUserRepository {
    override fun register(name: String, username: String, password: String): Flow<Resource<String>> {
        // Simulate a success response for previews
        return flowOf(Resource.Success("Registration successful"))
    }

    override fun login(username: String, password: String): Flow<Resource<String>> {
        // Simulate a success response for previews
        return flowOf(Resource.Success("Login successful"))
    }
}