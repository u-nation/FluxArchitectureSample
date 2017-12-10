package com.ogaclejapan.flux.modules

import dagger.Module
import dagger.Provides

@Module
class ScreenModule(private val screenLifecycleHook: ScreenLifecycleHook) {

  @ScreenScope
  @Provides
  fun provideLifecycleHook(): ScreenLifecycleHook = screenLifecycleHook
}
