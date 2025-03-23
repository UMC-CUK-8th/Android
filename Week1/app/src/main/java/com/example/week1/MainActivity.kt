package com.example.week1

import android.content.Intent              // ← 인텐트 import!
import android.os.Bundle
import android.view.View                 // ← View import도 필요!
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 💡 버튼 클릭 시 호출될 함수
    fun goToYellow(view: View) {
        val intent = Intent(this, YellowActivity::class.java)
        startActivity(intent)
    }
}