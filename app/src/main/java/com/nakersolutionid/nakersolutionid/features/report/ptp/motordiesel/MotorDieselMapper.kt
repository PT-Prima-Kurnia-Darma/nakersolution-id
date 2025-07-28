package com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel

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
 * Maps the DieselMotorUiState to an InspectionWithDetailsDomain model without using reflection.
 * All UI state data is flattened into InspectionDomain, InspectionFindingDomain, or InspectionCheckItemDomain.
 */
fun DieselMotorUiState.toInspectionWithDetailsDomain(
    currentTime: String,
    isEdited: Boolean,
    id: Long? = null
): InspectionWithDetailsDomain {
    val report = this.inspectionReport
    val generalData = report.generalData
    val technicalData = report.technicalData
    val visualInspection = report.visualInspection
    val testing = report.testingAndMeasurement
    val mcb = report.mcbCalculation
    val noise = report.noiseMeasurement
    val lighting = report.lightingMeasurement
    val conclusion = report.conclusion

    // Hardcoded values as requested
    val inspectionId = id ?: 0L
    val documentType = DocumentType.LAPORAN
    val inspectionType = InspectionType.PTP
    val subInspectionType = SubInspectionType.Motor_Diesel

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
        ownerName = generalData.ownerName,
        ownerAddress = generalData.ownerAddress,
        usageLocation = generalData.unitLocation,
        addressUsageLocation = generalData.userAddressInCharge,
        driveType = generalData.driveType,
        serialNumber = generalData.serialNumberUnitNumber,
        permitNumber = generalData.usagePermitNumber,
        capacity = generalData.capacityRpm,
        manufacturer = ManufacturerDomain(
            name = generalData.manufacturer,
            brandOrType = generalData.brandType,
            year = generalData.locationAndYearOfManufacture
        ),
        createdAt = currentTime,
        speed = "",
        floorServed = "",
        reportDate = "",
        nextInspectionDate = "",
        inspectorName = "",
        status = "",
        isSynced = false,
        isEdited = isEdited
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

    fun addConditionResultCheckItem(category: String, itemName: String, value: DieselMotorConditionResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = value.isGood,
                result = value.remarks
            )
        )
    }

    fun addTestResultCheckItem(category: String, itemName: String, value: DieselMotorTestResult) {
        checkItems.add(
            InspectionCheckItemDomain(
                inspectionId = inspectionId,
                category = category,
                itemName = itemName,
                status = false, // Status is not used in DieselMotorTestResult
                result = "${value.result}#${value.remarks}" // Combine result and remarks
            )
        )
    }

    // 2. Map remaining General Data to CheckItems
    addCheckItem("general_data", "userInCharge", generalData.userInCharge)
    addCheckItem("general_data", "subcontractorPersonInCharge", generalData.subcontractorPersonInCharge)
    addCheckItem("general_data", "intendedUse", generalData.intendedUse)
    addCheckItem("general_data", "pjk3SkpNo", generalData.pjk3SkpNo)
    addCheckItem("general_data", "ak3SkpNo", generalData.ak3SkpNo)
    addCheckItem("general_data", "classification", generalData.classification)
    addCheckItem("general_data", "operatorName", generalData.operatorName)
    addCheckItem("general_data", "equipmentHistory", generalData.equipmentHistory)

    // 3. Map all Technical Data to CheckItems
    val techCategory = "technical_data"
    technicalData.dieselMotor.let { motor ->
        addCheckItem(techCategory, "motor_brandModel", motor.brandModel)
        addCheckItem(techCategory, "motor_manufacturer", motor.manufacturer)
        addCheckItem(techCategory, "motor_classification", motor.classification)
        addCheckItem(techCategory, "motor_serialNumber", motor.serialNumber)
        addCheckItem(techCategory, "motor_powerRpm", motor.powerRpm)
        addCheckItem(techCategory, "motor_startingPower", motor.startingPower)
        addCheckItem(techCategory, "motor_cylinderCount", motor.cylinderCount)
    }
    technicalData.generator.let { gen ->
        addCheckItem(techCategory, "gen_brandType", gen.brandType)
        addCheckItem(techCategory, "gen_manufacturer", gen.manufacturer)
        addCheckItem(techCategory, "gen_serialNumber", gen.serialNumber)
        addCheckItem(techCategory, "gen_power", gen.power)
        addCheckItem(techCategory, "gen_frequency", gen.frequency)
        addCheckItem(techCategory, "gen_rpm", gen.rpm)
        addCheckItem(techCategory, "gen_voltage", gen.voltage)
        addCheckItem(techCategory, "gen_powerFactor", gen.powerFactor)
        addCheckItem(techCategory, "gen_current", gen.current)
    }

    // 4. Map all Visual Inspection to CheckItems
    val visualCategory = "visual_inspection"
    val v = visualInspection
    addConditionResultCheckItem(visualCategory, "baseConstructionFoundation", v.baseConstructionFoundation)
    addConditionResultCheckItem(visualCategory, "baseConstructionDieselHousing", v.baseConstructionDieselHousing)
    addConditionResultCheckItem(visualCategory, "baseConstructionSupport", v.baseConstructionSupport)
    addConditionResultCheckItem(visualCategory, "baseConstructionAnchorBolt", v.baseConstructionAnchorBolt)
    addConditionResultCheckItem(visualCategory, "structureDailyTank", v.structureDailyTank)
    addConditionResultCheckItem(visualCategory, "structureMuffler", v.structureMuffler)
    addConditionResultCheckItem(visualCategory, "structureAirVessel", v.structureAirVessel)
    addConditionResultCheckItem(visualCategory, "structurePanel", v.structurePanel)
    addConditionResultCheckItem(visualCategory, "lubeSystemOil", v.lubeSystemOil)
    addConditionResultCheckItem(visualCategory, "lubeSystemOilStrainer", v.lubeSystemOilStrainer)
    addConditionResultCheckItem(visualCategory, "lubeSystemOilCooler", v.lubeSystemOilCooler)
    addConditionResultCheckItem(visualCategory, "lubeSystemOilFilter", v.lubeSystemOilFilter)
    addConditionResultCheckItem(visualCategory, "lubeSystemByPassFilter", v.lubeSystemByPassFilter)
    addConditionResultCheckItem(visualCategory, "lubeSystemSafetyValve", v.lubeSystemSafetyValve)
    addConditionResultCheckItem(visualCategory, "lubeSystemPacking", v.lubeSystemPacking)
    addConditionResultCheckItem(visualCategory, "fuelSystemDailyTank", v.fuelSystemDailyTank)
    addConditionResultCheckItem(visualCategory, "fuelSystemFuelInjector", v.fuelSystemFuelInjector)
    addConditionResultCheckItem(visualCategory, "fuelSystemConnections", v.fuelSystemConnections)
    addConditionResultCheckItem(visualCategory, "fuelSystemFloatTank", v.fuelSystemFloatTank)
    addConditionResultCheckItem(visualCategory, "fuelSystemFuelFilter", v.fuelSystemFuelFilter)
    addConditionResultCheckItem(visualCategory, "fuelSystemFuelInjectorPump", v.fuelSystemFuelInjectorPump)
    addConditionResultCheckItem(visualCategory, "fuelSystemMagneticScreen", v.fuelSystemMagneticScreen)
    addConditionResultCheckItem(visualCategory, "fuelSystemGovernor", v.fuelSystemGovernor)
    addConditionResultCheckItem(visualCategory, "fuelSystemThrottleShaft", v.fuelSystemThrottleShaft)
    addConditionResultCheckItem(visualCategory, "fuelSystemRegulator", v.fuelSystemRegulator)
    addConditionResultCheckItem(visualCategory, "fuelSystemShutOffValve", v.fuelSystemShutOffValve)
    addConditionResultCheckItem(visualCategory, "startingSystemFeedPump", v.startingSystemFeedPump)
    addConditionResultCheckItem(visualCategory, "startingSystemFuelValve", v.startingSystemFuelValve)
    addConditionResultCheckItem(visualCategory, "startingSystemPrimingPump", v.startingSystemPrimingPump)
    addConditionResultCheckItem(visualCategory, "startingSystemHeaterPlug", v.startingSystemHeaterPlug)
    addConditionResultCheckItem(visualCategory, "startingSystemHeaterSwitch", v.startingSystemHeaterSwitch)
    addConditionResultCheckItem(visualCategory, "startingSystemPreHeater", v.startingSystemPreHeater)
    addConditionResultCheckItem(visualCategory, "startingSystemWaterSignal", v.startingSystemWaterSignal)
    addConditionResultCheckItem(visualCategory, "startingSystemSwitch", v.startingSystemSwitch)
    addConditionResultCheckItem(visualCategory, "startingSystemBatteryPoles", v.startingSystemBatteryPoles)
    addConditionResultCheckItem(visualCategory, "startingSystemThermostartTank", v.startingSystemThermostartTank)
    addConditionResultCheckItem(visualCategory, "startingSystemThermostart", v.startingSystemThermostart)
    addConditionResultCheckItem(visualCategory, "startingSystemHeaterSignal", v.startingSystemHeaterSignal)
    addConditionResultCheckItem(visualCategory, "startingSystemThermostartSwitch", v.startingSystemThermostartSwitch)
    addConditionResultCheckItem(visualCategory, "startingSystemGlowPlug", v.startingSystemGlowPlug)
    addConditionResultCheckItem(visualCategory, "startingSystemSpeedSensor", v.startingSystemSpeedSensor)
    addConditionResultCheckItem(visualCategory, "startingSystemServiceMeter", v.startingSystemServiceMeter)
    addConditionResultCheckItem(visualCategory, "startingSystemTempSensor", v.startingSystemTempSensor)
    addConditionResultCheckItem(visualCategory, "startingSystemMotor", v.startingSystemMotor)
    addConditionResultCheckItem(visualCategory, "coolingSystemWater", v.coolingSystemWater)
    addConditionResultCheckItem(visualCategory, "coolingSystemBolts", v.coolingSystemBolts)
    addConditionResultCheckItem(visualCategory, "coolingSystemClamps", v.coolingSystemClamps)
    addConditionResultCheckItem(visualCategory, "coolingSystemRadiator", v.coolingSystemRadiator)
    addConditionResultCheckItem(visualCategory, "coolingSystemThermostat", v.coolingSystemThermostat)
    addConditionResultCheckItem(visualCategory, "coolingSystemFan", v.coolingSystemFan)
    addConditionResultCheckItem(visualCategory, "coolingSystemFanGuard", v.coolingSystemFanGuard)
    addConditionResultCheckItem(visualCategory, "coolingSystemFanRotation", v.coolingSystemFanRotation)
    addConditionResultCheckItem(visualCategory, "coolingSystemBearing", v.coolingSystemBearing)
    addConditionResultCheckItem(visualCategory, "airCirculationPreCleaner", v.airCirculationPreCleaner)
    addConditionResultCheckItem(visualCategory, "airCirculationDustIndicator", v.airCirculationDustIndicator)
    addConditionResultCheckItem(visualCategory, "airCirculationAirCleaner", v.airCirculationAirCleaner)
    addConditionResultCheckItem(visualCategory, "airCirculationTurboCharger", v.airCirculationTurboCharger)
    addConditionResultCheckItem(visualCategory, "airCirculationClamps", v.airCirculationClamps)
    addConditionResultCheckItem(visualCategory, "airCirculationAfterCooler", v.airCirculationAfterCooler)
    addConditionResultCheckItem(visualCategory, "airCirculationMuffler", v.airCirculationMuffler)
    addConditionResultCheckItem(visualCategory, "airCirculationSilencer", v.airCirculationSilencer)
    addConditionResultCheckItem(visualCategory, "airCirculationHeatDamper", v.airCirculationHeatDamper)
    addConditionResultCheckItem(visualCategory, "airCirculationBolts", v.airCirculationBolts)
    addConditionResultCheckItem(visualCategory, "mainPartsDamperBolts", v.mainPartsDamperBolts)
    addConditionResultCheckItem(visualCategory, "mainPartsSupport", v.mainPartsSupport)
    addConditionResultCheckItem(visualCategory, "mainPartsFlyWheelHousing", v.mainPartsFlyWheelHousing)
    addConditionResultCheckItem(visualCategory, "mainPartsFlyWheel", v.mainPartsFlyWheel)
    addConditionResultCheckItem(visualCategory, "mainPartsVibrationDamper", v.mainPartsVibrationDamper)
    addConditionResultCheckItem(visualCategory, "mainPartsBeltAndPulley", v.mainPartsBeltAndPulley)
    addConditionResultCheckItem(visualCategory, "mainPartsCrankshaft", v.mainPartsCrankshaft)
    addConditionResultCheckItem(visualCategory, "generatorTerminalConnection", v.generatorTerminalConnection)
    addConditionResultCheckItem(visualCategory, "generatorCableToPanel", v.generatorCableToPanel)
    addConditionResultCheckItem(visualCategory, "generatorPanelBoard", v.generatorPanelBoard)
    addConditionResultCheckItem(visualCategory, "generatorAmpereMeter", v.generatorAmpereMeter)
    addConditionResultCheckItem(visualCategory, "generatorVoltMeter", v.generatorVoltMeter)
    addConditionResultCheckItem(visualCategory, "generatorFrequencyMeter", v.generatorFrequencyMeter)
    addConditionResultCheckItem(visualCategory, "generatorCircuitBreaker", v.generatorCircuitBreaker)
    addConditionResultCheckItem(visualCategory, "generatorOnOffSwitch", v.generatorOnOffSwitch)
    addConditionResultCheckItem(visualCategory, "transmissionGear", v.transmissionGear)
    addConditionResultCheckItem(visualCategory, "transmissionBelt", v.transmissionBelt)
    addConditionResultCheckItem(visualCategory, "transmissionChain", v.transmissionChain)
    addConditionResultCheckItem(visualCategory, "mdpCableConnection", v.mdpCableConnection)
    addConditionResultCheckItem(visualCategory, "mdpCondition", v.mdpCondition)
    addConditionResultCheckItem(visualCategory, "mdpAmpereMeter", v.mdpAmpereMeter)
    addConditionResultCheckItem(visualCategory, "mdpVoltMeter", v.mdpVoltMeter)
    addConditionResultCheckItem(visualCategory, "mdpMainCircuitBreaker", v.mdpMainCircuitBreaker)
    addConditionResultCheckItem(visualCategory, "safetyGrounding", v.safetyGrounding)
    addConditionResultCheckItem(visualCategory, "safetyLightningArrester", v.safetyLightningArrester)
    addConditionResultCheckItem(visualCategory, "safetyEmergencyStop", v.safetyEmergencyStop)
    addConditionResultCheckItem(visualCategory, "safetyGovernor", v.safetyGovernor)
    addConditionResultCheckItem(visualCategory, "safetyThermostat", v.safetyThermostat)
    addConditionResultCheckItem(visualCategory, "safetyWaterSignal", v.safetyWaterSignal)
    addConditionResultCheckItem(visualCategory, "safetyFanGuard", v.safetyFanGuard)
    addConditionResultCheckItem(visualCategory, "safetySilencer", v.safetySilencer)
    addConditionResultCheckItem(visualCategory, "safetyVibrationDamper", v.safetyVibrationDamper)
    addConditionResultCheckItem(visualCategory, "safetyCircuitBreaker", v.safetyCircuitBreaker)
    addConditionResultCheckItem(visualCategory, "safetyAvr", v.safetyAvr)

    // 5. Map Testing and Measurement to CheckItems
    val testCategory = "testing"
    testing.ndtTests.let { ndt ->
        addTestResultCheckItem(testCategory, "ndt_shaftRpm", ndt.shaftRpm)
        addTestResultCheckItem(testCategory, "ndt_weldJoint", ndt.weldJoint)
        addTestResultCheckItem(testCategory, "ndt_noise", ndt.noise)
        addTestResultCheckItem(testCategory, "ndt_lighting", ndt.lighting)
        addTestResultCheckItem(testCategory, "ndt_loadTest", ndt.loadTest)
    }
    testing.safetyDeviceTests.let { safety ->
        addTestResultCheckItem(testCategory, "safety_governor", safety.governor)
        addTestResultCheckItem(testCategory, "safety_emergencyStop", safety.emergencyStop)
        addTestResultCheckItem(testCategory, "safety_grounding", safety.grounding)
        addTestResultCheckItem(testCategory, "safety_panelIndicators", safety.panelIndicators)
        addTestResultCheckItem(testCategory, "safety_pressureGauge", safety.pressureGauge)
        addTestResultCheckItem(testCategory, "safety_temperatureIndicator", safety.temperatureIndicator)
        addTestResultCheckItem(testCategory, "safety_waterIndicator", safety.waterIndicator)
        addTestResultCheckItem(testCategory, "safety_safetyValves", safety.safetyValves)
        addTestResultCheckItem(testCategory, "safety_radiator", safety.radiator)
    }
    testing.electricalMeasurements.let { elec ->
        addCheckItem(testCategory, "elec_panel_ka", elec.panelControl.ka)
        addCheckItem(testCategory, "elec_panel_voltRS", elec.panelControl.voltageRS)
        addCheckItem(testCategory, "elec_panel_voltRT", elec.panelControl.voltageRT)
        addCheckItem(testCategory, "elec_panel_voltST", elec.panelControl.voltageST)
        addCheckItem(testCategory, "elec_panel_voltRN", elec.panelControl.voltageRN)
        addCheckItem(testCategory, "elec_panel_voltRG", elec.panelControl.voltageRG)
        addCheckItem(testCategory, "elec_panel_voltNG", elec.panelControl.voltageNG)
        addCheckItem(testCategory, "elec_power_freq", elec.powerInfo.frequency)
        addCheckItem(testCategory, "elec_power_cosQ", elec.powerInfo.cosQ)
        addCheckItem(testCategory, "elec_power_ampR", elec.powerInfo.ampereR)
        addCheckItem(testCategory, "elec_power_ampS", elec.powerInfo.ampereS)
        addCheckItem(testCategory, "elec_power_ampT", elec.powerInfo.ampereT)
        addCheckItem(testCategory, "elec_power_remarks", elec.powerInfo.remarks)
    }

    // 6. Map MCB Calculation to CheckItems
    val mcbCategory = "mcb_calculation"
    addCheckItem(mcbCategory, "known_phase", mcb.known.phase)
    addCheckItem(mcbCategory, "known_voltage", mcb.known.voltage)
    addCheckItem(mcbCategory, "known_cosQ", mcb.known.cosQ)
    addCheckItem(mcbCategory, "known_powerKva", mcb.known.generatorPowerKva)
    addCheckItem(mcbCategory, "known_powerKw", mcb.known.generatorPowerKw)
    addCheckItem(mcbCategory, "calc_formula", mcb.calculation.formula)
    addCheckItem(mcbCategory, "calc_resultA", mcb.calculation.resultA)
    addCheckItem(mcbCategory, "calc_requiredAmps", mcb.calculation.requiredAmps)
    addCheckItem(mcbCategory, "conclusion", mcb.conclusion)

    // 7. Petakan Noise dan Lighting ke CheckItems
    addCheckItem("noise_measurement", "pointA_result", noise.pointA.result)
    addCheckItem("noise_measurement", "pointA_analysis", noise.pointA.analysis)
    addCheckItem("noise_measurement", "pointB_result", noise.pointB.result)
    addCheckItem("noise_measurement", "pointB_analysis", noise.pointB.analysis)
    addCheckItem("noise_measurement", "pointC_result", noise.pointC.result)
    addCheckItem("noise_measurement", "pointC_analysis", noise.pointC.analysis)
    addCheckItem("noise_measurement", "pointD_result", noise.pointD.result)
    addCheckItem("noise_measurement", "pointD_analysis", noise.pointD.analysis)

    addCheckItem("lighting_measurement", "pointA_result", lighting.pointA.result)
    addCheckItem("lighting_measurement", "pointA_analysis", lighting.pointA.analysis)
    addCheckItem("lighting_measurement", "pointB_result", lighting.pointB.result)
    addCheckItem("lighting_measurement", "pointB_analysis", lighting.pointB.analysis)
    addCheckItem("lighting_measurement", "pointC_result", lighting.pointC.result)
    addCheckItem("lighting_measurement", "pointC_analysis", lighting.pointC.analysis)
    addCheckItem("lighting_measurement", "pointD_result", lighting.pointD.result)
    addCheckItem("lighting_measurement", "pointD_analysis", lighting.pointD.analysis)

    // 8. Map Conclusion to Findings
    val findings = mutableListOf<InspectionFindingDomain>()
    conclusion.summary.forEach {
        findings.add(
            InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING)
        )
    }
    conclusion.requirements.forEach {
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

fun InspectionWithDetailsDomain.toDieselMotorUiState(): DieselMotorUiState {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getCheckItemValue(category: String, itemName: String): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    fun getCheckItemValueForIndexed(prefix: String, index: Int, itemName: String): String {
        val category = "${prefix}_${index}"
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: ""
    }

    fun getConditionResult(category: String, itemName: String): DieselMotorConditionResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return DieselMotorConditionResult(
            isGood = item?.status ?: false,
            remarks = item?.result ?: ""
        )
    }

    fun getTestResult(category: String, itemName: String): DieselMotorTestResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        val parts = (item?.result ?: "").split("#")
        return DieselMotorTestResult(
            result = parts.getOrNull(0) ?: "",
            remarks = parts.getOrNull(1) ?: ""
        )
    }

    val generalData = DieselMotorGeneralData(
        ownerName = inspection.ownerName ?: "",
        ownerAddress = inspection.ownerAddress ?: "",
        userAddressInCharge = inspection.addressUsageLocation ?: "",
        unitLocation = inspection.usageLocation ?: "",
        driveType = inspection.driveType ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        brandType = inspection.manufacturer?.brandOrType ?: "",
        locationAndYearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = inspection.serialNumber ?: "",
        usagePermitNumber = inspection.permitNumber ?: "",
        capacityRpm = inspection.capacity ?: "",
        userInCharge = getCheckItemValue("general_data", "userInCharge"),
        subcontractorPersonInCharge = getCheckItemValue("general_data", "subcontractorPersonInCharge"),
        intendedUse = getCheckItemValue("general_data", "intendedUse"),
        pjk3SkpNo = getCheckItemValue("general_data", "pjk3SkpNo"),
        ak3SkpNo = getCheckItemValue("general_data", "ak3SkpNo"),
        classification = getCheckItemValue("general_data", "classification"),
        operatorName = getCheckItemValue("general_data", "operatorName"),
        equipmentHistory = getCheckItemValue("general_data", "equipmentHistory")
    )

    val techCategory = "technical_data"
    val technicalData = DieselMotorTechnicalData(
        dieselMotor = DieselMotorInfo(
            brandModel = getCheckItemValue(techCategory, "motor_brandModel"),
            manufacturer = getCheckItemValue(techCategory, "motor_manufacturer"),
            classification = getCheckItemValue(techCategory, "motor_classification"),
            serialNumber = getCheckItemValue(techCategory, "motor_serialNumber"),
            powerRpm = getCheckItemValue(techCategory, "motor_powerRpm"),
            startingPower = getCheckItemValue(techCategory, "motor_startingPower"),
            cylinderCount = getCheckItemValue(techCategory, "motor_cylinderCount")
        ),
        generator = DieselMotorGeneratorInfo(
            brandType = getCheckItemValue(techCategory, "gen_brandType"),
            manufacturer = getCheckItemValue(techCategory, "gen_manufacturer"),
            serialNumber = getCheckItemValue(techCategory, "gen_serialNumber"),
            power = getCheckItemValue(techCategory, "gen_power"),
            frequency = getCheckItemValue(techCategory, "gen_frequency"),
            rpm = getCheckItemValue(techCategory, "gen_rpm"),
            voltage = getCheckItemValue(techCategory, "gen_voltage"),
            powerFactor = getCheckItemValue(techCategory, "gen_powerFactor"),
            current = getCheckItemValue(techCategory, "gen_current")
        )
    )

    val visualCategory = "visual_inspection"
    val visualInspection = DieselMotorVisualInspection(
        baseConstructionFoundation = getConditionResult(visualCategory, "baseConstructionFoundation"),
        baseConstructionDieselHousing = getConditionResult(visualCategory, "baseConstructionDieselHousing"),
        baseConstructionSupport = getConditionResult(visualCategory, "baseConstructionSupport"),
        baseConstructionAnchorBolt = getConditionResult(visualCategory, "baseConstructionAnchorBolt"),
        structureDailyTank = getConditionResult(visualCategory, "structureDailyTank"),
        structureMuffler = getConditionResult(visualCategory, "structureMuffler"),
        structureAirVessel = getConditionResult(visualCategory, "structureAirVessel"),
        structurePanel = getConditionResult(visualCategory, "structurePanel"),
        lubeSystemOil = getConditionResult(visualCategory, "lubeSystemOil"),
        lubeSystemOilStrainer = getConditionResult(visualCategory, "lubeSystemOilStrainer"),
        lubeSystemOilCooler = getConditionResult(visualCategory, "lubeSystemOilCooler"),
        lubeSystemOilFilter = getConditionResult(visualCategory, "lubeSystemOilFilter"),
        lubeSystemByPassFilter = getConditionResult(visualCategory, "lubeSystemByPassFilter"),
        lubeSystemSafetyValve = getConditionResult(visualCategory, "lubeSystemSafetyValve"),
        lubeSystemPacking = getConditionResult(visualCategory, "lubeSystemPacking"),
        fuelSystemDailyTank = getConditionResult(visualCategory, "fuelSystemDailyTank"),
        fuelSystemFuelInjector = getConditionResult(visualCategory, "fuelSystemFuelInjector"),
        fuelSystemConnections = getConditionResult(visualCategory, "fuelSystemConnections"),
        fuelSystemFloatTank = getConditionResult(visualCategory, "fuelSystemFloatTank"),
        fuelSystemFuelFilter = getConditionResult(visualCategory, "fuelSystemFuelFilter"),
        fuelSystemFuelInjectorPump = getConditionResult(visualCategory, "fuelSystemFuelInjectorPump"),
        fuelSystemMagneticScreen = getConditionResult(visualCategory, "fuelSystemMagneticScreen"),
        fuelSystemGovernor = getConditionResult(visualCategory, "fuelSystemGovernor"),
        fuelSystemThrottleShaft = getConditionResult(visualCategory, "fuelSystemThrottleShaft"),
        fuelSystemRegulator = getConditionResult(visualCategory, "fuelSystemRegulator"),
        fuelSystemShutOffValve = getConditionResult(visualCategory, "fuelSystemShutOffValve"),
        startingSystemFeedPump = getConditionResult(visualCategory, "startingSystemFeedPump"),
        startingSystemFuelValve = getConditionResult(visualCategory, "startingSystemFuelValve"),
        startingSystemPrimingPump = getConditionResult(visualCategory, "startingSystemPrimingPump"),
        startingSystemHeaterPlug = getConditionResult(visualCategory, "startingSystemHeaterPlug"),
        startingSystemHeaterSwitch = getConditionResult(visualCategory, "startingSystemHeaterSwitch"),
        startingSystemPreHeater = getConditionResult(visualCategory, "startingSystemPreHeater"),
        startingSystemWaterSignal = getConditionResult(visualCategory, "startingSystemWaterSignal"),
        startingSystemSwitch = getConditionResult(visualCategory, "startingSystemSwitch"),
        startingSystemBatteryPoles = getConditionResult(visualCategory, "startingSystemBatteryPoles"),
        startingSystemThermostartTank = getConditionResult(visualCategory, "startingSystemThermostartTank"),
        startingSystemThermostart = getConditionResult(visualCategory, "startingSystemThermostart"),
        startingSystemHeaterSignal = getConditionResult(visualCategory, "startingSystemHeaterSignal"),
        startingSystemThermostartSwitch = getConditionResult(visualCategory, "startingSystemThermostartSwitch"),
        startingSystemGlowPlug = getConditionResult(visualCategory, "startingSystemGlowPlug"),
        startingSystemSpeedSensor = getConditionResult(visualCategory, "startingSystemSpeedSensor"),
        startingSystemServiceMeter = getConditionResult(visualCategory, "startingSystemServiceMeter"),
        startingSystemTempSensor = getConditionResult(visualCategory, "startingSystemTempSensor"),
        startingSystemMotor = getConditionResult(visualCategory, "startingSystemMotor"),
        coolingSystemWater = getConditionResult(visualCategory, "coolingSystemWater"),
        coolingSystemBolts = getConditionResult(visualCategory, "coolingSystemBolts"),
        coolingSystemClamps = getConditionResult(visualCategory, "coolingSystemClamps"),
        coolingSystemRadiator = getConditionResult(visualCategory, "coolingSystemRadiator"),
        coolingSystemThermostat = getConditionResult(visualCategory, "coolingSystemThermostat"),
        coolingSystemFan = getConditionResult(visualCategory, "coolingSystemFan"),
        coolingSystemFanGuard = getConditionResult(visualCategory, "coolingSystemFanGuard"),
        coolingSystemFanRotation = getConditionResult(visualCategory, "coolingSystemFanRotation"),
        coolingSystemBearing = getConditionResult(visualCategory, "coolingSystemBearing"),
        airCirculationPreCleaner = getConditionResult(visualCategory, "airCirculationPreCleaner"),
        airCirculationDustIndicator = getConditionResult(visualCategory, "airCirculationDustIndicator"),
        airCirculationAirCleaner = getConditionResult(visualCategory, "airCirculationAirCleaner"),
        airCirculationTurboCharger = getConditionResult(visualCategory, "airCirculationTurboCharger"),
        airCirculationClamps = getConditionResult(visualCategory, "airCirculationClamps"),
        airCirculationAfterCooler = getConditionResult(visualCategory, "airCirculationAfterCooler"),
        airCirculationMuffler = getConditionResult(visualCategory, "airCirculationMuffler"),
        airCirculationSilencer = getConditionResult(visualCategory, "airCirculationSilencer"),
        airCirculationHeatDamper = getConditionResult(visualCategory, "airCirculationHeatDamper"),
        airCirculationBolts = getConditionResult(visualCategory, "airCirculationBolts"),
        mainPartsDamperBolts = getConditionResult(visualCategory, "mainPartsDamperBolts"),
        mainPartsSupport = getConditionResult(visualCategory, "mainPartsSupport"),
        mainPartsFlyWheelHousing = getConditionResult(visualCategory, "mainPartsFlyWheelHousing"),
        mainPartsFlyWheel = getConditionResult(visualCategory, "mainPartsFlyWheel"),
        mainPartsVibrationDamper = getConditionResult(visualCategory, "mainPartsVibrationDamper"),
        mainPartsBeltAndPulley = getConditionResult(visualCategory, "mainPartsBeltAndPulley"),
        mainPartsCrankshaft = getConditionResult(visualCategory, "mainPartsCrankshaft"),
        generatorTerminalConnection = getConditionResult(visualCategory, "generatorTerminalConnection"),
        generatorCableToPanel = getConditionResult(visualCategory, "generatorCableToPanel"),
        generatorPanelBoard = getConditionResult(visualCategory, "generatorPanelBoard"),
        generatorAmpereMeter = getConditionResult(visualCategory, "generatorAmpereMeter"),
        generatorVoltMeter = getConditionResult(visualCategory, "generatorVoltMeter"),
        generatorFrequencyMeter = getConditionResult(visualCategory, "generatorFrequencyMeter"),
        generatorCircuitBreaker = getConditionResult(visualCategory, "generatorCircuitBreaker"),
        generatorOnOffSwitch = getConditionResult(visualCategory, "generatorOnOffSwitch"),
        transmissionGear = getConditionResult(visualCategory, "transmissionGear"),
        transmissionBelt = getConditionResult(visualCategory, "transmissionBelt"),
        transmissionChain = getConditionResult(visualCategory, "transmissionChain"),
        mdpCableConnection = getConditionResult(visualCategory, "mdpCableConnection"),
        mdpCondition = getConditionResult(visualCategory, "mdpCondition"),
        mdpAmpereMeter = getConditionResult(visualCategory, "mdpAmpereMeter"),
        mdpVoltMeter = getConditionResult(visualCategory, "mdpVoltMeter"),
        mdpMainCircuitBreaker = getConditionResult(visualCategory, "mdpMainCircuitBreaker"),
        safetyGrounding = getConditionResult(visualCategory, "safetyGrounding"),
        safetyLightningArrester = getConditionResult(visualCategory, "safetyLightningArrester"),
        safetyEmergencyStop = getConditionResult(visualCategory, "safetyEmergencyStop"),
        safetyGovernor = getConditionResult(visualCategory, "safetyGovernor"),
        safetyThermostat = getConditionResult(visualCategory, "safetyThermostat"),
        safetyWaterSignal = getConditionResult(visualCategory, "safetyWaterSignal"),
        safetyFanGuard = getConditionResult(visualCategory, "safetyFanGuard"),
        safetySilencer = getConditionResult(visualCategory, "safetySilencer"),
        safetyVibrationDamper = getConditionResult(visualCategory, "safetyVibrationDamper"),
        safetyCircuitBreaker = getConditionResult(visualCategory, "safetyCircuitBreaker"),
        safetyAvr = getConditionResult(visualCategory, "safetyAvr")
    )

    val testCategory = "testing"
    val testingAndMeasurement = DieselMotorTestingAndMeasurement(
        ndtTests = DieselMotorNdtTests(
            shaftRpm = getTestResult(testCategory, "ndt_shaftRpm"),
            weldJoint = getTestResult(testCategory, "ndt_weldJoint"),
            noise = getTestResult(testCategory, "ndt_noise"),
            lighting = getTestResult(testCategory, "ndt_lighting"),
            loadTest = getTestResult(testCategory, "ndt_loadTest")
        ),
        safetyDeviceTests = DieselMotorSafetyDeviceTests(
            governor = getTestResult(testCategory, "safety_governor"),
            emergencyStop = getTestResult(testCategory, "safety_emergencyStop"),
            grounding = getTestResult(testCategory, "safety_grounding"),
            panelIndicators = getTestResult(testCategory, "safety_panelIndicators"),
            pressureGauge = getTestResult(testCategory, "safety_pressureGauge"),
            temperatureIndicator = getTestResult(testCategory, "safety_temperatureIndicator"),
            waterIndicator = getTestResult(testCategory, "safety_waterIndicator"),
            safetyValves = getTestResult(testCategory, "safety_safetyValves"),
            radiator = getTestResult(testCategory, "safety_radiator")
        ),
        electricalMeasurements = DieselMotorElectricalMeasurements(
            panelControl = DieselMotorPanelControl(
                ka = getCheckItemValue(testCategory, "elec_panel_ka"),
                voltageRS = getCheckItemValue(testCategory, "elec_panel_voltRS"),
                voltageRT = getCheckItemValue(testCategory, "elec_panel_voltRT"),
                voltageST = getCheckItemValue(testCategory, "elec_panel_voltST"),
                voltageRN = getCheckItemValue(testCategory, "elec_panel_voltRN"),
                voltageRG = getCheckItemValue(testCategory, "elec_panel_voltRG"),
                voltageNG = getCheckItemValue(testCategory, "elec_panel_voltNG")
            ),
            powerInfo = DieselMotorPowerInfo(
                frequency = getCheckItemValue(testCategory, "elec_power_freq"),
                cosQ = getCheckItemValue(testCategory, "elec_power_cosQ"),
                ampereR = getCheckItemValue(testCategory, "elec_power_ampR"),
                ampereS = getCheckItemValue(testCategory, "elec_power_ampS"),
                ampereT = getCheckItemValue(testCategory, "elec_power_ampT"),
                remarks = getCheckItemValue(testCategory, "elec_power_remarks")
            )
        )
    )

    val mcbCategory = "mcb_calculation"
    val mcbCalculation = DieselMotorMcbCalculation(
        known = DieselMotorMcbKnownData(
            phase = getCheckItemValue(mcbCategory, "known_phase"),
            voltage = getCheckItemValue(mcbCategory, "known_voltage"),
            cosQ = getCheckItemValue(mcbCategory, "known_cosQ"),
            generatorPowerKva = getCheckItemValue(mcbCategory, "known_powerKva"),
            generatorPowerKw = getCheckItemValue(mcbCategory, "known_powerKw")
        ),
        calculation = DieselMotorMcbCalculationResult(
            formula = getCheckItemValue(mcbCategory, "calc_formula"),
            resultA = getCheckItemValue(mcbCategory, "calc_resultA"),
            requiredAmps = getCheckItemValue(mcbCategory, "calc_requiredAmps")
        ),
        conclusion = getCheckItemValue(mcbCategory, "conclusion")
    )

    val noiseMeasurement = DieselMotorNoiseMeasurement(
        pointA = DieselMotorMeasurementPoint(
            result = getCheckItemValue("noise_measurement", "pointA_result"),
            analysis = getCheckItemValue("noise_measurement", "pointA_analysis")
        ),
        pointB = DieselMotorMeasurementPoint(
            result = getCheckItemValue("noise_measurement", "pointB_result"),
            analysis = getCheckItemValue("noise_measurement", "pointB_analysis")
        ),
        pointC = DieselMotorMeasurementPoint(
            result = getCheckItemValue("noise_measurement", "pointC_result"),
            analysis = getCheckItemValue("noise_measurement", "pointC_analysis")
        ),
        pointD = DieselMotorMeasurementPoint(
            result = getCheckItemValue("noise_measurement", "pointD_result"),
            analysis = getCheckItemValue("noise_measurement", "pointD_analysis")
        )
    )

    val lightingMeasurement = DieselMotorLightingMeasurement(
        pointA = DieselMotorMeasurementPoint(
            result = getCheckItemValue("lighting_measurement", "pointA_result"),
            analysis = getCheckItemValue("lighting_measurement", "pointA_analysis")
        ),
        pointB = DieselMotorMeasurementPoint(
            result = getCheckItemValue("lighting_measurement", "pointB_result"),
            analysis = getCheckItemValue("lighting_measurement", "pointB_analysis")
        ),
        pointC = DieselMotorMeasurementPoint(
            result = getCheckItemValue("lighting_measurement", "pointC_result"),
            analysis = getCheckItemValue("lighting_measurement", "pointC_analysis")
        ),
        pointD = DieselMotorMeasurementPoint(
            result = getCheckItemValue("lighting_measurement", "pointD_result"),
            analysis = getCheckItemValue("lighting_measurement", "pointD_analysis")
        )
    )

    val summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }
    val requirements = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }
    val conclusion = DieselMotorConclusion(
        summary = summary.toImmutableList(),
        requirements = requirements.toImmutableList()
    )

    val report = DieselMotorInspectionReport(
        extraId = inspection.extraId,
        moreExtraId = inspection.moreExtraId,
        equipmentType = inspection.equipmentType,
        examinationType = inspection.examinationType,
        generalData = generalData,
        technicalData = technicalData,
        visualInspection = visualInspection,
        testingAndMeasurement = testingAndMeasurement,
        mcbCalculation = mcbCalculation,
        noiseMeasurement = noiseMeasurement,
        lightingMeasurement = lightingMeasurement,
        conclusion = conclusion
    )

    return DieselMotorUiState(inspectionReport = report)
}