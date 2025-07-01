package com.nakersolutionid.nakersolutionid.features.settings

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.utils.ThemeState

data class SettingsUiState(
    val name: String = "",
    val newName: String = "",

    val username: String = "",
    val newUsername: String = "",

    val oldPassword: String = "",
    val newPassword: String = "",
    val confirmNewPassword: String = "",

    val isOldPasswordVisible: Boolean = false,
    val isNewPasswordVisible: Boolean = false,
    val isConfirmNewPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,

    val nameError: String? = null,
    val usernameError: String? = null,
    val oldPasswordError: String? = null,
    val newPasswordError: String? = null,
    val confirmNewPasswordError: String? = null,

    val changePasswordResult: Resource<String>? = null,
    val changeNameResult: Resource<String>? = null,
    val changeUsernameResult: Resource<String>? = null,
    val logoutResult: Resource<String>? = null,

    val showChangePasswordDialog: Boolean = false,
    val showChangeNameDialog: Boolean = false,
    val showChangeUsernameDialog: Boolean = false,

    val currentTheme: ThemeState = ThemeState.SYSTEM,
    val currentThemeName: String = "Default sistem"
)
