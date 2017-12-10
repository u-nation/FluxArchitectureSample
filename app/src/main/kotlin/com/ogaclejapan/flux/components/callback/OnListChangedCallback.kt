package com.ogaclejapan.flux.components.callback

import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import timber.log.Timber

import java.lang.ref.WeakReference

abstract class OnListChangedCallback<T> : ObservableList.OnListChangedCallback<ObservableList<T>>() {

  abstract override fun onChanged(sender: ObservableList<T>)

  override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int,
      itemCount: Int) {
    onChanged(sender)
  }

  override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int,
      itemCount: Int) {
    onChanged(sender)
  }

  override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int,
      itemCount: Int) {
    onChanged(sender)
  }

  override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int,
      itemCount: Int) {
    onChanged(sender)
  }

  class SimpleListChangedCallback<T>(
      private val block: (ObservableList<T>) -> Unit
  ) : OnListChangedCallback<T>() {

    override fun onChanged(sender: ObservableList<T>) {
      block(sender)
    }

    override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
      block(sender)
    }

    override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int,
        itemCount: Int) {
      block(sender)
    }

    override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int,
        itemCount: Int) {
      block(sender)
    }

    override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
      block(sender)
    }
  }

  private class RecyclerViewAdapterDelegate<T>(
      adaptee: RecyclerView.Adapter<*>) : OnListChangedCallback<T>() {

    val weakRef: WeakReference<out RecyclerView.Adapter<*>> = WeakReference(adaptee)

    override fun onChanged(sender: ObservableList<T>) {
      weakRef.get()?.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
      weakRef.get()?.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int,
        itemCount: Int) {
      weakRef.get()?.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int,
        itemCount: Int) {
      weakRef.get()?.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
      weakRef.get()?.notifyItemRangeRemoved(positionStart, itemCount)
    }
  }

  companion object {
    fun <T> delegateTo(adaptee: RecyclerView.Adapter<*>): OnListChangedCallback<T> =
        RecyclerViewAdapterDelegate(adaptee)
  }

}
