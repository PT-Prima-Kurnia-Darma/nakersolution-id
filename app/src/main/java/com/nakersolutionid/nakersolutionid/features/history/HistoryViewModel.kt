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
            reportUseCase.getAllReports() // This returns Flow<List<Report>>
                .map { domainReportList ->
                    domainReportList.map {
                        ReportData(
                            id = it.id.orEmpty(),
                            name = it.typeInspection.orEmpty(),
                            subName = it.subNameOfInspectionType.orEmpty(),
                            typeInspection = it.typeInspection.orEmpty(),
                            type = it.eskOrElevType.orEmpty(),
                            createdAt = Utils.formatIsoDate(it.createdAt.orEmpty())
                        )
                    }
                }
                .catch { e ->
                    _uiState.update { it.copy(error = "Failed to load reports") }
                }
                .collect { uiReports ->
                    // Update the state with the final, mapped list
                    _uiState.update {
                        it.copy(isLoading = false, reports = uiReports)
                    }
                }
        }
    }
}