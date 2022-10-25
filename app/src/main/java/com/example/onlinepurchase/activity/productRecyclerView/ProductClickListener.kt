package com.example.onlinepurchase.activity.productRecyclerView

import com.example.onlinepurchase.activity.data.Product

interface ProductClickListener {
    fun onClick(product: Product)
}