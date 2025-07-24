package com.nakersolutionid.nakersolutionid.data.repository

import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.local.mapper.toDomain
import com.nakersolutionid.nakersolutionid.data.local.mapper.toEntity
import com.nakersolutionid.nakersolutionid.data.local.mapper.toHistory
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataSource
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
import kotlin.collections.forEach

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

    override suspend fun syncInspection(): Boolean {
        val listReports = localDataSource.getPendingSyncReports()
        if (listReports.isEmpty()) {
            return false
        }

        var fail = 0
        val token = "Bearer ${userPreference.getUserToken() ?: ""}"

        listReports.map {
            it.toDomain()
        }.forEach { report ->
            val reportPath = when (report.inspection.subInspectionType) {
                SubInspectionType.Elevator -> ApiPaths.LAPORAN_ELEVATOR
                SubInspectionType.Escalator -> ApiPaths.LAPORAN_ESKALATOR
                SubInspectionType.Forklift -> ApiPaths.LAPORAN_FORKLIFT
                SubInspectionType.Mobile_Crane -> ApiPaths.LAPORAN_MOBILE_CRANE
                SubInspectionType.Overhead_Crane -> ApiPaths.LAPORAN_OVERHEAD_CRANE
                SubInspectionType.Gantry_Crane -> ApiPaths.LAPORAN_GANTRY_CRANE
                SubInspectionType.Gondola -> ApiPaths.LAPORAN_GONDOLA
                else -> ""
            }

            val bapPath = when (report.inspection.subInspectionType) {
                SubInspectionType.Elevator -> ApiPaths.BAP_ELEVATOR
                SubInspectionType.Escalator -> ApiPaths.BAP_ESKALATOR
                SubInspectionType.Forklift -> ApiPaths.BAP_FORKLIFT
                SubInspectionType.Mobile_Crane -> ApiPaths.BAP_MOBILE_CRANE
                SubInspectionType.Overhead_Crane -> ApiPaths.BAP_OVERHEAD_CRANE
                SubInspectionType.Gantry_Crane -> ApiPaths.BAP_GANTRY_CRANE
                SubInspectionType.Gondola -> ApiPaths.BAP_GONDOLA
                else -> ""
            }

            val apiResponse = when (report.inspection.documentType) {
                DocumentType.LAPORAN -> when (report.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.createReport<ElevatorReportRequest, ElevatorSingleReportResponseData>(
                        token,
                        reportPath,
                        report.toElevatorReportRequest()
                    ).first()
                    SubInspectionType.Escalator -> remoteDataSource.createReport<EscalatorReportRequest, EscalatorSingleReportResponseData>(
                        token,
                        reportPath,
                        report.toEscalatorReportRequest()
                    ).first()
                    SubInspectionType.Forklift,
                    SubInspectionType.Mobile_Crane,
                    SubInspectionType.Overhead_Crane,
                    SubInspectionType.Gantry_Crane,
                    SubInspectionType.Gondola -> ApiResponse.Empty // Not yet implemented
                    else -> ApiResponse.Empty
                }

                DocumentType.BAP -> when (report.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.createReport<ElevatorBapRequest, ElevatorBapSingleReportResponseData>(
                        token,
                        bapPath,
                        report.toElevatorBapRequest()
                    ).first()
                    SubInspectionType.Escalator -> remoteDataSource.createReport<EscalatorBapRequest, EscalatorBapSingleReportResponseData>(
                        token,
                        bapPath,
                        report.toEscalatorBapRequest()
                    ).first()
                    SubInspectionType.Forklift,
                    SubInspectionType.Mobile_Crane,
                    SubInspectionType.Overhead_Crane,
                    SubInspectionType.Gantry_Crane,
                    SubInspectionType.Gondola -> ApiResponse.Empty
                    else -> ApiResponse.Empty
                }
                else -> ApiResponse.Empty
            }

            when (apiResponse) {
                is ApiResponse.Empty -> null
                is ApiResponse.Error -> {
                    fail++
                }
                is ApiResponse.Success -> {
                    try {
                        apiResponse.data.data.let { data ->
                            var id = 0L
                            var extraId  = ""
                            var moreExtraId = ""
                            val responseType = when (data) {
                                // Laporan
                                is ElevatorSingleReportResponseData -> { id = data.laporan.extraId; extraId = data.laporan.id; data.laporan.toInspectionWithDetailsDomain() }
                                is EscalatorSingleReportResponseData -> { id = data.laporan.extraId; extraId = data.laporan.id; data.laporan.toInspectionWithDetailsDomain() }

                                // Bap
                                is ElevatorBapSingleReportResponseData -> { id = data.bap.extraId; extraId = data.bap.laporanId; moreExtraId = data.bap.id; data.bap.toInspectionWithDetailsDomain() }
                                is EscalatorBapSingleReportResponseData -> { id = data.bap.extraId; extraId = data.bap.laporanId; moreExtraId = data.bap.id; data.bap.toInspectionWithDetailsDomain() }
                                else -> null
                            }

                            responseType?.let {
                                val modified = it.copy(inspection = it.inspection.copy(id = id, extraId = extraId, moreExtraId = moreExtraId))
                                saveReport(modified)
                            }

                        }
                    } catch (_: Exception) {
                        fail++
                    }
                }
            }
        }
        return fail == 0
    }

    override suspend fun syncUpdateInspection(): Boolean {
        val listReports = localDataSource.getPendingSyncReports()
        if (listReports.isEmpty()) {
            return false
        }

        var fail = 0
        val token = "Bearer ${userPreference.getUserToken() ?: ""}"

        listReports.map {
            it.toDomain()
        }.forEach { report ->
            val extraId = report.inspection.extraId
            val reportPath = when (report.inspection.subInspectionType) {
                SubInspectionType.Elevator -> ApiPaths.LAPORAN_ELEVATOR
                SubInspectionType.Escalator -> ApiPaths.LAPORAN_ESKALATOR
                SubInspectionType.Forklift -> ApiPaths.LAPORAN_FORKLIFT
                SubInspectionType.Mobile_Crane -> ApiPaths.LAPORAN_MOBILE_CRANE
                SubInspectionType.Overhead_Crane -> ApiPaths.LAPORAN_OVERHEAD_CRANE
                SubInspectionType.Gantry_Crane -> ApiPaths.LAPORAN_GANTRY_CRANE
                SubInspectionType.Gondola -> ApiPaths.LAPORAN_GONDOLA
                else -> ""
            }

            val bapPath = when (report.inspection.subInspectionType) {
                SubInspectionType.Elevator -> ApiPaths.BAP_ELEVATOR
                SubInspectionType.Escalator -> ApiPaths.BAP_ESKALATOR
                SubInspectionType.Forklift -> ApiPaths.BAP_FORKLIFT
                SubInspectionType.Mobile_Crane -> ApiPaths.BAP_MOBILE_CRANE
                SubInspectionType.Overhead_Crane -> ApiPaths.BAP_OVERHEAD_CRANE
                SubInspectionType.Gantry_Crane -> ApiPaths.BAP_GANTRY_CRANE
                SubInspectionType.Gondola -> ApiPaths.BAP_GONDOLA
                else -> ""
            }

            val apiResponse = when (report.inspection.documentType) {
                DocumentType.LAPORAN -> when (report.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.updateReport<ElevatorReportRequest, ElevatorSingleReportResponseData>(
                        token,
                        reportPath,
                        extraId,
                        report.toElevatorReportRequest()
                    ).first()
                    SubInspectionType.Escalator,
                    SubInspectionType.Forklift,
                    SubInspectionType.Mobile_Crane,
                    SubInspectionType.Overhead_Crane,
                    SubInspectionType.Gantry_Crane,
                    SubInspectionType.Gondola -> ApiResponse.Empty // Not yet implemented
                    else -> ApiResponse.Empty
                }

                DocumentType.BAP -> when (report.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.updateReport<ElevatorBapRequest, ElevatorBapSingleReportResponseData>(
                        token,
                        bapPath,
                        extraId,
                        report.toElevatorBapRequest()
                    ).first()
                    else -> ApiResponse.Empty
                }
                else -> ApiResponse.Empty
            }

            when (apiResponse) {
                is ApiResponse.Empty -> null
                is ApiResponse.Error -> {
                    fail++
                }
                is ApiResponse.Success -> {
                    try {
                        apiResponse.data.data.let { data ->
                            var id = 0L
                            var extraId  = ""
                            var moreExtraId = ""
                            val responseType = when (data) {
                                // Laporan
                                is ElevatorSingleReportResponseData -> { id = data.laporan.extraId; extraId = data.laporan.id;data.laporan.toInspectionWithDetailsDomain() }
                                // is EscalatorSingleReportResponseData -> { id = data.laporan.extraId; extraId = data.laporan.id; data.laporan }

                                // Bap
                                is ElevatorBapSingleReportResponseData -> { id = data.bap.extraId; extraId = data.bap.laporanId; moreExtraId = data.bap.id; data.bap.toInspectionWithDetailsDomain() }
                                // is EscalatorBapSingleReportResponseData -> { id = data.bap.extraId; extraId = data.bap.id }
                                else -> null
                            }

                            responseType?.let {
                                val modified = it.copy(inspection = it.inspection.copy(id = id, extraId = extraId, moreExtraId = moreExtraId))
                                saveReport(modified)
                            }
                        }
                    } catch (_: Exception) {
                        fail++
                    }
                }
            }

            /*when (apiResponse) {
                is ApiResponse.Empty -> null
                is ApiResponse.Error -> {
                    fail++
                }
                is ApiResponse.Success -> {
                    try {
                        apiResponse.data.data?.let { data ->
                            var id: Long = 0
                            var extraId: String = ""
                            val a = when (data) {
                                // Laporan
                                is ElevatorSingleReportResponseData -> { id = data.laporan.extraId; extraId = data.laporan.id; data.laporan.toInspectionWithDetailsDomain() }
                                // is EscalatorSingleReportResponseData -> { id = data.laporan.extraId; extraId = data.laporan.id; data.laporan }

                                // Bap
                                is ElevatorBapSingleReportResponseData -> { id = data.bap.extraId; extraId = data.bap.id; data.bap.toInspectionWithDetailsDomain() }
                                // is EscalatorBapSingleReportResponseData -> { id = data.bap.extraId; extraId = data.bap.id }
                                else -> null
                            }
                            val dummyDomain = InspectionDomain(0, "", DocumentType.LAPORAN, InspectionType.EE, SubInspectionType.Gantry_Crane, "", "")
                            val dummyInspectionWithDetails = InspectionWithDetailsDomain(dummyDomain, emptyList(), emptyList(), emptyList())
                            saveReport(a ?: dummyInspectionWithDetails, extraId)
                            localDataSource.updateSyncStatus(id, true)
                        } ?: fail++
                    } catch (_: Exception) {
                        fail++
                    }
                }
            }*/
        }
        return fail == 0
    }

    override fun getAllReports(): Flow<List<History>> {
        return localDataSource.getAllInspectionsWithDetails().map { it ->
            it.map {
                it.inspectionEntity.toHistory()
            }
        }
    }

    override suspend fun deleteReport(id: Long) {
        val data = localDataSource.getInspection(id).firstOrNull()
        data?.let { data ->
            val extraId = data.inspectionEntity.extraId
            val moreExtraId = data.inspectionEntity.moreExtraId
            val token = userPreference.getUserToken() ?: ""
            val isSynced = data.inspectionEntity.isSynced

            if (!isSynced) {
                localDataSource.deleteInspection(id)
                return
            }

            val reportPath = when (data.inspectionEntity.subInspectionType) {
                SubInspectionType.Elevator -> ApiPaths.LAPORAN_ELEVATOR
                SubInspectionType.Escalator -> ApiPaths.LAPORAN_ESKALATOR
                SubInspectionType.Forklift -> ApiPaths.LAPORAN_FORKLIFT
                SubInspectionType.Mobile_Crane -> ApiPaths.LAPORAN_MOBILE_CRANE
                SubInspectionType.Overhead_Crane -> ApiPaths.LAPORAN_OVERHEAD_CRANE
                SubInspectionType.Gantry_Crane -> ApiPaths.LAPORAN_GANTRY_CRANE
                SubInspectionType.Gondola -> ApiPaths.LAPORAN_GONDOLA
                else -> ""
            }

            val bapPath = when (data.inspectionEntity.subInspectionType) {
                SubInspectionType.Elevator -> ApiPaths.BAP_ELEVATOR
                SubInspectionType.Escalator -> ApiPaths.BAP_ESKALATOR
                SubInspectionType.Forklift -> ApiPaths.BAP_FORKLIFT
                SubInspectionType.Mobile_Crane -> ApiPaths.BAP_MOBILE_CRANE
                SubInspectionType.Overhead_Crane -> ApiPaths.BAP_OVERHEAD_CRANE
                SubInspectionType.Gantry_Crane -> ApiPaths.BAP_GANTRY_CRANE
                SubInspectionType.Gondola -> ApiPaths.BAP_GONDOLA
                else -> ""
            }

            val apiResponse = when (data.inspectionEntity.documentType) {
                DocumentType.LAPORAN -> remoteDataSource.deleteReport(token, reportPath, extraId).first()
                DocumentType.BAP -> remoteDataSource.deleteReport(token, bapPath, moreExtraId).first()
                else -> ApiResponse.Empty
            }

            when (apiResponse) {
                is ApiResponse.Empty -> null
                is ApiResponse.Error -> null
                is ApiResponse.Success -> {
                    localDataSource.deleteInspection(id)
                }
            }
        }
    }
}
