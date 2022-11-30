package com.example.onlinepurchase.activity.networking

import com.google.gson.annotations.SerializedName
import com.example.onlinepurchase.activity.data.Category
import com.example.onlinepurchase.activity.database.product.ProductEntity

data class ProductListDTO(
    @SerializedName("productList") val productList: List<ProductDTO>
) {
    // convert list of ProductDTO to list of ProductEntity
    fun toProductEntityList(): List<ProductEntity> {
        val productEntityList = mutableListOf<ProductEntity>()
        productList.forEach { productDTO ->
            productEntityList.add(
                ProductEntity(
                    name = productDTO.name,
                    description = productDTO.description,
                    price = productDTO.price,
                    cover = productDTO.image,
                    promoted = productDTO.promoted,
                    type = productDTO.type,
                    category = Category.valueOf(productDTO.category)
                )
            )
        }
        return productEntityList
    }
}

data class ProductDTO(
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("cover") val image: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("type") val type: Int,
    @SerializedName("promoted") val promoted: Boolean = false,
)
