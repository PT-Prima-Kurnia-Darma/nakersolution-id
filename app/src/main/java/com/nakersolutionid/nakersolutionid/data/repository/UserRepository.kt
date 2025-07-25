package com.nakersolutionid.nakersolutionid.data.repository

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.preference.model.UserModel
import com.nakersolutionid.nakersolutionid.data.preference.model.toUserDomain
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiResponse
import com.nakersolutionid.nakersolutionid.domain.model.User
import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import com.nakersolutionid.nakersolutionid.utils.AppExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val userPreference: UserPreference,
    private val appExecutors: AppExecutors
) : IUserRepository {
    override val currentUser: StateFlow<User> = userPreference.getUser()
        .map {
            it.toUserDomain()
        }
        .stateIn(
            CoroutineScope(Dispatchers.IO),
            SharingStarted.WhileSubscribed(),
            User("", "", "", "")
        )

    override fun register(name: String, username: String, password: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.register(name, username, password).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResponse.data.message))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override fun login(username: String, password: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.login(username, password).first()) {
            is ApiResponse.Success -> {
                val user = apiResponse.data.data!!.user
                val token = apiResponse.data.data.token
                val message = apiResponse.data.message
                val userModel = UserModel(user.id, user.name, user.username, token)
                userPreference.saveUser(userModel)
                userPreference.setPreference(UserPreference.Keys.LOGGED_IN, true)
                emit(Resource.Success(message))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override fun logout(): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val token = userPreference.getUserToken() ?: ""
        when (val apiResponse = remoteDataSource.logout(token).first()) {
            is ApiResponse.Success -> {
                userPreference.clearUser()
                userPreference.setPreference(UserPreference.Keys.LOGGED_IN, false)
                emit(Resource.Success(apiResponse.data.message))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override fun updateUser(
        name: String?,
        username: String?,
        oldPassword: String?,
        newPassword: String?
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val token = userPreference.getUserToken() ?: ""
        when (val apiResponse = remoteDataSource.updateUser(name, username, oldPassword, newPassword, token).first()) {
            is ApiResponse.Success -> {
                val user = userPreference.getUser().first()
                val userId = apiResponse.data.data.userId
                val name = apiResponse.data.data.name ?: user.name
                val username = apiResponse.data.data.username ?: user.username
                val userModel = UserModel(userId, name, username, token)
                userPreference.saveUser(userModel)
                emit(Resource.Success(apiResponse.data.message))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
            is ApiResponse.Empty -> {}
        }
    }

    override suspend fun clearUser() = userPreference.clearUser()
    override suspend fun isLoggedIn(): Boolean = userPreference.isLoggedIn()
}