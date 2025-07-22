package com.nakersolutionid.nakersolutionid.data.remote.network

import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateElevatorReportBody
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateElevatorReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateEscalatorReportBody
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateEscalatorReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.DeleteElevatorReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.DeleteEscalatorReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.GetElevatorReportsResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.GetEscalatorReportsResponse
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

    // region Elevator
    // TODO: Change input because now it has recommendation and not only conclusion
    @POST("elevatorEskalator/elevator/laporan")
    suspend fun createElevatorReport(
        @Header("Authorization") token: String,
        @Body request: CreateElevatorReportBody
    ) : CreateElevatorReportResponse

    @GET("elevatorEskalator/elevator/laporan")
    suspend fun getElevatorReports(
        @Header("Authorization") token: String
    ) : GetElevatorReportsResponse

    @GET("elevatorEskalator/elevator/laporan/{id}")
    suspend fun getElevatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : CreateElevatorReportResponse

    @PUT("elevatorEskalator/elevator/laporan/{id}")
    suspend fun updateElevatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: CreateElevatorReportBody
    ) : CreateElevatorReportResponse

    @DELETE("elevatorEskalator/elevator/laporan/{id}")
    suspend fun deleteElevatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : DeleteElevatorReportResponse

    @POST("elevatorEskalator/elevator/laporan/{id}/download")
    @Streaming
    suspend fun downloadElevatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<ResponseBody>
    // endregion

    // region Escalator
    @POST("elevatorEskalator/elevator/laporan")
    suspend fun createEscalatorReport(
        @Header("Authorization") token: String,
        @Body request: CreateEscalatorReportBody
    ) : CreateEscalatorReportResponse

    @GET("elevatorEskalator/elevator/laporan")
    suspend fun getEscalatorReports(
        @Header("Authorization") token: String
    ) : GetEscalatorReportsResponse

    @GET("elevatorEskalator/elevator/laporan/{id}")
    suspend fun getEscalatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : CreateEscalatorReportResponse

    @PUT("elevatorEskalator/elevator/laporan/{id}")
    suspend fun updateEscalatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: CreateElevatorReportBody
    ) : CreateEscalatorReportResponse

    @DELETE("elevatorEskalator/elevator/laporan/{id}")
    suspend fun deleteEscalatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : DeleteEscalatorReportResponse
    @POST("elevatorEskalator/elevator/laporan/{id}/download")
    @Streaming
    suspend fun downloadEscalatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<ResponseBody>
    // endregion

    // region Electrical

    // endregion

    // region Lightning

    // endregion

    // region Fire Protection

    // endregion

    // region Forklift

    // endregion

    // region Gantry Crane

    // endregion

    // region Gondola

    // endregion

    // region Mobile Crane

    // endregion

    // region Overhead Crane

    // endregion

    // region Machine

    // endregion

    // region Motor Diesel

    // endregion

    // region General

    // endregion
}