package com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane

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
 * Maps the OverheadCraneUiState to an InspectionWithDetailsDomain model without using reflection.
 * All UI state data is flattened into InspectionDomain, InspectionFindingDomain, or InspectionCheckItemDomain.
 */
fun OverheadCraneUiState.toInspectionWithDetailsDomain(
    currentTime: String,
    isEdited: Boolean,
    reportId: Long? = null
): InspectionWithDetailsDomain {
    val report = this.overheadCraneInspectionReport
    val generalData = report.generalData
    val technicalData = report.technicalData
    val visualInspection = report.visualInspection
    val nde = report.nonDestructiveExamination
    val testing = report.testing
    val conclusion = report.conclusion

    // Hardcoded values as requested
    val inspectionId = reportId ?: 0L
    val documentType = DocumentType.LAPORAN
    val inspectionType = InspectionType.PAA
    val subInspectionType = SubInspectionType.Overhead_Crane

    // 1. Map main and general data to InspectionDomain
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = report.extraId,
        moreExtraId = report.moreExtraId,
        documentType = documentType,
        inspectionType = inspectionType,
        subInspectionType = subInspectionType,
        equipmentType = report.equipmentType,
        examinationType = report.examinationType,
        ownerName = generalData.owner,
        ownerAddress = generalData.address,
        usageLocation = generalData.unitLocation,
        addressUsageLocation = generalData.user,
        driveType = generalData.hoistType,
        serialNumber = generalData.serialNumber,
        permitNumber = generalData.permitNumber,
        capacity = generalData.liftingCapacity,
        manufacturer = ManufacturerDomain(
            name = generalData.manufacturer,
            brandOrType = generalData.brandType,
            year = generalData.yearOfManufacture
        ),
        createdAt = currentTime,
        speed = "",
        floorServed = "",
        reportDate = generalData.inspectionDate,
        nextInspectionDate = "",
        inspectorName = "",
        status = "",
        isSynced = false,
        isEdited = isEdited
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun addCheckItem(category: String, itemName: String, value: String?) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = false,
                result = value ?: ""
            )
        )
    }

    fun addResultCheckItem(category: String, itemName: String, value: OverheadCraneInspectionResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.status ?: false,
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
    val v = visualInspection
    addResultCheckItem(visualCategory, "foundationAnchorBoltCorrosion", v.foundationAnchorBoltCorrosion)
    addResultCheckItem(visualCategory, "foundationAnchorBoltCracks", v.foundationAnchorBoltCracks)
    addResultCheckItem(visualCategory, "foundationAnchorBoltDeformation", v.foundationAnchorBoltDeformation)
    addResultCheckItem(visualCategory, "foundationAnchorBoltFastening", v.foundationAnchorBoltFastening)
    addResultCheckItem(visualCategory, "columnFrameCorrosion", v.columnFrameCorrosion)
    addResultCheckItem(visualCategory, "columnFrameCracks", v.columnFrameCracks)
    addResultCheckItem(visualCategory, "columnFrameDeformation", v.columnFrameDeformation)
    addResultCheckItem(visualCategory, "columnFrameFastening", v.columnFrameFastening)
    addResultCheckItem(visualCategory, "columnFrameCrossBracing", v.columnFrameCrossBracing)
    addResultCheckItem(visualCategory, "columnFrameDiagonalBracing", v.columnFrameDiagonalBracing)
    addResultCheckItem(visualCategory, "ladderCorrosion", v.ladderCorrosion)
    addResultCheckItem(visualCategory, "ladderCracks", v.ladderCracks)
    addResultCheckItem(visualCategory, "ladderDeformation", v.ladderDeformation)
    addResultCheckItem(visualCategory, "ladderFastening", v.ladderFastening)
    addResultCheckItem(visualCategory, "workPlatformCorrosion", v.workPlatformCorrosion)
    addResultCheckItem(visualCategory, "workPlatformCracks", v.workPlatformCracks)
    addResultCheckItem(visualCategory, "workPlatformDeformation", v.workPlatformDeformation)
    addResultCheckItem(visualCategory, "workPlatformFastening", v.workPlatformFastening)
    addResultCheckItem(visualCategory, "railMountingBeamCorrosion", v.railMountingBeamCorrosion)
    addResultCheckItem(visualCategory, "railMountingBeamCracks", v.railMountingBeamCracks)
    addResultCheckItem(visualCategory, "railMountingBeamDeformation", v.railMountingBeamDeformation)
    addResultCheckItem(visualCategory, "railMountingBeamFastening", v.railMountingBeamFastening)
    addResultCheckItem(visualCategory, "travelingRailCorrosion", v.travelingRailCorrosion)
    addResultCheckItem(visualCategory, "travelingRailCracks", v.travelingRailCracks)
    addResultCheckItem(visualCategory, "travelingRailJoint", v.travelingRailJoint)
    addResultCheckItem(visualCategory, "travelingRailStraightness", v.travelingRailStraightness)
    addResultCheckItem(visualCategory, "travelingRailAlignmentBetweenRails", v.travelingRailAlignmentBetweenRails)
    addResultCheckItem(visualCategory, "travelingRailEvennessBetweenRails", v.travelingRailEvennessBetweenRails)
    addResultCheckItem(visualCategory, "travelingRailGapBetweenRailJoints", v.travelingRailGapBetweenRailJoints)
    addResultCheckItem(visualCategory, "travelingRailFastener", v.travelingRailFastener)
    addResultCheckItem(visualCategory, "travelingRailStopper", v.travelingRailStopper)
    addResultCheckItem(visualCategory, "traversingRailCorrosion", v.traversingRailCorrosion)
    addResultCheckItem(visualCategory, "traversingRailCracks", v.traversingRailCracks)
    addResultCheckItem(visualCategory, "traversingRailJoint", v.traversingRailJoint)
    addResultCheckItem(visualCategory, "traversingRailStraightness", v.traversingRailStraightness)
    addResultCheckItem(visualCategory, "traversingRailAlignmentBetweenRails", v.traversingRailAlignmentBetweenRails)
    addResultCheckItem(visualCategory, "traversingRailEvennessBetweenRails", v.traversingRailEvennessBetweenRails)
    addResultCheckItem(visualCategory, "traversingRailGapBetweenRailJoints", v.traversingRailGapBetweenRailJoints)
    addResultCheckItem(visualCategory, "traversingRailFastener", v.traversingRailFastener)
    addResultCheckItem(visualCategory, "traversingRailStopper", v.traversingRailStopper)
    addResultCheckItem(visualCategory, "girderCorrosion", v.girderCorrosion)
    addResultCheckItem(visualCategory, "girderCracks", v.girderCracks)
    addResultCheckItem(visualCategory, "girderCamber", v.girderCamber)
    addResultCheckItem(visualCategory, "girderJoint", v.girderJoint)
    addResultCheckItem(visualCategory, "girderEndJoint", v.girderEndJoint)
    addResultCheckItem(visualCategory, "girderTruckMountOnGirder", v.girderTruckMountOnGirder)
    addResultCheckItem(visualCategory, "travelingGearboxGirderCorrosion", v.travelingGearboxGirderCorrosion)
    addResultCheckItem(visualCategory, "travelingGearboxGirderCracks", v.travelingGearboxGirderCracks)
    addResultCheckItem(visualCategory, "travelingGearboxGirderLubricatingOil", v.travelingGearboxGirderLubricatingOil)
    addResultCheckItem(visualCategory, "travelingGearboxGirderOilSeal", v.travelingGearboxGirderOilSeal)
    addResultCheckItem(visualCategory, "driveWheelWear", v.driveWheelWear)
    addResultCheckItem(visualCategory, "driveWheelCracks", v.driveWheelCracks)
    addResultCheckItem(visualCategory, "driveWheelDeformation", v.driveWheelDeformation)
    addResultCheckItem(visualCategory, "driveWheelFlangeCondition", v.driveWheelFlangeCondition)
    addResultCheckItem(visualCategory, "driveWheelChainCondition", v.driveWheelChainCondition)
    addResultCheckItem(visualCategory, "idleWheelSecurity", v.idleWheelSecurity)
    addResultCheckItem(visualCategory, "idleWheelCracks", v.idleWheelCracks)
    addResultCheckItem(visualCategory, "idleWheelDeformation", v.idleWheelDeformation)
    addResultCheckItem(visualCategory, "idleWheelFlangeCondition", v.idleWheelFlangeCondition)
    addResultCheckItem(visualCategory, "wheelConnectorBogieAxleStraightness", v.wheelConnectorBogieAxleStraightness)
    addResultCheckItem(visualCategory, "wheelConnectorBogieAxleCrossJoint", v.wheelConnectorBogieAxleCrossJoint)
    addResultCheckItem(visualCategory, "wheelConnectorBogieAxleLubrication", v.wheelConnectorBogieAxleLubrication)
    addResultCheckItem(visualCategory, "stopperBumperOnGirderCondition", v.stopperBumperOnGirderCondition)
    addResultCheckItem(visualCategory, "stopperBumperOnGirderReinforcement", v.stopperBumperOnGirderReinforcement)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierFastening", v.traversingGearboxTrolleyCarrierFastening)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierCorrosion", v.traversingGearboxTrolleyCarrierCorrosion)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierCracks", v.traversingGearboxTrolleyCarrierCracks)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierLubricatingOil", v.traversingGearboxTrolleyCarrierLubricatingOil)
    addResultCheckItem(visualCategory, "traversingGearboxTrolleyCarrierOilSeal", v.traversingGearboxTrolleyCarrierOilSeal)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyWear", v.driveWheelOnTrolleyWear)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyCracks", v.driveWheelOnTrolleyCracks)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyDeformation", v.driveWheelOnTrolleyDeformation)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyFlangeCondition", v.driveWheelOnTrolleyFlangeCondition)
    addResultCheckItem(visualCategory, "driveWheelOnTrolleyChainCondition", v.driveWheelOnTrolleyChainCondition)
    addResultCheckItem(visualCategory, "idleWheelOnTrolleyWear", v.idleWheelOnTrolleyWear)
    addResultCheckItem(visualCategory, "idleWheelOnTrolleyCracks", v.idleWheelOnTrolleyCracks)
    addResultCheckItem(visualCategory, "idleWheelOnTrolleyDeformation", v.idleWheelOnTrolleyDeformation)
    addResultCheckItem(visualCategory, "idleWheelOnTrolleyFlangeCondition", v.idleWheelOnTrolleyFlangeCondition)
    addResultCheckItem(visualCategory, "wheelConnectorBogieAxleOnTrolleyStraightness", v.wheelConnectorBogieAxleOnTrolleyStraightness)
    addResultCheckItem(visualCategory, "wheelConnectorBogieAxleOnTrolleyCrossJoint", v.wheelConnectorBogieAxleOnTrolleyCrossJoint)
    addResultCheckItem(visualCategory, "wheelConnectorBogieAxleOnTrolleyLubrication", v.wheelConnectorBogieAxleOnTrolleyLubrication)
    addResultCheckItem(visualCategory, "stopperBumperOnGirderOnTrolleyCondition", v.stopperBumperOnGirderOnTrolleyCondition)
    addResultCheckItem(visualCategory, "stopperBumperOnGirderOnTrolleyReinforcement", v.stopperBumperOnGirderOnTrolleyReinforcement)
    addResultCheckItem(visualCategory, "windingDrumGroove", v.windingDrumGroove)
    addResultCheckItem(visualCategory, "windingDrumGrooveFlange", v.windingDrumGrooveFlange)
    addResultCheckItem(visualCategory, "windingDrumFlanges", v.windingDrumFlanges)
    addResultCheckItem(visualCategory, "brakeWear", v.brakeWear)
    addResultCheckItem(visualCategory, "brakeAdjustment", v.brakeAdjustment)
    addResultCheckItem(visualCategory, "hoistGearboxLubrication", v.hoistGearboxLubrication)
    addResultCheckItem(visualCategory, "hoistGearboxOilSeal", v.hoistGearboxOilSeal)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyGroove", v.pulleyDiscChainSprocketPulleyGroove)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyFlange", v.pulleyDiscChainSprocketPulleyFlange)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyPin", v.pulleyDiscChainSprocketPulleyPin)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketBearing", v.pulleyDiscChainSprocketBearing)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketPulleyGuard", v.pulleyDiscChainSprocketPulleyGuard)
    addResultCheckItem(visualCategory, "pulleyDiscChainSprocketWireRopeChainGuard", v.pulleyDiscChainSprocketWireRopeChainGuard)
    addResultCheckItem(visualCategory, "mainHookWear", v.mainHookWear)
    addResultCheckItem(visualCategory, "mainHookThroatOpening", v.mainHookThroatOpening)
    addResultCheckItem(visualCategory, "mainHookSwivelNutAndBearing", v.mainHookSwivelNutAndBearing)
    addResultCheckItem(visualCategory, "mainHookTrunnion", v.mainHookTrunnion)
    addResultCheckItem(visualCategory, "auxiliaryHookWear", v.auxiliaryHookWear)
    addResultCheckItem(visualCategory, "auxiliaryHookThroatOpening", v.auxiliaryHookThroatOpening)
    addResultCheckItem(visualCategory, "auxiliaryHookSwivelNutAndBearing", v.auxiliaryHookSwivelNutAndBearing)
    addResultCheckItem(visualCategory, "auxiliaryHookTrunnion", v.auxiliaryHookTrunnion)
    addResultCheckItem(visualCategory, "mainWireRopeCorrosion", v.mainWireRopeCorrosion)
    addResultCheckItem(visualCategory, "mainWireRopeWear", v.mainWireRopeWear)
    addResultCheckItem(visualCategory, "mainWireRopeBroken", v.mainWireRopeBroken)
    addResultCheckItem(visualCategory, "mainWireRopeDeformation", v.mainWireRopeDeformation)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeCorrosion", v.auxiliaryWireRopeCorrosion)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeWear", v.auxiliaryWireRopeWear)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeBroken", v.auxiliaryWireRopeBroken)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeDeformation", v.auxiliaryWireRopeDeformation)
    addResultCheckItem(visualCategory, "mainChainCorrosion", v.mainChainCorrosion)
    addResultCheckItem(visualCategory, "mainChainWear", v.mainChainWear)
    addResultCheckItem(visualCategory, "mainChainCrackedBroken", v.mainChainCrackedBroken)
    addResultCheckItem(visualCategory, "mainChainDeformation", v.mainChainDeformation)
    addResultCheckItem(visualCategory, "auxiliaryChainCorrosion", v.auxiliaryChainCorrosion)
    addResultCheckItem(visualCategory, "auxiliaryChainWear", v.auxiliaryChainWear)
    addResultCheckItem(visualCategory, "auxiliaryChainCrackedBroken", v.auxiliaryChainCrackedBroken)
    addResultCheckItem(visualCategory, "auxiliaryChainDeformation", v.auxiliaryChainDeformation)
    addResultCheckItem(visualCategory, "limitSwitchLsLongTraveling", v.limitSwitchLsLongTraveling)
    addResultCheckItem(visualCategory, "limitSwitchLsCrossTraveling", v.limitSwitchLsCrossTraveling)
    addResultCheckItem(visualCategory, "limitSwitchLsHoisting", v.limitSwitchLsHoisting)
    addResultCheckItem(visualCategory, "operatorRoomCabinSafetyLadder", v.operatorRoomCabinSafetyLadder)
    addResultCheckItem(visualCategory, "operatorRoomCabinDoor", v.operatorRoomCabinDoor)
    addResultCheckItem(visualCategory, "operatorRoomCabinWindow", v.operatorRoomCabinWindow)
    addResultCheckItem(visualCategory, "operatorRoomCabinFanAC", v.operatorRoomCabinFanAC)
    addResultCheckItem(visualCategory, "operatorRoomCabinControlLeversButtons", v.operatorRoomCabinControlLeversButtons)
    addResultCheckItem(visualCategory, "operatorRoomCabinPendantControl", v.operatorRoomCabinPendantControl)
    addResultCheckItem(visualCategory, "operatorRoomCabinLighting", v.operatorRoomCabinLighting)
    addResultCheckItem(visualCategory, "operatorRoomCabinHorn", v.operatorRoomCabinHorn)
    addResultCheckItem(visualCategory, "operatorRoomCabinFuseProtection", v.operatorRoomCabinFuseProtection)
    addResultCheckItem(visualCategory, "operatorRoomCabinCommunicationDevice", v.operatorRoomCabinCommunicationDevice)
    addResultCheckItem(visualCategory, "operatorRoomCabinFireExtinguisher", v.operatorRoomCabinFireExtinguisher)
    addResultCheckItem(visualCategory, "operatorRoomCabinOperatingSigns", v.operatorRoomCabinOperatingSigns)
    addResultCheckItem(visualCategory, "operatorRoomCabinIgnitionKeyMasterSwitch", v.operatorRoomCabinIgnitionKeyMasterSwitch)
    addResultCheckItem(visualCategory, "electricalComponentPanelConnectorConductor", v.electricalComponentPanelConnectorConductor)
    addResultCheckItem(visualCategory, "electricalComponentConductorProtection", v.electricalComponentConductorProtection)
    addResultCheckItem(visualCategory, "electricalComponentMotorInstallationSafetySystem", v.electricalComponentMotorInstallationSafetySystem)
    addResultCheckItem(visualCategory, "electricalComponentGroundingSystem", v.electricalComponentGroundingSystem)
    addResultCheckItem(visualCategory, "electricalComponentInstallation", v.electricalComponentInstallation)

    // 5. Map NDE to CheckItems
    addCheckItem("nde_chain", "method", nde.chain.method)
    nde.chain.items.forEachIndexed { index, item ->
        val cat = "nde_chain_item_$index"
        addCheckItem(cat, "name", item.name)
        addCheckItem(cat, "specificationMm", item.specificationMm)
        addCheckItem(cat, "measurementMm", item.measurementMm)
        addCheckItem(cat, "lengthIncrease", item.lengthIncrease)
        addCheckItem(cat, "wear", item.wear)
        addCheckItem(cat, "safetyFactor", item.safetyFactor)
        addResultCheckItem(cat, "finding", item.finding)
    }

    nde.mainHook.let { hook ->
        val cat = "nde_mainHook"
        addCheckItem(cat, "method", hook.method)
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
        addCheckItem(cat, "safetyLatch_shouldBe", test.safetyLatch.shouldBe)
        addCheckItem(cat, "safetyLatch_testedMeasured", test.safetyLatch.testedMeasured)
        addCheckItem(cat, "safetyLatch_remarks", test.safetyLatch.remarks)
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
        addCheckItem(cat, "notes", test.notes)
        test.deflectionMeasurement.singleGirder.forEach { (key, value) ->
            addCheckItem(cat, "deflection_single_$key", value)
        }
        test.deflectionMeasurement.doubleGirder.forEach { (key, value) ->
            addCheckItem(cat, "deflection_double_$key", value)
        }
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
        testResults = emptyList()
    )
}

