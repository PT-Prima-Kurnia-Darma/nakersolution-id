package com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Fire Protection Installation inspection report.
 * This class is immutable.
 */
@Immutable
data class FireProtectionUiState(
    val isLoading: Boolean = false,
    val inspectionReport: FireProtectionInspectionReport = FireProtectionInspectionReport()
)

@Immutable
data class FireProtectionInspectionReport(
    val extraId: String = "",
    val documentChecklist: FireProtectionDocumentChecklist = FireProtectionDocumentChecklist(),
    val companyData: FireProtectionCompanyData = FireProtectionCompanyData(),
    val buildingData: FireProtectionBuildingData = FireProtectionBuildingData(),
    val automaticFireAlarmSpecifications: FireProtectionAutomaticFireAlarmSpecifications = FireProtectionAutomaticFireAlarmSpecifications(),
    val alarmInstallationTesting: FireProtectionAlarmInstallationTesting = FireProtectionAlarmInstallationTesting(),
    val hydrantSystemInstallation: FireProtectionHydrantSystemInstallation = FireProtectionHydrantSystemInstallation(),
    val pumpFunctionTest: ImmutableList<FireProtectionPumpFunctionTestItem> = persistentListOf(),
    val hydrantOperationalTest: ImmutableList<FireProtectionHydrantOperationalTestItem> = persistentListOf(),
    val conclusion: FireProtectionConclusion = FireProtectionConclusion()
)

