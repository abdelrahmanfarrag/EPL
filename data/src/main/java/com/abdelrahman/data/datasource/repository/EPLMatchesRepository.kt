package com.abdelrahman.data.datasource.repository


import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.GeneralError
import com.abdelrahman.ErrorTypes.NetworkError
import com.abdelrahman.ErrorTypes.UnAuthorized
import com.abdelrahman.data.datasource.local.datasource.ILocalDataSource
import com.abdelrahman.data.datasource.local.models.MatchDb
import com.abdelrahman.data.datasource.local.models.mapToMatche
import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.IRemoteDataSource
import com.abdelrahman.entity.Match
import com.abdelrahman.models.Competition
import com.abdelrahman.models.Matche
import com.abdelrahman.repository.IEPLMatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class EPLMatchesRepository @Inject constructor(
  private val iRemoteDataSource: IRemoteDataSource,
  private val iLocalDataSource: ILocalDataSource
) : IEPLMatchesRepository {

  private val eplMatchesMutex = Mutex()
  private fun createMapOfLocalAndRemoteMatches(
    listOfRemoteMatches: List<Matche>?,
    listOfLocalMatches: List<MatchDb>
  ): List<Matche>? {
    if (listOfLocalMatches.isEmpty())
      return listOfRemoteMatches
    else
      return listOfRemoteMatches?.map { rMatch ->
        val localMatch = listOfLocalMatches.find {
          it.matchId == rMatch.id
        }
        if (localMatch != null) {
          return@map rMatch.copy(isFavorite = true)
        } else
          return@map rMatch.copy(isFavorite = false)
      }
  }

  override suspend fun fetchEPLMatches(id: Int): Flow<DataState<Competition>> {
    return callRemoteEPLMatches(id).zip(callLocaleDatabaseToGetMatches()) { remote, local ->
      return@zip if (remote is DataState.SuccessState) {
        DataState.SuccessState(
          remote.data.copy(
            remote.data.competition,
            remote.data.count,
            remote.data.filters,
            createMapOfLocalAndRemoteMatches(remote.data.matches, local)
          )
        )
      } else if (remote is DataState.ErrorState) {
        if (remote.errorTypes == NetworkError)
          if (local.isEmpty())
            return@zip remote
          else
            return@zip DataState.SuccessState(
              Competition(null, null, null, local.mapToMatche()),
              isOfflineMode = true
            )
        else
          return@zip remote
      } else
        remote
    }
  }

  override suspend fun updateMatchesTable(match: Match) {
    if (match.isFavorite)
      iLocalDataSource.deleteAMatch(match.matchId)
    else
      iLocalDataSource.upsertAMatch(match)
  }


  private suspend fun callLocaleDatabaseToGetMatches(): Flow<List<MatchDb>> {
    return iLocalDataSource.getAllSavedMatches()
  }

  private suspend fun callRemoteEPLMatches(id: Int): Flow<DataState<Competition>> {
    return eplMatchesMutex.withLock {
      flow {
        emit(iRemoteDataSource.getCompetitionMatches(id))
      }.map { remoteResponseState ->
        when (remoteResponseState) {
          is RemoteResponseState.ValidResponse -> DataState.SuccessState(remoteResponseState.response)
          is RemoteResponseState.RemoteErrorResponse -> DataState.ServerErrorMessage(
            remoteResponseState.errorMessage
          )

          RemoteResponseState.NotValidResponse -> DataState.ErrorState(GeneralError)
          RemoteResponseState.NotAuthorized -> DataState.ErrorState(UnAuthorized)
          RemoteResponseState.NoInternetConnect -> DataState.ErrorState(NetworkError)
        }
      }
    }
  }
}