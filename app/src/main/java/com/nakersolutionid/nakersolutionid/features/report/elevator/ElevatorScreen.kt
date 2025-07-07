package com.nakersolutionid.nakersolutionid.features.report.elevator

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
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.report.ReportViewModel
import com.nakersolutionid.nakersolutionid.ui.components.ElevatorTopAppBar
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ElevatorScreen(
    modifier: Modifier = Modifier,
    viewModel: ReportViewModel = koinViewModel(),
    menuTitle: String,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val options = listOf(SubInspectionType.Elevator, SubInspectionType.Eskalator)
    var selectedIndex by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.onNameOfInspectionTypeChange(InspectionType.EE)

    LaunchedEffect(uiState.sendReportResult) {
        when (val result = uiState.sendReportResult) {
            is Resource.Success -> {
                viewModel.toggleLoading(false)
                viewModel.onStateHandledSuccess()
                onBackClick()
            }

            is Resource.Error -> {
                val errorMessage = result.message ?: "An unknown error occurred"
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                viewModel.toggleLoading(false)
                viewModel.onStateHandledFailed()
            }

            is Resource.Loading -> viewModel.toggleLoading(true)
            else -> { /* Do nothing for Initial state */
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                ElevatorTopAppBar(
                    name = menuTitle,
                    scrollBehavior = scrollBehavior,
                    onBackClick = { onBackClick() },
                    actionEnable = !uiState.isLoading,
                    onSaveClick = { viewModel.onSaveClick() }
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                ) {
                    options.forEachIndexed { index, label ->
                        ToggleButton(
                            checked = selectedIndex == index,
                            onCheckedChange = { selectedIndex = index },
                            modifier = Modifier
                                .weight(1f)
                                .semantics { role = Role.RadioButton },
                            shapes = when (index) {
                                0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                            },
                        ) {
                            Text(label.name)
                        }
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (selectedIndex == 0) {
                viewModel.onSubNameOfInspectionTypeChange(options[selectedIndex])

                item {
                    ExpandableSection(title = "Data Utama", initiallyExpanded = true) {
                        MainData(uiState = uiState, viewModel = viewModel)
                    }
                }
                item {
                    ExpandableSection(title = "Data Umum") {
                        GeneralDataSection(generalData = uiState.generalData, viewModel = viewModel)
                    }
                }
                item {
                    ExpandableSection(title = "Pemeriksaan Dokumen Teknis") {
                        TechnicalDocumentInspectionSection(
                            technicalDocs = uiState.technicalDocumentInspection,
                            viewModel = viewModel
                        )
                    }
                }
                item {
                    ExpandableSection(title = "Pemeriksaan dan Pengujian") {
                        InspectionAndTestingItems(
                            inspectionAndTesting = uiState.inspectionAndTesting,
                            viewModel = viewModel
                        )
                    }
                }
                item {
                    ExpandableSection(title = "Kesimpulan") {
                        ConclusionSection(conclusion = uiState.conclusion, viewModel = viewModel)
                    }
                }
            }
            // Add content for "Eskalator" (selectedIndex == 1) here if needed
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

/**
 * An expandable subsection for use within another card. It's lightweight and
 * doesn't use a Card for its background to prevent nested elevation issues.
 * This is key to making the "InspectionEntity and Testing" section performant.
 */
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
            ) // Faster animation
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
    resultStatus: ResultStatusUiState?,
    onValueChange: (String?, Boolean?) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        OutlinedTextField(
            value = resultStatus?.result.orEmpty(),
            onValueChange = { onValueChange(it, resultStatus?.status) },
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
                .clickable {
                    onValueChange(
                        resultStatus?.result,
                        resultStatus?.status?.not() ?: true
                    )
                }
                .padding(end = 8.dp)
        ) {
            Checkbox(
                checked = resultStatus?.status == true,
                onCheckedChange = { onValueChange(resultStatus?.result, it) }
            )
            Text("Memenuhi")
        }
    }
}

@Composable
fun MainData(uiState: ElevatorUiState?, viewModel: ReportViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        FormTextField("Tipe Inspeksi", uiState?.typeInspection) {
            viewModel.onTypeInspectionChange(
                it
            )
        }
        FormTextField(
            "Tipe Elevator",
            uiState?.eskOrElevType
        ) { viewModel.onEskOrElevTypeChange(it) }
    }
}

@Composable
fun GeneralDataSection(generalData: GeneralDataUiState?, viewModel: ReportViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        FormTextField("Nama Perusahaan", generalData?.ownerName) { viewModel.onOwnerNameChange(it) }
        FormTextField(
            "Alamat Perusahaan",
            generalData?.ownerAddress
        ) { viewModel.onOwnerAddressChange(it) }
        FormTextField(
            "Lokasi Pemakaian",
            generalData?.nameUsageLocation
        ) { viewModel.onNameUsageLocationChange(it) }
        FormTextField(
            "Alamat Pemakaian",
            generalData?.addressUsageLocation
        ) { viewModel.onAddressUsageLocationChange(it) }
        FormTextField(
            "Perusahaan Pembuat / Pemasang",
            generalData?.manufacturerOrInstaller
        ) { viewModel.onManufacturerOrInstallerChange(it) }
        FormTextField("Jenis Elevator", generalData?.elevatorType) {
            viewModel.onElevatorTypeChange(
                it
            )
        }
        FormTextField("Merk / Type", generalData?.brandOrType) { viewModel.onBrandOrTypeChange(it) }
        FormTextField(
            "Negara / Tahun Pembuatan",
            generalData?.countryAndYear
        ) { viewModel.onCountryAndYearChange(it) }
        FormTextField(
            "No. Seri / No. Unit",
            generalData?.serialNumber
        ) { viewModel.onSerialNumberChange(it) }
        FormTextField(
            "Kapasitas Angkut (Orang / Kg)",
            generalData?.capacity
        ) { viewModel.onCapacityChange(it) }
        FormTextField("Kecepatan Angkut (m/s)", generalData?.speed) { viewModel.onSpeedChange(it) }
        FormTextField(
            "Melayani (Lantai)",
            generalData?.floorsServed
        ) { viewModel.onFloorsServedChange(it) }
        FormTextField(
            "No. Izin Pengesahan",
            generalData?.permitNumber
        ) { viewModel.onPermitNumberChange(it) }
        FormTextField(
            "Tanggal Pemeriksaan",
            generalData?.inspectionDate
        ) { viewModel.onInspectionDateChange(it) }
    }
}

@Composable
fun TechnicalDocumentInspectionSection(
    technicalDocs: TechnicalDocumentInspectionUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { // Increased spacing for better separation
        // Moved "Memenuhi" text to be contextually relevant or removed if implied
        // Text("Memenuhi", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())

        // Use a helper composable for each item for consistency
        TechnicalDocumentItem(
            label = "Gambar Rencana",
            isChecked = technicalDocs?.designDrawing ?: false,
            onCheckedChange = { viewModel.onDesignDrawingChange(it) }
        )
        TechnicalDocumentItem(
            label = "Perhitungan Teknis",
            isChecked = technicalDocs?.technicalCalculation ?: false,
            onCheckedChange = { viewModel.onTechnicalCalculationChange(it) }
        )
        TechnicalDocumentItem(
            label = "Sertifikat Bahan",
            isChecked = technicalDocs?.materialCertificate ?: false,
            onCheckedChange = { viewModel.onMaterialCertificateChange(it) }
        )
        TechnicalDocumentItem(
            label = "Diagram Panel Pengendali",
            isChecked = technicalDocs?.controlPanelDiagram ?: false,
            onCheckedChange = { viewModel.onControlPanelDiagramChange(it) }
        )
        TechnicalDocumentItem(
            label = "Dokumen Gambar Terpasang (As Built Drawing)",
            isChecked = technicalDocs?.asBuiltDrawing ?: false,
            onCheckedChange = { viewModel.onAsBuiltDrawingChange(it) }
        )
        TechnicalDocumentItem(
            label = "Sertifikat Bagian-Bagian atau Perlengkapan",
            isChecked = technicalDocs?.componentCertificates ?: false,
            onCheckedChange = { viewModel.onComponentCertificatesChange(it) }
        )
        TechnicalDocumentItem(
            label = "Prosedur Kerja Aman",
            isChecked = technicalDocs?.safeWorkProcedure ?: false,
            onCheckedChange = { viewModel.onSafeWorkProcedureChange(it) }
        )
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
        // Arrangement.SpaceBetween here pushes the checkbox to the far right.
        // We'll adjust to have the "Memenuhi" label and Checkbox together.
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        // Spacer to push the following elements to the right
        Spacer(modifier = Modifier.width(8.dp))

        // Row to group Checkbox and "Memenuhi" label
        Row(
            verticalAlignment = Alignment.CenterVertically,
            // Use default arrangement to keep "Memenuhi" and checkbox together
        ) {
            Text(
                text = "Memenuhi",
                style = MaterialTheme.typography.bodySmall, // Smaller text for the label
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
fun InspectionAndTestingItems(
    inspectionAndTesting: InspectionAndTestingUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ExpandableSubSection("Mesin") {
            MachineRoomAndMachinerySection(inspectionAndTesting?.machineRoomAndMachinery, viewModel)
        }
        HorizontalDivider()
        ExpandableSubSection("Tali / Sabuk Penggantung") {
            SuspensionRopesAndBeltsSection(inspectionAndTesting?.suspensionRopesAndBelts, viewModel)
        }
        HorizontalDivider()
        ExpandableSubSection("Teromol") {
            DrumsAndSheavesSection(inspectionAndTesting?.drumsAndSheaves, viewModel)
        }
        HorizontalDivider()
        ExpandableSubSection("Bangunan Ruang Luncur, Ruang Atas dan Lekuk Dasar") {
            HoistwayAndPitSection(inspectionAndTesting?.hoistwayAndPit, viewModel)
        }
        HorizontalDivider()
        ExpandableSubSection("Kereta") {
            CarSection(inspectionAndTesting?.car, viewModel)
        }
        HorizontalDivider()
        ExpandableSubSection("Governor dan Rem Pengaman Kereta") {
            GovernorAndSafetyBrakeSection(inspectionAndTesting?.governorAndSafetyBrake, viewModel)
        }
        HorizontalDivider()
        ExpandableSubSection("Bobot Imbang, Rel Pemandu, dan Peredam") {
            CounterweightGuideRailsAndBuffersSection(
                inspectionAndTesting?.counterweightGuideRailsAndBuffers,
                viewModel
            )
        }
        HorizontalDivider()
        ExpandableSubSection("Instalasi Listrik") {
            ElectricalInstallationSection(inspectionAndTesting?.electricalInstallation, viewModel)
        }
    }
}

@Composable
fun MachineRoomAndMachinerySection(
    machineRoom: MachineRoomAndMachineryUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput(
            "Dudukan mesin",
            "Kuat",
            machineRoom?.machineMounting
        ) { r, s -> viewModel.onMachineMountingChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Rem mekanik",
            "Ada, berfungsi, baik",
            machineRoom?.mechanicalBrake
        ) { r, s -> viewModel.onMechanicalBrakeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Rem elektrik (brake switch)",
            "Ada, berfungsi, baik",
            machineRoom?.electricalBrake
        ) { r, s -> viewModel.onElectricalBrakeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Konstruksi kamar mesin",
            "Bebas air, Kuat, Tahan Api",
            machineRoom?.machineRoomConstruction
        ) { r, s -> viewModel.onMachineRoomConstructionChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ruang bebas kamar mesin",
            "Depan pengendali ≥ 700 mm, Depan brg bergerak ≥ 500x600 mm",
            machineRoom?.machineRoomClearance
        ) { r, s -> viewModel.onMachineRoomClearanceChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Penerapan kamar mesin",
            "Area kerja ≥ 100 lux, Di antara area kerja ≥ 50 lux",
            machineRoom?.machineRoomImplementation
        ) { r, s -> viewModel.onMachineRoomImplementationChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ventilasi/pendingin ruangan",
            "Ada, sesuai spesifikasi",
            machineRoom?.ventilation
        ) { r, s -> viewModel.onMachineRoomVentilationChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Pintu kamar mesin",
            "Membuka keluar, tahan api, lebar ≥ 75 cm, tinggi 2 Meter",
            machineRoom?.machineRoomDoor
        ) { r, s -> viewModel.onMachineRoomDoorChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Posisi panel hubung bagi listrik",
            "Di kamar mesin",
            machineRoom?.mainPowerPanelPosition
        ) { r, s -> viewModel.onMainPowerPanelPositionChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Alat pelindung benda berputar",
            "Ada",
            machineRoom?.rotatingPartsGuard
        ) { r, s -> viewModel.onRotatingPartsGuardChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Pelindung lubang tali baja/sabuk penggantung",
            "Tinggi ≥ 50mm",
            machineRoom?.ropeHoleGuard
        ) { r, s -> viewModel.onRopeHoleGuardChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tangga menuju kamar mesin",
            "Permanen, pagar pengaman, tahan api",
            machineRoom?.machineRoomAccessLadder
        ) { r, s -> viewModel.onMachineRoomAccessLadderChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Perbedaan ketinggian lantai > 500 mm",
            "Tersedia tangga dan pagar pengaman",
            machineRoom?.floorLevelDifference
        ) { r, s -> viewModel.onFloorLevelDifferenceChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tersedia alat pemadam api ringan",
            "Isi ≥ 5kg",
            machineRoom?.fireExtinguisher
        ) { r, s -> viewModel.onMachineRoomFireExtinguisherChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Terdapat emergency stop switch",
            "Terpasang di dekat dengan panel kontrol",
            machineRoom?.emergencyStopSwitch
        ) { r, s -> viewModel.onMachineRoomEmergencyStopSwitchChange(r, s) }

        SectionSubHeader(
            title = "Elevator Tanpa Kamar Mesin (Roomless)",
            style = MaterialTheme.typography.titleSmall
        )
        ResultStatusInput(
            "Penempatan panel kontrol dan PHB listrik",
            "Di lantai yang sama, jarak tidak lebih dari 5000 mm",
            machineRoom?.machineRoomless?.panelPlacement
        ) { r, s -> viewModel.onPanelPlacementChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Intensitas cahaya area kerja",
            "≥ 100 lux",
            machineRoom?.machineRoomless?.lightingWorkArea
        ) { r, s -> viewModel.onLightingWorkAreaChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Intensitas cahaya diantara area kerja",
            "≥ 50 lux",
            machineRoom?.machineRoomless?.lightingBetweenWorkArea
        ) { r, s -> viewModel.onLightingBetweenWorkAreaChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Alat pembuka rem (manual)",
            "Ada dan terpasang dengan baik",
            machineRoom?.machineRoomless?.manualBrakeRelease
        ) { r, s -> viewModel.onManualBrakeReleaseChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Penempatan APAR",
            "Dekat pintu elevator paling atas",
            machineRoom?.machineRoomless?.fireExtinguisherPlacement
        ) { r, s -> viewModel.onFireExtinguisherPlacementChange(r, s) }
    }
}

@Composable
fun SuspensionRopesAndBeltsSection(
    suspension: SuspensionRopesAndBeltsUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput(
            "Kondisi tali/sabuk penggantung",
            "Tidak memiliki sambungan, kuat, luwes, spesifikasi seragam",
            suspension?.condition
        ) { r, s -> viewModel.onSuspensionRopeConditionChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Penggunaan Rantai",
            "Tidak menggunakan rantai",
            suspension?.chainUsage
        ) { r, s -> viewModel.onChainUsageChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Faktor keamanan tali/sabuk",
            "Sesuai standar kecepatan (8-12x kapasitas angkut)",
            suspension?.safetyFactor
        ) { r, s -> viewModel.onSafetyFactorChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tali penggantung (dengan bobot imbang)",
            "≥ 6mm, ≥ 3 jalur",
            suspension?.ropeWithCounterweight
        ) { r, s -> viewModel.onRopeWithCounterweightChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tali penggantung (tanpa bobot imbang)",
            "≥ 6mm, ≥ 2 jalur",
            suspension?.ropeWithoutCounterweight
        ) { r, s -> viewModel.onRopeWithoutCounterweightChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Sabuk",
            "≥ 3 x 30 mm, ≥ 2 jalur",
            suspension?.belt
        ) { r, s -> viewModel.onBeltChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Alat pengaman (tanpa bobot imbang)",
            "Switch otomatis berfungsi, motor berhenti",
            suspension?.slackRopeDevice
        ) { r, s -> viewModel.onSlackRopeDeviceChange(r, s) }
    }
}

@Composable
fun DrumsAndSheavesSection(
    drums: DrumsAndSheavesUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput(
            "Alur teromol",
            "Ada",
            drums?.drumGrooves
        ) { r, s -> viewModel.onDrumGroovesChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Diameter teromol penumpang/barang",
            "40 : 1",
            drums?.passengerDrumDiameter
        ) { r, s -> viewModel.onPassengerDrumDiameterChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Diameter teromol Governor",
            "25 : 1",
            drums?.governorDrumDiameter
        ) { r, s -> viewModel.onGovernorDrumDiameterChange(r, s) }
    }
}

@Composable
fun HoistwayAndPitSection(
    hoistway: HoistwayAndPitUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput(
            "Konstruksi",
            "Kuat, kokoh, tahan api, dan tertutup rapat",
            hoistway?.construction
        ) { r, s -> viewModel.onHoistwayConstructionChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Dinding",
            "Dapat dilalui orang dengan tinggi ≥ 2000 mm",
            hoistway?.walls
        ) { r, s -> viewModel.onHoistwayWallsChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Landasan jalur (elevator miring)",
            "Kuat dan tahan cuaca",
            hoistway?.inclinedElevatorTrackBed
        ) { r, s -> viewModel.onInclinedElevatorTrackBedChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Kebersihan",
            "Bersih, bebas dari instalasi dan peralatan lain",
            hoistway?.cleanliness
        ) { r, s -> viewModel.onHoistwayCleanlinessChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Penerangan",
            "≥ 100 lux",
            hoistway?.lighting
        ) { r, s -> viewModel.onHoistwayLightingChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Pintu darurat (non stop)",
            "Jarak maks 1100 mm, tinggi ambang maks 300 mm",
            hoistway?.emergencyDoorNonStop
        ) { r, s -> viewModel.onEmergencyDoorNonStopChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ukuran pintu darurat",
            "Lebar 700 mm, tinggi 1400 mm, membuka keluar",
            hoistway?.emergencyDoorSize
        ) { r, s -> viewModel.onEmergencyDoorSizeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Saklar pengaman pintu darurat",
            "Tersedia",
            hoistway?.emergencyDoorSafetySwitch
        ) { r, s -> viewModel.onEmergencyDoorSafetySwitchChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Jembatan bantu dari pintu darurat",
            "Tersedia, lebar ≥ 500 mm dan berpagar",
            hoistway?.emergencyDoorBridge
        ) { r, s -> viewModel.onEmergencyDoorBridgeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ruang bebas diatas sangkar",
            "≥ 500 mm",
            hoistway?.carTopClearance
        ) { r, s -> viewModel.onCarTopClearanceChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ruang bebas lekuk dasar (pit)",
            "≥ 500 mm (rumah tinggal ≥ 300 mm)",
            hoistway?.pitClearance
        ) { r, s -> viewModel.onPitClearanceChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tangga lekuk dasar",
            "Tersedia mulai dari kedalaman 1000 mm",
            hoistway?.pitLadder
        ) { r, s -> viewModel.onPitLadderChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Syarat pit (bukan di atas tanah)",
            "Kekuatan lantai ≥ 500 N/m², tersedia rem pengaman",
            hoistway?.pitBelowWorkingArea
        ) { r, s -> viewModel.onPitBelowWorkingAreaChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Akses menuju lekuk dasar",
            "Tersedia saklar pengaman (tinggi 1500mm, 500mm dari lantai pit)",
            hoistway?.pitAccessSwitch
        ) { r, s -> viewModel.onPitAccessSwitchChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Lekuk dasar antara 2 elevator",
            "Tersedia pit screen (tinggi 300mm dari dasar pit s/d 3000mm keatas)",
            hoistway?.pitScreen
        ) { r, s -> viewModel.onPitScreenChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Daun pintu ruang luncur",
            "Tahan api ≥ 1 jam, menutup rapat",
            hoistway?.hoistwayDoorLeaf
        ) { r, s -> viewModel.onHoistwayDoorLeafChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Interlock/kunci kait pintu",
            "Tersedia, menutup rapat, pintu terbuka hanya di zona henti",
            hoistway?.hoistwayDoorInterlock
        ) { r, s -> viewModel.onHoistwayDoorInterlockChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Kerataan lantai",
            "< 10 mm",
            hoistway?.floorLeveling
        ) { r, s -> viewModel.onFloorLevelingChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Sekat ruang luncur (2 sangkar)",
            "> 500 mm",
            hoistway?.hoistwaySeparatorBeam
        ) { r, s -> viewModel.onHoistwaySeparatorBeamChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Elevator miring",
            "Dipasang tangga sepanjang rel",
            hoistway?.inclinedElevatorStairs
        ) { r, s -> viewModel.onInclinedElevatorStairsChange(r, s) }
    }
}

