package com.abdelrahman.presentation.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Authored by Abdelrahman Ahmed on 02 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */

fun View.gone() {
  visibility = View.GONE
}

fun View.visible() {
  visibility = View.VISIBLE
}

fun View.invisible() {
  visibility = View.INVISIBLE
}

fun RecyclerView.handleSwipeRefreshWhenScrolling(swipeRefreshLayout: SwipeRefreshLayout) {
  addOnScrollListener(object : OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
      val topRowVerticalPosition =
        if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(
          0
        ).top
      swipeRefreshLayout.isEnabled = topRowVerticalPosition >= 0
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
      super.onScrollStateChanged(recyclerView, newState)
    }
  })
}