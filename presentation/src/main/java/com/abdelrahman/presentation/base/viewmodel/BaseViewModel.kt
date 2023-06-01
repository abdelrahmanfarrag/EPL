package com.abdelrahman.presentation.base.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {

  private val initialUIState by lazy { createState() }

  abstract fun createState(): State

  abstract fun handleEvent(event: Event)

  abstract fun onExceptionThrown()

  private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialUIState)
  val uiState = _uiState.asStateFlow()

  val currentState: State
    get() = _uiState.value

  private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
  val event = _event.asSharedFlow()

  private val _effect: Channel<Effect> = Channel()
  var effect = _effect.receiveAsFlow()

  init {
    subscribeToEvents()
  }

  private fun subscribeToEvents() {
    launch(onErrorHappens = { onExceptionThrown() }) {
      event.collect { event ->
        handleEvent(event)
      }
    }
  }

   fun setEvent(event: Event) {
      val newEvent = event
    launch({ onExceptionThrown() }) {
      _event.emit(newEvent)
    }
  }

  protected fun setState(reduce: State.() -> State) {
    val newState = currentState.reduce()
    _uiState.value = newState
  }

  protected fun setEffect(effect: () -> Effect) {
    val newEffect = effect()
    launch({ onExceptionThrown() }) {
      _effect.send(newEffect)
    }
  }

  override fun onCleared() {
    super.onCleared()
  }
}