package com.nakersolutionid.nakersolutionid.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: Int,
    var firstName: String,
    var lastName: String,
    val username: String
) : Parcelable