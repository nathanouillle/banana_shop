package com.example.onlinepurchase.activity.activity

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.networking.ProductListDTO
import com.example.onlinepurchase.activity.networking.ProductRetrofit
import com.example.onlinepurchase.databinding.ActivityLoginBinding
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    private lateinit var productRetrofit: ProductRetrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        Glide.with(this)
            .load("")
            .into(binding.imageView)*/


        binding.login.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                // Search for user in database
                runBlocking { // runBlocking is used to wait for the result of the coroutine
                    val user = OnlinePurchase.onlinePurchaseDatabase.userDao()
                        .connectUser(username, password)
                    if (user != null) {
                        // User found

                        // Save user connected in shared preferences
                        user.id?.let { it1 -> OnlinePurchase.preferences.setUserID(it1) }

                        // Go to the menu activity
                        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                        startActivity(intent)
                    } else {
                        // User not found
                        Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(baseContext, "Complete all fields", Toast.LENGTH_LONG).show()
            }
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}