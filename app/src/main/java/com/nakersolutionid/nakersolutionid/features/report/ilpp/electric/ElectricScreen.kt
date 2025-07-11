package com.nakersolutionid.nakersolutionid.features.report.ilpp.electric

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.features.report.ilpp.ILPPViewModel
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel

@Composable
fun ElectricScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: ILPPViewModel = koinViewModel()
) {
    val uiState by viewModel.electricalUiState.collectAsStateWithLifecycle()
    val report = uiState.electricalInspectionReport
    val onDataChange = viewModel::onElectricalReportChange

    var showFindingDialog by remember { mutableStateOf(false) }
    var showSummaryDialog by remember { mutableStateOf(false) }
    var showRecommendationDialog by remember { mutableStateOf(false) }
    var showSdpFloorDialog by remember { mutableStateOf(false) }

    if (showFindingDialog) {
        AddElectricStringDialog(
            title = "Tambah Temuan",
            label = "Temuan",
            onDismissRequest = { showFindingDialog = false },
            onConfirm = {
                viewModel.addElectricalFinding(it)
                showFindingDialog = false
            }
        )
    }
    if (showSummaryDialog) {
        AddElectricStringDialog(
            title = "Tambah Poin Kesimpulan",
            label = "Poin Kesimpulan",
            onDismissRequest = { showSummaryDialog = false },
            onConfirm = {
                viewModel.addElectricalSummary(it)
                showSummaryDialog = false
            }
        )
    }
    if (showRecommendationDialog) {
        AddElectricStringDialog(
            title = "Tambah Poin Saran",
            label = "Poin Saran",
            onDismissRequest = { showRecommendationDialog = false },
            onConfirm = {
                viewModel.addElectricalRecommendation(it)
                showRecommendationDialog = false
            }
        )
    }
    if (showSdpFloorDialog) {
        AddSdpFloorDialog(
            onDismissRequest = { showSdpFloorDialog = false },
            onConfirm = {
                viewModel.addSdpInternalViewItem(it)
                showSdpFloorDialog = false
            }
        )
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        item {
            val data = report.generalData
            ElectricExpandableSection(title = "DATA UMUM", initiallyExpanded = true) {
                ElectricFormTextField(label = "Nama Perusahaan", value = data.companyName, onValueChange = { onDataChange(report.copy(generalData = data.copy(companyName = it))) })
                ElectricFormTextField(label = "Alamat Perusahaan", value = data.companyAddress, onValueChange = { onDataChange(report.copy(generalData = data.copy(companyAddress = it))) })
                ElectricFormTextField(label = "Jenis Instalasi", value = data.installationType, onValueChange = { onDataChange(report.copy(generalData = data.copy(installationType = it))) })
                ElectricFormTextField(label = "Jenis Pemeriksaan dan Pengujian", value = data.examinationType, onValueChange = { onDataChange(report.copy(generalData = data.copy(examinationType = it))) })
                ElectricFormTextField(label = "Bidang Usaha", value = data.businessField, onValueChange = { onDataChange(report.copy(generalData = data.copy(businessField = it))) })
                ElectricFormTextField(label = "PJK3 Pelaksana", value = data.safetyServiceProvider, onValueChange = { onDataChange(report.copy(generalData = data.copy(safetyServiceProvider = it))) })
                ElectricFormTextField(label = "Ahli K3 Riksa Uji", value = data.ohsExpert, onValueChange = { onDataChange(report.copy(generalData = data.copy(ohsExpert = it))) })
                ElectricFormTextField(label = "No. Ijin Pengesahan / Suket", value = data.permitNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(permitNumber = it))) })
                ElectricFormTextField(label = "Tanggal Riksa Uji", value = data.inspectionDate, onValueChange = { onDataChange(report.copy(generalData = data.copy(inspectionDate = it))) })
                ElectricFormTextField(label = "Tempat Pemeriksaan", value = data.inspectionLocation, onValueChange = { onDataChange(report.copy(generalData = data.copy(inspectionLocation = it))) })
            }
        }

        item {
            val data = report.technicalData
            ElectricExpandableSection(title = "DATA TEKNIS") {
                Text("Sumber Tenaga Listrik", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                ElectricFormTextField(label = "Daya PLN (KVA)", value = data.powerSource.plnKva, onValueChange = { onDataChange(report.copy(technicalData = data.copy(powerSource = data.powerSource.copy(plnKva = it)))) }, keyboardType = KeyboardType.Number)
                ElectricFormTextField(label = "Daya Diesel Generator (KVA)", value = data.powerSource.dieselGeneratorKva, onValueChange = { onDataChange(report.copy(technicalData = data.copy(powerSource = data.powerSource.copy(dieselGeneratorKva = it)))) }, keyboardType = KeyboardType.Number)
                Spacer(Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))
                Text("Sistem Pembebanan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                ElectricFormTextField(label = "Total Pembebanan Terpasang (WATT)", value = data.loadSystem.totalInstalledLoadWatt, onValueChange = { onDataChange(report.copy(technicalData = data.copy(loadSystem = data.loadSystem.copy(totalInstalledLoadWatt = it)))) }, keyboardType = KeyboardType.Number)
                ElectricFormTextField(label = "Daya Penerangan (WATT)", value = data.loadSystem.lightingPowerWatt, onValueChange = { onDataChange(report.copy(technicalData = data.copy(loadSystem = data.loadSystem.copy(lightingPowerWatt = it)))) }, keyboardType = KeyboardType.Number)
                ElectricFormTextField(label = "Daya Tenaga (WATT)", value = data.loadSystem.powerLoadWatt, onValueChange = { onDataChange(report.copy(technicalData = data.copy(loadSystem = data.loadSystem.copy(powerLoadWatt = it)))) }, keyboardType = KeyboardType.Number)
                Spacer(Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))
                Text("Jenis Arus / Tegangan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                ElectricFormTextField(label = "Jenis Arus / Tegangan", value = data.currentVoltageType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(currentVoltageType = it))) })
            }
        }

        item {
            val data = report.initialDocumentVerification
            ElectricExpandableSection(title = "PEMERIKSAAN DOKUMEN AWAL") {
                VerificationInput(label = "Single Line Diagram (SLD)", value = data.singleLineDiagram, onValueChange = { onDataChange(report.copy(initialDocumentVerification = data.copy(singleLineDiagram = it))) })
                VerificationInput(label = "Lay Out", value = data.layout, onValueChange = { onDataChange(report.copy(initialDocumentVerification = data.copy(layout = it))) })
                VerificationInput(label = "Sertifikat Izin Pengesahan Pemakaian", value = data.usagePermitCertificate, onValueChange = { onDataChange(report.copy(initialDocumentVerification = data.copy(usagePermitCertificate = it))) })
                VerificationInput(label = "Lisensi Teknisi", value = data.technicianLicense, onValueChange = { onDataChange(report.copy(initialDocumentVerification = data.copy(technicianLicense = it))) })
            }
        }

        item {
            val data = report.inspectionAndTesting
            ElectricExpandableSection(title = "PEMERIKSAAN & PENGUJIAN") {
                //region Dokumen 1
                ElectricExpandableSubSection("Pemeriksaan Dokumen I") {
                    val part1 = data.documentExaminationPart1
                    val onPart1Change: (ElectricalDocumentExaminationPart1) -> Unit = { onDataChange(report.copy(inspectionAndTesting = data.copy(documentExaminationPart1 = it))) }

                    TestResultInput(label = "Perencanaaan Memiliki Ijin", value = part1.planningHasPermit, onValueChange = { onPart1Change(part1.copy(planningHasPermit = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Peta Lokasi", value = part1.locationMapExists, onValueChange = { onPart1Change(part1.copy(locationMapExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Gambar Diagram Garis Tunggal", value = part1.singleLineDiagramExists, onValueChange = { onPart1Change(part1.copy(singleLineDiagramExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Gambar Layout Instalasi", value = part1.layoutDiagramExists, onValueChange = { onPart1Change(part1.copy(layoutDiagramExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Gambar Diagram Pengawatan", value = part1.wiringDiagramExists, onValueChange = { onPart1Change(part1.copy(wiringDiagramExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Gambar Area Klarifikasi", value = part1.areaClarificationDrawingExists, onValueChange = { onPart1Change(part1.copy(areaClarificationDrawingExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Daftar Komponen Panel", value = part1.panelComponentListExists, onValueChange = { onPart1Change(part1.copy(panelComponentListExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Perhitungan Arus Hubung Singkat", value = part1.shortCircuitCalculationExists, onValueChange = { onPart1Change(part1.copy(shortCircuitCalculationExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Buku Manual", value = part1.manualBookExists, onValueChange = { onPart1Change(part1.copy(manualBookExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Buku Pemeliharaan dan Operasi", value = part1.maintenanceAndOperationBookExists, onValueChange = { onPart1Change(part1.copy(maintenanceAndOperationBookExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Tanda Peringatan", value = part1.warningSignsInstalled, onValueChange = { onPart1Change(part1.copy(warningSignsInstalled = it)) }, positiveLabel = "Terpasang", negativeLabel = "Tidak Terpasang")
                    TestResultInput(label = "Sertifikasi Pabrik Pembuat", value = part1.manufacturerCertificationExists, onValueChange = { onPart1Change(part1.copy(manufacturerCertificationExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Spesifikasi Teknik Peralatan", value = part1.equipmentTechnicalSpecsExists, onValueChange = { onPart1Change(part1.copy(equipmentTechnicalSpecsExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Spesipikasi Teknik dan Sertifikasi", value = part1.equipmentCertificationAndSpecsExists, onValueChange = { onPart1Change(part1.copy(equipmentCertificationAndSpecsExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Perhitungan Rekapitulasi Daya", value = part1.powerRecapitulationCalculationExists, onValueChange = { onPart1Change(part1.copy(powerRecapitulationCalculationExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Record Daily", value = part1.dailyRecordExists, onValueChange = { onPart1Change(part1.copy(dailyRecordExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                    TestResultInput(label = "Cover Panel", value = part1.panelCoverCondition, onValueChange = { onPart1Change(part1.copy(panelCoverCondition = it)) })
                    TestResultInput(label = "Data Penunjang Lainnya", value = part1.otherSupportingDataExists, onValueChange = { onPart1Change(part1.copy(otherSupportingDataExists = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                }
                //endregion
                HorizontalDivider()
                //region Dokumen 2
                ElectricExpandableSubSection("Pemeriksaan Dokumen II") {
                    val part2 = data.documentExaminationPart2
                    val onPart2Change: (ElectricalDocumentExaminationPart2) -> Unit = { onDataChange(report.copy(inspectionAndTesting = data.copy(documentExaminationPart2 = it))) }

                    TestResultInput(label = "Kontruksi Unit LVMDP / SDP", value = part2.unitConstructionLvmdpSdp, onValueChange = { onPart2Change(part2.copy(unitConstructionLvmdpSdp = it)) })
                    TestResultInput(label = "Dudukan dan Penempatan", value = part2.mountingAndPlacement, onValueChange = { onPart2Change(part2.copy(mountingAndPlacement = it)) })
                    TestResultInput(label = "Verifikasi Plat Nama", value = part2.nameplateVerification, onValueChange = { onPart2Change(part2.copy(nameplateVerification = it)) })
                    TestResultInput(label = "Klasifikasi Area", value = part2.areaClassification, onValueChange = { onPart2Change(part2.copy(areaClassification = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Perlindungan Terhadap Kejut Listrik", value = part2.protectionAgainstElectricShock, onValueChange = { onPart2Change(part2.copy(protectionAgainstElectricShock = it)) })
                    TestResultInput(label = "Pengaman dari Radiasi", value = part2.radiationProtection, onValueChange = { onPart2Change(part2.copy(radiationProtection = it)) })
                    TestResultInput(label = "Pintu Panel Dilengkapi Penahan", value = part2.panelDoorStaysOpen, onValueChange = { onPart2Change(part2.copy(panelDoorStaysOpen = it)) })
                    TestResultInput(label = "Semua Baut dan Sekrup Kuat", value = part2.boltsAndScrewsTightened, onValueChange = { onPart2Change(part2.copy(boltsAndScrewsTightened = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Busbar Terisolasi dengan Kuat", value = part2.busbarInsulation, onValueChange = { onPart2Change(part2.copy(busbarInsulation = it)) })
                    TestResultInput(label = "Minimal Ruang Main Busbar", value = part2.busbarClearance, onValueChange = { onPart2Change(part2.copy(busbarClearance = it)) })
                    TestResultInput(label = "Pemasangan Kabel", value = part2.cableInstallation, onValueChange = { onPart2Change(part2.copy(cableInstallation = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Kabel Pada Pintu Panel Terlindungi", value = part2.panelDoorCableProtection, onValueChange = { onPart2Change(part2.copy(panelDoorCableProtection = it)) })
                    TestResultInput(label = "Penggantian Sekring Aman", value = part2.fuseReplacementSafety, onValueChange = { onPart2Change(part2.copy(fuseReplacementSafety = it)) })
                    TestResultInput(label = "Terminal Kabel Dilengkapi Pelindung", value = part2.cableTerminalProtection, onValueChange = { onPart2Change(part2.copy(cableTerminalProtection = it)) })
                    TestResultInput(label = "Instrumen Pengukur Diberi Tanda", value = part2.measuringInstrumentsMarking, onValueChange = { onPart2Change(part2.copy(measuringInstrumentsMarking = it)) })
                    TestResultInput(label = "Peralatan & Terminal Diberi Kode", value = part2.equipmentAndTerminalLabeling, onValueChange = { onPart2Change(part2.copy(equipmentAndTerminalLabeling = it)) })
                    TestResultInput(label = "Pemasangan Kabel Masuk dan Keluar", value = part2.incomingOutgoingCableInstallation, onValueChange = { onPart2Change(part2.copy(incomingOutgoingCableInstallation = it)) })
                    TestResultInput(label = "Ukuran Busbar", value = part2.busbarSize, onValueChange = { onPart2Change(part2.copy(busbarSize = it)) })
                    TestResultInput(label = "Kebersihan Busbar", value = part2.busbarCleanliness, onValueChange = { onPart2Change(part2.copy(busbarCleanliness = it)) }, positiveLabel = "Bersih", negativeLabel = "Tidak Bersih")
                    TestResultInput(label = "Penandaan Busbar (Phase)", value = part2.busbarPhaseMarking, onValueChange = { onPart2Change(part2.copy(busbarPhaseMarking = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Pemasangan Kabel Pembumian", value = part2.groundingCableInstallation, onValueChange = { onPart2Change(part2.copy(groundingCableInstallation = it)) })
                    TestResultInput(label = "Pemasangan Pintu Panel", value = part2.panelDoorInstallation, onValueChange = { onPart2Change(part2.copy(panelDoorInstallation = it)) })
                    TestResultInput(label = "Suku Cadang Sesuai Spesifikasi", value = part2.sparepartsSpecificationCompliance, onValueChange = { onPart2Change(part2.copy(sparepartsSpecificationCompliance = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Fasilitas Keselamatan & Tanda Bahaya", value = part2.safetyFacilitiesAndDangerSigns, onValueChange = { onPart2Change(part2.copy(safetyFacilitiesAndDangerSigns = it)) })
                    TestResultInput(label = "Pemeriksaaan Data Pemutus Daya", value = part2.circuitBreakerDataCheck, onValueChange = { onPart2Change(part2.copy(circuitBreakerDataCheck = it)) })
                    TestResultInput(label = "Rating Arus CB", value = part2.circuitBreakerCurrentRating, onValueChange = { onPart2Change(part2.copy(circuitBreakerCurrentRating = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Rating Tegangan CB", value = part2.circuitBreakerVoltageRating, onValueChange = { onPart2Change(part2.copy(circuitBreakerVoltageRating = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Rating Arus Pemutusan CB", value = part2.circuitBreakerInterruptingRating, onValueChange = { onPart2Change(part2.copy(circuitBreakerInterruptingRating = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Tegangan Kontrol CB", value = part2.circuitBreakerControlVoltage, onValueChange = { onPart2Change(part2.copy(circuitBreakerControlVoltage = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Pabrik Pembuat CB", value = part2.circuitBreakerManufacturer, onValueChange = { onPart2Change(part2.copy(circuitBreakerManufacturer = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "Tipe CB", value = part2.circuitBreakerType, onValueChange = { onPart2Change(part2.copy(circuitBreakerType = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    TestResultInput(label = "No. Seri CB", value = part2.circuitBreakerSerialNumber, onValueChange = { onPart2Change(part2.copy(circuitBreakerSerialNumber = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                }
                //endregion
                HorizontalDivider()
                //region Pengujian Utama
                ElectricExpandableSubSection("Pengujian Utama") {
                    val main = data.mainTesting
                    val onMainChange: (ElectricalMainTesting) -> Unit = { onDataChange(report.copy(inspectionAndTesting = data.copy(mainTesting = it))) }

                    TestResultInput(label = "Pengujian Tahanan Isolasi", value = main.insulationResistance, onValueChange = { onMainChange(main.copy(insulationResistance = it)) })
                    TestResultInput(label = "Pengukuran Tahanan Pentanahan", value = main.groundingResistance, onValueChange = { onMainChange(main.copy(groundingResistance = it)) })
                    TestResultInput(label = "Pengujian Perlengkapan Pemutus Daya", value = main.circuitBreakerEquipment, onValueChange = { onMainChange(main.copy(circuitBreakerEquipment = it)) })
                    TestResultInput(label = "Trafo Arus (CT)", value = main.currentTransformer, onValueChange = { onMainChange(main.copy(currentTransformer = it)) })
                    TestResultInput(label = "Trafo Tegangan (PT)", value = main.voltageTransformer, onValueChange = { onMainChange(main.copy(voltageTransformer = it)) })
                    TestResultInput(label = "Instrumen / Meter Pengukur", value = main.measuringInstrument, onValueChange = { onMainChange(main.copy(measuringInstrument = it)) })
                    TestResultInput(label = "Rating Sekring", value = main.fuseRating, onValueChange = { onMainChange(main.copy(fuseRating = it)) })
                    TestResultInput(label = "Pemutus Daya Mekanikal", value = main.mechanicalBreaker, onValueChange = { onMainChange(main.copy(mechanicalBreaker = it)) })
                    TestResultInput(label = "Terminal Kabel", value = main.cableTerminal, onValueChange = { onMainChange(main.copy(cableTerminal = it)) })
                    TestResultInput(label = "Penandaan Terminal", value = main.terminalMarking, onValueChange = { onMainChange(main.copy(terminalMarking = it)) })
                    TestResultInput(label = "System Interlock", value = main.interlockSystem, onValueChange = { onMainChange(main.copy(interlockSystem = it)) })
                    TestResultInput(label = "Saklar Bantu", value = main.auxiliarySwitch, onValueChange = { onMainChange(main.copy(auxiliarySwitch = it)) })
                    TestResultInput(label = "Kerja Trip Mekanis", value = main.mechanicalTripFunction, onValueChange = { onMainChange(main.copy(mechanicalTripFunction = it)) })
                    TestResultInput(label = "Uji Trip Tegangan Jatuh (Over Load)", value = main.overloadTripTest, onValueChange = { onMainChange(main.copy(overloadTripTest = it)) })
                    TestResultInput(label = "Uji Relay Daya Balik", value = main.reversePowerRelayTest, onValueChange = { onMainChange(main.copy(reversePowerRelayTest = it)) })
                    TestResultInput(label = "Uji Relay Arus Balik", value = main.reverseCurrentRelayTest, onValueChange = { onMainChange(main.copy(reverseCurrentRelayTest = it)) })
                    TestResultInput(label = "Uji Trip Pemutus Daya", value = main.breakerTripTest, onValueChange = { onMainChange(main.copy(breakerTripTest = it)) })
                    TestResultInput(label = "Pengukuran Temperatur", value = main.temperatureMeasurement, onValueChange = { onMainChange(main.copy(temperatureMeasurement = it)) })
                    TestResultInput(label = "Uji fungsi Lampu Indikator", value = main.indicatorLightFunction, onValueChange = { onMainChange(main.copy(indicatorLightFunction = it)) })
                    TestResultInput(label = "Uji Kesalahan / Penyimpangan Meter", value = main.meterDeviationTest, onValueChange = { onMainChange(main.copy(meterDeviationTest = it)) })
                    TestResultInput(label = "Uji Fungsi Sinkronisasi", value = main.synchronizationFunctionTest, onValueChange = { onMainChange(main.copy(synchronizationFunctionTest = it)) })
                    TestResultInput(label = "KHA Penghantar", value = main.conductorAmpacity, onValueChange = { onMainChange(main.copy(conductorAmpacity = it)) })
                    TestResultInput(label = "Rating Proteksi", value = main.protectionRating, onValueChange = { onMainChange(main.copy(protectionRating = it)) })
                    TestResultInput(label = "Susut Tegangan (Drop Voltage)", value = main.voltageDrop, onValueChange = { onMainChange(main.copy(voltageDrop = it)) })
                    TestResultInput(label = "Loss Connection", value = main.lossConnection, onValueChange = { onMainChange(main.copy(lossConnection = it)) })
                }
                //endregion
                HorizontalDivider()
                //region Visual SDP
                ElectricExpandableSubSection("Pemeriksaan Visual Panel SDP") {
                    val sdpVisual = data.sdpVisualInspection
                    val onSdpVisualChange: (ElectricalSdpVisualInspection) -> Unit = { onDataChange(report.copy(inspectionAndTesting = data.copy(sdpVisualInspection = it))) }

                    ElectricExpandableSubSection(title = "Tampak Depan") {
                        val frontView = sdpVisual.frontView
                        val onFrontViewChange: (ElectricalSdpFrontView) -> Unit = { onSdpVisualChange(sdpVisual.copy(frontView = it)) }
                        TestResultInput(label = "Lampu Indikator Pada Panel", value = frontView.panelIndicatorLights, onValueChange = { onFrontViewChange(frontView.copy(panelIndicatorLights = it)) })
                        TestResultInput(label = "Bebas Buka Pintu Panel", value = frontView.panelDoorClearance, onValueChange = { onFrontViewChange(frontView.copy(panelDoorClearance = it)) })
                        TestResultInput(label = "Pencahayaan", value = frontView.lighting, onValueChange = { onFrontViewChange(frontView.copy(lighting = it)) })
                        TestResultInput(label = "Pencahayaan Ruang Produksi", value = frontView.lightingProductionRoom, onValueChange = { onFrontViewChange(frontView.copy(lightingProductionRoom = it)) })
                        TestResultInput(label = "Pencahayaan Kantor", value = frontView.lightingOffice, onValueChange = { onFrontViewChange(frontView.copy(lightingOffice = it)) })
                        TestResultInput(label = "Pencahayaan Panel Utama", value = frontView.lightingMainPanel, onValueChange = { onFrontViewChange(frontView.copy(lightingMainPanel = it)) })
                        TestResultInput(label = "Pencahayaan Gudang", value = frontView.lightingWarehouse, onValueChange = { onFrontViewChange(frontView.copy(lightingWarehouse = it)) })
                        TestResultInput(label = "Barang-Barang Tidak Terpakai", value = frontView.unusedItemsClearance, onValueChange = { onFrontViewChange(frontView.copy(unusedItemsClearance = it)) })
                        TestResultInput(label = "Tanda Bahaya Pintu Panel", value = frontView.dangerSignOnMainPanelDoor, onValueChange = { onFrontViewChange(frontView.copy(dangerSignOnMainPanelDoor = it)) }, positiveLabel = "Terpasang", negativeLabel = "Tidak Terpasang")
                    }
                    HorizontalDivider()
                    ElectricExpandableSubSection(title = "Tampak Dalam") {
                        FilledTonalButton(onClick = { showSdpFloorDialog = true }, modifier = Modifier.fillMaxWidth()) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tambah Pemeriksaan Lantai")
                        }
                        sdpVisual.internalViews.forEachIndexed { index, item ->
                            val onItemChange: (ElectricalSdpInternalViewItem) -> Unit = { updatedItem ->
                                val newList = sdpVisual.internalViews.toMutableList()
                                newList[index] = updatedItem
                                onSdpVisualChange(sdpVisual.copy(internalViews = newList.toImmutableList()))
                            }
                            val inspections = item.inspections
                            val onInspectionsChange : (ElectricalSdpInternalInspections) -> Unit = { onItemChange(item.copy(inspections = it)) }

                            ElectricListItemWithDelete(onDelete = { viewModel.deleteSdpInternalViewItem(index) }) {
                                ElectricExpandableSubSection(title = "Pemeriksaan Lantai ${item.floor}") {
                                    TestResultInput(label = "Cover Pelindung Tegangan Sentuh", value = inspections.touchVoltageProtectionCover, onValueChange = { onInspectionsChange(inspections.copy(touchVoltageProtectionCover = it)) }, positiveLabel = "Terpasang", negativeLabel = "Tidak Terpasang")
                                    TestResultInput(label = "Gambar SLD & Kartu Perawatan", value = inspections.sldAndMaintenanceCard, onValueChange = { onInspectionsChange(inspections.copy(sldAndMaintenanceCard = it)) }, positiveLabel = "Terpasang", negativeLabel = "Tidak Terpasang")
                                    TestResultInput(label = "Kabel Bonding", value = inspections.bondingCable, onValueChange = { onInspectionsChange(inspections.copy(bondingCable = it)) }, positiveLabel = "Terpasang", negativeLabel = "Tidak Terpasang")
                                    TestResultInput(label = "Labeling", value = inspections.labeling, onValueChange = { onInspectionsChange(inspections.copy(labeling = it)) }, positiveLabel = "Ada Label", negativeLabel = "Tidak di Label")
                                    TestResultInput(label = "Kode Warna Kabel", value = inspections.cableColorCode, onValueChange = { onInspectionsChange(inspections.copy(cableColorCode = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                                    TestResultInput(label = "Kebersihan Panel", value = inspections.panelCleanliness, onValueChange = { onInspectionsChange(inspections.copy(panelCleanliness = it)) })
                                    TestResultInput(label = "Kerapihan Instalasi", value = inspections.installationNeatness, onValueChange = { onInspectionsChange(inspections.copy(installationNeatness = it)) })
                                }
                            }
                        }
                    }
                    HorizontalDivider()
                    ElectricExpandableSubSection(title = "Sistem Terminal") {
                        val terminal = sdpVisual.terminalSystem
                        val onTerminalChange: (ElectricalSdpTerminalSystem) -> Unit = { onSdpVisualChange(sdpVisual.copy(terminalSystem = it)) }
                        TestResultInput(label = "Busbar / Penghantar", value = terminal.busbar, onValueChange = { onTerminalChange(terminal.copy(busbar = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                        TestResultInput(label = "Pengaman (CB / FUSE)", value = terminal.breaker, onValueChange = { onTerminalChange(terminal.copy(breaker = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                        TestResultInput(label = "Sepatu Kabel", value = terminal.cableLugs, onValueChange = { onTerminalChange(terminal.copy(cableLugs = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                        TestResultInput(label = "Sistem Pembumian", value = terminal.groundingSystem, onValueChange = { onTerminalChange(terminal.copy(groundingSystem = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                        TestResultInput(label = "Jarak Busbar to Busbar", value = terminal.busbarToBusbarDistance, onValueChange = { onTerminalChange(terminal.copy(busbarToBusbarDistance = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    }
                    HorizontalDivider()
                    ElectricExpandableSubSection(title = "Pengujian SDP") {
                        val sdpTest = data.sdpTesting
                        val onSdpTestChange: (ElectricalSdpTesting) -> Unit = { onDataChange(report.copy(inspectionAndTesting = data.copy(sdpTesting = it))) }

                        TestResultInput(label = "Tegangan Phasa R, S, T, N", value = sdpTest.voltagePhaseRSTN, onValueChange = { onSdpTestChange(sdpTest.copy(voltagePhaseRSTN = it)) })
                        TestResultInput(label = "Arus Phase R, S, T, N", value = sdpTest.currentPhaseRSTN, onValueChange = { onSdpTestChange(sdpTest.copy(currentPhaseRSTN = it)) })
                        TestResultInput(label = "Fungsi Alat Ukur", value = sdpTest.meteringFunction, onValueChange = { onSdpTestChange(sdpTest.copy(meteringFunction = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                        TestResultInput(label = "Label Panel", value = sdpTest.panelLabeling, onValueChange = { onSdpTestChange(sdpTest.copy(panelLabeling = it)) }, positiveLabel = "Terpasang", negativeLabel = "Tidak Terpasang")
                        TestResultInput(label = "Tanda Bahaya Pada Pintu Panel", value = sdpTest.dangerSignOnPanelDoor, onValueChange = { onSdpTestChange(sdpTest.copy(dangerSignOnPanelDoor = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                        TestResultInput(label = "Selector Switch dan Kunci Pintu", value = sdpTest.selectorSwitchAndLock, onValueChange = { onSdpTestChange(sdpTest.copy(selectorSwitchAndLock = it)) }, positiveLabel = "Ada", negativeLabel = "Tidak Ada")
                        TestResultInput(label = "Panas Penghantar Terminal", value = sdpTest.conductorTerminalHeat, onValueChange = { onSdpTestChange(sdpTest.copy(conductorTerminalHeat = it)) }, positiveLabel = "Dilakukan", negativeLabel = "Tidak Dilakukan")
                        TestResultInput(label = "Pertanahan", value = sdpTest.groundingTest, onValueChange = { onSdpTestChange(sdpTest.copy(groundingTest = it)) }, positiveLabel = "Dilakukan", negativeLabel = "Tidak Dilakukan")
                        TestResultInput(label = "KHA Penghantar Utama", value = sdpTest.mainConductorAmpacity, onValueChange = { onSdpTestChange(sdpTest.copy(mainConductorAmpacity = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                        TestResultInput(label = "Rating Proteksi Utama", value = sdpTest.mainProtectionRating, onValueChange = { onSdpTestChange(sdpTest.copy(mainProtectionRating = it)) }, positiveLabel = "Sesuai", negativeLabel = "Tidak Sesuai")
                    }
                }
                //endregion
            }
        }

        item {
            val data = report.conclusion
            ElectricExpandableSection(title = "TEMUAN") {
                FilledTonalButton(onClick = { showFindingDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Temuan")
                }
                data.findings.forEachIndexed { index, item ->
                    ElectricListItemWithDelete(onDelete = { viewModel.removeElectricalFinding(index) }) {
                        Text(item, modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
                    }
                }
            }
        }
        item {
            val data = report.conclusion
            ElectricExpandableSection(title = "KESIMPULAN") {
                FilledTonalButton(onClick = { showSummaryDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Kesimpulan")
                }
                data.summary.forEachIndexed { index, item ->
                    ElectricListItemWithDelete(onDelete = { viewModel.removeElectricalSummary(index) }) {
                        Text(item, modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
                    }
                }
            }
        }

        item {
            val data = report.conclusion
            ElectricExpandableSection(title = "SARAN") {
                FilledTonalButton(onClick = { showRecommendationDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Saran")
                }
                data.recommendations.forEachIndexed { index, item ->
                    ElectricListItemWithDelete(onDelete = { viewModel.removeElectricalRecommendation(index) }) {
                        Text(item, modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


//region Reusable Composables for ElectricScreen
@Composable
private fun ElectricListItemWithDelete(
    onDelete: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(modifier = Modifier.weight(1f)) {
            Column(content = content)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Hapus Item")
        }
    }
}

@Composable
private fun ElectricExpandableSection(
    title: String,
    initiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(initiallyExpanded) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { expanded = !expanded }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = if (expanded) "Ciutkan" else "Lebarkan")
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    content()
                }
            }
        }
    }
}

@Composable
private fun ElectricExpandableSubSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .clickable { expanded = !expanded }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = if (expanded) "Ciutkan" else "Lebarkan")
            }
        }
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                content()
            }
        }
    }
}

@Composable
fun ElectricFormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = MaterialTheme.shapes.medium,
    )
}

@Composable
private fun VerificationInput(
    label: String,
    value: ElectricalVerificationResult,
    onValueChange: (ElectricalVerificationResult) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = value.isAvailable, onClick = { onValueChange(value.copy(isAvailable = true)) })
            Text("Ada")
            Spacer(Modifier.width(16.dp))
            RadioButton(selected = !value.isAvailable, onClick = { onValueChange(value.copy(isAvailable = false)) })
            Text("Tidak Ada")
        }
        ElectricFormTextField(
            label = "Keterangan",
            value = value.remarks,
            onValueChange = { onValueChange(value.copy(remarks = it)) }
        )
    }
}

@Composable
private fun TestResultInput(
    label: String,
    value: ElectricalTestResult,
    onValueChange: (ElectricalTestResult) -> Unit,
    positiveLabel: String = "Baik",
    negativeLabel: String = "Tidak Baik"
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(result = !value.result)) }) {
            Checkbox(checked = value.result, onCheckedChange = null)
            Spacer(Modifier.width(4.dp))
            Text(if(value.result) positiveLabel else negativeLabel)
        }
        ElectricFormTextField(
            label = "Metode / Keterangan",
            value = value.method,
            onValueChange = { onValueChange(value.copy(method = it)) }
        )
    }
}
//endregion