package com.ogaclejapan.flux.utils

import java.util.ArrayList
import java.util.Collections

class PaginatedArrayList<E>(
    items: List<E>,
    override val nextPage: Int,
    override val lastPage: Int) : ArrayList<E>(items), PaginatedList<E> {

  companion object {
    fun <E> empty(): PaginatedList<E> =
        PaginatedArrayList(emptyList(), Integer.MAX_VALUE, Integer.MIN_VALUE)
  }

  override val hasMore: Boolean = nextPage <= lastPage
}
