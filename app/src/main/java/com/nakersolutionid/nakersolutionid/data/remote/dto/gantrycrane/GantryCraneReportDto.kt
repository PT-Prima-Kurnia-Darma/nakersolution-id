package com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane

import com.google.gson.annotations.SerializedName

data class GantryCraneReportRequest(
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("generalData") val generalData: GantryCraneGeneralData,
    @SerializedName("technicalData") val technicalData: GantryCraneTechnicalData,
    @SerializedName("visualInspection") val visualInspection: GantryCraneVisualInspection,
    @SerializedName("ndt") val ndt: GantryCraneNdt,
    @SerializedName("dynamicTesting") val dynamicTesting: GantryCraneDynamicTesting,
    @SerializedName("staticTesting") val staticTesting: GantryCraneStaticTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recomendation") val recomendation: String,
)

// Data DTO for single Gantry Crane Report response
data class GantryCraneSingleReportResponseData(
    @SerializedName("laporan") val laporan: GantryCraneReportData
)

// Data DTO for list of Gantry Crane Reports response
data class GantryCraneListReportResponseData(
    @SerializedName("laporan") val laporan: List<GantryCraneReportData>
)

// Main DTO for Gantry Crane Report (used for create, update, and individual get)
data class GantryCraneReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("generalData") val generalData: GantryCraneGeneralData,
    @SerializedName("technicalData") val technicalData: GantryCraneTechnicalData,
    @SerializedName("visualInspection") val visualInspection: GantryCraneVisualInspection,
    @SerializedName("ndt") val ndt: GantryCraneNdt,
    @SerializedName("dynamicTesting") val dynamicTesting: GantryCraneDynamicTesting,
    @SerializedName("staticTesting") val staticTesting: GantryCraneStaticTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recomendation") val recomendation: String,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class GantryCraneGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyLocation") val companyLocation: String,
    @SerializedName("usageLocation") val usageLocation: String,
    @SerializedName("location") val location: String,
    @SerializedName("manufacturerHoist") val manufacturerHoist: String,
    @SerializedName("manufacturerStructure") val manufacturerStructure: String,
    @SerializedName("brandOrType") val brandOrType: String,
    @SerializedName("manufactureYear") val manufactureYear: String,
    @SerializedName("serialNumber") val serialNumber: String,
    @SerializedName("maxLiftingCapacityKg") val maxLiftingCapacityKg: String,
    @SerializedName("usagePermitNumber") val usagePermitNumber: String,
    @SerializedName("operatorcertificateStatus") val operatorCertificateStatus: String,
    @SerializedName("technicalDataManualStatus") val technicalDataManualStatus: String,
    @SerializedName("inspectionDate") val inspectionDate: String
)

