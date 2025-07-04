package com.nakersolutionid.nakersolutionid.features.report.elevator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.data.Resource
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

    val options = listOf("Elevator", "Eskalator")
    val checkedIcons = listOf(Icons.Filled.Work, Icons.Filled.Restaurant, Icons.Filled.Coffee)
    var selectedIndex by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.onNameOfInspectionTypeChange(menuTitle)

    // Handle side-effects from registrationResult
    val sendReportResult = uiState.sendReportResult
    LaunchedEffect(sendReportResult) {
        when (sendReportResult) {
            is Resource.Success -> {
                // Navigate on success
                viewModel.toggleLoading(false)
                viewModel.onStateHandledSuccess()
                onBackClick()
            }
            is Resource.Error -> {
                // Show error message
                val errorMessage = sendReportResult.message ?: "An unknown error occurred"
                scope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
                viewModel.toggleLoading(false)
                viewModel.onStateHandledFailed()
            }
            is Resource.Loading -> {
                viewModel.toggleLoading(true)
            }
            else -> { /* Do nothing for Loading or null */ }
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
                    Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                ) {
                    val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1f))
                    options.forEachIndexed { index, label ->
                        ToggleButton(
                            checked = selectedIndex == index,
                            onCheckedChange = { selectedIndex = index },
                            modifier = modifiers[index].semantics { role = Role.RadioButton },
                            shapes =
                                when (index) {
                                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                    options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                },
                        ) {
                            Text(label)
                        }
                    }
                }

                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = uiState.isLoading,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    LinearWavyProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        amplitude = 0.3f
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            if (selectedIndex == 0) {
                viewModel.onSubNameOfInspectionTypeChange(options[selectedIndex])
                item { MainData(uiState, viewModel) }
                item { GeneralDataSection(uiState.generalData, viewModel) }
                item { TechnicalDocumentInspectionSection(uiState.technicalDocumentInspection, viewModel) }
                item { InspectionAndTestingSection(uiState.inspectionAndTesting, viewModel) }
                item { ConclusionSection(uiState.conclusion, viewModel) }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleLarge) {
    Text(
        text = title,
        style = style,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
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
            .padding(vertical = 4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun ResultStatusInput(
    label: String,
    resultStatus: ResultStatusUiState?,
    onValueChange: (String?, Boolean?) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            value = resultStatus?.result.orEmpty(),
            onValueChange = { onValueChange(it, resultStatus?.status) },
            label = { Text(label) },
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = resultStatus?.status == true,
            onCheckedChange = { onValueChange(resultStatus?.result, it) }
        )
        Text("Memenuhi")
    }
}

@Composable
fun MainData(uiState: ElevatorUiState?, viewModel: ReportViewModel) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader("Main Data")
            FormTextField("Inspection Type", uiState?.typeInspection) { viewModel.onTypeInspectionChange(it) }
            FormTextField("Elevator Type", uiState?.eskOrElevType) { viewModel.onEskOrElevTypeChange(it) }
        }
    }
}

@Composable
fun GeneralDataSection(generalData: GeneralDataUiState?, viewModel: ReportViewModel) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader("General Data")
            FormTextField("Owner Name", generalData?.ownerName) { viewModel.onOwnerNameChange(it) }
            FormTextField("Owner Address", generalData?.ownerAddress) { viewModel.onOwnerAddressChange(it) }
            FormTextField("Usage Location", generalData?.nameUsageLocation) { viewModel.onNameUsageLocationChange(it) }
            FormTextField("Usage Address", generalData?.addressUsageLocation) { viewModel.onAddressUsageLocationChange(it) }
            FormTextField("Manufacturer/Installer", generalData?.manufacturerOrInstaller) { viewModel.onManufacturerOrInstallerChange(it) }
            FormTextField("Elevator Type", generalData?.elevatorType) { viewModel.onElevatorTypeChange(it) }
            FormTextField("Brand/Type", generalData?.brandOrType) { viewModel.onBrandOrTypeChange(it) }
            FormTextField("Country and Year", generalData?.countryAndYear) { viewModel.onCountryAndYearChange(it) }
            FormTextField("Serial Number", generalData?.serialNumber) { viewModel.onSerialNumberChange(it) }
            FormTextField("Capacity", generalData?.capacity, keyboardType = KeyboardType.Number) { viewModel.onCapacityChange(it) }
            FormTextField("Speed", generalData?.speed, keyboardType = KeyboardType.Number) { viewModel.onSpeedChange(it) }
            FormTextField("Floors Served", generalData?.floorsServed, keyboardType = KeyboardType.Number) { viewModel.onFloorsServedChange(it) }
            FormTextField("Permit Number", generalData?.permitNumber) { viewModel.onPermitNumberChange(it) }
            FormTextField("Inspection Date", generalData?.inspectionDate) { viewModel.onInspectionDateChange(it) }
        }
    }
}

