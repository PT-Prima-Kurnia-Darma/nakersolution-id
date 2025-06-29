package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteraction(private val userRepository: IUserRepository): UserUseCase {
    override fun register(name: String, username: String, password: String): Flow<Resource<String>> = userRepository.register(name, username, password)
    override fun login(username: String, password: String): Flow<Resource<String>> = userRepository.login(username, password)
    override fun logout(): Flow<Resource<String>> = userRepository.logout()
    override fun getUser(): Flow<Resource<User>> = userRepository.getUser()
    override suspend fun getUserToken(): String?  = userRepository.getUserToken()
    override suspend fun clearUser() = userRepository.clearUser()
}