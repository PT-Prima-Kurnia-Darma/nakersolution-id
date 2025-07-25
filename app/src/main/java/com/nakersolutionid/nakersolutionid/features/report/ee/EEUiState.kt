package com.nakersolutionid.nakersolutionid.features.report.ee

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

@Immutable
data class EEUiState(
    val isLoading: Boolean = false,
    val elevatorResult: Resource<String>? = null,
    val eskalatorResult: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null,
    val loadedEquipmentType: SubInspectionType? = null
)
