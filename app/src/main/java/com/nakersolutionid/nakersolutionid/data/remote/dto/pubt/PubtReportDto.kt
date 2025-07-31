package com.nakersolutionid.nakersolutionid.data.remote.dto.pubt

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus // Assuming ResultStatus is in common package

data class PubtReportRequest(
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String, // String karena bisa "" di body
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("generalData") val generalData: PubtGeneralData,
    @SerializedName("technicalData") val technicalData: PubtTechnicalData,
    @SerializedName("inspectionAndMeasurement") val inspectionAndMeasurement: PubtInspectionAndMeasurement,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String
)

// Data DTO for single PUBT Report response
data class PubtSingleReportResponseData(
    @SerializedName("laporan") val laporan: PubtReportData
)

// Data DTO for list of PUBT Reports response
data class PubtListReportResponseData(
    @SerializedName("laporan") val laporan: List<PubtReportData>
)

// Main DTO for PUBT Report (used for create, update, and individual get)
data class PubtReportData(
    @SerializedName("id") val id: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String, // String (timestamp)
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("generalData") val generalData: PubtGeneralData,
    @SerializedName("technicalData") val technicalData: PubtTechnicalData,
    @SerializedName("inspectionAndMeasurement") val inspectionAndMeasurement: PubtInspectionAndMeasurement,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class PubtGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("userUsage") val userUsage: String,
    @SerializedName("userAddress") val userAddress: String,
    @SerializedName("operatorName") val operatorName: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("brandType") val brandType: String,
    @SerializedName("countryAndYearOfManufacture") val countryAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("designPressure") val designPressure: String, // Changed from Int
    @SerializedName("maxAllowableWorkingPressure") val maxAllowableWorkingPressure: String, // Changed from Int
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String, // Changed from Int
    @SerializedName("steamTemperature") val steamTemperature: String,
    @SerializedName("operatingPressure") val operatingPressure: String, // Changed from Int
    @SerializedName("fuelType") val fuelType: String,
    @SerializedName("intendedUse") val intendedUse: String,
    @SerializedName("permitNumber") val permitNumber: String,
    @SerializedName("operatorCertificate") val operatorCertificate: String,
    @SerializedName("equipmentHistory") val equipmentHistory: String,
    @SerializedName("inspectionDate") val inspectionDate: String
)

data class PubtTechnicalData(
    @SerializedName("shell") val shell: PubtShell,
    @SerializedName("furnace") val furnace: PubtFurnace,
    @SerializedName("waterTubes") val waterTubes: PubtWaterTubes,
    @SerializedName("tubePlateSplicing") val tubePlateSplicing: String
)

data class PubtShell(
    @SerializedName("numberOfRounds") val numberOfRounds: String, // Changed from Int
    @SerializedName("connectionMethod") val connectionMethod: String,
    @SerializedName("material") val material: String,
    @SerializedName("pipeDiameter") val pipeDiameter: String, // Changed from Int
    @SerializedName("thickness") val thickness: String, // Changed from Int
    @SerializedName("bodyLength") val bodyLength: String, // Changed from Int
    @SerializedName("heads") val heads: PubtHeads,
    @SerializedName("tubePlate") val tubePlate: PubtTubePlate
)

data class PubtHeads(
    @SerializedName("top") val top: PubtHeadDetail,
    @SerializedName("rear") val rear: PubtHeadDetail
)

data class PubtHeadDetail(
    @SerializedName("diameter") val diameter: String, // Changed from Int
    @SerializedName("thickness") val thickness: String // Changed from Int
)

data class PubtTubePlate(
    @SerializedName("front") val front: PubtTubePlateDetail,
    @SerializedName("rear") val rear: PubtTubePlateDetail
)

data class PubtTubePlateDetail(
    @SerializedName("dim1") val dim1: String,
    @SerializedName("dim2") val dim2: String
)

data class PubtFurnace(
    @SerializedName("type") val type: String,
    @SerializedName("material") val material: String,
    @SerializedName("outerDiameter") val outerDiameter: String, // Changed from Int
    @SerializedName("innerDiameter") val innerDiameter: String, // Changed from Int
    @SerializedName("thickness") val thickness: String // Changed from Int
)

