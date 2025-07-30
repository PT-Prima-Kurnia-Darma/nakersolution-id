package com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Gantry Crane inspection report.
 * This class is immutable.
 */
@Immutable
data class GantryCraneUiState(
    val gantryCraneInspectionReport: GantryCraneInspectionReport = GantryCraneInspectionReport()
) {
    companion object {
        fun createDummyGantryCraneUiState(): GantryCraneUiState {
            return GantryCraneUiState(
                gantryCraneInspectionReport = GantryCraneInspectionReport(
                    extraId = "GC-2025-001",
                    moreExtraId = "XYZ-12345",
                    equipmentType = "Gantry Crane",
                    examinationType = "Periodic Inspection",
                    generalData = GantryCraneGeneralData(
                        owner = "PT. Maju Mundur",
                        address = "Jl. Raya Industri No. 10, Jakarta",
                        user = "PT. Pabrik Baja",
                        personInCharge = "Budi Santoso",
                        unitLocation = "Workshop A",
                        driveType = "Electric",
                        manufacturer = "Konecranes",
                        brandType = "SMD-20",
                        yearOfManufacture = "2018",
                        serialNumber = "KC-SMD-20-12345",
                        liftingCapacity = "20 Ton",
                        intendedUse = "Material Handling",
                        permitNumber = "IZIN-GC-001/2024",
                        operatorCertificate = "OPR-GC-001/2024",
                        technicalDataManual = "Available"
                    ),
                    technicalData = GantryCraneTechnicalData(
                        specifications = GantryCraneTechSpecs(
                            liftingHeight = GantryCraneMovementData(
                                hoisting = "10 m",
                                traveling = "",
                                traversing = ""
                            ),
                            girderLength = GantryCraneMovementData(
                                hoisting = "",
                                traveling = "25 m",
                                traversing = "8 m"
                            ),
                            speed = GantryCraneMovementData(
                                hoisting = "5 m/min",
                                traveling = "30 m/min",
                                traversing = "15 m/min"
                            )
                        ),
                        driveMotor = GantryCraneDriveMotor(
                            capacity = GantryCraneMovementData(
                                hoisting = "20 Ton",
                                traveling = "5 Ton",
                                traversing = "2 Ton"
                            ),
                            powerKw = GantryCraneMovementData(
                                hoisting = "18.5 kW",
                                traveling = "7.5 kW",
                                traversing = "3.7 kW"
                            ),
                            type = GantryCraneMovementData(
                                hoisting = "AC Induction",
                                traveling = "AC Induction",
                                traversing = "AC Induction"
                            ),
                            rpm = GantryCraneMovementData(
                                hoisting = "1450 rpm",
                                traveling = "1450 rpm",
                                traversing = "1450 rpm"
                            ),
                            voltageV = GantryCraneMovementData(
                                hoisting = "380 V",
                                traveling = "380 V",
                                traversing = "380 V"
                            ),
                            currentA = GantryCraneMovementData(
                                hoisting = "38 A",
                                traveling = "15 A",
                                traversing = "7.5 A"
                            ),
                            frequencyHz = GantryCraneMovementData(
                                hoisting = "50 Hz",
                                traveling = "50 Hz",
                                traversing = "50 Hz"
                            ),
                            phase = GantryCraneMovementData(
                                hoisting = "3 Phase",
                                traveling = "3 Phase",
                                traversing = "3 Phase"
                            ),
                            powerSupply = GantryCraneMovementData(
                                hoisting = "Grid",
                                traveling = "Grid",
                                traversing = "Grid"
                            )
                        ),
                        startingResistor = GantryCraneStartingResistor(
                            type = GantryCraneMovementData(
                                hoisting = "Wire Wound",
                                traveling = "Wire Wound",
                                traversing = "Wire Wound"
                            ),
                            voltageV = GantryCraneMovementData(
                                hoisting = "380 V",
                                traveling = "380 V",
                                traversing = "380 V"
                            ),
                            currentA = GantryCraneMovementData(
                                hoisting = "30 A",
                                traveling = "10 A",
                                traversing = "5 A"
                            )
                        ),
                        brake = GantryCraneBrake(
                            type = GantryCraneMovementData(
                                hoisting = "Disc",
                                traveling = "Drum",
                                traversing = "Drum"
                            ),
                            model = GantryCraneMovementData(
                                hoisting = "BRAKE-D100",
                                traveling = "BRAKE-T50",
                                traversing = "BRAKE-TR20"
                            )
                        ),
                        controllerBrake = GantryCraneControllerBrake(
                            type = GantryCraneMovementData(
                                hoisting = "Electro-Hydraulic",
                                traveling = "Electro-Magnetic",
                                traversing = "Electro-Magnetic"
                            ),
                            model = GantryCraneMovementData(
                                hoisting = "CONT-H200",
                                traveling = "CONT-M100",
                                traversing = "CONT-M50"
                            )
                        ),
                        hook = GantryCraneHook(
                            type = GantryCraneMovementData(
                                hoisting = "Single",
                                traveling = "",
                                traversing = ""
                            ),
                            capacity = GantryCraneMovementData(
                                hoisting = "20 Ton",
                                traveling = "",
                                traversing = ""
                            ),
                            material = GantryCraneMovementData(
                                hoisting = "Forged Steel",
                                traveling = "",
                                traversing = ""
                            )
                        ),
                        wireRope = GantryCraneWireRope(
                            type = GantryCraneMovementData(
                                hoisting = "IWRC 6x36",
                                traveling = "",
                                traversing = ""
                            ),
                            construction = GantryCraneMovementData(
                                hoisting = "Right Regular Lay",
                                traveling = "",
                                traversing = ""
                            ),
                            diameter = GantryCraneMovementData(
                                hoisting = "22 mm",
                                traveling = "",
                                traversing = ""
                            ),
                            length = GantryCraneMovementData(
                                hoisting = "100 m",
                                traveling = "",
                                traversing = ""
                            )
                        ),
                        chain = GantryCraneChain(
                            type = GantryCraneMovementData(
                                hoisting = "Grade 80",
                                traveling = "",
                                traversing = ""
                            ),
                            construction = GantryCraneMovementData(
                                hoisting = "Short Link",
                                traveling = "",
                                traversing = ""
                            ),
                            diameter = GantryCraneMovementData(
                                hoisting = "13 mm",
                                traveling = "",
                                traversing = ""
                            ),
                            length = GantryCraneMovementData(
                                hoisting = "15 m",
                                traveling = "",
                                traversing = ""
                            )
                        )
                    ),
                    visualInspection = GantryCraneVisualInspection(
                        // Pondasi & Rangka
                        foundationAnchorBoltCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "No corrosion"
                        ),
                        foundationAnchorBoltCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        foundationAnchorBoltDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        foundationAnchorBoltTightness = GantryCraneInspectionResult(
                            status = true,
                            result = "Tight"
                        ),
                        columnFrameCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "Minor surface rust"
                        ),
                        columnFrameCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        columnFrameDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        columnFrameFastening = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        columnFrameCrossBracing = GantryCraneInspectionResult(
                            status = true,
                            result = "Good condition"
                        ),
                        columnFrameDiagonalBracing = GantryCraneInspectionResult(
                            status = true,
                            result = "Good condition"
                        ),
                        // Akses
                        ladderCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "Minor surface rust"
                        ),
                        ladderCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        ladderDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        ladderFastening = GantryCraneInspectionResult(status = true, result = "Secure"),
                        workPlatformCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "No corrosion"
                        ),
                        workPlatformCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        workPlatformDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        workPlatformFastening = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        // Rel Travelling
                        railMountingBeamCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "No corrosion"
                        ),
                        railMountingBeamCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        railMountingBeamDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        railMountingBeamFastening = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        travelingRailCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "Slight surface rust"
                        ),
                        travelingRailCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        travelingRailJoint = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        travelingRailStraightness = GantryCraneInspectionResult(
                            status = true,
                            result = "Straight"
                        ),
                        travelingRailAlignmentBetweenRails = GantryCraneInspectionResult(
                            status = true,
                            result = "Aligned"
                        ),
                        travelingRailEvennessBetweenRails = GantryCraneInspectionResult(
                            status = true,
                            result = "Even"
                        ),
                        travelingRailGapBetweenRailJoints = GantryCraneInspectionResult(
                            status = true,
                            result = "Within tolerance"
                        ),
                        travelingRailFastener = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        travelingRailStopper = GantryCraneInspectionResult(
                            status = true,
                            result = "Intact"
                        ),
                        // Rel Traversing
                        traversingRailCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "Slight surface rust"
                        ),
                        traversingRailCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        traversingRailJoint = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        traversingRailStraightness = GantryCraneInspectionResult(
                            status = true,
                            result = "Straight"
                        ),
                        traversingRailAlignmentBetweenRails = GantryCraneInspectionResult(
                            status = true,
                            result = "Aligned"
                        ),
                        traversingRailEvennessBetweenRails = GantryCraneInspectionResult(
                            status = true,
                            result = "Even"
                        ),
                        traversingRailGapBetweenRailJoints = GantryCraneInspectionResult(
                            status = true,
                            result = "Within tolerance"
                        ),
                        traversingRailFastener = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        traversingRailStopper = GantryCraneInspectionResult(
                            status = true,
                            result = "Intact"
                        ),
                        // Girder
                        girderCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "Minor surface corrosion"
                        ),
                        girderCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks detected"
                        ),
                        girderCamber = GantryCraneInspectionResult(status = true, result = "Normal"),
                        girderJoint = GantryCraneInspectionResult(
                            status = true,
                            result = "Welds intact"
                        ),
                        girderEndJoint = GantryCraneInspectionResult(status = true, result = "Secure"),
                        girderTruckMountOnGirder = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        // Travelling Gearbox
                        travelingGearboxGirderCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "No significant corrosion"
                        ),
                        travelingGearboxGirderCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        travelingGearboxGirderLubricatingOil = GantryCraneInspectionResult(
                            status = true,
                            result = "Level OK, clean"
                        ),
                        travelingGearboxGirderOilSeal = GantryCraneInspectionResult(
                            status = true,
                            result = "No leaks"
                        ),
                        // Roda Penggerak
                        driveWheelWear = GantryCraneInspectionResult(
                            status = false,
                            result = "Minimal wear"
                        ),
                        driveWheelCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        driveWheelDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        driveWheelFlangeCondition = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        driveWheelChainCondition = GantryCraneInspectionResult(
                            status = true,
                            result = "Well lubricated"
                        ),
                        // Roda Idle
                        idleWheelSecurity = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        idleWheelCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        idleWheelDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        idleWheelFlangeCondition = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        // Penghubung Roda
                        wheelConnectorBogieAxleStraightness = GantryCraneInspectionResult(
                            status = true,
                            result = "Straight"
                        ),
                        wheelConnectorBogieAxleCrossJoint = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        wheelConnectorBogieAxleLubrication = GantryCraneInspectionResult(
                            status = true,
                            result = "Lubricated"
                        ),
                        stopperBumperOnGirderCondition = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        stopperBumperOnGirderReinforcement = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        // Traversing Gearbox
                        traversingGearboxTrolleyCarrierFastening = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        traversingGearboxTrolleyCarrierCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "No corrosion"
                        ),
                        traversingGearboxTrolleyCarrierCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        traversingGearboxTrolleyCarrierLubricatingOil = GantryCraneInspectionResult(
                            status = true,
                            result = "Level OK"
                        ),
                        traversingGearboxTrolleyCarrierOilSeal = GantryCraneInspectionResult(
                            status = true,
                            result = "No leaks"
                        ),
                        // Roda Penggerak Trolley
                        driveWheelOnTrolleyWear = GantryCraneInspectionResult(
                            status = false,
                            result = "Minimal wear"
                        ),
                        driveWheelOnTrolleyCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        driveWheelOnTrolleyDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        driveWheelOnTrolleyFlangeCondition = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        driveWheelOnTrolleyChainCondition = GantryCraneInspectionResult(
                            status = true,
                            result = "Well lubricated"
                        ),
                        // Roda Idle Trolley
                        idleWheelOnTrolleyWear = GantryCraneInspectionResult(
                            status = false,
                            result = "Minimal wear"
                        ),
                        idleWheelOnTrolleyCracks = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks"
                        ),
                        idleWheelOnTrolleyDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        idleWheelOnTrolleyFlangeCondition = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        // Penghubung Roda Trolley
                        wheelConnectorBogieAxleOnTrolleyStraightness = GantryCraneInspectionResult(
                            status = true,
                            result = "Straight"
                        ),
                        wheelConnectorBogieAxleOnTrolleyCrossJoint = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        wheelConnectorBogieAxleOnTrolleyLubrication = GantryCraneInspectionResult(
                            status = true,
                            result = "Lubricated"
                        ),
                        stopperBumperOnGirderOnTrolleyCondition = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        stopperBumperOnGirderOnTrolleyReinforcement = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        // Hoist
                        windingDrumGroove = GantryCraneInspectionResult(
                            status = true,
                            result = "Good condition"
                        ),
                        windingDrumGrooveFlange = GantryCraneInspectionResult(
                            status = true,
                            result = "Good condition"
                        ),
                        windingDrumFlanges = GantryCraneInspectionResult(
                            status = true,
                            result = "Intact"
                        ),
                        brakeWear = GantryCraneInspectionResult(
                            status = false,
                            result = "Minimal wear"
                        ),
                        brakeAdjustment = GantryCraneInspectionResult(
                            status = true,
                            result = "Properly adjusted"
                        ),
                        hoistGearboxLubrication = GantryCraneInspectionResult(
                            status = true,
                            result = "Level OK, clean"
                        ),
                        hoistGearboxOilSeal = GantryCraneInspectionResult(
                            status = true,
                            result = "No leaks"
                        ),
                        // Pully
                        pulleyDiscChainSprocketPulleyGroove = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        pulleyDiscChainSprocketPulleyFlange = GantryCraneInspectionResult(
                            status = true,
                            result = "Good"
                        ),
                        pulleyDiscChainSprocketPulleyPin = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure"
                        ),
                        pulleyDiscChainSprocketBearing = GantryCraneInspectionResult(
                            status = true,
                            result = "Smooth operation"
                        ),
                        pulleyDiscChainSprocketPulleyGuard = GantryCraneInspectionResult(
                            status = true,
                            result = "Intact"
                        ),
                        pulleyDiscChainSprocketWireRopeChainGuard = GantryCraneInspectionResult(
                            status = true,
                            result = "Intact"
                        ),
                        // Kait
                        mainHookWear = GantryCraneInspectionResult(
                            status = false,
                            result = "Minimal wear"
                        ),
                        mainHookThroatOpening = GantryCraneInspectionResult(
                            status = true,
                            result = "Within limits"
                        ),
                        mainHookSwivelNutAndBearing = GantryCraneInspectionResult(
                            status = true,
                            result = "Smooth rotation"
                        ),
                        mainHookTrunnion = GantryCraneInspectionResult(
                            status = true,
                            result = "Good condition"
                        ),
                        auxiliaryHookWear = GantryCraneInspectionResult(status = false, result = "N/A"),
                        auxiliaryHookThroatOpening = GantryCraneInspectionResult(
                            status = true,
                            result = "N/A"
                        ),
                        auxiliaryHookSwivelNutAndBearing = GantryCraneInspectionResult(
                            status = true,
                            result = "N/A"
                        ),
                        auxiliaryHookTrunnion = GantryCraneInspectionResult(
                            status = true,
                            result = "N/A"
                        ),
                        // Tali Baja
                        mainWireRopeCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "No significant corrosion"
                        ),
                        mainWireRopeWear = GantryCraneInspectionResult(
                            status = false,
                            result = "Minimal wear"
                        ),
                        mainWireRopeBroken = GantryCraneInspectionResult(
                            status = false,
                            result = "No broken wires"
                        ),
                        mainWireRopeDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        auxiliaryWireRopeCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "N/A"
                        ),
                        auxiliaryWireRopeWear = GantryCraneInspectionResult(
                            status = false,
                            result = "N/A"
                        ),
                        auxiliaryWireRopeBroken = GantryCraneInspectionResult(
                            status = false,
                            result = "N/A"
                        ),
                        auxiliaryWireRopeDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "N/A"
                        ),
                        // Rantai
                        mainChainCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "No corrosion"
                        ),
                        mainChainWear = GantryCraneInspectionResult(
                            status = false,
                            result = "Minimal wear"
                        ),
                        mainChainCrackedBroken = GantryCraneInspectionResult(
                            status = false,
                            result = "No cracks/breaks"
                        ),
                        mainChainDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "No deformation"
                        ),
                        auxiliaryChainCorrosion = GantryCraneInspectionResult(
                            status = false,
                            result = "N/A"
                        ),
                        auxiliaryChainWear = GantryCraneInspectionResult(
                            status = false,
                            result = "N/A"
                        ),
                        auxiliaryChainCrackedBroken = GantryCraneInspectionResult(
                            status = false,
                            result = "N/A"
                        ),
                        auxiliaryChainDeformation = GantryCraneInspectionResult(
                            status = false,
                            result = "N/A"
                        ),
                        // Limit Switch
                        limitSwitchLsLongTraveling = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        limitSwitchLsCrossTraveling = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        limitSwitchLsHoisting = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        // Kabin
                        operatorCabinSafetyLadder = GantryCraneInspectionResult(
                            status = true,
                            result = "Good condition"
                        ),
                        operatorCabinDoor = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        operatorCabinWindow = GantryCraneInspectionResult(
                            status = true,
                            result = "Clean and clear"
                        ),
                        operatorCabinFanAc = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        operatorCabinControlLeversButtons = GantryCraneInspectionResult(
                            status = true,
                            result = "Responsive"
                        ),
                        operatorCabinPendantControl = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        operatorCabinLighting = GantryCraneInspectionResult(
                            status = true,
                            result = "Working"
                        ),
                        operatorCabinHorn = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        operatorCabinFuse = GantryCraneInspectionResult(
                            status = true,
                            result = "Intact"
                        ),
                        operatorCabinCommunicationDevice = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        operatorCabinFireExtinguisher = GantryCraneInspectionResult(
                            status = true,
                            result = "Present, charged"
                        ),
                        operatorCabinOperatingSigns = GantryCraneInspectionResult(
                            status = true,
                            result = "Clear and visible"
                        ),
                        operatorCabinIgnitionKeyMasterSwitch = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        // Kelistrikan
                        electricalComponentsPanelConductorConnector = GantryCraneInspectionResult(
                            status = true,
                            result = "Secure, no loose connections"
                        ),
                        electricalComponentsConductorProtection = GantryCraneInspectionResult(
                            status = true,
                            result = "Intact insulation"
                        ),
                        electricalComponentsMotorInstallationSafetySystem = GantryCraneInspectionResult(
                            status = true,
                            result = "Functional"
                        ),
                        electricalComponentsGroundingSystem = GantryCraneInspectionResult(
                            status = true,
                            result = "Properly grounded"
                        ),
                        electricalComponentsInstallation = GantryCraneInspectionResult(
                            status = true,
                            result = "Neat and secure"
                        )
                    ),
                    nonDestructiveExamination = GantryCraneNde(
                        wireRope = GantryCraneNdeWireRope(
                            method = "Magnetic Rope Testing (MRT)",
                            items = persistentListOf(
                                GantryCraneNdeWireRopeItem(
                                    usage = "Main Hoist",
                                    specDiameter = "22 mm",
                                    actualDiameter = "21.8 mm",
                                    construction = "IWRC 6x36",
                                    type = "Galvanized",
                                    length = "100 m",
                                    age = "2 years",
                                    finding = GantryCraneInspectionResult(
                                        status = false,
                                        result = "No significant defects"
                                    )
                                )
                            )
                        ),
                        mainHook = GantryCraneNdeHook(
                            method = "Magnetic Particle Testing (MPT)",
                            specification = GantryCraneNdeMeasurement(
                                a = "100 mm", b = "50 mm", c = "20 mm", d = "15 mm",
                                e = "30 mm", f = "25 mm", g = "120 mm", h = "60 mm"
                            ),
                            measurementResult = GantryCraneNdeMeasurement(
                                a = "99.8 mm", b = "49.9 mm", c = "19.9 mm", d = "14.9 mm",
                                e = "29.9 mm", f = "24.9 mm", g = "119.8 mm", h = "59.9 mm"
                            ),
                            tolerance = GantryCraneNdeMeasurement(
                                a = "±1 mm", b = "±0.5 mm", c = "±0.2 mm", d = "±0.1 mm",
                                e = "±0.3 mm", f = "±0.2 mm", g = "±1 mm", h = "±0.5 mm",
                                finding = GantryCraneInspectionResult(
                                    status = true,
                                    result = "All within tolerance"
                                )
                            )
                        ),
                        girder = GantryCraneNdeGirder(
                            method = "Ultrasonic Testing (UT)",
                            items = persistentListOf(
                                GantryCraneNdeGirderItem(
                                    location = "Main Girder Mid-span",
                                    finding = GantryCraneInspectionResult(
                                        status = false,
                                        result = "No defects detected"
                                    )
                                ),
                                GantryCraneNdeGirderItem(
                                    location = "End Girder Joint (East)",
                                    finding = GantryCraneInspectionResult(
                                        status = false,
                                        result = "No defects detected"
                                    )
                                )
                            )
                        )
                    ),
                    testing = GantryCraneTesting(
                        dynamicTest = GantryCraneDynamicTest(
                            withoutLoad = GantryCraneDynamicWithoutLoad(
                                traveling = GantryCraneDynamicTestItem(
                                    shouldBe = "Smooth, no abnormal noise",
                                    testedMeasured = "Smooth, normal noise",
                                    remarks = "OK"
                                ),
                                traversing = GantryCraneDynamicTestItem(
                                    shouldBe = "Smooth, no abnormal noise",
                                    testedMeasured = "Smooth, normal noise",
                                    remarks = "OK"
                                ),
                                hoisting = GantryCraneDynamicTestItem(
                                    shouldBe = "Smooth, no abnormal noise",
                                    testedMeasured = "Smooth, normal noise",
                                    remarks = "OK"
                                ),
                                safetyDevice = GantryCraneDynamicTestItem(
                                    shouldBe = "Functional",
                                    testedMeasured = "Functional",
                                    remarks = "All limit switches tested OK"
                                ),
                                brakeSwitch = GantryCraneDynamicTestItem(
                                    shouldBe = "Functional",
                                    testedMeasured = "Functional",
                                    remarks = "Responds quickly"
                                ),
                                brakeLockingDevice = GantryCraneDynamicTestItem(
                                    shouldBe = "Functional",
                                    testedMeasured = "Functional",
                                    remarks = "Engages securely"
                                ),
                                electricalInstallation = GantryCraneDynamicTestItem(
                                    shouldBe = "No abnormal heating/sparks",
                                    testedMeasured = "No issues",
                                    remarks = "OK"
                                )
                            ),
                            withLoad = GantryCraneDynamicWithLoad(
                                noLoad = GantryCraneLoadTestResult(
                                    hoist = "Normal",
                                    traversing = "Normal",
                                    travelling = "Normal",
                                    brakeSystem = "Normal",
                                    remarks = "OK"
                                ),
                                swl25 = GantryCraneLoadTestResult(
                                    hoist = "Normal",
                                    traversing = "Normal",
                                    travelling = "Normal",
                                    brakeSystem = "Normal",
                                    remarks = "OK"
                                ),
                                swl50 = GantryCraneLoadTestResult(
                                    hoist = "Normal",
                                    traversing = "Normal",
                                    travelling = "Normal",
                                    brakeSystem = "Normal",
                                    remarks = "OK"
                                ),
                                swl75 = GantryCraneLoadTestResult(
                                    hoist = "Normal",
                                    traversing = "Normal",
                                    travelling = "Normal",
                                    brakeSystem = "Normal",
                                    remarks = "OK"
                                ),
                                swl100 = GantryCraneLoadTestResult(
                                    hoist = "Normal",
                                    traversing = "Normal",
                                    travelling = "Normal",
                                    brakeSystem = "Normal",
                                    remarks = "OK"
                                )
                            )
                        ),
                        staticTest = GantryCraneStaticTest(
                            load = "25 Ton (125% SWL)",
                            deflectionResult = GantryCraneInspectionResult(
                                status = true,
                                result = "Within standard"
                            ),
                            deflectionStandard = GantryCraneDeflectionStandard(
                                designBased = "L/1000 = 25 mm",
                                spanLength = "25000 mm",
                                calculation = "25 mm"
                            ),
                            deflectionMeasurement = persistentListOf(
                                GantryCraneDeflectionItem(
                                    position = "Mid-span",
                                    deflection = "18 mm",
                                    maxStandard = "25 mm",
                                    remarks = "OK"
                                ),
                                GantryCraneDeflectionItem(
                                    position = "1/4 Span (Left)",
                                    deflection = "10 mm",
                                    maxStandard = "25 mm",
                                    remarks = "OK"
                                ),
                                GantryCraneDeflectionItem(
                                    position = "1/4 Span (Right)",
                                    deflection = "11 mm",
                                    maxStandard = "25 mm",
                                    remarks = "OK"
                                )
                            )
                        )
                    ),
                    conclusion = GantryCraneConclusion(
                        summary = persistentListOf(
                            "Overall condition of the gantry crane is good.",
                            "All safety devices are functional.",
                            "No critical defects found during visual inspection and NDE."
                        ),
                        recommendations = persistentListOf(
                            "Perform routine lubrication as per manufacturer's guidelines.",
                            "Monitor minor surface corrosion on column frame and address in next maintenance.",
                            "Continue regular inspections."
                        ),
                        nextInspectionDateSuggestion = "2026-07-30"
                    )
                )
            )
        }
    }
}

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
    val startingResistor: GantryCraneStartingResistor = GantryCraneStartingResistor(), // FIXED: ADDED
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
    val phase: GantryCraneMovementData = GantryCraneMovementData(), // FIXED: ADDED
    val powerSupply: GantryCraneMovementData = GantryCraneMovementData() // FIXED: ADDED
)

