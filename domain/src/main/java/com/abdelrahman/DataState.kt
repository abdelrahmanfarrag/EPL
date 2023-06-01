package com.abdelrahman

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
sealed class DataState<out T> {
  data class SuccessState<T>(val data: T) : DataState<T>()
  data class ErrorState(val errorTypes: ErrorTypes, val message: String? = null) :
    DataState<Nothing>()
}
