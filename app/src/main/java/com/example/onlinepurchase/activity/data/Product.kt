package com.example.onlinepurchase.activity.data

import com.example.onlinepurchase.activity.database.product.ProductEntity

data class Product(

    val name: String,
    val description: String? = null,
    val price: Double? = null,
    val cover: String,
    val promoted: Boolean = false,
    val type: Int, //If 1->Category Title, if 2->Product
    val category: Category,
    val id: Int? = null
) {
    override fun toString(): String = "$category: $name, $price\n"

    companion object {
        fun fromProductEntity(productEntity: ProductEntity): Product {
            return Product(
                name = productEntity.name,
                description = productEntity.description,
                price = productEntity.price,
                cover = productEntity.cover,
                promoted = productEntity.promoted,
                type = productEntity.type,
                category = productEntity.category,
                id = productEntity.id
            )
        }
    }
}