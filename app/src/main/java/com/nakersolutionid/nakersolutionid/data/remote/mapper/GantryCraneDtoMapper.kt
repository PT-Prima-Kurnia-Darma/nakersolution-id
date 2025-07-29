package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane.*
import com.nakersolutionid.nakersolutionid.domain.model.*

// FIXED: Centralized BAP category constants to ensure consistency across all mappers.
private object BAPCategoryGantryCrane {
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val FUNCTIONAL_TEST = "UJI FUNGSI"
    const val NDT_TEST = "UJI NDT"
    const val LOAD_TEST = "UJI BEBAN"
}

// =================================================================================================
//                                  Report DTO <-> Domain Model
// =================================================================================================

/**
 * Converts [InspectionWithDetailsDomain] (from the data layer) into [GantryCraneReportRequest] (for API submission).
 * This mapping flattens the domain model into a request DTO, looking up each value from the checkItems list.
 */
fun InspectionWithDetailsDomain.toGantryCraneReportRequest(): GantryCraneReportRequest {
    val checkItemsMap = this.checkItems.associateBy { it.category to it.itemName }

    fun findString(category: String, itemName: String): String {
        return checkItemsMap[category to itemName]?.result ?: ""
    }

    fun findStatus(category: String, itemName: String): Boolean {
        return checkItemsMap[category to itemName]?.status ?: false
    }

    val generalData = GantryCraneGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        location = this.inspection.addressUsageLocation ?: "",
        manufacturerHoist = this.inspection.manufacturer?.name?.split(" / ")?.getOrNull(0) ?: "",
        manufacturerStructure = this.inspection.manufacturer?.name?.split(" / ")?.getOrNull(1) ?: "",
        brandOrType = this.inspection.manufacturer?.brandOrType ?: "",
        manufactureYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        maxLiftingCapacityKg = this.inspection.capacity ?: "",
        usagePermitNumber = this.inspection.permitNumber ?: "",
        operatorCertificateStatus = findString("general_data", "operatorCertificate"),
        technicalDataManualStatus = findString("general_data", "technicalDataManual"),
        inspectionDate = this.inspection.reportDate ?: ""
    )

    val techCategory = "technical_data"
    val technicalData = GantryCraneTechnicalData(
        liftHeight = findString(techCategory, "spec_liftingHeight_hoisting"),
        girderLength = findString(techCategory, "spec_girderLength_hoisting"),
        hoistingSpeed = findString(techCategory, "spec_speed_hoisting"),
        travelingSpeed = findString(techCategory, "spec_speed_traveling"),
        traversingSpeed = findString(techCategory, "spec_speed_traversing"),
        driveMotorCapacity = findString(techCategory, "motor_capacity_hoisting"),
        hoistingPowerKw = findString(techCategory, "motor_powerKw_hoisting"),
        travelingPowerKw = findString(techCategory, "motor_powerKw_traveling"),
        traversingPowerKw = findString(techCategory, "motor_powerKw_traversing"),
        hoistingType = findString(techCategory, "motor_type_hoisting"),
        travelingType = findString(techCategory, "motor_type_traveling"),
        traversingType = findString(techCategory, "motor_type_traversing"),
        hoistingRpm = findString(techCategory, "motor_rpm_hoisting"),
        travelingRpm = findString(techCategory, "motor_rpm_traveling"),
        traversingRpm = findString(techCategory, "motor_rpm_traversing"),
        hoistingVoltageV = findString(techCategory, "motor_voltageV_hoisting"),
        travelingVoltageV = findString(techCategory, "motor_voltageV_traveling"),
        traversingVoltageV = findString(techCategory, "motor_voltageV_traversing"),
        hoistingCurrentA = findString(techCategory, "motor_currentA_hoisting"),
        travelingCurrentA = findString(techCategory, "motor_currentA_traveling"),
        traversingCurrentA = findString(techCategory, "motor_currentA_traversing"),
        hoistingFrequencyHz = findString(techCategory, "motor_frequencyHz_hoisting"),
        travelingFrequencyHz = findString(techCategory, "motor_frequencyHz_traveling"),
        traversingFrequencyHz = findString(techCategory, "motor_frequencyHz_traversing"),
        hoistingPhase = findString(techCategory, "motor_phase_hoisting"),
        travelingPhase = findString(techCategory, "motor_phase_traveling"),
        traversingPhase = findString(techCategory, "motor_phase_traversing"),
        hoistingPowerSupply = findString(techCategory, "motor_powerSupply_hoisting"),
        travelingPowerSupply = findString(techCategory, "motor_powerSupply_traveling"),
        traversingPowerSupply = findString(techCategory, "motor_powerSupply_traversing"),
        brakeType = findString(techCategory, "brake_type_hoisting"),
        brakeModel = findString(techCategory, "brake_model_hoisting"),
        controlBrakeHoistingType = findString(techCategory, "cBrake_type_hoisting"),
        controlBrakeTravelingType = findString(techCategory, "cBrake_type_traveling"),
        controlBrakeTraversingType = findString(techCategory, "cBrake_type_traversing"),
        controlBrakeHoistingModel = findString(techCategory, "cBrake_model_hoisting"),
        controlBrakeTravelingModel = findString(techCategory, "cBrake_model_traveling"),
        controlBrakeTraversingModel = findString(techCategory, "cBrake_model_traversing"),
        hookHoistingType = findString(techCategory, "hook_type_hoisting"),
        hookTravelingType = findString(techCategory, "hook_type_traveling"),
        hookTraversingType = findString(techCategory, "hook_type_traversing"),
        hookHoistingCapacity = findString(techCategory, "hook_capacity_hoisting"),
        hookTravelingCapacity = findString(techCategory, "hook_capacity_traveling"),
        hookTraversingCapacity = findString(techCategory, "hook_capacity_traversing"),
        hookHoistingMaterial = findString(techCategory, "hook_material_hoisting"),
        hookTravelingMaterial = findString(techCategory, "hook_material_traveling"),
        hookTraversingMaterial = findString(techCategory, "hook_material_traversing"),
        wireRopeOrChainMediumType = if (findString(techCategory, "wireRope_type_hoisting").isNotBlank()) "wireRope" else "chain",
        mediumTypeHoistingType = findString(techCategory, "wireRope_type_hoisting").ifBlank { findString(techCategory, "chain_type_hoisting") },
        mediumTypeTravelingType = findString(techCategory, "wireRope_type_traveling").ifBlank { findString(techCategory, "chain_type_traveling") },
        mediumTypeTraversingType = findString(techCategory, "wireRope_type_traversing").ifBlank { findString(techCategory, "chain_type_traversing") },
        mediumTypeHoistingConstruction = findString(techCategory, "wireRope_construction_hoisting").ifBlank { findString(techCategory, "chain_construction_hoisting") },
        mediumTypeTravelingConstruction = findString(techCategory, "wireRope_construction_traveling").ifBlank { findString(techCategory, "chain_construction_traveling") },
        mediumTypeTraversingConstruction = findString(techCategory, "wireRope_construction_traversing").ifBlank { findString(techCategory, "chain_construction_traversing") },
        mediumTypeHoistingDiameter = findString(techCategory, "wireRope_diameter_hoisting").ifBlank { findString(techCategory, "chain_diameter_hoisting") },
        mediumTypeTravelingDiameter = findString(techCategory, "wireRope_diameter_traveling").ifBlank { findString(techCategory, "chain_diameter_traveling") },
        mediumTypeTraversingDiameter = findString(techCategory, "wireRope_diameter_traversing").ifBlank { findString(techCategory, "chain_diameter_traversing") },
        mediumTypeHoistingLength = findString(techCategory, "wireRope_length_hoisting").ifBlank { findString(techCategory, "chain_length_hoisting") },
        mediumTypeTravelingLength = findString(techCategory, "wireRope_length_traveling").ifBlank { findString(techCategory, "chain_length_traveling") },
        mediumTypeTraversingLength = findString(techCategory, "wireRope_length_traversing").ifBlank { findString(techCategory, "chain_length_traversing") }
    )

    val visualCategory = "visual_inspection"
    fun createVisualItem(itemName: String) = GantryCraneVisualInspectionItem(
        status = findStatus(visualCategory, itemName),
        result = findString(visualCategory, itemName)
    )

    val visualInspection = GantryCraneVisualInspection(
        foundationAndStructure = GantryCraneFoundationAndStructure(
            anchorBolts = GantryCraneAnchorBolts(
                corrosion = createVisualItem("foundationAnchorBoltCorrosion"),
                cracks = createVisualItem("foundationAnchorBoltCracks"),
                deformation = createVisualItem("foundationAnchorBoltDeformation"),
                fastening = createVisualItem("foundationAnchorBoltTightness")
            ),
            columnFrame = GantryCraneColumnFrame(
                corrosion = createVisualItem("columnFrameCorrosion"),
                cracks = createVisualItem("columnFrameCracks"),
                deformation = createVisualItem("columnFrameDeformation"),
                fastening = createVisualItem("columnFrameFastening"),
                transverseReinforcement = createVisualItem("columnFrameCrossBracing"),
                diagonalReinforcement = createVisualItem("columnFrameDiagonalBracing")
            ),
            ladder = GantryCraneLadder(
                corrosion = createVisualItem("ladderCorrosion"),
                cracks = createVisualItem("ladderCracks"),
                deformation = createVisualItem("ladderDeformation"),
                fastening = createVisualItem("ladderFastening")
            ),
            workingFloor = GantryCraneWorkingFloor(
                corrosion = createVisualItem("workPlatformCorrosion"),
                cracks = createVisualItem("workPlatformCracks"),
                deformation = createVisualItem("workPlatformDeformation"),
                fastening = createVisualItem("workPlatformFastening")
            )
        ),
        mechanismAndRail = GantryCraneMechanismAndRail(
            railSupportBeam = GantryCraneRailSupportBeam(
                corrosion = createVisualItem("railMountingBeamCorrosion"),
                cracks = createVisualItem("railMountingBeamCracks"),
                deformation = createVisualItem("railMountingBeamDeformation"),
                fastening = createVisualItem("railMountingBeamFastening")
            ),
            travelingRail = GantryCraneTravelingRail(
                corrosion = createVisualItem("travelingRailCorrosion"),
                cracks = createVisualItem("travelingRailCracks"),
                railConnection = createVisualItem("travelingRailJoint"),
                railAlignment = createVisualItem("travelingRailStraightness"),
                interRailAlignment = createVisualItem("travelingRailAlignmentBetweenRails"),
                interRailFlatness = createVisualItem("travelingRailEvennessBetweenRails"),
                railConnectionGap = createVisualItem("travelingRailGapBetweenRailJoints"),
                railFastener = createVisualItem("travelingRailFastener"),
                railStopper = createVisualItem("travelingRailStopper")
            ),
            traversingRail = GantryCraneTraversingRail(
                corrosion = createVisualItem("traversingRailCorrosion"),
                cracks = createVisualItem("traversingRailCracks"),
                railConnection = createVisualItem("traversingRailJoint"),
                railAlignment = createVisualItem("traversingRailStraightness"),
                interRailAlignment = createVisualItem("traversingRailAlignmentBetweenRails"),
                interRailFlatness = createVisualItem("traversingRailEvennessBetweenRails"),
                railConnectionGap = createVisualItem("traversingRailGapBetweenRailJoints"),
                railFastener = createVisualItem("traversingRailFastener"),
                railStopper = createVisualItem("traversingRailStopper")
            )
        ),
        girderAndTrolley = GantryCraneGirderAndTrolley(
            girder = GantryCraneGirder(
                corrosion = createVisualItem("girderCorrosion"),
                cracks = createVisualItem("girderCracks"),
                camber = createVisualItem("girderCamber"),
                connection = createVisualItem("girderJoint"),
                endGirderConnection = createVisualItem("girderEndJoint"),
                truckMountingOnGirder = createVisualItem("girderTruckMountOnGirder")
            ),
            travelingGearbox = GantryCraneTravelingGearbox(
                corrosion = createVisualItem("travelingGearboxGirderCorrosion"),
                cracks = createVisualItem("travelingGearboxGirderCracks"),
                lubricatingOil = createVisualItem("travelingGearboxGirderLubricatingOil"),
                oilSeal = createVisualItem("travelingGearboxGirderOilSeal")
            ),
            driveWheels = GantryCraneDriveWheels(
                wear = createVisualItem("driveWheelWear"),
                cracks = createVisualItem("driveWheelCracks"),
                deformation = createVisualItem("driveWheelDeformation"),
                flangeCondition = createVisualItem("driveWheelFlangeCondition"),
                chainCondition = createVisualItem("driveWheelChainCondition")
            ),
            idleWheels = GantryCraneIdleWheels(
                safety = createVisualItem("idleWheelSecurity"),
                cracks = createVisualItem("idleWheelCracks"),
                deformation = createVisualItem("idleWheelDeformation"),
                flangeCondition = createVisualItem("idleWheelFlangeCondition")
            ),
            wheelConnector = GantryCraneWheelConnector(
                alignment = createVisualItem("wheelConnectorBogieAxleStraightness"),
                crossJoint = createVisualItem("wheelConnectorBogieAxleCrossJoint"),
                lubrication = createVisualItem("wheelConnectorBogieAxleLubrication")
            ),
            girderStopper = GantryCraneGirderStopper(
                condition = createVisualItem("stopperBumperOnGirderCondition"),
                reinforcement = createVisualItem("stopperBumperOnGirderReinforcement")
            )
        ),
        trolleyMechanism = GantryCraneTrolleyMechanism(
            trolleyTraversingGearbox = GantryCraneTrolleyTraversingGearbox(
                fastening = createVisualItem("traversingGearboxTrolleyCarrierFastening"),
                corrosion = createVisualItem("traversingGearboxTrolleyCarrierCorrosion"),
                cracks = createVisualItem("traversingGearboxTrolleyCarrierCracks"),
                lubricatingOil = createVisualItem("traversingGearboxTrolleyCarrierLubricatingOil"),
                oilSeal = createVisualItem("traversingGearboxTrolleyCarrierOilSeal")
            ),
            trolleyDriveWheels = GantryCraneTrolleyDriveWheels(
                wear = createVisualItem("driveWheelOnTrolleyWear"),
                cracks = createVisualItem("driveWheelOnTrolleyCracks"),
                deformation = createVisualItem("driveWheelOnTrolleyDeformation"),
                flangeCondition = createVisualItem("driveWheelOnTrolleyFlangeCondition"),
                chainCondition = createVisualItem("driveWheelOnTrolleyChainCondition")
            ),
            trolleyIdleWheels = GantryCraneTrolleyIdleWheels(
                wear = createVisualItem("idleWheelOnTrolleyWear"),
                cracks = createVisualItem("idleWheelOnTrolleyCracks"),
                deformation = createVisualItem("idleWheelOnTrolleyDeformation"),
                flangeCondition = createVisualItem("idleWheelOnTrolleyFlangeCondition")
            ),
            trolleyWheelConnector = GantryCraneTrolleyWheelConnector(
                alignment = createVisualItem("wheelConnectorBogieAxleOnTrolleyStraightness"),
                crossJoint = createVisualItem("wheelConnectorBogieAxleOnTrolleyCrossJoint"),
                lubrication = createVisualItem("wheelConnectorBogieAxleOnTrolleyLubrication")
            ),
            trolleyGirderStopper = GantryCraneTrolleyGirderStopper(
                condition = createVisualItem("stopperBumperOnGirderOnTrolleyCondition"),
                reinforcement = createVisualItem("stopperBumperOnGirderOnTrolleyReinforcement")
            )
        ),
        liftingEquipment = GantryCraneLiftingEquipment(
            windingDrum = GantryCraneWindingDrum(
                groove = createVisualItem("windingDrumGroove"),
                grooveLip = createVisualItem("windingDrumGrooveFlange"),
                flanges = createVisualItem("windingDrumFlanges")
            ),
            visualBrakeInspection = GantryCraneVisualBrakeInspection(
                wear = createVisualItem("brakeWear"),
                adjustment = createVisualItem("brakeAdjustment")
            ),
            hoistGearbox = GantryCraneHoistGearbox(
                lubrication = createVisualItem("hoistGearboxLubrication"),
                oilSeal = createVisualItem("hoistGearboxOilSeal")
            ),
            pulleySprocket = GantryCranePulleySprocket(
                pulleyGroove = createVisualItem("pulleyDiscChainSprocketPulleyGroove"),
                pulleyLip = createVisualItem("pulleyDiscChainSprocketPulleyFlange"),
                pulleyPin = createVisualItem("pulleyDiscChainSprocketPulleyPin"),
                bearing = createVisualItem("pulleyDiscChainSprocketBearing"),
                pulleyGuard = createVisualItem("pulleyDiscChainSprocketPulleyGuard"),
                ropeChainGuard = createVisualItem("pulleyDiscChainSprocketWireRopeChainGuard")
            ),
            mainHook = GantryCraneHook(
                wear = createVisualItem("mainHookWear"),
                hookOpeningGap = createVisualItem("mainHookThroatOpening"),
                swivelNutAndBearing = createVisualItem("mainHookSwivelNutAndBearing"),
                trunnion = createVisualItem("mainHookTrunnion")
            ),
            auxiliaryHook = GantryCraneHook(
                wear = createVisualItem("auxiliaryHookWear"),
                hookOpeningGap = createVisualItem("auxiliaryHookThroatOpening"),
                swivelNutAndBearing = createVisualItem("auxiliaryHookSwivelNutAndBearing"),
                trunnion = createVisualItem("auxiliaryHookTrunnion")
            ),
            mainWireRope = GantryCraneWireRope(
                corrosion = createVisualItem("mainWireRopeCorrosion"),
                wear = createVisualItem("mainWireRopeWear"),
                breakage = createVisualItem("mainWireRopeBroken"),
                deformation = createVisualItem("mainWireRopeDeformation")
            ),
            auxiliaryWireRope = GantryCraneWireRope(
                corrosion = createVisualItem("auxiliaryWireRopeCorrosion"),
                wear = createVisualItem("auxiliaryWireRopeWear"),
                breakage = createVisualItem("auxiliaryWireRopeBroken"),
                deformation = createVisualItem("auxiliaryWireRopeDeformation")
            ),
            mainChain = GantryCraneChain(
                corrosion = createVisualItem("mainChainCorrosion"),
                wear = createVisualItem("mainChainWear"),
                crackOrBreakage = createVisualItem("mainChainCrackedBroken"),
                deformation = createVisualItem("mainChainDeformation")
            ),
            auxiliaryChain = GantryCraneChain(
                corrosion = createVisualItem("auxiliaryChainCorrosion"),
                wear = createVisualItem("auxiliaryChainWear"),
                crackOrBreakage = createVisualItem("auxiliaryChainCrackedBroken"),
                deformation = createVisualItem("auxiliaryChainDeformation")
            )
        ),
        controlAndSafetySystem = GantryCraneControlAndSafetySystem(
            limitSwitch = GantryCraneLimitSwitch(
                longTravel = createVisualItem("limitSwitchLsLongTraveling"),
                crossTravel = createVisualItem("limitSwitchLsCrossTraveling"),
                hoist = createVisualItem("limitSwitchLsHoisting")
            ),
            operatorCabin = GantryCraneOperatorCabin(
                safetyLadder = createVisualItem("operatorCabinSafetyLadder"),
                door = createVisualItem("operatorCabinDoor"),
                window = createVisualItem("operatorCabinWindow"),
                fanOrAC = createVisualItem("operatorCabinFanAc"),
                controlLeversOrButtons = createVisualItem("operatorCabinControlLeversButtons"),
                pendantControl = createVisualItem("operatorCabinPendantControl"),
                lighting = createVisualItem("operatorCabinLighting"),
                horn = createVisualItem("operatorCabinHorn"),
                fuseProtection = createVisualItem("operatorCabinFuse"),
                communicationDevice = createVisualItem("operatorCabinCommunicationDevice"),
                fireExtinguisher = createVisualItem("operatorCabinFireExtinguisher"),
                operationalSigns = createVisualItem("operatorCabinOperatingSigns"),
                ignitionOrMasterSwitch = createVisualItem("operatorCabinIgnitionKeyMasterSwitch")
            ),
            electricalComponents = GantryCraneElectricalComponents(
                panelConductorConnector = createVisualItem("electricalComponentsPanelConductorConnector"),
                conductorProtection = createVisualItem("electricalComponentsConductorProtection"),
                motorInstallationSafetySystem = createVisualItem("electricalComponentsMotorInstallationSafetySystem"),
                groundingSystem = createVisualItem("electricalComponentsGroundingSystem"),
                installation = createVisualItem("electricalComponentsInstallation")
            )
        )
    )

    val wireRopeItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_wirerope_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    val wireropeNumber = wireRopeItemCategories.map { index ->
        GantryCraneWireropeNumber(
            wireropeNumber = (index + 1).toString(),
            wireropeUsed = findString("nde_wirerope_item_$index", "usage"),
            dimensionSpec = findString("nde_wirerope_item_$index", "specDiameter"),
            dimensionResult = findString("nde_wirerope_item_$index", "actualDiameter"),
            construction = findString("nde_wirerope_item_$index", "construction"),
            type = findString("nde_wirerope_item_$index", "type"),
            length = findString("nde_wirerope_item_$index", "length"),
            age = findString("nde_wirerope_item_$index", "age"),
            defectAda = findStatus("nde_wirerope_item_$index", "finding"),
            defectTidakAda = !findStatus("nde_wirerope_item_$index", "finding"),
            description = findString("nde_wirerope_item_$index", "finding")
        )
    }

    val girderItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_girder_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    val griderNumber = girderItemCategories.map { index ->
        GantryCraneGriderNumber(
            griderNumber = (index + 1).toString(),
            griderLocation = findString("nde_girder_item_$index", "location"),
            griderAda = findStatus("nde_girder_item_$index", "finding"),
            griderTidakAda = !findStatus("nde_girder_item_$index", "finding"),
            griderDesc = findString("nde_girder_item_$index", "finding")
        )
    }

    val ndeHookCat = "nde_mainHook_measurement"
    val ndt = GantryCraneNdt(
        wireropeMethod = findString("nde_wirerope", "method"),
        wireropeNumber = wireropeNumber,
        hookspecA = findString(ndeHookCat, "spec_a"),
        hookspecB = findString(ndeHookCat, "spec_b"),
        hookspecC = findString(ndeHookCat, "spec_c"),
        hookspecD = findString(ndeHookCat, "spec_d"),
        hookspecE = findString(ndeHookCat, "spec_e"),
        hookspecF = findString(ndeHookCat, "spec_f"),
        hookspecG = findString(ndeHookCat, "spec_g"),
        hookspecH = findString(ndeHookCat, "spec_h"),
        hookspecBaik = findStatus(ndeHookCat, "spec_finding"),
        hookspecTidakBaik = !findStatus(ndeHookCat, "spec_finding"),
        hookspecDesc = findString(ndeHookCat, "spec_finding"),
        measurementResultsA = findString(ndeHookCat, "result_a"),
        measurementResultsB = findString(ndeHookCat, "result_b"),
        measurementResultsC = findString(ndeHookCat, "result_c"),
        measurementResultsD = findString(ndeHookCat, "result_d"),
        measurementResultsE = findString(ndeHookCat, "result_e"),
        measurementResultsF = findString(ndeHookCat, "result_f"),
        measurementResultsG = findString(ndeHookCat, "result_g"),
        measurementResultsH = findString(ndeHookCat, "result_h"),
        measurementResultsBaik = findStatus(ndeHookCat, "result_finding"),
        measurementResultsTidakBaik = !findStatus(ndeHookCat, "result_finding"),
        measurementResultsDesc = findString(ndeHookCat, "result_finding"),
        toleranceA = findString(ndeHookCat, "tolerance_a"),
        toleranceB = findString(ndeHookCat, "tolerance_b"),
        toleranceC = findString(ndeHookCat, "tolerance_c"),
        toleranceD = findString(ndeHookCat, "tolerance_d"),
        toleranceE = findString(ndeHookCat, "tolerance_e"),
        toleranceF = findString(ndeHookCat, "tolerance_f"),
        toleranceG = findString(ndeHookCat, "tolerance_g"),
        toleranceH = findString(ndeHookCat, "tolerance_h"),
        toleranceBaik = findStatus(ndeHookCat, "tolerance_finding"),
        toleranceTidakBaik = !findStatus(ndeHookCat, "tolerance_finding"),
        toleranceDesc = findString(ndeHookCat, "tolerance_finding"),
        griderMethod = findString("nde_girder", "method"),
        griderNumber = griderNumber
    )

    val dynamicWithoutLoadCat = "testing_dynamic_without_load"
    val dynamicWithLoadCat = "testing_dynamic_with_load"
    val dynamicTesting = GantryCraneDynamicTesting(
        travellingStatus = findString(dynamicWithoutLoadCat, "traveling_shouldBe"),
        travellingDesc = findString(dynamicWithoutLoadCat, "traveling_remarks"),
        traversingStatus = findString(dynamicWithoutLoadCat, "traversing_shouldBe"),
        traversingDesc = findString(dynamicWithoutLoadCat, "traversing_remarks"),
        hoistingStatus = findString(dynamicWithoutLoadCat, "hoisting_shouldBe"),
        hoistingDesc = findString(dynamicWithoutLoadCat, "hoisting_remarks"),
        safetyDeviceStatus = findString(dynamicWithoutLoadCat, "safetyDevice_shouldBe"),
        safetyDeviceDesc = findString(dynamicWithoutLoadCat, "safetyDevice_remarks"),
        brakeSwitchStatus = findString(dynamicWithoutLoadCat, "brakeSwitch_shouldBe"),
        brakeSwitchDesc = findString(dynamicWithoutLoadCat, "brakeSwitch_remarks"),
        brakeLockingStatus = findString(dynamicWithoutLoadCat, "brakeLockingDevice_shouldBe"),
        brakeLockingDesc = findString(dynamicWithoutLoadCat, "brakeLockingDevice_remarks"),
        instalasionElectricStatus = findString(dynamicWithoutLoadCat, "electricalInstallation_shouldBe"),
        instalasionElectricDesc = findString(dynamicWithoutLoadCat, "electricalInstallation_remarks"),
        hoist25 = findString(dynamicWithLoadCat, "swl25_hoist"),
        travesing25 = findString(dynamicWithLoadCat, "swl25_traversing"),
        travelling25 = findString(dynamicWithLoadCat, "swl25_travelling"),
        brakeSystem25 = findString(dynamicWithLoadCat, "swl25_brakeSystem"),
        desc25 = findString(dynamicWithLoadCat, "swl25_remarks"),
        hoist50 = findString(dynamicWithLoadCat, "swl50_hoist"),
        travesing50 = findString(dynamicWithLoadCat, "swl50_traversing"),
        travelling50 = findString(dynamicWithLoadCat, "swl50_travelling"),
        brakeSystem50 = findString(dynamicWithLoadCat, "swl50_brakeSystem"),
        desc50 = findString(dynamicWithLoadCat, "swl50_remarks"),
        hoist75 = findString(dynamicWithLoadCat, "swl75_hoist"),
        travesing75 = findString(dynamicWithLoadCat, "swl75_traversing"),
        travelling75 = findString(dynamicWithLoadCat, "swl75_travelling"),
        brakeSystem75 = findString(dynamicWithLoadCat, "swl75_brakeSystem"),
        desc75 = findString(dynamicWithLoadCat, "swl75_remarks"),
        hoist100 = findString(dynamicWithLoadCat, "swl100_hoist"),
        travesing100 = findString(dynamicWithLoadCat, "swl100_traversing"),
        travelling100 = findString(dynamicWithLoadCat, "swl100_travelling"),
        brakeSystem100 = findString(dynamicWithLoadCat, "swl100_brakeSystem"),
        desc100 = findString(dynamicWithLoadCat, "swl100_remarks")
    )

    val staticCat = "testing_static"
    val staticDeflectionCategories = this.checkItems.map { it.category }.filter { it.startsWith("testing_static_deflection_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    val defleksiPosision = staticDeflectionCategories.map { index ->
        GantryCraneDefleksiPosision(
            defleksiPosision = findString("testing_static_deflection_item_$index", "position"),
            defleksiMeasurements = findString("testing_static_deflection_item_$index", "deflection"),
            defleksiStandard = findString("testing_static_deflection_item_$index", "maxStandard"),
            defleksiDesc = findString("testing_static_deflection_item_$index", "remarks")
        )
    }
    val staticTesting = GantryCraneStaticTesting(
        loadTest = findString(staticCat, "load"),
        basedDesign = findString(staticCat, "deflection_designBased"),
        lengthSpan = findString(staticCat, "deflection_spanLength"),
        xspan = findString(staticCat, "deflection_calculation"),
        resultDefleksi = findStatus(staticCat, "deflectionResult"),
        defleksiPosision = defleksiPosision
    )

    val conclusion = this.findings.filter { it.type == FindingType.FINDING }.joinToString("\n") { it.description }
    val recomendation = this.findings.filter { it.type == FindingType.RECOMMENDATION }.joinToString("\n") { it.description }

    return GantryCraneReportRequest(
        examinationType = this.inspection.examinationType,
        inspectionType = this.inspection.inspectionType.name,
        createdAt = this.inspection.createdAt ?: "",
        extraId = this.inspection.extraId.toLongOrNull() ?: 0L,
        equipmentType = this.inspection.equipmentType,
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        ndt = ndt,
        dynamicTesting = dynamicTesting,
        staticTesting = staticTesting,
        conclusion = conclusion,
        recomendation = recomendation
    )
}


