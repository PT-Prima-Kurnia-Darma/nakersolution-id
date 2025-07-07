package com.nakersolutionid.nakersolutionid.di

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.Report
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeReportRepository : IReportRepository {
    override fun sendReport(request: Report): Flow<Resource<String>> {
        return flowOf(Resource.Success("Success"))
    }

    override fun getAllReports(): Flow<List<History>> {
        return flowOf(listOf(History(
            id = 0,
            extraId = "A",
            documentType = DocumentType.LAPORAN,
            inspectionType = InspectionType.EE,
            subInspectionType = SubInspectionType.Elevator,
            equipmentType = "Elevator Penumpang",
            examinationType = "Pemeriksaan Berkala",
            ownerName = "PT. Perindo",
            createdAt = "2025-07-06T15:22:10.123Z",
            reportDate = "Kamis, 24 Desember 2023"
        )))
    }
}