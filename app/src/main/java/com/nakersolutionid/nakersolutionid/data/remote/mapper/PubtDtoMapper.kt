package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.local.utils.toDocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.toInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toSubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.pubt.*
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain

// =================================================================================================
//                                  BAP: Domain -> DTO
// =================================================================================================

fun InspectionWithDetailsDomain.toPubtBapRequest(): PubtBapRequest {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }
    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = PubtBapGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        userUsage = this.inspection.usageLocation ?: "",
        userAddress = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = PubtBapTechnicalData(
        brandType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturer = this.inspection.manufacturer?.name ?: "",
        countryAndYearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = this.inspection.serialNumber ?: "",
        fuelType = findTestResult(PubtMappingKeys.BAP.TestName.FUEL_TYPE), //
        operatingPressure = findTestResult(PubtMappingKeys.BAP.TestName.PRESSURE_VESSEL_CONTENT), //
        designPressureKgCm2 = findTestResult(PubtMappingKeys.BAP.TestName.DESIGN_PRESSURE), //
        maxAllowableWorkingPressure = findTestResult(PubtMappingKeys.BAP.TestName.MAX_WORKING_PRESSURE), //
        technicalDataShellMaterial = findTestResult(PubtMappingKeys.BAP.TestName.MATERIAL_TYPE), //
        safetyValveType = findTestResult(PubtMappingKeys.BAP.TestName.SAFETY_VALVE_TYPE), //
        volumeLiters = findTestResult(PubtMappingKeys.BAP.TestName.VOLUME_LITERS) //
    )

    val visualInspection = PubtBapVisualInspection(
        foundationCondition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, PubtMappingKeys.BAP.ItemName.FOUNDATION_CONDITION),
        wheelCondition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, PubtMappingKeys.BAP.ItemName.WHEEL_CONDITION),
        pipeCondition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, PubtMappingKeys.BAP.ItemName.PIPE_CONDITION),
        safetyValveIsInstalled = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_SAFETY_VALVE, PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_INSTALLED),
        safetyValveCondition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_SAFETY_VALVE, PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_CONDITION),
        aparAvailable = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_APAR, PubtMappingKeys.BAP.ItemName.APAR_AVAILABLE),
        aparCondition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_APAR, PubtMappingKeys.BAP.ItemName.APAR_CONDITION)
    )

    val testing = PubtBapTesting(
        ndtTestingFulfilled = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.NDT_FULFILLED),
        thicknessTestingComply = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.THICKNESS_COMPLY),
        pneumaticTestingCondition = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.PNEUMATIC_CONDITION),
        hydroTestingFullFilled = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.HYDRO_TEST_FULFILLED),
        safetyValveTestingCondition = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_TEST_CONDITION)
    )

    return PubtBapRequest(
        laporanId = this.inspection.extraId,
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        extraId = this.inspection.id,
        createdAt = this.inspection.createdAt ?: "",
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        equipmentType = this.inspection.equipmentType,
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        testing = testing
    )
}

// =================================================================================================
//                                  BAP: DTO -> Domain
// =================================================================================================

fun PubtBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.laporanId,
        moreExtraId = this.id,
        documentType = this.documentType.toDocumentType() ?: DocumentType.BAP,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.PUBT,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.General_PUBT,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.userUsage,
        addressUsageLocation = this.generalData.userAddress,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.brandType,
            year = this.technicalData.countryAndYearOfManufacture
        ),
        serialNumber = this.technicalData.serialNumberUnitNumber,
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    this.visualInspection.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, itemName = PubtMappingKeys.BAP.ItemName.FOUNDATION_CONDITION, status = it.foundationCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, itemName = PubtMappingKeys.BAP.ItemName.WHEEL_CONDITION, status = it.wheelCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, itemName = PubtMappingKeys.BAP.ItemName.PIPE_CONDITION, status = it.pipeCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_SAFETY_VALVE, itemName = PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_INSTALLED, status = it.safetyValveIsInstalled))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_SAFETY_VALVE, itemName = PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_CONDITION, status = it.safetyValveCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_APAR, itemName = PubtMappingKeys.BAP.ItemName.APAR_AVAILABLE, status = it.aparAvailable))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_APAR, itemName = PubtMappingKeys.BAP.ItemName.APAR_CONDITION, status = it.aparCondition))
    }
    this.testing.let {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.TESTING, itemName = PubtMappingKeys.BAP.ItemName.NDT_FULFILLED, status = it.ndtTestingFulfilled))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.TESTING, itemName = PubtMappingKeys.BAP.ItemName.THICKNESS_COMPLY, status = it.thicknessTestingComply))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.TESTING, itemName = PubtMappingKeys.BAP.ItemName.PNEUMATIC_CONDITION, status = it.pneumaticTestingCondition))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.TESTING, itemName = PubtMappingKeys.BAP.ItemName.HYDRO_TEST_FULFILLED, status = it.hydroTestingFullFilled))
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.TESTING, itemName = PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_TEST_CONDITION, status = it.safetyValveTestingCondition))
    }

    val testResults = mutableListOf<InspectionTestResultDomain>()
    this.technicalData.let {
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.FUEL_TYPE, result = it.fuelType, notes = PubtMappingKeys.BAP.Category.TECHNICAL_DATA))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.PRESSURE_VESSEL_CONTENT, result = it.operatingPressure, notes = PubtMappingKeys.BAP.Category.TECHNICAL_DATA))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.DESIGN_PRESSURE, result = it.designPressureKgCm2, notes = PubtMappingKeys.BAP.Category.TECHNICAL_DATA))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.MAX_WORKING_PRESSURE, result = it.maxAllowableWorkingPressure, notes = PubtMappingKeys.BAP.Category.TECHNICAL_DATA))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.MATERIAL_TYPE, result = it.technicalDataShellMaterial, notes = PubtMappingKeys.BAP.Category.TECHNICAL_DATA))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.SAFETY_VALVE_TYPE, result = it.safetyValveType, notes = PubtMappingKeys.BAP.Category.TECHNICAL_DATA))
        testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.VOLUME_LITERS, result = it.volumeLiters, notes = PubtMappingKeys.BAP.Category.TECHNICAL_DATA))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = testResults
    )
}


