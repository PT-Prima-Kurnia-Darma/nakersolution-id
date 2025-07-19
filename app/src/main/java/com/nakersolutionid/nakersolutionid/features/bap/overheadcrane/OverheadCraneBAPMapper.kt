package com.nakersolutionid.nakersolutionid.features.bap.overheadcrane

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain

private object OverheadCraneBAPCategory {
    const val TECHNICAL_DATA = "DATA TEKNIK"
    const val INSPECTION = "PEMERIKSAAN"
    const val TESTING = "PENGUJIAN"
    const val TESTING_LOAD_TEST = "$TESTING - Uji Beban"
    const val TESTING_NDT_HOOK = "$TESTING - Uji NDT Hook"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun OverheadCraneBAPReport.toInspectionWithDetailsDomain(currentTime: String, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = "",
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
        isSynced = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.addAll(mapInspectionToDomain(this.testResults.inspection, inspectionId))
    checkItems.addAll(mapTestingToDomain(this.testResults.testing, inspectionId))

    val testResults = mapTechnicalDataToDomain(this.technicalData, inspectionId)

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = testResults
    )
}

private fun mapTechnicalDataToDomain(uiState: OverheadCraneBAPTechnicalData, inspectionId: Long): List<InspectionTestResultDomain> {
    val cat = OverheadCraneBAPCategory.TECHNICAL_DATA
    return listOf(
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Negara Pembuat", result = uiState.manufactureCountry, notes = cat)
    )
}

private fun mapInspectionToDomain(uiState: OverheadCraneBAPInspection, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = OverheadCraneBAPCategory.INSPECTION
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Ada cacat konstruksi", status = uiState.hasConstructionDefects),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Hook memiliki safety latch", status = uiState.hookHasSafetyLatch),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tombol darurat terpasang", status = uiState.isEmergencyStopInstalled),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Kondisi wirerope baik", status = uiState.isWireropeGoodCondition),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Operator memiliki lisensi K3", status = uiState.operatorHasK3License)
    )
}

private fun mapTestingToDomain(uiState: OverheadCraneBAPTesting, inspectionId: Long): List<InspectionCheckItemDomain> {
    return mutableListOf<InspectionCheckItemDomain>().apply {
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.TESTING, itemName = "Uji Fungsi Baik", status = uiState.functionTest))
        // Load Test
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.TESTING_LOAD_TEST, itemName = "Beban dalam Ton", status = uiState.loadTest.loadInTon))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.TESTING_LOAD_TEST, itemName = "Mampu mengangkat", status = uiState.loadTest.isAbleToLift))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.TESTING_LOAD_TEST, itemName = "Ada penurunan beban", status = uiState.loadTest.hasLoadDrop))
        // NDT Hook Test
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.TESTING_NDT_HOOK, itemName = "Hasil NDT baik", status = uiState.ndtHookTest.isNdtResultGood))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = OverheadCraneBAPCategory.TESTING_NDT_HOOK, itemName = "Ada indikasi retak", status = uiState.ndtHookTest.hasCrackIndication))
    }
}

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toOverheadCraneBAPReport(): OverheadCraneBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
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
        manufactureCountry = findTestResult("Negara Pembuat")
    )

    val inspection = OverheadCraneBAPInspection(
        hasConstructionDefects = findBoolItem(OverheadCraneBAPCategory.INSPECTION, "Ada cacat konstruksi"),
        hookHasSafetyLatch = findBoolItem(OverheadCraneBAPCategory.INSPECTION, "Hook memiliki safety latch"),
        isEmergencyStopInstalled = findBoolItem(OverheadCraneBAPCategory.INSPECTION, "Tombol darurat terpasang"),
        isWireropeGoodCondition = findBoolItem(OverheadCraneBAPCategory.INSPECTION, "Kondisi wirerope baik"),
        operatorHasK3License = findBoolItem(OverheadCraneBAPCategory.INSPECTION, "Operator memiliki lisensi K3")
    )

    val testing = OverheadCraneBAPTesting(
        functionTest = findBoolItem(OverheadCraneBAPCategory.TESTING, "Uji Fungsi Baik"),
        loadTest = OverheadCraneBAPLoadTest(
            loadInTon = findBoolItem(OverheadCraneBAPCategory.TESTING_LOAD_TEST, "Beban dalam Ton"),
            isAbleToLift = findBoolItem(OverheadCraneBAPCategory.TESTING_LOAD_TEST, "Mampu mengangkat"),
            hasLoadDrop = findBoolItem(OverheadCraneBAPCategory.TESTING_LOAD_TEST, "Ada penurunan beban")
        ),
        ndtHookTest = OverheadCraneBAPNdtHookTest(
            isNdtResultGood = findBoolItem(OverheadCraneBAPCategory.TESTING_NDT_HOOK, "Hasil NDT baik"),
            hasCrackIndication = findBoolItem(OverheadCraneBAPCategory.TESTING_NDT_HOOK, "Ada indikasi retak")
        )
    )

    return OverheadCraneBAPReport(
        examinationType = this.inspection.examinationType,
        subInspectionType = this.inspection.equipmentType,
        inspectionDate = this.inspection.reportDate ?: "",
        generalData = generalData,
        technicalData = technicalData,
        testResults = OverheadCraneBAPTestResults(inspection = inspection, testing = testing)
    )
}