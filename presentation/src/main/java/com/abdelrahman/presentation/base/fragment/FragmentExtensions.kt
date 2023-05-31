package com.abdelrahman.presentation.base.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
fun Fragment.lifeCycleStartedScope(
  job: Job,
  context: CoroutineContext,
  block: suspend CoroutineScope.() -> Unit
): Job {
  val scope = CoroutineScope(Dispatchers.Main + job + context)
  return scope.launch {
    lifecycle.whenStarted {
      block()
    }
  }

}