package com.nakersolutionid.nakersolutionid.features.history

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.utils.DownloadState

data class HistoryUiState(
    val isLoading: Boolean = false,
    val historyResult: Resource<String>? = null,
    val error: String? = null,
    val activeFilters: FilterState = FilterState(),
    val triggerSync: Int = 0,
    val downloadStates: Map<Long, DownloadState> = emptyMap()
)
