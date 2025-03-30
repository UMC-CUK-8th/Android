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
                    // 홈 버튼 눌렀을 때 (현재 화면이니까 아무 것도 안 해도 됨)
                    true
                }
                R.id.nav_pencil -> {
                    // pencil 버튼 누르면 PencilActivity로 이동
                    val intent = Intent(this, PencilActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_calender -> {
                    // pencil 버튼 누르면 CalenderActivity로 이동
                    val intent = Intent(this, PencilActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_user -> {
                    // pencil 버튼 누르면 UserActivity로 이동
                    val intent = Intent(this, PencilActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
