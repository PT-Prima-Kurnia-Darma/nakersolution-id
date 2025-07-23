package com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Gantry Crane inspection report.
 * This class is immutable.
 */
@Immutable
data class GantryCraneUiState(
    val gantryCraneInspectionReport: GantryCraneInspectionReport = GantryCraneInspectionReport()
)

@Immutable
data class GantryCraneInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val generalData: GantryCraneGeneralData = GantryCraneGeneralData(),
    val technicalData: GantryCraneTechnicalData = GantryCraneTechnicalData(),
    val visualInspection: GantryCraneVisualInspection = GantryCraneVisualInspection(),
    val nonDestructiveExamination: GantryCraneNde = GantryCraneNde(),
    val testing: GantryCraneTesting = GantryCraneTesting(),
    val conclusion: GantryCraneConclusion = GantryCraneConclusion()
)

@Immutable
data class GantryCraneGeneralData(
    val owner: String = "",
    val address: String = "",
    val user: String = "",
    val personInCharge: String = "",
    val unitLocation: String = "",
    val driveType: String = "",
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
data class GantryCraneTechnicalData(
    val specifications: GantryCraneTechSpecs = GantryCraneTechSpecs(),
    val driveMotor: GantryCraneDriveMotor = GantryCraneDriveMotor(),
    val startingResistor: GantryCraneStartingResistor = GantryCraneStartingResistor(), // ADDED
    val brake: GantryCraneBrake = GantryCraneBrake(),
    val controllerBrake: GantryCraneControllerBrake = GantryCraneControllerBrake(),
    val hook: GantryCraneHook = GantryCraneHook(),
    val wireRope: GantryCraneWireRope = GantryCraneWireRope(),
    val chain: GantryCraneChain = GantryCraneChain()
)

@Immutable
data class GantryCraneMovementData(
    val hoisting: String = "",
    val traveling: String = "",
    val traversing: String = ""
)

@Immutable
data class GantryCraneTechSpecs(
    val liftingHeight: GantryCraneMovementData = GantryCraneMovementData(),
    val girderLength: GantryCraneMovementData = GantryCraneMovementData(),
    val speed: GantryCraneMovementData = GantryCraneMovementData()
)

@Immutable
data class GantryCraneDriveMotor(
    val capacity: GantryCraneMovementData = GantryCraneMovementData(),
    val powerKw: GantryCraneMovementData = GantryCraneMovementData(),
    val type: GantryCraneMovementData = GantryCraneMovementData(),
    val rpm: GantryCraneMovementData = GantryCraneMovementData(),
    val voltageV: GantryCraneMovementData = GantryCraneMovementData(),
    val currentA: GantryCraneMovementData = GantryCraneMovementData(),
    val frequencyHz: GantryCraneMovementData = GantryCraneMovementData(),
    val phase: GantryCraneMovementData = GantryCraneMovementData(), // ADDED
    val powerSupply: GantryCraneMovementData = GantryCraneMovementData() // ADDED
)

// ADDED a new data class for Starting Resistor
@Immutable
data class GantryCraneStartingResistor(
    val type: GantryCraneMovementData = GantryCraneMovementData(),
    val voltageV: GantryCraneMovementData = GantryCraneMovementData(),
    val currentA: GantryCraneMovementData = GantryCraneMovementData()
)

@Immutable
data class GantryCraneBrake(
    val type: GantryCraneMovementData = GantryCraneMovementData(), // This is 'Jenis' in the report
    val model: GantryCraneMovementData = GantryCraneMovementData() // This is 'Type' in the report
)

@Immutable
data class GantryCraneControllerBrake(
    val type: GantryCraneMovementData = GantryCraneMovementData(), // This is 'Jenis' in the report
    val model: GantryCraneMovementData = GantryCraneMovementData() // This is 'Type' in the report
)

@Immutable
data class GantryCraneHook(
    val type: GantryCraneMovementData = GantryCraneMovementData(),
    val capacity: GantryCraneMovementData = GantryCraneMovementData(),
    val material: GantryCraneMovementData = GantryCraneMovementData()
)

@Immutable
data class GantryCraneWireRope(
    val type: GantryCraneMovementData = GantryCraneMovementData(),
    val construction: GantryCraneMovementData = GantryCraneMovementData(),
    val diameter: GantryCraneMovementData = GantryCraneMovementData(),
    val length: GantryCraneMovementData = GantryCraneMovementData()
)

@Immutable
data class GantryCraneChain(
    val type: GantryCraneMovementData = GantryCraneMovementData(),
    val construction: GantryCraneMovementData = GantryCraneMovementData(),
    val diameter: GantryCraneMovementData = GantryCraneMovementData(),
    val length: GantryCraneMovementData = GantryCraneMovementData()
)

@Immutable
data class GantryCraneInspectionResult(
    val status: Boolean = false,
    val result: String = ""
)

@Immutable
data class GantryCraneVisualInspection(
    // Pondasi & Rangka
    val foundationAnchorBoltCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val foundationAnchorBoltCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val foundationAnchorBoltDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val foundationAnchorBoltTightness: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val columnFrameCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val columnFrameCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val columnFrameDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val columnFrameFastening: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val columnFrameCrossBracing: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val columnFrameDiagonalBracing: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Akses
    val ladderCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val ladderCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val ladderDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val ladderFastening: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val workPlatformCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val workPlatformCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val workPlatformDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val workPlatformFastening: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Rel Travelling
    val railMountingBeamCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val railMountingBeamCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val railMountingBeamDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val railMountingBeamFastening: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingRailCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingRailCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingRailJoint: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingRailStraightness: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingRailAlignmentBetweenRails: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingRailEvennessBetweenRails: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingRailGapBetweenRailJoints: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingRailFastener: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingRailStopper: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Rel Traversing
    val traversingRailCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingRailCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingRailJoint: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingRailStraightness: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingRailAlignmentBetweenRails: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingRailEvennessBetweenRails: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingRailGapBetweenRailJoints: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingRailFastener: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingRailStopper: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Girder
    val girderCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val girderCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val girderCamber: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val girderJoint: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val girderEndJoint: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val girderTruckMountOnGirder: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Travelling Gearbox
    val travelingGearboxGirderCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingGearboxGirderCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingGearboxGirderLubricatingOil: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val travelingGearboxGirderOilSeal: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Roda Penggerak
    val driveWheelWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val driveWheelCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val driveWheelDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val driveWheelFlangeCondition: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val driveWheelChainCondition: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Roda Idle
    val idleWheelSecurity: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val idleWheelCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val idleWheelDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val idleWheelFlangeCondition: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Penghubung Roda
    val wheelConnectorBogieAxleStraightness: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val wheelConnectorBogieAxleCrossJoint: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val wheelConnectorBogieAxleLubrication: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val stopperBumperOnGirderCondition: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val stopperBumperOnGirderReinforcement: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Traversing Gearbox
    val traversingGearboxTrolleyCarrierFastening: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingGearboxTrolleyCarrierCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingGearboxTrolleyCarrierCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingGearboxTrolleyCarrierLubricatingOil: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val traversingGearboxTrolleyCarrierOilSeal: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Roda Penggerak Trolley
    val driveWheelOnTrolleyWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val driveWheelOnTrolleyCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val driveWheelOnTrolleyDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val driveWheelOnTrolleyFlangeCondition: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val driveWheelOnTrolleyChainCondition: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Roda Idle Trolley
    val idleWheelOnTrolleyWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val idleWheelOnTrolleyCracks: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val idleWheelOnTrolleyDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val idleWheelOnTrolleyFlangeCondition: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Hoist
    val windingDrumGroove: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val windingDrumGrooveFlange: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val windingDrumFlanges: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val brakeWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val brakeAdjustment: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val hoistGearboxLubrication: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val hoistGearboxOilSeal: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Pully
    val pulleyDiscChainSprocketPulleyGroove: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val pulleyDiscChainSprocketPulleyFlange: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val pulleyDiscChainSprocketPulleyPin: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val pulleyDiscChainSprocketBearing: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val pulleyDiscChainSprocketPulleyGuard: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val pulleyDiscChainSprocketWireRopeChainGuard: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Kait
    val mainHookWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val mainHookThroatOpening: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val mainHookSwivelNutAndBearing: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val mainHookTrunnion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryHookWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryHookThroatOpening: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryHookSwivelNutAndBearing: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryHookTrunnion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Tali Baja
    val mainWireRopeCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val mainWireRopeWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val mainWireRopeBroken: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val mainWireRopeDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryWireRopeCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryWireRopeWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryWireRopeBroken: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryWireRopeDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Rantai
    val mainChainCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val mainChainWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val mainChainCrackedBroken: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val mainChainDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryChainCorrosion: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryChainWear: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryChainCrackedBroken: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val auxiliaryChainDeformation: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Limit Switch
    val limitSwitchLsLongTraveling: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val limitSwitchLsCrossTraveling: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val limitSwitchLsHoisting: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Kabin
    val operatorCabinSafetyLadder: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinDoor: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinWindow: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinFanAc: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinControlLeversButtons: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinPendantControl: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinLighting: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinHorn: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinFuse: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinCommunicationDevice: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinFireExtinguisher: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinOperatingSigns: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val operatorCabinIgnitionKeyMasterSwitch: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    // Kelistrikan
    val electricalComponentsPanelConductorConnector: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val electricalComponentsConductorProtection: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val electricalComponentsMotorInstallationSafetySystem: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val electricalComponentsGroundingSystem: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val electricalComponentsInstallation: GantryCraneInspectionResult = GantryCraneInspectionResult()
)

@Immutable
data class GantryCraneNde(
    val wireRope: GantryCraneNdeWireRope = GantryCraneNdeWireRope(),
    val mainHook: GantryCraneNdeHook = GantryCraneNdeHook(),
    val girder: GantryCraneNdeGirder = GantryCraneNdeGirder()
)

@Immutable
data class GantryCraneNdeWireRope(
    val method: String = "",
    val items: ImmutableList<GantryCraneNdeWireRopeItem> = persistentListOf()
)

@Immutable
data class GantryCraneNdeWireRopeItem(
    val usage: String = "",
    val specDiameter: String = "",
    val actualDiameter: String = "",
    val construction: String = "",
    val type: String = "", // 'Jenis' in report
    val length: String = "",
    val age: String = "", // ADDED
    val finding: GantryCraneInspectionResult = GantryCraneInspectionResult()
)

@Immutable
data class GantryCraneNdeHook(
    val method: String = "",
    val specification: GantryCraneNdeMeasurement = GantryCraneNdeMeasurement(),
    val measurementResult: GantryCraneNdeMeasurement = GantryCraneNdeMeasurement(),
    val tolerance: GantryCraneNdeMeasurement = GantryCraneNdeMeasurement()
)

@Immutable
data class GantryCraneNdeMeasurement(
    val a: String = "", val b: String = "", val c: String = "", val d: String = "",
    val e: String = "", val f: String = "", val g: String = "", val h: String = "",
    val finding: GantryCraneInspectionResult = GantryCraneInspectionResult()
)

@Immutable
data class GantryCraneNdeGirder(
    val method: String = "",
    val items: ImmutableList<GantryCraneNdeGirderItem> = persistentListOf()
)

@Immutable
data class GantryCraneNdeGirderItem(
    val location: String = "",
    val finding: GantryCraneInspectionResult = GantryCraneInspectionResult()
)

@Immutable
data class GantryCraneTesting(
    val dynamicTest: GantryCraneDynamicTest = GantryCraneDynamicTest(),
    val staticTest: GantryCraneStaticTest = GantryCraneStaticTest()
)

@Immutable
data class GantryCraneDynamicTest(
    val withoutLoad: GantryCraneDynamicWithoutLoad = GantryCraneDynamicWithoutLoad(),
    val withLoad: GantryCraneDynamicWithLoad = GantryCraneDynamicWithLoad()
)

@Immutable
data class GantryCraneDynamicWithoutLoad(
    val traveling: GantryCraneDynamicTestItem = GantryCraneDynamicTestItem(),
    val traversing: GantryCraneDynamicTestItem = GantryCraneDynamicTestItem(),
    val hoisting: GantryCraneDynamicTestItem = GantryCraneDynamicTestItem(),
    val safetyDevice: GantryCraneDynamicTestItem = GantryCraneDynamicTestItem(),
    val brakeSwitch: GantryCraneDynamicTestItem = GantryCraneDynamicTestItem(),
    val brakeLockingDevice: GantryCraneDynamicTestItem = GantryCraneDynamicTestItem(),
    val electricalInstallation: GantryCraneDynamicTestItem = GantryCraneDynamicTestItem()
)

@Immutable
data class GantryCraneDynamicTestItem(
    val shouldBe: String = "",
    val testedMeasured: String = "",
    val remarks: String = ""
)

@Immutable
data class GantryCraneDynamicWithLoad(
    val noLoad: GantryCraneLoadTestResult = GantryCraneLoadTestResult(),
    val swl25: GantryCraneLoadTestResult = GantryCraneLoadTestResult(),    // ADDED
    val swl50: GantryCraneLoadTestResult = GantryCraneLoadTestResult(),    // ADDED
    val swl75: GantryCraneLoadTestResult = GantryCraneLoadTestResult(),    // ADDED
    val swl100: GantryCraneLoadTestResult = GantryCraneLoadTestResult()
)

@Immutable
data class GantryCraneLoadTestResult(
    val hoist: String = "",
    val traversing: String = "",
    val travelling: String = "",
    val brakeSystem: String = "",
    val remarks: String = ""
)

@Immutable
data class GantryCraneStaticTest(
    val load: String = "",
    val deflectionResult: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val deflectionStandard: GantryCraneDeflectionStandard = GantryCraneDeflectionStandard(),
    val deflectionMeasurement: ImmutableList<GantryCraneDeflectionItem> = persistentListOf()
)

@Immutable
data class GantryCraneDeflectionStandard(
    val designBased: String = " mm",
    val spanLength: String = " mm",
    val calculation: String = " mm"
)

@Immutable
data class GantryCraneDeflectionItem(
    val position: String = "",
    val deflection: String = "",
    val maxStandard: String = "",
    val remarks: String = ""
)

@Immutable
data class GantryCraneConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val recommendations: ImmutableList<String> = persistentListOf(),
    val nextInspectionDateSuggestion: String = ""
)