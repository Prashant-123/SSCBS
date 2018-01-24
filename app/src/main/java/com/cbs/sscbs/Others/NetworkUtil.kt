package com.cbs.sscbs.Others
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager

/**
 * Created by Tanya on 1/24/2018.
 */
object NetworkUtil {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager
                .activeNetworkInfo.isConnected
    }

    fun isWifiNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager
        return connectivityManager.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    fun enableWifi(context: Context) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as
                WifiManager
        wifiManager.isWifiEnabled = true
    }
}

