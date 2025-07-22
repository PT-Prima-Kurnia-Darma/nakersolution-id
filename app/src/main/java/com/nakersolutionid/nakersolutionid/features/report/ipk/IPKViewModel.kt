package com.nakersolutionid.nakersolutionid.features.report.ipk

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionHydrantOperationalTestItem
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionPumpFunctionTestItem
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionUiState
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.toFireProtectionUiState
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Dummy
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import com.nakersolutionid.nakersolutionid.workers.SyncManager
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IPKViewModel(
    private val reportUseCase: ReportUseCase,
    private val syncManager: SyncManager
) : ViewModel() {
    private val _ipkUiState = MutableStateFlow(IPKUiState())
    val ipkUiState: StateFlow<IPKUiState> = _ipkUiState.asStateFlow()

    private val _fireProtectionUiState = MutableStateFlow(Dummy.getDummyFireProtectionUiState())
    val fireProtectionUiState: StateFlow<FireProtectionUiState> = _fireProtectionUiState.asStateFlow()

    // Store the current report ID for editing
    private var currentReportId: Long? = null

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Fire_Protection -> {
                    val electricalInspection = _fireProtectionUiState.value.toInspectionWithDetailsDomain(currentTime, currentReportId)
                    try {
                        reportUseCase.saveReport(electricalInspection)
                        _ipkUiState.update { it.copy(fireProtectionResult = Resource.Success("Laporan berhasil disimpan")) }
                        startSync()
                    } catch(e: SQLiteConstraintException) {
                        _ipkUiState.update { it.copy(fireProtectionResult = Resource.Error("Laporan gagal disimpan")) }
                    } catch (e: Exception) {
                        _ipkUiState.update { it.copy(fireProtectionResult = Resource.Error("Laporan gagal disimpan")) }
                    }
                }
                else -> {}
            }
        }
    }

    fun onIPKUpdateState(updater: (IPKUiState) -> IPKUiState) {
        _ipkUiState.update(updater)
    }

    fun onFireProtectionReportChange(newReport: FireProtectionInspectionReport) {
        _fireProtectionUiState.update { it.copy(inspectionReport = newReport) }
    }

    fun addPumpFunctionTestItem(item: FireProtectionPumpFunctionTestItem) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val newItems = (report.pumpFunctionTest + item).toImmutableList()
        onFireProtectionReportChange(report.copy(pumpFunctionTest = newItems))
    }

    fun deletePumpFunctionTestItem(index: Int) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val newItems = report.pumpFunctionTest.toMutableList().apply { removeAt(index) }.toImmutableList()
        onFireProtectionReportChange(report.copy(pumpFunctionTest = newItems))
    }

    fun addHydrantOperationalTestItem(item: FireProtectionHydrantOperationalTestItem) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val newItems = (report.hydrantOperationalTest + item).toImmutableList()
        onFireProtectionReportChange(report.copy(hydrantOperationalTest = newItems))
    }

    fun deleteHydrantOperationalTestItem(index: Int) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val newItems = report.hydrantOperationalTest.toMutableList().apply { removeAt(index) }.toImmutableList()
        onFireProtectionReportChange(report.copy(hydrantOperationalTest = newItems))
    }

    fun addConclusionRecommendation(item: String) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onFireProtectionReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeConclusionRecommendation(index: Int) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onFireProtectionReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    /**
     * Load an existing report for editing.
     * @param reportId The ID of the report to load for editing
     */
    fun loadReportForEdit(reportId: Long) {
        viewModelScope.launch {
            try {
                _ipkUiState.update { it.copy(isLoading = true) }
                val inspection = reportUseCase.getInspection(reportId)
                
                if (inspection != null) {
                    // Store the report ID for editing
                    currentReportId = reportId
                    
                    // Extract the equipment type from the loaded inspection
                    val equipmentType = inspection.inspection.subInspectionType

                    when (equipmentType) {
                        SubInspectionType.Fire_Protection -> _fireProtectionUiState.update { inspection.toFireProtectionUiState() }
                        else -> {}
                    }

                    _ipkUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Success("Data laporan berhasil dimuat untuk diedit"),
                            loadedEquipmentType = equipmentType
                        )
                    }
                } else {
                    _ipkUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Error("Laporan tidak ditemukan")
                        )
                    }
                }
            } catch (e: Exception) {
                _ipkUiState.update { 
                    it.copy(
                        isLoading = false,
                        editLoadResult = Resource.Error("Gagal memuat data laporan: ${e.message}")
                    )
                }
            }
        }
    }

    fun startSync() {
        if (_ipkUiState.value.loadedEquipmentType != null) {
            Log.d("IPKViewModel", "Starting sync update")
            syncManager.startSyncUpdate()
            _ipkUiState.update { it.copy(loadedEquipmentType = null) }
        } else {
            Log.d("IPKViewModel", "Starting sync")
            syncManager.startSync()
            _ipkUiState.update { it.copy(loadedEquipmentType = null) }
        }
    }
}