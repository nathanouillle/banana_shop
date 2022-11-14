package com.example.onlinepurchase.activity.productRecyclerView

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.databinding.ItemViewProductBinding

class ProductListAdapter(
    private val products: List<Product>,
    private val clickListener: ProductClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemViewProductBinding.inflate(from, parent, false)
        return ProductViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size

}