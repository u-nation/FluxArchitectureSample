package com.ogaclejapan.flux.components.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mugen.Mugen
import com.mugen.MugenCallbacks
import com.ogaclejapan.flux.R
import com.ogaclejapan.flux.actions.UserSearchAction
import com.ogaclejapan.flux.components.adapter.UserSearchListAdapter
import com.ogaclejapan.flux.databinding.FragmentSearchResultBinding
import com.ogaclejapan.flux.models.LoadingState
import com.ogaclejapan.flux.modules.Components
import com.ogaclejapan.flux.stores.UserSearchStore
import com.ogaclejapan.flux.utils.ext.addOnChange
import com.ogaclejapan.flux.utils.ext.addOnSimpleChange
import javax.inject.Inject

class SearchResultFragment : BaseFragment(), MugenCallbacks {

  companion object {
    private val LOAD_OFFSET = 10
  }

  @Inject lateinit var action: UserSearchAction
  @Inject lateinit var store: UserSearchStore
  @Inject lateinit var recyclerAdapter: UserSearchListAdapter

  private lateinit var binding: FragmentSearchResultBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Components.forActivity(activity).inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View =
      DataBindingUtil.inflate<FragmentSearchResultBinding>(inflater,
          R.layout.fragment_search_result, container, false)
          .also { binding = it }
          .root

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.searchResultList.run {
      layoutManager = LinearLayoutManager(context)
      adapter = recyclerAdapter
    }
    Mugen.with(binding.searchResultList, this).start().loadMoreOffset = LOAD_OFFSET

    store.loadingState.addOnChange {
      binding.isLoading = it.isLoading
    }.addTo(this)

    store.userList.addOnSimpleChange {
      binding.itemCount = it.size
    }.addTo(this)
  }

  override fun onLoadMore() {
    action.findFollower(store.userId, store.nextPage)
  }

  override fun isLoading(): Boolean = store.getState().isLoading

  override fun hasLoadedAllItems(): Boolean {
    val state = store.getState()
    return state == LoadingState.FINISHED || state == LoadingState.CANCELED
  }
}
