package com.nakersolutionid.nakersolutionid.data.repository

import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.local.mapper.toDomain
import com.nakersolutionid.nakersolutionid.data.local.mapper.toEntity
import com.nakersolutionid.nakersolutionid.data.local.mapper.toHistory
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.BaseApiResponse
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toElevatorBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toElevatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toEscalatorBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toEscalatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiPaths
import com.nakersolutionid.nakersolutionid.data.remote.network.ApiResponse
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ReportRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val userPreference: UserPreference
) : IReportRepository {
    override suspend fun saveReport(request: InspectionWithDetailsDomain) {
        val inspectionWithDetails = request.toEntity()
        localDataSource.insertInspection(
            inspectionEntity = inspectionWithDetails.inspectionEntity,
            checkItems = inspectionWithDetails.checkItems,
            findings = inspectionWithDetails.findings,
            testResults = inspectionWithDetails.testResults
        )
    }

    override suspend fun getInspection(id: Long): InspectionWithDetailsDomain? {
        return localDataSource.getInspection(id).map { it.toDomain() }.firstOrNull()
    }

    override suspend fun getPendingSyncReports(): List<InspectionWithDetailsDomain> {
        return localDataSource.getPendingSyncReports().map { it.toDomain() }
    }

    override suspend fun updateSyncStatus(id: Long, isSynced: Boolean) {
        localDataSource.updateSyncStatus(id, isSynced)
    }

    private fun getApiPath(subInspectionType: SubInspectionType, documentType: DocumentType): String {
        val reportPathMap = mapOf(
            SubInspectionType.Elevator to ApiPaths.LAPORAN_ELEVATOR,
            SubInspectionType.Escalator to ApiPaths.LAPORAN_ESKALATOR,
            SubInspectionType.Forklift to ApiPaths.LAPORAN_FORKLIFT,
            SubInspectionType.Mobile_Crane to ApiPaths.LAPORAN_MOBILE_CRANE,
            SubInspectionType.Overhead_Crane to ApiPaths.LAPORAN_OVERHEAD_CRANE,
            SubInspectionType.Gantry_Crane to ApiPaths.LAPORAN_GANTRY_CRANE,
            SubInspectionType.Gondola to ApiPaths.LAPORAN_GONDOLA
        )

        val bapPathMap = mapOf(
            SubInspectionType.Elevator to ApiPaths.BAP_ELEVATOR,
            SubInspectionType.Escalator to ApiPaths.BAP_ESKALATOR,
            SubInspectionType.Forklift to ApiPaths.BAP_FORKLIFT,
            SubInspectionType.Mobile_Crane to ApiPaths.BAP_MOBILE_CRANE,
            SubInspectionType.Overhead_Crane to ApiPaths.BAP_OVERHEAD_CRANE,
            SubInspectionType.Gantry_Crane to ApiPaths.BAP_GANTRY_CRANE,
            SubInspectionType.Gondola to ApiPaths.BAP_GONDOLA
        )

        return when (documentType) {
            DocumentType.LAPORAN -> reportPathMap[subInspectionType] ?: ""
            DocumentType.BAP -> bapPathMap[subInspectionType] ?: ""
            else -> ""
        }
    }

    private suspend fun handleSyncSuccess(data: Any, failCounter: () -> Unit) {
        try {
            val innerData = (data as? BaseApiResponse<*>)?.data
            val responseType = when (innerData) {
                // Laporan
                is ElevatorSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is EscalatorSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }

                // Bap
                is ElevatorBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is EscalatorBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                else -> null
            }

            responseType?.let {
                saveReport(it)
            } ?: failCounter()
        } catch (_: Exception) {
            failCounter()
        }
    }

    private suspend fun processReports(
        reports: List<InspectionWithDetailsDomain>,
        token: String,
        action: suspend (String, String, String, InspectionWithDetailsDomain) -> ApiResponse<Any>
    ): Boolean {
        var fail = 0
        reports.forEach { report ->
            val path = getApiPath(report.inspection.subInspectionType, report.inspection.documentType)
            if (path.isEmpty()) {
                fail++
                return@forEach
            }
            val id = if (report.inspection.documentType == DocumentType.BAP) report.inspection.moreExtraId else report.inspection.extraId
            when (val apiResponse = action(token, path, id, report)) {
                is ApiResponse.Success -> handleSyncSuccess(apiResponse.data) { fail++ }
                is ApiResponse.Error -> fail++
                is ApiResponse.Empty -> null
            }
        }
        return fail == 0
    }

    override suspend fun syncInspection(): Boolean {
        val listReports = localDataSource.getPendingSyncReports().map { it.toDomain() }
        if (listReports.isEmpty()) return false
        val token = "Bearer ${userPreference.getUserToken() ?: ""}"
        return processReports(listReports, token) { t, p, _, r ->
            when (r.inspection.documentType) {
                DocumentType.LAPORAN -> when (r.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.createReport<ElevatorReportRequest, ElevatorSingleReportResponseData>(t, p, r.toElevatorReportRequest()).first()
                    SubInspectionType.Escalator -> remoteDataSource.createReport<EscalatorReportRequest, EscalatorSingleReportResponseData>(t, p, r.toEscalatorReportRequest()).first()
                    else -> ApiResponse.Empty
                }
                DocumentType.BAP -> when (r.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.createReport<ElevatorBapRequest, ElevatorBapSingleReportResponseData>(t, p, r.toElevatorBapRequest()).first()
                    SubInspectionType.Escalator -> remoteDataSource.createReport<EscalatorBapRequest, EscalatorBapSingleReportResponseData>(t, p, r.toEscalatorBapRequest()).first()
                    else -> ApiResponse.Empty
                }
                else -> ApiResponse.Empty
            }
        }
    }

    override suspend fun syncUpdateInspection(): Boolean {
        val listReports = localDataSource.getPendingSyncReports().map { it.toDomain() }
        if (listReports.isEmpty()) return false
        val token = "Bearer ${userPreference.getUserToken() ?: ""}"
        return processReports(listReports, token) { t, p, i, r ->
            when (r.inspection.documentType) {
                DocumentType.LAPORAN -> when (r.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.updateReport<ElevatorReportRequest, ElevatorSingleReportResponseData>(t, p, i, r.toElevatorReportRequest()).first()
                    SubInspectionType.Escalator -> remoteDataSource.updateReport<EscalatorReportRequest, EscalatorSingleReportResponseData>(t, p, i, r.toEscalatorReportRequest()).first()
                    else -> ApiResponse.Empty
                }
                DocumentType.BAP -> when (r.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.updateReport<ElevatorBapRequest, ElevatorBapSingleReportResponseData>(t, p, i, r.toElevatorBapRequest()).first()
                    SubInspectionType.Escalator -> remoteDataSource.updateReport<EscalatorBapRequest, EscalatorBapSingleReportResponseData>(t, p, i, r.toEscalatorBapRequest()).first()
                    else -> ApiResponse.Empty
                }
                else -> ApiResponse.Empty
            }
        }
    }

    override fun getAllReports(): Flow<List<History>> {
        return localDataSource.getAllInspectionsWithDetails().map { it ->
            it.map {
                it.inspectionEntity.toHistory()
            }
        }
    }

    override suspend fun deleteReport(id: Long) {
        val data = localDataSource.getInspection(id).firstOrNull() ?: return
        if (!data.inspectionEntity.isSynced) {
            localDataSource.deleteInspection(id)
            return
        }
        val token = "Bearer ${userPreference.getUserToken() ?: ""}"
        val path = getApiPath(data.inspectionEntity.subInspectionType, data.inspectionEntity.documentType)
        if (path.isEmpty()) return
        val extraId = if (data.inspectionEntity.documentType == DocumentType.BAP) data.inspectionEntity.moreExtraId else data.inspectionEntity.extraId
        val apiResponse = remoteDataSource.deleteReport(token, path, extraId).first()
        if (apiResponse is ApiResponse.Success) {
            localDataSource.deleteInspection(id)
        }
    }
}