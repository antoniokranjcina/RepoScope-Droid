package com.antoniok.reposcope

import android.app.Application
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.antoniok.reposcope.core.data.di.dataModule
import com.antoniok.reposcope.core.datasource.local.di.localModule
import com.antoniok.reposcope.core.datasource.remote.di.networkModule
import com.antoniok.reposcope.feature.repositories.di.repositoriesModule
import com.antoniok.reposcope.sync.SyncWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

private val modules = listOf(
    networkModule,
    localModule,
    dataModule,
    repositoriesModule
)

class RepoScopeApplication  : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RepoScopeApplication)
            koin.loadModules(modules)
        }

        val workRequest = OneTimeWorkRequest.Builder(SyncWorker::class.java).build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}
