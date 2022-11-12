package com.example.onlinepurchase.activity.activity

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.database.ProductEntity
import com.example.onlinepurchase.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    val productListLiveData = MutableLiveData<List<ProductEntity>>()
    val productByIdLiveData = MutableLiveData<ProductEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if(username.isNotBlank() && password.isNotBlank()) {
                //Try to connect
                Toast.makeText(baseContext, "Welcome $username!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MenuActivity::class.java))
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