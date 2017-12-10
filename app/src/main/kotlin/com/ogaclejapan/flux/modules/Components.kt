package com.ogaclejapan.flux.modules

import android.app.Activity
import android.app.Application

object Components {

  fun forApp(application: Application): AppComponent =
      AppComponent.Factory::class.java.cast(application).appComponent

  fun forActivity(activity: Activity): ActivityComponent =
      ActivityComponent.Factory::class.java.cast(activity).activityComponent
}

