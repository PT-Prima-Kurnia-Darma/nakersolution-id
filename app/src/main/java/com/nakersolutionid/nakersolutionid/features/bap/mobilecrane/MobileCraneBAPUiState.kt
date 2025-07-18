package com.nakersolutionid.nakersolutionid.features.bap.mobilecrane

import androidx.compose.runtime.Immutable

@Immutable
data class MobileCraneBAPUiState(
    val mobileCraneBAPReport: MobileCraneBAPReport = MobileCraneBAPReport()
)

@Immutable
data class MobileCraneBAPReport(
    val equipmentType: String = "",
    val examinationType: String = "",
    val inspectionDate: String = "",
    val generalData: MobileCraneBAPGeneralData = MobileCraneBAPGeneralData(),
    val technicalData: MobileCraneBAPTechnicalData = MobileCraneBAPTechnicalData(),
    val inspectionResult: MobileCraneBAPInspectionResult = MobileCraneBAPInspectionResult(),
    val signature: MobileCraneBAPSignature = MobileCraneBAPSignature()
)

@Immutable
data class MobileCraneBAPGeneralData(
    val ownerName: String = "",
    val ownerAddress: String = "",
    val usageLocationAddress: String = ""
)

@Immutable
data class MobileCraneBAPTechnicalData(
    val manufacturer: String = "",
    val locationAndYearOfManufacture: String = "",
    val serialNumber: String = "",
    val capacity: String = "",
    val maxLiftingHeight: String = "",
    val materialCertificateNumber: String = "",
    val liftingSpeed: String = ""
)

@Immutable
data class MobileCraneBAPInspectionResult(
    val visualCheck: MobileCraneBAPVisualCheck = MobileCraneBAPVisualCheck(),
    val functionalTest: MobileCraneBAPFunctionalTest = MobileCraneBAPFunctionalTest()
)

@Immutable
data class MobileCraneBAPVisualCheck(
    val hasBoomDefects: Boolean = false,
    val isNameplateAttached: Boolean = false,
    val areBoltsAndNutsSecure: Boolean = false,
    val isSlingGoodCondition: Boolean = false,
    val isHookGoodCondition: Boolean = false,
    val isSafetyLatchInstalled: Boolean = false,
    val isTireGoodCondition: Boolean = false,
    val isWorkLampFunctional: Boolean = false
)

@Immutable
data class MobileCraneBAPFunctionalTest(
    val isForwardReverseFunctionOk: Boolean = false,
    val isSwingFunctionOk: Boolean = false,
    val isHoistingFunctionOk: Boolean = false,
    val loadTest: MobileCraneBAPLoadTest = MobileCraneBAPLoadTest(),
    val ndtTest: MobileCraneBAPNdtTest = MobileCraneBAPNdtTest()
)

@Immutable
data class MobileCraneBAPLoadTest(
    val loadInKg: String = "",
    val liftHeightInMeters: String = "",
    val holdTimeInSeconds: String = "",
    val isResultGood: Boolean = false
)

@Immutable
data class MobileCraneBAPNdtTest(
    val method: String = "",
    val isResultGood: Boolean = false
)

@Immutable
data class MobileCraneBAPSignature(
    val companyName: String = ""
)