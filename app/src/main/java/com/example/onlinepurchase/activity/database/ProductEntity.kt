package com.example.onlinepurchase.activity.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.onlinepurchase.activity.data.Category
import com.example.onlinepurchase.activity.data.Product

@Entity(tableName = "product_table")
data class ProductEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    val description: String? = null,
    val price: Double? = null,
    val cover: Int,
    val promoted: Boolean = false,
    val type: Int, //If 1->Category Title, if 2->Product
    val category: Category
)
