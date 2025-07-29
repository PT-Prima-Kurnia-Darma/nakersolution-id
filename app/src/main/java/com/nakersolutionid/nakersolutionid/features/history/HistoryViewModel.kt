package com.nakersolutionid.nakersolutionid.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiPaths
import com.nakersolutionid.nakersolutionid.domain.model.DownloadInfo
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.utils.DownloadState
import com.nakersolutionid.nakersolutionid.utils.Utils
import com.nakersolutionid.nakersolutionid.workers.SyncManager
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale
import java.util.UUID

// Extension function to check if a History object matches the search query.
// This encapsulates the search logic for better readability and maintenance.
private fun History.matchesQuery(query: String): Boolean {
    val searchableFields = listOfNotNull(
        documentType.toDisplayString(),
        inspectionType.toDisplayString(),
        subInspectionType.toDisplayString(),
        equipmentType,
        examinationType,
        ownerName,
        reportDate,
        createdAt?.let { "Dibuat: ${Utils.formatIsoDate(it)}" }
            ?: run { "Tanggal Tidak Tersedia" } // Search on formatted date
    )

    // Returns true if any of the fields contain the query (case-insensitive)
    return searchableFields.any { it.contains(query, ignoreCase = true) }
}

// Extension function to check if a History object matches the given filters.
private fun History.matchesFilters(filters: FilterState): Boolean {
    if (filters.documentType != null && this.documentType != filters.documentType) {
        return false
    }
    if (filters.inspectionType != null && this.inspectionType != filters.inspectionType) {
        return false
    }
    if (filters.subInspectionType != null && this.subInspectionType != filters.subInspectionType) {
        return false
    }
    return true
}

