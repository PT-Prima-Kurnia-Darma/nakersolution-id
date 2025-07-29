package com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane

import com.google.gson.annotations.SerializedName
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus

data class MobileCraneReportRequest(
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("generalData") val generalData: MobileCraneGeneralData,
    @SerializedName("technicalData") val technicalData: MobileCraneTechnicalData,
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: MobileCraneInspectionAndTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendation") val recommendation: String,
)

// Data DTO for single Mobile Crane Report response
data class MobileCraneSingleReportResponseData(
    @SerializedName("laporan") val laporan: MobileCraneReportData
)

// Data DTO for list of Mobile Crane Reports response
data class MobileCraneListReportResponseData(
    @SerializedName("laporan") val laporan: List<MobileCraneReportData>
)

// Main DTO for Mobile Crane Report (used for create, update, and individual get)
data class MobileCraneReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("extraId") val extraId: Long,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("generalData") val generalData: MobileCraneGeneralData,
    @SerializedName("technicalData") val technicalData: MobileCraneTechnicalData,
    @SerializedName("inspectionAndTesting") val inspectionAndTesting: MobileCraneInspectionAndTesting,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendation") val recommendation: String,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class MobileCraneGeneralData(
    @SerializedName("ownerName") val ownerName: String,
    @SerializedName("ownerAddress") val ownerAddress: String,
    @SerializedName("userSubcontractorPersonInCharge") val userSubcontractorPersonInCharge: String,
    @SerializedName("userAddress") val userAddress: String,
    @SerializedName("unitLocation") val unitLocation: String,
    @SerializedName("operatorName") val operatorName: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("brandType") val brandType: String,
    @SerializedName("locationAndYearOfManufacture") val locationAndYearOfManufacture: String,
    @SerializedName("serialNumberUnitNumber") val serialNumberUnitNumber: String,
    @SerializedName("capacityWorkingLoad") val capacityWorkingLoad: String,
    @SerializedName("intendedUse") val intendedUse: String,
    @SerializedName("usagePermitNumber") val usagePermitNumber: String,
    @SerializedName("operatorCertificate") val operatorCertificate: String,
    @SerializedName("equipmentHistory") val equipmentHistory: String,
    @SerializedName("inspectionDate") val inspectionDate: String
)

