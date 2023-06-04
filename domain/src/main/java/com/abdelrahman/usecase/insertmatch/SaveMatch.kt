package com.abdelrahman.usecase.insertmatch

import com.abdelrahman.entity.Match
import com.abdelrahman.repository.IEPLMatchesRepository
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 03 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class SaveMatch @Inject constructor(private val iEPLMatchesRepository: IEPLMatchesRepository) :
  ISavedMatch {

  override suspend fun updateMatchesTable(match: Match) {
    iEPLMatchesRepository.updateMatchesTable(match)
  }
}