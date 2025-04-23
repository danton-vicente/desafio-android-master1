package com.picpay.desafio.android.commons.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager

class NetworkCheckerImpl(private val context: Context) : NetworkChecker {
    override fun hasNetworkConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
