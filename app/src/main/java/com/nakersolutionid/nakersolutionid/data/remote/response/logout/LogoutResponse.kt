package com.nakersolutionid.nakersolutionid.data.remote.response.logout

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LogoutResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
) : Parcelable