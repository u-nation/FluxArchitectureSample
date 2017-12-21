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

  override val screenComponent: ScreenComponent
    get() = ViewModelProviders.of(this).get<ScreenViewModel>().screenComponent

  override val activityComponent: ActivityComponent
    get() = screenComponent.plus(ActivityModule(this, activityLifecycleHook))

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Components.forActivity(this).inject(this)
    activityLifecycleHook.dispatchOnCreate()
  }

  override fun onStart() {
    super.onStart()
    activityLifecycleHook.dispatchOnStart()
  }

  override fun onResume() {
    super.onResume()
    activityLifecycleHook.dispatchOnResume()
  }

  override fun onPause() {
    super.onPause()
    activityLifecycleHook.dispatchOnPause()
  }

  override fun onStop() {
    super.onStop()
    activityLifecycleHook.dispatchOnStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    activityLifecycleHook.dispatchOnDestroy()
    refWatcher.watch(this)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      finish()
      return true
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
