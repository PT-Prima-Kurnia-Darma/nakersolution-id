package com.nakersolutionid.nakersolutionid.features.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.work.WorkInfo
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.utils.DownloadState
import com.nakersolutionid.nakersolutionid.workers.SyncManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

@OptIn(FlowPreview::class)
class HistoryViewModel(
    private val reportUseCase: ReportUseCase,
    private val syncManager: SyncManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    private val _shareEvent = MutableSharedFlow<String>()
    val shareEvent: SharedFlow<String> = _shareEvent.asSharedFlow()

    // The single source of truth for paginated data.
    // It automatically re-triggers when search query or filters change.
    @OptIn(ExperimentalCoroutinesApi::class)
    val historyPagingData: Flow<PagingData<History>> = combine(
        searchQuery,
        filterState
    ) { query, filters ->
        Pair(query, filters)
    }.flatMapLatest { (query, filters) ->
        // The use case now takes the query and filters for the database.
        // To maintain functionality, filters must be passed down.
        reportUseCase.getAllReports(query, filters)
    }.cachedIn(viewModelScope) // Cache the results in the ViewModel scope.

    init {
        viewModelScope.launch {
            reportUseCase.getDownloadedReports() // Assume this is a new lightweight function
                .collect { histories ->
                    val initialDownloadStates = histories
                        .filter { it.isDownloaded && it.filePath != null }
                        .associate { it.id to DownloadState.Downloaded(it.filePath!!) }
                    _uiState.update { it.copy(downloadStates = initialDownloadStates) }
                }
        }
    }


    fun downloadReport(history: History) {
        viewModelScope.launch {
            val downloadWorkId = syncManager.startDownload(history.id)
            if (downloadWorkId != null) {
                _uiState.update { currentState ->
                    val newStates = currentState.downloadStates.toMutableMap()
                    newStates[history.id] = DownloadState.Downloading(0)
                    currentState.copy(downloadStates = newStates)
                }
                observeDownloadProgress(downloadWorkId, history.id)
            }
        }
    }

    private fun observeDownloadProgress(workId: UUID, historyId: Long) {
        viewModelScope.launch {
            syncManager.getWorkInfoById(workId).collect { workInfo ->
                if (workInfo != null) {
                    val newDownloadState = when (workInfo.state) {
                        WorkInfo.State.RUNNING -> {
                            val progress = workInfo.progress.getInt("DOWNLOAD_PROGRESS", 0)
                            DownloadState.Downloading(progress)
                        }
                        WorkInfo.State.SUCCEEDED -> {
                            val filePath = workInfo.outputData.getString("KEY_FILE_PATH")
                            if (filePath != null) {
                                viewModelScope.launch {
                                    reportUseCase.updateDownloadedStatus(historyId, true, filePath)
                                    _shareEvent.emit(filePath)
                                }
                                DownloadState.Downloaded(filePath)
                            } else {
                                DownloadState.Idle
                            }
                        }
                        WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> DownloadState.Idle
                        else -> null
                    }

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

    fun triggerSync() {
        val uuid = syncManager.startSync()
        observeSyncProgress(uuid)
    }

    private fun observeSyncProgress(workId: UUID) {
        viewModelScope.launch {
            syncManager.getWorkInfoById(workId).collect { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.RUNNING -> {}
                        WorkInfo.State.SUCCEEDED -> {}
                        WorkInfo.State.FAILED -> {
                            onUpdateUiState { it.copy(error = workInfo.outputData.getString("message")) }
                        }
                        WorkInfo.State.CANCELLED -> {}
                        WorkInfo.State.ENQUEUED -> {}
                        WorkInfo.State.BLOCKED -> {}
                    }
                }
            }
        }
    }

    fun deleteReport(history: History) {
        viewModelScope.launch {
            try {
                if (history.isDownloaded && history.filePath != null) {
                    File(history.filePath).delete()
                }
                reportUseCase.deleteReport(history.id)
                // The UI will update automatically because PagingSource will be invalidated.
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun onUpdateUiState(newState: (HistoryUiState) -> HistoryUiState) {
        _uiState.update(newState)
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun applyFilters(newFilters: FilterState) {
        _filterState.value = newFilters
        _uiState.update { it.copy(activeFilters = newFilters) }
    }

    fun clearFilters() {
        _filterState.value = FilterState()
        _uiState.update { it.copy(activeFilters = FilterState()) }
    }
}