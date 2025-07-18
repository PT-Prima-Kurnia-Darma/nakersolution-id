package com.nakersolutionid.nakersolutionid.features.bap.lightning

import androidx.compose.runtime.Immutable

@Immutable
data class LightningBAPUiState(
    val report: LightningBAPReport = LightningBAPReport()
)

@Immutable
data class LightningBAPReport(
    val equipmentType: String = "",
    val examinationType: String = "",
    val inspectionDate: String = "",
    val generalData: LightningBAPGeneralData = LightningBAPGeneralData(),
    val technicalData: LightningBAPTechnicalData = LightningBAPTechnicalData(),
    val testResults: LightningBAPTestResults = LightningBAPTestResults()
)

@Immutable
data class LightningBAPGeneralData(
    val companyName: String = "",
    val companyLocation: String = "",
    val usageLocation: String = "",
    val addressUsageLocation: String = ""
)

@Immutable
data class LightningBAPTechnicalData(
    val installationType: String = "",
    val serialNumber: String = "",
    val buildingHeightInM: String = "",
    val buildingAreaInM2: String = "",
    val receiverHeightInM: String = "",
    val receiverCount: String = "",
    val groundElectrodeCount: String = "",
    val conductorDescriptionInMm: String = "",
    val installationYear: String = "",
    val groundingResistanceInOhm: String = ""
)

@Immutable
data class LightningBAPTestResults(
    val visualInspection: LightningBAPVisualInspection = LightningBAPVisualInspection(),
    val testing: LightningBAPTesting = LightningBAPTesting()
)

@Immutable
data class LightningBAPVisualInspection(
    val isSystemOverallGood: Boolean = false,
    val isReceiverConditionGood: Boolean = false,
    val isReceiverPoleConditionGood: Boolean = false,
    val isConductorInsulated: Boolean = false,
    val controlBox: LightningBAPControlBox = LightningBAPControlBox()
)

@Immutable
data class LightningBAPControlBox(
    val isAvailable: Boolean = false,
    val isConditionGood: Boolean = false
)

@Immutable
data class LightningBAPTesting(
    val conductorContinuityResult: Boolean = false,
    val measuredGroundingResistanceInOhm: Boolean = false
)