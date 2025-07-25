package com.nakersolutionid.nakersolutionid.data.remote.dto.overheadcrane

import com.google.gson.annotations.SerializedName

data class OverheadCraneReportRequest(
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: OverheadCraneGeneralData,
    @SerializedName("technicalData") val technicalData: OverheadCraneTechnicalData,
    @SerializedName("visualInspection") val visualInspection: OverheadCraneVisualInspection,
    @SerializedName("nonDestructiveExamination") val nonDestructiveExamination: OverheadCraneNonDestructiveExamination,
    @SerializedName("testing") val testing: OverheadCraneTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String,
)
// Data DTO for single Overhead Crane Report response
data class OverheadCraneSingleReportResponseData(
    @SerializedName("laporan") val laporan: OverheadCraneReportData
)

// Data DTO for list of Overhead Crane Reports response
data class OverheadCraneListReportResponseData(
    @SerializedName("laporan") val laporan: List<OverheadCraneReportData>
)

// Main DTO for Overhead Crane Report (used for create, update, and individual get)
data class OverheadCraneReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("generalData") val generalData: OverheadCraneGeneralData,
    @SerializedName("technicalData") val technicalData: OverheadCraneTechnicalData,
    @SerializedName("visualInspection") val visualInspection: OverheadCraneVisualInspection,
    @SerializedName("nonDestructiveExamination") val nonDestructiveExamination: OverheadCraneNonDestructiveExamination,
    @SerializedName("testing") val testing: OverheadCraneTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class OverheadCraneGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("ownerAddress") val ownerAddress: String,
    @SerializedName("userInCharge") val userInCharge: String,
    @SerializedName("subcontractorPersonInCharge") val subcontractorPersonInCharge: String,
    @SerializedName("unitLocation") val unitLocation: String,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("brandType") val brandType: String,
    @SerializedName("yearOfManufacture") val yearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("capacityWorkingLoadKg") val capacityWorkingLoadKg: String,
    @SerializedName("intendedUse") val intendedUse: String,
    @SerializedName("usagePermitNumber") val usagePermitNumber: String,
    @SerializedName("operatorCertificate") val operatorCertificate: String,
    @SerializedName("technicalOrManualData") val technicalOrManualData: String
)

data class OverheadCraneTechnicalData(
    @SerializedName("specifications") val specifications: OverheadCraneTechnicalSpecifications,
    @SerializedName("driveMotor") val driveMotor: OverheadCraneTechnicalDriveMotor,
    @SerializedName("startingResistor") val startingResistor: OverheadCraneTechnicalStartingResistor,
    @SerializedName("brake") val brake: OverheadCraneTechnicalBrake,
    @SerializedName("controllerBrake") val controllerBrake: OverheadCraneTechnicalControllerBrake,
    @SerializedName("hook") val hook: OverheadCraneTechnicalHook,
    @SerializedName("chain") val chain: OverheadCraneTechnicalChain
)

data class OverheadCraneTechnicalSpecifications(
    @SerializedName("liftingHeight") val liftingHeight: OverheadCraneMovementDetail,
    @SerializedName("girderLength") val girderLength: OverheadCraneMovementDetail,
    @SerializedName("speed_m_per_min") val speedPerMin: OverheadCraneMovementDetail
)

data class OverheadCraneMovementDetail(
    @SerializedName("hoisting") val hoisting: String,
    @SerializedName("traveling") val traveling: String,
    @SerializedName("traversing") val traversing: String
)

data class OverheadCraneTechnicalDriveMotor(
    @SerializedName("capacity_ton") val capacityTon: OverheadCraneMovementDetail,
    @SerializedName("power_kw") val powerKw: OverheadCraneMovementDetail,
    @SerializedName("type") val type: OverheadCraneMovementDetail,
    @SerializedName("revolution_rpm") val revolutionRpm: OverheadCraneMovementDetail,
    @SerializedName("voltage_v") val voltageV: OverheadCraneMovementDetail,
    @SerializedName("current_a") val currentA: OverheadCraneMovementDetail,
    @SerializedName("frequency_hz") val frequencyHz: OverheadCraneMovementDetail
)

data class OverheadCraneTechnicalStartingResistor(
    @SerializedName("type") val type: OverheadCraneMovementDetail,
    @SerializedName("voltage_v") val voltageV: OverheadCraneMovementDetail,
    @SerializedName("current_a") val currentA: OverheadCraneMovementDetail
)

data class OverheadCraneTechnicalBrake(
    @SerializedName("kind") val kind: OverheadCraneMovementDetail,
    @SerializedName("type") val type: OverheadCraneMovementDetail
)

