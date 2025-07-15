package com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Mobile Crane inspection report.
 * This class is immutable.
 */
@Immutable
data class MobileCraneUiState(
    val mobileCraneInspectionReport: MobileCraneInspectionReport = MobileCraneInspectionReport()
)

@Immutable
data class MobileCraneInspectionReport(
    val generalData: MobileCraneGeneralData = MobileCraneGeneralData(),
    val technicalData: MobileCraneTechnicalData = MobileCraneTechnicalData(),
    val visualInspection: MobileCraneVisualInspection = MobileCraneVisualInspection(),
    val nonDestructiveExamination: MobileCraneNonDestructiveExamination = MobileCraneNonDestructiveExamination(),
    val testing: MobileCraneTesting = MobileCraneTesting(),
    val conclusion: MobileCraneConclusion = MobileCraneConclusion()
)

@Immutable
data class MobileCraneGeneralData(
    val id: Long = 0,
    val documentType: DocumentType = DocumentType.LAPORAN,
    val inspectionType: InspectionType = InspectionType.EE,
    val subInspectionType: SubInspectionType = SubInspectionType.Escalator,
    val equipmentType: String = "",
    val examinationType: String = "",
    val owner: String = "",
    val address: String = "",
    val personInCharge: String = "",
    val userAddress: String = "",
    val unitLocation: String = "",
    val operatorName: String = "",
    val driveType: String = "",
    val manufacturer: String = "",
    val brandType: String = "",
    val yearOfManufacture: String = "",
    val serialNumber: String = "",
    val liftingCapacity: String = "",
    val intendedUse: String = "",
    val permitNumber: String = "",
    val operatorCertificate: String = "",
    val equipmentHistory: String = ""
)

@Immutable
data class MobileCraneTechnicalData(
    val specifications: MobileCraneSpecifications = MobileCraneSpecifications(),
    val driveMotor: MobileCraneDriveMotor = MobileCraneDriveMotor(),
    val mainHook: MobileCraneHook = MobileCraneHook(),
    val auxiliaryHook: MobileCraneHook = MobileCraneHook(),
    val wireRope: MobileCraneWireRope = MobileCraneWireRope()
)

@Immutable
data class MobileCraneSpecifications(
    val maximumWorkingLoadCapacity: String = "",
    val boomLength: String = "",
    val maximumJibLength: String = "",
    val maximumJibWorkingLoad: String = "",
    val maxBoomJibLength: String = "",
    val craneWeight: String = "",
    val maxLiftingHeight: String = "",
    val boomWorkingAngle: String = ""
)

@Immutable
data class MobileCraneDriveMotor(
    val engineNumber: String = "",
    val type: String = "",
    val numberOfCylinders: String = "",
    val netPower: String = "",
    val brandYearOfManufacture: String = "",
    val manufacturer: String = ""
)

@Immutable
data class MobileCraneHook(
    val type: String = "",
    val capacity: String = " Ton",
    val material: String = "",
    val serialNumber: String = ""
)

@Immutable
data class MobileCraneWireRope(
    val mainLoadHoistDrum: MobileCraneWireRopeDrum = MobileCraneWireRopeDrum(),
    val auxiliaryLoadHoistDrum: MobileCraneWireRopeDrum = MobileCraneWireRopeDrum(),
    val boomHoistDrum: MobileCraneWireRopeDrum = MobileCraneWireRopeDrum()
)

@Immutable
data class MobileCraneWireRopeDrum(
    val diameter: String = " Milimeter",
    val type: String = "",
    val construction: String = "",
    val breakingStrength: String = " kN",
    val length: String = " Meter"
)

