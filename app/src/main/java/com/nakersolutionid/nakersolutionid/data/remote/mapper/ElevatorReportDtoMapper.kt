package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.remote.body.report.CreateElevatorReportBody
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportAccessibilityElevatorDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportCarDoorSpecsDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportCarDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportCarSignageDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportCounterweightGuideRailsAndBuffersDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportDrumsAndSheavesDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportElectricalInstallationDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportFireServiceElevatorDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportGeneralDataDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportGovernorAndSafetyBrakeDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportHoistwayAndPitDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportInspectionAndTestingDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportInspectionResultDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportMachineRoomAndMachineryDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportMachineRoomlessDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportSeismicSensorDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportSuspensionRopesAndBeltsDto
import com.nakersolutionid.nakersolutionid.data.remote.body.report.ElevatorReportTechnicalDocumentInspectionDto
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain

/**
 * Menyimpan semua nama kategori sebagai konstanta untuk mencegah kesalahan ketik dan memastikan konsistensi.
 * Ini harus sesuai dengan yang digunakan di ElevatorMapper.kt
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

/**
 * Extension function to convert an [InspectionWithDetailsDomain] object
 * to a [CreateElevatorReportBody] object for Retrofit API calls.
 */
fun InspectionWithDetailsDomain.toCreateElevatorReportBody(): CreateElevatorReportBody {
    // Helper function to find a specific check item by category and item name.
    // Returns an ElevatorReportInspectionResultDto with default values if not found.
    fun findCheckItemResult(category: String, itemName: String): ElevatorReportInspectionResultDto {
        val item = this.checkItems.find { it.category == category && it.itemName == itemName }
        return item?.let {
            ElevatorReportInspectionResultDto(result = it.result ?: "", status = it.status)
        } ?: ElevatorReportInspectionResultDto()
    }

    // Helper function to find a boolean status for a specific check item.
    // Returns false if the item is not found.
    fun findCheckItemStatus(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    // Map general data from InspectionDomain to ElevatorReportGeneralDataDto
    val generalDataDto = ElevatorReportGeneralDataDto(
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

    // Map technical document inspection items to ElevatorReportTechnicalDocumentInspectionDto
    val technicalDocumentInspectionDto = ElevatorReportTechnicalDocumentInspectionDto(
        designDrawing = findCheckItemStatus(ElevatorCategory.TECHNICAL_DOC, "Gambar Rencana"),
        technicalCalculation = findCheckItemStatus(ElevatorCategory.TECHNICAL_DOC, "Perhitungan Teknis"),
        materialCertificate = findCheckItemStatus(ElevatorCategory.TECHNICAL_DOC, "Sertifikat Material"),
        controlPanelDiagram = findCheckItemStatus(ElevatorCategory.TECHNICAL_DOC, "Diagram Panel Kontrol"),
        asBuiltDrawing = findCheckItemStatus(ElevatorCategory.TECHNICAL_DOC, "Gambar Jadi (As Built Drawing)"),
        componentCertificates = findCheckItemStatus(ElevatorCategory.TECHNICAL_DOC, "Sertifikat Komponen"),
        safeWorkProcedure = findCheckItemStatus(ElevatorCategory.TECHNICAL_DOC, "Prosedur Kerja Aman")
    )

    // Map machine room and machinery items to ElevatorReportMachineRoomAndMachineryDto
    val machineRoomAndMachineryDto = ElevatorReportMachineRoomAndMachineryDto(
        machineMounting = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Pemasangan Mesin"),
        mechanicalBrake = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Rem Mekanik"),
        electricalBrake = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Rem Listrik"),
        machineRoomConstruction = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Konstruksi Ruang Mesin"),
        machineRoomClearance = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Jarak Bebas Ruang Mesin"),
        machineRoomImplementation = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Pelaksanaan Ruang Mesin"),
        ventilation = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Ventilasi"),
        machineRoomDoor = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Pintu Ruang Mesin"),
        mainPowerPanelPosition = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Posisi Panel Listrik Utama"),
        rotatingPartsGuard = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Pelindung Bagian Berputar"),
        ropeHoleGuard = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Pelindung Lubang Tali"),
        machineRoomAccessLadder = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Tangga Akses Ruang Mesin"),
        floorLevelDifference = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Perbedaan Level Lantai"),
        fireExtinguisher = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Alat Pemadam Api"),
        emergencyStopSwitch = findCheckItemResult(ElevatorCategory.MACHINE_ROOM, "Saklar Stop Darurat"),
        machineRoomless = ElevatorReportMachineRoomlessDto(
            panelPlacement = findCheckItemResult(ElevatorCategory.MACHINE_ROOMLESS, "Penempatan Panel"),
            lightingWorkArea = findCheckItemResult(ElevatorCategory.MACHINE_ROOMLESS, "Penerangan Area Kerja"),
            lightingBetweenWorkArea = findCheckItemResult(ElevatorCategory.MACHINE_ROOMLESS, "Penerangan Antar Area Kerja"),
            manualBrakeRelease = findCheckItemResult(ElevatorCategory.MACHINE_ROOMLESS, "Pelepas Rem Manual"),
            fireExtinguisherPlacement = findCheckItemResult(ElevatorCategory.MACHINE_ROOMLESS, "Penempatan Alat Pemadam Api")
        )
    )

    // Map suspension ropes and belts items to ElevatorReportSuspensionRopesAndBeltsDto
    val suspensionRopesAndBeltsDto = ElevatorReportSuspensionRopesAndBeltsDto(
        condition = findCheckItemResult(ElevatorCategory.SUSPENSION, "Kondisi"),
        chainUsage = findCheckItemResult(ElevatorCategory.SUSPENSION, "Penggunaan Rantai"),
        safetyFactor = findCheckItemResult(ElevatorCategory.SUSPENSION, "Faktor Keamanan"),
        ropeWithCounterweight = findCheckItemResult(ElevatorCategory.SUSPENSION, "Tali dengan Bobot Imbang"),
        ropeWithoutCounterweight = findCheckItemResult(ElevatorCategory.SUSPENSION, "Tali tanpa Bobot Imbang"),
        belt = findCheckItemResult(ElevatorCategory.SUSPENSION, "Sabuk (Belt)"),
        slackRopeDevice = findCheckItemResult(ElevatorCategory.SUSPENSION, "Alat Pengaman Tali Kendor")
    )

    // Map drums and sheaves items to ElevatorReportDrumsAndSheavesDto
    val drumsAndSheavesDto = ElevatorReportDrumsAndSheavesDto(
        drumGrooves = findCheckItemResult(ElevatorCategory.DRUMS_SHEAVES, "Alur Drum"),
        passengerDrumDiameter = findCheckItemResult(ElevatorCategory.DRUMS_SHEAVES, "Diameter Drum Penumpang"),
        governorDrumDiameter = findCheckItemResult(ElevatorCategory.DRUMS_SHEAVES, "Diameter Drum Governor")
    )

    // Map hoistway and pit items to ElevatorReportHoistwayAndPitDto
    val hoistwayAndPitDto = ElevatorReportHoistwayAndPitDto(
        construction = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Konstruksi"),
        walls = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Dinding"),
        inclinedElevatorTrackBed = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Landasan Lintasan Lift Miring"),
        cleanliness = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Kebersihan"),
        lighting = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Penerangan"),
        emergencyDoorNonStop = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Pintu Darurat Non-Stop"),
        emergencyDoorSize = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Ukuran Pintu Darurat"),
        emergencyDoorSafetySwitch = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Saklar Pengaman Pintu Darurat"),
        emergencyDoorBridge = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Jembatan Pintu Darurat"),
        carTopClearance = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Jarak Bebas Atas Kereta"),
        pitClearance = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Jarak Bebas Pit"),
        pitLadder = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Tangga Pit"),
        pitBelowWorkingArea = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Area Kerja di Bawah Pit"),
        pitAccessSwitch = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Saklar Akses Pit"),
        pitScreen = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Layar Pelindung Pit"),
        hoistwayDoorLeaf = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Daun Pintu Ruang Luncur"),
        hoistwayDoorInterlock = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Interlock Pintu Ruang Luncur"),
        floorLeveling = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Leveling Lantai"),
        hoistwaySeparatorBeam = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Balok Pemisah Ruang Luncur"),
        inclinedElevatorStairs = findCheckItemResult(ElevatorCategory.HOISTWAY_PIT, "Tangga Lift Miring")
    )

    // Map car items to ElevatorReportCarDto
    val carDto = ElevatorReportCarDto(
        frame = findCheckItemResult(ElevatorCategory.CAR, "Rangka Kereta"),
        body = findCheckItemResult(ElevatorCategory.CAR, "Badan Kereta"),
        wallHeight = findCheckItemResult(ElevatorCategory.CAR, "Tinggi Dinding"),
        floorArea = findCheckItemResult(ElevatorCategory.CAR, "Luas Lantai"),
        carAreaExpansion = findCheckItemResult(ElevatorCategory.CAR, "Perluasan Area Kereta"),
        carDoor = findCheckItemResult(ElevatorCategory.CAR, "Pintu Kereta"),
        carToBeamClearance = findCheckItemResult(ElevatorCategory.CAR, "Jarak Kereta ke Balok"),
        alarmBell = findCheckItemResult(ElevatorCategory.CAR, "Bel Alarm"),
        backupPowerARD = findCheckItemResult(ElevatorCategory.CAR, "Daya Cadangan (ARD)"),
        intercom = findCheckItemResult(ElevatorCategory.CAR, "Interkom"),
        ventilation = findCheckItemResult(ElevatorCategory.CAR, "Ventilasi"),
        emergencyLighting = findCheckItemResult(ElevatorCategory.CAR, "Penerangan Darurat"),
        operatingPanel = findCheckItemResult(ElevatorCategory.CAR, "Panel Operasi"),
        carPositionIndicator = findCheckItemResult(ElevatorCategory.CAR, "Indikator Posisi Kereta"),
        carRoofStrength = findCheckItemResult(ElevatorCategory.CAR, "Kekuatan Atap Kereta"),
        carTopEmergencyExit = findCheckItemResult(ElevatorCategory.CAR, "Pintu Keluar Darurat Atas"),
        carSideEmergencyExit = findCheckItemResult(ElevatorCategory.CAR, "Pintu Keluar Darurat Samping"),
        carTopGuardRail = findCheckItemResult(ElevatorCategory.CAR, "Pagar Pengaman Atap"),
        guardRailHeight300to850 = findCheckItemResult(ElevatorCategory.CAR, "Tinggi Pagar 300-850mm"),
        guardRailHeightOver850 = findCheckItemResult(ElevatorCategory.CAR, "Tinggi Pagar >850mm"),
        carTopLighting = findCheckItemResult(ElevatorCategory.CAR, "Penerangan Atap Kereta"),
        manualOperationButtons = findCheckItemResult(ElevatorCategory.CAR, "Tombol Operasi Manual"),
        carInterior = findCheckItemResult(ElevatorCategory.CAR, "Interior Kereta"),
        carDoorSpecs = ElevatorReportCarDoorSpecsDto(
            size = findCheckItemResult(ElevatorCategory.CAR_DOOR_SPECS, "Ukuran"),
            lockAndSwitch = findCheckItemResult(ElevatorCategory.CAR_DOOR_SPECS, "Kunci dan Saklar"),
            sillClearance = findCheckItemResult(ElevatorCategory.CAR_DOOR_SPECS, "Jarak Celah")
        ),
        carSignage = ElevatorReportCarSignageDto(
            manufacturerName = findCheckItemResult(ElevatorCategory.CAR_SIGNAGE, "Nama Pabrikan"),
            loadCapacity = findCheckItemResult(ElevatorCategory.CAR_SIGNAGE, "Kapasitas Beban"),
            noSmokingSign = findCheckItemResult(ElevatorCategory.CAR_SIGNAGE, "Tanda Dilarang Merokok"),
            overloadIndicator = findCheckItemResult(ElevatorCategory.CAR_SIGNAGE, "Indikator Beban Lebih"),
            doorOpenCloseButtons = findCheckItemResult(ElevatorCategory.CAR_SIGNAGE, "Tombol Buka/Tutup Pintu"),
            floorButtons = findCheckItemResult(ElevatorCategory.CAR_SIGNAGE, "Tombol Lantai"),
            alarmButton = findCheckItemResult(ElevatorCategory.CAR_SIGNAGE, "Tombol Alarm"),
            twoWayIntercom = findCheckItemResult(ElevatorCategory.CAR_SIGNAGE, "Interkom Dua Arah")
        )
    )

    // Map governor and safety brake items to ElevatorReportGovernorAndSafetyBrakeDto
    val governorAndSafetyBrakeDto = ElevatorReportGovernorAndSafetyBrakeDto(
        governorRopeClamp = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Klem Tali Governor"),
        governorSwitch = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Saklar Governor"),
        safetyBrakeSpeed = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Kecepatan Rem Pengaman"),
        safetyBrakeType = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Tipe Rem Pengaman"),
        safetyBrakeMechanism = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Mekanisme Rem Pengaman"),
        progressiveSafetyBrake = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Rem Pengaman Progresif"),
        instantaneousSafetyBrake = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Rem Pengaman Seketika"),
        safetyBrakeOperation = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Operasi Rem Pengaman"),
        electricalCutoutSwitch = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Saklar Pemutus Listrik"),
        limitSwitch = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Saklar Batas"),
        overloadDevice = findCheckItemResult(ElevatorCategory.GOVERNOR_BRAKE, "Alat Beban Lebih")
    )

    // Map counterweight guide rails and buffers items to ElevatorReportCounterweightGuideRailsAndBuffersDto
    val counterweightGuideRailsAndBuffersDto = ElevatorReportCounterweightGuideRailsAndBuffersDto(
        counterweightMaterial = findCheckItemResult(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Material Bobot Imbang"),
        counterweightGuardScreen = findCheckItemResult(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Layar Pelindung Bobot Imbang"),
        guideRailConstruction = findCheckItemResult(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Konstruksi Rel Pemandu"),
        bufferType = findCheckItemResult(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Tipe Buffer"),
        bufferFunction = findCheckItemResult(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Fungsi Buffer"),
        bufferSafetySwitch = findCheckItemResult(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Saklar Pengaman Buffer")
    )

    // Map electrical installation items to ElevatorReportElectricalInstallationDto
    val electricalInstallationDto = ElevatorReportElectricalInstallationDto(
        installationStandard = findCheckItemResult(ElevatorCategory.ELECTRICAL, "Standar Instalasi"),
        electricalPanel = findCheckItemResult(ElevatorCategory.ELECTRICAL, "Panel Listrik"),
        backupPowerARD = findCheckItemResult(ElevatorCategory.ELECTRICAL, "Daya Cadangan (ARD)"),
        groundingCable = findCheckItemResult(ElevatorCategory.ELECTRICAL, "Kabel Pembumian"),
        fireAlarmConnection = findCheckItemResult(ElevatorCategory.ELECTRICAL, "Koneksi Alarm Kebakaran"),
        fireServiceElevator = ElevatorReportFireServiceElevatorDto(
            backupPower = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Daya Cadangan"),
            specialOperation = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Operasi Khusus"),
            fireSwitch = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Saklar Kebakaran"),
            label = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Label"),
            electricalFireResistance = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Ketahanan Api Listrik"),
            hoistwayWallFireResistance = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Ketahanan Api Dinding Ruang Luncur"),
            carSize = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Ukuran Kereta"),
            doorSize = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Ukuran Pintu"),
            travelTime = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Waktu Perjalanan"),
            evacuationFloor = findCheckItemResult(ElevatorCategory.FIRE_SERVICE, "Lantai Evakuasi")
        ),
        accessibilityElevator = ElevatorReportAccessibilityElevatorDto(
            operatingPanel = findCheckItemResult(ElevatorCategory.ACCESSIBILITY, "Panel Operasi"),
            panelHeight = findCheckItemResult(ElevatorCategory.ACCESSIBILITY, "Tinggi Panel"),
            doorOpenTime = findCheckItemResult(ElevatorCategory.ACCESSIBILITY, "Waktu Buka Pintu"),
            doorWidth = findCheckItemResult(ElevatorCategory.ACCESSIBILITY, "Lebar Pintu"),
            audioInformation = findCheckItemResult(ElevatorCategory.ACCESSIBILITY, "Informasi Audio"),
            label = findCheckItemResult(ElevatorCategory.ACCESSIBILITY, "Label")
        ),
        seismicSensor = ElevatorReportSeismicSensorDto(
            availability = findCheckItemResult(ElevatorCategory.SEISMIC, "Ketersediaan"),
            function = findCheckItemResult(ElevatorCategory.SEISMIC, "Fungsi")
        )
    )

    // Combine all inspection and testing sections into a single DTO
    val inspectionAndTestingDto = ElevatorReportInspectionAndTestingDto(
        machineRoomAndMachinery = machineRoomAndMachineryDto,
        suspensionRopesAndBelts = suspensionRopesAndBeltsDto,
        drumsAndSheaves = drumsAndSheavesDto,
        hoistwayAndPit = hoistwayAndPitDto,
        car = carDto,
        governorAndSafetyBrake = governorAndSafetyBrakeDto,
        counterweightGuideRailsAndBuffers = counterweightGuideRailsAndBuffersDto,
        electricalInstallation = electricalInstallationDto
    )

    // Determine the conclusion. Prefer inspection status, fallback to finding description.
    val conclusionText = this.inspection.status ?: this.findings.firstOrNull()?.description ?: ""

    // Construct the final CreateElevatorReportBody
    return CreateElevatorReportBody( // Renamed to CreateElevatorReportBody
        inspectionType = this.inspection.inspectionType.toDisplayString(), // Assuming InspectionType is an enum
        examinationType = this.inspection.examinationType,
        createdAt = this.inspection.createdAt ?: "",
        equipmentType = this.inspection.equipmentType,
        extraId = this.inspection.id,
        generalData = generalDataDto,
        technicalDocumentInspection = technicalDocumentInspectionDto,
        inspectionAndTesting = inspectionAndTestingDto,
        conclusion = conclusionText
    )
}