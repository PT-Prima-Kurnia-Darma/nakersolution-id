package com.nakersolutionid.nakersolutionid.features.report.ee.eskalator

import androidx.compose.runtime.Immutable
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

@Immutable
data class EskalatorUiState(
    val isLoading: Boolean = false,
    val eskalatorData: EskalatorGeneralData = EskalatorGeneralData()
) {
    companion object {
        fun createDummyEskalatorUiState(): EskalatorUiState {
            return EskalatorUiState(
                isLoading = false,
                eskalatorData = EskalatorGeneralData(
                    extraId = "ESK-001",
                    moreExtraId = "BATCH-ABC",
                    equipmentType = "Escalator",
                    examinationType = "Periodic Inspection",
                    conclusion = "The escalator is in good working condition.",
                    companyData = EskalatorCompanyData(
                        companyOrBuildingName = "Metro Mall",
                        usageAddress = "789 Commerce St, Metropolis, USA",
                        safetyObjectTypeAndNumber = "ESK-12345",
                        intendedUse = "Public Transport",
                        usagePermit = "PERMIT-ESK-6789",
                        inspectionDate = "2023-10-27"
                    ),
                    technicalData = EskalatorTechnicalData(
                        manufacturer = "Global Elevators Inc.",
                        brand = "BrandY",
                        countryAndYear = "Germany, 2018",
                        serialNumber = "SN987654321",
                        transports = "Passengers",
                        capacity = "1350 persons/hour",
                        liftHeight = "15 meters",
                        speed = "0.5 m/s",
                        driveType = "Electric",
                        motorCurrent = "15 A",
                        motorPower = "7.5 kW",
                        safetyDevices = "Multiple safety devices installed and functional."
                    ),
                    inspectionAndTesting = EskalatorInspectionAndTesting(
                        frameAndMachineRoom = FrameAndMachineRoom(
                            frame = EskalatorResultStatus("Solid", true),
                            supportBeams = EskalatorResultStatus("In place", true),
                            machineRoomCondition = EskalatorResultStatus("Clean", true),
                            machineRoomClearance = EskalatorResultStatus("Adequate", true),
                            machineRoomLighting = EskalatorResultStatus("Sufficient", true),
                            machineCoverPlate = EskalatorResultStatus("Secure", true),
                            pitCondition = EskalatorResultStatus("Dry", true),
                            pitClearance = EskalatorResultStatus("Sufficient", true),
                            pitStepCoverPlate = EskalatorResultStatus("Present", true)
                        ),
                        driveEquipment = DriveEquipment(
                            driveMachine = EskalatorResultStatus("Lubricated", true),
                            speedUnder30Degrees = EskalatorResultStatus("0.5 m/s", true),
                            speed30to35Degrees = EskalatorResultStatus("0.5 m/s", true),
                            travelatorSpeed = EskalatorResultStatus("N/A", false),
                            stoppingDistance0_5 = EskalatorResultStatus("0.8 m", true),
                            stoppingDistance0_75 = EskalatorResultStatus("1.1 m", true),
                            stoppingDistance0_90 = EskalatorResultStatus("1.3 m", true),
                            driveChain = EskalatorResultStatus("Good condition", true),
                            chainBreakingStrength = EskalatorResultStatus("Verified", true)
                        ),
                        stepsOrPallets = StepsOrPallets(
                            stepMaterial = EskalatorResultStatus("Aluminum", true),
                            stepDimensions = EskalatorResultStatus("Standard", true),
                            palletDimensions = EskalatorResultStatus("Standard", true),
                            stepSurface = EskalatorResultStatus("Non-slip", true),
                            stepLevelness = EskalatorResultStatus("Even", true),
                            skirtBrush = EskalatorResultStatus("Present and effective", true),
                            stepWheels = EskalatorResultStatus("Well-maintained", true)
                        ),
                        landingArea = LandingArea(
                            landingPlates = EskalatorResultStatus("Secure", true),
                            combTeeth = EskalatorResultStatus("Undamaged", true),
                            combCondition = EskalatorResultStatus("Clean", true),
                            landingCover = EskalatorResultStatus("Accessible", true),
                            landingAccessArea = EskalatorResultStatus("Clear", true)
                        ),
                        balustrade = Balustrade(
                            balustradePanel = BalustradePanel(
                                material = EskalatorResultStatus("Tempered Glass", true),
                                height = EskalatorResultStatus("1.1 m", true),
                                sidePressure = EskalatorResultStatus("Withstood", true),
                                verticalPressure = EskalatorResultStatus("Withstood", true)
                            ),
                            skirtPanel = EskalatorResultStatus("No damage", true),
                            skirtPanelFlexibility = EskalatorResultStatus("Adequate", true),
                            stepToSkirtClearance = EskalatorResultStatus("Within limits", true)
                        ),
                        handrail = Handrail(
                            handrailCondition = EskalatorResultStatus("Good", true),
                            handrailSpeedSynchronization = EskalatorResultStatus("Synchronized", true),
                            handrailWidth = EskalatorResultStatus("Standard", true)
                        ),
                        runway = Runway(
                            beamStrengthAndPosition = EskalatorResultStatus("Correct", true),
                            pitWallCondition = EskalatorResultStatus("Good", true),
                            escalatorFrameEnclosure = EskalatorResultStatus("Complete", true),
                            lighting = EskalatorResultStatus("Adequate", true),
                            headroomClearance = EskalatorResultStatus("Sufficient", true),
                            balustradeToObjectClearance = EskalatorResultStatus("Adequate", true),
                            antiClimbDeviceHeight = EskalatorResultStatus("Compliant", true),
                            ornamentPlacement = EskalatorResultStatus("No obstructions", true),
                            outdoorClearance = EskalatorResultStatus("N/A", false)
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
                            stopButtons = EskalatorResultStatus("Clearly marked and functional", true)
                        ),
                        electricalInstallation = ElectricalInstallation(
                            installationStandard = EskalatorResultStatus("IEC Compliant", true),
                            electricalPanel = EskalatorResultStatus("Organized and labeled", true),
                            groundingCable = EskalatorResultStatus("Properly connected", true),
                            fireAlarmConnection = EskalatorResultStatus("Connected", true)
                        ),
                        outdoorSpecifics = OutdoorSpecifics(
                            pitWaterPump = EskalatorResultStatus("Functional", true),
                            weatherproofComponents = EskalatorResultStatus("Present", true)
                        ),
                        userSafetySignage = UserSafetySignage(
                            noBulkyItems = EskalatorResultStatus("Present", true),
                            noJumping = EskalatorResultStatus("Present", true),
                            unattendedChildren = EskalatorResultStatus("Present", true),
                            noTrolleysOrStrollers = EskalatorResultStatus("Present", true),
                            noLeaning = EskalatorResultStatus("Present", true),
                            noSteppingOnSkirt = EskalatorResultStatus("Present", true),
                            softSoleFootwearWarning = EskalatorResultStatus("Present", true),
                            noSittingOnSteps = EskalatorResultStatus("Present", true),
                            holdHandrail = EskalatorResultStatus("Present", true)
                        )
                    ),
                    testingEscalator = "Full operational test completed successfully."
                )
            )
        }
    }
}

