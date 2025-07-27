package com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection

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
private object FireProtectionCategory {
    const val DOC_CHECKLIST = "Pemeriksaan Dokumen"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun FireProtectionUiState.toInspectionWithDetailsDomain(currentTime: String, reportId: Long? = null): InspectionWithDetailsDomain {
    val report = this.inspectionReport
    val companyData = report.companyData
    val inspectionId: Long = reportId ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = report.extraId,
        moreExtraId = report.moreExtraId,
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.IPK,
        subInspectionType = SubInspectionType.Fire_Protection,
        equipmentType = companyData.objectType,
        examinationType = companyData.examinationType,
        ownerName = companyData.companyName,
        ownerAddress = companyData.companyLocation,
        usageLocation = companyData.usageLocation,
        addressUsageLocation = companyData.companyLocation,
        permitNumber = companyData.certificateNumber,
        reportDate = companyData.inspectionDate,
        createdAt = currentTime,
        isSynced = false
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

private fun createCheckItemsFromUiState(report: FireProtectionInspectionReport, inspectionId: Long): List<InspectionCheckItemDomain> {
    val items = mutableListOf<InspectionCheckItemDomain>()
    val cat = FireProtectionCategory.DOC_CHECKLIST
    report.documentChecklist.let {
        items.add(it.technicalDrawing.toCheckItem(inspectionId, cat, "Gambar Teknis"))
        items.add(it.previousTestDocumentation.toCheckItem(inspectionId, cat, "Dokumentasi Riksa/Uji Sebelumnya"))
        items.add(it.requestLetter.toCheckItem(inspectionId, cat, "Surat Permohonan"))
        items.add(it.specificationDocument.toCheckItem(inspectionId, cat, "Dokumen Spesifikasi"))
    }
    return items
}

private fun FireProtectionChecklistResult.toCheckItem(id: Long, cat: String, name: String) =
    InspectionCheckItemDomain(inspectionId = id, category = cat, itemName = name, status = this.isAvailable, result = this.remarks)

private fun createTestResultsFromUiState(report: FireProtectionInspectionReport, id: Long): List<InspectionTestResultDomain> {
    val results = mutableListOf<InspectionTestResultDomain>()
    fun addTest(name: String, value: String) { if (value.isNotBlank()) results.add(InspectionTestResultDomain(0, id, name, value, null)) }
    fun addBoolTest(name: String, value: Boolean) { results.add(InspectionTestResultDomain(0, id, name, value.toString(), null)) }

    report.buildingData.let {
        addTest("Bangunan - Luas Lahan (m²)", it.landAreaSqm)
        addTest("Bangunan - Luas Bangunan (m²)", it.buildingAreaSqm)
        addTest("Bangunan - Tinggi Bangunan (m)", it.buildingHeightM)
        addTest("Bangunan - Jumlah Lantai", it.floorCount)
        addTest("Bangunan - Dibangun Tahun", it.yearBuilt)
        it.construction.let { c ->
            addTest("Konstruksi - Struktur Utama", c.mainStructure); addTest("Konstruksi - Struktur Lantai", c.floorStructure); addTest("Konstruksi - Dinding Luar", c.exteriorWalls); addTest("Konstruksi - Dinding Dalam", c.interiorWalls); addTest("Konstruksi - Rangka Plafon", c.ceilingFrame); addTest("Konstruksi - Penutup Plafon", c.ceilingCover); addTest("Konstruksi - Rangka Atap", c.roofFrame); addTest("Konstruksi - Penutup Atap", c.roofCover)
        }
        it.fireProtectionEquipment.let { e ->
            addBoolTest("Perlengkapan - APAR", e.portableExtinguishers); addBoolTest("Perlengkapan - Box Hydrant Indoor", e.indoorHydrantBox); addBoolTest("Perlengkapan - Hydrant Pillar Outdoor", e.pillarAndOutdoorHydrant); addBoolTest("Perlengkapan - Siamese Connection", e.siameseConnection); addBoolTest("Perlengkapan - Instalasi Sprinkler", e.sprinklerSystem); addBoolTest("Perlengkapan - Detektor Panas & Asap", e.heatAndSmokeDetectors); addBoolTest("Perlengkapan - Petunjuk Arah", e.exitSigns); addBoolTest("Perlengkapan - Tangga Darurat", e.emergencyStairs); addBoolTest("Perlengkapan - Assembly Point", e.assemblyPoint)
        }
    }

    report.automaticFireAlarmSpecifications.let {
        it.mcfa.let { m ->
            addTest("MCFA - Merk/Type", m.brandOrType); addTest("MCFA - LED Annunciator", m.ledAnnunciator); addTest("MCFA - Type", m.type); addTest("MCFA - No. Serial", m.serialNumber); addTest("MCFA - Hasil", m.result)
            // PERBAIKAN: Hapus mapping untuk 'remarks' karena sudah dihapus dari UI state.
            // addTest("MCFA - Keterangan", m.remarks)
        }
        it.heatDetector.let { d ->
            addTest("Heat Detector - Merk/Type", d.brandOrType); addTest("Heat Detector - Jumlah Titik", d.pointCount); addTest("Heat Detector - Jarak (m)", d.spacingM); addTest("Heat Detector - Suhu Kerja (°C)", d.operatingTemperatureC); addTest("Heat Detector - Hasil", d.result)
            // PERBAIKAN: Hapus mapping untuk 'remarks'
            // addTest("Heat Detector - Keterangan", d.remarks)
        }
        it.smokeDetector.let { d ->
            addTest("Smoke Detector - Merk/Type", d.brandOrType); addTest("Smoke Detector - Jumlah Titik", d.pointCount); addTest("Smoke Detector - Jarak (m)", d.spacingM); addTest("Smoke Detector - Suhu Kerja (°C)", d.operatingTemperatureC); addTest("Smoke Detector - Hasil", d.result)
            // PERBAIKAN: Hapus mapping untuk 'remarks'
            // addTest("Smoke Detector - Keterangan", d.remarks)
        }
        it.apar.let { a ->
            addTest("APAR - Merk/Type", a.brandOrType); addTest("APAR - Jumlah", a.count); addTest("APAR - Jarak (m)", a.spacingM); addTest("APAR - Penempatan", a.placement); addTest("APAR - Hasil", a.result)
            // PERBAIKAN: Hapus mapping untuk 'remarks'
            // addTest("APAR - Keterangan", a.remarks)
        }
    }

    report.alarmInstallationTesting.let {
        addTest("Uji Alarm - Fungsi Kerja Panel", it.panelFunction); addTest("Uji Alarm - Test Alarm", it.alarmTest); addTest("Uji Alarm - Test Fault", it.faultTest); addTest("Uji Alarm - Test Interkoneksi", it.interconnectionTest)
        // PERBAIKAN: Hapus mapping untuk 'notes'
        // addTest("Uji Alarm - Catatan", it.notes)
    }

    report.alarmInstallationItems.forEachIndexed { i, item ->
        val notes = listOf(item.zone, item.ror, item.fixed, item.smoke, item.tpm, item.flsw, item.bell, item.lamp, item.status).joinToString("|")
        results.add(InspectionTestResultDomain(0, id, "Pemasangan Alarm #${i + 1}", item.location, notes))
    }
    addTest("Total Pemeriksaan Alarm", report.totalAlarmInstallation)
    addTest("Hasil Pemeriksaan Alarm", report.resultAlarmInstallation)

    report.hydrantSystemInstallation.let { h ->
        fun addSysComp(name: String, comp: FireProtectionSystemComponent) { addTest("Hydran - $name - Spec", comp.specification); addTest("Hydran - $name - Status", comp.status); addTest("Hydran - $name - Ket", comp.remarks) }
        addSysComp("Sumber Air Baku", h.waterSource)
        h.groundReservoir.let { addTest("Hydran - Ground Reservoir - Calc", it.calculation); addTest("Hydran - Ground Reservoir - Kapasitas", it.backupCapacityM3); addTest("Hydran - Ground Reservoir - Status", it.status); addTest("Hydran - Ground Reservoir - Ket", it.remarks) }
        addSysComp("Tangki Grafitasi", h.gravitationTank)
        addSysComp("Siamese Connection", h.siameseConnection)
        h.jockeyPump.let { p -> addTest("Jockey Pump - Model", p.model); addTest("Jockey Pump - No. Serial", p.serialNumber); addTest("Jockey Pump - Penggerak", p.driver); addTest("Jockey Pump - Daya/RPM", p.powerRpm); addTest("Jockey Pump - Penempatan", p.placement); addTest("Jockey Pump - Q (m³/H)", p.quantityM3H); addTest("Jockey Pump - H (m)", p.headM); addTest("Jockey Pump - Auto Start", p.autoStartKgCm2); addTest("Jockey Pump - Auto Stop", p.autoStopKgCm2) }
        h.electricPump.let { p -> addTest("Electric Pump - Model", p.model); addTest("Electric Pump - No. Serial", p.serialNumber); addTest("Electric Pump - Penggerak", p.driver); addTest("Electric Pump - Daya/RPM", p.powerRpm); addTest("Electric Pump - Penempatan", p.placement); addTest("Electric Pump - Q (Gpm)", p.quantityGpm); addTest("Electric Pump - H (m)", p.headM); addTest("Electric Pump - Auto Start", p.autoStartKgCm2); addTest("Electric Pump - Stop", p.stop) }
        h.dieselPump.let { p -> addTest("Diesel Pump - Model", p.model); addTest("Diesel Pump - No. Serial", p.serialNumber); addTest("Diesel Pump - Penggerak", p.driver); addTest("Diesel Pump - Q (US Gpm)", p.quantityUsGpm); addTest("Diesel Pump - H (m)", p.headM); addTest("Diesel Pump - Auto Start", p.autoStartKgCm2); addTest("Diesel Pump - Stop", p.stop) }
        h.buildingHydrant.let { p -> addTest("Building Hydrant - Titik", p.points); addTest("Building Hydrant - Dia Outlet", p.outletDiameterInch); addTest("Building Hydrant - Panjang Selang", p.hoseLengthM); addTest("Building Hydrant - Dia Nozzle", p.nozzleDiameterInch); addTest("Building Hydrant - Penempatan", p.placement) }
        h.landingValve.let { p -> addTest("Landing Valve - Titik", p.points); addTest("Landing Valve - Dia Outlet", p.outletDiameterInch); addTest("Landing Valve - Kopling", p.couplingType); addTest("Landing Valve - Penempatan", p.placement) }
        h.yardHydrant.let { p -> addTest("Yard Hydrant - Titik", p.points); addTest("Yard Hydrant - Dia Outlet", p.outletDiameterInch); addTest("Yard Hydrant - Panjang Selang", p.hoseLengthM); addTest("Yard Hydrant - Dia Nozzle", p.nozzleDiameterInch); addTest("Yard Hydrant - Penempatan", p.placement) }
        h.fireServiceConnection.let { p -> addTest("Fire Service - Titik", p.points); addTest("Fire Service - Dia Inlet", p.inletDiameterInch); addTest("Fire Service - Dia Outlet", p.outletDiameterInch); addTest("Fire Service - Kopling", p.couplingType); addTest("Fire Service - Kondisi", p.condition); addTest("Fire Service - Penempatan", p.placement) }
        addSysComp("Pressure Relief Valve", h.pressureReliefValve); addSysComp("Test Valve", h.testValve); addSysComp("Pipa Hisap", h.suctionPipe); addSysComp("Pipa Penyalur Utama", h.mainPipe); addSysComp("Pipa Tegak", h.standPipe); addSysComp("Hidran Pilar", h.hydrantPillar); addSysComp("Hidran Dalam Gedung", h.indoorHydrant); addSysComp("Hose Rell", h.hoseReel)
    }

    report.pumpFunctionTest.forEachIndexed { i, item ->
        val notes = "Start: ${item.startPressure}|Stop: ${item.stopPressure}"
        results.add(InspectionTestResultDomain(0, id, "Uji Fungsi Pompa #${i + 1}", item.pumpType, notes))
    }
    report.hydrantOperationalTest.forEachIndexed { i, item ->
        val notes = "Tekanan: ${item.pressure}|Pancar: ${item.jetLength}|Posisi: ${item.nozzlePosition}|Status: ${item.status}|Ket: ${item.remarks}"
        results.add(InspectionTestResultDomain(0, id, "Uji Operasional Hydran #${i + 1}", item.testPoint, notes))
    }
    return results
}

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toFireProtectionUiState(): FireProtectionUiState {
    fun findTest(name: String) = this.testResults.find { it.testName.equals(name, true) }?.result ?: ""
    fun findBoolTest(name: String) = findTest(name).toBoolean()
    fun findCheck(cat: String, name: String) = this.checkItems.find { it.category == cat && it.itemName.equals(name, true) }

    val companyData = FireProtectionCompanyData(
        examinationType = this.inspection.examinationType, companyName = this.inspection.ownerName ?: "", companyLocation = this.inspection.ownerAddress ?: "", usageLocation = this.inspection.usageLocation ?: "", certificateNumber = this.inspection.permitNumber ?: "", objectType = this.inspection.equipmentType, inspectionDate = this.inspection.reportDate ?: ""
    )

    val checklist = FireProtectionDocumentChecklist(
        technicalDrawing = findCheck(FireProtectionCategory.DOC_CHECKLIST, "Gambar Teknis")?.let { FireProtectionChecklistResult(it.status, it.result ?: "") } ?: FireProtectionChecklistResult(),
        previousTestDocumentation = findCheck(FireProtectionCategory.DOC_CHECKLIST, "Dokumentasi Riksa/Uji Sebelumnya")?.let { FireProtectionChecklistResult(it.status, it.result ?: "") } ?: FireProtectionChecklistResult(),
        requestLetter = findCheck(FireProtectionCategory.DOC_CHECKLIST, "Surat Permohonan")?.let { FireProtectionChecklistResult(it.status, it.result ?: "") } ?: FireProtectionChecklistResult(),
        specificationDocument = findCheck(FireProtectionCategory.DOC_CHECKLIST, "Dokumen Spesifikasi")?.let { FireProtectionChecklistResult(it.status, it.result ?: "") } ?: FireProtectionChecklistResult()
    )

    val buildingData = FireProtectionBuildingData(
        landAreaSqm = findTest("Bangunan - Luas Lahan (m²)"), buildingAreaSqm = findTest("Bangunan - Luas Bangunan (m²)"), buildingHeightM = findTest("Bangunan - Tinggi Bangunan (m)"), floorCount = findTest("Bangunan - Jumlah Lantai"), yearBuilt = findTest("Bangunan - Dibangun Tahun"),
        construction = FireProtectionConstruction(mainStructure = findTest("Konstruksi - Struktur Utama"), floorStructure = findTest("Konstruksi - Struktur Lantai"), exteriorWalls = findTest("Konstruksi - Dinding Luar"), interiorWalls = findTest("Konstruksi - Dinding Dalam"), ceilingFrame = findTest("Konstruksi - Rangka Plafon"), ceilingCover = findTest("Konstruksi - Penutup Plafon"), roofFrame = findTest("Konstruksi - Rangka Atap"), roofCover = findTest("Konstruksi - Penutup Atap")),
        fireProtectionEquipment = FireProtectionEquipment(portableExtinguishers = findBoolTest("Perlengkapan - APAR"), indoorHydrantBox = findBoolTest("Perlengkapan - Box Hydrant Indoor"), pillarAndOutdoorHydrant = findBoolTest("Perlengkapan - Hydrant Pillar Outdoor"), siameseConnection = findBoolTest("Perlengkapan - Siamese Connection"), sprinklerSystem = findBoolTest("Perlengkapan - Instalasi Sprinkler"), heatAndSmokeDetectors = findBoolTest("Perlengkapan - Detektor Panas & Asap"), exitSigns = findBoolTest("Perlengkapan - Petunjuk Arah"), emergencyStairs = findBoolTest("Perlengkapan - Tangga Darurat"), assemblyPoint = findBoolTest("Perlengkapan - Assembly Point"))
    )

    val alarmSpecs = FireProtectionAutomaticFireAlarmSpecifications(
        mcfa = FireProtectionMcfa(brandOrType = findTest("MCFA - Merk/Type"), ledAnnunciator = findTest("MCFA - LED Annunciator"), type = findTest("MCFA - Type"), serialNumber = findTest("MCFA - No. Serial"), result = findTest("MCFA - Hasil")), // PERBAIKAN: Hapus 'remarks'
        heatDetector = FireProtectionDetector(brandOrType = findTest("Heat Detector - Merk/Type"), pointCount = findTest("Heat Detector - Jumlah Titik"), spacingM = findTest("Heat Detector - Jarak (m)"), operatingTemperatureC = findTest("Heat Detector - Suhu Kerja (°C)"), result = findTest("Heat Detector - Hasil")), // PERBAIKAN: Hapus 'remarks'
        smokeDetector = FireProtectionDetector(brandOrType = findTest("Smoke Detector - Merk/Type"), pointCount = findTest("Smoke Detector - Jumlah Titik"), spacingM = findTest("Smoke Detector - Jarak (m)"), operatingTemperatureC = findTest("Smoke Detector - Suhu Kerja (°C)"), result = findTest("Smoke Detector - Hasil")), // PERBAIKAN: Hapus 'remarks'
        apar = FireProtectionApar(brandOrType = findTest("APAR - Merk/Type"), count = findTest("APAR - Jumlah"), spacingM = findTest("APAR - Jarak (m)"), placement = findTest("APAR - Penempatan"), result = findTest("APAR - Hasil")) // PERBAIKAN: Hapus 'remarks'
    )

    val alarmTesting = FireProtectionAlarmInstallationTesting(panelFunction = findTest("Uji Alarm - Fungsi Kerja Panel"), alarmTest = findTest("Uji Alarm - Test Alarm"), faultTest = findTest("Uji Alarm - Test Fault"), interconnectionTest = findTest("Uji Alarm - Test Interkoneksi")) // PERBAIKAN: Hapus 'notes'

    val alarmItems = this.testResults.filter { it.testName.startsWith("Pemasangan Alarm #") }.map {
        val notes = it.notes?.split('|') ?: emptyList()
        fun getNote(index: Int) = notes.getOrNull(index) ?: ""

        FireProtectionAlarmInstallationItem(
            location = it.result,
            zone = getNote(0),
            ror = getNote(1),
            fixed = getNote(2),
            smoke = getNote(3),
            tpm = getNote(4),
            flsw = getNote(5),
            bell = getNote(6),
            lamp = getNote(7),
            status = getNote(8)
        )
    }.toImmutableList()

    val totalAlarm = findTest("Total Pemeriksaan Alarm")
    val resultAlarm = findTest("Hasil Pemeriksaan Alarm")

    fun findSysComp(name: String) = FireProtectionSystemComponent(findTest("Hydran - $name - Spec"), findTest("Hydran - $name - Status"), findTest("Hydran - $name - Ket"))
    val hydrantSystem = FireProtectionHydrantSystemInstallation(
        waterSource = findSysComp("Sumber Air Baku"),
        groundReservoir = FireProtectionGroundReservoir(findTest("Hydran - Ground Reservoir - Calc"), findTest("Hydran - Ground Reservoir - Kapasitas"), findTest("Hydran - Ground Reservoir - Status"), findTest("Hydran - Ground Reservoir - Ket")),
        gravitationTank = findSysComp("Tangki Grafitasi"),
        siameseConnection = findSysComp("Siamese Connection"),
        jockeyPump = FireProtectionJockeyPump(findTest("Jockey Pump - Model"), findTest("Jockey Pump - No. Serial"), findTest("Jockey Pump - Penggerak"), findTest("Jockey Pump - Daya/RPM"), findTest("Jockey Pump - Penempatan"), findTest("Jockey Pump - Q (m³/H)"), findTest("Jockey Pump - H (m)"), findTest("Jockey Pump - Auto Start"), findTest("Jockey Pump - Auto Stop")),
        electricPump = FireProtectionElectricPump(findTest("Electric Pump - Model"), findTest("Electric Pump - No. Serial"), findTest("Electric Pump - Penggerak"), findTest("Electric Pump - Daya/RPM"), findTest("Electric Pump - Penempatan"), findTest("Electric Pump - Q (Gpm)"), findTest("Electric Pump - H (m)"), findTest("Electric Pump - Auto Start"), findTest("Electric Pump - Stop")),
        dieselPump = FireProtectionDieselPump(findTest("Diesel Pump - Model"), findTest("Diesel Pump - No. Serial"), findTest("Diesel Pump - Penggerak"), findTest("Diesel Pump - Q (US Gpm)"), findTest("Diesel Pump - H (m)"), findTest("Diesel Pump - Auto Start"), findTest("Diesel Pump - Stop")),
        buildingHydrant = FireProtectionBuildingHydrant(findTest("Building Hydrant - Titik"), findTest("Building Hydrant - Dia Outlet"), findTest("Building Hydrant - Panjang Selang"), findTest("Building Hydrant - Dia Nozzle"), findTest("Building Hydrant - Penempatan")),
        landingValve = FireProtectionLandingValve(findTest("Landing Valve - Titik"), findTest("Landing Valve - Dia Outlet"), findTest("Landing Valve - Kopling"), findTest("Landing Valve - Penempatan")),
        yardHydrant = FireProtectionYardHydrant(findTest("Yard Hydrant - Titik"), findTest("Yard Hydrant - Dia Outlet"), findTest("Yard Hydrant - Panjang Selang"), findTest("Yard Hydrant - Dia Nozzle"), findTest("Yard Hydrant - Penempatan")),
        fireServiceConnection = FireProtectionFireServiceConnection(findTest("Fire Service - Titik"), findTest("Fire Service - Dia Inlet"), findTest("Fire Service - Dia Outlet"), findTest("Fire Service - Kopling"), findTest("Fire Service - Kondisi"), findTest("Fire Service - Penempatan")),
        pressureReliefValve = findSysComp("Pressure Relief Valve"), testValve = findSysComp("Test Valve"), suctionPipe = findSysComp("Pipa Hisap"), mainPipe = findSysComp("Pipa Penyalur Utama"), standPipe = findSysComp("Pipa Tegak"), hydrantPillar = findSysComp("Hidran Pilar"), indoorHydrant = findSysComp("Hidran Dalam Gedung"), hoseReel = findSysComp("Hose Rell")
    )

    val pumpTests = this.testResults.filter { it.testName.startsWith("Uji Fungsi Pompa #") }.map {
        val notes = it.notes?.split('|') ?: listOf()
        FireProtectionPumpFunctionTestItem(it.result, notes.find { n -> n.startsWith("Start:") }?.removePrefix("Start:") ?: "", notes.find { n -> n.startsWith("Stop:") }?.removePrefix("Stop:") ?: "")
    }.toImmutableList()

    val hydrantTests = this.testResults.filter { it.testName.startsWith("Uji Operasional Hydran #") }.map {
        val notes = it.notes?.split('|') ?: listOf()
        FireProtectionHydrantOperationalTestItem(it.result, notes.find { n -> n.startsWith("Tekanan:") }?.removePrefix("Tekanan:") ?: "", notes.find { n -> n.startsWith("Pancar:") }?.removePrefix("Pancar:") ?: "", notes.find { n -> n.startsWith("Posisi:") }?.removePrefix("Posisi:") ?: "", notes.find { n -> n.startsWith("Status:") }?.removePrefix("Status:") ?: "", notes.find { n -> n.startsWith("Ket:") }?.removePrefix("Ket:") ?: "")
    }.toImmutableList()

    val conclusion = FireProtectionConclusion(
        summary = this.findings.find { it.type == FindingType.FINDING }?.description ?: "",
        recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }.toImmutableList()
    )

    return FireProtectionUiState(
        isLoading = false,
        inspectionReport = FireProtectionInspectionReport(
            extraId = this.inspection.extraId,
            moreExtraId = this.inspection.moreExtraId,
            documentChecklist = checklist,
            companyData = companyData,
            buildingData = buildingData,
            automaticFireAlarmSpecifications = alarmSpecs,
            alarmInstallationTesting = alarmTesting,
            alarmInstallationItems = alarmItems,
            totalAlarmInstallation = totalAlarm,
            resultAlarmInstallation = resultAlarm,
            hydrantSystemInstallation = hydrantSystem,
            pumpFunctionTest = pumpTests,
            hydrantOperationalTest = hydrantTests,
            conclusion = conclusion
        )
    )
}