package com.example.onlinepurchase.activity.database

import java.util.*
import androidx.room.TypeConverter
import com.example.onlinepurchase.activity.data.Category
import com.example.onlinepurchase.activity.data.Product

class DataConverter {
    @TypeConverter
    fun fromProductList(products: List<Product>): String {
        var string = ""
        for ((ind, product) in products.withIndex()) {
            string += product.name + ";" + product.price + ";" + product.description + ";" + product.cover + ";" + product.promoted + ";" + product.type + ";" + product.category + ";" + product.id
            if (ind != products.size - 1) {
                string += "*"
            }
        }
        return string
    }

    @TypeConverter
    fun toProductList(string: String): List<Product> {
        val products = mutableListOf<Product>()
        val productString = string.split("*")
        for (product in productString) {
            val productInfo = product.split(";")
            products.add(
                Product(
                    name = productInfo[0],
                    price = productInfo[1].toDouble(),
                    description = productInfo[2],
                    cover = productInfo[3],
                    promoted = productInfo[4].toBoolean(),
                    type = productInfo[5].toInt(),
                    category = Category.valueOf(productInfo[6]),
                    id = productInfo[7].toInt()
                )
            )

        }
        return products
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}