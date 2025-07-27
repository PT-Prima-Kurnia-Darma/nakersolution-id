package com.nakersolutionid.nakersolutionid.features.report.paa.gondola

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the state for the UI, containing loading status
 * and the main inspection report data. This class is immutable.
 */
@Immutable
data class GondolaUiState(
    val gondolaInspectionReport: GondolaInspectionReport = GondolaInspectionReport()
)

@Immutable
data class GondolaInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val generalData: GondolaGeneralData = GondolaGeneralData(),
    val technicalData: GondolaTechnicalData = GondolaTechnicalData(),
    val visualInspection: GondolaVisualInspection = GondolaVisualInspection(),
    val nonDestructiveTesting: GondolaNonDestructiveTesting = GondolaNonDestructiveTesting(),
    val testing: GondolaTesting = GondolaTesting(),
    val conclusion: GondolaConclusion = GondolaConclusion()
)

@Immutable
data class GondolaGeneralData(
    val owner: String = "",
    val ownerAddress: String = "",
    val user: String = "",
    val personInCharge: String? = "",
    val unitLocation: String = "",
    val operatorName: String? = "",
    val manufacturer: String = "",
    val brandType: String = "",
    val driveType: String = "",
    val yearOfManufacture: String = "",
    val serialNumberUnitNumber: String = "",
    val capacity: String = "",
    val standardUsed: String = "",
    val usedFor: String = "",
    val usagePermitNumber: String = "",
    val operatorCertificate: String = "",
    val inspectionDate: String = ""
)

@Immutable
data class GondolaTechnicalData(
    val gondolaSpecifications: GondolaSpecifications = GondolaSpecifications(),
    val hoist: GondolaHoist = GondolaHoist(),
    val safetyLockType: String = "",
    val brake: GondolaBrake = GondolaBrake(),
    val mechanicalSuspension: GondolaMechanicalSuspension = GondolaMechanicalSuspension(),
    val engineWeight: GondolaEngineWeight = GondolaEngineWeight()
)

@Immutable
data class GondolaSpecifications(
    val supportPoleHeight: String = "",
    val beam: GondolaBeam = GondolaBeam(),
    val balanceWeightToBeamDistance: String = "",
    val capacity: String = "",
    val speed: String = "",
    val platformSize: GondolaPlatformSize = GondolaPlatformSize(),
    val wireRope: String = ""
)

@Immutable
data class GondolaBeam(
    val frontBeamLength: String = "",
    val middleBeamLength: String = "",
    val rearBeamLength: String = ""
)

@Immutable
data class GondolaPlatformSize(
    val length: String = "",
    val width: String = "",
    val height: String = ""
)

@Immutable
data class GondolaHoist(
    val model: String = "",
    val liftingPower: String = "",
    val electricMotor: GondolaElectricMotor = GondolaElectricMotor()
)

@Immutable
data class GondolaElectricMotor(
    val type: String = "",
    val power: String = "",
    val voltage: String = "",
    val voltageHz: String = ""
)

@Immutable
data class GondolaBrake(
    val type: String = "",
    val model: String = "",
    val capacity: String = ""
)

@Immutable
data class GondolaMechanicalSuspension(
    val supportPoleHeight: String = "",
    val frontBeamLength: String = "",
    val material: String = ""
)

@Immutable
data class GondolaEngineWeight(
    val totalPlatformWeightIncludingHoistSafetyLockControlPanel: String = "",
    val mechanicalSuspensionWeight: String = "",
    val balanceWeight: String = "",
    val totalEngineWeightExcludingWireRopeAndCable: String = ""
)