@Composable
fun CarSection(
    car: CarUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput(
            "Kerangka",
            "Dari baja dan kuat",
            car?.frame
        ) { r, s -> viewModel.onCarFrameChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Badan Kereta",
            "Tertutup dan ada pintu",
            car?.body
        ) { r, s -> viewModel.onCarBodyChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tinggi dinding",
            "≥ 2000 mm",
            car?.wallHeight
        ) { r, s -> viewModel.onCarWallHeightChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Luas lantai",
            "Sesuai jumlah penumpang",
            car?.floorArea
        ) { r, s -> viewModel.onCarFloorAreaChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Perluasan luas kereta",
            "Pasien max 6%, Barang max 14%",
            car?.carAreaExpansion
        ) { r, s -> viewModel.onCarAreaExpansionChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Pintu kereta",
            "Kokoh, aman, otomatis",
            car?.carDoor
        ) { r, s -> viewModel.onCarDoorChange(r, s) }

        SectionSubHeader(title = "Syarat Pintu Kereta", style = MaterialTheme.typography.titleSmall)
        ResultStatusInput(
            "Ukuran",
            "≥ 700 x 2000 mm",
            car?.carDoorSpecs?.size
        ) { r, s -> viewModel.onCarDoorSizeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Kunci kait dan saklar pengaman",
            "Ada",
            car?.carDoorSpecs?.lockAndSwitch
        ) { r, s -> viewModel.onCarDoorLockAndSwitchChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Celah antar ambang pintu",
            "28 ≤ celah ≤ 32 mm",
            car?.carDoorSpecs?.sillClearance
        ) { r, s -> viewModel.onCarDoorSillClearanceChange(r, s) }

        SectionSubHeader(title = "Kelengkapan Kereta", style = MaterialTheme.typography.titleSmall)
        ResultStatusInput(
            "Sisi luar kereta dg balok (2 kereta)",
            "≥ 250 mm",
            car?.carToBeamClearance
        ) { r, s -> viewModel.onCarToBeamClearanceChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Alarm Bell",
            "Tersedia",
            car?.alarmBell
        ) { r, s -> viewModel.onCarAlarmBellChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Sumber tenaga cadangan (ARD)",
            "Tersedia",
            car?.backupPowerARD
        ) { r, s -> viewModel.onCarBackupPowerARDChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Intercom",
            "Tersedia",
            car?.intercom
        ) { r, s -> viewModel.onCarIntercomChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ventilasi",
            "Tersedia",
            car?.ventilation
        ) { r, s -> viewModel.onCarVentilationChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Penerangan darurat",
            "Tersedia",
            car?.emergencyLighting
        ) { r, s -> viewModel.onCarEmergencyLightingChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Panel operasi",
            "Tersedia",
            car?.operatingPanel
        ) { r, s -> viewModel.onCarOperatingPanelChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Petunjuk posisi sangkar",
            "Tersedia",
            car?.carPositionIndicator
        ) { r, s -> viewModel.onCarPositionIndicatorChange(r, s) }

        SectionSubHeader(
            title = "Keterangan Dalam Kereta (Signage)",
            style = MaterialTheme.typography.titleSmall
        )
        ResultStatusInput(
            "Nama Pembuat",
            "Tersedia",
            car?.carSignage?.manufacturerName
        ) { r, s -> viewModel.onCarSignageManufacturerNameChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Kapasitas Beban",
            "Tersedia",
            car?.carSignage?.loadCapacity
        ) { r, s -> viewModel.onCarSignageLoadCapacityChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Rambu dilarang merokok",
            "Tersedia",
            car?.carSignage?.noSmokingSign
        ) { r, s -> viewModel.onCarSignageNoSmokingSignChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Indikasi beban lebih",
            "Tersedia",
            car?.carSignage?.overloadIndicator
        ) { r, s -> viewModel.onCarSignageOverloadIndicatorChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tombol buka dan tutup",
            "Tersedia",
            car?.carSignage?.doorOpenCloseButtons
        ) { r, s -> viewModel.onCarSignageDoorOpenCloseButtonsChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tombol lantai pemberhentian",
            "Tersedia",
            car?.carSignage?.floorButtons
        ) { r, s -> viewModel.onCarSignageFloorButtonsChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tombol bell alarm",
            "Tersedia",
            car?.carSignage?.alarmButton
        ) { r, s -> viewModel.onCarSignageAlarmButtonChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Intercom dua arah",
            "Tersedia",
            car?.carSignage?.twoWayIntercom
        ) { r, s -> viewModel.onCarSignageTwoWayIntercomChange(r, s) }

        SectionSubHeader(
            title = "Atap Kereta dan Interior",
            style = MaterialTheme.typography.titleSmall
        )
        ResultStatusInput(
            "Kekuatan atap kereta",
            "≥ 200 kg",
            car?.carRoofStrength
        ) { r, s -> viewModel.onCarRoofStrengthChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Pintu darurat atap kereta",
            "Berengsel, saklar pengaman, buka dari luar, ukuran ≥ 350x450mm",
            car?.carTopEmergencyExit
        ) { r, s -> viewModel.onCarTopEmergencyExitChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Pintu darurat samping kereta",
            "Berengsel, buka dari luar, saklar pengaman, ukuran ≥ 350x1800mm",
            car?.carSideEmergencyExit
        ) { r, s -> viewModel.onCarSideEmergencyExitChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Pagar pengaman atap kereta",
            "Warna kuning, kekuatan ≥ 90 kg",
            car?.carTopGuardRail
        ) { r, s -> viewModel.onCarTopGuardRailChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ukuran pagar (celah 300-850mm)",
            "Tinggi ≥ 700 mm",
            car?.guardRailHeight300to850
        ) { r, s -> viewModel.onGuardRailHeight300to850Change(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ukuran pagar (celah >850mm)",
            "Tinggi ≥ 1100 mm",
            car?.guardRailHeightOver850
        ) { r, s -> viewModel.onGuardRailHeightOver850Change(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Penerangan atap kereta",
            "≥ 100 lux dengan kabel lentur 2 meter",
            car?.carTopLighting
        ) { r, s -> viewModel.onCarTopLightingChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tombol operasi Manual",
            "Permanen dengan tombol utama",
            car?.manualOperationButtons
        ) { r, s -> viewModel.onManualOperationButtonsChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Interior Kereta",
            "Bahan tidak mudah pecah/membahayakan",
            car?.carInterior
        ) { r, s -> viewModel.onCarInteriorChange(r, s) }

    }
}

@Composable
fun GovernorAndSafetyBrakeSection(
    gsb: GovernorAndSafetyBrakeUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput(
            "Penjepit Tali / Sabuk Governor",
            "Bekerja",
            gsb?.governorRopeClamp
        ) { r, s -> viewModel.onGovernorRopeClampChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Saklar Governor",
            "Berfungsi",
            gsb?.governorSwitch
        ) { r, s -> viewModel.onGovernorSwitchChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Fungsi Kecepatan Rem Pengaman",
            "115% - 140%, berhenti bertahap",
            gsb?.safetyBrakeSpeed
        ) { r, s -> viewModel.onSafetyBrakeSpeedChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Rem Pengaman",
            "Dipasang pada sangkar, berfungsi bertahap/berangsur/mendadak",
            gsb?.safetyBrakeType
        ) { r, s -> viewModel.onSafetyBrakeTypeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Bentuk Rem Pengaman",
            "Tidak boleh sistem elektris, hidrolik, atau pneumatik",
            gsb?.safetyBrakeMechanism
        ) { r, s -> viewModel.onSafetyBrakeMechanismChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Rem Pengaman Berangsur",
            "> 60 m/menit",
            gsb?.progressiveSafetyBrake
        ) { r, s -> viewModel.onProgressiveSafetyBrakeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Rem Pengaman Mendadak",
            "< 60 m/menit",
            gsb?.instantaneousSafetyBrake
        ) { r, s -> viewModel.onInstantaneousSafetyBrakeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Syarat Rem Pengaman",
            "Bekerja ke bawah, bekerja serempak",
            gsb?.safetyBrakeOperation
        ) { r, s -> viewModel.onSafetyBrakeOperationChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Kecepatan Kereta >= 60m/menit",
            "Ada pemutus elektrik",
            gsb?.electricalCutoutSwitch
        ) { r, s -> viewModel.onElectricalCutoutSwitchChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Saklar Pengaman Lintas Batas",
            "Berfungsi",
            gsb?.limitSwitch
        ) { r, s -> viewModel.onLimitSwitchChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Alat Pembatas Beban",
            "Berfungsi",
            gsb?.overloadDevice
        ) { r, s -> viewModel.onOverloadDeviceChange(r, s) }
    }
}

