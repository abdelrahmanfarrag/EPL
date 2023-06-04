package com.abdelrahman.data.datasource.local.models

import com.abdelrahman.models.AwayTeam
import com.abdelrahman.models.FullTime
import com.abdelrahman.models.HomeTeam
import com.abdelrahman.models.Matche
import com.abdelrahman.models.Score


/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */

fun List<MatchDb>.mapToMatche(): List<Matche> {
  val listOfMatch = arrayListOf<Matche>()
  val list = map {
    Matche(
      awayTeam = AwayTeam(null, it.awayTeamName),
      homeTeam = HomeTeam(null, it.homeTeamName),
      score = Score(null, null, FullTime(it.awayTeamScore, it.homeTeamScore), null, null, null),
      lastUpdated = null,
      group = null,
      matchday = it.matchDay,
      odds = null,
      id = it.matchId,
      season = null,
      stage = null,
      utcDate = it.matchTime,
      referees = null,
      status = it.status,
      isFavorite = true
    )
  }
  listOfMatch.addAll(list)
  return listOfMatch
}