// =================================================================================================
//                                  Report: Domain -> DTO
// =================================================================================================

fun InspectionWithDetailsDomain.toPubtReportRequest(): PubtReportRequest {

    fun getCheckItem(category: String, itemName: String) = this.checkItems.find { it.category == category && it.itemName == itemName }
    fun getCheckItemValue(category: String, itemName: String) = getCheckItem(category, itemName)?.result ?: ""
    fun getCheckResult(category: String, itemName: String): PubtStatusResult {
        val item = getCheckItem(category, itemName)
        return PubtStatusResult(status = item?.status ?: false, result = item?.result ?: "")
    }
    fun getValueAndRemarks(category: String, itemName: String): Pair<String, String> {
        val parts = getCheckItemValue(category, itemName).split(PubtMappingKeys.SEPARATOR)
        return Pair(parts.getOrNull(0) ?: "", parts.getOrNull(1) ?: "")
    }
    fun getNdtJoint(category: String, itemName: String): PubtNdtJoint {
        val item = getCheckItem(category, itemName)
        val parts = (item?.result ?: "").split(PubtMappingKeys.SEPARATOR)
        return PubtNdtJoint(
            status = item?.status ?: false,
            location = parts.getOrNull(0) ?: "",
            result = parts.getOrNull(1) ?: ""
        )
    }
    fun getAppendage(category: String, itemName: String): PubtAppendageItem {
        val item = getCheckItem(category, itemName)
        val parts = (item?.result ?: "").split(PubtMappingKeys.SEPARATOR)
        return PubtAppendageItem(
            status = item?.status ?: false,
            quantity = parts.getOrNull(0) ?: "",
            result = parts.getOrNull(1) ?: ""
        )
    }

    val generalData = PubtGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        userUsage = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.USER),
        userAddress = this.inspection.addressUsageLocation ?: "",
        operatorName = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.OPERATOR_NAME),
        equipmentType = this.inspection.equipmentType,
        manufacturer = this.inspection.manufacturer?.name ?: "",
        brandType = this.inspection.manufacturer?.brandOrType ?: "",
        countryAndYearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = this.inspection.serialNumber ?: "",
        designPressure = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.DESIGN_PRESSURE_KGCM2),
        maxAllowableWorkingPressure = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.MAX_ALLOWABLE_WORKING_PRESSURE_KGCM2),
        capacityWorkingLoad = this.inspection.capacity ?: "",
        steamTemperature = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.STEAM_TEMPERATURE),
        operatingPressure = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.OPERATING_PRESSURE_KGCM2),
        fuelType = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.FUEL_TYPE),
        intendedUse = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.INTENDED_USE),
        permitNumber = this.inspection.permitNumber ?: "",
        operatorCertificate = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.OPERATOR_CERTIFICATE),
        equipmentHistory = getCheckItemValue(PubtMappingKeys.Report.Category.GENERAL_DATA, PubtMappingKeys.Report.ItemName.EQUIPMENT_HISTORY),
        inspectionDate = this.inspection.reportDate ?: ""
    )

    val techCategory = PubtMappingKeys.Report.Category.TECHNICAL_DATA
    val technicalData = PubtTechnicalData(
        shell = PubtShell(
            numberOfRounds = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.SHELL_NUMBER_OF_ROUNDS),
            connectionMethod = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.SHELL_CONNECTION_METHOD),
            material = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.SHELL_MATERIAL),
            pipeDiameter = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.SHELL_PIPE_DIAMETER_MM),
            thickness = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.SHELL_THICKNESS_MM),
            bodyLength = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.SHELL_BODY_LENGTH_MM),
            heads = PubtHeads(
                top = PubtHeadDetail(
                    diameter = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.HEADS_TOP_DIAMETER_MM),
                    thickness = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.HEADS_TOP_THICKNESS_MM)
                ),
                rear = PubtHeadDetail(
                    diameter = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.HEADS_REAR_DIAMETER_MM),
                    thickness = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.HEADS_REAR_THICKNESS_MM)
                )
            ),
            tubePlate = PubtTubePlate(
                front = PubtTubePlateDetail(
                    dim1 = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBE_PLATE_FRONT_DIM1_MM),
                    dim2 = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBE_PLATE_FRONT_DIM2_MM)
                ),
                rear = PubtTubePlateDetail(
                    dim1 = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBE_PLATE_REAR_DIM1_MM),
                    dim2 = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBE_PLATE_REAR_DIM2_MM)
                )
            )
        ),
        furnace = PubtFurnace(
            type = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_TYPE),
            material = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_MATERIAL),
            outerDiameter = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_OUTER_DIAMETER_MM),
            innerDiameter = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_INNER_DIAMETER_MM),
            thickness = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_THICKNESS_MM)
        ),
        waterTubes = PubtWaterTubes(
            firstPass = PubtWaterTubePass(
                diameter = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_FIRST_DIAMETER),
                thickness = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_FIRST_THICKNESS),
                length = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_FIRST_LENGTH),
                quantity = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_FIRST_QUANTITY)
            ),
            secondPass = PubtWaterTubePass(
                diameter = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_SECOND_DIAMETER),
                thickness = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_SECOND_THICKNESS),
                length = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_SECOND_LENGTH),
                quantity = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_SECOND_QUANTITY)
            ),
            stayTube = PubtWaterTubeStayMaterial(
                diameter = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_STAY_DIAMETER),
                thickness = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_STAY_THICKNESS),
                length = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_STAY_LENGTH),
                quantity = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_STAY_QUANTITY)
            ),
            material = PubtWaterTubeStayMaterial(
                diameter = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_DIAMETER),
                thickness = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_THICKNESS),
                length = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_LENGTH),
                quantity = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_QUANTITY)
            )
        ),
        tubePlateSplicing = getCheckItemValue(techCategory, PubtMappingKeys.Report.ItemName.TUBES_CONNECTION_METHOD)
    )

    val visualCategory = PubtMappingKeys.Report.Category.VISUAL_INSPECTION
    val inspectionAndMeasurement = PubtInspectionAndMeasurement(
        visualChecks = PubtVisualChecks(
            steamEquipment = PubtSteamEquipment(
                shellDrum = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_SHELL_DRUM),
                bouileur = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_BOUILEUR),
                furnace = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FURNACE),
                fireChamber = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FIRE_CHAMBER),
                refractory = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_REFRACTORY),
                combustionChamber = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_COMBUSTION_CHAMBER),
                fireTubes = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FIRE_TUBES),
                superHeater = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_SUPER_HEATER),
                reheater = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_REHEATER),
                economizer = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_ECONOMIZER)
            ),
            boilerDetails = PubtBoilerDetails(
                grate = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_GRATE),
                burner = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_BURNER),
                fdf = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_FDF),
                idf = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_IDF),
                airHeater = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_AIR_HEATER),
                airDuct = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_AIR_DUCT),
                gasDuct = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_GAS_DUCT),
                ashCatcher = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_ASH_CATCHER),
                chimney = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_CHIMNEY),
                stairs = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_STAIRS),
                insulation = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_INSULATION)
            ),
            safetyDevices = PubtSafetyDevices(
                safetyValveRing = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_RING),
                safetyValvePipe = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_PIPE),
                safetyValveExhaust = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_EXHAUST),
                pressureGaugeMark = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_MARK),
                pressureGaugeSiphon = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_SIPHON),
                pressureGaugeCock = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_COCK),
                gaugeGlassTryCocks = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.GAUGE_GLASS_TRY_COCKS),
                gaugeGlassBlowdown = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.GAUGE_GLASS_BLOWDOWN),
                waterLevelLowestMark = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.WATER_LEVEL_LOWEST_MARK),
                waterLevelPosition = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.WATER_LEVEL_POSITION),
                feedwaterPump = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.FEEDWATER_PUMP),
                feedwaterCapacity = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.FEEDWATER_CAPACITY),
                feedwaterMotor = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.FEEDWATER_MOTOR),
                feedwaterCheckValve = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.FEEDWATER_CHECK_VALVE),
                controlBlacksFluit = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.CONTROL_BLACKS_FLUIT),
                controlFusiblePlug = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.CONTROL_FUSIBLE_PLUG),
                controlWaterLevel = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.CONTROL_WATER_LEVEL),
                controlSteamPressure = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.CONTROL_STEAM_PRESSURE),
                blowdownDesc = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BLOWDOWN_DESC),
                blowdownMaterial = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.BLOWDOWN_MATERIAL),
                manholeManhole = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.MANHOLE_MANHOLE),
                manholeInspectionHole = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.MANHOLE_INSPECTION_HOLE),
                idMarkNameplate = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.ID_MARK_NAMEPLATE),
                idMarkDataMatch = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.ID_MARK_DATA_MATCH),
                idMarkForm9Stamp = getCheckResult(visualCategory, PubtMappingKeys.Report.ItemName.ID_MARK_FORM_9_STAMP)
            )
        ),
        materialThickness = PubtMaterialThickness(
            bodyShell = PubtBodyShellThickness(
                thickness = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_THICKNESS_MM).first,
                diameter = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_DIAMETER_MM).first,
                thicknessResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_THICKNESS_MM).second,
                diameterResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_DIAMETER_MM).second
            ),
            vaporReceiverHeader = PubtVaporReceiverHeaderThickness(
                thickness = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_THICKNESS_MM).first,
                diameter = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_DIAMETER_MM).first,
                length = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_LENGTH_MM).first,
                thicknessResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_THICKNESS_MM).second,
                diameterResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_DIAMETER_MM).second,
                lengthResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_LENGTH_MM).second
            ),
            fireHallFurnance1 = PubtFurnaceThickness(
                thickness = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_THICKNESS_MM).first,
                diameter = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_DIAMETER_MM).first,
                length = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_LENGTH_MM).first,
                thicknessResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_THICKNESS_MM).second,
                diameterResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_DIAMETER_MM).second,
                lengthResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_LENGTH_MM).second
            ),
            fireHallFurnance2 = PubtFurnaceThickness(
                thickness = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_THICKNESS_MM).first,
                diameter = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_DIAMETER_MM).first,
                length = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_LENGTH_MM).first,
                thicknessResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_THICKNESS_MM).second,
                diameterResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_DIAMETER_MM).second,
                lengthResult = getValueAndRemarks(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_LENGTH_MM).second
            )
        ),
        thicknessMeasurementSetup = PubtThicknessMeasurementSetup(
            owner = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_OWNER),
            inspectionDate = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_INSPECTION_DATE),
            project = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_PROJECT),
            objectType = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_OBJECT_TYPE),
            workOrderNo = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_WORK_ORDER_NO),
            equipmentUsed = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_EQUIPMENT_USED),
            methodUsed = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_METHOD_USED),
            probeType = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_PROBE_TYPE),
            materialType = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_MATERIAL_TYPE),
            probeStyle = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_PROBE_STYLE),
            operatingTemp = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_OPERATING_TEMP),
            surfaceCondition = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_SURFACE_CONDITION),
            weldingProcess = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_WELDING_PROCESS),
            laminatingCheck = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_LAMINATING_CHECK),
            couplantUsed = getCheckItemValue(PubtMappingKeys.Report.Category.THICKNESS_SETUP, PubtMappingKeys.Report.ItemName.SETUP_COUPLANT_USED)
        ),
        measurementResults = PubtMeasurementResults(
            topHead = PubtMeasurementDetail(
                nominal = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_TOP_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_NOMINAL_MM),
                point1 = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_TOP_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_1),
                point2 = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_TOP_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_2),
                point3 = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_TOP_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_3),
                minimum = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_TOP_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_MINIMUM),
                maximum = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_TOP_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_MAXIMUM)
            ),
            shell = PubtMeasurementDetail(
                nominal = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_SHELL, PubtMappingKeys.Report.ItemName.MEASUREMENT_NOMINAL_MM),
                point1 = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_SHELL, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_1),
                point2 = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_SHELL, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_2),
                point3 = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_SHELL, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_3),
                minimum = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_SHELL, PubtMappingKeys.Report.ItemName.MEASUREMENT_MINIMUM),
                maximum = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_SHELL, PubtMappingKeys.Report.ItemName.MEASUREMENT_MAXIMUM)
            ),
            buttonHead = PubtMeasurementDetail(
                nominal = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_BUTTON_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_NOMINAL_MM),
                point1 = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_BUTTON_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_1),
                point2 = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_BUTTON_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_2),
                point3 = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_BUTTON_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_3),
                minimum = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_BUTTON_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_MINIMUM),
                maximum = getCheckItemValue(PubtMappingKeys.Report.Category.MEASUREMENT_BUTTON_HEAD, PubtMappingKeys.Report.ItemName.MEASUREMENT_MAXIMUM)
            )
        ),
        ndt = PubtNdt(
            shell = PubtNdtShell(
                testMethod = getCheckItemValue(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_SHELL_METHOD),
                longitudinalWeldJoint = getNdtJoint(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_SHELL_LONGITUDINAL),
                circumferentialWeldJoint = getNdtJoint(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_SHELL_CIRCUMFERENTIAL)
            ),
            furnace = PubtNdtFurnace(
                testMethod = getCheckItemValue(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FURNACE_METHOD),
                longitudinalWeldJoint = getNdtJoint(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FURNACE_LONGITUDINAL),
                circumferentialWeldJoint = getNdtJoint(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FURNACE_CIRCUMFERENTIAL)
            ),
            fireTubes = PubtNdtFireTubes(
                testMethod = getCheckItemValue(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_METHOD),
                weldJointFront = getNdtJoint(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_FRONT),
                weldJointRear = getNdtJoint(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_REAR)
            )
        ),
        hydrotest = PubtHydrotest(
            testPressure = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_PRESSURE_KGCM2),
            mawp = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_MAWP_KGCM2),
            testMedium = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_MEDIUM),
            testDate = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_DATE),
            testResult = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_RESULT)
        ),
        appendagesCheck = PubtAppendagesCheck(
            workingPressureGauge = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_PRESSURE_GAUGE),
            manHole = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_MANHOLE),
            safetyValveFullOpen = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_SAFETY_VALVE),
            mainSteamValve = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_MAIN_STEAM_VALVE),
            levelGlassIndicator = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_LEVEL_GLASS_INDICATOR),
            blowdownValve = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_BLOWDOWN_VALVE),
            feedwaterStopValve = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_FEEDWATER_STOP_VALVE),
            feedwaterInletValve = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_FEEDWATER_INLET_VALVE),
            steamDrier = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_STEAM_DRIER),
            waterPump = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_WATER_PUMP),
            controlPanel = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_CONTROL_PANEL),
            nameplate = getAppendage(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_NAMEPLATE)
        ),
        safetyValveTest = PubtSafetyValveTest(
            header = getCheckItemValue(PubtMappingKeys.Report.Category.SAFETY_VALVE_TEST, PubtMappingKeys.Report.ItemName.VALVE_TEST_HEADER),
            startsToOpen = getCheckItemValue(PubtMappingKeys.Report.Category.SAFETY_VALVE_TEST, PubtMappingKeys.Report.ItemName.VALVE_TEST_STARTS_TO_OPEN_KGCM2),
            valveInfo = getCheckItemValue(PubtMappingKeys.Report.Category.SAFETY_VALVE_TEST, PubtMappingKeys.Report.ItemName.VALVE_TEST_VALVE_INFO)
        )
    )

    val conclusion = this.findings.filter { it.type == FindingType.FINDING }.joinToString("\n") { it.description }
    val recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.joinToString("\n") { it.description }

    return PubtReportRequest(
        examinationType = this.inspection.examinationType,
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        createdAt = this.inspection.createdAt ?: "",
        extraId = this.inspection.id,
        generalData = generalData,
        technicalData = technicalData,
        inspectionAndMeasurement = inspectionAndMeasurement,
        conclusion = conclusion,
        recommendations = recommendations
    )
}

