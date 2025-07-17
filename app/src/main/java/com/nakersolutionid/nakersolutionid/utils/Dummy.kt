package com.nakersolutionid.nakersolutionid.utils

import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.* // Asumsikan semua data class berada dalam paket ini

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
}