/**
 * Converts [GantryCraneReportData] (from the API) into [InspectionWithDetailsDomain] (for the data layer).
 * This mapping reconstructs the domain model from the DTO by flattening all data into check items.
 */
fun GantryCraneReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.id.toLongOrNull() ?: 0L

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId.toString(),
        moreExtraId = "",
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Gantry_Crane,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.location,
        serialNumber = this.generalData.serialNumber,
        permitNumber = this.generalData.usagePermitNumber,
        capacity = this.generalData.maxLiftingCapacityKg,
        manufacturer = ManufacturerDomain(
            name = "${this.generalData.manufacturerHoist} / ${this.generalData.manufacturerStructure}",
            brandOrType = this.generalData.brandOrType,
            year = this.generalData.manufactureYear
        ),
        createdAt = this.createdAt,
        reportDate = this.generalData.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun add(category: String, itemName: String, status: Boolean, result: String) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = status, result = result))
    }
    fun add(category: String, itemName: String, value: String) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = false, result = value))
    }

    // General Data
    add("general_data", "operatorCertificate", this.generalData.operatorCertificateStatus)
    add("general_data", "technicalDataManual", this.generalData.technicalDataManualStatus)

    // Technical Data
    val techCategory = "technical_data"
    add(techCategory, "spec_liftingHeight_hoisting", this.technicalData.liftHeight)
    add(techCategory, "spec_girderLength_hoisting", this.technicalData.girderLength)
    add(techCategory, "spec_speed_hoisting", this.technicalData.hoistingSpeed)
    add(techCategory, "spec_speed_traveling", this.technicalData.travelingSpeed)
    add(techCategory, "spec_speed_traversing", this.technicalData.traversingSpeed)
    add(techCategory, "motor_capacity_hoisting", this.technicalData.driveMotorCapacity)
    add(techCategory, "motor_powerKw_hoisting", this.technicalData.hoistingPowerKw)
    add(techCategory, "motor_powerKw_traveling", this.technicalData.travelingPowerKw)
    add(techCategory, "motor_powerKw_traversing", this.technicalData.traversingPowerKw)
    add(techCategory, "motor_type_hoisting", this.technicalData.hoistingType)
    add(techCategory, "motor_type_traveling", this.technicalData.travelingType)
    add(techCategory, "motor_type_traversing", this.technicalData.traversingType)
    add(techCategory, "motor_rpm_hoisting", this.technicalData.hoistingRpm)
    add(techCategory, "motor_rpm_traveling", this.technicalData.travelingRpm)
    add(techCategory, "motor_rpm_traversing", this.technicalData.traversingRpm)
    add(techCategory, "motor_voltageV_hoisting", this.technicalData.hoistingVoltageV)
    add(techCategory, "motor_voltageV_traveling", this.technicalData.travelingVoltageV)
    add(techCategory, "motor_voltageV_traversing", this.technicalData.traversingVoltageV)
    add(techCategory, "motor_currentA_hoisting", this.technicalData.hoistingCurrentA)
    add(techCategory, "motor_currentA_traveling", this.technicalData.travelingCurrentA)
    add(techCategory, "motor_currentA_traversing", this.technicalData.traversingCurrentA)
    add(techCategory, "motor_frequencyHz_hoisting", this.technicalData.hoistingFrequencyHz)
    add(techCategory, "motor_frequencyHz_traveling", this.technicalData.travelingFrequencyHz)
    add(techCategory, "motor_frequencyHz_traversing", this.technicalData.traversingFrequencyHz)
    add(techCategory, "motor_phase_hoisting", this.technicalData.hoistingPhase)
    add(techCategory, "motor_phase_traveling", this.technicalData.travelingPhase)
    add(techCategory, "motor_phase_traversing", this.technicalData.traversingPhase)
    add(techCategory, "motor_powerSupply_hoisting", this.technicalData.hoistingPowerSupply)
    add(techCategory, "motor_powerSupply_traveling", this.technicalData.travelingPowerSupply)
    add(techCategory, "motor_powerSupply_traversing", this.technicalData.traversingPowerSupply)
    add(techCategory, "brake_type_hoisting", this.technicalData.brakeType)
    add(techCategory, "brake_model_hoisting", this.technicalData.brakeModel)
    add(techCategory, "cBrake_type_hoisting", this.technicalData.controlBrakeHoistingType)
    add(techCategory, "cBrake_type_traveling", this.technicalData.controlBrakeTravelingType)
    add(techCategory, "cBrake_type_traversing", this.technicalData.controlBrakeTraversingType)
    add(techCategory, "cBrake_model_hoisting", this.technicalData.controlBrakeHoistingModel)
    add(techCategory, "cBrake_model_traveling", this.technicalData.controlBrakeTravelingModel)
    add(techCategory, "cBrake_model_traversing", this.technicalData.controlBrakeTraversingModel)
    add(techCategory, "hook_type_hoisting", this.technicalData.hookHoistingType)
    add(techCategory, "hook_type_traveling", this.technicalData.hookTravelingType)
    add(techCategory, "hook_type_traversing", this.technicalData.hookTraversingType)
    add(techCategory, "hook_capacity_hoisting", this.technicalData.hookHoistingCapacity)
    add(techCategory, "hook_capacity_traveling", this.technicalData.hookTravelingCapacity)
    add(techCategory, "hook_capacity_traversing", this.technicalData.hookTraversingCapacity)
    add(techCategory, "hook_material_hoisting", this.technicalData.hookHoistingMaterial)
    add(techCategory, "hook_material_traveling", this.technicalData.hookTravelingMaterial)
    add(techCategory, "hook_material_traversing", this.technicalData.hookTraversingMaterial)
    add(techCategory, "wireRope_type_hoisting", this.technicalData.mediumTypeHoistingType)
    add(techCategory, "wireRope_type_traveling", this.technicalData.mediumTypeTravelingType)
    add(techCategory, "wireRope_type_traversing", this.technicalData.mediumTypeTraversingType)
    add(techCategory, "wireRope_construction_hoisting", this.technicalData.mediumTypeHoistingConstruction)
    add(techCategory, "wireRope_construction_traveling", this.technicalData.mediumTypeTravelingConstruction) // FIXED
    add(techCategory, "wireRope_construction_traversing", this.technicalData.mediumTypeTraversingConstruction)
    add(techCategory, "wireRope_diameter_hoisting", this.technicalData.mediumTypeHoistingDiameter)
    add(techCategory, "wireRope_diameter_traveling", this.technicalData.mediumTypeTravelingDiameter)
    add(techCategory, "wireRope_diameter_traversing", this.technicalData.mediumTypeTraversingDiameter)
    add(techCategory, "wireRope_length_hoisting", this.technicalData.mediumTypeHoistingLength)
    add(techCategory, "wireRope_length_traveling", this.technicalData.mediumTypeTravelingLength)
    add(techCategory, "wireRope_length_traversing", this.technicalData.mediumTypeTraversingLength)

    val visualCategory = "visual_inspection"
    fun addVisualItem(itemName: String, item: GantryCraneVisualInspectionItem) {
        add(visualCategory, itemName, item.status, item.result)
    }

    this.visualInspection.let { vis ->
        vis.foundationAndStructure.let { fas ->
            fas.anchorBolts.let {
                addVisualItem("foundationAnchorBoltCorrosion", it.corrosion)
                addVisualItem("foundationAnchorBoltCracks", it.cracks)
                addVisualItem("foundationAnchorBoltDeformation", it.deformation)
                addVisualItem("foundationAnchorBoltTightness", it.fastening)
            }
            fas.columnFrame.let {
                addVisualItem("columnFrameCorrosion", it.corrosion)
                addVisualItem("columnFrameCracks", it.cracks)
                addVisualItem("columnFrameDeformation", it.deformation)
                addVisualItem("columnFrameFastening", it.fastening)
                addVisualItem("columnFrameCrossBracing", it.transverseReinforcement)
                addVisualItem("columnFrameDiagonalBracing", it.diagonalReinforcement)
            }
            fas.ladder.let {
                addVisualItem("ladderCorrosion", it.corrosion)
                addVisualItem("ladderCracks", it.cracks)
                addVisualItem("ladderDeformation", it.deformation)
                addVisualItem("ladderFastening", it.fastening)
            }
            fas.workingFloor.let {
                addVisualItem("workPlatformCorrosion", it.corrosion)
                addVisualItem("workPlatformCracks", it.cracks)
                addVisualItem("workPlatformDeformation", it.deformation)
                addVisualItem("workPlatformFastening", it.fastening)
            }
        }
        vis.mechanismAndRail.let { mar ->
            mar.railSupportBeam.let {
                addVisualItem("railMountingBeamCorrosion", it.corrosion)
                addVisualItem("railMountingBeamCracks", it.cracks)
                addVisualItem("railMountingBeamDeformation", it.deformation)
                addVisualItem("railMountingBeamFastening", it.fastening)
            }
            mar.travelingRail.let {
                addVisualItem("travelingRailCorrosion", it.corrosion)
                addVisualItem("travelingRailCracks", it.cracks)
                addVisualItem("travelingRailJoint", it.railConnection)
                addVisualItem("travelingRailStraightness", it.railAlignment)
                addVisualItem("travelingRailAlignmentBetweenRails", it.interRailAlignment)
                addVisualItem("travelingRailEvennessBetweenRails", it.interRailFlatness)
                addVisualItem("travelingRailGapBetweenRailJoints", it.railConnectionGap)
                addVisualItem("travelingRailFastener", it.railFastener)
                addVisualItem("travelingRailStopper", it.railStopper)
            }
            mar.traversingRail.let {
                addVisualItem("traversingRailCorrosion", it.corrosion)
                addVisualItem("traversingRailCracks", it.cracks)
                addVisualItem("traversingRailJoint", it.railConnection)
                addVisualItem("traversingRailStraightness", it.railAlignment)
                addVisualItem("traversingRailAlignmentBetweenRails", it.interRailAlignment)
                addVisualItem("traversingRailEvennessBetweenRails", it.interRailFlatness)
                addVisualItem("traversingRailGapBetweenRailJoints", it.railConnectionGap)
                addVisualItem("traversingRailFastener", it.railFastener)
                addVisualItem("traversingRailStopper", it.railStopper)
            }
        }
        vis.girderAndTrolley.let { gat ->
            gat.girder.let {
                addVisualItem("girderCorrosion", it.corrosion)
                addVisualItem("girderCracks", it.cracks)
                addVisualItem("girderCamber", it.camber)
                addVisualItem("girderJoint", it.connection)
                addVisualItem("girderEndJoint", it.endGirderConnection)
                addVisualItem("girderTruckMountOnGirder", it.truckMountingOnGirder)
            }
            gat.travelingGearbox.let {
                addVisualItem("travelingGearboxGirderCorrosion", it.corrosion)
                addVisualItem("travelingGearboxGirderCracks", it.cracks)
                addVisualItem("travelingGearboxGirderLubricatingOil", it.lubricatingOil)
                addVisualItem("travelingGearboxGirderOilSeal", it.oilSeal)
            }
            gat.driveWheels.let {
                addVisualItem("driveWheelWear", it.wear)
                addVisualItem("driveWheelCracks", it.cracks)
                addVisualItem("driveWheelDeformation", it.deformation)
                addVisualItem("driveWheelFlangeCondition", it.flangeCondition)
                addVisualItem("driveWheelChainCondition", it.chainCondition)
            }
            gat.idleWheels.let {
                addVisualItem("idleWheelSecurity", it.safety)
                addVisualItem("idleWheelCracks", it.cracks)
                addVisualItem("idleWheelDeformation", it.deformation)
                addVisualItem("idleWheelFlangeCondition", it.flangeCondition)
            }
            gat.wheelConnector.let {
                addVisualItem("wheelConnectorBogieAxleStraightness", it.alignment)
                addVisualItem("wheelConnectorBogieAxleCrossJoint", it.crossJoint)
                addVisualItem("wheelConnectorBogieAxleLubrication", it.lubrication)
            }
            gat.girderStopper.let {
                addVisualItem("stopperBumperOnGirderCondition", it.condition)
                addVisualItem("stopperBumperOnGirderReinforcement", it.reinforcement)
            }
        }
        vis.trolleyMechanism.let { tm ->
            tm.trolleyTraversingGearbox.let {
                addVisualItem("traversingGearboxTrolleyCarrierFastening", it.fastening)
                addVisualItem("traversingGearboxTrolleyCarrierCorrosion", it.corrosion)
                addVisualItem("traversingGearboxTrolleyCarrierCracks", it.cracks)
                addVisualItem("traversingGearboxTrolleyCarrierLubricatingOil", it.lubricatingOil)
                addVisualItem("traversingGearboxTrolleyCarrierOilSeal", it.oilSeal)
            }
            tm.trolleyDriveWheels.let {
                addVisualItem("driveWheelOnTrolleyWear", it.wear)
                addVisualItem("driveWheelOnTrolleyCracks", it.cracks)
                addVisualItem("driveWheelOnTrolleyDeformation", it.deformation)
                addVisualItem("driveWheelOnTrolleyFlangeCondition", it.flangeCondition)
                addVisualItem("driveWheelOnTrolleyChainCondition", it.chainCondition)
            }
            tm.trolleyIdleWheels.let {
                addVisualItem("idleWheelOnTrolleyWear", it.wear)
                addVisualItem("idleWheelOnTrolleyCracks", it.cracks)
                addVisualItem("idleWheelOnTrolleyDeformation", it.deformation)
                addVisualItem("idleWheelOnTrolleyFlangeCondition", it.flangeCondition)
            }
            tm.trolleyWheelConnector.let {
                addVisualItem("wheelConnectorBogieAxleOnTrolleyStraightness", it.alignment)
                addVisualItem("wheelConnectorBogieAxleOnTrolleyCrossJoint", it.crossJoint)
                addVisualItem("wheelConnectorBogieAxleOnTrolleyLubrication", it.lubrication)
            }
            tm.trolleyGirderStopper.let {
                addVisualItem("stopperBumperOnGirderOnTrolleyCondition", it.condition)
                addVisualItem("stopperBumperOnGirderOnTrolleyReinforcement", it.reinforcement)
            }
        }
        vis.liftingEquipment.let { le ->
            le.windingDrum.let {
                addVisualItem("windingDrumGroove", it.groove)
                addVisualItem("windingDrumGrooveFlange", it.grooveLip)
                addVisualItem("windingDrumFlanges", it.flanges)
            }
            le.visualBrakeInspection.let {
                addVisualItem("brakeWear", it.wear)
                addVisualItem("brakeAdjustment", it.adjustment)
            }
            le.hoistGearbox.let {
                addVisualItem("hoistGearboxLubrication", it.lubrication)
                addVisualItem("hoistGearboxOilSeal", it.oilSeal)
            }
            le.pulleySprocket.let {
                addVisualItem("pulleyDiscChainSprocketPulleyGroove", it.pulleyGroove)
                addVisualItem("pulleyDiscChainSprocketPulleyFlange", it.pulleyLip)
                addVisualItem("pulleyDiscChainSprocketPulleyPin", it.pulleyPin)
                addVisualItem("pulleyDiscChainSprocketBearing", it.bearing)
                addVisualItem("pulleyDiscChainSprocketPulleyGuard", it.pulleyGuard)
                addVisualItem("pulleyDiscChainSprocketWireRopeChainGuard", it.ropeChainGuard)
            }
            le.mainHook.let {
                addVisualItem("mainHookWear", it.wear)
                addVisualItem("mainHookThroatOpening", it.hookOpeningGap)
                addVisualItem("mainHookSwivelNutAndBearing", it.swivelNutAndBearing)
                addVisualItem("mainHookTrunnion", it.trunnion)
            }
            le.auxiliaryHook.let {
                addVisualItem("auxiliaryHookWear", it.wear)
                addVisualItem("auxiliaryHookThroatOpening", it.hookOpeningGap)
                addVisualItem("auxiliaryHookSwivelNutAndBearing", it.swivelNutAndBearing)
                addVisualItem("auxiliaryHookTrunnion", it.trunnion)
            }
            le.mainWireRope.let {
                addVisualItem("mainWireRopeCorrosion", it.corrosion)
                addVisualItem("mainWireRopeWear", it.wear)
                addVisualItem("mainWireRopeBroken", it.breakage)
                addVisualItem("mainWireRopeDeformation", it.deformation)
            }
            le.auxiliaryWireRope.let {
                addVisualItem("auxiliaryWireRopeCorrosion", it.corrosion)
                addVisualItem("auxiliaryWireRopeWear", it.wear)
                addVisualItem("auxiliaryWireRopeBroken", it.breakage)
                addVisualItem("auxiliaryWireRopeDeformation", it.deformation)
            }
            le.mainChain.let {
                addVisualItem("mainChainCorrosion", it.corrosion)
                addVisualItem("mainChainWear", it.wear)
                addVisualItem("mainChainCrackedBroken", it.crackOrBreakage)
                addVisualItem("mainChainDeformation", it.deformation)
            }
            le.auxiliaryChain.let {
                addVisualItem("auxiliaryChainCorrosion", it.corrosion)
                addVisualItem("auxiliaryChainWear", it.wear)
                addVisualItem("auxiliaryChainCrackedBroken", it.crackOrBreakage)
                addVisualItem("auxiliaryChainDeformation", it.deformation)
            }
        }
        vis.controlAndSafetySystem.let { cs ->
            cs.limitSwitch.let {
                addVisualItem("limitSwitchLsLongTraveling", it.longTravel)
                addVisualItem("limitSwitchLsCrossTraveling", it.crossTravel)
                addVisualItem("limitSwitchLsHoisting", it.hoist)
            }
            cs.operatorCabin.let {
                addVisualItem("operatorCabinSafetyLadder", it.safetyLadder)
                addVisualItem("operatorCabinDoor", it.door)
                addVisualItem("operatorCabinWindow", it.window)
                addVisualItem("operatorCabinFanAc", it.fanOrAC)
                addVisualItem("operatorCabinControlLeversButtons", it.controlLeversOrButtons)
                addVisualItem("operatorCabinPendantControl", it.pendantControl)
                addVisualItem("operatorCabinLighting", it.lighting)
                addVisualItem("operatorCabinHorn", it.horn)
                addVisualItem("operatorCabinFuse", it.fuseProtection)
                addVisualItem("operatorCabinCommunicationDevice", it.communicationDevice)
                addVisualItem("operatorCabinFireExtinguisher", it.fireExtinguisher)
                addVisualItem("operatorCabinOperatingSigns", it.operationalSigns)
                addVisualItem("operatorCabinIgnitionKeyMasterSwitch", it.ignitionOrMasterSwitch)
            }
            cs.electricalComponents.let {
                addVisualItem("electricalComponentsPanelConductorConnector", it.panelConductorConnector)
                addVisualItem("electricalComponentsConductorProtection", it.conductorProtection)
                addVisualItem("electricalComponentsMotorInstallationSafetySystem", it.motorInstallationSafetySystem)
                addVisualItem("electricalComponentsGroundingSystem", it.groundingSystem)
                addVisualItem("electricalComponentsInstallation", it.installation)
            }
        }
    }


    // NDT
    add("nde_wirerope", "method", this.ndt.wireropeMethod)
    this.ndt.wireropeNumber.forEachIndexed { index, item ->
        val cat = "nde_wirerope_item_$index"
        add(cat, "usage", item.wireropeUsed)
        add(cat, "specDiameter", item.dimensionSpec)
        add(cat, "actualDiameter", item.dimensionResult)
        add(cat, "construction", item.construction)
        add(cat, "type", item.type)
        add(cat, "length", item.length)
        add(cat, "age", item.age)
        add(cat, "finding", item.defectAda, item.description)
    }
    val ndeHookCat = "nde_mainHook_measurement"
    add(ndeHookCat, "spec_a", this.ndt.hookspecA)
    add(ndeHookCat, "spec_b", this.ndt.hookspecB)
    add(ndeHookCat, "spec_c", this.ndt.hookspecC)
    add(ndeHookCat, "spec_d", this.ndt.hookspecD)
    add(ndeHookCat, "spec_e", this.ndt.hookspecE)
    add(ndeHookCat, "spec_f", this.ndt.hookspecF)
    add(ndeHookCat, "spec_g", this.ndt.hookspecG)
    add(ndeHookCat, "spec_h", this.ndt.hookspecH)
    add(ndeHookCat, "spec_finding", this.ndt.hookspecBaik, this.ndt.hookspecDesc)
    add(ndeHookCat, "result_a", this.ndt.measurementResultsA)
    add(ndeHookCat, "result_b", this.ndt.measurementResultsB)
    add(ndeHookCat, "result_c", this.ndt.measurementResultsC)
    add(ndeHookCat, "result_d", this.ndt.measurementResultsD)
    add(ndeHookCat, "result_e", this.ndt.measurementResultsE)
    add(ndeHookCat, "result_f", this.ndt.measurementResultsF)
    add(ndeHookCat, "result_g", this.ndt.measurementResultsG)
    add(ndeHookCat, "result_h", this.ndt.measurementResultsH)
    add(ndeHookCat, "result_finding", this.ndt.measurementResultsBaik, this.ndt.measurementResultsDesc)
    add(ndeHookCat, "tolerance_a", this.ndt.toleranceA)
    add(ndeHookCat, "tolerance_b", this.ndt.toleranceB)
    add(ndeHookCat, "tolerance_c", this.ndt.toleranceC)
    add(ndeHookCat, "tolerance_d", this.ndt.toleranceD)
    add(ndeHookCat, "tolerance_e", this.ndt.toleranceE)
    add(ndeHookCat, "tolerance_f", this.ndt.toleranceF)
    add(ndeHookCat, "tolerance_g", this.ndt.toleranceG)
    add(ndeHookCat, "tolerance_h", this.ndt.toleranceH)
    add(ndeHookCat, "tolerance_finding", this.ndt.toleranceBaik, this.ndt.toleranceDesc)
    add("nde_girder", "method", this.ndt.griderMethod)
    this.ndt.griderNumber.forEachIndexed { index, item ->
        val cat = "nde_girder_item_$index"
        add(cat, "location", item.griderLocation)
        add(cat, "finding", item.griderAda, item.griderDesc)
    }

    // Testing
    val dynamicWithoutLoadCat = "testing_dynamic_without_load"
    add(dynamicWithoutLoadCat, "traveling_shouldBe", this.dynamicTesting.travellingStatus)
    add(dynamicWithoutLoadCat, "traveling_remarks", this.dynamicTesting.travellingDesc)
    add(dynamicWithoutLoadCat, "traversing_shouldBe", this.dynamicTesting.traversingStatus)
    add(dynamicWithoutLoadCat, "traversing_remarks", this.dynamicTesting.traversingDesc)
    add(dynamicWithoutLoadCat, "hoisting_shouldBe", this.dynamicTesting.hoistingStatus)
    add(dynamicWithoutLoadCat, "hoisting_remarks", this.dynamicTesting.hoistingDesc)
    add(dynamicWithoutLoadCat, "safetyDevice_shouldBe", this.dynamicTesting.safetyDeviceStatus)
    add(dynamicWithoutLoadCat, "safetyDevice_remarks", this.dynamicTesting.safetyDeviceDesc)
    add(dynamicWithoutLoadCat, "brakeSwitch_shouldBe", this.dynamicTesting.brakeSwitchStatus)
    add(dynamicWithoutLoadCat, "brakeSwitch_remarks", this.dynamicTesting.brakeSwitchDesc)
    add(dynamicWithoutLoadCat, "brakeLockingDevice_shouldBe", this.dynamicTesting.brakeLockingStatus)
    add(dynamicWithoutLoadCat, "brakeLockingDevice_remarks", this.dynamicTesting.brakeLockingDesc)
    add(dynamicWithoutLoadCat, "electricalInstallation_shouldBe", this.dynamicTesting.instalasionElectricStatus)
    add(dynamicWithoutLoadCat, "electricalInstallation_remarks", this.dynamicTesting.instalasionElectricDesc)

    val dynamicWithLoadCat = "testing_dynamic_with_load"
    add(dynamicWithLoadCat, "swl25_hoist", this.dynamicTesting.hoist25)
    add(dynamicWithLoadCat, "swl25_traversing", this.dynamicTesting.travesing25)
    add(dynamicWithLoadCat, "swl25_travelling", this.dynamicTesting.travelling25)
    add(dynamicWithLoadCat, "swl25_brakeSystem", this.dynamicTesting.brakeSystem25)
    add(dynamicWithLoadCat, "swl25_remarks", this.dynamicTesting.desc25)
    add(dynamicWithLoadCat, "swl50_hoist", this.dynamicTesting.hoist50)
    add(dynamicWithLoadCat, "swl50_traversing", this.dynamicTesting.travesing50)
    add(dynamicWithLoadCat, "swl50_travelling", this.dynamicTesting.travelling50)
    add(dynamicWithLoadCat, "swl50_brakeSystem", this.dynamicTesting.brakeSystem50)
    add(dynamicWithLoadCat, "swl50_remarks", this.dynamicTesting.desc50)
    add(dynamicWithLoadCat, "swl75_hoist", this.dynamicTesting.hoist75)
    add(dynamicWithLoadCat, "swl75_traversing", this.dynamicTesting.travesing75)
    add(dynamicWithLoadCat, "swl75_travelling", this.dynamicTesting.travelling75)
    add(dynamicWithLoadCat, "swl75_brakeSystem", this.dynamicTesting.brakeSystem75)
    add(dynamicWithLoadCat, "swl75_remarks", this.dynamicTesting.desc75)
    add(dynamicWithLoadCat, "swl100_hoist", this.dynamicTesting.hoist100)
    add(dynamicWithLoadCat, "swl100_traversing", this.dynamicTesting.travesing100)
    add(dynamicWithLoadCat, "swl100_travelling", this.dynamicTesting.travelling100)
    add(dynamicWithLoadCat, "swl100_brakeSystem", this.dynamicTesting.brakeSystem100)
    add(dynamicWithLoadCat, "swl100_remarks", this.dynamicTesting.desc100)

    val staticCat = "testing_static"
    add(staticCat, "load", this.staticTesting.loadTest)
    add(staticCat, "deflection_designBased", this.staticTesting.basedDesign)
    add(staticCat, "deflection_spanLength", this.staticTesting.lengthSpan)
    add(staticCat, "deflection_calculation", this.staticTesting.xspan)
    add(staticCat, "deflectionResult", this.staticTesting.resultDefleksi, "")
    this.staticTesting.defleksiPosision.forEachIndexed { index, item ->
        val cat = "testing_static_deflection_item_$index"
        add(cat, "position", item.defleksiPosision)
        add(cat, "deflection", item.defleksiMeasurements)
        add(cat, "maxStandard", item.defleksiStandard)
        add(cat, "remarks", item.defleksiDesc)
    }

    val findings = this.conclusion.split("\n").filter { it.isNotBlank() }.map {
        InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING)
    }
    val recommendations = this.recomendation.split("\n").filter { it.isNotBlank() }.map {
        InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION)
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings + recommendations,
        testResults = emptyList()
    )
}


