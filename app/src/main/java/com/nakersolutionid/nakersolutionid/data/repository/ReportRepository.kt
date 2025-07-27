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
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.ElevatorSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane.GantryCraneBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane.GantryCraneBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane.GantryCraneReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane.GantryCraneSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.gondola.GondolaBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.gondola.GondolaBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.gondola.GondolaReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.gondola.GondolaSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.overheadcrane.OverheadCraneBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.overheadcrane.OverheadCraneBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.overheadcrane.OverheadCraneReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.overheadcrane.OverheadCraneSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.pubt.PubtBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.pubt.PubtBapSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.dto.pubt.PubtReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.pubt.PubtSingleReportResponseData
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toDieselBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toDieselReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toElectricalBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toElectricalReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toElevatorBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toElevatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toEscalatorBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toEscalatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toForkliftBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toForkliftReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toGantryCraneBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toGantryCraneReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toGondolaBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toGondolaReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toIpkBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toIpkReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toLightningBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toLightningReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toMachineBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toMachineReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toMobileCraneBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toMobileCraneReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toOverheadCraneBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toOverheadCraneReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toPubtBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.mapper.toPubtReportRequest
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
            SubInspectionType.Gondola to ApiPaths.LAPORAN_GONDOLA,
            SubInspectionType.Electrical to ApiPaths.LAPORAN_LISTRIK,
            SubInspectionType.Lightning_Conductor to ApiPaths.LAPORAN_PETIR,
            SubInspectionType.Fire_Protection to ApiPaths.LAPORAN_PROTEKSI_KEBAKARAN,
            SubInspectionType.Machine to ApiPaths.LAPORAN_MESIN,
            SubInspectionType.Motor_Diesel to ApiPaths.LAPORAN_MOTOR_DIESEL,
            SubInspectionType.General_PUBT to ApiPaths.LAPORAN_PUBT,
        )

        val bapPathMap = mapOf(
            SubInspectionType.Elevator to ApiPaths.BAP_ELEVATOR,
            SubInspectionType.Escalator to ApiPaths.BAP_ESKALATOR,
            SubInspectionType.Forklift to ApiPaths.BAP_FORKLIFT,
            SubInspectionType.Mobile_Crane to ApiPaths.BAP_MOBILE_CRANE,
            SubInspectionType.Overhead_Crane to ApiPaths.BAP_OVERHEAD_CRANE,
            SubInspectionType.Gantry_Crane to ApiPaths.BAP_GANTRY_CRANE,
            SubInspectionType.Gondola to ApiPaths.BAP_GONDOLA,
            SubInspectionType.Electrical to ApiPaths.BAP_LISTRIK,
            SubInspectionType.Lightning_Conductor to ApiPaths.BAP_PETIR,
            SubInspectionType.Fire_Protection to ApiPaths.BAP_PROTEKSI_KEBAKARAN,
            SubInspectionType.Machine to ApiPaths.BAP_MESIN,
            SubInspectionType.Motor_Diesel to ApiPaths.BAP_MOTOR_DIESEL,
            SubInspectionType.General_PUBT to ApiPaths.BAP_PUBT,
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
                is ForkliftSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is MobileCraneSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is OverheadCraneSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is GantryCraneSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is GondolaSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is ElectricalSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is LightningSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is PubtSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is IpkSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is DieselSingleReportResponseData -> {
                    val domain = innerData.laporan.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.laporan.extraId, extraId = innerData.laporan.id))
                }
                is MachineSingleReportResponseData -> {
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
                is ForkliftBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is MobileCraneBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is OverheadCraneBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.reportHeader.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is GantryCraneBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is GondolaBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is ElectricalBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is LightningBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is PubtBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is IpkBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is DieselBapSingleReportResponseData -> {
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    domain.copy(inspection = domain.inspection.copy(id = innerData.bap.extraId, extraId = innerData.bap.laporanId, moreExtraId = innerData.bap.id))
                }
                is MachineBapSingleReportResponseData -> {
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
                    SubInspectionType.Forklift -> remoteDataSource.createReport<ForkliftReportRequest, ForkliftSingleReportResponseData>(t, p, r.toForkliftReportRequest()).first()
                    SubInspectionType.Mobile_Crane -> remoteDataSource.createReport<MobileCraneReportRequest, MobileCraneSingleReportResponseData>(t, p, r.toMobileCraneReportRequest()).first()
                    SubInspectionType.Overhead_Crane -> remoteDataSource.createReport<OverheadCraneReportRequest, OverheadCraneSingleReportResponseData>(t, p, r.toOverheadCraneReportRequest()).first()
                    SubInspectionType.Gantry_Crane -> remoteDataSource.createReport<GantryCraneReportRequest, GantryCraneSingleReportResponseData>(t, p, r.toGantryCraneReportRequest()).first()
                    SubInspectionType.Gondola -> remoteDataSource.createReport<GondolaReportRequest, GondolaSingleReportResponseData>(t, p, r.toGondolaReportRequest()).first()
                    SubInspectionType.Electrical -> remoteDataSource.createReport<ElectricalReportRequest, ElectricalSingleReportResponseData>(t, p, r.toElectricalReportRequest()).first()
                    SubInspectionType.Lightning_Conductor -> remoteDataSource.createReport<LightningReportRequest, LightningSingleReportResponseData>(t, p, r.toLightningReportRequest()).first()
                    SubInspectionType.General_PUBT -> remoteDataSource.createReport<PubtReportRequest, PubtSingleReportResponseData>(t, p, r.toPubtReportRequest()).first()
                    SubInspectionType.Fire_Protection -> remoteDataSource.createReport<IpkReportRequest, IpkSingleReportResponseData>(t, p, r.toIpkReportRequest()).first()
                    SubInspectionType.Motor_Diesel -> remoteDataSource.createReport<DieselReportRequest, DieselSingleReportResponseData>(t, p, r.toDieselReportRequest()).first()
                    SubInspectionType.Machine -> remoteDataSource.createReport<MachineReportRequest, MachineSingleReportResponseData>(t, p, r.toMachineReportRequest()).first()
                }
                DocumentType.BAP -> when (r.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.createReport<ElevatorBapRequest, ElevatorBapSingleReportResponseData>(t, p, r.toElevatorBapRequest()).first()
                    SubInspectionType.Escalator -> remoteDataSource.createReport<EscalatorBapRequest, EscalatorBapSingleReportResponseData>(t, p, r.toEscalatorBapRequest()).first()
                    SubInspectionType.Forklift -> remoteDataSource.createReport<ForkliftBapRequest, ForkliftBapSingleReportResponseData>(t, p, r.toForkliftBapRequest()).first()
                    SubInspectionType.Mobile_Crane -> remoteDataSource.createReport<MobileCraneBapRequest, MobileCraneBapSingleReportResponseData>(t, p, r.toMobileCraneBapRequest()).first()
                    SubInspectionType.Overhead_Crane -> remoteDataSource.createReport<OverheadCraneBapRequest, OverheadCraneBapSingleReportResponseData>(t, p, r.toOverheadCraneBapRequest()).first()
                    SubInspectionType.Gantry_Crane -> remoteDataSource.createReport<GantryCraneBapRequest, GantryCraneBapSingleReportResponseData>(t, p, r.toGantryCraneBapRequest()).first()
                    SubInspectionType.Gondola -> remoteDataSource.createReport<GondolaBapRequest, GondolaBapSingleReportResponseData>(t, p, r.toGondolaBapRequest()).first()
                    SubInspectionType.Electrical -> remoteDataSource.createReport<ElectricalBapRequest, ElectricalBapSingleReportResponseData>(t, p, r.toElectricalBapRequest()).first()
                    SubInspectionType.Lightning_Conductor -> remoteDataSource.createReport<LightningBapRequest, LightningBapSingleReportResponseData>(t, p, r.toLightningBapRequest()).first()
                    SubInspectionType.General_PUBT -> remoteDataSource.createReport<PubtBapRequest, PubtBapSingleReportResponseData>(t, p, r.toPubtBapRequest()).first()
                    SubInspectionType.Fire_Protection -> remoteDataSource.createReport<IpkBapRequest, IpkBapSingleReportResponseData>(t, p, r.toIpkBapRequest()).first()
                    SubInspectionType.Motor_Diesel -> remoteDataSource.createReport<DieselBapRequest, DieselBapSingleReportResponseData>(t, p, r.toDieselBapRequest()).first()
                    SubInspectionType.Machine -> remoteDataSource.createReport<MachineBapRequest, MachineBapSingleReportResponseData>(t, p, r.toMachineBapRequest()).first()
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
                    SubInspectionType.Forklift -> remoteDataSource.updateReport<ForkliftReportRequest, ForkliftSingleReportResponseData>(t, p, i, r.toForkliftReportRequest()).first()
                    SubInspectionType.Mobile_Crane -> remoteDataSource.updateReport<MobileCraneReportRequest, MobileCraneSingleReportResponseData>(t, p, i, r.toMobileCraneReportRequest()).first()
                    SubInspectionType.Overhead_Crane -> remoteDataSource.updateReport<OverheadCraneReportRequest, OverheadCraneSingleReportResponseData>(t, p, i, r.toOverheadCraneReportRequest()).first()
                    SubInspectionType.Gantry_Crane -> remoteDataSource.updateReport<GantryCraneReportRequest, GantryCraneSingleReportResponseData>(t, p, i, r.toGantryCraneReportRequest()).first()
                    SubInspectionType.Gondola -> remoteDataSource.updateReport<GondolaReportRequest, GondolaSingleReportResponseData>(t, p, i, r.toGondolaReportRequest()).first()
                    SubInspectionType.Electrical -> remoteDataSource.updateReport<ElectricalReportRequest, ElectricalSingleReportResponseData>(t, p, i, r.toElectricalReportRequest()).first()
                    SubInspectionType.Lightning_Conductor -> remoteDataSource.updateReport<LightningReportRequest, LightningSingleReportResponseData>(t, p, i, r.toLightningReportRequest()).first()
                    SubInspectionType.General_PUBT -> remoteDataSource.updateReport<PubtReportRequest, PubtSingleReportResponseData>(t, p, i, r.toPubtReportRequest()).first()
                    SubInspectionType.Fire_Protection -> remoteDataSource.updateReport<IpkReportRequest, IpkSingleReportResponseData>(t, p, i, r.toIpkReportRequest()).first()
                    SubInspectionType.Motor_Diesel -> remoteDataSource.updateReport<DieselReportRequest, DieselSingleReportResponseData>(t, p, i, r.toDieselReportRequest()).first()
                    SubInspectionType.Machine -> remoteDataSource.updateReport<MachineReportRequest, MachineSingleReportResponseData>(t, p, i, r.toMachineReportRequest()).first()
                }
                DocumentType.BAP -> when (r.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> remoteDataSource.updateReport<ElevatorBapRequest, ElevatorBapSingleReportResponseData>(t, p, i, r.toElevatorBapRequest()).first()
                    SubInspectionType.Escalator -> remoteDataSource.updateReport<EscalatorBapRequest, EscalatorBapSingleReportResponseData>(t, p, i, r.toEscalatorBapRequest()).first()
                    SubInspectionType.Forklift -> remoteDataSource.updateReport<ForkliftBapRequest, ForkliftBapSingleReportResponseData>(t, p, i, r.toForkliftBapRequest()).first()
                    SubInspectionType.Mobile_Crane -> remoteDataSource.updateReport<MobileCraneBapRequest, MobileCraneBapSingleReportResponseData>(t, p, i, r.toMobileCraneBapRequest()).first()
                    SubInspectionType.Overhead_Crane -> remoteDataSource.updateReport<OverheadCraneBapRequest, OverheadCraneBapSingleReportResponseData>(t, p, i, r.toOverheadCraneBapRequest()).first()
                    SubInspectionType.Gantry_Crane -> remoteDataSource.updateReport<GantryCraneBapRequest, GantryCraneBapSingleReportResponseData>(t, p, i, r.toGantryCraneBapRequest()).first()
                    SubInspectionType.Gondola -> remoteDataSource.updateReport<GondolaBapRequest, GondolaBapSingleReportResponseData>(t, p, i, r.toGondolaBapRequest()).first()
                    SubInspectionType.Electrical -> remoteDataSource.updateReport<ElectricalBapRequest, ElectricalBapSingleReportResponseData>(t, p, i, r.toElectricalBapRequest()).first()
                    SubInspectionType.Lightning_Conductor -> remoteDataSource.updateReport<LightningBapRequest, LightningBapSingleReportResponseData>(t, p, i, r.toLightningBapRequest()).first()
                    SubInspectionType.General_PUBT -> remoteDataSource.updateReport<PubtBapRequest, PubtBapSingleReportResponseData>(t, p, i, r.toPubtBapRequest()).first()
                    SubInspectionType.Fire_Protection -> remoteDataSource.updateReport<IpkBapRequest, IpkBapSingleReportResponseData>(t, p, i, r.toIpkBapRequest()).first()
                    SubInspectionType.Motor_Diesel -> remoteDataSource.updateReport<DieselBapRequest, DieselBapSingleReportResponseData>(t, p, i, r.toDieselBapRequest()).first()
                    SubInspectionType.Machine -> remoteDataSource.updateReport<MachineBapRequest, MachineBapSingleReportResponseData>(t, p, i, r.toMachineBapRequest()).first()
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