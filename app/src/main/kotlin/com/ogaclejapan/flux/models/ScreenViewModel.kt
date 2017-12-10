package com.ogaclejapan.flux.models

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.ogaclejapan.flux.modules.Components
import com.ogaclejapan.flux.modules.ScreenComponent
import com.ogaclejapan.flux.modules.ScreenLifecycleHook
import com.ogaclejapan.flux.modules.ScreenModule

class ScreenViewModel(application: Application) : AndroidViewModel(application) {

  private val screenLifecycle: ScreenLifecycleHook = ScreenLifecycleHook()

  val screenComponent: ScreenComponent =
      Components.forApp(application).plus(ScreenModule(screenLifecycle))

  init {
    screenLifecycle.dispatchOnInit()
  }

  override fun onCleared() {
    screenLifecycle.dispatchOnCleared()
  }
}