data class OverheadCraneTechnicalControllerBrake(
    @SerializedName("kind") val kind: OverheadCraneMovementDetail,
    @SerializedName("type") val type: OverheadCraneMovementDetail
)

data class OverheadCraneTechnicalHook(
    @SerializedName("type") val type: OverheadCraneMovementDetail,
    @SerializedName("capacity") val capacity: OverheadCraneMovementDetail,
    @SerializedName("material") val material: OverheadCraneMovementDetail
)

data class OverheadCraneTechnicalChain(
    @SerializedName("type") val type: OverheadCraneMovementDetail,
    @SerializedName("construction") val construction: OverheadCraneMovementDetail,
    @SerializedName("diameter") val diameter: OverheadCraneMovementDetail,
    @SerializedName("length") val length: OverheadCraneMovementDetail
)

data class OverheadCraneVisualInspection(
    @SerializedName("foundation") val foundation: OverheadCraneVisualFoundation,
    @SerializedName("columnFrame") val columnFrame: OverheadCraneVisualColumnFrame,
    @SerializedName("stairs") val stairs: OverheadCraneVisualStairs,
    @SerializedName("platform") val platform: OverheadCraneVisualPlatform,
    @SerializedName("railSupportBeam") val railSupportBeam: OverheadCraneVisualRailSupportBeam,
    @SerializedName("travelingRail") val travelingRail: OverheadCraneVisualTravelingRail,
    @SerializedName("traversingRail") val traversingRail: OverheadCraneVisualTraversingRail,
    @SerializedName("girder") val girder: OverheadCraneVisualGirder,
    @SerializedName("travelingGearbox") val travelingGearbox: OverheadCraneVisualTravelingGearbox,
    @SerializedName("driveWheel") val driveWheel: OverheadCraneVisualDriveWheel,
    @SerializedName("idleWheel") val idleWheel: OverheadCraneVisualIdleWheel,
    @SerializedName("wheelConnector") val wheelConnector: OverheadCraneVisualWheelConnector,
    @SerializedName("girderBumper") val girderBumper: OverheadCraneVisualGirderBumper,
    @SerializedName("trolleyGearbox") val trolleyGearbox: OverheadCraneVisualTrolleyGearbox,
    @SerializedName("trolleyDriveWheel") val trolleyDriveWheel: OverheadCraneVisualTrolleyDriveWheel,
    @SerializedName("trolleyIdleWheel") val trolleyIdleWheel: OverheadCraneVisualTrolleyIdleWheel,
    @SerializedName("trolleyWheelConnector") val trolleyWheelConnector: OverheadCraneVisualTrolleyWheelConnector,
    @SerializedName("trolleyBumper") val trolleyBumper: OverheadCraneVisualTrolleyBumper,
    @SerializedName("drum") val drum: OverheadCraneVisualDrum,
    @SerializedName("brakeVisual") val brakeVisual: OverheadCraneVisualBrakeVisual,
    @SerializedName("hoistGearBox") val hoistGearBox: OverheadCraneVisualHoistGearBox,
    @SerializedName("pulleyChainSprocket") val pulleyChainSprocket: OverheadCraneVisualPulleyChainSprocket,
    @SerializedName("mainHook") val mainHook: OverheadCraneVisualMainHook,
    @SerializedName("auxHook") val auxHook: OverheadCraneVisualAuxHook,
    @SerializedName("mainWireRope") val mainWireRope: OverheadCraneVisualMainWireRope,
    @SerializedName("auxWireRope") val auxWireRope: OverheadCraneVisualAuxWireRope,
    @SerializedName("mainChain") val mainChain: OverheadCraneVisualMainChain,
    @SerializedName("auxChain") val auxChain: OverheadCraneVisualAuxChain,
    @SerializedName("limitSwitch") val limitSwitch: OverheadCraneVisualLimitSwitch,
    @SerializedName("operatorCabin") val operatorCabin: OverheadCraneVisualOperatorCabin,
    @SerializedName("electricalComponents") val electricalComponents: OverheadCraneVisualElectricalComponents
)

data class OverheadCraneVisualDetail(
    @SerializedName("status") val status: Boolean,
    @SerializedName("remarks") val remarks: String
)

data class OverheadCraneVisualFoundation(
    @SerializedName("bolts") val bolts: OverheadCraneVisualFoundationBolts
)

data class OverheadCraneVisualFoundationBolts(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail,
    @SerializedName("fastening") val fastening: OverheadCraneVisualDetail
)

