package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.local.utils.toDocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.toInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toSubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBapVisualInspection
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorBalustrade
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorDriveEquipment
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorElectricalInstallation
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorFrameAndMachineRoom
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorHandrail
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorInspectionAndTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorLandingArea
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorOutdoorSpecifics
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorRunway
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorSafetyEquipment
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorStepsOrPallets
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.escalator.EscalatorUserSafety
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain

private object EscalatorCategory {
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

fun InspectionWithDetailsDomain.toEscalatorBapRequest(): EscalatorBapRequest {
    val checkItems = this.checkItems
    fun findBoolItem(category: String, itemName: String): Boolean {
        return checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    val generalData = EscalatorBapGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        nameUsageLocation = this.inspection.usageLocation ?: "",
        locationUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = EscalatorBapTechnicalData(
        equipmentType = this.inspection.equipmentType,
        manufacturer = this.inspection.manufacturer?.name ?: "",
        brand = this.inspection.manufacturer?.brandOrType ?: "",
        countryAndYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        capacity = this.inspection.capacity ?: "",
        speed = this.inspection.speed ?: "",
        transports = this.inspection.floorServed ?: ""
    )

    val visualInspection = EscalatorBapVisualInspection(
        isMachineRoomConditionAcceptable = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Kondisi Ruang Mesin Memenuhi Syarat"),
        isPanelConditionAcceptable = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Kondisi Panel Dapat Diterima"),
        isLightingConditionIsPitLightAcceptable = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Kondisi Penerangan Dapat Diterima"),
        areSafetySignsAvailable = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Rambu-rambu Keselamatan Tersedia")
    )

    val testing = EscalatorBapTesting(
        isNdtThermographPanel = findBoolItem(BAPCategory.TESTING, "NDT Thermograph Panel OK"),
        areSafetyDevicesFunctional = findBoolItem(BAPCategory.TESTING, "Alat Pengaman Berfungsi"),
        isLimitSwitchFunctional = findBoolItem(BAPCategory.TESTING, "Limit Switch Berfungsi"),
        isDoorSwitchFunctional = findBoolItem(BAPCategory.TESTING, "Saklar Pintu Berfungsi"),
        pitEmergencyStopStatusIsAvailable = findBoolItem(BAPCategory.TESTING, "Stop Darurat 'Pit' Tersedia"),
        pitEmergencyStopStatusIsFunctional = findBoolItem(BAPCategory.TESTING, "Stop Darurat 'Pit' Berfungsi"),
        isEscalatorFunctionOk = findBoolItem(BAPCategory.TESTING, "Fungsi Escalator OK")
    )

    return EscalatorBapRequest(
        laporanId = this.inspection.extraId,
        inspectionDate = this.inspection.reportDate ?: "",
        examinationType = this.inspection.examinationType,
        extraId = this.inspection.id,
        createdAt = this.inspection.createdAt ?: "",
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        testing = testing
    )
}

fun EscalatorBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id,
        documentType = this.documentType.toDocumentType() ?: DocumentType.BAP,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.EE,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Escalator,
        equipmentType = this.technicalData.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.nameUsageLocation,
        addressUsageLocation = this.generalData.locationUsageLocation,
        serialNumber = this.technicalData.serialNumber,
        capacity = this.technicalData.capacity,
        speed = this.technicalData.speed,
        floorServed = this.technicalData.transports,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.brand,
            year = this.technicalData.countryAndYear
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        status = null,
        isSynced = true
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Ruang Mesin Memenuhi Syarat", status = this.visualInspection.isMachineRoomConditionAcceptable))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Panel Dapat Diterima", status = this.visualInspection.isPanelConditionAcceptable))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Kondisi Penerangan Dapat Diterima", status = this.visualInspection.isLightingConditionIsPitLightAcceptable))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.VISUAL_INSPECTION, itemName = "Rambu-rambu Keselamatan Tersedia", status = this.visualInspection.areSafetySignsAvailable))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "NDT Thermograph Panel OK", status = this.testing.isNdtThermographPanel))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Alat Pengaman Berfungsi", status = this.testing.areSafetyDevicesFunctional))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Limit Switch Berfungsi", status = this.testing.isLimitSwitchFunctional))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Saklar Pintu Berfungsi", status = this.testing.isDoorSwitchFunctional))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Stop Darurat 'Pit' Tersedia", status = this.testing.pitEmergencyStopStatusIsAvailable))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Stop Darurat 'Pit' Berfungsi", status = this.testing.pitEmergencyStopStatusIsFunctional))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = BAPCategory.TESTING, itemName = "Fungsi Escalator OK", status = this.testing.isEscalatorFunctionOk))


    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = emptyList()
    )
}

