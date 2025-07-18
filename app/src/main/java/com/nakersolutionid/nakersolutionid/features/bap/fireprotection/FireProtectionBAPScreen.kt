package com.nakersolutionid.nakersolutionid.features.bap.fireprotection

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
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.bap.BAPCreationViewModel
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun FireProtectionBAPScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: BAPCreationViewModel = koinViewModel()
) {
    val uiState by viewModel.fireProtectionBAPUiState.collectAsStateWithLifecycle()
    val report = uiState.report
    val onDataChange = viewModel::onUpdateFireProtectionBAPState

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
                FormTextField(label = "Luas Area (m²)", value = data.areaSizeInM2, onValueChange = { onDataChange(report.copy(technicalData = data.copy(areaSizeInM2 = it))) })
                FormTextField(label = "Luas Gedung (m²)", value = data.buildingSizeInM2, onValueChange = { onDataChange(report.copy(technicalData = data.copy(buildingSizeInM2 = it))) })
                FormTextField(label = "Tinggi Gedung (m)", value = data.buildingHeightInM, onValueChange = { onDataChange(report.copy(technicalData = data.copy(buildingHeightInM = it))) })
                FormTextField(label = "Jumlah Lantai", value = data.totalFloors, onValueChange = { onDataChange(report.copy(technicalData = data.copy(totalFloors = it))) })
                FormTextField(label = "Jumlah Pilar Hidran", value = data.totalHydrantPilar, onValueChange = { onDataChange(report.copy(technicalData = data.copy(totalHydrantPilar = it))) })
                FormTextField(label = "Jumlah Hidran Gedung", value = data.totalHydrantBuilding, onValueChange = { onDataChange(report.copy(technicalData = data.copy(totalHydrantBuilding = it))) })
                FormTextField(label = "Jumlah Hose Rell", value = data.totalHoseRell, onValueChange = { onDataChange(report.copy(technicalData = data.copy(totalHoseRell = it))) })
                FormTextField(label = "Jumlah Sprinkler", value = data.totalSprinkler, onValueChange = { onDataChange(report.copy(technicalData = data.copy(totalSprinkler = it))) })
                FormTextField(label = "Jumlah Heat Detector", value = data.totalHeatDetector, onValueChange = { onDataChange(report.copy(technicalData = data.copy(totalHeatDetector = it))) })
                FormTextField(label = "Jumlah Smoke Detector", value = data.totalSmokeDetector, onValueChange = { onDataChange(report.copy(technicalData = data.copy(totalSmokeDetector = it))) })
                FormTextField(label = "Jumlah Flame Detector", value = data.totalFlameDetector, onValueChange = { onDataChange(report.copy(technicalData = data.copy(totalFlameDetector = it))) })
                FormTextField(label = "Jumlah Gas Detector", value = data.totalGasDetector, onValueChange = { onDataChange(report.copy(technicalData = data.copy(totalGasDetector = it))) })
                FormTextField(label = "Tombol Manual", value = data.manualButton, onValueChange = { onDataChange(report.copy(technicalData = data.copy(manualButton = it))) })
                FormTextField(label = "Bel Alarm", value = data.alarmBell, onValueChange = { onDataChange(report.copy(technicalData = data.copy(alarmBell = it))) })
                FormTextField(label = "Lampu Sinyal Alarm", value = data.signalAlarmLamp, onValueChange = { onDataChange(report.copy(technicalData = data.copy(signalAlarmLamp = it))) })
                FormTextField(label = "Pintu Darurat", value = data.emergencyExit, onValueChange = { onDataChange(report.copy(technicalData = data.copy(emergencyExit = it))) })
                FormTextField(label = "APAR", value = data.apar, onValueChange = { onDataChange(report.copy(technicalData = data.copy(apar = it))) })
                FormTextField(label = "Nomor Seri", value = data.seriesNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(seriesNumber = it))) })
                FormTextField(label = "Lokasi Pemakaian", value = data.usageLocation, onValueChange = { onDataChange(report.copy(technicalData = data.copy(usageLocation = it))) })
            }
        }

        // Test Results Section
        item {
            val data = report.testResults
            ExpandableSection(title = "HASIL PEMERIKSAAN & PENGUJIAN") {
                // Visual Inspection
                Text("Pemeriksaan Visual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Status APAR", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top=4.dp))
                CheckboxWithLabel(label = "Tersedia", checked = data.visualInspection.aparStatus.isAvailable, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(aparStatus = data.visualInspection.aparStatus.copy(isAvailable = it))))) })
                CheckboxWithLabel(label = "Kondisi Baik", checked = data.visualInspection.aparStatus.isGoodCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(aparStatus = data.visualInspection.aparStatus.copy(isGoodCondition = it))))) })

                CheckboxWithLabel(label = "Kondisi Panel Hidran Baik", checked = data.visualInspection.isHydrantPanelGoodCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isHydrantPanelGoodCondition = it)))) })

                Text("Status Pompa", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top=8.dp))
                CheckboxWithLabel(label = "Tersedia", checked = data.visualInspection.pumpStatus.isAvailable, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(pumpStatus = data.visualInspection.pumpStatus.copy(isAvailable = it))))) })
                CheckboxWithLabel(label = "Kondisi Baik", checked = data.visualInspection.pumpStatus.isGoodCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(pumpStatus = data.visualInspection.pumpStatus.copy(isGoodCondition = it))))) })

                Text("Status Sistem Sprinkler", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top=8.dp))
                CheckboxWithLabel(label = "Tersedia", checked = data.visualInspection.sprinklerSystemStatus.isAvailable, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(sprinklerSystemStatus = data.visualInspection.sprinklerSystemStatus.copy(isAvailable = it))))) })
                CheckboxWithLabel(label = "Kondisi Baik", checked = data.visualInspection.sprinklerSystemStatus.isGoodCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(sprinklerSystemStatus = data.visualInspection.sprinklerSystemStatus.copy(isGoodCondition = it))))) })

                Text("Status Sistem Detektor", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top=8.dp))
                CheckboxWithLabel(label = "Tersedia", checked = data.visualInspection.detectorSystemStatus.isAvailable, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(detectorSystemStatus = data.visualInspection.detectorSystemStatus.copy(isAvailable = it))))) })
                CheckboxWithLabel(label = "Kondisi Baik", checked = data.visualInspection.detectorSystemStatus.isGoodCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(detectorSystemStatus = data.visualInspection.detectorSystemStatus.copy(isGoodCondition = it))))) })

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Testing
                Text("Pengujian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                CheckboxWithLabel(label = "APAR Berfungsi", checked = data.testing.isAparFunctional, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isAparFunctional = it)))) })
                CheckboxWithLabel(label = "Hasil Tes Pompa Baik", checked = data.testing.pumpTestResults, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(pumpTestResults = it)))) })
                CheckboxWithLabel(label = "Sprinkler Berfungsi", checked = data.testing.isSprinklerFunctional, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isSprinklerFunctional = it)))) })

                Text("Hasil Tes Detektor", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top=8.dp))
                CheckboxWithLabel(label = "Berfungsi", checked = data.testing.detectorTestResults.isFunctional, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(detectorTestResults = data.testing.detectorTestResults.copy(isFunctional = it))))) })
                CheckboxWithLabel(label = "Terhubung ke MCFA", checked = data.testing.detectorTestResults.isConnectedToMcfa, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(detectorTestResults = data.testing.detectorTestResults.copy(isConnectedToMcfa = it))))) })
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

@Preview(showBackground = true, name = "Fire Protection BAP Screen Preview")
@Composable
private fun FireProtectionBAPScreenPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                FireProtectionBAPScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
        }
    }
}