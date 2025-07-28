package com.nakersolutionid.nakersolutionid.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

/**
 * Domain model for an Inspection. This class represents the core business
 * object and is independent of the data layer (Room database entities).
 */
data class InspectionDomain(
    val id: Long,
    val extraId: String,
    val moreExtraId: String,
    val documentType: DocumentType,
    val inspectionType: InspectionType,
    val subInspectionType: SubInspectionType,
    val equipmentType: String,
    val examinationType: String,
    val ownerName: String? = null,
    val ownerAddress: String? = null,
    val usageLocation: String? = null,
    val addressUsageLocation: String? = null,
    val driveType: String? = null,
    val serialNumber: String? = null,
    val permitNumber: String? = null,
    val capacity: String? = null,
    val speed: String? = null,
    val floorServed: String? = null,
    val manufacturer: ManufacturerDomain? = null,
    val createdAt: String? = null,
    val reportDate: String? = null,
    val nextInspectionDate: String? = null,
    val inspectorName: String? = null,
    val status: String? = null,
    val isSynced: Boolean = false,
    val isEdited: Boolean = false
)

/**
 * Domain model for Manufacturer information.
 */
data class ManufacturerDomain(
    val name: String? = null,
    val brandOrType: String? = null,
    val year: String? = null
)

/**
 * Domain model for an Inspection Check Item.
 */
data class InspectionCheckItemDomain(
    val id: Long = 0,
    val inspectionId: Long,
    val category: String,
    val itemName: String,
    val status: Boolean,
    val result: String? = null
)

/**
 * Domain model for an Inspection Finding or Recommendation.
 */
data class InspectionFindingDomain(
    val id: Long = 0,
    val inspectionId: Long,
    val description: String,
    val type: FindingType
)

enum class FindingType {
    FINDING,
    RECOMMENDATION,
    SUMMARY // ADDED: To differentiate between findings and summary/conclusion
}

/**
 * Domain model for an Inspection Test Result.
 */
data class InspectionTestResultDomain(
    val id: Long = 0,
    val inspectionId: Long,
    val testName: String,
    val result: String,
    val notes: String?
)

/**
 * Domain model representing a complete Inspection report with all its details.
 */
data class InspectionWithDetailsDomain(
    val inspection: InspectionDomain,
    val checkItems: List<InspectionCheckItemDomain>,
    val findings: List<InspectionFindingDomain>,
    val testResults: List<InspectionTestResultDomain>
)