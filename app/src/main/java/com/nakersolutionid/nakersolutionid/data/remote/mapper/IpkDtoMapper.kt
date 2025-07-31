package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.local.utils.toDocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.toInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toSubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkAlarmInstallationData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkAlarmTestResults
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkApar
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapFunctionalTests
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapTestResults
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBapVisualInspection
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkBuildingData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkConstruction
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkDieselPump
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkDocumentCheckItem
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkDocumentChecklist
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkElectricPump
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkFireProtectionEquipment
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkFireServiceConnection
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkGravitationTank
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkGroundReservoir
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkHeatDetector
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkHydrantOperationalTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkHydrantSystem
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkIndoorHydrant
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkJockeyPump
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkLandingValve
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkMcfa
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkOutdoorHydrant
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkPipeValveItem
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkPipingAndValves
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkPumpFunctionTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkPumps
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkSiameseConnection
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkSmokeDetector
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkTechnicalSpecifications
import com.nakersolutionid.nakersolutionid.data.remote.dto.ipk.IpkWaterSource
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain

// --- Kategori yang digunakan untuk konsistensi dengan Mapper UI-Domain ---

private object FireProtectionBAPCategory {
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val VISUAL_APAR = "$VISUAL_INSPECTION - APAR"
    const val VISUAL_PUMP = "$VISUAL_INSPECTION - Pompa"
    const val VISUAL_SPRINKLER = "$VISUAL_INSPECTION - Sistem Sprinkler"
    const val VISUAL_DETECTOR = "$VISUAL_INSPECTION - Sistem Detektor"
    const val TESTING = "PENGUJIAN"
    const val TESTING_DETECTOR = "$TESTING - Detektor"
}

private object FireProtectionCategory {
    const val DOC_CHECKLIST = "Pemeriksaan Dokumen"
}


// =================================================================================================
//                                  BAP: Domain -> DTO
// =================================================================================================

fun InspectionWithDetailsDomain.toIpkBapRequest(): IpkBapRequest {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = IpkBapGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = IpkBapTechnicalData(
        landArea = findTestResult("Luas Area (m2)"),
        buildingArea = findTestResult("Luas Bangunan (m2)"),
        buildingHeight = findTestResult("Tinggi Bangunan (m)"),
        floorCount = findTestResult("Jumlah Lantai"),
        pillarAndOutdoorHydrant = findTestResult("Jumlah Hydrant Pilar"),
        totalHydrantBuilding = findTestResult("Jumlah Hydrant Gedung"),
        totalHoseRell = findTestResult("Jumlah Hose Rell"),
        totalSpinkler = findTestResult("Jumlah Sprinkler"),
        totalHeatDetector = findTestResult("Jumlah Heat Detector"),
        totalSmokeDetector = findTestResult("Jumlah Smoke Detector"),
        totalFlameDetector = findTestResult("Jumlah Flame Detector"),
        totalGasDetector = findTestResult("Jumlah Gas Detector"),
        manualButton = findTestResult("Tombol Manual"),
        alarmBell = findTestResult("Alarm Bell"),
        signalAlarmLamp = findTestResult("Lampu Indikator Alarm"),
        emergencyExit = findTestResult("Pintu Darurat"),
        apar = findTestResult("APAR"),
        certificateNumber = this.inspection.serialNumber ?: "" // DTO uses certificateNumber for BAP serial
    )

    val visualInspection = IpkBapVisualInspection(
        isAparAvailable = findBoolItem(FireProtectionBAPCategory.VISUAL_APAR, "Tersedia"),
        isAparInGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_APAR, "Kondisi Baik"),
        isHydrantPanelInGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_INSPECTION, "Kondisi Panel Hidran Baik"),
        isPumpsAvailable = findBoolItem(FireProtectionBAPCategory.VISUAL_PUMP, "Tersedia"),
        isPumpsInGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_PUMP, "Kondisi Baik"),
        isSprinklerSystemAvailable = findBoolItem(FireProtectionBAPCategory.VISUAL_SPRINKLER, "Tersedia"),
        isSprinklerSystemInGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_SPRINKLER, "Kondisi Baik"),
        isDetectorSystemAvailable = findBoolItem(FireProtectionBAPCategory.VISUAL_DETECTOR, "Tersedia"),
        isDetectorSystemInGoodCondition = findBoolItem(FireProtectionBAPCategory.VISUAL_DETECTOR, "Kondisi Baik")
    )

    val functionalTests = IpkBapFunctionalTests(
        isAparFunctional = findBoolItem(FireProtectionBAPCategory.TESTING, "APAR Berfungsi"),
        arePumpsFunctional = findBoolItem(FireProtectionBAPCategory.TESTING, "Hasil Tes Pompa Baik"),
        isSprinklerFunctional = findBoolItem(FireProtectionBAPCategory.TESTING, "Sprinkler Berfungsi"),
        isDetectorFunctional = findBoolItem(FireProtectionBAPCategory.TESTING_DETECTOR, "Berfungsi"),
        isDetectorConnectedToMcfa = findBoolItem(FireProtectionBAPCategory.TESTING_DETECTOR, "Terhubung ke MCFA")
    )

    return IpkBapRequest(
        laporanId = this.inspection.extraId,
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        extraId = this.inspection.id,
        createdAt = this.inspection.createdAt ?: "",
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        equipmentType = this.inspection.equipmentType,
        generalData = generalData,
        technicalData = technicalData,
        testResults = IpkBapTestResults(visualInspection, functionalTests)
    )
}

