package com.nakersolutionid.nakersolutionid.data.remote.network

import com.google.gson.JsonElement
import com.nakersolutionid.nakersolutionid.data.remote.dto.ml.MLData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface MLApiServices {
    @POST("llm-generate")
    suspend fun getMLResult(
        @Header("X-Api-Key") secret: String,
        @Body payload: JsonElement
    ) : Response<MLData>
}