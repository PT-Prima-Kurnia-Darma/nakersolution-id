package com.nakersolutionid.nakersolutionid.workers

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest

class SyncManager(
    private val workManager: WorkManager
) {

    companion object {
        private const val SYNC_WORK_UPDATE_NAME = "data_sync_update_work"
        private const val SYNC_WORK_NAME = "data_sync_work"
        private const val SYNC_WORK_TAG = "data_sync"

    }

    fun startSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncReportWorker>()
            .setConstraints(constraints)
            .addTag(SYNC_WORK_TAG)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                java.util.concurrent.TimeUnit.MILLISECONDS
            )
            .build()

        // Use REPLACE to ensure only one sync runs at a time
        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }

    fun startSyncUpdate() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncUpdateReportWorker>()
            .setConstraints(constraints)
            .addTag(SYNC_WORK_TAG)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                java.util.concurrent.TimeUnit.MILLISECONDS
            )
            .build()

        // Use REPLACE to ensure only one sync runs at a time
        workManager.enqueueUniqueWork(
            SYNC_WORK_UPDATE_NAME,
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }

    /*fun startSyncOnAppStart() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncReportWorker>()
            .setConstraints(constraints)
            .addTag(SYNC_WORK_TAG)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                java.util.concurrent.TimeUnit.MILLISECONDS
            )
            .build()

        // Use REPLACE to ensure only one sync runs at a time
        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }*/

    fun setupPeriodicSync() {
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresBatteryNotLow(true)
//            .build()
//
//        val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncReportWorker>(
//            6, java.util.concurrent.TimeUnit.HOURS // Sync every 6 hours
//        )
//            .setConstraints(constraints)
//            .addTag(SYNC_WORK_TAG)
//            .build()
//
//        workManager.enqueueUniquePeriodicWork(
//            "periodic_$SYNC_WORK_NAME",
//            ExistingPeriodicWorkPolicy.KEEP,
//            periodicSyncRequest
//        )
    }

    fun getSyncStatus() = workManager.getWorkInfosForUniqueWorkLiveData(SYNC_WORK_NAME)

    fun cancelSync() {
        workManager.cancelUniqueWork(SYNC_WORK_NAME)
    }

    fun cancelAllSyncWork() {
        workManager.cancelAllWorkByTag(SYNC_WORK_TAG)
    }
}