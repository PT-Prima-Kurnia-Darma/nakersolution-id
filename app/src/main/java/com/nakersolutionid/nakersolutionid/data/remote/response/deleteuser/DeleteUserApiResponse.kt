package com.nakersolutionid.nakersolutionid.data.remote.response.deleteuser

import com.google.gson.annotations.SerializedName

data class DeleteUserApiResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
