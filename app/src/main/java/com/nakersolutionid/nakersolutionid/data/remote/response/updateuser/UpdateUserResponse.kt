package com.nakersolutionid.nakersolutionid.data.remote.response.updateuser

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdateUserResponse(

	@field:SerializedName("data")
	val data: Data?,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
) : Parcelable