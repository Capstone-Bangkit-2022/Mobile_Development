package com.capstone.myapplication.module.homepage

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.myapplication.R
import com.capstone.myapplication.base.BaseActivity
import com.capstone.myapplication.databinding.ActivityHomepageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomepageActivity : BaseActivity<ActivityHomepageBinding>(R.layout.activity_homepage) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeNavigationView()
    }

    private fun initializeNavigationView() {
        val navView: BottomNavigationView = binding.bottomNavView

        val navController = findNavController(R.id.nav_host_fragment_activity_homepage)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_cart,
                R.id.navigation_agent, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}