package com.abdelrahman.presentation.competition.adapter.matches.grouped

import com.abdelrahman.entity.GroupedMatches
import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.base.BaseViewHolder
import com.abdelrahman.presentation.competition.adapter.matches.MatchesAdapter
import com.abdelrahman.presentation.databinding.ItemGroupedMatchBinding

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class GroupedMatchesViewHolder(
  private val itemSingleMatchHolder: ItemGroupedMatchBinding,
  private val onMatchClickListener: (Match) -> Unit
) : BaseViewHolder<GroupedMatches>(itemSingleMatchHolder) {

  override fun bind(t: GroupedMatches, isLast: Boolean) {
    itemSingleMatchHolder.apply {
      txtMatchDate.text = t.matchDate
      val adapter = MatchesAdapter(onMatchClickListener)
      rvMatches.adapter = adapter
      adapter.setItems(t.listOfMatches)
    }

  }
}