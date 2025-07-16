package com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import kotlinx.collections.immutable.toImmutableList

/**
 * Maps the GantryCraneUiState to an InspectionWithDetailsDomain model without using reflection.
 * All UI state data is flattened into InspectionDomain, InspectionFindingDomain, or InspectionCheckItemDomain.
 */
fun GantryCraneUiState.toInspectionWithDetailsDomain(
    currentTime: String
): InspectionWithDetailsDomain {
    val report = this.gantryCraneInspectionReport
    val generalData = report.generalData
    val technicalData = report.technicalData
    val visualInspection = report.visualInspection
    val nde = report.nonDestructiveExamination
    val testing = report.testing
    val conclusion = report.conclusion

    // Hardcoded values as requested
    val inspectionId = 0L
    val documentType = DocumentType.LAPORAN
    val inspectionType = InspectionType.PAA
    val subInspectionType = SubInspectionType.Gantry_Crane

    // 1. Map main and general data to InspectionDomain
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = "",
        documentType = documentType,
        inspectionType = inspectionType,
        subInspectionType = subInspectionType,
        equipmentType = report.equipmentType,
        examinationType = report.examinationType,
        ownerName = generalData.owner,
        ownerAddress = generalData.address,
        usageLocation = generalData.unitLocation,
        addressUsageLocation = generalData.user,
        driveType = generalData.driveType,
        serialNumber = generalData.serialNumber,
        permitNumber = generalData.permitNumber,
        capacity = generalData.liftingCapacity,
        manufacturer = ManufacturerDomain(
            name = generalData.manufacturer,
            brandOrType = generalData.brandType,
            year = generalData.yearOfManufacture
        ),
        createdAt = currentTime,
        // Fields not in UI State are set to empty string
        speed = "",
        floorServed = "",
        reportDate = "",
        nextInspectionDate = "",
        inspectorName = "",
        status = "",
        isSynced = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    // Helper to add a check item for a simple string value
    fun addCheckItem(category: String, itemName: String, value: String) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = false, // Not applicable for simple data points
                result = value
            )
        )
    }

    // Helper to add a check item for an InspectionResult
    fun addResultCheckItem(category: String, itemName: String, value: GantryCraneInspectionResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.status,
                result = value.result
            )
        )
    }

    // 2. Map remaining General Data to CheckItems
    addCheckItem("general_data", "personInCharge", generalData.personInCharge)
    addCheckItem("general_data", "intendedUse", generalData.intendedUse)
    addCheckItem("general_data", "operatorCertificate", generalData.operatorCertificate)
    addCheckItem("general_data", "technicalDataManual", generalData.technicalDataManual)

    // 3. Map all Technical Data to CheckItems
    val techCategory = "technical_data"
    technicalData.specifications.let { spec ->
        addCheckItem(techCategory, "spec_liftingHeight_hoisting", spec.liftingHeight.hoisting)
        addCheckItem(techCategory, "spec_liftingHeight_traveling", spec.liftingHeight.traveling)
        addCheckItem(techCategory, "spec_liftingHeight_traversing", spec.liftingHeight.traversing)
        addCheckItem(techCategory, "spec_girderLength_hoisting", spec.girderLength.hoisting)
        addCheckItem(techCategory, "spec_girderLength_traveling", spec.girderLength.traveling)
        addCheckItem(techCategory, "spec_girderLength_traversing", spec.girderLength.traversing)
        addCheckItem(techCategory, "spec_speed_hoisting", spec.speed.hoisting)
        addCheckItem(techCategory, "spec_speed_traveling", spec.speed.traveling)
        addCheckItem(techCategory, "spec_speed_traversing", spec.speed.traversing)
    }
    technicalData.driveMotor.let { motor ->
        addCheckItem(techCategory, "motor_capacity_hoisting", motor.capacity.hoisting)
        addCheckItem(techCategory, "motor_capacity_traveling", motor.capacity.traveling)
        addCheckItem(techCategory, "motor_capacity_traversing", motor.capacity.traversing)
        addCheckItem(techCategory, "motor_powerKw_hoisting", motor.powerKw.hoisting)
        addCheckItem(techCategory, "motor_powerKw_traveling", motor.powerKw.traveling)
        addCheckItem(techCategory, "motor_powerKw_traversing", motor.powerKw.traversing)
        addCheckItem(techCategory, "motor_type_hoisting", motor.type.hoisting)
        addCheckItem(techCategory, "motor_type_traveling", motor.type.traveling)
        addCheckItem(techCategory, "motor_type_traversing", motor.type.traversing)
        addCheckItem(techCategory, "motor_rpm_hoisting", motor.rpm.hoisting)
        addCheckItem(techCategory, "motor_rpm_traveling", motor.rpm.traveling)
        addCheckItem(techCategory, "motor_rpm_traversing", motor.rpm.traversing)
        addCheckItem(techCategory, "motor_voltageV_hoisting", motor.voltageV.hoisting)
        addCheckItem(techCategory, "motor_voltageV_traveling", motor.voltageV.traveling)
        addCheckItem(techCategory, "motor_voltageV_traversing", motor.voltageV.traversing)
        addCheckItem(techCategory, "motor_currentA_hoisting", motor.currentA.hoisting)
        addCheckItem(techCategory, "motor_currentA_traveling", motor.currentA.traveling)
        addCheckItem(techCategory, "motor_currentA_traversing", motor.currentA.traversing)
        addCheckItem(techCategory, "motor_frequencyHz_hoisting", motor.frequencyHz.hoisting)
        addCheckItem(techCategory, "motor_frequencyHz_traveling", motor.frequencyHz.traveling)
        addCheckItem(techCategory, "motor_frequencyHz_traversing", motor.frequencyHz.traversing)
    }
    technicalData.startingResistor.let { resistor ->
        addCheckItem(techCategory, "resistor_type_hoisting", resistor.type.hoisting)
        addCheckItem(techCategory, "resistor_type_traveling", resistor.type.traveling)
        addCheckItem(techCategory, "resistor_type_traversing", resistor.type.traversing)
        addCheckItem(techCategory, "resistor_voltageV_hoisting", resistor.voltageV.hoisting)
        addCheckItem(techCategory, "resistor_voltageV_traveling", resistor.voltageV.traveling)
        addCheckItem(techCategory, "resistor_voltageV_traversing", resistor.voltageV.traversing)
        addCheckItem(techCategory, "resistor_currentA_hoisting", resistor.currentA.hoisting)
        addCheckItem(techCategory, "resistor_currentA_traveling", resistor.currentA.traveling)
        addCheckItem(techCategory, "resistor_currentA_traversing", resistor.currentA.traversing)
    }
    technicalData.brake.let { brake ->
        addCheckItem(techCategory, "brake_type_hoisting", brake.type.hoisting)
        addCheckItem(techCategory, "brake_type_traveling", brake.type.traveling)
        addCheckItem(techCategory, "brake_type_traversing", brake.type.traversing)
        addCheckItem(techCategory, "brake_model_hoisting", brake.model.hoisting)
        addCheckItem(techCategory, "brake_model_traveling", brake.model.traveling)
        addCheckItem(techCategory, "brake_model_traversing", brake.model.traversing)
    }
    technicalData.controllerBrake.let { cBrake ->
        addCheckItem(techCategory, "cBrake_type_hoisting", cBrake.type.hoisting)
        addCheckItem(techCategory, "cBrake_type_traveling", cBrake.type.traveling)
        addCheckItem(techCategory, "cBrake_type_traversing", cBrake.type.traversing)
        addCheckItem(techCategory, "cBrake_model_hoisting", cBrake.model.hoisting)
        addCheckItem(techCategory, "cBrake_model_traveling", cBrake.model.traveling)
        addCheckItem(techCategory, "cBrake_model_traversing", cBrake.model.traversing)
    }
    technicalData.hook.let { hook ->
        addCheckItem(techCategory, "hook_type_hoisting", hook.type.hoisting)
        addCheckItem(techCategory, "hook_type_traveling", hook.type.traveling)
        addCheckItem(techCategory, "hook_type_traversing", hook.type.traversing)
        addCheckItem(techCategory, "hook_capacity_hoisting", hook.capacity.hoisting)
        addCheckItem(techCategory, "hook_capacity_traveling", hook.capacity.traveling)
        addCheckItem(techCategory, "hook_capacity_traversing", hook.capacity.traversing)
        addCheckItem(techCategory, "hook_material_hoisting", hook.material.hoisting)
        addCheckItem(techCategory, "hook_material_traveling", hook.material.traveling)
        addCheckItem(techCategory, "hook_material_traversing", hook.material.traversing)
    }
    technicalData.wireRope.let { wireRope ->
        addCheckItem(techCategory, "wireRope_type_hoisting", wireRope.type.hoisting)
        addCheckItem(techCategory, "wireRope_type_traveling", wireRope.type.traveling)
        addCheckItem(techCategory, "wireRope_type_traversing", wireRope.type.traversing)
        addCheckItem(techCategory, "wireRope_construction_hoisting", wireRope.construction.hoisting)
        addCheckItem(techCategory, "wireRope_construction_traveling", wireRope.construction.traveling)
        addCheckItem(techCategory, "wireRope_construction_traversing", wireRope.construction.traversing)
        addCheckItem(techCategory, "wireRope_diameter_hoisting", wireRope.diameter.hoisting)
        addCheckItem(techCategory, "wireRope_diameter_traveling", wireRope.diameter.traveling)
        addCheckItem(techCategory, "wireRope_diameter_traversing", wireRope.diameter.traversing)
        addCheckItem(techCategory, "wireRope_length_hoisting", wireRope.length.hoisting)
        addCheckItem(techCategory, "wireRope_length_traveling", wireRope.length.traveling)
        addCheckItem(techCategory, "wireRope_length_traversing", wireRope.length.traversing)
    }
    technicalData.chain.let { chain ->
        addCheckItem(techCategory, "chain_type_hoisting", chain.type.hoisting)
        addCheckItem(techCategory, "chain_type_traveling", chain.type.traveling)
        addCheckItem(techCategory, "chain_type_traversing", chain.type.traversing)
        addCheckItem(techCategory, "chain_construction_hoisting", chain.construction.hoisting)
        addCheckItem(techCategory, "chain_construction_traveling", chain.construction.traveling)
        addCheckItem(techCategory, "chain_construction_traversing", chain.construction.traversing)
        addCheckItem(techCategory, "chain_diameter_hoisting", chain.diameter.hoisting)
        addCheckItem(techCategory, "chain_diameter_traveling", chain.diameter.traveling)
        addCheckItem(techCategory, "chain_diameter_traversing", chain.diameter.traversing)
        addCheckItem(techCategory, "chain_length_hoisting", chain.length.hoisting)
        addCheckItem(techCategory, "chain_length_traveling", chain.length.traveling)
        addCheckItem(techCategory, "chain_length_traversing", chain.length.traversing)
    }


    // 4. Map all Visual Inspection to CheckItems
    val visualCategory = "visual_inspection"
    addResultCheckItem(visualCategory, "foundationAnchorBoltCorrosion", visualInspection.foundationAnchorBoltCorrosion)
    addResultCheckItem(visualCategory, "foundationAnchorBoltCracks", visualInspection.foundationAnchorBoltCracks)
    addResultCheckItem(visualCategory, "foundationAnchorBoltDeformation", visualInspection.foundationAnchorBoltDeformation)
    addResultCheckItem(visualCategory, "foundationAnchorBoltTightness", visualInspection.foundationAnchorBoltTightness)
    addResultCheckItem(visualCategory, "columnFrameCorrosion", visualInspection.columnFrameCorrosion)
    addResultCheckItem(visualCategory, "columnFrameCracks", visualInspection.columnFrameCracks)
    addResultCheckItem(visualCategory, "columnFrameDeformation", visualInspection.columnFrameDeformation)
    addResultCheckItem(visualCategory, "columnFrameFastening", visualInspection.columnFrameFastening)
    addResultCheckItem(visualCategory, "columnFrameCrossBracing", visualInspection.columnFrameCrossBracing)
    addResultCheckItem(visualCategory, "columnFrameDiagonalBracing", visualInspection.columnFrameDiagonalBracing)
    addResultCheckItem(visualCategory, "ladderCorrosion", visualInspection.ladderCorrosion)
    addResultCheckItem(visualCategory, "ladderCracks", visualInspection.ladderCracks)
    addResultCheckItem(visualCategory, "ladderDeformation", visualInspection.ladderDeformation)
    addResultCheckItem(visualCategory, "ladderFastening", visualInspection.ladderFastening)
    addResultCheckItem(visualCategory, "workPlatformCorrosion", visualInspection.workPlatformCorrosion)
    addResultCheckItem(visualCategory, "workPlatformCracks", visualInspection.workPlatformCracks)
    addResultCheckItem(visualCategory, "workPlatformDeformation", visualInspection.workPlatformDeformation)
    addResultCheckItem(visualCategory, "workPlatformFastening", visualInspection.workPlatformFastening)
    addResultCheckItem(visualCategory, "railMountingBeamCorrosion", visualInspection.railMountingBeamCorrosion)
    addResultCheckItem(visualCategory, "railMountingBeamCracks", visualInspection.railMountingBeamCracks)
    addResultCheckItem(visualCategory, "railMountingBeamDeformation", visualInspection.railMountingBeamDeformation)
    addResultCheckItem(visualCategory, "railMountingBeamFastening", visualInspection.railMountingBeamFastening)
    addResultCheckItem(visualCategory, "travelingRailCorrosion", visualInspection.travelingRailCorrosion)
    addResultCheckItem(visualCategory, "travelingRailCracks", visualInspection.travelingRailCracks)
    addResultCheckItem(visualCategory, "travelingRailJoint", visualInspection.travelingRailJoint)
    addResultCheckItem(visualCategory, "travelingRailStraightness", visualInspection.travelingRailStraightness)
    addResultCheckItem(visualCategory, "travelingRailAlignmentBetweenRails", visualInspection.travelingRailAlignmentBetweenRails)
    addResultCheckItem(visualCategory, "travelingRailEvennessBetweenRails", visualInspection.travelingRailEvennessBetweenRails)
    addResultCheckItem(visualCategory, "travelingRailGapBetweenRailJoints", visualInspection.travelingRailGapBetweenRailJoints)
    addResultCheckItem(visualCategory, "travelingRailFastener", visualInspection.travelingRailFastener)
    addResultCheckItem(visualCategory, "travelingRailStopper", visualInspection.travelingRailStopper)
    addResultCheckItem(visualCategory, "traversingRailCorrosion", visualInspection.traversingRailCorrosion)
    addResultCheckItem(visualCategory, "traversingRailCracks", visualInspection.traversingRailCracks)
    addResultCheckItem(visualCategory, "traversingRailJoint", visualInspection.traversingRailJoint)
    addResultCheckItem(visualCategory, "traversingRailStraightness", visualInspection.traversingRailStraightness)
    addResultCheckItem(visualCategory, "traversingRailAlignmentBetweenRails", visualInspection.traversingRailAlignmentBetweenRails)
    addResultCheckItem(visualCategory, "traversingRailEvennessBetweenRails", visualInspection.traversingRailEvennessBetweenRails)
    addResultCheckItem(visualCategory, "traversingRailGapBetweenRailJoints", visualInspection.traversingRailGapBetweenRailJoints)
    addResultCheckItem(visualCategory, "traversingRailFastener", visualInspection.traversingRailFastener)
    addResultCheckItem(visualCategory, "traversingRailStopper", visualInspection.traversingRailStopper)
    addResultCheckItem(visualCategory, "girderCorrosion", visualInspection.girderCorrosion)
    addResultCheckItem(visualCategory, "girderCracks", visualInspection.girderCracks)
    addResultCheckItem(visualCategory, "girderCamber", visualInspection.girderCamber)
    addResultCheckItem(visualCategory, "girderJoint", visualInspection.girderJoint)
    addResultCheckItem(visualCategory, "girderEndJoint", visualInspection.girderEndJoint)
    addResultCheckItem(visualCategory, "girderTruckMountOnGirder", visualInspection.girderTruckMountOnGirder)
    addResultCheckItem(visualCategory, "travelingGearboxGirderCorrosion", visualInspection.travelingGearboxGirderCorrosion)
    addResultCheckItem(visualCategory, "travelingGearboxGirderCracks", visualInspection.travelingGearboxGirderCracks)
    addResultCheckItem(visualCategory, "travelingGearboxGirderLubricatingOil", visualInspection.travelingGearboxGirderLubricatingOil)
    addResultCheckItem(visualCategory, "travelingGearboxGirderOilSeal", visualInspection.travelingGearboxGirderOilSeal)
    addResultCheckItem(visualCategory, "driveWheelWear", visualInspection.driveWheelWear)
    addResultCheckItem(visualCategory, "driveWheelCracks", visualInspection.driveWheelCracks)
    addResultCheckItem(visualCategory, "driveWheelDeformation", visualInspection.driveWheelDeformation)
    addResultCheckItem(visualCategory, "driveWheelFlangeCondition", visualInspection.driveWheelFlangeCondition)
    addResultCheckItem(visualCategory, "driveWheelChainCondition", visualInspection.driveWheelChainCondition)
    addResultCheckItem(visualCategory, "idleWheelSecurity", visualInspection.idleWheelSecurity)
    addResultCheckItem(visualCategory, "idleWheelCracks", visualInspection.idleWheelCracks)
    addResultCheckItem(visualCategory, "idleWheelDeformation", visualInspection.idleWheelDeformation)
    addResultCheckItem(visualCategory, "idleWheelFlangeCondition", visualInspection.idleWheelFlangeCondition)
    addResultCheckItem(visualCategory, "wheelConnectorBogieAxleStraightness", visualInspection.wheelConnectorBogieAxleStraightness)
    addResultCheckItem(visualCategory, "wheelConnectorBogieAxleCrossJoint", visualInspection.wheelConnectorBogieAxleCrossJoint)
    addResultCheckItem(visualCategory, "wheelConnectorBogieAxleLubrication", visualInspection.wheelConnectorBogieAxleLubrication)
    addResultCheckItem(visualCategory, "stopperBumperOnGirderCondition", visualInspection.stopperBumperOnGirderCondition)
    addResultCheckItem(visualCategory, "stopperBumperOnGirderReinforcement", visualInspection.stopperBumperOnGirderReinforcement)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierFastening", visualInspection.traversingGearboxTrolleyCarrierFastening)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierCorrosion", visualInspection.traversingGearboxTrolleyCarrierCorrosion)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierCracks", visualInspection.traversingGearboxTrolleyCarrierCracks)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierLubricatingOil", visualInspection.traversingGearboxTrolleyCarrierLubricatingOil)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierOilSeal", visualInspection.traversingGearboxTrolleyCarrierOilSeal)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyWear", visualInspection.driveWheelOnTrolleyWear)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyCracks", visualInspection.driveWheelOnTrolleyCracks)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyDeformation", visualInspection.driveWheelOnTrolleyDeformation)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyFlangeCondition", visualInspection.driveWheelOnTrolleyFlangeCondition)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyChainCondition", visualInspection.driveWheelOnTrolleyChainCondition)
    addResultCheckItem(visualCategory, "idleWheelOnTrolleyWear", visualInspection.idleWheelOnTrolleyWear)
    addResultCheckItem(visualCategory, "idleWheelOnTrolleyCracks", visualInspection.idleWheelOnTrolleyCracks)
    addResultCheckItem(visualCategory, "idleWheelOnTrolleyDeformation", visualInspection.idleWheelOnTrolleyDeformation)
    addResultCheckItem(visualCategory, "idleWheelOnTrolleyFlangeCondition", visualInspection.idleWheelOnTrolleyFlangeCondition)
    addResultCheckItem(visualCategory, "windingDrumGroove", visualInspection.windingDrumGroove)
    addResultCheckItem(visualCategory, "windingDrumGrooveFlange", visualInspection.windingDrumGrooveFlange)
    addResultCheckItem(visualCategory, "windingDrumFlanges", visualInspection.windingDrumFlanges)
    addResultCheckItem(visualCategory, "brakeWear", visualInspection.brakeWear)
    addResultCheckItem(visualCategory, "brakeAdjustment", visualInspection.brakeAdjustment)
    addResultCheckItem(visualCategory, "hoistGearboxLubrication", visualInspection.hoistGearboxLubrication)
    addResultCheckItem(visualCategory, "hoistGearboxOilSeal", visualInspection.hoistGearboxOilSeal)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyGroove", visualInspection.pulleyDiscChainSprocketPulleyGroove)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyFlange", visualInspection.pulleyDiscChainSprocketPulleyFlange)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyPin", visualInspection.pulleyDiscChainSprocketPulleyPin)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketBearing", visualInspection.pulleyDiscChainSprocketBearing)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyGuard", visualInspection.pulleyDiscChainSprocketPulleyGuard)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketWireRopeChainGuard", visualInspection.pulleyDiscChainSprocketWireRopeChainGuard)
    addResultCheckItem(visualCategory, "mainHookWear", visualInspection.mainHookWear)
    addResultCheckItem(visualCategory, "mainHookThroatOpening", visualInspection.mainHookThroatOpening)
    addResultCheckItem(visualCategory, "mainHookSwivelNutAndBearing", visualInspection.mainHookSwivelNutAndBearing)
    addResultCheckItem(visualCategory, "mainHookTrunnion", visualInspection.mainHookTrunnion)
    addResultCheckItem(visualCategory, "auxiliaryHookWear", visualInspection.auxiliaryHookWear)
    addResultCheckItem(visualCategory, "auxiliaryHookThroatOpening", visualInspection.auxiliaryHookThroatOpening)
    addResultCheckItem(visualCategory, "auxiliaryHookSwivelNutAndBearing", visualInspection.auxiliaryHookSwivelNutAndBearing)
    addResultCheckItem(visualCategory, "auxiliaryHookTrunnion", visualInspection.auxiliaryHookTrunnion)
    addResultCheckItem(visualCategory, "mainWireRopeCorrosion", visualInspection.mainWireRopeCorrosion)
    addResultCheckItem(visualCategory, "mainWireRopeWear", visualInspection.mainWireRopeWear)
    addResultCheckItem(visualCategory, "mainWireRopeBroken", visualInspection.mainWireRopeBroken)
    addResultCheckItem(visualCategory, "mainWireRopeDeformation", visualInspection.mainWireRopeDeformation)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeCorrosion", visualInspection.auxiliaryWireRopeCorrosion)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeWear", visualInspection.auxiliaryWireRopeWear)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeBroken", visualInspection.auxiliaryWireRopeBroken)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeDeformation", visualInspection.auxiliaryWireRopeDeformation)
    addResultCheckItem(visualCategory, "mainChainCorrosion", visualInspection.mainChainCorrosion)
    addResultCheckItem(visualCategory, "mainChainWear", visualInspection.mainChainWear)
    addResultCheckItem(visualCategory, "mainChainCrackedBroken", visualInspection.mainChainCrackedBroken)
    addResultCheckItem(visualCategory, "mainChainDeformation", visualInspection.mainChainDeformation)
    addResultCheckItem(visualCategory, "auxiliaryChainCorrosion", visualInspection.auxiliaryChainCorrosion)
    addResultCheckItem(visualCategory, "auxiliaryChainWear", visualInspection.auxiliaryChainWear)
    addResultCheckItem(visualCategory, "auxiliaryChainCrackedBroken", visualInspection.auxiliaryChainCrackedBroken)
    addResultCheckItem(visualCategory, "auxiliaryChainDeformation", visualInspection.auxiliaryChainDeformation)
    addResultCheckItem(visualCategory, "limitSwitchLsLongTraveling", visualInspection.limitSwitchLsLongTraveling)
    addResultCheckItem(visualCategory, "limitSwitchLsCrossTraveling", visualInspection.limitSwitchLsCrossTraveling)
    addResultCheckItem(visualCategory, "limitSwitchLsHoisting", visualInspection.limitSwitchLsHoisting)
    addResultCheckItem(visualCategory, "operatorCabinSafetyLadder", visualInspection.operatorCabinSafetyLadder)
    addResultCheckItem(visualCategory, "operatorCabinDoor", visualInspection.operatorCabinDoor)
    addResultCheckItem(visualCategory, "operatorCabinWindow", visualInspection.operatorCabinWindow)
    addResultCheckItem(visualCategory, "operatorCabinFanAc", visualInspection.operatorCabinFanAc)
    addResultCheckItem(visualCategory, "operatorCabinControlLeversButtons", visualInspection.operatorCabinControlLeversButtons)
    addResultCheckItem(visualCategory, "operatorCabinPendantControl", visualInspection.operatorCabinPendantControl)
    addResultCheckItem(visualCategory, "operatorCabinLighting", visualInspection.operatorCabinLighting)
    addResultCheckItem(visualCategory, "operatorCabinHorn", visualInspection.operatorCabinHorn)
    addResultCheckItem(visualCategory, "operatorCabinFuse", visualInspection.operatorCabinFuse)
    addResultCheckItem(visualCategory, "operatorCabinCommunicationDevice", visualInspection.operatorCabinCommunicationDevice)
    addResultCheckItem(visualCategory, "operatorCabinFireExtinguisher", visualInspection.operatorCabinFireExtinguisher)
    addResultCheckItem(visualCategory, "operatorCabinOperatingSigns", visualInspection.operatorCabinOperatingSigns)
    addResultCheckItem(visualCategory, "operatorCabinIgnitionKeyMasterSwitch", visualInspection.operatorCabinIgnitionKeyMasterSwitch)
    addResultCheckItem(visualCategory, "electricalComponentsPanelConductorConnector", visualInspection.electricalComponentsPanelConductorConnector)
    addResultCheckItem(visualCategory, "electricalComponentsConductorProtection", visualInspection.electricalComponentsConductorProtection)
    addResultCheckItem(visualCategory, "electricalComponentsMotorInstallationSafetySystem", visualInspection.electricalComponentsMotorInstallationSafetySystem)
    addResultCheckItem(visualCategory, "electricalComponentsGroundingSystem", visualInspection.electricalComponentsGroundingSystem)
    addResultCheckItem(visualCategory, "electricalComponentsInstallation", visualInspection.electricalComponentsInstallation)

    // 5. Map NDE to CheckItems
    addCheckItem("nde_wirerope", "method", nde.wireRope.method)
    nde.wireRope.items.forEachIndexed { index, item ->
        val cat = "nde_wirerope_item_$index"
        addCheckItem(cat, "usage", item.usage)
        addCheckItem(cat, "specDiameter", item.specDiameter)
        addCheckItem(cat, "actualDiameter", item.actualDiameter)
        addCheckItem(cat, "construction", item.construction)
        addCheckItem(cat, "type", item.type)
        addCheckItem(cat, "length", item.length)
        addCheckItem(cat, "age", item.age)
        addResultCheckItem(cat, "finding", item.finding)
    }
    addCheckItem("nde_mainHook", "method", nde.mainHook.method)
    nde.mainHook.let { hook ->
        val cat = "nde_mainHook_measurement"
        addCheckItem(cat, "spec_a", hook.specification.a)
        addCheckItem(cat, "spec_b", hook.specification.b)
        addCheckItem(cat, "spec_c", hook.specification.c)
        addCheckItem(cat, "spec_d", hook.specification.d)
        addCheckItem(cat, "spec_e", hook.specification.e)
        addCheckItem(cat, "spec_f", hook.specification.f)
        addCheckItem(cat, "spec_g", hook.specification.g)
        addCheckItem(cat, "spec_h", hook.specification.h)
        addResultCheckItem(cat, "spec_finding", hook.specification.finding)

        addCheckItem(cat, "result_a", hook.measurementResult.a)
        addCheckItem(cat, "result_b", hook.measurementResult.b)
        addCheckItem(cat, "result_c", hook.measurementResult.c)
        addCheckItem(cat, "result_d", hook.measurementResult.d)
        addCheckItem(cat, "result_e", hook.measurementResult.e)
        addCheckItem(cat, "result_f", hook.measurementResult.f)
        addCheckItem(cat, "result_g", hook.measurementResult.g)
        addCheckItem(cat, "result_h", hook.measurementResult.h)
        addResultCheckItem(cat, "result_finding", hook.measurementResult.finding)

        addCheckItem(cat, "tolerance_a", hook.tolerance.a)
        addCheckItem(cat, "tolerance_b", hook.tolerance.b)
        addCheckItem(cat, "tolerance_c", hook.tolerance.c)
        addCheckItem(cat, "tolerance_d", hook.tolerance.d)
        addCheckItem(cat, "tolerance_e", hook.tolerance.e)
        addCheckItem(cat, "tolerance_f", hook.tolerance.f)
        addCheckItem(cat, "tolerance_g", hook.tolerance.g)
        addCheckItem(cat, "tolerance_h", hook.tolerance.h)
        addResultCheckItem(cat, "tolerance_finding", hook.tolerance.finding)
    }
    addCheckItem("nde_girder", "method", nde.girder.method)
    nde.girder.items.forEachIndexed { index, item ->
        val cat = "nde_girder_item_$index"
        addCheckItem(cat, "location", item.location)
        addResultCheckItem(cat, "finding", item.finding)
    }


    // 6. Map Testing to CheckItems
    testing.dynamicTest.withoutLoad.let { test ->
        val cat = "testing_dynamic_without_load"
        addCheckItem(cat, "traveling_shouldBe", test.traveling.shouldBe)
        addCheckItem(cat, "traveling_testedMeasured", test.traveling.testedMeasured)
        addCheckItem(cat, "traveling_remarks", test.traveling.remarks)
        addCheckItem(cat, "traversing_shouldBe", test.traversing.shouldBe)
        addCheckItem(cat, "traversing_testedMeasured", test.traversing.testedMeasured)
        addCheckItem(cat, "traversing_remarks", test.traversing.remarks)
        addCheckItem(cat, "hoisting_shouldBe", test.hoisting.shouldBe)
        addCheckItem(cat, "hoisting_testedMeasured", test.hoisting.testedMeasured)
        addCheckItem(cat, "hoisting_remarks", test.hoisting.remarks)
        addCheckItem(cat, "safetyDevice_shouldBe", test.safetyDevice.shouldBe)
        addCheckItem(cat, "safetyDevice_testedMeasured", test.safetyDevice.testedMeasured)
        addCheckItem(cat, "safetyDevice_remarks", test.safetyDevice.remarks)
        addCheckItem(cat, "brakeSwitch_shouldBe", test.brakeSwitch.shouldBe)
        addCheckItem(cat, "brakeSwitch_testedMeasured", test.brakeSwitch.testedMeasured)
        addCheckItem(cat, "brakeSwitch_remarks", test.brakeSwitch.remarks)
        addCheckItem(cat, "brakeLockingDevice_shouldBe", test.brakeLockingDevice.shouldBe)
        addCheckItem(cat, "brakeLockingDevice_testedMeasured", test.brakeLockingDevice.testedMeasured)
        addCheckItem(cat, "brakeLockingDevice_remarks", test.brakeLockingDevice.remarks)
        addCheckItem(cat, "electricalInstallation_shouldBe", test.electricalInstallation.shouldBe)
        addCheckItem(cat, "electricalInstallation_testedMeasured", test.electricalInstallation.testedMeasured)
        addCheckItem(cat, "electricalInstallation_remarks", test.electricalInstallation.remarks)
    }
    testing.dynamicTest.withLoad.let { test ->
        val cat = "testing_dynamic_with_load"
        addCheckItem(cat, "noLoad_hoist", test.noLoad.hoist)
        addCheckItem(cat, "noLoad_traversing", test.noLoad.traversing)
        addCheckItem(cat, "noLoad_travelling", test.noLoad.travelling)
        addCheckItem(cat, "noLoad_brakeSystem", test.noLoad.brakeSystem)
        addCheckItem(cat, "noLoad_remarks", test.noLoad.remarks)
        addCheckItem(cat, "swl25_hoist", test.swl25.hoist)
        addCheckItem(cat, "swl25_traversing", test.swl25.traversing)
        addCheckItem(cat, "swl25_travelling", test.swl25.travelling)
        addCheckItem(cat, "swl25_brakeSystem", test.swl25.brakeSystem)
        addCheckItem(cat, "swl25_remarks", test.swl25.remarks)
        addCheckItem(cat, "swl50_hoist", test.swl50.hoist)
        addCheckItem(cat, "swl50_traversing", test.swl50.traversing)
        addCheckItem(cat, "swl50_travelling", test.swl50.travelling)
        addCheckItem(cat, "swl50_brakeSystem", test.swl50.brakeSystem)
        addCheckItem(cat, "swl50_remarks", test.swl50.remarks)
        addCheckItem(cat, "swl75_hoist", test.swl75.hoist)
        addCheckItem(cat, "swl75_traversing", test.swl75.traversing)
        addCheckItem(cat, "swl75_travelling", test.swl75.travelling)
        addCheckItem(cat, "swl75_brakeSystem", test.swl75.brakeSystem)
        addCheckItem(cat, "swl75_remarks", test.swl75.remarks)
        addCheckItem(cat, "swl100_hoist", test.swl100.hoist)
        addCheckItem(cat, "swl100_traversing", test.swl100.traversing)
        addCheckItem(cat, "swl100_travelling", test.swl100.travelling)
        addCheckItem(cat, "swl100_brakeSystem", test.swl100.brakeSystem)
        addCheckItem(cat, "swl100_remarks", test.swl100.remarks)
    }
    testing.staticTest.let { test ->
        val cat = "testing_static"
        addCheckItem(cat, "load", test.load)
        addCheckItem(cat, "deflection_designBased", test.deflectionStandard.designBased)
        addCheckItem(cat, "deflection_spanLength", test.deflectionStandard.spanLength)
        addCheckItem(cat, "deflection_calculation", test.deflectionStandard.calculation)
    }
    testing.staticTest.deflectionMeasurement.forEachIndexed { index, item ->
        val cat = "testing_static_deflection_item_$index"
        addCheckItem(cat, "position", item.position)
        addCheckItem(cat, "deflection", item.deflection)
        addCheckItem(cat, "maxStandard", item.maxStandard)
        addCheckItem(cat, "remarks", item.remarks)
    }


    // 7. Map Conclusion to Findings
    val findings = mutableListOf<InspectionFindingDomain>()
    conclusion.summary.forEach {
        findings.add(
            InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING)
        )
    }
    conclusion.recommendations.forEach {
        findings.add(
            InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION)
        )
    }


    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList() // All test data is in checkItems
    )
}

