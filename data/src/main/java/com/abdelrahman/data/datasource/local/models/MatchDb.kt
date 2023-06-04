package com.abdelrahman.data.datasource.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Authored by Abdelrahman Ahmed on 03 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Entity(tableName = "Match")
data class MatchDb(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val awayTeamName: String?,
  val awayTeamScore: Int?,
  val homeTeamName: String?,
  val homeTeamScore: Int?,
  val matchDay: Int?,
  val status: String?,
  val matchTime: String?,
  val matchId: Int?
)