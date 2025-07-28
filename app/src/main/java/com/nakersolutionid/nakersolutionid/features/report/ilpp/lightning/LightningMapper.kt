package com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import kotlinx.collections.immutable.toImmutableList

/**
 * Menyimpan semua nama kategori sebagai konstanta untuk mencegah kesalahan ketik dan memastikan konsistensi.
 */
private object LightningCategory {
    const val PHYSICAL_INSPECTION = "Pemeriksaan Fisik Instalasi"
    const val OTHER_STANDARDS = "Pemeriksaan Standar Lainnya"
    const val GROUNDING_RESISTANCE_TEST = "Pengujian Tahanan Pembumian"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun LightningProtectionUiState.toInspectionWithDetailsDomain(currentTime: String, isEdited: Boolean, reportId: Long? = null): InspectionWithDetailsDomain {
    val report = this.inspectionReport
    val serviceData = report.serviceProviderData
    val clientData = report.clientData
    val techData = report.technicalData
    val inspectionId: Long = reportId ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = report.extraId,
        moreExtraId = report.moreExtraId,
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.ILPP,
        subInspectionType = SubInspectionType.Lightning_Conductor,
        equipmentType = clientData.objectType,
        examinationType = clientData.inspectionType,
        ownerName = clientData.companyName,
        ownerAddress = clientData.companyLocation,
        usageLocation = clientData.usageLocation,
        addressUsageLocation = clientData.companyLocation,
        driveType = techData.conductorType,
        permitNumber = clientData.certificateNo,
        reportDate = clientData.inspectionDate,
        inspectorName = serviceData.expertName,
        createdAt = currentTime,
        isSynced = false,
        isEdited = isEdited
    )

    val checkItems = createCheckItemsFromUiState(report, inspectionId)
    val testResults = createTestResultsFromUiState(report, inspectionId)

    val findings = mutableListOf<InspectionFindingDomain>()
    if (report.conclusion.summary.isNotBlank()) {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = report.conclusion.summary, type = FindingType.FINDING))
    }
    report.conclusion.recommendations.forEach {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION))
    }

    return InspectionWithDetailsDomain(inspectionDomain, checkItems, findings, testResults)
}

private fun createCheckItemsFromUiState(report: LightningProtectionInspectionReport, id: Long): List<InspectionCheckItemDomain> {
    val items = mutableListOf<InspectionCheckItemDomain>()
    val catPhysical = LightningCategory.PHYSICAL_INSPECTION
    report.physicalInspection.let { data ->
        items.add(data.installationSystem.toCheckItem(id, catPhysical, "Sistem Instalasi"))
        items.add(data.receiverHead.toCheckItem(id, catPhysical, "Penerima (Head)"))
        items.add(data.receiverPole.toCheckItem(id, catPhysical, "Tiang penerima"))
        items.add(data.poleReinforcementSystem.toCheckItem(id, catPhysical, "Sistem Penguatan Tiang Penerima"))
        items.add(data.downConductor.toCheckItem(id, catPhysical, "Penghantar penurunan"))
        items.add(data.conductorClamps.toCheckItem(id, catPhysical, "Klem Kabel Penghantar"))
        items.add(data.jointConnections.toCheckItem(id, catPhysical, "Kondisi Joins sambungan"))
        items.add(data.downConductorBoxAndGroundingTerminal.toCheckItem(id, catPhysical, "Kotak Down Conductor dan terminal grounding"))
        items.add(data.controlBox.toCheckItem(id, catPhysical, "Bak Kontrol"))
        items.add(data.groundingSystem.toCheckItem(id, catPhysical, "System Pentanahan"))
        items.add(data.downConductorDirectConnection.toCheckItem(id, catPhysical, "Down conductor tersambung langsung ke permukaan"))
    }

    val catOther = LightningCategory.OTHER_STANDARDS
    report.otherStandardsInspection.let { data ->
        items.add(data.installationPlacementAsPerDrawing.toCheckItem(id, catOther, "Pemasangan Sesuai As Built Drawing"))
        items.add(data.airTerminalConnectionToDownConductor.toCheckItem(id, catOther, "Air Terminal Terhubung ke Penghantar"))
        items.add(data.downConductorJointsOnStructure.toCheckItem(id, catOther, "Sambungan pada Penghantar Penurunan"))
        items.add(data.groundingElectrodeUsesTestJointBox.toCheckItem(id, catOther, "Elektroda Pembumian Pakai Box Ukur"))
        items.add(data.downConductorMaterialStandard.toCheckItem(id, catOther, "Penghantar Penurunan Sesuai Standar"))
        items.add(data.lightningCounterInstalled.toCheckItem(id, catOther, "Terpasang Lightning Counter"))
        items.add(data.airTerminalIsRadioactive.toCheckItem(id, catOther, "Air Terminal Mengandung Radioaktif"))
        items.add(data.groundingElectrodeMaterialQuality.toCheckItem(id, catOther, "Elektroda Pembumian Non-Corrosive"))
        items.add(data.overvoltageProtectionInstalled.toCheckItem(id, catOther, "Proteksi Tegangan Lebih (Arrester)"))
    }

    val catGroundingTest = LightningCategory.GROUNDING_RESISTANCE_TEST
    report.testingResults.groundingResistanceTest.forEach { item ->
        val resultString = "Value: ${item.measuredValue}|Remarks: ${item.remarks}"
        items.add(InspectionCheckItemDomain(0, id, catGroundingTest, item.itemChecked, item.result, resultString))
    }

    return items
}

