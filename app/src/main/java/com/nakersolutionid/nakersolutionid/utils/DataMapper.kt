package com.nakersolutionid.nakersolutionid.utils

import com.nakersolutionid.nakersolutionid.data.remote.request.*
import com.nakersolutionid.nakersolutionid.domain.model.*
import com.nakersolutionid.nakersolutionid.features.report.elevator.*


fun ResultStatusUiState.toDomain(): ResultStatusDomain {
    return ResultStatusDomain(
        result = this.result,
        status = this.status
    )
}

fun GeneralDataUiState.toDomain(): GeneralDataDomain {
    return GeneralDataDomain(
        ownerName = this.ownerName,
        ownerAddress = this.ownerAddress,
        nameUsageLocation = this.nameUsageLocation,
        addressUsageLocation = this.addressUsageLocation,
        manufacturerOrInstaller = this.manufacturerOrInstaller,
        elevatorType = this.elevatorType,
        brandOrType = this.brandOrType,
        countryAndYear = this.countryAndYear,
        serialNumber = this.serialNumber,
        capacity = this.capacity,
        speed = this.speed,
        floorsServed = this.floorsServed,
        permitNumber = this.permitNumber,
        inspectionDate = this.inspectionDate
    )
}

fun TechnicalDocumentInspectionUiState.toDomain(): TechnicalDocumentInspectionDomain {
    return TechnicalDocumentInspectionDomain(
        designDrawing = this.designDrawing,
        technicalCalculation = this.technicalCalculation,
        materialCertificate = this.materialCertificate,
        controlPanelDiagram = this.controlPanelDiagram,
        asBuiltDrawing = this.asBuiltDrawing,
        componentCertificates = this.componentCertificates,
        safeWorkProcedure = this.safeWorkProcedure
    )
}

fun MachineRoomlessUiState.toDomain(): MachineRoomlessDomain {
    return MachineRoomlessDomain(
        panelPlacement = this.panelPlacement?.toDomain(),
        lightingWorkArea = this.lightingWorkArea?.toDomain(),
        lightingBetweenWorkArea = this.lightingBetweenWorkArea?.toDomain(),
        manualBrakeRelease = this.manualBrakeRelease?.toDomain(),
        fireExtinguisherPlacement = this.fireExtinguisherPlacement?.toDomain()
    )
}

fun MachineRoomAndMachineryUiState.toDomain(): MachineRoomAndMachineryDomain {
    return MachineRoomAndMachineryDomain(
        machineMounting = this.machineMounting?.toDomain(),
        mechanicalBrake = this.mechanicalBrake?.toDomain(),
        electricalBrake = this.electricalBrake?.toDomain(),
        machineRoomConstruction = this.machineRoomConstruction?.toDomain(),
        machineRoomClearance = this.machineRoomClearance?.toDomain(),
        machineRoomImplementation = this.machineRoomImplementation?.toDomain(),
        ventilation = this.ventilation?.toDomain(),
        machineRoomDoor = this.machineRoomDoor?.toDomain(),
        mainPowerPanelPosition = this.mainPowerPanelPosition?.toDomain(),
        rotatingPartsGuard = this.rotatingPartsGuard?.toDomain(),
        ropeHoleGuard = this.ropeHoleGuard?.toDomain(),
        machineRoomAccessLadder = this.machineRoomAccessLadder?.toDomain(),
        floorLevelDifference = this.floorLevelDifference?.toDomain(),
        fireExtinguisher = this.fireExtinguisher?.toDomain(),
        machineRoomless = this.machineRoomless?.toDomain(),
        emergencyStopSwitch = this.emergencyStopSwitch?.toDomain()
    )
}

fun SuspensionRopesAndBeltsUiState.toDomain(): SuspensionRopesAndBeltsDomain {
    return SuspensionRopesAndBeltsDomain(
        condition = this.condition?.toDomain(),
        chainUsage = this.chainUsage?.toDomain(),
        safetyFactor = this.safetyFactor?.toDomain(),
        ropeWithCounterweight = this.ropeWithCounterweight?.toDomain(),
        ropeWithoutCounterweight = this.ropeWithoutCounterweight?.toDomain(),
        belt = this.belt?.toDomain(),
        slackRopeDevice = this.slackRopeDevice?.toDomain()
    )
}

