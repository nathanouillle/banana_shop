package com.example.onlinepurchase.activity.categoryRecyclerView

import com.example.onlinepurchase.activity.data.Product

interface CategoryClickListener {
    fun onClick(product: Product)
}