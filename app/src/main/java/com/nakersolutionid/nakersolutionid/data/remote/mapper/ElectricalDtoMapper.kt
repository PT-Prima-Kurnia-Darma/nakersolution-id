package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.local.utils.toDocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.toInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toSubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBapGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBapTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBapVisualInspection
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBreakerData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalBreakerEquipment
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalDocumentCheckDetail
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalDocumentExamination1
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalDocumentExamination2
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalInitialDocumentCheck
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalSdpFloor
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalSdpFront
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalSdpTerminal
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalSdpTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.electrical.ElectricalVisualInspection
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain

// --- Kategori yang digunakan untuk konsistensi dengan Mapper UI-Domain ---

private object ElectricalCategory {
    const val INITIAL_DOC_VERIFICATION = "Pemeriksaan Dokumen Awal"
    const val DOC_EXAMINATION_PART_1 = "Pemeriksaan Dokumen I"
    const val DOC_EXAMINATION_PART_2 = "Pemeriksaan Dokumen II"
    const val MAIN_TESTING = "Pengujian Utama"
    const val SDP_VISUAL_FRONT = "Pemeriksaan Visual Panel SDP - Tampak Depan"
    const val SDP_VISUAL_INTERNAL = "Pemeriksaan Visual Panel SDP - Tampak Dalam"
    const val SDP_VISUAL_TERMINAL = "Pemeriksaan Visual Panel SDP - Sistem Terminal"
    const val SDP_TESTING = "Pengujian SDP"
}

private object ElectricBAPCategory {
    const val TECHNICAL_DATA = "DATA TEKNIK" // ADDED: For consistency in BAP Test Results
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val VISUAL_INSPECTION_PANEL_ROOM = "$VISUAL_INSPECTION - Kondisi Ruang Panel"
    const val TESTING = "PENGUJIAN"
}


// =================================================================================================
//                                  BAP: Domain -> DTO
// =================================================================================================

/**
 * Mengubah [InspectionWithDetailsDomain] menjadi [ElectricalBapRequest] untuk dikirim ke API.
 */
fun InspectionWithDetailsDomain.toElectricalBapRequest(): ElectricalBapRequest {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    fun findBoolTestResult(testName: String, positiveResult: String): Boolean {
        return this.testResults.find { it.testName == testName }?.result.equals(positiveResult, ignoreCase = true)
    }

    val generalData = ElectricalBapGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = ElectricalBapTechnicalData(
        plnPower = findTestResult("Sumber Listrik (PLN)"),
        generatorPower = findTestResult("Sumber Listrik (Generator)"),
        lightingPower = findTestResult("Penggunaan Daya (Penerangan)"),
        powerLoad = findTestResult("Penggunaan Daya (Tenaga)"),
        serialNumber = this.inspection.serialNumber ?: ""
    )

    val visualInspection = ElectricalBapVisualInspection(
        isRoomClean = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION_PANEL_ROOM, "Ruangan bersih"),
        isRoomClearItems = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION_PANEL_ROOM, "Ruangan tidak untuk menyimpan barang yang tidak perlu"),
        hasSingleLineDiagram = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION, "Tersedia diagram satu garis"),
        hasProtectiveCover = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION, "Panel dan MCB dilengkapi tutup pengaman"),
        isLabelingComplete = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION, "Labeling lengkap"),
        isFireExtinguisherAvailable = findBoolItem(ElectricBAPCategory.VISUAL_INSPECTION, "Tersedia APAR")
    )

    val testing = ElectricalBapTesting(
        isThermographTestOk = findBoolTestResult("Hasil Uji Thermograph", "Baik"),
        areSafetyDevicesFunctional = findBoolTestResult("Peralatan pengaman berfungsi", "Ya"),
        isVoltageBetweenPhasesNormal = findBoolTestResult("Tegangan antar fasa normal", "Ya"),
        isPhaseLoadBalanced = findBoolTestResult("Beban antar fasa seimbang", "Ya")
    )

    return ElectricalBapRequest(
        laporanId = this.inspection.extraId,
        examinationType = this.inspection.examinationType,
        equipmentType = this.inspection.equipmentType,
        inspectionDate = this.inspection.reportDate ?: "",
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        createdAt = this.inspection.createdAt ?: "",
        extraId = this.inspection.id,
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        testing = testing
    )
}

// =================================================================================================
//                                  BAP: DTO -> Domain
// =================================================================================================

/**
 * Mengubah [ElectricalBapReportData] dari API menjadi [InspectionWithDetailsDomain].
 */
