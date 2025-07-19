package com.nakersolutionid.nakersolutionid.features.report.ilpp.electric

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

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun ElectricalUiState.toInspectionWithDetailsDomain(currentTime: String, reportId: Long? = null): InspectionWithDetailsDomain {
    val report = this.electricalInspectionReport
    val generalData = report.generalData
    val technicalData = report.technicalData
    val inspectionId: Long = reportId ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = "",
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.ILPP,
        subInspectionType = SubInspectionType.Electrical,
        equipmentType = generalData.installationType,
        examinationType = generalData.examinationType,
        driveType = technicalData.currentVoltageType,
        ownerName = generalData.companyName,
        ownerAddress = generalData.companyAddress,
        usageLocation = generalData.inspectionLocation,
        addressUsageLocation = generalData.companyAddress,
        permitNumber = generalData.permitNumber,
        inspectorName = generalData.ohsExpert,
        reportDate = generalData.inspectionDate,
        createdAt = currentTime,
        isSynced = false
    )

    val checkItems = createCheckItemsFromUiState(report, inspectionId)

    val findings = mutableListOf<InspectionFindingDomain>()
    report.conclusion.findings.forEach { findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING)) }
    report.conclusion.summary.forEach { findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING)) }
    report.conclusion.recommendations.forEach { findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION)) }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Daya PLN (KVA)", technicalData.powerSource.plnKva, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Daya Diesel Generator (KVA)", technicalData.powerSource.dieselGeneratorKva, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Total Pembebanan Terpasang (WATT)", technicalData.loadSystem.totalInstalledLoadWatt, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Daya Penerangan (WATT)", technicalData.loadSystem.lightingPowerWatt, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Daya Tenaga (WATT)", technicalData.loadSystem.powerLoadWatt, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Jenis Arus / Tegangan", technicalData.currentVoltageType, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Bidang Usaha", generalData.businessField, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "PJK3 Pelaksana", generalData.safetyServiceProvider, null))

    return InspectionWithDetailsDomain(inspectionDomain, checkItems, findings, testResults)
}

