package com.example.onlinepurchase.activity.orderRecyclerView

import java.text.SimpleDateFormat
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Order
import com.example.onlinepurchase.databinding.ItemViewOrderBinding


const val datePattern = "MM-yyyy"
val simpleDateFormat = SimpleDateFormat(datePattern)

class OrderViewHolder(private val itemViewOrderBinding: ItemViewOrderBinding, private val clickListener: OrderClickListener)
    : RecyclerView.ViewHolder(itemViewOrderBinding.root) {

    fun bindOrder(order: Order) {
        itemViewOrderBinding.textViewOrderAddress.text = order.address
        itemViewOrderBinding.textViewOrderNumberItems.text = order.nbProduct.toString()
        itemViewOrderBinding.textViewOrderPrice.text = order.price.toString()
        itemViewOrderBinding.textViewOrderDate.text = simpleDateFormat.format(order.date)

        itemViewOrderBinding.cardViewOrder.setOnClickListener {
            clickListener.onClick(order)
        }
    }
}