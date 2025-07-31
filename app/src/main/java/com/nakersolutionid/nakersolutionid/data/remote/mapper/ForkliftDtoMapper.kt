package com.nakersolutionid.nakersolutionid.data.remote.mapper

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.toDisplayString
import com.nakersolutionid.nakersolutionid.data.remote.dto.common.ResultStatus
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftAttachments
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapFunctionalTest
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapInspectionResult
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftBapVisualCheck
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftDashboard
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftEngineOnChecks
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftGeneralData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftHydraulicComponents
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftInspectionAndTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftLiftingChainInspection
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftLoadTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftMainFrameAndChassis
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftNonDestructiveTesting
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftNonDestructiveTestingResult
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftPersonalBasketAndHandrail
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftPowerTrain
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftPrimeMover
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftReportData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftReportRequest
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftTechnicalData
import com.nakersolutionid.nakersolutionid.data.remote.dto.forklift.ForkliftTestingForklift
import com.nakersolutionid.nakersolutionid.domain.model.FindingType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain

/**
 * Stores all category names as constants to prevent typos and ensure consistency for Forklift reports.
 * These categories should match the ones used in ForkliftMapper.kt.
 */
private object ForkliftCategory {
    const val VISUAL_CHASSIS = "Visual - Kerangka Utama / Chasis"
    const val VISUAL_OTHER_EQUIPMENT = "Visual - Perlengkapan Lain"
    const val VISUAL_PRIME_MOVER = "Visual - Penggerak Utama"
    const val VISUAL_DASHBOARD = "Visual - DashBoard"
    const val VISUAL_POWERTRAIN = "Visual - Power Train"
    const val VISUAL_ATTACHMENT = "Visual - Attachment / Perlengkapan"
    const val VISUAL_PERSONAL_BASKET = "Visual - Personal Basket"
    const val VISUAL_HYDRAULIC = "Visual - Komponen Hidraulik"
    const val ENGINE_ON_INSPECTION = "Pemeriksaan Dengan Mesin Hidup"
    const val NDE_FORK = "Pengujian Tidak Merusak (Fork)"
    const val LIFTING_CHAIN_INSPECTION = "Pemeriksaan Rantai Pengangkat"
    const val LOAD_TESTING = "Uji Beban"
}

// =================================================================================================
//                                  Domain Model -> DTO (Request)
// =================================================================================================

/**
 * Converts an [InspectionWithDetailsDomain] object to a [ForkliftReportRequest] DTO.
 * This is used when sending forklift inspection report data to the API.
 *
 * @return A [ForkliftReportRequest] DTO populated with data from the domain model.
 */
