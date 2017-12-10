package com.ogaclejapan.flux.utils.ext

import android.databinding.ObservableList
import com.ogaclejapan.flux.components.callback.OnListChangedCallback
import com.ogaclejapan.flux.components.widget.Disposer
import com.ogaclejapan.flux.components.widget.Disposers

fun <T : Any> ObservableList<T>.addOnSimpleChange(block: (ObservableList<T>) -> Unit): Disposer {
  val callback = object : OnListChangedCallback<T>() {
    override fun onChanged(sender: ObservableList<T>) {
      block(sender)
    }
  }
  addOnListChangedCallback(callback)
  return Disposers.from(object : Disposer() {
    override fun dispose() {
      removeOnListChangedCallback(callback)
    }
  })
}