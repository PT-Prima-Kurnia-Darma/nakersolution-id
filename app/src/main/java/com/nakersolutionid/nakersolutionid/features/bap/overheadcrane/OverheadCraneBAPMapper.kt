package com.nakersolutionid.nakersolutionid.features.bap.overheadcrane

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import com.nakersolutionid.nakersolutionid.utils.Utils

/**
 * Defines the categories used for check items, aligned with the DTO mapper.
 */
private object OverheadCraneBAPCategory {
    const val VISUAL_INSPECTION = "BAP_VISUAL_INSPECTION"
    const val TESTING = "BAP_TESTING"
    const val LOAD_TEST = "BAP_LOAD_TEST"
    const val NDT_TEST = "BAP_NDT_TEST"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

/**
 * Maps the UI state (OverheadCraneBAPReport) to the central domain model (InspectionWithDetailsDomain).
 * This version uses the corrected, consistent keys for check items.
 */
fun OverheadCraneBAPReport.toInspectionWithDetailsDomain(currentTime: String, isEdited: Boolean, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId,
        moreExtraId = this.moreExtraId,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Overhead_Crane,
        equipmentType = this.subInspectionType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.addressUsageLocation,
        serialNumber = this.technicalData.serialNumber,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.brandOrType,
            year = this.technicalData.manufactureYear
        ),
        capacity = this.technicalData.maxLiftingCapacityInKg,
        speed = this.technicalData.liftingSpeedInMpm,
        createdAt = currentTime,
        reportDate = this.inspectionDate,
        isSynced = false,
        isEdited = isEdited
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.addAll(mapInspectionToDomain(this.testResults.inspection, inspectionId))
    checkItems.addAll(mapTestingToDomain(this.testResults.testing, inspectionId))

    // The 'manufactureCountry' is mapped to testResults as it's a simple key-value data point.
    // This part remains unchanged as it was not causing data loss.
    val testResults = listOf(
        InspectionTestResultDomain(
            inspectionId = inspectionId,
            testName = "manufactureCountry",
            result = this.technicalData.manufactureCountry,
            notes = "DATA TEKNIK"
        )
    )

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = testResults
    )
}

private fun mapInspectionToDomain(uiState: OverheadCraneBAPInspection, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = OverheadCraneBAPCategory.VISUAL_INSPECTION
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "hasConstructionDefects", status = uiState.hasConstructionDefects),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "hookHasSafetyLatch", status = uiState.hookHasSafetyLatch),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "isEmergencyStopInstalled", status = uiState.isEmergencyStopInstalled),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "isWireropeGoodCondition", status = uiState.isWireropeGoodCondition),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "operatorHasK3License", status = uiState.operatorHasK3License)
    )
}

private fun mapTestingToDomain(uiState: OverheadCraneBAPTesting, inspectionId: Long): List<InspectionCheckItemDomain> {
    return mutableListOf<InspectionCheckItemDomain>().apply {
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.TESTING, itemName = "functionTest", status = uiState.functionTest))

        // <-- PERUBAHAN DISINI
        // Load Test - now storing loadInTon as a string result in a dedicated item.
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.LOAD_TEST, itemName = "loadTon", status = false, result = uiState.loadTest.loadInTon))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.LOAD_TEST, itemName = "isAbleToLift", status = uiState.loadTest.isAbleToLift))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.LOAD_TEST, itemName = "hasLoadDrop", status = uiState.loadTest.hasLoadDrop))

        // NDT Hook Test
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.NDT_TEST, itemName = "isNdtResultGood", status = uiState.ndtHookTest.isNdtResultGood))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.NDT_TEST, itemName = "hasCrackIndication", status = uiState.ndtHookTest.hasCrackIndication))
    }
}


// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

/**
 * Maps the central domain model (InspectionWithDetailsDomain) back to the UI state (OverheadCraneBAPReport).
 * This version uses the corrected, consistent keys to find and display the data correctly.
 */
fun InspectionWithDetailsDomain.toOverheadCraneBAPReport(): OverheadCraneBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findStringItem(category: String, itemName: String): String {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.result ?: ""
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = OverheadCraneBAPGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = OverheadCraneBAPTechnicalData(
        brandOrType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturer = this.inspection.manufacturer?.name ?: "",
        manufactureYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        maxLiftingCapacityInKg = this.inspection.capacity ?: "",
        liftingSpeedInMpm = this.inspection.speed ?: "",
        manufactureCountry = findTestResult("manufactureCountry")
    )

    val inspection = OverheadCraneBAPInspection(
        hasConstructionDefects = findBoolItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "hasConstructionDefects"),
        hookHasSafetyLatch = findBoolItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "hookHasSafetyLatch"),
        isEmergencyStopInstalled = findBoolItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "isEmergencyStopInstalled"),
        isWireropeGoodCondition = findBoolItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "isWireropeGoodCondition"),
        operatorHasK3License = findBoolItem(OverheadCraneBAPCategory.VISUAL_INSPECTION, "operatorHasK3License")
    )

    val testing = OverheadCraneBAPTesting(
        functionTest = findBoolItem(OverheadCraneBAPCategory.TESTING, "functionTest"),
        loadTest = OverheadCraneBAPLoadTest(
            // <-- PERUBAHAN DISINI
            loadInTon = findStringItem(OverheadCraneBAPCategory.LOAD_TEST, "loadTon"),
            isAbleToLift = findBoolItem(OverheadCraneBAPCategory.LOAD_TEST, "isAbleToLift"),
            hasLoadDrop = findBoolItem(OverheadCraneBAPCategory.LOAD_TEST, "hasLoadDrop")
        ),
        ndtHookTest = OverheadCraneBAPNdtHookTest(
            isNdtResultGood = findBoolItem(OverheadCraneBAPCategory.NDT_TEST, "isNdtResultGood"),
            hasCrackIndication = findBoolItem(OverheadCraneBAPCategory.NDT_TEST, "hasCrackIndication")
        )
    )

    return OverheadCraneBAPReport(
        extraId = this.inspection.extraId,
        moreExtraId = this.inspection.moreExtraId,
        examinationType = this.inspection.examinationType,
        subInspectionType = this.inspection.equipmentType,
        inspectionDate = Utils.formatDateToIndonesian(this.inspection.createdAt ?: this.inspection.reportDate ?: ""),
        generalData = generalData,
        technicalData = technicalData,
        testResults = OverheadCraneBAPTestResults(inspection = inspection, testing = testing)
    )
}