package com.nakersolutionid.nakersolutionid.features.report.ee.elevator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.report.ee.EEViewModel
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview


@Composable
fun ElevatorScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: EEViewModel = koinViewModel(),
) {
    val uiState by viewModel.elevatorUiState.collectAsStateWithLifecycle()
    val onDataChange: (ElevatorUiState) -> Unit = viewModel::onDataChange

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        item {
            ExpandableSection(title = "Data Utama", initiallyExpanded = true) {
                MainData(uiState = uiState, onDataChange = onDataChange)
            }
        }
        item {
            ExpandableSection(title = "Data Umum") {
                GeneralDataSection(
                    generalData = uiState.generalData,
                    onDataChange = { newGeneralData ->
                        onDataChange(uiState.copy(generalData = newGeneralData))
                    }
                )
            }
        }
        item {
            ExpandableSection(title = "Pemeriksaan Dokumen Teknis") {
                TechnicalDocumentInspectionSection(
                    technicalDocs = uiState.technicalDocumentInspection,
                    onDataChange = { newDocs ->
                        onDataChange(uiState.copy(technicalDocumentInspection = newDocs))
                    }
                )
            }
        }
        item {
            ExpandableSection(title = "Pemeriksaan dan Pengujian") {
                InspectionAndTestingItems(
                    inspectionAndTesting = uiState.inspectionAndTesting,
                    onDataChange = { newInspection ->
                        onDataChange(uiState.copy(inspectionAndTesting = newInspection))
                    }
                )
            }
        }
        item {
            ExpandableSection(title = "Kesimpulan") {
                ConclusionSection(
                    conclusion = uiState.conclusion,
                    onDataChange = { newConclusion ->
                        onDataChange(uiState.copy(conclusion = newConclusion))
                    }
                )
            }
        }
    }
}

// Reusable composable for a collapsible section
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
            AnimatedVisibility(
                visible = expanded
            ) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(16.dp))
                    content()
                }
            }
        }
    }
}

@Composable
private fun ExpandableSubSection(
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
            Column(modifier = Modifier.padding(top = 8.dp)) {
                content()
            }
        }
    }
}


@Composable
fun SectionSubHeader(title: String, style: TextStyle = MaterialTheme.typography.titleMedium) {
    Text(
        text = title,
        style = style,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
    )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = MaterialTheme.shapes.medium,
    )
}

@Composable
fun ResultStatusInput(
    label: String,
    provision: String,
    resultStatus: ResultStatusUiState,
    onValueChange: (ResultStatusUiState) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        OutlinedTextField(
            value = resultStatus.result,
            onValueChange = { onValueChange(resultStatus.copy(result = it)) },
            label = { Text(label) },
            supportingText = { Text(text = "Ketentuan: $provision") },
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 8.dp)
                .clip(MaterialTheme.shapes.small)
                .clickable { onValueChange(resultStatus.copy(status = !resultStatus.status)) }
                .padding(end = 8.dp)
        ) {
            Checkbox(
                checked = resultStatus.status,
                onCheckedChange = { onValueChange(resultStatus.copy(status = it)) }
            )
            Text("Memenuhi")
        }
    }
}

@Composable
fun MainData(uiState: ElevatorUiState, onDataChange: (ElevatorUiState) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        FormTextField(
            "Jenis Elevator",
            uiState.eskOrElevType
        ) { onDataChange(uiState.copy(eskOrElevType = it)) }
        FormTextField("Jenis Pemeriksaan", uiState.typeInspection) {
            onDataChange(uiState.copy(typeInspection = it))
        }
    }
}

@Composable
fun GeneralDataSection(generalData: GeneralDataUiState, onDataChange: (GeneralDataUiState) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        FormTextField("Nama Perusahaan", generalData.ownerName) { onDataChange(generalData.copy(ownerName = it)) }
        FormTextField("Alamat Perusahaan", generalData.ownerAddress) { onDataChange(generalData.copy(ownerAddress = it)) }
        FormTextField("Lokasi Pemakaian", generalData.nameUsageLocation) { onDataChange(generalData.copy(nameUsageLocation = it)) }
        FormTextField("Alamat Pemakaian", generalData.addressUsageLocation) { onDataChange(generalData.copy(addressUsageLocation = it)) }
        FormTextField("Perusahaan Pembuat / Pemasang", generalData.manufacturerOrInstaller) { onDataChange(generalData.copy(manufacturerOrInstaller = it)) }
        FormTextField("Jenis Elevator", generalData.elevatorType) { onDataChange(generalData.copy(elevatorType = it)) }
        FormTextField("Merk / Type", generalData.brandOrType) { onDataChange(generalData.copy(brandOrType = it)) }
        FormTextField("Negara / Tahun Pembuatan", generalData.countryAndYear) { onDataChange(generalData.copy(countryAndYear = it)) }
        FormTextField("No. Seri / No. Unit", generalData.serialNumber) { onDataChange(generalData.copy(serialNumber = it)) }
        FormTextField("Kapasitas Angkut (Orang / Kg)", generalData.capacity) { onDataChange(generalData.copy(capacity = it)) }
        FormTextField("Kecepatan Angkut (m/s)", generalData.speed) { onDataChange(generalData.copy(speed = it)) }
        FormTextField("Melayani (Lantai)", generalData.floorsServed) { onDataChange(generalData.copy(floorsServed = it)) }
        FormTextField("No. Izin Pengesahan", generalData.permitNumber) { onDataChange(generalData.copy(permitNumber = it)) }
        FormTextField("Tanggal Pemeriksaan", generalData.inspectionDate) { onDataChange(generalData.copy(inspectionDate = it)) }
    }
}

