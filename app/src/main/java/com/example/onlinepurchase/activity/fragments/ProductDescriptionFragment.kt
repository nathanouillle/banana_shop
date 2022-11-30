package com.example.onlinepurchase.activity.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import androidx.navigation.fragment.navArgs
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.utils.imageOptions
import com.example.onlinepurchase.databinding.FragmentProductDescriptionBinding

class ProductDescriptionFragment : Fragment() {

    private lateinit var binding: FragmentProductDescriptionBinding
    private val args: ProductDescriptionFragmentArgs by navArgs()
    private lateinit var product: Product

    //get cart from application
    private var cart = OnlinePurchase.cart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Retrieve the product to show
        val productID = args.productID
        runBlocking {
            launch(Dispatchers.IO) {
                product = Product.fromProductEntity(
                    OnlinePurchase.onlinePurchaseDatabase.productDao().getProductById(productID)
                )
            }
        }


        // Inflate the layout for this fragment
        binding = FragmentProductDescriptionBinding.inflate(layoutInflater, container, false)

        // Filling the fragment with the product
        if (product != null) {
            binding.textViewProductName.text = product.name
            binding.textDescription.text = product.description
            binding.textViewProductPrice.text = product.price.toString()
            // Fill image with Glide
            Glide.with(binding.root.context)
                .load(product.cover)
                .apply(imageOptions)
                .into(binding.imageViewProduct)

            // Display + and - buttons when added to cart
            val add = binding.buttonAdd
            val increment = binding.actionIncrement
            val decrement = binding.actionDecrement
            val numberTextView = binding.numberOfProduct

            // Display add button if product is not in cart
            if (cart.contains(product)) {
                add.visibility = View.INVISIBLE
                increment.visibility = View.VISIBLE
                decrement.visibility = View.VISIBLE
                numberTextView.visibility = View.VISIBLE
                numberTextView.text = computeNumberProducts(product).toString()
            } else {
                add.visibility = View.VISIBLE
                increment.visibility = View.INVISIBLE
                decrement.visibility = View.INVISIBLE
                numberTextView.visibility = View.INVISIBLE
            }

            // Add product to cart
            add.setOnClickListener {
                displayAddingViews(add, increment, decrement, numberTextView)
                cart.add(product)
            }
            increment.setOnClickListener {
                numberTextView.text = incrementCart(numberTextView.text)
            }

            decrement.setOnClickListener {
                var currentNumber = Integer.parseInt(numberTextView.text.toString())
                cart.remove(product)
                if (currentNumber == 1) {
                    displayAddingViews(add, increment, decrement, numberTextView)
                } else {
                    --currentNumber
                    numberTextView.text = currentNumber.toString()
                }
            }
        }

        return binding.root
    }

    private fun displayAddingViews(
        add: Button,
        increment: Button,
        decrement: Button,
        numberTextView: TextView
    ) {
        if (add.visibility == View.VISIBLE) {
            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
            add.visibility = View.INVISIBLE
            increment.visibility = View.VISIBLE
            decrement.visibility = View.VISIBLE
            numberTextView.visibility = View.VISIBLE
        } else {
            add.visibility = View.VISIBLE
            increment.visibility = View.INVISIBLE
            decrement.visibility = View.INVISIBLE
            numberTextView.visibility = View.INVISIBLE
        }
    }

    private fun incrementCart(number: CharSequence): CharSequence {
        var currentNumber = Integer.parseInt(number.toString())
        if (currentNumber < 10) {
            ++currentNumber
            cart.add(product)
        } else {
            Toast.makeText(activity, "You can't add more than 10 items", Toast.LENGTH_LONG).show()
        }
        return currentNumber.toString()
    }

    private fun computeNumberProducts(product: Product): Int {
        var number = 0
        for (p in cart) {
            if (p == product) {
                ++number
            }
        }
        return number
    }
}