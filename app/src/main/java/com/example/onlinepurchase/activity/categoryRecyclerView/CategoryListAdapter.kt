package com.example.onlinepurchase.activity.categoryRecyclerView

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.databinding.ItemViewProductTitleBinding

class CategoryListAdapter(
    private val products: List<Product>,
    private val clickListener: CategoryClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemViewProductTitleBinding.inflate(from, parent, false)
        return CategoryViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bindCategory(products[position])
    }

    override fun getItemCount(): Int = products.size
}
