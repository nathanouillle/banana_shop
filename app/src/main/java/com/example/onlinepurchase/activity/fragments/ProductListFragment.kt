package com.example.onlinepurchase.activity.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.navigation.fragment.navArgs
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.data.Category
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.database.product.ProductEntity
import com.example.onlinepurchase.activity.productRecyclerView.ProductListAdapter
import com.example.onlinepurchase.activity.productRecyclerView.ProductClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductListFragment : Fragment(), ProductClickListener {

    private lateinit var clickListener: ProductClickListener
    private val args: ProductListFragmentArgs by navArgs()
    private val productsEntity = MutableLiveData<List<ProductEntity>>()
    private var products = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        // Retrieve the category to show
        val categoryToShow = args.category
        getProductList(categoryToShow)

        // Convert ProductEntity to Product to show in the recycler view
        productsEntity.map {
            it.map { productEntity ->
                Product.fromProductEntity(productEntity)
            }
        }.observe(viewLifecycleOwner, Observer {
            products = it.toMutableList()

            // Now that we have the products of type Products, we can set the recycler view
            if (view is RecyclerView) {
                with(view) {
                    layoutManager =
                        GridLayoutManager(context, OnlinePurchase.preferences.getUserProduct())
                    adapter = ProductListAdapter(products, clickListener)
                }
            }
        })

        return view
    }

    override fun onClick(product: Product) {
        val direction = product.id?.let {
            ProductListFragmentDirections.actionProductListFragmentToProductDescriptionFragment(
                it
            )
        }
        if (direction != null) {
            view?.findNavController()?.navigate(direction)
        }
    }

    private fun getProductList(category: Category) {
        runBlocking {
            launch(Dispatchers.IO) {
                val list = OnlinePurchase.onlinePurchaseDatabase.productDao()
                    .getProductsByCategory(category)
                productsEntity.postValue(list)
            }
        }
    }
}