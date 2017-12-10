package com.ogaclejapan.flux.utils.ext

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> ViewModelProvider.get(): T = this.get(T::class.java)