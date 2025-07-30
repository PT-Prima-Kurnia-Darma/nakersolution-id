package com.nakersolutionid.nakersolutionid.features.report.paa.forklift

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Forklift inspection report.
 * This class is immutable.
 */
@Immutable
data class ForkliftUiState(
    val forkliftInspectionReport: ForkliftInspectionReport = ForkliftInspectionReport()
) {
    companion object {
        fun createDummyForkliftUiState(): ForkliftUiState {
            return ForkliftUiState(
                forkliftInspectionReport = ForkliftInspectionReport(
                    extraId = "FL-001",
                    moreExtraId = "BATCH-FL-XYZ",
                    equipmentType = "Forklift",
                    examinationType = "Annual Inspection",
                    generalData = ForkliftGeneralData(
                        owner = "Warehouse Logistics Inc.",
                        address = "15 Logistics Way, Distribution City, USA",
                        user = "Warehouse Operations Dept.",
                        personInCharge = "Mike Johnson",
                        unitLocation = "Warehouse Bay 3",
                        operatorName = "Sarah Connor",
                        driveType = "Electric",
                        manufacturer = "LiftMaster Corp.",
                        brandType = "Model X",
                        yearOfManufacture = "2019",
                        serialNumber = "FL-XM98765",
                        liftingCapacity = "2500 kg",
                        intendedUse = "Material Handling",
                        permitNumber = "FL-PERMIT-1122",
                        equipmentHistory = "Regular maintenance performed."
                    ),
                    technicalData = ForkliftTechnicalData(
                        specifications = ForkliftSpecifications(
                            serialNumber = "FL-XM98765",
                            capacity = "2500 kg",
                            attachment = "Standard Fork",
                            forkDimension = "1070 x 100 x 40 mm"
                        ),
                        speed = ForkliftSpeed(
                            lifting = "0.4 m/s",
                            lowering = "0.5 m/s",
                            travelling = "12 km/h"
                        ),
                        primeMover = ForkliftPrimeMover(
                            revolution = "3000 RPM",
                            brandType = "BrandE Electric Motor",
                            serialNumber = "EM-ABC123",
                            yearOfManufacture = "2019",
                            power = "5 kW",
                            numberOfCylinders = "N/A (Electric)"
                        ),
                        dimensions = ForkliftDimensions(
                            length = "2.5 m",
                            width = "1.2 m",
                            height = "2.1 m",
                            forkLiftingHeight = "4.5 m"
                        ),
                        tirePressure = ForkliftTirePressure(
                            driveWheel = "5.5 Bar",
                            steeringWheel = "5.0 Bar"
                        ),
                        driveWheel = ForkliftWheel(size = "12 inch", type = "Solid Rubber"),
                        steeringWheel = ForkliftWheel(size = "10 inch", type = "Solid Rubber"),
                        travellingBrake = ForkliftWheel(size = "N/A", type = "Electric Brake"),
                        hydraulicPump = ForkliftHydraulicPump(
                            pressure = "150 Bar",
                            type = "Gear Pump",
                            reliefValve = "Set at 160 Bar"
                        )
                    ),
                    visualInspection = ForkliftVisualInspection(
                        chassisReinforcementCorrosion = ForkliftInspectionResult(status = true, result = "No significant corrosion"),
                        chassisReinforcementCracks = ForkliftInspectionResult(status = false, result = "No cracks found"),
                        chassisReinforcementDeformation = ForkliftInspectionResult(status = false, result = "No deformation observed"),
                        counterweightCorrosion = ForkliftInspectionResult(status = true, result = "Minor surface corrosion on bottom"),
                        counterweightCondition = ForkliftInspectionResult(status = true, result = "Securely mounted"),
                        otherEquipmentFloorDeck = ForkliftInspectionResult(status = true, result = "Clean and intact"),
                        otherEquipmentStairs = ForkliftInspectionResult(status = true, result = "Good condition"),
                        otherEquipmentBindingBolts = ForkliftInspectionResult(status = true, result = "All bolts tight"),
                        otherEquipmentOperatorSeat = ForkliftInspectionResult(status = true, result = "Suspension intact"),
                        primeMoverCoolingSystem = ForkliftInspectionResult(status = true, result = "Coolant level adequate"),
                        primeMoverLubricantSystem = ForkliftInspectionResult(status = true, result = "Oil level OK"),
                        primeMoverFuelSystem = ForkliftInspectionResult(status = true, result = "No leaks"),
                        primeMoverAirIntakeSystem = ForkliftInspectionResult(status = true, result = "Filter clean"),
                        primeMoverExhaustSystem = ForkliftInspectionResult(status = true, result = "No excessive smoke"),
                        primeMoverStarterSystem = ForkliftInspectionResult(status = true, result = "Starts reliably"),
                        primeMoverElectricalBattery = ForkliftInspectionResult(status = true, result = "Charged and clean terminals"),
                        primeMoverElectricalStartingDynamo = ForkliftInspectionResult(status = true, result = "Functional"),
                        primeMoverElectricalAlternator = ForkliftInspectionResult(status = true, result = "Charging voltage normal"),
                        primeMoverElectricalBatteryCable = ForkliftInspectionResult(status = true, result = "Secure connections"),
                        primeMoverElectricalWiring = ForkliftInspectionResult(status = true, result = "No visible damage"),
                        primeMoverElectricalLighting = ForkliftInspectionResult(status = true, result = "Headlights and taillights operational"),
                        primeMoverElectricalSafetyLights = ForkliftInspectionResult(status = true, result = "Warning beacon functional"),
                        primeMoverElectricalHorn = ForkliftInspectionResult(status = true, result = "Audible"),
                        primeMoverElectricalFuse = ForkliftInspectionResult(status = true, result = "All fuses intact"),
                        dashboardTemperatureIndicator = ForkliftInspectionResult(status = true, result = "Reading within normal range"),
                        dashboardEngineOilPressure = ForkliftInspectionResult(status = true, result = "Indicator light off when running"),
                        dashboardHydraulicPressure = ForkliftInspectionResult(status = true, result = "Gauge reading OK"),
                        dashboardHourMeter = ForkliftInspectionResult(status = true, result = "Shows 4500 hours"),
                        dashboardGlowPlug = ForkliftInspectionResult(status = true, result = "Indicator operational"),
                        dashboardFuelIndicator = ForkliftInspectionResult(status = true, result = "Reading accurate"),
                        dashboardLoadIndicator = ForkliftInspectionResult(status = true, result = "Working"),
                        dashboardLoadChart = ForkliftInspectionResult(status = true, result = "Visible and legible"),
                        dashboardAmpereMeter = ForkliftInspectionResult(status = true, result = "Reading within normal range"),
                        powerTrainSteeringWheel = ForkliftInspectionResult(status = true, result = "No excessive play"),
                        powerTrainSteeringRod = ForkliftInspectionResult(status = true, result = "No damage"),
                        powerTrainSteeringGearBox = ForkliftInspectionResult(status = true, result = "No visible leaks"),
                        powerTrainSteeringPitmanArm = ForkliftInspectionResult(status = true, result = "Secure"),
                        powerTrainSteeringDragLink = ForkliftInspectionResult(status = true, result = "Secure"),
                        powerTrainSteeringTieRod = ForkliftInspectionResult(status = true, result = "Secure"),
                        powerTrainSteeringLubrication = ForkliftInspectionResult(status = true, result = "Grease points serviced"),
                        powerTrainWheelFront = ForkliftInspectionResult(status = true, result = "Good tread, no damage"),
                        powerTrainWheelRear = ForkliftInspectionResult(status = true, result = "Good tread, no damage"),
                        powerTrainWheelBindingBolts = ForkliftInspectionResult(status = true, result = "All tight"),
                        powerTrainWheelHub = ForkliftInspectionResult(status = true, result = "No cracks"),
                        powerTrainWheelLubrication = ForkliftInspectionResult(status = true, result = "Wheel bearings greased"),
                        powerTrainWheelMechanicalEquipment = ForkliftInspectionResult(status = true, result = "Hubs in good condition"),
                        powerTrainClutchHousing = ForkliftInspectionResult(status = true, result = "No visible leaks"),
                        powerTrainClutchCondition = ForkliftInspectionResult(status = true, result = "Engages smoothly"),
                        powerTrainClutchTransmissionOil = ForkliftInspectionResult(status = true, result = "Level OK"),
                        powerTrainClutchTransmissionLeakage = ForkliftInspectionResult(status = false, result = "No leaks observed"),
                        powerTrainClutchConnectingShaft = ForkliftInspectionResult(status = true, result = "Secure"),
                        powerTrainClutchMechanicalEquipment = ForkliftInspectionResult(status = true, result = "No visible damage"),
                        powerTrainDifferentialHousing = ForkliftInspectionResult(status = true, result = "No visible leaks"),
                        powerTrainDifferentialCondition = ForkliftInspectionResult(status = true, result = "No abnormal noise"),
                        powerTrainDifferentialOil = ForkliftInspectionResult(status = true, result = "Level OK"),
                        powerTrainDifferentialLeakage = ForkliftInspectionResult(status = false, result = "No leaks observed"),
                        powerTrainDifferentialConnectingShaft = ForkliftInspectionResult(status = true, result = "Secure"),
                        powerTrainBrakeMainCondition = ForkliftInspectionResult(status = true, result = "Effective stopping power"),
                        powerTrainBrakeHandbrakeCondition = ForkliftInspectionResult(status = true, result = "Holds firmly"),
                        powerTrainBrakeEmergencyCondition = ForkliftInspectionResult(status = true, result = "Functional"),
                        powerTrainBrakeLeakage = ForkliftInspectionResult(status = false, result = "No leaks observed"),
                        powerTrainBrakeMechanicalComponents = ForkliftInspectionResult(status = true, result = "No visible damage"),
                        powerTrainTransmissionHousing = ForkliftInspectionResult(status = true, result = "No visible leaks"),
                        powerTrainTransmissionOil = ForkliftInspectionResult(status = true, result = "Level OK"),
                        powerTrainTransmissionLeakage = ForkliftInspectionResult(status = false, result = "No leaks observed"),
                        powerTrainTransmissionMechanicalEquipment = ForkliftInspectionResult(status = true, result = "No visible damage"),
                        attachmentMastWear = ForkliftInspectionResult(status = true, result = "Minor wear on chain rollers"),
                        attachmentMastCracks = ForkliftInspectionResult(status = false, result = "No cracks found"),
                        attachmentMastDeformation = ForkliftInspectionResult(status = false, result = "No deformation observed"),
                        attachmentMastLubrication = ForkliftInspectionResult(status = true, result = "Lubrication points serviced"),
                        attachmentMastShaftAndBearing = ForkliftInspectionResult(status = true, result = "Good condition"),
                        attachmentLiftChainCondition = ForkliftInspectionResult(status = true, result = "No visible wear or damage"),
                        attachmentLiftChainDeformation = ForkliftInspectionResult(status = false, result = "No deformation observed"),
                        attachmentLiftChainLubrication = ForkliftInspectionResult(status = true, result = "Chain lubricated"),
                        personalBasketWorkFloorCorrosion = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketWorkFloorCracks = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketWorkFloorDeformation = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketWorkFloorBinding = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketFrameCorrosion = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketFrameCracks = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketFrameDeformation = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketFrameCrossBracing = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketFrameDiagonalBracing = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketBindingBoltCorrosion = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketBindingBoltCracks = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketBindingBoltDeformation = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketBindingBoltBinding = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketDoorCorrosion = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketDoorCracks = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketDoorDeformation = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketDoorBinding = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketHandrailCracks = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketHandrailWear = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketHandrailRailStraightness = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketHandrailRailJoint = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketHandrailAlignmentBetweenRails = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketHandrailGapBetweenRailJoints = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketHandrailRailFastener = ForkliftInspectionResult(status = false, result = "N/A"),
                        personalBasketHandrailRailStopper = ForkliftInspectionResult(status = false, result = "N/A"),
                        hydraulicTankLeakage = ForkliftInspectionResult(status = false, result = "No leaks observed"),
                        hydraulicTankOilLevel = ForkliftInspectionResult(status = true, result = "Level adequate"),
                        hydraulicTankOilCondition = ForkliftInspectionResult(status = true, result = "Clean"),
                        hydraulicTankSuctionLineCondition = ForkliftInspectionResult(status = true, result = "No kinks or damage"),
                        hydraulicTankReturnLineCondition = ForkliftInspectionResult(status = true, result = "No kinks or damage"),
                        hydraulicPumpLeakage = ForkliftInspectionResult(status = false, result = "No leaks observed"),
                        hydraulicPumpSuctionLineCondition = ForkliftInspectionResult(status = true, result = "No restrictions"),
                        hydraulicPumpPressureLineCondition = ForkliftInspectionResult(status = true, result = "No visible damage"),
                        hydraulicPumpFunction = ForkliftInspectionResult(status = true, result = "Operates smoothly"),
                        hydraulicPumpAbnormalNoise = ForkliftInspectionResult(status = false, result = "No abnormal noise"),
                        controlValveLeakage = ForkliftInspectionResult(status = false, result = "No leaks observed"),
                        controlValveLineCondition = ForkliftInspectionResult(status = true, result = "All lines connected"),
                        controlValveReliefValveFunction = ForkliftInspectionResult(status = true, result = "Operates correctly"),
                        controlValveAbnormalNoise = ForkliftInspectionResult(status = false, result = "No abnormal noise"),
                        controlValveLiftCylinderFunction = ForkliftInspectionResult(status = true, result = "Operates smoothly"),
                        controlValveTiltCylinderFunction = ForkliftInspectionResult(status = true, result = "Operates smoothly"),
                        controlValveSteeringCylinderFunction = ForkliftInspectionResult(status = true, result = "Operates smoothly"),
                        actuatorLeakage = ForkliftInspectionResult(status = false, result = "No leaks observed"),
                        actuatorLineCondition = ForkliftInspectionResult(status = true, result = "All lines connected"),
                        actuatorAbnormalNoise = ForkliftInspectionResult(status = false, result = "No abnormal noise")
                    ),
                    nonDestructiveExamination = ForkliftNde(
                        liftingChainInspection = ForkliftNdeChainInspection(
                            items = persistentListOf(
                                ForkliftNdeChainItem(
                                    chain = "Main Lift Chain", typeAndConstruction = "Alloy Steel, Welded",
                                    standardPitchMm = "12.7", measuredPitchMm = "12.75",
                                    standardPinMm = "8.0", measuredPinMm = "8.05",
                                    remarks = "Within acceptable wear limits"
                                )
                            )
                        ),
                        forkNDT = ForkliftNdeFork(
                            ndtType = "Dye Penetrant Testing",
                            items = persistentListOf(
                                ForkliftNdeForkItem(
                                    partInspected = "Fork Tip", location = "300mm from tip",
                                    finding = ForkliftInspectionResult(status = false, result = "No cracks detected")
                                )
                            )
                        )
                    ),
                    testing = ForkliftTesting(
                        engineOnInspection = ForkliftEngineOnInspection(
                            dynamoStarter = ForkliftInspectionResult(status = true, result = "Operational"),
                            instrumentIndicatorFunction = ForkliftInspectionResult(status = true, result = "All indicators functional"),
                            electricalEquipmentFunction = ForkliftInspectionResult(status = true, result = "All systems operational"),
                            engineOilLeakage = ForkliftInspectionResult(status = false, result = "No leaks"),
                            fuelLeakage = ForkliftInspectionResult(status = false, result = "No leaks"),
                            coolantLeakage = ForkliftInspectionResult(status = false, result = "No leaks"),
                            hydraulicOilLeakage = ForkliftInspectionResult(status = false, result = "No leaks"),
                            transmissionOilLeakage = ForkliftInspectionResult(status = false, result = "No leaks"),
                            finalDriveOilLeakage = ForkliftInspectionResult(status = false, result = "No leaks"),
                            brakeFluidLeakage = ForkliftInspectionResult(status = false, result = "No leaks"),
                            clutchFunction = ForkliftInspectionResult(status = true, result = "Smooth engagement"),
                            transmissionFunction = ForkliftInspectionResult(status = true, result = "Smooth gear changes"),
                            brakeFunction = ForkliftInspectionResult(status = true, result = "Effective and smooth"),
                            hornAlarmFunction = ForkliftInspectionResult(status = true, result = "Audible"),
                            lightsFunction = ForkliftInspectionResult(status = true, result = "All lights working"),
                            hydraulicSystemFunction = ForkliftInspectionResult(status = true, result = "Operates smoothly under load"),
                            powerSteeringFunction = ForkliftInspectionResult(status = true, result = "Responsive"),
                            liftCylinderFunction = ForkliftInspectionResult(status = true, result = "Smooth and controlled lift"),
                            tiltCylinderFunction = ForkliftInspectionResult(status = true, result = "Smooth tilt action"),
                            exhaustGasCondition = ForkliftInspectionResult(status = true, result = "Low smoke, normal color"),
                            controlLeversFunction = ForkliftInspectionResult(status = true, result = "Smooth and precise"),
                            engineNoise = ForkliftInspectionResult(status = true, result = "Normal running noise"),
                            turbochargerNoise = ForkliftInspectionResult(status = true, result = "Normal whistling sound"),
                            transmissionNoise = ForkliftInspectionResult(status = true, result = "No abnormal noise"),
                            hydraulicPumpNoise = ForkliftInspectionResult(status = true, result = "Slight hum, normal"),
                            guardNoise = ForkliftInspectionResult(status = true, result = "No rattling or loose parts")
                        ),
                        loadTest = ForkliftLoadTest(
                            items = persistentListOf(
                                ForkliftLoadTestItem(
                                    forkLiftingHeight = "4.0 m", testLoad = "2500 kg",
                                    travelingSpeed = "8 km/h", movement = "Forward & Backward",
                                    result = "OK", remarks = "Stable during test"
                                )
                            )
                        )
                    ),
                    conclusion = ForkliftConclusion(
                        summary = persistentListOf("The forklift is in good working condition and passed all safety and operational tests."),
                        recommendations = persistentListOf("Continue with scheduled preventive maintenance.", "Monitor wear on the main lift chain rollers.")
                    )
                )
            )
        }
    }
}

