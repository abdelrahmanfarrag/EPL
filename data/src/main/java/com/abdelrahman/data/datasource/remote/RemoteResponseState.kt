package com.abdelrahman.data.datasource.remote

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
sealed class RemoteResponseState<out T> {
  data class ValidResponse<T>(val response: T) : RemoteResponseState<T>()
  object NoInternetConnect : RemoteResponseState<Any>()
  object NotAuthorized : RemoteResponseState<Any>()
  object NotValidResponse : RemoteResponseState<Any>()
}