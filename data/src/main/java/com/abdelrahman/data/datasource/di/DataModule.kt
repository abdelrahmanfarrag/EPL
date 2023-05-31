package com.abdelrahman.data.datasource.di

import android.content.Context
import com.abdelrahman.data.datasource.remote.validateresponse.ValidateRemoteResponse
import com.abdelrahman.data.datasource.remote.internetinterceptor.INetworkDetector
import com.abdelrahman.data.datasource.remote.internetinterceptor.NetworkDetectorImpl
import com.abdelrahman.data.datasource.remote.validateresponse.IValidateRemoteResponse
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
  fun providesGson(): Gson {
    return Gson()
  }

  @Provides
  @ViewModelScoped
  fun providesValidateRemoteResponse(
    gson: Gson,
    iNetworkDetector: INetworkDetector
  ): IValidateRemoteResponse {
    return ValidateRemoteResponse(gson, iNetworkDetector)
  }
}