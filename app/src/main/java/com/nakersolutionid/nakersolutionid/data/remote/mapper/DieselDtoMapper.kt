package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselAmpere
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapFunctionalTests
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapVisualChecks
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBasicConstruction
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselAirCirculationSystem
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselCoolingSystem
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselElectricalComponents
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselFuelSystem
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselGenerator
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselGeneratorParts
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselLightingMeasurement
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselLubricationSystem
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselMainParts
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselMcbCalculation
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselMdp
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselMeasurementPoint
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselMotor
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselNdt
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselNoiseMeasurement
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselPanelControl
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselPowerInfo
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselRemarksResult
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselSafetyDevice
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselStartingAid
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselStatusResult
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselStructuralConstruction
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselTestSafetyDevice
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselTests
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselTransmission
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselVisualChecks
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselVoltage
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import kotlin.math.abs

// =================================================================================================
//                                  Domain -> DTO (Request)
// =================================================================================================

/**
 * Maps the domain layer's [InspectionWithDetailsDomain] to a [DieselReportRequest] for the API.
 * This function retrieves all detailed data from the domain model and packs it into the DTO.
 */
fun InspectionWithDetailsDomain.toDieselReportRequest(): DieselReportRequest {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    // Helper functions to extract data safely
    fun getCheckItemValue(category: String, itemName: String, default: String = ""): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: default
    }

    fun getCheckItemStatus(category: String, itemName: String, default: Boolean = false): Boolean {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.status ?: default
    }

    fun getStatusResult(category: String, itemName: String): DieselStatusResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        return DieselStatusResult(
            status = item?.status ?: false,
            result = item?.result ?: ""
        )
    }

    fun getRemarksResult(category: String, itemName: String): DieselRemarksResult {
        val item = checkItemsByCategory[category]?.find { it.itemName == itemName }
        val parts = (item?.result ?: "").split("#", limit = 2)
        return DieselRemarksResult(
            result = parts.getOrNull(0) ?: "",
            remarks = parts.getOrNull(1) ?: ""
        )
    }

    fun getMeasurementPoint(category: String, pointPrefix: String): DieselMeasurementPoint {
        return DieselMeasurementPoint(
            result = getCheckItemValue(category, "${pointPrefix}_result").toDoubleOrNull() ?: 0.0,
            status = getCheckItemValue(category, "${pointPrefix}_analysis")
        )
    }

    // --- Building DTO Components ---

    val generalData = DieselGeneralData(
        companyName = inspection.ownerName ?: "",
        companyLocation = inspection.ownerAddress ?: "",
        userInCharge = getCheckItemValue("general_data", "userInCharge"),
        userAddressInCharge = inspection.addressUsageLocation ?: "",
        subcontractorPersonInCharge = getCheckItemValue("general_data", "subcontractorPersonInCharge"),
        unitLocation = inspection.usageLocation ?: "",
        equipmentType = inspection.equipmentType,
        brandType = inspection.manufacturer?.brandOrType ?: "",
        serialNumberUnitNumber = inspection.serialNumber ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        locationAndYearOfManufacture = inspection.manufacturer?.year ?: "",
        capacityWorkingLoad = inspection.capacity ?: "",
        intendedUse = getCheckItemValue("general_data", "intendedUse"),
        pjk3SkpNo = getCheckItemValue("general_data", "pjk3SkpNo"),
        ak3SkpNo = getCheckItemValue("general_data", "ak3SkpNo"),
        portableOrStationer = getCheckItemValue("general_data", "classification"),
        usagePermitNumber = inspection.permitNumber ?: "",
        operatorName = getCheckItemValue("general_data", "operatorName"),
        equipmentHistory = getCheckItemValue("general_data", "equipmentHistory")
    )

    val techCategory = "technical_data"
    val technicalData = DieselTechnicalData(
        dieselMotor = DieselMotor(
            brandModel = getCheckItemValue(techCategory, "motor_brandModel"),
            manufacturer = getCheckItemValue(techCategory, "motor_manufacturer"),
            classification = getCheckItemValue(techCategory, "motor_classification"),
            serialNumber = getCheckItemValue(techCategory, "motor_serialNumber"),
            powerRpm = getCheckItemValue(techCategory, "motor_powerRpm"),
            startingPower = getCheckItemValue(techCategory, "motor_startingPower"),
            cylinderCount = getCheckItemValue(techCategory, "motor_cylinderCount")
        ),
        generator = DieselGenerator(
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
    val visualChecks = DieselVisualChecks(
        basicConstruction = DieselBasicConstruction(
            foundation = getStatusResult(visualCategory, "baseConstructionFoundation"),
            dieselHousing = getStatusResult(visualCategory, "baseConstructionDieselHousing"),
            support = getStatusResult(visualCategory, "baseConstructionSupport"),
            anchorBolt = getStatusResult(visualCategory, "baseConstructionAnchorBolt")
        ),
        structuralConstruction = DieselStructuralConstruction(
            dailyTank = getStatusResult(visualCategory, "structureDailyTank"),
            muffler = getStatusResult(visualCategory, "structureMuffler"),
            airVessel = getStatusResult(visualCategory, "structureAirVessel"),
            panel = getStatusResult(visualCategory, "structurePanel")
        ),
        lubricationSystem = DieselLubricationSystem(
            oil = getStatusResult(visualCategory, "lubeSystemOil"),
            oilStrainer = getStatusResult(visualCategory, "lubeSystemOilStrainer"),
            oilCooler = getStatusResult(visualCategory, "lubeSystemOilCooler"),
            oilFilter = getStatusResult(visualCategory, "lubeSystemOilFilter"),
            byPassFilter = getStatusResult(visualCategory, "lubeSystemByPassFilter"),
            safetyValve = getStatusResult(visualCategory, "lubeSystemSafetyValve"),
            packing = getStatusResult(visualCategory, "lubeSystemPacking")
        ),
        fuelSystem = DieselFuelSystem(
            dailyTank = getStatusResult(visualCategory, "fuelSystemDailyTank"),
            fuelInjector = getStatusResult(visualCategory, "fuelSystemFuelInjector"),
            connections = getStatusResult(visualCategory, "fuelSystemConnections"),
            floatTank = getStatusResult(visualCategory, "fuelSystemFloatTank"),
            fuelFilter = getStatusResult(visualCategory, "fuelSystemFuelFilter"),
            fuelPump = getStatusResult(visualCategory, "fuelSystemFuelInjectorPump"),
            magneticScreen = getStatusResult(visualCategory, "fuelSystemMagneticScreen"),
            governor = getStatusResult(visualCategory, "fuelSystemGovernor"),
            throttleShaft = getStatusResult(visualCategory, "fuelSystemThrottleShaft"),
            regulator = getStatusResult(visualCategory, "fuelSystemRegulator"),
            shutOffValve = getStatusResult(visualCategory, "fuelSystemShutOffValve")
        ),
        startingAid = DieselStartingAid(
            feedPump = getStatusResult(visualCategory, "startingSystemFeedPump"),
            fuelValve = getStatusResult(visualCategory, "startingSystemFuelValve"),
            primingPump = getStatusResult(visualCategory, "startingSystemPrimingPump"),
            heaterPlug = getStatusResult(visualCategory, "startingSystemHeaterPlug"),
            heaterSwitch = getStatusResult(visualCategory, "startingSystemHeaterSwitch"),
            preHeater = getStatusResult(visualCategory, "startingSystemPreHeater"),
            waterSignal = getStatusResult(visualCategory, "startingSystemWaterSignal"),
            startingSwitch = getStatusResult(visualCategory, "startingSystemSwitch"),
            batteryPoles = getStatusResult(visualCategory, "startingSystemBatteryPoles"),
            thermostartTank = getStatusResult(visualCategory, "startingSystemThermostartTank"),
            thermostart = getStatusResult(visualCategory, "startingSystemThermostart"),
            heaterSignal = getStatusResult(visualCategory, "startingSystemHeaterSignal"),
            thermostartSwitch = getStatusResult(visualCategory, "startingSystemThermostartSwitch"),
            glowPlug = getStatusResult(visualCategory, "startingSystemGlowPlug"),
            speedSensor = getStatusResult(visualCategory, "startingSystemSpeedSensor"),
            serviceMeter = getStatusResult(visualCategory, "startingSystemServiceMeter"),
            tempSensor = getStatusResult(visualCategory, "startingSystemTempSensor"),
            startingMotor = getStatusResult(visualCategory, "startingSystemMotor")
        ),
        coolingSystem = DieselCoolingSystem(
            coolingWater = getStatusResult(visualCategory, "coolingSystemWater"),
            bolts = getStatusResult(visualCategory, "coolingSystemBolts"),
            clamps = getStatusResult(visualCategory, "coolingSystemClamps"),
            radiator = getStatusResult(visualCategory, "coolingSystemRadiator"),
            thermostat = getStatusResult(visualCategory, "coolingSystemThermostat"),
            fan = getStatusResult(visualCategory, "coolingSystemFan"),
            fanGuard = getStatusResult(visualCategory, "coolingSystemFanGuard"),
            fanRotation = getStatusResult(visualCategory, "coolingSystemFanRotation"),
            bearing = getStatusResult(visualCategory, "coolingSystemBearing")
        ),
        airCirculationSystem = DieselAirCirculationSystem(
            preCleaner = getStatusResult(visualCategory, "airCirculationPreCleaner"),
            dustIndicator = getStatusResult(visualCategory, "airCirculationDustIndicator"),
            airCleaner = getStatusResult(visualCategory, "airCirculationAirCleaner"),
            turboCharger = getStatusResult(visualCategory, "airCirculationTurboCharger"),
            clamps = getStatusResult(visualCategory, "airCirculationClamps"),
            afterCooler = getStatusResult(visualCategory, "airCirculationAfterCooler"),
            muffler = getStatusResult(visualCategory, "airCirculationMuffler"),
            silencer = getStatusResult(visualCategory, "airCirculationSilencer"),
            heatDamper = getStatusResult(visualCategory, "airCirculationHeatDamper"),
            bolts = getStatusResult(visualCategory, "airCirculationBolts")
        ),
        mainParts = DieselMainParts(
            damperBolts = getStatusResult(visualCategory, "mainPartsDamperBolts"),
            support = getStatusResult(visualCategory, "mainPartsSupport"),
            flyWheelHousing = getStatusResult(visualCategory, "mainPartsFlyWheelHousing"),
            flyWheel = getStatusResult(visualCategory, "mainPartsFlyWheel"),
            vibrationDamper = getStatusResult(visualCategory, "mainPartsVibrationDamper"),
            beltAndPulley = getStatusResult(visualCategory, "mainPartsBeltAndPulley"),
            crankshaft = getStatusResult(visualCategory, "mainPartsCrankshaft")
        ),
        generatorParts = DieselGeneratorParts(
            terminal = getStatusResult(visualCategory, "generatorTerminalConnection"),
            cable = getStatusResult(visualCategory, "generatorCableToPanel"),
            panel = getStatusResult(visualCategory, "generatorPanelBoard"),
            ampereMeter = getStatusResult(visualCategory, "generatorAmpereMeter"),
            voltMeter = getStatusResult(visualCategory, "generatorVoltMeter"),
            frequencyMeter = getStatusResult(visualCategory, "generatorFrequencyMeter"),
            circuitBreaker = getStatusResult(visualCategory, "generatorCircuitBreaker"),
            onOffSwitch = getStatusResult(visualCategory, "generatorOnOffSwitch")
        ),
        transmission = DieselTransmission(
            gear = getStatusResult(visualCategory, "transmissionGear"),
            belt = getStatusResult(visualCategory, "transmissionBelt"),
            chain = getStatusResult(visualCategory, "transmissionChain")
        ),
        mdp = DieselMdp(
            cable = getStatusResult(visualCategory, "mdpCableConnection"),
            condition = getStatusResult(visualCategory, "mdpCondition"),
            ampereMeter = getStatusResult(visualCategory, "mdpAmpereMeter"),
            voltMeter = getStatusResult(visualCategory, "mdpVoltMeter"),
            mainCircuitBreaker = getStatusResult(visualCategory, "mdpMainCircuitBreaker")
        ),
        safetyDevice = DieselSafetyDevice(
            grounding = getStatusResult(visualCategory, "safetyGrounding"),
            lightningArrester = getStatusResult(visualCategory, "safetyLightningArrester"),
            emergencyStop = getStatusResult(visualCategory, "safetyEmergencyStop"),
            governor = getStatusResult(visualCategory, "safetyGovernor"),
            thermostat = getStatusResult(visualCategory, "safetyThermostat"),
            waterSignal = getStatusResult(visualCategory, "safetyWaterSignal"),
            fanGuard = getStatusResult(visualCategory, "safetyFanGuard"),
            silencer = getStatusResult(visualCategory, "safetySilencer"),
            vibrationDamper = getStatusResult(visualCategory, "safetyVibrationDamper"),
            circuitBreaker = getStatusResult(visualCategory, "safetyCircuitBreaker"),
            avr = getStatusResult(visualCategory, "safetyAvr")
        )
    )

    val testCategory = "testing"
    val tests = DieselTests(
        ndt = DieselNdt(
            shaftRpm = getRemarksResult(testCategory, "ndt_shaftRpm"),
            weldJoint = getRemarksResult(testCategory, "ndt_weldJoint"),
            noise = getRemarksResult(testCategory, "ndt_noise"),
            lighting = getRemarksResult(testCategory, "ndt_lighting"),
            loadTest = getRemarksResult(testCategory, "ndt_loadTest")
        ),
        safetyDevice = DieselTestSafetyDevice(
            governor = getRemarksResult(testCategory, "safety_governor"),
            emergencyStop = getRemarksResult(testCategory, "safety_emergencyStop"),
            grounding = getRemarksResult(testCategory, "safety_grounding"),
            indicatorPanel = getRemarksResult(testCategory, "safety_panelIndicators"),
            pressureGauge = getRemarksResult(testCategory, "safety_pressureGauge"),
            tempIndicator = getRemarksResult(testCategory, "safety_temperatureIndicator"),
            waterIndicator = getRemarksResult(testCategory, "safety_waterIndicator"),
            safetyValves = getRemarksResult(testCategory, "safety_safetyValves"),
            radiator = getRemarksResult(testCategory, "safety_radiator")
        )
    )

    val electricalComponents = DieselElectricalComponents(
        panelControl = DieselPanelControl(
            ka = getCheckItemValue(testCategory, "elec_panel_ka"),
            voltage = DieselVoltage(
                rs = getCheckItemValue(testCategory, "elec_panel_voltRS").toIntOrNull() ?: 0,
                rt = getCheckItemValue(testCategory, "elec_panel_voltRT").toIntOrNull() ?: 0,
                st = getCheckItemValue(testCategory, "elec_panel_voltST").toIntOrNull() ?: 0,
                rn = getCheckItemValue(testCategory, "elec_panel_voltRN").toIntOrNull() ?: 0,
                rg = getCheckItemValue(testCategory, "elec_panel_voltRG").toIntOrNull() ?: 0,
                ng = getCheckItemValue(testCategory, "elec_panel_voltNG").toIntOrNull() ?: 0
            ),
            powerInfo = DieselPowerInfo(
                frequency = getCheckItemValue(testCategory, "elec_power_freq"),
                cosQ = getCheckItemValue(testCategory, "elec_power_cosQ").toDoubleOrNull() ?: 0.0,
                ampere = DieselAmpere(
                    r = getCheckItemValue(testCategory, "elec_power_ampR").toIntOrNull() ?: 0,
                    s = getCheckItemValue(testCategory, "elec_power_ampS").toIntOrNull() ?: 0,
                    t = getCheckItemValue(testCategory, "elec_power_ampT").toIntOrNull() ?: 0
                ),
                result = getCheckItemValue(testCategory, "elec_power_remarks")
            )
        )
    )

    val mcbCategory = "mcb_calculation"
    val mcbCalculation = DieselMcbCalculation(
        phase = getCheckItemValue(mcbCategory, "known_phase").toIntOrNull() ?: 0,
        voltage = getCheckItemValue(mcbCategory, "known_voltage"),
        cosQ = getCheckItemValue(mcbCategory, "known_cosQ").toDoubleOrNull() ?: 0.0,
        generatorPowerKva = getCheckItemValue(mcbCategory, "known_powerKva").toIntOrNull() ?: 0,
        generatorPowerKw = getCheckItemValue(mcbCategory, "known_powerKw").toIntOrNull() ?: 0,
        resultCalculation = getCheckItemValue(mcbCategory, "calc_resultA").toIntOrNull() ?: 0,
        requirementCalculation = getCheckItemValue(mcbCategory, "calc_requiredAmps").toIntOrNull() ?: 0,
        conclusion = getCheckItemValue(mcbCategory, "conclusion")
    )

    val noiseMeasurement = DieselNoiseMeasurement(
        pointA = getMeasurementPoint("noise_measurement", "pointA"),
        pointB = getMeasurementPoint("noise_measurement", "pointB"),
        pointC = getMeasurementPoint("noise_measurement", "pointC"),
        pointD = getMeasurementPoint("noise_measurement", "pointD")
    )

    val lightingMeasurement = DieselLightingMeasurement(
        pointA = getMeasurementPoint("lighting_measurement", "pointA"),
        pointB = getMeasurementPoint("lighting_measurement", "pointB"),
        pointC = getMeasurementPoint("lighting_measurement", "pointC"),
        pointD = getMeasurementPoint("lighting_measurement", "pointD")
    )

    val conclusionText = this.findings.filter { it.type == FindingType.FINDING }.joinToString("\n") { it.description }
    val recommendationsText = this.findings.filter { it.type == FindingType.RECOMMENDATION }.joinToString("\n") { it.description }

    // --- Assemble the final DTO ---
    return DieselReportRequest(
        examinationType = inspection.examinationType,
        extraId = inspection.extraId.toLongOrNull() ?: 0L,
        inspectionType = inspection.inspectionType.name,
        createdAt = inspection.createdAt ?: "",
        inspectionDate = inspection.reportDate ?: "",
        generalData = generalData,
        technicalData = technicalData,
        visualChecks = visualChecks,
        tests = tests,
        electricalComponents = electricalComponents,
        mcbCalculation = mcbCalculation,
        noiseMeasurement = noiseMeasurement,
        lightingMeasurement = lightingMeasurement,
        conclusion = conclusionText,
        recommendations = recommendationsText
    )
}

/**
 * Maps the domain layer's [InspectionWithDetailsDomain] to a [DieselBapRequest] for the API.
 * This is a summary report and involves interpreting detailed data into boolean flags.
 */
fun InspectionWithDetailsDomain.toDieselBapRequest(): DieselBapRequest {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getCheckItemValue(category: String, itemName: String, default: String = ""): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: default
    }

    fun getCheckItemStatus(category: String, itemName: String, default: Boolean = false): Boolean {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.status ?: default
    }

    // --- BAP Specific Logic ---
    val visualCategory = "visual_inspection"
    val testCategory = "testing"

    val isMachineGood = getCheckItemStatus(visualCategory, "baseConstructionFoundation") &&
            getCheckItemStatus(visualCategory, "mainPartsSupport") &&
            getCheckItemStatus(visualCategory, "mainPartsFlyWheel")

    val areElectricalIndicatorsGood = getCheckItemStatus(visualCategory, "generatorVoltMeter") &&
            getCheckItemStatus(visualCategory, "generatorAmpereMeter") &&
            getCheckItemStatus(visualCategory, "generatorFrequencyMeter")

    val isFoundationGood = getCheckItemStatus(visualCategory, "baseConstructionFoundation")
    val hasLubeLeak = !getCheckItemStatus(visualCategory, "lubeSystemPacking") // Inverted logic
    val hasHydraulicLeak = false // Diesel doesn't have hydraulic specific checks, default to false.

    val isLightingCompliant = (getCheckItemValue("testing", "ndt_lighting").toDoubleOrNull() ?: 0.0) > 0
    val isNoiseCompliant = (getCheckItemValue("testing", "ndt_noise").toDoubleOrNull() ?: 0.0) < 85.0
    val isEmergencyStopOk = getCheckItemValue(testCategory, "safety_emergencyStop").contains("Berfungsi", ignoreCase = true)
    val isMachineFunctional = getCheckItemValue(testCategory, "ndt_loadTest").contains("Berfungsi", ignoreCase = true)

    val isVoltageNormal: Boolean by lazy {
        val rs = getCheckItemValue(testCategory, "elec_panel_voltRS").toIntOrNull()
        val rt = getCheckItemValue(testCategory, "elec_panel_voltRT").toIntOrNull()
        val st = getCheckItemValue(testCategory, "elec_panel_voltST").toIntOrNull()
        (rs != null && rs > 0) && (rt != null && rt > 0) && (st != null && st > 0)
    }

    val isPhaseBalanced: Boolean by lazy {
        val r = getCheckItemValue(testCategory, "elec_power_ampR").toFloatOrNull()
        val s = getCheckItemValue(testCategory, "elec_power_ampS").toFloatOrNull()
        val t = getCheckItemValue(testCategory, "elec_power_ampT").toFloatOrNull()

        if (r != null && s != null && t != null && r > 0 && s > 0 && t > 0) {
            val avg = (r + s + t) / 3
            val maxDeviation = 0.15 // 15% tolerance
            abs(r - avg) / avg < maxDeviation &&
                    abs(s - avg) / avg < maxDeviation &&
                    abs(t - avg) / avg < maxDeviation
        } else {
            false
        }
    }


    // --- Building DTO Components ---
    val generalData = DieselBapGeneralData(
        companyName = inspection.ownerName ?: "",
        companyLocation = inspection.ownerAddress ?: "",
        unitLocation = inspection.usageLocation ?: "",
        userAddressInCharge = inspection.addressUsageLocation ?: ""
    )

    val technicalData = DieselBapTechnicalData(
        brandType = inspection.manufacturer?.brandOrType ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        locationAndYearOfManufacture = inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = inspection.serialNumber ?: "",
        capacityWorkingLoad = inspection.capacity ?: "",
        technicalDataDieselMotorPowerRpm = getCheckItemValue("technical_data", "motor_powerRpm"),
        specialSpecification = "", // Not available in Diesel Laporan
        dimensionsDescription = "", // Not available in Diesel Laporan
        rotationRpm = getCheckItemValue("technical_data", "gen_rpm"),
        technicalDataGeneratorFrequency = getCheckItemValue("technical_data", "gen_frequency"),
        technicalDataGeneratorCurrent = getCheckItemValue("technical_data", "gen_current"),
        machineWeightKg = "", // Not available in Diesel Laporan
        areSafetyFeaturesInstalled = getCheckItemStatus(visualCategory, "safetyGovernor") || getCheckItemStatus(visualCategory, "safetyEmergencyStop")
    )

    val visualChecks = DieselBapVisualChecks(
        isMachineGoodCondition = isMachineGood,
        areElectricalIndicatorsGood = areElectricalIndicatorsGood,
        isAparAvailable = false, // Not available in Diesel Laporan, default to false
        isPpeAvailable = false, // Not available in Diesel Laporan, default to false
        isGroundingInstalled = getCheckItemStatus(visualCategory, "safetyGrounding"),
        isBatteryGoodCondition = getCheckItemStatus(visualCategory, "startingSystemBatteryPoles"),
        hasLubricationLeak = hasLubeLeak,
        isFoundationGoodCondition = isFoundationGood,
        hasHydraulicLeak = hasHydraulicLeak
    )

    val functionalTests = DieselBapFunctionalTests(
        isLightingCompliant = isLightingCompliant,
        isNoiseLevelCompliant = isNoiseCompliant,
        isEmergencyStopFunctional = isEmergencyStopOk,
        isMachineFunctional = isMachineFunctional,
        isVibrationLevelCompliant = getCheckItemStatus(visualCategory, "mainPartsVibrationDamper"), // Best effort mapping
        isInsulationResistanceOk = true, // Not available in Diesel Laporan, default to true
        isShaftRotationCompliant = getCheckItemValue(testCategory, "ndt_shaftRpm").isNotBlank(),
        isGroundingResistanceCompliant = getCheckItemValue(testCategory, "safety_grounding").isNotBlank(),
        isNdtWeldTestOk = getCheckItemValue(testCategory, "ndt_weldJoint").contains("Baik", ignoreCase = true),
        isVoltageBetweenPhasesNormal = isVoltageNormal,
        isPhaseLoadBalanced = isPhaseBalanced
    )

    return DieselBapRequest(
        laporanId = this.inspection.moreExtraId,
        examinationType = inspection.examinationType,
        inspectionDate = inspection.reportDate ?: "",
        createdAt = inspection.createdAt ?: "",
        extraId = inspection.extraId.toLongOrNull() ?: 0L,
        generalData = generalData,
        technicalData = technicalData,
        visualChecks = visualChecks,
        functionalTests = functionalTests
    )
}

