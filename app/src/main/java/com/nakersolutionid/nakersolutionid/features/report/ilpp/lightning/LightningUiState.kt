package com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Lightning Protection inspection report.
 * This class is immutable.
 */
@Immutable
data class LightningProtectionUiState(
    val isLoading: Boolean = false,
    val inspectionReport: LightningProtectionInspectionReport = LightningProtectionInspectionReport()
)

@Immutable
data class LightningProtectionInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val serviceProviderData: LightningProtectionServiceProviderData = LightningProtectionServiceProviderData(),
    val clientData: LightningProtectionClientData = LightningProtectionClientData(),
    val technicalData: LightningProtectionTechnicalData = LightningProtectionTechnicalData(),
    val physicalInspection: LightningProtectionPhysicalInspection = LightningProtectionPhysicalInspection(),
    val groundingSystemVisualInspection: LightningProtectionGroundingSystemVisualInspection = LightningProtectionGroundingSystemVisualInspection(),
    val otherStandardsInspection: LightningProtectionOtherStandardsInspection = LightningProtectionOtherStandardsInspection(),
    val testingResults: LightningProtectionTestingResults = LightningProtectionTestingResults(),
    val conclusion: LightningProtectionConclusion = LightningProtectionConclusion()
)

@Immutable
data class LightningProtectionServiceProviderData(
    val companyName: String = "",
    val companyAddress: String = "",
    val companyPermitNo: String = "",
    val expertPermitNo: String = "",
    val expertName: String = "",
    val testEquipmentUsed: String = ""
)

@Immutable
data class LightningProtectionClientData(
    val companyName: String = "",
    val companyLocation: String = "",
    val usageLocation: String = "",
    val objectType: String = "",
    val inspectionType: String = "", // Examination Type
    val certificateNo: String = "",
    val inspectionDate: String = ""
)

@Immutable
data class LightningProtectionTechnicalData(
    val conductorType: String = "", // Drive Type
    val buildingHeight: String = "",
    val buildingArea: String = "",
    val receiverHeight: String = "",
    val receiverCount: String = "",
    val testJointCount: String = "",
    val conductorDescription: String = "",
    val conductorTypeAndSize: String = "",
    val groundingResistance: String = "",
    val installationYear: String = "",
    val installer: String = ""
)

@Immutable
data class LightningProtectionConditionResult(
    val good: Boolean = false,
    val fair: Boolean = false,
    val poor: Boolean = false,
)

@Immutable
data class LightningProtectionPhysicalInspection(
    val installationSystem: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val receiverHead: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val receiverPole: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val poleReinforcementSystem: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val downConductor: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val conductorClamps: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val jointConnections: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val downConductorBoxAndGroundingTerminal: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val controlBox: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val groundingSystem: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val downConductorDirectConnection: LightningProtectionConditionResult = LightningProtectionConditionResult(),
    val notes: String = ""
)

@Immutable
data class LightningProtectionGroundingSystemVisualInspection(
    val airTerminal: String = "",
    val downConductor: String = "",
    val groundingAndTestJoint: String = ""
)

@Immutable
data class LightningProtectionCheckResult(
    val checked: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class LightningProtectionOtherStandardsInspection(
    val installationPlacementAsPerDrawing: LightningProtectionCheckResult = LightningProtectionCheckResult(),
    val airTerminalConnectionToDownConductor: LightningProtectionCheckResult = LightningProtectionCheckResult(),
    val downConductorJointsOnStructure: LightningProtectionCheckResult = LightningProtectionCheckResult(),
    val groundingElectrodeUsesTestJointBox: LightningProtectionCheckResult = LightningProtectionCheckResult(),
    val downConductorMaterialStandard: LightningProtectionCheckResult = LightningProtectionCheckResult(),
    val lightningCounterInstalled: LightningProtectionCheckResult = LightningProtectionCheckResult(),
    val airTerminalIsRadioactive: LightningProtectionCheckResult = LightningProtectionCheckResult(),
    val groundingElectrodeMaterialQuality: LightningProtectionCheckResult = LightningProtectionCheckResult(),
    val overvoltageProtectionInstalled: LightningProtectionCheckResult = LightningProtectionCheckResult()
)

@Immutable
data class LightningProtectionTestingResults(
    val groundingResistanceMeasurement: ImmutableList<LightningProtectionGroundingMeasurementItem> = persistentListOf(),
    val groundingResistanceTest: ImmutableList<LightningProtectionGroundingTestItem> = persistentListOf()
)

@Immutable
data class LightningProtectionGroundingMeasurementItem(
    val ecDistance: String = "",
    val epDistance: String = "",
    val rValueOhm: String = "",
    val remarks: String = ""
)

@Immutable
data class LightningProtectionGroundingTestItem(
    val itemChecked: String = "",
    val result: Boolean = false,
    val measuredValue: String = "",
    val remarks: String = ""
)

@Immutable
data class LightningProtectionConclusion(
    val summary: String = "",
    val recommendations: ImmutableList<String> = persistentListOf()
)