@Immutable
data class ForkliftInspectionReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val generalData: ForkliftGeneralData = ForkliftGeneralData(),
    val technicalData: ForkliftTechnicalData = ForkliftTechnicalData(),
    val visualInspection: ForkliftVisualInspection = ForkliftVisualInspection(),
    val nonDestructiveExamination: ForkliftNde = ForkliftNde(),
    val testing: ForkliftTesting = ForkliftTesting(),
    val conclusion: ForkliftConclusion = ForkliftConclusion()
)

@Immutable
data class ForkliftGeneralData(
    val owner: String = "",
    val address: String = "",
    val user: String = "",
    val personInCharge: String = "",
    val unitLocation: String = "",
    val operatorName: String = "",
    val driveType: String = "",
    val manufacturer: String = "",
    val brandType: String = "",
    val yearOfManufacture: String = "",
    val serialNumber: String = "",
    val liftingCapacity: String = "",
    val intendedUse: String = "",
    val permitNumber: String = "",
    val equipmentHistory: String = ""
)

@Immutable
data class ForkliftTechnicalData(
    val specifications: ForkliftSpecifications = ForkliftSpecifications(),
    val speed: ForkliftSpeed = ForkliftSpeed(),
    val primeMover: ForkliftPrimeMover = ForkliftPrimeMover(),
    val dimensions: ForkliftDimensions = ForkliftDimensions(),
    val tirePressure: ForkliftTirePressure = ForkliftTirePressure(),
    val driveWheel: ForkliftWheel = ForkliftWheel(),
    val steeringWheel: ForkliftWheel = ForkliftWheel(),
    val travellingBrake: ForkliftWheel = ForkliftWheel(),
    val hydraulicPump: ForkliftHydraulicPump = ForkliftHydraulicPump()
)

