package com.nakersolutionid.nakersolutionid.features.report.ee.elevator
// Imports from Domain Layer
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain

// Imports from UI State

/**
 * Menyimpan semua nama kategori sebagai konstanta untuk mencegah kesalahan ketik dan memastikan konsistensi.
 */
private object ElevatorCategory {
    const val TECHNICAL_DOC = "Pemeriksaan Dokumen Teknis"
    const val MACHINE_ROOM = "Ruang Mesin dan Permesinan"
    const val MACHINE_ROOMLESS = "$MACHINE_ROOM - Tanpa Ruang Mesin"
    const val SUSPENSION = "Tali/Sabuk Penarik"
    const val DRUMS_SHEAVES = "Drum dan Pully"
    const val HOISTWAY_PIT = "Ruang Luncur dan Pit"
    const val CAR = "Kereta (Car)"
    const val CAR_DOOR_SPECS = "$CAR - Spesifikasi Pintu Kereta"
    const val CAR_SIGNAGE = "$CAR - Rambu-rambu Kereta"
    const val GOVERNOR_BRAKE = "Governor dan Rem Pengaman"
    const val COUNTERWEIGHT_BUFFER = "Bobot Imbang, Rel Pemandu, dan Buffer"
    const val ELECTRICAL = "Instalasi Listrik"
    const val FIRE_SERVICE = "$ELECTRICAL - Lift Kebakaran"
    const val ACCESSIBILITY = "$ELECTRICAL - Lift Aksesibilitas"
    const val SEISMIC = "$ELECTRICAL - Sensor Gempa"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

/**
 * Mengubah [ElevatorUiState] (dari UI) menjadi [InspectionWithDetailsDomain] (untuk data layer).
 */
fun ElevatorUiState.toInspectionWithDetailsDomain(currentTime: String): InspectionWithDetailsDomain {
    val inspectionId = this.id

    val inspectionDomain = InspectionDomain(
        id = 0,
        extraId = "",
        documentType = this.documentType,
        inspectionType = this.nameOfInspectionType,
        subInspectionType = this.subNameOfInspectionType,
        equipmentType = this.eskOrElevType,
        examinationType = this.typeInspection,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.nameUsageLocation,
        addressUsageLocation = this.generalData.addressUsageLocation,
        driveType = this.generalData.elevatorType,
        serialNumber = this.generalData.serialNumber,
        permitNumber = this.generalData.permitNumber,
        capacity = this.generalData.capacity,
        speed = this.generalData.speed,
        floorServed = this.generalData.floorsServed,
        manufacturer = ManufacturerDomain(
            name = this.generalData.manufacturerOrInstaller,
            brandOrType = this.generalData.brandOrType,
            year = this.generalData.countryAndYear
        ),
        createdAt = currentTime,
        reportDate = this.generalData.inspectionDate,
        status = this.conclusion,
        isSynced = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.addAll(mapTechnicalDocsToDomain(this.technicalDocumentInspection, inspectionId))
    checkItems.addAll(mapInspectionAndTestingToDomain(this.inspectionAndTesting, inspectionId))

    val findings = if (this.conclusion.isNotBlank()) {
        listOf(InspectionFindingDomain(inspectionId = inspectionId, description = this.conclusion, type = FindingType.RECOMMENDATION))
    } else {
        emptyList()
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList() // ElevatorUiState tidak memiliki test results
    )
}

// --- Helper Functions for UI State -> Domain ---

private fun mapTechnicalDocsToDomain(uiState: TechnicalDocumentInspectionUiState, inspectionId: Long): List<InspectionCheckItemDomain> {
    val items = mutableListOf<InspectionCheckItemDomain>()
    val cat = ElevatorCategory.TECHNICAL_DOC
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Gambar Rencana", status = uiState.designDrawing))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Perhitungan Teknis", status = uiState.technicalCalculation))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Sertifikat Material", status = uiState.materialCertificate))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Diagram Panel Kontrol", status = uiState.controlPanelDiagram))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Gambar Jadi (As Built Drawing)", status = uiState.asBuiltDrawing))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Sertifikat Komponen", status = uiState.componentCertificates))
    items.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Prosedur Kerja Aman", status = uiState.safeWorkProcedure))
    return items
}

private fun MutableList<InspectionCheckItemDomain>.addCheckItem(inspectionId: Long, category: String, itemName: String, data: ResultStatusUiState) {
    this.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = data.status, result = data.result))
}

