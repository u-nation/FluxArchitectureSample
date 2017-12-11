package com.ogaclejapan.flux.components.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.ogaclejapan.flux.components.widget.Disposable
import com.ogaclejapan.flux.components.widget.Disposer
import com.ogaclejapan.flux.components.widget.Disposers
import com.ogaclejapan.flux.modules.Components
import com.squareup.leakcanary.RefWatcher
import javax.inject.Inject


abstract class BaseFragment : Fragment(), Disposer.Group {

  private val disposerGroup = Disposers.newGroup()

  @Inject lateinit var refWatcher: RefWatcher

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Components.forActivity(activity).inject(this)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    disposerGroup.dispose()
  }

  override fun onDestroy() {
    super.onDestroy()
    refWatcher.watch(this)
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
