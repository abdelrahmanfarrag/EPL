package com.abdelrahman.data.datasource.di

import com.abdelrahman.data.datasource.local.datasource.ILocalDataSource
import com.abdelrahman.data.datasource.local.datasource.LocalDataSource
import com.abdelrahman.data.datasource.remote.API
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.IRemoteDataSource
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.RemoteDataSource
import com.abdelrahman.data.datasource.remote.networkdetector.INetworkDetector
import com.abdelrahman.data.datasource.remote.validateresponse.IValidateRemoteResponse
import com.abdelrahman.data.datasource.remote.validateresponse.ValidateRemoteResponse
import com.abdelrahman.data.datasource.repository.CachedMatchesRepository
import com.abdelrahman.data.datasource.repository.EPLMatchesRepository
import com.abdelrahman.repository.ICachedMatchesRepository
import com.abdelrahman.repository.IEPLMatchesRepository
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

  companion object {
    @Provides
    @ViewModelScoped
    fun providesValidateRemoteResponse(
      gson: Gson
    ): IValidateRemoteResponse {
      return ValidateRemoteResponse(gson)
    }

    @Provides
    @ViewModelScoped
    fun providesRemoteDataSource(
      api: API,
      iValidateRemoteResponse: IValidateRemoteResponse,
      iNetworkDetector: INetworkDetector
    ): IRemoteDataSource {
      return RemoteDataSource(iValidateRemoteResponse, iNetworkDetector, api)
    }

    @Provides
    @ViewModelScoped
    fun provideEPLMatchesRepository(
      iRemoteDataSource: IRemoteDataSource,
      iLocalDataSource: ILocalDataSource
    ): IEPLMatchesRepository {
      return EPLMatchesRepository(iRemoteDataSource, iLocalDataSource)
    }
  }

  @Binds
  @ViewModelScoped
  abstract fun bindsLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource

  @Binds
  @ViewModelScoped
  abstract fun bindsCachedMatchesRepository(cachedMatchesRepository: CachedMatchesRepository): ICachedMatchesRepository
}