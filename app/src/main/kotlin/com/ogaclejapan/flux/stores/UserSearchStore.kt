package com.ogaclejapan.flux.stores

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.ogaclejapan.flux.dispatcher.Dispatcher
import com.ogaclejapan.flux.events.SearchLoadingStateChangedEvent
import com.ogaclejapan.flux.events.SearchResultListChangedEvent
import com.ogaclejapan.flux.models.LoadingState
import com.ogaclejapan.flux.models.User
import com.ogaclejapan.flux.modules.ScreenLifecycleHook
import com.ogaclejapan.flux.modules.ScreenScope
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@ScreenScope
class UserSearchStore @Inject constructor(
    dispatcher: Dispatcher,
    screenLifecycleHook: ScreenLifecycleHook
) {

  val loadingState: ObservableField<LoadingState> = ObservableField(LoadingState.LOADABLE)
  val userList: ObservableList<User> = ObservableArrayList()

  var userId: String = ""
  var nextPage: Int = 0

  init {
    screenLifecycleHook.addOnInit { dispatcher.register(this) }
    screenLifecycleHook.addOnCleared { dispatcher.unregister(this) }
  }

  fun getState(): LoadingState = loadingState.get()
  fun getItemAt(index: Int): User? = userList[index]

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun on(event: SearchLoadingStateChangedEvent) {
    loadingState.set(event.state)
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun on(event: SearchResultListChangedEvent) {
    if (event.userId != userId) {
      userList.clear()
    }
    userId = event.userId
    nextPage = event.nextPage
    userList.addAll(event.followers)
  }

}
