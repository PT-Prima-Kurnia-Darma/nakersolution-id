package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapFunctionalTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapInspectionResult
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapLoadTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapNdtTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapSignature
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapVisualCheck
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneDynamicHookTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneInspectionAndTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneStaticHookTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneTechnicalData
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain

// =================================================================================================
//                                     Constants
// =================================================================================================

private object BapCategories {
    const val VISUAL_CHECK = "BAP_VISUAL_CHECK"
    const val FUNCTIONAL_TEST = "BAP_FUNCTIONAL_TEST"
    const val LOAD_TEST = "BAP_LOAD_TEST"
    const val NDT_TEST = "BAP_NDT_TEST"
    const val SIGNATURE = "BAP_SIGNATURE"
}

// =================================================================================================
//                                     BAP Mappers
// =================================================================================================

/**
 * Maps a [MobileCraneBapReportData] DTO (from API response) to an [InspectionWithDetailsDomain].
 * This reconstructs the domain model from the DTO.
 */
fun MobileCraneBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId
    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun addBooleanCheckItem(category: String, itemName: String, status: Boolean) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = status,
                result = null
            )
        )
    }

    fun addStringCheckItem(category: String, itemName: String, result: String?) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = false,
                result = result ?: ""
            )
        )
    }

    // --- Map Visual Check ---
    val visual = this.inspectionResult.visualCheck
    addBooleanCheckItem(BapCategories.VISUAL_CHECK, "hasBoomDefects", visual.hasBoomDefects)
    addBooleanCheckItem(BapCategories.VISUAL_CHECK, "isNameplateAttached", visual.isNameplateAttached)
    addBooleanCheckItem(BapCategories.VISUAL_CHECK, "areBoltsAndNutsSecure", visual.areBoltsAndNutsSecure)
    addBooleanCheckItem(BapCategories.VISUAL_CHECK, "isSlingGoodCondition", visual.isSlingGoodCondition)
    addBooleanCheckItem(BapCategories.VISUAL_CHECK, "isHookGoodCondition", visual.isHookGoodCondition)
    addBooleanCheckItem(BapCategories.VISUAL_CHECK, "isSafetyLatchInstalled", visual.isSafetyLatchInstalled)
    addBooleanCheckItem(BapCategories.VISUAL_CHECK, "isTireGoodCondition", visual.isTireGoodCondition)
    addBooleanCheckItem(BapCategories.VISUAL_CHECK, "isWorkLampFunctional", visual.isWorkLampFunctional)

    // --- Map Functional Test ---
    val functional = this.inspectionResult.functionalTest
    addBooleanCheckItem(BapCategories.FUNCTIONAL_TEST, "isForwardReverseFunctionOk", functional.isForwardReverseFunctionOk)
    addBooleanCheckItem(BapCategories.FUNCTIONAL_TEST, "isSwingFunctionOk", functional.isSwingFunctionOk)
    addBooleanCheckItem(BapCategories.FUNCTIONAL_TEST, "isHoistingFunctionOk", functional.isHoistingFunctionOk)

    // --- Map Load Test ---
    val loadTest = functional.loadTest
    addStringCheckItem(BapCategories.LOAD_TEST, "loadKg", loadTest.loadKg)
    addStringCheckItem(BapCategories.LOAD_TEST, "liftHeightMeters", loadTest.liftHeightMeters)
    addStringCheckItem(BapCategories.LOAD_TEST, "holdTimeSeconds", loadTest.holdTimeSeconds)
    addBooleanCheckItem(BapCategories.LOAD_TEST, "isResultGood", loadTest.isResultGood)

    // --- Map NDT Test ---
    val ndtTest = functional.ndtTest
    addStringCheckItem(BapCategories.NDT_TEST, "method", ndtTest.method)
    addBooleanCheckItem(BapCategories.NDT_TEST, "isResultGood", ndtTest.isResultGood)

    // --- Map Signature ---
    addStringCheckItem(BapCategories.SIGNATURE, "companyName", this.signature.companyName)

    // --- Main Inspection Domain Object ---
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id, // Store BAP's own ID here
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PAA, // Assuming PAA, adjust if needed
        subInspectionType = SubInspectionType.Mobile_Crane,
        equipmentType = "", // Not available in BAP DTO
        examinationType = this.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.userAddress,
        serialNumber = this.technicalData.serialNumberUnitNumber,
        permitNumber = this.technicalData.materialCertificateNumber,
        capacity = this.technicalData.capacityWorkingLoad,
        speed = this.technicalData.liftingSpeedMpm,
        floorServed = this.technicalData.maxLiftingHeight,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            year = this.technicalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(), // BAP DTO has no findings
        testResults = emptyList()
    )
}

/**
 * Maps an [InspectionWithDetailsDomain] to a [MobileCraneBapRequest] DTO for API submission.
 */
fun InspectionWithDetailsDomain.toMobileCraneBapRequest(): MobileCraneBapRequest {
    val checkItemsByCat = this.checkItems.groupBy { it.category }

    fun getStatus(category: String, itemName: String, default: Boolean = false): Boolean {
        return checkItemsByCat[category]?.find { it.itemName == itemName }?.status ?: default
    }

    fun getValue(category: String, itemName: String, default: String = ""): String {
        return checkItemsByCat[category]?.find { it.itemName == itemName }?.result ?: default
    }

    val generalData = MobileCraneBapGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        ownerAddress = this.inspection.ownerAddress ?: "",
        userAddress = this.inspection.usageLocation ?: ""
    )

    val technicalData = MobileCraneBapTechnicalData(
        manufacturer = this.inspection.manufacturer?.name ?: "",
        locationAndYearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = this.inspection.serialNumber ?: "",
        capacityWorkingLoad = this.inspection.capacity ?: "",
        maxLiftingHeight = this.inspection.floorServed ?: "",
        materialCertificateNumber = this.inspection.permitNumber ?: "",
        liftingSpeedMpm = this.inspection.speed ?: ""
    )

    val visualCheck = MobileCraneBapVisualCheck(
        hasBoomDefects = getStatus(BapCategories.VISUAL_CHECK, "hasBoomDefects"),
        isNameplateAttached = getStatus(BapCategories.VISUAL_CHECK, "isNameplateAttached"),
        areBoltsAndNutsSecure = getStatus(BapCategories.VISUAL_CHECK, "areBoltsAndNutsSecure"),
        isSlingGoodCondition = getStatus(BapCategories.VISUAL_CHECK, "isSlingGoodCondition"),
        isHookGoodCondition = getStatus(BapCategories.VISUAL_CHECK, "isHookGoodCondition"),
        isSafetyLatchInstalled = getStatus(BapCategories.VISUAL_CHECK, "isSafetyLatchInstalled"),
        isTireGoodCondition = getStatus(BapCategories.VISUAL_CHECK, "isTireGoodCondition"),
        isWorkLampFunctional = getStatus(BapCategories.VISUAL_CHECK, "isWorkLampFunctional")
    )

    val loadTest = MobileCraneBapLoadTest(
        loadKg = getValue(BapCategories.LOAD_TEST, "loadKg"),
        liftHeightMeters = getValue(BapCategories.LOAD_TEST, "liftHeightMeters"),
        holdTimeSeconds = getValue(BapCategories.LOAD_TEST, "holdTimeSeconds"),
        isResultGood = getStatus(BapCategories.LOAD_TEST, "isResultGood")
    )

    val ndtTest = MobileCraneBapNdtTest(
        method = getValue(BapCategories.NDT_TEST, "method"),
        isResultGood = getStatus(BapCategories.NDT_TEST, "isResultGood")
    )

    val functionalTest = MobileCraneBapFunctionalTest(
        isForwardReverseFunctionOk = getStatus(BapCategories.FUNCTIONAL_TEST, "isForwardReverseFunctionOk"),
        isSwingFunctionOk = getStatus(BapCategories.FUNCTIONAL_TEST, "isSwingFunctionOk"),
        isHoistingFunctionOk = getStatus(BapCategories.FUNCTIONAL_TEST, "isHoistingFunctionOk"),
        loadTest = loadTest,
        ndtTest = ndtTest
    )

    val inspectionResult = MobileCraneBapInspectionResult(
        visualCheck = visualCheck,
        functionalTest = functionalTest
    )

    val signature = MobileCraneBapSignature(
        companyName = getValue(BapCategories.SIGNATURE, "companyName")
    )

    return MobileCraneBapRequest(
        laporanId = this.inspection.extraId,
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        extraId = this.inspection.id,
        createdAt = this.inspection.createdAt ?: "",
        generalData = generalData,
        technicalData = technicalData,
        inspectionResult = inspectionResult,
        signature = signature
    )
}


// =================================================================================================
//                                     Report Mappers
// =================================================================================================

