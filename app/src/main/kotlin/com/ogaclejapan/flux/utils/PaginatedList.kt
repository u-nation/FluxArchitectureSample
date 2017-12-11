package com.ogaclejapan.flux.utils

interface PaginatedList<out E> : List<E> {
  val hasMore: Boolean
  val nextPage: Int
  val lastPage: Int
}
