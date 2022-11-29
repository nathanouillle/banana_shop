package com.example.onlinepurchase.activity.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.content.Intent
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.launch
import com.example.onlinepurchase.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinepurchase.activity.OnlinePurchase
import com.example.onlinepurchase.activity.data.Category
import com.example.onlinepurchase.activity.database.product.ProductEntity
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
    /*
    private fun insertProductsWithoutNetworking() {
        runBlocking {
            launch(Dispatchers.IO) {
                OnlinePurchase.onlinePurchaseDatabase.productDao().deleteAllProducts()

                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Fruits",
                        cover = R.drawable.fruits,
                        type = 1,
                        category = Category.Fruits
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Apple",
                        description = "Green apple",
                        price = 1.0,
                        cover = R.drawable.green_apple,
                        promoted = true,
                        type = 2,
                        category = Category.Fruits
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Blueberry",
                        description = "Small berry of blue-purple color",
                        price = 3.99,
                        cover = R.drawable.blueberry,
                        type = 2,
                        category = Category.Fruits
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Kiwi",
                        description = "This fruit is from China and grows up only in the beginning of november",
                        price = 0.69,
                        cover = R.drawable.kiwi,
                        type = 2,
                        category = Category.Fruits
                    )
                )


                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Vegetables",
                        cover = R.drawable.vegetables,
                        type = 1,
                        category = Category.Vegetables
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Tomato",
                        description = "Tomatoes are a good source of vitamin C and vitamin K, and a very good source of vitamin A and potassium.",
                        price = 1.0,
                        cover = R.drawable.tomato,
                        promoted = true,
                        type = 2,
                        category = Category.Vegetables
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Cucumber",
                        description = "Cucumbers are perfect for salads, sandwiches, and as a side dish.",
                        price = 3.99,
                        cover = R.drawable.minions,
                        type = 2,
                        category = Category.Vegetables
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Carrot",
                        description = "From France",
                        price = 0.69,
                        cover = R.drawable.carrot,
                        type = 2,
                        category = Category.Vegetables
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Pepper",
                        description = "Yellow good looking pepper",
                        price = 1.49,
                        cover = R.drawable.pepper,
                        type = 2,
                        category = Category.Vegetables
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Potato",
                        description = "Quickly cooked, these potatoes are delicious and nutritious.",
                        price = 2.39,
                        cover = R.drawable.potato,
                        type = 2,
                        category = Category.Vegetables
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Salad",
                        description = "Big crunchy leaves, yum yum",
                        price = 1.89,
                        cover = R.drawable.salad,
                        type = 2,
                        category = Category.Vegetables
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Onion",
                        description = "Yellow onion",
                        price = 0.39,
                        cover = R.drawable.onion,
                        type = 2,
                        category = Category.Vegetables
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Aubergine",
                        description = "Very good aubergine",
                        price = 1.5,
                        cover = R.drawable.aubergine,
                        type = 2,
                        category = Category.Vegetables
                    )
                )

                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Meat",
                        cover = R.drawable.meats,
                        type = 1,
                        category = Category.Meats
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Beef",
                        description = "Beef is the culinary name for meat from cattle, particularly skeletal muscle.",
                        price = 5.0,
                        cover = R.drawable.ground_beef,
                        promoted = true,
                        type = 2,
                        category = Category.Meats
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Chicken",
                        description = "Chicken is the most common type of poultry in the world.",
                        price = 3.99,
                        cover = R.drawable.chicken,
                        type = 2,
                        category = Category.Meats
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Pork",
                        description = "Pork is the culinary name for meat from the domestic pig.",
                        price = 4.69,
                        cover = R.drawable.minions,
                        type = 2,
                        category = Category.Meats
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Milanesa",
                        description = "Milanesa is a thin slice of meat, usually beef, coated in bread crumbs and fried.",
                        price = 5.0,
                        cover = R.drawable.milanesa,
                        promoted = true,
                        type = 2,
                        category = Category.Meats
                    )
                )

                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Morning",
                        cover = R.drawable.morning,
                        type = 1,
                        category = Category.Morning
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Bread",
                        description = "Bread is a staple food prepared from a dough of flour and water, usually by baking.",
                        price = 1.0,
                        cover = R.drawable.bread,
                        promoted = true,
                        type = 2,
                        category = Category.Morning
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Jam",
                        description = "Jam is a food preservation method in which fruit or vegetables are cooked with sugar and sealed in an airtight container.",
                        price = 4.0,
                        cover = R.drawable.jam,
                        promoted = true,
                        type = 2,
                        category = Category.Morning
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Cereals",
                        description = "Black chocolate and roasted almonds cereals",
                        price = 5.7,
                        cover = R.drawable.cereals,
                        type = 2,
                        category = Category.Morning
                    )
                )

                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Drinks",
                        cover = R.drawable.minions,
                        type = 1,
                        category = Category.Drinks
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Coca Cola",
                        description = "Coca Cola is a carbonated soft drink produced by The Coca-Cola Company. Originally intended as a patent medicine, it was invented in the late 19th century by John Stith Pemberton and was bought out by businessman Asa Griggs Candler, whose marketing tactics led Coca-Cola to its dominance of the world soft-drink market throughout the 20th century.",
                        price = 2.5,
                        cover = R.drawable.minions,
                        promoted = true,
                        type = 2,
                        category = Category.Drinks
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Pepsi",
                        description = "Pepsi is a carbonated soft drink manufactured by PepsiCo. Originally created and developed in 1893 by Caleb Bradham and introduced as Brad's Drink, it was renamed as Pepsi-Cola on August 28, 1898, and then as Pepsi in 1961. The drink Pepsi-Cola was first introduced as a carbonated beverage in New Bern, North Carolina, United States, in 1898 by Caleb Bradham, who made it at his drugstore where the drink was sold.",
                        price = 2.5,
                        cover = R.drawable.minions,
                        promoted = true,
                        type = 2,
                        category = Category.Drinks
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Fanta",
                        description = "Fanta is a brand of fruit-flavored carbonated soft drinks created by The Coca-Cola Company. The original formula was invented in Germany in 1940 by Max Keith, a New York-based food technologist working for Coca-Cola. The drink was first introduced in Germany in 1940 as Fanta Klare Zitrone, and was introduced in the United States in 1941 as Fanta Orange.",
                        price = 2.5,
                        cover = R.drawable.minions,
                        promoted = true,
                        type = 2,
                        category = Category.Drinks
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "Sprite",
                        description = "Sprite is a colorless, lemon and lime-flavored soft drink created by The Coca-Cola Company. It was first developed in West Germany in 1959 as Fanta Klare Zitrone, then renamed Sprite in 1961 when it was introduced in the United States. Sprite is caffeine-free and has a lemon-lime flavor. It is the best-selling lemon-lime soft drink in the world.",
                        price = 2.5,
                        cover = R.drawable.minions,
                        promoted = true,
                        type = 2,
                        category = Category.Drinks
                    )
                )
                OnlinePurchase.onlinePurchaseDatabase.productDao().insertProduct(
                    ProductEntity(
                        name = "7up",
                        description = "7 Up is a brand of lemon-lime flavored non-caffeinated soft drink. It is produced and distributed by Dr Pepper Snapple Group. The original formula was created in 1929 by Charles Leiper Grigg, who sold the formula to the UP Corporation, a division of the Dr Pepper Company. The drink was first introduced in 1936 as Bib-Label Lithiated Lemon-Lime Soda.",
                        price = 2.5,
                        cover = R.drawable.minions,
                        promoted = true,
                        type = 2,
                        category = Category.Drinks
                    )
                )
            }
        }
    }*/
}