private fun createTestResultsFromUiState(report: LightningProtectionInspectionReport, id: Long): List<InspectionTestResultDomain> {
    val results = mutableListOf<InspectionTestResultDomain>()
    report.serviceProviderData.let {
        results.add(InspectionTestResultDomain(0, id, "PJK3 - Nama & Alamat", it.companyName, null))
        results.add(InspectionTestResultDomain(0, id, "PJK3 - SKP Perusahaan", it.companyPermitNo, null))
        results.add(InspectionTestResultDomain(0, id, "PJK3 - SKP Ahli K3", it.expertPermitNo, null))
        results.add(InspectionTestResultDomain(0, id, "PJK3 - Peralatan Riksa Uji", it.testEquipmentUsed, null))
    }
    report.technicalData.let {
        results.add(InspectionTestResultDomain(0, id, "Teknis - Tinggi Bangunan", it.buildingHeight, null))
        results.add(InspectionTestResultDomain(0, id, "Teknis - Luas Bangunan", it.buildingArea, null))
        results.add(InspectionTestResultDomain(0, id, "Teknis - Tinggi Penerima", it.receiverHeight, null))
        results.add(InspectionTestResultDomain(0, id, "Teknis - Jumlah Penerima", it.receiverCount, null))
        results.add(InspectionTestResultDomain(0, id, "Teknis - Jumlah sambungan ukur", it.testJointCount, null))
        results.add(InspectionTestResultDomain(0, id, "Teknis - Jumlah hantaran penyalur", it.conductorDescription, null))
        results.add(InspectionTestResultDomain(0, id, "Teknis - Jenis & Ukuran Hantaran", it.conductorTypeAndSize, null))
        results.add(InspectionTestResultDomain(0, id, "Teknis - Tahanan Sebaran Tanah", it.groundingResistance, null))
        results.add(InspectionTestResultDomain(0, id, "Teknis - Tahun Pemasangan", it.installationYear, null))
        results.add(InspectionTestResultDomain(0, id, "Teknis - Instalatir", it.installer, null))
    }
    report.groundingSystemVisualInspection.let {
        results.add(InspectionTestResultDomain(0, id, "Visual - Penerima (Air Terminal)", it.airTerminal, null))
        results.add(InspectionTestResultDomain(0, id, "Visual - Penghantar Penurunan", it.downConductor, null))
        results.add(InspectionTestResultDomain(0, id, "Visual - Pembumian dan Sambungan Ukur", it.groundingAndTestJoint, null))
    }
    // --- PERBAIKAN DI SINI ---
    // Menyimpan catatan umum dari UI State ke domain model.
    // Anda perlu menambahkan field `notes: String` di data class `LightningProtectionPhysicalInspection`.
    report.physicalInspection.let {
        results.add(InspectionTestResultDomain(0, id, "Pemeriksaan Fisik - Catatan", it.notes, null))
    }
    report.testingResults.groundingResistanceMeasurement.forEachIndexed { index, item ->
        val notes = "EC: ${item.ecDistance}|EP: ${item.epDistance}|Remarks: ${item.remarks}"
        results.add(InspectionTestResultDomain(0, id, "Pengukuran Tahanan #${index + 1}", item.rValueOhm, notes))
    }
    return results
}