// =================================================================================================
//                                  Report: DTO -> Domain
// =================================================================================================

fun PubtReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id,
        moreExtraId = "",
        documentType = this.documentType.toDocumentType() ?: DocumentType.LAPORAN,
        inspectionType = this.inspectionType.toInspectionType() ?: InspectionType.PUBT,
        subInspectionType = this.subInspectionType.toSubInspectionType() ?: SubInspectionType.General_PUBT,
        equipmentType = this.generalData.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.userUsage,
        addressUsageLocation = this.generalData.userAddress,
        serialNumber = this.generalData.serialNumberUnitNumber,
        permitNumber = this.generalData.permitNumber,
        capacity = this.generalData.capacityWorkingLoad,
        manufacturer = ManufacturerDomain(
            name = this.generalData.manufacturer,
            brandOrType = this.generalData.brandType,
            year = this.generalData.countryAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.generalData.inspectionDate,
        isSynced = true,
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun addCheckItem(category: String, itemName: String, value: String?) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = false, result = value ?: ""))
    }
    fun addCheckResultItem(category: String, itemName: String, value: PubtStatusResult) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = value.status, result = value.result))
    }
    fun addValueResultItem(category: String, itemName: String, value: String, remarks: String) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = false, result = "$value${PubtMappingKeys.SEPARATOR}$remarks"))
    }
    fun addNdtResultItem(category: String, itemName: String, value: PubtNdtJoint) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = value.status, result = "${value.location}${PubtMappingKeys.SEPARATOR}${value.result}"))
    }
    fun addAppendageResultItem(category: String, itemName: String, value: PubtAppendageItem) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = value.status, result = "${value.quantity}${PubtMappingKeys.SEPARATOR}${value.result}"))
    }

    val generalCat = PubtMappingKeys.Report.Category.GENERAL_DATA
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.USER, this.generalData.userUsage)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.OPERATOR_NAME, this.generalData.operatorName)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.DESIGN_PRESSURE_KGCM2, this.generalData.designPressure)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.MAX_ALLOWABLE_WORKING_PRESSURE_KGCM2, this.generalData.maxAllowableWorkingPressure)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.STEAM_TEMPERATURE, this.generalData.steamTemperature)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.OPERATING_PRESSURE_KGCM2, this.generalData.operatingPressure)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.FUEL_TYPE, this.generalData.fuelType)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.INTENDED_USE, this.generalData.intendedUse)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.OPERATOR_CERTIFICATE, this.generalData.operatorCertificate)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.EQUIPMENT_HISTORY, this.generalData.equipmentHistory)

    val techCategory = PubtMappingKeys.Report.Category.TECHNICAL_DATA
    this.technicalData.shell.let {
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.SHELL_NUMBER_OF_ROUNDS, it.numberOfRounds)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.SHELL_CONNECTION_METHOD, it.connectionMethod)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.SHELL_MATERIAL, it.material)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.SHELL_PIPE_DIAMETER_MM, it.pipeDiameter)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.SHELL_THICKNESS_MM, it.thickness)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.SHELL_BODY_LENGTH_MM, it.bodyLength)
        it.heads.top.let { h ->
            addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.HEADS_TOP_DIAMETER_MM, h.diameter)
            addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.HEADS_TOP_THICKNESS_MM, h.thickness)
        }
        it.heads.rear.let { h ->
            addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.HEADS_REAR_DIAMETER_MM, h.diameter)
            addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.HEADS_REAR_THICKNESS_MM, h.thickness)
        }
        it.tubePlate.front.let { p ->
            addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBE_PLATE_FRONT_DIM1_MM, p.dim1)
            addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBE_PLATE_FRONT_DIM2_MM, p.dim2)
        }
        it.tubePlate.rear.let { p ->
            addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBE_PLATE_REAR_DIM1_MM, p.dim1)
            addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBE_PLATE_REAR_DIM2_MM, p.dim2)
        }
    }
    this.technicalData.furnace.let {
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_TYPE, it.type)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_MATERIAL, it.material)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_OUTER_DIAMETER_MM, it.outerDiameter)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_INNER_DIAMETER_MM, it.innerDiameter)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.FURNACE_THICKNESS_MM, it.thickness)
    }
    this.technicalData.waterTubes.let {
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_FIRST_DIAMETER, it.firstPass.diameter)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_FIRST_THICKNESS, it.firstPass.thickness)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_FIRST_LENGTH, it.firstPass.length)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_FIRST_QUANTITY, it.firstPass.quantity)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_SECOND_DIAMETER, it.secondPass.diameter)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_SECOND_THICKNESS, it.secondPass.thickness)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_SECOND_LENGTH, it.secondPass.length)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_SECOND_QUANTITY, it.secondPass.quantity)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_STAY_DIAMETER, it.stayTube.diameter)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_STAY_THICKNESS, it.stayTube.thickness)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_STAY_LENGTH, it.stayTube.length)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_STAY_QUANTITY, it.stayTube.quantity)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_DIAMETER, it.material.diameter)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_THICKNESS, it.material.thickness)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_LENGTH, it.material.length)
        addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_QUANTITY, it.material.quantity)
    }
    addCheckItem(techCategory, PubtMappingKeys.Report.ItemName.TUBES_CONNECTION_METHOD, this.technicalData.tubePlateSplicing)


    val visualCategory = PubtMappingKeys.Report.Category.VISUAL_INSPECTION
    this.inspectionAndMeasurement.visualChecks.let { v ->
        v.steamEquipment.let {
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_SHELL_DRUM, it.shellDrum)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_BOUILEUR, it.bouileur)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FURNACE, it.furnace)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FIRE_CHAMBER, it.fireChamber)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_REFRACTORY, it.refractory)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_COMBUSTION_CHAMBER, it.combustionChamber)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FIRE_TUBES, it.fireTubes)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_SUPER_HEATER, it.superHeater)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_REHEATER, it.reheater)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_ECONOMIZER, it.economizer)
        }
        v.boilerDetails.let {
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_GRATE, it.grate)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_BURNER, it.burner)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_FDF, it.fdf)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_IDF, it.idf)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_AIR_HEATER, it.airHeater)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_AIR_DUCT, it.airDuct)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_GAS_DUCT, it.gasDuct)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_ASH_CATCHER, it.ashCatcher)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_CHIMNEY, it.chimney)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_STAIRS, it.stairs)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_INSULATION, it.insulation)
        }
        v.safetyDevices.let {
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_RING, it.safetyValveRing)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_PIPE, it.safetyValvePipe)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_EXHAUST, it.safetyValveExhaust)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_MARK, it.pressureGaugeMark)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_SIPHON, it.pressureGaugeSiphon)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_COCK, it.pressureGaugeCock)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.GAUGE_GLASS_TRY_COCKS, it.gaugeGlassTryCocks)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.GAUGE_GLASS_BLOWDOWN, it.gaugeGlassBlowdown)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.WATER_LEVEL_LOWEST_MARK, it.waterLevelLowestMark)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.WATER_LEVEL_POSITION, it.waterLevelPosition)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.FEEDWATER_PUMP, it.feedwaterPump)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.FEEDWATER_CAPACITY, it.feedwaterCapacity)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.FEEDWATER_MOTOR, it.feedwaterMotor)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.FEEDWATER_CHECK_VALVE, it.feedwaterCheckValve)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.CONTROL_BLACKS_FLUIT, it.controlBlacksFluit)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.CONTROL_FUSIBLE_PLUG, it.controlFusiblePlug)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.CONTROL_WATER_LEVEL, it.controlWaterLevel)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.CONTROL_STEAM_PRESSURE, it.controlSteamPressure)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BLOWDOWN_DESC, it.blowdownDesc)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.BLOWDOWN_MATERIAL, it.blowdownMaterial)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.MANHOLE_MANHOLE, it.manholeManhole)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.MANHOLE_INSPECTION_HOLE, it.manholeInspectionHole)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.ID_MARK_NAMEPLATE, it.idMarkNameplate)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.ID_MARK_DATA_MATCH, it.idMarkDataMatch)
            addCheckResultItem(visualCategory, PubtMappingKeys.Report.ItemName.ID_MARK_FORM_9_STAMP, it.idMarkForm9Stamp)
        }
    }

    val thicknessCategory = PubtMappingKeys.Report.Category.MATERIAL_THICKNESS
    this.inspectionAndMeasurement.materialThickness.let {
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_THICKNESS_MM, it.bodyShell.thickness, it.bodyShell.thicknessResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_DIAMETER_MM, it.bodyShell.diameter, it.bodyShell.diameterResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_DIAMETER_MM, it.vaporReceiverHeader.diameter, it.vaporReceiverHeader.diameterResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_THICKNESS_MM, it.vaporReceiverHeader.thickness, it.vaporReceiverHeader.thicknessResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_LENGTH_MM, it.vaporReceiverHeader.length, it.vaporReceiverHeader.lengthResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_DIAMETER_MM, it.fireHallFurnance1.diameter, it.fireHallFurnance1.diameterResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_THICKNESS_MM, it.fireHallFurnance1.thickness, it.fireHallFurnance1.thicknessResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_LENGTH_MM, it.fireHallFurnance1.length, it.fireHallFurnance1.lengthResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_DIAMETER_MM, it.fireHallFurnance2.diameter, it.fireHallFurnance2.diameterResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_THICKNESS_MM, it.fireHallFurnance2.thickness, it.fireHallFurnance2.thicknessResult)
        addValueResultItem(thicknessCategory, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_LENGTH_MM, it.fireHallFurnance2.length, it.fireHallFurnance2.lengthResult)
    }

    val setupCategory = PubtMappingKeys.Report.Category.THICKNESS_SETUP
    this.inspectionAndMeasurement.thicknessMeasurementSetup.let {
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_OWNER, it.owner)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_INSPECTION_DATE, it.inspectionDate)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_PROJECT, it.project)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_OBJECT_TYPE, it.objectType)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_WORK_ORDER_NO, it.workOrderNo)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_EQUIPMENT_USED, it.equipmentUsed)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_METHOD_USED, it.methodUsed)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_PROBE_TYPE, it.probeType)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_MATERIAL_TYPE, it.materialType)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_PROBE_STYLE, it.probeStyle)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_OPERATING_TEMP, it.operatingTemp)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_SURFACE_CONDITION, it.surfaceCondition)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_WELDING_PROCESS, it.weldingProcess)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_LAMINATING_CHECK, it.laminatingCheck)
        addCheckItem(setupCategory, PubtMappingKeys.Report.ItemName.SETUP_COUPLANT_USED, it.couplantUsed)
    }

    fun addMeasurementResultItem(category: String, item: PubtMeasurementDetail) {
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_NOMINAL_MM, item.nominal)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_1, item.point1)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_2, item.point2)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_3, item.point3)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_MINIMUM, item.minimum)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_MAXIMUM, item.maximum)
    }
    addMeasurementResultItem(PubtMappingKeys.Report.Category.MEASUREMENT_TOP_HEAD, this.inspectionAndMeasurement.measurementResults.topHead)
    addMeasurementResultItem(PubtMappingKeys.Report.Category.MEASUREMENT_SHELL, this.inspectionAndMeasurement.measurementResults.shell)
    addMeasurementResultItem(PubtMappingKeys.Report.Category.MEASUREMENT_BUTTON_HEAD, this.inspectionAndMeasurement.measurementResults.buttonHead)

    val ndtCategory = PubtMappingKeys.Report.Category.NDT_TESTS
    this.inspectionAndMeasurement.ndt.shell.let {
        addCheckItem(ndtCategory, PubtMappingKeys.Report.ItemName.NDT_SHELL_METHOD, it.testMethod)
        addNdtResultItem(ndtCategory, PubtMappingKeys.Report.ItemName.NDT_SHELL_LONGITUDINAL, it.longitudinalWeldJoint)
        addNdtResultItem(ndtCategory, PubtMappingKeys.Report.ItemName.NDT_SHELL_CIRCUMFERENTIAL, it.circumferentialWeldJoint)
    }
    this.inspectionAndMeasurement.ndt.furnace.let {
        addCheckItem(ndtCategory, PubtMappingKeys.Report.ItemName.NDT_FURNACE_METHOD, it.testMethod)
        addNdtResultItem(ndtCategory, PubtMappingKeys.Report.ItemName.NDT_FURNACE_LONGITUDINAL, it.longitudinalWeldJoint)
        addNdtResultItem(ndtCategory, PubtMappingKeys.Report.ItemName.NDT_FURNACE_CIRCUMFERENTIAL, it.circumferentialWeldJoint)
    }
    this.inspectionAndMeasurement.ndt.fireTubes.let {
        addCheckItem(ndtCategory, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_METHOD, it.testMethod)
        addNdtResultItem(ndtCategory, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_FRONT, it.weldJointFront)
        addNdtResultItem(ndtCategory, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_REAR, it.weldJointRear)
    }

    val hydroCategory = PubtMappingKeys.Report.Category.HYDROTEST
    this.inspectionAndMeasurement.hydrotest.let {
        addCheckItem(hydroCategory, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_PRESSURE_KGCM2, it.testPressure)
        addCheckItem(hydroCategory, PubtMappingKeys.Report.ItemName.HYDROTEST_MAWP_KGCM2, it.mawp)
        addCheckItem(hydroCategory, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_MEDIUM, it.testMedium)
        addCheckItem(hydroCategory, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_DATE, it.testDate)
        addCheckItem(hydroCategory, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_RESULT, it.testResult)
    }

    val appendageCategory = PubtMappingKeys.Report.Category.APPENDAGES
    this.inspectionAndMeasurement.appendagesCheck.let {
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_PRESSURE_GAUGE, it.workingPressureGauge)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_MANHOLE, it.manHole)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_SAFETY_VALVE, it.safetyValveFullOpen)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_MAIN_STEAM_VALVE, it.mainSteamValve)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_LEVEL_GLASS_INDICATOR, it.levelGlassIndicator)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_BLOWDOWN_VALVE, it.blowdownValve)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_FEEDWATER_STOP_VALVE, it.feedwaterStopValve)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_FEEDWATER_INLET_VALVE, it.feedwaterInletValve)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_STEAM_DRIER, it.steamDrier)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_WATER_PUMP, it.waterPump)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_CONTROL_PANEL, it.controlPanel)
        addAppendageResultItem(appendageCategory, PubtMappingKeys.Report.ItemName.APPENDAGE_NAMEPLATE, it.nameplate)
    }

    val valveCategory = PubtMappingKeys.Report.Category.SAFETY_VALVE_TEST
    this.inspectionAndMeasurement.safetyValveTest.let {
        addCheckItem(valveCategory, PubtMappingKeys.Report.ItemName.VALVE_TEST_HEADER, it.header)
        addCheckItem(valveCategory, PubtMappingKeys.Report.ItemName.VALVE_TEST_STARTS_TO_OPEN_KGCM2, it.startsToOpen)
        addCheckItem(valveCategory, PubtMappingKeys.Report.ItemName.VALVE_TEST_VALVE_INFO, it.valveInfo)
    }

    val findings = mutableListOf<InspectionFindingDomain>()
    this.conclusion.split("\n").forEach {
        if(it.isNotBlank()) findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING))
    }
    this.recommendations.split("\n").forEach {
        if(it.isNotBlank()) findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList() // Laporan PUBT tidak menggunakan testResults
    )
}