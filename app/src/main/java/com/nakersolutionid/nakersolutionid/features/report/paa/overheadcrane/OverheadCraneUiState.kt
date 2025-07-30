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
) {
    companion object {
        fun createDummyOverheadCraneUiState(): OverheadCraneUiState {
            return OverheadCraneUiState(
                overheadCraneInspectionReport = OverheadCraneInspectionReport(
                    extraId = "OC-2025-002",
                    moreExtraId = "ABC-67890",
                    equipmentType = "Overhead Crane",
                    examinationType = "Annual Inspection",
                    generalData = OverheadCraneGeneralData(
                        owner = "PT. Bangun Jaya",
                        address = "Jl. Industri No. 5, Surabaya",
                        user = "PT. Manufaktur Logam",
                        personInCharge = "Dewi Susanti",
                        unitLocation = "Assembly Line 3",
                        hoistType = "Electric Wire Rope Hoist",
                        manufacturer = "Demag",
                        brandType = "EKKE 10",
                        yearOfManufacture = "2020",
                        serialNumber = "DEMAG-EKKE-10-67890",
                        liftingCapacity = "10 Ton",
                        intendedUse = "Production Line Material Handling",
                        permitNumber = "IZIN-OC-002/2024",
                        operatorCertificate = "OPR-OC-002/2024",
                        technicalDataManual = "Available",
                        inspectionDate = "2025-07-25"
                    ),
                    technicalData = OverheadCraneTechnicalData(
                        specifications = OverheadCraneTechSpecs(
                            liftingHeight = OverheadCraneMovementData(hoisting = "8 m", traveling = "", traversing = ""),
                            girderLength = OverheadCraneMovementData(hoisting = "", traveling = "20 m", traversing = "6 m"),
                            speed = OverheadCraneMovementData(hoisting = "3 m/min", traveling = "25 m/min", traversing = "10 m/min")
                        ),
                        driveMotor = OverheadCraneDriveMotor(
                            capacity = OverheadCraneMovementData(hoisting = "10 Ton", traveling = "3 Ton", traversing = "1 Ton"),
                            powerKw = OverheadCraneMovementData(hoisting = "11 kW", traveling = "5.5 kW", traversing = "2.2 kW"),
                            type = OverheadCraneMovementData(hoisting = "AC Induction", traveling = "AC Induction", traversing = "AC Induction"),
                            rpm = OverheadCraneMovementData(hoisting = "1400 rpm", traveling = "1400 rpm", traversing = "1400 rpm"),
                            voltageV = OverheadCraneMovementData(hoisting = "380 V", traveling = "380 V", traversing = "380 V"),
                            currentA = OverheadCraneMovementData(hoisting = "25 A", traveling = "10 A", traversing = "5 A"),
                            frequencyHz = OverheadCraneMovementData(hoisting = "50 Hz", traveling = "50 Hz", traversing = "50 Hz")
                        ),
                        startingResistor = OverheadCraneStartingResistor(
                            type = OverheadCraneMovementData(hoisting = "Wire Wound", traveling = "Wire Wound", traversing = "Wire Wound"),
                            voltageV = OverheadCraneMovementData(hoisting = "380 V", traveling = "380 V", traversing = "380 V"),
                            currentA = OverheadCraneMovementData(hoisting = "20 A", traveling = "8 A", traversing = "4 A")
                        ),
                        brake = OverheadCraneBrake(
                            type = OverheadCraneMovementData(hoisting = "Disc", traveling = "Drum", traversing = "Drum"),
                            model = OverheadCraneMovementData(hoisting = "Demag Brake", traveling = "Demag Brake", traversing = "Demag Brake")
                        ),
                        controllerBrake = OverheadCraneControllerBrake(
                            type = OverheadCraneMovementData(hoisting = "Contactor", traveling = "Contactor", traversing = "Contactor"),
                            model = OverheadCraneMovementData(hoisting = "Siemens", traveling = "Siemens", traversing = "Siemens")
                        ),
                        hook = OverheadCraneHook(
                            type = OverheadCraneMovementData(hoisting = "Single", traveling = "", traversing = ""),
                            capacity = OverheadCraneMovementData(hoisting = "10 Ton", traveling = "", traversing = ""),
                            material = OverheadCraneMovementData(hoisting = "Forged Steel", traveling = "", traversing = "")
                        ),
                        chain = OverheadCraneChain(
                            type = OverheadCraneMovementData(hoisting = "N/A", traveling = "N/A", traversing = "N/A"),
                            construction = OverheadCraneMovementData(hoisting = "N/A", traveling = "N/A", traversing = "N/A"),
                            diameter = OverheadCraneMovementData(hoisting = "N/A", traveling = "N/A", traversing = "N/A"),
                            length = OverheadCraneMovementData(hoisting = "N/A", traveling = "N/A", traversing = "N/A")
                        ),
                        wireRope = OverheadCraneWireRope(
                            type = OverheadCraneMovementData(hoisting = "IWRC 6x36", traveling = "", traversing = ""),
                            construction = OverheadCraneMovementData(hoisting = "Right Regular Lay", traveling = "", traversing = ""),
                            diameter = OverheadCraneMovementData(hoisting = "16 mm", traveling = "", traversing = ""),
                            length = OverheadCraneMovementData(hoisting = "80 m", traveling = "", traversing = "")
                        )
                    ),
                    visualInspection = OverheadCraneVisualInspection(
                        foundationAnchorBoltCorrosion = OverheadCraneInspectionResult(status = false, result = "No corrosion"),
                        foundationAnchorBoltCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        foundationAnchorBoltDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        foundationAnchorBoltFastening = OverheadCraneInspectionResult(status = true, result = "Tight"),
                        columnFrameCorrosion = OverheadCraneInspectionResult(status = false, result = "No corrosion"),
                        columnFrameCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        columnFrameDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        columnFrameFastening = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        columnFrameCrossBracing = OverheadCraneInspectionResult(status = true, result = "Good condition"),
                        columnFrameDiagonalBracing = OverheadCraneInspectionResult(status = true, result = "Good condition"),
                        ladderCorrosion = OverheadCraneInspectionResult(status = false, result = "Minor surface rust"),
                        ladderCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        ladderDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        ladderFastening = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        workPlatformCorrosion = OverheadCraneInspectionResult(status = false, result = "No corrosion"),
                        workPlatformCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        workPlatformDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        workPlatformFastening = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        railMountingBeamCorrosion = OverheadCraneInspectionResult(status = false, result = "No corrosion"),
                        railMountingBeamCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        railMountingBeamDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        railMountingBeamFastening = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        travelingRailCorrosion = OverheadCraneInspectionResult(status = false, result = "Slight surface rust"),
                        travelingRailCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        travelingRailJoint = OverheadCraneInspectionResult(status = true, result = "Good"),
                        travelingRailStraightness = OverheadCraneInspectionResult(status = true, result = "Straight"),
                        travelingRailAlignmentBetweenRails = OverheadCraneInspectionResult(status = true, result = "Aligned"),
                        travelingRailEvennessBetweenRails = OverheadCraneInspectionResult(status = true, result = "Even"),
                        travelingRailGapBetweenRailJoints = OverheadCraneInspectionResult(status = true, result = "Within tolerance"),
                        travelingRailFastener = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        travelingRailStopper = OverheadCraneInspectionResult(status = true, result = "Intact"),
                        traversingRailCorrosion = OverheadCraneInspectionResult(status = false, result = "Slight surface rust"),
                        traversingRailCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        traversingRailJoint = OverheadCraneInspectionResult(status = true, result = "Good"),
                        traversingRailStraightness = OverheadCraneInspectionResult(status = true, result = "Straight"),
                        traversingRailAlignmentBetweenRails = OverheadCraneInspectionResult(status = true, result = "Aligned"),
                        traversingRailEvennessBetweenRails = OverheadCraneInspectionResult(status = true, result = "Even"),
                        traversingRailGapBetweenRailJoints = OverheadCraneInspectionResult(status = true, result = "Within tolerance"),
                        traversingRailFastener = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        traversingRailStopper = OverheadCraneInspectionResult(status = true, result = "Intact"),
                        girderCorrosion = OverheadCraneInspectionResult(status = false, result = "Minor surface corrosion"),
                        girderCracks = OverheadCraneInspectionResult(status = false, result = "No cracks detected"),
                        girderCamber = OverheadCraneInspectionResult(status = true, result = "Normal"),
                        girderJoint = OverheadCraneInspectionResult(status = true, result = "Welds intact"),
                        girderEndJoint = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        girderTruckMountOnGirder = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        travelingGearboxGirderCorrosion = OverheadCraneInspectionResult(status = false, result = "No significant corrosion"),
                        travelingGearboxGirderCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        travelingGearboxGirderLubricatingOil = OverheadCraneInspectionResult(status = true, result = "Level OK, clean"),
                        travelingGearboxGirderOilSeal = OverheadCraneInspectionResult(status = true, result = "No leaks"),
                        driveWheelWear = OverheadCraneInspectionResult(status = false, result = "Minimal wear"),
                        driveWheelCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        driveWheelDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        driveWheelFlangeCondition = OverheadCraneInspectionResult(status = true, result = "Good"),
                        driveWheelChainCondition = OverheadCraneInspectionResult(status = false, result = "N/A"), // Assuming no chain drive for traveling
                        idleWheelSecurity = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        idleWheelCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        idleWheelDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        idleWheelFlangeCondition = OverheadCraneInspectionResult(status = true, result = "Good"),
                        wheelConnectorBogieAxleStraightness = OverheadCraneInspectionResult(status = true, result = "Straight"),
                        wheelConnectorBogieAxleCrossJoint = OverheadCraneInspectionResult(status = true, result = "Good"),
                        wheelConnectorBogieAxleLubrication = OverheadCraneInspectionResult(status = true, result = "Lubricated"),
                        stopperBumperOnGirderCondition = OverheadCraneInspectionResult(status = true, result = "Good"),
                        stopperBumperOnGirderReinforcement = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        traversingGearboxTrolleyCarrierFastening = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        traversingGearboxTrolleyCarrierCorrosion = OverheadCraneInspectionResult(status = false, result = "No corrosion"),
                        traversingGearboxTrolleyCarrierCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        traversingGearboxTrolleyCarrierLubricatingOil = OverheadCraneInspectionResult(status = true, result = "Level OK"),
                        traversingGearboxTrolleyCarrierOilSeal = OverheadCraneInspectionResult(status = true, result = "No leaks"),
                        driveWheelOnTrolleyWear = OverheadCraneInspectionResult(status = false, result = "Minimal wear"),
                        driveWheelOnTrolleyCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        driveWheelOnTrolleyDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        driveWheelOnTrolleyFlangeCondition = OverheadCraneInspectionResult(status = true, result = "Good"),
                        driveWheelOnTrolleyChainCondition = OverheadCraneInspectionResult(status = false, result = "N/A"), // Assuming no chain drive for trolley
                        idleWheelOnTrolleyWear = OverheadCraneInspectionResult(status = false, result = "Minimal wear"),
                        idleWheelOnTrolleyCracks = OverheadCraneInspectionResult(status = false, result = "No cracks"),
                        idleWheelOnTrolleyDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        idleWheelOnTrolleyFlangeCondition = OverheadCraneInspectionResult(status = true, result = "Good"),
                        wheelConnectorBogieAxleOnTrolleyStraightness = OverheadCraneInspectionResult(status = true, result = "Straight"),
                        wheelConnectorBogieAxleOnTrolleyCrossJoint = OverheadCraneInspectionResult(status = true, result = "Good"),
                        wheelConnectorBogieAxleOnTrolleyLubrication = OverheadCraneInspectionResult(status = true, result = "Lubricated"),
                        stopperBumperOnGirderOnTrolleyCondition = OverheadCraneInspectionResult(status = true, result = "Good"),
                        stopperBumperOnGirderOnTrolleyReinforcement = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        windingDrumGroove = OverheadCraneInspectionResult(status = true, result = "Good condition"),
                        windingDrumGrooveFlange = OverheadCraneInspectionResult(status = true, result = "Good condition"),
                        windingDrumFlanges = OverheadCraneInspectionResult(status = true, result = "Intact"),
                        brakeWear = OverheadCraneInspectionResult(status = false, result = "Minimal wear"),
                        brakeAdjustment = OverheadCraneInspectionResult(status = true, result = "Properly adjusted"),
                        hoistGearboxLubrication = OverheadCraneInspectionResult(status = true, result = "Level OK, clean"),
                        hoistGearboxOilSeal = OverheadCraneInspectionResult(status = true, result = "No leaks"),
                        pulleyDiscChainSprocketPulleyGroove = OverheadCraneInspectionResult(status = true, result = "Good"),
                        pulleyDiscChainSprocketPulleyFlange = OverheadCraneInspectionResult(status = true, result = "Good"),
                        pulleyDiscChainSprocketPulleyPin = OverheadCraneInspectionResult(status = true, result = "Secure"),
                        pulleyDiscChainSprocketBearing = OverheadCraneInspectionResult(status = true, result = "Smooth operation"),
                        pulleyDiscChainSprocketPulleyGuard = OverheadCraneInspectionResult(status = true, result = "Intact"),
                        pulleyDiscChainSprocketWireRopeChainGuard = OverheadCraneInspectionResult(status = true, result = "Intact"),
                        mainHookWear = OverheadCraneInspectionResult(status = false, result = "Minimal wear"),
                        mainHookThroatOpening = OverheadCraneInspectionResult(status = true, result = "Within limits"),
                        mainHookSwivelNutAndBearing = OverheadCraneInspectionResult(status = true, result = "Smooth rotation"),
                        mainHookTrunnion = OverheadCraneInspectionResult(status = true, result = "Good condition"),
                        auxiliaryHookWear = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryHookThroatOpening = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryHookSwivelNutAndBearing = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryHookTrunnion = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        mainWireRopeCorrosion = OverheadCraneInspectionResult(status = false, result = "No significant corrosion"),
                        mainWireRopeWear = OverheadCraneInspectionResult(status = false, result = "Minimal wear"),
                        mainWireRopeBroken = OverheadCraneInspectionResult(status = false, result = "No broken wires"),
                        mainWireRopeDeformation = OverheadCraneInspectionResult(status = false, result = "No deformation"),
                        auxiliaryWireRopeCorrosion = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryWireRopeWear = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryWireRopeBroken = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryWireRopeDeformation = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        mainChainCorrosion = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        mainChainWear = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        mainChainCrackedBroken = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        mainChainDeformation = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryChainCorrosion = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryChainWear = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryChainCrackedBroken = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        auxiliaryChainDeformation = OverheadCraneInspectionResult(status = null, result = "N/A"),
                        limitSwitchLsLongTraveling = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        limitSwitchLsCrossTraveling = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        limitSwitchLsHoisting = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        operatorRoomCabinSafetyLadder = OverheadCraneInspectionResult(status = true, result = "Good condition"),
                        operatorRoomCabinDoor = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        operatorRoomCabinWindow = OverheadCraneInspectionResult(status = true, result = "Clean and clear"),
                        operatorRoomCabinFanAC = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        operatorRoomCabinControlLeversButtons = OverheadCraneInspectionResult(status = true, result = "Responsive"),
                        operatorRoomCabinPendantControl = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        operatorRoomCabinLighting = OverheadCraneInspectionResult(status = true, result = "Working"),
                        operatorRoomCabinHorn = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        operatorRoomCabinFuseProtection = OverheadCraneInspectionResult(status = true, result = "Intact"),
                        operatorRoomCabinCommunicationDevice = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        operatorRoomCabinFireExtinguisher = OverheadCraneInspectionResult(status = true, result = "Present, charged"),
                        operatorRoomCabinOperatingSigns = OverheadCraneInspectionResult(status = true, result = "Clear and visible"),
                        operatorRoomCabinIgnitionKeyMasterSwitch = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        electricalComponentPanelConnectorConductor = OverheadCraneInspectionResult(status = true, result = "Secure, no loose connections"),
                        electricalComponentConductorProtection = OverheadCraneInspectionResult(status = true, result = "Intact insulation"),
                        electricalComponentMotorInstallationSafetySystem = OverheadCraneInspectionResult(status = true, result = "Functional"),
                        electricalComponentGroundingSystem = OverheadCraneInspectionResult(status = true, result = "Properly grounded"),
                        electricalComponentInstallation = OverheadCraneInspectionResult(status = true, result = "Neat and secure")
                    ),
                    nonDestructiveExamination = OverheadCraneNde(
                        chain = OverheadCraneNdeChain(
                            method = "N/A",
                            items = persistentListOf()
                        ),
                        mainHook = OverheadCraneNdeHook(
                            method = "Magnetic Particle Testing (MPT)",
                            specification = OverheadCraneNdeMeasurement(
                                a = "80 mm", b = "40 mm", c = "15 mm", d = "10 mm",
                                e = "25 mm", f = "20 mm", g = "100 mm", h = "50 mm"
                            ),
                            measurementResult = OverheadCraneNdeMeasurement(
                                a = "79.8 mm", b = "39.9 mm", c = "14.9 mm", d = "9.9 mm",
                                e = "24.9 mm", f = "19.9 mm", g = "99.8 mm", h = "49.9 mm"
                            ),
                            tolerance = OverheadCraneNdeMeasurement(
                                a = "±0.8 mm", b = "±0.4 mm", c = "±0.15 mm", d = "±0.1 mm",
                                e = "±0.25 mm", f = "±0.2 mm", g = "±1 mm", h = "±0.5 mm",
                                finding = OverheadCraneInspectionResult(status = true, result = "All within tolerance")
                            )
                        )
                    ),
                    testing = OverheadCraneTesting(
                        dynamicTest = OverheadCraneDynamicTest(
                            withoutLoad = OverheadCraneDynamicWithoutLoad(
                                traveling = OverheadCraneDynamicTestItem(shouldBe = "Smooth, no abnormal noise", testedMeasured = "Smooth, normal noise", remarks = "OK"),
                                traversing = OverheadCraneDynamicTestItem(shouldBe = "Smooth, no abnormal noise", testedMeasured = "Smooth, normal noise", remarks = "OK"),
                                hoisting = OverheadCraneDynamicTestItem(shouldBe = "Smooth, no abnormal noise", testedMeasured = "Smooth, normal noise", remarks = "OK"),
                                safetyLatch = OverheadCraneDynamicTestItem(shouldBe = "Functional", testedMeasured = "Functional", remarks = "Hook safety latch secure"),
                                brakeSwitch = OverheadCraneDynamicTestItem(shouldBe = "Functional", testedMeasured = "Functional", remarks = "Responds quickly"),
                                brakeLockingDevice = OverheadCraneDynamicTestItem(shouldBe = "Functional", testedMeasured = "Functional", remarks = "Engages securely"),
                                electricalInstallation = OverheadCraneDynamicTestItem(shouldBe = "No abnormal heating/sparks", testedMeasured = "No issues", remarks = "OK")
                            ),
                            withLoad = OverheadCraneDynamicWithLoad(
                                noLoad = OverheadCraneLoadTestResult(hoist = "Normal", traversing = "Normal", travelling = "Normal", brakeSystem = "Normal", remarks = "OK"),
                                swl25 = OverheadCraneLoadTestResult(hoist = "Normal", traversing = "Normal", travelling = "Normal", brakeSystem = "Normal", remarks = "OK"),
                                swl50 = OverheadCraneLoadTestResult(hoist = "Normal", traversing = "Normal", travelling = "Normal", brakeSystem = "Normal", remarks = "OK"),
                                swl75 = OverheadCraneLoadTestResult(hoist = "Normal", traversing = "Normal", travelling = "Normal", brakeSystem = "Normal", remarks = "OK"),
                                swl100 = OverheadCraneLoadTestResult(hoist = "Normal", traversing = "Normal", travelling = "Normal", brakeSystem = "Normal", remarks = "OK")
                            )
                        ),
                        staticTest = OverheadCraneStaticTest(
                            load = "12.5 Ton (125% SWL)",
                            deflectionMeasurement = OverheadCraneDeflectionMeasurement(
                                singleGirder = mapOf(
                                    "Mid-span" to "15 mm",
                                    "1/4 Span (Left)" to "8 mm",
                                    "1/4 Span (Right)" to "9 mm"
                                ),
                                doubleGirder = emptyMap() // Assuming it's a single girder crane for this dummy data
                            ),
                            notes = "Deflection within acceptable limits (L/1000 standard)."
                        )
                    ),
                    conclusion = OverheadCraneConclusion(
                        summary = persistentListOf(
                            "Overhead crane is in good operational condition.",
                            "All safety features are confirmed to be working.",
                            "No significant defects found during the inspection."
                        ),
                        recommendations = persistentListOf(
                            "Maintain regular lubrication schedule.",
                            "Consider minor touch-up painting for surface rust on ladder.",
                            "Next inspection recommended in 1 year."
                        )
                    )
                )
            )
        }
    }
}

@Immutable
data class OverheadCraneInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
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
    val technicalDataManual: String = "",
    val inspectionDate: String = ""
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
    val wheelConnectorBogieAxleOnTrolleyStraightness: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val wheelConnectorBogieAxleOnTrolleyCrossJoint: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val wheelConnectorBogieAxleOnTrolleyLubrication: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val stopperBumperOnGirderOnTrolleyCondition: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
    val stopperBumperOnGirderOnTrolleyReinforcement: OverheadCraneInspectionResult = OverheadCraneInspectionResult(),
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