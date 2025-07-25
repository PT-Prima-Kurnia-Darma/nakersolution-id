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
        subcontractorPersonInCharge = getString(ReportCategory.GENERAL_DATA, "subcontractorPersonInCharge"),
        unitLocation = inspection.usageLocation ?: "",
        operatorName = getString(ReportCategory.GENERAL_DATA, "operatorName"),
        equipmentType = inspection.equipmentType,
        manufacturer = inspection.manufacturer?.name ?: "",
        brandType = inspection.manufacturer?.brandOrType ?: "",
        locationAndYearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = inspection.serialNumber ?: "",
        capacityWorkingLoad = inspection.capacity ?: "",
        intendedUse = getString(ReportCategory.TECHNICAL_DATA, "spec_intendedUse"), // This seems to be missing from domain direct fields
        usagePermitNumber = inspection.permitNumber ?: "",
        operatorCertificate = getString(ReportCategory.GENERAL_DATA, "operatorCertificate")
    )

    val technicalData = GondolaTechnicalData(
        gondolaSpecification = GondolaSpecification(
            supportMastHeight = getString(ReportCategory.TECHNICAL_DATA, "spec_supportMastHeight").toIntOrNull() ?: 0,
            frontBeamLength = getString(ReportCategory.TECHNICAL_DATA, "spec_frontBeamLength").toIntOrNull() ?: 0,
            middleBeamLength = getString(ReportCategory.TECHNICAL_DATA, "spec_middleBeamLength").toIntOrNull() ?: 0,
            rearBeamLength = getString(ReportCategory.TECHNICAL_DATA, "spec_rearBeamLength").toIntOrNull() ?: 0,
            balanceWeightDistance = getString(ReportCategory.TECHNICAL_DATA, "spec_balanceWeightDistance").toIntOrNull() ?: 0,
            capacity = getString(ReportCategory.TECHNICAL_DATA, "spec_capacity"),
            speed = getString(ReportCategory.TECHNICAL_DATA, "spec_speed"),
            platformSize = getString(ReportCategory.TECHNICAL_DATA, "spec_platformSize"),
            wireRopeDiameter = getString(ReportCategory.TECHNICAL_DATA, "spec_wireRopeDiameter").toDoubleOrNull() ?: 0.0
        ),
        hoist = GondolaHoist(
            model = getString(ReportCategory.TECHNICAL_DATA, "hoist_model"),
            liftingCapacity = getString(ReportCategory.TECHNICAL_DATA, "hoist_liftingCapacity").toIntOrNull() ?: 0,
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
            supportMastHeight = getString(ReportCategory.TECHNICAL_DATA, "suspension_supportMastHeight").toIntOrNull() ?: 0,
            frontBeamLength = getString(ReportCategory.TECHNICAL_DATA, "suspension_frontBeamLength").toIntOrNull() ?: 0,
            material = getString(ReportCategory.TECHNICAL_DATA, "suspension_material")
        ),
        machineWeight = GondolaMachineWeight(
            totalPlatformWeight = getString(ReportCategory.TECHNICAL_DATA, "weight_totalPlatformWeight").toIntOrNull() ?: 0,
            suspensionMechanicalWeight = getString(ReportCategory.TECHNICAL_DATA, "weight_suspensionMechanicalWeight").toIntOrNull() ?: 0,
            balanceWeight = getString(ReportCategory.TECHNICAL_DATA, "weight_balanceWeight").toIntOrNull() ?: 0,
            totalMachineWeight = getString(ReportCategory.TECHNICAL_DATA, "weight_totalMachineWeight")
        )
    )
    
    val visualInspection = GondolaVisualInspection(
        suspensionStructure = GondolaSuspensionStructure(
            frontBeam = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_frontBeam"),
            middleBeam = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_middleBeam"),
            rearBeam = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_rearBeam"),
            frontBeamSupportMast = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_frontBeamSupportMast"),
            lowerFrontBeamSupportMast = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_lowerFrontBeamSupportMast"),
            mastAndBeamClamp = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_mastAndBeamClamp"),
            couplingSleeve = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_couplingSleeve"),
            turnBuckle = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_turnBuckle"),
            reinforcementRope = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_reinforcementRope"),
            rearSupportMast = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_rearSupportMast"),
            balanceWeight = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_balanceWeight"),
            frontBeamSupportBase = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_frontBeamSupportBase"),
            rearBeamSupportBase = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_rearBeamSupportBase"),
            jackBaseJoint = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_jackBaseJoint"),
            connectionBolts = getResult(ReportCategory.VISUAL_INSPECTION, "suspension_connectionBolts")
        ),
        steelWireRope = GondolaSteelWireRope(
            mainWireRope = getResult(ReportCategory.VISUAL_INSPECTION, "rope_mainWireRope"),
            safetyRope = getResult(ReportCategory.VISUAL_INSPECTION, "rope_safetyRope"),
            slingFasteners = getResult(ReportCategory.VISUAL_INSPECTION, "rope_slingFasteners")
        ),
        electricalSystem = GondolaElectricalSystem(
            hoistMotor = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_hoistMotor"),
            brakeRelease = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_brakeRelease"),
            manualRelease = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_manualRelease"),
            powerControl = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_powerControl"),
            powerCable = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_powerCable"),
            handleSwitch = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_handleSwitch"),
            upperLimitSwitch = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_upperLimitSwitch"),
            limitStopper = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_limitStopper"),
            socketFitting = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_socketFitting"),
            grounding = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_grounding"),
            breakerFuse = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_breakerFuse"),
            emergencyStop = getResult(ReportCategory.VISUAL_INSPECTION, "electrical_emergencyStop")
        ),
        platform = GondolaPlatform(
            hoistMountFrame = getResult(ReportCategory.VISUAL_INSPECTION, "platform_hoistMountFrame"),
            frame = getResult(ReportCategory.VISUAL_INSPECTION, "platform_frame"),
            bottomPlate = getResult(ReportCategory.VISUAL_INSPECTION, "platform_bottomPlate"),
            pinsAndBolts = getResult(ReportCategory.VISUAL_INSPECTION, "platform_pinsAndBolts"),
            bracket = getResult(ReportCategory.VISUAL_INSPECTION, "platform_bracket"),
            toeBoard = getResult(ReportCategory.VISUAL_INSPECTION, "platform_toeBoard"),
            rollerAndGuidePulley = getResult(ReportCategory.VISUAL_INSPECTION, "platform_rollerAndGuidePulley"),
            namePlate = getResult(ReportCategory.VISUAL_INSPECTION, "platform_namePlate")
        ),
        safetyDevices = GondolaSafetyDevices(
            safetyLock = getResult(ReportCategory.VISUAL_INSPECTION, "safety_safetyLock"),
            rubberBumper = getResult(ReportCategory.VISUAL_INSPECTION, "safety_rubberBumper"),
            safetyLifeLine = getResult(ReportCategory.VISUAL_INSPECTION, "safety_safetyLifeLine"),
            loadLimitSwitch = getResult(ReportCategory.VISUAL_INSPECTION, "safety_loadLimitSwitch"),
            limitBlock = getResult(ReportCategory.VISUAL_INSPECTION, "safety_limitBlock"),
            upperLimitSwitch = getResult(ReportCategory.VISUAL_INSPECTION, "safety_upperLimitSwitch"),
            bodyHarness = getResult(ReportCategory.VISUAL_INSPECTION, "safety_bodyHarness"),
            harnessAnchorage = getResult(ReportCategory.VISUAL_INSPECTION, "safety_harnessAnchorage"),
            communicationTool = getResult(ReportCategory.VISUAL_INSPECTION, "safety_communicationTool"),
            handyTalkie = getResult(ReportCategory.VISUAL_INSPECTION, "safety_handyTalkie"),
            safetyHelmet = getResult(ReportCategory.VISUAL_INSPECTION, "safety_safetyHelmet"),
            handRail = getResult(ReportCategory.VISUAL_INSPECTION, "safety_handRail"),
            otherPpe = getResult(ReportCategory.VISUAL_INSPECTION, "safety_otherPpe"),
            coupForGlass = getResult(ReportCategory.VISUAL_INSPECTION, "safety_coupForGlass")
        )
    )

    val nonDestructiveTesting = GondolaNonDestructiveTesting(
        steelCableRope = GondolaSteelCableRopeNdt(
            ndtType = getString(ReportCategory.NDE_STEEL_WIRE_ROPE, "ndtType"),
            items = getList("${ReportCategory.NDE_STEEL_WIRE_ROPE}_item") { index, getItem ->
                val cat = "${ReportCategory.NDE_STEEL_WIRE_ROPE}_item_$index"
                GondolaSteelCableRopeNdtItem(
                    usage = getItem(cat, "usage"),
                    specDiameter = getItem(cat, "specDiameter"),
                    actualDiameter = getItem(cat, "actualDiameter"),
                    construction = getItem(cat, "construction"),
                    type = getItem(cat, "type"),
                    length = getItem(cat, "length"),
                    age = getItem(cat, "age"),
                    defectFound = getItem(cat, "defectFound").toBoolean(),
                    result = getItem(cat, "result")
                )
            }
        ),
        suspensionStructure = GondolaSuspensionStructureNdt(
            ndtType = getString(ReportCategory.NDE_SUSPENSION_STRUCTURE, "ndtType"),
            items = getList("${ReportCategory.NDE_SUSPENSION_STRUCTURE}_item") { index, getItem ->
                val cat = "${ReportCategory.NDE_SUSPENSION_STRUCTURE}_item_$index"
                GondolaSuspensionStructureNdtItem(
                    part = getItem(cat, "part"),
                    location = getItem(cat, "location"),
                    defectFound = getItem(cat, "defectFound").toBoolean(),
                    result = getItem(cat, "result")
                )
            }
        ),
        gondolaCage = GondolaCageNdt(
            ndtType = getString(ReportCategory.NDE_GONDOLA_CAGE, "ndtType"),
            items = getList("${ReportCategory.NDE_GONDOLA_CAGE}_item") { index, getItem ->
                val cat = "${ReportCategory.NDE_GONDOLA_CAGE}_item_$index"
                GondolaCageNdtItem(
                    part = getItem(cat, "part"),
                    location = getItem(cat, "location"),
                    defectFound = getItem(cat, "defectFound").toBoolean(),
                    result = getItem(cat, "result")
                )
            }
        ),
        clamps = GondolaClampsNdt(
            items = getList("${ReportCategory.NDE_CLAMPS}_item") { index, getItem ->
                val cat = "${ReportCategory.NDE_CLAMPS}_item_$index"
                GondolaClampsNdtItem(
                    partCheck = getItem(cat, "partCheck"),
                    location = getItem(cat, "location"),
                    defectFound = getItem(cat, "defectFound").toBoolean(),
                    result = getItem(cat, "result")
                )
            }
        )
    )

    val testing = GondolaTesting(
        dynamicLoadTest = GondolaDynamicLoadTest(
            note = getString(ReportCategory.TESTING_DYNAMIC_LOAD, "note"),
            items = getList(ReportCategory.TESTING_DYNAMIC_LOAD) { index, getItem ->
                val cat = "${ReportCategory.TESTING_DYNAMIC_LOAD}_$index"
                GondolaLoadTestItem(
                    loadPercentage = getItem(cat, "loadPercentage"),
                    loadDetails = getItem(cat, "loadDetails"),
                    remarks = getItem(cat, "remarks"),
                    result = getItem(cat, "result")
                )
            }
        ),
        staticLoadTest = GondolaStaticLoadTest(
            note = getString(ReportCategory.TESTING_STATIC_LOAD, "note"),
            items = getList(ReportCategory.TESTING_STATIC_LOAD) { index, getItem ->
                val cat = "${ReportCategory.TESTING_STATIC_LOAD}_$index"
                GondolaLoadTestItem(
                    loadPercentage = getItem(cat, "loadPercentage"),
                    loadDetails = getItem(cat, "loadDetails"),
                    remarks = getItem(cat, "remarks"),
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
    add(ReportCategory.GENERAL_DATA, "subcontractorPersonInCharge", this.generalData.subcontractorPersonInCharge)
    add(ReportCategory.GENERAL_DATA, "operatorName", this.generalData.operatorName)
    add(ReportCategory.GENERAL_DATA, "operatorCertificate", this.generalData.operatorCertificate)
    add(ReportCategory.TECHNICAL_DATA, "spec_intendedUse", this.generalData.intendedUse)
    
    this.technicalData.let { tech ->
        tech.gondolaSpecification.let {
            add(ReportCategory.TECHNICAL_DATA, "spec_supportMastHeight", it.supportMastHeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_frontBeamLength", it.frontBeamLength.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_middleBeamLength", it.middleBeamLength.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_rearBeamLength", it.rearBeamLength.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_balanceWeightDistance", it.balanceWeightDistance.toString())
            add(ReportCategory.TECHNICAL_DATA, "spec_capacity", it.capacity)
            add(ReportCategory.TECHNICAL_DATA, "spec_speed", it.speed)
            add(ReportCategory.TECHNICAL_DATA, "spec_platformSize", it.platformSize)
            add(ReportCategory.TECHNICAL_DATA, "spec_wireRopeDiameter", it.wireRopeDiameter.toString())
        }
        tech.hoist.let {
            add(ReportCategory.TECHNICAL_DATA, "hoist_model", it.model)
            add(ReportCategory.TECHNICAL_DATA, "hoist_liftingCapacity", it.liftingCapacity.toString())
            add(ReportCategory.TECHNICAL_DATA, "hoist_motor_type", it.electricMotor.type)
            add(ReportCategory.TECHNICAL_DATA, "hoist_motor_power", it.electricMotor.power)
            add(ReportCategory.TECHNICAL_DATA, "hoist_motor_voltage", it.electricMotor.voltage.toString())
            add(ReportCategory.TECHNICAL_DATA, "hoist_motor_voltageHz", it.electricMotor.voltageHz.toString())
        }
        add(ReportCategory.TECHNICAL_DATA, "safetyLockType", tech.safetyLockType)
        tech.brake.let {
            add(ReportCategory.TECHNICAL_DATA, "brake_type", it.type)
            add(ReportCategory.TECHNICAL_DATA, "brake_model", it.model)
            add(ReportCategory.TECHNICAL_DATA, "brake_capacity", it.capacity)
        }
        tech.suspensionMechanical.let {
            add(ReportCategory.TECHNICAL_DATA, "suspension_supportMastHeight", it.supportMastHeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "suspension_frontBeamLength", it.frontBeamLength.toString())
            add(ReportCategory.TECHNICAL_DATA, "suspension_material", it.material)
        }
        tech.machineWeight.let {
            add(ReportCategory.TECHNICAL_DATA, "weight_totalPlatformWeight", it.totalPlatformWeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "weight_suspensionMechanicalWeight", it.suspensionMechanicalWeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "weight_balanceWeight", it.balanceWeight.toString())
            add(ReportCategory.TECHNICAL_DATA, "weight_totalMachineWeight", it.totalMachineWeight)
        }
    }
    
    // Flatten visual inspection
    this.visualInspection.let { visual ->
        visual.suspensionStructure.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_frontBeam", it.frontBeam)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_middleBeam", it.middleBeam)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_rearBeam", it.rearBeam)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_frontBeamSupportMast", it.frontBeamSupportMast)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_lowerFrontBeamSupportMast", it.lowerFrontBeamSupportMast)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_mastAndBeamClamp", it.mastAndBeamClamp)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_couplingSleeve", it.couplingSleeve)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_turnBuckle", it.turnBuckle)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_reinforcementRope", it.reinforcementRope)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_rearSupportMast", it.rearSupportMast)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_balanceWeight", it.balanceWeight)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_frontBeamSupportBase", it.frontBeamSupportBase)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_rearBeamSupportBase", it.rearBeamSupportBase)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_jackBaseJoint", it.jackBaseJoint)
            addResult(ReportCategory.VISUAL_INSPECTION, "suspension_connectionBolts", it.connectionBolts)
        }
        visual.steelWireRope.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "rope_mainWireRope", it.mainWireRope)
            addResult(ReportCategory.VISUAL_INSPECTION, "rope_safetyRope", it.safetyRope)
            addResult(ReportCategory.VISUAL_INSPECTION, "rope_slingFasteners", it.slingFasteners)
        }
        visual.electricalSystem.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_hoistMotor", it.hoistMotor)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_brakeRelease", it.brakeRelease)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_manualRelease", it.manualRelease)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_powerControl", it.powerControl)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_powerCable", it.powerCable)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_handleSwitch", it.handleSwitch)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_upperLimitSwitch", it.upperLimitSwitch)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_limitStopper", it.limitStopper)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_socketFitting", it.socketFitting)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_grounding", it.grounding)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_breakerFuse", it.breakerFuse)
            addResult(ReportCategory.VISUAL_INSPECTION, "electrical_emergencyStop", it.emergencyStop)
        }
        visual.platform.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "platform_hoistMountFrame", it.hoistMountFrame)
            addResult(ReportCategory.VISUAL_INSPECTION, "platform_frame", it.frame)
            addResult(ReportCategory.VISUAL_INSPECTION, "platform_bottomPlate", it.bottomPlate)
            addResult(ReportCategory.VISUAL_INSPECTION, "platform_pinsAndBolts", it.pinsAndBolts)
            addResult(ReportCategory.VISUAL_INSPECTION, "platform_bracket", it.bracket)
            addResult(ReportCategory.VISUAL_INSPECTION, "platform_toeBoard", it.toeBoard)
            addResult(ReportCategory.VISUAL_INSPECTION, "platform_rollerAndGuidePulley", it.rollerAndGuidePulley)
            addResult(ReportCategory.VISUAL_INSPECTION, "platform_namePlate", it.namePlate)
        }
        visual.safetyDevices.let {
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_safetyLock", it.safetyLock)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_rubberBumper", it.rubberBumper)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_safetyLifeLine", it.safetyLifeLine)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_loadLimitSwitch", it.loadLimitSwitch)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_limitBlock", it.limitBlock)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_upperLimitSwitch", it.upperLimitSwitch)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_bodyHarness", it.bodyHarness)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_harnessAnchorage", it.harnessAnchorage)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_communicationTool", it.communicationTool)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_handyTalkie", it.handyTalkie)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_safetyHelmet", it.safetyHelmet)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_handRail", it.handRail)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_otherPpe", it.otherPpe)
            addResult(ReportCategory.VISUAL_INSPECTION, "safety_coupForGlass", it.coupForGlass)
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
            add(cat, "defectFound", item.defectFound.toString())
            add(cat, "result", item.result)
        }
        add(ReportCategory.NDE_SUSPENSION_STRUCTURE, "ndtType", ndt.suspensionStructure.ndtType)
        ndt.suspensionStructure.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.NDE_SUSPENSION_STRUCTURE}_item_$i"
            add(cat, "part", item.part)
            add(cat, "location", item.location)
            add(cat, "defectFound", item.defectFound.toString())
            add(cat, "result", item.result)
        }
        add(ReportCategory.NDE_GONDOLA_CAGE, "ndtType", ndt.gondolaCage.ndtType)
        ndt.gondolaCage.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.NDE_GONDOLA_CAGE}_item_$i"
            add(cat, "part", item.part)
            add(cat, "location", item.location)
            add(cat, "defectFound", item.defectFound.toString())
            add(cat, "result", item.result)
        }
        ndt.clamps.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.NDE_CLAMPS}_item_$i"
            add(cat, "partCheck", item.partCheck)
            add(cat, "location", item.location)
            add(cat, "defectFound", item.defectFound.toString())
            add(cat, "result", item.result)
        }
    }
    
    this.testing.let { testing ->
        add(ReportCategory.TESTING_DYNAMIC_LOAD, "note", testing.dynamicLoadTest.note)
        testing.dynamicLoadTest.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.TESTING_DYNAMIC_LOAD}_$i"
            add(cat, "loadPercentage", item.loadPercentage)
            add(cat, "loadDetails", item.loadDetails)
            add(cat, "remarks", item.remarks)
            add(cat, "result", item.result)
        }
        add(ReportCategory.TESTING_STATIC_LOAD, "note", testing.staticLoadTest.note)
        testing.staticLoadTest.items.forEachIndexed { i, item ->
            val cat = "${ReportCategory.TESTING_STATIC_LOAD}_$i"
            add(cat, "loadPercentage", item.loadPercentage)
            add(cat, "loadDetails", item.loadDetails)
            add(cat, "remarks", item.remarks)
            add(cat, "result", item.result)
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
