package com.example.onlinepurchase.activity.cartRecyclerView

import com.example.onlinepurchase.activity.data.Product

interface AddingRemovingClickListener {
    fun onClickAdd(product: Product, quantity: Int)
    fun onClickRemove(product: Product, quantity: Int)
}