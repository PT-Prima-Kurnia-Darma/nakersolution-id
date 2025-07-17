package com.nakersolutionid.nakersolutionid.features.bap.elevator

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
fun ElevatorBAPScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: BAPCreationViewModel = koinViewModel()
) {
    val uiState by viewModel.elevatorBAPUiState.collectAsStateWithLifecycle()
    val report = uiState.elevatorBAPReport
    val onDataChange = viewModel::onUpdateElevatorBAPState

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
                    label = "Lokasi Pemakaian",
                    value = data.nameUsageLocation,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(nameUsageLocation = it))) }
                )
                FormTextField(
                    label = "Alamat Lokasi Pemakaian",
                    value = data.addressUsageLocation,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(addressUsageLocation = it))) }
                )
            }
        }

        // Technical Data Section
        item {
            val data = report.technicalData
            ExpandableSection(title = "DATA TEKNIK") {
                FormTextField(
                    label = "Jenis Elevator",
                    value = data.elevatorType,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(elevatorType = it))) }
                )
                FormTextField(
                    label = "Pabrik Pembuat / Pemasang",
                    value = data.manufacturerOrInstaller,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(manufacturerOrInstaller = it))) }
                )
                FormTextField(
                    label = "Merk / Tipe",
                    value = data.brandOrType,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(brandOrType = it))) }
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
                    label = "Lantai yang Dilayani",
                    value = data.floorsServed,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(floorsServed = it))) }
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
                    label = "Kondisi Panel Baik",
                    checked = data.isPanelGoodCondition,
                    onCheckedChange = { onDataChange(report.copy(visualInspection = data.copy(isPanelGoodCondition = it))) }
                )
                CheckboxWithLabel(
                    label = "APAR Tersedia di Ruang Panel",
                    checked = data.isAparAvailableInPanelRoom,
                    onCheckedChange = { onDataChange(report.copy(visualInspection = data.copy(isAparAvailableInPanelRoom = it))) }
                )
                CheckboxWithLabel(
                    label = "Kondisi Penerangan Baik",
                    checked = data.lightingCondition,
                    onCheckedChange = { onDataChange(report.copy(visualInspection = data.copy(lightingCondition = it))) }
                )
                CheckboxWithLabel(
                    label = "Tangga 'Pit' Tersedia",
                    checked = data.isPitLadderAvailable,
                    onCheckedChange = { onDataChange(report.copy(visualInspection = data.copy(isPitLadderAvailable = it))) }
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
                    label = "ARD Berfungsi",
                    checked = data.isArdFunctional,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isArdFunctional = it))) }
                )
                CheckboxWithLabel(
                    label = "Governor Berfungsi",
                    checked = data.isGovernorFunctional,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isGovernorFunctional = it))) }
                )
                CheckboxWithLabel(
                    label = "Kondisi Sling OK (oleh Tester)",
                    checked = data.isSlingConditionOkByTester,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isSlingConditionOkByTester = it))) }
                )
                CheckboxWithLabel(
                    label = "Tes Limit Switch OK",
                    checked = data.limitSwitchTest,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(limitSwitchTest = it))) }
                )
                CheckboxWithLabel(
                    label = "Saklar Pintu Berfungsi",
                    checked = data.isDoorSwitchFunctional,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isDoorSwitchFunctional = it))) }
                )
                CheckboxWithLabel(
                    label = "Status Stop Darurat 'Pit' OK",
                    checked = data.pitEmergencyStopStatus,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(pitEmergencyStopStatus = it))) }
                )
                CheckboxWithLabel(
                    label = "Interkom Berfungsi",
                    checked = data.isIntercomFunctional,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isIntercomFunctional = it))) }
                )
                CheckboxWithLabel(
                    label = "Saklar Pemadam Kebakaran Berfungsi",
                    checked = data.isFiremanSwitchFunctional,
                    onCheckedChange = { onDataChange(report.copy(testing = data.copy(isFiremanSwitchFunctional = it))) }
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

@Preview(showBackground = true, name = "Elevator BAP Screen Preview")
@Composable
private fun ElevatorBAPScreenPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                ElevatorBAPScreen(
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