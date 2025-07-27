package com.nakersolutionid.nakersolutionid.features.bap.ptp

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapFunctionalTests
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.diesel.DieselBapVisualChecks
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapFunctionalTests
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.machine.MachineBapVisualChecks
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import com.nakersolutionid.nakersolutionid.utils.Utils

private object PtpBAPCategory {
    const val TECHNICAL_DATA = "DATA TEKNIK"
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val VISUAL_PPE = "$VISUAL_INSPECTION - Alat Pelindung Diri"
    const val TESTING = "PENGUJIAN"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun PtpBAPReport.toInspectionWithDetailsDomain(currentTime: String, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId,
        moreExtraId = this.moreExtraId,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PTP,
        subInspectionType = if (this.equipmentType.contains("diesel", ignoreCase = true)) SubInspectionType.Motor_Diesel else SubInspectionType.Machine,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.companyName,
        ownerAddress = this.generalData.companyLocation,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.addressUsageLocation,
        serialNumber = this.technicalData.serialNumber,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.brandOrType,
            year = this.technicalData.manufactureYear
        ),
        createdAt = currentTime,
        reportDate = this.inspectionDate,
        isSynced = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.TECHNICAL_DATA, itemName = "Perlengkapan pengaman terpasang", status = this.technicalData.areSafetyFeaturesInstalled))
    checkItems.addAll(mapVisualInspectionToDomain(this.testResults.visualInspection, inspectionId))
    checkItems.addAll(mapTestingToDomain(this.testResults.testing, inspectionId))

    val testResults = mapTechnicalDataToDomain(this.technicalData, inspectionId)

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = testResults
    )
}

private fun mapTechnicalDataToDomain(uiState: PtpBAPTechnicalData, inspectionId: Long): List<InspectionTestResultDomain> {
    val cat = PtpBAPCategory.TECHNICAL_DATA
    return listOf(
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Negara Pembuat", result = uiState.manufactureCountry, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Keterangan Kapasitas", result = uiState.capacityDescription, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Daya Motor Penggerak (kW)", result = uiState.driveMotorPowerKw, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Spesifikasi Khusus", result = uiState.specialSpecification, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Keterangan Dimensi", result = uiState.dimensionsDescription, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Putaran (RPM)", result = uiState.rotationRpm, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Frekuensi (Hz)", result = uiState.frequencyHz, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Arus (Ampere)", result = uiState.currentAmperes, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = "Berat Mesin (Kg)", result = uiState.machineWeightKg, notes = cat)
    )
}

private fun mapVisualInspectionToDomain(uiState: PtpBAPVisualInspection, inspectionId: Long): List<InspectionCheckItemDomain> {
    return mutableListOf<InspectionCheckItemDomain>().apply {
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi mesin baik", status = uiState.isMachineGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Indikator kelistrikan baik", status = uiState.areElectricalIndicatorsGood))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Pembumian terpasang", status = uiState.isGroundingInstalled))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi baterai baik", status = uiState.isBatteryGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Ada kebocoran pelumasan", status = uiState.hasLubricationLeak))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Kondisi pondasi baik", status = uiState.isFoundationGoodCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_INSPECTION, itemName = "Ada kebocoran hidrolik", status = uiState.hasHydraulicLeak))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_PPE, itemName = "APAR tersedia", status = uiState.personalProtectiveEquipment.isAparAvailable))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PtpBAPCategory.VISUAL_PPE, itemName = "APD tersedia", status = uiState.personalProtectiveEquipment.isPpeAvailable))
    }
}

