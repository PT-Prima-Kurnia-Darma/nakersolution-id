package com.nakersolutionid.nakersolutionid.features.bap.fireprotection

import androidx.compose.runtime.Immutable

@Immutable
data class FireProtectionBAPUiState(
    val report: FireProtectionBAPReport = FireProtectionBAPReport()
)

@Immutable
data class FireProtectionBAPReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val inspectionDate: String = "",
    val generalData: FireProtectionBAPGeneralData = FireProtectionBAPGeneralData(),
    val technicalData: FireProtectionBAPTechnicalData = FireProtectionBAPTechnicalData(),
    val testResults: FireProtectionBAPTestResults = FireProtectionBAPTestResults()
)

@Immutable
data class FireProtectionBAPGeneralData(
    val companyName: String = "",
    val companyLocation: String = "",
    val usageLocation: String = "",
    val addressUsageLocation: String = ""
)

@Immutable
data class FireProtectionBAPTechnicalData(
    val areaSizeInM2: String = "",
    val buildingSizeInM2: String = "",
    val buildingHeightInM: String = "",
    val totalFloors: String = "",
    val totalHydrantPilar: String = "",
    val totalHydrantBuilding: String = "",
    val totalHoseRell: String = "",
    val totalSprinkler: String = "",
    val totalHeatDetector: String = "",
    val totalSmokeDetector: String = "",
    val totalFlameDetector: String = "",
    val totalGasDetector: String = "",
    val manualButton: String = "",
    val alarmBell: String = "",
    val signalAlarmLamp: String = "",
    val emergencyExit: String = "",
    val apar: String = "",
    val seriesNumber: String = ""
    // HAPUS: val usageLocation: String = "" --> Field ini duplikat dan menyebabkan data loss.
    // Sumber data yang benar ada di FireProtectionBAPGeneralData.
)

@Immutable
data class FireProtectionBAPTestResults(
    val visualInspection: FireProtectionBAPVisualInspection = FireProtectionBAPVisualInspection(),
    val testing: FireProtectionBAPTesting = FireProtectionBAPTesting()
)

@Immutable
data class FireProtectionBAPVisualInspection(
    val aparStatus: FireProtectionBAPAparStatus = FireProtectionBAPAparStatus(),
    val isHydrantPanelGoodCondition: Boolean = false,
    val pumpStatus: FireProtectionBAPPumpStatus = FireProtectionBAPPumpStatus(),
    val sprinklerSystemStatus: FireProtectionBAPSprinklerSystemStatus = FireProtectionBAPSprinklerSystemStatus(),
    val detectorSystemStatus: FireProtectionBAPDetectorSystemStatus = FireProtectionBAPDetectorSystemStatus()
)

@Immutable
data class FireProtectionBAPAparStatus(
    val isAvailable: Boolean = false,
    val isGoodCondition: Boolean = false
)

@Immutable
data class FireProtectionBAPPumpStatus(
    val isAvailable: Boolean = false,
    val isGoodCondition: Boolean = false
)

@Immutable
data class FireProtectionBAPSprinklerSystemStatus(
    val isAvailable: Boolean = false,
    val isGoodCondition: Boolean = false
)

@Immutable
data class FireProtectionBAPDetectorSystemStatus(
    val isAvailable: Boolean = false,
    val isGoodCondition: Boolean = false
)

@Immutable
data class FireProtectionBAPTesting(
    val isAparFunctional: Boolean = false,
    val pumpTestResults: Boolean = false,
    val isSprinklerFunctional: Boolean = false,
    val detectorTestResults: FireProtectionBAPDetectorTestResults = FireProtectionBAPDetectorTestResults()
)

@Immutable
data class FireProtectionBAPDetectorTestResults(
    val isFunctional: Boolean = false,
    val isConnectedToMcfa: Boolean = false
)