package com.nakersolutionid.nakersolutionid.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nakersolutionid.nakersolutionid.BuildConfig
import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.local.database.AppDatabase
import com.nakersolutionid.nakersolutionid.data.preference.SettingsPreference
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiServices
import com.nakersolutionid.nakersolutionid.data.remote.network.MLApiServices
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
import com.nakersolutionid.nakersolutionid.utils.AppExecutors
import com.nakersolutionid.nakersolutionid.workers.DownloadWorker
import com.nakersolutionid.nakersolutionid.workers.SyncManager
import com.nakersolutionid.nakersolutionid.workers.SyncReportWorker
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<AppDatabase>().inspectionDao() }
    factory { get<AppDatabase>().remoteKeyDao() }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app.db")
            .fallbackToDestructiveMigration(true)
            .build()
    }
}

val workerModule = module {
    single { WorkManager.getInstance(androidContext()) }
    single { SyncManager(get(), get(), get()) }
    worker { SyncReportWorker(get(), get(), get()) }
    worker { DownloadWorker(get(), get(), get()) }
}

val useCaseModule = module {
    factory<UserUseCase> { UserInteraction(get()) }
    factory<SettingsUseCase> { SettingsInteraction(get()) }
    factory<ReportUseCase> { ReportInteraction(get()) }
}

val networkModule = module {
    single<Gson> { GsonBuilder().create() }

    // region Main
    single<OkHttpClient> {
        val hostname = BuildConfig.HOSTNAME
        val mlHostName = BuildConfig.ML_HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/qpKwP/9PATGLiBmrkRPGfU1stGY5FlEWC8siPM+Cxug=")
            .add(hostname, "sha256/YPtHaftLw6/0vnc2BnNKGF54xiCA28WFcccjkA4ypCM=")
            .add(hostname, "sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=")
            .add(mlHostName, "sha256/47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=")
            .add(mlHostName, "sha256/YPtHaftLw6/0vnc2BnNKGF54xiCA28WFcccjkA4ypCM=")
            .add(mlHostName, "sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=")
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
    // endregion

    // region ML
    single<Retrofit>(named("MLRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.ML_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single<MLApiServices> {
        get<Retrofit>(named("MLRetrofit")).create(MLApiServices::class.java)
    }
    // endregion
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get(), get()) }
    single { AppExecutors() }
    single<IUserRepository> { UserRepository(get(), get(), get(), get()) }
    single<ISettingsRepository> { SettingsRepository(get()) }
    single<IReportRepository> { ReportRepository(get(), get(), get(), get(), get()) }
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
    viewModel { HistoryViewModel(get(), get()) }
    viewModel { BAPViewModel(get()) }
    viewModel { BAPCreationViewModel(get(), get()) }
    viewModel { EEViewModel(get(), get()) }
    viewModel { PAAViewModel(get(), get()) }
    viewModel { ILPPViewModel(get(), get()) }
    viewModel { PTPViewModel(get(), get()) }
    viewModel { IPKViewModel(get(), get()) }
    viewModel { PUBTViewModel(get(), get()) }
}