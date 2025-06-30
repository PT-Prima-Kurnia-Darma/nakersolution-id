package com.nakersolutionid.nakersolutionid.features.settings

import com.nakersolutionid.nakersolutionid.data.Resource

data class SettingsUiState(
    val name: String = "",

    val username: String = "",

    val oldPassword: String = "",
    val newPassword: String = "",
    val confirmNewPassword: String = "",

    val isOldPasswordVisible: Boolean = false,
    val isNewPasswordVisible: Boolean = false,
    val isConfirmNewPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,

    val oldPasswordError: String? = null,
    val newPasswordError: String? = null,
    val confirmNewPasswordError: String? = null,

    val settingsResult: Resource<String>? = null,
    val logoutResult: Resource<String>? = null
)
