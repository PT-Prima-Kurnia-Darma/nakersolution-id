package com.nakersolutionid.nakersolutionid.features.bap.lightning

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.features.bap.BAPCreationViewModel
// ... other necessary imports
import org.koin.androidx.compose.koinViewModel

@Composable
fun LightningBAPScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: BAPCreationViewModel = koinViewModel()
) {
    val uiState by viewModel.lightningBAPUiState.collectAsStateWithLifecycle()
    val report = uiState.report
    val onDataChange = viewModel::onUpdateLightningBAPState

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        // Main Data Section
        item {
            ExpandableSection(title = "DATA UTAMA", initiallyExpanded = true) {
                FormTextField(label = "Jenis Pemeriksaan", value = report.examinationType, onValueChange = { onDataChange(report.copy(examinationType = it)) })
                FormTextField(label = "Jenis Peralatan", value = report.equipmentType, onValueChange = { onDataChange(report.copy(equipmentType = it)) })
                FormTextField(label = "Tanggal Inspeksi", value = report.inspectionDate, onValueChange = { onDataChange(report.copy(inspectionDate = it)) })
            }
        }

        // General Data Section
        item {
            val data = report.generalData
            ExpandableSection(title = "DATA UMUM") {
                FormTextField(label = "Nama Perusahaan", value = data.companyName, onValueChange = { onDataChange(report.copy(generalData = data.copy(companyName = it))) })
                FormTextField(label = "Lokasi Perusahaan", value = data.companyLocation, onValueChange = { onDataChange(report.copy(generalData = data.copy(companyLocation = it))) })
                FormTextField(label = "Nama Lokasi Pemakaian", value = data.usageLocation, onValueChange = { onDataChange(report.copy(generalData = data.copy(usageLocation = it))) })
                FormTextField(label = "Alamat Lokasi Pemakaian", value = data.addressUsageLocation, onValueChange = { onDataChange(report.copy(generalData = data.copy(addressUsageLocation = it))) })
            }
        }

        // Technical Data Section
        item {
            val data = report.technicalData
            ExpandableSection(title = "DATA TEKNIK") {
                FormTextField(label = "Jenis Instalasi", value = data.installationType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(installationType = it))) })
                FormTextField(label = "Nomor Seri", value = data.serialNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(serialNumber = it))) })
                FormTextField(label = "Tinggi Gedung (m)", value = data.buildingHeightInM, onValueChange = { onDataChange(report.copy(technicalData = data.copy(buildingHeightInM = it))) })
                FormTextField(label = "Luas Gedung (m²)", value = data.buildingAreaInM2, onValueChange = { onDataChange(report.copy(technicalData = data.copy(buildingAreaInM2 = it))) })
                FormTextField(label = "Tinggi Penerima (m)", value = data.receiverHeightInM, onValueChange = { onDataChange(report.copy(technicalData = data.copy(receiverHeightInM = it))) })
                FormTextField(label = "Jumlah Penerima", value = data.receiverCount, onValueChange = { onDataChange(report.copy(technicalData = data.copy(receiverCount = it))) })
                FormTextField(label = "Jumlah Elektroda Tanah", value = data.groundElectrodeCount, onValueChange = { onDataChange(report.copy(technicalData = data.copy(groundElectrodeCount = it))) })
                FormTextField(label = "Deskripsi Konduktor (mm)", value = data.conductorDescriptionInMm, onValueChange = { onDataChange(report.copy(technicalData = data.copy(conductorDescriptionInMm = it))) })
                FormTextField(label = "Tahun Instalasi", value = data.installationYear, onValueChange = { onDataChange(report.copy(technicalData = data.copy(installationYear = it))) })
                FormTextField(label = "Resistansi Pembumian (Ω)", value = data.groundingResistanceInOhm, onValueChange = { onDataChange(report.copy(technicalData = data.copy(groundingResistanceInOhm = it))) })
            }
        }

        // Test Results Section
        item {
            val data = report.testResults
            ExpandableSection(title = "HASIL PEMERIKSAAN & PENGUJIAN") {
                // Visual Inspection
                Text("Pemeriksaan Visual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                CheckboxWithLabel(label = "Sistem Secara Keseluruhan Baik", checked = data.visualInspection.isSystemOverallGood, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isSystemOverallGood = it)))) })
                CheckboxWithLabel(label = "Kondisi Penerima Baik", checked = data.visualInspection.isReceiverConditionGood, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isReceiverConditionGood = it)))) })
                CheckboxWithLabel(label = "Kondisi Tiang Penerima Baik", checked = data.visualInspection.isReceiverPoleConditionGood, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isReceiverPoleConditionGood = it)))) })
                CheckboxWithLabel(label = "Konduktor Terisolasi", checked = data.visualInspection.isConductorInsulated, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isConductorInsulated = it)))) })

                // Control Box
                Text("Kotak Kontrol", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top=8.dp, bottom = 4.dp))
                CheckboxWithLabel(label = "Tersedia", checked = data.visualInspection.controlBox.isAvailable, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(controlBox = data.visualInspection.controlBox.copy(isAvailable = it))))) })
                CheckboxWithLabel(label = "Kondisi Baik", checked = data.visualInspection.controlBox.isConditionGood, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(controlBox = data.visualInspection.controlBox.copy(isConditionGood = it))))) })

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Testing
                Text("Pengujian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                CheckboxWithLabel(label = "Hasil Kontinuitas Konduktor Baik", checked = data.testing.conductorContinuityResult, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(conductorContinuityResult = it)))) })
                FormTextField(label = "Resistansi Pembumian (Ω)", value = data.testing.measuredGroundingResistanceValue, onValueChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(measuredGroundingResistanceValue = it)))) })
                CheckboxWithLabel(label = "Resistansi Pembumian Terukur Baik", checked = data.testing.measuredGroundingResistanceInOhm, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(measuredGroundingResistanceInOhm = it)))) })
            }
        }
    }
}


//region Reusable Composables

@Composable
private fun ExpandableSection(
    modifier: Modifier = Modifier,
    title: String,
    initiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(initiallyExpanded) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
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
                    Icon(imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = if (expanded) "Collapse" else "Expand")
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
fun FormTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value.orEmpty(),
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = MaterialTheme.shapes.medium,
    )
}

@Composable
fun CheckboxWithLabel(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null // null because the Row's click handler does the job
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}

//endregion