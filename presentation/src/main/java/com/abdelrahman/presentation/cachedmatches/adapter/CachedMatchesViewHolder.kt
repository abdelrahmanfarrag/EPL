package com.abdelrahman.presentation.cachedmatches.adapter

import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.R
import com.abdelrahman.presentation.base.BaseViewHolder
import com.abdelrahman.presentation.databinding.ItemCachedMatchesBinding

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class CachedMatchesViewHolder(private val itemCachedMatchesBinding: ItemCachedMatchesBinding) :
  BaseViewHolder<Match>(itemCachedMatchesBinding) {

  override fun bind(t: Match, isLast: Boolean) {
    itemCachedMatchesBinding.apply {
      txtAwayTeamName.text = t.awayTeamName
      txtAwayTeamScore.text = t.awayTeamScore.toString()
      txtHomeTeamName.text = t.homeTeamName
      txtHomeTeamScore.text = t.homeTeamScore.toString()
      txtGameWeek.text =
        this.txtGameWeek.context.getString(R.string.game_week, t.matchDay.toString())
      txtDate.text = t.matchDate
    }
  }
}