// =================================================================================================
//                                  BAP: DTO -> Domain
// =================================================================================================

fun IpkBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId.toLong()

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id,
        documentType = this.documentType.toDocumentType() ?: DocumentType.BAP,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.IPK,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Fire_Protection,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.addressUsageLocation,
        serialNumber = this.technicalData.certificateNumber,
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    this.testResults.visualInspection.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_APAR, itemName = "Tersedia", status = it.isAparAvailable))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_APAR, itemName = "Kondisi Baik", status = it.isAparInGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Panel Hidran Baik", status = it.isHydrantPanelInGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_PUMP, itemName = "Tersedia", status = it.isPumpsAvailable))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_PUMP, itemName = "Kondisi Baik", status = it.isPumpsInGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_SPRINKLER, itemName = "Tersedia", status = it.isSprinklerSystemAvailable))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_SPRINKLER, itemName = "Kondisi Baik", status = it.isSprinklerSystemInGoodCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_DETECTOR, itemName = "Tersedia", status = it.isDetectorSystemAvailable))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.VISUAL_DETECTOR, itemName = "Kondisi Baik", status = it.isDetectorSystemInGoodCondition))
    }
    this.testResults.functionalTests.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING, itemName = "APAR Berfungsi", status = it.isAparFunctional))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING, itemName = "Hasil Tes Pompa Baik", status = it.arePumpsFunctional))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING, itemName = "Sprinkler Berfungsi", status = it.isSprinklerFunctional))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING_DETECTOR, itemName = "Berfungsi", status = it.isDetectorFunctional))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = FireProtectionBAPCategory.TESTING_DETECTOR, itemName = "Terhubung ke MCFA", status = it.isDetectorConnectedToMcfa))
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    this.technicalData.let {
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Luas Area (m2)", result = it.landArea, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Luas Bangunan (m2)", result = it.buildingArea, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tinggi Bangunan (m)", result = it.buildingHeight, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Lantai", result = it.floorCount, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Hydrant Pilar", result = it.pillarAndOutdoorHydrant, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Hydrant Gedung", result = it.totalHydrantBuilding, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Hose Rell", result = it.totalHoseRell, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Sprinkler", result = it.totalSpinkler, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Heat Detector", result = it.totalHeatDetector, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Smoke Detector", result = it.totalSmokeDetector, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Flame Detector", result = it.totalFlameDetector, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jumlah Gas Detector", result = it.totalGasDetector, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tombol Manual", result = it.manualButton, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Alarm Bell", result = it.alarmBell, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Lampu Indikator Alarm", result = it.signalAlarmLamp, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Pintu Darurat", result = it.emergencyExit, notes = null))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "APAR", result = it.apar, notes = null))
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

