package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.local.utils.toDocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.toInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toSubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus
import com.nakersolutionid.nakersolutionid.data.remote.dto.gondola.*
import com.nakersolutionid.nakersolutionid.domain.model.*

private object BAPCategoryGondola {
    const val VISUAL_CHECK = "PEMERIKSAAN VISUAL"
    const val FUNCTIONAL_TEST = "UJI FUNGSI"
}

private object ReportCategory {
    const val GENERAL_DATA = "general_data"
    const val TECHNICAL_DATA = "technical_data"
    const val VISUAL_INSPECTION = "visual_inspection"
    const val NDE_STEEL_WIRE_ROPE = "nde_steelWireRope"
    const val NDE_SUSPENSION_STRUCTURE = "nde_suspensionStructure"
    const val NDE_GONDOLA_CAGE = "nde_gondolaCage"
    const val NDE_CLAMPS = "nde_clamps"
    const val TESTING_DYNAMIC_LOAD = "testing_dynamicLoad_item"
    const val TESTING_STATIC_LOAD = "testing_staticLoad_item"
}


// =================================================================================================
//                                  BAP (Berita Acara Pemeriksaan) Mappers
// =================================================================================================

fun InspectionWithDetailsDomain.toGondolaBapRequest(): GondolaBapRequest {
    val checkItems = this.checkItems
    fun findBoolItem(category: String, itemName: String): Boolean {
        return checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findStringItem(category: String, itemName: String): String {
        return checkItems.find { it.category == category && it.itemName == itemName }?.result ?: ""
    }

    val generalData = GondolaBapGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.usageLocation ?: "",
        userInCharge = this.inspection.addressUsageLocation ?: "",
        ownerAddress = this.inspection.ownerAddress ?: ""
    )

    val technicalData = GondolaBapTechnicalData(
        manufacturer = this.inspection.manufacturer?.name ?: "",
        locationAndYearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = this.inspection.serialNumber ?: "",
        intendedUse = this.inspection.manufacturer?.brandOrType ?: "",
        capacityWorkingLoad = this.inspection.capacity ?: "",
        maxLiftingHeightMeters = this.inspection.floorServed ?: ""
    )

    val driveMotorCondition = GondolaDriveMotorCondition(
        isGoodCondition = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Motor Penggerak Kondisi Baik"),
        hasOilLeak = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Terdapat Kebocoran Oli Motor")
    )

    val visualCheck = GondolaBapVisualCheck(
        isSlingDiameterAcceptable = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Diameter Sling Dapat Diterima"),
        isBumperInstalled = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Bumper Terpasang"),
        isCapacityMarkingDisplayed = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Penandaan Kapasitas Terpasang"),
        isPlatformConditionAcceptable = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Kondisi Platform Dapat Diterima"),
        driveMotorCondition = driveMotorCondition,
        isControlPanelClean = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Panel Kontrol Bersih"),
        isBodyHarnessAvailable = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Body Harness Tersedia"),
        isLifelineAvailable = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Lifeline Tersedia"),
        isButtonLabelsDisplayed = findBoolItem(BAPCategoryGondola.VISUAL_CHECK, "Label Tombol Terpasang")
    )

    val ndtTest = GondolaNdtTest(
        method = findStringItem(BAPCategoryGondola.FUNCTIONAL_TEST, "Metode NDT"),
        isResultGood = findBoolItem(BAPCategoryGondola.FUNCTIONAL_TEST, "Hasil NDT Baik"),
        hasCrackIndication = findBoolItem(BAPCategoryGondola.FUNCTIONAL_TEST, "Ada Indikasi Retak")
    )

    val functionalTest = GondolaBapFunctionalTest(
        isWireRopeMeasurementOk = findBoolItem(BAPCategoryGondola.FUNCTIONAL_TEST, "Pengukuran Wire Rope OK"),
        isUpDownFunctionOk = findBoolItem(BAPCategoryGondola.FUNCTIONAL_TEST, "Fungsi Naik Turun OK"),
        isDriveMotorFunctionOk = findBoolItem(BAPCategoryGondola.FUNCTIONAL_TEST, "Fungsi Motor Penggerak OK"),
        isEmergencyStopFunctional = findBoolItem(BAPCategoryGondola.FUNCTIONAL_TEST, "Stop Darurat Berfungsi"),
        isSafetyLifelineFunctional = findBoolItem(BAPCategoryGondola.FUNCTIONAL_TEST, "Safety Lifeline Berfungsi"),
        ndtTest = ndtTest
    )

    val inspectionResult = GondolaBapInspectionResult(
        visualCheck = visualCheck,
        functionalTest = functionalTest
    )

    return GondolaBapRequest(
        laporanId = this.inspection.extraId,
        examinationType = this.inspection.examinationType,
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        inspectionDate = this.inspection.reportDate ?: "",
        equipmentType = this.inspection.equipmentType,
        createdAt = this.inspection.createdAt ?: "",
        extraId = this.inspection.id,
        generalData = generalData,
        technicalData = technicalData,
        inspectionResult = inspectionResult
    )
}

