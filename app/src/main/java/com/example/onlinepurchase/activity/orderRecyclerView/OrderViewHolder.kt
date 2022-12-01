package com.example.onlinepurchase.activity.orderRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Order
import com.example.onlinepurchase.activity.utils.monthYearFormat
import com.example.onlinepurchase.databinding.ItemViewOrderBinding

class OrderViewHolder(
    private val itemViewOrderBinding: ItemViewOrderBinding,
    private val clickListener: OrderClickListener
) : RecyclerView.ViewHolder(itemViewOrderBinding.root) {

    fun bindOrder(order: Order) {
        itemViewOrderBinding.textViewOrderAddress.text = order.address
        itemViewOrderBinding.textViewOrderNumberItems.text = order.nbProduct.toString()
        itemViewOrderBinding.textViewOrderPrice.text = order.price.toString()
        itemViewOrderBinding.textViewOrderDate.text = monthYearFormat.format(order.date)

        itemViewOrderBinding.cardViewOrder.setOnClickListener {
            clickListener.onClick(order)
        }
    }
}