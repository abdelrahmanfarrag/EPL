package com.abdelrahman.presentation.competition.adapter.matches

import androidx.core.content.res.ResourcesCompat
import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.R
import com.abdelrahman.presentation.base.BaseViewHolder
import com.abdelrahman.presentation.databinding.ItemSingleMatchBinding
import com.abdelrahman.presentation.utils.Constants
import com.abdelrahman.presentation.utils.gone
import com.abdelrahman.presentation.utils.visible

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class MatchesViewHolder(
  private val itemSingleMatchHolder: ItemSingleMatchBinding,
  private val onMatchClickListener: (Match) -> Unit
) : BaseViewHolder<Match>(itemSingleMatchHolder) {

  override fun bind(t: Match, isLast: Boolean) {
    itemSingleMatchHolder.apply {
      txtAwayTeamName.text = t.awayTeamName
      txtHomeTeamName.text = t.homeTeamName
      if (t.status == Constants.STATUS_FINISHED) {
        txtAwayTeamScore.text = t.awayTeamScore.toString()
        txtHomeTeamScore.text = t.homeTeamScore.toString()
        groupMatchScore.visible()
        txtMatchTime.gone()
      } else {
        groupMatchScore.gone()
        txtMatchTime.visible()
        txtMatchTime.text = t.matchTime
      }
      imgFavorite.setOnClickListener {
        onMatchClickListener.invoke(t)
      }
      if (t.isFavorite)
        imgFavorite.background =
          ResourcesCompat.getDrawable(itemView.context.resources, R.drawable.ic_favortied, null)
      else
        ResourcesCompat.getDrawable(itemView.context.resources, R.drawable.ic_not_favorite, null)

    }
  }
}