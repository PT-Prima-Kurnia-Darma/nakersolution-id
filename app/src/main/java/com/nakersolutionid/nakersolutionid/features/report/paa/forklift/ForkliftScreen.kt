package com.nakersolutionid.nakersolutionid.features.report.paa.forklift

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
fun ForkliftScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: PAAViewModel = koinViewModel()
) {
    val uiState by viewModel.forkliftUiState.collectAsStateWithLifecycle()
    val report = uiState.forkliftInspectionReport
    val onDataChange = viewModel::onReportDataChange

    var showNdeChainDialog by remember { mutableStateOf(false) }
    var showNdeForkDialog by remember { mutableStateOf(false) }
    var showLoadTestDialog by remember { mutableStateOf(false) }
    var showSummaryDialog by remember { mutableStateOf(false) }
    var showRecommendationDialog by remember { mutableStateOf(false) }

    if (showNdeChainDialog) {
        AddNdeChainItemDialog(
            onDismissRequest = { showNdeChainDialog = false },
            onConfirm = {
                viewModel.addNdeChainItem(it)
                showNdeChainDialog = false
            }
        )
    }

    if (showNdeForkDialog) {
        AddNdeForkItemDialog(
            onDismissRequest = { showNdeForkDialog = false },
            onConfirm = {
                viewModel.addNdeForkItem(it)
                showNdeForkDialog = false
            }
        )
    }

    if (showLoadTestDialog) {
        AddLoadTestItemDialog(
            onDismissRequest = { showLoadTestDialog = false },
            onConfirm = {
                viewModel.addLoadTestItem(it)
                showLoadTestDialog = false
            }
        )
    }

    if (showSummaryDialog) {
        AddStringDialog(
            title = "Tambah Poin Kesimpulan",
            label = "Poin Kesimpulan",
            onDismissRequest = { showSummaryDialog = false },
            onConfirm = {
                viewModel.addConclusionSummaryItem(it)
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
                viewModel.addConclusionRecommendationItem(it)
                showRecommendationDialog = false
            }
        )
    }


    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        // General Data Section
        item {
            val data = report.generalData
            ExpandableSection(title = "DATA UMUM", initiallyExpanded = true) {
                FormTextField(label = "Pemilik", value = data.owner, onValueChange = { onDataChange(report.copy(generalData = data.copy(owner = it))) })
                FormTextField(label = "Alamat", value = data.address, onValueChange = { onDataChange(report.copy(generalData = data.copy(address = it))) })
                FormTextField(label = "Pemakai", value = data.user, onValueChange = { onDataChange(report.copy(generalData = data.copy(user = it))) })
                FormTextField(label = "Pengurus Kontraktor / Penanggung Jawab", value = data.personInCharge, onValueChange = { onDataChange(report.copy(generalData = data.copy(personInCharge = it))) })
                FormTextField(label = "Lokasi Unit", value = data.unitLocation, onValueChange = { onDataChange(report.copy(generalData = data.copy(unitLocation = it))) })
                FormTextField(label = "Nama Operator", value = data.operatorName, onValueChange = { onDataChange(report.copy(generalData = data.copy(operatorName = it))) })
                FormTextField(label = "Jenis Pesawat Angkat", value = data.driveType, onValueChange = { onDataChange(report.copy(generalData = data.copy(driveType = it))) })
                FormTextField(label = "Pabrik Pembuat", value = data.manufacturer, onValueChange = { onDataChange(report.copy(generalData = data.copy(manufacturer = it))) })
                FormTextField(label = "Model/Type", value = data.brandType, onValueChange = { onDataChange(report.copy(generalData = data.copy(brandType = it))) })
                FormTextField(label = "Tahun Pembuatan", value = data.yearOfManufacture, onValueChange = { onDataChange(report.copy(generalData = data.copy(yearOfManufacture = it))) })
                FormTextField(label = "No. Series / No. Unit", value = data.serialNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(serialNumber = it))) })
                FormTextField(label = "Kapasitas", value = data.liftingCapacity, onValueChange = { onDataChange(report.copy(generalData = data.copy(liftingCapacity = it))) })
                FormTextField(label = "Digunakan untuk", value = data.intendedUse, onValueChange = { onDataChange(report.copy(generalData = data.copy(intendedUse = it))) })
                FormTextField(label = "Nomor Surat Keterangan", value = data.permitNumber, onValueChange = { onDataChange(report.copy(generalData = data.copy(permitNumber = it))) })
                FormTextField(label = "Data Riwayat Pesawat", value = data.equipmentHistory, onValueChange = { onDataChange(report.copy(generalData = data.copy(equipmentHistory = it))) })
            }
        }

        // Technical Data Section
        item {
            val data = report.technicalData
            ExpandableSection(title = "DATA TEKNIK") {
                ExpandableSubSection("Spesifikasi Pesawat") {
                    val d = data.specifications
                    FormTextField(label = "No. Seri / Serial Number", value = d.serialNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = d.copy(serialNumber = it)))) })
                    FormTextField(label = "Kapasitas / Capacity", value = d.capacity, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = d.copy(capacity = it)))) })
                    FormTextField(label = "Perlengkapan / Attachment", value = d.attachment, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = d.copy(attachment = it)))) })
                    FormTextField(label = "Dimensi Garpu (P x L x T)", value = d.forkDimension, onValueChange = { onDataChange(report.copy(technicalData = data.copy(specifications = d.copy(forkDimension = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Kecepatan (Speed)") {
                    val d = data.speed
                    FormTextField(label = "Angkat / Lifting", value = d.lifting, onValueChange = { onDataChange(report.copy(technicalData = data.copy(speed = d.copy(lifting = it)))) })
                    FormTextField(label = "Turun / Lowering", value = d.lowering, onValueChange = { onDataChange(report.copy(technicalData = data.copy(speed = d.copy(lowering = it)))) })
                    FormTextField(label = "Jalan / Travelling", value = d.travelling, onValueChange = { onDataChange(report.copy(technicalData = data.copy(speed = d.copy(travelling = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Penggerak Utama (Prime Mover)") {
                    val d = data.primeMover
                    FormTextField(label = "Putaran / Revolution", value = d.revolution, onValueChange = { onDataChange(report.copy(technicalData = data.copy(primeMover = d.copy(revolution = it)))) })
                    FormTextField(label = "Merk / Tipe", value = d.brandType, onValueChange = { onDataChange(report.copy(technicalData = data.copy(primeMover = d.copy(brandType = it)))) })
                    FormTextField(label = "Nomor Seri / Serial Number", value = d.serialNumber, onValueChange = { onDataChange(report.copy(technicalData = data.copy(primeMover = d.copy(serialNumber = it)))) })
                    FormTextField(label = "Tahun Pembuatan", value = d.yearOfManufacture, onValueChange = { onDataChange(report.copy(technicalData = data.copy(primeMover = d.copy(yearOfManufacture = it)))) })
                    FormTextField(label = "Daya", value = d.power, onValueChange = { onDataChange(report.copy(technicalData = data.copy(primeMover = d.copy(power = it)))) })
                    FormTextField(label = "Jumlah Silinder", value = d.numberOfCylinders, onValueChange = { onDataChange(report.copy(technicalData = data.copy(primeMover = d.copy(numberOfCylinders = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Dimensi (Dimension)") {
                    val d = data.dimensions
                    FormTextField(label = "Panjang / Length", value = d.length, onValueChange = { onDataChange(report.copy(technicalData = data.copy(dimensions = d.copy(length = it)))) })
                    FormTextField(label = "Lebar / Width", value = d.width, onValueChange = { onDataChange(report.copy(technicalData = data.copy(dimensions = d.copy(width = it)))) })
                    FormTextField(label = "Tinggi / High", value = d.height, onValueChange = { onDataChange(report.copy(technicalData = data.copy(dimensions = d.copy(height = it)))) })
                    FormTextField(label = "Tinggi Angkat Garpu / Fork", value = d.forkLiftingHeight, onValueChange = { onDataChange(report.copy(technicalData = data.copy(dimensions = d.copy(forkLiftingHeight = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Tekanan Roda (Tire Pressure)") {
                    val d = data.tirePressure
                    FormTextField(label = "Roda Penggerak / Drive Wheel", value = d.driveWheel, onValueChange = { onDataChange(report.copy(technicalData = data.copy(tirePressure = d.copy(driveWheel = it)))) })
                    FormTextField(label = "Roda Kemudi / Steering Wheel", value = d.steeringWheel, onValueChange = { onDataChange(report.copy(technicalData = data.copy(tirePressure = d.copy(steeringWheel = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Roda Penggerak (Driver Wheel)") {
                    val d = data.driveWheel
                    FormTextField(label = "Ukuran / Size", value = d.size, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveWheel = d.copy(size = it)))) })
                    FormTextField(label = "Type", value = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(driveWheel = d.copy(type = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Roda Kemudi (Steering Wheel)") {
                    val d = data.steeringWheel
                    FormTextField(label = "Ukuran / Size", value = d.size, onValueChange = { onDataChange(report.copy(technicalData = data.copy(steeringWheel = d.copy(size = it)))) })
                    FormTextField(label = "Type", value = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(steeringWheel = d.copy(type = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Rem Jalan (Travelling Brake)") {
                    val d = data.travellingBrake
                    FormTextField(label = "Ukuran / Size", value = d.size, onValueChange = { onDataChange(report.copy(technicalData = data.copy(travellingBrake = d.copy(size = it)))) })
                    FormTextField(label = "Type", value = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(travellingBrake = d.copy(type = it)))) })
                }
                HorizontalDivider()
                ExpandableSubSection("Pompa Hidraulik (Hydraulic Pump)") {
                    val d = data.hydraulicPump
                    FormTextField(label = "Tekanan", value = d.pressure, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hydraulicPump = d.copy(pressure = it)))) })
                    FormTextField(label = "Type", value = d.type, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hydraulicPump = d.copy(type = it)))) })
                    FormTextField(label = "Relief Valve", value = d.reliefValve, onValueChange = { onDataChange(report.copy(technicalData = data.copy(hydraulicPump = d.copy(reliefValve = it)))) })
                }
            }
        }

        // Visual Inspection Section
        item {
            val data = report.visualInspection
            ExpandableSection(title = "PEMERIKSAAN VISUAL DAN FUNGSI") {
                ExpandableSubSection("Kerangka Utama / Chasis") {
                    Text("Rangka Penguat", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Korosi", value = data.chassisReinforcementCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(chassisReinforcementCorrosion = it))) })
                    ForkliftResultStatusInput(label = "Keretakan", value = data.chassisReinforcementCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(chassisReinforcementCracks = it))) })
                    ForkliftResultStatusInput(label = "Perubahan Bentuk", value = data.chassisReinforcementDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(chassisReinforcementDeformation = it))) })
                    Spacer(Modifier.height(4.dp))
                    Text("Pemberat (C/W)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Korosi", value = data.counterweightCorrosion, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(counterweightCorrosion = it))) })
                    ForkliftResultStatusInput(label = "Kondisi", value = data.counterweightCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(counterweightCondition = it))) })
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                ExpandableSubSection("Perlengkapan Lain") {
                    ForkliftResultStatusInput(label = "Lantai / Dek", value = data.otherEquipmentFloorDeck, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(otherEquipmentFloorDeck = it))) })
                    ForkliftResultStatusInput(label = "Tangga / pijakan", value = data.otherEquipmentStairs, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(otherEquipmentStairs = it))) })
                    ForkliftResultStatusInput(label = "Baut-baut Pengikat", value = data.otherEquipmentBindingBolts, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(otherEquipmentBindingBolts = it))) })
                    ForkliftResultStatusInput(label = "Dudukan Operator (Jok)", value = data.otherEquipmentOperatorSeat, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(otherEquipmentOperatorSeat = it))) })
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                ExpandableSubSection("Penggerak Utama / Prime Mover") {
                    Text("Sistem", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Pendingin", value = data.primeMoverCoolingSystem, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverCoolingSystem = it))) })
                    ForkliftResultStatusInput(label = "Pelumas", value = data.primeMoverLubricantSystem, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverLubricantSystem = it))) })
                    ForkliftResultStatusInput(label = "Bahan Bakar", value = data.primeMoverFuelSystem, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverFuelSystem = it))) })
                    ForkliftResultStatusInput(label = "Pemasukan Udara", value = data.primeMoverAirIntakeSystem, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverAirIntakeSystem = it))) })
                    ForkliftResultStatusInput(label = "Gas Buang", value = data.primeMoverExhaustSystem, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverExhaustSystem = it))) })
                    ForkliftResultStatusInput(label = "Starter", value = data.primeMoverStarterSystem, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverStarterSystem = it))) })
                    Spacer(Modifier.height(4.dp))
                    Text("Kelistrikan", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Accu / Battery", value = data.primeMoverElectricalBattery, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverElectricalBattery = it))) })
                    ForkliftResultStatusInput(label = "Dinamo Starting", value = data.primeMoverElectricalStartingDynamo, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverElectricalStartingDynamo = it))) })
                    ForkliftResultStatusInput(label = "Alternator", value = data.primeMoverElectricalAlternator, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverElectricalAlternator = it))) })
                    ForkliftResultStatusInput(label = "Kabel Accu", value = data.primeMoverElectricalBatteryCable, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverElectricalBatteryCable = it))) })
                    ForkliftResultStatusInput(label = "Kabel Instalasi", value = data.primeMoverElectricalWiring, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverElectricalWiring = it))) })
                    ForkliftResultStatusInput(label = "Lampu Penerangan", value = data.primeMoverElectricalLighting, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverElectricalLighting = it))) })
                    ForkliftResultStatusInput(label = "Lampu Pengaman / Sign", value = data.primeMoverElectricalSafetyLights, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverElectricalSafetyLights = it))) })
                    ForkliftResultStatusInput(label = "Klakson", value = data.primeMoverElectricalHorn, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverElectricalHorn = it))) })
                    ForkliftResultStatusInput(label = "Pengaman Lebur / Sekring", value = data.primeMoverElectricalFuse, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(primeMoverElectricalFuse = it))) })
                    Spacer(Modifier.height(4.dp))
                    Text("DashBoard", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Indikator Suhu", value = data.dashboardTemperatureIndicator, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(dashboardTemperatureIndicator = it))) })
                    ForkliftResultStatusInput(label = "Tekanan Oli Mesin", value = data.dashboardEngineOilPressure, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(dashboardEngineOilPressure = it))) })
                    ForkliftResultStatusInput(label = "Tekanan Hidraulik", value = data.dashboardHydraulicPressure, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(dashboardHydraulicPressure = it))) })
                    ForkliftResultStatusInput(label = "Hour Meter", value = data.dashboardHourMeter, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(dashboardHourMeter = it))) })
                    ForkliftResultStatusInput(label = "Pemanas awal / Glow Plug", value = data.dashboardGlowPlug, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(dashboardGlowPlug = it))) })
                    ForkliftResultStatusInput(label = "Indikator Bahan Bakar", value = data.dashboardFuelIndicator, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(dashboardFuelIndicator = it))) })
                    ForkliftResultStatusInput(label = "Indikator Beban", value = data.dashboardLoadIndicator, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(dashboardLoadIndicator = it))) })
                    ForkliftResultStatusInput(label = "Load Chart / Name Plate", value = data.dashboardLoadChart, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(dashboardLoadChart = it))) })
                    ForkliftResultStatusInput(label = "Pengisian Accu / Ampere", value = data.dashboardAmpereMeter, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(dashboardAmpereMeter = it))) })
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                ExpandableSubSection("Komponen Bagian Bawah / Power Train") {
                    Text("Sistem Kemudi", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Kemudi Roda", value = data.powerTrainSteeringWheel, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainSteeringWheel = it))) })
                    ForkliftResultStatusInput(label = "Batang Kemudi", value = data.powerTrainSteeringRod, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainSteeringRod = it))) })
                    ForkliftResultStatusInput(label = "Kotak Gigi / Gear Box", value = data.powerTrainSteeringGearBox, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainSteeringGearBox = it))) })
                    ForkliftResultStatusInput(label = "Pengubah Gerak / Pitman", value = data.powerTrainSteeringPitmanArm, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainSteeringPitmanArm = it))) })
                    ForkliftResultStatusInput(label = "Batang Tarik / Drag Link", value = data.powerTrainSteeringDragLink, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainSteeringDragLink = it))) })
                    ForkliftResultStatusInput(label = "Tire Rod", value = data.powerTrainSteeringTieRod, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainSteeringTieRod = it))) })
                    ForkliftResultStatusInput(label = "Pelumasan", value = data.powerTrainSteeringLubrication, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainSteeringLubrication = it))) })
                    Spacer(Modifier.height(4.dp))
                    Text("Roda (Wheel)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Front (Roda Penggerak)", value = data.powerTrainWheelFront, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainWheelFront = it))) })
                    ForkliftResultStatusInput(label = "Rear wheel (Roda kemudi)", value = data.powerTrainWheelRear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainWheelRear = it))) })
                    ForkliftResultStatusInput(label = "Baut Pengikat", value = data.powerTrainWheelBindingBolts, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainWheelBindingBolts = it))) })
                    ForkliftResultStatusInput(label = "Tromol / Hub", value = data.powerTrainWheelHub, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainWheelHub = it))) })
                    ForkliftResultStatusInput(label = "Pelumasan", value = data.powerTrainWheelLubrication, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainWheelLubrication = it))) })
                    ForkliftResultStatusInput(label = "Perlengkapan Mekanis", value = data.powerTrainWheelMechanicalEquipment, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainWheelMechanicalEquipment = it))) })
                    Spacer(Modifier.height(4.dp))
                    Text("Kopling (Clutch)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Rumah Kopling", value = data.powerTrainClutchHousing, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainClutchHousing = it))) })
                    ForkliftResultStatusInput(label = "Kondisi Kopling", value = data.powerTrainClutchCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainClutchCondition = it))) })
                    ForkliftResultStatusInput(label = "Pelumas/oli transmisi", value = data.powerTrainClutchTransmissionOil, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainClutchTransmissionOil = it))) })
                    ForkliftResultStatusInput(label = "Kebocoran Transmisi", value = data.powerTrainClutchTransmissionLeakage, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainClutchTransmissionLeakage = it))) })
                    ForkliftResultStatusInput(label = "Poros Penghubung", value = data.powerTrainClutchConnectingShaft, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainClutchConnectingShaft = it))) })
                    ForkliftResultStatusInput(label = "Perlengkapan Mekanis", value = data.powerTrainClutchMechanicalEquipment, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainClutchMechanicalEquipment = it))) })
                    Spacer(Modifier.height(4.dp))
                    Text("Rem (Brake)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Kondisi Rem Utama", value = data.powerTrainBrakeMainCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainBrakeMainCondition = it))) })
                    ForkliftResultStatusInput(label = "Kondisi Rem Tangan", value = data.powerTrainBrakeHandbrakeCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainBrakeHandbrakeCondition = it))) })
                    ForkliftResultStatusInput(label = "Kondisi Rem Darurat", value = data.powerTrainBrakeEmergencyCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainBrakeEmergencyCondition = it))) })
                    ForkliftResultStatusInput(label = "Kebocoran", value = data.powerTrainBrakeLeakage, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainBrakeLeakage = it))) })
                    ForkliftResultStatusInput(label = "Komponen Mekanis", value = data.powerTrainBrakeMechanicalComponents, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(powerTrainBrakeMechanicalComponents = it))) })
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                ExpandableSubSection("Attachment / Perlengkapan") {
                    Text("Tiang Penyangga (Mast)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Keausan", value = data.attachmentMastWear, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(attachmentMastWear = it))) })
                    ForkliftResultStatusInput(label = "Keretakan", value = data.attachmentMastCracks, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(attachmentMastCracks = it))) })
                    ForkliftResultStatusInput(label = "Perubahan Bentuk", value = data.attachmentMastDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(attachmentMastDeformation = it))) })
                    ForkliftResultStatusInput(label = "Pelumasan", value = data.attachmentMastLubrication, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(attachmentMastLubrication = it))) })
                    ForkliftResultStatusInput(label = "Poros dan Bantalan", value = data.attachmentMastShaftAndBearing, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(attachmentMastShaftAndBearing = it))) })
                    Spacer(Modifier.height(4.dp))
                    Text("Rantai Pengangkat (Lift Chain)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Kondisi Rantai", value = data.attachmentLiftChainCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(attachmentLiftChainCondition = it))) })
                    ForkliftResultStatusInput(label = "Perubahan Bentuk", value = data.attachmentLiftChainDeformation, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(attachmentLiftChainDeformation = it))) })
                    ForkliftResultStatusInput(label = "Pelumasan Rantai", value = data.attachmentLiftChainLubrication, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(attachmentLiftChainLubrication = it))) })
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                ExpandableSubSection("Komponen Hidraulik") {
                    Text("Tangki (Tank)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Kebocoran", value = data.hydraulicTankLeakage, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicTankLeakage = it))) })
                    ForkliftResultStatusInput(label = "Level Oli Hidraulik", value = data.hydraulicTankOilLevel, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicTankOilLevel = it))) })
                    ForkliftResultStatusInput(label = "Kondisi Oli Hidraulik", value = data.hydraulicTankOilCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicTankOilCondition = it))) })
                    ForkliftResultStatusInput(label = "Kondisi Saluran Isap", value = data.hydraulicTankSuctionLineCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicTankSuctionLineCondition = it))) })
                    ForkliftResultStatusInput(label = "Kondisi Saluran Balik", value = data.hydraulicTankReturnLineCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicTankReturnLineCondition = it))) })
                    Spacer(Modifier.height(4.dp))
                    Text("Pompa (Pump)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    ForkliftResultStatusInput(label = "Kebocoran", value = data.hydraulicPumpLeakage, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicPumpLeakage = it))) })
                    ForkliftResultStatusInput(label = "Kondisi Saluran Isap", value = data.hydraulicPumpSuctionLineCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicPumpSuctionLineCondition = it))) })
                    ForkliftResultStatusInput(label = "Kondisi Saluran Tekan", value = data.hydraulicPumpPressureLineCondition, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicPumpPressureLineCondition = it))) })
                    ForkliftResultStatusInput(label = "Fungsi", value = data.hydraulicPumpFunction, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicPumpFunction = it))) })
                    ForkliftResultStatusInput(label = "Kelainan Suara", value = data.hydraulicPumpAbnormalNoise, onValueChange = { onDataChange(report.copy(visualInspection = data.copy(hydraulicPumpAbnormalNoise = it))) })
                }
            }
        }

        // Engine On Inspection
        item {
            val data = report.testing.engineOnInspection
            ExpandableSection(title = "PEMERIKSAAN DENGAN MESIN HIDUP") {
                ForkliftResultStatusInput(label = "Dinamo starter", value = data.dynamoStarter, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(dynamoStarter = it)))) })
                ForkliftResultStatusInput(label = "Kerja instrumen/indikator", value = data.instrumentIndicatorFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(instrumentIndicatorFunction = it)))) })
                ForkliftResultStatusInput(label = "Kerja perlengkapan listrik", value = data.electricalEquipmentFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(electricalEquipmentFunction = it)))) })
                Text("Kebocoran-kebocoran:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=8.dp))
                ForkliftResultStatusInput(label = "Oli mesin", value = data.engineOilLeakage, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(engineOilLeakage = it)))) })
                ForkliftResultStatusInput(label = "Bahan bakar", value = data.fuelLeakage, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(fuelLeakage = it)))) })
                ForkliftResultStatusInput(label = "Air pendingin", value = data.coolantLeakage, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(coolantLeakage = it)))) })
                ForkliftResultStatusInput(label = "Oli hidraulik", value = data.hydraulicOilLeakage, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(hydraulicOilLeakage = it)))) })
                ForkliftResultStatusInput(label = "Oli transmisi", value = data.transmissionOilLeakage, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(transmissionOilLeakage = it)))) })
                ForkliftResultStatusInput(label = "Oli final drive", value = data.finalDriveOilLeakage, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(finalDriveOilLeakage = it)))) })
                ForkliftResultStatusInput(label = "Minyak rem", value = data.brakeFluidLeakage, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(brakeFluidLeakage = it)))) })
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                ForkliftResultStatusInput(label = "Kerja kopling", value = data.clutchFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(clutchFunction = it)))) })
                ForkliftResultStatusInput(label = "Kerja persneling (maju mundur)", value = data.transmissionFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(transmissionFunction = it)))) })
                ForkliftResultStatusInput(label = "Kerja rem tangan dan kaki", value = data.brakeFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(brakeFunction = it)))) })
                ForkliftResultStatusInput(label = "Kerja klakson signal alarm", value = data.hornAlarmFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(hornAlarmFunction = it)))) })
                ForkliftResultStatusInput(label = "Kerja lampu-lampu", value = data.lightsFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(lightsFunction = it)))) })
                ForkliftResultStatusInput(label = "Motor Hidraulik/sistem Hidraulik", value = data.hydraulicSystemFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(hydraulicSystemFunction = it)))) })
                ForkliftResultStatusInput(label = "Kerja silinder stir/power stering", value = data.powerSteeringFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(powerSteeringFunction = it)))) })
                ForkliftResultStatusInput(label = "Kerja silinder pengangkat", value = data.liftCylinderFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(liftCylinderFunction = it)))) })
                ForkliftResultStatusInput(label = "Kerja silinder ungkit", value = data.tiltCylinderFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(tiltCylinderFunction = it)))) })
                ForkliftResultStatusInput(label = "Kondisi gas buang", value = data.exhaustGasCondition, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(exhaustGasCondition = it)))) })
                ForkliftResultStatusInput(label = "Kerja semua tuas-tuas kontrol", value = data.controlLeversFunction, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(controlLeversFunction = it)))) })
                Text("Suara berisik dari:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top=8.dp))
                ForkliftResultStatusInput(label = "Mesin", value = data.engineNoise, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(engineNoise = it)))) })
                ForkliftResultStatusInput(label = "Turbocharger", value = data.turbochargerNoise, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(turbochargerNoise = it)))) })
                ForkliftResultStatusInput(label = "Transmisi", value = data.transmissionNoise, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(transmissionNoise = it)))) })
                ForkliftResultStatusInput(label = "Pompa Hidraulik", value = data.hydraulicPumpNoise, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(hydraulicPumpNoise = it)))) })
                ForkliftResultStatusInput(label = "Tutup pelindung", value = data.guardNoise, onValueChange = { onDataChange(report.copy(testing = report.testing.copy(engineOnInspection = data.copy(guardNoise = it)))) })
            }
        }

        // Lifting Chain Inspection
        item {
            val data = report.nonDestructiveExamination.liftingChainInspection
            ExpandableSection(title = "PEMERIKSAAN RANTAI PENGANGKAT") {
                FilledTonalButton(onClick = { showNdeChainDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Data Rantai")
                }
                Spacer(modifier = Modifier.height(8.dp))
                data.items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("Rantai: ${item.chain}", fontWeight = FontWeight.SemiBold)
                                Text("Jenis & Konstruksi: ${item.typeAndConstruction}")
                                Text("Pitch: ${item.measuredPitchMm}mm (Std: ${item.standardPitchMm}mm)")
                                Text("Pin: ${item.measuredPinMm}mm (Std: ${item.standardPinMm}mm)")
                                Text("Ket: ${item.remarks}")
                            }
                        }
                        IconButton(onClick = { viewModel.deleteNdeChainItem(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Hapus Item Rantai")
                        }
                    }
                }
            }
        }

        // NDT Section
        item {
            val data = report.nonDestructiveExamination.forkNDT
            ExpandableSection(title = "PENGUJIAN TIDAK MERUSAK") {
                FormTextField(
                    label = "Jenis NDT",
                    value = data.ndtType,
                    onValueChange = { viewModel.updateNdtType(it) }
                )
                FilledTonalButton(onClick = { showNdeForkDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Data NDT Fork")
                }
                Spacer(modifier = Modifier.height(8.dp))
                data.items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("Bagian: ${item.partInspected} (${item.location})", fontWeight = FontWeight.SemiBold)
                                Text("Ada Cacat: ${if(item.finding.status) "Ya" else "Tidak"}")
                                Text("Ket: ${item.finding.result}")
                            }
                        }
                        IconButton(onClick = { viewModel.deleteNdeForkItem(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Hapus Item NDT")
                        }
                    }
                }
            }
        }

        // Load Test Section
        item {
            val data = report.testing.loadTest
            ExpandableSection(title = "PENGUJIAN BEBAN") {
                FilledTonalButton(onClick = { showLoadTestDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Data Uji Beban")
                }
                Spacer(modifier = Modifier.height(8.dp))
                data.items.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("Beban: ${item.testLoad} @ ${item.forkLiftingHeight}", fontWeight = FontWeight.SemiBold)
                                Text("Kecepatan: ${item.travelingSpeed}")
                                Text("Gerakan: ${item.movement}")
                                Text("Hasil: ${item.result}")
                                Text("Ket: ${item.remarks}")
                            }
                        }
                        IconButton(onClick = { viewModel.deleteLoadTestItem(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Hapus Item Uji Beban")
                        }
                    }
                }
            }
        }

        // Conclusion Section
        item {
            val data = report.conclusion
            ExpandableSection(title = "KESIMPULAN") {
                FilledTonalButton(onClick = { showSummaryDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Kesimpulan")
                }
                Spacer(modifier = Modifier.height(8.dp))
                data.summary.forEachIndexed { index, item ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text(item, modifier = Modifier.weight(1f))
                        IconButton(onClick = { viewModel.removeConclusionSummaryItem(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }

        // Recommendations Section
        item {
            val data = report.conclusion
            ExpandableSection(title = "SARAN") {
                FilledTonalButton(onClick = { showRecommendationDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah Poin Saran")
                }
                Spacer(modifier = Modifier.height(8.dp))
                data.recommendations.forEachIndexed { index, item ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Text(item, modifier = Modifier.weight(1f))
                        IconButton(onClick = { viewModel.removeConclusionRecommendationItem(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }

        // Add a spacer at the bottom
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


// Reusable Composables (similar to EskalatorScreen)

@Composable
fun ExpandableSection(
    modifier: Modifier = Modifier,
    title: String,
    initiallyExpanded: Boolean = false,
    content: @Composable () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(initiallyExpanded) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { expanded = !expanded }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
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
    content: @Composable () -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 250,
                    easing = LinearOutSlowInEasing
                )
            )
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
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (expanded) "Collapse" else "Expand"
            )
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
    label: String,
    value: String?,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value.orEmpty(),
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = MaterialTheme.shapes.medium,
    )
}

@Composable
fun ForkliftResultStatusInput(
    label: String,
    value: ForkliftInspectionResult,
    onValueChange: (ForkliftInspectionResult) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            value = value.result,
            onValueChange = { onValueChange(value.copy(result = it)) },
            label = { Text(label) },
            supportingText = { Text("Keterangan") },
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .clickable { onValueChange(value.copy(status = !value.status)) }
                .padding(end = 8.dp)
        ) {
            Checkbox(
                // Di laporan, centang berarti "Memenuhi Syarat".
                // Di data class, 'status = false' bisa kita artikan "Memenuhi Syarat" (tidak ada masalah).
                // Jadi, `checked` adalah kebalikan dari `status`.
                checked = !value.status,
                onCheckedChange = { onValueChange(value.copy(status = !it)) }
            )
            Text("Memenuhi")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Phone View", group = "Phone")
@Composable
private fun ForkliftScreenPreview() {
    KoinApplicationPreview(application = {
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                ForkliftScreen(
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