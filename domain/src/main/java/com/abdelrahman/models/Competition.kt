package com.abdelrahman.models

import com.abdelrahman.entity.Match
import com.google.gson.annotations.SerializedName

data class Competition(
  @SerializedName("competition")
  val competition: CompetitionX?=null,
  @SerializedName("count")
  val count: Int?=null,
  @SerializedName("filters")
  val filters: Filters?=null,
  @SerializedName("matches")
  val matches: List<Matche>?= arrayListOf()
)

fun List<Matche>?.toListOfMatchEntity() = this?.map {
  Match(
    it.awayTeam?.name,
    it.score?.fullTime?.awayTeam,
    it.homeTeam?.name,
    it.score?.fullTime?.homeTeam,
    it.matchday
  )
}