data class GantryCraneTechnicalData(
    @SerializedName("liftHeight") val liftHeight: String,
    @SerializedName("girderLength") val girderLength: String,
    @SerializedName("hoistingSpeed") val hoistingSpeed: String,
    @SerializedName("travelingSpeed") val travelingSpeed: String,
    @SerializedName("traversingSpeed") val traversingSpeed: String,
    @SerializedName("driveMotorcapacity") val driveMotorCapacity: String,
    @SerializedName("hoistingpowerKw") val hoistingPowerKw: String,
    @SerializedName("travelingpowerKw") val travelingPowerKw: String,
    @SerializedName("traversingpowerKw") val traversingPowerKw: String,
    @SerializedName("hoistingtype") val hoistingType: String,
    @SerializedName("travelingtype") val travelingType: String,
    @SerializedName("traversingtype") val traversingType: String,
    @SerializedName("hoistingrpm") val hoistingRpm: String,
    @SerializedName("travelingrpm") val travelingRpm: String,
    @SerializedName("traversingrpm") val traversingRpm: String,
    @SerializedName("hoistingvoltageV") val hoistingVoltageV: String,
    @SerializedName("travelingvoltageV") val travelingVoltageV: String,
    @SerializedName("traversingvoltageV") val traversingVoltageV: String,
    @SerializedName("hoistingcurrentA") val hoistingCurrentA: String,
    @SerializedName("travelingcurrentA") val travelingCurrentA: String,
    @SerializedName("traversingcurrentA") val traversingCurrentA: String,
    @SerializedName("hoistingfrequencyHz") val hoistingFrequencyHz: String,
    @SerializedName("travelingfrequencyHz") val travelingFrequencyHz: String,
    @SerializedName("traversingfrequencyHz") val traversingFrequencyHz: String,
    @SerializedName("hoistingphase") val hoistingPhase: String,
    @SerializedName("travelingphase") val travelingPhase: String,
    @SerializedName("traversingphase") val traversingPhase: String,
    @SerializedName("hoistingpowerSupply") val hoistingPowerSupply: String,
    @SerializedName("travelingpowerSupply") val travelingPowerSupply: String,
    @SerializedName("traversingpowerSupply") val traversingPowerSupply: String,
    @SerializedName("braketype") val brakeType: String,
    @SerializedName("brakemodel") val brakeModel: String,
    @SerializedName("controlBrakehoistingtype") val controlBrakeHoistingType: String,
    @SerializedName("controlBraketravelingtype") val controlBrakeTravelingType: String,
    @SerializedName("controlBraketraversingtype") val controlBrakeTraversingType: String,
    @SerializedName("controlBrakehoistingmodel") val controlBrakeHoistingModel: String,
    @SerializedName("controlBraketravelingmodel") val controlBrakeTravelingModel: String,
    @SerializedName("controlBraketraversingmodel") val controlBrakeTraversingModel: String,
    @SerializedName("hookhoistingtype") val hookHoistingType: String,
    @SerializedName("hooktravelingtype") val hookTravelingType: String,
    @SerializedName("hooktraversingtype") val hookTraversingType: String,
    @SerializedName("hookhoistingcapacity") val hookHoistingCapacity: String,
    @SerializedName("hooktravelingcapacity") val hookTravelingCapacity: String,
    @SerializedName("hooktraversingcapacity") val hookTraversingCapacity: String,
    @SerializedName("hookhoistingmaterial") val hookHoistingMaterial: String,
    @SerializedName("hooktravelingmaterial") val hookTravelingMaterial: String,
    @SerializedName("hooktraversingmaterial") val hookTraversingMaterial: String,
    @SerializedName("wireRopeOrChainmediumType") val wireRopeOrChainMediumType: String,
    @SerializedName("mediumTypehoistingtype") val mediumTypeHoistingType: String,
    @SerializedName("mediumTypetravelingtype") val mediumTypeTravelingType: String,
    @SerializedName("mediumTypetraversingtype") val mediumTypeTraversingType: String,
    @SerializedName("mediumTypehoistingconstruction") val mediumTypeHoistingConstruction: String,
    @SerializedName("mediumTypetravelingconstruction") val mediumTypeTravelingConstruction: String,
    @SerializedName("mediumTypetraversingconstruction") val mediumTypeTraversingConstruction: String,
    @SerializedName("mediumTypehoistingdiameter") val mediumTypeHoistingDiameter: String,
    @SerializedName("mediumTypetravelingdiameter") val mediumTypeTravelingDiameter: String,
    @SerializedName("mediumTypetraversingdiameter") val mediumTypeTraversingDiameter: String,
    @SerializedName("mediumTypehoistinglength") val mediumTypeHoistingLength: String,
    @SerializedName("mediumTypetravelinglength") val mediumTypeTravelingLength: String,
    @SerializedName("mediumTypetraversinglength") val mediumTypeTraversingLength: String
)

// --- NEW VISUAL INSPECTION DATA CLASSES ---

data class GantryCraneVisualInspectionItem(
    @SerializedName("status") val status: Boolean,
    @SerializedName("result") val result: String
)

data class GantryCraneAnchorBolts(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem,
    @SerializedName("fastening") val fastening: GantryCraneVisualInspectionItem
)

data class GantryCraneColumnFrame(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem,
    @SerializedName("fastening") val fastening: GantryCraneVisualInspectionItem,
    @SerializedName("transverseReinforcement") val transverseReinforcement: GantryCraneVisualInspectionItem,
    @SerializedName("diagonalReinforcement") val diagonalReinforcement: GantryCraneVisualInspectionItem
)

