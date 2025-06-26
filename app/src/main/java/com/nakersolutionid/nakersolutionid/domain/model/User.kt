package com.nakersolutionid.nakersolutionid.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String,
    var username: String,
    val password: String
) : Parcelable