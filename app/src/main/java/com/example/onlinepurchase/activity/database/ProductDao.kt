package com.example.onlinepurchase.activity.database

import androidx.room.*
import com.example.onlinepurchase.activity.data.Category


@Dao
interface ProductDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(productEntity: ProductEntity)

   @Query("SELECT * from product_table WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity

   @Query("SELECT * FROM product_table WHERE type = 1")
    suspend fun getCategories(): List<ProductEntity>

   @Query("SELECT * FROM product_table WHERE type = 2 AND category = :category")
    suspend fun getProductsByCategory(category: Category): List<ProductEntity>

   @Query("SELECT * FROM product_table WHERE promoted = 1")
    suspend fun getPromotedProducts(): List<ProductEntity>

   @Query("DELETE FROM product_table")
    suspend fun deleteAllProducts()

   @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

   @Update
    suspend fun updateProduct(productEntity: ProductEntity)
}