fun InspectionWithDetailsDomain.toIpkReportRequest(): IpkReportRequest {
    fun findTest(name: String) = this.testResults.find { it.testName.equals(name, true) }?.result ?: ""
    fun findBoolTest(name: String) = findTest(name) // DTO for equipment is string "true" or "false"
    fun findCheck(cat: String, name: String) = this.checkItems.find { it.category == cat && it.itemName.equals(name, true) }

    val documentChecklist = IpkDocumentChecklist(
        technicalDrawing = findCheck(FireProtectionCategory.DOC_CHECKLIST, "Gambar Teknis")?.let { IpkDocumentCheckItem(it.status, it.result ?: "") } ?: IpkDocumentCheckItem(false, ""),
        previousTestDocumentation = findCheck(FireProtectionCategory.DOC_CHECKLIST, "Dokumentasi Riksa/Uji Sebelumnya")?.let { IpkDocumentCheckItem(it.status, it.result ?: "") } ?: IpkDocumentCheckItem(false, ""),
        requestLetter = findCheck(FireProtectionCategory.DOC_CHECKLIST, "Surat Permohonan")?.let { IpkDocumentCheckItem(it.status, it.result ?: "") } ?: IpkDocumentCheckItem(false, ""),
        specificationDocument = findCheck(FireProtectionCategory.DOC_CHECKLIST, "Dokumen Spesifikasi")?.let { IpkDocumentCheckItem(it.status, it.result ?: "") } ?: IpkDocumentCheckItem(false, "")
    )

    val generalData = IpkGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        certificateNumber = this.inspection.permitNumber ?: "",
        k3Object = this.inspection.equipmentType,
        inspectionDate = this.inspection.reportDate ?: ""
    )

    val buildingData = IpkBuildingData(
        landArea = findTest("Bangunan - Luas Lahan (m²)"),
        buildingArea = findTest("Bangunan - Luas Bangunan (m²)"),
        buildingHeight = findTest("Bangunan - Tinggi Bangunan (m)"),
        floorCount = findTest("Bangunan - Jumlah Lantai"),
        yearBuilt = findTest("Bangunan - Dibangun Tahun"),
        construction = IpkConstruction(
            mainStructure = findTest("Konstruksi - Struktur Utama"),
            floorStructure = findTest("Konstruksi - Struktur Lantai"),
            exteriorWalls = findTest("Konstruksi - Dinding Luar"),
            interiorWalls = findTest("Konstruksi - Dinding Dalam"),
            ceilingFrame = findTest("Konstruksi - Rangka Plafon"),
            ceilingCover = findTest("Konstruksi - Penutup Plafon"),
            roofFrame = findTest("Konstruksi - Rangka Atap"),
            roofCover = findTest("Konstruksi - Penutup Atap")
        ),
        fireProtectionEquipment = IpkFireProtectionEquipment(
            portableExtinguishers = findBoolTest("Perlengkapan - APAR"),
            indoorHydrantBox = findBoolTest("Perlengkapan - Box Hydrant Indoor"),
            pillarAndOutdoorHydrant = findBoolTest("Perlengkapan - Hydrant Pillar Outdoor"),
            siameseConnection = findBoolTest("Perlengkapan - Siamese Connection"),
            sprinklerSystem = findBoolTest("Perlengkapan - Instalasi Sprinkler"),
            heatAndSmokeDetectors = findBoolTest("Perlengkapan - Detektor Panas & Asap"),
            exitSigns = findBoolTest("Perlengkapan - Petunjuk Arah"),
            emergencyStairs = findBoolTest("Perlengkapan - Tangga Darurat"),
            assemblyPoint = findBoolTest("Perlengkapan - Assembly Point")
        )
    )

    val techSpecs = IpkTechnicalSpecifications(
        mcfa = IpkMcfa(brandOrType = findTest("MCFA - Merk/Type"), result = findTest("MCFA - Hasil"), ledAnnunciator = findTest("MCFA - LED Annunciator"), type = findTest("MCFA - Type"), serialNumber = findTest("MCFA - No. Serial")),
        heatDetector = IpkHeatDetector(brandOrType = findTest("Heat Detector - Merk/Type"), result = findTest("Heat Detector - Hasil"), pointCount = findTest("Heat Detector - Jumlah Titik"), spacing = findTest("Heat Detector - Jarak (m)"), operatingTemperature = findTest("Heat Detector - Suhu Kerja (°C)")),
        smokeDetector = IpkSmokeDetector(brandOrType = findTest("Smoke Detector - Merk/Type"), result = findTest("Smoke Detector - Hasil"), pointCount = findTest("Smoke Detector - Jumlah Titik"), spacing = findTest("Smoke Detector - Jarak (m)"), operatingTemperature = findTest("Smoke Detector - Suhu Kerja (°C)")),
        apar = IpkApar(brandOrType = findTest("APAR - Merk/Type"), result = findTest("APAR - Hasil"), count = findTest("APAR - Jumlah"), spacing = findTest("APAR - Jarak (m)"), placement = findTest("APAR - Penempatan"))
    )

    val alarmInstallationData = this.testResults.filter { it.testName.startsWith("Pemasangan Alarm #") }.map {
        val notes = it.notes?.split('|') ?: emptyList()
        fun getNote(index: Int) = notes.getOrNull(index) ?: ""
        IpkAlarmInstallationData(it.result, getNote(0), getNote(1), getNote(2), getNote(3), getNote(4), getNote(5), getNote(6), getNote(7), getNote(8))
    }

    val alarmTestResults = IpkAlarmTestResults(
        panelFunction = findTest("Uji Alarm - Fungsi Kerja Panel"),
        alarmTest = findTest("Uji Alarm - Test Alarm"),
        faultTest = findTest("Uji Alarm - Test Fault"),
        interconnectionTest = findTest("Uji Alarm - Test Interkoneksi")
    )

    fun findSysComp(name: String) = IpkPipeValveItem(spec = findTest("Hydran - $name - Spec"), status = findTest("Hydran - $name - Status"), remarks = findTest("Hydran - $name - Ket"))

    val hydrantSystem = IpkHydrantSystem(
        waterSource = IpkWaterSource(specification = findTest("Hydran - Sumber Air Baku - Spec"), status = findTest("Hydran - Sumber Air Baku - Status"), note = findTest("Hydran - Sumber Air Baku - Ket")),
        groundReservoir = IpkGroundReservoir(backupSpec = findTest("Hydran - Ground Reservoir - Kapasitas"), backupStatus = findTest("Hydran - Ground Reservoir - Status"), backupResult = findTest("Hydran - Ground Reservoir - Ket")),
        gravitationTank = IpkGravitationTank(spec = findTest("Hydran - Tangki Grafitasi - Spec"), status = findTest("Hydran - Tangki Grafitasi - Status"), result = findTest("Hydran - Tangki Grafitasi - Ket")),
        siameseConnection = IpkSiameseConnection(spec = findTest("Hydran - Siamese Connection - Spec"), status = findTest("Hydran - Siamese Connection - Status"), result = findTest("Hydran - Siamese Connection - Ket")),
        pumps = IpkPumps(
            jockey = IpkJockeyPump(quantity = findTest("Jockey Pump - Q (m³/H)"), headM = findTest("Jockey Pump - H (m)"), autoStart = findTest("Jockey Pump - Auto Start"), autoStop = findTest("Jockey Pump - Auto Stop")),
            electric = IpkElectricPump(quantity = findTest("Electric Pump - Q (Gpm)"), headM = findTest("Electric Pump - H (m)"), autoStart = findTest("Electric Pump - Auto Start"), stop = findTest("Electric Pump - Stop")),
            diesel = IpkDieselPump(quantity = findTest("Diesel Pump - Q (US Gpm)"), headM = findTest("Diesel Pump - H (m)"), autoStart = findTest("Diesel Pump - Auto Start"), stop = findTest("Diesel Pump - Stop"))
        ),
        indoorHydrant = IpkIndoorHydrant(points = findTest("Building Hydrant - Titik"), diameter = findTest("Building Hydrant - Dia Outlet"), hoseLength = findTest("Building Hydrant - Panjang Selang"), placement = findTest("Building Hydrant - Penempatan")),
        landingValve = IpkLandingValve(points = findTest("Landing Valve - Titik"), diameter = findTest("Landing Valve - Dia Outlet"), clutchType = findTest("Landing Valve - Kopling"), placement = findTest("Landing Valve - Penempatan")),
        outdoorHydrant = IpkOutdoorHydrant(points = findTest("Yard Hydrant - Titik"), diameter = findTest("Yard Hydrant - Dia Outlet"), hoseLength = findTest("Yard Hydrant - Panjang Selang"), nozzleDiameter = findTest("Yard Hydrant - Dia Nozzle"), placement = findTest("Yard Hydrant - Penempatan")),
        fireServiceConnection = IpkFireServiceConnection(points = findTest("Fire Service - Titik"), inletDiameter = findTest("Fire Service - Dia Inlet"), outletDiameter = findTest("Fire Service - Dia Outlet"), clutchType = findTest("Fire Service - Kopling"), condition = findTest("Fire Service - Kondisi"), placement = findTest("Fire Service - Penempatan")),
        pipingAndValves = IpkPipingAndValves(
            pressureReliefValve = findSysComp("Pressure Relief Valve"),
            testValve = findSysComp("Test Valve"),
            suctionPipe = findSysComp("Pipa Hisap"),
            mainPipe = findSysComp("Pipa Penyalur Utama"),
            standPipe = findSysComp("Pipa Tegak"),
            hydrantPillar = findSysComp("Hidran Pilar"),
            innerHydrant = findSysComp("Hidran Dalam Gedung"),
            hoseReel = findSysComp("Hose Rell")
        )
    )

    val pumpFunctionTest = this.testResults.filter { it.testName.startsWith("Uji Fungsi Pompa #") }.map {
        val notes = it.notes?.split('|') ?: emptyList()
        IpkPumpFunctionTest(it.result, notes.find { n -> n.startsWith("Start:") }?.removePrefix("Start:") ?: "", notes.find { n -> n.startsWith("Stop:") }?.removePrefix("Stop:") ?: "")
    }

    val hydrantOperationalTest = this.testResults.filter { it.testName.startsWith("Uji Operasional Hydran #") }.map {
        val notes = it.notes?.split('|') ?: emptyList()
        IpkHydrantOperationalTest(it.result, notes.find { n -> n.startsWith("Tekanan:") }?.removePrefix("Tekanan:") ?: "", notes.find { n -> n.startsWith("Pancar:") }?.removePrefix("Pancar:") ?: "", notes.find { n -> n.startsWith("Posisi:") }?.removePrefix("Posisi:") ?: "", notes.find { n -> n.startsWith("Status:") }?.removePrefix("Status:") ?: "", notes.find { n -> n.startsWith("Ket:") }?.removePrefix("Ket:") ?: "")
    }

    val conclusion = this.findings.find { it.type == FindingType.FINDING }?.description ?: ""
    val recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.joinToString("\n") { it.description }

    return IpkReportRequest(
        examinationType = this.inspection.examinationType,
        extraId = this.inspection.id,
        createdAt = this.inspection.createdAt ?: "",
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        equipmentType = this.inspection.equipmentType,
        documentChecklist = documentChecklist,
        generalData = generalData,
        buildingData = buildingData,
        technicalSpecifications = techSpecs,
        resultAlarmInstallation = findTest("Hasil Pemeriksaan Alarm"),
        totalAlarmInstallation = findTest("Total Pemeriksaan Alarm"),
        alarmInstallationData = alarmInstallationData,
        alarmTestResults = alarmTestResults,
        hydrantSystem = hydrantSystem,
        pumpFunctionTest = pumpFunctionTest,
        hydrantOperationalTest = hydrantOperationalTest,
        conclusion = conclusion,
        recommendations = recommendations
    )
}

