package com.nakersolutionid.nakersolutionid.utils

import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.AccessibilityElevatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.CarDoorSpecsUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.CarSignageUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.CarUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.CounterweightGuideRailsAndBuffersUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.DrumsAndSheavesUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.ElectricalInstallationUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.ElevatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.FireServiceElevatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.GeneralDataUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.GovernorAndSafetyBrakeUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.HoistwayAndPitUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.InspectionAndTestingUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.MachineRoomAndMachineryUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.MachineRoomlessUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.ResultStatusUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.SeismicSensorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.SuspensionRopesAndBeltsUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.TechnicalDocumentInspectionUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.Balustrade
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.BalustradePanel
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.DriveEquipment
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.ElectricalInstallation
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorCompanyData
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorGeneralData
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorInspectionAndTesting
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorResultStatus
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorTechnicalData
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorTestingSummary
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.FrameAndMachineRoom
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.Handrail
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.LandingArea
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.OutdoorSpecifics
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.Runway
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.SafetyEquipment
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.StepsOrPallets
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.UserSafetySignage
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalConclusion
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalDocumentExaminationPart1
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalDocumentExaminationPart2
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalGeneralData
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalInitialDocumentVerification
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalInspectionAndTesting
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalLoadSystem
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalMainTesting
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalPowerSource
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalSdpFrontView
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalSdpInternalInspections
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalSdpInternalViewItem
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalSdpTerminalSystem
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalSdpTesting
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalSdpVisualInspection
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalTechnicalData
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalTestResult
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalUiState
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalVerificationResult
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionCheckResult
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionClientData
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionConclusion
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionConditionResult
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionGroundingMeasurementItem
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionGroundingSystemVisualInspection
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionGroundingTestItem
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionOtherStandardsInspection
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionPhysicalInspection
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionServiceProviderData
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionTechnicalData
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionTestingResults
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionUiState
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionAlarmInstallationTesting
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionApar
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionAutomaticFireAlarmSpecifications
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionBuildingData
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionBuildingHydrant
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionChecklistResult
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionCompanyData
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionConclusion
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionConstruction
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionDetector
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionDieselPump
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionDocumentChecklist
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionElectricPump
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionEquipment
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionFireServiceConnection
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionGroundReservoir
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionHydrantOperationalTestItem
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionHydrantSystemInstallation
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionJockeyPump
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionLandingValve
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionMcfa
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionPumpFunctionTestItem
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionSystemComponent
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionUiState
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionYardHydrant
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftConclusion
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftDimensions
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftEngineOnInspection
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftGeneralData
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftHydraulicPump
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftInspectionResult
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftLoadTest
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftLoadTestItem
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftNde
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftNdeChainInspection
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftNdeChainItem
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftNdeFork
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftNdeForkItem
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftPrimeMover
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftSpecifications
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftSpeed
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftTechnicalData
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftTesting
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftTirePressure
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftVisualInspection
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftWheel
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneBrake
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneChain
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneConclusion
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneControllerBrake
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneDeflectionItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneDeflectionStandard
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneDriveMotor
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneDynamicTest
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneDynamicTestItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneDynamicWithLoad
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneDynamicWithoutLoad
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneGeneralData
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneHook
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneInspectionResult
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneLoadTestResult
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneMovementData
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNde
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeGirder
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeGirderItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeHook
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeMeasurement
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeWireRope
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeWireRopeItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneStartingResistor
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneStaticTest
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneTechSpecs
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneTechnicalData
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneTesting
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneVisualInspection
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneWireRope
import kotlinx.collections.immutable.persistentListOf

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
                    chainUsage = ResultStatusUiState(
                        "Not Applicable",
                        false
                    ), // Contoh tidak berlaku
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
                    carSideEmergencyExit = ResultStatusUiState(
                        "Not Present",
                        false
                    ), // Contoh tidak ada
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

    fun getDummyElectricUiState(): ElectricalUiState {
        return ElectricalUiState(
            isLoading = false,
            electricalInspectionReport = ElectricalInspectionReport(
                generalData = ElectricalGeneralData(
                    companyName = "PT. Listrik Jaya",
                    companyAddress = "Jl. Sudirman No. 1, Jakarta Pusat",
                    installationType = "Gedung Perkantoran",
                    examinationType = "Pemeriksaan Rutin",
                    businessField = "Properti",
                    safetyServiceProvider = "PT. Aman Sentosa",
                    ohsExpert = "Budi Santoso",
                    permitNumber = "IZIN-2024-001",
                    inspectionDate = "2024-08-15",
                    inspectionLocation = "Gedung A, Lantai 1-5"
                ),
                technicalData = ElectricalTechnicalData(
                    powerSource = ElectricalPowerSource(
                        plnKva = "1000",
                        dieselGeneratorKva = "500"
                    ),
                    loadSystem = ElectricalLoadSystem(
                        totalInstalledLoadWatt = "800000",
                        lightingPowerWatt = "300000",
                        powerLoadWatt = "500000"
                    ),
                    currentVoltageType = "AC 3 Fasa"
                ),
                initialDocumentVerification = ElectricalInitialDocumentVerification(
                    singleLineDiagram = ElectricalVerificationResult(true, "Lengkap dan sesuai"),
                    layout = ElectricalVerificationResult(true, "Sesuai dengan kondisi lapangan"),
                    usagePermitCertificate = ElectricalVerificationResult(true, "Masih berlaku"),
                    technicianLicense = ElectricalVerificationResult(
                        false,
                        "Beberapa teknisi belum memiliki lisensi terbaru"
                    )
                ),
                inspectionAndTesting = ElectricalInspectionAndTesting(
                    documentExaminationPart1 = ElectricalDocumentExaminationPart1(
                        planningHasPermit = ElectricalTestResult(true, "Ada"),
                        locationMapExists = ElectricalTestResult(true, "Ada"),
                        singleLineDiagramExists = ElectricalTestResult(true, "Ada"),
                        layoutDiagramExists = ElectricalTestResult(true, "Ada"),
                        wiringDiagramExists = ElectricalTestResult(true, "Ada"),
                        areaClarificationDrawingExists = ElectricalTestResult(true, "Ada"),
                        panelComponentListExists = ElectricalTestResult(true, "Ada"),
                        shortCircuitCalculationExists = ElectricalTestResult(true, "Ada"),
                        manualBookExists = ElectricalTestResult(true, "Ada"),
                        maintenanceAndOperationBookExists = ElectricalTestResult(true, "Ada"),
                        warningSignsInstalled = ElectricalTestResult(true, "Ada"),
                        manufacturerCertificationExists = ElectricalTestResult(true, "Ada"),
                        equipmentTechnicalSpecsExists = ElectricalTestResult(true, "Ada"),
                        equipmentCertificationAndSpecsExists = ElectricalTestResult(true, "Ada"),
                        powerRecapitulationCalculationExists = ElectricalTestResult(true, "Ada"),
                        dailyRecordExists = ElectricalTestResult(true, "Ada"),
                        panelCoverCondition = ElectricalTestResult(true, "Baik"),
                        otherSupportingDataExists = ElectricalTestResult(false, "Tidak ada")
                    ),
                    documentExaminationPart2 = ElectricalDocumentExaminationPart2(
                        unitConstructionLvmdpSdp = ElectricalTestResult(true, "Sesuai"),
                        mountingAndPlacement = ElectricalTestResult(true, "Sesuai"),
                        nameplateVerification = ElectricalTestResult(true, "Sesuai"),
                        areaClassification = ElectricalTestResult(true, "Sesuai"),
                        protectionAgainstElectricShock = ElectricalTestResult(true, "Sesuai"),
                        radiationProtection = ElectricalTestResult(true, "Sesuai"),
                        panelDoorStaysOpen = ElectricalTestResult(true, "Sesuai"),
                        boltsAndScrewsTightened = ElectricalTestResult(true, "Sesuai"),
                        busbarInsulation = ElectricalTestResult(true, "Sesuai"),
                        busbarClearance = ElectricalTestResult(true, "Sesuai"),
                        cableInstallation = ElectricalTestResult(true, "Sesuai"),
                        panelDoorCableProtection = ElectricalTestResult(true, "Sesuai"),
                        fuseReplacementSafety = ElectricalTestResult(true, "Sesuai"),
                        cableTerminalProtection = ElectricalTestResult(true, "Sesuai"),
                        measuringInstrumentsMarking = ElectricalTestResult(true, "Sesuai"),
                        equipmentAndTerminalLabeling = ElectricalTestResult(true, "Sesuai"),
                        incomingOutgoingCableInstallation = ElectricalTestResult(true, "Sesuai"),
                        busbarSize = ElectricalTestResult(true, "Sesuai"),
                        busbarCleanliness = ElectricalTestResult(true, "Sesuai"),
                        busbarPhaseMarking = ElectricalTestResult(true, "Sesuai"),
                        groundingCableInstallation = ElectricalTestResult(true, "Sesuai"),
                        panelDoorInstallation = ElectricalTestResult(true, "Sesuai"),
                        sparepartsSpecificationCompliance = ElectricalTestResult(true, "Sesuai"),
                        safetyFacilitiesAndDangerSigns = ElectricalTestResult(true, "Sesuai"),
                        circuitBreakerDataCheck = ElectricalTestResult(true, "Sesuai"),
                        circuitBreakerCurrentRating = ElectricalTestResult(true, "Sesuai"),
                        circuitBreakerVoltageRating = ElectricalTestResult(true, "Sesuai"),
                        circuitBreakerInterruptingRating = ElectricalTestResult(true, "Sesuai"),
                        circuitBreakerControlVoltage = ElectricalTestResult(true, "Sesuai"),
                        circuitBreakerManufacturer = ElectricalTestResult(true, "Sesuai"),
                        circuitBreakerType = ElectricalTestResult(true, "Sesuai"),
                        circuitBreakerSerialNumber = ElectricalTestResult(true, "Sesuai")
                    ),
                    mainTesting = ElectricalMainTesting(
                        insulationResistance = ElectricalTestResult(true, "Passed"),
                        groundingResistance = ElectricalTestResult(true, "Passed"),
                        circuitBreakerEquipment = ElectricalTestResult(true, "Passed"),
                        currentTransformer = ElectricalTestResult(true, "Passed"),
                        voltageTransformer = ElectricalTestResult(true, "Passed"),
                        measuringInstrument = ElectricalTestResult(true, "Passed"),
                        fuseRating = ElectricalTestResult(true, "Passed"),
                        mechanicalBreaker = ElectricalTestResult(true, "Passed"),
                        cableTerminal = ElectricalTestResult(true, "Passed"),
                        terminalMarking = ElectricalTestResult(true, "Passed"),
                        interlockSystem = ElectricalTestResult(true, "Passed"),
                        auxiliarySwitch = ElectricalTestResult(true, "Passed"),
                        mechanicalTripFunction = ElectricalTestResult(true, "Passed"),
                        overloadTripTest = ElectricalTestResult(true, "Passed"),
                        reversePowerRelayTest = ElectricalTestResult(true, "Passed"),
                        reverseCurrentRelayTest = ElectricalTestResult(true, "Passed"),
                        breakerTripTest = ElectricalTestResult(true, "Passed"),
                        temperatureMeasurement = ElectricalTestResult(true, "Passed"),
                        indicatorLightFunction = ElectricalTestResult(true, "Passed"),
                        meterDeviationTest = ElectricalTestResult(true, "Passed"),
                        synchronizationFunctionTest = ElectricalTestResult(true, "Passed"),
                        conductorAmpacity = ElectricalTestResult(true, "Passed"),
                        protectionRating = ElectricalTestResult(true, "Passed"),
                        voltageDrop = ElectricalTestResult(true, "Passed"),
                        lossConnection = ElectricalTestResult(true, "Passed")
                    ),
                    sdpVisualInspection = ElectricalSdpVisualInspection(
                        frontView = ElectricalSdpFrontView(
                            panelIndicatorLights = ElectricalTestResult(true, "Baik"),
                            panelDoorClearance = ElectricalTestResult(true, "Baik"),
                            lighting = ElectricalTestResult(true, "Baik"),
                            lightingProductionRoom = ElectricalTestResult(true, "Baik"),
                            lightingOffice = ElectricalTestResult(true, "Baik"),
                            lightingMainPanel = ElectricalTestResult(true, "Baik"),
                            lightingWarehouse = ElectricalTestResult(true, "Baik"),
                            unusedItemsClearance = ElectricalTestResult(true, "Baik"),
                            dangerSignOnMainPanelDoor = ElectricalTestResult(true, "Baik")
                        ),
                        internalViews = persistentListOf(
                            ElectricalSdpInternalViewItem(
                                floor = 1,
                                inspections = ElectricalSdpInternalInspections(
                                    touchVoltageProtectionCover = ElectricalTestResult(
                                        true,
                                        "Baik"
                                    ),
                                    sldAndMaintenanceCard = ElectricalTestResult(true, "Baik"),
                                    bondingCable = ElectricalTestResult(true, "Baik"),
                                    labeling = ElectricalTestResult(true, "Baik"),
                                    cableColorCode = ElectricalTestResult(true, "Baik"),
                                    panelCleanliness = ElectricalTestResult(true, "Baik"),
                                    installationNeatness = ElectricalTestResult(true, "Baik")
                                )
                            )
                        ),
                        terminalSystem = ElectricalSdpTerminalSystem(
                            busbar = ElectricalTestResult(true, "Baik"),
                            breaker = ElectricalTestResult(true, "Baik"),
                            cableLugs = ElectricalTestResult(true, "Baik"),
                            groundingSystem = ElectricalTestResult(true, "Baik"),
                            busbarToBusbarDistance = ElectricalTestResult(true, "Baik")
                        )
                    ),
                    sdpTesting = ElectricalSdpTesting(
                        voltagePhaseRSTN = ElectricalTestResult(true, "Passed"),
                        currentPhaseRSTN = ElectricalTestResult(true, "Passed"),
                        meteringFunction = ElectricalTestResult(true, "Passed"),
                        panelLabeling = ElectricalTestResult(true, "Passed"),
                        dangerSignOnPanelDoor = ElectricalTestResult(true, "Passed"),
                        selectorSwitchAndLock = ElectricalTestResult(true, "Passed"),
                        conductorTerminalHeat = ElectricalTestResult(true, "Passed"),

                        groundingTest = ElectricalTestResult(true, "Passed"),
                        mainConductorAmpacity = ElectricalTestResult(true, "Passed"),
                        mainProtectionRating = ElectricalTestResult(true, "Passed")
                    )
                ),
                conclusion = ElectricalConclusion(
                    findings = persistentListOf(
                        "Technician license for 2 staff members has expired.",
                        "Minor corrosion found on busbar in Panel C."
                    ),
                    summary = persistentListOf(
                        "The electrical installation is generally in good condition and complies with most safety standards.",
                        "All critical systems are functioning correctly."
                    ),
                    recommendations = persistentListOf(
                        "Renew expired technician licenses within 30 days.",
                        "Clean and apply anti-corrosion coating to the busbar in Panel C during the next scheduled maintenance."
                    )
                )
            )
        )
    }

    fun getDummyLightningUiState(): LightningProtectionUiState {
        return LightningProtectionUiState(
            isLoading = false,
            inspectionReport = LightningProtectionInspectionReport(
                serviceProviderData = LightningProtectionServiceProviderData(
                    companyName = "PT. Proteksi Petir Handal",
                    companyAddress = "Jl. Gatot Subroto No. 12, Jakarta Selatan",
                    companyPermitNo = "SKP/PJK3/01/2023",
                    expertPermitNo = "SKE/AHLI-K3/05/2022",
                    expertName = "Ahmad Zaelani",
                    testEquipmentUsed = "Earth Tester, Digital Multimeter"
                ),
                clientData = LightningProtectionClientData(
                    companyName = "Gedung Perkantoran ABC",
                    companyLocation = "Jl. Thamrin No. 5, Jakarta Pusat",
                    usageLocation = "Rooftop & Area Sekitar",
                    objectType = "Instalasi Penyalur Petir",
                    inspectionType = "Pemeriksaan dan Pengujian Rutin",
                    certificateNo = "CERT-LPS-2022-007",
                    inspectionDate = "2024-09-10"
                ),
                technicalData = LightningProtectionTechnicalData(
                    conductorType = "Konvensional (Franklin)",
                    buildingHeight = "75 meter",
                    buildingArea = "1500 m²",
                    receiverHeight = "3 meter",
                    receiverCount = "4",
                    testJointCount = "4",
                    conductorDescription = "Kabel BC 50mm²",
                    conductorTypeAndSize = "BC 50mm²",
                    groundingResistance = "0.8 Ohm",
                    installationYear = "2018",
                    installer = "PT. Instalasi Aman"
                ),
                physicalInspection = LightningProtectionPhysicalInspection(
                    installationSystem = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Sesuai standar"
                    ),
                    receiverHead = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Tidak ada kerusakan"
                    ),
                    receiverPole = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Kokoh dan tidak berkarat"
                    ),
                    poleReinforcementSystem = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Kuat dan terpasang baik"
                    ),
                    downConductor = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Terpasang rapi di dinding"
                    ),
                    conductorClamps = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Jarak antar klem sesuai"
                    ),
                    jointConnections = LightningProtectionConditionResult(
                        fair = true,
                        remarks = "Ada sedikit kelonggaran di satu titik"
                    ),
                    downConductorBoxAndGroundingTerminal = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Bersih dan mudah diakses"
                    ),
                    controlBox = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Tertutup rapat"
                    ),
                    groundingSystem = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Terkubur dengan baik"
                    ),
                    downConductorDirectConnection = LightningProtectionConditionResult(
                        good = true,
                        remarks = "Koneksi langsung dan kencang"
                    )
                ),
                groundingSystemVisualInspection = LightningProtectionGroundingSystemVisualInspection(
                    airTerminal = "Terpasang dengan baik, tidak ada korosi.",
                    downConductor = "Kabel terpasang rapi, isolasi utuh.",
                    groundingAndTestJoint = "Kotak sambungan bersih, terminal tidak ada oksidasi."
                ),
                otherStandardsInspection = LightningProtectionOtherStandardsInspection(
                    installationPlacementAsPerDrawing = LightningProtectionCheckResult(
                        checked = true,
                        remarks = "Sesuai gambar"
                    ),
                    airTerminalConnectionToDownConductor = LightningProtectionCheckResult(
                        checked = true,
                        remarks = "Koneksi kuat"
                    ),
                    downConductorJointsOnStructure = LightningProtectionCheckResult(
                        checked = true,
                        remarks = "Sambungan mekanis dan dilas"
                    ),
                    groundingElectrodeUsesTestJointBox = LightningProtectionCheckResult(
                        checked = true,
                        remarks = "Menggunakan bak kontrol"
                    ),
                    downConductorMaterialStandard = LightningProtectionCheckResult(
                        checked = true,
                        remarks = "Material sesuai SNI"
                    ),
                    lightningCounterInstalled = LightningProtectionCheckResult(
                        checked = true,
                        remarks = "Terpasang dan berfungsi"
                    ),
                    airTerminalIsRadioactive = LightningProtectionCheckResult(
                        checked = false,
                        remarks = "Bukan jenis radioaktif"
                    ),
                    groundingElectrodeMaterialQuality = LightningProtectionCheckResult(
                        checked = true,
                        remarks = "Tembaga murni"
                    ),
                    overvoltageProtectionInstalled = LightningProtectionCheckResult(
                        checked = true,
                        remarks = "Arrester terpasang di panel utama"
                    )
                ),
                testingResults = LightningProtectionTestingResults(
                    groundingResistanceMeasurement = persistentListOf(
                        LightningProtectionGroundingMeasurementItem("5m", "10m", "0.85", "Titik 1"),
                        LightningProtectionGroundingMeasurementItem("5m", "10m", "0.82", "Titik 2"),
                        LightningProtectionGroundingMeasurementItem("5m", "10m", "0.79", "Titik 3")
                    ),
                    groundingResistanceTest = persistentListOf(
                        LightningProtectionGroundingTestItem(
                            "Tahanan Pentanahan",
                            true,
                            "< 5 Ohm",
                            "Memenuhi syarat"
                        )
                    )
                ),
                conclusion = LightningProtectionConclusion(
                    summary = "Secara umum, instalasi penyalur petir dalam kondisi baik dan berfungsi sesuai standar yang berlaku. Ditemukan satu sambungan konduktor yang sedikit longgar.",
                    recommendations = persistentListOf(
                        "Kencangkan kembali sambungan konduktor yang longgar pada tiang penerima di sisi utara gedung.",
                        "Lakukan pemeriksaan visual rutin setiap 6 bulan sekali."
                    )
                )
            )
        )
    }

    fun getDummyFireProtectionUiState(): FireProtectionUiState {
        return FireProtectionUiState(
            isLoading = false,
            inspectionReport = FireProtectionInspectionReport(
                documentChecklist = FireProtectionDocumentChecklist(
                    technicalDrawing = FireProtectionChecklistResult(
                        true,
                        "Available and up-to-date"
                    ),
                    previousTestDocumentation = FireProtectionChecklistResult(
                        true,
                        "Complete records from last inspection"
                    ),
                    requestLetter = FireProtectionChecklistResult(
                        true,
                        "Official request letter on file"
                    ),
                    specificationDocument = FireProtectionChecklistResult(
                        true,
                        "Comprehensive specification document"
                    )
                ),
                companyData = FireProtectionCompanyData(
                    examinationType = "Routine Inspection",
                    companyName = "PT. Proteksi Kebakaran Abadi",
                    companyLocation = "Jl. Api Unggun No. 10, Jakarta Timur",
                    usageLocation = "Pabrik Tekstil Jaya",
                    certificateNumber = "FP-CERT-2024-001",
                    objectType = "Sistem Proteksi Kebakaran",
                    inspectionDate = "2024-10-01"
                ),
                buildingData = FireProtectionBuildingData(
                    landAreaSqm = "5000",
                    buildingAreaSqm = "3000",
                    buildingHeightM = "20",
                    floorCount = "4",
                    yearBuilt = "2010",
                    construction = FireProtectionConstruction(
                        mainStructure = "Reinforced Concrete",
                        floorStructure = "Concrete Slab",
                        exteriorWalls = "Brick with Fire-rated Coating",
                        interiorWalls = "Gypsum Board with Fire-rated Core",
                        ceilingFrame = "Metal Studs",
                        ceilingCover = "Acoustic Tiles",
                        roofFrame = "Steel Truss",
                        roofCover = "Metal Sheeting"
                    ),
                    fireProtectionEquipment = FireProtectionEquipment(
                        portableExtinguishers = true,
                        indoorHydrantBox = true,
                        pillarAndOutdoorHydrant = true,
                        siameseConnection = true,
                        sprinklerSystem = true,
                        heatAndSmokeDetectors = true,
                        exitSigns = true,
                        emergencyStairs = true,
                        assemblyPoint = true
                    )
                ),
                automaticFireAlarmSpecifications = FireProtectionAutomaticFireAlarmSpecifications(
                    mcfa = FireProtectionMcfa(
                        brandOrType = "Notifier",
                        ledAnnunciator = "Yes",
                        type = "Conventional",
                        serialNumber = "NTFR-MCFA-123",
                        result = "Operational",
                        remarks = "Maintained regularly"
                    ),
                    heatDetector = FireProtectionDetector(
                        brandOrType = "Kidde",
                        pointCount = "50",
                        spacingM = "7.5",
                        operatingTemperatureC = "57",
                        result = "Functional",
                        remarks = "All detectors respond correctly"
                    ),
                    smokeDetector = FireProtectionDetector(
                        brandOrType = "Siemens",
                        pointCount = "100",
                        spacingM = "9",
                        operatingTemperatureC = "N/A",
                        result = "Functional",
                        remarks = "All detectors respond correctly"
                    ),
                    apar = FireProtectionApar(
                        brandOrType = "Servvo",
                        count = "25",
                        spacingM = "20",
                        placement = "Strategically located near exits",
                        result = "Compliant",
                        remarks = "All APARs are fully charged and inspected"
                    )
                ),
                alarmInstallationTesting = FireProtectionAlarmInstallationTesting(
                    panelFunction = "All functions operational",
                    alarmTest = "Passed, audible and visual alarms activated",
                    faultTest = "Passed, system correctly identified simulated faults",
                    interconnectionTest = "Passed, all interconnected systems responded",
                    notes = "System is fully compliant and operational."
                ),
                hydrantSystemInstallation = FireProtectionHydrantSystemInstallation(
                    waterSource = FireProtectionSystemComponent(
                        "City Water Supply",
                        "Adequate",
                        "Sufficient pressure"
                    ),
                    groundReservoir = FireProtectionGroundReservoir(
                        "500 m³ calculation",
                        "500",
                        "Full",
                        "Reservoir is clean and well-maintained"
                    ),
                    gravitationTank = FireProtectionSystemComponent(
                        "Roof-mounted Tank",
                        "Operational",
                        "Provides backup pressure"
                    ),
                    siameseConnection = FireProtectionSystemComponent(
                        "2 x 4 inch inlets",
                        "Good",
                        "Accessible and clearly marked"
                    ),
                    jockeyPump = FireProtectionJockeyPump(
                        model = "Grundfos CR 10-2",
                        serialNumber = "GRD-JCK-456",
                        driver = "Electric",
                        powerRpm = "5 HP / 2900 RPM",
                        placement = "Pump Room",
                        quantityM3H = "3",
                        headM = "60",
                        autoStartKgCm2 = "7.0",
                        autoStopKgCm2 = "8.0"
                    ),
                    electricPump = FireProtectionElectricPump(
                        model = "Patterson 1000",
                        serialNumber = "PAT-ELEC-789",
                        driver = "Electric",
                        powerRpm = "100 HP / 1450 RPM",
                        placement = "Pump Room",
                        quantityGpm = "750",
                        headM = "100",
                        autoStartKgCm2 = "6.0",
                        stop = "Manual Stop"
                    ),
                    dieselPump = FireProtectionDieselPump(
                        model = "Clarke JU4H-UF50",
                        serialNumber = "CLR-DSL-012",
                        driver = "Diesel Engine",
                        quantityUsGpm = "1000",
                        headM = "100",
                        autoStartKgCm2 = "5.5",
                        stop = "Manual Stop"
                    ),
                    buildingHydrant = FireProtectionBuildingHydrant(
                        points = "15",
                        outletDiameterInch = "2.5",
                        hoseLengthM = "30",
                        nozzleDiameterInch = "1.5",
                        placement = "Strategically placed on each floor"
                    ),
                    landingValve = FireProtectionLandingValve(
                        points = "15",
                        outletDiameterInch = "2.5",
                        couplingType = "Instantaneous",
                        placement = "Next to building hydrants"
                    ),
                    yardHydrant = FireProtectionYardHydrant(
                        points = "5",
                        outletDiameterInch = "4",
                        hoseLengthM = "50",
                        nozzleDiameterInch = "2.5",
                        placement = "Around the building perimeter"
                    ),
                    fireServiceConnection = FireProtectionFireServiceConnection(
                        points = "2",
                        inletDiameterInch = "4",
                        outletDiameterInch = "4",
                        couplingType = "Storz",
                        condition = "Good",
                        placement = "Front of building, accessible"
                    ),
                    pressureReliefValve = FireProtectionSystemComponent(
                        "Zurn",
                        "Functional",
                        "Maintains system pressure"
                    ),
                    testValve = FireProtectionSystemComponent(
                        "Apollo",
                        "Functional",
                        "Used for flow testing"
                    ),
                    suctionPipe = FireProtectionSystemComponent(
                        "Steel, 8 inch",
                        "Good",
                        "No leaks detected"
                    ),
                    mainPipe = FireProtectionSystemComponent(
                        "Ductile Iron, 6 inch",
                        "Good",
                        "No leaks detected"
                    ),
                    standPipe = FireProtectionSystemComponent(
                        "Steel, 6 inch",
                        "Good",
                        "No leaks detected"
                    ),
                    hydrantPillar = FireProtectionSystemComponent(
                        "Cast Iron",
                        "Good",
                        "Clearly visible and painted red"
                    ),
                    indoorHydrant = FireProtectionSystemComponent(
                        "Metal Cabinet",
                        "Good",
                        "Fully equipped with hose and nozzle"
                    ),
                    hoseReel = FireProtectionSystemComponent(
                        "Automatic Rewind",
                        "Functional",
                        "Hose extends and retracts smoothly"
                    )
                ),
                pumpFunctionTest = persistentListOf(
                    FireProtectionPumpFunctionTestItem("Jockey Pump", "7.0 kg/cm²", "8.0 kg/cm²"),
                    FireProtectionPumpFunctionTestItem(
                        "Electric Pump",
                        "6.0 kg/cm²",
                        "Manual Stop"
                    ),
                    FireProtectionPumpFunctionTestItem("Diesel Pump", "5.5 kg/cm²", "Manual Stop")
                ),
                hydrantOperationalTest = persistentListOf(
                    FireProtectionHydrantOperationalTestItem(
                        "Floor 1 Hydrant",
                        "5.0 kg/cm²",
                        "20m",
                        "Direct",
                        "Passed",
                        "Good pressure and stream"
                    ),
                    FireProtectionHydrantOperationalTestItem(
                        "Outdoor Hydrant 1",
                        "7.5 kg/cm²",
                        "25m",
                        "Fog",
                        "Passed",
                        "Strong flow and good coverage"
                    )
                ),
                conclusion = FireProtectionConclusion(
                    summary = "The fire protection system is generally in excellent condition, with all major components functioning correctly and meeting safety standards. Minor recommendations are provided for ongoing maintenance.",
                    recommendations = persistentListOf(
                        "Conduct quarterly visual inspections of all portable fire extinguishers.",
                        "Ensure all fire alarm pull stations are unobstructed at all times.",
                        "Review and update emergency contact information annually."
                    )
                )
            )
        )
    }

    fun getDummyForkliftUiState(): ForkliftUiState {
        return ForkliftUiState(
            forkliftInspectionReport = ForkliftInspectionReport(
                equipmentType = "Forklift",
                examinationType = "Periodic Inspection",
                generalData = ForkliftGeneralData(
                    owner = "PT. Logistik Cepat",
                    address = "Jl. Industri No. 10, Kawasan Industri",
                    user = "Gudang Utama",
                    personInCharge = "Bambang Wijaya",
                    unitLocation = "Area Gudang A",
                    operatorName = "Slamet Raharjo",
                    driveType = "Electric",
                    manufacturer = "Toyota",
                    brandType = "8FBE20",
                    yearOfManufacture = "2020",
                    serialNumber = "TLF-2020-001",
                    liftingCapacity = "2000 kg",
                    intendedUse = "Material Handling",
                    permitNumber = "FORKLIFT-IZIN-2024-001",
                    equipmentHistory = "Routine maintenance performed every 3 months."
                ),
                technicalData = ForkliftTechnicalData(
                    specifications = ForkliftSpecifications(
                        serialNumber = "TLF-2020-001",
                        capacity = "2000 kg",
                        attachment = "Standard Forks",
                        forkDimension = "1070 mm x 120 mm x 45 mm"
                    ),
                    speed = ForkliftSpeed(
                        lifting = "0.5 m/s",
                        lowering = "0.6 m/s",
                        travelling = "15 km/h"
                    ),
                    primeMover = ForkliftPrimeMover(
                        revolution = "N/A (Electric)",
                        brandType = "AC Motor",
                        serialNumber = "MOT-AC-001",
                        yearOfManufacture = "2020",
                        power = "10 kW",
                        numberOfCylinders = "N/A"
                    ),
                    dimensions = ForkliftDimensions(
                        length = "2.5 m",
                        width = "1.2 m",
                        height = "2.1 m",
                        forkLiftingHeight = "4.5 m"
                    ),
                    tirePressure = ForkliftTirePressure(
                        driveWheel = "7 bar",
                        steeringWheel = "6 bar"
                    ),
                    driveWheel = ForkliftWheel(
                        size = "23x9-10",
                        type = "Pneumatic"
                    ),
                    steeringWheel = ForkliftWheel(
                        size = "18x7-8",
                        type = "Solid"
                    ),
                    travellingBrake = ForkliftWheel(
                        size = "N/A",
                        type = "N/A"
                    ),
                    hydraulicPump = ForkliftHydraulicPump(
                        pressure = "180 bar",
                        type = "Gear Pump",
                        reliefValve = "Functional"
                    )
                ),
                visualInspection = ForkliftVisualInspection(
                    chassisReinforcementCorrosion = ForkliftInspectionResult(true, "None"),
                    chassisReinforcementCracks = ForkliftInspectionResult(true, "None"),
                    chassisReinforcementDeformation = ForkliftInspectionResult(true, "None"),
                    counterweightCorrosion = ForkliftInspectionResult(true, "Minor surface rust"),
                    counterweightCondition = ForkliftInspectionResult(true, "Secure"),
                    otherEquipmentFloorDeck = ForkliftInspectionResult(true, "Clean and intact"),
                    otherEquipmentStairs = ForkliftInspectionResult(true, "Not applicable"),
                    otherEquipmentBindingBolts = ForkliftInspectionResult(true, "Tight"),
                    otherEquipmentOperatorSeat = ForkliftInspectionResult(true, "Good condition"),
                    primeMoverCoolingSystem = ForkliftInspectionResult(true, "N/A (Electric)"),
                    primeMoverLubricantSystem = ForkliftInspectionResult(true, "N/A (Electric)"),
                    primeMoverFuelSystem = ForkliftInspectionResult(true, "N/A (Electric)"),
                    primeMoverAirIntakeSystem = ForkliftInspectionResult(true, "N/A (Electric)"),
                    primeMoverExhaustSystem = ForkliftInspectionResult(true, "N/A (Electric)"),
                    primeMoverStarterSystem = ForkliftInspectionResult(true, "N/A (Electric)"),
                    primeMoverElectricalBattery = ForkliftInspectionResult(true, "Good, charged"),
                    primeMoverElectricalStartingDynamo = ForkliftInspectionResult(
                        true,
                        "N/A (Electric)"
                    ),
                    primeMoverElectricalAlternator = ForkliftInspectionResult(
                        true,
                        "N/A (Electric)"
                    ),
                    primeMoverElectricalBatteryCable = ForkliftInspectionResult(
                        true,
                        "Secure, no corrosion"
                    ),
                    primeMoverElectricalWiring = ForkliftInspectionResult(true, "Intact"),
                    primeMoverElectricalLighting = ForkliftInspectionResult(true, "Functional"),
                    primeMoverElectricalSafetyLights = ForkliftInspectionResult(true, "Functional"),
                    primeMoverElectricalHorn = ForkliftInspectionResult(true, "Functional"),
                    primeMoverElectricalFuse = ForkliftInspectionResult(true, "Intact"),
                    dashboardTemperatureIndicator = ForkliftInspectionResult(
                        true,
                        "N/A (Electric)"
                    ),
                    dashboardEngineOilPressure = ForkliftInspectionResult(true, "N/A (Electric)"),
                    dashboardHydraulicPressure = ForkliftInspectionResult(true, "Normal"),
                    dashboardHourMeter = ForkliftInspectionResult(true, "Working"),
                    dashboardGlowPlug = ForkliftInspectionResult(true, "N/A (Electric)"),
                    dashboardFuelIndicator = ForkliftInspectionResult(true, "N/A (Electric)"),
                    dashboardLoadIndicator = ForkliftInspectionResult(true, "Functional"),
                    dashboardLoadChart = ForkliftInspectionResult(true, "Present and legible"),
                    dashboardAmpereMeter = ForkliftInspectionResult(true, "Normal"),
                    powerTrainSteeringWheel = ForkliftInspectionResult(true, "Smooth operation"),
                    powerTrainSteeringRod = ForkliftInspectionResult(true, "No play"),
                    powerTrainSteeringGearBox = ForkliftInspectionResult(true, "No leaks"),
                    powerTrainSteeringPitmanArm = ForkliftInspectionResult(true, "Secure"),
                    powerTrainSteeringDragLink = ForkliftInspectionResult(true, "Secure"),
                    powerTrainSteeringTieRod = ForkliftInspectionResult(true, "Secure"),
                    powerTrainSteeringLubrication = ForkliftInspectionResult(true, "Adequate"),
                    powerTrainWheelFront = ForkliftInspectionResult(true, "Good tread"),
                    powerTrainWheelRear = ForkliftInspectionResult(true, "Good tread"),
                    powerTrainWheelBindingBolts = ForkliftInspectionResult(true, "Tight"),
                    powerTrainWheelHub = ForkliftInspectionResult(true, "No abnormal noise"),
                    powerTrainWheelLubrication = ForkliftInspectionResult(true, "Adequate"),
                    powerTrainWheelMechanicalEquipment = ForkliftInspectionResult(
                        true,
                        "Good condition"
                    ),
                    powerTrainClutchHousing = ForkliftInspectionResult(true, "N/A (Electric)"),
                    powerTrainClutchCondition = ForkliftInspectionResult(true, "N/A (Electric)"),
                    powerTrainClutchTransmissionOil = ForkliftInspectionResult(true, "Clean"),
                    powerTrainClutchTransmissionLeakage = ForkliftInspectionResult(true, "None"),
                    powerTrainClutchConnectingShaft = ForkliftInspectionResult(
                        true,
                        "N/A (Electric)"
                    ),
                    powerTrainClutchMechanicalEquipment = ForkliftInspectionResult(
                        true,
                        "N/A (Electric)"
                    ),
                    powerTrainDifferentialHousing = ForkliftInspectionResult(true, "No leaks"),
                    powerTrainDifferentialCondition = ForkliftInspectionResult(true, "Good"),
                    powerTrainDifferentialOil = ForkliftInspectionResult(true, "Clean"),
                    powerTrainDifferentialLeakage = ForkliftInspectionResult(true, "None"),
                    powerTrainDifferentialConnectingShaft = ForkliftInspectionResult(
                        true,
                        "Secure"
                    ),
                    powerTrainBrakeMainCondition = ForkliftInspectionResult(true, "Functional"),
                    powerTrainBrakeHandbrakeCondition = ForkliftInspectionResult(
                        true,
                        "Functional"
                    ),
                    powerTrainBrakeEmergencyCondition = ForkliftInspectionResult(
                        true,
                        "Functional"
                    ),
                    powerTrainBrakeLeakage = ForkliftInspectionResult(true, "None"),
                    powerTrainBrakeMechanicalComponents = ForkliftInspectionResult(
                        true,
                        "Good condition"
                    ),
                    powerTrainTransmissionHousing = ForkliftInspectionResult(true, "No leaks"),
                    powerTrainTransmissionOil = ForkliftInspectionResult(true, "Clean"),
                    powerTrainTransmissionLeakage = ForkliftInspectionResult(true, "None"),
                    powerTrainTransmissionMechanicalEquipment = ForkliftInspectionResult(
                        true,
                        "Good condition"
                    ),
                    attachmentMastWear = ForkliftInspectionResult(true, "Minor"),
                    attachmentMastCracks = ForkliftInspectionResult(true, "None"),
                    attachmentMastDeformation = ForkliftInspectionResult(true, "None"),
                    attachmentMastLubrication = ForkliftInspectionResult(true, "Adequate"),
                    attachmentMastShaftAndBearing = ForkliftInspectionResult(
                        true,
                        "Smooth operation"
                    ),
                    attachmentLiftChainCondition = ForkliftInspectionResult(
                        true,
                        "Good, well-lubricated"
                    ),
                    attachmentLiftChainDeformation = ForkliftInspectionResult(true, "None"),
                    attachmentLiftChainLubrication = ForkliftInspectionResult(true, "Adequate"),
                    personalBasketWorkFloorCorrosion = ForkliftInspectionResult(true, "N/A"),
                    personalBasketWorkFloorCracks = ForkliftInspectionResult(true, "N/A"),
                    personalBasketWorkFloorDeformation = ForkliftInspectionResult(true, "N/A"),
                    personalBasketWorkFloorBinding = ForkliftInspectionResult(true, "N/A"),
                    personalBasketFrameCorrosion = ForkliftInspectionResult(true, "N/A"),
                    personalBasketFrameCracks = ForkliftInspectionResult(true, "N/A"),
                    personalBasketFrameDeformation = ForkliftInspectionResult(true, "N/A"),
                    personalBasketFrameCrossBracing = ForkliftInspectionResult(true, "N/A"),
                    personalBasketFrameDiagonalBracing = ForkliftInspectionResult(true, "N/A"),
                    personalBasketBindingBoltCorrosion = ForkliftInspectionResult(true, "N/A"),
                    personalBasketBindingBoltCracks = ForkliftInspectionResult(true, "N/A"),
                    personalBasketBindingBoltDeformation = ForkliftInspectionResult(true, "N/A"),
                    personalBasketBindingBoltBinding = ForkliftInspectionResult(true, "N/A"),
                    personalBasketDoorCorrosion = ForkliftInspectionResult(true, "N/A"),
                    personalBasketDoorCracks = ForkliftInspectionResult(true, "N/A"),
                    personalBasketDoorDeformation = ForkliftInspectionResult(true, "N/A"),
                    personalBasketDoorBinding = ForkliftInspectionResult(true, "N/A"),
                    personalBasketHandrailCracks = ForkliftInspectionResult(true, "N/A"),
                    personalBasketHandrailWear = ForkliftInspectionResult(true, "N/A"),
                    personalBasketHandrailRailStraightness = ForkliftInspectionResult(true, "N/A"),
                    personalBasketHandrailRailJoint = ForkliftInspectionResult(true, "N/A"),
                    personalBasketHandrailAlignmentBetweenRails = ForkliftInspectionResult(
                        true,
                        "N/A"
                    ),
                    personalBasketHandrailGapBetweenRailJoints = ForkliftInspectionResult(
                        true,
                        "N/A"
                    ),
                    personalBasketHandrailRailFastener = ForkliftInspectionResult(true, "N/A"),
                    personalBasketHandrailRailStopper = ForkliftInspectionResult(true, "N/A"),
                    hydraulicTankLeakage = ForkliftInspectionResult(true, "None"),
                    hydraulicTankOilLevel = ForkliftInspectionResult(true, "Normal"),
                    hydraulicTankOilCondition = ForkliftInspectionResult(true, "Clean"),
                    hydraulicTankSuctionLineCondition = ForkliftInspectionResult(true, "Good"),
                    hydraulicTankReturnLineCondition = ForkliftInspectionResult(true, "Good"),
                    hydraulicPumpLeakage = ForkliftInspectionResult(true, "None"),
                    hydraulicPumpSuctionLineCondition = ForkliftInspectionResult(true, "Good"),
                    hydraulicPumpPressureLineCondition = ForkliftInspectionResult(true, "Good"),
                    hydraulicPumpFunction = ForkliftInspectionResult(true, "Smooth"),
                    hydraulicPumpAbnormalNoise = ForkliftInspectionResult(true, "None"),
                    controlValveLeakage = ForkliftInspectionResult(true, "None"),
                    controlValveLineCondition = ForkliftInspectionResult(true, "Good"),
                    controlValveReliefValveFunction = ForkliftInspectionResult(true, "Functional"),
                    controlValveAbnormalNoise = ForkliftInspectionResult(true, "None"),
                    controlValveLiftCylinderFunction = ForkliftInspectionResult(true, "Smooth"),
                    controlValveTiltCylinderFunction = ForkliftInspectionResult(true, "Smooth"),
                    controlValveSteeringCylinderFunction = ForkliftInspectionResult(true, "Smooth"),
                    actuatorLeakage = ForkliftInspectionResult(true, "None"),
                    actuatorLineCondition = ForkliftInspectionResult(true, "Good"),
                    actuatorAbnormalNoise = ForkliftInspectionResult(true, "None")
                ),
                nonDestructiveExamination = ForkliftNde(
                    liftingChainInspection = ForkliftNdeChainInspection(
                        items = persistentListOf(
                            ForkliftNdeChainItem(
                                chain = "Lift Chain 1",
                                typeAndConstruction = "Leaf Chain, BL6",
                                standardPitchMm = "15.875",
                                measuredPitchMm = "15.88",
                                standardPinMm = "5.08",
                                measuredPinMm = "5.07",
                                remarks = "Minor wear, within tolerance"
                            ),
                            ForkliftNdeChainItem(
                                chain = "Lift Chain 2",
                                typeAndConstruction = "Leaf Chain, BL6",
                                standardPitchMm = "15.875",
                                measuredPitchMm = "15.89",
                                standardPinMm = "5.08",
                                measuredPinMm = "5.09",
                                remarks = "Minor wear, within tolerance"
                            )
                        )
                    ),
                    forkNDT = ForkliftNdeFork(
                        ndtType = "Magnetic Particle Testing",
                        items = persistentListOf(
                            ForkliftNdeForkItem(
                                partInspected = "Left Fork Arm",
                                location = "Heel area",
                                finding = ForkliftInspectionResult(true, "No cracks found")
                            ),
                            ForkliftNdeForkItem(
                                partInspected = "Right Fork Arm",
                                location = "Heel area",
                                finding = ForkliftInspectionResult(true, "No cracks found")
                            )
                        )
                    )
                ),
                testing = ForkliftTesting(
                    engineOnInspection = ForkliftEngineOnInspection(
                        dynamoStarter = ForkliftInspectionResult(true, "N/A (Electric)"),
                        instrumentIndicatorFunction = ForkliftInspectionResult(true, "Functional"),
                        electricalEquipmentFunction = ForkliftInspectionResult(true, "Functional"),
                        engineOilLeakage = ForkliftInspectionResult(true, "N/A (Electric)"),
                        fuelLeakage = ForkliftInspectionResult(true, "N/A (Electric)"),
                        coolantLeakage = ForkliftInspectionResult(true, "N/A (Electric)"),
                        hydraulicOilLeakage = ForkliftInspectionResult(true, "None"),
                        transmissionOilLeakage = ForkliftInspectionResult(true, "None"),
                        finalDriveOilLeakage = ForkliftInspectionResult(true, "None"),
                        brakeFluidLeakage = ForkliftInspectionResult(true, "None"),
                        clutchFunction = ForkliftInspectionResult(true, "N/A (Electric)"),
                        transmissionFunction = ForkliftInspectionResult(true, "Smooth"),
                        brakeFunction = ForkliftInspectionResult(true, "Effective"),
                        hornAlarmFunction = ForkliftInspectionResult(true, "Functional"),
                        lightsFunction = ForkliftInspectionResult(true, "Functional"),
                        hydraulicSystemFunction = ForkliftInspectionResult(true, "Functional"),
                        powerSteeringFunction = ForkliftInspectionResult(true, "Smooth"),
                        liftCylinderFunction = ForkliftInspectionResult(true, "Smooth"),
                        tiltCylinderFunction = ForkliftInspectionResult(true, "Smooth"),
                        exhaustGasCondition = ForkliftInspectionResult(true, "N/A (Electric)"),
                        controlLeversFunction = ForkliftInspectionResult(true, "Responsive"),
                        engineNoise = ForkliftInspectionResult(true, "N/A (Electric)"),
                        turbochargerNoise = ForkliftInspectionResult(true, "N/A (Electric)"),
                        transmissionNoise = ForkliftInspectionResult(true, "Normal"),
                        hydraulicPumpNoise = ForkliftInspectionResult(true, "Normal"),
                        guardNoise = ForkliftInspectionResult(true, "None")
                    ),
                    loadTest = ForkliftLoadTest(
                        items = persistentListOf(
                            ForkliftLoadTestItem(
                                forkLiftingHeight = "4.0 m",
                                testLoad = "1800 kg",
                                travelingSpeed = "10 km/h",
                                movement = "Lift, Lower, Tilt, Travel",
                                result = "Passed",
                                remarks = "Stable, no issues"
                            ),
                            ForkliftLoadTestItem(
                                forkLiftingHeight = "4.5 m",
                                testLoad = "1500 kg",
                                travelingSpeed = "8 km/h",
                                movement = "Lift, Lower, Tilt",
                                result = "Passed",
                                remarks = "Stable, no issues"
                            )
                        )
                    )
                ),
                conclusion = ForkliftConclusion(
                    summary = persistentListOf(
                        "The forklift is in good operating condition and meets safety requirements.",
                        "Routine maintenance is up-to-date."
                    ),
                    recommendations = persistentListOf(
                        "Monitor minor surface rust on counterweight, apply anti-corrosion paint if worsening.",
                        "Schedule annual comprehensive inspection for all hydraulic components."
                    )
                )
            )
        )
    }

    fun getDummyGantryCraneUiState(): GantryCraneUiState {
        return GantryCraneUiState(
            gantryCraneInspectionReport = GantryCraneInspectionReport(
                equipmentType = "Gantry Crane",
                examinationType = "Periodic Inspection",
                generalData = GantryCraneGeneralData(
                    owner = "PT. Baja Perkasa",
                    address = "Jl. Industri Berat No. 5, Kawasan Industri",
                    user = "Produksi Baja",
                    personInCharge = "Dian Permata",
                    unitLocation = "Area Produksi 1",
                    driveType = "Electric",
                    manufacturer = "Konecranes",
                    brandType = "Goliath G-100",
                    yearOfManufacture = "2019",
                    serialNumber = "KCR-G100-2019-001",
                    liftingCapacity = "10000 kg",
                    intendedUse = "Heavy Material Lifting",
                    permitNumber = "CRANE-IZIN-2024-001",
                    operatorCertificate = "OPR-GC-2024-005",
                    technicalDataManual = "Available and up-to-date"
                ),
                technicalData = GantryCraneTechnicalData(
                    specifications = GantryCraneTechSpecs(
                        liftingHeight = GantryCraneMovementData("15 m", "", ""),
                        girderLength = GantryCraneMovementData("25 m", "", ""),
                        speed = GantryCraneMovementData(
                            "Hoist: 0.1 m/s",
                            "Travel: 0.5 m/s",
                            "Traverse: 0.3 m/s"
                        )
                    ),
                    driveMotor = GantryCraneDriveMotor(
                        capacity = GantryCraneMovementData(
                            "Hoist: 15 kW",
                            "Travel: 10 kW",
                            "Traverse: 7.5 kW"
                        ),
                        powerKw = GantryCraneMovementData(
                            "Hoist: 15",
                            "Travel: 10",
                            "Traverse: 7.5"
                        ),
                        type = GantryCraneMovementData("Hoist: AC", "Travel: AC", "Traverse: AC"),
                        rpm = GantryCraneMovementData(
                            "Hoist: 1450",
                            "Travel: 960",
                            "Traverse: 960"
                        ),
                        voltageV = GantryCraneMovementData(
                            "Hoist: 380",
                            "Travel: 380",
                            "Traverse: 380"
                        ),
                        currentA = GantryCraneMovementData(
                            "Hoist: 30",
                            "Travel: 20",
                            "Traverse: 15"
                        ),
                        frequencyHz = GantryCraneMovementData(
                            "Hoist: 50",
                            "Travel: 50",
                            "Traverse: 50"
                        ),
                        phase = GantryCraneMovementData("Hoist: 3", "Travel: 3", "Traverse: 3"),
                        powerSupply = GantryCraneMovementData(
                            "Hoist: PLN",
                            "Travel: PLN",
                            "Traverse: PLN"
                        )
                    ),
                    startingResistor = GantryCraneStartingResistor(
                        type = GantryCraneMovementData("Wire Wound", "", ""),
                        voltageV = GantryCraneMovementData("380", "", ""),
                        currentA = GantryCraneMovementData("50", "", "")
                    ),
                    brake = GantryCraneBrake(
                        type = GantryCraneMovementData(
                            "Electromagnetic",
                            "Electromagnetic",
                            "Electromagnetic"
                        ),
                        model = GantryCraneMovementData(
                            "Hoist: BM-500",
                            "Travel: BM-300",
                            "Traverse: BM-200"
                        )
                    ),
                    controllerBrake = GantryCraneControllerBrake(
                        type = GantryCraneMovementData("Joystick", "Joystick", "Joystick"),
                        model = GantryCraneMovementData(
                            "Hoist: JC-100",
                            "Travel: JC-50",
                            "Traverse: JC-50"
                        )
                    ),
                    hook = GantryCraneHook(
                        type = GantryCraneMovementData("Single Hook", "", ""),
                        capacity = GantryCraneMovementData("10000 kg", "", ""),
                        material = GantryCraneMovementData("Forged Steel", "", "")
                    ),
                    wireRope = GantryCraneWireRope(
                        type = GantryCraneMovementData("6x36 WS", "", ""),
                        construction = GantryCraneMovementData("IWRC", "", ""),
                        diameter = GantryCraneMovementData("20 mm", "", ""),
                        length = GantryCraneMovementData("150 m", "", "")
                    ),
                    chain = GantryCraneChain(
                        type = GantryCraneMovementData("N/A", "", ""),
                        construction = GantryCraneMovementData("N/A", "", ""),
                        diameter = GantryCraneMovementData("N/A", "", ""),
                        length = GantryCraneMovementData("N/A", "", "")
                    )
                ),
                visualInspection = GantryCraneVisualInspection(
                    foundationAnchorBoltCorrosion = GantryCraneInspectionResult(true, "None"),
                    foundationAnchorBoltCracks = GantryCraneInspectionResult(true, "None"),
                    foundationAnchorBoltDeformation = GantryCraneInspectionResult(true, "None"),
                    foundationAnchorBoltTightness = GantryCraneInspectionResult(true, "Tight"),
                    columnFrameCorrosion = GantryCraneInspectionResult(
                        true,
                        "Minor surface rust on some areas"
                    ),
                    columnFrameCracks = GantryCraneInspectionResult(true, "None"),
                    columnFrameDeformation = GantryCraneInspectionResult(true, "None"),
                    columnFrameFastening = GantryCraneInspectionResult(true, "Secure"),
                    columnFrameCrossBracing = GantryCraneInspectionResult(true, "Intact"),
                    columnFrameDiagonalBracing = GantryCraneInspectionResult(true, "Intact"),
                    ladderCorrosion = GantryCraneInspectionResult(true, "None"),
                    ladderCracks = GantryCraneInspectionResult(true, "None"),
                    ladderDeformation = GantryCraneInspectionResult(true, "None"),
                    ladderFastening = GantryCraneInspectionResult(true, "Secure"),
                    workPlatformCorrosion = GantryCraneInspectionResult(true, "None"),
                    workPlatformCracks = GantryCraneInspectionResult(true, "None"),
                    workPlatformDeformation = GantryCraneInspectionResult(true, "None"),
                    workPlatformFastening = GantryCraneInspectionResult(true, "Secure"),
                    railMountingBeamCorrosion = GantryCraneInspectionResult(true, "None"),
                    railMountingBeamCracks = GantryCraneInspectionResult(true, "None"),
                    railMountingBeamDeformation = GantryCraneInspectionResult(true, "None"),
                    railMountingBeamFastening = GantryCraneInspectionResult(true, "Secure"),
                    travelingRailCorrosion = GantryCraneInspectionResult(
                        true,
                        "Minor surface rust"
                    ),
                    travelingRailCracks = GantryCraneInspectionResult(true, "None"),
                    travelingRailJoint = GantryCraneInspectionResult(true, "Good"),
                    travelingRailStraightness = GantryCraneInspectionResult(true, "Straight"),
                    travelingRailAlignmentBetweenRails = GantryCraneInspectionResult(
                        true,
                        "Aligned"
                    ),
                    travelingRailEvennessBetweenRails = GantryCraneInspectionResult(true, "Even"),
                    travelingRailGapBetweenRailJoints = GantryCraneInspectionResult(
                        true,
                        "Consistent"
                    ),
                    travelingRailFastener = GantryCraneInspectionResult(true, "Tight"),
                    travelingRailStopper = GantryCraneInspectionResult(true, "Present and secure"),
                    traversingRailCorrosion = GantryCraneInspectionResult(true, "None"),
                    traversingRailCracks = GantryCraneInspectionResult(true, "None"),
                    traversingRailJoint = GantryCraneInspectionResult(true, "Good"),
                    traversingRailStraightness = GantryCraneInspectionResult(true, "Straight"),
                    traversingRailAlignmentBetweenRails = GantryCraneInspectionResult(
                        true,
                        "Aligned"
                    ),
                    traversingRailEvennessBetweenRails = GantryCraneInspectionResult(true, "Even"),
                    traversingRailGapBetweenRailJoints = GantryCraneInspectionResult(
                        true,
                        "Consistent"
                    ),
                    traversingRailFastener = GantryCraneInspectionResult(true, "Tight"),
                    traversingRailStopper = GantryCraneInspectionResult(true, "Present and secure"),
                    girderCorrosion = GantryCraneInspectionResult(true, "Minor"),
                    girderCracks = GantryCraneInspectionResult(true, "None"),
                    girderCamber = GantryCraneInspectionResult(true, "Within tolerance"),
                    girderJoint = GantryCraneInspectionResult(true, "Secure"),
                    girderEndJoint = GantryCraneInspectionResult(true, "Secure"),
                    girderTruckMountOnGirder = GantryCraneInspectionResult(true, "Secure"),
                    travelingGearboxGirderCorrosion = GantryCraneInspectionResult(true, "None"),
                    travelingGearboxGirderCracks = GantryCraneInspectionResult(true, "None"),
                    travelingGearboxGirderLubricatingOil = GantryCraneInspectionResult(
                        true,
                        "Adequate"
                    ),
                    travelingGearboxGirderOilSeal = GantryCraneInspectionResult(true, "No leaks"),
                    driveWheelWear = GantryCraneInspectionResult(true, "Minor"),
                    driveWheelCracks = GantryCraneInspectionResult(true, "None"),
                    driveWheelDeformation = GantryCraneInspectionResult(true, "None"),
                    driveWheelFlangeCondition = GantryCraneInspectionResult(true, "Good"),
                    driveWheelChainCondition = GantryCraneInspectionResult(true, "Good"),
                    idleWheelSecurity = GantryCraneInspectionResult(true, "Secure"),
                    idleWheelCracks = GantryCraneInspectionResult(true, "None"),
                    idleWheelDeformation = GantryCraneInspectionResult(true, "None"),
                    idleWheelFlangeCondition = GantryCraneInspectionResult(true, "Good"),
                    wheelConnectorBogieAxleStraightness = GantryCraneInspectionResult(
                        true,
                        "Straight"
                    ),
                    wheelConnectorBogieAxleCrossJoint = GantryCraneInspectionResult(true, "Secure"),
                    wheelConnectorBogieAxleLubrication = GantryCraneInspectionResult(
                        true,
                        "Adequate"
                    ),
                    stopperBumperOnGirderCondition = GantryCraneInspectionResult(true, "Good"),
                    stopperBumperOnGirderReinforcement = GantryCraneInspectionResult(
                        true,
                        "Strong"
                    ),
                    traversingGearboxTrolleyCarrierFastening = GantryCraneInspectionResult(
                        true,
                        "Secure"
                    ),
                    traversingGearboxTrolleyCarrierCorrosion = GantryCraneInspectionResult(
                        true,
                        "None"
                    ),
                    traversingGearboxTrolleyCarrierCracks = GantryCraneInspectionResult(
                        true,
                        "None"
                    ),
                    traversingGearboxTrolleyCarrierLubricatingOil = GantryCraneInspectionResult(
                        true,
                        "Adequate"
                    ),
                    traversingGearboxTrolleyCarrierOilSeal = GantryCraneInspectionResult(
                        true,
                        "No leaks"
                    ),
                    driveWheelOnTrolleyWear = GantryCraneInspectionResult(true, "Minor"),
                    driveWheelOnTrolleyCracks = GantryCraneInspectionResult(true, "None"),
                    driveWheelOnTrolleyDeformation = GantryCraneInspectionResult(true, "None"),
                    driveWheelOnTrolleyFlangeCondition = GantryCraneInspectionResult(true, "Good"),
                    driveWheelOnTrolleyChainCondition = GantryCraneInspectionResult(true, "Good"),
                    idleWheelOnTrolleyWear = GantryCraneInspectionResult(true, "Minor"),
                    idleWheelOnTrolleyCracks = GantryCraneInspectionResult(true, "None"),
                    idleWheelOnTrolleyDeformation = GantryCraneInspectionResult(true, "None"),
                    idleWheelOnTrolleyFlangeCondition = GantryCraneInspectionResult(true, "Good"),
                    windingDrumGroove = GantryCraneInspectionResult(true, "Good"),
                    windingDrumGrooveFlange = GantryCraneInspectionResult(true, "Good"),
                    windingDrumFlanges = GantryCraneInspectionResult(true, "Good"),
                    brakeWear = GantryCraneInspectionResult(true, "Within limit"),
                    brakeAdjustment = GantryCraneInspectionResult(true, "Properly adjusted"),
                    hoistGearboxLubrication = GantryCraneInspectionResult(true, "Adequate"),
                    hoistGearboxOilSeal = GantryCraneInspectionResult(true, "No leaks"),
                    pulleyDiscChainSprocketPulleyGroove = GantryCraneInspectionResult(true, "Good"),
                    pulleyDiscChainSprocketPulleyFlange = GantryCraneInspectionResult(true, "Good"),
                    pulleyDiscChainSprocketPulleyPin = GantryCraneInspectionResult(true, "Secure"),
                    pulleyDiscChainSprocketBearing = GantryCraneInspectionResult(true, "Smooth"),
                    pulleyDiscChainSprocketPulleyGuard = GantryCraneInspectionResult(
                        true,
                        "Present and secure"
                    ),
                    pulleyDiscChainSprocketWireRopeChainGuard = GantryCraneInspectionResult(
                        true,
                        "Present and secure"
                    ),
                    mainHookWear = GantryCraneInspectionResult(true, "Minor"),
                    mainHookThroatOpening = GantryCraneInspectionResult(true, "Within limit"),
                    mainHookSwivelNutAndBearing = GantryCraneInspectionResult(true, "Smooth"),
                    mainHookTrunnion = GantryCraneInspectionResult(true, "Good"),
                    auxiliaryHookWear = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryHookThroatOpening = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryHookSwivelNutAndBearing = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryHookTrunnion = GantryCraneInspectionResult(true, "N/A"),
                    mainWireRopeCorrosion = GantryCraneInspectionResult(true, "None"),
                    mainWireRopeWear = GantryCraneInspectionResult(true, "Minor"),
                    mainWireRopeBroken = GantryCraneInspectionResult(true, "None"),
                    mainWireRopeDeformation = GantryCraneInspectionResult(true, "None"),
                    auxiliaryWireRopeCorrosion = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryWireRopeWear = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryWireRopeBroken = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryWireRopeDeformation = GantryCraneInspectionResult(true, "N/A"),
                    mainChainCorrosion = GantryCraneInspectionResult(true, "N/A"),
                    mainChainWear = GantryCraneInspectionResult(true, "N/A"),
                    mainChainCrackedBroken = GantryCraneInspectionResult(true, "N/A"),
                    mainChainDeformation = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryChainCorrosion = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryChainWear = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryChainCrackedBroken = GantryCraneInspectionResult(true, "N/A"),
                    auxiliaryChainDeformation = GantryCraneInspectionResult(true, "N/A"),
                    limitSwitchLsLongTraveling = GantryCraneInspectionResult(true, "Functional"),
                    limitSwitchLsCrossTraveling = GantryCraneInspectionResult(true, "Functional"),
                    limitSwitchLsHoisting = GantryCraneInspectionResult(true, "Functional"),
                    operatorCabinSafetyLadder = GantryCraneInspectionResult(true, "Secure"),
                    operatorCabinDoor = GantryCraneInspectionResult(true, "Functional"),
                    operatorCabinWindow = GantryCraneInspectionResult(true, "Clean and clear"),
                    operatorCabinFanAc = GantryCraneInspectionResult(true, "Functional"),
                    operatorCabinControlLeversButtons = GantryCraneInspectionResult(
                        true,
                        "Responsive"
                    ),
                    operatorCabinPendantControl = GantryCraneInspectionResult(true, "Functional"),
                    operatorCabinLighting = GantryCraneInspectionResult(true, "Adequate"),
                    operatorCabinHorn = GantryCraneInspectionResult(true, "Functional"),
                    operatorCabinFuse = GantryCraneInspectionResult(true, "Intact"),
                    operatorCabinCommunicationDevice = GantryCraneInspectionResult(
                        true,
                        "Functional"
                    ),
                    operatorCabinFireExtinguisher = GantryCraneInspectionResult(
                        true,
                        "Present and charged"
                    ),
                    operatorCabinOperatingSigns = GantryCraneInspectionResult(
                        true,
                        "Clear and visible"
                    ),
                    operatorCabinIgnitionKeyMasterSwitch = GantryCraneInspectionResult(
                        true,
                        "Functional"
                    ),
                    electricalComponentsPanelConductorConnector = GantryCraneInspectionResult(
                        true,
                        "Secure"
                    ),
                    electricalComponentsConductorProtection = GantryCraneInspectionResult(
                        true,
                        "Intact"
                    ),
                    electricalComponentsMotorInstallationSafetySystem = GantryCraneInspectionResult(
                        true,
                        "Functional"
                    ),
                    electricalComponentsGroundingSystem = GantryCraneInspectionResult(true, "Good"),
                    electricalComponentsInstallation = GantryCraneInspectionResult(
                        true,
                        "Neat and compliant"
                    )
                ),
                nonDestructiveExamination = GantryCraneNde(
                    wireRope = GantryCraneNdeWireRope(
                        method = "Visual Inspection and Magnetic Rope Testing",
                        items = persistentListOf(
                            GantryCraneNdeWireRopeItem(
                                usage = "Main Hoist",
                                specDiameter = "20 mm",
                                actualDiameter = "19.8 mm",
                                construction = "6x36 WS",
                                type = "IWRC",
                                length = "150 m",
                                age = "2 years",
                                finding = GantryCraneInspectionResult(
                                    true,
                                    "Minor wear, no broken wires"
                                )
                            )
                        )
                    ),
                    mainHook = GantryCraneNdeHook(
                        method = "Magnetic Particle Testing",
                        specification = GantryCraneNdeMeasurement(
                            "100",
                            "50",
                            "120",
                            "30",
                            "",
                            "",
                            "",
                            "",
                            GantryCraneInspectionResult(true, "")
                        ), // dummy values
                        measurementResult = GantryCraneNdeMeasurement(
                            "99",
                            "49.5",
                            "119.5",
                            "29.8",
                            "",
                            "",
                            "",
                            "",
                            GantryCraneInspectionResult(true, "Within tolerance")
                        ), // dummy values
                        tolerance = GantryCraneNdeMeasurement(
                            "±2",
                            "±1",
                            "±2",
                            "±0.5",
                            "",
                            "",
                            "",
                            "",
                            GantryCraneInspectionResult(true, "")
                        ) // dummy values
                    ),
                    girder = GantryCraneNdeGirder(
                        method = "Ultrasonic Testing",
                        items = persistentListOf(
                            GantryCraneNdeGirderItem(
                                location = "Main Girder Weld Joint (Center)",
                                finding = GantryCraneInspectionResult(
                                    true,
                                    "No discontinuities found"
                                )
                            )
                        )
                    )
                ),
                testing = GantryCraneTesting(
                    dynamicTest = GantryCraneDynamicTest(
                        withoutLoad = GantryCraneDynamicWithoutLoad(
                            traveling = GantryCraneDynamicTestItem("Smooth", "Smooth", "Ok"),
                            traversing = GantryCraneDynamicTestItem("Smooth", "Smooth", "Ok"),
                            hoisting = GantryCraneDynamicTestItem("Smooth", "Smooth", "Ok"),
                            safetyDevice = GantryCraneDynamicTestItem(
                                "Functional",
                                "Functional",
                                "Ok"
                            ),
                            brakeSwitch = GantryCraneDynamicTestItem(
                                "Functional",
                                "Functional",
                                "Ok"
                            ),
                            brakeLockingDevice = GantryCraneDynamicTestItem(
                                "Functional",
                                "Functional",
                                "Ok"
                            ),
                            electricalInstallation = GantryCraneDynamicTestItem(
                                "Compliant",
                                "Compliant",
                                "Ok"
                            )
                        ),
                        withLoad = GantryCraneDynamicWithLoad(
                            noLoad = GantryCraneLoadTestResult(
                                "Smooth",
                                "Smooth",
                                "Smooth",
                                "Effective",
                                "Ok"
                            ),
                            swl25 = GantryCraneLoadTestResult(
                                "Smooth",
                                "Smooth",
                                "Smooth",
                                "Effective",
                                "Ok"
                            ),
                            swl50 = GantryCraneLoadTestResult(
                                "Smooth",
                                "Smooth",
                                "Smooth",
                                "Effective",
                                "Ok"
                            ),
                            swl75 = GantryCraneLoadTestResult(
                                "Smooth",
                                "Smooth",
                                "Smooth",
                                "Effective",
                                "Ok"
                            ),
                            swl100 = GantryCraneLoadTestResult(
                                "Smooth",
                                "Smooth",
                                "Smooth",
                                "Effective",
                                "Ok"
                            )
                        )
                    ),
                    staticTest = GantryCraneStaticTest(
                        load = "12500 kg (125% SWL)",
                        deflectionResult = GantryCraneInspectionResult(true, "Within tolerance"),
                        deflectionStandard = GantryCraneDeflectionStandard("L/1000", "25000", "25"),
                        deflectionMeasurement = persistentListOf(
                            GantryCraneDeflectionItem("Center", "18 mm", "25 mm", "Passed"),
                            GantryCraneDeflectionItem(
                                "Quarter Point Left",
                                "10 mm",
                                "25 mm",
                                "Passed"
                            ),
                            GantryCraneDeflectionItem(
                                "Quarter Point Right",
                                "12 mm",
                                "25 mm",
                                "Passed"
                            )
                        )
                    )
                ),
                conclusion = GantryCraneConclusion(
                    summary = persistentListOf(
                        "The gantry crane is in good operating condition and complies with all safety regulations.",
                        "All critical components passed visual inspection and NDE tests.",
                        "Dynamic and static load tests showed satisfactory performance."
                    ),
                    recommendations = persistentListOf(
                        "Implement a weekly visual inspection for wire ropes and hooks.",
                        "Schedule a full lubrication of all moving parts every six months.",
                        "Consider upgrading the operator cabin's AC unit for better climate control."
                    ),
                    nextInspectionDateSuggestion = "2025-10-01"
                )
            )
        )
    }
}
