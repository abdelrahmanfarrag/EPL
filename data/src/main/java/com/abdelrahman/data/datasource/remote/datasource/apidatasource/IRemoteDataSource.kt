package com.abdelrahman.data.datasource.remote.datasource.apidatasource

import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.models.Competition
import com.abdelrahman.models.Matche

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
interface IRemoteDataSource {
  suspend fun getCompetitionMatches(id:Int):RemoteResponseState<Competition>
}