data class PubtWaterTubes(
    @SerializedName("firstPass") val firstPass: PubtWaterTubePass,
    @SerializedName("secondPass") val secondPass: PubtWaterTubePass,
    @SerializedName("stayTube") val stayTube: PubtWaterTubeStayMaterial,
    @SerializedName("material") val material: PubtWaterTubeStayMaterial
)

data class PubtWaterTubePass(
    @SerializedName("diameter") val diameter: String, // Changed from Double
    @SerializedName("thickness") val thickness: String, // Changed from Double
    @SerializedName("length") val length: String, // Changed from Int
    @SerializedName("quantity") val quantity: String // Changed from Int
)

data class PubtWaterTubeStayMaterial(
    @SerializedName("diameter") val diameter: String, // Changed from Int
    @SerializedName("thickness") val thickness: String, // Changed from Int
    @SerializedName("length") val length: String, // Changed from Int
    @SerializedName("quantity") val quantity: String // Changed from Int
)

data class PubtInspectionAndMeasurement(
    @SerializedName("visualChecks") val visualChecks: PubtVisualChecks,
    @SerializedName("materialThickness") val materialThickness: PubtMaterialThickness,
    @SerializedName("thicknessMeasurementSetup") val thicknessMeasurementSetup: PubtThicknessMeasurementSetup,
    @SerializedName("measurementResults") val measurementResults: PubtMeasurementResults,
    @SerializedName("ndt") val ndt: PubtNdt,
    @SerializedName("hydrotest") val hydrotest: PubtHydrotest,
    @SerializedName("appendagesCheck") val appendagesCheck: PubtAppendagesCheck,
    @SerializedName("safetyValveTest") val safetyValveTest: PubtSafetyValveTest
)

data class PubtVisualChecks(
    @SerializedName("steamEquipment") val steamEquipment: PubtSteamEquipment,
    @SerializedName("boilerDetails") val boilerDetails: PubtBoilerDetails,
    @SerializedName("safetyDevices") val safetyDevices: PubtSafetyDevices
)

data class PubtSteamEquipment(
    @SerializedName("shellDrum") val shellDrum: PubtStatusResult,
    @SerializedName("bouileur") val bouileur: PubtStatusResult,
    @SerializedName("furnace") val furnace: PubtStatusResult,
    @SerializedName("fireChamber") val fireChamber: PubtStatusResult,
    @SerializedName("refractory") val refractory: PubtStatusResult,
    @SerializedName("combustionChamber") val combustionChamber: PubtStatusResult,
    @SerializedName("fireTubes") val fireTubes: PubtStatusResult,
    @SerializedName("superHeater") val superHeater: PubtStatusResult,
    @SerializedName("reheater") val reheater: PubtStatusResult,
    @SerializedName("economizer") val economizer: PubtStatusResult
)

data class PubtBoilerDetails(
    @SerializedName("grate") val grate: PubtStatusResult,
    @SerializedName("burner") val burner: PubtStatusResult,
    @SerializedName("fdf") val fdf: PubtStatusResult,
    @SerializedName("idf") val idf: PubtStatusResult,
    @SerializedName("airHeater") val airHeater: PubtStatusResult,
    @SerializedName("airDuct") val airDuct: PubtStatusResult,
    @SerializedName("gasDuct") val gasDuct: PubtStatusResult,
    @SerializedName("ashCatcher") val ashCatcher: PubtStatusResult,
    @SerializedName("chimney") val chimney: PubtStatusResult,
    @SerializedName("stairs") val stairs: PubtStatusResult,
    @SerializedName("insulation") val insulation: PubtStatusResult
)

