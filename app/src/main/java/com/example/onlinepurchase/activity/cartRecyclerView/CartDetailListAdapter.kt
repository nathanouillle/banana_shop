package com.example.onlinepurchase.activity.cartRecyclerView

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.orderDetailRecyclerView.OrderDetailViewHolder
import com.example.onlinepurchase.databinding.ItemViewProductCartBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class CartDetailListAdapter(
    private val cartProducts: Map<Int?, Int>, // Id of the product and the quantity
    private val clickListener: AddingRemovingClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemViewProductCartBinding.inflate(from, parent, false)
        return CartDetailViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // get productID quantity
        val productID = cartProducts.keys.elementAt(position)
        val quantity = cartProducts[productID]!!
        // get product from database
        runBlocking(Dispatchers.IO) {
            val product = productID?.let {
                OnlinePurchase.onlinePurchaseDatabase.productDao().getProductById(
                    it
                )
            }?.let { Product.fromProductEntity(it) }
            if (product != null) {
                (holder as CartDetailViewHolder).bindCartDetail(product, quantity)
            }
        }
    }

    override fun getItemCount(): Int = cartProducts.size

}