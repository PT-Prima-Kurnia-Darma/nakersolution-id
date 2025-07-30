package com.nakersolutionid.nakersolutionid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val inspectionId: Long,
    val prevKey: Int?,
    val nextKey: Int?,
    val createdAt: Long = System.currentTimeMillis()
)
