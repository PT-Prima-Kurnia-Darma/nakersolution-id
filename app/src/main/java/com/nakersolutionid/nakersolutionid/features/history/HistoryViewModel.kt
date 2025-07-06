package com.nakersolutionid.nakersolutionid.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.utils.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            reportUseCase.getAllReports() // This returns Flow<List<History>>
                .catch { e ->
                    _uiState.update { it.copy(error = "Failed to load reports") }
                }
                .collect { uiHistories ->
                    _uiState.update {
                        it.copy(isLoading = false, histories = uiHistories)
                    }
                }
        }
    }
}