package com.nakersolutionid.nakersolutionid.features.bap.mobilecrane

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import com.nakersolutionid.nakersolutionid.utils.Utils

/**
 * Stores all category and item key names as constants to prevent typos and ensure consistency for BAP.
 * These keys MUST match the ones used in MobileCraneDtoMapper.kt.
 */
private object BapKeys {
    object Category {
        const val VISUAL_CHECK = "BAP_VISUAL_CHECK"
        const val FUNCTIONAL_TEST = "BAP_FUNCTIONAL_TEST"
        const val LOAD_TEST = "BAP_LOAD_TEST"
        const val NDT_TEST = "BAP_NDT_TEST"
        const val SIGNATURE = "BAP_SIGNATURE"
    }

    object Visual {
        const val HAS_BOOM_DEFECTS = "hasBoomDefects"
        const val IS_NAMEPLATE_ATTACHED = "isNameplateAttached"
        const val ARE_BOLTS_AND_NUTS_SECURE = "areBoltsAndNutsSecure"
        const val IS_SLING_GOOD_CONDITION = "isSlingGoodCondition"
        const val IS_HOOK_GOOD_CONDITION = "isHookGoodCondition"
        const val IS_SAFETY_LATCH_INSTALLED = "isSafetyLatchInstalled"
        const val IS_TIRE_GOOD_CONDITION = "isTireGoodCondition"
        const val IS_WORK_LAMP_FUNCTIONAL = "isWorkLampFunctional"
    }

    object Functional {
        const val IS_FORWARD_REVERSE_OK = "isForwardReverseFunctionOk"
        const val IS_SWING_OK = "isSwingFunctionOk"
        const val IS_HOISTING_OK = "isHoistingFunctionOk"
    }

    object LoadTest {
        const val LOAD_KG = "loadKg"
        const val LIFT_HEIGHT_M = "liftHeightMeters"
        const val HOLD_TIME_S = "holdTimeSeconds"
        const val IS_RESULT_GOOD = "isResultGood"
    }

    object NdtTest {
        const val METHOD = "method"
        const val IS_RESULT_GOOD = "isResultGood"
    }

    object Signature {
        const val COMPANY_NAME = "companyName"
    }
}


// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun MobileCraneBAPReport.toInspectionWithDetailsDomain(currentTime: String, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId,
        moreExtraId = this.moreExtraId,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Mobile_Crane,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.usageLocationAddress,
        addressUsageLocation = null,
        driveType = null,
        serialNumber = this.technicalData.serialNumber,
        permitNumber = this.technicalData.materialCertificateNumber,
        capacity = this.technicalData.capacity,
        speed = this.technicalData.liftingSpeed,
        floorServed = this.technicalData.maxLiftingHeight,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = null,
            year = this.technicalData.locationAndYearOfManufacture
        ),
        createdAt = currentTime,
        reportDate = this.inspectionDate,
        status = null,
        isSynced = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.addAll(mapVisualCheckToDomain(this.inspectionResult.visualCheck, inspectionId))
    checkItems.addAll(mapFunctionalTestToDomain(this.inspectionResult.functionalTest, inspectionId))
    checkItems.add(
        InspectionCheckItemDomain(
            inspectionId = inspectionId,
            category = BapKeys.Category.SIGNATURE,
            itemName = BapKeys.Signature.COMPANY_NAME,
            status = false, // status is not relevant for this data
            result = this.signature.companyName
        )
    )

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = emptyList()
    )
}

private fun mapVisualCheckToDomain(uiState: MobileCraneBAPVisualCheck, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = BapKeys.Category.VISUAL_CHECK
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = BapKeys.Visual.HAS_BOOM_DEFECTS, status = uiState.hasBoomDefects, result = null),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = BapKeys.Visual.IS_NAMEPLATE_ATTACHED, status = uiState.isNameplateAttached, result = null),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = BapKeys.Visual.ARE_BOLTS_AND_NUTS_SECURE, status = uiState.areBoltsAndNutsSecure, result = null),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = BapKeys.Visual.IS_SLING_GOOD_CONDITION, status = uiState.isSlingGoodCondition, result = null),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = BapKeys.Visual.IS_HOOK_GOOD_CONDITION, status = uiState.isHookGoodCondition, result = null),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = BapKeys.Visual.IS_SAFETY_LATCH_INSTALLED, status = uiState.isSafetyLatchInstalled, result = null),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = BapKeys.Visual.IS_TIRE_GOOD_CONDITION, status = uiState.isTireGoodCondition, result = null),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = BapKeys.Visual.IS_WORK_LAMP_FUNCTIONAL, status = uiState.isWorkLampFunctional, result = null)
    )
}

