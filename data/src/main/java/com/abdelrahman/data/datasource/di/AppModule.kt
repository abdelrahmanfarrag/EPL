package com.abdelrahman.data.datasource.di

import android.content.Context
import android.util.Log
import com.abdelrahman.data.BuildConfig
import com.abdelrahman.data.datasource.remote.API
import com.abdelrahman.data.datasource.remote.Constants.URLS.BASE_URL
import com.abdelrahman.data.datasource.remote.interceptors.tokeninterceptor.ITokenInterceptor
import com.abdelrahman.data.datasource.remote.interceptors.tokeninterceptor.TokenInterceptor
import com.abdelrahman.data.datasource.remote.networkdetector.INetworkDetector
import com.abdelrahman.data.datasource.remote.networkdetector.NetworkDetectorImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  @Singleton
  fun providesINetworkDetector(@ApplicationContext context: Context): INetworkDetector {
    return NetworkDetectorImpl(context)
  }
  @Provides
  @Singleton
  fun providesGson(): Gson {
    return Gson()
  }

  @Provides
  @Singleton
  fun providesTokenInterceptor(): ITokenInterceptor {
    return TokenInterceptor()
  }

  @Provides
  @Singleton
  fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level =
      if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    return logging
  }

  @Provides
  @Singleton
  fun providesOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    tokenInterceptor: TokenInterceptor
  ): OkHttpClient {
    return getOkHttpClient()
      .addInterceptor(loggingInterceptor)
      .addInterceptor(tokenInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun providesRetrofitInstance(
    client: OkHttpClient,
    gson: Gson
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(client)
      .build()
  }

  @Provides
  @Singleton
  fun providesAPI(retrofit: Retrofit): API {
    return retrofit.create(API::class.java)
  }

  private fun getOkHttpClient(): OkHttpClient.Builder {
    return OkHttpClient.Builder()
      .connectTimeout(20, SECONDS)
      .readTimeout(30, SECONDS)
      .writeTimeout(20, SECONDS)
      .addInterceptor { interceptSocketException(it) }
      .retryOnConnectionFailure(true)
  }

  @Throws(IOException::class)
  private fun interceptSocketException(chain: Interceptor.Chain): Response {
    var request: Request = chain.request()
    val url: HttpUrl = request.url
      .newBuilder()
      .build()
    request = request
      .newBuilder()
      .url(url)
      .build()
    val response = chain.proceed(request)
    try {
      return response.newBuilder()
        .body(response.body?.string()?.toResponseBody(response.body?.contentType()))
        .build()
    } catch (exception: SocketTimeoutException) {
      Log.e("exception", exception.toString())
    }
    return response
  }
}