@Immutable
data class GondolaVisualInspection(
    val suspensionStructureFrontBeam: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureMiddleBeam: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureRearBeam: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureFrontBeamSupportPole: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureLowerFrontBeamSupportPole: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureReinforcementClamp: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureCouplingSleeve: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureTurnbuckle: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureReinforcementRope: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureRearSupportPole: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureBalanceWeight: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureFrontSupportPoleBase: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureRearSupportPoleBase: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureJackBaseJoint: GondolaInspectionResult = GondolaInspectionResult(),
    val suspensionStructureConnectionBolts: GondolaInspectionResult = GondolaInspectionResult(),
    val steelWireRopeMainRope: GondolaInspectionResult = GondolaInspectionResult(),
    val steelWireRopeSafetyRope: GondolaInspectionResult = GondolaInspectionResult(),
    val steelWireRopeSlingBinder: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemHoistMotor: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemBrakeRelease: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemManualRelease: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemPowerControl: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemPowerCable: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemHandleSwitch: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemUpperLimitSwitch: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemLimitStopper: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemSocketFitting: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemGrounding: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemBreakerFuse: GondolaInspectionResult = GondolaInspectionResult(),
    val electricalSystemEmergencyStop: GondolaInspectionResult = GondolaInspectionResult(),
    val platformHoistMountingFrame: GondolaInspectionResult = GondolaInspectionResult(),
    val platformFrame: GondolaInspectionResult = GondolaInspectionResult(),
    val platformBottomPlate: GondolaInspectionResult = GondolaInspectionResult(),
    val platformPinsAndBolts: GondolaInspectionResult = GondolaInspectionResult(),
    val platformBracket: GondolaInspectionResult = GondolaInspectionResult(),
    val platformToeBoard: GondolaInspectionResult = GondolaInspectionResult(),
    val platformRollerAndGuidePulley: GondolaInspectionResult = GondolaInspectionResult(),
    val platformNameplate: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesSafetyLock: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesRubberBumper: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesSafetyLifeline: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesLoadLimitSwitch: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesLimitBlock: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesUpperLimitSwitch: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesBodyHarness: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesSafetyHarnessAnchorage: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesHandyTalkie: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesSafetyHelmet: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesHandrail: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesOtherPpe: GondolaInspectionResult = GondolaInspectionResult(),
    val safetyDevicesCoupForGlass: GondolaInspectionResult? = GondolaInspectionResult()
)

@Immutable
data class GondolaInspectionResult(
    val status: Boolean? = false,
    val result: String? = ""
)

@Immutable
data class GondolaNonDestructiveTesting(
    val steelWireRope: GondolaNdtSection<GondolaSteelWireRopeItem> = GondolaNdtSection(),
    val suspensionStructure: GondolaNdtSection<GondolaSuspensionStructureItem> = GondolaNdtSection(),
    val gondolaCage: GondolaNdtSection<GondolaCageItem> = GondolaNdtSection(),
    val clamps: GondolaNdtSection<GondolaClampsItem> = GondolaNdtSection()
)

@Immutable
data class GondolaNdtSection<T>(
    val ndtType: String = "",
    val items: ImmutableList<T> = persistentListOf()
)

@Immutable
data class GondolaSteelWireRopeItem(
    val usage: String = "",
    val specDiameter: String = "",
    val actualDiameter: String = "",
    val construction: String = "",
    val type: String = "",
    val length: String = "",
    val age: String = "",
    val defect: String = "",
    val description: String = ""
)

@Immutable
data class GondolaSuspensionStructureItem(
    val inspectedPart: String = "",
    val location: String = "",
    val defect: String = "",
    val description: String = ""
)

@Immutable
data class GondolaCageItem(
    val inspectedPart: String = "",
    val location: String = "",
    val defect: String = "",
    val description: String = ""
)

@Immutable
data class GondolaClampsItem(
    val inspectedPart: String = "",
    val location: String = "",
    val defect: String = "",
    val description: String = ""
)

@Immutable
data class GondolaTesting(
    val dynamicLoad: ImmutableList<GondolaLoadTest> = persistentListOf(),
    val staticLoad: ImmutableList<GondolaLoadTest> = persistentListOf()
)

@Immutable
data class GondolaLoadTest(
    val loadPercentage: String = "",
    val load: String = "",
    val result: String = "",
    val description: String = ""
)

@Immutable
data class GondolaConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val recommendations: ImmutableList<String> = persistentListOf()
)