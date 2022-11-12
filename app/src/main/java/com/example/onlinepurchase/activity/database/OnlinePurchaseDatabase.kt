package com.example.onlinepurchase.activity.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlinepurchase.activity.OnlinePurchase


@Database(entities = [ProductEntity::class], version = 1)
abstract class OnlinePurchaseDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        val roomDatabase: OnlinePurchaseDatabase = Room.databaseBuilder(
            OnlinePurchase.onlinePurchaseContext,
            OnlinePurchaseDatabase::class.java,
            "OnlinePurchaseDatabase"
        ).build()
    }
}