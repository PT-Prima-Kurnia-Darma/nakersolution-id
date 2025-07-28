package com.nakersolutionid.nakersolutionid.features.report.pubt.general

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.mapper.PubtMappingKeys
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import kotlinx.collections.immutable.toImmutableList

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun GeneralUiState.toInspectionWithDetailsDomain(
    currentTime: String,
    isEdited: Boolean,
    id: Long? = null
): InspectionWithDetailsDomain {
    val report = this.inspectionReport
    val generalData = report.generalData
    val technicalData = report.technicalData
    val inspection = report.inspectionAndMeasurement
    val conclusion = report.conclusion

    val inspectionId = id ?: 0L

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = report.extraId,
        moreExtraId = report.moreExtraId,
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.PUBT,
        subInspectionType = SubInspectionType.General_PUBT,
        equipmentType = report.equipmentType,
        examinationType = report.examinationType,
        ownerName = generalData.ownerName,
        ownerAddress = generalData.ownerAddress,
        usageLocation = generalData.user,
        addressUsageLocation = generalData.userAddress,
        driveType = generalData.driveType,
        serialNumber = generalData.serialNumber,
        permitNumber = generalData.permitNumber,
        capacity = generalData.capacityKgH,
        manufacturer = ManufacturerDomain(
            name = generalData.manufacturer,
            brandOrType = generalData.brandModelType,
            year = generalData.countryAndYearOfManufacture
        ),
        createdAt = currentTime,
        reportDate = generalData.inspectionDate,
        isSynced = false,
        isEdited = isEdited
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun addCheckItem(category: String, itemName: String, value: String?) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = false, result = value ?: ""))
    }
    fun addCheckResultItem(category: String, itemName: String, value: GeneralCheckResult) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = value.isMet, result = value.remarks))
    }
    fun addValueResultItem(category: String, itemName: String, value: GeneralValueResult) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = false, result = "${value.value}${PubtMappingKeys.SEPARATOR}${value.remarks}"))
    }
    fun addNdtResultItem(category: String, itemName: String, value: GeneralNdtResult) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = value.isGood, result = "${value.location}${PubtMappingKeys.SEPARATOR}${value.remarks}"))
    }
    fun addAppendageResultItem(category: String, itemName: String, value: GeneralAppendageResult) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = value.isGood, result = "${value.quantity}${PubtMappingKeys.SEPARATOR}${value.remarks}"))
    }

    val generalCat = PubtMappingKeys.Report.Category.GENERAL_DATA
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.USER, generalData.user)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.OPERATOR_NAME, generalData.operatorName)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.DESIGN_PRESSURE_KGCM2, generalData.designPressureKgCm2)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.MAX_ALLOWABLE_WORKING_PRESSURE_KGCM2, generalData.maxAllowableWorkingPressureKgCm2)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.STEAM_TEMPERATURE, generalData.steamTemperature)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.OPERATING_PRESSURE_KGCM2, generalData.operatingPressureKgCm2)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.FUEL_TYPE, generalData.fuelType)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.INTENDED_USE, generalData.intendedUse)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.OPERATOR_CERTIFICATE, generalData.operatorCertificate)
    addCheckItem(generalCat, PubtMappingKeys.Report.ItemName.EQUIPMENT_HISTORY, generalData.equipmentHistory)

    val techCat = PubtMappingKeys.Report.Category.TECHNICAL_DATA
    technicalData.shell.let {
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.SHELL_NUMBER_OF_ROUNDS, it.numberOfRounds)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.SHELL_CONNECTION_METHOD, it.connectionMethod)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.SHELL_MATERIAL, it.material)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.SHELL_PIPE_DIAMETER_MM, it.pipeDiameterMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.SHELL_THICKNESS_MM, it.thicknessMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.SHELL_BODY_LENGTH_MM, it.bodyLengthMm)
    }
    technicalData.heads.let {
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.HEADS_TYPE, it.type)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.HEADS_TOP_DIAMETER_MM, it.topDiameterMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.HEADS_TOP_THICKNESS_MM, it.topThicknessMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.HEADS_REAR_DIAMETER_MM, it.rearDiameterMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.HEADS_REAR_THICKNESS_MM, it.rearThicknessMm)
    }
    technicalData.tubePlate.let {
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBE_PLATE_FRONT_DIM1_MM, it.frontDim1Mm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBE_PLATE_FRONT_DIM2_MM, it.frontDim2Mm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBE_PLATE_REAR_DIM1_MM, it.rearDim1Mm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBE_PLATE_REAR_DIM2_MM, it.rearDim2Mm)
    }
    technicalData.furnace.let {
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.FURNACE_TYPE, it.type)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.FURNACE_MATERIAL, it.material)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.FURNACE_OUTER_DIAMETER_MM, it.outerDiameterMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.FURNACE_INNER_DIAMETER_MM, it.innerDiameterMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.FURNACE_THICKNESS_MM, it.thicknessMm)
    }
    technicalData.waterTubes.let {
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_FIRST_DIAMETER, it.firstPass.diameterMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_FIRST_THICKNESS, it.firstPass.thicknessMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_FIRST_LENGTH, it.firstPass.lengthMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_FIRST_QUANTITY, it.firstPass.quantity)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_SECOND_DIAMETER, it.secondPass.diameterMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_SECOND_THICKNESS, it.secondPass.thicknessMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_SECOND_LENGTH, it.secondPass.lengthMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_SECOND_QUANTITY, it.secondPass.quantity)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_STAY_DIAMETER, it.stayTube.diameterMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_STAY_THICKNESS, it.stayTube.thicknessMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_STAY_LENGTH, it.stayTube.lengthMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_STAY_QUANTITY, it.stayTube.quantity)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_DIAMETER, it.material.diameterMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_THICKNESS, it.material.thicknessMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_LENGTH, it.material.lengthMm)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_QUANTITY, it.material.quantity)
        addCheckItem(techCat, PubtMappingKeys.Report.ItemName.TUBES_CONNECTION_METHOD, it.connectionMethod)
    }

    val visualCat = PubtMappingKeys.Report.Category.VISUAL_INSPECTION
    inspection.visualInspection.let { v ->
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_SHELL_DRUM, v.steamEquipmentShellDrum)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_BOUILEUR, v.steamEquipmentBouileur)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FURNACE, v.steamEquipmentFurnace)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FIRE_CHAMBER, v.steamEquipmentFireChamber)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_REFRACTORY, v.steamEquipmentRefractory)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_COMBUSTION_CHAMBER, v.steamEquipmentCombustionChamber)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FIRE_TUBES, v.steamEquipmentFireTubes)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_SUPER_HEATER, v.steamEquipmentSuperHeater)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_REHEATER, v.steamEquipmentReheater)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_ECONOMIZER, v.steamEquipmentEconomizer)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_GRATE, v.boilerDetailsGrate)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_BURNER, v.boilerDetailsBurner)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_FDF, v.boilerDetailsFdf)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_IDF, v.boilerDetailsIdf)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_AIR_HEATER, v.boilerDetailsAirHeater)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_AIR_DUCT, v.boilerDetailsAirDuct)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_GAS_DUCT, v.boilerDetailsGasDuct)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_ASH_CATCHER, v.boilerDetailsAshCatcher)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_CHIMNEY, v.boilerDetailsChimney)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_STAIRS, v.boilerDetailsStairs)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_INSULATION, v.boilerDetailsInsulation)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_RING, v.safetyValveRing)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_PIPE, v.safetyValvePipe)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_EXHAUST, v.safetyValveExhaust)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_MARK, v.pressureGaugeMark)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_SIPHON, v.pressureGaugeSiphon)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_COCK, v.pressureGaugeCock)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.GAUGE_GLASS_TRY_COCKS, v.gaugeGlassTryCocks)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.GAUGE_GLASS_BLOWDOWN, v.gaugeGlassBlowdown)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.WATER_LEVEL_LOWEST_MARK, v.waterLevelLowestMark)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.WATER_LEVEL_POSITION, v.waterLevelPosition)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.FEEDWATER_PUMP, v.feedwaterPump)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.FEEDWATER_CAPACITY, v.feedwaterCapacity)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.FEEDWATER_MOTOR, v.feedwaterMotor)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.FEEDWATER_CHECK_VALVE, v.feedwaterCheckValve)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.CONTROL_BLACKS_FLUIT, v.controlBlacksFluit)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.CONTROL_FUSIBLE_PLUG, v.controlFusiblePlug)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.CONTROL_WATER_LEVEL, v.controlWaterLevel)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.CONTROL_STEAM_PRESSURE, v.controlSteamPressure)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BLOWDOWN_DESC, v.blowdownDesc)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.BLOWDOWN_MATERIAL, v.blowdownMaterial)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.MANHOLE_MANHOLE, v.manholeManhole)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.MANHOLE_INSPECTION_HOLE, v.manholeInspectionHole)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.ID_MARK_NAMEPLATE, v.idMarkNameplate)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.ID_MARK_DATA_MATCH, v.idMarkDataMatch)
        addCheckResultItem(visualCat, PubtMappingKeys.Report.ItemName.ID_MARK_FORM_9_STAMP, v.idMarkForm9Stamp)
    }

    val thicknessCat = PubtMappingKeys.Report.Category.MATERIAL_THICKNESS
    inspection.materialThickness.let { t ->
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_THICKNESS_MM, t.shellThicknessMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_DIAMETER_MM, t.shellDiameterMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_DIAMETER_MM, t.headerDiameterMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_THICKNESS_MM, t.headerThicknessMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_LENGTH_MM, t.headerLengthMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_DIAMETER_MM, t.furnace1DiameterMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_THICKNESS_MM, t.furnace1ThicknessMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_LENGTH_MM, t.furnace1LengthMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_DIAMETER_MM, t.furnace2DiameterMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_THICKNESS_MM, t.furnace2ThicknessMm)
        addValueResultItem(thicknessCat, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_LENGTH_MM, t.furnace2LengthMm)
    }

    val setupCat = PubtMappingKeys.Report.Category.THICKNESS_SETUP
    inspection.thicknessMeasurementSetup.let { s ->
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_OWNER, s.owner)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_INSPECTION_DATE, s.inspectionDate)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_PROJECT, s.project)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_OBJECT_TYPE, s.objectType)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_WORK_ORDER_NO, s.workOrderNo)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_EQUIPMENT_USED, s.equipmentUsed)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_METHOD_USED, s.methodUsed)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_PROBE_TYPE, s.probeType)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_MATERIAL_TYPE, s.materialType)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_PROBE_STYLE, s.probeStyle)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_OPERATING_TEMP, s.operatingTemp)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_SURFACE_CONDITION, s.surfaceCondition)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_WELDING_PROCESS, s.weldingProcess)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_LAMINATING_CHECK, s.laminatingCheck)
        addCheckItem(setupCat, PubtMappingKeys.Report.ItemName.SETUP_COUPLANT_USED, s.couplantUsed)
    }

    fun addMeasurementResultItem(category: String, item: GeneralMeasurementResultItem) {
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_NOMINAL_MM, item.nominalMm)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_1, item.point1)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_2, item.point2)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_3, item.point3)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_MINIMUM, item.minimum)
        addCheckItem(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_MAXIMUM, item.maximum)
    }
    addMeasurementResultItem(PubtMappingKeys.Report.Category.MEASUREMENT_TOP_HEAD, inspection.measurementResultsTopHead)
    addMeasurementResultItem(PubtMappingKeys.Report.Category.MEASUREMENT_SHELL, inspection.measurementResultsShell)
    addMeasurementResultItem(PubtMappingKeys.Report.Category.MEASUREMENT_BUTTON_HEAD, inspection.measurementResultsButtonHead)

    val ndtCat = PubtMappingKeys.Report.Category.NDT_TESTS
    inspection.ndtTests.shell.let {
        addCheckItem(ndtCat, PubtMappingKeys.Report.ItemName.NDT_SHELL_METHOD, it.method)
        addNdtResultItem(ndtCat, PubtMappingKeys.Report.ItemName.NDT_SHELL_LONGITUDINAL, it.longitudinalWeldJoint)
        addNdtResultItem(ndtCat, PubtMappingKeys.Report.ItemName.NDT_SHELL_CIRCUMFERENTIAL, it.circumferentialWeldJoint)
    }
    inspection.ndtTests.furnace.let {
        addCheckItem(ndtCat, PubtMappingKeys.Report.ItemName.NDT_FURNACE_METHOD, it.method)
        addNdtResultItem(ndtCat, PubtMappingKeys.Report.ItemName.NDT_FURNACE_LONGITUDINAL, it.longitudinalWeldJoint)
        addNdtResultItem(ndtCat, PubtMappingKeys.Report.ItemName.NDT_FURNACE_CIRCUMFERENTIAL, it.circumferentialWeldJoint)
    }
    inspection.ndtTests.fireTubes.let {
        addCheckItem(ndtCat, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_METHOD, it.method)
        addNdtResultItem(ndtCat, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_FRONT, it.weldJointFront)
        addNdtResultItem(ndtCat, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_REAR, it.weldJointRear)
    }

    val hydroCat = PubtMappingKeys.Report.Category.HYDROTEST
    inspection.hydrotest.let {
        addCheckItem(hydroCat, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_PRESSURE_KGCM2, it.testPressureKgCm2)
        addCheckItem(hydroCat, PubtMappingKeys.Report.ItemName.HYDROTEST_MAWP_KGCM2, it.mawpKgCm2)
        addCheckItem(hydroCat, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_MEDIUM, it.testMedium)
        addCheckItem(hydroCat, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_DATE, it.testDate)
        addCheckItem(hydroCat, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_RESULT, it.testResult)
    }

    val appendageCat = PubtMappingKeys.Report.Category.APPENDAGES
    inspection.appendagesInspection.let { app ->
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_PRESSURE_GAUGE, app.pressureGauge)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_MANHOLE, app.manHole)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_SAFETY_VALVE, app.safetyValve)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_MAIN_STEAM_VALVE, app.mainSteamValve)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_LEVEL_GLASS_INDICATOR, app.levelGlassIndicator)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_BLOWDOWN_VALVE, app.blowdownValve)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_FEEDWATER_STOP_VALVE, app.feedwaterStopValve)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_FEEDWATER_INLET_VALVE, app.feedwaterInletValve)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_STEAM_DRIER, app.steamDrier)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_WATER_PUMP, app.waterPump)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_CONTROL_PANEL, app.controlPanel)
        addAppendageResultItem(appendageCat, PubtMappingKeys.Report.ItemName.APPENDAGE_NAMEPLATE, app.nameplate)
    }

    val valveCat = PubtMappingKeys.Report.Category.SAFETY_VALVE_TEST
    inspection.safetyValveTest.let {
        addCheckItem(valveCat, PubtMappingKeys.Report.ItemName.VALVE_TEST_HEADER, it.header)
        addCheckItem(valveCat, PubtMappingKeys.Report.ItemName.VALVE_TEST_STARTS_TO_OPEN_KGCM2, it.startsToOpenKgCm2)
        addCheckItem(valveCat, PubtMappingKeys.Report.ItemName.VALVE_TEST_VALVE_INFO, it.valveInfo)
    }

    val findings = mutableListOf<InspectionFindingDomain>()
    conclusion.summary.forEach {
        if (it.isNotBlank()) findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING))
    }
    conclusion.recommendations.forEach {
        if (it.isNotBlank()) findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList()
    )
}

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toGeneralUiState(): GeneralUiState {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getCheckItemValue(category: String, itemName: String): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }
    fun getCheckResult(category: String, itemName: String): GeneralCheckResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return GeneralCheckResult(isMet = item?.status ?: false, remarks = item?.result ?: "")
    }
    fun getValueResult(category: String, itemName: String): GeneralValueResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        val parts = (item?.result ?: "").split(PubtMappingKeys.SEPARATOR)
        return GeneralValueResult(value = parts.getOrNull(0) ?: "", remarks = parts.getOrNull(1) ?: "")
    }
    fun getNdtResult(category: String, itemName: String): GeneralNdtResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        val parts = (item?.result ?: "").split(PubtMappingKeys.SEPARATOR)
        return GeneralNdtResult(isGood = item?.status ?: false, location = parts.getOrNull(0) ?: "", remarks = parts.getOrNull(1) ?: "")
    }
    fun getAppendageResult(category: String, itemName: String): GeneralAppendageResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        val parts = (item?.result ?: "").split(PubtMappingKeys.SEPARATOR)
        return GeneralAppendageResult(isGood = item?.status ?: false, quantity = parts.getOrNull(0) ?: "", remarks = parts.getOrNull(1) ?: "")
    }
    fun getMeasurementResultItem(category: String): GeneralMeasurementResultItem {
        return GeneralMeasurementResultItem(
            nominalMm = getCheckItemValue(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_NOMINAL_MM),
            point1 = getCheckItemValue(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_1),
            point2 = getCheckItemValue(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_2),
            point3 = getCheckItemValue(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_POINT_3),
            minimum = getCheckItemValue(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_MINIMUM),
            maximum = getCheckItemValue(category, PubtMappingKeys.Report.ItemName.MEASUREMENT_MAXIMUM)
        )
    }

    val generalCat = PubtMappingKeys.Report.Category.GENERAL_DATA
    val generalData = GeneralData(
        ownerName = inspection.ownerName ?: "",
        ownerAddress = inspection.ownerAddress ?: "",
        userAddress = inspection.addressUsageLocation ?: "",
        driveType = inspection.driveType ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        brandModelType = inspection.manufacturer?.brandOrType ?: "",
        countryAndYearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumber = inspection.serialNumber ?: "",
        permitNumber = inspection.permitNumber ?: "",
        capacityKgH = inspection.capacity ?: "",
        inspectionDate = inspection.reportDate ?: "",
        user = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.USER),
        operatorName = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.OPERATOR_NAME),
        designPressureKgCm2 = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.DESIGN_PRESSURE_KGCM2),
        maxAllowableWorkingPressureKgCm2 = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.MAX_ALLOWABLE_WORKING_PRESSURE_KGCM2),
        steamTemperature = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.STEAM_TEMPERATURE),
        operatingPressureKgCm2 = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.OPERATING_PRESSURE_KGCM2),
        fuelType = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.FUEL_TYPE),
        intendedUse = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.INTENDED_USE),
        operatorCertificate = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.OPERATOR_CERTIFICATE),
        equipmentHistory = getCheckItemValue(generalCat, PubtMappingKeys.Report.ItemName.EQUIPMENT_HISTORY)
    )

    val techCat = PubtMappingKeys.Report.Category.TECHNICAL_DATA
    val technicalData = GeneralTechnicalData(
        shell = GeneralShell(
            numberOfRounds = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.SHELL_NUMBER_OF_ROUNDS),
            connectionMethod = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.SHELL_CONNECTION_METHOD),
            material = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.SHELL_MATERIAL),
            pipeDiameterMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.SHELL_PIPE_DIAMETER_MM),
            thicknessMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.SHELL_THICKNESS_MM),
            bodyLengthMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.SHELL_BODY_LENGTH_MM)
        ),
        heads = GeneralHeads(
            type = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.HEADS_TYPE),
            topDiameterMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.HEADS_TOP_DIAMETER_MM),
            topThicknessMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.HEADS_TOP_THICKNESS_MM),
            rearDiameterMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.HEADS_REAR_DIAMETER_MM),
            rearThicknessMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.HEADS_REAR_THICKNESS_MM)
        ),
        tubePlate = GeneralTubePlate(
            frontDim1Mm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBE_PLATE_FRONT_DIM1_MM),
            frontDim2Mm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBE_PLATE_FRONT_DIM2_MM),
            rearDim1Mm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBE_PLATE_REAR_DIM1_MM),
            rearDim2Mm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBE_PLATE_REAR_DIM2_MM)
        ),
        furnace = GeneralFurnace(
            type = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.FURNACE_TYPE),
            material = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.FURNACE_MATERIAL),
            outerDiameterMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.FURNACE_OUTER_DIAMETER_MM),
            innerDiameterMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.FURNACE_INNER_DIAMETER_MM),
            thicknessMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.FURNACE_THICKNESS_MM)
        ),
        waterTubes = GeneralWaterTubes(
            firstPass = GeneralTubePass(
                diameterMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_FIRST_DIAMETER),
                thicknessMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_FIRST_THICKNESS),
                lengthMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_FIRST_LENGTH),
                quantity = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_FIRST_QUANTITY)
            ),
            secondPass = GeneralTubePass(
                diameterMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_SECOND_DIAMETER),
                thicknessMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_SECOND_THICKNESS),
                lengthMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_SECOND_LENGTH),
                quantity = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_SECOND_QUANTITY)
            ),
            stayTube = GeneralTubePass(
                diameterMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_STAY_DIAMETER),
                thicknessMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_STAY_THICKNESS),
                lengthMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_STAY_LENGTH),
                quantity = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_STAY_QUANTITY)
            ),
            material = GeneralTubePass(
                diameterMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_DIAMETER),
                thicknessMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_THICKNESS),
                lengthMm = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_LENGTH),
                quantity = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_MATERIAL_QUANTITY)
            ),
            connectionMethod = getCheckItemValue(techCat, PubtMappingKeys.Report.ItemName.TUBES_CONNECTION_METHOD)
        )
    )

    val visualCat = PubtMappingKeys.Report.Category.VISUAL_INSPECTION
    val inspectionAndMeasurement = GeneralInspectionAndMeasurement(
        visualInspection = GeneralVisualInspection(
            steamEquipmentShellDrum = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_SHELL_DRUM),
            steamEquipmentBouileur = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_BOUILEUR),
            steamEquipmentFurnace = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FURNACE),
            steamEquipmentFireChamber = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FIRE_CHAMBER),
            steamEquipmentRefractory = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_REFRACTORY),
            steamEquipmentCombustionChamber = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_COMBUSTION_CHAMBER),
            steamEquipmentFireTubes = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_FIRE_TUBES),
            steamEquipmentSuperHeater = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_SUPER_HEATER),
            steamEquipmentReheater = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_REHEATER),
            steamEquipmentEconomizer = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.STEAM_EQUIPMENT_ECONOMIZER),
            boilerDetailsGrate = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_GRATE),
            boilerDetailsBurner = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_BURNER),
            boilerDetailsFdf = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_FDF),
            boilerDetailsIdf = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_IDF),
            boilerDetailsAirHeater = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_AIR_HEATER),
            boilerDetailsAirDuct = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_AIR_DUCT),
            boilerDetailsGasDuct = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_GAS_DUCT),
            boilerDetailsAshCatcher = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_ASH_CATCHER),
            boilerDetailsChimney = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_CHIMNEY),
            boilerDetailsStairs = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_STAIRS),
            boilerDetailsInsulation = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BOILER_DETAILS_INSULATION),
            safetyValveRing = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_RING),
            safetyValvePipe = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_PIPE),
            safetyValveExhaust = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.SAFETY_VALVE_EXHAUST),
            pressureGaugeMark = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_MARK),
            pressureGaugeSiphon = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_SIPHON),
            pressureGaugeCock = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.PRESSURE_GAUGE_COCK),
            gaugeGlassTryCocks = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.GAUGE_GLASS_TRY_COCKS),
            gaugeGlassBlowdown = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.GAUGE_GLASS_BLOWDOWN),
            waterLevelLowestMark = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.WATER_LEVEL_LOWEST_MARK),
            waterLevelPosition = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.WATER_LEVEL_POSITION),
            feedwaterPump = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.FEEDWATER_PUMP),
            feedwaterCapacity = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.FEEDWATER_CAPACITY),
            feedwaterMotor = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.FEEDWATER_MOTOR),
            feedwaterCheckValve = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.FEEDWATER_CHECK_VALVE),
            controlBlacksFluit = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.CONTROL_BLACKS_FLUIT),
            controlFusiblePlug = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.CONTROL_FUSIBLE_PLUG),
            controlWaterLevel = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.CONTROL_WATER_LEVEL),
            controlSteamPressure = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.CONTROL_STEAM_PRESSURE),
            blowdownDesc = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BLOWDOWN_DESC),
            blowdownMaterial = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.BLOWDOWN_MATERIAL),
            manholeManhole = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.MANHOLE_MANHOLE),
            manholeInspectionHole = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.MANHOLE_INSPECTION_HOLE),
            idMarkNameplate = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.ID_MARK_NAMEPLATE),
            idMarkDataMatch = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.ID_MARK_DATA_MATCH),
            idMarkForm9Stamp = getCheckResult(visualCat, PubtMappingKeys.Report.ItemName.ID_MARK_FORM_9_STAMP)
        ),
        materialThickness = GeneralMaterialThickness(
            shellThicknessMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_THICKNESS_MM),
            shellDiameterMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_SHELL_DIAMETER_MM),
            headerDiameterMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_DIAMETER_MM),
            headerThicknessMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_THICKNESS_MM),
            headerLengthMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_HEADER_LENGTH_MM),
            furnace1DiameterMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_DIAMETER_MM),
            furnace1ThicknessMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_THICKNESS_MM),
            furnace1LengthMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE1_LENGTH_MM),
            furnace2DiameterMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_DIAMETER_MM),
            furnace2ThicknessMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_THICKNESS_MM),
            furnace2LengthMm = getValueResult(PubtMappingKeys.Report.Category.MATERIAL_THICKNESS, PubtMappingKeys.Report.ItemName.THICKNESS_FURNACE2_LENGTH_MM)
        ),
        thicknessMeasurementSetup = GeneralThicknessMeasurementSetup(
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
        measurementResultsTopHead = getMeasurementResultItem(PubtMappingKeys.Report.Category.MEASUREMENT_TOP_HEAD),
        measurementResultsShell = getMeasurementResultItem(PubtMappingKeys.Report.Category.MEASUREMENT_SHELL),
        measurementResultsButtonHead = getMeasurementResultItem(PubtMappingKeys.Report.Category.MEASUREMENT_BUTTON_HEAD),
        ndtTests = GeneralNdtTests(
            shell = GeneralNdtTestComponent(
                method = getCheckItemValue(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_SHELL_METHOD),
                longitudinalWeldJoint = getNdtResult(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_SHELL_LONGITUDINAL),
                circumferentialWeldJoint = getNdtResult(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_SHELL_CIRCUMFERENTIAL)
            ),
            furnace = GeneralNdtTestComponent(
                method = getCheckItemValue(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FURNACE_METHOD),
                longitudinalWeldJoint = getNdtResult(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FURNACE_LONGITUDINAL),
                circumferentialWeldJoint = getNdtResult(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FURNACE_CIRCUMFERENTIAL)
            ),
            fireTubes = GeneralNdtTestFireTubes(
                method = getCheckItemValue(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_METHOD),
                weldJointFront = getNdtResult(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_FRONT),
                weldJointRear = getNdtResult(PubtMappingKeys.Report.Category.NDT_TESTS, PubtMappingKeys.Report.ItemName.NDT_FIRE_TUBES_REAR)
            )
        ),
        hydrotest = GeneralHydrotest(
            testPressureKgCm2 = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_PRESSURE_KGCM2),
            mawpKgCm2 = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_MAWP_KGCM2),
            testMedium = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_MEDIUM),
            testDate = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_DATE),
            testResult = getCheckItemValue(PubtMappingKeys.Report.Category.HYDROTEST, PubtMappingKeys.Report.ItemName.HYDROTEST_TEST_RESULT)
        ),
        appendagesInspection = GeneralAppendagesInspection(
            pressureGauge = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_PRESSURE_GAUGE),
            manHole = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_MANHOLE),
            safetyValve = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_SAFETY_VALVE),
            mainSteamValve = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_MAIN_STEAM_VALVE),
            levelGlassIndicator = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_LEVEL_GLASS_INDICATOR),
            blowdownValve = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_BLOWDOWN_VALVE),
            feedwaterStopValve = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_FEEDWATER_STOP_VALVE),
            feedwaterInletValve = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_FEEDWATER_INLET_VALVE),
            steamDrier = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_STEAM_DRIER),
            waterPump = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_WATER_PUMP),
            controlPanel = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_CONTROL_PANEL),
            nameplate = getAppendageResult(PubtMappingKeys.Report.Category.APPENDAGES, PubtMappingKeys.Report.ItemName.APPENDAGE_NAMEPLATE)
        ),
        safetyValveTest = GeneralSafetyValveTest(
            header = getCheckItemValue(PubtMappingKeys.Report.Category.SAFETY_VALVE_TEST, PubtMappingKeys.Report.ItemName.VALVE_TEST_HEADER),
            startsToOpenKgCm2 = getCheckItemValue(PubtMappingKeys.Report.Category.SAFETY_VALVE_TEST, PubtMappingKeys.Report.ItemName.VALVE_TEST_STARTS_TO_OPEN_KGCM2),
            valveInfo = getCheckItemValue(PubtMappingKeys.Report.Category.SAFETY_VALVE_TEST, PubtMappingKeys.Report.ItemName.VALVE_TEST_VALVE_INFO)
        )
    )

    val summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }
    val recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }
    val conclusion = GeneralConclusion(
        summary = summary.toImmutableList(),
        recommendations = recommendations.toImmutableList()
    )

    val report = GeneralInspectionReport(
        extraId = inspection.extraId,
        moreExtraId = inspection.moreExtraId,
        equipmentType = inspection.equipmentType,
        examinationType = inspection.examinationType,
        generalData = generalData,
        technicalData = technicalData,
        inspectionAndMeasurement = inspectionAndMeasurement,
        conclusion = conclusion
    )

    return GeneralUiState(inspectionReport = report)
}
