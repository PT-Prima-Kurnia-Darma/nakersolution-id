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
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneBapVisualCheck
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneDynamicHookTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneInspectionAndTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneStaticHookTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.MobileCraneTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.NdtBoom
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.NdtBoomInspection
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.NdtDrum
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.NdtHook
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.NdtMeasurement
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.NdtPulley
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.NdtSpecification
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.NdtSteelWireRope
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.NdtSteelWireRopeItem
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.TestingFunction
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualAuxiliaryHook
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualAuxiliaryWinch
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualBrake
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualControlValve
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualElectricalComponents
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualFoundationAndBolts
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualFrameColumnsOnFoundation
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualHoistGearBlock
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualHydraulic
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualHydraulicCylinder
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualInternalCombustionEngine
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualLadder
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualLatticeBoom
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualLimitSwitch
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualMainHook
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualMainPulley
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualMainWinch
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualOperatorCabin
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualOutriggers
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualPneumatic
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualSafetyDevices
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualSteering
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualTravelDrum
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualTurntable
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualWireRope
import com.nakersolutionid.nakersolutionid.data.remote.dto.mobilecrane.VisualWorkingPlatform
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

    // --- Main Inspection Domain Object ---
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id, // Store BAP's own ID here
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PAA,
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
        userAddress = this.inspection.usageLocation ?: "",
        inspectionType = this.inspection.inspectionType.toDisplayString()
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

    // Note: Signature is no longer part of the request DTO
    // val signature = MobileCraneBapSignature(
    //     companyName = getValue(BapCategories.SIGNATURE, "companyName")
    // )

    return MobileCraneBapRequest(
        laporanId = this.inspection.extraId,
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        extraId = this.inspection.id,
        createdAt = this.inspection.createdAt ?: "",
        generalData = generalData,
        technicalData = technicalData,
        inspectionResult = inspectionResult
        // UPDATE: Remove signature from the request object
    )
}


// =================================================================================================
//                                     Report Mappers
// =================================================================================================

/**
 * Maps a [MobileCraneReportData] DTO (from API response) to an [InspectionWithDetailsDomain].
 * This function converts the nested DTO structure into a flattened domain model, which is easier
 * to work with in the UI and local database.
 */