fun MobileCraneReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    val inspectionId = this.extraId

    fun addCheckItem(category: String, itemName: String, value: String?) {
        checkItems.add(
            InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = false, result = value ?: "")
        )
    }

    fun addResultStatusCheckItem(category: String, itemName: String, value: ResultStatus?) {
        checkItems.add(
            InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = value?.status ?: false, result = value?.result ?: "")
        )
    }

    // --- General Data ---
    val generalDataCategory = "general_data"
    addCheckItem(generalDataCategory, "personInCharge", this.generalData.userSubcontractorPersonInCharge)
    addCheckItem(generalDataCategory, "operatorName", this.generalData.operatorName)
    addCheckItem(generalDataCategory, "intendedUse", this.generalData.intendedUse)
    addCheckItem(generalDataCategory, "operatorCertificate", this.generalData.operatorCertificate)
    addCheckItem(generalDataCategory, "equipmentHistory", this.generalData.equipmentHistory)
    addCheckItem(generalDataCategory, "inspectionDate", this.generalData.inspectionDate)


    // --- Technical Data ---
    val techCategory = "technical_data"
    val tech = this.technicalData
    addCheckItem(techCategory, "spec_maximumWorkingLoadCapacity", tech.maximumWorkingLoadCapacity)
    addCheckItem(techCategory, "spec_boomLength", tech.boomLength)
    addCheckItem(techCategory, "spec_maximumJibLength", tech.maximumJibLength)
    addCheckItem(techCategory, "spec_maximumJibWorkingLoad", tech.maximumJibWorkingLoad)
    addCheckItem(techCategory, "spec_maxBoomJibLength", tech.maxBoomJibLength)
    addCheckItem(techCategory, "spec_craneWeight", tech.craneWeight)
    addCheckItem(techCategory, "spec_maxLiftingHeight", tech.maxLiftingHeight)
    addCheckItem(techCategory, "spec_boomWorkingAngle", tech.boomWorkingAngle)
    addCheckItem(techCategory, "motor_engineNumber", tech.engineNumber)
    addCheckItem(techCategory, "motor_type", tech.engineType)
    addCheckItem(techCategory, "motor_numberOfCylinders", tech.numberOfCylinders)
    addCheckItem(techCategory, "motor_netPower", tech.netPower)
    addCheckItem(techCategory, "motor_brandYearOfManufacture", tech.brandYearOfManufacture)
    // NOTE: DTO has `hookManufacturer` but UI/Domain has `driveMotor.manufacturer`. Assuming they are the same for this mapping.
    addCheckItem(techCategory, "motor_manufacturer", tech.hookManufacturer)
    addCheckItem(techCategory, "mainHook_type", tech.mainHookType)
    addCheckItem(techCategory, "mainHook_capacity", tech.mainHookCapacity)
    addCheckItem(techCategory, "mainHook_material", tech.mainHookMaterial)
    addCheckItem(techCategory, "mainHook_serialNumber", tech.mainHookSerialNumber)
    addCheckItem(techCategory, "auxHook_type", tech.auxiliaryHookType)
    addCheckItem(techCategory, "auxHook_capacity", tech.auxiliaryHookCapacity)
    addCheckItem(techCategory, "auxHook_material", tech.auxiliaryHookMaterial)
    addCheckItem(techCategory, "auxHook_serialNumber", tech.auxiliaryHookSerialNumber)
    addCheckItem(techCategory, "rope_main_diameter", tech.wireRopeMainLoadHoistDrumDiameter)
    addCheckItem(techCategory, "rope_main_type", tech.wireRopeMainLoadHoistDrumType)
    addCheckItem(techCategory, "rope_main_construction", tech.wireRopeMainLoadHoistDrumConstruction)
    addCheckItem(techCategory, "rope_main_breakingStrength", tech.wireRopeMainLoadHoistDrumBreakingStrength)
    addCheckItem(techCategory, "rope_main_length", tech.wireRopeMainLoadHoistDrumLength)
    addCheckItem(techCategory, "rope_aux_diameter", tech.wireRopeAuxiliaryLoadHoistDrumDiameter)
    addCheckItem(techCategory, "rope_aux_type", tech.wireRopeAuxiliaryLoadHoistDrumType)
    addCheckItem(techCategory, "rope_aux_construction", tech.wireRopeAuxiliaryLoadHoistDrumConstruction)
    addCheckItem(techCategory, "rope_aux_breakingStrength", tech.wireRopeAuxiliaryLoadHoistDrumBreakingStrength)
    addCheckItem(techCategory, "rope_aux_length", tech.wireRopeAuxiliaryLoadHoistDrumLength)
    addCheckItem(techCategory, "rope_boom_diameter", tech.wireRopeBoomHoistDrumDiameter)
    addCheckItem(techCategory, "rope_boom_type", tech.wireRopeBoomHoistDrumType)
    addCheckItem(techCategory, "rope_boom_construction", tech.wireRopeBoomHoistDrumConstruction)
    addCheckItem(techCategory, "rope_boom_breakingStrength", tech.wireRopeBoomHoistDrumBreakingStrength)
    addCheckItem(techCategory, "rope_boom_length", tech.wireRopeBoomHoistDrumLength)


    // --- Inspection and Testing ---
    val visualCategory = "visual_inspection"
    val it = this.inspectionAndTesting
    addResultStatusCheckItem(visualCategory, "foundationAnchorBoltCorrosion", it.visualFoundationAndBoltsCorrosionResult)
    addResultStatusCheckItem(visualCategory, "foundationAnchorBoltCracks", it.visualFoundationAndBoltsCracksResult)
    addResultStatusCheckItem(visualCategory, "foundationAnchorBoltDeformation", it.visualFoundationAndBoltsDeformationResult)
    addResultStatusCheckItem(visualCategory, "foundationAnchorBoltTightness", it.visualFoundationAndBoltsTightnessResult)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationCorrosion", it.visualFrameColumnsOnFoundationCorrosionResult)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationCracks", it.visualFrameColumnsOnFoundationCracksResult)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationDeformation", it.visualFrameColumnsOnFoundationDeformationResult)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationFastening", it.visualFrameColumnsOnFoundationFasteningResult)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationTransverseReinforcement", it.visualFrameColumnsOnFoundationTransverseReinforcementResult)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationDiagonalReinforcement", it.visualFrameColumnsOnFoundationDiagonalReinforcementResult)
    addResultStatusCheckItem(visualCategory, "ladderCorrosion", it.visualLadderCorrosionResult)
    addResultStatusCheckItem(visualCategory, "ladderCracks", it.visualLadderCracksResult)
    addResultStatusCheckItem(visualCategory, "ladderDeformation", it.visualLadderDeformationResult)
    addResultStatusCheckItem(visualCategory, "ladderFastening", it.visualLadderFasteningResult)
    addResultStatusCheckItem(visualCategory, "workingPlatformCorrosion", it.visualWorkingPlatformCorrosionResult)
    addResultStatusCheckItem(visualCategory, "workingPlatformCracks", it.visualWorkingPlatformCracksResult)
    addResultStatusCheckItem(visualCategory, "workingPlatformDeformation", it.visualWorkingPlatformDeformationResult)
    addResultStatusCheckItem(visualCategory, "workingPlatformFastening", it.visualWorkingPlatformFasteningResult)
    addResultStatusCheckItem(visualCategory, "outriggersOutriggerArmHousing", it.visualOutriggersOutriggerArmHousingResult)
    addResultStatusCheckItem(visualCategory, "outriggersOutriggerArms", it.visualOutriggersOutriggerArmsResult)
    addResultStatusCheckItem(visualCategory, "outriggersJack", it.visualOutriggersJackResult)
    addResultStatusCheckItem(visualCategory, "outriggersOutriggerPads", it.visualOutriggersOutriggerPadsResult)
    addResultStatusCheckItem(visualCategory, "outriggersHousingConnectionToChassis", it.visualOutriggersHousingConnectionToChassisResult)
    addResultStatusCheckItem(visualCategory, "outriggersOutriggerSafetyLocks", it.visualOutriggersOutriggerSafetyLocksResult)
    addResultStatusCheckItem(visualCategory, "turntableSlewingRollerBearing", it.visualTurntableSlewingRollerBearingResult)
    addResultStatusCheckItem(visualCategory, "turntableBrakeHousing", it.visualTurntableBrakeHousingResult)
    addResultStatusCheckItem(visualCategory, "turntableBrakeLiningsAndShoes", it.visualTurntableBrakeLiningsAndShoesResult)
    addResultStatusCheckItem(visualCategory, "turntableDrumSurface", it.visualTurntableDrumSurfaceResult)
    addResultStatusCheckItem(visualCategory, "turntablePressureCylinder", it.visualTurntablePressureCylinderResult)
    addResultStatusCheckItem(visualCategory, "turntableDrumAxle", it.visualTurntableDrumAxleResult)
    addResultStatusCheckItem(visualCategory, "turntableLeversPinsBolts", it.visualTurntableLeversPinsBoltsResult)
    addResultStatusCheckItem(visualCategory, "turntableGuard", it.visualTurntableGuardResult)
    addResultStatusCheckItem(visualCategory, "latticeBoomMainBoom", it.visualLatticeBoomMainBoomResult)
    addResultStatusCheckItem(visualCategory, "latticeBoomBoomSection", it.visualLatticeBoomBoomSectionResult)
    addResultStatusCheckItem(visualCategory, "latticeBoomTopPulley", it.visualLatticeBoomTopPulleyResult)
    addResultStatusCheckItem(visualCategory, "latticeBoomPulleyGuard", it.visualLatticeBoomPulleyGuardResult)
    addResultStatusCheckItem(visualCategory, "latticeBoomWireRopeGuard", it.visualLatticeBoomWireRopeGuardResult)
    addResultStatusCheckItem(visualCategory, "latticeBoomPulleyGrooveLip", it.visualLatticeBoomPulleyGrooveLipResult)
    addResultStatusCheckItem(visualCategory, "latticeBoomPivotPin", it.visualLatticeBoomPivotPinResult)
    addResultStatusCheckItem(visualCategory, "latticeBoomWireRopeGuidePulley", it.visualLatticeBoomWireRopeGuidePulleyResult)
    addResultStatusCheckItem(visualCategory, "clutchMainClutch", it.visualSteeringMainClutchResult)
    addResultStatusCheckItem(visualCategory, "transmission", it.visualTransmissionResult)
    addResultStatusCheckItem(visualCategory, "steeringFrontWheel", it.visualSteeringFrontWheelResult)
    addResultStatusCheckItem(visualCategory, "steeringMiddleWheel", it.visualSteeringMiddleWheelResult)
    addResultStatusCheckItem(visualCategory, "steeringRearWheel", it.visualSteeringRearWheelResult)
    addResultStatusCheckItem(visualCategory, "brakeServiceBrake", it.visualBrakeServiceBrakeResult)
    addResultStatusCheckItem(visualCategory, "brakeParkingBrake", it.visualBrakeParkingBrakeResult)
    addResultStatusCheckItem(visualCategory, "brakeBrakeHousing", it.visualBrakeBrakeHousingResult)
    addResultStatusCheckItem(visualCategory, "brakeBrakeLiningsAndShoes", it.visualBrakeBrakeLiningsAndShoesResult)
    addResultStatusCheckItem(visualCategory, "brakeDrumSurface", it.visualBrakeDrumSurfaceResult)
    addResultStatusCheckItem(visualCategory, "brakeLeversPinsBolts", it.visualBrakeLeversPinsAndBoltsResult)
    addResultStatusCheckItem(visualCategory, "brakeGuard", it.visualBrakeGuardResult)
    addResultStatusCheckItem(visualCategory, "travelDrumClutchHousing", it.visualTravelDrumClutchHousingResult)
    addResultStatusCheckItem(visualCategory, "travelDrumClutchLining", it.visualTravelDrumClutchLiningResult)
    addResultStatusCheckItem(visualCategory, "travelDrumClutchDrumSurface", it.visualTravelDrumClutchDrumSurfaceResult)
    addResultStatusCheckItem(visualCategory, "travelDrumLeversPinsBolts", it.visualTravelDrumLeversPinsAndBoltsResult)
    addResultStatusCheckItem(visualCategory, "travelDrumGuard", it.visualTravelDrumGuardResult)
    addResultStatusCheckItem(visualCategory, "mainWinchDrumMounting", it.visualMainWinchDrumMountingResult)
    addResultStatusCheckItem(visualCategory, "mainWinchWindingDrumSurface", it.visualMainWinchWindingDrumSurfaceResult)
    addResultStatusCheckItem(visualCategory, "mainWinchBrakeLiningsAndShoes", it.visualMainWinchBrakeLiningsAndShoesResult)
    addResultStatusCheckItem(visualCategory, "mainWinchBrakeDrumSurface", it.visualMainWinchBrakeDrumSurfaceResult)
    addResultStatusCheckItem(visualCategory, "mainWinchBrakeHousing", it.visualMainWinchBrakeHousingResult)
    addResultStatusCheckItem(visualCategory, "mainWinchClutchLiningsAndShoes", it.visualMainWinchClutchLiningsAndShoesResult)
    addResultStatusCheckItem(visualCategory, "mainWinchClutchDrumSurface", it.visualMainWinchClutchDrumSurfaceResult)
    addResultStatusCheckItem(visualCategory, "mainWinchGroove", it.visualMainWinchGrooveResult)
    addResultStatusCheckItem(visualCategory, "mainWinchGrooveLip", it.visualMainWinchGrooveLipResult)
    addResultStatusCheckItem(visualCategory, "mainWinchFlanges", it.visualMainWinchFlangesResult)
    addResultStatusCheckItem(visualCategory, "mainWinchBrakeActuatorLeversPinsAndBolts", it.visualMainWinchBrakeActuatorLeversPinsAndBoltsResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchDrumMounting", it.visualAuxiliaryWinchDrumMountingResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchWindingDrumSurface", it.visualAuxiliaryWinchWindingDrumSurfaceResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchBrakeLiningsAndShoes", it.visualAuxiliaryWinchBrakeLiningsAndShoesResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchBrakeDrumSurface", it.visualAuxiliaryWinchBrakeDrumSurfaceResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchBrakeHousing", it.visualAuxiliaryWinchBrakeHousingResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchClutchLiningsAndShoes", it.visualAuxiliaryWinchClutchLiningsAndShoesResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchClutchDrumSurface", it.visualAuxiliaryWinchClutchDrumSurfaceResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchGroove", it.visualAuxiliaryWinchGrooveResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchGrooveLip", it.visualAuxiliaryWinchGrooveLipResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchFlanges", it.visualAuxiliaryWinchFlangesResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchBrakeActuatorLeversPinsAndBolts", it.visualAuxiliaryWinchBrakeActuatorLeversPinsAndBoltsResult)
    addResultStatusCheckItem(visualCategory, "hoistGearBlockLubrication", it.visualHoistGearBlockLubricationResult)
    addResultStatusCheckItem(visualCategory, "hoistGearBlockOilSeal", it.visualHoistGearBlockOilSealResult)
    addResultStatusCheckItem(visualCategory, "mainPulleyPulleyGroove", it.visualMainPulleyPulleyGrooveResult)
    addResultStatusCheckItem(visualCategory, "mainPulleyPulleyGrooveLip", it.visualMainPulleyPulleyGrooveLipResult)
    addResultStatusCheckItem(visualCategory, "mainPulleyPulleyPin", it.visualMainPulleyPulleyPinResult)
    addResultStatusCheckItem(visualCategory, "mainPulleyBearing", it.visualMainPulleyBearingResult)
    addResultStatusCheckItem(visualCategory, "mainPulleyPulleyGuard", it.visualMainPulleyPulleyGuardResult)
    addResultStatusCheckItem(visualCategory, "mainPulleyWireRopeGuard", it.visualMainPulleyWireRopeGuardResult)
    addResultStatusCheckItem(visualCategory, "mainHookVisualSwivelNutAndBearing", it.visualMainHookSwivelNutAndBearingResult)
    addResultStatusCheckItem(visualCategory, "mainHookVisualTrunnion", it.visualMainHookTrunnionResult)
    addResultStatusCheckItem(visualCategory, "mainHookVisualSafetyLatch", it.visualMainHookSafetyLatchResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryHookVisualFreeFallWeight", it.visualAuxiliaryHookFreeFallWeightResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryHookVisualSwivelNutAndBearing", it.visualAuxiliaryHookSwivelNutAndBearingResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryHookVisualSafetyLatch", it.visualAuxiliaryHookSafetyLatchResult)
    addResultStatusCheckItem(visualCategory, "mainWireRopeVisualCorrosion", it.visualMainWireRopeCorrosionResult)
    addResultStatusCheckItem(visualCategory, "mainWireRopeVisualWear", it.visualMainWireRopeWearResult)
    addResultStatusCheckItem(visualCategory, "mainWireRopeVisualBreakage", it.visualMainWireRopeBreakageResult)
    addResultStatusCheckItem(visualCategory, "mainWireRopeVisualDeformation", it.visualMainWireRopeDeformationResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWireRopeVisualCorrosion", it.visualAuxiliaryWireRopeCorrosionResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWireRopeVisualWear", it.visualAuxiliaryWireRopeWearResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWireRopeVisualBreakage", it.visualAuxiliaryWireRopeBreakageResult)
    addResultStatusCheckItem(visualCategory, "auxiliaryWireRopeVisualDeformation", it.visualAuxiliaryWireRopeDeformationResult)
    addResultStatusCheckItem(visualCategory, "limitSwitchLsLongTravel", it.visualLimitSwitchLongTravelResult)
    addResultStatusCheckItem(visualCategory, "limitSwitchLsCrossTravel", it.visualLimitSwitchCrossTravelResult)
    addResultStatusCheckItem(visualCategory, "limitSwitchLsHoisting", it.visualLimitSwitchHoistingResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineCoolingSystem", it.visualInternalCombustionEngineCoolingSystemResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineLubricationSystem", it.visualInternalCombustionEngineLubricationSystemResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineEngineMounting", it.visualInternalCombustionEngineEngineMountingResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineSafetyGuardEquipment", it.visualInternalCombustionEngineSafetyGuardEquipmentResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineExhaustSystem", it.visualInternalCombustionEngineExhaustSystemResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineFuelSystem", it.visualInternalCombustionEngineFuelSystemResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEnginePowerTransmissionSystem", it.visualInternalCombustionEnginePowerTransmissionSystemResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineBattery", it.visualInternalCombustionEngineBatteryResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineStarterMotor", it.visualInternalCombustionEngineStarterMotorResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineWiringInstallation", it.visualInternalCombustionEngineWiringInstallationResult)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineTurbocharger", it.visualInternalCombustionEngineTurbochargerResult)
    addResultStatusCheckItem(visualCategory, "hydraulicHydraulicPump", it.visualHydraulicPumpResult)
    addResultStatusCheckItem(visualCategory, "hydraulicHydraulicLines", it.visualHydraulicLinesResult)
    addResultStatusCheckItem(visualCategory, "hydraulicHydraulicFilter", it.visualHydraulicFilterResult)
    addResultStatusCheckItem(visualCategory, "hydraulicHydraulicTank", it.visualHydraulicTankResult)
    addResultStatusCheckItem(visualCategory, "hydraulicMotorMainWinchMotor", it.visualHydraulicMainWinchMotorResult)
    addResultStatusCheckItem(visualCategory, "hydraulicMotorAuxiliaryWinchMotor", it.visualHydraulicAuxiliaryWinchMotorResult)
    addResultStatusCheckItem(visualCategory, "hydraulicMotorBoomWinchMotor", it.visualHydraulicBoomWinchMotorResult)
    addResultStatusCheckItem(visualCategory, "hydraulicMotorSwingMotor", it.visualHydraulicSwingMotorResult)
    addResultStatusCheckItem(visualCategory, "controlValveReliefValve", it.visualControlValveReliefValveResult)
    addResultStatusCheckItem(visualCategory, "controlValveMainWinchValve", it.visualControlValveMainWinchValveResult)
    addResultStatusCheckItem(visualCategory, "controlValveAuxiliaryWinchValve", it.visualControlValveAuxiliaryWinchValveResult)
    addResultStatusCheckItem(visualCategory, "controlValveBoomWinchValve", it.visualControlValveBoomWinchValveResult)
    addResultStatusCheckItem(visualCategory, "controlValveBoomMovementValve", it.visualControlValveBoomMovementValveResult)
    addResultStatusCheckItem(visualCategory, "controlValveSteeringCylinderValve", it.visualControlValveSteeringCylinderValveResult)
    addResultStatusCheckItem(visualCategory, "controlValveAxleOscillationValve", it.visualControlValveAxleOscillationValveResult)
    addResultStatusCheckItem(visualCategory, "controlValveOutriggerMovementValve", it.visualControlValveOutriggerMovementValveResult)
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderBoomMovementCylinder", it.visualHydraulicCylinderBoomMovementCylinderResult)
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderOutriggerCylinder", it.visualHydraulicCylinderOutriggerCylinderResult)
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderSteeringWheelCylinder", it.visualHydraulicCylinderSteeringWheelCylinderResult)
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderAxleOscillationCylinder", it.visualHydraulicCylinderAxleOscillationCylinderResult)
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderTelescopicCylinder", it.visualHydraulicCylinderTelescopicCylinderResult)
    addResultStatusCheckItem(visualCategory, "pneumaticCompressor", it.visualPneumaticCompressorResult)
    addResultStatusCheckItem(visualCategory, "pneumaticTankAndSafetyValve", it.visualPneumaticTankAndSafetyValveResult)
    addResultStatusCheckItem(visualCategory, "pneumaticPressurizedAirLines", it.visualPneumaticPressurizedAirLinesResult)
    addResultStatusCheckItem(visualCategory, "pneumaticAirFilter", it.visualPneumaticAirFilterResult)
    addResultStatusCheckItem(visualCategory, "pneumaticControlValve", it.visualPneumaticControlValveResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinSafetyLadder", it.visualOperatorCabinSafetyLadderResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinDoor", it.visualOperatorCabinDoorResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinWindow", it.visualOperatorCabinWindowResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinFanAc", it.visualOperatorCabinFanAcResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinControlLeversButtons", it.visualOperatorCabinControlLeversButtonsResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinPendantControl", it.visualOperatorCabinPendantControlResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinLighting", it.visualOperatorCabinLightingResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinHornSignalAlarm", it.visualOperatorCabinHornSignalAlarmResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinFuse", it.visualOperatorCabinFuseResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinCommunicationDevice", it.visualOperatorCabinCommunicationDeviceResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinFireExtinguisher", it.visualOperatorCabinFireExtinguisherResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinOperatingSigns", it.visualOperatorCabinOperatingSignsResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinIgnitionKeyMasterSwitch", it.visualOperatorCabinIgnitionKeyMasterSwitchResult)
    addResultStatusCheckItem(visualCategory, "operatorCabinButtonsHandlesLevers", it.visualOperatorCabinButtonsHandlesLeversResult)
    addResultStatusCheckItem(visualCategory, "electricalComponentsPanelConductorConnector", it.visualElectricalComponentsPanelConductorConnectorResult)
    addResultStatusCheckItem(visualCategory, "electricalComponentsConductorProtection", it.visualElectricalComponentsConductorProtectionResult)
    addResultStatusCheckItem(visualCategory, "electricalComponentsMotorInstallationSafetySystem", it.visualElectricalComponentsMotorInstallationSafetySystemResult)
    addResultStatusCheckItem(visualCategory, "electricalComponentsGroundingSystem", it.visualElectricalComponentsGroundingSystemResult)
    addResultStatusCheckItem(visualCategory, "electricalComponentsInstallation", it.visualElectricalComponentsInstallationResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLadderHandrail", it.visualSafetyDevicesLadderHandrailResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesEngineOilLubricantPressure", it.visualSafetyDevicesEngineOilLubricantPressureResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesHydraulicOilPressure", it.visualSafetyDevicesHydraulicOilPressureResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAirPressure", it.visualSafetyDevicesAirPressureResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAmperemeter", it.visualSafetyDevicesAmperemeterResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesVoltage", it.visualSafetyDevicesVoltageResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesEngineTemperature", it.visualSafetyDevicesEngineTemperatureResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesTransmissionTemperature", it.visualSafetyDevicesTransmissionTemperatureResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesConverterOilTemperaturePressure", it.visualSafetyDevicesConverterOilTemperaturePressureResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesSpeedometerIndicator", it.visualSafetyDevicesConverterSpeedometerIndicatorResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesRotaryLamp", it.visualSafetyDevicesConverterRotaryLampResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesMainHoistRopeUpDownLimit", it.visualSafetyDevicesConverterMainHoistRopeUpDownLimitResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAuxiliaryHoistRopeUpDownLimit", it.visualSafetyDevicesConverterAuxiliaryHoistRopeUpDownLimitResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesSwingMotionLimit", it.visualSafetyDevicesConverterSwingMotionLimitResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLevelIndicator", it.visualSafetyDevicesConverterLevelIndicatorResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLoadWeightIndicator", it.visualSafetyDevicesConverterLoadWeightIndicatorResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLoadChart", it.visualSafetyDevicesConverterLoadChartResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAnemometerWindSpeed", it.visualSafetyDevicesConverterAnemometerWindSpeedResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesBoomAngleIndicator", it.visualSafetyDevicesConverterBoomAngleIndicatorResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAirPressureIndicator", it.visualSafetyDevicesConverterAirPressureIndicatorResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesHydraulicPressureIndicator", it.visualSafetyDevicesConverterHydraulicPressureIndicatorResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesSafetyValves", it.visualSafetyDevicesConverterSafetyValvesResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesMainWindingDrumSafetyLock", it.visualSafetyDevicesConverterMainWindingDrumSafetyLockResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAuxiliaryWindingDrumSafetyLock", it.visualSafetyDevicesConverterAuxiliaryWindingDrumSafetyLockResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesTelescopicMotionLimit", it.visualSafetyDevicesConverterTelescopicMotionLimitResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLightningArrester", it.visualSafetyDevicesConverterLightningArresterResult)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLiftingHeightIndicator", it.visualSafetyDevicesConverterLiftingHeightIndicatorResult)


    // --- NDE ---
    val ndeCategory = "nde"
    addCheckItem(ndeCategory, "wireRope_main_specDiameter", it.ndtWireRopeMainLoadSpecDiameter)
    addCheckItem(ndeCategory, "wireRope_main_actualDiameter", it.ndtWireRopeMainLoadActualDiameter)
    addCheckItem(ndeCategory, "wireRope_main_construction", it.ndtWireRopeMainLoadConstruction)
    addCheckItem(ndeCategory, "wireRope_main_type", it.ndtWireRopeMainLoadType)
    addCheckItem(ndeCategory, "wireRope_main_length", it.ndtWireRopeMainLoadLength)
    addCheckItem(ndeCategory, "wireRope_main_age", it.ndtWireRopeMainLoadAge)
    addResultStatusCheckItem(ndeCategory, "wireRope_main_result", it.ndtWireRopeMainLoadResult)

    addCheckItem(ndeCategory, "wireRope_aux_specDiameter", it.ndtWireRopeAuxiliaryLoadSpecDiameter)
    addCheckItem(ndeCategory, "wireRope_aux_actualDiameter", it.ndtWireRopeAuxiliaryLoadActualDiameter)
    addCheckItem(ndeCategory, "wireRope_aux_construction", it.ndtWireRopeAuxiliaryLoadConstruction)
    addCheckItem(ndeCategory, "wireRope_aux_type", it.ndtWireRopeAuxiliaryLoadType)
    addCheckItem(ndeCategory, "wireRope_aux_length", it.ndtWireRopeAuxiliaryLoadLength)
    addCheckItem(ndeCategory, "wireRope_aux_age", it.ndtWireRopeAuxiliaryLoadAge)
    addResultStatusCheckItem(ndeCategory, "wireRope_aux_result", it.ndtWireRopeAuxiliaryLoadResult)

    addCheckItem(ndeCategory, "wireRope_boom_specDiameter", it.ndtWireRopeBoomHoistSpecDiameter)
    addCheckItem(ndeCategory, "wireRope_boom_actualDiameter", it.ndtWireRopeBoomHoistActualDiameter)
    addCheckItem(ndeCategory, "wireRope_boom_construction", it.ndtWireRopeBoomHoistConstruction)
    addCheckItem(ndeCategory, "wireRope_boom_type", it.ndtWireRopeBoomHoistType)
    addCheckItem(ndeCategory, "wireRope_boom_length", it.ndtWireRopeBoomHoistLength)
    addCheckItem(ndeCategory, "wireRope_boom_age", it.ndtWireRopeBoomHoistAge)
    addResultStatusCheckItem(ndeCategory, "wireRope_boom_result", it.ndtWireRopeBoomHoistResult)

    addCheckItem(ndeCategory, "boom_type", it.ndtBoomType)
    addCheckItem(ndeCategory, "boom_ndtType", it.ndtBoomNdtType)
    addCheckItem(ndeCategory, "boom_inspection1_part", it.ndtBoomInspection1Part)
    addCheckItem(ndeCategory, "boom_inspection1_location", it.ndtBoomInspection1Location)
    addResultStatusCheckItem(ndeCategory, "boom_inspection1_result", it.ndtBoomInspection1Result)
    addCheckItem(ndeCategory, "boom_inspection2_part", it.ndtBoomInspection2Part)
    addCheckItem(ndeCategory, "boom_inspection2_location", it.ndtBoomInspection2Location)
    addResultStatusCheckItem(ndeCategory, "boom_inspection2_result", it.ndtBoomInspection2Result)
    addCheckItem(ndeCategory, "boom_inspection3_part", it.ndtBoomInspection3Part)
    addCheckItem(ndeCategory, "boom_inspection3_location", it.ndtBoomInspection3Location)
    addResultStatusCheckItem(ndeCategory, "boom_inspection3_result", it.ndtBoomInspection3Result)

    addCheckItem(ndeCategory, "mainHook_ndtType", it.ndtMainHookNdtType)
    addCheckItem(ndeCategory, "mainHook_spec_a", it.ndtMainHookSpecificationA)
    addCheckItem(ndeCategory, "mainHook_spec_b", it.ndtMainHookSpecificationB)
    addCheckItem(ndeCategory, "mainHook_spec_c", it.ndtMainHookSpecificationC)
    addCheckItem(ndeCategory, "mainHook_spec_d", it.ndtMainHookSpecificationD)
    addCheckItem(ndeCategory, "mainHook_spec_e", it.ndtMainHookSpecificationE)
    addCheckItem(ndeCategory, "mainHook_spec_f", it.ndtMainHookSpecificationF)
    addCheckItem(ndeCategory, "mainHook_spec_g", it.ndtMainHookSpecificationG)
    addCheckItem(ndeCategory, "mainHook_spec_h", it.ndtMainHookSpecificationH)
    addResultStatusCheckItem(ndeCategory, "mainHook_spec_result", it.ndtMainHookSpecificationResult)
    addCheckItem(ndeCategory, "mainHook_measure_a", it.ndtMainHookMeasurementResultsA)
    addCheckItem(ndeCategory, "mainHook_measure_b", it.ndtMainHookMeasurementResultsB)
    addCheckItem(ndeCategory, "mainHook_measure_c", it.ndtMainHookMeasurementResultsC)
    addCheckItem(ndeCategory, "mainHook_measure_d", it.ndtMainHookMeasurementResultsD)
    addCheckItem(ndeCategory, "mainHook_measure_e", it.ndtMainHookMeasurementResultsE)
    addCheckItem(ndeCategory, "mainHook_measure_f", it.ndtMainHookMeasurementResultsF)
    addCheckItem(ndeCategory, "mainHook_measure_g", it.ndtMainHookMeasurementResultsG)
    addCheckItem(ndeCategory, "mainHook_measure_h", it.ndtMainHookMeasurementResultsH)
    addResultStatusCheckItem(ndeCategory, "mainHook_measure_result", it.ndtMainHookMeasurementResultsResult)
    addCheckItem(ndeCategory, "mainHook_tolerance_a", it.ndtMainHookToleranceMeasureA)
    addCheckItem(ndeCategory, "mainHook_tolerance_b", it.ndtMainHookToleranceMeasureB)
    addCheckItem(ndeCategory, "mainHook_tolerance_c", it.ndtMainHookToleranceMeasureC)
    addCheckItem(ndeCategory, "mainHook_tolerance_d", it.ndtMainHookToleranceMeasureD)
    addCheckItem(ndeCategory, "mainHook_tolerance_e", it.ndtMainHookToleranceMeasureE)
    addCheckItem(ndeCategory, "mainHook_tolerance_f", it.ndtMainHookToleranceMeasureF)
    addCheckItem(ndeCategory, "mainHook_tolerance_g", it.ndtMainHookToleranceMeasureG)
    addCheckItem(ndeCategory, "mainHook_tolerance_h", it.ndtMainHookToleranceMeasureH)
    addResultStatusCheckItem(ndeCategory, "mainHook_tolerance_result", it.ndtMainHookToleranceMeasureResult)

    addCheckItem(ndeCategory, "auxHook_ndtType", it.ndtAuxiliaryHookNdtType)
    addCheckItem(ndeCategory, "auxHook_spec_a", it.ndtAuxiliaryHookSpecificationA)
    addCheckItem(ndeCategory, "auxHook_spec_b", it.ndtAuxiliaryHookSpecificationB)
    addCheckItem(ndeCategory, "auxHook_spec_c", it.ndtAuxiliaryHookSpecificationC)
    addCheckItem(ndeCategory, "auxHook_spec_d", it.ndtAuxiliaryHookSpecificationD)
    addCheckItem(ndeCategory, "auxHook_spec_e", it.ndtAuxiliaryHookSpecificationE)
    addCheckItem(ndeCategory, "auxHook_spec_f", it.ndtAuxiliaryHookSpecificationF)
    addCheckItem(ndeCategory, "auxHook_spec_g", it.ndtAuxiliaryHookSpecificationG)
    addCheckItem(ndeCategory, "auxHook_spec_h", it.ndtAuxiliaryHookSpecificationH)
    addResultStatusCheckItem(ndeCategory, "auxHook_spec_result", it.ndtAuxiliaryHookSpecificationResult)
    addCheckItem(ndeCategory, "auxHook_measure_a", it.ndtAuxiliaryHookMeasurementResultsA)
    addCheckItem(ndeCategory, "auxHook_measure_b", it.ndtAuxiliaryHookMeasurementResultsB)
    addCheckItem(ndeCategory, "auxHook_measure_c", it.ndtAuxiliaryHookMeasurementResultsC)
    addCheckItem(ndeCategory, "auxHook_measure_d", it.ndtAuxiliaryHookMeasurementResultsD)
    addCheckItem(ndeCategory, "auxHook_measure_e", it.ndtAuxiliaryHookMeasurementResultsE)
    addCheckItem(ndeCategory, "auxHook_measure_f", it.ndtAuxiliaryHookMeasurementResultsF)
    addCheckItem(ndeCategory, "auxHook_measure_g", it.ndtAuxiliaryHookMeasurementResultsG)
    addCheckItem(ndeCategory, "auxHook_measure_h", it.ndtAuxiliaryHookMeasurementResultsH)
    addResultStatusCheckItem(ndeCategory, "auxHook_measure_result", it.ndtAuxiliaryHookMeasurementResultsResult)
    addCheckItem(ndeCategory, "auxHook_tolerance_a", it.ndtAuxiliaryHookToleranceMeasureA)
    addCheckItem(ndeCategory, "auxHook_tolerance_b", it.ndtAuxiliaryHookToleranceMeasureB)
    addCheckItem(ndeCategory, "auxHook_tolerance_c", it.ndtAuxiliaryHookToleranceMeasureC)
    addCheckItem(ndeCategory, "auxHook_tolerance_d", it.ndtAuxiliaryHookToleranceMeasureD)
    addCheckItem(ndeCategory, "auxHook_tolerance_e", it.ndtAuxiliaryHookToleranceMeasureE)
    addCheckItem(ndeCategory, "auxHook_tolerance_f", it.ndtAuxiliaryHookToleranceMeasureF)
    addCheckItem(ndeCategory, "auxHook_tolerance_g", it.ndtAuxiliaryHookToleranceMeasureG)
    addCheckItem(ndeCategory, "auxHook_tolerance_h", it.ndtAuxiliaryHookToleranceMeasureH)
    addResultStatusCheckItem(ndeCategory, "auxHook_tolerance_result", it.ndtAuxiliaryHookToleranceMeasureResult)

    addCheckItem(ndeCategory, "mainDrum_ndtType", it.ndtMainDrumNdtType)
    addCheckItem(ndeCategory, "mainDrum_spec_a", it.ndtMainDrumSpecificationA)
    addCheckItem(ndeCategory, "mainDrum_spec_b", it.ndtMainDrumSpecificationB)
    addCheckItem(ndeCategory, "mainDrum_spec_c", it.ndtMainDrumSpecificationC)
    addCheckItem(ndeCategory, "mainDrum_spec_d", it.ndtMainDrumSpecificationD)
    addCheckItem(ndeCategory, "mainDrum_spec_e", it.ndtMainDrumSpecificationE)
    addCheckItem(ndeCategory, "mainDrum_spec_f", it.ndtMainDrumSpecificationF)
    addCheckItem(ndeCategory, "mainDrum_spec_g", it.ndtMainDrumSpecificationG)
    addResultStatusCheckItem(ndeCategory, "mainDrum_spec_result", it.ndtMainDrumSpecificationResult)
    addCheckItem(ndeCategory, "mainDrum_measure_a", it.ndtMainDrumMeasurementResultsA)
    addCheckItem(ndeCategory, "mainDrum_measure_b", it.ndtMainDrumMeasurementResultsB)
    addCheckItem(ndeCategory, "mainDrum_measure_c", it.ndtMainDrumMeasurementResultsC)
    addCheckItem(ndeCategory, "mainDrum_measure_d", it.ndtMainDrumMeasurementResultsD)
    addCheckItem(ndeCategory, "mainDrum_measure_e", it.ndtMainDrumMeasurementResultsE)
    addCheckItem(ndeCategory, "mainDrum_measure_f", it.ndtMainDrumMeasurementResultsF)
    addCheckItem(ndeCategory, "mainDrum_measure_g", it.ndtMainDrumMeasurementResultsG)
    addResultStatusCheckItem(ndeCategory, "mainDrum_measure_result", it.ndtMainDrumMeasurementResultsResult)

    addCheckItem(ndeCategory, "auxDrum_ndtType", it.ndtAuxiliaryDrumNdtType)
    addCheckItem(ndeCategory, "auxDrum_spec_a", it.ndtAuxiliaryDrumSpecificationA)
    addCheckItem(ndeCategory, "auxDrum_spec_b", it.ndtAuxiliaryDrumSpecificationB)
    addCheckItem(ndeCategory, "auxDrum_spec_c", it.ndtAuxiliaryDrumSpecificationC)
    addCheckItem(ndeCategory, "auxDrum_spec_d", it.ndtAuxiliaryDrumSpecificationD)
    addCheckItem(ndeCategory, "auxDrum_spec_e", it.ndtAuxiliaryDrumSpecificationE)
    addCheckItem(ndeCategory, "auxDrum_spec_f", it.ndtAuxiliaryDrumSpecificationF)
    addCheckItem(ndeCategory, "auxDrum_spec_g", it.ndtAuxiliaryDrumSpecificationG)
    addResultStatusCheckItem(ndeCategory, "auxDrum_spec_result", it.ndtAuxiliaryDrumSpecificationResult)
    addCheckItem(ndeCategory, "auxDrum_measure_a", it.ndtAuxiliaryDrumMeasurementResultsA)
    addCheckItem(ndeCategory, "auxDrum_measure_b", it.ndtAuxiliaryDrumMeasurementResultsB)
    addCheckItem(ndeCategory, "auxDrum_measure_c", it.ndtAuxiliaryDrumMeasurementResultsC)
    addCheckItem(ndeCategory, "auxDrum_measure_d", it.ndtAuxiliaryDrumMeasurementResultsD)
    addCheckItem(ndeCategory, "auxDrum_measure_e", it.ndtAuxiliaryDrumMeasurementResultsE)
    addCheckItem(ndeCategory, "auxDrum_measure_f", it.ndtAuxiliaryDrumMeasurementResultsF)
    addCheckItem(ndeCategory, "auxDrum_measure_g", it.ndtAuxiliaryDrumMeasurementResultsG)
    addResultStatusCheckItem(ndeCategory, "auxDrum_measure_result", it.ndtAuxiliaryDrumMeasurementResultsResult)

    addCheckItem(ndeCategory, "mainPulley_spec_a", it.ndtMainPulleySpecificationA)
    addCheckItem(ndeCategory, "mainPulley_spec_b", it.ndtMainPulleySpecificationB)
    addCheckItem(ndeCategory, "mainPulley_spec_c", it.ndtMainPulleySpecificationC)
    addCheckItem(ndeCategory, "mainPulley_spec_d", it.ndtMainPulleySpecificationD)
    addCheckItem(ndeCategory, "mainPulley_spec_e", it.ndtMainPulleySpecificationE)
    addResultStatusCheckItem(ndeCategory, "mainPulley_spec_result", it.ndtMainPulleySpecificationResult)
    addCheckItem(ndeCategory, "mainPulley_measure_a", it.ndtMainPulleyMeasurementResultsA)
    addCheckItem(ndeCategory, "mainPulley_measure_b", it.ndtMainPulleyMeasurementResultsB)
    addCheckItem(ndeCategory, "mainPulley_measure_c", it.ndtMainPulleyMeasurementResultsC)
    addCheckItem(ndeCategory, "mainPulley_measure_d", it.ndtMainPulleyMeasurementResultsD)
    addCheckItem(ndeCategory, "mainPulley_measure_e", it.ndtMainPulleyMeasurementResultsE)
    addResultStatusCheckItem(ndeCategory, "mainPulley_measure_result", it.ndtMainPulleyMeasurementResultsResult)

    addCheckItem(ndeCategory, "auxPulley_spec_a", it.ndtAuxiliaryPulleySpecificationA)
    addCheckItem(ndeCategory, "auxPulley_spec_b", it.ndtAuxiliaryPulleySpecificationB)
    addCheckItem(ndeCategory, "auxPulley_spec_c", it.ndtAuxiliaryPulleySpecificationC)
    addCheckItem(ndeCategory, "auxPulley_spec_d", it.ndtAuxiliaryPulleySpecificationD)
    addCheckItem(ndeCategory, "auxPulley_spec_e", it.ndtAuxiliaryPulleySpecificationE)
    addResultStatusCheckItem(ndeCategory, "auxPulley_spec_result", it.ndtAuxiliaryPulleySpecificationResult)
    addCheckItem(ndeCategory, "auxPulley_measure_a", it.ndtAuxiliaryPulleyMeasurementResultsA)
    addCheckItem(ndeCategory, "auxPulley_measure_b", it.ndtAuxiliaryPulleyMeasurementResultsB)
    addCheckItem(ndeCategory, "auxPulley_measure_c", it.ndtAuxiliaryPulleyMeasurementResultsC)
    addCheckItem(ndeCategory, "auxPulley_measure_d", it.ndtAuxiliaryPulleyMeasurementResultsD)
    addCheckItem(ndeCategory, "auxPulley_measure_e", it.ndtAuxiliaryPulleyMeasurementResultsE)
    addResultStatusCheckItem(ndeCategory, "auxPulley_measure_result", it.ndtAuxiliaryPulleyMeasurementResultsResult)


    // --- Testing ---
    val funcTestCategory = "testing_function"
    addResultStatusCheckItem(funcTestCategory, "hoistingLowering", it.testingFunctionHoistingLoweringResult)
    addResultStatusCheckItem(funcTestCategory, "extendedRetractedBoom", it.testingFunctionExtendedRectractedBoomResult)
    addResultStatusCheckItem(funcTestCategory, "extendedRetractedOutrigger", it.testingFunctionExtendedRectractedOutriggerResult)
    addResultStatusCheckItem(funcTestCategory, "swingSlewing", it.testingFunctionSwingSlewingResult)
    addResultStatusCheckItem(funcTestCategory, "antiTwoBlock", it.testingFunctionAntiTwoBlockResult)
    addResultStatusCheckItem(funcTestCategory, "boomStop", it.testingFunctionBoomStopResult)
    addResultStatusCheckItem(funcTestCategory, "anemometerWindSpeed", it.testingFunctionAnemometerWindSpeedResult)
    addResultStatusCheckItem(funcTestCategory, "brakeLockingDevice", it.testingFunctionBrakeLockingDeviceResult)
    addResultStatusCheckItem(funcTestCategory, "loadMomentIndicator", it.testingFunctionLoadMomentIndicatorResult)
    addResultStatusCheckItem(funcTestCategory, "turnSignal", it.testingFunctionTurnSignalResult)
    addResultStatusCheckItem(funcTestCategory, "drivingLights", it.testingFunctionDrivingLightsResult)
    addResultStatusCheckItem(funcTestCategory, "loadIndicatorLight", it.testingFunctionLoadIndicatorLightResult)
    addResultStatusCheckItem(funcTestCategory, "rotaryLamp", it.testingFunctionRotaryLampResult)
    addResultStatusCheckItem(funcTestCategory, "horn", it.testingFunctionHornResult)
    addResultStatusCheckItem(funcTestCategory, "swingAlarm", it.testingFunctionSwingAlarmResult)
    addResultStatusCheckItem(funcTestCategory, "reverseAlarm", it.testingFunctionReverseAlarmResult)
    addResultStatusCheckItem(funcTestCategory, "overloadAlarm", it.testingFunctionOverloadAlarmResult)

    it.dynamicMainHookTests.forEachIndexed { index, test ->
        val cat = "testing_load_dynamic_main_item_$index"
        addCheckItem(cat, "boomLength", test.boomLength)
        addCheckItem(cat, "radius", test.radius)
        addCheckItem(cat, "boomAngle", test.boomAngle)
        addCheckItem(cat, "testLoadKg", test.testLoad)
        addCheckItem(cat, "safeWorkingLoadKg", test.safeWorkingLoad)
        addCheckItem(cat, "result", test.result)
    }
    it.dynamicAuxiliaryHookTests.forEachIndexed { index, test ->
        val cat = "testing_load_dynamic_aux_item_$index"
        addCheckItem(cat, "boomLength", test.boomLength)
        addCheckItem(cat, "radius", test.radius)
        addCheckItem(cat, "boomAngle", test.boomAngle)
        addCheckItem(cat, "testLoadKg", test.testLoad)
        addCheckItem(cat, "safeWorkingLoadKg", test.safeWorkingLoad)
        addCheckItem(cat, "result", test.result)
    }
    it.staticMainHookTests.forEachIndexed { index, test ->
        val cat = "testing_load_static_main_item_$index"
        addCheckItem(cat, "boomLength", test.boomLength)
        addCheckItem(cat, "radius", test.radius)
        addCheckItem(cat, "boomAngle", test.boomAngle)
        addCheckItem(cat, "testLoadKg", test.testLoad)
        addCheckItem(cat, "safeWorkingLoadKg", test.safeWorkingLoad)
        addCheckItem(cat, "result", test.result)
    }
    it.staticAuxiliaryHookTests.forEachIndexed { index, test ->
        val cat = "testing_load_static_aux_item_$index"
        addCheckItem(cat, "boomLength", test.boomLength)
        addCheckItem(cat, "radius", test.radius)
        addCheckItem(cat, "boomAngle", test.boomAngle)
        addCheckItem(cat, "testLoadKg", test.testLoad)
        addCheckItem(cat, "safeWorkingLoadKg", test.safeWorkingLoad)
        addCheckItem(cat, "result", test.result)
    }


    // --- Findings ---
    val findings = mutableListOf<InspectionFindingDomain>()
    if (this.conclusion.isNotBlank()) {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = this.conclusion, type = FindingType.FINDING))
    }
    if (this.recommendation.isNotBlank()) {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = this.recommendation, type = FindingType.RECOMMENDATION))
    }


    // --- Main Inspection Domain Object ---
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id,
        moreExtraId = "", // Not present in DTO
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Mobile_Crane,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.unitLocation,
        addressUsageLocation = this.generalData.userAddress,
        // FIXED: Acknowledging data loss as DTO doesn't contain this field.
        driveType = "", // This data is lost because it's not present in the MobileCraneReportData DTO.
        serialNumber = this.generalData.serialNumberUnitNumber,
        permitNumber = this.generalData.usagePermitNumber,
        capacity = this.generalData.capacityWorkingLoad,
        manufacturer = ManufacturerDomain(
            name = this.generalData.manufacturer,
            brandOrType = this.generalData.brandType,
            year = this.generalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.generalData.inspectionDate,
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

fun InspectionWithDetailsDomain.toMobileCraneReportRequest(): MobileCraneReportRequest {
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getCheckItemValue(category: String, itemName: String, default: String = ""): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: default
    }

    fun getResultStatus(category: String, itemName: String): ResultStatus {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return ResultStatus(status = item?.status ?: false, result = item?.result ?: "")
    }

    // --- General Data ---
    val generalData = MobileCraneGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        ownerAddress = this.inspection.ownerAddress ?: "",
        userSubcontractorPersonInCharge = getCheckItemValue("general_data", "personInCharge"),
        userAddress = this.inspection.addressUsageLocation ?: "",
        unitLocation = this.inspection.usageLocation ?: "",
        operatorName = getCheckItemValue("general_data", "operatorName"),
        manufacturer = this.inspection.manufacturer?.name ?: "",
        brandType = this.inspection.manufacturer?.brandOrType ?: "",
        locationAndYearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = this.inspection.serialNumber ?: "",
        capacityWorkingLoad = this.inspection.capacity ?: "",
        intendedUse = getCheckItemValue("general_data", "intendedUse"),
        usagePermitNumber = this.inspection.permitNumber ?: "",
        operatorCertificate = getCheckItemValue("general_data", "operatorCertificate"),
        equipmentHistory = getCheckItemValue("general_data", "equipmentHistory"),
        inspectionDate = this.inspection.reportDate ?: ""
        // NOTE: `driveType` from domain is lost here because the Request DTO does not have a field for it.
        // To fix this, `driveType` must be added to `MobileCraneGeneralData` in `MobileCraneReportDto.kt`.
    )

    // --- Technical Data ---
    val techCategory = "technical_data"
    val technicalData = MobileCraneTechnicalData(
        maximumWorkingLoadCapacity = getCheckItemValue(techCategory, "spec_maximumWorkingLoadCapacity"),
        boomLength = getCheckItemValue(techCategory, "spec_boomLength"),
        maximumJibLength = getCheckItemValue(techCategory, "spec_maximumJibLength"),
        maximumJibWorkingLoad = getCheckItemValue(techCategory, "spec_maximumJibWorkingLoad"),
        maxBoomJibLength = getCheckItemValue(techCategory, "spec_maxBoomJibLength"),
        craneWeight = getCheckItemValue(techCategory, "spec_craneWeight"),
        maxLiftingHeight = getCheckItemValue(techCategory, "spec_maxLiftingHeight"),
        boomWorkingAngle = getCheckItemValue(techCategory, "spec_boomWorkingAngle"),
        engineNumber = getCheckItemValue(techCategory, "motor_engineNumber"),
        engineType = getCheckItemValue(techCategory, "motor_type"),
        numberOfCylinders = getCheckItemValue(techCategory, "motor_numberOfCylinders"),
        netPower = getCheckItemValue(techCategory, "motor_netPower"),
        brandYearOfManufacture = getCheckItemValue(techCategory, "motor_brandYearOfManufacture"),
        // NOTE: `hookManufacturer` is being mapped from the `motor_manufacturer` check item.
        // This assumes the API intends for the motor's manufacturer to be placed here.
        hookManufacturer = getCheckItemValue(techCategory, "motor_manufacturer"),
        mainHookType = getCheckItemValue(techCategory, "mainHook_type"),
        mainHookCapacity = getCheckItemValue(techCategory, "mainHook_capacity"),
        mainHookMaterial = getCheckItemValue(techCategory, "mainHook_material"),
        mainHookSerialNumber = getCheckItemValue(techCategory, "mainHook_serialNumber"),
        auxiliaryHookType = getCheckItemValue(techCategory, "auxHook_type"),
        auxiliaryHookCapacity = getCheckItemValue(techCategory, "auxHook_capacity"),
        auxiliaryHookMaterial = getCheckItemValue(techCategory, "auxHook_material"),
        auxiliaryHookSerialNumber = getCheckItemValue(techCategory, "auxHook_serialNumber"),
        wireRopeMainLoadHoistDrumDiameter = getCheckItemValue(techCategory, "rope_main_diameter"),
        wireRopeMainLoadHoistDrumType = getCheckItemValue(techCategory, "rope_main_type"),
        wireRopeMainLoadHoistDrumConstruction = getCheckItemValue(techCategory, "rope_main_construction"),
        wireRopeMainLoadHoistDrumBreakingStrength = getCheckItemValue(techCategory, "rope_main_breakingStrength"),
        wireRopeMainLoadHoistDrumLength = getCheckItemValue(techCategory, "rope_main_length"),
        wireRopeAuxiliaryLoadHoistDrumDiameter = getCheckItemValue(techCategory, "rope_aux_diameter"),
        wireRopeAuxiliaryLoadHoistDrumType = getCheckItemValue(techCategory, "rope_aux_type"),
        wireRopeAuxiliaryLoadHoistDrumConstruction = getCheckItemValue(techCategory, "rope_aux_construction"),
        wireRopeAuxiliaryLoadHoistDrumBreakingStrength = getCheckItemValue(techCategory, "rope_aux_breakingStrength"),
        wireRopeAuxiliaryLoadHoistDrumLength = getCheckItemValue(techCategory, "rope_aux_length"),
        wireRopeBoomHoistDrumDiameter = getCheckItemValue(techCategory, "rope_boom_diameter"),
        wireRopeBoomHoistDrumType = getCheckItemValue(techCategory, "rope_boom_type"),
        wireRopeBoomHoistDrumConstruction = getCheckItemValue(techCategory, "rope_boom_construction"),
        wireRopeBoomHoistDrumBreakingStrength = getCheckItemValue(techCategory, "rope_boom_breakingStrength"),
        wireRopeBoomHoistDrumLength = getCheckItemValue(techCategory, "rope_boom_length")
    )

    // --- Inspection and Testing ---
    val visualCategory = "visual_inspection"
    val ndeCategory = "nde"
    val funcTestCategory = "testing_function"
    val inspectionAndTesting = MobileCraneInspectionAndTesting(
        visualFoundationAndBoltsCorrosionResult = getResultStatus(visualCategory, "foundationAnchorBoltCorrosion"),
        visualFoundationAndBoltsCracksResult = getResultStatus(visualCategory, "foundationAnchorBoltCracks"),
        visualFoundationAndBoltsDeformationResult = getResultStatus(visualCategory, "foundationAnchorBoltDeformation"),
        visualFoundationAndBoltsTightnessResult = getResultStatus(visualCategory, "foundationAnchorBoltTightness"),
        visualFrameColumnsOnFoundationCorrosionResult = getResultStatus(visualCategory, "frameColumnsOnFoundationCorrosion"),
        visualFrameColumnsOnFoundationCracksResult = getResultStatus(visualCategory, "frameColumnsOnFoundationCracks"),
        visualFrameColumnsOnFoundationDeformationResult = getResultStatus(visualCategory, "frameColumnsOnFoundationDeformation"),
        visualFrameColumnsOnFoundationFasteningResult = getResultStatus(visualCategory, "frameColumnsOnFoundationFastening"),
        visualFrameColumnsOnFoundationTransverseReinforcementResult = getResultStatus(visualCategory, "frameColumnsOnFoundationTransverseReinforcement"),
        visualFrameColumnsOnFoundationDiagonalReinforcementResult = getResultStatus(visualCategory, "frameColumnsOnFoundationDiagonalReinforcement"),
        visualLadderCorrosionResult = getResultStatus(visualCategory, "ladderCorrosion"),
        visualLadderCracksResult = getResultStatus(visualCategory, "ladderCracks"),
        visualLadderDeformationResult = getResultStatus(visualCategory, "ladderDeformation"),
        visualLadderFasteningResult = getResultStatus(visualCategory, "ladderFastening"),
        visualWorkingPlatformCorrosionResult = getResultStatus(visualCategory, "workingPlatformCorrosion"),
        visualWorkingPlatformCracksResult = getResultStatus(visualCategory, "workingPlatformCracks"),
        visualWorkingPlatformDeformationResult = getResultStatus(visualCategory, "workingPlatformDeformation"),
        visualWorkingPlatformFasteningResult = getResultStatus(visualCategory, "workingPlatformFastening"),
        visualOutriggersOutriggerArmHousingResult = getResultStatus(visualCategory, "outriggersOutriggerArmHousing"),
        visualOutriggersOutriggerArmsResult = getResultStatus(visualCategory, "outriggersOutriggerArms"),
        visualOutriggersJackResult = getResultStatus(visualCategory, "outriggersJack"),
        visualOutriggersOutriggerPadsResult = getResultStatus(visualCategory, "outriggersOutriggerPads"),
        visualOutriggersHousingConnectionToChassisResult = getResultStatus(visualCategory, "outriggersHousingConnectionToChassis"),
        visualOutriggersOutriggerSafetyLocksResult = getResultStatus(visualCategory, "outriggersOutriggerSafetyLocks"),
        visualTurntableSlewingRollerBearingResult = getResultStatus(visualCategory, "turntableSlewingRollerBearing"),
        visualTurntableBrakeHousingResult = getResultStatus(visualCategory, "turntableBrakeHousing"),
        visualTurntableBrakeLiningsAndShoesResult = getResultStatus(visualCategory, "turntableBrakeLiningsAndShoes"),
        visualTurntableDrumSurfaceResult = getResultStatus(visualCategory, "turntableDrumSurface"),
        visualTurntablePressureCylinderResult = getResultStatus(visualCategory, "turntablePressureCylinder"),
        visualTurntableDrumAxleResult = getResultStatus(visualCategory, "turntableDrumAxle"),
        visualTurntableLeversPinsBoltsResult = getResultStatus(visualCategory, "turntableLeversPinsBolts"),
        visualTurntableGuardResult = getResultStatus(visualCategory, "turntableGuard"),
        visualLatticeBoomMainBoomResult = getResultStatus(visualCategory, "latticeBoomMainBoom"),
        visualLatticeBoomBoomSectionResult = getResultStatus(visualCategory, "latticeBoomBoomSection"),
        visualLatticeBoomTopPulleyResult = getResultStatus(visualCategory, "latticeBoomTopPulley"),
        visualLatticeBoomPulleyGuardResult = getResultStatus(visualCategory, "latticeBoomPulleyGuard"),
        visualLatticeBoomWireRopeGuardResult = getResultStatus(visualCategory, "latticeBoomWireRopeGuard"),
        visualLatticeBoomPulleyGrooveLipResult = getResultStatus(visualCategory, "latticeBoomPulleyGrooveLip"),
        visualLatticeBoomPivotPinResult = getResultStatus(visualCategory, "latticeBoomPivotPin"),
        visualLatticeBoomWireRopeGuidePulleyResult = getResultStatus(visualCategory, "latticeBoomWireRopeGuidePulley"),
        visualSteeringMainClutchResult = getResultStatus(visualCategory, "clutchMainClutch"),
        visualTransmissionResult = getResultStatus(visualCategory, "transmission"),
        visualSteeringFrontWheelResult = getResultStatus(visualCategory, "steeringFrontWheel"),
        visualSteeringMiddleWheelResult = getResultStatus(visualCategory, "steeringMiddleWheel"),
        visualSteeringRearWheelResult = getResultStatus(visualCategory, "steeringRearWheel"),
        visualBrakeServiceBrakeResult = getResultStatus(visualCategory, "brakeServiceBrake"),
        visualBrakeParkingBrakeResult = getResultStatus(visualCategory, "brakeParkingBrake"),
        visualBrakeBrakeHousingResult = getResultStatus(visualCategory, "brakeBrakeHousing"),
        visualBrakeBrakeLiningsAndShoesResult = getResultStatus(visualCategory, "brakeBrakeLiningsAndShoes"),
        visualBrakeDrumSurfaceResult = getResultStatus(visualCategory, "brakeDrumSurface"),
        visualBrakeLeversPinsAndBoltsResult = getResultStatus(visualCategory, "brakeLeversPinsBolts"),
        visualBrakeGuardResult = getResultStatus(visualCategory, "brakeGuard"),
        visualTravelDrumClutchHousingResult = getResultStatus(visualCategory, "travelDrumClutchHousing"),
        visualTravelDrumClutchLiningResult = getResultStatus(visualCategory, "travelDrumClutchLining"),
        visualTravelDrumClutchDrumSurfaceResult = getResultStatus(visualCategory, "travelDrumClutchDrumSurface"),
        visualTravelDrumLeversPinsAndBoltsResult = getResultStatus(visualCategory, "travelDrumLeversPinsBolts"),
        visualTravelDrumGuardResult = getResultStatus(visualCategory, "travelDrumGuard"),
        visualMainWinchDrumMountingResult = getResultStatus(visualCategory, "mainWinchDrumMounting"),
        visualMainWinchWindingDrumSurfaceResult = getResultStatus(visualCategory, "mainWinchWindingDrumSurface"),
        visualMainWinchBrakeLiningsAndShoesResult = getResultStatus(visualCategory, "mainWinchBrakeLiningsAndShoes"),
        visualMainWinchBrakeDrumSurfaceResult = getResultStatus(visualCategory, "mainWinchBrakeDrumSurface"),
        visualMainWinchBrakeHousingResult = getResultStatus(visualCategory, "mainWinchBrakeHousing"),
        visualMainWinchClutchLiningsAndShoesResult = getResultStatus(visualCategory, "mainWinchClutchLiningsAndShoes"),
        visualMainWinchClutchDrumSurfaceResult = getResultStatus(visualCategory, "mainWinchClutchDrumSurface"),
        visualMainWinchGrooveResult = getResultStatus(visualCategory, "mainWinchGroove"),
        visualMainWinchGrooveLipResult = getResultStatus(visualCategory, "mainWinchGrooveLip"),
        visualMainWinchFlangesResult = getResultStatus(visualCategory, "mainWinchFlanges"),
        visualMainWinchBrakeActuatorLeversPinsAndBoltsResult = getResultStatus(visualCategory, "mainWinchBrakeActuatorLeversPinsAndBolts"),
        visualAuxiliaryWinchDrumMountingResult = getResultStatus(visualCategory, "auxiliaryWinchDrumMounting"),
        visualAuxiliaryWinchWindingDrumSurfaceResult = getResultStatus(visualCategory, "auxiliaryWinchWindingDrumSurface"),
        visualAuxiliaryWinchBrakeLiningsAndShoesResult = getResultStatus(visualCategory, "auxiliaryWinchBrakeLiningsAndShoes"),
        visualAuxiliaryWinchBrakeDrumSurfaceResult = getResultStatus(visualCategory, "auxiliaryWinchBrakeDrumSurface"),
        visualAuxiliaryWinchBrakeHousingResult = getResultStatus(visualCategory, "auxiliaryWinchBrakeHousing"),
        visualAuxiliaryWinchClutchLiningsAndShoesResult = getResultStatus(visualCategory, "auxiliaryWinchClutchLiningsAndShoes"),
        visualAuxiliaryWinchClutchDrumSurfaceResult = getResultStatus(visualCategory, "auxiliaryWinchClutchDrumSurface"),
        visualAuxiliaryWinchGrooveResult = getResultStatus(visualCategory, "auxiliaryWinchGroove"),
        visualAuxiliaryWinchGrooveLipResult = getResultStatus(visualCategory, "auxiliaryWinchGrooveLip"),
        visualAuxiliaryWinchFlangesResult = getResultStatus(visualCategory, "auxiliaryWinchFlanges"),
        visualAuxiliaryWinchBrakeActuatorLeversPinsAndBoltsResult = getResultStatus(visualCategory, "auxiliaryWinchBrakeActuatorLeversPinsAndBolts"),
        visualHoistGearBlockLubricationResult = getResultStatus(visualCategory, "hoistGearBlockLubrication"),
        visualHoistGearBlockOilSealResult = getResultStatus(visualCategory, "hoistGearBlockOilSeal"),
        visualMainPulleyPulleyGrooveResult = getResultStatus(visualCategory, "mainPulleyPulleyGroove"),
        visualMainPulleyPulleyGrooveLipResult = getResultStatus(visualCategory, "mainPulleyPulleyGrooveLip"),
        visualMainPulleyPulleyPinResult = getResultStatus(visualCategory, "mainPulleyPulleyPin"),
        visualMainPulleyBearingResult = getResultStatus(visualCategory, "mainPulleyBearing"),
        visualMainPulleyPulleyGuardResult = getResultStatus(visualCategory, "mainPulleyPulleyGuard"),
        visualMainPulleyWireRopeGuardResult = getResultStatus(visualCategory, "mainPulleyWireRopeGuard"),
        visualMainHookSwivelNutAndBearingResult = getResultStatus(visualCategory, "mainHookVisualSwivelNutAndBearing"),
        visualMainHookTrunnionResult = getResultStatus(visualCategory, "mainHookVisualTrunnion"),
        visualMainHookSafetyLatchResult = getResultStatus(visualCategory, "mainHookVisualSafetyLatch"),
        visualAuxiliaryHookFreeFallWeightResult = getResultStatus(visualCategory, "auxiliaryHookVisualFreeFallWeight"),
        visualAuxiliaryHookSwivelNutAndBearingResult = getResultStatus(visualCategory, "auxiliaryHookVisualSwivelNutAndBearing"),
        visualAuxiliaryHookSafetyLatchResult = getResultStatus(visualCategory, "auxiliaryHookVisualSafetyLatch"),
        visualMainWireRopeCorrosionResult = getResultStatus(visualCategory, "mainWireRopeVisualCorrosion"),
        visualMainWireRopeWearResult = getResultStatus(visualCategory, "mainWireRopeVisualWear"),
        visualMainWireRopeBreakageResult = getResultStatus(visualCategory, "mainWireRopeVisualBreakage"),
        visualMainWireRopeDeformationResult = getResultStatus(visualCategory, "mainWireRopeVisualDeformation"),
        visualAuxiliaryWireRopeCorrosionResult = getResultStatus(visualCategory, "auxiliaryWireRopeVisualCorrosion"),
        visualAuxiliaryWireRopeWearResult = getResultStatus(visualCategory, "auxiliaryWireRopeVisualWear"),
        visualAuxiliaryWireRopeBreakageResult = getResultStatus(visualCategory, "auxiliaryWireRopeVisualBreakage"),
        visualAuxiliaryWireRopeDeformationResult = getResultStatus(visualCategory, "auxiliaryWireRopeVisualDeformation"),
        visualLimitSwitchLongTravelResult = getResultStatus(visualCategory, "limitSwitchLsLongTravel"),
        visualLimitSwitchCrossTravelResult = getResultStatus(visualCategory, "limitSwitchLsCrossTravel"),
        visualLimitSwitchHoistingResult = getResultStatus(visualCategory, "limitSwitchLsHoisting"),
        visualInternalCombustionEngineCoolingSystemResult = getResultStatus(visualCategory, "internalCombustionEngineCoolingSystem"),
        visualInternalCombustionEngineLubricationSystemResult = getResultStatus(visualCategory, "internalCombustionEngineLubricationSystem"),
        visualInternalCombustionEngineEngineMountingResult = getResultStatus(visualCategory, "internalCombustionEngineEngineMounting"),
        visualInternalCombustionEngineSafetyGuardEquipmentResult = getResultStatus(visualCategory, "internalCombustionEngineSafetyGuardEquipment"),
        visualInternalCombustionEngineExhaustSystemResult = getResultStatus(visualCategory, "internalCombustionEngineExhaustSystem"),
        visualInternalCombustionEngineFuelSystemResult = getResultStatus(visualCategory, "internalCombustionEngineFuelSystem"),
        visualInternalCombustionEnginePowerTransmissionSystemResult = getResultStatus(visualCategory, "internalCombustionEnginePowerTransmissionSystem"),
        visualInternalCombustionEngineBatteryResult = getResultStatus(visualCategory, "internalCombustionEngineBattery"),
        visualInternalCombustionEngineStarterMotorResult = getResultStatus(visualCategory, "internalCombustionEngineStarterMotor"),
        visualInternalCombustionEngineWiringInstallationResult = getResultStatus(visualCategory, "internalCombustionEngineWiringInstallation"),
        visualInternalCombustionEngineTurbochargerResult = getResultStatus(visualCategory, "internalCombustionEngineTurbocharger"),
        visualHydraulicPumpResult = getResultStatus(visualCategory, "hydraulicHydraulicPump"),
        visualHydraulicLinesResult = getResultStatus(visualCategory, "hydraulicHydraulicLines"),
        visualHydraulicFilterResult = getResultStatus(visualCategory, "hydraulicHydraulicFilter"),
        visualHydraulicTankResult = getResultStatus(visualCategory, "hydraulicHydraulicTank"),
        visualHydraulicMainWinchMotorResult = getResultStatus(visualCategory, "hydraulicMotorMainWinchMotor"),
        visualHydraulicAuxiliaryWinchMotorResult = getResultStatus(visualCategory, "hydraulicMotorAuxiliaryWinchMotor"),
        visualHydraulicBoomWinchMotorResult = getResultStatus(visualCategory, "hydraulicMotorBoomWinchMotor"),
        visualHydraulicSwingMotorResult = getResultStatus(visualCategory, "hydraulicMotorSwingMotor"),
        visualControlValveReliefValveResult = getResultStatus(visualCategory, "controlValveReliefValve"),
        visualControlValveMainWinchValveResult = getResultStatus(visualCategory, "controlValveMainWinchValve"),
        visualControlValveAuxiliaryWinchValveResult = getResultStatus(visualCategory, "controlValveAuxiliaryWinchValve"),
        visualControlValveBoomWinchValveResult = getResultStatus(visualCategory, "controlValveBoomWinchValve"),
        visualControlValveBoomMovementValveResult = getResultStatus(visualCategory, "controlValveBoomMovementValve"),
        visualControlValveSteeringCylinderValveResult = getResultStatus(visualCategory, "controlValveSteeringCylinderValve"),
        visualControlValveAxleOscillationValveResult = getResultStatus(visualCategory, "controlValveAxleOscillationValve"),
        visualControlValveOutriggerMovementValveResult = getResultStatus(visualCategory, "controlValveOutriggerMovementValve"),
        visualHydraulicCylinderBoomMovementCylinderResult = getResultStatus(visualCategory, "hydraulicCylinderBoomMovementCylinder"),
        visualHydraulicCylinderOutriggerCylinderResult = getResultStatus(visualCategory, "hydraulicCylinderOutriggerCylinder"),
        visualHydraulicCylinderSteeringWheelCylinderResult = getResultStatus(visualCategory, "hydraulicCylinderSteeringWheelCylinder"),
        visualHydraulicCylinderAxleOscillationCylinderResult = getResultStatus(visualCategory, "hydraulicCylinderAxleOscillationCylinder"),
        visualHydraulicCylinderTelescopicCylinderResult = getResultStatus(visualCategory, "hydraulicCylinderTelescopicCylinder"),
        visualPneumaticCompressorResult = getResultStatus(visualCategory, "pneumaticCompressor"),
        visualPneumaticTankAndSafetyValveResult = getResultStatus(visualCategory, "pneumaticTankAndSafetyValve"),
        visualPneumaticPressurizedAirLinesResult = getResultStatus(visualCategory, "pneumaticPressurizedAirLines"),
        visualPneumaticAirFilterResult = getResultStatus(visualCategory, "pneumaticAirFilter"),
        visualPneumaticControlValveResult = getResultStatus(visualCategory, "pneumaticControlValve"),
        visualOperatorCabinSafetyLadderResult = getResultStatus(visualCategory, "operatorCabinSafetyLadder"),
        visualOperatorCabinDoorResult = getResultStatus(visualCategory, "operatorCabinDoor"),
        visualOperatorCabinWindowResult = getResultStatus(visualCategory, "operatorCabinWindow"),
        visualOperatorCabinFanAcResult = getResultStatus(visualCategory, "operatorCabinFanAc"),
        visualOperatorCabinControlLeversButtonsResult = getResultStatus(visualCategory, "operatorCabinControlLeversButtons"),
        visualOperatorCabinPendantControlResult = getResultStatus(visualCategory, "operatorCabinPendantControl"),
        visualOperatorCabinLightingResult = getResultStatus(visualCategory, "operatorCabinLighting"),
        visualOperatorCabinHornSignalAlarmResult = getResultStatus(visualCategory, "operatorCabinHornSignalAlarm"),
        visualOperatorCabinFuseResult = getResultStatus(visualCategory, "operatorCabinFuse"),
        visualOperatorCabinCommunicationDeviceResult = getResultStatus(visualCategory, "operatorCabinCommunicationDevice"),
        visualOperatorCabinFireExtinguisherResult = getResultStatus(visualCategory, "operatorCabinFireExtinguisher"),
        visualOperatorCabinOperatingSignsResult = getResultStatus(visualCategory, "operatorCabinOperatingSigns"),
        visualOperatorCabinIgnitionKeyMasterSwitchResult = getResultStatus(visualCategory, "operatorCabinIgnitionKeyMasterSwitch"),
        visualOperatorCabinButtonsHandlesLeversResult = getResultStatus(visualCategory, "operatorCabinButtonsHandlesLevers"),
        visualElectricalComponentsPanelConductorConnectorResult = getResultStatus(visualCategory, "electricalComponentsPanelConductorConnector"),
        visualElectricalComponentsConductorProtectionResult = getResultStatus(visualCategory, "electricalComponentsConductorProtection"),
        visualElectricalComponentsMotorInstallationSafetySystemResult = getResultStatus(visualCategory, "electricalComponentsMotorInstallationSafetySystem"),
        visualElectricalComponentsGroundingSystemResult = getResultStatus(visualCategory, "electricalComponentsGroundingSystem"),
        visualElectricalComponentsInstallationResult = getResultStatus(visualCategory, "electricalComponentsInstallation"),
        visualSafetyDevicesLadderHandrailResult = getResultStatus(visualCategory, "safetyDevicesLadderHandrail"),
        visualSafetyDevicesEngineOilLubricantPressureResult = getResultStatus(visualCategory, "safetyDevicesEngineOilLubricantPressure"),
        visualSafetyDevicesHydraulicOilPressureResult = getResultStatus(visualCategory, "safetyDevicesHydraulicOilPressure"),
        visualSafetyDevicesAirPressureResult = getResultStatus(visualCategory, "safetyDevicesAirPressure"),
        visualSafetyDevicesAmperemeterResult = getResultStatus(visualCategory, "safetyDevicesAmperemeter"),
        visualSafetyDevicesVoltageResult = getResultStatus(visualCategory, "safetyDevicesVoltage"),
        visualSafetyDevicesEngineTemperatureResult = getResultStatus(visualCategory, "safetyDevicesEngineTemperature"),
        visualSafetyDevicesTransmissionTemperatureResult = getResultStatus(visualCategory, "safetyDevicesTransmissionTemperature"),
        visualSafetyDevicesConverterOilTemperaturePressureResult = getResultStatus(visualCategory, "safetyDevicesConverterOilTemperaturePressure"),
        visualSafetyDevicesConverterSpeedometerIndicatorResult = getResultStatus(visualCategory, "safetyDevicesSpeedometerIndicator"),
        visualSafetyDevicesConverterRotaryLampResult = getResultStatus(visualCategory, "safetyDevicesRotaryLamp"),
        visualSafetyDevicesConverterMainHoistRopeUpDownLimitResult = getResultStatus(visualCategory, "safetyDevicesMainHoistRopeUpDownLimit"),
        visualSafetyDevicesConverterAuxiliaryHoistRopeUpDownLimitResult = getResultStatus(visualCategory, "safetyDevicesAuxiliaryHoistRopeUpDownLimit"),
        visualSafetyDevicesConverterSwingMotionLimitResult = getResultStatus(visualCategory, "safetyDevicesSwingMotionLimit"),
        visualSafetyDevicesConverterLevelIndicatorResult = getResultStatus(visualCategory, "safetyDevicesLevelIndicator"),
        visualSafetyDevicesConverterLoadWeightIndicatorResult = getResultStatus(visualCategory, "safetyDevicesLoadWeightIndicator"),
        visualSafetyDevicesConverterLoadChartResult = getResultStatus(visualCategory, "safetyDevicesLoadChart"),
        visualSafetyDevicesConverterAnemometerWindSpeedResult = getResultStatus(visualCategory, "safetyDevicesAnemometerWindSpeed"),
        visualSafetyDevicesConverterBoomAngleIndicatorResult = getResultStatus(visualCategory, "safetyDevicesBoomAngleIndicator"),
        visualSafetyDevicesConverterAirPressureIndicatorResult = getResultStatus(visualCategory, "safetyDevicesAirPressureIndicator"),
        visualSafetyDevicesConverterHydraulicPressureIndicatorResult = getResultStatus(visualCategory, "safetyDevicesHydraulicPressureIndicator"),
        visualSafetyDevicesConverterSafetyValvesResult = getResultStatus(visualCategory, "safetyDevicesSafetyValves"),
        visualSafetyDevicesConverterMainWindingDrumSafetyLockResult = getResultStatus(visualCategory, "safetyDevicesMainWindingDrumSafetyLock"),
        visualSafetyDevicesConverterAuxiliaryWindingDrumSafetyLockResult = getResultStatus(visualCategory, "safetyDevicesAuxiliaryWindingDrumSafetyLock"),
        visualSafetyDevicesConverterTelescopicMotionLimitResult = getResultStatus(visualCategory, "safetyDevicesTelescopicMotionLimit"),
        visualSafetyDevicesConverterLightningArresterResult = getResultStatus(visualCategory, "safetyDevicesLightningArrester"),
        visualSafetyDevicesConverterLiftingHeightIndicatorResult = getResultStatus(visualCategory, "safetyDevicesLiftingHeightIndicator"),

        // --- NDE ---
        ndtWireRopeMainLoadSpecDiameter = getCheckItemValue(ndeCategory, "wireRope_main_specDiameter"),
        ndtWireRopeMainLoadActualDiameter = getCheckItemValue(ndeCategory, "wireRope_main_actualDiameter"),
        ndtWireRopeMainLoadConstruction = getCheckItemValue(ndeCategory, "wireRope_main_construction"),
        ndtWireRopeMainLoadType = getCheckItemValue(ndeCategory, "wireRope_main_type"),
        ndtWireRopeMainLoadLength = getCheckItemValue(ndeCategory, "wireRope_main_length"),
        ndtWireRopeMainLoadAge = getCheckItemValue(ndeCategory, "wireRope_main_age"),
        ndtWireRopeMainLoadResult = getResultStatus(ndeCategory, "wireRope_main_result"),
        ndtWireRopeAuxiliaryLoadSpecDiameter = getCheckItemValue(ndeCategory, "wireRope_aux_specDiameter"),
        ndtWireRopeAuxiliaryLoadActualDiameter = getCheckItemValue(ndeCategory, "wireRope_aux_actualDiameter"),
        ndtWireRopeAuxiliaryLoadConstruction = getCheckItemValue(ndeCategory, "wireRope_aux_construction"),
        ndtWireRopeAuxiliaryLoadType = getCheckItemValue(ndeCategory, "wireRope_aux_type"),
        ndtWireRopeAuxiliaryLoadLength = getCheckItemValue(ndeCategory, "wireRope_aux_length"),
        ndtWireRopeAuxiliaryLoadAge = getCheckItemValue(ndeCategory, "wireRope_aux_age"),
        ndtWireRopeAuxiliaryLoadResult = getResultStatus(ndeCategory, "wireRope_aux_result"),
        ndtWireRopeBoomHoistSpecDiameter = getCheckItemValue(ndeCategory, "wireRope_boom_specDiameter"),
        ndtWireRopeBoomHoistActualDiameter = getCheckItemValue(ndeCategory, "wireRope_boom_actualDiameter"),
        ndtWireRopeBoomHoistConstruction = getCheckItemValue(ndeCategory, "wireRope_boom_construction"),
        ndtWireRopeBoomHoistType = getCheckItemValue(ndeCategory, "wireRope_boom_type"),
        ndtWireRopeBoomHoistLength = getCheckItemValue(ndeCategory, "wireRope_boom_length"),
        ndtWireRopeBoomHoistAge = getCheckItemValue(ndeCategory, "wireRope_boom_age"),
        ndtWireRopeBoomHoistResult = getResultStatus(ndeCategory, "wireRope_boom_result"),
        ndtBoomType = getCheckItemValue(ndeCategory, "boom_type"),
        ndtBoomNdtType = getCheckItemValue(ndeCategory, "boom_ndtType"),
        ndtBoomInspection1Part = getCheckItemValue(ndeCategory, "boom_inspection1_part"),
        ndtBoomInspection1Location = getCheckItemValue(ndeCategory, "boom_inspection1_location"),
        ndtBoomInspection1Result = getResultStatus(ndeCategory, "boom_inspection1_result"),
        ndtBoomInspection2Part = getCheckItemValue(ndeCategory, "boom_inspection2_part"),
        ndtBoomInspection2Location = getCheckItemValue(ndeCategory, "boom_inspection2_location"),
        ndtBoomInspection2Result = getResultStatus(ndeCategory, "boom_inspection2_result"),
        ndtBoomInspection3Part = getCheckItemValue(ndeCategory, "boom_inspection3_part"),
        ndtBoomInspection3Location = getCheckItemValue(ndeCategory, "boom_inspection3_location"),
        ndtBoomInspection3Result = getResultStatus(ndeCategory, "boom_inspection3_result"),
        ndtMainHookNdtType = getCheckItemValue(ndeCategory, "mainHook_ndtType"),
        ndtMainHookSpecificationA = getCheckItemValue(ndeCategory, "mainHook_spec_a"),
        ndtMainHookSpecificationB = getCheckItemValue(ndeCategory, "mainHook_spec_b"),
        ndtMainHookSpecificationC = getCheckItemValue(ndeCategory, "mainHook_spec_c"),
        ndtMainHookSpecificationD = getCheckItemValue(ndeCategory, "mainHook_spec_d"),
        ndtMainHookSpecificationE = getCheckItemValue(ndeCategory, "mainHook_spec_e"),
        ndtMainHookSpecificationF = getCheckItemValue(ndeCategory, "mainHook_spec_f"),
        ndtMainHookSpecificationG = getCheckItemValue(ndeCategory, "mainHook_spec_g"),
        ndtMainHookSpecificationH = getCheckItemValue(ndeCategory, "mainHook_spec_h"),
        ndtMainHookSpecificationResult = getResultStatus(ndeCategory, "mainHook_spec_result"),
        ndtMainHookMeasurementResultsA = getCheckItemValue(ndeCategory, "mainHook_measure_a"),
        ndtMainHookMeasurementResultsB = getCheckItemValue(ndeCategory, "mainHook_measure_b"),
        ndtMainHookMeasurementResultsC = getCheckItemValue(ndeCategory, "mainHook_measure_c"),
        ndtMainHookMeasurementResultsD = getCheckItemValue(ndeCategory, "mainHook_measure_d"),
        ndtMainHookMeasurementResultsE = getCheckItemValue(ndeCategory, "mainHook_measure_e"),
        ndtMainHookMeasurementResultsF = getCheckItemValue(ndeCategory, "mainHook_measure_f"),
        ndtMainHookMeasurementResultsG = getCheckItemValue(ndeCategory, "mainHook_measure_g"),
        ndtMainHookMeasurementResultsH = getCheckItemValue(ndeCategory, "mainHook_measure_h"),
        ndtMainHookMeasurementResultsResult = getResultStatus(ndeCategory, "mainHook_measure_result"),
        ndtMainHookToleranceMeasureA = getCheckItemValue(ndeCategory, "mainHook_tolerance_a"),
        ndtMainHookToleranceMeasureB = getCheckItemValue(ndeCategory, "mainHook_tolerance_b"),
        ndtMainHookToleranceMeasureC = getCheckItemValue(ndeCategory, "mainHook_tolerance_c"),
        ndtMainHookToleranceMeasureD = getCheckItemValue(ndeCategory, "mainHook_tolerance_d"),
        ndtMainHookToleranceMeasureE = getCheckItemValue(ndeCategory, "mainHook_tolerance_e"),
        ndtMainHookToleranceMeasureF = getCheckItemValue(ndeCategory, "mainHook_tolerance_f"),
        ndtMainHookToleranceMeasureG = getCheckItemValue(ndeCategory, "mainHook_tolerance_g"),
        ndtMainHookToleranceMeasureH = getCheckItemValue(ndeCategory, "mainHook_tolerance_h"),
        ndtMainHookToleranceMeasureResult = getResultStatus(ndeCategory, "mainHook_tolerance_result"),
        ndtAuxiliaryHookNdtType = getCheckItemValue(ndeCategory, "auxHook_ndtType"),
        ndtAuxiliaryHookSpecificationA = getCheckItemValue(ndeCategory, "auxHook_spec_a"),
        ndtAuxiliaryHookSpecificationB = getCheckItemValue(ndeCategory, "auxHook_spec_b"),
        ndtAuxiliaryHookSpecificationC = getCheckItemValue(ndeCategory, "auxHook_spec_c"),
        ndtAuxiliaryHookSpecificationD = getCheckItemValue(ndeCategory, "auxHook_spec_d"),
        ndtAuxiliaryHookSpecificationE = getCheckItemValue(ndeCategory, "auxHook_spec_e"),
        ndtAuxiliaryHookSpecificationF = getCheckItemValue(ndeCategory, "auxHook_spec_f"),
        ndtAuxiliaryHookSpecificationG = getCheckItemValue(ndeCategory, "auxHook_spec_g"),
        ndtAuxiliaryHookSpecificationH = getCheckItemValue(ndeCategory, "auxHook_spec_h"),
        ndtAuxiliaryHookSpecificationResult = getResultStatus(ndeCategory, "auxHook_spec_result"),
        ndtAuxiliaryHookMeasurementResultsA = getCheckItemValue(ndeCategory, "auxHook_measure_a"),
        ndtAuxiliaryHookMeasurementResultsB = getCheckItemValue(ndeCategory, "auxHook_measure_b"),
        ndtAuxiliaryHookMeasurementResultsC = getCheckItemValue(ndeCategory, "auxHook_measure_c"),
        ndtAuxiliaryHookMeasurementResultsD = getCheckItemValue(ndeCategory, "auxHook_measure_d"),
        ndtAuxiliaryHookMeasurementResultsE = getCheckItemValue(ndeCategory, "auxHook_measure_e"),
        ndtAuxiliaryHookMeasurementResultsF = getCheckItemValue(ndeCategory, "auxHook_measure_f"),
        ndtAuxiliaryHookMeasurementResultsG = getCheckItemValue(ndeCategory, "auxHook_measure_g"),
        ndtAuxiliaryHookMeasurementResultsH = getCheckItemValue(ndeCategory, "auxHook_measure_h"),
        ndtAuxiliaryHookMeasurementResultsResult = getResultStatus(ndeCategory, "auxHook_measure_result"),
        ndtAuxiliaryHookToleranceMeasureA = getCheckItemValue(ndeCategory, "auxHook_tolerance_a"),
        ndtAuxiliaryHookToleranceMeasureB = getCheckItemValue(ndeCategory, "auxHook_tolerance_b"),
        ndtAuxiliaryHookToleranceMeasureC = getCheckItemValue(ndeCategory, "auxHook_tolerance_c"),
        ndtAuxiliaryHookToleranceMeasureD = getCheckItemValue(ndeCategory, "auxHook_tolerance_d"),
        ndtAuxiliaryHookToleranceMeasureE = getCheckItemValue(ndeCategory, "auxHook_tolerance_e"),
        ndtAuxiliaryHookToleranceMeasureF = getCheckItemValue(ndeCategory, "auxHook_tolerance_f"),
        ndtAuxiliaryHookToleranceMeasureG = getCheckItemValue(ndeCategory, "auxHook_tolerance_g"),
        ndtAuxiliaryHookToleranceMeasureH = getCheckItemValue(ndeCategory, "auxHook_tolerance_h"),
        ndtAuxiliaryHookToleranceMeasureResult = getResultStatus(ndeCategory, "auxHook_tolerance_result"),
        ndtMainDrumNdtType = getCheckItemValue(ndeCategory, "mainDrum_ndtType"),
        ndtMainDrumSpecificationA = getCheckItemValue(ndeCategory, "mainDrum_spec_a"),
        ndtMainDrumSpecificationB = getCheckItemValue(ndeCategory, "mainDrum_spec_b"),
        ndtMainDrumSpecificationC = getCheckItemValue(ndeCategory, "mainDrum_spec_c"),
        ndtMainDrumSpecificationD = getCheckItemValue(ndeCategory, "mainDrum_spec_d"),
        ndtMainDrumSpecificationE = getCheckItemValue(ndeCategory, "mainDrum_spec_e"),
        ndtMainDrumSpecificationF = getCheckItemValue(ndeCategory, "mainDrum_spec_f"),
        ndtMainDrumSpecificationG = getCheckItemValue(ndeCategory, "mainDrum_spec_g"),
        ndtMainDrumSpecificationResult = getResultStatus(ndeCategory, "mainDrum_spec_result"),
        ndtMainDrumMeasurementResultsA = getCheckItemValue(ndeCategory, "mainDrum_measure_a"),
        ndtMainDrumMeasurementResultsB = getCheckItemValue(ndeCategory, "mainDrum_measure_b"),
        ndtMainDrumMeasurementResultsC = getCheckItemValue(ndeCategory, "mainDrum_measure_c"),
        ndtMainDrumMeasurementResultsD = getCheckItemValue(ndeCategory, "mainDrum_measure_d"),
        ndtMainDrumMeasurementResultsE = getCheckItemValue(ndeCategory, "mainDrum_measure_e"),
        ndtMainDrumMeasurementResultsF = getCheckItemValue(ndeCategory, "mainDrum_measure_f"),
        ndtMainDrumMeasurementResultsG = getCheckItemValue(ndeCategory, "mainDrum_measure_g"),
        ndtMainDrumMeasurementResultsResult = getResultStatus(ndeCategory, "mainDrum_measure_result"),
        ndtAuxiliaryDrumNdtType = getCheckItemValue(ndeCategory, "auxDrum_ndtType"),
        ndtAuxiliaryDrumSpecificationA = getCheckItemValue(ndeCategory, "auxDrum_spec_a"),
        ndtAuxiliaryDrumSpecificationB = getCheckItemValue(ndeCategory, "auxDrum_spec_b"),
        ndtAuxiliaryDrumSpecificationC = getCheckItemValue(ndeCategory, "auxDrum_spec_c"),
        ndtAuxiliaryDrumSpecificationD = getCheckItemValue(ndeCategory, "auxDrum_spec_d"),
        ndtAuxiliaryDrumSpecificationE = getCheckItemValue(ndeCategory, "auxDrum_spec_e"),
        ndtAuxiliaryDrumSpecificationF = getCheckItemValue(ndeCategory, "auxDrum_spec_f"),
        ndtAuxiliaryDrumSpecificationG = getCheckItemValue(ndeCategory, "auxDrum_spec_g"),
        ndtAuxiliaryDrumSpecificationResult = getResultStatus(ndeCategory, "auxDrum_spec_result"),
        ndtAuxiliaryDrumMeasurementResultsA = getCheckItemValue(ndeCategory, "auxDrum_measure_a"),
        ndtAuxiliaryDrumMeasurementResultsB = getCheckItemValue(ndeCategory, "auxDrum_measure_b"),
        ndtAuxiliaryDrumMeasurementResultsC = getCheckItemValue(ndeCategory, "auxDrum_measure_c"),
        ndtAuxiliaryDrumMeasurementResultsD = getCheckItemValue(ndeCategory, "auxDrum_measure_d"),
        ndtAuxiliaryDrumMeasurementResultsE = getCheckItemValue(ndeCategory, "auxDrum_measure_e"),
        ndtAuxiliaryDrumMeasurementResultsF = getCheckItemValue(ndeCategory, "auxDrum_measure_f"),
        ndtAuxiliaryDrumMeasurementResultsG = getCheckItemValue(ndeCategory, "auxDrum_measure_g"),
        ndtAuxiliaryDrumMeasurementResultsResult = getResultStatus(ndeCategory, "auxDrum_measure_result"),
        ndtMainPulleySpecificationA = getCheckItemValue(ndeCategory, "mainPulley_spec_a"),
        ndtMainPulleySpecificationB = getCheckItemValue(ndeCategory, "mainPulley_spec_b"),
        ndtMainPulleySpecificationC = getCheckItemValue(ndeCategory, "mainPulley_spec_c"),
        ndtMainPulleySpecificationD = getCheckItemValue(ndeCategory, "mainPulley_spec_d"),
        ndtMainPulleySpecificationE = getCheckItemValue(ndeCategory, "mainPulley_spec_e"),
        ndtMainPulleySpecificationResult = getResultStatus(ndeCategory, "mainPulley_spec_result"),
        ndtMainPulleyMeasurementResultsA = getCheckItemValue(ndeCategory, "mainPulley_measure_a"),
        ndtMainPulleyMeasurementResultsB = getCheckItemValue(ndeCategory, "mainPulley_measure_b"),
        ndtMainPulleyMeasurementResultsC = getCheckItemValue(ndeCategory, "mainPulley_measure_c"),
        ndtMainPulleyMeasurementResultsD = getCheckItemValue(ndeCategory, "mainPulley_measure_d"),
        ndtMainPulleyMeasurementResultsE = getCheckItemValue(ndeCategory, "mainPulley_measure_e"),
        ndtMainPulleyMeasurementResultsResult = getResultStatus(ndeCategory, "mainPulley_measure_result"),
        ndtAuxiliaryPulleySpecificationA = getCheckItemValue(ndeCategory, "auxPulley_spec_a"),
        ndtAuxiliaryPulleySpecificationB = getCheckItemValue(ndeCategory, "auxPulley_spec_b"),
        ndtAuxiliaryPulleySpecificationC = getCheckItemValue(ndeCategory, "auxPulley_spec_c"),
        ndtAuxiliaryPulleySpecificationD = getCheckItemValue(ndeCategory, "auxPulley_spec_d"),
        ndtAuxiliaryPulleySpecificationE = getCheckItemValue(ndeCategory, "auxPulley_spec_e"),
        ndtAuxiliaryPulleySpecificationResult = getResultStatus(ndeCategory, "auxPulley_spec_result"),
        ndtAuxiliaryPulleyMeasurementResultsA = getCheckItemValue(ndeCategory, "auxPulley_measure_a"),
        ndtAuxiliaryPulleyMeasurementResultsB = getCheckItemValue(ndeCategory, "auxPulley_measure_b"),
        ndtAuxiliaryPulleyMeasurementResultsC = getCheckItemValue(ndeCategory, "auxPulley_measure_c"),
        ndtAuxiliaryPulleyMeasurementResultsD = getCheckItemValue(ndeCategory, "auxPulley_measure_d"),
        ndtAuxiliaryPulleyMeasurementResultsE = getCheckItemValue(ndeCategory, "auxPulley_measure_e"),
        ndtAuxiliaryPulleyMeasurementResultsResult = getResultStatus(ndeCategory, "auxPulley_measure_result"),

        // --- Testing ---
        testingFunctionHoistingLoweringResult = getResultStatus(funcTestCategory, "hoistingLowering"),
        testingFunctionExtendedRectractedBoomResult = getResultStatus(funcTestCategory, "extendedRetractedBoom"),
        testingFunctionExtendedRectractedOutriggerResult = getResultStatus(funcTestCategory, "extendedRetractedOutrigger"),
        testingFunctionSwingSlewingResult = getResultStatus(funcTestCategory, "swingSlewing"),
        testingFunctionAntiTwoBlockResult = getResultStatus(funcTestCategory, "antiTwoBlock"),
        testingFunctionBoomStopResult = getResultStatus(funcTestCategory, "boomStop"),
        testingFunctionAnemometerWindSpeedResult = getResultStatus(funcTestCategory, "anemometerWindSpeed"),
        testingFunctionBrakeLockingDeviceResult = getResultStatus(funcTestCategory, "brakeLockingDevice"),
        testingFunctionLoadMomentIndicatorResult = getResultStatus(funcTestCategory, "loadMomentIndicator"),
        testingFunctionTurnSignalResult = getResultStatus(funcTestCategory, "turnSignal"),
        testingFunctionDrivingLightsResult = getResultStatus(funcTestCategory, "drivingLights"),
        testingFunctionLoadIndicatorLightResult = getResultStatus(funcTestCategory, "loadIndicatorLight"),
        testingFunctionRotaryLampResult = getResultStatus(funcTestCategory, "rotaryLamp"),
        testingFunctionHornResult = getResultStatus(funcTestCategory, "horn"),
        testingFunctionSwingAlarmResult = getResultStatus(funcTestCategory, "swingAlarm"),
        testingFunctionReverseAlarmResult = getResultStatus(funcTestCategory, "reverseAlarm"),
        testingFunctionOverloadAlarmResult = getResultStatus(funcTestCategory, "overloadAlarm"),

        dynamicMainHookTests = checkItemsByCategory.keys.filter { it.startsWith("testing_load_dynamic_main_item_") }.mapNotNull {
            val index = it.substringAfterLast('_').toIntOrNull()
            if (index != null) {
                MobileCraneDynamicHookTest(
                    boomLength = getCheckItemValue(it, "boomLength"),
                    radius = getCheckItemValue(it, "radius"),
                    boomAngle = getCheckItemValue(it, "boomAngle"),
                    testLoad = getCheckItemValue(it, "testLoadKg"),
                    safeWorkingLoad = getCheckItemValue(it, "safeWorkingLoadKg"),
                    result = getCheckItemValue(it, "result")
                )
            } else null
        },
        dynamicAuxiliaryHookTests = checkItemsByCategory.keys.filter { it.startsWith("testing_load_dynamic_aux_item_") }.mapNotNull {
            val index = it.substringAfterLast('_').toIntOrNull()
            if (index != null) {
                MobileCraneDynamicHookTest(
                    boomLength = getCheckItemValue(it, "boomLength"),
                    radius = getCheckItemValue(it, "radius"),
                    boomAngle = getCheckItemValue(it, "boomAngle"),
                    testLoad = getCheckItemValue(it, "testLoadKg"),
                    safeWorkingLoad = getCheckItemValue(it, "safeWorkingLoadKg"),
                    result = getCheckItemValue(it, "result")
                )
            } else null
        },
        staticMainHookTests = checkItemsByCategory.keys.filter { it.startsWith("testing_load_static_main_item_") }.mapNotNull {
            val index = it.substringAfterLast('_').toIntOrNull()
            if (index != null) {
                MobileCraneStaticHookTest(
                    boomLength = getCheckItemValue(it, "boomLength"),
                    radius = getCheckItemValue(it, "radius"),
                    boomAngle = getCheckItemValue(it, "boomAngle"),
                    testLoad = getCheckItemValue(it, "testLoadKg"),
                    safeWorkingLoad = getCheckItemValue(it, "safeWorkingLoadKg"),
                    result = getCheckItemValue(it, "result")
                )
            } else null
        },
        staticAuxiliaryHookTests = checkItemsByCategory.keys.filter { it.startsWith("testing_load_static_aux_item_") }.mapNotNull {
            val index = it.substringAfterLast('_').toIntOrNull()
            if (index != null) {
                MobileCraneStaticHookTest(
                    boomLength = getCheckItemValue(it, "boomLength"),
                    radius = getCheckItemValue(it, "radius"),
                    boomAngle = getCheckItemValue(it, "boomAngle"),
                    testLoad = getCheckItemValue(it, "testLoadKg"),
                    safeWorkingLoad = getCheckItemValue(it, "safeWorkingLoadKg"),
                    result = getCheckItemValue(it, "result")
                )
            } else null
        }
    )

    // --- Conclusion and Recommendation ---
    val conclusion = this.findings.find { it.type == FindingType.FINDING }?.description ?: ""
    val recommendation = this.findings.find { it.type == FindingType.RECOMMENDATION }?.description ?: ""

    return MobileCraneReportRequest(
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        createdAt = this.inspection.createdAt ?: "",
        extraId = this.inspection.id,
        equipmentType = this.inspection.equipmentType,
        examinationType = this.inspection.examinationType,
        generalData = generalData,
        technicalData = technicalData,
        inspectionAndTesting = inspectionAndTesting,
        conclusion = conclusion,
        recommendation = recommendation
    )
}