@OptIn(FlowPreview::class)
class HistoryViewModel(
    private val reportUseCase: ReportUseCase,
    private val syncManager: SyncManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // State untuk filter yang aktif
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow() // Expose filterState

    // Holds the original, unfiltered list of histories from the source.
    private val allHistories = MutableStateFlow<List<History>>(emptyList())

    private val _shareEvent = MutableSharedFlow<String>() // Akan berisi filePath
    val shareEvent: SharedFlow<String> = _shareEvent.asSharedFlow()

    init {
        // Coroutine to fetch all reports once and store them.
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            reportUseCase.getAllReports()
                .catch {
                    _uiState.update { it.copy(error = "Gagal memuat riwayat", isLoading = false) }
                }
                .collect { histories ->
                    allHistories.value = histories.reversed() // Membalik urutan di sini
                    // Jangan update uiState.histories langsung di sini, biarkan combine yang melakukannya

                    val initialDownloadStates = histories
                        .filter { it.isDownloaded && it.filePath != null }
                        .associate { it.id to DownloadState.Downloaded(it.filePath!!) }

                    _uiState.update { it.copy(isLoading = false, downloadStates = initialDownloadStates) }
                }
        }

        // Coroutine to handle the search and filter logic reactively.
        viewModelScope.launch {
            combine(
                searchQuery,
                filterState, // Amati perubahan filter
                allHistories // Amati perubahan daftar asli
            ) { query, filters, histories ->
                // Terapkan filter terlebih dahulu
                val filteredByCategories = histories.filter { it.matchesFilters(filters) }

                // Kemudian terapkan pencarian pada hasil yang sudah difilter
                if (query.isBlank()) {
                    filteredByCategories // Jika kueri kosong, kembalikan hasil filter saja
                } else {
                    filteredByCategories.filter { it.matchesQuery(query) }
                }
            }
                .catch {
                    _uiState.update { it.copy(error = "Terjadi kesalahan saat mencari atau memfilter") }
                }
                .collect { combinedFilteredHistories ->
                    // Update the UI state with the combined filtered list and active filters.
                    _uiState.update { it.copy(histories = combinedFilteredHistories) }
                }
        }
    }

    // Ganti fungsi downloadReport yang ada di HistoryViewModel.kt dengan ini
    fun downloadReport(history: History) {
        viewModelScope.launch {

            // Mulai worker dan dapatkan UUID-nya
            val downloadWorkId = syncManager.startDownload(history.id)

            if (downloadWorkId != null) {
                // Langsung update UI ke state Downloading
                _uiState.update { currentState ->
                    val newStates = currentState.downloadStates.toMutableMap()
                    newStates[history.id] = DownloadState.Downloading(0)
                    currentState.copy(downloadStates = newStates)
                }
                // Mulai amati progress untuk worker spesifik ini
                observeDownloadProgress(downloadWorkId, history.id)
            }
        }
    }

    private fun observeDownloadProgress(workId: UUID, historyId: Long) {
        // Luncurkan coroutine baru untuk mengamati Flow dari worker spesifik ini.
        // Coroutine ini akan otomatis dibatalkan saat ViewModel dihancurkan.
        viewModelScope.launch {
            syncManager.getWorkInfoById(workId).collect { workInfo -> // Gunakan .collect pada Flow
                if (workInfo != null) {
                    // Tentukan state baru berdasarkan status worker
                    val newDownloadState = when (workInfo.state) {
                        WorkInfo.State.RUNNING -> {
                            val progress = workInfo.progress.getInt("DOWNLOAD_PROGRESS", 0)
                            DownloadState.Downloading(progress)
                        }
                        WorkInfo.State.SUCCEEDED -> {
                            val filePath = workInfo.outputData.getString("KEY_FILE_PATH")
                            if (filePath != null) {
                                // Update database bahwa download selesai
                                viewModelScope.launch {
                                    // Ganti dengan use case Anda yang sesuai
                                    reportUseCase.updateDownloadedStatus(historyId, true, filePath)
                                    _shareEvent.emit(filePath)
                                }
                                DownloadState.Downloaded(filePath)
                            } else {
                                DownloadState.Idle // Gagal jika path null
                            }
                        }
                        WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> {
                            DownloadState.Idle // Kembali ke state awal jika gagal/dibatalkan
                        }
                        else -> null
                    }

                    // Update UI state jika ada perubahan state
                    if (newDownloadState != null) {
                        _uiState.update { currentState ->
                            val newStates = currentState.downloadStates.toMutableMap()
                            newStates[historyId] = newDownloadState
                            currentState.copy(downloadStates = newStates)
                        }
                    }
                }
            }
        }
    }

    fun deleteReport(history: History) { // Terima objek History, bukan hanya ID
        viewModelScope.launch {
            try {
                // Hapus file fisik jika ada
                if (history.isDownloaded && history.filePath != null) {
                    File(history.filePath).delete()
                }
                reportUseCase.deleteReport(history.id)
                // Data akan otomatis diperbarui melalui Flow
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Gagal menghapus laporan: ${e.message}") }
            }
        }
    }

    /**
     * Called by the UI whenever the search text changes.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    /**
     * Called by the UI to apply new filters.
     */
    fun applyFilters(newFilters: FilterState) {
        _filterState.value = newFilters
        _uiState.update { it.copy(activeFilters = newFilters) } // Perbarui activeFilters di UiState
    }

    /**
     * Called by the UI to clear all filters.
     */
    fun clearFilters() {
        _filterState.value = FilterState() // Reset filter ke kondisi awal
        _uiState.update { it.copy(activeFilters = FilterState()) } // Perbarui activeFilters di UiState
    }

    /**
     * Called by the UI to delete a report.
     */
    fun deleteReport(id: Long) {
        viewModelScope.launch {
            try {
                reportUseCase.deleteReport(id)
                // Data akan otomatis diperbarui melalui Flow yang sudah ada
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Gagal menghapus laporan: ${e.message}") }
            }
        }
    }

    fun increaseSyncCount() {
        _uiState.update { it.copy(triggerSync = it.triggerSync + 1) }
    }

    fun triggerSync() {
        syncManager.startSync()
    }

    /*fun setupPeriodicSync() {
        syncManager.setupPeriodicSync()
    }

    fun getSyncStatus(): LiveData<List<WorkInfo>> {
        return syncManager.getSyncStatus()
    }

    fun cancelSync() {
        syncManager.cancelSync()
    }*/

    private fun getApiPath(subInspectionType: SubInspectionType, documentType: DocumentType): String {
        val reportPathMap = mapOf(
            SubInspectionType.Elevator to ApiPaths.LAPORAN_ELEVATOR,
            SubInspectionType.Escalator to ApiPaths.LAPORAN_ESKALATOR,
            SubInspectionType.Forklift to ApiPaths.LAPORAN_FORKLIFT,
            SubInspectionType.Mobile_Crane to ApiPaths.LAPORAN_MOBILE_CRANE,
            SubInspectionType.Overhead_Crane to ApiPaths.LAPORAN_OVERHEAD_CRANE,
            SubInspectionType.Gantry_Crane to ApiPaths.LAPORAN_GANTRY_CRANE,
            SubInspectionType.Gondola to ApiPaths.LAPORAN_GONDOLA,
            SubInspectionType.Electrical to ApiPaths.LAPORAN_LISTRIK,
            SubInspectionType.Lightning_Conductor to ApiPaths.LAPORAN_PETIR,
            SubInspectionType.Fire_Protection to ApiPaths.LAPORAN_PROTEKSI_KEBAKARAN,
            SubInspectionType.Machine to ApiPaths.LAPORAN_MESIN,
            SubInspectionType.Motor_Diesel to ApiPaths.LAPORAN_MOTOR_DIESEL,
            SubInspectionType.General_PUBT to ApiPaths.LAPORAN_PUBT,
        )

        val bapPathMap = mapOf(
            SubInspectionType.Elevator to ApiPaths.BAP_ELEVATOR,
            SubInspectionType.Escalator to ApiPaths.BAP_ESKALATOR,
            SubInspectionType.Forklift to ApiPaths.BAP_FORKLIFT,
            SubInspectionType.Mobile_Crane to ApiPaths.BAP_MOBILE_CRANE,
            SubInspectionType.Overhead_Crane to ApiPaths.BAP_OVERHEAD_CRANE,
            SubInspectionType.Gantry_Crane to ApiPaths.BAP_GANTRY_CRANE,
            SubInspectionType.Gondola to ApiPaths.BAP_GONDOLA,
            SubInspectionType.Electrical to ApiPaths.BAP_LISTRIK,
            SubInspectionType.Lightning_Conductor to ApiPaths.BAP_PETIR,
            SubInspectionType.Fire_Protection to ApiPaths.BAP_PROTEKSI_KEBAKARAN,
            SubInspectionType.Machine to ApiPaths.BAP_MESIN,
            SubInspectionType.Motor_Diesel to ApiPaths.BAP_MOTOR_DIESEL,
            SubInspectionType.General_PUBT to ApiPaths.BAP_PUBT,
        )

        return when (documentType) {
            DocumentType.LAPORAN -> reportPathMap[subInspectionType] ?: ""
            DocumentType.BAP -> bapPathMap[subInspectionType] ?: ""
            else -> ""
        }
    }
}