@Composable
fun TechnicalDocumentItem(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = { onCheckedChange(!isChecked) })
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Memenuhi",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(end = 4.dp)
            )
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun TechnicalDocumentInspectionSection(
    technicalDocs: TechnicalDocumentInspectionUiState,
    onDataChange: (TechnicalDocumentInspectionUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TechnicalDocumentItem("Gambar Rencana", technicalDocs.designDrawing) { onDataChange(technicalDocs.copy(designDrawing = it)) }
        TechnicalDocumentItem("Perhitungan Teknis", technicalDocs.technicalCalculation) { onDataChange(technicalDocs.copy(technicalCalculation = it)) }
        TechnicalDocumentItem("Sertifikat Bahan", technicalDocs.materialCertificate) { onDataChange(technicalDocs.copy(materialCertificate = it)) }
        TechnicalDocumentItem("Diagram Panel Pengendali", technicalDocs.controlPanelDiagram) { onDataChange(technicalDocs.copy(controlPanelDiagram = it)) }
        TechnicalDocumentItem("Dokumen Gambar Terpasang (As Built Drawing)", technicalDocs.asBuiltDrawing) { onDataChange(technicalDocs.copy(asBuiltDrawing = it)) }
        TechnicalDocumentItem("Sertifikat Bagian-Bagian atau Perlengkapan", technicalDocs.componentCertificates) { onDataChange(technicalDocs.copy(componentCertificates = it)) }
        TechnicalDocumentItem("Prosedur Kerja Aman", technicalDocs.safeWorkProcedure) { onDataChange(technicalDocs.copy(safeWorkProcedure = it)) }
    }
}

@Composable
fun InspectionAndTestingItems(
    inspectionAndTesting: InspectionAndTestingUiState,
    onDataChange: (InspectionAndTestingUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ExpandableSubSection("Mesin") {
            MachineRoomAndMachinerySection(inspectionAndTesting.machineRoomAndMachinery) {
                onDataChange(inspectionAndTesting.copy(machineRoomAndMachinery = it))
            }
        }
        HorizontalDivider()
        ExpandableSubSection("Tali / Sabuk Penggantung") {
            SuspensionRopesAndBeltsSection(inspectionAndTesting.suspensionRopesAndBelts) {
                onDataChange(inspectionAndTesting.copy(suspensionRopesAndBelts = it))
            }
        }
        HorizontalDivider()
        ExpandableSubSection("Teromol") {
            DrumsAndSheavesSection(inspectionAndTesting.drumsAndSheaves) {
                onDataChange(inspectionAndTesting.copy(drumsAndSheaves = it))
            }
        }
        HorizontalDivider()
        ExpandableSubSection("Bangunan Ruang Luncur, Ruang Atas dan Lekuk Dasar") {
            HoistwayAndPitSection(inspectionAndTesting.hoistwayAndPit) {
                onDataChange(inspectionAndTesting.copy(hoistwayAndPit = it))
            }
        }
        HorizontalDivider()
        ExpandableSubSection("Kereta") {
            CarSection(inspectionAndTesting.car) {
                onDataChange(inspectionAndTesting.copy(car = it))
            }
        }
        HorizontalDivider()
        ExpandableSubSection("Governor dan Rem Pengaman Kereta") {
            GovernorAndSafetyBrakeSection(inspectionAndTesting.governorAndSafetyBrake) {
                onDataChange(inspectionAndTesting.copy(governorAndSafetyBrake = it))
            }
        }
        HorizontalDivider()
        ExpandableSubSection("Bobot Imbang, Rel Pemandu, dan Peredam") {
            CounterweightGuideRailsAndBuffersSection(inspectionAndTesting.counterweightGuideRailsAndBuffers) {
                onDataChange(inspectionAndTesting.copy(counterweightGuideRailsAndBuffers = it))
            }
        }
        HorizontalDivider()
        ExpandableSubSection("Instalasi Listrik") {
            ElectricalInstallationSection(inspectionAndTesting.electricalInstallation) {
                onDataChange(inspectionAndTesting.copy(electricalInstallation = it))
            }
        }
    }
}

