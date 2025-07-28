package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.local.utils.toDocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.toInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toSubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapMeasurement
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapTestResults
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningBapVisualInspection
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningDynamicItem
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningDynamicTestItem
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningOwnerData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningPhysicalInspection
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningPjk3Data
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningResultStatusDetail
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningStandardCompliance
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningStandardComplianceDetail
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.lightning.LightningVisualInspection
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain

// --- Kategori yang digunakan untuk konsistensi dengan Mapper UI-Domain ---

private object LightningBAPCategory {
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val VISUAL_CONTROL_BOX = "$VISUAL_INSPECTION - Kotak Kontrol"
    const val TESTING = "PENGUJIAN"

    object ItemName {
        const val IS_AVAILABLE = "Tersedia"
        const val IS_GOOD_CONDITION = "Kondisi Baik"
    }
}

private object LightningCategory {
    const val PHYSICAL_INSPECTION = "Pemeriksaan Fisik Instalasi"
    const val OTHER_STANDARDS = "Pemeriksaan Standar Lainnya"
    const val GROUNDING_RESISTANCE_TEST = "Pengujian Tahanan Pembumian"
}

// =================================================================================================
//                                  BAP: Domain -> DTO
// =================================================================================================

fun InspectionWithDetailsDomain.toLightningBapRequest(): LightningBapRequest {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = LightningBapGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = LightningBapTechnicalData(
        conductorType = findTestResult("Jenis Instalasi"),
        serialNumber = this.inspection.serialNumber ?: "",
        buildingHeight = findTestResult("Tinggi Bangunan (m)").toIntOrNull() ?: 0,
        buildingArea = findTestResult("Luas Bangunan (m2)").toIntOrNull() ?: 0,
        receiverHeight = findTestResult("Tinggi Penerima (m)").toIntOrNull() ?: 0,
        receiverCount = findTestResult("Jumlah Penerima").toIntOrNull() ?: 0,
        groundElectrodeCount = findTestResult("Jumlah Elektroda Tanah").toIntOrNull() ?: 0,
        conductorDescription = findTestResult("Deskripsi Konduktor (mm)"),
        installer = findTestResult("Tahun Pemasangan"), // DTO installer, UI Year
        groundingResistance = findTestResult("Tahanan Pembumian (Ohm)").toDoubleOrNull() ?: 0.0
    )

    val visualInspection = LightningBapVisualInspection(
        isSystemOverallGood = findBoolItem(LightningBAPCategory.VISUAL_INSPECTION, "Sistem secara keseluruhan baik"),
        isReceiverConditionGood = findBoolItem(LightningBAPCategory.VISUAL_INSPECTION, "Kondisi penerima baik"),
        isReceiverPoleConditionGood = findBoolItem(LightningBAPCategory.VISUAL_INSPECTION, "Kondisi tiang penerima baik"),
        isConductorInsulated = findBoolItem(LightningBAPCategory.VISUAL_INSPECTION, "Konduktor terisolasi"),
        isControlBoxAvailable = findBoolItem(LightningBAPCategory.VISUAL_CONTROL_BOX, LightningBAPCategory.ItemName.IS_AVAILABLE),
        isControlBoxConditionGood = findBoolItem(LightningBAPCategory.VISUAL_CONTROL_BOX, LightningBAPCategory.ItemName.IS_GOOD_CONDITION)
    )

    val measurement = LightningBapMeasurement(
        conductorContinuityResult = if (findBoolItem(LightningBAPCategory.TESTING, "Hasil kontinuitas konduktor baik")) "Baik" else "Tidak Baik",
        // --- PERBAIKAN DI SINI ---
        // Mengambil nilai pengukuran dari domain model, tidak lagi hardcoded.
        measuredGroundingResistance = findTestResult("Hasil Ukur Tahanan Pembumian (BAP)"),
        measuredGroundingResistanceResult = findBoolItem(LightningBAPCategory.TESTING, "Hasil pengukuran tahanan pembumian baik")
    )

    return LightningBapRequest(
        laporanId = this.inspection.extraId,
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        equipmentType = this.inspection.equipmentType,
        extraId = this.inspection.id,
        generalData = generalData,
        technicalData = technicalData,
        testResults = LightningBapTestResults(visualInspection, measurement)
    )
}

