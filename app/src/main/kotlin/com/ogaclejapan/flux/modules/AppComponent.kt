package com.ogaclejapan.flux.modules

import com.ogaclejapan.flux.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

  interface Factory {
    val appComponent: AppComponent
  }

  fun plus(module: ScreenModule): ScreenComponent

  fun inject(app: App)

}
