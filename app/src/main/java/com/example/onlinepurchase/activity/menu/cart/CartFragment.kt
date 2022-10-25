package com.example.onlinepurchase.activity.menu.cart

import android.net.Uri
import android.os.Bundle
import android.view.View
import java.lang.Exception
import android.widget.Toast
import android.content.Intent
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.data.Category
import com.example.onlinepurchase.databinding.FragmentCartBinding
import com.example.onlinepurchase.activity.orderDetailRecyclerView.OrderDetailListAdapter

class CartFragment : Fragment() {

    private var cart = mutableListOf<Product>()
    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            initCart()
        }catch(e: Exception){
            Toast.makeText(activity, "There has been a problem loading the cart, please try later", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)

        // Inflate the layout
        val cartPrice = computePrice(cart).toString()
        _binding!!.cartNbItems.text = cart.size.toString() + " items"
        _binding!!.cartPrice.text = cartPrice + "€"

        // Call the adapter to display the cart
        val productListView = _binding!!.cartProductList
        if (productListView is RecyclerView) {
            with(productListView) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = OrderDetailListAdapter(cart)
            }
        }

        // Action Pay
        _binding!!.actionPay.setOnClickListener {
            sendEmail(cartPrice)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        cart.clear()
    }

    private fun initCart(): List<Product>{
        cart.add(Product(name="Apple", description = "Green apple", price = 1.0,type=2, category = Category.Fruits, cover = R.drawable.green_apple))
        cart.add(Product(name="Banana", description = "Very good banana", price = 1.5,type=2, promoted = true, category = Category.Fruits, cover = R.drawable.banana))
        cart.add(Product(name="Beans", description = "Red beans", price = 1.0,type=2, category = Category.Vegetables, cover = R.drawable.beans))
        cart.add(Product(name="Aubergine", description = "Very good aubergine", price = 1.5,type=2, promoted = true, category = Category.Vegetables, cover = R.drawable.aubergine))
        return cart
    }

    private fun computePrice(data:List<Product>):Double {
        var price:Double = 0.0
        for(i in data) {
            price += i.price!!
        }
        return price
    }

    private fun sendEmail(cartPrice:String) {
        val uriText = "mailto:banana.shop@gmail.com" +
                "?subject=" + Uri.encode("Order") +
                "&body=" + Uri.encode("Send to 67 plaza España\nProducts:\n $cart \n Total: $cartPrice€")
        val email = Intent(Intent.ACTION_SENDTO)
        val uri = Uri.parse(uriText)
        email.data = uri
        try {
            startActivity(Intent.createChooser(email,"Pay with..."))
        }catch (e: Exception) {
            Toast.makeText(activity, "Error, try later", Toast.LENGTH_LONG).show()
        }
    }
}