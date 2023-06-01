package com.abdelrahman.presentation

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.GeneralError
import com.abdelrahman.ErrorTypes.NetworkError
import com.abdelrahman.ErrorTypes.UnAuthorized
import com.abdelrahman.entity.Competition
import com.abdelrahman.presentation.competition.CompetitionContract.Event
import com.abdelrahman.presentation.competition.CompetitionViewModel
import com.abdelrahman.usecase.competition.IFetchEPLMatchesUseCase
import com.abdelrahman.utils.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
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
class CompetitionViewModelShould {

//  @get:Rule
//  val mainDispatcherRule = MainDispatcherRule()
  private lateinit var competitionViewModel: CompetitionViewModel
  private val fetchMatchesUseCase = mock<IFetchEPLMatchesUseCase>()
  private val mockedCompetition = mock<Competition>()

  @Test
  fun `viewModel call fetch matches once`() = runTest {
    whenever(fetchMatchesUseCase.fetchEPLMatches(1)).thenReturn(flow {
      emit(DataState.SuccessState(mockedCompetition))
    })
    competitionViewModel = CompetitionViewModel(fetchMatchesUseCase)
    verify(fetchMatchesUseCase, times(1)).fetchEPLMatches(1)
  }

  @Test
  fun `viewModel collect success state when use case returns success`() = runTest {
    whenever(fetchMatchesUseCase.fetchEPLMatches(1)).thenReturn(flow {
      emit(DataState.SuccessState(mockedCompetition))
    })
    competitionViewModel = CompetitionViewModel(fetchMatchesUseCase)
    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
    }
    assertEquals(
      DataState.SuccessState(mockedCompetition),
      competitionViewModel.currentState.competitionState
    )
  }

  @Test
  fun `viewModel collect general error state when use case returns general error state`() =
    runTest {
      whenever(fetchMatchesUseCase.fetchEPLMatches(1)).thenReturn(flow {
        emit(DataState.ErrorState(GeneralError))
      })
      competitionViewModel = CompetitionViewModel(fetchMatchesUseCase)
      withContext(Dispatchers.IO) {
        Thread.sleep(1000)
      }
      assertEquals(
        DataState.ErrorState(GeneralError),
        competitionViewModel.currentState.competitionState
      )
    }

  @Test
  fun `viewModel collect network error state when use case returns no network error state`() =
    runTest {
      whenever(fetchMatchesUseCase.fetchEPLMatches(1)).thenReturn(flow {
        emit(DataState.ErrorState(NetworkError))
      })
      competitionViewModel = CompetitionViewModel(fetchMatchesUseCase)
      withContext(Dispatchers.IO) {
        Thread.sleep(1000)
      }
      assertEquals(
        DataState.ErrorState(NetworkError),
        competitionViewModel.currentState.competitionState
      )
    }

  @Test
  fun `viewModel collect unauthorized error state when use case returns unauthorizzed error state`() =
    runTest {
      whenever(fetchMatchesUseCase.fetchEPLMatches(1)).thenReturn(flow {
        emit(DataState.ErrorState(UnAuthorized))
      })
      competitionViewModel = CompetitionViewModel(fetchMatchesUseCase)
      withContext(Dispatchers.IO) {
        Thread.sleep(1000)
      }
      assertEquals(
        DataState.ErrorState(UnAuthorized),
        competitionViewModel.currentState.competitionState
      )
    }

  @Test
  fun `show loader on start of requesting state from use case`() = runTest {
    whenever(fetchMatchesUseCase.fetchEPLMatches(1)).thenReturn(flow {
      emit(DataState.ErrorState(UnAuthorized))
    })
    competitionViewModel = CompetitionViewModel(fetchMatchesUseCase)
    assertEquals(
      true,
      competitionViewModel.currentState.isLoading
    )
  }

  @Test
  fun `hide loader after data is collected`() = runTest {
    whenever(fetchMatchesUseCase.fetchEPLMatches(1)).thenReturn(flow {
      emit(DataState.ErrorState(UnAuthorized))
    })
    competitionViewModel = CompetitionViewModel(fetchMatchesUseCase)
    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
    }
    assertEquals(
      false,
      competitionViewModel.currentState.isLoading
    )
  }

}