package com.nakersolutionid.nakersolutionid.data.remote.network

import com.nakersolutionid.nakersolutionid.data.remote.dto.common.BaseApiResponse
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
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
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
    suspend fun <Req, Res> createReport(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String,
        @Body payload: Req
    ): Response<BaseApiResponse<Res>>

    @GET("{path}")
    suspend fun <T> getAllReport(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String
    ): Response<BaseApiResponse<T>>

    @GET("{path}/{extraId}")
    suspend fun <T> getReportById(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String,
        @Path("extraId") extraId: String
    ): Response<BaseApiResponse<T>>

    @PUT("{path}/{extraId}")
    suspend fun <Req, Res> updateReport(
        @Header("Authorization") token: String,
        @Path("path", encoded = true) path: String,
        @Path("extraId") extraId: String,
        @Body payload: Req
    ): Response<BaseApiResponse<Res>>

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
    // endregion
}