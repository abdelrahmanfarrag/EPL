package com.abdelrahman.presentation

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.EmptyList
import com.abdelrahman.entity.Match
import com.abdelrahman.presentation.cachedmatches.CachedMatchContract.Event
import com.abdelrahman.presentation.cachedmatches.CachedMatchesViewModel
import com.abdelrahman.usecase.getcachedmatches.IGetCachedMatchesUseCase
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
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class CachedMatchesViewModelShould {

  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()
  private lateinit var mCachedMatchesViewModel: CachedMatchesViewModel
  private val mIGetCachedMatchesUseCase = mock<IGetCachedMatchesUseCase>()
  private val match = mock<Match>()

  @Test
  fun `call useCase once`() = runTest {
    whenever(mIGetCachedMatchesUseCase.getAllSavedMatches()).thenReturn(flow {
      emit(DataState.SuccessState(arrayListOf()))
    })
    mCachedMatchesViewModel = CachedMatchesViewModel(mIGetCachedMatchesUseCase)
    mCachedMatchesViewModel.setEvent(Event.GetCachedMatches)
    advanceUntilIdle()
    verify(mIGetCachedMatchesUseCase, times(1)).getAllSavedMatches()
  }

  @Test
  fun `get cachedMatchesList`() = runTest {
    whenever(mIGetCachedMatchesUseCase.getAllSavedMatches()).thenReturn(flow {
      emit(DataState.SuccessState(arrayListOf(match)))
    })
    mCachedMatchesViewModel = CachedMatchesViewModel(mIGetCachedMatchesUseCase)
    mCachedMatchesViewModel.setEvent(Event.GetCachedMatches)
    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
    }
    assertEquals(
      arrayListOf(match),
      mCachedMatchesViewModel.currentState.matches
    )
    assertEquals(
      false,
      mCachedMatchesViewModel.currentState.isError
    )
  }

  @Test
  fun `get Error when list if empty`() = runTest {
    whenever(mIGetCachedMatchesUseCase.getAllSavedMatches()).thenReturn(flow {
      emit(DataState.ErrorState(EmptyList))
    })
    mCachedMatchesViewModel = CachedMatchesViewModel(mIGetCachedMatchesUseCase)
    mCachedMatchesViewModel.setEvent(Event.GetCachedMatches)

    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
    }
    assertEquals(
      true,
      mCachedMatchesViewModel.currentState.isError
    )
    assertEquals(
      null,
      mCachedMatchesViewModel.currentState.matches
    )
  }
}