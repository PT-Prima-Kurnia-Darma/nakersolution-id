package com.nakersolutionid.nakersolutionid.features.report.pubt.general

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
import com.nakersolutionid.nakersolutionid.features.report.pubt.MeasurementResultType
import com.nakersolutionid.nakersolutionid.features.report.pubt.PUBTViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GeneralScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: PUBTViewModel = koinViewModel()
) {
    val uiState by viewModel.generalUiState.collectAsStateWithLifecycle()
    val report = uiState.inspectionReport
    val onDataChange = viewModel::onGeneralReportChange

    var showMeasurementDialog by remember { mutableStateOf(false) }
    var showSummaryDialog by remember { mutableStateOf(false) }
    var showRecommendationDialog by remember { mutableStateOf(false) }

    if (showSummaryDialog) {
        AddGeneralStringDialog("Tambah Poin Kesimpulan", "Poin Kesimpulan", { showSummaryDialog = false }) {
            viewModel.addConclusionSummary(it)
            showSummaryDialog = false
        }
    }

    if (showRecommendationDialog) {
        AddGeneralStringDialog("Tambah Poin Rekomendasi", "Poin Rekomendasi", { showRecommendationDialog = false }) {
            viewModel.addConclusionRecommendation(it)
            showRecommendationDialog = false
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        item {
            val data = report
            GeneralExpandableSection("DATA UTAMA", true) {
                GeneralFormTextField(label = "Jenis Pemeriksaan", value = data.examinationType, onValueChange = { onDataChange(data.copy(examinationType = it)) })
                GeneralFormTextField(label = "Jenis Pesawat Uap", value = data.equipmentType, onValueChange = { onDataChange(data.copy(equipmentType = it)) })
            }
        }

        item {
            val data = report.generalData
            val onDataChanged: (GeneralData) -> Unit = { onDataChange(report.copy(generalData = it)) }
            GeneralExpandableSection("DATA UMUM", false) {
                GeneralFormTextField("Nama Perusahaan / Pemilik", data.ownerName) { onDataChanged(data.copy(ownerName = it)) }
                GeneralFormTextField("Alamat Perusahaan", data.ownerAddress) { onDataChanged(data.copy(ownerAddress = it)) }
                GeneralFormTextField("Pemakai", data.user) { onDataChanged(data.copy(user = it)) }
                GeneralFormTextField("Alamat Pemakai", data.userAddress) { onDataChanged(data.copy(userAddress = it)) }
                GeneralFormTextField("Nama Operator", data.operatorName) { onDataChanged(data.copy(operatorName = it)) }
                GeneralFormTextField("Jenis Pesawat Uap", data.driveType) { onDataChanged(data.copy(driveType = it)) }
                GeneralFormTextField("Pabrik Pembuat", data.manufacturer) { onDataChanged(data.copy(manufacturer = it)) }
                GeneralFormTextField("Merk/Model/Type", data.brandModelType) { onDataChanged(data.copy(brandModelType = it)) }
                GeneralFormTextField("Negara & Tahun Pembuatan", data.countryAndYearOfManufacture) { onDataChanged(data.copy(countryAndYearOfManufacture = it)) }
                GeneralFormTextField("Nomor Seri / Nomor unit", data.serialNumber) { onDataChanged(data.copy(serialNumber = it)) }
                GeneralFormTextField("Design Pressure (kg/cm²)", data.designPressureKgCm2) { onDataChanged(data.copy(designPressureKgCm2 = it)) }
                GeneralFormTextField("Tekanan Kerja Max (kg/cm²)", data.maxAllowableWorkingPressureKgCm2) { onDataChanged(data.copy(maxAllowableWorkingPressureKgCm2 = it)) }
                GeneralFormTextField("Kapasitas (Kg/h)", data.capacityKgH) { onDataChanged(data.copy(capacityKgH = it)) }
                GeneralFormTextField("Steam Temperature", data.steamTemperature) { onDataChanged(data.copy(steamTemperature = it)) }
                GeneralFormTextField("Tekanan Kerja Pemakaian (kg/cm²)", data.operatingPressureKgCm2) { onDataChanged(data.copy(operatingPressureKgCm2 = it)) }
                GeneralFormTextField("Jenis Bahan Bakar", data.fuelType) { onDataChanged(data.copy(fuelType = it)) }
                GeneralFormTextField("Digunakan Untuk", data.intendedUse) { onDataChanged(data.copy(intendedUse = it)) }
                GeneralFormTextField("Akte Izin Nomor", data.permitNumber) { onDataChanged(data.copy(permitNumber = it)) }
                GeneralFormTextField("Sertifikat Operator", data.operatorCertificate) { onDataChanged(data.copy(operatorCertificate = it)) }
                GeneralFormTextField("Data Riwayat Pesawat", data.equipmentHistory) { onDataChanged(data.copy(equipmentHistory = it)) }
                GeneralFormTextField("Tanggal Pelaksanaan", data.inspectionDate) { onDataChanged(data.copy(inspectionDate = it)) }
            }
        }

        item {
            val data = report.technicalData
            val onDataChanged: (GeneralTechnicalData) -> Unit = { onDataChange(report.copy(technicalData = it)) }
            GeneralExpandableSection("DATA TEKNIS") {
                GeneralExpandableSubSection("Badan / Shell") {
                    val shell = data.shell
                    val onShellChanged: (GeneralShell) -> Unit = { onDataChanged(data.copy(shell = it)) }
                    GeneralFormTextField("Jumlah Round Shell", shell.numberOfRounds) { onShellChanged(shell.copy(numberOfRounds = it)) }
                    GeneralFormTextField("Cara Penyambungan", shell.connectionMethod) { onShellChanged(shell.copy(connectionMethod = it)) }
                    GeneralFormTextField("Material / Bahan", shell.material) { onShellChanged(shell.copy(material = it)) }
                    GeneralFormTextField("Diameter Pipa (mm)", shell.pipeDiameterMm) { onShellChanged(shell.copy(pipeDiameterMm = it)) }
                    GeneralFormTextField("Ketebalan (mm)", shell.thicknessMm) { onShellChanged(shell.copy(thicknessMm = it)) }
                    GeneralFormTextField("Panjang Badan (mm)", shell.bodyLengthMm) { onShellChanged(shell.copy(bodyLengthMm = it)) }
                }
                GeneralExpandableSubSection("Tutup (Heads)") {
                    val heads = data.heads
                    val onHeadsChanged: (GeneralHeads) -> Unit = { onDataChanged(data.copy(heads = it)) }
                    GeneralFormTextField("Jenis", heads.type) { onHeadsChanged(heads.copy(type = it)) }
                    GeneralFormTextField("Diameter Atas (mm)", heads.topDiameterMm) { onHeadsChanged(heads.copy(topDiameterMm = it)) }
                    GeneralFormTextField("Ketebalan Atas (mm)", heads.topThicknessMm) { onHeadsChanged(heads.copy(topThicknessMm = it)) }
                    GeneralFormTextField("Diameter Belakang (mm)", heads.rearDiameterMm) { onHeadsChanged(heads.copy(rearDiameterMm = it)) }
                    GeneralFormTextField("Ketebalan Belakang (mm)", heads.rearThicknessMm) { onHeadsChanged(heads.copy(rearThicknessMm = it)) }
                }
                GeneralExpandableSubSection("Tube Plate") {
                    val plate = data.tubePlate
                    val onPlateChanged: (GeneralTubePlate) -> Unit = { onDataChanged(data.copy(tubePlate = it)) }
                    GeneralFormTextField("Dimensi Depan 1 (mm)", plate.frontDim1Mm) { onPlateChanged(plate.copy(frontDim1Mm = it)) }
                    GeneralFormTextField("Dimensi Depan 2 (mm)", plate.frontDim2Mm) { onPlateChanged(plate.copy(frontDim2Mm = it)) }
                    GeneralFormTextField("Dimensi Belakang 1 (mm)", plate.rearDim1Mm) { onPlateChanged(plate.copy(rearDim1Mm = it)) }
                    GeneralFormTextField("Dimensi Belakang 2 (mm)", plate.rearDim2Mm) { onPlateChanged(plate.copy(rearDim2Mm = it)) }
                }
                GeneralExpandableSubSection("Lorong Api (Furnace)") {
                    val furnace = data.furnace
                    val onFurnaceChanged: (GeneralFurnace) -> Unit = { onDataChanged(data.copy(furnace = it)) }
                    GeneralFormTextField("Jenis", furnace.type) { onFurnaceChanged(furnace.copy(type = it)) }
                    GeneralFormTextField("Material / Bahan", furnace.material) { onFurnaceChanged(furnace.copy(material = it)) }
                    GeneralFormTextField("Diameter Luar (mm)", furnace.outerDiameterMm) { onFurnaceChanged(furnace.copy(outerDiameterMm = it)) }
                    GeneralFormTextField("Diameter Dalam (mm)", furnace.innerDiameterMm) { onFurnaceChanged(furnace.copy(innerDiameterMm = it)) }
                    GeneralFormTextField("Ketebalan (mm)", furnace.thicknessMm) { onFurnaceChanged(furnace.copy(thicknessMm = it)) }
                }
                GeneralExpandableSubSection("Pipa Air (Water Tubes)") {
                    val tubes = data.waterTubes
                    val onTubesChanged: (GeneralWaterTubes) -> Unit = { onDataChanged(data.copy(waterTubes = it)) }
                    Text("Jalur Pertama / First Pass", fontWeight = FontWeight.SemiBold)
                    GeneralFormTextField("Diameter (mm)", tubes.firstPass.diameterMm) { onTubesChanged(tubes.copy(firstPass = tubes.firstPass.copy(diameterMm = it))) }
                    GeneralFormTextField("Ketebalan (mm)", tubes.firstPass.thicknessMm) { onTubesChanged(tubes.copy(firstPass = tubes.firstPass.copy(thicknessMm = it))) }
                    GeneralFormTextField("Panjang (mm)", tubes.firstPass.lengthMm) { onTubesChanged(tubes.copy(firstPass = tubes.firstPass.copy(lengthMm = it))) }
                    GeneralFormTextField("Jumlah", tubes.firstPass.quantity) { onTubesChanged(tubes.copy(firstPass = tubes.firstPass.copy(quantity = it))) }
                    HorizontalDivider(Modifier.padding(vertical = 4.dp))
                    Text("Jalur Kedua / Second Pass", fontWeight = FontWeight.SemiBold)
                    GeneralFormTextField("Diameter (mm)", tubes.secondPass.diameterMm) { onTubesChanged(tubes.copy(secondPass = tubes.secondPass.copy(diameterMm = it))) }
                    GeneralFormTextField("Ketebalan (mm)", tubes.secondPass.thicknessMm) { onTubesChanged(tubes.copy(secondPass = tubes.secondPass.copy(thicknessMm = it))) }
                    GeneralFormTextField("Panjang (mm)", tubes.secondPass.lengthMm) { onTubesChanged(tubes.copy(secondPass = tubes.secondPass.copy(lengthMm = it))) }
                    GeneralFormTextField("Jumlah", tubes.secondPass.quantity) { onTubesChanged(tubes.copy(secondPass = tubes.secondPass.copy(quantity = it))) }
                    HorizontalDivider(Modifier.padding(vertical = 4.dp))
                    Text("Stay Tube", fontWeight = FontWeight.SemiBold)
                    GeneralFormTextField("Diameter (mm)", tubes.stayTube.diameterMm) { onTubesChanged(tubes.copy(stayTube = tubes.stayTube.copy(diameterMm = it))) }
                    GeneralFormTextField("Ketebalan (mm)", tubes.stayTube.thicknessMm) { onTubesChanged(tubes.copy(stayTube = tubes.stayTube.copy(thicknessMm = it))) }
                    GeneralFormTextField("Panjang (mm)", tubes.stayTube.lengthMm) { onTubesChanged(tubes.copy(stayTube = tubes.stayTube.copy(lengthMm = it))) }
                    GeneralFormTextField("Jumlah", tubes.stayTube.quantity) { onTubesChanged(tubes.copy(stayTube = tubes.stayTube.copy(quantity = it))) }
                    HorizontalDivider(Modifier.padding(vertical = 4.dp))
                    Text("Material", fontWeight = FontWeight.SemiBold)
                    GeneralFormTextField("Diameter (mm)", tubes.material.diameterMm) { onTubesChanged(tubes.copy(material = tubes.material.copy(diameterMm = it))) }
                    GeneralFormTextField("Ketebalan (mm)", tubes.material.thicknessMm) { onTubesChanged(tubes.copy(material = tubes.material.copy(thicknessMm = it))) }
                    GeneralFormTextField("Panjang (mm)", tubes.material.lengthMm) { onTubesChanged(tubes.copy(material = tubes.material.copy(lengthMm = it))) }
                    GeneralFormTextField("Jumlah", tubes.material.quantity) { onTubesChanged(tubes.copy(material = tubes.material.copy(quantity = it))) }
                    GeneralFormTextField("Cara Penyambungan", tubes.connectionMethod) { onTubesChanged(tubes.copy(connectionMethod = it)) }
                }
            }
        }

        item {
            val data = report.inspectionAndMeasurement
            val onDataChanged: (GeneralInspectionAndMeasurement) -> Unit = { onDataChange(report.copy(inspectionAndMeasurement = it)) }
            GeneralExpandableSection("PEMERIKSAAN & PENGUKURAN") {
                GeneralExpandableSubSection("Pemeriksaan Visual") {
                    val visual = data.visualInspection
                    val onVisualChanged: (GeneralVisualInspection) -> Unit = { onDataChanged(data.copy(visualInspection = it)) }
                    Text("Komponen Pesawat Uap", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    CheckResultInput("a. Badan / Drum", visual.steamEquipmentShellDrum) { onVisualChanged(visual.copy(steamEquipmentShellDrum = it)) }
                    CheckResultInput("b. Bouileur", visual.steamEquipmentBouileur) { onVisualChanged(visual.copy(steamEquipmentBouileur = it)) }
                    CheckResultInput("c. Lorong Api / Furnace", visual.steamEquipmentFurnace) { onVisualChanged(visual.copy(steamEquipmentFurnace = it)) }
                    CheckResultInput("d. Peti Api / Fire Chamber", visual.steamEquipmentFireChamber) { onVisualChanged(visual.copy(steamEquipmentFireChamber = it)) }
                    CheckResultInput("e. Salut Peti Api / Batu Tahan Api", visual.steamEquipmentRefractory) { onVisualChanged(visual.copy(steamEquipmentRefractory = it)) }
                    CheckResultInput("f. Kamar Nyala", visual.steamEquipmentCombustionChamber) { onVisualChanged(visual.copy(steamEquipmentCombustionChamber = it)) }
                    CheckResultInput("g. Pipa-pipa Api", visual.steamEquipmentFireTubes) { onVisualChanged(visual.copy(steamEquipmentFireTubes = it)) }
                    CheckResultInput("h. Super Heater", visual.steamEquipmentSuperHeater) { onVisualChanged(visual.copy(steamEquipmentSuperHeater = it)) }
                    CheckResultInput("i. Reheater", visual.steamEquipmentReheater) { onVisualChanged(visual.copy(steamEquipmentReheater = it)) }
                    CheckResultInput("j. Ekonomizer", visual.steamEquipmentEconomizer) { onVisualChanged(visual.copy(steamEquipmentEconomizer = it)) }

                    Text("Keterangan Ketel Uap", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    CheckResultInput("a. Kisi / Sarang", visual.boilerDetailsGrate) { onVisualChanged(visual.copy(boilerDetailsGrate = it)) }
                    CheckResultInput("b. Alat Pembakar", visual.boilerDetailsBurner) { onVisualChanged(visual.copy(boilerDetailsBurner = it)) }
                    CheckResultInput("c. Kipas Tekanan (FDF)", visual.boilerDetailsFdf) { onVisualChanged(visual.copy(boilerDetailsFdf = it)) }
                    CheckResultInput("d. Kipas Isap (IDF)", visual.boilerDetailsIdf) { onVisualChanged(visual.copy(boilerDetailsIdf = it)) }
                    CheckResultInput("e. Pemanas udara Pembakar", visual.boilerDetailsAirHeater) { onVisualChanged(visual.copy(boilerDetailsAirHeater = it)) }
                    CheckResultInput("f. Penyalur Udara Pembakaran", visual.boilerDetailsAirDuct) { onVisualChanged(visual.copy(boilerDetailsAirDuct = it)) }
                    CheckResultInput("g. Penyalur Gas Pembakaran", visual.boilerDetailsGasDuct) { onVisualChanged(visual.copy(boilerDetailsGasDuct = it)) }
                    CheckResultInput("h. Unit Penangkap Abu Terbang", visual.boilerDetailsAshCatcher) { onVisualChanged(visual.copy(boilerDetailsAshCatcher = it)) }
                    CheckResultInput("i. Cerobong Asap", visual.boilerDetailsChimney) { onVisualChanged(visual.copy(boilerDetailsChimney = it)) }
                    CheckResultInput("j. Tangga-tangga / Bordes", visual.boilerDetailsStairs) { onVisualChanged(visual.copy(boilerDetailsStairs = it)) }
                    CheckResultInput("k. Salut / Isolasi", visual.boilerDetailsInsulation) { onVisualChanged(visual.copy(boilerDetailsInsulation = it)) }

                    Text("Alat-alat Pengaman", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    CheckResultInput("Tingkap Pengaman: Cincin Pengatur", visual.safetyValveRing) { onVisualChanged(visual.copy(safetyValveRing = it)) }
                    CheckResultInput("Tingkap Pengaman: Pipa Pengaman", visual.safetyValvePipe) { onVisualChanged(visual.copy(safetyValvePipe = it)) }
                    CheckResultInput("Tingkap Pengaman: Pipa Pembuang Asap", visual.safetyValveExhaust) { onVisualChanged(visual.copy(safetyValveExhaust = it)) }
                    CheckResultInput("Pedoman Tekanan: Tanda Tekanan Tinggi", visual.pressureGaugeMark) { onVisualChanged(visual.copy(pressureGaugeMark = it)) }
                    CheckResultInput("Pedoman Tekanan: Pipa Lengkung", visual.pressureGaugeSiphon) { onVisualChanged(visual.copy(pressureGaugeSiphon = it)) }
                    CheckResultInput("Pedoman Tekanan: Kerangan Cabang Tiga", visual.pressureGaugeCock) { onVisualChanged(visual.copy(pressureGaugeCock = it)) }
                    CheckResultInput("Gelas Pedoman Air: Cerat Duga", visual.gaugeGlassTryCocks) { onVisualChanged(visual.copy(gaugeGlassTryCocks = it)) }
                    CheckResultInput("Gelas Pedoman Air: Kerangka Sembur", visual.gaugeGlassBlowdown) { onVisualChanged(visual.copy(gaugeGlassBlowdown = it)) }
                    CheckResultInput("Tanda Batas Air: Tanda Batas Terendah", visual.waterLevelLowestMark) { onVisualChanged(visual.copy(waterLevelLowestMark = it)) }
                    CheckResultInput("Tanda Batas Air: Letaknya di Atas Garis Api", visual.waterLevelPosition) { onVisualChanged(visual.copy(waterLevelPosition = it)) }
                    CheckResultInput("Alat Pengisi Api: Pompa Pengisi", visual.feedwaterPump) { onVisualChanged(visual.copy(feedwaterPump = it)) }
                    CheckResultInput("Alat Pengisi Api: Kapasitas Pompa", visual.feedwaterCapacity) { onVisualChanged(visual.copy(feedwaterCapacity = it)) }
                    CheckResultInput("Alat Pengisi Api: Tenaga Penggerak", visual.feedwaterMotor) { onVisualChanged(visual.copy(feedwaterMotor = it)) }
                    CheckResultInput("Alat Pengisi Api: Tingkap Balik", visual.feedwaterCheckValve) { onVisualChanged(visual.copy(feedwaterCheckValve = it)) }
                    CheckResultInput("Peralatan Pengontrol: Blacks Fluit", visual.controlBlacksFluit) { onVisualChanged(visual.copy(controlBlacksFluit = it)) }
                    CheckResultInput("Peralatan Pengontrol: Sumbat Timah", visual.controlFusiblePlug) { onVisualChanged(visual.copy(controlFusiblePlug = it)) }
                    CheckResultInput("Peralatan Pengontrol: Kontrol Permukaan Air", visual.controlWaterLevel) { onVisualChanged(visual.copy(controlWaterLevel = it)) }
                    CheckResultInput("Peralatan Pengontrol: Kontrol Tekanan Uap", visual.controlSteamPressure) { onVisualChanged(visual.copy(controlSteamPressure = it)) }
                    CheckResultInput("Katup Pembuangan: Keterangan", visual.blowdownDesc) { onVisualChanged(visual.copy(blowdownDesc = it)) }
                    CheckResultInput("Katup Pembuangan: Bahan Kerangan", visual.blowdownMaterial) { onVisualChanged(visual.copy(blowdownMaterial = it)) }
                    CheckResultInput("Lubang Lalu Orang: Lubang Lalu Orang", visual.manholeManhole) { onVisualChanged(visual.copy(manholeManhole = it)) }
                    CheckResultInput("Lubang Lalu Orang: Lubang Pemeriksaan", visual.manholeInspectionHole) { onVisualChanged(visual.copy(manholeInspectionHole = it)) }
                    CheckResultInput("Tanda Pengenal: Pelat Nama", visual.idMarkNameplate) { onVisualChanged(visual.copy(idMarkNameplate = it)) }
                    CheckResultInput("Tanda Pengenal: Data Sesuai", visual.idMarkDataMatch) { onVisualChanged(visual.copy(idMarkDataMatch = it)) }
                    CheckResultInput("Tanda Pengenal: Cap Bentuk 9/9a", visual.idMarkForm9Stamp) { onVisualChanged(visual.copy(idMarkForm9Stamp = it)) }
                }
                HorizontalDivider()
                GeneralExpandableSubSection("Pengukuran Ketebalan Bahan") {
                    val thickness = data.materialThickness
                    val onThicknessChanged: (GeneralMaterialThickness) -> Unit = { onDataChanged(data.copy(materialThickness = it)) }
                    ValueResultInput("Badan/Shell: Ketebalan (mm)", thickness.shellThicknessMm) { onThicknessChanged(thickness.copy(shellThicknessMm = it)) }
                    ValueResultInput("Badan/Shell: Diameter (mm)", thickness.shellDiameterMm) { onThicknessChanged(thickness.copy(shellDiameterMm = it)) }
                    ValueResultInput("Header: Diameter (mm)", thickness.headerDiameterMm) { onThicknessChanged(thickness.copy(headerDiameterMm = it)) }
                    ValueResultInput("Header: Ketebalan (mm)", thickness.headerThicknessMm) { onThicknessChanged(thickness.copy(headerThicknessMm = it)) }
                    ValueResultInput("Header: Panjang (mm)", thickness.headerLengthMm) { onThicknessChanged(thickness.copy(headerLengthMm = it)) }
                    ValueResultInput("Furnace 1: Diameter (mm)", thickness.furnace1DiameterMm) { onThicknessChanged(thickness.copy(furnace1DiameterMm = it)) }
                    ValueResultInput("Furnace 1: Ketebalan (mm)", thickness.furnace1ThicknessMm) { onThicknessChanged(thickness.copy(furnace1ThicknessMm = it)) }
                    ValueResultInput("Furnace 1: Panjang (mm)", thickness.furnace1LengthMm) { onThicknessChanged(thickness.copy(furnace1LengthMm = it)) }
                    ValueResultInput("Furnace 2: Diameter (mm)", thickness.furnace2DiameterMm) { onThicknessChanged(thickness.copy(furnace2DiameterMm = it)) }
                    ValueResultInput("Furnace 2: Ketebalan (mm)", thickness.furnace2ThicknessMm) { onThicknessChanged(thickness.copy(furnace2ThicknessMm = it)) }
                    ValueResultInput("Furnace 2: Panjang (mm)", thickness.furnace2LengthMm) { onThicknessChanged(thickness.copy(furnace2LengthMm = it)) }
                }
                HorizontalDivider()
                GeneralExpandableSubSection("Pengaturan Pengukuran Ketebalan") {
                    val setup = data.thicknessMeasurementSetup
                    val onSetupChanged: (GeneralThicknessMeasurementSetup) -> Unit = { onDataChanged(data.copy(thicknessMeasurementSetup = it)) }
                    GeneralFormTextField("Pemilik", setup.owner) { onSetupChanged(setup.copy(owner = it)) }
                    GeneralFormTextField("Tanggal Pemeriksaan", setup.inspectionDate) { onSetupChanged(setup.copy(inspectionDate = it)) }
                    GeneralFormTextField("Project", setup.project) { onSetupChanged(setup.copy(project = it)) }
                    GeneralFormTextField("Jenis Objek K3", setup.objectType) { onSetupChanged(setup.copy(objectType = it)) }
                    GeneralFormTextField("Work Order No.", setup.workOrderNo) { onSetupChanged(setup.copy(workOrderNo = it)) }
                    GeneralFormTextField("Equipment Used", setup.equipmentUsed) { onSetupChanged(setup.copy(equipmentUsed = it)) }
                    GeneralFormTextField("Methode Used", setup.methodUsed) { onSetupChanged(setup.copy(methodUsed = it)) }
                    GeneralFormTextField("Type Of Probe Used", setup.probeType) { onSetupChanged(setup.copy(probeType = it)) }
                    GeneralFormTextField("Material Type", setup.materialType) { onSetupChanged(setup.copy(materialType = it)) }
                    GeneralFormTextField("Probe Style", setup.probeStyle) { onSetupChanged(setup.copy(probeStyle = it)) }
                    GeneralFormTextField("Operating Temp", setup.operatingTemp) { onSetupChanged(setup.copy(operatingTemp = it)) }
                    GeneralFormTextField("Surface Condition", setup.surfaceCondition) { onSetupChanged(setup.copy(surfaceCondition = it)) }
                    GeneralFormTextField("Welding Process", setup.weldingProcess) { onSetupChanged(setup.copy(weldingProcess = it)) }
                    GeneralFormTextField("Laminating Check", setup.laminatingCheck) { onSetupChanged(setup.copy(laminatingCheck = it)) }
                    GeneralFormTextField("Couplant Used", setup.couplantUsed) { onSetupChanged(setup.copy(couplantUsed = it)) }
                }
                HorizontalDivider()
                GeneralExpandableSubSection("Tabel Hasil Pengukuran") {
                    val data = report.inspectionAndMeasurement

                    // Top Head Measurement
                    Text("Top Head", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    MeasurementResultItemInput(
                        item = data.measurementResultsTopHead,
                        onValueChange = { updatedItem ->
                            viewModel.updateMeasurementResultItem(updatedItem, MeasurementResultType.TOP_HEAD)
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))

                    // Shell Measurement
                    Text("Shell", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    MeasurementResultItemInput(
                        item = data.measurementResultsShell,
                        onValueChange = { updatedItem ->
                            viewModel.updateMeasurementResultItem(updatedItem, MeasurementResultType.SHELL)
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))

                    // Button Head Measurement
                    Text("Button Head", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    MeasurementResultItemInput(
                        item = data.measurementResultsButtonHead,
                        onValueChange = { updatedItem ->
                            viewModel.updateMeasurementResultItem(updatedItem, MeasurementResultType.BUTTON_HEAD)
                        }
                    )
                }
                HorizontalDivider()
                GeneralExpandableSubSection("Pengujian NDT") {
                    val ndt = data.ndtTests
                    val onNdtChanged: (GeneralNdtTests) -> Unit = { onDataChanged(data.copy(ndtTests = it)) }
                    Text("Badan Shell", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    GeneralFormTextField("Metode NDT", ndt.shell.method) { onNdtChanged(ndt.copy(shell = ndt.shell.copy(method = it))) }
                    NdtResultInput("Longitudinal Weld Joint", ndt.shell.longitudinalWeldJoint) { onNdtChanged(ndt.copy(shell = ndt.shell.copy(longitudinalWeldJoint = it))) }
                    NdtResultInput("Circumferential Weld Joint", ndt.shell.circumferentialWeldJoint) { onNdtChanged(ndt.copy(shell = ndt.shell.copy(circumferentialWeldJoint = it))) }
                    HorizontalDivider(Modifier.padding(vertical = 4.dp))

                    Text("Furnace", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    GeneralFormTextField("Metode NDT", ndt.furnace.method) { onNdtChanged(ndt.copy(furnace = ndt.furnace.copy(method = it))) }
                    NdtResultInput("Longitudinal Weld Joint", ndt.furnace.longitudinalWeldJoint) { onNdtChanged(ndt.copy(furnace = ndt.furnace.copy(longitudinalWeldJoint = it))) }
                    NdtResultInput("Circumferential Weld Joint", ndt.furnace.circumferentialWeldJoint) { onNdtChanged(ndt.copy(furnace = ndt.furnace.copy(circumferentialWeldJoint = it))) }
                    HorizontalDivider(Modifier.padding(vertical = 4.dp))

                    Text("Pipa Api", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    GeneralFormTextField("Metode NDT", ndt.fireTubes.method) { onNdtChanged(ndt.copy(fireTubes = ndt.fireTubes.copy(method = it))) }
                    NdtResultInput("Weld Joint Front", ndt.fireTubes.weldJointFront) { onNdtChanged(ndt.copy(fireTubes = ndt.fireTubes.copy(weldJointFront = it))) }
                    NdtResultInput("Weld Joint Rear", ndt.fireTubes.weldJointRear) { onNdtChanged(ndt.copy(fireTubes = ndt.fireTubes.copy(weldJointRear = it))) }
                }
                HorizontalDivider()
                GeneralExpandableSubSection("Pengujian Hidrotest") {
                    val hydro = data.hydrotest
                    val onHydroChanged: (GeneralHydrotest) -> Unit = { onDataChanged(data.copy(hydrotest = it)) }
                    GeneralFormTextField("Tekanan Pengujian (Kg/cm²)", hydro.testPressureKgCm2) { onHydroChanged(hydro.copy(testPressureKgCm2 = it)) }
                    GeneralFormTextField("MAWP (Kg/cm²)", hydro.mawpKgCm2) { onHydroChanged(hydro.copy(mawpKgCm2 = it)) }
                    GeneralFormTextField("Media Pengujian", hydro.testMedium) { onHydroChanged(hydro.copy(testMedium = it)) }
                    GeneralFormTextField("Tanggal Pengujian", hydro.testDate) { onHydroChanged(hydro.copy(testDate = it)) }
                    GeneralFormTextField("Hasil Pengujian", hydro.testResult) { onHydroChanged(hydro.copy(testResult = it)) }
                }
                HorizontalDivider()
                GeneralExpandableSubSection("Pemeriksaan Apendages") {
                    val appendages = data.appendagesInspection
                    val onAppendagesChanged: (GeneralAppendagesInspection) -> Unit = { onDataChanged(data.copy(appendagesInspection = it)) }
                    AppendageResultInput("Pressure Tekanan Kerja", appendages.pressureGauge) { onAppendagesChanged(appendages.copy(pressureGauge = it)) }
                    AppendageResultInput("ManHole", appendages.manHole) { onAppendagesChanged(appendages.copy(manHole = it)) }
                    AppendageResultInput("Safety Valve Buka Penuh", appendages.safetyValve) { onAppendagesChanged(appendages.copy(safetyValve = it)) }
                    AppendageResultInput("Main Steam Valve", appendages.mainSteamValve) { onAppendagesChanged(appendages.copy(mainSteamValve = it)) }
                    AppendageResultInput("Level Glass Indicator", appendages.levelGlassIndicator) { onAppendagesChanged(appendages.copy(levelGlassIndicator = it)) }
                    AppendageResultInput("Tingkap Pembuang", appendages.blowdownValve) { onAppendagesChanged(appendages.copy(blowdownValve = it)) }
                    AppendageResultInput("Valve Stop Air", appendages.feedwaterStopValve) { onAppendagesChanged(appendages.copy(feedwaterStopValve = it)) }
                    AppendageResultInput("Valve Air Masuk", appendages.feedwaterInletValve) { onAppendagesChanged(appendages.copy(feedwaterInletValve = it)) }
                    AppendageResultInput("Steam Drier", appendages.steamDrier) { onAppendagesChanged(appendages.copy(steamDrier = it)) }
                    AppendageResultInput("Water Pump", appendages.waterPump) { onAppendagesChanged(appendages.copy(waterPump = it)) }
                    AppendageResultInput("Control Panel", appendages.controlPanel) { onAppendagesChanged(appendages.copy(controlPanel = it)) }
                    AppendageResultInput("Pelat Nama", appendages.nameplate) { onAppendagesChanged(appendages.copy(nameplate = it)) }
                }
                HorizontalDivider()
                GeneralExpandableSubSection("Pengujian Tingkap Pengaman") {
                    val valve = data.safetyValveTest
                    val onValveChanged: (GeneralSafetyValveTest) -> Unit = { onDataChanged(data.copy(safetyValveTest = it)) }
                    GeneralFormTextField("Keterangan Header", valve.header) { onValveChanged(valve.copy(header = it)) }
                    GeneralFormTextField("Mulai Membuka (Kg/cm²)", valve.startsToOpenKgCm2) { onValveChanged(valve.copy(startsToOpenKgCm2 = it)) }
                    GeneralFormTextField("Info Safety Valve", valve.valveInfo) { onValveChanged(valve.copy(valveInfo = it)) }
                }
            }
        }

        item {
            val data = report.conclusion
            GeneralExpandableSection("KESIMPULAN & REKOMENDASI") {
                Text("Kesimpulan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                FilledTonalButton(onClick = { showSummaryDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Poin Kesimpulan")
                }
                data.summary.forEachIndexed { index, item ->
                    GeneralListItemWithDelete(onDelete = { viewModel.removeConclusionSummary(index) }) {
                        Text(item, modifier = Modifier.padding(8.dp))
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text("Rekomendasi", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                FilledTonalButton(onClick = { showRecommendationDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Poin Rekomendasi")
                }
                data.recommendations.forEachIndexed { index, item ->
                    GeneralListItemWithDelete(onDelete = { viewModel.removeConclusionRecommendation(index) }) {
                        Text(item, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}


//region Reusable Composables for GeneralScreen
@Composable
private fun MeasurementResultItemInput(
    item: GeneralMeasurementResultItem,
    onValueChange: (GeneralMeasurementResultItem) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        GeneralFormTextField("Nominal (mm)", item.nominalMm) { onValueChange(item.copy(nominalMm = it)) }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                GeneralFormTextField("Titik 1", item.point1) { onValueChange(item.copy(point1 = it)) }
            }
            Box(modifier = Modifier.weight(1f)) {
                GeneralFormTextField("Titik 2", item.point2) { onValueChange(item.copy(point2 = it)) }
            }
            Box(modifier = Modifier.weight(1f)) {
                GeneralFormTextField("Titik 3", item.point3) { onValueChange(item.copy(point3 = it)) }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                GeneralFormTextField("Minimum", item.minimum) { onValueChange(item.copy(minimum = it)) }
            }
            Box(modifier = Modifier.weight(1f)) {
                GeneralFormTextField("Maximum", item.maximum) { onValueChange(item.copy(maximum = it)) }
            }
        }
    }
}

@Composable
private fun GeneralListItemWithDelete(
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
private fun GeneralExpandableSection(
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
private fun GeneralExpandableSubSection(
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
private fun GeneralFormTextField(
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
private fun CheckResultInput(
    label: String,
    value: GeneralCheckResult,
    onValueChange: (GeneralCheckResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isMet = true)) }) {
                RadioButton(selected = value.isMet, onClick = null)
                Text("Memenuhi Syarat")
            }
            Spacer(Modifier.width(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isMet = false)) }) {
                RadioButton(selected = !value.isMet, onClick = null)
                Text("Tidak")
            }
        }
        GeneralFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

@Composable
private fun ValueResultInput(
    label: String,
    value: GeneralValueResult,
    onValueChange: (GeneralValueResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        GeneralFormTextField(label, value.value) { onValueChange(value.copy(value = it)) }
        GeneralFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

@Composable
private fun NdtResultInput(
    label: String,
    value: GeneralNdtResult,
    onValueChange: (GeneralNdtResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        GeneralFormTextField("Lokasi", value.location) { onValueChange(value.copy(location = it)) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isGood = true)) }) {
                RadioButton(selected = value.isGood, onClick = null)
                Text("Baik")
            }
            Spacer(Modifier.width(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isGood = false)) }) {
                RadioButton(selected = !value.isGood, onClick = null)
                Text("Tidak Baik")
            }
        }
        GeneralFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

@Composable
private fun AppendageResultInput(
    label: String,
    value: GeneralAppendageResult,
    onValueChange: (GeneralAppendageResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        GeneralFormTextField("Jumlah", value.quantity) { onValueChange(value.copy(quantity = it)) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isGood = true)) }) {
                RadioButton(selected = value.isGood, onClick = null)
                Text("Baik")
            }
            Spacer(Modifier.width(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isGood = false)) }) {
                RadioButton(selected = !value.isGood, onClick = null)
                Text("Tidak Baik")
            }
        }
        GeneralFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

//endregion