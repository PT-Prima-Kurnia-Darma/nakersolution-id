package com.nakersolutionid.nakersolutionid.data.local.mapper

import com.nakersolutionid.nakersolutionid.data.local.entity.FindingType
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionCheckItem
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionEntity
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionFinding
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionTestResult
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails
import com.nakersolutionid.nakersolutionid.data.local.entity.Manufacturer
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.ReportType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubReportType
import com.nakersolutionid.nakersolutionid.domain.model.*

// --- PUBLIC MAPPER ---

/**
 * Maps a [Report] from the domain layer to an [InspectionWithDetails] object for the data layer.
 * This is the main entry point for the mapping process.
 *
 * This implementation is type-safe and avoids reflection for better performance and maintainability.
 *
 * @param inspectionId The ID of the inspection, typically 0 for a new entry to allow auto-generation.
 * @return The mapped [InspectionWithDetails] object, ready to be inserted into the Room database.
 */
fun Report.toInspectionWithDetails(
    inspectionId: Long = 0,
    extraId: String
): InspectionWithDetails {
    // 1. Map the main InspectionEntity from the report's general data.
    val inspectionEntity = InspectionEntity(
        id = inspectionId,
        extraId = extraId,
        documentType = DocumentType.LAPORAN,
        reportType = ReportType.EE,
        subReportType = SubReportType.Elevator,
        equipmentType = this.eskOrElevType ?: "Tidak Diketahui",
        inspectionType = this.typeInspection ?: "Tidak Diketahui",
        ownerName = this.generalData?.ownerName,
        ownerAddress = this.generalData?.ownerAddress,
        usageLocation = this.generalData?.nameUsageLocation,
        addressUsageLocation = this.generalData?.addressUsageLocation,
        driveType = this.generalData?.elevatorType,
        serialNumber = this.generalData?.serialNumber,
        permitNumber = this.generalData?.permitNumber,
        capacity = this.generalData?.capacity,
        speed = this.generalData?.speed,
        floorServed = this.generalData?.floorsServed,
        manufacturer = Manufacturer(
            name = this.generalData?.manufacturerOrInstaller,
            brandOrType = this.generalData?.brandOrType,
            year = this.generalData?.countryAndYear?.split("/")?.getOrNull(1)?.trim()
        ),
        createdAt = this.createdAt,
        reportDate = this.generalData?.inspectionDate,
        // These fields are not in the domain model, so they are set to null.
        nextInspectionDate = null,
        inspectorName = null,
        status = this.conclusion
    )

    // 2. Map all nested check items from the technical docs and inspection/testing sections.
    val checkItems = mutableListOf<InspectionCheckItem>()
    technicalDocumentInspection?.let { checkItems.addAll(mapTechnicalDocumentInspection(it, inspectionId)) }
    inspectionAndTesting?.let { checkItems.addAll(mapInspectionAndTesting(it, inspectionId)) }

    // 3. Map the conclusion text to the findings list.
    val findings = mutableListOf<InspectionFinding>()
    this.conclusion?.let {
        findings.add(
            InspectionFinding(
                inspectionId = inspectionId,
                description = it,
                type = FindingType.FINDING
            )
        )
    }

    // 4. The domain model does not contain specific test results, so return an empty list.
    val testResults = emptyList<InspectionTestResult>()

    return InspectionWithDetails(
        inspectionEntity = inspectionEntity,
        checkItems = checkItems,
        findings = findings,
        testResults = testResults
    )
}


// --- PRIVATE HELPER FUNCTIONS ---

/**
 * Orchestrates the mapping of all categories within the [InspectionAndTestingDomain].
 * It calls specific mapping functions for each sub-section.
 *
 * @param inspectionId The foreign key to link check items to the parent inspection.
 * @return A complete list of all [InspectionCheckItem]s from the inspection and testing section.
 */
private fun mapInspectionAndTesting(domain: InspectionAndTestingDomain, inspectionId: Long): List<InspectionCheckItem> {
    val allItems = mutableListOf<InspectionCheckItem>()

    domain.machineRoomAndMachinery?.let { allItems.addAll(mapMachineRoomAndMachinery(it, inspectionId)) }
    domain.suspensionRopesAndBelts?.let { allItems.addAll(mapSuspensionRopesAndBelts(it, inspectionId)) }
    domain.drumsAndSheaves?.let { allItems.addAll(mapDrumsAndSheaves(it, inspectionId)) }
    domain.hoistwayAndPit?.let { allItems.addAll(mapHoistwayAndPit(it, inspectionId)) }
    domain.car?.let { allItems.addAll(mapCar(it, inspectionId)) }
    domain.governorAndSafetyBrake?.let { allItems.addAll(mapGovernorAndSafetyBrake(it, inspectionId)) }
    domain.counterweightGuideRailsAndBuffers?.let { allItems.addAll(mapCounterweightGuideRailsAndBuffers(it, inspectionId)) }
    domain.electricalInstallation?.let { allItems.addAll(mapElectricalInstallation(it, inspectionId)) }

    return allItems
}