fun DrumsAndSheavesUiState.toDomain(): DrumsAndSheavesDomain {
    return DrumsAndSheavesDomain(
        drumGrooves = this.drumGrooves?.toDomain(),
        passengerDrumDiameter = this.passengerDrumDiameter?.toDomain(),
        governorDrumDiameter = this.governorDrumDiameter?.toDomain()
    )
}

fun HoistwayAndPitUiState.toDomain(): HoistwayAndPitDomain {
    return HoistwayAndPitDomain(
        construction = this.construction?.toDomain(),
        walls = this.walls?.toDomain(),
        inclinedElevatorTrackBed = this.inclinedElevatorTrackBed?.toDomain(),
        cleanliness = this.cleanliness?.toDomain(),
        lighting = this.lighting?.toDomain(),
        emergencyDoorNonStop = this.emergencyDoorNonStop?.toDomain(),
        emergencyDoorSize = this.emergencyDoorSize?.toDomain(),
        emergencyDoorSafetySwitch = this.emergencyDoorSafetySwitch?.toDomain(),
        emergencyDoorBridge = this.emergencyDoorBridge?.toDomain(),
        carTopClearance = this.carTopClearance?.toDomain(),
        pitClearance = this.pitClearance?.toDomain(),
        pitLadder = this.pitLadder?.toDomain(),
        pitBelowWorkingArea = this.pitBelowWorkingArea?.toDomain(),
        pitAccessSwitch = this.pitAccessSwitch?.toDomain(),
        pitScreen = this.pitScreen?.toDomain(),
        hoistwayDoorLeaf = this.hoistwayDoorLeaf?.toDomain(),
        hoistwayDoorInterlock = this.hoistwayDoorInterlock?.toDomain(),
        floorLeveling = this.floorLeveling?.toDomain(),
        hoistwaySeparatorBeam = this.hoistwaySeparatorBeam?.toDomain(),
        inclinedElevatorStairs = this.inclinedElevatorStairs?.toDomain()
    )
}

fun CarDoorSpecsUiState.toDomain(): CarDoorSpecsDomain {
    return CarDoorSpecsDomain(
        size = this.size?.toDomain(),
        lockAndSwitch = this.lockAndSwitch?.toDomain(),
        sillClearance = this.sillClearance?.toDomain()
    )
}

fun CarSignageUiState.toDomain(): CarSignageDomain {
    return CarSignageDomain(
        manufacturerName = this.manufacturerName?.toDomain(),
        loadCapacity = this.loadCapacity?.toDomain(),
        noSmokingSign = this.noSmokingSign?.toDomain(),
        overloadIndicator = this.overloadIndicator?.toDomain(),
        doorOpenCloseButtons = this.doorOpenCloseButtons?.toDomain(),
        floorButtons = this.floorButtons?.toDomain(),
        alarmButton = this.alarmButton?.toDomain(),
        twoWayIntercom = this.twoWayIntercom?.toDomain()
    )
}