data class OverheadCraneVisualColumnFrame(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail,
    @SerializedName("fastening") val fastening: OverheadCraneVisualDetail,
    @SerializedName("crossBracing") val crossBracing: OverheadCraneVisualDetail,
    @SerializedName("diagonalBracing") val diagonalBracing: OverheadCraneVisualDetail
)

data class OverheadCraneVisualStairs(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail,
    @SerializedName("fastening") val fastening: OverheadCraneVisualDetail
)

data class OverheadCraneVisualPlatform(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail,
    @SerializedName("fastening") val fastening: OverheadCraneVisualDetail
)

data class OverheadCraneVisualRailSupportBeam(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail,
    @SerializedName("fastening") val fastening: OverheadCraneVisualDetail
)

data class OverheadCraneVisualTravelingRail(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("joints") val joints: OverheadCraneVisualDetail,
    @SerializedName("straightness") val straightness: OverheadCraneVisualDetail,
    @SerializedName("interRailStraightness") val interRailStraightness: OverheadCraneVisualDetail,
    @SerializedName("interRailEvenness") val interRailEvenness: OverheadCraneVisualDetail,
    @SerializedName("jointGap") val jointGap: OverheadCraneVisualDetail,
    @SerializedName("fasteners") val fasteners: OverheadCraneVisualDetail,
    @SerializedName("stopper") val stopper: OverheadCraneVisualDetail
)

data class OverheadCraneVisualTraversingRail(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("joints") val joints: OverheadCraneVisualDetail,
    @SerializedName("straightness") val straightness: OverheadCraneVisualDetail,
    @SerializedName("interRailStraightness") val interRailStraightness: OverheadCraneVisualDetail,
    @SerializedName("interRailEvenness") val interRailEvenness: OverheadCraneVisualDetail,
    @SerializedName("jointGap") val jointGap: OverheadCraneVisualDetail,
    @SerializedName("fasteners") val fasteners: OverheadCraneVisualDetail,
    @SerializedName("stopper") val stopper: OverheadCraneVisualDetail
)

data class OverheadCraneVisualGirder(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("camber") val camber: OverheadCraneVisualDetail,
    @SerializedName("joints") val joints: OverheadCraneVisualDetail,
    @SerializedName("endJoints") val endJoints: OverheadCraneVisualDetail,
    @SerializedName("truckMount") val truckMount: OverheadCraneVisualDetail
)

data class OverheadCraneVisualTravelingGearbox(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("lubricant") val lubricant: OverheadCraneVisualDetail,
    @SerializedName("oilSeal") val oilSeal: OverheadCraneVisualDetail
)

data class OverheadCraneVisualDriveWheel(
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail,
    @SerializedName("flange") val flange: OverheadCraneVisualDetail,
    @SerializedName("chain") val chain: OverheadCraneVisualDetail
)

data class OverheadCraneVisualIdleWheel(
    @SerializedName("security") val security: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail,
    @SerializedName("flange") val flange: OverheadCraneVisualDetail
)

data class OverheadCraneVisualWheelConnector(
    @SerializedName("straightness") val straightness: OverheadCraneVisualDetail,
    @SerializedName("crossJoint") val crossJoint: OverheadCraneVisualDetail,
    @SerializedName("lubrication") val lubrication: OverheadCraneVisualDetail
)

data class OverheadCraneVisualGirderBumper(
    @SerializedName("condition") val condition: OverheadCraneVisualDetail,
    @SerializedName("reinforcement") val reinforcement: OverheadCraneVisualDetail
)

data class OverheadCraneVisualTrolleyGearbox(
    @SerializedName("fastening") val fastening: OverheadCraneVisualDetail,
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("lubricant") val lubricant: OverheadCraneVisualDetail,
    @SerializedName("oilSeal") val oilSeal: OverheadCraneVisualDetail
)

data class OverheadCraneVisualTrolleyDriveWheel(
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail,
    @SerializedName("flange") val flange: OverheadCraneVisualDetail,
    @SerializedName("chain") val chain: OverheadCraneVisualDetail
)

data class OverheadCraneVisualTrolleyIdleWheel(
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("cracks") val cracks: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail,
    @SerializedName("flange") val flange: OverheadCraneVisualDetail
)

data class OverheadCraneVisualTrolleyWheelConnector(
    @SerializedName("straightness") val straightness: OverheadCraneVisualDetail,
    @SerializedName("crossJoint") val crossJoint: OverheadCraneVisualDetail,
    @SerializedName("lubrication") val lubrication: OverheadCraneVisualDetail
)

