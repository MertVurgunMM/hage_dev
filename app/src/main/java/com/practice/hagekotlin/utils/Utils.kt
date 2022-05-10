package com.practice.hagekotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkManager(context: Context) {

    private val manager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isAvailable(): Boolean {
        val activeCapability = manager.getNetworkCapabilities(manager.activeNetwork)
        return activeCapability != null &&
                (
                        activeCapability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                activeCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        )
    }
}