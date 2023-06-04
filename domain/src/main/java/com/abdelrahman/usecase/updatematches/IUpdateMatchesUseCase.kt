package com.abdelrahman.usecase.updatematches

import com.abdelrahman.entity.Match

/**
 * Authored by Abdelrahman Ahmed on 03 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
interface IUpdateMatchesUseCase {
  suspend fun updateMatchesTable(match: Match)
}