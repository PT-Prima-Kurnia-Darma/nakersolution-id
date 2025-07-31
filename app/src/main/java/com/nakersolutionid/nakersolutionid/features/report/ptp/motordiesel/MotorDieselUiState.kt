package com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Diesel Motor inspection report.
 * This class is immutable.
 */
@Immutable
data class DieselMotorUiState(
    val isLoading: Boolean = false,
    val inspectionReport: DieselMotorInspectionReport = DieselMotorInspectionReport()
) {
    companion object {
        fun createDummyDieselMotorUiState(): DieselMotorUiState {
            return DieselMotorUiState(
                isLoading = false,
                inspectionReport = DieselMotorInspectionReport(
                    extraId = "DME-54321",
                    moreExtraId = "MDME-09876",
                    equipmentType = "Diesel Generator",
                    examinationType = "Routine Inspection",
                    generalData = DieselMotorGeneralData(
                        ownerName = "PT. Cipta Karya",
                        ownerAddress = "Jl. Produksi No. 2, Surabaya",
                        userInCharge = "Dewi Sartika",
                        userAddressInCharge = "Jl. Pabrik No. 3, Surabaya",
                        subcontractorPersonInCharge = "Joko Susilo",
                        unitLocation = "Genset Room, Ground Floor",
                        brandType = "Cummins",
                        driveType = "Diesel Engine",
                        serialNumberUnitNumber = "DG-CUMM-112233",
                        manufacturer = "Cummins Inc.",
                        locationAndYearOfManufacture = "USA, 2021",
                        capacityRpm = "200 KVA @ 1500 RPM",
                        intendedUse = "Backup Power Supply",
                        pjk3SkpNo = "PJK3-DG-001",
                        ak3SkpNo = "AK3-DG-002",
                        classification = "Standby Generator",
                        usagePermitNumber = "UP-DG-003",
                        operatorName = "Rudi Hartono",
                        equipmentHistory = "Last serviced 3 months ago. Oil and filter changed."
                    ),
                    technicalData = DieselMotorTechnicalData(
                        dieselMotor = DieselMotorInfo(
                            brandModel = "QSB7",
                            manufacturer = "Cummins",
                            classification = "Inline 6-Cylinder",
                            serialNumber = "QSB7-456789",
                            powerRpm = "200 HP @ 1500 RPM",
                            startingPower = "220 HP",
                            cylinderCount = "6"
                        ),
                        generator = DieselMotorGeneratorInfo(
                            brandType = "Stamford",
                            manufacturer = "Cummins Generator Technologies",
                            serialNumber = "STAM-ABC123",
                            power = "200 KVA",
                            frequency = "50 Hz",
                            rpm = "1500",
                            voltage = "400/230 V",
                            powerFactor = "0.8",
                            current = "289 A"
                        )
                    ),
                    visualInspection = DieselMotorVisualInspection(
                        baseConstructionFoundation = DieselMotorConditionResult(isGood = true, remarks = "Solid concrete foundation, no cracks."),
                        baseConstructionDieselHousing = DieselMotorConditionResult(isGood = true, remarks = "Housing is clean and intact."),
                        baseConstructionSupport = DieselMotorConditionResult(isGood = true, remarks = "Supports are firm."),
                        baseConstructionAnchorBolt = DieselMotorConditionResult(isGood = true, remarks = "Anchor bolts are tight and rust-free."),
                        structureDailyTank = DieselMotorConditionResult(isGood = true, remarks = "Daily tank is clean, no leaks."),
                        structureMuffler = DieselMotorConditionResult(isGood = true, remarks = "Muffler is securely mounted and free from rust."),
                        structureAirVessel = DieselMotorConditionResult(isGood = true, remarks = "Air vessel is intact."),
                        structurePanel = DieselMotorConditionResult(isGood = true, remarks = "Control panel is clean."),
                        lubeSystemOil = DieselMotorConditionResult(isGood = true, remarks = "Oil level is within the marked range."),
                        lubeSystemOilStrainer = DieselMotorConditionResult(isGood = true, remarks = "Strainer is clean."),
                        lubeSystemOilCooler = DieselMotorConditionResult(isGood = true, remarks = "Cooler fins are clear."),
                        lubeSystemOilFilter = DieselMotorConditionResult(isGood = true, remarks = "Oil filter is clean."),
                        lubeSystemByPassFilter = DieselMotorConditionResult(isGood = true, remarks = "By-pass filter is clean."),
                        lubeSystemSafetyValve = DieselMotorConditionResult(isGood = true, remarks = "Safety valve is properly set."),
                        lubeSystemPacking = DieselMotorConditionResult(isGood = true, remarks = "Packing is in good condition."),
                        fuelSystemDailyTank = DieselMotorConditionResult(isGood = true, remarks = "Fuel tank is clean and free of water."),
                        fuelSystemFuelInjector = DieselMotorConditionResult(isGood = true, remarks = "Injectors appear to be in good condition."),
                        fuelSystemConnections = DieselMotorConditionResult(isGood = true, remarks = "All fuel connections are tight and leak-free."),
                        fuelSystemFloatTank = DieselMotorConditionResult(isGood = true, remarks = "Float tank is functioning correctly."),
                        fuelSystemFuelFilter = DieselMotorConditionResult(isGood = true, remarks = "Fuel filter is clean."),
                        fuelSystemFuelInjectorPump = DieselMotorConditionResult(isGood = true, remarks = "Fuel pump shows no signs of leakage."),
                        fuelSystemMagneticScreen = DieselMotorConditionResult(isGood = true, remarks = "Magnetic screen is clean."),
                        fuelSystemGovernor = DieselMotorConditionResult(isGood = true, remarks = "Governor is clean and secure."),
                        fuelSystemThrottleShaft = DieselMotorConditionResult(isGood = true, remarks = "Throttle shaft moves freely."),
                        fuelSystemRegulator = DieselMotorConditionResult(isGood = true, remarks = "Fuel regulator appears to be in good condition."),
                        fuelSystemShutOffValve = DieselMotorConditionResult(isGood = true, remarks = "Shut-off valve operates correctly."),
                        startingSystemFeedPump = DieselMotorConditionResult(isGood = true, remarks = "Feed pump is functioning."),
                        startingSystemFuelValve = DieselMotorConditionResult(isGood = true, remarks = "Fuel valve is operating correctly."),
                        startingSystemPrimingPump = DieselMotorConditionResult(isGood = true, remarks = "Priming pump is functional."),
                        startingSystemHeaterPlug = DieselMotorConditionResult(isGood = true, remarks = "Heater plug is clean."),
                        startingSystemHeaterSwitch = DieselMotorConditionResult(isGood = true, remarks = "Heater switch is operational."),
                        startingSystemPreHeater = DieselMotorConditionResult(isGood = true, remarks = "Pre-heater unit is intact."),
                        startingSystemWaterSignal = DieselMotorConditionResult(isGood = true, remarks = "Water signal light is functional."),
                        startingSystemSwitch = DieselMotorConditionResult(isGood = true, remarks = "Start switch is working."),
                        startingSystemBatteryPoles = DieselMotorConditionResult(isGood = true, remarks = "Battery poles are clean and tight."),
                        startingSystemThermostartTank = DieselMotorConditionResult(isGood = true, remarks = "Thermostart tank is clean."),
                        startingSystemThermostart = DieselMotorConditionResult(isGood = true, remarks = "Thermostart unit is operational."),
                        startingSystemHeaterSignal = DieselMotorConditionResult(isGood = true, remarks = "Heater indicator light is functional."),
                        startingSystemThermostartSwitch = DieselMotorConditionResult(isGood = true, remarks = "Thermostart switch is working."),
                        startingSystemGlowPlug = DieselMotorConditionResult(isGood = true, remarks = "Glow plugs appear to be in good condition."),
                        startingSystemSpeedSensor = DieselMotorConditionResult(isGood = true, remarks = "Speed sensor is clean and properly mounted."),
                        startingSystemServiceMeter = DieselMotorConditionResult(isGood = true, remarks = "System meter is clean."),
                        startingSystemTempSensor = DieselMotorConditionResult(isGood = true, remarks = "Temperature sensor is clean and properly mounted."),
                        startingSystemMotor = DieselMotorConditionResult(isGood = true, remarks = "Starter motor is clean and secure."),
                        coolingSystemWater = DieselMotorConditionResult(isGood = true, remarks = "Coolant level is within the correct range."),
                        coolingSystemBolts = DieselMotorConditionResult(isGood = true, remarks = "Cooling system bolts are tight."),
                        coolingSystemClamps = DieselMotorConditionResult(isGood = true, remarks = "Clamps are secure and not corroded."),
                        coolingSystemRadiator = DieselMotorConditionResult(isGood = true, remarks = "Radiator is clean and free of leaks."),
                        coolingSystemThermostat = DieselMotorConditionResult(isGood = true, remarks = "Thermostat appears to be functioning."),
                        coolingSystemFan = DieselMotorConditionResult(isGood = true, remarks = "Fan blades are clean and undamaged."),
                        coolingSystemFanGuard = DieselMotorConditionResult(isGood = true, remarks = "Fan guard is in place and secure."),
                        coolingSystemFanRotation = DieselMotorConditionResult(isGood = true, remarks = "Fan rotates freely."),
                        coolingSystemBearing = DieselMotorConditionResult(isGood = true, remarks = "Fan bearing is smooth and quiet."),
                        airCirculationPreCleaner = DieselMotorConditionResult(isGood = true, remarks = "Pre-cleaner element is clean."),
                        airCirculationDustIndicator = DieselMotorConditionResult(isGood = true, remarks = "Dust indicator shows clean."),
                        airCirculationAirCleaner = DieselMotorConditionResult(isGood = true, remarks = "Air cleaner element is clean."),
                        airCirculationTurboCharger = DieselMotorConditionResult(isGood = true, remarks = "Turbocharger appears to be in good condition."),
                        airCirculationClamps = DieselMotorConditionResult(isGood = true, remarks = "Air intake clamps are tight."),
                        airCirculationAfterCooler = DieselMotorConditionResult(isGood = true, remarks = "Aftercooler is clean and free of leaks."),
                        airCirculationMuffler = DieselMotorConditionResult(isGood = true, remarks = "Muffler is securely mounted."),
                        airCirculationSilencer = DieselMotorConditionResult(isGood = true, remarks = "Silencer is intact."),
                        airCirculationHeatDamper = DieselMotorConditionResult(isGood = true, remarks = "Heat damper is functioning correctly."),
                        airCirculationBolts = DieselMotorConditionResult(isGood = true, remarks = "Air circulation bolts are tight."),
                        mainPartsDamperBolts = DieselMotorConditionResult(isGood = true, remarks = "Damper bolts are tight."),
                        mainPartsSupport = DieselMotorConditionResult(isGood = true, remarks = "Main supports are firm."),
                        mainPartsFlyWheelHousing = DieselMotorConditionResult(isGood = true, remarks = "Flywheel housing is clean and intact."),
                        mainPartsFlyWheel = DieselMotorConditionResult(isGood = true, remarks = "Flywheel is clean and balanced."),
                        mainPartsVibrationDamper = DieselMotorConditionResult(isGood = true, remarks = "Vibration damper is in good condition."),
                        mainPartsBeltAndPulley = DieselMotorConditionResult(isGood = true, remarks = "Belts and pulleys show no signs of wear."),
                        mainPartsCrankshaft = DieselMotorConditionResult(isGood = true, remarks = "Crankshaft appears to be in good condition."),
                        generatorTerminalConnection = DieselMotorConditionResult(isGood = true, remarks = "Generator terminals are clean and tight."),
                        generatorCableToPanel = DieselMotorConditionResult(isGood = true, remarks = "Cables to panel are securely connected and insulated."),
                        generatorPanelBoard = DieselMotorConditionResult(isGood = true, remarks = "Panel board is clean and organized."),
                        generatorAmpereMeter = DieselMotorConditionResult(isGood = true, remarks = "Ampere meter is functional."),
                        generatorVoltMeter = DieselMotorConditionResult(isGood = true, remarks = "Volt meter is functional."),
                        generatorFrequencyMeter = DieselMotorConditionResult(isGood = true, remarks = "Frequency meter is functional."),
                        generatorCircuitBreaker = DieselMotorConditionResult(isGood = true, remarks = "Circuit breaker is in good condition."),
                        generatorOnOffSwitch = DieselMotorConditionResult(isGood = true, remarks = "On/Off switch is operational."),
                        transmissionGear = DieselMotorConditionResult(isGood = true, remarks = "Transmission gears appear to be in good condition."),
                        transmissionBelt = DieselMotorConditionResult(isGood = true, remarks = "Transmission belt is in good condition."),
                        transmissionChain = DieselMotorConditionResult(isGood = true, remarks = "Transmission chain is clean and lubricated."),
                        mdpCableConnection = DieselMotorConditionResult(isGood = true, remarks = "MDP cable connections are tight and insulated."),
                        mdpCondition = DieselMotorConditionResult(isGood = true, remarks = "MDP is clean and functional."),
                        mdpAmpereMeter = DieselMotorConditionResult(isGood = true, remarks = "MDP Ampere meter is functional."),
                        mdpVoltMeter = DieselMotorConditionResult(isGood = true, remarks = "MDP Volt meter is functional."),
                        mdpMainCircuitBreaker = DieselMotorConditionResult(isGood = true, remarks = "MDP Main Circuit Breaker is operational."),
                        safetyGrounding = DieselMotorConditionResult(isGood = true, remarks = "Grounding connection is secure."),
                        safetyLightningArrester = DieselMotorConditionResult(isGood = true, remarks = "Lightning arrester is properly installed."),
                        safetyEmergencyStop = DieselMotorConditionResult(isGood = true, remarks = "Emergency stop button functions correctly."),
                        safetyGovernor = DieselMotorConditionResult(isGood = true, remarks = "Governor linkage is free and clean."),
                        safetyThermostat = DieselMotorConditionResult(isGood = true, remarks = "Thermostat bulb is properly fitted."),
                        safetyWaterSignal = DieselMotorConditionResult(isGood = true, remarks = "Water level sensor is clean."),
                        safetyFanGuard = DieselMotorConditionResult(isGood = true, remarks = "Fan guard is intact and secure."),
                        safetySilencer = DieselMotorConditionResult(isGood = true, remarks = "Silencer is securely fastened."),
                        safetyVibrationDamper = DieselMotorConditionResult(isGood = true, remarks = "Vibration dampers are in good condition."),
                        safetyCircuitBreaker = DieselMotorConditionResult(isGood = true, remarks = "Circuit breakers are properly rated and functional."),
                        safetyAvr = DieselMotorConditionResult(isGood = true, remarks = "AVR is properly mounted and connected.")
                    ),
                    testingAndMeasurement = DieselMotorTestingAndMeasurement(
                        ndtTests = DieselMotorNdtTests(
                            shaftRpm = DieselMotorTestResult(result = "1500 RPM", remarks = "Engine speed stable at rated RPM."),
                            weldJoint = DieselMotorTestResult(result = "N/A", remarks = "No visible weld joints on critical components."),
                            noise = DieselMotorTestResult(result = "78 dB", remarks = "Noise level within acceptable limits at 1 meter."),
                            lighting = DieselMotorTestResult(result = "500 lux", remarks = "Adequate lighting in genset room."),
                            loadTest = DieselMotorTestResult(result = "200 KVA", remarks = "Generator delivered rated power under load.")
                        ),
                        safetyDeviceTests = DieselMotorSafetyDeviceTests(
                            governor = DieselMotorTestResult(result = "Functional", remarks = "Governor maintains stable engine speed."),
                            emergencyStop = DieselMotorTestResult(result = "Effective", remarks = "Emergency stop halts engine immediately."),
                            grounding = DieselMotorTestResult(result = "Good", remarks = "Grounding resistance is low."),
                            panelIndicators = DieselMotorTestResult(result = "All OK", remarks = "All meters and indicators show correct readings."),
                            pressureGauge = DieselMotorTestResult(result = "Normal", remarks = "Oil pressure is within normal range."),
                            temperatureIndicator = DieselMotorTestResult(result = "Normal", remarks = "Coolant temperature is within normal range."),
                            waterIndicator = DieselMotorTestResult(result = "Normal", remarks = "Water level indicator shows correct level."),
                            safetyValves = DieselMotorTestResult(result = "Functional", remarks = "Safety valves are operational."),
                            radiator = DieselMotorTestResult(result = "No Leaks", remarks = "Radiator shows no signs of leaks.")
                        ),
                        electricalMeasurements = DieselMotorElectricalMeasurements(
                            panelControl = DieselMotorPanelControl(
                                ka = "125A",
                                voltageRS = "390V",
                                voltageRT = "395V",
                                voltageST = "392V",
                                voltageRN = "225V",
                                voltageRG = "222V",
                                voltageNG = "0.5V"
                            ),
                            powerInfo = DieselMotorPowerInfo(
                                frequency = "50 Hz",
                                cosQ = "0.8",
                                ampereR = "280A",
                                ampereS = "285A",
                                ampereT = "282A",
                                remarks = "Electrical measurements are stable under load."
                            )
                        )
                    ),
                    mcbCalculation = DieselMotorMcbCalculation(
                        known = DieselMotorMcbKnownData(
                            phase = "3",
                            voltage = "400 V",
                            cosQ = "0.8",
                            generatorPowerKva = "200",
                            generatorPowerKw = "160"
                        ),
                        calculation = DieselMotorMcbCalculationResult(
                            formula = "I = P / (V * CosQ * sqrt(3))",
                            resultA = "160000 W / (400 V * 0.8 * 1.732)",
                            requiredAmps = "289.17 A"
                        ),
                        conclusion = "The calculated current (289.17 A) is within the capacity of the generator's rated current."
                    ),
                    noiseMeasurement = DieselMotorNoiseMeasurement(
                        pointA = DieselMotorMeasurementPoint(result = "75 dB", analysis = "Below the 85 dB limit."),
                        pointB = DieselMotorMeasurementPoint(result = "78 dB", analysis = "Below the 85 dB limit."),
                        pointC = DieselMotorMeasurementPoint(result = "76 dB", analysis = "Below the 85 dB limit."),
                        pointD = DieselMotorMeasurementPoint(result = "77 dB", analysis = "Below the 85 dB limit.")
                    ),
                    lightingMeasurement = DieselMotorLightingMeasurement(
                        pointA = DieselMotorMeasurementPoint(result = "500 lux", analysis = "Adequate for operations."),
                        pointB = DieselMotorMeasurementPoint(result = "520 lux", analysis = "Adequate for operations."),
                        pointC = DieselMotorMeasurementPoint(result = "510 lux", analysis = "Adequate for operations."),
                        pointD = DieselMotorMeasurementPoint(result = "530 lux", analysis = "Adequate for operations.")
                    ),
                    conclusion = DieselMotorConclusion(
                        summary = persistentListOf(
                            "The diesel motor generator is in good operational condition.",
                            "All safety devices are functional.",
                            "Electrical and mechanical parameters are within acceptable ranges."
                        ),
                        requirements = persistentListOf(
                            "Regularly check fuel and oil levels.",
                            "Perform scheduled maintenance every 250 operating hours."
                        )
                    )
                )
            )
        }
    }
}