// =================================================================================================
//                                  BAP: DTO -> Domain
// =================================================================================================

fun LightningBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id,
        documentType = this.documentType.toDocumentType() ?: DocumentType.BAP,
        inspectionType = InspectionType.ILPP, // BAP Penyalur Petir selalu ILPP
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Lightning_Conductor,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.addressUsageLocation,
        serialNumber = this.technicalData.serialNumber,
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    this.testResults.visualInspection.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_INSPECTION, itemName = "Sistem secara keseluruhan baik", status = it.isSystemOverallGood))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi penerima baik", status = it.isReceiverConditionGood))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi tiang penerima baik", status = it.isReceiverPoleConditionGood))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_INSPECTION, itemName = "Konduktor terisolasi", status = it.isConductorInsulated))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_CONTROL_BOX, itemName = LightningBAPCategory.ItemName.IS_AVAILABLE, status = it.isControlBoxAvailable))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.VISUAL_CONTROL_BOX, itemName = LightningBAPCategory.ItemName.IS_GOOD_CONDITION, status = it.isControlBoxConditionGood))
    }
    this.testResults.measurement.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.TESTING, itemName = "Hasil kontinuitas konduktor baik", status = it.conductorContinuityResult.equals("Baik", true)))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = LightningBAPCategory.TESTING, itemName = "Hasil pengukuran tahanan pembumian baik", status = it.measuredGroundingResistanceResult))
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    this.technicalData.let {
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jenis Instalasi", result = it.conductorType, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tinggi Bangunan (m)", result = it.buildingHeight.toString(), notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Luas Bangunan (m2)", result = it.buildingArea.toString(), notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tinggi Penerima (m)", result = it.receiverHeight.toString(), notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Penerima", result = it.receiverCount.toString(), notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Elektroda Tanah", result = it.groundElectrodeCount.toString(), notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Deskripsi Konduktor (mm)", result = it.conductorDescription, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tahun Pemasangan", result = it.installer, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tahanan Pembumian (Ohm)", result = it.groundingResistance.toString(), notes = null))
    }

    // --- PERBAIKAN DI SINI ---
    // Menyimpan nilai pengukuran dari DTO ke domain model agar tidak hilang.
    this.testResults.measurement.let {
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Hasil Ukur Tahanan Pembumian (BAP)", result = it.measuredGroundingResistance, notes = null))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(), // BAP tidak punya findings
        testResults = testResults
    )
}

// =================================================================================================
//                                  Report: Domain -> DTO
// =================================================================================================

