package com.abdelrahman.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
abstract class RecyclerAdapter<T, H : BaseViewHolder<T>>() :
  RecyclerView.Adapter<H>() {

  /**
   * @Mandatory
   * By using this object in ChildAdapters you can submit list to AsyncListDiffer and do all stuff
   * you need with items in recycler.
   */
  protected val differAsync =
    AsyncListDiffer<T>(this@RecyclerAdapter, generateAsyncDifferCallback())

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    instantiateViewHolder(
      generatedBindingLayout(LayoutInflater.from(parent.context), parent),
      viewType
    )

  override fun getItemCount() = differAsync.currentList.size


  /**
   * @Mandatory
   * This function @MUST be overrode as its objective to create a  ViewHolder of type H which
   * is BaseViewHolder
   */
  abstract fun instantiateViewHolder(itemView: ViewBinding, viewType: Int): H

  /**
   * @Mandatory
   * This function @Must be overrode as its main object to create AsyncDiffer call back
   */
  abstract fun generateAsyncDifferCallback(): DiffUtil.ItemCallback<T>

  /**
   * @Mandatory
   * This function @Must be overrode as it main objective to create ViewBinding object
   */
  abstract fun generatedBindingLayout(
    inflater: LayoutInflater,
    parent: ViewGroup,
    attachToRoot: Boolean = false
  ): ViewBinding

  /**
   * @Mandatory
   * This function @MUST be overrode as its main job is to set list to adapter .
   */
  abstract fun setItems(items: List<T>)
}

abstract class BaseViewHolder<T>(view: ViewBinding) : RecyclerView.ViewHolder(view.root) {

  /**
   * @Mandatory
   * @FirstParam will help you to attach to result of type @T  to ViewBinding fields.
   * @SecondParam if last item of adapter requires some special UI interactions ,
   * then you can use this param to do so
   */
  abstract fun bind(t: T, isLast: Boolean = false)

}