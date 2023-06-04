package com.abdelrahman.domain.competition

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.EmptyList
import com.abdelrahman.entity.Match
import com.abdelrahman.repository.ICachedMatchesRepository
import com.abdelrahman.usecase.getcachedmatches.GetCachedMatchesUseCase
import com.abdelrahman.usecase.getcachedmatches.IGetCachedMatchesUseCase
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
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class GetCachedMatchesUseCaseShould {

  private lateinit var iGetCachedMatchesUseCase: IGetCachedMatchesUseCase
  private val cachedMatchesRepository = mock<ICachedMatchesRepository>()

  @Test
  fun `call getCachedMatchesRepository once`() = runTest {
    mockRepositoryResult(DataState.SuccessState(listOf()))
    iGetCachedMatchesUseCase.getAllSavedMatches()
    verify(cachedMatchesRepository, times(1)).getAllSavedMatches()
  }

  @Test
  fun `return matches`() = runTest {
    mockRepositoryResult(DataState.SuccessState(listOf()))
    assertEquals(
      DataState.SuccessState<List<Match>>(listOf()),
      iGetCachedMatchesUseCase.getAllSavedMatches().first()
    )
  }

  @Test
  fun `return empty list error in case of there is no matches in local database`() = runTest {
    mockRepositoryResult(DataState.ErrorState(EmptyList))
    assertEquals(
      DataState.ErrorState(EmptyList),
      iGetCachedMatchesUseCase.getAllSavedMatches().first()
    )
  }

  private suspend fun mockRepositoryResult(result: DataState<List<Match>>) {
    whenever(cachedMatchesRepository.getAllSavedMatches()).thenReturn(flow {
      emit(result)
    })
    iGetCachedMatchesUseCase = GetCachedMatchesUseCase(cachedMatchesRepository)

  }
}