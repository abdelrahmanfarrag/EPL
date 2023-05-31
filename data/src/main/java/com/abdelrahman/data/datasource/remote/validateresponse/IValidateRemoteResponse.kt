package com.abdelrahman.data.datasource.remote.validateresponse

import com.abdelrahman.data.datasource.remote.RemoteResponseState
import retrofit2.Response

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
interface IValidateRemoteResponse {
  fun <T> validateRemoteResponse(response: Response<T>?): RemoteResponseState<T>
}