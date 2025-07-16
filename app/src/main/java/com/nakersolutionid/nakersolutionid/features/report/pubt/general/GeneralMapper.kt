package com.nakersolutionid.nakersolutionid.features.report.pubt.general

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
 * Maps the GeneralUiState to an InspectionWithDetailsDomain model without using reflection.
 * All UI state data is flattened into InspectionDomain, InspectionFindingDomain, or InspectionCheckItemDomain.
 */
fun GeneralUiState.toInspectionWithDetailsDomain(
    currentTime: String
): InspectionWithDetailsDomain {
    val report = this.inspectionReport
    val generalData = report.generalData
    val technicalData = report.technicalData
    val inspection = report.inspectionAndMeasurement
    val conclusion = report.conclusion

    // Hardcoded values as requested
    val inspectionId = 0L
    val documentType = DocumentType.LAPORAN
    val inspectionType = InspectionType.PUBT
    val subInspectionType = SubInspectionType.General_PUBT

    // 1. Map main and general data to InspectionDomain
    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = "",
        documentType = documentType,
        inspectionType = inspectionType,
        subInspectionType = subInspectionType,
        equipmentType = report.equipmentType,
        examinationType = report.examinationType,
        ownerName = generalData.ownerName,
        ownerAddress = generalData.ownerAddress,
        usageLocation = "", // Not directly available
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
        speed = "",
        floorServed = "",
        reportDate = generalData.inspectionDate,
        nextInspectionDate = "",
        inspectorName = "",
        status = "",
        isSynced = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun addCheckItem(category: String, itemName: String, value: String?) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = false,
                result = value ?: ""
            )
        )
    }

    fun addCheckResultItem(category: String, itemName: String, value: GeneralCheckResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.isMet,
                result = value.remarks
            )
        )
    }

    fun addValueResultItem(category: String, itemName: String, value: GeneralValueResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = false, // status is not used here
                result = "${value.value}#${value.remarks}" // Combine value and remarks
            )
        )
    }

    fun addNdtResultItem(category: String, itemName: String, value: GeneralNdtResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.isGood,
                result = "${value.location}#${value.remarks}" // Combine location and remarks
            )
        )
    }

    fun addAppendageResultItem(category: String, itemName: String, value: GeneralAppendageResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.isGood,
                result = "${value.quantity}#${value.remarks}" // Combine quantity and remarks
            )
        )
    }

    // 2. Map remaining General Data to CheckItems
    addCheckItem("general_data", "user", generalData.user)
    addCheckItem("general_data", "operatorName", generalData.operatorName)
    addCheckItem("general_data", "designPressureKgCm2", generalData.designPressureKgCm2)
    addCheckItem("general_data", "maxAllowableWorkingPressureKgCm2", generalData.maxAllowableWorkingPressureKgCm2)
    addCheckItem("general_data", "steamTemperature", generalData.steamTemperature)
    addCheckItem("general_data", "operatingPressureKgCm2", generalData.operatingPressureKgCm2)
    addCheckItem("general_data", "fuelType", generalData.fuelType)
    addCheckItem("general_data", "intendedUse", generalData.intendedUse)
    addCheckItem("general_data", "operatorCertificate", generalData.operatorCertificate)
    addCheckItem("general_data", "equipmentHistory", generalData.equipmentHistory)

    // 3. Map all Technical Data to CheckItems
    val techCategory = "technical_data"
    technicalData.shell.let {
        addCheckItem(techCategory, "shell_numberOfRounds", it.numberOfRounds)
        addCheckItem(techCategory, "shell_connectionMethod", it.connectionMethod)
        addCheckItem(techCategory, "shell_material", it.material)
        addCheckItem(techCategory, "shell_pipeDiameterMm", it.pipeDiameterMm)
        addCheckItem(techCategory, "shell_thicknessMm", it.thicknessMm)
        addCheckItem(techCategory, "shell_bodyLengthMm", it.bodyLengthMm)
    }
    technicalData.heads.let {
        addCheckItem(techCategory, "heads_type", it.type)
        addCheckItem(techCategory, "heads_topDiameterMm", it.topDiameterMm)
        addCheckItem(techCategory, "heads_topThicknessMm", it.topThicknessMm)
        addCheckItem(techCategory, "heads_rearDiameterMm", it.rearDiameterMm)
        addCheckItem(techCategory, "heads_rearThicknessMm", it.rearThicknessMm)
    }
    technicalData.tubePlate.let {
        addCheckItem(techCategory, "tubePlate_frontDim1Mm", it.frontDim1Mm)
        addCheckItem(techCategory, "tubePlate_frontDim2Mm", it.frontDim2Mm)
        addCheckItem(techCategory, "tubePlate_rearDim1Mm", it.rearDim1Mm)
        addCheckItem(techCategory, "tubePlate_rearDim2Mm", it.rearDim2Mm)
    }
    technicalData.furnace.let {
        addCheckItem(techCategory, "furnace_type", it.type)
        addCheckItem(techCategory, "furnace_material", it.material)
        addCheckItem(techCategory, "furnace_outerDiameterMm", it.outerDiameterMm)
        addCheckItem(techCategory, "furnace_innerDiameterMm", it.innerDiameterMm)
        addCheckItem(techCategory, "furnace_thicknessMm", it.thicknessMm)
    }
    technicalData.waterTubes.let {
        addCheckItem(techCategory, "tubes_first_diameter", it.firstPass.diameterMm)
        addCheckItem(techCategory, "tubes_first_thickness", it.firstPass.thicknessMm)
        addCheckItem(techCategory, "tubes_first_length", it.firstPass.lengthMm)
        addCheckItem(techCategory, "tubes_first_quantity", it.firstPass.quantity)
        addCheckItem(techCategory, "tubes_second_diameter", it.secondPass.diameterMm)
        addCheckItem(techCategory, "tubes_second_thickness", it.secondPass.thicknessMm)
        addCheckItem(techCategory, "tubes_second_length", it.secondPass.lengthMm)
        addCheckItem(techCategory, "tubes_second_quantity", it.secondPass.quantity)
        addCheckItem(techCategory, "tubes_stay_diameter", it.stayTube.diameterMm)
        addCheckItem(techCategory, "tubes_stay_thickness", it.stayTube.thicknessMm)
        addCheckItem(techCategory, "tubes_stay_length", it.stayTube.lengthMm)
        addCheckItem(techCategory, "tubes_stay_quantity", it.stayTube.quantity)
        addCheckItem(techCategory, "tubes_material", it.material)
        addCheckItem(techCategory, "tubes_connectionMethod", it.connectionMethod)
    }

    // 4. Map Inspection and Measurement to CheckItems
    val visualCategory = "visual_inspection"
    inspection.visualInspection.let { v ->
        addCheckResultItem(visualCategory, "steamEquipmentShellDrum", v.steamEquipmentShellDrum)
        addCheckResultItem(visualCategory, "steamEquipmentBouileur", v.steamEquipmentBouileur)
        addCheckResultItem(visualCategory, "steamEquipmentFurnace", v.steamEquipmentFurnace)
        addCheckResultItem(visualCategory, "steamEquipmentFireChamber", v.steamEquipmentFireChamber)
        addCheckResultItem(visualCategory, "steamEquipmentRefractory", v.steamEquipmentRefractory)
        addCheckResultItem(visualCategory, "steamEquipmentCombustionChamber", v.steamEquipmentCombustionChamber)
        addCheckResultItem(visualCategory, "steamEquipmentFireTubes", v.steamEquipmentFireTubes)
        addCheckResultItem(visualCategory, "steamEquipmentSuperHeater", v.steamEquipmentSuperHeater)
        addCheckResultItem(visualCategory, "steamEquipmentReheater", v.steamEquipmentReheater)
        addCheckResultItem(visualCategory, "steamEquipmentEconomizer", v.steamEquipmentEconomizer)
        addCheckResultItem(visualCategory, "boilerDetailsGrate", v.boilerDetailsGrate)
        addCheckResultItem(visualCategory, "boilerDetailsBurner", v.boilerDetailsBurner)
        addCheckResultItem(visualCategory, "boilerDetailsFdf", v.boilerDetailsFdf)
        addCheckResultItem(visualCategory, "boilerDetailsIdf", v.boilerDetailsIdf)
        addCheckResultItem(visualCategory, "boilerDetailsAirHeater", v.boilerDetailsAirHeater)
        addCheckResultItem(visualCategory, "boilerDetailsAirDuct", v.boilerDetailsAirDuct)
        addCheckResultItem(visualCategory, "boilerDetailsGasDuct", v.boilerDetailsGasDuct)
        addCheckResultItem(visualCategory, "boilerDetailsAshCatcher", v.boilerDetailsAshCatcher)
        addCheckResultItem(visualCategory, "boilerDetailsChimney", v.boilerDetailsChimney)
        addCheckResultItem(visualCategory, "boilerDetailsStairs", v.boilerDetailsStairs)
        addCheckResultItem(visualCategory, "boilerDetailsInsulation", v.boilerDetailsInsulation)
        addCheckResultItem(visualCategory, "safetyValveRing", v.safetyValveRing)
        addCheckResultItem(visualCategory, "safetyValvePipe", v.safetyValvePipe)
        addCheckResultItem(visualCategory, "safetyValveExhaust", v.safetyValveExhaust)
        addCheckResultItem(visualCategory, "pressureGaugeMark", v.pressureGaugeMark)
        addCheckResultItem(visualCategory, "pressureGaugeSiphon", v.pressureGaugeSiphon)
        addCheckResultItem(visualCategory, "pressureGaugeCock", v.pressureGaugeCock)
        addCheckResultItem(visualCategory, "gaugeGlassTryCocks", v.gaugeGlassTryCocks)
        addCheckResultItem(visualCategory, "gaugeGlassBlowdown", v.gaugeGlassBlowdown)
        addCheckResultItem(visualCategory, "waterLevelLowestMark", v.waterLevelLowestMark)
        addCheckResultItem(visualCategory, "waterLevelPosition", v.waterLevelPosition)
        addCheckResultItem(visualCategory, "feedwaterPump", v.feedwaterPump)
        addCheckResultItem(visualCategory, "feedwaterCapacity", v.feedwaterCapacity)
        addCheckResultItem(visualCategory, "feedwaterMotor", v.feedwaterMotor)
        addCheckResultItem(visualCategory, "feedwaterCheckValve", v.feedwaterCheckValve)
        addCheckResultItem(visualCategory, "controlBlacksFluit", v.controlBlacksFluit)
        addCheckResultItem(visualCategory, "controlFusiblePlug", v.controlFusiblePlug)
        addCheckResultItem(visualCategory, "controlWaterLevel", v.controlWaterLevel)
        addCheckResultItem(visualCategory, "controlSteamPressure", v.controlSteamPressure)
        addCheckResultItem(visualCategory, "blowdownDesc", v.blowdownDesc)
        addCheckResultItem(visualCategory, "blowdownMaterial", v.blowdownMaterial)
        addCheckResultItem(visualCategory, "manholeManhole", v.manholeManhole)
        addCheckResultItem(visualCategory, "manholeInspectionHole", v.manholeInspectionHole)
        addCheckResultItem(visualCategory, "idMarkNameplate", v.idMarkNameplate)
        addCheckResultItem(visualCategory, "idMarkDataMatch", v.idMarkDataMatch)
        addCheckResultItem(visualCategory, "idMarkForm9Stamp", v.idMarkForm9Stamp)
    }

    val thicknessCategory = "material_thickness"
    inspection.materialThickness.let { t ->
        addValueResultItem(thicknessCategory, "shellThicknessMm", t.shellThicknessMm)
        addValueResultItem(thicknessCategory, "shellDiameterMm", t.shellDiameterMm)
        addValueResultItem(thicknessCategory, "headerDiameterMm", t.headerDiameterMm)
        addValueResultItem(thicknessCategory, "headerThicknessMm", t.headerThicknessMm)
        addValueResultItem(thicknessCategory, "headerLengthMm", t.headerLengthMm)
        addValueResultItem(thicknessCategory, "furnace1DiameterMm", t.furnace1DiameterMm)
        addValueResultItem(thicknessCategory, "furnace1ThicknessMm", t.furnace1ThicknessMm)
        addValueResultItem(thicknessCategory, "furnace1LengthMm", t.furnace1LengthMm)
        addValueResultItem(thicknessCategory, "furnace2DiameterMm", t.furnace2DiameterMm)
        addValueResultItem(thicknessCategory, "furnace2ThicknessMm", t.furnace2ThicknessMm)
        addValueResultItem(thicknessCategory, "furnace2LengthMm", t.furnace2LengthMm)
    }

    val setupCategory = "thickness_setup"
    inspection.thicknessMeasurementSetup.let { s ->
        addCheckItem(setupCategory, "owner", s.owner)
        addCheckItem(setupCategory, "inspectionDate", s.inspectionDate)
        addCheckItem(setupCategory, "project", s.project)
        addCheckItem(setupCategory, "objectType", s.objectType)
        addCheckItem(setupCategory, "workOrderNo", s.workOrderNo)
        addCheckItem(setupCategory, "equipmentUsed", s.equipmentUsed)
        addCheckItem(setupCategory, "methodUsed", s.methodUsed)
        addCheckItem(setupCategory, "probeType", s.probeType)
        addCheckItem(setupCategory, "materialType", s.materialType)
        addCheckItem(setupCategory, "probeStyle", s.probeStyle)
        addCheckItem(setupCategory, "operatingTemp", s.operatingTemp)
        addCheckItem(setupCategory, "surfaceCondition", s.surfaceCondition)
        addCheckItem(setupCategory, "weldingProcess", s.weldingProcess)
        addCheckItem(setupCategory, "laminatingCheck", s.laminatingCheck)
        addCheckItem(setupCategory, "couplantUsed", s.couplantUsed)
    }

    inspection.measurementResultsTable.forEachIndexed { index, item ->
        val cat = "measurement_results_$index"
        addCheckItem(cat, "position", item.position)
        addCheckItem(cat, "nominalMm", item.nominalMm)
        addCheckItem(cat, "point1", item.point1)
        addCheckItem(cat, "point2", item.point2)
        addCheckItem(cat, "point3", item.point3)
        addCheckItem(cat, "minimum", item.minimum)
        addCheckItem(cat, "maximum", item.maximum)
    }

    val ndtCategory = "ndt_tests"
    inspection.ndtTests.shell.let {
        addCheckItem(ndtCategory, "shell_method", it.method)
        addNdtResultItem(ndtCategory, "shell_longitudinal", it.longitudinalWeldJoint)
        addNdtResultItem(ndtCategory, "shell_circumferential", it.circumferentialWeldJoint)
    }
    inspection.ndtTests.furnace.let {
        addCheckItem(ndtCategory, "furnace_method", it.method)
        addNdtResultItem(ndtCategory, "furnace_longitudinal", it.longitudinalWeldJoint)
        addNdtResultItem(ndtCategory, "furnace_circumferential", it.circumferentialWeldJoint)
    }
    inspection.ndtTests.fireTubes.let {
        addCheckItem(ndtCategory, "fireTubes_method", it.method)
        addNdtResultItem(ndtCategory, "fireTubes_front", it.weldJointFront)
        addNdtResultItem(ndtCategory, "fireTubes_rear", it.weldJointRear)
    }

    val hydroCategory = "hydrotest"
    inspection.hydrotest.let {
        addCheckItem(hydroCategory, "testPressureKgCm2", it.testPressureKgCm2)
        addCheckItem(hydroCategory, "mawpKgCm2", it.mawpKgCm2)
        addCheckItem(hydroCategory, "testMedium", it.testMedium)
        addCheckItem(hydroCategory, "testDate", it.testDate)
        addCheckItem(hydroCategory, "testResult", it.testResult)
    }

    val appendageCategory = "appendages"
    inspection.appendagesInspection.let { app ->
        addAppendageResultItem(appendageCategory, "pressureGauge", app.pressureGauge)
        addAppendageResultItem(appendageCategory, "manHole", app.manHole)
        addAppendageResultItem(appendageCategory, "safetyValve", app.safetyValve)
        addAppendageResultItem(appendageCategory, "mainSteamValve", app.mainSteamValve)
        addAppendageResultItem(appendageCategory, "levelGlassIndicator", app.levelGlassIndicator)
        addAppendageResultItem(appendageCategory, "blowdownValve", app.blowdownValve)
        addAppendageResultItem(appendageCategory, "feedwaterStopValve", app.feedwaterStopValve)
        addAppendageResultItem(appendageCategory, "feedwaterInletValve", app.feedwaterInletValve)
        addAppendageResultItem(appendageCategory, "steamDrier", app.steamDrier)
        addAppendageResultItem(appendageCategory, "waterPump", app.waterPump)
        addAppendageResultItem(appendageCategory, "controlPanel", app.controlPanel)
        addAppendageResultItem(appendageCategory, "nameplate", app.nameplate)
    }

    val valveCategory = "safety_valve_test"
    inspection.safetyValveTest.let {
        addCheckItem(valveCategory, "header", it.header)
        addCheckItem(valveCategory, "startsToOpenKgCm2", it.startsToOpenKgCm2)
        addCheckItem(valveCategory, "valveInfo", it.valveInfo)
    }

    // 8. Map Conclusion to Findings
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
        testResults = emptyList()
    )
}

