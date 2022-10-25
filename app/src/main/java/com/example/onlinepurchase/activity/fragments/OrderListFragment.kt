package com.example.onlinepurchase.activity.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.onlinepurchase.activity.data.Order
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinepurchase.activity.data.ordersList
import com.example.onlinepurchase.activity.orderRecyclerView.OrderListAdapter
import com.example.onlinepurchase.activity.menu.profil.ProfilFragmentDirections
import com.example.onlinepurchase.activity.orderRecyclerView.OrderClickListener


class OrderListFragment: Fragment(),OrderClickListener {
    private lateinit var clickListener: OrderClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener=this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for the fragment
        val view = inflater.inflate(R.layout.fragment_order_list, container, false)

        // Call the adapter
        if(view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                adapter = OrderListAdapter(ordersList.toList(),clickListener)
            }
        }

        return view
    }

    override fun onClick(order: Order) {
        val direction = order.id.let {
            ProfilFragmentDirections.actionNavigationProfilToOrderDetailsFragment(
                it
            )
        }
        view?.findNavController()?.navigate(direction)
    }
}