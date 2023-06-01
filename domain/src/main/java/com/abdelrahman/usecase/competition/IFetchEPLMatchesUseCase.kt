package com.abdelrahman.usecase.competition

import com.abdelrahman.DataState
import com.abdelrahman.entity.Competition
import kotlinx.coroutines.flow.Flow

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
interface IFetchEPLMatchesUseCase {

  suspend fun fetchEPLMatches(id:Int):Flow<DataState<Competition>>
}