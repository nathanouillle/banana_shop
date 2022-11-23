package com.example.onlinepurchase.activity.orderDetailRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.utils.imageOptions
import com.example.onlinepurchase.databinding.ItemViewProductOrderBinding

class OrderDetailViewHolder(
    private val itemViewProductOrderBinding: ItemViewProductOrderBinding,
) :
    RecyclerView.ViewHolder(itemViewProductOrderBinding.root) {

    fun bindOrderDetail(product: Product, quantity: Int) {
        itemViewProductOrderBinding.textViewProductName.text = product.name
        itemViewProductOrderBinding.textViewProductPrice.text = product.price.toString()
        //itemViewProductOrderBinding.imageViewProduct.setImageResource(product.cover)
        // Fill image with Glide
        Glide.with(itemViewProductOrderBinding.root.context)
            .load(product.cover)
            .apply(imageOptions)
            .into(itemViewProductOrderBinding.imageViewProduct)
        itemViewProductOrderBinding.numberOfProduct.text = "x" + quantity.toString()

    }
}