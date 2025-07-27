package com.nakersolutionid.nakersolutionid.data.remote.dto.ipk

import com.google.gson.annotations.SerializedName

data class IpkReportRequest(
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("extraid") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("documentChecklist") val documentChecklist: IpkDocumentChecklist,
    @SerializedName("generalData") val generalData: IpkGeneralData,
    @SerializedName("buildingData") val buildingData: IpkBuildingData,
    @SerializedName("technicalSpecifications") val technicalSpecifications: IpkTechnicalSpecifications,
    @SerializedName("resultAlarmInstallation") val resultAlarmInstallation: String, // Dari JSON: "123" (string)
    @SerializedName("totalAlarmInstallation") val totalAlarmInstallation: String, // Dari JSON: "123" (string)
    @SerializedName("alarmInstallationData") val alarmInstallationData: List<IpkAlarmInstallationData>,
    @SerializedName("alarmTestResults") val alarmTestResults: IpkAlarmTestResults,
    @SerializedName("hydrantSystem") val hydrantSystem: IpkHydrantSystem,
    @SerializedName("pumpFunctionTest") val pumpFunctionTest: List<IpkPumpFunctionTest>,
    @SerializedName("hydrantOperationalTest") val hydrantOperationalTest: List<IpkHydrantOperationalTest>,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String
)

// Data DTO for single IPK Report response
data class IpkSingleReportResponseData(
    @SerializedName("laporan") val laporan: IpkReportData
)

// Data DTO for list of IPK Reports response
data class IpkListReportResponseData(
    @SerializedName("laporan") val laporan: List<IpkReportData>
)

// Main DTO for IPK Report (used for create, update, and individual get)
data class IpkReportData(
    @SerializedName("id") val id: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("extraid") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("documentChecklist") val documentChecklist: IpkDocumentChecklist,
    @SerializedName("generalData") val generalData: IpkGeneralData,
    @SerializedName("buildingData") val buildingData: IpkBuildingData,
    @SerializedName("technicalSpecifications") val technicalSpecifications: IpkTechnicalSpecifications,
    @SerializedName("resultAlarmInstallation") val resultAlarmInstallation: String, // Dari JSON: "123" (string)
    @SerializedName("totalAlarmInstallation") val totalAlarmInstallation: String, // Dari JSON: "123" (string)
    @SerializedName("alarmInstallationData") val alarmInstallationData: List<IpkAlarmInstallationData>,
    @SerializedName("alarmTestResults") val alarmTestResults: IpkAlarmTestResults,
    @SerializedName("hydrantSystem") val hydrantSystem: IpkHydrantSystem,
    @SerializedName("pumpFunctionTest") val pumpFunctionTest: List<IpkPumpFunctionTest>,
    @SerializedName("hydrantOperationalTest") val hydrantOperationalTest: List<IpkHydrantOperationalTest>,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String,
    @SerializedName("subInspectionType") val subInspectionType: String, // Dari JSON respons
    @SerializedName("documentType") val documentType: String // Dari JSON respons
)

data class IpkDocumentChecklist(
    @SerializedName("technicalDrawing") val technicalDrawing: IpkDocumentCheckItem,
    @SerializedName("previousTestDocumentation") val previousTestDocumentation: IpkDocumentCheckItem,
    @SerializedName("requestLetter") val requestLetter: IpkDocumentCheckItem,
    @SerializedName("specificationDocument") val specificationDocument: IpkDocumentCheckItem
)

data class IpkDocumentCheckItem(
    @SerializedName("available") val available: Boolean,
    @SerializedName("notes") val notes: String
)

data class IpkGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("usageLocation") val usageLocation: String,
    @SerializedName("certificateNumber") val certificateNumber: String,
    @SerializedName("k3Object") val k3Object: String,
    @SerializedName("inspectionDate") val inspectionDate: String
)

data class IpkBuildingData(
    @SerializedName("landArea") val landArea: String, // Dari JSON: "5000" (string)
    @SerializedName("buildingArea") val buildingArea: String, // Dari JSON: "12000" (string)
    @SerializedName("buildingHeight") val buildingHeight: String, // Dari JSON: "40" (string)
    @SerializedName("floorCount") val floorCount: String, // Dari JSON: "10" (string)
    @SerializedName("construction") val construction: IpkConstruction,
    @SerializedName("yearBuilt") val yearBuilt: String, // Dari JSON: "2015" (string)
    @SerializedName("fireProtectionEquipment") val fireProtectionEquipment: IpkFireProtectionEquipment
)

data class IpkConstruction(
    @SerializedName("mainStructure") val mainStructure: String,
    @SerializedName("floorStructure") val floorStructure: String,
    @SerializedName("exteriorWalls") val exteriorWalls: String,
    @SerializedName("interiorWalls") val interiorWalls: String,
    @SerializedName("ceilingFrame") val ceilingFrame: String,
    @SerializedName("ceilingCover") val ceilingCover: String,
    @SerializedName("roofFrame") val roofFrame: String,
    @SerializedName("roofCover") val roofCover: String
)

