package com.nakersolutionid.nakersolutionid.features.report.paa.mobilecrane

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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.report.paa.PAAViewModel
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@Composable
fun MobileCraneScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: PAAViewModel = koinViewModel()
) {
    val uiState by viewModel.mobileCraneUiState.collectAsStateWithLifecycle()
    val report = uiState.mobileCraneInspectionReport
    val onDataChange = viewModel::onMobileCraneReportDataChange

    var showNdeWireRopeDialog by remember { mutableStateOf(false) }
    var showNdeBoomDialog by remember { mutableStateOf(false) }
    var showDynMainLoadDialog by remember { mutableStateOf(false) }
    var showDynAuxLoadDialog by remember { mutableStateOf(false) }
    var showStaMainLoadDialog by remember { mutableStateOf(false) }
    var showStaAuxLoadDialog by remember { mutableStateOf(false) }
    var showSummaryDialog by remember { mutableStateOf(false) }
    var showRecommendationDialog by remember { mutableStateOf(false) }

    //region Dialog State Handling
    if (showNdeWireRopeDialog) {
        AddMobileCraneNdeWireRopeItemDialog(
            onDismissRequest = { showNdeWireRopeDialog = false },
            onConfirm = {
                viewModel.addMobileCraneNdeWireRopeItem(it)
                showNdeWireRopeDialog = false
            }
        )
    }
    if (showNdeBoomDialog) {
        AddMobileCraneNdeBoomItemDialog(
            onDismissRequest = { showNdeBoomDialog = false },
            onConfirm = {
                viewModel.addMobileCraneNdeBoomItem(it)
                showNdeBoomDialog = false
            }
        )
    }
    if (showDynMainLoadDialog) {
        AddMobileCraneLoadTestItemDialog(
            onDismissRequest = { showDynMainLoadDialog = false },
            onConfirm = {
                viewModel.addMobileCraneDynamicMainHookLoadTestItem(it)
                showDynMainLoadDialog = false
            }
        )
    }
    if (showDynAuxLoadDialog) {
        AddMobileCraneLoadTestItemDialog(
            onDismissRequest = { showDynAuxLoadDialog = false },
            onConfirm = {
                viewModel.addMobileCraneDynamicAuxHookLoadTestItem(it)
                showDynAuxLoadDialog = false
            }
        )
    }
    if (showStaMainLoadDialog) {
        AddMobileCraneLoadTestItemDialog(
            onDismissRequest = { showStaMainLoadDialog = false },
            onConfirm = {
                viewModel.addMobileCraneStaticMainHookLoadTestItem(it)
                showStaMainLoadDialog = false
            }
        )
    }
    if (showStaAuxLoadDialog) {
        AddMobileCraneLoadTestItemDialog(
            onDismissRequest = { showStaAuxLoadDialog = false },
            onConfirm = {
                viewModel.addMobileCraneStaticAuxHookLoadTestItem(it)
                showStaAuxLoadDialog = false
            }
        )
    }
    if (showSummaryDialog) {
        AddStringDialog(
            title = "Tambah Poin Kesimpulan",
            label = "Poin Kesimpulan",
            onDismissRequest = { showSummaryDialog = false },
            onConfirm = {
                viewModel.addMobileCraneConclusionSummaryItem(it)
                showSummaryDialog = false
            }
        )
    }
    if (showRecommendationDialog) {
        AddStringDialog(
            title = "Tambah Poin Saran",
            label = "Poin Saran",
            onDismissRequest = { showRecommendationDialog = false },
            onConfirm = {
                viewModel.addMobileCraneConclusionRecommendationItem(it)
                showRecommendationDialog = false
            }
        )
    }
    //endregion

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        // Main Data Section
        item {
            val data = report
            ExpandableSection(title = "DATA UTAMA", initiallyExpanded = true) {
                FormTextField(label = "Jenis Pemeriksaan", value = data.examinationType, onValueChange = { onDataChange(data.copy(examinationType = it)) })
                FormTextField(label = "Jenis Pesawat Angkat", value = data.equipmentType, onValueChange = { onDataChange(data.copy(equipmentType = it)) })
            }
        }

        item {
            val data = report.generalData
            ExpandableSection(title = "DATA UMUM", initiallyExpanded = false) {
                FormTextField(label = "Pemilik", value = data.owner, onValueChange = { onDataChange(report.copy(generalData = data.copy(owner = it))) })
                FormTextField(label = "Alamat", value = data.address, onValueChange = { onDataChange(report.copy(generalData = data.copy(address = it))) })
                FormTextField(label = "Pemakai / Penanggung Jawab", value = data.personInCharge, onValueChange = { onDataChange(report.copy(generalData = data.copy(personInCharge = it))) })
                FormTextField(label = "Alamat Pemakai", value = data.userAddress, onValueChange = { onDataChange(report.copy(generalData = data.copy(userAddress = it))) })
                FormTextField(label = "Lokasi Unit", value = data.unitLocation, onValueChange = { onDataChange(report.copy(generalData = data.copy(unitLocation = it))) })
                FormTextField(label = "Nama Operator", value = data.operatorName, onValueChange = { onDataChange(report.copy(generalData = data.copy(operatorName = it))) })
                FormTextField(label = "Jenis Pesawat", value = data.driveType, onValueChange = { onDataChange(report.copy(generalData = data.copy(driveType = it))) })
                FormTextField(label = "Pabrik Pembuat", value = data.manufacturer, onValueChange = { onDataChange(report.copy(generalData = data.copy(manufacturer = it))) })
                FormTextField(label = "Merek / Tipe", value = data.brandType, onValueChange = { onDataChange(report.copy(generalData = data.copy(brandType = it))) })
                FormTextField(label = "Tahun Pembuatan", value = data.yearOfManufacture, onValueChange = { onDataChange(report.copy(generalData = data.copy(yearOfManufacture = it))) })
                FormTextField(label = "No. Seri / No. Unit", value = data.serialNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(serialNumber = it))) })
                FormTextField(label = "Kapasitas Angkat", value = data.liftingCapacity, onValueChange = { onDataChange(report.copy(generalData = data.copy(liftingCapacity = it))) })
                FormTextField(label = "Digunakan Untuk", value = data.intendedUse, onValueChange = { onDataChange(report.copy(generalData = data.copy(intendedUse = it))) })
                FormTextField(label = "Nomor Izin Pemakaian", value = data.permitNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(permitNumber = it))) })
                FormTextField(label = "Sertifikat Operator", value = data.operatorCertificate, onValueChange = { onDataChange(report.copy(generalData = data.copy(operatorCertificate = it))) })
                FormTextField(label = "Data Riwayat Pesawat", value = data.equipmentHistory, onValueChange = { onDataChange(report.copy(generalData = data.copy(equipmentHistory = it))) })
            }
        }

        item {
            val data = report.technicalData
            ExpandableSection(title = "DATA TEKNIK") {
                ExpandableSubSection(title = "Spesifikasi") {
                    val spec = data.specifications
                    FormTextField(label = "Kapasitas Kerja Maksimum", value = spec.maximumWorkingLoadCapacity, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = spec.copy(maximumWorkingLoadCapacity = it)))) })
                    FormTextField(label = "Panjang Boom", value = spec.boomLength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = spec.copy(boomLength = it)))) })
                    FormTextField(label = "Panjang Jib Maksimum", value = spec.maximumJibLength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = spec.copy(maximumJibLength = it)))) })
                    FormTextField(label = "Bobot Kerja Maksimum Jib", value = spec.maximumJibWorkingLoad, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = spec.copy(maximumJibWorkingLoad = it)))) })
                    FormTextField(label = "Panjang Boom + Jib Maks.", value = spec.maxBoomJibLength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = spec.copy(maxBoomJibLength = it)))) })
                    FormTextField(label = "Berat Crane", value = spec.craneWeight, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = spec.copy(craneWeight = it)))) })
                    FormTextField(label = "Maks. Tinggi Angkat", value = spec.maxLiftingHeight, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = spec.copy(maxLiftingHeight = it)))) })
                    FormTextField(label = "Sudut Kerja Boom", value = spec.boomWorkingAngle, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = spec.copy(boomWorkingAngle = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection(title = "Motor Penggerak") {
                    val motor = data.driveMotor
                    FormTextField(label = "No. Mesin", value = motor.engineNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = motor.copy(engineNumber = it)))) })
                    FormTextField(label = "Tipe", value = motor.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = motor.copy(type = it)))) })
                    FormTextField(label = "Jumlah Silinder", value = motor.numberOfCylinders, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = motor.copy(numberOfCylinders = it)))) })
                    FormTextField(label = "Daya Bersih", value = motor.netPower, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = motor.copy(netPower = it)))) })
                    FormTextField(label = "Merek / Tahun Pembuatan", value = motor.brandYearOfManufacture, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = motor.copy(brandYearOfManufacture = it)))) })
                    FormTextField(label = "Pabrik Pembuat", value = motor.manufacturer, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = motor.copy(manufacturer = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection(title = "Kait (Hook)") {
                    val main = data.mainHook
                    val aux = data.auxiliaryHook
                    Text("Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    FormTextField(label = "Type", value = main.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(mainHook = main.copy(type = it)))) })
                    FormTextField(label = "Kapasitas", value = main.capacity, onValueChange = { onDataChange(report.copy(technicalData = data.copy(mainHook = main.copy(capacity = it)))) })
                    FormTextField(label = "Material", value = main.material, onValueChange = { onDataChange(report.copy(technicalData = data.copy(mainHook = main.copy(material = it)))) })
                    FormTextField(label = "No. Seri", value = main.serialNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(mainHook = main.copy(serialNumber = it)))) })
                    Spacer(Modifier.height(8.dp))
                    Text("Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    FormTextField(label = "Type", value = aux.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(auxiliaryHook = aux.copy(type = it)))) })
                    FormTextField(label = "Kapasitas", value = aux.capacity, onValueChange = { onDataChange(report.copy(technicalData = data.copy(auxiliaryHook = aux.copy(capacity = it)))) })
                    FormTextField(label = "Material", value = aux.material, onValueChange = { onDataChange(report.copy(technicalData = data.copy(auxiliaryHook = aux.copy(material = it)))) })
                    FormTextField(label = "No. Seri", value = aux.serialNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(auxiliaryHook = aux.copy(serialNumber = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection(title = "Tali Kawat Baja (Wire Rope)") {
                    val rope = data.wireRope
                    Text("Main Load Hoist Drum", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    FormTextField(label = "Diameter", value = rope.mainLoadHoistDrum.diameter, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(mainLoadHoistDrum = rope.mainLoadHoistDrum.copy(diameter = it))))) })
                    FormTextField(label = "Type", value = rope.mainLoadHoistDrum.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(mainLoadHoistDrum = rope.mainLoadHoistDrum.copy(type = it))))) })
                    FormTextField(label = "Konstruksi", value = rope.mainLoadHoistDrum.construction, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(mainLoadHoistDrum = rope.mainLoadHoistDrum.copy(construction = it))))) })
                    FormTextField(label = "Breaking Strength", value = rope.mainLoadHoistDrum.breakingStrength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(mainLoadHoistDrum = rope.mainLoadHoistDrum.copy(breakingStrength = it))))) })
                    FormTextField(label = "Panjang", value = rope.mainLoadHoistDrum.length, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(mainLoadHoistDrum = rope.mainLoadHoistDrum.copy(length = it))))) })
                    Spacer(Modifier.height(8.dp))
                    Text("Auxiliary Load Hoist Drum", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    FormTextField(label = "Diameter", value = rope.auxiliaryLoadHoistDrum.diameter, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(auxiliaryLoadHoistDrum = rope.auxiliaryLoadHoistDrum.copy(diameter = it))))) })
                    FormTextField(label = "Type", value = rope.auxiliaryLoadHoistDrum.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(auxiliaryLoadHoistDrum = rope.auxiliaryLoadHoistDrum.copy(type = it))))) })
                    FormTextField(label = "Konstruksi", value = rope.auxiliaryLoadHoistDrum.construction, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(auxiliaryLoadHoistDrum = rope.auxiliaryLoadHoistDrum.copy(construction = it))))) })
                    FormTextField(label = "Breaking Strength", value = rope.auxiliaryLoadHoistDrum.breakingStrength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(auxiliaryLoadHoistDrum = rope.auxiliaryLoadHoistDrum.copy(breakingStrength = it))))) })
                    FormTextField(label = "Panjang", value = rope.auxiliaryLoadHoistDrum.length, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(auxiliaryLoadHoistDrum = rope.auxiliaryLoadHoistDrum.copy(length = it))))) })
                    Spacer(Modifier.height(8.dp))
                    Text("Boom Hoist Drum", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    FormTextField(label = "Diameter", value = rope.boomHoistDrum.diameter, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(boomHoistDrum = rope.boomHoistDrum.copy(diameter = it))))) })
                    FormTextField(label = "Type", value = rope.boomHoistDrum.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(boomHoistDrum = rope.boomHoistDrum.copy(type = it))))) })
                    FormTextField(label = "Konstruksi", value = rope.boomHoistDrum.construction, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(boomHoistDrum = rope.boomHoistDrum.copy(construction = it))))) })
                    FormTextField(label = "Breaking Strength", value = rope.boomHoistDrum.breakingStrength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(boomHoistDrum = rope.boomHoistDrum.copy(breakingStrength = it))))) })
                    FormTextField(label = "Panjang", value = rope.boomHoistDrum.length, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = rope.copy(boomHoistDrum = rope.boomHoistDrum.copy(length = it))))) })
                }
            }
        }

        item {
            val v = report.visualInspection
            ExpandableSection(title = "PEMERIKSAAN VISUAL") {
                ExpandableSubSection("Pondasi, Rangka & Akses") {
                    Text("Pondasi Baut Pengikat", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    VisualInspectionInput(label = "Korosi", value = v.foundationAnchorBoltCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(foundationAnchorBoltCorrosion = it))) })
                    VisualInspectionInput(label = "Keretakan", value = v.foundationAnchorBoltCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(foundationAnchorBoltCracks = it))) })
                    VisualInspectionInput(label = "Perubahan Bentuk", value = v.foundationAnchorBoltDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(foundationAnchorBoltDeformation = it))) })
                    VisualInspectionInput(label = "Kekencangan", value = v.foundationAnchorBoltTightness, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(foundationAnchorBoltTightness = it))) })

                    Text("Kolom Rangka pada Pondasi", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Korosi", value = v.frameColumnsOnFoundationCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(frameColumnsOnFoundationCorrosion = it))) })
                    VisualInspectionInput(label = "Keretakan", value = v.frameColumnsOnFoundationCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(frameColumnsOnFoundationCracks = it))) })
                    VisualInspectionInput(label = "Perubahan Bentuk", value = v.frameColumnsOnFoundationDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(frameColumnsOnFoundationDeformation = it))) })
                    VisualInspectionInput(label = "Pengikat", value = v.frameColumnsOnFoundationFastening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(frameColumnsOnFoundationFastening = it))) })
                    VisualInspectionInput(label = "Penguat Melintang", value = v.frameColumnsOnFoundationTransverseReinforcement, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(frameColumnsOnFoundationTransverseReinforcement = it))) })
                    VisualInspectionInput(label = "Penguat Diagonal", value = v.frameColumnsOnFoundationDiagonalReinforcement, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(frameColumnsOnFoundationDiagonalReinforcement = it))) })

                    Text("Tangga", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Korosi", value = v.ladderCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(ladderCorrosion = it))) })
                    VisualInspectionInput(label = "Keretakan", value = v.ladderCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(ladderCracks = it))) })
                    VisualInspectionInput(label = "Perubahan Bentuk", value = v.ladderDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(ladderDeformation = it))) })
                    VisualInspectionInput(label = "Pengikat", value = v.ladderFastening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(ladderFastening = it))) })

                    Text("Lantai Kerja", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Korosi", value = v.workingPlatformCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(workingPlatformCorrosion = it))) })
                    VisualInspectionInput(label = "Keretakan", value = v.workingPlatformCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(workingPlatformCracks = it))) })
                    VisualInspectionInput(label = "Perubahan Bentuk", value = v.workingPlatformDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(workingPlatformDeformation = it))) })
                    VisualInspectionInput(label = "Pengikat", value = v.workingPlatformFastening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(workingPlatformFastening = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Struktur & Mekanisme") {
                    Text("Kaki Penumpu (Outrigger)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    VisualInspectionInput(label = "Rumah Lengan", value = v.outriggersOutriggerArmHousing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(outriggersOutriggerArmHousing = it))) })
                    VisualInspectionInput(label = "Lengan Penumpu", value = v.outriggersOutriggerArms, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(outriggersOutriggerArms = it))) })
                    VisualInspectionInput(label = "Dongkrak", value = v.outriggersJack, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(outriggersJack = it))) })
                    VisualInspectionInput(label = "Telapak Penumpu", value = v.outriggersOutriggerPads, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(outriggersOutriggerPads = it))) })
                    VisualInspectionInput(label = "Sambungan ke Chasis", value = v.outriggersHousingConnectionToChassis, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(outriggersHousingConnectionToChassis = it))) })
                    VisualInspectionInput(label = "Kunci Pengaman", value = v.outriggersOutriggerSafetyLocks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(outriggersOutriggerSafetyLocks = it))) })

                    Text("Meja Putar", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Bantalan Roller (Slewing)", value = v.turntableSlewingRollerBearing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(turntableSlewingRollerBearing = it))) })
                    VisualInspectionInput(label = "Rumah Rem", value = v.turntableBrakeHousing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(turntableBrakeHousing = it))) })
                    VisualInspectionInput(label = "Kampas & Sepatu Rem", value = v.turntableBrakeLiningsAndShoes, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(turntableBrakeLiningsAndShoes = it))) })
                    VisualInspectionInput(label = "Permukaan Tromol", value = v.turntableDrumSurface, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(turntableDrumSurface = it))) })
                    VisualInspectionInput(label = "Silinder Penekan", value = v.turntablePressureCylinder, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(turntablePressureCylinder = it))) })
                    VisualInspectionInput(label = "As Tromol", value = v.turntableDrumAxle, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(turntableDrumAxle = it))) })
                    VisualInspectionInput(label = "Tuas, Pin, Baut", value = v.turntableLeversPinsBolts, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(turntableLeversPinsBolts = it))) })
                    VisualInspectionInput(label = "Pelindung", value = v.turntableGuard, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(turntableGuard = it))) })

                    Text("Boom Latice", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Boom Utama", value = v.latticeBoomMainBoom, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(latticeBoomMainBoom = it))) })
                    VisualInspectionInput(label = "Boom Seksi", value = v.latticeBoomBoomSection, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(latticeBoomBoomSection = it))) })
                    VisualInspectionInput(label = "Puli Atas", value = v.latticeBoomTopPulley, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(latticeBoomTopPulley = it))) })
                    VisualInspectionInput(label = "Pelindung Puli", value = v.latticeBoomPulleyGuard, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(latticeBoomPulleyGuard = it))) })
                    VisualInspectionInput(label = "Penghadang Tali Kawat Baja", value = v.latticeBoomWireRopeGuard, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(latticeBoomWireRopeGuard = it))) })
                    VisualInspectionInput(label = "Alur Bibir Puli", value = v.latticeBoomPulleyGrooveLip, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(latticeBoomPulleyGrooveLip = it))) })
                    VisualInspectionInput(label = "Pin Pivot", value = v.latticeBoomPivotPin, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(latticeBoomPivotPin = it))) })
                    VisualInspectionInput(label = "Puli Penghantar Tali", value = v.latticeBoomWireRopeGuidePulley, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(latticeBoomWireRopeGuidePulley = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Sistem Penggerak (Transmisi, Rem, Winch)") {
                    Text("Kopling, Transmisi, Kemudi", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    VisualInspectionInput(label = "Kopling Utama", value = v.clutchMainClutch, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(clutchMainClutch = it))) })
                    VisualInspectionInput(label = "Transmisi", value = v.transmission, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(transmission = it))) })
                    VisualInspectionInput(label = "Roda Depan", value = v.steeringFrontWheel, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(steeringFrontWheel = it))) })
                    VisualInspectionInput(label = "Roda Tengah", value = v.steeringMiddleWheel, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(steeringMiddleWheel = it))) })
                    VisualInspectionInput(label = "Roda Belakang", value = v.steeringRearWheel, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(steeringRearWheel = it))) })

                    Text("Rem", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Rem Jalan", value = v.brakeServiceBrake, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(brakeServiceBrake = it))) })
                    VisualInspectionInput(label = "Rem Parkir", value = v.brakeParkingBrake, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(brakeParkingBrake = it))) })
                    VisualInspectionInput(label = "Rumah Rem", value = v.brakeBrakeHousing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(brakeBrakeHousing = it))) })
                    VisualInspectionInput(label = "Kampas & Sepatu Rem", value = v.brakeBrakeLiningsAndShoes, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(brakeBrakeLiningsAndShoes = it))) })
                    VisualInspectionInput(label = "Permukaan Tromol", value = v.brakeDrumSurface, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(brakeDrumSurface = it))) })
                    VisualInspectionInput(label = "Tuas, Pin, Baut", value = v.brakeLeversPinsBolts, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(brakeLeversPinsBolts = it))) })
                    VisualInspectionInput(label = "Pelindung", value = v.brakeGuard, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(brakeGuard = it))) })

                    Text("Tromol Jalan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Rumah Kopling", value = v.travelDrumClutchHousing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelDrumClutchHousing = it))) })
                    VisualInspectionInput(label = "Kampas Kopling", value = v.travelDrumClutchLining, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelDrumClutchLining = it))) })
                    VisualInspectionInput(label = "Permukaan Tromol Kopling", value = v.travelDrumClutchDrumSurface, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelDrumClutchDrumSurface = it))) })
                    VisualInspectionInput(label = "Tuas, Pin, Baut", value = v.travelDrumLeversPinsBolts, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelDrumLeversPinsBolts = it))) })
                    VisualInspectionInput(label = "Pelindung", value = v.travelDrumGuard, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelDrumGuard = it))) })

                    Text("Winch Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Dudukan Drum", value = v.mainWinchDrumMounting, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchDrumMounting = it))) })
                    VisualInspectionInput(label = "Permukaan Tromol Gulung", value = v.mainWinchWindingDrumSurface, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchWindingDrumSurface = it))) })
                    VisualInspectionInput(label = "Kampas & Sepatu Rem", value = v.mainWinchBrakeLiningsAndShoes, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchBrakeLiningsAndShoes = it))) })
                    VisualInspectionInput(label = "Permukaan Tromol Rem", value = v.mainWinchBrakeDrumSurface, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchBrakeDrumSurface = it))) })
                    VisualInspectionInput(label = "Rumah Rem", value = v.mainWinchBrakeHousing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchBrakeHousing = it))) })
                    VisualInspectionInput(label = "Kampas & Sepatu Kopling", value = v.mainWinchClutchLiningsAndShoes, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchClutchLiningsAndShoes = it))) })
                    VisualInspectionInput(label = "Permukaan Tromol Kopling", value = v.mainWinchClutchDrumSurface, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchClutchDrumSurface = it))) })
                    VisualInspectionInput(label = "Alur", value = v.mainWinchGroove, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchGroove = it))) })
                    VisualInspectionInput(label = "Bibir Alur", value = v.mainWinchGrooveLip, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchGrooveLip = it))) })
                    VisualInspectionInput(label = "Flensa", value = v.mainWinchFlanges, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchFlanges = it))) })
                    VisualInspectionInput(label = "Penggerak Rem", value = v.mainWinchBrakeActuatorLeversPinsAndBolts, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWinchBrakeActuatorLeversPinsAndBolts = it))) })

                    Text("Winch Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Dudukan Drum", value = v.auxiliaryWinchDrumMounting, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchDrumMounting = it))) })
                    VisualInspectionInput(label = "Permukaan Tromol Gulung", value = v.auxiliaryWinchWindingDrumSurface, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchWindingDrumSurface = it))) })
                    VisualInspectionInput(label = "Kampas & Sepatu Rem", value = v.auxiliaryWinchBrakeLiningsAndShoes, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchBrakeLiningsAndShoes = it))) })
                    VisualInspectionInput(label = "Permukaan Tromol Rem", value = v.auxiliaryWinchBrakeDrumSurface, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchBrakeDrumSurface = it))) })
                    VisualInspectionInput(label = "Rumah Rem", value = v.auxiliaryWinchBrakeHousing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchBrakeHousing = it))) })
                    VisualInspectionInput(label = "Kampas & Sepatu Kopling", value = v.auxiliaryWinchClutchLiningsAndShoes, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchClutchLiningsAndShoes = it))) })
                    VisualInspectionInput(label = "Permukaan Tromol Kopling", value = v.auxiliaryWinchClutchDrumSurface, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchClutchDrumSurface = it))) })
                    VisualInspectionInput(label = "Alur", value = v.auxiliaryWinchGroove, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchGroove = it))) })
                    VisualInspectionInput(label = "Bibir Alur", value = v.auxiliaryWinchGrooveLip, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchGrooveLip = it))) })
                    VisualInspectionInput(label = "Flensa", value = v.auxiliaryWinchFlanges, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchFlanges = it))) })
                    VisualInspectionInput(label = "Penggerak Rem", value = v.auxiliaryWinchBrakeActuatorLeversPinsAndBolts, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWinchBrakeActuatorLeversPinsAndBolts = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Kait, Puli & Tali Kawat Baja") {
                    Text("Hoist Gear Block", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    VisualInspectionInput(label = "Pelumasan", value = v.hoistGearBlockLubrication, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hoistGearBlockLubrication = it))) })
                    VisualInspectionInput(label = "Oli Seal", value = v.hoistGearBlockOilSeal, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hoistGearBlockOilSeal = it))) })

                    Text("Puli Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Alur Puli", value = v.mainPulleyPulleyGroove, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainPulleyPulleyGroove = it))) })
                    VisualInspectionInput(label = "Bibir Alur Puli", value = v.mainPulleyPulleyGrooveLip, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainPulleyPulleyGrooveLip = it))) })
                    VisualInspectionInput(label = "Pin Puli", value = v.mainPulleyPulleyPin, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainPulleyPulleyPin = it))) })
                    VisualInspectionInput(label = "Bantalan", value = v.mainPulleyBearing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainPulleyBearing = it))) })
                    VisualInspectionInput(label = "Pelindung Puli", value = v.mainPulleyPulleyGuard, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainPulleyPulleyGuard = it))) })
                    VisualInspectionInput(label = "Penghadang Tali", value = v.mainPulleyWireRopeGuard, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainPulleyWireRopeGuard = it))) })

                    Text("Kait Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Mur & Bantalan Putar", value = v.mainHookVisualSwivelNutAndBearing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainHookVisualSwivelNutAndBearing = it))) })
                    VisualInspectionInput(label = "Trunnion", value = v.mainHookVisualTrunnion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainHookVisualTrunnion = it))) })
                    VisualInspectionInput(label = "Kunci Kait (Safety Latch)", value = v.mainHookVisualSafetyLatch, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainHookVisualSafetyLatch = it))) })

                    Text("Kait Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Pemberat (Free Fall)", value = v.auxiliaryHookVisualFreeFallWeight, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryHookVisualFreeFallWeight = it))) })
                    VisualInspectionInput(label = "Mur & Bantalan Putar", value = v.auxiliaryHookVisualSwivelNutAndBearing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryHookVisualSwivelNutAndBearing = it))) })
                    VisualInspectionInput(label = "Kunci Kait (Safety Latch)", value = v.auxiliaryHookVisualSafetyLatch, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryHookVisualSafetyLatch = it))) })

                    Text("Tali Kawat Baja Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Korosi", value = v.mainWireRopeVisualCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWireRopeVisualCorrosion = it))) })
                    VisualInspectionInput(label = "Keausan", value = v.mainWireRopeVisualWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWireRopeVisualWear = it))) })
                    VisualInspectionInput(label = "Putus", value = v.mainWireRopeVisualBreakage, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWireRopeVisualBreakage = it))) })
                    VisualInspectionInput(label = "Perubahan Bentuk", value = v.mainWireRopeVisualDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWireRopeVisualDeformation = it))) })

                    Text("Tali Kawat Baja Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Korosi", value = v.auxiliaryWireRopeVisualCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWireRopeVisualCorrosion = it))) })
                    VisualInspectionInput(label = "Keausan", value = v.auxiliaryWireRopeVisualWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWireRopeVisualWear = it))) })
                    VisualInspectionInput(label = "Putus", value = v.auxiliaryWireRopeVisualBreakage, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWireRopeVisualBreakage = it))) })
                    VisualInspectionInput(label = "Perubahan Bentuk", value = v.auxiliaryWireRopeVisualDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWireRopeVisualDeformation = it))) })

                    Text("Limit Switch (LS)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Limit Switch Long Travel", value = v.limitSwitchLsLongTravel, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(limitSwitchLsLongTravel = it))) })
                    VisualInspectionInput(label = "Limit Switch Cross Travel", value = v.limitSwitchLsCrossTravel, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(limitSwitchLsCrossTravel = it))) })
                    VisualInspectionInput(label = "Limit Switch Hoisting", value = v.limitSwitchLsHoisting, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(limitSwitchLsHoisting = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Sistem Mesin, Hidrolik & Pneumatik") {
                    Text("Motor Bakar", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    VisualInspectionInput(label = "Sistem Pendingin", value = v.internalCombustionEngineCoolingSystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineCoolingSystem = it))) })
                    VisualInspectionInput(label = "Sistem Pelumasan", value = v.internalCombustionEngineLubricationSystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineLubricationSystem = it))) })
                    VisualInspectionInput(label = "Dudukan Mesin", value = v.internalCombustionEngineEngineMounting, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineEngineMounting = it))) })
                    VisualInspectionInput(label = "Pelindung (Safety Guard)", value = v.internalCombustionEngineSafetyGuardEquipment, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineSafetyGuardEquipment = it))) })
                    VisualInspectionInput(label = "Sistem Pembuangan", value = v.internalCombustionEngineExhaustSystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineExhaustSystem = it))) })
                    VisualInspectionInput(label = "Sistem Pemasukan Udara", value = v.internalCombustionEngineAirIntakeSystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineAirIntakeSystem = it))) })
                    VisualInspectionInput(label = "Sistem Bahan Bakar", value = v.internalCombustionEngineFuelSystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineFuelSystem = it))) })
                    VisualInspectionInput(label = "Sistem Pemindah Tenaga", value = v.internalCombustionEnginePowerTransmissionSystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEnginePowerTransmissionSystem = it))) })
                    VisualInspectionInput(label = "Accu (Battery)", value = v.internalCombustionEngineBattery, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineBattery = it))) })
                    VisualInspectionInput(label = "Motor Starter", value = v.internalCombustionEngineStarterMotor, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineStarterMotor = it))) })
                    VisualInspectionInput(label = "Kabel Instalasi", value = v.internalCombustionEngineWiringInstallation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineWiringInstallation = it))) })
                    VisualInspectionInput(label = "Turbocharger", value = v.internalCombustionEngineTurbocharger, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(internalCombustionEngineTurbocharger = it))) })

                    Text("Hidrolik", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Pompa Hidrolik", value = v.hydraulicHydraulicPump, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicHydraulicPump = it))) })
                    VisualInspectionInput(label = "Saluran Hidrolik", value = v.hydraulicHydraulicLines, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicHydraulicLines = it))) })
                    VisualInspectionInput(label = "Saringan Hidrolik", value = v.hydraulicHydraulicFilter, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicHydraulicFilter = it))) })
                    VisualInspectionInput(label = "Tangki Hidrolik", value = v.hydraulicHydraulicTank, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicHydraulicTank = it))) })

                    Text("Motor Hidrolik", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Motor Lier Utama", value = v.hydraulicMotorMainWinchMotor, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicMotorMainWinchMotor = it))) })
                    VisualInspectionInput(label = "Motor Lier Tambahan", value = v.hydraulicMotorAuxiliaryWinchMotor, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicMotorAuxiliaryWinchMotor = it))) })
                    VisualInspectionInput(label = "Motor Lier Boom", value = v.hydraulicMotorBoomWinchMotor, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicMotorBoomWinchMotor = it))) })
                    VisualInspectionInput(label = "Motor Swing", value = v.hydraulicMotorSwingMotor, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicMotorSwingMotor = it))) })

                    Text("Katup Pengontrol", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Katup Relief", value = v.controlValveReliefValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(controlValveReliefValve = it))) })
                    VisualInspectionInput(label = "Katup Lier Utama", value = v.controlValveMainWinchValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(controlValveMainWinchValve = it))) })
                    VisualInspectionInput(label = "Katup Lier Tambahan", value = v.controlValveAuxiliaryWinchValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(controlValveAuxiliaryWinchValve = it))) })
                    VisualInspectionInput(label = "Katup Lier Boom", value = v.controlValveBoomWinchValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(controlValveBoomWinchValve = it))) })
                    VisualInspectionInput(label = "Katup Gerakan Boom", value = v.controlValveBoomMovementValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(controlValveBoomMovementValve = it))) })
                    VisualInspectionInput(label = "Katup Silinder Kemudi", value = v.controlValveSteeringCylinderValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(controlValveSteeringCylinderValve = it))) })
                    VisualInspectionInput(label = "Katup Osulisi (Axle)", value = v.controlValveAxleOscillationValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(controlValveAxleOscillationValve = it))) })
                    VisualInspectionInput(label = "Katup Gerakan Penumpu", value = v.controlValveOutriggerMovementValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(controlValveOutriggerMovementValve = it))) })

                    Text("Silinder Hidrolik", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Silinder Gerakan Boom", value = v.hydraulicCylinderBoomMovementCylinder, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicCylinderBoomMovementCylinder = it))) })
                    VisualInspectionInput(label = "Silinder Penumpu", value = v.hydraulicCylinderOutriggerCylinder, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicCylinderOutriggerCylinder = it))) })
                    VisualInspectionInput(label = "Silinder Roda Kemudi", value = v.hydraulicCylinderSteeringWheelCylinder, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicCylinderSteeringWheelCylinder = it))) })
                    VisualInspectionInput(label = "Silinder Osilasi (Axle)", value = v.hydraulicCylinderAxleOscillationCylinder, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicCylinderAxleOscillationCylinder = it))) })
                    VisualInspectionInput(label = "Silinder Teleskopik", value = v.hydraulicCylinderTelescopicCylinder, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hydraulicCylinderTelescopicCylinder = it))) })

                    Text("Pneumatik", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Kompresor", value = v.pneumaticCompressor, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pneumaticCompressor = it))) })
                    VisualInspectionInput(label = "Tangki & Katup Pengaman", value = v.pneumaticTankAndSafetyValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pneumaticTankAndSafetyValve = it))) })
                    VisualInspectionInput(label = "Saluran Udara Bertekanan", value = v.pneumaticPressurizedAirLines, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pneumaticPressurizedAirLines = it))) })
                    VisualInspectionInput(label = "Saringan Udara", value = v.pneumaticAirFilter, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pneumaticAirFilter = it))) })
                    VisualInspectionInput(label = "Katup Pengontrol", value = v.pneumaticControlValve, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pneumaticControlValve = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Ruang Operator, Listrik & Alat Pengaman") {
                    Text("Ruang Operator (Cabin)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    VisualInspectionInput(label = "Tangga Pengaman", value = v.operatorCabinSafetyLadder, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinSafetyLadder = it))) })
                    VisualInspectionInput(label = "Pintu", value = v.operatorCabinDoor, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinDoor = it))) })
                    VisualInspectionInput(label = "Jendela", value = v.operatorCabinWindow, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinWindow = it))) })
                    VisualInspectionInput(label = "Kipas / AC", value = v.operatorCabinFanAc, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinFanAc = it))) })
                    VisualInspectionInput(label = "Tuas/Tombol Kontrol", value = v.operatorCabinControlLeversButtons, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinControlLeversButtons = it))) })
                    VisualInspectionInput(label = "Pendant Kontrol", value = v.operatorCabinPendantControl, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinPendantControl = it))) })
                    VisualInspectionInput(label = "Penerangan", value = v.operatorCabinLighting, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinLighting = it))) })
                    VisualInspectionInput(label = "Klakson/Signal Alarm", value = v.operatorCabinHornSignalAlarm, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinHornSignalAlarm = it))) })
                    VisualInspectionInput(label = "Pengaman Lebur", value = v.operatorCabinFuse, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinFuse = it))) })
                    VisualInspectionInput(label = "Alat Komunikasi", value = v.operatorCabinCommunicationDevice, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinCommunicationDevice = it))) })
                    VisualInspectionInput(label = "Pemadam Api (APAR)", value = v.operatorCabinFireExtinguisher, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinFireExtinguisher = it))) })
                    VisualInspectionInput(label = "Tanda Pengoperasian", value = v.operatorCabinOperatingSigns, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinOperatingSigns = it))) })
                    VisualInspectionInput(label = "Kunci Kontak/Master Switch", value = v.operatorCabinIgnitionKeyMasterSwitch, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinIgnitionKeyMasterSwitch = it))) })
                    VisualInspectionInput(label = "Tombol/Handle/Tuas", value = v.operatorCabinButtonsHandlesLevers, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorCabinButtonsHandlesLevers = it))) })

                    Text("Komponen Listrik", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Penyambung Panel", value = v.electricalComponentsPanelConductorConnector, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentsPanelConductorConnector = it))) })
                    VisualInspectionInput(label = "Pelindung Penghantar", value = v.electricalComponentsConductorProtection, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentsConductorProtection = it))) })
                    VisualInspectionInput(label = "Sistem Pengaman Instalasi", value = v.electricalComponentsMotorInstallationSafetySystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentsMotorInstallationSafetySystem = it))) })
                    VisualInspectionInput(label = "Sistem Pembumian", value = v.electricalComponentsGroundingSystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentsGroundingSystem = it))) })
                    VisualInspectionInput(label = "Instalasi", value = v.electricalComponentsInstallation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentsInstallation = it))) })

                    Text("Alat Pengaman", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    VisualInspectionInput(label = "Pegangan Tangan", value = v.safetyDevicesLadderHandrail, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesLadderHandrail = it))) })
                    VisualInspectionInput(label = "Tekanan Oli Mesin", value = v.safetyDevicesEngineOilLubricantPressure, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesEngineOilLubricantPressure = it))) })
                    VisualInspectionInput(label = "Tekanan Oli Hidrolik", value = v.safetyDevicesHydraulicOilPressure, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesHydraulicOilPressure = it))) })
                    VisualInspectionInput(label = "Tekanan Udara", value = v.safetyDevicesAirPressure, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesAirPressure = it))) })
                    VisualInspectionInput(label = "Amperemeter", value = v.safetyDevicesAmperemeter, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesAmperemeter = it))) })
                    VisualInspectionInput(label = "Voltage", value = v.safetyDevicesVoltage, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesVoltage = it))) })
                    VisualInspectionInput(label = "Suhu Mesin", value = v.safetyDevicesEngineTemperature, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesEngineTemperature = it))) })
                    VisualInspectionInput(label = "Suhu Transmisi", value = v.safetyDevicesTransmissionTemperature, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesTransmissionTemperature = it))) })
                    VisualInspectionInput(label = "Suhu/Tekanan Minyak Konventor", value = v.safetyDevicesConverterOilTemperaturePressure, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesConverterOilTemperaturePressure = it))) })
                    VisualInspectionInput(label = "Indikator Kecepatan/Speedometer", value = v.safetyDevicesSpeedometerIndicator, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesSpeedometerIndicator = it))) })
                    VisualInspectionInput(label = "Lampu Rotari", value = v.safetyDevicesRotaryLamp, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesRotaryLamp = it))) })
                    VisualInspectionInput(label = "Pembatas Gerak Naik/Turun Tali Utama", value = v.safetyDevicesMainHoistRopeUpDownLimit, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesMainHoistRopeUpDownLimit = it))) })
                    VisualInspectionInput(label = "Pembatas Gerak Naik/Turun Tali Bantu", value = v.safetyDevicesAuxiliaryHoistRopeUpDownLimit, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesAuxiliaryHoistRopeUpDownLimit = it))) })
                    VisualInspectionInput(label = "Pembatas Gerak Putar/Swing", value = v.safetyDevicesSwingMotionLimit, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesSwingMotionLimit = it))) })
                    VisualInspectionInput(label = "Level Indikator", value = v.safetyDevicesLevelIndicator, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesLevelIndicator = it))) })
                    VisualInspectionInput(label = "Indikator Berat Beban", value = v.safetyDevicesLoadWeightIndicator, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesLoadWeightIndicator = it))) })
                    VisualInspectionInput(label = "Daftar Beban (Load Chart)", value = v.safetyDevicesLoadChart, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesLoadChart = it))) })
                    VisualInspectionInput(label = "Anemometer", value = v.safetyDevicesAnemometerWindSpeed, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesAnemometerWindSpeed = it))) })
                    VisualInspectionInput(label = "Indikator Sudut Boom", value = v.safetyDevicesBoomAngleIndicator, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesBoomAngleIndicator = it))) })
                    VisualInspectionInput(label = "Indikator Tekanan Udara", value = v.safetyDevicesAirPressureIndicator, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesAirPressureIndicator = it))) })
                    VisualInspectionInput(label = "Indikator Tekanan Hidrolik", value = v.safetyDevicesHydraulicPressureIndicator, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesHydraulicPressureIndicator = it))) })
                    VisualInspectionInput(label = "Katup-katup Pengaman", value = v.safetyDevicesSafetyValves, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesSafetyValves = it))) })
                    VisualInspectionInput(label = "Kunci Pengaman Tromol Gulung Utama", value = v.safetyDevicesMainWindingDrumSafetyLock, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesMainWindingDrumSafetyLock = it))) })
                    VisualInspectionInput(label = "Kunci Pengaman Tromol Gulung Bantu", value = v.safetyDevicesAuxiliaryWindingDrumSafetyLock, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesAuxiliaryWindingDrumSafetyLock = it))) })
                    VisualInspectionInput(label = "Pembatas Gerak Teleskopik", value = v.safetyDevicesTelescopicMotionLimit, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesTelescopicMotionLimit = it))) })
                    VisualInspectionInput(label = "Penangkal Petir", value = v.safetyDevicesLightningArrester, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesLightningArrester = it))) })
                    VisualInspectionInput(label = "Indikator Ketinggian Angkat", value = v.safetyDevicesLiftingHeightIndicator, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(safetyDevicesLiftingHeightIndicator = it))) })
                }
            }
        }

        item {
            val nde = report.nonDestructiveExamination
            ExpandableSection(title = "PEMERIKSAAN TIDAK MERUSAK") {
                ExpandableSubSection("Tali Kawat Baja") {
                    val data = nde.wireRope
                    FormTextField(label = "Jenis NDT", value = data.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(wireRope = data.copy(ndtType = it)))) })
                    Spacer(Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showNdeWireRopeDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah")
                        Spacer(Modifier.width(8.dp))
                        Text("Tambah Data Tali Kawat Baja")
                    }
                    data.items.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteMobileCraneNdeWireRopeItem(index) }) {
                            Text("Penggunaan: ${item.usage}", fontWeight = FontWeight.SemiBold)
                            Text("Diameter: ${item.actualDiameter}mm (Spec: ${item.specDiameter}mm)")
                            Text("Keterangan: ${item.remarks}")
                        }
                    }
                }
                HorizontalDivider()
                ExpandableSubSection("Boom") {
                    val data = nde.boom
                    FormTextField(label = "Jenis Boom", value = data.boomType, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(boom = data.copy(boomType = it)))) })
                    FormTextField(label = "Jenis NDT", value = data.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(boom = data.copy(ndtType = it)))) })
                    Spacer(Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showNdeBoomDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah")
                        Spacer(Modifier.width(8.dp))
                        Text("Tambah Data Boom")
                    }
                    data.items.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteMobileCraneNdeBoomItem(index) }) {
                            Text("Bagian: ${item.partInspected} (${item.location})", fontWeight = FontWeight.SemiBold)
                            Text("Keterangan: ${item.remarks}")
                        }
                    }
                }
                HorizontalDivider()
                ExpandableSubSection("Kait (Hook) Utama") {
                    val hook = nde.mainHook
                    FormTextField(label = "Kapasitas Hook", value = hook.hookCapacity, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hook.copy(hookCapacity = it)))) })
                    FormTextField(label = "Jenis NDT", value = hook.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hook.copy(ndtType = it)))) })
                    NdeHookMeasurementInput(
                        spec = hook.specification,
                        result = hook.measurementResult,
                        tolerance = hook.tolerance,
                        onSpecChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hook.copy(specification = it)))) },
                        onResultChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hook.copy(measurementResult = it)))) },
                        onToleranceChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hook.copy(tolerance = it)))) }
                    )
                }
                HorizontalDivider()
                ExpandableSubSection("Kait (Hook) Tambahan") {
                    val hook = nde.auxiliaryHook
                    FormTextField(label = "Kapasitas Hook", value = hook.hookCapacity, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryHook = hook.copy(hookCapacity = it)))) })
                    FormTextField(label = "Jenis NDT", value = hook.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryHook = hook.copy(ndtType = it)))) })
                    NdeHookMeasurementInput(
                        spec = hook.specification,
                        result = hook.measurementResult,
                        tolerance = hook.tolerance,
                        onSpecChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryHook = hook.copy(specification = it)))) },
                        onResultChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryHook = hook.copy(measurementResult = it)))) },
                        onToleranceChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryHook = hook.copy(tolerance = it)))) }
                    )
                }
                HorizontalDivider()
                ExpandableSubSection("Drum Utama") {
                    val drum = nde.mainDrum
                    FormTextField(label = "Jenis NDT", value = drum.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainDrum = drum.copy(ndtType = it)))) })
                    NdeDrumPulleyMeasurementInput(
                        spec = drum.specification,
                        result = drum.measurementResult,
                        onSpecChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainDrum = drum.copy(specification = it)))) },
                        onResultChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainDrum = drum.copy(measurementResult = it)))) },
                        isDrum = true
                    )
                }
                HorizontalDivider()
                ExpandableSubSection("Drum Tambahan") {
                    val drum = nde.auxiliaryDrum
                    FormTextField(label = "Jenis NDT", value = drum.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryDrum = drum.copy(ndtType = it)))) })
                    NdeDrumPulleyMeasurementInput(
                        spec = drum.specification,
                        result = drum.measurementResult,
                        onSpecChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryDrum = drum.copy(specification = it)))) },
                        onResultChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryDrum = drum.copy(measurementResult = it)))) },
                        isDrum = true
                    )
                }
                HorizontalDivider()
                ExpandableSubSection("Puli Utama") {
                    val pulley = nde.mainPulley
                    FormTextField(label = "Jenis NDT", value = pulley.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainPulley = pulley.copy(ndtType = it)))) })
                    NdeDrumPulleyMeasurementInput(
                        spec = pulley.specification,
                        result = pulley.measurementResult,
                        onSpecChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainPulley = pulley.copy(specification = it)))) },
                        onResultChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainPulley = pulley.copy(measurementResult = it)))) },
                        isDrum = false
                    )
                }
                HorizontalDivider()
                ExpandableSubSection("Puli Tambahan") {
                    val pulley = nde.auxiliaryPulley
                    FormTextField(label = "Jenis NDT", value = pulley.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryPulley = pulley.copy(ndtType = it)))) })
                    NdeDrumPulleyMeasurementInput(
                        spec = pulley.specification,
                        result = pulley.measurementResult,
                        onSpecChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryPulley = pulley.copy(specification = it)))) },
                        onResultChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(auxiliaryPulley = pulley.copy(measurementResult = it)))) },
                        isDrum = false
                    )
                }
            }
        }

        item {
            val data = report.testing
            ExpandableSection(title = "PENGUJIAN") {
                ExpandableSubSection(title = "Pengujian Fungsi") {
                    val func = data.functionTest
                    FunctionTestInput(label = "Hoisting / Lowering", value = func.hoistingLowering, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(hoistingLowering = it)))) })
                    FunctionTestInput(label = "Extended / Retracted Boom", value = func.extendedRetractedBoom, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(extendedRetractedBoom = it)))) })
                    FunctionTestInput(label = "Extended / Retracted Outrigger", value = func.extendedRetractedOutrigger, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(extendedRetractedOutrigger = it)))) })
                    FunctionTestInput(label = "Swing, Slewing", value = func.swingSlewing, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(swingSlewing = it)))) })
                    FunctionTestInput(label = "Anti Two Block", value = func.antiTwoBlock, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(antiTwoBlock = it)))) })
                    FunctionTestInput(label = "Boom Stop", value = func.boomStop, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(boomStop = it)))) })
                    FunctionTestInput(label = "Anemometer", value = func.anemometerWindSpeed, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(anemometerWindSpeed = it)))) })
                    FunctionTestInput(label = "Brake Locking Device", value = func.brakeLockingDevice, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(brakeLockingDevice = it)))) })
                    FunctionTestInput(label = "Load Moment Indicator (LMI)", value = func.loadMomentIndicator, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(loadMomentIndicator = it)))) })
                    FunctionTestInput(label = "Lampu Belok", value = func.turnSignal, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(turnSignal = it)))) })
                    FunctionTestInput(label = "Lampu Jalan", value = func.drivingLights, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(drivingLights = it)))) })
                    FunctionTestInput(label = "Lampu Indikator Beban", value = func.loadIndicatorLight, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(loadIndicatorLight = it)))) })
                    FunctionTestInput(label = "Lampu Rotari", value = func.rotaryLamp, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(rotaryLamp = it)))) })
                    FunctionTestInput(label = "Klakson", value = func.horn, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(horn = it)))) })
                    FunctionTestInput(label = "Alarm Swing", value = func.swingAlarm, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(swingAlarm = it)))) })
                    FunctionTestInput(label = "Alarm Mundur", value = func.reverseAlarm, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(reverseAlarm = it)))) })
                    FunctionTestInput(label = "Alarm Overload", value = func.overloadAlarm, onValueChange = { onDataChange(report.copy(testing = data.copy(functionTest = func.copy(overloadAlarm = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Pengujian Beban") {
                    val load = data.loadTest
                    Text("Dinamis - Kait Utama", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    FilledTonalButton(onClick = { showDynMainLoadDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah")
                        Spacer(Modifier.width(8.dp))
                        Text("Tambah Uji Beban")
                    }
                    load.dynamic.mainHook.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteMobileCraneDynamicMainHookLoadTestItem(index) }) {
                            Text("Radius ${item.radius}m, Beban ${item.testLoadKg}kg", fontWeight = FontWeight.SemiBold)
                            Text("Hasil: ${item.result}")
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Dinamis - Kait Bantu", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    FilledTonalButton(onClick = { showDynAuxLoadDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah")
                        Spacer(Modifier.width(8.dp))
                        Text("Tambah Uji Beban")
                    }
                    load.dynamic.auxiliaryHook.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteMobileCraneDynamicAuxHookLoadTestItem(index) }) {
                            Text("Radius ${item.radius}m, Beban ${item.testLoadKg}kg", fontWeight = FontWeight.SemiBold)
                            Text("Hasil: ${item.result}")
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Statis - Kait Utama", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    FilledTonalButton(onClick = { showStaMainLoadDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah")
                        Spacer(Modifier.width(8.dp))
                        Text("Tambah Uji Beban")
                    }
                    load.static.mainHook.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteMobileCraneStaticMainHookLoadTestItem(index) }) {
                            Text("Radius ${item.radius}m, Beban ${item.testLoadKg}kg", fontWeight = FontWeight.SemiBold)
                            Text("Hasil: ${item.result}")
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Statis - Kait Bantu", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    FilledTonalButton(onClick = { showStaAuxLoadDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah")
                        Spacer(Modifier.width(8.dp))
                        Text("Tambah Uji Beban")
                    }
                    load.static.auxiliaryHook.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteMobileCraneStaticAuxHookLoadTestItem(index) }) {
                            Text("Radius ${item.radius}m, Beban ${item.testLoadKg}kg", fontWeight = FontWeight.SemiBold)
                            Text("Hasil: ${item.result}")
                        }
                    }
                }
            }
        }

        item {
            val data = report.conclusion
            ExpandableSection(title = "KESIMPULAN") {
                FilledTonalButton(onClick = { showSummaryDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Kesimpulan")
                }
                data.summary.forEachIndexed { index, item ->
                    ListItemWithDelete(onDelete = { viewModel.removeMobileCraneConclusionSummaryItem(index) }) {
                        Text(item)
                    }
                }
            }
        }
        item {
            val data = report.conclusion
            ExpandableSection(title = "SARAN-SARAN") {
                FilledTonalButton(onClick = { showRecommendationDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Saran")
                }
                data.recommendations.forEachIndexed { index, item ->
                    ListItemWithDelete(onDelete = { viewModel.removeMobileCraneConclusionRecommendationItem(index) }) {
                        Text(item)
                    }
                }
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
private fun ExpandableSubSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing))
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .clickable { expanded = !expanded }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            Icon(imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = if (expanded) "Collapse" else "Expand")
        }
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                content()
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
private fun ListItemWithDelete(
    onDelete: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                content = content
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Item")
        }
    }
}

