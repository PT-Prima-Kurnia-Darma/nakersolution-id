package com.nakersolutionid.nakersolutionid.di

import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import com.nakersolutionid.nakersolutionid.domain.usecase.UserInteraction
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import com.nakersolutionid.nakersolutionid.features.home.HomeViewModel
import com.nakersolutionid.nakersolutionid.features.login.LoginViewModel
import com.nakersolutionid.nakersolutionid.features.settings.SettingsViewModel
import com.nakersolutionid.nakersolutionid.features.signup.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val previewModule = module {
    single<IUserRepository> { FakeUserRepository() }
    factory<UserUseCase> { UserInteraction(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}