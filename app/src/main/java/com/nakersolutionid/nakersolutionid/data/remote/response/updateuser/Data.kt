package com.nakersolutionid.nakersolutionid.data.remote.response.updateuser

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Data(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("username")
	val username: String
) : Parcelable