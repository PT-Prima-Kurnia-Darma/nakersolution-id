package com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Overhead Crane inspection report.
 * This class is immutable.
 */
@Immutable
data class OverheadCraneUiState(
    val overheadCraneInspectionReport: OverheadCraneInspectionReport = OverheadCraneInspectionReport()
)

@Immutable
data class OverheadCraneInspectionReport(
    val generalData: OverheadCraneGeneralData = OverheadCraneGeneralData(),
    val technicalData: OverheadCraneTechnicalData = OverheadCraneTechnicalData(),
    val visualInspection: OverheadCraneVisualInspection = OverheadCraneVisualInspection(),
    val nonDestructiveExamination: OverheadCraneNde = OverheadCraneNde(),
    val testing: OverheadCraneTesting = OverheadCraneTesting(),
    val conclusion: OverheadCraneConclusion = OverheadCraneConclusion()
)

@Immutable
data class OverheadCraneGeneralData(
    val owner: String = "",
    val address: String = "",
    val user: String = "",
    val personInCharge: String = "",
    val unitLocation: String = "",
    val hoistType: String = "",
    val manufacturer: String = "",
    val brandType: String = "",
    val yearOfManufacture: String = "",
    val serialNumber: String = "",
    val liftingCapacity: String = "",
    val intendedUse: String = "",
    val permitNumber: String = "",
    val operatorCertificate: String = "",
    val technicalDataManual: String = ""
)

@Immutable
data class OverheadCraneTechnicalData(
    val specifications: OverheadCraneTechSpecs = OverheadCraneTechSpecs(),
    val driveMotor: OverheadCraneDriveMotor = OverheadCraneDriveMotor(),
    val startingResistor: OverheadCraneStartingResistor = OverheadCraneStartingResistor(),
    val brake: OverheadCraneBrake = OverheadCraneBrake(),
    val controllerBrake: OverheadCraneControllerBrake = OverheadCraneControllerBrake(),
    val hook: OverheadCraneHook = OverheadCraneHook(),
    val chain: OverheadCraneChain = OverheadCraneChain(),
    val wireRope: OverheadCraneWireRope = OverheadCraneWireRope()
)

@Immutable
data class OverheadCraneMovementData(
    val hoisting: String = "",
    val traveling: String = "",
    val traversing: String = ""
)

@Immutable
data class OverheadCraneTechSpecs(
    val liftingHeight: OverheadCraneMovementData = OverheadCraneMovementData(),
    val girderLength: OverheadCraneMovementData = OverheadCraneMovementData(),
    val speed: OverheadCraneMovementData = OverheadCraneMovementData()
)

@Immutable
data class OverheadCraneDriveMotor(
    val capacity: OverheadCraneMovementData = OverheadCraneMovementData(),
    val powerKw: OverheadCraneMovementData = OverheadCraneMovementData(),
    val type: OverheadCraneMovementData = OverheadCraneMovementData(),
    val rpm: OverheadCraneMovementData = OverheadCraneMovementData(),
    val voltageV: OverheadCraneMovementData = OverheadCraneMovementData(),
    val currentA: OverheadCraneMovementData = OverheadCraneMovementData(),
    val frequencyHz: OverheadCraneMovementData = OverheadCraneMovementData()
)

@Immutable
data class OverheadCraneStartingResistor(
    val type: OverheadCraneMovementData = OverheadCraneMovementData(),
    val voltageV: OverheadCraneMovementData = OverheadCraneMovementData(),
    val currentA: OverheadCraneMovementData = OverheadCraneMovementData()
)

@Immutable
data class OverheadCraneBrake(
    val type: OverheadCraneMovementData = OverheadCraneMovementData(),
    val model: OverheadCraneMovementData = OverheadCraneMovementData()
)

@Immutable
data class OverheadCraneControllerBrake(
    val type: OverheadCraneMovementData = OverheadCraneMovementData(),
    val model: OverheadCraneMovementData = OverheadCraneMovementData()
)

@Immutable
data class OverheadCraneHook(
    val type: OverheadCraneMovementData = OverheadCraneMovementData(),
    val capacity: OverheadCraneMovementData = OverheadCraneMovementData(),
    val material: OverheadCraneMovementData = OverheadCraneMovementData()
)

@Immutable
data class OverheadCraneChain(
    val type: OverheadCraneMovementData = OverheadCraneMovementData(),
    val construction: OverheadCraneMovementData = OverheadCraneMovementData(),
    val diameter: OverheadCraneMovementData = OverheadCraneMovementData(),
    val length: OverheadCraneMovementData = OverheadCraneMovementData()
)