@Composable
fun MachineRoomAndMachinerySection(
    machineRoom: MachineRoomAndMachineryUiState,
    onDataChange: (MachineRoomAndMachineryUiState) -> Unit
) {
    val roomlessData = machineRoom.machineRoomless
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput("Dudukan mesin", "Kuat", machineRoom.machineMounting) { onDataChange(machineRoom.copy(machineMounting = it)) }
        ResultStatusInput("Rem mekanik", "Ada, berfungsi, baik", machineRoom.mechanicalBrake) { onDataChange(machineRoom.copy(mechanicalBrake = it)) }
        ResultStatusInput("Rem elektrik (brake switch)", "Ada, berfungsi, baik", machineRoom.electricalBrake) { onDataChange(machineRoom.copy(electricalBrake = it)) }
        ResultStatusInput("Konstruksi kamar mesin", "Bebas air, Kuat, Tahan Api", machineRoom.machineRoomConstruction) { onDataChange(machineRoom.copy(machineRoomConstruction = it)) }
        ResultStatusInput("Ruang bebas kamar mesin", "Depan pengendali ≥ 700 mm, Depan brg bergerak ≥ 500x600 mm", machineRoom.machineRoomClearance) { onDataChange(machineRoom.copy(machineRoomClearance = it)) }
        ResultStatusInput("Penerapan kamar mesin", "Area kerja ≥ 100 lux, Di antara area kerja ≥ 50 lux", machineRoom.machineRoomImplementation) { onDataChange(machineRoom.copy(machineRoomImplementation = it)) }
        ResultStatusInput("Ventilasi/pendingin ruangan", "Ada, sesuai spesifikasi", machineRoom.ventilation) { onDataChange(machineRoom.copy(ventilation = it)) }
        ResultStatusInput("Pintu kamar mesin", "Membuka keluar, tahan api, lebar ≥ 75 cm, tinggi 2 Meter", machineRoom.machineRoomDoor) { onDataChange(machineRoom.copy(machineRoomDoor = it)) }
        ResultStatusInput("Posisi panel hubung bagi listrik", "Di kamar mesin", machineRoom.mainPowerPanelPosition) { onDataChange(machineRoom.copy(mainPowerPanelPosition = it)) }
        ResultStatusInput("Alat pelindung benda berputar", "Ada", machineRoom.rotatingPartsGuard) { onDataChange(machineRoom.copy(rotatingPartsGuard = it)) }
        ResultStatusInput("Pelindung lubang tali baja/sabuk penggantung", "Tinggi ≥ 50mm", machineRoom.ropeHoleGuard) { onDataChange(machineRoom.copy(ropeHoleGuard = it)) }
        ResultStatusInput("Tangga menuju kamar mesin", "Permanen, pagar pengaman, tahan api", machineRoom.machineRoomAccessLadder) { onDataChange(machineRoom.copy(machineRoomAccessLadder = it)) }
        ResultStatusInput("Perbedaan ketinggian lantai > 500 mm", "Tersedia tangga dan pagar pengaman", machineRoom.floorLevelDifference) { onDataChange(machineRoom.copy(floorLevelDifference = it)) }
        ResultStatusInput("Tersedia alat pemadam api ringan", "Isi ≥ 5kg", machineRoom.fireExtinguisher) { onDataChange(machineRoom.copy(fireExtinguisher = it)) }
        ResultStatusInput("Terdapat emergency stop switch", "Terpasang di dekat dengan panel kontrol", machineRoom.emergencyStopSwitch) { onDataChange(machineRoom.copy(emergencyStopSwitch = it)) }

        SectionSubHeader("Elevator Tanpa Kamar Mesin (Roomless)", MaterialTheme.typography.titleSmall)
        ResultStatusInput("Penempatan panel kontrol dan PHB listrik", "Di lantai yang sama, jarak tidak lebih dari 5000 mm", roomlessData.panelPlacement) { onDataChange(machineRoom.copy(machineRoomless = roomlessData.copy(panelPlacement = it))) }
        ResultStatusInput("Intensitas cahaya area kerja", "≥ 100 lux", roomlessData.lightingWorkArea) { onDataChange(machineRoom.copy(machineRoomless = roomlessData.copy(lightingWorkArea = it))) }
        ResultStatusInput("Intensitas cahaya diantara area kerja", "≥ 50 lux", roomlessData.lightingBetweenWorkArea) { onDataChange(machineRoom.copy(machineRoomless = roomlessData.copy(lightingBetweenWorkArea = it))) }
        ResultStatusInput("Alat pembuka rem (manual)", "Ada dan terpasang dengan baik", roomlessData.manualBrakeRelease) { onDataChange(machineRoom.copy(machineRoomless = roomlessData.copy(manualBrakeRelease = it))) }
        ResultStatusInput("Penempatan APAR", "Dekat pintu elevator paling atas", roomlessData.fireExtinguisherPlacement) { onDataChange(machineRoom.copy(machineRoomless = roomlessData.copy(fireExtinguisherPlacement = it))) }
    }
}