fun GondolaBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id,
        documentType = this.documentType.toDocumentType() ?: DocumentType.BAP,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.PAA,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Gondola,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.companyLocation,
        addressUsageLocation = this.generalData.userInCharge,
        driveType = null,
        serialNumber = this.technicalData.serialNumberUnitNumber,
        permitNumber = null,
        capacity = this.technicalData.capacityWorkingLoad,
        speed = null,
        floorServed = this.technicalData.maxLiftingHeightMeters,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.intendedUse,
            year = this.technicalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        status = null,
        isSynced = true
    )

    val visualCheck = this.inspectionResult.visualCheck
    val functionalTest = this.inspectionResult.functionalTest
    val catVisual = BAPCategoryGondola.VISUAL_CHECK
    val catFunctional = BAPCategoryGondola.FUNCTIONAL_TEST

    val checkItems = listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Diameter Sling Dapat Diterima", status = visualCheck.isSlingDiameterAcceptable),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Bumper Terpasang", status = visualCheck.isBumperInstalled),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Penandaan Kapasitas Terpasang", status = visualCheck.isCapacityMarkingDisplayed),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Kondisi Platform Dapat Diterima", status = visualCheck.isPlatformConditionAcceptable),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Motor Penggerak Kondisi Baik", status = visualCheck.driveMotorCondition.isGoodCondition),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Terdapat Kebocoran Oli Motor", status = visualCheck.driveMotorCondition.hasOilLeak),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Panel Kontrol Bersih", status = visualCheck.isControlPanelClean),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Body Harness Tersedia", status = visualCheck.isBodyHarnessAvailable),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Lifeline Tersedia", status = visualCheck.isLifelineAvailable),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catVisual, itemName = "Label Tombol Terpasang", status = visualCheck.isButtonLabelsDisplayed),

        InspectionCheckItemDomain(inspectionId = inspectionId, category = catFunctional, itemName = "Pengukuran Wire Rope OK", status = functionalTest.isWireRopeMeasurementOk),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catFunctional, itemName = "Fungsi Naik Turun OK", status = functionalTest.isUpDownFunctionOk),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catFunctional, itemName = "Fungsi Motor Penggerak OK", status = functionalTest.isDriveMotorFunctionOk),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catFunctional, itemName = "Stop Darurat Berfungsi", status = functionalTest.isEmergencyStopFunctional),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catFunctional, itemName = "Safety Lifeline Berfungsi", status = functionalTest.isSafetyLifelineFunctional),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catFunctional, itemName = "Metode NDT", status = functionalTest.ndtTest.method.isNotEmpty(), result = functionalTest.ndtTest.method),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catFunctional, itemName = "Hasil NDT Baik", status = functionalTest.ndtTest.isResultGood),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = catFunctional, itemName = "Ada Indikasi Retak", status = functionalTest.ndtTest.hasCrackIndication)
    )

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = emptyList()
    )
}


// =================================================================================================
//                                  Report (Laporan) Mappers
// =================================================================================================

