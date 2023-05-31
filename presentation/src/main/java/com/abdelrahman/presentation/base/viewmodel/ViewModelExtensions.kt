package com.abdelrahman.presentation.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */

fun ViewModel.launch(onErrorHappens: (Throwable) -> Unit, func: suspend CoroutineScope.() -> Unit) {
  viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
    onErrorHappens(throwable)
  }) {
    func()
  }
}