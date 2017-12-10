package com.ogaclejapan.flux.api

import android.net.Uri

import com.ogaclejapan.flux.models.User
import com.ogaclejapan.flux.utils.PaginatedArrayList
import com.ogaclejapan.flux.utils.PaginatedList

import io.reactivex.Single
import java.net.HttpURLConnection
import java.util.regex.Pattern

import okhttp3.Headers
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import timber.log.Timber

class GitHubApiClient(private val service: Service) : GitHubApi {

  companion object {
    private val NEXT_PAGE_LINK = Pattern.compile(".*<(.+)>; rel=\"next\".*")
    private val LAST_PAGE_LINK = Pattern.compile(".*<(.+)>; rel=\"last\".*")
  }

  interface Service {
    @GET("users/{user}/followers")
    fun listFollowers(
        @Path("user") userId: String,
        @Query("page") page: Int): Single<Response<List<User>>>
  }

  constructor(retrofit: Retrofit) : this(retrofit.create<Service>(Service::class.java))

  override fun followers(userId: String, page: Int): Single<PaginatedList<User>> {
    return service.listFollowers(userId, page)
        .flatMap { response ->
      return@flatMap when {
            response.isSuccessful -> {
              response.body()?.let {
                Single.just(toPaginatedList(it, response.headers()))
              } ?: Single.just(PaginatedArrayList.empty())
            }
            response.code() == HttpURLConnection.HTTP_NOT_FOUND -> {
              Single.just(PaginatedArrayList.empty())
            }
            else -> {
              Single.error(HttpException(response))
            }
          }
        }
  }

  private fun <T> toPaginatedList(items: List<T>, headers: Headers): PaginatedList<T> {
    var nextPage = Integer.MAX_VALUE
    var lastPage = Integer.MIN_VALUE

    val link = headers.get("Link") ?: return PaginatedArrayList(items, nextPage, lastPage)

    val nextMatcher = NEXT_PAGE_LINK.matcher(link)
    if (nextMatcher.matches()) {
      val nextUri = Uri.parse(nextMatcher.group(nextMatcher.groupCount()))
      val next = nextUri.getQueryParameter("page")
      nextPage = if (next != null) Integer.parseInt(next) else nextPage
    }

    val lastMatcher = LAST_PAGE_LINK.matcher(link)
    if (lastMatcher.matches()) {
      val lastUri = Uri.parse(lastMatcher.group(lastMatcher.groupCount()))
      val last = lastUri.getQueryParameter("page")
      lastPage = if (last != null) Integer.parseInt(last) else lastPage
    }

    return PaginatedArrayList(items, nextPage, lastPage)
  }
}
