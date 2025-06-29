package com.nakersolutionid.nakersolutionid.features.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    // 2. Expose event handlers for the UI to call
    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username, usernameError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null) }
    }

    fun onStateHandled() {
        _uiState.update { it.copy(registrationResult = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
    }

    fun toggleLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun onSignUpClicked() {
        if (validateInputs()) {
            registerUser()
        }
    }

    // 3. Keep validation and data logic private
    private fun validateInputs(): Boolean {
        val currentState = _uiState.value
        var nameError: String? = null
        var usernameError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null

        if (currentState.name.isBlank()) nameError = "Nama lengkap diperlukan"
        if (currentState.username.isBlank()) usernameError = "Nama pengguna diperlukan"
        if (currentState.password.isBlank()) passwordError = "Kata sandi diperlukan"
        if (currentState.confirmPassword.isBlank()) confirmPasswordError = "Konfirmasi kata sandi diperlukan"
        if (currentState.password != currentState.confirmPassword) {
            confirmPasswordError = "Kata sandi tidak cocok"
        }

        _uiState.update {
            it.copy(
                nameError = nameError,
                usernameError = usernameError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
        }

        return listOfNotNull(nameError, usernameError, passwordError, confirmPasswordError).isEmpty()
    }

    private fun registerUser() {
        val currentState = _uiState.value
        viewModelScope.launch {
            userUseCase.register(currentState.name, currentState.username, currentState.password)
                .collect { result ->
                    _uiState.update { it.copy(registrationResult = result) }
                }
        }
    }
}