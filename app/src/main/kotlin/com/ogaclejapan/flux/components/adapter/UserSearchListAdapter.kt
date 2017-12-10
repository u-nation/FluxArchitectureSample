package com.ogaclejapan.flux.components.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ogaclejapan.flux.R
import com.ogaclejapan.flux.actions.ActivityAction
import com.ogaclejapan.flux.components.callback.OnListChangedCallback
import com.ogaclejapan.flux.databinding.RowSearchBinding
import com.ogaclejapan.flux.models.User
import com.ogaclejapan.flux.modules.ActivityLifecycleHook
import com.ogaclejapan.flux.modules.ActivityScope
import com.ogaclejapan.flux.stores.UserSearchStore
import javax.inject.Inject

@ActivityScope
class UserSearchListAdapter @Inject constructor(
    private val activityAction: ActivityAction,
    private val store: UserSearchStore,
    hook: ActivityLifecycleHook) : RecyclerView.Adapter<UserSearchListAdapter.ViewHolder>() {

  init {
    val cb = OnListChangedCallback.delegateTo<User>(this)
    hook.addOnCreate { store.userList.addOnListChangedCallback(cb) }
    hook.addOnDestroy { store.userList.removeOnListChangedCallback(cb) }
  }

  private var inflater: LayoutInflater? = null

  override fun getItemCount(): Int = store.userList.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = inflater ?: LayoutInflater.from(parent.context).also { inflater = it }
    return ViewHolder(inflater.inflate(R.layout.row_search, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val user = store.getItemAt(position)
    user ?: return
    holder.itemView.setOnClickListener { activityAction.showUserDetail(user) }
    holder.binding.user = user
    holder.binding.executePendingBindings()
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: RowSearchBinding = DataBindingUtil.bind(itemView)
  }
}
