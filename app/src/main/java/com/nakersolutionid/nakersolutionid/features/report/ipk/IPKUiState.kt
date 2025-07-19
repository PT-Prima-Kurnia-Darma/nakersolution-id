package com.nakersolutionid.nakersolutionid.features.report.ipk

import com.nakersolutionid.nakersolutionid.data.Resource

data class IPKUiState(
    val isLoading: Boolean = false,
    val fireProtectionResult: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null
)