data class PubtSafetyDevices(
    @SerializedName("safetyValveRing") val safetyValveRing: PubtStatusResult,
    @SerializedName("safetyValvePipe") val safetyValvePipe: PubtStatusResult,
    @SerializedName("safetyValveExhaust") val safetyValveExhaust: PubtStatusResult,
    @SerializedName("pressureGaugeMark") val pressureGaugeMark: PubtStatusResult,
    @SerializedName("pressureGaugeSiphon") val pressureGaugeSiphon: PubtStatusResult,
    @SerializedName("pressureGaugeCock") val pressureGaugeCock: PubtStatusResult,
    @SerializedName("gaugeGlassTryCocks") val gaugeGlassTryCocks: PubtStatusResult,
    @SerializedName("gaugeGlassBlowdown") val gaugeGlassBlowdown: PubtStatusResult,
    @SerializedName("waterLevelLowestMark") val waterLevelLowestMark: PubtStatusResult,
    @SerializedName("waterLevelPosition") val waterLevelPosition: PubtStatusResult,
    @SerializedName("feedwaterPump") val feedwaterPump: PubtStatusResult,
    @SerializedName("feedwaterCapacity") val feedwaterCapacity: PubtStatusResult,
    @SerializedName("feedwaterMotor") val feedwaterMotor: PubtStatusResult,
    @SerializedName("feedwaterCheckValve") val feedwaterCheckValve: PubtStatusResult,
    @SerializedName("controlBlacksFluit") val controlBlacksFluit: PubtStatusResult,
    @SerializedName("controlFusiblePlug") val controlFusiblePlug: PubtStatusResult,
    @SerializedName("controlWaterLevel") val controlWaterLevel: PubtStatusResult,
    @SerializedName("controlSteamPressure") val controlSteamPressure: PubtStatusResult,
    @SerializedName("blowdownDesc") val blowdownDesc: PubtStatusResult,
    @SerializedName("blowdownMaterial") val blowdownMaterial: PubtStatusResult,
    @SerializedName("manholeManhole") val manholeManhole: PubtStatusResult,
    @SerializedName("manholeInspectionHole") val manholeInspectionHole: PubtStatusResult,
    @SerializedName("idMarkNameplate") val idMarkNameplate: PubtStatusResult,
    @SerializedName("idMarkDataMatch") val idMarkDataMatch: PubtStatusResult,
    @SerializedName("idMarkForm9Stamp") val idMarkForm9Stamp: PubtStatusResult
)

data class PubtStatusResult(
    @SerializedName("status") val status: Boolean,
    @SerializedName("result") val result: String
)

data class PubtMaterialThickness(
    @SerializedName("bodyShell") val bodyShell: PubtBodyShellThickness,
    @SerializedName("vaporReceiverHeader") val vaporReceiverHeader: PubtVaporReceiverHeaderThickness,
    @SerializedName("fireHallFurnance1") val fireHallFurnance1: PubtFurnaceThickness,
    @SerializedName("fireHallFurnance2") val fireHallFurnance2: PubtFurnaceThickness
)

data class PubtBodyShellThickness(
    @SerializedName("thickness") val thickness: String,
    @SerializedName("diameter") val diameter: String,
    @SerializedName("thicknessResult") val thicknessResult: String,
    @SerializedName("diameterResult") val diameterResult: String
)

data class PubtVaporReceiverHeaderThickness(
    @SerializedName("thickness") val thickness: String,
    @SerializedName("diameter") val diameter: String,
    @SerializedName("length") val length: String,
    @SerializedName("thicknessResult") val thicknessResult: String,
    @SerializedName("diameterResult") val diameterResult: String,
    @SerializedName("lengthResult") val lengthResult: String
)

data class PubtFurnaceThickness(
    @SerializedName("thickness") val thickness: String,
    @SerializedName("diameter") val diameter: String,
    @SerializedName("length") val length: String,
    @SerializedName("thicknessResult") val thicknessResult: String,
    @SerializedName("diameterResult") val diameterResult: String,
    @SerializedName("lengthResult") val lengthResult: String
)

data class PubtThicknessMeasurementSetup(
    @SerializedName("owner") val owner: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("project") val project: String,
    @SerializedName("objectType") val objectType: String,
    @SerializedName("workOrderNo") val workOrderNo: String,
    @SerializedName("equipmentUsed") val equipmentUsed: String,
    @SerializedName("methodUsed") val methodUsed: String,
    @SerializedName("probeType") val probeType: String,
    @SerializedName("materialType") val materialType: String,
    @SerializedName("probeStyle") val probeStyle: String,
    @SerializedName("operatingTemp") val operatingTemp: String,
    @SerializedName("surfaceCondition") val surfaceCondition: String,
    @SerializedName("weldingProcess") val weldingProcess: String,
    @SerializedName("laminatingCheck") val laminatingCheck: String,
    @SerializedName("couplantUsed") val couplantUsed: String
)

