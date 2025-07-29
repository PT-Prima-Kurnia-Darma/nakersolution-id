package com.nakersolutionid.nakersolutionid.data.remote.dto.electrical

import com.google.gson.annotations.SerializedName

data class ElectricalReportRequest(
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("extraid") val extraId: Long,
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("createdaAt") val createdAt: String,
    @SerializedName("generalData") val generalData: ElectricalGeneralData,
    @SerializedName("initialDocumentCheck") val initialDocumentCheck: ElectricalInitialDocumentCheck,
    @SerializedName("technicalData") val technicalData: ElectricalTechnicalData,
    @SerializedName("documentExamination1") val documentExamination1: ElectricalDocumentExamination1,
    @SerializedName("documentExamination2") val documentExamination2: ElectricalDocumentExamination2,
    @SerializedName("testing") val testing: ElectricalTesting,
    @SerializedName("visualInspection") val visualInspection: ElectricalVisualInspection,
    @SerializedName("found") val found: String,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String,
)

// Data DTO for single Electrical Report response
data class ElectricalSingleReportResponseData(
    @SerializedName("laporan") val laporan: ElectricalReportData
)

// Data DTO for list of Electrical Reports response
data class ElectricalListReportResponseData(
    @SerializedName("laporan") val laporan: List<ElectricalReportData>
)

// Main DTO for Electrical Report (used for create, update, and individual get)
data class ElectricalReportData(
    @SerializedName("id") val id: String, // From response JSON
    @SerializedName("examinationType") val examinationType: String,
    @SerializedName("inspectionType") val inspectionType: String,
    @SerializedName("extraid") val extraId: Long,
    @SerializedName("createdaAt") val createdAt: String, // Note: Typo in JSON "createdaAt"
    @SerializedName("equipmentType") val equipmentType: String,
    @SerializedName("generalData") val generalData: ElectricalGeneralData,
    @SerializedName("initialDocumentCheck") val initialDocumentCheck: ElectricalInitialDocumentCheck,
    @SerializedName("technicalData") val technicalData: ElectricalTechnicalData,
    @SerializedName("documentExamination1") val documentExamination1: ElectricalDocumentExamination1,
    @SerializedName("documentExamination2") val documentExamination2: ElectricalDocumentExamination2,
    @SerializedName("testing") val testing: ElectricalTesting,
    @SerializedName("visualInspection") val visualInspection: ElectricalVisualInspection,
    @SerializedName("found") val found: String,
    @SerializedName("conclusion") val conclusion: String,
    @SerializedName("recommendations") val recommendations: String,
    @SerializedName("subInspectionType") val subInspectionType: String, // From response JSON
    @SerializedName("documentType") val documentType: String // From response JSON
)

data class ElectricalGeneralData(
    @SerializedName("companyName") val companyName: String,
    @SerializedName("companyAddress") val companyAddress: String,
    @SerializedName("installationType") val installationType: String,
    @SerializedName("businessField") val businessField: String,
    @SerializedName("safetyServiceProvider") val safetyServiceProvider: String,
    @SerializedName("ohsExpert") val ohsExpert: String,
    @SerializedName("permitNumber") val permitNumber: String,
    @SerializedName("inspectionDate") val inspectionDate: String,
    @SerializedName("inspectionLocation") val inspectionLocation: String
)

data class ElectricalInitialDocumentCheck(
    @SerializedName("sld") val sld: ElectricalDocumentCheckDetail,
    @SerializedName("layout") val layout: ElectricalDocumentCheckDetail,
    @SerializedName("permit") val permit: ElectricalDocumentCheckDetail,
    @SerializedName("technicianLicense") val technicianLicense: ElectricalDocumentCheckDetail
)

data class ElectricalDocumentCheckDetail(
    @SerializedName("isAvailable") val isAvailable: Boolean,
    @SerializedName("result") val result: String
)

data class ElectricalTechnicalData(
    @SerializedName("plnPower") val plnPower: String,
    @SerializedName("generatorPower") val generatorPower: String,
    @SerializedName("totalInstalledLoad") val totalInstalledLoad: String,
    @SerializedName("lightingPower") val lightingPower: String,
    @SerializedName("powerLoad") val powerLoad: String
)

