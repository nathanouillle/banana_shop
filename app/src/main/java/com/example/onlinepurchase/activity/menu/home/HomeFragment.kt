package com.example.onlinepurchase.activity.menu.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.onlinepurchase.activity.data.*
import com.example.onlinepurchase.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Populate the products
        try{
            initProducts()
        } catch (e:Exception){
            Toast.makeText(activity, "There has been a problem loading the products, please try later", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        productsList.clear()
    }

    private fun initProducts() {
        productsList.add(Product(name="Fruits", type=1, category = Category.Fruits, cover = R.drawable.fruits))
        productsList.add(Product(name="Apple", description = "Green apple", price = 1.0,type=2, category = Category.Fruits, cover = R.drawable.green_apple))
        productsList.add(Product(name="Blueberry", description = "Small berry of blue-purple color", price = 3.99,type=2, category = Category.Fruits,cover = R.drawable.blueberry))
        productsList.add(Product(name="Kiwi", description = "This fruit is from China and grows up only in the beginning of november", price = 0.69,type=2, category = Category.Fruits,cover = R.drawable.kiwi))
        productsList.add(Product(name="Banana", description = "Very good banana", price = 1.5,type=2, promoted = true, category = Category.Fruits, cover = R.drawable.banana))
        productsList.add(Product(name="Strawberry", description = "Yummy", price = 3.0,type=2, category = Category.Fruits, cover = R.drawable.strawberry))
        productsList.add(Product(name="Vegetables", type=1, category = Category.Vegetables, cover = R.drawable.vegetables))
        productsList.add(Product(name="Bean", description = "Red beans", price = 1.0,type=2, category = Category.Vegetables, cover = R.drawable.beans))
        productsList.add(Product(name="Tomato", description = "These tomatoes are a must have. They can be used for salads and more!", price = 2.39,type=2, category = Category.Vegetables, cover = R.drawable.tomato))
        productsList.add(Product(name="Pepper", description = "Yellow good looking pepper", price = 1.49,type=2, category = Category.Vegetables, cover = R.drawable.pepper))
        productsList.add(Product(name="Carrot", description = "From France", price = 0.25,type=2, category = Category.Vegetables, cover = R.drawable.carrot))
        productsList.add(Product(name="Potato", description = "Quickly cooked, these potatoes are delicious", price = 2.39,type=2, category = Category.Vegetables, cover = R.drawable.potato))
        productsList.add(Product(name="Salad", description = "Big crunchy leaves, yum yum", price = 1.89,type=2, category = Category.Vegetables, cover = R.drawable.salad))
        productsList.add(Product(name="Onion", description = "Yellow onion", price = 0.39,type=2, category = Category.Vegetables, cover = R.drawable.onion))
        productsList.add(Product(name="Aubergine", description = "Very good aubergine", price = 1.5,type=2, promoted = true, category = Category.Vegetables, cover = R.drawable.aubergine))
        productsList.add(Product(name="Meats", type=1, category = Category.Meats, cover = R.drawable.meats))
        productsList.add(Product(name="Chicken", description = "Happy chicken ready to be eaten", price = 10.0,type=2, promoted = true, category = Category.Meats, cover = R.drawable.chicken))
        productsList.add(Product(name="Beef", description = "Ground beef, 12% of fat", price = 5.0,type=2, category = Category.Meats,cover = R.drawable.ground_beef))
        productsList.add(Product(name="Milanesa", description = "Argentinian speciality", price = 5.0,type=2, promoted = true, category = Category.Meats, cover = R.drawable.milanesa))
        productsList.add(Product(name="Morning", type=1, category = Category.Morning, cover = R.drawable.morning))
        productsList.add(Product(name="Bread", description = "Fresh baked bread of the day", price = 1.0,type=2, category = Category.Morning, cover = R.drawable.bread))
        productsList.add(Product(name="Jam", description = "Made from delicious fruits", price = 4.0,type=2, category = Category.Morning, cover = R.drawable.jam))
        productsList.add(Product(name="Cereals", description = "Black chocolate and roasted almonds", price = 5.7,type=2, category = Category.Morning, cover = R.drawable.cereals))
    }
}