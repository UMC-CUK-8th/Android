package com.example.week2_2  // ← 여기 패키지명은 너 프로젝트에 맞춰서!

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }
}