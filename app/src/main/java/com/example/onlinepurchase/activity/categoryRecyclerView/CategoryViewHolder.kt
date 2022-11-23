package com.example.onlinepurchase.activity.categoryRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.utils.imageOptions
import com.example.onlinepurchase.databinding.ItemViewProductTitleBinding

class CategoryViewHolder(
    private val itemViewProductTitleBinding: ItemViewProductTitleBinding,
    private val clickListener: CategoryClickListener
) : RecyclerView.ViewHolder(itemViewProductTitleBinding.root) {

    fun bindCategory(product: Product) {
        itemViewProductTitleBinding.textViewCategoryName.text = product.category.toString()
        //itemViewProductTitleBinding.imageViewCategory.setImageResource(product.cover)
        // Fill image with Glide
        Glide.with(itemViewProductTitleBinding.root.context)
            .load(product.cover)
            .apply(imageOptions)
            .into(itemViewProductTitleBinding.imageViewCategory)

        itemViewProductTitleBinding.cardViewCategory.setOnClickListener {
            clickListener.onClick(product)
        }
    }
}