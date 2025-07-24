package com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.features.report.ipk.IPKViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FireProtectionScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: IPKViewModel = koinViewModel()
) {
    val uiState by viewModel.fireProtectionUiState.collectAsStateWithLifecycle()
    val report = uiState.inspectionReport
    val onDataChange = viewModel::onFireProtectionReportChange

    var showPumpTestDialog by remember { mutableStateOf(false) }
    var showHydrantTestDialog by remember { mutableStateOf(false) }
    var showRecommendationDialog by remember { mutableStateOf(false) }
    var showAlarmInstallationDialog by remember { mutableStateOf(false) }

    if (showPumpTestDialog) {
        AddPumpTestDialog(
            onDismissRequest = { showPumpTestDialog = false },
            onConfirm = { viewModel.addPumpFunctionTestItem(it); showPumpTestDialog = false }
        )
    }
    if (showHydrantTestDialog) {
        AddHydrantTestDialog(
            onDismissRequest = { showHydrantTestDialog = false },
            onConfirm = { viewModel.addHydrantOperationalTestItem(it); showHydrantTestDialog = false }
        )
    }
    if (showRecommendationDialog) {
        AddFireProtectionStringDialog(
            title = "Tambah Poin Syarat",
            label = "Poin Syarat",
            onDismissRequest = { showRecommendationDialog = false },
            onConfirm = { viewModel.addConclusionRecommendation(it); showRecommendationDialog = false }
        )
    }

    if (showAlarmInstallationDialog) {
        AddAlarmInstallationDialog(
            onDismissRequest = { showAlarmInstallationDialog = false },
            onConfirm = {
                viewModel.addAlarmInstallationItem(it) // Assumes ViewModel has this method
                showAlarmInstallationDialog = false
            }
        )
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        item {
            val data = report.documentChecklist
            val onDataChanged: (FireProtectionDocumentChecklist) -> Unit = { onDataChange(report.copy(documentChecklist = it)) }
            FireProtectionExpandableSection("PEMERIKSAAN DOKUMEN", true) {
                ChecklistResultInput("Gambar Teknis", data.technicalDrawing) { onDataChanged(data.copy(technicalDrawing = it)) }
                ChecklistResultInput("Dokumentasi Riksa/Uji Sebelumnya", data.previousTestDocumentation) { onDataChanged(data.copy(previousTestDocumentation = it)) }
                ChecklistResultInput("Surat Permohonan", data.requestLetter) { onDataChanged(data.copy(requestLetter = it)) }
                ChecklistResultInput("Dokumen Spesifikasi", data.specificationDocument) { onDataChanged(data.copy(specificationDocument = it)) }
            }
        }

        item {
            val data = report.companyData
            val onDataChanged: (FireProtectionCompanyData) -> Unit = { onDataChange(report.copy(companyData = it)) }
            FireProtectionExpandableSection("DATA PERUSAHAAN") {
                FireProtectionFormTextField("Nama Instansi / Gedung", data.companyName) { onDataChanged(data.copy(companyName = it)) }
                FireProtectionFormTextField("Alamat Instansi", data.companyLocation) { onDataChanged(data.copy(companyLocation = it)) }
                FireProtectionFormTextField("Lokasi Gedung", data.usageLocation) { onDataChanged(data.copy(usageLocation = it)) }
                FireProtectionFormTextField("Jenis Pemeriksaan Dan Pengujian", data.examinationType) { onDataChanged(data.copy(examinationType = it)) }
                FireProtectionFormTextField("Nomor Surat Keterangan", data.certificateNumber) { onDataChanged(data.copy(certificateNumber = it)) }
                FireProtectionFormTextField("Jenis Objek K3", data.objectType) { onDataChanged(data.copy(objectType = it)) }
                FireProtectionFormTextField("Tanggal Pemeriksaan", data.inspectionDate) { onDataChanged(data.copy(inspectionDate = it)) }
            }
        }

        item {
            val data = report.buildingData
            val onDataChanged: (FireProtectionBuildingData) -> Unit = { onDataChange(report.copy(buildingData = it)) }
            FireProtectionExpandableSection("DATA BANGUNAN") {
                FireProtectionFormTextField("Luas Lahan (m²)", data.landAreaSqm) { onDataChanged(data.copy(landAreaSqm = it)) }
                FireProtectionFormTextField("Luas Bangunan (m²)", data.buildingAreaSqm) { onDataChanged(data.copy(buildingAreaSqm = it)) }
                FireProtectionFormTextField("Tinggi Bangunan (m)", data.buildingHeightM) { onDataChanged(data.copy(buildingHeightM = it)) }
                FireProtectionFormTextField("Jumlah Lantai", data.floorCount) { onDataChanged(data.copy(floorCount = it)) }
                FireProtectionFormTextField("Dibangun Tahun", data.yearBuilt) { onDataChanged(data.copy(yearBuilt = it)) }

                FireProtectionExpandableSubSection("Konstruksi Bangunan") {
                    val cons = data.construction
                    val onConsChanged: (FireProtectionConstruction) -> Unit = { onDataChanged(data.copy(construction = it)) }
                    FireProtectionFormTextField("Struktur Utama", cons.mainStructure) { onConsChanged(cons.copy(mainStructure = it)) }
                    FireProtectionFormTextField("Struktur Lantai", cons.floorStructure) { onConsChanged(cons.copy(floorStructure = it)) }
                    FireProtectionFormTextField("Dinding Luar", cons.exteriorWalls) { onConsChanged(cons.copy(exteriorWalls = it)) }
                    FireProtectionFormTextField("Dinding Dalam", cons.interiorWalls) { onConsChanged(cons.copy(interiorWalls = it)) }
                    FireProtectionFormTextField("Rangka Plafon", cons.ceilingFrame) { onConsChanged(cons.copy(ceilingFrame = it)) }
                    FireProtectionFormTextField("Penutup Plafon", cons.ceilingCover) { onConsChanged(cons.copy(ceilingCover = it)) }
                    FireProtectionFormTextField("Rangka Atap", cons.roofFrame) { onConsChanged(cons.copy(roofFrame = it)) }
                    FireProtectionFormTextField("Penutup Atap", cons.roofCover) { onConsChanged(cons.copy(roofCover = it)) }
                }
                HorizontalDivider()
                FireProtectionExpandableSubSection("Perlengkapan Proteksi Kebakaran") {
                    val equip = data.fireProtectionEquipment
                    val onEquipChanged: (FireProtectionEquipment) -> Unit = { onDataChanged(data.copy(fireProtectionEquipment = it)) }
                    CheckboxWithLabel("Alat Pemadam Api Ringan", equip.portableExtinguishers) { onEquipChanged(equip.copy(portableExtinguishers = it)) }
                    CheckboxWithLabel("Instalasi Box Hydrant In Door", equip.indoorHydrantBox) { onEquipChanged(equip.copy(indoorHydrantBox = it)) }
                    CheckboxWithLabel("Instalasi Hydrant Pillar & Box Out Door", equip.pillarAndOutdoorHydrant) { onEquipChanged(equip.copy(pillarAndOutdoorHydrant = it)) }
                    CheckboxWithLabel("Siamese Connection", equip.siameseConnection) { onEquipChanged(equip.copy(siameseConnection = it)) }
                    CheckboxWithLabel("Instalasi Sprinkler", equip.sprinklerSystem) { onEquipChanged(equip.copy(sprinklerSystem = it)) }
                    CheckboxWithLabel("Instalasi Detektor Heat & Smoke", equip.heatAndSmokeDetectors) { onEquipChanged(equip.copy(heatAndSmokeDetectors = it)) }
                    CheckboxWithLabel("Petunjuk Arah", equip.exitSigns) { onEquipChanged(equip.copy(exitSigns = it)) }
                    CheckboxWithLabel("Tangga Darurat", equip.emergencyStairs) { onEquipChanged(equip.copy(emergencyStairs = it)) }
                    CheckboxWithLabel("Assembly Point", equip.assemblyPoint) { onEquipChanged(equip.copy(assemblyPoint = it)) }
                }
            }
        }

        item {
            val data = report.automaticFireAlarmSpecifications
            val onDataChanged: (FireProtectionAutomaticFireAlarmSpecifications) -> Unit = { onDataChange(report.copy(automaticFireAlarmSpecifications = it)) }
            FireProtectionExpandableSection("SPESIFIKASI ALARM KEBAKARAN AUTOMATIK") {
                FireProtectionExpandableSubSection("Panel Control (MCFA)") {
                    val mcfa = data.mcfa
                    val onMcfaChanged: (FireProtectionMcfa) -> Unit = { onDataChanged(data.copy(mcfa = it)) }
                    FireProtectionFormTextField("Merk / Type", mcfa.brandOrType) { onMcfaChanged(mcfa.copy(brandOrType = it)) }
                    FireProtectionFormTextField("Led Annunciator", mcfa.ledAnnunciator) { onMcfaChanged(mcfa.copy(ledAnnunciator = it)) }
                    FireProtectionFormTextField("Type", mcfa.type) { onMcfaChanged(mcfa.copy(type = it)) }
                    FireProtectionFormTextField("No. Serial", mcfa.serialNumber) { onMcfaChanged(mcfa.copy(serialNumber = it)) }
//                    FireProtectionFormTextField("Hasil", mcfa.result) { onMcfaChanged(mcfa.copy(result = it)) }
                    FireProtectionFormTextField("Keterangan", mcfa.remarks) { onMcfaChanged(mcfa.copy(remarks = it)) }
                }
                HorizontalDivider()
                FireProtectionExpandableSubSection("Heat Detector") {
                    val heat = data.heatDetector
                    val onHeatChanged: (FireProtectionDetector) -> Unit = { onDataChanged(data.copy(heatDetector = it)) }
                    FireProtectionFormTextField("Merk / Type", heat.brandOrType) { onHeatChanged(heat.copy(brandOrType = it)) }
                    FireProtectionFormTextField("Jumlah Titik", heat.pointCount) { onHeatChanged(heat.copy(pointCount = it)) }
                    FireProtectionFormTextField("Jarak Antar Titik (m)", heat.spacingM) { onHeatChanged(heat.copy(spacingM = it)) }
                    FireProtectionFormTextField("Temperatur Kerja (°C)", heat.operatingTemperatureC) { onHeatChanged(heat.copy(operatingTemperatureC = it)) }
//                    FireProtectionFormTextField("Hasil", heat.result) { onHeatChanged(heat.copy(result = it)) }
                    FireProtectionFormTextField("Keterangan", heat.remarks) { onHeatChanged(heat.copy(remarks = it)) }
                }
                HorizontalDivider()
                FireProtectionExpandableSubSection("Smoke Detector") {
                    val smoke = data.smokeDetector
                    val onSmokeChanged: (FireProtectionDetector) -> Unit = { onDataChanged(data.copy(smokeDetector = it)) }
                    FireProtectionFormTextField("Merk / Type", smoke.brandOrType) { onSmokeChanged(smoke.copy(brandOrType = it)) }
                    FireProtectionFormTextField("Jumlah Titik", smoke.pointCount) { onSmokeChanged(smoke.copy(pointCount = it)) }
                    FireProtectionFormTextField("Jarak Antar Titik (m)", smoke.spacingM) { onSmokeChanged(smoke.copy(spacingM = it)) }
                    FireProtectionFormTextField("Temperatur Kerja (°C)", smoke.operatingTemperatureC) { onSmokeChanged(smoke.copy(operatingTemperatureC = it)) }
//                    FireProtectionFormTextField("Hasil", smoke.result) { onSmokeChanged(smoke.copy(result = it)) }
                    FireProtectionFormTextField("Keterangan", smoke.remarks) { onSmokeChanged(smoke.copy(remarks = it)) }
                }
                HorizontalDivider()
                FireProtectionExpandableSubSection("Alat Pemadam Api Ringan (APAR)") {
                    val apar = data.apar
                    val onAparChanged: (FireProtectionApar) -> Unit = { onDataChanged(data.copy(apar = it)) }
                    FireProtectionFormTextField("Merk / Type", apar.brandOrType) { onAparChanged(apar.copy(brandOrType = it)) }
                    FireProtectionFormTextField("Jumlah", apar.count) { onAparChanged(apar.copy(count = it)) }
                    FireProtectionFormTextField("Jarak Antar Titik (m)", apar.spacingM) { onAparChanged(apar.copy(spacingM = it)) }
                    FireProtectionFormTextField("Penempatan", apar.placement) { onAparChanged(apar.copy(placement = it)) }
//                    FireProtectionFormTextField("Hasil", apar.result) { onAparChanged(apar.copy(result = it)) }
                    FireProtectionFormTextField("Keterangan", apar.remarks) { onAparChanged(apar.copy(remarks = it)) }
                }
            }
        }

        item {
            FireProtectionExpandableSection("DATA PEMASANGAN INSTALASI ALARM") {
                FilledTonalButton(onClick = { showAlarmInstallationDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Data Pemasangan")
                }
                report.alarmInstallationItems.forEachIndexed { index, item ->
                    FireProtectionListItemWithDelete(onDelete = { viewModel.deleteAlarmInstallationItem(index) }) { // Assumes ViewModel has this method
                        Column(Modifier.padding(8.dp)) {
                            Text("Lokasi: ${item.location} | Zone: ${item.zone}", fontWeight = FontWeight.SemiBold)
                            Text("Status: ${item.status}")
                        }
                    }
                }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                FireProtectionFormTextField(
                    "Total Pemeriksaan",
                    report.totalAlarmInstallation
                ) { onDataChange(report.copy(totalAlarmInstallation = it)) }

                FireProtectionFormTextField(
                    "Hasil (%)",
                    report.resultAlarmInstallation
                ) { onDataChange(report.copy(resultAlarmInstallation = it)) }
            }
        }

        item {
            val data = report.alarmInstallationTesting
            val onDataChanged: (FireProtectionAlarmInstallationTesting) -> Unit = { onDataChange(report.copy(alarmInstallationTesting = it)) }
            FireProtectionExpandableSection("PENGUJIAN INSTALASI ALARM") {
                FireProtectionFormTextField("Fungsi Kerja Panel", data.panelFunction) { onDataChanged(data.copy(panelFunction = it)) }
                FireProtectionFormTextField("Test Alarm", data.alarmTest) { onDataChanged(data.copy(alarmTest = it)) }
                FireProtectionFormTextField("Test Fault", data.faultTest) { onDataChanged(data.copy(faultTest = it)) }
                FireProtectionFormTextField("Test Interkoneksi", data.interconnectionTest) { onDataChanged(data.copy(interconnectionTest = it)) }
//                FireProtectionFormTextField("Catatan", data.notes) { onDataChanged(data.copy(notes = it)) }
            }
        }

        item {
            val data = report.hydrantSystemInstallation
            val onDataChanged: (FireProtectionHydrantSystemInstallation) -> Unit = { onDataChange(report.copy(hydrantSystemInstallation = it)) }
            FireProtectionExpandableSection("SISTEM PEMASANGAN INSTALASI HYDRAN") {
                SystemComponentInput("Sumber Air Baku", data.waterSource) { onDataChanged(data.copy(waterSource = it)) }

                FireProtectionExpandableSubSection("Ground Reservoar") {
                    val reservoir = data.groundReservoir
                    val onReservoirChanged: (FireProtectionGroundReservoir) -> Unit = { onDataChanged(data.copy(groundReservoir = it)) }
                    FireProtectionFormTextField("Perhitungan Kecukupan Cadangan", reservoir.calculation) { onReservoirChanged(reservoir.copy(calculation = it)) }
                    FireProtectionFormTextField("Kapasitas Cadangan (m³)", reservoir.backupCapacityM3) { onReservoirChanged(reservoir.copy(backupCapacityM3 = it)) }
                    FireProtectionFormTextField("Status", reservoir.status) { onReservoirChanged(reservoir.copy(status = it)) }
                    FireProtectionFormTextField("Keterangan", reservoir.remarks) { onReservoirChanged(reservoir.copy(remarks = it)) }
                }

                SystemComponentInput("Tangki Grafitasi", data.gravitationTank) { onDataChanged(data.copy(gravitationTank = it)) }
                SystemComponentInput("Siamese Connection", data.siameseConnection) { onDataChanged(data.copy(siameseConnection = it)) }

                FireProtectionExpandableSubSection("Pompa Pacu (Jockey)") {
                    val pump = data.jockeyPump
                    val onPumpChanged: (FireProtectionJockeyPump) -> Unit = { onDataChanged(data.copy(jockeyPump = it)) }
                    FireProtectionFormTextField("Model", pump.model) { onPumpChanged(pump.copy(model = it)) }
                    FireProtectionFormTextField("No. Serial", pump.serialNumber) { onPumpChanged(pump.copy(serialNumber = it)) }
                    FireProtectionFormTextField("Penggerak", pump.driver) { onPumpChanged(pump.copy(driver = it)) }
                    FireProtectionFormTextField("Daya/Putaran", pump.powerRpm) { onPumpChanged(pump.copy(powerRpm = it)) }
                    FireProtectionFormTextField("Penempatan", pump.placement) { onPumpChanged(pump.copy(placement = it)) }
                    FireProtectionFormTextField("Q (m³/H)", pump.quantityM3H) { onPumpChanged(pump.copy(quantityM3H = it)) }
                    FireProtectionFormTextField("H (meter)", pump.headM) { onPumpChanged(pump.copy(headM = it)) }
                    FireProtectionFormTextField("Start Otomatis (Kg/Cm²)", pump.autoStartKgCm2) { onPumpChanged(pump.copy(autoStartKgCm2 = it)) }
                    FireProtectionFormTextField("Stop Otomatis (Kg/Cm²)", pump.autoStopKgCm2) { onPumpChanged(pump.copy(autoStopKgCm2 = it)) }
                }

                FireProtectionExpandableSubSection("Pompa Utama (Electric)") {
                    val pump = data.electricPump
                    val onPumpChanged: (FireProtectionElectricPump) -> Unit = { onDataChanged(data.copy(electricPump = it)) }
                    FireProtectionFormTextField("Model", pump.model) { onPumpChanged(pump.copy(model = it)) }
                    FireProtectionFormTextField("No. Serial", pump.serialNumber) { onPumpChanged(pump.copy(serialNumber = it)) }
                    FireProtectionFormTextField("Penggerak", pump.driver) { onPumpChanged(pump.copy(driver = it)) }
                    FireProtectionFormTextField("Daya/Putaran", pump.powerRpm) { onPumpChanged(pump.copy(powerRpm = it)) }
                    FireProtectionFormTextField("Penempatan", pump.placement) { onPumpChanged(pump.copy(placement = it)) }
                    FireProtectionFormTextField("Q (Gpm)", pump.quantityGpm) { onPumpChanged(pump.copy(quantityGpm = it)) }
                    FireProtectionFormTextField("H (meter)", pump.headM) { onPumpChanged(pump.copy(headM = it)) }
                    FireProtectionFormTextField("Start Otomatis (Kg/Cm²)", pump.autoStartKgCm2) { onPumpChanged(pump.copy(autoStartKgCm2 = it)) }
                    FireProtectionFormTextField("Stop", pump.stop) { onPumpChanged(pump.copy(stop = it)) }
                }

                FireProtectionExpandableSubSection("Pompa Cadangan (Diesel)") {
                    val pump = data.dieselPump
                    val onPumpChanged: (FireProtectionDieselPump) -> Unit = { onDataChanged(data.copy(dieselPump = it)) }
                    FireProtectionFormTextField("Model", pump.model) { onPumpChanged(pump.copy(model = it)) }
                    FireProtectionFormTextField("No. Serial", pump.serialNumber) { onPumpChanged(pump.copy(serialNumber = it)) }
                    FireProtectionFormTextField("Penggerak", pump.driver) { onPumpChanged(pump.copy(driver = it)) }
                    FireProtectionFormTextField("Q (US Gpm)", pump.quantityUsGpm) { onPumpChanged(pump.copy(quantityUsGpm = it)) }
                    FireProtectionFormTextField("H (meter)", pump.headM) { onPumpChanged(pump.copy(headM = it)) }
                    FireProtectionFormTextField("Start Otomatis (Kg/Cm²)", pump.autoStartKgCm2) { onPumpChanged(pump.copy(autoStartKgCm2 = it)) }
                    FireProtectionFormTextField("Stop", pump.stop) { onPumpChanged(pump.copy(stop = it)) }
                }

                FireProtectionExpandableSubSection("Hydrant Gedung") {
                    val hydrant = data.buildingHydrant
                    val onHydrantChanged: (FireProtectionBuildingHydrant) -> Unit = { onDataChanged(data.copy(buildingHydrant = it)) }
                    FireProtectionFormTextField("Jumlah Titik", hydrant.points) { onHydrantChanged(hydrant.copy(points = it)) }
                    FireProtectionFormTextField("Diameter Keluaran (Inch)", hydrant.outletDiameterInch) { onHydrantChanged(hydrant.copy(outletDiameterInch = it)) }
                    FireProtectionFormTextField("Panjang Selang (m)", hydrant.hoseLengthM) { onHydrantChanged(hydrant.copy(hoseLengthM = it)) }
                    FireProtectionFormTextField("Diameter Nozzle (Inch)", hydrant.nozzleDiameterInch) { onHydrantChanged(hydrant.copy(nozzleDiameterInch = it)) }
                    FireProtectionFormTextField("Penempatan", hydrant.placement) { onHydrantChanged(hydrant.copy(placement = it)) }
                }

                FireProtectionExpandableSubSection("Landing Valve") {
                    val valve = data.landingValve
                    val onValveChanged: (FireProtectionLandingValve) -> Unit = { onDataChanged(data.copy(landingValve = it)) }
                    FireProtectionFormTextField("Jumlah Titik", valve.points) { onValveChanged(valve.copy(points = it)) }
                    FireProtectionFormTextField("Diameter Keluaran (Inch)", valve.outletDiameterInch) { onValveChanged(valve.copy(outletDiameterInch = it)) }
                    FireProtectionFormTextField("Jenis Kopling", valve.couplingType) { onValveChanged(valve.copy(couplingType = it)) }
                    FireProtectionFormTextField("Penempatan", valve.placement) { onValveChanged(valve.copy(placement = it)) }
                }

                FireProtectionExpandableSubSection("Hydrant Halaman") {
                    val hydrant = data.yardHydrant
                    val onHydrantChanged: (FireProtectionYardHydrant) -> Unit = { onDataChanged(data.copy(yardHydrant = it)) }
                    FireProtectionFormTextField("Jumlah Titik", hydrant.points) { onHydrantChanged(hydrant.copy(points = it)) }
                    FireProtectionFormTextField("Diameter Keluaran (Inch)", hydrant.outletDiameterInch) { onHydrantChanged(hydrant.copy(outletDiameterInch = it)) }
                    FireProtectionFormTextField("Panjang Selang (m)", hydrant.hoseLengthM) { onHydrantChanged(hydrant.copy(hoseLengthM = it)) }
                    FireProtectionFormTextField("Diameter Nozzle (Inch)", hydrant.nozzleDiameterInch) { onHydrantChanged(hydrant.copy(nozzleDiameterInch = it)) }
                    FireProtectionFormTextField("Penempatan", hydrant.placement) { onHydrantChanged(hydrant.copy(placement = it)) }
                }

                FireProtectionExpandableSubSection("Sambungan Dinas Kebakaran") {
                    val conn = data.fireServiceConnection
                    val onConnChanged: (FireProtectionFireServiceConnection) -> Unit = { onDataChanged(data.copy(fireServiceConnection = it)) }
                    FireProtectionFormTextField("Jumlah Titik", conn.points) { onConnChanged(conn.copy(points = it)) }
                    FireProtectionFormTextField("Diameter Inlet (Inch)", conn.inletDiameterInch) { onConnChanged(conn.copy(inletDiameterInch = it)) }
                    FireProtectionFormTextField("Diameter Keluaran (Inch)", conn.outletDiameterInch) { onConnChanged(conn.copy(outletDiameterInch = it)) }
                    FireProtectionFormTextField("Jenis Kopling", conn.couplingType) { onConnChanged(conn.copy(couplingType = it)) }
                    FireProtectionFormTextField("Kondisi", conn.condition) { onConnChanged(conn.copy(condition = it)) }
                    FireProtectionFormTextField("Penempatan", conn.placement) { onConnChanged(conn.copy(placement = it)) }
                }

                SystemComponentInput("Pipa Hisap", data.suctionPipe) { onDataChanged(data.copy(suctionPipe = it)) }
                SystemComponentInput("Pipa Penyalur Utama", data.mainPipe) { onDataChanged(data.copy(mainPipe = it)) }
                SystemComponentInput("Pipa Tegak", data.standPipe) { onDataChanged(data.copy(standPipe = it)) }
                SystemComponentInput("Hidran Pilar / Halaman", data.hydrantPillar) { onDataChanged(data.copy(hydrantPillar = it)) }
                SystemComponentInput("Hidran Dalam Gedung", data.indoorHydrant) { onDataChanged(data.copy(indoorHydrant = it)) }
                SystemComponentInput("Hose Rell", data.hoseReel) { onDataChanged(data.copy(hoseReel = it)) }
                SystemComponentInput("Pressure Relief Valve", data.pressureReliefValve) { onDataChanged(data.copy(pressureReliefValve = it)) }
                SystemComponentInput("Test Valve", data.testValve) { onDataChanged(data.copy(testValve = it)) }
            }
        }

        item {
            FireProtectionExpandableSection("UJI FUNGSI POMPA") {
                FilledTonalButton(onClick = { showPumpTestDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Data Uji Pompa")
                }
                report.pumpFunctionTest.forEachIndexed { index, item ->
                    FireProtectionListItemWithDelete(onDelete = { viewModel.deletePumpFunctionTestItem(index) }) {
                        Text("Jenis: ${item.pumpType}, Start: ${item.startPressure}, Stop: ${item.stopPressure}", modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }

        item {
            FireProtectionExpandableSection("UJI OPERASIONAL HYDRAN") {
                FilledTonalButton(onClick = { showHydrantTestDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Data Uji Hydran")
                }
                report.hydrantOperationalTest.forEachIndexed { index, item ->
                    FireProtectionListItemWithDelete(onDelete = { viewModel.deleteHydrantOperationalTestItem(index) }) {
                        Column(Modifier.padding(8.dp)) {
                            Text("Titik: ${item.testPoint} - Status: ${item.status}", fontWeight = FontWeight.SemiBold)
                            Text("Tekanan: ${item.pressure}, Pancaran: ${item.jetLength}, Posisi: ${item.nozzlePosition}")
                            Text("Ket: ${item.remarks}")
                        }
                    }
                }
            }
        }

        item {
            val data = report.conclusion
            FireProtectionExpandableSection("KESIMPULAN DAN SYARAT") {
                FireProtectionFormTextField("Kesimpulan", data.summary) { onDataChange(report.copy(conclusion = data.copy(summary = it))) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                FilledTonalButton(onClick = { showRecommendationDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Syarat / Rekomendasi")
                }
                data.recommendations.forEachIndexed { index, item ->
                    FireProtectionListItemWithDelete(onDelete = { viewModel.removeConclusionRecommendation(index) }) {
                        Text(item, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}


//region Reusable Composables

@Composable
private fun FireProtectionListItemWithDelete(
    onDelete: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.fillMaxWidth(), content = content)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Hapus Item")
        }
    }
}

@Composable
private fun FireProtectionExpandableSection(
    title: String,
    initiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(initiallyExpanded) }
    Card(
        modifier = Modifier.fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium).clickable { expanded = !expanded }.padding(vertical = 4.dp),
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
private fun FireProtectionExpandableSubSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth().animateContentSize(
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        ).padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium).clickable { expanded = !expanded }.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
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
private fun FireProtectionFormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
private fun ChecklistResultInput(
    label: String,
    value: FireProtectionChecklistResult,
    onValueChange: (FireProtectionChecklistResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isAvailable = true)) }) {
                RadioButton(selected = value.isAvailable, onClick = null)
                Text("Ada")
            }
            Spacer(Modifier.width(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isAvailable = false)) }) {
                RadioButton(selected = !value.isAvailable, onClick = null)
                Text("Tidak Ada")
            }
        }
        FireProtectionFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

@Composable
private fun CheckboxWithLabel(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.small).clickable { onCheckedChange(!checked) }.padding(horizontal = 4.dp)
    ) {
        Checkbox(checked = checked, onCheckedChange = null)
        Spacer(Modifier.width(8.dp))
        Text(label)
    }
}

@Composable
private fun SystemComponentInput(
    label: String,
    value: FireProtectionSystemComponent,
    onValueChange: (FireProtectionSystemComponent) -> Unit
) {
    FireProtectionExpandableSubSection(label) {
        FireProtectionFormTextField("Spesifikasi", value.specification) { onValueChange(value.copy(specification = it)) }
        FireProtectionFormTextField("Status", value.status) { onValueChange(value.copy(status = it)) }
        FireProtectionFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

//endregion