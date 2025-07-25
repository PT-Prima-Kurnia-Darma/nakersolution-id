package com.nakersolutionid.nakersolutionid.features.report.paa.gondola

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import kotlinx.collections.immutable.toImmutableList

/**
 * Maps the GondolaUiState to an InspectionWithDetailsDomain model without using reflection.
 * All UI state data is flattened into InspectionDomain, InspectionFindingDomain, or InspectionCheckItemDomain.
 */
fun GondolaUiState.toInspectionWithDetailsDomain(
    currentTime: String,
    reportId: Long? = null
): InspectionWithDetailsDomain {
    val report = this.gondolaInspectionReport
    val generalData = report.generalData
    val technicalData = report.technicalData
    val visualInspection = report.visualInspection
    val nde = report.nonDestructiveTesting
    val testing = report.testing
    val conclusion = report.conclusion

    // Hardcoded values as requested
    val inspectionId = reportId ?: 0L
    val documentType = DocumentType.LAPORAN
    val inspectionType = InspectionType.PAA
    val subInspectionType = SubInspectionType.Gondola

    // 1. Map main and general data to InspectionDomain
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = report.extraId,
        moreExtraId = report.moreExtraId,
        documentType = documentType,
        inspectionType = inspectionType,
        subInspectionType = subInspectionType,
        equipmentType = report.equipmentType,
        examinationType = report.examinationType,
        ownerName = generalData.owner,
        ownerAddress = generalData.ownerAddress,
        usageLocation = generalData.unitLocation,
        addressUsageLocation = generalData.user,
        driveType = generalData.driveType,
        serialNumber = generalData.serialNumberUnitNumber,
        permitNumber = generalData.usagePermitNumber,
        capacity = generalData.capacity,
        manufacturer = ManufacturerDomain(
            name = generalData.manufacturer,
            brandOrType = generalData.brandType,
            year = generalData.yearOfManufacture
        ),
        createdAt = currentTime,
        // Fields not in UI State are set to empty string
        speed = "",
        floorServed = "",
        reportDate = generalData.inspectionDate,
        nextInspectionDate = "",
        inspectorName = "",
        status = "",
        isSynced = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    // Helper to add a check item for a simple string value
    fun addCheckItem(category: String, itemName: String, value: String?) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = false, // Not applicable for simple data points
                result = value ?: ""
            )
        )
    }

    // Helper to add a check item for an InspectionResult
    fun addResultCheckItem(category: String, itemName: String, value: GondolaInspectionResult?) {
        val currentVal = value ?: GondolaInspectionResult()
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = currentVal.status ?: false,
                result = currentVal.result ?: ""
            )
        )
    }

    // 2. Map remaining General Data to CheckItems
    addCheckItem("general_data", "personInCharge", generalData.personInCharge)
    addCheckItem("general_data", "operatorName", generalData.operatorName)
    addCheckItem("general_data", "standardUsed", generalData.standardUsed)
    addCheckItem("general_data", "usedFor", generalData.usedFor)
    addCheckItem("general_data", "operatorCertificate", generalData.operatorCertificate)

    // 3. Map all Technical Data to CheckItems
    val techCategory = "technical_data"
    technicalData.gondolaSpecifications.let { spec ->
        addCheckItem(techCategory, "spec_supportPoleHeight", spec.supportPoleHeight)
        addCheckItem(techCategory, "spec_beam_front", spec.beam.frontBeamLength)
        addCheckItem(techCategory, "spec_beam_middle", spec.beam.middleBeamLength)
        addCheckItem(techCategory, "spec_beam_rear", spec.beam.rearBeamLength)
        addCheckItem(techCategory, "spec_balanceWeightToBeamDistance", spec.balanceWeightToBeamDistance)
        addCheckItem(techCategory, "spec_capacity", spec.capacity)
        addCheckItem(techCategory, "spec_speed", spec.speed)
        addCheckItem(techCategory, "spec_platform_length", spec.platformSize.length)
        addCheckItem(techCategory, "spec_platform_width", spec.platformSize.width)
        addCheckItem(techCategory, "spec_platform_height", spec.platformSize.height)
        addCheckItem(techCategory, "spec_wireRope", spec.wireRope)
    }
    technicalData.hoist.let { hoist ->
        addCheckItem(techCategory, "hoist_model", hoist.model)
        addCheckItem(techCategory, "hoist_liftingPower", hoist.liftingPower)
        addCheckItem(techCategory, "hoist_motor_type", hoist.electricMotor.type)
        addCheckItem(techCategory, "hoist_motor_power", hoist.electricMotor.power)
        addCheckItem(techCategory, "hoist_motor_voltage", hoist.electricMotor.voltage)
    }
    addCheckItem(techCategory, "safetyLockType", technicalData.safetyLockType)
    technicalData.brake.let { brake ->
        addCheckItem(techCategory, "brake_type", brake.type)
        addCheckItem(techCategory, "brake_model", brake.model)
        addCheckItem(techCategory, "brake_capacity", brake.capacity)
    }
    technicalData.mechanicalSuspension.let { suspension ->
        addCheckItem(techCategory, "suspension_supportPoleHeight", suspension.supportPoleHeight)
        addCheckItem(techCategory, "suspension_frontBeamLength", suspension.frontBeamLength)
        addCheckItem(techCategory, "suspension_material", suspension.material)
    }
    technicalData.engineWeight.let { weight ->
        addCheckItem(techCategory, "weight_platform", weight.totalPlatformWeightIncludingHoistSafetyLockControlPanel)
        addCheckItem(techCategory, "weight_suspension", weight.mechanicalSuspensionWeight)
        addCheckItem(techCategory, "weight_balance", weight.balanceWeight)
        addCheckItem(techCategory, "weight_total", weight.totalEngineWeightExcludingWireRopeAndCable)
    }

    // 4. Map all Visual Inspection to CheckItems
    val visualCategory = "visual_inspection"
    addResultCheckItem(visualCategory, "suspensionStructureFrontBeam", visualInspection.suspensionStructureFrontBeam)
    addResultCheckItem(visualCategory, "suspensionStructureMiddleBeam", visualInspection.suspensionStructureMiddleBeam)
    addResultCheckItem(visualCategory, "suspensionStructureRearBeam", visualInspection.suspensionStructureRearBeam)
    addResultCheckItem(visualCategory, "suspensionStructureFrontBeamSupportPole", visualInspection.suspensionStructureFrontBeamSupportPole)
    addResultCheckItem(visualCategory, "suspensionStructureLowerFrontBeamSupportPole", visualInspection.suspensionStructureLowerFrontBeamSupportPole)
    addResultCheckItem(visualCategory, "suspensionStructureReinforcementClamp", visualInspection.suspensionStructureReinforcementClamp)
    addResultCheckItem(visualCategory, "suspensionStructureCouplingSleeve", visualInspection.suspensionStructureCouplingSleeve)
    addResultCheckItem(visualCategory, "suspensionStructureTurnbuckle", visualInspection.suspensionStructureTurnbuckle)
    addResultCheckItem(visualCategory, "suspensionStructureReinforcementRope", visualInspection.suspensionStructureReinforcementRope)
    addResultCheckItem(visualCategory, "suspensionStructureRearSupportPole", visualInspection.suspensionStructureRearSupportPole)
    addResultCheckItem(visualCategory, "suspensionStructureBalanceWeight", visualInspection.suspensionStructureBalanceWeight)
    addResultCheckItem(visualCategory, "suspensionStructureFrontSupportPoleBase", visualInspection.suspensionStructureFrontSupportPoleBase)
    addResultCheckItem(visualCategory, "suspensionStructureRearSupportPoleBase", visualInspection.suspensionStructureRearSupportPoleBase)
    addResultCheckItem(visualCategory, "suspensionStructureJackBaseJoint", visualInspection.suspensionStructureJackBaseJoint)
    addResultCheckItem(visualCategory, "suspensionStructureConnectionBolts", visualInspection.suspensionStructureConnectionBolts)
    addResultCheckItem(visualCategory, "steelWireRopeMainRope", visualInspection.steelWireRopeMainRope)
    addResultCheckItem(visualCategory, "steelWireRopeSafetyRope", visualInspection.steelWireRopeSafetyRope)
    addResultCheckItem(visualCategory, "steelWireRopeSlingBinder", visualInspection.steelWireRopeSlingBinder)
    addResultCheckItem(visualCategory, "electricalSystemHoistMotor", visualInspection.electricalSystemHoistMotor)
    addResultCheckItem(visualCategory, "electricalSystemBrakeRelease", visualInspection.electricalSystemBrakeRelease)
    addResultCheckItem(visualCategory, "electricalSystemManualRelease", visualInspection.electricalSystemManualRelease)
    addResultCheckItem(visualCategory, "electricalSystemPowerControl", visualInspection.electricalSystemPowerControl)
    addResultCheckItem(visualCategory, "electricalSystemPowerCable", visualInspection.electricalSystemPowerCable)
    addResultCheckItem(visualCategory, "electricalSystemHandleSwitch", visualInspection.electricalSystemHandleSwitch)
    addResultCheckItem(visualCategory, "electricalSystemUpperLimitSwitch", visualInspection.electricalSystemUpperLimitSwitch)
    addResultCheckItem(visualCategory, "electricalSystemLimitStopper", visualInspection.electricalSystemLimitStopper)
    addResultCheckItem(visualCategory, "electricalSystemSocketFitting", visualInspection.electricalSystemSocketFitting)
    addResultCheckItem(visualCategory, "electricalSystemGrounding", visualInspection.electricalSystemGrounding)
    addResultCheckItem(visualCategory, "electricalSystemBreakerFuse", visualInspection.electricalSystemBreakerFuse)
    addResultCheckItem(visualCategory, "electricalSystemEmergencyStop", visualInspection.electricalSystemEmergencyStop)
    addResultCheckItem(visualCategory, "platformHoistMountingFrame", visualInspection.platformHoistMountingFrame)
    addResultCheckItem(visualCategory, "platformFrame", visualInspection.platformFrame)
    addResultCheckItem(visualCategory, "platformBottomPlate", visualInspection.platformBottomPlate)
    addResultCheckItem(visualCategory, "platformPinsAndBolts", visualInspection.platformPinsAndBolts)
    addResultCheckItem(visualCategory, "platformBracket", visualInspection.platformBracket)
    addResultCheckItem(visualCategory, "platformToeBoard", visualInspection.platformToeBoard)
    addResultCheckItem(visualCategory, "platformRollerAndGuidePulley", visualInspection.platformRollerAndGuidePulley)
    addResultCheckItem(visualCategory, "platformNameplate", visualInspection.platformNameplate)
    addResultCheckItem(visualCategory, "safetyDevicesSafetyLock", visualInspection.safetyDevicesSafetyLock)
    addResultCheckItem(visualCategory, "safetyDevicesRubberBumper", visualInspection.safetyDevicesRubberBumper)
    addResultCheckItem(visualCategory, "safetyDevicesSafetyLifeline", visualInspection.safetyDevicesSafetyLifeline)
    addResultCheckItem(visualCategory, "safetyDevicesLoadLimitSwitch", visualInspection.safetyDevicesLoadLimitSwitch)
    addResultCheckItem(visualCategory, "safetyDevicesLimitBlock", visualInspection.safetyDevicesLimitBlock)
    addResultCheckItem(visualCategory, "safetyDevicesUpperLimitSwitch", visualInspection.safetyDevicesUpperLimitSwitch)
    addResultCheckItem(visualCategory, "safetyDevicesBodyHarness", visualInspection.safetyDevicesBodyHarness)
    addResultCheckItem(visualCategory, "safetyDevicesSafetyHarnessAnchorage", visualInspection.safetyDevicesSafetyHarnessAnchorage)
    addResultCheckItem(visualCategory, "safetyDevicesHandyTalkie", visualInspection.safetyDevicesHandyTalkie)
    addResultCheckItem(visualCategory, "safetyDevicesSafetyHelmet", visualInspection.safetyDevicesSafetyHelmet)
    addResultCheckItem(visualCategory, "safetyDevicesHandrail", visualInspection.safetyDevicesHandrail)
    addResultCheckItem(visualCategory, "safetyDevicesOtherPpe", visualInspection.safetyDevicesOtherPpe)
    addResultCheckItem(visualCategory, "safetyDevicesCoupForGlass", visualInspection.safetyDevicesCoupForGlass)

    // 5. Map NDE to CheckItems
    addCheckItem("nde_steelWireRope", "ndtType", nde.steelWireRope.ndtType)
    nde.steelWireRope.items.forEachIndexed { index, item ->
        val cat = "nde_steelWireRope_item_$index"
        addCheckItem(cat, "usage", item.usage)
        addCheckItem(cat, "specDiameter", item.specDiameter)
        addCheckItem(cat, "actualDiameter", item.actualDiameter)
        addCheckItem(cat, "construction", item.construction)
        addCheckItem(cat, "type", item.type)
        addCheckItem(cat, "length", item.length)
        addCheckItem(cat, "age", item.age)
        addCheckItem(cat, "defect", item.defect)
        addCheckItem(cat, "description", item.description)
    }

    addCheckItem("nde_suspensionStructure", "ndtType", nde.suspensionStructure.ndtType)
    nde.suspensionStructure.items.forEachIndexed { index, item ->
        val cat = "nde_suspensionStructure_item_$index"
        addCheckItem(cat, "inspectedPart", item.inspectedPart)
        addCheckItem(cat, "location", item.location)
        addCheckItem(cat, "defect", item.defect)
        addCheckItem(cat, "description", item.description)
    }

    addCheckItem("nde_gondolaCage", "ndtType", nde.gondolaCage.ndtType)
    nde.gondolaCage.items.forEachIndexed { index, item ->
        val cat = "nde_gondolaCage_item_$index"
        addCheckItem(cat, "inspectedPart", item.inspectedPart)
        addCheckItem(cat, "location", item.location)
        addCheckItem(cat, "defect", item.defect)
        addCheckItem(cat, "description", item.description)
    }

    addCheckItem("nde_clamps", "ndtType", nde.clamps.ndtType)
    nde.clamps.items.forEachIndexed { index, item ->
        val cat = "nde_clamps_item_$index"
        addCheckItem(cat, "inspectedPart", item.inspectedPart)
        addCheckItem(cat, "location", item.location)
        addCheckItem(cat, "defect", item.defect)
        addCheckItem(cat, "description", item.description)
    }

    // 6. Map Testing to CheckItems
    testing.dynamicLoad.forEachIndexed { index, item ->
        val cat = "testing_dynamicLoad_item_$index"
        addCheckItem(cat, "loadPercentage", item.loadPercentage)
        addCheckItem(cat, "load", item.load)
        addCheckItem(cat, "result", item.result)
        addCheckItem(cat, "description", item.description)
    }
    testing.staticLoad.forEachIndexed { index, item ->
        val cat = "testing_staticLoad_item_$index"
        addCheckItem(cat, "loadPercentage", item.loadPercentage)
        addCheckItem(cat, "load", item.load)
        addCheckItem(cat, "result", item.result)
        addCheckItem(cat, "description", item.description)
    }

    // 7. Map Conclusion to Findings
    val findings = mutableListOf<InspectionFindingDomain>()
    conclusion.summary.forEach {
        findings.add(
            InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING)
        )
    }
    conclusion.recommendations.forEach {
        findings.add(
            InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION)
        )
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList() // All test data is in checkItems
    )
}

