package com.nakersolutionid.nakersolutionid.features.bap

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.features.history.FilterState

data class BAPUiState(
    // REMOVED: The list is now provided by the PagingData flow.
    // val histories: List<History> = emptyList(),
    val isLoading: Boolean = false,
    val historyResult: Resource<String>? = null,
    val error: String? = null,
    val activeFilters: FilterState = FilterState()
)