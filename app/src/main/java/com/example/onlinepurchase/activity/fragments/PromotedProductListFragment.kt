package com.example.onlinepurchase.activity.fragments


import android.os.Bundle
import android.view.View
import androidx.lifecycle.map
import android.view.ViewGroup
import kotlinx.coroutines.launch
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.database.product.ProductEntity
import com.example.onlinepurchase.activity.menu.home.HomeFragmentDirections
import com.example.onlinepurchase.activity.productRecyclerView.ProductListAdapter
import com.example.onlinepurchase.activity.productRecyclerView.ProductClickListener
import com.example.onlinepurchase.activity.data.Product.Companion.fromProductEntity

class PromotedProductListFragment : Fragment(), ProductClickListener {

    private lateinit var clickListener: ProductClickListener
    private val promotedProductsEntity = MutableLiveData<List<ProductEntity>>()
    private var promotedProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener = this

        // Get promoted products from database
        getPromotedProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_promoted_product_list, container, false)

        // Convert ProductEntity to Product to show in the recycler view
        promotedProductsEntity.map {
            it.map { productEntity ->
                fromProductEntity(productEntity)
            }
        }.observe(viewLifecycleOwner, Observer {
            promotedProducts = it.toMutableList()

            // Now that we have the promoted products of type Products, we can set the recycler view
            if (view is RecyclerView) {
                with(view) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = ProductListAdapter(promotedProducts, clickListener)
                }
            }
        })

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

    private fun getPromotedProducts() {
        runBlocking {
            launch(Dispatchers.IO) {
                val list = OnlinePurchase.onlinePurchaseDatabase.productDao().getPromotedProducts()
                promotedProductsEntity.postValue(list)
            }
        }
    }
}