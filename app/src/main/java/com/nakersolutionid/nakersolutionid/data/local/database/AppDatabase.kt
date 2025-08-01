package com.nakersolutionid.nakersolutionid.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nakersolutionid.nakersolutionid.data.local.dao.InspectionDao
import com.nakersolutionid.nakersolutionid.data.local.dao.RemoteKeyDao
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionCheckItem
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionEntity
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionFinding
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionFtsEntity
import com.nakersolutionid.nakersolutionid.data.local.entity.InspectionTestResult
import com.nakersolutionid.nakersolutionid.data.local.entity.RemoteKeyEntity

@Database(
    entities = [
        InspectionEntity::class,
        InspectionCheckItem::class,
        InspectionFinding::class,
        InspectionTestResult::class,
        InspectionFtsEntity::class,
        RemoteKeyEntity::class
    ],
    version = 4,
    exportSchema = true // Set to true for production apps to export schema for migration testing
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inspectionDao(): InspectionDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}