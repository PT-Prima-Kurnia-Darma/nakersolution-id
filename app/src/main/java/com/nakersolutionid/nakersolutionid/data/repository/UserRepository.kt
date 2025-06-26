package com.nakersolutionid.nakersolutionid.data.repository

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiResponse
import com.nakersolutionid.nakersolutionid.data.remote.request.RegisterRequest
import com.nakersolutionid.nakersolutionid.domain.model.User
import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import com.nakersolutionid.nakersolutionid.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IUserRepository {
    override fun register(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.register(RegisterRequest(user.name, user.username, user.password)).first()) {
            is ApiResponse.Success -> {
                val registeredUser = User(
                    name = apiResponse.data.data?.name ?: "",
                    username = apiResponse.data.data?.username ?: "",
                    password = "" // Do not store the password in the domain model
                )
                emit(Resource.Success(registeredUser))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
            is ApiResponse.Empty -> {
                // Handle empty response if necessary
            }
        }
    }
}