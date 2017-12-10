package com.ogaclejapan.flux

import android.app.Activity
import android.app.Application
import android.os.Bundle

import timber.log.Timber

class DebugActivityLifecycle : Application.ActivityLifecycleCallbacks {

  companion object {
    private val LOG_FORMAT = "%s ⟳ %s"
  }

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityCreated")
  }

  override fun onActivityStarted(activity: Activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityStarted")
  }

  override fun onActivityResumed(activity: Activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityResumed")
  }

  override fun onActivityPaused(activity: Activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityPaused")
  }

  override fun onActivityStopped(activity: Activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityStopped")
  }

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivitySaveInstanceState")
  }

  override fun onActivityDestroyed(activity: Activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityDestroyed")
  }
}
