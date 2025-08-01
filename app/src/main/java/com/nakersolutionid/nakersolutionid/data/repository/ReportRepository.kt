package com.nakersolutionid.nakersolutionid.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.nakersolutionid.nakersolutionid.BuildConfig
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.LocalDataSource
import com.nakersolutionid.nakersolutionid.data.local.database.AppDatabase
import com.nakersolutionid.nakersolutionid.data.local.mapper.toDomain
import com.nakersolutionid.nakersolutionid.data.local.mapper.toEntity
import com.nakersolutionid.nakersolutionid.data.local.mapper.toHistory
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.preference.UserPreference
import com.nakersolutionid.nakersolutionid.data.remote.RemoteDataMediator
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
import com.nakersolutionid.nakersolutionid.data.remote.dto.ml.MLData
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
import com.nakersolutionid.nakersolutionid.domain.model.DownloadInfo
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.repository.IReportRepository
import com.nakersolutionid.nakersolutionid.features.history.FilterState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ReportRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val userPreference: UserPreference,
    private val appDatabase: AppDatabase,
    private val gson: Gson
) : IReportRepository {
    override suspend fun saveReport(request: InspectionWithDetailsDomain): Long {
        val inspectionWithDetails = request.toEntity()
        return localDataSource.insertInspection(
            inspectionEntity = inspectionWithDetails.inspectionEntity,
            checkItems = inspectionWithDetails.checkItems,
            findings = inspectionWithDetails.findings,
            testResults = inspectionWithDetails.testResults
        )
    }

    override fun createReport(report: InspectionWithDetailsDomain): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        /*val report = localDataSource.getInspection(report).map { it.toDomain() }.firstOrNull()
        if (report == null) {
            emit(Resource.Error("Report not found"))
            return@flow
        }*/

        val token = "Bearer ${userPreference.getUserToken() ?: ""}"
        val path = getApiPath(report.inspection.subInspectionType, report.inspection.documentType)
        if (path.isEmpty()) {
            emit(Resource.Error("Invalid report type"))
            return@flow
        }

        val apiResponse = when (report.inspection.documentType) {
            DocumentType.LAPORAN -> when (report.inspection.subInspectionType) {
                SubInspectionType.Elevator -> remoteDataSource.createReport<ElevatorReportRequest, ElevatorSingleReportResponseData>(token, path, report.toElevatorReportRequest()).first()
                SubInspectionType.Escalator -> remoteDataSource.createReport<EscalatorReportRequest, EscalatorSingleReportResponseData>(token, path, report.toEscalatorReportRequest()).first()
                SubInspectionType.Forklift -> remoteDataSource.createReport<ForkliftReportRequest, ForkliftSingleReportResponseData>(token, path, report.toForkliftReportRequest()).first()
                SubInspectionType.Mobile_Crane -> remoteDataSource.createReport<MobileCraneReportRequest, MobileCraneSingleReportResponseData>(token, path, report.toMobileCraneReportRequest()).first()
                SubInspectionType.Overhead_Crane -> remoteDataSource.createReport<OverheadCraneReportRequest, OverheadCraneSingleReportResponseData>(token, path, report.toOverheadCraneReportRequest()).first()
                SubInspectionType.Gantry_Crane -> remoteDataSource.createReport<GantryCraneReportRequest, GantryCraneSingleReportResponseData>(token, path, report.toGantryCraneReportRequest()).first()
                SubInspectionType.Gondola -> remoteDataSource.createReport<GondolaReportRequest, GondolaSingleReportResponseData>(token, path, report.toGondolaReportRequest()).first()
                SubInspectionType.Electrical -> remoteDataSource.createReport<ElectricalReportRequest, ElectricalSingleReportResponseData>(token, path, report.toElectricalReportRequest()).first()
                SubInspectionType.Lightning_Conductor -> remoteDataSource.createReport<LightningReportRequest, LightningSingleReportResponseData>(token, path, report.toLightningReportRequest()).first()
                SubInspectionType.General_PUBT -> remoteDataSource.createReport<PubtReportRequest, PubtSingleReportResponseData>(token, path, report.toPubtReportRequest()).first()
                SubInspectionType.Fire_Protection -> remoteDataSource.createReport<IpkReportRequest, IpkSingleReportResponseData>(token, path, report.toIpkReportRequest()).first()
                SubInspectionType.Motor_Diesel -> remoteDataSource.createReport<DieselReportRequest, DieselSingleReportResponseData>(token, path, report.toDieselReportRequest()).first()
                SubInspectionType.Machine -> remoteDataSource.createReport<MachineReportRequest, MachineSingleReportResponseData>(token, path, report.toMachineReportRequest()).first()
            }
            DocumentType.BAP -> when (report.inspection.subInspectionType) {
                SubInspectionType.Elevator -> remoteDataSource.createReport<ElevatorBapRequest, ElevatorBapSingleReportResponseData>(token, path, report.toElevatorBapRequest()).first()
                SubInspectionType.Escalator -> remoteDataSource.createReport<EscalatorBapRequest, EscalatorBapSingleReportResponseData>(token, path, report.toEscalatorBapRequest()).first()
                SubInspectionType.Forklift -> remoteDataSource.createReport<ForkliftBapRequest, ForkliftBapSingleReportResponseData>(token, path, report.toForkliftBapRequest()).first()
                SubInspectionType.Mobile_Crane -> remoteDataSource.createReport<MobileCraneBapRequest, MobileCraneBapSingleReportResponseData>(token, path, report.toMobileCraneBapRequest()).first()
                SubInspectionType.Overhead_Crane -> remoteDataSource.createReport<OverheadCraneBapRequest, OverheadCraneBapSingleReportResponseData>(token, path, report.toOverheadCraneBapRequest()).first()
                SubInspectionType.Gantry_Crane -> remoteDataSource.createReport<GantryCraneBapRequest, GantryCraneBapSingleReportResponseData>(token, path, report.toGantryCraneBapRequest()).first()
                SubInspectionType.Gondola -> remoteDataSource.createReport<GondolaBapRequest, GondolaBapSingleReportResponseData>(token, path, report.toGondolaBapRequest()).first()
                SubInspectionType.Electrical -> remoteDataSource.createReport<ElectricalBapRequest, ElectricalBapSingleReportResponseData>(token, path, report.toElectricalBapRequest()).first()
                SubInspectionType.Lightning_Conductor -> remoteDataSource.createReport<LightningBapRequest, LightningBapSingleReportResponseData>(token, path, report.toLightningBapRequest()).first()
                SubInspectionType.General_PUBT -> remoteDataSource.createReport<PubtBapRequest, PubtBapSingleReportResponseData>(token, path, report.toPubtBapRequest()).first()
                SubInspectionType.Fire_Protection -> remoteDataSource.createReport<IpkBapRequest, IpkBapSingleReportResponseData>(token, path, report.toIpkBapRequest()).first()
                SubInspectionType.Motor_Diesel -> remoteDataSource.createReport<DieselBapRequest, DieselBapSingleReportResponseData>(token, path, report.toDieselBapRequest()).first()
                SubInspectionType.Machine -> remoteDataSource.createReport<MachineBapRequest, MachineBapSingleReportResponseData>(token, path, report.toMachineBapRequest()).first()
            }
            else -> null
        }

        if (apiResponse == null) {
            emit(Resource.Error("Invalid document type"))
            return@flow
        }

        when (apiResponse) {
            is ApiResponse.Empty -> {
                emit(Resource.Error("Empty response"))
                return@flow
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
                return@flow
            }
            is ApiResponse.Success -> {
                val status = handleSyncSuccess(apiResponse.data)
                if (status) {
                    emit(Resource.Success("Report created successfully"))
                    return@flow
                } else {
                    emit(Resource.Error("Failed to save report"))
                    return@flow
                }
            }
        }
    }

    override fun updateReport(report: InspectionWithDetailsDomain): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        /*val report = localDataSource.getInspection(report).map { it.toDomain() }.firstOrNull()
        if (report == null) {
            emit(Resource.Error("Report not found"))
            return@flow
        }*/

        val token = "Bearer ${userPreference.getUserToken() ?: ""}"
        val path = getApiPath(report.inspection.subInspectionType, report.inspection.documentType)
        if (path.isEmpty()) {
            emit(Resource.Error("Invalid report type"))
            return@flow
        }

        val extraId = if (report.inspection.documentType == DocumentType.BAP) report.inspection.moreExtraId else report.inspection.extraId
        val apiResponse = when (report.inspection.documentType) {
            DocumentType.LAPORAN -> when (report.inspection.subInspectionType) {
                SubInspectionType.Elevator -> remoteDataSource.updateReport<ElevatorReportRequest, ElevatorSingleReportResponseData>(token, path, extraId, report.toElevatorReportRequest()).first()
                SubInspectionType.Escalator -> remoteDataSource.updateReport<EscalatorReportRequest, EscalatorSingleReportResponseData>(token, path, extraId, report.toEscalatorReportRequest()).first()
                SubInspectionType.Forklift -> remoteDataSource.updateReport<ForkliftReportRequest, ForkliftSingleReportResponseData>(token, path, extraId, report.toForkliftReportRequest()).first()
                SubInspectionType.Mobile_Crane -> remoteDataSource.updateReport<MobileCraneReportRequest, MobileCraneSingleReportResponseData>(token, path, extraId, report.toMobileCraneReportRequest()).first()
                SubInspectionType.Overhead_Crane -> remoteDataSource.updateReport<OverheadCraneReportRequest, OverheadCraneSingleReportResponseData>(token, path, extraId, report.toOverheadCraneReportRequest()).first()
                SubInspectionType.Gantry_Crane -> remoteDataSource.updateReport<GantryCraneReportRequest, GantryCraneSingleReportResponseData>(token, path, extraId, report.toGantryCraneReportRequest()).first()
                SubInspectionType.Gondola -> remoteDataSource.updateReport<GondolaReportRequest, GondolaSingleReportResponseData>(token, path, extraId, report.toGondolaReportRequest()).first()
                SubInspectionType.Electrical -> remoteDataSource.updateReport<ElectricalReportRequest, ElectricalSingleReportResponseData>(token, path, extraId, report.toElectricalReportRequest()).first()
                SubInspectionType.Lightning_Conductor -> remoteDataSource.updateReport<LightningReportRequest, LightningSingleReportResponseData>(token, path, extraId, report.toLightningReportRequest()).first()
                SubInspectionType.General_PUBT -> remoteDataSource.updateReport<PubtReportRequest, PubtSingleReportResponseData>(token, path, extraId, report.toPubtReportRequest()).first()
                SubInspectionType.Fire_Protection -> remoteDataSource.updateReport<IpkReportRequest, IpkSingleReportResponseData>(token, path, extraId, report.toIpkReportRequest()).first()
                SubInspectionType.Motor_Diesel -> remoteDataSource.updateReport<DieselReportRequest, DieselSingleReportResponseData>(token, path, extraId, report.toDieselReportRequest()).first()
                SubInspectionType.Machine -> remoteDataSource.updateReport<MachineReportRequest, MachineSingleReportResponseData>(token, path, extraId, report.toMachineReportRequest()).first()
            }
            DocumentType.BAP -> when (report.inspection.subInspectionType) {
                SubInspectionType.Elevator -> remoteDataSource.updateReport<ElevatorBapRequest, ElevatorBapSingleReportResponseData>(token, path, extraId, report.toElevatorBapRequest()).first()
                SubInspectionType.Escalator -> remoteDataSource.updateReport<EscalatorBapRequest, EscalatorBapSingleReportResponseData>(token, path, extraId, report.toEscalatorBapRequest()).first()
                SubInspectionType.Forklift -> remoteDataSource.updateReport<ForkliftBapRequest, ForkliftBapSingleReportResponseData>(token, path, extraId, report.toForkliftBapRequest()).first()
                SubInspectionType.Mobile_Crane -> remoteDataSource.updateReport<MobileCraneBapRequest, MobileCraneBapSingleReportResponseData>(token, path, extraId, report.toMobileCraneBapRequest()).first()
                SubInspectionType.Overhead_Crane -> remoteDataSource.updateReport<OverheadCraneBapRequest, OverheadCraneBapSingleReportResponseData>(token, path, extraId, report.toOverheadCraneBapRequest()).first()
                SubInspectionType.Gantry_Crane -> remoteDataSource.updateReport<GantryCraneBapRequest, GantryCraneBapSingleReportResponseData>(token, path, extraId, report.toGantryCraneBapRequest()).first()
                SubInspectionType.Gondola -> remoteDataSource.updateReport<GondolaBapRequest, GondolaBapSingleReportResponseData>(token, path, extraId, report.toGondolaBapRequest()).first()
                SubInspectionType.Electrical -> remoteDataSource.updateReport<ElectricalBapRequest, ElectricalBapSingleReportResponseData>(token, path, extraId, report.toElectricalBapRequest()).first()
                SubInspectionType.Lightning_Conductor -> remoteDataSource.updateReport<LightningBapRequest, LightningBapSingleReportResponseData>(token, path, extraId, report.toLightningBapRequest()).first()
                SubInspectionType.General_PUBT -> remoteDataSource.updateReport<PubtBapRequest, PubtBapSingleReportResponseData>(token, path, extraId, report.toPubtBapRequest()).first()
                SubInspectionType.Fire_Protection -> remoteDataSource.updateReport<IpkBapRequest, IpkBapSingleReportResponseData>(token, path, extraId, report.toIpkBapRequest()).first()
                SubInspectionType.Motor_Diesel -> remoteDataSource.updateReport<DieselBapRequest, DieselBapSingleReportResponseData>(token, path, extraId, report.toDieselBapRequest()).first()
                SubInspectionType.Machine -> remoteDataSource.updateReport<MachineBapRequest, MachineBapSingleReportResponseData>(token, path, extraId, report.toMachineBapRequest()).first()
            }
            else -> null
        }

        if (apiResponse == null) {
            emit(Resource.Error("Invalid document type"))
            return@flow
        }

        when (apiResponse) {
            is ApiResponse.Empty -> {
                emit(Resource.Error("Empty response"))
                return@flow
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
                return@flow
            }
            is ApiResponse.Success -> {
                val status = handleSyncSuccess(apiResponse.data)
                if (status) {
                    emit(Resource.Success("Report created successfully"))
                    return@flow
                } else {
                    emit(Resource.Error("Failed to save report"))
                    return@flow
                }
            }
        }
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

    override fun getApiPath(subInspectionType: SubInspectionType, documentType: DocumentType): String {
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

    override fun getMLResult(inspection: InspectionWithDetailsDomain): Flow<Resource<MLData>> = flow {
        emit(Resource.Loading())

        val secret = BuildConfig.ML_API
        val jsonElement: JsonElement = when (inspection.inspection.subInspectionType) {
            SubInspectionType.Elevator -> gson.toJsonTree(inspection.toElevatorReportRequest())
            SubInspectionType.Escalator -> gson.toJsonTree(inspection.toEscalatorReportRequest())
            SubInspectionType.Forklift -> gson.toJsonTree(inspection.toForkliftReportRequest())
            SubInspectionType.Mobile_Crane -> gson.toJsonTree(inspection.toMobileCraneReportRequest())
            SubInspectionType.Overhead_Crane -> gson.toJsonTree(inspection.toOverheadCraneReportRequest())
            SubInspectionType.Gantry_Crane -> gson.toJsonTree(inspection.toGantryCraneReportRequest())
            SubInspectionType.Gondola -> gson.toJsonTree(inspection.toGondolaReportRequest())
            SubInspectionType.Electrical -> gson.toJsonTree(inspection.toElectricalReportRequest())
            SubInspectionType.Lightning_Conductor -> gson.toJsonTree(inspection.toLightningReportRequest())
            SubInspectionType.General_PUBT -> gson.toJsonTree(inspection.toPubtReportRequest())
            SubInspectionType.Fire_Protection -> gson.toJsonTree(inspection.toIpkReportRequest())
            SubInspectionType.Motor_Diesel -> gson.toJsonTree(inspection.toDieselReportRequest())
            SubInspectionType.Machine -> gson.toJsonTree(inspection.toMachineReportRequest())
        }

        val newKey = "subInspectionType"
        val newValue = inspection.inspection.subInspectionType.toDisplayString()

        jsonElement.asJsonObject.addProperty(newKey, newValue)

        val response = remoteDataSource.getMLResult(secret, jsonElement)
        when (response) {
            is ApiResponse.Empty -> null
            is ApiResponse.Error -> emit(Resource.Error(response.errorMessage))
            is ApiResponse.Success -> emit(Resource.Success(response.data))
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

    private suspend fun handleSyncSuccess(data: Any): Boolean {
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
                    Log.d("ReportRepository", "Here: Mobile Crane")
                    val domain = innerData.bap.toInspectionWithDetailsDomain()
                    Log.d("ReportRepository", "Here: ${domain.inspection.subInspectionType.toDisplayString()}")
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

            return if (responseType != null) {
                saveReport(responseType)
                true
            } else {
                false
            }
        } catch (_: Exception) {
            return false
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
            val isEdited = report.inspection.isEdited
            when (val apiResponse = action(token, path, id, report)) {
                is ApiResponse.Success -> handleSyncSuccess(apiResponse.data) { fail++ }
                is ApiResponse.Error -> fail++
                is ApiResponse.Empty -> Unit
            }
        }
        return fail == 0
    }

    override suspend fun syncInspection(): Boolean {
        val listReports = localDataSource.getPendingSyncReports().map { it.toDomain() }
        if (listReports.isEmpty()) return true
        val token = "Bearer ${userPreference.getUserToken() ?: ""}"
        return processReports(listReports, token) { t, p, i, r ->
            var isInCloud = i.isNotEmpty()
            val isSynced = r.inspection.isSynced
            val isEdited = r.inspection.isEdited

            if (!isSynced) isInCloud = false

            if (isInCloud && isEdited) {
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
            } else {
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
    }

    override suspend fun syncUpdateInspection(): Boolean {
        /*val listReports = localDataSource.getPendingSyncReports().map { it.toDomain() }
        if (listReports.isEmpty()) return true
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
        }*/
        return true
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllReports(query: String, filters: FilterState): Flow<PagingData<History>> {
        // Determine if any search or filter is currently active.
        val sanitizedQuery = sanitizeSearchQuery(query)
        val isSearchOrFilterActive = sanitizedQuery != null || filters.documentType != null || filters.inspectionType != null || filters.subInspectionType != null

        return Pager(
            config = PagingConfig(pageSize = 5),

            // Use the RemoteMediator ONLY when the user is not searching.
            remoteMediator = if (isSearchOrFilterActive) null else RemoteDataMediator(
                userPreference = userPreference,
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource,
                appDatabase = appDatabase,
                gson = gson
            ),
            pagingSourceFactory = {
                // This PagingSource will now be the sole source during a search.
                localDataSource.searchAllInspectionsPaged(sanitizeSearchQuery(query), filters)
            }
        ).flow.map { paging -> paging.map { it.inspectionEntity.toHistory() } }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllReports(
        query: String,
        filters: FilterState,
        fromBapScreen: Boolean
    ): Flow<PagingData<History>> {
        // Determine if any search or filter is currently active.
        val sanitizedQuery = sanitizeSearchQuery(query)
        val isSearchOrFilterActive = sanitizedQuery != null || filters.inspectionType != null || filters.subInspectionType != null

        return Pager(
            config = PagingConfig(pageSize = 5, prefetchDistance = 1),

            // Use the RemoteMediator ONLY when the user is not searching.
            remoteMediator = if (isSearchOrFilterActive) null else RemoteDataMediator(
                userPreference = userPreference,
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource,
                appDatabase = appDatabase,
                gson = gson
            ),
            pagingSourceFactory = {
                // This PagingSource will now be the sole source during a search.
                localDataSource.searchAllInspectionsPaged(sanitizeSearchQuery(query), filters)
            }
        ).flow.map { paging -> paging.map { it.inspectionEntity.toHistory() } }
    }

    override fun getDownloadedReports(): Flow<List<History>> =
        localDataSource.getDownloadedInspectionsWithDetails().map { list -> list.map { it.inspectionEntity.toHistory() } }

    override suspend fun deleteReport(id: Long) {
        val data = localDataSource.getInspection(id).map { it.toDomain() }.firstOrNull() ?: return
        if (!data.inspection.isSynced) {
            localDataSource.deleteInspection(id)
            return
        }
        val token = "Bearer ${userPreference.getUserToken() ?: ""}"
        val path = getApiPath(data.inspection.subInspectionType, data.inspection.documentType)
        if (path.isEmpty()) return
        val extraId = if (data.inspection.documentType == DocumentType.BAP) data.inspection.moreExtraId else data.inspection.extraId
        val apiResponse = remoteDataSource.deleteReport(token, path, extraId).first()
        when (apiResponse) {
            ApiResponse.Empty -> null
            is ApiResponse.Error -> {
                localDataSource.updateSyncStatus(id, false)
                throw Exception(apiResponse.errorMessage)
            }
            is ApiResponse.Success -> localDataSource.deleteInspection(id)
        }
    }

    override suspend fun getDownloadInfo(id: Long): Resource<DownloadInfo> {
        val data = localDataSource.getInspection(id).map { it.toDomain() }.firstOrNull()
        if (data == null) {
            return Resource.Error("Data not found")
        }

        val path = getApiPath(data.inspection.subInspectionType, data.inspection.documentType)
        if (path.isEmpty()) {
            return Resource.Error("Path not found")
        }

        val token = "Bearer ${userPreference.getUserToken() ?: ""}"
        val extraId = if (data.inspection.documentType == DocumentType.BAP) data.inspection.moreExtraId else data.inspection.extraId

        return Resource.Success(DownloadInfo(path, token, extraId))
    }

    override suspend fun updateDownloadedStatus(id: Long, isDownloaded: Boolean, filePath: String) = localDataSource.updateDownloadStatus(id, isDownloaded, filePath)

    private fun sanitizeSearchQuery(query: String): String? {
        if (query.isBlank()) return null

        return query
            .trim()
            .lowercase()
            .replace(Regex("""["'`^|*()<>]"""), " ")  // remove risky FTS chars
            .split(Regex("\\s+"))                    // split by whitespace
            .joinToString(" ") { "$it*" }            // add wildcard per token
    }
}