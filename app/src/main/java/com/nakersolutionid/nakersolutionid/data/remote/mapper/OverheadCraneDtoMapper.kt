package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.remote.dto.overheadcrane.*
import com.nakersolutionid.nakersolutionid.domain.model.*

// =================================================================================================
//                                     Constants
// =================================================================================================

private object OverheadCraneBAPCategory {
    const val VISUAL_INSPECTION = "BAP_VISUAL_INSPECTION"
    const val TESTING = "BAP_TESTING"
    const val LOAD_TEST = "BAP_LOAD_TEST"
    const val NDT_TEST = "BAP_NDT_TEST"
}

/**
 * Maps an [InspectionWithDetailsDomain] to a [OverheadCraneBapRequest] DTO for API submission.
 */
fun InspectionWithDetailsDomain.toOverheadCraneBapRequest(): OverheadCraneBapRequest {
    val checkItemsByCat = this.checkItems.groupBy { it.category }

    fun getStatus(category: String, itemName: String, default: Boolean = false): Boolean {
        return checkItemsByCat[category]?.find { it.itemName == itemName }?.status ?: default
    }

    fun getValue(category: String, itemName: String, default: String = ""): String {
        return checkItemsByCat[category]?.find { it.itemName == itemName }?.result ?: default
    }

    val reportHeader = OverheadCraneBapReportHeader(
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        extraId = this.inspection.id,
        inspectionType = this.inspection.inspectionType.name,
        createdAt = this.inspection.createdAt ?: "",
        equipmentType = this.inspection.equipmentType
    )

    val generalData = OverheadCraneBapGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        ownerAddress = this.inspection.ownerAddress ?: "",
        userInCharge = this.inspection.addressUsageLocation ?: "",
        unitLocation = this.inspection.usageLocation ?: ""
    )

    val technicalData = OverheadCraneBapTechnicalData(
        brandType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturer = this.inspection.manufacturer?.name ?: "",
        locationAndYearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = this.inspection.serialNumber ?: "",
        capacityWorkingLoad = this.inspection.capacity ?: "",
        liftingSpeedMpm = this.inspection.speed ?: ""
    )

    val visualInspection = OverheadCraneBapVisualInspection(
        hasConstructionDefects = getStatus(OverheadCraneBAPCategory.VISUAL_INSPECTION, "hasConstructionDefects"),
        hookHasSafetyLatch = getStatus(OverheadCraneBAPCategory.VISUAL_INSPECTION, "hookHasSafetyLatch"),
        isEmergencyStopInstalled = getStatus(OverheadCraneBAPCategory.VISUAL_INSPECTION, "isEmergencyStopInstalled"),
        isWireropeGoodCondition = getStatus(OverheadCraneBAPCategory.VISUAL_INSPECTION, "isWireropeGoodCondition"),
        operatorHasK3License = getStatus(OverheadCraneBAPCategory.VISUAL_INSPECTION, "operatorHasK3License")
    )

    val testing = OverheadCraneBapTesting(
        functionTest = getStatus(OverheadCraneBAPCategory.TESTING, "functionTest"),
        loadTest = OverheadCraneBapLoadTest(
            loadTon = getValue(OverheadCraneBAPCategory.LOAD_TEST, "loadTon"),
            isAbleToLift = getStatus(OverheadCraneBAPCategory.LOAD_TEST, "isAbleToLift"),
            hasLoadDrop = getStatus(OverheadCraneBAPCategory.LOAD_TEST, "hasLoadDrop")
        ),
        ndtTest = OverheadCraneBapNdtTest(
            isNdtResultGood = getStatus(OverheadCraneBAPCategory.NDT_TEST, "isNdtResultGood"),
            hasCrackIndication = getStatus(OverheadCraneBAPCategory.NDT_TEST, "hasCrackIndication")
        )
    )

    return OverheadCraneBapRequest(
        laporanId = this.inspection.extraId,
        reportHeader = reportHeader,
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        testing = testing
    )
}

/**
 * Maps an [OverheadCraneBapReportData] DTO (from API response) to an [InspectionWithDetailsDomain].
 */
fun OverheadCraneBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.reportHeader.extraId
    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun addBooleanItem(category: String, itemName: String, status: Boolean) {
        checkItems.add(InspectionCheckItemDomain(0, inspectionId, category, itemName, status, null))
    }

    fun addStringItem(category: String, itemName: String, value: String?) {
        checkItems.add(InspectionCheckItemDomain(0, inspectionId, category, itemName, false, value ?: ""))
    }

    // --- Visual Inspection ---
    val v = this.visualInspection
    addBooleanItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "hasConstructionDefects", v.hasConstructionDefects)
    addBooleanItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "hookHasSafetyLatch", v.hookHasSafetyLatch)
    addBooleanItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "isEmergencyStopInstalled", v.isEmergencyStopInstalled)
    addBooleanItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "isWireropeGoodCondition", v.isWireropeGoodCondition)
    addBooleanItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "operatorHasK3License", v.operatorHasK3License)

    // --- Testing ---
    val t = this.testing
    addBooleanItem(OverheadCraneBAPCategory.TESTING, "functionTest", t.functionTest)
    addStringItem(OverheadCraneBAPCategory.LOAD_TEST, "loadTon", t.loadTest.loadTon.toString())
    addBooleanItem(OverheadCraneBAPCategory.LOAD_TEST, "isAbleToLift", t.loadTest.isAbleToLift)
    addBooleanItem(OverheadCraneBAPCategory.LOAD_TEST, "hasLoadDrop", t.loadTest.hasLoadDrop)
    addBooleanItem(OverheadCraneBAPCategory.NDT_TEST, "isNdtResultGood", t.ndtTest.isNdtResultGood)
    addBooleanItem(OverheadCraneBAPCategory.NDT_TEST, "hasCrackIndication", t.ndtTest.hasCrackIndication)

    // --- Main Inspection Domain ---
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Overhead_Crane,
        equipmentType = this.reportHeader.equipmentType,
        examinationType = this.reportHeader.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.unitLocation,
        addressUsageLocation = this.generalData.userInCharge,
        serialNumber = this.technicalData.serialNumberUnitNumber,
        capacity = this.technicalData.capacityWorkingLoad,
        speed = this.technicalData.liftingSpeedMpm,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.brandType,
            year = this.technicalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.reportHeader.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = emptyList()
    )
}

/**
 * Maps an [InspectionWithDetailsDomain] domain model to an [OverheadCraneReportRequest] DTO.
 * This function flattens the domain model into the structure required for the API request.
 */
