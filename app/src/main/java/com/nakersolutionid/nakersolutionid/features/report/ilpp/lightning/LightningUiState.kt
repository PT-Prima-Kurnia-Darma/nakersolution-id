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
) {
    companion object {
        fun createDummyLightningProtectionUiState(): LightningProtectionUiState {
            return LightningProtectionUiState(
                isLoading = false,
                inspectionReport = LightningProtectionInspectionReport(
                    extraId = "LP-001",
                    moreExtraId = "BATCH-LP-ABC",
                    serviceProviderData = LightningProtectionServiceProviderData(
                        companyName = "LightningGuard Services",
                        companyAddress = "111 Safety Way, Protection City, USA",
                        companyPermitNo = "LP-PERMIT-1234",
                        expertPermitNo = "EXP-LP-5678",
                        expertName = "Robert Storm",
                        testEquipmentUsed = "Earth Resistance Tester Model X"
                    ),
                    clientData = LightningProtectionClientData(
                        companyName = "Data Center Ops",
                        companyLocation = "Data City",
                        usageLocation = "Main Server Building",
                        objectType = "Building",
                        inspectionType = "Annual Inspection",
                        certificateNo = "CERT-LP-9876",
                        inspectionDate = "2023-10-27"
                    ),
                    technicalData = LightningProtectionTechnicalData(
                        conductorType = "Copper",
                        buildingHeight = "30 meters",
                        buildingArea = "5000 sq meters",
                        receiverHeight = "5 meters",
                        receiverCount = "3",
                        testJointCount = "4",
                        conductorDescription = "High conductivity copper conductor",
                        conductorTypeAndSize = "10mm²",
                        groundingResistance = "2.5 Ohm",
                        installationYear = "2015",
                        installer = "SafeInstallers Co."
                    ),
                    physicalInspection = LightningProtectionPhysicalInspection(
                        installationSystem = LightningProtectionConditionResult(good = true),
                        receiverHead = LightningProtectionConditionResult(good = true),
                        receiverPole = LightningProtectionConditionResult(good = true),
                        poleReinforcementSystem = LightningProtectionConditionResult(good = true),
                        downConductor = LightningProtectionConditionResult(good = true),
                        conductorClamps = LightningProtectionConditionResult(good = true),
                        jointConnections = LightningProtectionConditionResult(good = true),
                        downConductorBoxAndGroundingTerminal = LightningProtectionConditionResult(good = true),
                        controlBox = LightningProtectionConditionResult(good = true),
                        groundingSystem = LightningProtectionConditionResult(good = true),
                        downConductorDirectConnection = LightningProtectionConditionResult(good = true),
                        notes = "All visible components are in good condition."
                    ),
                    groundingSystemVisualInspection = LightningProtectionGroundingSystemVisualInspection(
                        airTerminal = "Visible and intact",
                        downConductor = "Securely fastened along the structure",
                        groundingAndTestJoint = "Test joints are accessible and clean"
                    ),
                    otherStandardsInspection = LightningProtectionOtherStandardsInspection(
                        installationPlacementAsPerDrawing = LightningProtectionCheckResult(checked = true, remarks = "Matches drawing"),
                        airTerminalConnectionToDownConductor = LightningProtectionCheckResult(checked = true, remarks = "Secure connection"),
                        downConductorJointsOnStructure = LightningProtectionCheckResult(checked = true, remarks = "All joints are present and appear solid"),
                        groundingElectrodeUsesTestJointBox = LightningProtectionCheckResult(checked = true, remarks = "Test joint boxes are used"),
                        downConductorMaterialStandard = LightningProtectionCheckResult(checked = true, remarks = "Compliant with standard"),
                        lightningCounterInstalled = LightningProtectionCheckResult(checked = false, remarks = "Not installed"),
                        airTerminalIsRadioactive = LightningProtectionCheckResult(checked = false, remarks = "Not applicable, standard air terminal used"),
                        groundingElectrodeMaterialQuality = LightningProtectionCheckResult(checked = true, remarks = "High quality material confirmed"),
                        overvoltageProtectionInstalled = LightningProtectionCheckResult(checked = true, remarks = "SPD installed on main power line")
                    ),
                    testingResults = LightningProtectionTestingResults(
                        groundingResistanceMeasurement = persistentListOf(
                            LightningProtectionGroundingMeasurementItem(
                                ecDistance = "10m",
                                epDistance = "20m",
                                rValueOhm = "2.1",
                                remarks = "Stable reading"
                            ),
                            LightningProtectionGroundingMeasurementItem(
                                ecDistance = "10m",
                                epDistance = "30m",
                                rValueOhm = "2.3",
                                remarks = "Stable reading"
                            )
                        ),
                        groundingResistanceTest = persistentListOf(
                            LightningProtectionGroundingTestItem(
                                itemChecked = "Grounding Electrode Resistance",
                                result = true,
                                measuredValue = "2.2 Ohm",
                                remarks = "Within acceptable limits (< 5 Ohm)"
                            ),
                            LightningProtectionGroundingTestItem(
                                itemChecked = "Down Conductor Continuity",
                                result = true,
                                measuredValue = "0 Ohm",
                                remarks = "Continuous path verified"
                            )
                        )
                    ),
                    conclusion = LightningProtectionConclusion(
                        summary = "The lightning protection system is generally in good condition and functioning as expected, with a grounding resistance within acceptable limits. However, a lightning counter is missing.",
                        recommendations = persistentListOf("Install a lightning counter.", "Regularly check grounding resistance during future inspections.")
                    )
                )
            )
        }
    }
}

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