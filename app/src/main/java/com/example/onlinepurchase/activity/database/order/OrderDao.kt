package com.example.onlinepurchase.activity.database.order

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     suspend fun addOrder(orderEntity: OrderEntity)

    @Query("SELECT * from order_table WHERE id = :id")
     suspend fun getOrderById(id: Int): OrderEntity

    @Query("SELECT * from order_table WHERE userId = :userId")
     suspend fun getOrderByUserId(userId: Int): List<OrderEntity>

     // delete all orders
    @Query("DELETE FROM order_table")
     suspend fun deleteAllOrders()

}