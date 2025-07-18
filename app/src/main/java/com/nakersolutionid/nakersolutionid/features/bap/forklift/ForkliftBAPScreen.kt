package com.nakersolutionid.nakersolutionid.features.bap.forklift

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
fun ForkliftBAPScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: BAPCreationViewModel = koinViewModel()
) {
    val uiState by viewModel.forkliftBAPUiState.collectAsStateWithLifecycle()
    val report = uiState.forkliftBAPReport
    val onDataChange = viewModel::onUpdateForkliftBAPState

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        // Main Data Section
        item {
            ExpandableSection(title = "DATA UTAMA", initiallyExpanded = true) {
                FormTextField(
                    label = "Jenis Pemeriksaan",
                    value = report.examinationType,
                    onValueChange = { onDataChange(report.copy(examinationType = it)) }
                )
                FormTextField(
                    label = "Jenis Peralatan",
                    value = report.equipmentType,
                    onValueChange = { onDataChange(report.copy(equipmentType = it)) }
                )
                FormTextField(
                    label = "Tanggal Inspeksi",
                    value = report.inspectionDate,
                    onValueChange = { onDataChange(report.copy(inspectionDate = it)) }
                )
            }
        }

        // General Data Section
        item {
            val data = report.generalData
            ExpandableSection(title = "DATA UMUM") {
                FormTextField(
                    label = "Nama Pemilik",
                    value = data.ownerName,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(ownerName = it))) }
                )
                FormTextField(
                    label = "Alamat Pemilik",
                    value = data.ownerAddress,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(ownerAddress = it))) }
                )
                FormTextField(
                    label = "Penanggung Jawab Pengguna",
                    value = data.userInCharge,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(userInCharge = it))) }
                )
            }
        }

        // Technical Data Section
        item {
            val data = report.technicalData
            ExpandableSection(title = "DATA TEKNIK") {
                FormTextField(
                    label = "Merk / Tipe",
                    value = data.brandType,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(brandType = it))) }
                )
                FormTextField(
                    label = "Pabrik Pembuat",
                    value = data.manufacturer,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(manufacturer = it))) }
                )
                FormTextField(
                    label = "Tempat & Tahun Pembuatan",
                    value = data.locationAndYearOfManufacture,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(locationAndYearOfManufacture = it))) }
                )
                FormTextField(
                    label = "Nomor Seri",
                    value = data.serialNumber,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(serialNumber = it))) }
                )
                FormTextField(
                    label = "Kapasitas (kg)",
                    value = data.capacityInKg,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(capacityInKg = it))) }
                )
                FormTextField(
                    label = "Tinggi Angkat (meter)",
                    value = data.liftingHeightInMeters,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(liftingHeightInMeters = it))) }
                )
            }
        }

        // Visual Inspection Section
        item {
            val visualData = report.inspectionResult.visualCheck
            ExpandableSection(title = "PEMERIKSAAN VISUAL") {
                CheckboxWithLabel(
                    label = "Fork Mengalami Cacat",
                    checked = visualData.hasForkDefects,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(hasForkDefects = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Name Plate Terpasang",
                    checked = visualData.isNameplateAttached,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isNameplateAttached = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "APAR Tersedia",
                    checked = visualData.isAparAvailable,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isAparAvailable = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Penandaan Kapasitas Terpasang",
                    checked = visualData.isCapacityMarkingDisplayed,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isCapacityMarkingDisplayed = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Terdapat Kebocoran Hidrolik",
                    checked = visualData.hasHydraulicLeak,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(hasHydraulicLeak = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Kondisi Rantai Baik",
                    checked = visualData.isChainGoodCondition,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isChainGoodCondition = it)
                                )
                            )
                        )
                    }
                )
            }
        }

        // Functional Test Section
        item {
            val functionalData = report.inspectionResult.functionalTest
            ExpandableSection(title = "UJI FUNGSI") {
                FormTextField(
                    label = "Beban Uji (kg)",
                    value = functionalData.loadTestInKg,
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(loadTestInKg = it)
                                )
                            )
                        )
                    }
                )
                FormTextField(
                    label = "Tinggi Angkat Uji (meter)",
                    value = functionalData.loadTestLiftHeightInMeters,
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(loadTestLiftHeightInMeters = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Mampu Mengangkat dan Menahan",
                    checked = functionalData.isAbleToLiftAndHold,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(isAbleToLiftAndHold = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Berfungsi Dengan Baik",
                    checked = functionalData.isFunctioningWell,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(isFunctioningWell = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Ada Indikasi Retak",
                    checked = functionalData.hasCrackIndication,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(hasCrackIndication = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Stop Darurat Berfungsi",
                    checked = functionalData.isEmergencyStopFunctional,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(isEmergencyStopFunctional = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Lampu Peringatan dan Klakson Berfungsi",
                    checked = functionalData.isWarningLampAndHornFunctional,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(isWarningLampAndHornFunctional = it)
                                )
                            )
                        )
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
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

@Preview(showBackground = true, name = "Forklift BAP Screen Preview")
@Composable
private fun ForkliftBAPScreenPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                ForkliftBAPScreen(
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
