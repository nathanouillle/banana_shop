package com.example.onlinepurchase.activity.data

import com.example.onlinepurchase.activity.database.order.OrderEntity
import java.util.*

var ordersList = mutableListOf<Order>()
private var lastID = 0

data class Order(
    val products: List<Product>,
    val nbProduct: Int? = products.size,
    val price: Double? = computePrice(products),
    val date: Date = Date(),
    val address: String,
    val id: Int = lastID++
) {
    companion object{
        fun fromOrderEntity(orderEntity: OrderEntity): Order {
            return Order(
                products = orderEntity.products,
                nbProduct = orderEntity.nbProduct,
                price = orderEntity.price,
                date = orderEntity.date,
                address = orderEntity.address,
                id = orderEntity.id!!
            )
        }
    }
}

private fun computePrice(data:List<Product>):Double {
    var price:Double = 0.0
    for(i in data) {
        price += i.price!!
    }
    return price
}
