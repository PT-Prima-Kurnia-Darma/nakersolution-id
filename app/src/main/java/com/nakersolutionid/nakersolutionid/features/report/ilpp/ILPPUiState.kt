package com.nakersolutionid.nakersolutionid.features.report.ilpp

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

@Immutable
data class ILPPUiState(
    val isLoading: Boolean = false,
    val electricResult: Resource<String>? = null,
    val lightningResult: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null,
    val loadedEquipmentType: SubInspectionType? = null
)