@Immutable
data class DieselMotorInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val generalData: DieselMotorGeneralData = DieselMotorGeneralData(),
    val technicalData: DieselMotorTechnicalData = DieselMotorTechnicalData(),
    val visualInspection: DieselMotorVisualInspection = DieselMotorVisualInspection(),
    val testingAndMeasurement: DieselMotorTestingAndMeasurement = DieselMotorTestingAndMeasurement(),
    val mcbCalculation: DieselMotorMcbCalculation = DieselMotorMcbCalculation(),
    val noiseMeasurement: DieselMotorNoiseMeasurement = DieselMotorNoiseMeasurement(),
    val lightingMeasurement: DieselMotorLightingMeasurement = DieselMotorLightingMeasurement(),
    val conclusion: DieselMotorConclusion = DieselMotorConclusion()
)

@Immutable
data class DieselMotorGeneralData(
    val ownerName: String = "",
    val ownerAddress: String = "",
    val userInCharge: String = "",
    val userAddressInCharge: String = "",
    val subcontractorPersonInCharge: String = "",
    val unitLocation: String = "",
    val brandType: String = "",
    val driveType: String = "",
    val serialNumberUnitNumber: String = "",
    val manufacturer: String = "",
    val locationAndYearOfManufacture: String = "",
    val capacityRpm: String = "",
    val intendedUse: String = "",
    val pjk3SkpNo: String = "",
    val ak3SkpNo: String = "",
    val classification: String = "",
    val usagePermitNumber: String = "",
    val operatorName: String = "",
    val equipmentHistory: String = ""
)

