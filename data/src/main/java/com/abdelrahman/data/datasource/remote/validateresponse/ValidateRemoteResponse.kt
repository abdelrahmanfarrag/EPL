package com.abdelrahman.data.datasource.remote.validateresponse

import com.abdelrahman.data.datasource.remote.Constants.UNAUTHORIZED_ERROR_CODE
import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.data.datasource.remote.RemoteResponseState.NoInternetConnect
import com.abdelrahman.data.datasource.remote.RemoteResponseState.NotAuthorized
import com.abdelrahman.data.datasource.remote.RemoteResponseState.NotValidResponse
import com.abdelrahman.data.datasource.remote.RemoteResponseState.ValidResponse
import com.abdelrahman.data.datasource.remote.internetinterceptor.INetworkDetector
import com.google.gson.Gson
import com.google.gson.JsonParseException
import retrofit2.Response
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Suppress("UNCHECKED_CAST")
class ValidateRemoteResponse @Inject constructor(
  private val mGson: Gson,
  private val iNetworkDetector: INetworkDetector
) : IValidateRemoteResponse {

  override fun <T> validateRemoteResponse(response: Response<T>?): RemoteResponseState<T> {
    return if (!iNetworkDetector.isConnected())
      NoInternetConnect as RemoteResponseState<T>
    else {
      if (response == null)
        return NotValidResponse as RemoteResponseState<T>
      else {
        if (response.isSuccessful) {
          return ValidResponse(mGson.toJson(response.body()) as T)
        } else {
          if (response.code() == UNAUTHORIZED_ERROR_CODE)
            return NotAuthorized as RemoteResponseState<T>
          else
            return NotValidResponse as RemoteResponseState<T>
        }
      }
    }
  }
}
