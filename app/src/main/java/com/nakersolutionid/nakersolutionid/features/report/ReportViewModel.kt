package com.nakersolutionid.nakersolutionid.features.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.elevator.*
import com.nakersolutionid.nakersolutionid.utils.DataMapper.toDomain
import com.nakersolutionid.nakersolutionid.utils.DataMapper.toNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(ElevatorUiState(
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

    )
    val uiState = _uiState.asStateFlow()

    fun onSaveClick() {
        val currentState = _uiState.value
        viewModelScope.launch {
            reportUseCase.sendReport(currentState.toDomain()).collect { result ->
                _uiState.update { it.copy(sendReportResult = result) }
            }
        }
    }

    fun onStateHandledSuccess() {
        _uiState.update { it.copy(sendReportResult = null) }
    }

    fun onStateHandledFailed() {
        _uiState.update { it.copy(sendReportResult = null) }
    }

    fun toggleLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    // region Top-Level Updaters
    fun onNameOfInspectionTypeChange(name: String) {
        _uiState.update { it.copy(nameOfInspectionType = name) }
    }

    fun onSubNameOfInspectionTypeChange(subName: String) {
        _uiState.update { it.copy(subNameOfInspectionType = subName) }
    }

    fun onTypeInspectionChange(type: String) {
        _uiState.update { it.copy(typeInspection = type) }
    }

    fun onEskOrElevTypeChange(type: String) {
        _uiState.update { it.copy(eskOrElevType = type) }
    }

    fun onConclusionChange(conclusion: String) {
        _uiState.update { it.copy(conclusion = conclusion) }
    }

    fun onSendReportResultChange(result: Resource<String>) {
        _uiState.update { it.copy(sendReportResult = result) }
    }
    // endregion

    // region General Data Updaters
    private fun updateGeneralData(
        transform: (GeneralDataUiState) -> GeneralDataUiState
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                generalData = transform(
                    currentState.generalData ?: GeneralDataUiState()
                )
            )
        }
    }

    fun onOwnerNameChange(name: String) {
        updateGeneralData { it.copy(ownerName = name) }
    }

    fun onOwnerAddressChange(address: String) {
        updateGeneralData { it.copy(ownerAddress = address) }
    }

    fun onNameUsageLocationChange(name: String) {
        updateGeneralData { it.copy(nameUsageLocation = name) }
    }

    fun onAddressUsageLocationChange(address: String) {
        updateGeneralData { it.copy(addressUsageLocation = address) }
    }

    fun onManufacturerOrInstallerChange(manufacturer: String) {
        updateGeneralData { it.copy(manufacturerOrInstaller = manufacturer) }
    }

    fun onElevatorTypeChange(type: String) {
        updateGeneralData { it.copy(elevatorType = type) }
    }

    fun onBrandOrTypeChange(brand: String) {
        updateGeneralData { it.copy(brandOrType = brand) }
    }

    fun onCountryAndYearChange(value: String) {
        updateGeneralData { it.copy(countryAndYear = value) }
    }

    fun onSerialNumberChange(serial: String) {
        updateGeneralData { it.copy(serialNumber = serial) }
    }

    fun onCapacityChange(capacity: String) {
        updateGeneralData { it.copy(capacity = capacity) }
    }

    fun onSpeedChange(speed: String) {
        updateGeneralData { it.copy(speed = speed) }
    }

    fun onFloorsServedChange(floors: String) {
        updateGeneralData { it.copy(floorsServed = floors) }
    }

    fun onPermitNumberChange(number: String) {
        updateGeneralData { it.copy(permitNumber = number) }
    }

    fun onInspectionDateChange(date: String) {
        updateGeneralData { it.copy(inspectionDate = date) }
    }
    // endregion

    // region Technical Document Inspection Updaters
    private fun updateTechnicalDocs(
        transform: (TechnicalDocumentInspectionUiState) -> TechnicalDocumentInspectionUiState
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                technicalDocumentInspection = transform(
                    currentState.technicalDocumentInspection
                        ?: TechnicalDocumentInspectionUiState()
                )
            )
        }
    }

    fun onDesignDrawingChange(value: String) {
        updateTechnicalDocs { it.copy(designDrawing = value) }
    }

    fun onTechnicalCalculationChange(value: String) {
        updateTechnicalDocs { it.copy(technicalCalculation = value) }
    }

    fun onMaterialCertificateChange(value: String) {
        updateTechnicalDocs { it.copy(materialCertificate = value) }
    }

    fun onControlPanelDiagramChange(value: String) {
        updateTechnicalDocs { it.copy(controlPanelDiagram = value) }
    }

    fun onAsBuiltDrawingChange(value: String) {
        updateTechnicalDocs { it.copy(asBuiltDrawing = value) }
    }

    fun onComponentCertificatesChange(value: String) {
        updateTechnicalDocs { it.copy(componentCertificates = value) }
    }

    fun onSafeWorkProcedureChange(value: String) {
        updateTechnicalDocs { it.copy(safeWorkProcedure = value) }
    }
    // endregion

    // region Inspection and Testing Updaters
    private fun updateInspectionAndTesting(
        transform: (InspectionAndTestingUiState) -> InspectionAndTestingUiState
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                inspectionAndTesting = transform(
                    currentState.inspectionAndTesting
                        ?: InspectionAndTestingUiState()
                )
            )
        }
    }

    // --- Helper for updating ResultStatusUiState ---
    private fun updateResultStatus(
        current: ResultStatusUiState?,
        result: String?,
        status: Boolean?
    ): ResultStatusUiState {
        val safeCurrent = current ?: ResultStatusUiState()
        return safeCurrent.copy(
            result = result ?: safeCurrent.result,
            status = status ?: safeCurrent.status
        )
    }

    // --- Machine Room and Machinery ---
    fun onMachineMountingChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineMounting = updateResultStatus(
                        mr.machineMounting,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onMechanicalBrakeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    mechanicalBrake = updateResultStatus(
                        mr.mechanicalBrake,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onElectricalBrakeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    electricalBrake = updateResultStatus(
                        mr.electricalBrake,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onMachineRoomConstructionChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomConstruction = updateResultStatus(
                        mr.machineRoomConstruction,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onMachineRoomClearanceChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomClearance = updateResultStatus(
                        mr.machineRoomClearance,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onMachineRoomImplementationChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomImplementation = updateResultStatus(
                        mr.machineRoomImplementation,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onMachineRoomVentilationChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    ventilation = updateResultStatus(mr.ventilation, result, status)
                )
            )
        }
    }

    fun onMachineRoomDoorChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomDoor = updateResultStatus(
                        mr.machineRoomDoor,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onMainPowerPanelPositionChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    mainPowerPanelPosition = updateResultStatus(
                        mr.mainPowerPanelPosition,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onRotatingPartsGuardChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    rotatingPartsGuard = updateResultStatus(
                        mr.rotatingPartsGuard,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onRopeHoleGuardChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    ropeHoleGuard = updateResultStatus(
                        mr.ropeHoleGuard,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onMachineRoomAccessLadderChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomAccessLadder = updateResultStatus(
                        mr.machineRoomAccessLadder,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onFloorLevelDifferenceChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    floorLevelDifference = updateResultStatus(
                        mr.floorLevelDifference,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onMachineRoomFireExtinguisherChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    fireExtinguisher = updateResultStatus(
                        mr.fireExtinguisher,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onMachineRoomEmergencyStopSwitchChange(
        result: String?,
        status: Boolean?
    ) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    emergencyStopSwitch = updateResultStatus(
                        mr.emergencyStopSwitch,
                        result,
                        status
                    )
                )
            )
        }
    }

    // --- Machine Roomless (Nested) ---
    fun onPanelPlacementChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            val mrl = mr.machineRoomless ?: MachineRoomlessUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomless = mrl.copy(
                        panelPlacement = updateResultStatus(
                            mrl.panelPlacement,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onLightingWorkAreaChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            val mrl = mr.machineRoomless ?: MachineRoomlessUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomless = mrl.copy(
                        lightingWorkArea = updateResultStatus(
                            mrl.lightingWorkArea,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onLightingBetweenWorkAreaChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            val mrl = mr.machineRoomless ?: MachineRoomlessUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomless = mrl.copy(
                        lightingBetweenWorkArea = updateResultStatus(
                            mrl.lightingBetweenWorkArea,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onManualBrakeReleaseChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            val mrl = mr.machineRoomless ?: MachineRoomlessUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomless = mrl.copy(
                        manualBrakeRelease = updateResultStatus(
                            mrl.manualBrakeRelease,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onFireExtinguisherPlacementChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val mr = i.machineRoomAndMachinery ?: MachineRoomAndMachineryUiState()
            val mrl = mr.machineRoomless ?: MachineRoomlessUiState()
            i.copy(
                machineRoomAndMachinery = mr.copy(
                    machineRoomless = mrl.copy(
                        fireExtinguisherPlacement = updateResultStatus(
                            mrl.fireExtinguisherPlacement,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    // --- Suspension Ropes and Belts ---
    fun onSuspensionRopeConditionChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val sr = i.suspensionRopesAndBelts
                ?: SuspensionRopesAndBeltsUiState()
            i.copy(
                suspensionRopesAndBelts = sr.copy(
                    condition = updateResultStatus(sr.condition, result, status)
                )
            )
        }
    }

    fun onChainUsageChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val sr = i.suspensionRopesAndBelts
                ?: SuspensionRopesAndBeltsUiState()
            i.copy(
                suspensionRopesAndBelts = sr.copy(
                    chainUsage = updateResultStatus(sr.chainUsage, result, status)
                )
            )
        }
    }

    fun onSafetyFactorChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val sr = i.suspensionRopesAndBelts
                ?: SuspensionRopesAndBeltsUiState()
            i.copy(
                suspensionRopesAndBelts = sr.copy(
                    safetyFactor = updateResultStatus(
                        sr.safetyFactor,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onRopeWithCounterweightChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val sr = i.suspensionRopesAndBelts
                ?: SuspensionRopesAndBeltsUiState()
            i.copy(
                suspensionRopesAndBelts = sr.copy(
                    ropeWithCounterweight = updateResultStatus(
                        sr.ropeWithCounterweight,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onRopeWithoutCounterweightChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val sr = i.suspensionRopesAndBelts
                ?: SuspensionRopesAndBeltsUiState()
            i.copy(
                suspensionRopesAndBelts = sr.copy(
                    ropeWithoutCounterweight = updateResultStatus(
                        sr.ropeWithoutCounterweight,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onBeltChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val sr = i.suspensionRopesAndBelts
                ?: SuspensionRopesAndBeltsUiState()
            i.copy(
                suspensionRopesAndBelts = sr.copy(
                    belt = updateResultStatus(sr.belt, result, status)
                )
            )
        }
    }

    fun onSlackRopeDeviceChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val sr = i.suspensionRopesAndBelts
                ?: SuspensionRopesAndBeltsUiState()
            i.copy(
                suspensionRopesAndBelts = sr.copy(
                    slackRopeDevice = updateResultStatus(
                        sr.slackRopeDevice,
                        result,
                        status
                    )
                )
            )
        }
    }

    // --- Drums and Sheaves ---
    fun onDrumGroovesChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ds = i.drumsAndSheaves ?: DrumsAndSheavesUiState()
            i.copy(
                drumsAndSheaves = ds.copy(
                    drumGrooves = updateResultStatus(ds.drumGrooves, result, status)
                )
            )
        }
    }

    fun onPassengerDrumDiameterChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ds = i.drumsAndSheaves ?: DrumsAndSheavesUiState()
            i.copy(
                drumsAndSheaves = ds.copy(
                    passengerDrumDiameter = updateResultStatus(
                        ds.passengerDrumDiameter,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onGovernorDrumDiameterChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ds = i.drumsAndSheaves ?: DrumsAndSheavesUiState()
            i.copy(
                drumsAndSheaves = ds.copy(
                    governorDrumDiameter = updateResultStatus(
                        ds.governorDrumDiameter,
                        result,
                        status
                    )
                )
            )
        }
    }

    // --- Hoistway and Pit ---
    fun onHoistwayConstructionChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    construction = updateResultStatus(
                        hp.construction,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onHoistwayWallsChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    walls = updateResultStatus(hp.walls, result, status)
                )
            )
        }
    }

    fun onInclinedElevatorTrackBedChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    inclinedElevatorTrackBed = updateResultStatus(
                        hp.inclinedElevatorTrackBed,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onHoistwayCleanlinessChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    cleanliness = updateResultStatus(hp.cleanliness, result, status)
                )
            )
        }
    }

    fun onHoistwayLightingChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    lighting = updateResultStatus(hp.lighting, result, status)
                )
            )
        }
    }

    fun onEmergencyDoorNonStopChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    emergencyDoorNonStop = updateResultStatus(
                        hp.emergencyDoorNonStop,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onEmergencyDoorSizeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    emergencyDoorSize = updateResultStatus(
                        hp.emergencyDoorSize,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onEmergencyDoorSafetySwitchChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    emergencyDoorSafetySwitch = updateResultStatus(
                        hp.emergencyDoorSafetySwitch,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onEmergencyDoorBridgeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    emergencyDoorBridge = updateResultStatus(
                        hp.emergencyDoorBridge,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarTopClearanceChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    carTopClearance = updateResultStatus(
                        hp.carTopClearance,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onPitClearanceChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    pitClearance = updateResultStatus(
                        hp.pitClearance,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onPitLadderChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    pitLadder = updateResultStatus(hp.pitLadder, result, status)
                )
            )
        }
    }

    fun onPitBelowWorkingAreaChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    pitBelowWorkingArea = updateResultStatus(
                        hp.pitBelowWorkingArea,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onPitAccessSwitchChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    pitAccessSwitch = updateResultStatus(
                        hp.pitAccessSwitch,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onPitScreenChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    pitScreen = updateResultStatus(hp.pitScreen, result, status)
                )
            )
        }
    }

    fun onHoistwayDoorLeafChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    hoistwayDoorLeaf = updateResultStatus(
                        hp.hoistwayDoorLeaf,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onHoistwayDoorInterlockChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    hoistwayDoorInterlock = updateResultStatus(
                        hp.hoistwayDoorInterlock,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onFloorLevelingChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    floorLeveling = updateResultStatus(
                        hp.floorLeveling,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onHoistwaySeparatorBeamChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    hoistwaySeparatorBeam = updateResultStatus(
                        hp.hoistwaySeparatorBeam,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onInclinedElevatorStairsChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val hp = i.hoistwayAndPit ?: HoistwayAndPitUiState()
            i.copy(
                hoistwayAndPit = hp.copy(
                    inclinedElevatorStairs = updateResultStatus(
                        hp.inclinedElevatorStairs,
                        result,
                        status
                    )
                )
            )
        }
    }

    // --- Car ---
    fun onCarFrameChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(car = car.copy(frame = updateResultStatus(car.frame, result, status)))
        }
    }

    fun onCarBodyChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(car = car.copy(body = updateResultStatus(car.body, result, status)))
        }
    }

    fun onCarWallHeightChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    wallHeight = updateResultStatus(
                        car.wallHeight,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarFloorAreaChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    floorArea = updateResultStatus(
                        car.floorArea,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarAreaExpansionChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    carAreaExpansion = updateResultStatus(
                        car.carAreaExpansion,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarDoorChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(car = car.copy(carDoor = updateResultStatus(car.carDoor, result, status)))
        }
    }

    fun onCarToBeamClearanceChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    carToBeamClearance = updateResultStatus(
                        car.carToBeamClearance,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarAlarmBellChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    alarmBell = updateResultStatus(
                        car.alarmBell,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarBackupPowerARDChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    backupPowerARD = updateResultStatus(
                        car.backupPowerARD,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarIntercomChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    intercom = updateResultStatus(
                        car.intercom,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarVentilationChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    ventilation = updateResultStatus(
                        car.ventilation,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarEmergencyLightingChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    emergencyLighting = updateResultStatus(
                        car.emergencyLighting,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarOperatingPanelChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    operatingPanel = updateResultStatus(
                        car.operatingPanel,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarPositionIndicatorChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    carPositionIndicator = updateResultStatus(
                        car.carPositionIndicator,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarRoofStrengthChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    carRoofStrength = updateResultStatus(
                        car.carRoofStrength,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarTopEmergencyExitChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    carTopEmergencyExit = updateResultStatus(
                        car.carTopEmergencyExit,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarSideEmergencyExitChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    carSideEmergencyExit = updateResultStatus(
                        car.carSideEmergencyExit,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarTopGuardRailChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    carTopGuardRail = updateResultStatus(
                        car.carTopGuardRail,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onGuardRailHeight300to850Change(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    guardRailHeight300to850 = updateResultStatus(
                        car.guardRailHeight300to850,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onGuardRailHeightOver850Change(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    guardRailHeightOver850 = updateResultStatus(
                        car.guardRailHeightOver850,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarTopLightingChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    carTopLighting = updateResultStatus(
                        car.carTopLighting,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onManualOperationButtonsChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    manualOperationButtons = updateResultStatus(
                        car.manualOperationButtons,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCarInteriorChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            i.copy(
                car = car.copy(
                    carInterior = updateResultStatus(
                        car.carInterior,
                        result,
                        status
                    )
                )
            )
        }
    }

    // --- Car Door Specs (Nested in Car) ---
    fun onCarDoorSizeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val specs = car.carDoorSpecs ?: CarDoorSpecsUiState()
            i.copy(
                car = car.copy(
                    carDoorSpecs = specs.copy(
                        size = updateResultStatus(
                            specs.size,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onCarDoorLockAndSwitchChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val specs = car.carDoorSpecs ?: CarDoorSpecsUiState()
            i.copy(
                car = car.copy(
                    carDoorSpecs = specs.copy(
                        lockAndSwitch = updateResultStatus(
                            specs.lockAndSwitch,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onCarDoorSillClearanceChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val specs = car.carDoorSpecs ?: CarDoorSpecsUiState()
            i.copy(
                car = car.copy(
                    carDoorSpecs = specs.copy(
                        sillClearance = updateResultStatus(
                            specs.sillClearance,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    // --- Car Signage (Nested in Car) ---
    fun onCarSignageManufacturerNameChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val signage = car.carSignage ?: CarSignageUiState()
            i.copy(
                car = car.copy(
                    carSignage = signage.copy(
                        manufacturerName = updateResultStatus(
                            signage.manufacturerName,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onCarSignageLoadCapacityChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val signage = car.carSignage ?: CarSignageUiState()
            i.copy(
                car = car.copy(
                    carSignage = signage.copy(
                        loadCapacity = updateResultStatus(
                            signage.loadCapacity,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onCarSignageNoSmokingSignChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val signage = car.carSignage ?: CarSignageUiState()
            i.copy(
                car = car.copy(
                    carSignage = signage.copy(
                        noSmokingSign = updateResultStatus(
                            signage.noSmokingSign,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onCarSignageOverloadIndicatorChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val signage = car.carSignage ?: CarSignageUiState()
            i.copy(
                car = car.copy(
                    carSignage = signage.copy(
                        overloadIndicator = updateResultStatus(
                            signage.overloadIndicator,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onCarSignageDoorOpenCloseButtonsChange(
        result: String?,
        status: Boolean?
    ) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val signage = car.carSignage ?: CarSignageUiState()
            i.copy(
                car = car.copy(
                    carSignage = signage.copy(
                        doorOpenCloseButtons = updateResultStatus(
                            signage.doorOpenCloseButtons,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onCarSignageFloorButtonsChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val signage = car.carSignage ?: CarSignageUiState()
            i.copy(
                car = car.copy(
                    carSignage = signage.copy(
                        floorButtons = updateResultStatus(
                            signage.floorButtons,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onCarSignageAlarmButtonChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val signage = car.carSignage ?: CarSignageUiState()
            i.copy(
                car = car.copy(
                    carSignage = signage.copy(
                        alarmButton = updateResultStatus(
                            signage.alarmButton,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onCarSignageTwoWayIntercomChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val car = i.car ?: CarUiState()
            val signage = car.carSignage ?: CarSignageUiState()
            i.copy(
                car = car.copy(
                    carSignage = signage.copy(
                        twoWayIntercom = updateResultStatus(
                            signage.twoWayIntercom,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    // --- Governor and Safety Brake ---
    fun onGovernorRopeClampChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    governorRopeClamp = updateResultStatus(
                        gsb.governorRopeClamp,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onGovernorSwitchChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    governorSwitch = updateResultStatus(
                        gsb.governorSwitch,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onSafetyBrakeSpeedChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    safetyBrakeSpeed = updateResultStatus(
                        gsb.safetyBrakeSpeed,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onSafetyBrakeTypeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    safetyBrakeType = updateResultStatus(
                        gsb.safetyBrakeType,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onSafetyBrakeMechanismChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    safetyBrakeMechanism = updateResultStatus(
                        gsb.safetyBrakeMechanism,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onProgressiveSafetyBrakeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    progressiveSafetyBrake = updateResultStatus(
                        gsb.progressiveSafetyBrake,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onInstantaneousSafetyBrakeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    instantaneousSafetyBrake = updateResultStatus(
                        gsb.instantaneousSafetyBrake,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onSafetyBrakeOperationChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    safetyBrakeOperation = updateResultStatus(
                        gsb.safetyBrakeOperation,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onElectricalCutoutSwitchChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    electricalCutoutSwitch = updateResultStatus(
                        gsb.electricalCutoutSwitch,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onLimitSwitchChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    limitSwitch = updateResultStatus(
                        gsb.limitSwitch,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onOverloadDeviceChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val gsb = i.governorAndSafetyBrake ?: GovernorAndSafetyBrakeUiState()
            i.copy(
                governorAndSafetyBrake = gsb.copy(
                    overloadDevice = updateResultStatus(
                        gsb.overloadDevice,
                        result,
                        status
                    )
                )
            )
        }
    }

    // --- Counterweight, Guide Rails and Buffers ---
    fun onCounterweightMaterialChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val cgb = i.counterweightGuideRailsAndBuffers
                ?: CounterweightGuideRailsAndBuffersUiState()
            i.copy(
                counterweightGuideRailsAndBuffers = cgb.copy(
                    counterweightMaterial = updateResultStatus(
                        cgb.counterweightMaterial,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onCounterweightGuardScreenChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val cgb = i.counterweightGuideRailsAndBuffers
                ?: CounterweightGuideRailsAndBuffersUiState()
            i.copy(
                counterweightGuideRailsAndBuffers = cgb.copy(
                    counterweightGuardScreen = updateResultStatus(
                        cgb.counterweightGuardScreen,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onGuideRailConstructionChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val cgb = i.counterweightGuideRailsAndBuffers
                ?: CounterweightGuideRailsAndBuffersUiState()
            i.copy(
                counterweightGuideRailsAndBuffers = cgb.copy(
                    guideRailConstruction = updateResultStatus(
                        cgb.guideRailConstruction,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onBufferTypeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val cgb = i.counterweightGuideRailsAndBuffers
                ?: CounterweightGuideRailsAndBuffersUiState()
            i.copy(
                counterweightGuideRailsAndBuffers = cgb.copy(
                    bufferType = updateResultStatus(cgb.bufferType, result, status)
                )
            )
        }
    }

    fun onBufferFunctionChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val cgb = i.counterweightGuideRailsAndBuffers
                ?: CounterweightGuideRailsAndBuffersUiState()
            i.copy(
                counterweightGuideRailsAndBuffers = cgb.copy(
                    bufferFunction = updateResultStatus(
                        cgb.bufferFunction,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onBufferSafetySwitchChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val cgb = i.counterweightGuideRailsAndBuffers
                ?: CounterweightGuideRailsAndBuffersUiState()
            i.copy(
                counterweightGuideRailsAndBuffers = cgb.copy(
                    bufferSafetySwitch = updateResultStatus(
                        cgb.bufferSafetySwitch,
                        result,
                        status
                    )
                )
            )
        }
    }

    // --- Electrical Installation ---
    fun onInstallationStandardChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    installationStandard = updateResultStatus(
                        ei.installationStandard,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onElectricalPanelChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    electricalPanel = updateResultStatus(
                        ei.electricalPanel,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onElectricalBackupPowerARDChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    backupPowerARD = updateResultStatus(
                        ei.backupPowerARD,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onGroundingCableChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    groundingCable = updateResultStatus(
                        ei.groundingCable,
                        result,
                        status
                    )
                )
            )
        }
    }

    fun onFireAlarmConnectionChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireAlarmConnection = updateResultStatus(
                        ei.fireAlarmConnection,
                        result,
                        status
                    )
                )
            )
        }
    }

    // --- Fire Service Elevator (Nested in Electrical) ---
    fun onFireServiceBackupPowerChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        backupPower = updateResultStatus(
                            fs.backupPower,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onFireServiceSpecialOperationChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        specialOperation = updateResultStatus(
                            fs.specialOperation,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onFireServiceFireSwitchChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        fireSwitch = updateResultStatus(
                            fs.fireSwitch,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onFireServiceLabelChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        label = updateResultStatus(fs.label, result, status)
                    )
                )
            )
        }
    }

    fun onFireServiceElectricalFireResistanceChange(
        result: String?,
        status: Boolean?
    ) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        electricalFireResistance = updateResultStatus(
                            fs.electricalFireResistance,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onFireServiceHoistwayWallFireResistanceChange(
        result: String?,
        status: Boolean?
    ) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        hoistwayWallFireResistance = updateResultStatus(
                            fs.hoistwayWallFireResistance,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onFireServiceCarSizeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        carSize = updateResultStatus(fs.carSize, result, status)
                    )
                )
            )
        }
    }

    fun onFireServiceDoorSizeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        doorSize = updateResultStatus(fs.doorSize, result, status)
                    )
                )
            )
        }
    }

    fun onFireServiceTravelTimeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        travelTime = updateResultStatus(
                            fs.travelTime,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onFireServiceEvacuationFloorChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val fs = ei.fireServiceElevator ?: FireServiceElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    fireServiceElevator = fs.copy(
                        evacuationFloor = updateResultStatus(
                            fs.evacuationFloor,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    // --- Accessibility Elevator (Nested in Electrical) ---
    fun onAccessibilityOperatingPanelChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val ae = ei.accessibilityElevator ?: AccessibilityElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    accessibilityElevator = ae.copy(
                        operatingPanel = updateResultStatus(
                            ae.operatingPanel,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onAccessibilityPanelHeightChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val ae = ei.accessibilityElevator ?: AccessibilityElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    accessibilityElevator = ae.copy(
                        panelHeight = updateResultStatus(
                            ae.panelHeight,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onAccessibilityDoorOpenTimeChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val ae = ei.accessibilityElevator ?: AccessibilityElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    accessibilityElevator = ae.copy(
                        doorOpenTime = updateResultStatus(
                            ae.doorOpenTime,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onAccessibilityDoorWidthChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val ae = ei.accessibilityElevator ?: AccessibilityElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    accessibilityElevator = ae.copy(
                        doorWidth = updateResultStatus(ae.doorWidth, result, status)
                    )
                )
            )
        }
    }

    fun onAccessibilityAudioInformationChange(
        result: String?,
        status: Boolean?
    ) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val ae = ei.accessibilityElevator ?: AccessibilityElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    accessibilityElevator = ae.copy(
                        audioInformation = updateResultStatus(
                            ae.audioInformation,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onAccessibilityLabelChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val ae = ei.accessibilityElevator ?: AccessibilityElevatorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    accessibilityElevator = ae.copy(
                        label = updateResultStatus(ae.label, result, status)
                    )
                )
            )
        }
    }

    // --- Seismic Sensor (Nested in Electrical) ---
    fun onSeismicSensorAvailabilityChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val ss = ei.seismicSensor ?: SeismicSensorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    seismicSensor = ss.copy(
                        availability = updateResultStatus(
                            ss.availability,
                            result,
                            status
                        )
                    )
                )
            )
        }
    }

    fun onSeismicSensorFunctionChange(result: String?, status: Boolean?) {
        updateInspectionAndTesting { i ->
            val ei = i.electricalInstallation ?: ElectricalInstallationUiState()
            val ss = ei.seismicSensor ?: SeismicSensorUiState()
            i.copy(
                electricalInstallation = ei.copy(
                    seismicSensor = ss.copy(
                        function = updateResultStatus(ss.function, result, status)
                    )
                )
            )
        }
    }
    // endregion
}