// FIXED: ADDED a new data class for Starting Resistor
@Immutable
data class GantryCraneStartingResistor(
    val type: GantryCraneMovementData = GantryCraneMovementData(),
    val voltageV: GantryCraneMovementData = GantryCraneMovementData(),
    val currentA: GantryCraneMovementData = GantryCraneMovementData()
)

@Immutable
data class GantryCraneBrake(
    val type: GantryCraneMovementData = GantryCraneMovementData(),
    val model: GantryCraneMovementData = GantryCraneMovementData()
)

@Immutable
data class GantryCraneControllerBrake(
    val type: GantryCraneMovementData = GantryCraneMovementData(),
    val model: GantryCraneMovementData = GantryCraneMovementData()
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
    // Penghubung Roda Trolley
    val wheelConnectorBogieAxleOnTrolleyStraightness: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val wheelConnectorBogieAxleOnTrolleyCrossJoint: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val wheelConnectorBogieAxleOnTrolleyLubrication: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val stopperBumperOnGirderOnTrolleyCondition: GantryCraneInspectionResult = GantryCraneInspectionResult(),
    val stopperBumperOnGirderOnTrolleyReinforcement: GantryCraneInspectionResult = GantryCraneInspectionResult(),
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
    val type: String = "",
    val length: String = "",
    val age: String = "", // FIXED: ADDED
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
    val swl25: GantryCraneLoadTestResult = GantryCraneLoadTestResult(),
    val swl50: GantryCraneLoadTestResult = GantryCraneLoadTestResult(),
    val swl75: GantryCraneLoadTestResult = GantryCraneLoadTestResult(),
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
    val deflectionResult: GantryCraneInspectionResult = GantryCraneInspectionResult(), // FIXED: ADDED
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