@Immutable
data class EskalatorGeneralData(
    val extraId: String = "",
    val moreExtraId: String = "",
    val equipmentType: String = "",
    val examinationType: String = "",
    val conclusion: String = "",
    val companyData: EskalatorCompanyData = EskalatorCompanyData(),
    val technicalData: EskalatorTechnicalData = EskalatorTechnicalData(),
    val inspectionAndTesting: EskalatorInspectionAndTesting = EskalatorInspectionAndTesting(),
    val testingEscalator: String = ""
)

@Immutable
data class EskalatorCompanyData(
    val companyOrBuildingName: String = "",
    val usageAddress: String = "",
    val safetyObjectTypeAndNumber: String = "",
    val intendedUse: String = "",
    val usagePermit: String = "",
    val inspectionDate: String = ""
)

@Immutable
data class EskalatorTechnicalData(
    val manufacturer: String = "",
    val brand: String = "",
    val countryAndYear: String = "",
    val serialNumber: String = "",
    val transports: String = "",
    val capacity: String = "",
    val liftHeight: String = "",
    val speed: String = "",
    val driveType: String = "",
    val motorCurrent: String = "",
    val motorPower: String = "",
    val safetyDevices: String = ""
)

@Immutable
data class EskalatorInspectionAndTesting(
    val frameAndMachineRoom: FrameAndMachineRoom = FrameAndMachineRoom(),
    val driveEquipment: DriveEquipment = DriveEquipment(),
    val stepsOrPallets: StepsOrPallets = StepsOrPallets(),
    val landingArea: LandingArea = LandingArea(),
    val balustrade: Balustrade = Balustrade(),
    val handrail: Handrail = Handrail(),
    val runway: Runway = Runway(),
    val safetyEquipment: SafetyEquipment = SafetyEquipment(),
    val electricalInstallation: ElectricalInstallation = ElectricalInstallation(),
    val outdoorSpecifics: OutdoorSpecifics = OutdoorSpecifics(),
    val userSafetySignage: UserSafetySignage = UserSafetySignage()
)

