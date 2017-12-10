package com.ogaclejapan.flux.utils

import java.util.ArrayList
import java.util.Collections

class PaginatedArrayList<E>(
    items: List<E>,
    private val nextPage: Int,
    private val lastPage: Int) : ArrayList<E>(items), PaginatedList<E> {

  companion object {
    fun <E> empty(): PaginatedList<E> =
        PaginatedArrayList(emptyList(), Integer.MAX_VALUE, Integer.MIN_VALUE)

  }

  override fun hasMore(): Boolean = nextPage <= lastPage

  override fun nextPage(): Int = nextPage

  override fun lastPage(): Int = lastPage

}
