package com.nakersolutionid.nakersolutionid.data.remote

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.nakersolutionid.nakersolutionid.data.remote.dto.ElevatorReportDto
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiResponse
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiServices
import com.nakersolutionid.nakersolutionid.data.remote.request.LoginRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.RegisterRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.SendElevatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.UpdateUserRequest
import com.nakersolutionid.nakersolutionid.data.remote.response.elevator.ElevatorReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.login.LoginResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.logout.LogoutResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.register.RegisterResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.updateuser.UpdateUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.net.SocketTimeoutException

class RemoteDataSource(private val apiServices: ApiServices) {
    fun register(name: String, username: String, password: String): Flow<ApiResponse<RegisterResponse>> {
        return flow {
            try {
                val response = apiServices.register(RegisterRequest(name, username, password))
                emit(ApiResponse.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    try {
                        val parsedError = Gson().fromJson(errorBody, RegisterResponse::class.java)
                        if (parsedError != null) {
                            emit(ApiResponse.Error(parsedError.message))
                        } else {
                            emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x0)"))
                        }
                    } catch (e: JsonSyntaxException) {
                        emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x1)"))
                    }
                } ?: run {
                    emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x2)"))
                }
            } catch (e: SocketTimeoutException) {
                emit(ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis"))
            } catch (e: Exception) {
                emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x3)"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun login(username: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return flow {
            try {
                val response = apiServices.login(LoginRequest(username, password))
                emit(ApiResponse.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    try {
                        val parsedError = Gson().fromJson(errorBody, LoginResponse::class.java)
                        if (parsedError != null) {
                            emit(ApiResponse.Error(parsedError.message))
                        } else {
                            emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x0)"))
                        }
                    } catch (e: JsonSyntaxException) {
                        emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x1)"))
                    }
                } ?: run {
                    emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x2)"))
                }
            } catch (e: SocketTimeoutException) {
                emit(ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis"))
            } catch (e: Exception) {
                emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x3)"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun logout(token: String): Flow<ApiResponse<LogoutResponse>> {
        return flow {
            try {
                val response = apiServices.logout("Bearer $token")
                emit(ApiResponse.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    try {
                        val parsedError = Gson().fromJson(errorBody, LogoutResponse::class.java)
                        if (parsedError != null) {
                            emit(ApiResponse.Error(parsedError.message))
                        } else {
                            emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x0)"))
                        }
                    } catch (e: JsonSyntaxException) {
                        emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x1)"))
                    }
                } ?: run {
                    emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x2)"))
                }
            } catch (e: SocketTimeoutException) {
                emit(ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis"))
            } catch (e: Exception) {
                emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x3)"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun updateUser(
        name: String?,
        username: String?,
        oldPassword: String?,
        newPassword: String?,
        token: String
    ): Flow<ApiResponse<UpdateUserResponse>> {
        return flow {
            try {
                val request = UpdateUserRequest(name, username, oldPassword, newPassword)
                val response = apiServices.updateUser("Bearer $token", request)
                emit(ApiResponse.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    try {
                        val parsedError = Gson().fromJson(errorBody, UpdateUserResponse::class.java)
                        if (parsedError != null) {
                            emit(ApiResponse.Error(parsedError.message))
                        } else {
                            emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x0)"))
                        }
                    } catch (e: JsonSyntaxException) {
                        emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x1)"))
                    }
                } ?: run {
                    emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x2)"))
                }
            } catch (e: SocketTimeoutException) {
                emit(ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis"))
            } catch (e: Exception) {
                emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x3)"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun sendReport(token: String, request: ElevatorReportDto): Flow<ApiResponse<ElevatorReportResponse>> {
        return flow {
            try {
                val response = apiServices.sendReport("Bearer $token", request)
                emit(ApiResponse.Success(response))
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    try {
                        val parsedError = Gson().fromJson(errorBody, ElevatorReportResponse::class.java)
                        if (parsedError != null) {
                            emit(ApiResponse.Error(parsedError.message))
                        } else {
                            emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x0)"))
                        }
                    } catch (e: JsonSyntaxException) {
                        emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x1)"))
                    }
                } ?: run {
                    emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x2)"))
                }
            } catch (e: SocketTimeoutException) {
                emit(ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis"))
            } catch (e: Exception) {
                emit(ApiResponse.Error("Terjadi sebuah kesalahan (0x3)"))
            }
        }.flowOn(Dispatchers.IO)
    }
}