data class GantryCraneLadder(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem,
    @SerializedName("fastening") val fastening: GantryCraneVisualInspectionItem
)

data class GantryCraneWorkingFloor(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem,
    @SerializedName("fastening") val fastening: GantryCraneVisualInspectionItem
)

data class GantryCraneFoundationAndStructure(
    @SerializedName("anchorBolts") val anchorBolts: GantryCraneAnchorBolts,
    @SerializedName("columnFrame") val columnFrame: GantryCraneColumnFrame,
    @SerializedName("ladder") val ladder: GantryCraneLadder,
    @SerializedName("workingFloor") val workingFloor: GantryCraneWorkingFloor
)

data class GantryCraneRailSupportBeam(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem,
    @SerializedName("fastening") val fastening: GantryCraneVisualInspectionItem
)

data class GantryCraneTravelingRail(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("railConnection") val railConnection: GantryCraneVisualInspectionItem,
    @SerializedName("railAlignment") val railAlignment: GantryCraneVisualInspectionItem,
    @SerializedName("interRailAlignment") val interRailAlignment: GantryCraneVisualInspectionItem,
    @SerializedName("interRailFlatness") val interRailFlatness: GantryCraneVisualInspectionItem,
    @SerializedName("railConnectionGap") val railConnectionGap: GantryCraneVisualInspectionItem,
    @SerializedName("railFastener") val railFastener: GantryCraneVisualInspectionItem,
    @SerializedName("railStopper") val railStopper: GantryCraneVisualInspectionItem
)

data class GantryCraneTraversingRail(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("railConnection") val railConnection: GantryCraneVisualInspectionItem,
    @SerializedName("railAlignment") val railAlignment: GantryCraneVisualInspectionItem,
    @SerializedName("interRailAlignment") val interRailAlignment: GantryCraneVisualInspectionItem,
    @SerializedName("interRailFlatness") val interRailFlatness: GantryCraneVisualInspectionItem,
    @SerializedName("railConnectionGap") val railConnectionGap: GantryCraneVisualInspectionItem,
    @SerializedName("railFastener") val railFastener: GantryCraneVisualInspectionItem,
    @SerializedName("railStopper") val railStopper: GantryCraneVisualInspectionItem
)

data class GantryCraneMechanismAndRail(
    @SerializedName("railSupportBeam") val railSupportBeam: GantryCraneRailSupportBeam,
    @SerializedName("travelingRail") val travelingRail: GantryCraneTravelingRail,
    @SerializedName("traversingRail") val traversingRail: GantryCraneTraversingRail
)

data class GantryCraneGirder(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("camber") val camber: GantryCraneVisualInspectionItem,
    @SerializedName("connection") val connection: GantryCraneVisualInspectionItem,
    @SerializedName("endGirderConnection") val endGirderConnection: GantryCraneVisualInspectionItem,
    @SerializedName("truckMountingOnGirder") val truckMountingOnGirder: GantryCraneVisualInspectionItem
)

data class GantryCraneTravelingGearbox(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("lubricatingOil") val lubricatingOil: GantryCraneVisualInspectionItem,
    @SerializedName("oilSeal") val oilSeal: GantryCraneVisualInspectionItem
)

data class GantryCraneDriveWheels(
    @SerializedName("wear") val wear: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem,
    @SerializedName("flangeCondition") val flangeCondition: GantryCraneVisualInspectionItem,
    @SerializedName("chainCondition") val chainCondition: GantryCraneVisualInspectionItem
)

data class GantryCraneIdleWheels(
    @SerializedName("safety") val safety: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem,
    @SerializedName("flangeCondition") val flangeCondition: GantryCraneVisualInspectionItem
)

data class GantryCraneWheelConnector(
    @SerializedName("alignment") val alignment: GantryCraneVisualInspectionItem,
    @SerializedName("crossJoint") val crossJoint: GantryCraneVisualInspectionItem,
    @SerializedName("lubrication") val lubrication: GantryCraneVisualInspectionItem
)

