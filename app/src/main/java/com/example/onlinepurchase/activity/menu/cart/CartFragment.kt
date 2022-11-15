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
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.data.Category
import com.example.onlinepurchase.activity.data.User
import com.example.onlinepurchase.activity.database.order.OrderEntity
import com.example.onlinepurchase.databinding.FragmentCartBinding
import com.example.onlinepurchase.activity.orderDetailRecyclerView.OrderDetailListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private var userID by Delegates.notNull<Int>()
    private lateinit var user: User
    private lateinit var cart: MutableList<Product>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get userID from shared preferences
        userID = OnlinePurchase.preferences.getUserID()

        // Get user from database
        runBlocking(Dispatchers.IO) {
            user = OnlinePurchase.onlinePurchaseDatabase.userDao().getUserById(userID).toUser()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        cart = OnlinePurchase.cart

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
            if(cart.size > 0) {
                //sendEmail(cartPrice)
                runBlocking(Dispatchers.IO) {
                    val orderEntity = OrderEntity(
                        userId = userID,
                        products = cart,
                        price = cartPrice.toDouble(),
                        address = user.address
                    )
                    OnlinePurchase.onlinePurchaseDatabase.orderDao().addOrder(orderEntity)
                }
                Toast.makeText(context, "Order sent!", Toast.LENGTH_SHORT).show()
                cart.clear()

            }
            else {
                Toast.makeText(context, "Your cart is empty", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun computePrice(data: List<Product>): Double {
        var price = 0.0
        for (i in data) {
            price += i.price!!
        }
        return price
    }


    private fun sendEmail(cartPrice: String) {
        val uriText = "mailto:banana.shop@gmail.com" +
                "?subject=" + Uri.encode("Order from ${user.firstName}") +
                "&body=" + Uri.encode("${user.address}\nProducts:\n $cart \n Total: $cartPrice€")
        val email = Intent(Intent.ACTION_SENDTO)
        val uri = Uri.parse(uriText)
        email.data = uri
        try {
            startActivity(Intent.createChooser(email, "Pay with..."))
            // Create an order in the database
            /*runBlocking(Dispatchers.IO) {
                val orderEntity = OrderEntity(
                    userId = userID,
                    products = cart,
                    price = cartPrice.toDouble(),
                    address = user.address
                )
                OnlinePurchase.onlinePurchaseDatabase.orderDao().addOrder(orderEntity)
            }
            cart.clear()*/
        } catch (e: Exception) {
            Toast.makeText(activity, "Error, try later", Toast.LENGTH_LONG).show()
        }
    }
}