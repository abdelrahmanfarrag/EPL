package com.abdelrahman.models


import com.google.gson.annotations.SerializedName

data class FullTime(
    @SerializedName("awayTeam")
    val awayTeam: Int?,
    @SerializedName("homeTeam")
    val homeTeam: Int?
)