package com.abdelrahman.usecase.getcachedmatches

import com.abdelrahman.DataState
import com.abdelrahman.entity.Match
import com.abdelrahman.repository.ICachedMatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class GetCachedMatchesUseCase @Inject constructor(private val iCachedMatchesRepository: ICachedMatchesRepository) :
  IGetCachedMatchesUseCase {

  override suspend fun getAllSavedMatches(): Flow<DataState<List<Match>>> {
    return iCachedMatchesRepository.getAllSavedMatches().map { state ->
      return@map if (state is DataState.SuccessState)
        state.copy(
          data = state.data.sortedByDescending { it.matchDay }
        )
      else
        state
    }
  }
}