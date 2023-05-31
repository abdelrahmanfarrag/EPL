package com.abdelrahman.data.datasource.remote

import com.abdelrahman.data.datasource.remote.Constants.ENDPOINTS.MATCHES
import com.abdelrahman.data.datasource.remote.Constants.Path.COMPETITION_ID
import com.abdelrahman.data.datasource.remote.Constants.Path.EPL_COMPETITION_ID
import com.abdelrahman.models.Competition
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
interface API {


  @GET(MATCHES)
  suspend fun getCompetitionMatches(
    @Path(COMPETITION_ID)
    competitionId: Int = EPL_COMPETITION_ID
  ): Response<Competition>
}