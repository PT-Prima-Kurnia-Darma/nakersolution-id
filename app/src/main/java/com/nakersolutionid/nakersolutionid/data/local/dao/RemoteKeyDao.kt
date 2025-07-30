package com.nakersolutionid.nakersolutionid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nakersolutionid.nakersolutionid.data.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE inspectionId = :id")
    suspend fun getRemoteKeyByInspectionId(id: Long): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT createdAt FROM remote_keys ORDER BY createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}
