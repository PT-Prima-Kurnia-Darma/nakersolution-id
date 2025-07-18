package com.nakersolutionid.nakersolutionid.features.bap.gondola

import androidx.compose.runtime.Immutable

@Immutable
data class GondolaBAPUiState(
    val gondolaBAPReport: GondolaBAPReport = GondolaBAPReport()
)

@Immutable
data class GondolaBAPReport(
    val equipmentType: String = "",
    val examinationType: String = "",
    val inspectionDate: String = "",
    val generalData: GondolaBAPGeneralData = GondolaBAPGeneralData(),
    val technicalData: GondolaBAPTechnicalData = GondolaBAPTechnicalData(),
    val inspectionResult: GondolaBAPInspectionResult = GondolaBAPInspectionResult()
)

@Immutable
data class GondolaBAPGeneralData(
    val companyName: String = "",
    val companyLocation: String = "",
    val userInCharge: String = "",
    val ownerAddress: String = ""
)

@Immutable
data class GondolaBAPTechnicalData(
    val manufacturer: String = "",
    val locationAndYearOfManufacture: String = "",
    val serialNumber: String = "",
    val intendedUse: String = "",
    val capacity: String = "",
    val maxLiftingHeightInMeters: String = ""
)

@Immutable
data class GondolaBAPInspectionResult(
    val visualCheck: GondolaBAPVisualCheck = GondolaBAPVisualCheck(),
    val functionalTest: GondolaBAPFunctionalTest = GondolaBAPFunctionalTest()
)

@Immutable
data class GondolaBAPVisualCheck(
    val isSlingDiameterAcceptable: Boolean = false,
    val isBumperInstalled: Boolean = false,
    val isCapacityMarkingDisplayed: Boolean = false,
    val isPlatformConditionAcceptable: Boolean = false,
    val driveMotorCondition: GondolaBAPDriveMotorCondition = GondolaBAPDriveMotorCondition(),
    val isControlPanelClean: Boolean = false,
    val isBodyHarnessAvailable: Boolean = false,
    val isLifelineAvailable: Boolean = false,
    val isButtonLabelsDisplayed: Boolean = false
)

@Immutable
data class GondolaBAPDriveMotorCondition(
    val isGoodCondition: Boolean = false,
    val hasOilLeak: Boolean = false
)

@Immutable
data class GondolaBAPFunctionalTest(
    val isWireRopeMeasurementOk: Boolean = false,
    val isUpDownFunctionOk: Boolean = false,
    val isDriveMotorFunctionOk: Boolean = false,
    val isEmergencyStopFunctional: Boolean = false,
    val isSafetyLifelineFunctional: Boolean = false,
    val ndtTest: GondolaBAPNdtTest = GondolaBAPNdtTest()
)

@Immutable
data class GondolaBAPNdtTest(
    val method: String = "",
    val isResultGood: Boolean = false,
    val hasCrackIndication: Boolean = false
)