package com.nakersolutionid.nakersolutionid.features.bap.overheadcrane

import androidx.compose.runtime.Immutable

@Immutable
data class OverheadCraneBAPUiState(
    val report: OverheadCraneBAPReport = OverheadCraneBAPReport()
)

@Immutable
data class OverheadCraneBAPReport(
    val examinationType: String = "",
    val subInspectionType: String = "",
    val inspectionDate: String = "",
    val generalData: OverheadCraneBAPGeneralData = OverheadCraneBAPGeneralData(),
    val technicalData: OverheadCraneBAPTechnicalData = OverheadCraneBAPTechnicalData(),
    val testResults: OverheadCraneBAPTestResults = OverheadCraneBAPTestResults()
)

@Immutable
data class OverheadCraneBAPGeneralData(
    val companyName: String = "",
    val companyLocation: String = "",
    val usageLocation: String = "",
    val addressUsageLocation: String = ""
)

@Immutable
data class OverheadCraneBAPTechnicalData(
    val brandOrType: String = "",
    val manufacturer: String = "",
    val manufactureYear: String = "",
    val manufactureCountry: String = "",
    val serialNumber: String = "",
    val maxLiftingCapacityInKg: String = "",
    val liftingSpeedInMpm: String = ""
)

@Immutable
data class OverheadCraneBAPTestResults(
    val inspection: OverheadCraneBAPInspection = OverheadCraneBAPInspection(),
    val testing: OverheadCraneBAPTesting = OverheadCraneBAPTesting()
)

@Immutable
data class OverheadCraneBAPInspection(
    val hasConstructionDefects: Boolean = false,
    val hookHasSafetyLatch: Boolean = false,
    val isEmergencyStopInstalled: Boolean = false,
    val isWireropeGoodCondition: Boolean = false,
    val operatorHasK3License: Boolean = false
)

@Immutable
data class OverheadCraneBAPTesting(
    val functionTest: Boolean = false,
    val loadTest: OverheadCraneBAPLoadTest = OverheadCraneBAPLoadTest(),
    val ndtHookTest: OverheadCraneBAPNdtHookTest = OverheadCraneBAPNdtHookTest()
)

@Immutable
data class OverheadCraneBAPLoadTest(
    val loadInTon: Boolean = false,
    val isAbleToLift: Boolean = false,
    val hasLoadDrop: Boolean = false
)

@Immutable
data class OverheadCraneBAPNdtHookTest(
    val isNdtResultGood: Boolean = false,
    val hasCrackIndication: Boolean = false
)