/**
 * A helper extension function to reduce boilerplate when creating and adding an [InspectionCheckItem]
 * from a [ResultStatusDomain] object.
 * It handles null data gracefully by doing nothing.
 *
 * @param inspectionId The foreign key for the item.
 * @param category The category name for the check item.
 * @param itemName The specific name of the check item.
 * @param data The [ResultStatusDomain] containing the result and status.
 */
private fun MutableList<InspectionCheckItem>.addCheckItem(
    inspectionId: Long,
    category: String,
    itemName: String,
    data: ResultStatusDomain?
) {
    data?.let {
        this.add(
            InspectionCheckItem(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = it.status ?: false, // Default to false if status is null
                result = it.result
            )
        )
    }
}

/**
 * A helper extension function to reduce boilerplate when creating and adding an [InspectionCheckItem]
 * from a simple [String] result.
 * It handles null or blank data gracefully by doing nothing.
 *
 * @param inspectionId The foreign key for the item.
 * @param category The category name for the check item.
 * @param itemName The specific name of the check item.
 * @param resultText The String containing the result. Status is considered `true` if the string is not blank.
 */
private fun MutableList<InspectionCheckItem>.addCheckItem(
    inspectionId: Long,
    category: String,
    itemName: String,
    resultText: String?
) {
    if (!resultText.isNullOrBlank()) {
        this.add(
            InspectionCheckItem(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = true, // Consider any non-blank text as a passing status
                result = resultText
            )
        )
    }
}

// --- EXPLICIT MAPPERS FOR EACH DOMAIN SECTION ---

private fun mapTechnicalDocumentInspection(domain: TechnicalDocumentInspectionDomain, inspectionId: Long): List<InspectionCheckItem> {
    val items = mutableListOf<InspectionCheckItem>()
    val category = "Technical Document Inspection"
    items.addCheckItem(inspectionId, category, "Design Drawing", domain.designDrawing)
    items.addCheckItem(inspectionId, category, "Technical Calculation", domain.technicalCalculation)
    items.addCheckItem(inspectionId, category, "Material Certificate", domain.materialCertificate)
    items.addCheckItem(inspectionId, category, "Control Panel Diagram", domain.controlPanelDiagram)
    items.addCheckItem(inspectionId, category, "As Built Drawing", domain.asBuiltDrawing)
    items.addCheckItem(inspectionId, category, "Component Certificates", domain.componentCertificates)
    items.addCheckItem(inspectionId, category, "Safe Work Procedure", domain.safeWorkProcedure)
    return items
}

private fun mapMachineRoomAndMachinery(domain: MachineRoomAndMachineryDomain, inspectionId: Long): List<InspectionCheckItem> {
    val items = mutableListOf<InspectionCheckItem>()
    val category = "Machine Room And Machinery"
    items.addCheckItem(inspectionId, category, "Machine Mounting", domain.machineMounting)
    items.addCheckItem(inspectionId, category, "Mechanical Brake", domain.mechanicalBrake)
    items.addCheckItem(inspectionId, category, "Electrical Brake", domain.electricalBrake)
    items.addCheckItem(inspectionId, category, "Machine Room Construction", domain.machineRoomConstruction)
    items.addCheckItem(inspectionId, category, "Machine Room Clearance", domain.machineRoomClearance)
    items.addCheckItem(inspectionId, category, "Machine Room Implementation", domain.machineRoomImplementation)
    items.addCheckItem(inspectionId, category, "Ventilation", domain.ventilation)
    items.addCheckItem(inspectionId, category, "Machine Room Door", domain.machineRoomDoor)
    items.addCheckItem(inspectionId, category, "Main Power Panel Position", domain.mainPowerPanelPosition)
    items.addCheckItem(inspectionId, category, "Rotating Parts Guard", domain.rotatingPartsGuard)
    items.addCheckItem(inspectionId, category, "Rope Hole Guard", domain.ropeHoleGuard)
    items.addCheckItem(inspectionId, category, "Machine Room Access Ladder", domain.machineRoomAccessLadder)
    items.addCheckItem(inspectionId, category, "Floor Level Difference", domain.floorLevelDifference)
    items.addCheckItem(inspectionId, category, "Fire Extinguisher", domain.fireExtinguisher)
    items.addCheckItem(inspectionId, category, "Emergency Stop Switch", domain.emergencyStopSwitch)

    // Handle nested MachineRoomless section
    domain.machineRoomless?.let {
        val subCategory = "$category - Machine Roomless"
        items.addCheckItem(inspectionId, subCategory, "Panel Placement", it.panelPlacement)
        items.addCheckItem(inspectionId, subCategory, "Lighting Work Area", it.lightingWorkArea)
        items.addCheckItem(inspectionId, subCategory, "Lighting Between Work Area", it.lightingBetweenWorkArea)
        items.addCheckItem(inspectionId, subCategory, "Manual Brake Release", it.manualBrakeRelease)
        items.addCheckItem(inspectionId, subCategory, "Fire Extinguisher Placement", it.fireExtinguisherPlacement)
    }
    return items
}