data class GantryCraneGirderStopper(
    @SerializedName("condition") val condition: GantryCraneVisualInspectionItem,
    @SerializedName("reinforcement") val reinforcement: GantryCraneVisualInspectionItem
)

data class GantryCraneGirderAndTrolley(
    @SerializedName("girder") val girder: GantryCraneGirder,
    @SerializedName("travelingGearbox") val travelingGearbox: GantryCraneTravelingGearbox,
    @SerializedName("driveWheels") val driveWheels: GantryCraneDriveWheels,
    @SerializedName("idleWheels") val idleWheels: GantryCraneIdleWheels,
    @SerializedName("wheelConnector") val wheelConnector: GantryCraneWheelConnector,
    @SerializedName("girderStopper") val girderStopper: GantryCraneGirderStopper
)

data class GantryCraneTrolleyTraversingGearbox(
    @SerializedName("fastening") val fastening: GantryCraneVisualInspectionItem,
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("lubricatingOil") val lubricatingOil: GantryCraneVisualInspectionItem,
    @SerializedName("oilSeal") val oilSeal: GantryCraneVisualInspectionItem
)

data class GantryCraneTrolleyDriveWheels(
    @SerializedName("wear") val wear: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem,
    @SerializedName("flangeCondition") val flangeCondition: GantryCraneVisualInspectionItem,
    @SerializedName("chainCondition") val chainCondition: GantryCraneVisualInspectionItem
)

data class GantryCraneTrolleyIdleWheels(
    @SerializedName("wear") val wear: GantryCraneVisualInspectionItem,
    @SerializedName("cracks") val cracks: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem,
    @SerializedName("flangeCondition") val flangeCondition: GantryCraneVisualInspectionItem
)

data class GantryCraneTrolleyWheelConnector(
    @SerializedName("alignment") val alignment: GantryCraneVisualInspectionItem,
    @SerializedName("crossJoint") val crossJoint: GantryCraneVisualInspectionItem,
    @SerializedName("lubrication") val lubrication: GantryCraneVisualInspectionItem
)

data class GantryCraneTrolleyGirderStopper(
    @SerializedName("condition") val condition: GantryCraneVisualInspectionItem,
    @SerializedName("reinforcement") val reinforcement: GantryCraneVisualInspectionItem
)

data class GantryCraneTrolleyMechanism(
    @SerializedName("trolleyTraversingGearbox") val trolleyTraversingGearbox: GantryCraneTrolleyTraversingGearbox,
    @SerializedName("trolleyDriveWheels") val trolleyDriveWheels: GantryCraneTrolleyDriveWheels,
    @SerializedName("trolleyIdleWheels") val trolleyIdleWheels: GantryCraneTrolleyIdleWheels,
    @SerializedName("trolleyWheelConnector") val trolleyWheelConnector: GantryCraneTrolleyWheelConnector,
    @SerializedName("trolleyGirderStopper") val trolleyGirderStopper: GantryCraneTrolleyGirderStopper
)

data class GantryCraneWindingDrum(
    @SerializedName("groove") val groove: GantryCraneVisualInspectionItem,
    @SerializedName("grooveLip") val grooveLip: GantryCraneVisualInspectionItem,
    @SerializedName("flanges") val flanges: GantryCraneVisualInspectionItem
)

data class GantryCraneVisualBrakeInspection(
    @SerializedName("wear") val wear: GantryCraneVisualInspectionItem,
    @SerializedName("adjustment") val adjustment: GantryCraneVisualInspectionItem
)

data class GantryCraneHoistGearbox(
    @SerializedName("lubrication") val lubrication: GantryCraneVisualInspectionItem,
    @SerializedName("oilSeal") val oilSeal: GantryCraneVisualInspectionItem
)

data class GantryCranePulleySprocket(
    @SerializedName("pulleyGroove") val pulleyGroove: GantryCraneVisualInspectionItem,
    @SerializedName("pulleyLip") val pulleyLip: GantryCraneVisualInspectionItem,
    @SerializedName("pulleyPin") val pulleyPin: GantryCraneVisualInspectionItem,
    @SerializedName("bearing") val bearing: GantryCraneVisualInspectionItem,
    @SerializedName("pulleyGuard") val pulleyGuard: GantryCraneVisualInspectionItem,
    @SerializedName("ropeChainGuard") val ropeChainGuard: GantryCraneVisualInspectionItem
)

