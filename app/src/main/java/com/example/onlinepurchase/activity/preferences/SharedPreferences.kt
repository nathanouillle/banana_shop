package com.example.onlinepurchase.activity.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {

    private val PREFS_NAME = "com.example.onlinepurchase.sharedPreferences"

    private val preferences : SharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    private val KEY_USER_PHONE = "USER_NAME"
    private val KEY_USER_EMAIL = "USER_EMAIL"
    private val KEY_USER_ADDRESS = "USER_ADDRESS"
    private val KEY_USER_CATEGORY = "USER_CATEGORY"
    private val KEY_USER_PRODUCT = "USER_PRODUCT"

    fun setUserPhone(userName: String) {
        preferences.edit().putString(KEY_USER_PHONE,userName).apply()
    }

    fun getUserPhone(): String {
        return preferences.getString(KEY_USER_PHONE,"") ?: ""
    }

    fun setUserEmail(userEmail: String) {
        preferences.edit().putString(KEY_USER_EMAIL,userEmail).apply()
    }

    fun getUserEmail(): String {
        return preferences.getString(KEY_USER_EMAIL,"") ?: ""
    }

    fun setUserAddress(userAddress: String) {
        preferences.edit().putString(KEY_USER_ADDRESS,userAddress).apply()
    }

    fun getUserAddress(): String {
        return preferences.getString(KEY_USER_ADDRESS,"") ?: ""
    }

    fun setUserCategory(userCategory: Int) {
        preferences.edit().putInt(KEY_USER_CATEGORY,userCategory).apply()
    }

    fun getUserCategory(): Int {
        return preferences.getInt(KEY_USER_CATEGORY,2) //2 represents the spinner position, hence 3 categories to show
    }

    fun setUserProduct(userProduct: Int) {
        preferences.edit().putInt(KEY_USER_PRODUCT,userProduct).apply()
    }

    fun getUserProduct(): Int {
        return preferences.getInt(KEY_USER_PRODUCT,2)
    }
}