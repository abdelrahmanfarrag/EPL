package com.abdelrahman.presentation.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abdelrahman.presentation.cachedmatches.CachedMatchesFragment
import com.abdelrahman.presentation.competition.CompetitionFragment

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */

class TabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

  override fun getItemCount(): Int {
    return 2
  }

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      0 -> CompetitionFragment.newInstance()
      1 -> CachedMatchesFragment.newInstance()
      else -> CompetitionFragment.newInstance()
    }
  }
}