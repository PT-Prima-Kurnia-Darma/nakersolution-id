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
) {
    companion object {
        fun createDummyGeneralUiState(): GeneralUiState {
            return GeneralUiState(
                isLoading = false,
                inspectionReport = GeneralInspectionReport(
                    extraId = "GEN-11223",
                    moreExtraId = "MGEN-44556",
                    equipmentType = "Steam Boiler",
                    examinationType = "Annual Inspection",
                    generalData = GeneralData(
                        ownerName = "PT. Jaya Abadi",
                        ownerAddress = "Jl. Industri Raya No. 3, Bandung",
                        user = "Andi Wijaya",
                        userAddress = "Jl. Karyawan Tetap No. 4, Bandung",
                        operatorName = "Siti Aminah",
                        manufacturer = "Babcock & Wilcox",
                        brandModelType = "Package Boiler FM-10",
                        driveType = "Combustion",
                        countryAndYearOfManufacture = "USA, 2019",
                        serialNumber = "BW-PB-19001",
                        designPressureKgCm2 = "15",
                        maxAllowableWorkingPressureKgCm2 = "10",
                        capacityKgH = "1000",
                        steamTemperature = "180 °C",
                        operatingPressureKgCm2 = "8",
                        fuelType = "Natural Gas",
                        intendedUse = "Process Steam Generation",
                        permitNumber = "PB-2024-001",
                        operatorCertificate = "OP-SA-005",
                        equipmentHistory = "Last internal inspection 1 year ago. Boiler blowdown performed daily.",
                        inspectionDate = "2025-07-30"
                    ),
                    technicalData = GeneralTechnicalData(
                        shell = GeneralShell(
                            numberOfRounds = "1",
                            connectionMethod = "Welded",
                            material = "SA-516 Gr. 70",
                            pipeDiameterMm = "1500",
                            thicknessMm = "8",
                            bodyLengthMm = "3000"
                        ),
                        heads = GeneralHeads(
                            type = "ASME F&D",
                            topDiameterMm = "1500",
                            topThicknessMm = "10",
                            rearDiameterMm = "1300",
                            rearThicknessMm = "9"
                        ),
                        tubePlate = GeneralTubePlate(
                            frontDim1Mm = "1300",
                            frontDim2Mm = "50",
                            rearDim1Mm = "1300",
                            rearDim2Mm = "50"
                        ),
                        furnace = GeneralFurnace(
                            type = "Horizontal",
                            material = "SA-210 Gr. A-1",
                            outerDiameterMm = "1000",
                            innerDiameterMm = "950",
                            thicknessMm = "6"
                        ),
                        waterTubes = GeneralWaterTubes(
                            firstPass = GeneralTubePass(diameterMm = "50", thicknessMm = "3", lengthMm = "2500", quantity = "100"),
                            secondPass = GeneralTubePass(diameterMm = "50", thicknessMm = "3", lengthMm = "2000", quantity = "80"),
                            stayTube = GeneralTubePass(diameterMm = "75", thicknessMm = "5", lengthMm = "1200", quantity = "10"),
                            material = GeneralTubePass(diameterMm = "SA-210 Gr. A-1", thicknessMm = "", lengthMm = "", quantity = ""),
                            connectionMethod = "Welded"
                        )
                    ),
                    inspectionAndMeasurement = GeneralInspectionAndMeasurement(
                        visualInspection = GeneralVisualInspection(
                            steamEquipmentShellDrum = GeneralCheckResult(isMet = true, remarks = "No visible corrosion or deformation."),
                            steamEquipmentFurnace = GeneralCheckResult(isMet = true, remarks = "Furnace interior clean."),
                            steamEquipmentFireTubes = GeneralCheckResult(isMet = true, remarks = "Fire tubes are clean."),
                            boilerDetailsChimney = GeneralCheckResult(isMet = true, remarks = "Chimney is structurally sound."),
                            safetyValveRing = GeneralCheckResult(isMet = true, remarks = "Safety valve ring is intact."),
                            pressureGaugeMark = GeneralCheckResult(isMet = true, remarks = "Pressure gauge marking is clear."),
                            gaugeGlassTryCocks = GeneralCheckResult(isMet = true, remarks = "Try cocks are functioning."),
                            waterLevelLowestMark = GeneralCheckResult(isMet = true, remarks = "Lowest water level mark is visible."),
                            feedwaterPump = GeneralCheckResult(isMet = true, remarks = "Feedwater pump is clean."),
                            controlWaterLevel = GeneralCheckResult(isMet = true, remarks = "Water level is stable."),
                            controlSteamPressure = GeneralCheckResult(isMet = true, remarks = "Steam pressure is within operating range."),
                            blowdownDesc = GeneralCheckResult(isMet = true, remarks = "Blowdown valve is operational."),
                            manholeManhole = GeneralCheckResult(isMet = true, remarks = "Manhole cover is secure."),
                            idMarkNameplate = GeneralCheckResult(isMet = true, remarks = "Nameplate is legible.")
                        ),
                        materialThickness = GeneralMaterialThickness(
                            shellThicknessMm = GeneralValueResult(value = "8", remarks = "Nominal thickness."),
                            shellDiameterMm = GeneralValueResult(value = "1500", remarks = "Nominal diameter."),
                            headerDiameterMm = GeneralValueResult(value = "1300", remarks = "Nominal diameter."),
                            headerThicknessMm = GeneralValueResult(value = "10", remarks = "Nominal thickness."),
                            headerLengthMm = GeneralValueResult(value = "", remarks = ""),
                            furnace1DiameterMm = GeneralValueResult(value = "1000", remarks = "Nominal diameter."),
                            furnace1ThicknessMm = GeneralValueResult(value = "6", remarks = "Nominal thickness."),
                            furnace1LengthMm = GeneralValueResult(value = "", remarks = ""),
                            furnace2DiameterMm = GeneralValueResult(value = "", remarks = ""),
                            furnace2ThicknessMm = GeneralValueResult(value = "", remarks = ""),
                            furnace2LengthMm = GeneralValueResult(value = "", remarks = "")
                        ),
                        thicknessMeasurementSetup = GeneralThicknessMeasurementSetup(
                            owner = "PT. Jaya Abadi",
                            inspectionDate = "2025-07-30",
                            project = "Annual Boiler Inspection",
                            objectType = "Steam Boiler",
                            workOrderNo = "WO-BOIL-2025-007",
                            equipmentUsed = "Krautkramer USM 35S",
                            methodUsed = "Ultrasonic Pulse Echo",
                            probeType = "5 MHz",
                            materialType = "Steel",
                            probeStyle = "Dual Element",
                            operatingTemp = "Ambient",
                            surfaceCondition = "Cleaned",
                            weldingProcess = "SMAW",
                            laminatingCheck = "Yes",
                            couplantUsed = "Glycerol"
                        ),
                        measurementResultsTopHead = GeneralMeasurementResultItem(
                            position = "Top Head",
                            nominalMm = "10",
                            point1 = "9.8",
                            point2 = "9.9",
                            point3 = "9.7",
                            minimum = "9.7",
                            maximum = "9.9"
                        ),
                        measurementResultsShell = GeneralMeasurementResultItem(
                            position = "Shell - Mid Section",
                            nominalMm = "8",
                            point1 = "7.9",
                            point2 = "8.1",
                            point3 = "7.8",
                            minimum = "7.8",
                            maximum = "8.1"
                        ),
                        measurementResultsButtonHead = GeneralMeasurementResultItem(
                            position = "Rear Head",
                            nominalMm = "9",
                            point1 = "8.8",
                            point2 = "8.9",
                            point3 = "8.7",
                            minimum = "8.7",
                            maximum = "8.9"
                        ),
                        ndtTests = GeneralNdtTests(
                            shell = GeneralNdtTestComponent(
                                method = "UT",
                                longitudinalWeldJoint = GeneralNdtResult(location = "Longitudinal Seam", isGood = true, remarks = "No Discontinuities Detected"),
                                circumferentialWeldJoint = GeneralNdtResult(location = "Circumferential Seam", isGood = true, remarks = "No Discontinuities Detected")
                            ),
                            furnace = GeneralNdtTestComponent(
                                method = "RT",
                                longitudinalWeldJoint = GeneralNdtResult(location = "Furnace Seam", isGood = true, remarks = "Radiography shows acceptable weld quality.")
                            ),
                            fireTubes = GeneralNdtTestFireTubes(
                                method = "VT",
                                weldJointFront = GeneralNdtResult(location = "Fire Tube Weld (Front)", isGood = true, remarks = "Visual inspection clear."),
                                weldJointRear = GeneralNdtResult(location = "Fire Tube Weld (Rear)", isGood = true, remarks = "Visual inspection clear.")
                            )
                        ),
                        hydrotest = GeneralHydrotest(
                            testPressureKgCm2 = "15",
                            mawpKgCm2 = "10",
                            testMedium = "Water",
                            testDate = "2025-07-29",
                            testResult = "Passed - No leaks observed"
                        ),
                        appendagesInspection = GeneralAppendagesInspection(
                            pressureGauge = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Calibrated and functional."),
                            manHole = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Gasket replaced."),
                            safetyValve = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Set pressure confirmed."),
                            mainSteamValve = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Operates smoothly."),
                            levelGlassIndicator = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Clear visibility."),
                            blowdownValve = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Operates freely."),
                            feedwaterStopValve = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Holds pressure."),
                            feedwaterInletValve = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Operates freely."),
                            steamDrier = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Appears intact."),
                            waterPump = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Pump is clean."),
                            controlPanel = GeneralAppendageResult(quantity = "1", isGood = true, remarks = "Control panel is clean.")
                        ),
                        safetyValveTest = GeneralSafetyValveTest(
                            header = "15 kg/cm²",
                            startsToOpenKgCm2 = "15",
                            valveInfo = "Set pressure confirmed by external calibration."
                        )
                    ),
                    conclusion = GeneralConclusion(
                        summary = persistentListOf(
                            "The steam boiler is in good condition and meets operational requirements.",
                            "All safety features and devices are functional.",
                            "Material thickness readings are within acceptable limits."
                        ),
                        recommendations = persistentListOf(
                            "Continue daily blowdown procedure.",
                            "Perform monthly checks on safety valve.",
                            "Schedule next internal inspection in 12 months."
                        )
                    )
                )
            )
        }
    }
}

@Immutable
data class GeneralInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
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
    val material: GeneralTubePass = GeneralTubePass(),
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
    val measurementResultsTopHead: GeneralMeasurementResultItem = GeneralMeasurementResultItem(),
    val measurementResultsShell: GeneralMeasurementResultItem = GeneralMeasurementResultItem(),
    val measurementResultsButtonHead: GeneralMeasurementResultItem = GeneralMeasurementResultItem(),
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