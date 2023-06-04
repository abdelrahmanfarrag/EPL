package com.abdelrahman.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abdelrahman.data.datasource.local.dao.MatchDAO
import com.abdelrahman.data.datasource.local.models.MatchDb

/**
 * Authored by Abdelrahman Ahmed on 03 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Database(entities = [MatchDb::class], version = 1)
abstract class MatchDatabase : RoomDatabase() {

  abstract val dao: MatchDAO
}