@Composable
fun TechnicalDocumentInspectionSection(
    technicalDocs: TechnicalDocumentInspectionUiState?,
    viewModel: ReportViewModel
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader("Technical Document Inspection")
            FormTextField("Design Drawing", technicalDocs?.designDrawing) { viewModel.onDesignDrawingChange(it) }
            FormTextField("Technical Calculation", technicalDocs?.technicalCalculation) { viewModel.onTechnicalCalculationChange(it) }
            FormTextField("Material Certificate", technicalDocs?.materialCertificate) { viewModel.onMaterialCertificateChange(it) }
            FormTextField("Control Panel Diagram", technicalDocs?.controlPanelDiagram) { viewModel.onControlPanelDiagramChange(it) }
            FormTextField("As-Built Drawing", technicalDocs?.asBuiltDrawing) { viewModel.onAsBuiltDrawingChange(it) }
            FormTextField("Component Certificates", technicalDocs?.componentCertificates) { viewModel.onComponentCertificatesChange(it) }
            FormTextField("Safe Work Procedure", technicalDocs?.safeWorkProcedure) { viewModel.onSafeWorkProcedureChange(it) }
        }
    }
}

@Composable
fun InspectionAndTestingSection(
    inspectionAndTesting: InspectionAndTestingUiState?,
    viewModel: ReportViewModel
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader("Inspection and Testing")
            MachineRoomAndMachinerySection(inspectionAndTesting?.machineRoomAndMachinery, viewModel)
            SuspensionRopesAndBeltsSection(inspectionAndTesting?.suspensionRopesAndBelts, viewModel)
            DrumsAndSheavesSection(inspectionAndTesting?.drumsAndSheaves, viewModel)
            HoistwayAndPitSection(inspectionAndTesting?.hoistwayAndPit, viewModel)
            CarSection(inspectionAndTesting?.car, viewModel)
            GovernorAndSafetyBrakeSection(inspectionAndTesting?.governorAndSafetyBrake, viewModel)
            CounterweightGuideRailsAndBuffersSection(inspectionAndTesting?.counterweightGuideRailsAndBuffers, viewModel)
            ElectricalInstallationSection(inspectionAndTesting?.electricalInstallation, viewModel)
        }
    }
}

