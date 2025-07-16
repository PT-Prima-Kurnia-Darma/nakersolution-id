package com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.features.report.ptp.PTPViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MotorDieselScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: PTPViewModel = koinViewModel()
) {
    val uiState by viewModel.motorDieselUiState.collectAsStateWithLifecycle()
    val report = uiState.inspectionReport
    val onDataChange = viewModel::onMotorDieselReportChange

    var showSummaryDialog by remember { mutableStateOf(false) }
    var showRequirementDialog by remember { mutableStateOf(false) }
    var showNoiseDialog by remember { mutableStateOf(false) }
    var showLightingDialog by remember { mutableStateOf(false) }

    if (showSummaryDialog) {
        AddMotorDieselStringDialog(
            title = "Tambah Poin Kesimpulan",
            label = "Poin Kesimpulan",
            onDismissRequest = { showSummaryDialog = false },
            onConfirm = { viewModel.addMotorDieselConclusionSummary(it); showSummaryDialog = false }
        )
    }

    if (showRequirementDialog) {
        AddMotorDieselStringDialog(
            title = "Tambah Poin Persyaratan",
            label = "Poin Persyaratan",
            onDismissRequest = { showRequirementDialog = false },
            onConfirm = { viewModel.addMotorDieselConclusionRequirement(it); showRequirementDialog = false }
        )
    }

    if (showNoiseDialog) {
        AddNoisePointDialog(
            onDismissRequest = { showNoiseDialog = false },
            onConfirm = { viewModel.addMotorDieselNoisePoint(it); showNoiseDialog = false }
        )
    }

    if (showLightingDialog) {
        AddLightingPointDialog(
            onDismissRequest = { showLightingDialog = false },
            onConfirm = { viewModel.addMotorDieselLightingPoint(it); showLightingDialog = false }
        )
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        item {
            val data = report
            MotorDieselExpandableSection("DATA UTAMA", true) {
                MotorDieselFormTextField(label = "Jenis Pemeriksaan", value = data.examinationType, onValueChange = { onDataChange(data.copy(examinationType = it)) })
                MotorDieselFormTextField(label = "Jenis Pesawat", value = data.equipmentType, onValueChange = { onDataChange(data.copy(equipmentType = it)) })
            }
        }

        item {
            val data = report.generalData
            val onDataChanged: (DieselMotorGeneralData) -> Unit = { onDataChange(report.copy(generalData = it)) }
            MotorDieselExpandableSection("DATA UMUM", false) {
                MotorDieselFormTextField("Perusahaan Pemilik", data.ownerName) { onDataChanged(data.copy(ownerName = it)) }
                MotorDieselFormTextField("Alamat Pemilik", data.ownerAddress) { onDataChanged(data.copy(ownerAddress = it)) }
                MotorDieselFormTextField("Perusahaan Pemakai", data.userInCharge) { onDataChanged(data.copy(userInCharge = it)) }
                MotorDieselFormTextField("Alamat Pemakai", data.userAddressInCharge) { onDataChanged(data.copy(userAddressInCharge = it)) }
                MotorDieselFormTextField("Pengurus / Penanggung Jawab", data.subcontractorPersonInCharge) { onDataChanged(data.copy(subcontractorPersonInCharge = it)) }
                MotorDieselFormTextField("Lokasi Unit", data.unitLocation) { onDataChanged(data.copy(unitLocation = it)) }
                MotorDieselFormTextField("Jenis Pesawat / Tipe", data.driveType) { onDataChanged(data.copy(driveType = it)) }
                MotorDieselFormTextField("Merk / Model", data.brandType) { onDataChanged(data.copy(brandType = it)) }
                MotorDieselFormTextField("No. Seri / No. Unit", data.serialNumberUnitNumber) { onDataChanged(data.copy(serialNumberUnitNumber = it)) }
                MotorDieselFormTextField("Perusahaan Pembuat / Pemasang", data.manufacturer) { onDataChanged(data.copy(manufacturer = it)) }
                MotorDieselFormTextField("Lokasi / Tahun Pembuatan", data.locationAndYearOfManufacture) { onDataChanged(data.copy(locationAndYearOfManufacture = it)) }
                MotorDieselFormTextField("Kapasitas / Putaran", data.capacityRpm) { onDataChanged(data.copy(capacityRpm = it)) }
                MotorDieselFormTextField("Digunakan Untuk", data.intendedUse) { onDataChanged(data.copy(intendedUse = it)) }
                MotorDieselFormTextField("No.SKP / Bidang PJK3", data.pjk3SkpNo) { onDataChanged(data.copy(pjk3SkpNo = it)) }
                MotorDieselFormTextField("No.SKP / Bidang AK3", data.ak3SkpNo) { onDataChanged(data.copy(ak3SkpNo = it)) }
                MotorDieselFormTextField("Klasifikasi (Portable/Stationer)", data.classification) { onDataChanged(data.copy(classification = it)) }
                MotorDieselFormTextField("Nomor Izin Pemakaian / Penerbit", data.usagePermitNumber) { onDataChanged(data.copy(usagePermitNumber = it)) }
                MotorDieselFormTextField("Nama Operator", data.operatorName) { onDataChanged(data.copy(operatorName = it)) }
                MotorDieselFormTextField("Data Riwayat Motor Diesel", data.equipmentHistory) { onDataChanged(data.copy(equipmentHistory = it)) }
            }
        }

        item {
            val data = report.technicalData
            val onDataChanged: (DieselMotorTechnicalData) -> Unit = { onDataChange(report.copy(technicalData = it)) }
            MotorDieselExpandableSection("DATA TEKNIK") {
                MotorDieselExpandableSubSection("Motor Diesel") {
                    val motor = data.dieselMotor
                    MotorDieselFormTextField("Merk / Model", motor.brandModel) { onDataChanged(data.copy(dieselMotor = motor.copy(brandModel = it))) }
                    MotorDieselFormTextField("Pabrik Pembuat / Negara / Tahun", motor.manufacturer) { onDataChanged(data.copy(dieselMotor = motor.copy(manufacturer = it))) }
                    MotorDieselFormTextField("Klasifikasi", motor.classification) { onDataChanged(data.copy(dieselMotor = motor.copy(classification = it))) }
                    MotorDieselFormTextField("Nomor Seri", motor.serialNumber) { onDataChanged(data.copy(dieselMotor = motor.copy(serialNumber = it))) }
                    MotorDieselFormTextField("Daya / Putaran", motor.powerRpm) { onDataChanged(data.copy(dieselMotor = motor.copy(powerRpm = it))) }
                    MotorDieselFormTextField("Tenaga Mula", motor.startingPower) { onDataChanged(data.copy(dieselMotor = motor.copy(startingPower = it))) }
                    MotorDieselFormTextField("Jumlah Silinder", motor.cylinderCount) { onDataChanged(data.copy(dieselMotor = motor.copy(cylinderCount = it))) }
                }
                HorizontalDivider()
                MotorDieselExpandableSubSection("Generator") {
                    val gen = data.generator
                    MotorDieselFormTextField("Merk / Type", gen.brandType) { onDataChanged(data.copy(generator = gen.copy(brandType = it))) }
                    MotorDieselFormTextField("Pabrik Pembuat / Negara / Tahun", gen.manufacturer) { onDataChanged(data.copy(generator = gen.copy(manufacturer = it))) }
                    MotorDieselFormTextField("Nomor Seri", gen.serialNumber) { onDataChanged(data.copy(generator = gen.copy(serialNumber = it))) }
                    MotorDieselFormTextField("Daya", gen.power) { onDataChanged(data.copy(generator = gen.copy(power = it))) }
                    MotorDieselFormTextField("Frekuensi", gen.frequency) { onDataChanged(data.copy(generator = gen.copy(frequency = it))) }
                    MotorDieselFormTextField("Putaran", gen.rpm) { onDataChanged(data.copy(generator = gen.copy(rpm = it))) }
                    MotorDieselFormTextField("Tegangan", gen.voltage) { onDataChanged(data.copy(generator = gen.copy(voltage = it))) }
                    MotorDieselFormTextField("Faktor Daya (cos φ)", gen.powerFactor) { onDataChanged(data.copy(generator = gen.copy(powerFactor = it))) }
                    MotorDieselFormTextField("Arus", gen.current) { onDataChanged(data.copy(generator = gen.copy(current = it))) }
                }
            }
        }

        item {
            val data = report.visualInspection
            val onDataChanged: (DieselMotorVisualInspection) -> Unit = { onDataChange(report.copy(visualInspection = it)) }
            MotorDieselExpandableSection("PEMERIKSAAN VISUAL") {
                Text("KONSTRUKSI DASAR", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Pondasi Dasar", data.baseConstructionFoundation) { onDataChanged(data.copy(baseConstructionFoundation = it)) }
                DieselConditionInput("Rumah Diesel", data.baseConstructionDieselHousing) { onDataChanged(data.copy(baseConstructionDieselHousing = it)) }
                DieselConditionInput("Support / Penopang", data.baseConstructionSupport) { onDataChanged(data.copy(baseConstructionSupport = it)) }
                DieselConditionInput("Anchor Bolt", data.baseConstructionAnchorBolt) { onDataChanged(data.copy(baseConstructionAnchorBolt = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("STRUKTUR KONSTRUKSI", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Tangki Harian", data.structureDailyTank) { onDataChanged(data.copy(structureDailyTank = it)) }
                DieselConditionInput("Muffler", data.structureMuffler) { onDataChanged(data.copy(structureMuffler = it)) }
                DieselConditionInput("Bejana Angin", data.structureAirVessel) { onDataChanged(data.copy(structureAirVessel = it)) }
                DieselConditionInput("Panel", data.structurePanel) { onDataChanged(data.copy(structurePanel = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("SISTEM PELUMASAN", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Oli", data.lubeSystemOil) { onDataChanged(data.copy(lubeSystemOil = it)) }
                DieselConditionInput("Oil Strainer / Carter", data.lubeSystemOilStrainer) { onDataChanged(data.copy(lubeSystemOilStrainer = it)) }
                DieselConditionInput("Oil Cooler", data.lubeSystemOilCooler) { onDataChanged(data.copy(lubeSystemOilCooler = it)) }
                DieselConditionInput("Oli Filter", data.lubeSystemOilFilter) { onDataChanged(data.copy(lubeSystemOilFilter = it)) }
                DieselConditionInput("By Pass Filter", data.lubeSystemByPassFilter) { onDataChanged(data.copy(lubeSystemByPassFilter = it)) }
                DieselConditionInput("Safety Valve", data.lubeSystemSafetyValve) { onDataChanged(data.copy(lubeSystemSafetyValve = it)) }
                DieselConditionInput("Packing", data.lubeSystemPacking) { onDataChanged(data.copy(lubeSystemPacking = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("SISTEM BAHAN BAKAR", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Tangki Harian", data.fuelSystemDailyTank) { onDataChanged(data.copy(fuelSystemDailyTank = it)) }
                DieselConditionInput("Fuel Injector (FIJ)", data.fuelSystemFuelInjector) { onDataChanged(data.copy(fuelSystemFuelInjector = it)) }
                DieselConditionInput("Sambungan-sambungan", data.fuelSystemConnections) { onDataChanged(data.copy(fuelSystemConnections = it)) }
                DieselConditionInput("Float Tank", data.fuelSystemFloatTank) { onDataChanged(data.copy(fuelSystemFloatTank = it)) }
                DieselConditionInput("Fuel Filter", data.fuelSystemFuelFilter) { onDataChanged(data.copy(fuelSystemFuelFilter = it)) }
                DieselConditionInput("Fuel Injector Pump (FIP)", data.fuelSystemFuelInjectorPump) { onDataChanged(data.copy(fuelSystemFuelInjectorPump = it)) }
                DieselConditionInput("Magnetic Screen", data.fuelSystemMagneticScreen) { onDataChanged(data.copy(fuelSystemMagneticScreen = it)) }
                DieselConditionInput("Governor", data.fuelSystemGovernor) { onDataChanged(data.copy(fuelSystemGovernor = it)) }
                DieselConditionInput("Throttle Shaft", data.fuelSystemThrottleShaft) { onDataChanged(data.copy(fuelSystemThrottleShaft = it)) }
                DieselConditionInput("Regulator", data.fuelSystemRegulator) { onDataChanged(data.copy(fuelSystemRegulator = it)) }
                DieselConditionInput("Shut Off Valve", data.fuelSystemShutOffValve) { onDataChanged(data.copy(fuelSystemShutOffValve = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("ALAT BANTU MENGHIDUPKAN MESIN", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Feed Pump", data.startingSystemFeedPump) { onDataChanged(data.copy(startingSystemFeedPump = it)) }
                DieselConditionInput("Fuel Valve", data.startingSystemFuelValve) { onDataChanged(data.copy(startingSystemFuelValve = it)) }
                DieselConditionInput("Priming Ring Pump", data.startingSystemPrimingPump) { onDataChanged(data.copy(startingSystemPrimingPump = it)) }
                DieselConditionInput("Heater Plug", data.startingSystemHeaterPlug) { onDataChanged(data.copy(startingSystemHeaterPlug = it)) }
                DieselConditionInput("Heater Switch", data.startingSystemHeaterSwitch) { onDataChanged(data.copy(startingSystemHeaterSwitch = it)) }
                DieselConditionInput("Pre Heater", data.startingSystemPreHeater) { onDataChanged(data.copy(startingSystemPreHeater = it)) }
                DieselConditionInput("Water Signal", data.startingSystemWaterSignal) { onDataChanged(data.copy(startingSystemWaterSignal = it)) }
                DieselConditionInput("Starting Switch", data.startingSystemSwitch) { onDataChanged(data.copy(startingSystemSwitch = it)) }
                DieselConditionInput("Kutub-kutub Baterai", data.startingSystemBatteryPoles) { onDataChanged(data.copy(startingSystemBatteryPoles = it)) }
                DieselConditionInput("Thermostart Tank", data.startingSystemThermostartTank) { onDataChanged(data.copy(startingSystemThermostartTank = it)) }
                DieselConditionInput("Thermostart", data.startingSystemThermostart) { onDataChanged(data.copy(startingSystemThermostart = it)) }
                DieselConditionInput("Heater Signal", data.startingSystemHeaterSignal) { onDataChanged(data.copy(startingSystemHeaterSignal = it)) }
                DieselConditionInput("Thermostart Switch", data.startingSystemThermostartSwitch) { onDataChanged(data.copy(startingSystemThermostartSwitch = it)) }
                DieselConditionInput("Glow Plug", data.startingSystemGlowPlug) { onDataChanged(data.copy(startingSystemGlowPlug = it)) }
                DieselConditionInput("Engine Speed Sensor", data.startingSystemSpeedSensor) { onDataChanged(data.copy(startingSystemSpeedSensor = it)) }
                DieselConditionInput("Service Meter", data.startingSystemServiceMeter) { onDataChanged(data.copy(startingSystemServiceMeter = it)) }
                DieselConditionInput("Water Temperature Sensor", data.startingSystemTempSensor) { onDataChanged(data.copy(startingSystemTempSensor = it)) }
                DieselConditionInput("Motor Stater", data.startingSystemMotor) { onDataChanged(data.copy(startingSystemMotor = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("SISTEM PENDINGIN", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Cooling Water", data.coolingSystemWater) { onDataChanged(data.copy(coolingSystemWater = it)) }
                DieselConditionInput("Baut-baut Pengikat", data.coolingSystemBolts) { onDataChanged(data.copy(coolingSystemBolts = it)) }
                DieselConditionInput("Klem Pengikat", data.coolingSystemClamps) { onDataChanged(data.copy(coolingSystemClamps = it)) }
                DieselConditionInput("Radiator", data.coolingSystemRadiator) { onDataChanged(data.copy(coolingSystemRadiator = it)) }
                DieselConditionInput("Thermostat", data.coolingSystemThermostat) { onDataChanged(data.copy(coolingSystemThermostat = it)) }
                DieselConditionInput("Kipas / Fan", data.coolingSystemFan) { onDataChanged(data.copy(coolingSystemFan = it)) }
                DieselConditionInput("Pelindung Kipas", data.coolingSystemFanGuard) { onDataChanged(data.copy(coolingSystemFanGuard = it)) }
                DieselConditionInput("Putaran Kipas", data.coolingSystemFanRotation) { onDataChanged(data.copy(coolingSystemFanRotation = it)) }
                DieselConditionInput("Bantalan / Dudukan", data.coolingSystemBearing) { onDataChanged(data.copy(coolingSystemBearing = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("SISTEM SIRKULASI UDARA", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Pre-Cleaner", data.airCirculationPreCleaner) { onDataChanged(data.copy(airCirculationPreCleaner = it)) }
                DieselConditionInput("Dust Indicator", data.airCirculationDustIndicator) { onDataChanged(data.copy(airCirculationDustIndicator = it)) }
                DieselConditionInput("Air Cleaner / Filter", data.airCirculationAirCleaner) { onDataChanged(data.copy(airCirculationAirCleaner = it)) }
                DieselConditionInput("Turbo Charger", data.airCirculationTurboCharger) { onDataChanged(data.copy(airCirculationTurboCharger = it)) }
                DieselConditionInput("Klem-klem Pengikat", data.airCirculationClamps) { onDataChanged(data.copy(airCirculationClamps = it)) }
                DieselConditionInput("After Cooler", data.airCirculationAfterCooler) { onDataChanged(data.copy(airCirculationAfterCooler = it)) }
                DieselConditionInput("Muffler", data.airCirculationMuffler) { onDataChanged(data.copy(airCirculationMuffler = it)) }
                DieselConditionInput("Silincer", data.airCirculationSilencer) { onDataChanged(data.copy(airCirculationSilencer = it)) }
                DieselConditionInput("Peredam Panas", data.airCirculationHeatDamper) { onDataChanged(data.copy(airCirculationHeatDamper = it)) }
                DieselConditionInput("Baut-baut Pengikat", data.airCirculationBolts) { onDataChanged(data.copy(airCirculationBolts = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("BAGIAN-BAGIAN UTAMA", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Baut-baut Pengikat Peredam", data.mainPartsDamperBolts) { onDataChanged(data.copy(mainPartsDamperBolts = it)) }
                DieselConditionInput("Support / Penopang", data.mainPartsSupport) { onDataChanged(data.copy(mainPartsSupport = it)) }
                DieselConditionInput("Rumah Fly Wheel", data.mainPartsFlyWheelHousing) { onDataChanged(data.copy(mainPartsFlyWheelHousing = it)) }
                DieselConditionInput("Fly Wheel", data.mainPartsFlyWheel) { onDataChanged(data.copy(mainPartsFlyWheel = it)) }
                DieselConditionInput("Peredam Getaran", data.mainPartsVibrationDamper) { onDataChanged(data.copy(mainPartsVibrationDamper = it)) }
                DieselConditionInput("Sabuk dan Puli", data.mainPartsBeltAndPulley) { onDataChanged(data.copy(mainPartsBeltAndPulley = it)) }
                DieselConditionInput("Crankshaft / Poros Engkol", data.mainPartsCrankshaft) { onDataChanged(data.copy(mainPartsCrankshaft = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("GENERATOR", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Hubungan Terminal Generator", data.generatorTerminalConnection) { onDataChanged(data.copy(generatorTerminalConnection = it)) }
                DieselConditionInput("Kabel dari Generator ke Panel Board", data.generatorCableToPanel) { onDataChanged(data.copy(generatorCableToPanel = it)) }
                DieselConditionInput("Panel Board", data.generatorPanelBoard) { onDataChanged(data.copy(generatorPanelBoard = it)) }
                DieselConditionInput("Ampere Meter", data.generatorAmpereMeter) { onDataChanged(data.copy(generatorAmpereMeter = it)) }
                DieselConditionInput("Volt Meter", data.generatorVoltMeter) { onDataChanged(data.copy(generatorVoltMeter = it)) }
                DieselConditionInput("Frequency", data.generatorFrequencyMeter) { onDataChanged(data.copy(generatorFrequencyMeter = it)) }
                DieselConditionInput("Circuit Breaker", data.generatorCircuitBreaker) { onDataChanged(data.copy(generatorCircuitBreaker = it)) }
                DieselConditionInput("Saklar On-Off", data.generatorOnOffSwitch) { onDataChanged(data.copy(generatorOnOffSwitch = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("TRANSMISI", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Roda Gigi", data.transmissionGear) { onDataChanged(data.copy(transmissionGear = it)) }
                DieselConditionInput("Transmisi Sabuk", data.transmissionBelt) { onDataChanged(data.copy(transmissionBelt = it)) }
                DieselConditionInput("Transmisi Rantai", data.transmissionChain) { onDataChanged(data.copy(transmissionChain = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("MAIN DISTRIBUTOR PANEL", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Hubungan Kabel ke MDP", data.mdpCableConnection) { onDataChanged(data.copy(mdpCableConnection = it)) }
                DieselConditionInput("Kondisi MDP", data.mdpCondition) { onDataChanged(data.copy(mdpCondition = it)) }
                DieselConditionInput("Ampere Meter", data.mdpAmpereMeter) { onDataChanged(data.copy(mdpAmpereMeter = it)) }
                DieselConditionInput("Volt Meter", data.mdpVoltMeter) { onDataChanged(data.copy(mdpVoltMeter = it)) }
                DieselConditionInput("Main Circuit Breaker", data.mdpMainCircuitBreaker) { onDataChanged(data.copy(mdpMainCircuitBreaker = it)) }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Text("SAFETY DEVICE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                DieselConditionInput("Grounding", data.safetyGrounding) { onDataChanged(data.copy(safetyGrounding = it)) }
                DieselConditionInput("Penyalur Petir", data.safetyLightningArrester) { onDataChanged(data.copy(safetyLightningArrester = it)) }
                DieselConditionInput("Emergency Stop", data.safetyEmergencyStop) { onDataChanged(data.copy(safetyEmergencyStop = it)) }
                DieselConditionInput("Governor", data.safetyGovernor) { onDataChanged(data.copy(safetyGovernor = it)) }
                DieselConditionInput("Thermostat", data.safetyThermostat) { onDataChanged(data.copy(safetyThermostat = it)) }
                DieselConditionInput("Water Signal", data.safetyWaterSignal) { onDataChanged(data.copy(safetyWaterSignal = it)) }
                DieselConditionInput("Pelindung Kipas", data.safetyFanGuard) { onDataChanged(data.copy(safetyFanGuard = it)) }
                DieselConditionInput("Silincer", data.safetySilencer) { onDataChanged(data.copy(safetySilencer = it)) }
                DieselConditionInput("Peredam Getaran", data.safetyVibrationDamper) { onDataChanged(data.copy(safetyVibrationDamper = it)) }
                DieselConditionInput("Circuit Breaker", data.safetyCircuitBreaker) { onDataChanged(data.copy(safetyCircuitBreaker = it)) }
                DieselConditionInput("AVR (Automatic Voltage Regulator)", data.safetyAvr) { onDataChanged(data.copy(safetyAvr = it)) }
            }
        }

        item {
            val data = report.testingAndMeasurement
            val onDataChanged: (DieselMotorTestingAndMeasurement) -> Unit = { onDataChange(report.copy(testingAndMeasurement = it)) }
            MotorDieselExpandableSection("PENGUJIAN & PENGUKURAN") {
                MotorDieselExpandableSubSection("Pengujian NDT") {
                    val ndt = data.ndtTests
                    val onNdtChanged: (DieselMotorNdtTests) -> Unit = { onDataChanged(data.copy(ndtTests = it)) }
                    DieselTestResultInput("Putaran Poros Diesel (Rpm)", ndt.shaftRpm) { onNdtChanged(ndt.copy(shaftRpm = it)) }
                    DieselTestResultInput("Pengujian Sambungan Las", ndt.weldJoint) { onNdtChanged(ndt.copy(weldJoint = it)) }
                    DieselTestResultInput("Kebisingan", ndt.noise) { onNdtChanged(ndt.copy(noise = it)) }
                    DieselTestResultInput("Pencahayaan", ndt.lighting) { onNdtChanged(ndt.copy(lighting = it)) }
                    DieselTestResultInput("Uji Beban (Load Test)", ndt.loadTest) { onNdtChanged(ndt.copy(loadTest = it)) }
                }
                HorizontalDivider()
                MotorDieselExpandableSubSection("Pengujian Safety Device") {
                    val safety = data.safetyDeviceTests
                    val onSafetyChanged: (DieselMotorSafetyDeviceTests) -> Unit = { onDataChanged(data.copy(safetyDeviceTests = it)) }
                    DieselTestResultInput("Governor", safety.governor) { onSafetyChanged(safety.copy(governor = it)) }
                    DieselTestResultInput("Emergency Stop", safety.emergencyStop) { onSafetyChanged(safety.copy(emergencyStop = it)) }
                    DieselTestResultInput("Tahanan Pembumian (Grounding)", safety.grounding) { onSafetyChanged(safety.copy(grounding = it)) }
                    DieselTestResultInput("Panel-panel Indikator", safety.panelIndicators) { onSafetyChanged(safety.copy(panelIndicators = it)) }
                    DieselTestResultInput("Pressure Gauge", safety.pressureGauge) { onSafetyChanged(safety.copy(pressureGauge = it)) }
                    DieselTestResultInput("Temperature Indicator", safety.temperatureIndicator) { onSafetyChanged(safety.copy(temperatureIndicator = it)) }
                    DieselTestResultInput("Water Indicator", safety.waterIndicator) { onSafetyChanged(safety.copy(waterIndicator = it)) }
                    DieselTestResultInput("Katup-katup Pengaman", safety.safetyValves) { onSafetyChanged(safety.copy(safetyValves = it)) }
                    DieselTestResultInput("Radiator", safety.radiator) { onSafetyChanged(safety.copy(radiator = it)) }
                }
                HorizontalDivider()
                MotorDieselExpandableSubSection("Pengukuran Komponen Listrik") {
                    val electrical = data.electricalMeasurements
                    val onElectricalChanged: (DieselMotorElectricalMeasurements) -> Unit = { onDataChanged(data.copy(electricalMeasurements = it)) }
                    Text("Panel Control", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    val drawing = electrical.panelControl
                    MotorDieselFormTextField("KA", drawing.ka) { onElectricalChanged(electrical.copy(panelControl = drawing.copy(ka = it))) }
                    MotorDieselFormTextField("Tegangan R-S", drawing.voltageRS) { onElectricalChanged(electrical.copy(panelControl = drawing.copy(voltageRS = it))) }
                    MotorDieselFormTextField("Tegangan R-T", drawing.voltageRT) { onElectricalChanged(electrical.copy(panelControl = drawing.copy(voltageRT = it))) }
                    MotorDieselFormTextField("Tegangan S-T", drawing.voltageST) { onElectricalChanged(electrical.copy(panelControl = drawing.copy(voltageST = it))) }
                    MotorDieselFormTextField("Tegangan R-N", drawing.voltageRN) { onElectricalChanged(electrical.copy(panelControl = drawing.copy(voltageRN = it))) }
                    MotorDieselFormTextField("Tegangan R-G", drawing.voltageRG) { onElectricalChanged(electrical.copy(panelControl = drawing.copy(voltageRG = it))) }
                    MotorDieselFormTextField("Tegangan N-G", drawing.voltageNG) { onElectricalChanged(electrical.copy(panelControl = drawing.copy(voltageNG = it))) }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("Power Info", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    val power = electrical.powerInfo
                    MotorDieselFormTextField("Frekuensi (Hz)", power.frequency) { onElectricalChanged(electrical.copy(powerInfo = power.copy(frequency = it))) }
                    MotorDieselFormTextField("Cos φ", power.cosQ) { onElectricalChanged(electrical.copy(powerInfo = power.copy(cosQ = it))) }
                    MotorDieselFormTextField("Arus R (A)", power.ampereR) { onElectricalChanged(electrical.copy(powerInfo = power.copy(ampereR = it))) }
                    MotorDieselFormTextField("Arus S (A)", power.ampereS) { onElectricalChanged(electrical.copy(powerInfo = power.copy(ampereS = it))) }
                    MotorDieselFormTextField("Arus T (A)", power.ampereT) { onElectricalChanged(electrical.copy(powerInfo = power.copy(ampereT = it))) }
                    MotorDieselFormTextField("Keterangan", power.remarks) { onElectricalChanged(electrical.copy(powerInfo = power.copy(remarks = it))) }
                }
            }
        }

        item {
            val data = report.mcbCalculation
            val onDataChanged: (DieselMotorMcbCalculation) -> Unit = { onDataChange(report.copy(mcbCalculation = it)) }
            MotorDieselExpandableSection("PERHITUNGAN MCB") {
                Text("Diketahui:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                val known = data.known
                MotorDieselFormTextField("Phase", known.phase) { onDataChanged(data.copy(known = known.copy(phase = it))) }
                MotorDieselFormTextField("Tegangan (V)", known.voltage) { onDataChanged(data.copy(known = known.copy(voltage = it))) }
                MotorDieselFormTextField("Cos φ", known.cosQ) { onDataChanged(data.copy(known = known.copy(cosQ = it))) }
                MotorDieselFormTextField("Daya Genset (KVA)", known.generatorPowerKva) { onDataChanged(data.copy(known = known.copy(generatorPowerKva = it))) }
                MotorDieselFormTextField("Daya Genset (KW)", known.generatorPowerKw) { onDataChanged(data.copy(known = known.copy(generatorPowerKw = it))) }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text("Perhitungan:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                val calc = data.calculation
                Text("Rumus: ${calc.formula}", style = MaterialTheme.typography.bodySmall)
                MotorDieselFormTextField("Hasil Perhitungan (A)", calc.resultA) { onDataChanged(data.copy(calculation = calc.copy(resultA = it))) }
                MotorDieselFormTextField("Kebutuhan (A) (Hasil x 125%)", calc.requiredAmps) { onDataChanged(data.copy(calculation = calc.copy(requiredAmps = it))) }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                MotorDieselFormTextField("Kesimpulan / Saran MCB", data.conclusion) { onDataChanged(data.copy(conclusion = it)) }
            }
        }

        item {
            val data = report.noiseMeasurement
            val onDataChanged : (DieselMotorNoiseMeasurement) -> Unit = { onDataChange(report.copy(noiseMeasurement = it)) }
            MotorDieselExpandableSection("PENGUKURAN KEBISINGAN") {
                MotorDieselFormTextField("Lokasi Pengukuran", data.location) { onDataChanged(data.copy(location = it)) }
                MotorDieselFormTextField("Standar (NAB)", data.analysis.standard) { onDataChanged(data.copy(analysis = data.analysis.copy(standard = it))) }
                MotorDieselFormTextField("Hasil Analisa", data.analysis.result) { onDataChanged(data.copy(analysis = data.analysis.copy(result = it))) }

                FilledTonalButton(onClick = { showNoiseDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Titik Pengukuran")
                }

                data.measurements.forEachIndexed { index, point ->
                    MotorDieselListItemWithDelete(onDelete = { viewModel.deleteMotorDieselNoisePoint(index) }) {
                        Text("Titik ${point.point}: ${point.valueDb} dB", modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }

        item {
            val data = report.lightingMeasurement
            val onDataChanged: (DieselMotorLightingMeasurement) -> Unit = { onDataChange(report.copy(lightingMeasurement = it)) }
            MotorDieselExpandableSection("PENGUKURAN PENCAHAYAAN") {
                MotorDieselFormTextField("Lokasi Pengukuran", data.location) { onDataChanged(data.copy(location = it)) }
                MotorDieselFormTextField("Standar (NAB)", data.analysis.standard) { onDataChanged(data.copy(analysis = data.analysis.copy(standard = it))) }
                MotorDieselFormTextField("Hasil Analisa", data.analysis.result) { onDataChanged(data.copy(analysis = data.analysis.copy(result = it))) }

                FilledTonalButton(onClick = { showLightingDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Titik Pengukuran")
                }

                data.measurements.forEachIndexed { index, point ->
                    MotorDieselListItemWithDelete(onDelete = { viewModel.deleteMotorDieselLightingPoint(index) }) {
                        Text("Titik ${point.point}: ${point.valueLux} Lux", modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }

        item {
            val data = report.conclusion
            MotorDieselExpandableSection("KESIMPULAN") {
                FilledTonalButton(onClick = { showSummaryDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Poin Kesimpulan")
                }
                data.summary.forEachIndexed { index, item ->
                    MotorDieselListItemWithDelete(onDelete = { viewModel.removeMotorDieselConclusionSummary(index) }) {
                        Text(item, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }

        item {
            val data = report.conclusion
            MotorDieselExpandableSection("PERSYARATAN") {
                FilledTonalButton(onClick = { showRequirementDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Text("Tambah Poin Persyaratan")
                }
                data.requirements.forEachIndexed { index, item ->
                    MotorDieselListItemWithDelete(onDelete = { viewModel.removeMotorDieselConclusionRequirement(index) }) {
                        Text(item, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}


//region Reusable Composables for MotorDieselScreen

@Composable
private fun MotorDieselListItemWithDelete(
    onDelete: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.fillMaxWidth(), content = content)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Hapus Item")
        }
    }
}

@Composable
private fun MotorDieselExpandableSection(
    title: String,
    initiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(initiallyExpanded) }
    Card(
        modifier = Modifier.fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium).clickable { expanded = !expanded }.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = if (expanded) "Ciutkan" else "Lebarkan")
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
private fun MotorDieselExpandableSubSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth().animateContentSize(
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        ).padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium).clickable { expanded = !expanded }.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = if (expanded) "Ciutkan" else "Lebarkan")
            }
        }
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun MotorDieselFormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
private fun DieselConditionInput(
    label: String,
    value: DieselMotorConditionResult,
    onValueChange: (DieselMotorConditionResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isGood = true)) }) {
                RadioButton(selected = value.isGood, onClick = null)
                Text("Baik")
            }
            Spacer(Modifier.width(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onValueChange(value.copy(isGood = false)) }) {
                RadioButton(selected = !value.isGood, onClick = null)
                Text("Buruk")
            }
        }
        MotorDieselFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}

@Composable
private fun DieselTestResultInput(
    label: String,
    value: DieselMotorTestResult,
    onValueChange: (DieselMotorTestResult) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        MotorDieselFormTextField("Hasil", value.result) { onValueChange(value.copy(result = it)) }
        MotorDieselFormTextField("Keterangan", value.remarks) { onValueChange(value.copy(remarks = it)) }
    }
}
//endregion