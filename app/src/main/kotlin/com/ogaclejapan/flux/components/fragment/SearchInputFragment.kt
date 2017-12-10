package com.ogaclejapan.flux.components.fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding2.view.clicks
import com.ogaclejapan.flux.R
import com.ogaclejapan.flux.actions.UserSearchAction
import com.ogaclejapan.flux.databinding.FragmentSearchInputBinding
import com.ogaclejapan.flux.models.LoadingState
import com.ogaclejapan.flux.modules.Components
import com.ogaclejapan.flux.stores.UserSearchStore
import com.ogaclejapan.flux.utils.ext.addOnChange
import javax.inject.Inject

class SearchInputFragment : BaseFragment() {

  @Inject lateinit var store: UserSearchStore
  @Inject lateinit var action: UserSearchAction

  private lateinit var binding: FragmentSearchInputBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Components.forActivity(activity).inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View =
      DataBindingUtil.inflate<FragmentSearchInputBinding>(inflater,
          R.layout.fragment_search_input, container,
          false).also { binding = it }.root

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = DataBindingUtil.bind(view)

    binding.searchButton.clicks()
        .doOnNext { hideKeyboard(binding.searchInputText.windowToken) }
        .map { binding.searchInputText.text?.toString() ?: "" }
        .filter { !it.isEmpty() }
        .subscribe { action.findFollower(it) }

    store.loadingState.addOnChange {
      binding.searchButton.isEnabled = it != LoadingState.LOADING
    }.addTo(this)
  }

  private fun hideKeyboard(windowToken: IBinder) {
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(windowToken, 0)
  }
}
