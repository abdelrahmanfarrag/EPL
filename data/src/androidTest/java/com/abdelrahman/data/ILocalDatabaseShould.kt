package com.abdelrahman.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abdelrahman.data.datasource.local.dao.MatchDAO
import com.abdelrahman.data.datasource.local.database.MatchDatabase
import com.abdelrahman.data.datasource.local.models.MatchDb
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Authored by Abdelrahman Ahmed on 04 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@RunWith(AndroidJUnit4::class)
class ILocalDatabaseShould {

  private lateinit var matchDAO: MatchDAO
  private lateinit var db: MatchDatabase

  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(
      context, MatchDatabase::class.java
    ).build()
    matchDAO = db.dao
  }

  @Test
  @Throws(Exception::class)
  suspend fun writeUserAndReadInList() {
    val matchDb = MatchDb(
      awayTeamName = "",
      awayTeamScore = 0,
      homeTeamName = "",
      homeTeamScore = 0,
      matchDay = 0,
      status = "",
      matchTime = "",
      matchId = 0
    )
    val result = matchDAO.upsertMatch(matchDb)
    assertThat(1L, equalTo(result))
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }


}