@Composable
fun SuspensionRopesAndBeltsSection(
    suspension: SuspensionRopesAndBeltsUiState,
    onDataChange: (SuspensionRopesAndBeltsUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput("Kondisi tali/sabuk penggantung", "Tidak memiliki sambungan, kuat, luwes, spesifikasi seragam", suspension.condition) { onDataChange(suspension.copy(condition = it)) }
        ResultStatusInput("Penggunaan Rantai", "Tidak menggunakan rantai", suspension.chainUsage) { onDataChange(suspension.copy(chainUsage = it)) }
        ResultStatusInput("Faktor keamanan tali/sabuk", "Sesuai standar kecepatan (8-12x kapasitas angkut)", suspension.safetyFactor) { onDataChange(suspension.copy(safetyFactor = it)) }
        ResultStatusInput("Tali penggantung (dengan bobot imbang)", "≥ 6mm, ≥ 3 jalur", suspension.ropeWithCounterweight) { onDataChange(suspension.copy(ropeWithCounterweight = it)) }
        ResultStatusInput("Tali penggantung (tanpa bobot imbang)", "≥ 6mm, ≥ 2 jalur", suspension.ropeWithoutCounterweight) { onDataChange(suspension.copy(ropeWithoutCounterweight = it)) }
        ResultStatusInput("Sabuk", "≥ 3 x 30 mm, ≥ 2 jalur", suspension.belt) { onDataChange(suspension.copy(belt = it)) }
        ResultStatusInput("Alat pengaman (tanpa bobot imbang)", "Switch otomatis berfungsi, motor berhenti", suspension.slackRopeDevice) { onDataChange(suspension.copy(slackRopeDevice = it)) }
    }
}

@Composable
fun DrumsAndSheavesSection(
    drums: DrumsAndSheavesUiState,
    onDataChange: (DrumsAndSheavesUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput("Alur teromol", "Ada", drums.drumGrooves) { onDataChange(drums.copy(drumGrooves = it)) }
        ResultStatusInput("Diameter teromol penumpang/barang", "40 : 1", drums.passengerDrumDiameter) { onDataChange(drums.copy(passengerDrumDiameter = it)) }
        ResultStatusInput("Diameter teromol Governor", "25 : 1", drums.governorDrumDiameter) { onDataChange(drums.copy(governorDrumDiameter = it)) }
    }
}

