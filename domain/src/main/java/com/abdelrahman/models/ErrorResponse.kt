package com.abdelrahman.models

import com.google.gson.annotations.SerializedName

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
data class ErrorResponse(
  @SerializedName("message") val message:String?,
  @SerializedName("errorCode") val code:Int?
)
