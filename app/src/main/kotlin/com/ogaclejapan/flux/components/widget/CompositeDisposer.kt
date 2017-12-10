package com.ogaclejapan.flux.components.widget

import java.util.Arrays
import java.util.HashSet

internal class CompositeDisposer : Disposer(), Disposer.Group {

  private val target = HashSet<Disposable>()

  override fun add(vararg disposables: Disposable) {
    target.addAll(Arrays.asList(*disposables))
  }

  override fun remove(vararg disposables: Disposable) {
    target.removeAll(Arrays.asList(*disposables))
  }

  override fun dispose() {
    if (target.size > 0) {
      for (d in target) {
        d.dispose()
      }
      target.clear()
    }
  }
}