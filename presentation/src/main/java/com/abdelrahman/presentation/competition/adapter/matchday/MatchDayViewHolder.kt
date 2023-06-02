package com.abdelrahman.presentation.competition.adapter.matchday

import android.graphics.drawable.ColorDrawable
import androidx.core.content.res.ResourcesCompat
import com.abdelrahman.entity.MatchDay
import com.abdelrahman.presentation.R
import com.abdelrahman.presentation.base.BaseViewHolder
import com.abdelrahman.presentation.databinding.ItemMatchdayBinding

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */

class MatchDayViewHolder(
  private val itemMatchDayHolder: ItemMatchdayBinding,
  private val onItemSelected: (Int) -> Unit
) : BaseViewHolder<MatchDay>(itemMatchDayHolder) {

  override fun bind(t: MatchDay, isLast: Boolean) {

    itemView.setOnClickListener {
      onItemSelected.invoke(t.day)
    }
    itemMatchDayHolder.apply {
      if (t.isSelected)
        txtMatchDay.background =
          ColorDrawable(ResourcesCompat.getColor(itemView.context.resources, R.color.white, null))
      else
        txtMatchDay.background =
          ColorDrawable(ResourcesCompat.getColor(itemView.context.resources, R.color.grey, null))
      txtMatchDay.text = itemView.context.getString(R.string.game_week, t.day.toString())

    }
  }
}
