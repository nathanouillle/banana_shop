package com.example.onlinepurchase.activity

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.database.OnlinePurchaseDatabase
import com.example.onlinepurchase.activity.preferences.SharedPreferences

class OnlinePurchase : Application() {

    companion object {
        lateinit var preferences: SharedPreferences
        lateinit var onlinePurchaseContext: Context
        lateinit var onlinePurchaseDatabase: OnlinePurchaseDatabase
        var cart = mutableListOf<Product>()

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        }

        fun showNoInternetDialog(context: Context) {
            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle("No Internet Connection")
            builder.setMessage("Please check your internet connection and try again.")
            builder.setPositiveButton("Settings") { _, _ ->
                context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
            }
            builder.setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
                (context as OnlinePurchase).finish()
            }
            builder.show()
        }
    }

    private fun finish() {
        // close the app
        ((onlinePurchaseContext as OnlinePurchase).finish())
    }

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
        onlinePurchaseContext = applicationContext
        onlinePurchaseDatabase = OnlinePurchaseDatabase.roomDatabase
    }
}