package com.nakersolutionid.nakersolutionid.data.local.mapper

import com.nakersolutionid.nakersolutionid.data.local.entity.FindingType
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionCheckItem
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionEntity
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionFinding
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionTestResult
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails
import com.nakersolutionid.nakersolutionid.data.local.entity.Manufacturer
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
    extraId: String,
    createdAt: String
): InspectionWithDetails {
    // 1. Map the main InspectionEntity from the report's general data.
    val inspectionEntity = InspectionEntity(
        id = inspectionId,
        extraId = extraId,
        documentType = documentType,
        inspectionType = nameOfInspectionType,
        subInspectionType = subNameOfInspectionType,
        equipmentType = this.equipmentType ?: "Tidak Diketahui",
        examinationType = this.examinationType ?: "Tidak Diketahui",
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
            year = this.generalData?.countryAndYear
        ),
        createdAt = createdAt,
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

// --- PUBLIC MAPPER ---

/**
 * Maps an [InspectionWithDetails] object from the data layer (Room) back to a [Report] object for the domain layer.
 * This is the main entry point for the reverse mapping process.
 *
 * @return The mapped [Report] object, ready for use in the UI or business logic.
 */
fun InspectionWithDetails.toDomain(): Report {
    // 1. Map the flat list of check items into the structured domain models.
    val technicalDocs = buildTechnicalDocumentInspection(this.checkItems)
    val inspectionAndTesting = buildInspectionAndTesting(this.checkItems)

    // 2. Map the core inspection data into the GeneralData domain model.
    val generalData = GeneralDataDomain(
        ownerName = this.inspectionEntity.ownerName,
        ownerAddress = this.inspectionEntity.ownerAddress,
        nameUsageLocation = this.inspectionEntity.usageLocation,
        addressUsageLocation = this.inspectionEntity.addressUsageLocation,
        manufacturerOrInstaller = this.inspectionEntity.manufacturer?.name,
        elevatorType = this.inspectionEntity.driveType,
        brandOrType = this.inspectionEntity.manufacturer?.brandOrType,
        countryAndYear = this.inspectionEntity.manufacturer?.year,
        serialNumber = this.inspectionEntity.serialNumber,
        capacity = this.inspectionEntity.capacity,
        speed = this.inspectionEntity.speed,
        floorsServed = this.inspectionEntity.floorServed,
        permitNumber = this.inspectionEntity.permitNumber,
        inspectionDate = this.inspectionEntity.reportDate
    )

    // 3. Construct the final Report object.
    return Report(
        id = this.inspectionEntity.id,
        documentType = this.inspectionEntity.documentType,
        nameOfInspectionType = this.inspectionEntity.inspectionType,
        subNameOfInspectionType = this.inspectionEntity.subInspectionType,
        examinationType = this.inspectionEntity.examinationType,
        equipmentType = this.inspectionEntity.equipmentType,
        generalData = generalData,
        technicalDocumentInspection = technicalDocs,
        inspectionAndTesting = inspectionAndTesting,
        conclusion = this.inspectionEntity.status,
        createdAt = this.inspectionEntity.createdAt
    )
}


// --- PRIVATE HELPER FUNCTIONS ---

/**
 * A helper extension function to find a specific check item from the list by its category and name,
 * and convert it into a [ResultStatusDomain] object.
 *
 * @param category The category to search for (e.g., "Car", "Car - Car Signage").
 * @param itemName The name of the item to find (e.g., "Frame", "Manufacturer Name").
 * @return A [ResultStatusDomain] object if the item is found, otherwise null.
 */
private fun List<InspectionCheckItem>.findItem(category: String, itemName: String): ResultStatusDomain? {
    return this.firstOrNull { it.category == category && it.itemName == itemName }
        ?.let { ResultStatusDomain(result = it.result, status = it.status) }
}

// --- BUILDER FUNCTIONS FOR EACH DOMAIN SECTION ---

private fun buildTechnicalDocumentInspection(items: List<InspectionCheckItem>): TechnicalDocumentInspectionDomain {
    val category = "Technical Document Inspection"
    return TechnicalDocumentInspectionDomain(
        designDrawing = items.findItem(category, "Design Drawing")?.result,
        technicalCalculation = items.findItem(category, "Technical Calculation")?.result,
        materialCertificate = items.findItem(category, "Material Certificate")?.result,
        controlPanelDiagram = items.findItem(category, "Control Panel Diagram")?.result,
        asBuiltDrawing = items.findItem(category, "As Built Drawing")?.result,
        componentCertificates = items.findItem(category, "Component Certificates")?.result,
        safeWorkProcedure = items.findItem(category, "Safe Work Procedure")?.result
    )
}

private fun buildInspectionAndTesting(items: List<InspectionCheckItem>): InspectionAndTestingDomain {
    return InspectionAndTestingDomain(
        machineRoomAndMachinery = buildMachineRoomAndMachinery(items),
        suspensionRopesAndBelts = buildSuspensionRopesAndBelts(items),
        drumsAndSheaves = buildDrumsAndSheaves(items),
        hoistwayAndPit = buildHoistwayAndPit(items),
        car = buildCar(items),
        governorAndSafetyBrake = buildGovernorAndSafetyBrake(items),
        counterweightGuideRailsAndBuffers = buildCounterweightGuideRailsAndBuffers(items),
        electricalInstallation = buildElectricalInstallation(items)
    )
}

private fun buildMachineRoomAndMachinery(items: List<InspectionCheckItem>): MachineRoomAndMachineryDomain {
    val category = "Machine Room And Machinery"
    val subCategory = "$category - Machine Roomless"
    return MachineRoomAndMachineryDomain(
        machineMounting = items.findItem(category, "Machine Mounting"),
        mechanicalBrake = items.findItem(category, "Mechanical Brake"),
        electricalBrake = items.findItem(category, "Electrical Brake"),
        machineRoomConstruction = items.findItem(category, "Machine Room Construction"),
        machineRoomClearance = items.findItem(category, "Machine Room Clearance"),
        machineRoomImplementation = items.findItem(category, "Machine Room Implementation"),
        ventilation = items.findItem(category, "Ventilation"),
        machineRoomDoor = items.findItem(category, "Machine Room Door"),
        mainPowerPanelPosition = items.findItem(category, "Main Power Panel Position"),
        rotatingPartsGuard = items.findItem(category, "Rotating Parts Guard"),
        ropeHoleGuard = items.findItem(category, "Rope Hole Guard"),
        machineRoomAccessLadder = items.findItem(category, "Machine Room Access Ladder"),
        floorLevelDifference = items.findItem(category, "Floor Level Difference"),
        fireExtinguisher = items.findItem(category, "Fire Extinguisher"),
        emergencyStopSwitch = items.findItem(category, "Emergency Stop Switch"),
        machineRoomless = MachineRoomlessDomain(
            panelPlacement = items.findItem(subCategory, "Panel Placement"),
            lightingWorkArea = items.findItem(subCategory, "Lighting Work Area"),
            lightingBetweenWorkArea = items.findItem(subCategory, "Lighting Between Work Area"),
            manualBrakeRelease = items.findItem(subCategory, "Manual Brake Release"),
            fireExtinguisherPlacement = items.findItem(subCategory, "Fire Extinguisher Placement")
        )
    )
}

private fun buildSuspensionRopesAndBelts(items: List<InspectionCheckItem>): SuspensionRopesAndBeltsDomain {
    val category = "Suspension Ropes And Belts"
    return SuspensionRopesAndBeltsDomain(
        condition = items.findItem(category, "Condition"),
        chainUsage = items.findItem(category, "Chain Usage"),
        safetyFactor = items.findItem(category, "Safety Factor"),
        ropeWithCounterweight = items.findItem(category, "Rope With Counterweight"),
        ropeWithoutCounterweight = items.findItem(category, "Rope Without Counterweight"),
        belt = items.findItem(category, "Belt"),
        slackRopeDevice = items.findItem(category, "Slack Rope Device")
    )
}

private fun buildDrumsAndSheaves(items: List<InspectionCheckItem>): DrumsAndSheavesDomain {
    val category = "Drums And Sheaves"
    return DrumsAndSheavesDomain(
        drumGrooves = items.findItem(category, "Drum Grooves"),
        passengerDrumDiameter = items.findItem(category, "Passenger Drum Diameter"),
        governorDrumDiameter = items.findItem(category, "Governor Drum Diameter")
    )
}

private fun buildHoistwayAndPit(items: List<InspectionCheckItem>): HoistwayAndPitDomain {
    val category = "Hoistway And Pit"
    return HoistwayAndPitDomain(
        construction = items.findItem(category, "Construction"),
        walls = items.findItem(category, "Walls"),
        inclinedElevatorTrackBed = items.findItem(category, "Inclined Elevator Track Bed"),
        cleanliness = items.findItem(category, "Cleanliness"),
        lighting = items.findItem(category, "Lighting"),
        emergencyDoorNonStop = items.findItem(category, "Emergency Door Non Stop"),
        emergencyDoorSize = items.findItem(category, "Emergency Door Size"),
        emergencyDoorSafetySwitch = items.findItem(category, "Emergency Door Safety Switch"),
        emergencyDoorBridge = items.findItem(category, "Emergency Door Bridge"),
        carTopClearance = items.findItem(category, "Car Top Clearance"),
        pitClearance = items.findItem(category, "Pit Clearance"),
        pitLadder = items.findItem(category, "Pit Ladder"),
        pitBelowWorkingArea = items.findItem(category, "Pit Below Working Area"),
        pitAccessSwitch = items.findItem(category, "Pit Access Switch"),
        pitScreen = items.findItem(category, "Pit Screen"),
        hoistwayDoorLeaf = items.findItem(category, "Hoistway Door Leaf"),
        hoistwayDoorInterlock = items.findItem(category, "Hoistway Door Interlock"),
        floorLeveling = items.findItem(category, "Floor Leveling"),
        hoistwaySeparatorBeam = items.findItem(category, "Hoistway Separator Beam"),
        inclinedElevatorStairs = items.findItem(category, "Inclined Elevator Stairs")
    )
}

private fun buildCar(items: List<InspectionCheckItem>): CarDomain {
    val category = "Car"
    val doorSpecsSubCategory = "$category - Car Door Specs"
    val signageSubCategory = "$category - Car Signage"
    return CarDomain(
        frame = items.findItem(category, "Frame"),
        body = items.findItem(category, "Body"),
        wallHeight = items.findItem(category, "Wall Height"),
        floorArea = items.findItem(category, "Floor Area"),
        carAreaExpansion = items.findItem(category, "Car Area Expansion"),
        carDoor = items.findItem(category, "Car Door"),
        carToBeamClearance = items.findItem(category, "Car To Beam Clearance"),
        alarmBell = items.findItem(category, "Alarm Bell"),
        backupPowerARD = items.findItem(category, "Backup Power ARD"),
        intercom = items.findItem(category, "Intercom"),
        ventilation = items.findItem(category, "Ventilation"),
        emergencyLighting = items.findItem(category, "Emergency Lighting"),
        operatingPanel = items.findItem(category, "Operating Panel"),
        carPositionIndicator = items.findItem(category, "Car Position Indicator"),
        carRoofStrength = items.findItem(category, "Car Roof Strength"),
        carTopEmergencyExit = items.findItem(category, "Car Top Emergency Exit"),
        carSideEmergencyExit = items.findItem(category, "Car Side Emergency Exit"),
        carTopGuardRail = items.findItem(category, "Car Top Guard Rail"),
        guardRailHeight300to850 = items.findItem(category, "Guard Rail Height 300 to 850"),
        guardRailHeightOver850 = items.findItem(category, "Guard Rail Height Over 850"),
        carTopLighting = items.findItem(category, "Car Top Lighting"),
        manualOperationButtons = items.findItem(category, "Manual Operation Buttons"),
        carInterior = items.findItem(category, "Car Interior"),
        carDoorSpecs = CarDoorSpecsDomain(
            size = items.findItem(doorSpecsSubCategory, "Size"),
            lockAndSwitch = items.findItem(doorSpecsSubCategory, "Lock And Switch"),
            sillClearance = items.findItem(doorSpecsSubCategory, "Sill Clearance")
        ),
        carSignage = CarSignageDomain(
            manufacturerName = items.findItem(signageSubCategory, "Manufacturer Name"),
            loadCapacity = items.findItem(signageSubCategory, "Load Capacity"),
            noSmokingSign = items.findItem(signageSubCategory, "No Smoking Sign"),
            overloadIndicator = items.findItem(signageSubCategory, "Overload Indicator"),
            doorOpenCloseButtons = items.findItem(signageSubCategory, "Door Open Close Buttons"),
            floorButtons = items.findItem(signageSubCategory, "Floor Buttons"),
            alarmButton = items.findItem(signageSubCategory, "Alarm Button"),
            twoWayIntercom = items.findItem(signageSubCategory, "Two Way Intercom")
        )
    )
}

private fun buildGovernorAndSafetyBrake(items: List<InspectionCheckItem>): GovernorAndSafetyBrakeDomain {
    val category = "Governor And Safety Brake"
    return GovernorAndSafetyBrakeDomain(
        governorRopeClamp = items.findItem(category, "Governor Rope Clamp"),
        governorSwitch = items.findItem(category, "Governor Switch"),
        safetyBrakeSpeed = items.findItem(category, "Safety Brake Speed"),
        safetyBrakeType = items.findItem(category, "Safety Brake Type"),
        safetyBrakeMechanism = items.findItem(category, "Safety Brake Mechanism"),
        progressiveSafetyBrake = items.findItem(category, "Progressive Safety Brake"),
        instantaneousSafetyBrake = items.findItem(category, "Instantaneous Safety Brake"),
        safetyBrakeOperation = items.findItem(category, "Safety Brake Operation"),
        electricalCutoutSwitch = items.findItem(category, "Electrical Cutout Switch"),
        limitSwitch = items.findItem(category, "Limit Switch"),
        overloadDevice = items.findItem(category, "Overload Device")
    )
}

private fun buildCounterweightGuideRailsAndBuffers(items: List<InspectionCheckItem>): CounterweightGuideRailsAndBuffersDomain {
    val category = "Counterweight, Guide Rails And Buffers"
    return CounterweightGuideRailsAndBuffersDomain(
        counterweightMaterial = items.findItem(category, "Counterweight Material"),
        counterweightGuardScreen = items.findItem(category, "Counterweight Guard Screen"),
        guideRailConstruction = items.findItem(category, "Guide Rail Construction"),
        bufferType = items.findItem(category, "Buffer Type"),
        bufferFunction = items.findItem(category, "Buffer Function"),
        bufferSafetySwitch = items.findItem(category, "Buffer Safety Switch")
    )
}

private fun buildElectricalInstallation(items: List<InspectionCheckItem>): ElectricalInstallationDomain {
    val category = "Electrical Installation"
    val fireServiceSubCategory = "$category - Fire Service Elevator"
    val accessibilitySubCategory = "$category - Accessibility Elevator"
    val seismicSubCategory = "$category - Seismic Sensor"
    return ElectricalInstallationDomain(
        installationStandard = items.findItem(category, "Installation Standard"),
        electricalPanel = items.findItem(category, "Electrical Panel"),
        backupPowerARD = items.findItem(category, "Backup Power ARD"),
        groundingCable = items.findItem(category, "Grounding Cable"),
        fireAlarmConnection = items.findItem(category, "Fire Alarm Connection"),
        fireServiceElevator = FireServiceElevatorDomain(
            backupPower = items.findItem(fireServiceSubCategory, "Backup Power"),
            specialOperation = items.findItem(fireServiceSubCategory, "Special Operation"),
            fireSwitch = items.findItem(fireServiceSubCategory, "Fire Switch"),
            label = items.findItem(fireServiceSubCategory, "Label"),
            electricalFireResistance = items.findItem(fireServiceSubCategory, "Electrical Fire Resistance"),
            hoistwayWallFireResistance = items.findItem(fireServiceSubCategory, "Hoistway Wall Fire Resistance"),
            carSize = items.findItem(fireServiceSubCategory, "Car Size"),
            doorSize = items.findItem(fireServiceSubCategory, "Door Size"),
            travelTime = items.findItem(fireServiceSubCategory, "Travel Time"),
            evacuationFloor = items.findItem(fireServiceSubCategory, "Evacuation Floor")
        ),
        accessibilityElevator = AccessibilityElevatorDomain(
            operatingPanel = items.findItem(accessibilitySubCategory, "Operating Panel"),
            panelHeight = items.findItem(accessibilitySubCategory, "Panel Height"),
            doorOpenTime = items.findItem(accessibilitySubCategory, "Door Open Time"),
            doorWidth = items.findItem(accessibilitySubCategory, "Door Width"),
            audioInformation = items.findItem(accessibilitySubCategory, "Audio Information"),
            label = items.findItem(accessibilitySubCategory, "Label")
        ),
        seismicSensor = SeismicSensorDomain(
            availability = items.findItem(seismicSubCategory, "Availability"),
            function = items.findItem(seismicSubCategory, "Function")
        )
    )
}

/**
 * Maps an [InspectionEntity] from the data layer (Room) to a [History] object for the domain layer.
 * This is used for populating list views or summaries where full details are not needed.
 *
 * @return The mapped [History] object.
 */
fun InspectionEntity.toHistory(): History {
    return History(
        id = this.id,
        extraId = this.extraId,
        documentType = this.documentType,
        inspectionType = this.inspectionType,
        subInspectionType = this.subInspectionType,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.ownerName,
        createdAt = this.createdAt,
        reportDate = this.reportDate
    )
}