package com.nakersolutionid.nakersolutionid.features.bap.electric

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
fun ElectricalBAPScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: BAPCreationViewModel = koinViewModel()
) {
    val uiState by viewModel.electricalBAPUiState.collectAsStateWithLifecycle()
    val report = uiState.report
    val onDataChange = viewModel::onUpdateElectricalBAPState

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
                Text("Sumber Tenaga", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                FormTextField(label = "PLN (KVA)", value = data.powerSource.plnKva, onValueChange = { onDataChange(report.copy(technicalData = data.copy(powerSource = data.powerSource.copy(plnKva = it)))) })
                FormTextField(label = "Generator (KW)", value = data.powerSource.generatorKw, onValueChange = { onDataChange(report.copy(technicalData = data.copy(powerSource = data.powerSource.copy(generatorKw = it)))) })

                Text("Penggunaan Tenaga", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp))
                FormTextField(label = "Penerangan (KVA)", value = data.powerUsage.lightingKva, onValueChange = { onDataChange(report.copy(technicalData = data.copy(powerUsage = data.powerUsage.copy(lightingKva = it)))) })
                FormTextField(label = "Tenaga (KVA)", value = data.powerUsage.powerKva, onValueChange = { onDataChange(report.copy(technicalData = data.copy(powerUsage = data.powerUsage.copy(powerKva = it)))) })

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                FormTextField(label = "Jenis Arus Listrik", value = data.electricCurrentType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(electricCurrentType = it))) })
                FormTextField(label = "Nomor Seri", value = data.serialNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(serialNumber = it))) })
            }
        }

        // Test Results Section
        item {
            val data = report.testResults
            ExpandableSection(title = "HASIL PEMERIKSAAN & PENGUJIAN") {
                Text("Pemeriksaan Visual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Kondisi Ruang Panel", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 4.dp))
                CheckboxWithLabel(label = "Ruangan Bersih", checked = data.visualInspection.panelRoomCondition.isRoomClean, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(panelRoomCondition = data.visualInspection.panelRoomCondition.copy(isRoomClean = it))))) })
                CheckboxWithLabel(label = "Bebas dari Benda yang Tidak Perlu", checked = data.visualInspection.panelRoomCondition.isRoomClearOfUnnecessaryItems, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(panelRoomCondition = data.visualInspection.panelRoomCondition.copy(isRoomClearOfUnnecessaryItems = it))))) })

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                CheckboxWithLabel(label = "Memiliki Diagram Single Line", checked = data.visualInspection.hasSingleLineDiagram, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(hasSingleLineDiagram = it)))) })
                CheckboxWithLabel(label = "Panel & MCB Memiliki Penutup Pelindung", checked = data.visualInspection.panelAndMcbHaveProtectiveCover, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(panelAndMcbHaveProtectiveCover = it)))) })
                CheckboxWithLabel(label = "Pelabelan Lengkap", checked = data.visualInspection.isLabelingComplete, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isLabelingComplete = it)))) })
                CheckboxWithLabel(label = "APAR Tersedia", checked = data.visualInspection.isFireExtinguisherAvailable, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isFireExtinguisherAvailable = it)))) })

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                Text("Pengujian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                CheckboxWithLabel(label = "Hasil Uji Termografi Baik", checked = data.testing.thermographTestResult, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(thermographTestResult = it)))) })
                CheckboxWithLabel(label = "Perangkat Keselamatan Berfungsi", checked = data.testing.areSafetyDevicesFunctional, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(areSafetyDevicesFunctional = it)))) })
                CheckboxWithLabel(label = "Tegangan Antar Fasa Normal", checked = data.testing.isVoltageBetweenPhasesNormal, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isVoltageBetweenPhasesNormal = it)))) })
                CheckboxWithLabel(label = "Beban Fasa Seimbang", checked = data.testing.isPhaseLoadBalanced, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isPhaseLoadBalanced = it)))) })
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

@Preview(showBackground = true, name = "Electrical BAP Screen Preview")
@Composable
private fun ElectricalBAPScreenPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                ElectricalBAPScreen(
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