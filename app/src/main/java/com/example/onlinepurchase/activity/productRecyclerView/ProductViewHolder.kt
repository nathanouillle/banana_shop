package com.example.onlinepurchase.activity.productRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.databinding.ItemViewProductBinding

class ProductViewHolder(
    private val itemViewProductBinding: ItemViewProductBinding,
    private val clickListener: ProductClickListener
) : RecyclerView.ViewHolder(itemViewProductBinding.root) {
    fun bindProduct(product: Product) {
        itemViewProductBinding.textViewProductName.text = product.name
        itemViewProductBinding.textViewProductPrice.text = product.price.toString() + "€"
        itemViewProductBinding.imageViewProduct.setImageResource(product.cover)

        itemViewProductBinding.cardViewProduct.setOnClickListener {
            clickListener.onClick(product)
        }
    }
}