private fun mapTestingToDomain(uiState: PtpBAPTesting, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = PtpBAPCategory.TESTING
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Penerangan memenuhi syarat", status = uiState.isLightingCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tingkat kebisingan memenuhi syarat", status = uiState.isNoiseLevelCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tombol darurat berfungsi", status = uiState.isEmergencyStopFunctional),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Mesin berfungsi baik", status = uiState.isMachineFunctional),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tingkat getaran memenuhi syarat", status = uiState.isVibrationLevelCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tahanan isolasi baik", status = uiState.isInsulationResistanceOk),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Putaran poros memenuhi syarat", status = uiState.isShaftRotationCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tahanan pembumian memenuhi syarat", status = uiState.isGroundingResistanceCompliant),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Hasil NDT Las baik", status = uiState.isNdtWeldTestOk),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tegangan antar fasa normal", status = uiState.isVoltageBetweenPhasesNormal),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Beban antar fasa seimbang", status = uiState.isPhaseLoadBalanced)
    )
}

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toPtpBAPReport(): PtpBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = PtpBAPGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = PtpBAPTechnicalData(
        brandOrType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturer = this.inspection.manufacturer?.name ?: "",
        manufactureYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        manufactureCountry = findTestResult("Negara Pembuat"),
        capacityDescription = findTestResult("Keterangan Kapasitas"),
        driveMotorPowerKw = findTestResult("Daya Motor Penggerak (kW)"),
        specialSpecification = findTestResult("Spesifikasi Khusus"),
        dimensionsDescription = findTestResult("Keterangan Dimensi"),
        rotationRpm = findTestResult("Putaran (RPM)"),
        frequencyHz = findTestResult("Frekuensi (Hz)"),
        currentAmperes = findTestResult("Arus (Ampere)"),
        machineWeightKg = findTestResult("Berat Mesin (Kg)"),
        areSafetyFeaturesInstalled = findBoolItem(PtpBAPCategory.TECHNICAL_DATA, "Perlengkapan pengaman terpasang")
    )

    val visualInspection = PtpBAPVisualInspection(
        isMachineGoodCondition = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Kondisi mesin baik"),
        areElectricalIndicatorsGood = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Indikator kelistrikan baik"),
        isGroundingInstalled = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Pembumian terpasang"),
        isBatteryGoodCondition = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Kondisi baterai baik"),
        hasLubricationLeak = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Ada kebocoran pelumasan"),
        isFoundationGoodCondition = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Kondisi pondasi baik"),
        hasHydraulicLeak = findBoolItem(PtpBAPCategory.VISUAL_INSPECTION, "Ada kebocoran hidrolik"),
        personalProtectiveEquipment = PtpBAPPersonalProtectiveEquipment(
            isAparAvailable = findBoolItem(PtpBAPCategory.VISUAL_PPE, "APAR tersedia"),
            isPpeAvailable = findBoolItem(PtpBAPCategory.VISUAL_PPE, "APD tersedia")
        )
    )

    val testing = PtpBAPTesting(
        isLightingCompliant = findBoolItem(PtpBAPCategory.TESTING, "Penerangan memenuhi syarat"),
        isNoiseLevelCompliant = findBoolItem(PtpBAPCategory.TESTING, "Tingkat kebisingan memenuhi syarat"),
        isEmergencyStopFunctional = findBoolItem(PtpBAPCategory.TESTING, "Tombol darurat berfungsi"),
        isMachineFunctional = findBoolItem(PtpBAPCategory.TESTING, "Mesin berfungsi baik"),
        isVibrationLevelCompliant = findBoolItem(PtpBAPCategory.TESTING, "Tingkat getaran memenuhi syarat"),
        isInsulationResistanceOk = findBoolItem(PtpBAPCategory.TESTING, "Tahanan isolasi baik"),
        isShaftRotationCompliant = findBoolItem(PtpBAPCategory.TESTING, "Putaran poros memenuhi syarat"),
        isGroundingResistanceCompliant = findBoolItem(PtpBAPCategory.TESTING, "Tahanan pembumian memenuhi syarat"),
        isNdtWeldTestOk = findBoolItem(PtpBAPCategory.TESTING, "Hasil NDT Las baik"),
        isVoltageBetweenPhasesNormal = findBoolItem(PtpBAPCategory.TESTING, "Tegangan antar fasa normal"),
        isPhaseLoadBalanced = findBoolItem(PtpBAPCategory.TESTING, "Beban antar fasa seimbang")
    )

    return PtpBAPReport(
        equipmentType = this.inspection.equipmentType,
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        generalData = generalData,
        technicalData = technicalData,
        testResults = PtpBAPTestResults(visualInspection = visualInspection, testing = testing)
    )
}

// =================================================================================================
//                                  NEW: Centralized BAP Logic
// =================================================================================================

/**
 * NEW: The single source of truth for creating a BAP from any Laporan type (Machine or Diesel).
 * This function ensures consistency between UI and DTO.
 *
 * @return A [PtpBAPReport] object containing the summarized BAP data.
 */