private fun createCheckItemsFromUiState(report: ElectricalInspectionReport, inspectionId: Long): List<InspectionCheckItemDomain> {
    val items = mutableListOf<InspectionCheckItemDomain>()
    val testing = report.inspectionAndTesting

    report.initialDocumentVerification.let { data ->
        val cat = ElectricalCategory.INITIAL_DOC_VERIFICATION
        items.add(data.singleLineDiagram.toCheckItem(inspectionId, cat, "Single Line Diagram (SLD)"))
        items.add(data.layout.toCheckItem(inspectionId, cat, "Lay Out"))
        items.add(data.usagePermitCertificate.toCheckItem(inspectionId, cat, "Sertifikat Izin Pengesahan Pemakaian"))
        items.add(data.technicianLicense.toCheckItem(inspectionId, cat, "Lisensi Teknisi"))
    }

    testing.documentExaminationPart1.let { data ->
        val cat = ElectricalCategory.DOC_EXAMINATION_PART_1
        items.add(data.planningHasPermit.toCheckItem(inspectionId, cat, "Perencanaaan Memiliki Ijin"))
        items.add(data.locationMapExists.toCheckItem(inspectionId, cat, "Peta Lokasi"))
        items.add(data.singleLineDiagramExists.toCheckItem(inspectionId, cat, "Gambar Diagram Garis Tunggal"))
        items.add(data.layoutDiagramExists.toCheckItem(inspectionId, cat, "Gambar Layout Instalasi"))
        items.add(data.wiringDiagramExists.toCheckItem(inspectionId, cat, "Gambar Diagram Pengawatan"))
        items.add(data.areaClarificationDrawingExists.toCheckItem(inspectionId, cat, "Gambar Area Klarifikasi"))
        items.add(data.panelComponentListExists.toCheckItem(inspectionId, cat, "Daftar Komponen Panel"))
        items.add(data.shortCircuitCalculationExists.toCheckItem(inspectionId, cat, "Perhitungan Arus Hubung Singkat"))
        items.add(data.manualBookExists.toCheckItem(inspectionId, cat, "Buku Manual"))
        items.add(data.maintenanceAndOperationBookExists.toCheckItem(inspectionId, cat, "Buku Pemeliharaan dan Operasi"))
        items.add(data.warningSignsInstalled.toCheckItem(inspectionId, cat, "Tanda Peringatan"))
        items.add(data.manufacturerCertificationExists.toCheckItem(inspectionId, cat, "Sertifikasi Pabrik Pembuat"))
        items.add(data.equipmentTechnicalSpecsExists.toCheckItem(inspectionId, cat, "Spesifikasi Teknik Peralatan"))
        items.add(data.equipmentCertificationAndSpecsExists.toCheckItem(inspectionId, cat, "Spesipikasi Teknik dan Sertifikasi"))
        items.add(data.powerRecapitulationCalculationExists.toCheckItem(inspectionId, cat, "Perhitungan Rekapitulasi Daya"))
        items.add(data.dailyRecordExists.toCheckItem(inspectionId, cat, "Record Daily"))
        items.add(data.panelCoverCondition.toCheckItem(inspectionId, cat, "Cover Panel"))
        items.add(data.otherSupportingDataExists.toCheckItem(inspectionId, cat, "Data Penunjang Lainnya"))
    }

    testing.documentExaminationPart2.let { data ->
        val cat = ElectricalCategory.DOC_EXAMINATION_PART_2
        items.add(data.unitConstructionLvmdpSdp.toCheckItem(inspectionId, cat, "Kontruksi Unit LVMDP / SDP"))
        items.add(data.mountingAndPlacement.toCheckItem(inspectionId, cat, "Dudukan dan Penempatan"))
        items.add(data.nameplateVerification.toCheckItem(inspectionId, cat, "Verifikasi Plat Nama"))
        items.add(data.areaClassification.toCheckItem(inspectionId, cat, "Klasifikasi Area"))
        items.add(data.protectionAgainstElectricShock.toCheckItem(inspectionId, cat, "Perlindungan Terhadap Kejut Listrik"))
        items.add(data.radiationProtection.toCheckItem(inspectionId, cat, "Pengaman dari Radiasi"))
        items.add(data.panelDoorStaysOpen.toCheckItem(inspectionId, cat, "Pintu Panel Dilengkapi Penahan"))
        items.add(data.boltsAndScrewsTightened.toCheckItem(inspectionId, cat, "Semua Baut dan Sekrup Kuat"))
        items.add(data.busbarInsulation.toCheckItem(inspectionId, cat, "Busbar Terisolasi dengan Kuat"))
        items.add(data.busbarClearance.toCheckItem(inspectionId, cat, "Minimal Ruang Main Busbar"))
        items.add(data.cableInstallation.toCheckItem(inspectionId, cat, "Pemasangan Kabel"))
        items.add(data.panelDoorCableProtection.toCheckItem(inspectionId, cat, "Kabel Pada Pintu Panel Terlindungi"))
        items.add(data.fuseReplacementSafety.toCheckItem(inspectionId, cat, "Penggantian Sekring Aman"))
        items.add(data.cableTerminalProtection.toCheckItem(inspectionId, cat, "Terminal Kabel Dilengkapi Pelindung"))
        items.add(data.measuringInstrumentsMarking.toCheckItem(inspectionId, cat, "Instrumen Pengukur Diberi Tanda"))
        items.add(data.equipmentAndTerminalLabeling.toCheckItem(inspectionId, cat, "Peralatan & Terminal Diberi Kode"))
        items.add(data.incomingOutgoingCableInstallation.toCheckItem(inspectionId, cat, "Pemasangan Kabel Masuk dan Keluar"))
        items.add(data.busbarSize.toCheckItem(inspectionId, cat, "Ukuran Busbar"))
        items.add(data.busbarCleanliness.toCheckItem(inspectionId, cat, "Kebersihan Busbar"))
        items.add(data.busbarPhaseMarking.toCheckItem(inspectionId, cat, "Penandaan Busbar (Phase)"))
        items.add(data.groundingCableInstallation.toCheckItem(inspectionId, cat, "Pemasangan Kabel Pembumian"))
        items.add(data.panelDoorInstallation.toCheckItem(inspectionId, cat, "Pemasangan Pintu Panel"))
        items.add(data.sparepartsSpecificationCompliance.toCheckItem(inspectionId, cat, "Suku Cadang Sesuai Spesifikasi"))
        items.add(data.safetyFacilitiesAndDangerSigns.toCheckItem(inspectionId, cat, "Fasilitas Keselamatan & Tanda Bahaya"))
        items.add(data.circuitBreakerDataCheck.toCheckItem(inspectionId, cat, "Pemeriksaaan Data Pemutus Daya"))
        items.add(data.circuitBreakerCurrentRating.toCheckItem(inspectionId, cat, "Rating Arus CB"))
        items.add(data.circuitBreakerVoltageRating.toCheckItem(inspectionId, cat, "Rating Tegangan CB"))
        items.add(data.circuitBreakerInterruptingRating.toCheckItem(inspectionId, cat, "Rating Arus Pemutusan CB"))
        items.add(data.circuitBreakerControlVoltage.toCheckItem(inspectionId, cat, "Tegangan Kontrol CB"))
        items.add(data.circuitBreakerManufacturer.toCheckItem(inspectionId, cat, "Pabrik Pembuat CB"))
        items.add(data.circuitBreakerType.toCheckItem(inspectionId, cat, "Tipe CB"))
        items.add(data.circuitBreakerSerialNumber.toCheckItem(inspectionId, cat, "No. Seri CB"))
    }

    testing.mainTesting.let { data ->
        val cat = ElectricalCategory.MAIN_TESTING
        items.add(data.insulationResistance.toCheckItem(inspectionId, cat, "Pengujian Tahanan Isolasi"))
        items.add(data.groundingResistance.toCheckItem(inspectionId, cat, "Pengukuran Tahanan Pentanahan"))
        items.add(data.circuitBreakerEquipment.toCheckItem(inspectionId, cat, "Pengujian Perlengkapan Pemutus Daya"))
        items.add(data.currentTransformer.toCheckItem(inspectionId, cat, "Trafo Arus (CT)"))
        items.add(data.voltageTransformer.toCheckItem(inspectionId, cat, "Trafo Tegangan (PT)"))
        items.add(data.measuringInstrument.toCheckItem(inspectionId, cat, "Instrumen / Meter Pengukur"))
        items.add(data.fuseRating.toCheckItem(inspectionId, cat, "Rating Sekring"))
        items.add(data.mechanicalBreaker.toCheckItem(inspectionId, cat, "Pemutus Daya Mekanikal"))
        items.add(data.cableTerminal.toCheckItem(inspectionId, cat, "Terminal Kabel"))
        items.add(data.terminalMarking.toCheckItem(inspectionId, cat, "Penandaan Terminal"))
        items.add(data.interlockSystem.toCheckItem(inspectionId, cat, "System Interlock"))
        items.add(data.auxiliarySwitch.toCheckItem(inspectionId, cat, "Saklar Bantu"))
        items.add(data.mechanicalTripFunction.toCheckItem(inspectionId, cat, "Kerja Trip Mekanis"))
        items.add(data.overloadTripTest.toCheckItem(inspectionId, cat, "Uji Trip Tegangan Jatuh (Over Load)"))
        items.add(data.reversePowerRelayTest.toCheckItem(inspectionId, cat, "Uji Relay Daya Balik"))
        items.add(data.reverseCurrentRelayTest.toCheckItem(inspectionId, cat, "Uji Relay Arus Balik"))
        items.add(data.breakerTripTest.toCheckItem(inspectionId, cat, "Uji Trip Pemutus Daya"))
        items.add(data.temperatureMeasurement.toCheckItem(inspectionId, cat, "Pengukuran Temperatur"))
        items.add(data.indicatorLightFunction.toCheckItem(inspectionId, cat, "Uji fungsi Lampu Indikator"))
        items.add(data.meterDeviationTest.toCheckItem(inspectionId, cat, "Uji Kesalahan / Penyimpangan Meter"))
        items.add(data.synchronizationFunctionTest.toCheckItem(inspectionId, cat, "Uji Fungsi Sinkronisasi"))
        items.add(data.conductorAmpacity.toCheckItem(inspectionId, cat, "KHA Penghantar"))
        items.add(data.protectionRating.toCheckItem(inspectionId, cat, "Rating Proteksi"))
        items.add(data.voltageDrop.toCheckItem(inspectionId, cat, "Susut Tegangan (Drop Voltage)"))
        items.add(data.lossConnection.toCheckItem(inspectionId, cat, "Loss Connection"))
    }

    testing.sdpVisualInspection.let { data ->
        data.frontView.let { front ->
            val cat = ElectricalCategory.SDP_VISUAL_FRONT
            items.add(front.panelIndicatorLights.toCheckItem(inspectionId, cat, "Lampu Indikator Pada Panel"))
            items.add(front.panelDoorClearance.toCheckItem(inspectionId, cat, "Bebas Buka Pintu Panel"))
            items.add(front.lighting.toCheckItem(inspectionId, cat, "Pencahayaan"))
            items.add(front.lightingProductionRoom.toCheckItem(inspectionId, cat, "Pencahayaan Ruang Produksi"))
            items.add(front.lightingOffice.toCheckItem(inspectionId, cat, "Pencahayaan Kantor"))
            items.add(front.lightingMainPanel.toCheckItem(inspectionId, cat, "Pencahayaan Panel Utama"))
            items.add(front.lightingWarehouse.toCheckItem(inspectionId, cat, "Pencahayaan Gudang"))
            items.add(front.unusedItemsClearance.toCheckItem(inspectionId, cat, "Barang-Barang Tidak Terpakai"))
            items.add(front.dangerSignOnMainPanelDoor.toCheckItem(inspectionId, cat, "Tanda Bahaya Pintu Panel"))
        }
        data.internalViews.forEach { view ->
            val cat = ElectricalCategory.SDP_VISUAL_INTERNAL
            val prefix = "Lantai ${view.floor}: "
            view.inspections.let { insp ->
                items.add(insp.touchVoltageProtectionCover.toCheckItem(inspectionId, cat, "${prefix}Cover Pelindung Tegangan Sentuh"))
                items.add(insp.sldAndMaintenanceCard.toCheckItem(inspectionId, cat, "${prefix}Gambar SLD & Kartu Perawatan"))
                items.add(insp.bondingCable.toCheckItem(inspectionId, cat, "${prefix}Kabel Bonding"))
                items.add(insp.labeling.toCheckItem(inspectionId, cat, "${prefix}Labeling"))
                items.add(insp.cableColorCode.toCheckItem(inspectionId, cat, "${prefix}Kode Warna Kabel"))
                items.add(insp.panelCleanliness.toCheckItem(inspectionId, cat, "${prefix}Kebersihan Panel"))
                items.add(insp.installationNeatness.toCheckItem(inspectionId, cat, "${prefix}Kerapihan Instalasi"))
            }
        }
        data.terminalSystem.let { term ->
            val cat = ElectricalCategory.SDP_VISUAL_TERMINAL
            items.add(term.busbar.toCheckItem(inspectionId, cat, "Busbar / Penghantar"))
            items.add(term.breaker.toCheckItem(inspectionId, cat, "Pengaman (CB / FUSE)"))
            items.add(term.cableLugs.toCheckItem(inspectionId, cat, "Sepatu Kabel"))
            items.add(term.groundingSystem.toCheckItem(inspectionId, cat, "Sistem Pembumian"))
            items.add(term.busbarToBusbarDistance.toCheckItem(inspectionId, cat, "Jarak Busbar to Busbar"))
        }
    }

    testing.sdpTesting.let { data ->
        val cat = ElectricalCategory.SDP_TESTING
        items.add(data.voltagePhaseRSTN.toCheckItem(inspectionId, cat, "Tegangan Phasa R, S, T, N"))
        items.add(data.currentPhaseRSTN.toCheckItem(inspectionId, cat, "Arus Phase R, S, T, N"))
        items.add(data.meteringFunction.toCheckItem(inspectionId, cat, "Fungsi Alat Ukur"))
        items.add(data.panelLabeling.toCheckItem(inspectionId, cat, "Label Panel"))
        items.add(data.dangerSignOnPanelDoor.toCheckItem(inspectionId, cat, "Tanda Bahaya Pada Pintu Panel"))
        items.add(data.selectorSwitchAndLock.toCheckItem(inspectionId, cat, "Selector Switch dan Kunci Pintu"))
        items.add(data.conductorTerminalHeat.toCheckItem(inspectionId, cat, "Panas Penghantar Terminal"))
        items.add(data.groundingTest.toCheckItem(inspectionId, cat, "Pertanahan"))
        items.add(data.mainConductorAmpacity.toCheckItem(inspectionId, cat, "KHA Penghantar Utama"))
        items.add(data.mainProtectionRating.toCheckItem(inspectionId, cat, "Rating Proteksi Utama"))
    }
    return items
}