private fun mapSuspensionRopesAndBelts(domain: SuspensionRopesAndBeltsDomain, inspectionId: Long): List<InspectionCheckItem> {
    val items = mutableListOf<InspectionCheckItem>()
    val category = "Suspension Ropes And Belts"
    items.addCheckItem(inspectionId, category, "Condition", domain.condition)
    items.addCheckItem(inspectionId, category, "Chain Usage", domain.chainUsage)
    items.addCheckItem(inspectionId, category, "Safety Factor", domain.safetyFactor)
    items.addCheckItem(inspectionId, category, "Rope With Counterweight", domain.ropeWithCounterweight)
    items.addCheckItem(inspectionId, category, "Rope Without Counterweight", domain.ropeWithoutCounterweight)
    items.addCheckItem(inspectionId, category, "Belt", domain.belt)
    items.addCheckItem(inspectionId, category, "Slack Rope Device", domain.slackRopeDevice)
    return items
}

private fun mapDrumsAndSheaves(domain: DrumsAndSheavesDomain, inspectionId: Long): List<InspectionCheckItem> {
    val items = mutableListOf<InspectionCheckItem>()
    val category = "Drums And Sheaves"
    items.addCheckItem(inspectionId, category, "Drum Grooves", domain.drumGrooves)
    items.addCheckItem(inspectionId, category, "Passenger Drum Diameter", domain.passengerDrumDiameter)
    items.addCheckItem(inspectionId, category, "Governor Drum Diameter", domain.governorDrumDiameter)
    return items
}

private fun mapHoistwayAndPit(domain: HoistwayAndPitDomain, inspectionId: Long): List<InspectionCheckItem> {
    val items = mutableListOf<InspectionCheckItem>()
    val category = "Hoistway And Pit"
    items.addCheckItem(inspectionId, category, "Construction", domain.construction)
    items.addCheckItem(inspectionId, category, "Walls", domain.walls)
    items.addCheckItem(inspectionId, category, "Inclined Elevator Track Bed", domain.inclinedElevatorTrackBed)
    items.addCheckItem(inspectionId, category, "Cleanliness", domain.cleanliness)
    items.addCheckItem(inspectionId, category, "Lighting", domain.lighting)
    items.addCheckItem(inspectionId, category, "Emergency Door Non Stop", domain.emergencyDoorNonStop)
    items.addCheckItem(inspectionId, category, "Emergency Door Size", domain.emergencyDoorSize)
    items.addCheckItem(inspectionId, category, "Emergency Door Safety Switch", domain.emergencyDoorSafetySwitch)
    items.addCheckItem(inspectionId, category, "Emergency Door Bridge", domain.emergencyDoorBridge)
    items.addCheckItem(inspectionId, category, "Car Top Clearance", domain.carTopClearance)
    items.addCheckItem(inspectionId, category, "Pit Clearance", domain.pitClearance)
    items.addCheckItem(inspectionId, category, "Pit Ladder", domain.pitLadder)
    items.addCheckItem(inspectionId, category, "Pit Below Working Area", domain.pitBelowWorkingArea)
    items.addCheckItem(inspectionId, category, "Pit Access Switch", domain.pitAccessSwitch)
    items.addCheckItem(inspectionId, category, "Pit Screen", domain.pitScreen)
    items.addCheckItem(inspectionId, category, "Hoistway Door Leaf", domain.hoistwayDoorLeaf)
    items.addCheckItem(inspectionId, category, "Hoistway Door Interlock", domain.hoistwayDoorInterlock)
    items.addCheckItem(inspectionId, category, "Floor Leveling", domain.floorLeveling)
    items.addCheckItem(inspectionId, category, "Hoistway Separator Beam", domain.hoistwaySeparatorBeam)
    items.addCheckItem(inspectionId, category, "Inclined Elevator Stairs", domain.inclinedElevatorStairs)
    return items
}

