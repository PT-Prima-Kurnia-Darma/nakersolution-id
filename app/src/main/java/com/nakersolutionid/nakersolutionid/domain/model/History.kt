package com.nakersolutionid.nakersolutionid.domain.model

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

data class History(
    val id: Long,
    val extraId: String,
    val documentType: DocumentType,
    val inspectionType: InspectionType,
    val subInspectionType: SubInspectionType,
    val equipmentType: String,
    val examinationType: String,
    val ownerName: String?,
    val createdAt: String?,
    val reportDate: String?
)