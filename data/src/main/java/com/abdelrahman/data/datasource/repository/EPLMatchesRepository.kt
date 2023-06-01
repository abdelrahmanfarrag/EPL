package com.abdelrahman.data.datasource.repository

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.GeneralError
import com.abdelrahman.ErrorTypes.NetworkError
import com.abdelrahman.ErrorTypes.UnAuthorized
import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.IRemoteDataSource
import com.abdelrahman.repository.IEPLMatchesRepository
import com.abdelrahman.models.Competition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class EPLMatchesRepository @Inject constructor(private val iRemoteDataSource: IRemoteDataSource) :
  IEPLMatchesRepository {

  override suspend fun fetchEPLMatches(id: Int): Flow<DataState<Competition>> {
    return flow {
      emit(iRemoteDataSource.getCompetitionMatches(id))
    }.map { remoteResponseState ->
      when (remoteResponseState) {
        is RemoteResponseState.ValidResponse -> DataState.SuccessState(remoteResponseState.response)
        RemoteResponseState.NotValidResponse -> DataState.ErrorState(GeneralError)
        RemoteResponseState.NotAuthorized -> DataState.ErrorState(UnAuthorized)
        RemoteResponseState.NoInternetConnect -> DataState.ErrorState(NetworkError)
       is RemoteResponseState.RemoteErrorResponse -> DataState.ErrorState(GeneralError,remoteResponseState.errorMessage)
      }
    }
  }
}