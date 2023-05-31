package com.abdelrahman.data

import com.abdelrahman.data.datasource.remote.API
import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.IRemoteDataSource
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.RemoteDataSource
import com.abdelrahman.data.datasource.remote.validateresponse.IValidateRemoteResponse
import kotlinx.coroutines.test.runTest
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
class RemoteDataSourceShould {

  private lateinit var mIRemoteDataSource: IRemoteDataSource
  private val mAPI = mock<API>()
  private val mIValidateRemoteResponse = mock<IValidateRemoteResponse>()

  @Test
  fun `get competition matches called once`() = runTest {
    mIRemoteDataSource = RemoteDataSource(mIValidateRemoteResponse, mAPI)
    mIRemoteDataSource.getCompetitionMatches(1)
    verify(mAPI, times(1)).getCompetitionMatches(1)
  }

  @Test
  fun `validate remote response called once`() = runTest {
    whenever(mIValidateRemoteResponse.validateRemoteResponse(Response.success(Any()))).thenReturn(
      RemoteResponseState.ValidResponse(null)
    )
    mIRemoteDataSource = RemoteDataSource(mIValidateRemoteResponse, mAPI)
    mIRemoteDataSource.getCompetitionMatches(1)
    verify(mIValidateRemoteResponse, times(1)).validateRemoteResponse<Any>(null)
  }
}