@Composable
fun HoistwayAndPitSection(
    hoistway: HoistwayAndPitUiState,
    onDataChange: (HoistwayAndPitUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput("Konstruksi", "Kuat, kokoh, tahan api, dan tertutup rapat", hoistway.construction) { onDataChange(hoistway.copy(construction = it)) }
        ResultStatusInput("Dinding", "Dapat dilalui orang dengan tinggi ≥ 2000 mm", hoistway.walls) { onDataChange(hoistway.copy(walls = it)) }
        ResultStatusInput("Landasan jalur (elevator miring)", "Kuat dan tahan cuaca", hoistway.inclinedElevatorTrackBed) { onDataChange(hoistway.copy(inclinedElevatorTrackBed = it)) }
        ResultStatusInput("Kebersihan", "Bersih, bebas dari instalasi dan peralatan lain", hoistway.cleanliness) { onDataChange(hoistway.copy(cleanliness = it)) }
        ResultStatusInput("Penerangan", "≥ 100 lux", hoistway.lighting) { onDataChange(hoistway.copy(lighting = it)) }
        ResultStatusInput("Pintu darurat (non stop)", "Jarak maks 1100 mm, tinggi ambang maks 300 mm", hoistway.emergencyDoorNonStop) { onDataChange(hoistway.copy(emergencyDoorNonStop = it)) }
        ResultStatusInput("Ukuran pintu darurat", "Lebar 700 mm, tinggi 1400 mm, membuka keluar", hoistway.emergencyDoorSize) { onDataChange(hoistway.copy(emergencyDoorSize = it)) }
        ResultStatusInput("Saklar pengaman pintu darurat", "Tersedia", hoistway.emergencyDoorSafetySwitch) { onDataChange(hoistway.copy(emergencyDoorSafetySwitch = it)) }
        ResultStatusInput("Jembatan bantu dari pintu darurat", "Tersedia, lebar ≥ 500 mm dan berpagar", hoistway.emergencyDoorBridge) { onDataChange(hoistway.copy(emergencyDoorBridge = it)) }
        ResultStatusInput("Ruang bebas diatas sangkar", "≥ 500 mm", hoistway.carTopClearance) { onDataChange(hoistway.copy(carTopClearance = it)) }
        ResultStatusInput("Ruang bebas lekuk dasar (pit)", "≥ 500 mm (rumah tinggal ≥ 300 mm)", hoistway.pitClearance) { onDataChange(hoistway.copy(pitClearance = it)) }
        ResultStatusInput("Tangga lekuk dasar", "Tersedia mulai dari kedalaman 1000 mm", hoistway.pitLadder) { onDataChange(hoistway.copy(pitLadder = it)) }
        ResultStatusInput("Syarat pit (bukan di atas tanah)", "Kekuatan lantai ≥ 500 N/m², tersedia rem pengaman", hoistway.pitBelowWorkingArea) { onDataChange(hoistway.copy(pitBelowWorkingArea = it)) }
        ResultStatusInput("Akses menuju lekuk dasar", "Tersedia saklar pengaman (tinggi 1500mm, 500mm dari lantai pit)", hoistway.pitAccessSwitch) { onDataChange(hoistway.copy(pitAccessSwitch = it)) }
        ResultStatusInput("Lekuk dasar antara 2 elevator", "Tersedia pit screen (tinggi 300mm dari dasar pit s/d 3000mm keatas)", hoistway.pitScreen) { onDataChange(hoistway.copy(pitScreen = it)) }
        ResultStatusInput("Daun pintu ruang luncur", "Tahan api ≥ 1 jam, menutup rapat", hoistway.hoistwayDoorLeaf) { onDataChange(hoistway.copy(hoistwayDoorLeaf = it)) }
        ResultStatusInput("Interlock/kunci kait pintu", "Tersedia, menutup rapat, pintu terbuka hanya di zona henti", hoistway.hoistwayDoorInterlock) { onDataChange(hoistway.copy(hoistwayDoorInterlock = it)) }
        ResultStatusInput("Kerataan lantai", "< 10 mm", hoistway.floorLeveling) { onDataChange(hoistway.copy(floorLeveling = it)) }
        ResultStatusInput("Sekat ruang luncur (2 sangkar)", "> 500 mm", hoistway.hoistwaySeparatorBeam) { onDataChange(hoistway.copy(hoistwaySeparatorBeam = it)) }
        ResultStatusInput("Elevator miring", "Dipasang tangga sepanjang rel", hoistway.inclinedElevatorStairs) { onDataChange(hoistway.copy(inclinedElevatorStairs = it)) }
    }
}

