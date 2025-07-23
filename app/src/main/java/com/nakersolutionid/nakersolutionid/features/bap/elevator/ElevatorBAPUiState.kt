package com.nakersolutionid.nakersolutionid.features.bap.elevator

import androidx.compose.runtime.Immutable

@Immutable
data class ElevatorBAPUiState(
    val elevatorBAPReport: ElevatorBAPReport = ElevatorBAPReport()
)

@Immutable
data class ElevatorBAPReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val inspectionDate: String = "",
    val generalData: ElevatorBAPGeneralData = ElevatorBAPGeneralData(),
    val technicalData: ElevatorBAPTechnicalData = ElevatorBAPTechnicalData(),
    val visualInspection: ElevatorBAPVisualInspection = ElevatorBAPVisualInspection(),
    val testing: ElevatorBAPTesting = ElevatorBAPTesting()
)

@Immutable
data class ElevatorBAPGeneralData(
    val ownerName: String = "",
    val ownerAddress: String = "",
    val nameUsageLocation: String = "",
    val addressUsageLocation: String = ""
)

@Immutable
data class ElevatorBAPTechnicalData(
    val elevatorType: String = "",
    val manufacturerOrInstaller: String = "",
    val brandOrType: String = "",
    val countryAndYear: String = "",
    val serialNumber: String = "",
    val capacity: String = "",
    val speed: String = "",
    val floorsServed: String = ""
)

@Immutable
data class ElevatorBAPVisualInspection(
    val isMachineRoomConditionAcceptable: Boolean = false,
    val isPanelGoodCondition: Boolean = false,
    val isAparAvailableInPanelRoom: Boolean = false,
    val lightingCondition: Boolean = false,
    val isPitLadderAvailable: Boolean = false
)

@Immutable
data class ElevatorBAPTesting(
    val isNdtThermographPanelOk: Boolean = false,
    val isArdFunctional: Boolean = false,
    val isGovernorFunctional: Boolean = false,
    val isSlingConditionOkByTester: Boolean = false,
    val limitSwitchTest: Boolean = false,
    val isDoorSwitchFunctional: Boolean = false,
    val pitEmergencyStopStatus: Boolean = false,
    val isIntercomFunctional: Boolean = false,
    val isFiremanSwitchFunctional: Boolean = false
)