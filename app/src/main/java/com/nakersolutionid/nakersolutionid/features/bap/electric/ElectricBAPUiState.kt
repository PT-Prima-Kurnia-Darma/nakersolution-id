package com.nakersolutionid.nakersolutionid.features.bap.electric

import androidx.compose.runtime.Immutable

@Immutable
data class ElectricalInstallationBAPUiState(
    val report: ElectricalInstallationBAPReport = ElectricalInstallationBAPReport()
)

@Immutable
data class ElectricalInstallationBAPReport(
    val examinationType: String = "",
    val equipmentType: String = "",
    val inspectionDate: String = "",
    val generalData: ElectricalInstallationBAPGeneralData = ElectricalInstallationBAPGeneralData(),
    val technicalData: ElectricalInstallationBAPTechnicalData = ElectricalInstallationBAPTechnicalData(),
    val testResults: ElectricalInstallationBAPTestResults = ElectricalInstallationBAPTestResults()
)

@Immutable
data class ElectricalInstallationBAPGeneralData(
    val companyName: String = "",
    val companyLocation: String = "",
    val usageLocation: String = "",
    val addressUsageLocation: String = ""
)

@Immutable
data class ElectricalInstallationBAPTechnicalData(
    val powerSource: ElectricalInstallationBAPPowerSource = ElectricalInstallationBAPPowerSource(),
    val powerUsage: ElectricalInstallationBAPPowerUsage = ElectricalInstallationBAPPowerUsage(),
    val electricCurrentType: String = "",
    val serialNumber: String = ""
)

@Immutable
data class ElectricalInstallationBAPPowerSource(
    val plnKva: String = "",
    val generatorKw: String = ""
)

@Immutable
data class ElectricalInstallationBAPPowerUsage(
    val lightingKva: String = "",
    val powerKva: String = ""
)

@Immutable
data class ElectricalInstallationBAPTestResults(
    val visualInspection: ElectricalInstallationBAPVisualInspection = ElectricalInstallationBAPVisualInspection(),
    val testing: ElectricalInstallationBAPTesting = ElectricalInstallationBAPTesting()
)

@Immutable
data class ElectricalInstallationBAPVisualInspection(
    val panelRoomCondition: ElectricalInstallationBAPPanelRoomCondition = ElectricalInstallationBAPPanelRoomCondition(),
    val hasSingleLineDiagram: Boolean = false,
    val panelAndMcbHaveProtectiveCover: Boolean = false,
    val isLabelingComplete: Boolean = false,
    val isFireExtinguisherAvailable: Boolean = false
)

@Immutable
data class ElectricalInstallationBAPPanelRoomCondition(
    val isRoomClean: Boolean = false,
    val isRoomClearOfUnnecessaryItems: Boolean = false
)

@Immutable
data class ElectricalInstallationBAPTesting(
    val thermographTestResult: Boolean = false,
    val areSafetyDevicesFunctional: Boolean = false,
    val isVoltageBetweenPhasesNormal: Boolean = false,
    val isPhaseLoadBalanced: Boolean = false
)