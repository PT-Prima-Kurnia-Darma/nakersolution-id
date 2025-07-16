package com.nakersolutionid.nakersolutionid.data.local.mapper

import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionCheckItem
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionEntity
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionFinding
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionTestResult
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionWithDetails
import com.nakersolutionid.nakersolutionid.data.local.entity.Manufacturer
import com.nakersolutionid.nakersolutionid.domain.model.History
import com.nakersolutionid.nakersolutionid.domain.model.InspectionCheckItemDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionFindingDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionTestResultDomain
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.model.ManufacturerDomain
// Menggunakan 'as' untuk membedakan dua enum dengan nama yang sama
import com.nakersolutionid.nakersolutionid.data.local.entity.FindingType as EntityFindingType
import com.nakersolutionid.nakersolutionid.domain.model.FindingType as DomainFindingType

// --- Mapper dari Entity (Data Layer) ke Domain (Business/UI Layer) ---

/**
 * Mengubah objek relasi [InspectionWithDetails] dari database menjadi [InspectionWithDetailsDomain]
 * yang bersih untuk digunakan di seluruh aplikasi.
 */
fun InspectionWithDetails.toDomain(): InspectionWithDetailsDomain {
    return InspectionWithDetailsDomain(
        inspection = this.inspectionEntity.toDomain(),
        checkItems = this.checkItems.map { it.toDomain() },
        findings = this.findings.map { it.toDomain() },
        testResults = this.testResults.map { it.toDomain() }
    )
}

/**
 * Mengubah [InspectionEntity] menjadi [InspectionDomain].
 */
fun InspectionEntity.toDomain(): InspectionDomain {
    return InspectionDomain(
        id = this.id,
        extraId = this.extraId,
        documentType = this.documentType,
        inspectionType = this.inspectionType,
        subInspectionType = this.subInspectionType,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.ownerName,
        ownerAddress = this.ownerAddress,
        usageLocation = this.usageLocation,
        addressUsageLocation = this.addressUsageLocation,
        driveType = this.driveType,
        serialNumber = this.serialNumber,
        permitNumber = this.permitNumber,
        capacity = this.capacity,
        speed = this.speed,
        floorServed = this.floorServed,
        manufacturer = this.manufacturer?.toDomain(),
        createdAt = this.createdAt,
        reportDate = this.reportDate,
        nextInspectionDate = this.nextInspectionDate,
        inspectorName = this.inspectorName,
        status = this.status
    )
}

/**
 * Maps an [InspectionEntity] from the data layer (Room) to a [History] object for the domain layer.
 * This is used for populating list views or summaries where full details are not needed.
 *
 * @return The mapped [History] object.
 */
fun InspectionEntity.toHistory(): History {
    return History(
        id = this.id,
        extraId = this.extraId,
        documentType = this.documentType,
        inspectionType = this.inspectionType,
        subInspectionType = this.subInspectionType,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.ownerName,
        createdAt = this.createdAt,
        reportDate = this.reportDate,
        isSynced = this.isSynced
    )
}

/**
 * Mengubah [Manufacturer] (entity) menjadi [ManufacturerDomain].
 */
fun Manufacturer.toDomain(): ManufacturerDomain {
    return ManufacturerDomain(
        name = this.name,
        brandOrType = this.brandOrType,
        year = this.year
    )
}

/**
 * Mengubah [InspectionCheckItem] (entity) menjadi [InspectionCheckItemDomain].
 */
fun InspectionCheckItem.toDomain(): InspectionCheckItemDomain {
    return InspectionCheckItemDomain(
        id = this.id,
        inspectionId = this.inspectionId,
        category = this.category,
        itemName = this.itemName,
        status = this.status,
        result = this.result
    )
}

/**
 * Mengubah [InspectionFinding] (entity) menjadi [InspectionFindingDomain].
 */
fun InspectionFinding.toDomain(): InspectionFindingDomain {
    return InspectionFindingDomain(
        id = this.id,
        inspectionId = this.inspectionId,
        description = this.description,
        type = when (this.type) {
            EntityFindingType.FINDING -> DomainFindingType.FINDING
            EntityFindingType.RECOMMENDATION -> DomainFindingType.RECOMMENDATION
        }
    )
}

