package com.example.onlinepurchase.activity.menu.profil

import android.util.Log
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.onlinepurchase.activity.data.*
import com.example.onlinepurchase.databinding.FragmentProfilBinding

val options = listOf(1,2,3)
var numberOfCategories = 3
var numberOfProducts = 3

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private lateinit var categoryOption: Spinner
    private lateinit var productOption: Spinner

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Populate the orders
        try{
            initOrders()
        }catch (e:Exception){
            Toast.makeText(activity, "There has been a problem loading the orders, please try later", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profilViewModel =
            ViewModelProvider(this).get(ProfilViewModel::class.java)

        _binding = FragmentProfilBinding.inflate(inflater, container, false)

        val root: View = binding.root

        // Category Spinner
        categoryOption = _binding!!.categorySpinner
        categoryOption.adapter =
            this.activity?.let { ArrayAdapter<Int>(it,android.R.layout.simple_spinner_item, options)}
        categoryOption.setSelection(2)

        categoryOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                numberOfCategories = options[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // Product Spinner
        productOption = _binding!!.productSpinner
        productOption.adapter =
            this.activity?.let { ArrayAdapter<Int>(it,android.R.layout.simple_spinner_item, options)}
        productOption.setSelection(2)

        productOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                numberOfProducts = options[p2]
                Log.i("TEST", "onItemSelected: $numberOfProducts")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        ordersList.clear()
    }

    private fun initOrders() {

        val products1 = listOf<Product>(Product(name="Apple", description = "Green apple", price = 1.0,type=2, category = Category.Fruits, cover = R.drawable.green_apple),Product(name="Strawberry", description = "Yummy", price = 3.0,type=2, category = Category.Fruits, cover = R.drawable.strawberry))
        val order1 = Order(products1,address="67 Plaza España, Nueva Cordoba")

        val products2 = listOf<Product>(Product(name="Apple", description = "Green apple", price = 1.0,type=2, category = Category.Fruits, cover = R.drawable.green_apple),Product(name="Strawberry", description = "Yummy", price = 3.0,type=2, category = Category.Fruits, cover = R.drawable.strawberry),Product(name="Beans", description = "Red beans", price = 1.0,type=2, category = Category.Vegetables, cover = R.drawable.beans))
        val order2 = Order(products2,address="67 Plaza España, Nueva Cordoba")

        val products3 = listOf<Product>(Product(name="Aubergine", description = "Very good aubergine", price = 1.5,type=2, promoted = true, category = Category.Vegetables, cover = R.drawable.aubergine),Product(name="Strawberry", description = "Yummy", price = 3.0,type=2, category = Category.Fruits, cover = R.drawable.strawberry),Product(name="Beans", description = "Red beans", price = 1.0,type=2, category = Category.Vegetables, cover = R.drawable.beans))
        val order3 = Order(products3,address="567 Bulevar Chacabuco, Nueva Cordoba")

        val products4 = listOf<Product>(Product(name="Aubergine", description = "Very good aubergine", price = 1.5,type=2, promoted = true, category = Category.Vegetables, cover = R.drawable.aubergine),Product(name="Strawberry", description = "Yummy", price = 3.0,type=2, category = Category.Fruits, cover = R.drawable.strawberry),Product(name="Beans", description = "Red beans", price = 1.0,type=2, category = Category.Vegetables, cover = R.drawable.beans),Product(name="Banana", description = "Very good banana", price = 1.5,type=2, promoted = true, category = Category.Fruits, cover = R.drawable.banana))
        val order4 = Order(products4,address="67 Plaza España, Nueva Cordoba")

        ordersList.add(order1)
        ordersList.add(order2)
        ordersList.add(order3)
        ordersList.add(order4)
    }
}