data class MobileCraneTechnicalData(
    @SerializedName("maximumWorkingLoadCapacity") val maximumWorkingLoadCapacity: String,
    @SerializedName("boomLength") val boomLength: String,
    @SerializedName("maximumJibLength") val maximumJibLength: String,
    @SerializedName("maximumJibWorkingLoad") val maximumJibWorkingLoad: String,
    @SerializedName("maxBoomJibLength") val maxBoomJibLength: String,
    @SerializedName("craneWeight") val craneWeight: String,
    @SerializedName("maxLiftingHeight") val maxLiftingHeight: String,
    @SerializedName("boomWorkingAngle") val boomWorkingAngle: String,
    @SerializedName("engineNumber") val engineNumber: String,
    @SerializedName("type") val type: String,
    @SerializedName("numberOfCylinders") val numberOfCylinders: String,
    @SerializedName("netPower") val netPower: String,
    @SerializedName("brandYearOfManufacture") val brandYearOfManufacture: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("mainHookType") val mainHookType: String,
    @SerializedName("mainHookCapacity") val mainHookCapacity: String,
    @SerializedName("mainHookMaterial") val mainHookMaterial: String,
    @SerializedName("mainHookSerialNumber") val mainHookSerialNumber: String,
    @SerializedName("auxiliaryHookType") val auxiliaryHookType: String,
    @SerializedName("auxiliaryHookCapacity") val auxiliaryHookCapacity: String,
    @SerializedName("auxiliaryHookMaterial") val auxiliaryHookMaterial: String,
    @SerializedName("auxiliaryHookSerialNumber") val auxiliaryHookSerialNumber: String,
    @SerializedName("wireRopeMainLoadHoistDrumDiameter") val wireRopeMainLoadHoistDrumDiameter: String,
    @SerializedName("wireRopeMainLoadHoistDrumType") val wireRopeMainLoadHoistDrumType: String,
    @SerializedName("wireRopeMainLoadHoistDrumConstruction") val wireRopeMainLoadHoistDrumConstruction: String,
    @SerializedName("wireRopeMainLoadHoistDrumBreakingStrength") val wireRopeMainLoadHoistDrumBreakingStrength: String,
    @SerializedName("wireRopeMainLoadHoistDrumLength") val wireRopeMainLoadHoistDrumLength: String,
    @SerializedName("wireRopeAuxiliaryLoadHoistDrumDiameter") val wireRopeAuxiliaryLoadHoistDrumDiameter: String,
    @SerializedName("wireRopeAuxiliaryLoadHoistDrumType") val wireRopeAuxiliaryLoadHoistDrumType: String,
    @SerializedName("wireRopeAuxiliaryLoadHoistDrumConstruction") val wireRopeAuxiliaryLoadHoistDrumConstruction: String,
    @SerializedName("wireRopeAuxiliaryLoadHoistDrumLength") val wireRopeAuxiliaryLoadHoistDrumLength: String,
    @SerializedName("wireRopeAuxiliaryLoadHoistDrumBreakingStrength") val wireRopeAuxiliaryLoadHoistDrumBreakingStrength: String,
    @SerializedName("wireRopeBoomHoistDrumDiameter") val wireRopeBoomHoistDrumDiameter: String,
    @SerializedName("wireRopeBoomHoistDrumType") val wireRopeBoomHoistDrumType: String,
    @SerializedName("wireRopeBoomHoistDrumConstruction") val wireRopeBoomHoistDrumConstruction: String,
    @SerializedName("wireRopeBoomHoistDrumLength") val wireRopeBoomHoistDrumLength: String,
    @SerializedName("wireRopeBoomHoistDrumBreakingStrength") val wireRopeBoomHoistDrumBreakingStrength: String
)

