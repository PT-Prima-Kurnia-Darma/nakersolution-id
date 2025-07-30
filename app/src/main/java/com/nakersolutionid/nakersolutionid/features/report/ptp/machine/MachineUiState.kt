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
) {
    companion object {
        fun createDummyProductionMachineUiState(): ProductionMachineUiState {
            return ProductionMachineUiState(
                isLoading = false,
                inspectionReport = ProductionMachineInspectionReport(
                    extraId = "EXP-12345",
                    moreExtraId = "MEXP-67890",
                    equipmentType = "CNC Lathe",
                    examinationType = "Pre-Use Inspection",
                    generalData = ProductionMachineGeneralData(
                        driveType = "Electric Motor",
                        ownerName = "PT. Maju Mundur",
                        ownerAddress = "Jl. Industri No. 1, Jakarta",
                        userInCharge = "Budi Santoso",
                        userAddressInCharge = "Jl. Karyawan No. 5, Jakarta",
                        subcontractorPersonInCharge = "Agus Salim",
                        unitLocation = "Workshop A, Floor 2",
                        brandType = "XYZ Machining",
                        serialNumberUnitNumber = "CNCL-987654",
                        manufacturer = "XYZ Corp",
                        locationAndYearOfManufacture = "Germany, 2022",
                        motorPower = "15 kW",
                        intendedUse = "Precision Metal Machining",
                        pjk3SkpNo = "PJK3-001",
                        ak3SkpNo = "AK3-002",
                        usagePermitNumber = "UP-003",
                        operatorName = "Citra Lestari",
                        equipmentHistory = "Regular maintenance performed every 6 months. No major breakdowns."
                    ),
                    technicalData = ProductionMachineTechnicalData(
                        type = "Horizontal CNC Lathe",
                        maxFeederSpeed = "12 m/min",
                        maxPlateWidth = "N/A",
                        plateThickness = "N/A",
                        maxPlateWeight = "N/A",
                        maxInnerCoilDiameter = "N/A",
                        maxOuterCoilDiameter = "N/A",
                        driveMotor = "Siemens 15kW",
                        motorPowerKw = "15",
                        brandAndSerial = "XYZ Machining, CNCL-987654",
                        locationAndYear = "Workshop A, 2022",
                        machineDimensions = ProductionMachineDimensions(
                            weightKg = "5000",
                            overallDimension = "3m x 2m x 2m"
                        ),
                        foundationDimensions = ProductionMachineFoundationDimensions(
                            dim = "2.5m x 1.5m x 0.5m",
                            distance = "1m from wall",
                            vibrationDamperType = "Rubber Mounts",
                            weightKg1 = "2000",
                            weightKg2 = "500"
                        )
                    ),
                    visualInspection = ProductionMachineVisualInspection(
                        foundationCondition = ProductionMachineConditionResult(isGood = true, remarks = "No visible cracks or damage."),
                        foundationBearingCondition = ProductionMachineConditionResult(isGood = true, remarks = "Bearing surfaces are clean and lubricated."),
                        mainFrameCondition = ProductionMachineConditionResult(isGood = true, remarks = "Frame is rigid and free from deformation."),
                        braceFrameCondition = ProductionMachineConditionResult(isGood = true, remarks = "Braces are secure."),
                        rollerCondition = ProductionMachineConditionResult(isGood = true, remarks = "Rollers are smooth and show no wear."),
                        controlPanelCondition = ProductionMachineConditionResult(isGood = true, remarks = "Panel is clean, buttons responsive."),
                        displayCondition = ProductionMachineConditionResult(isGood = true, remarks = "Display is clear and readable."),
                        operationButtonsCondition = ProductionMachineConditionResult(isGood = true, remarks = "All buttons function correctly."),
                        electricalVoltage = ProductionMachineConditionResult(isGood = true, remarks = "Within acceptable range."),
                        electricalPower = ProductionMachineConditionResult(isGood = true, remarks = "Stable."),
                        electricalPhase = ProductionMachineConditionResult(isGood = true, remarks = "3-phase detected."),
                        electricalFrequency = ProductionMachineConditionResult(isGood = true, remarks = "50 Hz."),
                        electricalCurrent = ProductionMachineConditionResult(isGood = true, remarks = "Normal operational current."),
                        electricalPanel = ProductionMachineConditionResult(isGood = true, remarks = "Clean and organized."),
                        electricalConductor = ProductionMachineConditionResult(isGood = true, remarks = "Cables are properly insulated and secured."),
                        electricalInsulation = ProductionMachineConditionResult(isGood = true, remarks = "Good insulation resistance."),
                        safetyLimitSwitchUp = ProductionMachineConditionResult(isGood = true, remarks = "Functioning correctly."),
                        safetyLimitSwitchDown = ProductionMachineConditionResult(isGood = true, remarks = "Functioning correctly."),
                        safetyGrounding = ProductionMachineConditionResult(isGood = true, remarks = "Effective grounding."),
                        safetyGuard = ProductionMachineConditionResult(isGood = true, remarks = "All guards in place and secure."),
                        safetyStampLock = ProductionMachineConditionResult(isGood = true, remarks = "Lock mechanism operational."),
                        safetyPressureIndicator = ProductionMachineConditionResult(isGood = true, remarks = "Indicator shows normal pressure."),
                        safetyEmergencyStop = ProductionMachineConditionResult(isGood = true, remarks = "Emergency stop button functions."),
                        safetyHandSensor = ProductionMachineConditionResult(isGood = true, remarks = "Hand sensor is responsive.")
                    ),
                    testingAndMeasurement = ProductionMachineTestingAndMeasurement(
                        functionalTests = ProductionMachineFunctionalTests(
                            safetyGrounding = ProductionMachineTestResult(isMet = true, remarks = "Grounding resistance < 1 Ohm."),
                            safetyGuard = ProductionMachineTestResult(isMet = true, remarks = "All safety guards engage stop function."),
                            safetyRoller = ProductionMachineTestResult(isMet = true, remarks = "Rollers rotate freely."),
                            safetyEmergencyStop = ProductionMachineTestResult(isMet = true, remarks = "Machine stops immediately."),
                            speedTest = ProductionMachineTestResult(isMet = true, remarks = "Max speed achieved and stable."),
                            functionTest = ProductionMachineTestResult(isMet = true, remarks = "All operational modes work as expected."),
                            weldJointTest = ProductionMachineTestResult(isMet = true, remarks = "N/A for this machine."),
                            vibrationTest = ProductionMachineTestResult(isMet = true, remarks = "Vibration levels within acceptable limits."),
                            lightingTest = ProductionMachineTestResult(isMet = true, remarks = "Adequate lighting."),
                            noiseTest = ProductionMachineTestResult(isMet = true, remarks = "Noise levels within permissible limits.")
                        ),
                        electricalMeasurements = ProductionMachineElectricalMeasurements(
                            panelControlDrawing = ProductionMachinePanelControlDrawing(
                                ka = "100A",
                                voltageRS = "380V",
                                voltageRT = "380V",
                                voltageST = "380V",
                                voltageRN = "220V",
                                voltageRG = "220V",
                                voltageNG = "0V"
                            ),
                            powerInfo = ProductionMachinePowerInfo(
                                frequency = "50 Hz",
                                cosQ = "0.85",
                                ampereR = "15A",
                                ampereS = "16A",
                                ampereT = "15.5A",
                                remarks = "Power consumption stable during operation."
                            )
                        )
                    ),
                    foundationAnalysis = ProductionMachineFoundationAnalysis(
                        machineWeight = ProductionMachineWeight(
                            actualTon = "5.0",
                            additionalMaterialTon = "1.0",
                            totalTon = "6.0"
                        ),
                        minFoundationWeight = ProductionMachineMinFoundationWeight(
                            calculation = "Machine Weight * 2",
                            resultTon = "12.0"
                        ),
                        foundationHeight = ProductionMachineFoundationHeight(
                            formula = "Berat Pondasi = Panjang x Lebar x Tinggi x Berat Jenis Pondasi",
                            calculation = "12000 kg / (2.5m * 1.5m * 2400 kg/m^3)",
                            resultMeter = "0.42"
                        ),
                        summary = "The foundation meets the minimum weight requirements and dimensions are adequate for machine stability."
                    ),
                    noiseMeasurement = ProductionMachineNoiseMeasurement(
                        pointA = ProductionMachineMeasurementPoint(result = "75 dB", analysis = "Below permissible limit."),
                        pointB = ProductionMachineMeasurementPoint(result = "78 dB", analysis = "Below permissible limit."),
                        pointC = ProductionMachineMeasurementPoint(result = "76 dB", analysis = "Below permissible limit."),
                        pointD = ProductionMachineMeasurementPoint(result = "77 dB", analysis = "Below permissible limit.")
                    ),
                    lightingMeasurement = ProductionMachineLightingMeasurement(
                        pointA = ProductionMachineMeasurementPoint(result = "500 lux", analysis = "Adequate lighting level."),
                        pointB = ProductionMachineMeasurementPoint(result = "520 lux", analysis = "Adequate lighting level."),
                        pointC = ProductionMachineMeasurementPoint(result = "510 lux", analysis = "Adequate lighting level."),
                        pointD = ProductionMachineMeasurementPoint(result = "530 lux", analysis = "Adequate lighting level.")
                    ),
                    conclusion = ProductionMachineConclusion(
                        summary = persistentListOf(
                            "The production machine is in good working condition.",
                            "All safety features are functional.",
                            "Electrical and mechanical parameters are within specifications."
                        ),
                        requirements = persistentListOf(
                            "Continue regular lubrication schedule.",
                            "Conduct detailed internal inspection every 12 months."
                        )
                    )
                )
            )
        }
    }
}

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