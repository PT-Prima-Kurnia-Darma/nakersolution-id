package com.nakersolutionid.nakersolutionid.features.report.paa.overheadcrane

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
fun OverheadCraneScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: PAAViewModel = koinViewModel()
) {
    val uiState by viewModel.overheadCraneUiState.collectAsStateWithLifecycle()
    val report = uiState.overheadCraneInspectionReport
    val onDataChange = viewModel::onOverheadCraneReportDataChange

    var showNdeChainDialog by remember { mutableStateOf(false) }
    var showSummaryDialog by remember { mutableStateOf(false) }
    var showRecommendationDialog by remember { mutableStateOf(false) }

    if (showNdeChainDialog) {
        AddNdeChainItemDialog(
            onDismissRequest = { showNdeChainDialog = false },
            onConfirm = {
                viewModel.addOverheadCraneNdeChainItem(it)
                showNdeChainDialog = false
            }
        )
    }
    if (showSummaryDialog) {
        AddStringDialog(
            title = "Tambah Poin Kesimpulan",
            label = "Poin Kesimpulan",
            onDismissRequest = { showSummaryDialog = false },
            onConfirm = {
                viewModel.addOverheadCraneConclusionSummaryItem(it)
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
                viewModel.addOverheadCraneConclusionRecommendationItem(it)
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
                FormTextField(label = "Jenis pesawat angkat", value = data.hoistType, onValueChange = { onDataChange(report.copy(generalData = data.copy(hoistType = it))) })
                FormTextField(label = "Pabrik pembuat", value = data.manufacturer, onValueChange = { onDataChange(report.copy(generalData = data.copy(manufacturer = it))) })
                FormTextField(label = "Merk / Type", value = data.brandType, onValueChange = { onDataChange(report.copy(generalData = data.copy(brandType = it))) })
                FormTextField(label = "Tahun pembuatan", value = data.yearOfManufacture, onValueChange = { onDataChange(report.copy(generalData = data.copy(yearOfManufacture = it))) })
                FormTextField(label = "No. Serie / No. Unit", value = data.serialNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(serialNumber = it))) })
                FormTextField(label = "Kapasitas angkat", value = data.liftingCapacity, onValueChange = { onDataChange(report.copy(generalData = data.copy(liftingCapacity = it))) })
                FormTextField(label = "Digunakan untuk", value = data.intendedUse, onValueChange = { onDataChange(report.copy(generalData = data.copy(intendedUse = it))) })
                FormTextField(label = "Ijin pemakaian No.", value = data.permitNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(permitNumber = it))) })
                FormTextField(label = "Sertifikat operator", value = data.operatorCertificate, onValueChange = { onDataChange(report.copy(generalData = data.copy(operatorCertificate = it))) })
                FormTextField(label = "Data teknik / Manual", value = data.technicalDataManual, onValueChange = { onDataChange(report.copy(generalData = data.copy(technicalDataManual = it))) })
                FormTextField(label = "Data teknik / Manual", value = data.inspectionDate, onValueChange = { onDataChange(report.copy(generalData = data.copy(inspectionDate = it))) })
            }
        }

        // Technical Data Section
        item {
            val data = report.technicalData
            ExpandableSection(title = "DATA TEKNIK") {
                Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.End) {
                    Text("", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
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
            val v = report.visualInspection
            ExpandableSection(title = "PEMERIKSAAN VISUAL") {
                ExpandableSubSection("Pondasi & Rangka Utama") {
                    Text("Baut Pengikat Pondasi", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    InspectionResultInput(label = "Korosi", value = v.foundationAnchorBoltCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(foundationAnchorBoltCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.foundationAnchorBoltCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(foundationAnchorBoltCracks = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.foundationAnchorBoltDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(foundationAnchorBoltDeformation = it))) })
                    InspectionResultInput(label = "Pengikatan", value = v.foundationAnchorBoltFastening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(foundationAnchorBoltFastening = it))) })

                    Text("Kolom / Rangka", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Korosi", value = v.columnFrameCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(columnFrameCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.columnFrameCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(columnFrameCracks = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.columnFrameDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(columnFrameDeformation = it))) })
                    InspectionResultInput(label = "Pengikatan", value = v.columnFrameFastening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(columnFrameFastening = it))) })
                    InspectionResultInput(label = "Penguat Melintang", value = v.columnFrameCrossBracing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(columnFrameCrossBracing = it))) })
                    InspectionResultInput(label = "Penguat Diagonal", value = v.columnFrameDiagonalBracing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(columnFrameDiagonalBracing = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Akses (Tangga & Lantai Kerja)") {
                    Text("Tangga", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    InspectionResultInput(label = "Korosi", value = v.ladderCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(ladderCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.ladderCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(ladderCracks = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.ladderDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(ladderDeformation = it))) })
                    InspectionResultInput(label = "Pengikatan", value = v.ladderFastening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(ladderFastening = it))) })

                    Text("Lantai Kerja", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Korosi", value = v.workPlatformCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(workPlatformCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.workPlatformCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(workPlatformCracks = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.workPlatformDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(workPlatformDeformation = it))) })
                    InspectionResultInput(label = "Pengikatan", value = v.workPlatformFastening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(workPlatformFastening = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Rel Travelling & Traversing") {
                    Text("Beam Dudukan Rel", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    InspectionResultInput(label = "Korosi", value = v.railMountingBeamCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(railMountingBeamCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.railMountingBeamCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(railMountingBeamCracks = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.railMountingBeamDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(railMountingBeamDeformation = it))) })
                    InspectionResultInput(label = "Pengikatan", value = v.railMountingBeamFastening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(railMountingBeamFastening = it))) })

                    Text("Rel Travelling", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Korosi", value = v.travelingRailCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingRailCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.travelingRailCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingRailCracks = it))) })
                    InspectionResultInput(label = "Sambungan Rel", value = v.travelingRailJoint, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingRailJoint = it))) })
                    InspectionResultInput(label = "Kelurusan Rel", value = v.travelingRailStraightness, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingRailStraightness = it))) })
                    InspectionResultInput(label = "Kelurusan Antar Rel", value = v.travelingRailAlignmentBetweenRails, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingRailAlignmentBetweenRails = it))) })
                    InspectionResultInput(label = "Kerataan Antar Rel", value = v.travelingRailEvennessBetweenRails, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingRailEvennessBetweenRails = it))) })
                    InspectionResultInput(label = "Jarak Antara Sambungan", value = v.travelingRailGapBetweenRailJoints, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingRailGapBetweenRailJoints = it))) })
                    InspectionResultInput(label = "Pengikat Rel", value = v.travelingRailFastener, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingRailFastener = it))) })
                    InspectionResultInput(label = "Rel Stopper", value = v.travelingRailStopper, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingRailStopper = it))) })

                    Text("Rel Traversing", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Korosi", value = v.traversingRailCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingRailCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.traversingRailCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingRailCracks = it))) })
                    InspectionResultInput(label = "Sambungan Rel", value = v.traversingRailJoint, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingRailJoint = it))) })
                    InspectionResultInput(label = "Kelurusan Rel", value = v.traversingRailStraightness, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingRailStraightness = it))) })
                    InspectionResultInput(label = "Kelurusan Antar Rel", value = v.traversingRailAlignmentBetweenRails, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingRailAlignmentBetweenRails = it))) })
                    InspectionResultInput(label = "Kerataan Antar Rel", value = v.traversingRailEvennessBetweenRails, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingRailEvennessBetweenRails = it))) })
                    InspectionResultInput(label = "Jarak Antara Sambungan", value = v.traversingRailGapBetweenRailJoints, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingRailGapBetweenRailJoints = it))) })
                    InspectionResultInput(label = "Pengikat Rel", value = v.traversingRailFastener, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingRailFastener = it))) })
                    InspectionResultInput(label = "Rel Stopper", value = v.traversingRailStopper, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingRailStopper = it))) })
                }
                HorizontalDivider()

                ExpandableSubSection("Girder & Komponen Bergerak") {
                    Text("Girder", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    InspectionResultInput(label = "Korosi", value = v.girderCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(girderCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.girderCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(girderCracks = it))) })
                    InspectionResultInput(label = "Kecembungan", value = v.girderCamber, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(girderCamber = it))) })
                    InspectionResultInput(label = "Sambungan Girder", value = v.girderJoint, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(girderJoint = it))) })
                    InspectionResultInput(label = "Sambungan Ujung Girder", value = v.girderEndJoint, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(girderEndJoint = it))) })
                    InspectionResultInput(label = "Dudukan Truck pada Girder", value = v.girderTruckMountOnGirder, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(girderTruckMountOnGirder = it))) })

                    Text("Rumah Roda Gigi (Travelling)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Korosi", value = v.travelingGearboxGirderCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingGearboxGirderCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.travelingGearboxGirderCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingGearboxGirderCracks = it))) })
                    InspectionResultInput(label = "Minyak Pelumas", value = v.travelingGearboxGirderLubricatingOil, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingGearboxGirderLubricatingOil = it))) })
                    InspectionResultInput(label = "Oli Seal", value = v.travelingGearboxGirderOilSeal, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(travelingGearboxGirderOilSeal = it))) })

                    Text("Roda Penggerak", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Keausan", value = v.driveWheelWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelWear = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.driveWheelCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelCracks = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.driveWheelDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelDeformation = it))) })
                    InspectionResultInput(label = "Kondisi Flensa", value = v.driveWheelFlangeCondition, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelFlangeCondition = it))) })
                    InspectionResultInput(label = "Kondisi Rantai", value = v.driveWheelChainCondition, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelChainCondition = it))) })

                    Text("Roda Idle", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Keamanan", value = v.idleWheelSecurity, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(idleWheelSecurity = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.idleWheelCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(idleWheelCracks = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.idleWheelDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(idleWheelDeformation = it))) })
                    InspectionResultInput(label = "Kondisi Flensa", value = v.idleWheelFlangeCondition, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(idleWheelFlangeCondition = it))) })

                    Text("Penghubung Roda / Gardan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Kelurusan", value = v.wheelConnectorBogieAxleStraightness, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(wheelConnectorBogieAxleStraightness = it))) })
                    InspectionResultInput(label = "Cross Joint", value = v.wheelConnectorBogieAxleCrossJoint, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(wheelConnectorBogieAxleCrossJoint = it))) })
                    InspectionResultInput(label = "Pelumasan", value = v.wheelConnectorBogieAxleLubrication, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(wheelConnectorBogieAxleLubrication = it))) })

                    Text("Stoper Bumper pada Girder", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Kondisi", value = v.stopperBumperOnGirderCondition, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(stopperBumperOnGirderCondition = it))) })
                    InspectionResultInput(label = "Penguat", value = v.stopperBumperOnGirderReinforcement, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(stopperBumperOnGirderReinforcement = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Komponen Traversing Trolley") {
                    Text("Rumah Roda Gigi (Traversing)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    InspectionResultInput(label = "Pengikatan", value = v.traversingGearboxTrolleyCarrierFastening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingGearboxTrolleyCarrierFastening = it))) })
                    InspectionResultInput(label = "Korosi", value = v.traversingGearboxTrolleyCarrierCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingGearboxTrolleyCarrierCorrosion = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.traversingGearboxTrolleyCarrierCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingGearboxTrolleyCarrierCracks = it))) })
                    InspectionResultInput(label = "Minyak Pelumas", value = v.traversingGearboxTrolleyCarrierLubricatingOil, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingGearboxTrolleyCarrierLubricatingOil = it))) })
                    InspectionResultInput(label = "Oli Seal", value = v.traversingGearboxTrolleyCarrierOilSeal, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(traversingGearboxTrolleyCarrierOilSeal = it))) })

                    Text("Roda Penggerak Trolley", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Keausan", value = v.driveWheelOnTrolleyWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelOnTrolleyWear = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.driveWheelOnTrolleyCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelOnTrolleyCracks = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.driveWheelOnTrolleyDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelOnTrolleyDeformation = it))) })
                    InspectionResultInput(label = "Kondisi Flensa", value = v.driveWheelOnTrolleyFlangeCondition, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelOnTrolleyFlangeCondition = it))) })
                    InspectionResultInput(label = "Kondisi Rantai", value = v.driveWheelOnTrolleyChainCondition, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(driveWheelOnTrolleyChainCondition = it))) })

                    Text("Roda Idle Trolley", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Keausan", value = v.idleWheelOnTrolleyWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(idleWheelOnTrolleyWear = it))) })
                    InspectionResultInput(label = "Keretakan", value = v.idleWheelOnTrolleyCracks, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(idleWheelOnTrolleyCracks = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.idleWheelOnTrolleyDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(idleWheelOnTrolleyDeformation = it))) })
                    InspectionResultInput(label = "Kondisi Flensa", value = v.idleWheelOnTrolleyFlangeCondition, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(idleWheelOnTrolleyFlangeCondition = it))) })

                    Text("Penghubung Roda Trolley / Gardan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Kelurusan", value = v.wheelConnectorBogieAxleOnTrolleyStraightness, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(wheelConnectorBogieAxleOnTrolleyStraightness = it))) })
                    InspectionResultInput(label = "Cross Joint", value = v.wheelConnectorBogieAxleOnTrolleyCrossJoint, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(wheelConnectorBogieAxleOnTrolleyCrossJoint = it))) })
                    InspectionResultInput(label = "Pelumasan", value = v.wheelConnectorBogieAxleOnTrolleyLubrication, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(wheelConnectorBogieAxleOnTrolleyLubrication = it))) })

                    Text("Stoper Bumper pada Girder (Trolley)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Kondisi", value = v.stopperBumperOnGirderOnTrolleyCondition, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(stopperBumperOnGirderOnTrolleyCondition = it))) })
                    InspectionResultInput(label = "Penguat", value = v.stopperBumperOnGirderOnTrolleyReinforcement, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(stopperBumperOnGirderOnTrolleyReinforcement = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Mekanisme Angkat (Hoist)") {
                    Text("Drum Tromol Gulung", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    InspectionResultInput(label = "Alur", value = v.windingDrumGroove, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(windingDrumGroove = it))) })
                    InspectionResultInput(label = "Bibir Alur", value = v.windingDrumGrooveFlange, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(windingDrumGrooveFlange = it))) })
                    InspectionResultInput(label = "Flens-Flens", value = v.windingDrumFlanges, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(windingDrumFlanges = it))) })

                    Text("Rem", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Keausan", value = v.brakeWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(brakeWear = it))) })
                    InspectionResultInput(label = "Penyetelan", value = v.brakeAdjustment, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(brakeAdjustment = it))) })

                    Text("Gear Box Hoist", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Pelumasan", value = v.hoistGearboxLubrication, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hoistGearboxLubrication = it))) })
                    InspectionResultInput(label = "Oli Seal", value = v.hoistGearboxOilSeal, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(hoistGearboxOilSeal = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Pully / Cakra & Kait") {
                    Text("Pully / Cakra / Chain Sproket", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    InspectionResultInput(label = "Alur Pully", value = v.pulleyDiscChainSprocketPulleyGroove, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pulleyDiscChainSprocketPulleyGroove = it))) })
                    InspectionResultInput(label = "Bibir Pully", value = v.pulleyDiscChainSprocketPulleyFlange, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pulleyDiscChainSprocketPulleyFlange = it))) })
                    InspectionResultInput(label = "Pin Pully", value = v.pulleyDiscChainSprocketPulleyPin, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pulleyDiscChainSprocketPulleyPin = it))) })
                    InspectionResultInput(label = "Bantalan", value = v.pulleyDiscChainSprocketBearing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pulleyDiscChainSprocketBearing = it))) })
                    InspectionResultInput(label = "Pelindung Pully", value = v.pulleyDiscChainSprocketPulleyGuard, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pulleyDiscChainSprocketPulleyGuard = it))) })
                    InspectionResultInput(label = "Penghadang Tali/Rantai", value = v.pulleyDiscChainSprocketWireRopeChainGuard, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(pulleyDiscChainSprocketWireRopeChainGuard = it))) })

                    Text("Kait Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Keausan", value = v.mainHookWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainHookWear = it))) })
                    InspectionResultInput(label = "Kerenggangan Mulut", value = v.mainHookThroatOpening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainHookThroatOpening = it))) })
                    InspectionResultInput(label = "Mur & Bantalan Putar", value = v.mainHookSwivelNutAndBearing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainHookSwivelNutAndBearing = it))) })
                    InspectionResultInput(label = "Trunion", value = v.mainHookTrunnion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainHookTrunnion = it))) })

                    Text("Kait Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Keausan", value = v.auxiliaryHookWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryHookWear = it))) })
                    InspectionResultInput(label = "Kerenggangan Mulut", value = v.auxiliaryHookThroatOpening, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryHookThroatOpening = it))) })
                    InspectionResultInput(label = "Mur & Bantalan Putar", value = v.auxiliaryHookSwivelNutAndBearing, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryHookSwivelNutAndBearing = it))) })
                    InspectionResultInput(label = "Trunion", value = v.auxiliaryHookTrunnion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryHookTrunnion = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Tali Kawat Baja & Rantai") {
                    Text("Tali Kawat Baja Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    InspectionResultInput(label = "Korosi", value = v.mainWireRopeCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWireRopeCorrosion = it))) })
                    InspectionResultInput(label = "Keausan", value = v.mainWireRopeWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWireRopeWear = it))) })
                    InspectionResultInput(label = "Putus", value = v.mainWireRopeBroken, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWireRopeBroken = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.mainWireRopeDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainWireRopeDeformation = it))) })

                    Text("Tali Kawat Baja Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Korosi", value = v.auxiliaryWireRopeCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWireRopeCorrosion = it))) })
                    InspectionResultInput(label = "Keausan", value = v.auxiliaryWireRopeWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWireRopeWear = it))) })
                    InspectionResultInput(label = "Putus", value = v.auxiliaryWireRopeBroken, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWireRopeBroken = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.auxiliaryWireRopeDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryWireRopeDeformation = it))) })

                    Text("Rantai Utama", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Korosi", value = v.mainChainCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainChainCorrosion = it))) })
                    InspectionResultInput(label = "Keausan", value = v.mainChainWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainChainWear = it))) })
                    InspectionResultInput(label = "Keretakan / Putus", value = v.mainChainCrackedBroken, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainChainCrackedBroken = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.mainChainDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(mainChainDeformation = it))) })

                    Text("Rantai Tambahan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Korosi", value = v.auxiliaryChainCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryChainCorrosion = it))) })
                    InspectionResultInput(label = "Keausan", value = v.auxiliaryChainWear, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryChainWear = it))) })
                    InspectionResultInput(label = "Keretakan / Putus", value = v.auxiliaryChainCrackedBroken, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryChainCrackedBroken = it))) })
                    InspectionResultInput(label = "Perubahan Bentuk", value = v.auxiliaryChainDeformation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(auxiliaryChainDeformation = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Alat Pengaman & Kontrol") {
                    Text("Limit Switch (LS)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    InspectionResultInput(label = "LS Long Travelling", value = v.limitSwitchLsLongTraveling, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(limitSwitchLsLongTraveling = it))) })
                    InspectionResultInput(label = "LS Cross Travelling", value = v.limitSwitchLsCrossTraveling, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(limitSwitchLsCrossTraveling = it))) })
                    InspectionResultInput(label = "LS Gerakan Angkat", value = v.limitSwitchLsHoisting, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(limitSwitchLsHoisting = it))) })

                    Text("Ruang Operator / Kabin", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    InspectionResultInput(label = "Tangga Pengaman", value = v.operatorRoomCabinSafetyLadder, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinSafetyLadder = it))) })
                    InspectionResultInput(label = "Pintu", value = v.operatorRoomCabinDoor, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinDoor = it))) })
                    InspectionResultInput(label = "Jendela", value = v.operatorRoomCabinWindow, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinWindow = it))) })
                    InspectionResultInput(label = "Kipas / AC", value = v.operatorRoomCabinFanAC, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinFanAC = it))) })
                    InspectionResultInput(label = "Tuas/Tombol Kontrol", value = v.operatorRoomCabinControlLeversButtons, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinControlLeversButtons = it))) })
                    InspectionResultInput(label = "Pendant Kontrol", value = v.operatorRoomCabinPendantControl, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinPendantControl = it))) })
                    InspectionResultInput(label = "Penerangan", value = v.operatorRoomCabinLighting, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinLighting = it))) })
                    InspectionResultInput(label = "Klakson", value = v.operatorRoomCabinHorn, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinHorn = it))) })
                    InspectionResultInput(label = "Pengaman Lebur", value = v.operatorRoomCabinFuseProtection, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinFuseProtection = it))) })
                    InspectionResultInput(label = "Alat Komunikasi", value = v.operatorRoomCabinCommunicationDevice, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinCommunicationDevice = it))) })
                    InspectionResultInput(label = "Pemadam Api (Apar)", value = v.operatorRoomCabinFireExtinguisher, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinFireExtinguisher = it))) })
                    InspectionResultInput(label = "Tanda Pengoperasian", value = v.operatorRoomCabinOperatingSigns, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinOperatingSigns = it))) })
                    InspectionResultInput(label = "Kunci Kontak/Master Switch", value = v.operatorRoomCabinIgnitionKeyMasterSwitch, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(operatorRoomCabinIgnitionKeyMasterSwitch = it))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Komponen Listrik") {
                    InspectionResultInput(label = "Penyambung Panel", value = v.electricalComponentPanelConnectorConductor, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentPanelConnectorConductor = it))) })
                    InspectionResultInput(label = "Pelindung Penghantar", value = v.electricalComponentConductorProtection, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentConductorProtection = it))) })
                    InspectionResultInput(label = "Sistem Pengaman Instalasi", value = v.electricalComponentMotorInstallationSafetySystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentMotorInstallationSafetySystem = it))) })
                    InspectionResultInput(label = "Sistem Pembumian", value = v.electricalComponentGroundingSystem, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentGroundingSystem = it))) })
                    InspectionResultInput(label = "Instalasi", value = v.electricalComponentInstallation, onValueChange = { onDataChange(report.copy(visualInspection = v.copy(electricalComponentInstallation = it))) })
                }
            }
        }

        // NDE Section
        item {
            val nde = report.nonDestructiveExamination
            ExpandableSection(title = "PEMERIKSAAN TIDAK MERUSAK") {
                ExpandableSubSection("Rantai (Chain)") {
                    val data = nde.chain
                    FormTextField(label = "Metode", value = data.method, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(chain = data.copy(method = it)))) })
                    Spacer(modifier = Modifier.height(8.dp))
                    FilledTonalButton(onClick = { showNdeChainDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tambah Data Pemeriksaan Rantai")
                    }
                    data.items.forEachIndexed { index, item ->
                        ListItemWithDelete(onDelete = { viewModel.deleteOverheadCraneNdeChainItem(index) }) {
                            Text("Nama: ${item.name}", fontWeight = FontWeight.SemiBold)
                            Text("Hasil Ukur: ${item.measurementMm}mm (Spec: ${item.specificationMm}mm)")
                            Text("Faktor Keamanan: ${item.safetyFactor}")
                            Text("Cacat: ${if(item.finding.status == true) "Ada" else "Tidak" }. Keterangan: ${item.finding.result}")
                        }
                    }
                }
                HorizontalDivider()
                ExpandableSubSection("Kait (Hook) Utama") {
                    val hookData = nde.mainHook
                    FormTextField(label = "Metode", value = hookData.method, onValueChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hookData.copy(method = it)))) })
                    Spacer(modifier = Modifier.height(8.dp))

                    NdeHookMeasurementInput(
                        spec = hookData.specification,
                        result = hookData.measurementResult,
                        tolerance = hookData.tolerance,
                        onResultChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hookData.copy(measurementResult = it)))) },
                        onSpecChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hookData.copy(specification = it)))) },
                        onToleranceChange = { onDataChange(report.copy(nonDestructiveExamination = nde.copy(mainHook = hookData.copy(tolerance = it)))) }
                    )
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
                    DynamicTestItemInput(label = "Safety Device", item = data.withoutLoad.safetyLatch, onValueChange = { onDataChange(report.copy(testing = testing.copy(dynamicTest = data.copy(withoutLoad = data.withoutLoad.copy(safetyLatch = it))))) })
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
                    StaticDeflectionInput(
                        deflectionMeasurement = data.deflectionMeasurement,
                        onValueChange = { onDataChange(report.copy(testing = testing.copy(staticTest = data.copy(deflectionMeasurement = it)))) }
                    )
                    FormTextField(label = "Catatan", value = data.notes, onValueChange = { onDataChange(report.copy(testing = testing.copy(staticTest = data.copy(notes = it)))) })
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
                    ListItemWithDelete(onDelete = { viewModel.removeOverheadCraneConclusionSummaryItem(index) }) {
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
                    ListItemWithDelete(onDelete = { viewModel.removeOverheadCraneConclusionRecommendationItem(index) }) {
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
private fun MovementDataInput(
    label: String,
    data: OverheadCraneMovementData,
    onValueChange: (OverheadCraneMovementData) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp)) {
        Text(label, modifier = Modifier.weight(2f), style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value = data.hoisting, onValueChange = { onValueChange(data.copy(hoisting = it)) }, modifier = Modifier
            .weight(1f)
            .padding(horizontal = 2.dp), textStyle = MaterialTheme.typography.bodySmall, shape = MaterialTheme.shapes.small)
        OutlinedTextField(value = data.traveling, onValueChange = { onValueChange(data.copy(traveling = it)) }, modifier = Modifier
            .weight(1f)
            .padding(horizontal = 2.dp), textStyle = MaterialTheme.typography.bodySmall, shape = MaterialTheme.shapes.small)
        OutlinedTextField(value = data.traversing, onValueChange = { onValueChange(data.copy(traversing = it)) }, modifier = Modifier
            .weight(1f)
            .padding(horizontal = 2.dp), textStyle = MaterialTheme.typography.bodySmall, shape = MaterialTheme.shapes.small)
    }
}