@Immutable
data class OverheadCraneWireRope(
    val type: OverheadCraneMovementData = OverheadCraneMovementData(),
    val construction: OverheadCraneMovementData = OverheadCraneMovementData(),
    val diameter: OverheadCraneMovementData = OverheadCraneMovementData(),
    val length: OverheadCraneMovementData = OverheadCraneMovementData()
)

@Immutable
data class OverheadCraneInspectionResult(
    val status: Boolean? = false,
    val result: String = ""
)

@Immutable
data class OverheadCraneVisualInspection(
    val foundationAnchorBoltCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val foundationAnchorBoltCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val foundationAnchorBoltDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val foundationAnchorBoltFastening: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val columnFrameCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val columnFrameCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val columnFrameDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val columnFrameFastening: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val columnFrameCrossBracing: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val columnFrameDiagonalBracing: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val ladderCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val ladderCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val ladderDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val ladderFastening: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val workPlatformCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val workPlatformCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val workPlatformDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val workPlatformFastening: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val railMountingBeamCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val railMountingBeamCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val railMountingBeamDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val railMountingBeamFastening: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingRailCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingRailCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingRailJoint: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingRailStraightness: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingRailAlignmentBetweenRails: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingRailEvennessBetweenRails: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingRailGapBetweenRailJoints: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingRailFastener: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingRailStopper: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingRailCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingRailCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingRailJoint: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingRailStraightness: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingRailAlignmentBetweenRails: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingRailEvennessBetweenRails: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingRailGapBetweenRailJoints: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingRailFastener: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingRailStopper: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val girderCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val girderCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val girderCamber: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val girderJoint: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val girderEndJoint: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val girderTruckMountOnGirder: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingGearboxGirderCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingGearboxGirderCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingGearboxGirderLubricatingOil: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val travelingGearboxGirderOilSeal: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelFlangeCondition: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelChainCondition: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val idleWheelSecurity: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val idleWheelCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val idleWheelDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val idleWheelFlangeCondition: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val wheelConnectorBogieAxleStraightness: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val wheelConnectorBogieAxleCrossJoint: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val wheelConnectorBogieAxleLubrication: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val stopperBumperOnGirderCondition: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val stopperBumperOnGirderReinforcement: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingGearboxTrolleyCarrierFastening: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingGearboxTrolleyCarrierCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingGearboxTrolleyCarrierCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingGearboxTrolleyCarrierLubricatingOil: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val traversingGearboxTrolleyCarrierOilSeal: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelOnTrolleyWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelOnTrolleyCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelOnTrolleyDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelOnTrolleyFlangeCondition: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val driveWheelOnTrolleyChainCondition: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val idleWheelOnTrolleyWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val idleWheelOnTrolleyCracks: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val idleWheelOnTrolleyDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val idleWheelOnTrolleyFlangeCondition: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val windingDrumGroove: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val windingDrumGrooveFlange: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val windingDrumFlanges: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val brakeWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val brakeAdjustment: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val hoistGearboxLubrication: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val hoistGearboxOilSeal: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val pulleyDiscChainSprocketPulleyGroove: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val pulleyDiscChainSprocketPulleyFlange: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val pulleyDiscChainSprocketPulleyPin: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val pulleyDiscChainSprocketBearing: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val pulleyDiscChainSprocketPulleyGuard: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val pulleyDiscChainSprocketWireRopeChainGuard: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainHookWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainHookThroatOpening: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainHookSwivelNutAndBearing: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainHookTrunnion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryHookWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryHookThroatOpening: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryHookSwivelNutAndBearing: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryHookTrunnion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainWireRopeCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainWireRopeWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainWireRopeBroken: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainWireRopeDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryWireRopeCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryWireRopeWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryWireRopeBroken: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryWireRopeDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainChainCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainChainWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainChainCrackedBroken: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val mainChainDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryChainCorrosion: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryChainWear: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryChainCrackedBroken: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val auxiliaryChainDeformation: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val limitSwitchLsLongTraveling: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val limitSwitchLsCrossTraveling: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val limitSwitchLsHoisting: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinSafetyLadder: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinDoor: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinWindow: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinFanAC: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinControlLeversButtons: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinPendantControl: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinLighting: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinHorn: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinFuseProtection: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinCommunicationDevice: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinFireExtinguisher: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinOperatingSigns: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val operatorRoomCabinIgnitionKeyMasterSwitch: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val electricalComponentPanelConnectorConductor: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val electricalComponentConductorProtection: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val electricalComponentMotorInstallationSafetySystem: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val electricalComponentGroundingSystem: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val electricalComponentInstallation: OverheadCraneInspectionResult = OverheadCraneInspectionResult()
)

