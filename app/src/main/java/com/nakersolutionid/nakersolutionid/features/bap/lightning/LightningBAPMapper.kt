package com.nakersolutionid.nakersolutionid.features.bap.lightning

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Utils

private object LightningBAPCategory {
    const val TECHNICAL_DATA = "DATA TEKNIK"
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val VISUAL_CONTROL_BOX = "$VISUAL_INSPECTION - Kotak Kontrol"
    const val TESTING = "PENGUJIAN"

    object ItemName {
        const val IS_AVAILABLE = "Tersedia"
        const val IS_GOOD_CONDITION = "Kondisi Baik"
    }
}

fun LightningBAPReport.toInspectionWithDetailsDomain(currentTime: String, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId,
        moreExtraId = this.moreExtraId,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.ILPP,
        subInspectionType = SubInspectionType.Lightning_Conductor,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.addressUsageLocation,
        serialNumber = this.technicalData.serialNumber,
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

private fun mapTechnicalDataToDomain(uiState: LightningBAPTechnicalData, inspectionId: Long): List<InspectionTestResultDomain> {
    val cat = LightningBAPCategory.TECHNICAL_DATA
    return listOf(
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jenis Instalasi", result = uiState.installationType, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tinggi Bangunan (m)", result = uiState.buildingHeightInM, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Luas Bangunan (m2)", result = uiState.buildingAreaInM2, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tinggi Penerima (m)", result = uiState.receiverHeightInM, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Penerima", result = uiState.receiverCount, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Elektroda Tanah", result = uiState.groundElectrodeCount, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Deskripsi Konduktor (mm)", result = uiState.conductorDescriptionInMm, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tahun Pemasangan", result = uiState.installationYear, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tahanan Pembumian (Ohm)", result = uiState.groundingResistanceInOhm, notes = cat)
    )
}

private fun mapVisualInspectionToDomain(uiState: LightningBAPVisualInspection, inspectionId: Long): List<InspectionCheckItemDomain> {
    return mutableListOf<InspectionCheckItemDomain>().apply {
        // --- PERBAIKAN DI SINI ---
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_INSPECTION, itemName = "Sistem secara keseluruhan baik", status = uiState.isSystemOverallGood))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi penerima baik", status = uiState.isReceiverConditionGood))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi tiang penerima baik", status = uiState.isReceiverPoleConditionGood))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_INSPECTION, itemName = "Konduktor terisolasi", status = uiState.isConductorInsulated))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_CONTROL_BOX, itemName = LightningBAPCategory.ItemName.IS_AVAILABLE, status = uiState.controlBox.isAvailable))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_CONTROL_BOX, itemName = LightningBAPCategory.ItemName.IS_GOOD_CONDITION, status = uiState.controlBox.isConditionGood))
    }
}

private fun mapTestingToDomain(uiState: LightningBAPTesting, inspectionId: Long): List<InspectionCheckItemDomain> {
    return listOf(
        // --- PERBAIKAN DI SINI ---
        InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.TESTING, itemName = "Hasil kontinuitas konduktor baik", status = uiState.conductorContinuityResult),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.TESTING, itemName = "Hasil pengukuran tahanan pembumian baik", status = uiState.measuredGroundingResistanceInOhm)
    )
}

fun InspectionWithDetailsDomain.toLightningBAPReport(): LightningBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = LightningBAPGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = LightningBAPTechnicalData(
        installationType = findTestResult("Jenis Instalasi"),
        serialNumber = this.inspection.serialNumber ?: "",
        buildingHeightInM = findTestResult("Tinggi Bangunan (m)"),
        buildingAreaInM2 = findTestResult("Luas Bangunan (m2)"),
        receiverHeightInM = findTestResult("Tinggi Penerima (m)"),
        receiverCount = findTestResult("Jumlah Penerima"),
        groundElectrodeCount = findTestResult("Jumlah Elektroda Tanah"),
        conductorDescriptionInMm = findTestResult("Deskripsi Konduktor (mm)"),
        installationYear = findTestResult("Tahun Pemasangan"),
        groundingResistanceInOhm = findTestResult("Tahanan Pembumian (Ohm)")
    )

    val visualInspection = LightningBAPVisualInspection(
        isSystemOverallGood = findBoolItem(LightningBAPCategory.VISUAL_INSPECTION, "Sistem secara keseluruhan baik"),
        isReceiverConditionGood = findBoolItem(LightningBAPCategory.VISUAL_INSPECTION, "Kondisi penerima baik"),
        isReceiverPoleConditionGood = findBoolItem(LightningBAPCategory.VISUAL_INSPECTION, "Kondisi tiang penerima baik"),
        isConductorInsulated = findBoolItem(LightningBAPCategory.VISUAL_INSPECTION, "Konduktor terisolasi"),
        controlBox = LightningBAPControlBox(
            isAvailable = findBoolItem(LightningBAPCategory.VISUAL_CONTROL_BOX, LightningBAPCategory.ItemName.IS_AVAILABLE),
            isConditionGood = findBoolItem(LightningBAPCategory.VISUAL_CONTROL_BOX, LightningBAPCategory.ItemName.IS_GOOD_CONDITION)
        )
    )

    val testing = LightningBAPTesting(
        conductorContinuityResult = findBoolItem(LightningBAPCategory.TESTING, "Hasil kontinuitas konduktor baik"),
        measuredGroundingResistanceInOhm = findBoolItem(LightningBAPCategory.TESTING, "Hasil pengukuran tahanan pembumian baik")
    )

    return LightningBAPReport(
        extraId = this.inspection.extraId,
        moreExtraId = this.inspection.moreExtraId,
        equipmentType = this.inspection.equipmentType,
        examinationType = this.inspection.examinationType,
        inspectionDate = Utils.formatDateToIndonesian(this.inspection.createdAt ?: this.inspection.reportDate ?: ""),
        generalData = generalData,
        technicalData = technicalData,
        testResults = LightningBAPTestResults(
            visualInspection = visualInspection,
            testing = testing
        )
    )
}