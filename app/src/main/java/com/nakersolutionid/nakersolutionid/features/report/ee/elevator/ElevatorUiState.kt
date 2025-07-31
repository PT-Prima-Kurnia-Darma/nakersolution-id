package com.nakersolutionid.nakersolutionid.features.report.ee.elevator

import androidx.compose.runtime.Immutable

@Immutable
data class ResultStatusUiState(
    val result: String = "",
    val status: Boolean = false
)

@Immutable
data class GeneralDataUiState(
    val ownerName: String = "",
    val ownerAddress: String = "",
    val nameUsageLocation: String = "",
    val addressUsageLocation: String = "",
    val manufacturerOrInstaller: String = "",
    val elevatorType: String = "",
    val brandOrType: String = "",
    val countryAndYear: String = "",
    val serialNumber: String = "",
    val capacity: String = "",
    val speed: String = "",
    val floorsServed: String = "",
    val permitNumber: String = "",
    val inspectionDate: String = ""
)

@Immutable
data class TechnicalDocumentInspectionUiState(
    val designDrawing: Boolean = false,
    val technicalCalculation: Boolean = false,
    val materialCertificate: Boolean = false,
    val controlPanelDiagram: Boolean = false,
    val asBuiltDrawing: Boolean = false,
    val componentCertificates: Boolean = false,
    val safeWorkProcedure: Boolean = false
)

