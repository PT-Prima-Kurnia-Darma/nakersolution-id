package com.nakersolutionid.nakersolutionid.data.remote.dto.gondola

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus

data class GondolaReportRequest(
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: GondolaGeneralData,
    @SerializedName("technicalData") val technicalData: GondolaTechnicalData,
    @SerializedName("visualInspection") val visualInspection: GondolaVisualInspection,
    @SerializedName("nonDestructiveTesting") val nonDestructiveTesting: GondolaNonDestructiveTesting,
    @SerializedName("testing") val testing: GondolaTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendation") val recommendation: String,
)
// Data DTO for single Gondola Report response
data class GondolaSingleReportResponseData(
    @SerializedName("laporan") val laporan: GondolaReportData
)

// Data DTO for list of Gondola Reports response
data class GondolaListReportResponseData(
    @SerializedName("laporan") val laporan: List<GondolaReportData>
)

// Main DTO for Gondola Report (used for create, update, and individual get)
data class GondolaReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: GondolaGeneralData,
    @SerializedName("technicalData") val technicalData: GondolaTechnicalData,
    @SerializedName("visualInspection") val visualInspection: GondolaVisualInspection,
    @SerializedName("nonDestructiveTesting") val nonDestructiveTesting: GondolaNonDestructiveTesting,
    @SerializedName("testing") val testing: GondolaTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendation") val recommendation: String,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class GondolaGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("ownerAddress") val ownerAddress: String,
    @SerializedName("userInCharge") val userInCharge: String,
    @SerializedName("subcontractorPersonInCharge") val subcontractorPersonInCharge: String,
    @SerializedName("unitLocation") val unitLocation: String,
    @SerializedName("operatorName") val operatorName: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("brandType") val brandType: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String,
    @SerializedName("intendedUse") val intendedUse: String,
    @SerializedName("usagePermitNumber") val usagePermitNumber: String,
    @SerializedName("operatorCertificate") val operatorCertificate: String
)

data class GondolaTechnicalData(
    @SerializedName("gondolaSpecification") val gondolaSpecification: GondolaSpecification,
    @SerializedName("hoist") val hoist: GondolaHoist,
    @SerializedName("safetyLockType") val safetyLockType: String,
    @SerializedName("brake") val brake: GondolaBrake,
    @SerializedName("suspensionMechanical") val suspensionMechanical: GondolaSuspensionMechanical,
    @SerializedName("machineWeight") val machineWeight: GondolaMachineWeight
)

data class GondolaSpecification(
    @SerializedName("supportMastHeight") val supportMastHeight: Int,
    @SerializedName("frontBeamLength") val frontBeamLength: Int,
    @SerializedName("middleBeamLength") val middleBeamLength: Int,
    @SerializedName("rearBeamLength") val rearBeamLength: Int,
    @SerializedName("balanceWeightDistance") val balanceWeightDistance: Int,
    @SerializedName("capacity") val capacity: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("platformSize") val platformSize: String,
    @SerializedName("wireRopeDiameter") val wireRopeDiameter: Double
)

data class GondolaHoist(
    @SerializedName("model") val model: String,
    @SerializedName("liftingCapacity") val liftingCapacity: Int,
    @SerializedName("electricMotor") val electricMotor: GondolaElectricMotor
)

data class GondolaElectricMotor(
    @SerializedName("type") val type: String,
    @SerializedName("power") val power: String,
    @SerializedName("voltage") val voltage: Int,
    @SerializedName("voltageHz") val voltageHz: Int
)

data class GondolaBrake(
    @SerializedName("type") val type: String,
    @SerializedName("model") val model: String,
    @SerializedName("capacity") val capacity: String
)

data class GondolaSuspensionMechanical(
    @SerializedName("supportMastHeight") val supportMastHeight: Int,
    @SerializedName("frontBeamLength") val frontBeamLength: Int,
    @SerializedName("material") val material: String
)

