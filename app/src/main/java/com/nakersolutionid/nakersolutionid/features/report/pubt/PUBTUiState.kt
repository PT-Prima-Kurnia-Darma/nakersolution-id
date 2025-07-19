package com.nakersolutionid.nakersolutionid.features.report.pubt

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

data class PUBTUiState(
    val isLoading: Boolean = false,
    val generalResult: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null,
    val loadedEquipmentType: SubInspectionType? = null
)
