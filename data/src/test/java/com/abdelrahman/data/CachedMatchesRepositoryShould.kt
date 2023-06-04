package com.abdelrahman.data

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.EmptyList
import com.abdelrahman.data.datasource.local.datasource.LocalDataSource
import com.abdelrahman.data.datasource.local.models.MatchDb
import com.abdelrahman.data.datasource.repository.CachedMatchesRepository
import com.abdelrahman.entity.Match
import com.abdelrahman.repository.ICachedMatchesRepository
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
class CachedMatchesRepositoryShould {

  private lateinit var cachedMatchesRepositoryShould: ICachedMatchesRepository
  private val iLocalDataSource = mock<LocalDataSource>()
  private val mockedMatchDB = mock<MatchDb>()
  private val stubbedMatch = listOf(
    Match(
      null,
      0, null, 0, 0, null, null, 0, true
    )
  )

  @Test
  fun `call get list of matches once`() = runTest {
    mockLocalDataSource()
    cachedMatchesRepositoryShould.getAllSavedMatches()
    verify(iLocalDataSource, times(1)).getAllSavedMatches()
  }

  @Test
  fun `return list of cached matches in case of user cache matches`() = runTest {
    mockLocalDataSource(listOf(mockedMatchDB))
    assertEquals(
      DataState.SuccessState(stubbedMatch),
      cachedMatchesRepositoryShould.getAllSavedMatches().first()
    )
  }

  @Test
  fun `return empty list it should return error type of EmptyList`() = runTest {
    mockLocalDataSource()
    assertEquals(
      DataState.ErrorState(EmptyList),
      cachedMatchesRepositoryShould.getAllSavedMatches().first()
    )
  }

  private suspend fun mockLocalDataSource(matchDbList: List<MatchDb> = listOf()) {
    whenever(iLocalDataSource.getAllSavedMatches()).thenReturn(flow {
      emit(matchDbList)
    })
    cachedMatchesRepositoryShould = CachedMatchesRepository(iLocalDataSource)

  }
}