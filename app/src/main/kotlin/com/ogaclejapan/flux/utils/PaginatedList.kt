package com.ogaclejapan.flux.utils

interface PaginatedList<out E> : List<E> {
  fun hasMore(): Boolean
  fun nextPage(): Int
  fun lastPage(): Int
}
