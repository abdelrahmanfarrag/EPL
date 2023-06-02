package com.abdelrahman.presentation.competition

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.GeneralError
import com.abdelrahman.ErrorTypes.NetworkError
import com.abdelrahman.ErrorTypes.UnAuthorized
import com.abdelrahman.presentation.base.viewmodel.BaseViewModel
import com.abdelrahman.presentation.competition.CompetitionContract.Effect
import com.abdelrahman.presentation.competition.CompetitionContract.Event
import com.abdelrahman.presentation.competition.CompetitionContract.State
import com.abdelrahman.usecase.competition.IFetchEPLMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@HiltViewModel
class CompetitionViewModel @Inject constructor(private val iFetchEPLMatchesUseCase: IFetchEPLMatchesUseCase) :
  BaseViewModel<Event, State, Effect>() {

  init {
    setEvent(Event.GetMatches)
  }

  override fun createState(): State {
    return State()
  }

  override fun onExceptionThrown(throwable: Throwable) {
    setEffect { Effect.UnKnownErrorHappens }
  }

  override fun handleEvent(event: Event) {
    when (event) {
      is Event.GameWeekSelected -> getMatchesBasedOnGameWeekSelection(event.gameWeek)
      Event.GetMatches -> getMatches()
    }
  }

  private fun getMatchesBasedOnGameWeekSelection(gameWeek: Int) {
    setState {
      val matches = currentState.matchesMap?.keys?.find {
        it.day == gameWeek
      }
      val updatedMatches = currentState.matchDays?.map {
        if (it.day == gameWeek) {
          it.copy(isSelected = true)
        } else
          it.copy(isSelected = false)
      }
      copy(
        matchDays = updatedMatches,
        groupedMatches = currentState.matchesMap?.get(matches)
      )
    }

  }

  private fun toggleLoading(isLoading: Boolean) {
    setState {
      copy(
        isLoading = isLoading
      )
    }
  }

  private fun getMatches() {
    launchCoroutine {
      iFetchEPLMatchesUseCase.fetchEPLMatches(2021).onStart {
        toggleLoading(true)
      }.onCompletion {
        toggleLoading(false)
      }.collect { matchesState ->
        setState {
          copy(
            competitionState = matchesState
          )
        }
        when (matchesState) {
          is DataState.SuccessState -> setState {
            val firstItem = matchesState.data.keys.first()
            copy(
              matchDays = matchesState.data.keys.mapIndexed { index, matchDay ->
                if (index == 0) return@mapIndexed matchDay.copy(isSelected = true)
                else matchDay
              },
              matchesMap = matchesState.data,
              groupedMatches = matchesState.data[firstItem]
            )
          }

          is DataState.ErrorState -> handleErrorState(matchesState)
          is DataState.ServerErrorMessage -> setEffect {
            Effect.ServerRespondsWithErrorHappened(matchesState.message)
          }
        }
      }
    }
  }

  private fun handleErrorState(matchesState: DataState.ErrorState) {
    when (matchesState.errorTypes) {
      GeneralError -> setEffect {
        Effect.UnKnownErrorHappens
      }

      NetworkError -> setEffect {
        Effect.NetworkErrorHappened
      }

      UnAuthorized -> setEffect {
        Effect.UnAuthorizedErrorHappened
      }
    }
  }
}