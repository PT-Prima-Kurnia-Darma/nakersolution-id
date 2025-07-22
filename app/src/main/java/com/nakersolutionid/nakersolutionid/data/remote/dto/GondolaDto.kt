package com.nakersolutionid.nakersolutionid.data.remote.dto

import com.google.gson.annotations.SerializedName

// --- Create Gondola Report DTOs ---

data class CreateGondolaReportBody(
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("inspectionDate") val inspectionDate: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("generalData") val generalData: GondolaReportGeneralData = GondolaReportGeneralData(),
    @SerializedName("technicalData") val technicalData: GondolaReportTechnicalData = GondolaReportTechnicalData(),
    @SerializedName("visualInspection") val visualInspection: GondolaReportVisualInspection = GondolaReportVisualInspection(),
    @SerializedName("nonDestructiveTesting") val nonDestructiveTesting: GondolaReportNonDestructiveTesting = GondolaReportNonDestructiveTesting(),
    @SerializedName("testing") val testing: GondolaReportTesting = GondolaReportTesting(),
    @SerializedName("conclusion") val conclusion: String = "",
    @SerializedName("recommendation") val recommendation: String = ""
)

data class CreateGondolaReportResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: GondolaReportData = GondolaReportData()
)

// --- Get Gondola Reports DTOs ---

data class GetGondolaReportsResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: GondolaReportsResponseData = GondolaReportsResponseData()
)

data class GondolaReportsResponseData(
    @SerializedName("laporan") val listLaporan: List<GondolaReportDetail> = emptyList()
)

// --- Delete Gondola Report DTOs ---

data class DeleteGondolaReportResponse(
    @SerializedName("message") val message: String = "",
    @SerializedName("status") val status: String = ""
)

// --- Core Gondola Report Data DTOs ---

data class GondolaReportData(
    @SerializedName("laporan") val laporan: GondolaReportDetail = GondolaReportDetail()
)

// UPDATED GondolaReportDetail to include 'id', 'subInspectionType', and 'documentType'
data class GondolaReportDetail(
    @SerializedName("id") val id: String = "",
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("inspectionDate") val inspectionDate: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("generalData") val generalData: GondolaReportGeneralData = GondolaReportGeneralData(),
    @SerializedName("technicalData") val technicalData: GondolaReportTechnicalData = GondolaReportTechnicalData(),
    @SerializedName("visualInspection") val visualInspection: GondolaReportVisualInspection = GondolaReportVisualInspection(),
    @SerializedName("nonDestructiveTesting") val nonDestructiveTesting: GondolaReportNonDestructiveTesting = GondolaReportNonDestructiveTesting(),
    @SerializedName("testing") val testing: GondolaReportTesting = GondolaReportTesting(),
    @SerializedName("conclusion") val conclusion: String = "",
    @SerializedName("recommendation") val recommendation: String = "",
    @SerializedName("subInspectionType") val subInspectionType: String = "", // Added based on response JSON
    @SerializedName("documentType") val documentType: String = "" // Added based on response JSON
)

// --- General Data DTO (from PAA - Gondola.json) ---

data class GondolaReportGeneralData(
    @SerializedName("ownerName") val ownerName: String = "",
    @SerializedName("ownerAddress") val ownerAddress: String = "",
    @SerializedName("userInCharge") val userInCharge: String = "",
    @SerializedName("subcontractorPersonInCharge") val subcontractorPersonInCharge: String = "",
    @SerializedName("unitLocation") val unitLocation: String = "",
    @SerializedName("operatorName") val operatorName: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("manufacturer") val manufacturer: String = "",
    @SerializedName("brandType") val brandType: String = "",
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String = "",
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String = "",
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String = "",
    @SerializedName("intendedUse") val intendedUse: String = "",
    @SerializedName("usagePermitNumber") val usagePermitNumber: String = "",
    @SerializedName("operatorCertificate") val operatorCertificate: String = ""
)

// --- Technical Data DTO (from PAA - Gondola.json) ---

data class GondolaReportTechnicalData(
    @SerializedName("gondolaSpecification") val gondolaSpecification: GondolaReportGondolaSpecification = GondolaReportGondolaSpecification(),
    @SerializedName("hoist") val hoist: GondolaReportHoist = GondolaReportHoist(),
    @SerializedName("safetyLockType") val safetyLockType: String = "",
    @SerializedName("brake") val brake: GondolaReportBrake = GondolaReportBrake(),
    @SerializedName("suspensionMechanical") val suspensionMechanical: GondolaReportSuspensionMechanical = GondolaReportSuspensionMechanical(),
    @SerializedName("machineWeight") val machineWeight: GondolaReportMachineWeight = GondolaReportMachineWeight()
)

