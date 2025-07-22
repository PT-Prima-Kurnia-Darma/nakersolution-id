package com.nakersolutionid.nakersolutionid.data.remote

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.nakersolutionid.nakersolutionid.data.remote.dto.*
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiResponse
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiServices
import com.nakersolutionid.nakersolutionid.data.remote.request.*
import com.nakersolutionid.nakersolutionid.data.remote.response.deleteuser.DeleteUserApiResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.login.LoginResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.logout.LogoutResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.register.RegisterResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.updateuser.UpdateUserResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.validatetoken.ValidateTokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

class RemoteDataSource(private val apiServices: ApiServices) {
    // region Auth
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

    fun validateToken(tokenRequest: ValidateTokenRequest): Flow<ApiResponse<ValidateTokenResponse>> {
        return flow<ApiResponse<ValidateTokenResponse>> {
            val response = apiServices.validateToken(tokenRequest)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<ValidateTokenResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, ValidateTokenResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteUser(id: String): Flow<ApiResponse<DeleteUserApiResponse>> {
        return flow<ApiResponse<DeleteUserApiResponse>> {
            val response = apiServices.deleteUser(id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<DeleteUserApiResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, DeleteUserApiResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }
    // endregion

    // region Elevator
    fun createElevatorReport(
        token: String,
        request: CreateElevatorReportBody
    ): Flow<ApiResponse<CreateElevatorReportResponse>> {
        return flow<ApiResponse<CreateElevatorReportResponse>> {
            val response = apiServices.createElevatorReport("Bearer $token", request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateElevatorReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateElevatorReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }

                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }

                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }

            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getElevatorReports(token: String): Flow<ApiResponse<GetElevatorReportsResponse>> {
        return flow<ApiResponse<GetElevatorReportsResponse>> {
            val response = apiServices.getElevatorReports("Bearer $token")
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<GetElevatorReportsResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, GetElevatorReportsResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getElevatorReport(token: String, id: String): Flow<ApiResponse<CreateElevatorReportResponse>> {
        return flow<ApiResponse<CreateElevatorReportResponse>> {
            val response = apiServices.getElevatorReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateElevatorReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateElevatorReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun updateElevatorReport(
        token: String,
        extraId: String,
        request: CreateElevatorReportBody
    ): Flow<ApiResponse<CreateElevatorReportResponse>> {
        return flow<ApiResponse<CreateElevatorReportResponse>> {
            val response = apiServices.updateElevatorReport("Bearer $token", extraId, request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateElevatorReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateElevatorReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }

                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }

                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }

            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteElevatorReport(token: String, id: String): Flow<ApiResponse<DeleteElevatorReportResponse>> {
        return flow<ApiResponse<DeleteElevatorReportResponse>> {
            val response = apiServices.deleteElevatorReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<DeleteElevatorReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, DeleteElevatorReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun downloadElevatorReport(token: String, id: String): Flow<ApiResponse<ResponseBody>> {
        return flow<ApiResponse<ResponseBody>> {
            val response = apiServices.downloadElevatorReport("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiResponse.Success(it))
                } ?: emit(ApiResponse.Error("Respon Body Kosong"))
            } else {
                emit(ApiResponse.Error("Gagal mengunduh laporan (Code: ${response.code()})"))
            }
        }.catch { e ->
            val errorResponse: ApiResponse<ResponseBody> = when (e) {
                is HttpException -> {
                    ApiResponse.Error("Gagal mengunduh laporan (HTTP: ${e.code()})")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan saat mengunduh laporan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }
    // endregion

    // region Escalator
    fun createEscalatorReport(
        token: String,
        request: CreateEscalatorReportBody
    ): Flow<ApiResponse<CreateEscalatorReportResponse>> {
        return flow<ApiResponse<CreateEscalatorReportResponse>> {
            val response = apiServices.createEscalatorReport("Bearer $token", request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateEscalatorReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateEscalatorReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getEscalatorReports(token: String): Flow<ApiResponse<GetEscalatorReportsResponse>> {
        return flow<ApiResponse<GetEscalatorReportsResponse>> {
            val response = apiServices.getEscalatorReports("Bearer $token")
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<GetEscalatorReportsResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, GetEscalatorReportsResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getEscalatorReport(token: String, id: String): Flow<ApiResponse<CreateEscalatorReportResponse>> {
        return flow<ApiResponse<CreateEscalatorReportResponse>> {
            val response = apiServices.getEscalatorReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateEscalatorReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateEscalatorReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun updateEscalatorReport(
        token: String,
        extraId: String,
        request: CreateEscalatorReportBody
    ): Flow<ApiResponse<CreateEscalatorReportResponse>> {
        return flow<ApiResponse<CreateEscalatorReportResponse>> {
            val response = apiServices.updateEscalatorReport("Bearer $token", extraId, request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateEscalatorReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateEscalatorReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteEscalatorReport(token: String, id: String): Flow<ApiResponse<DeleteEscalatorReportResponse>> {
        return flow<ApiResponse<DeleteEscalatorReportResponse>> {
            val response = apiServices.deleteEscalatorReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<DeleteEscalatorReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, DeleteEscalatorReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun downloadEscalatorReport(token: String, id: String): Flow<ApiResponse<ResponseBody>> {
        return flow<ApiResponse<ResponseBody>> {
            val response = apiServices.downloadEscalatorReport("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiResponse.Success(it))
                } ?: emit(ApiResponse.Error("Respon Body Kosong"))
            } else {
                emit(ApiResponse.Error("Gagal mengunduh laporan (Code: ${response.code()})"))
            }
        }.catch { e ->
            val errorResponse: ApiResponse<ResponseBody> = when (e) {
                is HttpException -> {
                    ApiResponse.Error("Gagal mengunduh laporan (HTTP: ${e.code()})")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan saat mengunduh laporan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }
    // endregion

    // region Forklift
    fun createForkliftReport(
        token: String,
        request: CreateForkliftReportBody
    ): Flow<ApiResponse<CreateForkliftReportResponse>> {
        return flow<ApiResponse<CreateForkliftReportResponse>> {
            val response = apiServices.createForkliftReport("Bearer $token", request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateForkliftReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateForkliftReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getForkliftReports(token: String): Flow<ApiResponse<GetForkliftReportsResponse>> {
        return flow<ApiResponse<GetForkliftReportsResponse>> {
            val response = apiServices.getForkliftReports("Bearer $token")
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<GetForkliftReportsResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, GetForkliftReportsResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getForkliftReport(token: String, id: String): Flow<ApiResponse<CreateForkliftReportResponse>> {
        return flow<ApiResponse<CreateForkliftReportResponse>> {
            val response = apiServices.getForkliftReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateForkliftReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateForkliftReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun updateForkliftReport(
        token: String,
        extraId: String,
        request: CreateForkliftReportBody
    ): Flow<ApiResponse<CreateForkliftReportResponse>> {
        return flow<ApiResponse<CreateForkliftReportResponse>> {
            val response = apiServices.updateForkliftReport("Bearer $token", extraId, request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateForkliftReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateForkliftReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteForkliftReport(token: String, id: String): Flow<ApiResponse<DeleteForkliftReportResponse>> {
        return flow<ApiResponse<DeleteForkliftReportResponse>> {
            val response = apiServices.deleteForkliftReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<DeleteForkliftReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, DeleteForkliftReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun downloadForkliftReport(token: String, id: String): Flow<ApiResponse<ResponseBody>> {
        return flow<ApiResponse<ResponseBody>> {
            val response = apiServices.downloadForkliftReport("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiResponse.Success(it))
                } ?: emit(ApiResponse.Error("Respon Body Kosong"))
            } else {
                emit(ApiResponse.Error("Gagal mengunduh laporan (Code: ${response.code()})"))
            }
        }.catch { e ->
            val errorResponse: ApiResponse<ResponseBody> = when (e) {
                is HttpException -> {
                    ApiResponse.Error("Gagal mengunduh laporan (HTTP: ${e.code()})")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan saat mengunduh laporan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }
    // endregion

    // region Gantry Crane
    fun createGantryCraneReport(
        token: String,
        request: CreateGantryCraneReportBody
    ): Flow<ApiResponse<CreateGantryCraneReportResponse>> {
        return flow<ApiResponse<CreateGantryCraneReportResponse>> {
            val response = apiServices.createGantryCraneReport("Bearer $token", request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateGantryCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateGantryCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getGantryCraneReports(token: String): Flow<ApiResponse<GetGantryCraneReportsResponse>> {
        return flow<ApiResponse<GetGantryCraneReportsResponse>> {
            val response = apiServices.getGantryCraneReports("Bearer $token")
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<GetGantryCraneReportsResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, GetGantryCraneReportsResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getGantryCraneReport(token: String, id: String): Flow<ApiResponse<CreateGantryCraneReportResponse>> {
        return flow<ApiResponse<CreateGantryCraneReportResponse>> {
            val response = apiServices.getGantryCraneReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateGantryCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateGantryCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun updateGantryCraneReport(
        token: String,
        extraId: String,
        request: CreateGantryCraneReportBody
    ): Flow<ApiResponse<CreateGantryCraneReportResponse>> {
        return flow<ApiResponse<CreateGantryCraneReportResponse>> {
            val response = apiServices.updateGantryCraneReport("Bearer $token", extraId, request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateGantryCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateGantryCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteGantryCraneReport(token: String, id: String): Flow<ApiResponse<DeleteGantryCraneReportResponse>> {
        return flow<ApiResponse<DeleteGantryCraneReportResponse>> {
            val response = apiServices.deleteGantryCraneReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<DeleteGantryCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, DeleteGantryCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun downloadGantryCraneReport(token: String, id: String): Flow<ApiResponse<ResponseBody>> {
        return flow<ApiResponse<ResponseBody>> {
            val response = apiServices.downloadGantryCraneReport("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiResponse.Success(it))
                } ?: emit(ApiResponse.Error("Respon Body Kosong"))
            } else {
                emit(ApiResponse.Error("Gagal mengunduh laporan (Code: ${response.code()})"))
            }
        }.catch { e ->
            val errorResponse: ApiResponse<ResponseBody> = when (e) {
                is HttpException -> {
                    ApiResponse.Error("Gagal mengunduh laporan (HTTP: ${e.code()})")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan saat mengunduh laporan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }
    // endregion

    // region Gondola
    fun createGondolaReport(
        token: String,
        request: CreateGondolaReportBody
    ): Flow<ApiResponse<CreateGondolaReportResponse>> {
        return flow<ApiResponse<CreateGondolaReportResponse>> {
            val response = apiServices.createGondolaReport("Bearer $token", request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateGondolaReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateGondolaReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getGondolaReports(token: String): Flow<ApiResponse<GetGondolaReportsResponse>> {
        return flow<ApiResponse<GetGondolaReportsResponse>> {
            val response = apiServices.getGondolaReports("Bearer $token")
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<GetGondolaReportsResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, GetGondolaReportsResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getGondolaReport(token: String, id: String): Flow<ApiResponse<CreateGondolaReportResponse>> {
        return flow<ApiResponse<CreateGondolaReportResponse>> {
            val response = apiServices.getGondolaReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateGondolaReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateGondolaReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun updateGondolaReport(
        token: String,
        extraId: String,
        request: CreateGondolaReportBody
    ): Flow<ApiResponse<CreateGondolaReportResponse>> {
        return flow<ApiResponse<CreateGondolaReportResponse>> {
            val response = apiServices.updateGondolaReport("Bearer $token", extraId, request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateGondolaReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateGondolaReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteGondolaReport(token: String, id: String): Flow<ApiResponse<DeleteGondolaReportResponse>> {
        return flow<ApiResponse<DeleteGondolaReportResponse>> {
            val response = apiServices.deleteGondolaReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<DeleteGondolaReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, DeleteGondolaReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun downloadGondolaReport(token: String, id: String): Flow<ApiResponse<ResponseBody>> {
        return flow<ApiResponse<ResponseBody>> {
            val response = apiServices.downloadGondolaReport("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiResponse.Success(it))
                } ?: emit(ApiResponse.Error("Respon Body Kosong"))
            } else {
                emit(ApiResponse.Error("Gagal mengunduh laporan (Code: ${response.code()})"))
            }
        }.catch { e ->
            val errorResponse: ApiResponse<ResponseBody> = when (e) {
                is HttpException -> {
                    ApiResponse.Error("Gagal mengunduh laporan (HTTP: ${e.code()})")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan saat mengunduh laporan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }
    // endregion

    // region Mobile Crane
    fun createMobileCraneReport(
        token: String,
        request: CreateMobileCraneReportBody
    ): Flow<ApiResponse<CreateMobileCraneReportResponse>> {
        return flow<ApiResponse<CreateMobileCraneReportResponse>> {
            val response = apiServices.createMobileCraneReport("Bearer $token", request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateMobileCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateMobileCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getMobileCraneReports(token: String): Flow<ApiResponse<GetMobileCraneReportsResponse>> {
        return flow<ApiResponse<GetMobileCraneReportsResponse>> {
            val response = apiServices.getMobileCraneReports("Bearer $token")
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<GetMobileCraneReportsResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, GetMobileCraneReportsResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getMobileCraneReport(token: String, id: String): Flow<ApiResponse<CreateMobileCraneReportResponse>> {
        return flow<ApiResponse<CreateMobileCraneReportResponse>> {
            val response = apiServices.getMobileCraneReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateMobileCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateMobileCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun updateMobileCraneReport(
        token: String,
        extraId: String,
        request: CreateMobileCraneReportBody
    ): Flow<ApiResponse<CreateMobileCraneReportResponse>> {
        return flow<ApiResponse<CreateMobileCraneReportResponse>> {
            val response = apiServices.updateMobileCraneReport("Bearer $token", extraId, request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateMobileCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateMobileCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteMobileCraneReport(token: String, id: String): Flow<ApiResponse<DeleteMobileCraneReportResponse>> {
        return flow<ApiResponse<DeleteMobileCraneReportResponse>> {
            val response = apiServices.deleteMobileCraneReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<DeleteMobileCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, DeleteMobileCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun downloadMobileCraneReport(token: String, id: String): Flow<ApiResponse<ResponseBody>> {
        return flow<ApiResponse<ResponseBody>> {
            val response = apiServices.downloadMobileCraneReport("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiResponse.Success(it))
                } ?: emit(ApiResponse.Error("Respon Body Kosong"))
            } else {
                emit(ApiResponse.Error("Gagal mengunduh laporan (Code: ${response.code()})"))
            }
        }.catch { e ->
            val errorResponse: ApiResponse<ResponseBody> = when (e) {
                is HttpException -> {
                    ApiResponse.Error("Gagal mengunduh laporan (HTTP: ${e.code()})")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan saat mengunduh laporan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }
    // endregion

    // region Overhead Crane
    fun createOverheadCraneReport(
        token: String,
        request: CreateOverheadCraneReportBody
    ): Flow<ApiResponse<CreateOverheadCraneReportResponse>> {
        return flow<ApiResponse<CreateOverheadCraneReportResponse>> {
            val response = apiServices.createOverheadCraneReport("Bearer $token", request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateOverheadCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateOverheadCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getOverheadCraneReports(token: String): Flow<ApiResponse<GetOverheadCraneReportsResponse>> {
        return flow<ApiResponse<GetOverheadCraneReportsResponse>> {
            val response = apiServices.getOverheadCraneReports("Bearer $token")
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<GetOverheadCraneReportsResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, GetOverheadCraneReportsResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
                }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun getOverheadCraneReport(token: String, id: String): Flow<ApiResponse<CreateOverheadCraneReportResponse>> {
        return flow<ApiResponse<CreateOverheadCraneReportResponse>> {
            val response = apiServices.getOverheadCraneReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateOverheadCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateOverheadCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun updateOverheadCraneReport(
        token: String,
        extraId: String,
        request: CreateOverheadCraneReportBody
    ): Flow<ApiResponse<CreateOverheadCraneReportResponse>> {
        return flow<ApiResponse<CreateOverheadCraneReportResponse>> {
            val response = apiServices.updateOverheadCraneReport("Bearer $token", extraId, request)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<CreateOverheadCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, CreateOverheadCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteOverheadCraneReport(token: String, id: String): Flow<ApiResponse<DeleteOverheadCraneReportResponse>> {
        return flow<ApiResponse<DeleteOverheadCraneReportResponse>> {
            val response = apiServices.deleteOverheadCraneReport("Bearer $token", id)
            emit(ApiResponse.Success(response))
        }.catch { e ->
            val errorResponse: ApiResponse<DeleteOverheadCraneReportResponse> = when (e) {
                is HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val parsedError = Gson().fromJson(it, DeleteOverheadCraneReportResponse::class.java)
                            ApiResponse.Error(parsedError.message)
                        } catch (jsonEx: JsonSyntaxException) {
                            ApiResponse.Error("Terjadi sebuah kesalahan (0x1)")
                        }
                    } ?: ApiResponse.Error("Terjadi sebuah kesalahan (0x2)")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun downloadOverheadCraneReport(token: String, id: String): Flow<ApiResponse<ResponseBody>> {
        return flow<ApiResponse<ResponseBody>> {
            val response = apiServices.downloadOverheadCraneReport("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiResponse.Success(it))
                } ?: emit(ApiResponse.Error("Respon Body Kosong"))
            } else {
                emit(ApiResponse.Error("Gagal mengunduh laporan (Code: ${response.code()})"))
            }
        }.catch { e ->
            val errorResponse: ApiResponse<ResponseBody> = when (e) {
                is HttpException -> {
                    ApiResponse.Error("Gagal mengunduh laporan (HTTP: ${e.code()})")
                }
                is SocketTimeoutException -> {
                    ApiResponse.Error("Gagal terhubung, waktu tunggu koneksi habis")
                }
                else -> {
                    ApiResponse.Error("Terjadi sebuah kesalahan saat mengunduh laporan (0x3)")
                }
            }
            emit(errorResponse)
        }.flowOn(Dispatchers.IO)
    }
    // endregion
}