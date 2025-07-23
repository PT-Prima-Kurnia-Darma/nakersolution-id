package com.nakersolutionid.nakersolutionid.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.nakersolutionid.nakersolutionid.data.local.utils.DocumentType
import com.nakersolutionid.nakersolutionid.data.local.utils.InspectionType
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType

// --- CORE ENTITIES (TABLES) ---

/**
 * The central table for all inspectionEntity reports.
 * It unifies common fields from all JSON files.
 * For example, 'ownerName' is mapped from:
 * - elevator.json -> generalData.ownerName
 * - pesawatAngkatAngkutForklift.json -> generalData.owner.name
 * - instalasiListrik.json -> generalData.companyName
 */
@Entity(tableName = "inspections")
data class InspectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name= "extra_id")
    val extraId: String, // Taken from backend

    @ColumnInfo(name= "more_extra_id")
    val moreExtraId: String, // Taken from backend

    /**
     * e.g., "Laporan, BAP, Surat Keterangan Sementara, Sertifikat Sementara"
     */
    @ColumnInfo(name = "document_type")
    val documentType: DocumentType,

    /**
     * e.g., "ILPP", "IPK", "PAA", "PUBT", "PTP", "EE"
     */
    @ColumnInfo(name = "inspection_type")
    val inspectionType: InspectionType,

    /**
     * e.g., "Elevator, Escalator, Forklift, Bulldozer, Excavator, Instalasi Petir"
     */
    @ColumnInfo(name = "sub_inspection_type")
    val subInspectionType: SubInspectionType,

    /**
     * e.g., "Elevator Penumpang, Elevator Barang, Counterbalance Forklift, Order Picker"
     */
    @ColumnInfo(name = "equipment_type")
    val equipmentType: String,

    /**
     * e.g., "Pemeriksaan Berkala, Pemeriksaan Pertama, Pemeriksaan Khusus, Pemeriksaan Ulang"
     */
    @ColumnInfo(name = "examination_type")
    val examinationType: String,

    // --- Owner & Location Data ---
    @ColumnInfo(name = "owner_name")
    val ownerName: String? = null, // e.g., "PT. ABC", "PT. XYZ"

    @ColumnInfo(name = "owner_address")
    val ownerAddress: String? = null, // e.g., "Jl. ABC No. 123", "Jl. XYZ No. 456"

    @ColumnInfo(name = "usage_location")
    val usageLocation: String? = null, // e.g., "Gedung A", "Gedung B"

    @ColumnInfo(name = "address_usage_location")
    val addressUsageLocation: String? = null, // e.g., "Jl. ABC No. 123", "Jl. XYZ No. 456"

    // --- Equipment Identification ---
    @ColumnInfo(name = "drive_type")
    val driveType: String? = null, // e.g., "Single Track", "Double Track, Gearless, Gasoline Forklift, Hydraulic Forklift"

    @ColumnInfo(name = "serial_number")
    val serialNumber: String? = null, // e.g., "123456789", "987654321, ELEV-JKT-2021-007, XXXXX-XX-JKT"

    @ColumnInfo(name = "permit_number")
    val permitNumber: String? = null, // e.g., "SKP-123/M/DJPPK/VII/2023"

    @ColumnInfo(name = "capacity")
    val capacity: String? = null, // e.g., "1250 kg / 18 orang, 5000 kg"

    @ColumnInfo(name = "speed")
    val speed: String? = null,

    @ColumnInfo(name = "floor_served")
    val floorServed: String? = null,

    // --- Manufacturer Data ---
    @Embedded(prefix = "manufacturer_")
    val manufacturer: Manufacturer? = null,

    // --- Report Metadata ---
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "report_date")
    val reportDate: String? = null,

    @ColumnInfo(name = "next_inspection_date")
    val nextInspectionDate: String? = null,

    @ColumnInfo(name = "inspector_name")
    val inspectorName: String? = null,

    @ColumnInfo(name = "status")
    val status: String? = null, // e.g., "LAIK", "Aman untuk dioperasikan"

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false
)

/**
 * A reusable data class for manufacturer information, embedded into the InspectionEntity table.
 * Mapped from 'manufacturer', 'manufacturerOrInstaller', etc.
 */
data class Manufacturer(
    val name: String? = null,
    @ColumnInfo(name = "brand_or_type")
    val brandOrType: String? = null,
    @ColumnInfo(name = "year_of_manufacture")
    val year: String? = null
)

/**
 * Table for visual inspectionEntity items or any checklist-style data.
 * This structure is highly flexible and can store checklist data from any JSON.
 * Example: `visualInspection.frame` from crane JSONs or `inspectionAndTesting.machineRoomAndMachinery.mechanicalBrake` from elevator.json
 */
@Entity(
    tableName = "inspection_check_items",
    foreignKeys = [ForeignKey(
        entity = InspectionEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class InspectionCheckItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @ColumnInfo(name = "category")
    val category: String, // e.g., "Machine Room", "Suspension Ropes"

    @ColumnInfo(name = "item_name")
    val itemName: String, // e.g., "Mechanical Brake", "Condition"

    @ColumnInfo(name = "status")
    val status: Boolean, // true for "OK/Good", false for "Fail/Bad"

    val result: String? = null // e.g., "Berfungsi dengan baik", "Tidak ada cacat"
)

/**
 * Table for findings and recommendations.
 * Mapped from 'conclusion.findings' or 'recommendations' arrays.
 */
@Entity(
    tableName = "inspection_findings",
    foreignKeys = [ForeignKey(
        entity = InspectionEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class InspectionFinding(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    val description: String,

    @ColumnInfo(name = "type")
    val type: FindingType // "FINDING" or "RECOMMENDATION"
)

enum class FindingType {
    FINDING,
    RECOMMENDATION
}

/**
 * Table for storing test results (e.g., dynamic, static, electrical).
 * Mapped from 'testing' or 'testResults' objects.
 */
@Entity(
    tableName = "inspection_test_results",
    foreignKeys = [ForeignKey(
        entity = InspectionEntity::class,
        parentColumns = ["id"],
        childColumns = ["inspection_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class InspectionTestResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "inspection_id", index = true)
    val inspectionId: Long,

    @ColumnInfo(name = "test_name")
    val testName: String, // e.g., "Dynamic Load Test", "Grounding Resistance"

    val result: String,

    val notes: String?
)


// --- RELATIONSHIP ENTITY ---

/**
 * This class defines the one-to-many relationship between an InspectionEntity
 * and its related data (CheckItems, Findings, TestResults).
 * It allows fetching a complete inspectionEntity report with a single query.
 */
data class InspectionWithDetails(
    @Embedded
    val inspectionEntity: InspectionEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "inspection_id"
    )
    val checkItems: List<InspectionCheckItem>,

    @Relation(
        parentColumn = "id",
        entityColumn = "inspection_id"
    )
    val findings: List<InspectionFinding>,

    @Relation(
        parentColumn = "id",
        entityColumn = "inspection_id"
    )
    val testResults: List<InspectionTestResult>
)