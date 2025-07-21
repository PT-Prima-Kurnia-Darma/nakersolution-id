package com.nakersolutionid.nakersolutionid

import android.app.Application
import com.nakersolutionid.nakersolutionid.di.databaseModule
import com.nakersolutionid.nakersolutionid.di.networkModule
import com.nakersolutionid.nakersolutionid.di.preferenceModule
import com.nakersolutionid.nakersolutionid.di.repositoryModule
import com.nakersolutionid.nakersolutionid.di.useCaseModule
import com.nakersolutionid.nakersolutionid.di.viewModelModule
import com.nakersolutionid.nakersolutionid.di.workerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@MyApp)
            workManagerFactory()
            modules(
                listOf(
                    networkModule,
                    useCaseModule,
                    viewModelModule,
                    repositoryModule,
                    preferenceModule,
                    databaseModule,
                    workerModule
                )
            )
        }
    }
}