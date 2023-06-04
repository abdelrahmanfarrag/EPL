package com.abdelrahman.data.datasource.local.datasource

import com.abdelrahman.data.datasource.local.database.MatchDatabase
import com.abdelrahman.data.datasource.local.models.MatchDb
import com.abdelrahman.entity.Match
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 03 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class LocalDataSource @Inject constructor(private val mMatchDatabase: MatchDatabase) :
  ILocalDataSource {

  override suspend fun upsertAMatch(match: Match): Long {
    return mMatchDatabase.dao.upsertMatch(
      MatchDb(
        homeTeamName = match.homeTeamName,
        homeTeamScore = match.homeTeamScore,
        awayTeamName = match.awayTeamName,
        awayTeamScore = match.awayTeamScore,
        matchId = match.matchId,
        status = match.status,
        matchTime = match.matchDate,
        matchDay = match.matchDay
      )
    )
  }

  override suspend fun deleteAMatch(id: Int?) {
    mMatchDatabase.dao.deleteMatch(id)
  }

  override suspend fun getAllSavedMatches(): Flow<List<MatchDb>> {
    return mMatchDatabase.dao.getStoredMatches()
  }
}