fun CarUiState.toDomain(): CarDomain {
    return CarDomain(
        frame = this.frame?.toDomain(),
        body = this.body?.toDomain(),
        wallHeight = this.wallHeight?.toDomain(),
        floorArea = this.floorArea?.toDomain(),
        carAreaExpansion = this.carAreaExpansion?.toDomain(),
        carDoor = this.carDoor?.toDomain(),
        carDoorSpecs = this.carDoorSpecs?.toDomain(),
        carToBeamClearance = this.carToBeamClearance?.toDomain(),
        alarmBell = this.alarmBell?.toDomain(),
        backupPowerARD = this.backupPowerARD?.toDomain(),
        intercom = this.intercom?.toDomain(),
        ventilation = this.ventilation?.toDomain(),
        emergencyLighting = this.emergencyLighting?.toDomain(),
        operatingPanel = this.operatingPanel?.toDomain(),
        carPositionIndicator = this.carPositionIndicator?.toDomain(),
        carSignage = this.carSignage?.toDomain(),
        carRoofStrength = this.carRoofStrength?.toDomain(),
        carTopEmergencyExit = this.carTopEmergencyExit?.toDomain(),
        carSideEmergencyExit = this.carSideEmergencyExit?.toDomain(),
        carTopGuardRail = this.carTopGuardRail?.toDomain(),
        guardRailHeight300to850 = this.guardRailHeight300to850?.toDomain(),
        guardRailHeightOver850 = this.guardRailHeightOver850?.toDomain(),
        carTopLighting = this.carTopLighting?.toDomain(),
        manualOperationButtons = this.manualOperationButtons?.toDomain(),
        carInterior = this.carInterior?.toDomain()
    )
}

fun GovernorAndSafetyBrakeUiState.toDomain(): GovernorAndSafetyBrakeDomain {
    return GovernorAndSafetyBrakeDomain(
        governorRopeClamp = this.governorRopeClamp?.toDomain(),
        governorSwitch = this.governorSwitch?.toDomain(),
        safetyBrakeSpeed = this.safetyBrakeSpeed?.toDomain(),
        safetyBrakeType = this.safetyBrakeType?.toDomain(),
        safetyBrakeMechanism = this.safetyBrakeMechanism?.toDomain(),
        progressiveSafetyBrake = this.progressiveSafetyBrake?.toDomain(),
        instantaneousSafetyBrake = this.instantaneousSafetyBrake?.toDomain(),
        safetyBrakeOperation = this.safetyBrakeOperation?.toDomain(),
        electricalCutoutSwitch = this.electricalCutoutSwitch?.toDomain(),
        limitSwitch = this.limitSwitch?.toDomain(),
        overloadDevice = this.overloadDevice?.toDomain()
    )
}

fun CounterweightGuideRailsAndBuffersUiState.toDomain(): CounterweightGuideRailsAndBuffersDomain {
    return CounterweightGuideRailsAndBuffersDomain(
        counterweightMaterial = this.counterweightMaterial?.toDomain(),
        counterweightGuardScreen = this.counterweightGuardScreen?.toDomain(),
        guideRailConstruction = this.guideRailConstruction?.toDomain(),
        bufferType = this.bufferType?.toDomain(),
        bufferFunction = this.bufferFunction?.toDomain(),
        bufferSafetySwitch = this.bufferSafetySwitch?.toDomain()
    )
}

fun FireServiceElevatorUiState.toDomain(): FireServiceElevatorDomain {
    return FireServiceElevatorDomain(
        backupPower = this.backupPower?.toDomain(),
        specialOperation = this.specialOperation?.toDomain(),
        fireSwitch = this.fireSwitch?.toDomain(),
        label = this.label?.toDomain(),
        electricalFireResistance = this.electricalFireResistance?.toDomain(),
        hoistwayWallFireResistance = this.hoistwayWallFireResistance?.toDomain(),
        carSize = this.carSize?.toDomain(),
        doorSize = this.doorSize?.toDomain(),
        travelTime = this.travelTime?.toDomain(),
        evacuationFloor = this.evacuationFloor?.toDomain()
    )
}

fun AccessibilityElevatorUiState.toDomain(): AccessibilityElevatorDomain {
    return AccessibilityElevatorDomain(
        operatingPanel = this.operatingPanel?.toDomain(),
        panelHeight = this.panelHeight?.toDomain(),
        doorOpenTime = this.doorOpenTime?.toDomain(),
        doorWidth = this.doorWidth?.toDomain(),
        audioInformation = this.audioInformation?.toDomain(),
        label = this.label?.toDomain()
    )
}

fun SeismicSensorUiState.toDomain(): SeismicSensorDomain {
    return SeismicSensorDomain(
        availability = this.availability?.toDomain(),
        function = this.function?.toDomain()
    )
}

