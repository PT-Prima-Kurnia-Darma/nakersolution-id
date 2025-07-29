package com.nakersolutionid.nakersolutionid.utils

sealed class DownloadState {
    object Idle : DownloadState() // State awal, tombol "Download"
    data class Downloading(val progress: Int) : DownloadState() // State saat download, menampilkan progress
    data class Downloaded(val filePath: String) : DownloadState() // State selesai, tombol "Share"
}