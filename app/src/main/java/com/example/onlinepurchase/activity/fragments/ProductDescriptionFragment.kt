package com.example.onlinepurchase.activity.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.data.productsList
import com.example.onlinepurchase.databinding.FragmentProductDescriptionBinding

class ProductDescriptionFragment: Fragment() {

    private lateinit var binding: FragmentProductDescriptionBinding
    private val args: ProductDescriptionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Retrieve the product to show
        val productID = args.productID
        val product = productFromID(productID)

        // Inflate the layout for this fragment
        val binding = FragmentProductDescriptionBinding.inflate(layoutInflater, container, false)

        // Filling the fragment with the product
        if(product!=null) {
            binding.textViewProductName.text = product.name
            binding.textDescription.text = product.description
            binding.textViewProductPrice.text = product.price.toString()
            binding.imageViewProduct.setImageResource(product.cover)

            // Display + and - buttons when added to cart
            val add = binding.buttonAdd
            val increment = binding.actionIncrement
            val decrement = binding.actionDecrement
            val numberTextView = binding.numberOfProduct

            add.setOnClickListener {
                displayAddingViews(add, increment, decrement, numberTextView)

                // +
                increment.setOnClickListener {
                    numberTextView.text = incrementCart(numberTextView.text)
                }

                // -
                decrement.setOnClickListener {
                    var currentNumber = Integer.parseInt(numberTextView.text.toString())
                    if(currentNumber==1) {
                        displayAddingViews(add,increment,decrement,numberTextView)
                    } else {
                        --currentNumber
                        numberTextView.text = currentNumber.toString()
                    }
                }
            }
        }


        return binding.root
    }

    private fun productFromID(productID: Int): Product? {
        for(product in productsList)
        {
            if(product.id == productID)
                return product
        }
        return null
    }

    private fun displayAddingViews(add: Button, increment: Button, decrement: Button, numberTextView: TextView){
        if(add.visibility==View.VISIBLE) {
            add.visibility = View.INVISIBLE
            increment.visibility = View.VISIBLE
            decrement.visibility = View.VISIBLE
            numberTextView.visibility = View.VISIBLE
        } else{
            add.visibility = View.VISIBLE
            increment.visibility = View.INVISIBLE
            decrement.visibility = View.INVISIBLE
            numberTextView.visibility = View.INVISIBLE
        }
    }

    private fun incrementCart(number: CharSequence): CharSequence{
        var currentNumber = Integer.parseInt(number.toString())
        if(currentNumber<10) {
            ++currentNumber
        } else{
            Toast.makeText(activity, "You can't add more than 10 items", Toast.LENGTH_LONG).show()
        }
        return currentNumber.toString()
    }
}