data class IpkFireProtectionEquipment(
    @SerializedName("portableExtinguishers") val portableExtinguishers: String,
    @SerializedName("indoorHydrantBox") val indoorHydrantBox: String,
    @SerializedName("pillarAndOutdoorHydrant") val pillarAndOutdoorHydrant: String,
    @SerializedName("siameseConnection") val siameseConnection: String,
    @SerializedName("sprinklerSystem") val sprinklerSystem: String,
    @SerializedName("heatAndSmokeDetectors") val heatAndSmokeDetectors: String,
    @SerializedName("exitSigns") val exitSigns: String,
    @SerializedName("emergencyStairs") val emergencyStairs: String,
    @SerializedName("assemblyPoint") val assemblyPoint: String
)

data class IpkTechnicalSpecifications(
    @SerializedName("mcfa") val mcfa: IpkMcfa,
    @SerializedName("heatDetector") val heatDetector: IpkHeatDetector,
    @SerializedName("smokeDetector") val smokeDetector: IpkSmokeDetector,
    @SerializedName("apar") val apar: IpkApar
)

data class IpkMcfa(
    @SerializedName("brandOrType") val brandOrType: String,
    @SerializedName("result") val result: String,
    @SerializedName("ledAnnunciator") val ledAnnunciator: String,
    @SerializedName("type") val type: String,
    @SerializedName("serialNumber") val serialNumber: String
)

data class IpkHeatDetector(
    @SerializedName("brandOrType") val brandOrType: String,
    @SerializedName("result") val result: String,
    @SerializedName("pointCount") val pointCount: String, // Dari JSON: "150" (string)
    @SerializedName("spacing") val spacing: String, // Dari JSON: "8" (string)
    @SerializedName("operatingTemperature") val operatingTemperature: String // Dari JSON: "58" (string)
)

data class IpkSmokeDetector(
    @SerializedName("brandOrType") val brandOrType: String,
    @SerializedName("result") val result: String,
    @SerializedName("pointCount") val pointCount: String, // Dari JSON: "200" (string)
    @SerializedName("spacing") val spacing: String, // Dari JSON: "9" (string)
    @SerializedName("operatingTemperature") val operatingTemperature: String // Dari JSON: "N/A" (string)
)

data class IpkApar(
    @SerializedName("brandOrType") val brandOrType: String,
    @SerializedName("result") val result: String,
    @SerializedName("count") val count: String, // Dari JSON: "50" (string)
    @SerializedName("spacing") val spacing: String, // Dari JSON: "15" (string)
    @SerializedName("placement") val placement: String
)

data class IpkAlarmInstallationData(
    @SerializedName("location") val location: String,
    @SerializedName("zone") val zone: String, // Dari JSON: "1" (string)
    @SerializedName("ror") val ror: String,
    @SerializedName("fixed") val fixed: String,
    @SerializedName("smoke") val smoke: String,
    @SerializedName("tpm") val tpm: String,
    @SerializedName("flsw") val flsw: String,
    @SerializedName("bell") val bell: String,
    @SerializedName("lamp") val lamp: String,
    @SerializedName("status") val status: String
)

data class IpkAlarmTestResults(
    @SerializedName("panelFunction") val panelFunction: String,
    @SerializedName("alarmTest") val alarmTest: String,
    @SerializedName("faultTest") val faultTest: String,
    @SerializedName("interconnectionTest") val interconnectionTest: String
)

data class IpkHydrantSystem(
    @SerializedName("waterSource") val waterSource: IpkWaterSource,
    @SerializedName("groundReservoir") val groundReservoir: IpkGroundReservoir,
    @SerializedName("gravitationTank") val gravitationTank: IpkGravitationTank,
    @SerializedName("siameseConnection") val siameseConnection: IpkSiameseConnection,
    @SerializedName("pumps") val pumps: IpkPumps,
    @SerializedName("indoorHydrant") val indoorHydrant: IpkIndoorHydrant,
    @SerializedName("landingValve") val landingValve: IpkLandingValve,
    @SerializedName("outdoorHydrant") val outdoorHydrant: IpkOutdoorHydrant,
    @SerializedName("fireServiceConnection") val fireServiceConnection: IpkFireServiceConnection,
    @SerializedName("pipingAndValves") val pipingAndValves: IpkPipingAndValves
)

data class IpkWaterSource(
    @SerializedName("specification") val specification: String,
    @SerializedName("status") val status: String,
    @SerializedName("note") val note: String
)

