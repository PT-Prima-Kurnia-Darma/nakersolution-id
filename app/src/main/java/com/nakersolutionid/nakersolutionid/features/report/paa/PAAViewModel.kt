package com.nakersolutionid.nakersolutionid.features.report.paa

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftLoadTestItem
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftNdeChainItem
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftNdeForkItem
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.toForkliftUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneDeflectionItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeGirderItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeWireRopeItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.toGantryCraneUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaCageItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaClampsItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaLoadTest
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaSteelWireRopeItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaSuspensionStructureItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.toGondolaUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane.MobileCraneInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane.MobileCraneLoadTestItem
import com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane.MobileCraneNdeBoomItem
import com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane.MobileCraneNdeWireRopeItem
import com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane.MobileCraneUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane.toMobileCraneUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane.OverheadCraneInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane.OverheadCraneNdeChainItem
import com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane.OverheadCraneUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane.toOverheadCraneUiState
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PAAViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _paaUiState = MutableStateFlow(PAAUiState())
    val paaUiState: StateFlow<PAAUiState> = _paaUiState.asStateFlow()

    // Track current report ID for edit mode
    private var currentReportId: Long? = null
    private var isSynced = false

//    private val _forkliftUiState = MutableStateFlow(ForkliftUiState.createDummyForkliftUiState())
    private val _forkliftUiState = MutableStateFlow(ForkliftUiState())
    val forkliftUiState: StateFlow<ForkliftUiState> = _forkliftUiState.asStateFlow()

//    private val _gantryCraneUiState = MutableStateFlow(GantryCraneUiState.createDummyGantryCraneUiState())
    private val _gantryCraneUiState = MutableStateFlow(GantryCraneUiState())
    val gantryCraneUiState: StateFlow<GantryCraneUiState> = _gantryCraneUiState.asStateFlow()

//    private val _gondolaUiState = MutableStateFlow(GondolaUiState.createDummyGondolaUiState())
    private val _gondolaUiState = MutableStateFlow(GondolaUiState())
    val gondolaUiState: StateFlow<GondolaUiState> = _gondolaUiState.asStateFlow()

//    private val _mobileCraneUiState = MutableStateFlow(MobileCraneUiState.createDummyMobileCraneUiState())
    private val _mobileCraneUiState = MutableStateFlow(MobileCraneUiState())
    val mobileCraneUiState: StateFlow<MobileCraneUiState> = _mobileCraneUiState.asStateFlow()

