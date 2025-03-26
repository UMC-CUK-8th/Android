package com.example.week2_jack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // 앱 실행 시 기본 화면을 HomeFragment로 설정
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())  // 기본 화면 설정
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val selectedFragment: Fragment = when (menuItem.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_search -> SearchFragment()
                R.id.nav_category -> CategoryFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> HomeFragment()
            }

            replaceFragment(selectedFragment)
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out) // 슬라이드 애니메이션 효과
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
