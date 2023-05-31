package com.abdelrahman.models


import com.google.gson.annotations.SerializedName

data class Odds(
    @SerializedName("msg")
    val msg: String?
)