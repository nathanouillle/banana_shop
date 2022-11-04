package com.example.onlinepurchase.activity

import android.app.Application
import com.example.onlinepurchase.activity.preferences.SharedPreferences

class OnlinePurchase: Application() {

    companion object {
        lateinit var preferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
    }
}