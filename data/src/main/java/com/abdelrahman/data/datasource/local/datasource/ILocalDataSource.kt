package com.abdelrahman.data.datasource.local.datasource

import com.abdelrahman.data.datasource.local.models.MatchDb
import com.abdelrahman.entity.Match
import kotlinx.coroutines.flow.Flow

/**
 * Authored by Abdelrahman Ahmed on 03 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
interface ILocalDataSource {

  suspend fun upsertAMatch(match: Match): Long

  suspend fun deleteAMatch(id: Int?)

  suspend fun getAllSavedMatches(): Flow<List<MatchDb>>
}