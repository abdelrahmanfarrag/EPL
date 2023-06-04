package com.abdelrahman.presentation

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes
import com.abdelrahman.ErrorTypes.GeneralError
import com.abdelrahman.ErrorTypes.NetworkError
import com.abdelrahman.ErrorTypes.UnAuthorized
import com.abdelrahman.entity.GroupedMatches
import com.abdelrahman.entity.MatchDay
import com.abdelrahman.presentation.competition.CompetitionViewModel
import com.abdelrahman.usecase.competition.IFetchEPLMatchesUseCase
import com.abdelrahman.usecase.insertmatch.IUpdateMatchesUseCase
import com.abdelrahman.utils.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Rule
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
@OptIn(ExperimentalCoroutinesApi::class)
class CompetitionViewModelShould {

  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()
  private lateinit var competitionViewModel: CompetitionViewModel
  private val iSaveMatch = mock<IUpdateMatchesUseCase>()
  private val fetchMatchesUseCase = mock<IFetchEPLMatchesUseCase>()
  private val mockedCompetition = HashMap<MatchDay, List<GroupedMatches>>()

  @Test
  fun `viewModel call fetch matches once`() = runTest {
    mockSuccess()
    advanceUntilIdle()
    verify(fetchMatchesUseCase, times(1)).fetchEPLMatches(2021)
  }

  @Test
  fun `viewModel collect success state when use case returns success`() = runTest {
    mockSuccess()
    withContext(Dispatchers.IO) {
      Thread.sleep(2500)
    }
    assertEquals(
      DataState.SuccessState(mockedCompetition),
      competitionViewModel.currentState.competitionState
    )
  }

  @Test
  fun `viewModel collect general error state when use case returns general error state`() =
    runTest {
      mockErrorType(GeneralError)
      withContext(Dispatchers.IO) {
        Thread.sleep(1800)
      }
      assertEquals(
        DataState.ErrorState(GeneralError),
        competitionViewModel.currentState.competitionState
      )
    }

  @Test
  fun `viewModel collect network error state when use case returns no network error state`() =
    runTest {
      mockErrorType(NetworkError)
      competitionViewModel = CompetitionViewModel(fetchMatchesUseCase, iSaveMatch)
      withContext(Dispatchers.IO) {
        Thread.sleep(1800)
      }
      assertEquals(
        DataState.ErrorState(NetworkError),
        competitionViewModel.currentState.competitionState
      )
    }

  @Test
  fun `viewModel collect unauthorized error state when use case returns unauthorizzed error state`() =
    runTest {
      mockErrorType(UnAuthorized)
      withContext(Dispatchers.IO) {
        Thread.sleep(1800)
      }
      assertEquals(
        DataState.ErrorState(UnAuthorized),
        competitionViewModel.currentState.competitionState
      )
    }

  @Test
  fun `show loader on start of requesting state from use case`() = runTest {
    mockErrorType(UnAuthorized)
    assertEquals(
      true,
      competitionViewModel.currentState.isLoading
    )
  }


  @Test
  fun `hide loader after data is collected successfully`() = runTest {
    mockSuccess()
    withContext(Dispatchers.IO) {
      Thread.sleep(2500)
    }
    assertEquals(
      false,
      competitionViewModel.currentState.isLoading
    )
  }

  @Test
  fun `hide loader after data state is not success`() = runTest {
    mockErrorType(GeneralError)
    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
    }
    assertEquals(
      false,
      competitionViewModel.currentState.isLoading
    )
  }

  @Test
  fun `returns server error message in case of error happens`() = runTest {
    whenever(fetchMatchesUseCase.fetchEPLMatches(2021)).thenReturn(flow {
      emit(DataState.ServerErrorMessage("Something Went wrong"))
    })
    competitionViewModel = CompetitionViewModel(fetchMatchesUseCase, iSaveMatch)
    withContext(Dispatchers.IO) {
      Thread.sleep(1800)
    }
    assertEquals(
      DataState.ServerErrorMessage("Something Went wrong"),
      competitionViewModel.currentState.competitionState
    )
  }

  private suspend fun mockErrorType(errorTypes: ErrorTypes) {
    whenever(fetchMatchesUseCase.fetchEPLMatches(2021)).thenReturn(flow {
      emit(DataState.ErrorState(errorTypes))
    })
    competitionViewModel = CompetitionViewModel(fetchMatchesUseCase, iSaveMatch)
  }

  private suspend fun mockSuccess() {
    whenever(fetchMatchesUseCase.fetchEPLMatches(2021)).thenReturn(flow {
      emit(DataState.SuccessState(mockedCompetition.toSortedMap(
        compareByDescending {
          it.day
        }
      )))
    })
    competitionViewModel = CompetitionViewModel(fetchMatchesUseCase, iSaveMatch)
  }

}