package com.example.onlinepurchase.activity.orderRecyclerView

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Order
import com.example.onlinepurchase.databinding.ItemViewOrderBinding

class OrderListAdapter(
    private val orders: List<Order>,
    private val clickListener: OrderClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemViewOrderBinding.inflate(from, parent, false)
        return OrderViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as OrderViewHolder).bindOrder(orders[position])
    }

    override fun getItemCount(): Int = orders.size
}