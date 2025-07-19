package com.nakersolutionid.nakersolutionid.features.report.ipk

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

data class IPKUiState(
    val isLoading: Boolean = false,
    val fireProtectionResult: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null,
    val loadedEquipmentType: SubInspectionType? = null
)