@Composable
fun MachineRoomAndMachinerySection(
    machineRoom: MachineRoomAndMachineryUiState?,
    viewModel: ReportViewModel
) {
    SectionHeader("Machine Room and Machinery", style = MaterialTheme.typography.titleMedium)
    ResultStatusInput("Machine Mounting", machineRoom?.machineMounting) { r, s -> viewModel.onMachineMountingChange(r, s) }
    ResultStatusInput("Mechanical Brake", machineRoom?.mechanicalBrake) { r, s -> viewModel.onMechanicalBrakeChange(r, s) }
    ResultStatusInput("Electrical Brake", machineRoom?.electricalBrake) { r, s -> viewModel.onElectricalBrakeChange(r, s) }
    ResultStatusInput("Machine Room Construction", machineRoom?.machineRoomConstruction) { r, s -> viewModel.onMachineRoomConstructionChange(r, s) }
    ResultStatusInput("Machine Room Clearance", machineRoom?.machineRoomClearance) { r, s -> viewModel.onMachineRoomClearanceChange(r, s) }
    ResultStatusInput("Machine Room Implementation", machineRoom?.machineRoomImplementation) { r, s -> viewModel.onMachineRoomImplementationChange(r, s) }
    ResultStatusInput("Ventilation", machineRoom?.ventilation) { r, s -> viewModel.onMachineRoomVentilationChange(r, s) }
    ResultStatusInput("Machine Room Door", machineRoom?.machineRoomDoor) { r, s -> viewModel.onMachineRoomDoorChange(r, s) }
    ResultStatusInput("Main Power Panel Position", machineRoom?.mainPowerPanelPosition) { r, s -> viewModel.onMainPowerPanelPositionChange(r, s) }
    ResultStatusInput("Rotating Parts Guard", machineRoom?.rotatingPartsGuard) { r, s -> viewModel.onRotatingPartsGuardChange(r, s) }
    ResultStatusInput("Rope Hole Guard", machineRoom?.ropeHoleGuard) { r, s -> viewModel.onRopeHoleGuardChange(r, s) }
    ResultStatusInput("Machine Room Access Ladder", machineRoom?.machineRoomAccessLadder) { r, s -> viewModel.onMachineRoomAccessLadderChange(r, s) }
    ResultStatusInput("Floor Level Difference", machineRoom?.floorLevelDifference) { r, s -> viewModel.onFloorLevelDifferenceChange(r, s) }
    ResultStatusInput("Fire Extinguisher", machineRoom?.fireExtinguisher) { r, s -> viewModel.onMachineRoomFireExtinguisherChange(r, s) }
    ResultStatusInput("Emergency Stop Switch", machineRoom?.emergencyStopSwitch) { r, s -> viewModel.onMachineRoomEmergencyStopSwitchChange(r, s) }

    SectionHeader("Machine Roomless", style = MaterialTheme.typography.titleSmall)
    ResultStatusInput("Panel Placement", machineRoom?.machineRoomless?.panelPlacement) { r, s -> viewModel.onPanelPlacementChange(r, s) }
    ResultStatusInput("Lighting Work Area", machineRoom?.machineRoomless?.lightingWorkArea) { r, s -> viewModel.onLightingWorkAreaChange(r, s) }
    ResultStatusInput("Lighting Between Work Area", machineRoom?.machineRoomless?.lightingBetweenWorkArea) { r, s -> viewModel.onLightingBetweenWorkAreaChange(r, s) }
    ResultStatusInput("Manual Brake Release", machineRoom?.machineRoomless?.manualBrakeRelease) { r, s -> viewModel.onManualBrakeReleaseChange(r, s) }
    ResultStatusInput("Fire Extinguisher Placement", machineRoom?.machineRoomless?.fireExtinguisherPlacement) { r, s -> viewModel.onFireExtinguisherPlacementChange(r, s) }
}

@Composable
fun SuspensionRopesAndBeltsSection(
    suspension: SuspensionRopesAndBeltsUiState?,
    viewModel: ReportViewModel
) {
    SectionHeader("Suspension Ropes and Belts", style = MaterialTheme.typography.titleMedium)
    ResultStatusInput("Condition", suspension?.condition) { r, s -> viewModel.onSuspensionRopeConditionChange(r, s) }
    ResultStatusInput("Chain Usage", suspension?.chainUsage) { r, s -> viewModel.onChainUsageChange(r, s) }
    ResultStatusInput("Safety Factor", suspension?.safetyFactor) { r, s -> viewModel.onSafetyFactorChange(r, s) }
    ResultStatusInput("Rope with Counterweight", suspension?.ropeWithCounterweight) { r, s -> viewModel.onRopeWithCounterweightChange(r, s) }
    ResultStatusInput("Rope without Counterweight", suspension?.ropeWithoutCounterweight) { r, s -> viewModel.onRopeWithoutCounterweightChange(r, s) }
    ResultStatusInput("Belt", suspension?.belt) { r, s -> viewModel.onBeltChange(r, s) }
    ResultStatusInput("Slack Rope Device", suspension?.slackRopeDevice) { r, s -> viewModel.onSlackRopeDeviceChange(r, s) }
}

@Composable
fun DrumsAndSheavesSection(
    drums: DrumsAndSheavesUiState?,
    viewModel: ReportViewModel
) {
    SectionHeader("Drums and Sheaves", style = MaterialTheme.typography.titleMedium)
    ResultStatusInput("Drum Grooves", drums?.drumGrooves) { r, s -> viewModel.onDrumGroovesChange(r, s) }
    ResultStatusInput("Passenger Drum Diameter", drums?.passengerDrumDiameter) { r, s -> viewModel.onPassengerDrumDiameterChange(r, s) }
    ResultStatusInput("Governor Drum Diameter", drums?.governorDrumDiameter) { r, s -> viewModel.onGovernorDrumDiameterChange(r, s) }
}

