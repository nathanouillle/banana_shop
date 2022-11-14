package com.example.onlinepurchase.activity.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.databinding.ActivityForgotPasswordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // retrieve button
        val button = binding.resetPassword

        // reset password from database
        button.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.password2.text.toString()
            if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                if (password != confirmPassword) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                } else {
                    runBlocking {
                        val user = OnlinePurchase.onlinePurchaseDatabase.userDao()
                            .getUserByEmail(email)
                        if (user == null) {
                            Toast.makeText(this@ForgotPasswordActivity, "User does not exist", Toast.LENGTH_SHORT).show()
                        } else {
                            user.password = password
                            OnlinePurchase.onlinePurchaseDatabase.userDao().updateUser(user)
                            Toast.makeText(this@ForgotPasswordActivity, "Password updated with success", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
            }
        }

    }
}