package com.ogaclejapan.flux.components.widget

abstract class Disposer : Disposable {

  fun addTo(group: Group) {
    group.add(this)
  }

  abstract override fun dispose()

  interface Group : Disposable {

    fun add(vararg disposables: Disposable)

    fun remove(vararg disposables: Disposable)
  }
}