data class GantryCraneHook(
    @SerializedName("wear") val wear: GantryCraneVisualInspectionItem,
    @SerializedName("hookOpeningGap") val hookOpeningGap: GantryCraneVisualInspectionItem,
    @SerializedName("swivelNutAndBearing") val swivelNutAndBearing: GantryCraneVisualInspectionItem,
    @SerializedName("trunnion") val trunnion: GantryCraneVisualInspectionItem
)

data class GantryCraneWireRope(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("wear") val wear: GantryCraneVisualInspectionItem,
    @SerializedName("breakage") val breakage: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem
)

data class GantryCraneChain(
    @SerializedName("corrosion") val corrosion: GantryCraneVisualInspectionItem,
    @SerializedName("wear") val wear: GantryCraneVisualInspectionItem,
    @SerializedName("crackOrBreakage") val crackOrBreakage: GantryCraneVisualInspectionItem,
    @SerializedName("deformation") val deformation: GantryCraneVisualInspectionItem
)

data class GantryCraneLiftingEquipment(
    @SerializedName("windingDrum") val windingDrum: GantryCraneWindingDrum,
    @SerializedName("visualBrakeInspection") val visualBrakeInspection: GantryCraneVisualBrakeInspection,
    @SerializedName("hoistGearbox") val hoistGearbox: GantryCraneHoistGearbox,
    @SerializedName("pulleySprocket") val pulleySprocket: GantryCranePulleySprocket,
    @SerializedName("mainHook") val mainHook: GantryCraneHook,
    @SerializedName("auxiliaryHook") val auxiliaryHook: GantryCraneHook,
    @SerializedName("mainWireRope") val mainWireRope: GantryCraneWireRope,
    @SerializedName("auxiliaryWireRope") val auxiliaryWireRope: GantryCraneWireRope,
    @SerializedName("mainChain") val mainChain: GantryCraneChain,
    @SerializedName("auxiliaryChain") val auxiliaryChain: GantryCraneChain
)

data class GantryCraneLimitSwitch(
    @SerializedName("longTravel") val longTravel: GantryCraneVisualInspectionItem,
    @SerializedName("crossTravel") val crossTravel: GantryCraneVisualInspectionItem,
    @SerializedName("hoist") val hoist: GantryCraneVisualInspectionItem
)

data class GantryCraneOperatorCabin(
    @SerializedName("safetyLadder") val safetyLadder: GantryCraneVisualInspectionItem,
    @SerializedName("door") val door: GantryCraneVisualInspectionItem,
    @SerializedName("window") val window: GantryCraneVisualInspectionItem,
    @SerializedName("fanOrAC") val fanOrAC: GantryCraneVisualInspectionItem,
    @SerializedName("controlLeversOrButtons") val controlLeversOrButtons: GantryCraneVisualInspectionItem,
    @SerializedName("pendantControl") val pendantControl: GantryCraneVisualInspectionItem,
    @SerializedName("lighting") val lighting: GantryCraneVisualInspectionItem,
    @SerializedName("horn") val horn: GantryCraneVisualInspectionItem,
    @SerializedName("fuseProtection") val fuseProtection: GantryCraneVisualInspectionItem,
    @SerializedName("communicationDevice") val communicationDevice: GantryCraneVisualInspectionItem,
    @SerializedName("fireExtinguisher") val fireExtinguisher: GantryCraneVisualInspectionItem,
    @SerializedName("operationalSigns") val operationalSigns: GantryCraneVisualInspectionItem,
    @SerializedName("ignitionOrMasterSwitch") val ignitionOrMasterSwitch: GantryCraneVisualInspectionItem
)

