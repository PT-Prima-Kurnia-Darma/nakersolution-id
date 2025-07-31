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
) {
    companion object {
        fun createDummyFireProtectionUiState(): FireProtectionUiState {
            return FireProtectionUiState(
                isLoading = false,
                inspectionReport = FireProtectionInspectionReport(
                    extraId = "FP-002",
                    moreExtraId = "BATCH-FP-XYZ-2",
                    documentChecklist = FireProtectionDocumentChecklist(
                        technicalDrawing = FireProtectionChecklistResult(isAvailable = true, remarks = "Latest revision available"),
                        previousTestDocumentation = FireProtectionChecklistResult(isAvailable = true, remarks = "All previous reports on file"),
                        requestLetter = FireProtectionChecklistResult(isAvailable = true, remarks = "Formal request for inspection received"),
                        specificationDocument = FireProtectionChecklistResult(isAvailable = true, remarks = "System specifications documented")
                    ),
                    companyData = FireProtectionCompanyData(
                        examinationType = "Routine Maintenance",
                        companyName = "Guardian Fire Solutions",
                        companyLocation = "Service Center Alpha",
                        usageLocation = "Industrial Warehouse Facility",
                        certificateNumber = "FP-CERT-445566",
                        objectType = "Industrial Building",
                        inspectionDate = "2023-10-27"
                    ),
                    buildingData = FireProtectionBuildingData(
                        landAreaSqm = "15000",
                        buildingAreaSqm = "7500",
                        buildingHeightM = "30",
                        floorCount = "3",
                        yearBuilt = "2012",
                        construction = FireProtectionConstruction(
                            mainStructure = "Steel Frame",
                            floorStructure = "Concrete Reinforced",
                            exteriorWalls = "Metal Cladding",
                            interiorWalls = "Concrete Blocks",
                            ceilingFrame = "Steel Beams",
                            ceilingCover = "Insulated Panels",
                            roofFrame = "Steel",
                            roofCover = "Corrugated Metal"
                        ),
                        fireProtectionEquipment = FireProtectionEquipment(
                            portableExtinguishers = true,
                            indoorHydrantBox = true,
                            pillarAndOutdoorHydrant = true,
                            siameseConnection = true,
                            sprinklerSystem = true,
                            heatAndSmokeDetectors = true,
                            exitSigns = true,
                            emergencyStairs = true,
                            assemblyPoint = true
                        )
                    ),
                    automaticFireAlarmSpecifications = FireProtectionAutomaticFireAlarmSpecifications(
                        mcfa = FireProtectionMcfa(
                            brandOrType = "BrandX Alarm Plus",
                            ledAnnunciator = "Full Graphics",
                            type = "Analog Addressable",
                            serialNumber = "MCFA-SN-54321",
                            result = "Excellent"
                        ),
                        heatDetector = FireProtectionDetector(
                            brandOrType = "BrandY Heat Pro",
                            pointCount = "75",
                            spacingM = "12",
                            operatingTemperatureC = "65",
                            result = "Excellent"
                        ),
                        smokeDetector = FireProtectionDetector(
                            brandOrType = "BrandZ Smoke Plus",
                            pointCount = "150",
                            spacingM = "10",
                            operatingTemperatureC = "N/A",
                            result = "Excellent"
                        ),
                        apar = FireProtectionApar(
                            brandOrType = "BrandA APAR Deluxe",
                            count = "15",
                            spacingM = "25",
                            placement = "Strategically placed",
                            result = "Excellent"
                        )
                    ),
                    alarmInstallationTesting = FireProtectionAlarmInstallationTesting(
                        panelFunction = "Panel tested, all functions working",
                        alarmTest = "All detection devices triggered alarms successfully",
                        faultTest = "Fault simulations correctly identified and reported",
                        interconnectionTest = "Interconnection between panels and systems verified"
                    ),
                    alarmInstallationItems = persistentListOf(
                        FireProtectionAlarmInstallationItem(
                            location = "Warehouse Section A", zone = "Zone 5", ror = "N/A", fixed = "N/A",
                            smoke = "5", tpm = "N/A", flsw = "N/A", bell = "2", lamp = "2", status = "Excellent"
                        ),
                        FireProtectionAlarmInstallationItem(
                            location = "Warehouse Section B", zone = "Zone 6", ror = "1", fixed = "N/A",
                            smoke = "5", tpm = "N/A", flsw = "N/A", bell = "2", lamp = "2", status = "Excellent"
                        )
                    ),
                    totalAlarmInstallation = "225 units installed",
                    resultAlarmInstallation = "225 units Excellent",
                    hydrantSystemInstallation = FireProtectionHydrantSystemInstallation(
                        waterSource = FireProtectionSystemComponent(specification = "On-site Fire Water Tank", status = "Connected", remarks = "Capacity 200 m³"),
                        groundReservoir = FireProtectionGroundReservoir(calculation = "Adequate for 2 hours", backupCapacityM3 = "200", status = "Full", remarks = "Regularly tested"),
                        gravitationTank = FireProtectionSystemComponent(specification = "None", status = "N/A", remarks = "N/A"),
                        siameseConnection = FireProtectionSystemComponent(specification = "1 Inlet", status = "Accessible", remarks = "Standard fitting"),
                        jockeyPump = FireProtectionJockeyPump(
                            model = "JP-75", serialNumber = "JP-SN-9876", driver = "Electric", powerRpm = "1800 RPM",
                            placement = "Pump Room", quantityM3H = "7", headM = "35", autoStartKgCm2 = "5.0", autoStopKgCm2 = "6.5"
                        ),
                        electricPump = FireProtectionElectricPump(
                            model = "EP-150", serialNumber = "EP-SN-1112", driver = "Electric Motor", powerRpm = "1450 RPM",
                            placement = "Pump Room", quantityGpm = "750", headM = "70", autoStartKgCm2 = "4.0", stop = "Automatic"
                        ),
                        dieselPump = FireProtectionDieselPump(
                            model = "DP-250", serialNumber = "DP-SN-1314", driver = "Diesel Engine", quantityUsGpm = "1000",
                            headM = "75", autoStartKgCm2 = "4.2", stop = "Automatic"
                        ),
                        buildingHydrant = FireProtectionBuildingHydrant(
                            points = "15", outletDiameterInch = "1.5", hoseLengthM = "30", nozzleDiameterInch = "0.5", placement = "Ground floor and mezzanine"
                        ),
                        landingValve = FireProtectionLandingValve(
                            points = "15", outletDiameterInch = "2.5", couplingType = "Threaded", placement = "All accessible levels"
                        ),
                        yardHydrant = FireProtectionYardHydrant(
                            points = "6", outletDiameterInch = "2.5", hoseLengthM = "30", nozzleDiameterInch = "0.5", placement = "Perimeter of property"
                        ),
                        fireServiceConnection = FireProtectionFireServiceConnection(
                            points = "2", inletDiameterInch = "5", outletDiameterInch = "2.5", couplingType = "Instantaneous",
                            condition = "Excellent", placement = "Front and rear of building"
                        ),
                        pressureReliefValve = FireProtectionSystemComponent(specification = "Set at 8 bar", status = "Functional", remarks = "Tested monthly"),
                        testValve = FireProtectionSystemComponent(specification = "2 x 2.5 inch outlets", status = "Accessible", remarks = "Used for system tests"),
                        suctionPipe = FireProtectionSystemComponent(specification = "12 inch diameter", status = "Connected", remarks = "Direct from reservoir"),
                        mainPipe = FireProtectionSystemComponent(specification = "8 inch diameter", status = "Installed", remarks = "Properly insulated"),
                        standPipe = FireProtectionSystemComponent(specification = "6 inch diameter", status = "Installed", remarks = "Serves all floors"),
                        hydrantPillar = FireProtectionSystemComponent(specification = "2 x 2.5 inch outlets", status = "Functional", remarks = "Located near access roads"),
                        indoorHydrant = FireProtectionSystemComponent(specification = "1.5 inch outlets with hose", status = "Functional", remarks = "Each designated fire cabinet"),
                        hoseReel = FireProtectionSystemComponent(specification = "1 inch, 20m hose", status = "Functional", remarks = "Wall mounted, easily accessible")
                    ),
                    pumpFunctionTest = persistentListOf(
                        FireProtectionPumpFunctionTestItem(pumpType = "Electric", startPressure = "4.0 bar", stopPressure = "8.0 bar"),
                        FireProtectionPumpFunctionTestItem(pumpType = "Diesel", startPressure = "4.2 bar", stopPressure = "8.5 bar")
                    ),
                    hydrantOperationalTest = persistentListOf(
                        FireProtectionHydrantOperationalTestItem(
                            testPoint = "Hydrant 3", pressure = "5.5 bar", jetLength = "27m",
                            nozzlePosition = "Elevated", status = "OK", remarks = "Good coverage"
                        ),
                        FireProtectionHydrantOperationalTestItem(
                            testPoint = "Hydrant 4", pressure = "5.8 bar", jetLength = "28m",
                            nozzlePosition = "Elevated", status = "OK", remarks = "Good coverage"
                        )
                    ),
                    conclusion = FireProtectionConclusion(
                        summary = "The fire protection systems are fully operational and meet all regulatory requirements. All components are in excellent condition.",
                        recommendations = persistentListOf("Schedule the next comprehensive pump inspection in six months.", "Ensure all personnel are aware of the fire assembly points.")
                    )
                )
            )
        }
    }
}