data class GondolaReportGondolaSpecification(
    @SerializedName("supportMastHeight") val supportMastHeight: Int = 0,
    @SerializedName("frontBeamLength") val frontBeamLength: Int = 0,
    @SerializedName("middleBeamLength") val middleBeamLength: Int = 0,
    @SerializedName("rearBeamLength") val rearBeamLength: Int = 0,
    @SerializedName("balanceWeightDistance") val balanceWeightDistance: Int = 0,
    @SerializedName("capacity") val capacity: String = "",
    @SerializedName("speed") val speed: String = "",
    @SerializedName("platformSize") val platformSize: String = "",
    @SerializedName("wireRopeDiameter") val wireRopeDiameter: Double = 0.0
)

data class GondolaReportHoist(
    @SerializedName("model") val model: String = "",
    @SerializedName("liftingCapacity") val liftingCapacity: Int = 0,
    @SerializedName("electricMotor") val electricMotor: GondolaReportElectricMotor = GondolaReportElectricMotor()
)

data class GondolaReportElectricMotor(
    @SerializedName("type") val type: String = "",
    @SerializedName("power") val power: String = "",
    @SerializedName("voltage") val voltage: Int = 0,
    @SerializedName("voltageHz") val voltageHz: Int = 0
)

data class GondolaReportBrake(
    @SerializedName("type") val type: String = "",
    @SerializedName("model") val model: String = "",
    @SerializedName("capacity") val capacity: String = ""
)

data class GondolaReportSuspensionMechanical(
    @SerializedName("supportMastHeight") val supportMastHeight: Int = 0,
    @SerializedName("frontBeamLength") val frontBeamLength: Int = 0,
    @SerializedName("material") val material: String = ""
)

data class GondolaReportMachineWeight(
    @SerializedName("totalPlatformWeight") val totalPlatformWeight: Int = 0,
    @SerializedName("suspensionMechanicalWeight") val suspensionMechanicalWeight: Int = 0,
    @SerializedName("balanceWeight") val balanceWeight: Int = 0,
    @SerializedName("totalMachineWeight") val totalMachineWeight: String = ""
)

// --- Visual Inspection DTOs (from PAA - Gondola.json) ---

data class GondolaReportVisualInspection(
    @SerializedName("suspensionStructure") val suspensionStructure: GondolaReportSuspensionStructureVisualInspection = GondolaReportSuspensionStructureVisualInspection(),
    @SerializedName("steelWireRope") val steelWireRope: GondolaReportSteelWireRopeVisualInspection = GondolaReportSteelWireRopeVisualInspection(),
    @SerializedName("electricalSystem") val electricalSystem: GondolaReportElectricalSystemVisualInspection = GondolaReportElectricalSystemVisualInspection(),
    @SerializedName("platform") val platform: GondolaReportPlatformVisualInspection = GondolaReportPlatformVisualInspection(),
    @SerializedName("safetyDevices") val safetyDevices: GondolaReportSafetyDevicesVisualInspection = GondolaReportSafetyDevicesVisualInspection()
)

data class GondolaReportSuspensionStructureVisualInspection(
    @SerializedName("frontBeam") val frontBeam: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("middleBeam") val middleBeam: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("rearBeam") val rearBeam: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("frontBeamSupportMast") val frontBeamSupportMast: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("lowerFrontBeamSupportMast") val lowerFrontBeamSupportMast: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("mastAndBeamClamp") val mastAndBeamClamp: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("couplingSleeve") val couplingSleeve: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("turnBuckle") val turnBuckle: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("reinforcementRope") val reinforcementRope: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("rearSupportMast") val rearSupportMast: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("balanceWeight") val balanceWeight: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("frontBeamSupportBase") val frontBeamSupportBase: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("rearBeamSupportBase") val rearBeamSupportBase: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("jackBaseJoint") val jackBaseJoint: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("connectionBolts") val connectionBolts: GondolaReportInspectionResult = GondolaReportInspectionResult()
)

data class GondolaReportSteelWireRopeVisualInspection(
    @SerializedName("mainWireRope") val mainWireRope: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("safetyRope") val safetyRope: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("slingFasteners") val slingFasteners: GondolaReportInspectionResult = GondolaReportInspectionResult()
)

data class GondolaReportElectricalSystemVisualInspection(
    @SerializedName("hoistMotor") val hoistMotor: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("brakeRelease") val brakeRelease: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("manualRelease") val manualRelease: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("powerControl") val powerControl: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("powerCable") val powerCable: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("handleSwitch") val handleSwitch: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("upperLimitSwitch") val upperLimitSwitch: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("limitStopper") val limitStopper: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("socketFitting") val socketFitting: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("grounding") val grounding: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("breakerFuse") val breakerFuse: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("emergencyStop") val emergencyStop: GondolaReportInspectionResult = GondolaReportInspectionResult()
)

data class GondolaReportPlatformVisualInspection(
    @SerializedName("hoistMountFrame") val hoistMountFrame: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("frame") val frame: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("bottomPlate") val bottomPlate: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("pinsAndBolts") val pinsAndBolts: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("bracket") val bracket: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("toeBoard") val toeBoard: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("rollerAndGuidePulley") val rollerAndGuidePulley: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("namePlate") val namePlate: GondolaReportInspectionResult = GondolaReportInspectionResult()
)