data class MobileCraneInspectionAndTesting(
    @SerializedName("visualFoundationAndBolts") val visualFoundationAndBolts: VisualFoundationAndBolts,
    @SerializedName("visualFrameColumnsOnFoundation") val visualFrameColumnsOnFoundation: VisualFrameColumnsOnFoundation,
    @SerializedName("visualLadder") val visualLadder: VisualLadder,
    @SerializedName("visualWorkingPlatform") val visualWorkingPlatform: VisualWorkingPlatform,
    @SerializedName("visualOutriggers") val visualOutriggers: VisualOutriggers,
    @SerializedName("visualTurntable") val visualTurntable: VisualTurntable,
    @SerializedName("visualLatticeBoom") val visualLatticeBoom: VisualLatticeBoom,
    @SerializedName("visualSteering") val visualSteering: VisualSteering,
    @SerializedName("visualBrake") val visualBrake: VisualBrake,
    @SerializedName("visualTravelDrum") val visualTravelDrum: VisualTravelDrum,
    @SerializedName("visualMainWinch") val visualMainWinch: VisualMainWinch,
    @SerializedName("visualAuxiliaryWinch") val visualAuxiliaryWinch: VisualAuxiliaryWinch,
    @SerializedName("visualHoistGearBlock") val visualHoistGearBlock: VisualHoistGearBlock,
    @SerializedName("visualMainPulley") val visualMainPulley: VisualMainPulley,
    @SerializedName("visualMainHook") val visualMainHook: VisualMainHook,
    @SerializedName("visualAuxiliaryHook") val visualAuxiliaryHook: VisualAuxiliaryHook,
    @SerializedName("visualMainWireRope") val visualMainWireRope: VisualWireRope,
    @SerializedName("visualAuxiliaryWireRope") val visualAuxiliaryWireRope: VisualWireRope,
    @SerializedName("visualLimitSwitch") val visualLimitSwitch: VisualLimitSwitch,
    @SerializedName("visualInternalCombustionEngine") val visualInternalCombustionEngine: VisualInternalCombustionEngine,
    @SerializedName("visualHydraulic") val visualHydraulic: VisualHydraulic,
    @SerializedName("visualControlValve") val visualControlValve: VisualControlValve,
    @SerializedName("visualHydraulicCylinder") val visualHydraulicCylinder: VisualHydraulicCylinder,
    @SerializedName("visualPneumatic") val visualPneumatic: VisualPneumatic,
    @SerializedName("visualOperatorCabin") val visualOperatorCabin: VisualOperatorCabin,
    @SerializedName("visualElectricalComponents") val visualElectricalComponents: VisualElectricalComponents,
    @SerializedName("visualSafetyDevices") val visualSafetyDevices: VisualSafetyDevices,
    @SerializedName("ndtSteelWireRope") val ndtSteelWireRope: NdtSteelWireRope,
    @SerializedName("ndtBoom") val ndtBoom: NdtBoom,
    @SerializedName("ndtMainHook") val ndtMainHook: NdtHook,
    @SerializedName("ndtAuxiliaryHook") val ndtAuxiliaryHook: NdtHook,
    @SerializedName("ndtMainDrum") val ndtMainDrum: NdtDrum,
    @SerializedName("ndtAuxiliaryDrum") val ndtAuxiliaryDrum: NdtDrum,
    @SerializedName("ndtMainPulley") val ndtMainPulley: NdtPulley,
    @SerializedName("ndtAuxiliaryPulley") val ndtAuxiliaryPulley: NdtPulley,
    @SerializedName("testingFunction") val testingFunction: TestingFunction,
    @SerializedName("dynamicMainHookTests") val dynamicMainHookTests: List<MobileCraneDynamicHookTest>,
    @SerializedName("dynamicAuxiliaryHookTests") val dynamicAuxiliaryHookTests: List<MobileCraneDynamicHookTest>,
    @SerializedName("staticMainHookTests") val staticMainHookTests: List<MobileCraneStaticHookTest>,
    @SerializedName("staticAuxiliaryHookTests") val staticAuxiliaryHookTests: List<MobileCraneStaticHookTest>
)

data class VisualFoundationAndBolts(
    @SerializedName("corrosion") val corrosion: ResultStatus,
    @SerializedName("cracks") val cracks: ResultStatus,
    @SerializedName("deformation") val deformation: ResultStatus,
    @SerializedName("tightness") val tightness: ResultStatus
)

data class VisualFrameColumnsOnFoundation(
    @SerializedName("corrosion") val corrosion: ResultStatus,
    @SerializedName("cracks") val cracks: ResultStatus,
    @SerializedName("deformation") val deformation: ResultStatus,
    @SerializedName("fastening") val fastening: ResultStatus,
    @SerializedName("transverseReinforcement") val transverseReinforcement: ResultStatus,
    @SerializedName("diagonalReinforcement") val diagonalReinforcement: ResultStatus
)

data class VisualLadder(
    @SerializedName("corrosion") val corrosion: ResultStatus,
    @SerializedName("cracks") val cracks: ResultStatus,
    @SerializedName("deformation") val deformation: ResultStatus,
    @SerializedName("fastening") val fastening: ResultStatus
)

data class VisualWorkingPlatform(
    @SerializedName("corrosion") val corrosion: ResultStatus,
    @SerializedName("cracks") val cracks: ResultStatus,
    @SerializedName("deformation") val deformation: ResultStatus,
    @SerializedName("fastening") val fastening: ResultStatus
)

