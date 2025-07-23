package com.nakersolutionid.nakersolutionid.features.bap.pubt

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import com.nakersolutionid.nakersolutionid.utils.Utils

private object PubtBAPCategory {
    const val TECHNICAL_DATA = "DATA TEKNIK"
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val VISUAL_SAFETY_VALVE = "$VISUAL_INSPECTION - Katup Pengaman"
    const val VISUAL_APAR = "$VISUAL_INSPECTION - APAR"
    const val TESTING = "PENGUJIAN"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun PubtBAPReport.toInspectionWithDetailsDomain(currentTime: String, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId,
        moreExtraId = this.moreExtraId,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PUBT,
        subInspectionType = SubInspectionType.General_PUBT,
        equipmentType = this.inspectionType,
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
        createdAt = currentTime,
        reportDate = this.inspectionDate,
        isSynced = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.addAll(mapVisualInspectionToDomain(this.testResults.visualInspection, inspectionId))
    checkItems.addAll(mapTestingToDomain(this.testResults.testing, inspectionId))

    val testResults = mapTechnicalDataToDomain(this.technicalData, inspectionId)

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = testResults
    )
}

private fun mapTechnicalDataToDomain(uiState: PubtBAPTechnicalData, inspectionId: Long): List<InspectionTestResultDomain> {
    val cat = PubtBAPCategory.TECHNICAL_DATA
    return listOf(
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Negara Pembuat", result = uiState.manufactureCountry, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jenis Bahan Bakar", result = uiState.fuelType, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Isi Bejana Tekan", result = uiState.pressureVesselContent, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tekanan Desain (Kg/Cm2)", result = uiState.designPressureInKgCm2, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tekanan Kerja Maks (Kg/Cm2)", result = uiState.maxWorkingPressureInKgCm2, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jenis Bahan", result = uiState.materialType, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jenis Katup Pengaman", result = uiState.safetyValveType, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Volume (Liter)", result = uiState.volumeInLiters, notes = cat)
    )
}

private fun mapVisualInspectionToDomain(uiState: PubtBAPVisualInspection, inspectionId: Long): List<InspectionCheckItemDomain> {
    return mutableListOf<InspectionCheckItemDomain>().apply {
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Pondasi Baik", status = uiState.fondationCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Roda Baik", status = uiState.wheelCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Pipa Baik", status = uiState.pipeCondition))
        // Safety Valve
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtBAPCategory.VISUAL_SAFETY_VALVE, itemName = "Terpasang", status = uiState.safetyValve.isInstalled))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtBAPCategory.VISUAL_SAFETY_VALVE, itemName = "Kondisi Baik", status = uiState.safetyValve.condition))
        // APAR
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtBAPCategory.VISUAL_APAR, itemName = "Tersedia", status = uiState.apar.isAvailable))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtBAPCategory.VISUAL_APAR, itemName = "Kondisi Baik", status = uiState.apar.condition))
    }
}

private fun mapTestingToDomain(uiState: PubtBAPTesting, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = PubtBAPCategory.TESTING
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Pengujian NDT Terpenuhi", status = uiState.ndtTestingFulfilled),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Pengujian Ketebalan Sesuai", status = uiState.thicknessTestingComply),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Kondisi Pengujian Pneumatik Baik", status = uiState.pneumaticTestingCondition),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Pengujian Hidrostatik Terpenuhi", status = uiState.hydroTestingFullFilled),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Kondisi Pengujian Katup Pengaman Baik", status = uiState.safetyValveTestingCondition)
    )
}

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toPubtBAPReport(): PubtBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = PubtBAPGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = PubtBAPTechnicalData(
        brandOrType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturer = this.inspection.manufacturer?.name ?: "",
        manufactureYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        manufactureCountry = findTestResult("Negara Pembuat"),
        fuelType = findTestResult("Jenis Bahan Bakar"),
        pressureVesselContent = findTestResult("Isi Bejana Tekan"),
        designPressureInKgCm2 = findTestResult("Tekanan Desain (Kg/Cm2)"),
        maxWorkingPressureInKgCm2 = findTestResult("Tekanan Kerja Maks (Kg/Cm2)"),
        materialType = findTestResult("Jenis Bahan"),
        safetyValveType = findTestResult("Jenis Katup Pengaman"),
        volumeInLiters = findTestResult("Volume (Liter)")
    )

    val visualInspection = PubtBAPVisualInspection(
        fondationCondition = findBoolItem(PubtBAPCategory.VISUAL_INSPECTION, "Kondisi Pondasi Baik"),
        wheelCondition = findBoolItem(PubtBAPCategory.VISUAL_INSPECTION, "Kondisi Roda Baik"),
        pipeCondition = findBoolItem(PubtBAPCategory.VISUAL_INSPECTION, "Kondisi Pipa Baik"),
        safetyValve = PubtBAPSafetyValve(
            isInstalled = findBoolItem(PubtBAPCategory.VISUAL_SAFETY_VALVE, "Terpasang"),
            condition = findBoolItem(PubtBAPCategory.VISUAL_SAFETY_VALVE, "Kondisi Baik")
        ),
        apar = PubtBAPApar(
            isAvailable = findBoolItem(PubtBAPCategory.VISUAL_APAR, "Tersedia"),
            condition = findBoolItem(PubtBAPCategory.VISUAL_APAR, "Kondisi Baik")
        )
    )

    val testing = PubtBAPTesting(
        ndtTestingFulfilled = findBoolItem(PubtBAPCategory.TESTING, "Pengujian NDT Terpenuhi"),
        thicknessTestingComply = findBoolItem(PubtBAPCategory.TESTING, "Pengujian Ketebalan Sesuai"),
        pneumaticTestingCondition = findBoolItem(PubtBAPCategory.TESTING, "Kondisi Pengujian Pneumatik Baik"),
        hydroTestingFullFilled = findBoolItem(PubtBAPCategory.TESTING, "Pengujian Hidrostatik Terpenuhi"),
        safetyValveTestingCondition = findBoolItem(PubtBAPCategory.TESTING, "Kondisi Pengujian Katup Pengaman Baik")
    )

    return PubtBAPReport(
        extraId = this.inspection.extraId,
        moreExtraId = this.inspection.moreExtraId,
        examinationType = this.inspection.examinationType,
        inspectionType = this.inspection.equipmentType,
        inspectionDate = Utils.formatDateToIndonesian(this.inspection.createdAt ?: this.inspection.reportDate ?: ""),
        generalData = generalData,
        technicalData = technicalData,
        testResults = PubtBAPTestResults(visualInspection = visualInspection, testing = testing)
    )
}