fun InspectionWithDetailsDomain.toOverheadCraneUiState(): OverheadCraneUiState {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getCheckItemValue(category: String, itemName: String): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    fun getResultCheckItem(category: String, itemName: String): OverheadCraneInspectionResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return OverheadCraneInspectionResult(
            status = item?.status ?: false,
            result = item?.result ?: ""
        )
    }

    val generalData = OverheadCraneGeneralData(
        owner = inspection.ownerName ?: "",
        address = inspection.ownerAddress ?: "",
        user = inspection.addressUsageLocation ?: "",
        unitLocation = inspection.usageLocation ?: "",
        hoistType = inspection.driveType ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        brandType = inspection.manufacturer?.brandOrType ?: "",
        yearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumber = inspection.serialNumber ?: "",
        liftingCapacity = inspection.capacity ?: "",
        permitNumber = inspection.permitNumber ?: "",
        personInCharge = getCheckItemValue("general_data", "personInCharge"),
        intendedUse = getCheckItemValue("general_data", "intendedUse"),
        operatorCertificate = getCheckItemValue("general_data", "operatorCertificate"),
        technicalDataManual = getCheckItemValue("general_data", "technicalDataManual"),
        inspectionDate = inspection.reportDate ?: ""
    )

    val techCategory = "technical_data"
    val technicalData = OverheadCraneTechnicalData(
        specifications = OverheadCraneTechSpecs(
            liftingHeight = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "spec_liftingHeight_hoisting"),
                traveling = getCheckItemValue(techCategory, "spec_liftingHeight_traveling"),
                traversing = getCheckItemValue(techCategory, "spec_liftingHeight_traversing")
            ),
            girderLength = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "spec_girderLength_hoisting"),
                traveling = getCheckItemValue(techCategory, "spec_girderLength_traveling"),
                traversing = getCheckItemValue(techCategory, "spec_girderLength_traversing")
            ),
            speed = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "spec_speed_hoisting"),
                traveling = getCheckItemValue(techCategory, "spec_speed_traveling"),
                traversing = getCheckItemValue(techCategory, "spec_speed_traversing")
            )
        ),
        driveMotor = OverheadCraneDriveMotor(
            capacity = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_capacity_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_capacity_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_capacity_traversing")
            ),
            powerKw = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_powerKw_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_powerKw_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_powerKw_traversing")
            ),
            type = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_type_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_type_traversing")
            ),
            rpm = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_rpm_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_rpm_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_rpm_traversing")
            ),
            voltageV = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_voltageV_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_voltageV_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_voltageV_traversing")
            ),
            currentA = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_currentA_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_currentA_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_currentA_traversing")
            ),
            frequencyHz = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "motor_frequencyHz_hoisting"),
                traveling = getCheckItemValue(techCategory, "motor_frequencyHz_traveling"),
                traversing = getCheckItemValue(techCategory, "motor_frequencyHz_traversing")
            )
        ),
        startingResistor = OverheadCraneStartingResistor(
            type = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "resistor_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "resistor_type_traveling"),
                traversing = getCheckItemValue(techCategory, "resistor_type_traversing")
            ),
            voltageV = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "resistor_voltageV_hoisting"),
                traveling = getCheckItemValue(techCategory, "resistor_voltageV_traveling"),
                traversing = getCheckItemValue(techCategory, "resistor_voltageV_traversing")
            ),
            currentA = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "resistor_currentA_hoisting"),
                traveling = getCheckItemValue(techCategory, "resistor_currentA_traveling"),
                traversing = getCheckItemValue(techCategory, "resistor_currentA_traversing")
            )
        ),
        brake = OverheadCraneBrake(
            type = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "brake_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "brake_type_traveling"),
                traversing = getCheckItemValue(techCategory, "brake_type_traversing")
            ),
            model = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "brake_model_hoisting"),
                traveling = getCheckItemValue(techCategory, "brake_model_traveling"),
                traversing = getCheckItemValue(techCategory, "brake_model_traversing")
            )
        ),
        controllerBrake = OverheadCraneControllerBrake(
            type = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "cBrake_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "cBrake_type_traveling"),
                traversing = getCheckItemValue(techCategory, "cBrake_type_traversing")
            ),
            model = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "cBrake_model_hoisting"),
                traveling = getCheckItemValue(techCategory, "cBrake_model_traveling"),
                traversing = getCheckItemValue(techCategory, "cBrake_model_traversing")
            )
        ),
        hook = OverheadCraneHook(
            type = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "hook_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "hook_type_traveling"),
                traversing = getCheckItemValue(techCategory, "hook_type_traversing")
            ),
            capacity = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "hook_capacity_hoisting"),
                traveling = getCheckItemValue(techCategory, "hook_capacity_traveling"),
                traversing = getCheckItemValue(techCategory, "hook_capacity_traversing")
            ),
            material = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "hook_material_hoisting"),
                traveling = getCheckItemValue(techCategory, "hook_material_traveling"),
                traversing = getCheckItemValue(techCategory, "hook_material_traversing")
            )
        ),
        wireRope = OverheadCraneWireRope(
            type = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "wireRope_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "wireRope_type_traveling"),
                traversing = getCheckItemValue(techCategory, "wireRope_type_traversing")
            ),
            construction = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "wireRope_construction_hoisting"),
                traveling = getCheckItemValue(techCategory, "wireRope_construction_traveling"),
                traversing = getCheckItemValue(techCategory, "wireRope_construction_traversing")
            ),
            diameter = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "wireRope_diameter_hoisting"),
                traveling = getCheckItemValue(techCategory, "wireRope_diameter_traveling"),
                traversing = getCheckItemValue(techCategory, "wireRope_diameter_traversing")
            ),
            length = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "wireRope_length_hoisting"),
                traveling = getCheckItemValue(techCategory, "wireRope_length_traveling"),
                traversing = getCheckItemValue(techCategory, "wireRope_length_traversing")
            )
        ),
        chain = OverheadCraneChain(
            type = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "chain_type_hoisting"),
                traveling = getCheckItemValue(techCategory, "chain_type_traveling"),
                traversing = getCheckItemValue(techCategory, "chain_type_traversing")
            ),
            construction = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "chain_construction_hoisting"),
                traveling = getCheckItemValue(techCategory, "chain_construction_traveling"),
                traversing = getCheckItemValue(techCategory, "chain_construction_traversing")
            ),
            diameter = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "chain_diameter_hoisting"),
                traveling = getCheckItemValue(techCategory, "chain_diameter_traveling"),
                traversing = getCheckItemValue(techCategory, "chain_diameter_traversing")
            ),
            length = OverheadCraneMovementData(
                hoisting = getCheckItemValue(techCategory, "chain_length_hoisting"),
                traveling = getCheckItemValue(techCategory, "chain_length_traveling"),
                traversing = getCheckItemValue(techCategory, "chain_length_traversing")
            )
        )
    )

    val visualInspection = OverheadCraneVisualInspection(
        foundationAnchorBoltCorrosion = getResultCheckItem("visual_inspection", "foundationAnchorBoltCorrosion"),
        foundationAnchorBoltCracks = getResultCheckItem("visual_inspection", "foundationAnchorBoltCracks"),
        foundationAnchorBoltDeformation = getResultCheckItem("visual_inspection", "foundationAnchorBoltDeformation"),
        foundationAnchorBoltFastening = getResultCheckItem("visual_inspection", "foundationAnchorBoltFastening"),
        columnFrameCorrosion = getResultCheckItem("visual_inspection", "columnFrameCorrosion"),
        columnFrameCracks = getResultCheckItem("visual_inspection", "columnFrameCracks"),
        columnFrameDeformation = getResultCheckItem("visual_inspection", "columnFrameDeformation"),
        columnFrameFastening = getResultCheckItem("visual_inspection", "columnFrameFastening"),
        columnFrameCrossBracing = getResultCheckItem("visual_inspection", "columnFrameCrossBracing"),
        columnFrameDiagonalBracing = getResultCheckItem("visual_inspection", "columnFrameDiagonalBracing"),
        ladderCorrosion = getResultCheckItem("visual_inspection", "ladderCorrosion"),
        ladderCracks = getResultCheckItem("visual_inspection", "ladderCracks"),
        ladderDeformation = getResultCheckItem("visual_inspection", "ladderDeformation"),
        ladderFastening = getResultCheckItem("visual_inspection", "ladderFastening"),
        workPlatformCorrosion = getResultCheckItem("visual_inspection", "workPlatformCorrosion"),
        workPlatformCracks = getResultCheckItem("visual_inspection", "workPlatformCracks"),
        workPlatformDeformation = getResultCheckItem("visual_inspection", "workPlatformDeformation"),
        workPlatformFastening = getResultCheckItem("visual_inspection", "workPlatformFastening"),
        railMountingBeamCorrosion = getResultCheckItem("visual_inspection", "railMountingBeamCorrosion"),
        railMountingBeamCracks = getResultCheckItem("visual_inspection", "railMountingBeamCracks"),
        railMountingBeamDeformation = getResultCheckItem("visual_inspection", "railMountingBeamDeformation"),
        railMountingBeamFastening = getResultCheckItem("visual_inspection", "railMountingBeamFastening"),
        travelingRailCorrosion = getResultCheckItem("visual_inspection", "travelingRailCorrosion"),
        travelingRailCracks = getResultCheckItem("visual_inspection", "travelingRailCracks"),
        travelingRailJoint = getResultCheckItem("visual_inspection", "travelingRailJoint"),
        travelingRailStraightness = getResultCheckItem("visual_inspection", "travelingRailStraightness"),
        travelingRailAlignmentBetweenRails = getResultCheckItem("visual_inspection", "travelingRailAlignmentBetweenRails"),
        travelingRailEvennessBetweenRails = getResultCheckItem("visual_inspection", "travelingRailEvennessBetweenRails"),
        travelingRailGapBetweenRailJoints = getResultCheckItem("visual_inspection", "travelingRailGapBetweenRailJoints"),
        travelingRailFastener = getResultCheckItem("visual_inspection", "travelingRailFastener"),
        travelingRailStopper = getResultCheckItem("visual_inspection", "travelingRailStopper"),
        traversingRailCorrosion = getResultCheckItem("visual_inspection", "traversingRailCorrosion"),
        traversingRailCracks = getResultCheckItem("visual_inspection", "traversingRailCracks"),
        traversingRailJoint = getResultCheckItem("visual_inspection", "traversingRailJoint"),
        traversingRailStraightness = getResultCheckItem("visual_inspection", "traversingRailStraightness"),
        traversingRailAlignmentBetweenRails = getResultCheckItem("visual_inspection", "traversingRailAlignmentBetweenRails"),
        traversingRailEvennessBetweenRails = getResultCheckItem("visual_inspection", "traversingRailEvennessBetweenRails"),
        traversingRailGapBetweenRailJoints = getResultCheckItem("visual_inspection", "traversingRailGapBetweenRailJoints"),
        traversingRailFastener = getResultCheckItem("visual_inspection", "traversingRailFastener"),
        traversingRailStopper = getResultCheckItem("visual_inspection", "traversingRailStopper"),
        girderCorrosion = getResultCheckItem("visual_inspection", "girderCorrosion"),
        girderCracks = getResultCheckItem("visual_inspection", "girderCracks"),
        girderCamber = getResultCheckItem("visual_inspection", "girderCamber"),
        girderJoint = getResultCheckItem("visual_inspection", "girderJoint"),
        girderEndJoint = getResultCheckItem("visual_inspection", "girderEndJoint"),
        girderTruckMountOnGirder = getResultCheckItem("visual_inspection", "girderTruckMountOnGirder"),
        travelingGearboxGirderCorrosion = getResultCheckItem("visual_inspection", "travelingGearboxGirderCorrosion"),
        travelingGearboxGirderCracks = getResultCheckItem("visual_inspection", "travelingGearboxGirderCracks"),
        travelingGearboxGirderLubricatingOil = getResultCheckItem("visual_inspection", "travelingGearboxGirderLubricatingOil"),
        travelingGearboxGirderOilSeal = getResultCheckItem("visual_inspection", "travelingGearboxGirderOilSeal"),
        driveWheelWear = getResultCheckItem("visual_inspection", "driveWheelWear"),
        driveWheelCracks = getResultCheckItem("visual_inspection", "driveWheelCracks"),
        driveWheelDeformation = getResultCheckItem("visual_inspection", "driveWheelDeformation"),
        driveWheelFlangeCondition = getResultCheckItem("visual_inspection", "driveWheelFlangeCondition"),
        driveWheelChainCondition = getResultCheckItem("visual_inspection", "driveWheelChainCondition"),
        idleWheelSecurity = getResultCheckItem("visual_inspection", "idleWheelSecurity"),
        idleWheelCracks = getResultCheckItem("visual_inspection", "idleWheelCracks"),
        idleWheelDeformation = getResultCheckItem("visual_inspection", "idleWheelDeformation"),
        idleWheelFlangeCondition = getResultCheckItem("visual_inspection", "idleWheelFlangeCondition"),
        wheelConnectorBogieAxleStraightness = getResultCheckItem("visual_inspection", "wheelConnectorBogieAxleStraightness"),
        wheelConnectorBogieAxleCrossJoint = getResultCheckItem("visual_inspection", "wheelConnectorBogieAxleCrossJoint"),
        wheelConnectorBogieAxleLubrication = getResultCheckItem("visual_inspection", "wheelConnectorBogieAxleLubrication"),
        stopperBumperOnGirderCondition = getResultCheckItem("visual_inspection", "stopperBumperOnGirderCondition"),
        stopperBumperOnGirderReinforcement = getResultCheckItem("visual_inspection", "stopperBumperOnGirderReinforcement"),
        traversingGearboxTrolleyCarrierFastening = getResultCheckItem("visual_inspection", "traversingGearboxTrolleyCarrierFastening"),
        traversingGearboxTrolleyCarrierCorrosion = getResultCheckItem("visual_inspection", "traversingGearboxTrolleyCarrierCorrosion"),
        traversingGearboxTrolleyCarrierCracks = getResultCheckItem("visual_inspection", "traversingGearboxTrolleyCarrierCracks"),
        traversingGearboxTrolleyCarrierLubricatingOil = getResultCheckItem("visual_inspection", "traversingGearboxTrolleyCarrierLubricatingOil"),
        traversingGearboxTrolleyCarrierOilSeal = getResultCheckItem("visual_inspection", "traversingGearboxTrolleyCarrierOilSeal"),
        driveWheelOnTrolleyWear = getResultCheckItem("visual_inspection", "driveWheelOnTrolleyWear"),
        driveWheelOnTrolleyCracks = getResultCheckItem("visual_inspection", "driveWheelOnTrolleyCracks"),
        driveWheelOnTrolleyDeformation = getResultCheckItem("visual_inspection", "driveWheelOnTrolleyDeformation"),
        driveWheelOnTrolleyFlangeCondition = getResultCheckItem("visual_inspection", "driveWheelOnTrolleyFlangeCondition"),
        driveWheelOnTrolleyChainCondition = getResultCheckItem("visual_inspection", "driveWheelOnTrolleyChainCondition"),
        idleWheelOnTrolleyWear = getResultCheckItem("visual_inspection", "idleWheelOnTrolleyWear"),
        idleWheelOnTrolleyCracks = getResultCheckItem("visual_inspection", "idleWheelOnTrolleyCracks"),
        idleWheelOnTrolleyDeformation = getResultCheckItem("visual_inspection", "idleWheelOnTrolleyDeformation"),
        idleWheelOnTrolleyFlangeCondition = getResultCheckItem("visual_inspection", "idleWheelOnTrolleyFlangeCondition"),
        wheelConnectorBogieAxleOnTrolleyStraightness = getResultCheckItem("visual_inspection", "wheelConnectorBogieAxleOnTrolleyStraightness"),
        wheelConnectorBogieAxleOnTrolleyCrossJoint = getResultCheckItem("visual_inspection", "wheelConnectorBogieAxleOnTrolleyCrossJoint"),
        wheelConnectorBogieAxleOnTrolleyLubrication = getResultCheckItem("visual_inspection", "wheelConnectorBogieAxleOnTrolleyLubrication"),
        stopperBumperOnGirderOnTrolleyCondition = getResultCheckItem("visual_inspection", "stopperBumperOnGirderOnTrolleyCondition"),
        stopperBumperOnGirderOnTrolleyReinforcement = getResultCheckItem("visual_inspection", "stopperBumperOnGirderOnTrolleyReinforcement"),
        windingDrumGroove = getResultCheckItem("visual_inspection", "windingDrumGroove"),
        windingDrumGrooveFlange = getResultCheckItem("visual_inspection", "windingDrumGrooveFlange"),
        windingDrumFlanges = getResultCheckItem("visual_inspection", "windingDrumFlanges"),
        brakeWear = getResultCheckItem("visual_inspection", "brakeWear"),
        brakeAdjustment = getResultCheckItem("visual_inspection", "brakeAdjustment"),
        hoistGearboxLubrication = getResultCheckItem("visual_inspection", "hoistGearboxLubrication"),
        hoistGearboxOilSeal = getResultCheckItem("visual_inspection", "hoistGearboxOilSeal"),
        pulleyDiscChainSprocketPulleyGroove = getResultCheckItem("visual_inspection", "pulleyDiscChainSprocketPulleyGroove"),
        pulleyDiscChainSprocketPulleyFlange = getResultCheckItem("visual_inspection", "pulleyDiscChainSprocketPulleyFlange"),
        pulleyDiscChainSprocketPulleyPin = getResultCheckItem("visual_inspection", "pulleyDiscChainSprocketPulleyPin"),
        pulleyDiscChainSprocketBearing = getResultCheckItem("visual_inspection", "pulleyDiscChainSprocketBearing"),
        pulleyDiscChainSprocketPulleyGuard = getResultCheckItem("visual_inspection", "pulleyDiscChainSprocketPulleyGuard"),
        pulleyDiscChainSprocketWireRopeChainGuard = getResultCheckItem("visual_inspection", "pulleyDiscChainSprocketWireRopeChainGuard"),
        mainHookWear = getResultCheckItem("visual_inspection", "mainHookWear"),
        mainHookThroatOpening = getResultCheckItem("visual_inspection", "mainHookThroatOpening"),
        mainHookSwivelNutAndBearing = getResultCheckItem("visual_inspection", "mainHookSwivelNutAndBearing"),
        mainHookTrunnion = getResultCheckItem("visual_inspection", "mainHookTrunnion"),
        auxiliaryHookWear = getResultCheckItem("visual_inspection", "auxiliaryHookWear"),
        auxiliaryHookThroatOpening = getResultCheckItem("visual_inspection", "auxiliaryHookThroatOpening"),
        auxiliaryHookSwivelNutAndBearing = getResultCheckItem("visual_inspection", "auxiliaryHookSwivelNutAndBearing"),
        auxiliaryHookTrunnion = getResultCheckItem("visual_inspection", "auxiliaryHookTrunnion"),
        mainWireRopeCorrosion = getResultCheckItem("visual_inspection", "mainWireRopeCorrosion"),
        mainWireRopeWear = getResultCheckItem("visual_inspection", "mainWireRopeWear"),
        mainWireRopeBroken = getResultCheckItem("visual_inspection", "mainWireRopeBroken"),
        mainWireRopeDeformation = getResultCheckItem("visual_inspection", "mainWireRopeDeformation"),
        auxiliaryWireRopeCorrosion = getResultCheckItem("visual_inspection", "auxiliaryWireRopeCorrosion"),
        auxiliaryWireRopeWear = getResultCheckItem("visual_inspection", "auxiliaryWireRopeWear"),
        auxiliaryWireRopeBroken = getResultCheckItem("visual_inspection", "auxiliaryWireRopeBroken"),
        auxiliaryWireRopeDeformation = getResultCheckItem("visual_inspection", "auxiliaryWireRopeDeformation"),
        mainChainCorrosion = getResultCheckItem("visual_inspection", "mainChainCorrosion"),
        mainChainWear = getResultCheckItem("visual_inspection", "mainChainWear"),
        mainChainCrackedBroken = getResultCheckItem("visual_inspection", "mainChainCrackedBroken"),
        mainChainDeformation = getResultCheckItem("visual_inspection", "mainChainDeformation"),
        auxiliaryChainCorrosion = getResultCheckItem("visual_inspection", "auxiliaryChainCorrosion"),
        auxiliaryChainWear = getResultCheckItem("visual_inspection", "auxiliaryChainWear"),
        auxiliaryChainCrackedBroken = getResultCheckItem("visual_inspection", "auxiliaryChainCrackedBroken"),
        auxiliaryChainDeformation = getResultCheckItem("visual_inspection", "auxiliaryChainDeformation"),
        limitSwitchLsLongTraveling = getResultCheckItem("visual_inspection", "limitSwitchLsLongTraveling"),
        limitSwitchLsCrossTraveling = getResultCheckItem("visual_inspection", "limitSwitchLsCrossTraveling"),
        limitSwitchLsHoisting = getResultCheckItem("visual_inspection", "limitSwitchLsHoisting"),
        operatorRoomCabinSafetyLadder = getResultCheckItem("visual_inspection", "operatorRoomCabinSafetyLadder"),
        operatorRoomCabinDoor = getResultCheckItem("visual_inspection", "operatorRoomCabinDoor"),
        operatorRoomCabinWindow = getResultCheckItem("visual_inspection", "operatorRoomCabinWindow"),
        operatorRoomCabinFanAC = getResultCheckItem("visual_inspection", "operatorRoomCabinFanAC"),
        operatorRoomCabinControlLeversButtons = getResultCheckItem("visual_inspection", "operatorRoomCabinControlLeversButtons"),
        operatorRoomCabinPendantControl = getResultCheckItem("visual_inspection", "operatorRoomCabinPendantControl"),
        operatorRoomCabinLighting = getResultCheckItem("visual_inspection", "operatorRoomCabinLighting"),
        operatorRoomCabinHorn = getResultCheckItem("visual_inspection", "operatorRoomCabinHorn"),
        operatorRoomCabinFuseProtection = getResultCheckItem("visual_inspection", "operatorRoomCabinFuseProtection"),
        operatorRoomCabinCommunicationDevice = getResultCheckItem("visual_inspection", "operatorRoomCabinCommunicationDevice"),
        operatorRoomCabinFireExtinguisher = getResultCheckItem("visual_inspection", "operatorRoomCabinFireExtinguisher"),
        operatorRoomCabinOperatingSigns = getResultCheckItem("visual_inspection", "operatorRoomCabinOperatingSigns"),
        operatorRoomCabinIgnitionKeyMasterSwitch = getResultCheckItem("visual_inspection", "operatorRoomCabinIgnitionKeyMasterSwitch"),
        electricalComponentPanelConnectorConductor = getResultCheckItem("visual_inspection", "electricalComponentPanelConnectorConductor"),
        electricalComponentConductorProtection = getResultCheckItem("visual_inspection", "electricalComponentConductorProtection"),
        electricalComponentMotorInstallationSafetySystem = getResultCheckItem("visual_inspection", "electricalComponentMotorInstallationSafetySystem"),
        electricalComponentGroundingSystem = getResultCheckItem("visual_inspection", "electricalComponentGroundingSystem"),
        electricalComponentInstallation = getResultCheckItem("visual_inspection", "electricalComponentInstallation")
    )

    val ndeChainItems = mutableListOf<OverheadCraneNdeChainItem>()
    val chainItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_chain_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    chainItemCategories.forEach { index ->
        val cat = "nde_chain_item_$index"
        ndeChainItems.add(OverheadCraneNdeChainItem(
            name = getCheckItemValue(cat, "name"),
            specificationMm = getCheckItemValue(cat, "specificationMm"),
            measurementMm = getCheckItemValue(cat, "measurementMm"),
            lengthIncrease = getCheckItemValue(cat, "lengthIncrease"),
            wear = getCheckItemValue(cat, "wear"),
            safetyFactor = getCheckItemValue(cat, "safetyFactor"),
            finding = getResultCheckItem(cat, "finding")
        ))
    }

    val mainHook = OverheadCraneNdeHook(
        method = getCheckItemValue("nde_mainHook", "method"),
        specification = OverheadCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainHook", "spec_a"), b = getCheckItemValue("nde_mainHook", "spec_b"),
            c = getCheckItemValue("nde_mainHook", "spec_c"), d = getCheckItemValue("nde_mainHook", "spec_d"),
            e = getCheckItemValue("nde_mainHook", "spec_e"), f = getCheckItemValue("nde_mainHook", "spec_f"),
            g = getCheckItemValue("nde_mainHook", "spec_g"), h = getCheckItemValue("nde_mainHook", "spec_h"),
            finding = getResultCheckItem("nde_mainHook", "spec_finding")
        ),
        measurementResult = OverheadCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainHook", "result_a"), b = getCheckItemValue("nde_mainHook", "result_b"),
            c = getCheckItemValue("nde_mainHook", "result_c"), d = getCheckItemValue("nde_mainHook", "result_d"),
            e = getCheckItemValue("nde_mainHook", "result_e"), f = getCheckItemValue("nde_mainHook", "result_f"),
            g = getCheckItemValue("nde_mainHook", "result_g"), h = getCheckItemValue("nde_mainHook", "result_h"),
            finding = getResultCheckItem("nde_mainHook", "result_finding")
        ),
        tolerance = OverheadCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainHook", "tolerance_a"), b = getCheckItemValue("nde_mainHook", "tolerance_b"),
            c = getCheckItemValue("nde_mainHook", "tolerance_c"), d = getCheckItemValue("nde_mainHook", "tolerance_d"),
            e = getCheckItemValue("nde_mainHook", "tolerance_e"), f = getCheckItemValue("nde_mainHook", "tolerance_f"),
            g = getCheckItemValue("nde_mainHook", "tolerance_g"), h = getCheckItemValue("nde_mainHook", "tolerance_h"),
            finding = getResultCheckItem("nde_mainHook", "tolerance_finding")
        )
    )

    val nonDestructiveExamination = OverheadCraneNde(
        chain = OverheadCraneNdeChain(
            method = getCheckItemValue("nde_chain", "method"),
            items = ndeChainItems.toImmutableList()
        ),
        mainHook = mainHook
    )

    val dynWithoutLoadCat = "testing_dynamic_without_load"
    val dynWithLoadCat = "testing_dynamic_with_load"
    val testing = OverheadCraneTesting(
        dynamicTest = OverheadCraneDynamicTest(
            withoutLoad = OverheadCraneDynamicWithoutLoad(
                traveling = OverheadCraneDynamicTestItem(getCheckItemValue(dynWithoutLoadCat, "traveling_shouldBe"), getCheckItemValue(dynWithoutLoadCat, "traveling_testedMeasured"), getCheckItemValue(dynWithoutLoadCat, "traveling_remarks")),
                traversing = OverheadCraneDynamicTestItem(getCheckItemValue(dynWithoutLoadCat, "traversing_shouldBe"), getCheckItemValue(dynWithoutLoadCat, "traversing_testedMeasured"), getCheckItemValue(dynWithoutLoadCat, "traversing_remarks")),
                hoisting = OverheadCraneDynamicTestItem(getCheckItemValue(dynWithoutLoadCat, "hoisting_shouldBe"), getCheckItemValue(dynWithoutLoadCat, "hoisting_testedMeasured"), getCheckItemValue(dynWithoutLoadCat, "hoisting_remarks")),
                safetyLatch = OverheadCraneDynamicTestItem(getCheckItemValue(dynWithoutLoadCat, "safetyLatch_shouldBe"), getCheckItemValue(dynWithoutLoadCat, "safetyLatch_testedMeasured"), getCheckItemValue(dynWithoutLoadCat, "safetyLatch_remarks")),
                brakeSwitch = OverheadCraneDynamicTestItem(getCheckItemValue(dynWithoutLoadCat, "brakeSwitch_shouldBe"), getCheckItemValue(dynWithoutLoadCat, "brakeSwitch_testedMeasured"), getCheckItemValue(dynWithoutLoadCat, "brakeSwitch_remarks")),
                brakeLockingDevice = OverheadCraneDynamicTestItem(getCheckItemValue(dynWithoutLoadCat, "brakeLockingDevice_shouldBe"), getCheckItemValue(dynWithoutLoadCat, "brakeLockingDevice_testedMeasured"), getCheckItemValue(dynWithoutLoadCat, "brakeLockingDevice_remarks")),
                electricalInstallation = OverheadCraneDynamicTestItem(getCheckItemValue(dynWithoutLoadCat, "electricalInstallation_shouldBe"), getCheckItemValue(dynWithoutLoadCat, "electricalInstallation_testedMeasured"), getCheckItemValue(dynWithoutLoadCat, "electricalInstallation_remarks"))
            ),
            withLoad = OverheadCraneDynamicWithLoad(
                noLoad = OverheadCraneLoadTestResult(getCheckItemValue(dynWithLoadCat, "noLoad_hoist"), getCheckItemValue(dynWithLoadCat, "noLoad_traversing"), getCheckItemValue(dynWithLoadCat, "noLoad_travelling"), getCheckItemValue(dynWithLoadCat, "noLoad_brakeSystem"), getCheckItemValue(dynWithLoadCat, "noLoad_remarks")),
                swl25 = OverheadCraneLoadTestResult(getCheckItemValue(dynWithLoadCat, "swl25_hoist"), getCheckItemValue(dynWithLoadCat, "swl25_traversing"), getCheckItemValue(dynWithLoadCat, "swl25_travelling"), getCheckItemValue(dynWithLoadCat, "swl25_brakeSystem"), getCheckItemValue(dynWithLoadCat, "swl25_remarks")),
                swl50 = OverheadCraneLoadTestResult(getCheckItemValue(dynWithLoadCat, "swl50_hoist"), getCheckItemValue(dynWithLoadCat, "swl50_traversing"), getCheckItemValue(dynWithLoadCat, "swl50_travelling"), getCheckItemValue(dynWithLoadCat, "swl50_brakeSystem"), getCheckItemValue(dynWithLoadCat, "swl50_remarks")),
                swl75 = OverheadCraneLoadTestResult(getCheckItemValue(dynWithLoadCat, "swl75_hoist"), getCheckItemValue(dynWithLoadCat, "swl75_traversing"), getCheckItemValue(dynWithLoadCat, "swl75_travelling"), getCheckItemValue(dynWithLoadCat, "swl75_brakeSystem"), getCheckItemValue(dynWithLoadCat, "swl75_remarks")),
                swl100 = OverheadCraneLoadTestResult(getCheckItemValue(dynWithLoadCat, "swl100_hoist"), getCheckItemValue(dynWithLoadCat, "swl100_traversing"), getCheckItemValue(dynWithLoadCat, "swl100_travelling"), getCheckItemValue(dynWithLoadCat, "swl100_brakeSystem"), getCheckItemValue(dynWithLoadCat, "swl100_remarks"))
            )
        ),
        staticTest = OverheadCraneStaticTest(
            load = getCheckItemValue("testing_static", "load"),
            notes = getCheckItemValue("testing_static", "notes"),
            deflectionMeasurement = OverheadCraneDeflectionMeasurement(
                singleGirder = checkItemsByCategory["testing_static"]
                    ?.filter { it.itemName.startsWith("deflection_single_") }
                    ?.associate { it.itemName.removePrefix("deflection_single_") to (it.result ?: "") } ?: emptyMap(),
                doubleGirder = checkItemsByCategory["testing_static"]
                    ?.filter { it.itemName.startsWith("deflection_double_") }
                    ?.associate { it.itemName.removePrefix("deflection_double_") to (it.result ?: "") } ?: emptyMap()
            )
        )
    )

    val summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }
    val recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }
    val conclusion = OverheadCraneConclusion(
        summary = summary.toImmutableList(),
        recommendations = recommendations.toImmutableList()
    )

    val report = OverheadCraneInspectionReport(
        extraId = inspection.extraId,
        moreExtraId = inspection.moreExtraId,
        equipmentType = inspection.equipmentType,
        examinationType = inspection.examinationType,
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        nonDestructiveExamination = nonDestructiveExamination,
        testing = testing,
        conclusion = conclusion
    )

    return OverheadCraneUiState(overheadCraneInspectionReport = report)
}