data class VisualOutriggers(
    @SerializedName("outriggerArmHousing") val outriggerArmHousing: ResultStatus,
    @SerializedName("outriggerArms") val outriggerArms: ResultStatus,
    @SerializedName("jack") val jack: ResultStatus,
    @SerializedName("outriggerPads") val outriggerPads: ResultStatus,
    @SerializedName("housingConnectionToChassis") val housingConnectionToChassis: ResultStatus,
    @SerializedName("outriggerSafetyLocks") val outriggerSafetyLocks: ResultStatus
)

data class VisualTurntable(
    @SerializedName("slewingRollerBearing") val slewingRollerBearing: ResultStatus,
    @SerializedName("brakeHousing") val brakeHousing: ResultStatus,
    @SerializedName("brakeLiningsAndShoes") val brakeLiningsAndShoes: ResultStatus,
    @SerializedName("drumSurface") val drumSurface: ResultStatus,
    @SerializedName("pressureCylinder") val pressureCylinder: ResultStatus,
    @SerializedName("drumAxle") val drumAxle: ResultStatus,
    @SerializedName("leversPinsBolts") val leversPinsBolts: ResultStatus,
    @SerializedName("guard") val guard: ResultStatus
)

data class VisualLatticeBoom(
    @SerializedName("mainBoom") val mainBoom: ResultStatus,
    @SerializedName("boomSection") val boomSection: ResultStatus,
    @SerializedName("topPulley") val topPulley: ResultStatus,
    @SerializedName("pulleyGuard") val pulleyGuard: ResultStatus,
    @SerializedName("wireRopeGuard") val wireRopeGuard: ResultStatus,
    @SerializedName("pulleyGrooveLip") val pulleyGrooveLip: ResultStatus,
    @SerializedName("pivotPin") val pivotPin: ResultStatus,
    @SerializedName("wireRopeGuidePulley") val wireRopeGuidePulley: ResultStatus
)

data class VisualSteering(
    @SerializedName("mainClutch") val mainClutch: ResultStatus,
    @SerializedName("transmission") val transmission: ResultStatus,
    @SerializedName("frontWheel") val frontWheel: ResultStatus,
    @SerializedName("middleWheel") val middleWheel: ResultStatus,
    @SerializedName("rearWheel") val rearWheel: ResultStatus
)

data class VisualBrake(
    @SerializedName("serviceBrake") val serviceBrake: ResultStatus,
    @SerializedName("parkingBrake") val parkingBrake: ResultStatus,
    @SerializedName("brakeHousing") val brakeHousing: ResultStatus,
    @SerializedName("brakeLiningsAndShoes") val brakeLiningsAndShoes: ResultStatus,
    @SerializedName("drumSurface") val drumSurface: ResultStatus,
    @SerializedName("leversPinsBolts") val leversPinsBolts: ResultStatus,
    @SerializedName("guard") val guard: ResultStatus
)

data class VisualTravelDrum(
    @SerializedName("clutchHousing") val clutchHousing: ResultStatus,
    @SerializedName("clutchLining") val clutchLining: ResultStatus,
    @SerializedName("clutchDrumSurface") val clutchDrumSurface: ResultStatus,
    @SerializedName("leversPinsBolts") val leversPinsBolts: ResultStatus,
    @SerializedName("guard") val guard: ResultStatus
)

data class VisualMainWinch(
    @SerializedName("drumMounting") val drumMounting: ResultStatus,
    @SerializedName("windingDrumSurface") val windingDrumSurface: ResultStatus,
    @SerializedName("brakeLiningsAndShoes") val brakeLiningsAndShoes: ResultStatus,
    @SerializedName("brakeDrumSurface") val brakeDrumSurface: ResultStatus,
    @SerializedName("brakeHousing") val brakeHousing: ResultStatus,
    @SerializedName("clutchLiningsAndShoes") val clutchLiningsAndShoes: ResultStatus,
    @SerializedName("clutchDrumSurface") val clutchDrumSurface: ResultStatus,
    @SerializedName("groove") val groove: ResultStatus,
    @SerializedName("grooveLip") val grooveLip: ResultStatus,
    @SerializedName("flanges") val flanges: ResultStatus,
    @SerializedName("brakeActuatorLeversPinsAndBolts") val brakeActuatorLeversPinsAndBolts: ResultStatus
)

