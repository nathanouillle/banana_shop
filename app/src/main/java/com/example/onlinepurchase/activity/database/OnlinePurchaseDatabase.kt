package com.example.onlinepurchase.activity.database

import androidx.room.*
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.database.order.MIGRATION_3_4
import com.example.onlinepurchase.activity.database.order.OrderDao
import com.example.onlinepurchase.activity.database.order.OrderEntity
import com.example.onlinepurchase.activity.database.product.ProductDao
import com.example.onlinepurchase.activity.database.product.ProductEntity
import com.example.onlinepurchase.activity.database.user.MIGRATION_1_2
import com.example.onlinepurchase.activity.database.user.MIGRATION_2_3
import com.example.onlinepurchase.activity.database.user.UserDao
import com.example.onlinepurchase.activity.database.user.UserEntity


@Database(
    entities = [ProductEntity::class, UserEntity::class, OrderEntity::class],
    version = 4,
    exportSchema = true
)
@TypeConverters(DataConverter::class)
abstract class OnlinePurchaseDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao

    companion object {
        val roomDatabase: OnlinePurchaseDatabase = Room.databaseBuilder(
            OnlinePurchase.onlinePurchaseContext,
            OnlinePurchaseDatabase::class.java,
            "OnlinePurchaseDatabase"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
            .build()
    }
}