fun MobileCraneReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    val inspectionId = this.extraId

    // Helper functions to simplify creating check items
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

    fun addBooleanCheckItem(category: String, itemName: String, value: Boolean) {
        checkItems.add(
            InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = value, result = "")
        )
    }

    // --- General Data ---
    val generalDataCategory = "general_data"
    addCheckItem(generalDataCategory, "personInCharge", this.generalData.userSubcontractorPersonInCharge)
    addCheckItem(generalDataCategory, "operatorName", this.generalData.operatorName)
    addCheckItem(generalDataCategory, "intendedUse", this.generalData.intendedUse)
    addCheckItem(generalDataCategory, "operatorCertificate", this.generalData.operatorCertificate)
    addCheckItem(generalDataCategory, "equipmentHistory", this.generalData.equipmentHistory)

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
    addCheckItem(techCategory, "motor_type", tech.type)
    addCheckItem(techCategory, "motor_numberOfCylinders", tech.numberOfCylinders)
    addCheckItem(techCategory, "motor_netPower", tech.netPower)
    addCheckItem(techCategory, "motor_brandYearOfManufacture", tech.brandYearOfManufacture)
    addCheckItem(techCategory, "motor_manufacturer", tech.manufacturer)
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

    // --- Inspection and Testing (Visual) ---
    val visualCategory = "visual_inspection"
    val it = this.inspectionAndTesting
    // VisualFoundationAndBolts
    addResultStatusCheckItem(visualCategory, "foundationAnchorBoltCorrosion", it.visualFoundationAndBolts.corrosion)
    addResultStatusCheckItem(visualCategory, "foundationAnchorBoltCracks", it.visualFoundationAndBolts.cracks)
    addResultStatusCheckItem(visualCategory, "foundationAnchorBoltDeformation", it.visualFoundationAndBolts.deformation)
    addResultStatusCheckItem(visualCategory, "foundationAnchorBoltTightness", it.visualFoundationAndBolts.tightness)
    // VisualFrameColumnsOnFoundation
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationCorrosion", it.visualFrameColumnsOnFoundation.corrosion)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationCracks", it.visualFrameColumnsOnFoundation.cracks)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationDeformation", it.visualFrameColumnsOnFoundation.deformation)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationFastening", it.visualFrameColumnsOnFoundation.fastening)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationTransverseReinforcement", it.visualFrameColumnsOnFoundation.transverseReinforcement)
    addResultStatusCheckItem(visualCategory, "frameColumnsOnFoundationDiagonalReinforcement", it.visualFrameColumnsOnFoundation.diagonalReinforcement)
    // VisualLadder
    addResultStatusCheckItem(visualCategory, "ladderCorrosion", it.visualLadder.corrosion)
    addResultStatusCheckItem(visualCategory, "ladderCracks", it.visualLadder.cracks)
    addResultStatusCheckItem(visualCategory, "ladderDeformation", it.visualLadder.deformation)
    addResultStatusCheckItem(visualCategory, "ladderFastening", it.visualLadder.fastening)
    // VisualWorkingPlatform
    addResultStatusCheckItem(visualCategory, "workingPlatformCorrosion", it.visualWorkingPlatform.corrosion)
    addResultStatusCheckItem(visualCategory, "workingPlatformCracks", it.visualWorkingPlatform.cracks)
    addResultStatusCheckItem(visualCategory, "workingPlatformDeformation", it.visualWorkingPlatform.deformation)
    addResultStatusCheckItem(visualCategory, "workingPlatformFastening", it.visualWorkingPlatform.fastening)
    // VisualOutriggers
    addResultStatusCheckItem(visualCategory, "outriggersOutriggerArmHousing", it.visualOutriggers.outriggerArmHousing)
    addResultStatusCheckItem(visualCategory, "outriggersOutriggerArms", it.visualOutriggers.outriggerArms)
    addResultStatusCheckItem(visualCategory, "outriggersJack", it.visualOutriggers.jack)
    addResultStatusCheckItem(visualCategory, "outriggersOutriggerPads", it.visualOutriggers.outriggerPads)
    addResultStatusCheckItem(visualCategory, "outriggersHousingConnectionToChassis", it.visualOutriggers.housingConnectionToChassis)
    addResultStatusCheckItem(visualCategory, "outriggersOutriggerSafetyLocks", it.visualOutriggers.outriggerSafetyLocks)
    // VisualTurntable
    addResultStatusCheckItem(visualCategory, "turntableSlewingRollerBearing", it.visualTurntable.slewingRollerBearing)
    addResultStatusCheckItem(visualCategory, "turntableBrakeHousing", it.visualTurntable.brakeHousing)
    addResultStatusCheckItem(visualCategory, "turntableBrakeLiningsAndShoes", it.visualTurntable.brakeLiningsAndShoes)
    addResultStatusCheckItem(visualCategory, "turntableDrumSurface", it.visualTurntable.drumSurface)
    addResultStatusCheckItem(visualCategory, "turntablePressureCylinder", it.visualTurntable.pressureCylinder)
    addResultStatusCheckItem(visualCategory, "turntableDrumAxle", it.visualTurntable.drumAxle)
    addResultStatusCheckItem(visualCategory, "turntableLeversPinsBolts", it.visualTurntable.leversPinsBolts)
    addResultStatusCheckItem(visualCategory, "turntableGuard", it.visualTurntable.guard)
    // VisualLatticeBoom
    addResultStatusCheckItem(visualCategory, "latticeBoomMainBoom", it.visualLatticeBoom.mainBoom)
    addResultStatusCheckItem(visualCategory, "latticeBoomBoomSection", it.visualLatticeBoom.boomSection)
    addResultStatusCheckItem(visualCategory, "latticeBoomTopPulley", it.visualLatticeBoom.topPulley)
    addResultStatusCheckItem(visualCategory, "latticeBoomPulleyGuard", it.visualLatticeBoom.pulleyGuard)
    addResultStatusCheckItem(visualCategory, "latticeBoomWireRopeGuard", it.visualLatticeBoom.wireRopeGuard)
    addResultStatusCheckItem(visualCategory, "latticeBoomPulleyGrooveLip", it.visualLatticeBoom.pulleyGrooveLip)
    addResultStatusCheckItem(visualCategory, "latticeBoomPivotPin", it.visualLatticeBoom.pivotPin)
    addResultStatusCheckItem(visualCategory, "latticeBoomWireRopeGuidePulley", it.visualLatticeBoom.wireRopeGuidePulley)
    // VisualSteering
    addResultStatusCheckItem(visualCategory, "clutchMainClutch", it.visualSteering.mainClutch)
    addResultStatusCheckItem(visualCategory, "transmission", it.visualSteering.transmission)
    addResultStatusCheckItem(visualCategory, "steeringFrontWheel", it.visualSteering.frontWheel)
    addResultStatusCheckItem(visualCategory, "steeringMiddleWheel", it.visualSteering.middleWheel)
    addResultStatusCheckItem(visualCategory, "steeringRearWheel", it.visualSteering.rearWheel)
    // VisualBrake
    addResultStatusCheckItem(visualCategory, "brakeServiceBrake", it.visualBrake.serviceBrake)
    addResultStatusCheckItem(visualCategory, "brakeParkingBrake", it.visualBrake.parkingBrake)
    addResultStatusCheckItem(visualCategory, "brakeBrakeHousing", it.visualBrake.brakeHousing)
    addResultStatusCheckItem(visualCategory, "brakeBrakeLiningsAndShoes", it.visualBrake.brakeLiningsAndShoes)
    addResultStatusCheckItem(visualCategory, "brakeDrumSurface", it.visualBrake.drumSurface)
    addResultStatusCheckItem(visualCategory, "brakeLeversPinsBolts", it.visualBrake.leversPinsBolts)
    addResultStatusCheckItem(visualCategory, "brakeGuard", it.visualBrake.guard)
    // VisualTravelDrum
    addResultStatusCheckItem(visualCategory, "travelDrumClutchHousing", it.visualTravelDrum.clutchHousing)
    addResultStatusCheckItem(visualCategory, "travelDrumClutchLining", it.visualTravelDrum.clutchLining)
    addResultStatusCheckItem(visualCategory, "travelDrumClutchDrumSurface", it.visualTravelDrum.clutchDrumSurface)
    addResultStatusCheckItem(visualCategory, "travelDrumLeversPinsBolts", it.visualTravelDrum.leversPinsBolts)
    addResultStatusCheckItem(visualCategory, "travelDrumGuard", it.visualTravelDrum.guard)
    // VisualMainWinch
    addResultStatusCheckItem(visualCategory, "mainWinchDrumMounting", it.visualMainWinch.drumMounting)
    addResultStatusCheckItem(visualCategory, "mainWinchWindingDrumSurface", it.visualMainWinch.windingDrumSurface)
    addResultStatusCheckItem(visualCategory, "mainWinchBrakeLiningsAndShoes", it.visualMainWinch.brakeLiningsAndShoes)
    addResultStatusCheckItem(visualCategory, "mainWinchBrakeDrumSurface", it.visualMainWinch.brakeDrumSurface)
    addResultStatusCheckItem(visualCategory, "mainWinchBrakeHousing", it.visualMainWinch.brakeHousing)
    addResultStatusCheckItem(visualCategory, "mainWinchClutchLiningsAndShoes", it.visualMainWinch.clutchLiningsAndShoes)
    addResultStatusCheckItem(visualCategory, "mainWinchClutchDrumSurface", it.visualMainWinch.clutchDrumSurface)
    addResultStatusCheckItem(visualCategory, "mainWinchGroove", it.visualMainWinch.groove)
    addResultStatusCheckItem(visualCategory, "mainWinchGrooveLip", it.visualMainWinch.grooveLip)
    addResultStatusCheckItem(visualCategory, "mainWinchFlanges", it.visualMainWinch.flanges)
    addResultStatusCheckItem(visualCategory, "mainWinchBrakeActuatorLeversPinsAndBolts", it.visualMainWinch.brakeActuatorLeversPinsAndBolts)
    // VisualAuxiliaryWinch
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchDrumMounting", it.visualAuxiliaryWinch.drumMounting)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchWindingDrumSurface", it.visualAuxiliaryWinch.windingDrumSurface)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchBrakeLiningsAndShoes", it.visualAuxiliaryWinch.brakeLiningsAndShoes)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchBrakeDrumSurface", it.visualAuxiliaryWinch.brakeDrumSurface)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchBrakeHousing", it.visualAuxiliaryWinch.brakeHousing)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchClutchLiningsAndShoes", it.visualAuxiliaryWinch.clutchLiningsAndShoes)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchClutchDrumSurface", it.visualAuxiliaryWinch.clutchDrumSurface)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchGroove", it.visualAuxiliaryWinch.groove)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchGrooveLip", it.visualAuxiliaryWinch.grooveLip)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchFlanges", it.visualAuxiliaryWinch.flanges)
    addResultStatusCheckItem(visualCategory, "auxiliaryWinchBrakeActuatorLeversPinsAndBolts", it.visualAuxiliaryWinch.brakeActuatorLeversPinsAndBolts)
    // VisualHoistGearBlock
    addResultStatusCheckItem(visualCategory, "hoistGearBlockLubrication", it.visualHoistGearBlock.lubrication)
    addResultStatusCheckItem(visualCategory, "hoistGearBlockOilSeal", it.visualHoistGearBlock.oilSeal)
    // VisualMainPulley
    addResultStatusCheckItem(visualCategory, "mainPulleyPulleyGroove", it.visualMainPulley.pulleyGroove)
    addResultStatusCheckItem(visualCategory, "mainPulleyPulleyGrooveLip", it.visualMainPulley.pulleyGrooveLip)
    addResultStatusCheckItem(visualCategory, "mainPulleyPulleyPin", it.visualMainPulley.pulleyPin)
    addResultStatusCheckItem(visualCategory, "mainPulleyBearing", it.visualMainPulley.bearing)
    addResultStatusCheckItem(visualCategory, "mainPulleyPulleyGuard", it.visualMainPulley.pulleyGuard)
    addResultStatusCheckItem(visualCategory, "mainPulleyWireRopeGuard", it.visualMainPulley.wireRopeGuard)
    // VisualMainHook
    addResultStatusCheckItem(visualCategory, "mainHookVisualSwivelNutAndBearing", it.visualMainHook.swivelNutAndBearing)
    addResultStatusCheckItem(visualCategory, "mainHookVisualTrunnion", it.visualMainHook.trunnion)
    addResultStatusCheckItem(visualCategory, "mainHookVisualSafetyLatch", it.visualMainHook.safetyLatch)
    // VisualAuxiliaryHook
    addResultStatusCheckItem(visualCategory, "auxiliaryHookVisualFreeFallWeight", it.visualAuxiliaryHook.freeFallWeight)
    addResultStatusCheckItem(visualCategory, "auxiliaryHookVisualSwivelNutAndBearing", it.visualAuxiliaryHook.swivelNutAndBearing)
    addResultStatusCheckItem(visualCategory, "auxiliaryHookVisualSafetyLatch", it.visualAuxiliaryHook.safetyLatch)
    // VisualWireRope
    addResultStatusCheckItem(visualCategory, "mainWireRopeVisualCorrosion", it.visualMainWireRope.corrosion)
    addResultStatusCheckItem(visualCategory, "mainWireRopeVisualWear", it.visualMainWireRope.wear)
    addResultStatusCheckItem(visualCategory, "mainWireRopeVisualBreakage", it.visualMainWireRope.breakage)
    addResultStatusCheckItem(visualCategory, "mainWireRopeVisualDeformation", it.visualMainWireRope.deformation)
    addResultStatusCheckItem(visualCategory, "auxiliaryWireRopeVisualCorrosion", it.visualAuxiliaryWireRope.corrosion)
    addResultStatusCheckItem(visualCategory, "auxiliaryWireRopeVisualWear", it.visualAuxiliaryWireRope.wear)
    addResultStatusCheckItem(visualCategory, "auxiliaryWireRopeVisualBreakage", it.visualAuxiliaryWireRope.breakage)
    addResultStatusCheckItem(visualCategory, "auxiliaryWireRopeVisualDeformation", it.visualAuxiliaryWireRope.deformation)
    // VisualLimitSwitch
    addResultStatusCheckItem(visualCategory, "limitSwitchLsLongTravel", it.visualLimitSwitch.longTravel)
    addResultStatusCheckItem(visualCategory, "limitSwitchLsCrossTravel", it.visualLimitSwitch.crossTravel)
    addResultStatusCheckItem(visualCategory, "limitSwitchLsHoisting", it.visualLimitSwitch.hoisting)
    // VisualInternalCombustionEngine
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineCoolingSystem", it.visualInternalCombustionEngine.coolingSystem)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineLubricationSystem", it.visualInternalCombustionEngine.lubricationSystem)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineEngineMounting", it.visualInternalCombustionEngine.engineMounting)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineSafetyGuardEquipment", it.visualInternalCombustionEngine.safetyGuardEquipment)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineExhaustSystem", it.visualInternalCombustionEngine.exhaustSystem)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineFuelSystem", it.visualInternalCombustionEngine.fuelSystem)
    addResultStatusCheckItem(visualCategory, "internalCombustionEnginePowerTransmissionSystem", it.visualInternalCombustionEngine.powerTransmissionSystem)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineBattery", it.visualInternalCombustionEngine.battery)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineStarterMotor", it.visualInternalCombustionEngine.starterMotor)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineWiringInstallation", it.visualInternalCombustionEngine.wiringInstallation)
    addResultStatusCheckItem(visualCategory, "internalCombustionEngineTurbocharger", it.visualInternalCombustionEngine.turbocharger)
    // VisualHydraulic
    addResultStatusCheckItem(visualCategory, "hydraulicHydraulicPump", it.visualHydraulic.pump)
    addResultStatusCheckItem(visualCategory, "hydraulicHydraulicLines", it.visualHydraulic.lines)
    addResultStatusCheckItem(visualCategory, "hydraulicHydraulicFilter", it.visualHydraulic.filter)
    addResultStatusCheckItem(visualCategory, "hydraulicHydraulicTank", it.visualHydraulic.tank)
    addResultStatusCheckItem(visualCategory, "hydraulicMotorMainWinchMotor", it.visualHydraulic.mainWinchMotor)
    addResultStatusCheckItem(visualCategory, "hydraulicMotorAuxiliaryWinchMotor", it.visualHydraulic.auxiliaryWinchMotor)
    addResultStatusCheckItem(visualCategory, "hydraulicMotorBoomWinchMotor", it.visualHydraulic.boomWinchMotor)
    addResultStatusCheckItem(visualCategory, "hydraulicMotorSwingMotor", it.visualHydraulic.swingMotor)
    // VisualControlValve
    addResultStatusCheckItem(visualCategory, "controlValveReliefValve", it.visualControlValve.reliefValve)
    addResultStatusCheckItem(visualCategory, "controlValveMainWinchValve", it.visualControlValve.mainWinchValve)
    addResultStatusCheckItem(visualCategory, "controlValveAuxiliaryWinchValve", it.visualControlValve.auxiliaryWinchValve)
    addResultStatusCheckItem(visualCategory, "controlValveBoomWinchValve", it.visualControlValve.boomWinchValve)
    addResultStatusCheckItem(visualCategory, "controlValveBoomMovementValve", it.visualControlValve.boomMovementValve)
    addResultStatusCheckItem(visualCategory, "controlValveSteeringCylinderValve", it.visualControlValve.steeringCylinderValve)
    addResultStatusCheckItem(visualCategory, "controlValveAxleOscillationValve", it.visualControlValve.axleOscillationValve)
    addResultStatusCheckItem(visualCategory, "controlValveOutriggerMovementValve", it.visualControlValve.outriggerMovementValve)
    // VisualHydraulicCylinder
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderBoomMovementCylinder", it.visualHydraulicCylinder.boomMovementCylinder)
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderOutriggerCylinder", it.visualHydraulicCylinder.outriggerCylinder)
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderSteeringWheelCylinder", it.visualHydraulicCylinder.steeringWheelCylinder)
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderAxleOscillationCylinder", it.visualHydraulicCylinder.axleOscillationCylinder)
    addResultStatusCheckItem(visualCategory, "hydraulicCylinderTelescopicCylinder", it.visualHydraulicCylinder.telescopicCylinder)
    // VisualPneumatic
    addResultStatusCheckItem(visualCategory, "pneumaticCompressor", it.visualPneumatic.compressor)
    addResultStatusCheckItem(visualCategory, "pneumaticTankAndSafetyValve", it.visualPneumatic.tankAndSafetyValve)
    addResultStatusCheckItem(visualCategory, "pneumaticPressurizedAirLines", it.visualPneumatic.pressurizedAirLines)
    addResultStatusCheckItem(visualCategory, "pneumaticAirFilter", it.visualPneumatic.airFilter)
    addResultStatusCheckItem(visualCategory, "pneumaticControlValve", it.visualPneumatic.controlValve)
    // VisualOperatorCabin
    addResultStatusCheckItem(visualCategory, "operatorCabinSafetyLadder", it.visualOperatorCabin.safetyLadder)
    addResultStatusCheckItem(visualCategory, "operatorCabinDoor", it.visualOperatorCabin.door)
    addResultStatusCheckItem(visualCategory, "operatorCabinWindow", it.visualOperatorCabin.window)
    addResultStatusCheckItem(visualCategory, "operatorCabinFanAc", it.visualOperatorCabin.fanAc)
    addResultStatusCheckItem(visualCategory, "operatorCabinControlLeversButtons", it.visualOperatorCabin.controlLeversButtons)
    addResultStatusCheckItem(visualCategory, "operatorCabinPendantControl", it.visualOperatorCabin.pendantControl)
    addResultStatusCheckItem(visualCategory, "operatorCabinLighting", it.visualOperatorCabin.lighting)
    addResultStatusCheckItem(visualCategory, "operatorCabinHornSignalAlarm", it.visualOperatorCabin.hornSignalAlarm)
    addResultStatusCheckItem(visualCategory, "operatorCabinFuse", it.visualOperatorCabin.fuse)
    addResultStatusCheckItem(visualCategory, "operatorCabinCommunicationDevice", it.visualOperatorCabin.communicationDevice)
    addResultStatusCheckItem(visualCategory, "operatorCabinFireExtinguisher", it.visualOperatorCabin.fireExtinguisher)
    addResultStatusCheckItem(visualCategory, "operatorCabinOperatingSigns", it.visualOperatorCabin.operatingSigns)
    addResultStatusCheckItem(visualCategory, "operatorCabinIgnitionKeyMasterSwitch", it.visualOperatorCabin.ignitionKeyMasterSwitch)
    addResultStatusCheckItem(visualCategory, "operatorCabinButtonsHandlesLevers", it.visualOperatorCabin.buttonsHandlesLevers)
    // VisualElectricalComponents
    addResultStatusCheckItem(visualCategory, "electricalComponentsPanelConductorConnector", it.visualElectricalComponents.panelConductorConnector)
    addResultStatusCheckItem(visualCategory, "electricalComponentsConductorProtection", it.visualElectricalComponents.conductorProtection)
    addResultStatusCheckItem(visualCategory, "electricalComponentsMotorInstallationSafetySystem", it.visualElectricalComponents.motorInstallationSafetySystem)
    addResultStatusCheckItem(visualCategory, "electricalComponentsGroundingSystem", it.visualElectricalComponents.groundingSystem)
    addResultStatusCheckItem(visualCategory, "electricalComponentsInstallation", it.visualElectricalComponents.installation)
    // VisualSafetyDevices
    addResultStatusCheckItem(visualCategory, "safetyDevicesLadderHandrail", it.visualSafetyDevices.ladderHandrail)
    addResultStatusCheckItem(visualCategory, "safetyDevicesEngineOilLubricantPressure", it.visualSafetyDevices.engineOilLubricantPressure)
    addResultStatusCheckItem(visualCategory, "safetyDevicesHydraulicOilPressure", it.visualSafetyDevices.hydraulicOilPressure)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAirPressure", it.visualSafetyDevices.airPressure)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAmperemeter", it.visualSafetyDevices.amperemeter)
    addResultStatusCheckItem(visualCategory, "safetyDevicesVoltage", it.visualSafetyDevices.voltage)
    addResultStatusCheckItem(visualCategory, "safetyDevicesEngineTemperature", it.visualSafetyDevices.engineTemperature)
    addResultStatusCheckItem(visualCategory, "safetyDevicesTransmissionTemperature", it.visualSafetyDevices.transmissionTemperature)
    addResultStatusCheckItem(visualCategory, "safetyDevicesConverterOilTemperaturePressure", it.visualSafetyDevices.converterOilTemperaturePressure)
    addResultStatusCheckItem(visualCategory, "safetyDevicesSpeedometerIndicator", it.visualSafetyDevices.converterSpeedometerIndicator)
    addResultStatusCheckItem(visualCategory, "safetyDevicesRotaryLamp", it.visualSafetyDevices.converterRotaryLamp)
    addResultStatusCheckItem(visualCategory, "safetyDevicesMainHoistRopeUpDownLimit", it.visualSafetyDevices.converterMainHoistRopeUpDownLimit)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAuxiliaryHoistRopeUpDownLimit", it.visualSafetyDevices.converterAuxiliaryHoistRopeUpDownLimit)
    addResultStatusCheckItem(visualCategory, "safetyDevicesSwingMotionLimit", it.visualSafetyDevices.converterSwingMotionLimit)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLevelIndicator", it.visualSafetyDevices.converterLevelIndicator)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLoadWeightIndicator", it.visualSafetyDevices.converterLoadWeightIndicator)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLoadChart", it.visualSafetyDevices.converterLoadChart)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAnemometerWindSpeed", it.visualSafetyDevices.converterAnemometerWindSpeed)
    addResultStatusCheckItem(visualCategory, "safetyDevicesBoomAngleIndicator", it.visualSafetyDevices.converterBoomAngleIndicator)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAirPressureIndicator", it.visualSafetyDevices.converterAirPressureIndicator)
    addResultStatusCheckItem(visualCategory, "safetyDevicesHydraulicPressureIndicator", it.visualSafetyDevices.converterHydraulicPressureIndicator)
    addResultStatusCheckItem(visualCategory, "safetyDevicesSafetyValves", it.visualSafetyDevices.converterSafetyValves)
    addResultStatusCheckItem(visualCategory, "safetyDevicesMainWindingDrumSafetyLock", it.visualSafetyDevices.converterMainWindingDrumSafetyLock)
    addResultStatusCheckItem(visualCategory, "safetyDevicesAuxiliaryWindingDrumSafetyLock", it.visualSafetyDevices.converterAuxiliaryWindingDrumSafetyLock)
    addResultStatusCheckItem(visualCategory, "safetyDevicesTelescopicMotionLimit", it.visualSafetyDevices.converterTelescopicMotionLimit)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLightningArrester", it.visualSafetyDevices.converterLightningArrester)
    addResultStatusCheckItem(visualCategory, "safetyDevicesLiftingHeightIndicator", it.visualSafetyDevices.converterLiftingHeightIndicator)

    // --- Inspection and Testing (NDE) ---
    addCheckItem("nde_wireRope", "ndtType", it.ndtSteelWireRope.ndtType)
    it.ndtSteelWireRope.ropes.forEachIndexed { index, item ->
        val cat = "nde_wireRope_item_$index"
        addCheckItem(cat, "usage", item.usageAt)
        addCheckItem(cat, "specDiameter", item.specDiameter)
        addCheckItem(cat, "actualDiameter", item.actualDiameter)
        addCheckItem(cat, "construction", item.construction)
        addCheckItem(cat, "type", item.type)
        addCheckItem(cat, "length", item.length)
        addCheckItem(cat, "age", item.age)
        addBooleanCheckItem(cat, "hasDefect", item.result.status)
        addCheckItem(cat, "remarks", item.result.result)
    }

    addCheckItem("nde_boom", "boomType", it.ndtBoom.boomType)
    addCheckItem("nde_boom", "ndtType", it.ndtBoom.ndtType)
    it.ndtBoom.inspections.forEachIndexed { index, item ->
        val cat = "nde_boom_item_$index"
        addCheckItem(cat, "partInspected", item.part)
        addCheckItem(cat, "location", item.location)
        addBooleanCheckItem(cat, "hasDefect", item.result.status)
        addCheckItem(cat, "remarks", item.result.result)
    }

    it.ndtMainHook.let { hook ->
        val cat = "nde_mainHook"
        addCheckItem(cat, "hookCapacity", hook.capacity)
        addCheckItem(cat, "ndtType", hook.ndtType)
        addCheckItem(cat, "spec_a", hook.specification.a)
        addCheckItem(cat, "spec_b", hook.specification.b)
        addCheckItem(cat, "spec_c", hook.specification.c)
        addCheckItem(cat, "spec_d", hook.specification.d)
        addCheckItem(cat, "spec_e", hook.specification.e)
        addCheckItem(cat, "spec_f", hook.specification.f)
        addCheckItem(cat, "spec_g", hook.specification.g)
        addCheckItem(cat, "spec_h", hook.specification.h)
        addResultStatusCheckItem(cat, "spec_finding", hook.specification.result)
        addCheckItem(cat, "result_a", hook.measurementResults.a)
        addCheckItem(cat, "result_b", hook.measurementResults.b)
        addCheckItem(cat, "result_c", hook.measurementResults.c)
        addCheckItem(cat, "result_d", hook.measurementResults.d)
        addCheckItem(cat, "result_e", hook.measurementResults.e)
        addCheckItem(cat, "result_f", hook.measurementResults.f)
        addCheckItem(cat, "result_g", hook.measurementResults.g)
        addCheckItem(cat, "result_h", hook.measurementResults.h)
        addResultStatusCheckItem(cat, "result_finding", hook.measurementResults.result)
        addCheckItem(cat, "tolerance_a", hook.toleranceMeasure.a)
        addCheckItem(cat, "tolerance_b", hook.toleranceMeasure.b)
        addCheckItem(cat, "tolerance_c", hook.toleranceMeasure.c)
        addCheckItem(cat, "tolerance_d", hook.toleranceMeasure.d)
        addCheckItem(cat, "tolerance_e", hook.toleranceMeasure.e)
        addCheckItem(cat, "tolerance_f", hook.toleranceMeasure.f)
        addCheckItem(cat, "tolerance_g", hook.toleranceMeasure.g)
        addCheckItem(cat, "tolerance_h", hook.toleranceMeasure.h)
        addResultStatusCheckItem(cat, "tolerance_finding", hook.toleranceMeasure.result)
    }

    it.ndtAuxiliaryHook.let { hook ->
        val cat = "nde_auxHook"
        addCheckItem(cat, "hookCapacity", hook.capacity)
        addCheckItem(cat, "ndtType", hook.ndtType)
        addCheckItem(cat, "spec_a", hook.specification.a)
        addCheckItem(cat, "spec_b", hook.specification.b)
        addCheckItem(cat, "spec_c", hook.specification.c)
        addCheckItem(cat, "spec_d", hook.specification.d)
        addCheckItem(cat, "spec_e", hook.specification.e)
        addCheckItem(cat, "spec_f", hook.specification.f)
        addCheckItem(cat, "spec_g", hook.specification.g)
        addCheckItem(cat, "spec_h", hook.specification.h)
        addResultStatusCheckItem(cat, "spec_finding", hook.specification.result)
        addCheckItem(cat, "result_a", hook.measurementResults.a)
        addCheckItem(cat, "result_b", hook.measurementResults.b)
        addCheckItem(cat, "result_c", hook.measurementResults.c)
        addCheckItem(cat, "result_d", hook.measurementResults.d)
        addCheckItem(cat, "result_e", hook.measurementResults.e)
        addCheckItem(cat, "result_f", hook.measurementResults.f)
        addCheckItem(cat, "result_g", hook.measurementResults.g)
        addCheckItem(cat, "result_h", hook.measurementResults.h)
        addResultStatusCheckItem(cat, "result_finding", hook.measurementResults.result)
        addCheckItem(cat, "tolerance_a", hook.toleranceMeasure.a)
        addCheckItem(cat, "tolerance_b", hook.toleranceMeasure.b)
        addCheckItem(cat, "tolerance_c", hook.toleranceMeasure.c)
        addCheckItem(cat, "tolerance_d", hook.toleranceMeasure.d)
        addCheckItem(cat, "tolerance_e", hook.toleranceMeasure.e)
        addCheckItem(cat, "tolerance_f", hook.toleranceMeasure.f)
        addCheckItem(cat, "tolerance_g", hook.toleranceMeasure.g)
        addCheckItem(cat, "tolerance_h", hook.toleranceMeasure.h)
        addResultStatusCheckItem(cat, "tolerance_finding", hook.toleranceMeasure.result)
    }

    it.ndtMainDrum.let { drum ->
        val cat = "nde_mainDrum"
        addCheckItem(cat, "ndtType", drum.ndtType)
        addCheckItem(cat, "spec_a", drum.specification.a)
        addCheckItem(cat, "spec_b", drum.specification.b)
        addCheckItem(cat, "spec_c", drum.specification.c)
        addCheckItem(cat, "spec_d", drum.specification.d)
        addCheckItem(cat, "spec_e", drum.specification.e)
        addCheckItem(cat, "spec_f", drum.specification.f)
        addCheckItem(cat, "spec_g", drum.specification.g)
        addResultStatusCheckItem(cat, "spec_finding", drum.specification.result)
        addCheckItem(cat, "result_a", drum.measurementResults.a)
        addCheckItem(cat, "result_b", drum.measurementResults.b)
        addCheckItem(cat, "result_c", drum.measurementResults.c)
        addCheckItem(cat, "result_d", drum.measurementResults.d)
        addCheckItem(cat, "result_e", drum.measurementResults.e)
        addCheckItem(cat, "result_f", drum.measurementResults.f)
        addCheckItem(cat, "result_g", drum.measurementResults.g)
        addResultStatusCheckItem(cat, "result_finding", drum.measurementResults.result)
    }

    it.ndtAuxiliaryDrum.let { drum ->
        val cat = "nde_auxDrum"
        addCheckItem(cat, "ndtType", drum.ndtType)
        addCheckItem(cat, "spec_a", drum.specification.a)
        addCheckItem(cat, "spec_b", drum.specification.b)
        addCheckItem(cat, "spec_c", drum.specification.c)
        addCheckItem(cat, "spec_d", drum.specification.d)
        addCheckItem(cat, "spec_e", drum.specification.e)
        addCheckItem(cat, "spec_f", drum.specification.f)
        addCheckItem(cat, "spec_g", drum.specification.g)
        addResultStatusCheckItem(cat, "spec_finding", drum.specification.result)
        addCheckItem(cat, "result_a", drum.measurementResults.a)
        addCheckItem(cat, "result_b", drum.measurementResults.b)
        addCheckItem(cat, "result_c", drum.measurementResults.c)
        addCheckItem(cat, "result_d", drum.measurementResults.d)
        addCheckItem(cat, "result_e", drum.measurementResults.e)
        addCheckItem(cat, "result_f", drum.measurementResults.f)
        addCheckItem(cat, "result_g", drum.measurementResults.g)
        addResultStatusCheckItem(cat, "result_finding", drum.measurementResults.result)
    }

    it.ndtMainPulley.let { pulley ->
        val cat = "nde_mainPulley"
        addCheckItem(cat, "ndtType", pulley.ndtType)
        addCheckItem(cat, "spec_a", pulley.specification.a)
        addCheckItem(cat, "spec_b", pulley.specification.b)
        addCheckItem(cat, "spec_c", pulley.specification.c)
        addCheckItem(cat, "spec_d", pulley.specification.d)
        addCheckItem(cat, "spec_e", pulley.specification.e)
        addResultStatusCheckItem(cat, "spec_finding", pulley.specification.result)
        addCheckItem(cat, "result_a", pulley.measurementResults.a)
        addCheckItem(cat, "result_b", pulley.measurementResults.b)
        addCheckItem(cat, "result_c", pulley.measurementResults.c)
        addCheckItem(cat, "result_d", pulley.measurementResults.d)
        addCheckItem(cat, "result_e", pulley.measurementResults.e)
        addResultStatusCheckItem(cat, "result_finding", pulley.measurementResults.result)
    }

    it.ndtAuxiliaryPulley.let { pulley ->
        val cat = "nde_auxPulley"
        addCheckItem(cat, "ndtType", pulley.ndtType)
        addCheckItem(cat, "spec_a", pulley.specification.a)
        addCheckItem(cat, "spec_b", pulley.specification.b)
        addCheckItem(cat, "spec_c", pulley.specification.c)
        addCheckItem(cat, "spec_d", pulley.specification.d)
        addCheckItem(cat, "spec_e", pulley.specification.e)
        addResultStatusCheckItem(cat, "spec_finding", pulley.specification.result)
        addCheckItem(cat, "result_a", pulley.measurementResults.a)
        addCheckItem(cat, "result_b", pulley.measurementResults.b)
        addCheckItem(cat, "result_c", pulley.measurementResults.c)
        addCheckItem(cat, "result_d", pulley.measurementResults.d)
        addCheckItem(cat, "result_e", pulley.measurementResults.e)
        addResultStatusCheckItem(cat, "result_finding", pulley.measurementResults.result)
    }

    // --- Inspection and Testing (Functional & Load Tests) ---
    val funcTestCategory = "testing_function"
    val funcTest = it.testingFunction
    addResultStatusCheckItem(funcTestCategory, "hoistingLowering", funcTest.hoistingLowering)
    addResultStatusCheckItem(funcTestCategory, "extendedRetractedBoom", funcTest.extendedRetractedBoom)
    addResultStatusCheckItem(funcTestCategory, "extendedRetractedOutrigger", funcTest.extendedRetractedOutrigger)
    addResultStatusCheckItem(funcTestCategory, "swingSlewing", funcTest.swingSlewing)
    addResultStatusCheckItem(funcTestCategory, "antiTwoBlock", funcTest.antiTwoBlock)
    addResultStatusCheckItem(funcTestCategory, "boomStop", funcTest.boomStop)
    addResultStatusCheckItem(funcTestCategory, "anemometerWindSpeed", funcTest.anemometerWindSpeed)
    addResultStatusCheckItem(funcTestCategory, "brakeLockingDevice", funcTest.brakeLockingDevice)
    addResultStatusCheckItem(funcTestCategory, "loadMomentIndicator", funcTest.loadMomentIndicator)
    addResultStatusCheckItem(funcTestCategory, "turnSignal", funcTest.turnSignal)
    addResultStatusCheckItem(funcTestCategory, "drivingLights", funcTest.drivingLights)
    addResultStatusCheckItem(funcTestCategory, "loadIndicatorLight", funcTest.loadIndicatorLight)
    addResultStatusCheckItem(funcTestCategory, "rotaryLamp", funcTest.rotaryLamp)
    addResultStatusCheckItem(funcTestCategory, "horn", funcTest.horn)
    addResultStatusCheckItem(funcTestCategory, "swingAlarm", funcTest.swingAlarm)
    addResultStatusCheckItem(funcTestCategory, "reverseAlarm", funcTest.reverseAlarm)
    addResultStatusCheckItem(funcTestCategory, "overloadAlarm", funcTest.overloadAlarm)

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
        driveType = "", // This data is lost because it's not present in the DTO.
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