data class VisualAuxiliaryWinch(
    @SerializedName("drumMounting") val drumMounting: ResultStatus,
    @SerializedName("windingDrumSurface") val windingDrumSurface: ResultStatus,
    @SerializedName("brakeLiningsAndShoes") val brakeLiningsAndShoes: ResultStatus,
    @SerializedName("brakeDrumSurface") val brakeDrumSurface: ResultStatus,
    @SerializedName("brakeHousing") val brakeHousing: ResultStatus,
    @SerializedName("clutchLiningsAndShoes") val clutchLiningsAndShoes: ResultStatus,
    @SerializedName("clutchDrumSurface") val clutchDrumSurface: ResultStatus,
    @SerializedName("groove") val groove: ResultStatus,
    @SerializedName("grooveLip") val grooveLip: ResultStatus,
    @SerializedName("flanges") val flanges: ResultStatus,
    @SerializedName("brakeActuatorLeversPinsAndBolts") val brakeActuatorLeversPinsAndBolts: ResultStatus
)

data class VisualHoistGearBlock(
    @SerializedName("lubrication") val lubrication: ResultStatus,
    @SerializedName("oilSeal") val oilSeal: ResultStatus
)

data class VisualMainPulley(
    @SerializedName("pulleyGroove") val pulleyGroove: ResultStatus,
    @SerializedName("pulleyGrooveLip") val pulleyGrooveLip: ResultStatus,
    @SerializedName("pulleyPin") val pulleyPin: ResultStatus,
    @SerializedName("bearing") val bearing: ResultStatus,
    @SerializedName("pulleyGuard") val pulleyGuard: ResultStatus,
    @SerializedName("wireRopeGuard") val wireRopeGuard: ResultStatus
)

data class VisualMainHook(
    @SerializedName("swivelNutAndBearing") val swivelNutAndBearing: ResultStatus,
    @SerializedName("trunnion") val trunnion: ResultStatus,
    @SerializedName("safetyLatch") val safetyLatch: ResultStatus
)

data class VisualAuxiliaryHook(
    @SerializedName("freeFallWeight") val freeFallWeight: ResultStatus,
    @SerializedName("swivelNutAndBearing") val swivelNutAndBearing: ResultStatus,
    @SerializedName("safetyLatch") val safetyLatch: ResultStatus
)

data class VisualWireRope(
    @SerializedName("corrosion") val corrosion: ResultStatus,
    @SerializedName("wear") val wear: ResultStatus,
    @SerializedName("breakage") val breakage: ResultStatus,
    @SerializedName("deformation") val deformation: ResultStatus
)

data class VisualLimitSwitch(
    @SerializedName("longTravel") val longTravel: ResultStatus,
    @SerializedName("crossTravel") val crossTravel: ResultStatus,
    @SerializedName("hoisting") val hoisting: ResultStatus
)

data class VisualInternalCombustionEngine(
    @SerializedName("coolingSystem") val coolingSystem: ResultStatus,
    @SerializedName("lubricationSystem") val lubricationSystem: ResultStatus,
    @SerializedName("engineMounting") val engineMounting: ResultStatus,
    @SerializedName("safetyGuardEquipment") val safetyGuardEquipment: ResultStatus,
    @SerializedName("exhaustSystem") val exhaustSystem: ResultStatus,
    @SerializedName("fuelSystem") val fuelSystem: ResultStatus,
    @SerializedName("powerTransmissionSystem") val powerTransmissionSystem: ResultStatus,
    @SerializedName("battery") val battery: ResultStatus,
    @SerializedName("starterMotor") val starterMotor: ResultStatus,
    @SerializedName("wiringInstallation") val wiringInstallation: ResultStatus,
    @SerializedName("turbocharger") val turbocharger: ResultStatus
)