@Composable
fun HoistwayAndPitSection(
    hoistway: HoistwayAndPitUiState?,
    viewModel: ReportViewModel
) {
    SectionHeader("Hoistway and Pit", style = MaterialTheme.typography.titleMedium)
    ResultStatusInput("Construction", hoistway?.construction) { r, s -> viewModel.onHoistwayConstructionChange(r, s) }
    ResultStatusInput("Walls", hoistway?.walls) { r, s -> viewModel.onHoistwayWallsChange(r, s) }
    ResultStatusInput("Inclined Elevator Track Bed", hoistway?.inclinedElevatorTrackBed) { r, s -> viewModel.onInclinedElevatorTrackBedChange(r, s) }
    ResultStatusInput("Cleanliness", hoistway?.cleanliness) { r, s -> viewModel.onHoistwayCleanlinessChange(r, s) }
    ResultStatusInput("Lighting", hoistway?.lighting) { r, s -> viewModel.onHoistwayLightingChange(r, s) }
    ResultStatusInput("Emergency Door (Non-Stop)", hoistway?.emergencyDoorNonStop) { r, s -> viewModel.onEmergencyDoorNonStopChange(r, s) }
    ResultStatusInput("Emergency Door Size", hoistway?.emergencyDoorSize) { r, s -> viewModel.onEmergencyDoorSizeChange(r, s) }
    ResultStatusInput("Emergency Door Safety Switch", hoistway?.emergencyDoorSafetySwitch) { r, s -> viewModel.onEmergencyDoorSafetySwitchChange(r, s) }
    ResultStatusInput("Emergency Door Bridge", hoistway?.emergencyDoorBridge) { r, s -> viewModel.onEmergencyDoorBridgeChange(r, s) }
    ResultStatusInput("Car Top Clearance", hoistway?.carTopClearance) { r, s -> viewModel.onCarTopClearanceChange(r, s) }
    ResultStatusInput("Pit Clearance", hoistway?.pitClearance) { r, s -> viewModel.onPitClearanceChange(r, s) }
    ResultStatusInput("Pit Ladder", hoistway?.pitLadder) { r, s -> viewModel.onPitLadderChange(r, s) }
    ResultStatusInput("Pit Below Working Area", hoistway?.pitBelowWorkingArea) { r, s -> viewModel.onPitBelowWorkingAreaChange(r, s) }
    ResultStatusInput("Pit Access Switch", hoistway?.pitAccessSwitch) { r, s -> viewModel.onPitAccessSwitchChange(r, s) }
    ResultStatusInput("Pit Screen", hoistway?.pitScreen) { r, s -> viewModel.onPitScreenChange(r, s) }
    ResultStatusInput("Hoistway Door Leaf", hoistway?.hoistwayDoorLeaf) { r, s -> viewModel.onHoistwayDoorLeafChange(r, s) }
    ResultStatusInput("Hoistway Door Interlock", hoistway?.hoistwayDoorInterlock) { r, s -> viewModel.onHoistwayDoorInterlockChange(r, s) }
    ResultStatusInput("Floor Leveling", hoistway?.floorLeveling) { r, s -> viewModel.onFloorLevelingChange(r, s) }
    ResultStatusInput("Hoistway Separator Beam", hoistway?.hoistwaySeparatorBeam) { r, s -> viewModel.onHoistwaySeparatorBeamChange(r, s) }
    ResultStatusInput("Inclined Elevator Stairs", hoistway?.inclinedElevatorStairs) { r, s -> viewModel.onInclinedElevatorStairsChange(r, s) }
}