fun ElectricalBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id,
        documentType = this.documentType.toDocumentType() ?: DocumentType.BAP,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.ILPP, // Correct default
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Electrical,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.addressUsageLocation,
        serialNumber = this.technicalData.serialNumber,
        // FIXED: The DTO doesn't contain driveType, so it will be null from server.
        // The local value will be preserved until a new value is fetched.
        driveType = null,
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElectricBAPCategory.VISUAL_INSPECTION_PANEL_ROOM, itemName = "Ruangan bersih", status = this.visualInspection.isRoomClean))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElectricBAPCategory.VISUAL_INSPECTION_PANEL_ROOM, itemName = "Ruangan tidak untuk menyimpan barang yang tidak perlu", status = this.visualInspection.isRoomClearItems))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElectricBAPCategory.VISUAL_INSPECTION, itemName = "Tersedia diagram satu garis", status = this.visualInspection.hasSingleLineDiagram))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElectricBAPCategory.VISUAL_INSPECTION, itemName = "Panel dan MCB dilengkapi tutup pengaman", status = this.visualInspection.hasProtectiveCover))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElectricBAPCategory.VISUAL_INSPECTION, itemName = "Labeling lengkap", status = this.visualInspection.isLabelingComplete))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElectricBAPCategory.VISUAL_INSPECTION, itemName = "Tersedia APAR", status = this.visualInspection.isFireExtinguisherAvailable))

    val testResults = mutableListOf<InspectionTestResultDomain>()
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Sumber Listrik (PLN)", result = this.technicalData.plnPower, notes = "kVA"))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Sumber Listrik (Generator)", result = this.technicalData.generatorPower, notes = "kW"))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Penggunaan Daya (Penerangan)", result = this.technicalData.lightingPower, notes = "kVA"))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Penggunaan Daya (Tenaga)", result = this.technicalData.powerLoad, notes = "kVA"))
    // FIXED: The DTO doesn't contain a direct field for this, but to be consistent with BAP UI, we can add it from technical data.
    // However, as the `technicalData` in DTO doesn't have it, we can't map it back from DTO. But we do need to map the tests.
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Hasil Uji Thermograph", result = if(this.testing.isThermographTestOk) "Baik" else "Tidak Baik", notes = ElectricBAPCategory.TESTING))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Peralatan pengaman berfungsi", result = if(this.testing.areSafetyDevicesFunctional) "Ya" else "Tidak", notes = ElectricBAPCategory.TESTING))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Tegangan antar fasa normal", result = if(this.testing.isVoltageBetweenPhasesNormal) "Ya" else "Tidak", notes = ElectricBAPCategory.TESTING))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Beban antar fasa seimbang", result = if(this.testing.isPhaseLoadBalanced) "Ya" else "Tidak", notes = ElectricBAPCategory.TESTING))


    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(), // BAP tidak memiliki findings
        testResults = testResults
    )
}


// =================================================================================================
//                                  Report: Domain -> DTO
// =================================================================================================

/**
 * Mengubah [InspectionWithDetailsDomain] menjadi [ElectricalReportRequest] untuk dikirim ke API.
 */
