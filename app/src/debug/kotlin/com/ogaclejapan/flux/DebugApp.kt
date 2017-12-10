package com.ogaclejapan.flux

import com.facebook.stetho.Stetho
import com.ogaclejapan.flux.modules.DebugNetworkModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class DebugApp : App() {

  override fun networkModule() = DebugNetworkModule()

  override fun onTrimMemory(level: Int) {
    super.onTrimMemory(level)
    Timber.w("onTrimMemory: level=%d", level)
  }

  override fun onLowMemory() {
    super.onLowMemory()
    Timber.w("onLowMemory")
  }

  override fun initialize() {
    super.initialize()
    initStetho()
    initLeakCanary()
    initDebugLifecycleCallbacks()
  }

  override fun initTimber() {
    Timber.plant(Timber.DebugTree())
  }

  private fun initStetho() {
    Stetho.initializeWithDefaults(this)
  }

  private fun initLeakCanary() {
    LeakCanary.install(this)
  }

  private fun initDebugLifecycleCallbacks() {
    registerActivityLifecycleCallbacks(DebugActivityLifecycle())
  }
}
