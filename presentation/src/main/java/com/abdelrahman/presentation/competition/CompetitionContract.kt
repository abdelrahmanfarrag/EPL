package com.abdelrahman.presentation.competition

import com.abdelrahman.DataState
import com.abdelrahman.entity.GroupedMatches
import com.abdelrahman.entity.MatchDay
import com.abdelrahman.presentation.base.viewmodel.UiEffect
import com.abdelrahman.presentation.base.viewmodel.UiEvent
import com.abdelrahman.presentation.base.viewmodel.UiState
import java.util.SortedMap

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class CompetitionContract {

  data class State(
    val isLoading: Boolean = true,
    val competitionState: DataState<SortedMap<MatchDay, List<GroupedMatches>>>? = null,
    val matchesMap: SortedMap<MatchDay, List<GroupedMatches>>? = null,
    val matchDays: List<MatchDay>? = null,
    val groupedMatches: List<GroupedMatches>? = null
  ) : UiState

  sealed class Event : UiEvent {
    object GetMatches : Event()
    data class GameWeekSelected(val gameWeek: Int) : Event()
  }

  sealed class Effect : UiEffect {
    data class ServerRespondsWithErrorHappened(val message: String?) : Effect()
    object NetworkErrorHappened : Effect()
    object UnAuthorizedErrorHappened : Effect()
    object UnKnownErrorHappens : Effect()
  }
}