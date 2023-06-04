package com.abdelrahman.di

import com.abdelrahman.usecase.competition.FetchEPLMatchesUseCase
import com.abdelrahman.usecase.competition.IFetchEPLMatchesUseCase
import com.abdelrahman.usecase.getcachedmatches.GetCachedMatchesUseCase
import com.abdelrahman.usecase.getcachedmatches.IGetCachedMatchesUseCase
import com.abdelrahman.usecase.updatematches.IUpdateMatchesUseCase
import com.abdelrahman.usecase.updatematches.UpdateMatchesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Authored by Abdelrahman Ahmed on 01 Jun, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

  @Binds
  @ViewModelScoped
  abstract fun bindsGetCachedMatchesUseCase(cachedMatchesUseCase: GetCachedMatchesUseCase): IGetCachedMatchesUseCase

  @Binds
  @ViewModelScoped
  abstract fun bindsSavedMatchesUseCase(updateMatchesUseCase: UpdateMatchesUseCase): IUpdateMatchesUseCase

  @Binds
  @ViewModelScoped
  abstract fun bindsFetchEPLMatches(fetchEPLMatchesUseCase: FetchEPLMatchesUseCase): IFetchEPLMatchesUseCase
}