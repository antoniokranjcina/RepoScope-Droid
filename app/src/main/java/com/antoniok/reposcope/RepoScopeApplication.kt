package com.antoniok.reposcope

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.antoniok.reposcope.core.data.di.dataModule
import com.antoniok.reposcope.core.datasource.local.di.localModule
import com.antoniok.reposcope.core.datasource.remote.di.networkModule
import com.antoniok.reposcope.feature.repositories.di.repositoriesModule
import com.antoniok.reposcope.sync.DEFAULT_ORG
import com.antoniok.reposcope.sync.SyncWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import java.util.concurrent.TimeUnit

private val modules = listOf(
    networkModule,
    localModule,
    dataModule,
    repositoriesModule
)

class RepoScopeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RepoScopeApplication)
            koin.loadModules(modules)
        }

        val inputData = workDataOf("org" to DEFAULT_ORG)

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            workerClass = SyncWorker::class.java,
            repeatInterval = 6,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            uniqueWorkName = "SyncWorker",
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = periodicWorkRequest
        )
    }
}