/**
 * Maps an [InspectionWithDetailsDomain] to a [MobileCraneReportRequest] DTO for API submission.
 * This function reconstructs the nested DTO from the flattened domain model.
 */
fun InspectionWithDetailsDomain.toMobileCraneReportRequest(): MobileCraneReportRequest {
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    // Helper functions to retrieve data from the flattened check items list
    fun getCheckItemValue(category: String, itemName: String, default: String = ""): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: default
    }

    fun getBooleanStatus(category: String, itemName: String, default: Boolean = false): Boolean {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.status ?: default
    }

    fun getResultStatus(category: String, itemName: String): ResultStatus {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return ResultStatus(status = item?.status ?: false, result = item?.result ?: "")
    }

    fun getIndexedCheckItemValue(prefix: String, index: Int, itemName: String, default: String = ""): String {
        val category = "${prefix}_${index}"
        return getCheckItemValue(category, itemName, default)
    }

    fun getIndexedResultStatus(prefix: String, index: Int, itemName: String): ResultStatus {
        val category = "${prefix}_${index}"
        return getResultStatus(category, itemName)
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
        // NOTE: `driveType` from the domain model is lost here because the Request DTO does not have a field for it.
        // To fix this, a `driveType` field would need to be added to the `MobileCraneGeneralData` DTO class.
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
        type = getCheckItemValue(techCategory, "motor_type"),
        numberOfCylinders = getCheckItemValue(techCategory, "motor_numberOfCylinders"),
        netPower = getCheckItemValue(techCategory, "motor_netPower"),
        brandYearOfManufacture = getCheckItemValue(techCategory, "motor_brandYearOfManufacture"),
        manufacturer = getCheckItemValue(techCategory, "motor_manufacturer"),
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
    val funcTestCategory = "testing_function"

    // NDE Wire Rope
    val ndeWireRopeItems = checkItemsByCategory.keys
        .filter { it.startsWith("nde_wireRope_item_") }
        .mapNotNull { it.substringAfterLast('_').toIntOrNull() }
        .distinct()
        .map { index ->
            NdtSteelWireRopeItem(
                usageAt = getIndexedCheckItemValue("nde_wireRope_item", index, "usage"),
                specDiameter = getIndexedCheckItemValue("nde_wireRope_item", index, "specDiameter"),
                actualDiameter = getIndexedCheckItemValue("nde_wireRope_item", index, "actualDiameter"),
                construction = getIndexedCheckItemValue("nde_wireRope_item", index, "construction"),
                type = getIndexedCheckItemValue("nde_wireRope_item", index, "type"),
                length = getIndexedCheckItemValue("nde_wireRope_item", index, "length"),
                age = getIndexedCheckItemValue("nde_wireRope_item", index, "age"),
                result = ResultStatus(
                    status = getBooleanStatus("nde_wireRope_item_$index", "hasDefect"),
                    result = getIndexedCheckItemValue("nde_wireRope_item", index, "remarks")
                )
            )
        }

    // NDE Boom
    val ndeBoomItems = checkItemsByCategory.keys
        .filter { it.startsWith("nde_boom_item_") }
        .mapNotNull { it.substringAfterLast('_').toIntOrNull() }
        .distinct()
        .map { index ->
            NdtBoomInspection(
                part = getIndexedCheckItemValue("nde_boom_item", index, "partInspected"),
                location = getIndexedCheckItemValue("nde_boom_item", index, "location"),
                result = ResultStatus(
                    status = getBooleanStatus("nde_boom_item_$index", "hasDefect"),
                    result = getIndexedCheckItemValue("nde_boom_item", index, "remarks")
                )
            )
        }

    val inspectionAndTesting = MobileCraneInspectionAndTesting(
        // Visual Checks
        visualFoundationAndBolts = VisualFoundationAndBolts(
            corrosion = getResultStatus(visualCategory, "foundationAnchorBoltCorrosion"),
            cracks = getResultStatus(visualCategory, "foundationAnchorBoltCracks"),
            deformation = getResultStatus(visualCategory, "foundationAnchorBoltDeformation"),
            tightness = getResultStatus(visualCategory, "foundationAnchorBoltTightness")
        ),
        visualFrameColumnsOnFoundation = VisualFrameColumnsOnFoundation(
            corrosion = getResultStatus(visualCategory, "frameColumnsOnFoundationCorrosion"),
            cracks = getResultStatus(visualCategory, "frameColumnsOnFoundationCracks"),
            deformation = getResultStatus(visualCategory, "frameColumnsOnFoundationDeformation"),
            fastening = getResultStatus(visualCategory, "frameColumnsOnFoundationFastening"),
            transverseReinforcement = getResultStatus(visualCategory, "frameColumnsOnFoundationTransverseReinforcement"),
            diagonalReinforcement = getResultStatus(visualCategory, "frameColumnsOnFoundationDiagonalReinforcement")
        ),
        visualLadder = VisualLadder(
            corrosion = getResultStatus(visualCategory, "ladderCorrosion"),
            cracks = getResultStatus(visualCategory, "ladderCracks"),
            deformation = getResultStatus(visualCategory, "ladderDeformation"),
            fastening = getResultStatus(visualCategory, "ladderFastening")
        ),
        visualWorkingPlatform = VisualWorkingPlatform(
            corrosion = getResultStatus(visualCategory, "workingPlatformCorrosion"),
            cracks = getResultStatus(visualCategory, "workingPlatformCracks"),
            deformation = getResultStatus(visualCategory, "workingPlatformDeformation"),
            fastening = getResultStatus(visualCategory, "workingPlatformFastening")
        ),
        visualOutriggers = VisualOutriggers(
            outriggerArmHousing = getResultStatus(visualCategory, "outriggersOutriggerArmHousing"),
            outriggerArms = getResultStatus(visualCategory, "outriggersOutriggerArms"),
            jack = getResultStatus(visualCategory, "outriggersJack"),
            outriggerPads = getResultStatus(visualCategory, "outriggersOutriggerPads"),
            housingConnectionToChassis = getResultStatus(visualCategory, "outriggersHousingConnectionToChassis"),
            outriggerSafetyLocks = getResultStatus(visualCategory, "outriggersOutriggerSafetyLocks")
        ),
        visualTurntable = VisualTurntable(
            slewingRollerBearing = getResultStatus(visualCategory, "turntableSlewingRollerBearing"),
            brakeHousing = getResultStatus(visualCategory, "turntableBrakeHousing"),
            brakeLiningsAndShoes = getResultStatus(visualCategory, "turntableBrakeLiningsAndShoes"),
            drumSurface = getResultStatus(visualCategory, "turntableDrumSurface"),
            pressureCylinder = getResultStatus(visualCategory, "turntablePressureCylinder"),
            drumAxle = getResultStatus(visualCategory, "turntableDrumAxle"),
            leversPinsBolts = getResultStatus(visualCategory, "turntableLeversPinsBolts"),
            guard = getResultStatus(visualCategory, "turntableGuard")
        ),
        visualLatticeBoom = VisualLatticeBoom(
            mainBoom = getResultStatus(visualCategory, "latticeBoomMainBoom"),
            boomSection = getResultStatus(visualCategory, "latticeBoomBoomSection"),
            topPulley = getResultStatus(visualCategory, "latticeBoomTopPulley"),
            pulleyGuard = getResultStatus(visualCategory, "latticeBoomPulleyGuard"),
            wireRopeGuard = getResultStatus(visualCategory, "latticeBoomWireRopeGuard"),
            pulleyGrooveLip = getResultStatus(visualCategory, "latticeBoomPulleyGrooveLip"),
            pivotPin = getResultStatus(visualCategory, "latticeBoomPivotPin"),
            wireRopeGuidePulley = getResultStatus(visualCategory, "latticeBoomWireRopeGuidePulley")
        ),
        visualSteering = VisualSteering(
            mainClutch = getResultStatus(visualCategory, "clutchMainClutch"),
            transmission = getResultStatus(visualCategory, "transmission"),
            frontWheel = getResultStatus(visualCategory, "steeringFrontWheel"),
            middleWheel = getResultStatus(visualCategory, "steeringMiddleWheel"),
            rearWheel = getResultStatus(visualCategory, "steeringRearWheel")
        ),
        visualBrake = VisualBrake(
            serviceBrake = getResultStatus(visualCategory, "brakeServiceBrake"),
            parkingBrake = getResultStatus(visualCategory, "brakeParkingBrake"),
            brakeHousing = getResultStatus(visualCategory, "brakeBrakeHousing"),
            brakeLiningsAndShoes = getResultStatus(visualCategory, "brakeBrakeLiningsAndShoes"),
            drumSurface = getResultStatus(visualCategory, "brakeDrumSurface"),
            leversPinsBolts = getResultStatus(visualCategory, "brakeLeversPinsBolts"),
            guard = getResultStatus(visualCategory, "brakeGuard")
        ),
        visualTravelDrum = VisualTravelDrum(
            clutchHousing = getResultStatus(visualCategory, "travelDrumClutchHousing"),
            clutchLining = getResultStatus(visualCategory, "travelDrumClutchLining"),
            clutchDrumSurface = getResultStatus(visualCategory, "travelDrumClutchDrumSurface"),
            leversPinsBolts = getResultStatus(visualCategory, "travelDrumLeversPinsBolts"),
            guard = getResultStatus(visualCategory, "travelDrumGuard")
        ),
        visualMainWinch = VisualMainWinch(
            drumMounting = getResultStatus(visualCategory, "mainWinchDrumMounting"),
            windingDrumSurface = getResultStatus(visualCategory, "mainWinchWindingDrumSurface"),
            brakeLiningsAndShoes = getResultStatus(visualCategory, "mainWinchBrakeLiningsAndShoes"),
            brakeDrumSurface = getResultStatus(visualCategory, "mainWinchBrakeDrumSurface"),
            brakeHousing = getResultStatus(visualCategory, "mainWinchBrakeHousing"),
            clutchLiningsAndShoes = getResultStatus(visualCategory, "mainWinchClutchLiningsAndShoes"),
            clutchDrumSurface = getResultStatus(visualCategory, "mainWinchClutchDrumSurface"),
            groove = getResultStatus(visualCategory, "mainWinchGroove"),
            grooveLip = getResultStatus(visualCategory, "mainWinchGrooveLip"),
            flanges = getResultStatus(visualCategory, "mainWinchFlanges"),
            brakeActuatorLeversPinsAndBolts = getResultStatus(visualCategory, "mainWinchBrakeActuatorLeversPinsAndBolts")
        ),
        visualAuxiliaryWinch = VisualAuxiliaryWinch(
            drumMounting = getResultStatus(visualCategory, "auxiliaryWinchDrumMounting"),
            windingDrumSurface = getResultStatus(visualCategory, "auxiliaryWinchWindingDrumSurface"),
            brakeLiningsAndShoes = getResultStatus(visualCategory, "auxiliaryWinchBrakeLiningsAndShoes"),
            brakeDrumSurface = getResultStatus(visualCategory, "auxiliaryWinchBrakeDrumSurface"),
            brakeHousing = getResultStatus(visualCategory, "auxiliaryWinchBrakeHousing"),
            clutchLiningsAndShoes = getResultStatus(visualCategory, "auxiliaryWinchClutchLiningsAndShoes"),
            clutchDrumSurface = getResultStatus(visualCategory, "auxiliaryWinchClutchDrumSurface"),
            groove = getResultStatus(visualCategory, "auxiliaryWinchGroove"),
            grooveLip = getResultStatus(visualCategory, "auxiliaryWinchGrooveLip"),
            flanges = getResultStatus(visualCategory, "auxiliaryWinchFlanges"),
            brakeActuatorLeversPinsAndBolts = getResultStatus(visualCategory, "auxiliaryWinchBrakeActuatorLeversPinsAndBolts")
        ),
        visualHoistGearBlock = VisualHoistGearBlock(
            lubrication = getResultStatus(visualCategory, "hoistGearBlockLubrication"),
            oilSeal = getResultStatus(visualCategory, "hoistGearBlockOilSeal")
        ),
        visualMainPulley = VisualMainPulley(
            pulleyGroove = getResultStatus(visualCategory, "mainPulleyPulleyGroove"),
            pulleyGrooveLip = getResultStatus(visualCategory, "mainPulleyPulleyGrooveLip"),
            pulleyPin = getResultStatus(visualCategory, "mainPulleyPulleyPin"),
            bearing = getResultStatus(visualCategory, "mainPulleyBearing"),
            pulleyGuard = getResultStatus(visualCategory, "mainPulleyPulleyGuard"),
            wireRopeGuard = getResultStatus(visualCategory, "mainPulleyWireRopeGuard")
        ),
        visualMainHook = VisualMainHook(
            swivelNutAndBearing = getResultStatus(visualCategory, "mainHookVisualSwivelNutAndBearing"),
            trunnion = getResultStatus(visualCategory, "mainHookVisualTrunnion"),
            safetyLatch = getResultStatus(visualCategory, "mainHookVisualSafetyLatch")
        ),
        visualAuxiliaryHook = VisualAuxiliaryHook(
            freeFallWeight = getResultStatus(visualCategory, "auxiliaryHookVisualFreeFallWeight"),
            swivelNutAndBearing = getResultStatus(visualCategory, "auxiliaryHookVisualSwivelNutAndBearing"),
            safetyLatch = getResultStatus(visualCategory, "auxiliaryHookVisualSafetyLatch")
        ),
        visualMainWireRope = VisualWireRope(
            corrosion = getResultStatus(visualCategory, "mainWireRopeVisualCorrosion"),
            wear = getResultStatus(visualCategory, "mainWireRopeVisualWear"),
            breakage = getResultStatus(visualCategory, "mainWireRopeVisualBreakage"),
            deformation = getResultStatus(visualCategory, "mainWireRopeVisualDeformation")
        ),
        visualAuxiliaryWireRope = VisualWireRope(
            corrosion = getResultStatus(visualCategory, "auxiliaryWireRopeVisualCorrosion"),
            wear = getResultStatus(visualCategory, "auxiliaryWireRopeVisualWear"),
            breakage = getResultStatus(visualCategory, "auxiliaryWireRopeVisualBreakage"),
            deformation = getResultStatus(visualCategory, "auxiliaryWireRopeVisualDeformation")
        ),
        visualLimitSwitch = VisualLimitSwitch(
            longTravel = getResultStatus(visualCategory, "limitSwitchLsLongTravel"),
            crossTravel = getResultStatus(visualCategory, "limitSwitchLsCrossTravel"),
            hoisting = getResultStatus(visualCategory, "limitSwitchLsHoisting")
        ),
        visualInternalCombustionEngine = VisualInternalCombustionEngine(
            coolingSystem = getResultStatus(visualCategory, "internalCombustionEngineCoolingSystem"),
            lubricationSystem = getResultStatus(visualCategory, "internalCombustionEngineLubricationSystem"),
            engineMounting = getResultStatus(visualCategory, "internalCombustionEngineEngineMounting"),
            safetyGuardEquipment = getResultStatus(visualCategory, "internalCombustionEngineSafetyGuardEquipment"),
            exhaustSystem = getResultStatus(visualCategory, "internalCombustionEngineExhaustSystem"),
            fuelSystem = getResultStatus(visualCategory, "internalCombustionEngineFuelSystem"),
            powerTransmissionSystem = getResultStatus(visualCategory, "internalCombustionEnginePowerTransmissionSystem"),
            battery = getResultStatus(visualCategory, "internalCombustionEngineBattery"),
            starterMotor = getResultStatus(visualCategory, "internalCombustionEngineStarterMotor"),
            wiringInstallation = getResultStatus(visualCategory, "internalCombustionEngineWiringInstallation"),
            turbocharger = getResultStatus(visualCategory, "internalCombustionEngineTurbocharger")
        ),
        visualHydraulic = VisualHydraulic(
            pump = getResultStatus(visualCategory, "hydraulicHydraulicPump"),
            lines = getResultStatus(visualCategory, "hydraulicHydraulicLines"),
            filter = getResultStatus(visualCategory, "hydraulicHydraulicFilter"),
            tank = getResultStatus(visualCategory, "hydraulicHydraulicTank"),
            mainWinchMotor = getResultStatus(visualCategory, "hydraulicMotorMainWinchMotor"),
            auxiliaryWinchMotor = getResultStatus(visualCategory, "hydraulicMotorAuxiliaryWinchMotor"),
            boomWinchMotor = getResultStatus(visualCategory, "hydraulicMotorBoomWinchMotor"),
            swingMotor = getResultStatus(visualCategory, "hydraulicMotorSwingMotor")
        ),
        visualControlValve = VisualControlValve(
            reliefValve = getResultStatus(visualCategory, "controlValveReliefValve"),
            mainWinchValve = getResultStatus(visualCategory, "controlValveMainWinchValve"),
            auxiliaryWinchValve = getResultStatus(visualCategory, "controlValveAuxiliaryWinchValve"),
            boomWinchValve = getResultStatus(visualCategory, "controlValveBoomWinchValve"),
            boomMovementValve = getResultStatus(visualCategory, "controlValveBoomMovementValve"),
            steeringCylinderValve = getResultStatus(visualCategory, "controlValveSteeringCylinderValve"),
            axleOscillationValve = getResultStatus(visualCategory, "controlValveAxleOscillationValve"),
            outriggerMovementValve = getResultStatus(visualCategory, "controlValveOutriggerMovementValve")
        ),
        visualHydraulicCylinder = VisualHydraulicCylinder(
            boomMovementCylinder = getResultStatus(visualCategory, "hydraulicCylinderBoomMovementCylinder"),
            outriggerCylinder = getResultStatus(visualCategory, "hydraulicCylinderOutriggerCylinder"),
            steeringWheelCylinder = getResultStatus(visualCategory, "hydraulicCylinderSteeringWheelCylinder"),
            axleOscillationCylinder = getResultStatus(visualCategory, "hydraulicCylinderAxleOscillationCylinder"),
            telescopicCylinder = getResultStatus(visualCategory, "hydraulicCylinderTelescopicCylinder")
        ),
        visualPneumatic = VisualPneumatic(
            compressor = getResultStatus(visualCategory, "pneumaticCompressor"),
            tankAndSafetyValve = getResultStatus(visualCategory, "pneumaticTankAndSafetyValve"),
            pressurizedAirLines = getResultStatus(visualCategory, "pneumaticPressurizedAirLines"),
            airFilter = getResultStatus(visualCategory, "pneumaticAirFilter"),
            controlValve = getResultStatus(visualCategory, "pneumaticControlValve")
        ),
        visualOperatorCabin = VisualOperatorCabin(
            safetyLadder = getResultStatus(visualCategory, "operatorCabinSafetyLadder"),
            door = getResultStatus(visualCategory, "operatorCabinDoor"),
            window = getResultStatus(visualCategory, "operatorCabinWindow"),
            fanAc = getResultStatus(visualCategory, "operatorCabinFanAc"),
            controlLeversButtons = getResultStatus(visualCategory, "operatorCabinControlLeversButtons"),
            pendantControl = getResultStatus(visualCategory, "operatorCabinPendantControl"),
            lighting = getResultStatus(visualCategory, "operatorCabinLighting"),
            hornSignalAlarm = getResultStatus(visualCategory, "operatorCabinHornSignalAlarm"),
            fuse = getResultStatus(visualCategory, "operatorCabinFuse"),
            communicationDevice = getResultStatus(visualCategory, "operatorCabinCommunicationDevice"),
            fireExtinguisher = getResultStatus(visualCategory, "operatorCabinFireExtinguisher"),
            operatingSigns = getResultStatus(visualCategory, "operatorCabinOperatingSigns"),
            ignitionKeyMasterSwitch = getResultStatus(visualCategory, "operatorCabinIgnitionKeyMasterSwitch"),
            buttonsHandlesLevers = getResultStatus(visualCategory, "operatorCabinButtonsHandlesLevers")
        ),
        visualElectricalComponents = VisualElectricalComponents(
            panelConductorConnector = getResultStatus(visualCategory, "electricalComponentsPanelConductorConnector"),
            conductorProtection = getResultStatus(visualCategory, "electricalComponentsConductorProtection"),
            motorInstallationSafetySystem = getResultStatus(visualCategory, "electricalComponentsMotorInstallationSafetySystem"),
            groundingSystem = getResultStatus(visualCategory, "electricalComponentsGroundingSystem"),
            installation = getResultStatus(visualCategory, "electricalComponentsInstallation")
        ),
        visualSafetyDevices = VisualSafetyDevices(
            ladderHandrail = getResultStatus(visualCategory, "safetyDevicesLadderHandrail"),
            engineOilLubricantPressure = getResultStatus(visualCategory, "safetyDevicesEngineOilLubricantPressure"),
            hydraulicOilPressure = getResultStatus(visualCategory, "safetyDevicesHydraulicOilPressure"),
            airPressure = getResultStatus(visualCategory, "safetyDevicesAirPressure"),
            amperemeter = getResultStatus(visualCategory, "safetyDevicesAmperemeter"),
            voltage = getResultStatus(visualCategory, "safetyDevicesVoltage"),
            engineTemperature = getResultStatus(visualCategory, "safetyDevicesEngineTemperature"),
            transmissionTemperature = getResultStatus(visualCategory, "safetyDevicesTransmissionTemperature"),
            converterOilTemperaturePressure = getResultStatus(visualCategory, "safetyDevicesConverterOilTemperaturePressure"),
            converterSpeedometerIndicator = getResultStatus(visualCategory, "safetyDevicesSpeedometerIndicator"),
            converterRotaryLamp = getResultStatus(visualCategory, "safetyDevicesRotaryLamp"),
            converterMainHoistRopeUpDownLimit = getResultStatus(visualCategory, "safetyDevicesMainHoistRopeUpDownLimit"),
            converterAuxiliaryHoistRopeUpDownLimit = getResultStatus(visualCategory, "safetyDevicesAuxiliaryHoistRopeUpDownLimit"),
            converterSwingMotionLimit = getResultStatus(visualCategory, "safetyDevicesSwingMotionLimit"),
            converterLevelIndicator = getResultStatus(visualCategory, "safetyDevicesLevelIndicator"),
            converterLoadWeightIndicator = getResultStatus(visualCategory, "safetyDevicesLoadWeightIndicator"),
            converterLoadChart = getResultStatus(visualCategory, "safetyDevicesLoadChart"),
            converterAnemometerWindSpeed = getResultStatus(visualCategory, "safetyDevicesAnemometerWindSpeed"),
            converterBoomAngleIndicator = getResultStatus(visualCategory, "safetyDevicesBoomAngleIndicator"),
            converterAirPressureIndicator = getResultStatus(visualCategory, "safetyDevicesAirPressureIndicator"),
            converterHydraulicPressureIndicator = getResultStatus(visualCategory, "safetyDevicesHydraulicPressureIndicator"),
            converterSafetyValves = getResultStatus(visualCategory, "safetyDevicesSafetyValves"),
            converterMainWindingDrumSafetyLock = getResultStatus(visualCategory, "safetyDevicesMainWindingDrumSafetyLock"),
            converterAuxiliaryWindingDrumSafetyLock = getResultStatus(visualCategory, "safetyDevicesAuxiliaryWindingDrumSafetyLock"),
            converterTelescopicMotionLimit = getResultStatus(visualCategory, "safetyDevicesTelescopicMotionLimit"),
            converterLightningArrester = getResultStatus(visualCategory, "safetyDevicesLightningArrester"),
            converterLiftingHeightIndicator = getResultStatus(visualCategory, "safetyDevicesLiftingHeightIndicator")
        ),

        // NDE
        ndtSteelWireRope = NdtSteelWireRope(
            ndtType = getCheckItemValue("nde_wireRope", "ndtType"),
            ropes = ndeWireRopeItems
        ),
        ndtBoom = NdtBoom(
            boomType = getCheckItemValue("nde_boom", "boomType"),
            ndtType = getCheckItemValue("nde_boom", "ndtType"),
            inspections = ndeBoomItems
        ),
        ndtMainHook = NdtHook(
            ndtType = getCheckItemValue("nde_mainHook", "ndtType"),
            capacity = getCheckItemValue("nde_mainHook", "hookCapacity"),
            specification = NdtSpecification(
                a = getCheckItemValue("nde_mainHook", "spec_a"), b = getCheckItemValue("nde_mainHook", "spec_b"),
                c = getCheckItemValue("nde_mainHook", "spec_c"), d = getCheckItemValue("nde_mainHook", "spec_d"),
                e = getCheckItemValue("nde_mainHook", "spec_e"), f = getCheckItemValue("nde_mainHook", "spec_f"),
                g = getCheckItemValue("nde_mainHook", "spec_g"), h = getCheckItemValue("nde_mainHook", "spec_h"),
                result = getResultStatus("nde_mainHook", "spec_finding")
            ),
            measurementResults = NdtMeasurement(
                a = getCheckItemValue("nde_mainHook", "result_a"), b = getCheckItemValue("nde_mainHook", "result_b"),
                c = getCheckItemValue("nde_mainHook", "result_c"), d = getCheckItemValue("nde_mainHook", "result_d"),
                e = getCheckItemValue("nde_mainHook", "result_e"), f = getCheckItemValue("nde_mainHook", "result_f"),
                g = getCheckItemValue("nde_mainHook", "result_g"), h = getCheckItemValue("nde_mainHook", "result_h"),
                result = getResultStatus("nde_mainHook", "result_finding")
            ),
            toleranceMeasure = NdtMeasurement(
                a = getCheckItemValue("nde_mainHook", "tolerance_a"), b = getCheckItemValue("nde_mainHook", "tolerance_b"),
                c = getCheckItemValue("nde_mainHook", "tolerance_c"), d = getCheckItemValue("nde_mainHook", "tolerance_d"),
                e = getCheckItemValue("nde_mainHook", "tolerance_e"), f = getCheckItemValue("nde_mainHook", "tolerance_f"),
                g = getCheckItemValue("nde_mainHook", "tolerance_g"), h = getCheckItemValue("nde_mainHook", "tolerance_h"),
                result = getResultStatus("nde_mainHook", "tolerance_finding")
            )
        ),
        ndtAuxiliaryHook = NdtHook(
            ndtType = getCheckItemValue("nde_auxHook", "ndtType"),
            capacity = getCheckItemValue("nde_auxHook", "hookCapacity"),
            specification = NdtSpecification(
                a = getCheckItemValue("nde_auxHook", "spec_a"), b = getCheckItemValue("nde_auxHook", "spec_b"),
                c = getCheckItemValue("nde_auxHook", "spec_c"), d = getCheckItemValue("nde_auxHook", "spec_d"),
                e = getCheckItemValue("nde_auxHook", "spec_e"), f = getCheckItemValue("nde_auxHook", "spec_f"),
                g = getCheckItemValue("nde_auxHook", "spec_g"), h = getCheckItemValue("nde_auxHook", "spec_h"),
                result = getResultStatus("nde_auxHook", "spec_finding")
            ),
            measurementResults = NdtMeasurement(
                a = getCheckItemValue("nde_auxHook", "result_a"), b = getCheckItemValue("nde_auxHook", "result_b"),
                c = getCheckItemValue("nde_auxHook", "result_c"), d = getCheckItemValue("nde_auxHook", "result_d"),
                e = getCheckItemValue("nde_auxHook", "result_e"), f = getCheckItemValue("nde_auxHook", "result_f"),
                g = getCheckItemValue("nde_auxHook", "result_g"), h = getCheckItemValue("nde_auxHook", "result_h"),
                result = getResultStatus("nde_auxHook", "result_finding")
            ),
            toleranceMeasure = NdtMeasurement(
                a = getCheckItemValue("nde_auxHook", "tolerance_a"), b = getCheckItemValue("nde_auxHook", "tolerance_b"),
                c = getCheckItemValue("nde_auxHook", "tolerance_c"), d = getCheckItemValue("nde_auxHook", "tolerance_d"),
                e = getCheckItemValue("nde_auxHook", "tolerance_e"), f = getCheckItemValue("nde_auxHook", "tolerance_f"),
                g = getCheckItemValue("nde_auxHook", "tolerance_g"), h = getCheckItemValue("nde_auxHook", "tolerance_h"),
                result = getResultStatus("nde_auxHook", "tolerance_finding")
            )
        ),
        ndtMainDrum = NdtDrum(
            ndtType = getCheckItemValue("nde_mainDrum", "ndtType"),
            capacity = "", // Not available in domain model
            specification = NdtSpecification(
                a = getCheckItemValue("nde_mainDrum", "spec_a"), b = getCheckItemValue("nde_mainDrum", "spec_b"),
                c = getCheckItemValue("nde_mainDrum", "spec_c"), d = getCheckItemValue("nde_mainDrum", "spec_d"),
                e = getCheckItemValue("nde_mainDrum", "spec_e"), f = getCheckItemValue("nde_mainDrum", "spec_f"),
                g = getCheckItemValue("nde_mainDrum", "spec_g"), h = "",
                result = getResultStatus("nde_mainDrum", "spec_finding")
            ),
            measurementResults = NdtMeasurement(
                a = getCheckItemValue("nde_mainDrum", "result_a"), b = getCheckItemValue("nde_mainDrum", "result_b"),
                c = getCheckItemValue("nde_mainDrum", "result_c"), d = getCheckItemValue("nde_mainDrum", "result_d"),
                e = getCheckItemValue("nde_mainDrum", "result_e"), f = getCheckItemValue("nde_mainDrum", "result_f"),
                g = getCheckItemValue("nde_mainDrum", "result_g"), h = "",
                result = getResultStatus("nde_mainDrum", "result_finding")
            )
        ),
        ndtAuxiliaryDrum = NdtDrum(
            ndtType = getCheckItemValue("nde_auxDrum", "ndtType"),
            capacity = "", // Not available in domain model
            specification = NdtSpecification(
                a = getCheckItemValue("nde_auxDrum", "spec_a"), b = getCheckItemValue("nde_auxDrum", "spec_b"),
                c = getCheckItemValue("nde_auxDrum", "spec_c"), d = getCheckItemValue("nde_auxDrum", "spec_d"),
                e = getCheckItemValue("nde_auxDrum", "spec_e"), f = getCheckItemValue("nde_auxDrum", "spec_f"),
                g = getCheckItemValue("nde_auxDrum", "spec_g"), h = "",
                result = getResultStatus("nde_auxDrum", "spec_finding")
            ),
            measurementResults = NdtMeasurement(
                a = getCheckItemValue("nde_auxDrum", "result_a"), b = getCheckItemValue("nde_auxDrum", "result_b"),
                c = getCheckItemValue("nde_auxDrum", "result_c"), d = getCheckItemValue("nde_auxDrum", "result_d"),
                e = getCheckItemValue("nde_auxDrum", "result_e"), f = getCheckItemValue("nde_auxDrum", "result_f"),
                g = getCheckItemValue("nde_auxDrum", "result_g"), h = "",
                result = getResultStatus("nde_auxDrum", "result_finding")
            )
        ),
        ndtMainPulley = NdtPulley(
            ndtType = getCheckItemValue("nde_mainPulley", "ndtType"),
            capacity = "", // Not available in domain model
            specification = NdtSpecification(
                a = getCheckItemValue("nde_mainPulley", "spec_a"), b = getCheckItemValue("nde_mainPulley", "spec_b"),
                c = getCheckItemValue("nde_mainPulley", "spec_c"), d = getCheckItemValue("nde_mainPulley", "spec_d"),
                e = getCheckItemValue("nde_mainPulley", "spec_e"), f = "", g = "", h = "",
                result = getResultStatus("nde_mainPulley", "spec_finding")
            ),
            measurementResults = NdtMeasurement(
                a = getCheckItemValue("nde_mainPulley", "result_a"), b = getCheckItemValue("nde_mainPulley", "result_b"),
                c = getCheckItemValue("nde_mainPulley", "result_c"), d = getCheckItemValue("nde_mainPulley", "result_d"),
                e = getCheckItemValue("nde_mainPulley", "result_e"), f = "", g = "", h = "",
                result = getResultStatus("nde_mainPulley", "result_finding")
            )
        ),
        ndtAuxiliaryPulley = NdtPulley(
            ndtType = getCheckItemValue("nde_auxPulley", "ndtType"),
            capacity = "", // Not available in domain model
            specification = NdtSpecification(
                a = getCheckItemValue("nde_auxPulley", "spec_a"), b = getCheckItemValue("nde_auxPulley", "spec_b"),
                c = getCheckItemValue("nde_auxPulley", "spec_c"), d = getCheckItemValue("nde_auxPulley", "spec_d"),
                e = getCheckItemValue("nde_auxPulley", "spec_e"), f = "", g = "", h = "",
                result = getResultStatus("nde_auxPulley", "spec_finding")
            ),
            measurementResults = NdtMeasurement(
                a = getCheckItemValue("nde_auxPulley", "result_a"), b = getCheckItemValue("nde_auxPulley", "result_b"),
                c = getCheckItemValue("nde_auxPulley", "result_c"), d = getCheckItemValue("nde_auxPulley", "result_d"),
                e = getCheckItemValue("nde_auxPulley", "result_e"), f = "", g = "", h = "",
                result = getResultStatus("nde_auxPulley", "result_finding")
            )
        ),

        // Testing
        testingFunction = TestingFunction(
            hoistingLowering = getResultStatus(funcTestCategory, "hoistingLowering"),
            extendedRetractedBoom = getResultStatus(funcTestCategory, "extendedRetractedBoom"),
            extendedRetractedOutrigger = getResultStatus(funcTestCategory, "extendedRetractedOutrigger"),
            swingSlewing = getResultStatus(funcTestCategory, "swingSlewing"),
            antiTwoBlock = getResultStatus(funcTestCategory, "antiTwoBlock"),
            boomStop = getResultStatus(funcTestCategory, "boomStop"),
            anemometerWindSpeed = getResultStatus(funcTestCategory, "anemometerWindSpeed"),
            brakeLockingDevice = getResultStatus(funcTestCategory, "brakeLockingDevice"),
            loadMomentIndicator = getResultStatus(funcTestCategory, "loadMomentIndicator"),
            turnSignal = getResultStatus(funcTestCategory, "turnSignal"),
            drivingLights = getResultStatus(funcTestCategory, "drivingLights"),
            loadIndicatorLight = getResultStatus(funcTestCategory, "loadIndicatorLight"),
            rotaryLamp = getResultStatus(funcTestCategory, "rotaryLamp"),
            horn = getResultStatus(funcTestCategory, "horn"),
            swingAlarm = getResultStatus(funcTestCategory, "swingAlarm"),
            reverseAlarm = getResultStatus(funcTestCategory, "reverseAlarm"),
            overloadAlarm = getResultStatus(funcTestCategory, "overloadAlarm")
        ),

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