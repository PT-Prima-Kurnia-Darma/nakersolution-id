package com.nakersolutionid.nakersolutionid.features.bap.pubt

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.remote.mapper.PubtMappingKeys
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import com.nakersolutionid.nakersolutionid.utils.Utils

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun PubtBAPReport.toInspectionWithDetailsDomain(currentTime: String, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId,
        moreExtraId = this.moreExtraId,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PUBT,
        subInspectionType = SubInspectionType.General_PUBT,
        equipmentType = this.inspectionType,
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

private fun mapTechnicalDataToDomain(uiState: PubtBAPTechnicalData, inspectionId: Long): List<InspectionTestResultDomain> {
    val cat = PubtMappingKeys.BAP.Category.TECHNICAL_DATA
    return listOf(
        InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.MANUFACTURE_COUNTRY, result = uiState.manufactureCountry, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.FUEL_TYPE, result = uiState.fuelType, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.PRESSURE_VESSEL_CONTENT, result = uiState.pressureVesselContent, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.DESIGN_PRESSURE, result = uiState.designPressureInKgCm2, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.MAX_WORKING_PRESSURE, result = uiState.maxWorkingPressureInKgCm2, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.MATERIAL_TYPE, result = uiState.materialType, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.SAFETY_VALVE_TYPE, result = uiState.safetyValveType, notes = cat),
        InspectionTestResultDomain(inspectionId = inspectionId, testName = PubtMappingKeys.BAP.TestName.VOLUME_LITERS, result = uiState.volumeInLiters, notes = cat)
    )
}

private fun mapVisualInspectionToDomain(uiState: PubtBAPVisualInspection, inspectionId: Long): List<InspectionCheckItemDomain> {
    return mutableListOf<InspectionCheckItemDomain>().apply {
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, itemName = PubtMappingKeys.BAP.ItemName.FOUNDATION_CONDITION, status = uiState.fondationCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, itemName = PubtMappingKeys.BAP.ItemName.WHEEL_CONDITION, status = uiState.wheelCondition))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, itemName = PubtMappingKeys.BAP.ItemName.PIPE_CONDITION, status = uiState.pipeCondition))
        // Safety Valve
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_SAFETY_VALVE, itemName = PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_INSTALLED, status = uiState.safetyValve.isInstalled))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_SAFETY_VALVE, itemName = PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_CONDITION, status = uiState.safetyValve.condition))
        // APAR
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_APAR, itemName = PubtMappingKeys.BAP.ItemName.APAR_AVAILABLE, status = uiState.apar.isAvailable))
        add(InspectionCheckItemDomain(inspectionId = inspectionId, category = PubtMappingKeys.BAP.Category.VISUAL_APAR, itemName = PubtMappingKeys.BAP.ItemName.APAR_CONDITION, status = uiState.apar.condition))
    }
}

private fun mapTestingToDomain(uiState: PubtBAPTesting, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = PubtMappingKeys.BAP.Category.TESTING
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = PubtMappingKeys.BAP.ItemName.NDT_FULFILLED, status = uiState.ndtTestingFulfilled),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = PubtMappingKeys.BAP.ItemName.THICKNESS_COMPLY, status = uiState.thicknessTestingComply),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = PubtMappingKeys.BAP.ItemName.PNEUMATIC_CONDITION, status = uiState.pneumaticTestingCondition),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = PubtMappingKeys.BAP.ItemName.HYDRO_TEST_FULFILLED, status = uiState.hydroTestingFullFilled),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_TEST_CONDITION, status = uiState.safetyValveTestingCondition)
    )
}

// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

fun InspectionWithDetailsDomain.toPubtBAPReport(): PubtBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findTestResult(testName: String): String {
        return this.testResults.find { it.testName == testName }?.result ?: ""
    }

    val generalData = PubtBAPGeneralData(
        companyName = this.inspection.ownerName ?: "",
        companyLocation = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        addressUsageLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = PubtBAPTechnicalData(
        brandOrType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturer = this.inspection.manufacturer?.name ?: "",
        manufactureYear = this.inspection.manufacturer?.year ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        manufactureCountry = findTestResult(PubtMappingKeys.BAP.TestName.MANUFACTURE_COUNTRY),
        fuelType = findTestResult(PubtMappingKeys.BAP.TestName.FUEL_TYPE),
        pressureVesselContent = findTestResult(PubtMappingKeys.BAP.TestName.PRESSURE_VESSEL_CONTENT),
        designPressureInKgCm2 = findTestResult(PubtMappingKeys.BAP.TestName.DESIGN_PRESSURE),
        maxWorkingPressureInKgCm2 = findTestResult(PubtMappingKeys.BAP.TestName.MAX_WORKING_PRESSURE),
        materialType = findTestResult(PubtMappingKeys.BAP.TestName.MATERIAL_TYPE),
        safetyValveType = findTestResult(PubtMappingKeys.BAP.TestName.SAFETY_VALVE_TYPE),
        volumeInLiters = findTestResult(PubtMappingKeys.BAP.TestName.VOLUME_LITERS)
    )

    val visualInspection = PubtBAPVisualInspection(
        fondationCondition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, PubtMappingKeys.BAP.ItemName.FOUNDATION_CONDITION),
        wheelCondition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, PubtMappingKeys.BAP.ItemName.WHEEL_CONDITION),
        pipeCondition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_INSPECTION, PubtMappingKeys.BAP.ItemName.PIPE_CONDITION),
        safetyValve = PubtBAPSafetyValve(
            isInstalled = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_SAFETY_VALVE, PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_INSTALLED),
            condition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_SAFETY_VALVE, PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_CONDITION)
        ),
        apar = PubtBAPApar(
            isAvailable = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_APAR, PubtMappingKeys.BAP.ItemName.APAR_AVAILABLE),
            condition = findBoolItem(PubtMappingKeys.BAP.Category.VISUAL_APAR, PubtMappingKeys.BAP.ItemName.APAR_CONDITION)
        )
    )

    val testing = PubtBAPTesting(
        ndtTestingFulfilled = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.NDT_FULFILLED),
        thicknessTestingComply = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.THICKNESS_COMPLY),
        pneumaticTestingCondition = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.PNEUMATIC_CONDITION),
        hydroTestingFullFilled = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.HYDRO_TEST_FULFILLED),
        safetyValveTestingCondition = findBoolItem(PubtMappingKeys.BAP.Category.TESTING, PubtMappingKeys.BAP.ItemName.SAFETY_VALVE_TEST_CONDITION)
    )

    return PubtBAPReport(
        extraId = this.inspection.extraId,
        moreExtraId = this.inspection.moreExtraId,
        examinationType = this.inspection.examinationType,
        inspectionType = this.inspection.equipmentType,
        inspectionDate = Utils.formatDateToIndonesian(this.inspection.createdAt ?: this.inspection.reportDate ?: ""),
        generalData = generalData,
        technicalData = technicalData,
        testResults = PubtBAPTestResults(visualInspection = visualInspection, testing = testing)
    )
}
