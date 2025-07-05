package com.nakersolutionid.nakersolutionid

import android.app.Application
import com.nakersolutionid.nakersolutionid.di.databaseModule
import com.nakersolutionid.nakersolutionid.di.networkModule
import com.nakersolutionid.nakersolutionid.di.preferenceModule
import com.nakersolutionid.nakersolutionid.di.repositoryModule
import com.nakersolutionid.nakersolutionid.di.useCaseModule
import com.nakersolutionid.nakersolutionid.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                listOf(
                    networkModule,
                    useCaseModule,
                    viewModelModule,
                    repositoryModule,
                    preferenceModule,
                    databaseModule
                )
            )
        }
    }
}