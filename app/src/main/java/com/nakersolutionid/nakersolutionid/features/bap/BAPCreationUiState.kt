package com.nakersolutionid.nakersolutionid.features.bap

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain

@Immutable
data class BAPCreationUiState(
    val isLoading: Boolean = false,
    val result: Resource<String>? = null,
//    val createResult: Resource<String>? = null,
    val inspectionWithDetailsDomain: InspectionWithDetailsDomain? = null,
    val editMode: Boolean = false
)
