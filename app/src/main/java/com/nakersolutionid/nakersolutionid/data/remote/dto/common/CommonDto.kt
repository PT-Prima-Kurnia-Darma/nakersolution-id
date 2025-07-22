package com.nakersolutionid.nakersolutionid.data.remote.dto.common

import com.google.gson.annotations.SerializedName

// Base API Response DTO for all API calls
data class BaseApiResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T? // Kept nullable because delete_report.json does not contain a 'data' field
)

// Generic DTO for result and status pairs
data class ResultStatus(
    @SerializedName("result") val result: String,
    @SerializedName("status") val status: Boolean
)

// DTO for error responses (e.g., 4xx, 5xx)
data class ErrorResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

/*
interface ElevatorApiService {
    // Report endpoints
    @POST("reports/elevator")
    suspend fun createElevatorReport(@Body report: ElevatorReportDto): Response<BaseResponse<SingleReportDataDto>>

    @PUT("reports/elevator/{id}")
    suspend fun updateElevatorReport(@Path("id") id: String, @Body report: ElevatorReportDto): Response<BaseResponse<SingleReportDataDto>>

    @GET("reports/elevator/{id}")
    suspend fun getElevatorReport(@Path("id") id: String): Response<BaseResponse<SingleReportDataDto>>

    @GET("reports/elevator")
    suspend fun getAllElevatorReports(): Response<BaseResponse<ListReportDataDto>>

    @DELETE("reports/elevator/{id}")
    suspend fun deleteElevatorReport(@Path("id") id: String): Response<BaseResponse<Any>> // Or a specific DTO if delete returns a structured message

    @GET("reports/elevator/{id}/download")
    suspend fun downloadElevatorReport(@Path("id") id: String): Response<ResponseBody>

    // BAP Report endpoints
    @POST("bap/elevator") // Example endpoint
    suspend fun createBapReport(@Body bapReport: BapReportDto): Response<BaseResponse<SingleBapDataDto>>

    @PUT("bap/elevator/{id}") // Example endpoint
    suspend fun updateBapReport(@Path("id") id: String, @Body bapReport: BapReportDto): Response<BaseResponse<SingleBapDataDto>>

    @GET("bap/elevator/{id}") // Example endpoint
    suspend fun getBapReport(@Path("id") id: String): Response<BaseResponse<SingleBapDataDto>>

    @GET("bap/elevator") // Example endpoint
    suspend fun getAllBapReports(): Response<BaseResponse<ListBapDataDto>>

    @DELETE("bap/elevator/{id}") // Example endpoint
    suspend fun deleteBapReport(@Path("id") id: String): Response<BaseResponse<Any>> // Delete typically returns no data, just status/message

    class ReportRepository(private val apiService: ElevatorApiService, private val gson: Gson) {

    suspend fun downloadReport(reportId: String, outputFile: File): Result<File> {
        return withContext(Dispatchers.IO) { // Perform IO operations on IO dispatcher
            try {
                val response = apiService.downloadElevatorReport(reportId)

                if (response.isSuccessful) {
                    // Success: Raw bytes are in response.body()
                    response.body()?.let { body ->
                        val inputStream = body.byteStream()
                        val outputStream = FileOutputStream(outputFile)

                        inputStream.use { input ->
                            outputStream.use { output ->
                                input.copyTo(output)
                            }
                        }
                        Result.success(outputFile)
                    } ?: Result.failure(Exception("Download body is null"))
                } else {
                    // Error: Parse the errorBody() as JSON
                    val errorBodyString = response.errorBody()?.string()
                    if (!errorBodyString.isNullOrEmpty()) {
                        try {
                            val errorResponse = gson.fromJson(errorBodyString, ErrorResponseDto::class.java)
                            Result.failure(Exception("Error: ${errorResponse.message} (Status: ${errorResponse.status})"))
                        } catch (e: Exception) {
                            Result.failure(Exception("Failed to parse error response: $errorBodyString", e))
                        }
                    } else {
                        Result.failure(Exception("Unknown error: ${response.code()} ${response.message()}"))
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}*/
