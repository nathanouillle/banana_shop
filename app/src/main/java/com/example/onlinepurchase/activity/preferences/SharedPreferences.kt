package com.example.onlinepurchase.activity.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context) {

    private val PREFS_NAME = "com.example.onlinepurchase.sharedPreferences"

    private val preferences : SharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    private val KEY_USER_CATEGORY = "USER_CATEGORY"
    private val KEY_USER_PRODUCT = "USER_PRODUCT"

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