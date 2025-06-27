package com.nakersolutionid.nakersolutionid.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val username: String,
    val token: String
) : Parcelable