data class OverheadCraneVisualTrolleyBumper(
    @SerializedName("condition") val condition: OverheadCraneVisualDetail,
    @SerializedName("reinforcement") val reinforcement: OverheadCraneVisualDetail
)

data class OverheadCraneVisualDrum(
    @SerializedName("groove") val groove: OverheadCraneVisualDetail,
    @SerializedName("grooveLip") val grooveLip: OverheadCraneVisualDetail,
    @SerializedName("flanges") val flanges: OverheadCraneVisualDetail
)

data class OverheadCraneVisualBrakeVisual(
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("adjustment") val adjustment: OverheadCraneVisualDetail
)

data class OverheadCraneVisualHoistGearBox(
    @SerializedName("lubrication") val lubrication: OverheadCraneVisualDetail,
    @SerializedName("oilSeal") val oilSeal: OverheadCraneVisualDetail
)

data class OverheadCraneVisualPulleyChainSprocket(
    @SerializedName("pulleyGroove") val pulleyGroove: OverheadCraneVisualDetail,
    @SerializedName("pulleyLip") val pulleyLip: OverheadCraneVisualDetail,
    @SerializedName("pulleyPin") val pulleyPin: OverheadCraneVisualDetail,
    @SerializedName("pulleyBearing") val pulleyBearing: OverheadCraneVisualDetail,
    @SerializedName("pulleyGuard") val pulleyGuard: OverheadCraneVisualDetail,
    @SerializedName("ropeChainGuard") val ropeChainGuard: OverheadCraneVisualDetail
)

data class OverheadCraneVisualMainHook(
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("throatOpening") val throatOpening: OverheadCraneVisualDetail,
    @SerializedName("swivel") val swivel: OverheadCraneVisualDetail,
    @SerializedName("trunnion") val trunnion: OverheadCraneVisualDetail
)

data class OverheadCraneVisualAuxHook(
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("throatOpening") val throatOpening: OverheadCraneVisualDetail,
    @SerializedName("swivel") val swivel: OverheadCraneVisualDetail,
    @SerializedName("trunnion") val trunnion: OverheadCraneVisualDetail
)

data class OverheadCraneVisualMainWireRope(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("broken") val broken: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail
)

data class OverheadCraneVisualAuxWireRope(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("broken") val broken: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail
)

data class OverheadCraneVisualMainChain(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("cracksBroken") val cracksBroken: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail
)

data class OverheadCraneVisualAuxChain(
    @SerializedName("corrosion") val corrosion: OverheadCraneVisualDetail,
    @SerializedName("wear") val wear: OverheadCraneVisualDetail,
    @SerializedName("cracksBroken") val cracksBroken: OverheadCraneVisualDetail,
    @SerializedName("deformation") val deformation: OverheadCraneVisualDetail
)

data class OverheadCraneVisualLimitSwitch(
    @SerializedName("longTraveling") val longTraveling: OverheadCraneVisualDetail,
    @SerializedName("crossTraveling") val crossTraveling: OverheadCraneVisualDetail,
    @SerializedName("lifting") val lifting: OverheadCraneVisualDetail
)

data class OverheadCraneVisualOperatorCabin(
    @SerializedName("safetyStairs") val safetyStairs: OverheadCraneVisualDetail,
    @SerializedName("door") val door: OverheadCraneVisualDetail,
    @SerializedName("window") val window: OverheadCraneVisualDetail,
    @SerializedName("fanAc") val fanAc: OverheadCraneVisualDetail,
    @SerializedName("controlLevers") val controlLevers: OverheadCraneVisualDetail,
    @SerializedName("pendantControl") val pendantControl: OverheadCraneVisualDetail,
    @SerializedName("lighting") val lighting: OverheadCraneVisualDetail,
    @SerializedName("horn") val horn: OverheadCraneVisualDetail,
    @SerializedName("fuse") val fuse: OverheadCraneVisualDetail,
    @SerializedName("commTool") val commTool: OverheadCraneVisualDetail,
    @SerializedName("fireExtinguisher") val fireExtinguisher: OverheadCraneVisualDetail,
    @SerializedName("operatingSigns") val operatingSigns: OverheadCraneVisualDetail,
    @SerializedName("masterSwitch") val masterSwitch: OverheadCraneVisualDetail
)

data class OverheadCraneVisualElectricalComponents(
    @SerializedName("panelConnector") val panelConnector: OverheadCraneVisualDetail,
    @SerializedName("conductorGuard") val conductorGuard: OverheadCraneVisualDetail,
    @SerializedName("motorSafetySystem") val motorSafetySystem: OverheadCraneVisualDetail,
    @SerializedName("groundingSystem") val groundingSystem: OverheadCraneVisualDetail,
    @SerializedName("installation") val installation: OverheadCraneVisualDetail
)

