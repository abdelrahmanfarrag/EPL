package com.abdelrahman.models

import com.abdelrahman.entity.Match
import com.abdelrahman.utils.DateFormat
import com.abdelrahman.utils.convertToDesiredDateFormat
import com.google.gson.annotations.SerializedName

data class Competition(
  @SerializedName("competition")
  val competition: CompetitionX? = null,
  @SerializedName("count")
  val count: Int? = null,
  @SerializedName("filters")
  val filters: Filters? = null,
  @SerializedName("matches")
  val matches: List<Matche>? = arrayListOf()
)

fun List<Matche>?.toListOfMatchEntity() = this?.map {
  Match(
    it.awayTeam?.name,
    it.score?.fullTime?.awayTeam,
    it.homeTeam?.name,
    it.score?.fullTime?.homeTeam,
    it.matchday,
    it.status,
    it.utcDate.convertToDesiredDateFormat(
      DateFormat.SERVER_DATE_FORMAT,
      DateFormat.USER_FRIENDLY_FORMAT
    ),
    it.id,
    it.isFavorite
  )
}
