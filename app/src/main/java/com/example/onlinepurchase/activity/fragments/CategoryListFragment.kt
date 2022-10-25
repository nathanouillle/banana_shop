package com.example.onlinepurchase.activity.fragments

import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.data.productsList
import com.example.onlinepurchase.activity.menu.profil.numberOfCategories
import com.example.onlinepurchase.activity.menu.home.HomeFragmentDirections
import com.example.onlinepurchase.activity.categoryRecyclerView.CategoryListAdapter
import com.example.onlinepurchase.activity.categoryRecyclerView.CategoryClickListener

class CategoryListFragment: Fragment(),CategoryClickListener {

    private lateinit var clickListener : CategoryClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener=this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for the fragment
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        // Call the adapter
        if(view is RecyclerView) {
            with(view) {
                layoutManager = GridLayoutManager(context, numberOfCategories)
                adapter = CategoryListAdapter(productsList.filter { it.type==1 },clickListener)
            }
        }
        return view
    }

    override fun onClick(product: Product) {
        val direction = HomeFragmentDirections.actionNavigationHomeToProductListFragment(product.category)
        view?.findNavController()?.navigate(direction)
    }
}

