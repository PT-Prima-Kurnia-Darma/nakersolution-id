package com.nakersolutionid.nakersolutionid.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ErrorResponse
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DownloadWorker(
    context: Context,
    params: WorkerParameters,
    private val apiServices: ApiServices,
) : CoroutineWorker(context, params) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        val path = inputData.getString("path") ?: return Result.failure()
        val token = inputData.getString("token") ?: return Result.failure()
        val extraId = inputData.getString("extraId") ?: return Result.failure()

        setForeground(createForegroundInfo("Downloading in progress..."))

        return downloadFile(token, path, extraId)
    }

    private suspend fun downloadFile(token: String, path: String, extraId: String): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiServices.downloadDocument(token, path, extraId)

                if (response.isSuccessful) {
                    val body = response.body() ?: return@withContext Result.failure()
                    val header = response.headers() ?: return@withContext Result.failure()

                    // val fileName = header["Content-Disposition"]?.substringAfterLast("filename=") ?: "report_${getTimestampForFilename()}.docx"
                    var receivedFileName = header["Content-Disposition"]?.substringAfterLast("filename=")
                    receivedFileName = receivedFileName?.trim('"')
                    val fileName = receivedFileName ?: "report_${getTimestampForFilename()}.docx"
                    val contentType = body.contentType()?.toString()

                    val outputDir = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    val outputFile = File(outputDir, fileName)

                    // 3. Proses streaming ResponseBody
                    var inputStream: InputStream? = null
                    var outputStream: FileOutputStream? = null
                    try {
                        inputStream = body.byteStream()
                        outputStream = FileOutputStream(outputFile)
                        val fileReader = ByteArray(4096)

                        val fileSize = body.contentLength()
                        var fileSizeDownloaded: Long = 0
                        var read: Int

                        while (inputStream.read(fileReader).also { read = it } != -1) {
                            outputStream.write(fileReader, 0, read)
                            fileSizeDownloaded += read.toLong()

                            // Hitung dan kirim progress
                            val progress = (fileSizeDownloaded * 100 / fileSize).toInt()
                            val progressText = "Downloaded ${formatBytes(fileSizeDownloaded)} of ${formatBytes(fileSize)} ($progress%)"
                            setForeground(createForegroundInfo(progressText))
                            setProgress(workDataOf("DOWNLOAD_PROGRESS" to progress))
                        }

                        outputStream.flush()

                        // Kirim path file jika berhasil
                        val outputData = workDataOf(
                            "KEY_FILE_PATH" to outputFile.absolutePath,
                            "CONTENT_TYPE" to contentType,
                            "FILE_NAME" to fileName
                        )

                        setForeground(createForegroundInfo("Download completed"))
                        Result.success(outputData)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Result.failure(workDataOf(
                            "error" to "Download failed: ${e.message}"
                        ))
                    } finally {
                        inputStream?.close()
                        outputStream?.close()
                    }
                } else {
                    val errorBody = response.body()?.toString()
                    Log.e("DownloadWorker", "Download failed: $errorBody")
                    val remote = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    Result.failure(workDataOf(
                        "error" to "Download failed: ${remote.message}"
                    ))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(workDataOf(
                    "error" to "Download failed: ${e.message}"
                ))
            }
        }
    }

    private fun formatBytes(bytes: Long): String {
        val kb = bytes / 1024.0
        val mb = kb / 1024.0

        return when {
            mb >= 1 -> String.format(Locale.getDefault(), "%.1f MB", mb)
            kb >= 1 -> String.format(Locale.getDefault(), "%.1f KB", kb)
            else -> "$bytes B"
        }
    }

    // Creates an instance of ForegroundInfo which can be used to update the
    // ongoing notification.
    private fun createForegroundInfo(progress: String): ForegroundInfo {
        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        createChannel()

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(NOTIFICATION_TITLE)
            .setTicker(NOTIFICATION_TITLE)
            .setContentText(progress)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setOngoing(true)
            // Add the cancel action to the notification which can
            // be used to cancel the worker
            .addAction(android.R.drawable.ic_delete, "Cancel", intent)
            .build()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(NOTIFICATION_ID, notification, FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            ForegroundInfo(NOTIFICATION_ID, notification)
        }
    }

    private fun createChannel() {
        // Create a Notification channel
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun getTimestampForFilename(): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private companion object {
        const val NOTIFICATION_ID = 2
        const val CHANNEL_ID = "download_channel"
        const val CHANNEL_NAME = "Download"
        const val NOTIFICATION_TITLE = "Downloading"
    }
}