fun InspectionWithDetailsDomain.toElectricalReportRequest(): ElectricalReportRequest {
    fun findTestResult(name: String) = this.testResults.find { it.testName.equals(name, true) }?.result ?: ""
    fun findCheckItem(cat: String, name: String) = this.checkItems.find { it.category == cat && it.itemName.equals(name, true) }

    fun findTestItemStatus(cat: String, name: String): Boolean {
        return findCheckItem(cat, name)?.status ?: false
    }

    fun findVerificationItem(cat: String, name: String): ElectricalDocumentCheckDetail {
        val item = findCheckItem(cat, name)
        return ElectricalDocumentCheckDetail(isAvailable = item?.status ?: false, result = item?.result ?: "")
    }

    val generalData = ElectricalGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyAddress = this.inspection.ownerAddress ?: "",
        installationType = this.inspection.equipmentType,
        businessField = findTestResult("Bidang Usaha"),
        safetyServiceProvider = findTestResult("PJK3 Pelaksana"),
        ohsExpert = this.inspection.inspectorName ?: "",
        permitNumber = this.inspection.permitNumber ?: "",
        inspectionDate = this.inspection.reportDate ?: "",
        inspectionLocation = this.inspection.usageLocation ?: ""
    )

    val technicalData = ElectricalTechnicalData(
        plnPower = findTestResult("Daya PLN (KVA)"),
        generatorPower = findTestResult("Daya Diesel Generator (KVA)"),
        totalInstalledLoad = findTestResult("Total Pembebanan Terpasang (WATT)"),
        lightingPower = findTestResult("Daya Penerangan (WATT)"),
        powerLoad = findTestResult("Daya Tenaga (WATT)")
    )

    val initialDocCheck = ElectricalInitialDocumentCheck(
        sld = findVerificationItem(ElectricalCategory.INITIAL_DOC_VERIFICATION, "Single Line Diagram (SLD)"),
        layout = findVerificationItem(ElectricalCategory.INITIAL_DOC_VERIFICATION, "Lay Out"),
        permit = findVerificationItem(ElectricalCategory.INITIAL_DOC_VERIFICATION, "Sertifikat Izin Pengesahan Pemakaian"),
        technicianLicense = findVerificationItem(ElectricalCategory.INITIAL_DOC_VERIFICATION, "Lisensi Teknisi")
    )

    val docExam1 = ElectricalDocumentExamination1(
        hasPlanningPermit = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Perencanaaan Memiliki Ijin"),
        hasLocationMap = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Peta Lokasi"),
        hasSld = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Gambar Diagram Garis Tunggal"),
        hasLayout = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Gambar Layout Instalasi"),
        hasWiringDiagram = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Gambar Diagram Pengawatan"),
        hasAreaClassification = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Gambar Area Klarifikasi"),
        hasPanelComponentList = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Daftar Komponen Panel"),
        hasShortCircuitCalc = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Perhitungan Arus Hubung Singkat"),
        hasManualBook = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Buku Manual"),
        hasMaintenanceBook = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Buku Pemeliharaan dan Operasi"),
        hasWarningSigns = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Tanda Peringatan"),
        hasManufacturerCert = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Sertifikasi Pabrik Pembuat"),
        hasTechSpec = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Spesifikasi Teknik Peralatan"),
        hasTechSpecCert = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Spesipikasi Teknik dan Sertifikasi"),
        hasPowerRecap = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Perhitungan Rekapitulasi Daya"),
        hasDailyRecord = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Record Daily"),
        isPanelCoverGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Cover Panel"),
        hasOtherData = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Data Penunjang Lainnya"),
        // FIXED: Map the actual value instead of hardcoding 'false'.
        hasPanelPointCount = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_1, "Perhitungan Jumlah Titik Panel")
    )

    val docExam2 = ElectricalDocumentExamination2(
        isUnitConstructionGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Kontruksi Unit LVMDP / SDP"),
        isPlacementGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Dudukan dan Penempatan"),
        isNameplateVerified = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Verifikasi Plat Nama"),
        isAreaClassificationOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Klasifikasi Area"),
        isProtectionGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Perlindungan Terhadap Kejut Listrik"),
        isRadiationShieldingGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Pengaman dari Radiasi"),
        hasPanelDoorHolder = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Pintu Panel Dilengkapi Penahan"),
        areBoltsAndScrewsSecure = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Semua Baut dan Sekrup Kuat"),
        isBusbarIsolated = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Busbar Terisolasi dengan Kuat"),
        isBusbarClearanceGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Minimal Ruang Main Busbar"),
        isCableInstallationOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Pemasangan Kabel"),
        isDoorCableProtected = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Kabel Pada Pintu Panel Terlindungi"),
        isFuseChangeSafe = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Penggantian Sekring Aman"),
        hasCableTerminalProtection = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Terminal Kabel Dilengkapi Pelindung"),
        areInstrumentsMarked = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Instrumen Pengukur Diberi Tanda"),
        areEquipmentsCoded = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Peralatan & Terminal Diberi Kode"),
        isCableInOutGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Pemasangan Kabel Masuk dan Keluar"),
        isBusbarSizeGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Ukuran Busbar"),
        isBusbarClean = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Kebersihan Busbar"),
        isBusbarMarkingOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Penandaan Busbar (Phase)"),
        isGroundingCableGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Pemasangan Kabel Pembumian"),
        isPanelDoorGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Pemasangan Pintu Panel"),
        areSparepartsOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Suku Cadang Sesuai Spesifikasi"),
        areSafetyFacilitiesGood = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Fasilitas Keselamatan & Tanda Bahaya"),
        breakerData = ElectricalBreakerData(
            isCurrentRatingOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Rating Arus CB"),
            isVoltageRatingOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Rating Tegangan CB"),
            isBreakingCurrentOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Rating Arus Pemutusan CB"),
            isControlVoltageOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Tegangan Kontrol CB"),
            isManufacturerOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Pabrik Pembuat CB"),
            isTypeOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "Tipe CB"),
            isSerialOk = findTestItemStatus(ElectricalCategory.DOC_EXAMINATION_PART_2, "No. Seri CB")
        )
    )

    val testing = ElectricalTesting(
        isInsulationOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Pengujian Tahanan Isolasi"),
        isGroundingOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Pengukuran Tahanan Pentanahan"),
        isOverloadTripOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Uji Trip Tegangan Jatuh (Over Load)"),
        isReversePowerRelayOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Uji Relay Daya Balik"),
        isReverseCurrentRelayOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Uji Relay Arus Balik"),
        isBreakerTripOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Uji Trip Pemutus Daya"),
        isTemperatureOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Pengukuran Temperatur"),
        isIndicatorLampOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Uji fungsi Lampu Indikator"),
        isMeterDeviationOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Uji Kesalahan / Penyimpangan Meter"),
        isSynchronizationOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Uji Fungsi Sinkronisasi"),
        isConductorAmpacityOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "KHA Penghantar"),
        isProtectionRatingOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Rating Proteksi"),
        isVoltageDropOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Susut Tegangan (Drop Voltage)"),
        hasLossConnection = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Loss Connection"),
        breakerEquipment = ElectricalBreakerEquipment(
            isCtOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Trafo Arus (CT)"),
            isPtOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Trafo Tegangan (PT)"),
            isInstrumentsOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Instrumen / Meter Pengukur"),
            isFuseRatingOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Rating Sekring"),
            isMechanicalOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Pemutus Daya Mekanikal"),
            isTerminalOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Terminal Kabel"),
            isTerminalMarkingOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Penandaan Terminal"),
            isInterlockSystemOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "System Interlock"),
            isAuxSwitchOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Saklar Bantu"),
            isMechanicalTripOk = findTestItemStatus(ElectricalCategory.MAIN_TESTING, "Kerja Trip Mekanis")
        )
    )

    val sdpFront = ElectricalSdpFront(
        areIndicatorLampsGood = findTestItemStatus(ElectricalCategory.SDP_VISUAL_FRONT, "Lampu Indikator Pada Panel"),
        isDoorClearanceGood = findTestItemStatus(ElectricalCategory.SDP_VISUAL_FRONT, "Bebas Buka Pintu Panel"),
        isLightingGood = findTestItemStatus(ElectricalCategory.SDP_VISUAL_FRONT, "Pencahayaan"),
        isProductionLightingGood = findTestItemStatus(ElectricalCategory.SDP_VISUAL_FRONT, "Pencahayaan Ruang Produksi"),
        isOfficeLightingGood = findTestItemStatus(ElectricalCategory.SDP_VISUAL_FRONT, "Pencahayaan Kantor"),
        isMainPanelLightingGood = findTestItemStatus(ElectricalCategory.SDP_VISUAL_FRONT, "Pencahayaan Panel Utama"),
        isWarehouseLightingGood = findTestItemStatus(ElectricalCategory.SDP_VISUAL_FRONT, "Pencahayaan Gudang"),
        isAreaClearOfUnusedItems = findTestItemStatus(ElectricalCategory.SDP_VISUAL_FRONT, "Barang-Barang Tidak Terpakai"),
        hasVentilationAndSigns = findTestItemStatus(ElectricalCategory.SDP_VISUAL_FRONT, "Tanda Bahaya Pintu Panel")
    )

    val sdpFloors = this.checkItems
        .filter { it.category == ElectricalCategory.SDP_VISUAL_INTERNAL }
        .groupBy { it.itemName.substringBefore(":").replace("Lantai ", "").toIntOrNull() }
        .mapNotNull { (floor, items) ->
            if (floor == null) return@mapNotNull null
            val prefix = "Lantai $floor: "
            ElectricalSdpFloor(
                floorNumber = floor,
                hasCover = findTestItemStatus(ElectricalCategory.SDP_VISUAL_INTERNAL, "${prefix}Cover Pelindung Tegangan Sentuh"),
                hasSld = findTestItemStatus(ElectricalCategory.SDP_VISUAL_INTERNAL, "${prefix}Gambar SLD & Kartu Perawatan"),
                hasBonding = findTestItemStatus(ElectricalCategory.SDP_VISUAL_INTERNAL, "${prefix}Kabel Bonding"),
                hasLabeling = findTestItemStatus(ElectricalCategory.SDP_VISUAL_INTERNAL, "${prefix}Labeling"),
                isColorCodeOk = findTestItemStatus(ElectricalCategory.SDP_VISUAL_INTERNAL, "${prefix}Kode Warna Kabel"),
                isClean = findTestItemStatus(ElectricalCategory.SDP_VISUAL_INTERNAL, "${prefix}Kebersihan Panel"),
                isNeat = findTestItemStatus(ElectricalCategory.SDP_VISUAL_INTERNAL, "${prefix}Kerapihan Instalasi")
            )
        }

    val sdpTerminal = ElectricalSdpTerminal(
        isBusbarOk = findTestItemStatus(ElectricalCategory.SDP_VISUAL_TERMINAL, "Busbar / Penghantar"),
        isBreakerOk = findTestItemStatus(ElectricalCategory.SDP_VISUAL_TERMINAL, "Pengaman (CB / FUSE)"),
        areCableLugsOk = findTestItemStatus(ElectricalCategory.SDP_VISUAL_TERMINAL, "Sepatu Kabel"),
        isGroundingSystemOk = findTestItemStatus(ElectricalCategory.SDP_VISUAL_TERMINAL, "Sistem Pembumian"),
        isBusbarDistanceOk = findTestItemStatus(ElectricalCategory.SDP_VISUAL_TERMINAL, "Jarak Busbar to Busbar")
    )

    val sdpTesting = ElectricalSdpTesting(
        isVoltageOk = findTestItemStatus(ElectricalCategory.SDP_TESTING, "Tegangan Phasa R, S, T, N"),
        isCurrentOk = findTestItemStatus(ElectricalCategory.SDP_TESTING, "Arus Phase R, S, T, N"),
        isMeteringOk = findTestItemStatus(ElectricalCategory.SDP_TESTING, "Fungsi Alat Ukur"),
        hasPanelLabel = findTestItemStatus(ElectricalCategory.SDP_TESTING, "Label Panel"),
        hasWarningSign = findTestItemStatus(ElectricalCategory.SDP_TESTING, "Tanda Bahaya Pada Pintu Panel"),
        hasSwitchAndLock = findTestItemStatus(ElectricalCategory.SDP_TESTING, "Selector Switch dan Kunci Pintu"),
        isTerminalHeatTested = findTestItemStatus(ElectricalCategory.SDP_TESTING, "Panas Penghantar Terminal"),
        isGroundingTested = findTestItemStatus(ElectricalCategory.SDP_TESTING, "Pertanahan"),
        isConductorAmpacityOk = findTestItemStatus(ElectricalCategory.SDP_TESTING, "KHA Penghantar Utama"),
        isProtectionRatingOk = findTestItemStatus(ElectricalCategory.SDP_TESTING, "Rating Proteksi Utama")
    )

    val visualInspection = ElectricalVisualInspection(sdpFront, sdpFloors, sdpTerminal, sdpTesting)

    // FIXED: Map domain's FINDING to DTO's 'found' and SUMMARY to DTO's 'conclusion'
    val allFindings = this.findings.filter { it.type == FindingType.FINDING }.joinToString("\n") { it.description }
    val allSummary = this.findings.filter { it.type == FindingType.SUMMARY }.joinToString("\n") { it.description }
    val allRecommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.joinToString("\n") { it.description }


    return ElectricalReportRequest(
        examinationType = this.inspection.examinationType,
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        extraId = this.inspection.id,
        equipmentType = this.inspection.equipmentType,
        createdAt = this.inspection.createdAt ?: "",
        generalData = generalData,
        initialDocumentCheck = initialDocCheck,
        technicalData = technicalData,
        documentExamination1 = docExam1,
        documentExamination2 = docExam2,
        testing = testing,
        visualInspection = visualInspection,
        found = allFindings,
        conclusion = allSummary, // FIXED
        recommendations = allRecommendations
    )
}


