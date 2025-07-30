package com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Mobile Crane inspection report.
 * This class is immutable.
 */
@Immutable
data class MobileCraneUiState(
    val mobileCraneInspectionReport: MobileCraneInspectionReport = MobileCraneInspectionReport()
) {
    companion object {
        fun createDummyMobileCraneUiState(): MobileCraneUiState {
            return MobileCraneUiState(
                mobileCraneInspectionReport = MobileCraneInspectionReport(
                    extraId = "MC-12345",
                    moreExtraId = "EX-67890",
                    equipmentType = "Mobile Crane",
                    examinationType = "Routine Inspection",
                    generalData = MobileCraneGeneralData(
                        owner = "Awesome Crane Co.",
                        address = "123 Crane Way, Boomtown, CA 90210",
                        personInCharge = "John Doe",
                        userAddress = "456 Operator Ave, Lift City, NY 10001",
                        unitLocation = "Construction Site Alpha",
                        operatorName = "Alice Operator",
                        driveType = "Diesel Electric",
                        manufacturer = "HeavyLifter Inc.",
                        brandType = "HL-5000",
                        yearOfManufacture = "2022",
                        serialNumber = "SN-HL5000-2022-001",
                        liftingCapacity = "50 Ton",
                        intendedUse = "General Construction Lifting",
                        permitNumber = "PERMIT-CRANE-007",
                        operatorCertificate = "CERT-OP-ALICE-001",
                        equipmentHistory = "Regular maintenance performed. No major repairs.",
                        inspectionDate = "2025-07-30"
                    ),
                    technicalData = MobileCraneTechnicalData(
                        specifications = MobileCraneSpecifications(
                            maximumWorkingLoadCapacity = "50 Ton",
                            boomLength = "30 Meter",
                            maximumJibLength = "15 Meter",
                            maximumJibWorkingLoad = "5 Ton",
                            maxBoomJibLength = "45 Meter",
                            craneWeight = "40 Ton",
                            maxLiftingHeight = "40 Meter",
                            boomWorkingAngle = "75 Degrees"
                        ),
                        driveMotor = MobileCraneDriveMotor(
                            engineNumber = "ENG-HLD-9876",
                            type = "Cummins QSL9",
                            numberOfCylinders = "6",
                            netPower = "300 HP",
                            brandYearOfManufacture = "Cummins 2021",
                            manufacturer = "Cummins"
                        ),
                        mainHook = MobileCraneHook(
                            type = "Single Hook",
                            capacity = "50 Ton",
                            material = "High-Strength Steel",
                            serialNumber = "HOOK-MAIN-001"
                        ),
                        auxiliaryHook = MobileCraneHook(
                            type = "Double Hook",
                            capacity = "10 Ton",
                            material = "High-Strength Steel",
                            serialNumber = "HOOK-AUX-002"
                        ),
                        wireRope = MobileCraneWireRope(
                            mainLoadHoistDrum = MobileCraneWireRopeDrum(
                                diameter = "20 Milimeter",
                                type = "Steel Wire Rope",
                                construction = "6x37 IWRC",
                                breakingStrength = "300 kN",
                                length = "150 Meter"
                            ),
                            auxiliaryLoadHoistDrum = MobileCraneWireRopeDrum(
                                diameter = "10 Milimeter",
                                type = "Steel Wire Rope",
                                construction = "6x19 IWRC",
                                breakingStrength = "100 kN",
                                length = "50 Meter"
                            ),
                            boomHoistDrum = MobileCraneWireRopeDrum(
                                diameter = "16 Milimeter",
                                type = "Steel Wire Rope",
                                construction = "6x24 IWRC",
                                breakingStrength = "200 kN",
                                length = "100 Meter"
                            )
                        )
                    ),
                    visualInspection = MobileCraneVisualInspection(
                        foundationAnchorBoltCorrosion = MobileCraneInspectionResult(true, "No corrosion"),
                        foundationAnchorBoltCracks = MobileCraneInspectionResult(true, "No cracks"),
                        foundationAnchorBoltDeformation = MobileCraneInspectionResult(true, "No deformation"),
                        foundationAnchorBoltTightness = MobileCraneInspectionResult(true, "Tight"),
                        frameColumnsOnFoundationCorrosion = MobileCraneInspectionResult(true, "No corrosion"),
                        frameColumnsOnFoundationCracks = MobileCraneInspectionResult(true, "No cracks"),
                        frameColumnsOnFoundationDeformation = MobileCraneInspectionResult(true, "No deformation"),
                        frameColumnsOnFoundationFastening = MobileCraneInspectionResult(true, "Secure"),
                        frameColumnsOnFoundationTransverseReinforcement = MobileCraneInspectionResult(true, "Good condition"),
                        frameColumnsOnFoundationDiagonalReinforcement = MobileCraneInspectionResult(true, "Good condition"),
                        ladderCorrosion = MobileCraneInspectionResult(true, "No corrosion"),
                        ladderCracks = MobileCraneInspectionResult(true, "No cracks"),
                        ladderDeformation = MobileCraneInspectionResult(true, "No deformation"),
                        ladderFastening = MobileCraneInspectionResult(true, "Secure"),
                        workingPlatformCorrosion = MobileCraneInspectionResult(true, "No corrosion"),
                        workingPlatformCracks = MobileCraneInspectionResult(true, "No cracks"),
                        workingPlatformDeformation = MobileCraneInspectionResult(true, "No deformation"),
                        workingPlatformFastening = MobileCraneInspectionResult(true, "Secure"),
                        outriggersOutriggerArmHousing = MobileCraneInspectionResult(true, "Good condition"),
                        outriggersOutriggerArms = MobileCraneInspectionResult(true, "Good condition"),
                        outriggersJack = MobileCraneInspectionResult(true, "Good condition"),
                        outriggersOutriggerPads = MobileCraneInspectionResult(true, "Good condition"),
                        outriggersHousingConnectionToChassis = MobileCraneInspectionResult(true, "Secure"),
                        outriggersOutriggerSafetyLocks = MobileCraneInspectionResult(true, "Functional"),
                        turntableSlewingRollerBearing = MobileCraneInspectionResult(true, "Lubricated"),
                        turntableBrakeHousing = MobileCraneInspectionResult(true, "No damage"),
                        turntableBrakeLiningsAndShoes = MobileCraneInspectionResult(true, "Good"),
                        turntableDrumSurface = MobileCraneInspectionResult(true, "Clean"),
                        turntablePressureCylinder = MobileCraneInspectionResult(true, "No leaks"),
                        turntableDrumAxle = MobileCraneInspectionResult(true, "No wear"),
                        turntableLeversPinsBolts = MobileCraneInspectionResult(true, "Secure"),
                        turntableGuard = MobileCraneInspectionResult(true, "In place"),
                        latticeBoomMainBoom = MobileCraneInspectionResult(true, "No damage"),
                        latticeBoomBoomSection = MobileCraneInspectionResult(true, "Good condition"),
                        latticeBoomTopPulley = MobileCraneInspectionResult(true, "Lubricated"),
                        latticeBoomPulleyGuard = MobileCraneInspectionResult(true, "In place"),
                        latticeBoomWireRopeGuard = MobileCraneInspectionResult(true, "In place"),
                        latticeBoomPulleyGrooveLip = MobileCraneInspectionResult(true, "No wear"),
                        latticeBoomPivotPin = MobileCraneInspectionResult(true, "Lubricated"),
                        latticeBoomWireRopeGuidePulley = MobileCraneInspectionResult(true, "Good condition"),
                        clutchMainClutch = MobileCraneInspectionResult(true, "Engages properly"),
                        transmission = MobileCraneInspectionResult(true, "Smooth operation"),
                        steeringFrontWheel = MobileCraneInspectionResult(true, "Responds well"),
                        steeringMiddleWheel = MobileCraneInspectionResult(true, "Responds well"),
                        steeringRearWheel = MobileCraneInspectionResult(true, "Responds well"),
                        brakeServiceBrake = MobileCraneInspectionResult(true, "Effective"),
                        brakeParkingBrake = MobileCraneInspectionResult(true, "Holds well"),
                        brakeBrakeHousing = MobileCraneInspectionResult(true, "No damage"),
                        brakeBrakeLiningsAndShoes = MobileCraneInspectionResult(true, "Good"),
                        brakeDrumSurface = MobileCraneInspectionResult(true, "Clean"),
                        brakeLeversPinsBolts = MobileCraneInspectionResult(true, "Secure"),
                        brakeGuard = MobileCraneInspectionResult(true, "In place"),
                        travelDrumClutchHousing = MobileCraneInspectionResult(true, "No damage"),
                        travelDrumClutchLining = MobileCraneInspectionResult(true, "Good"),
                        travelDrumClutchDrumSurface = MobileCraneInspectionResult(true, "Clean"),
                        travelDrumLeversPinsBolts = MobileCraneInspectionResult(true, "Secure"),
                        travelDrumGuard = MobileCraneInspectionResult(true, "In place"),
                        mainWinchDrumMounting = MobileCraneInspectionResult(true, "Secure"),
                        mainWinchWindingDrumSurface = MobileCraneInspectionResult(true, "Clean"),
                        mainWinchBrakeLiningsAndShoes = MobileCraneInspectionResult(true, "Good"),
                        mainWinchBrakeDrumSurface = MobileCraneInspectionResult(true, "Clean"),
                        mainWinchBrakeHousing = MobileCraneInspectionResult(true, "No damage"),
                        mainWinchClutchLiningsAndShoes = MobileCraneInspectionResult(true, "Good"),
                        mainWinchClutchDrumSurface = MobileCraneInspectionResult(true, "Clean"),
                        mainWinchGroove = MobileCraneInspectionResult(true, "No damage"),
                        mainWinchGrooveLip = MobileCraneInspectionResult(true, "No wear"),
                        mainWinchFlanges = MobileCraneInspectionResult(true, "No damage"),
                        mainWinchBrakeActuatorLeversPinsAndBolts = MobileCraneInspectionResult(true, "Secure"),
                        auxiliaryWinchDrumMounting = MobileCraneInspectionResult(true, "Secure"),
                        auxiliaryWinchWindingDrumSurface = MobileCraneInspectionResult(true, "Clean"),
                        auxiliaryWinchBrakeLiningsAndShoes = MobileCraneInspectionResult(true, "Good"),
                        auxiliaryWinchBrakeDrumSurface = MobileCraneInspectionResult(true, "Clean"),
                        auxiliaryWinchBrakeHousing = MobileCraneInspectionResult(true, "No damage"),
                        auxiliaryWinchClutchLiningsAndShoes = MobileCraneInspectionResult(true, "Good"),
                        auxiliaryWinchClutchDrumSurface = MobileCraneInspectionResult(true, "Clean"),
                        auxiliaryWinchGroove = MobileCraneInspectionResult(true, "No damage"),
                        auxiliaryWinchGrooveLip = MobileCraneInspectionResult(true, "No wear"),
                        auxiliaryWinchFlanges = MobileCraneInspectionResult(true, "No damage"),
                        auxiliaryWinchBrakeActuatorLeversPinsAndBolts = MobileCraneInspectionResult(true, "Secure"),
                        hoistGearBlockLubrication = MobileCraneInspectionResult(true, "Sufficient"),
                        hoistGearBlockOilSeal = MobileCraneInspectionResult(true, "No leaks"),
                        mainPulleyPulleyGroove = MobileCraneInspectionResult(true, "No wear"),
                        mainPulleyPulleyGrooveLip = MobileCraneInspectionResult(true, "No wear"),
                        mainPulleyPulleyPin = MobileCraneInspectionResult(true, "Lubricated"),
                        mainPulleyBearing = MobileCraneInspectionResult(true, "Smooth"),
                        mainPulleyPulleyGuard = MobileCraneInspectionResult(true, "In place"),
                        mainPulleyWireRopeGuard = MobileCraneInspectionResult(true, "In place"),
                        mainHookVisualSwivelNutAndBearing = MobileCraneInspectionResult(true, "Good condition"),
                        mainHookVisualTrunnion = MobileCraneInspectionResult(true, "No damage"),
                        mainHookVisualSafetyLatch = MobileCraneInspectionResult(true, "Functional"),
                        auxiliaryHookVisualFreeFallWeight = MobileCraneInspectionResult(true, "Present"),
                        auxiliaryHookVisualSwivelNutAndBearing = MobileCraneInspectionResult(true, "Good condition"),
                        auxiliaryHookVisualSafetyLatch = MobileCraneInspectionResult(true, "Functional"),
                        mainWireRopeVisualCorrosion = MobileCraneInspectionResult(true, "No corrosion"),
                        mainWireRopeVisualWear = MobileCraneInspectionResult(true, "Minimal wear"),
                        mainWireRopeVisualBreakage = MobileCraneInspectionResult(true, "No broken wires"),
                        mainWireRopeVisualDeformation = MobileCraneInspectionResult(true, "No deformation"),
                        auxiliaryWireRopeVisualCorrosion = MobileCraneInspectionResult(true, "No corrosion"),
                        auxiliaryWireRopeVisualWear = MobileCraneInspectionResult(true, "Minimal wear"),
                        auxiliaryWireRopeVisualBreakage = MobileCraneInspectionResult(true, "No broken wires"),
                        auxiliaryWireRopeVisualDeformation = MobileCraneInspectionResult(true, "No deformation"),
                        limitSwitchLsLongTravel = MobileCraneInspectionResult(true, "Functional"),
                        limitSwitchLsCrossTravel = MobileCraneInspectionResult(true, "Functional"),
                        limitSwitchLsHoisting = MobileCraneInspectionResult(true, "Functional"),
                        internalCombustionEngineCoolingSystem = MobileCraneInspectionResult(true, "Good"),
                        internalCombustionEngineLubricationSystem = MobileCraneInspectionResult(true, "Good"),
                        internalCombustionEngineEngineMounting = MobileCraneInspectionResult(true, "Secure"),
                        internalCombustionEngineSafetyGuardEquipment = MobileCraneInspectionResult(true, "In place"),
                        internalCombustionEngineExhaustSystem = MobileCraneInspectionResult(true, "No leaks"),
                        internalCombustionEngineAirIntakeSystem = MobileCraneInspectionResult(true, "Clear"),
                        internalCombustionEngineFuelSystem = MobileCraneInspectionResult(true, "No leaks"),
                        internalCombustionEnginePowerTransmissionSystem = MobileCraneInspectionResult(true, "Smooth"),
                        internalCombustionEngineBattery = MobileCraneInspectionResult(true, "Charged"),
                        internalCombustionEngineStarterMotor = MobileCraneInspectionResult(true, "Operates"),
                        internalCombustionEngineWiringInstallation = MobileCraneInspectionResult(true, "Neat"),
                        internalCombustionEngineTurbocharger = MobileCraneInspectionResult(true, "No unusual noise"),
                        hydraulicHydraulicPump = MobileCraneInspectionResult(true, "Good"),
                        hydraulicHydraulicLines = MobileCraneInspectionResult(true, "No leaks"),
                        hydraulicHydraulicFilter = MobileCraneInspectionResult(true, "Clean"),
                        hydraulicHydraulicTank = MobileCraneInspectionResult(true, "Sufficient level"),
                        hydraulicMotorMainWinchMotor = MobileCraneInspectionResult(true, "Operates smoothly"),
                        hydraulicMotorAuxiliaryWinchMotor = MobileCraneInspectionResult(true, "Operates smoothly"),
                        hydraulicMotorBoomWinchMotor = MobileCraneInspectionResult(true, "Operates smoothly"),
                        hydraulicMotorSwingMotor = MobileCraneInspectionResult(true, "Operates smoothly"),
                        controlValveReliefValve = MobileCraneInspectionResult(true, "Set correctly"),
                        controlValveMainWinchValve = MobileCraneInspectionResult(true, "Operates"),
                        controlValveAuxiliaryWinchValve = MobileCraneInspectionResult(true, "Operates"),
                        controlValveBoomWinchValve = MobileCraneInspectionResult(true, "Operates"),
                        controlValveBoomMovementValve = MobileCraneInspectionResult(true, "Operates"),
                        controlValveSteeringCylinderValve = MobileCraneInspectionResult(true, "Operates"),
                        controlValveAxleOscillationValve = MobileCraneInspectionResult(true, "Operates"),
                        controlValveOutriggerMovementValve = MobileCraneInspectionResult(true, "Operates"),
                        hydraulicCylinderBoomMovementCylinder = MobileCraneInspectionResult(true, "No leaks"),
                        hydraulicCylinderOutriggerCylinder = MobileCraneInspectionResult(true, "No leaks"),
                        hydraulicCylinderSteeringWheelCylinder = MobileCraneInspectionResult(true, "No leaks"),
                        hydraulicCylinderAxleOscillationCylinder = MobileCraneInspectionResult(true, "No leaks"),
                        hydraulicCylinderTelescopicCylinder = MobileCraneInspectionResult(true, "No leaks"),
                        pneumaticCompressor = MobileCraneInspectionResult(true, "Operates"),
                        pneumaticTankAndSafetyValve = MobileCraneInspectionResult(true, "Good condition"),
                        pneumaticPressurizedAirLines = MobileCraneInspectionResult(true, "No leaks"),
                        pneumaticAirFilter = MobileCraneInspectionResult(true, "Clean"),
                        pneumaticControlValve = MobileCraneInspectionResult(true, "Operates"),
                        operatorCabinSafetyLadder = MobileCraneInspectionResult(true, "Secure"),
                        operatorCabinDoor = MobileCraneInspectionResult(true, "Opens/Closes properly"),
                        operatorCabinWindow = MobileCraneInspectionResult(true, "Clean and intact"),
                        operatorCabinFanAc = MobileCraneInspectionResult(true, "Functional"),
                        operatorCabinControlLeversButtons = MobileCraneInspectionResult(true, "Responsive"),
                        operatorCabinPendantControl = MobileCraneInspectionResult(true, "Functional"),
                        operatorCabinLighting = MobileCraneInspectionResult(true, "All lights work"),
                        operatorCabinHornSignalAlarm = MobileCraneInspectionResult(true, "Audible and visible"),
                        operatorCabinFuse = MobileCraneInspectionResult(true, "All fuses intact"),
                        operatorCabinCommunicationDevice = MobileCraneInspectionResult(true, "Functional"),
                        operatorCabinFireExtinguisher = MobileCraneInspectionResult(true, "Charged and accessible"),
                        operatorCabinOperatingSigns = MobileCraneInspectionResult(true, "Clear and legible"),
                        operatorCabinIgnitionKeyMasterSwitch = MobileCraneInspectionResult(true, "Functional"),
                        operatorCabinButtonsHandlesLevers = MobileCraneInspectionResult(true, "Operate smoothly"),
                        electricalComponentsPanelConductorConnector = MobileCraneInspectionResult(true, "Clean and secure"),
                        electricalComponentsConductorProtection = MobileCraneInspectionResult(true, "Adequate"),
                        electricalComponentsMotorInstallationSafetySystem = MobileCraneInspectionResult(true, "Installed correctly"),
                        electricalComponentsGroundingSystem = MobileCraneInspectionResult(true, "Properly grounded"),
                        electricalComponentsInstallation = MobileCraneInspectionResult(true, "Neat and organized"),
                        safetyDevicesLadderHandrail = MobileCraneInspectionResult(true, "Secure"),
                        safetyDevicesEngineOilLubricantPressure = MobileCraneInspectionResult(true, "Within limits"),
                        safetyDevicesHydraulicOilPressure = MobileCraneInspectionResult(true, "Within limits"),
                        safetyDevicesAirPressure = MobileCraneInspectionResult(true, "Within limits"),
                        safetyDevicesAmperemeter = MobileCraneInspectionResult(true, "Reads correctly"),
                        safetyDevicesVoltage = MobileCraneInspectionResult(true, "Reads correctly"),
                        safetyDevicesEngineTemperature = MobileCraneInspectionResult(true, "Normal"),
                        safetyDevicesTransmissionTemperature = MobileCraneInspectionResult(true, "Normal"),
                        safetyDevicesConverterOilTemperaturePressure = MobileCraneInspectionResult(true, "Normal"),
                        safetyDevicesSpeedometerIndicator = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesRotaryLamp = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesMainHoistRopeUpDownLimit = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesAuxiliaryHoistRopeUpDownLimit = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesSwingMotionLimit = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesLevelIndicator = MobileCraneInspectionResult(true, "Accurate"),
                        safetyDevicesLoadWeightIndicator = MobileCraneInspectionResult(true, "Accurate"),
                        safetyDevicesLoadChart = MobileCraneInspectionResult(true, "Present and legible"),
                        safetyDevicesAnemometerWindSpeed = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesBoomAngleIndicator = MobileCraneInspectionResult(true, "Accurate"),
                        safetyDevicesAirPressureIndicator = MobileCraneInspectionResult(true, "Accurate"),
                        safetyDevicesHydraulicPressureIndicator = MobileCraneInspectionResult(true, "Accurate"),
                        safetyDevicesSafetyValves = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesMainWindingDrumSafetyLock = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesAuxiliaryWindingDrumSafetyLock = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesTelescopicMotionLimit = MobileCraneInspectionResult(true, "Functional"),
                        safetyDevicesLightningArrester = MobileCraneInspectionResult(true, "In place"),
                        safetyDevicesLiftingHeightIndicator = MobileCraneInspectionResult(true, "Accurate")
                    ),
                    nonDestructiveExamination = MobileCraneNonDestructiveExamination(
                        wireRope = MobileCraneNdeWireRopeSection(
                            ndtType = "Visual & Magnetic Particle",
                            items = persistentListOf(
                                MobileCraneNdeWireRopeItem(
                                    usage = "Main Hoist",
                                    specDiameter = "20 mm",
                                    actualDiameter = "19.8 mm",
                                    construction = "6x37 IWRC",
                                    type = "Steel Wire Rope",
                                    length = "150 m",
                                    age = "2 years",
                                    hasDefect = false,
                                    remarks = "Good condition, no visible defects."
                                ),
                                MobileCraneNdeWireRopeItem(
                                    usage = "Auxiliary Hoist",
                                    specDiameter = "10 mm",
                                    actualDiameter = "9.9 mm",
                                    construction = "6x19 IWRC",
                                    type = "Steel Wire Rope",
                                    length = "50 m",
                                    age = "2 years",
                                    hasDefect = false,
                                    remarks = "Good condition, no visible defects."
                                )
                            )
                        ),
                        boom = MobileCraneNdeBoomSection(
                            boomType = "Lattice Boom",
                            ndtType = "Visual & Dye Penetrant",
                            items = persistentListOf(
                                MobileCraneNdeBoomItem(
                                    partInspected = "Boom Section 1",
                                    location = "Base",
                                    hasDefect = false,
                                    remarks = "No cracks or deformation found."
                                ),
                                MobileCraneNdeBoomItem(
                                    partInspected = "Boom Section 2",
                                    location = "Mid",
                                    hasDefect = false,
                                    remarks = "No cracks or deformation found."
                                )
                            )
                        ),
                        mainHook = MobileCraneNdeHookSection(
                            hookCapacity = "50 T",
                            ndtType = "Visual & Magnetic Particle",
                            specification = MobileCraneNdeMeasurement(a = "75", b = "80", c = "15", d = "20", e = "30", f = "40", g = "50", h = "60", finding = MobileCraneInspectionResult(true, "")),
                            measurementResult = MobileCraneNdeMeasurement(a = "75.2", b = "79.8", c = "15.1", d = "20.0", e = "30.3", f = "40.1", g = "50.0", h = "60.2", finding = MobileCraneInspectionResult(true, "Within tolerance")),
                            tolerance = MobileCraneNdeMeasurement(a = "0.5", b = "0.5", c = "0.5", d = "0.5", e = "0.5", f = "0.5", g = "0.5", h = "0.5", finding = MobileCraneInspectionResult(true, ""))
                        ),
                        auxiliaryHook = MobileCraneNdeHookSection(
                            hookCapacity = "10 T",
                            ndtType = "Visual",
                            specification = MobileCraneNdeMeasurement(a = "50", b = "55", c = "10", d = "15", finding = MobileCraneInspectionResult(true, "")),
                            measurementResult = MobileCraneNdeMeasurement(a = "50.1", b = "54.9", c = "10.0", d = "15.2", finding = MobileCraneInspectionResult(true, "Within tolerance")),
                            tolerance = MobileCraneNdeMeasurement(a = "0.3", b = "0.3", c = "0.3", d = "0.3", finding = MobileCraneInspectionResult(true, ""))
                        ),
                        mainDrum = MobileCraneNdeDrumSection(
                            ndtType = "Visual",
                            specification = MobileCraneNdeMeasurement(a = "500", b = "300", finding = MobileCraneInspectionResult(true, "")),
                            measurementResult = MobileCraneNdeMeasurement(a = "500.5", b = "300.2", finding = MobileCraneInspectionResult(true, "Within tolerance"))
                        ),
                        auxiliaryDrum = MobileCraneNdeDrumSection(
                            ndtType = "Visual",
                            specification = MobileCraneNdeMeasurement(a = "200", b = "100", finding = MobileCraneInspectionResult(true, "")),
                            measurementResult = MobileCraneNdeMeasurement(a = "200.1", b = "100.0", finding = MobileCraneInspectionResult(true, "Within tolerance"))
                        ),
                        mainPulley = MobileCraneNdePulleySection(
                            ndtType = "Visual",
                            specification = MobileCraneNdeMeasurement(a = "250", finding = MobileCraneInspectionResult(true, "")),
                            measurementResult = MobileCraneNdeMeasurement(a = "250.3", finding = MobileCraneInspectionResult(true, "Within tolerance"))
                        ),
                        auxiliaryPulley = MobileCraneNdePulleySection(
                            ndtType = "Visual",
                            specification = MobileCraneNdeMeasurement(a = "150", finding = MobileCraneInspectionResult(true, "")),
                            measurementResult = MobileCraneNdeMeasurement(a = "150.1", finding = MobileCraneInspectionResult(true, "Within tolerance"))
                        )
                    ),
                    testing = MobileCraneTesting(
                        functionTest = MobileCraneFunctionTest(
                            hoistingLowering = MobileCraneTestResult(true, "Smooth operation"),
                            extendedRetractedBoom = MobileCraneTestResult(true, "Operates correctly"),
                            extendedRetractedOutrigger = MobileCraneTestResult(true, "Operates correctly"),
                            swingSlewing = MobileCraneTestResult(true, "Smooth and accurate"),
                            antiTwoBlock = MobileCraneTestResult(true, "Functional"),
                            boomStop = MobileCraneTestResult(true, "Functional"),
                            anemometerWindSpeed = MobileCraneTestResult(true, "Reads correctly"),
                            brakeLockingDevice = MobileCraneTestResult(true, "Engages firmly"),
                            loadMomentIndicator = MobileCraneTestResult(true, "Displays accurate readings"),
                            turnSignal = MobileCraneTestResult(true, "Functional"),
                            drivingLights = MobileCraneTestResult(true, "All lights functional"),
                            loadIndicatorLight = MobileCraneTestResult(true, "Functional"),
                            rotaryLamp = MobileCraneTestResult(true, "Functional"),
                            horn = MobileCraneTestResult(true, "Audible"),
                            swingAlarm = MobileCraneTestResult(true, "Audible"),
                            reverseAlarm = MobileCraneTestResult(true, "Audible"),
                            overloadAlarm = MobileCraneTestResult(true, "Activates correctly")
                        ),
                        loadTest = MobileCraneLoadTest(
                            dynamic = MobileCraneLoadTestType(
                                mainHook = persistentListOf(
                                    MobileCraneLoadTestItem(
                                        boomLength = "15 m",
                                        radius = "10 m",
                                        boomAngle = "70 deg",
                                        testLoadKg = "30000",
                                        safeWorkingLoadKg = "35000",
                                        result = "Pass"
                                    )
                                ),
                                auxiliaryHook = persistentListOf(
                                    MobileCraneLoadTestItem(
                                        boomLength = "25 m",
                                        radius = "15 m",
                                        boomAngle = "60 deg",
                                        testLoadKg = "20000",
                                        safeWorkingLoadKg = "22000",
                                        result = "Pass"
                                    )
                                )
                            ),
                            static = MobileCraneLoadTestType(
                                mainHook = persistentListOf(
                                    MobileCraneLoadTestItem(
                                        boomLength = "15 m",
                                        radius = "10 m",
                                        boomAngle = "70 deg",
                                        testLoadKg = "30000",
                                        safeWorkingLoadKg = "35000",
                                        result = "Pass"
                                    )
                                ),
                                auxiliaryHook = persistentListOf(
                                    MobileCraneLoadTestItem(
                                        boomLength = "25 m",
                                        radius = "15 m",
                                        boomAngle = "60 deg",
                                        testLoadKg = "20000",
                                        safeWorkingLoadKg = "22000",
                                        result = "Pass"
                                    )
                                )
                            )
                        )
                    ),
                    conclusion = MobileCraneConclusion(
                        summary = persistentListOf(
                            "The mobile crane is in good overall condition.",
                            "All major components are functioning as expected.",
                            "No significant defects were found during visual inspection or NDT.",
                            "Load tests were successfully completed."
                        ),
                        recommendations = persistentListOf(
                            "Continue with regular scheduled maintenance.",
                            "Ensure operator certification is kept up-to-date.",
                            "Monitor wire rope condition periodically.",
                            "Conduct next inspection in 12 months."
                        )
                    )
                )
            )
        }
    }
}

@Immutable
data class MobileCraneInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val generalData: MobileCraneGeneralData = MobileCraneGeneralData(),
    val technicalData: MobileCraneTechnicalData = MobileCraneTechnicalData(),
    val visualInspection: MobileCraneVisualInspection = MobileCraneVisualInspection(),
    val nonDestructiveExamination: MobileCraneNonDestructiveExamination = MobileCraneNonDestructiveExamination(),
    val testing: MobileCraneTesting = MobileCraneTesting(),
    val conclusion: MobileCraneConclusion = MobileCraneConclusion()
)

@Immutable
data class MobileCraneGeneralData(
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
    val equipmentHistory: String = "",
    val inspectionDate: String = "",
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