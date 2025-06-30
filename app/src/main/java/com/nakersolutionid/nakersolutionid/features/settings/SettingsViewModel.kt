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

    fun onNameChange(name: String) {
        _uiState.update { it.copy(newName = name, nameError = null) }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(newUsername = username, usernameError = null) }
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

    fun onChangePasswordStateHandleSuccess() {
            _uiState.update { it.copy(
                changePasswordResult = null,
                oldPassword = "",
                newPassword = "",
                confirmNewPassword = "",
                oldPasswordError = null,
                newPasswordError = null,
            )
        }
    }

    fun onChangePasswordStateHandleFailed() {
        _uiState.update { it.copy(changePasswordResult = null) }
    }

    fun onChangeNameStateHandleSuccess() {
            _uiState.update { it.copy(
                changeNameResult = null,
                newName = "",
                nameError = null
            )
        }
    }

    fun onChangeNameStateHandleFailed() {
        _uiState.update { it.copy(changeNameResult = null) }
    }

    fun onChangeUsernameStateHandleSuccess() {
        _uiState.update {
            it.copy(
                changeUsernameResult = null,
                newUsername = "",
                usernameError = null
            )
        }
    }

    fun onChangeUsernameStateHandleFailed() {
        _uiState.update { it.copy(changeUsernameResult = null) }
    }

    fun toggleLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun toggleChangePasswordDialog() {
        _uiState.update { it.copy(showChangePasswordDialog = !it.showChangePasswordDialog) }
    }

    fun toggleChangeNameDialog() {
        _uiState.update { it.copy(showChangeNameDialog = !it.showChangeNameDialog) }
    }

    fun toggleChangeUsernameDialog() {
        _uiState.update { it.copy(showChangeUsernameDialog = !it.showChangeUsernameDialog) }
    }

    fun onLogoutClicked() {
        logoutUser()
    }

    fun onPasswordChangeSave() {
        if (validateChangePasswordInputs()) {
            changePasswordUser()
        }
    }

    fun onNameChangeSave() {
        if (validateNameInputs()) {
            changeNameUser()
        }
    }

    fun onUsernameChangeSave() {
        if (validateUsernameInputs()) {
            changeUsernameUser()
        }
    }

    private fun validateNameInputs(): Boolean {
        val currentState = _uiState.value
        var nameError: String? = null
        if (currentState.name.isBlank()) nameError = "Nama lengkap diperlukan"
        _uiState.update { it.copy(nameError = nameError) }
        return nameError == null
    }

    private fun validateUsernameInputs(): Boolean {
        val currentState = _uiState.value
        var usernameError: String? = null
        if (currentState.username.isBlank()) usernameError = "Nama pengguna diperlukan"
        _uiState.update { it.copy(usernameError = usernameError) }
        return usernameError == null
    }

    private fun validateChangePasswordInputs(): Boolean {
        val currentState = _uiState.value
        var oldPasswordError: String? = null
        var newPasswordError: String? = null
        var confirmNewPasswordError: String? = null

        if (currentState.oldPassword.isBlank()) oldPasswordError = "Kata sandi lama diperlukan"
        if (currentState.newPassword.isBlank()) newPasswordError = "Kata sandi baru diperlukan"
        if (currentState.confirmNewPassword.isBlank()) confirmNewPasswordError = "Konfirmasi kata sandi diperlukan"
        if (currentState.newPassword != currentState.confirmNewPassword) {
            confirmNewPasswordError = "Kata sandi baru tidak cocok"
        }

        _uiState.update {
            it.copy(
                oldPasswordError = oldPasswordError,
                newPasswordError = newPasswordError,
                confirmNewPasswordError = confirmNewPasswordError
            )
        }

        return listOfNotNull(oldPasswordError, newPasswordError, confirmNewPasswordError).isEmpty()
    }

    private fun logoutUser() {
        viewModelScope.launch {
            userUseCase.logout().collect { result ->
                _uiState.update { it.copy(logoutResult = result) }
            }
        }
    }

    private fun changePasswordUser() {
        val currentState = _uiState.value
        viewModelScope.launch {
            userUseCase.updateUser(
                null,
                null,
                oldPassword = currentState.oldPassword,
                newPassword = currentState.newPassword
            ).collect { result ->
                _uiState.update { it.copy(changePasswordResult = result) }
            }
        }
    }

    private fun changeNameUser() {
        val currentState = _uiState.value
        viewModelScope.launch {
            userUseCase.updateUser(currentState.newName, null, null, null).collect { result ->
                _uiState.update { it.copy(changeNameResult = result) }
            }
        }
    }

    private fun changeUsernameUser() {
        val currentState = _uiState.value
        viewModelScope.launch {
            userUseCase.updateUser(null, currentState.newUsername, null, null).collect { result ->
                _uiState.update { it.copy(changeUsernameResult = result) }
            }
        }
    }

    fun clearUser() {
        viewModelScope.launch {
            userUseCase.clearUser()
        }
    }
}