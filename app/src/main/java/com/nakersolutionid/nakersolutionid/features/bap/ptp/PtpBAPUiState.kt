package com.nakersolutionid.nakersolutionid.features.bap.ptp

import androidx.compose.runtime.Immutable

@Immutable
data class PtpBAPUiState(
    val report: PtpBAPReport = PtpBAPReport()
)

@Immutable
data class PtpBAPReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val inspectionDate: String = "",
    val generalData: PtpBAPGeneralData = PtpBAPGeneralData(),
    val technicalData: PtpBAPTechnicalData = PtpBAPTechnicalData(),
    val testResults: PtpBAPTestResults = PtpBAPTestResults()
)

@Immutable
data class PtpBAPGeneralData(
    val companyName: String = "",
    val companyLocation: String = "",
    val usageLocation: String = "",
    val addressUsageLocation: String = ""
)

@Immutable
data class PtpBAPTechnicalData(
    val brandOrType: String = "",
    val manufacturer: String = "",
    val manufactureYear: String = "",
    val manufactureCountry: String = "",
    val serialNumber: String = "",
    val capacityDescription: String = "",
    val driveMotorPowerKw: String = "",
    val specialSpecification: String = "",
    val dimensionsDescription: String = "",
    val rotationRpm: String = "",
    val frequencyHz: String = "",
    val currentAmperes: String = "",
    val machineWeightKg: String = "",
    val areSafetyFeaturesInstalled: Boolean = false
)

@Immutable
data class PtpBAPTestResults(
    val visualInspection: PtpBAPVisualInspection = PtpBAPVisualInspection(),
    val testing: PtpBAPTesting = PtpBAPTesting()
)

@Immutable
data class PtpBAPVisualInspection(
    val isMachineGoodCondition: Boolean = false,
    val areElectricalIndicatorsGood: Boolean = false,
    val personalProtectiveEquipment: PtpBAPPersonalProtectiveEquipment = PtpBAPPersonalProtectiveEquipment(),
    val isGroundingInstalled: Boolean = false,
    val isBatteryGoodCondition: Boolean = false,
    val hasLubricationLeak: Boolean = false,
    val isFoundationGoodCondition: Boolean = false,
    val hasHydraulicLeak: Boolean = false
)

@Immutable
data class PtpBAPPersonalProtectiveEquipment(
    val isAparAvailable: Boolean = false,
    val isPpeAvailable: Boolean = false
)

@Immutable
data class PtpBAPTesting(
    val isLightingCompliant: Boolean = false,
    val isNoiseLevelCompliant: Boolean = false,
    val isEmergencyStopFunctional: Boolean = false,
    val isMachineFunctional: Boolean = false,
    val isVibrationLevelCompliant: Boolean = false,
    val isInsulationResistanceOk: Boolean = false,
    val isShaftRotationCompliant: Boolean = false,
    val isGroundingResistanceCompliant: Boolean = false,
    val isNdtWeldTestOk: Boolean = false,
    val isVoltageBetweenPhasesNormal: Boolean = false,
    val isPhaseLoadBalanced: Boolean = false
)