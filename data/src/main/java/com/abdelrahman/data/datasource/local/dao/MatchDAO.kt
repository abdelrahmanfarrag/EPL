package com.abdelrahman.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.abdelrahman.data.datasource.local.models.MatchDb
import kotlinx.coroutines.flow.Flow

/**
 * Authored by Abdelrahman Ahmed on 03 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Dao
interface MatchDAO {

  @Insert(onConflict = REPLACE)
  suspend fun upsertMatch(matchDb: MatchDb): Long

  @Query("DELETE FROM `Match` WHERE matchId = :id")
  suspend fun deleteMatch(id: Int?)

  @Query("SELECT * FROM `Match` ORDER BY id ASC")
  fun getStoredMatches(): Flow<List<MatchDb>>
}