/**
 * Maps an InspectionWithDetailsDomain model to the GondolaUiState without using reflection.
 * This function reconstructs the nested UI state from the flattened domain model.
 */
fun InspectionWithDetailsDomain.toGondolaUiState(): GondolaUiState {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    // Helper to get a simple string value from check items
    fun getCheckItemValue(category: String, itemName: String): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    fun getCheckItemValueForIndexed(prefix: String, index: Int, itemName: String): String {
        val category = "${prefix}_${index}"
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    // Helper to get an InspectionResult from check items
    fun getResultCheckItem(category: String, itemName: String): GondolaInspectionResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return GondolaInspectionResult(
            status = item?.status ?: false,
            result = item?.result ?: ""
        )
    }

    // 1. Reconstruct General Data
    val generalData = GondolaGeneralData(
        owner = inspection.ownerName ?: "",
        ownerAddress = inspection.ownerAddress ?: "",
        user = inspection.addressUsageLocation ?: "",
        unitLocation = inspection.usageLocation ?: "",
        driveType = inspection.driveType ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        brandType = inspection.manufacturer?.brandOrType ?: "",
        yearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = inspection.serialNumber ?: "",
        capacity = inspection.capacity ?: "",
        usagePermitNumber = inspection.permitNumber ?: "",
        personInCharge = getCheckItemValue("general_data", "personInCharge"),
        operatorName = getCheckItemValue("general_data", "operatorName"),
        standardUsed = getCheckItemValue("general_data", "standardUsed"),
        usedFor = getCheckItemValue("general_data", "usedFor"),
        operatorCertificate = getCheckItemValue("general_data", "operatorCertificate"),
        inspectionDate = inspection.reportDate ?: ""
    )

    // 2. Reconstruct Technical Data
    val techCategory = "technical_data"
    val technicalData = GondolaTechnicalData(
        gondolaSpecifications = GondolaSpecifications(
            supportPoleHeight = getCheckItemValue(techCategory, "spec_supportPoleHeight"),
            beam = GondolaBeam(
                frontBeamLength = getCheckItemValue(techCategory, "spec_beam_front"),
                middleBeamLength = getCheckItemValue(techCategory, "spec_beam_middle"),
                rearBeamLength = getCheckItemValue(techCategory, "spec_beam_rear")
            ),
            balanceWeightToBeamDistance = getCheckItemValue(techCategory, "spec_balanceWeightToBeamDistance"),
            capacity = getCheckItemValue(techCategory, "spec_capacity"),
            speed = getCheckItemValue(techCategory, "spec_speed"),
            platformSize = GondolaPlatformSize(
                length = getCheckItemValue(techCategory, "spec_platform_length"),
                width = getCheckItemValue(techCategory, "spec_platform_width"),
                height = getCheckItemValue(techCategory, "spec_platform_height")
            ),
            wireRope = getCheckItemValue(techCategory, "spec_wireRope")
        ),
        hoist = GondolaHoist(
            model = getCheckItemValue(techCategory, "hoist_model"),
            liftingPower = getCheckItemValue(techCategory, "hoist_liftingPower"),
            electricMotor = GondolaElectricMotor(
                type = getCheckItemValue(techCategory, "hoist_motor_type"),
                power = getCheckItemValue(techCategory, "hoist_motor_power"),
                voltage = getCheckItemValue(techCategory, "hoist_motor_voltage")
            )
        ),
        safetyLockType = getCheckItemValue(techCategory, "safetyLockType"),
        brake = GondolaBrake(
            type = getCheckItemValue(techCategory, "brake_type"),
            model = getCheckItemValue(techCategory, "brake_model"),
            capacity = getCheckItemValue(techCategory, "brake_capacity")
        ),
        mechanicalSuspension = GondolaMechanicalSuspension(
            supportPoleHeight = getCheckItemValue(techCategory, "suspension_supportPoleHeight"),
            frontBeamLength = getCheckItemValue(techCategory, "suspension_frontBeamLength"),
            material = getCheckItemValue(techCategory, "suspension_material")
        ),
        engineWeight = GondolaEngineWeight(
            totalPlatformWeightIncludingHoistSafetyLockControlPanel = getCheckItemValue(techCategory, "weight_platform"),
            mechanicalSuspensionWeight = getCheckItemValue(techCategory, "weight_suspension"),
            balanceWeight = getCheckItemValue(techCategory, "weight_balance"),
            totalEngineWeightExcludingWireRopeAndCable = getCheckItemValue(techCategory, "weight_total")
        )
    )

    // 3. Reconstruct Visual Inspection
    val visualCategory = "visual_inspection"
    val visualInspection = GondolaVisualInspection(
        suspensionStructureFrontBeam = getResultCheckItem(visualCategory, "suspensionStructureFrontBeam"),
        suspensionStructureMiddleBeam = getResultCheckItem(visualCategory, "suspensionStructureMiddleBeam"),
        suspensionStructureRearBeam = getResultCheckItem(visualCategory, "suspensionStructureRearBeam"),
        suspensionStructureFrontBeamSupportPole = getResultCheckItem(visualCategory, "suspensionStructureFrontBeamSupportPole"),
        suspensionStructureLowerFrontBeamSupportPole = getResultCheckItem(visualCategory, "suspensionStructureLowerFrontBeamSupportPole"),
        suspensionStructureReinforcementClamp = getResultCheckItem(visualCategory, "suspensionStructureReinforcementClamp"),
        suspensionStructureCouplingSleeve = getResultCheckItem(visualCategory, "suspensionStructureCouplingSleeve"),
        suspensionStructureTurnbuckle = getResultCheckItem(visualCategory, "suspensionStructureTurnbuckle"),
        suspensionStructureReinforcementRope = getResultCheckItem(visualCategory, "suspensionStructureReinforcementRope"),
        suspensionStructureRearSupportPole = getResultCheckItem(visualCategory, "suspensionStructureRearSupportPole"),
        suspensionStructureBalanceWeight = getResultCheckItem(visualCategory, "suspensionStructureBalanceWeight"),
        suspensionStructureFrontSupportPoleBase = getResultCheckItem(visualCategory, "suspensionStructureFrontSupportPoleBase"),
        suspensionStructureRearSupportPoleBase = getResultCheckItem(visualCategory, "suspensionStructureRearSupportPoleBase"),
        suspensionStructureJackBaseJoint = getResultCheckItem(visualCategory, "suspensionStructureJackBaseJoint"),
        suspensionStructureConnectionBolts = getResultCheckItem(visualCategory, "suspensionStructureConnectionBolts"),
        steelWireRopeMainRope = getResultCheckItem(visualCategory, "steelWireRopeMainRope"),
        steelWireRopeSafetyRope = getResultCheckItem(visualCategory, "steelWireRopeSafetyRope"),
        steelWireRopeSlingBinder = getResultCheckItem(visualCategory, "steelWireRopeSlingBinder"),
        electricalSystemHoistMotor = getResultCheckItem(visualCategory, "electricalSystemHoistMotor"),
        electricalSystemBrakeRelease = getResultCheckItem(visualCategory, "electricalSystemBrakeRelease"),
        electricalSystemManualRelease = getResultCheckItem(visualCategory, "electricalSystemManualRelease"),
        electricalSystemPowerControl = getResultCheckItem(visualCategory, "electricalSystemPowerControl"),
        electricalSystemPowerCable = getResultCheckItem(visualCategory, "electricalSystemPowerCable"),
        electricalSystemHandleSwitch = getResultCheckItem(visualCategory, "electricalSystemHandleSwitch"),
        electricalSystemUpperLimitSwitch = getResultCheckItem(visualCategory, "electricalSystemUpperLimitSwitch"),
        electricalSystemLimitStopper = getResultCheckItem(visualCategory, "electricalSystemLimitStopper"),
        electricalSystemSocketFitting = getResultCheckItem(visualCategory, "electricalSystemSocketFitting"),
        electricalSystemGrounding = getResultCheckItem(visualCategory, "electricalSystemGrounding"),
        electricalSystemBreakerFuse = getResultCheckItem(visualCategory, "electricalSystemBreakerFuse"),
        electricalSystemEmergencyStop = getResultCheckItem(visualCategory, "electricalSystemEmergencyStop"),
        platformHoistMountingFrame = getResultCheckItem(visualCategory, "platformHoistMountingFrame"),
        platformFrame = getResultCheckItem(visualCategory, "platformFrame"),
        platformBottomPlate = getResultCheckItem(visualCategory, "platformBottomPlate"),
        platformPinsAndBolts = getResultCheckItem(visualCategory, "platformPinsAndBolts"),
        platformBracket = getResultCheckItem(visualCategory, "platformBracket"),
        platformToeBoard = getResultCheckItem(visualCategory, "platformToeBoard"),
        platformRollerAndGuidePulley = getResultCheckItem(visualCategory, "platformRollerAndGuidePulley"),
        platformNameplate = getResultCheckItem(visualCategory, "platformNameplate"),
        safetyDevicesSafetyLock = getResultCheckItem(visualCategory, "safetyDevicesSafetyLock"),
        safetyDevicesRubberBumper = getResultCheckItem(visualCategory, "safetyDevicesRubberBumper"),
        safetyDevicesSafetyLifeline = getResultCheckItem(visualCategory, "safetyDevicesSafetyLifeline"),
        safetyDevicesLoadLimitSwitch = getResultCheckItem(visualCategory, "safetyDevicesLoadLimitSwitch"),
        safetyDevicesLimitBlock = getResultCheckItem(visualCategory, "safetyDevicesLimitBlock"),
        safetyDevicesUpperLimitSwitch = getResultCheckItem(visualCategory, "safetyDevicesUpperLimitSwitch"),
        safetyDevicesBodyHarness = getResultCheckItem(visualCategory, "safetyDevicesBodyHarness"),
        safetyDevicesSafetyHarnessAnchorage = getResultCheckItem(visualCategory, "safetyDevicesSafetyHarnessAnchorage"),
        safetyDevicesHandyTalkie = getResultCheckItem(visualCategory, "safetyDevicesHandyTalkie"),
        safetyDevicesSafetyHelmet = getResultCheckItem(visualCategory, "safetyDevicesSafetyHelmet"),
        safetyDevicesHandrail = getResultCheckItem(visualCategory, "safetyDevicesHandrail"),
        safetyDevicesOtherPpe = getResultCheckItem(visualCategory, "safetyDevicesOtherPpe"),
        safetyDevicesCoupForGlass = getResultCheckItem(visualCategory, "safetyDevicesCoupForGlass")
    )

    // 4. Reconstruct NDE
    val ndeWireRopeItems = mutableListOf<GondolaSteelWireRopeItem>()
    val wireRopeItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_steelWireRope_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    wireRopeItemCategories.forEach { index ->
        ndeWireRopeItems.add(GondolaSteelWireRopeItem(
            usage = getCheckItemValueForIndexed("nde_steelWireRope_item", index, "usage"),
            specDiameter = getCheckItemValueForIndexed("nde_steelWireRope_item", index, "specDiameter"),
            actualDiameter = getCheckItemValueForIndexed("nde_steelWireRope_item", index, "actualDiameter"),
            construction = getCheckItemValueForIndexed("nde_steelWireRope_item", index, "construction"),
            type = getCheckItemValueForIndexed("nde_steelWireRope_item", index, "type"),
            length = getCheckItemValueForIndexed("nde_steelWireRope_item", index, "length"),
            age = getCheckItemValueForIndexed("nde_steelWireRope_item", index, "age"),
            defect = getCheckItemValueForIndexed("nde_steelWireRope_item", index, "defect"),
            description = getCheckItemValueForIndexed("nde_steelWireRope_item", index, "description")
        ))
    }

    val ndeSuspensionItems = mutableListOf<GondolaSuspensionStructureItem>()
    val suspensionItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_suspensionStructure_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    suspensionItemCategories.forEach { index ->
        ndeSuspensionItems.add(GondolaSuspensionStructureItem(
            inspectedPart = getCheckItemValueForIndexed("nde_suspensionStructure_item", index, "inspectedPart"),
            location = getCheckItemValueForIndexed("nde_suspensionStructure_item", index, "location"),
            defect = getCheckItemValueForIndexed("nde_suspensionStructure_item", index, "defect"),
            description = getCheckItemValueForIndexed("nde_suspensionStructure_item", index, "description")
        ))
    }

    val ndeCageItems = mutableListOf<GondolaCageItem>()
    val cageItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_gondolaCage_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    cageItemCategories.forEach { index ->
        ndeCageItems.add(GondolaCageItem(
            inspectedPart = getCheckItemValueForIndexed("nde_gondolaCage_item", index, "inspectedPart"),
            location = getCheckItemValueForIndexed("nde_gondolaCage_item", index, "location"),
            defect = getCheckItemValueForIndexed("nde_gondolaCage_item", index, "defect"),
            description = getCheckItemValueForIndexed("nde_gondolaCage_item", index, "description")
        ))
    }

    val ndeClampsItems = mutableListOf<GondolaClampsItem>()
    val clampsItemCategories = this.checkItems.map { it.category }.filter { it.startsWith("nde_clamps_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    clampsItemCategories.forEach { index ->
        ndeClampsItems.add(GondolaClampsItem(
            inspectedPart = getCheckItemValueForIndexed("nde_clamps_item", index, "inspectedPart"),
            location = getCheckItemValueForIndexed("nde_clamps_item", index, "location"),
            defect = getCheckItemValueForIndexed("nde_clamps_item", index, "defect"),
            description = getCheckItemValueForIndexed("nde_clamps_item", index, "description")
        ))
    }

    val nonDestructiveTesting = GondolaNonDestructiveTesting(
        steelWireRope = GondolaNdtSection(
            ndtType = getCheckItemValue("nde_steelWireRope", "ndtType"),
            items = ndeWireRopeItems.toImmutableList()
        ),
        suspensionStructure = GondolaNdtSection(
            ndtType = getCheckItemValue("nde_suspensionStructure", "ndtType"),
            items = ndeSuspensionItems.toImmutableList()
        ),
        gondolaCage = GondolaNdtSection(
            ndtType = getCheckItemValue("nde_gondolaCage", "ndtType"),
            items = ndeCageItems.toImmutableList()
        ),
        clamps = GondolaNdtSection(
            ndtType = getCheckItemValue("nde_clamps", "ndtType"),
            items = ndeClampsItems.toImmutableList()
        )
    )

    // 5. Reconstruct Testing
    val dynamicLoadItems = mutableListOf<GondolaLoadTest>()
    val dynamicLoadCategories = this.checkItems.map { it.category }.filter { it.startsWith("testing_dynamicLoad_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    dynamicLoadCategories.forEach { index ->
        dynamicLoadItems.add(GondolaLoadTest(
            loadPercentage = getCheckItemValueForIndexed("testing_dynamicLoad_item", index, "loadPercentage"),
            load = getCheckItemValueForIndexed("testing_dynamicLoad_item", index, "load"),
            result = getCheckItemValueForIndexed("testing_dynamicLoad_item", index, "result"),
            description = getCheckItemValueForIndexed("testing_dynamicLoad_item", index, "description")
        ))
    }

    val staticLoadItems = mutableListOf<GondolaLoadTest>()
    val staticLoadCategories = this.checkItems.map { it.category }.filter { it.startsWith("testing_staticLoad_item_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    staticLoadCategories.forEach { index ->
        staticLoadItems.add(GondolaLoadTest(
            loadPercentage = getCheckItemValueForIndexed("testing_staticLoad_item", index, "loadPercentage"),
            load = getCheckItemValueForIndexed("testing_staticLoad_item", index, "load"),
            result = getCheckItemValueForIndexed("testing_staticLoad_item", index, "result"),
            description = getCheckItemValueForIndexed("testing_staticLoad_item", index, "description")
        ))
    }

    val testing = GondolaTesting(
        dynamicLoad = dynamicLoadItems.toImmutableList(),
        staticLoad = staticLoadItems.toImmutableList()
    )

    // 6. Reconstruct Conclusion
    val summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }
    val recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }
    val conclusion = GondolaConclusion(
        summary = summary.toImmutableList(),
        recommendations = recommendations.toImmutableList()
    )

    // 7. Assemble the final report
    val report = GondolaInspectionReport(
        extraId = inspection.extraId,
        moreExtraId = inspection.moreExtraId,
        equipmentType = inspection.equipmentType,
        examinationType = inspection.examinationType,
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        nonDestructiveTesting = nonDestructiveTesting,
        testing = testing,
        conclusion = conclusion
    )

    return GondolaUiState(gondolaInspectionReport = report)
}