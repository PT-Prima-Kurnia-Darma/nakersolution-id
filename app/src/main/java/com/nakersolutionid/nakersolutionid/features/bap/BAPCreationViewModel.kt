package com.nakersolutionid.nakersolutionid.features.bap

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.bap.electric.ElectricalInstallationBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.electric.ElectricalInstallationBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.electric.toElectricalInstallationBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.electric.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.elevator.ElevatorBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.elevator.ElevatorBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.elevator.toElevatorBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.elevator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.escalator.EscalatorBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.escalator.EscalatorBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.escalator.toEscalatorBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.escalator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.fireprotection.FireProtectionBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.fireprotection.FireProtectionBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.fireprotection.toFireProtectionBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.fireprotection.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.forklift.ForkliftBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.forklift.ForkliftBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.forklift.toForkliftBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.forklift.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.gantrycrane.GantryCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.gantrycrane.GantryCraneBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.gantrycrane.toGantryCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.gantrycrane.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.gondola.GondolaBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.gondola.GondolaBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.gondola.toGondolaBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.gondola.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.lightning.LightningBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.lightning.LightningBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.lightning.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.lightning.toLightningBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.mobilecrane.MobileCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.mobilecrane.MobileCraneBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.mobilecrane.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.mobilecrane.toMobileCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.overheadcrane.OverheadCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.overheadcrane.OverheadCraneBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.overheadcrane.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.overheadcrane.toOverheadCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.ptp.PtpBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.ptp.PtpBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.ptp.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.ptp.toPtpBAPCreationReport
import com.nakersolutionid.nakersolutionid.features.bap.pubt.PubtBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.pubt.PubtBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.pubt.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.pubt.toPubtBAPReport
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import com.nakersolutionid.nakersolutionid.workers.SyncManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BAPCreationViewModel(
    private val reportUseCase: ReportUseCase,
    private val syncManager: SyncManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(BAPCreationUiState())
    val uiState: StateFlow<BAPCreationUiState> = _uiState.asStateFlow()

    private val _elevatorBAPUiState = MutableStateFlow(ElevatorBAPUiState())
    val elevatorBAPUiState: StateFlow<ElevatorBAPUiState> = _elevatorBAPUiState.asStateFlow()

    private val _escalatorBAPUiState = MutableStateFlow(EscalatorBAPUiState())
    val escalatorBAPUiState: StateFlow<EscalatorBAPUiState> = _escalatorBAPUiState.asStateFlow()

    private val _forkliftBAPUiState = MutableStateFlow(ForkliftBAPUiState())
    val forkliftBAPUiState: StateFlow<ForkliftBAPUiState> = _forkliftBAPUiState.asStateFlow()

    private val _gantryCraneBAPUiState = MutableStateFlow(GantryCraneBAPUiState())
    val gantryCraneBAPUiState: StateFlow<GantryCraneBAPUiState> = _gantryCraneBAPUiState.asStateFlow()

    private val _gondolaBAPUiState = MutableStateFlow(GondolaBAPUiState())
    val gondolaBAPUiState: StateFlow<GondolaBAPUiState> = _gondolaBAPUiState.asStateFlow()

    private val _mobileCraneBAPUiState = MutableStateFlow(MobileCraneBAPUiState())
    val mobileCraneBAPUiState: StateFlow<MobileCraneBAPUiState> = _mobileCraneBAPUiState.asStateFlow()

    // New states
    private val _lightningBAPUiState = MutableStateFlow(LightningBAPUiState())
    val lightningBAPUiState: StateFlow<LightningBAPUiState> = _lightningBAPUiState.asStateFlow()

    private val _electricalBAPUiState = MutableStateFlow(ElectricalInstallationBAPUiState())
    val electricalBAPUiState: StateFlow<ElectricalInstallationBAPUiState> = _electricalBAPUiState.asStateFlow()

    private val _pubtBAPUiState = MutableStateFlow(PubtBAPUiState())
    val pubtBAPUiState: StateFlow<PubtBAPUiState> = _pubtBAPUiState.asStateFlow()

    private val _ptpBAPUiState = MutableStateFlow(PtpBAPUiState())
    val ptpBAPUiState: StateFlow<PtpBAPUiState> = _ptpBAPUiState.asStateFlow()

    private val _fireProtectionBAPUiState = MutableStateFlow(FireProtectionBAPUiState())
    val fireProtectionBAPUiState: StateFlow<FireProtectionBAPUiState> = _fireProtectionBAPUiState.asStateFlow()

    private val _overheadCraneBAPUiState = MutableStateFlow(OverheadCraneBAPUiState())
    val overheadCraneBAPUiState: StateFlow<OverheadCraneBAPUiState> = _overheadCraneBAPUiState.asStateFlow()

    private var currentReportId: Long? = null
    private var cloudReportId: String? = null

    fun getInspectionDetail(id: Long) {
        viewModelScope.launch {
            onUpdateState { it.copy(isLoading = true) }
            val inspection = reportUseCase.getInspection(id)
            if (inspection != null) {
                currentReportId = id
                cloudReportId = inspection.inspection.extraId
                // Load appropriate data based on subInspectionType
                when (inspection.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> {
                        val data = inspection.toElevatorBAPReport()
                        onUpdateElevatorBAPState(data)
                    }
                    SubInspectionType.Escalator -> {
                        val data = inspection.toEscalatorBAPReport()
                        onUpdateEscalatorBAPState(data)
                    }
                    SubInspectionType.Forklift -> {
                        val data = inspection.toForkliftBAPReport()
                        onUpdateForkliftBAPState(data)
                    }
                    SubInspectionType.Gantry_Crane -> {
                        val data = inspection.toGantryCraneBAPReport()
                        onUpdateGantryCraneBAPState(data)
                    }
                    SubInspectionType.Gondola -> {
                        val data = inspection.toGondolaBAPReport()
                        onUpdateGondolaBAPState(data)
                    }
                    SubInspectionType.Mobile_Crane -> {
                        val data = inspection.toMobileCraneBAPReport()
                        onUpdateMobileCraneBAPState(data)
                    }
                    SubInspectionType.Overhead_Crane -> {
                        val data = inspection.toOverheadCraneBAPReport()
                        onUpdateOverheadCraneBAPState(data)
                    }
                    SubInspectionType.Electrical -> {
                        val data = inspection.toElectricalInstallationBAPReport()
                        onUpdateElectricalBAPState(data)
                    }
                    SubInspectionType.Lightning_Conductor -> {
                        val data = inspection.toLightningBAPReport()
                        onUpdateLightningBAPState(data)
                    }
                    SubInspectionType.General_PUBT -> {
                        val data = inspection.toPubtBAPReport()
                        onUpdatePubtBAPState(data)
                    }
                    SubInspectionType.Fire_Protection -> {
                        val data = inspection.toFireProtectionBAPReport()
                        onUpdateFireProtectionBAPState(data)
                    }
                    SubInspectionType.Motor_Diesel -> {
                        val data = inspection.toPtpBAPCreationReport().report
                        onUpdatePtpBAPState(data)
                    }
                    SubInspectionType.Machine -> {
                        val data = inspection.toPtpBAPCreationReport().report
                        onUpdatePtpBAPState(data)
                    }
                }
            }
            onUpdateState { it.copy(isLoading = false) }
        }
    }

    fun onSaveClick(selectedIndex: SubInspectionType, id: Long? = null) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Elevator -> {
                    val inspection = _elevatorBAPUiState.value.elevatorBAPReport.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Escalator -> {
                    val inspection = _escalatorBAPUiState.value.escalatorBAPReport.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Forklift -> {
                    val inspection = _forkliftBAPUiState.value.forkliftBAPReport.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Gantry_Crane -> {
                    val inspection = _gantryCraneBAPUiState.value.gantryCraneBAPReport.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Gondola -> {
                    val inspection = _gondolaBAPUiState.value.gondolaBAPReport.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Mobile_Crane -> {
                    val inspection = _mobileCraneBAPUiState.value.mobileCraneBAPReport.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Overhead_Crane -> {
                    val inspection = _overheadCraneBAPUiState.value.report.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Electrical -> {
                    val inspection = _electricalBAPUiState.value.report.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Lightning_Conductor -> {
                    val inspection = _lightningBAPUiState.value.report.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.General_PUBT -> {
                    val inspection = _pubtBAPUiState.value.report.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Fire_Protection -> {
                    val inspection = _fireProtectionBAPUiState.value.report.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Motor_Diesel -> {
                    val inspection = _ptpBAPUiState.value.report.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
                SubInspectionType.Machine -> {
                    val inspection = _ptpBAPUiState.value.report.toInspectionWithDetailsDomain(currentTime, id)
                    saveReport(inspection)
                }
            }
        }
    }

    private suspend fun saveReport(inspection: com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain) {
        try {
            reportUseCase.saveReport(inspection)
            _uiState.update { it.copy(result = Resource.Success("Laporan berhasil disimpan")) }
            startSync()
        } catch(_: SQLiteConstraintException) {
            _uiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        } catch (_: Exception) {
            _uiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    fun onUpdateElevatorBAPState(newData: ElevatorBAPReport) {
        _elevatorBAPUiState.update { it.copy(elevatorBAPReport = newData) }
    }

    fun onUpdateEscalatorBAPState(newData: EscalatorBAPReport) {
        _escalatorBAPUiState.update { it.copy(escalatorBAPReport = newData) }
    }

    fun onUpdateForkliftBAPState(newData: ForkliftBAPReport) {
        _forkliftBAPUiState.update { it.copy(forkliftBAPReport = newData) }
    }

    fun onUpdateGantryCraneBAPState(newData: GantryCraneBAPReport) {
        _gantryCraneBAPUiState.update { it.copy(gantryCraneBAPReport = newData) }
    }

    fun onUpdateGondolaBAPState(newData: GondolaBAPReport) {
        _gondolaBAPUiState.update { it.copy(gondolaBAPReport = newData) }
    }

    fun onUpdateMobileCraneBAPState(newData: MobileCraneBAPReport) {
        _mobileCraneBAPUiState.update { it.copy(mobileCraneBAPReport = newData) }
    }

    // New update functions
    fun onUpdateLightningBAPState(newData: LightningBAPReport) {
        _lightningBAPUiState.update { it.copy(report = newData) }
    }

    fun onUpdateElectricalBAPState(newData: ElectricalInstallationBAPReport) {
        _electricalBAPUiState.update { it.copy(report = newData) }
    }

    fun onUpdatePubtBAPState(newData: PubtBAPReport) {
        _pubtBAPUiState.update { it.copy(report = newData) }
    }

    fun onUpdatePtpBAPState(newData: PtpBAPReport) {
        _ptpBAPUiState.update { it.copy(report = newData) }
    }

    fun onUpdateFireProtectionBAPState(newData: FireProtectionBAPReport) {
        _fireProtectionBAPUiState.update { it.copy(report = newData) }
    }

    fun onUpdateOverheadCraneBAPState(newData: OverheadCraneBAPReport) {
        _overheadCraneBAPUiState.update { it.copy(report = newData) }
    }

    fun onUpdateState(updater: (BAPCreationUiState) -> BAPCreationUiState) {
        _uiState.update(updater)
    }

    fun startSync() {
        if (_uiState.value.editMode) {
            syncManager.startSyncUpdate()
            onUpdateState { it.copy(editMode = false) }
        } else {
            syncManager.startSync()
            onUpdateState { it.copy(editMode = false) }
        }
    }
}