fun InspectionWithDetailsDomain.toGondolaReportRequest(): GondolaReportRequest {
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getString(category: String, itemName: String): String =
        checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""

    fun getResult(category: String, itemName: String): ResultStatus {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return ResultStatus(status = item?.status ?: false, result = item?.result ?: "")
    }

    fun <T> getList(
        prefix: String,
        builder: (Int, (String, String) -> String) -> T
    ): List<T> {
        val itemCategories = checkItems.map { it.category }
            .filter { it.startsWith(prefix) }
            .mapNotNull { it.substringAfterLast('_').toIntOrNull() }
            .distinct()

        return itemCategories.map { index ->
            builder(index) { cat, itemName ->
                getString(cat, itemName)
            }
        }
    }

    val generalData = GondolaGeneralData(
        ownerName = inspection.ownerName ?: "",
        ownerAddress = inspection.ownerAddress ?: "",
        userInCharge = inspection.addressUsageLocation ?: "",
        subcontractorPersonInCharge = getString(ReportCategory.GENERAL_DATA, "personInCharge"),
        unitLocation = inspection.usageLocation ?: "",
        operatorName = getString(ReportCategory.GENERAL_DATA, "operatorName"),
        equipmentType = inspection.equipmentType,
        manufacturer = inspection.manufacturer?.name ?: "",
        brandType = inspection.manufacturer?.brandOrType ?: "",
        locationAndYearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = inspection.serialNumber ?: "",
        capacityWorkingLoad = inspection.capacity ?: "",
        intendedUse = getString(ReportCategory.GENERAL_DATA, "usedFor"),
        usagePermitNumber = inspection.permitNumber ?: "",
        operatorCertificate = getString(ReportCategory.GENERAL_DATA, "operatorCertificate")
    )

    val technicalData = GondolaTechnicalData(
        gondolaSpecification = GondolaSpecification(
            supportMastHeight = getString(ReportCategory.TECHNICAL_DATA, "spec_supportPoleHeight").toIntOrNull() ?: 0,
            frontBeamLength = getString(ReportCategory.TECHNICAL_DATA, "spec_beam_front").toIntOrNull() ?: 0,
            middleBeamLength = getString(ReportCategory.TECHNICAL_DATA, "spec_beam_middle").toIntOrNull() ?: 0,
            rearBeamLength = getString(ReportCategory.TECHNICAL_DATA, "spec_beam_rear").toIntOrNull() ?: 0,
            balanceWeightDistance = getString(ReportCategory.TECHNICAL_DATA, "spec_balanceWeightToBeamDistance").toIntOrNull() ?: 0,
            capacity = getString(ReportCategory.TECHNICAL_DATA, "spec_capacity"),
            speed = getString(ReportCategory.TECHNICAL_DATA, "spec_speed"),
            platformSize = "${getString(ReportCategory.TECHNICAL_DATA, "spec_platform_length")} x ${getString(ReportCategory.TECHNICAL_DATA, "spec_platform_width")} x ${getString(ReportCategory.TECHNICAL_DATA, "spec_platform_height")}",
            wireRopeDiameter = getString(ReportCategory.TECHNICAL_DATA, "spec_wireRope").toDoubleOrNull() ?: 0.0
        ),
        hoist = GondolaHoist(
            model = getString(ReportCategory.TECHNICAL_DATA, "hoist_model"),
            liftingCapacity = getString(ReportCategory.TECHNICAL_DATA, "hoist_liftingPower").toIntOrNull() ?: 0,
            electricMotor = GondolaElectricMotor(
                type = getString(ReportCategory.TECHNICAL_DATA, "hoist_motor_type"),
                power = getString(ReportCategory.TECHNICAL_DATA, "hoist_motor_power"),
                voltage = getString(ReportCategory.TECHNICAL_DATA, "hoist_motor_voltage").toIntOrNull() ?: 0,
                voltageHz = getString(ReportCategory.TECHNICAL_DATA, "hoist_motor_voltageHz").toIntOrNull() ?: 0
            )
        ),
        safetyLockType = getString(ReportCategory.TECHNICAL_DATA, "safetyLockType"),
        brake = GondolaBrake(
            type = getString(ReportCategory.TECHNICAL_DATA, "brake_type"),
            model = getString(ReportCategory.TECHNICAL_DATA, "brake_model"),
            capacity = getString(ReportCategory.TECHNICAL_DATA, "brake_capacity")
        ),
        suspensionMechanical = GondolaSuspensionMechanical(
            supportMastHeight = getString(ReportCategory.TECHNICAL_DATA, "suspension_supportPoleHeight").toIntOrNull() ?: 0,
            frontBeamLength = getString(ReportCategory.TECHNICAL_DATA, "suspension_frontBeamLength").toIntOrNull() ?: 0,
            material = getString(ReportCategory.TECHNICAL_DATA, "suspension_material")
        ),
        machineWeight = GondolaMachineWeight(
            totalPlatformWeight = getString(ReportCategory.TECHNICAL_DATA, "weight_platform").toIntOrNull() ?: 0,
            suspensionMechanicalWeight = getString(ReportCategory.TECHNICAL_DATA, "weight_suspension").toIntOrNull() ?: 0,
            balanceWeight = getString(ReportCategory.TECHNICAL_DATA, "weight_balance").toIntOrNull() ?: 0,
            totalMachineWeight = getString(ReportCategory.TECHNICAL_DATA, "weight_total")
        )
    )

    val visualInspection = GondolaVisualInspection(
        suspensionStructure = GondolaSuspensionStructure(
            frontBeam = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureFrontBeam"),
            middleBeam = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureMiddleBeam"),
            rearBeam = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureRearBeam"),
            frontBeamSupportMast = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureFrontBeamSupportPole"),
            lowerFrontBeamSupportMast = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureLowerFrontBeamSupportPole"),
            mastAndBeamClamp = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureReinforcementClamp"),
            couplingSleeve = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureCouplingSleeve"),
            turnBuckle = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureTurnbuckle"),
            reinforcementRope = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureReinforcementRope"),
            rearSupportMast = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureRearSupportPole"),
            balanceWeight = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureBalanceWeight"),
            frontBeamSupportBase = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureFrontSupportPoleBase"),
            rearBeamSupportBase = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureRearSupportPoleBase"),
            jackBaseJoint = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureJackBaseJoint"),
            connectionBolts = getResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureConnectionBolts")
        ),
        steelWireRope = GondolaSteelWireRope(
            mainWireRope = getResult(ReportCategory.VISUAL_INSPECTION, "steelWireRopeMainRope"),
            safetyRope = getResult(ReportCategory.VISUAL_INSPECTION, "steelWireRopeSafetyRope"),
            slingFasteners = getResult(ReportCategory.VISUAL_INSPECTION, "steelWireRopeSlingBinder")
        ),
        electricalSystem = GondolaElectricalSystem(
            hoistMotor = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemHoistMotor"),
            brakeRelease = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemBrakeRelease"),
            manualRelease = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemManualRelease"),
            powerControl = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemPowerControl"),
            powerCable = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemPowerCable"),
            handleSwitch = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemHandleSwitch"),
            upperLimitSwitch = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemUpperLimitSwitch"),
            limitStopper = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemLimitStopper"),
            socketFitting = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemSocketFitting"),
            grounding = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemGrounding"),
            breakerFuse = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemBreakerFuse"),
            emergencyStop = getResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemEmergencyStop")
        ),
        platform = GondolaPlatform(
            hoistMountFrame = getResult(ReportCategory.VISUAL_INSPECTION, "platformHoistMountingFrame"),
            frame = getResult(ReportCategory.VISUAL_INSPECTION, "platformFrame"),
            bottomPlate = getResult(ReportCategory.VISUAL_INSPECTION, "platformBottomPlate"),
            pinsAndBolts = getResult(ReportCategory.VISUAL_INSPECTION, "platformPinsAndBolts"),
            bracket = getResult(ReportCategory.VISUAL_INSPECTION, "platformBracket"),
            toeBoard = getResult(ReportCategory.VISUAL_INSPECTION, "platformToeBoard"),
            rollerAndGuidePulley = getResult(ReportCategory.VISUAL_INSPECTION, "platformRollerAndGuidePulley"),
            namePlate = getResult(ReportCategory.VISUAL_INSPECTION, "platformNameplate")
        ),
        safetyDevices = GondolaSafetyDevices(
            safetyLock = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesSafetyLock"),
            rubberBumper = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesRubberBumper"),
            safetyLifeLine = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesSafetyLifeline"),
            loadLimitSwitch = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesLoadLimitSwitch"),
            limitBlock = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesLimitBlock"),
            upperLimitSwitch = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesUpperLimitSwitch"),
            bodyHarness = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesBodyHarness"),
            harnessAnchorage = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesSafetyHarnessAnchorage"),
            communicationTool = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesHandyTalkie"),
            handyTalkie = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesHandyTalkie"),
            safetyHelmet = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesSafetyHelmet"),
            handRail = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesHandrail"),
            otherPpe = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesOtherPpe"),
            coupForGlass = getResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesCoupForGlass")
        )
    )

    val nonDestructiveTesting = GondolaNonDestructiveTesting(
        steelCableRope = GondolaSteelCableRopeNdt(
            ndtType = getString(ReportCategory.NDE_STEEL_WIRE_ROPE, "ndtType"),
            items = getList("${ReportCategory.NDE_STEEL_WIRE_ROPE}_item") { index, getItem ->
                val cat = "${ReportCategory.NDE_STEEL_WIRE_ROPE}_item_$index"
                val defect = getItem(cat, "defect")
                val description = getItem(cat, "description")
                GondolaSteelCableRopeNdtItem(
                    usage = getItem(cat, "usage"),
                    specDiameter = getItem(cat, "specDiameter"),
                    actualDiameter = getItem(cat, "actualDiameter"),
                    construction = getItem(cat, "construction"),
                    type = getItem(cat, "type"),
                    length = getItem(cat, "length"),
                    age = getItem(cat, "age"),
                    // **FIX**: Map domain `defect` to DTO `defectFound` (boolean)
                    defectFound = defect.isNotBlank(),
                    // **FIX**: Combine domain `defect` and `description` into DTO `result` to prevent data loss
                    result = if (defect.isNotBlank() || description.isNotBlank()) "$defect - $description" else ""
                )
            }
        ),
        suspensionStructure = GondolaSuspensionStructureNdt(
            ndtType = getString(ReportCategory.NDE_SUSPENSION_STRUCTURE, "ndtType"),
            items = getList("${ReportCategory.NDE_SUSPENSION_STRUCTURE}_item") { index, getItem ->
                val cat = "${ReportCategory.NDE_SUSPENSION_STRUCTURE}_item_$index"
                val defect = getItem(cat, "defect")
                val description = getItem(cat, "description")
                GondolaSuspensionStructureNdtItem(
                    part = getItem(cat, "inspectedPart"),
                    location = getItem(cat, "location"),
                    // **FIX**: Map domain `defect` to DTO `defectFound` (boolean)
                    defectFound = defect.isNotBlank(),
                    // **FIX**: Combine domain `defect` and `description` into DTO `result`
                    result = if (defect.isNotBlank() || description.isNotBlank()) "$defect - $description" else ""
                )
            }
        ),
        gondolaCage = GondolaCageNdt(
            ndtType = getString(ReportCategory.NDE_GONDOLA_CAGE, "ndtType"),
            items = getList("${ReportCategory.NDE_GONDOLA_CAGE}_item") { index, getItem ->
                val cat = "${ReportCategory.NDE_GONDOLA_CAGE}_item_$index"
                val defect = getItem(cat, "defect")
                val description = getItem(cat, "description")
                GondolaCageNdtItem(
                    part = getItem(cat, "inspectedPart"),
                    location = getItem(cat, "location"),
                    // **FIX**: Map domain `defect` to DTO `defectFound` (boolean)
                    defectFound = defect.isNotBlank(),
                    // **FIX**: Combine domain `defect` and `description` into DTO `result`
                    result = if (defect.isNotBlank() || description.isNotBlank()) "$defect - $description" else ""
                )
            }
        ),
        clamps = GondolaClampsNdt(
            items = getList("${ReportCategory.NDE_CLAMPS}_item") { index, getItem ->
                val cat = "${ReportCategory.NDE_CLAMPS}_item_$index"
                val defect = getItem(cat, "defect")
                val description = getItem(cat, "description")
                GondolaClampsNdtItem(
                    partCheck = getItem(cat, "inspectedPart"),
                    location = getItem(cat, "location"),
                    // **FIX**: Map domain `defect` to DTO `defectFound` (boolean)
                    defectFound = defect.isNotBlank(),
                    // **FIX**: Combine domain `defect` and `description` into DTO `result`
                    result = if (defect.isNotBlank() || description.isNotBlank()) "$defect - $description" else ""
                )
            }
        )
    )

    val testing = GondolaTesting(
        dynamicLoadTest = GondolaDynamicLoadTest(
            note = "", // Not in UI state
            items = getList(ReportCategory.TESTING_DYNAMIC_LOAD) { index, getItem ->
                val cat = "${ReportCategory.TESTING_DYNAMIC_LOAD}_$index"
                GondolaLoadTestItem(
                    loadPercentage = getItem(cat, "loadPercentage"),
                    // **FIX**: Map domain `load` to DTO `loadDetails`
                    loadDetails = getItem(cat, "load"),
                    // **FIX**: Map domain `description` to DTO `remarks`
                    remarks = getItem(cat, "description"),
                    result = getItem(cat, "result")
                )
            }
        ),
        staticLoadTest = GondolaStaticLoadTest(
            note = "", // Not in UI state
            items = getList(ReportCategory.TESTING_STATIC_LOAD) { index, getItem ->
                val cat = "${ReportCategory.TESTING_STATIC_LOAD}_$index"
                GondolaLoadTestItem(
                    loadPercentage = getItem(cat, "loadPercentage"),
                    // **FIX**: Map domain `load` to DTO `loadDetails`
                    loadDetails = getItem(cat, "load"),
                    // **FIX**: Map domain `description` to DTO `remarks`
                    remarks = getItem(cat, "description"),
                    result = getItem(cat, "result")
                )
            }
        )
    )

    val conclusion = findings.filter { it.type == FindingType.FINDING }.joinToString("\n") { it.description }
    val recommendation = findings.filter { it.type == FindingType.RECOMMENDATION }.joinToString("\n") { it.description }

    return GondolaReportRequest(
        inspectionType = inspection.inspectionType.toDisplayString(),
        examinationType = inspection.examinationType,
        inspectionDate = inspection.reportDate ?: "",
        extraId = inspection.id,
        createdAt = inspection.createdAt ?: "",
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        nonDestructiveTesting = nonDestructiveTesting,
        testing = testing,
        conclusion = conclusion,
        recommendation = recommendation
    )
}