@Composable
fun CounterweightGuideRailsAndBuffersSection(
    cgb: CounterweightGuideRailsAndBuffersUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput(
            "Bahan Bobot Imbang",
            "Beton/steel block",
            cgb?.counterweightMaterial
        ) { r, s -> viewModel.onCounterweightMaterialChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Sekat Pengaman Bobot Imbang",
            "Tinggi 2500 mm, > 300mm dari dasar pit",
            cgb?.counterweightGuardScreen
        ) { r, s -> viewModel.onCounterweightGuardScreenChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Konstruksi Rel Pemandu",
            "Kuat memandu jalan, menahan tekanan saat rem bekerja",
            cgb?.guideRailConstruction
        ) { r, s -> viewModel.onGuideRailConstructionChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Jenis Peredam",
            "Massif kenyal / pegas / hidrolik",
            cgb?.bufferType
        ) { r, s -> viewModel.onBufferTypeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Fungsi Peredam",
            "Meredam secara bertahap",
            cgb?.bufferFunction
        ) { r, s -> viewModel.onBufferFunctionChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Saklar Pengaman (kecepatan >= 90m/menit)",
            "Tersedia",
            cgb?.bufferSafetySwitch
        ) { r, s -> viewModel.onBufferSafetySwitchChange(r, s) }
    }
}