@Immutable
data class FireProtectionInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val documentChecklist: FireProtectionDocumentChecklist = FireProtectionDocumentChecklist(),
    val companyData: FireProtectionCompanyData = FireProtectionCompanyData(),
    val buildingData: FireProtectionBuildingData = FireProtectionBuildingData(),
    val automaticFireAlarmSpecifications: FireProtectionAutomaticFireAlarmSpecifications = FireProtectionAutomaticFireAlarmSpecifications(),
    val alarmInstallationTesting: FireProtectionAlarmInstallationTesting = FireProtectionAlarmInstallationTesting(),
    val alarmInstallationItems: ImmutableList<FireProtectionAlarmInstallationItem> = persistentListOf(),
    val totalAlarmInstallation: String = "",
    val resultAlarmInstallation: String = "",
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
data class FireProtectionAlarmInstallationItem(
    val location: String = "",
    val zone: String = "",
    val ror: String = "",
    val fixed: String = "",
    val smoke: String = "",
    val tpm: String = "",
    val flsw: String = "",
    val bell: String = "",
    val lamp: String = "",
    val status: String = ""
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
    val result: String = ""
    // HAPUS: val remarks: String = "" --> Field ini tidak ada di DTO, menyebabkan data loss.
)

@Immutable
data class FireProtectionDetector(
    val brandOrType: String = "",
    val pointCount: String = "",
    val spacingM: String = "",
    val operatingTemperatureC: String = "",
    val result: String = ""
    // HAPUS: val remarks: String = "" --> Field ini tidak ada di DTO, menyebabkan data loss.
)

@Immutable
data class FireProtectionApar(
    val brandOrType: String = "",
    val count: String = "",
    val spacingM: String = "",
    val placement: String = "",
    val result: String = ""
    // HAPUS: val remarks: String = "" --> Field ini tidak ada di DTO, menyebabkan data loss.
)

@Immutable
data class FireProtectionAlarmInstallationTesting(
    val panelFunction: String = "",
    val alarmTest: String = "",
    val faultTest: String = "",
    val interconnectionTest: String = ""
    // HAPUS: val notes: String = "" --> Field ini tidak ada di DTO, menyebabkan data loss.
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