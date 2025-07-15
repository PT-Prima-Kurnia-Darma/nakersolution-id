package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.remote.dto.*
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain

// --- Kategori yang sama dari Mapper UI State, untuk konsistensi ---
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

/**
 * Mengubah [InspectionWithDetailsDomain] menjadi [ElevatorReportDto] yang siap dikirim ke network.
 */
fun InspectionWithDetailsDomain.toNetworkDto(): ElevatorReportDto {

    fun findItem(category: String, itemName: String): ResultStatusDto {
        val item = this.checkItems.find { it.category == category && it.itemName == itemName }
        return item?.let { ResultStatusDto(result = it.result ?: "", status = it.status) } ?: ResultStatusDto()
    }

    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    val generalDataDto = GeneralDataDto(
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

    val technicalDocumentDto = TechnicalDocumentInspectionDto(
        designDrawing = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Gambar Rencana"),
        technicalCalculation = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Perhitungan Teknis"),
        materialCertificate = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Sertifikat Material"),
        controlPanelDiagram = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Diagram Panel Kontrol"),
        asBuiltDrawing = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Gambar Jadi (As Built Drawing)"),
        componentCertificates = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Sertifikat Komponen"),
        safeWorkProcedure = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Prosedur Kerja Aman")
    )

    val inspectionAndTestingDto = InspectionAndTestingDto(
        machineRoomAndMachinery = MachineRoomAndMachineryDto(
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
            machineRoomless = MachineRoomlessDto(
                panelPlacement = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penempatan Panel"),
                lightingWorkArea = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penerangan Area Kerja"),
                lightingBetweenWorkArea = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penerangan Antar Area Kerja"),
                manualBrakeRelease = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Pelepas Rem Manual"),
                fireExtinguisherPlacement = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penempatan Alat Pemadam Api")
            )
        ),
        suspensionRopesAndBelts = SuspensionRopesAndBeltsDto(
            condition = findItem(ElevatorCategory.SUSPENSION, "Kondisi"),
            chainUsage = findItem(ElevatorCategory.SUSPENSION, "Penggunaan Rantai"),
            safetyFactor = findItem(ElevatorCategory.SUSPENSION, "Faktor Keamanan"),
            ropeWithCounterweight = findItem(ElevatorCategory.SUSPENSION, "Tali dengan Bobot Imbang"),
            ropeWithoutCounterweight = findItem(ElevatorCategory.SUSPENSION, "Tali tanpa Bobot Imbang"),
            belt = findItem(ElevatorCategory.SUSPENSION, "Sabuk (Belt)"),
            slackRopeDevice = findItem(ElevatorCategory.SUSPENSION, "Alat Pengaman Tali Kendor")
        ),
        drumsAndSheaves = DrumsAndSheavesDto(
            drumGrooves = findItem(ElevatorCategory.DRUMS_SHEAVES, "Alur Drum"),
            passengerDrumDiameter = findItem(ElevatorCategory.DRUMS_SHEAVES, "Diameter Drum Penumpang"),
            governorDrumDiameter = findItem(ElevatorCategory.DRUMS_SHEAVES, "Diameter Drum Governor")
        ),
        hoistwayAndPit = HoistwayAndPitDto(
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
        car = CarDto(
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
            carDoorSpecs = CarDoorSpecsDto(
                size = findItem(ElevatorCategory.CAR_DOOR_SPECS, "Ukuran"),
                lockAndSwitch = findItem(ElevatorCategory.CAR_DOOR_SPECS, "Kunci dan Saklar"),
                sillClearance = findItem(ElevatorCategory.CAR_DOOR_SPECS, "Jarak Celah")
            ),
            carSignage = CarSignageDto(
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
        governorAndSafetyBrake = GovernorAndSafetyBrakeDto(
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
        counterweightGuideRailsAndBuffers = CounterweightGuideRailsAndBuffersDto(
            counterweightMaterial = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Material Bobot Imbang"),
            counterweightGuardScreen = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Layar Pelindung Bobot Imbang"),
            guideRailConstruction = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Konstruksi Rel Pemandu"),
            bufferType = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Tipe Buffer"),
            bufferFunction = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Fungsi Buffer"),
            bufferSafetySwitch = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Saklar Pengaman Buffer")
        ),
        electricalInstallation = ElectricalInstallationDto(
            installationStandard = findItem(ElevatorCategory.ELECTRICAL, "Standar Instalasi"),
            electricalPanel = findItem(ElevatorCategory.ELECTRICAL, "Panel Listrik"),
            backupPowerARD = findItem(ElevatorCategory.ELECTRICAL, "Daya Cadangan (ARD)"),
            groundingCable = findItem(ElevatorCategory.ELECTRICAL, "Kabel Pembumian"),
            fireAlarmConnection = findItem(ElevatorCategory.ELECTRICAL, "Koneksi Alarm Kebakaran"),
            fireServiceElevator = FireServiceElevatorDto(
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
            accessibilityElevator = AccessibilityElevatorDto(
                operatingPanel = findItem(ElevatorCategory.ACCESSIBILITY, "Panel Operasi"),
                panelHeight = findItem(ElevatorCategory.ACCESSIBILITY, "Tinggi Panel"),
                doorOpenTime = findItem(ElevatorCategory.ACCESSIBILITY, "Waktu Buka Pintu"),
                doorWidth = findItem(ElevatorCategory.ACCESSIBILITY, "Lebar Pintu"),
                audioInformation = findItem(ElevatorCategory.ACCESSIBILITY, "Informasi Audio"),
                label = findItem(ElevatorCategory.ACCESSIBILITY, "Label")
            ),
            seismicSensor = SeismicSensorDto(
                availability = findItem(ElevatorCategory.SEISMIC, "Ketersediaan"),
                function = findItem(ElevatorCategory.SEISMIC, "Fungsi")
            )
        )
    )

    return ElevatorReportDto(
        inspectionType = this.inspection.inspectionType.name, // atau cara lain untuk mendapatkan string
        examinationType = this.inspection.examinationType,
        equipmentType = this.inspection.equipmentType,
        generalData = generalDataDto,
        technicalDocumentInspection = technicalDocumentDto,
        inspectionAndTesting = inspectionAndTestingDto,
        conclusion = this.inspection.status ?: ""
    )
}