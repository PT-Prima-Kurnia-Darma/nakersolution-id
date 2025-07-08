package com.nakersolutionid.nakersolutionid.di

import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import com.nakersolutionid.nakersolutionid.domain.repository.ISettingsRepository
import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportInteraction
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.domain.usecase.SettingsInteraction
import com.nakersolutionid.nakersolutionid.domain.usecase.SettingsUseCase
import com.nakersolutionid.nakersolutionid.domain.usecase.UserInteraction
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import com.nakersolutionid.nakersolutionid.features.history.HistoryViewModel
import com.nakersolutionid.nakersolutionid.features.home.HomeViewModel
import com.nakersolutionid.nakersolutionid.features.login.LoginViewModel
import com.nakersolutionid.nakersolutionid.features.report.ReportViewModel
import com.nakersolutionid.nakersolutionid.features.report.eskalator.EskalatorViewModel
import com.nakersolutionid.nakersolutionid.features.settings.SettingsViewModel
import com.nakersolutionid.nakersolutionid.features.signup.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val previewModule = module {
    single<IUserRepository> { FakeUserRepository() }
    single<ISettingsRepository> { FakeSettingsRepository() }
    single<IReportRepository> { FakeReportRepository() }
    factory<UserUseCase> { UserInteraction(get()) }
    factory<SettingsUseCase> { SettingsInteraction(get()) }
    factory<ReportUseCase> { ReportInteraction(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { ReportViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { EskalatorViewModel(get()) }
}