fun InspectionWithDetailsDomain.toEscalatorReportRequest(): EscalatorReportRequest {
    val checkItems = this.checkItems
    val testResults = this.testResults

    fun findItem(category: String, itemName: String): ResultStatus {
        val item = checkItems.find { it.category == category && it.itemName == itemName }
        return ResultStatus(result = item?.result ?: "", status = item?.status ?: false)
    }

    fun findTestResult(testName: String): String {
        return testResults.find { it.testName.equals(testName, true) }?.result ?: ""
    }

    val generalData = EscalatorGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        nameUsageLocation = this.inspection.usageLocation ?: "",
        safetyObjectTypeAndNumber = findTestResult("Jenis Objek K3/No."),
        intendedUse = findTestResult("Digunakan Untuk"),
        permitNumber = this.inspection.permitNumber ?: "",
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: ""
    )

    val technicalData = EscalatorTechnicalData(
        manufacturer = this.inspection.manufacturer?.name ?: "",
        brand = this.inspection.manufacturer?.brandOrType ?: "",
        countryAndYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        transports = findTestResult("Technical - Alat Angkut"),
        capacity = this.inspection.capacity ?: "",
        liftHeight = this.inspection.floorServed ?: "",
        speed = this.inspection.speed ?: "",
        driveType = this.inspection.driveType ?: "",
        motorCurrent = findTestResult("Technical - Arus Motor"),
        motorPower = findTestResult("Technical - Daya Motor"),
        safetyDevices = findTestResult("Technical - Alat Pengaman (Umum)")
    )

    val inspectionAndTesting = EscalatorInspectionAndTesting(
        frameAndMachineRoom = EscalatorFrameAndMachineRoom(
            frameResult = findItem(EscalatorCategory.FRAME_MACHINE_ROOM, "Kerangka"),
            supportBeamsResults = findItem(EscalatorCategory.FRAME_MACHINE_ROOM, "Balok Penyangga"),
            machineRoomConditionResult = findItem(EscalatorCategory.FRAME_MACHINE_ROOM, "Kondisi Ruang Mesin"),
            machineRoomClearanceResult = findItem(EscalatorCategory.FRAME_MACHINE_ROOM, "Jarak Bebas Ruang Mesin"),
            machineRoomLightingResult = findItem(EscalatorCategory.FRAME_MACHINE_ROOM, "Penerangan Ruang Mesin"),
            machineCoverPlateResult = findItem(EscalatorCategory.FRAME_MACHINE_ROOM, "Plat Penutup Mesin"),
            pitConditionResult = findItem(EscalatorCategory.FRAME_MACHINE_ROOM, "Kondisi Pit"),
            pitClearanceResult = findItem(EscalatorCategory.FRAME_MACHINE_ROOM, "Jarak Bebas Pit"),
            pitStepCoverPlateResult = findItem(EscalatorCategory.FRAME_MACHINE_ROOM, "Plat Penutup Step di Pit")
        ),
        driveEquipment = EscalatorDriveEquipment(
            driveMachineResult = findItem(EscalatorCategory.DRIVE_EQUIPMENT, "Mesin Penggerak"),
            speedUnder30DegreesResult = findItem(EscalatorCategory.DRIVE_EQUIPMENT, "Kecepatan (≤ 30°)"),
            speed30to35DegreesResult = findItem(EscalatorCategory.DRIVE_EQUIPMENT, "Kecepatan (30°-35°)"),
            travelatorSpeedResult = findItem(EscalatorCategory.DRIVE_EQUIPMENT, "Kecepatan Travelator"),
            stoppingDistance0_5Result = findItem(EscalatorCategory.DRIVE_EQUIPMENT, "Jarak Berhenti (0.5 m/s)"),
            stoppingDistance0_75Result = findItem(EscalatorCategory.DRIVE_EQUIPMENT, "Jarak Berhenti (0.75 m/s)"),
            stoppingDistance0_90Result = findItem(EscalatorCategory.DRIVE_EQUIPMENT, "Jarak Berhenti (0.90 m/s)"),
            driveChainResult = findItem(EscalatorCategory.DRIVE_EQUIPMENT, "Rantai Penggerak"),
            chainBreakingStrengthResult = findItem(EscalatorCategory.DRIVE_EQUIPMENT, "Kekuatan Putus Rantai")
        ),
        stepsOrPallets = EscalatorStepsOrPallets(
            stepMaterialResult = findItem(EscalatorCategory.STEPS_PALLETS, "Bahan Step"),
            stepDimensionsResult = findItem(EscalatorCategory.STEPS_PALLETS, "Dimensi Step"),
            palletDimensionsResult = findItem(EscalatorCategory.STEPS_PALLETS, "Dimensi Pallet"),
            stepSurfaceResult = findItem(EscalatorCategory.STEPS_PALLETS, "Permukaan Step"),
            stepLevelnessResult = findItem(EscalatorCategory.STEPS_PALLETS, "Kerataan Step"),
            skirtBrushResult = findItem(EscalatorCategory.STEPS_PALLETS, "Sikat Skirt"),
            stepWheelsResult = findItem(EscalatorCategory.STEPS_PALLETS, "Roda Step")
        ),
        landingArea = EscalatorLandingArea(
            landingPlatesResult = findItem(EscalatorCategory.LANDING_AREA, "Plat Pendaratan"),
            combTeethResult = findItem(EscalatorCategory.LANDING_AREA, "Gigi Sisir"),
            combConditionResult = findItem(EscalatorCategory.LANDING_AREA, "Kondisi Sisir"),
            landingCoverResult = findItem(EscalatorCategory.LANDING_AREA, "Penutup Pendaratan"),
            landingAccessAreaResult = findItem(EscalatorCategory.LANDING_AREA, "Area Akses Pendaratan")
        ),
        balustrade = EscalatorBalustrade(
            balustradePanelMaterialResult = findItem(EscalatorCategory.BALUSTRADE, "Material Panel Balustrade"),
            balustradePanelHeightResult = findItem(EscalatorCategory.BALUSTRADE, "Tinggi Panel Balustrade"),
            balustradePanelSidePressureResult = findItem(EscalatorCategory.BALUSTRADE, "Tekanan Samping Panel Balustrade"),
            balustradePanelVerticalPressureResult = findItem(EscalatorCategory.BALUSTRADE, "Tekanan Vertikal Panel Balustrade"),
            skirtPanelResult = findItem(EscalatorCategory.BALUSTRADE, "Panel Skirt"),
            skirtPanelFlexibilityResult = findItem(EscalatorCategory.BALUSTRADE, "Fleksibilitas Panel Skirt"),
            stepToSkirtClearanceResult = findItem(EscalatorCategory.BALUSTRADE, "Jarak Bebas Step ke Skirt")
        ),
        handrail = EscalatorHandrail(
            handrailConditionResult = findItem(EscalatorCategory.HANDRAIL, "Kondisi Handrail"),
            handrailSpeedSynchronizationResult = findItem(EscalatorCategory.HANDRAIL, "Sinkronisasi Kecepatan Handrail"),
            handrailWidthResult = findItem(EscalatorCategory.HANDRAIL, "Lebar Handrail")
        ),
        runway = EscalatorRunway(
            beamStrengthAndPositionResult = findItem(EscalatorCategory.RUNWAY, "Kekuatan & Posisi Balok"),
            pitWallConditionResult = findItem(EscalatorCategory.RUNWAY, "Kondisi Dinding Pit"),
            escalatorFrameEnclosureResult = findItem(EscalatorCategory.RUNWAY, "Penutup Rangka Eskalator"),
            lightingResult = findItem(EscalatorCategory.RUNWAY, "Penerangan"),
            headroomClearanceResult = findItem(EscalatorCategory.RUNWAY, "Jarak Bebas Headroom"),
            balustradeToObjectClearanceResult = findItem(EscalatorCategory.RUNWAY, "Jarak Balustrade ke Objek"),
            antiClimbDeviceHeightResult = findItem(EscalatorCategory.RUNWAY, "Tinggi Alat Anti-Panjat"),
            ornamentPlacementResult = findItem(EscalatorCategory.RUNWAY, "Penempatan Ornamen"),
            outdoorClearanceResult = findItem(EscalatorCategory.RUNWAY, "Jarak Bebas Outdoor")
        ),
        safetyEquipment = EscalatorSafetyEquipment(
            operationControlKeyResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Kunci Kontrol Operasi"),
            emergencyStopSwitchResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Saklar Stop Darurat"),
            stepChainSafetyDeviceResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Pengaman Rantai Step"),
            driveChainSafetyDeviceResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Pengaman Rantai Penggerak"),
            stepSafetyDeviceResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Pengaman Step"),
            handrailSafetyDeviceResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Pengaman Handrail"),
            reversalStopDeviceResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Pengaman Pembalikan Arah"),
            handrailEntryGuardResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Pelindung Masuk Handrail"),
            combPlateSafetyDeviceResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Pengaman Plat Sisir"),
            innerDeckingBrushResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Sikat Inner Decking"),
            stopButtonsResult = findItem(EscalatorCategory.SAFETY_EQUIPMENT, "Tombol Stop")
        ),
        electricalInstallation = EscalatorElectricalInstallation(
            installationStandardResult = findItem(EscalatorCategory.ELECTRICAL_INSTALLATION, "Standar Instalasi"),
            electricalPanelResult = findItem(EscalatorCategory.ELECTRICAL_INSTALLATION, "Panel Listrik"),
            groundingCableResult = findItem(EscalatorCategory.ELECTRICAL_INSTALLATION, "Kabel Grounding"),
            fireAlarmConnectionResult = findItem(EscalatorCategory.ELECTRICAL_INSTALLATION, "Koneksi Alarm Kebakaran")
        ),
        outdoorSpecifics = EscalatorOutdoorSpecifics(
            pitWaterPumpResult = findItem(EscalatorCategory.OUTDOOR_SPECIFICS, "Pompa Air Pit"),
            weatherproofComponentsResult = findItem(EscalatorCategory.OUTDOOR_SPECIFICS, "Komponen Tahan Cuaca")
        ),
        userSafety = EscalatorUserSafety(
            noBulkyItemsResult = findItem(EscalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Membawa Barang Besar"),
            noJumpingResult = findItem(EscalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Melompat"),
            unattendedChildrenResult = findItem(EscalatorCategory.USER_SAFETY_SIGNAGE, "Anak-anak Harus Diawasi"),
            noTrolleysOrStrollersResult = findItem(EscalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Membawa Troli/Stroller"),
            noLeaningResult = findItem(EscalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Bersandar"),
            noSteppingOnSkirtResult = findItem(EscalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Menginjak Skirt"),
            softSoleFootwearWarningResult = findItem(EscalatorCategory.USER_SAFETY_SIGNAGE, "Peringatan Alas Kaki Lunak"),
            noSittingOnStepsResult = findItem(EscalatorCategory.USER_SAFETY_SIGNAGE, "Dilarang Duduk di Tangga"),
            holdHandrailResult = findItem(EscalatorCategory.USER_SAFETY_SIGNAGE, "Pegang Handrail")
        )
    )

    val testingSummary = listOfNotNull(
        findTestResult("Alat Pengaman"),
        findTestResult("Uji Tanpa Beban"),
        findTestResult("Uji Rem")
    ).joinToString("\n")

    val conclusion = findings.firstOrNull { it.type == FindingType.RECOMMENDATION }?.description ?: ""

    return EscalatorReportRequest(
        inspectionType = inspection.inspectionType.toDisplayString(),
        equipmentType = inspection.equipmentType,
        extraId = inspection.id,
        createdAt = inspection.createdAt ?: "",
        generalData = generalData,
        technicalData = technicalData,
        inspectionAndTesting = inspectionAndTesting,
        testingEscalator = testingSummary,
        conclusion = conclusion
    )
}

fun EscalatorReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id,
        moreExtraId = "",
        documentType = this.documentType.toDocumentType() ?: DocumentType.LAPORAN,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.EE,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Escalator,
        equipmentType = this.equipmentType,
        examinationType = this.generalData.examinationType,
        ownerName = this.generalData.ownerName,
        usageLocation = this.generalData.nameUsageLocation,
        addressUsageLocation = null, // Not available in EscalatorReportData
        driveType = this.technicalData.driveType,
        serialNumber = this.technicalData.serialNumber,
        permitNumber = this.generalData.permitNumber,
        capacity = this.technicalData.capacity,
        speed = this.technicalData.speed,
        floorServed = this.technicalData.liftHeight,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.brand,
            year = this.technicalData.countryAndYear
        ),
        createdAt = this.createdAt,
        reportDate = this.generalData.inspectionDate,
        status = this.conclusion,
        isSynced = true
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    fun MutableList<InspectionCheckItemDomain>.addCheckItem(category: String, itemName: String, data: ResultStatus) {
        this.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = data.status, result = data.result))
    }

    this.inspectionAndTesting.let {
        it.frameAndMachineRoom.let { data ->
            val cat = EscalatorCategory.FRAME_MACHINE_ROOM
            checkItems.addCheckItem(cat, "Kerangka", data.frameResult)
            checkItems.addCheckItem(cat, "Balok Penyangga", data.supportBeamsResults)
            checkItems.addCheckItem(cat, "Kondisi Ruang Mesin", data.machineRoomConditionResult)
            checkItems.addCheckItem(cat, "Jarak Bebas Ruang Mesin", data.machineRoomClearanceResult)
            checkItems.addCheckItem(cat, "Penerangan Ruang Mesin", data.machineRoomLightingResult)
            checkItems.addCheckItem(cat, "Plat Penutup Mesin", data.machineCoverPlateResult)
            checkItems.addCheckItem(cat, "Kondisi Pit", data.pitConditionResult)
            checkItems.addCheckItem(cat, "Jarak Bebas Pit", data.pitClearanceResult)
            checkItems.addCheckItem(cat, "Plat Penutup Step di Pit", data.pitStepCoverPlateResult)
        }
        it.driveEquipment.let { data ->
            val cat = EscalatorCategory.DRIVE_EQUIPMENT
            checkItems.addCheckItem(cat, "Mesin Penggerak", data.driveMachineResult)
            checkItems.addCheckItem(cat, "Kecepatan (≤ 30°)", data.speedUnder30DegreesResult)
            checkItems.addCheckItem(cat, "Kecepatan (30°-35°)", data.speed30to35DegreesResult)
            checkItems.addCheckItem(cat, "Kecepatan Travelator", data.travelatorSpeedResult)
            checkItems.addCheckItem(cat, "Jarak Berhenti (0.5 m/s)", data.stoppingDistance0_5Result)
            checkItems.addCheckItem(cat, "Jarak Berhenti (0.75 m/s)", data.stoppingDistance0_75Result)
            checkItems.addCheckItem(cat, "Jarak Berhenti (0.90 m/s)", data.stoppingDistance0_90Result)
            checkItems.addCheckItem(cat, "Rantai Penggerak", data.driveChainResult)
            checkItems.addCheckItem(cat, "Kekuatan Putus Rantai", data.chainBreakingStrengthResult)
        }
        it.stepsOrPallets.let { data ->
            val cat = EscalatorCategory.STEPS_PALLETS
            checkItems.addCheckItem(cat, "Bahan Step", data.stepMaterialResult)
            checkItems.addCheckItem(cat, "Dimensi Step", data.stepDimensionsResult)
            checkItems.addCheckItem(cat, "Dimensi Pallet", data.palletDimensionsResult)
            checkItems.addCheckItem(cat, "Permukaan Step", data.stepSurfaceResult)
            checkItems.addCheckItem(cat, "Kerataan Step", data.stepLevelnessResult)
            checkItems.addCheckItem(cat, "Sikat Skirt", data.skirtBrushResult)
            checkItems.addCheckItem(cat, "Roda Step", data.stepWheelsResult)
        }
        it.landingArea.let { data ->
            val cat = EscalatorCategory.LANDING_AREA
            checkItems.addCheckItem(cat, "Plat Pendaratan", data.landingPlatesResult)
            checkItems.addCheckItem(cat, "Gigi Sisir", data.combTeethResult)
            checkItems.addCheckItem(cat, "Kondisi Sisir", data.combConditionResult)
            checkItems.addCheckItem(cat, "Penutup Pendaratan", data.landingCoverResult)
            checkItems.addCheckItem(cat, "Area Akses Pendaratan", data.landingAccessAreaResult)
        }
        it.balustrade.let { data ->
            val cat = EscalatorCategory.BALUSTRADE
            checkItems.addCheckItem(cat, "Material Panel Balustrade", data.balustradePanelMaterialResult)
            checkItems.addCheckItem(cat, "Tinggi Panel Balustrade", data.balustradePanelHeightResult)
            checkItems.addCheckItem(cat, "Tekanan Samping Panel Balustrade", data.balustradePanelSidePressureResult)
            checkItems.addCheckItem(cat, "Tekanan Vertikal Panel Balustrade", data.balustradePanelVerticalPressureResult)
            checkItems.addCheckItem(cat, "Panel Skirt", data.skirtPanelResult)
            checkItems.addCheckItem(cat, "Fleksibilitas Panel Skirt", data.skirtPanelFlexibilityResult)
            checkItems.addCheckItem(cat, "Jarak Bebas Step ke Skirt", data.stepToSkirtClearanceResult)
        }
        it.handrail.let { data ->
            val cat = EscalatorCategory.HANDRAIL
            checkItems.addCheckItem(cat, "Kondisi Handrail", data.handrailConditionResult)
            checkItems.addCheckItem(cat, "Sinkronisasi Kecepatan Handrail", data.handrailSpeedSynchronizationResult)
            checkItems.addCheckItem(cat, "Lebar Handrail", data.handrailWidthResult)
        }
        it.runway.let { data ->
            val cat = EscalatorCategory.RUNWAY
            checkItems.addCheckItem(cat, "Kekuatan & Posisi Balok", data.beamStrengthAndPositionResult)
            checkItems.addCheckItem(cat, "Kondisi Dinding Pit", data.pitWallConditionResult)
            checkItems.addCheckItem(cat, "Penutup Rangka Eskalator", data.escalatorFrameEnclosureResult)
            checkItems.addCheckItem(cat, "Penerangan", data.lightingResult)
            checkItems.addCheckItem(cat, "Jarak Bebas Headroom", data.headroomClearanceResult)
            checkItems.addCheckItem(cat, "Jarak Balustrade ke Objek", data.balustradeToObjectClearanceResult)
            checkItems.addCheckItem(cat, "Tinggi Alat Anti-Panjat", data.antiClimbDeviceHeightResult)
            checkItems.addCheckItem(cat, "Penempatan Ornamen", data.ornamentPlacementResult)
            checkItems.addCheckItem(cat, "Jarak Bebas Outdoor", data.outdoorClearanceResult)
        }
        it.safetyEquipment.let { data ->
            val cat = EscalatorCategory.SAFETY_EQUIPMENT
            checkItems.addCheckItem(cat, "Kunci Kontrol Operasi", data.operationControlKeyResult)
            checkItems.addCheckItem(cat, "Saklar Stop Darurat", data.emergencyStopSwitchResult)
            checkItems.addCheckItem(cat, "Pengaman Rantai Step", data.stepChainSafetyDeviceResult)
            checkItems.addCheckItem(cat, "Pengaman Rantai Penggerak", data.driveChainSafetyDeviceResult)
            checkItems.addCheckItem(cat, "Pengaman Step", data.stepSafetyDeviceResult)
            checkItems.addCheckItem(cat, "Pengaman Handrail", data.handrailSafetyDeviceResult)
            checkItems.addCheckItem(cat, "Pengaman Pembalikan Arah", data.reversalStopDeviceResult)
            checkItems.addCheckItem(cat, "Pelindung Masuk Handrail", data.handrailEntryGuardResult)
            checkItems.addCheckItem(cat, "Pengaman Plat Sisir", data.combPlateSafetyDeviceResult)
            checkItems.addCheckItem(cat, "Sikat Inner Decking", data.innerDeckingBrushResult)
            checkItems.addCheckItem(cat, "Tombol Stop", data.stopButtonsResult)
        }
        it.electricalInstallation.let { data ->
            val cat = EscalatorCategory.ELECTRICAL_INSTALLATION
            checkItems.addCheckItem(cat, "Standar Instalasi", data.installationStandardResult)
            checkItems.addCheckItem(cat, "Panel Listrik", data.electricalPanelResult)
            checkItems.addCheckItem(cat, "Kabel Grounding", data.groundingCableResult)
            checkItems.addCheckItem(cat, "Koneksi Alarm Kebakaran", data.fireAlarmConnectionResult)
        }
        it.outdoorSpecifics.let { data ->
            val cat = EscalatorCategory.OUTDOOR_SPECIFICS
            checkItems.addCheckItem(cat, "Pompa Air Pit", data.pitWaterPumpResult)
            checkItems.addCheckItem(cat, "Komponen Tahan Cuaca", data.weatherproofComponentsResult)
        }
        it.userSafety.let { data ->
            val cat = EscalatorCategory.USER_SAFETY_SIGNAGE
            checkItems.addCheckItem(cat, "Dilarang Membawa Barang Besar", data.noBulkyItemsResult)
            checkItems.addCheckItem(cat, "Dilarang Melompat", data.noJumpingResult)
            checkItems.addCheckItem(cat, "Anak-anak Harus Diawasi", data.unattendedChildrenResult)
            checkItems.addCheckItem(cat, "Dilarang Membawa Troli/Stroller", data.noTrolleysOrStrollersResult)
            checkItems.addCheckItem(cat, "Dilarang Bersandar", data.noLeaningResult)
            checkItems.addCheckItem(cat, "Dilarang Menginjak Skirt", data.noSteppingOnSkirtResult)
            checkItems.addCheckItem(cat, "Peringatan Alas Kaki Lunak", data.softSoleFootwearWarningResult)
            checkItems.addCheckItem(cat, "Dilarang Duduk di Tangga", data.noSittingOnStepsResult)
            checkItems.addCheckItem(cat, "Pegang Handrail", data.holdHandrailResult)
        }
    }

    val findings = mutableListOf<InspectionFindingDomain>()
    if (this.conclusion.isNotBlank()) {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = this.conclusion, type = FindingType.RECOMMENDATION))
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    if (this.testingEscalator.isNotBlank()) {
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Testing Escalator", result = this.testingEscalator, notes = null))
    }
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Jenis Objek K3/No.", result = this.generalData.safetyObjectTypeAndNumber, notes = null))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Digunakan Untuk", result = this.generalData.intendedUse, notes = null))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Technical - Alat Angkut", result = this.technicalData.transports, notes = null))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Technical - Arus Motor", result = this.technicalData.motorCurrent, notes = null))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Technical - Daya Motor", result = this.technicalData.motorPower, notes = null))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Technical - Alat Pengaman (Umum)", result = this.technicalData.safetyDevices, notes = null))

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = testResults
    )
}
