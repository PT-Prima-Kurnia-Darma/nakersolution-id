package com.nakersolutionid.nakersolutionid.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private val _logoutState = MutableStateFlow<Resource<String>?>(null)
    val logoutState: StateFlow<Resource<String>?> = _logoutState

    fun logoutUser() {
        viewModelScope.launch {
            val token = userUseCase.getUserToken() ?: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
            userUseCase.logout(token).collect {
                _logoutState.value = it
            }
        }
    }

    suspend fun clearUser() {
        userUseCase.clearUser()
    }

    fun onStateHandled() {
        _logoutState.value = null
    }
}