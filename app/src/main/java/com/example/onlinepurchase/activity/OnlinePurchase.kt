package com.example.onlinepurchase.activity

import android.app.Application
import android.content.Context
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.database.OnlinePurchaseDatabase
import com.example.onlinepurchase.activity.preferences.SharedPreferences

class OnlinePurchase: Application() {

    companion object {
        lateinit var preferences: SharedPreferences
        lateinit var onlinePurchaseContext: Context
        lateinit var onlinePurchaseDatabase: OnlinePurchaseDatabase
        var cart = mutableListOf<Product>()
    }

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
        onlinePurchaseContext = applicationContext
        onlinePurchaseDatabase = OnlinePurchaseDatabase.roomDatabase
    }
}