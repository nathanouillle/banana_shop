package com.example.onlinepurchase.activity.activity

import android.os.Bundle
import com.example.onlinepurchase.R
import androidx.navigation.ui.navigateUp
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.onlinepurchase.activity.data.Product
import com.example.onlinepurchase.activity.menu.profil.ProfilFragment
import com.example.onlinepurchase.databinding.ActivityMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve userID from intent
        // The fragment will use it to retrieve the user's data
        val userID = intent.getIntExtra("userID", 0)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_cart, R.id.navigation_profil
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}