package com.abdelrahman.presentation.competition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.abdelrahman.DataState
import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.base.fragment.BaseFragment
import com.abdelrahman.presentation.base.viewmodel.BaseViewModel
import com.abdelrahman.presentation.base.viewmodel.UiEffect
import com.abdelrahman.presentation.base.viewmodel.UiEvent
import com.abdelrahman.presentation.base.viewmodel.UiState
import com.abdelrahman.presentation.competition.CompetitionContract.State
import com.abdelrahman.presentation.databinding.FragmentCompetitionBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class CompetitionFragment : BaseFragment<FragmentCompetitionBinding>() {

  private val competitionViewModel by viewModels<CompetitionViewModel>()

  override val viewModel: BaseViewModel<UiEvent, UiState, UiEffect>
    get() = competitionViewModel as BaseViewModel<UiEvent, UiState, UiEffect>
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCompetitionBinding
    get() = FragmentCompetitionBinding::inflate

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    collect()
  }
  private fun collect(){
    lifecycleScope.launchWhenStarted {
      competitionViewModel.uiState.collect{state->
        when(state.competitionState){
          is DataState.SuccessState ->{
          }
          else->{}
        }
      }
    }
  }
  override fun renderState(state: UiState) {
      //val current = ((state as State).competitionState) as DataState.SuccessState<HashMap<Int,List<Match>>>
  binding.txtsss.text ="${state}"
  }

  override fun onErrorHappens(throwable: Throwable) {
  }
}