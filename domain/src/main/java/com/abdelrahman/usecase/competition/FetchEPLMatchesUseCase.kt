package com.abdelrahman.usecase.competition

import com.abdelrahman.DataState
import com.abdelrahman.entity.Competition
import com.abdelrahman.models.toListOfMatchEntity
import com.abdelrahman.repository.IEPLMatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class FetchEPLMatchesUseCase @Inject constructor(
  private val iEPLMatchesRepository: IEPLMatchesRepository
) : IFetchEPLMatchesUseCase {

  override suspend fun fetchEPLMatches(id: Int): Flow<DataState<Competition>> {
    return iEPLMatchesRepository.fetchEPLMatches(1).map { remoteState ->
      when (remoteState) {
        is DataState.SuccessState -> {
          remoteState.data.let { competition ->
            DataState.SuccessState(
              Competition(
                competition.matches.toListOfMatchEntity() ?: arrayListOf(),
                competition.matches?.map { match ->
                  match.matchday ?: -1
                }?.distinct() ?: arrayListOf()
              )
            )
          }
        }

        is DataState.ErrorState -> DataState.ErrorState(remoteState.errorTypes)
      }
    }

  }
}