data class GondolaReportSafetyDevicesVisualInspection(
    @SerializedName("safetyLock") val safetyLock: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("rubberBumper") val rubberBumper: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("safetyLifeLine") val safetyLifeLine: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("loadLimitSwitch") val loadLimitSwitch: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("limitBlock") val limitBlock: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("upperLimitSwitch") val upperLimitSwitch: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("bodyHarness") val bodyHarness: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("harnessAnchorage") val harnessAnchorage: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("communicationTool") val communicationTool: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("handyTalkie") val handyTalkie: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("safetyHelmet") val safetyHelmet: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("handRail") val handRail: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("otherPpe") val otherPpe: GondolaReportInspectionResult = GondolaReportInspectionResult(),
    @SerializedName("coupForGlass") val coupForGlass: GondolaReportInspectionResult = GondolaReportInspectionResult()
)

// --- Non Destructive Testing DTOs (from PAA - Gondola.json) ---

data class GondolaReportNonDestructiveTesting(
    @SerializedName("steelCableRope") val steelCableRope: GondolaReportSteelCableRopeNdt = GondolaReportSteelCableRopeNdt(),
    @SerializedName("suspensionStructure") val suspensionStructure: GondolaReportSuspensionStructureNdt = GondolaReportSuspensionStructureNdt(),
    @SerializedName("gondolaCage") val gondolaCage: GondolaReportGondolaCageNdt = GondolaReportGondolaCageNdt(),
    @SerializedName("clamps") val clamps: GondolaReportClampsNdt = GondolaReportClampsNdt()
)

data class GondolaReportSteelCableRopeNdt(
    @SerializedName("ndtType") val ndtType: String = "",
    @SerializedName("items") val items: List<GondolaReportSteelCableRopeNdtItem> = emptyList()
)

data class GondolaReportSteelCableRopeNdtItem(
    @SerializedName("usage") val usage: String = "",
    @SerializedName("specDiameter") val specDiameter: String = "",
    @SerializedName("actualDiameter") val actualDiameter: String = "",
    @SerializedName("construction") val construction: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("length") val length: String = "",
    @SerializedName("age") val age: String = "",
    @SerializedName("defectFound") val defectFound: Boolean = false,
    @SerializedName("result") val result: String = ""
)

data class GondolaReportSuspensionStructureNdt(
    @SerializedName("ndtType") val ndtType: String = "",
    @SerializedName("items") val items: List<GondolaReportSuspensionStructureNdtItem> = emptyList()
)

data class GondolaReportSuspensionStructureNdtItem(
    @SerializedName("part") val part: String = "",
    @SerializedName("location") val location: String = "",
    @SerializedName("defectFound") val defectFound: Boolean = false,
    @SerializedName("result") val result: String = ""
)

data class GondolaReportGondolaCageNdt(
    @SerializedName("ndtType") val ndtType: String = "",
    @SerializedName("items") val items: List<GondolaReportGondolaCageNdtItem> = emptyList()
)

data class GondolaReportGondolaCageNdtItem(
    @SerializedName("part") val part: String = "",
    @SerializedName("location") val location: String = "",
    @SerializedName("defectFound") val defectFound: Boolean = false,
    @SerializedName("result") val result: String = ""
)

data class GondolaReportClampsNdt(
    @SerializedName("items") val items: List<GondolaReportClampsNdtItem> = emptyList()
)

data class GondolaReportClampsNdtItem(
    @SerializedName("partCheck") val partCheck: String = "",
    @SerializedName("location") val location: String = "",
    @SerializedName("defectFound") val defectFound: Boolean = false,
    @SerializedName("result") val result: String = ""
)

// --- Testing DTOs (from PAA - Gondola.json) ---

data class GondolaReportTesting(
    @SerializedName("dynamicLoadTest") val dynamicLoadTest: GondolaReportDynamicLoadTest = GondolaReportDynamicLoadTest(),
    @SerializedName("staticLoadTest") val staticLoadTest: GondolaReportStaticLoadTest = GondolaReportStaticLoadTest()
)

data class GondolaReportDynamicLoadTest(
    @SerializedName("note") val note: String = "",
    @SerializedName("items") val items: List<GondolaReportLoadTestItem> = emptyList()
)

data class GondolaReportStaticLoadTest(
    @SerializedName("note") val note: String = "",
    @SerializedName("items") val items: List<GondolaReportLoadTestItem> = emptyList()
)

data class GondolaReportLoadTestItem(
    @SerializedName("loadPercentage") val loadPercentage: String = "",
    @SerializedName("loadDetails") val loadDetails: String = "",
    @SerializedName("remarks") val remarks: String = "",
    @SerializedName("result") val result: String = ""
)

// --- Generic Inspection Result DTO (reusable for Visual Inspection items) ---

data class GondolaReportInspectionResult(
    @SerializedName("status") val status: Boolean = false,
    @SerializedName("result") val result: String = ""
)