@Composable
private fun InspectionResultInput(
    label: String,
    value: OverheadCraneInspectionResult,
    onValueChange: (OverheadCraneInspectionResult) -> Unit
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
                .clickable { onValueChange(value.copy(status = !(value.status ?: false))) }
                .padding(start = 4.dp, end = 4.dp)
        ) {
            Checkbox(
                checked = value.status ?: false,
                onCheckedChange = null
            )
            Text("Memenuhi")
        }
    }
}

@Composable
private fun DynamicTestItemInput(
    label: String,
    item: OverheadCraneDynamicTestItem,
    onValueChange: (OverheadCraneDynamicTestItem) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        FormTextField(label = "Seharusnya", value = item.shouldBe, onValueChange = { onValueChange(item.copy(shouldBe = it)) })
        FormTextField(label = "Dicoba / Diukur", value = item.testedMeasured, onValueChange = { onValueChange(item.copy(testedMeasured = it)) })
        FormTextField(label = "Keterangan", value = item.remarks, onValueChange = { onValueChange(item.copy(remarks = it)) })
    }
}

@Composable
private fun LoadTestResultInput(
    label: String,
    item: OverheadCraneLoadTestResult,
    onValueChange: (OverheadCraneLoadTestResult) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            FormTextField(label = "Hoist", value = item.hoist, onValueChange = { onValueChange(item.copy(hoist = it)) }, modifier = Modifier.weight(1f))
            FormTextField(label = "Traversing", value = item.traversing, onValueChange = { onValueChange(item.copy(traversing = it)) }, modifier = Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            FormTextField(label = "Travelling", value = item.travelling, onValueChange = { onValueChange(item.copy(travelling = it)) }, modifier = Modifier.weight(1f))
            FormTextField(label = "Brake", value = item.brakeSystem, onValueChange = { onValueChange(item.copy(brakeSystem = it)) }, modifier = Modifier.weight(1f))
        }
        FormTextField(label = "Keterangan", value = item.remarks, onValueChange = { onValueChange(item.copy(remarks = it)) })
    }
}

