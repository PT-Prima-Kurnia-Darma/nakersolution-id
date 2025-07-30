package com.nakersolutionid.nakersolutionid.features.bap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.history.FilterState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
class BAPViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(BAPUiState())
    val uiState: StateFlow<BAPUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // This state correctly sets the default filter to only show 'LAPORAN'.
    // The filter sheet for BAP doesn't allow changing this, so it remains constant.
    private val _filterState = MutableStateFlow(FilterState(documentType = DocumentType.LAPORAN))
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    // The new PagingData flow that reacts to search and filter changes.
    val historyPagingData: Flow<PagingData<History>> = combine(
        searchQuery,
        filterState
    ) { query, filters ->
        Pair(query, filters)
    }.flatMapLatest { (query, filters) ->
        reportUseCase.getAllReports(query, filters, true)
    }.cachedIn(viewModelScope)


    /**
     * Called by the UI whenever the search text changes.
     */
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    /**
     * Called by the UI to apply new filters from the bottom sheet.
     */
    fun applyFilters(newFilters: FilterState) {
        // Ensure the documentType filter is preserved.
        _filterState.value = newFilters.copy(documentType = DocumentType.LAPORAN)
        _uiState.update { it.copy(activeFilters = _filterState.value) }
    }

    /**
     * Called by the UI to clear all filters.
     */
    fun clearFilters() {
        // Reset filters but keep the essential documentType filter.
        _filterState.value = FilterState(documentType = DocumentType.LAPORAN)
        _uiState.update { it.copy(activeFilters = _filterState.value) }
    }
}