// =================================================================================================
//                                  BAP DTO <-> Domain Model
// =================================================================================================

/**
 * Converts [GantryCraneBapReportData] (from the API) into [InspectionWithDetailsDomain] (for the data layer).
 * This mapping reconstructs the domain model from the DTO.
 */
fun GantryCraneBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.id.toLongOrNull() ?: 0L

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId.toString(),
        moreExtraId = "",
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Gantry_Crane,
        equipmentType = "",
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.location,
        serialNumber = this.technicalData.serialNumber,
        capacity = this.technicalData.maxLiftingCapacityKg,
        speed = this.technicalData.liftingSpeedMpm,
        manufacturer = ManufacturerDomain(
            name = "${this.technicalData.manufacturerHoist} / ${this.technicalData.manufactureStructure}",
            brandOrType = this.technicalData.brandOrType,
            year = "${this.technicalData.manufactureCountry} - ${this.technicalData.manufactureYear}"
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    // Map Visual Check
    this.inspectionResult.visualCheck.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.VISUAL_INSPECTION, itemName = "Struktur Utama Baik", status = it.isMainStructureGood))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.VISUAL_INSPECTION, itemName = "Baut dan Mur Terpasang Kencang", status = it.areBoltsAndNutsSecure))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.VISUAL_INSPECTION, itemName = "Kondisi Wire Rope Baik", status = it.isWireRopeGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.VISUAL_INSPECTION, itemName = "Kondisi Hook Baik", status = it.isHookGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.VISUAL_INSPECTION, itemName = "Kondisi Gearbox Baik", status = it.isGearboxGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.VISUAL_INSPECTION, itemName = "Terdapat Kebocoran Oli Gearbox", status = it.hasGearboxOilLeak))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.VISUAL_INSPECTION, itemName = "Kondisi Lampu Peringatan Baik", status = it.isWarningLampGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.VISUAL_INSPECTION, itemName = "Penandaan Kapasitas Terpasang", status = it.isCapacityMarkingDisplayed))
    }

    // Map Functional Test
    this.inspectionResult.functionalTest.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.FUNCTIONAL_TEST, itemName = "Fungsi Maju Mundur OK", status = it.isForwardReverseFunctionOk))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.FUNCTIONAL_TEST, itemName = "Fungsi Hoisting OK", status = it.isHoistingFunctionOk))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.FUNCTIONAL_TEST, itemName = "Limit Switch Berfungsi", status = it.isLimitSwitchFunctional))
    }

    // Map NDT Test
    this.inspectionResult.ndtTest.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.NDT_TEST, itemName = "Metode", status = true, result = it.method))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.NDT_TEST, itemName = "Hasil Baik", status = it.isNdtResultGood))
    }

    // Map Load Test
    this.inspectionResult.loadTest.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.LOAD_TEST, itemName = "Beban (kg)", status = true, result = it.loadKg))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.LOAD_TEST, itemName = "Tinggi Angkat (meter)", status = true, result = it.liftHeightMeters))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.LOAD_TEST, itemName = "Waktu Tahan (detik)", status = true, result = it.holdTimeSeconds))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategoryGantryCrane.LOAD_TEST, itemName = "Hasil Baik", status = it.isLoadTestResultGood))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = emptyList()
    )
}

