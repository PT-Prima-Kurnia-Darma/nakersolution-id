package com.nakersolutionid.nakersolutionid.features.bap.fireprotection

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Utils

private object FireProtectionBAPCategory {
    const val TECHNICAL_DATA = "DATA TEKNIK"
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val VISUAL_APAR = "$VISUAL_INSPECTION - APAR"
    const val VISUAL_PUMP = "$VISUAL_INSPECTION - Pompa"
    const val VISUAL_SPRINKLER = "$VISUAL_INSPECTION - Sistem Sprinkler"
    const val VISUAL_DETECTOR = "$VISUAL_INSPECTION - Sistem Detektor"
    const val TESTING = "PENGUJIAN"
    const val TESTING_DETECTOR = "$TESTING - Detektor"
}

fun FireProtectionBAPReport.toInspectionWithDetailsDomain(currentTime: String, isEdited: Boolean, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId,
        moreExtraId = this.moreExtraId,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.IPK,
        subInspectionType = SubInspectionType.Fire_Protection,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        // PERBAIKAN: Ambil dari generalData, bukan technicalData.
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.addressUsageLocation,
        serialNumber = this.technicalData.seriesNumber,
        createdAt = currentTime,
        reportDate = this.inspectionDate,
        isSynced = false,
        isEdited = isEdited
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

private fun mapTechnicalDataToDomain(uiState: FireProtectionBAPTechnicalData, inspectionId: Long): List<InspectionTestResultDomain> {
    val cat = FireProtectionBAPCategory.TECHNICAL_DATA
    return listOf(
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Luas Area (m2)", result = uiState.areaSizeInM2, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Luas Bangunan (m2)", result = uiState.buildingSizeInM2, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tinggi Bangunan (m)", result = uiState.buildingHeightInM, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Lantai", result = uiState.totalFloors, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Hydrant Pilar", result = uiState.totalHydrantPilar, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Hydrant Gedung", result = uiState.totalHydrantBuilding, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Hose Rell", result = uiState.totalHoseRell, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Sprinkler", result = uiState.totalSprinkler, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Heat Detector", result = uiState.totalHeatDetector, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Smoke Detector", result = uiState.totalSmokeDetector, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Flame Detector", result = uiState.totalFlameDetector, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Gas Detector", result = uiState.totalGasDetector, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tombol Manual", result = uiState.manualButton, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Alarm Bell", result = uiState.alarmBell, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Lampu Indikator Alarm", result = uiState.signalAlarmLamp, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Pintu Darurat", result = uiState.emergencyExit, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "APAR", result = uiState.apar, notes = cat)
    )
}

private fun mapVisualInspectionToDomain(uiState: FireProtectionBAPVisualInspection, inspectionId: Long): List<InspectionCheckItemDomain> {
    return mutableListOf<InspectionCheckItemDomain>().apply {
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_APAR, itemName = "Tersedia", status = uiState.aparStatus.isAvailable))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_APAR, itemName = "Kondisi Baik", status = uiState.aparStatus.isGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Panel Hidran Baik", status = uiState.isHydrantPanelGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_PUMP, itemName = "Tersedia", status = uiState.pumpStatus.isAvailable))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_PUMP, itemName = "Kondisi Baik", status = uiState.pumpStatus.isGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_SPRINKLER, itemName = "Tersedia", status = uiState.sprinklerSystemStatus.isAvailable))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_SPRINKLER, itemName = "Kondisi Baik", status = uiState.sprinklerSystemStatus.isGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_DETECTOR, itemName = "Tersedia", status = uiState.detectorSystemStatus.isAvailable))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_DETECTOR, itemName = "Kondisi Baik", status = uiState.detectorSystemStatus.isGoodCondition))
    }
}

private fun mapTestingToDomain(uiState: FireProtectionBAPTesting, inspectionId: Long): List<InspectionCheckItemDomain> {
    return mutableListOf<InspectionCheckItemDomain>().apply {
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING, itemName = "APAR Berfungsi", status = uiState.isAparFunctional))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING, itemName = "Hasil Tes Pompa Baik", status = uiState.pumpTestResults))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING, itemName = "Sprinkler Berfungsi", status = uiState.isSprinklerFunctional))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING_DETECTOR, itemName = "Berfungsi", status = uiState.detectorTestResults.isFunctional))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING_DETECTOR, itemName = "Terhubung ke MCFA", status = uiState.detectorTestResults.isConnectedToMcfa))
    }
}

