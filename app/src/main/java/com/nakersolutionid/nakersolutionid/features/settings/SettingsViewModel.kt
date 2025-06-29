package com.nakersolutionid.nakersolutionid.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    fun onLogoutStateHandle() {
        _uiState.update { it.copy(logoutResult = null) }
    }

    fun toggleLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
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