package com.example.onlinepurchase.activity.database

import androidx.room.TypeConverter
import com.example.onlinepurchase.activity.data.Category
import com.example.onlinepurchase.activity.data.Product
import java.util.*

class DataConverter {
    @TypeConverter
    fun fromProductList(products: List<Product>): String {
        var string = ""
        for (product in products) {
            string += product.name + "," + product.price + "," + product.description + "," + product.cover + "," + product.promoted + "," + product.type + "," + product.category + ";"
        }
        return string
    }

    @TypeConverter
    fun toProductList(string: String): List<Product> {
        val products = mutableListOf<Product>()
        val productString = string.split(";")
        for (product in productString) {
            val productData = product.split(",")
            products.add(Product(productData[0], productData[2], productData[1].toDouble(), productData[3].toInt(), productData[4].toBoolean(), productData[5].toInt(), Category.valueOf(productData[6])))
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