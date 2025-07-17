package com.nakersolutionid.nakersolutionid.features.bap

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails

@Immutable
data class BAPCreationUiState(
    val isLoading: Boolean = false,
    // EE
    val elevatorResult: Resource<String>? = null,
    val eskalatorResult: Resource<String>? = null,
    // ILPP
    val electricResult: Resource<String>? = null,
    val lightningResult: Resource<String>? = null,
    // IPK
    val fireProtectionResult: Resource<String>? = null,
    // PAA
    val forkliftResult: Resource<String>? = null,
    val gantryCraneResult: Resource<String>? = null,
    val gondolaResult: Resource<String>? = null,
    val mobileCraneResult: Resource<String>? = null,
    val overheadCraneResult: Resource<String>? = null,
    // PTP
    val machineResult: Resource<String>? = null,
    val motorDieselResult: Resource<String>? = null,
    // PUBT
    val pubtResult: Resource<String>? = null
)
