package com.nakersolutionid.nakersolutionid.features.history

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.History

data class HistoryUiState(
    val histories: List<History> = emptyList(),
    val isLoading: Boolean = false,
    val historyResult: Resource<String>? = null,
    val error: String? = null
)
