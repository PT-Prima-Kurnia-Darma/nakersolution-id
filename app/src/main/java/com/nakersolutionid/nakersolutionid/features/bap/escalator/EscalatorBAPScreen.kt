package com.nakersolutionid.nakersolutionid.features.bap.escalator

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
fun EscalatorBAPScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: BAPCreationViewModel = koinViewModel()
) {
    val uiState by viewModel.escalatorBAPUiState.collectAsStateWithLifecycle()
    val report = uiState.escalatorBAPReport
    val onDataChange = viewModel::onUpdateEscalatorBAPState

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
                    label = "Lokasi Perusahaan",
                    value = data.companyLocation,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(companyLocation = it))) }
                )
                FormTextField(
                    label = "Nama Lokasi Pemakaian",
                    value = data.nameUsageLocation,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(nameUsageLocation = it))) }
                )
                FormTextField(
                    label = "Alamat Lokasi Pemakaian",
                    value = data.locationUsageLocation,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(locationUsageLocation = it))) }
                )
            }
        }

        // Technical Data Section
        item {
            val data = report.technicalData
            ExpandableSection(title = "DATA TEKNIK") {
                FormTextField(
                    label = "Pabrik Pembuat",
                    value = data.manufacturer,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(manufacturer = it))) }
                )
                FormTextField(
                    label = "Merk",
                    value = data.brand,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(brand = it))) }
                )
                FormTextField(
                    label = "Negara & Tahun Pembuatan",
                    value = data.countryAndYear,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(countryAndYear = it))) }
                )
                FormTextField(
                    label = "Nomor Seri",
                    value = data.serialNumber,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(serialNumber = it))) }
                )
                FormTextField(
                    label = "Kapasitas",
                    value = data.capacity,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(capacity = it))) }
                )
                FormTextField(
                    label = "Kecepatan",
                    value = data.speed,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(speed = it))) }
                )
                FormTextField(
                    label = "Transportasi",
                    value = data.transports,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(transports = it))) }
                )
            }
        }

        // Visual Inspection Section
        item {
            val data = report.visualInspection
            ExpandableSection(title = "PEMERIKSAAN VISUAL") {
                CheckboxWithLabel(
                    label = "Kondisi Ruang Mesin Memenuhi Syarat",
                    checked = data.isMachineRoomConditionAcceptable,
                    onCheckedChange = { onDataChange(report.copy(visualInspection = data.copy(isMachineRoomConditionAcceptable = it))) }
                )
                CheckboxWithLabel(
                    label = "Kondisi Panel Dapat Diterima",
                    checked = data.isPanelConditionAcceptable,
                    onCheckedChange = { onDataChange(report.copy(visualInspection = data.copy(isPanelConditionAcceptable = it))) }
                )
                CheckboxWithLabel(
                    label = "Kondisi Penerangan Dapat Diterima",
                    checked = data.isLightingConditionAcceptable,
                    onCheckedChange = { onDataChange(report.copy(visualInspection = data.copy(isLightingConditionAcceptable = it))) }
                )
                CheckboxWithLabel(
                    label = "Rambu-rambu Keselamatan Tersedia",
                    checked = data.areSafetySignsAvailable,
                    onCheckedChange = { onDataChange(report.copy(visualInspection = data.copy(areSafetySignsAvailable = it))) }
                )
            }
        }

        // Testing Section
        item {
            val data = report.testing
            ExpandableSection(title = "PENGUJIAN") {
                CheckboxWithLabel(
                    label = "NDT Thermograph Panel OK",
                    checked = data.isNdtThermographPanelOk,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isNdtThermographPanelOk = it))) }
                )
                CheckboxWithLabel(
                    label = "Alat Pengaman Berfungsi",
                    checked = data.areSafetyDevicesFunctional,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(areSafetyDevicesFunctional = it))) }
                )
                CheckboxWithLabel(
                    label = "Limit Switch Berfungsi",
                    checked = data.isLimitSwitchFunctional,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isLimitSwitchFunctional = it))) }
                )
                CheckboxWithLabel(
                    label = "Saklar Pintu Berfungsi",
                    checked = data.isDoorSwitchFunctional,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isDoorSwitchFunctional = it))) }
                )
                CheckboxWithLabel(
                    label = "Stop Darurat 'Pit' Tersedia",
                    checked = data.isPitEmergencyStopAvailable,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isPitEmergencyStopAvailable = it))) }
                )
                CheckboxWithLabel(
                    label = "Stop Darurat 'Pit' Berfungsi",
                    checked = data.isPitEmergencyStopFunctional,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isPitEmergencyStopFunctional = it))) }
                )
                CheckboxWithLabel(
                    label = "Fungsi Escalator OK",
                    checked = data.isEscalatorFunctionOk,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isEscalatorFunctionOk = it))) }
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

@Preview(showBackground = true, name = "Escalator BAP Screen Preview")
@Composable
private fun EscalatorBAPScreenPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                EscalatorBAPScreen(
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
