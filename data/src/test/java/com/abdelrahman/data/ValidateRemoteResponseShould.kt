package com.abdelrahman.data

import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.data.datasource.remote.networkdetector.INetworkDetector
import com.abdelrahman.data.datasource.remote.validateresponse.IValidateRemoteResponse
import com.abdelrahman.data.datasource.remote.validateresponse.ValidateRemoteResponse
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class ValidateRemoteResponseShould {

  private lateinit var validateRemoteResponse: IValidateRemoteResponse
  private val gson = mock<Gson>()
  private val iNetworkDetector = mock<INetworkDetector>()
  private val expectedNoNetwork = RemoteResponseState.NoInternetConnect
  private val successResponse = Response.success(null)
  private val unAuthorizedErrorResponse = Response.error<Any>(403, "Error".toResponseBody())
  private val errorResponse = Response.error<Any>(500, "Error".toResponseBody())


  @Test
  fun `call isConnected once`() {
    createMockedConnectedRemote()
    validateRemoteResponse.validateRemoteResponse(successResponse)
    verify(iNetworkDetector, times(1)).isConnected()
  }

  @Test
  fun `return response status of no internet connect when no connection`() {
    createMockedNotConnectedRemote()
    assertEquals(
      expectedNoNetwork,
      validateRemoteResponse.validateRemoteResponse(Response.success(Any()))
    )
  }

  @Test
  fun `return response status of valid when internet is connected`() {
    createMockedConnectedRemote()
    assertEquals(
      RemoteResponseState.ValidResponse(null),
      validateRemoteResponse.validateRemoteResponse(successResponse)
    )
  }

  @Test
  fun `return unauthorized status when status code is 401`() {
    createMockedConnectedRemote()
    assertEquals(
      RemoteResponseState.NotAuthorized,
      validateRemoteResponse.validateRemoteResponse(unAuthorizedErrorResponse)
    )
  }

  @Test
  fun `return unauthorized status when status code is not 401 or 200`() {
    createMockedConnectedRemote()
    assertEquals(
      RemoteResponseState.NotValidResponse,
      validateRemoteResponse.validateRemoteResponse(errorResponse)
    )
  }

  @Test
  fun `return not valid status when response is null`() {
    createMockedConnectedRemote()
    assertEquals(
      RemoteResponseState.NotValidResponse,
      validateRemoteResponse.validateRemoteResponse<Any>(null)
    )
  }

  private fun createMockedNotConnectedRemote() {
    whenever(iNetworkDetector.isConnected()).thenReturn(false)
    validateRemoteResponse = ValidateRemoteResponse(gson, iNetworkDetector)

  }

  private fun createMockedConnectedRemote() {
    whenever(iNetworkDetector.isConnected()).thenReturn(true)
    validateRemoteResponse = ValidateRemoteResponse(gson, iNetworkDetector)
  }
}