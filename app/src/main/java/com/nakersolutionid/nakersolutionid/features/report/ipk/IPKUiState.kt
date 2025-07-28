package com.nakersolutionid.nakersolutionid.features.report.ipk

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain

data class IPKUiState(
    val isLoading: Boolean = false,
    val result: Resource<String>? = null,
    val editLoadResult: Resource<String>? = null,
    val loadedEquipmentType: SubInspectionType? = null,
    val inspectionWithDetailsDomain: InspectionWithDetailsDomain? = null,
    val editMode: Boolean = false
)
