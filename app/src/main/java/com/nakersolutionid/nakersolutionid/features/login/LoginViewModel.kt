package com.nakersolutionid.nakersolutionid.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    private val _loginState = MutableStateFlow<Resource<String>?>(null)
    val loginState: StateFlow<Resource<String>?> = _loginState

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            userUseCase.login(username, password).collect {
                _loginState.value = it
            }
        }
    }

    fun onStateHandled() {
        _loginState.value = null
    }
}