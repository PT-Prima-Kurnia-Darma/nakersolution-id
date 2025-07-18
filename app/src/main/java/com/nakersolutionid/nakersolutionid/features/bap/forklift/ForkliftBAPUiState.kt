package com.nakersolutionid.nakersolutionid.features.bap.forklift

import androidx.compose.runtime.Immutable

@Immutable
data class ForkliftBAPUiState(
    val forkliftBAPReport: ForkliftBAPReport = ForkliftBAPReport()
)

@Immutable
data class ForkliftBAPReport(
    val equipmentType: String = "",
    val examinationType: String = "",
    val inspectionDate: String = "",
    val generalData: ForkliftBAPGeneralData = ForkliftBAPGeneralData(),
    val technicalData: ForkliftBAPTechnicalData = ForkliftBAPTechnicalData(),
    val inspectionResult: ForkliftBAPInspectionResult = ForkliftBAPInspectionResult()
)

@Immutable
data class ForkliftBAPGeneralData(
    val ownerName: String = "",
    val ownerAddress: String = "",
    val userInCharge: String = ""
)

@Immutable
data class ForkliftBAPTechnicalData(
    val brandType: String = "",
    val manufacturer: String = "",
    val locationAndYearOfManufacture: String = "",
    val serialNumber: String = "",
    val capacityInKg: String = "",
    val liftingHeightInMeters: String = ""
)

@Immutable
data class ForkliftBAPInspectionResult(
    val visualCheck: ForkliftBAPVisualCheck = ForkliftBAPVisualCheck(),
    val functionalTest: ForkliftBAPFunctionalTest = ForkliftBAPFunctionalTest()
)

@Immutable
data class ForkliftBAPVisualCheck(
    val hasForkDefects: Boolean = false,
    val isNameplateAttached: Boolean = false,
    val isAparAvailable: Boolean = false,
    val isCapacityMarkingDisplayed: Boolean = false,
    val hasHydraulicLeak: Boolean = false,
    val isChainGoodCondition: Boolean = false
)

@Immutable
data class ForkliftBAPFunctionalTest(
    val loadTestInKg: String = "",
    val loadTestLiftHeightInMeters: String = "",
    val isAbleToLiftAndHold: Boolean = false,
    val isFunctioningWell: Boolean = false,
    val hasCrackIndication: Boolean = false,
    val isEmergencyStopFunctional: Boolean = false,
    val isWarningLampAndHornFunctional: Boolean = false
)