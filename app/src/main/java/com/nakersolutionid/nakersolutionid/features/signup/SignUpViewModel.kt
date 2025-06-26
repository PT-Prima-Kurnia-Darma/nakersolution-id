package com.nakersolutionid.nakersolutionid.features.signup

import androidx.lifecycle.ViewModel
import com.nakersolutionid.nakersolutionid.domain.model.User
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase

class SignUpViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun setFavoriteManga(name: String, username: String, password: String) {
        val user = User(name, username, password)
        userUseCase.register(user)
    }
}