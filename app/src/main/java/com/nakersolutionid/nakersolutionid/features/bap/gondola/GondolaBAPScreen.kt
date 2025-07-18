package com.nakersolutionid.nakersolutionid.features.bap.gondola

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
fun GondolaBAPScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: BAPCreationViewModel = koinViewModel()
) {
    val uiState by viewModel.gondolaBAPUiState.collectAsStateWithLifecycle()
    val report = uiState.gondolaBAPReport
    val onDataChange = viewModel::onUpdateGondolaBAPState

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
                    label = "Nama Perusahaan",
                    value = data.companyName,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(companyName = it))) }
                )
                FormTextField(
                    label = "Lokasi Perusahaan",
                    value = data.companyLocation,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(companyLocation = it))) }
                )
                FormTextField(
                    label = "Penanggung Jawab Pengguna",
                    value = data.userInCharge,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(userInCharge = it))) }
                )
                FormTextField(
                    label = "Alamat Pemilik",
                    value = data.ownerAddress,
                    onValueChange = { onDataChange(report.copy(generalData = data.copy(ownerAddress = it))) }
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
                    label = "Tujuan Penggunaan",
                    value = data.intendedUse,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(intendedUse = it))) }
                )
                FormTextField(
                    label = "Kapasitas",
                    value = data.capacity,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(capacity = it))) }
                )
                FormTextField(
                    label = "Tinggi Angkat Maksimum (meter)",
                    value = data.maxLiftingHeightInMeters,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { onDataChange(report.copy(technicalData = data.copy(maxLiftingHeightInMeters = it))) }
                )
            }
        }

        // Visual Check Section
        item {
            val visualData = report.inspectionResult.visualCheck
            ExpandableSection(title = "PEMERIKSAAN VISUAL") {
                CheckboxWithLabel(
                    label = "Diameter Sling Dapat Diterima",
                    checked = visualData.isSlingDiameterAcceptable,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isSlingDiameterAcceptable = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Bumper Terpasang",
                    checked = visualData.isBumperInstalled,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isBumperInstalled = it)
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
                    label = "Kondisi Platform Dapat Diterima",
                    checked = visualData.isPlatformConditionAcceptable,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isPlatformConditionAcceptable = it)
                                )
                            )
                        )
                    }
                )

                // Drive Motor Condition Sub-section
                val motorData = visualData.driveMotorCondition
                Text(
                    text = "Kondisi Motor Penggerak",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
                CheckboxWithLabel(
                    label = "Kondisi Baik",
                    checked = motorData.isGoodCondition,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(
                                        driveMotorCondition = motorData.copy(isGoodCondition = it)
                                    )
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Terdapat Kebocoran Oli",
                    checked = motorData.hasOilLeak,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(
                                        driveMotorCondition = motorData.copy(hasOilLeak = it)
                                    )
                                )
                            )
                        )
                    }
                )

                CheckboxWithLabel(
                    label = "Panel Kontrol Bersih",
                    checked = visualData.isControlPanelClean,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isControlPanelClean = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Body Harness Tersedia",
                    checked = visualData.isBodyHarnessAvailable,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isBodyHarnessAvailable = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Lifeline Tersedia",
                    checked = visualData.isLifelineAvailable,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isLifelineAvailable = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Label Tombol Terpasang",
                    checked = visualData.isButtonLabelsDisplayed,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    visualCheck = visualData.copy(isButtonLabelsDisplayed = it)
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
                CheckboxWithLabel(
                    label = "Pengukuran Wire Rope OK",
                    checked = functionalData.isWireRopeMeasurementOk,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(isWireRopeMeasurementOk = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Fungsi Naik Turun OK",
                    checked = functionalData.isUpDownFunctionOk,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(isUpDownFunctionOk = it)
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Fungsi Motor Penggerak OK",
                    checked = functionalData.isDriveMotorFunctionOk,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(isDriveMotorFunctionOk = it)
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
                    label = "Safety Lifeline Berfungsi",
                    checked = functionalData.isSafetyLifelineFunctional,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(isSafetyLifelineFunctional = it)
                                )
                            )
                        )
                    }
                )

                // NDT Test Sub-section
                val ndtData = functionalData.ndtTest
                Text(
                    text = "Uji NDT",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                FormTextField(
                    label = "Metode",
                    value = ndtData.method,
                    onValueChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(
                                        ndtTest = ndtData.copy(method = it)
                                    )
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Hasil Baik",
                    checked = ndtData.isResultGood,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(
                                        ndtTest = ndtData.copy(isResultGood = it)
                                    )
                                )
                            )
                        )
                    }
                )
                CheckboxWithLabel(
                    label = "Ada Indikasi Retak",
                    checked = ndtData.hasCrackIndication,
                    onCheckedChange = {
                        onDataChange(
                            report.copy(
                                inspectionResult = report.inspectionResult.copy(
                                    functionalTest = functionalData.copy(
                                        ndtTest = ndtData.copy(hasCrackIndication = it)
                                    )
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

@Preview(showBackground = true, name = "Gondola BAP Screen Preview")
@Composable
private fun GondolaBAPScreenPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                GondolaBAPScreen(
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
