package com.abdelrahman.presentation.competition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.abdelrahman.entity.GroupedMatches
import com.abdelrahman.entity.MatchDay
import com.abdelrahman.presentation.R
import com.abdelrahman.presentation.base.fragment.BaseFragment
import com.abdelrahman.presentation.base.viewmodel.BaseViewModel
import com.abdelrahman.presentation.base.viewmodel.UiEffect
import com.abdelrahman.presentation.base.viewmodel.UiEvent
import com.abdelrahman.presentation.base.viewmodel.UiState
import com.abdelrahman.presentation.competition.CompetitionContract.Effect
import com.abdelrahman.presentation.competition.CompetitionContract.Event
import com.abdelrahman.presentation.competition.CompetitionContract.State
import com.abdelrahman.presentation.competition.adapter.matchday.MatchDayAdapter
import com.abdelrahman.presentation.competition.adapter.matches.grouped.GroupedMatchesAdapter
import com.abdelrahman.presentation.databinding.FragmentCompetitionBinding
import com.abdelrahman.presentation.utils.gone
import com.abdelrahman.presentation.utils.visible
import dagger.hilt.android.AndroidEntryPoint

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class CompetitionFragment : BaseFragment<FragmentCompetitionBinding>() {

  private lateinit var mMatchDayAdapter: MatchDayAdapter
  private lateinit var mGroupedMatchesAdapter: GroupedMatchesAdapter
  private val competitionViewModel by viewModels<CompetitionViewModel>()
  override val viewModel: BaseViewModel<UiEvent, UiState, UiEffect>
    get() = competitionViewModel as BaseViewModel<UiEvent, UiState, UiEffect>
  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCompetitionBinding
    get() = FragmentCompetitionBinding::inflate

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initAdapters()
  }

  override fun renderState(state: UiState) {
    val competitionFragmentState = state as State
    bindLoading(competitionFragmentState.isLoading)
    val matchDays = competitionFragmentState.matchDays
    val groupedMatches = competitionFragmentState.groupedMatches
    bindMatchDayAdapter(matchDays)
    bindGroupedMatches(groupedMatches)
  }

  private fun bindGroupedMatches(groupedMatches: List<GroupedMatches>?) {
    if (!groupedMatches.isNullOrEmpty()) {
      mGroupedMatchesAdapter.setItems(groupedMatches)
    }
  }

  private fun initAdapters() {
    mMatchDayAdapter = MatchDayAdapter()
    mGroupedMatchesAdapter = GroupedMatchesAdapter()

    mMatchDayAdapter.setOnItemClicked { gameWeek ->
      viewModel.setEvent(Event.GameWeekSelected(gameWeek))
    }
    binding.rvMatchDay.apply {
      adapter = mMatchDayAdapter
      itemAnimator = null
    }
    binding.rvMatches.apply {
      adapter = mGroupedMatchesAdapter
      itemAnimator = null
    }
  }

  override fun renderEffects(effect: UiEffect) {
    when (effect) {
      is Effect.ServerRespondsWithErrorHappened -> collectErrorEffects(effect.message)
      Effect.UnAuthorizedErrorHappened -> collectErrorEffects(getString(R.string.you_are_not_authorized))
      Effect.UnKnownErrorHappens -> collectErrorEffects(getString(R.string.something_went_wrong))
      Effect.NetworkErrorHappened -> collectErrorEffects(getString(R.string.internet_connection))
    }
  }

  override fun initClickListeners() {
    onRetryClickListener()
  }

  private fun onRetryClickListener() {
    binding.layoutError.btnTryAgain.setOnClickListener {
      binding.layoutError.constraintError.gone()
      binding.grpCompetitionSuccess.visible()
      viewModel.setEvent(Event.GetMatches)
    }
  }

  override fun onErrorHappens(throwable: Throwable) {
    collectErrorEffects(getString(R.string.something_went_wrong))
  }

  private fun bindMatchDayAdapter(matchDays: List<MatchDay>?) {
    if (!matchDays.isNullOrEmpty()) {
      mMatchDayAdapter.setItems(matchDays)
    }
  }

  private fun bindLoading(isLoading: Boolean) {
    if (isLoading)
      binding.layoutProgress.viewProgress.visible()
    else
      binding.layoutProgress.viewProgress.gone()
  }

  private fun collectErrorEffects(message: String?) {
    binding.layoutError.constraintError.visible()
    binding.grpCompetitionSuccess.gone()
    binding.layoutError.txtError.text = message
  }
}