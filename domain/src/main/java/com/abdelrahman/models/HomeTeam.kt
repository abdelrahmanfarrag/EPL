package com.abdelrahman.models


import com.google.gson.annotations.SerializedName

data class HomeTeam(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)