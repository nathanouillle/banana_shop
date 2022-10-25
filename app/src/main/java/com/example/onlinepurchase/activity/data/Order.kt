package com.example.onlinepurchase.activity.data

import java.util.*

var ordersList = mutableListOf<Order>()
var lastID = 0

data class Order(
    val products: List<Product>,
    val nbProduct: Int? = products.size,
    val price: Double? = computePrice(products),
    val date: Date = Date(),
    val address: String,
    val id: Int = lastID++
) {
    override fun toString(): String {
        var display = "Order date: $date\n" +
                "Send to $address\n" +
                "Product list:\n"
        for(product in products) {
            display += "$product\n"
        }
       display += "Total: $price€"
        return display
    }
}

private fun computePrice(data:List<Product>):Double {
    var price:Double = 0.0
    for(i in data) {
        price += i.price!!
    }
    return price
}