data class GondolaMachineWeight(
    @SerializedName("totalPlatformWeight") val totalPlatformWeight: Int,
    @SerializedName("suspensionMechanicalWeight") val suspensionMechanicalWeight: Int,
    @SerializedName("balanceWeight") val balanceWeight: Int,
    @SerializedName("totalMachineWeight") val totalMachineWeight: String
)

data class GondolaVisualInspection(
    @SerializedName("suspensionStructure") val suspensionStructure: GondolaSuspensionStructure,
    @SerializedName("steelWireRope") val steelWireRope: GondolaSteelWireRope,
    @SerializedName("electricalSystem") val electricalSystem: GondolaElectricalSystem,
    @SerializedName("platform") val platform: GondolaPlatform,
    @SerializedName("safetyDevices") val safetyDevices: GondolaSafetyDevices
)

data class GondolaSuspensionStructure(
    @SerializedName("frontBeam") val frontBeam: ResultStatus,
    @SerializedName("middleBeam") val middleBeam: ResultStatus,
    @SerializedName("rearBeam") val rearBeam: ResultStatus,
    @SerializedName("frontBeamSupportMast") val frontBeamSupportMast: ResultStatus,
    @SerializedName("lowerFrontBeamSupportMast") val lowerFrontBeamSupportMast: ResultStatus,
    @SerializedName("mastAndBeamClamp") val mastAndBeamClamp: ResultStatus,
    @SerializedName("couplingSleeve") val couplingSleeve: ResultStatus,
    @SerializedName("turnBuckle") val turnBuckle: ResultStatus,
    @SerializedName("reinforcementRope") val reinforcementRope: ResultStatus,
    @SerializedName("rearSupportMast") val rearSupportMast: ResultStatus,
    @SerializedName("balanceWeight") val balanceWeight: ResultStatus,
    @SerializedName("frontBeamSupportBase") val frontBeamSupportBase: ResultStatus,
    @SerializedName("rearBeamSupportBase") val rearBeamSupportBase: ResultStatus,
    @SerializedName("jackBaseJoint") val jackBaseJoint: ResultStatus,
    @SerializedName("connectionBolts") val connectionBolts: ResultStatus
)

data class GondolaSteelWireRope(
    @SerializedName("mainWireRope") val mainWireRope: ResultStatus,
    @SerializedName("safetyRope") val safetyRope: ResultStatus,
    @SerializedName("slingFasteners") val slingFasteners: ResultStatus
)

data class GondolaElectricalSystem(
    @SerializedName("hoistMotor") val hoistMotor: ResultStatus,
    @SerializedName("brakeRelease") val brakeRelease: ResultStatus,
    @SerializedName("manualRelease") val manualRelease: ResultStatus,
    @SerializedName("powerControl") val powerControl: ResultStatus,
    @SerializedName("powerCable") val powerCable: ResultStatus,
    @SerializedName("handleSwitch") val handleSwitch: ResultStatus,
    @SerializedName("upperLimitSwitch") val upperLimitSwitch: ResultStatus,
    @SerializedName("limitStopper") val limitStopper: ResultStatus,
    @SerializedName("socketFitting") val socketFitting: ResultStatus,
    @SerializedName("grounding") val grounding: ResultStatus,
    @SerializedName("breakerFuse") val breakerFuse: ResultStatus,
    @SerializedName("emergencyStop") val emergencyStop: ResultStatus
)

data class GondolaPlatform(
    @SerializedName("hoistMountFrame") val hoistMountFrame: ResultStatus,
    @SerializedName("frame") val frame: ResultStatus,
    @SerializedName("bottomPlate") val bottomPlate: ResultStatus,
    @SerializedName("pinsAndBolts") val pinsAndBolts: ResultStatus,
    @SerializedName("bracket") val bracket: ResultStatus,
    @SerializedName("toeBoard") val toeBoard: ResultStatus,
    @SerializedName("rollerAndGuidePulley") val rollerAndGuidePulley: ResultStatus,
    @SerializedName("namePlate") val namePlate: ResultStatus
)

