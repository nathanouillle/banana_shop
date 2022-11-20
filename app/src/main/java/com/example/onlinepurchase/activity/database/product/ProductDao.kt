package com.example.onlinepurchase.activity.database.product

import androidx.room.*
import com.example.onlinepurchase.activity.data.Category

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(productEntity: ProductEntity)

    // insert product list to database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductList(productList: List<ProductEntity>)

    // update product list to database
    @Update
    suspend fun updateProductList(productList: List<ProductEntity>)

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
}