// =================================================================================================
//                                  Report: DTO -> Domain
// =================================================================================================

fun IpkReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId.toLong()

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id, // ID dari cloud
        moreExtraId = "",
        documentType = this.documentType.toDocumentType() ?: DocumentType.LAPORAN,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.IPK,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Fire_Protection,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.companyLocation,
        permitNumber = this.generalData.certificateNumber,
        reportDate = this.generalData.inspectionDate,
        createdAt = this.createdAt,
        isSynced = true,
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    this.documentChecklist.let {
        val cat = FireProtectionCategory.DOC_CHECKLIST
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Gambar Teknis", status = it.technicalDrawing.available, result = it.technicalDrawing.notes))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Dokumentasi Riksa/Uji Sebelumnya", status = it.previousTestDocumentation.available, result = it.previousTestDocumentation.notes))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Surat Permohonan", status = it.requestLetter.available, result = it.requestLetter.notes))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Dokumen Spesifikasi", status = it.specificationDocument.available, result = it.specificationDocument.notes))
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    fun addTest(name: String, value: String?) { if (!value.isNullOrBlank()) testResults.add(InspectionTestResultDomain(0, inspectionId, name, value, null)) }

    this.buildingData.let {
        addTest("Bangunan - Luas Lahan (m²)", it.landArea); addTest("Bangunan - Luas Bangunan (m²)", it.buildingArea); addTest("Bangunan - Tinggi Bangunan (m)", it.buildingHeight); addTest("Bangunan - Jumlah Lantai", it.floorCount); addTest("Bangunan - Dibangun Tahun", it.yearBuilt)
        it.construction.let { c -> addTest("Konstruksi - Struktur Utama", c.mainStructure); addTest("Konstruksi - Struktur Lantai", c.floorStructure); addTest("Konstruksi - Dinding Luar", c.exteriorWalls); addTest("Konstruksi - Dinding Dalam", c.interiorWalls); addTest("Konstruksi - Rangka Plafon", c.ceilingFrame); addTest("Konstruksi - Penutup Plafon", c.ceilingCover); addTest("Konstruksi - Rangka Atap", c.roofFrame); addTest("Konstruksi - Penutup Atap", c.roofCover) }
        it.fireProtectionEquipment.let { e -> addTest("Perlengkapan - APAR", e.portableExtinguishers); addTest("Perlengkapan - Box Hydrant Indoor", e.indoorHydrantBox); addTest("Perlengkapan - Hydrant Pillar Outdoor", e.pillarAndOutdoorHydrant); addTest("Perlengkapan - Siamese Connection", e.siameseConnection); addTest("Perlengkapan - Instalasi Sprinkler", e.sprinklerSystem); addTest("Perlengkapan - Detektor Panas & Asap", e.heatAndSmokeDetectors); addTest("Perlengkapan - Petunjuk Arah", e.exitSigns); addTest("Perlengkapan - Tangga Darurat", e.emergencyStairs); addTest("Perlengkapan - Assembly Point", e.assemblyPoint) }
    }

    this.technicalSpecifications.let {
        it.mcfa.let { m -> addTest("MCFA - Merk/Type", m.brandOrType); addTest("MCFA - LED Annunciator", m.ledAnnunciator); addTest("MCFA - Type", m.type); addTest("MCFA - No. Serial", m.serialNumber); addTest("MCFA - Hasil", m.result); }
        it.heatDetector.let { d -> addTest("Heat Detector - Merk/Type", d.brandOrType); addTest("Heat Detector - Jumlah Titik", d.pointCount); addTest("Heat Detector - Jarak (m)", d.spacing); addTest("Heat Detector - Suhu Kerja (°C)", d.operatingTemperature); addTest("Heat Detector - Hasil", d.result); }
        it.smokeDetector.let { d -> addTest("Smoke Detector - Merk/Type", d.brandOrType); addTest("Smoke Detector - Jumlah Titik", d.pointCount); addTest("Smoke Detector - Jarak (m)", d.spacing); addTest("Smoke Detector - Suhu Kerja (°C)", d.operatingTemperature); addTest("Smoke Detector - Hasil", d.result); }
        it.apar.let { a -> addTest("APAR - Merk/Type", a.brandOrType); addTest("APAR - Jumlah", a.count); addTest("APAR - Jarak (m)", a.spacing); addTest("APAR - Penempatan", a.placement); addTest("APAR - Hasil", a.result); }
    }

    this.alarmTestResults.let { addTest("Uji Alarm - Fungsi Kerja Panel", it.panelFunction); addTest("Uji Alarm - Test Alarm", it.alarmTest); addTest("Uji Alarm - Test Fault", it.faultTest); addTest("Uji Alarm - Test Interkoneksi", it.interconnectionTest); }

    this.alarmInstallationData.forEachIndexed { i, item ->
        val notes = listOf(item.zone, item.ror, item.fixed, item.smoke, item.tpm, item.flsw, item.bell, item.lamp, item.status).joinToString("|")
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Pemasangan Alarm #${i + 1}", item.location, notes))
    }
    addTest("Total Pemeriksaan Alarm", this.totalAlarmInstallation)
    addTest("Hasil Pemeriksaan Alarm", this.resultAlarmInstallation)

    this.hydrantSystem.let { h ->
        fun addSysComp(name: String, comp: IpkPipeValveItem) { addTest("Hydran - $name - Spec", comp.spec); addTest("Hydran - $name - Status", comp.status); addTest("Hydran - $name - Ket", comp.remarks) }
        addTest("Hydran - Sumber Air Baku - Spec", h.waterSource.specification); addTest("Hydran - Sumber Air Baku - Status", h.waterSource.status); addTest("Hydran - Sumber Air Baku - Ket", h.waterSource.note)
        addTest("Hydran - Ground Reservoir - Kapasitas", h.groundReservoir.backupSpec); addTest("Hydran - Ground Reservoir - Status", h.groundReservoir.backupStatus); addTest("Hydran - Ground Reservoir - Ket", h.groundReservoir.backupResult)
        addTest("Hydran - Tangki Grafitasi - Spec", h.gravitationTank.spec); addTest("Hydran - Tangki Grafitasi - Status", h.gravitationTank.status); addTest("Hydran - Tangki Grafitasi - Ket", h.gravitationTank.result)
        addTest("Hydran - Siamese Connection - Spec", h.siameseConnection.spec); addTest("Hydran - Siamese Connection - Status", h.siameseConnection.status); addTest("Hydran - Siamese Connection - Ket", h.siameseConnection.result)
        h.pumps.jockey.let { p -> addTest("Jockey Pump - Q (m³/H)", p.quantity); addTest("Jockey Pump - H (m)", p.headM); addTest("Jockey Pump - Auto Start", p.autoStart); addTest("Jockey Pump - Auto Stop", p.autoStop) }
        h.pumps.electric.let { p -> addTest("Electric Pump - Q (Gpm)", p.quantity); addTest("Electric Pump - H (m)", p.headM); addTest("Electric Pump - Auto Start", p.autoStart); addTest("Electric Pump - Stop", p.stop) }
        h.pumps.diesel.let { p -> addTest("Diesel Pump - Q (US Gpm)", p.quantity); addTest("Diesel Pump - H (m)", p.headM); addTest("Diesel Pump - Auto Start", p.autoStart); addTest("Diesel Pump - Stop", p.stop) }
        h.indoorHydrant.let { p -> addTest("Building Hydrant - Titik", p.points); addTest("Building Hydrant - Dia Outlet", p.diameter); addTest("Building Hydrant - Panjang Selang", p.hoseLength); addTest("Building Hydrant - Penempatan", p.placement) }
        h.landingValve.let { p -> addTest("Landing Valve - Titik", p.points); addTest("Landing Valve - Dia Outlet", p.diameter); addTest("Landing Valve - Kopling", p.clutchType); addTest("Landing Valve - Penempatan", p.placement) }
        h.outdoorHydrant.let { p -> addTest("Yard Hydrant - Titik", p.points); addTest("Yard Hydrant - Dia Outlet", p.diameter); addTest("Yard Hydrant - Panjang Selang", p.hoseLength); addTest("Yard Hydrant - Dia Nozzle", p.nozzleDiameter); addTest("Yard Hydrant - Penempatan", p.placement) }
        h.fireServiceConnection.let { p -> addTest("Fire Service - Titik", p.points); addTest("Fire Service - Dia Inlet", p.inletDiameter); addTest("Fire Service - Dia Outlet", p.outletDiameter); addTest("Fire Service - Kopling", p.clutchType); addTest("Fire Service - Kondisi", p.condition); addTest("Fire Service - Penempatan", p.placement) }
        h.pipingAndValves.let { pv -> addSysComp("Pressure Relief Valve", pv.pressureReliefValve); addSysComp("Test Valve", pv.testValve); addSysComp("Pipa Hisap", pv.suctionPipe); addSysComp("Pipa Penyalur Utama", pv.mainPipe); addSysComp("Pipa Tegak", pv.standPipe); addSysComp("Hidran Pilar", pv.hydrantPillar); addSysComp("Hidran Dalam Gedung", pv.innerHydrant); addSysComp("Hose Rell", pv.hoseReel) }
    }

    this.pumpFunctionTest.forEachIndexed { i, item ->
        val notes = "Start: ${item.start}|Stop: ${item.stop}"
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Uji Fungsi Pompa #${i + 1}", item.pumpType, notes))
    }
    this.hydrantOperationalTest.forEachIndexed { i, item ->
        val notes = "Tekanan: ${item.pressure}|Pancar: ${item.transmitPower}|Posisi: ${item.nozzlePosition}|Status: ${item.status}|Ket: ${item.description}"
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Uji Operasional Hydran #${i + 1}", item.test, notes))
    }

    val findings = mutableListOf<InspectionFindingDomain>()
    if (this.conclusion.isNotBlank()) {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = this.conclusion, type = FindingType.FINDING))
    }
    this.recommendations.split("\n").forEach {
        if(it.isNotBlank()) findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION))
    }

    return InspectionWithDetailsDomain(inspectionDomain, checkItems, findings, testResults)
}