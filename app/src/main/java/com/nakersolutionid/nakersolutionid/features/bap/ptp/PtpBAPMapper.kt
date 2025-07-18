package com.nakersolutionid.nakersolutionid.features.bap.ptp

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain

private object PtpBAPCategory {
    const val TECHNICAL_DATA = "DATA TEKNIK"
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val VISUAL_PPE = "$VISUAL_INSPECTION - Alat Pelindung Diri"
    const val TESTING = "PENGUJIAN"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun PtpBAPReport.toInspectionWithDetailsDomain(currentTime: String): InspectionWithDetailsDomain {
    val inspectionId: Long = 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = "",
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PTP,
        subInspectionType = if (this.equipmentType.contains("diesel", ignoreCase = true)) SubInspectionType.Motor_Diesel else SubInspectionType.Machine,
        equipmentType = this.equipmentType,
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
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.TECHNICAL_DATA, itemName = "Perlengkapan pengaman terpasang", status = this.technicalData.areSafetyFeaturesInstalled))
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

private fun mapTechnicalDataToDomain(uiState: PtpBAPTechnicalData, inspectionId: Long): List<InspectionTestResultDomain> {
    val cat = PtpBAPCategory.TECHNICAL_DATA
    return listOf(
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Negara Pembuat", result = uiState.manufactureCountry, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Keterangan Kapasitas", result = uiState.capacityDescription, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Daya Motor Penggerak (kW)", result = uiState.driveMotorPowerKw, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Spesifikasi Khusus", result = uiState.specialSpecification, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Keterangan Dimensi", result = uiState.dimensionsDescription, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Putaran (RPM)", result = uiState.rotationRpm, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Frekuensi (Hz)", result = uiState.frequencyHz, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Arus (Ampere)", result = uiState.currentAmperes, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Berat Mesin (Kg)", result = uiState.machineWeightKg, notes = cat)
    )
}

private fun mapVisualInspectionToDomain(uiState: PtpBAPVisualInspection, inspectionId: Long): List<InspectionCheckItemDomain> {
    return mutableListOf<InspectionCheckItemDomain>().apply {
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi mesin baik", status = uiState.isMachineGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Indikator kelistrikan baik", status = uiState.areElectricalIndicatorsGood))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Pembumian terpasang", status = uiState.isGroundingInstalled))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi baterai baik", status = uiState.isBatteryGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Ada kebocoran pelumasan", status = uiState.hasLubricationLeak))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi pondasi baik", status = uiState.isFoundationGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Ada kebocoran hidrolik", status = uiState.hasHydraulicLeak))
        // PPE
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_PPE, itemName = "APAR tersedia", status = uiState.personalProtectiveEquipment.isAparAvailable))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_PPE, itemName = "APD tersedia", status = uiState.personalProtectiveEquipment.isPpeAvailable))
    }
}

private fun mapTestingToDomain(uiState: PtpBAPTesting, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = PtpBAPCategory.TESTING
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Penerangan memenuhi syarat", status = uiState.isLightingCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tingkat kebisingan memenuhi syarat", status = uiState.isNoiseLevelCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tombol darurat berfungsi", status = uiState.isEmergencyStopFunctional),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Mesin berfungsi baik", status = uiState.isMachineFunctional),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tingkat getaran memenuhi syarat", status = uiState.isVibrationLevelCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tahanan isolasi baik", status = uiState.isInsulationResistanceOk),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Putaran poros memenuhi syarat", status = uiState.isShaftRotationCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tahanan pembumian memenuhi syarat", status = uiState.isGroundingResistanceCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Hasil NDT Las baik", status = uiState.isNdtWeldTestOk),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tegangan antar fasa normal", status = uiState.isVoltageBetweenPhasesNormal),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Beban antar fasa seimbang", status = uiState.isPhaseLoadBalanced)
    )
}

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toPtpBAPReport(): PtpBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = PtpBAPGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = PtpBAPTechnicalData(
        brandOrType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturer = this.inspection.manufacturer?.name ?: "",
        manufactureYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        manufactureCountry = findTestResult("Negara Pembuat"),
        capacityDescription = findTestResult("Keterangan Kapasitas"),
        driveMotorPowerKw = findTestResult("Daya Motor Penggerak (kW)"),
        specialSpecification = findTestResult("Spesifikasi Khusus"),
        dimensionsDescription = findTestResult("Keterangan Dimensi"),
        rotationRpm = findTestResult("Putaran (RPM)"),
        frequencyHz = findTestResult("Frekuensi (Hz)"),
        currentAmperes = findTestResult("Arus (Ampere)"),
        machineWeightKg = findTestResult("Berat Mesin (Kg)"),
        areSafetyFeaturesInstalled = findBoolItem(PtpBAPCategory.TECHNICAL_DATA, "Perlengkapan pengaman terpasang")
    )

    val visualInspection = PtpBAPVisualInspection(
        isMachineGoodCondition = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Kondisi mesin baik"),
        areElectricalIndicatorsGood = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Indikator kelistrikan baik"),
        isGroundingInstalled = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Pembumian terpasang"),
        isBatteryGoodCondition = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Kondisi baterai baik"),
        hasLubricationLeak = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Ada kebocoran pelumasan"),
        isFoundationGoodCondition = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Kondisi pondasi baik"),
        hasHydraulicLeak = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Ada kebocoran hidrolik"),
        personalProtectiveEquipment = PtpBAPPersonalProtectiveEquipment(
            isAparAvailable = findBoolItem(PtpBAPCategory.VISUAL_PPE, "APAR tersedia"),
            isPpeAvailable = findBoolItem(PtpBAPCategory.VISUAL_PPE, "APD tersedia")
        )
    )

    val testing = PtpBAPTesting(
        isLightingCompliant = findBoolItem(PtpBAPCategory.TESTING, "Penerangan memenuhi syarat"),
        isNoiseLevelCompliant = findBoolItem(PtpBAPCategory.TESTING, "Tingkat kebisingan memenuhi syarat"),
        isEmergencyStopFunctional = findBoolItem(PtpBAPCategory.TESTING, "Tombol darurat berfungsi"),
        isMachineFunctional = findBoolItem(PtpBAPCategory.TESTING, "Mesin berfungsi baik"),
        isVibrationLevelCompliant = findBoolItem(PtpBAPCategory.TESTING, "Tingkat getaran memenuhi syarat"),
        isInsulationResistanceOk = findBoolItem(PtpBAPCategory.TESTING, "Tahanan isolasi baik"),
        isShaftRotationCompliant = findBoolItem(PtpBAPCategory.TESTING, "Putaran poros memenuhi syarat"),
        isGroundingResistanceCompliant = findBoolItem(PtpBAPCategory.TESTING, "Tahanan pembumian memenuhi syarat"),
        isNdtWeldTestOk = findBoolItem(PtpBAPCategory.TESTING, "Hasil NDT Las baik"),
        isVoltageBetweenPhasesNormal = findBoolItem(PtpBAPCategory.TESTING, "Tegangan antar fasa normal"),
        isPhaseLoadBalanced = findBoolItem(PtpBAPCategory.TESTING, "Beban antar fasa seimbang")
    )

    return PtpBAPReport(
        equipmentType = this.inspection.equipmentType,
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        generalData = generalData,
        technicalData = technicalData,
        testResults = PtpBAPTestResults(visualInspection = visualInspection, testing = testing)
    )
}