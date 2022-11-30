package com.example.onlinepurchase.activity.fragments

import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Order
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.databinding.FragmentOrderDetailsBinding
import com.example.onlinepurchase.activity.orderDetailRecyclerView.OrderDetailListAdapter

class OrderDetailsFragment : Fragment() {

    private val datePattern = "EEEE dd MMMM, yyyy"
    private val simpleDateFormat = SimpleDateFormat(datePattern)
    private lateinit var binding: FragmentOrderDetailsBinding
    private val args: OrderDetailsFragmentArgs by navArgs()
    private lateinit var order: Order


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Retrieve the order to show
        val orderID = args.orderID
        runBlocking(Dispatchers.IO) {
            val orderEntity = OnlinePurchase.onlinePurchaseDatabase.orderDao().getOrderById(orderID)
            order = Order.fromOrderEntity(orderEntity)
        }

        // Inflate the layout for this fragment
        binding = FragmentOrderDetailsBinding.inflate(layoutInflater, container, false)
        binding.titleAddress.text = order.address
        binding.titleDate.text = order.date.let { simpleDateFormat.format(it) }
        binding.orderPrice.text = order.price.toString()

        val productsList = order.products
        val map = productsList.groupingBy { it.id }.eachCount()

        // Call the adapter for the order content
        if (binding.orderProductListDetails is RecyclerView) {
            with(binding.orderProductListDetails) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = OrderDetailListAdapter(map)
            }
        }

        return binding.root
    }
}