/**
 * Converts [InspectionWithDetailsDomain] (from the data layer) into [GantryCraneBapRequest] (for API submission).
 * This mapping flattens the domain model into a request DTO.
 */
fun InspectionWithDetailsDomain.toGantryCraneBapRequest(): GantryCraneBapRequest {
    val checkItemsMap = this.checkItems.associateBy { it.category to it.itemName }

    fun findBool(category: String, itemName: String): Boolean {
        return checkItemsMap[category to itemName]?.status ?: false
    }

    fun findString(category: String, itemName: String): String {
        return checkItemsMap[category to itemName]?.result ?: ""
    }

    val generalData = GantryCraneBapGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        location = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = GantryCraneBapTechnicalData(
        brandOrType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturerHoist = this.inspection.manufacturer?.name?.split(" / ")?.getOrNull(0) ?: "",
        manufactureStructure = this.inspection.manufacturer?.name?.split(" / ")?.getOrNull(1) ?: "",
        manufactureYear = this.inspection.manufacturer?.year?.split(" - ")?.getOrNull(1) ?: "",
        manufactureCountry = this.inspection.manufacturer?.year?.split(" - ")?.getOrNull(0) ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        maxLiftingCapacityKg = this.inspection.capacity ?: "",
        liftingSpeedMpm = this.inspection.speed ?: ""
    )

    val visualCheck = GantryCraneBapVisualCheck(
        isMainStructureGood = findBool(BAPCategoryGantryCrane.VISUAL_INSPECTION, "Struktur Utama Baik"),
        areBoltsAndNutsSecure = findBool(BAPCategoryGantryCrane.VISUAL_INSPECTION, "Baut dan Mur Terpasang Kencang"),
        isWireRopeGoodCondition = findBool(BAPCategoryGantryCrane.VISUAL_INSPECTION, "Kondisi Wire Rope Baik"),
        isHookGoodCondition = findBool(BAPCategoryGantryCrane.VISUAL_INSPECTION, "Kondisi Hook Baik"),
        isGearboxGoodCondition = findBool(BAPCategoryGantryCrane.VISUAL_INSPECTION, "Kondisi Gearbox Baik"),
        hasGearboxOilLeak = findBool(BAPCategoryGantryCrane.VISUAL_INSPECTION, "Terdapat Kebocoran Oli Gearbox"),
        isWarningLampGoodCondition = findBool(BAPCategoryGantryCrane.VISUAL_INSPECTION, "Kondisi Lampu Peringatan Baik"),
        isCapacityMarkingDisplayed = findBool(BAPCategoryGantryCrane.VISUAL_INSPECTION, "Penandaan Kapasitas Terpasang")
    )

    val functionalTest = GantryCraneBapFunctionalTest(
        isForwardReverseFunctionOk = findBool(BAPCategoryGantryCrane.FUNCTIONAL_TEST, "Fungsi Maju Mundur OK"),
        isHoistingFunctionOk = findBool(BAPCategoryGantryCrane.FUNCTIONAL_TEST, "Fungsi Hoisting OK"),
        isLimitSwitchFunctional = findBool(BAPCategoryGantryCrane.FUNCTIONAL_TEST, "Limit Switch Berfungsi")
    )

    val ndtTest = GantryCraneBapNdtTest(
        method = findString(BAPCategoryGantryCrane.NDT_TEST, "Metode"),
        isNdtResultGood = findBool(BAPCategoryGantryCrane.NDT_TEST, "Hasil Baik")
    )

    val loadTest = GantryCraneBapLoadTest(
        loadKg = findString(BAPCategoryGantryCrane.LOAD_TEST, "Beban (kg)"),
        liftHeightMeters = findString(BAPCategoryGantryCrane.LOAD_TEST, "Tinggi Angkat (meter)"),
        holdTimeSeconds = findString(BAPCategoryGantryCrane.LOAD_TEST, "Waktu Tahan (detik)"),
        isLoadTestResultGood = findBool(BAPCategoryGantryCrane.LOAD_TEST, "Hasil Baik")
    )

    val inspectionResult = GantryCraneBapInspectionResult(
        visualCheck = visualCheck,
        functionalTest = functionalTest,
        ndtTest = ndtTest,
        loadTest = loadTest
    )

    return GantryCraneBapRequest(
        laporanId = this.inspection.id.toString(),
        examinationType = this.inspection.examinationType,
        inspectionType = this.inspection.inspectionType.name,
        inspectionDate = this.inspection.reportDate ?: "",
        createdAt = this.inspection.createdAt ?: "",
        extraId = this.inspection.extraId.toLongOrNull() ?: 0L,
        generalData = generalData,
        technicalData = technicalData,
        inspectionResult = inspectionResult
    )
}