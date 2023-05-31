package com.abdelrahman.presentation.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.abdelrahman.presentation.base.viewmodel.BaseViewModel
import com.abdelrahman.presentation.base.viewmodel.UiEffect
import com.abdelrahman.presentation.base.viewmodel.UiEvent
import com.abdelrahman.presentation.base.viewmodel.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
abstract class BaseFragment<out BINDING : ViewBinding> : Fragment() {

  abstract val viewModel: BaseViewModel<UiEvent, UiState, UiEffect>
  private var mBinding: BINDING? = null
  private val mCoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    onErrorHappens(throwable)
  }
  private val mJob = Job()
  protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING
  protected val binding: BINDING
    get() = mBinding as BINDING

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mBinding = bindingInflater.invoke(inflater, container, false)
    return requireNotNull(mBinding).root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViews()
    initClickListeners()
    collectState()
  }
  private fun collectState() {
     lifeCycleStartedScope(mJob, mCoroutineExceptionHandler) {
      viewModel.uiState.collect { state ->
        renderState(state)
      }
    }
  }

  override fun onDestroyView() {
    mJob.cancel()
    super.onDestroyView()
  }

  open fun initViews() {}
  open fun initClickListeners() {}
  abstract fun renderState(state: UiState)
  abstract fun onErrorHappens(throwable: Throwable)
}