fun ElectricalInstallationUiState.toDomain(): ElectricalInstallationDomain {
    return ElectricalInstallationDomain(
        installationStandard = this.installationStandard?.toDomain(),
        electricalPanel = this.electricalPanel?.toDomain(),
        backupPowerARD = this.backupPowerARD?.toDomain(),
        groundingCable = this.groundingCable?.toDomain(),
        fireAlarmConnection = this.fireAlarmConnection?.toDomain(),
        fireServiceElevator = this.fireServiceElevator?.toDomain(),
        accessibilityElevator = this.accessibilityElevator?.toDomain(),
        seismicSensor = this.seismicSensor?.toDomain()
    )
}

fun InspectionAndTestingUiState.toDomain(): InspectionAndTestingDomain {
    return InspectionAndTestingDomain(
        machineRoomAndMachinery = this.machineRoomAndMachinery?.toDomain(),
        suspensionRopesAndBelts = this.suspensionRopesAndBelts?.toDomain(),
        drumsAndSheaves = this.drumsAndSheaves?.toDomain(),
        hoistwayAndPit = this.hoistwayAndPit?.toDomain(),
        car = this.car?.toDomain(),
        governorAndSafetyBrake = this.governorAndSafetyBrake?.toDomain(),
        counterweightGuideRailsAndBuffers = this.counterweightGuideRailsAndBuffers?.toDomain(),
        electricalInstallation = this.electricalInstallation?.toDomain()
    )
}

fun ElevatorUiState.toDomain(): Report {
    return Report(
        id = this.id,
        nameOfInspectionType = this.nameOfInspectionType,
        subNameOfInspectionType = this.subNameOfInspectionType,
        typeInspection = this.typeInspection,
        eskOrElevType = this.eskOrElevType,
        generalData = this.generalData?.toDomain(),
        technicalDocumentInspection = this.technicalDocumentInspection?.toDomain(),
        inspectionAndTesting = this.inspectionAndTesting?.toDomain(),
        conclusion = this.conclusion
    )
}

// --- Domain to Network Mappers ---

fun ResultStatusDomain.toNetwork(): ResultStatusNetwork {
    return ResultStatusNetwork(
        result = this.result,
        status = this.status
    )
}

fun GeneralDataDomain.toNetwork(): GeneralDataNetwork {
    return GeneralDataNetwork(
        ownerName = this.ownerName,
        ownerAddress = this.ownerAddress,
        nameUsageLocation = this.nameUsageLocation,
        addressUsageLocation = this.addressUsageLocation,
        manufacturerOrInstaller = this.manufacturerOrInstaller,
        elevatorType = this.elevatorType,
        brandOrType = this.brandOrType,
        countryAndYear = this.countryAndYear,
        serialNumber = this.serialNumber,
        capacity = this.capacity,
        speed = this.speed,
        floorsServed = this.floorsServed,
        permitNumber = this.permitNumber,
        inspectionDate = this.inspectionDate
    )
}

fun TechnicalDocumentInspectionDomain.toNetwork(): TechnicalDocumentInspectionNetwork {
    return TechnicalDocumentInspectionNetwork(
        designDrawing = this.designDrawing,
        technicalCalculation = this.technicalCalculation,
        materialCertificate = this.materialCertificate,
        controlPanelDiagram = this.controlPanelDiagram,
        asBuiltDrawing = this.asBuiltDrawing,
        componentCertificates = this.componentCertificates,
        safeWorkProcedure = this.safeWorkProcedure
    )
}

fun MachineRoomlessDomain.toNetwork(): MachineRoomlessNetwork {
    return MachineRoomlessNetwork(
        panelPlacement = this.panelPlacement?.toNetwork(),
        lightingWorkArea = this.lightingWorkArea?.toNetwork(),
        lightingBetweenWorkArea = this.lightingBetweenWorkArea?.toNetwork(),
        manualBrakeRelease = this.manualBrakeRelease?.toNetwork(),
        fireExtinguisherPlacement = this.fireExtinguisherPlacement?.toNetwork()
    )
}

