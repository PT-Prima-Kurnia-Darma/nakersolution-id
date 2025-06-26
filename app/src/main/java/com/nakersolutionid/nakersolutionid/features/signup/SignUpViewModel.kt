package com.nakersolutionid.nakersolutionid.features.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.User
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _registrationState = MutableStateFlow<Resource<User>?>(null)
    val registrationState: StateFlow<Resource<User>?> = _registrationState

    fun registerUser(name: String, username: String, password: String) {
        viewModelScope.launch {
            userUseCase.register(User(name, username, password)).collect {
                _registrationState.value = it
            }
        }
    }
}