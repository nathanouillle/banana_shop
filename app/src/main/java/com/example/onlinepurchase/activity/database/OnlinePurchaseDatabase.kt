package com.example.onlinepurchase.activity.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.database.product.ProductDao
import com.example.onlinepurchase.activity.database.product.ProductEntity
import com.example.onlinepurchase.activity.database.user.MIGRATION_1_2
import com.example.onlinepurchase.activity.database.user.MIGRATION_2_3
import com.example.onlinepurchase.activity.database.user.UserDao
import com.example.onlinepurchase.activity.database.user.UserEntity



@Database(entities = [ProductEntity::class, UserEntity::class], version = 3, exportSchema = true)
abstract class OnlinePurchaseDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {
        val roomDatabase: OnlinePurchaseDatabase = Room.databaseBuilder(
            OnlinePurchase.onlinePurchaseContext,
            OnlinePurchaseDatabase::class.java,
            "OnlinePurchaseDatabase"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }
}