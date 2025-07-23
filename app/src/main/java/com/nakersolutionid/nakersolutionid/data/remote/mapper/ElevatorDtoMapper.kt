package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus
import com.nakersolutionid.nakersolutionid.data.remote.dto.elevator.*
import com.nakersolutionid.nakersolutionid.domain.model.*

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

fun InspectionWithDetailsDomain.toElevatorReportRequest(): ElevatorReportRequest {
    val checkItems = this.checkItems

    fun findItem(category: String, itemName: String): ResultStatus {
        val item = checkItems.find { it.category == category && it.itemName == itemName }
        return ResultStatus(result = item?.result ?: "", status = item?.status ?: false)
    }

    fun findBoolItem(category: String, itemName: String): Boolean {
        return checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    val generalData = ElevatorGeneralData(
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

    val technicalDocumentInspection = ElevatorTechnicalDocumentInspection(
        designDrawing = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Gambar Rencana"),
        technicalCalculation = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Perhitungan Teknis"),
        materialCertificate = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Sertifikat Material"),
        controlPanelDiagram = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Diagram Panel Kontrol"),
        asBuiltDrawing = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Gambar Jadi (As Built Drawing)"),
        componentCertificates = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Sertifikat Komponen"),
        safeWorkProcedure = findBoolItem(ElevatorCategory.TECHNICAL_DOC, "Prosedur Kerja Aman")
    )

    val inspectionAndTesting = ElevatorInspectionAndTesting(
        machineRoomAndMachinery = ElevatorMachineRoomAndMachinery(
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
            machineRoomless = ElevatorMachineRoomless(
                panelPlacement = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penempatan Panel"),
                lightingWorkArea = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penerangan Area Kerja"),
                lightingBetweenWorkArea = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penerangan Antar Area Kerja"),
                manualBrakeRelease = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Pelepas Rem Manual"),
                fireExtinguisherPlacement = findItem(ElevatorCategory.MACHINE_ROOMLESS, "Penempatan Alat Pemadam Api")
            )
        ),
        suspensionRopesAndBelts = ElevatorSuspensionRopesAndBelts(
            condition = findItem(ElevatorCategory.SUSPENSION, "Kondisi"),
            chainUsage = findItem(ElevatorCategory.SUSPENSION, "Penggunaan Rantai"),
            safetyFactor = findItem(ElevatorCategory.SUSPENSION, "Faktor Keamanan"),
            ropeWithCounterweight = findItem(ElevatorCategory.SUSPENSION, "Tali dengan Bobot Imbang"),
            ropeWithoutCounterweight = findItem(ElevatorCategory.SUSPENSION, "Tali tanpa Bobot Imbang"),
            belt = findItem(ElevatorCategory.SUSPENSION, "Sabuk (Belt)"),
            slackRopeDevice = findItem(ElevatorCategory.SUSPENSION, "Alat Pengaman Tali Kendor")
        ),
        drumsAndSheaves = ElevatorDrumsAndSheaves(
            drumGrooves = findItem(ElevatorCategory.DRUMS_SHEAVES, "Alur Drum"),
            passengerDrumDiameter = findItem(ElevatorCategory.DRUMS_SHEAVES, "Diameter Drum Penumpang"),
            governorDrumDiameter = findItem(ElevatorCategory.DRUMS_SHEAVES, "Diameter Drum Governor")
        ),
        hoistwayAndPit = ElevatorHoistwayAndPit(
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
        car = ElevatorCar(
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
            carDoorSpecs = ElevatorCarDoorSpecs(
                size = findItem(ElevatorCategory.CAR_DOOR_SPECS, "Ukuran"),
                lockAndSwitch = findItem(ElevatorCategory.CAR_DOOR_SPECS, "Kunci dan Saklar"),
                sillClearance = findItem(ElevatorCategory.CAR_DOOR_SPECS, "Jarak Celah")
            ),
            carSignage = ElevatorCarSignage(
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
        governorAndSafetyBrake = ElevatorGovernorAndSafetyBrake(
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
        counterweightGuideRailsAndBuffers = ElevatorCounterweightGuideRailsAndBuffers(
            counterweightMaterial = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Material Bobot Imbang"),
            counterweightGuardScreen = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Layar Pelindung Bobot Imbang"),
            guideRailConstruction = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Konstruksi Rel Pemandu"),
            bufferType = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Tipe Buffer"),
            bufferFunction = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Fungsi Buffer"),
            bufferSafetySwitch = findItem(ElevatorCategory.COUNTERWEIGHT_BUFFER, "Saklar Pengaman Buffer")
        ),
        electricalInstallation = ElevatorElectricalInstallation(
            installationStandard = findItem(ElevatorCategory.ELECTRICAL, "Standar Instalasi"),
            electricalPanel = findItem(ElevatorCategory.ELECTRICAL, "Panel Listrik"),
            backupPowerARD = findItem(ElevatorCategory.ELECTRICAL, "Daya Cadangan (ARD)"),
            groundingCable = findItem(ElevatorCategory.ELECTRICAL, "Kabel Pembumian"),
            fireAlarmConnection = findItem(ElevatorCategory.ELECTRICAL, "Koneksi Alarm Kebakaran"),
            fireServiceElevator = ElevatorFireServiceElevator(
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
            accessibilityElevator = ElevatorAccessibilityElevator(
                operatingPanel = findItem(ElevatorCategory.ACCESSIBILITY, "Panel Operasi"),
                panelHeight = findItem(ElevatorCategory.ACCESSIBILITY, "Tinggi Panel"),
                doorOpenTime = findItem(ElevatorCategory.ACCESSIBILITY, "Waktu Buka Pintu"),
                doorWidth = findItem(ElevatorCategory.ACCESSIBILITY, "Lebar Pintu"),
                audioInformation = findItem(ElevatorCategory.ACCESSIBILITY, "Informasi Audio"),
                label = findItem(ElevatorCategory.ACCESSIBILITY, "Label")
            ),
            seismicSensor = ElevatorSeismicSensor(
                availability = findItem(ElevatorCategory.SEISMIC, "Ketersediaan"),
                function = findItem(ElevatorCategory.SEISMIC, "Fungsi")
            )
        )
    )

    val conclusion = findings.firstOrNull { it.type == FindingType.RECOMMENDATION }?.description ?: ""
    val recommendations = findings.filter { it.type == FindingType.FINDING }.joinToString("\n") { it.description }


    return ElevatorReportRequest(
        inspectionType = inspection.inspectionType.name,
        examinationType = inspection.examinationType,
        createdAt = inspection.createdAt ?: "",
        equipmentType = inspection.equipmentType,
        extraId = inspection.extraId.toIntOrNull() ?: 0,
        generalData = generalData,
        technicalDocumentInspection = technicalDocumentInspection,
        inspectionAndTesting = inspectionAndTesting,
        conclusion = conclusion,
        recomendations = recommendations
    )
}

fun ElevatorReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.id.toLongOrNull() ?: 0L

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId.toString(),
        documentType = DocumentType.valueOf(this.documentType),
        inspectionType = InspectionType.valueOf(this.inspectionType),
        subInspectionType = SubInspectionType.valueOf(this.subInspectionType),
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
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
        createdAt = this.createdAt,
        reportDate = this.generalData.inspectionDate,
        status = this.conclusion,
        isSynced = true // Data from network is considered synced
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    val techDocs = this.technicalDocumentInspection
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElevatorCategory.TECHNICAL_DOC, itemName = "Gambar Rencana", status = techDocs.designDrawing))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElevatorCategory.TECHNICAL_DOC, itemName = "Perhitungan Teknis", status = techDocs.technicalCalculation))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElevatorCategory.TECHNICAL_DOC, itemName = "Sertifikat Material", status = techDocs.materialCertificate))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElevatorCategory.TECHNICAL_DOC, itemName = "Diagram Panel Kontrol", status = techDocs.controlPanelDiagram))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElevatorCategory.TECHNICAL_DOC, itemName = "Gambar Jadi (As Built Drawing)", status = techDocs.asBuiltDrawing))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElevatorCategory.TECHNICAL_DOC, itemName = "Sertifikat Komponen", status = techDocs.componentCertificates))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ElevatorCategory.TECHNICAL_DOC, itemName = "Prosedur Kerja Aman", status = techDocs.safeWorkProcedure))

    fun MutableList<InspectionCheckItemDomain>.addCheckItem(category: String, itemName: String, data: ResultStatus) {
        this.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = data.status, result = data.result))
    }

    this.inspectionAndTesting.let {
        it.machineRoomAndMachinery.let { data ->
            val cat = ElevatorCategory.MACHINE_ROOM
            checkItems.addCheckItem(cat, "Pemasangan Mesin", data.machineMounting)
            checkItems.addCheckItem(cat, "Rem Mekanik", data.mechanicalBrake)
            checkItems.addCheckItem(cat, "Rem Listrik", data.electricalBrake)
            checkItems.addCheckItem(cat, "Konstruksi Ruang Mesin", data.machineRoomConstruction)
            checkItems.addCheckItem(cat, "Jarak Bebas Ruang Mesin", data.machineRoomClearance)
            checkItems.addCheckItem(cat, "Pelaksanaan Ruang Mesin", data.machineRoomImplementation)
            checkItems.addCheckItem(cat, "Ventilasi", data.ventilation)
            checkItems.addCheckItem(cat, "Pintu Ruang Mesin", data.machineRoomDoor)
            checkItems.addCheckItem(cat, "Posisi Panel Listrik Utama", data.mainPowerPanelPosition)
            checkItems.addCheckItem(cat, "Pelindung Bagian Berputar", data.rotatingPartsGuard)
            checkItems.addCheckItem(cat, "Pelindung Lubang Tali", data.ropeHoleGuard)
            checkItems.addCheckItem(cat, "Tangga Akses Ruang Mesin", data.machineRoomAccessLadder)
            checkItems.addCheckItem(cat, "Perbedaan Level Lantai", data.floorLevelDifference)
            checkItems.addCheckItem(cat, "Alat Pemadam Api", data.fireExtinguisher)
            checkItems.addCheckItem(cat, "Saklar Stop Darurat", data.emergencyStopSwitch)

            data.machineRoomless.let { subData ->
                val subCat = ElevatorCategory.MACHINE_ROOMLESS
                checkItems.addCheckItem(subCat, "Penempatan Panel", subData.panelPlacement)
                checkItems.addCheckItem(subCat, "Penerangan Area Kerja", subData.lightingWorkArea)
                checkItems.addCheckItem(subCat, "Penerangan Antar Area Kerja", subData.lightingBetweenWorkArea)
                checkItems.addCheckItem(subCat, "Pelepas Rem Manual", subData.manualBrakeRelease)
                checkItems.addCheckItem(subCat, "Penempatan Alat Pemadam Api", subData.fireExtinguisherPlacement)
            }
        }
        it.suspensionRopesAndBelts.let { data ->
            val cat = ElevatorCategory.SUSPENSION
            checkItems.addCheckItem(cat, "Kondisi", data.condition)
            checkItems.addCheckItem(cat, "Penggunaan Rantai", data.chainUsage)
            checkItems.addCheckItem(cat, "Faktor Keamanan", data.safetyFactor)
            checkItems.addCheckItem(cat, "Tali dengan Bobot Imbang", data.ropeWithCounterweight)
            checkItems.addCheckItem(cat, "Tali tanpa Bobot Imbang", data.ropeWithoutCounterweight)
            checkItems.addCheckItem(cat, "Sabuk (Belt)", data.belt)
            checkItems.addCheckItem(cat, "Alat Pengaman Tali Kendor", data.slackRopeDevice)
        }

        it.drumsAndSheaves.let { data ->
            val cat = ElevatorCategory.DRUMS_SHEAVES
            checkItems.addCheckItem(cat, "Alur Drum", data.drumGrooves)
            checkItems.addCheckItem(cat, "Diameter Drum Penumpang", data.passengerDrumDiameter)
            checkItems.addCheckItem(cat, "Diameter Drum Governor", data.governorDrumDiameter)
        }

        it.hoistwayAndPit.let { data ->
            val cat = ElevatorCategory.HOISTWAY_PIT
            checkItems.addCheckItem(cat, "Konstruksi", data.construction)
            checkItems.addCheckItem(cat, "Dinding", data.walls)
            checkItems.addCheckItem(cat, "Landasan Lintasan Lift Miring", data.inclinedElevatorTrackBed)
            checkItems.addCheckItem(cat, "Kebersihan", data.cleanliness)
            checkItems.addCheckItem(cat, "Penerangan", data.lighting)
            checkItems.addCheckItem(cat, "Pintu Darurat Non-Stop", data.emergencyDoorNonStop)
            checkItems.addCheckItem(cat, "Ukuran Pintu Darurat", data.emergencyDoorSize)
            checkItems.addCheckItem(cat, "Saklar Pengaman Pintu Darurat", data.emergencyDoorSafetySwitch)
            checkItems.addCheckItem(cat, "Jembatan Pintu Darurat", data.emergencyDoorBridge)
            checkItems.addCheckItem(cat, "Jarak Bebas Atas Kereta", data.carTopClearance)
            checkItems.addCheckItem(cat, "Jarak Bebas Pit", data.pitClearance)
            checkItems.addCheckItem(cat, "Tangga Pit", data.pitLadder)
            checkItems.addCheckItem(cat, "Area Kerja di Bawah Pit", data.pitBelowWorkingArea)
            checkItems.addCheckItem(cat, "Saklar Akses Pit", data.pitAccessSwitch)
            checkItems.addCheckItem(cat, "Layar Pelindung Pit", data.pitScreen)
            checkItems.addCheckItem(cat, "Daun Pintu Ruang Luncur", data.hoistwayDoorLeaf)
            checkItems.addCheckItem(cat, "Interlock Pintu Ruang Luncur", data.hoistwayDoorInterlock)
            checkItems.addCheckItem(cat, "Leveling Lantai", data.floorLeveling)
            checkItems.addCheckItem(cat, "Balok Pemisah Ruang Luncur", data.hoistwaySeparatorBeam)
            checkItems.addCheckItem(cat, "Tangga Lift Miring", data.inclinedElevatorStairs)
        }
        it.car.let { data ->
            val cat = ElevatorCategory.CAR
            checkItems.addCheckItem(cat, "Rangka Kereta", data.frame)
            checkItems.addCheckItem(cat, "Badan Kereta", data.body)
            checkItems.addCheckItem(cat, "Tinggi Dinding", data.wallHeight)
            checkItems.addCheckItem(cat, "Luas Lantai", data.floorArea)
            checkItems.addCheckItem(cat, "Perluasan Area Kereta", data.carAreaExpansion)
            checkItems.addCheckItem(cat, "Pintu Kereta", data.carDoor)
            checkItems.addCheckItem(cat, "Jarak Kereta ke Balok", data.carToBeamClearance)
            checkItems.addCheckItem(cat, "Bel Alarm", data.alarmBell)
            checkItems.addCheckItem(cat, "Daya Cadangan (ARD)", data.backupPowerARD)
            checkItems.addCheckItem(cat, "Interkom", data.intercom)
            checkItems.addCheckItem(cat, "Ventilasi", data.ventilation)
            checkItems.addCheckItem(cat, "Penerangan Darurat", data.emergencyLighting)
            checkItems.addCheckItem(cat, "Panel Operasi", data.operatingPanel)
            checkItems.addCheckItem(cat, "Indikator Posisi Kereta", data.carPositionIndicator)
            checkItems.addCheckItem(cat, "Kekuatan Atap Kereta", data.carRoofStrength)
            checkItems.addCheckItem(cat, "Pintu Keluar Darurat Atas", data.carTopEmergencyExit)
            checkItems.addCheckItem(cat, "Pintu Keluar Darurat Samping", data.carSideEmergencyExit)
            checkItems.addCheckItem(cat, "Pagar Pengaman Atap", data.carTopGuardRail)
            checkItems.addCheckItem(cat, "Tinggi Pagar 300-850mm", data.guardRailHeight300to850)
            checkItems.addCheckItem(cat, "Tinggi Pagar >850mm", data.guardRailHeightOver850)
            checkItems.addCheckItem(cat, "Penerangan Atap Kereta", data.carTopLighting)
            checkItems.addCheckItem(cat, "Tombol Operasi Manual", data.manualOperationButtons)
            checkItems.addCheckItem(cat, "Interior Kereta", data.carInterior)

            data.carDoorSpecs.let { subData ->
                val subCat = ElevatorCategory.CAR_DOOR_SPECS
                checkItems.addCheckItem(subCat, "Ukuran", subData.size)
                checkItems.addCheckItem(subCat, "Kunci dan Saklar", subData.lockAndSwitch)
                checkItems.addCheckItem(subCat, "Jarak Celah", subData.sillClearance)
            }
            data.carSignage.let { subData ->
                val subCat = ElevatorCategory.CAR_SIGNAGE
                checkItems.addCheckItem(subCat, "Nama Pabrikan", subData.manufacturerName)
                checkItems.addCheckItem(subCat, "Kapasitas Beban", subData.loadCapacity)
                checkItems.addCheckItem(subCat, "Tanda Dilarang Merokok", subData.noSmokingSign)
                checkItems.addCheckItem(subCat, "Indikator Beban Lebih", subData.overloadIndicator)
                checkItems.addCheckItem(subCat, "Tombol Buka/Tutup Pintu", subData.doorOpenCloseButtons)
                checkItems.addCheckItem(subCat, "Tombol Lantai", subData.floorButtons)
                checkItems.addCheckItem(subCat, "Tombol Alarm", subData.alarmButton)
                checkItems.addCheckItem(subCat, "Interkom Dua Arah", subData.twoWayIntercom)
            }
        }
        it.governorAndSafetyBrake.let { data ->
            val cat = ElevatorCategory.GOVERNOR_BRAKE
            checkItems.addCheckItem(cat, "Klem Tali Governor", data.governorRopeClamp)
            checkItems.addCheckItem(cat, "Saklar Governor", data.governorSwitch)
            checkItems.addCheckItem(cat, "Kecepatan Rem Pengaman", data.safetyBrakeSpeed)
            checkItems.addCheckItem(cat, "Tipe Rem Pengaman", data.safetyBrakeType)
            checkItems.addCheckItem(cat, "Mekanisme Rem Pengaman", data.safetyBrakeMechanism)
            checkItems.addCheckItem(cat, "Rem Pengaman Progresif", data.progressiveSafetyBrake)
            checkItems.addCheckItem(cat, "Rem Pengaman Seketika", data.instantaneousSafetyBrake)
            checkItems.addCheckItem(cat, "Operasi Rem Pengaman", data.safetyBrakeOperation)
            checkItems.addCheckItem(cat, "Saklar Pemutus Listrik", data.electricalCutoutSwitch)
            checkItems.addCheckItem(cat, "Saklar Batas", data.limitSwitch)
            checkItems.addCheckItem(cat, "Alat Beban Lebih", data.overloadDevice)
        }
        it.counterweightGuideRailsAndBuffers.let { data ->
            val cat = ElevatorCategory.COUNTERWEIGHT_BUFFER
            checkItems.addCheckItem(cat, "Material Bobot Imbang", data.counterweightMaterial)
            checkItems.addCheckItem(cat, "Layar Pelindung Bobot Imbang", data.counterweightGuardScreen)
            checkItems.addCheckItem(cat, "Konstruksi Rel Pemandu", data.guideRailConstruction)
            checkItems.addCheckItem(cat, "Tipe Buffer", data.bufferType)
            checkItems.addCheckItem(cat, "Fungsi Buffer", data.bufferFunction)
            checkItems.addCheckItem(cat, "Saklar Pengaman Buffer", data.bufferSafetySwitch)
        }
        it.electricalInstallation.let { data ->
            val cat = ElevatorCategory.ELECTRICAL
            checkItems.addCheckItem(cat, "Standar Instalasi", data.installationStandard)
            checkItems.addCheckItem(cat, "Panel Listrik", data.electricalPanel)
            checkItems.addCheckItem(cat, "Daya Cadangan (ARD)", data.backupPowerARD)
            checkItems.addCheckItem(cat, "Kabel Pembumian", data.groundingCable)
            checkItems.addCheckItem(cat, "Koneksi Alarm Kebakaran", data.fireAlarmConnection)

            data.fireServiceElevator.let { subData ->
                val subCat = ElevatorCategory.FIRE_SERVICE
                checkItems.addCheckItem(subCat, "Daya Cadangan", subData.backupPower)
                checkItems.addCheckItem(subCat, "Operasi Khusus", subData.specialOperation)
                checkItems.addCheckItem(subCat, "Saklar Kebakaran", subData.fireSwitch)
                checkItems.addCheckItem(subCat, "Label", subData.label)
                checkItems.addCheckItem(subCat, "Ketahanan Api Listrik", subData.electricalFireResistance)
                checkItems.addCheckItem(subCat, "Ketahanan Api Dinding Ruang Luncur", subData.hoistwayWallFireResistance)
                checkItems.addCheckItem(subCat, "Ukuran Kereta", subData.carSize)
                checkItems.addCheckItem(subCat, "Ukuran Pintu", subData.doorSize)
                checkItems.addCheckItem(subCat, "Waktu Perjalanan", subData.travelTime)
                checkItems.addCheckItem(subCat, "Lantai Evakuasi", subData.evacuationFloor)
            }
            data.accessibilityElevator.let { subData ->
                val subCat = ElevatorCategory.ACCESSIBILITY
                checkItems.addCheckItem(subCat, "Panel Operasi", subData.operatingPanel)
                checkItems.addCheckItem(subCat, "Tinggi Panel", subData.panelHeight)
                checkItems.addCheckItem(subCat, "Waktu Buka Pintu", subData.doorOpenTime)
                checkItems.addCheckItem(subCat, "Lebar Pintu", subData.doorWidth)
                checkItems.addCheckItem(subCat, "Informasi Audio", subData.audioInformation)
                checkItems.addCheckItem(subCat, "Label", subData.label)
            }
            data.seismicSensor.let { subData ->
                val subCat = ElevatorCategory.SEISMIC
                checkItems.addCheckItem(subCat, "Ketersediaan", subData.availability)
                checkItems.addCheckItem(subCat, "Fungsi", subData.function)
            }
        }
    }

    val findings = mutableListOf<InspectionFindingDomain>()
    if (this.conclusion.isNotBlank()) {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = this.conclusion, type = FindingType.RECOMMENDATION))
    }
    if (this.recomendations.isNotBlank()) {
        this.recomendations.split("\n").forEach {
            findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING))
        }
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList() // ElevatorReportData does not contain test results
    )
}