fun InspectionWithDetailsDomain.toGeneralUiState(): GeneralUiState {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getCheckItemValue(category: String, itemName: String): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    fun getCheckResult(category: String, itemName: String): GeneralCheckResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return GeneralCheckResult(
            isMet = item?.status ?: false,
            remarks = item?.result ?: ""
        )
    }

    fun getValueResult(category: String, itemName: String): GeneralValueResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        val parts = (item?.result ?: "").split("#")
        return GeneralValueResult(
            value = parts.getOrNull(0) ?: "",
            remarks = parts.getOrNull(1) ?: ""
        )
    }

    fun getNdtResult(category: String, itemName: String): GeneralNdtResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        val parts = (item?.result ?: "").split("#")
        return GeneralNdtResult(
            isGood = item?.status ?: false,
            location = parts.getOrNull(0) ?: "",
            remarks = parts.getOrNull(1) ?: ""
        )
    }

    fun getAppendageResult(category: String, itemName: String): GeneralAppendageResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        val parts = (item?.result ?: "").split("#")
        return GeneralAppendageResult(
            isGood = item?.status ?: false,
            quantity = parts.getOrNull(0) ?: "",
            remarks = parts.getOrNull(1) ?: ""
        )
    }

    fun getCheckItemValueForIndexed(prefix: String, index: Int, itemName: String): String {
        val category = "${prefix}_${index}"
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

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
        user = getCheckItemValue("general_data", "user"),
        operatorName = getCheckItemValue("general_data", "operatorName"),
        designPressureKgCm2 = getCheckItemValue("general_data", "designPressureKgCm2"),
        maxAllowableWorkingPressureKgCm2 = getCheckItemValue("general_data", "maxAllowableWorkingPressureKgCm2"),
        steamTemperature = getCheckItemValue("general_data", "steamTemperature"),
        operatingPressureKgCm2 = getCheckItemValue("general_data", "operatingPressureKgCm2"),
        fuelType = getCheckItemValue("general_data", "fuelType"),
        intendedUse = getCheckItemValue("general_data", "intendedUse"),
        operatorCertificate = getCheckItemValue("general_data", "operatorCertificate"),
        equipmentHistory = getCheckItemValue("general_data", "equipmentHistory")
    )

    val techCategory = "technical_data"
    val technicalData = GeneralTechnicalData(
        shell = GeneralShell(
            numberOfRounds = getCheckItemValue(techCategory, "shell_numberOfRounds"),
            connectionMethod = getCheckItemValue(techCategory, "shell_connectionMethod"),
            material = getCheckItemValue(techCategory, "shell_material"),
            pipeDiameterMm = getCheckItemValue(techCategory, "shell_pipeDiameterMm"),
            thicknessMm = getCheckItemValue(techCategory, "shell_thicknessMm"),
            bodyLengthMm = getCheckItemValue(techCategory, "shell_bodyLengthMm")
        ),
        heads = GeneralHeads(
            type = getCheckItemValue(techCategory, "heads_type"),
            topDiameterMm = getCheckItemValue(techCategory, "heads_topDiameterMm"),
            topThicknessMm = getCheckItemValue(techCategory, "heads_topThicknessMm"),
            rearDiameterMm = getCheckItemValue(techCategory, "heads_rearDiameterMm"),
            rearThicknessMm = getCheckItemValue(techCategory, "heads_rearThicknessMm")
        ),
        tubePlate = GeneralTubePlate(
            frontDim1Mm = getCheckItemValue(techCategory, "tubePlate_frontDim1Mm"),
            frontDim2Mm = getCheckItemValue(techCategory, "tubePlate_frontDim2Mm"),
            rearDim1Mm = getCheckItemValue(techCategory, "tubePlate_rearDim1Mm"),
            rearDim2Mm = getCheckItemValue(techCategory, "tubePlate_rearDim2Mm")
        ),
        furnace = GeneralFurnace(
            type = getCheckItemValue(techCategory, "furnace_type"),
            material = getCheckItemValue(techCategory, "furnace_material"),
            outerDiameterMm = getCheckItemValue(techCategory, "furnace_outerDiameterMm"),
            innerDiameterMm = getCheckItemValue(techCategory, "furnace_innerDiameterMm"),
            thicknessMm = getCheckItemValue(techCategory, "furnace_thicknessMm")
        ),
        waterTubes = GeneralWaterTubes(
            firstPass = GeneralTubePass(
                diameterMm = getCheckItemValue(techCategory, "tubes_first_diameter"),
                thicknessMm = getCheckItemValue(techCategory, "tubes_first_thickness"),
                lengthMm = getCheckItemValue(techCategory, "tubes_first_length"),
                quantity = getCheckItemValue(techCategory, "tubes_first_quantity")
            ),
            secondPass = GeneralTubePass(
                diameterMm = getCheckItemValue(techCategory, "tubes_second_diameter"),
                thicknessMm = getCheckItemValue(techCategory, "tubes_second_thickness"),
                lengthMm = getCheckItemValue(techCategory, "tubes_second_length"),
                quantity = getCheckItemValue(techCategory, "tubes_second_quantity")
            ),
            stayTube = GeneralTubePass(
                diameterMm = getCheckItemValue(techCategory, "tubes_stay_diameter"),
                thicknessMm = getCheckItemValue(techCategory, "tubes_stay_thickness"),
                lengthMm = getCheckItemValue(techCategory, "tubes_stay_length"),
                quantity = getCheckItemValue(techCategory, "tubes_stay_quantity")
            ),
            material = getCheckItemValue(techCategory, "tubes_material"),
            connectionMethod = getCheckItemValue(techCategory, "tubes_connectionMethod")
        )
    )

    val visualCategory = "visual_inspection"
    val visualInspection = GeneralVisualInspection(
        steamEquipmentShellDrum = getCheckResult(visualCategory, "steamEquipmentShellDrum"),
        steamEquipmentBouileur = getCheckResult(visualCategory, "steamEquipmentBouileur"),
        steamEquipmentFurnace = getCheckResult(visualCategory, "steamEquipmentFurnace"),
        steamEquipmentFireChamber = getCheckResult(visualCategory, "steamEquipmentFireChamber"),
        steamEquipmentRefractory = getCheckResult(visualCategory, "steamEquipmentRefractory"),
        steamEquipmentCombustionChamber = getCheckResult(visualCategory, "steamEquipmentCombustionChamber"),
        steamEquipmentFireTubes = getCheckResult(visualCategory, "steamEquipmentFireTubes"),
        steamEquipmentSuperHeater = getCheckResult(visualCategory, "steamEquipmentSuperHeater"),
        steamEquipmentReheater = getCheckResult(visualCategory, "steamEquipmentReheater"),
        steamEquipmentEconomizer = getCheckResult(visualCategory, "steamEquipmentEconomizer"),
        boilerDetailsGrate = getCheckResult(visualCategory, "boilerDetailsGrate"),
        boilerDetailsBurner = getCheckResult(visualCategory, "boilerDetailsBurner"),
        boilerDetailsFdf = getCheckResult(visualCategory, "boilerDetailsFdf"),
        boilerDetailsIdf = getCheckResult(visualCategory, "boilerDetailsIdf"),
        boilerDetailsAirHeater = getCheckResult(visualCategory, "boilerDetailsAirHeater"),
        boilerDetailsAirDuct = getCheckResult(visualCategory, "boilerDetailsAirDuct"),
        boilerDetailsGasDuct = getCheckResult(visualCategory, "boilerDetailsGasDuct"),
        boilerDetailsAshCatcher = getCheckResult(visualCategory, "boilerDetailsAshCatcher"),
        boilerDetailsChimney = getCheckResult(visualCategory, "boilerDetailsChimney"),
        boilerDetailsStairs = getCheckResult(visualCategory, "boilerDetailsStairs"),
        boilerDetailsInsulation = getCheckResult(visualCategory, "boilerDetailsInsulation"),
        safetyValveRing = getCheckResult(visualCategory, "safetyValveRing"),
        safetyValvePipe = getCheckResult(visualCategory, "safetyValvePipe"),
        safetyValveExhaust = getCheckResult(visualCategory, "safetyValveExhaust"),
        pressureGaugeMark = getCheckResult(visualCategory, "pressureGaugeMark"),
        pressureGaugeSiphon = getCheckResult(visualCategory, "pressureGaugeSiphon"),
        pressureGaugeCock = getCheckResult(visualCategory, "pressureGaugeCock"),
        gaugeGlassTryCocks = getCheckResult(visualCategory, "gaugeGlassTryCocks"),
        gaugeGlassBlowdown = getCheckResult(visualCategory, "gaugeGlassBlowdown"),
        waterLevelLowestMark = getCheckResult(visualCategory, "waterLevelLowestMark"),
        waterLevelPosition = getCheckResult(visualCategory, "waterLevelPosition"),
        feedwaterPump = getCheckResult(visualCategory, "feedwaterPump"),
        feedwaterCapacity = getCheckResult(visualCategory, "feedwaterCapacity"),
        feedwaterMotor = getCheckResult(visualCategory, "feedwaterMotor"),
        feedwaterCheckValve = getCheckResult(visualCategory, "feedwaterCheckValve"),
        controlBlacksFluit = getCheckResult(visualCategory, "controlBlacksFluit"),
        controlFusiblePlug = getCheckResult(visualCategory, "controlFusiblePlug"),
        controlWaterLevel = getCheckResult(visualCategory, "controlWaterLevel"),
        controlSteamPressure = getCheckResult(visualCategory, "controlSteamPressure"),
        blowdownDesc = getCheckResult(visualCategory, "blowdownDesc"),
        blowdownMaterial = getCheckResult(visualCategory, "blowdownMaterial"),
        manholeManhole = getCheckResult(visualCategory, "manholeManhole"),
        manholeInspectionHole = getCheckResult(visualCategory, "manholeInspectionHole"),
        idMarkNameplate = getCheckResult(visualCategory, "idMarkNameplate"),
        idMarkDataMatch = getCheckResult(visualCategory, "idMarkDataMatch"),
        idMarkForm9Stamp = getCheckResult(visualCategory, "idMarkForm9Stamp")
    )

    val measurementItems = mutableListOf<GeneralMeasurementResultItem>()
    val measurementCategories = this.checkItems.map { it.category }.filter { it.startsWith("measurement_results_") }.mapNotNull { it.substringAfterLast('_').toIntOrNull() }.distinct()
    measurementCategories.forEach { index ->
        measurementItems.add(GeneralMeasurementResultItem(
            position = getCheckItemValueForIndexed("measurement_results", index, "position"),
            nominalMm = getCheckItemValueForIndexed("measurement_results", index, "nominalMm"),
            point1 = getCheckItemValueForIndexed("measurement_results", index, "point1"),
            point2 = getCheckItemValueForIndexed("measurement_results", index, "point2"),
            point3 = getCheckItemValueForIndexed("measurement_results", index, "point3"),
            minimum = getCheckItemValueForIndexed("measurement_results", index, "minimum"),
            maximum = getCheckItemValueForIndexed("measurement_results", index, "maximum"),
        ))
    }

    val inspectionAndMeasurement = GeneralInspectionAndMeasurement(
        visualInspection = visualInspection,
        materialThickness = GeneralMaterialThickness(
            shellThicknessMm = getValueResult("material_thickness", "shellThicknessMm"),
            shellDiameterMm = getValueResult("material_thickness", "shellDiameterMm"),
            headerDiameterMm = getValueResult("material_thickness", "headerDiameterMm"),
            headerThicknessMm = getValueResult("material_thickness", "headerThicknessMm"),
            headerLengthMm = getValueResult("material_thickness", "headerLengthMm"),
            furnace1DiameterMm = getValueResult("material_thickness", "furnace1DiameterMm"),
            furnace1ThicknessMm = getValueResult("material_thickness", "furnace1ThicknessMm"),
            furnace1LengthMm = getValueResult("material_thickness", "furnace1LengthMm"),
            furnace2DiameterMm = getValueResult("material_thickness", "furnace2DiameterMm"),
            furnace2ThicknessMm = getValueResult("material_thickness", "furnace2ThicknessMm"),
            furnace2LengthMm = getValueResult("material_thickness", "furnace2LengthMm")
        ),
        thicknessMeasurementSetup = GeneralThicknessMeasurementSetup(
            owner = getCheckItemValue("thickness_setup", "owner"),
            inspectionDate = getCheckItemValue("thickness_setup", "inspectionDate"),
            project = getCheckItemValue("thickness_setup", "project"),
            objectType = getCheckItemValue("thickness_setup", "objectType"),
            workOrderNo = getCheckItemValue("thickness_setup", "workOrderNo"),
            equipmentUsed = getCheckItemValue("thickness_setup", "equipmentUsed"),
            methodUsed = getCheckItemValue("thickness_setup", "methodUsed"),
            probeType = getCheckItemValue("thickness_setup", "probeType"),
            materialType = getCheckItemValue("thickness_setup", "materialType"),
            probeStyle = getCheckItemValue("thickness_setup", "probeStyle"),
            operatingTemp = getCheckItemValue("thickness_setup", "operatingTemp"),
            surfaceCondition = getCheckItemValue("thickness_setup", "surfaceCondition"),
            weldingProcess = getCheckItemValue("thickness_setup", "weldingProcess"),
            laminatingCheck = getCheckItemValue("thickness_setup", "laminatingCheck"),
            couplantUsed = getCheckItemValue("thickness_setup", "couplantUsed")
        ),
        measurementResultsTable = measurementItems.toImmutableList(),
        ndtTests = GeneralNdtTests(
            shell = GeneralNdtTestComponent(
                method = getCheckItemValue("ndt_tests", "shell_method"),
                longitudinalWeldJoint = getNdtResult("ndt_tests", "shell_longitudinal"),
                circumferentialWeldJoint = getNdtResult("ndt_tests", "shell_circumferential")
            ),
            furnace = GeneralNdtTestComponent(
                method = getCheckItemValue("ndt_tests", "furnace_method"),
                longitudinalWeldJoint = getNdtResult("ndt_tests", "furnace_longitudinal"),
                circumferentialWeldJoint = getNdtResult("ndt_tests", "furnace_circumferential")
            ),
            fireTubes = GeneralNdtTestFireTubes(
                method = getCheckItemValue("ndt_tests", "fireTubes_method"),
                weldJointFront = getNdtResult("ndt_tests", "fireTubes_front"),
                weldJointRear = getNdtResult("ndt_tests", "fireTubes_rear")
            )
        ),
        hydrotest = GeneralHydrotest(
            testPressureKgCm2 = getCheckItemValue("hydrotest", "testPressureKgCm2"),
            mawpKgCm2 = getCheckItemValue("hydrotest", "mawpKgCm2"),
            testMedium = getCheckItemValue("hydrotest", "testMedium"),
            testDate = getCheckItemValue("hydrotest", "testDate"),
            testResult = getCheckItemValue("hydrotest", "testResult")
        ),
        appendagesInspection = GeneralAppendagesInspection(
            pressureGauge = getAppendageResult("appendages", "pressureGauge"),
            manHole = getAppendageResult("appendages", "manHole"),
            safetyValve = getAppendageResult("appendages", "safetyValve"),
            mainSteamValve = getAppendageResult("appendages", "mainSteamValve"),
            levelGlassIndicator = getAppendageResult("appendages", "levelGlassIndicator"),
            blowdownValve = getAppendageResult("appendages", "blowdownValve"),
            feedwaterStopValve = getAppendageResult("appendages", "feedwaterStopValve"),
            feedwaterInletValve = getAppendageResult("appendages", "feedwaterInletValve"),
            steamDrier = getAppendageResult("appendages", "steamDrier"),
            waterPump = getAppendageResult("appendages", "waterPump"),
            controlPanel = getAppendageResult("appendages", "controlPanel"),
            nameplate = getAppendageResult("appendages", "nameplate")
        ),
        safetyValveTest = GeneralSafetyValveTest(
            header = getCheckItemValue("safety_valve_test", "header"),
            startsToOpenKgCm2 = getCheckItemValue("safety_valve_test", "startsToOpenKgCm2"),
            valveInfo = getCheckItemValue("safety_valve_test", "valveInfo")
        )
    )

    val summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }
    val recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }
    val conclusion = GeneralConclusion(
        summary = summary.toImmutableList(),
        recommendations = recommendations.toImmutableList()
    )

    val report = GeneralInspectionReport(
        equipmentType = inspection.equipmentType,
        examinationType = inspection.examinationType,
        generalData = generalData,
        technicalData = technicalData,
        inspectionAndMeasurement = inspectionAndMeasurement,
        conclusion = conclusion
    )

    return GeneralUiState(inspectionReport = report)
}