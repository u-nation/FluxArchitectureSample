package com.ogaclejapan.flux.utils.ext

import android.databinding.ObservableList
import android.support.annotation.CheckResult
import com.ogaclejapan.flux.components.callback.OnListChangedCallback
import com.ogaclejapan.flux.components.widget.Disposer
import com.ogaclejapan.flux.components.widget.Disposers

@CheckResult
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

@CheckResult
fun <T : Any> ObservableList<T>.addOnSimpleChangeWithValue(
    block: (ObservableList<T>) -> Unit): Disposer {
  block.invoke(this)
  return addOnSimpleChange(block)
}