data class GantryCraneElectricalComponents(
    @SerializedName("panelConductorConnector") val panelConductorConnector: GantryCraneVisualInspectionItem,
    @SerializedName("conductorProtection") val conductorProtection: GantryCraneVisualInspectionItem,
    @SerializedName("motorInstallationSafetySystem") val motorInstallationSafetySystem: GantryCraneVisualInspectionItem,
    @SerializedName("groundingSystem") val groundingSystem: GantryCraneVisualInspectionItem,
    @SerializedName("installation") val installation: GantryCraneVisualInspectionItem
)

data class GantryCraneControlAndSafetySystem(
    @SerializedName("limitSwitch") val limitSwitch: GantryCraneLimitSwitch,
    @SerializedName("operatorCabin") val operatorCabin: GantryCraneOperatorCabin,
    @SerializedName("electricalComponents") val electricalComponents: GantryCraneElectricalComponents
)

data class GantryCraneVisualInspection(
    @SerializedName("foundationAndStructure") val foundationAndStructure: GantryCraneFoundationAndStructure,
    @SerializedName("mechanismAndRail") val mechanismAndRail: GantryCraneMechanismAndRail,
    @SerializedName("girderAndTrolley") val girderAndTrolley: GantryCraneGirderAndTrolley,
    @SerializedName("trolleyMechanism") val trolleyMechanism: GantryCraneTrolleyMechanism,
    @SerializedName("liftingEquipment") val liftingEquipment: GantryCraneLiftingEquipment,
    @SerializedName("controlAndSafetySystem") val controlAndSafetySystem: GantryCraneControlAndSafetySystem
)


data class GantryCraneNdt(
    @SerializedName("wireropeMethod") val wireropeMethod: String,
    @SerializedName("wireropeNumber") val wireropeNumber: List<GantryCraneWireropeNumber>,
    @SerializedName("HookspecA") val hookspecA: String,
    @SerializedName("HookspecB") val hookspecB: String,
    @SerializedName("HookspecC") val hookspecC: String,
    @SerializedName("HookspecD") val hookspecD: String,
    @SerializedName("HookspecE") val hookspecE: String,
    @SerializedName("HookspecF") val hookspecF: String,
    @SerializedName("HookspecG") val hookspecG: String,
    @SerializedName("HookspecH") val hookspecH: String,
    @SerializedName("HookspecBaik") val hookspecBaik: Boolean,
    @SerializedName("HookspecTidakBaik") val hookspecTidakBaik: Boolean,
    @SerializedName("HookspecDesc") val hookspecDesc: String,
    @SerializedName("measurementResultsA") val measurementResultsA: String,
    @SerializedName("measurementResultsB") val measurementResultsB: String,
    @SerializedName("measurementResultsC") val measurementResultsC: String,
    @SerializedName("measurementResultsD") val measurementResultsD: String,
    @SerializedName("measurementResultsE") val measurementResultsE: String,
    @SerializedName("measurementResultsF") val measurementResultsF: String,
    @SerializedName("measurementResultsG") val measurementResultsG: String,
    @SerializedName("measurementResultsH") val measurementResultsH: String,
    @SerializedName("measurementResultsBaik") val measurementResultsBaik: Boolean,
    @SerializedName("measurementResultsTidakBaik") val measurementResultsTidakBaik: Boolean,
    @SerializedName("measurementResultsDesc") val measurementResultsDesc: String,
    @SerializedName("toleranceA") val toleranceA: String,
    @SerializedName("toleranceB") val toleranceB: String,
    @SerializedName("toleranceC") val toleranceC: String,
    @SerializedName("toleranceD") val toleranceD: String,
    @SerializedName("toleranceE") val toleranceE: String,
    @SerializedName("toleranceF") val toleranceF: String,
    @SerializedName("toleranceG") val toleranceG: String,
    @SerializedName("toleranceH") val toleranceH: String,
    @SerializedName("toleranceBaik") val toleranceBaik: Boolean,
    @SerializedName("toleranceTidakBaik") val toleranceTidakBaik: Boolean,
    @SerializedName("toleranceDesc") val toleranceDesc: String,
    @SerializedName("griderMethod") val griderMethod: String,
    @SerializedName("griderNumber") val griderNumber: List<GantryCraneGriderNumber>
)

