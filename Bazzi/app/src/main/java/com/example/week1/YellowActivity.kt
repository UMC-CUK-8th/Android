package com.example.week1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity  // ← 여기 꼭 이걸로!

class YellowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yellow)  // XML 레이아웃 연결!
    }
}