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
) {
    companion object {
        fun createDummyGondolaUiState(): GondolaUiState {
            return GondolaUiState(
                gondolaInspectionReport = GondolaInspectionReport(
                    extraId = "GON-001",
                    moreExtraId = "BATCH-GON-XYZ",
                    equipmentType = "Gondola",
                    examinationType = "Annual Inspection",
                    generalData = GondolaGeneralData(
                        owner = "SkyView Apartments",
                        ownerAddress = "50 Sky Lane, High City, USA",
                        user = "Building Management",
                        personInCharge = "Mr. Arthur Dent",
                        unitLocation = "Building West Facade",
                        operatorName = "Maria Popova",
                        manufacturer = "Elevate Solutions",
                        brandType = "Model SG-20",
                        driveType = "Electric",
                        yearOfManufacture = "2018",
                        serialNumberUnitNumber = "GSG20-ABC123",
                        capacity = "500 kg",
                        standardUsed = "EN 1808",
                        usedFor = "Building Facade Maintenance",
                        usagePermitNumber = "GON-PERMIT-98765",
                        operatorCertificate = "Certified Gondola Operator",
                        inspectionDate = "2023-10-27"
                    ),
                    technicalData = GondolaTechnicalData(
                        gondolaSpecifications = GondolaSpecifications(
                            supportPoleHeight = "40 m",
                            beam = GondolaBeam(frontBeamLength = "3 m", middleBeamLength = "2 m", rearBeamLength = "3 m"),
                            balanceWeightToBeamDistance = "0.5 m",
                            capacity = "500 kg",
                            speed = "8 m/min",
                            platformSize = GondolaPlatformSize(length = "3 m", width = "1.5 m", height = "2.1 m"),
                            wireRope = "6x25+PWS-13mm"
                        ),
                        hoist = GondolaHoist(
                            model = "Hoist-E5",
                            liftingPower = "2.2 kW",
                            electricMotor = GondolaElectricMotor(
                                type = "Induction Motor",
                                power = "2.2 kW",
                                voltage = "400 V",
                                voltageHz = "50 Hz"
                            )
                        ),
                        safetyLockType = "Rope Clamp Safety Lock",
                        brake = GondolaBrake(
                            type = "Electromagnetic Shoe",
                            model = "Brake-S2",
                            capacity = "150% of SWL"
                        ),
                        mechanicalSuspension = GondolaMechanicalSuspension(
                            supportPoleHeight = "40 m",
                            frontBeamLength = "3 m",
                            material = "Galvanized Steel"
                        ),
                        engineWeight = GondolaEngineWeight(
                            totalPlatformWeightIncludingHoistSafetyLockControlPanel = "1500 kg",
                            mechanicalSuspensionWeight = "800 kg",
                            balanceWeight = "1200 kg",
                            totalEngineWeightExcludingWireRopeAndCable = "3500 kg"
                        )
                    ),
                    visualInspection = GondolaVisualInspection(
                        suspensionStructureFrontBeam = GondolaInspectionResult(status = true, result = "No cracks or deformation"),
                        suspensionStructureMiddleBeam = GondolaInspectionResult(status = true, result = "No cracks or deformation"),
                        suspensionStructureRearBeam = GondolaInspectionResult(status = true, result = "No cracks or deformation"),
                        suspensionStructureFrontBeamSupportPole = GondolaInspectionResult(status = true, result = "Securely anchored"),
                        suspensionStructureLowerFrontBeamSupportPole = GondolaInspectionResult(status = true, result = "Securely anchored"),
                        suspensionStructureReinforcementClamp = GondolaInspectionResult(status = true, result = "Clamps tight"),
                        suspensionStructureCouplingSleeve = GondolaInspectionResult(status = true, result = "Sleeves in place"),
                        suspensionStructureTurnbuckle = GondolaInspectionResult(status = true, result = "Turnbuckles secure"),
                        suspensionStructureReinforcementRope = GondolaInspectionResult(status = true, result = "Rope properly tensioned"),
                        suspensionStructureRearSupportPole = GondolaInspectionResult(status = true, result = "Securely anchored"),
                        suspensionStructureBalanceWeight = GondolaInspectionResult(status = true, result = "Securely attached"),
                        suspensionStructureFrontSupportPoleBase = GondolaInspectionResult(status = true, result = "Base plates intact"),
                        suspensionStructureRearSupportPoleBase = GondolaInspectionResult(status = true, result = "Base plates intact"),
                        suspensionStructureJackBaseJoint = GondolaInspectionResult(status = true, result = "Joints are firm"),
                        suspensionStructureConnectionBolts = GondolaInspectionResult(status = true, result = "All bolts are tight"),
                        steelWireRopeMainRope = GondolaInspectionResult(status = true, result = "No broken wires, wear within limits"),
                        steelWireRopeSafetyRope = GondolaInspectionResult(status = true, result = "No broken wires, wear within limits"),
                        steelWireRopeSlingBinder = GondolaInspectionResult(status = true, result = "Sling binders secure"),
                        electricalSystemHoistMotor = GondolaInspectionResult(status = true, result = "Motor operates normally"),
                        electricalSystemBrakeRelease = GondolaInspectionResult(status = true, result = "Brake releases correctly"),
                        electricalSystemManualRelease = GondolaInspectionResult(status = true, result = "Manual release accessible"),
                        electricalSystemPowerControl = GondolaInspectionResult(status = true, result = "Control panel functional"),
                        electricalSystemPowerCable = GondolaInspectionResult(status = true, result = "Cable in good condition, no damage"),
                        electricalSystemHandleSwitch = GondolaInspectionResult(status = true, result = "Switches operate smoothly"),
                        electricalSystemUpperLimitSwitch = GondolaInspectionResult(status = true, result = "Upper limit switch functional"),
                        electricalSystemLimitStopper = GondolaInspectionResult(status = true, result = "Stopper properly set"),
                        electricalSystemSocketFitting = GondolaInspectionResult(status = true, result = "All fittings secure"),
                        electricalSystemGrounding = GondolaInspectionResult(status = true, result = "Grounding system intact"),
                        electricalSystemBreakerFuse = GondolaInspectionResult(status = true, result = "Breaker and fuses in good condition"),
                        electricalSystemEmergencyStop = GondolaInspectionResult(status = true, result = "Emergency stop button functional"),
                        platformHoistMountingFrame = GondolaInspectionResult(status = true, result = "Frame intact"),
                        platformFrame = GondolaInspectionResult(status = true, result = "No cracks or deformation"),
                        platformBottomPlate = GondolaInspectionResult(status = true, result = "Bottom plate secure"),
                        platformPinsAndBolts = GondolaInspectionResult(status = true, result = "Pins and bolts tight"),
                        platformBracket = GondolaInspectionResult(status = true, result = "Brackets secure"),
                        platformToeBoard = GondolaInspectionResult(status = true, result = "Toe board present"),
                        platformRollerAndGuidePulley = GondolaInspectionResult(status = true, result = "Rollers and pulleys lubricated"),
                        platformNameplate = GondolaInspectionResult(status = true, result = "Nameplate legible"),
                        safetyDevicesSafetyLock = GondolaInspectionResult(status = true, result = "Safety lock engages correctly"),
                        safetyDevicesRubberBumper = GondolaInspectionResult(status = true, result = "Bumpers in place"),
                        safetyDevicesSafetyLifeline = GondolaInspectionResult(status = true, result = "Lifeline properly installed"),
                        safetyDevicesLoadLimitSwitch = GondolaInspectionResult(status = true, result = "Load limit switch functional"),
                        safetyDevicesLimitBlock = GondolaInspectionResult(status = true, result = "Limit blocks set correctly"),
                        safetyDevicesUpperLimitSwitch = GondolaInspectionResult(status = true, result = "Upper limit switch operational"),
                        safetyDevicesBodyHarness = GondolaInspectionResult(status = true, result = "Harness available and in good condition"),
                        safetyDevicesSafetyHarnessAnchorage = GondolaInspectionResult(status = true, result = "Anchorage points secure"),
                        safetyDevicesHandyTalkie = GondolaInspectionResult(status = true, result = "Handy talkie available and charged"),
                        safetyDevicesSafetyHelmet = GondolaInspectionResult(status = true, result = "Helmet available"),
                        safetyDevicesHandrail = GondolaInspectionResult(status = true, result = "Handrails are secure"),
                        safetyDevicesOtherPpe = GondolaInspectionResult(status = true, result = "Gloves and safety shoes available"),
                        safetyDevicesCoupForGlass = GondolaInspectionResult(status = true, result = "Coupling for glass present")
                    ),
                    nonDestructiveTesting = GondolaNonDestructiveTesting(
                        steelWireRope = GondolaNdtSection(
                            ndtType = "Visual Inspection",
                            items = persistentListOf(
                                GondolaSteelWireRopeItem(
                                    usage = "Main Rope", specDiameter = "13 mm", actualDiameter = "12.8 mm",
                                    construction = "6x25+PWS", type = "Galvanized Steel", length = "60 m", age = "4 Years",
                                    defect = "Minor rust spots", description = "Rust on outer wires, within acceptable limits."
                                )
                            )
                        ),
                        suspensionStructure = GondolaNdtSection(
                            ndtType = "Dye Penetrant Testing",
                            items = persistentListOf(
                                GondolaSuspensionStructureItem(
                                    inspectedPart = "Front Beam Welds", location = "Connection to support pole",
                                    defect = "No cracks detected", description = "Weld integrity confirmed."
                                )
                            )
                        ),
                        gondolaCage = GondolaNdtSection(
                            ndtType = "Visual Inspection",
                            items = persistentListOf(
                                GondolaCageItem(
                                    inspectedPart = "Platform Floor", location = "Center",
                                    defect = "Minor scratches", description = "Surface scratches from use."
                                )
                            )
                        ),
                        clamps = GondolaNdtSection(
                            ndtType = "Visual Inspection",
                            items = persistentListOf(
                                GondolaClampsItem(
                                    inspectedPart = "Wire Rope Clamp", location = "Top of main rope",
                                    defect = "Securely tightened", description = "Clamps appear to be properly installed."
                                )
                            )
                        )
                    ),
                    testing = GondolaTesting(
                        dynamicLoad = persistentListOf(
                            GondolaLoadTest(loadPercentage = "50%", load = "250 kg", result = "Passed", description = "Smooth operation at 50% load"),
                            GondolaLoadTest(loadPercentage = "100%", load = "500 kg", result = "Passed", description = "Stable operation at SWL")
                        ),
                        staticLoad = persistentListOf(
                            GondolaLoadTest(loadPercentage = "150%", load = "750 kg", result = "Passed", description = "Structure remained stable during static test")
                        )
                    ),
                    conclusion = GondolaConclusion(
                        summary = persistentListOf("The gondola is in good working condition and meets safety standards. All inspected components are functional."),
                        recommendations = persistentListOf("Regularly inspect wire rope for any signs of wear.", "Perform load tests annually.")
                    )
                )
            )
        }
    }
}

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