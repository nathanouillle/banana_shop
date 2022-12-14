package com.example.onlinepurchase.activity.database.order

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrder(orderEntity: OrderEntity)

    @Query("SELECT * from order_table WHERE id = :id")
    suspend fun getOrderById(id: Int): OrderEntity

    @Query("SELECT * from order_table WHERE userId = :userId ORDER BY date DESC")
    suspend fun getOrderByUserId(userId: Int): List<OrderEntity>

    @Query("DELETE FROM order_table")
    suspend fun deleteAllOrders()

}