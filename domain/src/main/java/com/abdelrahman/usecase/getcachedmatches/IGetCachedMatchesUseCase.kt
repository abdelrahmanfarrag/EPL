package com.abdelrahman.usecase.getcachedmatches

import com.abdelrahman.DataState
import com.abdelrahman.entity.Match
import kotlinx.coroutines.flow.Flow

interface IGetCachedMatchesUseCase {

  suspend fun getAllSavedMatches(): Flow<DataState<List<Match>>>

}
