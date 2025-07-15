package com.nakersolutionid.nakersolutionid.features.report.pubt

import com.nakersolutionid.nakersolutionid.data.Resource

data class PUBTUiState(
    val isLoading: Boolean = false,
    val generalResult: Resource<String>? = null
)
