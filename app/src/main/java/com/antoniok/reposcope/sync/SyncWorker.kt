package com.antoniok.reposcope.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.antoniok.reposcope.core.data.repository.RepoScopeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val DEFAULT_ORG = "square"

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val weatherRepository: RepoScopeRepository by inject()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val result = weatherRepository.sync(org = DEFAULT_ORG)
            if (result) {
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