@Immutable
data class MobileCraneVisualInspection(
    val foundationAnchorBoltCorrosion: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val foundationAnchorBoltCracks: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val foundationAnchorBoltDeformation: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val foundationAnchorBoltTightness: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val frameColumnsOnFoundationCorrosion: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val frameColumnsOnFoundationCracks: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val frameColumnsOnFoundationDeformation: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val frameColumnsOnFoundationFastening: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val frameColumnsOnFoundationTransverseReinforcement: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val frameColumnsOnFoundationDiagonalReinforcement: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val ladderCorrosion: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val ladderCracks: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val ladderDeformation: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val ladderFastening: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val workingPlatformCorrosion: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val workingPlatformCracks: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val workingPlatformDeformation: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val workingPlatformFastening: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val outriggersOutriggerArmHousing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val outriggersOutriggerArms: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val outriggersJack: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val outriggersOutriggerPads: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val outriggersHousingConnectionToChassis: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val outriggersOutriggerSafetyLocks: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val turntableSlewingRollerBearing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val turntableBrakeHousing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val turntableBrakeLiningsAndShoes: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val turntableDrumSurface: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val turntablePressureCylinder: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val turntableDrumAxle: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val turntableLeversPinsBolts: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val turntableGuard: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val latticeBoomMainBoom: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val latticeBoomBoomSection: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val latticeBoomTopPulley: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val latticeBoomPulleyGuard: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val latticeBoomWireRopeGuard: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val latticeBoomPulleyGrooveLip: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val latticeBoomPivotPin: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val latticeBoomWireRopeGuidePulley: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val clutchMainClutch: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val transmission: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val steeringFrontWheel: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val steeringMiddleWheel: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val steeringRearWheel: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val brakeServiceBrake: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val brakeParkingBrake: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val brakeBrakeHousing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val brakeBrakeLiningsAndShoes: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val brakeDrumSurface: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val brakeLeversPinsBolts: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val brakeGuard: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val travelDrumClutchHousing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val travelDrumClutchLining: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val travelDrumClutchDrumSurface: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val travelDrumLeversPinsBolts: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val travelDrumGuard: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchDrumMounting: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchWindingDrumSurface: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchBrakeLiningsAndShoes: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchBrakeDrumSurface: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchBrakeHousing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchClutchLiningsAndShoes: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchClutchDrumSurface: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchGroove: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchGrooveLip: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchFlanges: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWinchBrakeActuatorLeversPinsAndBolts: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchDrumMounting: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchWindingDrumSurface: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchBrakeLiningsAndShoes: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchBrakeDrumSurface: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchBrakeHousing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchClutchLiningsAndShoes: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchClutchDrumSurface: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchGroove: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchGrooveLip: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchFlanges: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWinchBrakeActuatorLeversPinsAndBolts: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hoistGearBlockLubrication: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hoistGearBlockOilSeal: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainPulleyPulleyGroove: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainPulleyPulleyGrooveLip: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainPulleyPulleyPin: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainPulleyBearing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainPulleyPulleyGuard: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainPulleyWireRopeGuard: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainHookVisualSwivelNutAndBearing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainHookVisualTrunnion: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainHookVisualSafetyLatch: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryHookVisualFreeFallWeight: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryHookVisualSwivelNutAndBearing: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryHookVisualSafetyLatch: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWireRopeVisualCorrosion: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWireRopeVisualWear: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWireRopeVisualBreakage: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val mainWireRopeVisualDeformation: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWireRopeVisualCorrosion: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWireRopeVisualWear: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWireRopeVisualBreakage: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val auxiliaryWireRopeVisualDeformation: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val limitSwitchLsLongTravel: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val limitSwitchLsCrossTravel: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val limitSwitchLsHoisting: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineCoolingSystem: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineLubricationSystem: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineEngineMounting: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineSafetyGuardEquipment: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineExhaustSystem: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineAirIntakeSystem: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineFuelSystem: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEnginePowerTransmissionSystem: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineBattery: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineStarterMotor: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineWiringInstallation: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val internalCombustionEngineTurbocharger: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicHydraulicPump: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicHydraulicLines: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicHydraulicFilter: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicHydraulicTank: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicMotorMainWinchMotor: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicMotorAuxiliaryWinchMotor: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicMotorBoomWinchMotor: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicMotorSwingMotor: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val controlValveReliefValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val controlValveMainWinchValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val controlValveAuxiliaryWinchValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val controlValveBoomWinchValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val controlValveBoomMovementValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val controlValveSteeringCylinderValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val controlValveAxleOscillationValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val controlValveOutriggerMovementValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicCylinderBoomMovementCylinder: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicCylinderOutriggerCylinder: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicCylinderSteeringWheelCylinder: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicCylinderAxleOscillationCylinder: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val hydraulicCylinderTelescopicCylinder: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val pneumaticCompressor: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val pneumaticTankAndSafetyValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val pneumaticPressurizedAirLines: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val pneumaticAirFilter: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val pneumaticControlValve: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinSafetyLadder: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinDoor: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinWindow: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinFanAc: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinControlLeversButtons: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinPendantControl: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinLighting: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinHornSignalAlarm: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinFuse: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinCommunicationDevice: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinFireExtinguisher: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinOperatingSigns: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinIgnitionKeyMasterSwitch: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val operatorCabinButtonsHandlesLevers: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val electricalComponentsPanelConductorConnector: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val electricalComponentsConductorProtection: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val electricalComponentsMotorInstallationSafetySystem: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val electricalComponentsGroundingSystem: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val electricalComponentsInstallation: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesLadderHandrail: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesEngineOilLubricantPressure: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesHydraulicOilPressure: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesAirPressure: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesAmperemeter: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesVoltage: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesEngineTemperature: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesTransmissionTemperature: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesConverterOilTemperaturePressure: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesSpeedometerIndicator: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesRotaryLamp: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesMainHoistRopeUpDownLimit: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesAuxiliaryHoistRopeUpDownLimit: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesSwingMotionLimit: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesLevelIndicator: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesLoadWeightIndicator: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesLoadChart: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesAnemometerWindSpeed: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesBoomAngleIndicator: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesAirPressureIndicator: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesHydraulicPressureIndicator: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesSafetyValves: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesMainWindingDrumSafetyLock: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesAuxiliaryWindingDrumSafetyLock: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesTelescopicMotionLimit: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesLightningArrester: MobileCraneInspectionResult = MobileCraneInspectionResult(),
    val safetyDevicesLiftingHeightIndicator: MobileCraneInspectionResult = MobileCraneInspectionResult()
)