private fun mapFunctionalTestToDomain(uiState: MobileCraneBAPFunctionalTest, inspectionId: Long): List<InspectionCheckItemDomain> {
    val funcCat = BapKeys.Category.FUNCTIONAL_TEST
    val loadCat = BapKeys.Category.LOAD_TEST
    val ndtCat = BapKeys.Category.NDT_TEST

    return listOf(
        // Functional Tests - only use 'status'
        InspectionCheckItemDomain(inspectionId = inspectionId, category = funcCat, itemName = BapKeys.Functional.IS_FORWARD_REVERSE_OK, status = uiState.isForwardReverseFunctionOk, result = null),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = funcCat, itemName = BapKeys.Functional.IS_SWING_OK, status = uiState.isSwingFunctionOk, result = null),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = funcCat, itemName = BapKeys.Functional.IS_HOISTING_OK, status = uiState.isHoistingFunctionOk, result = null),

        // Load Test - use 'result' for data and 'status' for boolean result
        InspectionCheckItemDomain(inspectionId = inspectionId, category = loadCat, itemName = BapKeys.LoadTest.LOAD_KG, status = false, result = uiState.loadTest.loadInKg),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = loadCat, itemName = BapKeys.LoadTest.LIFT_HEIGHT_M, status = false, result = uiState.loadTest.liftHeightInMeters),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = loadCat, itemName = BapKeys.LoadTest.HOLD_TIME_S, status = false, result = uiState.loadTest.holdTimeInSeconds),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = loadCat, itemName = BapKeys.LoadTest.IS_RESULT_GOOD, status = uiState.loadTest.isResultGood, result = null),

        // NDT Test - use 'result' for data and 'status' for boolean result
        InspectionCheckItemDomain(inspectionId = inspectionId, category = ndtCat, itemName = BapKeys.NdtTest.METHOD, status = false, result = uiState.ndtTest.method),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = ndtCat, itemName = BapKeys.NdtTest.IS_RESULT_GOOD, status = uiState.ndtTest.isResultGood, result = null)
    )
}


// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toMobileCraneBAPReport(): MobileCraneBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findStringItem(category: String, itemName: String): String {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.result ?: ""
    }

    val generalData = MobileCraneBAPGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        ownerAddress = this.inspection.ownerAddress ?: "",
        usageLocationAddress = this.inspection.usageLocation ?: ""
    )

    val technicalData = MobileCraneBAPTechnicalData(
        manufacturer = this.inspection.manufacturer?.name ?: "",
        locationAndYearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        capacity = this.inspection.capacity ?: "",
        maxLiftingHeight = this.inspection.floorServed ?: "",
        materialCertificateNumber = this.inspection.permitNumber ?: "",
        liftingSpeed = this.inspection.speed ?: ""
    )

    val visualCheck = MobileCraneBAPVisualCheck(
        hasBoomDefects = findBoolItem(BapKeys.Category.VISUAL_CHECK, BapKeys.Visual.HAS_BOOM_DEFECTS),
        isNameplateAttached = findBoolItem(BapKeys.Category.VISUAL_CHECK, BapKeys.Visual.IS_NAMEPLATE_ATTACHED),
        areBoltsAndNutsSecure = findBoolItem(BapKeys.Category.VISUAL_CHECK, BapKeys.Visual.ARE_BOLTS_AND_NUTS_SECURE),
        isSlingGoodCondition = findBoolItem(BapKeys.Category.VISUAL_CHECK, BapKeys.Visual.IS_SLING_GOOD_CONDITION),
        isHookGoodCondition = findBoolItem(BapKeys.Category.VISUAL_CHECK, BapKeys.Visual.IS_HOOK_GOOD_CONDITION),
        isSafetyLatchInstalled = findBoolItem(BapKeys.Category.VISUAL_CHECK, BapKeys.Visual.IS_SAFETY_LATCH_INSTALLED),
        isTireGoodCondition = findBoolItem(BapKeys.Category.VISUAL_CHECK, BapKeys.Visual.IS_TIRE_GOOD_CONDITION),
        isWorkLampFunctional = findBoolItem(BapKeys.Category.VISUAL_CHECK, BapKeys.Visual.IS_WORK_LAMP_FUNCTIONAL)
    )

    val loadTest = MobileCraneBAPLoadTest(
        loadInKg = findStringItem(BapKeys.Category.LOAD_TEST, BapKeys.LoadTest.LOAD_KG),
        liftHeightInMeters = findStringItem(BapKeys.Category.LOAD_TEST, BapKeys.LoadTest.LIFT_HEIGHT_M),
        holdTimeInSeconds = findStringItem(BapKeys.Category.LOAD_TEST, BapKeys.LoadTest.HOLD_TIME_S),
        isResultGood = findBoolItem(BapKeys.Category.LOAD_TEST, BapKeys.LoadTest.IS_RESULT_GOOD)
    )

    val ndtTest = MobileCraneBAPNdtTest(
        method = findStringItem(BapKeys.Category.NDT_TEST, BapKeys.NdtTest.METHOD),
        isResultGood = findBoolItem(BapKeys.Category.NDT_TEST, BapKeys.NdtTest.IS_RESULT_GOOD)
    )

    val functionalTest = MobileCraneBAPFunctionalTest(
        isForwardReverseFunctionOk = findBoolItem(BapKeys.Category.FUNCTIONAL_TEST, BapKeys.Functional.IS_FORWARD_REVERSE_OK),
        isSwingFunctionOk = findBoolItem(BapKeys.Category.FUNCTIONAL_TEST, BapKeys.Functional.IS_SWING_OK),
        isHoistingFunctionOk = findBoolItem(BapKeys.Category.FUNCTIONAL_TEST, BapKeys.Functional.IS_HOISTING_OK),
        loadTest = loadTest,
        ndtTest = ndtTest
    )

    val inspectionResult = MobileCraneBAPInspectionResult(
        visualCheck = visualCheck,
        functionalTest = functionalTest
    )

    val signature = MobileCraneBAPSignature(
        companyName = findStringItem(BapKeys.Category.SIGNATURE, BapKeys.Signature.COMPANY_NAME)
    )

    return MobileCraneBAPReport(
        extraId = this.inspection.extraId,
        moreExtraId = this.inspection.moreExtraId,
        equipmentType = this.inspection.equipmentType,
        examinationType = this.inspection.examinationType,
        inspectionDate = Utils.formatDateToIndonesian(this.inspection.createdAt ?: this.inspection.reportDate ?: ""),
        generalData = generalData,
        technicalData = technicalData,
        inspectionResult = inspectionResult,
        signature = signature
    )
}