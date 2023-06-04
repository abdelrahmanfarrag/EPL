package com.abdelrahman.entity

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
  data class Match(
  val awayTeamName: String?,
  val awayTeamScore: Int?,
  val homeTeamName: String?,
  val homeTeamScore: Int?,
  val matchDay: Int?,
  val status: String?,
  val matchTime: String?,
  val matchId: Int?,
  val isFavorite: Boolean
)
