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
    const val FRAME_MACHINE_ROOM = "Frame & Machine Room"
    const val DRIVE_EQUIPMENT = "Drive Equipment"
    const val STEPS_PALLETS = "Steps or Pallets"
    const val LANDING_AREA = "Landing Area"
    const val BALUSTRADE = "Balustrade"
    const val HANDRAIL = "Handrail"
    const val RUNWAY = "Runway"
    const val SAFETY_EQUIPMENT = "Safety Equipment"
    const val ELECTRICAL_INSTALLATION = "Electrical Installation"
    const val OUTDOOR_SPECIFICS = "Outdoor Specifics"
    const val USER_SAFETY_SIGNAGE = "User Safety Signage"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

/**
 * Mengubah EskalatorUiState menjadi model domain InspectionWithDetailsDomain.
 */
fun EskalatorUiState.toInspectionWithDetailsDomain(currentTime: String): InspectionWithDetailsDomain {
    val uiData = this.eskalatorData
    val inspectionId = uiData.id

    val manufacturerDomain = ManufacturerDomain(
        name = uiData.technicalData.manufacturer,
        brandOrType = uiData.technicalData.brand,
        year = uiData.technicalData.countryAndYear
    )

    val inspectionDomain = InspectionDomain(
        id = 0,
        extraId = "",
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.EE,
        subInspectionType = SubInspectionType.Escalator,
        equipmentType = uiData.equipmentType,
        examinationType = uiData.examinationType,
        ownerName = uiData.companyData.companyOrBuildingName,
        ownerAddress = uiData.companyData.usageAddress,
        usageLocation = uiData.companyData.companyOrBuildingName,
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

    val checkItems = createCheckItemsFromUiState(uiData, inspectionId)

    val findings = if (uiData.conclusion.isNotBlank()) {
        listOf(InspectionFindingDomain(inspectionId = inspectionId, description = uiData.conclusion, type = FindingType.RECOMMENDATION))
    } else {
        emptyList()
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    uiData.testingSummary.let {
        if (it.safetyDevices.isNotBlank()) testResults.add(InspectionTestResultDomain(0, inspectionId, "Safety Devices Test", it.safetyDevices, null))
        if (it.noLoadTest.isNotBlank()) testResults.add(InspectionTestResultDomain(0, inspectionId, "No-Load Test", it.noLoadTest, null))
        if (it.brakeTest.isNotBlank()) testResults.add(InspectionTestResultDomain(0, inspectionId, "Brake Test", it.brakeTest, null))
    }

    return InspectionWithDetailsDomain(inspectionDomain, checkItems, findings, testResults)
}

private fun createCheckItemsFromUiState(uiData: EskalatorGeneralData, inspectionId: Long): List<InspectionCheckItemDomain> {
    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    val inspectionTestingData = uiData.inspectionAndTesting

    inspectionTestingData.frameAndMachineRoom.let { data ->
        val cat = EskalatorCategory.FRAME_MACHINE_ROOM
        checkItems.add(data.frame.toCheckItem(inspectionId, cat, "Frame"))
        checkItems.add(data.supportBeams.toCheckItem(inspectionId, cat, "Support Beams"))
        checkItems.add(data.machineRoomCondition.toCheckItem(inspectionId, cat, "Machine Room Condition"))
        checkItems.add(data.machineRoomClearance.toCheckItem(inspectionId, cat, "Machine Room Clearance"))
        checkItems.add(data.machineRoomLighting.toCheckItem(inspectionId, cat, "Machine Room Lighting"))
        checkItems.add(data.machineCoverPlate.toCheckItem(inspectionId, cat, "Machine Cover Plate"))
        checkItems.add(data.pitCondition.toCheckItem(inspectionId, cat, "Pit Condition"))
        checkItems.add(data.pitClearance.toCheckItem(inspectionId, cat, "Pit Clearance"))
        checkItems.add(data.pitStepCoverPlate.toCheckItem(inspectionId, cat, "Pit Step Cover Plate"))
    }

    inspectionTestingData.driveEquipment.let { data ->
        val cat = EskalatorCategory.DRIVE_EQUIPMENT
        checkItems.add(data.driveMachine.toCheckItem(inspectionId, cat, "Drive Machine"))
        checkItems.add(data.speedUnder30Degrees.toCheckItem(inspectionId, cat, "Speed (Inclination < 30°)"))
        checkItems.add(data.speed30to35Degrees.toCheckItem(inspectionId, cat, "Speed (Inclination 30°-35°)"))
        checkItems.add(data.travelatorSpeed.toCheckItem(inspectionId, cat, "Travelator Speed"))
        checkItems.add(data.stoppingDistance0_5.toCheckItem(inspectionId, cat, "Stopping Distance (0.5 m/s)"))
        checkItems.add(data.stoppingDistance0_75.toCheckItem(inspectionId, cat, "Stopping Distance (0.75 m/s)"))
        checkItems.add(data.stoppingDistance0_90.toCheckItem(inspectionId, cat, "Stopping Distance (0.90 m/s)"))
        checkItems.add(data.driveChain.toCheckItem(inspectionId, cat, "Drive Chain"))
        checkItems.add(data.chainBreakingStrength.toCheckItem(inspectionId, cat, "Chain Breaking Strength"))
    }

    inspectionTestingData.stepsOrPallets.let { data ->
        val cat = EskalatorCategory.STEPS_PALLETS
        checkItems.add(data.stepMaterial.toCheckItem(inspectionId, cat, "Step Material"))
        checkItems.add(data.stepDimensions.toCheckItem(inspectionId, cat, "Step Dimensions"))
        checkItems.add(data.palletDimensions.toCheckItem(inspectionId, cat, "Pallet Dimensions"))
        checkItems.add(data.stepSurface.toCheckItem(inspectionId, cat, "Step Surface"))
        checkItems.add(data.stepLevelness.toCheckItem(inspectionId, cat, "Step Levelness"))
        checkItems.add(data.skirtBrush.toCheckItem(inspectionId, cat, "Skirt Brush"))
        checkItems.add(data.stepWheels.toCheckItem(inspectionId, cat, "Step Wheels"))
    }

    inspectionTestingData.landingArea.let { data ->
        val cat = EskalatorCategory.LANDING_AREA
        checkItems.add(data.landingPlates.toCheckItem(inspectionId, cat, "Landing Plates"))
        checkItems.add(data.combTeeth.toCheckItem(inspectionId, cat, "Comb Teeth"))
        checkItems.add(data.combCondition.toCheckItem(inspectionId, cat, "Comb Condition"))
        checkItems.add(data.landingCover.toCheckItem(inspectionId, cat, "Landing Cover"))
        checkItems.add(data.landingAccessArea.toCheckItem(inspectionId, cat, "Landing Access Area"))
    }

    inspectionTestingData.balustrade.let { data ->
        val cat = EskalatorCategory.BALUSTRADE
        checkItems.add(data.balustradePanel.material.toCheckItem(inspectionId, cat, "Balustrade Panel Material"))
        checkItems.add(data.balustradePanel.height.toCheckItem(inspectionId, cat, "Balustrade Panel Height"))
        checkItems.add(data.balustradePanel.sidePressure.toCheckItem(inspectionId, cat, "Balustrade Panel Side Pressure"))
        checkItems.add(data.balustradePanel.verticalPressure.toCheckItem(inspectionId, cat, "Balustrade Panel Vertical Pressure"))
        checkItems.add(data.skirtPanel.toCheckItem(inspectionId, cat, "Skirt Panel"))
        checkItems.add(data.skirtPanelFlexibility.toCheckItem(inspectionId, cat, "Skirt Panel Flexibility"))
        checkItems.add(data.stepToSkirtClearance.toCheckItem(inspectionId, cat, "Step to Skirt Clearance"))
    }

    // Melanjutkan untuk kategori lainnya...
    inspectionTestingData.handrail.let { data ->
        val cat = EskalatorCategory.HANDRAIL
        checkItems.add(data.handrailCondition.toCheckItem(inspectionId, cat, "Handrail Condition"))
        checkItems.add(data.handrailSpeedSynchronization.toCheckItem(inspectionId, cat, "Handrail Speed Synchronization"))
        checkItems.add(data.handrailWidth.toCheckItem(inspectionId, cat, "Handrail Width"))
    }

    inspectionTestingData.runway.let { data ->
        val cat = EskalatorCategory.RUNWAY
        checkItems.add(data.beamStrengthAndPosition.toCheckItem(inspectionId, cat, "Beam Strength & Position"))
        checkItems.add(data.pitWallCondition.toCheckItem(inspectionId, cat, "Pit Wall Condition"))
        checkItems.add(data.escalatorFrameEnclosure.toCheckItem(inspectionId, cat, "Escalator Frame Enclosure"))
        checkItems.add(data.lighting.toCheckItem(inspectionId, cat, "Lighting"))
        checkItems.add(data.headroomClearance.toCheckItem(inspectionId, cat, "Headroom Clearance"))
        checkItems.add(data.balustradeToObjectClearance.toCheckItem(inspectionId, cat, "Balustrade to Object Clearance"))
        checkItems.add(data.antiClimbDeviceHeight.toCheckItem(inspectionId, cat, "Anti-Climb Device Height"))
        checkItems.add(data.ornamentPlacement.toCheckItem(inspectionId, cat, "Ornament Placement"))
        checkItems.add(data.outdoorClearance.toCheckItem(inspectionId, cat, "Outdoor Clearance"))
    }

    inspectionTestingData.safetyEquipment.let { data ->
        val cat = EskalatorCategory.SAFETY_EQUIPMENT
        checkItems.add(data.operationControlKey.toCheckItem(inspectionId, cat, "Operation Control Key"))
        checkItems.add(data.emergencyStopSwitch.toCheckItem(inspectionId, cat, "Emergency Stop Switch"))
        checkItems.add(data.stepChainSafetyDevice.toCheckItem(inspectionId, cat, "Step Chain Safety Device"))
        checkItems.add(data.driveChainSafetyDevice.toCheckItem(inspectionId, cat, "Drive Chain Safety Device"))
        checkItems.add(data.stepSafetyDevice.toCheckItem(inspectionId, cat, "Step Safety Device"))
        checkItems.add(data.handrailSafetyDevice.toCheckItem(inspectionId, cat, "Handrail Safety Device"))
        checkItems.add(data.reversalStopDevice.toCheckItem(inspectionId, cat, "Reversal Stop Device"))
        checkItems.add(data.handrailEntryGuard.toCheckItem(inspectionId, cat, "Handrail Entry Guard"))
        checkItems.add(data.combPlateSafetyDevice.toCheckItem(inspectionId, cat, "Comb Plate Safety Device"))
        checkItems.add(data.innerDeckingBrush.toCheckItem(inspectionId, cat, "Inner Decking Brush"))
        checkItems.add(data.stopButtons.toCheckItem(inspectionId, cat, "Stop Buttons"))
    }

    inspectionTestingData.electricalInstallation.let { data ->
        val cat = EskalatorCategory.ELECTRICAL_INSTALLATION
        checkItems.add(data.installationStandard.toCheckItem(inspectionId, cat, "Installation Standard"))
        checkItems.add(data.electricalPanel.toCheckItem(inspectionId, cat, "Electrical Panel"))
        checkItems.add(data.groundingCable.toCheckItem(inspectionId, cat, "Grounding Cable"))
        checkItems.add(data.fireAlarmConnection.toCheckItem(inspectionId, cat, "Fire Alarm Connection"))
    }

    inspectionTestingData.outdoorSpecifics.let { data ->
        val cat = EskalatorCategory.OUTDOOR_SPECIFICS
        checkItems.add(data.pitWaterPump.toCheckItem(inspectionId, cat, "Pit Water Pump"))
        checkItems.add(data.weatherproofComponents.toCheckItem(inspectionId, cat, "Weatherproof Components"))
    }

    inspectionTestingData.userSafetySignage.let { data ->
        val cat = EskalatorCategory.USER_SAFETY_SIGNAGE
        checkItems.add(data.noBulkyItems.toCheckItem(inspectionId, cat, "No Bulky Items Sign"))
        checkItems.add(data.noJumping.toCheckItem(inspectionId, cat, "No Jumping Sign"))
        checkItems.add(data.unattendedChildren.toCheckItem(inspectionId, cat, "Unattended Children Sign"))
        checkItems.add(data.noTrolleysOrStrollers.toCheckItem(inspectionId, cat, "No Trolleys or Strollers Sign"))
        checkItems.add(data.noLeaning.toCheckItem(inspectionId, cat, "No Leaning Sign"))
        checkItems.add(data.noSteppingOnSkirt.toCheckItem(inspectionId, cat, "No Stepping on Skirt Sign"))
        checkItems.add(data.softSoleFootwearWarning.toCheckItem(inspectionId, cat, "Soft Sole Footwear Warning"))
        checkItems.add(data.noSittingOnSteps.toCheckItem(inspectionId, cat, "No Sitting on Steps Sign"))
        checkItems.add(data.holdHandrail.toCheckItem(inspectionId, cat, "Hold Handrail Sign"))
    }

    return checkItems
}

private fun EskalatorResultStatus.toCheckItem(inspectionId: Long, category: String, itemName: String): InspectionCheckItemDomain {
    return InspectionCheckItemDomain(0, inspectionId, category, itemName, this.status, this.result)
}

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

/**
 * Mengubah model domain InspectionWithDetailsDomain kembali menjadi EskalatorUiState.
 */
fun InspectionWithDetailsDomain.toEskalatorUiState(): EskalatorUiState {
    val domainData = this

    fun findCheckItem(category: String, itemName: String): EskalatorResultStatus {
        val item = domainData.checkItems.find { it.category == category && it.itemName.equals(itemName, true) }
        return item?.let { EskalatorResultStatus(it.result ?: "", it.status) } ?: EskalatorResultStatus()
    }

    fun findTestResult(testName: String): String {
        return domainData.testResults.find { it.testName.equals(testName, true) }?.result ?: ""
    }

    val companyData = EskalatorCompanyData(
        companyOrBuildingName = domainData.inspection.ownerName ?: "",
        usageAddress = domainData.inspection.addressUsageLocation ?: "",
        usagePermit = domainData.inspection.permitNumber ?: "",
        inspectionDate = domainData.inspection.reportDate ?: ""
    )

    val technicalData = EskalatorTechnicalData(
        manufacturer = domainData.inspection.manufacturer?.name ?: "",
        brand = domainData.inspection.manufacturer?.brandOrType ?: "",
        countryAndYear = domainData.inspection.manufacturer?.year?.let { " / $it" }?.trim() ?: "",
        serialNumber = domainData.inspection.serialNumber ?: "",
        capacity = domainData.inspection.capacity ?: "",
        liftHeight = domainData.inspection.floorServed ?: "",
        speed = domainData.inspection.speed ?: "",
        driveType = domainData.inspection.driveType ?: ""
    )

    val inspectionAndTesting = EskalatorInspectionAndTesting(
        frameAndMachineRoom = FrameAndMachineRoom(
            frame = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Frame"),
            supportBeams = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Support Beams"),
            machineRoomCondition = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Machine Room Condition"),
            machineRoomClearance = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Machine Room Clearance"),
            machineRoomLighting = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Machine Room Lighting"),
            machineCoverPlate = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Machine Cover Plate"),
            pitCondition = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Pit Condition"),
            pitClearance = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Pit Clearance"),
            pitStepCoverPlate = findCheckItem(EskalatorCategory.FRAME_MACHINE_ROOM, "Pit Step Cover Plate")
        ),
        driveEquipment = DriveEquipment(
            driveMachine = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Drive Machine"),
            speedUnder30Degrees = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Speed (Inclination < 30°)"),
            speed30to35Degrees = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Speed (Inclination 30°-35°)"),
            travelatorSpeed = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Travelator Speed"),
            stoppingDistance0_5 = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Stopping Distance (0.5 m/s)"),
            stoppingDistance0_75 = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Stopping Distance (0.75 m/s)"),
            stoppingDistance0_90 = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Stopping Distance (0.90 m/s)"),
            driveChain = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Drive Chain"),
            chainBreakingStrength = findCheckItem(EskalatorCategory.DRIVE_EQUIPMENT, "Chain Breaking Strength")
        ),
        stepsOrPallets = StepsOrPallets(
            stepMaterial = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Step Material"),
            stepDimensions = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Step Dimensions"),
            palletDimensions = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Pallet Dimensions"),
            stepSurface = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Step Surface"),
            stepLevelness = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Step Levelness"),
            skirtBrush = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Skirt Brush"),
            stepWheels = findCheckItem(EskalatorCategory.STEPS_PALLETS, "Step Wheels")
        ),
        landingArea = LandingArea(
            landingPlates = findCheckItem(EskalatorCategory.LANDING_AREA, "Landing Plates"),
            combTeeth = findCheckItem(EskalatorCategory.LANDING_AREA, "Comb Teeth"),
            combCondition = findCheckItem(EskalatorCategory.LANDING_AREA, "Comb Condition"),
            landingCover = findCheckItem(EskalatorCategory.LANDING_AREA, "Landing Cover"),
            landingAccessArea = findCheckItem(EskalatorCategory.LANDING_AREA, "Landing Access Area")
        ),
        balustrade = Balustrade(
            balustradePanel = BalustradePanel(
                material = findCheckItem(EskalatorCategory.BALUSTRADE, "Balustrade Panel Material"),
                height = findCheckItem(EskalatorCategory.BALUSTRADE, "Balustrade Panel Height"),
                sidePressure = findCheckItem(EskalatorCategory.BALUSTRADE, "Balustrade Panel Side Pressure"),
                verticalPressure = findCheckItem(EskalatorCategory.BALUSTRADE, "Balustrade Panel Vertical Pressure")
            ),
            skirtPanel = findCheckItem(EskalatorCategory.BALUSTRADE, "Skirt Panel"),
            skirtPanelFlexibility = findCheckItem(EskalatorCategory.BALUSTRADE, "Skirt Panel Flexibility"),
            stepToSkirtClearance = findCheckItem(EskalatorCategory.BALUSTRADE, "Step to Skirt Clearance")
        ),
        handrail = Handrail(
            handrailCondition = findCheckItem(EskalatorCategory.HANDRAIL, "Handrail Condition"),
            handrailSpeedSynchronization = findCheckItem(EskalatorCategory.HANDRAIL, "Handrail Speed Synchronization"),
            handrailWidth = findCheckItem(EskalatorCategory.HANDRAIL, "Handrail Width")
        ),
        runway = Runway(
            beamStrengthAndPosition = findCheckItem(EskalatorCategory.RUNWAY, "Beam Strength & Position"),
            pitWallCondition = findCheckItem(EskalatorCategory.RUNWAY, "Pit Wall Condition"),
            escalatorFrameEnclosure = findCheckItem(EskalatorCategory.RUNWAY, "Escalator Frame Enclosure"),
            lighting = findCheckItem(EskalatorCategory.RUNWAY, "Lighting"),
            headroomClearance = findCheckItem(EskalatorCategory.RUNWAY, "Headroom Clearance"),
            balustradeToObjectClearance = findCheckItem(EskalatorCategory.RUNWAY, "Balustrade to Object Clearance"),
            antiClimbDeviceHeight = findCheckItem(EskalatorCategory.RUNWAY, "Anti-Climb Device Height"),
            ornamentPlacement = findCheckItem(EskalatorCategory.RUNWAY, "Ornament Placement"),
            outdoorClearance = findCheckItem(EskalatorCategory.RUNWAY, "Outdoor Clearance")
        ),
        safetyEquipment = SafetyEquipment(
            operationControlKey = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Operation Control Key"),
            emergencyStopSwitch = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Emergency Stop Switch"),
            stepChainSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Step Chain Safety Device"),
            driveChainSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Drive Chain Safety Device"),
            stepSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Step Safety Device"),
            handrailSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Handrail Safety Device"),
            reversalStopDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Reversal Stop Device"),
            handrailEntryGuard = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Handrail Entry Guard"),
            combPlateSafetyDevice = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Comb Plate Safety Device"),
            innerDeckingBrush = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Inner Decking Brush"),
            stopButtons = findCheckItem(EskalatorCategory.SAFETY_EQUIPMENT, "Stop Buttons")
        ),
        electricalInstallation = ElectricalInstallation(
            installationStandard = findCheckItem(EskalatorCategory.ELECTRICAL_INSTALLATION, "Installation Standard"),
            electricalPanel = findCheckItem(EskalatorCategory.ELECTRICAL_INSTALLATION, "Electrical Panel"),
            groundingCable = findCheckItem(EskalatorCategory.ELECTRICAL_INSTALLATION, "Grounding Cable"),
            fireAlarmConnection = findCheckItem(EskalatorCategory.ELECTRICAL_INSTALLATION, "Fire Alarm Connection")
        ),
        outdoorSpecifics = OutdoorSpecifics(
            pitWaterPump = findCheckItem(EskalatorCategory.OUTDOOR_SPECIFICS, "Pit Water Pump"),
            weatherproofComponents = findCheckItem(EskalatorCategory.OUTDOOR_SPECIFICS, "Weatherproof Components")
        ),
        userSafetySignage = UserSafetySignage(
            noBulkyItems = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "No Bulky Items Sign"),
            noJumping = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "No Jumping Sign"),
            unattendedChildren = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Unattended Children Sign"),
            noTrolleysOrStrollers = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "No Trolleys or Strollers Sign"),
            noLeaning = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "No Leaning Sign"),
            noSteppingOnSkirt = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "No Stepping on Skirt Sign"),
            softSoleFootwearWarning = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Soft Sole Footwear Warning"),
            noSittingOnSteps = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "No Sitting on Steps Sign"),
            holdHandrail = findCheckItem(EskalatorCategory.USER_SAFETY_SIGNAGE, "Hold Handrail Sign")
        )
    )

    val testingSummary = EskalatorTestingSummary(
        safetyDevices = findTestResult("Safety Devices Test"),
        noLoadTest = findTestResult("No-Load Test"),
        brakeTest = findTestResult("Brake Test")
    )

    val generalData = EskalatorGeneralData(
        id = domainData.checkItems.firstOrNull()?.inspectionId ?: 0L,
        documentType = domainData.inspection.documentType,
        inspectionType = domainData.inspection.inspectionType,
        subInspectionType = domainData.inspection.subInspectionType,
        equipmentType = domainData.inspection.equipmentType,
        examinationType = domainData.inspection.examinationType,
        conclusion = domainData.findings.find { it.type == FindingType.RECOMMENDATION }?.description ?: "",
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