private fun ElectricalVerificationResult.toCheckItem(id: Long, cat: String, name: String) =
    InspectionCheckItemDomain(inspectionId = id, category = cat, itemName = name, status = this.isAvailable, result = this.remarks)

private fun ElectricalTestResult.toCheckItem(id: Long, cat: String, name: String) =
    InspectionCheckItemDomain(inspectionId = id, category = cat, itemName = name, status = this.result, result = this.method)

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toElectricalUiState(): ElectricalUiState {

    fun findTestResult(name: String) = this.testResults.find { it.testName.equals(name, true) }?.result ?: ""
    fun findCheckItem(cat: String, name: String) = this.checkItems.find { it.category == cat && it.itemName.equals(name, true) }

    fun findTestItem(cat: String, name: String): ElectricalTestResult {
        val item = findCheckItem(cat, name)
        return item?.let { ElectricalTestResult(result = it.status, method = it.result ?: "") } ?: ElectricalTestResult()
    }

    fun findVerificationItem(cat: String, name: String): ElectricalVerificationResult {
        val item = findCheckItem(cat, name)
        return item?.let { ElectricalVerificationResult(isAvailable = it.status, remarks = it.result ?: "") } ?: ElectricalVerificationResult()
    }

    val generalData = ElectricalGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyAddress = this.inspection.ownerAddress ?: "",
        installationType = this.inspection.equipmentType,
        examinationType = this.inspection.examinationType,
        permitNumber = this.inspection.permitNumber ?: "",
        inspectionDate = this.inspection.reportDate ?: "",
        inspectionLocation = this.inspection.usageLocation ?: "",
        ohsExpert = this.inspection.inspectorName ?: "",
        businessField = findTestResult("Bidang Usaha"),
        safetyServiceProvider = findTestResult("PJK3 Pelaksana")
    )

    val technicalData = ElectricalTechnicalData(
        powerSource = ElectricalPowerSource(
            plnKva = findTestResult("Daya PLN (KVA)"),
            dieselGeneratorKva = findTestResult("Daya Diesel Generator (KVA)")
        ),
        loadSystem = ElectricalLoadSystem(
            totalInstalledLoadWatt = findTestResult("Total Pembebanan Terpasang (WATT)"),
            lightingPowerWatt = findTestResult("Daya Penerangan (WATT)"),
            powerLoadWatt = findTestResult("Daya Tenaga (WATT)")
        ),
        currentVoltageType = this.inspection.driveType ?: findTestResult("Jenis Arus / Tegangan")
    )

    val initialDocs = ElectricalInitialDocumentVerification(
        singleLineDiagram = findVerificationItem(ElectricalCategory.INITIAL_DOC_VERIFICATION, "Single Line Diagram (SLD)"),
        layout = findVerificationItem(ElectricalCategory.INITIAL_DOC_VERIFICATION, "Lay Out"),
        usagePermitCertificate = findVerificationItem(ElectricalCategory.INITIAL_DOC_VERIFICATION, "Sertifikat Izin Pengesahan Pemakaian"),
        technicianLicense = findVerificationItem(ElectricalCategory.INITIAL_DOC_VERIFICATION, "Lisensi Teknisi")
    )

    val internalViews = this.checkItems
        .filter { it.category == ElectricalCategory.SDP_VISUAL_INTERNAL }
        .groupBy { it.itemName.substringBefore(":").replace("Lantai ", "").toIntOrNull() }
        .mapNotNull { (floor, items) ->
            if (floor == null) return@mapNotNull null
            val prefix = "Lantai $floor: "
            val cat = ElectricalCategory.SDP_VISUAL_INTERNAL
            ElectricalSdpInternalViewItem(
                floor = floor,
                inspections = ElectricalSdpInternalInspections(
                    touchVoltageProtectionCover = findTestItem(cat, "${prefix}Cover Pelindung Tegangan Sentuh"),
                    sldAndMaintenanceCard = findTestItem(cat, "${prefix}Gambar SLD & Kartu Perawatan"),
                    bondingCable = findTestItem(cat, "${prefix}Kabel Bonding"),
                    labeling = findTestItem(cat, "${prefix}Labeling"),
                    cableColorCode = findTestItem(cat, "${prefix}Kode Warna Kabel"),
                    panelCleanliness = findTestItem(cat, "${prefix}Kebersihan Panel"),
                    installationNeatness = findTestItem(cat, "${prefix}Kerapihan Instalasi")
                )
            )
        }.toImmutableList()

    val catDoc1 = ElectricalCategory.DOC_EXAMINATION_PART_1
    val docPart1 = ElectricalDocumentExaminationPart1(
        planningHasPermit = findTestItem(catDoc1, "Perencanaaan Memiliki Ijin"),
        locationMapExists = findTestItem(catDoc1, "Peta Lokasi"),
        singleLineDiagramExists = findTestItem(catDoc1, "Gambar Diagram Garis Tunggal"),
        layoutDiagramExists = findTestItem(catDoc1, "Gambar Layout Instalasi"),
        wiringDiagramExists = findTestItem(catDoc1, "Gambar Diagram Pengawatan"),
        areaClarificationDrawingExists = findTestItem(catDoc1, "Gambar Area Klarifikasi"),
        panelComponentListExists = findTestItem(catDoc1, "Daftar Komponen Panel"),
        shortCircuitCalculationExists = findTestItem(catDoc1, "Perhitungan Arus Hubung Singkat"),
        manualBookExists = findTestItem(catDoc1, "Buku Manual"),
        maintenanceAndOperationBookExists = findTestItem(catDoc1, "Buku Pemeliharaan dan Operasi"),
        warningSignsInstalled = findTestItem(catDoc1, "Tanda Peringatan"),
        manufacturerCertificationExists = findTestItem(catDoc1, "Sertifikasi Pabrik Pembuat"),
        equipmentTechnicalSpecsExists = findTestItem(catDoc1, "Spesifikasi Teknik Peralatan"),
        equipmentCertificationAndSpecsExists = findTestItem(catDoc1, "Spesipikasi Teknik dan Sertifikasi"),
        powerRecapitulationCalculationExists = findTestItem(catDoc1, "Perhitungan Rekapitulasi Daya"),
        dailyRecordExists = findTestItem(catDoc1, "Record Daily"),
        panelCoverCondition = findTestItem(catDoc1, "Cover Panel"),
        otherSupportingDataExists = findTestItem(catDoc1, "Data Penunjang Lainnya")
    )

    val catDoc2 = ElectricalCategory.DOC_EXAMINATION_PART_2
    val docPart2 = ElectricalDocumentExaminationPart2(
        unitConstructionLvmdpSdp = findTestItem(catDoc2, "Kontruksi Unit LVMDP / SDP"),
        mountingAndPlacement = findTestItem(catDoc2, "Dudukan dan Penempatan"),
        nameplateVerification = findTestItem(catDoc2, "Verifikasi Plat Nama"),
        areaClassification = findTestItem(catDoc2, "Klasifikasi Area"),
        protectionAgainstElectricShock = findTestItem(catDoc2, "Perlindungan Terhadap Kejut Listrik"),
        radiationProtection = findTestItem(catDoc2, "Pengaman dari Radiasi"),
        panelDoorStaysOpen = findTestItem(catDoc2, "Pintu Panel Dilengkapi Penahan"),
        boltsAndScrewsTightened = findTestItem(catDoc2, "Semua Baut dan Sekrup Kuat"),
        busbarInsulation = findTestItem(catDoc2, "Busbar Terisolasi dengan Kuat"),
        busbarClearance = findTestItem(catDoc2, "Minimal Ruang Main Busbar"),
        cableInstallation = findTestItem(catDoc2, "Pemasangan Kabel"),
        panelDoorCableProtection = findTestItem(catDoc2, "Kabel Pada Pintu Panel Terlindungi"),
        fuseReplacementSafety = findTestItem(catDoc2, "Penggantian Sekring Aman"),
        cableTerminalProtection = findTestItem(catDoc2, "Terminal Kabel Dilengkapi Pelindung"),
        measuringInstrumentsMarking = findTestItem(catDoc2, "Instrumen Pengukur Diberi Tanda"),
        equipmentAndTerminalLabeling = findTestItem(catDoc2, "Peralatan & Terminal Diberi Kode"),
        incomingOutgoingCableInstallation = findTestItem(catDoc2, "Pemasangan Kabel Masuk dan Keluar"),
        busbarSize = findTestItem(catDoc2, "Ukuran Busbar"),
        busbarCleanliness = findTestItem(catDoc2, "Kebersihan Busbar"),
        busbarPhaseMarking = findTestItem(catDoc2, "Penandaan Busbar (Phase)"),
        groundingCableInstallation = findTestItem(catDoc2, "Pemasangan Kabel Pembumian"),
        panelDoorInstallation = findTestItem(catDoc2, "Pemasangan Pintu Panel"),
        sparepartsSpecificationCompliance = findTestItem(catDoc2, "Suku Cadang Sesuai Spesifikasi"),
        safetyFacilitiesAndDangerSigns = findTestItem(catDoc2, "Fasilitas Keselamatan & Tanda Bahaya"),
        circuitBreakerDataCheck = findTestItem(catDoc2, "Pemeriksaaan Data Pemutus Daya"),
        circuitBreakerCurrentRating = findTestItem(catDoc2, "Rating Arus CB"),
        circuitBreakerVoltageRating = findTestItem(catDoc2, "Rating Tegangan CB"),
        circuitBreakerInterruptingRating = findTestItem(catDoc2, "Rating Arus Pemutusan CB"),
        circuitBreakerControlVoltage = findTestItem(catDoc2, "Tegangan Kontrol CB"),
        circuitBreakerManufacturer = findTestItem(catDoc2, "Pabrik Pembuat CB"),
        circuitBreakerType = findTestItem(catDoc2, "Tipe CB"),
        circuitBreakerSerialNumber = findTestItem(catDoc2, "No. Seri CB")
    )

    val catMain = ElectricalCategory.MAIN_TESTING
    val mainTesting = ElectricalMainTesting(
        insulationResistance = findTestItem(catMain, "Pengujian Tahanan Isolasi"),
        groundingResistance = findTestItem(catMain, "Pengukuran Tahanan Pentanahan"),
        circuitBreakerEquipment = findTestItem(catMain, "Pengujian Perlengkapan Pemutus Daya"),
        currentTransformer = findTestItem(catMain, "Trafo Arus (CT)"),
        voltageTransformer = findTestItem(catMain, "Trafo Tegangan (PT)"),
        measuringInstrument = findTestItem(catMain, "Instrumen / Meter Pengukur"),
        fuseRating = findTestItem(catMain, "Rating Sekring"),
        mechanicalBreaker = findTestItem(catMain, "Pemutus Daya Mekanikal"),
        cableTerminal = findTestItem(catMain, "Terminal Kabel"),
        terminalMarking = findTestItem(catMain, "Penandaan Terminal"),
        interlockSystem = findTestItem(catMain, "System Interlock"),
        auxiliarySwitch = findTestItem(catMain, "Saklar Bantu"),
        mechanicalTripFunction = findTestItem(catMain, "Kerja Trip Mekanis"),
        overloadTripTest = findTestItem(catMain, "Uji Trip Tegangan Jatuh (Over Load)"),
        reversePowerRelayTest = findTestItem(catMain, "Uji Relay Daya Balik"),
        reverseCurrentRelayTest = findTestItem(catMain, "Uji Relay Arus Balik"),
        breakerTripTest = findTestItem(catMain, "Uji Trip Pemutus Daya"),
        temperatureMeasurement = findTestItem(catMain, "Pengukuran Temperatur"),
        indicatorLightFunction = findTestItem(catMain, "Uji fungsi Lampu Indikator"),
        meterDeviationTest = findTestItem(catMain, "Uji Kesalahan / Penyimpangan Meter"),
        synchronizationFunctionTest = findTestItem(catMain, "Uji Fungsi Sinkronisasi"),
        conductorAmpacity = findTestItem(catMain, "KHA Penghantar"),
        protectionRating = findTestItem(catMain, "Rating Proteksi"),
        voltageDrop = findTestItem(catMain, "Susut Tegangan (Drop Voltage)"),
        lossConnection = findTestItem(catMain, "Loss Connection")
    )

    val catSdpFront = ElectricalCategory.SDP_VISUAL_FRONT
    val sdpFront = ElectricalSdpFrontView(
        panelIndicatorLights = findTestItem(catSdpFront, "Lampu Indikator Pada Panel"),
        panelDoorClearance = findTestItem(catSdpFront, "Bebas Buka Pintu Panel"),
        lighting = findTestItem(catSdpFront, "Pencahayaan"),
        lightingProductionRoom = findTestItem(catSdpFront, "Pencahayaan Ruang Produksi"),
        lightingOffice = findTestItem(catSdpFront, "Pencahayaan Kantor"),
        lightingMainPanel = findTestItem(catSdpFront, "Pencahayaan Panel Utama"),
        lightingWarehouse = findTestItem(catSdpFront, "Pencahayaan Gudang"),
        unusedItemsClearance = findTestItem(catSdpFront, "Barang-Barang Tidak Terpakai"),
        dangerSignOnMainPanelDoor = findTestItem(catSdpFront, "Tanda Bahaya Pintu Panel")
    )

    val catSdpTerm = ElectricalCategory.SDP_VISUAL_TERMINAL
    val sdpTerm = ElectricalSdpTerminalSystem(
        busbar = findTestItem(catSdpTerm, "Busbar / Penghantar"),
        breaker = findTestItem(catSdpTerm, "Pengaman (CB / FUSE)"),
        cableLugs = findTestItem(catSdpTerm, "Sepatu Kabel"),
        groundingSystem = findTestItem(catSdpTerm, "Sistem Pembumian"),
        busbarToBusbarDistance = findTestItem(catSdpTerm, "Jarak Busbar to Busbar")
    )

    val catSdpTest = ElectricalCategory.SDP_TESTING
    val sdpTest = ElectricalSdpTesting(
        voltagePhaseRSTN = findTestItem(catSdpTest, "Tegangan Phasa R, S, T, N"),
        currentPhaseRSTN = findTestItem(catSdpTest, "Arus Phase R, S, T, N"),
        meteringFunction = findTestItem(catSdpTest, "Fungsi Alat Ukur"),
        panelLabeling = findTestItem(catSdpTest, "Label Panel"),
        dangerSignOnPanelDoor = findTestItem(catSdpTest, "Tanda Bahaya Pada Pintu Panel"),
        selectorSwitchAndLock = findTestItem(catSdpTest, "Selector Switch dan Kunci Pintu"),
        conductorTerminalHeat = findTestItem(catSdpTest, "Panas Penghantar Terminal"),
        groundingTest = findTestItem(catSdpTest, "Pertanahan"),
        mainConductorAmpacity = findTestItem(catSdpTest, "KHA Penghantar Utama"),
        mainProtectionRating = findTestItem(catSdpTest, "Rating Proteksi Utama")
    )

    val inspectionAndTesting = ElectricalInspectionAndTesting(
        documentExaminationPart1 = docPart1,
        documentExaminationPart2 = docPart2,
        mainTesting = mainTesting,
        sdpVisualInspection = ElectricalSdpVisualInspection(
            frontView = sdpFront,
            internalViews = internalViews,
            terminalSystem = sdpTerm
        ),
        sdpTesting = sdpTest
    )

    val conclusion = ElectricalConclusion(
        findings = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }.toImmutableList(),
        summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }.toImmutableList(), // Summary juga diambil dari Finding
        recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }.toImmutableList()
    )

    return ElectricalUiState(
        isLoading = false,
        electricalInspectionReport = ElectricalInspectionReport(
            generalData = generalData,
            technicalData = technicalData,
            initialDocumentVerification = initialDocs,
            inspectionAndTesting = inspectionAndTesting,
            conclusion = conclusion
        )
    )
}