private fun mapInspectionAndTestingToDomain(uiState: InspectionAndTestingUiState, inspectionId: Long): List<InspectionCheckItemDomain> {
    val items = mutableListOf<InspectionCheckItemDomain>()

    uiState.machineRoomAndMachinery.let { data ->
        val cat = ElevatorCategory.MACHINE_ROOM
        items.addCheckItem(inspectionId, cat, "Pemasangan Mesin", data.machineMounting)
        items.addCheckItem(inspectionId, cat, "Rem Mekanik", data.mechanicalBrake)
        items.addCheckItem(inspectionId, cat, "Rem Listrik", data.electricalBrake)
        items.addCheckItem(inspectionId, cat, "Konstruksi Ruang Mesin", data.machineRoomConstruction)
        items.addCheckItem(inspectionId, cat, "Jarak Bebas Ruang Mesin", data.machineRoomClearance)
        items.addCheckItem(inspectionId, cat, "Pelaksanaan Ruang Mesin", data.machineRoomImplementation)
        items.addCheckItem(inspectionId, cat, "Ventilasi", data.ventilation)
        items.addCheckItem(inspectionId, cat, "Pintu Ruang Mesin", data.machineRoomDoor)
        items.addCheckItem(inspectionId, cat, "Posisi Panel Listrik Utama", data.mainPowerPanelPosition)
        items.addCheckItem(inspectionId, cat, "Pelindung Bagian Berputar", data.rotatingPartsGuard)
        items.addCheckItem(inspectionId, cat, "Pelindung Lubang Tali", data.ropeHoleGuard)
        items.addCheckItem(inspectionId, cat, "Tangga Akses Ruang Mesin", data.machineRoomAccessLadder)
        items.addCheckItem(inspectionId, cat, "Perbedaan Level Lantai", data.floorLevelDifference)
        items.addCheckItem(inspectionId, cat, "Alat Pemadam Api", data.fireExtinguisher)
        items.addCheckItem(inspectionId, cat, "Saklar Stop Darurat", data.emergencyStopSwitch)

        data.machineRoomless.let { subData ->
            val subCat = ElevatorCategory.MACHINE_ROOMLESS
            items.addCheckItem(inspectionId, subCat, "Penempatan Panel", subData.panelPlacement)
            items.addCheckItem(inspectionId, subCat, "Penerangan Area Kerja", subData.lightingWorkArea)
            items.addCheckItem(inspectionId, subCat, "Penerangan Antar Area Kerja", subData.lightingBetweenWorkArea)
            items.addCheckItem(inspectionId, subCat, "Pelepas Rem Manual", subData.manualBrakeRelease)
            items.addCheckItem(inspectionId, subCat, "Penempatan Alat Pemadam Api", subData.fireExtinguisherPlacement)
        }
    }

    uiState.suspensionRopesAndBelts.let { data ->
        val cat = ElevatorCategory.SUSPENSION
        items.addCheckItem(inspectionId, cat, "Kondisi", data.condition)
        items.addCheckItem(inspectionId, cat, "Penggunaan Rantai", data.chainUsage)
        items.addCheckItem(inspectionId, cat, "Faktor Keamanan", data.safetyFactor)
        items.addCheckItem(inspectionId, cat, "Tali dengan Bobot Imbang", data.ropeWithCounterweight)
        items.addCheckItem(inspectionId, cat, "Tali tanpa Bobot Imbang", data.ropeWithoutCounterweight)
        items.addCheckItem(inspectionId, cat, "Sabuk (Belt)", data.belt)
        items.addCheckItem(inspectionId, cat, "Alat Pengaman Tali Kendor", data.slackRopeDevice)
    }

    uiState.drumsAndSheaves.let { data ->
        val cat = ElevatorCategory.DRUMS_SHEAVES
        items.addCheckItem(inspectionId, cat, "Alur Drum", data.drumGrooves)
        items.addCheckItem(inspectionId, cat, "Diameter Drum Penumpang", data.passengerDrumDiameter)
        items.addCheckItem(inspectionId, cat, "Diameter Drum Governor", data.governorDrumDiameter)
    }

    uiState.hoistwayAndPit.let { data ->
        val cat = ElevatorCategory.HOISTWAY_PIT
        items.addCheckItem(inspectionId, cat, "Konstruksi", data.construction)
        items.addCheckItem(inspectionId, cat, "Dinding", data.walls)
        items.addCheckItem(inspectionId, cat, "Landasan Lintasan Lift Miring", data.inclinedElevatorTrackBed)
        items.addCheckItem(inspectionId, cat, "Kebersihan", data.cleanliness)
        items.addCheckItem(inspectionId, cat, "Penerangan", data.lighting)
        items.addCheckItem(inspectionId, cat, "Pintu Darurat Non-Stop", data.emergencyDoorNonStop)
        items.addCheckItem(inspectionId, cat, "Ukuran Pintu Darurat", data.emergencyDoorSize)
        items.addCheckItem(inspectionId, cat, "Saklar Pengaman Pintu Darurat", data.emergencyDoorSafetySwitch)
        items.addCheckItem(inspectionId, cat, "Jembatan Pintu Darurat", data.emergencyDoorBridge)
        items.addCheckItem(inspectionId, cat, "Jarak Bebas Atas Kereta", data.carTopClearance)
        items.addCheckItem(inspectionId, cat, "Jarak Bebas Pit", data.pitClearance)
        items.addCheckItem(inspectionId, cat, "Tangga Pit", data.pitLadder)
        items.addCheckItem(inspectionId, cat, "Area Kerja di Bawah Pit", data.pitBelowWorkingArea)
        items.addCheckItem(inspectionId, cat, "Saklar Akses Pit", data.pitAccessSwitch)
        items.addCheckItem(inspectionId, cat, "Layar Pelindung Pit", data.pitScreen)
        items.addCheckItem(inspectionId, cat, "Daun Pintu Ruang Luncur", data.hoistwayDoorLeaf)
        items.addCheckItem(inspectionId, cat, "Interlock Pintu Ruang Luncur", data.hoistwayDoorInterlock)
        items.addCheckItem(inspectionId, cat, "Leveling Lantai", data.floorLeveling)
        items.addCheckItem(inspectionId, cat, "Balok Pemisah Ruang Luncur", data.hoistwaySeparatorBeam)
        items.addCheckItem(inspectionId, cat, "Tangga Lift Miring", data.inclinedElevatorStairs)
    }

    uiState.car.let { data ->
        val cat = ElevatorCategory.CAR
        items.addCheckItem(inspectionId, cat, "Rangka Kereta", data.frame)
        items.addCheckItem(inspectionId, cat, "Badan Kereta", data.body)
        items.addCheckItem(inspectionId, cat, "Tinggi Dinding", data.wallHeight)
        items.addCheckItem(inspectionId, cat, "Luas Lantai", data.floorArea)
        items.addCheckItem(inspectionId, cat, "Perluasan Area Kereta", data.carAreaExpansion)
        items.addCheckItem(inspectionId, cat, "Pintu Kereta", data.carDoor)
        items.addCheckItem(inspectionId, cat, "Jarak Kereta ke Balok", data.carToBeamClearance)
        items.addCheckItem(inspectionId, cat, "Bel Alarm", data.alarmBell)
        items.addCheckItem(inspectionId, cat, "Daya Cadangan (ARD)", data.backupPowerARD)
        items.addCheckItem(inspectionId, cat, "Interkom", data.intercom)
        items.addCheckItem(inspectionId, cat, "Ventilasi", data.ventilation)
        items.addCheckItem(inspectionId, cat, "Penerangan Darurat", data.emergencyLighting)
        items.addCheckItem(inspectionId, cat, "Panel Operasi", data.operatingPanel)
        items.addCheckItem(inspectionId, cat, "Indikator Posisi Kereta", data.carPositionIndicator)
        items.addCheckItem(inspectionId, cat, "Kekuatan Atap Kereta", data.carRoofStrength)
        items.addCheckItem(inspectionId, cat, "Pintu Keluar Darurat Atas", data.carTopEmergencyExit)
        items.addCheckItem(inspectionId, cat, "Pintu Keluar Darurat Samping", data.carSideEmergencyExit)
        items.addCheckItem(inspectionId, cat, "Pagar Pengaman Atap", data.carTopGuardRail)
        items.addCheckItem(inspectionId, cat, "Tinggi Pagar 300-850mm", data.guardRailHeight300to850)
        items.addCheckItem(inspectionId, cat, "Tinggi Pagar >850mm", data.guardRailHeightOver850)
        items.addCheckItem(inspectionId, cat, "Penerangan Atap Kereta", data.carTopLighting)
        items.addCheckItem(inspectionId, cat, "Tombol Operasi Manual", data.manualOperationButtons)
        items.addCheckItem(inspectionId, cat, "Interior Kereta", data.carInterior)

        data.carDoorSpecs.let { subData ->
            val subCat = ElevatorCategory.CAR_DOOR_SPECS
            items.addCheckItem(inspectionId, subCat, "Ukuran", subData.size)
            items.addCheckItem(inspectionId, subCat, "Kunci dan Saklar", subData.lockAndSwitch)
            items.addCheckItem(inspectionId, subCat, "Jarak Celah", subData.sillClearance)
        }

        data.carSignage.let { subData ->
            val subCat = ElevatorCategory.CAR_SIGNAGE
            items.addCheckItem(inspectionId, subCat, "Nama Pabrikan", subData.manufacturerName)
            items.addCheckItem(inspectionId, subCat, "Kapasitas Beban", subData.loadCapacity)
            items.addCheckItem(inspectionId, subCat, "Tanda Dilarang Merokok", subData.noSmokingSign)
            items.addCheckItem(inspectionId, subCat, "Indikator Beban Lebih", subData.overloadIndicator)
            items.addCheckItem(inspectionId, subCat, "Tombol Buka/Tutup Pintu", subData.doorOpenCloseButtons)
            items.addCheckItem(inspectionId, subCat, "Tombol Lantai", subData.floorButtons)
            items.addCheckItem(inspectionId, subCat, "Tombol Alarm", subData.alarmButton)
            items.addCheckItem(inspectionId, subCat, "Interkom Dua Arah", subData.twoWayIntercom)
        }
    }

    uiState.governorAndSafetyBrake.let { data ->
        val cat = ElevatorCategory.GOVERNOR_BRAKE
        items.addCheckItem(inspectionId, cat, "Klem Tali Governor", data.governorRopeClamp)
        items.addCheckItem(inspectionId, cat, "Saklar Governor", data.governorSwitch)
        items.addCheckItem(inspectionId, cat, "Kecepatan Rem Pengaman", data.safetyBrakeSpeed)
        items.addCheckItem(inspectionId, cat, "Tipe Rem Pengaman", data.safetyBrakeType)
        items.addCheckItem(inspectionId, cat, "Mekanisme Rem Pengaman", data.safetyBrakeMechanism)
        items.addCheckItem(inspectionId, cat, "Rem Pengaman Progresif", data.progressiveSafetyBrake)
        items.addCheckItem(inspectionId, cat, "Rem Pengaman Seketika", data.instantaneousSafetyBrake)
        items.addCheckItem(inspectionId, cat, "Operasi Rem Pengaman", data.safetyBrakeOperation)
        items.addCheckItem(inspectionId, cat, "Saklar Pemutus Listrik", data.electricalCutoutSwitch)
        items.addCheckItem(inspectionId, cat, "Saklar Batas", data.limitSwitch)
        items.addCheckItem(inspectionId, cat, "Alat Beban Lebih", data.overloadDevice)
    }

    uiState.counterweightGuideRailsAndBuffers.let { data ->
        val cat = ElevatorCategory.COUNTERWEIGHT_BUFFER
        items.addCheckItem(inspectionId, cat, "Material Bobot Imbang", data.counterweightMaterial)
        items.addCheckItem(inspectionId, cat, "Layar Pelindung Bobot Imbang", data.counterweightGuardScreen)
        items.addCheckItem(inspectionId, cat, "Konstruksi Rel Pemandu", data.guideRailConstruction)
        items.addCheckItem(inspectionId, cat, "Tipe Buffer", data.bufferType)
        items.addCheckItem(inspectionId, cat, "Fungsi Buffer", data.bufferFunction)
        items.addCheckItem(inspectionId, cat, "Saklar Pengaman Buffer", data.bufferSafetySwitch)
    }

    uiState.electricalInstallation.let { data ->
        val cat = ElevatorCategory.ELECTRICAL
        items.addCheckItem(inspectionId, cat, "Standar Instalasi", data.installationStandard)
        items.addCheckItem(inspectionId, cat, "Panel Listrik", data.electricalPanel)
        items.addCheckItem(inspectionId, cat, "Daya Cadangan (ARD)", data.backupPowerARD)
        items.addCheckItem(inspectionId, cat, "Kabel Pembumian", data.groundingCable)
        items.addCheckItem(inspectionId, cat, "Koneksi Alarm Kebakaran", data.fireAlarmConnection)

        data.fireServiceElevator.let { subData ->
            val subCat = ElevatorCategory.FIRE_SERVICE
            items.addCheckItem(inspectionId, subCat, "Daya Cadangan", subData.backupPower)
            items.addCheckItem(inspectionId, subCat, "Operasi Khusus", subData.specialOperation)
            items.addCheckItem(inspectionId, subCat, "Saklar Kebakaran", subData.fireSwitch)
            items.addCheckItem(inspectionId, subCat, "Label", subData.label)
            items.addCheckItem(inspectionId, subCat, "Ketahanan Api Listrik", subData.electricalFireResistance)
            items.addCheckItem(inspectionId, subCat, "Ketahanan Api Dinding Ruang Luncur", subData.hoistwayWallFireResistance)
            items.addCheckItem(inspectionId, subCat, "Ukuran Kereta", subData.carSize)
            items.addCheckItem(inspectionId, subCat, "Ukuran Pintu", subData.doorSize)
            items.addCheckItem(inspectionId, subCat, "Waktu Perjalanan", subData.travelTime)
            items.addCheckItem(inspectionId, subCat, "Lantai Evakuasi", subData.evacuationFloor)
        }

        data.accessibilityElevator.let { subData ->
            val subCat = ElevatorCategory.ACCESSIBILITY
            items.addCheckItem(inspectionId, subCat, "Panel Operasi", subData.operatingPanel)
            items.addCheckItem(inspectionId, subCat, "Tinggi Panel", subData.panelHeight)
            items.addCheckItem(inspectionId, subCat, "Waktu Buka Pintu", subData.doorOpenTime)
            items.addCheckItem(inspectionId, subCat, "Lebar Pintu", subData.doorWidth)
            items.addCheckItem(inspectionId, subCat, "Informasi Audio", subData.audioInformation)
            items.addCheckItem(inspectionId, subCat, "Label", subData.label)
        }

        data.seismicSensor.let { subData ->
            val subCat = ElevatorCategory.SEISMIC
            items.addCheckItem(inspectionId, subCat, "Ketersediaan", subData.availability)
            items.addCheckItem(inspectionId, subCat, "Fungsi", subData.function)
        }
    }

    return items
}

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

