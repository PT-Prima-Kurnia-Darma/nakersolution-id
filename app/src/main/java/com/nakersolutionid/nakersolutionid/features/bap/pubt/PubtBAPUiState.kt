package com.nakersolutionid.nakersolutionid.features.bap.pubt

import androidx.compose.runtime.Immutable

@Immutable
data class PubtBAPUiState(
    val report: PubtBAPReport = PubtBAPReport()
)

@Immutable
data class PubtBAPReport(
    val extraId: String = "",
    val moreExtraId: String = "",
    val examinationType: String = "",
    val inspectionType: String = "",
    val inspectionDate: String = "",
    val generalData: PubtBAPGeneralData = PubtBAPGeneralData(),
    val technicalData: PubtBAPTechnicalData = PubtBAPTechnicalData(),
    val testResults: PubtBAPTestResults = PubtBAPTestResults()
)

@Immutable
data class PubtBAPGeneralData(
    val companyName: String = "",
    val companyLocation: String = "",
    val usageLocation: String = "",
    val addressUsageLocation: String = ""
)

@Immutable
data class PubtBAPTechnicalData(
    val brandOrType: String = "",
    val manufacturer: String = "",
    // FIXED: Combined into a single field to match the DTO and prevent data loss.
    val countryAndYearOfManufacture: String = "",
    val serialNumber: String = "",
    val fuelType: String = "",
    val pressureVesselContent: String = "",
    val designPressureInKgCm2: String = "",
    val maxWorkingPressureInKgCm2: String = "",
    val materialType: String = "",
    val safetyValveType: String = "",
    val volumeInLiters: String = ""
)

@Immutable
data class PubtBAPTestResults(
    val visualInspection: PubtBAPVisualInspection = PubtBAPVisualInspection(),
    val testing: PubtBAPTesting = PubtBAPTesting()
)

@Immutable
data class PubtBAPVisualInspection(
    // FIXED: Typo corrected from 'fondationCondition' to 'foundationCondition'
    val foundationCondition: Boolean = false,
    val safetyValve: PubtBAPSafetyValve = PubtBAPSafetyValve(),
    val apar: PubtBAPApar = PubtBAPApar(),
    val wheelCondition: Boolean = false,
    val pipeCondition: Boolean = false
)

@Immutable
data class PubtBAPSafetyValve(
    val isInstalled: Boolean = false,
    val condition: Boolean = false
)

@Immutable
data class PubtBAPApar(
    val isAvailable: Boolean = false,
    val condition: Boolean = false
)

@Immutable
data class PubtBAPTesting(
    val ndtTestingFulfilled: Boolean = false,
    val thicknessTestingComply: Boolean = false,
    val pneumaticTestingCondition: Boolean = false,
    val hydroTestingFullFilled: Boolean = false,
    val safetyValveTestingCondition: Boolean = false
)