package com.abdelrahman.usecase.competition

import com.abdelrahman.DataState
import com.abdelrahman.entity.GroupedMatches
import com.abdelrahman.entity.MatchDay
import com.abdelrahman.models.Competition
import com.abdelrahman.models.toListOfMatchEntity
import com.abdelrahman.repository.IEPLMatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.SortedMap
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class FetchEPLMatchesUseCase @Inject constructor(
  private val iEPLMatchesRepository: IEPLMatchesRepository
) : IFetchEPLMatchesUseCase {

  override suspend fun fetchEPLMatches(id: Int): Flow<DataState<SortedMap<MatchDay, List<GroupedMatches>>>> {
    return iEPLMatchesRepository.fetchEPLMatches(id).map { remoteState ->
      when (remoteState) {
        is DataState.SuccessState -> {
          remoteState.data.let { competition ->
            DataState.SuccessState(
              getMatches(competition), remoteState.isOfflineMode
            )
          }
        }

        is DataState.ServerErrorMessage -> DataState.ServerErrorMessage(remoteState.message)
        is DataState.ErrorState -> DataState.ErrorState(remoteState.errorTypes)
      }
    }
  }

  private fun getMatches(competition: Competition): SortedMap<MatchDay, List<GroupedMatches>> {
    val map = hashMapOf<MatchDay, List<GroupedMatches>>()
    val matchEntityList = competition.matches?.toListOfMatchEntity() ?: arrayListOf()
    for (match in matchEntityList) {
      val matchDay = MatchDay(match.matchDay ?: -1)
      val listOfMatches = matchEntityList.filter {
        it.matchDay == matchDay.day
      }.groupBy {
        it.matchTime
      }.map {
        GroupedMatches(it.key ?: "-", it.value)
      }
      map[matchDay] = listOfMatches
    }

    return map.toSortedMap(compareByDescending {
      it.day
    })
  }
}