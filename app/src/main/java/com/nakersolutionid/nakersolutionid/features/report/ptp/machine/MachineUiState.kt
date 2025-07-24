package com.nakersolutionid.nakersolutionid.features.report.ptp.machine

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Production Machine inspection report.
 * This class is immutable.
 */
@Immutable
data class ProductionMachineUiState(
    val isLoading: Boolean = false,
    val inspectionReport: ProductionMachineInspectionReport = ProductionMachineInspectionReport()
)

@Immutable
data class ProductionMachineInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val generalData: ProductionMachineGeneralData = ProductionMachineGeneralData(),
    val technicalData: ProductionMachineTechnicalData = ProductionMachineTechnicalData(),
    val visualInspection: ProductionMachineVisualInspection = ProductionMachineVisualInspection(),
    val testingAndMeasurement: ProductionMachineTestingAndMeasurement = ProductionMachineTestingAndMeasurement(),
    val foundationAnalysis: ProductionMachineFoundationAnalysis = ProductionMachineFoundationAnalysis(),
    val noiseMeasurement: ProductionMachineNoiseMeasurement = ProductionMachineNoiseMeasurement(),
    val lightingMeasurement: ProductionMachineLightingMeasurement = ProductionMachineLightingMeasurement(),
    val conclusion: ProductionMachineConclusion = ProductionMachineConclusion()
)

@Immutable
data class ProductionMachineGeneralData(
    val driveType: String = "",
    val ownerName: String = "",
    val ownerAddress: String = "",
    val userInCharge: String = "",
    val userAddressInCharge: String = "",
    val subcontractorPersonInCharge: String = "",
    val unitLocation: String = "",
    val brandType: String = "",
    val serialNumberUnitNumber: String = "",
    val manufacturer: String = "",
    val locationAndYearOfManufacture: String = "",
    val motorPower: String = "",
    val intendedUse: String = "",
    val pjk3SkpNo: String = "",
    val ak3SkpNo: String = "",
    val usagePermitNumber: String = "",
    val operatorName: String = "",
    val equipmentHistory: String = ""
)

@Immutable
data class ProductionMachineTechnicalData(
    val type: String = "",
    val maxFeederSpeed: String = "",
    val maxPlateWidth: String = "",
    val plateThickness: String = "",
    val maxPlateWeight: String = "",
    val maxInnerCoilDiameter: String = "",
    val maxOuterCoilDiameter: String = "",
    val driveMotor: String = "",
    val motorPowerKw: String = "",
    val brandAndSerial: String = "",
    val locationAndYear: String = "",
    val machineDimensions: ProductionMachineDimensions = ProductionMachineDimensions(),
    val foundationDimensions: ProductionMachineFoundationDimensions = ProductionMachineFoundationDimensions()
)

@Immutable
data class ProductionMachineDimensions(
    val weightKg: String = "",
    val overallDimension: String = ""
)

@Immutable
data class ProductionMachineFoundationDimensions(
    val dim: String = "",
    val distance: String = "",
    val vibrationDamperType: String = "",
    val weightKg1: String = "",
    val weightKg2: String = ""
)

