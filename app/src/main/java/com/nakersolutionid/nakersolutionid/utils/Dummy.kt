package com.nakersolutionid.nakersolutionid.utils

import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.*
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.*

object Dummy {
    fun getDummyElevatorUiState(): ElevatorUiState {
        return ElevatorUiState(
            typeInspection = "Regular Inspection",
            eskOrElevType = "EKE-12345",
            generalData = GeneralDataUiState(
                ownerName = "PT. Maju Jaya",
                ownerAddress = "Jl. Merdeka No. 1, Jakarta Pusat",
                nameUsageLocation = "Gedung Perkantoran Sinar Mas",
                addressUsageLocation = "Jl. Sudirman No. 5, Jakarta Selatan",
                manufacturerOrInstaller = "PT. Elevatorindo Global",
                elevatorType = "Traction Elevator",
                brandOrType = "Otis",
                countryAndYear = "USA, 2018",
                serialNumber = "SN-OTIS-98765",
                capacity = "1000 kg",
                speed = "2.5 m/s",
                floorsServed = "LG, 1-10",
                permitNumber = "PERMIT-INV-2023-001",
                inspectionDate = "2025-07-15"
            ),
            technicalDocumentInspection = TechnicalDocumentInspectionUiState(
                designDrawing = true,
                technicalCalculation = true,
                materialCertificate = true,
                controlPanelDiagram = true,
                asBuiltDrawing = false, // Contoh salah satu bernilai false
                componentCertificates = true,
                safeWorkProcedure = true
            ),
            inspectionAndTesting = InspectionAndTestingUiState(
                machineRoomAndMachinery = MachineRoomAndMachineryUiState(
                    machineMounting = ResultStatusUiState("Secure", true),
                    mechanicalBrake = ResultStatusUiState("Functional", true),
                    electricalBrake = ResultStatusUiState("Operational", true),
                    machineRoomConstruction = ResultStatusUiState("Good", true),
                    machineRoomClearance = ResultStatusUiState("Sufficient", true),
                    machineRoomImplementation = ResultStatusUiState("Compliant", true),
                    ventilation = ResultStatusUiState("Adequate", true),
                    machineRoomDoor = ResultStatusUiState("Secure", true),
                    mainPowerPanelPosition = ResultStatusUiState("Accessible", true),
                    rotatingPartsGuard = ResultStatusUiState("Present", true),
                    ropeHoleGuard = ResultStatusUiState("Present", true),
                    machineRoomAccessLadder = ResultStatusUiState("Good Condition", true),
                    floorLevelDifference = ResultStatusUiState("Within Limit", true),
                    fireExtinguisher = ResultStatusUiState("Accessible", true),
                    machineRoomless = MachineRoomlessUiState(
                        panelPlacement = ResultStatusUiState("Convenient", true),
                        lightingWorkArea = ResultStatusUiState("Sufficient", true),
                        lightingBetweenWorkArea = ResultStatusUiState("Sufficient", true),
                        manualBrakeRelease = ResultStatusUiState("Accessible", true),
                        fireExtinguisherPlacement = ResultStatusUiState("Accessible", true)
                    ),
                    emergencyStopSwitch = ResultStatusUiState("Accessible", true)
                ),
                suspensionRopesAndBelts = SuspensionRopesAndBeltsUiState(
                    condition = ResultStatusUiState("Good", true),
                    chainUsage = ResultStatusUiState("Not Applicable", false), // Contoh tidak berlaku
                    safetyFactor = ResultStatusUiState("Sufficient", true),
                    ropeWithCounterweight = ResultStatusUiState("Good", true),
                    ropeWithoutCounterweight = ResultStatusUiState("Good", true),
                    belt = ResultStatusUiState("Good", true),
                    slackRopeDevice = ResultStatusUiState("Functional", true)
                ),
                drumsAndSheaves = DrumsAndSheavesUiState(
                    drumGrooves = ResultStatusUiState("Good", true),
                    passengerDrumDiameter = ResultStatusUiState("Within Spec", true),
                    governorDrumDiameter = ResultStatusUiState("Within Spec", true)
                ),
                hoistwayAndPit = HoistwayAndPitUiState(
                    construction = ResultStatusUiState("Good", true),
                    walls = ResultStatusUiState("Smooth", true),
                    inclinedElevatorTrackBed = ResultStatusUiState("Not Applicable", false),
                    cleanliness = ResultStatusUiState("Clean", true),
                    lighting = ResultStatusUiState("Sufficient", true),
                    emergencyDoorNonStop = ResultStatusUiState("Not Applicable", false),
                    emergencyDoorSize = ResultStatusUiState("Standard", true),
                    emergencyDoorSafetySwitch = ResultStatusUiState("Functional", true),
                    emergencyDoorBridge = ResultStatusUiState("Present", true),
                    carTopClearance = ResultStatusUiState("Sufficient", true),
                    pitClearance = ResultStatusUiState("Sufficient", true),
                    pitLadder = ResultStatusUiState("Good Condition", true),
                    pitBelowWorkingArea = ResultStatusUiState("Clear", true),
                    pitAccessSwitch = ResultStatusUiState("Functional", true),
                    pitScreen = ResultStatusUiState("Present", true),
                    hoistwayDoorLeaf = ResultStatusUiState("Good", true),
                    hoistwayDoorInterlock = ResultStatusUiState("Functional", true),
                    floorLeveling = ResultStatusUiState("Accurate", true),
                    hoistwaySeparatorBeam = ResultStatusUiState("Present", true),
                    inclinedElevatorStairs = ResultStatusUiState("Not Applicable", false)
                ),
                car = CarUiState(
                    frame = ResultStatusUiState("Solid", true),
                    body = ResultStatusUiState("Good Condition", true),
                    wallHeight = ResultStatusUiState("Standard", true),
                    floorArea = ResultStatusUiState("Sufficient", true),
                    carAreaExpansion = ResultStatusUiState("Not Applicable", false),
                    carDoor = ResultStatusUiState("Good", true),
                    carDoorSpecs = CarDoorSpecsUiState(
                        size = ResultStatusUiState("Standard", true),
                        lockAndSwitch = ResultStatusUiState("Functional", true),
                        sillClearance = ResultStatusUiState("Within Limit", true)
                    ),
                    carToBeamClearance = ResultStatusUiState("Sufficient", true),
                    alarmBell = ResultStatusUiState("Functional", true),
                    backupPowerARD = ResultStatusUiState("Operational", true),
                    intercom = ResultStatusUiState("Functional", true),
                    ventilation = ResultStatusUiState("Adequate", true),
                    emergencyLighting = ResultStatusUiState("Functional", true),
                    operatingPanel = ResultStatusUiState("Good Condition", true),
                    carPositionIndicator = ResultStatusUiState("Accurate", true),
                    carSignage = CarSignageUiState(
                        manufacturerName = ResultStatusUiState("Present", true),
                        loadCapacity = ResultStatusUiState("Visible", true),
                        noSmokingSign = ResultStatusUiState("Present", true),
                        overloadIndicator = ResultStatusUiState("Functional", true),
                        doorOpenCloseButtons = ResultStatusUiState("Clear", true),
                        floorButtons = ResultStatusUiState("Responsive", true),
                        alarmButton = ResultStatusUiState("Accessible", true),
                        twoWayIntercom = ResultStatusUiState("Functional", true)
                    ),
                    carRoofStrength = ResultStatusUiState("Tested", true),
                    carTopEmergencyExit = ResultStatusUiState("Present", true),
                    carSideEmergencyExit = ResultStatusUiState("Not Present", false), // Contoh tidak ada
                    carTopGuardRail = ResultStatusUiState("Present", true),
                    guardRailHeight300to850 = ResultStatusUiState("Compliant", true),
                    guardRailHeightOver850 = ResultStatusUiState("Compliant", true),
                    carTopLighting = ResultStatusUiState("Sufficient", true),
                    manualOperationButtons = ResultStatusUiState("Accessible", true),
                    carInterior = ResultStatusUiState("Clean", true)
                ),
                governorAndSafetyBrake = GovernorAndSafetyBrakeUiState(
                    governorRopeClamp = ResultStatusUiState("Functional", true),
                    governorSwitch = ResultStatusUiState("Functional", true),
                    safetyBrakeSpeed = ResultStatusUiState("Within Limit", true),
                    safetyBrakeType = ResultStatusUiState("Mechanical", true),
                    safetyBrakeMechanism = ResultStatusUiState("Lubricated", true),
                    progressiveSafetyBrake = ResultStatusUiState("Functional", true),
                    instantaneousSafetyBrake = ResultStatusUiState("Not Applicable", false),
                    safetyBrakeOperation = ResultStatusUiState("Smooth", true),
                    electricalCutoutSwitch = ResultStatusUiState("Functional", true),
                    limitSwitch = ResultStatusUiState("Functional", true),
                    overloadDevice = ResultStatusUiState("Functional", true)
                ),
                counterweightGuideRailsAndBuffers = CounterweightGuideRailsAndBuffersUiState(
                    counterweightMaterial = ResultStatusUiState("Cast Iron", true),
                    counterweightGuardScreen = ResultStatusUiState("Present", true),
                    guideRailConstruction = ResultStatusUiState("Good", true),
                    bufferType = ResultStatusUiState("Spring Buffer", true),
                    bufferFunction = ResultStatusUiState("Tested", true),
                    bufferSafetySwitch = ResultStatusUiState("Functional", true)
                ),
                electricalInstallation = ElectricalInstallationUiState(
                    installationStandard = ResultStatusUiState("IEC Compliant", true),
                    electricalPanel = ResultStatusUiState("Organized", true),
                    backupPowerARD = ResultStatusUiState("Tested", true),
                    groundingCable = ResultStatusUiState("Connected", true),
                    fireAlarmConnection = ResultStatusUiState("Linked", true),
                    fireServiceElevator = FireServiceElevatorUiState(
                        backupPower = ResultStatusUiState("Available", true),
                        specialOperation = ResultStatusUiState("Functional", true),
                        fireSwitch = ResultStatusUiState("Accessible", true),
                        label = ResultStatusUiState("Present", true),
                        electricalFireResistance = ResultStatusUiState("Compliant", true),
                        hoistwayWallFireResistance = ResultStatusUiState("Compliant", true),
                        carSize = ResultStatusUiState("Standard", true),
                        doorSize = ResultStatusUiState("Standard", true),
                        travelTime = ResultStatusUiState("Within Limit", true),
                        evacuationFloor = ResultStatusUiState("Designated", true)
                    ),
                    accessibilityElevator = AccessibilityElevatorUiState(
                        operatingPanel = ResultStatusUiState("Accessible", true),
                        panelHeight = ResultStatusUiState("Compliant", true),
                        doorOpenTime = ResultStatusUiState("Adequate", true),
                        doorWidth = ResultStatusUiState("Sufficient", true),
                        audioInformation = ResultStatusUiState("Available", true),
                        label = ResultStatusUiState("Visible", true)
                    ),
                    seismicSensor = SeismicSensorUiState(
                        availability = ResultStatusUiState("Present", true),
                        function = ResultStatusUiState("Tested", true)
                    )
                )
            ),
            conclusion = "Elevator in good working condition, minor recommendations for future maintenance.",
            createdAt = "2025-07-16T10:00:00Z",
            isLoading = false
        )
    }