@Composable
fun CarSection(
    car: CarUiState,
    onDataChange: (CarUiState) -> Unit
) {
    val carDoorSpecs = car.carDoorSpecs
    val carSignage = car.carSignage
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput("Kerangka", "Dari baja dan kuat", car.frame) { onDataChange(car.copy(frame = it)) }
        ResultStatusInput("Badan Kereta", "Tertutup dan ada pintu", car.body) { onDataChange(car.copy(body = it)) }
        ResultStatusInput("Tinggi dinding", "≥ 2000 mm", car.wallHeight) { onDataChange(car.copy(wallHeight = it)) }
        ResultStatusInput("Luas lantai", "Sesuai jumlah penumpang", car.floorArea) { onDataChange(car.copy(floorArea = it)) }
        ResultStatusInput("Perluasan luas kereta", "Pasien max 6%, Barang max 14%", car.carAreaExpansion) { onDataChange(car.copy(carAreaExpansion = it)) }
        ResultStatusInput("Pintu kereta", "Kokoh, aman, otomatis", car.carDoor) { onDataChange(car.copy(carDoor = it)) }

        SectionSubHeader("Syarat Pintu Kereta", MaterialTheme.typography.titleSmall)
        ResultStatusInput("Ukuran", "≥ 700 x 2000 mm", carDoorSpecs.size) { onDataChange(car.copy(carDoorSpecs = carDoorSpecs.copy(size = it))) }
        ResultStatusInput("Kunci kait dan saklar pengaman", "Ada", carDoorSpecs.lockAndSwitch) { onDataChange(car.copy(carDoorSpecs = carDoorSpecs.copy(lockAndSwitch = it))) }
        ResultStatusInput("Celah antar ambang pintu", "28 ≤ celah ≤ 32 mm", carDoorSpecs.sillClearance) { onDataChange(car.copy(carDoorSpecs = carDoorSpecs.copy(sillClearance = it))) }

        SectionSubHeader("Kelengkapan Kereta", MaterialTheme.typography.titleSmall)
        ResultStatusInput("Sisi luar kereta dg balok (2 kereta)", "≥ 250 mm", car.carToBeamClearance) { onDataChange(car.copy(carToBeamClearance = it)) }
        ResultStatusInput("Alarm Bell", "Tersedia", car.alarmBell) { onDataChange(car.copy(alarmBell = it)) }
        ResultStatusInput("Sumber tenaga cadangan (ARD)", "Tersedia", car.backupPowerARD) { onDataChange(car.copy(backupPowerARD = it)) }
        ResultStatusInput("Intercom", "Tersedia", car.intercom) { onDataChange(car.copy(intercom = it)) }
        ResultStatusInput("Ventilasi", "Tersedia", car.ventilation) { onDataChange(car.copy(ventilation = it)) }
        ResultStatusInput("Penerangan darurat", "Tersedia", car.emergencyLighting) { onDataChange(car.copy(emergencyLighting = it)) }
        ResultStatusInput("Panel operasi", "Tersedia", car.operatingPanel) { onDataChange(car.copy(operatingPanel = it)) }
        ResultStatusInput("Petunjuk posisi sangkar", "Tersedia", car.carPositionIndicator) { onDataChange(car.copy(carPositionIndicator = it)) }

        SectionSubHeader("Keterangan Dalam Kereta (Signage)", MaterialTheme.typography.titleSmall)
        ResultStatusInput("Nama Pembuat", "Tersedia", carSignage.manufacturerName) { onDataChange(car.copy(carSignage = carSignage.copy(manufacturerName = it))) }
        ResultStatusInput("Kapasitas Beban", "Tersedia", carSignage.loadCapacity) { onDataChange(car.copy(carSignage = carSignage.copy(loadCapacity = it))) }
        ResultStatusInput("Rambu dilarang merokok", "Tersedia", carSignage.noSmokingSign) { onDataChange(car.copy(carSignage = carSignage.copy(noSmokingSign = it))) }
        ResultStatusInput("Indikasi beban lebih", "Tersedia", carSignage.overloadIndicator) { onDataChange(car.copy(carSignage = carSignage.copy(overloadIndicator = it))) }
        ResultStatusInput("Tombol buka dan tutup", "Tersedia", carSignage.doorOpenCloseButtons) { onDataChange(car.copy(carSignage = carSignage.copy(doorOpenCloseButtons = it))) }
        ResultStatusInput("Tombol lantai pemberhentian", "Tersedia", carSignage.floorButtons) { onDataChange(car.copy(carSignage = carSignage.copy(floorButtons = it))) }
        ResultStatusInput("Tombol bell alarm", "Tersedia", carSignage.alarmButton) { onDataChange(car.copy(carSignage = carSignage.copy(alarmButton = it))) }
        ResultStatusInput("Intercom dua arah", "Tersedia", carSignage.twoWayIntercom) { onDataChange(car.copy(carSignage = carSignage.copy(twoWayIntercom = it))) }

        SectionSubHeader("Atap Kereta dan Interior", MaterialTheme.typography.titleSmall)
        ResultStatusInput("Kekuatan atap kereta", "≥ 200 kg", car.carRoofStrength) { onDataChange(car.copy(carRoofStrength = it)) }
        ResultStatusInput("Pintu darurat atap kereta", "Berengsel, saklar pengaman, buka dari luar, ukuran ≥ 350x450mm", car.carTopEmergencyExit) { onDataChange(car.copy(carTopEmergencyExit = it)) }
        ResultStatusInput("Pintu darurat samping kereta", "Berengsel, buka dari luar, saklar pengaman, ukuran ≥ 350x1800mm", car.carSideEmergencyExit) { onDataChange(car.copy(carSideEmergencyExit = it)) }
        ResultStatusInput("Pagar pengaman atap kereta", "Warna kuning, kekuatan ≥ 90 kg", car.carTopGuardRail) { onDataChange(car.copy(carTopGuardRail = it)) }
        ResultStatusInput("Ukuran pagar (celah 300-850mm)", "Tinggi ≥ 700 mm", car.guardRailHeight300to850) { onDataChange(car.copy(guardRailHeight300to850 = it)) }
        ResultStatusInput("Ukuran pagar (celah >850mm)", "Tinggi ≥ 1100 mm", car.guardRailHeightOver850) { onDataChange(car.copy(guardRailHeightOver850 = it)) }
        ResultStatusInput("Penerangan atap kereta", "≥ 100 lux dengan kabel lentur 2 meter", car.carTopLighting) { onDataChange(car.copy(carTopLighting = it)) }
        ResultStatusInput("Tombol operasi Manual", "Permanen dengan tombol utama", car.manualOperationButtons) { onDataChange(car.copy(manualOperationButtons = it)) }
        ResultStatusInput("Interior Kereta", "Bahan tidak mudah pecah/membahayakan", car.carInterior) { onDataChange(car.copy(carInterior = it)) }
    }
}