fun MachineRoomAndMachineryDomain.toNetwork(): MachineRoomAndMachineryNetwork {
    return MachineRoomAndMachineryNetwork(
        machineMounting = this.machineMounting?.toNetwork(),
        mechanicalBrake = this.mechanicalBrake?.toNetwork(),
        electricalBrake = this.electricalBrake?.toNetwork(),
        machineRoomConstruction = this.machineRoomConstruction?.toNetwork(),
        machineRoomClearance = this.machineRoomClearance?.toNetwork(),
        machineRoomImplementation = this.machineRoomImplementation?.toNetwork(),
        ventilation = this.ventilation?.toNetwork(),
        machineRoomDoor = this.machineRoomDoor?.toNetwork(),
        mainPowerPanelPosition = this.mainPowerPanelPosition?.toNetwork(),
        rotatingPartsGuard = this.rotatingPartsGuard?.toNetwork(),
        ropeHoleGuard = this.ropeHoleGuard?.toNetwork(),
        machineRoomAccessLadder = this.machineRoomAccessLadder?.toNetwork(),
        floorLevelDifference = this.floorLevelDifference?.toNetwork(),
        fireExtinguisher = this.fireExtinguisher?.toNetwork(),
        machineRoomless = this.machineRoomless?.toNetwork(),
        emergencyStopSwitch = this.emergencyStopSwitch?.toNetwork()
    )
}

fun SuspensionRopesAndBeltsDomain.toNetwork(): SuspensionRopesAndBeltsNetwork {
    return SuspensionRopesAndBeltsNetwork(
        condition = this.condition?.toNetwork(),
        chainUsage = this.chainUsage?.toNetwork(),
        safetyFactor = this.safetyFactor?.toNetwork(),
        ropeWithCounterweight = this.ropeWithCounterweight?.toNetwork(),
        ropeWithoutCounterweight = this.ropeWithoutCounterweight?.toNetwork(),
        belt = this.belt?.toNetwork(),
        slackRopeDevice = this.slackRopeDevice?.toNetwork()
    )
}

fun DrumsAndSheavesDomain.toNetwork(): DrumsAndSheavesNetwork {
    return DrumsAndSheavesNetwork(
        drumGrooves = this.drumGrooves?.toNetwork(),
        passengerDrumDiameter = this.passengerDrumDiameter?.toNetwork(),
        governorDrumDiameter = this.governorDrumDiameter?.toNetwork()
    )
}

fun HoistwayAndPitDomain.toNetwork(): HoistwayAndPitNetwork {
    return HoistwayAndPitNetwork(
        construction = this.construction?.toNetwork(),
        walls = this.walls?.toNetwork(),
        inclinedElevatorTrackBed = this.inclinedElevatorTrackBed?.toNetwork(),
        cleanliness = this.cleanliness?.toNetwork(),
        lighting = this.lighting?.toNetwork(),
        emergencyDoorNonStop = this.emergencyDoorNonStop?.toNetwork(),
        emergencyDoorSize = this.emergencyDoorSize?.toNetwork(),
        emergencyDoorSafetySwitch = this.emergencyDoorSafetySwitch?.toNetwork(),
        emergencyDoorBridge = this.emergencyDoorBridge?.toNetwork(),
        carTopClearance = this.carTopClearance?.toNetwork(),
        pitClearance = this.pitClearance?.toNetwork(),
        pitLadder = this.pitLadder?.toNetwork(),
        pitBelowWorkingArea = this.pitBelowWorkingArea?.toNetwork(),
        pitAccessSwitch = this.pitAccessSwitch?.toNetwork(),
        pitScreen = this.pitScreen?.toNetwork(),
        hoistwayDoorLeaf = this.hoistwayDoorLeaf?.toNetwork(),
        hoistwayDoorInterlock = this.hoistwayDoorInterlock?.toNetwork(),
        floorLeveling = this.floorLeveling?.toNetwork(),
        hoistwaySeparatorBeam = this.hoistwaySeparatorBeam?.toNetwork(),
        inclinedElevatorStairs = this.inclinedElevatorStairs?.toNetwork()
    )
}

