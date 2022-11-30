package com.example.onlinepurchase.activity.data

import java.util.*
import java.text.DecimalFormat
import com.example.onlinepurchase.activity.database.order.OrderEntity

data class Order(
    val products: List<Product>,
    val nbProduct: Int? = products.size,
    val price: Double? = computePrice(products),
    val date: Date = Date(),
    val address: String,
    val id: Int
) {
    companion object {
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

        private fun computePrice(data: List<Product>): Double {
            val df = DecimalFormat("#.##")
            var price = 0.0
            for (i in data) {
                price += i.price!!
            }
            return df.format(price).toDouble()
        }
    }
}