fun InspectionWithDetailsDomain.toForkliftReportRequest(): ForkliftReportRequest {
    val checkItems = this.checkItems
    val testResults = this.testResults

    // Helper to find a check item's result and status by category and name.
    fun findCheckItemStatus(category: String, itemName: String): ResultStatus {
        val item = checkItems.find { it.category == category && it.itemName == itemName }
        return ResultStatus(result = item?.result ?: "", status = item?.status ?: false)
    }

    // Helper to find a test result by name.
    fun findTestResult(testName: String): String {
        return testResults.find { it.testName.equals(testName, ignoreCase = true) }?.result ?: ""
    }

    // Helper to find a test result's notes by name and parse a specific key from it.
    fun findTestResultNote(testName: String, noteKey: String): String {
        val notes = testResults.find { it.testName.equals(testName, ignoreCase = true) }?.notes
        return notes?.split('|')?.find { it.startsWith("$noteKey:") }?.removePrefix("$noteKey:") ?: ""
    }

    // General Data
    val generalData = ForkliftGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        ownerAddress = this.inspection.ownerAddress ?: "",
        userInCharge = testResults.find { it.testName == "Pemakai" }?.result ?: "",
        subcontractorPersonInCharge = testResults.find { it.testName == "Penanggung Jawab" }?.result ?: "",
        unitLocation = this.inspection.usageLocation ?: "",
        operatorName = testResults.find { it.testName == "Nama Operator" }?.result ?: "",
        equipmentType = this.inspection.equipmentType,
        manufacturer = this.inspection.manufacturer?.name ?: "",
        brandType = this.inspection.manufacturer?.brandOrType ?: "",
        locationAndYearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = this.inspection.serialNumber ?: "",
        capacityWorkingLoad = this.inspection.capacity ?: "",
        intendedUse = testResults.find { it.testName == "Digunakan untuk" }?.result ?: "",
        certificateNumber = this.inspection.permitNumber ?: "",
        equipmentHistory = testResults.find { it.testName == "Data Riwayat Pesawat" }?.result ?: ""
    )

    // Technical Data
    val technicalData = ForkliftTechnicalData(
        specificationSerialNumber = findTestResult("Spesifikasi - No. Seri"),
        specificationCapacity = findTestResult("Spesifikasi - Kapasitas"),
        specificationAttachment = findTestResult("Spesifikasi - Attachment"),
        specificationForkDimensions = findTestResult("Spesifikasi - Dimensi Garpu"),
        specificationSpeedLifting = findTestResult("Kecepatan - Angkat"),
        specificationSpeedLowering = findTestResult("Kecepatan - Turun"),
        specificationSpeedTravelling = findTestResult("Kecepatan - Jalan"),
        primeMoverBrandType = findTestResult("Prime Mover - Merk/Tipe"),
        primeMoverSerialNumber = findTestResult("Prime Mover - No. Seri"),
        primeMoverYearOfManufacture = findTestResult("Prime Mover - Tahun"),
        primeMoverRevolution = findTestResult("Prime Mover - Putaran"),
        primeMoverPower = findTestResult("Prime Mover - Daya"),
        primeMoverNumberOfCylinders = findTestResult("Prime Mover - Jml Silinder"),
        dimensionLength = findTestResult("Dimensi - Panjang"),
        dimensionWidth = findTestResult("Dimensi - Lebar"),
        dimensionHeight = findTestResult("Dimensi - Tinggi"),
        dimensionForkLiftingHeight = findTestResult("Dimensi - Tinggi Angkat Garpu"),
        tirePressureDriveWheel = findTestResult("Tekanan Roda - Penggerak"),
        tirePressureSteeringWheel = findTestResult("Tekanan Roda - Kemudi"),
        driveWheelSize = findTestResult("Roda Penggerak - Ukuran"),
        driveWheelType = findTestResult("Roda Penggerak - Tipe"),
        steeringWheelSize = findTestResult("Roda Kemudi - Ukuran"),
        steeringWheelType = findTestResult("Roda Kemudi - Tipe"),
        travellingBrakeSize = findTestResult("Rem Jalan - Ukuran"),
        travellingBrakeType = findTestResult("Rem Jalan - Tipe"),
        hydraulicPumpPressure = findTestResult("Pompa Hidraulik - Tekanan"),
        hydraulicPumpType = findTestResult("Pompa Hidraulik - Tipe"),
        hydraulicPumpReliefValve = findTestResult("Pompa Hidraulik - Relief Valve")
    )

    // Inspection and Testing (Visual Checks)
    val inspectionAndTesting = ForkliftInspectionAndTesting(
        mainFrameAndChassis = ForkliftMainFrameAndChassis(
            reinforcingFrameCorrosionResult = findCheckItemStatus(ForkliftCategory.VISUAL_CHASSIS, "Rangka Penguat - Korosi"),
            reinforcingFrameCracksResult = findCheckItemStatus(ForkliftCategory.VISUAL_CHASSIS, "Rangka Penguat - Keretakan"),
            reinforcingFrameDeformationResult = findCheckItemStatus(ForkliftCategory.VISUAL_CHASSIS, "Rangka Penguat - Perubahan Bentuk"),
            counterweightCorrosionResult = findCheckItemStatus(ForkliftCategory.VISUAL_CHASSIS, "Pemberat (C/W) - Korosi"),
            counterweightConditionResult = findCheckItemStatus(ForkliftCategory.VISUAL_CHASSIS, "Pemberat (C/W) - Kondisi"),
            otherEquipmentFloorDeckResult = findCheckItemStatus(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Lantai / Dek"),
            otherEquipmentStairsStepsResult = findCheckItemStatus(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Tangga / pijakan"),
            otherEquipmentFasteningBoltsResult = findCheckItemStatus(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Baut-baut Pengikat"),
            otherEquipmentOperatorSeatResult = findCheckItemStatus(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Dudukan Operator (Jok)")
        ),
        primeMover = ForkliftPrimeMover(
            systemCoolingResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Pendingin"),
            systemLubricationResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Pelumas"),
            systemFuelResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Bahan Bakar"),
            systemAirIntakeResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Pemasukan Udara"),
            systemExhaustGasResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Gas Buang"),
            systemStarterResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Starter"),
            electricalBatteryResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Accu/Battery"),
            electricalStartingDynamoResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Dinamo Starting"),
            electricalAlternatorResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Alternator"),
            electricalBatteryCableResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Kabel Accu"),
            electricalInstallationCableResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Kabel Instalasi"),
            electricalLightingLampsResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Lampu Penerangan"),
            electricalSafetyLampsResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Lampu Pengaman/Sign"),
            electricalHornResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Klakson"),
            electricalFuseResult = findCheckItemStatus(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Pengaman Lebur/Sekring")
        ),
        dashboard = ForkliftDashboard(
            tempIndicatorResult = findCheckItemStatus(ForkliftCategory.VISUAL_DASHBOARD, "Indikator Suhu"),
            oilPressureResult = findCheckItemStatus(ForkliftCategory.VISUAL_DASHBOARD, "Tekanan Oli Mesin"),
            hydraulicPressureResult = findCheckItemStatus(ForkliftCategory.VISUAL_DASHBOARD, "Tekanan Hidraulik"),
            hourMeterResult = findCheckItemStatus(ForkliftCategory.VISUAL_DASHBOARD, "Hour Meter"),
            glowPlugResult = findCheckItemStatus(ForkliftCategory.VISUAL_DASHBOARD, "Pemanas awal / Glow Plug"),
            fuelIndicatorResult = findCheckItemStatus(ForkliftCategory.VISUAL_DASHBOARD, "Indikator Bahan Bakar"),
            loadIndicatorResult = findCheckItemStatus(ForkliftCategory.VISUAL_DASHBOARD, "Indikator Beban"),
            loadChartResult = findCheckItemStatus(ForkliftCategory.VISUAL_DASHBOARD, "Load Chart / Name Plate")
        ),
        powerTrain = ForkliftPowerTrain(
            starterDynamoResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Dinamo starter"),
            steeringWheelResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Kemudi Roda"),
            steeringRodResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Batang Kemudi"),
            steeringGearBoxResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Kotak Gigi/Gear Box"),
            steeringPitmanResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Pengubah Gerak/Pitman"),
            steeringDragLinkResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Batang Tarik/Drag Link"),
            steeringTieRodResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Tire Rod"),
            steeringLubeResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Pelumasan"),
            wheelsFrontResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Front (Penggerak)"),
            wheelsRearResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Rear (Kemudi)"),
            wheelsBoltsResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Baut Pengikat"),
            wheelsDrumResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Tromol/Hub"),
            wheelsLubeResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Pelumasan"),
            wheelsMechanicalResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Perlengkapan Mekanis"),
            clutchHousingResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Rumah Kopling"),
            clutchConditionResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Kondisi Kopling"),
            clutchTransOilResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Pelumas/Oli Transmisi"),
            clutchTransLeakResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Kebocoran Transmisi"),
            clutchShaftResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Poros Penghubung"),
            clutchMechanicalResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Perlengkapan Mekanis"),
            diffHousingResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Rumah Gardan"),
            diffConditionResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Kondisi Gardan"),
            diffOilResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Oli Gardan"),
            diffLeakResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Kebocoran Gardan"),
            diffShaftResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Poros Penghubung"),
            brakesMainResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kondisi Rem Utama"),
            brakesHandResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kondisi Rem Tangan"),
            brakesEmergencyResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kondisi Rem Darurat"),
            brakesLeakResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kebocoran"),
            brakesMechanicalResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Komponen Mekanis"),
            transHousingResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Rumah Transmisi"),
            transOilResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Oli Transmisi"),
            transLeakResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Kebocoran Transmisi"),
            transMechanicalResult = findCheckItemStatus(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Perlengkapan Mekanis")
        ),
        attachments = ForkliftAttachments(
            mastWearResult = findCheckItemStatus(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Keausan"),
            mastCracksResult = findCheckItemStatus(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Keretakan"),
            mastDeformationResult = findCheckItemStatus(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Perubahan Bentuk"),
            mastLubeResult = findCheckItemStatus(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Pelumasan"),
            mastShaftBearingResult = findCheckItemStatus(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Poros dan Bantalan"),
            liftChainConditionResult = findCheckItemStatus(ForkliftCategory.VISUAL_ATTACHMENT, "Rantai Pengangkat - Kondisi"),
            liftChainDeformationResult = findCheckItemStatus(ForkliftCategory.VISUAL_ATTACHMENT, "Rantai Pengangkat - Perubahan Bentuk"),
            liftChainLubeResult = findCheckItemStatus(ForkliftCategory.VISUAL_ATTACHMENT, "Rantai Pengangkat - Pelumasan")
        ),
        personalBasketAndHandrail = ForkliftPersonalBasketAndHandrail(
            basketFloorCorrosionResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Korosi"),
            basketFloorCracksResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Keretakan"),
            basketFloorDeformationResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Perubahan Bentuk"),
            basketFloorFasteningResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Pengikat"),
            basketFrameCorrosionResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Korosi"),
            basketFrameCracksResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Keretakan"),
            basketFrameDeformationResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Perubahan Bentuk"),
            basketFrameCrossBracingResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Penguat Melintang"),
            basketFrameDiagonalBracingResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Penguat Diagonal"),
            basketBoltsCorrosionResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Korosi"),
            basketBoltsCracksResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Keretakan"),
            basketBoltsDeformationResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Perubahan Bentuk"),
            basketBoltsFasteningResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Pengikat"),
            basketDoorCorrosionResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Korosi"),
            basketDoorCracksResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Keretakan"),
            basketDoorDeformationResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Perubahan Bentuk"),
            basketDoorFasteningResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Pengikat"),
            handrailCracksResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Keretakan"),
            handrailWearResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Keausan"),
            handrailCracks2Result = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Kelurusan Rel"), // This seems to be a duplicate name, mapping to "Kelurusan Rel"
            handrailRailStraightnessResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Kelurusan Rel"),
            handrailRailJointsResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Sambungan Rel"),
            handrailInterRailStraightnessResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Kelurusan Antar Rel"),
            handrailRailJointGapResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Jarak Antar Sambungan Rel"),
            handrailRailFastenersResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Pengikat Rel"),
            handrailRailStopperResult = findCheckItemStatus(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Rel Stopper")
        ),
        hydraulicComponents = ForkliftHydraulicComponents(
            tankLeakageResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kebocoran"),
            tankOilLevelResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Level Oli"),
            tankOilConditionResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kondisi Oli"),
            tankSuctionLineResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kondisi Saluran Isap"),
            tankReturnLineResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kondisi Saluran Balik"),
            pumpLeakageResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kebocoran"),
            pumpSuctionLineResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kondisi Saluran Isap"),
            pumpPressureLineResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kondisi Saluran Tekan"),
            pumpFunctionResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Fungsi"),
            pumpNoiseResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kelainan Suara"),
            valveLeakageResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Kebocoran"),
            valveLineConditionResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Kondisi Saluran"),
            valveReliefFunctionResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Relief Valve"),
            valveNoiseResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Kelainan Suara"),
            valveLiftCylinderResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Silinder Angkat"),
            valveTiltCylinderResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Silinder Ungkit"),
            valveSteeringCylinderResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Silinder Kemudi"),
            actuatorLeakageResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Aktuator - Kebocoran"),
            actuatorLineConditionResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Aktuator - Kondisi Saluran"),
            actuatorNoiseResult = findCheckItemStatus(ForkliftCategory.VISUAL_HYDRAULIC, "Aktuator - Kelainan Suara")
        ),
        engineOnChecks = ForkliftEngineOnChecks(
            starterDynamoResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Dinamo starter"),
            instrumentResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja instrumen/indikator"),
            electricalResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja perlengkapan listrik"),
            leakageEngineOilResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Oli mesin"),
            leakageFuelResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Bahan bakar"),
            leakageCoolantResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Air pendingin"),
            leakageHydraulicOilResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Oli hidraulik"),
            leakageTransmissionOilResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Oli transmisi"),
            leakageFinalDriveOilResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Oli final drive"),
            leakageBrakeFluidResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Minyak rem"),
            clutchResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja kopling"),
            transmissionResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja persneling (maju mundur)"),
            brakeResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja rem tangan dan kaki"),
            hornAlarmResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja klakson signal alarm"),
            lampsResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja lampu-lampu"),
            hydraulicMotorResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Motor Hidraulik/sistem Hidraulik"),
            steeringCylinderResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja silinder stir/power stering"),
            liftingCylinderResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja silinder pengangkat"),
            tiltingCylinderResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja silinder ungkit"),
            exhaustGasResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kondisi gas buang"),
            controlLeversResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja semua tuas-tuas kontrol"),
            noiseEngineResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Mesin"),
            noiseTurbochargerResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Turbocharger"),
            noiseTransmissionResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Transmisi"),
            noiseHydraulicPumpResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Pompa Hidraulik"),
            noiseProtectiveCoverResult = findCheckItemStatus(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Tutup pelindung")
        )
    )

    // Testing Forklift
    val liftingChainInspectionList = testResults.filter { it.testName.startsWith("Pemeriksaan Rantai #") }.map {
        ForkliftLiftingChainInspection(
            inspectedPart = it.result ?: "",
            constructionType = parseNote(it.notes, "Jenis"),
            standardPitch = parseNote(it.notes, "StdPitch"),
            measuredPitch = parseNote(it.notes, "MeasurePitch"),
            standardPin = parseNote(it.notes, "StdPin"),
            measuredPin = parseNote(it.notes, "MeasurePin"),
            result = parseNote(it.notes, "Hasil")
        )
    }

    val ndtResults = checkItems.filter { it.category == ForkliftCategory.NDE_FORK }.map {
        val parts = it.itemName.split(" - ", limit = 2)
        ForkliftNonDestructiveTestingResult(
            inspectedPart = parts.getOrNull(0) ?: "",
            location = parts.getOrNull(1) ?: "",
            defectFound = if (!it.status) it.result ?: "" else "", // If status is false (defect found), use result
            defectNotFound = if (it.status) it.result ?: "" else "", // If status is true (defect not found), use result
            result = it.result ?: ""
        )
    }
    val nonDestructiveTesting = ForkliftNonDestructiveTesting(
        ndtType = findTestResult("Jenis NDT"),
        results = ndtResults
    )

    val loadTestingList = testResults.filter { it.testName.startsWith("Uji Beban #") }.map {
        ForkliftLoadTesting(
            liftingHeight = parseNote(it.notes, "Tinggi Angkat"),
            testLoad = it.result ?: "",
            speed = parseNote(it.notes, "Kecepatan"),
            movement = parseNote(it.notes, "Gerakan"),
            remarks = parseNote(it.notes, "Ket"),
            result = parseNote(it.notes, "Hasil")
        )
    }

    val testingForklift = ForkliftTestingForklift(
        liftingChainInspection = liftingChainInspectionList,
        nonDestructiveTesting = nonDestructiveTesting,
        loadTesting = loadTestingList
    )

    // Conclusion and Recommendation
    val conclusion = this.findings
        .filter { it.type == FindingType.RECOMMENDATION }
        .joinToString("\n") { it.description }

    val recommendation = this.findings
        .filter { it.type == FindingType.FINDING }
        .joinToString("\n") { it.description }

    return ForkliftReportRequest(
        inspectionType = this.inspection.inspectionType.toDisplayString(),
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        extraId = this.inspection.id,
        createdAt = this.inspection.createdAt ?: "",
        generalData = generalData,
        technicalData = technicalData,
        inspectionAndTesting = inspectionAndTesting,
        testingForklift = testingForklift,
        conclusion = conclusion,
        recommendation = recommendation
    )
}

/**
 * Converts an [InspectionWithDetailsDomain] object to a [ForkliftBapRequest] DTO.
 * This is used when sending forklift BAP data to the API.
 *
 * @return A [ForkliftBapRequest] DTO populated with data from the domain model.
 */
fun InspectionWithDetailsDomain.toForkliftBapRequest(): ForkliftBapRequest {
    val checkItems = this.checkItems

    fun findBoolItem(category: String, itemName: String): Boolean {
        return checkItems.find { it.category == category && it.itemName == itemName }?.status ?: false
    }

    fun findStringItem(category: String, itemName: String): String {
        return checkItems.find { it.category == category && it.itemName == itemName }?.result ?: ""
    }

    val generalData = ForkliftBapGeneralData(
        ownerName = this.inspection.ownerName ?: "",
        ownerAddress = this.inspection.ownerAddress ?: "",
        userInCharge = this.inspection.usageLocation ?: ""
    )

    val technicalData = ForkliftBapTechnicalData(
        brandType = this.inspection.manufacturer?.brandOrType ?: "",
        manufacturer = this.inspection.manufacturer?.name ?: "",
        locationAndYearOfManufacture = this.inspection.manufacturer?.year ?: "",
        serialNumberUnitNumber = this.inspection.serialNumber ?: "",
        capacityWorkingLoad = this.inspection.capacity ?: "",
        liftingHeightMeters = this.inspection.floorServed ?: ""
    )

    val visualCheck = ForkliftBapVisualCheck(
        hasForkDefects = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Fork Mengalami Cacat"),
        isNameplateAttached = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Name Plate Terpasang"),
        isAparAvailable = findBoolItem(BAPCategory.VISUAL_INSPECTION, "APAR Tersedia"),
        isCapacityMarkingDisplayed = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Penandaan Kapasitas Terpasang"),
        hasHydraulicLeak = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Terdapat Kebocoran Hidrolik"),
        isChainGoodCondition = findBoolItem(BAPCategory.VISUAL_INSPECTION, "Kondisi Rantai Baik")
    )

    val functionalTest = ForkliftBapFunctionalTest(
        loadKg = findStringItem(BAPCategory.TESTING, "Beban Uji (kg)"),
        liftHeightMeters = findStringItem(BAPCategory.TESTING, "Tinggi Angkat Uji (meter)"),
        isAbleToLiftAndHold = findBoolItem(BAPCategory.TESTING, "Mampu Mengangkat dan Menahan"),
        isFunctioningWell = findBoolItem(BAPCategory.TESTING, "Berfungsi Dengan Baik"),
        hasCrackIndication = findBoolItem(BAPCategory.TESTING, "Ada Indikasi Retak"),
        isEmergencyStopFunctional = findBoolItem(BAPCategory.TESTING, "Stop Darurat Berfungsi"),
        isWarningLampHornFunctional = findBoolItem(BAPCategory.TESTING, "Lampu Peringatan dan Klakson Berfungsi")
    )

    val inspectionResult = ForkliftBapInspectionResult(
        visualCheck = visualCheck,
        functionalTest = functionalTest
    )

    return ForkliftBapRequest(
        laporanId = this.inspection.extraId,
        examinationType = this.inspection.examinationType,
        inspectionDate = this.inspection.reportDate ?: "",
        createdAt = this.inspection.createdAt ?: "",
        extraId = this.inspection.id,
        generalData = generalData,
        technicalData = technicalData,
        inspectionResult = inspectionResult,
        equipmentType = this.inspection.equipmentType,
        inspectionType = this.inspection.inspectionType.toDisplayString()
    )
}

// Helper function to parse notes string
private fun parseNote(notes: String?, key: String): String {
    return notes?.split('|')?.find { it.startsWith("$key:") }?.removePrefix("$key:") ?: ""
}

// =================================================================================================
//                                  DTO -> Domain Model (Response)
// =================================================================================================

/**
 * Converts a [ForkliftReportData] DTO to an [InspectionWithDetailsDomain] object.
 * This is used when receiving forklift inspection report data from the API.
 *
 * @return An [InspectionWithDetailsDomain] object populated with data from the DTO.
 */
fun ForkliftReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId // Assuming extraId from DTO maps to local inspection ID

    val inspectionDomain = InspectionDomain(
        id = inspectionId,
        extraId = this.id, // ID from cloud response
        moreExtraId = "", // Not available in report response
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Forklift,
        equipmentType = this.generalData.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.unitLocation,
        addressUsageLocation = this.generalData.ownerAddress, // Assuming this maps to ownerAddress
        driveType = null, // Not directly available in ForkliftReportData, might need to infer or add
        serialNumber = this.generalData.serialNumberUnitNumber,
        permitNumber = this.generalData.certificateNumber,
        capacity = this.generalData.capacityWorkingLoad,
        speed = null, // Not directly available
        floorServed = null, // Not directly available
        manufacturer = ManufacturerDomain(
            name = this.generalData.manufacturer,
            brandOrType = this.generalData.brandType,
            year = this.generalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        status = this.conclusion, // Conclusion can be used as status
        isSynced = true, // Data from network is considered synced
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()
    val testResults = mutableListOf<InspectionTestResultDomain>()
    val findings = mutableListOf<InspectionFindingDomain>()

    // Helper to add check items from ResultStatus
    fun MutableList<InspectionCheckItemDomain>.addCheckItem(category: String, itemName: String, data: ResultStatus) {
        this.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = data.status, result = data.result))
    }

    // Map General Data to Test Results
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Pemakai", this.generalData.userInCharge, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Penanggung Jawab", this.generalData.subcontractorPersonInCharge, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Nama Operator", this.generalData.operatorName, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Digunakan untuk", this.generalData.intendedUse, null))
    testResults.add(InspectionTestResultDomain(0, inspectionId, "Data Riwayat Pesawat", this.generalData.equipmentHistory, null))

    // Map Technical Data to Test Results
    this.technicalData.let { techData ->
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Spesifikasi - No. Seri", techData.specificationSerialNumber, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Spesifikasi - Kapasitas", techData.specificationCapacity, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Spesifikasi - Attachment", techData.specificationAttachment, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Spesifikasi - Dimensi Garpu", techData.specificationForkDimensions, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Kecepatan - Angkat", techData.specificationSpeedLifting, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Kecepatan - Turun", techData.specificationSpeedLowering, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Kecepatan - Jalan", techData.specificationSpeedTravelling, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Prime Mover - Merk/Tipe", techData.primeMoverBrandType, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Prime Mover - No. Seri", techData.primeMoverSerialNumber, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Prime Mover - Tahun", techData.primeMoverYearOfManufacture, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Prime Mover - Putaran", techData.primeMoverRevolution, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Prime Mover - Daya", techData.primeMoverPower, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Prime Mover - Jml Silinder", techData.primeMoverNumberOfCylinders, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Dimensi - Panjang", techData.dimensionLength, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Dimensi - Lebar", techData.dimensionWidth, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Dimensi - Tinggi", techData.dimensionHeight, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Dimensi - Tinggi Angkat Garpu", techData.dimensionForkLiftingHeight, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Tekanan Roda - Penggerak", techData.tirePressureDriveWheel, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Tekanan Roda - Kemudi", techData.tirePressureSteeringWheel, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Roda Penggerak - Ukuran", techData.driveWheelSize, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Roda Penggerak - Tipe", techData.driveWheelType, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Roda Kemudi - Ukuran", techData.steeringWheelSize, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Roda Kemudi - Tipe", techData.steeringWheelType, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Rem Jalan - Ukuran", techData.travellingBrakeSize, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Rem Jalan - Tipe", techData.travellingBrakeType, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Pompa Hidraulik - Tekanan", techData.hydraulicPumpPressure, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Pompa Hidraulik - Tipe", techData.hydraulicPumpType, null))
        testResults.add(InspectionTestResultDomain(0, inspectionId, "Pompa Hidraulik - Relief Valve", techData.hydraulicPumpReliefValve, null))
    }

    // Map InspectionAndTesting (Visual Checks) to Check Items
    this.inspectionAndTesting.let { inspectionAndTesting ->
        inspectionAndTesting.mainFrameAndChassis.let { data ->
            val cat = ForkliftCategory.VISUAL_CHASSIS
            checkItems.addCheckItem(cat, "Rangka Penguat - Korosi", data.reinforcingFrameCorrosionResult)
            checkItems.addCheckItem(cat, "Rangka Penguat - Keretakan", data.reinforcingFrameCracksResult)
            checkItems.addCheckItem(cat, "Rangka Penguat - Perubahan Bentuk", data.reinforcingFrameDeformationResult)
            checkItems.addCheckItem(cat, "Pemberat (C/W) - Korosi", data.counterweightCorrosionResult)
            checkItems.addCheckItem(cat, "Pemberat (C/W) - Kondisi", data.counterweightConditionResult)
            checkItems.addCheckItem(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Lantai / Dek", data.otherEquipmentFloorDeckResult)
            checkItems.addCheckItem(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Tangga / pijakan", data.otherEquipmentStairsStepsResult)
            checkItems.addCheckItem(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Baut-baut Pengikat", data.otherEquipmentFasteningBoltsResult)
            checkItems.addCheckItem(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Dudukan Operator (Jok)", data.otherEquipmentOperatorSeatResult)
        }
        inspectionAndTesting.primeMover.let { data ->
            val cat = ForkliftCategory.VISUAL_PRIME_MOVER
            checkItems.addCheckItem(cat, "Sistem Pendingin", data.systemCoolingResult)
            checkItems.addCheckItem(cat, "Sistem Pelumas", data.systemLubricationResult)
            checkItems.addCheckItem(cat, "Sistem Bahan Bakar", data.systemFuelResult)
            checkItems.addCheckItem(cat, "Sistem Pemasukan Udara", data.systemAirIntakeResult)
            checkItems.addCheckItem(cat, "Sistem Gas Buang", data.systemExhaustGasResult)
            checkItems.addCheckItem(cat, "Sistem Starter", data.systemStarterResult)
            checkItems.addCheckItem(cat, "Kelistrikan - Accu/Battery", data.electricalBatteryResult)
            checkItems.addCheckItem(cat, "Kelistrikan - Dinamo Starting", data.electricalStartingDynamoResult)
            checkItems.addCheckItem(cat, "Kelistrikan - Alternator", data.electricalAlternatorResult)
            checkItems.addCheckItem(cat, "Kelistrikan - Kabel Accu", data.electricalBatteryCableResult)
            checkItems.addCheckItem(cat, "Kelistrikan - Kabel Instalasi", data.electricalInstallationCableResult)
            checkItems.addCheckItem(cat, "Kelistrikan - Lampu Penerangan", data.electricalLightingLampsResult)
            checkItems.addCheckItem(cat, "Kelistrikan - Lampu Pengaman/Sign", data.electricalSafetyLampsResult)
            checkItems.addCheckItem(cat, "Kelistrikan - Klakson", data.electricalHornResult)
            checkItems.addCheckItem(cat, "Kelistrikan - Pengaman Lebur/Sekring", data.electricalFuseResult)
        }
        inspectionAndTesting.dashboard.let { data ->
            val cat = ForkliftCategory.VISUAL_DASHBOARD
            checkItems.addCheckItem(cat, "Indikator Suhu", data.tempIndicatorResult)
            checkItems.addCheckItem(cat, "Tekanan Oli Mesin", data.oilPressureResult)
            checkItems.addCheckItem(cat, "Tekanan Hidraulik", data.hydraulicPressureResult)
            checkItems.addCheckItem(cat, "Hour Meter", data.hourMeterResult)
            checkItems.addCheckItem(cat, "Pemanas awal / Glow Plug", data.glowPlugResult)
            checkItems.addCheckItem(cat, "Indikator Bahan Bakar", data.fuelIndicatorResult)
            checkItems.addCheckItem(cat, "Indikator Beban", data.loadIndicatorResult)
            checkItems.addCheckItem(cat, "Load Chart / Name Plate", data.loadChartResult)
        }
        inspectionAndTesting.powerTrain.let { data ->
            val cat = ForkliftCategory.VISUAL_POWERTRAIN
            checkItems.addCheckItem(cat, "Dinamo starter", data.starterDynamoResult)
            checkItems.addCheckItem(cat, "Kemudi - Kemudi Roda", data.steeringWheelResult)
            checkItems.addCheckItem(cat, "Kemudi - Batang Kemudi", data.steeringRodResult)
            checkItems.addCheckItem(cat, "Kemudi - Kotak Gigi/Gear Box", data.steeringGearBoxResult)
            checkItems.addCheckItem(cat, "Kemudi - Pengubah Gerak/Pitman", data.steeringPitmanResult)
            checkItems.addCheckItem(cat, "Kemudi - Batang Tarik/Drag Link", data.steeringDragLinkResult)
            checkItems.addCheckItem(cat, "Kemudi - Tire Rod", data.steeringTieRodResult)
            checkItems.addCheckItem(cat, "Kemudi - Pelumasan", data.steeringLubeResult)
            checkItems.addCheckItem(cat, "Roda - Front (Penggerak)", data.wheelsFrontResult)
            checkItems.addCheckItem(cat, "Roda - Rear (Kemudi)", data.wheelsRearResult)
            checkItems.addCheckItem(cat, "Roda - Baut Pengikat", data.wheelsBoltsResult)
            checkItems.addCheckItem(cat, "Roda - Tromol/Hub", data.wheelsDrumResult)
            checkItems.addCheckItem(cat, "Roda - Pelumasan", data.wheelsLubeResult)
            checkItems.addCheckItem(cat, "Roda - Perlengkapan Mekanis", data.wheelsMechanicalResult)
            checkItems.addCheckItem(cat, "Kopling - Rumah Kopling", data.clutchHousingResult)
            checkItems.addCheckItem(cat, "Kopling - Kondisi Kopling", data.clutchConditionResult)
            checkItems.addCheckItem(cat, "Kopling - Pelumas/Oli Transmisi", data.clutchTransOilResult)
            checkItems.addCheckItem(cat, "Kopling - Kebocoran Transmisi", data.clutchTransLeakResult)
            checkItems.addCheckItem(cat, "Kopling - Poros Penghubung", data.clutchShaftResult)
            checkItems.addCheckItem(cat, "Kopling - Perlengkapan Mekanis", data.clutchMechanicalResult)
            checkItems.addCheckItem(cat, "Gardan - Rumah Gardan", data.diffHousingResult)
            checkItems.addCheckItem(cat, "Gardan - Kondisi Gardan", data.diffConditionResult)
            checkItems.addCheckItem(cat, "Gardan - Oli Gardan", data.diffOilResult)
            checkItems.addCheckItem(cat, "Gardan - Kebocoran Gardan", data.diffLeakResult)
            checkItems.addCheckItem(cat, "Gardan - Poros Penghubung", data.diffShaftResult)
            checkItems.addCheckItem(cat, "Rem - Kondisi Rem Utama", data.brakesMainResult)
            checkItems.addCheckItem(cat, "Rem - Kondisi Rem Tangan", data.brakesHandResult)
            checkItems.addCheckItem(cat, "Rem - Kondisi Rem Darurat", data.brakesEmergencyResult)
            checkItems.addCheckItem(cat, "Rem - Kebocoran", data.brakesLeakResult)
            checkItems.addCheckItem(cat, "Rem - Komponen Mekanis", data.brakesMechanicalResult)
            checkItems.addCheckItem(cat, "Transmisi - Rumah Transmisi", data.transHousingResult)
            checkItems.addCheckItem(cat, "Transmisi - Oli Transmisi", data.transOilResult)
            checkItems.addCheckItem(cat, "Transmisi - Kebocoran Transmisi", data.transLeakResult)
            checkItems.addCheckItem(cat, "Transmisi - Perlengkapan Mekanis", data.transMechanicalResult)
        }
        inspectionAndTesting.attachments.let { data ->
            val cat = ForkliftCategory.VISUAL_ATTACHMENT
            checkItems.addCheckItem(cat, "Tiang Penyangga - Keausan", data.mastWearResult)
            checkItems.addCheckItem(cat, "Tiang Penyangga - Keretakan", data.mastCracksResult)
            checkItems.addCheckItem(cat, "Tiang Penyangga - Perubahan Bentuk", data.mastDeformationResult)
            checkItems.addCheckItem(cat, "Tiang Penyangga - Pelumasan", data.mastLubeResult)
            checkItems.addCheckItem(cat, "Tiang Penyangga - Poros dan Bantalan", data.mastShaftBearingResult)
            checkItems.addCheckItem(cat, "Rantai Pengangkat - Kondisi", data.liftChainConditionResult)
            checkItems.addCheckItem(cat, "Rantai Pengangkat - Perubahan Bentuk", data.liftChainDeformationResult)
            checkItems.addCheckItem(cat, "Rantai Pengangkat - Pelumasan", data.liftChainLubeResult)
        }
        inspectionAndTesting.personalBasketAndHandrail.let { data ->
            val cat = ForkliftCategory.VISUAL_PERSONAL_BASKET
            checkItems.addCheckItem(cat, "Lantai Kerja - Korosi", data.basketFloorCorrosionResult)
            checkItems.addCheckItem(cat, "Lantai Kerja - Keretakan", data.basketFloorCracksResult)
            checkItems.addCheckItem(cat, "Lantai Kerja - Perubahan Bentuk", data.basketFloorDeformationResult)
            checkItems.addCheckItem(cat, "Lantai Kerja - Pengikat", data.basketFloorFasteningResult)
            checkItems.addCheckItem(cat, "Rangka - Korosi", data.basketFrameCorrosionResult)
            checkItems.addCheckItem(cat, "Rangka - Keretakan", data.basketFrameCracksResult)
            checkItems.addCheckItem(cat, "Rangka - Perubahan Bentuk", data.basketFrameDeformationResult)
            checkItems.addCheckItem(cat, "Rangka - Penguat Melintang", data.basketFrameCrossBracingResult)
            checkItems.addCheckItem(cat, "Rangka - Penguat Diagonal", data.basketFrameDiagonalBracingResult)
            checkItems.addCheckItem(cat, "Baut Pengikat - Korosi", data.basketBoltsCorrosionResult)
            checkItems.addCheckItem(cat, "Baut Pengikat - Keretakan", data.basketBoltsCracksResult)
            checkItems.addCheckItem(cat, "Baut Pengikat - Perubahan Bentuk", data.basketBoltsDeformationResult)
            checkItems.addCheckItem(cat, "Baut Pengikat - Pengikat", data.basketBoltsFasteningResult)
            checkItems.addCheckItem(cat, "Pintu - Korosi", data.basketDoorCorrosionResult)
            checkItems.addCheckItem(cat, "Pintu - Keretakan", data.basketDoorCracksResult)
            checkItems.addCheckItem(cat, "Pintu - Perubahan Bentuk", data.basketDoorDeformationResult)
            checkItems.addCheckItem(cat, "Pintu - Pengikat", data.basketDoorFasteningResult)
            checkItems.addCheckItem(cat, "Handrail - Keretakan", data.handrailCracksResult)
            checkItems.addCheckItem(cat, "Handrail - Keausan", data.handrailWearResult)
            checkItems.addCheckItem(cat, "Handrail - Kelurusan Rel", data.handrailRailStraightnessResult)
            checkItems.addCheckItem(cat, "Handrail - Sambungan Rel", data.handrailRailJointsResult)
            checkItems.addCheckItem(cat, "Handrail - Kelurusan Antar Rel", data.handrailInterRailStraightnessResult)
            checkItems.addCheckItem(cat, "Handrail - Jarak Antar Sambungan Rel", data.handrailRailJointGapResult)
            checkItems.addCheckItem(cat, "Handrail - Pengikat Rel", data.handrailRailFastenersResult)
            checkItems.addCheckItem(cat, "Handrail - Rel Stopper", data.handrailRailStopperResult)
        }
        inspectionAndTesting.hydraulicComponents.let { data ->
            val cat = ForkliftCategory.VISUAL_HYDRAULIC
            checkItems.addCheckItem(cat, "Tangki - Kebocoran", data.tankLeakageResult)
            checkItems.addCheckItem(cat, "Tangki - Level Oli", data.tankOilLevelResult)
            checkItems.addCheckItem(cat, "Tangki - Kondisi Oli", data.tankOilConditionResult)
            checkItems.addCheckItem(cat, "Tangki - Kondisi Saluran Isap", data.tankSuctionLineResult)
            checkItems.addCheckItem(cat, "Tangki - Kondisi Saluran Balik", data.tankReturnLineResult)
            checkItems.addCheckItem(cat, "Pompa - Kebocoran", data.pumpLeakageResult)
            checkItems.addCheckItem(cat, "Pompa - Kondisi Saluran Isap", data.pumpSuctionLineResult)
            checkItems.addCheckItem(cat, "Pompa - Kondisi Saluran Tekan", data.pumpPressureLineResult)
            checkItems.addCheckItem(cat, "Pompa - Fungsi", data.pumpFunctionResult)
            checkItems.addCheckItem(cat, "Pompa - Kelainan Suara", data.pumpNoiseResult)
            checkItems.addCheckItem(cat, "Control Valve - Kebocoran", data.valveLeakageResult)
            checkItems.addCheckItem(cat, "Control Valve - Kondisi Saluran", data.valveLineConditionResult)
            checkItems.addCheckItem(cat, "Control Valve - Fungsi Relief Valve", data.valveReliefFunctionResult)
            checkItems.addCheckItem(cat, "Control Valve - Kelainan Suara", data.valveNoiseResult)
            checkItems.addCheckItem(cat, "Control Valve - Fungsi Silinder Angkat", data.valveLiftCylinderResult)
            checkItems.addCheckItem(cat, "Control Valve - Fungsi Silinder Ungkit", data.valveTiltCylinderResult)
            checkItems.addCheckItem(cat, "Control Valve - Fungsi Silinder Kemudi", data.valveSteeringCylinderResult)
            checkItems.addCheckItem(cat, "Aktuator - Kebocoran", data.actuatorLeakageResult)
            checkItems.addCheckItem(cat, "Aktuator - Kondisi Saluran", data.actuatorLineConditionResult)
            checkItems.addCheckItem(cat, "Aktuator - Kelainan Suara", data.actuatorNoiseResult)
        }
        inspectionAndTesting.engineOnChecks.let { data ->
            val cat = ForkliftCategory.ENGINE_ON_INSPECTION
            checkItems.addCheckItem(cat, "Dinamo starter", data.starterDynamoResult)
            checkItems.addCheckItem(cat, "Kerja instrumen/indikator", data.instrumentResult)
            checkItems.addCheckItem(cat, "Kerja perlengkapan listrik", data.electricalResult)
            checkItems.addCheckItem(cat, "Kebocoran - Oli mesin", data.leakageEngineOilResult)
            checkItems.addCheckItem(cat, "Kebocoran - Bahan bakar", data.leakageFuelResult)
            checkItems.addCheckItem(cat, "Kebocoran - Air pendingin", data.leakageCoolantResult)
            checkItems.addCheckItem(cat, "Kebocoran - Oli hidraulik", data.leakageHydraulicOilResult)
            checkItems.addCheckItem(cat, "Kebocoran - Oli transmisi", data.leakageTransmissionOilResult)
            checkItems.addCheckItem(cat, "Kebocoran - Oli final drive", data.leakageFinalDriveOilResult)
            checkItems.addCheckItem(cat, "Kebocoran - Minyak rem", data.leakageBrakeFluidResult)
            checkItems.addCheckItem(cat, "Kerja kopling", data.clutchResult)
            checkItems.addCheckItem(cat, "Kerja persneling (maju mundur)", data.transmissionResult)
            checkItems.addCheckItem(cat, "Kerja rem tangan dan kaki", data.brakeResult)
            checkItems.addCheckItem(cat, "Kerja klakson signal alarm", data.hornAlarmResult)
            checkItems.addCheckItem(cat, "Kerja lampu-lampu", data.lampsResult)
            checkItems.addCheckItem(cat, "Motor Hidraulik/sistem Hidraulik", data.hydraulicMotorResult)
            checkItems.addCheckItem(cat, "Kerja silinder stir/power stering", data.steeringCylinderResult)
            checkItems.addCheckItem(cat, "Kerja silinder pengangkat", data.liftingCylinderResult)
            checkItems.addCheckItem(cat, "Kerja silinder ungkit", data.tiltingCylinderResult)
            checkItems.addCheckItem(cat, "Kondisi gas buang", data.exhaustGasResult)
            checkItems.addCheckItem(cat, "Kerja semua tuas-tuas kontrol", data.controlLeversResult)
            checkItems.addCheckItem(cat, "Suara berisik - Mesin", data.noiseEngineResult)
            checkItems.addCheckItem(cat, "Suara berisik - Turbocharger", data.noiseTurbochargerResult)
            checkItems.addCheckItem(cat, "Suara berisik - Transmisi", data.noiseTransmissionResult)
            checkItems.addCheckItem(cat, "Suara berisik - Pompa Hidraulik", data.noiseHydraulicPumpResult)
            checkItems.addCheckItem(cat, "Suara berisik - Tutup pelindung", data.noiseProtectiveCoverResult)
        }
    }

    // Map TestingForklift to Test Results and Check Items (for NDE Fork)
    this.testingForklift.let { testingForklift ->
        testingForklift.liftingChainInspection.forEachIndexed { index, item ->
            val notes = "Jenis:${item.constructionType}|StdPitch:${item.standardPitch}|MeasurePitch:${item.measuredPitch}|StdPin:${item.standardPin}|MeasurePin:${item.measuredPin}|Hasil:${item.result}"
            testResults.add(InspectionTestResultDomain(0, inspectionId, "Pemeriksaan Rantai #${index + 1}", item.inspectedPart, notes))
        }
        testingForklift.nonDestructiveTesting.let { ndt ->
            testResults.add(InspectionTestResultDomain(0, inspectionId, "Jenis NDT", ndt.ndtType, null))
            ndt.results.forEach { result ->
                // For NDE Fork, we store it as a check item
                val status = result.defectNotFound.isNotBlank() || result.defectFound.isBlank() // If defectNotFound has value or defectFound is blank, then status is true (OK)
                val itemResult = if (result.defectFound.isNotBlank()) result.defectFound else result.defectNotFound
                checkItems.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = ForkliftCategory.NDE_FORK, itemName = "${result.inspectedPart} - ${result.location}", status = status, result = itemResult))
            }
        }
        testingForklift.loadTesting.forEachIndexed { index, item ->
            val notes = "Tinggi Angkat:${item.liftingHeight}|Kecepatan:${item.speed}|Gerakan:${item.movement}|Hasil:${item.result}|Ket:${item.remarks}"
            testResults.add(InspectionTestResultDomain(0, inspectionId, "Uji Beban #${index + 1}", item.testLoad, notes))
        }
    }

    // Map Conclusion and Recommendation to Findings
    this.conclusion.split('\n').filter { it.isNotBlank() }.forEach { line ->
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = line, type = FindingType.RECOMMENDATION))
    }

    this.recommendation.split('\n').filter { it.isNotBlank() }.forEach { line ->
        findings.add(InspectionFindingDomain(inspectionId = inspectionId, description = line, type = FindingType.FINDING))
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = findings,
        testResults = testResults
    )
}

/**
 * Converts a [ForkliftBapReportData] DTO to an [InspectionWithDetailsDomain] object.
 * This is used when receiving forklift BAP data from the API.
 *
 * @return An [InspectionWithDetailsDomain] object populated with data from the DTO.
 */
fun ForkliftBapReportData.toInspectionWithDetailsDomain(): InspectionWithDetailsDomain {
    val inspectionId = this.extraId // BAP ID from local to cloud
    val inspectionDomain = InspectionDomain(
        id = inspectionId, // BAP ID from local
        extraId = this.laporanId, // REPORT ID from local and cloud
        moreExtraId = this.id, // BAP ID from cloud
        documentType = DocumentType.BAP, // This DTO is specifically for BAP
        inspectionType = InspectionType.PAA, // Assuming PAA for Forklift BAP
        subInspectionType = SubInspectionType.Forklift,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.generalData.ownerName,
        ownerAddress = this.generalData.ownerAddress,
        usageLocation = this.generalData.userInCharge,
        addressUsageLocation = null,
        driveType = null,
        serialNumber = this.technicalData.serialNumberUnitNumber,
        permitNumber = null,
        capacity = this.technicalData.capacityWorkingLoad,
        speed = null,
        floorServed = this.technicalData.liftingHeightMeters,
        manufacturer = ManufacturerDomain(
            name = this.technicalData.manufacturer,
            brandOrType = this.technicalData.brandType,
            year = this.technicalData.locationAndYearOfManufacture
        ),
        createdAt = this.createdAt,
        reportDate = this.inspectionDate,
        status = null, // BAP does not have a final conclusion/status field
        isSynced = true,
        isEdited = false
    )

    val checkItems = mutableListOf<InspectionCheckItemDomain>()

    fun MutableList<InspectionCheckItemDomain>.addCheckItem(category: String, itemName: String, status: Boolean, result: String? = null) {
        this.add(InspectionCheckItemDomain(inspectionId = inspectionId, category = category, itemName = itemName, status = status, result = result))
    }

    // Visual Check
    this.inspectionResult.visualCheck.let { data ->
        checkItems.addCheckItem(BAPCategory.VISUAL_INSPECTION, "Fork Mengalami Cacat", data.hasForkDefects)
        checkItems.addCheckItem(BAPCategory.VISUAL_INSPECTION, "Name Plate Terpasang", data.isNameplateAttached)
        checkItems.addCheckItem(BAPCategory.VISUAL_INSPECTION, "APAR Tersedia", data.isAparAvailable)
        checkItems.addCheckItem(BAPCategory.VISUAL_INSPECTION, "Penandaan Kapasitas Terpasang", data.isCapacityMarkingDisplayed)
        checkItems.addCheckItem(BAPCategory.VISUAL_INSPECTION, "Terdapat Kebocoran Hidrolik", data.hasHydraulicLeak)
        checkItems.addCheckItem(BAPCategory.VISUAL_INSPECTION, "Kondisi Rantai Baik", data.isChainGoodCondition)
    }

    // Functional Test
    this.inspectionResult.functionalTest.let { data ->
        checkItems.addCheckItem(BAPCategory.TESTING, "Beban Uji (kg)", data.loadKg.isNotBlank(), data.loadKg)
        checkItems.addCheckItem(BAPCategory.TESTING, "Tinggi Angkat Uji (meter)", data.liftHeightMeters.isNotBlank(), data.liftHeightMeters)
        checkItems.addCheckItem(BAPCategory.TESTING, "Mampu Mengangkat dan Menahan", data.isAbleToLiftAndHold)
        checkItems.addCheckItem(BAPCategory.TESTING, "Berfungsi Dengan Baik", data.isFunctioningWell)
        checkItems.addCheckItem(BAPCategory.TESTING, "Ada Indikasi Retak", data.hasCrackIndication)
        checkItems.addCheckItem(BAPCategory.TESTING, "Stop Darurat Berfungsi", data.isEmergencyStopFunctional)
        checkItems.addCheckItem(BAPCategory.TESTING, "Lampu Peringatan dan Klakson Berfungsi", data.isWarningLampHornFunctional)
    }

    return InspectionWithDetailsDomain(
        inspection = inspectionDomain,
        checkItems = checkItems,
        findings = emptyList(), // BAP DTO does not include findings
        testResults = emptyList() // BAP DTO does not include detailed test results
    )
}
