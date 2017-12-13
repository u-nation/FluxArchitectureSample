package com.ogaclejapan.flux.actions

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.ogaclejapan.flux.components.activity.UserDetailActivity
import com.ogaclejapan.flux.models.User
import com.ogaclejapan.flux.modules.ActivityScope
import javax.inject.Inject

@ActivityScope
class ActivityAction @Inject constructor(
    private val activity: AppCompatActivity
) {

  fun showUserDetail(user: User) {
    show(UserDetailActivity.newIntent(activity, user.login, user.avatar_url))
  }

  private fun show(intent: Intent) = activity.startActivity(intent)
}
