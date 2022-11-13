package com.example.onlinepurchase.activity.activity

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.databinding.ActivityLoginBinding
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if(username.isNotBlank() && password.isNotBlank()) {
                // Search for user in database
                runBlocking { // runBlocking is used to wait for the result of the coroutine
                    val user = OnlinePurchase.onlinePurchaseDatabase.userDao().connectUser(username, password)
                    if(user != null) {
                        // User found
                        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                        intent.putExtra("userID", user.id)
                        startActivity(intent)
                    } else {
                        // User not found
                        Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
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