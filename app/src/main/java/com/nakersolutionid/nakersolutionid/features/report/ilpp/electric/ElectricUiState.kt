package com.nakersolutionid.nakersolutionid.features.report.ilpp.electric

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Electrical Installation inspection report.
 * This class is immutable.
 */
@Immutable
data class ElectricalUiState(
    val isLoading: Boolean = false,
    val electricalInspectionReport: ElectricalInspectionReport = ElectricalInspectionReport()
)

@Immutable
data class ElectricalInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val generalData: ElectricalGeneralData = ElectricalGeneralData(),
    val technicalData: ElectricalTechnicalData = ElectricalTechnicalData(),
    val initialDocumentVerification: ElectricalInitialDocumentVerification = ElectricalInitialDocumentVerification(),
    val inspectionAndTesting: ElectricalInspectionAndTesting = ElectricalInspectionAndTesting(),
    val conclusion: ElectricalConclusion = ElectricalConclusion()
)

@Immutable
data class ElectricalGeneralData(
    val companyName: String = "",
    val companyAddress: String = "",
    val installationType: String = "",
    val examinationType: String = "",
    val businessField: String = "",
    val safetyServiceProvider: String = "",
    val ohsExpert: String = "",
    val permitNumber: String = "",
    val inspectionDate: String = "",
    val inspectionLocation: String = ""
)

@Immutable
data class ElectricalTechnicalData(
    val powerSource: ElectricalPowerSource = ElectricalPowerSource(),
    val loadSystem: ElectricalLoadSystem = ElectricalLoadSystem(),
    val currentVoltageType: String = ""
)

@Immutable
data class ElectricalPowerSource(
    val plnKva: String = "",
    val dieselGeneratorKva: String = ""
)

@Immutable
data class ElectricalLoadSystem(
    val totalInstalledLoadWatt: String = "",
    val lightingPowerWatt: String = "",
    val powerLoadWatt: String = ""
)

@Immutable
data class ElectricalInitialDocumentVerification(
    val singleLineDiagram: ElectricalVerificationResult = ElectricalVerificationResult(),
    val layout: ElectricalVerificationResult = ElectricalVerificationResult(),
    val usagePermitCertificate: ElectricalVerificationResult = ElectricalVerificationResult(),
    val technicianLicense: ElectricalVerificationResult = ElectricalVerificationResult()
)