@Composable
fun CarSection(
    car: CarUiState?,
    viewModel: ReportViewModel
) {
    SectionHeader("Car", style = MaterialTheme.typography.titleMedium)
    ResultStatusInput("Frame", car?.frame) { r, s -> viewModel.onCarFrameChange(r, s) }
    ResultStatusInput("Body", car?.body) { r, s -> viewModel.onCarBodyChange(r, s) }
    ResultStatusInput("Wall Height", car?.wallHeight) { r, s -> viewModel.onCarWallHeightChange(r, s) }
    ResultStatusInput("Floor Area", car?.floorArea) { r, s -> viewModel.onCarFloorAreaChange(r, s) }
    ResultStatusInput("Car Area Expansion", car?.carAreaExpansion) { r, s -> viewModel.onCarAreaExpansionChange(r, s) }
    ResultStatusInput("Car Door", car?.carDoor) { r, s -> viewModel.onCarDoorChange(r, s) }
    ResultStatusInput("Car to Beam Clearance", car?.carToBeamClearance) { r, s -> viewModel.onCarToBeamClearanceChange(r, s) }
    ResultStatusInput("Alarm Bell", car?.alarmBell) { r, s -> viewModel.onCarAlarmBellChange(r, s) }
    ResultStatusInput("Backup Power (ARD)", car?.backupPowerARD) { r, s -> viewModel.onCarBackupPowerARDChange(r, s) }
    ResultStatusInput("Intercom", car?.intercom) { r, s -> viewModel.onCarIntercomChange(r, s) }
    ResultStatusInput("Ventilation", car?.ventilation) { r, s -> viewModel.onCarVentilationChange(r, s) }
    ResultStatusInput("Emergency Lighting", car?.emergencyLighting) { r, s -> viewModel.onCarEmergencyLightingChange(r, s) }
    ResultStatusInput("Operating Panel", car?.operatingPanel) { r, s -> viewModel.onCarOperatingPanelChange(r, s) }
    ResultStatusInput("Car Position Indicator", car?.carPositionIndicator) { r, s -> viewModel.onCarPositionIndicatorChange(r, s) }
    ResultStatusInput("Car Roof Strength", car?.carRoofStrength) { r, s -> viewModel.onCarRoofStrengthChange(r, s) }
    ResultStatusInput("Car Top Emergency Exit", car?.carTopEmergencyExit) { r, s -> viewModel.onCarTopEmergencyExitChange(r, s) }
    ResultStatusInput("Car Side Emergency Exit", car?.carSideEmergencyExit) { r, s -> viewModel.onCarSideEmergencyExitChange(r, s) }
    ResultStatusInput("Car Top Guard Rail", car?.carTopGuardRail) { r, s -> viewModel.onCarTopGuardRailChange(r, s) }
    ResultStatusInput("Guard Rail Height (300-850mm)", car?.guardRailHeight300to850) { r, s -> viewModel.onGuardRailHeight300to850Change(r, s) }
    ResultStatusInput("Guard Rail Height (>850mm)", car?.guardRailHeightOver850) { r, s -> viewModel.onGuardRailHeightOver850Change(r, s) }
    ResultStatusInput("Car Top Lighting", car?.carTopLighting) { r, s -> viewModel.onCarTopLightingChange(r, s) }
    ResultStatusInput("Manual Operation Buttons", car?.manualOperationButtons) { r, s -> viewModel.onManualOperationButtonsChange(r, s) }
    ResultStatusInput("Car Interior", car?.carInterior) { r, s -> viewModel.onCarInteriorChange(r, s) }

    SectionHeader("Car Door Specs", style = MaterialTheme.typography.titleSmall)
    ResultStatusInput("Size", car?.carDoorSpecs?.size) { r, s -> viewModel.onCarDoorSizeChange(r, s) }
    ResultStatusInput("Lock and Switch", car?.carDoorSpecs?.lockAndSwitch) { r, s -> viewModel.onCarDoorLockAndSwitchChange(r, s) }
    ResultStatusInput("Sill Clearance", car?.carDoorSpecs?.sillClearance) { r, s -> viewModel.onCarDoorSillClearanceChange(r, s) }

    SectionHeader("Car Signage", style = MaterialTheme.typography.titleSmall)
    ResultStatusInput("Manufacturer Name", car?.carSignage?.manufacturerName) { r, s -> viewModel.onCarSignageManufacturerNameChange(r, s) }
    ResultStatusInput("Load Capacity", car?.carSignage?.loadCapacity) { r, s -> viewModel.onCarSignageLoadCapacityChange(r, s) }
    ResultStatusInput("No Smoking Sign", car?.carSignage?.noSmokingSign) { r, s -> viewModel.onCarSignageNoSmokingSignChange(r, s) }
    ResultStatusInput("Overload Indicator", car?.carSignage?.overloadIndicator) { r, s -> viewModel.onCarSignageOverloadIndicatorChange(r, s) }
    ResultStatusInput("Door Open/Close Buttons", car?.carSignage?.doorOpenCloseButtons) { r, s -> viewModel.onCarSignageDoorOpenCloseButtonsChange(r, s) }
    ResultStatusInput("Floor Buttons", car?.carSignage?.floorButtons) { r, s -> viewModel.onCarSignageFloorButtonsChange(r, s) }
    ResultStatusInput("Alarm Button", car?.carSignage?.alarmButton) { r, s -> viewModel.onCarSignageAlarmButtonChange(r, s) }
    ResultStatusInput("Two-Way Intercom", car?.carSignage?.twoWayIntercom) { r, s -> viewModel.onCarSignageTwoWayIntercomChange(r, s) }
}

