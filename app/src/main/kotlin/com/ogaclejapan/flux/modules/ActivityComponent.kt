package com.ogaclejapan.flux.modules

import com.ogaclejapan.flux.components.activity.BaseActivity
import com.ogaclejapan.flux.components.activity.UserDetailActivity
import com.ogaclejapan.flux.components.fragment.BaseFragment
import com.ogaclejapan.flux.components.fragment.SearchInputFragment
import com.ogaclejapan.flux.components.fragment.SearchResultFragment

import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
  interface Factory {
    val activityComponent: ActivityComponent
  }

  fun inject(activity: BaseActivity)

  fun inject(activity: UserDetailActivity)

  fun inject(fragment: BaseFragment)

  fun inject(fragment: SearchInputFragment)

  fun inject(fragment: SearchResultFragment)
}
