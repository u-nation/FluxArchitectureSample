package com.ogaclejapan.flux.components.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.ogaclejapan.flux.R
import com.ogaclejapan.flux.databinding.ActivityUserDetailBinding

class UserDetailActivity : BaseActivity() {

  companion object {

    private val EXTRA_USER_ID = "user_id"
    private val EXTRA_USER_IMAGE = "user_image"

    fun newIntent(context: Context, userId: String, userImage: String): Intent =
        Intent(context, UserDetailActivity::class.java)
            .putExtra(EXTRA_USER_ID, userId)
            .putExtra(EXTRA_USER_IMAGE, userImage)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = DataBindingUtil.setContentView<ActivityUserDetailBinding>(this,
        R.layout.activity_user_detail)
    binding.userName = intent.getStringExtra(EXTRA_USER_ID)
    binding.userThumbnail = intent.getStringExtra(EXTRA_USER_IMAGE)
  }
}