@Composable
fun GovernorAndSafetyBrakeSection(
    gsb: GovernorAndSafetyBrakeUiState?,
    viewModel: ReportViewModel
) {
    SectionHeader("Governor and Safety Brake", style = MaterialTheme.typography.titleMedium)
    ResultStatusInput("Governor Rope Clamp", gsb?.governorRopeClamp) { r, s -> viewModel.onGovernorRopeClampChange(r, s) }
    ResultStatusInput("Governor Switch", gsb?.governorSwitch) { r, s -> viewModel.onGovernorSwitchChange(r, s) }
    ResultStatusInput("Safety Brake Speed", gsb?.safetyBrakeSpeed) { r, s -> viewModel.onSafetyBrakeSpeedChange(r, s) }
    ResultStatusInput("Safety Brake Type", gsb?.safetyBrakeType) { r, s -> viewModel.onSafetyBrakeTypeChange(r, s) }
    ResultStatusInput("Safety Brake Mechanism", gsb?.safetyBrakeMechanism) { r, s -> viewModel.onSafetyBrakeMechanismChange(r, s) }
    ResultStatusInput("Progressive Safety Brake", gsb?.progressiveSafetyBrake) { r, s -> viewModel.onProgressiveSafetyBrakeChange(r, s) }
    ResultStatusInput("Instantaneous Safety Brake", gsb?.instantaneousSafetyBrake) { r, s -> viewModel.onInstantaneousSafetyBrakeChange(r, s) }
    ResultStatusInput("Safety Brake Operation", gsb?.safetyBrakeOperation) { r, s -> viewModel.onSafetyBrakeOperationChange(r, s) }
    ResultStatusInput("Electrical Cutout Switch", gsb?.electricalCutoutSwitch) { r, s -> viewModel.onElectricalCutoutSwitchChange(r, s) }
    ResultStatusInput("Limit Switch", gsb?.limitSwitch) { r, s -> viewModel.onLimitSwitchChange(r, s) }
    ResultStatusInput("Overload Device", gsb?.overloadDevice) { r, s -> viewModel.onOverloadDeviceChange(r, s) }
}

@Composable
fun CounterweightGuideRailsAndBuffersSection(
    cgb: CounterweightGuideRailsAndBuffersUiState?,
    viewModel: ReportViewModel
) {
    SectionHeader("Counterweight, Guide Rails, and Buffers", style = MaterialTheme.typography.titleMedium)
    ResultStatusInput("Counterweight Material", cgb?.counterweightMaterial) { r, s -> viewModel.onCounterweightMaterialChange(r, s) }
    ResultStatusInput("Counterweight Guard Screen", cgb?.counterweightGuardScreen) { r, s -> viewModel.onCounterweightGuardScreenChange(r, s) }
    ResultStatusInput("Guide Rail Construction", cgb?.guideRailConstruction) { r, s -> viewModel.onGuideRailConstructionChange(r, s) }
    ResultStatusInput("Buffer Type", cgb?.bufferType) { r, s -> viewModel.onBufferTypeChange(r, s) }
    ResultStatusInput("Buffer Function", cgb?.bufferFunction) { r, s -> viewModel.onBufferFunctionChange(r, s) }
    ResultStatusInput("Buffer Safety Switch", cgb?.bufferSafetySwitch) { r, s -> viewModel.onBufferSafetySwitchChange(r, s) }
}

