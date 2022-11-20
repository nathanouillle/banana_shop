package com.example.onlinepurchase.activity.cartRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.databinding.ItemViewProductCartBinding

class CartDetailViewHolder(
    private val itemViewProductOrderBinding: ItemViewProductCartBinding,
    private val clickListener: AddingRemovingClickListener
) :
    RecyclerView.ViewHolder(itemViewProductOrderBinding.root) {

    fun bindCartDetail(product: Product, quantity: Int) {
        itemViewProductOrderBinding.textViewProductName.text = product.name
        itemViewProductOrderBinding.textViewProductPrice.text = product.price.toString()
        itemViewProductOrderBinding.imageViewProduct.setImageResource(product.cover)
        itemViewProductOrderBinding.numberOfProduct.text = quantity.toString()

        itemViewProductOrderBinding.buttonRemoveProduct.setOnClickListener {
            clickListener.onClickRemove(product, quantity)
        }

        itemViewProductOrderBinding.buttonAddProduct.setOnClickListener {
            clickListener.onClickAdd(product, quantity)
        }
    }
}