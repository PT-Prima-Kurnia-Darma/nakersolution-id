package com.nakersolutionid.nakersolutionid.features.report.pubt.general

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the General inspection report.
 * This class is immutable.
 */
@Immutable
data class GeneralUiState(
    val isLoading: Boolean = false,
    val inspectionReport: GeneralInspectionReport = GeneralInspectionReport()
)

@Immutable
data class GeneralInspectionReport(
    val extraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val generalData: GeneralData = GeneralData(),
    val technicalData: GeneralTechnicalData = GeneralTechnicalData(),
    val inspectionAndMeasurement: GeneralInspectionAndMeasurement = GeneralInspectionAndMeasurement(),
    val conclusion: GeneralConclusion = GeneralConclusion()
)

@Immutable
data class GeneralData(
    val ownerName: String = "",
    val ownerAddress: String = "",
    val user: String = "",
    val userAddress: String = "",
    val operatorName: String = "",
    val manufacturer: String = "",
    val brandModelType: String = "",
    val driveType: String = "",
    val countryAndYearOfManufacture: String = "",
    val serialNumber: String = "",
    val designPressureKgCm2: String = "",
    val maxAllowableWorkingPressureKgCm2: String = "",
    val capacityKgH: String = "",
    val steamTemperature: String = "",
    val operatingPressureKgCm2: String = "",
    val fuelType: String = "",
    val intendedUse: String = "",
    val permitNumber: String = "",
    val operatorCertificate: String = "",
    val equipmentHistory: String = "",
    val inspectionDate: String = ""
)

@Immutable
data class GeneralTechnicalData(
    val shell: GeneralShell = GeneralShell(),
    val heads: GeneralHeads = GeneralHeads(),
    val tubePlate: GeneralTubePlate = GeneralTubePlate(),
    val furnace: GeneralFurnace = GeneralFurnace(),
    val waterTubes: GeneralWaterTubes = GeneralWaterTubes()
)

@Immutable
data class GeneralShell(
    val numberOfRounds: String = "",
    val connectionMethod: String = "",
    val material: String = "",
    val pipeDiameterMm: String = "",
    val thicknessMm: String = "",
    val bodyLengthMm: String = ""
)

@Immutable
data class GeneralHeads(
    val type: String = "",
    val topDiameterMm: String = "",
    val topThicknessMm: String = "",
    val rearDiameterMm: String = "",
    val rearThicknessMm: String = ""
)

@Immutable
data class GeneralTubePlate(
    val frontDim1Mm: String = "",
    val frontDim2Mm: String = "",
    val rearDim1Mm: String = "",
    val rearDim2Mm: String = ""
)

@Immutable
data class GeneralFurnace(
    val type: String = "",
    val material: String = "",
    val outerDiameterMm: String = "",
    val innerDiameterMm: String = "",
    val thicknessMm: String = ""
)

@Immutable
data class GeneralWaterTubes(
    val firstPass: GeneralTubePass = GeneralTubePass(),
    val secondPass: GeneralTubePass = GeneralTubePass(),
    val stayTube: GeneralTubePass = GeneralTubePass(),
    val material: String = "",
    val connectionMethod: String = ""
)

@Immutable
data class GeneralTubePass(
    val diameterMm: String = "",
    val thicknessMm: String = "",
    val lengthMm: String = "",
    val quantity: String = ""
)

@Immutable
data class GeneralInspectionAndMeasurement(
    val visualInspection: GeneralVisualInspection = GeneralVisualInspection(),
    val materialThickness: GeneralMaterialThickness = GeneralMaterialThickness(),
    val thicknessMeasurementSetup: GeneralThicknessMeasurementSetup = GeneralThicknessMeasurementSetup(),
    val measurementResultsTable: ImmutableList<GeneralMeasurementResultItem> = persistentListOf(),
    val ndtTests: GeneralNdtTests = GeneralNdtTests(),
    val hydrotest: GeneralHydrotest = GeneralHydrotest(),
    val appendagesInspection: GeneralAppendagesInspection = GeneralAppendagesInspection(),
    val safetyValveTest: GeneralSafetyValveTest = GeneralSafetyValveTest()
)

