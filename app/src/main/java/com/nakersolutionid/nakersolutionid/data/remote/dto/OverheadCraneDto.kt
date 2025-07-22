package com.nakersolutionid.nakersolutionid.data.remote.dto

import com.google.gson.annotations.SerializedName

// --- Create OverheadCrane Report DTOs ---

data class CreateOverheadCraneReportBody(
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("inspectionDate") val inspectionDate: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("generalData") val generalData: OverheadCraneReportGeneralData = OverheadCraneReportGeneralData(),
    @SerializedName("technicalData") val technicalData: OverheadCraneReportTechnicalData = OverheadCraneReportTechnicalData(),
    @SerializedName("visualInspection") val visualInspection: OverheadCraneReportVisualInspection = OverheadCraneReportVisualInspection(),
    @SerializedName("nonDestructiveExamination") val nonDestructiveExamination: OverheadCraneReportNonDestructiveExamination = OverheadCraneReportNonDestructiveExamination(),
    @SerializedName("testing") val testing: OverheadCraneReportTesting = OverheadCraneReportTesting(),
    @SerializedName("conclusion") val conclusion: String = "",
    @SerializedName("recommendations") val recommendations: String = ""
)

data class CreateOverheadCraneReportResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: OverheadCraneReportData = OverheadCraneReportData()
)

// --- Get OverheadCrane Reports DTOs ---

data class GetOverheadCraneReportsResponse(
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: OverheadCraneReportsResponseData = OverheadCraneReportsResponseData()
)

data class OverheadCraneReportsResponseData(
    @SerializedName("laporan") val listLaporan: List<OverheadCraneReportDetail> = emptyList()
)

// --- Delete OverheadCrane Report DTOs ---

data class DeleteOverheadCraneReportResponse(
    @SerializedName("message") val message: String = "",
    @SerializedName("status") val status: String = ""
)

// --- Core OverheadCrane Report Data DTOs ---

data class OverheadCraneReportData(
    @SerializedName("laporan") val laporan: OverheadCraneReportDetail = OverheadCraneReportDetail()
)

data class OverheadCraneReportDetail(
    @SerializedName("id") val id: String = "", // Assuming an ID would be assigned by the backend for a fetched report
    @SerializedName("examinationType") val examinationType: String = "",
    @SerializedName("inspectionType") val inspectionType: String = "",
    @SerializedName("inspectionDate") val inspectionDate: String = "",
    @SerializedName("extraId") val extraId: Int = 0,
    @SerializedName("createdAt") val createdAt: String = "",
    @SerializedName("generalData") val generalData: OverheadCraneReportGeneralData = OverheadCraneReportGeneralData(),
    @SerializedName("technicalData") val technicalData: OverheadCraneReportTechnicalData = OverheadCraneReportTechnicalData(),
    @SerializedName("visualInspection") val visualInspection: OverheadCraneReportVisualInspection = OverheadCraneReportVisualInspection(),
    @SerializedName("nonDestructiveExamination") val nonDestructiveExamination: OverheadCraneReportNonDestructiveExamination = OverheadCraneReportNonDestructiveExamination(),
    @SerializedName("testing") val testing: OverheadCraneReportTesting = OverheadCraneReportTesting(),
    @SerializedName("conclusion") val conclusion: String = "",
    @SerializedName("recommendations") val recommendations: String = ""
)

// --- General Data DTO (from PAA - Overhead Crane.json) ---

data class OverheadCraneReportGeneralData(
    @SerializedName("ownerName") val ownerName: String = "",
    @SerializedName("ownerAddress") val ownerAddress: String = "",
    @SerializedName("userInCharge") val userInCharge: String = "",
    @SerializedName("subcontractorPersonInCharge") val subcontractorPersonInCharge: String = "",
    @SerializedName("unitLocation") val unitLocation: String = "",
    @SerializedName("equipmentType") val equipmentType: String = "",
    @SerializedName("manufacturer") val manufacturer: String = "",
    @SerializedName("brandType") val brandType: String = "",
    @SerializedName("yearOfManufacture") val yearOfManufacture: String = "",
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String = "",
    @SerializedName("capacityWorkingLoadKg") val capacityWorkingLoadKg: String = "",
    @SerializedName("legalBasis") val legalBasis: String = "",
    @SerializedName("intendedUse") val intendedUse: String = "",
    @SerializedName("usagePermitNumber") val usagePermitNumber: String = "",
    @SerializedName("operatorCertificate") val operatorCertificate: String = "",
    @SerializedName("technicalOrManualData") val technicalOrManualData: String = ""
)

// --- Technical Data DTO (from PAA - Overhead Crane.json) ---

