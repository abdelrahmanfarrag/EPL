package com.abdelrahman.data.datasource.remote.interceptors.tokeninterceptor

import com.abdelrahman.data.datasource.remote.Constants.Header.TOKEN_HEADER
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class TokenInterceptor @Inject constructor(): ITokenInterceptor {

  override fun intercept(chain: Chain): Response {
    val request = chain.request()
    val builder = request.newBuilder()
    builder.addHeader(TOKEN_HEADER,"9d97c6598abc41aca3fa6c665bb4f3ec")
    return chain.proceed(builder.build())
  }

}