package com.nakersolutionid.nakersolutionid.data.remote.dto.ml

import com.google.gson.annotations.SerializedName

data class MLData(
	@SerializedName("conclusion") val conclusion: String,
	@SerializedName("recommendation") val recommendation: List<String>
)

data class MLError(
	@SerializedName("error") val error: String,
	@SerializedName("message") val message: String
)