package com.nakersolutionid.nakersolutionid.features.bap.escalator

import androidx.compose.runtime.Immutable

@Immutable
data class EscalatorBAPUiState(
    val escalatorBAPReport: EscalatorBAPReport = EscalatorBAPReport()
)

@Immutable
data class EscalatorBAPReport(
    val equipmentType: String = "",
    val examinationType: String = "",
    val inspectionDate: String = "",
    val generalData: EscalatorBAPGeneralData = EscalatorBAPGeneralData(),
    val technicalData: EscalatorBAPTechnicalData = EscalatorBAPTechnicalData(),
    val visualInspection: EscalatorBAPVisualInspection = EscalatorBAPVisualInspection(),
    val testing: EscalatorBAPTesting = EscalatorBAPTesting()
)

@Immutable
data class EscalatorBAPGeneralData(
    val ownerName: String = "",
    val companyLocation: String = "",
    val nameUsageLocation: String = "",
    val locationUsageLocation: String = ""
)

@Immutable
data class EscalatorBAPTechnicalData(
    val manufacturer: String = "",
    val brand: String = "",
    val countryAndYear: String = "",
    val serialNumber: String = "",
    val capacity: String = "",
    val speed: String = "",
    val transports: String = ""
)

@Immutable
data class EscalatorBAPVisualInspection(
    val isMachineRoomConditionAcceptable: Boolean = false,
    val isPanelConditionAcceptable: Boolean = false,
    val isLightingConditionAcceptable: Boolean = false,
    val areSafetySignsAvailable: Boolean = false
)

@Immutable
data class EscalatorBAPTesting(
    val isNdtThermographPanelOk: Boolean = false,
    val areSafetyDevicesFunctional: Boolean = false,
    val isLimitSwitchFunctional: Boolean = false,
    val isDoorSwitchFunctional: Boolean = false,
    val isPitEmergencyStopAvailable: Boolean = false,
    val isPitEmergencyStopFunctional: Boolean = false,
    val isEscalatorFunctionOk: Boolean = false
)