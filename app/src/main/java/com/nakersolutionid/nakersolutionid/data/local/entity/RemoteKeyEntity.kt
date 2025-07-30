package com.nakersolutionid.nakersolutionid.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "remote_keys",
    // 👇 ADD THIS FOREIGN KEY DEFINITION
    foreignKeys = [
        ForeignKey(
            entity = InspectionEntity::class,
            parentColumns = ["rowid"],
            childColumns = ["inspectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RemoteKeyEntity(
    @PrimaryKey val inspectionId: Long,
    val prevKey: Int?,
    val nextKey: Int?,
    val createdAt: Long = System.currentTimeMillis()
)
