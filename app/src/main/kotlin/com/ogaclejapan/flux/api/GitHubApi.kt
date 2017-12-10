package com.ogaclejapan.flux.api

import com.ogaclejapan.flux.models.User
import com.ogaclejapan.flux.utils.PaginatedList
import io.reactivex.Single

interface GitHubApi {
  companion object {
    val DEFAULT_PAGE = 1
  }
  fun followers(userId: String, page: Int): Single<PaginatedList<User>>
}

