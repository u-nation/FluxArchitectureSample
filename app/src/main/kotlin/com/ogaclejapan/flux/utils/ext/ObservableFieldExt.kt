package com.ogaclejapan.flux.utils.ext

import android.databinding.ObservableField
import com.ogaclejapan.flux.components.callback.OnFieldChangedCallback
import com.ogaclejapan.flux.components.widget.Disposer
import com.ogaclejapan.flux.components.widget.Disposers

fun <T> ObservableField<T>.addOnChange(block: (T) -> Unit): Disposer {
  val callback = OnFieldChangedCallback<T> { block(it) }
  addOnPropertyChangedCallback(callback)
  return Disposers.from(object : Disposer() {
    override fun dispose() {
      removeOnPropertyChangedCallback(callback)
    }
  })
}

fun <T> ObservableField<T>.addOnChangeWithValue(block: (T) -> Unit): Disposer {
  block.invoke(get())
  return addOnChange(block)
}