@Immutable
data class ForkliftSpecifications(
    val serialNumber: String = "",
    val capacity: String = "",
    val attachment: String = "",
    val forkDimension: String = ""
)

@Immutable
data class ForkliftSpeed(
    val lifting: String = "",
    val lowering: String = "",
    val travelling: String = ""
)

@Immutable
data class ForkliftPrimeMover(
    val revolution: String = "",
    val brandType: String = "",
    val serialNumber: String = "",
    val yearOfManufacture: String = "",
    val power: String = "",
    val numberOfCylinders: String = ""
)

@Immutable
data class ForkliftDimensions(
    val length: String = "",
    val width: String = "",
    val height: String = "",
    val forkLiftingHeight: String = ""
)

@Immutable
data class ForkliftTirePressure(
    val driveWheel: String = "",
    val steeringWheel: String = ""
)

@Immutable
data class ForkliftWheel(
    val size: String = "",
    val type: String = ""
)

@Immutable
data class ForkliftHydraulicPump(
    val pressure: String = "",
    val type: String = "",
    val reliefValve: String = ""
)

@Immutable
data class ForkliftInspectionResult(
    val status: Boolean = false,
    val result: String = ""
)

@Immutable
data class ForkliftVisualInspection(
    val chassisReinforcementCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val chassisReinforcementCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val chassisReinforcementDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val counterweightCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val counterweightCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val otherEquipmentFloorDeck: ForkliftInspectionResult = ForkliftInspectionResult(),
    val otherEquipmentStairs: ForkliftInspectionResult = ForkliftInspectionResult(),
    val otherEquipmentBindingBolts: ForkliftInspectionResult = ForkliftInspectionResult(),
    val otherEquipmentOperatorSeat: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverCoolingSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverLubricantSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverFuelSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverAirIntakeSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverExhaustSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverStarterSystem: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalBattery: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalStartingDynamo: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalAlternator: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalBatteryCable: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalWiring: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalLighting: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalSafetyLights: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalHorn: ForkliftInspectionResult = ForkliftInspectionResult(),
    val primeMoverElectricalFuse: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardTemperatureIndicator: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardEngineOilPressure: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardHydraulicPressure: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardHourMeter: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardGlowPlug: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardFuelIndicator: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardLoadIndicator: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardLoadChart: ForkliftInspectionResult = ForkliftInspectionResult(),
    val dashboardAmpereMeter: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringWheel: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringRod: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringGearBox: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringPitmanArm: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringDragLink: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringTieRod: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainSteeringLubrication: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelFront: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelRear: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelBindingBolts: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelHub: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelLubrication: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainWheelMechanicalEquipment: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchHousing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchTransmissionOil: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchTransmissionLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchConnectingShaft: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainClutchMechanicalEquipment: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialHousing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialOil: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainDifferentialConnectingShaft: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeMainCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeHandbrakeCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeEmergencyCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainBrakeMechanicalComponents: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainTransmissionHousing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainTransmissionOil: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainTransmissionLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerTrainTransmissionMechanicalEquipment: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastWear: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastLubrication: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentMastShaftAndBearing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentLiftChainCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentLiftChainDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val attachmentLiftChainLubrication: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketWorkFloorCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketWorkFloorCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketWorkFloorDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketWorkFloorBinding: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameCrossBracing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketFrameDiagonalBracing: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketBindingBoltCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketBindingBoltCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketBindingBoltDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketBindingBoltBinding: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketDoorCorrosion: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketDoorCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketDoorDeformation: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketDoorBinding: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailCracks: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailWear: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailRailStraightness: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailRailJoint: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailAlignmentBetweenRails: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailGapBetweenRailJoints: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailRailFastener: ForkliftInspectionResult = ForkliftInspectionResult(),
    val personalBasketHandrailRailStopper: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankOilLevel: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankOilCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankSuctionLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicTankReturnLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpSuctionLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpPressureLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpAbnormalNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveReliefValveFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveAbnormalNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveLiftCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveTiltCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlValveSteeringCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val actuatorLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val actuatorLineCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val actuatorAbnormalNoise: ForkliftInspectionResult = ForkliftInspectionResult()
)

