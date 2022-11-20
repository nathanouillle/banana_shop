package com.example.onlinepurchase.activity.menu.cart

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import java.lang.Exception
import android.widget.Toast
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.onlinepurchase.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.cartRecyclerView.CartDetailListAdapter
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.data.User
import com.example.onlinepurchase.activity.database.order.OrderEntity
import com.example.onlinepurchase.activity.cartRecyclerView.AddingRemovingClickListener
import com.example.onlinepurchase.databinding.FragmentCartBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.text.DecimalFormat
import kotlin.properties.Delegates

private const val CHANNEL_ID = "channel_id"
private const val notificationId = 1

class CartFragment : Fragment(), AddingRemovingClickListener {

    private var _binding: FragmentCartBinding? = null
    private var userID by Delegates.notNull<Int>()
    private lateinit var user: User
    private lateinit var cart: MutableList<Product>
    private lateinit var clickListener: AddingRemovingClickListener

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener = this
        // get userID from shared preferences
        userID = OnlinePurchase.preferences.getUserID()

        // Get user from database
        runBlocking(Dispatchers.IO) {
            user = OnlinePurchase.onlinePurchaseDatabase.userDao().getUserById(userID).toUser()
        }

        createNotificationChannel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        cart = OnlinePurchase.cart
        val cartPrice = computePrice(cart).toString()


        // Action Pay
        _binding!!.actionPay.setOnClickListener {
            if (cart.size > 0) {
                runBlocking(Dispatchers.IO) {
                    val orderEntity = OrderEntity(
                        userId = userID,
                        products = cart,
                        price = cartPrice.toDouble(),
                        address = user.address
                    )
                    OnlinePurchase.onlinePurchaseDatabase.orderDao().addOrder(orderEntity)
                }
                // send notification
                sendNotification()
                // send email
                sendEmail(cartPrice)
                // clear cart
                cart.clear()
            } else {
                Toast.makeText(context, "Your cart is empty", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val cartPrice = computePrice(cart).toString()
        _binding!!.cartNbItems.text = cart.size.toString() + " items"
        _binding!!.cartPrice.text = cartPrice + "€"

        // Call the adapter to display the cart
        displayCart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun computePrice(data: List<Product>): Double {
        val df = DecimalFormat("#.##")
        var price = 0.0
        for (i in data) {
            price += i.price!!
        }
        return df.format(price).toDouble()
    }


    private fun sendEmail(cartPrice: String) {
        val uriText = "mailto:banana.shop@gmail.com" +
                "?subject=" + Uri.encode("Order from ${user.firstName}") +
                "&body=" + Uri.encode("Send to : ${user.address}\n\n" +
                                        "Products:\n $cart \n " +
                                        "Total: $cartPrice€\n\n" +
                                        "Any comment: ")
        val email = Intent(Intent.ACTION_SENDTO)
        val uri = Uri.parse(uriText)
        email.data = uri
        try {
            startActivity(
                Intent.createChooser(
                    email,
                    "Please send us the details of your order and any comments to help us deliver you the best service possible"
                )
            )
        } catch (e: Exception) {
            Toast.makeText(
                activity,
                "Unable to confirm your order, please try again later",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Order confirmation"
        val descriptionText = "Order confirmation"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(OnlinePurchase.onlinePurchaseContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.banana_icon)
            .setContentTitle("Order confirmed")
            .setContentText("Your order has been confirmed")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(OnlinePurchase.onlinePurchaseContext)) {
            notify(notificationId, builder.build())
        }
    }

    override fun onClickAdd(product: Product, quantity: Int) {
        if(quantity < 10) {
            cart.add(product)
            _binding!!.cartNbItems.text = cart.size.toString() + " items"
            _binding!!.cartPrice.text = computePrice(cart).toString() + "€"
            // Update the adapter
            displayCart()
        }
    }

    override fun onClickRemove(product: Product, quantity: Int) {
        if(quantity > 0) {
            cart.remove(product)
            _binding!!.cartNbItems.text = cart.size.toString() + " items"
            _binding!!.cartPrice.text = computePrice(cart).toString() + "€"
        }
        // Update the adapter
        displayCart()
    }

    private fun displayCart() {
        val productListView = _binding!!.cartProductList
        if (productListView is RecyclerView) {
            with(productListView) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter =
                    CartDetailListAdapter(cart.groupingBy { it.id }.eachCount(), clickListener)

            }
        }
    }
}