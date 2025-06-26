package com.nakersolutionid.nakersolutionid.data.remote.network

import com.nakersolutionid.nakersolutionid.data.remote.request.RegisterRequest
import com.nakersolutionid.nakersolutionid.data.remote.response.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ) : RegisterResponse
}