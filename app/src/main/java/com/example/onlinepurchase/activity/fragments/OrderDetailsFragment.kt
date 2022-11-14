package com.example.onlinepurchase.activity.fragments

import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Order
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.data.ordersList
import com.example.onlinepurchase.databinding.FragmentOrderDetailsBinding
import com.example.onlinepurchase.activity.orderDetailRecyclerView.OrderDetailListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


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


        //val orderedProducts = order?.products
        binding.titleAddress.text = order.address
        binding.titleDate.text = order.date.let { simpleDateFormat.format(it) }
        binding.orderPrice.text = order.price.toString()

        // Call the adapter for the order content
        if(binding.orderProductListDetails is RecyclerView) {
            with(binding.orderProductListDetails) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                adapter = OrderDetailListAdapter(order.products)
            }
        }

        return binding.root
    }

    private fun orderFromID(orderID: Int): Order? {
        for(order in ordersList)
        {
            if(order.id == orderID)
                return order
        }
        return null
    }
}