// =================================================================================================
//                                  Report: DTO -> Domain
// =================================================================================================

/**
 * Mengubah [ElectricalReportData] dari API menjadi [InspectionWithDetailsDomain].
 */
fun ElectricalReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id, // ID dari cloud
        moreExtraId = "", // Tidak ada data ini dari response report
        documentType = this.documentType.toDocumentType() ?: DocumentType.LAPORAN,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.ILPP,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Electrical,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyAddress,
        usageLocation = this.generalData.inspectionLocation,
        addressUsageLocation = this.generalData.companyAddress,
        permitNumber = this.generalData.permitNumber,
        inspectorName = this.generalData.ohsExpert,
        reportDate = this.generalData.inspectionDate,
        createdAt = this.createdAt,
        isSynced = true,
        // The DTO for Report also doesn't contain a direct `driveType` or `currentVoltageType`
        // We will retrieve it from test results if available.
        driveType = null
    )

    fun ElectricalDocumentCheckDetail.toCheckItem(cat: String, name: String) =
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = name, status = this.isAvailable, result = this.result)

    fun Boolean.toCheckItem(cat: String, name: String) =
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = name, status = this, result = null)

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    // Initial Document Verification
    this.initialDocumentCheck.let { data ->
        val cat = ElectricalCategory.INITIAL_DOC_VERIFICATION
        checkItems.add(data.sld.toCheckItem(cat, "Single Line Diagram (SLD)"))
        checkItems.add(data.layout.toCheckItem(cat, "Lay Out"))
        checkItems.add(data.permit.toCheckItem(cat, "Sertifikat Izin Pengesahan Pemakaian"))
        checkItems.add(data.technicianLicense.toCheckItem(cat, "Lisensi Teknisi"))
    }

    // Document Examination 1
    this.documentExamination1.let { data ->
        val cat = ElectricalCategory.DOC_EXAMINATION_PART_1
        checkItems.add(data.hasPlanningPermit.toCheckItem(cat, "Perencanaaan Memiliki Ijin"))
        checkItems.add(data.hasLocationMap.toCheckItem(cat, "Peta Lokasi"))
        checkItems.add(data.hasSld.toCheckItem(cat, "Gambar Diagram Garis Tunggal"))
        checkItems.add(data.hasLayout.toCheckItem(cat, "Gambar Layout Instalasi"))
        checkItems.add(data.hasWiringDiagram.toCheckItem(cat, "Gambar Diagram Pengawatan"))
        checkItems.add(data.hasAreaClassification.toCheckItem(cat, "Gambar Area Klarifikasi"))
        checkItems.add(data.hasPanelComponentList.toCheckItem(cat, "Daftar Komponen Panel"))
        checkItems.add(data.hasShortCircuitCalc.toCheckItem(cat, "Perhitungan Arus Hubung Singkat"))
        checkItems.add(data.hasManualBook.toCheckItem(cat, "Buku Manual"))
        checkItems.add(data.hasMaintenanceBook.toCheckItem(cat, "Buku Pemeliharaan dan Operasi"))
        checkItems.add(data.hasWarningSigns.toCheckItem(cat, "Tanda Peringatan"))
        checkItems.add(data.hasManufacturerCert.toCheckItem(cat, "Sertifikasi Pabrik Pembuat"))
        checkItems.add(data.hasTechSpec.toCheckItem(cat, "Spesifikasi Teknik Peralatan"))
        checkItems.add(data.hasTechSpecCert.toCheckItem(cat, "Spesipikasi Teknik dan Sertifikasi"))
        checkItems.add(data.hasPowerRecap.toCheckItem(cat, "Perhitungan Rekapitulasi Daya"))
        checkItems.add(data.hasDailyRecord.toCheckItem(cat, "Record Daily"))
        checkItems.add(data.isPanelCoverGood.toCheckItem(cat, "Cover Panel"))
        checkItems.add(data.hasOtherData.toCheckItem(cat, "Data Penunjang Lainnya"))
        // FIXED: Map the DTO field back to a domain check item.
        checkItems.add(data.hasPanelPointCount.toCheckItem(cat, "Perhitungan Jumlah Titik Panel"))
    }

    // Document Examination 2
    this.documentExamination2.let { data ->
        val cat = ElectricalCategory.DOC_EXAMINATION_PART_2
        checkItems.add(data.isUnitConstructionGood.toCheckItem(cat, "Kontruksi Unit LVMDP / SDP"))
        checkItems.add(data.isPlacementGood.toCheckItem(cat, "Dudukan dan Penempatan"))
        checkItems.add(data.isNameplateVerified.toCheckItem(cat, "Verifikasi Plat Nama"))
        checkItems.add(data.isAreaClassificationOk.toCheckItem(cat, "Klasifikasi Area"))
        checkItems.add(data.isProtectionGood.toCheckItem(cat, "Perlindungan Terhadap Kejut Listrik"))
        checkItems.add(data.isRadiationShieldingGood.toCheckItem(cat, "Pengaman dari Radiasi"))
        checkItems.add(data.hasPanelDoorHolder.toCheckItem(cat, "Pintu Panel Dilengkapi Penahan"))
        checkItems.add(data.areBoltsAndScrewsSecure.toCheckItem(cat, "Semua Baut dan Sekrup Kuat"))
        checkItems.add(data.isBusbarIsolated.toCheckItem(cat, "Busbar Terisolasi dengan Kuat"))
        checkItems.add(data.isBusbarClearanceGood.toCheckItem(cat, "Minimal Ruang Main Busbar"))
        checkItems.add(data.isCableInstallationOk.toCheckItem(cat, "Pemasangan Kabel"))
        checkItems.add(data.isDoorCableProtected.toCheckItem(cat, "Kabel Pada Pintu Panel Terlindungi"))
        checkItems.add(data.isFuseChangeSafe.toCheckItem(cat, "Penggantian Sekring Aman"))
        checkItems.add(data.hasCableTerminalProtection.toCheckItem(cat, "Terminal Kabel Dilengkapi Pelindung"))
        checkItems.add(data.areInstrumentsMarked.toCheckItem(cat, "Instrumen Pengukur Diberi Tanda"))
        checkItems.add(data.areEquipmentsCoded.toCheckItem(cat, "Peralatan & Terminal Diberi Kode"))
        checkItems.add(data.isCableInOutGood.toCheckItem(cat, "Pemasangan Kabel Masuk dan Keluar"))
        checkItems.add(data.isBusbarSizeGood.toCheckItem(cat, "Ukuran Busbar"))
        checkItems.add(data.isBusbarClean.toCheckItem(cat, "Kebersihan Busbar"))
        checkItems.add(data.isBusbarMarkingOk.toCheckItem(cat, "Penandaan Busbar (Phase)"))
        checkItems.add(data.isGroundingCableGood.toCheckItem(cat, "Pemasangan Kabel Pembumian"))
        checkItems.add(data.isPanelDoorGood.toCheckItem(cat, "Pemasangan Pintu Panel"))
        checkItems.add(data.areSparepartsOk.toCheckItem(cat, "Suku Cadang Sesuai Spesifikasi"))
        checkItems.add(data.areSafetyFacilitiesGood.toCheckItem(cat, "Fasilitas Keselamatan & Tanda Bahaya"))
        // Breaker Data
        checkItems.add(data.breakerData.isCurrentRatingOk.toCheckItem(cat, "Rating Arus CB"))
        checkItems.add(data.breakerData.isVoltageRatingOk.toCheckItem(cat, "Rating Tegangan CB"))
        checkItems.add(data.breakerData.isBreakingCurrentOk.toCheckItem(cat, "Rating Arus Pemutusan CB"))
        checkItems.add(data.breakerData.isControlVoltageOk.toCheckItem(cat, "Tegangan Kontrol CB"))
        checkItems.add(data.breakerData.isManufacturerOk.toCheckItem(cat, "Pabrik Pembuat CB"))
        checkItems.add(data.breakerData.isTypeOk.toCheckItem(cat, "Tipe CB"))
        checkItems.add(data.breakerData.isSerialOk.toCheckItem(cat, "No. Seri CB"))
    }

    // Main Testing
    this.testing.let { data ->
        val cat = ElectricalCategory.MAIN_TESTING
        checkItems.add(data.isInsulationOk.toCheckItem(cat, "Pengujian Tahanan Isolasi"))
        checkItems.add(data.isGroundingOk.toCheckItem(cat, "Pengukuran Tahanan Pentanahan"))
        checkItems.add(data.isOverloadTripOk.toCheckItem(cat, "Uji Trip Tegangan Jatuh (Over Load)"))
        checkItems.add(data.isReversePowerRelayOk.toCheckItem(cat, "Uji Relay Daya Balik"))
        checkItems.add(data.isReverseCurrentRelayOk.toCheckItem(cat, "Uji Relay Arus Balik"))
        checkItems.add(data.isBreakerTripOk.toCheckItem(cat, "Uji Trip Pemutus Daya"))
        checkItems.add(data.isTemperatureOk.toCheckItem(cat, "Pengukuran Temperatur"))
        checkItems.add(data.isIndicatorLampOk.toCheckItem(cat, "Uji fungsi Lampu Indikator"))
        checkItems.add(data.isMeterDeviationOk.toCheckItem(cat, "Uji Kesalahan / Penyimpangan Meter"))
        checkItems.add(data.isSynchronizationOk.toCheckItem(cat, "Uji Fungsi Sinkronisasi"))
        checkItems.add(data.isConductorAmpacityOk.toCheckItem(cat, "KHA Penghantar"))
        checkItems.add(data.isProtectionRatingOk.toCheckItem(cat, "Rating Proteksi"))
        checkItems.add(data.isVoltageDropOk.toCheckItem(cat, "Susut Tegangan (Drop Voltage)"))
        checkItems.add(data.hasLossConnection.toCheckItem(cat, "Loss Connection"))
        // Breaker Equipment
        checkItems.add(data.breakerEquipment.isCtOk.toCheckItem(cat, "Trafo Arus (CT)"))
        checkItems.add(data.breakerEquipment.isPtOk.toCheckItem(cat, "Trafo Tegangan (PT)"))
        checkItems.add(data.breakerEquipment.isInstrumentsOk.toCheckItem(cat, "Instrumen / Meter Pengukur"))
        checkItems.add(data.breakerEquipment.isFuseRatingOk.toCheckItem(cat, "Rating Sekring"))
        checkItems.add(data.breakerEquipment.isMechanicalOk.toCheckItem(cat, "Pemutus Daya Mekanikal"))
        checkItems.add(data.breakerEquipment.isTerminalOk.toCheckItem(cat, "Terminal Kabel"))
        checkItems.add(data.breakerEquipment.isTerminalMarkingOk.toCheckItem(cat, "Penandaan Terminal"))
        checkItems.add(data.breakerEquipment.isInterlockSystemOk.toCheckItem(cat, "System Interlock"))
        checkItems.add(data.breakerEquipment.isAuxSwitchOk.toCheckItem(cat, "Saklar Bantu"))
        checkItems.add(data.breakerEquipment.isMechanicalTripOk.toCheckItem(cat, "Kerja Trip Mekanis"))
    }

    // Visual Inspection
    this.visualInspection.let { data ->
        // SDP Front
        val catFront = ElectricalCategory.SDP_VISUAL_FRONT
        checkItems.add(data.sdpFront.areIndicatorLampsGood.toCheckItem(catFront, "Lampu Indikator Pada Panel"))
        checkItems.add(data.sdpFront.isDoorClearanceGood.toCheckItem(catFront, "Bebas Buka Pintu Panel"))
        checkItems.add(data.sdpFront.isLightingGood.toCheckItem(catFront, "Pencahayaan"))
        checkItems.add(data.sdpFront.isProductionLightingGood.toCheckItem(catFront, "Pencahayaan Ruang Produksi"))
        checkItems.add(data.sdpFront.isOfficeLightingGood.toCheckItem(catFront, "Pencahayaan Kantor"))
        checkItems.add(data.sdpFront.isMainPanelLightingGood.toCheckItem(catFront, "Pencahayaan Panel Utama"))
        checkItems.add(data.sdpFront.isWarehouseLightingGood.toCheckItem(catFront, "Pencahayaan Gudang"))
        checkItems.add(data.sdpFront.isAreaClearOfUnusedItems.toCheckItem(catFront, "Barang-Barang Tidak Terpakai"))
        checkItems.add(data.sdpFront.hasVentilationAndSigns.toCheckItem(catFront, "Tanda Bahaya Pintu Panel"))

        // SDP Internal (Floors)
        val catInternal = ElectricalCategory.SDP_VISUAL_INTERNAL
        data.sdpFloors.forEach { floorData ->
            val prefix = "Lantai ${floorData.floorNumber}: "
            checkItems.add(floorData.hasCover.toCheckItem(catInternal, "${prefix}Cover Pelindung Tegangan Sentuh"))
            checkItems.add(floorData.hasSld.toCheckItem(catInternal, "${prefix}Gambar SLD & Kartu Perawatan"))
            checkItems.add(floorData.hasBonding.toCheckItem(catInternal, "${prefix}Kabel Bonding"))
            checkItems.add(floorData.hasLabeling.toCheckItem(catInternal, "${prefix}Labeling"))
            checkItems.add(floorData.isColorCodeOk.toCheckItem(catInternal, "${prefix}Kode Warna Kabel"))
            checkItems.add(floorData.isClean.toCheckItem(catInternal, "${prefix}Kebersihan Panel"))
            checkItems.add(floorData.isNeat.toCheckItem(catInternal, "${prefix}Kerapihan Instalasi"))
        }

        // SDP Terminal
        val catTerminal = ElectricalCategory.SDP_VISUAL_TERMINAL
        checkItems.add(data.sdpTerminal.isBusbarOk.toCheckItem(catTerminal, "Busbar / Penghantar"))
        checkItems.add(data.sdpTerminal.isBreakerOk.toCheckItem(catTerminal, "Pengaman (CB / FUSE)"))
        checkItems.add(data.sdpTerminal.areCableLugsOk.toCheckItem(catTerminal, "Sepatu Kabel"))
        checkItems.add(data.sdpTerminal.isGroundingSystemOk.toCheckItem(catTerminal, "Sistem Pembumian"))
        checkItems.add(data.sdpTerminal.isBusbarDistanceOk.toCheckItem(catTerminal, "Jarak Busbar to Busbar"))

        // SDP Testing
        val catSdpTest = ElectricalCategory.SDP_TESTING
        checkItems.add(data.sdpTesting.isVoltageOk.toCheckItem(catSdpTest, "Tegangan Phasa R, S, T, N"))
        checkItems.add(data.sdpTesting.isCurrentOk.toCheckItem(catSdpTest, "Arus Phase R, S, T, N"))
        checkItems.add(data.sdpTesting.isMeteringOk.toCheckItem(catSdpTest, "Fungsi Alat Ukur"))
        checkItems.add(data.sdpTesting.hasPanelLabel.toCheckItem(catSdpTest, "Label Panel"))
        checkItems.add(data.sdpTesting.hasWarningSign.toCheckItem(catSdpTest, "Tanda Bahaya Pada Pintu Panel"))
        checkItems.add(data.sdpTesting.hasSwitchAndLock.toCheckItem(catSdpTest, "Selector Switch dan Kunci Pintu"))
        checkItems.add(data.sdpTesting.isTerminalHeatTested.toCheckItem(catSdpTest, "Panas Penghantar Terminal"))
        checkItems.add(data.sdpTesting.isGroundingTested.toCheckItem(catSdpTest, "Pertanahan"))
        checkItems.add(data.sdpTesting.isConductorAmpacityOk.toCheckItem(catSdpTest, "KHA Penghantar Utama"))
        checkItems.add(data.sdpTesting.isProtectionRatingOk.toCheckItem(catSdpTest, "Rating Proteksi Utama"))
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    this.technicalData.let {
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Daya PLN (KVA)", it.plnPower, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Daya Diesel Generator (KVA)", it.generatorPower, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Total Pembebanan Terpasang (WATT)", it.totalInstalledLoad, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Daya Penerangan (WATT)", it.lightingPower, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Daya Tenaga (WATT)", it.powerLoad, null))
    }
    this.generalData.let {
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Bidang Usaha", it.businessField, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "PJK3 Pelaksana", it.safetyServiceProvider, null))
    }
    // Attempt to find current/voltage type from test results if it was saved there previously.
    // This is for backward compatibility if old data saved it this way.
    val finalDomain = inspectionDomain.copy(
        driveType = testResults.find { it.testName.equals("Jenis Arus / Tegangan", true) }?.result
    )

    val findings = mutableListOf<InspectionFindingDomain>()
    // FIXED: Map DTO's 'found' to FINDING and 'conclusion' to SUMMARY
    this.found.split("\n").forEach { if (it.isNotBlank()) findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING)) }
    this.conclusion.split("\n").forEach { if (it.isNotBlank()) findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.SUMMARY)) }
    this.recommendations.split("\n").forEach { if (it.isNotBlank()) findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION)) }


    return InspectionWithDetailsDomain(finalDomain, checkItems, findings, testResults)
}