data class IpkGroundReservoir(
    @SerializedName("backupSpec") val backupSpec: String, // Dari JSON: "150" (string)
    @SerializedName("backupStatus") val backupStatus: String,
    @SerializedName("backupResult") val backupResult: String
)

data class IpkGravitationTank(
    @SerializedName("spec") val spec: String,
    @SerializedName("status") val status: String,
    @SerializedName("result") val result: String
)

data class IpkSiameseConnection(
    @SerializedName("spec") val spec: String,
    @SerializedName("status") val status: String,
    @SerializedName("result") val result: String
)

data class IpkPumps(
    @SerializedName("jockey") val jockey: IpkJockeyPump,
    @SerializedName("electric") val electric: IpkElectricPump,
    @SerializedName("diesel") val diesel: IpkDieselPump
)

data class IpkJockeyPump(
    @SerializedName("quantity") val quantity: String, // Dari JSON: "3" (string)
    @SerializedName("headM") val headM: String, // Dari JSON: "100" (string)
    @SerializedName("autoStart") val autoStart: String, // Dari JSON: "8" (string)
    @SerializedName("autoStop") val autoStop: String // Dari JSON: "10" (string)
)

data class IpkElectricPump(
    @SerializedName("quantity") val quantity: String, // Dari JSON: "750" (string)
    @SerializedName("headM") val headM: String, // Dari JSON: "90" (string)
    @SerializedName("autoStart") val autoStart: String, // Dari JSON: "6" (string)
    @SerializedName("stop") val stop: String
)

data class IpkDieselPump(
    @SerializedName("quantity") val quantity: String, // Dari JSON: "750" (string)
    @SerializedName("headM") val headM: String, // Dari JSON: "90" (string)
    @SerializedName("autoStart") val autoStart: String, // Dari JSON: "4" (string)
    @SerializedName("stop") val stop: String
)

data class IpkIndoorHydrant(
    @SerializedName("points") val points: String, // Dari JSON: "20" (string)
    @SerializedName("diameter") val diameter: String, // Dari JSON: "1.5" (string)
    @SerializedName("hoseLength") val hoseLength: String, // Dari JSON: "30" (string)
    @SerializedName("placement") val placement: String
)

data class IpkLandingValve(
    @SerializedName("points") val points: String, // Dari JSON: "10" (string)
    @SerializedName("diameter") val diameter: String, // Dari JSON: "2.5" (string)
    @SerializedName("clutchType") val clutchType: String,
    @SerializedName("placement") val placement: String
)

data class IpkOutdoorHydrant(
    @SerializedName("points") val points: String, // Dari JSON: "4" (string)
    @SerializedName("diameter") val diameter: String, // Dari JSON: "2.5" (string)
    @SerializedName("hoseLength") val hoseLength: String, // Dari JSON: "30" (string)
    @SerializedName("nozzleDiameter") val nozzleDiameter: String, // Dari JSON: "2.5" (string)
    @SerializedName("placement") val placement: String
)

data class IpkFireServiceConnection(
    @SerializedName("points") val points: String, // Dari JSON: "2" (string)
    @SerializedName("inletDiameter") val inletDiameter: String, // Dari JSON: "4" (string)
    @SerializedName("outletDiameter") val outletDiameter: String, // Dari JSON: "2.5" (string)
    @SerializedName("clutchType") val clutchType: String,
    @SerializedName("condition") val condition: String,
    @SerializedName("placement") val placement: String
)

data class IpkPipingAndValves(
    @SerializedName("pressureReliefValve") val pressureReliefValve: IpkPipeValveItem,
    @SerializedName("testValve") val testValve: IpkPipeValveItem,
    @SerializedName("suctionPipe") val suctionPipe: IpkPipeValveItem,
    @SerializedName("mainPipe") val mainPipe: IpkPipeValveItem,
    @SerializedName("standPipe") val standPipe: IpkPipeValveItem,
    @SerializedName("hydrantPillar") val hydrantPillar: IpkPipeValveItem,
    @SerializedName("innerHydrant") val innerHydrant: IpkPipeValveItem,
    @SerializedName("hoseReel") val hoseReel: IpkPipeValveItem
)

data class IpkPipeValveItem(
    @SerializedName("spec") val spec: String,
    @SerializedName("status") val status: String,
    @SerializedName("remarks") val remarks: String
)

data class IpkPumpFunctionTest(
    @SerializedName("pumpType") val pumpType: String,
    @SerializedName("start") val start: String,
    @SerializedName("stop") val stop: String
)

data class IpkHydrantOperationalTest(
    @SerializedName("test") val test: String,
    @SerializedName("pressure") val pressure: String,
    @SerializedName("transmitPower") val transmitPower: String,
    @SerializedName("nozzlePosition") val nozzlePosition: String,
    @SerializedName("status") val status: String,
    @SerializedName("description") val description: String
)