data class ElectricalDocumentExamination1(
    @SerializedName("hasPlanningPermit") val hasPlanningPermit: Boolean,
    @SerializedName("hasLocationMap") val hasLocationMap: Boolean,
    @SerializedName("hasSld") val hasSld: Boolean,
    @SerializedName("hasLayout") val hasLayout: Boolean,
    @SerializedName("hasWiringDiagram") val hasWiringDiagram: Boolean,
    @SerializedName("hasAreaClassification") val hasAreaClassification: Boolean,
    @SerializedName("hasPanelComponentList") val hasPanelComponentList: Boolean,
    @SerializedName("hasShortCircuitCalc") val hasShortCircuitCalc: Boolean,
    @SerializedName("hasManualBook") val hasManualBook: Boolean,
    @SerializedName("hasMaintenanceBook") val hasMaintenanceBook: Boolean,
    @SerializedName("hasWarningSigns") val hasWarningSigns: Boolean,
    @SerializedName("hasManufacturerCert") val hasManufacturerCert: Boolean,
    @SerializedName("hasTechSpec") val hasTechSpec: Boolean,
    @SerializedName("hasTechSpecCert") val hasTechSpecCert: Boolean,
    @SerializedName("hasPowerRecap") val hasPowerRecap: Boolean,
    @SerializedName("hasDailyRecord") val hasDailyRecord: Boolean,
    @SerializedName("hasPanelPointCount") val hasPanelPointCount: Boolean,
    @SerializedName("isPanelCoverGood") val isPanelCoverGood: Boolean,
    @SerializedName("hasOtherData") val hasOtherData: Boolean
)

data class ElectricalDocumentExamination2(
    @SerializedName("isUnitConstructionGood") val isUnitConstructionGood: Boolean,
    @SerializedName("isPlacementGood") val isPlacementGood: Boolean,
    @SerializedName("isNameplateVerified") val isNameplateVerified: Boolean,
    @SerializedName("isAreaClassificationOk") val isAreaClassificationOk: Boolean,
    @SerializedName("isProtectionGood") val isProtectionGood: Boolean,
    @SerializedName("isRadiationShieldingGood") val isRadiationShieldingGood: Boolean,
    @SerializedName("hasPanelDoorHolder") val hasPanelDoorHolder: Boolean,
    @SerializedName("areBoltsAndScrewsSecure") val areBoltsAndScrewsSecure: Boolean,
    @SerializedName("isBusbarIsolated") val isBusbarIsolated: Boolean,
    @SerializedName("isBusbarClearanceGood") val isBusbarClearanceGood: Boolean,
    @SerializedName("isCableInstallationOk") val isCableInstallationOk: Boolean,
    @SerializedName("isDoorCableProtected") val isDoorCableProtected: Boolean,
    @SerializedName("isFuseChangeSafe") val isFuseChangeSafe: Boolean,
    @SerializedName("hasCableTerminalProtection") val hasCableTerminalProtection: Boolean,
    @SerializedName("areInstrumentsMarked") val areInstrumentsMarked: Boolean,
    @SerializedName("areEquipmentsCoded") val areEquipmentsCoded: Boolean,
    @SerializedName("isCableInOutGood") val isCableInOutGood: Boolean,
    @SerializedName("isBusbarSizeGood") val isBusbarSizeGood: Boolean,
    @SerializedName("isBusbarClean") val isBusbarClean: Boolean,
    @SerializedName("isBusbarMarkingOk") val isBusbarMarkingOk: Boolean,
    @SerializedName("isGroundingCableGood") val isGroundingCableGood: Boolean,
    @SerializedName("isPanelDoorGood") val isPanelDoorGood: Boolean,
    @SerializedName("areSparepartsOk") val areSparepartsOk: Boolean,
    @SerializedName("areSafetyFacilitiesGood") val areSafetyFacilitiesGood: Boolean,
    @SerializedName("breakerData") val breakerData: ElectricalBreakerData
)

data class ElectricalBreakerData(
    @SerializedName("isCurrentRatingOk") val isCurrentRatingOk: Boolean,
    @SerializedName("isVoltageRatingOk") val isVoltageRatingOk: Boolean,
    @SerializedName("isBreakingCurrentOk") val isBreakingCurrentOk: Boolean,
    @SerializedName("isControlVoltageOk") val isControlVoltageOk: Boolean,
    @SerializedName("isManufacturerOk") val isManufacturerOk: Boolean,
    @SerializedName("isTypeOk") val isTypeOk: Boolean,
    @SerializedName("isSerialOk") val isSerialOk: Boolean
)

data class ElectricalTesting(
    @SerializedName("isInsulationOk") val isInsulationOk: Boolean,
    @SerializedName("isGroundingOk") val isGroundingOk: Boolean,
    @SerializedName("isOverloadTripOk") val isOverloadTripOk: Boolean,
    @SerializedName("isReversePowerRelayOk") val isReversePowerRelayOk: Boolean,
    @SerializedName("isReverseCurrentRelayOk") val isReverseCurrentRelayOk: Boolean,
    @SerializedName("isBreakerTripOk") val isBreakerTripOk: Boolean,
    @SerializedName("isTemperatureOk") val isTemperatureOk: Boolean,
    @SerializedName("isIndicatorLampOk") val isIndicatorLampOk: Boolean,
    @SerializedName("isMeterDeviationOk") val isMeterDeviationOk: Boolean,
    @SerializedName("isSynchronizationOk") val isSynchronizationOk: Boolean,
    @SerializedName("isConductorAmpacityOk") val isConductorAmpacityOk: Boolean,
    @SerializedName("isProtectionRatingOk") val isProtectionRatingOk: Boolean,
    @SerializedName("isVoltageDropOk") val isVoltageDropOk: Boolean,
    @SerializedName("hasLossConnection") val hasLossConnection: Boolean,
    @SerializedName("breakerEquipment") val breakerEquipment: ElectricalBreakerEquipment
)

