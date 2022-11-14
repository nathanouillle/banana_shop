package com.example.onlinepurchase.activity.database.user


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.onlinepurchase.activity.data.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    var password: String,
    val picture: ByteArray? = null
) {
    fun toUser(): User {
        return User(
            firstName = name.split(" ")[0],
            lastName = name.split(" ")[1],
            address = address,
            phone = phone,
            email = email,
            password = password,
            picture = picture
        )
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `user_table` (`id` INTEGER , `name` TEXT NOT NULL, `email` TEXT NOT NULL, `password` TEXT NOT NULL, `phone` TEXT NOT NULL, `address` TEXT NOT NULL, `picture` BLOB, " +
                    "PRIMARY KEY(`id`))"
        )
    }
}

// Room stopped working for an unknown reason, so I had to use this workaround
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `user_table` (`id` INTEGER , `name` TEXT NOT NULL, `email` TEXT NOT NULL, `password` TEXT NOT NULL, `phone` TEXT NOT NULL, `address` TEXT NOT NULL, `picture` BLOB, " +
                    "PRIMARY KEY(`id`))"
        )
    }
}