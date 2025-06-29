package com.nakersolutionid.nakersolutionid.features.home

import com.nakersolutionid.nakersolutionid.data.Resource

data class HomeUiState(
    val isLoading: Boolean = false,
    val logoutResult: Resource<String>? = null
)
