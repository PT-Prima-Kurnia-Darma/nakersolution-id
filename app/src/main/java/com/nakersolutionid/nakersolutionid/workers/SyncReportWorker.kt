package com.nakersolutionid.nakersolutionid.workers

import android.app.Notification
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncReportWorker(
    context: Context,
    params: WorkerParameters,
    private val reportRepository: IReportRepository
) : CoroutineWorker(context, params) {
    private val NOTIFICATION_ID = 1

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Be explicit about the type here as well
            ForegroundInfo(NOTIFICATION_ID, createNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            ForegroundInfo(NOTIFICATION_ID, createNotification())
        }
        /*return ForegroundInfo(
            1, createNotification(), Service
        )*/
    }

    override suspend fun doWork(): Result {
        // Only use foreground service for long-running tasks
        // For quick syncs, you can remove this line
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                setForeground(getForegroundInfo())
            } catch (e: Exception) {
                println("Warning: Could not set foreground service: ${e.message}")
                return Result.failure(workDataOf(
                    "message" to e.message
                ))
            }
        }*/

        return withContext(Dispatchers.IO) {
            val isSuccess = reportRepository.syncInspection()
            if (isSuccess) {
                Result.success()
            } else {
                Result.failure(workDataOf(
                    "message" to "Sync failed"
                ))
            }
        }
    }

    private fun createNotification() : Notification {
        return NotificationCompat.Builder(
            applicationContext,
            "sync_channel"
        )
            .setContentTitle("Syncing Data")
            .setContentText("Synchronizing your data with server...")
            .setSmallIcon(android.R.drawable.stat_sys_upload)
            .setOngoing(true)
            .build()
    }
}