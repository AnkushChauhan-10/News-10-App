package com.example.news10.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import com.example.news10.response.NetworkStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class PermissionUtil {
        fun isInternetAvailable(context: Context): Flow<NetworkStatus>{
            var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return callbackFlow {
                val callback = object : ConnectivityManager.NetworkCallback(){

                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        launch { send(NetworkStatus.Available) }
                    }

                    override fun onLosing(network: Network, maxMsToLive: Int) {
                        super.onLosing(network, maxMsToLive)
                        launch { send(NetworkStatus.Unavailable) }
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        launch { send(NetworkStatus.Unavailable) }
                    }

                    override fun onUnavailable() {
                        super.onUnavailable()
                        launch { send(NetworkStatus.Unavailable) }
                    }
                }
                connectivityManager.registerDefaultNetworkCallback(callback)
                awaitClose {
                    connectivityManager.unregisterNetworkCallback(callback)
                }
            }.distinctUntilChanged()
        }


    fun checkInternet(context: Context):Boolean{
        var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                else -> false
            }
        }else{
            //If ver below M
            @Suppress("DEPRECATION") val network =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return network.isConnected
        }
    }

}