@Immutable
data class MobileCraneInspectionResult(
    val status: Boolean = false,
    val result: String = ""
)

@Immutable
data class MobileCraneNonDestructiveExamination(
    val wireRope: MobileCraneNdeWireRopeSection = MobileCraneNdeWireRopeSection(),
    val boom: MobileCraneNdeBoomSection = MobileCraneNdeBoomSection(),
    val mainHook: MobileCraneNdeHookSection = MobileCraneNdeHookSection(),
    val auxiliaryHook: MobileCraneNdeHookSection = MobileCraneNdeHookSection(),
    val mainDrum: MobileCraneNdeDrumSection = MobileCraneNdeDrumSection(),
    val auxiliaryDrum: MobileCraneNdeDrumSection = MobileCraneNdeDrumSection(),
    val mainPulley: MobileCraneNdePulleySection = MobileCraneNdePulleySection(),
    val auxiliaryPulley: MobileCraneNdePulleySection = MobileCraneNdePulleySection()
)

@Immutable
data class MobileCraneNdeWireRopeSection(
    val ndtType: String = "",
    val items: ImmutableList<MobileCraneNdeWireRopeItem> = persistentListOf()
)

@Immutable
data class MobileCraneNdeWireRopeItem(
    val usage: String = "",
    val specDiameter: String = "",
    val actualDiameter: String = "",
    val construction: String = "",
    val type: String = "",
    val length: String = "",
    val age: String = "",
    val hasDefect: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class MobileCraneNdeBoomSection(
    val boomType: String = "",
    val ndtType: String = "",
    val items: ImmutableList<MobileCraneNdeBoomItem> = persistentListOf()
)

@Immutable
data class MobileCraneNdeBoomItem(
    val partInspected: String = "",
    val location: String = "",
    val hasDefect: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class MobileCraneNdeHookSection(
    val hookCapacity: String = " T",
    val ndtType: String = "",
    val specification: MobileCraneNdeMeasurement = MobileCraneNdeMeasurement(),
    val measurementResult: MobileCraneNdeMeasurement = MobileCraneNdeMeasurement(),
    val tolerance: MobileCraneNdeMeasurement = MobileCraneNdeMeasurement()
)

@Immutable
data class MobileCraneNdeDrumSection(
    val ndtType: String = "",
    val specification: MobileCraneNdeMeasurement = MobileCraneNdeMeasurement(),
    val measurementResult: MobileCraneNdeMeasurement = MobileCraneNdeMeasurement()
)

@Immutable
data class MobileCraneNdePulleySection(
    val ndtType: String = "",
    val specification: MobileCraneNdeMeasurement = MobileCraneNdeMeasurement(),
    val measurementResult: MobileCraneNdeMeasurement = MobileCraneNdeMeasurement()
)

@Immutable
data class MobileCraneNdeMeasurement(
    val a: String = "", val b: String = "", val c: String = "", val d: String = "",
    val e: String = "", val f: String = "", val g: String = "", val h: String = "",
    val finding: MobileCraneInspectionResult = MobileCraneInspectionResult()
)

@Immutable
data class MobileCraneTesting(
    val functionTest: MobileCraneFunctionTest = MobileCraneFunctionTest(),
    val loadTest: MobileCraneLoadTest = MobileCraneLoadTest()
)

@Immutable
data class MobileCraneFunctionTest(
    val hoistingLowering: MobileCraneTestResult = MobileCraneTestResult(),
    val extendedRetractedBoom: MobileCraneTestResult = MobileCraneTestResult(),
    val extendedRetractedOutrigger: MobileCraneTestResult = MobileCraneTestResult(),
    val swingSlewing: MobileCraneTestResult = MobileCraneTestResult(),
    val antiTwoBlock: MobileCraneTestResult = MobileCraneTestResult(),
    val boomStop: MobileCraneTestResult = MobileCraneTestResult(),
    val anemometerWindSpeed: MobileCraneTestResult = MobileCraneTestResult(),
    val brakeLockingDevice: MobileCraneTestResult = MobileCraneTestResult(),
    val loadMomentIndicator: MobileCraneTestResult = MobileCraneTestResult(),
    val turnSignal: MobileCraneTestResult = MobileCraneTestResult(),
    val drivingLights: MobileCraneTestResult = MobileCraneTestResult(),
    val loadIndicatorLight: MobileCraneTestResult = MobileCraneTestResult(),
    val rotaryLamp: MobileCraneTestResult = MobileCraneTestResult(),
    val horn: MobileCraneTestResult = MobileCraneTestResult(),
    val swingAlarm: MobileCraneTestResult = MobileCraneTestResult(),
    val reverseAlarm: MobileCraneTestResult = MobileCraneTestResult(),
    val overloadAlarm: MobileCraneTestResult = MobileCraneTestResult()
)

@Immutable
data class MobileCraneTestResult(
    val status: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class MobileCraneLoadTest(
    val dynamic: MobileCraneLoadTestType = MobileCraneLoadTestType(),
    val static: MobileCraneLoadTestType = MobileCraneLoadTestType()
)

@Immutable
data class MobileCraneLoadTestType(
    val mainHook: ImmutableList<MobileCraneLoadTestItem> = persistentListOf(),
    val auxiliaryHook: ImmutableList<MobileCraneLoadTestItem> = persistentListOf()
)

@Immutable
data class MobileCraneLoadTestItem(
    val boomLength: String = "",
    val radius: String = "",
    val boomAngle: String = "",
    val testLoadKg: String = "",
    val safeWorkingLoadKg: String = "",
    val result: String = ""
)

@Immutable
data class MobileCraneConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val recommendations: ImmutableList<String> = persistentListOf()
)
