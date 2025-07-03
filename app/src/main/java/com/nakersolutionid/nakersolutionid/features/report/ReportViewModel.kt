package com.nakersolutionid.nakersolutionid.features.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.elevator.*
import com.nakersolutionid.nakersolutionid.utils.DataMapper.toDomain
import com.nakersolutionid.nakersolutionid.utils.DataMapper.toNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(ElevatorUiState())
    val uiState = _uiState.asStateFlow()

    fun test() {
        viewModelScope.launch {
            val dummyElevatorUiState = ElevatorUiState(
                nameOfInspectionType = "Pemeriksaan dan Pengujian K3",
                subNameOfInspectionType = "Elevator",
                typeInspection = "Pemeriksaan Berkala",
                eskOrElevType = "Elevator Penumpang",
                conclusion = "Elevator dinyatakan LAIK K3 dengan beberapa catatan perbaikan minor yang harus ditindaklanjuti.",
                generalData = GeneralDataUiState(
                    ownerName = "PT Properti Megah Abadi",
                    ownerAddress = "Jl. Gatot Subroto No. 12, Jakarta Selatan",
                    nameUsageLocation = "Gedung Perkantoran Thamrin Tower",
                    addressUsageLocation = "Jl. M.H. Thamrin Kav. 8, Jakarta Pusat",
                    manufacturerOrInstaller = "PT Liftindo Prima Teknik",
                    elevatorType = "Traction (Gearless)",
                    brandOrType = "Kone MonoSpace",
                    countryAndYear = "Finlandia / 2021",
                    serialNumber = "ELEV-JKT-2021-007",
                    capacity = "1250 kg / 18 orang",
                    speed = "180 m/min",
                    floorsServed = "30 lantai (LG, G, 1-28)",
                    permitNumber = "SKP-123/M/DJPPK/VII/2023",
                    inspectionDate = "2025-07-03"
                ),
                technicalDocumentInspection = TechnicalDocumentInspectionUiState(
                    designDrawing = "Tersedia dan sesuai",
                    technicalCalculation = "Tersedia dan sesuai",
                    materialCertificate = "Lengkap",
                    controlPanelDiagram = "Tersedia pada panel",
                    asBuiltDrawing = "Tersedia",
                    componentCertificates = "Lengkap",
                    safeWorkProcedure = "Tersedia dan dipahami oleh teknisi"
                ),
                inspectionAndTesting = InspectionAndTestingUiState(
                    machineRoomAndMachinery = MachineRoomAndMachineryUiState(
                        machineMounting = ResultStatusUiState("Dudukan mesin kokoh, tidak ada getaran berlebih", true),
                        mechanicalBrake = ResultStatusUiState("Berfungsi dengan baik saat uji pengereman", true),
                        electricalBrake = ResultStatusUiState("Switch dan kontaktor berfungsi normal", true),
                        machineRoomConstruction = ResultStatusUiState("Ruangan kuat, bersih, dan tahan api", true),
                        machineRoomClearance = ResultStatusUiState("Jarak bebas sesuai standar", true),
                        machineRoomImplementation = ResultStatusUiState("Penerangan 200 lux, sangat baik", true),
                        ventilation = ResultStatusUiState("Sirkulasi udara baik, suhu terjaga", true),
                        machineRoomDoor = ResultStatusUiState("Pintu tahan api, membuka keluar, terkunci", true),
                        mainPowerPanelPosition = ResultStatusUiState("Terpasang di dalam kamar mesin, mudah diakses", true),
                        rotatingPartsGuard = ResultStatusUiState("Semua bagian berputar terlindungi dengan baik", true),
                        ropeHoleGuard = ResultStatusUiState("Terpasang dengan ketinggian 50mm", true),
                        machineRoomAccessLadder = ResultStatusUiState("Tersedia tangga permanen yang kokoh", true),
                        floorLevelDifference = ResultStatusUiState("Tidak ada perbedaan ketinggian lantai", true),
                        fireExtinguisher = ResultStatusUiState("Tersedia APAR CO2 5kg, belum expired", true),
                        emergencyStopSwitch = ResultStatusUiState("Berfungsi dengan baik, memutus sirkuit", true),
                        machineRoomless = MachineRoomlessUiState(
                            panelPlacement = ResultStatusUiState("N/A", true),
                            lightingWorkArea = ResultStatusUiState("N/A", true),
                            lightingBetweenWorkArea = ResultStatusUiState("N/A", true),
                            manualBrakeRelease = ResultStatusUiState("N/A", true),
                            fireExtinguisherPlacement = ResultStatusUiState("N/A", true)
                        )
                    ),
                    suspensionRopesAndBelts = SuspensionRopesAndBeltsUiState(
                        condition = ResultStatusUiState("Tidak ada cacat, karat, atau putus", true),
                        chainUsage = ResultStatusUiState("N/A", true),
                        safetyFactor = ResultStatusUiState("Faktor keamanan 12x, sesuai standar", true),
                        ropeWithCounterweight = ResultStatusUiState("5 jalur, diameter 10mm, kondisi baik", true),
                        ropeWithoutCounterweight = ResultStatusUiState("N/A", true),
                        belt = ResultStatusUiState("N/A", true),
                        slackRopeDevice = ResultStatusUiState("N/A", true)
                    ),
                    drumsAndSheaves = DrumsAndSheavesUiState(
                        drumGrooves = ResultStatusUiState("Alur puli dalam kondisi baik, tidak aus", true),
                        passengerDrumDiameter = ResultStatusUiState("Rasio 40:1, sesuai", true),
                        governorDrumDiameter = ResultStatusUiState("Rasio 30:1, sesuai", true)
                    ),
                    hoistwayAndPit = HoistwayAndPitUiState(
                        construction = ResultStatusUiState("Struktur kokoh dan tertutup penuh", true),
                        walls = ResultStatusUiState("Permukaan halus, tidak ada tonjolan berbahaya", true),
                        inclinedElevatorTrackBed = ResultStatusUiState("N/A", true),
                        cleanliness = ResultStatusUiState("Pit bersih dari sampah dan ceceran oli", true),
                        lighting = ResultStatusUiState("Penerangan 100 lux, cukup", true),
                        emergencyDoorNonStop = ResultStatusUiState("Jarak antar pintu darurat sesuai standar", true),
                        emergencyDoorSize = ResultStatusUiState("Ukuran sesuai standar", true),
                        emergencyDoorSafetySwitch = ResultStatusUiState("Berfungsi dengan baik", true),
                        emergencyDoorBridge = ResultStatusUiState("N/A", true),
                        carTopClearance = ResultStatusUiState("Ruang bebas 1.8m, aman", true),
                        pitClearance = ResultStatusUiState("Ruang bebas 2.0m, aman", true),
                        pitLadder = ResultStatusUiState("Tersedia dan kokoh", true),
                        pitBelowWorkingArea = ResultStatusUiState("Lantai kuat, tidak ada ruang kerja di bawah", true),
                        pitAccessSwitch = ResultStatusUiState("Saklar berfungsi normal", true),
                        pitScreen = ResultStatusUiState("N/A", true),
                        hoistwayDoorLeaf = ResultStatusUiState("Tahan api 2 jam, sertifikat tersedia", true),
                        hoistwayDoorInterlock = ResultStatusUiState("Bekerja dengan baik di semua lantai", true),
                        floorLeveling = ResultStatusUiState("Akurasi perataan lantai < 10mm", true),
                        hoistwaySeparatorBeam = ResultStatusUiState("N/A", true),
                        inclinedElevatorStairs = ResultStatusUiState("N/A", true)
                    ),
                    car = CarUiState(
                        frame = ResultStatusUiState("Struktur rangka kokoh, tidak ada deformasi", true),
                        body = ResultStatusUiState("Dinding dan atap tertutup penuh", true),
                        wallHeight = ResultStatusUiState("2300mm", true),
                        floorArea = ResultStatusUiState("Sesuai dengan kapasitas yang tertera", true),
                        carAreaExpansion = ResultStatusUiState("Tidak ada perluasan area", true),
                        carDoor = ResultStatusUiState("Pintu otomatis berfungsi mulus", true),
                        carDoorSpecs = CarDoorSpecsUiState(
                            size = ResultStatusUiState("900mm x 2100mm", true),
                            lockAndSwitch = ResultStatusUiState("Berfungsi dengan baik", true),
                            sillClearance = ResultStatusUiState("Celah 25mm, sesuai", true)
                        ),
                        carToBeamClearance = ResultStatusUiState("N/A", true),
                        alarmBell = ResultStatusUiState("Berbunyi nyaring, terdengar jelas", true),
                        backupPowerARD = ResultStatusUiState("ARD berfungsi, membawa ke lantai terdekat saat tes", true),
                        intercom = ResultStatusUiState("Komunikasi 2 arah ke ruang security jelas", true),
                        ventilation = ResultStatusUiState("Fan berfungsi baik, sirkulasi udara cukup", true),
                        emergencyLighting = ResultStatusUiState("Menyala otomatis saat listrik padam", true),
                        operatingPanel = ResultStatusUiState("Semua tombol berfungsi, terdapat huruf braille", true),
                        carPositionIndicator = ResultStatusUiState("Indikator posisi akurat", true),
                        carSignage = CarSignageUiState(
                            manufacturerName = ResultStatusUiState("Terpasang", true),
                            loadCapacity = ResultStatusUiState("Terpasang jelas", true),
                            noSmokingSign = ResultStatusUiState("Terpasang", true),
                            overloadIndicator = ResultStatusUiState("Buzzer dan indikator berfungsi pada 110% beban", true),
                            doorOpenCloseButtons = ResultStatusUiState("Berfungsi", true),
                            floorButtons = ResultStatusUiState("Semua berfungsi", true),
                            alarmButton = ResultStatusUiState("Berfungsi", true),
                            twoWayIntercom = ResultStatusUiState("Berfungsi", true)
                        ),
                        carRoofStrength = ResultStatusUiState("Kuat, tidak ada deformasi saat diinjak", true),
                        carTopEmergencyExit = ResultStatusUiState("Saklar berfungsi, mudah dibuka dari luar", true),
                        carSideEmergencyExit = ResultStatusUiState("N/A", true),
                        carTopGuardRail = ResultStatusUiState("Ketinggian 1100mm, kokoh", true),
                        guardRailHeight300to850 = ResultStatusUiState("N/A", true),
                        guardRailHeightOver850 = ResultStatusUiState("Ketinggian 1100mm, sesuai", true),
                        carTopLighting = ResultStatusUiState("Lampu inspeksi di atas kereta berfungsi", true),
                        manualOperationButtons = ResultStatusUiState("Tombol inspeksi berfungsi normal", true),
                        carInterior = ResultStatusUiState("Bahan tidak mudah terbakar dan aman", true)
                    ),
                    governorAndSafetyBrake = GovernorAndSafetyBrakeUiState(
                        governorRopeClamp = ResultStatusUiState("Berfungsi saat tes overspeed", true),
                        governorSwitch = ResultStatusUiState("Berfungsi, memutus sirkuit", true),
                        safetyBrakeSpeed = ResultStatusUiState("Aktif pada 125% kecepatan normal", true),
                        safetyBrakeType = ResultStatusUiState("Tipe Progressive (Berangsur)", true),
                        safetyBrakeMechanism = ResultStatusUiState("Mekanikal, tidak bergantung listrik", true),
                        progressiveSafetyBrake = ResultStatusUiState("Berfungsi dengan baik, pengereman halus", true),
                        instantaneousSafetyBrake = ResultStatusUiState("N/A", true),
                        safetyBrakeOperation = ResultStatusUiState("Bekerja serempak pada kedua rel pemandu", true),
                        electricalCutoutSwitch = ResultStatusUiState("Berfungsi saat rem pengaman aktif", true),
                        limitSwitch = ResultStatusUiState("Normal limit dan final limit berfungsi", true),
                        overloadDevice = ResultStatusUiState("Berfungsi pada beban 110%", true)
                    ),
                    counterweightGuideRailsAndBuffers = CounterweightGuideRailsAndBuffersUiState(
                        counterweightMaterial = ResultStatusUiState("Besi cor (cast iron)", true),
                        counterweightGuardScreen = ResultStatusUiState("Terpasang dengan ketinggian 2.5m dari dasar pit", true),
                        guideRailConstruction = ResultStatusUiState("Rel pemandu kokoh, lurus, dan terlumasi", true),
                        bufferType = ResultStatusUiState("Tipe hidrolik untuk kereta dan counterweight", true),
                        bufferFunction = ResultStatusUiState("Bekerja dengan baik, meredam secara bertahap", true),
                        bufferSafetySwitch = ResultStatusUiState("Lampu indikator di intercom menyala redup, perlu pengecekan bohlam", false)
                    ),
                    electricalInstallation = ElectricalInstallationUiState(
                        installationStandard = ResultStatusUiState("Sesuai standar PUIL 2011", true),
                        electricalPanel = ResultStatusUiState("Panel khusus, bersih, dan diberi label", true),
                        backupPowerARD = ResultStatusUiState("Tersedia dan berfungsi", true),
                        groundingCable = ResultStatusUiState("Penampang sesuai, resistansi < 1 Ohm", true),
                        fireAlarmConnection = ResultStatusUiState("Terhubung ke sistem alarm kebakaran gedung", true),
                        fireServiceElevator = FireServiceElevatorUiState(
                            backupPower = ResultStatusUiState("N/A", true),
                            specialOperation = ResultStatusUiState("N/A", true),
                            fireSwitch = ResultStatusUiState("N/A", true),
                            label = ResultStatusUiState("N/A", true),
                            electricalFireResistance = ResultStatusUiState("N/A", true),
                            hoistwayWallFireResistance = ResultStatusUiState("N/A", true),
                            carSize = ResultStatusUiState("N/A", true),
                            doorSize = ResultStatusUiState("N/A", true),
                            travelTime = ResultStatusUiState("N/A", true),
                            evacuationFloor = ResultStatusUiState("N/A", true)
                        ),
                        accessibilityElevator = AccessibilityElevatorUiState(
                            operatingPanel = ResultStatusUiState("Terdapat huruf braille", true),
                            panelHeight = ResultStatusUiState("1100mm dari lantai", true),
                            doorOpenTime = ResultStatusUiState("Setting > 5 detik", true),
                            doorWidth = ResultStatusUiState("Bukaan 900mm, sesuai untuk kursi roda", true),
                            audioInformation = ResultStatusUiState("Tersedia informasi suara untuk setiap lantai", true),
                            label = ResultStatusUiState("Tersedia label disabilitas", true)
                        ),
                        seismicSensor = SeismicSensorUiState(
                            availability = ResultStatusUiState("Tersedia", true),
                            function = ResultStatusUiState("Berfungsi saat disimulasikan", true)
                        )
                    )
                )
            )
            /*reportUseCase.sendReport(dummyElevatorUiState.toDomain()).collect { result ->
                _uiState.update { it.copy(sendReportResult = result) }
            }*/
            /*Log.i("TAG", "ui: $dummyElevatorUiState")
            Log.i("TAG", "domain: ${dummyElevatorUiState.toDomain()}")
            Log.i("TAG", "network: ${dummyElevatorUiState.toDomain().toNetwork()}")*/
            reportUseCase.sendReport(dummyElevatorUiState.toDomain()).collect {
                _uiState.update { it.copy(sendReportResult = it.sendReportResult) }
            }
        }
    }
}