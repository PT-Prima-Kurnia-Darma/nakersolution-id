package com.nakersolutionid.nakersolutionid.features.report.ilpp

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain

@Immutable
data class ILPPUiState(
    val isLoading: Boolean = false,
    val mlLoading: Boolean = false,
    val mlResult: String? = null,
    val result: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null,
    val loadedEquipmentType: SubInspectionType? = null,
    val inspectionWithDetailsDomain: InspectionWithDetailsDomain? = null,
    val editMode: Boolean = false
)