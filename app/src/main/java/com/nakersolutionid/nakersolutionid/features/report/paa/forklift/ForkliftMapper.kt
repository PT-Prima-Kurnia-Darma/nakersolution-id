package com.nakersolutionid.nakersolutionid.features.report.paa.forklift

import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.*
import kotlinx.collections.immutable.toImmutableList

/**
 * Menyimpan semua nama kategori sebagai konstanta untuk mencegah kesalahan ketik dan memastikan konsistensi.
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
}

// =================================================================================================
//                                  UI State -> Domain Model
// =================================================================================================

fun ForkliftUiState.toInspectionWithDetailsDomain(currentTime: String): InspectionWithDetailsDomain {
    val report = this.forkliftInspectionReport
    val general = report.generalData
    val inspectionId: Long = 0

    val manufacturer = ManufacturerDomain(
        name = general.manufacturer,
        brandOrType = general.brandType,
        year = general.yearOfManufacture
    )

    val inspection = InspectionDomain(
        id = inspectionId,
        extraId = "",
        documentType = DocumentType.LAPORAN,
        inspectionType = InspectionType.PAA,
        subInspectionType = SubInspectionType.Forklift,
        equipmentType = report.equipmentType,
        examinationType = report.examinationType,
        ownerName = general.owner,
        ownerAddress = general.address,
        usageLocation = general.unitLocation,
        addressUsageLocation = general.address,
        driveType = general.driveType,
        serialNumber = general.serialNumber,
        permitNumber = general.permitNumber,
        capacity = general.liftingCapacity,
        manufacturer = manufacturer,
        createdAt = currentTime,
        isSynced = false
    )

    val checkItems = createCheckItemsFromUiState(report, inspectionId)
    val testResults = createTestResultsFromUiState(report, inspectionId)
    val findings = createFindingsFromUiState(report, inspectionId)

    return InspectionWithDetailsDomain(inspection, checkItems, findings, testResults)
}

private fun createCheckItemsFromUiState(report: ForkliftInspectionReport, id: Long): List<InspectionCheckItemDomain> {
    val items = mutableListOf<InspectionCheckItemDomain>()
    fun add(cat: String, name: String, value: ForkliftInspectionResult) {
        items.add(InspectionCheckItemDomain(inspectionId = id, category = cat, itemName = name, status = !value.status, result = value.result))
    }

    report.visualInspection.let { v ->
        add(ForkliftCategory.VISUAL_CHASSIS, "Rangka Penguat - Korosi", v.chassisReinforcementCorrosion); add(ForkliftCategory.VISUAL_CHASSIS, "Rangka Penguat - Keretakan", v.chassisReinforcementCracks); add(ForkliftCategory.VISUAL_CHASSIS, "Rangka Penguat - Perubahan Bentuk", v.chassisReinforcementDeformation); add(ForkliftCategory.VISUAL_CHASSIS, "Pemberat (C/W) - Korosi", v.counterweightCorrosion); add(ForkliftCategory.VISUAL_CHASSIS, "Pemberat (C/W) - Kondisi", v.counterweightCondition)
        add(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Lantai / Dek", v.otherEquipmentFloorDeck); add(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Tangga / pijakan", v.otherEquipmentStairs); add(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Baut-baut Pengikat", v.otherEquipmentBindingBolts); add(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Dudukan Operator (Jok)", v.otherEquipmentOperatorSeat)
        add(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Pendingin", v.primeMoverCoolingSystem); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Pelumas", v.primeMoverLubricantSystem); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Bahan Bakar", v.primeMoverFuelSystem); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Pemasukan Udara", v.primeMoverAirIntakeSystem); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Gas Buang", v.primeMoverExhaustSystem); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Starter", v.primeMoverStarterSystem); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Accu/Battery", v.primeMoverElectricalBattery); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Dinamo Starting", v.primeMoverElectricalStartingDynamo); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Alternator", v.primeMoverElectricalAlternator); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Kabel Accu", v.primeMoverElectricalBatteryCable); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Kabel Instalasi", v.primeMoverElectricalWiring); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Lampu Penerangan", v.primeMoverElectricalLighting); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Lampu Pengaman/Sign", v.primeMoverElectricalSafetyLights); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Klakson", v.primeMoverElectricalHorn); add(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Pengaman Lebur/Sekring", v.primeMoverElectricalFuse)
        add(ForkliftCategory.VISUAL_DASHBOARD, "Indikator Suhu", v.dashboardTemperatureIndicator); add(ForkliftCategory.VISUAL_DASHBOARD, "Tekanan Oli Mesin", v.dashboardEngineOilPressure); add(ForkliftCategory.VISUAL_DASHBOARD, "Tekanan Hidraulik", v.dashboardHydraulicPressure); add(ForkliftCategory.VISUAL_DASHBOARD, "Hour Meter", v.dashboardHourMeter); add(ForkliftCategory.VISUAL_DASHBOARD, "Pemanas awal / Glow Plug", v.dashboardGlowPlug); add(ForkliftCategory.VISUAL_DASHBOARD, "Indikator Bahan Bakar", v.dashboardFuelIndicator); add(ForkliftCategory.VISUAL_DASHBOARD, "Indikator Beban", v.dashboardLoadIndicator); add(ForkliftCategory.VISUAL_DASHBOARD, "Load Chart / Name Plate", v.dashboardLoadChart); add(ForkliftCategory.VISUAL_DASHBOARD, "Pengisian Accu / Ampere", v.dashboardAmpereMeter)
        add(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Kemudi Roda", v.powerTrainSteeringWheel); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Batang Kemudi", v.powerTrainSteeringRod); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Kotak Gigi/Gear Box", v.powerTrainSteeringGearBox); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Pengubah Gerak/Pitman", v.powerTrainSteeringPitmanArm); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Batang Tarik/Drag Link", v.powerTrainSteeringDragLink); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Tire Rod", v.powerTrainSteeringTieRod); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Pelumasan", v.powerTrainSteeringLubrication); add(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Front (Penggerak)", v.powerTrainWheelFront); add(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Rear (Kemudi)", v.powerTrainWheelRear); add(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Baut Pengikat", v.powerTrainWheelBindingBolts); add(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Tromol/Hub", v.powerTrainWheelHub); add(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Pelumasan", v.powerTrainWheelLubrication); add(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Perlengkapan Mekanis", v.powerTrainWheelMechanicalEquipment); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Rumah Kopling", v.powerTrainClutchHousing); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Kondisi Kopling", v.powerTrainClutchCondition); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Pelumas/Oli Transmisi", v.powerTrainClutchTransmissionOil); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Kebocoran Transmisi", v.powerTrainClutchTransmissionLeakage); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Poros Penghubung", v.powerTrainClutchConnectingShaft); add(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Perlengkapan Mekanis", v.powerTrainClutchMechanicalEquipment); add(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Rumah Gardan", v.powerTrainDifferentialHousing); add(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Kondisi Gardan", v.powerTrainDifferentialCondition); add(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Oli Gardan", v.powerTrainDifferentialOil); add(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Kebocoran Gardan", v.powerTrainDifferentialLeakage); add(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Poros Penghubung", v.powerTrainDifferentialConnectingShaft); add(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kondisi Rem Utama", v.powerTrainBrakeMainCondition); add(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kondisi Rem Tangan", v.powerTrainBrakeHandbrakeCondition); add(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kondisi Rem Darurat", v.powerTrainBrakeEmergencyCondition); add(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kebocoran", v.powerTrainBrakeLeakage); add(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Komponen Mekanis", v.powerTrainBrakeMechanicalComponents); add(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Rumah Transmisi", v.powerTrainTransmissionHousing); add(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Oli Transmisi", v.powerTrainTransmissionOil); add(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Kebocoran Transmisi", v.powerTrainTransmissionLeakage); add(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Perlengkapan Mekanis", v.powerTrainTransmissionMechanicalEquipment)
        add(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Keausan", v.attachmentMastWear); add(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Keretakan", v.attachmentMastCracks); add(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Perubahan Bentuk", v.attachmentMastDeformation); add(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Pelumasan", v.attachmentMastLubrication); add(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Poros dan Bantalan", v.attachmentMastShaftAndBearing); add(ForkliftCategory.VISUAL_ATTACHMENT, "Rantai Pengangkat - Kondisi", v.attachmentLiftChainCondition); add(ForkliftCategory.VISUAL_ATTACHMENT, "Rantai Pengangkat - Perubahan Bentuk", v.attachmentLiftChainDeformation); add(ForkliftCategory.VISUAL_ATTACHMENT, "Rantai Pengangkat - Pelumasan", v.attachmentLiftChainLubrication)
        add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Korosi", v.personalBasketWorkFloorCorrosion); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Keretakan", v.personalBasketWorkFloorCracks); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Perubahan Bentuk", v.personalBasketWorkFloorDeformation); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Pengikat", v.personalBasketWorkFloorBinding); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Korosi", v.personalBasketFrameCorrosion); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Keretakan", v.personalBasketFrameCracks); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Perubahan Bentuk", v.personalBasketFrameDeformation); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Penguat Melintang", v.personalBasketFrameCrossBracing); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Penguat Diagonal", v.personalBasketFrameDiagonalBracing); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Korosi", v.personalBasketBindingBoltCorrosion); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Keretakan", v.personalBasketBindingBoltCracks); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Perubahan Bentuk", v.personalBasketBindingBoltDeformation); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Pengikat", v.personalBasketBindingBoltBinding); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Korosi", v.personalBasketDoorCorrosion); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Keretakan", v.personalBasketDoorCracks); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Perubahan Bentuk", v.personalBasketDoorDeformation); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Pengikat", v.personalBasketDoorBinding); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Keretakan", v.personalBasketHandrailCracks); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Keausan", v.personalBasketHandrailWear); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Kelurusan Rel", v.personalBasketHandrailRailStraightness); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Sambungan Rel", v.personalBasketHandrailRailJoint); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Kelurusan Antar Rel", v.personalBasketHandrailAlignmentBetweenRails); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Jarak Antar Sambungan Rel", v.personalBasketHandrailGapBetweenRailJoints); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Pengikat Rel", v.personalBasketHandrailRailFastener); add(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Rel Stopper", v.personalBasketHandrailRailStopper)
        add(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kebocoran", v.hydraulicTankLeakage); add(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Level Oli", v.hydraulicTankOilLevel); add(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kondisi Oli", v.hydraulicTankOilCondition); add(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kondisi Saluran Isap", v.hydraulicTankSuctionLineCondition); add(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kondisi Saluran Balik", v.hydraulicTankReturnLineCondition); add(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kebocoran", v.hydraulicPumpLeakage); add(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kondisi Saluran Isap", v.hydraulicPumpSuctionLineCondition); add(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kondisi Saluran Tekan", v.hydraulicPumpPressureLineCondition); add(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Fungsi", v.hydraulicPumpFunction); add(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kelainan Suara", v.hydraulicPumpAbnormalNoise); add(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Kebocoran", v.controlValveLeakage); add(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Kondisi Saluran", v.controlValveLineCondition); add(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Relief Valve", v.controlValveReliefValveFunction); add(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Kelainan Suara", v.controlValveAbnormalNoise); add(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Silinder Angkat", v.controlValveLiftCylinderFunction); add(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Silinder Ungkit", v.controlValveTiltCylinderFunction); add(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Silinder Kemudi", v.controlValveSteeringCylinderFunction); add(ForkliftCategory.VISUAL_HYDRAULIC, "Aktuator - Kebocoran", v.actuatorLeakage); add(ForkliftCategory.VISUAL_HYDRAULIC, "Aktuator - Kondisi Saluran", v.actuatorLineCondition); add(ForkliftCategory.VISUAL_HYDRAULIC, "Aktuator - Kelainan Suara", v.actuatorAbnormalNoise)
    }

    report.testing.engineOnInspection.let { e ->
        val cat = ForkliftCategory.ENGINE_ON_INSPECTION
        add(cat, "Dinamo starter", e.dynamoStarter); add(cat, "Kerja instrumen/indikator", e.instrumentIndicatorFunction); add(cat, "Kerja perlengkapan listrik", e.electricalEquipmentFunction); add(cat, "Kebocoran - Oli mesin", e.engineOilLeakage); add(cat, "Kebocoran - Bahan bakar", e.fuelLeakage); add(cat, "Kebocoran - Air pendingin", e.coolantLeakage); add(cat, "Kebocoran - Oli hidraulik", e.hydraulicOilLeakage); add(cat, "Kebocoran - Oli transmisi", e.transmissionOilLeakage); add(cat, "Kebocoran - Oli final drive", e.finalDriveOilLeakage); add(cat, "Kebocoran - Minyak rem", e.brakeFluidLeakage); add(cat, "Kerja kopling", e.clutchFunction); add(cat, "Kerja persneling (maju mundur)", e.transmissionFunction); add(cat, "Kerja rem tangan dan kaki", e.brakeFunction); add(cat, "Kerja klakson signal alarm", e.hornAlarmFunction); add(cat, "Kerja lampu-lampu", e.lightsFunction); add(cat, "Motor Hidraulik/sistem Hidraulik", e.hydraulicSystemFunction); add(cat, "Kerja silinder stir/power stering", e.powerSteeringFunction); add(cat, "Kerja silinder pengangkat", e.liftCylinderFunction); add(cat, "Kerja silinder ungkit", e.tiltCylinderFunction); add(cat, "Kondisi gas buang", e.exhaustGasCondition); add(cat, "Kerja semua tuas-tuas kontrol", e.controlLeversFunction); add(cat, "Suara berisik - Mesin", e.engineNoise); add(cat, "Suara berisik - Turbocharger", e.turbochargerNoise); add(cat, "Suara berisik - Transmisi", e.transmissionNoise); add(cat, "Suara berisik - Pompa Hidraulik", e.hydraulicPumpNoise); add(cat, "Suara berisik - Tutup pelindung", e.guardNoise)
    }

    report.nonDestructiveExamination.forkNDT.items.forEach { item ->
        items.add(InspectionCheckItemDomain(inspectionId = id, category = ForkliftCategory.NDE_FORK, itemName = "${item.partInspected} - ${item.location}", status = !item.finding.status, result = item.finding.result))
    }
    return items
}

private fun createTestResultsFromUiState(report: ForkliftInspectionReport, id: Long): List<InspectionTestResultDomain> {
    val results = mutableListOf<InspectionTestResultDomain>()
    fun add(name: String, value: String?) { if (!value.isNullOrBlank()) results.add(InspectionTestResultDomain(0, id, name, value, null)) }
    report.generalData.let { add("Pemakai", it.user); add("Penanggung Jawab", it.personInCharge); add("Nama Operator", it.operatorName); add("Digunakan untuk", it.intendedUse); add("Data Riwayat Pesawat", it.equipmentHistory) }
    report.technicalData.let { t ->
        t.specifications.let { s -> add("Spesifikasi - No. Seri", s.serialNumber); add("Spesifikasi - Kapasitas", s.capacity); add("Spesifikasi - Attachment", s.attachment); add("Spesifikasi - Dimensi Garpu", s.forkDimension) }
        t.speed.let { s -> add("Kecepatan - Angkat", s.lifting); add("Kecepatan - Turun", s.lowering); add("Kecepatan - Jalan", s.travelling) }
        t.primeMover.let { p -> add("Prime Mover - Putaran", p.revolution); add("Prime Mover - Merk/Tipe", p.brandType); add("Prime Mover - No. Seri", p.serialNumber); add("Prime Mover - Tahun", p.yearOfManufacture); add("Prime Mover - Daya", p.power); add("Prime Mover - Jml Silinder", p.numberOfCylinders) }
        t.dimensions.let { d -> add("Dimensi - Panjang", d.length); add("Dimensi - Lebar", d.width); add("Dimensi - Tinggi", d.height); add("Dimensi - Tinggi Angkat Garpu", d.forkLiftingHeight) }
        t.tirePressure.let { p -> add("Tekanan Roda - Penggerak", p.driveWheel); add("Tekanan Roda - Kemudi", p.steeringWheel) }
        t.driveWheel.let { w -> add("Roda Penggerak - Ukuran", w.size); add("Roda Penggerak - Tipe", w.type) }
        t.steeringWheel.let { w -> add("Roda Kemudi - Ukuran", w.size); add("Roda Kemudi - Tipe", w.type) }
        t.travellingBrake.let { b -> add("Rem Jalan - Ukuran", b.size); add("Rem Jalan - Tipe", b.type) }
        t.hydraulicPump.let { p -> add("Pompa Hidraulik - Tekanan", p.pressure); add("Pompa Hidraulik - Tipe", p.type); add("Pompa Hidraulik - Relief Valve", p.reliefValve) }
    }
    add("Jenis NDT", report.nonDestructiveExamination.forkNDT.ndtType)
    report.nonDestructiveExamination.liftingChainInspection.items.forEachIndexed { i, item ->
        val notes = "Jenis:${item.typeAndConstruction}|StdPitch:${item.standardPitchMm}|MeasurePitch:${item.measuredPitchMm}|StdPin:${item.standardPinMm}|MeasurePin:${item.measuredPinMm}|Ket:${item.remarks}"
        results.add(InspectionTestResultDomain(0, id, "Pemeriksaan Rantai #${i + 1}", item.chain, notes))
    }
    report.testing.loadTest.items.forEachIndexed { i, item ->
        val notes = "Tinggi Angkat:${item.forkLiftingHeight}|Kecepatan:${item.travelingSpeed}|Gerakan:${item.movement}|Hasil:${item.result}|Ket:${item.remarks}"
        results.add(InspectionTestResultDomain(0, id, "Uji Beban #${i + 1}", item.testLoad, notes))
    }
    return results
}

private fun createFindingsFromUiState(report: ForkliftInspectionReport, id: Long): List<InspectionFindingDomain> {
    val findings = mutableListOf<InspectionFindingDomain>()
    report.conclusion.summary.forEach { findings.add(InspectionFindingDomain(inspectionId = id, description = it, type = FindingType.FINDING)) }
    report.conclusion.recommendations.forEach { findings.add(InspectionFindingDomain(inspectionId = id, description = it, type = FindingType.RECOMMENDATION)) }
    return findings
}


// =================================================================================================
//                                  Domain Model -> UI State
// =================================================================================================

/**
 * Helper function to parse a serialized note string from the database.
 * Example note: "Key1:Value1|Key2:Value2"
 */
