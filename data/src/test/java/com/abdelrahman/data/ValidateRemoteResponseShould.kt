package com.abdelrahman.data

import com.abdelrahman.data.datasource.remote.Constants.UNAUTHORIZED_ERROR_CODE
import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.data.datasource.remote.validateresponse.IValidateRemoteResponse
import com.abdelrahman.data.datasource.remote.validateresponse.ValidateRemoteResponse
import com.abdelrahman.models.ErrorResponse
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.kotlin.mock
import retrofit2.Response

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class ValidateRemoteResponseShould {

  private lateinit var validateRemoteResponse: IValidateRemoteResponse
  private val gson = mock<Gson>()
  private val successResponse = Response.success("")
  private val unAuthorizedErrorResponse =
    Response.error<ErrorResponse>(UNAUTHORIZED_ERROR_CODE, "".toResponseBody())
  private val serverErrorResponse = Response.error<ErrorResponse>(500, "".toResponseBody())

  @Test
  fun `return error `() {
    validateRemoteResponse = ValidateRemoteResponse(gson)
    assertEquals(
      RemoteResponseState.RemoteErrorResponse<ErrorResponse>(""),
      validateRemoteResponse.validateRemoteResponse(serverErrorResponse)
    )
  }

  @Test
  fun `return not valid response in case of response is null`() {
    validateRemoteResponse = ValidateRemoteResponse(gson)
    assertEquals(
      RemoteResponseState.NotValidResponse,
      validateRemoteResponse.validateRemoteResponse<Nothing>(null)
    )
  }

  @Test
  fun `return unauthorized state in case of code is unauthorized code`() {
    validateRemoteResponse = ValidateRemoteResponse(gson)
    assertEquals(
      RemoteResponseState.NotAuthorized,
      validateRemoteResponse.validateRemoteResponse(unAuthorizedErrorResponse)
    )
  }
  @Test
  fun `return valid response in case of response is success`() {
    validateRemoteResponse = ValidateRemoteResponse(gson)
    assertEquals(
      RemoteResponseState.ValidResponse(""),
      validateRemoteResponse.validateRemoteResponse(successResponse)
    )
  }
}