@Composable
fun GovernorAndSafetyBrakeSection(
    gsb: GovernorAndSafetyBrakeUiState,
    onDataChange: (GovernorAndSafetyBrakeUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput("Penjepit Tali / Sabuk Governor", "Bekerja", gsb.governorRopeClamp) { onDataChange(gsb.copy(governorRopeClamp = it)) }
        ResultStatusInput("Saklar Governor", "Berfungsi", gsb.governorSwitch) { onDataChange(gsb.copy(governorSwitch = it)) }
        ResultStatusInput("Fungsi Kecepatan Rem Pengaman", "115% - 140%, berhenti bertahap", gsb.safetyBrakeSpeed) { onDataChange(gsb.copy(safetyBrakeSpeed = it)) }
        ResultStatusInput("Rem Pengaman", "Dipasang pada sangkar, berfungsi bertahap/berangsur/mendadak", gsb.safetyBrakeType) { onDataChange(gsb.copy(safetyBrakeType = it)) }
        ResultStatusInput("Bentuk Rem Pengaman", "Tidak boleh sistem elektris, hidrolik, atau pneumatik", gsb.safetyBrakeMechanism) { onDataChange(gsb.copy(safetyBrakeMechanism = it)) }
        ResultStatusInput("Rem Pengaman Berangsur", "> 60 m/menit", gsb.progressiveSafetyBrake) { onDataChange(gsb.copy(progressiveSafetyBrake = it)) }
        ResultStatusInput("Rem Pengaman Mendadak", "< 60 m/menit", gsb.instantaneousSafetyBrake) { onDataChange(gsb.copy(instantaneousSafetyBrake = it)) }
        ResultStatusInput("Syarat Rem Pengaman", "Bekerja ke bawah, bekerja serempak", gsb.safetyBrakeOperation) { onDataChange(gsb.copy(safetyBrakeOperation = it)) }
        ResultStatusInput("Kecepatan Kereta >= 60m/menit", "Ada pemutus elektrik", gsb.electricalCutoutSwitch) { onDataChange(gsb.copy(electricalCutoutSwitch = it)) }
        ResultStatusInput("Saklar Pengaman Lintas Batas", "Berfungsi", gsb.limitSwitch) { onDataChange(gsb.copy(limitSwitch = it)) }
        ResultStatusInput("Alat Pembatas Beban", "Berfungsi", gsb.overloadDevice) { onDataChange(gsb.copy(overloadDevice = it)) }
    }
}

@Composable
fun CounterweightGuideRailsAndBuffersSection(
    cgb: CounterweightGuideRailsAndBuffersUiState,
    onDataChange: (CounterweightGuideRailsAndBuffersUiState) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput("Bahan Bobot Imbang", "Beton/steel block", cgb.counterweightMaterial) { onDataChange(cgb.copy(counterweightMaterial = it)) }
        ResultStatusInput("Sekat Pengaman Bobot Imbang", "Tinggi 2500 mm, > 300mm dari dasar pit", cgb.counterweightGuardScreen) { onDataChange(cgb.copy(counterweightGuardScreen = it)) }
        ResultStatusInput("Konstruksi Rel Pemandu", "Kuat memandu jalan, menahan tekanan saat rem bekerja", cgb.guideRailConstruction) { onDataChange(cgb.copy(guideRailConstruction = it)) }
        ResultStatusInput("Jenis Peredam", "Massif kenyal / pegas / hidrolik", cgb.bufferType) { onDataChange(cgb.copy(bufferType = it)) }
        ResultStatusInput("Fungsi Peredam", "Meredam secara bertahap", cgb.bufferFunction) { onDataChange(cgb.copy(bufferFunction = it)) }
        ResultStatusInput("Saklar Pengaman (kecepatan >= 90m/menit)", "Tersedia", cgb.bufferSafetySwitch) { onDataChange(cgb.copy(bufferSafetySwitch = it)) }
    }
}

