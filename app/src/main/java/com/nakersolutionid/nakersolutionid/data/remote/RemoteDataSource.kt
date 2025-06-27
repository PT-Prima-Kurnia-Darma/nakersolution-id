package com.nakersolutionid.nakersolutionid.data.remote

import com.google.gson.JsonParser
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiResponse
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiServices
import com.nakersolutionid.nakersolutionid.data.remote.request.LoginRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.RegisterRequest
import com.nakersolutionid.nakersolutionid.data.remote.response.login.LoginResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.register.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RemoteDataSource(private val apiServices: ApiServices) {
    fun register(name: String, username: String, password: String): Flow<ApiResponse<RegisterResponse>> {
        return flow {
            try {
                val response = apiServices.register(RegisterRequest(name, username, password))
                emit(ApiResponse.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    val jsonElement = JsonParser.parseString(it)
                    val detail = jsonElement
                        .asJsonObject["message"]
                        .asString
                    emit(ApiResponse.Error(detail))
                }
            }
        }
    }

    fun login(username: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return flow {
            try {
                val response = apiServices.login(LoginRequest(username, password))
                emit(ApiResponse.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    val jsonElement = JsonParser.parseString(it)
                    val detail = jsonElement
                        .asJsonObject["message"]
                        .asString
                    emit(ApiResponse.Error(detail))
                }
            }
        }
    }
}