// =================================================================================================
//                                  DTO (Response) -> Domain
// =================================================================================================

/**
 * Maps a [DieselReportData] from the API response to the domain layer's [InspectionWithDetailsDomain].
 * This function unpacks the DTO and reconstructs the detailed domain model.
 */
fun DieselReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = 0L // For new local inserts, ID is auto-generated by Room

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId.toString(),
        moreExtraId = this.id, // Use API ID as moreExtraId
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.PTP,
        subInspectionType = SubInspectionType.Motor_Diesel,
        equipmentType = this.generalData.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.unitLocation,
        addressUsageLocation = this.generalData.userAddressInCharge,
        driveType = "", // Not explicitly in DTO, can be derived if needed
        serialNumber = this.generalData.serialNumberUnitNumber,
        permitNumber = this.generalData.usagePermitNumber,
        capacity = this.generalData.capacityWorkingLoad,
        manufacturer = ManufacturerDomain(
            name = this.generalData.manufacturer,
            brandOrType = this.generalData.brandType,
            year = this.generalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun addCheckItem(category: String, itemName: String, value: String?) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = false, result = value ?: ""))
    }

    fun addConditionResultCheckItem(category: String, itemName: String, value: DieselStatusResult) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = value.status, result = value.result))
    }

    fun addTestResultCheckItem(category: String, itemName: String, value: DieselRemarksResult) {
        checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = false, result = "${value.result}#${value.remarks}"))
    }

    // --- Map All DTO fields back to CheckItems ---

    // General Data
    val generalCategory = "general_data"
    addCheckItem(generalCategory, "userInCharge", this.generalData.userInCharge)
    addCheckItem(generalCategory, "subcontractorPersonInCharge", this.generalData.subcontractorPersonInCharge)
    addCheckItem(generalCategory, "intendedUse", this.generalData.intendedUse)
    addCheckItem(generalCategory, "pjk3SkpNo", this.generalData.pjk3SkpNo)
    addCheckItem(generalCategory, "ak3SkpNo", this.generalData.ak3SkpNo)
    addCheckItem(generalCategory, "classification", this.generalData.portableOrStationer)
    addCheckItem(generalCategory, "operatorName", this.generalData.operatorName)
    addCheckItem(generalCategory, "equipmentHistory", this.generalData.equipmentHistory)

    // Technical Data
    val techCategory = "technical_data"
    this.technicalData.dieselMotor.let { motor ->
        addCheckItem(techCategory, "motor_brandModel", motor.brandModel)
        addCheckItem(techCategory, "motor_manufacturer", motor.manufacturer)
        addCheckItem(techCategory, "motor_classification", motor.classification)
        addCheckItem(techCategory, "motor_serialNumber", motor.serialNumber)
        addCheckItem(techCategory, "motor_powerRpm", motor.powerRpm)
        addCheckItem(techCategory, "motor_startingPower", motor.startingPower)
        addCheckItem(techCategory, "motor_cylinderCount", motor.cylinderCount)
    }
    this.technicalData.generator.let { gen ->
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

    // Visual Inspection
    val visualCategory = "visual_inspection"
    val v = this.visualChecks
    addConditionResultCheckItem(visualCategory, "baseConstructionFoundation", v.basicConstruction.foundation)
    addConditionResultCheckItem(visualCategory, "baseConstructionDieselHousing", v.basicConstruction.dieselHousing)
    addConditionResultCheckItem(visualCategory, "baseConstructionSupport", v.basicConstruction.support)
    addConditionResultCheckItem(visualCategory, "baseConstructionAnchorBolt", v.basicConstruction.anchorBolt)
    addConditionResultCheckItem(visualCategory, "structureDailyTank", v.structuralConstruction.dailyTank)
    addConditionResultCheckItem(visualCategory, "structureMuffler", v.structuralConstruction.muffler)
    addConditionResultCheckItem(visualCategory, "structureAirVessel", v.structuralConstruction.airVessel)
    addConditionResultCheckItem(visualCategory, "structurePanel", v.structuralConstruction.panel)
    addConditionResultCheckItem(visualCategory, "lubeSystemOil", v.lubricationSystem.oil)
    addConditionResultCheckItem(visualCategory, "lubeSystemOilStrainer", v.lubricationSystem.oilStrainer)
    addConditionResultCheckItem(visualCategory, "lubeSystemOilCooler", v.lubricationSystem.oilCooler)
    addConditionResultCheckItem(visualCategory, "lubeSystemOilFilter", v.lubricationSystem.oilFilter)
    addConditionResultCheckItem(visualCategory, "lubeSystemByPassFilter", v.lubricationSystem.byPassFilter)
    addConditionResultCheckItem(visualCategory, "lubeSystemSafetyValve", v.lubricationSystem.safetyValve)
    addConditionResultCheckItem(visualCategory, "lubeSystemPacking", v.lubricationSystem.packing)
    addConditionResultCheckItem(visualCategory, "fuelSystemDailyTank", v.fuelSystem.dailyTank)
    addConditionResultCheckItem(visualCategory, "fuelSystemFuelInjector", v.fuelSystem.fuelInjector)
    addConditionResultCheckItem(visualCategory, "fuelSystemConnections", v.fuelSystem.connections)
    addConditionResultCheckItem(visualCategory, "fuelSystemFloatTank", v.fuelSystem.floatTank)
    addConditionResultCheckItem(visualCategory, "fuelSystemFuelFilter", v.fuelSystem.fuelFilter)
    addConditionResultCheckItem(visualCategory, "fuelSystemFuelInjectorPump", v.fuelSystem.fuelPump)
    addConditionResultCheckItem(visualCategory, "fuelSystemMagneticScreen", v.fuelSystem.magneticScreen)
    addConditionResultCheckItem(visualCategory, "fuelSystemGovernor", v.fuelSystem.governor)
    addConditionResultCheckItem(visualCategory, "fuelSystemThrottleShaft", v.fuelSystem.throttleShaft)
    addConditionResultCheckItem(visualCategory, "fuelSystemRegulator", v.fuelSystem.regulator)
    addConditionResultCheckItem(visualCategory, "fuelSystemShutOffValve", v.fuelSystem.shutOffValve)
    addConditionResultCheckItem(visualCategory, "startingSystemFeedPump", v.startingAid.feedPump)
    addConditionResultCheckItem(visualCategory, "startingSystemFuelValve", v.startingAid.fuelValve)
    addConditionResultCheckItem(visualCategory, "startingSystemPrimingPump", v.startingAid.primingPump)
    addConditionResultCheckItem(visualCategory, "startingSystemHeaterPlug", v.startingAid.heaterPlug)
    addConditionResultCheckItem(visualCategory, "startingSystemHeaterSwitch", v.startingAid.heaterSwitch)
    addConditionResultCheckItem(visualCategory, "startingSystemPreHeater", v.startingAid.preHeater)
    addConditionResultCheckItem(visualCategory, "startingSystemWaterSignal", v.startingAid.waterSignal)
    addConditionResultCheckItem(visualCategory, "startingSystemSwitch", v.startingAid.startingSwitch)
    addConditionResultCheckItem(visualCategory, "startingSystemBatteryPoles", v.startingAid.batteryPoles)
    addConditionResultCheckItem(visualCategory, "startingSystemThermostartTank", v.startingAid.thermostartTank)
    addConditionResultCheckItem(visualCategory, "startingSystemThermostart", v.startingAid.thermostart)
    addConditionResultCheckItem(visualCategory, "startingSystemHeaterSignal", v.startingAid.heaterSignal)
    addConditionResultCheckItem(visualCategory, "startingSystemThermostartSwitch", v.startingAid.thermostartSwitch)
    addConditionResultCheckItem(visualCategory, "startingSystemGlowPlug", v.startingAid.glowPlug)
    addConditionResultCheckItem(visualCategory, "startingSystemSpeedSensor", v.startingAid.speedSensor)
    addConditionResultCheckItem(visualCategory, "startingSystemServiceMeter", v.startingAid.serviceMeter)
    addConditionResultCheckItem(visualCategory, "startingSystemTempSensor", v.startingAid.tempSensor)
    addConditionResultCheckItem(visualCategory, "startingSystemMotor", v.startingAid.startingMotor)
    addConditionResultCheckItem(visualCategory, "coolingSystemWater", v.coolingSystem.coolingWater)
    addConditionResultCheckItem(visualCategory, "coolingSystemBolts", v.coolingSystem.bolts)
    addConditionResultCheckItem(visualCategory, "coolingSystemClamps", v.coolingSystem.clamps)
    addConditionResultCheckItem(visualCategory, "coolingSystemRadiator", v.coolingSystem.radiator)
    addConditionResultCheckItem(visualCategory, "coolingSystemThermostat", v.coolingSystem.thermostat)
    addConditionResultCheckItem(visualCategory, "coolingSystemFan", v.coolingSystem.fan)
    addConditionResultCheckItem(visualCategory, "coolingSystemFanGuard", v.coolingSystem.fanGuard)
    addConditionResultCheckItem(visualCategory, "coolingSystemFanRotation", v.coolingSystem.fanRotation)
    addConditionResultCheckItem(visualCategory, "coolingSystemBearing", v.coolingSystem.bearing)
    addConditionResultCheckItem(visualCategory, "airCirculationPreCleaner", v.airCirculationSystem.preCleaner)
    addConditionResultCheckItem(visualCategory, "airCirculationDustIndicator", v.airCirculationSystem.dustIndicator)
    addConditionResultCheckItem(visualCategory, "airCirculationAirCleaner", v.airCirculationSystem.airCleaner)
    addConditionResultCheckItem(visualCategory, "airCirculationTurboCharger", v.airCirculationSystem.turboCharger)
    addConditionResultCheckItem(visualCategory, "airCirculationClamps", v.airCirculationSystem.clamps)
    addConditionResultCheckItem(visualCategory, "airCirculationAfterCooler", v.airCirculationSystem.afterCooler)
    addConditionResultCheckItem(visualCategory, "airCirculationMuffler", v.airCirculationSystem.muffler)
    addConditionResultCheckItem(visualCategory, "airCirculationSilencer", v.airCirculationSystem.silencer)
    addConditionResultCheckItem(visualCategory, "airCirculationHeatDamper", v.airCirculationSystem.heatDamper)
    addConditionResultCheckItem(visualCategory, "airCirculationBolts", v.airCirculationSystem.bolts)
    addConditionResultCheckItem(visualCategory, "mainPartsDamperBolts", v.mainParts.damperBolts)
    addConditionResultCheckItem(visualCategory, "mainPartsSupport", v.mainParts.support)
    addConditionResultCheckItem(visualCategory, "mainPartsFlyWheelHousing", v.mainParts.flyWheelHousing)
    addConditionResultCheckItem(visualCategory, "mainPartsFlyWheel", v.mainParts.flyWheel)
    addConditionResultCheckItem(visualCategory, "mainPartsVibrationDamper", v.mainParts.vibrationDamper)
    addConditionResultCheckItem(visualCategory, "mainPartsBeltAndPulley", v.mainParts.beltAndPulley)
    addConditionResultCheckItem(visualCategory, "mainPartsCrankshaft", v.mainParts.crankshaft)
    addConditionResultCheckItem(visualCategory, "generatorTerminalConnection", v.generatorParts.terminal)
    addConditionResultCheckItem(visualCategory, "generatorCableToPanel", v.generatorParts.cable)
    addConditionResultCheckItem(visualCategory, "generatorPanelBoard", v.generatorParts.panel)
    addConditionResultCheckItem(visualCategory, "generatorAmpereMeter", v.generatorParts.ampereMeter)
    addConditionResultCheckItem(visualCategory, "generatorVoltMeter", v.generatorParts.voltMeter)
    addConditionResultCheckItem(visualCategory, "generatorFrequencyMeter", v.generatorParts.frequencyMeter)
    addConditionResultCheckItem(visualCategory, "generatorCircuitBreaker", v.generatorParts.circuitBreaker)
    addConditionResultCheckItem(visualCategory, "generatorOnOffSwitch", v.generatorParts.onOffSwitch)
    addConditionResultCheckItem(visualCategory, "transmissionGear", v.transmission.gear)
    addConditionResultCheckItem(visualCategory, "transmissionBelt", v.transmission.belt)
    addConditionResultCheckItem(visualCategory, "transmissionChain", v.transmission.chain)
    addConditionResultCheckItem(visualCategory, "mdpCableConnection", v.mdp.cable)
    addConditionResultCheckItem(visualCategory, "mdpCondition", v.mdp.condition)
    addConditionResultCheckItem(visualCategory, "mdpAmpereMeter", v.mdp.ampereMeter)
    addConditionResultCheckItem(visualCategory, "mdpVoltMeter", v.mdp.voltMeter)
    addConditionResultCheckItem(visualCategory, "mdpMainCircuitBreaker", v.mdp.mainCircuitBreaker)
    addConditionResultCheckItem(visualCategory, "safetyGrounding", v.safetyDevice.grounding)
    addConditionResultCheckItem(visualCategory, "safetyLightningArrester", v.safetyDevice.lightningArrester)
    addConditionResultCheckItem(visualCategory, "safetyEmergencyStop", v.safetyDevice.emergencyStop)
    addConditionResultCheckItem(visualCategory, "safetyGovernor", v.safetyDevice.governor)
    addConditionResultCheckItem(visualCategory, "safetyThermostat", v.safetyDevice.thermostat)
    addConditionResultCheckItem(visualCategory, "safetyWaterSignal", v.safetyDevice.waterSignal)
    addConditionResultCheckItem(visualCategory, "safetyFanGuard", v.safetyDevice.fanGuard)
    addConditionResultCheckItem(visualCategory, "safetySilencer", v.safetyDevice.silencer)
    addConditionResultCheckItem(visualCategory, "safetyVibrationDamper", v.safetyDevice.vibrationDamper)
    addConditionResultCheckItem(visualCategory, "safetyCircuitBreaker", v.safetyDevice.circuitBreaker)
    addConditionResultCheckItem(visualCategory, "safetyAvr", v.safetyDevice.avr)

    // Testing and Measurement
    val testCategory = "testing"
    val t = this.tests
    addTestResultCheckItem(testCategory, "ndt_shaftRpm", t.ndt.shaftRpm)
    addTestResultCheckItem(testCategory, "ndt_weldJoint", t.ndt.weldJoint)
    addTestResultCheckItem(testCategory, "ndt_noise", t.ndt.noise)
    addTestResultCheckItem(testCategory, "ndt_lighting", t.ndt.lighting)
    addTestResultCheckItem(testCategory, "ndt_loadTest", t.ndt.loadTest)
    addTestResultCheckItem(testCategory, "safety_governor", t.safetyDevice.governor)
    addTestResultCheckItem(testCategory, "safety_emergencyStop", t.safetyDevice.emergencyStop)
    addTestResultCheckItem(testCategory, "safety_grounding", t.safetyDevice.grounding)
    addTestResultCheckItem(testCategory, "safety_panelIndicators", t.safetyDevice.indicatorPanel)
    addTestResultCheckItem(testCategory, "safety_pressureGauge", t.safetyDevice.pressureGauge)
    addTestResultCheckItem(testCategory, "safety_temperatureIndicator", t.safetyDevice.tempIndicator)
    addTestResultCheckItem(testCategory, "safety_waterIndicator", t.safetyDevice.waterIndicator)
    addTestResultCheckItem(testCategory, "safety_safetyValves", t.safetyDevice.safetyValves)
    addTestResultCheckItem(testCategory, "safety_radiator", t.safetyDevice.radiator)

    val elec = this.electricalComponents.panelControl
    addCheckItem(testCategory, "elec_panel_ka", elec.ka)
    addCheckItem(testCategory, "elec_panel_voltRS", elec.voltage.rs.toString())
    addCheckItem(testCategory, "elec_panel_voltRT", elec.voltage.rt.toString())
    addCheckItem(testCategory, "elec_panel_voltST", elec.voltage.st.toString())
    addCheckItem(testCategory, "elec_panel_voltRN", elec.voltage.rn.toString())
    addCheckItem(testCategory, "elec_panel_voltRG", elec.voltage.rg.toString())
    addCheckItem(testCategory, "elec_panel_voltNG", elec.voltage.ng.toString())
    addCheckItem(testCategory, "elec_power_freq", elec.powerInfo.frequency)
    addCheckItem(testCategory, "elec_power_cosQ", elec.powerInfo.cosQ.toString())
    addCheckItem(testCategory, "elec_power_ampR", elec.powerInfo.ampere.r.toString())
    addCheckItem(testCategory, "elec_power_ampS", elec.powerInfo.ampere.s.toString())
    addCheckItem(testCategory, "elec_power_ampT", elec.powerInfo.ampere.t.toString())
    addCheckItem(testCategory, "elec_power_remarks", elec.powerInfo.result)

    // MCB Calculation
    val mcbCategory = "mcb_calculation"
    val mcb = this.mcbCalculation
    addCheckItem(mcbCategory, "known_phase", mcb.phase.toString())
    addCheckItem(mcbCategory, "known_voltage", mcb.voltage)
    addCheckItem(mcbCategory, "known_cosQ", mcb.cosQ.toString())
    addCheckItem(mcbCategory, "known_powerKva", mcb.generatorPowerKva.toString())
    addCheckItem(mcbCategory, "known_powerKw", mcb.generatorPowerKw.toString())
    addCheckItem(mcbCategory, "calc_formula", "I = P / (V * CosQ * sqrt(3))") // Formula is static
    addCheckItem(mcbCategory, "calc_resultA", mcb.resultCalculation.toString())
    addCheckItem(mcbCategory, "calc_requiredAmps", mcb.requirementCalculation.toString())
    addCheckItem(mcbCategory, "conclusion", mcb.conclusion)

    // Noise Measurement
    val noiseCategory = "noise_measurement"
    addCheckItem(noiseCategory, "pointA_result", this.noiseMeasurement.pointA.result.toString())
    addCheckItem(noiseCategory, "pointA_analysis", this.noiseMeasurement.pointA.status)
    addCheckItem(noiseCategory, "pointB_result", this.noiseMeasurement.pointB.result.toString())
    addCheckItem(noiseCategory, "pointB_analysis", this.noiseMeasurement.pointB.status)
    addCheckItem(noiseCategory, "pointC_result", this.noiseMeasurement.pointC.result.toString())
    addCheckItem(noiseCategory, "pointC_analysis", this.noiseMeasurement.pointC.status)
    addCheckItem(noiseCategory, "pointD_result", this.noiseMeasurement.pointD.result.toString())
    addCheckItem(noiseCategory, "pointD_analysis", this.noiseMeasurement.pointD.status)

    // Lighting Measurement
    val lightCategory = "lighting_measurement"
    addCheckItem(lightCategory, "pointA_result", this.lightingMeasurement.pointA.result.toString())
    addCheckItem(lightCategory, "pointA_analysis", this.lightingMeasurement.pointA.status)
    addCheckItem(lightCategory, "pointB_result", this.lightingMeasurement.pointB.result.toString())
    addCheckItem(lightCategory, "pointB_analysis", this.lightingMeasurement.pointB.status)
    addCheckItem(lightCategory, "pointC_result", this.lightingMeasurement.pointC.result.toString())
    addCheckItem(lightCategory, "pointC_analysis", this.lightingMeasurement.pointC.status)
    addCheckItem(lightCategory, "pointD_result", this.lightingMeasurement.pointD.result.toString())
    addCheckItem(lightCategory, "pointD_analysis", this.lightingMeasurement.pointD.status)

    // Findings
    val findings = mutableListOf<InspectionFindingDomain>()
    this.conclusion.split("\n").filter { it.isNotBlank() }.forEach {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.FINDING))
    }
    this.recommendations.split("\n").filter { it.isNotBlank() }.forEach {
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = it, type = FindingType.RECOMMENDATION))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = emptyList() // Laporan has no separate test results
    )
}