    fun getDummyEskalatorUiState(): EskalatorUiState {
        return EskalatorUiState(
            isLoading = false,
            eskalatorData = EskalatorGeneralData(
                equipmentType = "Eskalator",
                examinationType = "Routine Inspection",
                conclusion = "Eskalator is in good condition and operating normally.",
                companyData = EskalatorCompanyData(
                    companyOrBuildingName = "Plaza Serpong",
                    usageAddress = "Jl. MH Thamrin No. 1, Tangerang Selatan",
                    safetyObjectTypeAndNumber = "ESK-45678",
                    intendedUse = "Commercial Building",
                    usagePermit = "PERMIT-ESK-2024-002",
                    inspectionDate = "2024-08-01"
                ),
                technicalData = EskalatorTechnicalData(
                    manufacturer = "Hyundai",
                    brand = "Hyundai Elevator",
                    countryAndYear = "South Korea, 2015",
                    serialNumber = "SN-HYUNDAI-54321",
                    transports = "Passenger",
                    capacity = "750 kg",
                    liftHeight = "15 meters",
                    speed = "0.75 m/s",
                    driveType = "Standard Drive",
                    motorCurrent = "15A",
                    motorPower = "7.5 kW",
                    safetyDevices = "Emergency Stop, Handrail Safety Device, Step Chain Safety Device"
                ),
                inspectionAndTesting = EskalatorInspectionAndTesting(
                    frameAndMachineRoom = FrameAndMachineRoom(
                        frame = EskalatorResultStatus("Solid", true),
                        supportBeams = EskalatorResultStatus("Stable", true),
                        machineRoomCondition = EskalatorResultStatus("Clean", true),
                        machineRoomClearance = EskalatorResultStatus("Sufficient", true),
                        machineRoomLighting = EskalatorResultStatus("Adequate", true),
                        machineCoverPlate = EskalatorResultStatus("Secure", true),
                        pitCondition = EskalatorResultStatus("Dry", true),
                        pitClearance = EskalatorResultStatus("Sufficient", true),
                        pitStepCoverPlate = EskalatorResultStatus("In Place", true)
                    ),
                    driveEquipment = DriveEquipment(
                        driveMachine = EskalatorResultStatus("Lubricated", true),
                        speedUnder30Degrees = EskalatorResultStatus("0.75 m/s", true),
                        speed30to35Degrees = EskalatorResultStatus("Not Applicable", false),
                        travelatorSpeed = EskalatorResultStatus("Not Applicable", false),
                        stoppingDistance0_5 = EskalatorResultStatus("0.5 m", true),
                        stoppingDistance0_75 = EskalatorResultStatus("0.75 m", true),
                        stoppingDistance0_90 = EskalatorResultStatus("0.90 m", true),
                        driveChain = EskalatorResultStatus("Good Condition", true),
                        chainBreakingStrength = EskalatorResultStatus("Sufficient", true)
                    ),
                    stepsOrPallets = StepsOrPallets(
                        stepMaterial = EskalatorResultStatus("Aluminum", true),
                        stepDimensions = EskalatorResultStatus("Standard", true),
                        palletDimensions = EskalatorResultStatus("Standard", true),
                        stepSurface = EskalatorResultStatus("Non-slip", true),
                        stepLevelness = EskalatorResultStatus("Accurate", true),
                        skirtBrush = EskalatorResultStatus("Present and Intact", true),
                        stepWheels = EskalatorResultStatus("Well-lubricated", true)
                    ),
                    landingArea = LandingArea(
                        landingPlates = EskalatorResultStatus("Clean and Intact", true),
                        combTeeth = EskalatorResultStatus("Undamaged", true),
                        combCondition = EskalatorResultStatus("Good", true),
                        landingCover = EskalatorResultStatus("Secure", true),
                        landingAccessArea = EskalatorResultStatus("Clear", true)
                    ),
                    balustrade = Balustrade(
                        balustradePanel = BalustradePanel(
                            material = EskalatorResultStatus("Tempered Glass", true),
                            height = EskalatorResultStatus("1100 mm", true),
                            sidePressure = EskalatorResultStatus("Within Limit", true),
                            verticalPressure = EskalatorResultStatus("Within Limit", true)
                        ),
                        skirtPanel = EskalatorResultStatus("Intact", true),
                        skirtPanelFlexibility = EskalatorResultStatus("Good", true),
                        stepToSkirtClearance = EskalatorResultStatus("Sufficient", true)
                    ),
                    handrail = Handrail(
                        handrailCondition = EskalatorResultStatus("Good", true),
                        handrailSpeedSynchronization = EskalatorResultStatus("Synchronized", true),
                        handrailWidth = EskalatorResultStatus("Standard", true)
                    ),
                    runway = Runway(
                        beamStrengthAndPosition = EskalatorResultStatus("Good", true),
                        pitWallCondition = EskalatorResultStatus("Clean", true),
                        escalatorFrameEnclosure = EskalatorResultStatus("Secure", true),
                        lighting = EskalatorResultStatus("Adequate", true),
                        headroomClearance = EskalatorResultStatus("Sufficient", true),
                        balustradeToObjectClearance = EskalatorResultStatus("Sufficient", true),
                        antiClimbDeviceHeight = EskalatorResultStatus("Compliant", true),
                        ornamentPlacement = EskalatorResultStatus("Not Present", false),
                        outdoorClearance = EskalatorResultStatus("Not Applicable", false)
                    ),
                    safetyEquipment = SafetyEquipment(
                        operationControlKey = EskalatorResultStatus("Accessible", true),
                        emergencyStopSwitch = EskalatorResultStatus("Functional", true),
                        stepChainSafetyDevice = EskalatorResultStatus("Functional", true),
                        driveChainSafetyDevice = EskalatorResultStatus("Functional", true),
                        stepSafetyDevice = EskalatorResultStatus("Functional", true),
                        handrailSafetyDevice = EskalatorResultStatus("Functional", true),
                        reversalStopDevice = EskalatorResultStatus("Functional", true),
                        handrailEntryGuard = EskalatorResultStatus("Present", true),
                        combPlateSafetyDevice = EskalatorResultStatus("Functional", true),
                        innerDeckingBrush = EskalatorResultStatus("Present", true),
                        stopButtons = EskalatorResultStatus("Accessible", true)
                    ),
                    electricalInstallation = ElectricalInstallation(
                        installationStandard = EskalatorResultStatus("IEC Compliant", true),
                        electricalPanel = EskalatorResultStatus("Organized", true),
                        groundingCable = EskalatorResultStatus("Connected", true),
                        fireAlarmConnection = EskalatorResultStatus("Linked", true)
                    ),
                    outdoorSpecifics = OutdoorSpecifics(
                        pitWaterPump = EskalatorResultStatus("Functional", true),
                        weatherproofComponents = EskalatorResultStatus("Compliant", true)
                    ),
                    userSafetySignage = UserSafetySignage(
                        noBulkyItems = EskalatorResultStatus("Visible", true),
                        noJumping = EskalatorResultStatus("Visible", true),
                        unattendedChildren = EskalatorResultStatus("Visible", true),
                        noTrolleysOrStrollers = EskalatorResultStatus("Visible", true),
                        noLeaning = EskalatorResultStatus("Visible", true),
                        noSteppingOnSkirt = EskalatorResultStatus("Visible", true),
                        softSoleFootwearWarning = EskalatorResultStatus("Visible", true),
                        noSittingOnSteps = EskalatorResultStatus("Visible", true),
                        holdHandrail = EskalatorResultStatus("Visible", true)
                    )
                ),
                testingSummary = EskalatorTestingSummary(
                    safetyDevices = "All safety devices tested and functional.",
                    noLoadTest = "Passed",
                    brakeTest = "Passed"
                )
            )
        )
    }
}
