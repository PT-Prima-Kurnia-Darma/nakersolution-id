package com.nakersolutionid.nakersolutionid.domain.model

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.ReportType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubReportType

data class History(
    val id: Long,
    val extraId: String,
    val documentType: DocumentType,
    val reportType: ReportType,
    val subReportType: SubReportType,
    val equipmentType: String,
    val inspectionType: String,
    val ownerName: String?,
    val createdAt: String?,
    val reportDate: String?
)