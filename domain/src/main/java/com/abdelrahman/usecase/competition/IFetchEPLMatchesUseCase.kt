package com.abdelrahman.usecase.competition

import com.abdelrahman.DataState
import com.abdelrahman.entity.GroupedMatches
import com.abdelrahman.entity.MatchDay
import kotlinx.coroutines.flow.Flow
import java.util.SortedMap

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
interface IFetchEPLMatchesUseCase {
  suspend fun fetchEPLMatches(id: Int): Flow<DataState<SortedMap<MatchDay, List<GroupedMatches>>>>
}