package com.nakersolutionid.nakersolutionid.features.report.ptp.machine

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.features.report.ptp.PTPViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MachineScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: PTPViewModel = koinViewModel()
) {
    val uiState by viewModel.machineUiState.collectAsStateWithLifecycle()
    val report = uiState.inspectionReport
    val onDataChange = viewModel::onMachineReportChange

    var showSummaryDialog by remember { mutableStateOf(false) }
    var showRequirementDialog by remember { mutableStateOf(false) }

    if (showSummaryDialog) {
        AddMachineStringDialog(
            title = "Tambah Temuan Kesimpulan",
            label = "Poin Kesimpulan",
            onDismissRequest = { showSummaryDialog = false },
            onConfirm = {
                viewModel.addMachineConclusionSummary(it)
                showSummaryDialog = false
            }
        )
    }

    if (showRequirementDialog) {
        AddMachineStringDialog(
            title = "Tambah Poin Persyaratan",
            label = "Poin Persyaratan",
            onDismissRequest = { showRequirementDialog = false },
            onConfirm = {
                viewModel.addMachineConclusionRequirement(it)
                showRequirementDialog = false
            }
        )
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        item {
            val data = report
            MachineExpandableSection("DATA UTAMA", true) {
                MachineFormTextField(label = "Jenis Pemeriksaan", value = data.examinationType, onValueChange = { onDataChange(data.copy(examinationType = it)) })
                MachineFormTextField(label = "Jenis Pesawat", value = data.equipmentType, onValueChange = { onDataChange(data.copy(equipmentType = it)) })
            }
        }

        item {
            val data = report.generalData
            val onDataChanged: (ProductionMachineGeneralData) -> Unit = { onDataChange(report.copy(generalData = it)) }
            MachineExpandableSection("DATA UMUM", false) {
                MachineFormTextField("Perusahaan Pemilik", data.ownerName) { onDataChanged(data.copy(ownerName = it)) }
                MachineFormTextField("Alamat Pemilik", data.ownerAddress) { onDataChanged(data.copy(ownerAddress = it)) }
                MachineFormTextField("Perusahaan Pemakai", data.userInCharge) { onDataChanged(data.copy(userInCharge = it)) }
                MachineFormTextField("Alamat Pemakai", data.userAddressInCharge) { onDataChanged(data.copy(userAddressInCharge = it)) }
                MachineFormTextField("Pengurus / Penanggung Jawab", data.subcontractorPersonInCharge) { onDataChanged(data.copy(subcontractorPersonInCharge = it)) }
                MachineFormTextField("Lokasi Unit", data.unitLocation) { onDataChanged(data.copy(unitLocation = it)) }
                MachineFormTextField("Jenis Pesawat / Tipe", data.driveType) { onDataChanged(data.copy(driveType = it)) }
                MachineFormTextField("Merek / Tipe", data.brandType) { onDataChanged(data.copy(brandType = it)) }
                MachineFormTextField("No Seri / No. Unit", data.serialNumberUnitNumber) { onDataChanged(data.copy(serialNumberUnitNumber = it)) }
                MachineFormTextField("Perusahaan Pembuat / Pemasang", data.manufacturer) { onDataChanged(data.copy(manufacturer = it)) }
                MachineFormTextField("Lokasi / Tahun Pembuatan", data.locationAndYearOfManufacture) { onDataChanged(data.copy(locationAndYearOfManufacture = it)) }
                MachineFormTextField("Daya Motor Penggerak", data.motorPower) { onDataChanged(data.copy(motorPower = it)) }
                MachineFormTextField("Digunakan Untuk", data.intendedUse) { onDataChanged(data.copy(intendedUse = it)) }
                MachineFormTextField("No. SKP / Bidang PJK3", data.pjk3SkpNo) { onDataChanged(data.copy(pjk3SkpNo = it)) }
                MachineFormTextField("No. SKP / Bidang AK3", data.ak3SkpNo) { onDataChanged(data.copy(ak3SkpNo = it)) }
                MachineFormTextField("No Izin Pemakaian / Penerbit", data.usagePermitNumber) { onDataChanged(data.copy(usagePermitNumber = it)) }
                MachineFormTextField("Nama Operator", data.operatorName) { onDataChanged(data.copy(operatorName = it)) }
                MachineFormTextField("Data Riwayat Mesin/Pesawat", data.equipmentHistory) { onDataChanged(data.copy(equipmentHistory = it)) }
            }
        }

        item {
            val data = report.technicalData
            val onDataChanged: (ProductionMachineTechnicalData) -> Unit = { onDataChange(report.copy(technicalData = it)) }
            MachineExpandableSection("DATA TEKNIK") {
                Text("Spesifikasi Mesin", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                MachineFormTextField("Jenis / Tipe", data.type) { onDataChanged(data.copy(type = it)) }
                MachineFormTextField("Kecepatan Feeder Max (m/min)", data.maxFeederSpeed) { onDataChanged(data.copy(maxFeederSpeed = it)) }
                MachineFormTextField("Kapasitas Lebar Plat Max (mm)", data.maxPlateWidth) { onDataChanged(data.copy(maxPlateWidth = it)) }
                MachineFormTextField("Ketebalan Plat (mm)", data.plateThickness) { onDataChanged(data.copy(plateThickness = it)) }
                MachineFormTextField("Berat Plat Max (kg)", data.maxPlateWeight) { onDataChanged(data.copy(maxPlateWeight = it)) }
                MachineFormTextField("Diameter Dalam Gulungan Max (mm)", data.maxInnerCoilDiameter) { onDataChanged(data.copy(maxInnerCoilDiameter = it)) }
                MachineFormTextField("Diameter Luar Gulungan Max (mm)", data.maxOuterCoilDiameter) { onDataChanged(data.copy(maxOuterCoilDiameter = it)) }
                MachineFormTextField("Motor Penggerak", data.driveMotor) { onDataChanged(data.copy(driveMotor = it)) }
                MachineFormTextField("Daya Motor (KW)", data.motorPowerKw) { onDataChanged(data.copy(motorPowerKw = it)) }
                MachineFormTextField("Merk / No Seri", data.brandAndSerial) { onDataChanged(data.copy(brandAndSerial = it)) }
                MachineFormTextField("Lokasi / Tahun Pembuatan", data.locationAndYear) { onDataChanged(data.copy(locationAndYear = it)) }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text("Dimensi Mesin", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                MachineFormTextField("Berat Mesin (Kg)", data.machineDimensions.weightKg) { onDataChanged(data.copy(machineDimensions = data.machineDimensions.copy(weightKg = it))) }
                MachineFormTextField("Dimensi Keseluruhan (mm)", data.machineDimensions.overallDimension) { onDataChanged(data.copy(machineDimensions = data.machineDimensions.copy(overallDimension = it))) }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text("Dimensi Pondasi", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                MachineFormTextField("Dimensi Pondasi (mm)", data.foundationDimensions.dim) { onDataChanged(data.copy(foundationDimensions = data.foundationDimensions.copy(dim = it))) }
                MachineFormTextField("Jarak Pondasi", data.foundationDimensions.distance) { onDataChanged(data.copy(foundationDimensions = data.foundationDimensions.copy(distance = it))) }
                MachineFormTextField("Jenis Peredam Getaran", data.foundationDimensions.vibrationDamperType) { onDataChanged(data.copy(foundationDimensions = data.foundationDimensions.copy(vibrationDamperType = it))) }
                MachineFormTextField("Berat Pondasi 1 (Kg)", data.foundationDimensions.weightKg1) { onDataChanged(data.copy(foundationDimensions = data.foundationDimensions.copy(weightKg1 = it))) }
                MachineFormTextField("Berat Pondasi 2 (Kg)", data.foundationDimensions.weightKg2) { onDataChanged(data.copy(foundationDimensions = data.foundationDimensions.copy(weightKg2 = it))) }
            }
        }

        item {
            val data = report.visualInspection
            val onDataChanged: (ProductionMachineVisualInspection) -> Unit = { onDataChange(report.copy(visualInspection = it)) }
            MachineExpandableSection("PEMERIKSAAN VISUAL") {
                ConditionInput("Pondasi Mesin", data.foundationCondition) { onDataChanged(data.copy(foundationCondition = it)) }
                ConditionInput("Bantalan Pondasi (Karet / Pegas)", data.foundationBearingCondition) { onDataChanged(data.copy(foundationBearingCondition = it)) }
                Text("Rangka Mesin", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                ConditionInput("a. Rangka Utama", data.mainFrameCondition) { onDataChanged(data.copy(mainFrameCondition = it)) }
                ConditionInput("b. Rangka Penguat (Brace)", data.braceFrameCondition) { onDataChanged(data.copy(braceFrameCondition = it)) }
                ConditionInput("Roller", data.rollerCondition) { onDataChanged(data.copy(rollerCondition = it)) }
                ConditionInput("Panel Kontrol", data.controlPanelCondition) { onDataChanged(data.copy(controlPanelCondition = it)) }
                ConditionInput("Display", data.displayCondition) { onDataChanged(data.copy(displayCondition = it)) }
                ConditionInput("Tombol Operasi", data.operationButtonsCondition) { onDataChanged(data.copy(operationButtonsCondition = it)) }
                Text("Komponen Listrik", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                ConditionInput("a. Tegangan (V)", data.electricalVoltage) { onDataChanged(data.copy(electricalVoltage = it)) }
                ConditionInput("b. Daya (KW)", data.electricalPower) { onDataChanged(data.copy(electricalPower = it)) }
                ConditionInput("c. Phase", data.electricalPhase) { onDataChanged(data.copy(electricalPhase = it)) }
                ConditionInput("d. Frekuensi (Hz)", data.electricalFrequency) { onDataChanged(data.copy(electricalFrequency = it)) }
                ConditionInput("e. Arus (A)", data.electricalCurrent) { onDataChanged(data.copy(electricalCurrent = it)) }
                ConditionInput("f. Panel Listrik", data.electricalPanel) { onDataChanged(data.copy(electricalPanel = it)) }
                ConditionInput("g. Penghantar", data.electricalConductor) { onDataChanged(data.copy(electricalConductor = it)) }
                ConditionInput("i. Isolasi", data.electricalInsulation) { onDataChanged(data.copy(electricalInsulation = it)) }
                Text("Alat Pengaman", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                ConditionInput("a. Limit Switch Up", data.safetyLimitSwitchUp) { onDataChanged(data.copy(safetyLimitSwitchUp = it)) }
                ConditionInput("b. Limit Switch Down", data.safetyLimitSwitchDown) { onDataChanged(data.copy(safetyLimitSwitchDown = it)) }
                ConditionInput("c. Tahanan Pembumian (Grounding)", data.safetyGrounding) { onDataChanged(data.copy(safetyGrounding = it)) }
                ConditionInput("d. Tutup Pengaman", data.safetyGuard) { onDataChanged(data.copy(safetyGuard = it)) }
                ConditionInput("e. Pengunci Stempel", data.safetyStampLock) { onDataChanged(data.copy(safetyStampLock = it)) }
                ConditionInput("f. Indikator Tekanan", data.safetyPressureIndicator) { onDataChanged(data.copy(safetyPressureIndicator = it)) }
                ConditionInput("g. Emergency Stop", data.safetyEmergencyStop) { onDataChanged(data.copy(safetyEmergencyStop = it)) }
                ConditionInput("h. Sensor Tangan Henti Otomatis", data.safetyHandSensor) { onDataChanged(data.copy(safetyHandSensor = it)) }
                ConditionInput("Hidrolik Pump", data.hydraulicPumpCondition) { onDataChanged(data.copy(hydraulicPumpCondition = it)) }
                ConditionInput("Selang Hidrolik (Hose)", data.hydraulicHoseCondition) { onDataChanged(data.copy(hydraulicHoseCondition = it)) }
            }
        }

        item {
            val data = report.testingAndMeasurement
            val onDataChanged: (ProductionMachineTestingAndMeasurement) -> Unit = { onDataChange(report.copy(testingAndMeasurement = it)) }
            MachineExpandableSection("PENGUKURAN DAN PENGUJIAN") {
                MachineExpandableSubSection("Pengujian Fungsi & Safety Device") {
                    val functional = data.functionalTests
                    val onFunctionalChanged: (ProductionMachineFunctionalTests) -> Unit = { onDataChanged(data.copy(functionalTests = it)) }
                    TestResultInput("Tahanan Pembumian (Grounding)", functional.safetyGrounding) { onFunctionalChanged(functional.copy(safetyGrounding = it)) }
                    TestResultInput("Tutup Pengaman", functional.safetyGuard) { onFunctionalChanged(functional.copy(safetyGuard = it)) }
                    TestResultInput("Roller", functional.safetyRoller) { onFunctionalChanged(functional.copy(safetyRoller = it)) }
                    TestResultInput("Emergency Stop", functional.safetyEmergencyStop) { onFunctionalChanged(functional.copy(safetyEmergencyStop = it)) }
                    TestResultInput("Pengujian Kecepatan", functional.speedTest) { onFunctionalChanged(functional.copy(speedTest = it)) }
                    TestResultInput("Pengujian Fungsi", functional.functionTest) { onFunctionalChanged(functional.copy(functionTest = it)) }
                    TestResultInput("Sambungan Las", functional.weldJointTest) { onFunctionalChanged(functional.copy(weldJointTest = it)) }
                    TestResultInput("Pengujian Getaran", functional.vibrationTest) { onFunctionalChanged(functional.copy(vibrationTest = it)) }
                    TestResultInput("Pengujian Pencahayaan", functional.lightingTest) { onFunctionalChanged(functional.copy(lightingTest = it)) }
                    TestResultInput("Pengujian Kebisingan", functional.noiseTest) { onFunctionalChanged(functional.copy(noiseTest = it)) }
                }
                HorizontalDivider()
                MachineExpandableSubSection("Pengukuran Komponen Listrik") {
                    val electrical = data.electricalMeasurements
                    val onElectricalChanged: (ProductionMachineElectricalMeasurements) -> Unit = { onDataChanged(data.copy(electricalMeasurements = it)) }
                    Text("Panel Control Drawing", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    val drawing = electrical.panelControlDrawing
                    MachineFormTextField("KA", drawing.ka) { onElectricalChanged(electrical.copy(panelControlDrawing = drawing.copy(ka = it))) }
                    MachineFormTextField("Tegangan R-S", drawing.voltageRS) { onElectricalChanged(electrical.copy(panelControlDrawing = drawing.copy(voltageRS = it))) }
                    MachineFormTextField("Tegangan R-T", drawing.voltageRT) { onElectricalChanged(electrical.copy(panelControlDrawing = drawing.copy(voltageRT = it))) }
                    MachineFormTextField("Tegangan S-T", drawing.voltageST) { onElectricalChanged(electrical.copy(panelControlDrawing = drawing.copy(voltageST = it))) }
                    MachineFormTextField("Tegangan R-N", drawing.voltageRN) { onElectricalChanged(electrical.copy(panelControlDrawing = drawing.copy(voltageRN = it))) }
                    MachineFormTextField("Tegangan R-G", drawing.voltageRG) { onElectricalChanged(electrical.copy(panelControlDrawing = drawing.copy(voltageRG = it))) }
                    MachineFormTextField("Tegangan N-G", drawing.voltageNG) { onElectricalChanged(electrical.copy(panelControlDrawing = drawing.copy(voltageNG = it))) }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("Power Info", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    val power = electrical.powerInfo
                    MachineFormTextField("Frekuensi (Hz)", power.frequency) { onElectricalChanged(electrical.copy(powerInfo = power.copy(frequency = it))) }
                    MachineFormTextField("Cos φ", power.cosQ) { onElectricalChanged(electrical.copy(powerInfo = power.copy(cosQ = it))) }
                    MachineFormTextField("Arus R (A)", power.ampereR) { onElectricalChanged(electrical.copy(powerInfo = power.copy(ampereR = it))) }
                    MachineFormTextField("Arus S (A)", power.ampereS) { onElectricalChanged(electrical.copy(powerInfo = power.copy(ampereS = it))) }
                    MachineFormTextField("Arus T (A)", power.ampereT) { onElectricalChanged(electrical.copy(powerInfo = power.copy(ampereT = it))) }
                    MachineFormTextField("Keterangan", power.remarks) { onElectricalChanged(electrical.copy(powerInfo = power.copy(remarks = it))) }
                }
            }
        }

        item {
            val data = report.foundationAnalysis
            val onDataChanged: (ProductionMachineFoundationAnalysis) -> Unit = { onDataChange(report.copy(foundationAnalysis = it)) }
            MachineExpandableSection("ANALISA PONDASI") {
                Text("Berat Mesin", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                MachineFormTextField("Berat Aktual (Ton)", data.machineWeight.actualTon) { onDataChanged(data.copy(machineWeight = data.machineWeight.copy(actualTon = it))) }
                MachineFormTextField("Material Tambahan (Ton)", data.machineWeight.additionalMaterialTon) { onDataChanged(data.copy(machineWeight = data.machineWeight.copy(additionalMaterialTon = it))) }
                MachineFormTextField("Total (Ton)", data.machineWeight.totalTon) { onDataChanged(data.copy(machineWeight = data.machineWeight.copy(totalTon = it))) }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text("Berat Pondasi Minimal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                MachineFormTextField("Perhitungan (Berat Total x 1.5)", data.minFoundationWeight.calculation) { onDataChanged(data.copy(minFoundationWeight = data.minFoundationWeight.copy(calculation = it))) }
                MachineFormTextField("Hasil (Ton)", data.minFoundationWeight.resultTon) { onDataChanged(data.copy(minFoundationWeight = data.minFoundationWeight.copy(resultTon = it))) }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text("Tinggi Pondasi", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Rumus: ${data.foundationHeight.formula}", style = MaterialTheme.typography.bodySmall)
                MachineFormTextField("Perhitungan (e.g. 4 = 1.1x0.9xtx2.4)", data.foundationHeight.calculation) { onDataChanged(data.copy(foundationHeight = data.foundationHeight.copy(calculation = it))) }
                MachineFormTextField("Tinggi Pondasi (m)", data.foundationHeight.resultMeter) { onDataChanged(data.copy(foundationHeight = data.foundationHeight.copy(resultMeter = it))) }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                MachineFormTextField("Kesimpulan Analisa", data.summary) { onDataChanged(data.copy(summary = it)) }
            }
        }

        item {
            val data = report.noiseAndLightingMeasurement
            val onDataChanged: (ProductionMachineNoiseAndLightingMeasurement) -> Unit = { onDataChange(report.copy(noiseAndLightingMeasurement = it)) }
            MachineExpandableSection("PENGUKURAN KEBISINGAN & PENCAHAYAAN") {
                Text("Dasar Aturan: ${data.regulationBasis}", style = MaterialTheme.typography.bodySmall)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text("Kebisingan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                val noise = data.noise
                MachineFormTextField("Titik A (dB)", noise.measurementPointA_db) { onDataChanged(data.copy(noise = noise.copy(measurementPointA_db = it))) }
                Text("Standar: ${noise.standard}", style = MaterialTheme.typography.bodyMedium)
                MachineFormTextField("Hasil Kebisingan", noise.result) { onDataChanged(data.copy(noise = noise.copy(result = it))) }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text("Penerangan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                val lighting = data.lighting
                MachineFormTextField("Titik A (lux)", lighting.measurementPointA_lux) { onDataChanged(data.copy(lighting = lighting.copy(measurementPointA_lux = it))) }
                Text("Standar: ${lighting.standard}", style = MaterialTheme.typography.bodyMedium)
                MachineFormTextField("Hasil Penerangan", lighting.result) { onDataChanged(data.copy(lighting = lighting.copy(result = it))) }
            }
        }

        item {
            val data = report.conclusion
            MachineExpandableSection("KESIMPULAN") {
                FilledTonalButton(onClick = { showSummaryDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Kesimpulan")
                }
                data.summary.forEachIndexed { index, item ->
                    MachineListItemWithDelete(onDelete = { viewModel.removeMachineConclusionSummary(index) }) {
                        Text(item, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }

        item {
            val data = report.conclusion
            MachineExpandableSection("PERSYARATAN") {
                FilledTonalButton(onClick = { showRequirementDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Persyaratan")
                }
                data.requirements.forEachIndexed { index, item ->
                    MachineListItemWithDelete(onDelete = { viewModel.removeMachineConclusionRequirement(index) }) {
                        Text(item, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}


//region Reusable Composables for MachineScreen

@Composable
private fun MachineListItemWithDelete(
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
private fun MachineExpandableSection(
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
private fun MachineExpandableSubSection(
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
private fun MachineFormTextField(
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
private fun ConditionInput(
    label: String,
    value: ProductionMachineConditionResult,
    onValueChange: (ProductionMachineConditionResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isGood = true)) }) {
                RadioButton(selected = value.isGood, onClick = null)
                Text("Baik")
            }
            Spacer(Modifier.width(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isGood = false)) }) {
                RadioButton(selected = !value.isGood, onClick = null)
                Text("Buruk")
            }
        }
        MachineFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

@Composable
private fun TestResultInput(
    label: String,
    value: ProductionMachineTestResult,
    onValueChange: (ProductionMachineTestResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isMet = true)) }) {
                RadioButton(selected = value.isMet, onClick = null)
                Text("Memenuhi")
            }
            Spacer(Modifier.width(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isMet = false)) }) {
                RadioButton(selected = !value.isMet, onClick = null)
                Text("Tidak Memenuhi")
            }
        }
        MachineFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

//endregion