private fun InspectionWithDetailsDomain.toPtpBAPReportFromLaporan(): PtpBAPReport {
    val inspection = this.inspection
    val checkItemsByCategory = this.checkItems.groupBy { it.category }

    fun getCheckItemValue(category: String, itemName: String, defaultValue: String = ""): String {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.result ?: defaultValue
    }

    fun getCheckItemStatus(category: String, itemName: String, defaultValue: Boolean = false): Boolean {
        return checkItemsByCategory[category]?.find { it.itemName == itemName }?.status ?: defaultValue
    }

    fun areAnySafetyFeaturesInstalled(vararg itemNames: String): Boolean {
        return itemNames.any { getCheckItemStatus("visual_inspection", it) }
    }

    // --- General Data Mapping ---
    val generalData = PtpBAPGeneralData(
        companyName = inspection.ownerName ?: "",
        companyLocation = inspection.ownerAddress ?: "",
        usageLocation = inspection.usageLocation ?: "",
        addressUsageLocation = inspection.addressUsageLocation ?: ""
    )

    // --- Technical Data Mapping ---
    val technicalData = PtpBAPTechnicalData(
        brandOrType = inspection.manufacturer?.brandOrType ?: "",
        manufacturer = inspection.manufacturer?.name ?: "",
        manufactureYear = inspection.manufacturer?.year ?: "",
        serialNumber = inspection.serialNumber ?: "",
        capacityDescription = inspection.capacity ?: "",
        driveMotorPowerKw = when (inspection.subInspectionType) {
            SubInspectionType.Machine -> getCheckItemValue("general_data", "motorPower")
            SubInspectionType.Motor_Diesel -> getCheckItemValue("technical_data", "motor_powerRpm")
            else -> ""
        },
        dimensionsDescription = if (inspection.subInspectionType == SubInspectionType.Machine) {
            getCheckItemValue("technical_data", "machineDim_overall")
        } else "",
        rotationRpm = inspection.speed ?: getCheckItemValue("technical_data", "gen_rpm"),
        frequencyHz = getCheckItemValue("testing", "elec_power_freq"),
        currentAmperes = getCheckItemValue("testing", "elec_power_ampR"), // Simplified
        machineWeightKg = if (inspection.subInspectionType == SubInspectionType.Machine) {
            getCheckItemValue("technical_data", "machineDim_weightKg")
        } else "",
        areSafetyFeaturesInstalled = areAnySafetyFeaturesInstalled("safetyEmergencyStop", "safetyGovernor", "safetyGuard")
    )

    // --- Test Results: Visual Inspection ---
    val visualInspection = PtpBAPVisualInspection(
        isMachineGoodCondition = getCheckItemStatus("visual_inspection", "mainFrameCondition") || getCheckItemStatus("visual_inspection", "baseConstructionFoundation"),
        areElectricalIndicatorsGood = getCheckItemStatus("visual_inspection", "displayCondition") || getCheckItemStatus("visual_inspection", "generatorVoltMeter"),
        isGroundingInstalled = getCheckItemStatus("visual_inspection", "safetyGrounding"),
        isFoundationGoodCondition = getCheckItemStatus("visual_inspection", "foundationCondition") || getCheckItemStatus("visual_inspection", "baseConstructionFoundation"),
        isBatteryGoodCondition = if (inspection.subInspectionType == SubInspectionType.Motor_Diesel) getCheckItemStatus("visual_inspection", "startingSystemBatteryPoles") else false,
        hasLubricationLeak = if (inspection.subInspectionType == SubInspectionType.Motor_Diesel) !getCheckItemStatus("visual_inspection", "lubeSystemPacking", true) else false,
        hasHydraulicLeak = if (inspection.subInspectionType == SubInspectionType.Machine) !getCheckItemStatus("visual_inspection", "hydraulicHoseCondition", true) else false,
        personalProtectiveEquipment = PtpBAPPersonalProtectiveEquipment(isAparAvailable = false, isPpeAvailable = false) // Not available from Laporan
    )

    // --- Test Results: Functional Testing ---
    val testing = PtpBAPTesting(
        isLightingCompliant = (getCheckItemValue("testing", "ndt_lighting").takeIf { it.isNotEmpty() }?.split("#")?.firstOrNull() ?: getCheckItemValue("lighting_measurement", "pointA_result")).toDoubleOrNull() ?: 0.0 > 0.0,
        isNoiseLevelCompliant = (getCheckItemValue("testing", "ndt_noise").takeIf { it.isNotEmpty() }?.split("#")?.firstOrNull() ?: getCheckItemValue("noise_measurement", "pointA_result")).toDoubleOrNull() ?: 100.0 < 85.0,
        isEmergencyStopFunctional = getCheckItemStatus("testing", "func_safetyEmergencyStop") || getCheckItemValue("testing", "safety_emergencyStop").contains("Berfungsi", true),
        isMachineFunctional = getCheckItemStatus("testing", "func_functionTest") || getCheckItemValue("testing", "ndt_loadTest").contains("Berfungsi", true),
        isVibrationLevelCompliant = getCheckItemStatus("testing", "func_vibrationTest") || getCheckItemStatus("visual_inspection", "mainPartsVibrationDamper"),
        isInsulationResistanceOk = getCheckItemStatus("visual_inspection", "electricalInsulation"),
        isShaftRotationCompliant = when (inspection.subInspectionType) {
            SubInspectionType.Machine -> getCheckItemStatus("testing", "func_speedTest")
            SubInspectionType.Motor_Diesel -> getCheckItemValue("testing", "ndt_shaftRpm").isNotBlank()
            else -> false
        },
        isGroundingResistanceCompliant = getCheckItemStatus("testing", "func_safetyGrounding") || getCheckItemValue("testing", "safety_grounding").isNotBlank(),
        isNdtWeldTestOk = getCheckItemStatus("testing", "func_weldJointTest") || getCheckItemValue("testing", "ndt_weldJoint").contains("Baik", true),
        isVoltageBetweenPhasesNormal = getCheckItemValue("testing", "elec_panel_voltRS").isNotEmpty(),
        isPhaseLoadBalanced = {
            val r = getCheckItemValue("testing", "elec_power_ampR").toFloatOrNull()
            val s = getCheckItemValue("testing", "elec_power_ampS").toFloatOrNull()
            val t = getCheckItemValue("testing", "elec_power_ampT").toFloatOrNull()
            if (r != null && s != null && t != null && r >= 0f && s >= 0f && t >= 0f) {
                if (r == s && s == t) true
                else {
                    val avg = (r + s + t) / 3
                    if (avg == 0f) false
                    else {
                        val maxDev = 0.10f
                        (kotlin.math.abs(r - avg) / avg < maxDev) &&
                                (kotlin.math.abs(s - avg) / avg < maxDev) &&
                                (kotlin.math.abs(t - avg) / avg < maxDev)
                    }
                }
            } else false
        }()
    )

    return PtpBAPReport(
        extraId = inspection.extraId,
        moreExtraId = inspection.moreExtraId,
        equipmentType = inspection.equipmentType,
        examinationType = inspection.examinationType,
        inspectionDate = Utils.formatDateToIndonesian(inspection.createdAt ?: inspection.reportDate ?: ""),
        generalData = generalData,
        technicalData = technicalData,
        testResults = PtpBAPTestResults(visualInspection = visualInspection, testing = testing)
    )
}