fun GondolaReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id, // cloud report id
        moreExtraId = "",
        documentType = this.documentType.toDocumentType() ?: DocumentType.LAPORAN,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.PAA,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.Gondola,
        equipmentType = this.generalData.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.unitLocation,
        addressUsageLocation = this.generalData.userInCharge,
        driveType = null,
        serialNumber = this.generalData.serialNumberUnitNumber,
        permitNumber = this.generalData.usagePermitNumber,
        capacity = this.generalData.capacityWorkingLoad,
        speed = this.technicalData.gondolaSpecification.speed,
        floorServed = null,
        manufacturer = ManufacturerDomain(
            name = this.generalData.manufacturer,
            brandOrType = this.generalData.brandType,
            year = this.generalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        status = this.conclusion,
        isSynced = true
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    fun add(cat: String, name: String, value: String?) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = cat,
                itemName = name,
                status = false,
                result = value ?: ""
            )
        )
    }

    fun addResult(cat: String, name: String, value: ResultStatus?) {
        val v = value ?: ResultStatus(status = false, result = "")
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = cat,
                itemName = name,
                status = v.status,
                result = v.result
            )
        )
    }

    // Flatten general and technical data
    add(ReportCategory.GENERAL_DATA, "personInCharge", this.generalData.subcontractorPersonInCharge)
    add(ReportCategory.GENERAL_DATA, "operatorName", this.generalData.operatorName)
    add(ReportCategory.GENERAL_DATA, "operatorCertificate", this.generalData.operatorCertificate)
    add(ReportCategory.GENERAL_DATA, "usedFor", this.generalData.intendedUse)

    this.technicalData.let { tech ->
        tech.gondolaSpecification.let {
            add(ReportCategory.TECHNICAL_DATA, "spec_supportPoleHeight", it.supportMastHeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_beam_front", it.frontBeamLength.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_beam_middle", it.middleBeamLength.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_beam_rear", it.rearBeamLength.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_balanceWeightToBeamDistance", it.balanceWeightDistance.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_capacity", it.capacity)
            add(ReportCategory.TECHNICAL_DATA, "spec_speed", it.speed)
            val platformParts = it.platformSize.split("x").map { part -> part.trim() }
            add(ReportCategory.TECHNICAL_DATA, "spec_platform_length", platformParts.getOrNull(0) ?: "")
            add(ReportCategory.TECHNICAL_DATA, "spec_platform_width", platformParts.getOrNull(1) ?: "")
            add(ReportCategory.TECHNICAL_DATA, "spec_platform_height", platformParts.getOrNull(2) ?: "")
            add(ReportCategory.TECHNICAL_DATA, "spec_wireRope", it.wireRopeDiameter.toString())
        }
        tech.hoist.let {
            add(ReportCategory.TECHNICAL_DATA, "hoist_model", it.model)
            add(ReportCategory.TECHNICAL_DATA, "hoist_liftingPower", it.liftingCapacity.toString())
            add(ReportCategory.TECHNICAL_DATA, "hoist_motor_type", it.electricMotor.type)
            add(ReportCategory.TECHNICAL_DATA, "hoist_motor_power", it.electricMotor.power)
            add(ReportCategory.TECHNICAL_DATA, "hoist_motor_voltage", it.electricMotor.voltage.toString())
        }
        add(ReportCategory.TECHNICAL_DATA, "safetyLockType", tech.safetyLockType)
        tech.brake.let {
            add(ReportCategory.TECHNICAL_DATA, "brake_type", it.type)
            add(ReportCategory.TECHNICAL_DATA, "brake_model", it.model)
            add(ReportCategory.TECHNICAL_DATA, "brake_capacity", it.capacity)
        }
        tech.suspensionMechanical.let {
            add(ReportCategory.TECHNICAL_DATA, "suspension_supportPoleHeight", it.supportMastHeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "suspension_frontBeamLength", it.frontBeamLength.toString())
            add(ReportCategory.TECHNICAL_DATA, "suspension_material", it.material)
        }
        tech.machineWeight.let {
            add(ReportCategory.TECHNICAL_DATA, "weight_platform", it.totalPlatformWeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "weight_suspension", it.suspensionMechanicalWeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "weight_balance", it.balanceWeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "weight_total", it.totalMachineWeight)
        }
    }

    // Flatten visual inspection
    this.visualInspection.let { visual ->
        visual.suspensionStructure.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureFrontBeam", it.frontBeam)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureMiddleBeam", it.middleBeam)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureRearBeam", it.rearBeam)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureFrontBeamSupportPole", it.frontBeamSupportMast)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureLowerFrontBeamSupportPole", it.lowerFrontBeamSupportMast)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureReinforcementClamp", it.mastAndBeamClamp)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureCouplingSleeve", it.couplingSleeve)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureTurnbuckle", it.turnBuckle)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureReinforcementRope", it.reinforcementRope)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureRearSupportPole", it.rearSupportMast)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureBalanceWeight", it.balanceWeight)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureFrontSupportPoleBase", it.frontBeamSupportBase)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureRearSupportPoleBase", it.rearBeamSupportBase)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureJackBaseJoint", it.jackBaseJoint)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspensionStructureConnectionBolts", it.connectionBolts)
        }
        visual.steelWireRope.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "steelWireRopeMainRope", it.mainWireRope)
            addResult(ReportCategory.VISUAL_INSPECTION, "steelWireRopeSafetyRope", it.safetyRope)
            addResult(ReportCategory.VISUAL_INSPECTION, "steelWireRopeSlingBinder", it.slingFasteners)
        }
        visual.electricalSystem.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemHoistMotor", it.hoistMotor)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemBrakeRelease", it.brakeRelease)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemManualRelease", it.manualRelease)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemPowerControl", it.powerControl)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemPowerCable", it.powerCable)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemHandleSwitch", it.handleSwitch)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemUpperLimitSwitch", it.upperLimitSwitch)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemLimitStopper", it.limitStopper)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemSocketFitting", it.socketFitting)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemGrounding", it.grounding)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemBreakerFuse", it.breakerFuse)
            addResult(ReportCategory.VISUAL_INSPECTION, "electricalSystemEmergencyStop", it.emergencyStop)
        }
        visual.platform.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "platformHoistMountingFrame", it.hoistMountFrame)
            addResult(ReportCategory.VISUAL_INSPECTION, "platformFrame", it.frame)
            addResult(ReportCategory.VISUAL_INSPECTION, "platformBottomPlate", it.bottomPlate)
            addResult(ReportCategory.VISUAL_INSPECTION, "platformPinsAndBolts", it.pinsAndBolts)
            addResult(ReportCategory.VISUAL_INSPECTION, "platformBracket", it.bracket)
            addResult(ReportCategory.VISUAL_INSPECTION, "platformToeBoard", it.toeBoard)
            addResult(ReportCategory.VISUAL_INSPECTION, "platformRollerAndGuidePulley", it.rollerAndGuidePulley)
            addResult(ReportCategory.VISUAL_INSPECTION, "platformNameplate", it.namePlate)
        }
        visual.safetyDevices.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesSafetyLock", it.safetyLock)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesRubberBumper", it.rubberBumper)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesSafetyLifeline", it.safetyLifeLine)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesLoadLimitSwitch", it.loadLimitSwitch)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesLimitBlock", it.limitBlock)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesUpperLimitSwitch", it.upperLimitSwitch)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesBodyHarness", it.bodyHarness)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesSafetyHarnessAnchorage", it.harnessAnchorage)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesHandyTalkie", it.handyTalkie)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesSafetyHelmet", it.safetyHelmet)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesHandrail", it.handRail)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesOtherPpe", it.otherPpe)
            addResult(ReportCategory.VISUAL_INSPECTION, "safetyDevicesCoupForGlass", it.coupForGlass)
        }
    }

    // Flatten NDT and Testing
    this.nonDestructiveTesting.let { ndt ->
        add(ReportCategory.NDE_STEEL_WIRE_ROPE, "ndtType", ndt.steelCableRope.ndtType)
        ndt.steelCableRope.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.NDE_STEEL_WIRE_ROPE}_item_$i"
            add(cat, "usage", item.usage)
            add(cat, "specDiameter", item.specDiameter)
            add(cat, "actualDiameter", item.actualDiameter)
            add(cat, "construction", item.construction)
            add(cat, "type", item.type)
            add(cat, "length", item.length)
            add(cat, "age", item.age)
            // **FIX**: Unpack DTO `result` back into domain `defect` and `description`.
            // While we cannot perfectly split them, we put the full content into description for review.
            add(cat, "defect", if(item.defectFound) "Defect Found" else "")
            add(cat, "description", item.result)
        }
        add(ReportCategory.NDE_SUSPENSION_STRUCTURE, "ndtType", ndt.suspensionStructure.ndtType)
        ndt.suspensionStructure.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.NDE_SUSPENSION_STRUCTURE}_item_$i"
            add(cat, "inspectedPart", item.part)
            add(cat, "location", item.location)
            add(cat, "defect", if(item.defectFound) "Defect Found" else "")
            add(cat, "description", item.result)
        }
        add(ReportCategory.NDE_GONDOLA_CAGE, "ndtType", ndt.gondolaCage.ndtType)
        ndt.gondolaCage.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.NDE_GONDOLA_CAGE}_item_$i"
            add(cat, "inspectedPart", item.part)
            add(cat, "location", item.location)
            add(cat, "defect", if(item.defectFound) "Defect Found" else "")
            add(cat, "description", item.result)
        }
        ndt.clamps.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.NDE_CLAMPS}_item_$i"
            add(cat, "inspectedPart", item.partCheck)
            add(cat, "location", item.location)
            add(cat, "defect", if(item.defectFound) "Defect Found" else "")
            add(cat, "description", item.result)
        }
    }

    this.testing.let { testing ->
        add(ReportCategory.TESTING_DYNAMIC_LOAD, "note", testing.dynamicLoadTest.note)
        testing.dynamicLoadTest.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.TESTING_DYNAMIC_LOAD}_$i"
            add(cat, "loadPercentage", item.loadPercentage)
            // **FIX**: Map DTO `loadDetails` back to domain `load`
            add(cat, "load", item.loadDetails)
            add(cat, "result", item.result)
            // **FIX**: Map DTO `remarks` back to domain `description`
            add(cat, "description", item.remarks)
        }
        add(ReportCategory.TESTING_STATIC_LOAD, "note", testing.staticLoadTest.note)
        testing.staticLoadTest.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.TESTING_STATIC_LOAD}_$i"
            add(cat, "loadPercentage", item.loadPercentage)
            // **FIX**: Map DTO `loadDetails` back to domain `load`
            add(cat, "load", item.loadDetails)
            add(cat, "result", item.result)
            // **FIX**: Map DTO `remarks` back to domain `description`
            add(cat, "description", item.remarks)
        }
    }

    val findings = mutableListOf<InspectionFindingDomain>()
    if (this.conclusion.isNotBlank()) {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = this.conclusion, type = FindingType.FINDING))
    }
    if (this.recommendation.isNotBlank()) {
        this.recommendation.split("\n").forEach {
            if (it.isNotBlank()) {
                findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION))
            }
        }
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList()
    )
}