@Composable
private fun VisualInspectionInput(
    label: String,
    value: MobileCraneInspectionResult,
    onValueChange: (MobileCraneInspectionResult) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = value.result,
            onValueChange = { onValueChange(value.copy(result = it)) },
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable { onValueChange(value.copy(status = !value.status)) }
                .padding(start = 4.dp, end = 4.dp)
        ) {
            Checkbox(
                checked = value.status,
                onCheckedChange = null
            )
            Text("Memenuhi")
        }
    }
}

@Composable
private fun FunctionTestInput(
    label: String,
    value: MobileCraneTestResult,
    onValueChange: (MobileCraneTestResult) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = value.remarks,
            onValueChange = { onValueChange(value.copy(remarks = it)) },
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable { onValueChange(value.copy(status = !value.status)) }
                .padding(start = 4.dp, end = 4.dp)
        ) {
            Checkbox(
                checked = value.status,
                onCheckedChange = null
            )
            Text("Baik")
        }
    }
}

@Composable
private fun NdeHookMeasurementInput(
    spec: MobileCraneNdeMeasurement,
    result: MobileCraneNdeMeasurement,
    tolerance: MobileCraneNdeMeasurement,
    onSpecChange: (MobileCraneNdeMeasurement) -> Unit,
    onResultChange: (MobileCraneNdeMeasurement) -> Unit,
    onToleranceChange: (MobileCraneNdeMeasurement) -> Unit
) {
    val items = listOf("A", "B", "C", "D", "E", "F", "G", "H")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Spesifikasi (mm)", style = MaterialTheme.typography.titleSmall)
        items.chunked(4).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowItems.forEach { label ->
                    val value = getMeasurementValueByLabel(spec, label)
                    FormTextField(label = label, value = value, onValueChange = { onSpecChange(getUpdatedMeasurement(spec, label, it)) }, modifier = Modifier.weight(1f))
                }
            }
        }
        VisualInspectionInput(label = "Temuan", value = spec.finding, onValueChange = { onSpecChange(spec.copy(finding = it)) })
        HorizontalDivider()

        Text("Hasil Pengukuran (mm)", style = MaterialTheme.typography.titleSmall)
        items.chunked(4).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowItems.forEach { label ->
                    val value = getMeasurementValueByLabel(result, label)
                    FormTextField(label = label, value = value, onValueChange = { onResultChange(getUpdatedMeasurement(result, label, it)) }, modifier = Modifier.weight(1f))
                }
            }
        }
        VisualInspectionInput(label = "Temuan", value = result.finding, onValueChange = { onResultChange(result.copy(finding = it)) })
        HorizontalDivider()

        Text("Ukuran Toleransi (mm)", style = MaterialTheme.typography.titleSmall)
        items.chunked(4).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowItems.forEach { label ->
                    val value = getMeasurementValueByLabel(tolerance, label)
                    FormTextField(label = label, value = value, onValueChange = { onToleranceChange(getUpdatedMeasurement(tolerance, label, it)) }, modifier = Modifier.weight(1f))
                }
            }
        }
        VisualInspectionInput(label = "Temuan", value = tolerance.finding, onValueChange = { onToleranceChange(tolerance.copy(finding = it)) })
    }
}

