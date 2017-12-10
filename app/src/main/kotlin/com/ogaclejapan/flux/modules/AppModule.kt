package com.ogaclejapan.flux.modules

import android.app.Application
import android.content.Context
import com.ogaclejapan.flux.BuildConfig
import com.ogaclejapan.flux.dispatcher.Dispatcher
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
  private val dispatcher = Dispatcher()

  @Singleton
  @Provides
  fun provideContext(): Context = application

  @Singleton
  @Provides
  fun provideApplication(): Application = application

  @Singleton
  @Provides
  fun provideRefWatcher(): RefWatcher =
      if (BuildConfig.DEBUG) LeakCanary.install(application) else RefWatcher.DISABLED

  @Singleton
  @Provides
  fun provideDispatcher(): Dispatcher = dispatcher

}