data class OverheadCraneNonDestructiveExamination(
    @SerializedName("chain") val chain: OverheadCraneNdeChain,
    @SerializedName("mainHook") val mainHook: OverheadCraneNdeMainHook
)

data class OverheadCraneNdeChain(
    @SerializedName("method") val method: String,
    @SerializedName("items") val items: List<OverheadCraneNdeChainItem>
)

data class OverheadCraneNdeChainItem(
    @SerializedName("chainLocaton") val chainLocation: String,
    @SerializedName("specDimension") val specDimension: String,
    @SerializedName("resultDimension") val resultDimension: String,
    @SerializedName("extendLengthMax") val extendLengthMax: String,
    @SerializedName("wearMax") val wearMax: String,
    @SerializedName("safetyFactor") val safetyFactor: String,
    @SerializedName("defectAda") val defectAda: Boolean,
    @SerializedName("defectTidakAda") val defectTidakAda: Boolean,
    @SerializedName("description") val description: String
)

data class OverheadCraneNdeMainHook(
    @SerializedName("method") val method: String,
    @SerializedName("measurements") val measurements: OverheadCraneNdeHookMeasurements,
    @SerializedName("tolerances") val tolerances: OverheadCraneNdeHookTolerances,
    @SerializedName("result") val result: String
)

data class OverheadCraneNdeHookMeasurements(
    @SerializedName("A") val A: String,
    @SerializedName("B") val B: String,
    @SerializedName("C") val C: String,
    @SerializedName("D") val D: String,
    @SerializedName("E") val E: String,
    @SerializedName("F") val F: String,
    @SerializedName("G") val G: String,
    @SerializedName("H") val H: String
)

data class OverheadCraneNdeHookTolerances(
    @SerializedName("A") val A: String,
    @SerializedName("B") val B: String,
    @SerializedName("C") val C: String,
    @SerializedName("D") val D: String,
    @SerializedName("E") val E: String,
    @SerializedName("F") val F: String,
    @SerializedName("G") val G: String,
    @SerializedName("H") val H: String
)

data class OverheadCraneTesting(
    @SerializedName("dynamicTest") val dynamicTest: OverheadCraneDynamicTest,
    @SerializedName("staticTest") val staticTest: OverheadCraneStaticTest
)

data class OverheadCraneDynamicTest(
    @SerializedName("withoutLoad") val withoutLoad: List<OverheadCraneDynamicTestWithoutLoadItem>,
    @SerializedName("withLoad") val withLoad: List<OverheadCraneDynamicTestWithLoadItem>
)

data class OverheadCraneDynamicTestWithoutLoadItem(
    @SerializedName("test") val test: String,
    @SerializedName("shouldBe") val shouldBe: String,
    @SerializedName("testedOrMeasured") val testedOrMeasured: String,
    @SerializedName("remarks") val remarks: String
)

data class OverheadCraneDynamicTestWithLoadItem(
    @SerializedName("load") val load: String,
    @SerializedName("hoist") val hoist: String,
    @SerializedName("traversing") val traversing: String,
    @SerializedName("traveling") val traveling: String,
    @SerializedName("brakeSystem") val brakeSystem: String,
    @SerializedName("remarks") val remarks: String
)

data class OverheadCraneStaticTest(
    @SerializedName("testLoad") val testLoad: String,
    @SerializedName("deflection") val deflection: OverheadCraneStaticTestDeflection,
    @SerializedName("singleGirder") val singleGirder: OverheadCraneStaticTestGirderDetail,
    @SerializedName("doubleGirder") val doubleGirder: OverheadCraneStaticTestGirderDetail,
    @SerializedName("notes") val notes: String
)

data class OverheadCraneStaticTestDeflection(
    @SerializedName("singleGirder") val singleGirder: OverheadCraneStaticTestDeflectionDetail,
    @SerializedName("doubleGirder") val doubleGirder: OverheadCraneStaticTestDeflectionDetail
)

data class OverheadCraneStaticTestDeflectionDetail(
    @SerializedName("measurement") val measurement: String,
    @SerializedName("description") val description: String
)

data class OverheadCraneStaticTestGirderDetail(
    @SerializedName("design_mm") val designMm: String,
    @SerializedName("span_mm") val spanMm: String,
    @SerializedName("result") val result: Boolean
)