data class VisualHydraulic(
    @SerializedName("pump") val pump: ResultStatus,
    @SerializedName("lines") val lines: ResultStatus,
    @SerializedName("filter") val filter: ResultStatus,
    @SerializedName("tank") val tank: ResultStatus,
    @SerializedName("mainWinchMotor") val mainWinchMotor: ResultStatus,
    @SerializedName("auxiliaryWinchMotor") val auxiliaryWinchMotor: ResultStatus,
    @SerializedName("boomWinchMotor") val boomWinchMotor: ResultStatus,
    @SerializedName("swingMotor") val swingMotor: ResultStatus
)

data class VisualControlValve(
    @SerializedName("reliefValve") val reliefValve: ResultStatus,
    @SerializedName("mainWinchValve") val mainWinchValve: ResultStatus,
    @SerializedName("auxiliaryWinchValve") val auxiliaryWinchValve: ResultStatus,
    @SerializedName("boomWinchValve") val boomWinchValve: ResultStatus,
    @SerializedName("boomMovementValve") val boomMovementValve: ResultStatus,
    @SerializedName("steeringCylinderValve") val steeringCylinderValve: ResultStatus,
    @SerializedName("axleOscillationValve") val axleOscillationValve: ResultStatus,
    @SerializedName("outriggerMovementValve") val outriggerMovementValve: ResultStatus
)

data class VisualHydraulicCylinder(
    @SerializedName("boomMovementCylinder") val boomMovementCylinder: ResultStatus,
    @SerializedName("outriggerCylinder") val outriggerCylinder: ResultStatus,
    @SerializedName("steeringWheelCylinder") val steeringWheelCylinder: ResultStatus,
    @SerializedName("axleOscillationCylinder") val axleOscillationCylinder: ResultStatus,
    @SerializedName("telescopicCylinder") val telescopicCylinder: ResultStatus
)

data class VisualPneumatic(
    @SerializedName("compressor") val compressor: ResultStatus,
    @SerializedName("tankAndSafetyValve") val tankAndSafetyValve: ResultStatus,
    @SerializedName("pressurizedAirLines") val pressurizedAirLines: ResultStatus,
    @SerializedName("airFilter") val airFilter: ResultStatus,
    @SerializedName("controlValve") val controlValve: ResultStatus
)

data class VisualOperatorCabin(
    @SerializedName("safetyLadder") val safetyLadder: ResultStatus,
    @SerializedName("door") val door: ResultStatus,
    @SerializedName("window") val window: ResultStatus,
    @SerializedName("fanAc") val fanAc: ResultStatus,
    @SerializedName("controlLeversButtons") val controlLeversButtons: ResultStatus,
    @SerializedName("pendantControl") val pendantControl: ResultStatus,
    @SerializedName("lighting") val lighting: ResultStatus,
    @SerializedName("hornSignalAlarm") val hornSignalAlarm: ResultStatus,
    @SerializedName("fuse") val fuse: ResultStatus,
    @SerializedName("communicationDevice") val communicationDevice: ResultStatus,
    @SerializedName("fireExtinguisher") val fireExtinguisher: ResultStatus,
    @SerializedName("operatingSigns") val operatingSigns: ResultStatus,
    @SerializedName("ignitionKeyMasterSwitch") val ignitionKeyMasterSwitch: ResultStatus,
    @SerializedName("buttonsHandlesLevers") val buttonsHandlesLevers: ResultStatus
)

data class VisualElectricalComponents(
    @SerializedName("panelConductorConnector") val panelConductorConnector: ResultStatus,
    @SerializedName("conductorProtection") val conductorProtection: ResultStatus,
    @SerializedName("motorInstallationSafetySystem") val motorInstallationSafetySystem: ResultStatus,
    @SerializedName("groundingSystem") val groundingSystem: ResultStatus,
    @SerializedName("installation") val installation: ResultStatus
)

