package com.abdelrahman.data.datasource.remote.datasource.apidatasource

import com.abdelrahman.data.datasource.remote.API
import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.models.Competition
import com.abdelrahman.data.datasource.remote.validateresponse.IValidateRemoteResponse
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class RemoteDataSource @Inject constructor(
  private val iValidateRemoteResponse: IValidateRemoteResponse,
  private val api: API
) : IRemoteDataSource {

  override suspend fun getCompetitionMatches(id: Int): RemoteResponseState<Competition> {
    return iValidateRemoteResponse.validateRemoteResponse(api.getCompetitionMatches(id))
  }
}