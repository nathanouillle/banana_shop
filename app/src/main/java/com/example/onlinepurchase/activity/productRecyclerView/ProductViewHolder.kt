package com.example.onlinepurchase.activity.productRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.utils.imageOptions
import com.example.onlinepurchase.databinding.ItemViewProductBinding

class ProductViewHolder(
    private val itemViewProductBinding: ItemViewProductBinding,
    private val clickListener: ProductClickListener
) : RecyclerView.ViewHolder(itemViewProductBinding.root) {
    fun bindProduct(product: Product) {
        itemViewProductBinding.textViewProductName.text = product.name
        itemViewProductBinding.textViewProductPrice.text = product.price.toString() + "€"
        //itemViewProductBinding.imageViewProduct.setImageResource(product.cover)
        // Fill image with Glide
        Glide.with(itemViewProductBinding.root.context)
            .load(product.cover)
            .apply(imageOptions)
            .into(itemViewProductBinding.imageViewProduct)

        itemViewProductBinding.cardViewProduct.setOnClickListener {
            clickListener.onClick(product)
        }
    }
}