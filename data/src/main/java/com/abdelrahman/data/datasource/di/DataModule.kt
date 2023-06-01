package com.abdelrahman.data.datasource.di

import android.content.Context
import com.abdelrahman.data.datasource.remote.API
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.IRemoteDataSource
import com.abdelrahman.data.datasource.remote.datasource.apidatasource.RemoteDataSource
import com.abdelrahman.data.datasource.remote.validateresponse.ValidateRemoteResponse
import com.abdelrahman.data.datasource.remote.networkdetector.INetworkDetector
import com.abdelrahman.data.datasource.remote.networkdetector.NetworkDetectorImpl
import com.abdelrahman.data.datasource.remote.interceptors.tokeninterceptor.ITokenInterceptor
import com.abdelrahman.data.datasource.remote.interceptors.tokeninterceptor.TokenInterceptor
import com.abdelrahman.data.datasource.remote.validateresponse.IValidateRemoteResponse
import com.abdelrahman.data.datasource.repository.EPLMatchesRepository
import com.abdelrahman.repository.IEPLMatchesRepository
import com.google.gson.Gson
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
class DataModule {

  @Provides
  @ViewModelScoped
  fun providesINetworkDetector(context: Context): INetworkDetector {
    return NetworkDetectorImpl(context)
  }

  @Provides
  @ViewModelScoped
  fun providesValidateRemoteResponse(
    gson: Gson,
    iNetworkDetector: INetworkDetector
  ): IValidateRemoteResponse {
    return ValidateRemoteResponse(gson, iNetworkDetector)
  }

  @Provides
  @ViewModelScoped
  fun providesRemoteDataSource(
    api: API,
    iValidateRemoteResponse: IValidateRemoteResponse
  ): IRemoteDataSource {
    return RemoteDataSource(iValidateRemoteResponse, api)
  }

  @Provides
  @ViewModelScoped
  fun provideEPLMatchesRepository(iRemoteDataSource: IRemoteDataSource): IEPLMatchesRepository {
    return EPLMatchesRepository(iRemoteDataSource)
  }
}