private fun mapCar(domain: CarDomain, inspectionId: Long): List<InspectionCheckItem> {
    val items = mutableListOf<InspectionCheckItem>()
    val category = "Car"
    items.addCheckItem(inspectionId, category, "Frame", domain.frame)
    items.addCheckItem(inspectionId, category, "Body", domain.body)
    items.addCheckItem(inspectionId, category, "Wall Height", domain.wallHeight)
    items.addCheckItem(inspectionId, category, "Floor Area", domain.floorArea)
    items.addCheckItem(inspectionId, category, "Car Area Expansion", domain.carAreaExpansion)
    items.addCheckItem(inspectionId, category, "Car Door", domain.carDoor)
    items.addCheckItem(inspectionId, category, "Car To Beam Clearance", domain.carToBeamClearance)
    items.addCheckItem(inspectionId, category, "Alarm Bell", domain.alarmBell)
    items.addCheckItem(inspectionId, category, "Backup Power ARD", domain.backupPowerARD)
    items.addCheckItem(inspectionId, category, "Intercom", domain.intercom)
    items.addCheckItem(inspectionId, category, "Ventilation", domain.ventilation)
    items.addCheckItem(inspectionId, category, "Emergency Lighting", domain.emergencyLighting)
    items.addCheckItem(inspectionId, category, "Operating Panel", domain.operatingPanel)
    items.addCheckItem(inspectionId, category, "Car Position Indicator", domain.carPositionIndicator)
    items.addCheckItem(inspectionId, category, "Car Roof Strength", domain.carRoofStrength)
    items.addCheckItem(inspectionId, category, "Car Top Emergency Exit", domain.carTopEmergencyExit)
    items.addCheckItem(inspectionId, category, "Car Side Emergency Exit", domain.carSideEmergencyExit)
    items.addCheckItem(inspectionId, category, "Car Top Guard Rail", domain.carTopGuardRail)
    items.addCheckItem(inspectionId, category, "Guard Rail Height 300 to 850", domain.guardRailHeight300to850)
    items.addCheckItem(inspectionId, category, "Guard Rail Height Over 850", domain.guardRailHeightOver850)
    items.addCheckItem(inspectionId, category, "Car Top Lighting", domain.carTopLighting)
    items.addCheckItem(inspectionId, category, "Manual Operation Buttons", domain.manualOperationButtons)
    items.addCheckItem(inspectionId, category, "Car Interior", domain.carInterior)

    // Handle nested CarDoorSpecs
    domain.carDoorSpecs?.let {
        val subCategory = "$category - Car Door Specs"
        items.addCheckItem(inspectionId, subCategory, "Size", it.size)
        items.addCheckItem(inspectionId, subCategory, "Lock And Switch", it.lockAndSwitch)
        items.addCheckItem(inspectionId, subCategory, "Sill Clearance", it.sillClearance)
    }

    // Handle nested CarSignage
    domain.carSignage?.let {
        val subCategory = "$category - Car Signage"
        items.addCheckItem(inspectionId, subCategory, "Manufacturer Name", it.manufacturerName)
        items.addCheckItem(inspectionId, subCategory, "Load Capacity", it.loadCapacity)
        items.addCheckItem(inspectionId, subCategory, "No Smoking Sign", it.noSmokingSign)
        items.addCheckItem(inspectionId, subCategory, "Overload Indicator", it.overloadIndicator)
        items.addCheckItem(inspectionId, subCategory, "Door Open Close Buttons", it.doorOpenCloseButtons)
        items.addCheckItem(inspectionId, subCategory, "Floor Buttons", it.floorButtons)
        items.addCheckItem(inspectionId, subCategory, "Alarm Button", it.alarmButton)
        items.addCheckItem(inspectionId, subCategory, "Two Way Intercom", it.twoWayIntercom)
    }
    return items
}

