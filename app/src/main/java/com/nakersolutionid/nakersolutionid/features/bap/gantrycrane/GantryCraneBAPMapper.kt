package com.nakersolutionid.nakersolutionid.features.bap.gantrycrane

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
import com.nakersolutionid.nakersolutionid.utils.Utils

/**
 * FIXED: Stores all category names as constants to prevent typos and ensure consistency for BAP.
 * These values MUST match the ones in GantryCraneDtoMapper.kt
 */
private object BAPCategory {
    const val VISUAL_INSPECTION = "PEMERIKSAAN VISUAL"
    const val FUNCTIONAL_TEST = "UJI FUNGSI"
    const val NDT_TEST = "UJI NDT"
    const val LOAD_TEST = "UJI BEBAN"
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

/**
 * Converts [GantryCraneBAPReport] (from the UI) into [InspectionWithDetailsDomain] (for the data layer).
 *
 * @param currentTime The timestamp when the conversion happens, used for 'createdAt'.
 * @return An [InspectionWithDetailsDomain] object populated with data from the BAP report.
 */
fun GantryCraneBAPReport.toInspectionWithDetailsDomain(currentTime: String, isEdited: Boolean, id: Long?): InspectionWithDetailsDomain {
    val inspectionId: Long = id ?: 0

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.extraId,
        moreExtraId = this.moreExtraId,
        documentType = DocumentType.BAP,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Gantry_Crane,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.usageLocation,
        addressUsageLocation = this.generalData.siteLocation,
        driveType = null,
        serialNumber = this.technicalData.serialNumber,
        permitNumber = null,
        capacity = this.technicalData.maxLiftingCapacityInKg,
        speed = this.technicalData.liftingSpeedInMpm,
        floorServed = null,
        manufacturer = ManufacturerDomain(
            name = "${this.technicalData.manufacturerHoist} / ${this.technicalData.manufactureStructure}",
            brandOrType = this.technicalData.brandOrType,
            year = "${this.technicalData.manufactureCountry} - ${this.technicalData.manufactureYear}"
        ),
        createdAt = currentTime,
        reportDate = this.inspectionDate,
        status = null,
        isSynced = false,
        isEdited = isEdited
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    checkItems.addAll(mapVisualCheckToDomain(this.inspectionResult.visualCheck, inspectionId))
    checkItems.addAll(mapFunctionalTestToDomain(this.inspectionResult.functionalTest, inspectionId))
    checkItems.addAll(mapNdtTestToDomain(this.inspectionResult.ndtTest, inspectionId))
    checkItems.addAll(mapLoadTestToDomain(this.inspectionResult.loadTest, inspectionId))

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(),
        testResults = emptyList()
    )
}

private fun mapVisualCheckToDomain(uiState: GantryCraneBAPVisualCheck, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = BAPCategory.VISUAL_INSPECTION // FIXED
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Struktur Utama Baik", status = uiState.isMainStructureGood),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Baut dan Mur Terpasang Kencang", status = uiState.areBoltsAndNutsSecure),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Kondisi Wire Rope Baik", status = uiState.isWireRopeGoodCondition),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Kondisi Hook Baik", status = uiState.isHookGoodCondition),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Kondisi Gearbox Baik", status = uiState.isGearboxGoodCondition),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Terdapat Kebocoran Oli Gearbox", status = uiState.hasGearboxOilLeak),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Kondisi Lampu Peringatan Baik", status = uiState.isWarningLampGoodCondition),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Penandaan Kapasitas Terpasang", status = uiState.isCapacityMarkingDisplayed)
    )
}

private fun mapFunctionalTestToDomain(uiState: GantryCraneBAPFunctionalTest, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = BAPCategory.FUNCTIONAL_TEST // FIXED
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Fungsi Maju Mundur OK", status = uiState.isForwardReverseFunctionOk),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Fungsi Hoisting OK", status = uiState.isHoistingFunctionOk),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Limit Switch Berfungsi", status = uiState.isLimitSwitchFunctional)
    )
}

private fun mapNdtTestToDomain(uiState: GantryCraneBAPNdtTest, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = BAPCategory.NDT_TEST // FIXED
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Metode", status = uiState.method.isNotEmpty(), result = uiState.method),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Hasil Baik", status = uiState.isResultGood)
    )
}

