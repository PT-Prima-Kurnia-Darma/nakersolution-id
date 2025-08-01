package com.nakersolutionid.nakersolutionid.data.remote

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.BaseApiResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ErrorResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.GetAllAuditResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.ml.MLData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ml.MLError
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiResponse
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiServices
import com.nakersolutionid.nakersolutionid.data.remote.network.MLApiServices
import com.nakersolutionid.nakersolutionid.data.remote.request.LoginRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.RegisterRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.UpdateUserRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.ValidateTokenRequest
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.Type
import java.net.SocketTimeoutException

class RemoteDataSource(
    val apiServices: ApiServices,
    val mlApiServices: MLApiServices
) {
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

    // region Generic Endpoint
    inline fun <reified Req : Any, reified Res : Any> createReport(
        token: String,
        path: String,
        payload: Req
    ): Flow<ApiResponse<BaseApiResponse<Res>>> {
        return flow {
            val result: ApiResponse<BaseApiResponse<Res>> = try {
                val requestJson = Gson().toJson(payload)
                val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())

                val response: Response<ResponseBody> =
                    apiServices.createReport(token, path, requestBody)
                if (response.isSuccessful) {
                    val responseBody: ResponseBody? = response.body()
                    if (responseBody != null) {
                        val jsonString = responseBody.string()

                        val type: Type = object : TypeToken<BaseApiResponse<Res>>() {}.type
                        val baseApiResponse: BaseApiResponse<Res> = Gson().fromJson(jsonString, type)

                        ApiResponse.Success(baseApiResponse)
                    } else {
                        ApiResponse.Error("Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val parsedError = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    ApiResponse.Error(parsedError?.message ?: "Parsing JSON response failed")
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                ApiResponse.Error(errorBody ?: "Unknown error")
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /*fun <T> getAllReport(
        token: String,
        path: String
    ): Flow<ApiResponse<BaseApiResponse<T>>> {
        return flow {
            val result: ApiResponse<BaseApiResponse<T>> = try {
                val response = apiServices.getAllReport<T>("Bearer $token", path)
                if (response.isSuccessful) {
                    response.body()?.let {
                        ApiResponse.Success(it)
                    } ?: ApiResponse.Error("Response body is null")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val parsedError = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    ApiResponse.Error(parsedError?.message ?: "Parsing JSON response failed")
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                ApiResponse.Error(errorBody ?: "Unknown error")
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }*/

    /*fun <T> getReportById(
        token: String,
        path: String,
        extraId: String
    ): Flow<ApiResponse<BaseApiResponse<T>>> {
        return flow {
            val result: ApiResponse<BaseApiResponse<T>> = try {
                val response = apiServices.getReportById<T>("Bearer $token", path, extraId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        ApiResponse.Success(it)
                    } ?: ApiResponse.Error("Response body is null")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val parsedError = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    ApiResponse.Error(parsedError?.message ?: "Parsing JSON response failed")
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                ApiResponse.Error(errorBody ?: "Unknown error")
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }*/

    inline fun <reified Req : Any, reified Res : Any> updateReport(
        token: String,
        path: String,
        extraId: String,
        payload: Req
    ): Flow<ApiResponse<BaseApiResponse<Res>>> {
        return flow {
            val result: ApiResponse<BaseApiResponse<Res>> = try {
                val requestJson = Gson().toJson(payload)
                val requestBody = requestJson.toRequestBody("application/json".toMediaTypeOrNull())

                val response: Response<ResponseBody> =
                    apiServices.updateReport(token, path, extraId, requestBody)
                if (response.isSuccessful) {
                    val responseBody: ResponseBody? = response.body()
                    if (responseBody != null) {
                        val jsonString = responseBody.string()

                        val type: Type = object : TypeToken<BaseApiResponse<Res>>() {}.type
                        val baseApiResponse: BaseApiResponse<Res> = Gson().fromJson(jsonString, type)

                        ApiResponse.Success(baseApiResponse)
                    } else {
                        ApiResponse.Error("Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val parsedError = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    ApiResponse.Error(parsedError?.message ?: " Parsing JSON response failed")
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                ApiResponse.Error(errorBody ?: "Unknown error")
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteReport(
        token: String,
        path: String,
        extraId: String
    ): Flow<ApiResponse<BaseApiResponse<Unit>>> {
        return flow {
            val result: ApiResponse<BaseApiResponse<Unit>> = try {
                val response = apiServices.deleteReport(token, path, extraId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        ApiResponse.Success(it)
                    } ?: ApiResponse.Error("Response body is null")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val parsedError = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    ApiResponse.Error(parsedError?.message ?: "Parsing JSON response failed")
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                ApiResponse.Error(errorBody ?: "Unknown error")
            } catch (e: Exception) {
                ApiResponse.Error(e.message.toString())
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
    // endregion

    suspend fun getAllAudits(token: String, page: Int, size: Int): Response<GetAllAuditResponse> =
        apiServices.getAllAudits(token, page, size)

    suspend fun getMLResult(secret: String, payload: JsonElement): ApiResponse<MLData> {
        try {
            val response = mlApiServices.getMLResult(secret, payload)

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                val parsedError = Gson().fromJson(errorBody, MLError::class.java)
                return ApiResponse.Error(parsedError?.error ?: "Parsing JSON response failed")
            }

            val body = response.body()
            if (body == null) {
                return ApiResponse.Error("Response body is null")
            }

            return ApiResponse.Success(body)
        } catch (e: Exception) {
            return ApiResponse.Error(e.message ?: "Something went wrong")
        }
    }
}