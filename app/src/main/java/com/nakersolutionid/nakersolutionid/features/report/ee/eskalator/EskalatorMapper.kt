package com.nakersolutionid.nakersolutionid.features.report.ee.eskalator

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain

/**
 * Menyimpan semua nama kategori sebagai konstanta untuk mencegah kesalahan ketik dan memastikan konsistensi.
 */
private object EskalatorCategory {
    const val FRAME_MACHINE_ROOM = "Kerangka, Ruang Mesin & Pit"
    const val DRIVE_EQUIPMENT = "Peralatan Penggerak"
    const val STEPS_PALLETS = "Anak Tangga / Pallet"
    const val LANDING_AREA = "Bidang Landas"
    const val BALUSTRADE = "Pagar Pelindung"
    const val HANDRAIL = "Ban Pegangan"
    const val RUNWAY = "Lintasan Luncur (Void)"
    const val SAFETY_EQUIPMENT = "Peralatan Pengaman"
    const val ELECTRICAL_INSTALLATION = "Instalasi Listrik"
    const val OUTDOOR_SPECIFICS = "Khusus Eskalator Outdoor"
    const val USER_SAFETY_SIGNAGE = "Keselamatan Pengguna"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

/**
 * Mengubah [EskalatorUiState] (dari UI) menjadi [InspectionWithDetailsDomain] (untuk data layer).
 *
 * @param currentTime Waktu saat ini dalam format String untuk kolom `createdAt`.
 * @return Objek [InspectionWithDetailsDomain] yang siap untuk disimpan.
 */
fun EskalatorUiState.toInspectionWithDetailsDomain(currentTime: String, reportId: Long? = null): InspectionWithDetailsDomain {
    val uiData = this.eskalatorData
    val inspectionId: Long = reportId ?: 0

    val manufacturerDomain = ManufacturerDomain(
        name = uiData.technicalData.manufacturer,
        brandOrType = uiData.technicalData.brand,
        year = uiData.technicalData.countryAndYear
    )

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = uiData.extraId,
        moreExtraId = uiData.moreExtraId,
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.EE,
        subInspectionType = SubInspectionType.Escalator,
        equipmentType = uiData.equipmentType,
        examinationType = uiData.examinationType,
        ownerName = uiData.companyData.companyOrBuildingName,
        ownerAddress = uiData.companyData.usageAddress,
        usageLocation = uiData.companyData.companyOrBuildingName, // Lokasi pemakaian adalah nama gedung/perusahaan itu sendiri.
        addressUsageLocation = uiData.companyData.usageAddress,
        driveType = uiData.technicalData.driveType,
        serialNumber = uiData.technicalData.serialNumber,
        permitNumber = uiData.companyData.usagePermit,
        capacity = uiData.technicalData.capacity,
        speed = uiData.technicalData.speed,
        floorServed = uiData.technicalData.liftHeight,
        manufacturer = manufacturerDomain,
        reportDate = uiData.companyData.inspectionDate,
        createdAt = currentTime,
        isSynced = false
    )

    val checkItems = mapInspectionAndTestingToDomain(uiData.inspectionAndTesting, inspectionId)

    val findings = if (uiData.conclusion.isNotBlank()) {
        listOf(InspectionFindingDomain(inspectionId = inspectionId, description = uiData.conclusion, type = FindingType.RECOMMENDATION))
    } else {
        emptyList()
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    uiData.testingSummary.let {
        if (it.safetyDevices.isNotBlank()) testResults.add(InspectionTestResultDomain(0, inspectionId, "Alat Pengaman", it.safetyDevices, null))
        if (it.noLoadTest.isNotBlank()) testResults.add(InspectionTestResultDomain(0, inspectionId, "Uji Tanpa Beban", it.noLoadTest, null))
        if (it.brakeTest.isNotBlank()) testResults.add(InspectionTestResultDomain(0, inspectionId, "Uji Rem", it.brakeTest, null))
    }

    // Menyimpan data yang tidak punya kolom langsung di `InspectionTestResultDomain`
    uiData.companyData.let {
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Jenis Objek K3/No.", it.safetyObjectTypeAndNumber, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Digunakan Untuk", it.intendedUse, null))
    }
    uiData.technicalData.let {
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Technical - Alat Angkut", it.transports, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Technical - Arus Motor", it.motorCurrent, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Technical - Daya Motor", it.motorPower, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Technical - Alat Pengaman (Umum)", it.safetyDevices, null))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = testResults
    )
}

