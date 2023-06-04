package com.abdelrahman.presentation.home

import com.abdelrahman.presentation.base.viewmodel.BaseViewModel
import com.abdelrahman.presentation.home.HomeContract.Effect
import com.abdelrahman.presentation.home.HomeContract.Event
import com.abdelrahman.presentation.home.HomeContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<Event, State, Effect>() {

  override fun createState(): State {
    return State()
  }

  override fun onExceptionThrown(throwable: Throwable) {}

  override fun handleEvent(event: Event) {}
}