fun InspectionWithDetailsDomain.toFireProtectionBAPReport(): FireProtectionBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = FireProtectionBAPGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = FireProtectionBAPTechnicalData(
        areaSizeInM2 = findTestResult("Luas Area (m2)"),
        buildingSizeInM2 = findTestResult("Luas Bangunan (m2)"),
        buildingHeightInM = findTestResult("Tinggi Bangunan (m)"),
        totalFloors = findTestResult("Jumlah Lantai"),
        totalHydrantPilar = findTestResult("Jumlah Hydrant Pilar"),
        totalHydrantBuilding = findTestResult("Jumlah Hydrant Gedung"),
        totalHoseRell = findTestResult("Jumlah Hose Rell"),
        totalSprinkler = findTestResult("Jumlah Sprinkler"),
        totalHeatDetector = findTestResult("Jumlah Heat Detector"),
        totalSmokeDetector = findTestResult("Jumlah Smoke Detector"),
        totalFlameDetector = findTestResult("Jumlah Flame Detector"),
        totalGasDetector = findTestResult("Jumlah Gas Detector"),
        manualButton = findTestResult("Tombol Manual"),
        alarmBell = findTestResult("Alarm Bell"),
        signalAlarmLamp = findTestResult("Lampu Indikator Alarm"),
        emergencyExit = findTestResult("Pintu Darurat"),
        apar = findTestResult("APAR"),
        seriesNumber = this.inspection.serialNumber ?: ""
        // PERBAIKAN: Hapus mapping `usageLocation` dari sini karena sudah tidak ada di UI State
        // dan untuk menghindari duplikasi.
    )

    val visualInspection = FireProtectionBAPVisualInspection(
        aparStatus = FireProtectionBAPAparStatus(
            isAvailable = findBoolItem(FireProtectionBAPCategory.VISUAL_APAR, "Tersedia"),
            isGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_APAR, "Kondisi Baik")
        ),
        isHydrantPanelGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_INSPECTION, "Kondisi Panel Hidran Baik"),
        pumpStatus = FireProtectionBAPPumpStatus(
            isAvailable = findBoolItem(FireProtectionBAPCategory.VISUAL_PUMP, "Tersedia"),
            isGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_PUMP, "Kondisi Baik")
        ),
        sprinklerSystemStatus = FireProtectionBAPSprinklerSystemStatus(
            isAvailable = findBoolItem(FireProtectionBAPCategory.VISUAL_SPRINKLER, "Tersedia"),
            isGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_SPRINKLER, "Kondisi Baik")
        ),
        detectorSystemStatus = FireProtectionBAPDetectorSystemStatus(
            isAvailable = findBoolItem(FireProtectionBAPCategory.VISUAL_DETECTOR, "Tersedia"),
            isGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_DETECTOR, "Kondisi Baik")
        )
    )

    val testing = FireProtectionBAPTesting(
        isAparFunctional = findBoolItem(FireProtectionBAPCategory.TESTING, "APAR Berfungsi"),
        pumpTestResults = findBoolItem(FireProtectionBAPCategory.TESTING, "Hasil Tes Pompa Baik"),
        isSprinklerFunctional = findBoolItem(FireProtectionBAPCategory.TESTING, "Sprinkler Berfungsi"),
        detectorTestResults = FireProtectionBAPDetectorTestResults(
            isFunctional = findBoolItem(FireProtectionBAPCategory.TESTING_DETECTOR, "Berfungsi"),
            isConnectedToMcfa = findBoolItem(FireProtectionBAPCategory.TESTING_DETECTOR, "Terhubung ke MCFA")
        )
    )

    return FireProtectionBAPReport(
        extraId = this.inspection.extraId,
        moreExtraId = this.inspection.moreExtraId,
        equipmentType = this.inspection.equipmentType,
        examinationType = this.inspection.examinationType,
        inspectionDate = Utils.formatDateToIndonesian(this.inspection.createdAt ?: this.inspection.reportDate ?: ""),
        generalData = generalData,
        technicalData = technicalData,
        testResults = FireProtectionBAPTestResults(
            visualInspection = visualInspection,
            testing = testing
        )
    )
}