@Immutable
data class DieselMotorTechnicalData(
    val dieselMotor: DieselMotorInfo = DieselMotorInfo(),
    val generator: DieselMotorGeneratorInfo = DieselMotorGeneratorInfo()
)

@Immutable
data class DieselMotorInfo(
    val brandModel: String = "",
    val manufacturer: String = "",
    val classification: String = "",
    val serialNumber: String = "",
    val powerRpm: String = "",
    val startingPower: String = "",
    val cylinderCount: String = ""
)

@Immutable
data class DieselMotorGeneratorInfo(
    val brandType: String = "",
    val manufacturer: String = "",
    val serialNumber: String = "",
    val power: String = "",
    val frequency: String = "",
    val rpm: String = "",
    val voltage: String = "",
    val powerFactor: String = "",
    val current: String = ""
)

@Immutable
data class DieselMotorConditionResult(
    val isGood: Boolean = false,
    val remarks: String = ""
)

@Immutable
data class DieselMotorVisualInspection(
    val baseConstructionFoundation: DieselMotorConditionResult = DieselMotorConditionResult(),
    val baseConstructionDieselHousing: DieselMotorConditionResult = DieselMotorConditionResult(),
    val baseConstructionSupport: DieselMotorConditionResult = DieselMotorConditionResult(),
    val baseConstructionAnchorBolt: DieselMotorConditionResult = DieselMotorConditionResult(),
    val structureDailyTank: DieselMotorConditionResult = DieselMotorConditionResult(),
    val structureMuffler: DieselMotorConditionResult = DieselMotorConditionResult(),
    val structureAirVessel: DieselMotorConditionResult = DieselMotorConditionResult(),
    val structurePanel: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemOil: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemOilStrainer: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemOilCooler: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemOilFilter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemByPassFilter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemSafetyValve: DieselMotorConditionResult = DieselMotorConditionResult(),
    val lubeSystemPacking: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemDailyTank: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemFuelInjector: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemConnections: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemFloatTank: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemFuelFilter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemFuelInjectorPump: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemMagneticScreen: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemGovernor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemThrottleShaft: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemRegulator: DieselMotorConditionResult = DieselMotorConditionResult(),
    val fuelSystemShutOffValve: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemFeedPump: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemFuelValve: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemPrimingPump: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemHeaterPlug: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemHeaterSwitch: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemPreHeater: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemWaterSignal: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemSwitch: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemBatteryPoles: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemThermostartTank: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemThermostart: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemHeaterSignal: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemThermostartSwitch: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemGlowPlug: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemSpeedSensor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemServiceMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemTempSensor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val startingSystemMotor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemWater: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemBolts: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemClamps: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemRadiator: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemThermostat: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemFan: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemFanGuard: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemFanRotation: DieselMotorConditionResult = DieselMotorConditionResult(),
    val coolingSystemBearing: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationPreCleaner: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationDustIndicator: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationAirCleaner: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationTurboCharger: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationClamps: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationAfterCooler: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationMuffler: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationSilencer: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationHeatDamper: DieselMotorConditionResult = DieselMotorConditionResult(),
    val airCirculationBolts: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsDamperBolts: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsSupport: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsFlyWheelHousing: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsFlyWheel: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsVibrationDamper: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsBeltAndPulley: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mainPartsCrankshaft: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorTerminalConnection: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorCableToPanel: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorPanelBoard: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorAmpereMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorVoltMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorFrequencyMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorCircuitBreaker: DieselMotorConditionResult = DieselMotorConditionResult(),
    val generatorOnOffSwitch: DieselMotorConditionResult = DieselMotorConditionResult(),
    val transmissionGear: DieselMotorConditionResult = DieselMotorConditionResult(),
    val transmissionBelt: DieselMotorConditionResult = DieselMotorConditionResult(),
    val transmissionChain: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpCableConnection: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpCondition: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpAmpereMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpVoltMeter: DieselMotorConditionResult = DieselMotorConditionResult(),
    val mdpMainCircuitBreaker: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyGrounding: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyLightningArrester: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyEmergencyStop: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyGovernor: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyThermostat: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyWaterSignal: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyFanGuard: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetySilencer: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyVibrationDamper: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyCircuitBreaker: DieselMotorConditionResult = DieselMotorConditionResult(),
    val safetyAvr: DieselMotorConditionResult = DieselMotorConditionResult()
)

