package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.gantrycrane.*
import com.nakersolutionid.nakersolutionid.domain.model.*

// FIXED: Centralized BAP category constants to ensure consistency across all mappers.
private object BAPCategory {
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
    val visualInspection = GantryCraneVisualInspection(
        anchorBoltsCorrosionMemenuhi = findStatus(visualCategory, "foundationAnchorBoltCorrosion"),
        anchorBoltsCorrosionTidakMemenuhi = !findStatus(visualCategory, "foundationAnchorBoltCorrosion"),
        anchorBoltsCorrosionResult = findString(visualCategory, "foundationAnchorBoltCorrosion"),
        anchorBoltsCracksMemenuhi = findStatus(visualCategory, "foundationAnchorBoltCracks"),
        anchorBoltsCracksTidakMemenuhi = !findStatus(visualCategory, "foundationAnchorBoltCracks"),
        anchorBoltsCracksResult = findString(visualCategory, "foundationAnchorBoltCracks"),
        anchorBoltsDeformationMemenuhi = findStatus(visualCategory, "foundationAnchorBoltDeformation"),
        anchorBoltsDeformationTidakMemenuhi = !findStatus(visualCategory, "foundationAnchorBoltDeformation"),
        anchorBoltsDeformationResult = findString(visualCategory, "foundationAnchorBoltDeformation"),
        anchorBoltsFasteningMemenuhi = findStatus(visualCategory, "foundationAnchorBoltTightness"),
        anchorBoltsFasteningTidakMemenuhi = !findStatus(visualCategory, "foundationAnchorBoltTightness"),
        anchorBoltsFasteningResult = findString(visualCategory, "foundationAnchorBoltTightness"),
        columnFrameCorrosionMemenuhi = findStatus(visualCategory, "columnFrameCorrosion"),
        columnFrameCorrosionTidakMemenuhi = !findStatus(visualCategory, "columnFrameCorrosion"),
        columnFrameCorrosionResult = findString(visualCategory, "columnFrameCorrosion"),
        columnFrameCracksMemenuhi = findStatus(visualCategory, "columnFrameCracks"),
        columnFrameCracksTidakMemenuhi = !findStatus(visualCategory, "columnFrameCracks"),
        columnFrameCracksResult = findString(visualCategory, "columnFrameCracks"),
        columnFrameDeformationMemenuhi = findStatus(visualCategory, "columnFrameDeformation"),
        columnFrameDeformationTidakMemenuhi = !findStatus(visualCategory, "columnFrameDeformation"),
        columnFrameDeformationResult = findString(visualCategory, "columnFrameDeformation"),
        columnFrameFasteningMemenuhi = findStatus(visualCategory, "columnFrameFastening"),
        columnFrameFasteningTidakMemenuhi = !findStatus(visualCategory, "columnFrameFastening"),
        columnFrameFasteningResult = findString(visualCategory, "columnFrameFastening"),
        columnFrameTransverseReinforcementMemenuhi = findStatus(visualCategory, "columnFrameCrossBracing"),
        columnFrameTransverseReinforcementTidakMemenuhi = !findStatus(visualCategory, "columnFrameCrossBracing"),
        columnFrameTransverseReinforcementResult = findString(visualCategory, "columnFrameCrossBracing"),
        columnFrameDiagonalReinforcementMemenuhi = findStatus(visualCategory, "columnFrameDiagonalBracing"),
        columnFrameDiagonalReinforcementTidakMemenuhi = !findStatus(visualCategory, "columnFrameDiagonalBracing"),
        columnFrameDiagonalReinforcementResult = findString(visualCategory, "columnFrameDiagonalBracing"),
        ladderCorrosionMemenuhi = findStatus(visualCategory, "ladderCorrosion"),
        ladderCorrosionTidakMemenuhi = !findStatus(visualCategory, "ladderCorrosion"),
        ladderCorrosionResult = findString(visualCategory, "ladderCorrosion"),
        ladderCracksMemenuhi = findStatus(visualCategory, "ladderCracks"),
        ladderCracksTidakMemenuhi = !findStatus(visualCategory, "ladderCracks"),
        ladderCracksResult = findString(visualCategory, "ladderCracks"),
        ladderDeformationMemenuhi = findStatus(visualCategory, "ladderDeformation"),
        ladderDeformationTidakMemenuhi = !findStatus(visualCategory, "ladderDeformation"),
        ladderDeformationResult = findString(visualCategory, "ladderDeformation"),
        ladderFasteningMemenuhi = findStatus(visualCategory, "ladderFastening"),
        ladderFasteningTidakMemenuhi = !findStatus(visualCategory, "ladderFastening"),
        ladderFasteningResult = findString(visualCategory, "ladderFastening"),
        workingFloorCorrosionMemenuhi = findStatus(visualCategory, "workPlatformCorrosion"),
        workingFloorCorrosionTidakMemenuhi = !findStatus(visualCategory, "workPlatformCorrosion"),
        workingFloorCorrosionResult = findString(visualCategory, "workPlatformCorrosion"),
        workingFloorCracksMemenuhi = findStatus(visualCategory, "workPlatformCracks"),
        workingFloorCracksTidakMemenuhi = !findStatus(visualCategory, "workPlatformCracks"),
        workingFloorCracksResult = findString(visualCategory, "workPlatformCracks"),
        workingFloorDeformationMemenuhi = findStatus(visualCategory, "workPlatformDeformation"),
        workingFloorDeformationTidakMemenuhi = !findStatus(visualCategory, "workPlatformDeformation"),
        workingFloorDeformationResult = findString(visualCategory, "workPlatformDeformation"),
        workingFloorFasteningMemenuhi = findStatus(visualCategory, "workPlatformFastening"),
        workingFloorFasteningTidakMemenuhi = !findStatus(visualCategory, "workPlatformFastening"),
        workingFloorFasteningResult = findString(visualCategory, "workPlatformFastening"),
        railSupportBeamCorrosionMemenuhi = findStatus(visualCategory, "railMountingBeamCorrosion"),
        railSupportBeamCorrosionTidakMemenuhi = !findStatus(visualCategory, "railMountingBeamCorrosion"),
        railSupportBeamCorrosionResult = findString(visualCategory, "railMountingBeamCorrosion"),
        railSupportBeamCracksMemenuhi = findStatus(visualCategory, "railMountingBeamCracks"),
        railSupportBeamCracksTidakMemenuhi = !findStatus(visualCategory, "railMountingBeamCracks"),
        railSupportBeamCracksResult = findString(visualCategory, "railMountingBeamCracks"),
        railSupportBeamDeformationMemenuhi = findStatus(visualCategory, "railMountingBeamDeformation"),
        railSupportBeamDeformationTidakMemenuhi = !findStatus(visualCategory, "railMountingBeamDeformation"),
        railSupportBeamDeformationResult = findString(visualCategory, "railMountingBeamDeformation"),
        railSupportBeamFasteningMemenuhi = findStatus(visualCategory, "railMountingBeamFastening"),
        railSupportBeamFasteningTidakMemenuhi = !findStatus(visualCategory, "railMountingBeamFastening"),
        railSupportBeamFasteningResult = findString(visualCategory, "railMountingBeamFastening"),
        travelingRailCorrosionMemenuhi = findStatus(visualCategory, "travelingRailCorrosion"),
        travelingRailCracksTidakMemenuhi = !findStatus(visualCategory, "travelingRailCorrosion"),
        travelingRailCracksResult = findString(visualCategory, "travelingRailCorrosion"),
        travelingCracksMemenuhi = findStatus(visualCategory, "travelingRailCracks"),
        travelingCracksTidakMemenuhi = !findStatus(visualCategory, "travelingRailCracks"), // FIXED
        travelingCracksResult = findString(visualCategory, "travelingRailCracks"),
        travelingRailConnectionMemenuhi = findStatus(visualCategory, "travelingRailJoint"),
        travelingRailConnectionTidakMemenuhi = !findStatus(visualCategory, "travelingRailJoint"),
        travelingRailConnectionResult = findString(visualCategory, "travelingRailJoint"),
        travelingRailAlignmentMemenuhi = findStatus(visualCategory, "travelingRailStraightness"),
        travelingRailAlignmentTidakMemenuhi = !findStatus(visualCategory, "travelingRailStraightness"),
        travelingRailAlignmentResult = findString(visualCategory, "travelingRailStraightness"),
        travelingInterRailAlignmentMemenuhi = findStatus(visualCategory, "travelingRailAlignmentBetweenRails"),
        travelingInterRailAlignmentTidakMemenuhi = !findStatus(visualCategory, "travelingRailAlignmentBetweenRails"),
        travelingInterRailAlignmentResult = findString(visualCategory, "travelingRailAlignmentBetweenRails"),
        travelingInterRailFlatnessMemenuhi = findStatus(visualCategory, "travelingRailEvennessBetweenRails"),
        travelingInterRailFlatnessTidakMemenuhi = !findStatus(visualCategory, "travelingRailEvennessBetweenRails"),
        travelingInterRailFlatnessResult = findString(visualCategory, "travelingRailEvennessBetweenRails"),
        travelingRailConnectionGapMemenuhi = findStatus(visualCategory, "travelingRailGapBetweenRailJoints"),
        travelingRailConnectionGapTidakMemenuhi = !findStatus(visualCategory, "travelingRailGapBetweenRailJoints"),
        travelingRailConnectionGapResult = findString(visualCategory, "travelingRailGapBetweenRailJoints"),
        travelingRailFastenerMemenuhi = findStatus(visualCategory, "travelingRailFastener"),
        travelingRailFastenerTidakMemenuhi = !findStatus(visualCategory, "travelingRailFastener"),
        travelingRailFastenerResult = findString(visualCategory, "travelingRailFastener"),
        travelingRailStopperMemenuhi = findStatus(visualCategory, "travelingRailStopper"),
        travelingRailStopperTidakMemenuhi = !findStatus(visualCategory, "travelingRailStopper"),
        travelingRailStopperResult = findString(visualCategory, "travelingRailStopper"),
        traversingRailCorrosionMemenuhi = findStatus(visualCategory, "traversingRailCorrosion"),
        traversingRailCorrosionTidakMemenuhi = !findStatus(visualCategory, "traversingRailCorrosion"),
        traversingRailCorrosionResult = findString(visualCategory, "traversingRailCorrosion"),
        traversingRailCracksMemenuhi = findStatus(visualCategory, "traversingRailCracks"),
        traversingRailCracksTidakMemenuhi = !findStatus(visualCategory, "traversingRailCracks"),
        traversingRailCracksResult = findString(visualCategory, "traversingRailCracks"),
        traversingRailRailConnectionMemenuhi = findStatus(visualCategory, "traversingRailJoint"),
        traversingRailRailConnectionTidakMemenuhi = !findStatus(visualCategory, "traversingRailJoint"),
        traversingRailRailConnectionResult = findString(visualCategory, "traversingRailJoint"),
        traversingRailRailAlignmentMemenuhi = findStatus(visualCategory, "traversingRailStraightness"),
        traversingRailRailAlignmentTidakMemenuhi = !findStatus(visualCategory, "traversingRailStraightness"),
        traversingRailRailAlignmentResult = findString(visualCategory, "traversingRailStraightness"),
        traversingRailInterRailAlignmentMemenuhi = findStatus(visualCategory, "traversingRailAlignmentBetweenRails"),
        traversingRailInterRailAlignmentTidakMemenuhi = !findStatus(visualCategory, "traversingRailAlignmentBetweenRails"),
        traversingRailInterRailAlignmentResult = findString(visualCategory, "traversingRailAlignmentBetweenRails"),
        traversingRailInterRailFlatnessMemenuhi = findStatus(visualCategory, "traversingRailEvennessBetweenRails"),
        traversingRailInterRailFlatnessTidakMemenuhi = !findStatus(visualCategory, "traversingRailEvennessBetweenRails"),
        traversingRailInterRailFlatnessResult = findString(visualCategory, "traversingRailEvennessBetweenRails"),
        traversingRailRailConnectionGapMemenuhi = findStatus(visualCategory, "traversingRailGapBetweenRailJoints"),
        traversingRailRailConnectionGapTidakMemenuhi = !findStatus(visualCategory, "traversingRailGapBetweenRailJoints"),
        traversingRailRailConnectionGapResult = findString(visualCategory, "traversingRailGapBetweenRailJoints"),
        traversingRailRailFastenerMemenuhi = findStatus(visualCategory, "traversingRailFastener"),
        traversingRailRailFastenerTidakMemenuhi = !findStatus(visualCategory, "traversingRailFastener"),
        traversingRailRailFastenerResult = findString(visualCategory, "traversingRailFastener"),
        traversingRailRailStopperMemenuhi = findStatus(visualCategory, "traversingRailStopper"),
        traversingRailRailStopperTidakMemenuhi = !findStatus(visualCategory, "traversingRailStopper"),
        traversingRailRailStopperResult = findString(visualCategory, "traversingRailStopper"),
        girderCorrosionMemenuhi = findStatus(visualCategory, "girderCorrosion"),
        girderCorrosionTidakMemenuhi = !findStatus(visualCategory, "girderCorrosion"),
        girderCorrosionResult = findString(visualCategory, "girderCorrosion"),
        girderCracksMemenuhi = findStatus(visualCategory, "girderCracks"),
        girderCracksTidakMemenuhi = !findStatus(visualCategory, "girderCracks"),
        girderCracksResult = findString(visualCategory, "girderCracks"),
        girderCamberMemenuhi = findStatus(visualCategory, "girderCamber"),
        girderCamberTidakMemenuhi = !findStatus(visualCategory, "girderCamber"),
        girderCamberResult = findString(visualCategory, "girderCamber"),
        girderConnectionMemenuhi = findStatus(visualCategory, "girderJoint"),
        girderConnectionTidakMemenuhi = !findStatus(visualCategory, "girderJoint"),
        girderConnectionResult = findString(visualCategory, "girderJoint"),
        girderEndGirderConnectionMemenuhi = findStatus(visualCategory, "girderEndJoint"),
        girderEndGirderConnectionTidakMemenuhi = !findStatus(visualCategory, "girderEndJoint"),
        girderEndGirderConnectionResult = findString(visualCategory, "girderEndJoint"),
        girderTruckMountingOnGirderMemenuhi = findStatus(visualCategory, "girderTruckMountOnGirder"),
        girderTruckMountingOnGirderTidakMemenuhi = !findStatus(visualCategory, "girderTruckMountOnGirder"),
        girderTruckMountingOnGirderResult = findString(visualCategory, "girderTruckMountOnGirder"),
        travelingGearboxCorrosionMemenuhi = findStatus(visualCategory, "travelingGearboxGirderCorrosion"),
        travelingGearboxCorrosionTidakMemenuhi = !findStatus(visualCategory, "travelingGearboxGirderCorrosion"),
        travelingGearboxCorrosionResult = findString(visualCategory, "travelingGearboxGirderCorrosion"),
        travelingGearboxCracksMemenuhi = findStatus(visualCategory, "travelingGearboxGirderCracks"),
        travelingGearboxCracksTidakMemenuhi = !findStatus(visualCategory, "travelingGearboxGirderCracks"),
        travelingGearboxCracksResult = findString(visualCategory, "travelingGearboxGirderCracks"),
        travelingGearboxLubricatingOilMemenuhi = findStatus(visualCategory, "travelingGearboxGirderLubricatingOil"),
        travelingGearboxLubricatingOilTidakMemenuhi = !findStatus(visualCategory, "travelingGearboxGirderLubricatingOil"),
        travelingGearboxLubricatingOilResult = findString(visualCategory, "travelingGearboxGirderLubricatingOil"),
        travelingGearboxOilSealMemenuhi = findStatus(visualCategory, "travelingGearboxGirderOilSeal"),
        travelingGearboxOilSealTidakMemenuhi = !findStatus(visualCategory, "travelingGearboxGirderOilSeal"),
        travelingGearboxOilSealResult = findString(visualCategory, "travelingGearboxGirderOilSeal"),
        driveWheelsWearMemenuhi = findStatus(visualCategory, "driveWheelWear"),
        driveWheelsWearTidakMemenuhi = !findStatus(visualCategory, "driveWheelWear"),
        driveWheelsWearResult = findString(visualCategory, "driveWheelWear"),
        driveWheelsCracksMemenuhi = findStatus(visualCategory, "driveWheelCracks"),
        driveWheelsCracksTidakMemenuhi = !findStatus(visualCategory, "driveWheelCracks"),
        driveWheelsCracksResult = findString(visualCategory, "driveWheelCracks"),
        driveWheelsDeformationMemenuhi = findStatus(visualCategory, "driveWheelDeformation"),
        driveWheelsDeformationTidakMemenuhi = !findStatus(visualCategory, "driveWheelDeformation"),
        driveWheelsDeformationResult = findString(visualCategory, "driveWheelDeformation"),
        driveWheelsFlangeConditionMemenuhi = findStatus(visualCategory, "driveWheelFlangeCondition"),
        driveWheelsFlangeConditionTidakMemenuhi = !findStatus(visualCategory, "driveWheelFlangeCondition"),
        driveWheelsFlangeConditionResult = findString(visualCategory, "driveWheelFlangeCondition"),
        driveWheelsChainConditionMemenuhi = findStatus(visualCategory, "driveWheelChainCondition"),
        driveWheelsChainConditionTidakMemenuhi = !findStatus(visualCategory, "driveWheelChainCondition"),
        driveWheelsChainConditionResult = findString(visualCategory, "driveWheelChainCondition"),
        idleWheelsSafetyMemenuhi = findStatus(visualCategory, "idleWheelSecurity"),
        idleWheelsSafetyTidakMemenuhi = !findStatus(visualCategory, "idleWheelSecurity"),
        idleWheelsSafetyResult = findString(visualCategory, "idleWheelSecurity"),
        idleWheelsCracksMemenuhi = findStatus(visualCategory, "idleWheelCracks"),
        idleWheelsCracksTidakMemenuhi = !findStatus(visualCategory, "idleWheelCracks"),
        idleWheelsCracksResult = findString(visualCategory, "idleWheelCracks"),
        idleWheelsDeformationMemenuhi = findStatus(visualCategory, "idleWheelDeformation"),
        idleWheelsDeformationTidakMemenuhi = !findStatus(visualCategory, "idleWheelDeformation"),
        idleWheelsDeformationResult = findString(visualCategory, "idleWheelDeformation"),
        idleWheelsFlangeConditionMemenuhi = findStatus(visualCategory, "idleWheelFlangeCondition"),
        idleWheelsFlangeConditionTidakMemenuhi = !findStatus(visualCategory, "idleWheelFlangeCondition"),
        idleWheelsFlangeConditionResult = findString(visualCategory, "idleWheelFlangeCondition"),
        wheelConnectorAlignmentMemenuhi = findStatus(visualCategory, "wheelConnectorBogieAxleStraightness"),
        wheelConnectorAlignmentTidakMemenuhi = !findStatus(visualCategory, "wheelConnectorBogieAxleStraightness"),
        wheelConnectorAlignmentResult = findString(visualCategory, "wheelConnectorBogieAxleStraightness"),
        wheelConnectorCrossJointMemenuhi = findStatus(visualCategory, "wheelConnectorBogieAxleCrossJoint"),
        wheelConnectorCrossJointTidakMemenuhi = !findStatus(visualCategory, "wheelConnectorBogieAxleCrossJoint"),
        wheelConnectorCrossJointResult = findString(visualCategory, "wheelConnectorBogieAxleCrossJoint"),
        wheelConnectorLubricationMemenuhi = findStatus(visualCategory, "wheelConnectorBogieAxleLubrication"),
        wheelConnectorLubricationTidakMemenuhi = !findStatus(visualCategory, "wheelConnectorBogieAxleLubrication"),
        wheelConnectorLubricationResult = findString(visualCategory, "wheelConnectorBogieAxleLubrication"),
        girderStopperConditionMemenuhi = findStatus(visualCategory, "stopperBumperOnGirderCondition"),
        girderStopperConditionTidakMemenuhi = !findStatus(visualCategory, "stopperBumperOnGirderCondition"),
        girderStopperConditionResult = findString(visualCategory, "stopperBumperOnGirderCondition"),
        girderStopperReinforcementMemenuhi = findStatus(visualCategory, "stopperBumperOnGirderReinforcement"),
        girderStopperReinforcementTidakMemenuhi = !findStatus(visualCategory, "stopperBumperOnGirderReinforcement"),
        girderStopperReinforcementResult = findString(visualCategory, "stopperBumperOnGirderReinforcement"),
        trolleyTraversingGearboxFasteningMemenuhi = findStatus(visualCategory, "traversingGearboxTrolleyCarrierFastening"),
        trolleyTraversingGearboxFasteningTidakMemenuhi = !findStatus(visualCategory, "traversingGearboxTrolleyCarrierFastening"),
        trolleyTraversingGearboxFasteningResult = findString(visualCategory, "traversingGearboxTrolleyCarrierFastening"),
        trolleyTraversingGearboxCorrosionMemenuhi = findStatus(visualCategory, "traversingGearboxTrolleyCarrierCorrosion"),
        trolleyTraversingGearboxCorrosionTidakMemenuhi = !findStatus(visualCategory, "traversingGearboxTrolleyCarrierCorrosion"),
        trolleyTraversingGearboxCorrosionResult = findString(visualCategory, "traversingGearboxTrolleyCarrierCorrosion"),
        trolleyTraversingGearboxCracksMemenuhi = findStatus(visualCategory, "traversingGearboxTrolleyCarrierCracks"),
        trolleyTraversingGearboxCracksTidakMemenuhi = !findStatus(visualCategory, "traversingGearboxTrolleyCarrierCracks"),
        trolleyTraversingGearboxCracksResult = findString(visualCategory, "traversingGearboxTrolleyCarrierCracks"),
        trolleyTraversingGearboxLubricatingOilMemenuhi = findStatus(visualCategory, "traversingGearboxTrolleyCarrierLubricatingOil"),
        trolleyTraversingGearboxLubricatingOilTidakMemenuhi = !findStatus(visualCategory, "traversingGearboxTrolleyCarrierLubricatingOil"),
        trolleyTraversingGearboxLubricatingOilResult = findString(visualCategory, "traversingGearboxTrolleyCarrierLubricatingOil"),
        trolleyTraversingGearboxOilSealMemenuhi = findStatus(visualCategory, "traversingGearboxTrolleyCarrierOilSeal"),
        trolleyTraversingGearboxOilSealTidakMemenuhi = !findStatus(visualCategory, "traversingGearboxTrolleyCarrierOilSeal"),
        trolleyTraversingGearboxOilSealResult = findString(visualCategory, "traversingGearboxTrolleyCarrierOilSeal"),
        trolleyDriveWheelsWearMemenuhi = findStatus(visualCategory, "driveWheelOnTrolleyWear"),
        trolleyDriveWheelsWearTidakMemenuhi = !findStatus(visualCategory, "driveWheelOnTrolleyWear"),
        trolleyDriveWheelsWearResult = findString(visualCategory, "driveWheelOnTrolleyWear"),
        trolleyDriveWheelsCracksMemenuhi = findStatus(visualCategory, "driveWheelOnTrolleyCracks"),
        trolleyDriveWheelsCracksTidakMemenuhi = !findStatus(visualCategory, "driveWheelOnTrolleyCracks"),
        trolleyDriveWheelsCracksResult = findString(visualCategory, "driveWheelOnTrolleyCracks"),
        trolleyDriveWheelsDeformationMemenuhi = findStatus(visualCategory, "driveWheelOnTrolleyDeformation"),
        trolleyDriveWheelsDeformationTidakMemenuhi = !findStatus(visualCategory, "driveWheelOnTrolleyDeformation"),
        trolleyDriveWheelsDeformationResult = findString(visualCategory, "driveWheelOnTrolleyDeformation"),
        trolleyDriveWheelsFlangeConditionMemenuhi = findStatus(visualCategory, "driveWheelOnTrolleyFlangeCondition"),
        trolleyDriveWheelsFlangeConditionTidakMemenuhi = !findStatus(visualCategory, "driveWheelOnTrolleyFlangeCondition"),
        trolleyDriveWheelsFlangeConditionResult = findString(visualCategory, "driveWheelOnTrolleyFlangeCondition"),
        trolleyDriveWheelsChainConditionMemenuhi = findStatus(visualCategory, "driveWheelOnTrolleyChainCondition"),
        trolleyDriveWheelsChainConditionTidakMemenuhi = !findStatus(visualCategory, "driveWheelOnTrolleyChainCondition"),
        trolleyDriveWheelsChainConditionResult = findString(visualCategory, "driveWheelOnTrolleyChainCondition"),
        trolleyIdleWheelsWearMemenuhi = findStatus(visualCategory, "idleWheelOnTrolleyWear"),
        trolleyIdleWheelsWearTidakMemenuhi = !findStatus(visualCategory, "idleWheelOnTrolleyWear"),
        trolleyIdleWheelsWearResult = findString(visualCategory, "idleWheelOnTrolleyWear"),
        trolleyIdleWheelsCracksMemenuhi = findStatus(visualCategory, "idleWheelOnTrolleyCracks"),
        trolleyIdleWheelsCracksTidakMemenuhi = !findStatus(visualCategory, "idleWheelOnTrolleyCracks"),
        trolleyIdleWheelsCracksResult = findString(visualCategory, "idleWheelOnTrolleyCracks"),
        trolleyIdleWheelsDeformationMemenuhi = findStatus(visualCategory, "idleWheelOnTrolleyDeformation"),
        trolleyIdleWheelsDeformationTidakMemenuhi = !findStatus(visualCategory, "idleWheelOnTrolleyDeformation"),
        trolleyIdleWheelsDeformationResult = findString(visualCategory, "idleWheelOnTrolleyDeformation"),
        trolleyIdleWheelsFlangeConditionMemenuhi = findStatus(visualCategory, "idleWheelOnTrolleyFlangeCondition"),
        trolleyIdleWheelsFlangeConditionTidakMemenuhi = !findStatus(visualCategory, "idleWheelOnTrolleyFlangeCondition"),
        trolleyIdleWheelsFlangeConditionResult = findString(visualCategory, "idleWheelOnTrolleyFlangeCondition"),
        trolleyWheelConnectorAlignmentMemenuhi = findStatus(visualCategory, "wheelConnectorBogieAxleOnTrolleyStraightness"),
        trolleyWheelConnectorAlignmentTidakMemenuhi = !findStatus(visualCategory, "wheelConnectorBogieAxleOnTrolleyStraightness"),
        trolleyWheelConnectorAlignmentResult = findString(visualCategory, "wheelConnectorBogieAxleOnTrolleyStraightness"),
        trolleyWheelConnectorCrossJointMemenuhi = findStatus(visualCategory, "wheelConnectorBogieAxleOnTrolleyCrossJoint"),
        trolleyWheelConnectorCrossJointTidakMemenuhi = !findStatus(visualCategory, "wheelConnectorBogieAxleOnTrolleyCrossJoint"),
        trolleyWheelConnectorCrossJointResult = findString(visualCategory, "wheelConnectorBogieAxleOnTrolleyCrossJoint"),
        trolleyWheelConnectorLubricationMemenuhi = findStatus(visualCategory, "wheelConnectorBogieAxleOnTrolleyLubrication"),
        trolleyWheelConnectorLubricationTidakMemenuhi = !findStatus(visualCategory, "wheelConnectorBogieAxleOnTrolleyLubrication"),
        trolleyWheelConnectorLubricationResult = findString(visualCategory, "wheelConnectorBogieAxleOnTrolleyLubrication"),
        trolleyGirderStopperConditionMemenuhi = findStatus(visualCategory, "stopperBumperOnGirderOnTrolleyCondition"),
        trolleyGirderStopperConditionTidakMemenuhi = !findStatus(visualCategory, "stopperBumperOnGirderOnTrolleyCondition"),
        trolleyGirderStopperConditionResult = findString(visualCategory, "stopperBumperOnGirderOnTrolleyCondition"),
        trolleyGirderStopperReinforcementMemenuhi = findStatus(visualCategory, "stopperBumperOnGirderOnTrolleyReinforcement"),
        trolleyGirderStopperReinforcementTidakMemenuhi = !findStatus(visualCategory, "stopperBumperOnGirderOnTrolleyReinforcement"),
        trolleyGirderStopperReinforcementResult = findString(visualCategory, "stopperBumperOnGirderOnTrolleyReinforcement"),
        windingDrumGrooveMemenuhi = findStatus(visualCategory, "windingDrumGroove"),
        windingDrumGrooveTidakMemenuhi = !findStatus(visualCategory, "windingDrumGroove"),
        windingDrumGrooveResult = findString(visualCategory, "windingDrumGroove"),
        windingDrumGrooveLipMemenuhi = findStatus(visualCategory, "windingDrumGrooveFlange"),
        windingDrumGrooveLipTidakMemenuhi = !findStatus(visualCategory, "windingDrumGrooveFlange"),
        windingDrumGrooveLipResult = findString(visualCategory, "windingDrumGrooveFlange"),
        windingDrumFlangesMemenuhi = findStatus(visualCategory, "windingDrumFlanges"),
        windingDrumFlangesTidakMemenuhi = !findStatus(visualCategory, "windingDrumFlanges"),
        windingDrumFlangesResult = findString(visualCategory, "windingDrumFlanges"),
        visualBrakeInspectionWearMemenuhi = findStatus(visualCategory, "brakeWear"),
        visualBrakeInspectionWearTidakMemenuhi = !findStatus(visualCategory, "brakeWear"),
        visualBrakeInspectionWearResult = findString(visualCategory, "brakeWear"),
        visualBrakeInspectionAdjustmentMemenuhi = findStatus(visualCategory, "brakeAdjustment"),
        visualBrakeInspectionAdjustmentTidakMemenuhi = !findStatus(visualCategory, "brakeAdjustment"),
        visualBrakeInspectionAdjustmentResult = findString(visualCategory, "brakeAdjustment"),
        hoistGearboxLubricationMemenuhi = findStatus(visualCategory, "hoistGearboxLubrication"),
        hoistGearboxLubricationTidakMemenuhi = !findStatus(visualCategory, "hoistGearboxLubrication"),
        hoistGearboxLubricationResult = findString(visualCategory, "hoistGearboxLubrication"),
        hoistGearboxOilSealMemenuhi = findStatus(visualCategory, "hoistGearboxOilSeal"),
        hoistGearboxOilSealTidakMemenuhi = !findStatus(visualCategory, "hoistGearboxOilSeal"),
        hoistGearboxOilSealResult = findString(visualCategory, "hoistGearboxOilSeal"),
        pulleySprocketPulleyGrooveMemenuhi = findStatus(visualCategory, "pulleyDiscChainSprocketPulleyGroove"),
        pulleySprocketPulleyGrooveTidakMemenuhi = !findStatus(visualCategory, "pulleyDiscChainSprocketPulleyGroove"),
        pulleySprocketPulleyGrooveResult = findString(visualCategory, "pulleyDiscChainSprocketPulleyGroove"),
        pulleySprocketPulleyLipMemenuhi = findStatus(visualCategory, "pulleyDiscChainSprocketPulleyFlange"),
        pulleySprocketPulleyLipTidakMemenuhi = !findStatus(visualCategory, "pulleyDiscChainSprocketPulleyFlange"),
        pulleySprocketPulleyLipResult = findString(visualCategory, "pulleyDiscChainSprocketPulleyFlange"),
        pulleySprocketPulleyPinMemenuhi = findStatus(visualCategory, "pulleyDiscChainSprocketPulleyPin"),
        pulleySprocketPulleyPinTidakMemenuhi = !findStatus(visualCategory, "pulleyDiscChainSprocketPulleyPin"),
        pulleySprocketPulleyPinResult = findString(visualCategory, "pulleyDiscChainSprocketPulleyPin"),
        pulleySprocketBearingMemenuhi = findStatus(visualCategory, "pulleyDiscChainSprocketBearing"),
        pulleySprocketBearingTidakMemenuhi = !findStatus(visualCategory, "pulleyDiscChainSprocketBearing"),
        pulleySprocketBearingResult = findString(visualCategory, "pulleyDiscChainSprocketBearing"),
        pulleySprocketPulleyGuardMemenuhi = findStatus(visualCategory, "pulleyDiscChainSprocketPulleyGuard"),
        pulleySprocketPulleyGuardTidakMemenuhi = !findStatus(visualCategory, "pulleyDiscChainSprocketPulleyGuard"),
        pulleySprocketPulleyGuardResult = findString(visualCategory, "pulleyDiscChainSprocketPulleyGuard"),
        pulleySprocketRopeChainGuardMemenuhi = findStatus(visualCategory, "pulleyDiscChainSprocketWireRopeChainGuard"),
        pulleySprocketRopeChainGuardTidakMemenuhi = !findStatus(visualCategory, "pulleyDiscChainSprocketWireRopeChainGuard"),
        pulleySprocketRopeChainGuardResult = findString(visualCategory, "pulleyDiscChainSprocketWireRopeChainGuard"),
        mainHookInspectionWearMemenuhi = findStatus(visualCategory, "mainHookWear"),
        mainHookInspectionWearTidakMemenuhi = !findStatus(visualCategory, "mainHookWear"),
        mainHookInspectionWearResult = findString(visualCategory, "mainHookWear"),
        mainHookInspectionHookOpeningGapMemenuhi = findStatus(visualCategory, "mainHookThroatOpening"),
        mainHookInspectionHookOpeningGapTidakMemenuhi = !findStatus(visualCategory, "mainHookThroatOpening"),
        mainHookInspectionHookOpeningGapResult = findString(visualCategory, "mainHookThroatOpening"),
        mainHookInspectionSwivelNutAndBearingMemenuhi = findStatus(visualCategory, "mainHookSwivelNutAndBearing"),
        mainHookInspectionSwivelNutAndBearingTidakMemenuhi = !findStatus(visualCategory, "mainHookSwivelNutAndBearing"),
        mainHookInspectionSwivelNutAndBearingResult = findString(visualCategory, "mainHookSwivelNutAndBearing"),
        mainHookInspectionTrunnionMemenuhi = findStatus(visualCategory, "mainHookTrunnion"),
        mainHookInspectionTrunnionTidakMemenuhi = !findStatus(visualCategory, "mainHookTrunnion"),
        mainHookInspectionTrunnionResult = findString(visualCategory, "mainHookTrunnion"),
        auxiliaryHookInspectionWearMemenuhi = findStatus(visualCategory, "auxiliaryHookWear"),
        auxiliaryHookInspectionWearTidakMemenuhi = !findStatus(visualCategory, "auxiliaryHookWear"),
        auxiliaryHookInspectionWearResult = findString(visualCategory, "auxiliaryHookWear"),
        auxiliaryHookInspectionHookOpeningGapMemenuhi = findStatus(visualCategory, "auxiliaryHookThroatOpening"),
        auxiliaryHookInspectionHookOpeningGapTidakMemenuhi = !findStatus(visualCategory, "auxiliaryHookThroatOpening"),
        auxiliaryHookInspectionHookOpeningGapResult = findString(visualCategory, "auxiliaryHookThroatOpening"),
        auxiliaryHookInspectionSwivelNutAndBearingMemenuhi = findStatus(visualCategory, "auxiliaryHookSwivelNutAndBearing"),
        auxiliaryHookInspectionSwivelNutAndBearingTidakMemenuhi = !findStatus(visualCategory, "auxiliaryHookSwivelNutAndBearing"),
        auxiliaryHookInspectionSwivelNutAndBearingResult = findString(visualCategory, "auxiliaryHookSwivelNutAndBearing"),
        auxiliaryHookInspectionTrunnionMemenuhi = findStatus(visualCategory, "auxiliaryHookTrunnion"),
        auxiliaryHookInspectionTrunnionTidakMemenuhi = !findStatus(visualCategory, "auxiliaryHookTrunnion"),
        auxiliaryHookInspectionTrunnionResult = findString(visualCategory, "auxiliaryHookTrunnion"),
        mainWireRopeInspectionCorrosionMemenuhi = findStatus(visualCategory, "mainWireRopeCorrosion"),
        mainWireRopeInspectionCorrosionTidakMemenuhi = !findStatus(visualCategory, "mainWireRopeCorrosion"),
        mainWireRopeInspectionCorrosionResult = findString(visualCategory, "mainWireRopeCorrosion"),
        mainWireRopeInspectionWearMemenuhi = findStatus(visualCategory, "mainWireRopeWear"),
        mainWireRopeInspectionWearTidakMemenuhi = !findStatus(visualCategory, "mainWireRopeWear"),
        mainWireRopeInspectionWearResult = findString(visualCategory, "mainWireRopeWear"),
        mainWireRopeInspectionBreakageMemenuhi = findStatus(visualCategory, "mainWireRopeBroken"),
        mainWireRopeInspectionBreakageTidakMemenuhi = !findStatus(visualCategory, "mainWireRopeBroken"),
        mainWireRopeInspectionBreakageResult = findString(visualCategory, "mainWireRopeBroken"),
        mainWireRopeInspectionDeformationMemenuhi = findStatus(visualCategory, "mainWireRopeDeformation"),
        mainWireRopeInspectionDeformationTidakMemenuhi = !findStatus(visualCategory, "mainWireRopeDeformation"),
        mainWireRopeInspectionDeformationResult = findString(visualCategory, "mainWireRopeDeformation"),
        auxiliaryWireRopeInspectionCorrosionMemenuhi = findStatus(visualCategory, "auxiliaryWireRopeCorrosion"),
        auxiliaryWireRopeInspectionCorrosionTidakMemenuhi = !findStatus(visualCategory, "auxiliaryWireRopeCorrosion"),
        auxiliaryWireRopeInspectionCorrosionResult = findString(visualCategory, "auxiliaryWireRopeCorrosion"),
        auxiliaryWireRopeInspectionWearMemenuhi = findStatus(visualCategory, "auxiliaryWireRopeWear"),
        auxiliaryWireRopeInspectionWearTidakMemenuhi = !findStatus(visualCategory, "auxiliaryWireRopeWear"),
        auxiliaryWireRopeInspectionWearResult = findString(visualCategory, "auxiliaryWireRopeWear"),
        auxiliaryWireRopeInspectionBreakageMemenuhi = findStatus(visualCategory, "auxiliaryWireRopeBroken"),
        auxiliaryWireRopeInspectionBreakageTidakMemenuhi = !findStatus(visualCategory, "auxiliaryWireRopeBroken"),
        auxiliaryWireRopeInspectionBreakageResult = findString(visualCategory, "auxiliaryWireRopeBroken"),
        auxiliaryWireRopeInspectionDeformationMemenuhi = findStatus(visualCategory, "auxiliaryWireRopeDeformation"),
        auxiliaryWireRopeInspectionDeformationTidakMemenuhi = !findStatus(visualCategory, "auxiliaryWireRopeDeformation"),
        auxiliaryWireRopeInspectionDeformationResult = findString(visualCategory, "auxiliaryWireRopeDeformation"),
        mainChainInspectionCorrosionMemenuhi = findStatus(visualCategory, "mainChainCorrosion"),
        mainChainInspectionCorrosionTidakMemenuhi = !findStatus(visualCategory, "mainChainCorrosion"),
        mainChainInspectionCorrosionResult = findString(visualCategory, "mainChainCorrosion"),
        mainChainInspectionWearMemenuhi = findStatus(visualCategory, "mainChainWear"),
        mainChainInspectionWearTidakMemenuhi = !findStatus(visualCategory, "mainChainWear"),
        mainChainInspectionWearResult = findString(visualCategory, "mainChainWear"),
        mainChainInspectionCrackOrBreakageMemenuhi = findStatus(visualCategory, "mainChainCrackedBroken"),
        mainChainInspectionCrackOrBreakageTidakMemenuhi = !findStatus(visualCategory, "mainChainCrackedBroken"),
        mainChainInspectionCrackOrBreakageResult = findString(visualCategory, "mainChainCrackedBroken"),
        mainChainInspectionDeformationMemenuhi = findStatus(visualCategory, "mainChainDeformation"),
        mainChainInspectionDeformationTidakMemenuhi = !findStatus(visualCategory, "mainChainDeformation"),
        mainChainInspectionDeformationResult = findString(visualCategory, "mainChainDeformation"),
        auxiliaryChainInspectionCorrosionMemenuhi = findStatus(visualCategory, "auxiliaryChainCorrosion"),
        auxiliaryChainInspectionCorrosionTidakMemenuhi = !findStatus(visualCategory, "auxiliaryChainCorrosion"),
        auxiliaryChainInspectionCorrosionResult = findString(visualCategory, "auxiliaryChainCorrosion"),
        auxiliaryChainInspectionWearMemenuhi = findStatus(visualCategory, "auxiliaryChainWear"),
        auxiliaryChainInspectionWearTidakMemenuhi = !findStatus(visualCategory, "auxiliaryChainWear"),
        auxiliaryChainInspectionWearResult = findString(visualCategory, "auxiliaryChainWear"),
        auxiliaryChainInspectionCrackOrBreakageMemenuhi = findStatus(visualCategory, "auxiliaryChainCrackedBroken"),
        auxiliaryChainInspectionCrackOrBreakageTidakMemenuhi = !findStatus(visualCategory, "auxiliaryChainCrackedBroken"),
        auxiliaryChainInspectionCrackOrBreakageResult = findString(visualCategory, "auxiliaryChainCrackedBroken"),
        auxiliaryChainInspectionDeformationMemenuhi = findStatus(visualCategory, "auxiliaryChainDeformation"),
        auxiliaryChainInspectionDeformationTidakMemenuhi = !findStatus(visualCategory, "auxiliaryChainDeformation"),
        auxiliaryChainInspectionDeformationResult = findString(visualCategory, "auxiliaryChainDeformation"),
        limitSwitchLongTravelMemenuhi = findStatus(visualCategory, "limitSwitchLsLongTraveling"),
        limitSwitchLongTravelTidakMemenuhi = !findStatus(visualCategory, "limitSwitchLsLongTraveling"),
        limitSwitchLongTravelResult = findString(visualCategory, "limitSwitchLsLongTraveling"),
        limitSwitchCrossTravelMemenuhi = findStatus(visualCategory, "limitSwitchLsCrossTraveling"),
        limitSwitchCrossTravelTidakMemenuhi = !findStatus(visualCategory, "limitSwitchLsCrossTraveling"),
        limitSwitchCrossTravelResult = findString(visualCategory, "limitSwitchLsCrossTraveling"),
        limitSwitchHoistMemenuhi = findStatus(visualCategory, "limitSwitchLsHoisting"),
        limitSwitchHoistTidakMemenuhi = !findStatus(visualCategory, "limitSwitchLsHoisting"),
        limitSwitchHoistResult = findString(visualCategory, "limitSwitchLsHoisting"),
        operatorCabinSafetyLadderMemenuhi = findStatus(visualCategory, "operatorCabinSafetyLadder"),
        operatorCabinSafetyLadderTidakMemenuhi = !findStatus(visualCategory, "operatorCabinSafetyLadder"),
        operatorCabinSafetyLadderResult = findString(visualCategory, "operatorCabinSafetyLadder"),
        operatorCabinDoorMemenuhi = findStatus(visualCategory, "operatorCabinDoor"),
        operatorCabinDoorTidakMemenuhi = !findStatus(visualCategory, "operatorCabinDoor"),
        operatorCabinDoorResult = findString(visualCategory, "operatorCabinDoor"),
        operatorCabinWindowMemenuhi = findStatus(visualCategory, "operatorCabinWindow"),
        operatorCabinWindowTidakMemenuhi = !findStatus(visualCategory, "operatorCabinWindow"),
        operatorCabinWindowResult = findString(visualCategory, "operatorCabinWindow"),
        operatorCabinFanOrACMemenuhi = findStatus(visualCategory, "operatorCabinFanAc"),
        operatorCabinFanOrACTidakMemenuhi = !findStatus(visualCategory, "operatorCabinFanAc"),
        operatorCabinFanOrACResult = findString(visualCategory, "operatorCabinFanAc"),
        operatorCabinControlLeversOrButtonsMemenuhi = findStatus(visualCategory, "operatorCabinControlLeversButtons"),
        operatorCabinControlLeversOrButtonsTidakMemenuhi = !findStatus(visualCategory, "operatorCabinControlLeversButtons"),
        operatorCabinControlLeversOrButtonsResult = findString(visualCategory, "operatorCabinControlLeversButtons"),
        operatorCabinPendantControlMemenuhi = findStatus(visualCategory, "operatorCabinPendantControl"),
        operatorCabinPendantControlTidakMemenuhi = !findStatus(visualCategory, "operatorCabinPendantControl"),
        operatorCabinPendantControlResult = findString(visualCategory, "operatorCabinPendantControl"),
        operatorCabinLightingMemenuhi = findStatus(visualCategory, "operatorCabinLighting"),
        operatorCabinLightingTidakMemenuhi = !findStatus(visualCategory, "operatorCabinLighting"),
        operatorCabinLightingResult = findString(visualCategory, "operatorCabinLighting"),
        operatorCabinHornMemenuhi = findStatus(visualCategory, "operatorCabinHorn"),
        operatorCabinHornTidakMemenuhi = !findStatus(visualCategory, "operatorCabinHorn"),
        operatorCabinHornResult = findString(visualCategory, "operatorCabinHorn"),
        operatorCabinFuseProtectionMemenuhi = findStatus(visualCategory, "operatorCabinFuse"),
        operatorCabinFuseProtectionTidakMemenuhi = !findStatus(visualCategory, "operatorCabinFuse"),
        operatorCabinFuseProtectionResult = findString(visualCategory, "operatorCabinFuse"),
        operatorCabinCommunicationDeviceMemenuhi = findStatus(visualCategory, "operatorCabinCommunicationDevice"),
        operatorCabinCommunicationDeviceTidakMemenuhi = !findStatus(visualCategory, "operatorCabinCommunicationDevice"),
        operatorCabinCommunicationDeviceResult = findString(visualCategory, "operatorCabinCommunicationDevice"),
        operatorCabinFireExtinguisherMemenuhi = findStatus(visualCategory, "operatorCabinFireExtinguisher"),
        operatorCabinFireExtinguisherTidakMemenuhi = !findStatus(visualCategory, "operatorCabinFireExtinguisher"),
        operatorCabinFireExtinguisherResult = findString(visualCategory, "operatorCabinFireExtinguisher"),
        operatorCabinOperationalSignsMemenuhi = findStatus(visualCategory, "operatorCabinOperatingSigns"),
        operatorCabinOperationalSignsTidakMemenuhi = !findStatus(visualCategory, "operatorCabinOperatingSigns"),
        operatorCabinOperationalSignsResult = findString(visualCategory, "operatorCabinOperatingSigns"),
        operatorCabinIgnitionOrMasterSwitchMemenuhi = findStatus(visualCategory, "operatorCabinIgnitionKeyMasterSwitch"),
        operatorCabinIgnitionOrMasterSwitchTidakMemenuhi = !findStatus(visualCategory, "operatorCabinIgnitionKeyMasterSwitch"),
        operatorCabinIgnitionOrMasterSwitchResult = findString(visualCategory, "operatorCabinIgnitionKeyMasterSwitch"),
        electricalComponentsPanelConductorConnectorMemenuhi = findStatus(visualCategory, "electricalComponentsPanelConductorConnector"),
        electricalComponentsPanelConductorConnectorTidakMemenuhi = !findStatus(visualCategory, "electricalComponentsPanelConductorConnector"),
        electricalComponentsPanelConductorConnectorResult = findString(visualCategory, "electricalComponentsPanelConductorConnector"),
        electricalComponentsConductorProtectionMemenuhi = findStatus(visualCategory, "electricalComponentsConductorProtection"),
        electricalComponentsConductorProtectionTidakMemenuhi = !findStatus(visualCategory, "electricalComponentsConductorProtection"),
        electricalComponentsConductorProtectionResult = findString(visualCategory, "electricalComponentsConductorProtection"),
        electricalComponentsMotorInstallationSafetySystemMemenuhi = findStatus(visualCategory, "electricalComponentsMotorInstallationSafetySystem"),
        electricalComponentsMotorInstallationSafetySystemTidakMemenuhi = !findStatus(visualCategory, "electricalComponentsMotorInstallationSafetySystem"),
        electricalComponentsMotorInstallationSafetySystemResult = findString(visualCategory, "electricalComponentsMotorInstallationSafetySystem"),
        electricalComponentsGroundingSystemMemenuhi = findStatus(visualCategory, "electricalComponentsGroundingSystem"),
        electricalComponentsGroundingSystemTidakMemenuhi = !findStatus(visualCategory, "electricalComponentsGroundingSystem"),
        electricalComponentsGroundingSystemResult = findString(visualCategory, "electricalComponentsGroundingSystem"),
        electricalComponentsInstallationMemenuhi = findStatus(visualCategory, "electricalComponentsInstallation"),
        electricalComponentsInstallationTidakMemenuhi = !findStatus(visualCategory, "electricalComponentsInstallation"),
        electricalComponentsInstallationResult = findString(visualCategory, "electricalComponentsInstallation")
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
        isSynced = true
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

    // Visual Inspection
    val visualCategory = "visual_inspection"
    this.visualInspection.let {
        add(visualCategory, "foundationAnchorBoltCorrosion", it.anchorBoltsCorrosionMemenuhi, it.anchorBoltsCorrosionResult)
        add(visualCategory, "foundationAnchorBoltCracks", it.anchorBoltsCracksMemenuhi, it.anchorBoltsCracksResult)
        add(visualCategory, "foundationAnchorBoltDeformation", it.anchorBoltsDeformationMemenuhi, it.anchorBoltsDeformationResult)
        add(visualCategory, "foundationAnchorBoltTightness", it.anchorBoltsFasteningMemenuhi, it.anchorBoltsFasteningResult)
        add(visualCategory, "columnFrameCorrosion", it.columnFrameCorrosionMemenuhi, it.columnFrameCorrosionResult)
        add(visualCategory, "columnFrameCracks", it.columnFrameCracksMemenuhi, it.columnFrameCracksResult)
        add(visualCategory, "columnFrameDeformation", it.columnFrameDeformationMemenuhi, it.columnFrameDeformationResult)
        add(visualCategory, "columnFrameFastening", it.columnFrameFasteningMemenuhi, it.columnFrameFasteningResult)
        add(visualCategory, "columnFrameCrossBracing", it.columnFrameTransverseReinforcementMemenuhi, it.columnFrameTransverseReinforcementResult)
        add(visualCategory, "columnFrameDiagonalBracing", it.columnFrameDiagonalReinforcementMemenuhi, it.columnFrameDiagonalReinforcementResult)
        add(visualCategory, "ladderCorrosion", it.ladderCorrosionMemenuhi, it.ladderCorrosionResult)
        add(visualCategory, "ladderCracks", it.ladderCracksMemenuhi, it.ladderCracksResult)
        add(visualCategory, "ladderDeformation", it.ladderDeformationMemenuhi, it.ladderDeformationResult)
        add(visualCategory, "ladderFastening", it.ladderFasteningMemenuhi, it.ladderFasteningResult)
        add(visualCategory, "workPlatformCorrosion", it.workingFloorCorrosionMemenuhi, it.workingFloorCorrosionResult)
        add(visualCategory, "workPlatformCracks", it.workingFloorCracksMemenuhi, it.workingFloorCracksResult)
        add(visualCategory, "workPlatformDeformation", it.workingFloorDeformationMemenuhi, it.workingFloorDeformationResult)
        add(visualCategory, "workPlatformFastening", it.workingFloorFasteningMemenuhi, it.workingFloorFasteningResult)
        add(visualCategory, "railMountingBeamCorrosion", it.railSupportBeamCorrosionMemenuhi, it.railSupportBeamCorrosionResult)
        add(visualCategory, "railMountingBeamCracks", it.railSupportBeamCracksMemenuhi, it.railSupportBeamCracksResult)
        add(visualCategory, "railMountingBeamDeformation", it.railSupportBeamDeformationMemenuhi, it.railSupportBeamDeformationResult)
        add(visualCategory, "railMountingBeamFastening", it.railSupportBeamFasteningMemenuhi, it.railSupportBeamFasteningResult)
        add(visualCategory, "travelingRailCorrosion", it.travelingRailCorrosionMemenuhi, it.travelingRailCracksResult) // FIXED
        add(visualCategory, "travelingRailCracks", it.travelingCracksMemenuhi, it.travelingCracksResult)
        add(visualCategory, "travelingRailJoint", it.travelingRailConnectionMemenuhi, it.travelingRailConnectionResult)
        add(visualCategory, "travelingRailStraightness", it.travelingRailAlignmentMemenuhi, it.travelingRailAlignmentResult)
        add(visualCategory, "travelingRailAlignmentBetweenRails", it.travelingInterRailAlignmentMemenuhi, it.travelingInterRailAlignmentResult)
        add(visualCategory, "travelingRailEvennessBetweenRails", it.travelingInterRailFlatnessMemenuhi, it.travelingInterRailFlatnessResult)
        add(visualCategory, "travelingRailGapBetweenRailJoints", it.travelingRailConnectionGapMemenuhi, it.travelingRailConnectionGapResult)
        add(visualCategory, "travelingRailFastener", it.travelingRailFastenerMemenuhi, it.travelingRailFastenerResult)
        add(visualCategory, "travelingRailStopper", it.travelingRailStopperMemenuhi, it.travelingRailStopperResult)
        add(visualCategory, "traversingRailCorrosion", it.traversingRailCorrosionMemenuhi, it.traversingRailCorrosionResult)
        add(visualCategory, "traversingRailCracks", it.traversingRailCracksMemenuhi, it.traversingRailCracksResult)
        add(visualCategory, "traversingRailJoint", it.traversingRailRailConnectionMemenuhi, it.traversingRailRailConnectionResult)
        add(visualCategory, "traversingRailStraightness", it.traversingRailRailAlignmentMemenuhi, it.traversingRailRailAlignmentResult)
        add(visualCategory, "traversingRailAlignmentBetweenRails", it.traversingRailInterRailAlignmentMemenuhi, it.traversingRailInterRailAlignmentResult)
        add(visualCategory, "traversingRailEvennessBetweenRails", it.traversingRailInterRailFlatnessMemenuhi, it.traversingRailInterRailFlatnessResult)
        add(visualCategory, "traversingRailGapBetweenRailJoints", it.traversingRailRailConnectionGapMemenuhi, it.traversingRailRailConnectionGapResult)
        add(visualCategory, "traversingRailFastener", it.traversingRailRailFastenerMemenuhi, it.traversingRailRailFastenerResult)
        add(visualCategory, "traversingRailStopper", it.traversingRailRailStopperMemenuhi, it.traversingRailRailStopperResult)
        add(visualCategory, "girderCorrosion", it.girderCorrosionMemenuhi, it.girderCorrosionResult)
        add(visualCategory, "girderCracks", it.girderCracksMemenuhi, it.girderCracksResult)
        add(visualCategory, "girderCamber", it.girderCamberMemenuhi, it.girderCamberResult)
        add(visualCategory, "girderJoint", it.girderConnectionMemenuhi, it.girderConnectionResult)
        add(visualCategory, "girderEndJoint", it.girderEndGirderConnectionMemenuhi, it.girderEndGirderConnectionResult)
        add(visualCategory, "girderTruckMountOnGirder", it.girderTruckMountingOnGirderMemenuhi, it.girderTruckMountingOnGirderResult)
        add(visualCategory, "travelingGearboxGirderCorrosion", it.travelingGearboxCorrosionMemenuhi, it.travelingGearboxCorrosionResult)
        add(visualCategory, "travelingGearboxGirderCracks", it.travelingGearboxCracksMemenuhi, it.travelingGearboxCracksResult)
        add(visualCategory, "travelingGearboxGirderLubricatingOil", it.travelingGearboxLubricatingOilMemenuhi, it.travelingGearboxLubricatingOilResult)
        add(visualCategory, "travelingGearboxGirderOilSeal", it.travelingGearboxOilSealMemenuhi, it.travelingGearboxOilSealResult)
        add(visualCategory, "driveWheelWear", it.driveWheelsWearMemenuhi, it.driveWheelsWearResult)
        add(visualCategory, "driveWheelCracks", it.driveWheelsCracksMemenuhi, it.driveWheelsCracksResult)
        add(visualCategory, "driveWheelDeformation", it.driveWheelsDeformationMemenuhi, it.driveWheelsDeformationResult)
        add(visualCategory, "driveWheelFlangeCondition", it.driveWheelsFlangeConditionMemenuhi, it.driveWheelsFlangeConditionResult)
        add(visualCategory, "driveWheelChainCondition", it.driveWheelsChainConditionMemenuhi, it.driveWheelsChainConditionResult)
        add(visualCategory, "idleWheelSecurity", it.idleWheelsSafetyMemenuhi, it.idleWheelsSafetyResult)
        add(visualCategory, "idleWheelCracks", it.idleWheelsCracksMemenuhi, it.idleWheelsCracksResult)
        add(visualCategory, "idleWheelDeformation", it.idleWheelsDeformationMemenuhi, it.idleWheelsDeformationResult)
        add(visualCategory, "idleWheelFlangeCondition", it.idleWheelsFlangeConditionMemenuhi, it.idleWheelsFlangeConditionResult)
        add(visualCategory, "wheelConnectorBogieAxleStraightness", it.wheelConnectorAlignmentMemenuhi, it.wheelConnectorAlignmentResult)
        add(visualCategory, "wheelConnectorBogieAxleCrossJoint", it.wheelConnectorCrossJointMemenuhi, it.wheelConnectorCrossJointResult)
        add(visualCategory, "wheelConnectorBogieAxleLubrication", it.wheelConnectorLubricationMemenuhi, it.wheelConnectorLubricationResult)
        add(visualCategory, "stopperBumperOnGirderCondition", it.girderStopperConditionMemenuhi, it.girderStopperConditionResult)
        add(visualCategory, "stopperBumperOnGirderReinforcement", it.girderStopperReinforcementMemenuhi, it.girderStopperReinforcementResult)
        add(visualCategory, "traversingGearboxTrolleyCarrierFastening", it.trolleyTraversingGearboxFasteningMemenuhi, it.trolleyTraversingGearboxFasteningResult)
        add(visualCategory, "traversingGearboxTrolleyCarrierCorrosion", it.trolleyTraversingGearboxCorrosionMemenuhi, it.trolleyTraversingGearboxCorrosionResult)
        add(visualCategory, "traversingGearboxTrolleyCarrierCracks", it.trolleyTraversingGearboxCracksMemenuhi, it.trolleyTraversingGearboxCracksResult)
        add(visualCategory, "traversingGearboxTrolleyCarrierLubricatingOil", it.trolleyTraversingGearboxLubricatingOilMemenuhi, it.trolleyTraversingGearboxLubricatingOilResult)
        add(visualCategory, "traversingGearboxTrolleyCarrierOilSeal", it.trolleyTraversingGearboxOilSealMemenuhi, it.trolleyTraversingGearboxOilSealResult)
        add(visualCategory, "driveWheelOnTrolleyWear", it.trolleyDriveWheelsWearMemenuhi, it.trolleyDriveWheelsWearResult)
        add(visualCategory, "driveWheelOnTrolleyCracks", it.trolleyDriveWheelsCracksMemenuhi, it.trolleyDriveWheelsCracksResult)
        add(visualCategory, "driveWheelOnTrolleyDeformation", it.trolleyDriveWheelsDeformationMemenuhi, it.trolleyDriveWheelsDeformationResult)
        add(visualCategory, "driveWheelOnTrolleyFlangeCondition", it.trolleyDriveWheelsFlangeConditionMemenuhi, it.trolleyDriveWheelsFlangeConditionResult)
        add(visualCategory, "driveWheelOnTrolleyChainCondition", it.trolleyDriveWheelsChainConditionMemenuhi, it.trolleyDriveWheelsChainConditionResult)
        add(visualCategory, "idleWheelOnTrolleyWear", it.trolleyIdleWheelsWearMemenuhi, it.trolleyIdleWheelsWearResult)
        add(visualCategory, "idleWheelOnTrolleyCracks", it.trolleyIdleWheelsCracksMemenuhi, it.trolleyIdleWheelsCracksResult)
        add(visualCategory, "idleWheelOnTrolleyDeformation", it.trolleyIdleWheelsDeformationMemenuhi, it.trolleyIdleWheelsDeformationResult)
        add(visualCategory, "idleWheelOnTrolleyFlangeCondition", it.trolleyIdleWheelsFlangeConditionMemenuhi, it.trolleyIdleWheelsFlangeConditionResult)
        add(visualCategory, "wheelConnectorBogieAxleOnTrolleyStraightness", it.trolleyWheelConnectorAlignmentMemenuhi, it.trolleyWheelConnectorAlignmentResult)
        add(visualCategory, "wheelConnectorBogieAxleOnTrolleyCrossJoint", it.trolleyWheelConnectorCrossJointMemenuhi, it.trolleyWheelConnectorCrossJointResult)
        add(visualCategory, "wheelConnectorBogieAxleOnTrolleyLubrication", it.trolleyWheelConnectorLubricationMemenuhi, it.trolleyWheelConnectorLubricationResult)
        add(visualCategory, "stopperBumperOnGirderOnTrolleyCondition", it.trolleyGirderStopperConditionMemenuhi, it.trolleyGirderStopperConditionResult)
        add(visualCategory, "stopperBumperOnGirderOnTrolleyReinforcement", it.trolleyGirderStopperReinforcementMemenuhi, it.trolleyGirderStopperReinforcementResult)
        add(visualCategory, "windingDrumGroove", it.windingDrumGrooveMemenuhi, it.windingDrumGrooveResult)
        add(visualCategory, "windingDrumGrooveFlange", it.windingDrumGrooveLipMemenuhi, it.windingDrumGrooveLipResult)
        add(visualCategory, "windingDrumFlanges", it.windingDrumFlangesMemenuhi, it.windingDrumFlangesResult)
        add(visualCategory, "brakeWear", it.visualBrakeInspectionWearMemenuhi, it.visualBrakeInspectionWearResult)
        add(visualCategory, "brakeAdjustment", it.visualBrakeInspectionAdjustmentMemenuhi, it.visualBrakeInspectionAdjustmentResult)
        add(visualCategory, "hoistGearboxLubrication", it.hoistGearboxLubricationMemenuhi, it.hoistGearboxLubricationResult)
        add(visualCategory, "hoistGearboxOilSeal", it.hoistGearboxOilSealMemenuhi, it.hoistGearboxOilSealResult)
        add(visualCategory, "pulleyDiscChainSprocketPulleyGroove", it.pulleySprocketPulleyGrooveMemenuhi, it.pulleySprocketPulleyGrooveResult)
        add(visualCategory, "pulleyDiscChainSprocketPulleyFlange", it.pulleySprocketPulleyLipMemenuhi, it.pulleySprocketPulleyLipResult)
        add(visualCategory, "pulleyDiscChainSprocketPulleyPin", it.pulleySprocketPulleyPinMemenuhi, it.pulleySprocketPulleyPinResult)
        add(visualCategory, "pulleyDiscChainSprocketBearing", it.pulleySprocketBearingMemenuhi, it.pulleySprocketBearingResult)
        add(visualCategory, "pulleyDiscChainSprocketPulleyGuard", it.pulleySprocketPulleyGuardMemenuhi, it.pulleySprocketPulleyGuardResult)
        add(visualCategory, "pulleyDiscChainSprocketWireRopeChainGuard", it.pulleySprocketRopeChainGuardMemenuhi, it.pulleySprocketRopeChainGuardResult)
        add(visualCategory, "mainHookWear", it.mainHookInspectionWearMemenuhi, it.mainHookInspectionWearResult)
        add(visualCategory, "mainHookThroatOpening", it.mainHookInspectionHookOpeningGapMemenuhi, it.mainHookInspectionHookOpeningGapResult)
        add(visualCategory, "mainHookSwivelNutAndBearing", it.mainHookInspectionSwivelNutAndBearingMemenuhi, it.mainHookInspectionSwivelNutAndBearingResult)
        add(visualCategory, "mainHookTrunnion", it.mainHookInspectionTrunnionMemenuhi, it.mainHookInspectionTrunnionResult)
        add(visualCategory, "auxiliaryHookWear", it.auxiliaryHookInspectionWearMemenuhi, it.auxiliaryHookInspectionWearResult)
        add(visualCategory, "auxiliaryHookThroatOpening", it.auxiliaryHookInspectionHookOpeningGapMemenuhi, it.auxiliaryHookInspectionHookOpeningGapResult)
        add(visualCategory, "auxiliaryHookSwivelNutAndBearing", it.auxiliaryHookInspectionSwivelNutAndBearingMemenuhi, it.auxiliaryHookInspectionSwivelNutAndBearingResult)
        add(visualCategory, "auxiliaryHookTrunnion", it.auxiliaryHookInspectionTrunnionMemenuhi, it.auxiliaryHookInspectionTrunnionResult)
        add(visualCategory, "mainWireRopeCorrosion", it.mainWireRopeInspectionCorrosionMemenuhi, it.mainWireRopeInspectionCorrosionResult)
        add(visualCategory, "mainWireRopeWear", it.mainWireRopeInspectionWearMemenuhi, it.mainWireRopeInspectionWearResult)
        add(visualCategory, "mainWireRopeBroken", it.mainWireRopeInspectionBreakageMemenuhi, it.mainWireRopeInspectionBreakageResult)
        add(visualCategory, "mainWireRopeDeformation", it.mainWireRopeInspectionDeformationMemenuhi, it.mainWireRopeInspectionDeformationResult)
        add(visualCategory, "auxiliaryWireRopeCorrosion", it.auxiliaryWireRopeInspectionCorrosionMemenuhi, it.auxiliaryWireRopeInspectionCorrosionResult)
        add(visualCategory, "auxiliaryWireRopeWear", it.auxiliaryWireRopeInspectionWearMemenuhi, it.auxiliaryWireRopeInspectionWearResult)
        add(visualCategory, "auxiliaryWireRopeBroken", it.auxiliaryWireRopeInspectionBreakageMemenuhi, it.auxiliaryWireRopeInspectionBreakageResult)
        add(visualCategory, "auxiliaryWireRopeDeformation", it.auxiliaryWireRopeInspectionDeformationMemenuhi, it.auxiliaryWireRopeInspectionDeformationResult)
        add(visualCategory, "mainChainCorrosion", it.mainChainInspectionCorrosionMemenuhi, it.mainChainInspectionCorrosionResult)
        add(visualCategory, "mainChainWear", it.mainChainInspectionWearMemenuhi, it.mainChainInspectionWearResult)
        add(visualCategory, "mainChainCrackedBroken", it.mainChainInspectionCrackOrBreakageMemenuhi, it.mainChainInspectionCrackOrBreakageResult)
        add(visualCategory, "mainChainDeformation", it.mainChainInspectionDeformationMemenuhi, it.mainChainInspectionDeformationResult)
        add(visualCategory, "auxiliaryChainCorrosion", it.auxiliaryChainInspectionCorrosionMemenuhi, it.auxiliaryChainInspectionCorrosionResult)
        add(visualCategory, "auxiliaryChainWear", it.auxiliaryChainInspectionWearMemenuhi, it.auxiliaryChainInspectionWearResult)
        add(visualCategory, "auxiliaryChainCrackedBroken", it.auxiliaryChainInspectionCrackOrBreakageMemenuhi, it.auxiliaryChainInspectionCrackOrBreakageResult)
        add(visualCategory, "auxiliaryChainDeformation", it.auxiliaryChainInspectionDeformationMemenuhi, it.auxiliaryChainInspectionDeformationResult)
        add(visualCategory, "limitSwitchLsLongTraveling", it.limitSwitchLongTravelMemenuhi, it.limitSwitchLongTravelResult)
        add(visualCategory, "limitSwitchLsCrossTraveling", it.limitSwitchCrossTravelMemenuhi, it.limitSwitchCrossTravelResult)
        add(visualCategory, "limitSwitchLsHoisting", it.limitSwitchHoistMemenuhi, it.limitSwitchHoistResult)
        add(visualCategory, "operatorCabinSafetyLadder", it.operatorCabinSafetyLadderMemenuhi, it.operatorCabinSafetyLadderResult)
        add(visualCategory, "operatorCabinDoor", it.operatorCabinDoorMemenuhi, it.operatorCabinDoorResult)
        add(visualCategory, "operatorCabinWindow", it.operatorCabinWindowMemenuhi, it.operatorCabinWindowResult)
        add(visualCategory, "operatorCabinFanAc", it.operatorCabinFanOrACMemenuhi, it.operatorCabinFanOrACResult)
        add(visualCategory, "operatorCabinControlLeversButtons", it.operatorCabinControlLeversOrButtonsMemenuhi, it.operatorCabinControlLeversOrButtonsResult)
        add(visualCategory, "operatorCabinPendantControl", it.operatorCabinPendantControlMemenuhi, it.operatorCabinPendantControlResult)
        add(visualCategory, "operatorCabinLighting", it.operatorCabinLightingMemenuhi, it.operatorCabinLightingResult)
        add(visualCategory, "operatorCabinHorn", it.operatorCabinHornMemenuhi, it.operatorCabinHornResult)
        add(visualCategory, "operatorCabinFuse", it.operatorCabinFuseProtectionMemenuhi, it.operatorCabinFuseProtectionResult)
        add(visualCategory, "operatorCabinCommunicationDevice", it.operatorCabinCommunicationDeviceMemenuhi, it.operatorCabinCommunicationDeviceResult)
        add(visualCategory, "operatorCabinFireExtinguisher", it.operatorCabinFireExtinguisherMemenuhi, it.operatorCabinFireExtinguisherResult)
        add(visualCategory, "operatorCabinOperatingSigns", it.operatorCabinOperationalSignsMemenuhi, it.operatorCabinOperationalSignsResult)
        add(visualCategory, "operatorCabinIgnitionKeyMasterSwitch", it.operatorCabinIgnitionOrMasterSwitchMemenuhi, it.operatorCabinIgnitionOrMasterSwitchResult)
        add(visualCategory, "electricalComponentsPanelConductorConnector", it.electricalComponentsPanelConductorConnectorMemenuhi, it.electricalComponentsPanelConductorConnectorResult)
        add(visualCategory, "electricalComponentsConductorProtection", it.electricalComponentsConductorProtectionMemenuhi, it.electricalComponentsConductorProtectionResult)
        add(visualCategory, "electricalComponentsMotorInstallationSafetySystem", it.electricalComponentsMotorInstallationSafetySystemMemenuhi, it.electricalComponentsMotorInstallationSafetySystemResult)
        add(visualCategory, "electricalComponentsGroundingSystem", it.electricalComponentsGroundingSystemMemenuhi, it.electricalComponentsGroundingSystemResult)
        add(visualCategory, "electricalComponentsInstallation", it.electricalComponentsInstallationMemenuhi, it.electricalComponentsInstallationResult)
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
        isSynced = true
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    // Map Visual Check
    this.inspectionResult.visualCheck.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Struktur Utama Baik", status = it.isMainStructureGood))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Baut dan Mur Terpasang Kencang", status = it.areBoltsAndNutsSecure))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Wire Rope Baik", status = it.isWireRopeGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Hook Baik", status = it.isHookGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Gearbox Baik", status = it.isGearboxGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Terdapat Kebocoran Oli Gearbox", status = it.hasGearboxOilLeak))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Lampu Peringatan Baik", status = it.isWarningLampGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Penandaan Kapasitas Terpasang", status = it.isCapacityMarkingDisplayed))
    }

    // Map Functional Test
    this.inspectionResult.functionalTest.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.FUNCTIONAL_TEST, itemName = "Fungsi Maju Mundur OK", status = it.isForwardReverseFunctionOk))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.FUNCTIONAL_TEST, itemName = "Fungsi Hoisting OK", status = it.isHoistingFunctionOk))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.FUNCTIONAL_TEST, itemName = "Limit Switch Berfungsi", status = it.isLimitSwitchFunctional))
    }

    // Map NDT Test
    this.inspectionResult.ndtTest.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.NDT_TEST, itemName = "Metode", status = true, result = it.method))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.NDT_TEST, itemName = "Hasil Baik", status = it.isNdtResultGood))
    }

    // Map Load Test
    this.inspectionResult.loadTest.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.LOAD_TEST, itemName = "Beban (kg)", status = true, result = it.loadKg))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.LOAD_TEST, itemName = "Tinggi Angkat (meter)", status = true, result = it.liftHeightMeters))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.LOAD_TEST, itemName = "Waktu Tahan (detik)", status = true, result = it.holdTimeSeconds))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.LOAD_TEST, itemName = "Hasil Baik", status = it.isLoadTestResultGood))
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
        isMainStructureGood = findBool(BAPCategory.VISUAL_INSPECTION, "Struktur Utama Baik"),
        areBoltsAndNutsSecure = findBool(BAPCategory.VISUAL_INSPECTION, "Baut dan Mur Terpasang Kencang"),
        isWireRopeGoodCondition = findBool(BAPCategory.VISUAL_INSPECTION, "Kondisi Wire Rope Baik"),
        isHookGoodCondition = findBool(BAPCategory.VISUAL_INSPECTION, "Kondisi Hook Baik"),
        isGearboxGoodCondition = findBool(BAPCategory.VISUAL_INSPECTION, "Kondisi Gearbox Baik"),
        hasGearboxOilLeak = findBool(BAPCategory.VISUAL_INSPECTION, "Terdapat Kebocoran Oli Gearbox"),
        isWarningLampGoodCondition = findBool(BAPCategory.VISUAL_INSPECTION, "Kondisi Lampu Peringatan Baik"),
        isCapacityMarkingDisplayed = findBool(BAPCategory.VISUAL_INSPECTION, "Penandaan Kapasitas Terpasang")
    )

    val functionalTest = GantryCraneBapFunctionalTest(
        isForwardReverseFunctionOk = findBool(BAPCategory.FUNCTIONAL_TEST, "Fungsi Maju Mundur OK"),
        isHoistingFunctionOk = findBool(BAPCategory.FUNCTIONAL_TEST, "Fungsi Hoisting OK"),
        isLimitSwitchFunctional = findBool(BAPCategory.FUNCTIONAL_TEST, "Limit Switch Berfungsi")
    )

    val ndtTest = GantryCraneBapNdtTest(
        method = findString(BAPCategory.NDT_TEST, "Metode"),
        isNdtResultGood = findBool(BAPCategory.NDT_TEST, "Hasil Baik")
    )

    val loadTest = GantryCraneBapLoadTest(
        loadKg = findString(BAPCategory.LOAD_TEST, "Beban (kg)"),
        liftHeightMeters = findString(BAPCategory.LOAD_TEST, "Tinggi Angkat (meter)"),
        holdTimeSeconds = findString(BAPCategory.LOAD_TEST, "Waktu Tahan (detik)"),
        isLoadTestResultGood = findBool(BAPCategory.LOAD_TEST, "Hasil Baik")
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