/**
 * Data class for inspection items related to the frame and machine room.
 */
@Immutable
data class FrameAndMachineRoom(
    val frame: EskalatorResultStatus = EskalatorResultStatus(),
    val supportBeams: EskalatorResultStatus = EskalatorResultStatus(),
    val machineRoomCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val machineRoomClearance: EskalatorResultStatus = EskalatorResultStatus(),
    val machineRoomLighting: EskalatorResultStatus = EskalatorResultStatus(),
    val machineCoverPlate: EskalatorResultStatus = EskalatorResultStatus(),
    val pitCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val pitClearance: EskalatorResultStatus = EskalatorResultStatus(),
    val pitStepCoverPlate: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to drive equipment.
 */
@Immutable
data class DriveEquipment(
    val driveMachine: EskalatorResultStatus = EskalatorResultStatus(),
    val speedUnder30Degrees: EskalatorResultStatus = EskalatorResultStatus(),
    val speed30to35Degrees: EskalatorResultStatus = EskalatorResultStatus(),
    val travelatorSpeed: EskalatorResultStatus = EskalatorResultStatus(),
    val stoppingDistance0_5: EskalatorResultStatus = EskalatorResultStatus(),
    val stoppingDistance0_75: EskalatorResultStatus = EskalatorResultStatus(),
    val stoppingDistance0_90: EskalatorResultStatus = EskalatorResultStatus(),
    val driveChain: EskalatorResultStatus = EskalatorResultStatus(),
    val chainBreakingStrength: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to steps or pallets.
 */
@Immutable
data class StepsOrPallets(
    val stepMaterial: EskalatorResultStatus = EskalatorResultStatus(),
    val stepDimensions: EskalatorResultStatus = EskalatorResultStatus(),
    val palletDimensions: EskalatorResultStatus = EskalatorResultStatus(),
    val stepSurface: EskalatorResultStatus = EskalatorResultStatus(),
    val stepLevelness: EskalatorResultStatus = EskalatorResultStatus(),
    val skirtBrush: EskalatorResultStatus = EskalatorResultStatus(),
    val stepWheels: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to the landing area.
 */
@Immutable
data class LandingArea(
    val landingPlates: EskalatorResultStatus = EskalatorResultStatus(),
    val combTeeth: EskalatorResultStatus = EskalatorResultStatus(),
    val combCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val landingCover: EskalatorResultStatus = EskalatorResultStatus(),
    val landingAccessArea: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to the balustrade.
 */
@Immutable
data class Balustrade(
    val balustradePanel: BalustradePanel = BalustradePanel(),
    val skirtPanel: EskalatorResultStatus = EskalatorResultStatus(),
    val skirtPanelFlexibility: EskalatorResultStatus = EskalatorResultStatus(),
    val stepToSkirtClearance: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Nested data class for balustrade panel details.
 */
data class BalustradePanel(
    val material: EskalatorResultStatus = EskalatorResultStatus(),
    val height: EskalatorResultStatus = EskalatorResultStatus(),
    val sidePressure: EskalatorResultStatus = EskalatorResultStatus(),
    val verticalPressure: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to the handrail.
 */
@Immutable
data class Handrail(
    val handrailCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val handrailSpeedSynchronization: EskalatorResultStatus = EskalatorResultStatus(),
    val handrailWidth: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to the runway.
 */
@Immutable
data class Runway(
    val beamStrengthAndPosition: EskalatorResultStatus = EskalatorResultStatus(),
    val pitWallCondition: EskalatorResultStatus = EskalatorResultStatus(),
    val escalatorFrameEnclosure: EskalatorResultStatus = EskalatorResultStatus(),
    val lighting: EskalatorResultStatus = EskalatorResultStatus(),
    val headroomClearance: EskalatorResultStatus = EskalatorResultStatus(),
    val balustradeToObjectClearance: EskalatorResultStatus = EskalatorResultStatus(),
    val antiClimbDeviceHeight: EskalatorResultStatus = EskalatorResultStatus(),
    val ornamentPlacement: EskalatorResultStatus = EskalatorResultStatus(),
    val outdoorClearance: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to safety equipment.
 */
@Immutable
data class SafetyEquipment(
    val operationControlKey: EskalatorResultStatus = EskalatorResultStatus(),
    val emergencyStopSwitch: EskalatorResultStatus = EskalatorResultStatus(),
    val stepChainSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val driveChainSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val stepSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val handrailSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val reversalStopDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val handrailEntryGuard: EskalatorResultStatus = EskalatorResultStatus(),
    val combPlateSafetyDevice: EskalatorResultStatus = EskalatorResultStatus(),
    val innerDeckingBrush: EskalatorResultStatus = EskalatorResultStatus(),
    val stopButtons: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items related to electrical installation.
 */
@Immutable
data class ElectricalInstallation(
    val installationStandard: EskalatorResultStatus = EskalatorResultStatus(),
    val electricalPanel: EskalatorResultStatus = EskalatorResultStatus(),
    val groundingCable: EskalatorResultStatus = EskalatorResultStatus(),
    val fireAlarmConnection: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for inspection items specific to outdoor installations.
 */
@Immutable
data class OutdoorSpecifics(
    val pitWaterPump: EskalatorResultStatus = EskalatorResultStatus(),
    val weatherproofComponents: EskalatorResultStatus = EskalatorResultStatus()
)

/**
 * Data class for user safety signage inspection items.
 */
@Immutable
data class UserSafetySignage(
    val noBulkyItems: EskalatorResultStatus = EskalatorResultStatus(),
    val noJumping: EskalatorResultStatus = EskalatorResultStatus(),
    val unattendedChildren: EskalatorResultStatus = EskalatorResultStatus(),
    val noTrolleysOrStrollers: EskalatorResultStatus = EskalatorResultStatus(),
    val noLeaning: EskalatorResultStatus = EskalatorResultStatus(),
    val noSteppingOnSkirt: EskalatorResultStatus = EskalatorResultStatus(),
    val softSoleFootwearWarning: EskalatorResultStatus = EskalatorResultStatus(),
    val noSittingOnSteps: EskalatorResultStatus = EskalatorResultStatus(),
    val holdHandrail: EskalatorResultStatus = EskalatorResultStatus()
)

@Immutable
data class EskalatorResultStatus(
    val result: String = "",
    val status: Boolean = false
)