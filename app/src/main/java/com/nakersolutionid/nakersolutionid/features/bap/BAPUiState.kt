package com.nakersolutionid.nakersolutionid.features.bap

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.History

data class BAPUiState(
    val histories: List<History> = emptyList(),
    val isLoading: Boolean = false,
    val historyResult: Resource<String>? = null,
    val error: String? = null,
    val activeFilters: FilterState = FilterState()
)