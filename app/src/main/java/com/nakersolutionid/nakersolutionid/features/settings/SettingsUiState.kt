package com.nakersolutionid.nakersolutionid.features.settings

import com.nakersolutionid.nakersolutionid.data.Resource

data class SettingsUiState(
    val name: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
    val settingsResult: Resource<String>? = null,
    val logoutResult: Resource<String>? = null
)
