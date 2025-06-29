package com.nakersolutionid.nakersolutionid.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import com.nakersolutionid.nakersolutionid.features.signup.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username, usernameError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
    }

    fun onStateHandled() {
        _uiState.update { it.copy(loginResult = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun toggleLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun onLoginClicked() {
        if (validateInputs()) {
            loginUser()
        }
    }

    private fun validateInputs(): Boolean {
        val currentState = _uiState.value
        var usernameError: String? = null
        var passwordError: String? = null

        if (currentState.username.isBlank()) usernameError = "Nama pengguna diperlukan"
        if (currentState.password.isBlank()) passwordError = "Kata sandi diperlukan"

        _uiState.update {
            it.copy(
                usernameError = usernameError,
                passwordError = passwordError
            )
        }

        return listOfNotNull(usernameError, passwordError).isEmpty()
    }

    private fun loginUser() {
        val currentState = _uiState.value
        viewModelScope.launch {
            userUseCase.login(currentState.username, currentState.password)
                .collect { result ->
                    _uiState.update { it.copy(loginResult = result) }
                }
        }
    }
}