package com.nakersolutionid.nakersolutionid.features.report.pubt

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain

data class PUBTUiState(
    val isLoading: Boolean = false,
    val result: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null,
    val loadedEquipmentType: SubInspectionType? = null,
    val inspectionWithDetailsDomain: InspectionWithDetailsDomain? = null,
    val editMode: Boolean = false,
    val mlLoading: Boolean = false,
    val mlResult: String? = null,
)
