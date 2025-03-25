package com.example.week1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        val btnHappy = findViewById<Button>(R.id.btn_1) // 행복 버튼
        val btnExcited = findViewById<Button>(R.id.btn_2) // 들뜬 버튼
        val btnNormal = findViewById<Button>(R.id.btn_3) // 평범한 버튼
        val btnAnxious = findViewById<Button>(R.id.btn_4) // 불안한 버튼
        val btnAngry = findViewById<Button>(R.id.btn_5) // 화난 버튼

        btnHappy.setOnClickListener {
            val intent = Intent(this, HappyActivity::class.java)
            startActivity(intent)
        }

        btnExcited.setOnClickListener {
            val intent = Intent(this, ExcitedActivity::class.java)
            startActivity(intent)
        }

        btnNormal.setOnClickListener {
            val intent = Intent(this, NormalActivity::class.java)
            startActivity(intent)
        }

        btnAnxious.setOnClickListener {
            val intent = Intent(this, AnxiousActivity::class.java)
            startActivity(intent)
        }

        btnAngry.setOnClickListener {
            val intent = Intent(this, AngryActivity::class.java)
            startActivity(intent)
        }
    }
}
