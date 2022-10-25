package com.example.onlinepurchase.activity.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.data.productsList
import com.example.onlinepurchase.activity.menu.home.HomeFragmentDirections
import com.example.onlinepurchase.activity.productRecyclerView.ProductListAdapter
import com.example.onlinepurchase.activity.productRecyclerView.ProductClickListener


class PromotedProductListFragment: Fragment(), ProductClickListener{

    private lateinit var clickListener : ProductClickListener
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener=this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_promoted_product_list, container, false)

        // Call the adapter
        if(view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                adapter = ProductListAdapter(productsList.filter{ it.promoted },clickListener)
            }
        }
        return view
    }

    override fun onClick(product: Product) {
        val direction = product.id?.let {
            HomeFragmentDirections.actionNavigationHomeToProductDescriptionFragment(
                it
            )
        }
        if (direction != null) {
            view?.findNavController()?.navigate(direction)
        }
    }
}