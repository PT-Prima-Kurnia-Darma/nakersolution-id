package com.nakersolutionid.nakersolutionid.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nakersolutionid.nakersolutionid.data.local.dao.ReportDao
import com.nakersolutionid.nakersolutionid.data.local.entity.Converters
import com.nakersolutionid.nakersolutionid.data.local.entity.ReportEntity

@Database(entities = [ReportEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReportDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao
}