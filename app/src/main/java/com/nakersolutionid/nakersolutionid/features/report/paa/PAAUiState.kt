package com.nakersolutionid.nakersolutionid.features.report.paa

import com.nakersolutionid.nakersolutionid.data.Resource

data class PAAUiState(
    val isLoading: Boolean = false,
    val forkliftResult: Resource<String>? = null,
    val gantryCraneResult: Resource<String>? = null,
    val gondolaResult: Resource<String>? = null,
    val mobileCraneResult: Resource<String>? = null,
    val overheadCraneResult: Resource<String>? = null
)