@Immutable
data class MachineRoomlessUiState(
    val panelPlacement: ResultStatusUiState = ResultStatusUiState(),
    val lightingWorkArea: ResultStatusUiState = ResultStatusUiState(),
    val lightingBetweenWorkArea: ResultStatusUiState = ResultStatusUiState(),
    val manualBrakeRelease: ResultStatusUiState = ResultStatusUiState(),
    val fireExtinguisherPlacement: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class MachineRoomAndMachineryUiState(
    val machineMounting: ResultStatusUiState = ResultStatusUiState(),
    val mechanicalBrake: ResultStatusUiState = ResultStatusUiState(),
    val electricalBrake: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomConstruction: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomClearance: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomImplementation: ResultStatusUiState = ResultStatusUiState(),
    val ventilation: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomDoor: ResultStatusUiState = ResultStatusUiState(),
    val mainPowerPanelPosition: ResultStatusUiState = ResultStatusUiState(),
    val rotatingPartsGuard: ResultStatusUiState = ResultStatusUiState(),
    val ropeHoleGuard: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomAccessLadder: ResultStatusUiState = ResultStatusUiState(),
    val floorLevelDifference: ResultStatusUiState = ResultStatusUiState(),
    val fireExtinguisher: ResultStatusUiState = ResultStatusUiState(),
    val machineRoomless: MachineRoomlessUiState = MachineRoomlessUiState(),
    val emergencyStopSwitch: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class SuspensionRopesAndBeltsUiState(
    val condition: ResultStatusUiState = ResultStatusUiState(),
    val chainUsage: ResultStatusUiState = ResultStatusUiState(),
    val safetyFactor: ResultStatusUiState = ResultStatusUiState(),
    val ropeWithCounterweight: ResultStatusUiState = ResultStatusUiState(),
    val ropeWithoutCounterweight: ResultStatusUiState = ResultStatusUiState(),
    val belt: ResultStatusUiState = ResultStatusUiState(),
    val slackRopeDevice: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class DrumsAndSheavesUiState(
    val drumGrooves: ResultStatusUiState = ResultStatusUiState(),
    val passengerDrumDiameter: ResultStatusUiState = ResultStatusUiState(),
    val governorDrumDiameter: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class HoistwayAndPitUiState(
    val construction: ResultStatusUiState = ResultStatusUiState(),
    val walls: ResultStatusUiState = ResultStatusUiState(),
    val inclinedElevatorTrackBed: ResultStatusUiState = ResultStatusUiState(),
    val cleanliness: ResultStatusUiState = ResultStatusUiState(),
    val lighting: ResultStatusUiState = ResultStatusUiState(),
    val emergencyDoorNonStop: ResultStatusUiState = ResultStatusUiState(),
    val emergencyDoorSize: ResultStatusUiState = ResultStatusUiState(),
    val emergencyDoorSafetySwitch: ResultStatusUiState = ResultStatusUiState(),
    val emergencyDoorBridge: ResultStatusUiState = ResultStatusUiState(),
    val carTopClearance: ResultStatusUiState = ResultStatusUiState(),
    val pitClearance: ResultStatusUiState = ResultStatusUiState(),
    val pitLadder: ResultStatusUiState = ResultStatusUiState(),
    val pitBelowWorkingArea: ResultStatusUiState = ResultStatusUiState(),
    val pitAccessSwitch: ResultStatusUiState = ResultStatusUiState(),
    val pitScreen: ResultStatusUiState = ResultStatusUiState(),
    val hoistwayDoorLeaf: ResultStatusUiState = ResultStatusUiState(),
    val hoistwayDoorInterlock: ResultStatusUiState = ResultStatusUiState(),
    val floorLeveling: ResultStatusUiState = ResultStatusUiState(),
    val hoistwaySeparatorBeam: ResultStatusUiState = ResultStatusUiState(),
    val inclinedElevatorStairs: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class CarDoorSpecsUiState(
    val size: ResultStatusUiState = ResultStatusUiState(),
    val lockAndSwitch: ResultStatusUiState = ResultStatusUiState(),
    val sillClearance: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class CarSignageUiState(
    val manufacturerName: ResultStatusUiState = ResultStatusUiState(),
    val loadCapacity: ResultStatusUiState = ResultStatusUiState(),
    val noSmokingSign: ResultStatusUiState = ResultStatusUiState(),
    val overloadIndicator: ResultStatusUiState = ResultStatusUiState(),
    val doorOpenCloseButtons: ResultStatusUiState = ResultStatusUiState(),
    val floorButtons: ResultStatusUiState = ResultStatusUiState(),
    val alarmButton: ResultStatusUiState = ResultStatusUiState(),
    val twoWayIntercom: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class CarUiState(
    val frame: ResultStatusUiState = ResultStatusUiState(),
    val body: ResultStatusUiState = ResultStatusUiState(),
    val wallHeight: ResultStatusUiState = ResultStatusUiState(),
    val floorArea: ResultStatusUiState = ResultStatusUiState(),
    val carAreaExpansion: ResultStatusUiState = ResultStatusUiState(),
    val carDoor: ResultStatusUiState = ResultStatusUiState(),
    val carDoorSpecs: CarDoorSpecsUiState = CarDoorSpecsUiState(),
    val carToBeamClearance: ResultStatusUiState = ResultStatusUiState(),
    val alarmBell: ResultStatusUiState = ResultStatusUiState(),
    val backupPowerARD: ResultStatusUiState = ResultStatusUiState(),
    val intercom: ResultStatusUiState = ResultStatusUiState(),
    val ventilation: ResultStatusUiState = ResultStatusUiState(),
    val emergencyLighting: ResultStatusUiState = ResultStatusUiState(),
    val operatingPanel: ResultStatusUiState = ResultStatusUiState(),
    val carPositionIndicator: ResultStatusUiState = ResultStatusUiState(),
    val carSignage: CarSignageUiState = CarSignageUiState(),
    val carRoofStrength: ResultStatusUiState = ResultStatusUiState(),
    val carTopEmergencyExit: ResultStatusUiState = ResultStatusUiState(),
    val carSideEmergencyExit: ResultStatusUiState = ResultStatusUiState(),
    val carTopGuardRail: ResultStatusUiState = ResultStatusUiState(),
    val guardRailHeight300to850: ResultStatusUiState = ResultStatusUiState(),
    val guardRailHeightOver850: ResultStatusUiState = ResultStatusUiState(),
    val carTopLighting: ResultStatusUiState = ResultStatusUiState(),
    val manualOperationButtons: ResultStatusUiState = ResultStatusUiState(),
    val carInterior: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class GovernorAndSafetyBrakeUiState(
    val governorRopeClamp: ResultStatusUiState = ResultStatusUiState(),
    val governorSwitch: ResultStatusUiState = ResultStatusUiState(),
    val safetyBrakeSpeed: ResultStatusUiState = ResultStatusUiState(),
    val safetyBrakeType: ResultStatusUiState = ResultStatusUiState(),
    val safetyBrakeMechanism: ResultStatusUiState = ResultStatusUiState(),
    val progressiveSafetyBrake: ResultStatusUiState = ResultStatusUiState(),
    val instantaneousSafetyBrake: ResultStatusUiState = ResultStatusUiState(),
    val safetyBrakeOperation: ResultStatusUiState = ResultStatusUiState(),
    val electricalCutoutSwitch: ResultStatusUiState = ResultStatusUiState(),
    val limitSwitch: ResultStatusUiState = ResultStatusUiState(),
    val overloadDevice: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class CounterweightGuideRailsAndBuffersUiState(
    val counterweightMaterial: ResultStatusUiState = ResultStatusUiState(),
    val counterweightGuardScreen: ResultStatusUiState = ResultStatusUiState(),
    val guideRailConstruction: ResultStatusUiState = ResultStatusUiState(),
    val bufferType: ResultStatusUiState = ResultStatusUiState(),
    val bufferFunction: ResultStatusUiState = ResultStatusUiState(),
    val bufferSafetySwitch: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class FireServiceElevatorUiState(
    val backupPower: ResultStatusUiState = ResultStatusUiState(),
    val specialOperation: ResultStatusUiState = ResultStatusUiState(),
    val fireSwitch: ResultStatusUiState = ResultStatusUiState(),
    val label: ResultStatusUiState = ResultStatusUiState(),
    val electricalFireResistance: ResultStatusUiState = ResultStatusUiState(),
    val hoistwayWallFireResistance: ResultStatusUiState = ResultStatusUiState(),
    val carSize: ResultStatusUiState = ResultStatusUiState(),
    val doorSize: ResultStatusUiState = ResultStatusUiState(),
    val travelTime: ResultStatusUiState = ResultStatusUiState(),
    val evacuationFloor: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class AccessibilityElevatorUiState(
    val operatingPanel: ResultStatusUiState = ResultStatusUiState(),
    val panelHeight: ResultStatusUiState = ResultStatusUiState(),
    val doorOpenTime: ResultStatusUiState = ResultStatusUiState(),
    val doorWidth: ResultStatusUiState = ResultStatusUiState(),
    val audioInformation: ResultStatusUiState = ResultStatusUiState(),
    val label: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class SeismicSensorUiState(
    val availability: ResultStatusUiState = ResultStatusUiState(),
    val function: ResultStatusUiState = ResultStatusUiState()
)

@Immutable
data class ElectricalInstallationUiState(
    val installationStandard: ResultStatusUiState = ResultStatusUiState(),
    val electricalPanel: ResultStatusUiState = ResultStatusUiState(),
    val backupPowerARD: ResultStatusUiState = ResultStatusUiState(),
    val groundingCable: ResultStatusUiState = ResultStatusUiState(),
    val fireAlarmConnection: ResultStatusUiState = ResultStatusUiState(),
    val fireServiceElevator: FireServiceElevatorUiState = FireServiceElevatorUiState(),
    val accessibilityElevator: AccessibilityElevatorUiState = AccessibilityElevatorUiState(),
    val seismicSensor: SeismicSensorUiState = SeismicSensorUiState()
)

@Immutable
data class InspectionAndTestingUiState(
    val machineRoomAndMachinery: MachineRoomAndMachineryUiState = MachineRoomAndMachineryUiState(),
    val suspensionRopesAndBelts: SuspensionRopesAndBeltsUiState = SuspensionRopesAndBeltsUiState(),
    val drumsAndSheaves: DrumsAndSheavesUiState = DrumsAndSheavesUiState(),
    val hoistwayAndPit: HoistwayAndPitUiState = HoistwayAndPitUiState(),
    val car: CarUiState = CarUiState(),
    val governorAndSafetyBrake: GovernorAndSafetyBrakeUiState = GovernorAndSafetyBrakeUiState(),
    val counterweightGuideRailsAndBuffers: CounterweightGuideRailsAndBuffersUiState = CounterweightGuideRailsAndBuffersUiState(),
    val electricalInstallation: ElectricalInstallationUiState = ElectricalInstallationUiState()
)

@Immutable
data class ElevatorUiState(
    val typeInspection: String = "",
    val eskOrElevType: String = "",
    val generalData: GeneralDataUiState = GeneralDataUiState(),
    val technicalDocumentInspection: TechnicalDocumentInspectionUiState = TechnicalDocumentInspectionUiState(),
    val inspectionAndTesting: InspectionAndTestingUiState = InspectionAndTestingUiState(),
    val conclusion: String = "",
    val recommendation: String = "",
    val createdAt: String = "",
    val isLoading: Boolean = false,
    val extraId: String = "",
    val moreExtraId: String = ""
) {
    companion object {
        fun createDummyElevatorUiState(): ElevatorUiState {
            return ElevatorUiState(
                typeInspection = "Dummy Inspection Type",
                eskOrElevType = "Dummy Elevator Type",
                generalData = GeneralDataUiState(
                    ownerName = "John Doe",
                    ownerAddress = "123 Main St, Anytown, USA",
                    nameUsageLocation = "Office Building",
                    addressUsageLocation = "456 Business Ave, Anytown, USA",
                    manufacturerOrInstaller = "Elevator Corp",
                    elevatorType = "Traction",
                    brandOrType = "BrandX",
                    countryAndYear = "USA, 2020",
                    serialNumber = "SN123456789",
                    capacity = "1000 kg",
                    speed = "1.0 m/s",
                    floorsServed = "G+10",
                    permitNumber = "PERMIT-9876",
                    inspectionDate = "2023-10-27"
                ),
                technicalDocumentInspection = TechnicalDocumentInspectionUiState(
                    designDrawing = true,
                    technicalCalculation = true,
                    materialCertificate = false,
                    controlPanelDiagram = true,
                    asBuiltDrawing = false,
                    componentCertificates = true,
                    safeWorkProcedure = true
                ),
                inspectionAndTesting = InspectionAndTestingUiState(
                    machineRoomAndMachinery = MachineRoomAndMachineryUiState(
                        machineMounting = ResultStatusUiState("Good", true),
                        mechanicalBrake = ResultStatusUiState("Compliant", true),
                        electricalBrake = ResultStatusUiState("Functional", true),
                        machineRoomConstruction = ResultStatusUiState("Solid", true),
                        machineRoomClearance = ResultStatusUiState("Adequate", true),
                        machineRoomImplementation = ResultStatusUiState("Standard", true),
                        ventilation = ResultStatusUiState("Effective", true),
                        machineRoomDoor = ResultStatusUiState("Secure", true),
                        mainPowerPanelPosition = ResultStatusUiState("Accessible", true),
                        rotatingPartsGuard = ResultStatusUiState("Present", true),
                        ropeHoleGuard = ResultStatusUiState("Present", true),
                        machineRoomAccessLadder = ResultStatusUiState("Secure", true),
                        floorLevelDifference = ResultStatusUiState("Minimal", true),
                        fireExtinguisher = ResultStatusUiState("Available", true),
                        machineRoomless = MachineRoomlessUiState(
                            panelPlacement = ResultStatusUiState("Convenient", true),
                            lightingWorkArea = ResultStatusUiState("Sufficient", true),
                            lightingBetweenWorkArea = ResultStatusUiState("Adequate", true),
                            manualBrakeRelease = ResultStatusUiState("Accessible", true),
                            fireExtinguisherPlacement = ResultStatusUiState("Visible", true)
                        ),
                        emergencyStopSwitch = ResultStatusUiState("Functional", true)
                    ),
                    suspensionRopesAndBelts = SuspensionRopesAndBeltsUiState(
                        condition = ResultStatusUiState("Good", true),
                        chainUsage = ResultStatusUiState("Not Used", false),
                        safetyFactor = ResultStatusUiState("Sufficient", true),
                        ropeWithCounterweight = ResultStatusUiState("Good", true),
                        ropeWithoutCounterweight = ResultStatusUiState("N/A", false),
                        belt = ResultStatusUiState("N/A", false),
                        slackRopeDevice = ResultStatusUiState("Present and Functional", true)
                    ),
                    drumsAndSheaves = DrumsAndSheavesUiState(
                        drumGrooves = ResultStatusUiState("Good Condition", true),
                        passengerDrumDiameter = ResultStatusUiState("Correct Diameter", true),
                        governorDrumDiameter = ResultStatusUiState("Correct Diameter", true)
                    ),
                    hoistwayAndPit = HoistwayAndPitUiState(
                        construction = ResultStatusUiState("Solid", true),
                        walls = ResultStatusUiState("Smooth", true),
                        inclinedElevatorTrackBed = ResultStatusUiState("N/A", false),
                        cleanliness = ResultStatusUiState("Clean", true),
                        lighting = ResultStatusUiState("Adequate", true),
                        emergencyDoorNonStop = ResultStatusUiState("Compliant", true),
                        emergencyDoorSize = ResultStatusUiState("Sufficient", true),
                        emergencyDoorSafetySwitch = ResultStatusUiState("Functional", true),
                        emergencyDoorBridge = ResultStatusUiState("Present", true),
                        carTopClearance = ResultStatusUiState("Adequate", true),
                        pitClearance = ResultStatusUiState("Sufficient", true),
                        pitLadder = ResultStatusUiState("Secure", true),
                        pitBelowWorkingArea = ResultStatusUiState("Clear", true),
                        pitAccessSwitch = ResultStatusUiState("Functional", true),
                        pitScreen = ResultStatusUiState("Present", true),
                        hoistwayDoorLeaf = ResultStatusUiState("Good", true),
                        hoistwayDoorInterlock = ResultStatusUiState("Functional", true),
                        floorLeveling = ResultStatusUiState("Accurate", true),
                        hoistwaySeparatorBeam = ResultStatusUiState("Present", true),
                        inclinedElevatorStairs = ResultStatusUiState("N/A", false)
                    ),
                    car = CarUiState(
                        frame = ResultStatusUiState("Sturdy", true),
                        body = ResultStatusUiState("Good Condition", true),
                        wallHeight = ResultStatusUiState("Standard", true),
                        floorArea = ResultStatusUiState("Adequate", true),
                        carAreaExpansion = ResultStatusUiState("N/A", false),
                        carDoor = ResultStatusUiState("Functional", true),
                        carDoorSpecs = CarDoorSpecsUiState(
                            size = ResultStatusUiState("Sufficient", true),
                            lockAndSwitch = ResultStatusUiState("Functional", true),
                            sillClearance = ResultStatusUiState("Within Limits", true)
                        ),
                        carToBeamClearance = ResultStatusUiState("Adequate", true),
                        alarmBell = ResultStatusUiState("Functional", true),
                        backupPowerARD = ResultStatusUiState("Functional", true),
                        intercom = ResultStatusUiState("Functional", true),
                        ventilation = ResultStatusUiState("Effective", true),
                        emergencyLighting = ResultStatusUiState("Functional", true),
                        operatingPanel = ResultStatusUiState("Accessible", true),
                        carPositionIndicator = ResultStatusUiState("Clear", true),
                        carSignage = CarSignageUiState(
                            manufacturerName = ResultStatusUiState("Present", true),
                            loadCapacity = ResultStatusUiState("Visible", true),
                            noSmokingSign = ResultStatusUiState("Present", true),
                            overloadIndicator = ResultStatusUiState("Functional", true),
                            doorOpenCloseButtons = ResultStatusUiState("Clearly Marked", true),
                            floorButtons = ResultStatusUiState("Clearly Marked", true),
                            alarmButton = ResultStatusUiState("Clearly Marked", true),
                            twoWayIntercom = ResultStatusUiState("Functional", true)
                        ),
                        carRoofStrength = ResultStatusUiState("Sufficient", true),
                        carTopEmergencyExit = ResultStatusUiState("Present", true),
                        carSideEmergencyExit = ResultStatusUiState("N/A", false),
                        carTopGuardRail = ResultStatusUiState("Present", true),
                        guardRailHeight300to850 = ResultStatusUiState("Compliant", true),
                        guardRailHeightOver850 = ResultStatusUiState("N/A", false),
                        carTopLighting = ResultStatusUiState("Adequate", true),
                        manualOperationButtons = ResultStatusUiState("Accessible", true),
                        carInterior = ResultStatusUiState("Clean and Well-Maintained", true)
                    ),
                    governorAndSafetyBrake = GovernorAndSafetyBrakeUiState(
                        governorRopeClamp = ResultStatusUiState("Secure", true),
                        governorSwitch = ResultStatusUiState("Functional", true),
                        safetyBrakeSpeed = ResultStatusUiState("Within Limit", true),
                        safetyBrakeType = ResultStatusUiState("Type A", true),
                        safetyBrakeMechanism = ResultStatusUiState("Lubricated", true),
                        progressiveSafetyBrake = ResultStatusUiState("Present", true),
                        instantaneousSafetyBrake = ResultStatusUiState("N/A", false),
                        safetyBrakeOperation = ResultStatusUiState("Smooth", true),
                        electricalCutoutSwitch = ResultStatusUiState("Functional", true),
                        limitSwitch = ResultStatusUiState("Functional", true),
                        overloadDevice = ResultStatusUiState("Functional", true)
                    ),
                    counterweightGuideRailsAndBuffers = CounterweightGuideRailsAndBuffersUiState(
                        counterweightMaterial = ResultStatusUiState("Cast Iron", true),
                        counterweightGuardScreen = ResultStatusUiState("Present", true),
                        guideRailConstruction = ResultStatusUiState("Solid", true),
                        bufferType = ResultStatusUiState("Spring", true),
                        bufferFunction = ResultStatusUiState("Functional", true),
                        bufferSafetySwitch = ResultStatusUiState("Functional", true)
                    ),
                    electricalInstallation = ElectricalInstallationUiState(
                        installationStandard = ResultStatusUiState("IEC Compliant", true),
                        electricalPanel = ResultStatusUiState("Organized", true),
                        backupPowerARD = ResultStatusUiState("Functional", true),
                        groundingCable = ResultStatusUiState("Connected", true),
                        fireAlarmConnection = ResultStatusUiState("Connected", true),
                        fireServiceElevator = FireServiceElevatorUiState(
                            backupPower = ResultStatusUiState("Present", true),
                            specialOperation = ResultStatusUiState("Enabled", true),
                            fireSwitch = ResultStatusUiState("Accessible", true),
                            label = ResultStatusUiState("Fire Service", true),
                            electricalFireResistance = ResultStatusUiState("Class A", true),
                            hoistwayWallFireResistance = ResultStatusUiState("Class B", true),
                            carSize = ResultStatusUiState("Sufficient", true),
                            doorSize = ResultStatusUiState("Sufficient", true),
                            travelTime = ResultStatusUiState("Normal", true),
                            evacuationFloor = ResultStatusUiState("Yes", true)
                        ),
                        accessibilityElevator = AccessibilityElevatorUiState(
                            operatingPanel = ResultStatusUiState("Accessible", true),
                            panelHeight = ResultStatusUiState("Optimal", true),
                            doorOpenTime = ResultStatusUiState("Adequate", true),
                            doorWidth = ResultStatusUiState("Sufficient", true),
                            audioInformation = ResultStatusUiState("Available", true),
                            label = ResultStatusUiState("Accessible", true)
                        ),
                        seismicSensor = SeismicSensorUiState(
                            availability = ResultStatusUiState("Available", true),
                            function = ResultStatusUiState("Functional", true)
                        )
                    )
                ),
                conclusion = "Elevator passed all major tests.",
                recommendation = "Perform routine maintenance every 6 months.",
                createdAt = "2023-10-27T10:00:00Z",
                isLoading = false,
                extraId = "ELEV-001",
                moreExtraId = "BATCH-XYZ"
            )
        }
    }
}
