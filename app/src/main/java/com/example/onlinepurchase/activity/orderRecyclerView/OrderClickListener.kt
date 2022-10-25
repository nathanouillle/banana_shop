package com.example.onlinepurchase.activity.orderRecyclerView

import com.example.onlinepurchase.activity.data.Order

interface OrderClickListener {
    fun onClick(order: Order)
}