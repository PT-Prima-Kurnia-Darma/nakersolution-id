package com.nakersolutionid.nakersolutionid.features.bap.pubt

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
fun PubtBAPScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: BAPCreationViewModel = koinViewModel()
) {
    val uiState by viewModel.pubtBAPUiState.collectAsStateWithLifecycle()
    val report = uiState.report
    val onDataChange = viewModel::onUpdatePubtBAPState

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        // Main Data Section
        item {
            ExpandableSection(title = "DATA UTAMA", initiallyExpanded = true) {
                FormTextField(label = "Jenis Pemeriksaan", value = report.examinationType, onValueChange = { onDataChange(report.copy(examinationType = it)) })
                FormTextField(label = "Jenis Inspeksi", value = report.inspectionType, onValueChange = { onDataChange(report.copy(inspectionType = it)) })
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
                FormTextField(label = "Merk / Tipe", value = data.brandOrType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(brandOrType = it))) })
                FormTextField(label = "Pabrik Pembuat", value = data.manufacturer, onValueChange = { onDataChange(report.copy(technicalData = data.copy(manufacturer = it))) })
                FormTextField(label = "Tahun Pembuatan", value = data.manufactureYear, onValueChange = { onDataChange(report.copy(technicalData = data.copy(manufactureYear = it))) })
                FormTextField(label = "Negara Pembuat", value = data.manufactureCountry, onValueChange = { onDataChange(report.copy(technicalData = data.copy(manufactureCountry = it))) })
                FormTextField(label = "Nomor Seri", value = data.serialNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(serialNumber = it))) })
                FormTextField(label = "Jenis Bahan Bakar", value = data.fuelType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(fuelType = it))) })
                FormTextField(label = "Isi Bejana Tekan", value = data.pressureVesselContent, onValueChange = { onDataChange(report.copy(technicalData = data.copy(pressureVesselContent = it))) })
                FormTextField(label = "Tekanan Desain (Kg/Cm²)", value = data.designPressureInKgCm2, onValueChange = { onDataChange(report.copy(technicalData = data.copy(designPressureInKgCm2 = it))) })
                FormTextField(label = "Tekanan Kerja Maks (Kg/Cm²)", value = data.maxWorkingPressureInKgCm2, onValueChange = { onDataChange(report.copy(technicalData = data.copy(maxWorkingPressureInKgCm2 = it))) })
                FormTextField(label = "Jenis Material", value = data.materialType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(materialType = it))) })
                FormTextField(label = "Tipe Katup Pengaman", value = data.safetyValveType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(safetyValveType = it))) })
                FormTextField(label = "Volume (Liter)", value = data.volumeInLiters, onValueChange = { onDataChange(report.copy(technicalData = data.copy(volumeInLiters = it))) })
            }
        }

        // Test Results Section
        item {
            val data = report.testResults
            ExpandableSection(title = "HASIL PEMERIKSAAN & PENGUJIAN") {
                Text("Pemeriksaan Visual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                CheckboxWithLabel(label = "Kondisi Pondasi Baik", checked = data.visualInspection.fondationCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(fondationCondition = it)))) })

                Text("Katup Pengaman", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                CheckboxWithLabel(label = "Terpasang", checked = data.visualInspection.safetyValve.isInstalled, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(safetyValve = data.visualInspection.safetyValve.copy(isInstalled = it))))) })
                CheckboxWithLabel(label = "Kondisi Baik", checked = data.visualInspection.safetyValve.condition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(safetyValve = data.visualInspection.safetyValve.copy(condition = it))))) })

                Text("APAR", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                CheckboxWithLabel(label = "Tersedia", checked = data.visualInspection.apar.isAvailable, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(apar = data.visualInspection.apar.copy(isAvailable = it))))) })
                CheckboxWithLabel(label = "Kondisi Baik", checked = data.visualInspection.apar.condition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(apar = data.visualInspection.apar.copy(condition = it))))) })

                CheckboxWithLabel(label = "Kondisi Roda Baik", checked = data.visualInspection.wheelCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(wheelCondition = it)))) })
                CheckboxWithLabel(label = "Kondisi Pipa Baik", checked = data.visualInspection.pipeCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(pipeCondition = it)))) })

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                Text("Pengujian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                CheckboxWithLabel(label = "Pengujian NDT Terpenuhi", checked = data.testing.ndtTestingFulfilled, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(ndtTestingFulfilled = it)))) })
                CheckboxWithLabel(label = "Pengujian Ketebalan Sesuai", checked = data.testing.thicknessTestingComply, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(thicknessTestingComply = it)))) })
                CheckboxWithLabel(label = "Kondisi Pengujian Pneumatik Baik", checked = data.testing.pneumaticTestingCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(pneumaticTestingCondition = it)))) })
                CheckboxWithLabel(label = "Pengujian Hidro Terpenuhi", checked = data.testing.hydroTestingFullFilled, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(hydroTestingFullFilled = it)))) })
                CheckboxWithLabel(label = "Kondisi Pengujian Katup Pengaman Baik", checked = data.testing.safetyValveTestingCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(safetyValveTestingCondition = it)))) })
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

@Preview(showBackground = true, name = "PUBT BAP Screen Preview")
@Composable
private fun PubtBAPScreenPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                PubtBAPScreen(
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