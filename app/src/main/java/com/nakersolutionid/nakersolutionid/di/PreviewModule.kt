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
import com.nakersolutionid.nakersolutionid.features.bap.BAPCreationViewModel
import com.nakersolutionid.nakersolutionid.features.bap.BAPViewModel
import com.nakersolutionid.nakersolutionid.features.history.HistoryViewModel
import com.nakersolutionid.nakersolutionid.features.home.HomeViewModel
import com.nakersolutionid.nakersolutionid.features.login.LoginViewModel
import com.nakersolutionid.nakersolutionid.features.report.ee.EEViewModel
import com.nakersolutionid.nakersolutionid.features.report.ilpp.ILPPViewModel
import com.nakersolutionid.nakersolutionid.features.report.ipk.IPKViewModel
import com.nakersolutionid.nakersolutionid.features.report.paa.PAAViewModel
import com.nakersolutionid.nakersolutionid.features.report.ptp.PTPViewModel
import com.nakersolutionid.nakersolutionid.features.report.pubt.PUBTViewModel
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
    viewModel { HistoryViewModel(get(), get()) }
    viewModel { BAPViewModel(get()) }
    viewModel { BAPCreationViewModel(get()) }
    viewModel { EEViewModel(get(), get()) }
    viewModel { PAAViewModel(get(), get()) }
    viewModel { ILPPViewModel(get(), get()) }
    viewModel { PTPViewModel(get(), get()) }
    viewModel { IPKViewModel(get(), get()) }
    viewModel { PUBTViewModel(get(), get()) }
}