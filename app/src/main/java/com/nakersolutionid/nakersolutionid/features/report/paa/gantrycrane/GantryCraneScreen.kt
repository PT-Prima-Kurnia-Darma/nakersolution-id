package com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane

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
fun GantryCraneScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: PAAViewModel = koinViewModel()
) {
    val uiState by viewModel.gantryCraneUiState.collectAsStateWithLifecycle()
    val report = uiState.gantryCraneInspectionReport
    val onDataChange = viewModel::onGantryCraneReportDataChange

    var showNdeWireRopeDialog by remember { mutableStateOf(false) }
    var showNdeGirderDialog by remember { mutableStateOf(false) }
    var showDeflectionDialog by remember { mutableStateOf(false) }
    var showSummaryDialog by remember { mutableStateOf(false) }
    var showRecommendationDialog by remember { mutableStateOf(false) }

    if (showNdeWireRopeDialog) {
        AddNdeWireRopeItemDialog(
            onDismissRequest = { showNdeWireRopeDialog = false },
            onConfirm = {
                viewModel.addGantryNdeWireRopeItem(it)
                showNdeWireRopeDialog = false
            }
        )
    }

    if (showNdeGirderDialog) {
        AddNdeGirderItemDialog(
            onDismissRequest = { showNdeGirderDialog = false },
            onConfirm = {
                viewModel.addGantryNdeGirderItem(it)
                showNdeGirderDialog = false
            }
        )
    }

    if (showDeflectionDialog) {
        AddDeflectionItemDialog(
            onDismissRequest = { showDeflectionDialog = false },
            onConfirm = {
                viewModel.addGantryDeflectionItem(it)
                showDeflectionDialog = false
            }
        )
    }

    if (showSummaryDialog) {
        AddStringDialog(
            title = "Tambah Poin Kesimpulan",
            label = "Poin Kesimpulan",
            onDismissRequest = { showSummaryDialog = false },
            onConfirm = {
                viewModel.addGantryConclusionSummaryItem(it)
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
                viewModel.addGantryConclusionRecommendationItem(it)
                showRecommendationDialog = false
            }
        )
    }


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

        // General Data Section
        item {
            val data = report.generalData
            ExpandableSection(title = "DATA UMUM", initiallyExpanded = false) {
                FormTextField(label = "Pemilik", value = data.owner, onValueChange = { onDataChange(report.copy(generalData = data.copy(owner = it))) })
                FormTextField(label = "Alamat", value = data.address, onValueChange = { onDataChange(report.copy(generalData = data.copy(address = it))) })
                FormTextField(label = "Pemakai", value = data.user, onValueChange = { onDataChange(report.copy(generalData = data.copy(user = it))) })
                FormTextField(label = "Pengurus/ Sub kontraktor / Penanggung jawab", value = data.personInCharge, onValueChange = { onDataChange(report.copy(generalData = data.copy(personInCharge = it))) })
                FormTextField(label = "Lokasi unit", value = data.unitLocation, onValueChange = { onDataChange(report.copy(generalData = data.copy(unitLocation = it))) })
                FormTextField(label = "Jenis pesawat angkat", value = data.driveType, onValueChange = { onDataChange(report.copy(generalData = data.copy(driveType = it))) })
                FormTextField(label = "Pabrik pembuat", value = data.manufacturer, onValueChange = { onDataChange(report.copy(generalData = data.copy(manufacturer = it))) })
                FormTextField(label = "Merk / Type", value = data.brandType, onValueChange = { onDataChange(report.copy(generalData = data.copy(brandType = it))) })
                FormTextField(label = "Tahun pembuatan", value = data.yearOfManufacture, onValueChange = { onDataChange(report.copy(generalData = data.copy(yearOfManufacture = it))) })
                FormTextField(label = "No. Serie / No. Unit", value = data.serialNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(serialNumber = it))) })
                FormTextField(label = "Kapasitas angkat", value = data.liftingCapacity, onValueChange = { onDataChange(report.copy(generalData = data.copy(liftingCapacity = it))) })
                FormTextField(label = "Digunakan untuk", value = data.intendedUse, onValueChange = { onDataChange(report.copy(generalData = data.copy(intendedUse = it))) })
                FormTextField(label = "Ijin pemakaian No.", value = data.permitNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(permitNumber = it))) })
                FormTextField(label = "Sertifikat operator", value = data.operatorCertificate, onValueChange = { onDataChange(report.copy(generalData = data.copy(operatorCertificate = it))) })
                FormTextField(label = "Data teknik / Manual", value = data.technicalDataManual, onValueChange = { onDataChange(report.copy(generalData = data.copy(technicalDataManual = it))) })
            }
        }

        // Technical Data Section
        item {
            val data = report.technicalData
            ExpandableSection(title = "DATA TEKNIK") {
                Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.End) {
                    Text("Hoisting", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Text("Traveling", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Text("Traversing", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                }
                HorizontalDivider()

                ExpandableSubSection("SPESIFIKASI") {
                    val d = data.specifications
                    MovementDataInput(label = "Tinggi Angkat", data = d.liftingHeight, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = d.copy(liftingHeight = it)))) })
                    MovementDataInput(label = "Panjang Girder", data = d.girderLength, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = d.copy(girderLength = it)))) })
                    MovementDataInput(label = "Kecepatan", data = d.speed, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = d.copy(speed = it)))) })
                }
                HorizontalDivider()

                ExpandableSubSection("MOTOR PENGGERAK") {
                    val d = data.driveMotor
                    MovementDataInput(label = "Kapasitas", data = d.capacity, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = d.copy(capacity = it)))) })
                    MovementDataInput(label = "Daya (kW)", data = d.powerKw, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = d.copy(powerKw = it)))) })
                    MovementDataInput(label = "Type", data = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = d.copy(type = it)))) })
                    MovementDataInput(label = "Putaran (Rpm)", data = d.rpm, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = d.copy(rpm = it)))) })
                    MovementDataInput(label = "Voltage (v)", data = d.voltageV, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = d.copy(voltageV = it)))) })
                    MovementDataInput(label = "Arus (A)", data = d.currentA, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = d.copy(currentA = it)))) })
                    MovementDataInput(label = "Frekuensi (Hz)", data = d.frequencyHz, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveMotor = d.copy(frequencyHz = it)))) })
                }
                HorizontalDivider()

                ExpandableSubSection("STARTING RESISTOR") {
                    val d = data.startingResistor
                    MovementDataInput(label = "Type", data = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(startingResistor = d.copy(type = it)))) })
                    MovementDataInput(label = "Voltage (v)", data = d.voltageV, onValueChange = { onDataChange(report.copy(technicalData = data.copy(startingResistor = d.copy(voltageV = it)))) })
                    MovementDataInput(label = "Arus (A)", data = d.currentA, onValueChange = { onDataChange(report.copy(technicalData = data.copy(startingResistor = d.copy(currentA = it)))) })
                }
                HorizontalDivider()

                ExpandableSubSection("REM") {
                    val d = data.brake
                    MovementDataInput(label = "Jenis", data = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(brake = d.copy(type = it)))) })
                    MovementDataInput(label = "Type", data = d.model, onValueChange = { onDataChange(report.copy(technicalData = data.copy(brake = d.copy(model = it)))) })
                }
                HorizontalDivider()

                ExpandableSubSection("REM PENGONTROL") {
                    val d = data.controllerBrake
                    MovementDataInput(label = "Jenis", data = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(controllerBrake = d.copy(type = it)))) })
                    MovementDataInput(label = "Type", data = d.model, onValueChange = { onDataChange(report.copy(technicalData = data.copy(controllerBrake = d.copy(model = it)))) })
                }
                HorizontalDivider()

                ExpandableSubSection("KAIT (HOOK)") {
                    val d = data.hook
                    MovementDataInput(label = "Type", data = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hook = d.copy(type = it)))) })
                    MovementDataInput(label = "Kapasitas", data = d.capacity, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hook = d.copy(capacity = it)))) })
                    MovementDataInput(label = "Material", data = d.material, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hook = d.copy(material = it)))) })
                }
                HorizontalDivider()

                ExpandableSubSection("TALI BAJA (WIRE ROPE)") {
                    val d = data.wireRope
                    MovementDataInput(label = "Jenis", data = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = d.copy(type = it)))) })
                    MovementDataInput(label = "Konstruksi", data = d.construction, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = d.copy(construction = it)))) })
                    MovementDataInput(label = "Diameter", data = d.diameter, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = d.copy(diameter = it)))) })
                    MovementDataInput(label = "Panjang", data = d.length, onValueChange = { onDataChange(report.copy(technicalData = data.copy(wireRope = d.copy(length = it)))) })
                }
                HorizontalDivider()

                ExpandableSubSection("RANTAI (CHAIN)") {
                    val d = data.chain
                    MovementDataInput(label = "Jenis", data = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(chain = d.copy(type = it)))) })
                    MovementDataInput(label = "Konstruksi", data = d.construction, onValueChange = { onDataChange(report.copy(technicalData = data.copy(chain = d.copy(construction = it)))) })
                    MovementDataInput(label = "Diameter", data = d.diameter, onValueChange = { onDataChange(report.copy(technicalData = data.copy(chain = d.copy(diameter = it)))) })
                    MovementDataInput(label = "Panjang", data = d.length, onValueChange = { onDataChange(report.copy(technicalData = data.copy(chain = d.copy(length = it)))) })
                }
            }
        }

        // Visual Inspection Section
        item {
            val data = report.visualInspection
            ExpandableSection(title = "PEMERIKSAAN VISUAL") {
                ExpandableSubSection("Pondasi & Rangka Utama") {
                    Text("Baut Pengikat Pondasi", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    GantryCraneResultStatusInput(label = "Korosi", value = data.foundationAnchorBoltCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(foundationAnchorBoltCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.foundationAnchorBoltCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(foundationAnchorBoltCracks = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.foundationAnchorBoltDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(foundationAnchorBoltDeformation = it))) })
                    GantryCraneResultStatusInput(label = "Pengikatan", value = data.foundationAnchorBoltTightness, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(foundationAnchorBoltTightness = it))) })

                    Text("Kolom / Rangka", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Korosi", value = data.columnFrameCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(columnFrameCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.columnFrameCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(columnFrameCracks = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.columnFrameDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(columnFrameDeformation = it))) })
                    GantryCraneResultStatusInput(label = "Pengikatan", value = data.columnFrameFastening, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(columnFrameFastening = it))) })
                    GantryCraneResultStatusInput(label = "Penguat Melintang", value = data.columnFrameCrossBracing, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(columnFrameCrossBracing = it))) })
                    GantryCraneResultStatusInput(label = "Penguat Diagonal", value = data.columnFrameDiagonalBracing, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(columnFrameDiagonalBracing = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Akses (Tangga & Lantai Kerja)") {
                    Text("Tangga", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    GantryCraneResultStatusInput(label = "Korosi", value = data.ladderCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(ladderCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.ladderCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(ladderCracks = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.ladderDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(ladderDeformation = it))) })
                    GantryCraneResultStatusInput(label = "Pengikatan", value = data.ladderFastening, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(ladderFastening = it))) })

                    Text("Lantai Kerja", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Korosi", value = data.workPlatformCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(workPlatformCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.workPlatformCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(workPlatformCracks = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.workPlatformDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(workPlatformDeformation = it))) })
                    GantryCraneResultStatusInput(label = "Pengikatan", value = data.workPlatformFastening, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(workPlatformFastening = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Rel Travelling") {
                    Text("Beam Dudukan Rel", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    GantryCraneResultStatusInput(label = "Korosi", value = data.railMountingBeamCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(railMountingBeamCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.railMountingBeamCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(railMountingBeamCracks = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.railMountingBeamDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(railMountingBeamDeformation = it))) })
                    GantryCraneResultStatusInput(label = "Pengikatan", value = data.railMountingBeamFastening, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(railMountingBeamFastening = it))) })

                    Text("Rel Travelling", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Korosi", value = data.travelingRailCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingRailCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.travelingRailCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingRailCracks = it))) })
                    GantryCraneResultStatusInput(label = "Sambungan Rel", value = data.travelingRailJoint, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingRailJoint = it))) })
                    GantryCraneResultStatusInput(label = "Kelurusan Rel", value = data.travelingRailStraightness, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingRailStraightness = it))) })
                    GantryCraneResultStatusInput(label = "Kelurusan Antar Rel", value = data.travelingRailAlignmentBetweenRails, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingRailAlignmentBetweenRails = it))) })
                    GantryCraneResultStatusInput(label = "Kerataan Antar Rel", value = data.travelingRailEvennessBetweenRails, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingRailEvennessBetweenRails = it))) })
                    GantryCraneResultStatusInput(label = "Jarak Antara Sambungan", value = data.travelingRailGapBetweenRailJoints, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingRailGapBetweenRailJoints = it))) })
                    GantryCraneResultStatusInput(label = "Pengikat Rel", value = data.travelingRailFastener, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingRailFastener = it))) })
                    GantryCraneResultStatusInput(label = "Rel Stopper", value = data.travelingRailStopper, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingRailStopper = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Rel Traversing") {
                    GantryCraneResultStatusInput(label = "Korosi", value = data.traversingRailCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingRailCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.traversingRailCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingRailCracks = it))) })
                    GantryCraneResultStatusInput(label = "Sambungan Rel", value = data.traversingRailJoint, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingRailJoint = it))) })
                    GantryCraneResultStatusInput(label = "Kelurusan Rel", value = data.traversingRailStraightness, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingRailStraightness = it))) })
                    GantryCraneResultStatusInput(label = "Kelurusan Antar Rel", value = data.traversingRailAlignmentBetweenRails, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingRailAlignmentBetweenRails = it))) })
                    GantryCraneResultStatusInput(label = "Kerataan Antar Rel", value = data.traversingRailEvennessBetweenRails, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingRailEvennessBetweenRails = it))) })
                    GantryCraneResultStatusInput(label = "Jarak Antara Sambungan", value = data.traversingRailGapBetweenRailJoints, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingRailGapBetweenRailJoints = it))) })
                    GantryCraneResultStatusInput(label = "Pengikat Rel", value = data.traversingRailFastener, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingRailFastener = it))) })
                    GantryCraneResultStatusInput(label = "Rel Stopper", value = data.traversingRailStopper, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingRailStopper = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Girder") {
                    GantryCraneResultStatusInput(label = "Korosi", value = data.girderCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(girderCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.girderCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(girderCracks = it))) })
                    GantryCraneResultStatusInput(label = "Kecembungan", value = data.girderCamber, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(girderCamber = it))) })
                    GantryCraneResultStatusInput(label = "Sambungan Girder", value = data.girderJoint, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(girderJoint = it))) })
                    GantryCraneResultStatusInput(label = "Sambungan Ujung Girder", value = data.girderEndJoint, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(girderEndJoint = it))) })
                    GantryCraneResultStatusInput(label = "Dudukan Truck pada Girder", value = data.girderTruckMountOnGirder, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(girderTruckMountOnGirder = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Komponen Travelling") {
                    Text("Rumah Roda Gigi (Travelling)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    GantryCraneResultStatusInput(label = "Korosi", value = data.travelingGearboxGirderCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingGearboxGirderCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.travelingGearboxGirderCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingGearboxGirderCracks = it))) })
                    GantryCraneResultStatusInput(label = "Minyak Pelumas", value = data.travelingGearboxGirderLubricatingOil, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingGearboxGirderLubricatingOil = it))) })
                    GantryCraneResultStatusInput(label = "Oli Seal", value = data.travelingGearboxGirderOilSeal, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(travelingGearboxGirderOilSeal = it))) })

                    Text("Roda Penggerak", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Keausan", value = data.driveWheelWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelWear = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.driveWheelCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelCracks = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.driveWheelDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelDeformation = it))) })
                    GantryCraneResultStatusInput(label = "Kondisi Flensa", value = data.driveWheelFlangeCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelFlangeCondition = it))) })
                    GantryCraneResultStatusInput(label = "Kondisi Rantai", value = data.driveWheelChainCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelChainCondition = it))) })

                    Text("Roda Idle", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Keamanan", value = data.idleWheelSecurity, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(idleWheelSecurity = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.idleWheelCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(idleWheelCracks = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.idleWheelDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(idleWheelDeformation = it))) })
                    GantryCraneResultStatusInput(label = "Kondisi Flensa", value = data.idleWheelFlangeCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(idleWheelFlangeCondition = it))) })

                    Text("Penghubung Roda / Gardan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Kelurusan", value = data.wheelConnectorBogieAxleStraightness, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(wheelConnectorBogieAxleStraightness = it))) })
                    GantryCraneResultStatusInput(label = "Cross Joint", value = data.wheelConnectorBogieAxleCrossJoint, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(wheelConnectorBogieAxleCrossJoint = it))) })
                    GantryCraneResultStatusInput(label = "Pelumasan", value = data.wheelConnectorBogieAxleLubrication, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(wheelConnectorBogieAxleLubrication = it))) })

                    Text("Stoper Bumper pada Girder", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Kondisi", value = data.stopperBumperOnGirderCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(stopperBumperOnGirderCondition = it))) })
                    GantryCraneResultStatusInput(label = "Penguat", value = data.stopperBumperOnGirderReinforcement, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(stopperBumperOnGirderReinforcement = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Komponen Traversing Trolley") {
                    Text("Rumah Roda Gigi (Traversing)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    GantryCraneResultStatusInput(label = "Pengikatan", value = data.traversingGearboxTrolleyCarrierFastening, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingGearboxTrolleyCarrierFastening = it))) })
                    GantryCraneResultStatusInput(label = "Korosi", value = data.traversingGearboxTrolleyCarrierCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingGearboxTrolleyCarrierCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.traversingGearboxTrolleyCarrierCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingGearboxTrolleyCarrierCracks = it))) })
                    GantryCraneResultStatusInput(label = "Minyak Pelumas", value = data.traversingGearboxTrolleyCarrierLubricatingOil, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingGearboxTrolleyCarrierLubricatingOil = it))) })
                    GantryCraneResultStatusInput(label = "Oli Seal", value = data.traversingGearboxTrolleyCarrierOilSeal, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(traversingGearboxTrolleyCarrierOilSeal = it))) })

                    Text("Roda Penggerak Trolley", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Keausan", value = data.driveWheelOnTrolleyWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelOnTrolleyWear = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.driveWheelOnTrolleyCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelOnTrolleyCracks = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.driveWheelOnTrolleyDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelOnTrolleyDeformation = it))) })
                    GantryCraneResultStatusInput(label = "Kondisi Flensa", value = data.driveWheelOnTrolleyFlangeCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelOnTrolleyFlangeCondition = it))) })
                    GantryCraneResultStatusInput(label = "Kondisi Rantai", value = data.driveWheelOnTrolleyChainCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(driveWheelOnTrolleyChainCondition = it))) })

                    Text("Roda Idle Trolley", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Keausan", value = data.idleWheelOnTrolleyWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(idleWheelOnTrolleyWear = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan", value = data.idleWheelOnTrolleyCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(idleWheelOnTrolleyCracks = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.idleWheelOnTrolleyDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(idleWheelOnTrolleyDeformation = it))) })
                    GantryCraneResultStatusInput(label = "Kondisi Flensa", value = data.idleWheelOnTrolleyFlangeCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(idleWheelOnTrolleyFlangeCondition = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Mekanisme Angkat (Hoist)") {
                    Text("Drum Tromol Gulung", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    GantryCraneResultStatusInput(label = "Alur", value = data.windingDrumGroove, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(windingDrumGroove = it))) })
                    GantryCraneResultStatusInput(label = "Bibir Alur", value = data.windingDrumGrooveFlange, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(windingDrumGrooveFlange = it))) })
                    GantryCraneResultStatusInput(label = "Flens-Flens", value = data.windingDrumFlanges, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(windingDrumFlanges = it))) })

                    Text("Rem", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Keausan", value = data.brakeWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(brakeWear = it))) })
                    GantryCraneResultStatusInput(label = "Penyetelan", value = data.brakeAdjustment, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(brakeAdjustment = it))) })

                    Text("Gear Box Hoist", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Pelumasan", value = data.hoistGearboxLubrication, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hoistGearboxLubrication = it))) })
                    GantryCraneResultStatusInput(label = "Olie Seal", value = data.hoistGearboxOilSeal, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hoistGearboxOilSeal = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Pully / Cakra") {
                    GantryCraneResultStatusInput(label = "Alur Pully", value = data.pulleyDiscChainSprocketPulleyGroove, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(pulleyDiscChainSprocketPulleyGroove = it))) })
                    GantryCraneResultStatusInput(label = "Bibir Pully", value = data.pulleyDiscChainSprocketPulleyFlange, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(pulleyDiscChainSprocketPulleyFlange = it))) })
                    GantryCraneResultStatusInput(label = "Pin Pully", value = data.pulleyDiscChainSprocketPulleyPin, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(pulleyDiscChainSprocketPulleyPin = it))) })
                    GantryCraneResultStatusInput(label = "Bantalan", value = data.pulleyDiscChainSprocketBearing, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(pulleyDiscChainSprocketBearing = it))) })
                    GantryCraneResultStatusInput(label = "Pelindung Pully", value = data.pulleyDiscChainSprocketPulleyGuard, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(pulleyDiscChainSprocketPulleyGuard = it))) })
                    GantryCraneResultStatusInput(label = "Penghadang Tali", value = data.pulleyDiscChainSprocketWireRopeChainGuard, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(pulleyDiscChainSprocketWireRopeChainGuard = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Kait (Hook)") {
                    Text("Kait Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    GantryCraneResultStatusInput(label = "Keausan", value = data.mainHookWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainHookWear = it))) })
                    GantryCraneResultStatusInput(label = "Kerenggangan Mulut", value = data.mainHookThroatOpening, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainHookThroatOpening = it))) })
                    GantryCraneResultStatusInput(label = "Mur & Bantalan Putar", value = data.mainHookSwivelNutAndBearing, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainHookSwivelNutAndBearing = it))) })
                    GantryCraneResultStatusInput(label = "Trunion", value = data.mainHookTrunnion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainHookTrunnion = it))) })

                    Text("Kait Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Keausan", value = data.auxiliaryHookWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryHookWear = it))) })
                    GantryCraneResultStatusInput(label = "Kerenggangan Mulut", value = data.auxiliaryHookThroatOpening, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryHookThroatOpening = it))) })
                    GantryCraneResultStatusInput(label = "Mur & Bantalan Putar", value = data.auxiliaryHookSwivelNutAndBearing, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryHookSwivelNutAndBearing = it))) })
                    GantryCraneResultStatusInput(label = "Trunion", value = data.auxiliaryHookTrunnion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryHookTrunnion = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Tali Kawat Baja & Rantai") {
                    Text("Tali Kawat Baja Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    GantryCraneResultStatusInput(label = "Korosi", value = data.mainWireRopeCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainWireRopeCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keausan", value = data.mainWireRopeWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainWireRopeWear = it))) })
                    GantryCraneResultStatusInput(label = "Putus", value = data.mainWireRopeBroken, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainWireRopeBroken = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.mainWireRopeDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainWireRopeDeformation = it))) })

                    Text("Tali Kawat Baja Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Korosi", value = data.auxiliaryWireRopeCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryWireRopeCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keausan", value = data.auxiliaryWireRopeWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryWireRopeWear = it))) })
                    GantryCraneResultStatusInput(label = "Putus", value = data.auxiliaryWireRopeBroken, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryWireRopeBroken = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.auxiliaryWireRopeDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryWireRopeDeformation = it))) })

                    Text("Rantai Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Korosi", value = data.mainChainCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainChainCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keausan", value = data.mainChainWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainChainWear = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan / Putus", value = data.mainChainCrackedBroken, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainChainCrackedBroken = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.mainChainDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(mainChainDeformation = it))) })

                    Text("Rantai Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Korosi", value = data.auxiliaryChainCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryChainCorrosion = it))) })
                    GantryCraneResultStatusInput(label = "Keausan", value = data.auxiliaryChainWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryChainWear = it))) })
                    GantryCraneResultStatusInput(label = "Keretakan / Putus", value = data.auxiliaryChainCrackedBroken, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryChainCrackedBroken = it))) })
                    GantryCraneResultStatusInput(label = "Perubahan Bentuk", value = data.auxiliaryChainDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(auxiliaryChainDeformation = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Alat Pengaman & Kontrol") {
                    Text("Limit Switch (LS)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    GantryCraneResultStatusInput(label = "LS Long Travelling", value = data.limitSwitchLsLongTraveling, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(limitSwitchLsLongTraveling = it))) })
                    GantryCraneResultStatusInput(label = "LS Cross Travelling", value = data.limitSwitchLsCrossTraveling, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(limitSwitchLsCrossTraveling = it))) })
                    GantryCraneResultStatusInput(label = "LS Gerakan Angkat", value = data.limitSwitchLsHoisting, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(limitSwitchLsHoisting = it))) })

                    Text("Ruang Operator / Kabin", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    GantryCraneResultStatusInput(label = "Tangga Pengaman", value = data.operatorCabinSafetyLadder, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinSafetyLadder = it))) })
                    GantryCraneResultStatusInput(label = "Pintu", value = data.operatorCabinDoor, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinDoor = it))) })
                    GantryCraneResultStatusInput(label = "Jendela", value = data.operatorCabinWindow, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinWindow = it))) })
                    GantryCraneResultStatusInput(label = "Kipas / AC", value = data.operatorCabinFanAc, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinFanAc = it))) })
                    GantryCraneResultStatusInput(label = "Tuas/Tombol Kontrol", value = data.operatorCabinControlLeversButtons, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinControlLeversButtons = it))) })
                    GantryCraneResultStatusInput(label = "Pendant Kontrol", value = data.operatorCabinPendantControl, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinPendantControl = it))) })
                    GantryCraneResultStatusInput(label = "Penerangan", value = data.operatorCabinLighting, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinLighting = it))) })
                    GantryCraneResultStatusInput(label = "Klakson", value = data.operatorCabinHorn, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinHorn = it))) })
                    GantryCraneResultStatusInput(label = "Pengaman Lebur", value = data.operatorCabinFuse, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinFuse = it))) })
                    GantryCraneResultStatusInput(label = "Alat Komunikasi", value = data.operatorCabinCommunicationDevice, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinCommunicationDevice = it))) })
                    GantryCraneResultStatusInput(label = "Pemadam Api (Apar)", value = data.operatorCabinFireExtinguisher, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinFireExtinguisher = it))) })
                    GantryCraneResultStatusInput(label = "Tanda Pengoperasian", value = data.operatorCabinOperatingSigns, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinOperatingSigns = it))) })
                    GantryCraneResultStatusInput(label = "Kunci Kontak/Master Switch", value = data.operatorCabinIgnitionKeyMasterSwitch, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(operatorCabinIgnitionKeyMasterSwitch = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Komponen Listrik") {
                    GantryCraneResultStatusInput(label = "Penyambung Panel", value = data.electricalComponentsPanelConductorConnector, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalComponentsPanelConductorConnector = it))) })
                    GantryCraneResultStatusInput(label = "Pelindung Penghantar", value = data.electricalComponentsConductorProtection, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalComponentsConductorProtection = it))) })
                    GantryCraneResultStatusInput(label = "Sistem Pengaman Instalasi", value = data.electricalComponentsMotorInstallationSafetySystem, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalComponentsMotorInstallationSafetySystem = it))) })
                    GantryCraneResultStatusInput(label = "Sistem Pembumian", value = data.electricalComponentsGroundingSystem, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalComponentsGroundingSystem = it))) })
                    GantryCraneResultStatusInput(label = "Instalasi", value = data.electricalComponentsInstallation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(electricalComponentsInstallation = it))) })
                }
            }
        }


        // NDE Section
        item {
            val nde = report.nonDestructiveExamination
            ExpandableSection(title = "PEMERIKSAAN TIDAK MERUSAK") {
                ExpandableSubSection("Wirerope") {
                    val data = nde.wireRope
                    FormTextField(label = "Metode", value = data.method, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(wireRope = data.copy(method = it)))) })
                    Spacer(modifier = Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showNdeWireRopeDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Data Wirerope")
                    }
                    data.items.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteGantryNdeWireRopeItem(index) }) {
                            Text("Penggunaan: ${item.usage}", fontWeight = FontWeight.SemiBold)
                            Text("Diameter: ${item.actualDiameter}mm (Spec: ${item.specDiameter}mm)")
                            Text("Konstruksi: ${item.construction}")
                            Text("Umur: ${item.age}")
                            Text("Keterangan: ${item.finding.result}")
                        }
                    }
                }
                HorizontalDivider()
                ExpandableSubSection("Kait (Hook) Utama") {
                    val hookData = nde.mainHook
                    FormTextField(label = "Metode", value = hookData.method, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hookData.copy(method = it)))) })
                    Spacer(Modifier.height(8.dp))

                    NdeHookMeasurementInput(
                        spec = hookData.specification,
                        result = hookData.measurementResult,
                        tolerance = hookData.tolerance,
                        onResultChange = { newResult ->
                            onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hookData.copy(measurementResult = newResult))))
                        },
                        onSpecChange = { newSpec ->
                            onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hookData.copy(specification = newSpec))))
                        },
                        onToleranceChange = { newTolerance ->
                            onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hookData.copy(tolerance = newTolerance))))
                        }
                    )
                }
                HorizontalDivider()
                ExpandableSubSection("Girder") {
                    val data = nde.girder
                    FormTextField(label = "Metode", value = data.method, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(girder = data.copy(method = it)))) })
                    Spacer(modifier = Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showNdeGirderDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Data Girder")
                    }
                    data.items.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteGantryNdeGirderItem(index) }) {
                            Text("Lokasi: ${item.location}", fontWeight = FontWeight.SemiBold)
                            Text("Keterangan: ${item.finding.result}")
                            Text("Cacat: ${if (item.finding.status) "Ada" else "Tidak Ada"}")
                        }
                    }
                }
            }
        }

        // Testing Section
        item {
            val testing = report.testing
            ExpandableSection(title = "PENGUJIAN") {
                ExpandableSubSection("PENGUJIAN DINAMIS") {
                    val data = testing.dynamicTest
                    Text("Tanpa Beban", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    DynamicTestItemInput(label = "Travelling", item = data.withoutLoad.traveling, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withoutLoad = data.withoutLoad.copy(traveling = it))))) })
                    DynamicTestItemInput(label = "Traversing", item = data.withoutLoad.traversing, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withoutLoad = data.withoutLoad.copy(traversing = it))))) })
                    DynamicTestItemInput(label = "Hoisting", item = data.withoutLoad.hoisting, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withoutLoad = data.withoutLoad.copy(hoisting = it))))) })
                    DynamicTestItemInput(label = "Safety Device", item = data.withoutLoad.safetyDevice, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withoutLoad = data.withoutLoad.copy(safetyDevice = it))))) })
                    DynamicTestItemInput(label = "Brake Switch", item = data.withoutLoad.brakeSwitch, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withoutLoad = data.withoutLoad.copy(brakeSwitch = it))))) })
                    DynamicTestItemInput(label = "Brake Locking Device", item = data.withoutLoad.brakeLockingDevice, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withoutLoad = data.withoutLoad.copy(brakeLockingDevice = it))))) })
                    DynamicTestItemInput(label = "Instalasi Listrik", item = data.withoutLoad.electricalInstallation, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withoutLoad = data.withoutLoad.copy(electricalInstallation = it))))) })

                    Text("Dengan Beban", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp))
                    LoadTestResultInput(label = "Tanpa Beban", item = data.withLoad.noLoad, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withLoad = data.withLoad.copy(noLoad = it))))) })
                    LoadTestResultInput(label = "25% SWL", item = data.withLoad.swl25, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withLoad = data.withLoad.copy(swl25 = it))))) })
                    LoadTestResultInput(label = "50% SWL", item = data.withLoad.swl50, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withLoad = data.withLoad.copy(swl50 = it))))) })
                    LoadTestResultInput(label = "75% SWL", item = data.withLoad.swl75, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withLoad = data.withLoad.copy(swl75 = it))))) })
                    LoadTestResultInput(label = "100% SWL", item = data.withLoad.swl100, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withLoad = data.withLoad.copy(swl100 = it))))) })
                }
                HorizontalDivider()
                ExpandableSubSection("PENGUJIAN STATIS (Defleksi)") {
                    val data = testing.staticTest
                    FormTextField(label = "Beban Uji", value = data.load, onValueChange = { onDataChange(report.copy(testing = testing.copy(staticTest = data.copy(load = it)))) })
                    FormTextField(label = "Panjang Span", value = data.deflectionStandard.spanLength, onValueChange = { onDataChange(report.copy(testing = testing.copy(staticTest = data.copy(deflectionStandard = data.deflectionStandard.copy(spanLength = it))))) })
                    FormTextField(label = "Perhitungan (1/600 x Span)", value = data.deflectionStandard.calculation, onValueChange = { onDataChange(report.copy(testing = testing.copy(staticTest = data.copy(deflectionStandard = data.deflectionStandard.copy(calculation = it))))) })

                    Spacer(modifier = Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showDeflectionDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Pengukuran Defleksi")
                    }
                    data.deflectionMeasurement.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteGantryDeflectionItem(index) }) {
                            Text("Posisi: ${item.position}", fontWeight = FontWeight.SemiBold)
                            Text("Defleksi: ${item.deflection}")
                            Text("Keterangan: ${item.remarks}")
                        }
                    }
                }
            }
        }

        // Conclusion & Recommendations
        item {
            val data = report.conclusion
            ExpandableSection(title = "KESIMPULAN") {
                FilledTonalButton(onClick = { showSummaryDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Kesimpulan")
                }
                data.summary.forEachIndexed { index, item ->
                    ListItemWithDelete(onDelete = { viewModel.removeGantryConclusionSummaryItem(index) }) {
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
                    ListItemWithDelete(onDelete = { viewModel.removeGantryConclusionRecommendationItem(index) }) {
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
private fun ListItemWithDelete(
    onDelete: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
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
fun ExpandableSection(
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
fun ExpandableSubSection(
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
fun MovementDataInput(
    label: String,
    data: GantryCraneMovementData,
    onValueChange: (GantryCraneMovementData) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(label, modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value = data.hoisting, onValueChange = { onValueChange(data.copy(hoisting = it)) }, modifier = Modifier.weight(1f).padding(horizontal = 2.dp), textStyle = MaterialTheme.typography.bodySmall, shape = MaterialTheme.shapes.small)
        OutlinedTextField(value = data.traveling, onValueChange = { onValueChange(data.copy(traveling = it)) }, modifier = Modifier.weight(1f).padding(horizontal = 2.dp), textStyle = MaterialTheme.typography.bodySmall, shape = MaterialTheme.shapes.small)
        OutlinedTextField(value = data.traversing, onValueChange = { onValueChange(data.copy(traversing = it)) }, modifier = Modifier.weight(1f).padding(horizontal = 2.dp), textStyle = MaterialTheme.typography.bodySmall, shape = MaterialTheme.shapes.small)
    }
}

@Composable
fun GantryCraneResultStatusInput(
    label: String,
    value: GantryCraneInspectionResult,
    onValueChange: (GantryCraneInspectionResult) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        OutlinedTextField(
            value = value.result,
            onValueChange = { onValueChange(value.copy(result = it)) },
            label = { Text(label) },
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clip(MaterialTheme.shapes.small).clickable { onValueChange(value.copy(status = !value.status)) }.padding(end = 8.dp)
        ) {
            Checkbox(
                checked = !value.status,
                onCheckedChange = { onCheckedChange -> onValueChange(value.copy(status = !onCheckedChange)) }
            )
            Text("Memenuhi")
        }
    }
}

@Composable
fun DynamicTestItemInput(
    label: String,
    item: GantryCraneDynamicTestItem,
    onValueChange: (GantryCraneDynamicTestItem) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        FormTextField(label = "Seharusnya", value = item.shouldBe, onValueChange = { onValueChange(item.copy(shouldBe = it)) })
        FormTextField(label = "Dicoba / Diukur", value = item.testedMeasured, onValueChange = { onValueChange(item.copy(testedMeasured = it)) })
        FormTextField(label = "Keterangan", value = item.remarks, onValueChange = { onValueChange(item.copy(remarks = it)) })
    }
}

@Composable
fun LoadTestResultInput(
    label: String,
    item: GantryCraneLoadTestResult,
    onValueChange: (GantryCraneLoadTestResult) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        Row {
            FormTextField(label = "Hoist", value = item.hoist, onValueChange = { onValueChange(item.copy(hoist = it)) }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            FormTextField(label = "Traversing", value = item.traversing, onValueChange = { onValueChange(item.copy(traversing = it)) }, modifier = Modifier.weight(1f))
        }
        Row {
            FormTextField(label = "Travelling", value = item.travelling, onValueChange = { onValueChange(item.copy(travelling = it)) }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(4.dp))
            FormTextField(label = "Brake", value = item.brakeSystem, onValueChange = { onValueChange(item.copy(brakeSystem = it)) }, modifier = Modifier.weight(1f))
        }
        FormTextField(label = "Keterangan", value = item.remarks, onValueChange = { onValueChange(item.copy(remarks = it)) })
    }
}

@Composable
fun NdeHookMeasurementInput(
    spec: GantryCraneNdeMeasurement,
    result: GantryCraneNdeMeasurement,
    tolerance: GantryCraneNdeMeasurement,
    onSpecChange: (GantryCraneNdeMeasurement) -> Unit,
    onResultChange: (GantryCraneNdeMeasurement) -> Unit,
    onToleranceChange: (GantryCraneNdeMeasurement) -> Unit
) {
    val items = listOf("A", "B", "C", "D", "E", "F", "G", "H")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // --- HASIL PENGUKURAN ---
        Text("Hasil Pengukuran (mm)", style = MaterialTheme.typography.titleSmall)
        items.chunked(4).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowItems.forEach { label ->
                    val resultValue = when(label) { "A" -> result.a; "B" -> result.b; "C" -> result.c; "D" -> result.d; "E" -> result.e; "F" -> result.f; "G" -> result.g; else -> result.h }
                    FormTextField(label = label, value = resultValue, onValueChange = {
                        val newResult = when(label) {
                            "A" -> result.copy(a = it); "B" -> result.copy(b = it); "C" -> result.copy(c = it); "D" -> result.copy(d = it)
                            "E" -> result.copy(e = it); "F" -> result.copy(f = it); "G" -> result.copy(g = it); else -> result.copy(h = it)
                        }
                        onResultChange(newResult)
                    }, modifier = Modifier.weight(1f))
                }
            }
        }
        GantryCraneResultStatusInput(label = "Keterangan Hasil", value = result.finding, onValueChange = { onResultChange(result.copy(finding = it)) })
        HorizontalDivider()

        // --- SPESIFIKASI ---
        Text("Spesifikasi (mm)", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = 4.dp))
        items.chunked(4).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowItems.forEach { label ->
                    val specValue = when(label) { "A" -> spec.a; "B" -> spec.b; "C" -> spec.c; "D" -> spec.d; "E" -> spec.e; "F" -> spec.f; "G" -> spec.g; else -> spec.h }
                    FormTextField(label = label, value = specValue, onValueChange = {
                        val newSpec = when(label) {
                            "A" -> spec.copy(a = it); "B" -> spec.copy(b = it); "C" -> spec.copy(c = it); "D" -> spec.copy(d = it)
                            "E" -> spec.copy(e = it); "F" -> spec.copy(f = it); "G" -> spec.copy(g = it); else -> spec.copy(h = it)
                        }
                        onSpecChange(newSpec)
                    }, modifier = Modifier.weight(1f))
                }
            }
        }
        HorizontalDivider()

        // --- TOLERANSI ---
        Text("Ukuran Toleransi (mm)", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = 4.dp))
        items.chunked(4).forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowItems.forEach { label ->
                    val toleranceValue = when(label) { "A" -> tolerance.a; "B" -> tolerance.b; "C" -> tolerance.c; "D" -> tolerance.d; "E" -> tolerance.e; "F" -> tolerance.f; "G" -> tolerance.g; else -> tolerance.h }
                    FormTextField(label = label, value = toleranceValue, onValueChange = {
                        val newTolerance = when(label) {
                            "A" -> tolerance.copy(a = it); "B" -> tolerance.copy(b = it); "C" -> tolerance.copy(c = it); "D" -> tolerance.copy(d = it)
                            "E" -> tolerance.copy(e = it); "F" -> tolerance.copy(f = it); "G" -> tolerance.copy(g = it); else -> tolerance.copy(h = it)
                        }
                        onToleranceChange(newTolerance)
                    }, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

//endregion

@Preview(showBackground = true, name = "Gantry Crane Screen Preview")
@Composable
private fun GantryCraneScreenPreview() {
    KoinApplicationPreview(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                GantryCraneScreen(
                    modifier = Modifier.fillMaxSize().padding(paddingValues).imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
        }
    }
}