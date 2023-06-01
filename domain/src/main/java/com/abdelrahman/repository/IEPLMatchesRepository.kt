package com.abdelrahman.repository

import com.abdelrahman.DataState
import com.abdelrahman.models.Competition
import kotlinx.coroutines.flow.Flow

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
interface IEPLMatchesRepository {

  suspend fun fetchEPLMatches(id:Int): Flow<DataState<Competition>>
}