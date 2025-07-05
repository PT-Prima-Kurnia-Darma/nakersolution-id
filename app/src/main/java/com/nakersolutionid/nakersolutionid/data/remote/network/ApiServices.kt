package com.nakersolutionid.nakersolutionid.data.remote.network

import com.nakersolutionid.nakersolutionid.data.remote.request.LoginRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.RegisterRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.SendReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.UpdateUserRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.ValidateTokenRequest
import com.nakersolutionid.nakersolutionid.data.remote.response.login.LoginResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.logout.LogoutResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.register.RegisterResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.sendreport.SendReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.updateuser.UpdateUserResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.validatetoken.ValidateTokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiServices {
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

    @POST("auth/validateToken")
    suspend fun validateToken(
        @Body request: ValidateTokenRequest
    ) : ValidateTokenResponse

    @POST("elevatorEskalator/elevator/laporan")
    suspend fun sendReport(
        @Header("Authorization") token: String,
        @Body request: SendReportRequest
    ) : SendReportResponse
}