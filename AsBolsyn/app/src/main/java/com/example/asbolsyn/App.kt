package com.example.asbolsyn

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.example.asbolsyn.di.modules
import com.example.asbolsyn.utils.network.BaseNetworkConfig
import com.example.asbolsyn.utils.network.NetworkKit

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        NetworkKit.initWithConfig(BaseNetworkConfig())

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(modules = modules)
        }
    }
}