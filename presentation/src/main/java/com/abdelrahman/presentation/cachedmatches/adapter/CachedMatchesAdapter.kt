package com.abdelrahman.presentation.cachedmatches.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.viewbinding.ViewBinding
import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.base.RecyclerAdapter
import com.abdelrahman.presentation.databinding.ItemCachedMatchesBinding

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class CachedMatchesAdapter : RecyclerAdapter<Match, CachedMatchesViewHolder>() {

  override fun instantiateViewHolder(
    itemView: ViewBinding,
    viewType: Int
  ): CachedMatchesViewHolder = CachedMatchesViewHolder(itemView as ItemCachedMatchesBinding)

  override fun generateAsyncDifferCallback(): ItemCallback<Match> {
    return object : ItemCallback<Match>() {
      override fun areItemsTheSame(
        oldItem: Match,
        newItem: Match
      ): Boolean {
        return oldItem.equals(newItem)
      }

      override fun areContentsTheSame(
        oldItem: Match,
        newItem: Match
      ): Boolean {
        return oldItem.isFavorite == newItem.isFavorite
      }
    }
  }

  override fun generatedBindingLayout(
    inflater: LayoutInflater,
    parent: ViewGroup,
    attachToRoot: Boolean
  ): ViewBinding {
    return ItemCachedMatchesBinding.inflate(inflater, parent, attachToRoot)
  }

  override fun onBindViewHolder(holder: CachedMatchesViewHolder, position: Int) {
    holder.bind(differAsync.currentList[position])
  }

  override fun setItems(items: List<Match>) {
    differAsync.submitList(items)
  }
}