package com.nakersolutionid.nakersolutionid.features.bap

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.Resource

@Immutable
data class BAPCreationUiState(
    val isLoading: Boolean = false,
    val result: Resource<String>? = null,
)