/**
 * REFACTORED: Now acts as a simple bridge to the centralized BAP generation logic.
 */
fun InspectionWithDetailsDomain.toPtpBAPCreationReport(): PtpBAPUiState {
    return PtpBAPUiState(report = this.toPtpBAPReportFromLaporan())
}

/**
 * NEW: Converts the canonical PtpBAPReport UI model to the DTO request for MACHINE.
 */
fun PtpBAPReport.toBapRequest(laporanId: String, createdAt: String, extraId: Long): MachineBapRequest {
    return MachineBapRequest(
        laporanId = laporanId,
        examinationType = this.examinationType,
        inspectionDate = this.inspectionDate,
        createdAt = createdAt,
        extraId = extraId,
        generalData = MachineBapGeneralData(this.generalData.companyName, this.generalData.companyLocation, this.generalData.usageLocation, this.generalData.addressUsageLocation),
        technicalData = MachineBapTechnicalData(this.technicalData.brandOrType, this.technicalData.manufacturer, this.technicalData.manufactureYear, this.technicalData.serialNumber, this.technicalData.capacityDescription, this.technicalData.driveMotorPowerKw, this.technicalData.specialSpecification, this.technicalData.dimensionsDescription, this.technicalData.rotationRpm, this.technicalData.frequencyHz, this.technicalData.currentAmperes, this.technicalData.machineWeightKg, this.technicalData.areSafetyFeaturesInstalled),
        visualChecks = MachineBapVisualChecks(this.testResults.visualInspection.isMachineGoodCondition, this.testResults.visualInspection.areElectricalIndicatorsGood, this.testResults.visualInspection.personalProtectiveEquipment.isAparAvailable, this.testResults.visualInspection.personalProtectiveEquipment.isPpeAvailable, this.testResults.visualInspection.isGroundingInstalled, this.testResults.visualInspection.isBatteryGoodCondition, this.testResults.visualInspection.hasLubricationLeak, this.testResults.visualInspection.isFoundationGoodCondition, this.testResults.visualInspection.hasHydraulicLeak),
        functionalTests = MachineBapFunctionalTests(this.testResults.testing.isLightingCompliant, this.testResults.testing.isNoiseLevelCompliant, this.testResults.testing.isEmergencyStopFunctional, this.testResults.testing.isMachineFunctional, this.testResults.testing.isVibrationLevelCompliant, this.testResults.testing.isInsulationResistanceOk, this.testResults.testing.isShaftRotationCompliant, this.testResults.testing.isGroundingResistanceCompliant, this.testResults.testing.isNdtWeldTestOk, this.testResults.testing.isVoltageBetweenPhasesNormal, this.testResults.testing.isPhaseLoadBalanced)
    )
}