@Composable
private fun NdeDrumPulleyMeasurementInput(
    spec: MobileCraneNdeMeasurement,
    result: MobileCraneNdeMeasurement,
    onSpecChange: (MobileCraneNdeMeasurement) -> Unit,
    onResultChange: (MobileCraneNdeMeasurement) -> Unit,
    isDrum: Boolean
) {
    val items = if (isDrum) listOf("A", "B", "C", "D", "E", "F", "G") else listOf("A", "B", "C", "D", "E")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Spesifikasi (mm)", style = MaterialTheme.typography.titleSmall)
        items.chunked(4).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowItems.forEach { label ->
                    val value = getMeasurementValueByLabel(spec, label)
                    FormTextField(label = label, value = value, onValueChange = { onSpecChange(getUpdatedMeasurement(spec, label, it)) }, modifier = Modifier.weight(1f))
                }
            }
        }
        VisualInspectionInput(label = "Temuan", value = spec.finding, onValueChange = { onSpecChange(spec.copy(finding = it)) })
        HorizontalDivider()

        Text("Hasil Pengukuran (mm)", style = MaterialTheme.typography.titleSmall)
        items.chunked(4).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowItems.forEach { label ->
                    val value = getMeasurementValueByLabel(result, label)
                    FormTextField(label = label, value = value, onValueChange = { onResultChange(getUpdatedMeasurement(result, label, it)) }, modifier = Modifier.weight(1f))
                }
            }
        }
        VisualInspectionInput(label = "Temuan", value = result.finding, onValueChange = { onResultChange(result.copy(finding = it)) })
    }
}

private fun getMeasurementValueByLabel(measurement: MobileCraneNdeMeasurement, label: String): String {
    return when (label) {
        "A" -> measurement.a; "B" -> measurement.b; "C" -> measurement.c; "D" -> measurement.d
        "E" -> measurement.e; "F" -> measurement.f; "G" -> measurement.g; "H" -> measurement.h
        else -> ""
    }
}

private fun getUpdatedMeasurement(measurement: MobileCraneNdeMeasurement, label: String, newValue: String): MobileCraneNdeMeasurement {
    return when (label) {
        "A" -> measurement.copy(a = newValue); "B" -> measurement.copy(b = newValue)
        "C" -> measurement.copy(c = newValue); "D" -> measurement.copy(d = newValue)
        "E" -> measurement.copy(e = newValue); "F" -> measurement.copy(f = newValue)
        "G" -> measurement.copy(g = newValue); "H" -> measurement.copy(h = newValue)
        else -> measurement
    }
}
//endregion

@Preview(showBackground = true, name = "Mobile Crane Screen Preview")
@Composable
private fun MobileCraneScreenPreview() {
    KoinApplicationPreview(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                MobileCraneScreen(
                    modifier = Modifier.fillMaxSize().padding(paddingValues).imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
        }
    }
}