data class VisualSafetyDevices(
    @SerializedName("ladderHandrail") val ladderHandrail: ResultStatus,
    @SerializedName("engineOilLubricantPressure") val engineOilLubricantPressure: ResultStatus,
    @SerializedName("hydraulicOilPressure") val hydraulicOilPressure: ResultStatus,
    @SerializedName("airPressure") val airPressure: ResultStatus,
    @SerializedName("amperemeter") val amperemeter: ResultStatus,
    @SerializedName("voltage") val voltage: ResultStatus,
    @SerializedName("engineTemperature") val engineTemperature: ResultStatus,
    @SerializedName("transmissionTemperature") val transmissionTemperature: ResultStatus,
    @SerializedName("converterOilTemperaturePressure") val converterOilTemperaturePressure: ResultStatus,
    @SerializedName("converterSpeedometerIndicator") val converterSpeedometerIndicator: ResultStatus,
    @SerializedName("converterRotaryLamp") val converterRotaryLamp: ResultStatus,
    @SerializedName("converterMainHoistRopeUpDownLimit") val converterMainHoistRopeUpDownLimit: ResultStatus,
    @SerializedName("converterAuxiliaryHoistRopeUpDownLimit") val converterAuxiliaryHoistRopeUpDownLimit: ResultStatus,
    @SerializedName("converterSwingMotionLimit") val converterSwingMotionLimit: ResultStatus,
    @SerializedName("converterLevelIndicator") val converterLevelIndicator: ResultStatus,
    @SerializedName("converterLoadWeightIndicator") val converterLoadWeightIndicator: ResultStatus,
    @SerializedName("converterLoadChart") val converterLoadChart: ResultStatus,
    @SerializedName("converterAnemometerWindSpeed") val converterAnemometerWindSpeed: ResultStatus,
    @SerializedName("converterBoomAngleIndicator") val converterBoomAngleIndicator: ResultStatus,
    @SerializedName("converterAirPressureIndicator") val converterAirPressureIndicator: ResultStatus,
    @SerializedName("converterHydraulicPressureIndicator") val converterHydraulicPressureIndicator: ResultStatus,
    @SerializedName("converterSafetyValves") val converterSafetyValves: ResultStatus,
    @SerializedName("converterMainWindingDrumSafetyLock") val converterMainWindingDrumSafetyLock: ResultStatus,
    @SerializedName("converterAuxiliaryWindingDrumSafetyLock") val converterAuxiliaryWindingDrumSafetyLock: ResultStatus,
    @SerializedName("converterTelescopicMotionLimit") val converterTelescopicMotionLimit: ResultStatus,
    @SerializedName("converterLightningArrester") val converterLightningArrester: ResultStatus,
    @SerializedName("converterLiftingHeightIndicator") val converterLiftingHeightIndicator: ResultStatus
)

data class NdtSteelWireRope(
    @SerializedName("ndtType") val ndtType: String,
    @SerializedName("ropes") val ropes: List<NdtSteelWireRopeItem>
)

data class NdtSteelWireRopeItem(
    @SerializedName("usageAt") val usageAt: String,
    @SerializedName("specDiameter") val specDiameter: String,
    @SerializedName("actualDiameter") val actualDiameter: String,
    @SerializedName("construction") val construction: String,
    @SerializedName("type") val type: String,
    @SerializedName("length") val length: String,
    @SerializedName("age") val age: String,
    @SerializedName("result") val result: ResultStatus
)

data class NdtBoom(
    @SerializedName("boomType") val boomType: String,
    @SerializedName("ndtType") val ndtType: String,
    @SerializedName("inspections") val inspections: List<NdtBoomInspection>
)

data class NdtBoomInspection(
    @SerializedName("part") val part: String,
    @SerializedName("location") val location: String,
    @SerializedName("result") val result: ResultStatus
)

