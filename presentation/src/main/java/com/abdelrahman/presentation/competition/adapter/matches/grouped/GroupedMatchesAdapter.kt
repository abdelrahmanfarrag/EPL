package com.abdelrahman.presentation.competition.adapter.matches.grouped

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.viewbinding.ViewBinding
import com.abdelrahman.entity.GroupedMatches
import com.abdelrahman.presentation.base.RecyclerAdapter
import com.abdelrahman.presentation.databinding.ItemGroupedMatchBinding

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class GroupedMatchesAdapter : RecyclerAdapter<GroupedMatches, GroupedMatchesViewHolder>() {

  override fun instantiateViewHolder(
    itemView: ViewBinding,
    viewType: Int
  ) = GroupedMatchesViewHolder(itemView as ItemGroupedMatchBinding)

  override fun generateAsyncDifferCallback(): ItemCallback<GroupedMatches> {

    return object : ItemCallback<GroupedMatches>() {
      override fun areItemsTheSame(
        oldItem: GroupedMatches,
        newItem: GroupedMatches
      ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
      }

      override fun areContentsTheSame(
        oldItem: GroupedMatches,
        newItem: GroupedMatches
      ): Boolean {
        return oldItem == newItem
      }
    }
  }


  override fun generatedBindingLayout(
    inflater: LayoutInflater,
    parent: ViewGroup,
    attachToRoot: Boolean
  ) = ItemGroupedMatchBinding.inflate(inflater, parent, attachToRoot)

  override fun setItems(items: List<GroupedMatches>) {
    differAsync.submitList(items)
  }

  override fun onBindViewHolder(holder: GroupedMatchesViewHolder, position: Int) {
    holder.bind(differAsync.currentList[position])
  }
}