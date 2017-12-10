package com.ogaclejapan.flux.modules

import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [ScreenModule::class])
interface ScreenComponent {

  interface Factory {
    val screenComponent: ScreenComponent
  }

  fun plus(module: ActivityModule): ActivityComponent
}