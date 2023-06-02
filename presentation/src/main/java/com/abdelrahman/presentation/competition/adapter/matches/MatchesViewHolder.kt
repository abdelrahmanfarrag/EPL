package com.abdelrahman.presentation.competition.adapter.matches

import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.base.BaseViewHolder
import com.abdelrahman.presentation.databinding.ItemSingleMatchBinding

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class MatchesViewHolder(
  private val itemSingleMatchHolder: ItemSingleMatchBinding
) : BaseViewHolder<Match>(itemSingleMatchHolder) {

  override fun bind(t: Match, isLast: Boolean) {
    itemSingleMatchHolder.apply {
      txtAwayTeamName.text = t.awayTeamName
      txtAwayTeamScore.text = t.awayTeamScore.toString()
      txtHomeTeamName.text = t.homeTeamName
      txtHomeTeamScore.text = t.homeTeamScore.toString()
    }
  }
}