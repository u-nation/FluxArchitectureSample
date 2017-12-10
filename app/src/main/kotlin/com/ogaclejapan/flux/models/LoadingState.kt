package com.ogaclejapan.flux.models

enum class LoadingState {
  LOADABLE,
  LOADING,
  FINISHED,
  CANCELED;

  val isLoading: Boolean
    get() = this == LOADING
}
