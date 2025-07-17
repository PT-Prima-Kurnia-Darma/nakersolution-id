package com.nakersolutionid.nakersolutionid.features.bap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.utils.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

class BAPViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(BAPUiState())
    val uiState: StateFlow<BAPUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // State untuk filter yang aktif
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow() // Expose filterState

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
                    allHistories.value = histories.reversed() // Membalik urutan di sini
                    // Jangan update uiState.histories langsung di sini, biarkan combine yang melakukannya
                    _uiState.update { it.copy(isLoading = false) } // Hanya update isLoading
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
}