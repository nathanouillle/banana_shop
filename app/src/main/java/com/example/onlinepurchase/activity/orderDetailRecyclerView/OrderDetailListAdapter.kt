package com.example.onlinepurchase.activity.orderDetailRecyclerView

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.databinding.ItemViewProductOrderBinding

class OrderDetailListAdapter(private val orderedProducts: List<Product>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemViewProductOrderBinding.inflate(from, parent, false)
        return OrderDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as OrderDetailViewHolder).bindOrderDetail(orderedProducts[position])
    }

    override fun getItemCount(): Int = orderedProducts.size

}