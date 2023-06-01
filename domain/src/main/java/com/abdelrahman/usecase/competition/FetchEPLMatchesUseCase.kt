package com.abdelrahman.usecase.competition

import com.abdelrahman.DataState
import com.abdelrahman.entity.Match
import com.abdelrahman.models.Competition
import com.abdelrahman.models.toListOfMatchEntity
import com.abdelrahman.repository.IEPLMatchesRepository
import kotlinx.coroutines.flow.Flow
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

  override suspend fun fetchEPLMatches(id: Int): Flow<DataState<HashMap<Int, List<Match>>>> {
    return iEPLMatchesRepository.fetchEPLMatches(id).map { remoteState ->
      when (remoteState) {
        is DataState.SuccessState -> {
          remoteState.data.let { competition ->
            DataState.SuccessState(
              getMatches(competition)
            )
          }
        }

        is DataState.ErrorState -> DataState.ErrorState(remoteState.errorTypes)
      }
    }
  }

  private fun getMatches(competition: Competition): HashMap<Int, List<Match>> {
    val map = hashMapOf<Int, List<Match>>()
    val matchEntityList = competition.matches?.toListOfMatchEntity() ?: arrayListOf()
    for (match in matchEntityList) {
      val matchDay = match.matchDay ?: -1
      val listOfMatches = matchEntityList.filter {
        it.matchDay == matchDay
      }
      map[matchDay] = listOfMatches
    }

    return map.apply {
      keys.sortedByDescending {
        it
      }
    }
  }
}