@Immutable
data class OverheadCraneNde(
    val chain: OverheadCraneNdeChain = OverheadCraneNdeChain(),
    val mainHook: OverheadCraneNdeHook = OverheadCraneNdeHook()
)

@Immutable
data class OverheadCraneNdeChain(
    val method: String = "",
    val items: ImmutableList<OverheadCraneNdeChainItem> = persistentListOf()
)

@Immutable
data class OverheadCraneNdeChainItem(
    val name: String = "",
    val specificationMm: String = "",
    val measurementMm: String = "",
    val lengthIncrease: String = "",
    val wear: String = "",
    val safetyFactor: String = "",
    val finding: OverheadCraneInspectionResult = OverheadCraneInspectionResult() // MODIFIED
)

@Immutable
data class OverheadCraneNdeHook(
    val method: String = "",
    val specification: OverheadCraneNdeMeasurement = OverheadCraneNdeMeasurement(),
    val measurementResult: OverheadCraneNdeMeasurement = OverheadCraneNdeMeasurement(),
    val tolerance: OverheadCraneNdeMeasurement = OverheadCraneNdeMeasurement()
)

@Immutable
data class OverheadCraneNdeMeasurement(
    val a: String = "", val b: String = "", val c: String = "", val d: String = "",
    val e: String = "", val f: String = "", val g: String = "", val h: String = "",
    val finding: OverheadCraneInspectionResult = OverheadCraneInspectionResult()
)

@Immutable
data class OverheadCraneTesting(
    val dynamicTest: OverheadCraneDynamicTest = OverheadCraneDynamicTest(),
    val staticTest: OverheadCraneStaticTest = OverheadCraneStaticTest()
)

@Immutable
data class OverheadCraneDynamicTest(
    val withoutLoad: OverheadCraneDynamicWithoutLoad = OverheadCraneDynamicWithoutLoad(),
    val withLoad: OverheadCraneDynamicWithLoad = OverheadCraneDynamicWithLoad()
)

@Immutable
data class OverheadCraneDynamicWithoutLoad(
    val traveling: OverheadCraneDynamicTestItem = OverheadCraneDynamicTestItem(),
    val traversing: OverheadCraneDynamicTestItem = OverheadCraneDynamicTestItem(),
    val hoisting: OverheadCraneDynamicTestItem = OverheadCraneDynamicTestItem(),
    val safetyLatch: OverheadCraneDynamicTestItem = OverheadCraneDynamicTestItem(),
    val brakeSwitch: OverheadCraneDynamicTestItem = OverheadCraneDynamicTestItem(),
    val brakeLockingDevice: OverheadCraneDynamicTestItem = OverheadCraneDynamicTestItem(),
    val electricalInstallation: OverheadCraneDynamicTestItem = OverheadCraneDynamicTestItem()
)

@Immutable
data class OverheadCraneDynamicTestItem(
    val shouldBe: String = "",
    val testedMeasured: String = "",
    val remarks: String = ""
)

@Immutable
data class OverheadCraneDynamicWithLoad(
    val noLoad: OverheadCraneLoadTestResult = OverheadCraneLoadTestResult(),
    val swl25: OverheadCraneLoadTestResult = OverheadCraneLoadTestResult(),
    val swl50: OverheadCraneLoadTestResult = OverheadCraneLoadTestResult(),
    val swl75: OverheadCraneLoadTestResult = OverheadCraneLoadTestResult(),
    val swl100: OverheadCraneLoadTestResult = OverheadCraneLoadTestResult()
)

@Immutable
data class OverheadCraneLoadTestResult(
    val hoist: String = "",
    val traversing: String = "",
    val travelling: String = "",
    val brakeSystem: String = "",
    val remarks: String = ""
)

@Immutable
data class OverheadCraneStaticTest(
    val load: String = "",
    val deflectionMeasurement: OverheadCraneDeflectionMeasurement = OverheadCraneDeflectionMeasurement(),
    val notes: String = ""
)

@Immutable
data class OverheadCraneDeflectionMeasurement(
    val singleGirder: Map<String, String> = emptyMap(),
    val doubleGirder: Map<String, String> = emptyMap()
)

@Immutable
data class OverheadCraneConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val recommendations: ImmutableList<String> = persistentListOf()
)