package com.abdelrahman.presentation.competition.adapter.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.viewbinding.ViewBinding
import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.base.RecyclerAdapter
import com.abdelrahman.presentation.databinding.ItemSingleMatchBinding

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class MatchesAdapter(private val onMatchClickListener: (Match) -> Unit) :
  RecyclerAdapter<Match, MatchesViewHolder>() {

  override fun instantiateViewHolder(
    itemView: ViewBinding,
    viewType: Int
  ) = MatchesViewHolder(itemView as ItemSingleMatchBinding, onMatchClickListener)

  override fun generateAsyncDifferCallback(): ItemCallback<Match> {

    return object : ItemCallback<Match>() {
      override fun areItemsTheSame(
        oldItem: Match,
        newItem: Match
      ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
      }

      override fun areContentsTheSame(
        oldItem: Match,
        newItem: Match
      ): Boolean {
        return oldItem == newItem
      }
    }
  }


  override fun generatedBindingLayout(
    inflater: LayoutInflater,
    parent: ViewGroup,
    attachToRoot: Boolean
  ) = ItemSingleMatchBinding.inflate(inflater, parent, attachToRoot)

  override fun setItems(items: List<Match>) {
    differAsync.submitList(items)
  }

  override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
    holder.bind(differAsync.currentList[position])
  }

}