/**
 * NEW: Converts the canonical PtpBAPReport UI model to the DTO request for DIESEL.
 */
fun PtpBAPReport.toDieselBapRequest(laporanId: String, createdAt: String, extraId: Long): DieselBapRequest {
    return DieselBapRequest(
        laporanId = laporanId,
        examinationType = this.examinationType,
        inspectionDate = this.inspectionDate,
        createdAt = createdAt,
        extraId = extraId,
        generalData = DieselBapGeneralData(this.generalData.companyName, this.generalData.companyLocation, this.generalData.usageLocation, this.generalData.addressUsageLocation),
        technicalData = DieselBapTechnicalData(this.technicalData.brandOrType, this.technicalData.manufacturer, this.technicalData.manufactureYear, this.technicalData.serialNumber, this.technicalData.capacityDescription, this.technicalData.driveMotorPowerKw, this.technicalData.specialSpecification, this.technicalData.dimensionsDescription, this.technicalData.rotationRpm, this.technicalData.frequencyHz, this.technicalData.currentAmperes, this.technicalData.machineWeightKg, this.technicalData.areSafetyFeaturesInstalled),
        visualChecks = DieselBapVisualChecks(this.testResults.visualInspection.isMachineGoodCondition, this.testResults.visualInspection.areElectricalIndicatorsGood, this.testResults.visualInspection.personalProtectiveEquipment.isAparAvailable, this.testResults.visualInspection.personalProtectiveEquipment.isPpeAvailable, this.testResults.visualInspection.isGroundingInstalled, this.testResults.visualInspection.isBatteryGoodCondition, this.testResults.visualInspection.hasLubricationLeak, this.testResults.visualInspection.isFoundationGoodCondition, this.testResults.visualInspection.hasHydraulicLeak),
        functionalTests = DieselBapFunctionalTests(this.testResults.testing.isLightingCompliant, this.testResults.testing.isNoiseLevelCompliant, this.testResults.testing.isEmergencyStopFunctional, this.testResults.testing.isMachineFunctional, this.testResults.testing.isVibrationLevelCompliant, this.testResults.testing.isInsulationResistanceOk, this.testResults.testing.isShaftRotationCompliant, this.testResults.testing.isGroundingResistanceCompliant, this.testResults.testing.isNdtWeldTestOk, this.testResults.testing.isVoltageBetweenPhasesNormal, this.testResults.testing.isPhaseLoadBalanced)
    )
}