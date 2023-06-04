package com.abdelrahman.data.datasource.repository

import com.abdelrahman.DataState
import com.abdelrahman.ErrorTypes.EmptyList
import com.abdelrahman.data.datasource.local.datasource.ILocalDataSource
import com.abdelrahman.data.datasource.local.models.mapToMatche
import com.abdelrahman.entity.Match
import com.abdelrahman.models.toListOfMatchEntity
import com.abdelrahman.repository.ICachedMatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class CachedMatchesRepository @Inject constructor(private val iLocalDataSource: ILocalDataSource) :
  ICachedMatchesRepository {

  override suspend fun getAllSavedMatches(): Flow<DataState<List<Match>>> {
    return iLocalDataSource.getAllSavedMatches().map {
      if (it.isEmpty())
        return@map DataState.ErrorState(EmptyList)
      else
        return@map DataState.SuccessState(it.mapToMatche().toListOfMatchEntity() ?: arrayListOf())

    }
  }
}