private fun LightningProtectionConditionResult.toCheckItem(id: Long, cat: String, name: String): InspectionCheckItemDomain {
    return InspectionCheckItemDomain(0, id, cat, name, good || fair || poor)
}

private fun LightningProtectionCheckResult.toCheckItem(id: Long, cat: String, name: String) =
    InspectionCheckItemDomain(0, id, cat, name, this.checked, this.remarks)

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toLightningProtectionUiState(): LightningProtectionUiState {

    fun findTest(name: String) = this.testResults.find { it.testName.equals(name, true) }?.result ?: ""
    fun findCheck(cat: String, name: String) = this.checkItems.find { it.category == cat && it.itemName.equals(name, true) }

    fun findConditionItem(cat: String, name: String): LightningProtectionConditionResult {
        val item = findCheck(cat, name)
        val parts = item?.result?.split('|', limit = 2) ?: listOf("")
        val condition = parts[0]
        val remarks = parts.getOrNull(1) ?: ""
        return LightningProtectionConditionResult(
            good = condition == "Baik",
            fair = condition == "Kurang Baik",
            poor = condition == "Buruk",
        )
    }

    fun findCheckItem(cat: String, name: String) = findCheck(cat, name)?.let {
        LightningProtectionCheckResult(it.status, it.result ?: "")
    } ?: LightningProtectionCheckResult()

    val serviceProvider = LightningProtectionServiceProviderData(
        companyName = findTest("PJK3 - Nama & Alamat"),
        companyPermitNo = findTest("PJK3 - SKP Perusahaan"),
        expertPermitNo = findTest("PJK3 - SKP Ahli K3"),
        testEquipmentUsed = findTest("PJK3 - Peralatan Riksa Uji"),
        expertName = this.inspection.inspectorName ?: ""
    )

    val client = LightningProtectionClientData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        objectType = this.inspection.equipmentType,
        inspectionType = this.inspection.examinationType,
        certificateNo = this.inspection.permitNumber ?: "",
        inspectionDate = this.inspection.reportDate ?: ""
    )

    val technical = LightningProtectionTechnicalData(
        conductorType = this.inspection.driveType ?: "",
        buildingHeight = findTest("Teknis - Tinggi Bangunan"),
        buildingArea = findTest("Teknis - Luas Bangunan"),
        receiverHeight = findTest("Teknis - Tinggi Penerima"),
        receiverCount = findTest("Teknis - Jumlah Penerima"),
        testJointCount = findTest("Teknis - Jumlah sambungan ukur"),
        conductorDescription = findTest("Teknis - Jumlah hantaran penyalur"),
        conductorTypeAndSize = findTest("Teknis - Jenis & Ukuran Hantaran"),
        groundingResistance = findTest("Teknis - Tahanan Sebaran Tanah"),
        installationYear = findTest("Teknis - Tahun Pemasangan"),
        installer = findTest("Teknis - Instalatir")
    )

    val catPhysical = LightningCategory.PHYSICAL_INSPECTION
    val physical = LightningProtectionPhysicalInspection(
        installationSystem = findConditionItem(catPhysical, "Sistem Instalasi"),
        receiverHead = findConditionItem(catPhysical, "Penerima (Head)"),
        receiverPole = findConditionItem(catPhysical, "Tiang penerima"),
        poleReinforcementSystem = findConditionItem(catPhysical, "Sistem Penguatan Tiang Penerima"),
        downConductor = findConditionItem(catPhysical, "Penghantar penurunan"),
        conductorClamps = findConditionItem(catPhysical, "Klem Kabel Penghantar"),
        jointConnections = findConditionItem(catPhysical, "Kondisi Joins sambungan"),
        downConductorBoxAndGroundingTerminal = findConditionItem(catPhysical, "Kotak Down Conductor dan terminal grounding"),
        controlBox = findConditionItem(catPhysical, "Bak Kontrol"),
        groundingSystem = findConditionItem(catPhysical, "System Pentanahan"),
        downConductorDirectConnection = findConditionItem(catPhysical, "Down conductor tersambung langsung ke permukaan"),
        // --- PERBAIKAN DI SINI ---
        // Membaca catatan umum dari domain model ke UI State.
        // Anda perlu menambahkan field `notes: String` di data class `LightningProtectionPhysicalInspection`.
        notes = findTest("Pemeriksaan Fisik - Catatan")
    )

    val visual = LightningProtectionGroundingSystemVisualInspection(
        airTerminal = findTest("Visual - Penerima (Air Terminal)"),
        downConductor = findTest("Visual - Penghantar Penurunan"),
        groundingAndTestJoint = findTest("Visual - Pembumian dan Sambungan Ukur")
    )

    val catOther = LightningCategory.OTHER_STANDARDS
    val otherStandards = LightningProtectionOtherStandardsInspection(
        installationPlacementAsPerDrawing = findCheckItem(catOther, "Pemasangan Sesuai As Built Drawing"),
        airTerminalConnectionToDownConductor = findCheckItem(catOther, "Air Terminal Terhubung ke Penghantar"),
        downConductorJointsOnStructure = findCheckItem(catOther, "Sambungan pada Penghantar Penurunan"),
        groundingElectrodeUsesTestJointBox = findCheckItem(catOther, "Elektroda Pembumian Pakai Box Ukur"),
        downConductorMaterialStandard = findCheckItem(catOther, "Penghantar Penurunan Sesuai Standar"),
        lightningCounterInstalled = findCheckItem(catOther, "Terpasang Lightning Counter"),
        airTerminalIsRadioactive = findCheckItem(catOther, "Air Terminal Mengandung Radioaktif"),
        groundingElectrodeMaterialQuality = findCheckItem(catOther, "Elektroda Pembumian Non-Corrosive"),
        overvoltageProtectionInstalled = findCheckItem(catOther, "Proteksi Tegangan Lebih (Arrester)")
    )

    val measurements = this.testResults
        .filter { it.testName.startsWith("Pengukuran Tahanan #") }
        .map { res ->
            val notes = res.notes?.split('|') ?: listOf()
            LightningProtectionGroundingMeasurementItem(
                rValueOhm = res.result,
                ecDistance = notes.find { it.startsWith("EC:") }?.removePrefix("EC:") ?: "",
                epDistance = notes.find { it.startsWith("EP:") }?.removePrefix("EP:") ?: "",
                remarks = notes.find { it.startsWith("Remarks:") }?.removePrefix("Remarks:") ?: ""
            )
        }.toImmutableList()

    val groundingTests = this.checkItems
        .filter { it.category == LightningCategory.GROUNDING_RESISTANCE_TEST }
        .map { item ->
            val resultParts = item.result?.split('|') ?: listOf()
            LightningProtectionGroundingTestItem(
                itemChecked = item.itemName,
                result = item.status,
                measuredValue = resultParts.find { it.startsWith("Value:") }?.removePrefix("Value:") ?: "",
                remarks = resultParts.find { it.startsWith("Remarks:") }?.removePrefix("Remarks:") ?: ""
            )
        }.toImmutableList()

    val testingResults = LightningProtectionTestingResults(
        groundingResistanceMeasurement = measurements,
        groundingResistanceTest = groundingTests
    )

    val conclusion = LightningProtectionConclusion(
        summary = this.findings.find { it.type == FindingType.FINDING }?.description ?: "",
        recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }.toImmutableList()
    )

    return LightningProtectionUiState(
        isLoading = false,
        inspectionReport = LightningProtectionInspectionReport(
            extraId = this.inspection.extraId,
            moreExtraId = this.inspection.moreExtraId,
            serviceProviderData = serviceProvider,
            clientData = client,
            technicalData = technical,
            physicalInspection = physical,
            groundingSystemVisualInspection = visual,
            otherStandardsInspection = otherStandards,
            testingResults = testingResults,
            conclusion = conclusion
        )
    )
}