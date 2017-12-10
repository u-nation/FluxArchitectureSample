package com.ogaclejapan.flux.modules

import com.facebook.stetho.okhttp3.StethoInterceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DebugNetworkModule : NetworkModule() {

   override fun provideOkHttpClient(
      loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val client = super.provideOkHttpClient(loggingInterceptor)
    return client.newBuilder().addNetworkInterceptor(StethoInterceptor()).build()
  }
}
