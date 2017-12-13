package com.ogaclejapan.flux.modules

import com.ogaclejapan.flux.utils.Runnable

class ScreenLifecycleHook {

  companion object {
    private val NONE = 0
    private val INIT = 1
    private val CLEARED = 2
  }

  private var state = NONE

  private val onInitHooks = HashSet<Runnable>()
  private val onDestroyHooks = HashSet<Runnable>()

   fun addOnInit(r: Runnable) {
    onInitHooks.add(r)
    if (state == INIT) {
      r()
    }
  }

   fun addOnCleared(r: Runnable) {
    onDestroyHooks.add(r)
    if (state == CLEARED) {
      r()
    }
  }

  fun dispatchOnInit() {
    state = INIT
    onInitHooks.forEach { it() }
  }

  fun dispatchOnCleared() {
    state = CLEARED
    onDestroyHooks.forEach { it() }
  }
}