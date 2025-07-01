package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import com.nakersolutionid.nakersolutionid.utils.ThemeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class UserInteraction(private val userRepository: IUserRepository): UserUseCase {
    override val currentUser: StateFlow<User> = userRepository.currentUser
    override fun register(name: String, username: String, password: String): Flow<Resource<String>> = userRepository.register(name, username, password)
    override fun login(username: String, password: String): Flow<Resource<String>> = userRepository.login(username, password)
    override fun logout(): Flow<Resource<String>> = userRepository.logout()
    override fun updateUser(
        name: String?,
        username: String?,
        oldPassword: String?,
        newPassword: String?
    ): Flow<Resource<String>> = userRepository.updateUser(
        name,
        username,
        oldPassword,
        newPassword
    )

    override suspend fun clearUser() = userRepository.clearUser()
}