package com.nakersolutionid.nakersolutionid.features.history

import com.nakersolutionid.nakersolutionid.data.Resource


data class ReportData(
    val id: String,
    val name: String,
    val subName: String,
    val typeInspection: String,
    val type: String,
    val createdAt: String
)

data class HistoryUiState(
    val reports: List<ReportData> = emptyList(),
    val isLoading: Boolean = false,
    val historyResult: Resource<String>? = null,
    val error: String? = null
)