data class GondolaSafetyDevices(
    @SerializedName("safetyLock") val safetyLock: ResultStatus,
    @SerializedName("rubberBumper") val rubberBumper: ResultStatus,
    @SerializedName("safetyLifeLine") val safetyLifeLine: ResultStatus,
    @SerializedName("loadLimitSwitch") val loadLimitSwitch: ResultStatus,
    @SerializedName("limitBlock") val limitBlock: ResultStatus,
    @SerializedName("upperLimitSwitch") val upperLimitSwitch: ResultStatus,
    @SerializedName("bodyHarness") val bodyHarness: ResultStatus,
    @SerializedName("harnessAnchorage") val harnessAnchorage: ResultStatus,
    @SerializedName("communicationTool") val communicationTool: ResultStatus,
    @SerializedName("handyTalkie") val handyTalkie: ResultStatus,
    @SerializedName("safetyHelmet") val safetyHelmet: ResultStatus,
    @SerializedName("handRail") val handRail: ResultStatus,
    @SerializedName("otherPpe") val otherPpe: ResultStatus,
    @SerializedName("coupForGlass") val coupForGlass: ResultStatus
)

data class GondolaNonDestructiveTesting(
    @SerializedName("steelCableRope") val steelCableRope: GondolaSteelCableRopeNdt,
    @SerializedName("suspensionStructure") val suspensionStructure: GondolaSuspensionStructureNdt,
    @SerializedName("gondolaCage") val gondolaCage: GondolaCageNdt,
    @SerializedName("clamps") val clamps: GondolaClampsNdt
)

data class GondolaSteelCableRopeNdt(
    @SerializedName("ndtType") val ndtType: String,
    @SerializedName("items") val items: List<GondolaSteelCableRopeNdtItem>
)

data class GondolaSteelCableRopeNdtItem(
    @SerializedName("usage") val usage: String,
    @SerializedName("specDiameter") val specDiameter: String,
    @SerializedName("actualDiameter") val actualDiameter: String,
    @SerializedName("construction") val construction: String,
    @SerializedName("type") val type: String,
    @SerializedName("length") val length: String,
    @SerializedName("age") val age: String,
    @SerializedName("defectFound") val defectFound: Boolean,
    @SerializedName("result") val result: String
)

data class GondolaSuspensionStructureNdt(
    @SerializedName("ndtType") val ndtType: String,
    @SerializedName("items") val items: List<GondolaSuspensionStructureNdtItem>
)

data class GondolaSuspensionStructureNdtItem(
    @SerializedName("part") val part: String,
    @SerializedName("location") val location: String,
    @SerializedName("defectFound") val defectFound: Boolean,
    @SerializedName("result") val result: String
)

data class GondolaCageNdt(
    @SerializedName("ndtType") val ndtType: String,
    @SerializedName("items") val items: List<GondolaCageNdtItem>
)

data class GondolaCageNdtItem(
    @SerializedName("part") val part: String,
    @SerializedName("location") val location: String,
    @SerializedName("defectFound") val defectFound: Boolean,
    @SerializedName("result") val result: String
)

data class GondolaClampsNdt(
    @SerializedName("items") val items: List<GondolaClampsNdtItem>
)

data class GondolaClampsNdtItem(
    @SerializedName("partCheck") val partCheck: String,
    @SerializedName("location") val location: String,
    @SerializedName("defectFound") val defectFound: Boolean,
    @SerializedName("result") val result: String
)

data class GondolaTesting(
    @SerializedName("dynamicLoadTest") val dynamicLoadTest: GondolaDynamicLoadTest,
    @SerializedName("staticLoadTest") val staticLoadTest: GondolaStaticLoadTest
)

data class GondolaDynamicLoadTest(
    @SerializedName("note") val note: String,
    @SerializedName("items") val items: List<GondolaLoadTestItem>
)

data class GondolaStaticLoadTest(
    @SerializedName("note") val note: String,
    @SerializedName("items") val items: List<GondolaLoadTestItem>
)

data class GondolaLoadTestItem(
    @SerializedName("loadPercentage") val loadPercentage: String,
    @SerializedName("loadDetails") val loadDetails: String,
    @SerializedName("remarks") val remarks: String,
    @SerializedName("result") val result: String
)
