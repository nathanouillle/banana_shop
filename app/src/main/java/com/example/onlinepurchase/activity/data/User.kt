package com.example.onlinepurchase.activity.data

import android.widget.ImageView

data class User(
    val firstName: String,
    val lastName: String,
    val address: String,
    val phone: String,
    val email: String,
    val password: String,
    val picture: ImageView
)
