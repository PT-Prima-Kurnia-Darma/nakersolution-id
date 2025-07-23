package com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane

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
 * Maps the MobileCraneUiState to an InspectionWithDetailsDomain model without using reflection.
 * All UI state data is flattened into InspectionDomain, InspectionFindingDomain, or InspectionCheckItemDomain.
 */
fun MobileCraneUiState.toInspectionWithDetailsDomain(
    currentTime: String,
    reportId: Long? = null
): InspectionWithDetailsDomain {
    val report = this.mobileCraneInspectionReport
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
    val subInspectionType = SubInspectionType.Mobile_Crane

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
        addressUsageLocation = generalData.userAddress,
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
    fun addCheckItem(category: String, itemName: String, value: String?) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = false, // Not applicable for simple data points
                result = value ?: ""
            )
        )
    }

    // Helper to add a boolean check item
    fun addBooleanCheckItem(category: String, itemName: String, value: Boolean) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value,
                result = ""
            )
        )
    }

    // Helper to add a check item for an InspectionResult
    fun addResultCheckItem(category: String, itemName: String, value: MobileCraneInspectionResult) {
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

    // Helper to add a check item for a TestResult
    fun addTestResultCheckItem(category: String, itemName: String, value: MobileCraneTestResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.status,
                result = value.remarks
            )
        )
    }

    // 2. Map remaining General Data to CheckItems
    addCheckItem("general_data", "personInCharge", generalData.personInCharge)
    addCheckItem("general_data", "operatorName", generalData.operatorName)
    addCheckItem("general_data", "intendedUse", generalData.intendedUse)
    addCheckItem("general_data", "operatorCertificate", generalData.operatorCertificate)
    addCheckItem("general_data", "equipmentHistory", generalData.equipmentHistory)

    // 3. Map all Technical Data to CheckItems
    val techCategory = "technical_data"
    technicalData.specifications.let { spec ->
        addCheckItem(techCategory, "spec_maximumWorkingLoadCapacity", spec.maximumWorkingLoadCapacity)
        addCheckItem(techCategory, "spec_boomLength", spec.boomLength)
        addCheckItem(techCategory, "spec_maximumJibLength", spec.maximumJibLength)
        addCheckItem(techCategory, "spec_maximumJibWorkingLoad", spec.maximumJibWorkingLoad)
        addCheckItem(techCategory, "spec_maxBoomJibLength", spec.maxBoomJibLength)
        addCheckItem(techCategory, "spec_craneWeight", spec.craneWeight)
        addCheckItem(techCategory, "spec_maxLiftingHeight", spec.maxLiftingHeight)
        addCheckItem(techCategory, "spec_boomWorkingAngle", spec.boomWorkingAngle)
    }
    technicalData.driveMotor.let { motor ->
        addCheckItem(techCategory, "motor_engineNumber", motor.engineNumber)
        addCheckItem(techCategory, "motor_type", motor.type)
        addCheckItem(techCategory, "motor_numberOfCylinders", motor.numberOfCylinders)
        addCheckItem(techCategory, "motor_netPower", motor.netPower)
        addCheckItem(techCategory, "motor_brandYearOfManufacture", motor.brandYearOfManufacture)
        addCheckItem(techCategory, "motor_manufacturer", motor.manufacturer)
    }
    technicalData.mainHook.let { hook ->
        addCheckItem(techCategory, "mainHook_type", hook.type)
        addCheckItem(techCategory, "mainHook_capacity", hook.capacity)
        addCheckItem(techCategory, "mainHook_material", hook.material)
        addCheckItem(techCategory, "mainHook_serialNumber", hook.serialNumber)
    }
    technicalData.auxiliaryHook.let { hook ->
        addCheckItem(techCategory, "auxHook_type", hook.type)
        addCheckItem(techCategory, "auxHook_capacity", hook.capacity)
        addCheckItem(techCategory, "auxHook_material", hook.material)
        addCheckItem(techCategory, "auxHook_serialNumber", hook.serialNumber)
    }
    technicalData.wireRope.mainLoadHoistDrum.let { drum ->
        addCheckItem(techCategory, "rope_main_diameter", drum.diameter)
        addCheckItem(techCategory, "rope_main_type", drum.type)
        addCheckItem(techCategory, "rope_main_construction", drum.construction)
        addCheckItem(techCategory, "rope_main_breakingStrength", drum.breakingStrength)
        addCheckItem(techCategory, "rope_main_length", drum.length)
    }
    technicalData.wireRope.auxiliaryLoadHoistDrum.let { drum ->
        addCheckItem(techCategory, "rope_aux_diameter", drum.diameter)
        addCheckItem(techCategory, "rope_aux_type", drum.type)
        addCheckItem(techCategory, "rope_aux_construction", drum.construction)
        addCheckItem(techCategory, "rope_aux_breakingStrength", drum.breakingStrength)
        addCheckItem(techCategory, "rope_aux_length", drum.length)
    }
    technicalData.wireRope.boomHoistDrum.let { drum ->
        addCheckItem(techCategory, "rope_boom_diameter", drum.diameter)
        addCheckItem(techCategory, "rope_boom_type", drum.type)
        addCheckItem(techCategory, "rope_boom_construction", drum.construction)
        addCheckItem(techCategory, "rope_boom_breakingStrength", drum.breakingStrength)
        addCheckItem(techCategory, "rope_boom_length", drum.length)
    }

    // 4. Map all Visual Inspection to CheckItems
    val visualCategory = "visual_inspection"
    val v = visualInspection
    addResultCheckItem(visualCategory, "foundationAnchorBoltCorrosion", v.foundationAnchorBoltCorrosion)
    addResultCheckItem(visualCategory, "foundationAnchorBoltCracks", v.foundationAnchorBoltCracks)
    addResultCheckItem(visualCategory, "foundationAnchorBoltDeformation", v.foundationAnchorBoltDeformation)
    addResultCheckItem(visualCategory, "foundationAnchorBoltTightness", v.foundationAnchorBoltTightness)
    addResultCheckItem(visualCategory, "frameColumnsOnFoundationCorrosion", v.frameColumnsOnFoundationCorrosion)
    addResultCheckItem(visualCategory, "frameColumnsOnFoundationCracks", v.frameColumnsOnFoundationCracks)
    addResultCheckItem(visualCategory, "frameColumnsOnFoundationDeformation", v.frameColumnsOnFoundationDeformation)
    addResultCheckItem(visualCategory, "frameColumnsOnFoundationFastening", v.frameColumnsOnFoundationFastening)
    addResultCheckItem(visualCategory, "frameColumnsOnFoundationTransverseReinforcement", v.frameColumnsOnFoundationTransverseReinforcement)
    addResultCheckItem(visualCategory, "frameColumnsOnFoundationDiagonalReinforcement", v.frameColumnsOnFoundationDiagonalReinforcement)
    addResultCheckItem(visualCategory, "ladderCorrosion", v.ladderCorrosion)
    addResultCheckItem(visualCategory, "ladderCracks", v.ladderCracks)
    addResultCheckItem(visualCategory, "ladderDeformation", v.ladderDeformation)
    addResultCheckItem(visualCategory, "ladderFastening", v.ladderFastening)
    addResultCheckItem(visualCategory, "workingPlatformCorrosion", v.workingPlatformCorrosion)
    addResultCheckItem(visualCategory, "workingPlatformCracks", v.workingPlatformCracks)
    addResultCheckItem(visualCategory, "workingPlatformDeformation", v.workingPlatformDeformation)
    addResultCheckItem(visualCategory, "workingPlatformFastening", v.workingPlatformFastening)
    addResultCheckItem(visualCategory, "outriggersOutriggerArmHousing", v.outriggersOutriggerArmHousing)
    addResultCheckItem(visualCategory, "outriggersOutriggerArms", v.outriggersOutriggerArms)
    addResultCheckItem(visualCategory, "outriggersJack", v.outriggersJack)
    addResultCheckItem(visualCategory, "outriggersOutriggerPads", v.outriggersOutriggerPads)
    addResultCheckItem(visualCategory, "outriggersHousingConnectionToChassis", v.outriggersHousingConnectionToChassis)
    addResultCheckItem(visualCategory, "outriggersOutriggerSafetyLocks", v.outriggersOutriggerSafetyLocks)
    addResultCheckItem(visualCategory, "turntableSlewingRollerBearing", v.turntableSlewingRollerBearing)
    addResultCheckItem(visualCategory, "turntableBrakeHousing", v.turntableBrakeHousing)
    addResultCheckItem(visualCategory, "turntableBrakeLiningsAndShoes", v.turntableBrakeLiningsAndShoes)
    addResultCheckItem(visualCategory, "turntableDrumSurface", v.turntableDrumSurface)
    addResultCheckItem(visualCategory, "turntablePressureCylinder", v.turntablePressureCylinder)
    addResultCheckItem(visualCategory, "turntableDrumAxle", v.turntableDrumAxle)
    addResultCheckItem(visualCategory, "turntableLeversPinsBolts", v.turntableLeversPinsBolts)
    addResultCheckItem(visualCategory, "turntableGuard", v.turntableGuard)
    addResultCheckItem(visualCategory, "latticeBoomMainBoom", v.latticeBoomMainBoom)
    addResultCheckItem(visualCategory, "latticeBoomBoomSection", v.latticeBoomBoomSection)
    addResultCheckItem(visualCategory, "latticeBoomTopPulley", v.latticeBoomTopPulley)
    addResultCheckItem(visualCategory, "latticeBoomPulleyGuard", v.latticeBoomPulleyGuard)
    addResultCheckItem(visualCategory, "latticeBoomWireRopeGuard", v.latticeBoomWireRopeGuard)
    addResultCheckItem(visualCategory, "latticeBoomPulleyGrooveLip", v.latticeBoomPulleyGrooveLip)
    addResultCheckItem(visualCategory, "latticeBoomPivotPin", v.latticeBoomPivotPin)
    addResultCheckItem(visualCategory, "latticeBoomWireRopeGuidePulley", v.latticeBoomWireRopeGuidePulley)
    addResultCheckItem(visualCategory, "clutchMainClutch", v.clutchMainClutch)
    addResultCheckItem(visualCategory, "transmission", v.transmission)
    addResultCheckItem(visualCategory, "steeringFrontWheel", v.steeringFrontWheel)
    addResultCheckItem(visualCategory, "steeringMiddleWheel", v.steeringMiddleWheel)
    addResultCheckItem(visualCategory, "steeringRearWheel", v.steeringRearWheel)
    addResultCheckItem(visualCategory, "brakeServiceBrake", v.brakeServiceBrake)
    addResultCheckItem(visualCategory, "brakeParkingBrake", v.brakeParkingBrake)
    addResultCheckItem(visualCategory, "brakeBrakeHousing", v.brakeBrakeHousing)
    addResultCheckItem(visualCategory, "brakeBrakeLiningsAndShoes", v.brakeBrakeLiningsAndShoes)
    addResultCheckItem(visualCategory, "brakeDrumSurface", v.brakeDrumSurface)
    addResultCheckItem(visualCategory, "brakeLeversPinsBolts", v.brakeLeversPinsBolts)
    addResultCheckItem(visualCategory, "brakeGuard", v.brakeGuard)
    addResultCheckItem(visualCategory, "travelDrumClutchHousing", v.travelDrumClutchHousing)
    addResultCheckItem(visualCategory, "travelDrumClutchLining", v.travelDrumClutchLining)
    addResultCheckItem(visualCategory, "travelDrumClutchDrumSurface", v.travelDrumClutchDrumSurface)
    addResultCheckItem(visualCategory, "travelDrumLeversPinsBolts", v.travelDrumLeversPinsBolts)
    addResultCheckItem(visualCategory, "travelDrumGuard", v.travelDrumGuard)
    addResultCheckItem(visualCategory, "mainWinchDrumMounting", v.mainWinchDrumMounting)
    addResultCheckItem(visualCategory, "mainWinchWindingDrumSurface", v.mainWinchWindingDrumSurface)
    addResultCheckItem(visualCategory, "mainWinchBrakeLiningsAndShoes", v.mainWinchBrakeLiningsAndShoes)
    addResultCheckItem(visualCategory, "mainWinchBrakeDrumSurface", v.mainWinchBrakeDrumSurface)
    addResultCheckItem(visualCategory, "mainWinchBrakeHousing", v.mainWinchBrakeHousing)
    addResultCheckItem(visualCategory, "mainWinchClutchLiningsAndShoes", v.mainWinchClutchLiningsAndShoes)
    addResultCheckItem(visualCategory, "mainWinchClutchDrumSurface", v.mainWinchClutchDrumSurface)
    addResultCheckItem(visualCategory, "mainWinchGroove", v.mainWinchGroove)
    addResultCheckItem(visualCategory, "mainWinchGrooveLip", v.mainWinchGrooveLip)
    addResultCheckItem(visualCategory, "mainWinchFlanges", v.mainWinchFlanges)
    addResultCheckItem(visualCategory, "mainWinchBrakeActuatorLeversPinsAndBolts", v.mainWinchBrakeActuatorLeversPinsAndBolts)
    addResultCheckItem(visualCategory, "auxiliaryWinchDrumMounting", v.auxiliaryWinchDrumMounting)
    addResultCheckItem(visualCategory, "auxiliaryWinchWindingDrumSurface", v.auxiliaryWinchWindingDrumSurface)
    addResultCheckItem(visualCategory, "auxiliaryWinchBrakeLiningsAndShoes", v.auxiliaryWinchBrakeLiningsAndShoes)
    addResultCheckItem(visualCategory, "auxiliaryWinchBrakeDrumSurface", v.auxiliaryWinchBrakeDrumSurface)
    addResultCheckItem(visualCategory, "auxiliaryWinchBrakeHousing", v.auxiliaryWinchBrakeHousing)
    addResultCheckItem(visualCategory, "auxiliaryWinchClutchLiningsAndShoes", v.auxiliaryWinchClutchLiningsAndShoes)
    addResultCheckItem(visualCategory, "auxiliaryWinchClutchDrumSurface", v.auxiliaryWinchClutchDrumSurface)
    addResultCheckItem(visualCategory, "auxiliaryWinchGroove", v.auxiliaryWinchGroove)
    addResultCheckItem(visualCategory, "auxiliaryWinchGrooveLip", v.auxiliaryWinchGrooveLip)
    addResultCheckItem(visualCategory, "auxiliaryWinchFlanges", v.auxiliaryWinchFlanges)
    addResultCheckItem(visualCategory, "auxiliaryWinchBrakeActuatorLeversPinsAndBolts", v.auxiliaryWinchBrakeActuatorLeversPinsAndBolts)
    addResultCheckItem(visualCategory, "hoistGearBlockLubrication", v.hoistGearBlockLubrication)
    addResultCheckItem(visualCategory, "hoistGearBlockOilSeal", v.hoistGearBlockOilSeal)
    addResultCheckItem(visualCategory, "mainPulleyPulleyGroove", v.mainPulleyPulleyGroove)
    addResultCheckItem(visualCategory, "mainPulleyPulleyGrooveLip", v.mainPulleyPulleyGrooveLip)
    addResultCheckItem(visualCategory, "mainPulleyPulleyPin", v.mainPulleyPulleyPin)
    addResultCheckItem(visualCategory, "mainPulleyBearing", v.mainPulleyBearing)
    addResultCheckItem(visualCategory, "mainPulleyPulleyGuard", v.mainPulleyPulleyGuard)
    addResultCheckItem(visualCategory, "mainPulleyWireRopeGuard", v.mainPulleyWireRopeGuard)
    addResultCheckItem(visualCategory, "mainHookVisualSwivelNutAndBearing", v.mainHookVisualSwivelNutAndBearing)
    addResultCheckItem(visualCategory, "mainHookVisualTrunnion", v.mainHookVisualTrunnion)
    addResultCheckItem(visualCategory, "mainHookVisualSafetyLatch", v.mainHookVisualSafetyLatch)
    addResultCheckItem(visualCategory, "auxiliaryHookVisualFreeFallWeight", v.auxiliaryHookVisualFreeFallWeight)
    addResultCheckItem(visualCategory, "auxiliaryHookVisualSwivelNutAndBearing", v.auxiliaryHookVisualSwivelNutAndBearing)
    addResultCheckItem(visualCategory, "auxiliaryHookVisualSafetyLatch", v.auxiliaryHookVisualSafetyLatch)
    addResultCheckItem(visualCategory, "mainWireRopeVisualCorrosion", v.mainWireRopeVisualCorrosion)
    addResultCheckItem(visualCategory, "mainWireRopeVisualWear", v.mainWireRopeVisualWear)
    addResultCheckItem(visualCategory, "mainWireRopeVisualBreakage", v.mainWireRopeVisualBreakage)
    addResultCheckItem(visualCategory, "mainWireRopeVisualDeformation", v.mainWireRopeVisualDeformation)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeVisualCorrosion", v.auxiliaryWireRopeVisualCorrosion)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeVisualWear", v.auxiliaryWireRopeVisualWear)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeVisualBreakage", v.auxiliaryWireRopeVisualBreakage)
    addResultCheckItem(visualCategory, "auxiliaryWireRopeVisualDeformation", v.auxiliaryWireRopeVisualDeformation)
    addResultCheckItem(visualCategory, "limitSwitchLsLongTravel", v.limitSwitchLsLongTravel)
    addResultCheckItem(visualCategory, "limitSwitchLsCrossTravel", v.limitSwitchLsCrossTravel)
    addResultCheckItem(visualCategory, "limitSwitchLsHoisting", v.limitSwitchLsHoisting)
    addResultCheckItem(visualCategory, "internalCombustionEngineCoolingSystem", v.internalCombustionEngineCoolingSystem)
    addResultCheckItem(visualCategory, "internalCombustionEngineLubricationSystem", v.internalCombustionEngineLubricationSystem)
    addResultCheckItem(visualCategory, "internalCombustionEngineEngineMounting", v.internalCombustionEngineEngineMounting)
    addResultCheckItem(visualCategory, "internalCombustionEngineSafetyGuardEquipment", v.internalCombustionEngineSafetyGuardEquipment)
    addResultCheckItem(visualCategory, "internalCombustionEngineExhaustSystem", v.internalCombustionEngineExhaustSystem)
    addResultCheckItem(visualCategory, "internalCombustionEngineAirIntakeSystem", v.internalCombustionEngineAirIntakeSystem)
    addResultCheckItem(visualCategory, "internalCombustionEngineFuelSystem", v.internalCombustionEngineFuelSystem)
    addResultCheckItem(visualCategory, "internalCombustionEnginePowerTransmissionSystem", v.internalCombustionEnginePowerTransmissionSystem)
    addResultCheckItem(visualCategory, "internalCombustionEngineBattery", v.internalCombustionEngineBattery)
    addResultCheckItem(visualCategory, "internalCombustionEngineStarterMotor", v.internalCombustionEngineStarterMotor)
    addResultCheckItem(visualCategory, "internalCombustionEngineWiringInstallation", v.internalCombustionEngineWiringInstallation)
    addResultCheckItem(visualCategory, "internalCombustionEngineTurbocharger", v.internalCombustionEngineTurbocharger)
    addResultCheckItem(visualCategory, "hydraulicHydraulicPump", v.hydraulicHydraulicPump)
    addResultCheckItem(visualCategory, "hydraulicHydraulicLines", v.hydraulicHydraulicLines)
    addResultCheckItem(visualCategory, "hydraulicHydraulicFilter", v.hydraulicHydraulicFilter)
    addResultCheckItem(visualCategory, "hydraulicHydraulicTank", v.hydraulicHydraulicTank)
    addResultCheckItem(visualCategory, "hydraulicMotorMainWinchMotor", v.hydraulicMotorMainWinchMotor)
    addResultCheckItem(visualCategory, "hydraulicMotorAuxiliaryWinchMotor", v.hydraulicMotorAuxiliaryWinchMotor)
    addResultCheckItem(visualCategory, "hydraulicMotorBoomWinchMotor", v.hydraulicMotorBoomWinchMotor)
    addResultCheckItem(visualCategory, "hydraulicMotorSwingMotor", v.hydraulicMotorSwingMotor)
    addResultCheckItem(visualCategory, "controlValveReliefValve", v.controlValveReliefValve)
    addResultCheckItem(visualCategory, "controlValveMainWinchValve", v.controlValveMainWinchValve)
    addResultCheckItem(visualCategory, "controlValveAuxiliaryWinchValve", v.controlValveAuxiliaryWinchValve)
    addResultCheckItem(visualCategory, "controlValveBoomWinchValve", v.controlValveBoomWinchValve)
    addResultCheckItem(visualCategory, "controlValveBoomMovementValve", v.controlValveBoomMovementValve)
    addResultCheckItem(visualCategory, "controlValveSteeringCylinderValve", v.controlValveSteeringCylinderValve)
    addResultCheckItem(visualCategory, "controlValveAxleOscillationValve", v.controlValveAxleOscillationValve)
    addResultCheckItem(visualCategory, "controlValveOutriggerMovementValve", v.controlValveOutriggerMovementValve)
    addResultCheckItem(visualCategory, "hydraulicCylinderBoomMovementCylinder", v.hydraulicCylinderBoomMovementCylinder)
    addResultCheckItem(visualCategory, "hydraulicCylinderOutriggerCylinder", v.hydraulicCylinderOutriggerCylinder)
    addResultCheckItem(visualCategory, "hydraulicCylinderSteeringWheelCylinder", v.hydraulicCylinderSteeringWheelCylinder)
    addResultCheckItem(visualCategory, "hydraulicCylinderAxleOscillationCylinder", v.hydraulicCylinderAxleOscillationCylinder)
    addResultCheckItem(visualCategory, "hydraulicCylinderTelescopicCylinder", v.hydraulicCylinderTelescopicCylinder)
    addResultCheckItem(visualCategory, "pneumaticCompressor", v.pneumaticCompressor)
    addResultCheckItem(visualCategory, "pneumaticTankAndSafetyValve", v.pneumaticTankAndSafetyValve)
    addResultCheckItem(visualCategory, "pneumaticPressurizedAirLines", v.pneumaticPressurizedAirLines)
    addResultCheckItem(visualCategory, "pneumaticAirFilter", v.pneumaticAirFilter)
    addResultCheckItem(visualCategory, "pneumaticControlValve", v.pneumaticControlValve)
    addResultCheckItem(visualCategory, "operatorCabinSafetyLadder", v.operatorCabinSafetyLadder)
    addResultCheckItem(visualCategory, "operatorCabinDoor", v.operatorCabinDoor)
    addResultCheckItem(visualCategory, "operatorCabinWindow", v.operatorCabinWindow)
    addResultCheckItem(visualCategory, "operatorCabinFanAc", v.operatorCabinFanAc)
    addResultCheckItem(visualCategory, "operatorCabinControlLeversButtons", v.operatorCabinControlLeversButtons)
    addResultCheckItem(visualCategory, "operatorCabinPendantControl", v.operatorCabinPendantControl)
    addResultCheckItem(visualCategory, "operatorCabinLighting", v.operatorCabinLighting)
    addResultCheckItem(visualCategory, "operatorCabinHornSignalAlarm", v.operatorCabinHornSignalAlarm)
    addResultCheckItem(visualCategory, "operatorCabinFuse", v.operatorCabinFuse)
    addResultCheckItem(visualCategory, "operatorCabinCommunicationDevice", v.operatorCabinCommunicationDevice)
    addResultCheckItem(visualCategory, "operatorCabinFireExtinguisher", v.operatorCabinFireExtinguisher)
    addResultCheckItem(visualCategory, "operatorCabinOperatingSigns", v.operatorCabinOperatingSigns)
    addResultCheckItem(visualCategory, "operatorCabinIgnitionKeyMasterSwitch", v.operatorCabinIgnitionKeyMasterSwitch)
    addResultCheckItem(visualCategory, "operatorCabinButtonsHandlesLevers", v.operatorCabinButtonsHandlesLevers)
    addResultCheckItem(visualCategory, "electricalComponentsPanelConductorConnector", v.electricalComponentsPanelConductorConnector)
    addResultCheckItem(visualCategory, "electricalComponentsConductorProtection", v.electricalComponentsConductorProtection)
    addResultCheckItem(visualCategory, "electricalComponentsMotorInstallationSafetySystem", v.electricalComponentsMotorInstallationSafetySystem)
    addResultCheckItem(visualCategory, "electricalComponentsGroundingSystem", v.electricalComponentsGroundingSystem)
    addResultCheckItem(visualCategory, "electricalComponentsInstallation", v.electricalComponentsInstallation)
    addResultCheckItem(visualCategory, "safetyDevicesLadderHandrail", v.safetyDevicesLadderHandrail)
    addResultCheckItem(visualCategory, "safetyDevicesEngineOilLubricantPressure", v.safetyDevicesEngineOilLubricantPressure)
    addResultCheckItem(visualCategory, "safetyDevicesHydraulicOilPressure", v.safetyDevicesHydraulicOilPressure)
    addResultCheckItem(visualCategory, "safetyDevicesAirPressure", v.safetyDevicesAirPressure)
    addResultCheckItem(visualCategory, "safetyDevicesAmperemeter", v.safetyDevicesAmperemeter)
    addResultCheckItem(visualCategory, "safetyDevicesVoltage", v.safetyDevicesVoltage)
    addResultCheckItem(visualCategory, "safetyDevicesEngineTemperature", v.safetyDevicesEngineTemperature)
    addResultCheckItem(visualCategory, "safetyDevicesTransmissionTemperature", v.safetyDevicesTransmissionTemperature)
    addResultCheckItem(visualCategory, "safetyDevicesConverterOilTemperaturePressure", v.safetyDevicesConverterOilTemperaturePressure)
    addResultCheckItem(visualCategory, "safetyDevicesSpeedometerIndicator", v.safetyDevicesSpeedometerIndicator)
    addResultCheckItem(visualCategory, "safetyDevicesRotaryLamp", v.safetyDevicesRotaryLamp)
    addResultCheckItem(visualCategory, "safetyDevicesMainHoistRopeUpDownLimit", v.safetyDevicesMainHoistRopeUpDownLimit)
    addResultCheckItem(visualCategory, "safetyDevicesAuxiliaryHoistRopeUpDownLimit", v.safetyDevicesAuxiliaryHoistRopeUpDownLimit)
    addResultCheckItem(visualCategory, "safetyDevicesSwingMotionLimit", v.safetyDevicesSwingMotionLimit)
    addResultCheckItem(visualCategory, "safetyDevicesLevelIndicator", v.safetyDevicesLevelIndicator)
    addResultCheckItem(visualCategory, "safetyDevicesLoadWeightIndicator", v.safetyDevicesLoadWeightIndicator)
    addResultCheckItem(visualCategory, "safetyDevicesLoadChart", v.safetyDevicesLoadChart)
    addResultCheckItem(visualCategory, "safetyDevicesAnemometerWindSpeed", v.safetyDevicesAnemometerWindSpeed)
    addResultCheckItem(visualCategory, "safetyDevicesBoomAngleIndicator", v.safetyDevicesBoomAngleIndicator)
    addResultCheckItem(visualCategory, "safetyDevicesAirPressureIndicator", v.safetyDevicesAirPressureIndicator)
    addResultCheckItem(visualCategory, "safetyDevicesHydraulicPressureIndicator", v.safetyDevicesHydraulicPressureIndicator)
    addResultCheckItem(visualCategory, "safetyDevicesSafetyValves", v.safetyDevicesSafetyValves)
    addResultCheckItem(visualCategory, "safetyDevicesMainWindingDrumSafetyLock", v.safetyDevicesMainWindingDrumSafetyLock)
    addResultCheckItem(visualCategory, "safetyDevicesAuxiliaryWindingDrumSafetyLock", v.safetyDevicesAuxiliaryWindingDrumSafetyLock)
    addResultCheckItem(visualCategory, "safetyDevicesTelescopicMotionLimit", v.safetyDevicesTelescopicMotionLimit)
    addResultCheckItem(visualCategory, "safetyDevicesLightningArrester", v.safetyDevicesLightningArrester)
    addResultCheckItem(visualCategory, "safetyDevicesLiftingHeightIndicator", v.safetyDevicesLiftingHeightIndicator)

    // 5. Map NDE to CheckItems
    addCheckItem("nde_wireRope", "ndtType", nde.wireRope.ndtType)
    nde.wireRope.items.forEachIndexed { index, item ->
        val cat = "nde_wireRope_item_$index"
        addCheckItem(cat, "usage", item.usage)
        addCheckItem(cat, "specDiameter", item.specDiameter)
        addCheckItem(cat, "actualDiameter", item.actualDiameter)
        addCheckItem(cat, "construction", item.construction)
        addCheckItem(cat, "type", item.type)
        addCheckItem(cat, "length", item.length)
        addCheckItem(cat, "age", item.age)
        addBooleanCheckItem(cat, "hasDefect", item.hasDefect)
        addCheckItem(cat, "remarks", item.remarks)
    }

    addCheckItem("nde_boom", "boomType", nde.boom.boomType)
    addCheckItem("nde_boom", "ndtType", nde.boom.ndtType)
    nde.boom.items.forEachIndexed { index, item ->
        val cat = "nde_boom_item_$index"
        addCheckItem(cat, "partInspected", item.partInspected)
        addCheckItem(cat, "location", item.location)
        addBooleanCheckItem(cat, "hasDefect", item.hasDefect)
        addCheckItem(cat, "remarks", item.remarks)
    }

    nde.mainHook.let { hook ->
        val cat = "nde_mainHook"
        addCheckItem(cat, "hookCapacity", hook.hookCapacity)
        addCheckItem(cat, "ndtType", hook.ndtType)
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

    nde.auxiliaryHook.let { hook ->
        val cat = "nde_auxHook"
        addCheckItem(cat, "hookCapacity", hook.hookCapacity)
        addCheckItem(cat, "ndtType", hook.ndtType)
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

    nde.mainDrum.let { drum ->
        val cat = "nde_mainDrum"
        addCheckItem(cat, "ndtType", drum.ndtType)
        addCheckItem(cat, "spec_a", drum.specification.a)
        addCheckItem(cat, "spec_b", drum.specification.b)
        addCheckItem(cat, "spec_c", drum.specification.c)
        addCheckItem(cat, "spec_d", drum.specification.d)
        addCheckItem(cat, "spec_e", drum.specification.e)
        addCheckItem(cat, "spec_f", drum.specification.f)
        addCheckItem(cat, "spec_g", drum.specification.g)
        addResultCheckItem(cat, "spec_finding", drum.specification.finding)
        addCheckItem(cat, "result_a", drum.measurementResult.a)
        addCheckItem(cat, "result_b", drum.measurementResult.b)
        addCheckItem(cat, "result_c", drum.measurementResult.c)
        addCheckItem(cat, "result_d", drum.measurementResult.d)
        addCheckItem(cat, "result_e", drum.measurementResult.e)
        addCheckItem(cat, "result_f", drum.measurementResult.f)
        addCheckItem(cat, "result_g", drum.measurementResult.g)
        addResultCheckItem(cat, "result_finding", drum.measurementResult.finding)
    }

    nde.auxiliaryDrum.let { drum ->
        val cat = "nde_auxDrum"
        addCheckItem(cat, "ndtType", drum.ndtType)
        addCheckItem(cat, "spec_a", drum.specification.a)
        addCheckItem(cat, "spec_b", drum.specification.b)
        addCheckItem(cat, "spec_c", drum.specification.c)
        addCheckItem(cat, "spec_d", drum.specification.d)
        addCheckItem(cat, "spec_e", drum.specification.e)
        addCheckItem(cat, "spec_f", drum.specification.f)
        addCheckItem(cat, "spec_g", drum.specification.g)
        addResultCheckItem(cat, "spec_finding", drum.specification.finding)
        addCheckItem(cat, "result_a", drum.measurementResult.a)
        addCheckItem(cat, "result_b", drum.measurementResult.b)
        addCheckItem(cat, "result_c", drum.measurementResult.c)
        addCheckItem(cat, "result_d", drum.measurementResult.d)
        addCheckItem(cat, "result_e", drum.measurementResult.e)
        addCheckItem(cat, "result_f", drum.measurementResult.f)
        addCheckItem(cat, "result_g", drum.measurementResult.g)
        addResultCheckItem(cat, "result_finding", drum.measurementResult.finding)
    }

    nde.mainPulley.let { pulley ->
        val cat = "nde_mainPulley"
        addCheckItem(cat, "ndtType", pulley.ndtType)
        addCheckItem(cat, "spec_a", pulley.specification.a)
        addCheckItem(cat, "spec_b", pulley.specification.b)
        addCheckItem(cat, "spec_c", pulley.specification.c)
        addCheckItem(cat, "spec_d", pulley.specification.d)
        addCheckItem(cat, "spec_e", pulley.specification.e)
        addResultCheckItem(cat, "spec_finding", pulley.specification.finding)
        addCheckItem(cat, "result_a", pulley.measurementResult.a)
        addCheckItem(cat, "result_b", pulley.measurementResult.b)
        addCheckItem(cat, "result_c", pulley.measurementResult.c)
        addCheckItem(cat, "result_d", pulley.measurementResult.d)
        addCheckItem(cat, "result_e", pulley.measurementResult.e)
        addResultCheckItem(cat, "result_finding", pulley.measurementResult.finding)
    }

    nde.auxiliaryPulley.let { pulley ->
        val cat = "nde_auxPulley"
        addCheckItem(cat, "ndtType", pulley.ndtType)
        addCheckItem(cat, "spec_a", pulley.specification.a)
        addCheckItem(cat, "spec_b", pulley.specification.b)
        addCheckItem(cat, "spec_c", pulley.specification.c)
        addCheckItem(cat, "spec_d", pulley.specification.d)
        addCheckItem(cat, "spec_e", pulley.specification.e)
        addResultCheckItem(cat, "spec_finding", pulley.specification.finding)
        addCheckItem(cat, "result_a", pulley.measurementResult.a)
        addCheckItem(cat, "result_b", pulley.measurementResult.b)
        addCheckItem(cat, "result_c", pulley.measurementResult.c)
        addCheckItem(cat, "result_d", pulley.measurementResult.d)
        addCheckItem(cat, "result_e", pulley.measurementResult.e)
        addResultCheckItem(cat, "result_finding", pulley.measurementResult.finding)
    }


    // 6. Map Testing to CheckItems
    val funcTestCat = "testing_function"
    testing.functionTest.let { test ->
        addTestResultCheckItem(funcTestCat, "hoistingLowering", test.hoistingLowering)
        addTestResultCheckItem(funcTestCat, "extendedRetractedBoom", test.extendedRetractedBoom)
        addTestResultCheckItem(funcTestCat, "extendedRetractedOutrigger", test.extendedRetractedOutrigger)
        addTestResultCheckItem(funcTestCat, "swingSlewing", test.swingSlewing)
        addTestResultCheckItem(funcTestCat, "antiTwoBlock", test.antiTwoBlock)
        addTestResultCheckItem(funcTestCat, "boomStop", test.boomStop)
        addTestResultCheckItem(funcTestCat, "anemometerWindSpeed", test.anemometerWindSpeed)
        addTestResultCheckItem(funcTestCat, "brakeLockingDevice", test.brakeLockingDevice)
        addTestResultCheckItem(funcTestCat, "loadMomentIndicator", test.loadMomentIndicator)
        addTestResultCheckItem(funcTestCat, "turnSignal", test.turnSignal)
        addTestResultCheckItem(funcTestCat, "drivingLights", test.drivingLights)
        addTestResultCheckItem(funcTestCat, "loadIndicatorLight", test.loadIndicatorLight)
        addTestResultCheckItem(funcTestCat, "rotaryLamp", test.rotaryLamp)
        addTestResultCheckItem(funcTestCat, "horn", test.horn)
        addTestResultCheckItem(funcTestCat, "swingAlarm", test.swingAlarm)
        addTestResultCheckItem(funcTestCat, "reverseAlarm", test.reverseAlarm)
        addTestResultCheckItem(funcTestCat, "overloadAlarm", test.overloadAlarm)
    }

    testing.loadTest.dynamic.mainHook.forEachIndexed { index, item ->
        val cat = "testing_load_dynamic_main_item_$index"
        addCheckItem(cat, "boomLength", item.boomLength)
        addCheckItem(cat, "radius", item.radius)
        addCheckItem(cat, "boomAngle", item.boomAngle)
        addCheckItem(cat, "testLoadKg", item.testLoadKg)
        addCheckItem(cat, "safeWorkingLoadKg", item.safeWorkingLoadKg)
        addCheckItem(cat, "result", item.result)
    }
    testing.loadTest.dynamic.auxiliaryHook.forEachIndexed { index, item ->
        val cat = "testing_load_dynamic_aux_item_$index"
        addCheckItem(cat, "boomLength", item.boomLength)
        addCheckItem(cat, "radius", item.radius)
        addCheckItem(cat, "boomAngle", item.boomAngle)
        addCheckItem(cat, "testLoadKg", item.testLoadKg)
        addCheckItem(cat, "safeWorkingLoadKg", item.safeWorkingLoadKg)
        addCheckItem(cat, "result", item.result)
    }
    testing.loadTest.static.mainHook.forEachIndexed { index, item ->
        val cat = "testing_load_static_main_item_$index"
        addCheckItem(cat, "boomLength", item.boomLength)
        addCheckItem(cat, "radius", item.radius)
        addCheckItem(cat, "boomAngle", item.boomAngle)
        addCheckItem(cat, "testLoadKg", item.testLoadKg)
        addCheckItem(cat, "safeWorkingLoadKg", item.safeWorkingLoadKg)
        addCheckItem(cat, "result", item.result)
    }
    testing.loadTest.static.auxiliaryHook.forEachIndexed { index, item ->
        val cat = "testing_load_static_aux_item_$index"
        addCheckItem(cat, "boomLength", item.boomLength)
        addCheckItem(cat, "radius", item.radius)
        addCheckItem(cat, "boomAngle", item.boomAngle)
        addCheckItem(cat, "testLoadKg", item.testLoadKg)
        addCheckItem(cat, "safeWorkingLoadKg", item.safeWorkingLoadKg)
        addCheckItem(cat, "result", item.result)
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
 * Maps an InspectionWithDetailsDomain model to the MobileCraneUiState without using reflection.
 * This function reconstructs the nested UI state from the flattened domain model.
 */
fun InspectionWithDetailsDomain.toMobileCraneUiState(): MobileCraneUiState {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    // Helper to get a simple string value from check items
    fun getCheckItemValue(category: String, itemName: String): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    fun getBooleanCheckItemValue(category: String, itemName: String): Boolean {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.status ?: false
    }

    fun getCheckItemValueForIndexed(prefix: String, index: Int, itemName: String): String {
        val category = "${prefix}_${index}"
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    fun getBooleanCheckItemValueForIndexed(prefix: String, index: Int, itemName: String): Boolean {
        val category = "${prefix}_${index}"
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.status ?: false
    }

    // Helper to get an InspectionResult from check items
    fun getResultCheckItem(category: String, itemName: String): MobileCraneInspectionResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return MobileCraneInspectionResult(
            status = item?.status ?: false,
            result = item?.result ?: ""
        )
    }

    // Helper to get a TestResult from check items
    fun getTestResultCheckItem(category: String, itemName: String): MobileCraneTestResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return MobileCraneTestResult(
            status = item?.status ?: false,
            remarks = item?.result ?: ""
        )
    }


    // 1. Reconstruct General Data
    val generalData = MobileCraneGeneralData(
        owner = inspection.ownerName ?: "",
        address = inspection.ownerAddress ?: "",
        userAddress = inspection.addressUsageLocation ?: "",
        unitLocation = inspection.usageLocation ?: "",
        driveType = inspection.driveType ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        brandType = inspection.manufacturer?.brandOrType ?: "",
        yearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumber = inspection.serialNumber ?: "",
        liftingCapacity = inspection.capacity ?: "",
        permitNumber = inspection.permitNumber ?: "",
        personInCharge = getCheckItemValue("general_data", "personInCharge"),
        operatorName = getCheckItemValue("general_data", "operatorName"),
        intendedUse = getCheckItemValue("general_data", "intendedUse"),
        operatorCertificate = getCheckItemValue("general_data", "operatorCertificate"),
        equipmentHistory = getCheckItemValue("general_data", "equipmentHistory")
    )

    // 2. Reconstruct Technical Data
    val techCategory = "technical_data"
    val technicalData = MobileCraneTechnicalData(
        specifications = MobileCraneSpecifications(
            maximumWorkingLoadCapacity = getCheckItemValue(techCategory, "spec_maximumWorkingLoadCapacity"),
            boomLength = getCheckItemValue(techCategory, "spec_boomLength"),
            maximumJibLength = getCheckItemValue(techCategory, "spec_maximumJibLength"),
            maximumJibWorkingLoad = getCheckItemValue(techCategory, "spec_maximumJibWorkingLoad"),
            maxBoomJibLength = getCheckItemValue(techCategory, "spec_maxBoomJibLength"),
            craneWeight = getCheckItemValue(techCategory, "spec_craneWeight"),
            maxLiftingHeight = getCheckItemValue(techCategory, "spec_maxLiftingHeight"),
            boomWorkingAngle = getCheckItemValue(techCategory, "spec_boomWorkingAngle")
        ),
        driveMotor = MobileCraneDriveMotor(
            engineNumber = getCheckItemValue(techCategory, "motor_engineNumber"),
            type = getCheckItemValue(techCategory, "motor_type"),
            numberOfCylinders = getCheckItemValue(techCategory, "motor_numberOfCylinders"),
            netPower = getCheckItemValue(techCategory, "motor_netPower"),
            brandYearOfManufacture = getCheckItemValue(techCategory, "motor_brandYearOfManufacture"),
            manufacturer = getCheckItemValue(techCategory, "motor_manufacturer")
        ),
        mainHook = MobileCraneHook(
            type = getCheckItemValue(techCategory, "mainHook_type"),
            capacity = getCheckItemValue(techCategory, "mainHook_capacity"),
            material = getCheckItemValue(techCategory, "mainHook_material"),
            serialNumber = getCheckItemValue(techCategory, "mainHook_serialNumber")
        ),
        auxiliaryHook = MobileCraneHook(
            type = getCheckItemValue(techCategory, "auxHook_type"),
            capacity = getCheckItemValue(techCategory, "auxHook_capacity"),
            material = getCheckItemValue(techCategory, "auxHook_material"),
            serialNumber = getCheckItemValue(techCategory, "auxHook_serialNumber")
        ),
        wireRope = MobileCraneWireRope(
            mainLoadHoistDrum = MobileCraneWireRopeDrum(
                diameter = getCheckItemValue(techCategory, "rope_main_diameter"),
                type = getCheckItemValue(techCategory, "rope_main_type"),
                construction = getCheckItemValue(techCategory, "rope_main_construction"),
                breakingStrength = getCheckItemValue(techCategory, "rope_main_breakingStrength"),
                length = getCheckItemValue(techCategory, "rope_main_length")
            ),
            auxiliaryLoadHoistDrum = MobileCraneWireRopeDrum(
                diameter = getCheckItemValue(techCategory, "rope_aux_diameter"),
                type = getCheckItemValue(techCategory, "rope_aux_type"),
                construction = getCheckItemValue(techCategory, "rope_aux_construction"),
                breakingStrength = getCheckItemValue(techCategory, "rope_aux_breakingStrength"),
                length = getCheckItemValue(techCategory, "rope_aux_length")
            ),
            boomHoistDrum = MobileCraneWireRopeDrum(
                diameter = getCheckItemValue(techCategory, "rope_boom_diameter"),
                type = getCheckItemValue(techCategory, "rope_boom_type"),
                construction = getCheckItemValue(techCategory, "rope_boom_construction"),
                breakingStrength = getCheckItemValue(techCategory, "rope_boom_breakingStrength"),
                length = getCheckItemValue(techCategory, "rope_boom_length")
            )
        )
    )

    // 3. Reconstruct Visual Inspection
    val visualCategory = "visual_inspection"
    val visualInspection = MobileCraneVisualInspection(
        foundationAnchorBoltCorrosion = getResultCheckItem(visualCategory, "foundationAnchorBoltCorrosion"),
        foundationAnchorBoltCracks = getResultCheckItem(visualCategory, "foundationAnchorBoltCracks"),
        foundationAnchorBoltDeformation = getResultCheckItem(visualCategory, "foundationAnchorBoltDeformation"),
        foundationAnchorBoltTightness = getResultCheckItem(visualCategory, "foundationAnchorBoltTightness"),
        frameColumnsOnFoundationCorrosion = getResultCheckItem(visualCategory, "frameColumnsOnFoundationCorrosion"),
        frameColumnsOnFoundationCracks = getResultCheckItem(visualCategory, "frameColumnsOnFoundationCracks"),
        frameColumnsOnFoundationDeformation = getResultCheckItem(visualCategory, "frameColumnsOnFoundationDeformation"),
        frameColumnsOnFoundationFastening = getResultCheckItem(visualCategory, "frameColumnsOnFoundationFastening"),
        frameColumnsOnFoundationTransverseReinforcement = getResultCheckItem(visualCategory, "frameColumnsOnFoundationTransverseReinforcement"),
        frameColumnsOnFoundationDiagonalReinforcement = getResultCheckItem(visualCategory, "frameColumnsOnFoundationDiagonalReinforcement"),
        ladderCorrosion = getResultCheckItem(visualCategory, "ladderCorrosion"),
        ladderCracks = getResultCheckItem(visualCategory, "ladderCracks"),
        ladderDeformation = getResultCheckItem(visualCategory, "ladderDeformation"),
        ladderFastening = getResultCheckItem(visualCategory, "ladderFastening"),
        workingPlatformCorrosion = getResultCheckItem(visualCategory, "workingPlatformCorrosion"),
        workingPlatformCracks = getResultCheckItem(visualCategory, "workingPlatformCracks"),
        workingPlatformDeformation = getResultCheckItem(visualCategory, "workingPlatformDeformation"),
        workingPlatformFastening = getResultCheckItem(visualCategory, "workingPlatformFastening"),
        outriggersOutriggerArmHousing = getResultCheckItem(visualCategory, "outriggersOutriggerArmHousing"),
        outriggersOutriggerArms = getResultCheckItem(visualCategory, "outriggersOutriggerArms"),
        outriggersJack = getResultCheckItem(visualCategory, "outriggersJack"),
        outriggersOutriggerPads = getResultCheckItem(visualCategory, "outriggersOutriggerPads"),
        outriggersHousingConnectionToChassis = getResultCheckItem(visualCategory, "outriggersHousingConnectionToChassis"),
        outriggersOutriggerSafetyLocks = getResultCheckItem(visualCategory, "outriggersOutriggerSafetyLocks"),
        turntableSlewingRollerBearing = getResultCheckItem(visualCategory, "turntableSlewingRollerBearing"),
        turntableBrakeHousing = getResultCheckItem(visualCategory, "turntableBrakeHousing"),
        turntableBrakeLiningsAndShoes = getResultCheckItem(visualCategory, "turntableBrakeLiningsAndShoes"),
        turntableDrumSurface = getResultCheckItem(visualCategory, "turntableDrumSurface"),
        turntablePressureCylinder = getResultCheckItem(visualCategory, "turntablePressureCylinder"),
        turntableDrumAxle = getResultCheckItem(visualCategory, "turntableDrumAxle"),
        turntableLeversPinsBolts = getResultCheckItem(visualCategory, "turntableLeversPinsBolts"),
        turntableGuard = getResultCheckItem(visualCategory, "turntableGuard"),
        latticeBoomMainBoom = getResultCheckItem(visualCategory, "latticeBoomMainBoom"),
        latticeBoomBoomSection = getResultCheckItem(visualCategory, "latticeBoomBoomSection"),
        latticeBoomTopPulley = getResultCheckItem(visualCategory, "latticeBoomTopPulley"),
        latticeBoomPulleyGuard = getResultCheckItem(visualCategory, "latticeBoomPulleyGuard"),
        latticeBoomWireRopeGuard = getResultCheckItem(visualCategory, "latticeBoomWireRopeGuard"),
        latticeBoomPulleyGrooveLip = getResultCheckItem(visualCategory, "latticeBoomPulleyGrooveLip"),
        latticeBoomPivotPin = getResultCheckItem(visualCategory, "latticeBoomPivotPin"),
        latticeBoomWireRopeGuidePulley = getResultCheckItem(visualCategory, "latticeBoomWireRopeGuidePulley"),
        clutchMainClutch = getResultCheckItem(visualCategory, "clutchMainClutch"),
        transmission = getResultCheckItem(visualCategory, "transmission"),
        steeringFrontWheel = getResultCheckItem(visualCategory, "steeringFrontWheel"),
        steeringMiddleWheel = getResultCheckItem(visualCategory, "steeringMiddleWheel"),
        steeringRearWheel = getResultCheckItem(visualCategory, "steeringRearWheel"),
        brakeServiceBrake = getResultCheckItem(visualCategory, "brakeServiceBrake"),
        brakeParkingBrake = getResultCheckItem(visualCategory, "brakeParkingBrake"),
        brakeBrakeHousing = getResultCheckItem(visualCategory, "brakeBrakeHousing"),
        brakeBrakeLiningsAndShoes = getResultCheckItem(visualCategory, "brakeBrakeLiningsAndShoes"),
        brakeDrumSurface = getResultCheckItem(visualCategory, "brakeDrumSurface"),
        brakeLeversPinsBolts = getResultCheckItem(visualCategory, "brakeLeversPinsBolts"),
        brakeGuard = getResultCheckItem(visualCategory, "brakeGuard"),
        travelDrumClutchHousing = getResultCheckItem(visualCategory, "travelDrumClutchHousing"),
        travelDrumClutchLining = getResultCheckItem(visualCategory, "travelDrumClutchLining"),
        travelDrumClutchDrumSurface = getResultCheckItem(visualCategory, "travelDrumClutchDrumSurface"),
        travelDrumLeversPinsBolts = getResultCheckItem(visualCategory, "travelDrumLeversPinsBolts"),
        travelDrumGuard = getResultCheckItem(visualCategory, "travelDrumGuard"),
        mainWinchDrumMounting = getResultCheckItem(visualCategory, "mainWinchDrumMounting"),
        mainWinchWindingDrumSurface = getResultCheckItem(visualCategory, "mainWinchWindingDrumSurface"),
        mainWinchBrakeLiningsAndShoes = getResultCheckItem(visualCategory, "mainWinchBrakeLiningsAndShoes"),
        mainWinchBrakeDrumSurface = getResultCheckItem(visualCategory, "mainWinchBrakeDrumSurface"),
        mainWinchBrakeHousing = getResultCheckItem(visualCategory, "mainWinchBrakeHousing"),
        mainWinchClutchLiningsAndShoes = getResultCheckItem(visualCategory, "mainWinchClutchLiningsAndShoes"),
        mainWinchClutchDrumSurface = getResultCheckItem(visualCategory, "mainWinchClutchDrumSurface"),
        mainWinchGroove = getResultCheckItem(visualCategory, "mainWinchGroove"),
        mainWinchGrooveLip = getResultCheckItem(visualCategory, "mainWinchGrooveLip"),
        mainWinchFlanges = getResultCheckItem(visualCategory, "mainWinchFlanges"),
        mainWinchBrakeActuatorLeversPinsAndBolts = getResultCheckItem(visualCategory, "mainWinchBrakeActuatorLeversPinsAndBolts"),
        auxiliaryWinchDrumMounting = getResultCheckItem(visualCategory, "auxiliaryWinchDrumMounting"),
        auxiliaryWinchWindingDrumSurface = getResultCheckItem(visualCategory, "auxiliaryWinchWindingDrumSurface"),
        auxiliaryWinchBrakeLiningsAndShoes = getResultCheckItem(visualCategory, "auxiliaryWinchBrakeLiningsAndShoes"),
        auxiliaryWinchBrakeDrumSurface = getResultCheckItem(visualCategory, "auxiliaryWinchBrakeDrumSurface"),
        auxiliaryWinchBrakeHousing = getResultCheckItem(visualCategory, "auxiliaryWinchBrakeHousing"),
        auxiliaryWinchClutchLiningsAndShoes = getResultCheckItem(visualCategory, "auxiliaryWinchClutchLiningsAndShoes"),
        auxiliaryWinchClutchDrumSurface = getResultCheckItem(visualCategory, "auxiliaryWinchClutchDrumSurface"),
        auxiliaryWinchGroove = getResultCheckItem(visualCategory, "auxiliaryWinchGroove"),
        auxiliaryWinchGrooveLip = getResultCheckItem(visualCategory, "auxiliaryWinchGrooveLip"),
        auxiliaryWinchFlanges = getResultCheckItem(visualCategory, "auxiliaryWinchFlanges"),
        auxiliaryWinchBrakeActuatorLeversPinsAndBolts = getResultCheckItem(visualCategory, "auxiliaryWinchBrakeActuatorLeversPinsAndBolts"),
        hoistGearBlockLubrication = getResultCheckItem(visualCategory, "hoistGearBlockLubrication"),
        hoistGearBlockOilSeal = getResultCheckItem(visualCategory, "hoistGearBlockOilSeal"),
        mainPulleyPulleyGroove = getResultCheckItem(visualCategory, "mainPulleyPulleyGroove"),
        mainPulleyPulleyGrooveLip = getResultCheckItem(visualCategory, "mainPulleyPulleyGrooveLip"),
        mainPulleyPulleyPin = getResultCheckItem(visualCategory, "mainPulleyPulleyPin"),
        mainPulleyBearing = getResultCheckItem(visualCategory, "mainPulleyBearing"),
        mainPulleyPulleyGuard = getResultCheckItem(visualCategory, "mainPulleyPulleyGuard"),
        mainPulleyWireRopeGuard = getResultCheckItem(visualCategory, "mainPulleyWireRopeGuard"),
        mainHookVisualSwivelNutAndBearing = getResultCheckItem(visualCategory, "mainHookVisualSwivelNutAndBearing"),
        mainHookVisualTrunnion = getResultCheckItem(visualCategory, "mainHookVisualTrunnion"),
        mainHookVisualSafetyLatch = getResultCheckItem(visualCategory, "mainHookVisualSafetyLatch"),
        auxiliaryHookVisualFreeFallWeight = getResultCheckItem(visualCategory, "auxiliaryHookVisualFreeFallWeight"),
        auxiliaryHookVisualSwivelNutAndBearing = getResultCheckItem(visualCategory, "auxiliaryHookVisualSwivelNutAndBearing"),
        auxiliaryHookVisualSafetyLatch = getResultCheckItem(visualCategory, "auxiliaryHookVisualSafetyLatch"),
        mainWireRopeVisualCorrosion = getResultCheckItem(visualCategory, "mainWireRopeVisualCorrosion"),
        mainWireRopeVisualWear = getResultCheckItem(visualCategory, "mainWireRopeVisualWear"),
        mainWireRopeVisualBreakage = getResultCheckItem(visualCategory, "mainWireRopeVisualBreakage"),
        mainWireRopeVisualDeformation = getResultCheckItem(visualCategory, "mainWireRopeVisualDeformation"),
        auxiliaryWireRopeVisualCorrosion = getResultCheckItem(visualCategory, "auxiliaryWireRopeVisualCorrosion"),
        auxiliaryWireRopeVisualWear = getResultCheckItem(visualCategory, "auxiliaryWireRopeVisualWear"),
        auxiliaryWireRopeVisualBreakage = getResultCheckItem(visualCategory, "auxiliaryWireRopeVisualBreakage"),
        auxiliaryWireRopeVisualDeformation = getResultCheckItem(visualCategory, "auxiliaryWireRopeVisualDeformation"),
        limitSwitchLsLongTravel = getResultCheckItem(visualCategory, "limitSwitchLsLongTravel"),
        limitSwitchLsCrossTravel = getResultCheckItem(visualCategory, "limitSwitchLsCrossTravel"),
        limitSwitchLsHoisting = getResultCheckItem(visualCategory, "limitSwitchLsHoisting"),
        internalCombustionEngineCoolingSystem = getResultCheckItem(visualCategory, "internalCombustionEngineCoolingSystem"),
        internalCombustionEngineLubricationSystem = getResultCheckItem(visualCategory, "internalCombustionEngineLubricationSystem"),
        internalCombustionEngineEngineMounting = getResultCheckItem(visualCategory, "internalCombustionEngineEngineMounting"),
        internalCombustionEngineSafetyGuardEquipment = getResultCheckItem(visualCategory, "internalCombustionEngineSafetyGuardEquipment"),
        internalCombustionEngineExhaustSystem = getResultCheckItem(visualCategory, "internalCombustionEngineExhaustSystem"),
        internalCombustionEngineAirIntakeSystem = getResultCheckItem(visualCategory, "internalCombustionEngineAirIntakeSystem"),
        internalCombustionEngineFuelSystem = getResultCheckItem(visualCategory, "internalCombustionEngineFuelSystem"),
        internalCombustionEnginePowerTransmissionSystem = getResultCheckItem(visualCategory, "internalCombustionEnginePowerTransmissionSystem"),
        internalCombustionEngineBattery = getResultCheckItem(visualCategory, "internalCombustionEngineBattery"),
        internalCombustionEngineStarterMotor = getResultCheckItem(visualCategory, "internalCombustionEngineStarterMotor"),
        internalCombustionEngineWiringInstallation = getResultCheckItem(visualCategory, "internalCombustionEngineWiringInstallation"),
        internalCombustionEngineTurbocharger = getResultCheckItem(visualCategory, "internalCombustionEngineTurbocharger"),
        hydraulicHydraulicPump = getResultCheckItem(visualCategory, "hydraulicHydraulicPump"),
        hydraulicHydraulicLines = getResultCheckItem(visualCategory, "hydraulicHydraulicLines"),
        hydraulicHydraulicFilter = getResultCheckItem(visualCategory, "hydraulicHydraulicFilter"),
        hydraulicHydraulicTank = getResultCheckItem(visualCategory, "hydraulicHydraulicTank"),
        hydraulicMotorMainWinchMotor = getResultCheckItem(visualCategory, "hydraulicMotorMainWinchMotor"),
        hydraulicMotorAuxiliaryWinchMotor = getResultCheckItem(visualCategory, "hydraulicMotorAuxiliaryWinchMotor"),
        hydraulicMotorBoomWinchMotor = getResultCheckItem(visualCategory, "hydraulicMotorBoomWinchMotor"),
        hydraulicMotorSwingMotor = getResultCheckItem(visualCategory, "hydraulicMotorSwingMotor"),
        controlValveReliefValve = getResultCheckItem(visualCategory, "controlValveReliefValve"),
        controlValveMainWinchValve = getResultCheckItem(visualCategory, "controlValveMainWinchValve"),
        controlValveAuxiliaryWinchValve = getResultCheckItem(visualCategory, "controlValveAuxiliaryWinchValve"),
        controlValveBoomWinchValve = getResultCheckItem(visualCategory, "controlValveBoomWinchValve"),
        controlValveBoomMovementValve = getResultCheckItem(visualCategory, "controlValveBoomMovementValve"),
        controlValveSteeringCylinderValve = getResultCheckItem(visualCategory, "controlValveSteeringCylinderValve"),
        controlValveAxleOscillationValve = getResultCheckItem(visualCategory, "controlValveAxleOscillationValve"),
        controlValveOutriggerMovementValve = getResultCheckItem(visualCategory, "controlValveOutriggerMovementValve"),
        hydraulicCylinderBoomMovementCylinder = getResultCheckItem(visualCategory, "hydraulicCylinderBoomMovementCylinder"),
        hydraulicCylinderOutriggerCylinder = getResultCheckItem(visualCategory, "hydraulicCylinderOutriggerCylinder"),
        hydraulicCylinderSteeringWheelCylinder = getResultCheckItem(visualCategory, "hydraulicCylinderSteeringWheelCylinder"),
        hydraulicCylinderAxleOscillationCylinder = getResultCheckItem(visualCategory, "hydraulicCylinderAxleOscillationCylinder"),
        hydraulicCylinderTelescopicCylinder = getResultCheckItem(visualCategory, "hydraulicCylinderTelescopicCylinder"),
        pneumaticCompressor = getResultCheckItem(visualCategory, "pneumaticCompressor"),
        pneumaticTankAndSafetyValve = getResultCheckItem(visualCategory, "pneumaticTankAndSafetyValve"),
        pneumaticPressurizedAirLines = getResultCheckItem(visualCategory, "pneumaticPressurizedAirLines"),
        pneumaticAirFilter = getResultCheckItem(visualCategory, "pneumaticAirFilter"),
        pneumaticControlValve = getResultCheckItem(visualCategory, "pneumaticControlValve"),
        operatorCabinSafetyLadder = getResultCheckItem(visualCategory, "operatorCabinSafetyLadder"),
        operatorCabinDoor = getResultCheckItem(visualCategory, "operatorCabinDoor"),
        operatorCabinWindow = getResultCheckItem(visualCategory, "operatorCabinWindow"),
        operatorCabinFanAc = getResultCheckItem(visualCategory, "operatorCabinFanAc"),
        operatorCabinControlLeversButtons = getResultCheckItem(visualCategory, "operatorCabinControlLeversButtons"),
        operatorCabinPendantControl = getResultCheckItem(visualCategory, "operatorCabinPendantControl"),
        operatorCabinLighting = getResultCheckItem(visualCategory, "operatorCabinLighting"),
        operatorCabinHornSignalAlarm = getResultCheckItem(visualCategory, "operatorCabinHornSignalAlarm"),
        operatorCabinFuse = getResultCheckItem(visualCategory, "operatorCabinFuse"),
        operatorCabinCommunicationDevice = getResultCheckItem(visualCategory, "operatorCabinCommunicationDevice"),
        operatorCabinFireExtinguisher = getResultCheckItem(visualCategory, "operatorCabinFireExtinguisher"),
        operatorCabinOperatingSigns = getResultCheckItem(visualCategory, "operatorCabinOperatingSigns"),
        operatorCabinIgnitionKeyMasterSwitch = getResultCheckItem(visualCategory, "operatorCabinIgnitionKeyMasterSwitch"),
        operatorCabinButtonsHandlesLevers = getResultCheckItem(visualCategory, "operatorCabinButtonsHandlesLevers"),
        electricalComponentsPanelConductorConnector = getResultCheckItem(visualCategory, "electricalComponentsPanelConductorConnector"),
        electricalComponentsConductorProtection = getResultCheckItem(visualCategory, "electricalComponentsConductorProtection"),
        electricalComponentsMotorInstallationSafetySystem = getResultCheckItem(visualCategory, "electricalComponentsMotorInstallationSafetySystem"),
        electricalComponentsGroundingSystem = getResultCheckItem(visualCategory, "electricalComponentsGroundingSystem"),
        electricalComponentsInstallation = getResultCheckItem(visualCategory, "electricalComponentsInstallation"),
        safetyDevicesLadderHandrail = getResultCheckItem(visualCategory, "safetyDevicesLadderHandrail"),
        safetyDevicesEngineOilLubricantPressure = getResultCheckItem(visualCategory, "safetyDevicesEngineOilLubricantPressure"),
        safetyDevicesHydraulicOilPressure = getResultCheckItem(visualCategory, "safetyDevicesHydraulicOilPressure"),
        safetyDevicesAirPressure = getResultCheckItem(visualCategory, "safetyDevicesAirPressure"),
        safetyDevicesAmperemeter = getResultCheckItem(visualCategory, "safetyDevicesAmperemeter"),
        safetyDevicesVoltage = getResultCheckItem(visualCategory, "safetyDevicesVoltage"),
        safetyDevicesEngineTemperature = getResultCheckItem(visualCategory, "safetyDevicesEngineTemperature"),
        safetyDevicesTransmissionTemperature = getResultCheckItem(visualCategory, "safetyDevicesTransmissionTemperature"),
        safetyDevicesConverterOilTemperaturePressure = getResultCheckItem(visualCategory, "safetyDevicesConverterOilTemperaturePressure"),
        safetyDevicesSpeedometerIndicator = getResultCheckItem(visualCategory, "safetyDevicesSpeedometerIndicator"),
        safetyDevicesRotaryLamp = getResultCheckItem(visualCategory, "safetyDevicesRotaryLamp"),
        safetyDevicesMainHoistRopeUpDownLimit = getResultCheckItem(visualCategory, "safetyDevicesMainHoistRopeUpDownLimit"),
        safetyDevicesAuxiliaryHoistRopeUpDownLimit = getResultCheckItem(visualCategory, "safetyDevicesAuxiliaryHoistRopeUpDownLimit"),
        safetyDevicesSwingMotionLimit = getResultCheckItem(visualCategory, "safetyDevicesSwingMotionLimit"),
        safetyDevicesLevelIndicator = getResultCheckItem(visualCategory, "safetyDevicesLevelIndicator"),
        safetyDevicesLoadWeightIndicator = getResultCheckItem(visualCategory, "safetyDevicesLoadWeightIndicator"),
        safetyDevicesLoadChart = getResultCheckItem(visualCategory, "safetyDevicesLoadChart"),
        safetyDevicesAnemometerWindSpeed = getResultCheckItem(visualCategory, "safetyDevicesAnemometerWindSpeed"),
        safetyDevicesBoomAngleIndicator = getResultCheckItem(visualCategory, "safetyDevicesBoomAngleIndicator"),
        safetyDevicesAirPressureIndicator = getResultCheckItem(visualCategory, "safetyDevicesAirPressureIndicator"),
        safetyDevicesHydraulicPressureIndicator = getResultCheckItem(visualCategory, "safetyDevicesHydraulicPressureIndicator"),
        safetyDevicesSafetyValves = getResultCheckItem(visualCategory, "safetyDevicesSafetyValves"),
        safetyDevicesMainWindingDrumSafetyLock = getResultCheckItem(visualCategory, "safetyDevicesMainWindingDrumSafetyLock"),
        safetyDevicesAuxiliaryWindingDrumSafetyLock = getResultCheckItem(visualCategory, "safetyDevicesAuxiliaryWindingDrumSafetyLock"),
        safetyDevicesTelescopicMotionLimit = getResultCheckItem(visualCategory, "safetyDevicesTelescopicMotionLimit"),
        safetyDevicesLightningArrester = getResultCheckItem(visualCategory, "safetyDevicesLightningArrester"),
        safetyDevicesLiftingHeightIndicator = getResultCheckItem(visualCategory, "safetyDevicesLiftingHeightIndicator")
    )

    // 4. Reconstruct NDE
    val ndeWireRopeItems = mutableListOf<MobileCraneNdeWireRopeItem>()
    val wireRopeItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_wireRope_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    wireRopeItemCategories.forEach { index ->
        ndeWireRopeItems.add(MobileCraneNdeWireRopeItem(
            usage = getCheckItemValueForIndexed("nde_wireRope_item", index, "usage"),
            specDiameter = getCheckItemValueForIndexed("nde_wireRope_item", index, "specDiameter"),
            actualDiameter = getCheckItemValueForIndexed("nde_wireRope_item", index, "actualDiameter"),
            construction = getCheckItemValueForIndexed("nde_wireRope_item", index, "construction"),
            type = getCheckItemValueForIndexed("nde_wireRope_item", index, "type"),
            length = getCheckItemValueForIndexed("nde_wireRope_item", index, "length"),
            age = getCheckItemValueForIndexed("nde_wireRope_item", index, "age"),
            hasDefect = getBooleanCheckItemValueForIndexed("nde_wireRope_item", index, "hasDefect"),
            remarks = getCheckItemValueForIndexed("nde_wireRope_item", index, "remarks")
        ))
    }

    val ndeBoomItems = mutableListOf<MobileCraneNdeBoomItem>()
    val boomItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_boom_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    boomItemCategories.forEach { index ->
        ndeBoomItems.add(MobileCraneNdeBoomItem(
            partInspected = getCheckItemValueForIndexed("nde_boom_item", index, "partInspected"),
            location = getCheckItemValueForIndexed("nde_boom_item", index, "location"),
            hasDefect = getBooleanCheckItemValueForIndexed("nde_boom_item", index, "hasDefect"),
            remarks = getCheckItemValueForIndexed("nde_boom_item", index, "remarks")
        ))
    }

    val mainHook = MobileCraneNdeHookSection(
        hookCapacity = getCheckItemValue("nde_mainHook", "hookCapacity"),
        ndtType = getCheckItemValue("nde_mainHook", "ndtType"),
        specification = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainHook", "spec_a"), b = getCheckItemValue("nde_mainHook", "spec_b"),
            c = getCheckItemValue("nde_mainHook", "spec_c"), d = getCheckItemValue("nde_mainHook", "spec_d"),
            e = getCheckItemValue("nde_mainHook", "spec_e"), f = getCheckItemValue("nde_mainHook", "spec_f"),
            g = getCheckItemValue("nde_mainHook", "spec_g"), h = getCheckItemValue("nde_mainHook", "spec_h"),
            finding = getResultCheckItem("nde_mainHook", "spec_finding")
        ),
        measurementResult = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainHook", "result_a"), b = getCheckItemValue("nde_mainHook", "result_b"),
            c = getCheckItemValue("nde_mainHook", "result_c"), d = getCheckItemValue("nde_mainHook", "result_d"),
            e = getCheckItemValue("nde_mainHook", "result_e"), f = getCheckItemValue("nde_mainHook", "result_f"),
            g = getCheckItemValue("nde_mainHook", "result_g"), h = getCheckItemValue("nde_mainHook", "result_h"),
            finding = getResultCheckItem("nde_mainHook", "result_finding")
        ),
        tolerance = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainHook", "tolerance_a"), b = getCheckItemValue("nde_mainHook", "tolerance_b"),
            c = getCheckItemValue("nde_mainHook", "tolerance_c"), d = getCheckItemValue("nde_mainHook", "tolerance_d"),
            e = getCheckItemValue("nde_mainHook", "tolerance_e"), f = getCheckItemValue("nde_mainHook", "tolerance_f"),
            g = getCheckItemValue("nde_mainHook", "tolerance_g"), h = getCheckItemValue("nde_mainHook", "tolerance_h"),
            finding = getResultCheckItem("nde_mainHook", "tolerance_finding")
        )
    )

    val auxHook = MobileCraneNdeHookSection(
        hookCapacity = getCheckItemValue("nde_auxHook", "hookCapacity"),
        ndtType = getCheckItemValue("nde_auxHook", "ndtType"),
        specification = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_auxHook", "spec_a"), b = getCheckItemValue("nde_auxHook", "spec_b"),
            c = getCheckItemValue("nde_auxHook", "spec_c"), d = getCheckItemValue("nde_auxHook", "spec_d"),
            e = getCheckItemValue("nde_auxHook", "spec_e"), f = getCheckItemValue("nde_auxHook", "spec_f"),
            g = getCheckItemValue("nde_auxHook", "spec_g"), h = getCheckItemValue("nde_auxHook", "spec_h"),
            finding = getResultCheckItem("nde_auxHook", "spec_finding")
        ),
        measurementResult = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_auxHook", "result_a"), b = getCheckItemValue("nde_auxHook", "result_b"),
            c = getCheckItemValue("nde_auxHook", "result_c"), d = getCheckItemValue("nde_auxHook", "result_d"),
            e = getCheckItemValue("nde_auxHook", "result_e"), f = getCheckItemValue("nde_auxHook", "result_f"),
            g = getCheckItemValue("nde_auxHook", "result_g"), h = getCheckItemValue("nde_auxHook", "result_h"),
            finding = getResultCheckItem("nde_auxHook", "result_finding")
        ),
        tolerance = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_auxHook", "tolerance_a"), b = getCheckItemValue("nde_auxHook", "tolerance_b"),
            c = getCheckItemValue("nde_auxHook", "tolerance_c"), d = getCheckItemValue("nde_auxHook", "tolerance_d"),
            e = getCheckItemValue("nde_auxHook", "tolerance_e"), f = getCheckItemValue("nde_auxHook", "tolerance_f"),
            g = getCheckItemValue("nde_auxHook", "tolerance_g"), h = getCheckItemValue("nde_auxHook", "tolerance_h"),
            finding = getResultCheckItem("nde_auxHook", "tolerance_finding")
        )
    )

    val mainDrum = MobileCraneNdeDrumSection(
        ndtType = getCheckItemValue("nde_mainDrum", "ndtType"),
        specification = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainDrum", "spec_a"), b = getCheckItemValue("nde_mainDrum", "spec_b"),
            c = getCheckItemValue("nde_mainDrum", "spec_c"), d = getCheckItemValue("nde_mainDrum", "spec_d"),
            e = getCheckItemValue("nde_mainDrum", "spec_e"), f = getCheckItemValue("nde_mainDrum", "spec_f"),
            g = getCheckItemValue("nde_mainDrum", "spec_g"),
            finding = getResultCheckItem("nde_mainDrum", "spec_finding")
        ),
        measurementResult = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainDrum", "result_a"), b = getCheckItemValue("nde_mainDrum", "result_b"),
            c = getCheckItemValue("nde_mainDrum", "result_c"), d = getCheckItemValue("nde_mainDrum", "result_d"),
            e = getCheckItemValue("nde_mainDrum", "result_e"), f = getCheckItemValue("nde_mainDrum", "result_f"),
            g = getCheckItemValue("nde_mainDrum", "result_g"),
            finding = getResultCheckItem("nde_mainDrum", "result_finding")
        )
    )

    val auxDrum = MobileCraneNdeDrumSection(
        ndtType = getCheckItemValue("nde_auxDrum", "ndtType"),
        specification = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_auxDrum", "spec_a"), b = getCheckItemValue("nde_auxDrum", "spec_b"),
            c = getCheckItemValue("nde_auxDrum", "spec_c"), d = getCheckItemValue("nde_auxDrum", "spec_d"),
            e = getCheckItemValue("nde_auxDrum", "spec_e"), f = getCheckItemValue("nde_auxDrum", "spec_f"),
            g = getCheckItemValue("nde_auxDrum", "spec_g"),
            finding = getResultCheckItem("nde_auxDrum", "spec_finding")
        ),
        measurementResult = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_auxDrum", "result_a"), b = getCheckItemValue("nde_auxDrum", "result_b"),
            c = getCheckItemValue("nde_auxDrum", "result_c"), d = getCheckItemValue("nde_auxDrum", "result_d"),
            e = getCheckItemValue("nde_auxDrum", "result_e"), f = getCheckItemValue("nde_auxDrum", "result_f"),
            g = getCheckItemValue("nde_auxDrum", "result_g"),
            finding = getResultCheckItem("nde_auxDrum", "result_finding")
        )
    )

    val mainPulley = MobileCraneNdePulleySection(
        ndtType = getCheckItemValue("nde_mainPulley", "ndtType"),
        specification = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainPulley", "spec_a"), b = getCheckItemValue("nde_mainPulley", "spec_b"),
            c = getCheckItemValue("nde_mainPulley", "spec_c"), d = getCheckItemValue("nde_mainPulley", "spec_d"),
            e = getCheckItemValue("nde_mainPulley", "spec_e"),
            finding = getResultCheckItem("nde_mainPulley", "spec_finding")
        ),
        measurementResult = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_mainPulley", "result_a"), b = getCheckItemValue("nde_mainPulley", "result_b"),
            c = getCheckItemValue("nde_mainPulley", "result_c"), d = getCheckItemValue("nde_mainPulley", "result_d"),
            e = getCheckItemValue("nde_mainPulley", "result_e"),
            finding = getResultCheckItem("nde_mainPulley", "result_finding")
        )
    )

    val auxPulley = MobileCraneNdePulleySection(
        ndtType = getCheckItemValue("nde_auxPulley", "ndtType"),
        specification = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_auxPulley", "spec_a"), b = getCheckItemValue("nde_auxPulley", "spec_b"),
            c = getCheckItemValue("nde_auxPulley", "spec_c"), d = getCheckItemValue("nde_auxPulley", "spec_d"),
            e = getCheckItemValue("nde_auxPulley", "spec_e"),
            finding = getResultCheckItem("nde_auxPulley", "spec_finding")
        ),
        measurementResult = MobileCraneNdeMeasurement(
            a = getCheckItemValue("nde_auxPulley", "result_a"), b = getCheckItemValue("nde_auxPulley", "result_b"),
            c = getCheckItemValue("nde_auxPulley", "result_c"), d = getCheckItemValue("nde_auxPulley", "result_d"),
            e = getCheckItemValue("nde_auxPulley", "result_e"),
            finding = getResultCheckItem("nde_auxPulley", "result_finding")
        )
    )

    val nonDestructiveExamination = MobileCraneNonDestructiveExamination(
        wireRope = MobileCraneNdeWireRopeSection(
            ndtType = getCheckItemValue("nde_wireRope", "ndtType"),
            items = ndeWireRopeItems.toImmutableList()
        ),
        boom = MobileCraneNdeBoomSection(
            boomType = getCheckItemValue("nde_boom", "boomType"),
            ndtType = getCheckItemValue("nde_boom", "ndtType"),
            items = ndeBoomItems.toImmutableList()
        ),
        mainHook = mainHook,
        auxiliaryHook = auxHook,
        mainDrum = mainDrum,
        auxiliaryDrum = auxDrum,
        mainPulley = mainPulley,
        auxiliaryPulley = auxPulley
    )

    // 5. Reconstruct Testing
    val funcTestCat = "testing_function"
    val functionTest = MobileCraneFunctionTest(
        hoistingLowering = getTestResultCheckItem(funcTestCat, "hoistingLowering"),
        extendedRetractedBoom = getTestResultCheckItem(funcTestCat, "extendedRetractedBoom"),
        extendedRetractedOutrigger = getTestResultCheckItem(funcTestCat, "extendedRetractedOutrigger"),
        swingSlewing = getTestResultCheckItem(funcTestCat, "swingSlewing"),
        antiTwoBlock = getTestResultCheckItem(funcTestCat, "antiTwoBlock"),
        boomStop = getTestResultCheckItem(funcTestCat, "boomStop"),
        anemometerWindSpeed = getTestResultCheckItem(funcTestCat, "anemometerWindSpeed"),
        brakeLockingDevice = getTestResultCheckItem(funcTestCat, "brakeLockingDevice"),
        loadMomentIndicator = getTestResultCheckItem(funcTestCat, "loadMomentIndicator"),
        turnSignal = getTestResultCheckItem(funcTestCat, "turnSignal"),
        drivingLights = getTestResultCheckItem(funcTestCat, "drivingLights"),
        loadIndicatorLight = getTestResultCheckItem(funcTestCat, "loadIndicatorLight"),
        rotaryLamp = getTestResultCheckItem(funcTestCat, "rotaryLamp"),
        horn = getTestResultCheckItem(funcTestCat, "horn"),
        swingAlarm = getTestResultCheckItem(funcTestCat, "swingAlarm"),
        reverseAlarm = getTestResultCheckItem(funcTestCat, "reverseAlarm"),
        overloadAlarm = getTestResultCheckItem(funcTestCat, "overloadAlarm")
    )

    val dynMainItems = mutableListOf<MobileCraneLoadTestItem>()
    val dynMainCategories = this.checkItems.map { it.category }.filter { it.startsWith("testing_load_dynamic_main_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    dynMainCategories.forEach { index ->
        dynMainItems.add(MobileCraneLoadTestItem(
            boomLength = getCheckItemValueForIndexed("testing_load_dynamic_main_item", index, "boomLength"),
            radius = getCheckItemValueForIndexed("testing_load_dynamic_main_item", index, "radius"),
            boomAngle = getCheckItemValueForIndexed("testing_load_dynamic_main_item", index, "boomAngle"),
            testLoadKg = getCheckItemValueForIndexed("testing_load_dynamic_main_item", index, "testLoadKg"),
            safeWorkingLoadKg = getCheckItemValueForIndexed("testing_load_dynamic_main_item", index, "safeWorkingLoadKg"),
            result = getCheckItemValueForIndexed("testing_load_dynamic_main_item", index, "result"),
        ))
    }

    val dynAuxItems = mutableListOf<MobileCraneLoadTestItem>()
    val dynAuxCategories = this.checkItems.map { it.category }.filter { it.startsWith("testing_load_dynamic_aux_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    dynAuxCategories.forEach { index ->
        dynAuxItems.add(MobileCraneLoadTestItem(
            boomLength = getCheckItemValueForIndexed("testing_load_dynamic_aux_item", index, "boomLength"),
            radius = getCheckItemValueForIndexed("testing_load_dynamic_aux_item", index, "radius"),
            boomAngle = getCheckItemValueForIndexed("testing_load_dynamic_aux_item", index, "boomAngle"),
            testLoadKg = getCheckItemValueForIndexed("testing_load_dynamic_aux_item", index, "testLoadKg"),
            safeWorkingLoadKg = getCheckItemValueForIndexed("testing_load_dynamic_aux_item", index, "safeWorkingLoadKg"),
            result = getCheckItemValueForIndexed("testing_load_dynamic_aux_item", index, "result"),
        ))
    }

    val staMainItems = mutableListOf<MobileCraneLoadTestItem>()
    val staMainCategories = this.checkItems.map { it.category }.filter { it.startsWith("testing_load_static_main_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    staMainCategories.forEach { index ->
        staMainItems.add(MobileCraneLoadTestItem(
            boomLength = getCheckItemValueForIndexed("testing_load_static_main_item", index, "boomLength"),
            radius = getCheckItemValueForIndexed("testing_load_static_main_item", index, "radius"),
            boomAngle = getCheckItemValueForIndexed("testing_load_static_main_item", index, "boomAngle"),
            testLoadKg = getCheckItemValueForIndexed("testing_load_static_main_item", index, "testLoadKg"),
            safeWorkingLoadKg = getCheckItemValueForIndexed("testing_load_static_main_item", index, "safeWorkingLoadKg"),
            result = getCheckItemValueForIndexed("testing_load_static_main_item", index, "result"),
        ))
    }

    val staAuxItems = mutableListOf<MobileCraneLoadTestItem>()
    val staAuxCategories = this.checkItems.map { it.category }.filter { it.startsWith("testing_load_static_aux_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    staAuxCategories.forEach { index ->
        staAuxItems.add(MobileCraneLoadTestItem(
            boomLength = getCheckItemValueForIndexed("testing_load_static_aux_item", index, "boomLength"),
            radius = getCheckItemValueForIndexed("testing_load_static_aux_item", index, "radius"),
            boomAngle = getCheckItemValueForIndexed("testing_load_static_aux_item", index, "boomAngle"),
            testLoadKg = getCheckItemValueForIndexed("testing_load_static_aux_item", index, "testLoadKg"),
            safeWorkingLoadKg = getCheckItemValueForIndexed("testing_load_static_aux_item", index, "safeWorkingLoadKg"),
            result = getCheckItemValueForIndexed("testing_load_static_aux_item", index, "result"),
        ))
    }

    val testing = MobileCraneTesting(
        functionTest = functionTest,
        loadTest = MobileCraneLoadTest(
            dynamic = MobileCraneLoadTestType(
                mainHook = dynMainItems.toImmutableList(),
                auxiliaryHook = dynAuxItems.toImmutableList()
            ),
            static = MobileCraneLoadTestType(
                mainHook = staMainItems.toImmutableList(),
                auxiliaryHook = staAuxItems.toImmutableList()
            )
        )
    )

    // 6. Reconstruct Conclusion
    val summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }
    val recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }
    val conclusion = MobileCraneConclusion(
        summary = summary.toImmutableList(),
        recommendations = recommendations.toImmutableList()
    )

    // 7. Assemble the final report
    val report = MobileCraneInspectionReport(
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

    return MobileCraneUiState(mobileCraneInspectionReport = report)
}