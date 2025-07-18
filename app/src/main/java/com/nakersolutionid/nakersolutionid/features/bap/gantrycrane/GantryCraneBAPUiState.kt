package com.nakersolutionid.nakersolutionid.features.bap.gantrycrane

import androidx.compose.runtime.Immutable

@Immutable
data class GantryCraneBAPUiState(
    val gantryCraneBAPReport: GantryCraneBAPReport = GantryCraneBAPReport()
)

@Immutable
data class GantryCraneBAPReport(
    val equipmentType: String = "",
    val examinationType: String = "",
    val inspectionDate: String = "",
    val generalData: GantryCraneBAPGeneralData = GantryCraneBAPGeneralData(),
    val technicalData: GantryCraneBAPTechnicalData = GantryCraneBAPTechnicalData(),
    val inspectionResult: GantryCraneBAPInspectionResult = GantryCraneBAPInspectionResult()
)

@Immutable
data class GantryCraneBAPGeneralData(
    val ownerName: String = "",
    val ownerAddress: String = "",
    val usageLocation: String = "",
    val siteLocation: String = ""
)

@Immutable
data class GantryCraneBAPTechnicalData(
    val brandOrType: String = "",
    val manufacturerHoist: String = "",
    val manufactureStructure: String = "",
    val manufactureYear: String = "",
    val manufactureCountry: String = "",
    val serialNumber: String = "",
    val maxLiftingCapacityInKg: String = "",
    val liftingSpeedInMpm: String = ""
)

@Immutable
data class GantryCraneBAPInspectionResult(
    val visualCheck: GantryCraneBAPVisualCheck = GantryCraneBAPVisualCheck(),
    val functionalTest: GantryCraneBAPFunctionalTest = GantryCraneBAPFunctionalTest(),
    val ndtTest: GantryCraneBAPNdtTest = GantryCraneBAPNdtTest(),
    val loadTest: GantryCraneBAPLoadTest = GantryCraneBAPLoadTest()
)

@Immutable
data class GantryCraneBAPVisualCheck(
    val isMainStructureGood: Boolean = false,
    val areBoltsAndNutsSecure: Boolean = false,
    val isWireRopeGoodCondition: Boolean = false,
    val isHookGoodCondition: Boolean = false,
    val isGearboxGoodCondition: Boolean = false,
    val hasGearboxOilLeak: Boolean = false,
    val isWarningLampGoodCondition: Boolean = false,
    val isCapacityMarkingDisplayed: Boolean = false
)

@Immutable
data class GantryCraneBAPFunctionalTest(
    val isForwardReverseFunctionOk: Boolean = false,
    val isHoistingFunctionOk: Boolean = false,
    val isLimitSwitchFunctional: Boolean = false
)

@Immutable
data class GantryCraneBAPNdtTest(
    val method: String = "",
    val isResultGood: Boolean = false
)

@Immutable
data class GantryCraneBAPLoadTest(
    val loadInKg: String = "",
    val liftHeightInMeters: String = "",
    val holdTimeInSeconds: String = "",
    val isResultGood: Boolean = false
)