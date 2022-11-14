package com.example.onlinepurchase.activity.menu.profil

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.data.*
import com.example.onlinepurchase.databinding.FragmentProfilBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates


class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private lateinit var categoryOption: Spinner
    private lateinit var productOption: Spinner
    private val options = listOf(1, 2, 3)
    private lateinit var user: User
    private var userID by Delegates.notNull<Int>()
    private lateinit var orders: List<Order>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get userID from menu activity
        userID = requireActivity().intent.getIntExtra("userID", 0)

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

        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Filling the profil
        _binding!!.titleProfil.text = user.firstName + "\n" + user.lastName
        val picture = user.picture?.let { BitmapFactory.decodeByteArray(user.picture, 0, it.size) }
        _binding!!.imageViewPicture.setImageBitmap(picture)

        // Category Spinner
        categoryOption = _binding!!.categorySpinner
        categoryOption.adapter =
            this.activity?.let {
                ArrayAdapter<Int>(
                    it,
                    android.R.layout.simple_spinner_item,
                    options
                )
            }

        categoryOption.setSelection(OnlinePurchase.preferences.getUserCategory() - 1)

        categoryOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                OnlinePurchase.preferences.setUserCategory(options[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // Product Spinner
        productOption = _binding!!.productSpinner
        productOption.adapter =
            this.activity?.let {
                ArrayAdapter<Int>(
                    it,
                    android.R.layout.simple_spinner_item,
                    options
                )
            }
        productOption.setSelection(OnlinePurchase.preferences.getUserProduct() - 1)

        productOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                OnlinePurchase.preferences.setUserProduct(options[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        runBlocking(Dispatchers.IO) {
            user = OnlinePurchase.onlinePurchaseDatabase.userDao().getUserById(userID).toUser()
        }
        _binding?.userEmail?.setText(user.email)
        _binding?.userAddress?.setText(user.address)
        _binding?.userPhone?.setText(user.phone)
    }

    override fun onPause() {
        super.onPause()
        val email = _binding?.userEmail?.text.toString()
        val address = _binding?.userAddress?.text.toString()
        val phone = _binding?.userPhone?.text.toString()

        if (email.isNotBlank()) {
            runBlocking(Dispatchers.IO) {
                OnlinePurchase.onlinePurchaseDatabase.userDao().updateUserEmail(userID, email)
            }
        }
        if (address.isNotBlank()) {
            runBlocking(Dispatchers.IO) {
                OnlinePurchase.onlinePurchaseDatabase.userDao().updateUserAddress(userID, address)
            }
        }
        if (phone.isNotBlank()) {
            runBlocking(Dispatchers.IO) {
                OnlinePurchase.onlinePurchaseDatabase.userDao().updateUserPhone(userID, phone)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

