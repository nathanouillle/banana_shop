package com.example.onlinepurchase.activity.orderDetailRecyclerView

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.data.Order
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.databinding.ItemViewProductOrderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class OrderDetailListAdapter(private val orderedProducts: Map<Int?, Int>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemViewProductOrderBinding.inflate(from, parent, false)
        return OrderDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // get productID quantity
        val productID = orderedProducts.keys.elementAt(position)
        val quantity = orderedProducts[productID]!!
        // get product from database
        val product = runBlocking(Dispatchers.IO) {
            productID?.let {
                OnlinePurchase.onlinePurchaseDatabase.productDao().getProductById(
                    it
                )
            }?.let { Product.fromProductEntity(it) }
        }
        if (product != null) {
            (holder as OrderDetailViewHolder).bindOrderDetail(product, quantity)
        }
    }

    override fun getItemCount(): Int = orderedProducts.size

}