data class GantryCraneWireropeNumber(
    @SerializedName("wireropeNumber") val wireropeNumber: String,
    @SerializedName("wireropeUsed") val wireropeUsed: String,
    @SerializedName("dimensionSpec") val dimensionSpec: String,
    @SerializedName("dimensionResult") val dimensionResult: String,
    @SerializedName("construction") val construction: String,
    @SerializedName("type") val type: String,
    @SerializedName("length") val length: String,
    @SerializedName("age") val age: String,
    @SerializedName("defectAda") val defectAda: Boolean,
    @SerializedName("defectTidakAda") val defectTidakAda: Boolean,
    @SerializedName("description") val description: String
)

data class GantryCraneGriderNumber(
    @SerializedName("griderNumber") val griderNumber: String,
    @SerializedName("griderLocation") val griderLocation: String,
    @SerializedName("griderAda") val griderAda: Boolean,
    @SerializedName("griderTidakAda") val griderTidakAda: Boolean,
    @SerializedName("griderDesc") val griderDesc: String
)

data class GantryCraneDynamicTesting(
    @SerializedName("travellingStatus") val travellingStatus: String,
    @SerializedName("travellingDesc") val travellingDesc: String,
    @SerializedName("traversingStatus") val traversingStatus: String,
    @SerializedName("traversingDesc") val traversingDesc: String,
    @SerializedName("hoistingStatus") val hoistingStatus: String,
    @SerializedName("hoistingDesc") val hoistingDesc: String,
    @SerializedName("safetyDeviceStatus") val safetyDeviceStatus: String,
    @SerializedName("safetyDeviceDesc") val safetyDeviceDesc: String,
    @SerializedName("brakeSwitchStatus") val brakeSwitchStatus: String,
    @SerializedName("brakeSwitchDesc") val brakeSwitchDesc: String,
    @SerializedName("brakeLockingStatus") val brakeLockingStatus: String,
    @SerializedName("brakeLockingDesc") val brakeLockingDesc: String,
    @SerializedName("instalasionElectricStatus") val instalasionElectricStatus: String,
    @SerializedName("instalasionElectricDesc") val instalasionElectricDesc: String,
    @SerializedName("hoist25") val hoist25: String,
    @SerializedName("travesing25") val travesing25: String,
    @SerializedName("travelling25") val travelling25: String,
    @SerializedName("brakeSystem25") val brakeSystem25: String,
    @SerializedName("desc25") val desc25: String,
    @SerializedName("hoist50") val hoist50: String,
    @SerializedName("travesing50") val travesing50: String,
    @SerializedName("travelling50") val travelling50: String,
    @SerializedName("brakeSystem50") val brakeSystem50: String,
    @SerializedName("desc50") val desc50: String,
    @SerializedName("hoist75") val hoist75: String,
    @SerializedName("travesing75") val travesing75: String,
    @SerializedName("travelling75") val travelling75: String,
    @SerializedName("brakeSystem75") val brakeSystem75: String,
    @SerializedName("desc75") val desc75: String,
    @SerializedName("hoist100") val hoist100: String,
    @SerializedName("travesing100") val travesing100: String,
    @SerializedName("travelling100") val travelling100: String,
    @SerializedName("brakeSystem100") val brakeSystem100: String,
    @SerializedName("desc100") val desc100: String
)

data class GantryCraneStaticTesting(
    @SerializedName("loadTest") val loadTest: String,
    @SerializedName("basedDesign") val basedDesign: String,
    @SerializedName("lengthSpan") val lengthSpan: String,
    @SerializedName("xspan") val xspan: String,
    @SerializedName("resultDefleksi") val resultDefleksi: Boolean,
    @SerializedName("defleksiPosision") val defleksiPosision: List<GantryCraneDefleksiPosision>
)

data class GantryCraneDefleksiPosision(
    @SerializedName("defleksiPosision") val defleksiPosision: String,
    @SerializedName("defleksiMeasuraments") val defleksiMeasurements: String,
    @SerializedName("defleksiStandard") val defleksiStandard: String,
    @SerializedName("defleksiDesc") val defleksiDesc: String
)