@Immutable
data class DieselMotorTestingAndMeasurement(
    val ndtTests: DieselMotorNdtTests = DieselMotorNdtTests(),
    val safetyDeviceTests: DieselMotorSafetyDeviceTests = DieselMotorSafetyDeviceTests(),
    val electricalMeasurements: DieselMotorElectricalMeasurements = DieselMotorElectricalMeasurements()
)

@Immutable
data class DieselMotorTestResult(
    val result: String = "",
    val remarks: String = ""
)

@Immutable
data class DieselMotorNdtTests(
    val shaftRpm: DieselMotorTestResult = DieselMotorTestResult(),
    val weldJoint: DieselMotorTestResult = DieselMotorTestResult(),
    val noise: DieselMotorTestResult = DieselMotorTestResult(),
    val lighting: DieselMotorTestResult = DieselMotorTestResult(),
    val loadTest: DieselMotorTestResult = DieselMotorTestResult()
)

@Immutable
data class DieselMotorSafetyDeviceTests(
    val governor: DieselMotorTestResult = DieselMotorTestResult(),
    val emergencyStop: DieselMotorTestResult = DieselMotorTestResult(),
    val grounding: DieselMotorTestResult = DieselMotorTestResult(),
    val panelIndicators: DieselMotorTestResult = DieselMotorTestResult(),
    val pressureGauge: DieselMotorTestResult = DieselMotorTestResult(),
    val temperatureIndicator: DieselMotorTestResult = DieselMotorTestResult(),
    val waterIndicator: DieselMotorTestResult = DieselMotorTestResult(),
    val safetyValves: DieselMotorTestResult = DieselMotorTestResult(),
    val radiator: DieselMotorTestResult = DieselMotorTestResult()
)