fun InspectionWithDetailsDomain.toOverheadCraneReportRequest(): OverheadCraneReportRequest {
    val checkItemsByCat = this.checkItems.groupBy { it.category }

    fun getValue(category: String, itemName: String, default: String = ""): String {
        return checkItemsByCat[category]?.find { it.itemName == itemName }?.result ?: default
    }

    fun getResult(category: String, itemName: String): OverheadCraneVisualDetail {
        val item = checkItemsByCat[category]?.find { it.itemName == itemName }
        return OverheadCraneVisualDetail(status = item?.status ?: false, remarks = item?.result ?: "")
    }

    fun getMovementData(category: String, baseName: String): OverheadCraneMovementDetail {
        return OverheadCraneMovementDetail(
            hoisting = getValue(category, "${baseName}_hoisting"),
            traveling = getValue(category, "${baseName}_traveling"),
            traversing = getValue(category, "${baseName}_traversing")
        )
    }

    // --- General Data ---
    val generalData = OverheadCraneGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        ownerAddress = this.inspection.ownerAddress ?: "",
        userInCharge = this.inspection.addressUsageLocation ?: "",
        subcontractorPersonInCharge = getValue("general_data", "personInCharge"),
        unitLocation = this.inspection.usageLocation ?: "",
        equipmentType = this.inspection.equipmentType,
        manufacturer = this.inspection.manufacturer?.name ?: "",
        brandType = this.inspection.manufacturer?.brandOrType ?: "",
        yearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = this.inspection.serialNumber ?: "",
        capacityWorkingLoadKg = this.inspection.capacity ?: "",
        intendedUse = getValue("general_data", "intendedUse"),
        usagePermitNumber = this.inspection.permitNumber ?: "",
        operatorCertificate = getValue("general_data", "operatorCertificate"),
        technicalOrManualData = getValue("general_data", "technicalDataManual")
    )

    // --- Technical Data ---
    val techCategory = "technical_data"
    val technicalData = OverheadCraneTechnicalData(
        specifications = OverheadCraneTechnicalSpecifications(
            liftingHeight = getMovementData(techCategory, "spec_liftingHeight"),
            girderLength = getMovementData(techCategory, "spec_girderLength"),
            speedPerMin = getMovementData(techCategory, "spec_speed")
        ),
        driveMotor = OverheadCraneTechnicalDriveMotor(
            capacityTon = getMovementData(techCategory, "motor_capacity"),
            powerKw = getMovementData(techCategory, "motor_powerKw"),
            type = getMovementData(techCategory, "motor_type"),
            revolutionRpm = getMovementData(techCategory, "motor_rpm"),
            voltageV = getMovementData(techCategory, "motor_voltageV"),
            currentA = getMovementData(techCategory, "motor_currentA"),
            frequencyHz = getMovementData(techCategory, "motor_frequencyHz")
        ),
        startingResistor = OverheadCraneTechnicalStartingResistor(
            type = getMovementData(techCategory, "resistor_type"),
            voltageV = getMovementData(techCategory, "resistor_voltageV"),
            currentA = getMovementData(techCategory, "resistor_currentA")
        ),
        brake = OverheadCraneTechnicalBrake(
            kind = getMovementData(techCategory, "brake_type"),
            type = getMovementData(techCategory, "brake_model")
        ),
        controllerBrake = OverheadCraneTechnicalControllerBrake(
            kind = getMovementData(techCategory, "cBrake_type"),
            type = getMovementData(techCategory, "cBrake_model")
        ),
        hook = OverheadCraneTechnicalHook(
            type = getMovementData(techCategory, "hook_type"),
            capacity = getMovementData(techCategory, "hook_capacity"),
            material = getMovementData(techCategory, "hook_material")
        ),
        chain = OverheadCraneTechnicalChain(
            type = getMovementData(techCategory, "chain_type"),
            construction = getMovementData(techCategory, "chain_construction"),
            diameter = getMovementData(techCategory, "chain_diameter"),
            length = getMovementData(techCategory, "chain_length")
        )
    )

    // --- Visual Inspection ---
    val visualCategory = "visual_inspection"
    val visualInspection = OverheadCraneVisualInspection(
        foundation = OverheadCraneVisualFoundation(OverheadCraneVisualFoundationBolts(
            corrosion = getResult(visualCategory, "foundationAnchorBoltCorrosion"),
            cracks = getResult(visualCategory, "foundationAnchorBoltCracks"),
            deformation = getResult(visualCategory, "foundationAnchorBoltDeformation"),
            fastening = getResult(visualCategory, "foundationAnchorBoltFastening")
        )),
        columnFrame = OverheadCraneVisualColumnFrame(
            corrosion = getResult(visualCategory, "columnFrameCorrosion"),
            cracks = getResult(visualCategory, "columnFrameCracks"),
            deformation = getResult(visualCategory, "columnFrameDeformation"),
            fastening = getResult(visualCategory, "columnFrameFastening"),
            crossBracing = getResult(visualCategory, "columnFrameCrossBracing"),
            diagonalBracing = getResult(visualCategory, "columnFrameDiagonalBracing")
        ),
        stairs = OverheadCraneVisualStairs(
            corrosion = getResult(visualCategory, "ladderCorrosion"),
            cracks = getResult(visualCategory, "ladderCracks"),
            deformation = getResult(visualCategory, "ladderDeformation"),
            fastening = getResult(visualCategory, "ladderFastening")
        ),
        platform = OverheadCraneVisualPlatform(
            corrosion = getResult(visualCategory, "workPlatformCorrosion"),
            cracks = getResult(visualCategory, "workPlatformCracks"),
            deformation = getResult(visualCategory, "workPlatformDeformation"),
            fastening = getResult(visualCategory, "workPlatformFastening")
        ),
        railSupportBeam = OverheadCraneVisualRailSupportBeam(
            corrosion = getResult(visualCategory, "railMountingBeamCorrosion"),
            cracks = getResult(visualCategory, "railMountingBeamCracks"),
            deformation = getResult(visualCategory, "railMountingBeamDeformation"),
            fastening = getResult(visualCategory, "railMountingBeamFastening")
        ),
        travelingRail = OverheadCraneVisualTravelingRail(
            corrosion = getResult(visualCategory, "travelingRailCorrosion"),
            cracks = getResult(visualCategory, "travelingRailCracks"),
            joints = getResult(visualCategory, "travelingRailJoint"),
            straightness = getResult(visualCategory, "travelingRailStraightness"),
            interRailStraightness = getResult(visualCategory, "travelingRailAlignmentBetweenRails"),
            interRailEvenness = getResult(visualCategory, "travelingRailEvennessBetweenRails"),
            jointGap = getResult(visualCategory, "travelingRailGapBetweenRailJoints"),
            fasteners = getResult(visualCategory, "travelingRailFastener"),
            stopper = getResult(visualCategory, "travelingRailStopper")
        ),
        traversingRail = OverheadCraneVisualTraversingRail(
            corrosion = getResult(visualCategory, "traversingRailCorrosion"),
            cracks = getResult(visualCategory, "traversingRailCracks"),
            joints = getResult(visualCategory, "traversingRailJoint"),
            straightness = getResult(visualCategory, "traversingRailStraightness"),
            interRailStraightness = getResult(visualCategory, "traversingRailAlignmentBetweenRails"),
            interRailEvenness = getResult(visualCategory, "traversingRailEvennessBetweenRails"),
            jointGap = getResult(visualCategory, "traversingRailGapBetweenRailJoints"),
            fasteners = getResult(visualCategory, "traversingRailFastener"),
            stopper = getResult(visualCategory, "traversingRailStopper")
        ),
        girder = OverheadCraneVisualGirder(
            corrosion = getResult(visualCategory, "girderCorrosion"),
            cracks = getResult(visualCategory, "girderCracks"),
            camber = getResult(visualCategory, "girderCamber"),
            joints = getResult(visualCategory, "girderJoint"),
            endJoints = getResult(visualCategory, "girderEndJoint"),
            truckMount = getResult(visualCategory, "girderTruckMountOnGirder")
        ),
        travelingGearbox = OverheadCraneVisualTravelingGearbox(
            corrosion = getResult(visualCategory, "travelingGearboxGirderCorrosion"),
            cracks = getResult(visualCategory, "travelingGearboxGirderCracks"),
            lubricant = getResult(visualCategory, "travelingGearboxGirderLubricatingOil"),
            oilSeal = getResult(visualCategory, "travelingGearboxGirderOilSeal")
        ),
        driveWheel = OverheadCraneVisualDriveWheel(
            wear = getResult(visualCategory, "driveWheelWear"),
            cracks = getResult(visualCategory, "driveWheelCracks"),
            deformation = getResult(visualCategory, "driveWheelDeformation"),
            flange = getResult(visualCategory, "driveWheelFlangeCondition"),
            chain = getResult(visualCategory, "driveWheelChainCondition")
        ),
        idleWheel = OverheadCraneVisualIdleWheel(
            security = getResult(visualCategory, "idleWheelSecurity"),
            cracks = getResult(visualCategory, "idleWheelCracks"),
            deformation = getResult(visualCategory, "idleWheelDeformation"),
            flange = getResult(visualCategory, "idleWheelFlangeCondition")
        ),
        wheelConnector = OverheadCraneVisualWheelConnector(
            straightness = getResult(visualCategory, "wheelConnectorBogieAxleStraightness"),
            crossJoint = getResult(visualCategory, "wheelConnectorBogieAxleCrossJoint"),
            lubrication = getResult(visualCategory, "wheelConnectorBogieAxleLubrication")
        ),
        girderBumper = OverheadCraneVisualGirderBumper(
            condition = getResult(visualCategory, "stopperBumperOnGirderCondition"),
            reinforcement = getResult(visualCategory, "stopperBumperOnGirderReinforcement")
        ),
        trolleyGearbox = OverheadCraneVisualTrolleyGearbox(
            fastening = getResult(visualCategory, "traversingGearboxTrolleyCarrierFastening"),
            corrosion = getResult(visualCategory, "traversingGearboxTrolleyCarrierCorrosion"),
            cracks = getResult(visualCategory, "traversingGearboxTrolleyCarrierCracks"),
            lubricant = getResult(visualCategory, "traversingGearboxTrolleyCarrierLubricatingOil"),
            oilSeal = getResult(visualCategory, "traversingGearboxTrolleyCarrierOilSeal")
        ),
        trolleyDriveWheel = OverheadCraneVisualTrolleyDriveWheel(
            wear = getResult(visualCategory, "driveWheelOnTrolleyWear"),
            cracks = getResult(visualCategory, "driveWheelOnTrolleyCracks"),
            deformation = getResult(visualCategory, "driveWheelOnTrolleyDeformation"),
            flange = getResult(visualCategory, "driveWheelOnTrolleyFlangeCondition"),
            chain = getResult(visualCategory, "driveWheelOnTrolleyChainCondition")
        ),
        trolleyIdleWheel = OverheadCraneVisualTrolleyIdleWheel(
            wear = getResult(visualCategory, "idleWheelOnTrolleyWear"),
            cracks = getResult(visualCategory, "idleWheelOnTrolleyCracks"),
            deformation = getResult(visualCategory, "idleWheelOnTrolleyDeformation"),
            flange = getResult(visualCategory, "idleWheelOnTrolleyFlangeCondition")
        ),
        trolleyWheelConnector = OverheadCraneVisualTrolleyWheelConnector(
            straightness = getResult(visualCategory, "wheelConnectorBogieAxleOnTrolleyStraightness"),
            crossJoint = getResult(visualCategory, "wheelConnectorBogieAxleOnTrolleyCrossJoint"),
            lubrication = getResult(visualCategory, "wheelConnectorBogieAxleOnTrolleyLubrication")
        ),
        trolleyBumper = OverheadCraneVisualTrolleyBumper(
            condition = getResult(visualCategory, "stopperBumperOnGirderOnTrolleyCondition"),
            reinforcement = getResult(visualCategory, "stopperBumperOnGirderOnTrolleyReinforcement")
        ),
        drum = OverheadCraneVisualDrum(
            groove = getResult(visualCategory, "windingDrumGroove"),
            grooveLip = getResult(visualCategory, "windingDrumGrooveFlange"),
            flanges = getResult(visualCategory, "windingDrumFlanges")
        ),
        brakeVisual = OverheadCraneVisualBrakeVisual(
            wear = getResult(visualCategory, "brakeWear"),
            adjustment = getResult(visualCategory, "brakeAdjustment")
        ),
        hoistGearBox = OverheadCraneVisualHoistGearBox(
            lubrication = getResult(visualCategory, "hoistGearboxLubrication"),
            oilSeal = getResult(visualCategory, "hoistGearboxOilSeal")
        ),
        pulleyChainSprocket = OverheadCraneVisualPulleyChainSprocket(
            pulleyGroove = getResult(visualCategory, "pulleyDiscChainSprocketPulleyGroove"),
            pulleyLip = getResult(visualCategory, "pulleyDiscChainSprocketPulleyFlange"),
            pulleyPin = getResult(visualCategory, "pulleyDiscChainSprocketPulleyPin"),
            pulleyBearing = getResult(visualCategory, "pulleyDiscChainSprocketBearing"),
            pulleyGuard = getResult(visualCategory, "pulleyDiscChainSprocketPulleyGuard"),
            ropeChainGuard = getResult(visualCategory, "pulleyDiscChainSprocketWireRopeChainGuard")
        ),
        mainHook = OverheadCraneVisualMainHook(
            wear = getResult(visualCategory, "mainHookWear"),
            throatOpening = getResult(visualCategory, "mainHookThroatOpening"),
            swivel = getResult(visualCategory, "mainHookSwivelNutAndBearing"),
            trunnion = getResult(visualCategory, "mainHookTrunnion")
        ),
        auxHook = OverheadCraneVisualAuxHook(
            wear = getResult(visualCategory, "auxiliaryHookWear"),
            throatOpening = getResult(visualCategory, "auxiliaryHookThroatOpening"),
            swivel = getResult(visualCategory, "auxiliaryHookSwivelNutAndBearing"),
            trunnion = getResult(visualCategory, "auxiliaryHookTrunnion")
        ),
        mainWireRope = OverheadCraneVisualMainWireRope(
            corrosion = getResult(visualCategory, "mainWireRopeCorrosion"),
            wear = getResult(visualCategory, "mainWireRopeWear"),
            broken = getResult(visualCategory, "mainWireRopeBroken"),
            deformation = getResult(visualCategory, "mainWireRopeDeformation")
        ),
        auxWireRope = OverheadCraneVisualAuxWireRope(
            corrosion = getResult(visualCategory, "auxiliaryWireRopeCorrosion"),
            wear = getResult(visualCategory, "auxiliaryWireRopeWear"),
            broken = getResult(visualCategory, "auxiliaryWireRopeBroken"),
            deformation = getResult(visualCategory, "auxiliaryWireRopeDeformation")
        ),
        mainChain = OverheadCraneVisualMainChain(
            corrosion = getResult(visualCategory, "mainChainCorrosion"),
            wear = getResult(visualCategory, "mainChainWear"),
            cracksBroken = getResult(visualCategory, "mainChainCrackedBroken"),
            deformation = getResult(visualCategory, "mainChainDeformation")
        ),
        auxChain = OverheadCraneVisualAuxChain(
            corrosion = getResult(visualCategory, "auxiliaryChainCorrosion"),
            wear = getResult(visualCategory, "auxiliaryChainWear"),
            cracksBroken = getResult(visualCategory, "auxiliaryChainCrackedBroken"),
            deformation = getResult(visualCategory, "auxiliaryChainDeformation")
        ),
        limitSwitch = OverheadCraneVisualLimitSwitch(
            longTraveling = getResult(visualCategory, "limitSwitchLsLongTraveling"),
            crossTraveling = getResult(visualCategory, "limitSwitchLsCrossTraveling"),
            lifting = getResult(visualCategory, "limitSwitchLsHoisting")
        ),
        operatorCabin = OverheadCraneVisualOperatorCabin(
            safetyStairs = getResult(visualCategory, "operatorRoomCabinSafetyLadder"),
            door = getResult(visualCategory, "operatorRoomCabinDoor"),
            window = getResult(visualCategory, "operatorRoomCabinWindow"),
            fanAc = getResult(visualCategory, "operatorRoomCabinFanAC"),
            controlLevers = getResult(visualCategory, "operatorRoomCabinControlLeversButtons"),
            pendantControl = getResult(visualCategory, "operatorRoomCabinPendantControl"),
            lighting = getResult(visualCategory, "operatorRoomCabinLighting"),
            horn = getResult(visualCategory, "operatorRoomCabinHorn"),
            fuse = getResult(visualCategory, "operatorRoomCabinFuseProtection"),
            commTool = getResult(visualCategory, "operatorRoomCabinCommunicationDevice"),
            fireExtinguisher = getResult(visualCategory, "operatorRoomCabinFireExtinguisher"),
            operatingSigns = getResult(visualCategory, "operatorRoomCabinOperatingSigns"),
            masterSwitch = getResult(visualCategory, "operatorRoomCabinIgnitionKeyMasterSwitch")
        ),
        electricalComponents = OverheadCraneVisualElectricalComponents(
            panelConnector = getResult(visualCategory, "electricalComponentPanelConnectorConductor"),
            conductorGuard = getResult(visualCategory, "electricalComponentConductorProtection"),
            motorSafetySystem = getResult(visualCategory, "electricalComponentMotorInstallationSafetySystem"),
            groundingSystem = getResult(visualCategory, "electricalComponentGroundingSystem"),
            installation = getResult(visualCategory, "electricalComponentInstallation")
        )
    )

    // --- NDE ---
    val ndeChainItems = checkItemsByCat.keys.filter { it.startsWith("nde_chain_item_") }.mapNotNull { cat ->
        OverheadCraneNdeChainItem(
            chainLocation = getValue(cat, "name"),
            specDimension = getValue(cat, "specificationMm"),
            resultDimension = getValue(cat, "measurementMm"),
            extendLengthMax = getValue(cat, "lengthIncrease"),
            wearMax = getValue(cat, "wear"),
            safetyFactor = getValue(cat, "safetyFactor"),
            defectAda = getResult(cat, "finding").status,
            defectTidakAda = !getResult(cat, "finding").status,
            description = getResult(cat, "finding").remarks
        )
    }

    val ndeMainHook = OverheadCraneNdeMainHook(
        method = getValue("nde_mainHook", "method"),
        measurements = OverheadCraneNdeHookMeasurements(
            A = getValue("nde_mainHook", "result_a"), B = getValue("nde_mainHook", "result_b"),
            C = getValue("nde_mainHook", "result_c"), D = getValue("nde_mainHook", "result_d"),
            E = getValue("nde_mainHook", "result_e"), F = getValue("nde_mainHook", "result_f"),
            G = getValue("nde_mainHook", "result_g"), H = getValue("nde_mainHook", "result_h")
        ),
        tolerances = OverheadCraneNdeHookTolerances(
            A = getValue("nde_mainHook", "tolerance_a"), B = getValue("nde_mainHook", "tolerance_b"),
            C = getValue("nde_mainHook", "tolerance_c"), D = getValue("nde_mainHook", "tolerance_d"),
            E = getValue("nde_mainHook", "tolerance_e"), F = getValue("nde_mainHook", "tolerance_f"),
            G = getValue("nde_mainHook", "tolerance_g"), H = getValue("nde_mainHook", "tolerance_h")
        ),
        result = getResult("nde_mainHook", "result_finding").remarks
    )

    val nonDestructiveExamination = OverheadCraneNonDestructiveExamination(
        chain = OverheadCraneNdeChain(method = getValue("nde_chain", "method"), items = ndeChainItems),
        mainHook = ndeMainHook
    )

    // --- Testing ---
    val dynWithoutLoadCat = "testing_dynamic_without_load"
    val dynWithLoadCat = "testing_dynamic_with_load"
    val staticTestCat = "testing_static"

    val testing = OverheadCraneTesting(
        dynamicTest = OverheadCraneDynamicTest(
            withoutLoad = listOf(
                OverheadCraneDynamicTestWithoutLoadItem("Traveling", getValue(dynWithoutLoadCat, "traveling_shouldBe"), getValue(dynWithoutLoadCat, "traveling_testedMeasured"), getValue(dynWithoutLoadCat, "traveling_remarks")),
                OverheadCraneDynamicTestWithoutLoadItem("Traversing", getValue(dynWithoutLoadCat, "traversing_shouldBe"), getValue(dynWithoutLoadCat, "traversing_testedMeasured"), getValue(dynWithoutLoadCat, "traversing_remarks")),
                OverheadCraneDynamicTestWithoutLoadItem("Hoisting", getValue(dynWithoutLoadCat, "hoisting_shouldBe"), getValue(dynWithoutLoadCat, "hoisting_testedMeasured"), getValue(dynWithoutLoadCat, "hoisting_remarks")),
                OverheadCraneDynamicTestWithoutLoadItem("Safety Latch", getValue(dynWithoutLoadCat, "safetyLatch_shouldBe"), getValue(dynWithoutLoadCat, "safetyLatch_testedMeasured"), getValue(dynWithoutLoadCat, "safetyLatch_remarks")),
                OverheadCraneDynamicTestWithoutLoadItem("Brake Switch", getValue(dynWithoutLoadCat, "brakeSwitch_shouldBe"), getValue(dynWithoutLoadCat, "brakeSwitch_testedMeasured"), getValue(dynWithoutLoadCat, "brakeSwitch_remarks")),
                OverheadCraneDynamicTestWithoutLoadItem("Brake Locking Device", getValue(dynWithoutLoadCat, "brakeLockingDevice_shouldBe"), getValue(dynWithoutLoadCat, "brakeLockingDevice_testedMeasured"), getValue(dynWithoutLoadCat, "brakeLockingDevice_remarks")),
                OverheadCraneDynamicTestWithoutLoadItem("Electrical Installation", getValue(dynWithoutLoadCat, "electricalInstallation_shouldBe"), getValue(dynWithoutLoadCat, "electricalInstallation_testedMeasured"), getValue(dynWithoutLoadCat, "electricalInstallation_remarks"))
            ),
            withLoad = listOf(
                OverheadCraneDynamicTestWithLoadItem("No Load", getValue(dynWithLoadCat, "noLoad_hoist"), getValue(dynWithLoadCat, "noLoad_traversing"), getValue(dynWithLoadCat, "noLoad_travelling"), getValue(dynWithLoadCat, "noLoad_brakeSystem"), getValue(dynWithLoadCat, "noLoad_remarks")),
                OverheadCraneDynamicTestWithLoadItem("25% SWL", getValue(dynWithLoadCat, "swl25_hoist"), getValue(dynWithLoadCat, "swl25_traversing"), getValue(dynWithLoadCat, "swl25_travelling"), getValue(dynWithLoadCat, "swl25_brakeSystem"), getValue(dynWithLoadCat, "swl25_remarks")),
                OverheadCraneDynamicTestWithLoadItem("50% SWL", getValue(dynWithLoadCat, "swl50_hoist"), getValue(dynWithLoadCat, "swl50_traversing"), getValue(dynWithLoadCat, "swl50_travelling"), getValue(dynWithLoadCat, "swl50_brakeSystem"), getValue(dynWithLoadCat, "swl50_remarks")),
                OverheadCraneDynamicTestWithLoadItem("75% SWL", getValue(dynWithLoadCat, "swl75_hoist"), getValue(dynWithLoadCat, "swl75_traversing"), getValue(dynWithLoadCat, "swl75_travelling"), getValue(dynWithLoadCat, "swl75_brakeSystem"), getValue(dynWithLoadCat, "swl75_remarks")),
                OverheadCraneDynamicTestWithLoadItem("100% SWL", getValue(dynWithLoadCat, "swl100_hoist"), getValue(dynWithLoadCat, "swl100_traversing"), getValue(dynWithLoadCat, "swl100_travelling"), getValue(dynWithLoadCat, "swl100_brakeSystem"), getValue(dynWithLoadCat, "swl100_remarks"))
            )
        ),
        staticTest = OverheadCraneStaticTest(
            testLoad = getValue(staticTestCat, "load"),
            deflection = OverheadCraneStaticTestDeflection(
                singleGirder = OverheadCraneStaticTestDeflectionDetail(getValue(staticTestCat, "deflection_single_measurement"), getValue(staticTestCat, "deflection_single_description")),
                doubleGirder = OverheadCraneStaticTestDeflectionDetail(getValue(staticTestCat, "deflection_double_measurement"), getValue(staticTestCat, "deflection_double_description"))
            ),
            singleGirder = OverheadCraneStaticTestGirderDetail(
                designMm = getValue(staticTestCat, "deflection_single_design_mm"),
                spanMm = getValue(staticTestCat, "deflection_single_span_mm"),
                result = getResult(staticTestCat, "deflection_single_result").status
            ),
            doubleGirder = OverheadCraneStaticTestGirderDetail(
                designMm = getValue(staticTestCat, "deflection_double_design_mm"),
                spanMm = getValue(staticTestCat, "deflection_double_span_mm"),
                result = getResult(staticTestCat, "deflection_double_result").status
            ),
            notes = getValue(staticTestCat, "notes")
        )
    )

    // --- Conclusion and Recommendations ---
    val conclusion = this.findings.filter { it.type == FindingType.FINDING }.joinToString("\n") { it.description }
    val recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.joinToString("\n") { it.description }

    return OverheadCraneReportRequest(
        examinationType = this.inspection.examinationType,
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        inspectionDate = this.inspection.reportDate ?: "",
        extraId = this.inspection.id,
        createdAt = this.inspection.createdAt ?: "",
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        nonDestructiveExamination = nonDestructiveExamination,
        testing = testing,
        conclusion = conclusion,
        recommendations = recommendations
    )
}

