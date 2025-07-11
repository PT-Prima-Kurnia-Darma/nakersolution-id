package com.nakersolutionid.nakersolutionid.features.report.ee

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.Resource

@Immutable
data class EEUiState(
    val isLoading: Boolean = false,
    val elevatorResult: Resource<String>? = null,
    val eskalatorResult: Resource<String>? = null
)
