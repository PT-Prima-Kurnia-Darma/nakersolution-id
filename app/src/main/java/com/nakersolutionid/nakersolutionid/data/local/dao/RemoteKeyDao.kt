package com.nakersolutionid.nakersolutionid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nakersolutionid.nakersolutionid.data.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert
    suspend fun insertKey(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys ORDER BY id DESC LIMIT 1")
    suspend fun getLatestKey(): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearAllRemoteKeys()
}
