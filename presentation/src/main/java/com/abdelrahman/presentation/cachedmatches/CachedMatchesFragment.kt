@file:Suppress("UNCHECKED_CAST")

package com.abdelrahman.presentation.cachedmatches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.R
import com.abdelrahman.presentation.base.fragment.BaseFragment
import com.abdelrahman.presentation.base.viewmodel.BaseViewModel
import com.abdelrahman.presentation.base.viewmodel.UiEffect
import com.abdelrahman.presentation.base.viewmodel.UiEvent
import com.abdelrahman.presentation.base.viewmodel.UiState
import com.abdelrahman.presentation.cachedmatches.CachedMatchContract.Event
import com.abdelrahman.presentation.cachedmatches.CachedMatchContract.State
import com.abdelrahman.presentation.cachedmatches.adapter.CachedMatchesAdapter
import com.abdelrahman.presentation.databinding.FragmentCachedMatchesBinding
import com.abdelrahman.presentation.utils.gone
import com.abdelrahman.presentation.utils.visible
import dagger.hilt.android.AndroidEntryPoint

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@AndroidEntryPoint
class CachedMatchesFragment : BaseFragment<FragmentCachedMatchesBinding>() {

  companion object {
    fun newInstance() = CachedMatchesFragment()
  }

  private val cachedMatchesViewModel by viewModels<CachedMatchesViewModel>()
  private lateinit var mCachedMatchesAdapter: CachedMatchesAdapter

  override val viewModel: BaseViewModel<UiEvent, UiState, UiEffect>
    get() = cachedMatchesViewModel as BaseViewModel<UiEvent, UiState, UiEffect>

  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCachedMatchesBinding
    get() = FragmentCachedMatchesBinding::inflate

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initAdapter()
    viewModel.setEvent(Event.GetCachedMatches)
  }

  private fun initAdapter() {
    mCachedMatchesAdapter = CachedMatchesAdapter()
    binding.rvCachedMatches.adapter = mCachedMatchesAdapter
  }

  override fun renderState(state: UiState) {
    val cachedMatchState = state as State
    if (!cachedMatchState.matches.isNullOrEmpty())
      submitList(cachedMatchState.matches)
    else
      if (cachedMatchState.isError)
        onNoItemsFound()
  }

  private fun submitList(matches: List<Match>) {
    mCachedMatchesAdapter.setItems(matches)
    binding.txtNoMatches.gone()
    binding.rvCachedMatches.visible()
  }

  private fun onNoItemsFound() {
    binding.txtNoMatches.visible()
    binding.rvCachedMatches.gone()
  }

  override fun renderEffects(effect: UiEffect) {
  }

  override fun onErrorHappens(throwable: Throwable) {
    Toast.makeText(requireContext(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG)
      .show()
  }
}