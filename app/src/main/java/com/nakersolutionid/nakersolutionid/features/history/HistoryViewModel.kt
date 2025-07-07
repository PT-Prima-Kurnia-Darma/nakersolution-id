package com.nakersolutionid.nakersolutionid.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.utils.Utils
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

// Helper function to format enum names for searching and displaying
private fun formatEnumName(enum: Enum<*>): String {
    return enum.name.replace('_', ' ').lowercase(Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

// Extension function to check if a History object matches the search query.
// This encapsulates the search logic for better readability and maintenance.
private fun History.matchesQuery(query: String): Boolean {
    val searchableFields = listOfNotNull(
        formatEnumName(documentType),
        inspectionType.name,
        formatEnumName(subInspectionType),
        equipmentType,
        examinationType,
        ownerName,
        reportDate,
        createdAt?.let { Utils.formatIsoDate(it) } // Search on formatted date
    )

    // Returns true if any of the fields contain the query (case-insensitive)
    return searchableFields.any { it.contains(query, ignoreCase = true) }
}


@OptIn(FlowPreview::class)
class HistoryViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Holds the original, unfiltered list of histories from the source.
    private val allHistories = MutableStateFlow<List<History>>(emptyList())

    init {
        // Coroutine to fetch all reports once and store them.
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            reportUseCase.getAllReports()
                .catch {
                    _uiState.update { it.copy(error = "Gagal memuat riwayat", isLoading = false) }
                }
                .collect { histories ->
                    allHistories.value = histories
                    _uiState.update {
                        it.copy(isLoading = false, histories = histories)
                    }
                }
        }

        // Coroutine to handle the search logic reactively.
        viewModelScope.launch {
            searchQuery
                .debounce(50L) // Wait for 300ms of no new input before searching
                .combine(allHistories) { query, histories ->
                    if (query.isBlank()) {
                        histories // If query is empty, return the full list
                    } else {
                        // Filter the list based on the query using our extension function
                        histories.filter { it.matchesQuery(query) }
                    }
                }
                .catch {
                    _uiState.update { it.copy(error = "Terjadi kesalahan saat mencari") }
                }
                .collect { filteredHistories ->
                    // Update the UI state with the filtered list.
                    _uiState.update { it.copy(histories = filteredHistories) }
                }
        }
    }

    /**
     * Called by the UI whenever the search text changes.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}