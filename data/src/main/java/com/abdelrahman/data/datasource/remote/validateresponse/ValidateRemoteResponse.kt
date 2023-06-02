package com.abdelrahman.data.datasource.remote.validateresponse

import com.abdelrahman.data.datasource.remote.Constants
import com.abdelrahman.data.datasource.remote.Constants.UNAUTHORIZED_ERROR_CODE
import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.data.datasource.remote.RemoteResponseState.NotAuthorized
import com.abdelrahman.data.datasource.remote.RemoteResponseState.NotValidResponse
import com.abdelrahman.data.datasource.remote.RemoteResponseState.ValidResponse
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class ValidateRemoteResponse @Inject constructor(
  private val mGson: Gson
) : IValidateRemoteResponse {

  override fun <T> validateRemoteResponse(response: Response<T>?): RemoteResponseState<T> {
    response?.let {
      it.apply {
        when (code()) {
          Constants.SUCCESS_CODE -> {
            if (isSuccessful) {
              val model = response.body()
              return if (model != null)
                ValidResponse(response = model)
              else
                NotValidResponse
            }
          }

          UNAUTHORIZED_ERROR_CODE -> return NotAuthorized
          else -> return handErrorResponse(response)
        }
      }

    }
    return NotValidResponse
  }

  private fun <T> handErrorResponse(response: Response<T>): RemoteResponseState<T> {
    return try {
      val jObjError = JSONObject(response.errorBody()!!.string())
      val errorMsg = jObjError.getString("message")
      RemoteResponseState.RemoteErrorResponse(errorMessage = errorMsg ?: "")
    } catch (e: Exception) {
      NotValidResponse
    }
  }
}