private fun mapGovernorAndSafetyBrake(domain: GovernorAndSafetyBrakeDomain, inspectionId: Long): List<InspectionCheckItem> {
    val items = mutableListOf<InspectionCheckItem>()
    val category = "Governor And Safety Brake"
    items.addCheckItem(inspectionId, category, "Governor Rope Clamp", domain.governorRopeClamp)
    items.addCheckItem(inspectionId, category, "Governor Switch", domain.governorSwitch)
    items.addCheckItem(inspectionId, category, "Safety Brake Speed", domain.safetyBrakeSpeed)
    items.addCheckItem(inspectionId, category, "Safety Brake Type", domain.safetyBrakeType)
    items.addCheckItem(inspectionId, category, "Safety Brake Mechanism", domain.safetyBrakeMechanism)
    items.addCheckItem(inspectionId, category, "Progressive Safety Brake", domain.progressiveSafetyBrake)
    items.addCheckItem(inspectionId, category, "Instantaneous Safety Brake", domain.instantaneousSafetyBrake)
    items.addCheckItem(inspectionId, category, "Safety Brake Operation", domain.safetyBrakeOperation)
    items.addCheckItem(inspectionId, category, "Electrical Cutout Switch", domain.electricalCutoutSwitch)
    items.addCheckItem(inspectionId, category, "Limit Switch", domain.limitSwitch)
    items.addCheckItem(inspectionId, category, "Overload Device", domain.overloadDevice)
    return items
}

private fun mapCounterweightGuideRailsAndBuffers(domain: CounterweightGuideRailsAndBuffersDomain, inspectionId: Long): List<InspectionCheckItem> {
    val items = mutableListOf<InspectionCheckItem>()
    val category = "Counterweight, Guide Rails And Buffers"
    items.addCheckItem(inspectionId, category, "Counterweight Material", domain.counterweightMaterial)
    items.addCheckItem(inspectionId, category, "Counterweight Guard Screen", domain.counterweightGuardScreen)
    items.addCheckItem(inspectionId, category, "Guide Rail Construction", domain.guideRailConstruction)
    items.addCheckItem(inspectionId, category, "Buffer Type", domain.bufferType)
    items.addCheckItem(inspectionId, category, "Buffer Function", domain.bufferFunction)
    items.addCheckItem(inspectionId, category, "Buffer Safety Switch", domain.bufferSafetySwitch)
    return items
}

private fun mapElectricalInstallation(domain: ElectricalInstallationDomain, inspectionId: Long): List<InspectionCheckItem> {
    val items = mutableListOf<InspectionCheckItem>()
    val category = "Electrical Installation"
    items.addCheckItem(inspectionId, category, "Installation Standard", domain.installationStandard)
    items.addCheckItem(inspectionId, category, "Electrical Panel", domain.electricalPanel)
    items.addCheckItem(inspectionId, category, "Backup Power ARD", domain.backupPowerARD)
    items.addCheckItem(inspectionId, category, "Grounding Cable", domain.groundingCable)
    items.addCheckItem(inspectionId, category, "Fire Alarm Connection", domain.fireAlarmConnection)

    // Handle nested FireServiceElevator
    domain.fireServiceElevator?.let {
        val subCategory = "$category - Fire Service Elevator"
        items.addCheckItem(inspectionId, subCategory, "Backup Power", it.backupPower)
        items.addCheckItem(inspectionId, subCategory, "Special Operation", it.specialOperation)
        items.addCheckItem(inspectionId, subCategory, "Fire Switch", it.fireSwitch)
        items.addCheckItem(inspectionId, subCategory, "Label", it.label)
        items.addCheckItem(inspectionId, subCategory, "Electrical Fire Resistance", it.electricalFireResistance)
        items.addCheckItem(inspectionId, subCategory, "Hoistway Wall Fire Resistance", it.hoistwayWallFireResistance)
        items.addCheckItem(inspectionId, subCategory, "Car Size", it.carSize)
        items.addCheckItem(inspectionId, subCategory, "Door Size", it.doorSize)
        items.addCheckItem(inspectionId, subCategory, "Travel Time", it.travelTime)
        items.addCheckItem(inspectionId, subCategory, "Evacuation Floor", it.evacuationFloor)
    }

    // Handle nested AccessibilityElevator
    domain.accessibilityElevator?.let {
        val subCategory = "$category - Accessibility Elevator"
        items.addCheckItem(inspectionId, subCategory, "Operating Panel", it.operatingPanel)
        items.addCheckItem(inspectionId, subCategory, "Panel Height", it.panelHeight)
        items.addCheckItem(inspectionId, subCategory, "Door Open Time", it.doorOpenTime)
        items.addCheckItem(inspectionId, subCategory, "Door Width", it.doorWidth)
        items.addCheckItem(inspectionId, subCategory, "Audio Information", it.audioInformation)
        items.addCheckItem(inspectionId, subCategory, "Label", it.label)
    }

    // Handle nested SeismicSensor
    domain.seismicSensor?.let {
        val subCategory = "$category - Seismic Sensor"
        items.addCheckItem(inspectionId, subCategory, "Availability", it.availability)
        items.addCheckItem(inspectionId, subCategory, "Function", it.function)
    }
    return items
}