package com.abdelrahman.domain.competition

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes
import com.abdelrahman.ErrorTypes.GeneralError
import com.abdelrahman.ErrorTypes.NetworkError
import com.abdelrahman.ErrorTypes.UnAuthorized
import com.abdelrahman.entity.GroupedMatches
import com.abdelrahman.models.Competition
import com.abdelrahman.repository.IEPLMatchesRepository
import com.abdelrahman.usecase.competition.FetchEPLMatchesUseCase
import com.abdelrahman.usecase.competition.IFetchEPLMatchesUseCase
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
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class FetchEPLMatchesUseCaseShould {

  private lateinit var mIFetchEPLMatchesUseCase: IFetchEPLMatchesUseCase
  private val mIEPLMatchesRepository = mock<IEPLMatchesRepository>()
  private val mMockedCompetition = mock<Competition>()
  private val mStubbedCompetition = HashMap<Int, List<GroupedMatches>>()

  @Test
  fun `should call repository once`() = runTest {
    createSuccessMockedObject()
    verify(mIEPLMatchesRepository, times(1)).fetchEPLMatches(1)
  }

  @Test
  fun `should return no network error when repository returns error of type network`() = runTest {
    createFailureMockedObject(NetworkError)
    assertEquals(
      DataState.ErrorState(NetworkError),
      mIFetchEPLMatchesUseCase.fetchEPLMatches(1).first()
    )
  }


  @Test
  fun `should return general error when API responds with general error`() = runTest {
    createFailureMockedObject(GeneralError)
    assertEquals(
      DataState.ErrorState(GeneralError),
      mIFetchEPLMatchesUseCase.fetchEPLMatches(1).first()
    )
  }

  @Test
  fun `should return unauthorized error when API responds with unauthorized error`() = runTest {
    createFailureMockedObject(UnAuthorized)
    assertEquals(
      DataState.ErrorState(UnAuthorized),
      mIFetchEPLMatchesUseCase.fetchEPLMatches(1).first()
    )
  }

  @Test
  fun `should return competition of entity when repository got the data successfully from remote datasource`() =
    runTest {
      createSuccessMockedObject()
      assertEquals(
        DataState.SuccessState(mStubbedCompetition.toSortedMap()),
        mIFetchEPLMatchesUseCase.fetchEPLMatches(1).first()
      )
    }

  private suspend fun createFailureMockedObject(errorTypes: ErrorTypes) {
    whenever(mIEPLMatchesRepository.fetchEPLMatches(1)).thenReturn(flow {
      emit(DataState.ErrorState(errorTypes))
    })
    mIFetchEPLMatchesUseCase = FetchEPLMatchesUseCase(mIEPLMatchesRepository)
    mIFetchEPLMatchesUseCase.fetchEPLMatches(1)
  }

  private suspend fun createSuccessMockedObject() {
    whenever(mIEPLMatchesRepository.fetchEPLMatches(1)).thenReturn(flow {
      emit(DataState.SuccessState(mMockedCompetition))
    })
    mIFetchEPLMatchesUseCase = FetchEPLMatchesUseCase(mIEPLMatchesRepository)
    mIFetchEPLMatchesUseCase.fetchEPLMatches(1)
  }
}