//    private val _overheadCraneUiState = MutableStateFlow(OverheadCraneUiState.createDummyOverheadCraneUiState())
    private val _overheadCraneUiState = MutableStateFlow(OverheadCraneUiState())
    val overheadCraneUiState: StateFlow<OverheadCraneUiState> = _overheadCraneUiState.asStateFlow()

    fun onGetMLResult(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            val inspection = when (selectedIndex) {
                SubInspectionType.Forklift -> _forkliftUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                SubInspectionType.Mobile_Crane -> _mobileCraneUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                SubInspectionType.Overhead_Crane -> _overheadCraneUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                SubInspectionType.Gantry_Crane -> _gantryCraneUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                SubInspectionType.Gondola -> _gondolaUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                else -> null
            }
            inspection?.let { collectMlResult(it) }
        }
    }

    private suspend fun collectMlResult(inspection: InspectionWithDetailsDomain) {
        reportUseCase.getMLResult(inspection).collect { data ->
            when (data) {
                is Resource.Error -> {
                    onUpdatePAAState { it.copy(mlResult = data.message, mlLoading = false) }
                }
                is Resource.Loading -> onUpdatePAAState { it.copy(mlLoading = true) }
                is Resource.Success -> {
                    val conclusion = data.data?.conclusion ?: ""
                    val recommendation = data.data?.recommendation?.toImmutableList() ?: persistentListOf()
                    when (inspection.inspection.subInspectionType) {
                        SubInspectionType.Forklift -> {
                            val report = _forkliftUiState.value.forkliftInspectionReport
                            val updatedConclusion = report.conclusion.copy(summary = persistentListOf(conclusion), recommendations = recommendation)
                            onForkliftReportDataChange(report.copy(conclusion = updatedConclusion))
                        }
                        SubInspectionType.Mobile_Crane -> {
                            val report = _mobileCraneUiState.value.mobileCraneInspectionReport
                            val updatedConclusion = report.conclusion.copy(summary = persistentListOf(conclusion), recommendations = recommendation)
                            onMobileCraneReportDataChange(report.copy(conclusion = updatedConclusion))
                        }
                        SubInspectionType.Overhead_Crane -> {
                            val report = _overheadCraneUiState.value.overheadCraneInspectionReport
                            val updatedConclusion = report.conclusion.copy(summary = persistentListOf(conclusion), recommendations = recommendation)
                            onOverheadCraneReportDataChange(report.copy(conclusion = updatedConclusion))
                        }
                        SubInspectionType.Gantry_Crane -> {
                            val report = _gantryCraneUiState.value.gantryCraneInspectionReport
                            val updatedConclusion = report.conclusion.copy(summary = persistentListOf(conclusion), recommendations = recommendation)
                            onGantryCraneReportDataChange(report.copy(conclusion = updatedConclusion))
                        }
                        SubInspectionType.Gondola -> {
                            val report = _gondolaUiState.value.gondolaInspectionReport
                            val updatedConclusion = report.conclusion.copy(summary = persistentListOf(conclusion), recommendations = recommendation)
                            onGondolaReportDataChange(report.copy(conclusion = updatedConclusion))
                        }
                        else -> {}
                    }
                    onUpdatePAAState { it.copy(mlLoading = false) }
                }
            }
        }
    }

    fun onCopyClick(selectedIndex: SubInspectionType, isInternetAvailable: Boolean) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Forklift -> {
                    val inspection = _forkliftUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, 0)
                    triggerCopy(inspection, isInternetAvailable)
                }
                SubInspectionType.Mobile_Crane -> {
                    val inspection = _mobileCraneUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, 0)
                    triggerCopy(inspection, isInternetAvailable)
                }
                SubInspectionType.Overhead_Crane -> {
                    val inspection = _overheadCraneUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, 0)
                    triggerCopy(inspection, isInternetAvailable)
                }
                SubInspectionType.Gantry_Crane -> {
                    val inspection = _gantryCraneUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, 0)
                    triggerCopy(inspection, isInternetAvailable)
                }
                SubInspectionType.Gondola -> {
                    val inspection = _gondolaUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, 0)
                    triggerCopy(inspection, isInternetAvailable)
                }
                else -> null
            }
        }
    }

    private suspend fun triggerCopy(inspection: InspectionWithDetailsDomain, isInternetAvailable: Boolean) {
        val id = saveReport(inspection)

        if (id == null) return

        if (isInternetAvailable) {
            val cloudInspection = inspection.copy(inspection = inspection.inspection.copy(id = id))
            createReport(cloudInspection)
        } else {
            _paaUiState.update { it.copy(result = Resource.Success("Laporan berhasil disimpan")) }
        }
    }

    fun onSaveClick(selectedIndex: SubInspectionType, isInternetAvailable: Boolean) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Forklift -> {
                    val inspection = _forkliftUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                    triggerSaving(inspection, isInternetAvailable)
                }
                SubInspectionType.Mobile_Crane -> {
                    val inspection = _mobileCraneUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                    triggerSaving(inspection, isInternetAvailable)
                }
                SubInspectionType.Overhead_Crane -> {
                    val inspection = _overheadCraneUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                    triggerSaving(inspection, isInternetAvailable)
                }
                SubInspectionType.Gantry_Crane -> {
                    val inspection = _gantryCraneUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                    triggerSaving(inspection, isInternetAvailable)
                }
                SubInspectionType.Gondola -> {
                    val inspection = _gondolaUiState.value.toInspectionWithDetailsDomain(currentTime, _paaUiState.value.editMode, currentReportId)
                    triggerSaving(inspection, isInternetAvailable)
                }
                else -> {}
            }
        }
    }

    private suspend fun triggerSaving(inspection: InspectionWithDetailsDomain, isInternetAvailable: Boolean) {
        val isEditMode = _paaUiState.value.editMode

        val id = saveReport(inspection)

        if (id == null) {
            return
        }

        if (isInternetAvailable) {
            val cloudInspection = inspection.copy(inspection = inspection.inspection.copy(id = id))
            if (isEditMode) {
                if (isSynced) updateReport(cloudInspection)
            } else {
                createReport(cloudInspection)
            }
        } else {
            _paaUiState.update { it.copy(result = Resource.Success("Laporan berhasil disimpan")) }
        }
    }

    suspend fun saveReport(inspection: InspectionWithDetailsDomain): Long? {
        try {
            val id = reportUseCase.saveReport(inspection)
            return id
        } catch(_: SQLiteConstraintException) {
            _paaUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
            return null
        } catch (_: Exception) {
            _paaUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
            return null
        }
    }

    private suspend fun createReport(inspection: InspectionWithDetailsDomain) {
        try {
            reportUseCase.createReport(inspection).collect { result ->
                _paaUiState.update { it.copy(result = result) }
            }
        } catch (_: Exception) {
            _paaUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    private suspend fun updateReport(inspection: InspectionWithDetailsDomain) {
        try {
            reportUseCase.updateReport(inspection).collect { result ->
                _paaUiState.update { it.copy(result = result) }
            }
        } catch (_: Exception) {
            _paaUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    fun onUpdatePAAState(updater: (PAAUiState) -> PAAUiState) {
        _paaUiState.update(updater)
    }

    //region Overhead Crane Logic
    fun onOverheadCraneReportDataChange(newReportData: OverheadCraneInspectionReport) {
        _overheadCraneUiState.update { it.copy(overheadCraneInspectionReport = newReportData) }
    }

    fun addOverheadCraneNdeChainItem(item: OverheadCraneNdeChainItem) = viewModelScope.launch {
        val report = _overheadCraneUiState.value.overheadCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val chain = nde.chain
        val newItems = (chain.items + item).toImmutableList()
        onOverheadCraneReportDataChange(report.copy(nonDestructiveExamination = nde.copy(chain = chain.copy(items = newItems))))
    }

    fun deleteOverheadCraneNdeChainItem(index: Int) = viewModelScope.launch {
        val report = _overheadCraneUiState.value.overheadCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val chain = nde.chain
        val newItems = chain.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        onOverheadCraneReportDataChange(report.copy(nonDestructiveExamination = nde.copy(chain = chain.copy(items = newItems))))
    }

    fun addOverheadCraneConclusionSummaryItem(item: String) = viewModelScope.launch {
        val report = _overheadCraneUiState.value.overheadCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onOverheadCraneReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeOverheadCraneConclusionSummaryItem(index: Int) = viewModelScope.launch {
        val report = _overheadCraneUiState.value.overheadCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onOverheadCraneReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addOverheadCraneConclusionRecommendationItem(item: String) = viewModelScope.launch {
        val report = _overheadCraneUiState.value.overheadCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onOverheadCraneReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeOverheadCraneConclusionRecommendationItem(index: Int) = viewModelScope.launch {
        val report = _overheadCraneUiState.value.overheadCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onOverheadCraneReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
    //endregion

    //region Mobile Crane Logic
    fun onMobileCraneReportDataChange(newReportData: MobileCraneInspectionReport) {
        _mobileCraneUiState.update { it.copy(mobileCraneInspectionReport = newReportData) }
    }

    fun addMobileCraneNdeWireRopeItem(item: MobileCraneNdeWireRopeItem) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val wireRope = nde.wireRope
        val newItems = (wireRope.items + item).toImmutableList()
        onMobileCraneReportDataChange(report.copy(nonDestructiveExamination = nde.copy(wireRope = wireRope.copy(items = newItems))))
    }

    fun deleteMobileCraneNdeWireRopeItem(index: Int) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val wireRope = nde.wireRope
        val newItems = wireRope.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMobileCraneReportDataChange(report.copy(nonDestructiveExamination = nde.copy(wireRope = wireRope.copy(items = newItems))))
    }

    fun addMobileCraneNdeBoomItem(item: MobileCraneNdeBoomItem) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val boom = nde.boom
        val newItems = (boom.items + item).toImmutableList()
        onMobileCraneReportDataChange(report.copy(nonDestructiveExamination = nde.copy(boom = boom.copy(items = newItems))))
    }

    fun deleteMobileCraneNdeBoomItem(index: Int) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val boom = nde.boom
        val newItems = boom.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMobileCraneReportDataChange(report.copy(nonDestructiveExamination = nde.copy(boom = boom.copy(items = newItems))))
    }

    fun addMobileCraneDynamicMainHookLoadTestItem(item: MobileCraneLoadTestItem) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val testing = report.testing
        val loadTest = testing.loadTest
        val dynamic = loadTest.dynamic
        val newItems = (dynamic.mainHook + item).toImmutableList()
        onMobileCraneReportDataChange(report.copy(testing = testing.copy(loadTest = loadTest.copy(dynamic = dynamic.copy(mainHook = newItems)))))
    }

    fun deleteMobileCraneDynamicMainHookLoadTestItem(index: Int) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val testing = report.testing
        val loadTest = testing.loadTest
        val dynamic = loadTest.dynamic
        val newItems = dynamic.mainHook.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMobileCraneReportDataChange(report.copy(testing = testing.copy(loadTest = loadTest.copy(dynamic = dynamic.copy(mainHook = newItems)))))
    }

    fun addMobileCraneDynamicAuxHookLoadTestItem(item: MobileCraneLoadTestItem) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val testing = report.testing
        val loadTest = testing.loadTest
        val dynamic = loadTest.dynamic
        val newItems = (dynamic.auxiliaryHook + item).toImmutableList()
        onMobileCraneReportDataChange(report.copy(testing = testing.copy(loadTest = loadTest.copy(dynamic = dynamic.copy(auxiliaryHook = newItems)))))
    }

    fun deleteMobileCraneDynamicAuxHookLoadTestItem(index: Int) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val testing = report.testing
        val loadTest = testing.loadTest
        val dynamic = loadTest.dynamic
        val newItems = dynamic.auxiliaryHook.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMobileCraneReportDataChange(report.copy(testing = testing.copy(loadTest = loadTest.copy(dynamic = dynamic.copy(auxiliaryHook = newItems)))))
    }

    fun addMobileCraneStaticMainHookLoadTestItem(item: MobileCraneLoadTestItem) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val testing = report.testing
        val loadTest = testing.loadTest
        val static = loadTest.static
        val newItems = (static.mainHook + item).toImmutableList()
        onMobileCraneReportDataChange(report.copy(testing = testing.copy(loadTest = loadTest.copy(static = static.copy(mainHook = newItems)))))
    }

    fun deleteMobileCraneStaticMainHookLoadTestItem(index: Int) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val testing = report.testing
        val loadTest = testing.loadTest
        val static = loadTest.static
        val newItems = static.mainHook.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMobileCraneReportDataChange(report.copy(testing = testing.copy(loadTest = loadTest.copy(static = static.copy(mainHook = newItems)))))
    }

    fun addMobileCraneStaticAuxHookLoadTestItem(item: MobileCraneLoadTestItem) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val testing = report.testing
        val loadTest = testing.loadTest
        val static = loadTest.static
        val newItems = (static.auxiliaryHook + item).toImmutableList()
        onMobileCraneReportDataChange(report.copy(testing = testing.copy(loadTest = loadTest.copy(static = static.copy(auxiliaryHook = newItems)))))
    }

    fun deleteMobileCraneStaticAuxHookLoadTestItem(index: Int) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val testing = report.testing
        val loadTest = testing.loadTest
        val static = loadTest.static
        val newItems = static.auxiliaryHook.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMobileCraneReportDataChange(report.copy(testing = testing.copy(loadTest = loadTest.copy(static = static.copy(auxiliaryHook = newItems)))))
    }

    fun addMobileCraneConclusionSummaryItem(item: String) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onMobileCraneReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeMobileCraneConclusionSummaryItem(index: Int) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMobileCraneReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addMobileCraneConclusionRecommendationItem(item: String) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onMobileCraneReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeMobileCraneConclusionRecommendationItem(index: Int) = viewModelScope.launch {
        val report = _mobileCraneUiState.value.mobileCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMobileCraneReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
    //endregion

    //region Forklift Logic
    fun onForkliftReportDataChange(newReportData: ForkliftInspectionReport) {
        _forkliftUiState.update { it.copy(forkliftInspectionReport = newReportData) }
    }

    fun addNdeChainItem(item: ForkliftNdeChainItem) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentChainInspection = currentNde.liftingChainInspection
        val newItems = (currentChainInspection.items + item).toImmutableList()
        val updatedNde = currentNde.copy(
            liftingChainInspection = currentChainInspection.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun deleteNdeChainItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentChainInspection = currentNde.liftingChainInspection
        val newItems =
            currentChainInspection.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNde = currentNde.copy(
            liftingChainInspection = currentChainInspection.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun addNdeForkItem(item: ForkliftNdeForkItem) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentForkNdt = currentNde.forkNDT
        val newItems = (currentForkNdt.items + item).toImmutableList()
        val updatedNde = currentNde.copy(
            forkNDT = currentForkNdt.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun deleteNdeForkItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentForkNdt = currentNde.forkNDT
        val newItems =
            currentForkNdt.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNde = currentNde.copy(
            forkNDT = currentForkNdt.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun updateNdtType(ndtType: String) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val updatedForkNdt = currentNde.forkNDT.copy(ndtType = ndtType)
        val updatedNde = currentNde.copy(forkNDT = updatedForkNdt)
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun addLoadTestItem(item: ForkliftLoadTestItem) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentTesting = currentReport.testing
        val currentLoadTest = currentTesting.loadTest
        val newItems = (currentLoadTest.items + item).toImmutableList()
        val updatedTesting = currentTesting.copy(
            loadTest = currentLoadTest.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(testing = updatedTesting))
    }

    fun deleteLoadTestItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentTesting = currentReport.testing
        val currentLoadTest = currentTesting.loadTest
        val newItems =
            currentLoadTest.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedTesting = currentTesting.copy(
            loadTest = currentLoadTest.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(testing = updatedTesting))
    }

    fun addConclusionSummaryItem(item: String) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = (currentConclusion.summary + item).toImmutableList()
        onForkliftReportDataChange(currentReport.copy(conclusion = currentConclusion.copy(summary = newItems)))
    }

    fun removeConclusionSummaryItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems =
            currentConclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onForkliftReportDataChange(currentReport.copy(conclusion = currentConclusion.copy(summary = newItems)))
    }

    fun addConclusionRecommendationItem(item: String) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = (currentConclusion.recommendations + item).toImmutableList()
        onForkliftReportDataChange(
            currentReport.copy(
                conclusion = currentConclusion.copy(
                    recommendations = newItems
                )
            )
        )
    }

    fun removeConclusionRecommendationItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = currentConclusion.recommendations.toMutableList().apply { removeAt(index) }
            .toImmutableList()
        onForkliftReportDataChange(
            currentReport.copy(
                conclusion = currentConclusion.copy(
                    recommendations = newItems
                )
            )
        )
    }
    //endregion

    //region Gantry Crane Logic
    fun onGantryCraneReportDataChange(newReportData: GantryCraneInspectionReport) {
        _gantryCraneUiState.update { it.copy(gantryCraneInspectionReport = newReportData) }
    }

    fun addGantryNdeWireRopeItem(item: GantryCraneNdeWireRopeItem) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val wireRope = nde.wireRope
        val newItems = (wireRope.items + item).toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                nonDestructiveExamination = nde.copy(
                    wireRope = wireRope.copy(
                        items = newItems
                    )
                )
            )
        )
    }

    fun deleteGantryNdeWireRopeItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val wireRope = nde.wireRope
        val newItems = wireRope.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                nonDestructiveExamination = nde.copy(
                    wireRope = wireRope.copy(
                        items = newItems
                    )
                )
            )
        )
    }

    fun addGantryNdeGirderItem(item: GantryCraneNdeGirderItem) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val girder = nde.girder
        val newItems = (girder.items + item).toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                nonDestructiveExamination = nde.copy(
                    girder = girder.copy(
                        items = newItems
                    )
                )
            )
        )
    }

    fun deleteGantryNdeGirderItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val girder = nde.girder
        val newItems = girder.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                nonDestructiveExamination = nde.copy(
                    girder = girder.copy(
                        items = newItems
                    )
                )
            )
        )
    }

    fun addGantryDeflectionItem(item: GantryCraneDeflectionItem) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val testing = report.testing
        val staticTest = testing.staticTest
        val newItems = (staticTest.deflectionMeasurement + item).toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                testing = testing.copy(
                    staticTest = staticTest.copy(
                        deflectionMeasurement = newItems
                    )
                )
            )
        )
    }

    fun deleteGantryDeflectionItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val testing = report.testing
        val staticTest = testing.staticTest
        val newItems = staticTest.deflectionMeasurement.toMutableList().apply { removeAt(index) }
            .toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                testing = testing.copy(
                    staticTest = staticTest.copy(
                        deflectionMeasurement = newItems
                    )
                )
            )
        )
    }

    fun addGantryConclusionSummaryItem(item: String) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onGantryCraneReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeGantryConclusionSummaryItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val conclusion = report.conclusion
        val newItems =
            conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGantryCraneReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addGantryConclusionRecommendationItem(item: String) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onGantryCraneReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeGantryConclusionRecommendationItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val conclusion = report.conclusion
        val newItems =
            conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGantryCraneReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
    //endregion

    //region Gondola Logic
    fun onGondolaReportDataChange(newReportData: GondolaInspectionReport) {
        _gondolaUiState.update { it.copy(gondolaInspectionReport = newReportData) }
    }

    fun addGondolaNdeSteelWireRopeItem(item: GondolaSteelWireRopeItem) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val steelWireRope = ndt.steelWireRope
        val newItems = (steelWireRope.items + item).toImmutableList()
        val updatedNdt = ndt.copy(steelWireRope = steelWireRope.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun deleteGondolaNdeSteelWireRopeItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val steelWireRope = ndt.steelWireRope
        val newItems = steelWireRope.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNdt = ndt.copy(steelWireRope = steelWireRope.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun addGondolaNdeSuspensionStructureItem(item: GondolaSuspensionStructureItem) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val suspensionStructure = ndt.suspensionStructure
        val newItems = (suspensionStructure.items + item).toImmutableList()
        val updatedNdt = ndt.copy(suspensionStructure = suspensionStructure.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun deleteGondolaNdeSuspensionStructureItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val suspensionStructure = ndt.suspensionStructure
        val newItems = suspensionStructure.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNdt = ndt.copy(suspensionStructure = suspensionStructure.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun addGondolaNdeCageItem(item: GondolaCageItem) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val cage = ndt.gondolaCage
        val newItems = (cage.items + item).toImmutableList()
        val updatedNdt = ndt.copy(gondolaCage = cage.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun deleteGondolaNdeCageItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val cage = ndt.gondolaCage
        val newItems = cage.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNdt = ndt.copy(gondolaCage = cage.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun addGondolaNdeClampsItem(item: GondolaClampsItem) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val clamps = ndt.clamps
        val newItems = (clamps.items + item).toImmutableList()
        val updatedNdt = ndt.copy(clamps = clamps.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun deleteGondolaNdeClampsItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val clamps = ndt.clamps
        val newItems = clamps.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNdt = ndt.copy(clamps = clamps.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun addGondolaDynamicLoadTestItem(item: GondolaLoadTest) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val testing = report.testing
        val newItems = (testing.dynamicLoad + item).toImmutableList()
        onGondolaReportDataChange(report.copy(testing = testing.copy(dynamicLoad = newItems)))
    }

    fun deleteGondolaDynamicLoadTestItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val testing = report.testing
        val newItems = testing.dynamicLoad.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGondolaReportDataChange(report.copy(testing = testing.copy(dynamicLoad = newItems)))
    }

    fun addGondolaStaticLoadTestItem(item: GondolaLoadTest) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val testing = report.testing
        val newItems = (testing.staticLoad + item).toImmutableList()
        onGondolaReportDataChange(report.copy(testing = testing.copy(staticLoad = newItems)))
    }

    fun deleteGondolaStaticLoadTestItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val testing = report.testing
        val newItems = testing.staticLoad.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGondolaReportDataChange(report.copy(testing = testing.copy(staticLoad = newItems)))
    }

    fun addGondolaConclusionSummaryItem(item: String) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onGondolaReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeGondolaConclusionSummaryItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGondolaReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addGondolaConclusionRecommendationItem(item: String) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onGondolaReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeGondolaConclusionRecommendationItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGondolaReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
    //endregion

    /**
     * Load an existing report for editing.
     * @param reportId The ID of the report to load for editing
     */
    fun loadReportForEdit(reportId: Long) {
        viewModelScope.launch {
            try {
                _paaUiState.update { it.copy(isLoading = true) }
                val inspection = reportUseCase.getInspection(reportId)
                
                if (inspection != null) {
                    // Store the report ID for editing
                    currentReportId = reportId
                    isSynced = inspection.inspection.isSynced
                    
                    // Extract the equipment type from the loaded inspection
                    val equipmentType = inspection.inspection.subInspectionType

                    when (equipmentType) {
                        SubInspectionType.Forklift -> _forkliftUiState.update { inspection.toForkliftUiState() }
                        SubInspectionType.Gantry_Crane -> _gantryCraneUiState.update { inspection.toGantryCraneUiState() }
                        SubInspectionType.Gondola -> _gondolaUiState.update { inspection.toGondolaUiState() }
                        SubInspectionType.Mobile_Crane -> _mobileCraneUiState.update { inspection.toMobileCraneUiState() }
                        SubInspectionType.Overhead_Crane -> _overheadCraneUiState.update { inspection.toOverheadCraneUiState() }
                        else -> {}
                    }

                    _paaUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Success("Data laporan berhasil dimuat untuk diedit"),
                            loadedEquipmentType = equipmentType
                        )
                    }
                } else {
                    _paaUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Error("Laporan tidak ditemukan")
                        )
                    }
                }
            } catch (e: Exception) {
                _paaUiState.update { 
                    it.copy(
                        isLoading = false,
                        editLoadResult = Resource.Error("Gagal memuat data laporan: ${e.message}")
                    )
                }
            }
        }
    }
}