@Composable
private fun StaticDeflectionInput(
    deflectionMeasurement: OverheadCraneDeflectionMeasurement,
    onValueChange: (OverheadCraneDeflectionMeasurement) -> Unit
) {
    val singleGirderPoints = listOf("1", "2", "3")
    val doubleGirderPoints = listOf("1", "2", "3", "4", "5", "6")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Single Girder", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            singleGirderPoints.forEach { point ->
                FormTextField(
                    label = point,
                    value = deflectionMeasurement.singleGirder[point],
                    onValueChange = {
                        val newMap = deflectionMeasurement.singleGirder.toMutableMap()
                        newMap[point] = it
                        onValueChange(deflectionMeasurement.copy(singleGirder = newMap))
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        HorizontalDivider()
        Text("Double Girder", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
        doubleGirderPoints.chunked(3).forEach { rowPoints ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                rowPoints.forEach { point ->
                    FormTextField(
                        label = point,
                        value = deflectionMeasurement.doubleGirder[point],
                        onValueChange = {
                            val newMap = deflectionMeasurement.doubleGirder.toMutableMap()
                            newMap[point] = it
                            onValueChange(deflectionMeasurement.copy(doubleGirder = newMap))
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun NdeHookMeasurementInput(
    spec: OverheadCraneNdeMeasurement,
    result: OverheadCraneNdeMeasurement,
    tolerance: OverheadCraneNdeMeasurement,
    onSpecChange: (OverheadCraneNdeMeasurement) -> Unit,
    onResultChange: (OverheadCraneNdeMeasurement) -> Unit,
    onToleranceChange: (OverheadCraneNdeMeasurement) -> Unit
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
        InspectionResultInput(label = "Temuan", value = spec.finding, onValueChange = { onSpecChange(spec.copy(finding = it)) })
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
        InspectionResultInput(label = "Temuan", value = result.finding, onValueChange = { onResultChange(result.copy(finding = it)) })
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
        InspectionResultInput(label = "Temuan", value = tolerance.finding, onValueChange = { onToleranceChange(tolerance.copy(finding = it)) })
    }
}

private fun getMeasurementValueByLabel(measurement: OverheadCraneNdeMeasurement, label: String): String {
    return when (label) {
        "A" -> measurement.a; "B" -> measurement.b; "C" -> measurement.c; "D" -> measurement.d
        "E" -> measurement.e; "F" -> measurement.f; "G" -> measurement.g; "H" -> measurement.h
        else -> ""
    }
}

private fun getUpdatedMeasurement(measurement: OverheadCraneNdeMeasurement, label: String, newValue: String): OverheadCraneNdeMeasurement {
    return when (label) {
        "A" -> measurement.copy(a = newValue); "B" -> measurement.copy(b = newValue)
        "C" -> measurement.copy(c = newValue); "D" -> measurement.copy(d = newValue)
        "E" -> measurement.copy(e = newValue); "F" -> measurement.copy(f = newValue)
        "G" -> measurement.copy(g = newValue); "H" -> measurement.copy(h = newValue)
        else -> measurement
    }
}

//endregion

@Preview(showBackground = true, name = "Overhead Crane Screen Preview")
@Composable
private fun OverheadCraneScreenPreview() {
    KoinApplicationPreview(application = { modules(previewModule) }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                OverheadCraneScreen(
                    modifier = Modifier.fillMaxSize().padding(paddingValues).imePadding(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
            }
        }
    }
}