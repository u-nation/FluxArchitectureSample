package com.ogaclejapan.flux.modules

import com.ogaclejapan.flux.BuildConfig
import com.ogaclejapan.flux.api.GitHubApi
import com.ogaclejapan.flux.api.GitHubApiClient
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class NetworkModule {

  @Singleton
  @Provides
  fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
      loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
    }
    return loggingInterceptor
  }

  @Singleton
  @Provides
  open fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor)
        .connectTimeout(10L, TimeUnit.SECONDS)
        .writeTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()
  }

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  @Singleton
  @Provides
  fun provideGitHubApi(retrofit: Retrofit): GitHubApi = GitHubApiClient(retrofit)
}
