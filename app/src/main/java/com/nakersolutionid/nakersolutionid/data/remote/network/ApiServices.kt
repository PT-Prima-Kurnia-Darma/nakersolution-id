package com.nakersolutionid.nakersolutionid.data.remote.network

import com.nakersolutionid.nakersolutionid.data.remote.dto.ElevatorReportDto
import com.nakersolutionid.nakersolutionid.data.remote.request.LoginRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.RegisterRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.SendElevatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.UpdateUserRequest
import com.nakersolutionid.nakersolutionid.data.remote.request.ValidateTokenRequest
import com.nakersolutionid.nakersolutionid.data.remote.response.elevator.ElevatorReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.elevator.ElevatorReportsResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.login.LoginResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.logout.LogoutResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.register.RegisterResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.updateuser.UpdateUserResponse
import com.nakersolutionid.nakersolutionid.data.remote.response.validatetoken.ValidateTokenResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServices {
    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ) : RegisterResponse

    @POST("auth/login") // TODO: Create endpoints for checking if JWT is expired
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
        @Body request: ElevatorReportDto
    ) : ElevatorReportResponse

    @GET("elevatorEskalator/elevator/laporan")
    suspend fun getReports(
        @Header("Authorization") token: String
    ) : ElevatorReportsResponse

    @GET("elevatorEskalator/elevator/laporan/{id}")
    suspend fun getReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

    @PUT("elevatorEskalator/elevator/laporan/{id}")
    suspend fun updateReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

    @DELETE("elevatorEskalator/elevator/laporan/{id}")
    suspend fun deleteReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

    @POST("elevatorEskalator/elevator/laporan/{id}/download")
    suspend fun downloadReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

    @GET("elevatorEskalator/elevator/bap/prefill")
    suspend fun prefillBap(@Header("Authorization") token: String)
}