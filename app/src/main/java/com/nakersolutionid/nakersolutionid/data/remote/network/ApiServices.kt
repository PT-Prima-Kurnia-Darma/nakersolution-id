package com.nakersolutionid.nakersolutionid.data.remote.network

import com.nakersolutionid.nakersolutionid.data.remote.dto.common.BaseApiResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.GetAllAuditResponse
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
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ApiServices {
    // region Auth
    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ) : RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ) : LoginResponse

    @POST("auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ) : LogoutResponse

    @PUT("auth/change")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body request: UpdateUserRequest
    ) : UpdateUserResponse

    @POST("auth/validateToken") // TODO: Implement validation for JWT token
    suspend fun validateToken(
        @Body request: ValidateTokenRequest
    ) : ValidateTokenResponse

    @DELETE("auth/delete/{id}") // TODO: Implement delete user
    suspend fun deleteUser(
        @Path("id") id: String
    ) : DeleteUserApiResponse
    // endregion

    // region Generic Endpoint
    @POST("{path}")
    suspend fun createReport(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String,
        @Body payload: RequestBody
    ): Response<ResponseBody>

    @GET("{path}")
    suspend fun getAllReport(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String
    ): Response<ResponseBody>

    @GET("{path}/{extraId}")
    suspend fun getReportById(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String,
        @Path("extraId") extraId: String
    ): Response<ResponseBody>

    @PUT("{path}/{extraId}")
    suspend fun updateReport(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String,
        @Path("extraId") extraId: String,
        @Body payload: RequestBody
    ): Response<ResponseBody>

    @DELETE("{path}/{extraId}")
    suspend fun deleteReport(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String,
        @Path("extraId") extraId: String
    ): Response<BaseApiResponse<Unit>>

    @Streaming
    @GET("{path}/download/{extraId}")
    suspend fun downloadDocument(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String,
        @Path("extraId") extraId: String
    ): Response<ResponseBody>

    @GET("audits/all")
    suspend fun getAllAudits(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetAllAuditResponse>
    // endregion
}