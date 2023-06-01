package com.abdelrahman.data

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.GeneralError
import com.abdelrahman.ErrorTypes.NetworkError
import com.abdelrahman.ErrorTypes.UnAuthorized
import com.abdelrahman.data.datasource.remote.RemoteResponseState
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.IRemoteDataSource
import com.abdelrahman.models.Competition
import com.abdelrahman.data.datasource.repository.EPLMatchesRepository
import com.abdelrahman.repository.IEPLMatchesRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
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
  private val competition = mock<Competition>()
  private val successState = DataState.SuccessState(competition)

  @Test
  fun `call get competition matches once`() = runTest {
    whenever(mIRemoteDataSource.getCompetitionMatches(1)).thenReturn(
      RemoteResponseState.ValidResponse(competition)
    )
    mEPLMatchesRepository = EPLMatchesRepository(mIRemoteDataSource)
    mEPLMatchesRepository.fetchEPLMatches(1).first()
    verify(mIRemoteDataSource, times(1)).getCompetitionMatches(1)
  }

  @Test
  fun `call get competition matches returns un authorized  state when data source responds with unauthorized`() =
    runTest {
      whenever(mIRemoteDataSource.getCompetitionMatches(1)).thenReturn(RemoteResponseState.NotAuthorized)
      mEPLMatchesRepository = EPLMatchesRepository(mIRemoteDataSource)
      mEPLMatchesRepository.fetchEPLMatches(1).first()
      assertEquals(
        DataState.ErrorState(UnAuthorized),
        mEPLMatchesRepository.fetchEPLMatches(1).first()
      )
    }

  @Test
  fun `call get competition match returns success data state when remote data state success `() =
    runTest {
      whenever(mIRemoteDataSource.getCompetitionMatches(1)).thenReturn(
        RemoteResponseState.ValidResponse(competition)
      )
      mEPLMatchesRepository = EPLMatchesRepository(mIRemoteDataSource)
      mEPLMatchesRepository.fetchEPLMatches(1).first()
      assertEquals(successState, mEPLMatchesRepository.fetchEPLMatches(1).first())
    }

  @Test
  fun `call get competition matches return error of type general when something went wrong with remote data source`() =
    runTest {
      whenever(mIRemoteDataSource.getCompetitionMatches(1)).thenReturn(RemoteResponseState.NotValidResponse)
      mEPLMatchesRepository = EPLMatchesRepository(mIRemoteDataSource)
      mEPLMatchesRepository.fetchEPLMatches(1).first()
      assertEquals(
        DataState.ErrorState(GeneralError),
        mEPLMatchesRepository.fetchEPLMatches(1).first()
      )
    }

  @Test
  fun `call get competition matches returns error of type no network when no network is returned from data source`() =
    runTest {
      whenever(mIRemoteDataSource.getCompetitionMatches(1)).thenReturn(
        RemoteResponseState.NoInternetConnect
      )
      mEPLMatchesRepository = EPLMatchesRepository(mIRemoteDataSource)
      mEPLMatchesRepository.fetchEPLMatches(1).first()
      assertEquals(
        DataState.ErrorState(NetworkError),
        mEPLMatchesRepository.fetchEPLMatches(1).first()
      )
    }
}