fun InspectionWithDetailsDomain.toLightningReportRequest(): LightningReportRequest {
    fun findTest(name: String) = this.testResults.find { it.testName.equals(name, true) }?.result ?: ""
    fun findCheck(cat: String, name: String) = this.checkItems.find { it.category == cat && it.itemName.equals(name, true) }

    fun findConditionItem(cat: String, name: String): LightningResultStatusDetail {
        val item = findCheck(cat, name)
        val parts = item?.result?.split('|', limit = 2) ?: listOf("")
        val condition = parts[0]
        return LightningResultStatusDetail(
            good = condition == "Baik",
            fair = condition == "Kurang Baik",
            poor = condition == "Buruk"
        )
    }

    fun findStandardComplianceItem(cat: String, name: String): LightningStandardComplianceDetail {
        val item = findCheck(cat, name)
        return LightningStandardComplianceDetail(
            good = item?.status ?: false,
            poor = !(item?.status ?: true),
            notes = item?.result ?: ""
        )
    }

    val pjk3Data = LightningPjk3Data(
        companyNameAndAddress = findTest("PJK3 - Nama & Alamat"),
        companyPermitNo = findTest("PJK3 - SKP Perusahaan"),
        expertPermitNo = findTest("PJK3 - SKP Ahli K3"),
        expertName = this.inspection.inspectorName ?: "",
        riksaujiTools = findTest("PJK3 - Peralatan Riksa Uji")
    )

    val ownerData = LightningOwnerData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        objectType = this.inspection.equipmentType,
        typeInspection = this.inspection.examinationType,
        certificateNo = this.inspection.permitNumber ?: "",
        inspectionDate = this.inspection.reportDate ?: ""
    )

    val technicalData = LightningTechnicalData(
        conductorType = this.inspection.driveType ?: "",
        buildingHeight = findTest("Teknis - Tinggi Bangunan"),
        buildingArea = findTest("Teknis - Luas Bangunan"),
        receiverHeight = findTest("Teknis - Tinggi Penerima"),
        receiverCount = findTest("Teknis - Jumlah Penerima").toIntOrNull() ?: 0,
        testJointCount = findTest("Teknis - Jumlah sambungan ukur").toIntOrNull() ?: 0,
        conductorDescription = findTest("Teknis - Jumlah hantaran penyalur"),
        groundingResistance = findTest("Teknis - Jenis & Ukuran Hantaran"),
        spreadingResistance = findTest("Teknis - Tahanan Sebaran Tanah"),
        installer = "${findTest("Teknis - Tahun Pemasangan")} / ${findTest("Teknis - Instalatir")}"
    )

    val catPhysical = LightningCategory.PHYSICAL_INSPECTION
    val physicalInspection = LightningPhysicalInspection(
        installationSystem = findConditionItem(catPhysical, "Sistem Instalasi"),
        receiverHead = findConditionItem(catPhysical, "Penerima (Head)"),
        receiverPole = findConditionItem(catPhysical, "Tiang penerima"),
        poleReinforcement = findConditionItem(catPhysical, "Sistem Penguatan Tiang Penerima"),
        downConductor = findConditionItem(catPhysical, "Penghantar penurunan"),
        conductorClamps = findConditionItem(catPhysical, "Klem Kabel Penghantar"),
        jointConnections = findConditionItem(catPhysical, "Kondisi Joins sambungan"),
        groundingTerminalBox = findConditionItem(catPhysical, "Kotak Down Conductor dan terminal grounding"),
        controlBox = findConditionItem(catPhysical, "Bak Kontrol"),
        groundingSystem = findConditionItem(catPhysical, "System Pentanahan"),
        conductorToGroundConnection = findConditionItem(catPhysical, "Down conductor tersambung langsung ke permukaan"),
        // --- PERBAIKAN DI SINI ---
        // Mengambil catatan dari domain model, tidak lagi hardcoded.
        notes = findTest("Pemeriksaan Fisik - Catatan")
    )

    val visualInspection = LightningVisualInspection(
        airTerminal = findTest("Visual - Penerima (Air Terminal)"),
        downConductorCheck = findTest("Visual - Penghantar Penurunan"),
        groundingAndTestJoint = findTest("Visual - Pembumian dan Sambungan Ukur")
    )

    val catOther = LightningCategory.OTHER_STANDARDS
    val standardCompliance = LightningStandardCompliance(
        asBuiltDrawing = findStandardComplianceItem(catOther, "Pemasangan Sesuai As Built Drawing"),
        terminalToConductorConnection = findStandardComplianceItem(catOther, "Air Terminal Terhubung ke Penghantar"),
        downConductorJoints = findStandardComplianceItem(catOther, "Sambungan pada Penghantar Penurunan"),
        testJointBoxInstallation = findStandardComplianceItem(catOther, "Elektroda Pembumian Pakai Box Ukur"),
        conductorMaterialStandard = findStandardComplianceItem(catOther, "Penghantar Penurunan Sesuai Standar"),
        lightningCounter = findStandardComplianceItem(catOther, "Terpasang Lightning Counter"),
        radioactiveTerminal = findStandardComplianceItem(catOther, "Air Terminal Mengandung Radioaktif"),
        groundingRodMaterial = findStandardComplianceItem(catOther, "Elektroda Pembumian Non-Corrosive"),
        arresterInstallation = findStandardComplianceItem(catOther, "Proteksi Tegangan Lebih (Arrester)")
    )

    val dynamicTestItems = this.testResults
        .filter { it.testName.startsWith("Pengukuran Tahanan #") }
        .map { res ->
            val notes = res.notes?.split('|') ?: listOf()
            LightningDynamicTestItem(
                rValue = res.result.toDoubleOrNull() ?: 0.0,
                ecResult = notes.find { it.startsWith("EC:") }?.removePrefix("EC:") ?: "",
                epResult = notes.find { it.startsWith("EP:") }?.removePrefix("EP:") ?: "",
                result = notes.find { it.startsWith("Remarks:") }?.removePrefix("Remarks:") ?: ""
            )
        }

    val dynamicItems = this.checkItems
        .filter { it.category == LightningCategory.GROUNDING_RESISTANCE_TEST }
        .map { item ->
            val resultParts = item.result?.split('|') ?: listOf()
            LightningDynamicItem(
                materialConditionsItems = item.itemName,
                outcomeBaik = item.status,
                outcomeBuruk = !item.status,
                rGradeItems = resultParts.find { it.startsWith("Value:") }?.removePrefix("Value:") ?: "",
                result = resultParts.find { it.startsWith("Remarks:") }?.removePrefix("Remarks:") ?: ""
            )
        }

    val conclusionText = this.findings.find { it.type == FindingType.FINDING }?.description ?: ""
    val recommendationsText = this.findings.filter { it.type == FindingType.RECOMMENDATION }.joinToString("\n") { it.description }

    return LightningReportRequest(
        examinationType = this.inspection.examinationType,
        equipmentType = this.inspection.equipmentType,
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        createdAt = this.inspection.createdAt ?: "",
        extraId = this.inspection.id,
        pjk3Data = pjk3Data,
        ownerData = ownerData,
        technicalData = technicalData,
        physicalInspection = physicalInspection,
        visualInspection = visualInspection,
        standardCompliance = standardCompliance,
        dynamicTestItems = dynamicTestItems,
        dynamicItems = dynamicItems,
        conclusion = conclusionText,
        recomendations = recommendationsText
    )
}