@Immutable
data class ForkliftNde(
    val liftingChainInspection: ForkliftNdeChainInspection = ForkliftNdeChainInspection(),
    val forkNDT: ForkliftNdeFork = ForkliftNdeFork()
)

@Immutable
data class ForkliftNdeChainInspection(
    val items: ImmutableList<ForkliftNdeChainItem> = persistentListOf()
)

@Immutable
data class ForkliftNdeChainItem(
    val chain: String = "",
    val typeAndConstruction: String = "",
    val standardPitchMm: String = "",
    val measuredPitchMm: String = "",
    val standardPinMm: String = "",
    val measuredPinMm: String = "",
    val remarks: String = ""
)

@Immutable
data class ForkliftNdeFork(
    val ndtType: String = "",
    val items: ImmutableList<ForkliftNdeForkItem> = persistentListOf()
)

@Immutable
data class ForkliftNdeForkItem(
    val partInspected: String = "",
    val location: String = "",
    val finding: ForkliftInspectionResult = ForkliftInspectionResult()
)

@Immutable
data class ForkliftTesting(
    val engineOnInspection: ForkliftEngineOnInspection = ForkliftEngineOnInspection(),
    val loadTest: ForkliftLoadTest = ForkliftLoadTest()
)

@Immutable
data class ForkliftEngineOnInspection(
    val dynamoStarter: ForkliftInspectionResult = ForkliftInspectionResult(),
    val instrumentIndicatorFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val electricalEquipmentFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val engineOilLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val fuelLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val coolantLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicOilLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val transmissionOilLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val finalDriveOilLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val brakeFluidLeakage: ForkliftInspectionResult = ForkliftInspectionResult(),
    val clutchFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val transmissionFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val brakeFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hornAlarmFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val lightsFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicSystemFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val powerSteeringFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val liftCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val tiltCylinderFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val exhaustGasCondition: ForkliftInspectionResult = ForkliftInspectionResult(),
    val controlLeversFunction: ForkliftInspectionResult = ForkliftInspectionResult(),
    val engineNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val turbochargerNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val transmissionNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val hydraulicPumpNoise: ForkliftInspectionResult = ForkliftInspectionResult(),
    val guardNoise: ForkliftInspectionResult = ForkliftInspectionResult()
)

@Immutable
data class ForkliftLoadTest(
    val items: ImmutableList<ForkliftLoadTestItem> = persistentListOf()
)

@Immutable
data class ForkliftLoadTestItem(
    val forkLiftingHeight: String = "",
    val testLoad: String = "",
    val travelingSpeed: String = "",
    val movement: String = "",
    val result: String = "",
    val remarks: String = ""
)

@Immutable
data class ForkliftConclusion(
    val summary: ImmutableList<String> = persistentListOf(),
    val recommendations: ImmutableList<String> = persistentListOf()
)