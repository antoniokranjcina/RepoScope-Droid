package com.antoniok.reposcope.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val DEFAULT_ORG = "square"

internal class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val repoScopeRepository: RepoScopeRepository by inject()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val org = inputData.getString("org") ?: DEFAULT_ORG
        try {
            val success = repoScopeRepository.sync(org = org)
            if (success) Result.success() else Result.retry()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