fun CarDoorSpecsDomain.toNetwork(): CarDoorSpecsNetwork {
    return CarDoorSpecsNetwork(
        size = this.size?.toNetwork(),
        lockAndSwitch = this.lockAndSwitch?.toNetwork(),
        sillClearance = this.sillClearance?.toNetwork()
    )
}

fun CarSignageDomain.toNetwork(): CarSignageNetwork {
    return CarSignageNetwork(
        manufacturerName = this.manufacturerName?.toNetwork(),
        loadCapacity = this.loadCapacity?.toNetwork(),
        noSmokingSign = this.noSmokingSign?.toNetwork(),
        overloadIndicator = this.overloadIndicator?.toNetwork(),
        doorOpenCloseButtons = this.doorOpenCloseButtons?.toNetwork(),
        floorButtons = this.floorButtons?.toNetwork(),
        alarmButton = this.alarmButton?.toNetwork(),
        twoWayIntercom = this.twoWayIntercom?.toNetwork()
    )
}

fun CarDomain.toNetwork(): CarNetwork {
    return CarNetwork(
        frame = this.frame?.toNetwork(),
        body = this.body?.toNetwork(),
        wallHeight = this.wallHeight?.toNetwork(),
        floorArea = this.floorArea?.toNetwork(),
        carAreaExpansion = this.carAreaExpansion?.toNetwork(),
        carDoor = this.carDoor?.toNetwork(),
        carDoorSpecs = this.carDoorSpecs?.toNetwork(),
        carToBeamClearance = this.carToBeamClearance?.toNetwork(),
        alarmBell = this.alarmBell?.toNetwork(),
        backupPowerARD = this.backupPowerARD?.toNetwork(),
        intercom = this.intercom?.toNetwork(),
        ventilation = this.ventilation?.toNetwork(),
        emergencyLighting = this.emergencyLighting?.toNetwork(),
        operatingPanel = this.operatingPanel?.toNetwork(),
        carPositionIndicator = this.carPositionIndicator?.toNetwork(),
        carSignage = this.carSignage?.toNetwork(),
        carRoofStrength = this.carRoofStrength?.toNetwork(),
        carTopEmergencyExit = this.carTopEmergencyExit?.toNetwork(),
        carSideEmergencyExit = this.carSideEmergencyExit?.toNetwork(),
        carTopGuardRail = this.carTopGuardRail?.toNetwork(),
        guardRailHeight300to850 = this.guardRailHeight300to850?.toNetwork(),
        guardRailHeightOver850 = this.guardRailHeightOver850?.toNetwork(),
        carTopLighting = this.carTopLighting?.toNetwork(),
        manualOperationButtons = this.manualOperationButtons?.toNetwork(),
        carInterior = this.carInterior?.toNetwork()
    )
}

fun GovernorAndSafetyBrakeDomain.toNetwork(): GovernorAndSafetyBrakeNetwork {
    return GovernorAndSafetyBrakeNetwork(
        governorRopeClamp = this.governorRopeClamp?.toNetwork(),
        governorSwitch = this.governorSwitch?.toNetwork(),
        safetyBrakeSpeed = this.safetyBrakeSpeed?.toNetwork(),
        safetyBrakeType = this.safetyBrakeType?.toNetwork(),
        safetyBrakeMechanism = this.safetyBrakeMechanism?.toNetwork(),
        progressiveSafetyBrake = this.progressiveSafetyBrake?.toNetwork(),
        instantaneousSafetyBrake = this.instantaneousSafetyBrake?.toNetwork(),
        safetyBrakeOperation = this.safetyBrakeOperation?.toNetwork(),
        electricalCutoutSwitch = this.electricalCutoutSwitch?.toNetwork(),
        limitSwitch = this.limitSwitch?.toNetwork(),
        overloadDevice = this.overloadDevice?.toNetwork()
    )
}

