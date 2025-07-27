package com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning

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
import org.koin.androidx.compose.koinViewModel

@Composable
fun LightningScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: ILPPViewModel = koinViewModel()
) {
    val uiState by viewModel.lightningUiState.collectAsStateWithLifecycle()
    val report = uiState.inspectionReport
    val onDataChange = viewModel::onLightningReportChange

    var showMeasurementDialog by remember { mutableStateOf(false) }
    var showRecommendationDialog by remember { mutableStateOf(false) }

    if (showMeasurementDialog) {
        AddGroundingMeasurementDialog(
            onDismissRequest = { showMeasurementDialog = false },
            onConfirm = {
                viewModel.addGroundingMeasurementItem(it)
                showMeasurementDialog = false
            }
        )
    }

    if (showRecommendationDialog) {
        AddLightningStringDialog(
            title = "Tambah Poin Syarat",
            label = "Poin Syarat",
            onDismissRequest = { showRecommendationDialog = false },
            onConfirm = {
                viewModel.addLightningRecommendation(it)
                showRecommendationDialog = false
            }
        )
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        item {
            val data = report.serviceProviderData
            LightningExpandableSection("DATA PERUSAHAAN JASA K3", true) {
                LightningFormTextField("Nama & Alamat Perusahaan", data.companyName) {
                    onDataChange(report.copy(serviceProviderData = data.copy(companyName = it)))
                }
                LightningFormTextField("SKP Perusahaan", data.companyPermitNo) {
                    onDataChange(report.copy(serviceProviderData = data.copy(companyPermitNo = it)))
                }
                LightningFormTextField("SKP Ahli K3", data.expertPermitNo) {
                    onDataChange(report.copy(serviceProviderData = data.copy(expertPermitNo = it)))
                }
                LightningFormTextField("Nama Ahli K3", data.expertName) {
                    onDataChange(report.copy(serviceProviderData = data.copy(expertName = it)))
                }
                LightningFormTextField("Peralatan Riksa Uji", data.testEquipmentUsed) {
                    onDataChange(report.copy(serviceProviderData = data.copy(testEquipmentUsed = it)))
                }
            }
        }

        item {
            val data = report.clientData
            LightningExpandableSection("DATA PERUSAHAAN PEMILIK") {
                LightningFormTextField("Nama Perusahaan", data.companyName) { onDataChange(report.copy(clientData = data.copy(companyName = it))) }
                LightningFormTextField("Alamat Perusahaan", data.companyLocation) { onDataChange(report.copy(clientData = data.copy(companyLocation = it))) }
                LightningFormTextField("Lokasi Pemakaian", data.usageLocation) { onDataChange(report.copy(clientData = data.copy(usageLocation = it))) }
                LightningFormTextField("Jenis Objek K3", data.objectType) { onDataChange(report.copy(clientData = data.copy(objectType = it))) }
                LightningFormTextField("Jenis Riksa Uji", data.inspectionType) { onDataChange(report.copy(clientData = data.copy(inspectionType = it))) }
                LightningFormTextField("No. Pengesahan / Suket", data.certificateNo) { onDataChange(report.copy(clientData = data.copy(certificateNo = it))) }
                LightningFormTextField("Tanggal Riksa Uji", data.inspectionDate) { onDataChange(report.copy(clientData = data.copy(inspectionDate = it))) }
            }
        }

        item {
            val data = report.technicalData
            LightningExpandableSection("DATA TEKNIS PENYALUR PETIR") {
                LightningFormTextField("Jenis Penyalur Petir / Merk", data.conductorType) { onDataChange(report.copy(technicalData = data.copy(conductorType = it))) }
                LightningFormTextField("Tinggi Bangunan", data.buildingHeight) { onDataChange(report.copy(technicalData = data.copy(buildingHeight = it))) }
                LightningFormTextField("Luas Bangunan", data.buildingArea) { onDataChange(report.copy(technicalData = data.copy(buildingArea = it))) }
                LightningFormTextField("Tinggi Penerima", data.receiverHeight) { onDataChange(report.copy(technicalData = data.copy(receiverHeight = it))) }
                LightningFormTextField("Jumlah Penerima", data.receiverCount) { onDataChange(report.copy(technicalData = data.copy(receiverCount = it))) }
                LightningFormTextField("Jumlah Sambungan Ukur", data.testJointCount, keyboardType = KeyboardType.Number) { onDataChange(report.copy(technicalData = data.copy(testJointCount = it))) }
                LightningFormTextField("Jumlah Hantaran Penyalur", data.conductorDescription) { onDataChange(report.copy(technicalData = data.copy(conductorDescription = it))) }
                LightningFormTextField("Jenis & Ukuran Hantaran Penyalur", data.conductorTypeAndSize) { onDataChange(report.copy(technicalData = data.copy(conductorTypeAndSize = it))) }
                LightningFormTextField("Tahanan Sebaran Tanah", data.groundingResistance) { onDataChange(report.copy(technicalData = data.copy(groundingResistance = it))) }
                LightningFormTextField("Tahun Pemasangan", data.installationYear) { onDataChange(report.copy(technicalData = data.copy(installationYear = it))) }
                LightningFormTextField("Instalatir", data.installer) { onDataChange(report.copy(technicalData = data.copy(installer = it))) }
            }
        }

        item {
            val data = report.physicalInspection
            val onDataChanged: (LightningProtectionPhysicalInspection) -> Unit = { onDataChange(report.copy(physicalInspection = it)) }
            LightningExpandableSection("PEMERIKSAAN FISIK INSTALASI") {
                ConditionResultInput("Sistem Instalasi", data.installationSystem) { onDataChanged(data.copy(installationSystem = it)) }
                ConditionResultInput("Penerima (Head)", data.receiverHead) { onDataChanged(data.copy(receiverHead = it)) }
                ConditionResultInput("Tiang Penerima", data.receiverPole) { onDataChanged(data.copy(receiverPole = it)) }
                ConditionResultInput("Sistem Penguatan Tiang", data.poleReinforcementSystem) { onDataChanged(data.copy(poleReinforcementSystem = it)) }
                ConditionResultInput("Penghantar Penurunan", data.downConductor) { onDataChanged(data.copy(downConductor = it)) }
                ConditionResultInput("Klem Kabel Penghantar", data.conductorClamps) { onDataChanged(data.copy(conductorClamps = it)) }
                ConditionResultInput("Kondisi Sambungan", data.jointConnections) { onDataChanged(data.copy(jointConnections = it)) }
                ConditionResultInput("Kotak Down Conductor", data.downConductorBoxAndGroundingTerminal) { onDataChanged(data.copy(downConductorBoxAndGroundingTerminal = it)) }
                ConditionResultInput("Bak Kontrol", data.controlBox) { onDataChanged(data.copy(controlBox = it)) }
                ConditionResultInput("Sistem Pentanahan", data.groundingSystem) { onDataChanged(data.copy(groundingSystem = it)) }
                ConditionResultInput("Down Conductor Tersambung Langsung", data.downConductorDirectConnection) { onDataChanged(data.copy(downConductorDirectConnection = it)) }
                LightningFormTextField("Catatan", data.notes) { onDataChange(report.copy(physicalInspection = data.copy(notes = it))) }
            }
        }

        item {
            val data = report.groundingSystemVisualInspection
            LightningExpandableSection("PEMERIKSAAN VISUAL PEMBUMIAN") {
                LightningFormTextField("Penerima (Air Terminal)", data.airTerminal) { onDataChange(report.copy(groundingSystemVisualInspection = data.copy(airTerminal = it))) }
                LightningFormTextField("Penghantar Penurunan", data.downConductor) { onDataChange(report.copy(groundingSystemVisualInspection = data.copy(downConductor = it))) }
                LightningFormTextField("Pembumian dan Sambungan Ukur", data.groundingAndTestJoint) { onDataChange(report.copy(groundingSystemVisualInspection = data.copy(groundingAndTestJoint = it))) }
            }
        }

        item {
            val data = report.otherStandardsInspection
            val onDataChanged: (LightningProtectionOtherStandardsInspection) -> Unit = { onDataChange(report.copy(otherStandardsInspection = it)) }
            LightningExpandableSection("PEMERIKSAAN STANDAR LAINNYA") {
                CheckResultInput("Pemasangan Sesuai As Built Drawing", data.installationPlacementAsPerDrawing) { onDataChanged(data.copy(installationPlacementAsPerDrawing = it)) }
                CheckResultInput("Air Terminal Terhubung ke Penghantar", data.airTerminalConnectionToDownConductor) { onDataChanged(data.copy(airTerminalConnectionToDownConductor = it)) }
                CheckResultInput("Sambungan pada Penghantar Penurunan", data.downConductorJointsOnStructure) { onDataChanged(data.copy(downConductorJointsOnStructure = it)) }
                CheckResultInput("Elektroda Pembumian Pakai Box Ukur", data.groundingElectrodeUsesTestJointBox) { onDataChanged(data.copy(groundingElectrodeUsesTestJointBox = it)) }
                CheckResultInput("Penghantar Penurunan Sesuai Standar", data.downConductorMaterialStandard) { onDataChanged(data.copy(downConductorMaterialStandard = it)) }
                CheckResultInput("Terpasang Lightning Counter", data.lightningCounterInstalled) { onDataChanged(data.copy(lightningCounterInstalled = it)) }
                CheckResultInput("Air Terminal Mengandung Radioaktif", data.airTerminalIsRadioactive) { onDataChanged(data.copy(airTerminalIsRadioactive = it)) }
                CheckResultInput("Elektroda Pembumian Non-Corrosive", data.groundingElectrodeMaterialQuality) { onDataChanged(data.copy(groundingElectrodeMaterialQuality = it)) }
                CheckResultInput("Proteksi Tegangan Lebih (Arrester)", data.overvoltageProtectionInstalled) { onDataChanged(data.copy(overvoltageProtectionInstalled = it)) }
            }
        }

        item {
            val data = report.testingResults
            LightningExpandableSection("HASIL PENGUJIAN / PENGUKURAN") {
                FilledTonalButton(onClick = { showMeasurementDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Data Pengukuran Tahanan")
                }
                data.groundingResistanceMeasurement.forEachIndexed { index, item ->
                    LightningListItemWithDelete(onDelete = { viewModel.deleteGroundingMeasurementItem(index) }) {
                        Text("Pengukuran #${index + 1}", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(8.dp))
                        Text("Jarak E-C: ${item.ecDistance}m, E-P: ${item.epDistance}m", modifier = Modifier.padding(horizontal = 8.dp))
                        Text("Nilai Tahanan: ${item.rValueOhm}Ω", modifier = Modifier.padding(horizontal = 8.dp))
                        Text("Ket: ${item.remarks}", modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp))
                    }
                }
            }
        }


        item {
            val data = report.conclusion
            LightningExpandableSection("KESIMPULAN DAN SYARAT") {
                LightningFormTextField("Kesimpulan", data.summary) { onDataChange(report.copy(conclusion = data.copy(summary = it))) }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                FilledTonalButton(onClick = { showRecommendationDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Syarat Keselamatan")
                }
                data.recommendations.forEachIndexed { index, recommendation ->
                    LightningListItemWithDelete(onDelete = { viewModel.removeLightningRecommendation(index) }) {
                        Text(recommendation, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


//region Reusable Composables for LightningScreen

@Composable
private fun LightningListItemWithDelete(
    onDelete: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
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
private fun LightningExpandableSection(
    title: String,
    initiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(initiallyExpanded) }
    Card(
        modifier = Modifier.fillMaxWidth().animateContentSize(
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        )
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
private fun LightningFormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    )
}

@Composable
private fun LightningFormTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}

@Composable
private fun ConditionResultInput(
    label: String,
    value: LightningProtectionConditionResult,
    onValueChange: (LightningProtectionConditionResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(good = true, fair = false, poor = false)) }) {
                RadioButton(selected = value.good, onClick = null)
                Text("Baik")
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(good = false, fair = true, poor = false)) }) {
                RadioButton(selected = value.fair, onClick = null)
                Text("Kurang Baik")
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(good = false, fair = false, poor = true)) }) {
                RadioButton(selected = value.poor, onClick = null)
                Text("Buruk")
            }
        }
    }
}

@Composable
private fun CheckResultInput(
    label: String,
    value: LightningProtectionCheckResult,
    onValueChange: (LightningProtectionCheckResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(checked = !value.checked)) }) {
            Checkbox(checked = value.checked, onCheckedChange = null)
            Spacer(Modifier.width(4.dp))
            Text(if (value.checked) "Baik" else "Tidak")
        }
        LightningFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

//endregion