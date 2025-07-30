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
) {
    companion object {
        fun createDummyElectricalUiState(): ElectricalUiState {
            return ElectricalUiState(
                isLoading = false,
                electricalInspectionReport = ElectricalInspectionReport(
                    extraId = "ELECT-001",
                    moreExtraId = "BATCH-XYZ-ELECT",
                    generalData = ElectricalGeneralData(
                        companyName = "Tech Solutions Inc.",
                        companyAddress = "101 Innovation Drive, Tech City, USA",
                        installationType = "Industrial Facility",
                        examinationType = "Routine Inspection",
                        businessField = "Manufacturing",
                        safetyServiceProvider = "SafeSys Corp",
                        ohsExpert = "Jane Smith",
                        permitNumber = "ELECT-PERMIT-5555",
                        inspectionDate = "2023-10-27",
                        inspectionLocation = "Main Plant Area"
                    ),
                    technicalData = ElectricalTechnicalData(
                        powerSource = ElectricalPowerSource(
                            plnKva = "1000 KVA",
                            dieselGeneratorKva = "500 KVA"
                        ),
                        loadSystem = ElectricalLoadSystem(
                            totalInstalledLoadWatt = "800000 W",
                            lightingPowerWatt = "100000 W",
                            powerLoadWatt = "700000 W"
                        ),
                        currentVoltageType = "AC, 3 Phase, 400V"
                    ),
                    initialDocumentVerification = ElectricalInitialDocumentVerification(
                        singleLineDiagram = ElectricalVerificationResult(isAvailable = true, remarks = "Complete"),
                        layout = ElectricalVerificationResult(isAvailable = true, remarks = "Approved"),
                        usagePermitCertificate = ElectricalVerificationResult(isAvailable = true, remarks = "Valid"),
                        technicianLicense = ElectricalVerificationResult(isAvailable = true, remarks = "Certified")
                    ),
                    inspectionAndTesting = ElectricalInspectionAndTesting(
                        documentExaminationPart1 = ElectricalDocumentExaminationPart1(
                            planningHasPermit = ElectricalTestResult(result = true, method = "Review"),
                            locationMapExists = ElectricalTestResult(result = true, method = "Review"),
                            singleLineDiagramExists = ElectricalTestResult(result = true, method = "Review"),
                            layoutDiagramExists = ElectricalTestResult(result = true, method = "Review"),
                            wiringDiagramExists = ElectricalTestResult(result = true, method = "Review"),
                            areaClarificationDrawingExists = ElectricalTestResult(result = true, method = "Review"),
                            panelComponentListExists = ElectricalTestResult(result = true, method = "Review"),
                            shortCircuitCalculationExists = ElectricalTestResult(result = true, method = "Review"),
                            manualBookExists = ElectricalTestResult(result = true, method = "Review"),
                            maintenanceAndOperationBookExists = ElectricalTestResult(result = true, method = "Review"),
                            warningSignsInstalled = ElectricalTestResult(result = true, method = "Visual"),
                            manufacturerCertificationExists = ElectricalTestResult(result = true, method = "Review"),
                            equipmentTechnicalSpecsExists = ElectricalTestResult(result = true, method = "Review"),
                            equipmentCertificationAndSpecsExists = ElectricalTestResult(result = true, method = "Review"),
                            powerRecapitulationCalculationExists = ElectricalTestResult(result = true, method = "Review"),
                            dailyRecordExists = ElectricalTestResult(result = true, method = "Review"),
                            panelCoverCondition = ElectricalTestResult(result = true, method = "Visual"),
                            otherSupportingDataExists = ElectricalTestResult(result = true, method = "Review"),
                            hasPanelPointCount = ElectricalTestResult(result = true, method = "Review")
                        ),
                        documentExaminationPart2 = ElectricalDocumentExaminationPart2(
                            unitConstructionLvmdpSdp = ElectricalTestResult(result = true, method = "Visual"),
                            mountingAndPlacement = ElectricalTestResult(result = true, method = "Visual"),
                            nameplateVerification = ElectricalTestResult(result = true, method = "Visual"),
                            areaClassification = ElectricalTestResult(result = true, method = "Visual"),
                            protectionAgainstElectricShock = ElectricalTestResult(result = true, method = "Visual"),
                            radiationProtection = ElectricalTestResult(result = true, method = "Visual"),
                            panelDoorStaysOpen = ElectricalTestResult(result = true, method = "Test"),
                            boltsAndScrewsTightened = ElectricalTestResult(result = true, method = "Visual"),
                            busbarInsulation = ElectricalTestResult(result = true, method = "Visual"),
                            busbarClearance = ElectricalTestResult(result = true, method = "Visual"),
                            cableInstallation = ElectricalTestResult(result = true, method = "Visual"),
                            panelDoorCableProtection = ElectricalTestResult(result = true, method = "Visual"),
                            fuseReplacementSafety = ElectricalTestResult(result = true, method = "Visual"),
                            cableTerminalProtection = ElectricalTestResult(result = true, method = "Visual"),
                            measuringInstrumentsMarking = ElectricalTestResult(result = true, method = "Visual"),
                            equipmentAndTerminalLabeling = ElectricalTestResult(result = true, method = "Visual"),
                            incomingOutgoingCableInstallation = ElectricalTestResult(result = true, method = "Visual"),
                            busbarSize = ElectricalTestResult(result = true, method = "Visual"),
                            busbarCleanliness = ElectricalTestResult(result = true, method = "Visual"),
                            busbarPhaseMarking = ElectricalTestResult(result = true, method = "Visual"),
                            groundingCableInstallation = ElectricalTestResult(result = true, method = "Visual"),
                            panelDoorInstallation = ElectricalTestResult(result = true, method = "Visual"),
                            sparepartsSpecificationCompliance = ElectricalTestResult(result = true, method = "Review"),
                            safetyFacilitiesAndDangerSigns = ElectricalTestResult(result = true, method = "Visual"),
                            circuitBreakerDataCheck = ElectricalTestResult(result = true, method = "Review"),
                            circuitBreakerCurrentRating = ElectricalTestResult(result = true, method = "Review"),
                            circuitBreakerVoltageRating = ElectricalTestResult(result = true, method = "Review"),
                            circuitBreakerInterruptingRating = ElectricalTestResult(result = true, method = "Review"),
                            circuitBreakerControlVoltage = ElectricalTestResult(result = true, method = "Review"),
                            circuitBreakerManufacturer = ElectricalTestResult(result = true, method = "Review"),
                            circuitBreakerType = ElectricalTestResult(result = true, method = "Review"),
                            circuitBreakerSerialNumber = ElectricalTestResult(result = true, method = "Review")
                        ),
                        mainTesting = ElectricalMainTesting(
                            insulationResistance = ElectricalTestResult(result = true, method = "Megger Test"),
                            groundingResistance = ElectricalTestResult(result = true, method = "Earth Tester"),
                            circuitBreakerEquipment = ElectricalTestResult(result = true, method = "Trip Test"),
                            currentTransformer = ElectricalTestResult(result = true, method = "Ratio Test"),
                            voltageTransformer = ElectricalTestResult(result = true, method = "Ratio Test"),
                            measuringInstrument = ElectricalTestResult(result = true, method = "Calibration Check"),
                            fuseRating = ElectricalTestResult(result = true, method = "Visual"),
                            mechanicalBreaker = ElectricalTestResult(result = true, method = "Operation Test"),
                            cableTerminal = ElectricalTestResult(result = true, method = "Torque Check"),
                            terminalMarking = ElectricalTestResult(result = true, method = "Visual"),
                            interlockSystem = ElectricalTestResult(result = true, method = "Functional Test"),
                            auxiliarySwitch = ElectricalTestResult(result = true, method = "Functional Test"),
                            mechanicalTripFunction = ElectricalTestResult(result = true, method = "Test"),
                            overloadTripTest = ElectricalTestResult(result = true, method = "Test"),
                            reversePowerRelayTest = ElectricalTestResult(result = true, method = "Test"),
                            reverseCurrentRelayTest = ElectricalTestResult(result = true, method = "Test"),
                            breakerTripTest = ElectricalTestResult(result = true, method = "Test"),
                            temperatureMeasurement = ElectricalTestResult(result = true, method = "Thermal Camera"),
                            indicatorLightFunction = ElectricalTestResult(result = true, method = "Test"),
                            meterDeviationTest = ElectricalTestResult(result = true, method = "Test"),
                            synchronizationFunctionTest = ElectricalTestResult(result = true, method = "Test"),
                            conductorAmpacity = ElectricalTestResult(result = true, method = "Calculation Check"),
                            protectionRating = ElectricalTestResult(result = true, method = "Review"),
                            voltageDrop = ElectricalTestResult(result = true, method = "Calculation Check"),
                            lossConnection = ElectricalTestResult(result = true, method = "Visual")
                        ),
                        sdpVisualInspection = ElectricalSdpVisualInspection(
                            frontView = ElectricalSdpFrontView(
                                panelIndicatorLights = ElectricalTestResult(result = true, method = "Visual"),
                                panelDoorClearance = ElectricalTestResult(result = true, method = "Visual"),
                                lighting = ElectricalTestResult(result = true, method = "Visual"),
                                lightingProductionRoom = ElectricalTestResult(result = true, method = "Visual"),
                                lightingOffice = ElectricalTestResult(result = true, method = "Visual"),
                                lightingMainPanel = ElectricalTestResult(result = true, method = "Visual"),
                                lightingWarehouse = ElectricalTestResult(result = true, method = "Visual"),
                                unusedItemsClearance = ElectricalTestResult(result = true, method = "Visual"),
                                dangerSignOnMainPanelDoor = ElectricalTestResult(result = true, method = "Visual")
                            ),
                            internalViews = persistentListOf(
                                ElectricalSdpInternalViewItem(
                                    floor = 1,
                                    inspections = ElectricalSdpInternalInspections(
                                        touchVoltageProtectionCover = ElectricalTestResult(result = true, method = "Visual"),
                                        sldAndMaintenanceCard = ElectricalTestResult(result = true, method = "Visual"),
                                        bondingCable = ElectricalTestResult(result = true, method = "Visual"),
                                        labeling = ElectricalTestResult(result = true, method = "Visual"),
                                        cableColorCode = ElectricalTestResult(result = true, method = "Visual"),
                                        panelCleanliness = ElectricalTestResult(result = true, method = "Visual"),
                                        installationNeatness = ElectricalTestResult(result = true, method = "Visual")
                                    )
                                ),
                                ElectricalSdpInternalViewItem(
                                    floor = 2,
                                    inspections = ElectricalSdpInternalInspections(
                                        touchVoltageProtectionCover = ElectricalTestResult(result = true, method = "Visual"),
                                        sldAndMaintenanceCard = ElectricalTestResult(result = true, method = "Visual"),
                                        bondingCable = ElectricalTestResult(result = true, method = "Visual"),
                                        labeling = ElectricalTestResult(result = true, method = "Visual"),
                                        cableColorCode = ElectricalTestResult(result = true, method = "Visual"),
                                        panelCleanliness = ElectricalTestResult(result = true, method = "Visual"),
                                        installationNeatness = ElectricalTestResult(result = true, method = "Visual")
                                    )
                                )
                            ),
                            terminalSystem = ElectricalSdpTerminalSystem(
                                busbar = ElectricalTestResult(result = true, method = "Visual"),
                                breaker = ElectricalTestResult(result = true, method = "Visual"),
                                cableLugs = ElectricalTestResult(result = true, method = "Visual"),
                                groundingSystem = ElectricalTestResult(result = true, method = "Visual"),
                                busbarToBusbarDistance = ElectricalTestResult(result = true, method = "Visual")
                            )
                        ),
                        sdpTesting = ElectricalSdpTesting(
                            voltagePhaseRSTN = ElectricalTestResult(result = true, method = "Voltage Measurement"),
                            currentPhaseRSTN = ElectricalTestResult(result = true, method = "Current Measurement"),
                            meteringFunction = ElectricalTestResult(result = true, method = "Test"),
                            panelLabeling = ElectricalTestResult(result = true, method = "Visual"),
                            dangerSignOnPanelDoor = ElectricalTestResult(result = true, method = "Visual"),
                            selectorSwitchAndLock = ElectricalTestResult(result = true, method = "Test"),
                            conductorTerminalHeat = ElectricalTestResult(result = true, method = "Thermal Imaging"),
                            groundingTest = ElectricalTestResult(result = true, method = "Resistance Test"),
                            mainConductorAmpacity = ElectricalTestResult(result = true, method = "Calculation"),
                            mainProtectionRating = ElectricalTestResult(result = true, method = "Review")
                        )
                    ),
                    conclusion = ElectricalConclusion(
                        findings = persistentListOf("All systems appear to be in good working order."),
                        summary = persistentListOf("The electrical installation meets safety and operational standards."),
                        recommendations = persistentListOf("Continue regular maintenance schedule.")
                    )
                )
            )
        }
    }
}

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