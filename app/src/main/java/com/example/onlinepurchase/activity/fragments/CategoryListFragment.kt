package com.example.onlinepurchase.activity.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.map
import android.view.ViewGroup
import kotlinx.coroutines.launch
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import com.example.onlinepurchase.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.database.product.ProductEntity
import com.example.onlinepurchase.activity.menu.home.HomeFragmentDirections
import com.example.onlinepurchase.activity.categoryRecyclerView.CategoryListAdapter
import com.example.onlinepurchase.activity.categoryRecyclerView.CategoryClickListener

class CategoryListFragment : Fragment(), CategoryClickListener {

    private lateinit var clickListener: CategoryClickListener
    private val categoriesProductEntity = MutableLiveData<List<ProductEntity>>()
    private var categoriesProduct = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener = this

        // Get categories from database
        getCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for the fragment
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        // Convert ProductEntity to Product to show in the recycler view
        categoriesProductEntity.map {
            it.map { productEntity ->
                Product.fromProductEntity(productEntity)
            }
        }.observe(viewLifecycleOwner, Observer {
            categoriesProduct = it.toMutableList()

            // Now that we have the categories of products of type Product, we can set the recycler view
            if (view is RecyclerView) {
                with(view) {
                    layoutManager =
                        GridLayoutManager(context, OnlinePurchase.preferences.getUserCategory())
                    adapter = CategoryListAdapter(categoriesProduct, clickListener)
                }
            }
        })

        return view
    }

    override fun onClick(product: Product) {
        val direction =
            HomeFragmentDirections.actionNavigationHomeToProductListFragment(product.category)
        view?.findNavController()?.navigate(direction)
    }

    private fun getCategories() {
        runBlocking {
            launch(Dispatchers.IO) {
                val list = OnlinePurchase.onlinePurchaseDatabase.productDao().getCategories()
                categoriesProductEntity.postValue(list)
            }
        }
    }
}