@Immutable
data class DieselMotorElectricalMeasurements(
    val panelControl: DieselMotorPanelControl = DieselMotorPanelControl(),
    val powerInfo: DieselMotorPowerInfo = DieselMotorPowerInfo()
)

@Immutable
data class DieselMotorPanelControl(
    val ka: String = "",
    val voltageRS: String = "",
    val voltageRT: String = "",
    val voltageST: String = "",
    val voltageRN: String = "",
    val voltageRG: String = "",
    val voltageNG: String = ""
)

@Immutable
data class DieselMotorPowerInfo(
    val frequency: String = "",
    val cosQ: String = "",
    val ampereR: String = "",
    val ampereS: String = "",
    val ampereT: String = "",
    val remarks: String = ""
)

@Immutable
data class DieselMotorMcbCalculation(
    val known: DieselMotorMcbKnownData = DieselMotorMcbKnownData(),
    val calculation: DieselMotorMcbCalculationResult = DieselMotorMcbCalculationResult(),
    val conclusion: String = ""
)

@Immutable
data class DieselMotorMcbKnownData(
    val phase: String = "",
    val voltage: String = "",
    val cosQ: String = "",
    val generatorPowerKva: String = "",
    val generatorPowerKw: String = ""
)

@Immutable
data class DieselMotorMcbCalculationResult(
    val formula: String = "I = P / (V * CosQ * sqrt(3))",
    val resultA: String = "",
    val requiredAmps: String = ""
)

