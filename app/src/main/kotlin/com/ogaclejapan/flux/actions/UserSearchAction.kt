package com.ogaclejapan.flux.actions

import com.ogaclejapan.flux.api.GitHubApi
import com.ogaclejapan.flux.dispatcher.Dispatcher
import com.ogaclejapan.flux.events.SearchLoadingStateChangedEvent
import com.ogaclejapan.flux.events.SearchResultListChangedEvent
import com.ogaclejapan.flux.models.LoadingState
import com.ogaclejapan.flux.modules.ActivityScope
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

import timber.log.Timber

@ActivityScope
class UserSearchAction @Inject constructor(
    private val dispatcher: Dispatcher,
    private val gitHubApi: GitHubApi
) {

  fun findFollower(userId: String, nextPage: Int = GitHubApi.DEFAULT_PAGE) {
    dispatchState(LoadingState.LOADING)
    gitHubApi.followers(userId, nextPage)
        .subscribeOn(Schedulers.io())
        .subscribe(
            { users ->
              dispatcher.dispatch(
                  SearchResultListChangedEvent(userId, users, users.nextPage()))
              dispatchState(if (users.hasMore())
                LoadingState.LOADABLE
              else
                LoadingState.FINISHED)
            },
            { e ->
              dispatchState(LoadingState.FINISHED)
              Timber.e(e, "Failed to find follower")
            })
  }

  private fun dispatchState(state: LoadingState) {
    dispatcher.dispatch(SearchLoadingStateChangedEvent(state))
  }
}
