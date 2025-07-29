package com.nakersolutionid.nakersolutionid.di

import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.DownloadInfo
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeReportRepository : IReportRepository {
    override suspend fun saveReport(request: InspectionWithDetailsDomain) {
        return
    }

    override fun createReport(report: InspectionWithDetailsDomain): Flow<Resource<String>> {
        return flow { emit(Resource.Success("Success")) }
    }

    override fun updateReport(report: InspectionWithDetailsDomain): Flow<Resource<String>> {
        return flow { emit(Resource.Success("Success")) }
    }

    override suspend fun getInspection(id: Long): InspectionWithDetailsDomain? {
        return null
    }

    override suspend fun getPendingSyncReports(): List<InspectionWithDetailsDomain> {
        return emptyList()
    }

    override suspend fun updateSyncStatus(id: Long, isSynced: Boolean) {
        return
    }

    override suspend fun syncInspection(): Boolean = true
    override suspend fun syncUpdateInspection(): Boolean = true

    override fun getAllReports(): Flow<List<History>> {
        return flowOf(
            listOf(
                History(
                    id = 0,
                    extraId = "A",
                    moreExtraId = "B",
                    documentType = DocumentType.LAPORAN,
                    inspectionType = InspectionType.EE,
                    subInspectionType = SubInspectionType.Elevator,
                    equipmentType = "Elevator Penumpang",
                    examinationType = "Pemeriksaan Berkala",
                    ownerName = "PT. Perindo",
                    createdAt = "2025-07-06T15:22:10.123Z",
                    reportDate = "Kamis, 24 Desember 2025",
                    isSynced = false
                ),
                History(
                    id = 1,
                    extraId = "B",
                    moreExtraId = "C",
                    documentType = DocumentType.LAPORAN,
                    inspectionType = InspectionType.EE,
                    subInspectionType = SubInspectionType.Elevator,
                    equipmentType = "Elevator Penumpang",
                    examinationType = "Pemeriksaan Berkala",
                    ownerName = "PT. Perindo",
                    createdAt = "2025-07-06T15:22:10.123Z",
                    reportDate = "Kamis, 27 Desember 2023",
                    isSynced = true
                )
            )
        )
    }

    override suspend fun deleteReport(id: Long) {
        null
    }

    override suspend fun getDownloadInfo(id: Long): Resource<DownloadInfo> {
        return Resource.Success(DownloadInfo("", "", ""))
    }

    override suspend fun updateDownloadedStatus(
        id: Long,
        isDownloaded: Boolean,
        filePath: String
    ) {
        null
    }

    override fun getApiPath(
        subInspectionType: SubInspectionType,
        documentType: DocumentType
    ): String {
        return ""
    }
}