package com.abdelrahman.data.datasource.remote

import com.abdelrahman.data.datasource.remote.Constants.Path.COMPETITION_ID

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
object Constants {

  object URLS {
    const val BASE_URL = "https://api.football-data.org/v2/"
  }

  object ENDPOINTS {
    const val MATCHES = "competitions/{$COMPETITION_ID}/matches"

  }

  object Path {
    const val COMPETITION_ID = "competition_id"
    const val EPL_COMPETITION_ID = 2021
  }

  object Header {
    const val TOKEN_HEADER = "X-Auth-Token"
  }

  const val SUCCESS_CODE = 200
  const val UNAUTHORIZED_ERROR_CODE = 403
}