package com.ogaclejapan.flux.modules

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val activity: AppCompatActivity,
    private val activityLifecycleHook: ActivityLifecycleHook
) {

  @ActivityScope
  @Provides
  fun provideAppCompatActivity(): AppCompatActivity = activity

  @ActivityScope
  @Provides
  fun provideFragmentManager(): FragmentManager = activity.supportFragmentManager

  @ActivityScope
  @Provides
  fun provideActivityLifecycleHook(): ActivityLifecycleHook = activityLifecycleHook
}
