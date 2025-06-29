package com.nakersolutionid.nakersolutionid.features.signup

import com.nakersolutionid.nakersolutionid.data.Resource

data class SignUpUiState(
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val nameError: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val registrationResult: Resource<String>? = null
)
