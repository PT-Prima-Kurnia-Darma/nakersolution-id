package com.nakersolutionid.nakersolutionid.features.bap.electric

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Utils

private object ElectricBAPCategory {
    const val TECHNICAL_DATA = "DATA TEKNIK"
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val VISUAL_INSPECTION_PANEL_ROOM = "$VISUAL_INSPECTION - Kondisi Ruang Panel"
    const val TESTING = "PENGUJIAN"
}

fun ElectricalInstallationBAPReport.toInspectionWithDetailsDomain(currentTime: String, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId,
        moreExtraId = this.moreExtraId,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.EE,
        subInspectionType = SubInspectionType.Electrical, // Mengganti dari ElectricalInstallation
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

    val testResults = mutableListOf<InspectionTestResultDomain>()
    testResults.addAll(mapTechnicalDataToDomain(this.technicalData, inspectionId))
    testResults.addAll(mapTestingToDomain(this.testResults.testing, inspectionId))

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = testResults
    )
}

private fun mapTechnicalDataToDomain(uiState: ElectricalInstallationBAPTechnicalData, inspectionId: Long): List<InspectionTestResultDomain> {
    val cat = ElectricBAPCategory.TECHNICAL_DATA
    return listOf(
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Sumber Listrik (PLN)", result = uiState.powerSource.plnKva, notes = "kVA"),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Sumber Listrik (Generator)", result = uiState.powerSource.generatorKw, notes = "kW"),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Penggunaan Daya (Penerangan)", result = uiState.powerUsage.lightingKva, notes = "kVA"),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Penggunaan Daya (Tenaga)", result = uiState.powerUsage.powerKva, notes = "kVA"),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jenis Arus Listrik", result = uiState.electricCurrentType, notes = null)
    )
}

private fun mapVisualInspectionToDomain(uiState: ElectricalInstallationBAPVisualInspection, inspectionId: Long): List<InspectionCheckItemDomain> {
    val items = mutableListOf<InspectionCheckItemDomain>()
    val cat = ElectricBAPCategory.VISUAL_INSPECTION
    val panelRoomCat = ElectricBAPCategory.VISUAL_INSPECTION_PANEL_ROOM

    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = panelRoomCat, itemName = "Ruangan bersih", status = uiState.panelRoomCondition.isRoomClean))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = panelRoomCat, itemName = "Ruangan tidak untuk menyimpan barang yang tidak perlu", status = uiState.panelRoomCondition.isRoomClearOfUnnecessaryItems))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tersedia diagram satu garis", status = uiState.hasSingleLineDiagram))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Panel dan MCB dilengkapi tutup pengaman", status = uiState.panelAndMcbHaveProtectiveCover))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Labeling lengkap", status = uiState.isLabelingComplete))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tersedia APAR", status = uiState.isFireExtinguisherAvailable))

    return items
}

private fun mapTestingToDomain(uiState: ElectricalInstallationBAPTesting, inspectionId: Long): List<InspectionTestResultDomain> {
    val cat = ElectricBAPCategory.TESTING
    return listOf(
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Hasil Uji Thermograph", result = if(uiState.thermographTestResult) "Baik" else "Tidak Baik", notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Peralatan pengaman berfungsi", result = if(uiState.areSafetyDevicesFunctional) "Ya" else "Tidak", notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tegangan antar fasa normal", result = if(uiState.isVoltageBetweenPhasesNormal) "Ya" else "Tidak", notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Beban antar fasa seimbang", result = if(uiState.isPhaseLoadBalanced) "Ya" else "Tidak", notes = cat)
    )
}

fun InspectionWithDetailsDomain.toElectricalInstallationBAPReport(): ElectricalInstallationBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    fun findBoolTestResult(testName: String, positiveResult: String): Boolean {
        val result = this.testResults.find { it.testName == testName }?.result
        return result.equals(positiveResult, ignoreCase = true)
    }

    val generalData = ElectricalInstallationBAPGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = ElectricalInstallationBAPTechnicalData(
        powerSource = ElectricalInstallationBAPPowerSource(
            plnKva = findTestResult("Sumber Listrik (PLN)"),
            generatorKw = findTestResult("Sumber Listrik (Generator)")
        ),
        powerUsage = ElectricalInstallationBAPPowerUsage(
            lightingKva = findTestResult("Penggunaan Daya (Penerangan)"),
            powerKva = findTestResult("Penggunaan Daya (Tenaga)")
        ),
        electricCurrentType = findTestResult("Jenis Arus Listrik"),
        serialNumber = this.inspection.serialNumber ?: ""
    )

    val visualInspection = ElectricalInstallationBAPVisualInspection(
        panelRoomCondition = ElectricalInstallationBAPPanelRoomCondition(
            isRoomClean = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION_PANEL_ROOM, "Ruangan bersih"),
            isRoomClearOfUnnecessaryItems = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION_PANEL_ROOM, "Ruangan tidak untuk menyimpan barang yang tidak perlu")
        ),
        hasSingleLineDiagram = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION, "Tersedia diagram satu garis"),
        panelAndMcbHaveProtectiveCover = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION, "Panel dan MCB dilengkapi tutup pengaman"),
        isLabelingComplete = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION, "Labeling lengkap"),
        isFireExtinguisherAvailable = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION, "Tersedia APAR")
    )

    val testing = ElectricalInstallationBAPTesting(
        thermographTestResult = findBoolTestResult("Hasil Uji Thermograph", "Baik"),
        areSafetyDevicesFunctional = findBoolTestResult("Peralatan pengaman berfungsi", "Ya"),
        isVoltageBetweenPhasesNormal = findBoolTestResult("Tegangan antar fasa normal", "Ya"),
        isPhaseLoadBalanced = findBoolTestResult("Beban antar fasa seimbang", "Ya")
    )

    return ElectricalInstallationBAPReport(
        extraId = this.inspection.extraId,
        moreExtraId = this.inspection.moreExtraId,
        examinationType = this.inspection.examinationType,
        equipmentType = this.inspection.equipmentType,
        inspectionDate = Utils.formatDateToIndonesian(this.inspection.createdAt ?: this.inspection.reportDate ?: ""),
        generalData = generalData,
        technicalData = technicalData,
        testResults = ElectricalInstallationBAPTestResults(
            visualInspection = visualInspection,
            testing = testing
        )
    )
}