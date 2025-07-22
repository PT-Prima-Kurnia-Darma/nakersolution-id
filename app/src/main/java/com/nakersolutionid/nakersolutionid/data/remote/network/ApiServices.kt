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
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateForkliftReportBody
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateForkliftReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.DeleteForkliftReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.GetForkliftReportsResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateGantryCraneReportBody
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateGantryCraneReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.DeleteGantryCraneReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.GetGantryCraneReportsResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateGondolaReportBody
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateGondolaReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.DeleteGondolaReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.GetGondolaReportsResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateMobileCraneReportBody
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateMobileCraneReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.DeleteMobileCraneReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.GetMobileCraneReportsResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateOverheadCraneReportBody
import com.nakersolutionid.nakersolutionid.data.remote.dto.CreateOverheadCraneReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.DeleteOverheadCraneReportResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.GetOverheadCraneReportsResponse
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
    @POST("elevatorEskalator/escalator/laporan")
    suspend fun createEscalatorReport(
        @Header("Authorization") token: String,
        @Body request: CreateEscalatorReportBody
    ) : CreateEscalatorReportResponse

    @GET("elevatorEskalator/escalator/laporan")
    suspend fun getEscalatorReports(
        @Header("Authorization") token: String
    ) : GetEscalatorReportsResponse

    @GET("elevatorEskalator/escalator/laporan/{id}")
    suspend fun getEscalatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : CreateEscalatorReportResponse

    @PUT("elevatorEskalator/escalator/laporan/{id}")
    suspend fun updateEscalatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: CreateEscalatorReportBody
    ) : CreateEscalatorReportResponse

    @DELETE("elevatorEskalator/escalator/laporan/{id}")
    suspend fun deleteEscalatorReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : DeleteEscalatorReportResponse
    @POST("elevatorEskalator/escalator/laporan/{id}/download")
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
    @POST("elevatorEskalator/forklift/laporan")
    suspend fun createForkliftReport(
        @Header("Authorization") token: String,
        @Body request: CreateForkliftReportBody
    ) : CreateForkliftReportResponse

    @GET("elevatorEskalator/forklift/laporan")
    suspend fun getForkliftReports(
        @Header("Authorization") token: String
    ) : GetForkliftReportsResponse

    @GET("elevatorEskalator/forklift/laporan/{id}")
    suspend fun getForkliftReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : CreateForkliftReportResponse

    @PUT("elevatorEskalator/forklift/laporan/{id}")
    suspend fun updateForkliftReport(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: CreateForkliftReportBody
    ) : CreateForkliftReportResponse

    @DELETE("elevatorEskalator/forklift/laporan/{id}")
    suspend fun deleteForkliftReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : DeleteForkliftReportResponse

    @POST("elevatorEskalator/forklift/laporan/{id}/download")
    @Streaming
    suspend fun downloadForkliftReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<ResponseBody>
    // endregion

    // region Gantry Crane
    @POST("elevatorEskalator/gantrycrane/laporan")
    suspend fun createGantryCraneReport(
        @Header("Authorization") token: String,
        @Body request: CreateGantryCraneReportBody
    ) : CreateGantryCraneReportResponse

    @GET("elevatorEskalator/gantrycrane/laporan")
    suspend fun getGantryCraneReports(
        @Header("Authorization") token: String
    ) : GetGantryCraneReportsResponse

    @GET("elevatorEskalator/gantrycrane/laporan/{id}")
    suspend fun getGantryCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : CreateGantryCraneReportResponse

    @PUT("elevatorEskalator/gantrycrane/laporan/{id}")
    suspend fun updateGantryCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: CreateGantryCraneReportBody
    ) : CreateGantryCraneReportResponse

    @DELETE("elevatorEskalator/gantrycrane/laporan/{id}")
    suspend fun deleteGantryCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : DeleteGantryCraneReportResponse

    @POST("elevatorEskalator/gantrycrane/laporan/{id}/download")
    @Streaming
    suspend fun downloadGantryCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<ResponseBody>
    // endregion

    // region Gondola
    @POST("elevatorEskalator/gondola/laporan")
    suspend fun createGondolaReport(
        @Header("Authorization") token: String,
        @Body request: CreateGondolaReportBody
    ) : CreateGondolaReportResponse

    @GET("elevatorEskalator/gondola/laporan")
    suspend fun getGondolaReports(
        @Header("Authorization") token: String
    ) : GetGondolaReportsResponse

    @GET("elevatorEskalator/gondola/laporan/{id}")
    suspend fun getGondolaReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : CreateGondolaReportResponse

    @PUT("elevatorEskalator/gondola/laporan/{id}")
    suspend fun updateGondolaReport(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: CreateGondolaReportBody
    ) : CreateGondolaReportResponse

    @DELETE("elevatorEskalator/gondola/laporan/{id}")
    suspend fun deleteGondolaReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : DeleteGondolaReportResponse

    @POST("elevatorEskalator/gondola/laporan/{id}/download")
    @Streaming
    suspend fun downloadGondolaReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<ResponseBody>
    // endregion

    // region Mobile Crane
    @POST("elevatorEskalator/mobilecrane/laporan")
    suspend fun createMobileCraneReport(
        @Header("Authorization") token: String,
        @Body request: CreateMobileCraneReportBody
    ) : CreateMobileCraneReportResponse

    @GET("elevatorEskalator/mobilecrane/laporan")
    suspend fun getMobileCraneReports(
        @Header("Authorization") token: String
    ) : GetMobileCraneReportsResponse

    @GET("elevatorEskalator/mobilecrane/laporan/{id}")
    suspend fun getMobileCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : CreateMobileCraneReportResponse

    @PUT("elevatorEskalator/mobilecrane/laporan/{id}")
    suspend fun updateMobileCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: CreateMobileCraneReportBody
    ) : CreateMobileCraneReportResponse

    @DELETE("elevatorEskalator/mobilecrane/laporan/{id}")
    suspend fun deleteMobileCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : DeleteMobileCraneReportResponse

    @POST("elevatorEskalator/mobilecrane/laporan/{id}/download")
    @Streaming
    suspend fun downloadMobileCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<ResponseBody>
    // endregion

    // region Overhead Crane
    @POST("elevatorEskalator/overheadcrane/laporan")
    suspend fun createOverheadCraneReport(
        @Header("Authorization") token: String,
        @Body request: CreateOverheadCraneReportBody
    ) : CreateOverheadCraneReportResponse

    @GET("elevatorEskalator/overheadcrane/laporan")
    suspend fun getOverheadCraneReports(
        @Header("Authorization") token: String
    ) : GetOverheadCraneReportsResponse

    @GET("elevatorEskalator/overheadcrane/laporan/{id}")
    suspend fun getOverheadCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : CreateOverheadCraneReportResponse

    @PUT("elevatorEskalator/overheadcrane/laporan/{id}")
    suspend fun updateOverheadCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: CreateOverheadCraneReportBody
    ) : CreateOverheadCraneReportResponse

    @DELETE("elevatorEskalator/overheadcrane/laporan/{id}")
    suspend fun deleteOverheadCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : DeleteOverheadCraneReportResponse

    @POST("elevatorEskalator/overheadcrane/laporan/{id}/download")
    @Streaming
    suspend fun downloadOverheadCraneReport(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Response<ResponseBody>
    // endregion

    // region Machine

    // endregion

    // region Motor Diesel

    // endregion

    // region General

    // endregion
}