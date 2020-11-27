package com.ebo.mobileshop.utilities

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class Network {

    companion object {
        // used for suppressing deprecated functions can be found in intention actions
        // here we are suppressing everything inside networkAvailable()
        @Suppress("DEPRECATION")
        fun isAvailable(app: Application): Boolean {
            val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                    // implicit type of object
                    as ConnectivityManager

            val networkInfo = connectivityManager.activeNetworkInfo

            return networkInfo?.isConnectedOrConnecting ?: false
        }
    }

}