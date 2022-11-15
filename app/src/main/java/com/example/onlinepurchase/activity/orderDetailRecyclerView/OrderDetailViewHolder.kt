package com.example.onlinepurchase.activity.orderDetailRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.databinding.ItemViewProductOrderBinding

class OrderDetailViewHolder(private val itemViewProductOrderBinding: ItemViewProductOrderBinding) :
    RecyclerView.ViewHolder(itemViewProductOrderBinding.root) {

    fun bindOrderDetail(product: Product, quantity: Int) {
        itemViewProductOrderBinding.textViewProductName.text = product.name
        itemViewProductOrderBinding.textViewProductPrice.text = product.price.toString()
        itemViewProductOrderBinding.imageViewProduct.setImageResource(product.cover)
        itemViewProductOrderBinding.numberOfProduct.text = "x"+quantity.toString()
    }
}