/**
 * Mengubah [InspectionTestResult] (entity) menjadi [InspectionTestResultDomain].
 */
fun InspectionTestResult.toDomain(): InspectionTestResultDomain {
    return InspectionTestResultDomain(
        id = this.id,
        inspectionId = this.inspectionId,
        testName = this.testName,
        result = this.result,
        notes = this.notes
    )
}


// --- Mapper dari Domain (Business/UI Layer) ke Entity (Data Layer) ---

/**
 * Mengubah objek domain [InspectionWithDetailsDomain] menjadi [InspectionWithDetails]
 * yang siap untuk disimpan atau diperbarui di database Room.
 * @param extraId ID dari backend yang perlu disimpan.
 * @param inspectionId ID dari inspeksi yang sudah ada (jika update), default 0 untuk data baru.
 */
fun InspectionWithDetailsDomain.toEntity(extraId: String, inspectionId: Long = 0): InspectionWithDetails {
    val entityInspection = this.inspection.toEntity(extraId, inspectionId)
    // Gunakan ID dari entity utama (bisa 0 untuk data baru, atau id yang sudah ada)
    val currentInspectionId = entityInspection.id

    return InspectionWithDetails(
        inspectionEntity = entityInspection,
        checkItems = this.checkItems.map { it.toEntity(currentInspectionId) },
        findings = this.findings.map { it.toEntity(currentInspectionId) },
        testResults = this.testResults.map { it.toEntity(currentInspectionId) }
    )
}

/**
 * Mengubah [InspectionDomain] menjadi [InspectionEntity].
 */
fun InspectionDomain.toEntity(extraId: String, id: Long): InspectionEntity {
    return InspectionEntity(
        id = id, // 0 untuk data baru, akan di-generate otomatis oleh Room
        extraId = extraId,
        documentType = this.documentType,
        inspectionType = this.inspectionType,
        subInspectionType = this.subInspectionType,
        equipmentType = this.equipmentType,
        examinationType = this.examinationType,
        ownerName = this.ownerName,
        ownerAddress = this.ownerAddress,
        usageLocation = this.usageLocation,
        addressUsageLocation = this.addressUsageLocation,
        driveType = this.driveType,
        serialNumber = this.serialNumber,
        permitNumber = this.permitNumber,
        capacity = this.capacity,
        speed = this.speed,
        floorServed = this.floorServed,
        manufacturer = this.manufacturer?.toEntity(),
        createdAt = this.createdAt,
        reportDate = this.reportDate,
        nextInspectionDate = this.nextInspectionDate,
        inspectorName = this.inspectorName,
        status = this.status
    )
}

/**
 * Mengubah [ManufacturerDomain] menjadi [Manufacturer] (entity).
 */
fun ManufacturerDomain.toEntity(): Manufacturer {
    return Manufacturer(
        name = this.name,
        brandOrType = this.brandOrType,
        year = this.year
    )
}

/**
 * Mengubah [InspectionCheckItemDomain] menjadi [InspectionCheckItem] (entity).
 */
fun InspectionCheckItemDomain.toEntity(inspectionId: Long): InspectionCheckItem {
    return InspectionCheckItem(
        id = this.id,
        inspectionId = inspectionId,
        category = this.category,
        itemName = this.itemName,
        status = this.status,
        result = this.result
    )
}

/**
 * Mengubah [InspectionFindingDomain] menjadi [InspectionFinding] (entity).
 */
fun InspectionFindingDomain.toEntity(inspectionId: Long): InspectionFinding {
    return InspectionFinding(
        id = this.id,
        inspectionId = inspectionId,
        description = this.description,
        type = when (this.type) {
            DomainFindingType.FINDING -> EntityFindingType.FINDING
            DomainFindingType.RECOMMENDATION -> EntityFindingType.RECOMMENDATION
        }
    )
}

/**
 * Mengubah [InspectionTestResultDomain] menjadi [InspectionTestResult] (entity).
 */
fun InspectionTestResultDomain.toEntity(inspectionId: Long): InspectionTestResult {
    return InspectionTestResult(
        id = this.id,
        inspectionId = inspectionId,
        testName = this.testName,
        result = this.result,
        notes = this.notes
    )
}