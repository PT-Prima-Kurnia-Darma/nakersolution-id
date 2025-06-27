package com.nakersolutionid.nakersolutionid.data.remote.response.login

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Data(

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("token")
	val token: String
) : Parcelable