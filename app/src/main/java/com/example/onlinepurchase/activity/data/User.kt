package com.example.onlinepurchase.activity.data

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.onlinepurchase.activity.database.user.UserEntity

data class User(
    val firstName: String,
    val lastName: String,
    val address: String,
    val phone: String,
    val email: String,
    val password: String,
    val picture: ByteArray? = null
) {
    // convert user to user entity
    fun toUserEntity(): UserEntity {
        return UserEntity(
            name = "$firstName $lastName",
            address = address,
            phone = phone,
            email = email,
            password = password,
            picture = picture
        )
    }
}
