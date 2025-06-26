package com.nakersolutionid.nakersolutionid.domain.usecase

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteraction(private val userRepository: IUserRepository): UserUseCase {
    override fun register(user: User): Flow<Resource<User>> = userRepository.register(user)

}