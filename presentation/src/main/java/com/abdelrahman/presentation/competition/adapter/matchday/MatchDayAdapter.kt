package com.abdelrahman.presentation.competition.adapter.matchday

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.viewbinding.ViewBinding
import com.abdelrahman.entity.MatchDay
import com.abdelrahman.presentation.base.RecyclerAdapter
import com.abdelrahman.presentation.databinding.ItemMatchdayBinding

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */

class MatchDayAdapter : RecyclerAdapter<MatchDay, MatchDayViewHolder>() {

  private lateinit var onItemClicked: (Int) -> Unit

  override fun instantiateViewHolder(
    itemView: ViewBinding,
    viewType: Int
  ) = MatchDayViewHolder(itemView as ItemMatchdayBinding, onItemClicked)

  override fun generateAsyncDifferCallback(): ItemCallback<MatchDay> {
    return object : ItemCallback<MatchDay>() {
      override fun areItemsTheSame(
        oldItem: MatchDay,
        newItem: MatchDay
      ): Boolean {
        return oldItem.equals(newItem)
      }

      override fun areContentsTheSame(
        oldItem: MatchDay,
        newItem: MatchDay
      ): Boolean {
        return oldItem.day == newItem.day
      }
    }
  }


  override fun generatedBindingLayout(
    inflater: LayoutInflater,
    parent: ViewGroup,
    attachToRoot: Boolean
  ) = ItemMatchdayBinding.inflate(inflater, parent, attachToRoot)

  override fun setItems(items: List<MatchDay>) {
    differAsync.submitList(items)
  }

  override fun onBindViewHolder(holder: MatchDayViewHolder, position: Int) {
    holder.bind(differAsync.currentList[position])
  }

  fun setOnItemClicked(onItemClicked: (Int) -> Unit) {
    this.onItemClicked = onItemClicked
  }

}