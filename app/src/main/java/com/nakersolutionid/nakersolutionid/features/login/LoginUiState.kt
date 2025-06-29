package com.nakersolutionid.nakersolutionid.features.login

import com.nakersolutionid.nakersolutionid.data.Resource

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val loginResult: Resource<String>? = null
)