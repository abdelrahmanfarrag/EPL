package com.abdelrahman.presentation.cachedmatches

import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.base.viewmodel.UiEffect
import com.abdelrahman.presentation.base.viewmodel.UiEvent
import com.abdelrahman.presentation.base.viewmodel.UiState

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class CachedMatchContract {

  data class State(
    val matches: List<Match>? = null,
    val isError: Boolean = false
  ) : UiState

  sealed class Event : UiEvent {
    object GetCachedMatches : Event()
    object XYZ : Event()
  }

  sealed class Effect : UiEffect {
    object ErrorHappens : Effect()
  }

}