@Composable
fun ElectricalInstallationSection(
    electrical: ElectricalInstallationUiState,
    onDataChange: (ElectricalInstallationUiState) -> Unit
) {
    val fireService = electrical.fireServiceElevator
    val accessibility = electrical.accessibilityElevator
    val seismic = electrical.seismicSensor
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput("Standar Rangkaian Instalasi Listrik", "SNI dan standar Internasional", electrical.installationStandard) { onDataChange(electrical.copy(installationStandard = it)) }
        ResultStatusInput("Panel Listrik", "Panel khusus untuk elevator", electrical.electricalPanel) { onDataChange(electrical.copy(electricalPanel = it)) }
        ResultStatusInput("Catu Daya Pengganti (ARD)", "Tersedia", electrical.backupPowerARD) { onDataChange(electrical.copy(backupPowerARD = it)) }
        ResultStatusInput("Kabel Grounding", "Penampang ≥ 10 mm², Tahanan ≤ 5 Ω (ohm)", electrical.groundingCable) { onDataChange(electrical.copy(groundingCable = it)) }
        ResultStatusInput("Alarm Kebakaran", "Terhubung dan beroperasi otomatis", electrical.fireAlarmConnection) { onDataChange(electrical.copy(fireAlarmConnection = it)) }

        SectionSubHeader("Elevator Penanggulangan Kebakaran", MaterialTheme.typography.titleSmall)
        ResultStatusInput("Catu Daya Cadangan", "Tersedia", fireService.backupPower) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(backupPower = it))) }
        ResultStatusInput("Pengoperasian Khusus", "Manual, dapat berhenti tiap lantai", fireService.specialOperation) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(specialOperation = it))) }
        ResultStatusInput("Saklar Kebakaran", "Di lantai evakuasi, dapat dioperasikan manual", fireService.fireSwitch) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(fireSwitch = it))) }
        ResultStatusInput("Label 'Elevator Penanggulangan Kebakaran'", "Tersedia", fireService.label) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(label = it))) }
        ResultStatusInput("Ketahanan Instalasi Listrik Terhadap Api", "≥ 2 jam", fireService.electricalFireResistance) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(electricalFireResistance = it))) }
        ResultStatusInput("Dinding Luncur", "Tertutup rapat, tahan api ≥ 1 jam", fireService.hoistwayWallFireResistance) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(hoistwayWallFireResistance = it))) }
        ResultStatusInput("Ukuran Sangkar", "≥ 1100x1400 mm, kapasitas ≥ 630 kg", fireService.carSize) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(carSize = it))) }
        ResultStatusInput("Ukuran Pintu Kereta", "≥ 800 x 2100 mm", fireService.doorSize) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(doorSize = it))) }
        ResultStatusInput("Waktu Tempuh", "≤ 60 detik", fireService.travelTime) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(travelTime = it))) }
        ResultStatusInput("Lantai Evakuasi", "Tidak boleh ada penghalang", fireService.evacuationFloor) { onDataChange(electrical.copy(fireServiceElevator = fireService.copy(evacuationFloor = it))) }

        SectionSubHeader("Elevator Untuk Disabilitas", MaterialTheme.typography.titleSmall)
        ResultStatusInput("Panel Operasi", "Huruf braille", accessibility.operatingPanel) { onDataChange(electrical.copy(accessibilityElevator = accessibility.copy(operatingPanel = it))) }
        ResultStatusInput("Tinggi Panel Operasi", "900 mm ≤ tinggi ≤ 1100 mm", accessibility.panelHeight) { onDataChange(electrical.copy(accessibilityElevator = accessibility.copy(panelHeight = it))) }
        ResultStatusInput("Waktu Bukaan Pintu", "≥ 2 menit", accessibility.doorOpenTime) { onDataChange(electrical.copy(accessibilityElevator = accessibility.copy(doorOpenTime = it))) }
        ResultStatusInput("Lebar Bukaan Pintu", "≥ 1000 mm", accessibility.doorWidth) { onDataChange(electrical.copy(accessibilityElevator = accessibility.copy(doorWidth = it))) }
        ResultStatusInput("Informasi Operasi", "Bersuara", accessibility.audioInformation) { onDataChange(electrical.copy(accessibilityElevator = accessibility.copy(audioInformation = it))) }
        ResultStatusInput("Label 'Elevator Disabilitas'", "Tersedia", accessibility.label) { onDataChange(electrical.copy(accessibilityElevator = accessibility.copy(label = it))) }

        SectionSubHeader("Sensor Gempa", MaterialTheme.typography.titleSmall)
        ResultStatusInput("Ketersediaan (> 10 Lt / 40 m)", "Tersedia sensor gempa", seismic.availability) { onDataChange(electrical.copy(seismicSensor = seismic.copy(availability = it))) }
        ResultStatusInput("Fungsi Input Sinyal Sensor Gempa", "Berhenti lantai terdekat, pintu terbuka, tidak dapat dioperasikan", seismic.function) { onDataChange(electrical.copy(seismicSensor = seismic.copy(function = it))) }
    }
}

@Composable
fun ConclusionSection(conclusion: String, onDataChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        OutlinedTextField(
            value = conclusion,
            onValueChange = onDataChange,
            label = { Text("Kesimpulan Hasil Pemeriksaan & Pengujian") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = MaterialTheme.shapes.medium,
        )
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "Phone View", group = "Phone")
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone Landscape",
    device = "spec:width=891dp,height=411dp,orientation=landscape",
    group = "Phone"
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tablet View",
    device = Devices.TABLET,
    group = "Table"
)
@Composable
fun ElevatorScreenPreview() {
    KoinApplicationPreview(application = {
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                ElevatorScreen(
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