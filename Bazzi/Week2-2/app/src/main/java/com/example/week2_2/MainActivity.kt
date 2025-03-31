package com.example.week2_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {

                    supportFragmentManager.popBackStack(
                        null,
                        androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    true
                }

                R.id.nav_pencil -> {
                    val fragment = PencilFragment()
                    showFragment(fragment)
                    true
                }

                R.id.nav_calender -> {
                    val fragment = CalendarFragment()
                    showFragment(fragment)
                    true
                }

                R.id.nav_user -> {
                    val fragment = UserFragment()
                    showFragment(fragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun showFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