data class PubtMeasurementResults(
    @SerializedName("topHead") val topHead: PubtMeasurementDetail,
    @SerializedName("shell") val shell: PubtMeasurementDetail,
    @SerializedName("buttonHead") val buttonHead: PubtMeasurementDetail
)

data class PubtMeasurementDetail(
    @SerializedName("nominal") val nominal: String, // Changed from Int
    @SerializedName("point1") val point1: String, // Changed from Double
    @SerializedName("point2") val point2: String, // Changed from Double
    @SerializedName("point3") val point3: String, // Changed from Double
    @SerializedName("minimum") val minimum: String, // Changed from Double
    @SerializedName("maximum") val maximum: String // Changed from Double
)

data class PubtNdt(
    @SerializedName("shell") val shell: PubtNdtShell,
    @SerializedName("furnace") val furnace: PubtNdtFurnace,
    @SerializedName("fireTubes") val fireTubes: PubtNdtFireTubes
)

data class PubtNdtShell(
    @SerializedName("testMethod") val testMethod: String,
    @SerializedName("longitudinalWeldJoint") val longitudinalWeldJoint: PubtNdtJoint,
    @SerializedName("circumferentialWeldJoint") val circumferentialWeldJoint: PubtNdtJoint
)

data class PubtNdtFurnace(
    @SerializedName("testMethod") val testMethod: String,
    @SerializedName("longitudinalWeldJoint") val longitudinalWeldJoint: PubtNdtJoint,
    @SerializedName("circumferentialWeldJoint") val circumferentialWeldJoint: PubtNdtJoint
)

data class PubtNdtFireTubes(
    @SerializedName("testMethod") val testMethod: String,
    @SerializedName("weldJointFront") val weldJointFront: PubtNdtJoint,
    @SerializedName("weldJointRear") val weldJointRear: PubtNdtJoint
)

data class PubtNdtJoint(
    @SerializedName("location") val location: String,
    @SerializedName("status") val status: Boolean,
    @SerializedName("result") val result: String
)

data class PubtHydrotest(
    @SerializedName("testPressure") val testPressure: String, // Changed from Int
    @SerializedName("mawp") val mawp: String, // Changed from Int
    @SerializedName("testMedium") val testMedium: String,
    @SerializedName("testDate") val testDate: String,
    @SerializedName("testResult") val testResult: String
)

data class PubtAppendagesCheck(
    @SerializedName("workingPressureGauge") val workingPressureGauge: PubtAppendageItem,
    @SerializedName("manHole") val manHole: PubtAppendageItem,
    @SerializedName("safetyValveFullOpen") val safetyValveFullOpen: PubtAppendageItem,
    @SerializedName("mainSteamValve") val mainSteamValve: PubtAppendageItem,
    @SerializedName("levelGlassIndicator") val levelGlassIndicator: PubtAppendageItem,
    @SerializedName("blowdownValve") val blowdownValve: PubtAppendageItem,
    @SerializedName("feedwaterStopValve") val feedwaterStopValve: PubtAppendageItem,
    @SerializedName("feedwaterInletValve") val feedwaterInletValve: PubtAppendageItem,
    @SerializedName("steamDrier") val steamDrier: PubtAppendageItem,
    @SerializedName("waterPump") val waterPump: PubtAppendageItem,
    @SerializedName("controlPanel") val controlPanel: PubtAppendageItem,
    @SerializedName("nameplate") val nameplate: PubtAppendageItem
)

data class PubtAppendageItem(
    @SerializedName("quantity") val quantity: String, // Changed from Int
    @SerializedName("status") val status: Boolean,
    @SerializedName("result") val result: String
)

data class PubtSafetyValveTest(
    @SerializedName("header") val header: String,
    @SerializedName("startsToOpen") val startsToOpen: String, // Changed from Double
    @SerializedName("valveInfo") val valveInfo: String
)