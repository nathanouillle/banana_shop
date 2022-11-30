package com.example.onlinepurchase.activity.activity

import android.util.Log
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import android.content.Intent
import kotlinx.coroutines.launch
import com.example.onlinepurchase.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.networking.ProductListDTO
import com.example.onlinepurchase.activity.networking.ProductRetrofit
import com.example.onlinepurchase.activity.preferences.SharedPreferences

class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Check internet connection
        if (!OnlinePurchase.isNetworkAvailable(context = this)) {
            // Dialog to show no internet connection
            OnlinePurchase.showNoInternetDialog(context = this)
        } else {

            // Connect to API and update database
            initializeProducts()

            // Check if user is connected
            val sharedPreferences = SharedPreferences(this)
            val userConnected = sharedPreferences.getUserID()


            // wait 3 seconds before going to the next activity
            handler = Handler()
            handler.postDelayed({
                if (userConnected != -1) {
                    // Go to the main activity
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                } else {
                    // Go to the login activity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }, 3000)
        }

    }


    private fun initializeProducts() {

        // Get product list from API
        val productRetrofit = ProductRetrofit()
        val serviceResult = productRetrofit.productRetrofitService.getProductList()
        serviceResult.enqueue(object : retrofit2.Callback<ProductListDTO> {
            override fun onResponse(
                call: retrofit2.Call<ProductListDTO>,
                response: retrofit2.Response<ProductListDTO>
            ) {
                if (response.isSuccessful) {
                    val productList = response.body()?.productList
                    if (productList != null) {
                        // convert product list to product entity list
                        val productEntityList = ProductListDTO(productList).toProductEntityList()

                        // update product list in database
                        runBlocking {
                            launch(Dispatchers.IO) {
                                OnlinePurchase.onlinePurchaseDatabase.productDao().deleteAllProducts()
                                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProductList(
                                    productEntityList
                                )
                            }

                        }
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<ProductListDTO>, t: Throwable) {
                Toast.makeText(this@SplashActivity, "Error initializing the database", Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Error: ${t.message}")
            }
        })
    }
}