package com.abdelrahman.presentation.competition

import com.abdelrahman.DataState
import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.base.viewmodel.UiEffect
import com.abdelrahman.presentation.base.viewmodel.UiEvent
import com.abdelrahman.presentation.base.viewmodel.UiState

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class CompetitionContract {

  data class State(
    val isLoading: Boolean = true,
    val competitionState : DataState<HashMap<Int,List<Match>>>?=null
  ) : UiState

  sealed class Event : UiEvent {
    object GetMatches : Event()
  }

  sealed class Effect : UiEffect
}