@Immutable
data class ElectricalVerificationResult(
    val isAvailable: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class ElectricalTestResult(
    val result: Boolean = false,
    val method: String = ""
)

@Immutable
data class ElectricalInspectionAndTesting(
    val documentExaminationPart1: ElectricalDocumentExaminationPart1 = ElectricalDocumentExaminationPart1(),
    val documentExaminationPart2: ElectricalDocumentExaminationPart2 = ElectricalDocumentExaminationPart2(),
    val mainTesting: ElectricalMainTesting = ElectricalMainTesting(),
    val sdpVisualInspection: ElectricalSdpVisualInspection = ElectricalSdpVisualInspection(),
    val sdpTesting: ElectricalSdpTesting = ElectricalSdpTesting()
)

@Immutable
data class ElectricalDocumentExaminationPart1(
    val planningHasPermit: ElectricalTestResult = ElectricalTestResult(),
    val locationMapExists: ElectricalTestResult = ElectricalTestResult(),
    val singleLineDiagramExists: ElectricalTestResult = ElectricalTestResult(),
    val layoutDiagramExists: ElectricalTestResult = ElectricalTestResult(),
    val wiringDiagramExists: ElectricalTestResult = ElectricalTestResult(),
    val areaClarificationDrawingExists: ElectricalTestResult = ElectricalTestResult(),
    val panelComponentListExists: ElectricalTestResult = ElectricalTestResult(),
    val shortCircuitCalculationExists: ElectricalTestResult = ElectricalTestResult(),
    val manualBookExists: ElectricalTestResult = ElectricalTestResult(),
    val maintenanceAndOperationBookExists: ElectricalTestResult = ElectricalTestResult(),
    val warningSignsInstalled: ElectricalTestResult = ElectricalTestResult(),
    val manufacturerCertificationExists: ElectricalTestResult = ElectricalTestResult(),
    val equipmentTechnicalSpecsExists: ElectricalTestResult = ElectricalTestResult(),
    val equipmentCertificationAndSpecsExists: ElectricalTestResult = ElectricalTestResult(),
    val powerRecapitulationCalculationExists: ElectricalTestResult = ElectricalTestResult(),
    val dailyRecordExists: ElectricalTestResult = ElectricalTestResult(),
    val panelCoverCondition: ElectricalTestResult = ElectricalTestResult(),
    val otherSupportingDataExists: ElectricalTestResult = ElectricalTestResult(),
    // ADDED: Field to match the DTO property `hasPanelPointCount`
    val hasPanelPointCount: ElectricalTestResult = ElectricalTestResult()
)

@Immutable
data class ElectricalDocumentExaminationPart2(
    val unitConstructionLvmdpSdp: ElectricalTestResult = ElectricalTestResult(),
    val mountingAndPlacement: ElectricalTestResult = ElectricalTestResult(),
    val nameplateVerification: ElectricalTestResult = ElectricalTestResult(),
    val areaClassification: ElectricalTestResult = ElectricalTestResult(),
    val protectionAgainstElectricShock: ElectricalTestResult = ElectricalTestResult(),
    val radiationProtection: ElectricalTestResult = ElectricalTestResult(),
    val panelDoorStaysOpen: ElectricalTestResult = ElectricalTestResult(),
    val boltsAndScrewsTightened: ElectricalTestResult = ElectricalTestResult(),
    val busbarInsulation: ElectricalTestResult = ElectricalTestResult(),
    val busbarClearance: ElectricalTestResult = ElectricalTestResult(),
    val cableInstallation: ElectricalTestResult = ElectricalTestResult(),
    val panelDoorCableProtection: ElectricalTestResult = ElectricalTestResult(),
    val fuseReplacementSafety: ElectricalTestResult = ElectricalTestResult(),
    val cableTerminalProtection: ElectricalTestResult = ElectricalTestResult(),
    val measuringInstrumentsMarking: ElectricalTestResult = ElectricalTestResult(),
    val equipmentAndTerminalLabeling: ElectricalTestResult = ElectricalTestResult(),
    val incomingOutgoingCableInstallation: ElectricalTestResult = ElectricalTestResult(),
    val busbarSize: ElectricalTestResult = ElectricalTestResult(),
    val busbarCleanliness: ElectricalTestResult = ElectricalTestResult(),
    val busbarPhaseMarking: ElectricalTestResult = ElectricalTestResult(),
    val groundingCableInstallation: ElectricalTestResult = ElectricalTestResult(),
    val panelDoorInstallation: ElectricalTestResult = ElectricalTestResult(),
    val sparepartsSpecificationCompliance: ElectricalTestResult = ElectricalTestResult(),
    val safetyFacilitiesAndDangerSigns: ElectricalTestResult = ElectricalTestResult(),
    val circuitBreakerDataCheck: ElectricalTestResult = ElectricalTestResult(),
    val circuitBreakerCurrentRating: ElectricalTestResult = ElectricalTestResult(),
    val circuitBreakerVoltageRating: ElectricalTestResult = ElectricalTestResult(),
    val circuitBreakerInterruptingRating: ElectricalTestResult = ElectricalTestResult(),
    val circuitBreakerControlVoltage: ElectricalTestResult = ElectricalTestResult(),
    val circuitBreakerManufacturer: ElectricalTestResult = ElectricalTestResult(),
    val circuitBreakerType: ElectricalTestResult = ElectricalTestResult(),
    val circuitBreakerSerialNumber: ElectricalTestResult = ElectricalTestResult()
)

@Immutable
data class ElectricalMainTesting(
    val insulationResistance: ElectricalTestResult = ElectricalTestResult(),
    val groundingResistance: ElectricalTestResult = ElectricalTestResult(),
    val circuitBreakerEquipment: ElectricalTestResult = ElectricalTestResult(),
    val currentTransformer: ElectricalTestResult = ElectricalTestResult(),
    val voltageTransformer: ElectricalTestResult = ElectricalTestResult(),
    val measuringInstrument: ElectricalTestResult = ElectricalTestResult(),
    val fuseRating: ElectricalTestResult = ElectricalTestResult(),
    val mechanicalBreaker: ElectricalTestResult = ElectricalTestResult(),
    val cableTerminal: ElectricalTestResult = ElectricalTestResult(),
    val terminalMarking: ElectricalTestResult = ElectricalTestResult(),
    val interlockSystem: ElectricalTestResult = ElectricalTestResult(),
    val auxiliarySwitch: ElectricalTestResult = ElectricalTestResult(),
    val mechanicalTripFunction: ElectricalTestResult = ElectricalTestResult(),
    val overloadTripTest: ElectricalTestResult = ElectricalTestResult(),
    val reversePowerRelayTest: ElectricalTestResult = ElectricalTestResult(),
    val reverseCurrentRelayTest: ElectricalTestResult = ElectricalTestResult(),
    val breakerTripTest: ElectricalTestResult = ElectricalTestResult(),
    val temperatureMeasurement: ElectricalTestResult = ElectricalTestResult(),
    val indicatorLightFunction: ElectricalTestResult = ElectricalTestResult(),
    val meterDeviationTest: ElectricalTestResult = ElectricalTestResult(),
    val synchronizationFunctionTest: ElectricalTestResult = ElectricalTestResult(),
    val conductorAmpacity: ElectricalTestResult = ElectricalTestResult(),
    val protectionRating: ElectricalTestResult = ElectricalTestResult(),
    val voltageDrop: ElectricalTestResult = ElectricalTestResult(),
    val lossConnection: ElectricalTestResult = ElectricalTestResult()
)

@Immutable
data class ElectricalSdpVisualInspection(
    val frontView: ElectricalSdpFrontView = ElectricalSdpFrontView(),
    val internalViews: ImmutableList<ElectricalSdpInternalViewItem> = persistentListOf(),
    val terminalSystem: ElectricalSdpTerminalSystem = ElectricalSdpTerminalSystem()
)

@Immutable
data class ElectricalSdpFrontView(
    val panelIndicatorLights: ElectricalTestResult = ElectricalTestResult(),
    val panelDoorClearance: ElectricalTestResult = ElectricalTestResult(),
    val lighting: ElectricalTestResult = ElectricalTestResult(),
    val lightingProductionRoom: ElectricalTestResult = ElectricalTestResult(),
    val lightingOffice: ElectricalTestResult = ElectricalTestResult(),
    val lightingMainPanel: ElectricalTestResult = ElectricalTestResult(),
    val lightingWarehouse: ElectricalTestResult = ElectricalTestResult(),
    val unusedItemsClearance: ElectricalTestResult = ElectricalTestResult(),
    val dangerSignOnMainPanelDoor: ElectricalTestResult = ElectricalTestResult()
)

@Immutable
data class ElectricalSdpInternalViewItem(
    val floor: Int = 0,
    val inspections: ElectricalSdpInternalInspections = ElectricalSdpInternalInspections()
)

@Immutable
data class ElectricalSdpInternalInspections(
    val touchVoltageProtectionCover: ElectricalTestResult = ElectricalTestResult(),
    val sldAndMaintenanceCard: ElectricalTestResult = ElectricalTestResult(),
    val bondingCable: ElectricalTestResult = ElectricalTestResult(),
    val labeling: ElectricalTestResult = ElectricalTestResult(),
    val cableColorCode: ElectricalTestResult = ElectricalTestResult(),
    val panelCleanliness: ElectricalTestResult = ElectricalTestResult(),
    val installationNeatness: ElectricalTestResult = ElectricalTestResult()
)

@Immutable
data class ElectricalSdpTerminalSystem(
    val busbar: ElectricalTestResult = ElectricalTestResult(),
    val breaker: ElectricalTestResult = ElectricalTestResult(),
    val cableLugs: ElectricalTestResult = ElectricalTestResult(),
    val groundingSystem: ElectricalTestResult = ElectricalTestResult(),
    val busbarToBusbarDistance: ElectricalTestResult = ElectricalTestResult()
)

@Immutable
data class ElectricalSdpTesting(
    val voltagePhaseRSTN: ElectricalTestResult = ElectricalTestResult(),
    val currentPhaseRSTN: ElectricalTestResult = ElectricalTestResult(),
    val meteringFunction: ElectricalTestResult = ElectricalTestResult(),
    val panelLabeling: ElectricalTestResult = ElectricalTestResult(),
    val dangerSignOnPanelDoor: ElectricalTestResult = ElectricalTestResult(),
    val selectorSwitchAndLock: ElectricalTestResult = ElectricalTestResult(),
    val conductorTerminalHeat: ElectricalTestResult = ElectricalTestResult(),
    val groundingTest: ElectricalTestResult = ElectricalTestResult(),
    val mainConductorAmpacity: ElectricalTestResult = ElectricalTestResult(),
    val mainProtectionRating: ElectricalTestResult = ElectricalTestResult()
)

@Immutable
data class ElectricalConclusion(
    val findings: ImmutableList<String> = persistentListOf(),
    val summary: ImmutableList<String> = persistentListOf(),
    val recommendations: ImmutableList<String> = persistentListOf()
)