@Immutable
data class GeneralCheckResult(
    val isMet: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class GeneralVisualInspection(
    val steamEquipmentShellDrum: GeneralCheckResult = GeneralCheckResult(),
    val steamEquipmentBouileur: GeneralCheckResult = GeneralCheckResult(),
    val steamEquipmentFurnace: GeneralCheckResult = GeneralCheckResult(),
    val steamEquipmentFireChamber: GeneralCheckResult = GeneralCheckResult(),
    val steamEquipmentRefractory: GeneralCheckResult = GeneralCheckResult(),
    val steamEquipmentCombustionChamber: GeneralCheckResult = GeneralCheckResult(),
    val steamEquipmentFireTubes: GeneralCheckResult = GeneralCheckResult(),
    val steamEquipmentSuperHeater: GeneralCheckResult = GeneralCheckResult(),
    val steamEquipmentReheater: GeneralCheckResult = GeneralCheckResult(),
    val steamEquipmentEconomizer: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsGrate: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsBurner: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsFdf: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsIdf: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsAirHeater: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsAirDuct: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsGasDuct: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsAshCatcher: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsChimney: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsStairs: GeneralCheckResult = GeneralCheckResult(),
    val boilerDetailsInsulation: GeneralCheckResult = GeneralCheckResult(),
    val safetyValveRing: GeneralCheckResult = GeneralCheckResult(),
    val safetyValvePipe: GeneralCheckResult = GeneralCheckResult(),
    val safetyValveExhaust: GeneralCheckResult = GeneralCheckResult(),
    val pressureGaugeMark: GeneralCheckResult = GeneralCheckResult(),
    val pressureGaugeSiphon: GeneralCheckResult = GeneralCheckResult(),
    val pressureGaugeCock: GeneralCheckResult = GeneralCheckResult(),
    val gaugeGlassTryCocks: GeneralCheckResult = GeneralCheckResult(),
    val gaugeGlassBlowdown: GeneralCheckResult = GeneralCheckResult(),
    val waterLevelLowestMark: GeneralCheckResult = GeneralCheckResult(),
    val waterLevelPosition: GeneralCheckResult = GeneralCheckResult(),
    val feedwaterPump: GeneralCheckResult = GeneralCheckResult(),
    val feedwaterCapacity: GeneralCheckResult = GeneralCheckResult(),
    val feedwaterMotor: GeneralCheckResult = GeneralCheckResult(),
    val feedwaterCheckValve: GeneralCheckResult = GeneralCheckResult(),
    val controlBlacksFluit: GeneralCheckResult = GeneralCheckResult(),
    val controlFusiblePlug: GeneralCheckResult = GeneralCheckResult(),
    val controlWaterLevel: GeneralCheckResult = GeneralCheckResult(),
    val controlSteamPressure: GeneralCheckResult = GeneralCheckResult(),
    val blowdownDesc: GeneralCheckResult = GeneralCheckResult(),
    val blowdownMaterial: GeneralCheckResult = GeneralCheckResult(),
    val manholeManhole: GeneralCheckResult = GeneralCheckResult(),
    val manholeInspectionHole: GeneralCheckResult = GeneralCheckResult(),
    val idMarkNameplate: GeneralCheckResult = GeneralCheckResult(),
    val idMarkDataMatch: GeneralCheckResult = GeneralCheckResult(),
    val idMarkForm9Stamp: GeneralCheckResult = GeneralCheckResult()
)

@Immutable
data class GeneralValueResult(
    val value: String = "",
    val remarks: String = ""
)

@Immutable
data class GeneralMaterialThickness(
    val shellThicknessMm: GeneralValueResult = GeneralValueResult(),
    val shellDiameterMm: GeneralValueResult = GeneralValueResult(),
    val headerDiameterMm: GeneralValueResult = GeneralValueResult(),
    val headerThicknessMm: GeneralValueResult = GeneralValueResult(),
    val headerLengthMm: GeneralValueResult = GeneralValueResult(),
    val furnace1DiameterMm: GeneralValueResult = GeneralValueResult(),
    val furnace1ThicknessMm: GeneralValueResult = GeneralValueResult(),
    val furnace1LengthMm: GeneralValueResult = GeneralValueResult(),
    val furnace2DiameterMm: GeneralValueResult = GeneralValueResult(),
    val furnace2ThicknessMm: GeneralValueResult = GeneralValueResult(),
    val furnace2LengthMm: GeneralValueResult = GeneralValueResult()
)

@Immutable
data class GeneralThicknessMeasurementSetup(
    val owner: String = "",
    val inspectionDate: String = "",
    val project: String = "",
    val objectType: String = "",
    val workOrderNo: String = "",
    val equipmentUsed: String = "",
    val methodUsed: String = "",
    val probeType: String = "",
    val materialType: String = "",
    val probeStyle: String = "",
    val operatingTemp: String = "",
    val surfaceCondition: String = "",
    val weldingProcess: String = "",
    val laminatingCheck: String = "",
    val couplantUsed: String = ""
)

@Immutable
data class GeneralMeasurementResultItem(
    val position: String = "",
    val nominalMm: String = "",
    val point1: String = "",
    val point2: String = "",
    val point3: String = "",
    val minimum: String = "",
    val maximum: String = ""
)

@Immutable
data class GeneralNdtTests(
    val shell: GeneralNdtTestComponent = GeneralNdtTestComponent(),
    val furnace: GeneralNdtTestComponent = GeneralNdtTestComponent(),
    val fireTubes: GeneralNdtTestFireTubes = GeneralNdtTestFireTubes()
)

@Immutable
data class GeneralNdtResult(
    val location: String = "",
    val isGood: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class GeneralNdtTestComponent(
    val method: String = "",
    val longitudinalWeldJoint: GeneralNdtResult = GeneralNdtResult(),
    val circumferentialWeldJoint: GeneralNdtResult = GeneralNdtResult()
)

@Immutable
data class GeneralNdtTestFireTubes(
    val method: String = "",
    val weldJointFront: GeneralNdtResult = GeneralNdtResult(),
    val weldJointRear: GeneralNdtResult = GeneralNdtResult()
)

@Immutable
data class GeneralHydrotest(
    val testPressureKgCm2: String = "",
    val mawpKgCm2: String = "",
    val testMedium: String = "",
    val testDate: String = "",
    val testResult: String = ""
)

@Immutable
data class GeneralAppendageResult(
    val quantity: String = "",
    val isGood: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class GeneralAppendagesInspection(
    val pressureGauge: GeneralAppendageResult = GeneralAppendageResult(),
    val manHole: GeneralAppendageResult = GeneralAppendageResult(),
    val safetyValve: GeneralAppendageResult = GeneralAppendageResult(),
    val mainSteamValve: GeneralAppendageResult = GeneralAppendageResult(),
    val levelGlassIndicator: GeneralAppendageResult = GeneralAppendageResult(),
    val blowdownValve: GeneralAppendageResult = GeneralAppendageResult(),
    val feedwaterStopValve: GeneralAppendageResult = GeneralAppendageResult(),
    val feedwaterInletValve: GeneralAppendageResult = GeneralAppendageResult(),
    val steamDrier: GeneralAppendageResult = GeneralAppendageResult(),
    val waterPump: GeneralAppendageResult = GeneralAppendageResult(),
    val controlPanel: GeneralAppendageResult = GeneralAppendageResult(),
    val nameplate: GeneralAppendageResult = GeneralAppendageResult()
)

@Immutable
data class GeneralSafetyValveTest(
    val header: String = "",
    val startsToOpenKgCm2: String = "",
    val valveInfo: String = ""
)

@Immutable
data class GeneralConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val recommendations: ImmutableList<String> = persistentListOf()
)