data class ElectricalBreakerEquipment(
    @SerializedName("isCtOk") val isCtOk: Boolean,
    @SerializedName("isPtOk") val isPtOk: Boolean,
    @SerializedName("isInstrumentsOk") val isInstrumentsOk: Boolean,
    @SerializedName("isFuseRatingOk") val isFuseRatingOk: Boolean,
    @SerializedName("isMechanicalOk") val isMechanicalOk: Boolean,
    @SerializedName("isTerminalOk") val isTerminalOk: Boolean,
    @SerializedName("isTerminalMarkingOk") val isTerminalMarkingOk: Boolean,
    @SerializedName("isInterlockSystemOk") val isInterlockSystemOk: Boolean,
    @SerializedName("isAuxSwitchOk") val isAuxSwitchOk: Boolean,
    @SerializedName("isMechanicalTripOk") val isMechanicalTripOk: Boolean
)

data class ElectricalVisualInspection(
    @SerializedName("sdpFront") val sdpFront: ElectricalSdpFront,
    @SerializedName("sdpFloors") val sdpFloors: List<ElectricalSdpFloor>,
    @SerializedName("sdpTerminal") val sdpTerminal: ElectricalSdpTerminal,
    @SerializedName("sdpTesting") val sdpTesting: ElectricalSdpTesting
)

data class ElectricalSdpFront(
    @SerializedName("areIndicatorLampsGood") val areIndicatorLampsGood: Boolean,
    @SerializedName("isDoorClearanceGood") val isDoorClearanceGood: Boolean,
    @SerializedName("isLightingGood") val isLightingGood: Boolean,
    @SerializedName("isProductionLightingGood") val isProductionLightingGood: Boolean,
    @SerializedName("isOfficeLightingGood") val isOfficeLightingGood: Boolean,
    @SerializedName("isMainPanelLightingGood") val isMainPanelLightingGood: Boolean,
    @SerializedName("isWarehouseLightingGood") val isWarehouseLightingGood: Boolean,
    @SerializedName("isAreaClearOfUnusedItems") val isAreaClearOfUnusedItems: Boolean,
    @SerializedName("hasVentilationAndSigns") val hasVentilationAndSigns: Boolean
)

data class ElectricalSdpFloor(
    @SerializedName("floorNumber") val floorNumber: String,
    @SerializedName("hasCover") val hasCover: Boolean,
    @SerializedName("hasSld") val hasSld: Boolean,
    @SerializedName("hasBonding") val hasBonding: Boolean,
    @SerializedName("hasLabeling") val hasLabeling: Boolean,
    @SerializedName("isColorCodeOk") val isColorCodeOk: Boolean,
    @SerializedName("isClean") val isClean: Boolean,
    @SerializedName("isNeat") val isNeat: Boolean
)

data class ElectricalSdpTerminal(
    @SerializedName("isBusbarOk") val isBusbarOk: Boolean,
    @SerializedName("isBreakerOk") val isBreakerOk: Boolean,
    @SerializedName("areCableLugsOk") val areCableLugsOk: Boolean,
    @SerializedName("isGroundingSystemOk") val isGroundingSystemOk: Boolean,
    @SerializedName("isBusbarDistanceOk") val isBusbarDistanceOk: Boolean
)

data class ElectricalSdpTesting(
    @SerializedName("isVoltageOk") val isVoltageOk: Boolean,
    @SerializedName("isCurrentOk") val isCurrentOk: Boolean,
    @SerializedName("isMeteringOk") val isMeteringOk: Boolean,
    @SerializedName("hasPanelLabel") val hasPanelLabel: Boolean,
    @SerializedName("hasWarningSign") val hasWarningSign: Boolean,
    @SerializedName("hasSwitchAndLock") val hasSwitchAndLock: Boolean,
    @SerializedName("isTerminalHeatTested") val isTerminalHeatTested: Boolean,
    @SerializedName("isGroundingTested") val isGroundingTested: Boolean,
    @SerializedName("isConductorAmpacityOk") val isConductorAmpacityOk: Boolean,
    @SerializedName("isProtectionRatingOk") val isProtectionRatingOk: Boolean
)