/**
 * Mewakili satu titik pengukuran dengan hasil dan analisanya.
 */
@Immutable
data class DieselMotorMeasurementPoint(
    val result: String = "",
    val analysis: String = ""
)

/**
 * Kelas data statis baru untuk Pengukuran Kebisingan.
 */
@Immutable
data class DieselMotorNoiseMeasurement(
    val pointA: DieselMotorMeasurementPoint = DieselMotorMeasurementPoint(),
    val pointB: DieselMotorMeasurementPoint = DieselMotorMeasurementPoint(),
    val pointC: DieselMotorMeasurementPoint = DieselMotorMeasurementPoint(),
    val pointD: DieselMotorMeasurementPoint = DieselMotorMeasurementPoint()
)

/**
 * Kelas data statis baru untuk Pengukuran Pencahayaan.
 */
@Immutable
data class DieselMotorLightingMeasurement(
    val pointA: DieselMotorMeasurementPoint = DieselMotorMeasurementPoint(),
    val pointB: DieselMotorMeasurementPoint = DieselMotorMeasurementPoint(),
    val pointC: DieselMotorMeasurementPoint = DieselMotorMeasurementPoint(),
    val pointD: DieselMotorMeasurementPoint = DieselMotorMeasurementPoint()
)

@Immutable
data class DieselMotorConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val requirements: ImmutableList<String> = persistentListOf()
)