fun CounterweightGuideRailsAndBuffersDomain.toNetwork(): CounterweightGuideRailsAndBuffersNetwork {
    return CounterweightGuideRailsAndBuffersNetwork(
        counterweightMaterial = this.counterweightMaterial?.toNetwork(),
        counterweightGuardScreen = this.counterweightGuardScreen?.toNetwork(),
        guideRailConstruction = this.guideRailConstruction?.toNetwork(),
        bufferType = this.bufferType?.toNetwork(),
        bufferFunction = this.bufferFunction?.toNetwork(),
        bufferSafetySwitch = this.bufferSafetySwitch?.toNetwork()
    )
}

fun FireServiceElevatorDomain.toNetwork(): FireServiceElevatorNetwork {
    return FireServiceElevatorNetwork(
        backupPower = this.backupPower?.toNetwork(),
        specialOperation = this.specialOperation?.toNetwork(),
        fireSwitch = this.fireSwitch?.toNetwork(),
        label = this.label?.toNetwork(),
        electricalFireResistance = this.electricalFireResistance?.toNetwork(),
        hoistwayWallFireResistance = this.hoistwayWallFireResistance?.toNetwork(),
        carSize = this.carSize?.toNetwork(),
        doorSize = this.doorSize?.toNetwork(),
        travelTime = this.travelTime?.toNetwork(),
        evacuationFloor = this.evacuationFloor?.toNetwork()
    )
}

fun AccessibilityElevatorDomain.toNetwork(): AccessibilityElevatorNetwork {
    return AccessibilityElevatorNetwork(
        operatingPanel = this.operatingPanel?.toNetwork(),
        panelHeight = this.panelHeight?.toNetwork(),
        doorOpenTime = this.doorOpenTime?.toNetwork(),
        doorWidth = this.doorWidth?.toNetwork(),
        audioInformation = this.audioInformation?.toNetwork(),
        label = this.label?.toNetwork()
    )
}

fun SeismicSensorDomain.toNetwork(): SeismicSensorNetwork {
    return SeismicSensorNetwork(
        availability = this.availability?.toNetwork(),
        function = this.function?.toNetwork()
    )
}

fun ElectricalInstallationDomain.toNetwork(): ElectricalInstallationNetwork {
    return ElectricalInstallationNetwork(
        installationStandard = this.installationStandard?.toNetwork(),
        electricalPanel = this.electricalPanel?.toNetwork(),
        backupPowerARD = this.backupPowerARD?.toNetwork(),
        groundingCable = this.groundingCable?.toNetwork(),
        fireAlarmConnection = this.fireAlarmConnection?.toNetwork(),
        fireServiceElevator = this.fireServiceElevator?.toNetwork(),
        accessibilityElevator = this.accessibilityElevator?.toNetwork(),
        seismicSensor = this.seismicSensor?.toNetwork()
    )
}

fun InspectionAndTestingDomain.toNetwork(): InspectionAndTestingNetwork {
    return InspectionAndTestingNetwork(
        machineRoomAndMachinery = this.machineRoomAndMachinery?.toNetwork(),
        suspensionRopesAndBelts = this.suspensionRopesAndBelts?.toNetwork(),
        drumsAndSheaves = this.drumsAndSheaves?.toNetwork(),
        hoistwayAndPit = this.hoistwayAndPit?.toNetwork(),
        car = this.car?.toNetwork(),
        governorAndSafetyBrake = this.governorAndSafetyBrake?.toNetwork(),
        counterweightGuideRailsAndBuffers = this.counterweightGuideRailsAndBuffers?.toNetwork(),
        electricalInstallation = this.electricalInstallation?.toNetwork()
    )
}

fun Report.toNetwork(): SendReportRequest {
    return SendReportRequest(
        nameOfInspectionType = this.nameOfInspectionType,
        subNameOfInspectionType = this.subNameOfInspectionType,
        typeInspection = this.typeInspection,
        EskOrElevType = this.eskOrElevType,
        generalData = this.generalData?.toNetwork(),
        technicalDocumentInspection = this.technicalDocumentInspection?.toNetwork(),
        inspectionAndTesting = this.inspectionAndTesting?.toNetwork(),
        conclusion = this.conclusion
    )
}