/**
 * Mengubah [InspectionWithDetailsDomain] (dari data layer) menjadi [ElevatorUiState] (untuk UI).
 */
fun InspectionWithDetailsDomain.toElevatorUiState(): ElevatorUiState {
    // Helper untuk mencari item check berdasarkan kategori dan nama
    fun findItem(category: String, itemName: String): ResultStatusUiState {
        val item = this.checkItems.find { it.category == category && it.itemName == itemName }
        return item?.let { ResultStatusUiState(result = it.result ?: "", status = it.status) } ?: ResultStatusUiState()
    }

    // Helper untuk mencari item check yang statusnya boolean
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    val generalData = GeneralDataUiState(
        ownerName = this.inspection.ownerName ?: "",
        ownerAddress = this.inspection.ownerAddress ?: "",
        nameUsageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: "",
        manufacturerOrInstaller = this.inspection.manufacturer?.name ?: "",
        elevatorType = this.inspection.driveType ?: "",
        brandOrType = this.inspection.manufacturer?.brandOrType ?: "",
        countryAndYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        capacity = this.inspection.capacity ?: "",
        speed = this.inspection.speed ?: "",
        floorsServed = this.inspection.floorServed ?: "",
        permitNumber = this.inspection.permitNumber ?: "",
        inspectionDate = this.inspection.reportDate ?: ""
    )

    val technicalDocs = TechnicalDocumentInspectionUiState(
        designDrawing = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Gambar Rencana"),
        technicalCalculation = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Perhitungan Teknis"),
        materialCertificate = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Sertifikat Material"),
        controlPanelDiagram = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Diagram Panel Kontrol"),
        asBuiltDrawing = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Gambar Jadi (As Built Drawing)"),
        componentCertificates = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Sertifikat Komponen"),
        safeWorkProcedure = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Prosedur Kerja Aman")
    )

    val inspectionAndTesting = InspectionAndTestingUiState(
        machineRoomAndMachinery = MachineRoomAndMachineryUiState(
            machineMounting = findItem(ElevatorCategory.MACHINE_ROOM, "Pemasangan Mesin"),
            mechanicalBrake = findItem(ElevatorCategory.MACHINE_ROOM, "Rem Mekanik"),
            electricalBrake = findItem(ElevatorCategory.MACHINE_ROOM, "Rem Listrik"),
            machineRoomConstruction = findItem(ElevatorCategory.MACHINE_ROOM, "Konstruksi Ruang Mesin"),
            machineRoomClearance = findItem(ElevatorCategory.MACHINE_ROOM, "Jarak Bebas Ruang Mesin"),
            machineRoomImplementation = findItem(ElevatorCategory.MACHINE_ROOM, "Pelaksanaan Ruang Mesin"),
            ventilation = findItem(ElevatorCategory.MACHINE_ROOM, "Ventilasi"),
            machineRoomDoor = findItem(ElevatorCategory.MACHINE_ROOM, "Pintu Ruang Mesin"),
            mainPowerPanelPosition = findItem(ElevatorCategory.MACHINE_ROOM, "Posisi Panel Listrik Utama"),
            rotatingPartsGuard = findItem(ElevatorCategory.MACHINE_ROOM, "Pelindung Bagian Berputar"),
            ropeHoleGuard = findItem(ElevatorCategory.MACHINE_ROOM, "Pelindung Lubang Tali"),
            machineRoomAccessLadder = findItem(ElevatorCategory.MACHINE_ROOM, "Tangga Akses Ruang Mesin"),
            floorLevelDifference = findItem(ElevatorCategory.MACHINE_ROOM, "Perbedaan Level Lantai"),
            fireExtinguisher = findItem(ElevatorCategory.MACHINE_ROOM, "Alat Pemadam Api"),
            emergencyStopSwitch = findItem(ElevatorCategory.MACHINE_ROOM, "Saklar Stop Darurat"),
            machineRoomless = MachineRoomlessUiState(
                panelPlacement = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penempatan Panel"),
                lightingWorkArea = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penerangan Area Kerja"),
                lightingBetweenWorkArea = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penerangan Antar Area Kerja"),
                manualBrakeRelease = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Pelepas Rem Manual"),
                fireExtinguisherPlacement = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penempatan Alat Pemadam Api")
            )
        ),
        suspensionRopesAndBelts = SuspensionRopesAndBeltsUiState(
            condition = findItem(ElevatorCategory.SUSPENSION, "Kondisi"),
            chainUsage = findItem(ElevatorCategory.SUSPENSION, "Penggunaan Rantai"),
            safetyFactor = findItem(ElevatorCategory.SUSPENSION, "Faktor Keamanan"),
            ropeWithCounterweight = findItem(ElevatorCategory.SUSPENSION, "Tali dengan Bobot Imbang"),
            ropeWithoutCounterweight = findItem(ElevatorCategory.SUSPENSION, "Tali tanpa Bobot Imbang"),
            belt = findItem(ElevatorCategory.SUSPENSION, "Sabuk (Belt)"),
            slackRopeDevice = findItem(ElevatorCategory.SUSPENSION, "Alat Pengaman Tali Kendor")
        ),
        drumsAndSheaves = DrumsAndSheavesUiState(
            drumGrooves = findItem(ElevatorCategory.DRUMS_SHEAVES, "Alur Drum"),
            passengerDrumDiameter = findItem(ElevatorCategory.DRUMS_SHEAVES, "Diameter Drum Penumpang"),
            governorDrumDiameter = findItem(ElevatorCategory.DRUMS_SHEAVES, "Diameter Drum Governor")
        ),
        hoistwayAndPit = HoistwayAndPitUiState(
            construction = findItem(ElevatorCategory.HOISTWAY_PIT, "Konstruksi"),
            walls = findItem(ElevatorCategory.HOISTWAY_PIT, "Dinding"),
            inclinedElevatorTrackBed = findItem(ElevatorCategory.HOISTWAY_PIT, "Landasan Lintasan Lift Miring"),
            cleanliness = findItem(ElevatorCategory.HOISTWAY_PIT, "Kebersihan"),
            lighting = findItem(ElevatorCategory.HOISTWAY_PIT, "Penerangan"),
            emergencyDoorNonStop = findItem(ElevatorCategory.HOISTWAY_PIT, "Pintu Darurat Non-Stop"),
            emergencyDoorSize = findItem(ElevatorCategory.HOISTWAY_PIT, "Ukuran Pintu Darurat"),
            emergencyDoorSafetySwitch = findItem(ElevatorCategory.HOISTWAY_PIT, "Saklar Pengaman Pintu Darurat"),
            emergencyDoorBridge = findItem(ElevatorCategory.HOISTWAY_PIT, "Jembatan Pintu Darurat"),
            carTopClearance = findItem(ElevatorCategory.HOISTWAY_PIT, "Jarak Bebas Atas Kereta"),
            pitClearance = findItem(ElevatorCategory.HOISTWAY_PIT, "Jarak Bebas Pit"),
            pitLadder = findItem(ElevatorCategory.HOISTWAY_PIT, "Tangga Pit"),
            pitBelowWorkingArea = findItem(ElevatorCategory.HOISTWAY_PIT, "Area Kerja di Bawah Pit"),
            pitAccessSwitch = findItem(ElevatorCategory.HOISTWAY_PIT, "Saklar Akses Pit"),
            pitScreen = findItem(ElevatorCategory.HOISTWAY_PIT, "Layar Pelindung Pit"),
            hoistwayDoorLeaf = findItem(ElevatorCategory.HOISTWAY_PIT, "Daun Pintu Ruang Luncur"),
            hoistwayDoorInterlock = findItem(ElevatorCategory.HOISTWAY_PIT, "Interlock Pintu Ruang Luncur"),
            floorLeveling = findItem(ElevatorCategory.HOISTWAY_PIT, "Leveling Lantai"),
            hoistwaySeparatorBeam = findItem(ElevatorCategory.HOISTWAY_PIT, "Balok Pemisah Ruang Luncur"),
            inclinedElevatorStairs = findItem(ElevatorCategory.HOISTWAY_PIT, "Tangga Lift Miring")
        ),
        car = CarUiState(
            frame = findItem(ElevatorCategory.CAR, "Rangka Kereta"),
            body = findItem(ElevatorCategory.CAR, "Badan Kereta"),
            wallHeight = findItem(ElevatorCategory.CAR, "Tinggi Dinding"),
            floorArea = findItem(ElevatorCategory.CAR, "Luas Lantai"),
            carAreaExpansion = findItem(ElevatorCategory.CAR, "Perluasan Area Kereta"),
            carDoor = findItem(ElevatorCategory.CAR, "Pintu Kereta"),
            carToBeamClearance = findItem(ElevatorCategory.CAR, "Jarak Kereta ke Balok"),
            alarmBell = findItem(ElevatorCategory.CAR, "Bel Alarm"),
            backupPowerARD = findItem(ElevatorCategory.CAR, "Daya Cadangan (ARD)"),
            intercom = findItem(ElevatorCategory.CAR, "Interkom"),
            ventilation = findItem(ElevatorCategory.CAR, "Ventilasi"),
            emergencyLighting = findItem(ElevatorCategory.CAR, "Penerangan Darurat"),
            operatingPanel = findItem(ElevatorCategory.CAR, "Panel Operasi"),
            carPositionIndicator = findItem(ElevatorCategory.CAR, "Indikator Posisi Kereta"),
            carRoofStrength = findItem(ElevatorCategory.CAR, "Kekuatan Atap Kereta"),
            carTopEmergencyExit = findItem(ElevatorCategory.CAR, "Pintu Keluar Darurat Atas"),
            carSideEmergencyExit = findItem(ElevatorCategory.CAR, "Pintu Keluar Darurat Samping"),
            carTopGuardRail = findItem(ElevatorCategory.CAR, "Pagar Pengaman Atap"),
            guardRailHeight300to850 = findItem(ElevatorCategory.CAR, "Tinggi Pagar 300-850mm"),
            guardRailHeightOver850 = findItem(ElevatorCategory.CAR, "Tinggi Pagar >850mm"),
            carTopLighting = findItem(ElevatorCategory.CAR, "Penerangan Atap Kereta"),
            manualOperationButtons = findItem(ElevatorCategory.CAR, "Tombol Operasi Manual"),
            carInterior = findItem(ElevatorCategory.CAR, "Interior Kereta"),
            carDoorSpecs = CarDoorSpecsUiState(
                size = findItem(ElevatorCategory.CAR_DOOR_SPECS, "Ukuran"),
                lockAndSwitch = findItem(ElevatorCategory.CAR_DOOR_SPECS, "Kunci dan Saklar"),
                sillClearance = findItem(ElevatorCategory.CAR_DOOR_SPECS, "Jarak Celah")
            ),
            carSignage = CarSignageUiState(
                manufacturerName = findItem(ElevatorCategory.CAR_SIGNAGE, "Nama Pabrikan"),
                loadCapacity = findItem(ElevatorCategory.CAR_SIGNAGE, "Kapasitas Beban"),
                noSmokingSign = findItem(ElevatorCategory.CAR_SIGNAGE, "Tanda Dilarang Merokok"),
                overloadIndicator = findItem(ElevatorCategory.CAR_SIGNAGE, "Indikator Beban Lebih"),
                doorOpenCloseButtons = findItem(ElevatorCategory.CAR_SIGNAGE, "Tombol Buka/Tutup Pintu"),
                floorButtons = findItem(ElevatorCategory.CAR_SIGNAGE, "Tombol Lantai"),
                alarmButton = findItem(ElevatorCategory.CAR_SIGNAGE, "Tombol Alarm"),
                twoWayIntercom = findItem(ElevatorCategory.CAR_SIGNAGE, "Interkom Dua Arah")
            )
        ),
        governorAndSafetyBrake = GovernorAndSafetyBrakeUiState(
            governorRopeClamp = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Klem Tali Governor"),
            governorSwitch = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Saklar Governor"),
            safetyBrakeSpeed = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Kecepatan Rem Pengaman"),
            safetyBrakeType = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Tipe Rem Pengaman"),
            safetyBrakeMechanism = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Mekanisme Rem Pengaman"),
            progressiveSafetyBrake = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Rem Pengaman Progresif"),
            instantaneousSafetyBrake = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Rem Pengaman Seketika"),
            safetyBrakeOperation = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Operasi Rem Pengaman"),
            electricalCutoutSwitch = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Saklar Pemutus Listrik"),
            limitSwitch = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Saklar Batas"),
            overloadDevice = findItem(ElevatorCategory.GOVERNOR_BRAKE, "Alat Beban Lebih")
        ),
        counterweightGuideRailsAndBuffers = CounterweightGuideRailsAndBuffersUiState(
            counterweightMaterial = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Material Bobot Imbang"),
            counterweightGuardScreen = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Layar Pelindung Bobot Imbang"),
            guideRailConstruction = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Konstruksi Rel Pemandu"),
            bufferType = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Tipe Buffer"),
            bufferFunction = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Fungsi Buffer"),
            bufferSafetySwitch = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Saklar Pengaman Buffer")
        ),
        electricalInstallation = ElectricalInstallationUiState(
            installationStandard = findItem(ElevatorCategory.ELECTRICAL, "Standar Instalasi"),
            electricalPanel = findItem(ElevatorCategory.ELECTRICAL, "Panel Listrik"),
            backupPowerARD = findItem(ElevatorCategory.ELECTRICAL, "Daya Cadangan (ARD)"),
            groundingCable = findItem(ElevatorCategory.ELECTRICAL, "Kabel Pembumian"),
            fireAlarmConnection = findItem(ElevatorCategory.ELECTRICAL, "Koneksi Alarm Kebakaran"),
            fireServiceElevator = FireServiceElevatorUiState(
                backupPower = findItem(ElevatorCategory.FIRE_SERVICE, "Daya Cadangan"),
                specialOperation = findItem(ElevatorCategory.FIRE_SERVICE, "Operasi Khusus"),
                fireSwitch = findItem(ElevatorCategory.FIRE_SERVICE, "Saklar Kebakaran"),
                label = findItem(ElevatorCategory.FIRE_SERVICE, "Label"),
                electricalFireResistance = findItem(ElevatorCategory.FIRE_SERVICE, "Ketahanan Api Listrik"),
                hoistwayWallFireResistance = findItem(ElevatorCategory.FIRE_SERVICE, "Ketahanan Api Dinding Ruang Luncur"),
                carSize = findItem(ElevatorCategory.FIRE_SERVICE, "Ukuran Kereta"),
                doorSize = findItem(ElevatorCategory.FIRE_SERVICE, "Ukuran Pintu"),
                travelTime = findItem(ElevatorCategory.FIRE_SERVICE, "Waktu Perjalanan"),
                evacuationFloor = findItem(ElevatorCategory.FIRE_SERVICE, "Lantai Evakuasi")
            ),
            accessibilityElevator = AccessibilityElevatorUiState(
                operatingPanel = findItem(ElevatorCategory.ACCESSIBILITY, "Panel Operasi"),
                panelHeight = findItem(ElevatorCategory.ACCESSIBILITY, "Tinggi Panel"),
                doorOpenTime = findItem(ElevatorCategory.ACCESSIBILITY, "Waktu Buka Pintu"),
                doorWidth = findItem(ElevatorCategory.ACCESSIBILITY, "Lebar Pintu"),
                audioInformation = findItem(ElevatorCategory.ACCESSIBILITY, "Informasi Audio"),
                label = findItem(ElevatorCategory.ACCESSIBILITY, "Label")
            ),
            seismicSensor = SeismicSensorUiState(
                availability = findItem(ElevatorCategory.SEISMIC, "Ketersediaan"),
                function = findItem(ElevatorCategory.SEISMIC, "Fungsi")
            )
        )
    )

    return ElevatorUiState(
        id = this.checkItems.firstOrNull()?.inspectionId ?: 0L,
        documentType = this.inspection.documentType,
        nameOfInspectionType = this.inspection.inspectionType,
        subNameOfInspectionType = this.inspection.subInspectionType,
        typeInspection = this.inspection.examinationType,
        eskOrElevType = this.inspection.equipmentType,
        generalData = generalData,
        technicalDocumentInspection = technicalDocs,
        inspectionAndTesting = inspectionAndTesting,
        conclusion = this.inspection.status ?: "",
        createdAt = this.inspection.createdAt ?: ""
    )
}