private fun parseNote(notes: String?, key: String): String {
    return notes?.split('|')?.find { it.startsWith(key) }?.removePrefix(key) ?: ""
}

fun InspectionWithDetailsDomain.toForkliftUiState(): ForkliftUiState {
    fun findTest(name: String) = this.testResults.find { it.testName.equals(name, true) }?.result ?: ""
    fun findCheck(cat: String, name: String) = this.checkItems.find { it.category == cat && it.itemName == name }?.let { ForkliftInspectionResult(status = !it.status, result = it.result ?: "") } ?: ForkliftInspectionResult()

    val generalData = ForkliftGeneralData(
        owner = this.inspection.ownerName ?: "", address = this.inspection.ownerAddress ?: "", unitLocation = this.inspection.usageLocation ?: "", driveType = this.inspection.driveType ?: "", manufacturer = this.inspection.manufacturer?.name ?: "", brandType = this.inspection.manufacturer?.brandOrType ?: "", yearOfManufacture = this.inspection.manufacturer?.year ?: "", serialNumber = this.inspection.serialNumber ?: "", liftingCapacity = this.inspection.capacity ?: "", permitNumber = this.inspection.permitNumber ?: "", user = findTest("Pemakai"), personInCharge = findTest("Penanggung Jawab"), operatorName = findTest("Nama Operator"), intendedUse = findTest("Digunakan untuk"), equipmentHistory = findTest("Data Riwayat Pesawat")
    )
    val technicalData = ForkliftTechnicalData(
        specifications = ForkliftSpecifications(findTest("Spesifikasi - No. Seri"), findTest("Spesifikasi - Kapasitas"), findTest("Spesifikasi - Attachment"), findTest("Spesifikasi - Dimensi Garpu")),
        speed = ForkliftSpeed(findTest("Kecepatan - Angkat"), findTest("Kecepatan - Turun"), findTest("Kecepatan - Jalan")),
        primeMover = ForkliftPrimeMover(findTest("Prime Mover - Putaran"), findTest("Prime Mover - Merk/Tipe"), findTest("Prime Mover - No. Seri"), findTest("Prime Mover - Tahun"), findTest("Prime Mover - Daya"), findTest("Prime Mover - Jml Silinder")),
        dimensions = ForkliftDimensions(findTest("Dimensi - Panjang"), findTest("Dimensi - Lebar"), findTest("Dimensi - Tinggi"), findTest("Dimensi - Tinggi Angkat Garpu")),
        tirePressure = ForkliftTirePressure(findTest("Tekanan Roda - Penggerak"), findTest("Tekanan Roda - Kemudi")),
        driveWheel = ForkliftWheel(findTest("Roda Penggerak - Ukuran"), findTest("Roda Penggerak - Tipe")),
        steeringWheel = ForkliftWheel(findTest("Roda Kemudi - Ukuran"), findTest("Roda Kemudi - Tipe")),
        travellingBrake = ForkliftWheel(findTest("Rem Jalan - Ukuran"), findTest("Rem Jalan - Tipe")),
        hydraulicPump = ForkliftHydraulicPump(findTest("Pompa Hidraulik - Tekanan"), findTest("Pompa Hidraulik - Tipe"), findTest("Pompa Hidraulik - Relief Valve"))
    )
    val visualInspection = ForkliftVisualInspection(
        chassisReinforcementCorrosion = findCheck(ForkliftCategory.VISUAL_CHASSIS, "Rangka Penguat - Korosi"), chassisReinforcementCracks = findCheck(ForkliftCategory.VISUAL_CHASSIS, "Rangka Penguat - Keretakan"), chassisReinforcementDeformation = findCheck(ForkliftCategory.VISUAL_CHASSIS, "Rangka Penguat - Perubahan Bentuk"), counterweightCorrosion = findCheck(ForkliftCategory.VISUAL_CHASSIS, "Pemberat (C/W) - Korosi"), counterweightCondition = findCheck(ForkliftCategory.VISUAL_CHASSIS, "Pemberat (C/W) - Kondisi"),
        otherEquipmentFloorDeck = findCheck(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Lantai / Dek"), otherEquipmentStairs = findCheck(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Tangga / pijakan"), otherEquipmentBindingBolts = findCheck(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Baut-baut Pengikat"), otherEquipmentOperatorSeat = findCheck(ForkliftCategory.VISUAL_OTHER_EQUIPMENT, "Dudukan Operator (Jok)"),
        primeMoverCoolingSystem = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Pendingin"), primeMoverLubricantSystem = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Pelumas"), primeMoverFuelSystem = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Bahan Bakar"), primeMoverAirIntakeSystem = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Pemasukan Udara"), primeMoverExhaustSystem = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Gas Buang"), primeMoverStarterSystem = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Sistem Starter"), primeMoverElectricalBattery = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Accu/Battery"), primeMoverElectricalStartingDynamo = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Dinamo Starting"), primeMoverElectricalAlternator = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Alternator"), primeMoverElectricalBatteryCable = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Kabel Accu"), primeMoverElectricalWiring = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Kabel Instalasi"), primeMoverElectricalLighting = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Lampu Penerangan"), primeMoverElectricalSafetyLights = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Lampu Pengaman/Sign"), primeMoverElectricalHorn = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Klakson"), primeMoverElectricalFuse = findCheck(ForkliftCategory.VISUAL_PRIME_MOVER, "Kelistrikan - Pengaman Lebur/Sekring"),
        dashboardTemperatureIndicator = findCheck(ForkliftCategory.VISUAL_DASHBOARD, "Indikator Suhu"), dashboardEngineOilPressure = findCheck(ForkliftCategory.VISUAL_DASHBOARD, "Tekanan Oli Mesin"), dashboardHydraulicPressure = findCheck(ForkliftCategory.VISUAL_DASHBOARD, "Tekanan Hidraulik"), dashboardHourMeter = findCheck(ForkliftCategory.VISUAL_DASHBOARD, "Hour Meter"), dashboardGlowPlug = findCheck(ForkliftCategory.VISUAL_DASHBOARD, "Pemanas awal / Glow Plug"), dashboardFuelIndicator = findCheck(ForkliftCategory.VISUAL_DASHBOARD, "Indikator Bahan Bakar"), dashboardLoadIndicator = findCheck(ForkliftCategory.VISUAL_DASHBOARD, "Indikator Beban"), dashboardLoadChart = findCheck(ForkliftCategory.VISUAL_DASHBOARD, "Load Chart / Name Plate"), dashboardAmpereMeter = findCheck(ForkliftCategory.VISUAL_DASHBOARD, "Pengisian Accu / Ampere"),
        powerTrainSteeringWheel = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Kemudi Roda"), powerTrainSteeringRod = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Batang Kemudi"), powerTrainSteeringGearBox = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Kotak Gigi/Gear Box"), powerTrainSteeringPitmanArm = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Pengubah Gerak/Pitman"), powerTrainSteeringDragLink = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Batang Tarik/Drag Link"), powerTrainSteeringTieRod = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Tire Rod"), powerTrainSteeringLubrication = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kemudi - Pelumasan"), powerTrainWheelFront = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Front (Penggerak)"), powerTrainWheelRear = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Rear (Kemudi)"), powerTrainWheelBindingBolts = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Baut Pengikat"), powerTrainWheelHub = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Tromol/Hub"), powerTrainWheelLubrication = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Pelumasan"), powerTrainWheelMechanicalEquipment = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Roda - Perlengkapan Mekanis"), powerTrainClutchHousing = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Rumah Kopling"), powerTrainClutchCondition = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Kondisi Kopling"), powerTrainClutchTransmissionOil = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Pelumas/Oli Transmisi"), powerTrainClutchTransmissionLeakage = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Kebocoran Transmisi"), powerTrainClutchConnectingShaft = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Poros Penghubung"), powerTrainClutchMechanicalEquipment = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Kopling - Perlengkapan Mekanis"), powerTrainDifferentialHousing = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Rumah Gardan"), powerTrainDifferentialCondition = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Kondisi Gardan"), powerTrainDifferentialOil = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Oli Gardan"), powerTrainDifferentialLeakage = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Kebocoran Gardan"), powerTrainDifferentialConnectingShaft = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Gardan - Poros Penghubung"), powerTrainBrakeMainCondition = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kondisi Rem Utama"), powerTrainBrakeHandbrakeCondition = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kondisi Rem Tangan"), powerTrainBrakeEmergencyCondition = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kondisi Rem Darurat"), powerTrainBrakeLeakage = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Kebocoran"), powerTrainBrakeMechanicalComponents = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Rem - Komponen Mekanis"), powerTrainTransmissionHousing = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Rumah Transmisi"), powerTrainTransmissionOil = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Oli Transmisi"), powerTrainTransmissionLeakage = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Kebocoran Transmisi"), powerTrainTransmissionMechanicalEquipment = findCheck(ForkliftCategory.VISUAL_POWERTRAIN, "Transmisi - Perlengkapan Mekanis"),
        attachmentMastWear = findCheck(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Keausan"), attachmentMastCracks = findCheck(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Keretakan"), attachmentMastDeformation = findCheck(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Perubahan Bentuk"), attachmentMastLubrication = findCheck(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Pelumasan"), attachmentMastShaftAndBearing = findCheck(ForkliftCategory.VISUAL_ATTACHMENT, "Tiang Penyangga - Poros dan Bantalan"), attachmentLiftChainCondition = findCheck(ForkliftCategory.VISUAL_ATTACHMENT, "Rantai Pengangkat - Kondisi"), attachmentLiftChainDeformation = findCheck(ForkliftCategory.VISUAL_ATTACHMENT, "Rantai Pengangkat - Perubahan Bentuk"), attachmentLiftChainLubrication = findCheck(ForkliftCategory.VISUAL_ATTACHMENT, "Rantai Pengangkat - Pelumasan"),
        personalBasketWorkFloorCorrosion = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Korosi"), personalBasketWorkFloorCracks = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Keretakan"), personalBasketWorkFloorDeformation = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Perubahan Bentuk"), personalBasketWorkFloorBinding = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Lantai Kerja - Pengikat"), personalBasketFrameCorrosion = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Korosi"), personalBasketFrameCracks = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Keretakan"), personalBasketFrameDeformation = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Perubahan Bentuk"), personalBasketFrameCrossBracing = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Penguat Melintang"), personalBasketFrameDiagonalBracing = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Rangka - Penguat Diagonal"), personalBasketBindingBoltCorrosion = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Korosi"), personalBasketBindingBoltCracks = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Keretakan"), personalBasketBindingBoltDeformation = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Perubahan Bentuk"), personalBasketBindingBoltBinding = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Baut Pengikat - Pengikat"), personalBasketDoorCorrosion = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Korosi"), personalBasketDoorCracks = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Keretakan"), personalBasketDoorDeformation = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Perubahan Bentuk"), personalBasketDoorBinding = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Pintu - Pengikat"), personalBasketHandrailCracks = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Keretakan"), personalBasketHandrailWear = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Keausan"), personalBasketHandrailRailStraightness = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Kelurusan Rel"), personalBasketHandrailRailJoint = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Sambungan Rel"), personalBasketHandrailAlignmentBetweenRails = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Kelurusan Antar Rel"), personalBasketHandrailGapBetweenRailJoints = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Jarak Antar Sambungan Rel"), personalBasketHandrailRailFastener = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Pengikat Rel"), personalBasketHandrailRailStopper = findCheck(ForkliftCategory.VISUAL_PERSONAL_BASKET, "Handrail - Rel Stopper"),
        hydraulicTankLeakage = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kebocoran"), hydraulicTankOilLevel = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Level Oli"), hydraulicTankOilCondition = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kondisi Oli"), hydraulicTankSuctionLineCondition = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kondisi Saluran Isap"), hydraulicTankReturnLineCondition = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Tangki - Kondisi Saluran Balik"), hydraulicPumpLeakage = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kebocoran"), hydraulicPumpSuctionLineCondition = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kondisi Saluran Isap"), hydraulicPumpPressureLineCondition = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kondisi Saluran Tekan"), hydraulicPumpFunction = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Fungsi"), hydraulicPumpAbnormalNoise = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Pompa - Kelainan Suara"), controlValveLeakage = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Kebocoran"), controlValveLineCondition = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Kondisi Saluran"), controlValveReliefValveFunction = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Relief Valve"), controlValveAbnormalNoise = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Kelainan Suara"), controlValveLiftCylinderFunction = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Silinder Angkat"), controlValveTiltCylinderFunction = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Silinder Ungkit"), controlValveSteeringCylinderFunction = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Control Valve - Fungsi Silinder Kemudi"), actuatorLeakage = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Aktuator - Kebocoran"), actuatorLineCondition = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Aktuator - Kondisi Saluran"), actuatorAbnormalNoise = findCheck(ForkliftCategory.VISUAL_HYDRAULIC, "Aktuator - Kelainan Suara")
    )
    val engineOnInspection = ForkliftEngineOnInspection(
        dynamoStarter = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Dinamo starter"), instrumentIndicatorFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja instrumen/indikator"), electricalEquipmentFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja perlengkapan listrik"), engineOilLeakage = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Oli mesin"), fuelLeakage = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Bahan bakar"), coolantLeakage = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Air pendingin"), hydraulicOilLeakage = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Oli hidraulik"), transmissionOilLeakage = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Oli transmisi"), finalDriveOilLeakage = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Oli final drive"), brakeFluidLeakage = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kebocoran - Minyak rem"), clutchFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja kopling"), transmissionFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja persneling (maju mundur)"), brakeFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja rem tangan dan kaki"), hornAlarmFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja klakson signal alarm"), lightsFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja lampu-lampu"), hydraulicSystemFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Motor Hidraulik/sistem Hidraulik"), powerSteeringFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja silinder stir/power stering"), liftCylinderFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja silinder pengangkat"), tiltCylinderFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja silinder ungkit"), exhaustGasCondition = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kondisi gas buang"), controlLeversFunction = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Kerja semua tuas-tuas kontrol"), engineNoise = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Mesin"), turbochargerNoise = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Turbocharger"), transmissionNoise = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Transmisi"), hydraulicPumpNoise = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Pompa Hidraulik"), guardNoise = findCheck(ForkliftCategory.ENGINE_ON_INSPECTION, "Suara berisik - Tutup pelindung")
    )

    val ndeChains = this.testResults.filter { it.testName.startsWith("Pemeriksaan Rantai #") }.map {
        ForkliftNdeChainItem(
            chain = it.result,
            typeAndConstruction = parseNote(it.notes, "Jenis:"),
            standardPitchMm = parseNote(it.notes, "StdPitch:"),
            measuredPitchMm = parseNote(it.notes, "MeasurePitch:"),
            standardPinMm = parseNote(it.notes, "StdPin:"),
            measuredPinMm = parseNote(it.notes, "MeasurePin:"),
            remarks = parseNote(it.notes, "Ket:")
        )
    }.toImmutableList()

    val ndeForks = this.checkItems.filter { it.category == ForkliftCategory.NDE_FORK }.map {
        val parts = it.itemName.split(" - ", limit = 2)
        ForkliftNdeForkItem(
            partInspected = parts.getOrNull(0) ?: "",
            location = parts.getOrNull(1) ?: "",
            finding = ForkliftInspectionResult(status = !it.status, result = it.result ?: "")
        )
    }.toImmutableList()

    val loadTests = this.testResults.filter { it.testName.startsWith("Uji Beban #") }.map {
        ForkliftLoadTestItem(
            testLoad = it.result,
            forkLiftingHeight = parseNote(it.notes, "Tinggi Angkat:"),
            travelingSpeed = parseNote(it.notes, "Kecepatan:"),
            movement = parseNote(it.notes, "Gerakan:"),
            result = parseNote(it.notes, "Hasil:"),
            remarks = parseNote(it.notes, "Ket:")
        )
    }.toImmutableList()

    val conclusion = ForkliftConclusion(
        summary = this.findings.filter { it.type == FindingType.FINDING }.map { it.description }.toImmutableList(),
        recommendations = this.findings.filter { it.type == FindingType.RECOMMENDATION }.map { it.description }.toImmutableList()
    )

    return ForkliftUiState(
        forkliftInspectionReport = ForkliftInspectionReport(
            equipmentType = this.inspection.equipmentType, examinationType = this.inspection.examinationType, generalData = generalData, technicalData = technicalData, visualInspection = visualInspection,
            nonDestructiveExamination = ForkliftNde(ForkliftNdeChainInspection(ndeChains), ForkliftNdeFork(findTest("Jenis NDT"), ndeForks)),
            testing = ForkliftTesting(engineOnInspection = engineOnInspection, loadTest = ForkliftLoadTest(loadTests)),
            conclusion = conclusion
        )
    )
}