data class OverheadCraneReportTechnicalData(
    @SerializedName("specifications") val specifications: OverheadCraneReportSpecifications = OverheadCraneReportSpecifications(),
    @SerializedName("driveMotor") val driveMotor: OverheadCraneReportDriveMotor = OverheadCraneReportDriveMotor(),
    @SerializedName("startingResistor") val startingResistor: OverheadCraneReportStartingResistor = OverheadCraneReportStartingResistor(),
    @SerializedName("brake") val brake: OverheadCraneReportBrake = OverheadCraneReportBrake(),
    @SerializedName("controllerBrake") val controllerBrake: OverheadCraneReportControllerBrake = OverheadCraneReportControllerBrake(),
    @SerializedName("hook") val hook: OverheadCraneReportHook = OverheadCraneReportHook(),
    @SerializedName("chain") val chain: OverheadCraneReportChain = OverheadCraneReportChain()
)

data class OverheadCraneReportSpecifications(
    @SerializedName("liftingHeight") val liftingHeight: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("girderLength") val girderLength: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("speed_m_per_min") val speedMPerMin: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue()
)

data class OverheadCraneReportDriveMotor(
    @SerializedName("capacity_ton") val capacityTon: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("power_kw") val powerKw: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("type") val type: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("revolution_rpm") val revolutionRpm: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("voltage_v") val voltageV: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("current_a") val currentA: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("frequency_hz") val frequencyHz: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue()
)

data class OverheadCraneReportStartingResistor(
    @SerializedName("type") val type: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("voltage_v") val voltageV: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("current_a") val currentA: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue()
)

data class OverheadCraneReportBrake(
    @SerializedName("kind") val kind: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("type") val type: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue()
)

data class OverheadCraneReportControllerBrake(
    @SerializedName("kind") val kind: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("type") val type: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue()
)

data class OverheadCraneReportHook(
    @SerializedName("type") val type: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("capacity") val capacity: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("material") val material: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue()
)

data class OverheadCraneReportChain(
    @SerializedName("type") val type: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("construction") val construction: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("diameter") val diameter: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue(),
    @SerializedName("length") val length: OverheadCraneReportHoistingTravelingTraversingValue = OverheadCraneReportHoistingTravelingTraversingValue()
)

data class OverheadCraneReportHoistingTravelingTraversingValue(
    @SerializedName("hoisting") val hoisting: String = "",
    @SerializedName("traveling") val traveling: String = "",
    @SerializedName("traversing") val traversing: String = ""
)

// --- Visual Inspection DTOs (from PAA - Overhead Crane.json) ---