@Composable
fun ElectricalInstallationSection(
    electrical: ElectricalInstallationUiState?,
    viewModel: ReportViewModel
) {
    SectionHeader("Electrical Installation", style = MaterialTheme.typography.titleMedium)
    ResultStatusInput("Installation Standard", electrical?.installationStandard) { r, s -> viewModel.onInstallationStandardChange(r, s) }
    ResultStatusInput("Electrical Panel", electrical?.electricalPanel) { r, s -> viewModel.onElectricalPanelChange(r, s) }
    ResultStatusInput("Backup Power (ARD)", electrical?.backupPowerARD) { r, s -> viewModel.onElectricalBackupPowerARDChange(r, s) }
    ResultStatusInput("Grounding Cable", electrical?.groundingCable) { r, s -> viewModel.onGroundingCableChange(r, s) }
    ResultStatusInput("Fire Alarm Connection", electrical?.fireAlarmConnection) { r, s -> viewModel.onFireAlarmConnectionChange(r, s) }

    SectionHeader("Fire Service Elevator", style = MaterialTheme.typography.titleSmall)
    ResultStatusInput("Backup Power", electrical?.fireServiceElevator?.backupPower) { r, s -> viewModel.onFireServiceBackupPowerChange(r, s) }
    ResultStatusInput("Special Operation", electrical?.fireServiceElevator?.specialOperation) { r, s -> viewModel.onFireServiceSpecialOperationChange(r, s) }
    ResultStatusInput("Fire Switch", electrical?.fireServiceElevator?.fireSwitch) { r, s -> viewModel.onFireServiceFireSwitchChange(r, s) }
    ResultStatusInput("Label", electrical?.fireServiceElevator?.label) { r, s -> viewModel.onFireServiceLabelChange(r, s) }
    ResultStatusInput("Electrical Fire Resistance", electrical?.fireServiceElevator?.electricalFireResistance) { r, s -> viewModel.onFireServiceElectricalFireResistanceChange(r, s) }
    ResultStatusInput("Hoistway Wall Fire Resistance", electrical?.fireServiceElevator?.hoistwayWallFireResistance) { r, s -> viewModel.onFireServiceHoistwayWallFireResistanceChange(r, s) }
    ResultStatusInput("Car Size", electrical?.fireServiceElevator?.carSize) { r, s -> viewModel.onFireServiceCarSizeChange(r, s) }
    ResultStatusInput("Door Size", electrical?.fireServiceElevator?.doorSize) { r, s -> viewModel.onFireServiceDoorSizeChange(r, s) }
    ResultStatusInput("Travel Time", electrical?.fireServiceElevator?.travelTime) { r, s -> viewModel.onFireServiceTravelTimeChange(r, s) }
    ResultStatusInput("Evacuation Floor", electrical?.fireServiceElevator?.evacuationFloor) { r, s -> viewModel.onFireServiceEvacuationFloorChange(r, s) }

    SectionHeader("Accessibility Elevator", style = MaterialTheme.typography.titleSmall)
    ResultStatusInput("Operating Panel", electrical?.accessibilityElevator?.operatingPanel) { r, s -> viewModel.onAccessibilityOperatingPanelChange(r, s) }
    ResultStatusInput("Panel Height", electrical?.accessibilityElevator?.panelHeight) { r, s -> viewModel.onAccessibilityPanelHeightChange(r, s) }
    ResultStatusInput("Door Open Time", electrical?.accessibilityElevator?.doorOpenTime) { r, s -> viewModel.onAccessibilityDoorOpenTimeChange(r, s) }
    ResultStatusInput("Door Width", electrical?.accessibilityElevator?.doorWidth) { r, s -> viewModel.onAccessibilityDoorWidthChange(r, s) }
    ResultStatusInput("Audio Information", electrical?.accessibilityElevator?.audioInformation) { r, s -> viewModel.onAccessibilityAudioInformationChange(r, s) }
    ResultStatusInput("Label", electrical?.accessibilityElevator?.label) { r, s -> viewModel.onAccessibilityLabelChange(r, s) }

    SectionHeader("Seismic Sensor", style = MaterialTheme.typography.titleSmall)
    ResultStatusInput("Availability", electrical?.seismicSensor?.availability) { r, s -> viewModel.onSeismicSensorAvailabilityChange(r, s) }
    ResultStatusInput("Function", electrical?.seismicSensor?.function) { r, s -> viewModel.onSeismicSensorFunctionChange(r, s) }
}

@Composable
fun ConclusionSection(conclusion: String?, viewModel: ReportViewModel) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader("Conclusion")
            FormTextField("Conclusion", conclusion) { viewModel.onConclusionChange(it) }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone View",
    group = "Phone"
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Phone Portrait",
    device = "spec:width=411dp,height=891dp,orientation=landscape",
    group = "Phone"
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tablet View",
    device = Devices.TABLET,
    group = "Table"
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Tablet Portrait",
    device = "spec:width=800dp,height=1280dp,dpi=240,orientation=portrait",
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
