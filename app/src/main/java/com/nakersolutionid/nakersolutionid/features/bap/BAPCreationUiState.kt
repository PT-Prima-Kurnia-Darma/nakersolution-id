package com.nakersolutionid.nakersolutionid.features.bap

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

@Immutable
data class BAPCreationUiState(
    val isLoading: Boolean = false,
    val result: Resource<String>? = null,
    val editMode: Boolean = false
)
