package com.nakersolutionid.nakersolutionid.data.remote.response.elevator

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ElevatorReportsResponse(

	@field:SerializedName("data")
	val data: ListData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class ElevatorReportResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class Frame(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarTopGuardRail(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class OverloadIndicator(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class BufferSafetySwitch(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class LightingBetweenWorkArea(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarAreaExpansion(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class WallHeight(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class LoadCapacity(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Size(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class MachineRoomImplementation(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Belt(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Lighting(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Walls(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class InclinedElevatorStairs(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class GroundingCable(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ElectricalBrake(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ElectricalCutoutSwitch(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class EmergencyDoorNonStop(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarDoor(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ManufacturerName(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class FireAlarmConnection(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class SafetyBrakeSpeed(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class SpecialOperation(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CounterweightMaterial(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class TechnicalDocumentInspection(

	@field:SerializedName("materialCertificate")
	val materialCertificate: Boolean? = null,

	@field:SerializedName("safeWorkProcedure")
	val safeWorkProcedure: Boolean? = null,

	@field:SerializedName("designDrawing")
	val designDrawing: Boolean? = null,

	@field:SerializedName("technicalCalculation")
	val technicalCalculation: Boolean? = null,

	@field:SerializedName("controlPanelDiagram")
	val controlPanelDiagram: Boolean? = null,

	@field:SerializedName("asBuiltDrawing")
	val asBuiltDrawing: Boolean? = null,

	@field:SerializedName("componentCertificates")
	val componentCertificates: Boolean? = null
) : Parcelable

@Parcelize
data class MachineRoomless(

	@field:SerializedName("manualBrakeRelease")
	val manualBrakeRelease: ManualBrakeRelease? = null,

	@field:SerializedName("fireExtinguisherPlacement")
	val fireExtinguisherPlacement: FireExtinguisherPlacement? = null,

	@field:SerializedName("lightingBetweenWorkArea")
	val lightingBetweenWorkArea: LightingBetweenWorkArea? = null,

	@field:SerializedName("panelPlacement")
	val panelPlacement: PanelPlacement? = null,

	@field:SerializedName("lightingWorkArea")
	val lightingWorkArea: LightingWorkArea? = null
) : Parcelable

@Parcelize
data class FireSwitch(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class MachineMounting(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class GuideRailConstruction(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Availability(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class PitAccessSwitch(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarSize(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class EvacuationFloor(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class RotatingPartsGuard(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class MachineRoomDoor(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Function(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarSignage(

	@field:SerializedName("twoWayIntercom")
	val twoWayIntercom: TwoWayIntercom? = null,

	@field:SerializedName("manufacturerName")
	val manufacturerName: ManufacturerName? = null,

	@field:SerializedName("noSmokingSign")
	val noSmokingSign: NoSmokingSign? = null,

	@field:SerializedName("alarmButton")
	val alarmButton: AlarmButton? = null,

	@field:SerializedName("loadCapacity")
	val loadCapacity: LoadCapacity? = null,

	@field:SerializedName("overloadIndicator")
	val overloadIndicator: OverloadIndicator? = null,

	@field:SerializedName("floorButtons")
	val floorButtons: FloorButtons? = null,

	@field:SerializedName("doorOpenCloseButtons")
	val doorOpenCloseButtons: DoorOpenCloseButtons? = null
) : Parcelable

@Parcelize
data class EmergencyDoorSize(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class NoSmokingSign(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CounterweightGuideRailsAndBuffers(

	@field:SerializedName("bufferType")
	val bufferType: BufferType? = null,

	@field:SerializedName("bufferFunction")
	val bufferFunction: BufferFunction? = null,

	@field:SerializedName("counterweightGuardScreen")
	val counterweightGuardScreen: CounterweightGuardScreen? = null,

	@field:SerializedName("counterweightMaterial")
	val counterweightMaterial: CounterweightMaterial? = null,

	@field:SerializedName("bufferSafetySwitch")
	val bufferSafetySwitch: BufferSafetySwitch? = null,

	@field:SerializedName("guideRailConstruction")
	val guideRailConstruction: GuideRailConstruction? = null
) : Parcelable

@Parcelize
data class GuardRailHeightOver850(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class GovernorRopeClamp(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Laporan(

	@field:SerializedName("conclusion")
	val conclusion: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("technicalDocumentInspection")
	val technicalDocumentInspection: TechnicalDocumentInspection? = null,

	@field:SerializedName("documentType")
	val documentType: String? = null,

	@field:SerializedName("generalData")
	val generalData: GeneralData? = null,

	@field:SerializedName("inspectionType")
	val inspectionType: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("subInspectionType")
	val subInspectionType: String? = null,

	@field:SerializedName("examinationType")
	val examinationType: String? = null,

	@field:SerializedName("equipmentType")
	val equipmentType: String? = null,

	@field:SerializedName("inspectionAndTesting")
	val inspectionAndTesting: InspectionAndTesting? = null
) : Parcelable

@Parcelize
data class FloorLeveling(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Body(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class GuardRailHeight300to850(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class SlackRopeDevice(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class AlarmButton(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class GovernorDrumDiameter(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class PitLadder(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class EmergencyDoorBridge(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class HoistwayWallFireResistance(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class PitClearance(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class MachineRoomConstruction(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Ventilation(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DoorOpenCloseButtons(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class EmergencyDoorSafetySwitch(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class RopeHoleGuard(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class MachineRoomAccessLadder(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class PitBelowWorkingArea(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class SuspensionRopesAndBelts(

	@field:SerializedName("ropeWithCounterweight")
	val ropeWithCounterweight: RopeWithCounterweight? = null,

	@field:SerializedName("condition")
	val condition: Condition? = null,

	@field:SerializedName("slackRopeDevice")
	val slackRopeDevice: SlackRopeDevice? = null,

	@field:SerializedName("belt")
	val belt: Belt? = null,

	@field:SerializedName("ropeWithoutCounterweight")
	val ropeWithoutCounterweight: RopeWithoutCounterweight? = null,

	@field:SerializedName("safetyFactor")
	val safetyFactor: SafetyFactor? = null,

	@field:SerializedName("chainUsage")
	val chainUsage: ChainUsage? = null
) : Parcelable

@Parcelize
data class PanelPlacement(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class InclinedElevatorTrackBed(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ManualBrakeRelease(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class FloorArea(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class OverloadDevice(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class LockAndSwitch(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("laporan")
	val laporan: Laporan? = null
) : Parcelable

@Parcelize
data class ListData(

	@field:SerializedName("laporan")
	val laporan: List<Laporan?>
) : Parcelable

@Parcelize
data class ChainUsage(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class InstantaneousSafetyBrake(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class BufferType(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class SafetyBrakeOperation(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Condition(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class MachineRoomAndMachinery(

	@field:SerializedName("mechanicalBrake")
	val mechanicalBrake: MechanicalBrake? = null,

	@field:SerializedName("electricalBrake")
	val electricalBrake: ElectricalBrake? = null,

	@field:SerializedName("machineRoomless")
	val machineRoomless: MachineRoomless? = null,

	@field:SerializedName("fireExtinguisher")
	val fireExtinguisher: FireExtinguisher? = null,

	@field:SerializedName("machineRoomImplementation")
	val machineRoomImplementation: MachineRoomImplementation? = null,

	@field:SerializedName("ventilation")
	val ventilation: Ventilation? = null,

	@field:SerializedName("mainPowerPanelPosition")
	val mainPowerPanelPosition: MainPowerPanelPosition? = null,

	@field:SerializedName("machineMounting")
	val machineMounting: MachineMounting? = null,

	@field:SerializedName("ropeHoleGuard")
	val ropeHoleGuard: RopeHoleGuard? = null,

	@field:SerializedName("machineRoomDoor")
	val machineRoomDoor: MachineRoomDoor? = null,

	@field:SerializedName("emergencyStopSwitch")
	val emergencyStopSwitch: EmergencyStopSwitch? = null,

	@field:SerializedName("machineRoomConstruction")
	val machineRoomConstruction: MachineRoomConstruction? = null,

	@field:SerializedName("machineRoomAccessLadder")
	val machineRoomAccessLadder: MachineRoomAccessLadder? = null,

	@field:SerializedName("rotatingPartsGuard")
	val rotatingPartsGuard: RotatingPartsGuard? = null,

	@field:SerializedName("floorLevelDifference")
	val floorLevelDifference: FloorLevelDifference? = null,

	@field:SerializedName("machineRoomClearance")
	val machineRoomClearance: MachineRoomClearance? = null
) : Parcelable

@Parcelize
data class CarPositionIndicator(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DoorSize(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class BackupPower(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class LimitSwitch(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Label(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class InstallationStandard(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class BufferFunction(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DrumGrooves(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class FireExtinguisherPlacement(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ProgressiveSafetyBrake(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class FloorButtons(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Construction(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class TravelTime(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarSideEmergencyExit(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class HoistwayAndPit(

	@field:SerializedName("emergencyDoorSize")
	val emergencyDoorSize: EmergencyDoorSize? = null,

	@field:SerializedName("pitClearance")
	val pitClearance: PitClearance? = null,

	@field:SerializedName("emergencyDoorNonStop")
	val emergencyDoorNonStop: EmergencyDoorNonStop? = null,

	@field:SerializedName("floorLeveling")
	val floorLeveling: FloorLeveling? = null,

	@field:SerializedName("pitLadder")
	val pitLadder: PitLadder? = null,

	@field:SerializedName("lighting")
	val lighting: Lighting? = null,

	@field:SerializedName("emergencyDoorSafetySwitch")
	val emergencyDoorSafetySwitch: EmergencyDoorSafetySwitch? = null,

	@field:SerializedName("pitScreen")
	val pitScreen: PitScreen? = null,

	@field:SerializedName("cleanliness")
	val cleanliness: Cleanliness? = null,

	@field:SerializedName("pitAccessSwitch")
	val pitAccessSwitch: PitAccessSwitch? = null,

	@field:SerializedName("carTopClearance")
	val carTopClearance: CarTopClearance? = null,

	@field:SerializedName("hoistwaySeparatorBeam")
	val hoistwaySeparatorBeam: HoistwaySeparatorBeam? = null,

	@field:SerializedName("walls")
	val walls: Walls? = null,

	@field:SerializedName("hoistwayDoorLeaf")
	val hoistwayDoorLeaf: HoistwayDoorLeaf? = null,

	@field:SerializedName("inclinedElevatorStairs")
	val inclinedElevatorStairs: InclinedElevatorStairs? = null,

	@field:SerializedName("emergencyDoorBridge")
	val emergencyDoorBridge: EmergencyDoorBridge? = null,

	@field:SerializedName("pitBelowWorkingArea")
	val pitBelowWorkingArea: PitBelowWorkingArea? = null,

	@field:SerializedName("hoistwayDoorInterlock")
	val hoistwayDoorInterlock: HoistwayDoorInterlock? = null,

	@field:SerializedName("construction")
	val construction: Construction? = null,

	@field:SerializedName("inclinedElevatorTrackBed")
	val inclinedElevatorTrackBed: InclinedElevatorTrackBed? = null
) : Parcelable

@Parcelize
data class EmergencyStopSwitch(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class RopeWithoutCounterweight(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class OperatingPanel(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ElectricalFireResistance(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarTopEmergencyExit(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CounterweightGuardScreen(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class SafetyFactor(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class AudioInformation(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarToBeamClearance(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class HoistwayDoorInterlock(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class SafetyBrakeMechanism(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class FloorLevelDifference(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class AlarmBell(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarRoofStrength(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class GovernorAndSafetyBrake(

	@field:SerializedName("electricalCutoutSwitch")
	val electricalCutoutSwitch: ElectricalCutoutSwitch? = null,

	@field:SerializedName("safetyBrakeType")
	val safetyBrakeType: SafetyBrakeType? = null,

	@field:SerializedName("safetyBrakeMechanism")
	val safetyBrakeMechanism: SafetyBrakeMechanism? = null,

	@field:SerializedName("limitSwitch")
	val limitSwitch: LimitSwitch? = null,

	@field:SerializedName("governorSwitch")
	val governorSwitch: GovernorSwitch? = null,

	@field:SerializedName("instantaneousSafetyBrake")
	val instantaneousSafetyBrake: InstantaneousSafetyBrake? = null,

	@field:SerializedName("safetyBrakeOperation")
	val safetyBrakeOperation: SafetyBrakeOperation? = null,

	@field:SerializedName("overloadDevice")
	val overloadDevice: OverloadDevice? = null,

	@field:SerializedName("governorRopeClamp")
	val governorRopeClamp: GovernorRopeClamp? = null,

	@field:SerializedName("safetyBrakeSpeed")
	val safetyBrakeSpeed: SafetyBrakeSpeed? = null,

	@field:SerializedName("progressiveSafetyBrake")
	val progressiveSafetyBrake: ProgressiveSafetyBrake? = null
) : Parcelable

@Parcelize
data class ManualOperationButtons(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ElectricalInstallation(

	@field:SerializedName("accessibilityElevator")
	val accessibilityElevator: AccessibilityElevator? = null,

	@field:SerializedName("fireAlarmConnection")
	val fireAlarmConnection: FireAlarmConnection? = null,

	@field:SerializedName("electricalPanel")
	val electricalPanel: ElectricalPanel? = null,

	@field:SerializedName("groundingCable")
	val groundingCable: GroundingCable? = null,

	@field:SerializedName("fireServiceElevator")
	val fireServiceElevator: FireServiceElevator? = null,

	@field:SerializedName("backupPowerARD")
	val backupPowerARD: BackupPowerARD? = null,

	@field:SerializedName("installationStandard")
	val installationStandard: InstallationStandard? = null,

	@field:SerializedName("seismicSensor")
	val seismicSensor: SeismicSensor? = null
) : Parcelable

@Parcelize
data class SafetyBrakeType(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class GeneralData(

	@field:SerializedName("serialNumber")
	val serialNumber: String? = null,

	@field:SerializedName("addressUsageLocation")
	val addressUsageLocation: String? = null,

	@field:SerializedName("manufacturerOrInstaller")
	val manufacturerOrInstaller: String? = null,

	@field:SerializedName("speed")
	val speed: String? = null,

	@field:SerializedName("capacity")
	val capacity: String? = null,

	@field:SerializedName("nameUsageLocation")
	val nameUsageLocation: String? = null,

	@field:SerializedName("countryAndYear")
	val countryAndYear: String? = null,

	@field:SerializedName("permitNumber")
	val permitNumber: String? = null,

	@field:SerializedName("ownerName")
	val ownerName: String? = null,

	@field:SerializedName("brandOrType")
	val brandOrType: String? = null,

	@field:SerializedName("ownerAddress")
	val ownerAddress: String? = null,

	@field:SerializedName("elevatorType")
	val elevatorType: String? = null,

	@field:SerializedName("floorsServed")
	val floorsServed: String? = null,

	@field:SerializedName("inspectionDate")
	val inspectionDate: String? = null
) : Parcelable

@Parcelize
data class ElectricalPanel(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class PitScreen(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarTopLighting(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class FireExtinguisher(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class BackupPowerARD(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DoorOpenTime(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Cleanliness(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class SillClearance(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class PassengerDrumDiameter(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class EmergencyLighting(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class FireServiceElevator(

	@field:SerializedName("specialOperation")
	val specialOperation: SpecialOperation? = null,

	@field:SerializedName("electricalFireResistance")
	val electricalFireResistance: ElectricalFireResistance? = null,

	@field:SerializedName("travelTime")
	val travelTime: TravelTime? = null,

	@field:SerializedName("hoistwayWallFireResistance")
	val hoistwayWallFireResistance: HoistwayWallFireResistance? = null,

	@field:SerializedName("evacuationFloor")
	val evacuationFloor: EvacuationFloor? = null,

	@field:SerializedName("carSize")
	val carSize: CarSize? = null,

	@field:SerializedName("fireSwitch")
	val fireSwitch: FireSwitch? = null,

	@field:SerializedName("backupPower")
	val backupPower: BackupPower? = null,

	@field:SerializedName("label")
	val label: Label? = null,

	@field:SerializedName("doorSize")
	val doorSize: DoorSize? = null
) : Parcelable

@Parcelize
data class DrumsAndSheaves(

	@field:SerializedName("governorDrumDiameter")
	val governorDrumDiameter: GovernorDrumDiameter? = null,

	@field:SerializedName("drumGrooves")
	val drumGrooves: DrumGrooves? = null,

	@field:SerializedName("passengerDrumDiameter")
	val passengerDrumDiameter: PassengerDrumDiameter? = null
) : Parcelable

@Parcelize
data class Car(

	@field:SerializedName("ventilation")
	val ventilation: Ventilation? = null,

	@field:SerializedName("body")
	val body: Body? = null,

	@field:SerializedName("carDoor")
	val carDoor: CarDoor? = null,

	@field:SerializedName("carTopGuardRail")
	val carTopGuardRail: CarTopGuardRail? = null,

	@field:SerializedName("carToBeamClearance")
	val carToBeamClearance: CarToBeamClearance? = null,

	@field:SerializedName("carDoorSpecs")
	val carDoorSpecs: CarDoorSpecs? = null,

	@field:SerializedName("intercom")
	val intercom: Intercom? = null,

	@field:SerializedName("carPositionIndicator")
	val carPositionIndicator: CarPositionIndicator? = null,

	@field:SerializedName("wallHeight")
	val wallHeight: WallHeight? = null,

	@field:SerializedName("carTopLighting")
	val carTopLighting: CarTopLighting? = null,

	@field:SerializedName("floorArea")
	val floorArea: FloorArea? = null,

	@field:SerializedName("operatingPanel")
	val operatingPanel: OperatingPanel? = null,

	@field:SerializedName("carRoofStrength")
	val carRoofStrength: CarRoofStrength? = null,

	@field:SerializedName("alarmBell")
	val alarmBell: AlarmBell? = null,

	@field:SerializedName("carSignage")
	val carSignage: CarSignage? = null,

	@field:SerializedName("carSideEmergencyExit")
	val carSideEmergencyExit: CarSideEmergencyExit? = null,

	@field:SerializedName("guardRailHeightOver850")
	val guardRailHeightOver850: GuardRailHeightOver850? = null,

	@field:SerializedName("backupPowerARD")
	val backupPowerARD: BackupPowerARD? = null,

	@field:SerializedName("carTopEmergencyExit")
	val carTopEmergencyExit: CarTopEmergencyExit? = null,

	@field:SerializedName("manualOperationButtons")
	val manualOperationButtons: ManualOperationButtons? = null,

	@field:SerializedName("guardRailHeight300to850")
	val guardRailHeight300to850: GuardRailHeight300to850? = null,

	@field:SerializedName("emergencyLighting")
	val emergencyLighting: EmergencyLighting? = null,

	@field:SerializedName("carInterior")
	val carInterior: CarInterior? = null,

	@field:SerializedName("frame")
	val frame: Frame? = null,

	@field:SerializedName("carAreaExpansion")
	val carAreaExpansion: CarAreaExpansion? = null
) : Parcelable

@Parcelize
data class MainPowerPanelPosition(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class GovernorSwitch(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class InspectionAndTesting(

	@field:SerializedName("machineRoomAndMachinery")
	val machineRoomAndMachinery: MachineRoomAndMachinery? = null,

	@field:SerializedName("drumsAndSheaves")
	val drumsAndSheaves: DrumsAndSheaves? = null,

	@field:SerializedName("hoistwayAndPit")
	val hoistwayAndPit: HoistwayAndPit? = null,

	@field:SerializedName("car")
	val car: Car? = null,

	@field:SerializedName("counterweightGuideRailsAndBuffers")
	val counterweightGuideRailsAndBuffers: CounterweightGuideRailsAndBuffers? = null,

	@field:SerializedName("suspensionRopesAndBelts")
	val suspensionRopesAndBelts: SuspensionRopesAndBelts? = null,

	@field:SerializedName("governorAndSafetyBrake")
	val governorAndSafetyBrake: GovernorAndSafetyBrake? = null,

	@field:SerializedName("electricalInstallation")
	val electricalInstallation: ElectricalInstallation? = null
) : Parcelable

@Parcelize
data class LightingWorkArea(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class Intercom(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class AccessibilityElevator(

	@field:SerializedName("doorOpenTime")
	val doorOpenTime: DoorOpenTime? = null,

	@field:SerializedName("operatingPanel")
	val operatingPanel: OperatingPanel? = null,

	@field:SerializedName("panelHeight")
	val panelHeight: PanelHeight? = null,

	@field:SerializedName("audioInformation")
	val audioInformation: AudioInformation? = null,

	@field:SerializedName("label")
	val label: Label? = null,

	@field:SerializedName("doorWidth")
	val doorWidth: DoorWidth? = null
) : Parcelable

@Parcelize
data class DoorWidth(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class MechanicalBrake(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class MachineRoomClearance(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class TwoWayIntercom(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarDoorSpecs(

	@field:SerializedName("sillClearance")
	val sillClearance: SillClearance? = null,

	@field:SerializedName("lockAndSwitch")
	val lockAndSwitch: LockAndSwitch? = null,

	@field:SerializedName("size")
	val size: Size? = null
) : Parcelable

@Parcelize
data class HoistwayDoorLeaf(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarInterior(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class HoistwaySeparatorBeam(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class CarTopClearance(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class SeismicSensor(

	@field:SerializedName("function")
	val function: Function? = null,

	@field:SerializedName("availability")
	val availability: Availability? = null
) : Parcelable

@Parcelize
data class RopeWithCounterweight(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class PanelHeight(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable
