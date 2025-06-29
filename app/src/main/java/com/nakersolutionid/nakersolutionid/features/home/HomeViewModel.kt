package com.nakersolutionid.nakersolutionid.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onStateHandled() {
        _uiState.update { it.copy(logoutResult = null) }
    }

    fun toggleLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun onLoginClicked() {
        logoutUser()
    }

    fun logoutUser() {
        val currentState = _uiState.value
        viewModelScope.launch {
            userUseCase.logout().collect { result ->
                _uiState.update { it.copy(logoutResult = result) }
            }
        }
    }
}