/**
 * Maps an [OverheadCraneReportData] DTO to an [InspectionWithDetailsDomain] domain model.
 */
fun OverheadCraneReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId
    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun addStringItem(category: String, itemName: String, value: String?) {
        checkItems.add(InspectionCheckItemDomain(0, inspectionId, category, itemName, false, value ?: ""))
    }

    fun addResultItem(category: String, itemName: String, detail: OverheadCraneVisualDetail) {
        checkItems.add(InspectionCheckItemDomain(0, inspectionId, category, itemName, detail.status, detail.remarks))
    }

    fun addMovementItems(category: String, baseName: String, data: OverheadCraneMovementDetail) {
        addStringItem(category, "${baseName}_hoisting", data.hoisting)
        addStringItem(category, "${baseName}_traveling", data.traveling)
        addStringItem(category, "${baseName}_traversing", data.traversing)
    }

    // --- General Data ---
    addStringItem("general_data", "personInCharge", this.generalData.subcontractorPersonInCharge)
    addStringItem("general_data", "intendedUse", this.generalData.intendedUse)
    addStringItem("general_data", "operatorCertificate", this.generalData.operatorCertificate)
    addStringItem("general_data", "technicalDataManual", this.generalData.technicalOrManualData)


    // --- Technical Data ---
    val techCategory = "technical_data"
    addMovementItems(techCategory, "spec_liftingHeight", this.technicalData.specifications.liftingHeight)
    addMovementItems(techCategory, "spec_girderLength", this.technicalData.specifications.girderLength)
    addMovementItems(techCategory, "spec_speed", this.technicalData.specifications.speedPerMin)
    addMovementItems(techCategory, "motor_capacity", this.technicalData.driveMotor.capacityTon)
    addMovementItems(techCategory, "motor_powerKw", this.technicalData.driveMotor.powerKw)
    addMovementItems(techCategory, "motor_type", this.technicalData.driveMotor.type)
    addMovementItems(techCategory, "motor_rpm", this.technicalData.driveMotor.revolutionRpm)
    addMovementItems(techCategory, "motor_voltageV", this.technicalData.driveMotor.voltageV)
    addMovementItems(techCategory, "motor_currentA", this.technicalData.driveMotor.currentA)
    addMovementItems(techCategory, "motor_frequencyHz", this.technicalData.driveMotor.frequencyHz)
    addMovementItems(techCategory, "resistor_type", this.technicalData.startingResistor.type)
    addMovementItems(techCategory, "resistor_voltageV", this.technicalData.startingResistor.voltageV)
    addMovementItems(techCategory, "resistor_currentA", this.technicalData.startingResistor.currentA)
    addMovementItems(techCategory, "brake_type", this.technicalData.brake.kind)
    addMovementItems(techCategory, "brake_model", this.technicalData.brake.type)
    addMovementItems(techCategory, "cBrake_type", this.technicalData.controllerBrake.kind)
    addMovementItems(techCategory, "cBrake_model", this.technicalData.controllerBrake.type)
    addMovementItems(techCategory, "hook_type", this.technicalData.hook.type)
    addMovementItems(techCategory, "hook_capacity", this.technicalData.hook.capacity)
    addMovementItems(techCategory, "hook_material", this.technicalData.hook.material)
    addMovementItems(techCategory, "chain_type", this.technicalData.chain.type)
    addMovementItems(techCategory, "chain_construction", this.technicalData.chain.construction)
    addMovementItems(techCategory, "chain_diameter", this.technicalData.chain.diameter)
    addMovementItems(techCategory, "chain_length", this.technicalData.chain.length)

    // --- Visual Inspection ---
    val visualCategory = "visual_inspection"
    val v = this.visualInspection
    addResultItem(visualCategory, "foundationAnchorBoltCorrosion", v.foundation.bolts.corrosion)
    addResultItem(visualCategory, "foundationAnchorBoltCracks", v.foundation.bolts.cracks)
    addResultItem(visualCategory, "foundationAnchorBoltDeformation", v.foundation.bolts.deformation)
    addResultItem(visualCategory, "foundationAnchorBoltFastening", v.foundation.bolts.fastening)
    addResultItem(visualCategory, "columnFrameCorrosion", v.columnFrame.corrosion)
    addResultItem(visualCategory, "columnFrameCracks", v.columnFrame.cracks)
    addResultItem(visualCategory, "columnFrameDeformation", v.columnFrame.deformation)
    addResultItem(visualCategory, "columnFrameFastening", v.columnFrame.fastening)
    addResultItem(visualCategory, "columnFrameCrossBracing", v.columnFrame.crossBracing)
    addResultItem(visualCategory, "columnFrameDiagonalBracing", v.columnFrame.diagonalBracing)
    addResultItem(visualCategory, "ladderCorrosion", v.stairs.corrosion)
    addResultItem(visualCategory, "ladderCracks", v.stairs.cracks)
    addResultItem(visualCategory, "ladderDeformation", v.stairs.deformation)
    addResultItem(visualCategory, "ladderFastening", v.stairs.fastening)
    addResultItem(visualCategory, "workPlatformCorrosion", v.platform.corrosion)
    addResultItem(visualCategory, "workPlatformCracks", v.platform.cracks)
    addResultItem(visualCategory, "workPlatformDeformation", v.platform.deformation)
    addResultItem(visualCategory, "workPlatformFastening", v.platform.fastening)
    addResultItem(visualCategory, "railMountingBeamCorrosion", v.railSupportBeam.corrosion)
    addResultItem(visualCategory, "railMountingBeamCracks", v.railSupportBeam.cracks)
    addResultItem(visualCategory, "railMountingBeamDeformation", v.railSupportBeam.deformation)
    addResultItem(visualCategory, "railMountingBeamFastening", v.railSupportBeam.fastening)
    addResultItem(visualCategory, "travelingRailCorrosion", v.travelingRail.corrosion)
    addResultItem(visualCategory, "travelingRailCracks", v.travelingRail.cracks)
    addResultItem(visualCategory, "travelingRailJoint", v.travelingRail.joints)
    addResultItem(visualCategory, "travelingRailStraightness", v.travelingRail.straightness)
    addResultItem(visualCategory, "travelingRailAlignmentBetweenRails", v.travelingRail.interRailStraightness)
    addResultItem(visualCategory, "travelingRailEvennessBetweenRails", v.travelingRail.interRailEvenness)
    addResultItem(visualCategory, "travelingRailGapBetweenRailJoints", v.travelingRail.jointGap)
    addResultItem(visualCategory, "travelingRailFastener", v.travelingRail.fasteners)
    addResultItem(visualCategory, "travelingRailStopper", v.travelingRail.stopper)
    addResultItem(visualCategory, "traversingRailCorrosion", v.traversingRail.corrosion)
    addResultItem(visualCategory, "traversingRailCracks", v.traversingRail.cracks)
    addResultItem(visualCategory, "traversingRailJoint", v.traversingRail.joints)
    addResultItem(visualCategory, "traversingRailStraightness", v.traversingRail.straightness)
    addResultItem(visualCategory, "traversingRailAlignmentBetweenRails", v.traversingRail.interRailStraightness)
    addResultItem(visualCategory, "traversingRailEvennessBetweenRails", v.traversingRail.interRailEvenness)
    addResultItem(visualCategory, "traversingRailGapBetweenRailJoints", v.traversingRail.jointGap)
    addResultItem(visualCategory, "traversingRailFastener", v.traversingRail.fasteners)
    addResultItem(visualCategory, "traversingRailStopper", v.traversingRail.stopper)
    addResultItem(visualCategory, "girderCorrosion", v.girder.corrosion)
    addResultItem(visualCategory, "girderCracks", v.girder.cracks)
    addResultItem(visualCategory, "girderCamber", v.girder.camber)
    addResultItem(visualCategory, "girderJoint", v.girder.joints)
    addResultItem(visualCategory, "girderEndJoint", v.girder.endJoints)
    addResultItem(visualCategory, "girderTruckMountOnGirder", v.girder.truckMount)
    addResultItem(visualCategory, "travelingGearboxGirderCorrosion", v.travelingGearbox.corrosion)
    addResultItem(visualCategory, "travelingGearboxGirderCracks", v.travelingGearbox.cracks)
    addResultItem(visualCategory, "travelingGearboxGirderLubricatingOil", v.travelingGearbox.lubricant)
    addResultItem(visualCategory, "travelingGearboxGirderOilSeal", v.travelingGearbox.oilSeal)
    addResultItem(visualCategory, "driveWheelWear", v.driveWheel.wear)
    addResultItem(visualCategory, "driveWheelCracks", v.driveWheel.cracks)
    addResultItem(visualCategory, "driveWheelDeformation", v.driveWheel.deformation)
    addResultItem(visualCategory, "driveWheelFlangeCondition", v.driveWheel.flange)
    addResultItem(visualCategory, "driveWheelChainCondition", v.driveWheel.chain)
    addResultItem(visualCategory, "idleWheelSecurity", v.idleWheel.security)
    addResultItem(visualCategory, "idleWheelCracks", v.idleWheel.cracks)
    addResultItem(visualCategory, "idleWheelDeformation", v.idleWheel.deformation)
    addResultItem(visualCategory, "idleWheelFlangeCondition", v.idleWheel.flange)
    addResultItem(visualCategory, "wheelConnectorBogieAxleStraightness", v.wheelConnector.straightness)
    addResultItem(visualCategory, "wheelConnectorBogieAxleCrossJoint", v.wheelConnector.crossJoint)
    addResultItem(visualCategory, "wheelConnectorBogieAxleLubrication", v.wheelConnector.lubrication)
    addResultItem(visualCategory, "stopperBumperOnGirderCondition", v.girderBumper.condition)
    addResultItem(visualCategory, "stopperBumperOnGirderReinforcement", v.girderBumper.reinforcement)
    addResultItem(visualCategory, "traversingGearboxTrolleyCarrierFastening", v.trolleyGearbox.fastening)
    addResultItem(visualCategory, "traversingGearboxTrolleyCarrierCorrosion", v.trolleyGearbox.corrosion)
    addResultItem(visualCategory, "traversingGearboxTrolleyCarrierCracks", v.trolleyGearbox.cracks)
    addResultItem(visualCategory, "traversingGearboxTrolleyCarrierLubricatingOil", v.trolleyGearbox.lubricant)
    addResultItem(visualCategory, "traversingGearboxTrolleyCarrierOilSeal", v.trolleyGearbox.oilSeal)
    addResultItem(visualCategory, "driveWheelOnTrolleyWear", v.trolleyDriveWheel.wear)
    addResultItem(visualCategory, "driveWheelOnTrolleyCracks", v.trolleyDriveWheel.cracks)
    addResultItem(visualCategory, "driveWheelOnTrolleyDeformation", v.trolleyDriveWheel.deformation)
    addResultItem(visualCategory, "driveWheelOnTrolleyFlangeCondition", v.trolleyDriveWheel.flange)
    addResultItem(visualCategory, "driveWheelOnTrolleyChainCondition", v.trolleyDriveWheel.chain)
    addResultItem(visualCategory, "idleWheelOnTrolleyWear", v.trolleyIdleWheel.wear)
    addResultItem(visualCategory, "idleWheelOnTrolleyCracks", v.trolleyIdleWheel.cracks)
    addResultItem(visualCategory, "idleWheelOnTrolleyDeformation", v.trolleyIdleWheel.deformation)
    addResultItem(visualCategory, "idleWheelOnTrolleyFlangeCondition", v.trolleyIdleWheel.flange)
    addResultItem(visualCategory, "wheelConnectorBogieAxleOnTrolleyStraightness", v.trolleyWheelConnector.straightness)
    addResultItem(visualCategory, "wheelConnectorBogieAxleOnTrolleyCrossJoint", v.trolleyWheelConnector.crossJoint)
    addResultItem(visualCategory, "wheelConnectorBogieAxleOnTrolleyLubrication", v.trolleyWheelConnector.lubrication)
    addResultItem(visualCategory, "stopperBumperOnGirderOnTrolleyCondition", v.trolleyBumper.condition)
    addResultItem(visualCategory, "stopperBumperOnGirderOnTrolleyReinforcement", v.trolleyBumper.reinforcement)
    addResultItem(visualCategory, "windingDrumGroove", v.drum.groove)
    addResultItem(visualCategory, "windingDrumGrooveFlange", v.drum.grooveLip)
    addResultItem(visualCategory, "windingDrumFlanges", v.drum.flanges)
    addResultItem(visualCategory, "brakeWear", v.brakeVisual.wear)
    addResultItem(visualCategory, "brakeAdjustment", v.brakeVisual.adjustment)
    addResultItem(visualCategory, "hoistGearboxLubrication", v.hoistGearBox.lubrication)
    addResultItem(visualCategory, "hoistGearboxOilSeal", v.hoistGearBox.oilSeal)
    addResultItem(visualCategory, "pulleyDiscChainSprocketPulleyGroove", v.pulleyChainSprocket.pulleyGroove)
    addResultItem(visualCategory, "pulleyDiscChainSprocketPulleyFlange", v.pulleyChainSprocket.pulleyLip)
    addResultItem(visualCategory, "pulleyDiscChainSprocketPulleyPin", v.pulleyChainSprocket.pulleyPin)
    addResultItem(visualCategory, "pulleyDiscChainSprocketBearing", v.pulleyChainSprocket.pulleyBearing)
    addResultItem(visualCategory, "pulleyDiscChainSprocketPulleyGuard", v.pulleyChainSprocket.pulleyGuard)
    addResultItem(visualCategory, "pulleyDiscChainSprocketWireRopeChainGuard", v.pulleyChainSprocket.ropeChainGuard)
    addResultItem(visualCategory, "mainHookWear", v.mainHook.wear)
    addResultItem(visualCategory, "mainHookThroatOpening", v.mainHook.throatOpening)
    addResultItem(visualCategory, "mainHookSwivelNutAndBearing", v.mainHook.swivel)
    addResultItem(visualCategory, "mainHookTrunnion", v.mainHook.trunnion)
    addResultItem(visualCategory, "auxiliaryHookWear", v.auxHook.wear)
    addResultItem(visualCategory, "auxiliaryHookThroatOpening", v.auxHook.throatOpening)
    addResultItem(visualCategory, "auxiliaryHookSwivelNutAndBearing", v.auxHook.swivel)
    addResultItem(visualCategory, "auxiliaryHookTrunnion", v.auxHook.trunnion)
    addResultItem(visualCategory, "mainWireRopeCorrosion", v.mainWireRope.corrosion)
    addResultItem(visualCategory, "mainWireRopeWear", v.mainWireRope.wear)
    addResultItem(visualCategory, "mainWireRopeBroken", v.mainWireRope.broken)
    addResultItem(visualCategory, "mainWireRopeDeformation", v.mainWireRope.deformation)
    addResultItem(visualCategory, "auxiliaryWireRopeCorrosion", v.auxWireRope.corrosion)
    addResultItem(visualCategory, "auxiliaryWireRopeWear", v.auxWireRope.wear)
    addResultItem(visualCategory, "auxiliaryWireRopeBroken", v.auxWireRope.broken)
    addResultItem(visualCategory, "auxiliaryWireRopeDeformation", v.auxWireRope.deformation)
    addResultItem(visualCategory, "mainChainCorrosion", v.mainChain.corrosion)
    addResultItem(visualCategory, "mainChainWear", v.mainChain.wear)
    addResultItem(visualCategory, "mainChainCrackedBroken", v.mainChain.cracksBroken)
    addResultItem(visualCategory, "mainChainDeformation", v.mainChain.deformation)
    addResultItem(visualCategory, "auxiliaryChainCorrosion", v.auxChain.corrosion)
    addResultItem(visualCategory, "auxiliaryChainWear", v.auxChain.wear)
    addResultItem(visualCategory, "auxiliaryChainCrackedBroken", v.auxChain.cracksBroken)
    addResultItem(visualCategory, "auxiliaryChainDeformation", v.auxChain.deformation)
    addResultItem(visualCategory, "limitSwitchLsLongTraveling", v.limitSwitch.longTraveling)
    addResultItem(visualCategory, "limitSwitchLsCrossTraveling", v.limitSwitch.crossTraveling)
    addResultItem(visualCategory, "limitSwitchLsHoisting", v.limitSwitch.lifting)
    addResultItem(visualCategory, "operatorRoomCabinSafetyLadder", v.operatorCabin.safetyStairs)
    addResultItem(visualCategory, "operatorRoomCabinDoor", v.operatorCabin.door)
    addResultItem(visualCategory, "operatorRoomCabinWindow", v.operatorCabin.window)
    addResultItem(visualCategory, "operatorRoomCabinFanAC", v.operatorCabin.fanAc)
    addResultItem(visualCategory, "operatorRoomCabinControlLeversButtons", v.operatorCabin.controlLevers)
    addResultItem(visualCategory, "operatorRoomCabinPendantControl", v.operatorCabin.pendantControl)
    addResultItem(visualCategory, "operatorRoomCabinLighting", v.operatorCabin.lighting)
    addResultItem(visualCategory, "operatorRoomCabinHorn", v.operatorCabin.horn)
    addResultItem(visualCategory, "operatorRoomCabinFuseProtection", v.operatorCabin.fuse)
    addResultItem(visualCategory, "operatorRoomCabinCommunicationDevice", v.operatorCabin.commTool)
    addResultItem(visualCategory, "operatorRoomCabinFireExtinguisher", v.operatorCabin.fireExtinguisher)
    addResultItem(visualCategory, "operatorRoomCabinOperatingSigns", v.operatorCabin.operatingSigns)
    addResultItem(visualCategory, "operatorRoomCabinIgnitionKeyMasterSwitch", v.operatorCabin.masterSwitch)
    addResultItem(visualCategory, "electricalComponentPanelConnectorConductor", v.electricalComponents.panelConnector)
    addResultItem(visualCategory, "electricalComponentConductorProtection", v.electricalComponents.conductorGuard)
    addResultItem(visualCategory, "electricalComponentMotorInstallationSafetySystem", v.electricalComponents.motorSafetySystem)
    addResultItem(visualCategory, "electricalComponentGroundingSystem", v.electricalComponents.groundingSystem)
    addResultItem(visualCategory, "electricalComponentInstallation", v.electricalComponents.installation)

    // --- NDE ---
    addStringItem("nde_chain", "method", this.nonDestructiveExamination.chain.method)
    this.nonDestructiveExamination.chain.items.forEachIndexed { index, item ->
        val cat = "nde_chain_item_$index"
        addStringItem(cat, "name", item.chainLocation)
        addStringItem(cat, "specificationMm", item.specDimension)
        addStringItem(cat, "measurementMm", item.resultDimension)
        addStringItem(cat, "lengthIncrease", item.extendLengthMax)
        addStringItem(cat, "wear", item.wearMax)
        addStringItem(cat, "safetyFactor", item.safetyFactor)
        checkItems.add(InspectionCheckItemDomain(0, inspectionId, cat, "finding", item.defectAda, item.description))
    }
    val hook = this.nonDestructiveExamination.mainHook
    addStringItem("nde_mainHook", "method", hook.method)
    addStringItem("nde_mainHook", "result_a", hook.measurements.A)
    addStringItem("nde_mainHook", "result_b", hook.measurements.B)
    addStringItem("nde_mainHook", "result_c", hook.measurements.C)
    addStringItem("nde_mainHook", "result_d", hook.measurements.D)
    addStringItem("nde_mainHook", "result_e", hook.measurements.E)
    addStringItem("nde_mainHook", "result_f", hook.measurements.F)
    addStringItem("nde_mainHook", "result_g", hook.measurements.G)
    addStringItem("nde_mainHook", "result_h", hook.measurements.H)
    addStringItem("nde_mainHook", "tolerance_a", hook.tolerances.A)
    addStringItem("nde_mainHook", "tolerance_b", hook.tolerances.B)
    addStringItem("nde_mainHook", "tolerance_c", hook.tolerances.C)
    addStringItem("nde_mainHook", "tolerance_d", hook.tolerances.D)
    addStringItem("nde_mainHook", "tolerance_e", hook.tolerances.E)
    addStringItem("nde_mainHook", "tolerance_f", hook.tolerances.F)
    addStringItem("nde_mainHook", "tolerance_g", hook.tolerances.G)
    addStringItem("nde_mainHook", "tolerance_h", hook.tolerances.H)
    checkItems.add(InspectionCheckItemDomain(0, inspectionId, "nde_mainHook", "result_finding", false, hook.result))

    // --- Testing ---
    val dynWithoutLoadCat = "testing_dynamic_without_load"
    this.testing.dynamicTest.withoutLoad.forEach {
        val itemName = it.test.lowercase().replace(" ", "")
        addStringItem(dynWithoutLoadCat, "${itemName}_shouldBe", it.shouldBe)
        addStringItem(dynWithoutLoadCat, "${itemName}_testedMeasured", it.testedOrMeasured)
        addStringItem(dynWithoutLoadCat, "${itemName}_remarks", it.remarks)
    }

    val dynWithLoadCat = "testing_dynamic_with_load"
    this.testing.dynamicTest.withLoad.forEach {
        val itemName = it.load.lowercase().replace("% swl", "").replace(" ", "")
        addStringItem(dynWithLoadCat, "${itemName}_hoist", it.hoist)
        addStringItem(dynWithLoadCat, "${itemName}_traversing", it.traversing)
        addStringItem(dynWithLoadCat, "${itemName}_travelling", it.traveling)
        addStringItem(dynWithLoadCat, "${itemName}_brakeSystem", it.brakeSystem)
        addStringItem(dynWithLoadCat, "${itemName}_remarks", it.remarks)
    }

    val staticTestCat = "testing_static"
    val static = this.testing.staticTest
    addStringItem(staticTestCat, "load", static.testLoad)
    addStringItem(staticTestCat, "notes", static.notes)
    addStringItem(staticTestCat, "deflection_single_measurement", static.deflection.singleGirder.measurement)
    addStringItem(staticTestCat, "deflection_single_description", static.deflection.singleGirder.description)
    addStringItem(staticTestCat, "deflection_double_measurement", static.deflection.doubleGirder.measurement)
    addStringItem(staticTestCat, "deflection_double_description", static.deflection.doubleGirder.description)
    addStringItem(staticTestCat, "deflection_single_design_mm", static.singleGirder.designMm)
    addStringItem(staticTestCat, "deflection_single_span_mm", static.singleGirder.spanMm)
    checkItems.add(InspectionCheckItemDomain(0, inspectionId, staticTestCat, "deflection_single_result", static.singleGirder.result, null))
    addStringItem(staticTestCat, "deflection_double_design_mm", static.doubleGirder.designMm)
    addStringItem(staticTestCat, "deflection_double_span_mm", static.doubleGirder.spanMm)
    checkItems.add(InspectionCheckItemDomain(0, inspectionId, staticTestCat, "deflection_double_result", static.doubleGirder.result, null))


    // --- Findings ---
    val findings = mutableListOf<InspectionFindingDomain>()
    this.conclusion.split("\n").filter { it.isNotBlank() }.forEach {
        findings.add(InspectionFindingDomain(0, inspectionId, it, FindingType.FINDING))
    }
    this.recommendations.split("\n").filter { it.isNotBlank() }.forEach {
        findings.add(InspectionFindingDomain(0, inspectionId, it, FindingType.RECOMMENDATION))
    }

    // --- Main Inspection Domain ---
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id,
        moreExtraId = "",
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Overhead_Crane,
        equipmentType = this.generalData.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.unitLocation,
        addressUsageLocation = this.generalData.userInCharge,
        serialNumber = this.generalData.serialNumberUnitNumber,
        permitNumber = this.generalData.usagePermitNumber,
        capacity = this.generalData.capacityWorkingLoadKg,
        manufacturer = ManufacturerDomain(
            name = this.generalData.manufacturer,
            brandOrType = this.generalData.brandType,
            year = this.generalData.yearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList()
    )
}
