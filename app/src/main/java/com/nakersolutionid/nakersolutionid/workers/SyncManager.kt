package com.nakersolutionid.nakersolutionid.workers

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import java.util.UUID

class SyncManager(
    private val workManager: WorkManager,
    private val reportRepository: IReportRepository,
    private val userPreference: UserPreference
) {

    private companion object {
        private const val SYNC_WORK_NAME = "data_sync_work"
        private const val SYNC_WORK_TAG = "data_sync"

        private const val DOWNLOAD_WORK_TAG = "download_work"
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

    suspend fun startDownload(id: Long): UUID? {
        val inspection = reportRepository.getInspection(id)
        inspection?.let { inspection ->
            val path = reportRepository.getApiPath(inspection.inspection.subInspectionType, inspection.inspection.documentType)
            val token = "Bearer ${userPreference.getUserToken() ?: ""}"
            val extraId = if (inspection.inspection.documentType == DocumentType.BAP) inspection.inspection.moreExtraId else inspection.inspection.extraId
            val data = workDataOf(
                "path" to path,
                "token" to token,
                "extraId" to extraId
            )

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = OneTimeWorkRequestBuilder<DownloadWorker>()
                .setConstraints(constraints)
                .setInputData(data)
                .addTag(DOWNLOAD_WORK_TAG)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    java.util.concurrent.TimeUnit.MILLISECONDS
                )
                .build()

            workManager.enqueue(request)

            return request.id
        }
        return null
    }

    /*fun startSyncUpdate() {
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
    }*/

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

    /*fun setupPeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncReportWorker>(
            6, java.util.concurrent.TimeUnit.HOURS // Sync every 6 hours
        )
            .setConstraints(constraints)
            .addTag(SYNC_WORK_TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "periodic_$SYNC_WORK_NAME",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncRequest
        )
    }*/

    fun getWorkInfoById(uuid: UUID) = workManager.getWorkInfoByIdFlow(uuid)

    fun getSyncStatus() = workManager.getWorkInfosForUniqueWorkLiveData(SYNC_WORK_NAME)

    fun cancelSync() {
        workManager.cancelUniqueWork(SYNC_WORK_NAME)
    }

    fun cancelAllSyncWork() {
        workManager.cancelAllWorkByTag(SYNC_WORK_TAG)
    }
}