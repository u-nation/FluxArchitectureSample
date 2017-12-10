package com.ogaclejapan.flux.components.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ogaclejapan.flux.components.widget.Disposable
import com.ogaclejapan.flux.components.widget.Disposer
import com.ogaclejapan.flux.components.widget.Disposers
import com.ogaclejapan.flux.models.ScreenViewModel
import com.ogaclejapan.flux.modules.*
import com.ogaclejapan.flux.utils.ext.get
import com.squareup.leakcanary.RefWatcher
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), ScreenComponent.Factory, ActivityComponent.Factory, Disposer.Group {

  @Inject lateinit var refWatcher: RefWatcher

  private val disposerGroup = Disposers.newGroup()
  private val activityLifecycleHook: ActivityLifecycleHook = ActivityLifecycleHook()

  /*画面回転を生き抜くコンポーネント*/
  override val screenComponent: ScreenComponent
    get() = ViewModelProviders.of(this).get<ScreenViewModel>().screenComponent

  override val activityComponent: ActivityComponent
    get() = screenComponent.plus(ActivityModule(this, activityLifecycleHook))

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycle.addObserver(activityLifecycleHook)
    Components.forActivity(this).inject(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    refWatcher.watch(this)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> finish()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun add(vararg disposables: Disposable) {
    disposerGroup.add(*disposables)
  }

  override fun remove(vararg disposables: Disposable) {
    disposerGroup.remove(*disposables)
  }

  override fun dispose() {
    disposerGroup.dispose()
  }

}
