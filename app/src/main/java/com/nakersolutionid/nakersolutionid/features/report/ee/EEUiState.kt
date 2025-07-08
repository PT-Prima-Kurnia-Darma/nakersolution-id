package com.nakersolutionid.nakersolutionid.features.report.ee

import com.nakersolutionid.nakersolutionid.data.Resource

data class EEUiState(
    val isLoading: Boolean = false,
    val elevatorResult: Resource<String>? = null,
    val eskalatorResult: Resource<String>? = null
)
