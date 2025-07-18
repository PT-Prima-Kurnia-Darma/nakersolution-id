package com.nakersolutionid.nakersolutionid.features.bap.ptp

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
fun PtpBAPScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: BAPCreationViewModel = koinViewModel()
) {
    val uiState by viewModel.ptpBAPUiState.collectAsStateWithLifecycle()
    val report = uiState.report
    val onDataChange = viewModel::onUpdatePtpBAPState

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
                FormTextField(label = "Merk / Tipe", value = data.brandOrType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(brandOrType = it))) })
                FormTextField(label = "Pabrik Pembuat", value = data.manufacturer, onValueChange = { onDataChange(report.copy(technicalData = data.copy(manufacturer = it))) })
                FormTextField(label = "Tahun Pembuatan", value = data.manufactureYear, onValueChange = { onDataChange(report.copy(technicalData = data.copy(manufactureYear = it))) })
                FormTextField(label = "Negara Pembuat", value = data.manufactureCountry, onValueChange = { onDataChange(report.copy(technicalData = data.copy(manufactureCountry = it))) })
                FormTextField(label = "Nomor Seri", value = data.serialNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(serialNumber = it))) })
                FormTextField(label = "Deskripsi Kapasitas", value = data.capacityDescription, onValueChange = { onDataChange(report.copy(technicalData = data.copy(capacityDescription = it))) })
                FormTextField(label = "Daya Motor Penggerak (KW)", value = data.driveMotorPowerKw, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotorPowerKw = it))) })
                FormTextField(label = "Spesifikasi Khusus", value = data.specialSpecification, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specialSpecification = it))) })
                FormTextField(label = "Deskripsi Dimensi", value = data.dimensionsDescription, onValueChange = { onDataChange(report.copy(technicalData = data.copy(dimensionsDescription = it))) })
                FormTextField(label = "Rotasi (RPM)", value = data.rotationRpm, onValueChange = { onDataChange(report.copy(technicalData = data.copy(rotationRpm = it))) })
                FormTextField(label = "Frekuensi (Hz)", value = data.frequencyHz, onValueChange = { onDataChange(report.copy(technicalData = data.copy(frequencyHz = it))) })
                FormTextField(label = "Arus (Ampere)", value = data.currentAmperes, onValueChange = { onDataChange(report.copy(technicalData = data.copy(currentAmperes = it))) })
                FormTextField(label = "Berat Mesin (Kg)", value = data.machineWeightKg, onValueChange = { onDataChange(report.copy(technicalData = data.copy(machineWeightKg = it))) })
                CheckboxWithLabel(label = "Fitur Keselamatan Terpasang", checked = data.areSafetyFeaturesInstalled, onCheckedChange = { onDataChange(report.copy(technicalData = data.copy(areSafetyFeaturesInstalled = it))) })
            }
        }

        // Test Results Section
        item {
            val data = report.testResults
            ExpandableSection(title = "HASIL PEMERIKSAAN & PENGUJIAN") {
                // Visual Inspection
                Text("Pemeriksaan Visual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                CheckboxWithLabel(label = "Kondisi Mesin Baik", checked = data.visualInspection.isMachineGoodCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isMachineGoodCondition = it)))) })
                CheckboxWithLabel(label = "Indikator Elektrikal Baik", checked = data.visualInspection.areElectricalIndicatorsGood, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(areElectricalIndicatorsGood = it)))) })

                Text("Alat Pelindung Diri", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top=8.dp, bottom = 4.dp))
                CheckboxWithLabel(label = "APAR Tersedia", checked = data.visualInspection.personalProtectiveEquipment.isAparAvailable, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(personalProtectiveEquipment = data.visualInspection.personalProtectiveEquipment.copy(isAparAvailable = it))))) })
                CheckboxWithLabel(label = "APD Tersedia", checked = data.visualInspection.personalProtectiveEquipment.isPpeAvailable, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(personalProtectiveEquipment = data.visualInspection.personalProtectiveEquipment.copy(isPpeAvailable = it))))) })

                CheckboxWithLabel(label = "Pembumian Terpasang", checked = data.visualInspection.isGroundingInstalled, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isGroundingInstalled = it)))) })
                CheckboxWithLabel(label = "Kondisi Baterai Baik", checked = data.visualInspection.isBatteryGoodCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isBatteryGoodCondition = it)))) })
                CheckboxWithLabel(label = "Ada Kebocoran Pelumasan", checked = data.visualInspection.hasLubricationLeak, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(hasLubricationLeak = it)))) })
                CheckboxWithLabel(label = "Kondisi Pondasi Baik", checked = data.visualInspection.isFoundationGoodCondition, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(isFoundationGoodCondition = it)))) })
                CheckboxWithLabel(label = "Ada Kebocoran Hidrolik", checked = data.visualInspection.hasHydraulicLeak, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(visualInspection = data.visualInspection.copy(hasHydraulicLeak = it)))) })

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Testing
                Text("Pengujian", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                CheckboxWithLabel(label = "Penerangan Sesuai", checked = data.testing.isLightingCompliant, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isLightingCompliant = it)))) })
                CheckboxWithLabel(label = "Tingkat Kebisingan Sesuai", checked = data.testing.isNoiseLevelCompliant, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isNoiseLevelCompliant = it)))) })
                CheckboxWithLabel(label = "Stop Darurat Berfungsi", checked = data.testing.isEmergencyStopFunctional, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isEmergencyStopFunctional = it)))) })
                CheckboxWithLabel(label = "Mesin Berfungsi", checked = data.testing.isMachineFunctional, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isMachineFunctional = it)))) })
                CheckboxWithLabel(label = "Tingkat Getaran Sesuai", checked = data.testing.isVibrationLevelCompliant, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isVibrationLevelCompliant = it)))) })
                CheckboxWithLabel(label = "Resistansi Isolasi OK", checked = data.testing.isInsulationResistanceOk, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isInsulationResistanceOk = it)))) })
                CheckboxWithLabel(label = "Rotasi Poros Sesuai", checked = data.testing.isShaftRotationCompliant, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isShaftRotationCompliant = it)))) })
                CheckboxWithLabel(label = "Resistansi Pembumian Sesuai", checked = data.testing.isGroundingResistanceCompliant, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isGroundingResistanceCompliant = it)))) })
                CheckboxWithLabel(label = "Uji NDT Las OK", checked = data.testing.isNdtWeldTestOk, onCheckedChange = { onDataChange(report.copy(testResults = data.copy(testing = data.testing.copy(isNdtWeldTestOk = it)))) })
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

@Preview(showBackground = true, name = "PTP BAP Screen Preview")
@Composable
private fun PtpBAPScreenPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                PtpBAPScreen(
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