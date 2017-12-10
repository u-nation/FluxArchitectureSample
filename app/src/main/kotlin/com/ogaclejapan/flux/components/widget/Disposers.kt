package com.ogaclejapan.flux.components.widget

object Disposers {

  fun newGroup(): Disposer.Group {
    return CompositeDisposer()
  }

  fun from(d: Disposable): Disposer {
    return object : Disposer() {
      override fun dispose() {
        d.dispose()
      }
    }
  }
}
