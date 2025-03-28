package com.example.week2_jack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.fragment.app.FragmentTransaction
import com.example.week2_jack.R
import com.example.week2_jack.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavOptions

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(mBinding.myBottomNav, navController)
        mBinding.myBottomNav.setOnItemSelectedListener { menuItem ->

            val navOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in)
                .setExitAnim(R.anim.slide_out)
                .build()


            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment, null, navOptions)
                }
                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment, null, navOptions)
                }
                R.id.categoryFragment -> {
                    navController.navigate(R.id.categoryFragment, null, navOptions)
                }
                R.id.profileFragment -> {
                    navController.navigate(R.id.profileFragment, null, navOptions)
                }
            }

            true
        }
        }
    }
