package com.example.onlinepurchase.activity.categoryRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.databinding.ItemViewProductTitleBinding

class CategoryViewHolder(private val itemViewProductTitleBinding: ItemViewProductTitleBinding, private val clickListener: CategoryClickListener)
    : RecyclerView.ViewHolder(itemViewProductTitleBinding.root) {

    fun bindCategory(product: Product) {
        itemViewProductTitleBinding.textViewCategoryName.text = product.category.toString()
        itemViewProductTitleBinding.imageViewCategory.setImageResource(product.cover)

        itemViewProductTitleBinding.cardViewCategory.setOnClickListener {
            clickListener.onClick(product)
        }
    }
}