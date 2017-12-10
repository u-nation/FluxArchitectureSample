package com.ogaclejapan.flux.dispatcher

import com.ogaclejapan.flux.BuildConfig
import com.ogaclejapan.flux.MyEventBusIndex
import org.greenrobot.eventbus.EventBus

class Dispatcher {

  private val dispatcherBus: EventBus = EventBus.builder()
      .addIndex(MyEventBusIndex())
      .throwSubscriberException(BuildConfig.DEBUG)
      .build()

  fun dispatch(payload: Any) {
    dispatcherBus.post(payload)
  }

  fun register(observer: Any) {
    dispatcherBus.register(observer)
  }

  fun unregister(observer: Any) {
    dispatcherBus.unregister(observer)
  }
}