@Immutable
data class ProductionMachineConditionResult(
    val isGood: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class ProductionMachineVisualInspection(
    val foundationCondition: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val foundationBearingCondition: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val mainFrameCondition: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val braceFrameCondition: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val rollerCondition: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val controlPanelCondition: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val displayCondition: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val operationButtonsCondition: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val electricalVoltage: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val electricalPower: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val electricalPhase: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val electricalFrequency: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val electricalCurrent: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val electricalPanel: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val electricalConductor: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val electricalInsulation: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val safetyLimitSwitchUp: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val safetyLimitSwitchDown: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val safetyGrounding: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val safetyGuard: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val safetyStampLock: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val safetyPressureIndicator: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val safetyEmergencyStop: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val safetyHandSensor: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val hydraulicPumpCondition: ProductionMachineConditionResult = ProductionMachineConditionResult(),
    val hydraulicHoseCondition: ProductionMachineConditionResult = ProductionMachineConditionResult()
)

@Immutable
data class ProductionMachineTestingAndMeasurement(
    val functionalTests: ProductionMachineFunctionalTests = ProductionMachineFunctionalTests(),
    val electricalMeasurements: ProductionMachineElectricalMeasurements = ProductionMachineElectricalMeasurements()
)

@Immutable
data class ProductionMachineTestResult(
    val isMet: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class ProductionMachineFunctionalTests(
    val safetyGrounding: ProductionMachineTestResult = ProductionMachineTestResult(),
    val safetyGuard: ProductionMachineTestResult = ProductionMachineTestResult(),
    val safetyRoller: ProductionMachineTestResult = ProductionMachineTestResult(),
    val safetyEmergencyStop: ProductionMachineTestResult = ProductionMachineTestResult(),
    val speedTest: ProductionMachineTestResult = ProductionMachineTestResult(),
    val functionTest: ProductionMachineTestResult = ProductionMachineTestResult(),
    val weldJointTest: ProductionMachineTestResult = ProductionMachineTestResult(),
    val vibrationTest: ProductionMachineTestResult = ProductionMachineTestResult(),
    val lightingTest: ProductionMachineTestResult = ProductionMachineTestResult(),
    val noiseTest: ProductionMachineTestResult = ProductionMachineTestResult()
)

@Immutable
data class ProductionMachineElectricalMeasurements(
    val panelControlDrawing: ProductionMachinePanelControlDrawing = ProductionMachinePanelControlDrawing(),
    val powerInfo: ProductionMachinePowerInfo = ProductionMachinePowerInfo()
)

@Immutable
data class ProductionMachinePanelControlDrawing(
    val ka: String = "",
    val voltageRS: String = "",
    val voltageRT: String = "",
    val voltageST: String = "",
    val voltageRN: String = "",
    val voltageRG: String = "",
    val voltageNG: String = ""
)

@Immutable
data class ProductionMachinePowerInfo(
    val frequency: String = "",
    val cosQ: String = "",
    val ampereR: String = "",
    val ampereS: String = "",
    val ampereT: String = "",
    val remarks: String = ""
)

@Immutable
data class ProductionMachineFoundationAnalysis(
    val machineWeight: ProductionMachineWeight = ProductionMachineWeight(),
    val minFoundationWeight: ProductionMachineMinFoundationWeight = ProductionMachineMinFoundationWeight(),
    val foundationHeight: ProductionMachineFoundationHeight = ProductionMachineFoundationHeight(),
    val summary: String = ""
)

@Immutable
data class ProductionMachineWeight(
    val actualTon: String = "",
    val additionalMaterialTon: String = "",
    val totalTon: String = ""
)

@Immutable
data class ProductionMachineMinFoundationWeight(
    val calculation: String = "",
    val resultTon: String = ""
)

@Immutable
data class ProductionMachineFoundationHeight(
    val formula: String = "Berat Pondasi = Panjang x Lebar x Tinggi x Berat Jenis Pondasi",
    val calculation: String = "",
    val resultMeter: String = ""
)

@Immutable
data class ProductionMachineNoiseAndLightingMeasurement(
    val noise: ProductionMachineNoiseMeasurement = ProductionMachineNoiseMeasurement(),
    val lighting: ProductionMachineLightingMeasurement = ProductionMachineLightingMeasurement(),
    val regulationBasis: String = "Permenaker No. 5 Tahun 2018"
)

/**
 * Mewakili satu titik pengukuran untuk mesin produksi dengan hasil dan analisanya.
 */
@Immutable
data class ProductionMachineMeasurementPoint(
    val result: String = "",
    val analysis: String = ""
)

/**
 * Kelas data baru untuk Pengukuran Kebisingan Mesin Produksi.
 */
@Immutable
data class ProductionMachineNoiseMeasurement(
    val pointA: ProductionMachineMeasurementPoint = ProductionMachineMeasurementPoint(),
    val pointB: ProductionMachineMeasurementPoint = ProductionMachineMeasurementPoint(),
    val pointC: ProductionMachineMeasurementPoint = ProductionMachineMeasurementPoint(),
    val pointD: ProductionMachineMeasurementPoint = ProductionMachineMeasurementPoint()
)

/**
 * Kelas data baru untuk Pengukuran Pencahayaan Mesin Produksi.
 */
@Immutable
data class ProductionMachineLightingMeasurement(
    val pointA: ProductionMachineMeasurementPoint = ProductionMachineMeasurementPoint(),
    val pointB: ProductionMachineMeasurementPoint = ProductionMachineMeasurementPoint(),
    val pointC: ProductionMachineMeasurementPoint = ProductionMachineMeasurementPoint(),
    val pointD: ProductionMachineMeasurementPoint = ProductionMachineMeasurementPoint()
)

@Immutable
data class ProductionMachineConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val requirements: ImmutableList<String> = persistentListOf()
)