/**
 * Maps a [DieselBapReportData] from the API response to the domain layer's [InspectionWithDetailsDomain].
 * This function unpacks the summary DTO and reconstructs a simplified domain model.
 */
fun DieselBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = 0L // For new local inserts

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId.toString(),
        moreExtraId = this.id, // Use BAP ID as moreExtraId
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PTP,
        subInspectionType = SubInspectionType.Motor_Diesel,
        equipmentType = "Motor Diesel", // BAP doesn't specify, default
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.unitLocation,
        addressUsageLocation = this.generalData.userAddressInCharge,
        serialNumber = this.technicalData.serialNumberUnitNumber,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.brandType,
            year = this.technicalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        isSynced = true,
        capacity = this.technicalData.capacityWorkingLoad
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    val testResults = mutableListOf<InspectionTestResultDomain>()

    // Technical Data - Map to TestResults as they are not simple checks
    val techCat = "DATA TEKNIK" // BAP Category
    val td = this.technicalData
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Daya Motor Penggerak", result = td.technicalDataDieselMotorPowerRpm, notes = techCat))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Spesifikasi Khusus", result = td.specialSpecification, notes = techCat))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Keterangan Dimensi", result = td.dimensionsDescription, notes = techCat))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Putaran (RPM)", result = td.rotationRpm, notes = techCat))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Frekuensi (Hz)", result = td.technicalDataGeneratorFrequency, notes = techCat))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Arus (Ampere)", result = td.technicalDataGeneratorCurrent, notes = techCat))
    testResults.add(InspectionTestResultDomain(inspectionId = inspectionId, testName = "Berat Mesin (Kg)", result = td.machineWeightKg, notes = techCat))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = techCat, itemName = "Perlengkapan pengaman terpasang", status = td.areSafetyFeaturesInstalled))

    // Visual Checks
    val visualCat = BAPCategory.VISUAL_INSPECTION
    val v = this.visualChecks
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = visualCat, itemName = "Kondisi mesin baik", status = v.isMachineGoodCondition))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = visualCat, itemName = "Indikator kelistrikan baik", status = v.areElectricalIndicatorsGood))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = "$visualCat - Alat Pelindung Diri", itemName = "APAR tersedia", status = v.isAparAvailable))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = "$visualCat - Alat Pelindung Diri", itemName = "APD tersedia", status = v.isPpeAvailable))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = visualCat, itemName = "Pembumian terpasang", status = v.isGroundingInstalled))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = visualCat, itemName = "Kondisi baterai baik", status = v.isBatteryGoodCondition))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = visualCat, itemName = "Ada kebocoran pelumasan", status = v.hasLubricationLeak))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = visualCat, itemName = "Kondisi pondasi baik", status = v.isFoundationGoodCondition))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = visualCat, itemName = "Ada kebocoran hidrolik", status = v.hasHydraulicLeak))

    // Functional Tests
    val testCat = BAPCategory.TESTING
    val t = this.functionalTests
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Penerangan memenuhi syarat", status = t.isLightingCompliant))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Tingkat kebisingan memenuhi syarat", status = t.isNoiseLevelCompliant))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Tombol darurat berfungsi", status = t.isEmergencyStopFunctional))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Mesin berfungsi baik", status = t.isMachineFunctional))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Tingkat getaran memenuhi syarat", status = t.isVibrationLevelCompliant))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Tahanan isolasi baik", status = t.isInsulationResistanceOk))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Putaran poros memenuhi syarat", status = t.isShaftRotationCompliant))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Tahanan pembumian memenuhi syarat", status = t.isGroundingResistanceCompliant))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Hasil NDT Las baik", status = t.isNdtWeldTestOk))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Tegangan antar fasa normal", status = t.isVoltageBetweenPhasesNormal))
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = testCat, itemName = "Beban antar fasa seimbang", status = t.isPhaseLoadBalanced))

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(), // BAP has no findings/recommendations
        testResults = testResults
    )
}