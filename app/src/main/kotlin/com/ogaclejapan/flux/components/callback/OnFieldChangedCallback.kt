package com.ogaclejapan.flux.components.callback

import android.databinding.Observable
import android.databinding.ObservableField

class OnFieldChangedCallback<T>(
    private val block: (T) -> Unit
) : Observable.OnPropertyChangedCallback() {

  @Suppress("UNCHECKED_CAST")
  override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
    if (sender is ObservableField<*>) {
      block((sender as ObservableField<T>).get())
    } else {
      throw  IllegalStateException("Must be ObservableField")
    }
  }
}