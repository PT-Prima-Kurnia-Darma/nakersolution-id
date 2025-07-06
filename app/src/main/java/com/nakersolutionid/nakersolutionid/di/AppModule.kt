package com.nakersolutionid.nakersolutionid.di

import androidx.room.Room
import com.nakersolutionid.nakersolutionid.BuildConfig
import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.local.database.AppDatabase
import com.nakersolutionid.nakersolutionid.data.preference.SettingsPreference
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiServices
import com.nakersolutionid.nakersolutionid.data.repository.ReportRepository
import com.nakersolutionid.nakersolutionid.data.repository.SettingsRepository
import com.nakersolutionid.nakersolutionid.data.repository.UserRepository
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
import com.nakersolutionid.nakersolutionid.features.settings.SettingsViewModel
import com.nakersolutionid.nakersolutionid.features.signup.SignUpViewModel
import com.nakersolutionid.nakersolutionid.utils.AppExecutors
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<AppDatabase>().inspectionDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "Manga.db"
        ).fallbackToDestructiveMigration(false)
            .build()
    }
}

val useCaseModule = module {
    factory<UserUseCase> { UserInteraction(get()) }
    factory<SettingsUseCase> { SettingsInteraction(get()) }
    factory<ReportUseCase> { ReportInteraction(get()) }
}

val networkModule = module {
    single<OkHttpClient> {
        val hostname = BuildConfig.HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/a08oRcn8xp9bFmcfCL+hb1hj9XxBex51c+AjY7s107I=")
            .add(hostname, "sha256/vh78KSg1Ry4NaqGDV10w/cTb9VH3BQUZoCWNa93W/EY=")
            .add(hostname, "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=")
            .add(hostname, "sha256/ELwJ807jfDO1HcD6LeFP25hG3VP8ZO0iddJpVcWDIeA=")
            .add(hostname, "sha256/YPtHaftLw6/0vnc2BnNKGF54xiCA28WFcccjkA4ypCM=")
            .add(hostname, "sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=")
            .add(hostname, "sha256/S2G2H+GIZnJWzyR92vGtlICRS0DDkWORvtlQTBwdff0=")
            .build()
        val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single<ApiServices> {
        get<Retrofit>().create(ApiServices::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single<IUserRepository> { UserRepository(get(), get(), get(), get()) }
    single<ISettingsRepository> { SettingsRepository(get()) }
    single<IReportRepository> { ReportRepository(get(), get(), get()) }
}

val preferenceModule = module {
    single { UserPreference(androidContext()) }
    single { SettingsPreference(androidContext()) }
}

val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { ReportViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}