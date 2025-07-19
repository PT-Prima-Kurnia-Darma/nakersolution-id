package com.nakersolutionid.nakersolutionid.features.report.ptp

import com.nakersolutionid.nakersolutionid.data.Resource

data class PTPUiState(
    val isLoading: Boolean = false,
    val machineResult: Resource<String>? = null,
    val motorDieselResult: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null
)
