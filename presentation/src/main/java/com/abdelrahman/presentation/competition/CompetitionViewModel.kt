package com.abdelrahman.presentation.competition

import androidx.lifecycle.viewModelScope
import com.abdelrahman.DataState
import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.base.viewmodel.BaseViewModel
import com.abdelrahman.presentation.competition.CompetitionContract.Event
import com.abdelrahman.presentation.competition.CompetitionContract.State
import com.abdelrahman.usecase.competition.IFetchEPLMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@HiltViewModel
class CompetitionViewModel @Inject constructor(private val iFetchEPLMatchesUseCase: IFetchEPLMatchesUseCase) :
  BaseViewModel<Event, State, CompetitionContract.Effect>() {

  init {
    setEvent(Event.GetMatches)
  }

  override fun createState(): State {
    return State()
  }

  override fun onExceptionThrown() {
  }

  override fun handleEvent(event: Event) {
    when (event) {
      Event.GetMatches -> getMatches()
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
    viewModelScope.launch(Dispatchers.IO) {
      iFetchEPLMatchesUseCase.fetchEPLMatches(2021).onStart {
        toggleLoading(true)
      }.onCompletion {
        toggleLoading(false)
      }.collectLatest {
        setState {
          copy(
            competitionState = it
          )
        }
      }
    }
  }

}