package com.nakersolutionid.nakersolutionid.features.report.ee.eskalator

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nakersolutionid.nakersolutionid.di.previewModule
import com.nakersolutionid.nakersolutionid.features.report.ee.EEViewModel
import com.nakersolutionid.nakersolutionid.ui.theme.NakersolutionidTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplicationPreview

/**
 * The main content of the report, displayed in a LazyColumn for performance.
 *
 * @param data The general data for the escalator report.
 * @param onDataChange A lambda to be invoked when the general data changes.
 * @param modifier Modifier for this composable.
 */
@Composable
fun EskalatorScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    verticalArrangement: Arrangement.Vertical,
    viewModel: EEViewModel = koinViewModel(),
) {
    val uiState by viewModel.eskalatorUiState.collectAsStateWithLifecycle()
    val data = uiState.eskalatorData

    val onDataChange: (EskalatorGeneralData) -> Unit = viewModel::onDataChange

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        // General Section
        item {
            ExpandableSection(title = "Informasi Umum", initiallyExpanded = true) {
                FormTextField(
                    label = "Jenis Eskalator",
                    value = data.equipmentType,
                    onValueChange = { onDataChange(data.copy(equipmentType = it)) }
                )
                FormTextField(
                    label = "Jenis Pemeriksaan",
                    value = data.examinationType,
                    onValueChange = { onDataChange(data.copy(examinationType = it)) }
                )
            }
        }

        // Company Data Section
        item {
            val companyData = data.companyData
            ExpandableSection(title = "Data Perusahaan") {
                FormTextField(
                    label = "Nama Perusahaan / Gedung",
                    value = companyData.companyOrBuildingName,
                    onValueChange = { onDataChange(data.copy(companyData = companyData.copy(companyOrBuildingName = it))) }
                )
                FormTextField(
                    label = "Alamat Pemakaian",
                    value = companyData.usageAddress,
                    onValueChange = { onDataChange(data.copy(companyData = companyData.copy(usageAddress = it))) }
                )
                FormTextField(
                    label = "Objek K3 & Nomor",
                    value = companyData.safetyObjectTypeAndNumber,
                    onValueChange = { onDataChange(data.copy(companyData = companyData.copy(safetyObjectTypeAndNumber = it))) }
                )
                FormTextField(
                    label = "Digunakan Untuk",
                    value = companyData.intendedUse,
                    onValueChange = { onDataChange(data.copy(companyData = companyData.copy(intendedUse = it))) }
                )
                FormTextField(
                    label = "Izin Pemakaian",
                    value = companyData.usagePermit,
                    onValueChange = { onDataChange(data.copy(companyData = companyData.copy(usagePermit = it))) }
                )
                FormTextField(
                    label = "Tanggal Pemeriksaan",
                    value = companyData.inspectionDate,
                    onValueChange = { onDataChange(data.copy(companyData = companyData.copy(inspectionDate = it))) }
                )
            }
        }

        // Technical Data Section
        item {
            val technicalData = data.technicalData
            ExpandableSection(title = "Data Teknis") {
                FormTextField(label = "Pabrik Pembuat", value = technicalData.manufacturer, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(manufacturer = it))) })
                FormTextField(label = "Merk", value = technicalData.brand, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(brand = it))) })
                FormTextField(label = "Negara & Tahun Pembuatan", value = technicalData.countryAndYear, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(countryAndYear = it))) })
                FormTextField(label = "Nomor Seri", value = technicalData.serialNumber, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(serialNumber = it))) })
                FormTextField(label = "Alat Angkut", value = technicalData.transports, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(transports = it))) })
                FormTextField(label = "Kapasitas", value = technicalData.capacity, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(capacity = it))) })
                FormTextField(label = "Tinggi Angkat", value = technicalData.liftHeight, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(liftHeight = it))) })
                FormTextField(label = "Kecepatan", value = technicalData.speed, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(speed = it))) })
                FormTextField(label = "Jenis Penggerak", value = technicalData.driveType, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(driveType = it))) })
                FormTextField(label = "Arus Motor", value = technicalData.motorCurrent, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(motorCurrent = it))) })
                FormTextField(label = "Daya Motor", value = technicalData.motorPower, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(motorPower = it))) })
                FormTextField(label = "Alat Pengaman", value = technicalData.safetyDevices, onValueChange = { onDataChange(data.copy(technicalData = technicalData.copy(safetyDevices = it))) })
            }
        }

        // Inspection and Testing Section
        item {
            val inspection = data.inspectionAndTesting
            ExpandableSection(title = "Pemeriksaan dan Pengujian") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Frame and Machine Room
                    ExpandableSubSection(title = "Kerangka, Ruang Mesin & Pit") {
                        val d = inspection.frameAndMachineRoom
                        ResultStatusInput(label = "Kerangka", provision = "≥ 30 N/cm 2 , ditopang oleh ≥ 2 balok pendukung, defleksi < 0,1%, penyambung tipe pasak mempunyai kekuatan torsi antara 2 s.d. 88 kg/meter, faktor keamanan ≥ 2,5", value = d.frame, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(frameAndMachineRoom = d.copy(frame = it)))) })
                        ResultStatusInput(label = "Balok Penyangga", provision = "Dilapisi karet peredam", value = d.supportBeams, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(frameAndMachineRoom = d.copy(supportBeams = it)))) })
                        ResultStatusInput(label = "Kondisi Ruang Mesin", provision = "Bersih", value = d.machineRoomCondition, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(frameAndMachineRoom = d.copy(machineRoomCondition = it)))) })
                        ResultStatusInput(label = "Jarak Bebas Ruang Mesin", provision = "< 0,3 m²", value = d.machineRoomClearance, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(frameAndMachineRoom = d.copy(machineRoomClearance = it)))) })
                        ResultStatusInput(label = "Penerangan Ruang Mesin", provision = "≥ 100 lux", value = d.machineRoomLighting, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(frameAndMachineRoom = d.copy(machineRoomLighting = it)))) })
                        ResultStatusInput(label = "Plat Penutup Mesin", provision = "Tersedia dan kuat", value = d.machineCoverPlate, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(frameAndMachineRoom = d.copy(machineCoverPlate = it)))) })
                        ResultStatusInput(label = "Kondisi Pit", provision = "Bersih", value = d.pitCondition, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(frameAndMachineRoom = d.copy(pitCondition = it)))) })
                        ResultStatusInput(label = "Jarak Bebas Pit", provision = "≥ 0,3 m²", value = d.pitClearance, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(frameAndMachineRoom = d.copy(pitClearance = it)))) })
                        ResultStatusInput(label = "Plat Penutup Step di Pit", provision = "Ada & Kuat", value = d.pitStepCoverPlate, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(frameAndMachineRoom = d.copy(pitStepCoverPlate = it)))) })
                    }
                    HorizontalDivider()
                    // Drive Equipment
                    ExpandableSubSection(title = "Peralatan Penggerak") {
                        val d = inspection.driveEquipment
                        ResultStatusInput(label = "Mesin Penggerak", provision = "Hanya menggerakkan 1 eskalator dilengkapi elektromekanis yang otomatis", value = d.driveMachine, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(driveEquipment = d.copy(driveMachine = it)))) })
                        ResultStatusInput(label = "Kecepatan (≤ 30°)", provision = "Kecepatan maksimal 0,75 meter/detik", value = d.speedUnder30Degrees, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(driveEquipment = d.copy(speedUnder30Degrees = it)))) })
                        ResultStatusInput(label = "Kecepatan (30°-35°)", provision = "Kecepatan maksimal 0,5 meter/detik", value = d.speed30to35Degrees, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(driveEquipment = d.copy(speed30to35Degrees = it)))) })
                        ResultStatusInput(label = "Kecepatan Travelator", provision = "Kecepatan maksimal 0,75 meter/detik, dapat ditingkatkan maksimal 0,9 meter/detik", value = d.travelatorSpeed, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(driveEquipment = d.copy(travelatorSpeed = it)))) })
                        ResultStatusInput(label = "Jarak Berhenti (0.5 m/s)", provision = "200 mm < jarak pemberhentian < 1000 mm", value = d.stoppingDistance0_5, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(driveEquipment = d.copy(stoppingDistance0_5 = it)))) })
                        ResultStatusInput(label = "Jarak Berhenti (0.75 m/s)", provision = "350 mm < jarak pemberhentian < 1500 mm", value = d.stoppingDistance0_75, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(driveEquipment = d.copy(stoppingDistance0_75 = it)))) })
                        ResultStatusInput(label = "Jarak Berhenti (0.90 m/s)", provision = "550 mm < jarak pemberhentian < 1700 mm", value = d.stoppingDistance0_90, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(driveEquipment = d.copy(stoppingDistance0_90 = it)))) })
                        ResultStatusInput(label = "Rantai Penggerak", provision = "Pelat baja yang dikeling", value = d.driveChain, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(driveEquipment = d.copy(driveChain = it)))) })
                        ResultStatusInput(label = "Kekuatan Putus Rantai", provision = "≥ 140 kg tiap lembar rantai", value = d.chainBreakingStrength, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(driveEquipment = d.copy(chainBreakingStrength = it)))) })
                    }
                    HorizontalDivider()
                    // Steps or Pallets
                    ExpandableSubSection(title = "Anak Tangga / Pallet") {
                        val d = inspection.stepsOrPallets
                        ResultStatusInput(label = "Bahan Step", provision = "Palet baja, baja tuang yang dianeling atau alumunium", value = d.stepMaterial, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(stepsOrPallets = d.copy(stepMaterial = it)))) })
                        ResultStatusInput(label = "Dimensi Step", provision = "Lebar (depth) ≥ 400mm, Panjang (width) ≥ 560mm, Tinggi < 240mm", value = d.stepDimensions, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(stepsOrPallets = d.copy(stepDimensions = it)))) })
                        ResultStatusInput(label = "Dimensi Pallet", provision = "Lebar (depth) ≥ 150mm, Panjang (width) ≥ 560mm", value = d.palletDimensions, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(stepsOrPallets = d.copy(palletDimensions = it)))) })
                        ResultStatusInput(label = "Permukaan Step", provision = "Terbuat dari bahan yang padat, rata, tidak licin, dan kisi-kisi dengan tebal ≥ 3mm", value = d.stepSurface, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(stepsOrPallets = d.copy(stepSurface = it)))) })
                        ResultStatusInput(label = "Kerataan Step", provision = "≥ 600mm", value = d.stepLevelness, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(stepsOrPallets = d.copy(stepLevelness = it)))) })
                        ResultStatusInput(label = "Sikat Skirt", provision = "Terpasang sepanjang pelindung bawah", value = d.skirtBrush, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(stepsOrPallets = d.copy(skirtBrush = it)))) })
                        ResultStatusInput(label = "Roda Step", provision = "Mempunyai 4 buah roda atau 2 pasang roda dengan kondisi tidak pecah", value = d.stepWheels, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(stepsOrPallets = d.copy(stepWheels = it)))) })
                    }
                    HorizontalDivider()
                    // Landing Area
                    ExpandableSubSection(title = "Bidang Landas") {
                        val d = inspection.landingArea
                        ResultStatusInput(label = "Plat Pendaratan", provision = "Terpasang berderet dan Dikencangkan dengan sekrup", value = d.landingPlates, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(landingArea = d.copy(landingPlates = it)))) })
                        ResultStatusInput(label = "Gigi Sisir", provision = "Terbuat dari bahan yang mudah patah, dan dapat masuk ke dalam alur anak tangga/palet", value = d.combTeeth, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(landingArea = d.copy(combTeeth = it)))) })
                        ResultStatusInput(label = "Kondisi Sisir", provision = "Boleh kondisi patah maksimal 2", value = d.combCondition, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(landingArea = d.copy(combCondition = it)))) })
                        ResultStatusInput(label = "Penutup Pendaratan", provision = "Dari bahan yang kuat dan tidak licin, dan dilengkapi saklar pemutus", value = d.landingCover, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(landingArea = d.copy(landingCover = it)))) })
                        ResultStatusInput(label = "Area Akses Pendaratan", provision = "- Ruang bebas ≥ 160mm, - Panjang ≥ 2500mm, - Jika bidang landas lebih besar dari 2000, ruang bebas 2 x lebar ban pegangan ditambah 160 mm", value = d.landingAccessArea, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(landingArea = d.copy(landingAccessArea = it)))) })
                    }
                    HorizontalDivider()
                    // Balustrade
                    ExpandableSubSection(title = "Pagar Pelindung") {
                        val d = inspection.balustrade
                        val p = d.balustradePanel
                        ExpandableSubSection(title = "Panel Balustrade", modifier = Modifier.padding(horizontal = 12.dp)) {
                            ResultStatusInput(label = "Material", provision = "Dari baja dan kuat", value = p.material, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(balustrade = d.copy(balustradePanel = p.copy(material = it))))) })
                            ResultStatusInput(label = "Tinggi", provision = "750 < tinggi < 1100mm", value = p.height, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(balustrade = d.copy(balustradePanel = p.copy(height = it))))) })
                            ResultStatusInput(label = "Tekanan Samping", provision = "> 58,5 kg/m²", value = p.sidePressure, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(balustrade = d.copy(balustradePanel = p.copy(sidePressure = it))))) })
                            ResultStatusInput(label = "Tekanan Vertikal", provision = "> 73 kg/m²", value = p.verticalPressure, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(balustrade = d.copy(balustradePanel = p.copy(verticalPressure = it))))) })
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        ResultStatusInput(label = "Panel Skirt", provision = "Dari bahan tahan benturan, tahan gesekan, permukaan licin dan tidak mudah aus", value = d.skirtPanel, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(balustrade = d.copy(skirtPanel = it)))) })
                        ResultStatusInput(label = "Fleksibilitas Panel Skirt", provision = "< 4mm jika diberi tekanan 50kg", value = d.skirtPanelFlexibility, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(balustrade = d.copy(skirtPanelFlexibility = it)))) })
                        ResultStatusInput(label = "Jarak Bebas Step ke Skirt", provision = "< 4mm dan jumlah jarak antar keduanya < 7mm", value = d.stepToSkirtClearance, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(balustrade = d.copy(stepToSkirtClearance = it)))) })
                    }
                    HorizontalDivider()
                    // Handrail
                    ExpandableSubSection(title = "Ban Pegangan") {
                        val d = inspection.handrail
                        ResultStatusInput(label = "Kondisi Handrail", provision = "Kuat, tidak cacat, terbuat dari karet Vulkanisir", value = d.handrailCondition, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(handrail = d.copy(handrailCondition = it)))) })
                        ResultStatusInput(label = "Sinkronisasi Kecepatan Handrail", provision = "Harus sama dan searah atau < 2% Terhadap anak tangga", value = d.handrailSpeedSynchronization, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(handrail = d.copy(handrailSpeedSynchronization = it)))) })
                        ResultStatusInput(label = "Lebar Handrail", provision = "70 – 100mm", value = d.handrailWidth, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(handrail = d.copy(handrailWidth = it)))) })
                    }
                    HorizontalDivider()
                    // Runway
                    ExpandableSubSection(title = "Lintasan Luncur (Void)") {
                        val d = inspection.runway
                        ResultStatusInput(label = "Kekuatan & Posisi Balok", provision = "Disesuaikan dengan spesifikasi", value = d.beamStrengthAndPosition, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(runway = d.copy(beamStrengthAndPosition = it)))) })
                        ResultStatusInput(label = "Kondisi Dinding Pit", provision = "Kedap air", value = d.pitWallCondition, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(runway = d.copy(pitWallCondition = it)))) })
                        ResultStatusInput(label = "Penutup Rangka Escalator", provision = "Tertutup dan dari bahan yang tidak Mudah pecah", value = d.escalatorFrameEnclosure, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(runway = d.copy(escalatorFrameEnclosure = it)))) })
                        ResultStatusInput(label = "Penerangan", provision = "> 50 lux", value = d.lighting, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(runway = d.copy(lighting = it)))) })
                        ResultStatusInput(label = "Jarak Bebas Headroom", provision = "> 2300 mm", value = d.headroomClearance, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(runway = d.copy(headroomClearance = it)))) })
                        ResultStatusInput(label = "Jarak Balustrade ke Objek", provision = "< 120 mm", value = d.balustradeToObjectClearance, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(runway = d.copy(balustradeToObjectClearance = it)))) })
                        ResultStatusInput(label = "Tinggi Alat Anti-Panjat", provision = "> 100 mm dari permukaan ban Pegangan", value = d.antiClimbDeviceHeight, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(runway = d.copy(antiClimbDeviceHeight = it)))) })
                        ResultStatusInput(label = "Penempatan Ornamen", provision = "Berjarak > 80 mm dan tinggi > 2100 mm", value = d.ornamentPlacement, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(runway = d.copy(ornamentPlacement = it)))) })
                        ResultStatusInput(label = "Jarak Bebas Outdoor", provision = "Jarak antara pelindung luar dengan balok struktur > 400 mm", value = d.outdoorClearance, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(runway = d.copy(outdoorClearance = it)))) })
                    }
                    HorizontalDivider()
                    // Safety Equipment
                    ExpandableSubSection(title = "Peralatan Pengaman") {
                        val d = inspection.safetyEquipment
                        ResultStatusInput(label = "Kunci Kontrol Operasi", provision = "Tersedia", value = d.operationControlKey, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(operationControlKey = it)))) })
                        ResultStatusInput(label = "Saklar Stop Darurat", provision = "Tersedia", value = d.emergencyStopSwitch, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(emergencyStopSwitch = it)))) })
                        ResultStatusInput(label = "Pengaman Rantai Step", provision = "Tersedia", value = d.stepChainSafetyDevice, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(stepChainSafetyDevice = it)))) })
                        ResultStatusInput(label = "Pengaman Rantai Penggerak", provision = "Tersedia", value = d.driveChainSafetyDevice, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(driveChainSafetyDevice = it)))) })
                        ResultStatusInput(label = "Pengaman Step", provision = "Tersedia", value = d.stepSafetyDevice, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(stepSafetyDevice = it)))) })
                        ResultStatusInput(label = "Pengaman Handrail", provision = "Tersedia", value = d.handrailSafetyDevice, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(handrailSafetyDevice = it)))) })
                        ResultStatusInput(label = "Pengaman Pembalikan Arah", provision = "Tersedia", value = d.reversalStopDevice, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(reversalStopDevice = it)))) })
                        ResultStatusInput(label = "Pelindung Masuk Handrail", provision = "Tersedia", value = d.handrailEntryGuard, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(handrailEntryGuard = it)))) })
                        ResultStatusInput(label = "Pengaman Plat Sisir", provision = "Tersedia", value = d.combPlateSafetyDevice, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(combPlateSafetyDevice = it)))) })
                        ResultStatusInput(label = "Sikat Inner Decking", provision = "Tersedia", value = d.innerDeckingBrush, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(innerDeckingBrush = it)))) })
                        ResultStatusInput(label = "Tombol Stop", provision = "Posisi mudah dicapai dan mempunyai jarak antar tombol < 3000 mm, mempunyai tanda yang jelas", value = d.stopButtons, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(safetyEquipment = d.copy(stopButtons = it)))) })
                    }
                    HorizontalDivider()
                    // Electrical Installation
                    ExpandableSubSection(title = "Instalasi Listrik") {
                        val d = inspection.electricalInstallation
                        ResultStatusInput(label = "Standar Instalasi", provision = "SNI dan standar internasional", value = d.installationStandard, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(electricalInstallation = d.copy(installationStandard = it)))) })
                        ResultStatusInput(label = "Panel Listrik", provision = "Panel khusus untuk eskalator", value = d.electricalPanel, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(electricalInstallation = d.copy(electricalPanel = it)))) })
                        ResultStatusInput(label = "Kabel Grounding", provision = "- Penampang ≥ 6mm² ≤ 5 Ω (ohm)", value = d.groundingCable, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(electricalInstallation = d.copy(groundingCable = it)))) })
                        ResultStatusInput(label = "Koneksi Alarm Kebakaran", provision = "Terhubung dan beroperasi otomatis", value = d.fireAlarmConnection, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(electricalInstallation = d.copy(fireAlarmConnection = it)))) })
                    }
                    HorizontalDivider()
                    // Outdoor Specifics
                    ExpandableSubSection(title = "Khusus Escalator Outdoor") {
                        val d = inspection.outdoorSpecifics
                        ResultStatusInput(label = "Pompa Air Pit", provision = "Tersedia", value = d.pitWaterPump, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(outdoorSpecifics = d.copy(pitWaterPump = it)))) })
                        ResultStatusInput(label = "Komponen Tahan Cuaca", provision = "Tahan air, suhu/cuaca", value = d.weatherproofComponents, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(outdoorSpecifics = d.copy(weatherproofComponents = it)))) })
                    }
                    HorizontalDivider()
                    // User Safety Signage
                    ExpandableSubSection(title = "Keselamatan Pengguna") {
                        val d = inspection.userSafetySignage
                        ResultStatusInput(label = "Dilarang Membawa Barang Besar", provision = "Tersedia", value = d.noBulkyItems, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(userSafetySignage = d.copy(noBulkyItems = it)))) })
                        ResultStatusInput(label = "Dilarang Melompat", provision = "Tersedia", value = d.noJumping, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(userSafetySignage = d.copy(noJumping = it)))) })
                        ResultStatusInput(label = "Anak-anak Harus Diawasi", provision = "Tersedia", value = d.unattendedChildren, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(userSafetySignage = d.copy(unattendedChildren = it)))) })
                        ResultStatusInput(label = "Dilarang Membawa Troli/Stroller", provision = "Tersedia", value = d.noTrolleysOrStrollers, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(userSafetySignage = d.copy(noTrolleysOrStrollers = it)))) })
                        ResultStatusInput(label = "Dilarang Bersandar", provision = "Tersedia", value = d.noLeaning, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(userSafetySignage = d.copy(noLeaning = it)))) })
                        ResultStatusInput(label = "Dilarang Menginjak Skirt", provision = "Tersedia", value = d.noSteppingOnSkirt, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(userSafetySignage = d.copy(noSteppingOnSkirt = it)))) })
                        ResultStatusInput(label = "Peringatan Alas Kaki Lunak", provision = "Tersedia", value = d.softSoleFootwearWarning, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(userSafetySignage = d.copy(softSoleFootwearWarning = it)))) })
                        ResultStatusInput(label = "Dilarang Duduk di Tangga", provision = "Tersedia", value = d.noSittingOnSteps, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(userSafetySignage = d.copy(noSittingOnSteps = it)))) })
                        ResultStatusInput(label = "Pegang Handrail", provision = "Tersedia", value = d.holdHandrail, onValueChange = { onDataChange(data.copy(inspectionAndTesting = inspection.copy(userSafetySignage = d.copy(holdHandrail = it)))) })
                    }
                }
            }
        }

        // Testing Summary Section
        item {
            val summary = data.testingEscalator
            ExpandableSection(title = "Ringkasan Pengujian") {
                OutlinedTextField(
                    value = summary,
                    onValueChange = { onDataChange(data.copy(conclusion = it)) },
                    label = { Text("Kesimpulan / Rekomendasi") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = MaterialTheme.shapes.medium,
                )
            }
        }

        // Conclusion Section
        item {
            ExpandableSection(title = "Kesimpulan") {
                OutlinedTextField(
                    value = data.conclusion,
                    onValueChange = { onDataChange(data.copy(conclusion = it)) },
                    label = { Text("Kesimpulan / Rekomendasi") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = MaterialTheme.shapes.medium,
                )
            }
        }
    }
}

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
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier
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
    provision: String = "",
    value: EskalatorResultStatus,
    onValueChange: (EskalatorResultStatus) -> Unit
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
            supportingText = { if (provision.isNotBlank()) Text(text = "Ketentuan: $provision") },
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
                checked = value.status,
                onCheckedChange = { onValueChange(value.copy(status = it)) }
            )
            Text("Memenuhi")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Phone View", group = "Phone")
@Composable
private fun EskalatorScreenPreview() {
    KoinApplicationPreview(application = {
        modules(previewModule)
    }) {
        NakersolutionidTheme {
            Scaffold { paddingValues ->
                EskalatorScreen(
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