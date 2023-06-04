package com.abdelrahman.presentation.cachedmatches

import com.abdelrahman.DataState
import com.abdelrahman.presentation.base.viewmodel.BaseViewModel
import com.abdelrahman.presentation.cachedmatches.CachedMatchContract.Effect
import com.abdelrahman.presentation.cachedmatches.CachedMatchContract.Event
import com.abdelrahman.presentation.cachedmatches.CachedMatchContract.State
import com.abdelrahman.usecase.getcachedmatches.IGetCachedMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@HiltViewModel
class CachedMatchesViewModel @Inject constructor(private val iGetCachedMatchesUseCase: IGetCachedMatchesUseCase) :
  BaseViewModel<Event, State, Effect>() {

  override fun createState(): State {
    return State()
  }

  override fun onExceptionThrown(throwable: Throwable) {
    setEffect { Effect.ErrorHappens }
  }

  override fun handleEvent(event: Event) {
    when (event) {
      is Event.GetCachedMatches -> getCachedMatches()

      else -> {}
    }
  }

  private fun getCachedMatches() {
    launchCoroutine {
      iGetCachedMatchesUseCase.getAllSavedMatches().collect { dataState ->
        when (dataState) {
          is DataState.SuccessState -> setState {
            copy(matches = dataState.data)
          }

          else -> setState {
            copy(isError = true)
          }
        }
      }
    }
  }
}