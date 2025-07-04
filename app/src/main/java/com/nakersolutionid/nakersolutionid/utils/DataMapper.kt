package com.nakersolutionid.nakersolutionid.utils

import com.nakersolutionid.nakersolutionid.data.local.entity.*
import com.nakersolutionid.nakersolutionid.data.remote.request.*
import com.nakersolutionid.nakersolutionid.domain.model.*
import com.nakersolutionid.nakersolutionid.features.report.elevator.*

object DataMapper {
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

    fun Report.toEntity(id: String): ReportEntity {
        return ReportEntity(
            id = id, // The ID must be provided as it's not in the domain model
            nameOfInspectionType = this.nameOfInspectionType,
            subNameOfInspectionType = this.subNameOfInspectionType,
            typeInspection = this.typeInspection,
            eskOrElevType = this.eskOrElevType,
            generalData = this.generalData?.toEntity(),
            technicalDocumentInspection = this.technicalDocumentInspection?.toEntity(),
            inspectionAndTesting = this.inspectionAndTesting?.toEntity(),
            conclusion = this.conclusion
        )
    }


// --- Component Mappers ---

    private fun ResultStatusDomain.toEntity(): ResultStatus {
        return ResultStatus(
            result = this.result,
            status = this.status
        )
    }

    private fun GeneralDataDomain.toEntity(): GeneralData {
        return GeneralData(
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

    private fun TechnicalDocumentInspectionDomain.toEntity(): TechnicalDocumentInspection {
        return TechnicalDocumentInspection(
            designDrawing = this.designDrawing,
            technicalCalculation = this.technicalCalculation,
            materialCertificate = this.materialCertificate,
            controlPanelDiagram = this.controlPanelDiagram,
            asBuiltDrawing = this.asBuiltDrawing,
            componentCertificates = this.componentCertificates,
            safeWorkProcedure = this.safeWorkProcedure
        )
    }

    private fun InspectionAndTestingDomain.toEntity(): InspectionAndTesting {
        return InspectionAndTesting(
            machineRoomAndMachinery = this.machineRoomAndMachinery?.toEntity(),
            suspensionRopesAndBelts = this.suspensionRopesAndBelts?.toEntity(),
            drumsAndSheaves = this.drumsAndSheaves?.toEntity(),
            hoistwayAndPit = this.hoistwayAndPit?.toEntity(),
            car = this.car?.toEntity(),
            governorAndSafetyBrake = this.governorAndSafetyBrake?.toEntity(),
            counterweightGuideRailsAndBuffers = this.counterweightGuideRailsAndBuffers?.toEntity(),
            electricalInstallation = this.electricalInstallation?.toEntity()
        )
    }

    private fun MachineRoomAndMachineryDomain.toEntity(): MachineRoomAndMachinery {
        return MachineRoomAndMachinery(
            machineMounting = this.machineMounting?.toEntity(),
            mechanicalBrake = this.mechanicalBrake?.toEntity(),
            electricalBrake = this.electricalBrake?.toEntity(),
            machineRoomConstruction = this.machineRoomConstruction?.toEntity(),
            machineRoomClearance = this.machineRoomClearance?.toEntity(),
            machineRoomImplementation = this.machineRoomImplementation?.toEntity(),
            ventilation = this.ventilation?.toEntity(),
            machineRoomDoor = this.machineRoomDoor?.toEntity(),
            mainPowerPanelPosition = this.mainPowerPanelPosition?.toEntity(),
            rotatingPartsGuard = this.rotatingPartsGuard?.toEntity(),
            ropeHoleGuard = this.ropeHoleGuard?.toEntity(),
            machineRoomAccessLadder = this.machineRoomAccessLadder?.toEntity(),
            floorLevelDifference = this.floorLevelDifference?.toEntity(),
            fireExtinguisher = this.fireExtinguisher?.toEntity(),
            machineRoomless = this.machineRoomless?.toEntity(),
            emergencyStopSwitch = this.emergencyStopSwitch?.toEntity()
        )
    }

    private fun MachineRoomlessDomain.toEntity(): MachineRoomless {
        return MachineRoomless(
            panelPlacement = this.panelPlacement?.toEntity(),
            lightingWorkArea = this.lightingWorkArea?.toEntity(),
            lightingBetweenWorkArea = this.lightingBetweenWorkArea?.toEntity(),
            manualBrakeRelease = this.manualBrakeRelease?.toEntity(),
            fireExtinguisherPlacement = this.fireExtinguisherPlacement?.toEntity()
        )
    }

    private fun SuspensionRopesAndBeltsDomain.toEntity(): SuspensionRopesAndBelts {
        return SuspensionRopesAndBelts(
            condition = this.condition?.toEntity(),
            chainUsage = this.chainUsage?.toEntity(),
            safetyFactor = this.safetyFactor?.toEntity(),
            ropeWithCounterweight = this.ropeWithCounterweight?.toEntity(),
            ropeWithoutCounterweight = this.ropeWithoutCounterweight?.toEntity(),
            belt = this.belt?.toEntity(),
            slackRopeDevice = this.slackRopeDevice?.toEntity()
        )
    }

    private fun DrumsAndSheavesDomain.toEntity(): DrumsAndSheaves {
        return DrumsAndSheaves(
            drumGrooves = this.drumGrooves?.toEntity(),
            passengerDrumDiameter = this.passengerDrumDiameter?.toEntity(),
            governorDrumDiameter = this.governorDrumDiameter?.toEntity()
        )
    }

    private fun HoistwayAndPitDomain.toEntity(): HoistwayAndPit {
        return HoistwayAndPit(
            construction = this.construction?.toEntity(),
            walls = this.walls?.toEntity(),
            inclinedElevatorTrackBed = this.inclinedElevatorTrackBed?.toEntity(),
            cleanliness = this.cleanliness?.toEntity(),
            lighting = this.lighting?.toEntity(),
            emergencyDoorNonStop = this.emergencyDoorNonStop?.toEntity(),
            emergencyDoorSize = this.emergencyDoorSize?.toEntity(),
            emergencyDoorSafetySwitch = this.emergencyDoorSafetySwitch?.toEntity(),
            emergencyDoorBridge = this.emergencyDoorBridge?.toEntity(),
            carTopClearance = this.carTopClearance?.toEntity(),
            pitClearance = this.pitClearance?.toEntity(),
            pitLadder = this.pitLadder?.toEntity(),
            pitBelowWorkingArea = this.pitBelowWorkingArea?.toEntity(),
            pitAccessSwitch = this.pitAccessSwitch?.toEntity(),
            pitScreen = this.pitScreen?.toEntity(),
            hoistwayDoorLeaf = this.hoistwayDoorLeaf?.toEntity(),
            hoistwayDoorInterlock = this.hoistwayDoorInterlock?.toEntity(),
            floorLeveling = this.floorLeveling?.toEntity(),
            hoistwaySeparatorBeam = this.hoistwaySeparatorBeam?.toEntity(),
            inclinedElevatorStairs = this.inclinedElevatorStairs?.toEntity()
        )
    }

    private fun CarDomain.toEntity(): Car {
        return Car(
            frame = this.frame?.toEntity(),
            body = this.body?.toEntity(),
            wallHeight = this.wallHeight?.toEntity(),
            floorArea = this.floorArea?.toEntity(),
            carAreaExpansion = this.carAreaExpansion?.toEntity(),
            carDoor = this.carDoor?.toEntity(),
            carDoorSpecs = this.carDoorSpecs?.toEntity(),
            carToBeamClearance = this.carToBeamClearance?.toEntity(),
            alarmBell = this.alarmBell?.toEntity(),
            backupPowerARD = this.backupPowerARD?.toEntity(),
            intercom = this.intercom?.toEntity(),
            ventilation = this.ventilation?.toEntity(),
            emergencyLighting = this.emergencyLighting?.toEntity(),
            operatingPanel = this.operatingPanel?.toEntity(),
            carPositionIndicator = this.carPositionIndicator?.toEntity(),
            carSignage = this.carSignage?.toEntity(),
            carRoofStrength = this.carRoofStrength?.toEntity(),
            carTopEmergencyExit = this.carTopEmergencyExit?.toEntity(),
            carSideEmergencyExit = this.carSideEmergencyExit?.toEntity(),
            carTopGuardRail = this.carTopGuardRail?.toEntity(),
            guardRailHeight300to850 = this.guardRailHeight300to850?.toEntity(),
            guardRailHeightOver850 = this.guardRailHeightOver850?.toEntity(),
            carTopLighting = this.carTopLighting?.toEntity(),
            manualOperationButtons = this.manualOperationButtons?.toEntity(),
            carInterior = this.carInterior?.toEntity()
        )
    }

    private fun CarDoorSpecsDomain.toEntity(): CarDoorSpecs {
        return CarDoorSpecs(
            size = this.size?.toEntity(),
            lockAndSwitch = this.lockAndSwitch?.toEntity(),
            sillClearance = this.sillClearance?.toEntity()
        )
    }

    private fun CarSignageDomain.toEntity(): CarSignage {
        return CarSignage(
            manufacturerName = this.manufacturerName?.toEntity(),
            loadCapacity = this.loadCapacity?.toEntity(),
            noSmokingSign = this.noSmokingSign?.toEntity(),
            overloadIndicator = this.overloadIndicator?.toEntity(),
            doorOpenCloseButtons = this.doorOpenCloseButtons?.toEntity(),
            floorButtons = this.floorButtons?.toEntity(),
            alarmButton = this.alarmButton?.toEntity(),
            twoWayIntercom = this.twoWayIntercom?.toEntity()
        )
    }

    private fun GovernorAndSafetyBrakeDomain.toEntity(): GovernorAndSafetyBrake {
        return GovernorAndSafetyBrake(
            governorRopeClamp = this.governorRopeClamp?.toEntity(),
            governorSwitch = this.governorSwitch?.toEntity(),
            safetyBrakeSpeed = this.safetyBrakeSpeed?.toEntity(),
            safetyBrakeType = this.safetyBrakeType?.toEntity(),
            safetyBrakeMechanism = this.safetyBrakeMechanism?.toEntity(),
            progressiveSafetyBrake = this.progressiveSafetyBrake?.toEntity(),
            instantaneousSafetyBrake = this.instantaneousSafetyBrake?.toEntity(),
            safetyBrakeOperation = this.safetyBrakeOperation?.toEntity(),
            electricalCutoutSwitch = this.electricalCutoutSwitch?.toEntity(),
            limitSwitch = this.limitSwitch?.toEntity(),
            overloadDevice = this.overloadDevice?.toEntity()
        )
    }

    private fun CounterweightGuideRailsAndBuffersDomain.toEntity(): CounterweightGuideRailsAndBuffers {
        return CounterweightGuideRailsAndBuffers(
            counterweightMaterial = this.counterweightMaterial?.toEntity(),
            counterweightGuardScreen = this.counterweightGuardScreen?.toEntity(),
            guideRailConstruction = this.guideRailConstruction?.toEntity(),
            bufferType = this.bufferType?.toEntity(),
            bufferFunction = this.bufferFunction?.toEntity(),
            bufferSafetySwitch = this.bufferSafetySwitch?.toEntity()
        )
    }

    private fun ElectricalInstallationDomain.toEntity(): ElectricalInstallation {
        return ElectricalInstallation(
            installationStandard = this.installationStandard?.toEntity(),
            electricalPanel = this.electricalPanel?.toEntity(),
            backupPowerARD = this.backupPowerARD?.toEntity(),
            groundingCable = this.groundingCable?.toEntity(),
            fireAlarmConnection = this.fireAlarmConnection?.toEntity(),
            fireServiceElevator = this.fireServiceElevator?.toEntity(),
            accessibilityElevator = this.accessibilityElevator?.toEntity(),
            seismicSensor = this.seismicSensor?.toEntity()
        )
    }

    private fun FireServiceElevatorDomain.toEntity(): FireServiceElevator {
        return FireServiceElevator(
            backupPower = this.backupPower?.toEntity(),
            specialOperation = this.specialOperation?.toEntity(),
            fireSwitch = this.fireSwitch?.toEntity(),
            label = this.label?.toEntity(),
            electricalFireResistance = this.electricalFireResistance?.toEntity(),
            hoistwayWallFireResistance = this.hoistwayWallFireResistance?.toEntity(),
            carSize = this.carSize?.toEntity(),
            doorSize = this.doorSize?.toEntity(),
            travelTime = this.travelTime?.toEntity(),
            evacuationFloor = this.evacuationFloor?.toEntity()
        )
    }

    private fun AccessibilityElevatorDomain.toEntity(): AccessibilityElevator {
        return AccessibilityElevator(
            operatingPanel = this.operatingPanel?.toEntity(),
            panelHeight = this.panelHeight?.toEntity(),
            doorOpenTime = this.doorOpenTime?.toEntity(),
            doorWidth = this.doorWidth?.toEntity(),
            audioInformation = this.audioInformation?.toEntity(),
            label = this.label?.toEntity()
        )
    }

    private fun SeismicSensorDomain.toEntity(): SeismicSensor {
        return SeismicSensor(
            availability = this.availability?.toEntity(),
            function = this.function?.toEntity()
        )
    }
}