package com.ogaclejapan.flux

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.ogaclejapan.flux.modules.AppComponent
import com.ogaclejapan.flux.modules.AppModule
import com.ogaclejapan.flux.modules.DaggerAppComponent
import com.ogaclejapan.flux.modules.NetworkModule

@SuppressLint("Registered")
open class App : Application(), AppComponent.Factory {

  override val appComponent: AppComponent by lazy {
    DaggerAppComponent.builder()
        .appModule(appModule())
        .networkModule(networkModule())
        .build()
  }

  protected open fun appModule() = AppModule(this)

  protected open fun networkModule() = NetworkModule()

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    AndroidThreeTen.init(this)
  }

  override fun onCreate() {
    super.onCreate()
    initialize()
  }

  protected open fun initialize() {
    initTimber()
  }

  protected open fun initTimber() {
    //Timber.plant(new CrashlyticsTree());
  }

}
