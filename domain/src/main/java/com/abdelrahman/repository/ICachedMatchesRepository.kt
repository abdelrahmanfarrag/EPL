package com.abdelrahman.repository

import com.abdelrahman.DataState
import com.abdelrahman.entity.Match
import kotlinx.coroutines.flow.Flow

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
interface ICachedMatchesRepository {
  suspend fun getAllSavedMatches(): Flow<DataState<List<Match>>>
}