@Immutable
data class FireProtectionChecklistResult(
    val isAvailable: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class FireProtectionDocumentChecklist(
    val technicalDrawing: FireProtectionChecklistResult = FireProtectionChecklistResult(),
    val previousTestDocumentation: FireProtectionChecklistResult = FireProtectionChecklistResult(),
    val requestLetter: FireProtectionChecklistResult = FireProtectionChecklistResult(),
    val specificationDocument: FireProtectionChecklistResult = FireProtectionChecklistResult()
)

@Immutable
data class FireProtectionCompanyData(
    val examinationType: String = "",
    val companyName: String = "",
    val companyLocation: String = "",
    val usageLocation: String = "",
    val certificateNumber: String = "",
    val objectType: String = "",
    val inspectionDate: String = ""
)

@Immutable
data class FireProtectionBuildingData(
    val landAreaSqm: String = "",
    val buildingAreaSqm: String = "",
    val buildingHeightM: String = "",
    val floorCount: String = "",
    val yearBuilt: String = "",
    val construction: FireProtectionConstruction = FireProtectionConstruction(),
    val fireProtectionEquipment: FireProtectionEquipment = FireProtectionEquipment()
)

@Immutable
data class FireProtectionConstruction(
    val mainStructure: String = "",
    val floorStructure: String = "",
    val exteriorWalls: String = "",
    val interiorWalls: String = "",
    val ceilingFrame: String = "",
    val ceilingCover: String = "",
    val roofFrame: String = "",
    val roofCover: String = ""
)

@Immutable
data class FireProtectionEquipment(
    val portableExtinguishers: Boolean = false,
    val indoorHydrantBox: Boolean = false,
    val pillarAndOutdoorHydrant: Boolean = false,
    val siameseConnection: Boolean = false,
    val sprinklerSystem: Boolean = false,
    val heatAndSmokeDetectors: Boolean = false,
    val exitSigns: Boolean = false,
    val emergencyStairs: Boolean = false,
    val assemblyPoint: Boolean = false
)

@Immutable
data class FireProtectionAutomaticFireAlarmSpecifications(
    val mcfa: FireProtectionMcfa = FireProtectionMcfa(),
    val heatDetector: FireProtectionDetector = FireProtectionDetector(),
    val smokeDetector: FireProtectionDetector = FireProtectionDetector(),
    val apar: FireProtectionApar = FireProtectionApar()
)

@Immutable
data class FireProtectionMcfa(
    val brandOrType: String = "",
    val ledAnnunciator: String = "",
    val type: String = "",
    val serialNumber: String = "",
    val result: String = "",
    val remarks: String = ""
)

@Immutable
data class FireProtectionDetector(
    val brandOrType: String = "",
    val pointCount: String = "",
    val spacingM: String = "",
    val operatingTemperatureC: String = "",
    val result: String = "",
    val remarks: String = ""
)

@Immutable
data class FireProtectionApar(
    val brandOrType: String = "",
    val count: String = "",
    val spacingM: String = "",
    val placement: String = "",
    val result: String = "",
    val remarks: String = ""
)

@Immutable
data class FireProtectionAlarmInstallationTesting(
    val panelFunction: String = "",
    val alarmTest: String = "",
    val faultTest: String = "",
    val interconnectionTest: String = "",
    val notes: String = ""
)

@Immutable
data class FireProtectionHydrantSystemInstallation(
    val waterSource: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val groundReservoir: FireProtectionGroundReservoir = FireProtectionGroundReservoir(),
    val gravitationTank: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val siameseConnection: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val jockeyPump: FireProtectionJockeyPump = FireProtectionJockeyPump(),
    val electricPump: FireProtectionElectricPump = FireProtectionElectricPump(),
    val dieselPump: FireProtectionDieselPump = FireProtectionDieselPump(),
    val buildingHydrant: FireProtectionBuildingHydrant = FireProtectionBuildingHydrant(),
    val landingValve: FireProtectionLandingValve = FireProtectionLandingValve(),
    val yardHydrant: FireProtectionYardHydrant = FireProtectionYardHydrant(),
    val fireServiceConnection: FireProtectionFireServiceConnection = FireProtectionFireServiceConnection(),
    val pressureReliefValve: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val testValve: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val suctionPipe: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val mainPipe: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val standPipe: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val hydrantPillar: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val indoorHydrant: FireProtectionSystemComponent = FireProtectionSystemComponent(),
    val hoseReel: FireProtectionSystemComponent = FireProtectionSystemComponent()
)

@Immutable
data class FireProtectionSystemComponent(
    val specification: String = "",
    val status: String = "",
    val remarks: String = ""
)

@Immutable
data class FireProtectionGroundReservoir(
    val calculation: String = "",
    val backupCapacityM3: String = "",
    val status: String = "",
    val remarks: String = ""
)

@Immutable
data class FireProtectionJockeyPump(
    val model: String = "",
    val serialNumber: String = "",
    val driver: String = "",
    val powerRpm: String = "",
    val placement: String = "",
    val quantityM3H: String = "",
    val headM: String = "",
    val autoStartKgCm2: String = "",
    val autoStopKgCm2: String = ""
)

@Immutable
data class FireProtectionElectricPump(
    val model: String = "",
    val serialNumber: String = "",
    val driver: String = "",
    val powerRpm: String = "",
    val placement: String = "",
    val quantityGpm: String = "",
    val headM: String = "",
    val autoStartKgCm2: String = "",
    val stop: String = ""
)

@Immutable
data class FireProtectionDieselPump(
    val model: String = "",
    val serialNumber: String = "",
    val driver: String = "",
    val quantityUsGpm: String = "",
    val headM: String = "",
    val autoStartKgCm2: String = "",
    val stop: String = ""
)

@Immutable
data class FireProtectionBuildingHydrant(
    val points: String = "",
    val outletDiameterInch: String = "",
    val hoseLengthM: String = "",
    val nozzleDiameterInch: String = "",
    val placement: String = ""
)

@Immutable
data class FireProtectionLandingValve(
    val points: String = "",
    val outletDiameterInch: String = "",
    val couplingType: String = "",
    val placement: String = ""
)

@Immutable
data class FireProtectionYardHydrant(
    val points: String = "",
    val outletDiameterInch: String = "",
    val hoseLengthM: String = "",
    val nozzleDiameterInch: String = "",
    val placement: String = ""
)

@Immutable
data class FireProtectionFireServiceConnection(
    val points: String = "",
    val inletDiameterInch: String = "",
    val outletDiameterInch: String = "",
    val couplingType: String = "",
    val condition: String = "",
    val placement: String = ""
)

@Immutable
data class FireProtectionPumpFunctionTestItem(
    val pumpType: String = "",
    val startPressure: String = "",
    val stopPressure: String = ""
)

@Immutable
data class FireProtectionHydrantOperationalTestItem(
    val testPoint: String = "",
    val pressure: String = "",
    val jetLength: String = "",
    val nozzlePosition: String = "",
    val status: String = "",
    val remarks: String = ""
)

@Immutable
data class FireProtectionConclusion(
    val summary: String = "",
    val recommendations: ImmutableList<String> = persistentListOf()
)