// =================================================================================================
//                                  Report: DTO -> Domain
// =================================================================================================

fun LightningReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id, // ID dari cloud
        moreExtraId = "",
        documentType = this.documentType.toDocumentType() ?: DocumentType.LAPORAN,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.ILPP,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Lightning_Conductor,
        equipmentType = this.ownerData.objectType,
        examinationType = this.ownerData.typeInspection,
        ownerName = this.ownerData.companyName,
        ownerAddress = this.ownerData.companyLocation,
        usageLocation = this.ownerData.usageLocation,
        addressUsageLocation = this.ownerData.companyLocation,
        driveType = this.technicalData.conductorType,
        permitNumber = this.ownerData.certificateNo,
        reportDate = this.ownerData.inspectionDate,
        inspectorName = this.pjk3Data.expertName,
        createdAt = this.createdAt,
        isSynced = true,
        isEdited = false
    )

    fun LightningResultStatusDetail.toCheckItem(cat: String, name: String): InspectionCheckItemDomain {
        val condition = when { good -> "Baik"; fair -> "Kurang Baik"; poor -> "Buruk"; else -> "" }
        return InspectionCheckItemDomain(0, inspectionId, cat, name, good || fair || poor, condition)
    }

    fun LightningStandardComplianceDetail.toCheckItem(cat: String, name: String): InspectionCheckItemDomain {
        return InspectionCheckItemDomain(0, inspectionId, cat, name, this.good, this.notes)
    }

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    val catPhysical = LightningCategory.PHYSICAL_INSPECTION
    this.physicalInspection.let {
        checkItems.add(it.installationSystem.toCheckItem(catPhysical, "Sistem Instalasi"))
        checkItems.add(it.receiverHead.toCheckItem(catPhysical, "Penerima (Head)"))
        checkItems.add(it.receiverPole.toCheckItem(catPhysical, "Tiang penerima"))
        checkItems.add(it.poleReinforcement.toCheckItem(catPhysical, "Sistem Penguatan Tiang Penerima"))
        checkItems.add(it.downConductor.toCheckItem(catPhysical, "Penghantar penurunan"))
        checkItems.add(it.conductorClamps.toCheckItem(catPhysical, "Klem Kabel Penghantar"))
        checkItems.add(it.jointConnections.toCheckItem(catPhysical, "Kondisi Joins sambungan"))
        checkItems.add(it.groundingTerminalBox.toCheckItem(catPhysical, "Kotak Down Conductor dan terminal grounding"))
        checkItems.add(it.controlBox.toCheckItem(catPhysical, "Bak Kontrol"))
        checkItems.add(it.groundingSystem.toCheckItem(catPhysical, "System Pentanahan"))
        checkItems.add(it.conductorToGroundConnection.toCheckItem(catPhysical, "Down conductor tersambung langsung ke permukaan"))
    }

    val catOther = LightningCategory.OTHER_STANDARDS
    this.standardCompliance.let {
        checkItems.add(it.asBuiltDrawing.toCheckItem(catOther, "Pemasangan Sesuai As Built Drawing"))
        checkItems.add(it.terminalToConductorConnection.toCheckItem(catOther, "Air Terminal Terhubung ke Penghantar"))
        checkItems.add(it.downConductorJoints.toCheckItem(catOther, "Sambungan pada Penghantar Penurunan"))
        checkItems.add(it.testJointBoxInstallation.toCheckItem(catOther, "Elektroda Pembumian Pakai Box Ukur"))
        checkItems.add(it.conductorMaterialStandard.toCheckItem(catOther, "Penghantar Penurunan Sesuai Standar"))
        checkItems.add(it.lightningCounter.toCheckItem(catOther, "Terpasang Lightning Counter"))
        checkItems.add(it.radioactiveTerminal.toCheckItem(catOther, "Air Terminal Mengandung Radioaktif"))
        checkItems.add(it.groundingRodMaterial.toCheckItem(catOther, "Elektroda Pembumian Non-Corrosive"))
        checkItems.add(it.arresterInstallation.toCheckItem(catOther, "Proteksi Tegangan Lebih (Arrester)"))
    }

    this.dynamicItems.forEach {
        val resultString = "Value: ${it.rGradeItems}|Remarks: ${it.result}"
        checkItems.add(InspectionCheckItemDomain(0, inspectionId, LightningCategory.GROUNDING_RESISTANCE_TEST, it.materialConditionsItems, it.outcomeBaik, resultString))
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    this.pjk3Data.let {
        testResults.add(InspectionTestResultDomain(0, inspectionId, "PJK3 - Nama & Alamat", it.companyNameAndAddress, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "PJK3 - SKP Perusahaan", it.companyPermitNo, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "PJK3 - SKP Ahli K3", it.expertPermitNo, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "PJK3 - Peralatan Riksa Uji", it.riksaujiTools, null))
    }
    this.technicalData.let {
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Teknis - Tinggi Bangunan", it.buildingHeight, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Teknis - Luas Bangunan", it.buildingArea, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Teknis - Tinggi Penerima", it.receiverHeight, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Teknis - Jumlah Penerima", it.receiverCount.toString(), null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Teknis - Jumlah sambungan ukur", it.testJointCount.toString(), null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Teknis - Jenis & Ukuran Hantaran", it.conductorDescription, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Jenis & Ukuran Hantaran", it.groundingResistance, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Teknis - Tahanan Sebaran Tanah", it.spreadingResistance, null))
        val tahunPemasangan = it.installer.split("/").getOrNull(0)?.trim() ?: it.installer
        val installer = it.installer.split("/").getOrNull(1)?.trim() ?: ""
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Teknis - Tahun Pemasangan", tahunPemasangan, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Teknis - Instalatir", installer, null))
    }
    this.visualInspection.let {
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Visual - Penerima (Air Terminal)", it.airTerminal, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Visual - Penghantar Penurunan", it.downConductorCheck, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Visual - Pembumian dan Sambungan Ukur", it.groundingAndTestJoint, null))
    }
    // --- PERBAIKAN DI SINI ---
    // Menyimpan catatan umum dari DTO ke domain model agar tidak hilang.
    this.physicalInspection.let {
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Pemeriksaan Fisik - Catatan", it.notes, null))
    }

    this.dynamicTestItems.forEachIndexed { index, item ->
        val notes = "EC: ${item.ecResult}|EP: ${item.epResult}|Remarks: ${item.result}"
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Pengukuran Tahanan #${index + 1}", item.rValue.toString(), notes))
    }

    val findings = mutableListOf<InspectionFindingDomain>()
    if (this.conclusion.isNotBlank()) {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = this.conclusion, type = FindingType.FINDING))
    }
    this.recomendations.split("\n").forEach {
        if(it.isNotBlank()) findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION))
    }

    return InspectionWithDetailsDomain(inspectionDomain, checkItems, findings, testResults)
}