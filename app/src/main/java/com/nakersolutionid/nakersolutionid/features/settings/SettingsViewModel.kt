package com.nakersolutionid.nakersolutionid.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Start observing the user data as soon as the ViewModel is created
        userUseCase.currentUser
            .onEach { user ->
                // Whenever the user data changes (login, logout, profile update),
                // this block will be executed.
                _uiState.update { currentState ->
                    currentState.copy(
                        name = user.name,
                        username = user.username
                    )
                }
            }
            .launchIn(viewModelScope) // Launch the collection in the viewModelScope
    }

    fun onOldPasswordChange(password: String) {
        _uiState.update { it.copy(oldPassword = password, oldPasswordError = null) }
    }

    fun onNewPasswordChange(password: String) {
        _uiState.update { it.copy(newPassword = password, newPasswordError = null) }
    }

    fun onConfirmNewPasswordChange(password: String) {
        _uiState.update { it.copy(confirmNewPassword = password, confirmNewPasswordError = null) }
    }

    fun toggleOldPasswordVisibility() {
        _uiState.update { it.copy(isOldPasswordVisible = !it.isOldPasswordVisible) }
    }

    fun toggleNewPasswordVisibility() {
        _uiState.update { it.copy(isNewPasswordVisible = !it.isNewPasswordVisible) }
    }

    fun toggleConfirmNewPasswordVisibility() {
        _uiState.update { it.copy(isConfirmNewPasswordVisible = !it.isConfirmNewPasswordVisible) }
    }

    fun onLogoutStateHandle() {
        _uiState.update { it.copy(logoutResult = null) }
    }

    fun toggleLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun toggleChangePasswordDialog() {
        _uiState.update { it.copy(showChangePasswordDialog = !it.showChangePasswordDialog) }
    }

    fun onLogoutClicked() {
        logoutUser()
    }

    private fun logoutUser() {
        viewModelScope.launch {
            userUseCase.logout().collect { result ->
                _uiState.update { it.copy(logoutResult = result) }
            }
        }
    }
}