data class OverheadCraneReportVisualInspection(
    @SerializedName("foundation") val foundation: OverheadCraneReportFoundationVisualInspection = OverheadCraneReportFoundationVisualInspection(),
    @SerializedName("columnFrame") val columnFrame: OverheadCraneReportColumnFrameVisualInspection = OverheadCraneReportColumnFrameVisualInspection(),
    @SerializedName("stairs") val stairs: OverheadCraneReportStairsVisualInspection = OverheadCraneReportStairsVisualInspection(),
    @SerializedName("platform") val platform: OverheadCraneReportPlatformVisualInspection = OverheadCraneReportPlatformVisualInspection(),
    @SerializedName("railSupportBeam") val railSupportBeam: OverheadCraneReportRailSupportBeamVisualInspection = OverheadCraneReportRailSupportBeamVisualInspection(),
    @SerializedName("travelingRail") val travelingRail: OverheadCraneReportTravelingRailVisualInspection = OverheadCraneReportTravelingRailVisualInspection(),
    @SerializedName("traversingRail") val traversingRail: OverheadCraneReportTraversingRailVisualInspection = OverheadCraneReportTraversingRailVisualInspection(),
    @SerializedName("girder") val girder: OverheadCraneReportGirderVisualInspection = OverheadCraneReportGirderVisualInspection(),
    @SerializedName("travelingGearbox") val travelingGearbox: OverheadCraneReportTravelingGearboxVisualInspection = OverheadCraneReportTravelingGearboxVisualInspection(),
    @SerializedName("driveWheel") val driveWheel: OverheadCraneReportDriveWheelVisualInspection = OverheadCraneReportDriveWheelVisualInspection(),
    @SerializedName("idleWheel") val idleWheel: OverheadCraneReportIdleWheelVisualInspection = OverheadCraneReportIdleWheelVisualInspection(),
    @SerializedName("wheelConnector") val wheelConnector: OverheadCraneReportWheelConnectorVisualInspection = OverheadCraneReportWheelConnectorVisualInspection(),
    @SerializedName("girderBumper") val girderBumper: OverheadCraneReportGirderBumperVisualInspection = OverheadCraneReportGirderBumperVisualInspection(),
    @SerializedName("trolleyGearbox") val trolleyGearbox: OverheadCraneReportTrolleyGearboxVisualInspection = OverheadCraneReportTrolleyGearboxVisualInspection(),
    @SerializedName("trolleyDriveWheel") val trolleyDriveWheel: OverheadCraneReportTrolleyDriveWheelVisualInspection = OverheadCraneReportTrolleyDriveWheelVisualInspection(),
    @SerializedName("trolleyIdleWheel") val trolleyIdleWheel: OverheadCraneReportTrolleyIdleWheelVisualInspection = OverheadCraneReportTrolleyIdleWheelVisualInspection(),
    @SerializedName("trolleyWheelConnector") val trolleyWheelConnector: OverheadCraneReportTrolleyWheelConnectorVisualInspection = OverheadCraneReportTrolleyWheelConnectorVisualInspection(),
    @SerializedName("trolleyBumper") val trolleyBumper: OverheadCraneReportTrolleyBumperVisualInspection = OverheadCraneReportTrolleyBumperVisualInspection(),
    @SerializedName("drum") val drum: OverheadCraneReportDrumVisualInspection = OverheadCraneReportDrumVisualInspection(),
    @SerializedName("brakeVisual") val brakeVisual: OverheadCraneReportBrakeVisualInspection = OverheadCraneReportBrakeVisualInspection(),
    @SerializedName("hoistGearBox") val hoistGearBox: OverheadCraneReportHoistGearBoxVisualInspection = OverheadCraneReportHoistGearBoxVisualInspection(),
    @SerializedName("pulleyChainSprocket") val pulleyChainSprocket: OverheadCraneReportPulleyChainSprocketVisualInspection = OverheadCraneReportPulleyChainSprocketVisualInspection(),
    @SerializedName("mainHook") val mainHook: OverheadCraneReportMainHookVisualInspection = OverheadCraneReportMainHookVisualInspection(),
    @SerializedName("auxHook") val auxHook: OverheadCraneReportAuxHookVisualInspection = OverheadCraneReportAuxHookVisualInspection(),
    @SerializedName("mainWireRope") val mainWireRope: OverheadCraneReportMainWireRopeVisualInspection = OverheadCraneReportMainWireRopeVisualInspection(),
    @SerializedName("auxWireRope") val auxWireRope: OverheadCraneReportAuxWireRopeVisualInspection = OverheadCraneReportAuxWireRopeVisualInspection(),
    @SerializedName("mainChain") val mainChain: OverheadCraneReportMainChainVisualInspection = OverheadCraneReportMainChainVisualInspection(),
    @SerializedName("auxChain") val auxChain: OverheadCraneReportAuxChainVisualInspection = OverheadCraneReportAuxChainVisualInspection(),
    @SerializedName("limitSwitch") val limitSwitch: OverheadCraneReportLimitSwitchVisualInspection = OverheadCraneReportLimitSwitchVisualInspection(),
    @SerializedName("operatorCabin") val operatorCabin: OverheadCraneReportOperatorCabinVisualInspection = OverheadCraneReportOperatorCabinVisualInspection(),
    @SerializedName("electricalComponents") val electricalComponents: OverheadCraneReportElectricalComponentsVisualInspection = OverheadCraneReportElectricalComponentsVisualInspection()
)

data class OverheadCraneReportFoundationVisualInspection(
    @SerializedName("bolts") val bolts: OverheadCraneReportBoltVisualInspection = OverheadCraneReportBoltVisualInspection()
)

data class OverheadCraneReportBoltVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fastening") val fastening: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportColumnFrameVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fastening") val fastening: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("crossBracing") val crossBracing: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("diagonalBracing") val diagonalBracing: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportStairsVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fastening") val fastening: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportPlatformVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fastening") val fastening: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportRailSupportBeamVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fastening") val fastening: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportTravelingRailVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("joints") val joints: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("straightness") val straightness: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("interRailStraightness") val interRailStraightness: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("interRailEvenness") val interRailEvenness: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("jointGap") val jointGap: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fasteners") val fasteners: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("stopper") val stopper: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportTraversingRailVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("joints") val joints: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("straightness") val straightness: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("interRailStraightness") val interRailStraightness: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("interRailEvenness") val interRailEvenness: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("jointGap") val jointGap: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fasteners") val fasteners: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("stopper") val stopper: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportGirderVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("camber") val camber: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("joints") val joints: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("endJoints") val endJoints: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("truckMount") val truckMount: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportTravelingGearboxVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("lubricant") val lubricant: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("oilSeal") val oilSeal: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportDriveWheelVisualInspection(
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("flange") val flange: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("chain") val chain: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportIdleWheelVisualInspection(
    @SerializedName("security") val security: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("flange") val flange: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportWheelConnectorVisualInspection(
    @SerializedName("straightness") val straightness: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("crossJoint") val crossJoint: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("lubrication") val lubrication: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportGirderBumperVisualInspection(
    @SerializedName("condition") val condition: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("reinforcement") val reinforcement: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportTrolleyGearboxVisualInspection(
    @SerializedName("fastening") val fastening: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("lubricant") val lubricant: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("oilSeal") val oilSeal: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportTrolleyDriveWheelVisualInspection(
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("flange") val flange: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("chain") val chain: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportTrolleyIdleWheelVisualInspection(
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracks") val cracks: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("flange") val flange: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportTrolleyWheelConnectorVisualInspection(
    @SerializedName("straightness") val straightness: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("crossJoint") val crossJoint: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("lubrication") val lubrication: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportTrolleyBumperVisualInspection(
    @SerializedName("condition") val condition: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("reinforcement") val reinforcement: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportDrumVisualInspection(
    @SerializedName("groove") val groove: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("grooveLip") val grooveLip: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("flanges") val flanges: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportBrakeVisualInspection(
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("adjustment") val adjustment: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportHoistGearBoxVisualInspection(
    @SerializedName("lubrication") val lubrication: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("oilSeal") val oilSeal: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportPulleyChainSprocketVisualInspection(
    @SerializedName("pulleyGroove") val pulleyGroove: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("pulleyLip") val pulleyLip: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("pulleyPin") val pulleyPin: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("pulleyBearing") val pulleyBearing: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("pulleyGuard") val pulleyGuard: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("ropeChainGuard") val ropeChainGuard: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportMainHookVisualInspection(
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("throatOpening") val throatOpening: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("swivel") val swivel: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("trunnion") val trunnion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportAuxHookVisualInspection(
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("throatOpening") val throatOpening: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("swivel") val swivel: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("trunnion") val trunnion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportMainWireRopeVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("broken") val broken: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportAuxWireRopeVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("broken") val broken: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportMainChainVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracksBroken") val cracksBroken: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportAuxChainVisualInspection(
    @SerializedName("corrosion") val corrosion: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("wear") val wear: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("cracksBroken") val cracksBroken: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("deformation") val deformation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportLimitSwitchVisualInspection(
    @SerializedName("longTraveling") val longTraveling: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("crossTraveling") val crossTraveling: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("lifting") val lifting: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportOperatorCabinVisualInspection(
    @SerializedName("safetyStairs") val safetyStairs: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("door") val door: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("window") val window: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fanAc") val fanAc: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("controlLevers") val controlLevers: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("pendantControl") val pendantControl: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("lighting") val lighting: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("horn") val horn: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fuse") val fuse: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("commTool") val commTool: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("fireExtinguisher") val fireExtinguisher: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("operatingSigns") val operatingSigns: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("masterSwitch") val masterSwitch: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

data class OverheadCraneReportElectricalComponentsVisualInspection(
    @SerializedName("panelConnector") val panelConnector: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("conductorGuard") val conductorGuard: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("motorSafetySystem") val motorSafetySystem: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("groundingSystem") val groundingSystem: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult(),
    @SerializedName("installation") val installation: OverheadCraneReportInspectionResult = OverheadCraneReportInspectionResult()
)

// --- Non-Destructive Examination DTOs (from PAA - Overhead Crane.json) ---

data class OverheadCraneReportNonDestructiveExamination(
    @SerializedName("chain") val chain: OverheadCraneReportChainNde = OverheadCraneReportChainNde(),
    @SerializedName("mainHook") val mainHook: OverheadCraneReportMainHookNde = OverheadCraneReportMainHookNde()
)

data class OverheadCraneReportChainNde(
    @SerializedName("method") val method: String = "",
    @SerializedName("items") val items: List<OverheadCraneReportChainNdeItem> = emptyList()
)

data class OverheadCraneReportChainNdeItem(
    @SerializedName("chainLocaton") val chainLocaton: String = "",
    @SerializedName("specDimension") val specDimension: String = "",
    @SerializedName("resultDimension") val resultDimension: String = "",
    @SerializedName("extendLengthMax") val extendLengthMax: String = "",
    @SerializedName("wearMax") val wearMax: String = "",
    @SerializedName("safetyFactor") val safetyFactor: String = "",
    @SerializedName("defectAda") val defectAda: Boolean = false,
    @SerializedName("defectTidakAda") val defectTidakAda: Boolean = false,
    @SerializedName("description") val description: String = ""
)

data class OverheadCraneReportMainHookNde(
    @SerializedName("method") val method: String = "",
    @SerializedName("measurements") val measurements: OverheadCraneReportHookMeasurements = OverheadCraneReportHookMeasurements(),
    @SerializedName("tolerances") val tolerances: OverheadCraneReportHookTolerances = OverheadCraneReportHookTolerances(),
    @SerializedName("result") val result: String = ""
)

data class OverheadCraneReportHookMeasurements(
    @SerializedName("A") val a: String = "",
    @SerializedName("B") val b: String = "",
    @SerializedName("C") val c: String = "",
    @SerializedName("D") val d: String = "",
    @SerializedName("E") val e: String = "",
    @SerializedName("F") val f: String = "",
    @SerializedName("G") val g: String = "",
    @SerializedName("H") val h: String = ""
)

data class OverheadCraneReportHookTolerances(
    @SerializedName("A") val a: String = "",
    @SerializedName("B") val b: String = "",
    @SerializedName("C") val c: String = "",
    @SerializedName("D") val d: String = "",
    @SerializedName("E") val e: String = "",
    @SerializedName("F") val f: String = "",
    @SerializedName("G") val g: String = "",
    @SerializedName("H") val h: String = ""
)

// --- Testing DTOs (from PAA - Overhead Crane.json) ---

data class OverheadCraneReportTesting(
    @SerializedName("dynamicTest") val dynamicTest: OverheadCraneReportDynamicTest = OverheadCraneReportDynamicTest(),
    @SerializedName("staticTest") val staticTest: OverheadCraneReportStaticTest = OverheadCraneReportStaticTest()
)

data class OverheadCraneReportDynamicTest(
    @SerializedName("withoutLoad") val withoutLoad: List<OverheadCraneReportDynamicTestWithoutLoadItem> = emptyList(),
    @SerializedName("withLoad") val withLoad: List<OverheadCraneReportDynamicTestWithLoadItem> = emptyList()
)

data class OverheadCraneReportDynamicTestWithoutLoadItem(
    @SerializedName("test") val test: String = "",
    @SerializedName("shouldBe") val shouldBe: String = "",
    @SerializedName("testedOrMeasured") val testedOrMeasured: String = "",
    @SerializedName("remarks") val remarks: String = ""
)

data class OverheadCraneReportDynamicTestWithLoadItem(
    @SerializedName("load") val load: String = "",
    @SerializedName("hoist") val hoist: String = "",
    @SerializedName("traversing") val traversing: String = "",
    @SerializedName("traveling") val traveling: String = "",
    @SerializedName("brakeSystem") val brakeSystem: String = "",
    @SerializedName("remarks") val remarks: String = ""
)

data class OverheadCraneReportStaticTest(
    @SerializedName("testLoad") val testLoad: String = "",
    @SerializedName("deflection") val deflection: OverheadCraneReportDeflection = OverheadCraneReportDeflection(),
    @SerializedName("singleGirder") val singleGirder: OverheadCraneReportStaticTestSingleGirder = OverheadCraneReportStaticTestSingleGirder(),
    @SerializedName("doubleGirder") val doubleGirder: OverheadCraneReportStaticTestDoubleGirder = OverheadCraneReportStaticTestDoubleGirder(),
    @SerializedName("notes") val notes: String = ""
)

data class OverheadCraneReportDeflection(
    @SerializedName("singleGirder") val singleGirder: OverheadCraneReportDeflectionDetail = OverheadCraneReportDeflectionDetail(),
    @SerializedName("doubleGirder") val doubleGirder: OverheadCraneReportDeflectionDetail = OverheadCraneReportDeflectionDetail()
)

data class OverheadCraneReportDeflectionDetail(
    @SerializedName("measurement") val measurement: String = "",
    @SerializedName("description") val description: String = ""
)

data class OverheadCraneReportStaticTestSingleGirder(
    @SerializedName("design_mm") val designMm: String = "",
    @SerializedName("span_mm") val spanMm: String = "",
    @SerializedName("result") val result: Boolean = false
)

data class OverheadCraneReportStaticTestDoubleGirder(
    @SerializedName("design_mm") val designMm: String = "",
    @SerializedName("span_mm") val spanMm: String = "",
    @SerializedName("result") val result: Boolean = false
)

// --- Generic Inspection Result DTO (reusable for Visual Inspection items) ---

data class OverheadCraneReportInspectionResult(
    @SerializedName("status") val status: Boolean = false,
    @SerializedName("remarks") val remarks: String = ""
)