/**
 * Maps an InspectionWithDetailsDomain model to the GantryCraneUiState without using reflection.
 * This function reconstructs the nested UI state from the flattened domain model.
 */
fun InspectionWithDetailsDomain.toGantryCraneUiState(): GantryCraneUiState {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    // Helper to get a simple string value from check items
    fun getCheckItemValue(category: String, itemName: String): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    fun getCheckItemValueForIndexed(prefix: String, index: Int, itemName: String): String {
        val category = "${prefix}_${index}"
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    // Helper to get an InspectionResult from check items
    fun getResultCheckItem(category: String, itemName: String): GantryCraneInspectionResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return GantryCraneInspectionResult(
            status = item?.status ?: false,
            result = item?.result ?: ""
        )
    }

    fun getResultCheckItemForIndexed(prefix: String, index: Int, itemName: String): GantryCraneInspectionResult {
        val category = "${prefix}_${index}"
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return GantryCraneInspectionResult(
            status = item?.status ?: false,
            result = item?.result ?: ""
        )
    }

    // 1. Reconstruct General Data
    val generalData = GantryCraneGeneralData(
        owner = inspection.ownerName ?: "",
        address = inspection.ownerAddress ?: "",
        user = inspection.addressUsageLocation ?: "",
        unitLocation = inspection.usageLocation ?: "",
        driveType = inspection.driveType ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        brandType = inspection.manufacturer?.brandOrType ?: "",
        yearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumber = inspection.serialNumber ?: "",
        liftingCapacity = inspection.capacity ?: "",
        permitNumber = inspection.permitNumber ?: "",
        personInCharge = getCheckItemValue("general_data", "personInCharge"),
        intendedUse = getCheckItemValue("general_data", "intendedUse"),
        operatorCertificate = getCheckItemValue("general_data", "operatorCertificate"),
        technicalDataManual = getCheckItemValue("general_data", "technicalDataManual")
    )

    // 2. Reconstruct Technical Data
    val techCategory = "technical_data"
    val technicalData = GantryCraneTechnicalData(
        specifications = GantryCraneTechSpecs(
            liftingHeight = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "spec_liftingHeight_hoisting"),
                traveling = getCheckItemValue(techCategory, "spec_liftingHeight_traveling"),
                traversing = getCheckItemValue(techCategory, "spec_liftingHeight_traversing")
            ),
            girderLength = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "spec_girderLength_hoisting"),
                traveling = getCheckItemValue(techCategory, "spec_girderLength_traveling"),
                traversing = getCheckItemValue(techCategory, "spec_girderLength_traversing")
            ),
            speed = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "spec_speed_hoisting"),
                traveling = getCheckItemValue(techCategory, "spec_speed_traveling"),
                traversing = getCheckItemValue(techCategory, "spec_speed_traversing")
            )
        ),
        driveMotor = GantryCraneDriveMotor(
            capacity = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_capacity_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_capacity_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_capacity_traversing")
            ),
            powerKw = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_powerKw_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_powerKw_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_powerKw_traversing")
            ),
            type = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_type_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_type_traversing")
            ),
            rpm = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_rpm_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_rpm_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_rpm_traversing")
            ),
            voltageV = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_voltageV_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_voltageV_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_voltageV_traversing")
            ),
            currentA = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_currentA_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_currentA_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_currentA_traversing")
            ),
            frequencyHz = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_frequencyHz_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_frequencyHz_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_frequencyHz_traversing")
            )
        ),
        startingResistor = GantryCraneStartingResistor(
            type = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "resistor_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "resistor_type_traveling"),
                traversing = getCheckItemValue(techCategory, "resistor_type_traversing")
            ),
            voltageV = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "resistor_voltageV_hoisting"),
                traveling = getCheckItemValue(techCategory, "resistor_voltageV_traveling"),
                traversing = getCheckItemValue(techCategory, "resistor_voltageV_traversing")
            ),
            currentA = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "resistor_currentA_hoisting"),
                traveling = getCheckItemValue(techCategory, "resistor_currentA_traveling"),
                traversing = getCheckItemValue(techCategory, "resistor_currentA_traversing")
            )
        ),
        brake = GantryCraneBrake(
            type = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "brake_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "brake_type_traveling"),
                traversing = getCheckItemValue(techCategory, "brake_type_traversing")
            ),
            model = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "brake_model_hoisting"),
                traveling = getCheckItemValue(techCategory, "brake_model_traveling"),
                traversing = getCheckItemValue(techCategory, "brake_model_traversing")
            )
        ),
        controllerBrake = GantryCraneControllerBrake(
            type = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "cBrake_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "cBrake_type_traveling"),
                traversing = getCheckItemValue(techCategory, "cBrake_type_traversing")
            ),
            model = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "cBrake_model_hoisting"),
                traveling = getCheckItemValue(techCategory, "cBrake_model_traveling"),
                traversing = getCheckItemValue(techCategory, "cBrake_model_traversing")
            )
        ),
        hook = GantryCraneHook(
            type = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "hook_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "hook_type_traveling"),
                traversing = getCheckItemValue(techCategory, "hook_type_traversing")
            ),
            capacity = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "hook_capacity_hoisting"),
                traveling = getCheckItemValue(techCategory, "hook_capacity_traveling"),
                traversing = getCheckItemValue(techCategory, "hook_capacity_traversing")
            ),
            material = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "hook_material_hoisting"),
                traveling = getCheckItemValue(techCategory, "hook_material_traveling"),
                traversing = getCheckItemValue(techCategory, "hook_material_traversing")
            )
        ),
        wireRope = GantryCraneWireRope(
            type = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "wireRope_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "wireRope_type_traveling"),
                traversing = getCheckItemValue(techCategory, "wireRope_type_traversing")
            ),
            construction = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "wireRope_construction_hoisting"),
                traveling = getCheckItemValue(techCategory, "wireRope_construction_traveling"),
                traversing = getCheckItemValue(techCategory, "wireRope_construction_traversing")
            ),
            diameter = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "wireRope_diameter_hoisting"),
                traveling = getCheckItemValue(techCategory, "wireRope_diameter_traveling"),
                traversing = getCheckItemValue(techCategory, "wireRope_diameter_traversing")
            ),
            length = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "wireRope_length_hoisting"),
                traveling = getCheckItemValue(techCategory, "wireRope_length_traveling"),
                traversing = getCheckItemValue(techCategory, "wireRope_length_traversing")
            )
        ),
        chain = GantryCraneChain(
            type = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "chain_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "chain_type_traveling"),
                traversing = getCheckItemValue(techCategory, "chain_type_traversing")
            ),
            construction = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "chain_construction_hoisting"),
                traveling = getCheckItemValue(techCategory, "chain_construction_traveling"),
                traversing = getCheckItemValue(techCategory, "chain_construction_traversing")
            ),
            diameter = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "chain_diameter_hoisting"),
                traveling = getCheckItemValue(techCategory, "chain_diameter_traveling"),
                traversing = getCheckItemValue(techCategory, "chain_diameter_traversing")
            ),
            length = GantryCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "chain_length_hoisting"),
                traveling = getCheckItemValue(techCategory, "chain_length_traveling"),
                traversing = getCheckItemValue(techCategory, "chain_length_traversing")
            )
        )
    )


    // 3. Reconstruct Visual Inspection
    val visualCategory = "visual_inspection"
    val visualInspection = GantryCraneVisualInspection(
        foundationAnchorBoltCorrosion = getResultCheckItem(visualCategory, "foundationAnchorBoltCorrosion"),
        foundationAnchorBoltCracks = getResultCheckItem(visualCategory, "foundationAnchorBoltCracks"),
        foundationAnchorBoltDeformation = getResultCheckItem(visualCategory, "foundationAnchorBoltDeformation"),
        foundationAnchorBoltTightness = getResultCheckItem(visualCategory, "foundationAnchorBoltTightness"),
        columnFrameCorrosion = getResultCheckItem(visualCategory, "columnFrameCorrosion"),
        columnFrameCracks = getResultCheckItem(visualCategory, "columnFrameCracks"),
        columnFrameDeformation = getResultCheckItem(visualCategory, "columnFrameDeformation"),
        columnFrameFastening = getResultCheckItem(visualCategory, "columnFrameFastening"),
        columnFrameCrossBracing = getResultCheckItem(visualCategory, "columnFrameCrossBracing"),
        columnFrameDiagonalBracing = getResultCheckItem(visualCategory, "columnFrameDiagonalBracing"),
        ladderCorrosion = getResultCheckItem(visualCategory, "ladderCorrosion"),
        ladderCracks = getResultCheckItem(visualCategory, "ladderCracks"),
        ladderDeformation = getResultCheckItem(visualCategory, "ladderDeformation"),
        ladderFastening = getResultCheckItem(visualCategory, "ladderFastening"),
        workPlatformCorrosion = getResultCheckItem(visualCategory, "workPlatformCorrosion"),
        workPlatformCracks = getResultCheckItem(visualCategory, "workPlatformCracks"),
        workPlatformDeformation = getResultCheckItem(visualCategory, "workPlatformDeformation"),
        workPlatformFastening = getResultCheckItem(visualCategory, "workPlatformFastening"),
        railMountingBeamCorrosion = getResultCheckItem(visualCategory, "railMountingBeamCorrosion"),
        railMountingBeamCracks = getResultCheckItem(visualCategory, "railMountingBeamCracks"),
        railMountingBeamDeformation = getResultCheckItem(visualCategory, "railMountingBeamDeformation"),
        railMountingBeamFastening = getResultCheckItem(visualCategory, "railMountingBeamFastening"),
        travelingRailCorrosion = getResultCheckItem(visualCategory, "travelingRailCorrosion"),
        travelingRailCracks = getResultCheckItem(visualCategory, "travelingRailCracks"),
        travelingRailJoint = getResultCheckItem(visualCategory, "travelingRailJoint"),
        travelingRailStraightness = getResultCheckItem(visualCategory, "travelingRailStraightness"),
        travelingRailAlignmentBetweenRails = getResultCheckItem(visualCategory, "travelingRailAlignmentBetweenRails"),
        travelingRailEvennessBetweenRails = getResultCheckItem(visualCategory, "travelingRailEvennessBetweenRails"),
        travelingRailGapBetweenRailJoints = getResultCheckItem(visualCategory, "travelingRailGapBetweenRailJoints"),
        travelingRailFastener = getResultCheckItem(visualCategory, "travelingRailFastener"),
        travelingRailStopper = getResultCheckItem(visualCategory, "travelingRailStopper"),
        traversingRailCorrosion = getResultCheckItem(visualCategory, "traversingRailCorrosion"),
        traversingRailCracks = getResultCheckItem(visualCategory, "traversingRailCracks"),
        traversingRailJoint = getResultCheckItem(visualCategory, "traversingRailJoint"),
        traversingRailStraightness = getResultCheckItem(visualCategory, "traversingRailStraightness"),
        traversingRailAlignmentBetweenRails = getResultCheckItem(visualCategory, "traversingRailAlignmentBetweenRails"),
        traversingRailEvennessBetweenRails = getResultCheckItem(visualCategory, "traversingRailEvennessBetweenRails"),
        traversingRailGapBetweenRailJoints = getResultCheckItem(visualCategory, "traversingRailGapBetweenRailJoints"),
        traversingRailFastener = getResultCheckItem(visualCategory, "traversingRailFastener"),
        traversingRailStopper = getResultCheckItem(visualCategory, "traversingRailStopper"),
        girderCorrosion = getResultCheckItem(visualCategory, "girderCorrosion"),
        girderCracks = getResultCheckItem(visualCategory, "girderCracks"),
        girderCamber = getResultCheckItem(visualCategory, "girderCamber"),
        girderJoint = getResultCheckItem(visualCategory, "girderJoint"),
        girderEndJoint = getResultCheckItem(visualCategory, "girderEndJoint"),
        girderTruckMountOnGirder = getResultCheckItem(visualCategory, "girderTruckMountOnGirder"),
        travelingGearboxGirderCorrosion = getResultCheckItem(visualCategory, "travelingGearboxGirderCorrosion"),
        travelingGearboxGirderCracks = getResultCheckItem(visualCategory, "travelingGearboxGirderCracks"),
        travelingGearboxGirderLubricatingOil = getResultCheckItem(visualCategory, "travelingGearboxGirderLubricatingOil"),
        travelingGearboxGirderOilSeal = getResultCheckItem(visualCategory, "travelingGearboxGirderOilSeal"),
        driveWheelWear = getResultCheckItem(visualCategory, "driveWheelWear"),
        driveWheelCracks = getResultCheckItem(visualCategory, "driveWheelCracks"),
        driveWheelDeformation = getResultCheckItem(visualCategory, "driveWheelDeformation"),
        driveWheelFlangeCondition = getResultCheckItem(visualCategory, "driveWheelFlangeCondition"),
        driveWheelChainCondition = getResultCheckItem(visualCategory, "driveWheelChainCondition"),
        idleWheelSecurity = getResultCheckItem(visualCategory, "idleWheelSecurity"),
        idleWheelCracks = getResultCheckItem(visualCategory, "idleWheelCracks"),
        idleWheelDeformation = getResultCheckItem(visualCategory, "idleWheelDeformation"),
        idleWheelFlangeCondition = getResultCheckItem(visualCategory, "idleWheelFlangeCondition"),
        wheelConnectorBogieAxleStraightness = getResultCheckItem(visualCategory, "wheelConnectorBogieAxleStraightness"),
        wheelConnectorBogieAxleCrossJoint = getResultCheckItem(visualCategory, "wheelConnectorBogieAxleCrossJoint"),
        wheelConnectorBogieAxleLubrication = getResultCheckItem(visualCategory, "wheelConnectorBogieAxleLubrication"),
        stopperBumperOnGirderCondition = getResultCheckItem(visualCategory, "stopperBumperOnGirderCondition"),
        stopperBumperOnGirderReinforcement = getResultCheckItem(visualCategory, "stopperBumperOnGirderReinforcement"),
        traversingGearboxTrolleyCarrierFastening = getResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierFastening"),
        traversingGearboxTrolleyCarrierCorrosion = getResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierCorrosion"),
        traversingGearboxTrolleyCarrierCracks = getResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierCracks"),
        traversingGearboxTrolleyCarrierLubricatingOil = getResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierLubricatingOil"),
        traversingGearboxTrolleyCarrierOilSeal = getResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierOilSeal"),
        driveWheelOnTrolleyWear = getResultCheckItem(visualCategory, "driveWheelOnTrolleyWear"),
        driveWheelOnTrolleyCracks = getResultCheckItem(visualCategory, "driveWheelOnTrolleyCracks"),
        driveWheelOnTrolleyDeformation = getResultCheckItem(visualCategory, "driveWheelOnTrolleyDeformation"),
        driveWheelOnTrolleyFlangeCondition = getResultCheckItem(visualCategory, "driveWheelOnTrolleyFlangeCondition"),
        driveWheelOnTrolleyChainCondition = getResultCheckItem(visualCategory, "driveWheelOnTrolleyChainCondition"),
        idleWheelOnTrolleyWear = getResultCheckItem(visualCategory, "idleWheelOnTrolleyWear"),
        idleWheelOnTrolleyCracks = getResultCheckItem(visualCategory, "idleWheelOnTrolleyCracks"),
        idleWheelOnTrolleyDeformation = getResultCheckItem(visualCategory, "idleWheelOnTrolleyDeformation"),
        idleWheelOnTrolleyFlangeCondition = getResultCheckItem(visualCategory, "idleWheelOnTrolleyFlangeCondition"),
        windingDrumGroove = getResultCheckItem(visualCategory, "windingDrumGroove"),
        windingDrumGrooveFlange = getResultCheckItem(visualCategory, "windingDrumGrooveFlange"),
        windingDrumFlanges = getResultCheckItem(visualCategory, "windingDrumFlanges"),
        brakeWear = getResultCheckItem(visualCategory, "brakeWear"),
        brakeAdjustment = getResultCheckItem(visualCategory, "brakeAdjustment"),
        hoistGearboxLubrication = getResultCheckItem(visualCategory, "hoistGearboxLubrication"),
        hoistGearboxOilSeal = getResultCheckItem(visualCategory, "hoistGearboxOilSeal"),
        pulleyDiscChainSprocketPulleyGroove = getResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyGroove"),
        pulleyDiscChainSprocketPulleyFlange = getResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyFlange"),
        pulleyDiscChainSprocketPulleyPin = getResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyPin"),
        pulleyDiscChainSprocketBearing = getResultCheckItem(visualCategory, "pulleyDiscChainSprocketBearing"),
        pulleyDiscChainSprocketPulleyGuard = getResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyGuard"),
        pulleyDiscChainSprocketWireRopeChainGuard = getResultCheckItem(visualCategory, "pulleyDiscChainSprocketWireRopeChainGuard"),
        mainHookWear = getResultCheckItem(visualCategory, "mainHookWear"),
        mainHookThroatOpening = getResultCheckItem(visualCategory, "mainHookThroatOpening"),
        mainHookSwivelNutAndBearing = getResultCheckItem(visualCategory, "mainHookSwivelNutAndBearing"),
        mainHookTrunnion = getResultCheckItem(visualCategory, "mainHookTrunnion"),
        auxiliaryHookWear = getResultCheckItem(visualCategory, "auxiliaryHookWear"),
        auxiliaryHookThroatOpening = getResultCheckItem(visualCategory, "auxiliaryHookThroatOpening"),
        auxiliaryHookSwivelNutAndBearing = getResultCheckItem(visualCategory, "auxiliaryHookSwivelNutAndBearing"),
        auxiliaryHookTrunnion = getResultCheckItem(visualCategory, "auxiliaryHookTrunnion"),
        mainWireRopeCorrosion = getResultCheckItem(visualCategory, "mainWireRopeCorrosion"),
        mainWireRopeWear = getResultCheckItem(visualCategory, "mainWireRopeWear"),
        mainWireRopeBroken = getResultCheckItem(visualCategory, "mainWireRopeBroken"),
        mainWireRopeDeformation = getResultCheckItem(visualCategory, "mainWireRopeDeformation"),
        auxiliaryWireRopeCorrosion = getResultCheckItem(visualCategory, "auxiliaryWireRopeCorrosion"),
        auxiliaryWireRopeWear = getResultCheckItem(visualCategory, "auxiliaryWireRopeWear"),
        auxiliaryWireRopeBroken = getResultCheckItem(visualCategory, "auxiliaryWireRopeBroken"),
        auxiliaryWireRopeDeformation = getResultCheckItem(visualCategory, "auxiliaryWireRopeDeformation"),
        mainChainCorrosion = getResultCheckItem(visualCategory, "mainChainCorrosion"),
        mainChainWear = getResultCheckItem(visualCategory, "mainChainWear"),
        mainChainCrackedBroken = getResultCheckItem(visualCategory, "mainChainCrackedBroken"),
        mainChainDeformation = getResultCheckItem(visualCategory, "mainChainDeformation"),
        auxiliaryChainCorrosion = getResultCheckItem(visualCategory, "auxiliaryChainCorrosion"),
        auxiliaryChainWear = getResultCheckItem(visualCategory, "auxiliaryChainWear"),
        auxiliaryChainCrackedBroken = getResultCheckItem(visualCategory, "auxiliaryChainCrackedBroken"),
        auxiliaryChainDeformation = getResultCheckItem(visualCategory, "auxiliaryChainDeformation"),
        limitSwitchLsLongTraveling = getResultCheckItem(visualCategory, "limitSwitchLsLongTraveling"),
        limitSwitchLsCrossTraveling = getResultCheckItem(visualCategory, "limitSwitchLsCrossTraveling"),
        limitSwitchLsHoisting = getResultCheckItem(visualCategory, "limitSwitchLsHoisting"),
        operatorCabinSafetyLadder = getResultCheckItem(visualCategory, "operatorCabinSafetyLadder"),
        operatorCabinDoor = getResultCheckItem(visualCategory, "operatorCabinDoor"),
        operatorCabinWindow = getResultCheckItem(visualCategory, "operatorCabinWindow"),
        operatorCabinFanAc = getResultCheckItem(visualCategory, "operatorCabinFanAc"),
        operatorCabinControlLeversButtons = getResultCheckItem(visualCategory, "operatorCabinControlLeversButtons"),
        operatorCabinPendantControl = getResultCheckItem(visualCategory, "operatorCabinPendantControl"),
        operatorCabinLighting = getResultCheckItem(visualCategory, "operatorCabinLighting"),
        operatorCabinHorn = getResultCheckItem(visualCategory, "operatorCabinHorn"),
        operatorCabinFuse = getResultCheckItem(visualCategory, "operatorCabinFuse"),
        operatorCabinCommunicationDevice = getResultCheckItem(visualCategory, "operatorCabinCommunicationDevice"),
        operatorCabinFireExtinguisher = getResultCheckItem(visualCategory, "operatorCabinFireExtinguisher"),
        operatorCabinOperatingSigns = getResultCheckItem(visualCategory, "operatorCabinOperatingSigns"),
        operatorCabinIgnitionKeyMasterSwitch = getResultCheckItem(visualCategory, "operatorCabinIgnitionKeyMasterSwitch"),
        electricalComponentsPanelConductorConnector = getResultCheckItem(visualCategory, "electricalComponentsPanelConductorConnector"),
        electricalComponentsConductorProtection = getResultCheckItem(visualCategory, "electricalComponentsConductorProtection"),
        electricalComponentsMotorInstallationSafetySystem = getResultCheckItem(visualCategory, "electricalComponentsMotorInstallationSafetySystem"),
        electricalComponentsGroundingSystem = getResultCheckItem(visualCategory, "electricalComponentsGroundingSystem"),
        electricalComponentsInstallation = getResultCheckItem(visualCategory, "electricalComponentsInstallation")
    )

    // 4. Reconstruct NDE list items
    val ndeWireRopeItems = mutableListOf<GantryCraneNdeWireRopeItem>()
    val wireRopeItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_wirerope_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()

    wireRopeItemCategories.forEach { index ->
        ndeWireRopeItems.add(GantryCraneNdeWireRopeItem(
            usage = getCheckItemValueForIndexed("nde_wirerope_item", index, "usage"),
            specDiameter = getCheckItemValueForIndexed("nde_wirerope_item", index, "specDiameter"),
            actualDiameter = getCheckItemValueForIndexed("nde_wirerope_item", index, "actualDiameter"),
            construction = getCheckItemValueForIndexed("nde_wirerope_item", index, "construction"),
            type = getCheckItemValueForIndexed("nde_wirerope_item", index, "type"),
            length = getCheckItemValueForIndexed("nde_wirerope_item", index, "length"),
            age = getCheckItemValueForIndexed("nde_wirerope_item", index, "age"),
            finding = getResultCheckItemForIndexed("nde_wirerope_item", index, "finding")
        ))
    }

    val ndeGirderItems = mutableListOf<GantryCraneNdeGirderItem>()
    val girderItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_girder_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()

    girderItemCategories.forEach { index ->
        ndeGirderItems.add(GantryCraneNdeGirderItem(
            location = getCheckItemValueForIndexed("nde_girder_item", index, "location"),
            finding = getResultCheckItemForIndexed("nde_girder_item", index, "finding")
        ))
    }

    val ndeHookCategory = "nde_mainHook_measurement"
    val mainHook = GantryCraneNdeHook(
        method = getCheckItemValue("nde_mainHook", "method"),
        specification = GantryCraneNdeMeasurement(
            a = getCheckItemValue(ndeHookCategory, "spec_a"),
            b = getCheckItemValue(ndeHookCategory, "spec_b"),
            c = getCheckItemValue(ndeHookCategory, "spec_c"),
            d = getCheckItemValue(ndeHookCategory, "spec_d"),
            e = getCheckItemValue(ndeHookCategory, "spec_e"),
            f = getCheckItemValue(ndeHookCategory, "spec_f"),
            g = getCheckItemValue(ndeHookCategory, "spec_g"),
            h = getCheckItemValue(ndeHookCategory, "spec_h"),
            finding = getResultCheckItem(ndeHookCategory, "spec_finding")
        ),
        measurementResult = GantryCraneNdeMeasurement(
            a = getCheckItemValue(ndeHookCategory, "result_a"),
            b = getCheckItemValue(ndeHookCategory, "result_b"),
            c = getCheckItemValue(ndeHookCategory, "result_c"),
            d = getCheckItemValue(ndeHookCategory, "result_d"),
            e = getCheckItemValue(ndeHookCategory, "result_e"),
            f = getCheckItemValue(ndeHookCategory, "result_f"),
            g = getCheckItemValue(ndeHookCategory, "result_g"),
            h = getCheckItemValue(ndeHookCategory, "result_h"),
            finding = getResultCheckItem(ndeHookCategory, "result_finding")
        ),
        tolerance = GantryCraneNdeMeasurement(
            a = getCheckItemValue(ndeHookCategory, "tolerance_a"),
            b = getCheckItemValue(ndeHookCategory, "tolerance_b"),
            c = getCheckItemValue(ndeHookCategory, "tolerance_c"),
            d = getCheckItemValue(ndeHookCategory, "tolerance_d"),
            e = getCheckItemValue(ndeHookCategory, "tolerance_e"),
            f = getCheckItemValue(ndeHookCategory, "tolerance_f"),
            g = getCheckItemValue(ndeHookCategory, "tolerance_g"),
            h = getCheckItemValue(ndeHookCategory, "tolerance_h"),
            finding = getResultCheckItem(ndeHookCategory, "tolerance_finding")
        )
    )

    val nonDestructiveExamination = GantryCraneNde(
        wireRope = GantryCraneNdeWireRope(
            method = getCheckItemValue("nde_wirerope", "method"),
            items = ndeWireRopeItems.toImmutableList()
        ),
        mainHook = mainHook,
        girder = GantryCraneNdeGirder(
            method = getCheckItemValue("nde_girder", "method"),
            items = ndeGirderItems.toImmutableList()
        )
    )

    // 5. Reconstruct Testing
    val staticDeflectionItems = mutableListOf<GantryCraneDeflectionItem>()
    val staticDeflectionCategories = this.checkItems.map { it.category }.filter { it.startsWith("testing_static_deflection_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()

    staticDeflectionCategories.forEach { index ->
        staticDeflectionItems.add(GantryCraneDeflectionItem(
            position = getCheckItemValueForIndexed("testing_static_deflection_item", index, "position"),
            deflection = getCheckItemValueForIndexed("testing_static_deflection_item", index, "deflection"),
            maxStandard = getCheckItemValueForIndexed("testing_static_deflection_item", index, "maxStandard"),
            remarks = getCheckItemValueForIndexed("testing_static_deflection_item", index, "remarks")
        ))
    }

    val dynamicWithoutLoadCat = "testing_dynamic_without_load"
    val dynamicWithLoadCat = "testing_dynamic_with_load"
    val staticCat = "testing_static"

    val testing = GantryCraneTesting(
        dynamicTest = GantryCraneDynamicTest(
            withoutLoad = GantryCraneDynamicWithoutLoad(
                traveling = GantryCraneDynamicTestItem(
                    shouldBe = getCheckItemValue(dynamicWithoutLoadCat, "traveling_shouldBe"),
                    testedMeasured = getCheckItemValue(dynamicWithoutLoadCat, "traveling_testedMeasured"),
                    remarks = getCheckItemValue(dynamicWithoutLoadCat, "traveling_remarks")
                ),
                traversing = GantryCraneDynamicTestItem(
                    shouldBe = getCheckItemValue(dynamicWithoutLoadCat, "traversing_shouldBe"),
                    testedMeasured = getCheckItemValue(dynamicWithoutLoadCat, "traversing_testedMeasured"),
                    remarks = getCheckItemValue(dynamicWithoutLoadCat, "traversing_remarks")
                ),
                hoisting = GantryCraneDynamicTestItem(
                    shouldBe = getCheckItemValue(dynamicWithoutLoadCat, "hoisting_shouldBe"),
                    testedMeasured = getCheckItemValue(dynamicWithoutLoadCat, "hoisting_testedMeasured"),
                    remarks = getCheckItemValue(dynamicWithoutLoadCat, "hoisting_remarks")
                ),
                safetyDevice = GantryCraneDynamicTestItem(
                    shouldBe = getCheckItemValue(dynamicWithoutLoadCat, "safetyDevice_shouldBe"),
                    testedMeasured = getCheckItemValue(dynamicWithoutLoadCat, "safetyDevice_testedMeasured"),
                    remarks = getCheckItemValue(dynamicWithoutLoadCat, "safetyDevice_remarks")
                ),
                brakeSwitch = GantryCraneDynamicTestItem(
                    shouldBe = getCheckItemValue(dynamicWithoutLoadCat, "brakeSwitch_shouldBe"),
                    testedMeasured = getCheckItemValue(dynamicWithoutLoadCat, "brakeSwitch_testedMeasured"),
                    remarks = getCheckItemValue(dynamicWithoutLoadCat, "brakeSwitch_remarks")
                ),
                brakeLockingDevice = GantryCraneDynamicTestItem(
                    shouldBe = getCheckItemValue(dynamicWithoutLoadCat, "brakeLockingDevice_shouldBe"),
                    testedMeasured = getCheckItemValue(dynamicWithoutLoadCat, "brakeLockingDevice_testedMeasured"),
                    remarks = getCheckItemValue(dynamicWithoutLoadCat, "brakeLockingDevice_remarks")
                ),
                electricalInstallation = GantryCraneDynamicTestItem(
                    shouldBe = getCheckItemValue(dynamicWithoutLoadCat, "electricalInstallation_shouldBe"),
                    testedMeasured = getCheckItemValue(dynamicWithoutLoadCat, "electricalInstallation_testedMeasured"),
                    remarks = getCheckItemValue(dynamicWithoutLoadCat, "electricalInstallation_remarks")
                )
            ),
            withLoad = GantryCraneDynamicWithLoad(
                noLoad = GantryCraneLoadTestResult(
                    hoist = getCheckItemValue(dynamicWithLoadCat, "noLoad_hoist"),
                    traversing = getCheckItemValue(dynamicWithLoadCat, "noLoad_traversing"),
                    travelling = getCheckItemValue(dynamicWithLoadCat, "noLoad_travelling"),
                    brakeSystem = getCheckItemValue(dynamicWithLoadCat, "noLoad_brakeSystem"),
                    remarks = getCheckItemValue(dynamicWithLoadCat, "noLoad_remarks")
                ),
                swl25 = GantryCraneLoadTestResult(
                    hoist = getCheckItemValue(dynamicWithLoadCat, "swl25_hoist"),
                    traversing = getCheckItemValue(dynamicWithLoadCat, "swl25_traversing"),
                    travelling = getCheckItemValue(dynamicWithLoadCat, "swl25_travelling"),
                    brakeSystem = getCheckItemValue(dynamicWithLoadCat, "swl25_brakeSystem"),
                    remarks = getCheckItemValue(dynamicWithLoadCat, "swl25_remarks")
                ),
                swl50 = GantryCraneLoadTestResult(
                    hoist = getCheckItemValue(dynamicWithLoadCat, "swl50_hoist"),
                    traversing = getCheckItemValue(dynamicWithLoadCat, "swl50_traversing"),
                    travelling = getCheckItemValue(dynamicWithLoadCat, "swl50_travelling"),
                    brakeSystem = getCheckItemValue(dynamicWithLoadCat, "swl50_brakeSystem"),
                    remarks = getCheckItemValue(dynamicWithLoadCat, "swl50_remarks")
                ),
                swl75 = GantryCraneLoadTestResult(
                    hoist = getCheckItemValue(dynamicWithLoadCat, "swl75_hoist"),
                    traversing = getCheckItemValue(dynamicWithLoadCat, "swl75_traversing"),
                    travelling = getCheckItemValue(dynamicWithLoadCat, "swl75_travelling"),
                    brakeSystem = getCheckItemValue(dynamicWithLoadCat, "swl75_brakeSystem"),
                    remarks = getCheckItemValue(dynamicWithLoadCat, "swl75_remarks")
                ),
                swl100 = GantryCraneLoadTestResult(
                    hoist = getCheckItemValue(dynamicWithLoadCat, "swl100_hoist"),
                    traversing = getCheckItemValue(dynamicWithLoadCat, "swl100_traversing"),
                    travelling = getCheckItemValue(dynamicWithLoadCat, "swl100_travelling"),
                    brakeSystem = getCheckItemValue(dynamicWithLoadCat, "swl100_brakeSystem"),
                    remarks = getCheckItemValue(dynamicWithLoadCat, "swl100_remarks")
                )
            )
        ),
        staticTest = GantryCraneStaticTest(
            load = getCheckItemValue(staticCat, "load"),
            deflectionStandard = GantryCraneDeflectionStandard(
                designBased = getCheckItemValue(staticCat, "deflection_designBased"),
                spanLength = getCheckItemValue(staticCat, "deflection_spanLength"),
                calculation = getCheckItemValue(staticCat, "deflection_calculation")
            ),
            deflectionMeasurement = staticDeflectionItems.toImmutableList()
        )
    )


    // 6. Reconstruct Conclusion
    val summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }
    val recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }
    val conclusion = GantryCraneConclusion(
        summary = summary.toImmutableList(),
        recommendations = recommendations.toImmutableList()
    )

    // 7. Assemble the final report
    val report = GantryCraneInspectionReport(
        equipmentType = inspection.equipmentType,
        examinationType = inspection.examinationType,
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        nonDestructiveExamination = nonDestructiveExamination,
        testing = testing,
        conclusion = conclusion
    )

    return GantryCraneUiState(gantryCraneInspectionReport = report)
}