@Composable
fun ElectricalInstallationSection(
    electrical: ElectricalInstallationUiState?,
    viewModel: ReportViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ResultStatusInput(
            "Standar Rangkaian Instalasi Listrik",
            "SNI dan standar Internasional",
            electrical?.installationStandard
        ) { r, s -> viewModel.onInstallationStandardChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Panel Listrik",
            "Panel khusus untuk elevator",
            electrical?.electricalPanel
        ) { r, s -> viewModel.onElectricalPanelChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Catu Daya Pengganti (ARD)",
            "Tersedia",
            electrical?.backupPowerARD
        ) { r, s -> viewModel.onElectricalBackupPowerARDChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Kabel Grounding",
            "Penampang ≥ 10 mm², Tahanan ≤ 5 Ω (ohm)",
            electrical?.groundingCable
        ) { r, s -> viewModel.onGroundingCableChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Alarm Kebakaran",
            "Terhubung dan beroperasi otomatis",
            electrical?.fireAlarmConnection
        ) { r, s -> viewModel.onFireAlarmConnectionChange(r, s) }

        SectionSubHeader(
            title = "Elevator Penanggulangan Kebakaran",
            style = MaterialTheme.typography.titleSmall
        )
        ResultStatusInput(
            "Catu Daya Cadangan",
            "Tersedia",
            electrical?.fireServiceElevator?.backupPower
        ) { r, s -> viewModel.onFireServiceBackupPowerChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Pengoperasian Khusus",
            "Manual, dapat berhenti tiap lantai",
            electrical?.fireServiceElevator?.specialOperation
        ) { r, s -> viewModel.onFireServiceSpecialOperationChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Saklar Kebakaran",
            "Di lantai evakuasi, dapat dioperasikan manual",
            electrical?.fireServiceElevator?.fireSwitch
        ) { r, s -> viewModel.onFireServiceFireSwitchChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Label 'Elevator Penanggulangan Kebakaran'",
            "Tersedia",
            electrical?.fireServiceElevator?.label
        ) { r, s -> viewModel.onFireServiceLabelChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ketahanan Instalasi Listrik Terhadap Api",
            "≥ 2 jam",
            electrical?.fireServiceElevator?.electricalFireResistance
        ) { r, s -> viewModel.onFireServiceElectricalFireResistanceChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Dinding Luncur",
            "Tertutup rapat, tahan api ≥ 1 jam",
            electrical?.fireServiceElevator?.hoistwayWallFireResistance
        ) { r, s -> viewModel.onFireServiceHoistwayWallFireResistanceChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ukuran Sangkar",
            "≥ 1100x1400 mm, kapasitas ≥ 630 kg",
            electrical?.fireServiceElevator?.carSize
        ) { r, s -> viewModel.onFireServiceCarSizeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Ukuran Pintu Kereta",
            "≥ 800 x 2100 mm",
            electrical?.fireServiceElevator?.doorSize
        ) { r, s -> viewModel.onFireServiceDoorSizeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Waktu Tempuh",
            "≤ 60 detik",
            electrical?.fireServiceElevator?.travelTime
        ) { r, s -> viewModel.onFireServiceTravelTimeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Lantai Evakuasi",
            "Tidak boleh ada penghalang",
            electrical?.fireServiceElevator?.evacuationFloor
        ) { r, s -> viewModel.onFireServiceEvacuationFloorChange(r, s) }

        SectionSubHeader(
            title = "Elevator Untuk Disabilitas",
            style = MaterialTheme.typography.titleSmall
        )
        ResultStatusInput(
            "Panel Operasi",
            "Huruf braille",
            electrical?.accessibilityElevator?.operatingPanel
        ) { r, s -> viewModel.onAccessibilityOperatingPanelChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Tinggi Panel Operasi",
            "900 mm ≤ tinggi ≤ 1100 mm",
            electrical?.accessibilityElevator?.panelHeight
        ) { r, s -> viewModel.onAccessibilityPanelHeightChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Waktu Bukaan Pintu",
            "≥ 2 menit",
            electrical?.accessibilityElevator?.doorOpenTime
        ) { r, s -> viewModel.onAccessibilityDoorOpenTimeChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Lebar Bukaan Pintu",
            "≥ 1000 mm",
            electrical?.accessibilityElevator?.doorWidth
        ) { r, s -> viewModel.onAccessibilityDoorWidthChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Informasi Operasi",
            "Bersuara",
            electrical?.accessibilityElevator?.audioInformation
        ) { r, s -> viewModel.onAccessibilityAudioInformationChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Label 'Elevator Disabilitas'",
            "Tersedia",
            electrical?.accessibilityElevator?.label
        ) { r, s -> viewModel.onAccessibilityLabelChange(r, s) }

        SectionSubHeader(title = "Sensor Gempa", style = MaterialTheme.typography.titleSmall)
        ResultStatusInput(
            "Ketersediaan (> 10 Lt / 40 m)",
            "Tersedia sensor gempa",
            electrical?.seismicSensor?.availability
        ) { r, s -> viewModel.onSeismicSensorAvailabilityChange(r, s) }
        Spacer(modifier = Modifier.height(8.dp))
        ResultStatusInput(
            "Fungsi Input Sinyal Sensor Gempa",
            "Berhenti lantai terdekat, pintu terbuka, tidak dapat dioperasikan",
            electrical?.seismicSensor?.function
        ) { r, s -> viewModel.onSeismicSensorFunctionChange(r, s) }
    }
}

@Composable
fun ConclusionSection(conclusion: String?, viewModel: ReportViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        OutlinedTextField(
            value = conclusion.orEmpty(),
            onValueChange = { viewModel.onConclusionChange(it) },
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
            ElevatorScreen(onBackClick = {}, menuTitle = "Elevator dan Eskalator")
        }
    }
}