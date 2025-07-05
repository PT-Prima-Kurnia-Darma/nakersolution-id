package com.nakersolutionid.nakersolutionid.data.remote.response.sendreport

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Data(

	@field:SerializedName("laporan")
	val laporan: Laporan
) : Parcelable