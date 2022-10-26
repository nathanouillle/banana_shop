package com.example.onlinepurchase.activity.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.data.productsList
import com.example.onlinepurchase.activity.menu.profil.ProfilFragment
import com.example.onlinepurchase.activity.productRecyclerView.ProductListAdapter
import com.example.onlinepurchase.activity.productRecyclerView.ProductClickListener
import kotlinx.android.synthetic.main.fragment_product_description.*

class ProductListFragment : Fragment(), ProductClickListener{

    private lateinit var clickListener: ProductClickListener
    private val args: ProductListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener=this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Retrieve the category to show
        val categoryToShow = args.category

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)

        //val profil = ProfilFragment()
        // Call the adapter
        if(view is RecyclerView) {
            with(view) {
                layoutManager = GridLayoutManager(context,3)
                adapter = ProductListAdapter(productsList.filter { it.type==2  && it.category==categoryToShow},clickListener)
            }
        }
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
}