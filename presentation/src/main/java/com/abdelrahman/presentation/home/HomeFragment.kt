package com.abdelrahman.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.abdelrahman.presentation.R
import com.abdelrahman.presentation.base.fragment.BaseFragment
import com.abdelrahman.presentation.base.viewmodel.BaseViewModel
import com.abdelrahman.presentation.base.viewmodel.UiEffect
import com.abdelrahman.presentation.base.viewmodel.UiEvent
import com.abdelrahman.presentation.base.viewmodel.UiState
import com.abdelrahman.presentation.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  private val homeViewModel by viewModels<HomeViewModel>()
  override val viewModel: BaseViewModel<UiEvent, UiState, UiEffect>
    get() = homeViewModel as BaseViewModel<UiEvent, UiState, UiEffect>

  override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
    get() = FragmentHomeBinding::inflate

  override fun renderState(state: UiState) {

  }

  override fun initViews() {
    val adapter = TabAdapter(requireActivity())
    binding.viewPager.adapter = adapter
    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
      tab.text = when (position) {
        0 -> getString(R.string.competition)
        1 -> getString(R.string.offline)
        else -> getString(R.string.competition)
      }

    }.attach()


  }

  override fun renderEffects(effect: UiEffect) {}

  override fun onErrorHappens(throwable: Throwable) {
    Toast.makeText(requireContext(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG)
      .show()
  }
}