package com.example.onlinepurchase.activity.database.user


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Entity(tableName = "user_table")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val password: String,
    val picture: ByteArray? = null
)

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `user_table` (`id` INTEGER , `name` TEXT NOT NULL, `email` TEXT NOT NULL, `password` TEXT NOT NULL, `phone` TEXT NOT NULL, `address` TEXT NOT NULL, `picture` BLOB, " +
                "PRIMARY KEY(`id`))")
    }
}