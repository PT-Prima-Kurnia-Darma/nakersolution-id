package com.nakersolutionid.nakersolutionid.di

import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiServices
import com.nakersolutionid.nakersolutionid.data.repository.UserRepository
import com.nakersolutionid.nakersolutionid.domain.repository.IUserRepository
import com.nakersolutionid.nakersolutionid.domain.usecase.UserInteraction
import com.nakersolutionid.nakersolutionid.domain.usecase.UserUseCase
import com.nakersolutionid.nakersolutionid.features.signup.SignUpViewModel
import com.nakersolutionid.nakersolutionid.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val useCaseModule = module {
    factory<UserUseCase> { UserInteraction(get()) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.216.171:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiServices::class.java)
    }
}

val repositoryModule = module {
    single<IUserRepository> { UserRepository(get(), get(), get()) }
    single { LocalDataSource() }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
}

val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
}