package com.nakersolutionid.nakersolutionid.features.report.paa.gondola

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
fun GondolaScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: PAAViewModel = koinViewModel()
) {
    val uiState by viewModel.gondolaUiState.collectAsStateWithLifecycle()
    val report = uiState.gondolaInspectionReport
    val onDataChange = viewModel::onGondolaReportDataChange

    var showNdeWireRopeDialog by remember { mutableStateOf(false) }
    var showNdeSuspensionDialog by remember { mutableStateOf(false) }
    var showNdeCageDialog by remember { mutableStateOf(false) }
    var showNdeClampsDialog by remember { mutableStateOf(false) }
    var showDynamicLoadDialog by remember { mutableStateOf(false) }
    var showStaticLoadDialog by remember { mutableStateOf(false) }
    var showSummaryDialog by remember { mutableStateOf(false) }
    var showRecommendationDialog by remember { mutableStateOf(false) }

    //region Dialog State Handling
    if (showNdeWireRopeDialog) {
        AddGondolaNdeSteelWireRopeItemDialog(
            onDismissRequest = { showNdeWireRopeDialog = false },
            onConfirm = {
                viewModel.addGondolaNdeSteelWireRopeItem(it)
                showNdeWireRopeDialog = false
            }
        )
    }

    if (showNdeSuspensionDialog) {
        AddGondolaNdeItemDialog(
            title = "Tambah Data Struktur Penggantung",
            onDismissRequest = { showNdeSuspensionDialog = false },
            onConfirm = {
                viewModel.addGondolaNdeSuspensionStructureItem(it)
                showNdeSuspensionDialog = false
            }
        )
    }

    if (showNdeCageDialog) {
        AddGondolaNdeItemDialog(
            title = "Tambah Data Sangkar Gondola",
            onDismissRequest = { showNdeCageDialog = false },
            onConfirm = {
                viewModel.addGondolaNdeCageItem(it.toCageItem())
                showNdeCageDialog = false
            }
        )
    }

    if (showNdeClampsDialog) {
        AddGondolaNdeItemDialog(
            title = "Tambah Data Clamp",
            onDismissRequest = { showNdeClampsDialog = false },
            onConfirm = {
                viewModel.addGondolaNdeClampsItem(it.toClampsItem())
                showNdeClampsDialog = false
            }
        )
    }

    if (showDynamicLoadDialog) {
        AddGondolaLoadTestDialog(
            title = "Tambah Uji Beban Dinamis",
            onDismissRequest = { showDynamicLoadDialog = false },
            onConfirm = {
                viewModel.addGondolaDynamicLoadTestItem(it)
                showDynamicLoadDialog = false
            }
        )
    }

    if (showStaticLoadDialog) {
        AddGondolaLoadTestDialog(
            title = "Tambah Uji Beban Statis",
            onDismissRequest = { showStaticLoadDialog = false },
            onConfirm = {
                viewModel.addGondolaStaticLoadTestItem(it)
                showStaticLoadDialog = false
            }
        )
    }

    if (showSummaryDialog) {
        AddStringDialog(
            title = "Tambah Poin Kesimpulan",
            label = "Poin Kesimpulan",
            onDismissRequest = { showSummaryDialog = false },
            onConfirm = {
                viewModel.addGondolaConclusionSummaryItem(it)
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
                viewModel.addGondolaConclusionRecommendationItem(it)
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
                FormTextField(label = "Alamat", value = data.ownerAddress, onValueChange = { onDataChange(report.copy(generalData = data.copy(ownerAddress = it))) })
                FormTextField(label = "Pemakai", value = data.user, onValueChange = { onDataChange(report.copy(generalData = data.copy(user = it))) })
                FormTextField(label = "Pengurus/Sub Kontraktor", value = data.personInCharge, onValueChange = { onDataChange(report.copy(generalData = data.copy(personInCharge = it))) })
                FormTextField(label = "Lokasi Unit", value = data.unitLocation, onValueChange = { onDataChange(report.copy(generalData = data.copy(unitLocation = it))) })
                FormTextField(label = "Nama Operator", value = data.operatorName, onValueChange = { onDataChange(report.copy(generalData = data.copy(operatorName = it))) })
                FormTextField(label = "Jenis Pesawat", value = data.driveType, onValueChange = { onDataChange(report.copy(generalData = data.copy(driveType = it))) })
                FormTextField(label = "Pabrik Pembuat", value = data.manufacturer, onValueChange = { onDataChange(report.copy(generalData = data.copy(manufacturer = it))) })
                FormTextField(label = "Merek / Type", value = data.brandType, onValueChange = { onDataChange(report.copy(generalData = data.copy(brandType = it))) })
                FormTextField(label = "Tahun Pembuatan", value = data.yearOfManufacture, onValueChange = { onDataChange(report.copy(generalData = data.copy(yearOfManufacture = it))) })
                FormTextField(label = "No. Serie / No. Unit", value = data.serialNumberUnitNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(serialNumberUnitNumber = it))) })
                FormTextField(label = "Kapasitas", value = data.capacity, onValueChange = { onDataChange(report.copy(generalData = data.copy(capacity = it))) })
                FormTextField(label = "Standar yang Dipakai", value = data.standardUsed, onValueChange = { onDataChange(report.copy(generalData = data.copy(standardUsed = it))) })
                FormTextField(label = "Digunakan Untuk", value = data.usedFor, onValueChange = { onDataChange(report.copy(generalData = data.copy(usedFor = it))) })
                FormTextField(label = "Ijin Pemakaian No.", value = data.usagePermitNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(usagePermitNumber = it))) })
                FormTextField(label = "Sertifikat Operator", value = data.operatorCertificate, onValueChange = { onDataChange(report.copy(generalData = data.copy(operatorCertificate = it))) })
            }
        }

        item {
            val data = report.technicalData
            ExpandableSection(title = "DATA TEKNIK") {
                ExpandableSubSection(title = "SPESIFIKASI GONDOLA") {
                    val spec = data.gondolaSpecifications
                    FormTextField(label = "Tinggi tiang penyangga", value = spec.supportPoleHeight, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(supportPoleHeight = it)))) })
                    Text("Beam", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    FormTextField(label = "Panjang beam depan", value = spec.beam.frontBeamLength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(beam = spec.beam.copy(frontBeamLength = it))))) })
                    FormTextField(label = "Panjang beam tengah", value = spec.beam.middleBeamLength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(beam = spec.beam.copy(middleBeamLength = it))))) })
                    FormTextField(label = "Panjang beam belakang", value = spec.beam.rearBeamLength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(beam = spec.beam.copy(rearBeamLength = it))))) })
                    FormTextField(label = "Jarak balance weight dengan beam", value = spec.balanceWeightToBeamDistance, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(balanceWeightToBeamDistance = it)))) })
                    FormTextField(label = "Kapasitas", value = spec.capacity, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(capacity = it)))) })
                    FormTextField(label = "Kecepatan", value = spec.speed, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(speed = it)))) })
                    Text("Ukuran platform (P x L x T)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    FormTextField(label = "Panjang", value = spec.platformSize.length, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(platformSize = spec.platformSize.copy(length = it))))) })
                    FormTextField(label = "Lebar", value = spec.platformSize.width, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(platformSize = spec.platformSize.copy(width = it))))) })
                    FormTextField(label = "Tinggi", value = spec.platformSize.height, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(platformSize = spec.platformSize.copy(height = it))))) })
                    FormTextField(label = "Wire Rope", value = spec.wireRope, onValueChange = { onDataChange(report.copy(technicalData = data.copy(gondolaSpecifications = spec.copy(wireRope = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection(title = "HOIST") {
                    val hoist = data.hoist
                    FormTextField(label = "Model", value = hoist.model, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hoist = hoist.copy(model = it)))) })
                    FormTextField(label = "Daya angkat", value = hoist.liftingPower, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hoist = hoist.copy(liftingPower = it)))) })
                    Text("Electric Motor", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    FormTextField(label = "Type", value = hoist.electricMotor.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hoist = hoist.copy(electricMotor = hoist.electricMotor.copy(type = it))))) })
                    FormTextField(label = "Power", value = hoist.electricMotor.power, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hoist = hoist.copy(electricMotor = hoist.electricMotor.copy(power = it))))) })
                    FormTextField(label = "Voltage", value = hoist.electricMotor.voltage, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hoist = hoist.copy(electricMotor = hoist.electricMotor.copy(voltage = it))))) })
                }
                HorizontalDivider()
                FormTextField(label = "SAFETY LOCK TYPE", value = data.safetyLockType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(safetyLockType = it))) })
                HorizontalDivider()
                ExpandableSubSection(title = "REM") {
                    val brake = data.brake
                    FormTextField(label = "Jenis", value = brake.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(brake = brake.copy(type = it)))) })
                    FormTextField(label = "Type", value = brake.model, onValueChange = { onDataChange(report.copy(technicalData = data.copy(brake = brake.copy(model = it)))) })
                    FormTextField(label = "Kapasitas", value = brake.capacity, onValueChange = { onDataChange(report.copy(technicalData = data.copy(brake = brake.copy(capacity = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection(title = "MEKANIKAL SUSPENSI") {
                    val suspension = data.mechanicalSuspension
                    FormTextField(label = "Tinggi tiang penyangga", value = suspension.supportPoleHeight, onValueChange = { onDataChange(report.copy(technicalData = data.copy(mechanicalSuspension = suspension.copy(supportPoleHeight = it)))) })
                    FormTextField(label = "Panjang beam depan", value = suspension.frontBeamLength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(mechanicalSuspension = suspension.copy(frontBeamLength = it)))) })
                    FormTextField(label = "Material", value = suspension.material, onValueChange = { onDataChange(report.copy(technicalData = data.copy(mechanicalSuspension = suspension.copy(material = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection(title = "BERAT MESIN") {
                    val weight = data.engineWeight
                    FormTextField(label = "Berat total platform", value = weight.totalPlatformWeightIncludingHoistSafetyLockControlPanel, onValueChange = { onDataChange(report.copy(technicalData = data.copy(engineWeight = weight.copy(totalPlatformWeightIncludingHoistSafetyLockControlPanel = it)))) })
                    FormTextField(label = "Berat mechanical suspensi", value = weight.mechanicalSuspensionWeight, onValueChange = { onDataChange(report.copy(technicalData = data.copy(engineWeight = weight.copy(mechanicalSuspensionWeight = it)))) })
                    FormTextField(label = "Balance weight", value = weight.balanceWeight, onValueChange = { onDataChange(report.copy(technicalData = data.copy(engineWeight = weight.copy(balanceWeight = it)))) })
                    FormTextField(label = "Berat mesin keseluruhan", value = weight.totalEngineWeightExcludingWireRopeAndCable, onValueChange = { onDataChange(report.copy(technicalData = data.copy(engineWeight = weight.copy(totalEngineWeightExcludingWireRopeAndCable = it)))) })
                }
            }
        }

        item {
            val data = report.visualInspection
            ExpandableSection(title = "PEMERIKSAAN VISUAL") {
                ExpandableSubSection("Struktur Penggantung") {
                    GondolaResultStatusInput(label = "Beam bagian depan", value = data.suspensionStructureFrontBeam, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureFrontBeam = it))) })
                    GondolaResultStatusInput(label = "Beam bagian tengah", value = data.suspensionStructureMiddleBeam, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureMiddleBeam = it))) })
                    GondolaResultStatusInput(label = "Beam bagian belakang", value = data.suspensionStructureRearBeam, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureRearBeam = it))) })
                    GondolaResultStatusInput(label = "Tiang penyangga beam depan", value = data.suspensionStructureFrontBeamSupportPole, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureFrontBeamSupportPole = it))) })
                    GondolaResultStatusInput(label = "Tiang penyangga beam depan bagian bawah", value = data.suspensionStructureLowerFrontBeamSupportPole, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureLowerFrontBeamSupportPole = it))) })
                    GondolaResultStatusInput(label = "Klem penguat tiang penyangga dan beam", value = data.suspensionStructureReinforcementClamp, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureReinforcementClamp = it))) })
                    GondolaResultStatusInput(label = "Coupling Sleeve", value = data.suspensionStructureCouplingSleeve, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureCouplingSleeve = it))) })
                    GondolaResultStatusInput(label = "Turn buckle", value = data.suspensionStructureTurnbuckle, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureTurnbuckle = it))) })
                    GondolaResultStatusInput(label = "Tali penguat", value = data.suspensionStructureReinforcementRope, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureReinforcementRope = it))) })
                    GondolaResultStatusInput(label = "Tiang penyangga belakang", value = data.suspensionStructureRearSupportPole, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureRearSupportPole = it))) })
                    GondolaResultStatusInput(label = "Balance weight/bobot pengimbang", value = data.suspensionStructureBalanceWeight, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureBalanceWeight = it))) })
                    GondolaResultStatusInput(label = "Tumpuan tiang penyangga beam depan", value = data.suspensionStructureFrontSupportPoleBase, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureFrontSupportPoleBase = it))) })
                    GondolaResultStatusInput(label = "Tumpuan tiang penyangga beam belakang", value = data.suspensionStructureRearSupportPoleBase, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureRearSupportPoleBase = it))) })
                    GondolaResultStatusInput(label = "Joint tumpuan jack", value = data.suspensionStructureJackBaseJoint, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureJackBaseJoint = it))) })
                    GondolaResultStatusInput(label = "Baut baut sambungan", value = data.suspensionStructureConnectionBolts, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(suspensionStructureConnectionBolts = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Tali Kawat Baja") {
                    GondolaResultStatusInput(label = "TKB utama", value = data.steelWireRopeMainRope, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(steelWireRopeMainRope = it))) })
                    GondolaResultStatusInput(label = "Safety rope", value = data.steelWireRopeSafetyRope, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(steelWireRopeSafetyRope = it))) })
                    GondolaResultStatusInput(label = "Pengikat sling", value = data.steelWireRopeSlingBinder, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(steelWireRopeSlingBinder = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Sistem Kelistrikan") {
                    GondolaResultStatusInput(label = "Motor Hoist (1-2)", value = data.electricalSystemHoistMotor, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemHoistMotor = it))) })
                    GondolaResultStatusInput(label = "Break release", value = data.electricalSystemBrakeRelease, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemBrakeRelease = it))) })
                    GondolaResultStatusInput(label = "Manual release", value = data.electricalSystemManualRelease, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemManualRelease = it))) })
                    GondolaResultStatusInput(label = "Power control", value = data.electricalSystemPowerControl, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemPowerControl = it))) })
                    GondolaResultStatusInput(label = "Kabel power", value = data.electricalSystemPowerCable, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemPowerCable = it))) })
                    GondolaResultStatusInput(label = "Handle switch", value = data.electricalSystemHandleSwitch, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemHandleSwitch = it))) })
                    GondolaResultStatusInput(label = "Upper limit switch", value = data.electricalSystemUpperLimitSwitch, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemUpperLimitSwitch = it))) })
                    GondolaResultStatusInput(label = "Limit stopper", value = data.electricalSystemLimitStopper, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemLimitStopper = it))) })
                    GondolaResultStatusInput(label = "Socket/Fitting", value = data.electricalSystemSocketFitting, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemSocketFitting = it))) })
                    GondolaResultStatusInput(label = "Grounding", value = data.electricalSystemGrounding, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemGrounding = it))) })
                    GondolaResultStatusInput(label = "Breaker/fuse", value = data.electricalSystemBreakerFuse, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemBreakerFuse = it))) })
                    GondolaResultStatusInput(label = "Emergency stop", value = data.electricalSystemEmergencyStop, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalSystemEmergencyStop = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Platform") {
                    GondolaResultStatusInput(label = "Rangka dudukan hoist", value = data.platformHoistMountingFrame, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(platformHoistMountingFrame = it))) })
                    GondolaResultStatusInput(label = "Rangka platform", value = data.platformFrame, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(platformFrame = it))) })
                    GondolaResultStatusInput(label = "Bottom plate", value = data.platformBottomPlate, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(platformBottomPlate = it))) })
                    GondolaResultStatusInput(label = "Pin pin dan baut baut", value = data.platformPinsAndBolts, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(platformPinsAndBolts = it))) })
                    GondolaResultStatusInput(label = "Bracket", value = data.platformBracket, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(platformBracket = it))) })
                    GondolaResultStatusInput(label = "Toe board", value = data.platformToeBoard, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(platformToeBoard = it))) })
                    GondolaResultStatusInput(label = "Roller dan guide pully", value = data.platformRollerAndGuidePulley, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(platformRollerAndGuidePulley = it))) })
                    GondolaResultStatusInput(label = "Name plate", value = data.platformNameplate, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(platformNameplate = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Alat - Alat Pengaman") {
                    GondolaResultStatusInput(label = "Safety lock", value = data.safetyDevicesSafetyLock, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesSafetyLock = it))) })
                    GondolaResultStatusInput(label = "Bumper Karet", value = data.safetyDevicesRubberBumper, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesRubberBumper = it))) })
                    GondolaResultStatusInput(label = "Safety life line", value = data.safetyDevicesSafetyLifeline, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesSafetyLifeline = it))) })
                    GondolaResultStatusInput(label = "Load limit switch", value = data.safetyDevicesLoadLimitSwitch, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesLoadLimitSwitch = it))) })
                    GondolaResultStatusInput(label = "Limit block", value = data.safetyDevicesLimitBlock, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesLimitBlock = it))) })
                    GondolaResultStatusInput(label = "Upper limit switch", value = data.safetyDevicesUpperLimitSwitch, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesUpperLimitSwitch = it))) })
                    GondolaResultStatusInput(label = "Body harness", value = data.safetyDevicesBodyHarness, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesBodyHarness = it))) })
                    GondolaResultStatusInput(label = "Safety harness anchorage", value = data.safetyDevicesSafetyHarnessAnchorage, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesSafetyHarnessAnchorage = it))) })
                    GondolaResultStatusInput(label = "Handy talkie / alat komunikasi", value = data.safetyDevicesHandyTalkie, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesHandyTalkie = it))) })
                    GondolaResultStatusInput(label = "Safety helmet", value = data.safetyDevicesSafetyHelmet, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesSafetyHelmet = it))) })
                    GondolaResultStatusInput(label = "Hand rail", value = data.safetyDevicesHandrail, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesHandrail = it))) })
                    GondolaResultStatusInput(label = "APD lainnya", value = data.safetyDevicesOtherPpe, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesOtherPpe = it))) })
                    GondolaResultStatusInput(label = "Coup for glass", value = data.safetyDevicesCoupForGlass, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(safetyDevicesCoupForGlass = it))) })
                }
            }
        }

        item {
            val data = report.nonDestructiveTesting
            ExpandableSection(title = "PEMERIKSAAN TIDAK MERUSAK") {
                ExpandableSubSection(title = "TALI KAWAT BAJA") {
                    val ndt = data.steelWireRope
                    FormTextField(label = "Jenis NDT", value = ndt.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveTesting = data.copy(steelWireRope = ndt.copy(ndtType = it)))) })
                    Spacer(modifier = Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showNdeWireRopeDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Data TKB")
                    }
                    ndt.items.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteGondolaNdeSteelWireRopeItem(index) }) {
                            Text("Penggunaan: ${item.usage}", fontWeight = FontWeight.SemiBold)
                            Text("Diameter: ${item.actualDiameter}mm (Spec: ${item.specDiameter}mm)")
                            Text("Konstruksi: ${item.construction}")
                            Text("Keterangan: ${item.description}")
                        }
                    }
                }
                HorizontalDivider()
                ExpandableSubSection(title = "STRUKTUR PENGGANTUNG") {
                    val ndt = data.suspensionStructure
                    FormTextField(label = "Jenis NDT", value = ndt.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveTesting = data.copy(suspensionStructure = ndt.copy(ndtType = it)))) })
                    Spacer(modifier = Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showNdeSuspensionDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Data Struktur Penggantung")
                    }
                    ndt.items.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteGondolaNdeSuspensionStructureItem(index) }) {
                            Text("Bagian: ${item.inspectedPart}", fontWeight = FontWeight.SemiBold)
                            Text("Lokasi: ${item.location}")
                            Text("Cacat: ${item.defect}")
                            Text("Keterangan: ${item.description}")
                        }
                    }
                }
                HorizontalDivider()
                ExpandableSubSection(title = "SANGKAR GONDOLA") {
                    val ndt = data.gondolaCage
                    FormTextField(label = "Jenis NDT", value = ndt.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveTesting = data.copy(gondolaCage = ndt.copy(ndtType = it)))) })
                    Spacer(modifier = Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showNdeCageDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Data Sangkar Gondola")
                    }
                    ndt.items.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteGondolaNdeCageItem(index) }) {
                            Text("Bagian: ${item.inspectedPart}", fontWeight = FontWeight.SemiBold)
                            Text("Lokasi: ${item.location}")
                            Text("Cacat: ${item.defect}")
                            Text("Keterangan: ${item.description}")
                        }
                    }
                }
                HorizontalDivider()
                ExpandableSubSection(title = "CLAMPS") {
                    val ndt = data.clamps
                    FormTextField(label = "Jenis NDT", value = ndt.ndtType, onValueChange = { onDataChange(report.copy(nonDestructiveTesting = data.copy(clamps = ndt.copy(ndtType = it)))) })
                    Spacer(modifier = Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showNdeClampsDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Data Clamp")
                    }
                    ndt.items.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteGondolaNdeClampsItem(index) }) {
                            Text("Bagian: ${item.inspectedPart}", fontWeight = FontWeight.SemiBold)
                            Text("Lokasi: ${item.location}")
                            Text("Cacat: ${item.defect}")
                            Text("Keterangan: ${item.description}")
                        }
                    }
                }
            }
        }

        item {
            val data = report.testing
            ExpandableSection(title = "PENGUJIAN") {
                ExpandableSubSection("Pengujian Beban Dinamis") {
                    FilledTonalButton(onClick = { showDynamicLoadDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Data Uji Beban Dinamis")
                    }
                    data.dynamicLoad.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteGondolaDynamicLoadTestItem(index) }) {
                            Text("Beban: ${item.loadPercentage} (${item.load})", fontWeight = FontWeight.SemiBold)
                            Text("Hasil: ${item.result}")
                            Text("Keterangan: ${item.description}")
                        }
                    }
                }
                HorizontalDivider()
                ExpandableSubSection("Pengujian Beban Statis") {
                    FilledTonalButton(onClick = { showStaticLoadDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Data Uji Beban Statis")
                    }
                    data.staticLoad.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteGondolaStaticLoadTestItem(index) }) {
                            Text("Beban: ${item.loadPercentage} (${item.load})", fontWeight = FontWeight.SemiBold)
                            Text("Hasil: ${item.result}")
                            Text("Keterangan: ${item.description}")
                        }
                    }
                }
            }
        }

        item {
            val data = report.conclusion
            ExpandableSection(title = "KESIMPULAN") {
                FilledTonalButton(onClick = { showSummaryDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Kesimpulan")
                }
                data.summary.forEachIndexed { index, item ->
                    ListItemWithDelete(onDelete = { viewModel.removeGondolaConclusionSummaryItem(index) }) {
                        Text(item)
                    }
                }
            }
        }

        item {
            val data = report.conclusion
            ExpandableSection(title = "SARAN-SARAN") {
                FilledTonalButton(onClick = { showRecommendationDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Saran")
                }
                data.recommendations.forEachIndexed { index, item ->
                    ListItemWithDelete(onDelete = { viewModel.removeGondolaConclusionRecommendationItem(index) }) {
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
private fun GondolaResultStatusInput(
    label: String,
    value: GondolaInspectionResult?,
    onValueChange: (GondolaInspectionResult) -> Unit
) {
    val currentVal = value ?: GondolaInspectionResult()
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        OutlinedTextField(
            value = currentVal.result.orEmpty(),
            onValueChange = { onValueChange(currentVal.copy(result = it)) },
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable { onValueChange(currentVal.copy(status = !(currentVal.status ?: false))) }
                .padding(end = 8.dp)
        ) {
            Checkbox(
                checked = currentVal.status ?: false,
                onCheckedChange = { onCheckedChange -> onValueChange(currentVal.copy(status = onCheckedChange)) }
            )
            Text("Memenuhi")
        }
    }
}
//endregion

//region Mappers
private fun GondolaSuspensionStructureItem.toCageItem(): GondolaCageItem {
    return GondolaCageItem(
        inspectedPart = this.inspectedPart,
        location = this.location,
        defect = this.defect,
        description = this.description
    )
}

private fun GondolaSuspensionStructureItem.toClampsItem(): GondolaClampsItem {
    return GondolaClampsItem(
        inspectedPart = this.inspectedPart,
        location = this.location,
        defect = this.defect,
        description = this.description
    )
}
//endregion

@Preview(showBackground = true, name = "Gondola Screen Preview")
@Composable
private fun GondolaScreenPreview() {
    KoinApplicationPreview(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                GondolaScreen(
                    modifier = Modifier.fillMaxSize().padding(paddingValues).imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
        }
    }
}