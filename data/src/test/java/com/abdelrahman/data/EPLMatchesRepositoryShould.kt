package com.abdelrahman.data

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.GeneralError
import com.abdelrahman.ErrorTypes.NetworkError
import com.abdelrahman.ErrorTypes.UnAuthorized
import com.abdelrahman.data.datasource.local.datasource.ILocalDataSource
import com.abdelrahman.data.datasource.local.models.MatchDb
import com.abdelrahman.data.datasource.local.models.mapToMatche
import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.IRemoteDataSource
import com.abdelrahman.data.datasource.repository.EPLMatchesRepository
import com.abdelrahman.models.Competition
import com.abdelrahman.repository.IEPLMatchesRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class EPLMatchesRepositoryShould {

  private lateinit var mEPLMatchesRepository: IEPLMatchesRepository
  private val mIRemoteDataSource = mock<IRemoteDataSource>()
  private val mILocalDataSource = mock<ILocalDataSource>()
  private val competition = mock<Competition>()
  private val matchDb = mock<MatchDb>()
  private val successState = DataState.SuccessState(null)

  @Test
  fun `call get competition matches once`() = runTest {
    mockDataSources(RemoteResponseState.ValidResponse(competition))
    mEPLMatchesRepository.fetchEPLMatches(1).first()
    verify(mIRemoteDataSource, times(1)).getCompetitionMatches(1)
  }

  @Test
  fun `call get competition matches returns un authorized  state when data source responds with unauthorized`() =
    runTest {
      mockDataSources(RemoteResponseState.NotAuthorized)
      assertEquals(
        DataState.ErrorState(UnAuthorized),
        mEPLMatchesRepository.fetchEPLMatches(1).first()
      )
    }


  @Test
  fun `call get competition match returns success data state when remote data state success `() =
    runTest {
      mockDataSources(RemoteResponseState.ValidResponse(competition))
      mEPLMatchesRepository.fetchEPLMatches(1).first()
      assertEquals(successState, mEPLMatchesRepository.fetchEPLMatches(1).first())
    }

  @Test
  fun `call get competition matches return error of type general when something went wrong with remote data source`() =
    runTest {
      mockDataSources(RemoteResponseState.NotValidResponse)
      assertEquals(
        DataState.ErrorState(GeneralError),
        mEPLMatchesRepository.fetchEPLMatches(1).first()
      )
    }

  @Test
  fun `call get competition matches returns error of type no network when no network is returned from data source`() =
    runTest {
      mockDataSources(RemoteResponseState.NoInternetConnect, arrayListOf())
      assertEquals(
        DataState.ErrorState(NetworkError),
        mEPLMatchesRepository.fetchEPLMatches(1).first()
      )
    }

  @Test
  fun `call get competition matches returns success in case of there is not network but there are stored matches in room`() =
    runTest {
      mockDataSources(RemoteResponseState.NoInternetConnect)
      assertEquals(
        DataState.SuccessState(Competition(null, null, null, arrayListOf(matchDb).mapToMatche())),
        mEPLMatchesRepository.fetchEPLMatches(1).first()
      )
    }

  private suspend fun mockDataSources(
    remoteResponseState: RemoteResponseState<Competition>,
    items: List<MatchDb> = arrayListOf(matchDb)
  ) {
    whenever(mIRemoteDataSource.getCompetitionMatches(1)).thenReturn(
      remoteResponseState
    )
    whenever(mILocalDataSource.getAllSavedMatches()).thenReturn(flow {
      emit(items)
    })
    mEPLMatchesRepository = EPLMatchesRepository(mIRemoteDataSource, mILocalDataSource)
  }
}