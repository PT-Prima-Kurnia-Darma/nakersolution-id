package com.nakersolutionid.nakersolutionid.features.report.paa

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.Resource

@Immutable
data class PAAUiState(
    val isLoading: Boolean = false,
    val forkliftResult: Resource<String>? = null,
    val gantryCraneResult: Resource<String>? = null,
    val gondolaResult: Resource<String>? = null,
    val mobileCraneResult: Resource<String>? = null,
    val overheadCraneResult: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null
)