data class NdtHook(
    @SerializedName("ndtType") val ndtType: String,
    @SerializedName("capacity") val capacity: String,
    @SerializedName("specification") val specification: NdtSpecification,
    @SerializedName("measurementResults") val measurementResults: NdtMeasurement,
    @SerializedName("toleranceMeasure") val toleranceMeasure: NdtMeasurement
)

data class NdtDrum(
    @SerializedName("ndtType") val ndtType: String,
    @SerializedName("capacity") val capacity: String,
    @SerializedName("specification") val specification: NdtSpecification,
    @SerializedName("measurementResults") val measurementResults: NdtMeasurement
)

data class NdtPulley(
    @SerializedName("ndtType") val ndtType: String,
    @SerializedName("capacity") val capacity: String,
    @SerializedName("specification") val specification: NdtSpecification,
    @SerializedName("measurementResults") val measurementResults: NdtMeasurement
)

data class NdtSpecification(
    @SerializedName("a") val a: String,
    @SerializedName("b") val b: String,
    @SerializedName("c") val c: String,
    @SerializedName("d") val d: String,
    @SerializedName("e") val e: String,
    @SerializedName("f") val f: String,
    @SerializedName("g") val g: String,
    @SerializedName("h") val h: String,
    @SerializedName("result") val result: ResultStatus
)

data class NdtMeasurement(
    @SerializedName("a") val a: String,
    @SerializedName("b") val b: String,
    @SerializedName("c") val c: String,
    @SerializedName("d") val d: String,
    @SerializedName("e") val e: String,
    @SerializedName("f") val f: String,
    @SerializedName("g") val g: String,
    @SerializedName("h") val h: String,
    @SerializedName("result") val result: ResultStatus
)

data class TestingFunction(
    @SerializedName("hoistingLowering") val hoistingLowering: ResultStatus,
    @SerializedName("extendedRetractedBoom") val extendedRetractedBoom: ResultStatus,
    @SerializedName("extendedRetractedOutrigger") val extendedRetractedOutrigger: ResultStatus,
    @SerializedName("swingSlewing") val swingSlewing: ResultStatus,
    @SerializedName("antiTwoBlock") val antiTwoBlock: ResultStatus,
    @SerializedName("boomStop") val boomStop: ResultStatus,
    @SerializedName("anemometerWindSpeed") val anemometerWindSpeed: ResultStatus,
    @SerializedName("brakeLockingDevice") val brakeLockingDevice: ResultStatus,
    @SerializedName("loadMomentIndicator") val loadMomentIndicator: ResultStatus,
    @SerializedName("turnSignal") val turnSignal: ResultStatus,
    @SerializedName("drivingLights") val drivingLights: ResultStatus,
    @SerializedName("loadIndicatorLight") val loadIndicatorLight: ResultStatus,
    @SerializedName("rotaryLamp") val rotaryLamp: ResultStatus,
    @SerializedName("horn") val horn: ResultStatus,
    @SerializedName("swingAlarm") val swingAlarm: ResultStatus,
    @SerializedName("reverseAlarm") val reverseAlarm: ResultStatus,
    @SerializedName("overloadAlarm") val overloadAlarm: ResultStatus
)

data class MobileCraneDynamicHookTest(
    @SerializedName("boomLength") val boomLength: String,
    @SerializedName("radius") val radius: String,
    @SerializedName("boomAngle") val boomAngle: String,
    @SerializedName("testLoad") val testLoad: String,
    @SerializedName("safeWorkingLoad") val safeWorkingLoad: String,
    @SerializedName("result") val result: String
)

data class MobileCraneStaticHookTest(
    @SerializedName("boomLength") val boomLength: String,
    @SerializedName("radius") val radius: String,
    @SerializedName("boomAngle") val boomAngle: String,
    @SerializedName("testLoad") val testLoad: String,
    @SerializedName("safeWorkingLoad") val safeWorkingLoad: String,
    @SerializedName("result") val result: String
)