private fun mapLoadTestToDomain(uiState: GantryCraneBAPLoadTest, inspectionId: Long): List<InspectionCheckItemDomain> {
    val cat = BAPCategory.LOAD_TEST // FIXED
    return listOf(
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Beban (kg)", status = uiState.loadInKg.isNotEmpty(), result = uiState.loadInKg),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Tinggi Angkat (meter)", status = uiState.liftHeightInMeters.isNotEmpty(), result = uiState.liftHeightInMeters),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Waktu Tahan (detik)", status = uiState.holdTimeInSeconds.isNotEmpty(), result = uiState.holdTimeInSeconds),
        InspectionCheckItemDomain(inspectionId = inspectionId, category = cat, itemName = "Hasil Baik", status = uiState.isResultGood)
    )
}


// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

/**
 * Converts [InspectionWithDetailsDomain] (from the data layer) into [GantryCraneBAPReport] (for the UI).
 *
 * @return A [GantryCraneBAPReport] object populated with data from the domain model.
 */
fun InspectionWithDetailsDomain.toGantryCraneBAPReport(): GantryCraneBAPReport {
    fun findBoolItem(category: String, itemName: String): Boolean {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findStringItem(category: String, itemName: String): String {
        return this.checkItems.find { it.category == category && it.itemName == itemName }?.result ?: ""
    }

    val generalData = GantryCraneBAPGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        ownerAddress = this.inspection.ownerAddress ?: "",
        usageLocation = this.inspection.usageLocation ?: "",
        siteLocation = this.inspection.addressUsageLocation ?: ""
    )

    val technicalData = GantryCraneBAPTechnicalData(
        brandOrType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturerHoist = this.inspection.manufacturer?.name?.split(" / ")?.getOrNull(0) ?: "",
        manufactureStructure = this.inspection.manufacturer?.name?.split(" / ")?.getOrNull(1) ?: "",
        manufactureYear = this.inspection.manufacturer?.year?.split(" - ")?.getOrNull(1) ?: "",
        manufactureCountry = this.inspection.manufacturer?.year?.split(" - ")?.getOrNull(0) ?: "",
        serialNumber = this.inspection.serialNumber ?: "",
        maxLiftingCapacityInKg = this.inspection.capacity ?: "",
        liftingSpeedInMpm = this.inspection.speed ?: ""
    )

    val visualCheck = GantryCraneBAPVisualCheck(
        isMainStructureGood = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Struktur Utama Baik"),
        areBoltsAndNutsSecure = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Baut dan Mur Terpasang Kencang"),
        isWireRopeGoodCondition = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Kondisi Wire Rope Baik"),
        isHookGoodCondition = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Kondisi Hook Baik"),
        isGearboxGoodCondition = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Kondisi Gearbox Baik"),
        hasGearboxOilLeak = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Terdapat Kebocoran Oli Gearbox"),
        isWarningLampGoodCondition = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Kondisi Lampu Peringatan Baik"),
        isCapacityMarkingDisplayed = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Penandaan Kapasitas Terpasang")
    )

    val functionalTest = GantryCraneBAPFunctionalTest(
        isForwardReverseFunctionOk = findBoolItem(BAPCategory.FUNCTIONAL_TEST, "Fungsi Maju Mundur OK"),
        isHoistingFunctionOk = findBoolItem(BAPCategory.FUNCTIONAL_TEST, "Fungsi Hoisting OK"),
        isLimitSwitchFunctional = findBoolItem(BAPCategory.FUNCTIONAL_TEST, "Limit Switch Berfungsi")
    )

    val ndtTest = GantryCraneBAPNdtTest(
        method = findStringItem(BAPCategory.NDT_TEST, "Metode"),
        isResultGood = findBoolItem(BAPCategory.NDT_TEST, "Hasil Baik")
    )

    val loadTest = GantryCraneBAPLoadTest(
        loadInKg = findStringItem(BAPCategory.LOAD_TEST, "Beban (kg)"),
        liftHeightInMeters = findStringItem(BAPCategory.LOAD_TEST, "Tinggi Angkat (meter)"),
        holdTimeInSeconds = findStringItem(BAPCategory.LOAD_TEST, "Waktu Tahan (detik)"),
        isResultGood = findBoolItem(BAPCategory.LOAD_TEST, "Hasil Baik")
    )

    val inspectionResult = GantryCraneBAPInspectionResult(
        visualCheck = visualCheck,
        functionalTest = functionalTest,
        ndtTest = ndtTest,
        loadTest = loadTest
    )

    return GantryCraneBAPReport(
        extraId = this.inspection.extraId,
        moreExtraId = this.inspection.moreExtraId,
        equipmentType = this.inspection.equipmentType,
        examinationType = this.inspection.examinationType,
        inspectionDate = Utils.formatDateToIndonesian(this.inspection.createdAt ?: this.inspection.reportDate ?: ""),
        generalData = generalData,
        technicalData = technicalData,
        inspectionResult = inspectionResult
    )
}