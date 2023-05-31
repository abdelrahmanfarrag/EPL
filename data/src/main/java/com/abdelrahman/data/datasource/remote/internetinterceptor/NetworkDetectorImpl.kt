package com.abdelrahman.data.datasource.remote.internetinterceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

/**
 * Authored by Abdelrahman Ahmed on 31 May, 2023.
 * Contact: abdelrahmanfarrag291@gmail.com
 * by :ABDELRAHMAN
 */
class NetworkDetectorImpl @Inject constructor(private val mContext: Context) :
  INetworkDetector {

  override fun isConnected(): Boolean {
    return checkNetworkState()
  }


  private fun checkNetworkState(): Boolean {
    val connectivityManager = createConnectivityManager()
    val network = connectivityManager.activeNetwork
    val connection = connectivityManager.getNetworkCapabilities(network)
    return connection?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true || connection?.hasTransport(
      NetworkCapabilities.TRANSPORT_WIFI
    ) == true || connection?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
  }

  private fun createConnectivityManager(): ConnectivityManager {
    return mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  }
}