private fun MutableList<InspectionCheckItemDomain>.addCheckItem(
    inspectionId: Long, category: String, itemName: String, data: EskalatorResultStatus
) {
    this.add(
        InspectionCheckItemDomain(
            inspectionId = inspectionId,
            category = category,
            itemName = itemName,
            status = data.status,
            result = data.result
        )
    )
}

private fun mapInspectionAndTestingToDomain(uiState: EskalatorInspectionAndTesting, inspectionId: Long): List<InspectionCheckItemDomain> {
    val items = mutableListOf<InspectionCheckItemDomain>()
    // Pemetaan di sini tetap sama seperti sebelumnya
    uiState.frameAndMachineRoom.let { data ->
        val cat = EskalatorCategory.FRAME_MACHINE_ROOM
        items.addCheckItem(inspectionId, cat, "Kerangka", data.frame)
        items.addCheckItem(inspectionId, cat, "Balok Penyangga", data.supportBeams)
        items.addCheckItem(inspectionId, cat, "Kondisi Ruang Mesin", data.machineRoomCondition)
        items.addCheckItem(inspectionId, cat, "Jarak Bebas Ruang Mesin", data.machineRoomClearance)
        items.addCheckItem(inspectionId, cat, "Penerangan Ruang Mesin", data.machineRoomLighting)
        items.addCheckItem(inspectionId, cat, "Plat Penutup Mesin", data.machineCoverPlate)
        items.addCheckItem(inspectionId, cat, "Kondisi Pit", data.pitCondition)
        items.addCheckItem(inspectionId, cat, "Jarak Bebas Pit", data.pitClearance)
        items.addCheckItem(inspectionId, cat, "Plat Penutup Step di Pit", data.pitStepCoverPlate)
    }
    uiState.driveEquipment.let { data ->
        val cat = EskalatorCategory.DRIVE_EQUIPMENT
        items.addCheckItem(inspectionId, cat, "Mesin Penggerak", data.driveMachine)
        items.addCheckItem(inspectionId, cat, "Kecepatan (≤ 30°)", data.speedUnder30Degrees)
        items.addCheckItem(inspectionId, cat, "Kecepatan (30°-35°)", data.speed30to35Degrees)
        items.addCheckItem(inspectionId, cat, "Kecepatan Travelator", data.travelatorSpeed)
        items.addCheckItem(inspectionId, cat, "Jarak Berhenti (0.5 m/s)", data.stoppingDistance0_5)
        items.addCheckItem(inspectionId, cat, "Jarak Berhenti (0.75 m/s)", data.stoppingDistance0_75)
        items.addCheckItem(inspectionId, cat, "Jarak Berhenti (0.90 m/s)", data.stoppingDistance0_90)
        items.addCheckItem(inspectionId, cat, "Rantai Penggerak", data.driveChain)
        items.addCheckItem(inspectionId, cat, "Kekuatan Putus Rantai", data.chainBreakingStrength)
    }
    uiState.stepsOrPallets.let { data ->
        val cat = EskalatorCategory.STEPS_PALLETS
        items.addCheckItem(inspectionId, cat, "Bahan Step", data.stepMaterial)
        items.addCheckItem(inspectionId, cat, "Dimensi Step", data.stepDimensions)
        items.addCheckItem(inspectionId, cat, "Dimensi Pallet", data.palletDimensions)
        items.addCheckItem(inspectionId, cat, "Permukaan Step", data.stepSurface)
        items.addCheckItem(inspectionId, cat, "Kerataan Step", data.stepLevelness)
        items.addCheckItem(inspectionId, cat, "Sikat Skirt", data.skirtBrush)
        items.addCheckItem(inspectionId, cat, "Roda Step", data.stepWheels)
    }
    uiState.landingArea.let { data ->
        val cat = EskalatorCategory.LANDING_AREA
        items.addCheckItem(inspectionId, cat, "Plat Pendaratan", data.landingPlates)
        items.addCheckItem(inspectionId, cat, "Gigi Sisir", data.combTeeth)
        items.addCheckItem(inspectionId, cat, "Kondisi Sisir", data.combCondition)
        items.addCheckItem(inspectionId, cat, "Penutup Pendaratan", data.landingCover)
        items.addCheckItem(inspectionId, cat, "Area Akses Pendaratan", data.landingAccessArea)
    }
    uiState.balustrade.let { data ->
        val cat = EskalatorCategory.BALUSTRADE
        data.balustradePanel.let { panelData ->
            items.addCheckItem(inspectionId, cat, "Material Panel Balustrade", panelData.material)
            items.addCheckItem(inspectionId, cat, "Tinggi Panel Balustrade", panelData.height)
            items.addCheckItem(inspectionId, cat, "Tekanan Samping Panel Balustrade", panelData.sidePressure)
            items.addCheckItem(inspectionId, cat, "Tekanan Vertikal Panel Balustrade", panelData.verticalPressure)
        }
        items.addCheckItem(inspectionId, cat, "Panel Skirt", data.skirtPanel)
        items.addCheckItem(inspectionId, cat, "Fleksibilitas Panel Skirt", data.skirtPanelFlexibility)
        items.addCheckItem(inspectionId, cat, "Jarak Bebas Step ke Skirt", data.stepToSkirtClearance)
    }
    uiState.handrail.let { data ->
        val cat = EskalatorCategory.HANDRAIL
        items.addCheckItem(inspectionId, cat, "Kondisi Handrail", data.handrailCondition)
        items.addCheckItem(inspectionId, cat, "Sinkronisasi Kecepatan Handrail", data.handrailSpeedSynchronization)
        items.addCheckItem(inspectionId, cat, "Lebar Handrail", data.handrailWidth)
    }
    uiState.runway.let { data ->
        val cat = EskalatorCategory.RUNWAY
        items.addCheckItem(inspectionId, cat, "Kekuatan & Posisi Balok", data.beamStrengthAndPosition)
        items.addCheckItem(inspectionId, cat, "Kondisi Dinding Pit", data.pitWallCondition)
        items.addCheckItem(inspectionId, cat, "Penutup Rangka Eskalator", data.escalatorFrameEnclosure)
        items.addCheckItem(inspectionId, cat, "Penerangan", data.lighting)
        items.addCheckItem(inspectionId, cat, "Jarak Bebas Headroom", data.headroomClearance)
        items.addCheckItem(inspectionId, cat, "Jarak Balustrade ke Objek", data.balustradeToObjectClearance)
        items.addCheckItem(inspectionId, cat, "Tinggi Alat Anti-Panjat", data.antiClimbDeviceHeight)
        items.addCheckItem(inspectionId, cat, "Penempatan Ornamen", data.ornamentPlacement)
        items.addCheckItem(inspectionId, cat, "Jarak Bebas Outdoor", data.outdoorClearance)
    }
    uiState.safetyEquipment.let { data ->
        val cat = EskalatorCategory.SAFETY_EQUIPMENT
        items.addCheckItem(inspectionId, cat, "Kunci Kontrol Operasi", data.operationControlKey)
        items.addCheckItem(inspectionId, cat, "Saklar Stop Darurat", data.emergencyStopSwitch)
        items.addCheckItem(inspectionId, cat, "Pengaman Rantai Step", data.stepChainSafetyDevice)
        items.addCheckItem(inspectionId, cat, "Pengaman Rantai Penggerak", data.driveChainSafetyDevice)
        items.addCheckItem(inspectionId, cat, "Pengaman Step", data.stepSafetyDevice)
        items.addCheckItem(inspectionId, cat, "Pengaman Handrail", data.handrailSafetyDevice)
        items.addCheckItem(inspectionId, cat, "Pengaman Pembalikan Arah", data.reversalStopDevice)
        items.addCheckItem(inspectionId, cat, "Pelindung Masuk Handrail", data.handrailEntryGuard)
        items.addCheckItem(inspectionId, cat, "Pengaman Plat Sisir", data.combPlateSafetyDevice)
        items.addCheckItem(inspectionId, cat, "Sikat Inner Decking", data.innerDeckingBrush)
        items.addCheckItem(inspectionId, cat, "Tombol Stop", data.stopButtons)
    }
    uiState.electricalInstallation.let { data ->
        val cat = EskalatorCategory.ELECTRICAL_INSTALLATION
        items.addCheckItem(inspectionId, cat, "Standar Instalasi", data.installationStandard)
        items.addCheckItem(inspectionId, cat, "Panel Listrik", data.electricalPanel)
        items.addCheckItem(inspectionId, cat, "Kabel Grounding", data.groundingCable)
        items.addCheckItem(inspectionId, cat, "Koneksi Alarm Kebakaran", data.fireAlarmConnection)
    }
    uiState.outdoorSpecifics.let { data ->
        val cat = EskalatorCategory.OUTDOOR_SPECIFICS
        items.addCheckItem(inspectionId, cat, "Pompa Air Pit", data.pitWaterPump)
        items.addCheckItem(inspectionId, cat, "Komponen Tahan Cuaca", data.weatherproofComponents)
    }
    uiState.userSafetySignage.let { data ->
        val cat = EskalatorCategory.USER_SAFETY_SIGNAGE
        items.addCheckItem(inspectionId, cat, "Dilarang Membawa Barang Besar", data.noBulkyItems)
        items.addCheckItem(inspectionId, cat, "Dilarang Melompat", data.noJumping)
        items.addCheckItem(inspectionId, cat, "Anak-anak Harus Diawasi", data.unattendedChildren)
        items.addCheckItem(inspectionId, cat, "Dilarang Membawa Troli/Stroller", data.noTrolleysOrStrollers)
        items.addCheckItem(inspectionId, cat, "Dilarang Bersandar", data.noLeaning)
        items.addCheckItem(inspectionId, cat, "Dilarang Menginjak Skirt", data.noSteppingOnSkirt)
        items.addCheckItem(inspectionId, cat, "Peringatan Alas Kaki Lunak", data.softSoleFootwearWarning)
        items.addCheckItem(inspectionId, cat, "Dilarang Duduk di Tangga", data.noSittingOnSteps)
        items.addCheckItem(inspectionId, cat, "Pegang Handrail", data.holdHandrail)
    }

    return items
}


// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

/**
 * Mengubah [InspectionWithDetailsDomain] (dari data layer) menjadi [EskalatorUiState] (untuk UI).
 */
fun InspectionWithDetailsDomain.toEskalatorUiState(): EskalatorUiState {

    fun findCheckItem(category: String, itemName: String): EskalatorResultStatus {
        val item = this.checkItems.find { it.category == category && it.itemName.equals(itemName, true) }
        return item?.let { EskalatorResultStatus(result = it.result ?: "", status = it.status) } ?: EskalatorResultStatus()
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName.equals(testName, true) }?.result ?: ""
    }

    val companyData = EskalatorCompanyData(
        companyOrBuildingName = this.inspection.ownerName ?: "",
        usageAddress = this.inspection.addressUsageLocation ?: "",
        safetyObjectTypeAndNumber = findTestResult("Jenis Objek K3/No."),
        intendedUse = findTestResult("Digunakan Untuk"),
        usagePermit = this.inspection.permitNumber ?: "",
        inspectionDate = this.inspection.reportDate ?: ""
    )

    val technicalData = EskalatorTechnicalData(
        manufacturer = this.inspection.manufacturer?.name ?: "",
        brand = this.inspection.manufacturer?.brandOrType ?: "",
        countryAndYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        capacity = this.inspection.capacity ?: "",
        liftHeight = this.inspection.floorServed ?: "",
        speed = this.inspection.speed ?: "",
        driveType = this.inspection.driveType ?: "",
        transports = findTestResult("Technical - Alat Angkut"),
        motorCurrent = findTestResult("Technical - Arus Motor"),
        motorPower = findTestResult("Technical - Daya Motor"),
        safetyDevices = findTestResult("Technical - Alat Pengaman (Umum)")
    )

    val inspectionAndTesting = EskalatorInspectionAndTesting(
        frameAndMachineRoom = FrameAndMachineRoom(
            frame = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Kerangka"),
            supportBeams = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Balok Penyangga"),
            machineRoomCondition = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Kondisi Ruang Mesin"),
            machineRoomClearance = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Jarak Bebas Ruang Mesin"),
            machineRoomLighting = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Penerangan Ruang Mesin"),
            machineCoverPlate = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Plat Penutup Mesin"),
            pitCondition = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Kondisi Pit"),
            pitClearance = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Jarak Bebas Pit"),
            pitStepCoverPlate = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Plat Penutup Step di Pit")
        ),
        driveEquipment = DriveEquipment(
            driveMachine = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Mesin Penggerak"),
            speedUnder30Degrees = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Kecepatan (≤ 30°)"),
            speed30to35Degrees = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Kecepatan (30°-35°)"),
            travelatorSpeed = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Kecepatan Travelator"),
            stoppingDistance0_5 = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Jarak Berhenti (0.5 m/s)"),
            stoppingDistance0_75 = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Jarak Berhenti (0.75 m/s)"),
            stoppingDistance0_90 = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Jarak Berhenti (0.90 m/s)"),
            driveChain = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Rantai Penggerak"),
            chainBreakingStrength = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Kekuatan Putus Rantai")
        ),
        stepsOrPallets = StepsOrPallets(
            stepMaterial = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Bahan Step"),
            stepDimensions = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Dimensi Step"),
            palletDimensions = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Dimensi Pallet"),
            stepSurface = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Permukaan Step"),
            stepLevelness = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Kerataan Step"),
            skirtBrush = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Sikat Skirt"),
            stepWheels = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Roda Step")
        ),
        landingArea = LandingArea(
            landingPlates = findCheckItem(EskalatorCategory.LANDING_AREA, "Plat Pendaratan"),
            combTeeth = findCheckItem(EskalatorCategory.LANDING_AREA, "Gigi Sisir"),
            combCondition = findCheckItem(EskalatorCategory.LANDING_AREA, "Kondisi Sisir"),
            landingCover = findCheckItem(EskalatorCategory.LANDING_AREA, "Penutup Pendaratan"),
            landingAccessArea = findCheckItem(EskalatorCategory.LANDING_AREA, "Area Akses Pendaratan")
        ),
        balustrade = Balustrade(
            balustradePanel = BalustradePanel(
                material = findCheckItem(EskalatorCategory.BALUSTRADE, "Material Panel Balustrade"),
                height = findCheckItem(EskalatorCategory.BALUSTRADE, "Tinggi Panel Balustrade"),
                sidePressure = findCheckItem(EskalatorCategory.BALUSTRADE, "Tekanan Samping Panel Balustrade"),
                verticalPressure = findCheckItem(EskalatorCategory.BALUSTRADE, "Tekanan Vertikal Panel Balustrade")
            ),
            skirtPanel = findCheckItem(EskalatorCategory.BALUSTRADE, "Panel Skirt"),
            skirtPanelFlexibility = findCheckItem(EskalatorCategory.BALUSTRADE, "Fleksibilitas Panel Skirt"),
            stepToSkirtClearance = findCheckItem(EskalatorCategory.BALUSTRADE, "Jarak Bebas Step ke Skirt")
        ),
        handrail = Handrail(
            handrailCondition = findCheckItem(EskalatorCategory.HANDRAIL, "Kondisi Handrail"),
            handrailSpeedSynchronization = findCheckItem(EskalatorCategory.HANDRAIL, "Sinkronisasi Kecepatan Handrail"),
            handrailWidth = findCheckItem(EskalatorCategory.HANDRAIL, "Lebar Handrail")
        ),
        runway = Runway(
            beamStrengthAndPosition = findCheckItem(EskalatorCategory.RUNWAY, "Kekuatan & Posisi Balok"),
            pitWallCondition = findCheckItem(EskalatorCategory.RUNWAY, "Kondisi Dinding Pit"),
            escalatorFrameEnclosure = findCheckItem(EskalatorCategory.RUNWAY, "Penutup Rangka Eskalator"),
            lighting = findCheckItem(EskalatorCategory.RUNWAY, "Penerangan"),
            headroomClearance = findCheckItem(EskalatorCategory.RUNWAY, "Jarak Bebas Headroom"),
            balustradeToObjectClearance = findCheckItem(EskalatorCategory.RUNWAY, "Jarak Balustrade ke Objek"),
            antiClimbDeviceHeight = findCheckItem(EskalatorCategory.RUNWAY, "Tinggi Alat Anti-Panjat"),
            ornamentPlacement = findCheckItem(EskalatorCategory.RUNWAY, "Penempatan Ornamen"),
            outdoorClearance = findCheckItem(EskalatorCategory.RUNWAY, "Jarak Bebas Outdoor")
        ),
        safetyEquipment = SafetyEquipment(
            operationControlKey = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Kunci Kontrol Operasi"),
            emergencyStopSwitch = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Saklar Stop Darurat"),
            stepChainSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Pengaman Rantai Step"),
            driveChainSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Pengaman Rantai Penggerak"),
            stepSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Pengaman Step"),
            handrailSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Pengaman Handrail"),
            reversalStopDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Pengaman Pembalikan Arah"),
            handrailEntryGuard = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Pelindung Masuk Handrail"),
            combPlateSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Pengaman Plat Sisir"),
            innerDeckingBrush = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Sikat Inner Decking"),
            stopButtons = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Tombol Stop")
        ),
        electricalInstallation = ElectricalInstallation(
            installationStandard = findCheckItem(EskalatorCategory.ELECTRICAL_INSTALLATION, "Standar Instalasi"),
            electricalPanel = findCheckItem(EskalatorCategory.ELECTRICAL_INSTALLATION, "Panel Listrik"),
            groundingCable = findCheckItem(EskalatorCategory.ELECTRICAL_INSTALLATION, "Kabel Grounding"),
            fireAlarmConnection = findCheckItem(EskalatorCategory.ELECTRICAL_INSTALLATION, "Koneksi Alarm Kebakaran")
        ),
        outdoorSpecifics = OutdoorSpecifics(
            pitWaterPump = findCheckItem(EskalatorCategory.OUTDOOR_SPECIFICS, "Pompa Air Pit"),
            weatherproofComponents = findCheckItem(EskalatorCategory.OUTDOOR_SPECIFICS, "Komponen Tahan Cuaca")
        ),
        userSafetySignage = UserSafetySignage(
            noBulkyItems = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Membawa Barang Besar"),
            noJumping = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Melompat"),
            unattendedChildren = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Anak-anak Harus Diawasi"),
            noTrolleysOrStrollers = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Membawa Troli/Stroller"),
            noLeaning = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Bersandar"),
            noSteppingOnSkirt = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Menginjak Skirt"),
            softSoleFootwearWarning = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Peringatan Alas Kaki Lunak"),
            noSittingOnSteps = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Duduk di Tangga"),
            holdHandrail = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Pegang Handrail")
        )
    )

    val testingSummary = EskalatorTestingSummary(
        safetyDevices = findTestResult("Alat Pengaman"),
        noLoadTest = findTestResult("Uji Tanpa Beban"),
        brakeTest = findTestResult("Uji Rem")
    )

    val generalData = EskalatorGeneralData(
        extraId = this.inspection.extraId,
        moreExtraId = this.inspection.moreExtraId,
        equipmentType = this.inspection.equipmentType,
        examinationType = this.inspection.examinationType,
        conclusion = this.findings.find { it.type == FindingType.RECOMMENDATION }?.description ?: "",
        companyData = companyData,
        technicalData = technicalData,
        inspectionAndTesting = inspectionAndTesting,
        testingSummary = testingSummary
    )

    return EskalatorUiState(
        isLoading = false,
        eskalatorData = generalData
    )
}