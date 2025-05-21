package com.antoniok.reposcope

import android.app.Application
import com.antoniok.reposcope.core.datasource.local.di.localModule
import com.antoniok.reposcope.core.datasource.remote.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

private val modules = listOf(
    networkModule,
    localModule
)

class RepoScopeApplication  : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RepoScopeApplication)
            koin.loadModules(modules)
        }
    }
}
