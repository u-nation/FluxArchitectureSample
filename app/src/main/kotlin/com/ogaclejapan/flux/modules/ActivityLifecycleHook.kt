package com.ogaclejapan.flux.modules

import com.ogaclejapan.flux.utils.Runnable

class ActivityLifecycleHook {

  companion object {

    private val NONE = 0
    private val CREATE = 1
    private val START = 1 shl 1
    private val RESUME = 1 shl 2
    private val PAUSE = 1 shl 3
    private val STOP = 1 shl 4
    private val DESTROY = 1 shl 5

    private val ON_CREATE = CREATE
    private val ON_START = CREATE or START
    private val ON_RESUME = CREATE or START or RESUME
    private val ON_PAUSE = PAUSE
    private val ON_STOP = PAUSE or STOP
    private val ON_DESTROY = PAUSE or STOP or DESTROY
  }

  private var state = NONE

  private val onCreateHooks = HashSet<Runnable>()
  private val onStartHooks = HashSet<Runnable>()
  private val onResumeHooks = HashSet<Runnable>()
  private val onPauseHooks = HashSet<Runnable>()
  private val onStopHooks = HashSet<Runnable>()
  private val onDestroyHooks = HashSet<Runnable>()

  fun addOnCreate(r: Runnable) {
    onCreateHooks.add(r)
    if (state and CREATE > 0) {
      r()
    }
  }

  fun addOnStart(r: Runnable) {
    onStartHooks.add(r)
    if (state and START > 0) {
      r()
    }
  }

  fun addOnResume(r: Runnable) {
    onResumeHooks.add(r)
    if (state and RESUME > 0) {
      r()
    }
  }

  fun addOnPause(r: Runnable) {
    onPauseHooks.add(r)
    if (state and PAUSE > 0) {
      r()
    }
  }

  fun addOnStop(r: Runnable) {
    onStopHooks.add(r)
    if (state and STOP > 0) {
      r()
    }
  }

  fun addOnDestroy(r: Runnable) {
    onDestroyHooks.add(r)
    if (state and DESTROY > 0) {
      r()
    }
  }

  fun dispatchOnCreate() {
    state = ON_CREATE
    onCreateHooks.forEach { it() }
  }

  fun dispatchOnStart() {
    state = ON_START
    onStartHooks.forEach { it() }
  }

  fun dispatchOnResume() {
    state = ON_RESUME
    onResumeHooks.forEach { it() }
  }

  fun dispatchOnPause() {
    state = ON_PAUSE
    onPauseHooks.forEach { it() }
  }

  fun dispatchOnStop() {
    state = ON_STOP
    onStopHooks.forEach { it() }
  }

  fun dispatchOnDestroy() {
    state = ON_DESTROY
    onDestroyHooks.forEach { it() }
  }
}